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
 * Created on Jan 21, 2007
 */
package net.sf.nmedit.jtheme.component;

import net.sf.nmedit.jtheme.JTContext;
import net.sf.nmedit.jtheme.util.RetargetMouseEventSupport;

public class JTDisplay extends JTComponent
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -2772033221761972736L;
    public static final String uiClassID = "DisplayUI";
    
    public JTDisplay(JTContext context)
    {
        super(context);
        setOpaque(true);
        RetargetMouseEventSupport rmes = RetargetMouseEventSupport.retargetToParent(this);
        rmes.install(this);
    }
    
    public String getUIClassID()
    {
        return uiClassID;
    }
    
}
