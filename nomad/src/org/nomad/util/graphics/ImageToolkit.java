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
 * Created on Jan 1, 2006
 */
package org.nomad.util.graphics;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;

/**
 * A class providing some helper methods related to images.
 * 
 * @author Christian Schneider
 */
public class ImageToolkit {

	/**
	 * Creates a image with the same size as the component using the components graphics configuration.
	 * @see #createCompatibleBuffer(int, int, int, GraphicsConfiguration)
	 */
	public static BufferedImage createCompatibleBuffer(Component c, int transparency) {
		return createCompatibleBuffer(c.getSize(), transparency, c);
	}
	
	/**
	 * Creates a image using the screen graphics configuration.
	 * @see #createCompatibleBuffer(int, int, int, GraphicsConfiguration)
	 */
	public static BufferedImage createCompatibleBuffer(Dimension size, int transparency) {
		return createCompatibleBuffer(size.width, size.height, transparency);
	}
	
	/**
	 * Creates a image using the screen graphics configuration.
	 * @see #createCompatibleBuffer(int, int, int, GraphicsConfiguration)
	 */
	public static BufferedImage createCompatibleBuffer(int width, int height, int transparency) {
	    return createCompatibleBuffer(width, height, transparency, 
	    		GraphicsEnvironment
					.getLocalGraphicsEnvironment()
					.getDefaultScreenDevice()
					.getDefaultConfiguration());
	}

	/**
	 * Creates a compatible image using the components graphics configuration.
	 * @see #createCompatibleBuffer(int, int, int, GraphicsConfiguration)
	 */
	public static BufferedImage createCompatibleBuffer(Dimension size, int transparency, Component c) {
		return createCompatibleBuffer(size.width, size.height, transparency, c);
	}
	
	/**
	 * Creates a compatible image using the components graphics configuration.
	 * @see #createCompatibleBuffer(int, int, int, GraphicsConfiguration)
	 */
	public static BufferedImage createCompatibleBuffer(int width, int height, int transparency, Component c) {
		
		if (c==null || c.getGraphicsConfiguration()==null) {
			return createCompatibleBuffer(width, height, transparency);
		}
		
		return createCompatibleBuffer(width, height, transparency, c.getGraphicsConfiguration());
	}

	/**
	 * @see #createCompatibleBuffer(int, int, int, GraphicsConfiguration)
	 */
	public static BufferedImage createCompatibleBuffer(Dimension size, int transparency, GraphicsConfiguration gc) {
		return createCompatibleBuffer(size.width, size.height, transparency, gc);
	}
	
	/**
	 * Creates a compatible image.
	 * @param width with of image
	 * @param height height of image
	 * @param transparency transparency of image
	 * @param gc graphics configuration
	 * @return a image compatible to the graphics configuration
	 */
	public static BufferedImage createCompatibleBuffer(int width, int height, int transparency, GraphicsConfiguration gc) {
		return gc.createCompatibleImage(width, height, transparency);
	}

	/**
	 * Returns true if the image has transparent pixels or the value
	 * of alternativeResult if the transparency could not be determined.
	 * 
	 * @param image The image to check for transparent pixels
	 * @return true if the image has transparent pixels.
	 */
    public static boolean hasAlpha(Image image, boolean alternativeResult) {
        if (image instanceof BufferedImage) {
            BufferedImage bimage = (BufferedImage)image;
            return bimage.getColorModel().hasAlpha();
        }

        PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
           pg.grabPixels();
        } catch (InterruptedException e) {
        	// ups
        }

     	if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
     		// return alternativeResult
    	    return alternativeResult;
    	}

        // Get the image's color model
        ColorModel cm = pg.getColorModel();
        return cm.hasAlpha();
    }

    /**
	 * Returns true if the image has transparent pixels or the value
	 * of true if the transparency could not be determined.
	 * 
	 * @param image The image to check for transparent pixels
	 * @return true if the image has transparent pixels.
     */
    public static boolean hasAlpha(Image image) {
    	return hasAlpha(image, true);
    }

    public static void paintRegion(Graphics g, Image image, Rectangle srcReg, int dx, int dy) {
		Rectangle clip = g.getClipBounds(); // use clip bounds if necessary
		if (clip==null) {
			if (srcReg!=null) {
				g.drawImage(image, dx, dy, dx+srcReg.width, dy+srcReg.height,
					srcReg.x, srcReg.y,srcReg.x+srcReg.width,srcReg.y+ srcReg.height, null);
			} else {
				g.drawImage(image, dx, dy, null);
			}
		} else { // clip!=null
			if (srcReg!=null) {	
				Rectangle target = new Rectangle(0, 0, srcReg.width, srcReg.height); // move region to 0,0
				Rectangle ri = clip.intersection(target); // intersect region and component
				if (ri.isEmpty()) {
					// no painting necessary
				} else {
					
					int ix = srcReg.x+ri.x;
					int iy = srcReg.y+ri.y;
					
					g.drawImage(image,
						dx+ri.x, dy+ri.y, dx+ri.x+ri.width, dy+ri.y+ri.height,
						ix, iy, ix+ri.width, iy+ri.height, null);
				}
			} else {
				// intersect clip with image bounds
				Rectangle ri = clip.intersection(new Rectangle(0, 0, image.getWidth(null), image.getHeight(null)));
				if (ri.isEmpty()){
					// no painting necessary
				} else {
					// paint intersection of clip and image
					int r = ri.x+ri.width;
					int b = ri.y+ri.height;
					g.drawImage(image,
							dx+ri.x, dy+ri.y, dx+r, dy+b,
							ri.x, ri.y, r, b, null);
				}
			}
		}
	}

}