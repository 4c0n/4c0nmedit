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
package net.sf.nmedit.jpatch.clavia.nordmodular.formatter;

import net.sf.nmedit.jpatch.Parameter;
import net.sf.nmedit.jpatch.clavia.nordmodular.formatter.Math2;
import net.sf.nmedit.jpatch.spec.formatter.Formatter;

/**
 * @author Christian Schneider
 * @hidden
 */
public class DigitizerHz  implements Formatter
{

    public String getString( Parameter parameter, int value )
    {
        double aFloat = 32.70 * Math.pow(2.0, value/12.0d);

        if (aFloat<100)
            return ""+Math2.roundTo(aFloat, -2)+" Hz";
        
        else if (aFloat<1000)
            return ""+Math2.roundTo(aFloat, -1)+" Hz";
        
        else if (aFloat<10000)
            return ""+Math2.roundTo(aFloat/1000.0d, -3)+" kHz";
        
        else
            return ""+Math2.roundTo(aFloat/1000.0d, -2)+" kHz";
    }

}

