/*
    Nord Modular Midi Protocol 3.03 Library
    Copyright (C) 2003-2006 Marcus Andersson

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

package net.sf.nmedit.jnmprotocol;

import java.util.*;
import net.sf.nmedit.jpdl.*;

/**
 * A message containing the names of consecutive patches
 * stored in the synthesizer.
 * 
 * The message has two parameters section and position
 * describing the location of the first patch.
 * 
 * Subsequent patches are at the next position (position+1).
 * 
 * If the patch name starts with the byte 0x01 then the second
 * byte (supplementary value) contains the absolute position
 * (position=getSupplementaryValue(name)), in the current section.
 * 
 * If the patch name starts with the byte 0x02 then the current
 * location is unused, thus the patch name does not exist.
 * 
 * If the patch name starts with the byte 0x03 then the 
 * second byte (supplementary value) contains the section
 * of the next patch name. (section=getSupplementaryValue(name),
 * position=0)
 */
public class PatchListMessage extends MidiMessage
{
    private LinkedList<PatchListEntry> names;
    private int nextPosition;
    private int nextSection;

    public PatchListMessage()
	throws Exception
    {
	super();

	names = new LinkedList();

	set("cc", 0x16);
	set("slot", 0);

	isreply = true;
    }

    PatchListMessage(Packet packet)
	throws Exception
    {
	this();
	setAll(packet);
	
	int section = 0;
	int position = 0;
	Packet entry = packet.getPacket("data:patchList:data");
	
	while (entry != null) {
	    Packet cmd = entry.getPacket("cmd");
	    if (cmd != null) {
		if (cmd.contains("NextPosition")) {
		    position = cmd.getVariable("nextposition:position");
		}
		else if (cmd.contains("EmptyPosition")) {
		    position++;
		}
		else if (cmd.contains("NextSection")) {
		    section = cmd.getVariable("nextsection:section");
		    position = cmd.getVariable("nextsection:position");
		}
	    }
	    names.add(new PatchListEntry(extractName(entry.getPacket("name")),
					 section,
					 position));
		    
	    position++;
	    entry = entry.getPacket("next");
	}
	if (packet.getVariable("data:patchList:endmarker") == 4) {
	    section = -1;
	    position = -1;
	}
	if (position == 99) {
	    position = 0;
	    section++;
	}
	if (section == 9) {
	    section = -1;
	}
	nextPosition = position;
	nextSection = section;
    }

    public List getBitStream()
	throws Exception
    {
	throw new
	    MidiException("PatchListMessage::getBitStream not implemented.",
			  0);
    }
    
    public void notifyListener(NmProtocolListener listener)
	throws Exception
    {
	listener.messageReceived(this);
    }

    /** If list is empty, it means all patch names has been sent from
     * the synth.
     */
    public List<PatchListEntry> getEntries()
    {
	return names;
    }

    public int getNextSection()
    {
	return nextSection;
    }
    
    public int getNextPosition()
    {
	return nextPosition;
    }
}
