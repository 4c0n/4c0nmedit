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
package net.sf.nmedit.jpdl2;

/**
 * TODO 
 */
public class PDLWriter
{
    
    private int indentIncrement = 4;
    private String newline = "\r\n";
    private StringBuilder s;
    private String headerComment = "# generated by "+getClass().getName();
    
    public PDLWriter(StringBuilder s)
    {
        this.s = s;
    }

    public void append(PDLDocument doc)
    {
        if (headerComment != null)
        {
            s.append(headerComment);
            s.append(newline);
        }
        for (PDLPacketDecl packetdecl: doc)
        {
            append(packetdecl);
            s.append(newline);
        }
    }
    
    public void append(PDLOptional optional)
    {
        s.append('?');
        appendBlock(optional, 0);
    }
    
    public void append(PDLMultiplicity multiplicity)
    {
        if (multiplicity.getConstant()>=0)
        {
            s.append(Integer.valueOf(multiplicity.getConstant()));
            s.append('*');
        }
        else if (multiplicity.getVariable() != null)
        {
            s.append(multiplicity.getVariable());
            s.append('*');
        }
    }

    public void append(PDLLabel label)
    {
        s.append('@');
        s.append(label.getName());
    }
    
    public void append(PDLConstant constant)
    {
        if (constant.getMultiplicity() != null)
            append(constant.getMultiplicity());
        s.append(Integer.valueOf(constant.getValue()));
        s.append(':');
        s.append(Integer.valueOf(constant.getSize()));
    }
    
    public void append(PDLVariable variable)
    {
        s.append(variable.getName());
        s.append(':');
        s.append(Integer.valueOf(variable.getSize()));
    }
    
    public void append(PDLVariableList variableList)
    {
        append(variableList.getMultiplicity());
        append(variableList.getVariable());
    }

    public void append(PDLPacketRef packetReference)
    {
        s.append(packetReference.getPacketName());
        s.append('$');
        s.append(packetReference.getBinding());
    }

    public void append(PDLPacketRefList packetRefList)
    {
        append(packetRefList.getMultiplicity());
        append(packetRefList.getPacketRef());
    }
    

    public void append(PDLConditional conditionalBlock)
    {
        append(conditionalBlock, 0);
    }
    
    public void append(PDLConditional conditionalBlock, int indent)
    {
        s.append(conditionalBlock.getCondition());
        appendBlock(conditionalBlock, indent+indentIncrement);
    }
    
    private void appendBlock(PDLBlock block, int indent)
    {
        if (block.getItemCount() == 1)
        {
            append(block.getItem(0));
            s.append(newline);
        }
        else if (block.getItemCount() > 1)
        {
            s.append("{");
            s.append(newline);
            for (int i=0;i<block.getItemCount();i++)
            {
                append(' ', (i+1)%6==0 ? indent : 1);
                PDLItem item = block.getItem(i);
                append(item, indent);
            }
            s.append(newline);
            s.append('}');
            s.append(newline);
        }
    }

    public void append(PDLItem item)
    {
        append(item, 0);
    }
    
    public void append(PDLItem item, int indent)
    {
        switch (item.getType())
        {
            case Conditional:
                append(item.asConditional(), indent+indentIncrement);
                break;
            case Label:
                append(item.asLabel());
                break;
            case Constant:
                append(item.asConstant());
                break;
            case PacketRef:
                append(item.asPacketRef());
                break;
            case PacketRefList:
                append(item.asPacketRefList());
                break;
            case Variable:
                append(item.asVariable());
                break;
            case VariableList:
                append(item.asVariableList());
                break;
            case Optional:
                append(item.asOptional());
                break;
            default:
                throw new InternalError("unsupported type: "+item.getType());
        }    
    }

    public void append(PDLPacketDecl pdecl)
    {
        s.append(pdecl.getName());
        s.append('%');
        s.append(Integer.valueOf(pdecl.getPadding()));
        s.append(" := \r\n");
        appendBlock(pdecl, 0);
        s.append("\r\n");
        append(' ', indentIncrement);
        s.append(";\r\n");
    }
    
    private void append(char c, int count)
    {
        while (count>0)
        {
            s.append(c);
            count--;
        }
    }
    
}
