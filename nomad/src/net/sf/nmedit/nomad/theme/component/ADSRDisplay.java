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
 * Created on Jul 26, 2006
 */
package net.sf.nmedit.nomad.theme.component;

import net.sf.nmedit.jpatch.clavia.nordmodular.v3_03.Module;
import net.sf.nmedit.jpatch.clavia.nordmodular.v3_03.Parameter;
import net.sf.nmedit.jpatch.clavia.nordmodular.v3_03.event.Event;
import net.sf.nmedit.nomad.theme.property.ParameterProperty;
import net.sf.nmedit.nomad.theme.property.PropertySet;

public class ADSRDisplay extends ADSRModDisplay
{

    // attack type
    private Parameter parAT = null;
    public final static String IAT = "parameter#5";
    
    public void registerProperties(PropertySet set) {
        super.registerProperties(set);
        set.add(new ParameterProperty(5));
    }

    public void link(Module module) 
    {
        parAT = module.getParameter(getParameterInfo(IAT).getContextId());
        if (parAT!=null) parAT.addParameterListener(this);
        super.link(module);
    }

    public void unlink() {
        if (parAT!=null) parAT.removeParameterListener(this);
        parAT = null;
        super.unlink();
    }
    
    public void parameterValueChanged( Event e )
    {
        super.parameterValueChanged(e);
        Parameter p = e.getParameter();
        if (parAT==p) setAttackType(p.getValue());
    }

    protected void updateValues()
    {
        if (parAT!=null) setAttackType(parAT.getValue());
        super.updateValues();
    }
    

}
