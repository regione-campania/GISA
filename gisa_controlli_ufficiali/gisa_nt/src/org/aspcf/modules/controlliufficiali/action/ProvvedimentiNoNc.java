/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.controlliufficiali.action;

import java.sql.Connection;
import java.sql.SQLException;

import org.aspcfs.controller.SystemStatus;
import org.aspcf.modules.controlliufficiali.base.Organization;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanList;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.CategoryEditor;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;

import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.sanzioni.base.Ticket;
import org.aspcfs.modules.troubletickets.base.TicketCategory;
//import org.aspcfs.modules.troubletickets.base.TicketCategoryList;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class ProvvedimentiNoNc  extends CFSModule {
 
	public String executeCommandAdd(ActionContext context,Connection db ) {
		
		Ticket newTic = null;
		try {
			SystemStatus systemStatus = this.getSystemStatus(context);
			LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti_no_nc");
			Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
			Provvedimenti.setMultiple(true);
			Provvedimenti.setSelectSize(7);
			context.getRequest().setAttribute("Provvedimenti", Provvedimenti);
			String id = context.getRequest().getParameter("idC");
			context.getRequest().setAttribute("idC",id);
			org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket();
			cu.queryRecord(db, Integer.parseInt(id));
			context.getRequest().setAttribute("CU", cu);
			context.getRequest().setAttribute("id_asl",cu.getSiteId());
			
			if (context.getRequest().getParameter("dataC") != null)
			{
				context.getRequest().setAttribute("dataC", context.getRequest().getParameter("dataC"));
			}
			else
			{
				context.getRequest().setAttribute("dataC", context.getRequest().getAttribute("dataC"));
			}

			LookupList SanzioniAmministrative = new LookupList(db, "lookup_sanzioni_amministrative");
			SanzioniAmministrative.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SanzioniAmministrative", SanzioniAmministrative);

			
			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			SiteIdList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);
			String idControlloUfficiale = context.getRequest().getParameter("idControllo");
			String idC = context.getRequest().getParameter("idC");
			context.getRequest().setAttribute("idControllo",idControlloUfficiale);
			context.getRequest().setAttribute("idC",idC);

			Organization thisOrganization = new Organization(db, Integer.parseInt(id));
			context.getRequest().setAttribute("OrgDetails", thisOrganization);
			context.getRequest().setAttribute("TipoNC",context.getRequest().getParameter("tipoNc"));

			String temporgId = context.getRequest().getParameter("orgId");
			//getting current date in mm/dd/yyyy format
			String currentDate = getCurrentDateAsString(context);
			context.getRequest().setAttribute("currentDate", currentDate);
			context.getRequest().setAttribute(
					"systemStatus", this.getSystemStatus(context));
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			
			return ("SystemError");
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

	public String executeCommandInsert(ActionContext context,Connection db ) throws SQLException {
			
		boolean recordInserted = false;
		boolean isValid = true;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Ticket newTicket = null;
		Ticket newTic = (Ticket) context.getFormBean();
		UserBean user = (UserBean) context.getSession().getAttribute("User");
	    String ip = user.getUserRecord().getIp();
	    newTic.setIpEntered(ip);
	    newTic.setIpModified(ip);
		String id = context.getRequest().getParameter("idC");
		context.getRequest().setAttribute("idC",id);
		String id2 = context.getRequest().getParameter("idNC");
		context.getRequest().setAttribute("idNC",id2);
		context.getRequest().setAttribute("dataC", ""+newTic.getAssignedDate());
		newTic.setTipo_nc(Integer.parseInt(context.getRequest().getParameter("tipoNc")));

		String idControlloUfficiale = (String.valueOf(Integer.parseInt(id)));
		while (idControlloUfficiale.length() < 6) {
			idControlloUfficiale = "0" + idControlloUfficiale;
		}
		newTic.setIdControlloUfficiale(idControlloUfficiale);



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
		//newTic.setIdControlloUfficiale(context.getRequest().getParameter("idControlloUfficiale"));

		String idCampione = site+idControllo;
		newTic.setTipo_richiesta( context.getRequest().getParameter( "tipo_richiesta" ) );
		newTic.setEnteredBy(getUserId(context));
		newTic.setModifiedBy(getUserId(context));
			newTic.setTrasgressore(context.getRequest().getParameter("trasgressore"));


		newTic.setObbligatoinSolido(context.getRequest().getParameter("obbligatoinSolido"));

		if(!context.getRequest().getParameter("pagamento").equals(""))
			newTic.setPagamento(Double.parseDouble(context.getRequest().getParameter("pagamento")));



		try {

			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
			Provvedimenti.setMultiple(true);
			Provvedimenti.setSelectSize(7);

			context.getRequest().setAttribute("Provvedimenti", Provvedimenti);

			
			isValid = this.validateObject(context, db, newTic) && isValid;
			if (isValid) {
				newTic.setTestoAppoggio("Inserimento"); //aggiunto da d.dauria
				recordInserted = newTic.insert(db,context);
				newTic.insertAzioniNonConforme(db, context.getRequest().getParameterValues("Provvedimenti"));
			}


			if (recordInserted) {

				newTicket = new Ticket(db, newTic.getId());
				context.getRequest().setAttribute("TicketDetails", newTicket);

				addRecentItem(context, newTicket);

				processInsertHook(context, newTicket);
			} else {
				if (newTic.getOrgId() != -1) {
					Organization thisOrg = new Organization(db, newTic.getOrgId());
					newTic.setCompanyName(thisOrg.getName());
				}
			}

		

		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 
		addModuleBean(context, "ViewTickets", "Ticket Insert ok");
		if (recordInserted && isValid) {
			if (context.getRequest().getParameter("actionSource") != null) {
				context.getRequest().setAttribute(
						"actionSource", context.getRequest().getParameter("actionSource"));
				return "InsertTicketOK";
			}
			String retPage = "DettaglioOK";
			String tipo_richiesta = newTic.getTipo_richiesta();
			String obbligato=newTic.getObbligatoinSolido();
			String trasgressore=newTic.getTrasgressore();
			tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);
			obbligato = (obbligato == null) ? ("") : (obbligato);
			trasgressore = (trasgressore == null) ? ("") : (trasgressore);

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
					
						context.getRequest().setAttribute("attivita_gravi_inseriti",context.getRequest().getParameter("attivita_gravi_inseriti")+ ";"+newTicket.getId()+"-san");

			    	}
			    	else
			    	{
				    	 context.getRequest().setAttribute("attivita_gravi_inseriti",newTicket.getId()+"-san");

			    	}
				}
				    	
			}
			else
			{
				context.getRequest().setAttribute("inserito", ""+newTicket.getId()+";");

			}
			context.getRequest().setAttribute("TipoNC",context.getRequest().getParameter("tipoNc"));

		}
		return executeCommandAdd(context,db); 
	}
	


	  /**
	   * Loads the ticket for modification
	   *
	   * @param context Description of Parameter
	   * @return Description of the Returned Value
	   */
	  public String executeCommandModifyTicket(ActionContext context,Connection db ){
	   			
	    Ticket newTic = null;
	    //Parameters
	    String ticketId = context.getRequest().getParameter("id");
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    try {
	      User user = this.getUser(context, this.getUserId(context));
	      //Load the ticket
	      if (context.getRequest().getParameter("companyName") == null) {
	        newTic = new Ticket(db, Integer.parseInt(ticketId));
	      } else {
	        newTic = (Ticket) context.getFormBean();
	      }
	      //check permission to record
	     

	      String id = context.getRequest().getParameter("idC");
	    	context.getRequest().setAttribute("idC",id);
	    	
	    	String id2 = context.getRequest().getParameter("idNC");
	    	context.getRequest().setAttribute("idNC",
	    			id2);
	      //Load the organization
	      Organization thisOrganization = new Organization(db, newTic.getOrgId());
	      context.getRequest().setAttribute("OrgDetails", thisOrganization);
	      //Load the departmentList for assigning
	     
	      
	      LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
	      
	      Provvedimenti.addItem(-1,"--SELEZIONARE UNA O PIU VOCI");

	      context.getRequest().setAttribute("Provvedimenti", Provvedimenti);
	      
	     
	      LookupList SiteIdList = new LookupList(db, "lookup_site_id");
	      SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
	      SiteIdList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
	      context.getRequest().setAttribute("SiteIdList", SiteIdList);

	      
	    
	      //Put the ticket in the request
	      addRecentItem(context, newTic);
	      context.getRequest().setAttribute("TicketDetails", newTic);
	      addModuleBean(context, "View Accounts", "View Tickets");
	      LookupList isp = new LookupList(db, "lookup_ispezione");
	      isp.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("Ispezioni", isp);
	  
	  	org.aspcfs.modules.vigilanza.base.Ticket CU = new org.aspcfs.modules.vigilanza.base.Ticket(db,Integer.parseInt(newTic.getIdControlloUfficiale()));
		context.getRequest().setAttribute("CU",CU);
	      //getting current date in mm/dd/yyyy format
	      String currentDate = getCurrentDateAsString(context);
	      context.getRequest().setAttribute("currentDate", currentDate);

	    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      return ("SystemError");
	    } 
	    //return "ModifyautorizzazionetrasportoanimaliviviOK";
	    return "";
	    //return getReturn(context, "ModifyTicket");
	  }
	  
	  
	  public String executeCommandUpdateTicket(ActionContext context,Connection db) {
		
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
		    String id = context.getRequest().getParameter("idC");
		  	context.getRequest().setAttribute("idC",
		  			id);
		  	
		  	String id2 = context.getRequest().getParameter("idNC");
		  	context.getRequest().setAttribute("idNC",
		  			id2);
		   
		    newTic.setTipo_richiesta( context.getRequest().getParameter( "tipo_richiesta" ) );
		    newTic.setPunteggio(25);
		    newTic.setTrasgressore(context.getRequest().getParameter("trasgressore"));
		    newTic.setTrasgressore2(context.getRequest().getParameter("trasgressore2"));
		    newTic.setTrasgressore3(context.getRequest().getParameter("trasgressore3"));
		    newTic.setObbligatoinSolido(context.getRequest().getParameter("obbligato"));
		    newTic.setObbligatoinSolido2(context.getRequest().getParameter("obbligatoinSolido2"));
		    newTic.setObbligatoinSolido3(context.getRequest().getParameter("obbligatoinSolido3"));
		   newTic.setNormaviolata(context.getRequest().getParameter("normaviolata"));
		    if(!context.getRequest().getParameter("pagamento").equals(""))
		    	newTic.setPagamento(Double.parseDouble(context.getRequest().getParameter("pagamento")));
	
		  

		    try {
		      
		      Ticket oldTic = new Ticket(db, newTic.getId());
		      
		     
		    
		      //Get the previousTicket, update the ticket, then send both to a hook
		      Ticket previousTicket = new Ticket(db, Integer.parseInt(context.getParameter("id")));
		      newTic.setModifiedBy(getUserId(context));
		      //newTic.setSiteId(Organization.getOrganizationSiteId(db, newTic.getOrgId()));
		      isValid = this.validateObject(context, db, newTic) && isValid;
		      if (isValid) {
		    	newTic.setTestoAppoggio("Modifica"); //aggiunto da d.dauria  
		        resultCount = newTic.update(db);
		        newTic.updateAzioniNonConforme(db, context.getRequest().getParameterValues("Provvedimenti"));
		      }
		    
		      Integer idtampone = newTic.getId();
		      
//				BeanSaver.save( null, newTic, newTic.getId(),
//			      		"ticket", context, db, null, "O.S.A: Modifica Sanzioni", idtampone.toString() );
				
				      

		    } catch (Exception e) {
		      context.getRequest().setAttribute("Error", e);
		      return ("SystemError");
		    } 

		   
		    return "";
		  }

	  public String executeCommandTicketDetails(ActionContext context,Connection db) {
		  
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
		    } 
		    //return getReturn(context, "TicketDetails");
		    return retPag;
		  }

}
