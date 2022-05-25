/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.controlliufficiali.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import org.aspcfs.controller.SystemStatus;
import org.aspcf.modules.controlliufficiali.base.Organization;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanList;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.CategoryEditor;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.reati.base.Ticket;
import org.aspcfs.modules.troubletickets.base.TicketCategory;
//import org.aspcfs.modules.troubletickets.base.TicketCategoryList;
import org.aspcfs.modules.troubletickets.base.TicketDefect;
import org.aspcfs.modules.troubletickets.base.TicketDefectList;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class Reati extends CFSModule {

	public String executeCommandAdd(ActionContext context) {

		Connection db = null;
		Ticket newTic = null;
		try {
			db = this.getConnection(context);
			SystemStatus systemStatus = this.getSystemStatus(context);
			LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
			Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Provvedimenti", Provvedimenti);
			if (context.getRequest().getParameter("dataC") != null)
			{
				context.getRequest().setAttribute("dataC", context.getRequest().getParameter("dataC"));
			}
			else
			{
				context.getRequest().setAttribute("dataC", context.getRequest().getAttribute("dataC"));
			}			LookupList ReatiAmministrative = new LookupList(db, "lookup_reati_amministrative");
			ReatiAmministrative.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("ReatiAmministrative", ReatiAmministrative);

			LookupList ReatiPenali = new LookupList(db, "lookup_reati_penali");
			ReatiPenali.addItem(-1, "-- SELEZIONA VOCE --");
			ReatiPenali.setMultiple(true);
			ReatiPenali.setSelectSize(7);

			context.getRequest().setAttribute("ReatiPenali", ReatiPenali);


			String id = context.getRequest().getParameter("idC");
			context.getRequest().setAttribute("idC",id);
			org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket();
			cu.queryRecord(db, Integer.parseInt(id));
			context.getRequest().setAttribute("CU", cu);
			context.getRequest().setAttribute("id_asl",cu.getSiteId());
			
			LookupList Sequestri = new LookupList(db, "lookup_sequestri");
			Sequestri.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Sequestri", Sequestri);

			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			SiteIdList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			//Load the organization
			Organization thisOrganization = new Organization(db, Integer.parseInt(id));
			if (context.getParameter("orgId")==null && context.getParameter("stabId")!=null)
			{
				thisOrganization.setIdStabilimento(Integer.parseInt(context.getParameter("stabId")));
				thisOrganization.setOrgId(-1);
			
			}
			context.getRequest().setAttribute("OrgDetails", thisOrganization);
			String idControlloUfficiale = context.getRequest().getParameter("idControllo");
			String idC = context.getRequest().getParameter("idC");
			context.getRequest().setAttribute("idControllo",idControlloUfficiale);
			context.getRequest().setAttribute("idC",idC);
			context.getRequest().setAttribute("TipoNC",context.getRequest().getParameter("tipoNc")); 

			//getting current date in mm/dd/yyyy format
			String currentDate = getCurrentDateAsString(context);
			context.getRequest().setAttribute("currentDate", currentDate);
			context.getRequest().setAttribute(
					"systemStatus", this.getSystemStatus(context));
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
		
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "AddTicket", "Ticket Add");
		if (context.getRequest().getParameter("actionSource") != null) {
			context.getRequest().setAttribute(
					"actionSource", context.getRequest().getParameter("actionSource"));
			return "AddTicketOK";
		}
		context.getRequest().setAttribute(
				"systemStatus", this.getSystemStatus(context));
		return ("AddOK");
	}


	public String executeCommandInsert(ActionContext context) throws SQLException {

		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = true;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Ticket newTicket = null;
		String id = context.getRequest().getParameter("idC");
		context.getRequest().setAttribute("idC",id);
		Ticket newTic = (Ticket) context.getFormBean();
		context.getRequest().setAttribute("dataC", ""+newTic.getAssignedDate());
		newTic.setTipo_nc(Integer.parseInt(context.getRequest().getParameter("tipoNc")));
		String idControlloUfficiale = (String.valueOf(Integer.parseInt(id)));
		while (idControlloUfficiale.length() < 6) {
			idControlloUfficiale = "0" + idControlloUfficiale;
		}
		UserBean user = (UserBean) context.getSession().getAttribute("User");
	    String ip = user.getUserRecord().getIp();
	    newTic.setIpEntered(ip);
	    newTic.setIpModified(ip);
	    
	   
	    if (context.getParameter("altId") != null && !context.getParameter("altId").equals("null"))
			newTic.setAltId(Integer.parseInt(context.getParameter("altId")));
		
		if (context.getParameter("stabId") != null && !context.getParameter("stabId").equals("null")){
			newTic.setIdStabilimento(Integer.parseInt(context.getParameter("stabId")));
		}
		else if(newTic.getIdStabilimento() > 0 && newTic.getIdApiario() > 0){
			newTic.setIdStabilimento(0);
		}
		newTic.setIdControlloUfficiale(idControlloUfficiale);
		newTic.setNormaviolata(context.getRequest().getParameter("normaviolata"));
		String site =  context.getRequest().getParameter("siteId");
		if(site.equals("201")){
			site = "AV";		
		}else if(site.equals("202")){
			site = "BN";
		}else if(site.equals("203")){
			site = "CE";
		}else if(site.equals("204")){
			site = "NA1C";
		}else if(site.equals("205")){
			site = "NA2N";
		}else if(site.equals("206")){
			site = "NA3S";
		}else if(site.equals("207")){
			site = "SA";
		}/*else if(site.equals("8")){
			site = "NA3";
		}else if(site.equals("9")){
			site = "NA4";
		}else if(site.equals("10")){
			site = "NA5";
		}else if(site.equals("11")){
			site = "SA1";
		}else if(site.equals("12")){
			site = "SA2";
		}else if(site.equals("13")){
			site = "SA3";
		}*/
		else{
			if(site.equals(16))
			{
				site ="FuoriRegione";
			}
		}
		String idControllo = context.getRequest().getParameter("idControlloUfficiale");
		String idC = context.getRequest().getParameter("idC");
		String idCampione = site+idControllo;
		newTic.setTipo_richiesta( context.getRequest().getParameter( "tipo_richiesta" ) );
		newTic.setEnteredBy(getUserId(context));
		newTic.setModifiedBy(getUserId(context));

		try {
			db = this.getConnection(context);
			 org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket();
				cu.queryRecord(db, Integer.parseInt(id));
		cu.controlloBloccoCu(db,cu.getId());
				
				cu.setFlagBloccoNonConformita(db, cu.getId());
				if(cu.isflagBloccoCu()==true && cu.isFlagBloccoNonConformita()==true)
				{
					newTic.setFlag_posticipato(true);
					newTic.setFlag_campione_non_conforme(false);
				}
				else
				{
					if(cu.isflagBloccoCu()==true && cu.isFlagBloccoNonConformita()==false)
					{
						newTic.setFlag_posticipato(true);
						newTic.setFlag_campione_non_conforme(true);
					}
				}
				
			if (cu.getAltId()>0)
			{
				newTic.setAltId(cu.getAltId());
			}
			
			newTic.setAssignedDate(cu.getAssignedDate());
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
			isValid = this.validateObject(context, db, newTic) && isValid;
			if (isValid) {
				newTic.setTestoAppoggio("Inserimento"); //aggiunto da d.dauria
				recordInserted = newTic.insert(db,context);
				newTic.insertIllecitiPenali(db, context.getRequest().getParameterValues("ReatiPenali"));
			}

			if (recordInserted) {
				newTicket = new Ticket(db, newTic.getId());
				context.getRequest().setAttribute("TicketDetails", newTicket);
			}


		
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		if (!"null".equals(context.getRequest().getParameter("inseriti")) && context.getRequest().getParameter("inseriti")!=null)
		{
			  if (! "".equals(context.getRequest().getParameter("inseriti")))
			  {
				  context.getRequest().setAttribute("inserito",context.getRequest().getParameter("inseriti")+";"+newTicket.getId());
			  }
			  else
			  {
				  context.getRequest().setAttribute("inserito",newTicket.getId());
			  }
			String tipo_nc = context.getRequest().getParameter("tipoNc");
	
			if (tipo_nc.equals("3"))
			{
				
				if(! "".equals(context.getRequest().getParameter("attivita_gravi_inseriti")))
		    	{
				
					context.getRequest().setAttribute("attivita_gravi_inseriti",context.getRequest().getParameter("attivita_gravi_inseriti")+ ";"+newTicket.getId()+"-rea");

		    	}
		    	else
		    	{
			    	 context.getRequest().setAttribute("attivita_gravi_inseriti",newTicket.getId()+"-rea");

		    	}
			 }
			    	 
			    	 
			    
			
		}
		else
		{
			context.getRequest().setAttribute("inserito", ""+newTicket.getId()+";");

		}
		context.getRequest().setAttribute("TipoNC",context.getRequest().getParameter("tipoNc"));
		return executeCommandAdd(context); 
	}

	 public String executeCommandModifyTicket(ActionContext context) {
		   			
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

				org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket(db,Integer.parseInt(newTic.getIdControlloUfficiale()));
				context.getRequest().setAttribute("CU", cu);
		      
		      //Put the ticket in the request
		      addRecentItem(context, newTic);
		      context.getRequest().setAttribute("TicketDetails", newTic);
		      addModuleBean(context, "View Accounts", "View Tickets");

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
		    return "";
		    //return getReturn(context, "ModifyTicket");
		  }
	 
	 
	 public String executeCommandUpdateTicket(ActionContext context) {
		    
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
		      org.aspcfs.modules.vigilanza.base.Ticket cu =new org.aspcfs.modules.vigilanza.base.Ticket(db,Integer.parseInt(id));
				newTic.setAssignedDate(cu.getAssignedDate());
		      Ticket oldTic = new Ticket(db, newTic.getId());
		      
		      LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
		      Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("Provvedimenti", Provvedimenti);
		        //Get the previousTicket, update the ticket, then send both to a hook
		      Ticket previousTicket = new Ticket(db, Integer.parseInt(context.getParameter("id")));
		      newTic.setModifiedBy(getUserId(context));
		      newTic.setSiteId(context.getParameter("siteId"));
		      //isValid = this.validateObject(context, db, newTic) && isValid;
		      isValid = true;
		      
		     newTic.setTipo_richiesta(context.getRequest().getParameter("tipo_richiesta"));
		      if (isValid) {
		    	newTic.setTestoAppoggio("Modifica"); //aggiunto da d.dauria  
		        resultCount = newTic.update(db);
		        newTic.updateIllecitiPenali(db, context.getRequest().getParameterValues("ReatiPenali"));
		      }
		      if (resultCount == 1) {
		        newTic.queryRecord(db, newTic.getId());
		       
		      }
		      Integer idtampone = newTic.getId();
//				BeanSaver.save( null, newTic, newTic.getId(),
//			      		"ticket", context, db, null, "O.S.A: Modifica Reato", idtampone.toString() );
		      
				      
		      
		      
		    } catch (Exception e) {
		      context.getRequest().setAttribute("Error", e);
		      return ("SystemError");
		    } finally {
		      this.freeConnection(context, db);
		    }

		   
		    return "";
		  }
	 
	 
	 
	 public String executeCommandTicketDetails(ActionContext context) {
		   
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
		     
		      addModuleBean(context, "View Accounts", "View Tickets");
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
		    return "";
		  }




}
