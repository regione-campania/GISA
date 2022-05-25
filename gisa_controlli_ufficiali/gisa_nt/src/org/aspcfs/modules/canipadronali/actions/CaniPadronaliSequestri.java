/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.canipadronali.actions;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanList;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.CategoryEditor;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.canipadronali.base.Cane;
import org.aspcfs.modules.canipadronali.base.OperazioniCaniDAO;
import org.aspcfs.modules.canipadronali.base.Proprietario;

import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.products.base.CustomerProduct;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.sequestri.base.Ticket;
import org.aspcfs.modules.troubletickets.base.TicketCategory;
//import org.aspcfs.modules.troubletickets.base.TicketCategoryList;
import org.aspcfs.modules.troubletickets.base.TicketDefect;
import org.aspcfs.modules.troubletickets.base.TicketDefectList;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.RequestUtils;

import com.darkhorseventures.framework.actions.ActionContext;

public class CaniPadronaliSequestri extends CFSModule {

	
	 public String executeCommandTicketDetails(ActionContext context) {
		    if (!hasPermission(context, "canipadronali-sequestri-view")) {
		      return ("PermissionError");
		    }
		    Connection db = null;
		    
		    if(context.getRequest().getAttribute("Messaggio")!=null)
			{
			
				context.getRequest().setAttribute("Messaggio", context.getRequest().getAttribute("Messaggio"));
			}
		    //Parameters
		    String ticketId = context.getRequest().getParameter("id");
		    String tickId = context.getRequest().getParameter("ticketId");
		    String retPag = null;
		    
		    String id = context.getRequest().getParameter("idC");
		  	context.getRequest().setAttribute("idC",
		  			id);
		  	
		  	String id2 = context.getRequest().getParameter("idNC");
		  	context.getRequest().setAttribute("idNC",
		  			id2);

		    try {
		      db = this.getConnection(context);
		      // Load the ticket
		      Ticket newTic = new Ticket();
		      SystemStatus systemStatus = this.getSystemStatus(context);
		      newTic.setSystemStatus(systemStatus);
		      if(tickId == null)
		        newTic.queryRecord(db, Integer.parseInt(ticketId));
		      else
		    	newTic.queryRecord(db, Integer.parseInt(tickId));

		    
		     

		      
		      int passedId = newTic.getOrgId();
		      org.aspcfs.modules.sanzioni.base.TicketList sanzioniList = new org.aspcfs.modules.sanzioni.base.TicketList();
		      int passId = newTic.getOrgId();
		      sanzioniList.setOrgId(passedId);
		      sanzioniList.buildListControlli(db, passId, ticketId,8);
		      context.getRequest().setAttribute("SanzioniList", sanzioniList);
		      
		      org.aspcfs.modules.reati.base.TicketList reatiList = new org.aspcfs.modules.reati.base.TicketList();
		      int passIdR = newTic.getOrgId();
		      reatiList.setOrgId(passedId);
		      reatiList.buildListControlli(db, passIdR, ticketId,8);
		      context.getRequest().setAttribute("ReatiList", reatiList);
		      
		      org.aspcfs.modules.sequestri.base.TicketList seqList = new org.aspcfs.modules.sequestri.base.TicketList();
		      int passIdS = newTic.getOrgId();
		      seqList.setOrgId(passedId);
		      seqList.buildListControlli(db, passIdS, ticketId,8);
		      context.getRequest().setAttribute("SequestriList", seqList);
		      
		      
		      
		      LookupList SiteIdList = new LookupList(db, "lookup_site_id");
		      SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("SiteIdList", SiteIdList);
		      
		      LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
		      Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("Provvedimenti", Provvedimenti);
		      
		      LookupList SequestroDi = new LookupList(db, "lookup_sequestri_amministrative");
		      SequestroDi.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("SequestroDi", SequestroDi);
		      
		      LookupList AzioneNonConforme = new LookupList(db, "lookup_sequestri_penali");
		      AzioneNonConforme.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("AzioneNonConforme", AzioneNonConforme);
		      
		      LookupList Sequestri = new LookupList(db, "lookup_sequestri");
		      Sequestri.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("Sequestri", Sequestri);

		      // check wether or not the customer product id exists
		  
		      context.getRequest().setAttribute("TicketDetails", newTic);
		      
		      retPag = "DettaglioOK";
		      
		      addRecentItem(context, newTic);
		      // Load the organization for the header
			  	String temporgId = context.getRequest().getParameter("orgId");
				int id_prop = Integer.parseInt(temporgId);
				OperazioniCaniDAO op = new OperazioniCaniDAO() ;
				op.setDb(db);
				Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(id)) ;
				int idAsl = -1 ;
				if (proprietario != null )
					idAsl = proprietario.getIdAsl() ;
					context.getRequest().setAttribute("siteId",idAsl) ;
					context.getRequest().setAttribute("OrgDetails", proprietario);
		      addModuleBean(context, "View CaniPadronali", "View Tickets");
		      // Reset any pagedLists since this could be a new visit to this ticket
		      deletePagedListInfo(context, "AccountTicketsFolderInfo");
		      deletePagedListInfo(context, "AccountTicketDocumentListInfo");
		      deletePagedListInfo(context, "AccountTicketTaskListInfo");
		      deletePagedListInfo(context, "accountTicketPlanWorkListInfo");
		     
		    } catch (Exception errorMessage) {
		      context.getRequest().setAttribute("Error", errorMessage);
		      return ("SystemError");
		    } finally {
		      this.freeConnection(context, db);
		    }
		    //return getReturn(context, "TicketDetails");
		    return retPag;
		  }
	 
	 public String executeCommandModifyTicket(ActionContext context) {
		    if (!hasPermission(context, "canipadronali-sequestri-edit")) {
		      return ("PermissionError");
		    }				
		    Connection db = null;
		    Ticket newTic = null;
		    //Parameters
		    String ticketId = context.getRequest().getParameter("id");
		    SystemStatus systemStatus = this.getSystemStatus(context);
		    try {
		      db = this.getConnection(context);
		      User user = this.getUser(context, this.getUserId(context));
		      //Load the ticket
		      if (context.getRequest().getParameter("companyName") == null) {
		        newTic = new Ticket(db, Integer.parseInt(ticketId));
		      } else {
		        newTic = (Ticket) context.getFormBean();
		      }
		      String id = context.getRequest().getParameter("idC");
		    	context.getRequest().setAttribute("idC",
		    			id);
		    	
		    	String id2 = context.getRequest().getParameter("idNC");
		    	context.getRequest().setAttribute("idNC",
		    			id2);
		      
		      //Load the organization
			  	String temporgId = context.getRequest().getParameter("orgId");
				int id_prop = Integer.parseInt(temporgId);
				OperazioniCaniDAO op = new OperazioniCaniDAO() ;
				op.setDb(db);
				Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(id)) ;
				int idAsl = -1 ;
				if (proprietario != null )
					idAsl = proprietario.getIdAsl() ;
					context.getRequest().setAttribute("siteId",idAsl) ;
					context.getRequest().setAttribute("OrgDetails", proprietario);
		      //Load the departmentList for assigning
		  
		      
		      LookupList EsitiSequestri = new LookupList(db, "lookup_esiti_sequestri");
		      EsitiSequestri.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("EsitiSequestri", EsitiSequestri);
		      
		      
		     
		     
		     
		      LookupList SequestroDiStabilimento = new LookupList(db, "lookup_sequestri_amministrative_stabilimento");
		      SequestroDiStabilimento.setMultiple(true);
		      SequestroDiStabilimento.setSelectSize(5);
		      
		      //SequestroDi.addItem(-1,  "-- SELEZIONA VOCE --");
		     
		      
		      LookupList SequestroDiLocali = new LookupList(db, "lookup_sequestri_amministrative_locali");
		      SequestroDiLocali.setMultiple(true);
		      SequestroDiLocali.setSelectSize(5);
		      
		      //SequestroDi.addItem(-1,  "-- SELEZIONA VOCE --");
		      
		      
		      LookupList SequestroDiAttrezzature = new LookupList(db, "lookup_sequestri_amministrative_attrezzature");
		      SequestroDiAttrezzature.setMultiple(true);
		      SequestroDiAttrezzature.setSelectSize(5);
		      
		      //SequestroDi.addItem(-1,  "-- SELEZIONA VOCE --");
		    
		      
		      
		      LookupList SequestroDiAlimentiorigineAnimale = new LookupList(db, "lookup_sequestri_amministrative_alimentiorigineanimale");
		      SequestroDiAlimentiorigineAnimale.setMultiple(true);
		      SequestroDiAlimentiorigineAnimale.setSelectSize(5);
		      
		      //SequestroDi.addItem(-1,  "-- SELEZIONA VOCE --");
		    
		      LookupList SequestroDiAlimentiorigineVegetale = new LookupList(db, "lookup_sequestri_amministrative_alimentioriginevegetale");
		      SequestroDiAlimentiorigineVegetale.setMultiple(true);
		      SequestroDiAlimentiorigineVegetale.setSelectSize(5);
		      
		      //SequestroDi.addItem(-1,  "-- SELEZIONA VOCE --");
		    
		      
		      
		      LookupList SequestroDiAnimali = new LookupList(db, "lookup_sequestri_amministrative_animali");
		      SequestroDiAnimali.setMultiple(true);
		      SequestroDiAnimali.setSelectSize(5);
		      
		      //SequestroDi.addItem(-1,  "-- SELEZIONA VOCE --");
		     
		      
		      LookupList SequestroDiLocalieAttrezzature = new LookupList(db, "lookup_sequestri_amministrative_locali_attrezzature");
		      SequestroDiLocalieAttrezzature.setMultiple(true);
		      SequestroDiLocalieAttrezzature.setSelectSize(5);
		      
		      //SequestroDi.addItem(-1,  "-- SELEZIONA VOCE --");
		    
		      
		      
		      LookupList SequestroDivegetaleEanimale = new LookupList(db, "lookup_sequestri_amministrative_vegetale_animale");
		      SequestroDivegetaleEanimale.setMultiple(true);
		      SequestroDivegetaleEanimale.setSelectSize(5);
		      
		      //SequestroDi.addItem(-1,  "-- SELEZIONA VOCE --");
		      
		      
		      
		      LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
		      Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("Provvedimenti", Provvedimenti);
		      
		      LookupList SequestroDi = new LookupList(db, "lookup_sequestri_amministrative");
		   
		      LookupList multipleSelects=new LookupList();
		      
		      
		      
		      HashMap<Integer, String> ListaOggetti=newTic.getOggettiSequestrati();
		      Iterator<Integer> iteraKiavi= newTic.getOggettiSequestrati().keySet().iterator();
		      while(iteraKiavi.hasNext()){
		      int kiave=iteraKiavi.next();
		      String valore=ListaOggetti.get(kiave);
		    	  
		    	  multipleSelects.addItem(kiave,valore);
		    	  
		      }
		      
		      
		      if(newTic.getSequestroDi()==1){
		    	     
		   	      
		   	   SequestroDiStabilimento.setMultipleSelects(multipleSelects);
		    	  
		      }else{
		    	  if(newTic.getSequestroDi()==2){
		    		SequestroDiAttrezzature.setMultipleSelects(multipleSelects);
		        	  
		        	  
		          }else{
		        	  if(newTic.getSequestroDi()==3){
		            	SequestroDiLocali.setMultipleSelects(multipleSelects);  
		        		  
		            	  
		              }
		        	  else{
		        		  if(newTic.getSequestroDi()==4){
			            	SequestroDiLocalieAttrezzature.setMultipleSelects(multipleSelects);  
			            	  
			              }
		        		  else{
		        			  if(newTic.getSequestroDi()==5){
		    	            	SequestroDiAnimali.setMultipleSelects(multipleSelects);  

		    	            	  
		    	              }else{
		    	            	  if(newTic.getSequestroDi()==6){
		    		            	  SequestroDiAlimentiorigineAnimale.setMultipleSelects(multipleSelects);
		    	            		 
		    		            	  
		    		              }else{
		    		            	  if(newTic.getSequestroDi()==7){
		    		            	 SequestroDiAlimentiorigineVegetale.setMultipleSelects(multipleSelects);
		    		            		  
		    			              }else{
		    			            	  if(newTic.getSequestroDi()==8){
		    				            	  SequestroDivegetaleEanimale.setMultipleSelects(multipleSelects);
		    			            		
		    				            	  
		    				              }
		    			              }
		    		              }
		    	              }
		        		  }
		        	  }
		          }
		    	  
		      }
		     
		      context.getRequest().setAttribute("SequestroDivegetaleEanimale", SequestroDivegetaleEanimale);
		      
		      context.getRequest().setAttribute("SequestroDiLocalieAttrezzature", SequestroDiLocalieAttrezzature);
		      context.getRequest().setAttribute("SequestroDiAnimali", SequestroDiAnimali);
		      context.getRequest().setAttribute("SequestroDiLocali", SequestroDiLocali);
		      context.getRequest().setAttribute("SequestroDiStabilimento", SequestroDiStabilimento);
		      context.getRequest().setAttribute("SequestroDiAlimentiorigineAnimale", SequestroDiAlimentiorigineAnimale);
		      context.getRequest().setAttribute("SequestroDiAlimentiorigineVegetale", SequestroDiAlimentiorigineVegetale);
		      context.getRequest().setAttribute("SequestroDiAttrezzature", SequestroDiAttrezzature);
		      
		      
		      context.getRequest().setAttribute("SequestroDi", SequestroDi);
		      
		      LookupList AzioneNonConforme = new LookupList(db, "lookup_sequestri_penali");
		      
		      
		      AzioneNonConforme.setMultiple(true);
		      AzioneNonConforme.setSelectSize(7);
		      
		  
		      LookupList multipleSelects1=new LookupList();
		      
		      HashMap<Integer, String> ListaAzioni=newTic.getAzioniNonConformi();
		      Iterator<Integer> iteraKiaviAzioni= newTic.getAzioniNonConformi().keySet().iterator();
		      while(iteraKiaviAzioni.hasNext()){
		      int kiave=iteraKiaviAzioni.next();
		      String valore=ListaAzioni.get(kiave);
		    	  
		    	  multipleSelects1.addItem(kiave,valore);
		    	  
		      }
		      
		      AzioneNonConforme.setMultipleSelects(multipleSelects1);
		     
		      context.getRequest().setAttribute("AzioneNonConforme", AzioneNonConforme);
		      
		      LookupList Sequestri = new LookupList(db, "lookup_sequestri");
		      Sequestri.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("Sequestri", Sequestri);

		      LookupList SiteIdList = new LookupList(db, "lookup_site_id");
		      SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
		      SiteIdList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
		      context.getRequest().setAttribute("SiteIdList", SiteIdList);

		  
		    
		      //Put the ticket in the request
		      addRecentItem(context, newTic);
		      context.getRequest().setAttribute("TicketDetails", newTic);
		      addModuleBean(context, "View CaniPadronali", "View Tickets");

		      //getting current date in mm/dd/yyyy format
		      String currentDate = getCurrentDateAsString(context);
		      context.getRequest().setAttribute("currentDate", currentDate);

		    } catch (Exception errorMessage) {
		      context.getRequest().setAttribute("Error", errorMessage);
		      return ("SystemError");
		    } finally {
		      this.freeConnection(context, db);
		    }
		    //return "ModifyautorizzazionetrasportoanimaliviviOK";
		    return "ModifyOK";
		    //return getReturn(context, "ModifyTicket");
		  }
		  
	 
	 public String executeCommandUpdateTicket(ActionContext context) {
		    if (!hasPermission(context, "canipadronali-sequestri-edit")) {
		      return ("PermissionError");
		    }
		    Connection db = null;
		    int resultCount = 0;

		    int catCount = 0;
		    TicketCategory thisCat = null;
		    boolean catInserted = false;
		    boolean isValid = true;

		    Ticket newTic = (Ticket) context.getFormBean();
		    UserBean user = (UserBean) context.getSession().getAttribute("User");
		    String ip = user.getUserRecord().getIp();
		    newTic.setIpEntered(ip);
		    newTic.setIpModified(ip);
		    newTic.setTipo_richiesta( context.getRequest().getParameter( "tipo_richiesta" ) );
		    newTic.setDescrizione(context.getRequest().getParameter("descrizione"));
		  
		    String id = context.getRequest().getParameter("idC");
		  	context.getRequest().setAttribute("idC",
		  			id);
		  	
		  	String id2 = context.getRequest().getParameter("idNC");
		  	context.getRequest().setAttribute("idNC",
		  			id2);
		 
		    try {
		      db = this.getConnection(context);
		      
		      Ticket oldTic = new Ticket(db, newTic.getId());
		      
			  	String temporgId = context.getRequest().getParameter("orgId");
				int id_prop = Integer.parseInt(temporgId);
				OperazioniCaniDAO op = new OperazioniCaniDAO() ;
				op.setDb(db);
				Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(id)) ;
				int idAsl = -1 ;
				if (proprietario != null )
					idAsl = proprietario.getIdAsl() ;
					context.getRequest().setAttribute("siteId",idAsl) ;
					context.getRequest().setAttribute("OrgDetails", proprietario);
		      String oggettiSequestrati=  context.getRequest().getParameter("SequestroDi");
		      newTic.setSequestroDi(Integer.parseInt(oggettiSequestrati));
		     
		      
		      newTic.setTipo_richiesta( context.getRequest().getParameter( "tipo_richiesta" ) );
			    newTic.setCodiceArticolo(Integer.parseInt(context.getRequest().getParameter("articolo")));
			    newTic.setEsitoSequestro(Integer.parseInt( context.getRequest().getParameter("esitoSequestro")));   
		newTic.setNotaSequestro(context.getRequest().getParameter("sequestroNota"));
			newTic.setNoteSequestrodi(context.getRequest().getParameter("notesequestridi"));	   
			    
		      //Get the previousTicket, update the ticket, then send both to a hook
		      Ticket previousTicket = new Ticket(db, Integer.parseInt(context.getParameter("id")));
		      newTic.setModifiedBy(getUserId(context));
		      newTic.setSiteId(Integer.parseInt(context.getRequest().getParameter("siteId")));		
		      isValid = this.validateObject(context, db, newTic) && isValid;
		      if (isValid) {
		    	newTic.setTestoAppoggio("Modifica"); //aggiunto da d.dauria  
		        resultCount = newTic.update(db);
				
		      }
		      if (resultCount == 1) {
		        newTic.queryRecord(db, newTic.getId());
		        processUpdateHook(context, previousTicket, newTic);
//		        TicketCategoryList ticketCategoryList = new TicketCategoryList();
//		        ticketCategoryList.setEnabledState(Constants.TRUE);
//		        ticketCategoryList.setSiteId(newTic.getSiteId());
//		        ticketCategoryList.setExclusiveToSite(true);
//		        ticketCategoryList.buildList(db);
//		        context.getRequest().setAttribute("ticketCategoryList", ticketCategoryList);
		      }
		      Integer idtampone = newTic.getId();
	      
		      addModuleBean(context, "CaniPadronali CU", "CaniPadronali CU");
		    } catch (Exception e) {
		      context.getRequest().setAttribute("Error", e);
		      return ("SystemError");
		    } finally {
		      this.freeConnection(context, db);
		    }

		   
		    return (executeCommandTicketDetails(context));
		  }

	 

	  public String executeCommandChiudi(ActionContext context)
	  {
		  	    if (!(hasPermission(context, "canipadronali-sequestri-edit"))){
		      return ("PermissionError");
		    }
		    int resultCount = -1;
		    Connection db = null;
		    Ticket thisTicket = null;
		    try {
		      db = this.getConnection(context);
		      thisTicket = new Ticket(
		          db, Integer.parseInt(context.getRequest().getParameter("id")));
		      Ticket oldTicket = new Ticket(db, thisTicket.getId());
		      //check permission to record
		     
		      thisTicket.setModifiedBy(getUserId(context));
		      resultCount = thisTicket.chiudi(db);
		      if (resultCount == 0)
				{
					context.getRequest().setAttribute("Messaggio", "Chiusura non avvenuta assicurarsi di aver inserito l' Esito");
				}
		      
		      if (resultCount == -1 || resultCount == 0 ) {
		        return ( executeCommandTicketDetails(context));
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
	  
	  public String executeCommandConfirmDelete(ActionContext context) {
		    if (!hasPermission(context, "canipadronali-sequestri-delete")) {
		      return ("PermissionError");
		    }
		    Connection db = null;
		    //Parameters
		    String id = context.getRequest().getParameter("id");
		    //int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
		    try {
		      db = this.getConnection(context);
		      SystemStatus systemStatus = this.getSystemStatus(context);
		      Ticket ticket = new Ticket(db, Integer.parseInt(id));
		      int orgId = ticket.getOrgId();
		      //check permission to record
		   
			  	String temporgId = context.getRequest().getParameter("orgId");
				int id_prop = Integer.parseInt(temporgId);
				OperazioniCaniDAO op = new OperazioniCaniDAO() ;
				op.setDb(db);
				Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(ticket.getIdControlloUfficiale())) ;
				int idAsl = -1 ;
				if (proprietario != null )
					idAsl = proprietario.getIdAsl() ;
					context.getRequest().setAttribute("siteId",idAsl) ;
					context.getRequest().setAttribute("OrgDetails", proprietario);
		      DependencyList dependencies = ticket.processDependencies(db);
		      //Prepare the dialog based on the dependencies
		      HtmlDialog htmlDialog = new HtmlDialog();
		      dependencies.setSystemStatus(systemStatus);
		      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
		      htmlDialog.addMessage(
		          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
		      /*htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));*/
		      htmlDialog.addButton(
		          systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='CaniPadronaliSequestri.do?command=DeleteTicket&id=" + id + "&orgId=" + orgId + "&forceDelete=true" + RequestUtils.addLinkParams(
		          context.getRequest(), "popup|popupType|actionId") + "'");
		      htmlDialog.addButton(
		          systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
		      context.getSession().setAttribute("Dialog", htmlDialog);
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
		   * @param context Description of Parameter
		   * @return Description of the Returned Value
		   */
		  public String executeCommandDeleteTicket(ActionContext context) {
		    if (!hasPermission(context, "canipadronali-sequestri-delete")) {
		      return ("PermissionError");
		    }
		    boolean recordDeleted = false;
		    Connection db = null;
		    //Parameters
		    int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
		    String passedId = context.getRequest().getParameter("id");
		    try {
		      db = this.getConnection(context);
			  	String temporgId = context.getRequest().getParameter("orgId");
				int id_prop = Integer.parseInt(temporgId);
				OperazioniCaniDAO op = new OperazioniCaniDAO() ;
				op.setDb(db);
				 
				Ticket thisTic = new Ticket(db, Integer.parseInt(passedId));
		      //check permission to record
		     
		      String id_controllo=""+thisTic.getIdControlloUfficialeTicket();
				

		  	context.getRequest().setAttribute("idC", id_controllo);
		  	Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(id_controllo)) ;
			int idAsl = -1 ;
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
				context.getRequest().setAttribute("siteId",idAsl) ;
				context.getRequest().setAttribute("OrgDetails", proprietario);   
		  	recordDeleted = thisTic.logicdelete(db, getDbNamePath(context));
		  	if (recordDeleted) {
		  		processDeleteHook(context, thisTic);
		  		//del
		  		String inline = context.getRequest().getParameter("popupType");
		  		context
		  				.getRequest()
		  				.setAttribute(
		  						"refreshUrl",
		  						"CaniPadronaliNonConformita.do?command=TicketDetails&id="+id_controllo+"&orgId="
		  								+ orgId
		  								+ (inline != null
		  										&& "inline".equals(inline
		  												.trim()) ? "&popup=true"
		  										: ""));
		      }
		  	
		  	
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


		  
		  public String executeCommandReopenTicket(ActionContext context) {
			    if (!hasPermission(context, "canipadronali-sequestri-edit")) {
			      return ("PermissionError");
			    }
			    int resultCount = -1;
			    Connection db = null;
			    Ticket thisTicket = null;
			    Ticket oldTicket = null;
			    try {
			      db = this.getConnection(context);
			      thisTicket = new Ticket(
			          db, Integer.parseInt(context.getRequest().getParameter("id")));
			      oldTicket = new Ticket(db, thisTicket.getId());
			      //check permission to record
			    
			      thisTicket.setModifiedBy(getUserId(context));
			      resultCount = thisTicket.reopen(db);
			      thisTicket.queryRecord(db, thisTicket.getId());
			
			      
			    } catch (Exception errorMessage) {
			      context.getRequest().setAttribute("Error", errorMessage);
			      return ("SystemError");
			    } finally {
			      this.freeConnection(context, db);
			    }
			    if (resultCount == -1) {
			      return (executeCommandTicketDetails(context));
			    } else if (resultCount == 1) {
			      this.processUpdateHook(context, oldTicket, thisTicket);
			      return (executeCommandTicketDetails(context));
			    } else {
			      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
			      return ("UserError");
			    }
			  }
}
