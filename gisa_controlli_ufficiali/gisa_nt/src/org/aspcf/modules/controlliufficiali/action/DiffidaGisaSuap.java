/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.controlliufficiali.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.aspcfs.controller.SystemStatus;
import org.aspcf.modules.controlliufficiali.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.CategoryEditor;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;

import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.nonconformita.base.ElementoNonConformita;
import org.aspcfs.modules.suap.base.Stabilimento;
import org.aspcfs.modules.diffida.base.Ticket;
import org.aspcfs.modules.diffida.base.TicketList;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.RequestUtils;

import com.darkhorseventures.framework.actions.ActionContext;

public class DiffidaGisaSuap  extends CFSModule {
	
	

	
	
	 public String executeCommandChiudi(ActionContext context)
	  {
		  	  
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
	 
	 
	 public String executeCommandReopenTicket(ActionContext context) {
		   
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
	 
	  public String executeCommandConfirmDelete(ActionContext context) {
	    
	    Connection db = null;
	    //Parameters
	    String id = context.getRequest().getParameter("id");
	    //int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
	    try {
	      db = this.getConnection(context);
	      SystemStatus systemStatus = this.getSystemStatus(context);
	      Ticket ticket = new Ticket(db, Integer.parseInt(id));
	      int altId = ticket.getAltId();
	      //check permission to record
	    
	      Stabilimento st = new Stabilimento();
	      st.getPrefissoAction(context.getAction().getActionName());
	      DependencyList dependencies = ticket.processDependencies(db);
	      //Prepare the dialog based on the dependencies
	      HtmlDialog htmlDialog = new HtmlDialog();
	      dependencies.setSystemStatus(systemStatus);
	      htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
	      htmlDialog.addMessage(
	          systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
	      /*htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));*/
	      htmlDialog.addButton(
	          systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='"+context.getAction().getActionName()+".do?command=DeleteTicket&id=" + id + "&altId=" + altId + "&forceDelete=true" + RequestUtils.addLinkParams(
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
	   * @return D	escription of the Returned Value
	   */
	  public String executeCommandDeleteTicket(ActionContext context) {
	   
	    boolean recordDeleted = false;
	    Connection db = null;
	    //Parameters
	    int altId = Integer.parseInt(context.getRequest().getParameter("altId"));
	    String passedId = context.getRequest().getParameter("id");
	    try {
	      db = this.getConnection(context);
	      Stabilimento newOrg = new Stabilimento(db, altId, true);
	      Ticket thisTic = new Ticket(db, Integer.parseInt(passedId));
	      //check permission to record
	      newOrg.getPrefissoAction(context.getAction().getActionName());
	     
	      String id_controllo=""+thisTic.getIdControlloUfficialeTicket();
			

		context.getRequest().setAttribute("idC", id_controllo);
		
		recordDeleted = thisTic.delete(db, getDbNamePath(context));
		if (recordDeleted) {
			processDeleteHook(context, thisTic);
			String inline = context.getRequest().getParameter("popupType");
			context.getRequest().setAttribute("OrgDetails", newOrg);
			context
					.getRequest()
					.setAttribute(
							"refreshUrl",
							newOrg.getAction()+"NonConformita.do?command=TicketDetails&id="+thisTic.getId_nonconformita()+"&altId="
	  								+ altId
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
	  
	  
	
	public String executeCommandAdd(ActionContext context) {
		
		Connection db = null;
		Ticket newTic = null;
		try {
			db = this.getConnection(context);
		
			LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
			Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
			//Provvedimenti.setMultiple(true);
			//Provvedimenti.setSelectSize(7);
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

			LookupList listaNorme = new LookupList(db,"lookup_norme","ordinamento");
			listaNorme.removeItemfromLookupByCode(db,"lookup_norme", "trashed_date is not null");
			listaNorme.addItem(-1,"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("ListaNorme",listaNorme);
			
			LookupList SanzioniPenali = new LookupList(db, "lookup_sanzioni_penali");
			SanzioniPenali.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SanzioniPenali", SanzioniPenali);

			LookupList Sequestri = new LookupList(db, "lookup_sequestri");
			Sequestri.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Sequestri", Sequestri);

			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			SiteIdList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);
			String idControlloUfficiale = context.getRequest().getParameter("idControllo");
			String idC = context.getRequest().getParameter("idC");
			context.getRequest().setAttribute("idControllo",idControlloUfficiale);
			context.getRequest().setAttribute("idC",idC);

			Stabilimento thisOrganization = new Stabilimento();
			if (context.getParameter("altId")==null && context.getParameter("altId")!=null)
			{
				thisOrganization.setAltId(Integer.parseInt(context.getParameter("altId")));
				thisOrganization.getPrefissoAction(context.getAction().getActionName());
				
			}
			context.getRequest().setAttribute("OrgDetails", thisOrganization);
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
		Ticket newTic = (Ticket) context.getFormBean();
		newTic.setIdStabilimento(context.getParameter("stabId"));
		UserBean user = (UserBean) context.getSession().getAttribute("User");
	    String ip = user.getUserRecord().getIp();
	  
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
		}
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
		
		newTic.setEnteredBy(getUserId(context));
		newTic.setModifiedBy(getUserId(context));


		try {
			db = this.getConnection(context);

			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
			Provvedimenti.setMultiple(true);
			Provvedimenti.setSelectSize(7);

			context.getRequest().setAttribute("Provvedimenti", Provvedimenti);

			LookupList SanzioniAmministrative = new LookupList(db, "lookup_sanzioni_amministrative");
			SanzioniAmministrative.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SanzioniAmministrative", SanzioniAmministrative);

			LookupList SanzioniPenali = new LookupList(db, "lookup_sanzioni_penali");
			SanzioniPenali.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SanzioniPenali", SanzioniPenali);

			LookupList Sequestri = new LookupList(db, "lookup_sequestri");
			Sequestri.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Sequestri", Sequestri);

			
			org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket  (db,Integer.parseInt(id));
			newTic.setAssignedDate(cu.getAssignedDate());
			
			newTic.setIdNormeViolate( context.getRequest().getParameterValues("listaNorme"));
			isValid = this.validateObject(context, db, newTic) && isValid;
			if (isValid) {
				newTic.setTestoAppoggio("Inserimento"); //aggiunto da d.dauria
				recordInserted = newTic.insert(db,context);
				newTic.insertAzioniNonConforme(db, context.getRequest().getParameterValues("Provvedimenti"));
				//Gestione norme violate multiple con lookup NEW
				if(context.getRequest().getParameterValues("listaNorme") != null)
					newTic.insertNormeViolate(db, context.getRequest().getParameterValues("listaNorme"));
			}


			if (recordInserted) {

				newTicket = new Ticket(db, newTic.getId());
				context.getRequest().setAttribute("TicketDetails", newTicket);

				addRecentItem(context, newTicket);

				processInsertHook(context, newTicket);
			} else {
				if (newTic.getAltId() != -1) {
					Stabilimento thisOrg = new Stabilimento(db, newTic.getAltId(), true);
					newTic.setCompanyName(thisOrg.getName());
					thisOrg.getPrefissoAction(context.getAction().getActionName());
				}
			}

	


		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "ViewTickets", "Ticket Insert ok");
		if (recordInserted && isValid) {
			if (context.getRequest().getParameter("actionSource") != null) {
				context.getRequest().setAttribute(
						"actionSource", context.getRequest().getParameter("actionSource"));
				return "InsertTicketOK";
			}
			String retPage = "DettaglioOK";
			
			

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
					
						context.getRequest().setAttribute("attivita_gravi_inseriti",context.getRequest().getParameter("attivita_gravi_inseriti")+ ";"+newTicket.getId()+"-diff");

			    	}
			    	else
			    	{
				    	 context.getRequest().setAttribute("attivita_gravi_inseriti",newTicket.getId()+"-diff");

			    	}
				}
				    	
			}
			else
			{
				context.getRequest().setAttribute("inserito", ""+newTicket.getId()+";");

			}
			context.getRequest().setAttribute("TipoNC",context.getRequest().getParameter("tipoNc"));

		}
		return executeCommandAdd(context); 
	}
	
	 public String executeCommandViewDiffideOsa(ActionContext context) {
		 
		 Connection db = null;
		    Ticket newTic = null;
		    //Parameters
		    String ticketId = context.getRequest().getParameter("id");
		    SystemStatus systemStatus = this.getSystemStatus(context);
		    try {
		      db = this.getConnection(context);
		      
		      
		      Stabilimento thisOrg = new Stabilimento();
		     
		      thisOrg.queryRecordStabilimentoAlt(db, Integer.parseInt(context.getParameter("altId")));
		      thisOrg.getPrefissoAction(context.getAction().getActionName());
		     
		      Ticket ticket = new Ticket();
		      ticket.setAltId(thisOrg.getAltId());
		      ticket.setTipologia_operatore(thisOrg.getTipologia());
		      context.getRequest().setAttribute("TicketDetails", ticket);
		      TicketList diffList = new TicketList();
		      diffList.setAltId(Integer.parseInt(context.getParameter("altId")));
		      diffList.buildListDiffideOsa(db);
		      context.getRequest().setAttribute("DiffideList", diffList);
		      context.getRequest().setAttribute("OrgDetails", thisOrg);

		      
		    } catch (Exception errorMessage) {
			      context.getRequest().setAttribute("Error", errorMessage);
			      return ("SystemError");
			    } finally {
			      this.freeConnection(context, db);
			    }
			    //return "ModifyautorizzazionetrasportoanimaliviviOK";
			    return "ViewDiffideOK";
			    //return getReturn(context, "ModifyTicket");
			  }

	  /**
	   * Loads the ticket for modification
	   *
	   * @param context Description of Parameter
	   * @return Description of the Returned Value
	   */
	  public String executeCommandModifyTicket(ActionContext context) {
	   			
	    Connection db = null;
	    Ticket newTic = null;
	    //Parameters
	    String ticketId = context.getRequest().getParameter("id");
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    String retPage = "ModifyOK";	
	    try {
	      db = this.getConnection(context);
	      User user = this.getUser(context, this.getUserId(context));
	      //Load the ticket
	
	    	  
	        newTic = new Ticket(db, Integer.parseInt(ticketId));
	        //newTic.setIdRuoloUtente(user.getRoleId());
	        //newTic.setFieldsRegistro(db);
	      
	      //check permission to record
	      String id = context.getRequest().getParameter("idC");
	    	context.getRequest().setAttribute("idC",id);
	    	
	    	String id2 = context.getRequest().getParameter("idNC");
	    	context.getRequest().setAttribute("idNC",
	    			id2);
	      //Load the organization
	     //Gestire le api...	
	    	
	    
	    	
	  
	    	  Stabilimento thisOrganization = new Stabilimento();
		      thisOrganization.queryRecordStabilimentoAlt(db, newTic.getAltId());
		      thisOrganization.getPrefissoAction(context.getAction().getActionName());
		      context.getRequest().setAttribute("OrgDetails", thisOrganization);
	    
	     

	      LookupList Provvedimenti = new LookupList();

			
			
			org.aspcfs.modules.nonconformita.base.Ticket nc = new org.aspcfs.modules.nonconformita.base.Ticket(db,newTic.getId_nonconformita());
			ArrayList<ElementoNonConformita> listancGravi = nc.getNon_conformita_gravi();
			for(ElementoNonConformita enc :listancGravi)
			{
				
				LookupElement el = new LookupElement();
				el.setCode(enc.getId_nc());
				el.setDescription(enc.getDescrizione_nc());
				Provvedimenti.add(el);
			}
			Provvedimenti.setMultiple(true);
			Provvedimenti.setSelectSize(7);
			Provvedimenti.addItem(-1,"--SELEZIONARE UNA O PIU VOCI");
			context.getRequest().setAttribute("Provvedimenti", Provvedimenti);
	      
			LookupList listaNorme = new LookupList(db,"lookup_norme","ordinamento");
			listaNorme.removeItemfromLookupByCode(db,"lookup_norme", "trashed_date is not null");
			listaNorme.addItem(-1,"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("ListaNorme",listaNorme);
	      
	      LookupList SanzioniAmministrative = new LookupList(db, "lookup_sanzioni_amministrative");
	      SanzioniAmministrative.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SanzioniAmministrative", SanzioniAmministrative);
	      
	      LookupList SanzioniPenali = new LookupList(db, "lookup_sanzioni_penali");
	      SanzioniPenali.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("SanzioniPenali", SanzioniPenali);
	      
	      LookupList Sequestri = new LookupList(db, "lookup_sequestri");
	      Sequestri.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("Sequestri", Sequestri);

	      LookupList SiteIdList = new LookupList(db, "lookup_site_id");
	      SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
	      SiteIdList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
	      context.getRequest().setAttribute("SiteIdList", SiteIdList);


	      org.aspcfs.modules.vigilanza.base.Ticket CU = new org.aspcfs.modules.vigilanza.base.Ticket(db,Integer.parseInt(newTic.getIdControlloUfficiale()));
	  		context.getRequest().setAttribute("CU",CU);
	    
	      //Put the ticket in the request
	      addRecentItem(context, newTic);
	      context.getRequest().setAttribute("TicketDetails", newTic);
	      addModuleBean(context, "View Accounts", "View Tickets");
	      LookupList isp = new LookupList(db, "lookup_ispezione");
	      isp.addItem(-1, "-- SELEZIONA VOCE --");
	      context.getRequest().setAttribute("Ispezioni", isp);
	  

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
	    return retPage;
	    //return getReturn(context, "ModifyTicket");
	  }
	  
	  
	  public String executeCommandUpdateTicket(ActionContext context) {
		
		    Connection db = null;
		    int resultCount = 0;

		    int catCount = 0;
		    
		    boolean catInserted = false;
		    boolean isValid = true;

		    Ticket newTic = (Ticket) context.getFormBean();
		    UserBean user = (UserBean) context.getSession().getAttribute("User");
		    String ip = user.getUserRecord().getIp();
		   
		    String id = context.getRequest().getParameter("idC");
		  	context.getRequest().setAttribute("idC",
		  			id);
		  	
		  	String id2 = context.getRequest().getParameter("idNC");
		  	context.getRequest().setAttribute("idNC",
		  			id2);
		   
		    newTic.setTipo_richiesta( context.getRequest().getParameter( "tipo_richiesta" ) );
		    newTic.setPunteggio(25);
		  
		   newTic.setNormaviolata(context.getRequest().getParameter("normaviolata"));
	
		 
		    try {
		      db = this.getConnection(context);
		      
		      Ticket oldTic = new Ticket(db, newTic.getId());
		      
		     
		      
		      org.aspcfs.modules.vigilanza.base.Ticket CU = new org.aspcfs.modules.vigilanza.base.Ticket(db,Integer.parseInt(id));
		  		context.getRequest().setAttribute("CU",CU);
		  		
				newTic.setAssignedDate(CU.getAssignedDate());
		  		newTic.setIdControlloUfficiale(CU.getIdControlloUfficiale());
		      //Get the previousTicket, update the ticket, then send both to a hook
		      Ticket previousTicket = new Ticket(db, Integer.parseInt(context.getParameter("id")));
		      newTic.setModifiedBy(getUserId(context));
		      //newTic.setSiteId(Organization.getOrganizationSiteId(db, newTic.getOrgId()));
		      newTic.setIdNormeViolate( context.getRequest().getParameterValues("listaNorme"));
		      isValid = this.validateObject(context, db, newTic) && isValid;
		      if (isValid) {
		    	newTic.setTestoAppoggio("Modifica"); //aggiunto da d.dauria  
		        resultCount = newTic.update(db);
		        newTic.updateAzioniNonConforme(db, context.getRequest().getParameterValues("Provvedimenti"));
		        if(context.getRequest().getParameterValues("listaNorme") != null)
		        	newTic.updateNormeViolate(db, context.getRequest().getParameterValues("listaNorme"));
		     
		        return executeCommandTicketDetails(context);
		      }
		    
		      Integer idtampone = newTic.getId();
					
		 
		    } catch (Exception e) {
		      context.getRequest().setAttribute("Error", e);
		      return ("SystemError");
		    } finally {
		      this.freeConnection(context, db);
		    }

		   
		    return executeCommandModifyTicket(context);
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

		      //find record permissions for portal users
		    
		      org.aspcfs.modules.vigilanza.base.Ticket CU = new org.aspcfs.modules.vigilanza.base.Ticket(db,Integer.parseInt(newTic.getIdControlloUfficiale()));
		  		context.getRequest().setAttribute("CU",CU);
		  		
		  		LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			      SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			      context.getRequest().setAttribute("SiteIdList", SiteIdList);
			      
			      LookupList Provvedimenti = new LookupList(db, "lookup_provvedimenti");
			      Provvedimenti.addItem(-1, "-- SELEZIONA VOCE --");
			      context.getRequest().setAttribute("Provvedimenti", Provvedimenti);
			      
			      LookupList SanzioniAmministrative = new LookupList(db, "lookup_sanzioni_amministrative");
			      SanzioniAmministrative.addItem(-1, "-- SELEZIONA VOCE --");
			      context.getRequest().setAttribute("SanzioniAmministrative", SanzioniAmministrative);
			      
			      LookupList SanzioniPenali = new LookupList(db, "lookup_sanzioni_penali");
			      SanzioniPenali.addItem(-1, "-- SELEZIONA VOCE --");
			      context.getRequest().setAttribute("SanzioniPenali", SanzioniPenali);
			      
			      LookupList Sequestri = new LookupList(db, "lookup_sequestri");
			      Sequestri.addItem(-1, "-- SELEZIONA VOCE --");
			      context.getRequest().setAttribute("Sequestri", Sequestri);

			      LookupList isp = new LookupList(db, "lookup_ispezione");
			      isp.addItem(-1, "-- SELEZIONA VOCE --");
			      context.getRequest().setAttribute("Ispezioni", isp);
			      context.getRequest().setAttribute("TicketDetails", newTic);
			      
			      retPag = "DettaglioOK";

		  		
		  
			  	
			  		 Stabilimento thisOrganization = new Stabilimento();
			  		 thisOrganization.queryRecordStabilimentoAlt(db, newTic.getAltId());
			  		thisOrganization.getPrefissoAction(context.getAction().getActionName());
			  		context.getRequest().setAttribute("OrgDetails", thisOrganization);
			  	
		  	 
		       
		  	  
		         
		      		      
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
		    return retPag;
		  }

}
