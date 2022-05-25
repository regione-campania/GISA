/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils.images;

import java.awt.Graphics2D;
import java.awt.image.*;

import org.aspcfs.utils.ApplicationProperties;

public class GestioneImmagini {
	
	
	
	
	  public static BufferedImage resizeImage(BufferedImage originalImage, int type){
			BufferedImage resizedImage = new BufferedImage(Integer.parseInt(ApplicationProperties.getProperty("IMG_WIDTH")), 
					Integer.parseInt(ApplicationProperties.getProperty("IMG_HEIGHT")), type);
			Graphics2D g = resizedImage.createGraphics();
			g.drawImage(originalImage, 0, 0, Integer.parseInt(ApplicationProperties.getProperty("IMG_WIDTH")), 
					Integer.parseInt(ApplicationProperties.getProperty("IMG_HEIGHT")), null);
			g.dispose();
		 
			return resizedImage;
		    }

}
