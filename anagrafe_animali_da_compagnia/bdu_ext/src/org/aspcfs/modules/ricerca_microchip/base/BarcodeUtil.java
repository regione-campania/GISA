/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.ricerca_microchip.base;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.lowagie.text.pdf.Barcode128;



public class BarcodeUtil {
	
	/**
	 * Genera l'immagine del codice a barre<br/>
	 * @param code il codice da generare
	 * @return l'immagine in formato BufferedImage
	 */
	public static BufferedImage generateImage(String code){
		
		Barcode128 code128 = new Barcode128();
		code128.setCode(code);
		code128.setBaseline(16);
		code128.setSize(16);
		code128.setX(1.6f);
		code128.setInkSpreading(0.8f);
		
		java.awt.Image im = code128.createAwtImage(Color.BLACK, Color.WHITE);
		int w = im.getWidth(null);
		int h = im.getHeight(null);
		float scaleFactor = 1.5f;
		int w_scale = Math.round((w+10)*scaleFactor);
		int h_scale = Math.round((h+16)*scaleFactor);
		
		BufferedImage img = new BufferedImage(w_scale, h_scale, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();

		g2d.scale(scaleFactor, scaleFactor);
		g2d.drawRect(0, 0, w_scale, h_scale);
		g2d.fillRect(0, 0, w_scale, h_scale);
		g2d.drawImage(im, 5, 0, null);
		g2d.setColor(Color.WHITE);
		
		String s = code128.getCode();
		g2d.setColor(Color.BLACK);
		g2d.drawString(s,h-h/2,36);
		//g2d.scale(2.0, 2.0);
		g2d.dispose();
		
		return img;
	}
	
}
