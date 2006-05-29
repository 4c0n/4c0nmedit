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

/*
 * Created on Mar 9, 2006
 */
package net.sf.nmedit.nomad.theme.property;

import java.awt.Font;

import net.sf.nmedit.nomad.theme.component.NomadComponent;
import net.sf.nmedit.nomad.theme.property.editor.Editor;
import net.sf.nmedit.nomad.theme.property.editor.FontEditor;


public abstract class FontProperty extends Property
{

    public FontProperty( String name )
    {
        super( name );
        // TODO Auto-generated constructor stub
    }

    @Override
    public Value decode( String value )
    {
        return decodeFont(value);
    }

    @Override
    public Value encode( NomadComponent component )
    {
        return encodeFont(component);
    }

    @Override
    public Editor newEditor( NomadComponent component )
    {
        return new FontEditor(this, component);
    }

    public abstract FontValue encodeFont( Font font );
    public abstract FontValue encodeFont( NomadComponent component );
    public abstract FontValue decodeFont(String value);

}