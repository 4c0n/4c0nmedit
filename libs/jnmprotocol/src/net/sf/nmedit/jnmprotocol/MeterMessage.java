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

public class MeterMessage extends MidiMessage
{
    public MeterMessage()
    {
	super();

	addParameter("pid", "data:pid");
	addParameter("startIndex", "data:data:startIndex");
	addParameter("b0", "data:data:b0");
	addParameter("a0", "data:data:a0");
	addParameter("b1", "data:data:b1");
	addParameter("a1", "data:data:a1");
	addParameter("b2", "data:data:b2");
	addParameter("a2", "data:data:a2");
	addParameter("b3", "data:data:b3");
	addParameter("a3", "data:data:a3");
	addParameter("b4", "data:data:b4");
	addParameter("a4", "data:data:a4");
    }

    MeterMessage(Packet packet)
    {
	this();
	setAll(packet);
    }

    public List<BitStream> getBitStream()
	throws Exception
    {
	throw new MidiException("MeterMessage::getBitStream not implemented.",
				0);
    }
    
    public void notifyListener(NmProtocolListener listener)
    {
	listener.messageReceived(this);
    }
}
