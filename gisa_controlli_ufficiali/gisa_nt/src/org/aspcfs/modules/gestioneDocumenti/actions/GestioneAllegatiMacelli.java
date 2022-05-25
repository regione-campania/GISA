/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneDocumenti.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.ParseException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.Charsets;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.json.JSONObject;

import com.darkhorseventures.framework.actions.ActionContext;

public class GestioneAllegatiMacelli extends CFSModule {
	
	private byte[] ba = null;
	private int org = -1;
	private String dataMacellazione = null;
	private int userId = -1;
	private String userIp="";
	private String tipoDocumento="";
	
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public byte[] getBa() {
		return ba;
	}

	public void setBa(byte[] ba) {
		this.ba = ba;
	}
	
	public String getDataMacellazione() {
		return dataMacellazione;
	}

	public void setDataMacellazione(String dataMacellazione) {
		this.dataMacellazione = dataMacellazione;
	}
	
	
	public String chiamaServerDocumentale(ActionContext context) throws SQLException, IOException{
		
		
		String baString="";
		baString = byteArrayToString();
		
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		userIp = context.getIpAddress();
		userId = user.getUserRecord().getId();

		
		HttpURLConnection conn =null;
		String url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_NO_CODIFICA");
		//STAMPE
		
		URL obj;
		try {
			obj = new URL(url);
			 conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
 
			StringBuffer requestParams = new StringBuffer();
			requestParams.append("baString");
			requestParams.append("=").append(URLEncoder.encode(baString, "ISO-8859-1"));
			requestParams.append("&");
			requestParams.append("provenienza");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
			requestParams.append("&");
			requestParams.append("tipoCertificato");
			requestParams.append("=").append(tipoDocumento);
			requestParams.append("&");
			requestParams.append("tipoTimbro");
			requestParams.append("=").append("Macelli");
			requestParams.append("&");
			requestParams.append("orgId");
			requestParams.append("=").append(org);
			requestParams.append("&");
			requestParams.append("dataMacellazione");
			requestParams.append("=").append(dataMacellazione);
			requestParams.append("&");
			requestParams.append("extra");
			requestParams.append("=").append(dataMacellazione);
			requestParams.append("&");
			requestParams.append("userId");
			requestParams.append("=").append(userId);
			requestParams.append("&");
			requestParams.append("userIp");
			requestParams.append("=").append(userIp);
			
			OutputStreamWriter wr = new OutputStreamWriter(conn
						.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			
			conn.getContentLength();
			
			
			String codDocumento="", titolo="";
			BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
			StringBuffer result = new StringBuffer();
	
			//Leggo l'output: l'header del documento generato e il nome assegnatogli
			if (in != null) {
				String ricevuto = in.readLine();
				result.append(ricevuto); }
				in.close();
			JSONObject jo = new JSONObject(result.toString());
			codDocumento = jo.get("codDocumento").toString();
			titolo = jo.get("titolo").toString();
			context.getRequest().setAttribute("codDocumento", codDocumento);
			context.getRequest().setAttribute("titolo", titolo);
						} 
		 catch (ConnectException e1){
	        	context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
				return "documentaleError";
	        }
		catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		finally{
			conn.disconnect();
		}
		return executeCommandDownloadPDF(context);
			
		
	}
public String executeCommandDownloadPDF(ActionContext context) throws SQLException, IOException {
		
		//recupero l'id timbro
			String codDocumento 		=  null;
				codDocumento = context.getRequest().getParameter("codDocumento");
			if (codDocumento==null)
				codDocumento = (String)context.getRequest().getAttribute("codDocumento");
			String idDocumento 				= null;
			idDocumento = context.getRequest().getParameter("idDocumento");
			
			String titolo="";
			String provenienza = ApplicationProperties.getProperty("APP_NAME_GISA");
		
			String download_url = "http://srvDOCUMENTALEL"+ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE")+"/"+ApplicationProperties.getProperty("APP_DOCUMENTALE_DOWNLOAD_SERVICE");
			
			if (codDocumento!=null && !codDocumento.equals("null")){
				download_url+="?codDocumento="+codDocumento;
				titolo=codDocumento+".pdf";
			}
			else {
				
				download_url+="?idDocumento="+idDocumento+"&provenienza="+provenienza;
				titolo=provenienza+"_"+idDocumento+".pdf";
			}
			
				if (context.getRequest().getAttribute("titolo")!=null)
					titolo= (String)context.getRequest().getAttribute("titolo");
				
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
        try {
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
         context.getResponse().setContentType(fileType);

         // Make sure to show the download dialog
         context.getResponse().setHeader("Content-disposition","attachment; filename="+titolo);

         // Assume file name is retrieved from database
         // For example D:\\file\\test.pdf

         File my_file = new File(inputFile.getAbsolutePath());

         // This should send the file to browser
         OutputStream out = context.getResponse().getOutputStream();
         FileInputStream in = new FileInputStream(my_file);
         byte[] buffer = new byte[4096];
         int length;
         while ((length = in.read(buffer)) > 0){
            out.write(buffer, 0, length);
         }
         in.close();
         out.flush();
  
         return ("-none-");	
}
         
         catch (ConnectException e1){
	        	context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
				return "documentaleError";
	        }
	        
		
		
		}

public int getOrg() {
	return org;
}

public void setOrg(int org) {
	this.org = org;
}

private String byteArrayToString () throws UnsupportedEncodingException{
	String s = new String(ba, "ISO-8859-1");
	return s;
	
	
}
}
