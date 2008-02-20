/*
    Protocol Definition Language
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
package net.sf.nmedit.jpdl2.impl;

import net.sf.nmedit.jpdl2.PDLBlockItem;
import net.sf.nmedit.jpdl2.PDLConditional;
import net.sf.nmedit.jpdl2.PDLConstant;
import net.sf.nmedit.jpdl2.PDLInstruction;
import net.sf.nmedit.jpdl2.PDLImplicitVariable;
import net.sf.nmedit.jpdl2.PDLItemType;
import net.sf.nmedit.jpdl2.PDLLabel;
import net.sf.nmedit.jpdl2.PDLChoice;
import net.sf.nmedit.jpdl2.PDLOptional;
import net.sf.nmedit.jpdl2.PDLPacketRef;
import net.sf.nmedit.jpdl2.PDLPacketRefList;
import net.sf.nmedit.jpdl2.PDLSwitchStatement;
import net.sf.nmedit.jpdl2.PDLVariable;
import net.sf.nmedit.jpdl2.PDLVariableList;

public class PDLBlockItemImpl extends PDLBlockImpl implements PDLBlockItem
{

    public PDLLabel asLabel()
    {
        return PDLLabel.class.cast(this);
    }
    
    public PDLConstant asConstant()
    {
        return PDLConstant.class.cast(this);
    }

    public PDLVariable asVariable()
    {
        return PDLVariable.class.cast(this);
    }

    public PDLImplicitVariable asImplicitVariable()
    {
        return PDLImplicitVariable.class.cast(this);
    }

    public PDLPacketRef asPacketRef()
    {
        return PDLPacketRef.class.cast(this);
    }

    public PDLConditional asConditional()
    {
        return PDLConditional.class.cast(this);
    }

    public PDLOptional asOptional()
    {
        return PDLOptional.class.cast(this);
    }

    public PDLPacketRefList asPacketRefList()
    {
        return PDLPacketRefList.class.cast(this);
    }

    public PDLVariableList asVariableList()
    {
        return PDLVariableList.class.cast(this);
    }

    public PDLChoice asChoice()
    {
        return PDLChoice.class.cast(this);
    }

    public PDLInstruction asInstruction()
    {
        return PDLInstruction.class.cast(this);
    }

    public PDLBlockItem asBlock()
    {
        return this;
    }

    public PDLItemType getType()
    {
        return PDLItemType.Block;
    }

    public PDLSwitchStatement asSwitchStatement()
    {
        return PDLSwitchStatement.class.cast(this);
    }
    
}
