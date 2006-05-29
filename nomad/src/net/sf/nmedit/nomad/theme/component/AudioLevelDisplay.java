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
 * Created on Jan 20, 2006
 */
package net.sf.nmedit.nomad.theme.component;

import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Point2D;

import net.sf.nmedit.nomad.theme.NomadClassicColors;

public class AudioLevelDisplay extends NomadComponent {

	public AudioLevelDisplay() {
		super();
		setSize(90,5);
		setDynamicOverlay(true);
	}

	private final static Point2D.Float gradientStart = new Point2D.Float(1,0);
	private Point2D.Float gradientStop = new Point2D.Float(0,0);
	
	public void paintDecoration(Graphics2D g2) {
		
		g2.setColor(NomadClassicColors.MODULE_BACKGROUND);
		g2.fillRect(0,0,getWidth()-1,getHeight()-1);
		
		gradientStop.x = getWidth()-1;
		GradientPaint gradient = new GradientPaint(gradientStart, NomadClassicColors.AUDIO_LEVEL_DISPLAY_LOW,
				gradientStop, NomadClassicColors.AUDIO_LEVEL_DISPLAY_HIGH);
		Paint p = g2.getPaint();
		g2.setPaint(gradient);
		g2.fillRect(1,1,getWidth()-2,getHeight()-2);
		g2.setPaint(p);

		g2.setColor(NomadClassicColors.MODULE_BACKGROUND.darker());
		g2.drawLine(1, 0, getWidth()-3, 0); // top
		g2.setColor(NomadClassicColors.MODULE_BACKGROUND.brighter());
		g2.drawLine(1, getHeight()-1, getWidth()-2, getHeight()-1); // bottom
		
		g2.setColor(NomadClassicColors.MODULE_BACKGROUND.darker());
		g2.drawLine(0, 1, 0, getHeight()-2); // left
		g2.setColor(NomadClassicColors.MODULE_BACKGROUND.brighter());
		g2.drawLine(getWidth()-1, 1, getWidth()-1, getHeight()-2); // right
		
	}
	
	public void paintDynamicOverlay(Graphics2D g2) {
		g2.setColor(NomadClassicColors.AUDIO_LEVEL_DISPLAY_LIGHT);
		int width = getWidth()-2;
		int scaledWidth = (int) Math.round(width * getScale());
		g2.fillRect(1,1,scaledWidth,getHeight()-2);
	}

	private double getScale() {
		return 0.25;
	}
	
}
