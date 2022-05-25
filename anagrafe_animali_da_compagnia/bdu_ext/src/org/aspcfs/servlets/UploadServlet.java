/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.servlets;

//Import required java libraries
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.utils.ApplicationProperties;
import org.imgscalr.Scalr;

import com.darkhorseventures.database.ConnectionPool;

public class UploadServlet extends HttpServlet {

	private boolean isMultipart;
	private String filePath;
	private int maxFileSize = 50 * 1024;
	private int maxMemSize = 4 * 1024;
	private File file;

	public void init() {
		// Get the file location where it would be stored.
		// filePath =
		// getServletContext().getContextPath();

		ConnectionPool ce = (ConnectionPool) getServletContext().getAttribute("ConnectionPool");
		if (ce != null)
			ce.getDbName();

		ApplicationPrefs prefs = (ApplicationPrefs) getServletContext().getAttribute("applicationPrefs");
		filePath = (prefs.get("FILELIBRARY") + (ce.getDbName() == null ? "" : ce.getDbName()) + File.separator + "immagini_animali" + File.separator);
		// (moduleFolderName == null ? "" : moduleFolderName + fs));
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			java.io.IOException {
		// Check that we have a file upload request
		isMultipart = ServletFileUpload.isMultipartContent(request);
		response.setContentType("text/html");
		java.io.PrintWriter out = response.getWriter();
		if (!isMultipart) {
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet upload</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<p>No file uploaded</p>");
			out.println("</body>");
			out.println("</html>");
			return;
		}

		String microchip = "";
		String estensione = "";
		Path dest = null;
		BufferedImage newImage;

		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		factory.setSizeThreshold(maxMemSize);
		// Location to save data that is larger than maxMemSize.
		factory.setRepository(new File(filePath));

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum file size to be uploaded.
		upload.setSizeMax(maxFileSize);
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Caricamento file</title>");
		out.println("</head>");
		out.println("<body>");

		try {
			// Parse the request to get file items.
			List fileItems = upload.parseRequest(request);

			// Process the uploaded file items
			Iterator i = fileItems.iterator();


			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
				if (!fi.isFormField()) {
					// Get the uploaded file parameters
					String fieldName = fi.getFieldName();
					String fileName = fi.getName();
					String contentType = fi.getContentType();
					boolean isInMemory = fi.isInMemory();
					long sizeInBytes = fi.getSize();

					// try (InputStream input = fi.getInputStream()) {

					//
					estensione = fileName.substring(fileName.lastIndexOf(".") + 1);
					try {
						BufferedImage buff = ImageIO.read(fi.getInputStream());
						// It's an image (only BMP, GIF, JPG and PNG are
						// recognized).
						if (buff == null || !("jpg").equals(estensione)  ) {

							out.println("<html>");
							out.println("<head>");
							out.println("<title>Caricamento foto</title>");
							out.println("</head>");
							out.println("<body>");
							out.println("<p>File non caricato, scegliere un file immagine</p>");
							out.println("</body>");
							out.println("</html>");
							return;

						}
						
						// newImage = GestioneImmagini.resizeImage(buff, BufferedImage.TYPE_INT_RGB); //Immagine quadrata
						newImage = Scalr.resize(buff, Integer.valueOf(ApplicationProperties.getProperty("MAX_WIDTH_HEIGHT")));
					} catch (Exception e) {
						out.println("<html>");
						out.println("<head>");
						out.println("<title>Caricamento foto</title>");
						out.println("</head>");
						out.println("<body>");
						out.println("<p>File non caricato, scegliere un file immagine</p>");
						out.println("</body>");
						out.println("</html>");
						return;
					}
					// }

					// Write the file

					
					if (fileName.lastIndexOf("\\") >= 0) {
						file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")));
					} else {
						file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\") + 1));
					}
					
					ImageIO.write(newImage, "jpg", file);
					
				} else {
					if (fi.getFieldName() != null && !("").equals(fi.getFieldName())
							&& ("microchip").equals(fi.getFieldName())) {
						microchip = fi.getString();
						// File file2 = new File(filePath + microchip + "." +
						// estensione);
						// file.renameTo(file2);
						// Check if file exists, if yes delete it

						File f = new File(filePath + microchip + "." + estensione);

						if (f.exists()) {
							f.delete();
						}

						Path source = file.toPath();
						dest = Files.move(source, source.resolveSibling(microchip + "." + estensione));
					}
				}
			}

			// UPLOAD
			HttpURLConnection httpUrlConnection = (HttpURLConnection) new URL(
					"http://www.anagrafecaninacampania.it/public_area/upload.php?filename=" + microchip + "."
							+ estensione).openConnection();
			httpUrlConnection.setDoOutput(true);
			httpUrlConnection.setRequestMethod("POST");
			OutputStream os = httpUrlConnection.getOutputStream();
			Thread.sleep(1000);
			BufferedInputStream fis = new BufferedInputStream(new FileInputStream(dest.toString()));

			while (fis.available() > 0) {
				os.write(fis.read());

			}

			os.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));

			String s = null;
			while ((s = in.readLine()) != null) {
				System.out.println(s);
			}
			in.close();
			fis.close();
			out.println("File correttamente caricato per il microchip: " + microchip + "<br>");
			out.println("</body>");
			out.println("</html>");
		}catch(org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException limit){
			out.println("File di dimensione superiore alla dimensione consentita di " +maxFileSize + " KB");
		}
//			catch (Exception ex) {
//		
//			System.out.println(ex);
//		}
 catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileUploadException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			java.io.IOException {

		throw new ServletException("GET method used with " + getClass().getName() + ": POST method required.");
	}
}