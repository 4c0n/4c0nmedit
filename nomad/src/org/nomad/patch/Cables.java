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
 * Created on Feb 2, 2006
 */
package org.nomad.patch;

import java.awt.Color;
import java.util.ArrayList;

import org.nomad.theme.NomadClassicColors;
import org.nomad.theme.curve.CCurve;
import org.nomad.util.array.TransitionChangeListener;
import org.nomad.util.array.TransitionMatrix;

public class Cables extends TransitionMatrix<Connector, CCurve> {

	private ArrayList<Module> modules = new ArrayList<Module>();
	
	public Cables() {
		addChangeListener(new Colorizer());
	}
	
	protected CCurve[] newArray(int size) {
		return new CCurve[size];
	}

	public void add(Module m) {
		if (!modules.contains(m)) {
			modules.add(m);
			for (Connector c : m.getConnectors()) addNodeU(c);
			nodesChanged();
		}
	}

	public void remove(Module m) {
		if (modules.remove(m)) {
			for (Connector c : m.getConnectors()) removeNode(c);
		}
	}
	
	public boolean canHaveTransition(Connector c1, Connector c2) {
		if (c1==c2) return false;
		if (hasTransition(c1, c2)) return false;

		return getOutput(c1)==null || getOutput(c2)==null; // at least one output has to be null
	}
	
	public void addTransition(Connector c1, Connector c2) {
		if (canHaveTransition(c1, c2)) {
			setTransition(c1, c2, new CCurve(c1, c2));
		}
	}
	
	public Connector getOutput(Connector c) {
		if (c.getInfo()==null) return null;
		if (c.getInfo().isOutput()) return c;
		for (Connector candidate : getLinked(c)) {
			if (candidate.getInfo().isOutput()) return candidate;
		}
		return null;
	}

	public void addTransition(CCurve curve) {
		if (canHaveTransition(curve.getC1(), curve.getC2())) {
			setTransition(curve.getC1(), curve.getC2(), curve);
		}
	}
	
	public void setCableColor(Connector connector, Color color) {
		for (CCurve curve : getTransitions(connector)) 
			curve.setColor(color);
	}
	
	private class Colorizer implements TransitionChangeListener<Connector, CCurve> {
		public void transitionChanged(TransitionMatrix<Connector, CCurve> matrix, Connector a, Connector b, CCurve told, CCurve tnew) {

			Color color = Color.WHITE;
			Connector out = getOutput(a);
			if (out!=null) {
				color = NomadClassicColors.getConnectorColor(out.getInfo());
				setCableColor(b, color);
			} else {
				out = getOutput(b);
				if (out!=null) {
					color = NomadClassicColors.getConnectorColor(out.getInfo());
					setCableColor(a, color);
				}
			}
			
			if (tnew!=null)
				tnew.setColor(color);

		}
	}

	public Color determineColor(Connector c1, Connector c2) 
	{
		if (c1!=null) c1 = getOutput(c1);
		if (c1!=null) return NomadClassicColors.getConnectorColor(c1.getInfo());
		if (c2!=null) c2 = getOutput(c2);
		if (c2!=null) return NomadClassicColors.getConnectorColor(c2.getInfo());
		return Color.WHITE;
	}

	public void remove(ArrayList<CCurve> transitions) {
		for (CCurve c : transitions) {
			setTransition(c.getC1(), c.getC2(), null);
		}
	}

}
