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

package net.sf.nmedit.jnmprotocol2;

import net.sf.nmedit.jpdl2.PDLPacket;
import net.sf.nmedit.jpdl2.stream.BitStream;
import net.sf.nmedit.jpdl2.stream.IntStream;

public class PatchMessage extends MidiMessage
{
    private BitStream patchStream;
    
    private PatchMessage()
    {
	super();
	addParameter("pid", "data:pid");
	set("cc", 0x1c);
	set("pid", 0);

	expectsreply = true;
	isreply = true;
    }

    public PatchMessage(PDLPacket packet)
    {
	this();
    patchStream = new BitStream();
	setAll(packet);
	
	packet = packet.getPacket("data:next");
	while (packet != null) {
	    patchStream.append(packet.getVariable("data"), 7);
	    packet = packet.getPacket("next");
	}
	// Remove padding
	patchStream.setSize((patchStream.getSize()/8)*8);
    }

    public PatchMessage(BitStream section, int slot, int sectionIndex, int sectionCount) throws MidiException
    {
	this();
	set("slot", slot);

	// Create sysex messages

	    int first = sectionIndex == 0 ? 1 : 0;
	    int last = sectionIndex == (sectionCount-1) ? 1 : 0;
	    int sectionsEnded = sectionIndex+1;
        BitStream partialPatchStream = BitStream.copyOf(section);
        partialPatchStream.setSize((partialPatchStream.getSize()/8)*8);
        // Pad. Extra bits are ignored later.
        partialPatchStream.append(0, 6);

        // Generate sysex bistream
        IntStream intStream = new IntStream();
        intStream.append(get("cc") + first + 2*last);
        first = 0;
        intStream.append(get("slot"));
        intStream.append(0x01);
        intStream.append(sectionsEnded);
        partialPatchStream.setPosition(0);
        while (partialPatchStream.isAvailable(7)) {
        intStream.append(partialPatchStream.getInt(7));
        }
        
        intStream.setPosition(0);
        
        // Generate sysex bitstream
        this.patchStream = getBitStream(intStream);
    }

    public BitStream getBitStream() 
    {
    return patchStream;
    }
    
    public void notifyListener(NmProtocolListener listener)
    {
	listener.messageReceived(this);
    }
}
