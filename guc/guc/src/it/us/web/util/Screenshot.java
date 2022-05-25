/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;


public class Screenshot {

	public Screenshot() {
		
	}
	
	public BufferedImage takeBufferedScreenshot () throws Exception {
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		Robot robot = new Robot();
		
		Rectangle rectangle = new Rectangle(0, 0, screenSize.width-15, screenSize.height);
		BufferedImage image = robot.createScreenCapture(rectangle);
		
		return image;
	}
	
	public void takeScreenshot () throws Exception {
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		Robot robot = new Robot();
		
		Rectangle rectangle = new Rectangle(0, 0, screenSize.width-15, screenSize.height);
		BufferedImage image = robot.createScreenCapture(rectangle);
		
		ImageIO.write(image, "gif", new File("c:\\mmgg.jpg")); 
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Screenshot s = new Screenshot();
		s.takeScreenshot();
	}

}
