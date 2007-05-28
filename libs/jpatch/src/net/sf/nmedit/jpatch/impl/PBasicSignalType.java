/* Copyright (C) 2006 Christian Schneider
 * 
 * This file is part of Nomad.
 * 
 * Nomad is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * Nomad is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Nomad; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package net.sf.nmedit.jpatch.impl;

import java.awt.Color;
import java.io.Serializable;

import net.sf.nmedit.jpatch.PSignalType;
import net.sf.nmedit.jpatch.PSignalTypes;

public class PBasicSignalType implements PSignalType, Serializable
{

    private static final long serialVersionUID = -2637295506140546814L;
    
    private PSignalTypes parent;
    private int id;
    private String name;
    private Color color;

    public PBasicSignalType(PSignalTypes parent, int id, String name, Color color)
    {
        this.parent = parent;
        this.id = id;
        this.name = name;
        this.color = color;
    }
    
    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public PSignalTypes getSignalTypes()
    {
        return parent;
    }

    public String toString()
    {
        return getClass().getName()+"[id="+id+",name="+name+",color="+color+"]";
    }

    public Color getColor()
    {
        return color;
    }
    
    public int hashCode()
    {
        return id;
    }
    
    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (o == null || (!(o instanceof PSignalType))) return false;
        
        PSignalType st = (PSignalType) o;
        return st.getId() == id && 
        ((name == st.getName()) || ((name != null) && name.equals(st.getName())));
    }

}
