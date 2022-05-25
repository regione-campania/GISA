/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.servlet;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.pdf.Barcode128;
 
/**
 * Servlet implementation class ServletComuni
 */
public class ServletBarcode extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	/** 
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletBarcode() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String stringa = request.getParameter("barcode");
		String barcode = "";
		
		try {barcode = createBarcodeImage(stringa);}
		catch (Exception e){
			
		}
		response.getWriter().println(barcode.toString());

	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	
	public static String createBarcodeImage(String code) {
		
		Barcode128 code128 = new Barcode128();
		code128.setCode(code);
		java.awt.Image im = code128.createAwtImage(Color.BLACK, Color.WHITE);
		int w = im.getWidth(null);
		int h = im.getHeight(null);
		BufferedImage img = new BufferedImage(w, h+12, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		g2d.drawImage(im, 0, 0, null);
		g2d.drawRect(0, h, w, 12);
		g2d.fillRect(0, h+1, w, 12);
		g2d.setColor(Color.WHITE);
		String s = code128.getCode();
		g2d.setColor(Color.BLACK);
		g2d.drawString(s,h+2,34);
		g2d.dispose();

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
		   ImageIO.write(img, "PNG", out);
		} catch (IOException e) {
		  e.printStackTrace();
		}
		byte[] bytes = out.toByteArray();
		
		String base64bytes = com.itextpdf.text.pdf.codec.Base64.encodeBytes(bytes);
		String src = "data:image/png;base64," + base64bytes;
		
		return src;

		};
		
}
