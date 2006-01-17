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
 * Created on Jan 6, 2006
 */
package org.nomad.theme.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;

import org.nomad.theme.component.model.NomadClassicKnobBehaviour;
import org.nomad.theme.component.model.NomadClassicKnobBehaviour.Metrics;


/**
 * @author Christian Schneider
 */
public class NomadClassicKnob extends NomadControl {

	//private final Color clDefaultKnobFill = Color.decode("#9B9B9B");
	//private final Color clDefaultKnobOutline = Color.BLACK;

	private final Color clFillDark = Color.decode("#969696");
	private final Color clFillLight = Color.decode("#9D9D9D");
	
	private final Color clHighlight = new Color(245, 245, 220, 180);

	private NomadClassicKnobBehaviour behaviour = null;
	
	public NomadClassicKnob() {
		behaviour = new NomadClassicKnobBehaviour(this);
		setOpaque(false);
		setFocusable(true);
		setDynamicOverlay(true);
		setPreferredSize(new Dimension(24,24));
		setSize(24,24);
		setMinimumSize(new Dimension(24,24));
		setMaximumSize(new Dimension(24,24));
		deleteOnScreenBuffer();
	}
	
	boolean morphEnabled = true;

	protected void configureGraphics(Graphics2D g2) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
	}
	
	public void paintDecoration(Graphics2D g2) {
		configureGraphics(g2);

		behaviour.metrics.update();
		Metrics m = behaviour.metrics;
		
		g2.setColor(Color.BLACK);
		// mid - left bottom corner
		g2.drawLine(Math.round((float)m.cx), Math.round((float)m.cy), m.lBar.x, m.lBar.y);
		// mid - right bottom corner
		g2.drawLine(Math.round((float)m.cx), Math.round((float)m.cy), m.rBar.x, m.rBar.y);

		// fill
		g2.setPaint(new GradientPaint(
			m.pointTL1, clFillLight,
			m.pointBR1, clFillDark, true
			//m.pointTL1, Color.LIGHT_GRAY, --- looks better
			//m.pointBR1, Color.GRAY, true
		));
		g2.fill(m.ellipse);

		// inner outline
		g2.setPaint(new GradientPaint(
			m.pointTL1, Color.WHITE,
			m.pointBR1, Color.BLACK, true
		));
		g2.setStroke(new BasicStroke(1.5f));
		g2.draw(m.ellipseSmall);

		// outer outline
		g2.setPaint(new GradientPaint(
			m.pointTL1, Color.LIGHT_GRAY,
			m.pointBR1, Color.BLACK, true
		));
		g2.setStroke(new BasicStroke(1.0f));
		g2.draw(m.ellipse);
			
	}
	
	public void paintDynamicOverlay(Graphics2D g2) {
		configureGraphics(g2);
		Metrics m = behaviour.metrics;

		if (hasFocus()) {
			// we highlight the button
			g2.setColor(clHighlight);
			// outer outline
			g2.setPaint(new GradientPaint(
					m.pointTL1, clFillLight,
					m.pointBR1, clHighlight, true
				));
				g2.fill(m.ellipseSmall);
			
		}
		
		Arc2D morph = m.getMorphArc();
		if (morph!=null) {
			// draw morph color overlay
			g2.setPaint(getMorphBackground());
			g2.fill(m.ellipse);

			// draw morph arc overlay
			g2.setPaint(getMorphForeground());
			g2.fill(morph);	
		}

		// draw marker
		Point p = m.calcPosition(getValuePercentage());
		g2.setColor(Color.BLACK);
		g2.drawLine(Math.round((float)m.cx), Math.round((float)m.cy), p.x, p.y);
	}
	
}