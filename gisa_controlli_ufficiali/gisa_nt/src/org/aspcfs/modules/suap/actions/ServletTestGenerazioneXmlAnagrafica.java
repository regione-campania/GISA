/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.opu.base.StabilimentoList;

import com.darkhorseventures.database.ConnectionPool;

public class ServletTestGenerazioneXmlAnagrafica extends HttpServlet{

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Connection db = null;
		OutputStream os = null;
		FileInputStream fis = null;
		StabilimentoList stabsToGet = new StabilimentoList();
		ArrayList<File> toZip = new ArrayList<File>();
		String stringaNumRegStabs = null;
		XSDSchemaRestGisaGeneratore gen = new XSDSchemaRestGisaGeneratore();
		/*
		 * CHIEDE PER TUTTI GLI ID STAB IL DOCUMENTO XML GENERATO SECONDO ANAGRAFICA, LI METTE TUTTI IN UNICO ZIP
		 * E CREA ANCHE UN UNICO XML RIEPILOGATIVO - NB SE E' SETTATO IL PARAMETRO APPOSITO, SONO USATI TUTTI GLI STAB ID TROVATI IN OPU_OPERATORI_DENORMALIZZATI_VIEW
		 * 
		 */
		try
		{	
			File tempFold = new File(getServletContext().getRealPath("/")+"/WEB-INF/tmpFoldTest");
			db =  ((ConnectionPool)getServletContext().getAttribute("ConnectionPool")).dataSource.getConnection();
			//creo file zip unico
			String tokenPerUnlockModal = req.getParameter("tokenPerUnlockModal");
			
			
			if(req.getParameter("allStabs") != null && req.getParameter("allStabs").equals("true"))
			{//li prendo tutti gli stab
				stabsToGet.buildList(db); 
				
			}
			else
			{ //gli stab id sono passati da client come parametro  concatenati con - 
				stringaNumRegStabs = req.getParameter("numRegStab");
				String[] t2 = stringaNumRegStabs.split("-");
				for(String numReg : t2)
				{	
					if(numReg.equals("null"))
						continue;
					
					
					Stabilimento stab = new Stabilimento();
					
					stab.queryRecordStabilimento(db, numReg);
					stabsToGet.add(stab);
				}
			}
			
			
			
			 
			
			TreeSet<String> usati = new TreeSet<String>();
			for(int i = 0; i< stabsToGet.size(); i++) 
			{
				try
				{
					Stabilimento stabDiCuiAnag = (Stabilimento) stabsToGet.get(i);
					int stabId = stabDiCuiAnag.getIdStabilimento();
					String numReg = stabDiCuiAnag.getNumero_registrazione();
					
					//dal punto di vista della generazione, USIAMO GLI ID STAB NON I NUMERI REGISTRAZIONE RICEVUTI
					/*per il bug che causa di vedere piu' entry nella tabella di ricerca
					 * per lo stesso operatore, faccio un controllo per evitare di aggiungere io piu' entry 
					 ------------------------------------*/
					if(usati.contains(stabId+","+numReg))
						continue;
					usati.add(stabId+","+numReg);
					/*--------------------------------------*/
					
					
					if(tempFold.exists() == false)
					{
						tempFold.mkdir();
					}
					
					
					
					File xmlGenerato = gen.generaFileXmlSecondoSchema(db, tempFold.getAbsolutePath(), new int[]{stabId,-1}, "anagrafica_stab_?.xml".replace("?",
							numReg ), new File[]{}, "ANAGRAFICA", new Boolean[]{});
					
					
					toZip.add(xmlGenerato);
					
					
					 
					
					
				}
				catch(Exception x){
					x.printStackTrace();
					continue;}
				
				
			}
			
			//genero file globale di tutte le anagrafiche assieme
			File anagGenFile = gen.rappresentaTuttiXmlInUnicaAnagrafica(toZip.toArray(new File[toZip.size()]),tempFold);
			//e lo appendo ai file da izppare
			toZip.add(anagGenFile);
			
			//zippo files (tutti)
			File zipped = zippaFiles(toZip.toArray(new File[toZip.size()]), "/archivio_insiemeanag.zip", tempFold.getAbsolutePath());
			
			
			Cookie cookiePerUnlockModale = new Cookie("tokenPerUnlockModal",tokenPerUnlockModal);
			resp.addCookie(cookiePerUnlockModale);
			
			resp.setContentType("application/zip");
			resp.addHeader("Content-Disposition", "attachment; filename=\""+zipped.getName()+"\"");
			resp.setContentLength((int) zipped.length());
				
			fis = new FileInputStream(zipped);
			os = resp.getOutputStream();
			
			byte[] buff = new byte[1024];
			int r = -1;
			while((r = fis.read(buff))>0)
			{
				os.write(buff,0,r);
			}
			
			os.flush();
			System.out.println("TERMINATO");
		}
		catch(Exception ex){ex.printStackTrace();}
		finally
		{
			try{db.close();} catch(Exception ex){}
			try{os.close();} catch(Exception ex){}
			try{fis.close();} catch(Exception ex){}
			for(File el : toZip)
			{
				
				try
				{
					boolean deleted = el.delete();
					System.out.println(deleted);
				}catch(Exception ex){}
			}
		}
	}
		
		
		
	




public File zippaFiles(  File[] filesToZip, String nomeZip, String tmpFolderPath ) throws Exception 
{
			File fileZip = new File(tmpFolderPath + nomeZip);
			InputStream iS = null;
			//HashMap<String,Boolean> hash0 = new HashMap<String,Boolean>(); /*la uso poiche' c'e' un bug nella vista che mostra i risultati in tabella, dove per alcuni operatori sono mostrate piu' entry anche se e' lo stesso, questo farebbe
			//generare piu' zipentries con lo stesso nome poiche' sono in realta lo stesso operatore */
			ZipOutputStream zOs = new ZipOutputStream(new FileOutputStream(fileZip));
			try {
				for (File fileToAdd : filesToZip) {
					String filename = fileToAdd.getName();
					
					/*if(hash0.containsKey(filename))
					{
						continue;
					}*/
					 
					//hash0.put(filename, true);
					ZipEntry zipEntry = new ZipEntry(filename);
					try
					{
						zOs.putNextEntry(zipEntry);
					}
					catch(java.util.zip.ZipException ex)
					{
						 
						continue;
					}
					
					iS = new FileInputStream(fileToAdd);
					int t = -1;
					while ((t = iS.read()) != -1) {
						zOs.write(t);
					}
					zOs.closeEntry();
					
					try{
						iS.close();
					}catch(Exception ex){}

				}
			} catch (Exception ex) {
				throw ex;
			} finally {
				if (zOs != null)
					zOs.close();
				if (iS != null)
					iS.close();

			}
			return fileZip;
		}
	
}
