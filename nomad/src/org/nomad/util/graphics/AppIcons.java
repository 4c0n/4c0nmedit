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
 * Created on Jan 18, 2006
 */
package org.nomad.util.graphics;

import java.awt.Image;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public final class AppIcons extends ImageTracker {

	private final static AppIcons instance = new AppIcons();
	public final static ImageIcon IC_DOCUMENT_NEW = getImageIcon("new");
	public final static ImageIcon IC_DOCUMENT_SAVE = getImageIcon("save");
	public final static ImageIcon IC_DOCUMENT_SAVE_AS = getImageIcon("save_as");
	public final static ImageIcon IC_DOCUMENT_CLOSE = getImageIcon("close");
	public final static ImageIcon IC_APP_HELP = getImageIcon("help-agent");
	public final static ImageIcon IC_APP_ABOUT = getImageIcon("draw-callouts");
	public final static ImageIcon IC_PATCH_CHOOSE_THEMES = getImageIcon("choose-themes");
	
	public static Image getIcon(String key) {
		return instance.getImage(key);
	}
	
	public static ImageIcon getImageIcon(String key) {
		return new ImageIcon(getIcon(key));
	}

	public AppIcons() {
		try {
			loadFromDirectory("data/images/icons/app");
			formatKeys();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/*
	 * removes prefixes:
	 *   'stock_'
	 *
	 * suffixes:
	 *   '_\d+'   
	 */
	protected void formatKeys() {
		ArrayList<String> keys = new ArrayList<String>(images.keySet());
		for (String oldkey:keys) {
			String key = oldkey;
			if (key.startsWith("stock_"))
				key = key.replaceFirst("stock_","");
			if (key.matches(".*-\\d+"))
				key = key.substring(0, key.lastIndexOf("-"));
			else if (key.matches(".*_\\d+"))
				key = key.substring(0, key.lastIndexOf("_"));
			images.put(key, images.remove(oldkey));
		}
	}
	
}