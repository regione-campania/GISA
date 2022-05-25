/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.documentale;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.hibernate.PersistenceFactory;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.Configuratore;
import it.us.web.util.properties.Application;

public class DownloadPdf extends GenericAction {

	
	public void can() throws AuthorizationException
	{
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("cc");
	}

	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}
	
	public void execute() throws Exception
	{
		
		//recupero l'id timbro
		String codDocumento 		=  null;
		codDocumento = req.getParameter("codDocumento");
		if (codDocumento==null)
			codDocumento = (String)req.getAttribute("codDocumento");
		String idDocumento 				= null;
		idDocumento = req.getParameter("idDocumento");
		
		String tipoDocumento 		=  null;
		tipoDocumento = req.getParameter("tipoDocumento");
		if (tipoDocumento==null)
		tipoDocumento = (String)req.getAttribute("tipoDocumento");
	
		
		String titolo="";
		String provenienza = "vam";
		
		String estensione = ".pdf";
		if (tipoDocumento!=null)
			estensione = "."+tipoDocumento;

	
		//String download_url = "http://" + InetAddress.getByName(Application.get("DOCUMENTALE_URL")).getHostAddress() + ":" + Application.get("DOCUMENTALE_PORT") + "/" + Application.get("DOCUMENTALE_APPLICATION_NAME") + "/DownloadService";
		//String download_url = "http://" + Configuratore.getSrvDOCUMENTALEL().getHostAddress() + ":" + Application.get("DOCUMENTALE_PORT") + "/" + Application.get("DOCUMENTALE_APPLICATION_NAME") + "/DownloadService";
		String download_url = "http://" + ((HashMap<String, InetAddress>)req.getServletContext().getAttribute("hosts")).get("srvDOCUMENTALEL").getHostAddress() + ":" + Application.get("DOCUMENTALE_PORT") + "/" + Application.get("DOCUMENTALE_APPLICATION_NAME") + "/DownloadService";
		
		
		if (codDocumento!=null && !codDocumento.equals("null")){
			download_url+="?codDocumento="+codDocumento;
			titolo=codDocumento+estensione;
		}
		else {
			
			download_url+="?idDocumento="+idDocumento+"&provenienza="+provenienza;
			titolo=provenienza+"_"+idDocumento+estensione;
		}
		System.out.println("[DOCUMENTALE VAM] Download da: "+download_url);
			if (req.getAttribute("titolo")!=null)
				titolo= (String)req.getAttribute("titolo");
			
		//Cartella temporanea sull'APP
        String path_doc = getWebInfPath(context,"tmp_documentale");
        //Creare il file ...(ispirarsi a GestoreGlifo servlet)
        
        File theDir = new File(path_doc);
     	theDir.mkdirs();

     	File inputFile = new File(path_doc+titolo);
     	if (!inputFile.exists())
     		inputFile.createNewFile();
     	URL copyurl;
     	InputStream outputFile=null;
    copyurl = new URL(download_url);
  
    outputFile = copyurl.openStream();
    FileOutputStream out2 = new FileOutputStream(inputFile);
    int c;
    while ((c = outputFile.read()) != -1)
        out2.write(c);
    outputFile.close();
    out2.close();
 
    String fileType = "";
     
  //  if (new File(fileName).exists()){
     // Find this file id in database to get file name, and file type

     // You must tell the browser the file type you are going to send
     // for example application/pdf, text/plain, text/html, image/jpg
     res.setContentType(fileType);

     // Make sure to show the download dialog
     res.setHeader("Content-disposition","attachment; filename="+titolo);

     // Assume file name is retrieved from database
     // For example D:\\file\\test.pdf

     File my_file = new File(inputFile.getAbsolutePath());

     // This should send the file to browser
     OutputStream out = res.getOutputStream();
     FileInputStream in = new FileInputStream(my_file);
     byte[] buffer = new byte[4096];
     int length;
     while ((length = in.read(buffer)) > 0){
        out.write(buffer, 0, length);
     }
     in.close();
     out.flush();

	}
	
	
	private String getWebInfPath(ServletContext context, String moduleFolderName) 
	{
        return (context.getRealPath("/") + "WEB-INF" + File.separator + moduleFolderName + File.separator);
	}

}

