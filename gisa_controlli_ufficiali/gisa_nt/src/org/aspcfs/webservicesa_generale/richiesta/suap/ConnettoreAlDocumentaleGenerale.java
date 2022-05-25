/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.webservicesa_generale.richiesta.suap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;



public class ConnettoreAlDocumentaleGenerale {
	
	URL urlServControllAllegati;
	URL urlServInvioAllegati;
	int timeout;
	Logger logger = Logger.getLogger(ThreadElaborazioneAsincronaRichiestaRestSAGenerale.class);
	
	public ConnettoreAlDocumentaleGenerale(URL urlControllo, URL urlInvio,int timeout)
	{
		this.urlServControllAllegati = urlControllo;
		this.urlServInvioAllegati = urlInvio;
		this.timeout = timeout;
	}
	
	public String[] controllaFirmaDigitaleFile(File fileToCheck) throws SQLException, IOException, ParseException
	{
		return chiamaServerDocumentaleControlloFile(fileToCheck);
	}
	

	
	public String[] chiamaServerDocumentaleControlloFile(File fileDaCaricare) throws SQLException, IOException, ParseException{
		
		String esitoReturn = "false";
		String oggettoReturn = "";

		byte[] buffer = new byte[(int) fileDaCaricare.length()];
		InputStream ios = null;
		try {
			ios = new FileInputStream(fileDaCaricare);
			if (ios.read(buffer) == -1) {
				throw new IOException("EOF reached while trying to read the whole file");
			}
		} finally {
			try { 
				if (ios != null)
					ios.close();
			} catch (IOException e) {
			}
		}

		
		
		
		String baString =new String(buffer, "ISO-8859-1");
		
		HttpURLConnection conn =null;
		
		//STAMPE
		System.out.println("\n[DOCUMENTALE GISA] Url generato(chiamata a servlet): "+urlServControllAllegati);
		URL obj;
		try {
			obj = urlServControllAllegati;
			 conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(this.timeout);
				
			StringBuffer requestParams = new StringBuffer();
			requestParams.append("baString");
			requestParams.append("=").append(URLEncoder.encode(baString, "ISO-8859-1"));
			requestParams.append("&");
			requestParams.append("provenienza");
			requestParams.append("=").append("CLIENT_CONTROLLO_FIRMA_DIGITALE");
			requestParams.append("&");
			requestParams.append("filename");
			requestParams.append("=").append(fileDaCaricare.getName());
			requestParams.append("&");
			requestParams.append("&checkP7M=si&");
			requestParams.append("oggetto");
			requestParams.append("=").append("CONTROLLO_FIRMA_DIGITALE");
			OutputStreamWriter wr = new OutputStreamWriter(conn
						.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();
			System.out.println("[DOCUMENTALE GISA] Conn: "+conn.toString());
			conn.getContentLength();
		
			BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
			StringBuffer result = new StringBuffer();

			//Leggo l'output: l'header del documento generato e il nome assegnatogli
			if (in != null) {
				String ricevuto = in.readLine();
				result.append(ricevuto); }
				in.close();
			JSONObject jo = new JSONObject(result.toString());
			esitoReturn = jo.get("esitoCheck").toString();
			oggettoReturn = jo.get("oggetto").toString();
			
			} 
		 catch (ConnectException e1){ e1.printStackTrace();}
		catch(SocketTimeoutException e){ e.printStackTrace();}
		catch (MalformedURLException e) { e.printStackTrace();} 
		catch (IOException e) { e.printStackTrace();} 
		
		finally{
			conn.disconnect();
		}
		
		String[] esito = {oggettoReturn, esitoReturn};
		return esito;
	}
	
	
	
	//ritorna true se per tutti gli allegati ha funzionato la chiamata al documentale
		public boolean inviaAllegati(HashMap<String,File> files,int idAssociato,int idUser,String appName) throws SQLException, IOException, ParseException
		{
			boolean res = true;
			for(Map.Entry<String, File> entry : files.entrySet())
			{
				res = res && inviaFileAlDocumentale(entry,idAssociato,idUser,appName);
				if(res == true)
				{
					logger.info("THREAD SERVIZ REST (CONNETTORE DOCUMENTALE) > *FILE* INVIATO CON SUCCESSO AL DOCUMENTALE (NOME REALE: ?) NOME PER DOCUMENTALE: ^".replace("?",entry.getValue().getName()).replace("^",entry.getKey()));
				}
			}
			
			return res;
		}
		
		
		public boolean inviaFileAlDocumentale(Map.Entry<String, File> entryToSend,int idAssociato,int idUser,String appName) throws SQLException, IOException, ParseException{
			
//			verbali.setOp("suap");
//			verbali.setAltId(altId);
//			verbali.setSubject( parts.getParameter("subject"+nomeFile.substring(4)));
//			verbali.setTipoAllegato("AllegatoScia");
//			verbali.setFileDaCaricare(file1);
//			verbali.setTipoAllegato("AllegatoScia");
//			verbali.setObbligoCheckFile(obbligoCheckFile);

			File fileDaCaricare = entryToSend.getValue();
			String nomeGruppoAllegatoPerFile = entryToSend.getKey();
			
			byte[] buffer = new byte[(int) fileDaCaricare.length()];
			InputStream ios = null;
			try {
				ios = new FileInputStream(fileDaCaricare);
				if (ios.read(buffer) == -1) {
					throw new IOException("EOF reached while trying to read the whole file");
				}
			} finally {
				try { 
					if (ios != null)
						ios.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			
			String baString =new String(buffer, "ISO-8859-1");
			HttpURLConnection conn =null;
			
			URL obj;
			try 
			{
				obj = urlServInvioAllegati;
				 conn = (HttpURLConnection) obj.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setConnectTimeout(this.timeout);
					
				StringBuffer requestParams = new StringBuffer();
				requestParams.append("baString");
				requestParams.append("=").append(URLEncoder.encode(baString, "ISO-8859-1"));
				requestParams.append("&");
				requestParams.append("provenienza");
				requestParams.append("=").append(appName);
				requestParams.append("&");
				requestParams.append("altId");
				requestParams.append("=").append(idAssociato+"");
				requestParams.append("&");
				requestParams.append("filename");
				requestParams.append("=").append(fileDaCaricare.getName());
				requestParams.append("&");
				requestParams.append("tipoCertificato");
				requestParams.append("=").append("allegatoServizioRest");
				requestParams.append("&");
				requestParams.append("fileDimension");
				requestParams.append("=").append(fileDaCaricare.length());
				requestParams.append("&");
				requestParams.append("oggetto");
				requestParams.append("=").append(nomeGruppoAllegatoPerFile);
				requestParams.append("&");
				requestParams.append("parentId=");
				requestParams.append("-1");
				requestParams.append("&");
				requestParams.append("folderId=");
				requestParams.append("-1");
				requestParams.append("&");
				requestParams.append("idUtente=");
				requestParams.append(idUser);
				
				OutputStreamWriter wr = new OutputStreamWriter(conn
							.getOutputStream());
				wr.write(requestParams.toString());
				wr.flush();
				System.out.println("[DOCUMENTALE GISA] Conn: "+conn.toString());
				conn.getContentLength();
			
				BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
				StringBuffer result = new StringBuffer();

				//Leggo l'output: l'header del documento generato e il nome assegnatogli
				if (in != null) 
				{
					String ricevuto = in.readLine();
					result.append(ricevuto); 
				}
				in.close();
				
				JSONObject jo = new JSONObject(result.toString());
				String codDocumento = jo.get("codDocumento").toString();
				
				if (codDocumento==null || codDocumento.equals("null") || codDocumento.equals(""))
					return false;
				
				
			} 
			 
			catch (ConnectException e1){ e1.printStackTrace();}
			catch(SocketTimeoutException e){ e.printStackTrace();}
			catch (MalformedURLException e) { e.printStackTrace();} 
			catch (IOException e) { e.printStackTrace();} 
			
			finally{
				conn.disconnect();
			}
			
			return true;
		
		}
	
	
}
