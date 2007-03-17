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
package net.sf.nmedit.jtheme.clavia.nordmodular;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.nmedit.jtheme.JTContext;
import net.sf.nmedit.jtheme.clavia.nordmodular.graphics.EqualizerMid;
import net.sf.nmedit.jtheme.component.JTControlAdapter;
import net.sf.nmedit.jtheme.component.JTDisplay;

public class JTEqMidDisplay extends JTDisplay implements ChangeListener
{
    
    private EqualizerMid equalizerMid;
    
    private JTControlAdapter freqAdapter;
    private JTControlAdapter gainAdapter;
    private JTControlAdapter bwAdapter;

    public JTEqMidDisplay(JTContext context)
    {
        super(context);
        
        equalizerMid = new EqualizerMid();
    }

    protected void paintDynamicLayer(Graphics2D g)
    {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(JTNM1Context.GRAPH_DISPLAY_LINE);
        int y = getHeight()/2;
        g.drawLine(0, y, getWidth(), y);
        
        g.setColor(getForeground());
        equalizerMid.setBounds(0, 0, getWidth(), getHeight());
        g.draw(equalizerMid);
    }
    
    public float getFreq()
    {
        return equalizerMid.getFreq();
    }
    
    public void setFreq(float value)
    {
        if (getFreq() != value)
        {
            equalizerMid.setFreq(value);
            repaint();
        }
    }
    
    public float getGain()
    {
        return equalizerMid.getGain();
    }
    
    public void setGain(float value)
    {
        if (getGain() != value)
        {
            equalizerMid.setGain(value);
            repaint();
        }
    }
    
    public float getBandWidth()
    {
        return equalizerMid.getBW();
    }
    
    public void setBandWidth(float value)
    {
        if (getGain() != value)
        {
            equalizerMid.setBW(value);
            repaint();
        }
    }

    public JTControlAdapter getFreqAdapter()
    {
        return freqAdapter;
    }

    public JTControlAdapter getGainAdapter()
    {
        return gainAdapter;
    }

    public JTControlAdapter getBWAdapter()
    {
        return bwAdapter;
    }

    public void setFreqAdapter(JTControlAdapter adapter)
    {
        JTControlAdapter oldAdapter = this.freqAdapter;
        
        if (oldAdapter != adapter)
        {
            if (oldAdapter != null)
                oldAdapter.setChangeListener(null);
            this.freqAdapter = adapter;
            if (adapter != null)
                adapter.setChangeListener(this);
            
            updateFreq();
        }
    }

    public void setGainAdapter(JTControlAdapter adapter)
    {
        JTControlAdapter oldAdapter = this.gainAdapter;
        
        if (oldAdapter != adapter)
        {
            if (oldAdapter != null)
                oldAdapter.setChangeListener(null);
            this.gainAdapter = adapter;
            if (adapter != null)
                adapter.setChangeListener(this);
            
            updateGain();
        }
    }

    public void setBWAdapter(JTControlAdapter adapter)
    {
        JTControlAdapter oldAdapter = this.bwAdapter;
        
        if (oldAdapter != adapter)
        {
            if (oldAdapter != null)
                oldAdapter.setChangeListener(null);
            this.bwAdapter = adapter;
            if (adapter != null)
                adapter.setChangeListener(this);
            
            updateBW();
        }
    }

    protected void updateFreq()
    {
        if (freqAdapter != null)
            setFreq((float)freqAdapter.getNormalizedValue());
    }

    protected void updateGain()
    {
        if (gainAdapter != null)
            setGain((float)gainAdapter.getNormalizedValue());
    }

    protected void updateBW()
    {
        if (bwAdapter != null)
            setBandWidth((float)bwAdapter.getNormalizedValue());
    }

    public void stateChanged(ChangeEvent e)
    {
        if (e.getSource() == freqAdapter)
        {
            updateFreq();
            return;
        }
        if (e.getSource() == gainAdapter)
        {
            updateGain();
            return;
        }
        if (e.getSource() == bwAdapter)
        {
            updateBW();
            return;
        }
    }
}