/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class MultiPartClientUtility {
	
	
	public static String inviaRichiestaPiuFile(String urlString, File[] filesToSend,String[] nomiParametriFile,HashMap<String,String> parametriAggiuntivi,HashMap<String,String> headerAggiuntivi)
	{
		String toRet = null;
	    HttpURLConnection conn = null;
	    DataOutputStream dos = null;
	    DataInputStream inStream = null;
	    FileInputStream fileInputStream = null;
	    int bytesRead, bytesAvailable, bufferSize;
	    byte[] buffer;
	    String debug = "";

	    //definisco un boundary
	    String lineEnd = "\r\n";
	    String twoHyphens = "--";
	    String boundaryInputsAtomici = "*****";

	    //Url da chiamare
//	    String urlString = "http://localhost/gisa_nt/rest/services/send";

	    try {
	
		    // Apro una connessione alla mia servlet
		    URL url = new URL(urlString);
		    // Apro una conessione HTTP
		    conn = (HttpURLConnection) url.openConnection();
		    // Imposto alcuni parametri per la connessione
		    conn.setDoInput(true);
		    conn.setDoOutput(true);
		    conn.setUseCaches(false);
		    conn.setRequestMethod("POST");
		    conn.setRequestProperty("Connection", "Keep-Alive");
		    //Il Content-Type della form
		    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundaryInputsAtomici);
	
		    for(String el : headerAggiuntivi.keySet())
		    {
		    	conn.setRequestProperty(el, headerAggiuntivi.get(el));
		    }
		    
		    //Apro lo streaming verso la servlet
		    dos = new DataOutputStream( conn.getOutputStream() );
	
		    for(String fieldName : parametriAggiuntivi.keySet())
		    {
			    dos.writeBytes(twoHyphens + boundaryInputsAtomici + lineEnd); debug += (twoHyphens + boundaryInputsAtomici + lineEnd);
			    dos.writeBytes("Content-Disposition: form-data; name=\""+fieldName+"\"" + lineEnd); debug += ("Content-Disposition: form-data; name=\""+fieldName+"\"" + lineEnd);
			    dos.writeBytes(lineEnd); debug+= lineEnd;
			    dos.writeBytes(parametriAggiuntivi.get(fieldName));  debug += parametriAggiuntivi.get(fieldName);
			    //Invio il boundary per delimitare la fine del file
			    dos.writeBytes(lineEnd); debug+= lineEnd;
		    }
		    
		    
		    
		    int maxBufferSize = 1024*1024;
		    /*SCRITTURA FILES-------------------------------------------------------------------*/
		    int i = 0;
		    for(File el : filesToSend)
		    {	
		    	 dos.writeBytes(twoHyphens + boundaryInputsAtomici + lineEnd) ; debug+= (twoHyphens + boundaryInputsAtomici + lineEnd);
		    	 dos.writeBytes("Content-Disposition: form-data; name=\""+nomiParametriFile[i]+"\";" + " filename=\"" + el.getName() +"\"" + lineEnd); debug += ("Content-Disposition: form-data; name=\""+nomiParametriFile[i]+"\";" + " filename=\"" + el.getName() +"\"" + lineEnd);
		    	 dos.writeBytes(lineEnd); debug += lineEnd;
		    	 
		    	 fileInputStream = new FileInputStream( el );
		    	 
		    	 bytesAvailable = fileInputStream.available();
				 bufferSize = Math.min(bytesAvailable, maxBufferSize);
				 buffer = new byte[bufferSize];
			
				 bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				 debug+="contenutofile...";
				 while (bytesRead > 0) {
			
					    dos.write(buffer, 0, bufferSize); //debug += new String(buffer,0,bufferSize);
					    bytesAvailable = fileInputStream.available();
					    bufferSize = Math.min(bytesAvailable, maxBufferSize);
					    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			
				 }
				 fileInputStream.close();
				 
				 dos.writeBytes(lineEnd); debug+= lineEnd;
				 //dos.writeBytes(twoHyphens + boundaryInputsAtomici + /*twoHyphens*/ lineEnd); debug += (twoHyphens + boundaryInputsAtomici + /*twoHyphens*/  lineEnd);
				 dos.flush();
		    	 
		    	 i++;
		    }
		    
		    dos.writeBytes(twoHyphens + boundaryInputsAtomici + /*twoHyphens*/ lineEnd); debug += (twoHyphens + boundaryInputsAtomici + /*twoHyphens*/  lineEnd);
		   
		    System.out.println("debug-------\n"+debug);
		    
		    
		    
		    StringBuilder sb = new StringBuilder();
	
		    //rimango in attesa risposta
	
			inStream = new DataInputStream(conn.getInputStream());
			String str;
			while ((str = inStream.readLine()) != null) {
				sb.append(str);
			}

			toRet = sb.toString();
	
		    

	    }
	    catch(Exception ex)
	    {
		   ex.printStackTrace();
		  
	    }
	    finally
	    {
	    	 try { fileInputStream.close(); } catch(Exception ex){}
	    	 try { dos.close(); } catch(Exception ex){}
	    	 try{ inStream.close(); } catch(Exception ex) {}
	    }
	
	    return toRet;
	}
	
	public static String inviaRichiestaSingoloFile(String urlString,File fileToSend,String nomeParametroFile,HashMap<String,String> parametriAggiuntivi)
	{
		String toRet = null;
	    HttpURLConnection conn = null;
	    DataOutputStream dos = null;
	    DataInputStream inStream = null;
	    FileInputStream fileInputStream = null;
//	    String exsistingFileName = "E:\\USB-key\\eclipse\\WorkspaceMars\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp3\\wtpwebapps\\DemoRestClient\\richiesta.zip";
	    int bytesRead, bytesAvailable, bufferSize;
	    byte[] buffer;

	    //definisco un boundary
	    String lineEnd = "\r\n";
	    String twoHyphens = "--";
	    String boundaryInputsAtomici = "*****";

	    //Url da chiamare
//	    String urlString = "http://localhost/gisa_nt/rest/services/send";

	    try {
	
		    // Apro una connessione alla mia servlet
		    URL url = new URL(urlString);
		    // Apro una conessione HTTP
		    conn = (HttpURLConnection) url.openConnection();
		    // Imposto alcuni parametri per la connessione
		    conn.setDoInput(true);
		    conn.setDoOutput(true);
		    conn.setUseCaches(false);
		    conn.setRequestMethod("POST");
		    conn.setRequestProperty("Connection", "Keep-Alive");
		    //Il Content-Type della form
		    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundaryInputsAtomici);
	
		    //Apro lo streaming verso la servlet
		    dos = new DataOutputStream( conn.getOutputStream() );
	
		    for(String fieldName : parametriAggiuntivi.keySet())
		    {
			    dos.writeBytes(twoHyphens + boundaryInputsAtomici + lineEnd); 
			    dos.writeBytes("Content-Disposition: form-data; name=\""+fieldName+"\"" + lineEnd); 
			    dos.writeBytes(lineEnd);
			    dos.writeBytes(parametriAggiuntivi.get(fieldName));  
			    //Invio il boundary per delimitare la fine del file
			    dos.writeBytes(lineEnd); 
		    }
		    
		    
		    
		    
		    /*SCRITTURA FILE-------------------------------------------------------------------*/
		    //Scrivo la prima riga
		    
		    dos.writeBytes(twoHyphens + boundaryInputsAtomici + lineEnd);
		    dos.writeBytes("Content-Disposition: form-data; name=\""+nomeParametroFile+"\";" + " filename=\"" + fileToSend.getName() +"\"" + lineEnd);
		    dos.writeBytes(lineEnd);
		   
		    
		    		
		    		
		    
		    int maxBufferSize = 1024*1024;
		    fileInputStream = new FileInputStream( fileToSend);
		    
		    bytesAvailable = fileInputStream.available();
		    bufferSize = Math.min(bytesAvailable, maxBufferSize);
		    buffer = new byte[bufferSize];
	
		    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
		    while (bytesRead > 0) {
	
			    dos.write(buffer, 0, bufferSize);
			    bytesAvailable = fileInputStream.available();
			    bufferSize = Math.min(bytesAvailable, maxBufferSize);
			    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
	
		    }
		    //Chiudo il file
		   
		    //Invio il boundary per delimitare la fine del file
		    dos.writeBytes(lineEnd);
		    dos.writeBytes(twoHyphens + boundaryInputsAtomici + twoHyphens + lineEnd);
		    
		    
		    //FINE SCRITTURA FILE-----------------------------------------------------------------------
		    
		    
		    
		    StringBuilder sb = new StringBuilder();
	
		    //rimango in attesa risposta
	
			inStream = new DataInputStream(conn.getInputStream());
			String str;
			while ((str = inStream.readLine()) != null) {
				sb.append(str);
			}

			toRet = sb.toString();
	
		    

	    }
	    catch(Exception ex)
	    {
		   ex.printStackTrace();
		  
	    }
	    finally
	    {
	    	 try { fileInputStream.close(); } catch(Exception ex){}
	    	 try { dos.close(); } catch(Exception ex){}
	    	 try{ inStream.close(); } catch(Exception ex) {}
	    }
	
	    return toRet;
	}
	
	
}
