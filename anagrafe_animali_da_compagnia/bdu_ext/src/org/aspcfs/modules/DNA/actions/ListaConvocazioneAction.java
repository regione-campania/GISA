/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.DNA.actions;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.DNA.base.Convocato;
import org.aspcfs.modules.DNA.base.ConvocazioneTemporale;
import org.aspcfs.modules.DNA.base.ListaConvocazione;
import org.aspcfs.modules.DNA.base.ListaConvocazioneList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu_ext.base.Circoscrizione;
import org.aspcfs.modules.opu_ext.base.ComuniAnagrafica;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
import com.zeroio.webutils.FileDownload;


public class ListaConvocazioneAction extends CFSModule {
	
	public String executeCommandDefault(ActionContext context) {

		return "";
	}
	
	public String executeCommandAdd(ActionContext context) {

	if (!hasPermission(context, "lista_convocazione-add")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		
		
		try {
			db = this.getConnection(context);		

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, this.getUserSiteId(context), 1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("comuniList", comuniList);
			

			ApplicationPrefs prefs = (ApplicationPrefs) context
					.getServletContext().getAttribute("applicationPrefs");

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Add Account", "Accounts Add");
		context.getRequest().setAttribute("systemStatus",
				this.getSystemStatus(context));
		// if a different module reuses this action then do a explicit return
		if (context.getRequest().getParameter("actionSource") != null) {
			return getReturn(context, "AddAccount");
		}

		return getReturn(context, "PrepareAddLista");
	}
	
	
	public String executeCommandSalvaListaConvocazione (ActionContext context){

	    if (!(hasPermission(context, "lista_convocazione-add"))) {
	      return ("PermissionError");
	    }
	    
	    HashMap errors = new HashMap();
	    Connection db = null;
	    boolean contactRecordInserted = false;
	    boolean isValid = false;
	    boolean fileRecordInserted = false;
	   
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    ListaConvocazione listaC = (ListaConvocazione) context
		.getRequest().getAttribute("ListaConvocazione");
	    try {
	      db = getConnection(context);
	     
	      
	      String filePath = this.getPath(context, "files_convocazioni");
	      //Process the form data
	      HttpMultiPartParser multiPart = new HttpMultiPartParser();
	      multiPart.setUsePathParam(false);
	      multiPart.setUseUniqueName(true);
	      multiPart.setUseDateForFolder(true);
	      multiPart.setExtensionId(getUserId(context));
	      HashMap parts = multiPart.parseData(context.getRequest(), filePath);

	      //set import properties
	      listaC.setIdUtenteInserimento(this.getUserId(context));
	      listaC.setIdUtenteModifica(this.getUserId(context));
	      listaC.setDenominazione((String) parts.get("denominazione"));
	      listaC.setDataInizio((String) parts.get("dataInizio"));
	      listaC.setDataFine((String) parts.get("dataFine"));
	      listaC.setIdComune((String) parts.get("idComune"));
	      listaC.setNumeroCaniEsclusione((String) parts.get("numeroCaniEsclusione"));
	      String circoscrizione="-1";
	      if (parts.get("idCircoscrizione")!=null)
	    	  circoscrizione=(String) parts.get("idCircoscrizione");
	    
	      listaC.setIdCircoscrizione(circoscrizione);
	      if (!((Object) parts.get("id") instanceof FileInfo)) {
	    	  
	        fileRecordInserted = false;
	        errors.put(
	            "actionError", systemStatus.getLabel(
	                "object.validation.incorrectFileName"));
	        processErrors(context, errors);
	        
	        context.getRequest().setAttribute("name", "errore");
	        isValid = false;
	      } else {
	    	  File a = ((FileInfo) parts.get("id")).getLocalFile();
	    	  listaC.setNomeFile(a.getAbsolutePath());
	        isValid = this.validateObject(context, db, listaC);
	        
	        if (isValid) {
	        	
	        	contactRecordInserted = listaC.insert(db);
	        	
	        	
				ComuniAnagrafica c = new ComuniAnagrafica();
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, this.getUserSiteId(context), 1);
				LookupList comuniList = new LookupList(listaComuni, -1);
				comuniList.addItem(-1, "--Seleziona--");
				context.getRequest().setAttribute("comuniList", comuniList);
				
				LookupList stato = new LookupList(db, "lookup_stato_lista_convocazioni");
				context.getRequest().setAttribute("stato", stato);
				
				Circoscrizione circoscrizioni = new Circoscrizione();
				ArrayList<Circoscrizione> listaCirc = circoscrizioni.getListaByIdComune(db, listaC.getIdComune());
	        	LookupList circoscrizioniList = new LookupList(listaCirc, -1);
	        	context.getRequest().setAttribute("circoscrizioniList", circoscrizioniList);
	        	
	        	listaC.start();
//	        	 ImportManager manager = systemStatus.getImportManager(context);
//	        	Object activeImport = manager.getImport(listaC.getIdListaConvocazione());
//	        	manager.add(listaC);
	        //  contactRecordInserted = listaC.insert(db);
//	          if (contactRecordInserted) {
//	            if ((Object) parts.get("id") instanceof FileInfo) {
//	              //Update the database with the resulting file
//	              FileInfo newFileInfo = (FileInfo) parts.get("id");
//	              //Insert a file description record into the database
//	              FileItem thisItem = new FileItem();
//	              thisItem.setLinkModuleId(Constants.IMPORT_ACCOUNT_CONTACTS);
//	              thisItem.setLinkItemId(thisImport.getId());
//	              thisItem.setEnteredBy(getUserId(context));
//	              thisItem.setModifiedBy(getUserId(context));
//	              thisItem.setSubject(subject);
//	              thisItem.setClientFilename(newFileInfo.getClientFileName());
//	              thisItem.setFilename(newFileInfo.getRealFilename());
//	              thisItem.setVersion(Import.IMPORT_FILE_VERSION);
//	              thisItem.setSize(newFileInfo.getSize());
//	              isValid = this.validateObject(context, db, thisItem);
//	              if (isValid) {
//	                fileRecordInserted = thisItem.insert(db);
//	              }
//	            }
//	          }
	        }
	      }
	      if (contactRecordInserted && fileRecordInserted) {
//	        thisImport = new MicrochipImport(db, thisImport.getId());
//	        thisImport.buildFileDetails(db);
//	        thisImport.setSystemStatus(systemStatus);
	      } /*else if (contactRecordInserted) {
	        thisImport.delete(db);
	        thisImport.setId(-1);
	      }*/
	      context.getRequest().setAttribute("listaImportata", listaC);
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      freeConnection(context, db);
	    }

	    
	    if (contactRecordInserted) {
	    	
	    	return getReturn(context, "DetailsStato");
	      }
	    
	    return "";
	  
	}
	
	
	public String executeCommandDettaglioListaConvocazione(ActionContext context){
		


	    if (!(hasPermission(context, "lista_convocazione-view"))) {
	      return ("PermissionError");
	    }
	    
	
	    Connection db = null;
	    ListaConvocazione thisLista = new ListaConvocazione();
	   
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    String idListaConvocazione = (String) context.getRequest().getParameter("idLista");
	    int idStatoConvocati = -1; //Default tutti
	    
	    if ((String) context.getRequest().getParameter("idStatoConvocati") != null && 
	    		!("").equals((String) context.getRequest().getParameter("idStatoConvocati"))){
	    	idStatoConvocati = Integer.parseInt((String) context.getRequest().getParameter("idStatoConvocati"));
	    	thisLista.setIdStatoConvocati(idStatoConvocati);
	    }
	      
	      thisLista.setIdListaConvocazione(new Integer (idListaConvocazione));

	    try {
	      db = getConnection(context);
	      
	      if (idListaConvocazione != null){
	    	  thisLista.build(db);
	      }
	      
	      LookupList statiList = new LookupList(db, "lookup_stato_convocati");
	      statiList.addItem(-1, "Tutti");
		  context.getRequest().setAttribute("statiList", statiList);
		  
    	  LookupList statiConvTempList = new LookupList(db, "lookup_stato_convocazioni_temporali");
		  context.getRequest().setAttribute("statiConvTempList", statiConvTempList);
		  
//		 ComuniAnagrafica c = new ComuniAnagrafica();
//		 ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, this.getUserSiteId(context), 1);
//		 LookupList comuniList = new LookupList(listaComuni, -1);
//		 context.getRequest().setAttribute("comuniList", comuniList);
	     
	     

	      context.getRequest().setAttribute("lista", thisLista);
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      freeConnection(context, db);
	    }

	    
	    if ((String) context.getParameter("idTipoVisualizzazione") != null &&  ("1").equals((String) context.getParameter("idTipoVisualizzazione")) ){
	    	

	    	return getReturn(context, "DetailsListaConvocazioni");
	    }
	    	
	    	return getReturn(context, "DetailsLista");

	    
	    
	  
	
		
	}
	

	public String executeCommandList (ActionContext context){

	    if (!(hasPermission(context, "lista_convocazione-view"))) {
	      return ("PermissionError");
	    }
		    Connection db = null;
		    try {
				db = getConnection(context);
				UserBean user = (UserBean) context.getSession().getAttribute("User");
				// lista convocazioni
				ListaConvocazioneList lista = new ListaConvocazioneList();
				if (user.getRoleId() == Integer.parseInt(ApplicationProperties.getProperty("RUOLO_UTENTE_COMUNE")))
					lista.setIdComune(user.getUserRecord().getIdComune());
				else lista.setIdAsl(user.getSiteId());
				 lista.buildList(db);
				 context.getRequest().setAttribute("convocazioniList", lista);
				 
				// lookup stati
				 LookupList statiList = new LookupList(db, "lookup_stato_lista_convocazioni");
				context.getRequest().setAttribute("statiList", statiList);
				
				//lookup comuni
				ComuniAnagrafica c = new ComuniAnagrafica();
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, this.getUserSiteId(context), 1);
				LookupList comuniList = new LookupList(listaComuni, -1);
				context.getRequest().setAttribute("comuniList", comuniList);
				
				Circoscrizione circoscrizioni = new Circoscrizione();
				ArrayList<Circoscrizione> listaCirc = circoscrizioni.getListaByIdComune(db,-1);
	        	LookupList circoscrizioniList = new LookupList(listaCirc, -1);
	        	context.getRequest().setAttribute("circoscrizioniList", circoscrizioniList);
				 
				 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				this.freeConnection(context, db);
			}
	      return "ListOk";
	  
	}
	
	
	public String executeCommandCreaNuovoGruppoConvocazione(ActionContext context){
		


	    if (!(hasPermission(context, "lista_convocazione-edit"))) {
	      return ("PermissionError");
	    }
	    
	
	    Connection db = null;
	    ListaConvocazione thisLista = null;
	   
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    String idListaConvocazione = (String) context.getRequest().getParameter("idLista");
	    
	    if (idListaConvocazione == null)
	    	idListaConvocazione = (String) context.getRequest().getAttribute("idLista");
	      
	      ConvocazioneTemporale newConvocazioneTemporale = (ConvocazioneTemporale) context
			.getRequest().getAttribute("ConvocazioneTemporale");
	    	  //(ConvocazioneTemporale) context.getFormBean();
	      newConvocazioneTemporale.setIdUtenteInserimento(this.getUserId(context));
	      newConvocazioneTemporale.setIdUtenteModifica(this.getUserId(context));
	      
	      ArrayList<Convocato> listaConvocatiSelezionati = new ArrayList<Convocato>();

	    try {
	      db = getConnection(context);
	      
	      if (idListaConvocazione != null){
	    	  newConvocazioneTemporale.setIdListaConvocazionePadre(new Integer (idListaConvocazione));
	    	  thisLista = new ListaConvocazione(new Integer (idListaConvocazione), db);
	      }
	      
	      ArrayList<Convocato> listaAppartenentiAConvocazione = new ArrayList<Convocato>();
	      
	      listaAppartenentiAConvocazione = (ArrayList<Convocato>) thisLista.getConvocazioni();
	      
	      Iterator itr = listaAppartenentiAConvocazione.iterator();
	      
	      String toDo = "";
	      while (itr.hasNext()){
	    	  Convocato conv = (Convocato) itr.next();
	    	  toDo = (String) context.getRequest().getParameter("checkConvocato_"+conv.getId());
	    	  System.out.println(toDo);
	    	  if (toDo != null && ("on").equals(toDo) && conv.getIdStatoPresentazione() != Convocato.presentato && 
	    			  conv.getIdStatoPresentazione() != Convocato.convocato_ma_escluso_per_regolarizzazione ){
	    		  listaConvocatiSelezionati.add(conv);
	    	  }
	      }
	      
	      newConvocazioneTemporale.setConvocazioni(listaConvocatiSelezionati);
	      
	      newConvocazioneTemporale.insert(db);
	      HttpServletResponse response = context.getResponse();
	  	  response.setContentType("application/vnd.ms-excel");

	      response.setHeader("Content-Disposition", 
	     "attachment; filename="+newConvocazioneTemporale.getDenominazione().replaceAll(" ",  "")+".xls");

	     WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream()); 
	     newConvocazioneTemporale.createXLS(w);
	     
	      //--- VECCHIA GESTIONE CSV ---
//	      StringBuffer sb = newConvocazioneTemporale.createCSV(db);
//	      
//		  HttpServletResponse res = context.getResponse();
//		  res.setContentType( "application/csv" );
//		  res.setHeader( "Content-Disposition","attachment; filename=" + newConvocazioneTemporale.getDenominazione().replaceAll(" ", "") + "_.csv" );
//		//  res.setHeader( "Content-Disposition","attachment; filename=" +c.getNome_canile().replace(" ","_") + ".csv" ); 
//		  ServletOutputStream sout = res.getOutputStream();
//		  sout.write(sb.toString().getBytes());
//		 	
//		 	sout.flush();

		  
//		 ComuniAnagrafica c = new ComuniAnagrafica();
//		 ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, this.getUserSiteId(context), 1);
//		 LookupList comuniList = new LookupList(listaComuni, -1);
//		 context.getRequest().setAttribute("comuniList", comuniList);
	     
	     

	      context.getRequest().setAttribute("lista", thisLista);
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      freeConnection(context, db);
	    }

	    return getReturn(context, "GruppoConvocazioneCreato");
		
	}
	
	/**
	 * Restituisce il dettaglio di una convocazione temporale all'interno di una lista di convocazione
	 * @param context
	 * @return
	 */
	
public String executeCommandDettaglioConvocazioneTemporale(ActionContext context){
		


	    if (!(hasPermission(context, "lista_convocazione-view"))) {
	      return ("PermissionError");
	    }
	    
	
	    Connection db = null;
	    ConvocazioneTemporale thisConvocazione = new ConvocazioneTemporale();
	   int idStatoConvocati = -1;
	   
	    String idListaConvocazione = (String) context.getRequest().getParameter("idListaConvocazioneTemporale");
	    
	    if ((String) context.getRequest().getParameter("idStatoConvocati") != null && 
	    		!("").equals((String) context.getRequest().getParameter("idStatoConvocati"))){
	    	idStatoConvocati = Integer.parseInt((String) context.getRequest().getParameter("idStatoConvocati"));
	    	thisConvocazione.setIdStatoConvocati(idStatoConvocati);
	    }
	      
	      

	    try {
	      db = getConnection(context);
	      
	      if (idListaConvocazione != null){
	    	  thisConvocazione.setId(new Integer (idListaConvocazione));
	    	  thisConvocazione.build(db); // = new ConvocazioneTemporale(new Integer (idListaConvocazione), db);
	      }
	      
	      LookupList statiList = new LookupList(db, "lookup_stato_convocati");
	      statiList.addItem(-1, "Tutti");
		  context.getRequest().setAttribute("statiList", statiList);
		  
    	  LookupList statiConvTempList = new LookupList(db, "lookup_stato_convocazioni_temporali");
		  context.getRequest().setAttribute("statiConvTempList", statiConvTempList);
		  
//		 ComuniAnagrafica c = new ComuniAnagrafica();
//		 ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, this.getUserSiteId(context), 1);
//		 LookupList comuniList = new LookupList(listaComuni, -1);
//		 context.getRequest().setAttribute("comuniList", comuniList);
	     
	     

	      context.getRequest().setAttribute("convocazione", thisConvocazione);
	    } catch (Exception e) {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    } finally {
	      freeConnection(context, db);
	    }

	    	return getReturn(context, "DetailsListaConvocazioneTemporale");

	    
	    
	  
	
		
	}


	public String executeCommandStampaCsvAggiornatoConvocazioneTemporale(
			ActionContext context) {

		 if (!(hasPermission(context, "lista_convocazione-view"))) {
		 return ("PermissionError");
		 }

		Connection db = null;
		ConvocazioneTemporale thisConvocazione = null;

		String idListaConvocazione = (String) context.getRequest()
				.getParameter("idListaConvocazioneTemporale");

		try {
			db = getConnection(context);

			if (idListaConvocazione != null) {
				thisConvocazione = new ConvocazioneTemporale(new Integer(
						idListaConvocazione), db);
			}

			StringBuffer sb = thisConvocazione.createCSV(db);

			HttpServletResponse res = context.getResponse();
			res.setContentType("application/csv");
			res.setHeader("Content-Disposition", "attachment; filename="
					+ thisConvocazione.getDenominazione().replaceAll(" ",  "") + "_.csv");
			// res.setHeader( "Content-Disposition","attachment; filename="
			// +c.getNome_canile().replace(" ","_") + ".csv" );
			ServletOutputStream sout = res.getOutputStream();
			sout.write(sb.toString().getBytes());

			sout.flush();
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			freeConnection(context, db);
		}

		return "-none-";

	}
	
	public String executeCommandStampaXLSAggiornatoConvocazioneTemporale(
			ActionContext context) {

		 if (!(hasPermission(context, "lista_convocazione-view"))) {
		 return ("PermissionError");
		 }

		Connection db = null;
		ConvocazioneTemporale thisConvocazione = null;

		String idListaConvocazione = (String) context.getRequest()
				.getParameter("idListaConvocazioneTemporale");

		HttpServletResponse response = context.getResponse();
		try {
			db = getConnection(context);

			if (idListaConvocazione != null) {
				thisConvocazione = new ConvocazioneTemporale();
				thisConvocazione.setIdStatoConvocati(Convocato.convocato_non_presentato);
				thisConvocazione.setId(new Integer(
						idListaConvocazione));
				thisConvocazione.build(db);
				
		
			}
			
			response.setContentType("application/vnd.ms-excel");

		     response.setHeader("Content-Disposition", 
		    "attachment; filename="+thisConvocazione.getDenominazione().replaceAll(" ",  "")+".xls");

		     WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream()); 
		     thisConvocazione.createXLS(w);
//		     WritableSheet s = w.createSheet("Demo", 0);
//
//		     s.addCell(new Label(0, 0, "Hello World"));
//		     w.write();
//		     w.close();


			
		}catch (IllegalStateException e) {
			System.out.println("evitata");
		} 
		
		
		catch (IOException e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 		
		catch (SQLException e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}
		
		finally {
			freeConnection(context, db);
		}

		return "-none-";

	}
	
	
	
	/**
	 * Stampa tutti i convocati di una lista
	 * 
	 */
	
	
	public String executeCommandStampaXLSAggiornatoConvocazione(
			ActionContext context) {

		 if (!(hasPermission(context, "lista_convocazione-view"))) {
		 return ("PermissionError");
		 }

		Connection db = null;
		ListaConvocazione thisConvocazione = null;

		String idListaConvocazione = (String) context.getRequest()
				.getParameter("idListaConvocazione");

		HttpServletResponse response = context.getResponse();
		try {
			db = getConnection(context);

			if (idListaConvocazione != null) {
				thisConvocazione = new ListaConvocazione();
				//thisConvocazione.setIdStatoConvocati(Convocato.convocato_non_presentato);
				thisConvocazione.setIdListaConvocazione(new Integer(
						idListaConvocazione));
				thisConvocazione.build(db);
				
		
			}
			
			response.setContentType("application/vnd.ms-excel");

		     response.setHeader("Content-Disposition", 
		    "attachment; filename="+thisConvocazione.getDenominazione().replaceAll(" ",  "")+".xls");

		     WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream()); 
		     thisConvocazione.createXLS(w);
//		     WritableSheet s = w.createSheet("Demo", 0);
//
//		     s.addCell(new Label(0, 0, "Hello World"));
//		     w.write();
//		     w.close();


			
		}catch (IllegalStateException e) {
			System.out.println("evitata");
		} 
		
		
		catch (IOException e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 		
		catch (SQLException e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}
		
		finally {
			freeConnection(context, db);
		}

		return "-none-";

	}

	
	
	

	
	public String executeCommandVisualizzaFilesEsito(
			ActionContext context) {

		 if (!(hasPermission(context, "lista_convocazione-view"))) {
		 return ("PermissionError");
		 }

		Connection db = null;
		ListaConvocazione thisConvocazione = null;

		String idListaConvocazione = (String) context.getRequest()
				.getParameter("idListaConvocazione");

		try {
			db = getConnection(context);

			if (idListaConvocazione != null) {
				thisConvocazione = new ListaConvocazione(new Integer(
						idListaConvocazione), db);
			}
			StringBuffer sb = null;
	//		StringBuffer sb = thisConvocazione.createCSV(db);
			File f = null;
			
			 FileDownload fileDownload = new FileDownload();
			 
				if (context.getRequest().getParameter("errore") != null ){					
					fileDownload.setFullPath(thisConvocazione.getNomeFile()+"_error");
					 fileDownload.setDisplayName(thisConvocazione.getNomeFile().substring(thisConvocazione.getNomeFile().lastIndexOf("\\")+1)+"_error.csv");
				}
				else{
					 fileDownload.setFullPath(thisConvocazione.getNomeFile());
					 fileDownload.setDisplayName(thisConvocazione.getNomeFile().substring(thisConvocazione.getNomeFile().lastIndexOf("\\")+1)+".csv");
				}
				
				
			 
		       
		     //   fileDownload.setDisplayName(itemToDownload.getClientFilename());
		        if (fileDownload.fileExists()) {
		          fileDownload.sendFile(context);
		        }
			
//			if (context.getRequest().getAttribute("errore") != null )
//				
//				f = new File(thisConvocazione.getNomeFile()+"_error");
//			else
//				f = new File(thisConvocazione.getNomeFile());
//
//			sb.append(f.toString());
//			HttpServletResponse res = context.getResponse();
//			res.setContentType("application/csv");
//			res.setHeader("Content-Disposition", "attachment; filename="
//					+ thisConvocazione.getDenominazione() + "_.csv");
//			// res.setHeader( "Content-Disposition","attachment; filename="
//			// +c.getNome_canile().replace(" ","_") + ".csv" );
//			ServletOutputStream sout = res.getOutputStream();
//			sout.write(sb.toString().getBytes());
//
//			sout.flush();
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			freeConnection(context, db);
		}

		return ("-none-");

	}

	public String executeCommandAggiornaListaDaBDU(
			ActionContext context) throws UnknownHostException, SQLException {

		 if (!(hasPermission(context, "lista_convocazione-view"))) {
		 return ("PermissionError");
		 }
		
		 ListaConvocazione thisLista = null;
		   
		    SystemStatus systemStatus = this.getSystemStatus(context);
		    String idListaConvocazione = (String) context.getRequest().getParameter("idLista");
		     Connection db = null; 
		      

		    try {
		   		      
		      if (idListaConvocazione != null){
		    	  db = GestoreConnessioni.getConnection();
		    	  thisLista = new ListaConvocazione(new Integer (idListaConvocazione), db);
		    	  GestoreConnessioni.freeConnection(db);
		    	  
		      }
		      
		      
		      //Aggiornamento lista
		      
		      thisLista.aggiornaDaBDU();
		      //Fine aggiornamento lista 
		      

		      context.getRequest().setAttribute("lista", thisLista);
		      
		      db = GestoreConnessioni.getConnection();
		      LookupList statiList = new LookupList(db, "lookup_stato_convocati");
			  context.getRequest().setAttribute("statiList", statiList);
			  
	    	  LookupList statiConvTempList = new LookupList(db, "lookup_stato_convocazioni_temporali");
			  context.getRequest().setAttribute("statiConvTempList", statiConvTempList);
			  GestoreConnessioni.freeConnection(db);
		      
		      
		    } catch (Exception e) {
		      context.getRequest().setAttribute("Error", e);
		      return ("SystemError");
		    } 

		    context.getRequest().setAttribute("aggiornaBDUmessage", "Aggiornameno eseguito.");
		    
		    if ((String) context.getParameter("idTipoVisualizzazione") != null &&  ("1").equals((String) context.getParameter("idTipoVisualizzazione")) ){
		    	

		    	return getReturn(context, "DetailsListaConvocazioni");
		    }
		    	
		    	return getReturn(context, "DetailsLista");

		   		
		}


	
	

}
