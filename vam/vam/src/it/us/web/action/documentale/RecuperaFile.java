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
import java.util.List;

import javax.servlet.ServletContext;

import it.us.web.action.GenericAction;
import it.us.web.bean.BGuiView;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.hibernate.PersistenceFactory;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.properties.Application;

public class RecuperaFile extends GenericAction {

	
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
		Persistence persistenceDoc = PersistenceFactory.getPersistenceDocumentale();
		GenericAction.aggiornaConnessioneApertaSessione(req);
		
		String fileName 	= stringaFromRequest("nome");
        String titolo 		= stringaFromRequest("titolo");
        String pathDoc 		= stringaFromRequest("pathDoc");
        String downloadUrl 	= stringaFromRequest("downloadUrl");
        downloadUrl = downloadUrl.replaceAll(" ", "%20");
        
        File theDir = new File(pathDoc);
        theDir.mkdirs();

        File inputFile = new File(pathDoc+titolo);
        if(!inputFile.exists())
        	inputFile.createNewFile();
        URL copyurl;
        InputStream outputFile;
        copyurl = new URL(downloadUrl);
        outputFile = copyurl.openStream();
        FileOutputStream out2 = new FileOutputStream(inputFile);
        int c;
        while ((c = outputFile.read()) != -1)
        	out2.write(c);
        outputFile.close();
        out2.close();

        String fileType = "";

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
  

        PersistenceFactory.closePersistence( persistenceDoc, false );
        GenericAction.aggiornaConnessioneChiusaSessione(req);
		
	}
	
	
	
}

