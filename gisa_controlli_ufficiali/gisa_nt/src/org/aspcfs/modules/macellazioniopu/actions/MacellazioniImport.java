/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioniopu.actions;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.macellazioniopu.base.CapoImport;
import org.aspcfs.modules.macellazioniopu.base.ImportLog;
import org.aspcfs.modules.macellazioniopu.base.ImportLogList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;

public final class MacellazioniImport extends CFSModule
{
	Logger logger = Logger.getLogger("MainLogger");
	
	private boolean fileImportabile = true;
	private String logErrori = "";

	private ArrayList<CapoImport> capi = new ArrayList<CapoImport>();
	private String idMacello = null;
	private String vpmData =null;
	private String cdSedutaMacellazione = null;
	private String csvFile = null;
	
	private void resetCampi() throws IllegalArgumentException, IllegalAccessException{
	 fileImportabile = true;
	 logErrori ="";
	 capi = new ArrayList<CapoImport>();
	 idMacello = null;
	 vpmData = null;
	 cdSedutaMacellazione = null;
	 csvFile = null;
	}
	
	public String executeCommandImportDaFile(ActionContext context) throws SQLException, IllegalArgumentException, IllegalAccessException{
		
		resetCampi();
		
		
//		String filePath = this.getPath(context, "macelli_import");
        String filePath = getWebInfPath(context,"tmp_macelli_import");

		String fileName = "";
		
		HttpMultiPartParser multiPart = new HttpMultiPartParser();
	   	
		HashMap parts = null;
		try {
			parts = multiPart.parseData(context.getRequest(), filePath);
		} catch (IllegalArgumentException | IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	     idMacello = (String) parts.get("idMacello");
	     vpmData = (String) parts.get("vpmData");
	     cdSedutaMacellazione = (String) parts.get("cdSedutaMacellazione");
	    
	    if ((Object)  parts.get("file") instanceof FileInfo) {
	          //Update the database with the resulting file
	          FileInfo newFileInfo = (FileInfo) parts.get("file");
	          //Insert a file description record into the database
	          com.zeroio.iteam.base.FileItem thisItem = new com.zeroio.iteam.base.FileItem();
	          thisItem.setLinkModuleId(Constants.ACCOUNTS);
	          thisItem.setEnteredBy(getUserId(context));
	          thisItem.setModifiedBy(getUserId(context));
	          thisItem.setClientFilename(newFileInfo.getClientFileName());
	          thisItem.setFilename(newFileInfo.getRealFilename());
	          fileName = newFileInfo.getRealFilename();
	          thisItem.setVersion(1.0);
	          thisItem.setSize(newFileInfo.getSize());
	    }
		
	    csvFile = filePath + fileName;
	  	
	    //LEGGO DAL FILE E POPOLO LISTA DEI CAPI
	   
	    leggiDaFile(context);
	    
	    Connection db = null;
	    
	    try {
	      	db = this.getConnection(context);
	    	
	    ImportLog log = new ImportLog();
	    log.setIdMacello(idMacello);
	    log.setDataMacellazione(vpmData);
	    log.setFileImport(csvFile);
	    log.setUtenteImport(this.getUserId(context));
	    log.insert(db);
	    
		if (fileImportabile){
			for (CapoImport c : capi){
				c.setIdImport(log.getId());
				
				if (c.getErroreImport()!=null && !c.getErroreImport().equals(""))
					logErrori+= "[Riga " + c.getNumeroRiga()+"] " + c.getCd_matricola() + ": " + c.getErroreImport()+ "<br/>";
				
				c.insert(db);
			}
		}
		else {
			for (CapoImport c : capi){
				if (!c.isImportabile())
					logErrori+= "[Riga " + c.getNumeroRiga()+"] " + c.getCd_matricola() + ": " + c.getErroreImport()+ "<br/>";
			}
		}
	 	
	    log.setEsitoImport(fileImportabile);
		log.setErroriImport(logErrori);
		log.updateEsito(db);
	
	
		
		System.out.println("MACELLAZIONI Import terminato");

		context.getRequest().setAttribute("orgId", idMacello);
		context.getRequest().setAttribute("tipo", "1");
		context.getRequest().setAttribute("comboSessioniMacellazione", "-1"); //Faccio tornare a CAPI NON MACELLATI
		
		String messaggio = "";
		
		if (fileImportabile){
			messaggio="<font color=\"green\">Import terminato con successo</font>";
		} else {
			messaggio="<font color=\"red\">Import fallito per questi errori:<br/>"+logErrori+"</font>";
		}
		
		context.getRequest().setAttribute("messaggioImport", messaggio);
		
		Macellazioni m = new Macellazioni();
		return m.executeCommandList(context);
		
	    } catch (Exception e) {
	    
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}finally{
			//GestoreConnessioni.freeConnectionImportatori(dbImportatori);
		
			this.freeConnection(context, db);
		}
	}
	
	
	private void leggiDaFile(ActionContext context){
		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";
		
		ArrayList<String> matricole = new ArrayList<String>();
	
		try {
			int i = 0;
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				
				Connection db = null;
				try {
					db =this.getConnection(context);
			        // use punto and virgola as separator
					String[] valori = line.split(cvsSplitBy);
					//Capi da importare se il file va bene
					CapoImport capo = null;
					capo= new CapoImport(valori);
					capo.setNumeroRiga(i);
					capo.setListaVeterinari(db);
					if (i==0){
						//creaFileScarti(intestazioni, idMacello, vpmData, cdSedutaMacellazione);
					}
					if (capo!=null && i>0){
						capo.setId_macello(idMacello);
						capo.setCd_seduta_macellazione(cdSedutaMacellazione);
						capo.setVpm_data(vpmData);
						capi.add(capo);
						matricole.add(capo.getCd_matricola());
						if (!capo.controllaValiditaRiga(db, matricole)){
							fileImportabile = false;
						}
					}
					i++;
					} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				finally {this.freeConnection(context, db);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();}
		finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
		}
		
		System.out.println("MACELLAZIONI Lettura file import terminato");
	}
	
	
	public String executeCommandListImport(ActionContext context){
	
		int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
		StringBuffer sql = new StringBuffer();
		sql.append("select * from m_import_storico where id_macello = ? order by data_import desc");
		PreparedStatement pst;
		
		Connection db = null;
		
		try {
			db = this.getConnection(context);
			pst = db.prepareStatement(sql.toString());
			pst.setInt(1, orgId) ;
		
			ResultSet rs = pst.executeQuery();
			ImportLogList logList = new ImportLogList();
			logList.creaElenco(rs);
			context.getRequest().setAttribute("listaImport", logList);
			
			
			org.aspcf.modules.controlliufficiali.base.Organization org = new org.aspcf.modules.controlliufficiali.base.Organization();
			org.getOnlyRagioneSociale(db, orgId);
			context.getRequest().setAttribute("OrgDetails", org);
			context.getRequest().setAttribute("orgId", String.valueOf(orgId));
			context.getRequest().setAttribute("op", context.getRequest().getParameter("op"));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			this.freeConnection(context, db);
		}
		
		return "ImportLogListOK";
	}
	
	
	public String executeCommandDownloadImport(ActionContext context){
		

		// TODO Auto-generated method stub
		
		int idImport=-1;
		String path_server="", titolo="";
		
		idImport = Integer.parseInt(context.getRequest().getParameter("idImport"));
		
		Connection db = null;
		
		try {
			db = this.getConnection(context);
			
			ImportLog log = new ImportLog(db, idImport);
			path_server = log.getFileImport();
	        titolo = "Import_"+log.getId()+".csv";
	        	
	        	        
	        String fileType = "";
	        String fileName = path_server;
	        
	         
	         if (new File(fileName).exists()){
	         // Find this file id in database to get file name, and file type

	         // You must tell the browser the file type you are going to send
	         // for example application/pdf, text/plain, text/html, image/jpg
	         context.getResponse().setContentType(fileType);

	         // Make sure to show the download dialog
	         context.getResponse().setHeader("Content-disposition","attachment; filename="+titolo);

	         // Assume file name is retrieved from database
	         // For example D:\\file\\test.pdf

	         File my_file = new File(fileName);

	         // This should send the file to browser
	         OutputStream out =   context.getResponse().getOutputStream();
	         FileInputStream in = new FileInputStream(my_file);
	         byte[] buffer = new byte[4096];
	         int length;
	         while ((length = in.read(buffer)) > 0){
	            out.write(buffer, 0, length);
	         }
	         in.close();
	         out.flush();
	    }
	         else{
	        	 PrintWriter out =   context.getResponse().getWriter();
	        	 out.println("File non trovato!");
	        	 out.println(fileName);
	        	 out.println("Si e' verificato un problema con il recupero del file. Si prega di contattare l'HelpDesk.");
	          }
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			this.freeConnection(context, db);
		}
				
		   return ("-none-");	
		
	}
	

	public String executeCommandLiberoConsumoVeloce(ActionContext context){
		
		int idCapo = Integer.parseInt(context.getRequest().getParameter("idCapo"));
		String messaggio ="";
		boolean macellabile = true;
		int orgId = -1;
		Connection db = null;
		
		try {
			db = this.getConnection(context);
			CapoImport c = new CapoImport(db,idCapo);
			orgId = c.getId_macello();
			
			if (c.getVpm_data()==null){
				macellabile = false;
				messaggio+="<font color=\"red\">Il capo "+c.getCd_matricola()+" non risulta assegnato a nessuna sessione.</font>";
			}
			
			if (c.getIdImport()<=0){
				macellabile = false;
				messaggio+="<font color=\"red\">Il capo "+c.getCd_matricola()+" non risulta inserito tramite import.</font>";
			}
			if (!c.getStato_macellazione().toLowerCase().contains("incompleto")){
				macellabile = false;
				messaggio+="<font color=\"red\">Il capo "+c.getCd_matricola()+" non risulta in uno stato macellabile.</font>";
			}
			
			if (macellabile){
			c.setStato_macellazione("OK.");
			c.setVpm_esito(1);
			c.aggiornaLiberoConsumo(db);
			messaggio = "<font color=\"green\">Capo "+c.getCd_matricola()+" correttamente macellato in libero consumo alla data "+ c.getVpm_dataString()+".</font>";
			}
			
			context.getRequest().setAttribute("messaggioLiberoConsumo", messaggio);
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally {
			this.freeConnection(context, db);
		}
		
		context.getRequest().setAttribute("orgId", String.valueOf(orgId));
		context.getRequest().setAttribute("tipo", "1");
		context.getRequest().setAttribute("comboSessioniMacellazione", "-1");
		
		Macellazioni m = new Macellazioni();
		return m.executeCommandList(context);
	
	}
	
}
