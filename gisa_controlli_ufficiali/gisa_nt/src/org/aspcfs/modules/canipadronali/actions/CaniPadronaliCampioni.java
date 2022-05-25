/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.canipadronali.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.campioni.actions.Campioni;
import org.aspcfs.modules.campioni.base.Ticket;
import org.aspcfs.modules.canipadronali.base.Cane;
import org.aspcfs.modules.canipadronali.base.OperazioniCaniDAO;
import org.aspcfs.modules.canipadronali.base.Proprietario;
import org.aspcfs.utils.ControlliUfficialiUtil;
import org.aspcfs.utils.DwrPreaccettazione;
import org.aspcfs.utils.RispostaDwrCodicePiano;
import org.aspcfs.utils.web.CustomLookupList;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.RequestUtils;
import org.json.JSONObject;

import com.darkhorseventures.framework.actions.ActionContext;

public class CaniPadronaliCampioni extends CFSModule {
	
	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "canipadronali-campioni-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		
		try {
			db = this.getConnection(context);
			
			context.getRequest().setAttribute("isCanePadronale", "si");
			// aggiunte queste istruzioni
			String temporgId = context.getRequest().getParameter("orgId");
			int tempid = Integer.parseInt(temporgId);
			//String assetId = context.getRequest().getParameter("assetId");
			int id_prop = Integer.parseInt(temporgId);
			//int id_cane = Integer.parseInt(assetId) ;
			
			
			String idC = context.getRequest().getParameter("idC");
			int idControlloUfficiale = Integer.parseInt(idC);
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			Proprietario proprietario = op.dettaglioProprietario(id_prop, idControlloUfficiale) ;
			CustomLookupList lista_mc = new CustomLookupList();
			
			
			lista_mc.buildListCaniControllo(db, idControlloUfficiale);
			context.getRequest().setAttribute("ListaMc", lista_mc);
			int idAsl = -1 ;
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
			context.getRequest().setAttribute("siteId",idAsl) ;
			context.getRequest().setAttribute("OrgDetails", proprietario);
			addModuleBean(context, "CaniPadronali CU", "CaniPadronali CU");
		
			Campioni c = new Campioni ();
			c.executeCommandAdd(context,db);

		} catch (Exception e) 
		{
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	
		if (context.getRequest().getParameter("actionSource") != null) {
			context.getRequest().setAttribute("actionSource",
					context.getRequest().getParameter("actionSource"));
			return "AddTicketOK";
		}
		context.getRequest().setAttribute("systemStatus",
				this.getSystemStatus(context));
		return ("AddOK");
	}
	
	
	

	public String executeCommandInsert(ActionContext context)
			throws SQLException {
		if (!(hasPermission(context, "canipadronali-campioni-add"))) {
			return ("PermissionError");
		}
		Connection db = null;
		boolean recordInserted = false;
		boolean contactRecordInserted = false;
		boolean isValid = true;
		Ticket newTic = (Ticket) context.getFormBean();
		
		
		try
		{
			db = this.getConnection(context);
			
			
			
			String temporgId = context.getRequest().getParameter("orgId");
			//String assetId = context.getRequest().getParameter("assetId");
			String idC = context.getRequest().getParameter("idC");

			
			int id_prop = Integer.parseInt(temporgId);
			//int id_cane = Integer.parseInt(assetId) ;
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(idC)) ;
			int idAsl = -1 ;
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
			context.getRequest().setAttribute("siteId",idAsl) ;
			context.getRequest().setAttribute("ragione_sociale", proprietario.getName());
			
			Campioni c = new Campioni();
			c.executeCommandInsert(context,db);
			recordInserted = (Boolean) context.getRequest().getAttribute("recordInserted");
			isValid = (Boolean) context.getRequest().getAttribute("isValid");
			
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "ViewCaniPadronali", "Ticket Insert ok");
		if (recordInserted && isValid) {
			if (context.getRequest().getParameter("actionSource") != null) {
				context.getRequest().setAttribute("actionSource",
						context.getRequest().getParameter("actionSource"));
				return "InsertTicketOK";
			}
			String retPage = "DettaglioOK";
			//String tipo_richiesta = newTic.getTipo_richiesta();
			//tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);

			retPage = "InsertOK";
		
			return (retPage);// ("InsertOK");
		}
		return (executeCommandAdd(context));
	}

	public String executeCommandReopenTicket(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-campioni-edit")) {
			return ("PermissionError");
		}
		
		Integer resultCount = -1 ;
		Connection db = null ;
		try {
			db = this.getConnection(context);
			
			Campioni c = new Campioni();
			
			c.executeCommandReopenTicket(context,db);
			
			resultCount = (Integer) context.getRequest().getAttribute("resultCount");
			
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		}
		finally
		{
			this.freeConnection(context, db);
		}
		if (resultCount == -1) {
			return (executeCommandTicketDetails(context));
		} else if (resultCount == 1) {
			return (executeCommandTicketDetails(context));
		} else {
			context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
			return ("UserError");
		}
	}
	public String executeCommandTicketDetails(ActionContext context) {
		if (!hasPermission(context, "canipadronali-campioni-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		// Parameters
				
		String retPag = null;
		try {
			db = this.getConnection(context);
			Integer orgId = -1 ;
			Campioni c = new Campioni();
			c.executeCommandTicketDetails(context,db);
			String ticketId = context.getRequest().getParameter("id");
			if (ticketId== null)
			{
				ticketId = (String) context.getRequest().getAttribute("idCampione");
			}

			orgId = (Integer) context.getRequest().getAttribute("orgId");			
			
			int id_prop = orgId;
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(ticketId)) ;
			int idAsl = -1 ;
			
			addModuleBean(context, "CaniPadronali CU", "CaniPadronali CU");
			
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
			context.getRequest().setAttribute("siteId",idAsl) ;
			context.getRequest().setAttribute("OrgDetails", proprietario);
			// find record permissions for portal users
			retPag = "DettaglioOK";

			
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		// return getReturn(context, "TicketDetails");
		return retPag;
	}
	
	public String executeCommandConfirmDelete(ActionContext context) {
		if (!hasPermission(context, "canipadronali-campioni-delete")) {
			return ("PermissionError");
		}
		Connection db = null;
		// Parameters
		String id = context.getRequest().getParameter("id");
		int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
		try {
			db = this.getConnection(context);
			SystemStatus systemStatus = this.getSystemStatus(context);
			Ticket ticket = new Ticket(db, Integer.parseInt(id));
			//check permission to record
			String temporgId = context.getRequest().getParameter("orgId");
			String assetId = context.getRequest().getParameter("assetId");
			int id_prop = Integer.parseInt(temporgId);
			int id_cane = -1;
			//ma questo controllo sul cane ci vuole??
			if (assetId!= null && !assetId.equals("")) { 
				id_cane = Integer.parseInt(assetId) ;
				OperazioniCaniDAO op = new OperazioniCaniDAO() ;
				op.setDb(db);
				Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(id)) ;
				int idAsl = -1 ;
				if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
				context.getRequest().setAttribute("siteId",idAsl) ;
				context.getRequest().setAttribute("OrgDetails", proprietario);
			}
			DependencyList dependencies = ticket.processDependencies(db);
			// Prepare the dialog based on the dependencies
			HtmlDialog htmlDialog = new HtmlDialog();
			dependencies.setSystemStatus(systemStatus);
			htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
			htmlDialog.addMessage(systemStatus
					.getLabel("confirmdelete.caution")
					+ "\n" + dependencies.getHtmlString());
			/*htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));*/
			htmlDialog.addButton(systemStatus.getLabel("global.button.delete"),
					"javascript:window.location.href='CaniPadronaliCampioni.do?command=DeleteTicket&id="
							+ id 
							+ "&assetId="
							+id_cane 
							+ "&orgId="
							+ orgId
							+ "&forceDelete=true"
							+ RequestUtils.addLinkParams(context.getRequest(),
									"popup|popupType|actionId") + "'");
			htmlDialog.addButton(systemStatus.getLabel("button.cancel"),
					"javascript:parent.window.close()");
			context.getSession().setAttribute("Dialog", htmlDialog);
			
			String checkCampioneCancellabile = ticket.getCampioneCancellabile(db);
			int codiceCampioneCancellabile = Integer.parseInt(checkCampioneCancellabile.split(";;")[0]);
			if (codiceCampioneCancellabile > 0){
				String erroreCampioneCancellabile = checkCampioneCancellabile.split(";;")[1];
				htmlDialog = new HtmlDialog();
				dependencies.setSystemStatus(systemStatus);
				htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
				htmlDialog.addMessage("<font color=\"red\"><b>"+erroreCampioneCancellabile + "</b></font>\n" + dependencies.getHtmlString());
				htmlDialog.addButton(systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
				context.getSession().setAttribute("Dialog", htmlDialog);
			}
			//Preaccettazione
			
			DwrPreaccettazione dwrPreacc = new DwrPreaccettazione();
			String result = dwrPreacc.Preaccettazione_CancellazionePreaccettazione(id, "0", String.valueOf(getUserId(context)));
			JSONObject jsonObj;
			jsonObj = new JSONObject(result);
			int esitoCancellazionePreacc =  Integer.parseInt(jsonObj.getString("esito_cancellazione"));
			String erroreCancellazionePreacc = jsonObj.getString("errore_cancellazione");
			
			if (esitoCancellazionePreacc > 0){
				htmlDialog = new HtmlDialog();
				dependencies.setSystemStatus(systemStatus);
				htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
				htmlDialog.addMessage("<font color=\"red\"><b>"+erroreCancellazionePreacc + "</b></font>\n" + dependencies.getHtmlString());
				htmlDialog.addButton(systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
				context.getSession().setAttribute("Dialog", htmlDialog);
			}
			return ("ConfirmDeleteOK");
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	}

	/**
	 * Delete the specified ticket
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandDeleteTicket(ActionContext context) {
		if (!hasPermission(context, "canipadronali-campioni-delete")) {
			return ("PermissionError");
		}
		boolean recordDeleted = false;
		Connection db = null;
		// Parameters
		int orgId = Integer
				.parseInt(context.getRequest().getParameter("orgId"));
		String passedId = context.getRequest().getParameter("id");
		try {
			db = this.getConnection(context);
			Ticket thisTic = new Ticket(db, Integer.parseInt(passedId));
			//check permission to record
			
			
			 String id_controllo=thisTic.getIdControlloUfficiale();
				while(id_controllo.startsWith("0")){
					
					id_controllo=id_controllo.substring(1);
				}

			context.getRequest().setAttribute("idC", id_controllo);
			
			String temporgId = context.getRequest().getParameter("orgId");
			String assetId = context.getRequest().getParameter("assetId");
			int id_prop = Integer.parseInt(temporgId);
			int id_cane = Integer.parseInt(assetId) ;
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(id_controllo)) ;
			int idAsl = -1 ;
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
			context.getRequest().setAttribute("siteId",idAsl) ;
			context.getRequest().setAttribute("OrgDetails", proprietario);
			recordDeleted = thisTic.delete(db, getDbNamePath(context));
			if (recordDeleted) {
				processDeleteHook(context, thisTic);
				//del
				String inline = context.getRequest().getParameter("popupType");
				context
						.getRequest()
						.setAttribute(
								"refreshUrl",
								"CaniPadronaliVigilanza.do?command=TicketDetails&assetId="+id_cane+"&id="+id_controllo+"&orgId="
										+ orgId
										+ (inline != null
												&& "inline".equals(inline
														.trim()) ? "&popup=true"
												: ""));
			}
			//Preaccettazione
			DwrPreaccettazione dwrPreacc = new DwrPreaccettazione();
			String result = dwrPreacc.Preaccettazione_CancellazionePreaccettazione(passedId, "1", String.valueOf(getUserId(context)));
			JSONObject jsonObj;
			jsonObj = new JSONObject(result);
			int esitoCancellazionePreacc =  Integer.parseInt(jsonObj.getString("esito_cancellazione"));
			String erroreCancellazionePreacc = jsonObj.getString("errore_cancellazione");
						
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		if (recordDeleted) {
			return ("DeleteTicketOK");
		} else {
			return (executeCommandTicketDetails(context));
		}
	}
	
	public String executeCommandChiudi(ActionContext context) {
		if (!(hasPermission(context, "canipadronali-campioni-edit"))) {
			return ("PermissionError");
		}
		int resultCount = -1;
		Connection db = null;
		Ticket thisTicket = null;
		try {
			db = this.getConnection(context);
			thisTicket = new Ticket(db, Integer.parseInt(context.getRequest()
					.getParameter("id")));
			Ticket oldTicket = new Ticket(db, thisTicket.getId());
			//check permission to record
			
			thisTicket.setModifiedBy(getUserId(context));
			resultCount = thisTicket.chiudi(db);
			
			thisTicket.getIdControlloUfficiale();
			String padd="000000";
			 String id_controllo=thisTicket.getIdControlloUfficiale();
				while(id_controllo.startsWith("0")){
					
					id_controllo=id_controllo.substring(1);
				}

			context.getRequest().setAttribute("idC", id_controllo);
			
			if (resultCount <= 0 && resultCount!=-3)
			{
				context.getRequest().setAttribute("Messaggio", "Chiusura non avvenuta assicurarsi di aver inserito l' Esito");
				return (executeCommandTicketDetails(context));
			}
			
			
			int pianoBenessereAnimale = 14 ;
			
			
			RispostaDwrCodicePiano codiceInterno = ControlliUfficialiUtil.getCodiceInternoPianoMonitoraggio(db, "lookup_piano_monitoraggio", thisTicket.getMotivazione_piano_campione());
			if (resultCount == -3 && codiceInterno.getCodiceInterno().equalsIgnoreCase(""+pianoBenessereAnimale))
			{
				context.getRequest().setAttribute("Messaggio", "Controllare di aver inserito la Scheda di Valutazione Comportamentale Benessere Animale");
				return (executeCommandTicketDetails(context));
			}
			
			
			
			
			/*
			 * Inifio Giuseppe
			 * 
			 */
			
			
			org.aspcfs.modules.vigilanza.base.Ticket thisTicketV = new org.aspcfs.modules.vigilanza.base.Ticket(
			          db, Integer.parseInt(id_controllo));
			   
			
			String ticketId = context.getRequest().getParameter("id");
		      
		     
			 
			 org.aspcfs.modules.campioni.base.TicketList ticList = new org.aspcfs.modules.campioni.base.TicketList();
			 int passedId = thisTicketV.getOrgId();
			 ticList.setOrgId(passedId);
			 ticList.buildListControlli(db, passedId, id_controllo);

			 
			 org.aspcfs.modules.tamponi.base.TicketList tamponiList = new org.aspcfs.modules.tamponi.base.TicketList();
			 int pasId = thisTicketV.getOrgId();
			 tamponiList.setOrgId(passedId);
			 tamponiList.buildListControlli(db, pasId, id_controllo);

				  	    
				      
				      
				      Iterator campioniIterator=ticList.iterator();
				      Iterator tamponiIterator=tamponiList.iterator();
				      
				      
				      
				      int flag=0;
				      while(campioniIterator.hasNext()){
				    	  
				    	  org.aspcfs.modules.campioni.base.Ticket tic = (org.aspcfs.modules.campioni.base.Ticket) campioniIterator.next();
				    	  
				    	  if(tic.getClosed()==null){
				    		  flag=1;
				    		  
				    		  break;
				    		  
				    	  }
				    		  
				      }
				      
				     
				      while(tamponiIterator.hasNext()){
				    	  
				    	  org.aspcfs.modules.tamponi.base.Ticket tic = (org.aspcfs.modules.tamponi.base.Ticket) tamponiIterator.next();
				    	  
				    	  if(tic.getClosed()==null){
				    		  flag=1;
				    		  
				    		  break;
				  	  }
				    	 	  
				      }
				      
				      
				      if (flag == 0)
				      {
				    	  thisTicketV.apriTemp(db);
				      }
				     
				      
				      if(thisTicketV.isNcrilevate()==true){
				    	  org.aspcfs.modules.nonconformita.base.TicketList nonCList = new org.aspcfs.modules.nonconformita.base.TicketList();
				          int passIdN = thisTicketV.getOrgId();
				          nonCList.setOrgId(passedId);
				          nonCList.buildListControlli(db, passIdN, id_controllo);
				    	  
				          Iterator ncIterator=nonCList.iterator();
				          
				          while(ncIterator.hasNext()){
					    	  
					    	  org.aspcfs.modules.nonconformita.base.Ticket tic = (org.aspcfs.modules.nonconformita.base.Ticket) ncIterator.next();
					    	  
					    	  if(tic.getClosed()==null){
					    		  flag=1;
					    		  
					    		  break;
					    		  
					    	  }
					    		  
					      }
				  
				      }
				      
				      
				      
				      if(thisTicketV.getTipoCampione()==5){
				    	  
				    	  org.aspcfs.modules.audit.base.AuditList audit = new org.aspcfs.modules.audit.base.AuditList();
				          int AuditOrgId = thisTicketV.getOrgId();
				          String idTi = thisTicketV.getPaddedId();
				          audit.setOrgId(AuditOrgId);
				         
				          audit.buildListControlli(db, AuditOrgId, idTi);
				          
				    	  Iterator itAudit=audit.iterator();
				    	  
				    	  if(!itAudit.hasNext()){
				    		  
				  		    	flag=2;
				  	    			  		    
				    	  }else{
				    	  
			      while(itAudit.hasNext()){
					    	  
					    	  org.aspcfs.modules.audit.base.Audit auditTemp = (org.aspcfs.modules.audit.base.Audit) itAudit.next();
					    	  Organization orgDetails = new Organization(db,thisTicketV.getOrgId());
					    	  
					    	  if(thisTicketV.isCategoriaisAggiornata()==false){
					    		  flag=2;
					    		  
					    		  break;
					    		  
					    	  }
					    		  
					    }}
				     }
				          
				      
				      
				      String attivitaCollegate=context.getRequest().getParameter("altro");
				 		      
				      if(attivitaCollegate==null){
				      if(flag==1 || flag==2){
				    	  context.getRequest().setAttribute("Chiudi", ""+flag);
				    	  return (executeCommandTicketDetails(context));
				    	  
				      }
				      }
				      
				  
				      String chiudiCu = context.getRequest().getParameter("chiudiCu");
					  if(flag==0 ){
						  if(chiudiCu !=null)
						  {
					      thisTicketV.setModifiedBy(getUserId(context));
					      resultCount = thisTicketV.chiudi(db);
						  }else
						  {
							  context.getRequest().setAttribute("Messaggio2", "Attivita collegate al cu chiuse");
						  }
					  }
			
			if (resultCount == -1) {
				return (executeCommandTicketDetails(context));
			} else if (resultCount == 1) {
				thisTicket.queryRecord(db, thisTicket.getId());
				this.processUpdateHook(context, oldTicket, thisTicket);
				
				
				
				return (executeCommandTicketDetails(context));
			}
			
		    
			
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
		return ("UserError");

	}

	public String executeCommandUpdateTicketEsiti(ActionContext context) {
		Connection db = null;
		Ticket thisTicket = null;
		try {
			db = this.getConnection(context);
			Campioni.executeCommandUpdateTicketEsiti(context, db);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return executeCommandTicketDetails(context);
}
	public String executeCommandRiapriTicketEsiti(ActionContext context) {
		Connection db = null;
		Ticket thisTicket = null;
		try {
			db = this.getConnection(context);
			Campioni.executeCommandRiapriTicketEsiti(context, db);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return executeCommandTicketDetails(context);
}	
}
