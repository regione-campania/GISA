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
import org.aspcfs.modules.reati.base.Ticket;
import org.aspcfs.modules.troubletickets.base.TicketCategory;
//import org.aspcfs.modules.troubletickets.base.TicketCategoryList;
import org.aspcfs.modules.troubletickets.base.TicketDefect;
import org.aspcfs.modules.troubletickets.base.TicketDefectList;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.RequestUtils;

import com.darkhorseventures.framework.actions.ActionContext;

public class CaniPadronaliReati extends CFSModule {
	
	public String executeCommandTicketDetails(ActionContext context) {
	    if (!hasPermission(context, "canipadronali-reati-view")) {
	      return ("PermissionError");
	    }
	    Connection db = null;
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

	      //find record permissions for portal users
	      
	    

	   
	      
	      LookupList SiteIdList = new LookupList(db, "lookup_site_id");
	      SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SiteIdList", SiteIdList);
	      
	      LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
	      Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("Provvedimenti", Provvedimenti);
	      
	      LookupList ReatiAmministrative = new LookupList(db, "lookup_reati_amministrative");
	      ReatiAmministrative.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("ReatiAmministrative", ReatiAmministrative);
	      
	      LookupList ReatiPenali = new LookupList(db, "lookup_reati_penali");
	      ReatiPenali.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("ReatiPenali", ReatiPenali);
	      
	      LookupList Sequestri = new LookupList(db, "lookup_sequestri");
	      Sequestri.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("Sequestri", Sequestri);

	   
	      context.getRequest().setAttribute("TicketDetails", newTic);
	      
	      retPag = "DettaglioOK";
	      
	      addRecentItem(context, newTic);
	      // Load the organization for the header
		  	String temporgId = context.getRequest().getParameter("orgId");
			int id_prop = Integer.parseInt(temporgId);
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			Proprietario  proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(id)) ;
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
		    if (!hasPermission(context, "canipadronali-reati-edit")) {
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
		      //check permission to record
		     
		      
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
				Proprietario  proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(id)) ;
				int idAsl = -1 ;
				if (proprietario != null )
					idAsl = proprietario.getIdAsl() ;
				context.getRequest().setAttribute("siteId",idAsl) ;
				context.getRequest().setAttribute("OrgDetails", proprietario);
		      //Load the departmentList for assigning
		     
		      
		      
		      LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
		      Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("Provvedimenti", Provvedimenti);
		      
		      LookupList ReatiAmministrative = new LookupList(db, "lookup_reati_amministrative");
		      ReatiAmministrative.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("ReatiAmministrative", ReatiAmministrative);
		      
		      LookupList ReatiPenali = new LookupList(db, "lookup_reati_penali");
		      ReatiPenali.addItem(-1, "-- SELEZIONA VOCE --");
		      
		     ReatiPenali.setMultiple(true);
		      ReatiPenali.setSelectSize(7);
		      LookupList multipleSelects=new LookupList();
		 
		      HashMap<Integer, String> ListaIllecitiPenali=newTic.getIllecitiPenali();
		      Iterator<Integer> iteraKiavi= newTic.getIllecitiPenali().keySet().iterator();
		      while(iteraKiavi.hasNext()){
		      int kiave=iteraKiavi.next();
		      String valore=ListaIllecitiPenali.get(kiave);
		    	  
		    	  multipleSelects.addItem(kiave,valore);
		    	  
		      }
		      
		      ReatiPenali.setMultipleSelects(multipleSelects);
		      
		      
		      context.getRequest().setAttribute("ReatiPenali", ReatiPenali);
		      
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
		    if (!hasPermission(context, "canipadronali-reati-edit")) {
		      return ("PermissionError");
		    }
		    Connection db = null;
		    int resultCount = 0;

		    int catCount = 0;
		    TicketCategory thisCat = null;
		    boolean catInserted = false;
		    boolean isValid = true;

		    Ticket  newTic = (Ticket) context.getFormBean();
		    UserBean user = (UserBean) context.getSession().getAttribute("User");
		    String ip = user.getUserRecord().getIp();
		    newTic.setIpEntered(ip);
		    newTic.setIpModified(ip);
		     
		    newTic.setNormaviolata(context.getRequest().getParameter("normaviolata"));
		   
		    String id = context.getRequest().getParameter("idC");
		  	context.getRequest().setAttribute("idC",
		  			id);
		  	
		  	String id2 = context.getRequest().getParameter("idNC");
		  	context.getRequest().setAttribute("idNC",
		  			id2);
		    try {
		      db = this.getConnection(context);
		      
		      Ticket oldTic = new Ticket(db, newTic.getId());
		      
		      LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
		      Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("Provvedimenti", Provvedimenti);
		      
			  	String temporgId = context.getRequest().getParameter("orgId");
				int id_prop = Integer.parseInt(temporgId);
				OperazioniCaniDAO op = new OperazioniCaniDAO() ;
				op.setDb(db);
				Proprietario  proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(id)) ;
				int idAsl = -1 ;
				if (proprietario != null )
					idAsl = proprietario.getIdAsl() ;
				context.getRequest().setAttribute("siteId",idAsl) ;
				context.getRequest().setAttribute("OrgDetails", proprietario);
		      //check permission to record
		   
		      
		      
		      //Get the previousTicket, update the ticket, then send both to a hook
		      Ticket previousTicket = new Ticket(db, Integer.parseInt(context.getParameter("id")));
		      newTic.setModifiedBy(getUserId(context));
		      newTic.setSiteId(Integer.parseInt(context.getRequest().getParameter("siteId")));		      isValid = this.validateObject(context, db, newTic) && isValid;
		     newTic.setTipo_richiesta(context.getRequest().getParameter("tipo_richiesta"));
		      if (isValid) {
		    	newTic.setTestoAppoggio("Modifica"); //aggiunto da d.dauria  
		        resultCount = newTic.update(db);
		        newTic.updateIllecitiPenali(db, context.getRequest().getParameterValues("ReatiPenali"));
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
//				BeanSaver.save( null, newTic, newTic.getId(),
//			      		"ticket", context, db, null, "O.S.A: Modifica Reato", idtampone.toString() );
		       
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
		  	    if (!(hasPermission(context, "canipadronali-reati-edit"))){
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
		      if (resultCount == -1) {
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
		    if (!hasPermission(context, "canipadronali-reati-delete")) {
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
		      if (!isRecordAccessPermitted(context, db, ticket.getOrgId())) {
		        return ("PermissionError");
		      }
			  	String temporgId = context.getRequest().getParameter("orgId");
				int id_prop = Integer.parseInt(temporgId);
				OperazioniCaniDAO op = new OperazioniCaniDAO() ;
				op.setDb(db);
				Proprietario  proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(ticket.getIdControlloUfficiale())) ;
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
		          systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='CaniPadronaliReati.do?command=DeleteTicke&id=" + id + "&orgId=" + orgId + "&forceDelete=true" + RequestUtils.addLinkParams(
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
		    if (!hasPermission(context, "canipadronali-reati-delete")) {
		      return ("PermissionError");
		    }
		    boolean recordDeleted = false;
		    Connection db = null;
		    //Parameters
		    int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
		    String passedId = context.getRequest().getParameter("id");
		    try {
		      db = this.getConnection(context);
		      Organization newOrg = new Organization(db, orgId);
		      Ticket thisTic = new Ticket(db, Integer.parseInt(passedId));
		      //check permission to record
		      if (!isRecordAccessPermitted(context, db, thisTic.getOrgId())) {
		        return ("PermissionError");
		      }
		      String id_controllo=""+thisTic.getIdControlloUfficialeTicket();
				

		  	context.getRequest().setAttribute("idC", id_controllo);
		  	String temporgId = context.getRequest().getParameter("orgId");
			int id_prop = Integer.parseInt(temporgId);
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
		  		context.getRequest().setAttribute("OrgDetails", newOrg);
		  		context
		  				.getRequest()
		  				.setAttribute(
		  						"refreshUrl",
		  						"CaniPadronaliNonConformita.do?command=TicketDetails&&id="+id_controllo+"&orgId="
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
			    if (!hasPermission(context, "canipadronali-reati-edit")) {
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
