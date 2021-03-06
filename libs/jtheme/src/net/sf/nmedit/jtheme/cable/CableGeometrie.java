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
package net.sf.nmedit.jtheme.cable;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

public interface CableGeometrie
{

    Rectangle getBounds();
    Rectangle getBounds(Rectangle r);
    boolean intersects(int x, int y, int width, int height);
    boolean intersects(Rectangle r);

    Shape getShape();

    void setEndPoints(int x1, int y1, int x2, int y2);
    void setEndPoints(Point p1, Point p2);

    Point getStart();
    Point getStop();
    
    void setShake(double shake);
    double getShake();
    void shake();
    
}

