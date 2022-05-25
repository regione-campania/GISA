/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.canipadronali.actions;

import java.sql.Connection;
import java.sql.SQLException;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.canipadronali.base.Cane;
import org.aspcfs.modules.canipadronali.base.OperazioniCaniDAO;
import org.aspcfs.modules.canipadronali.base.Proprietario;
import org.aspcfs.modules.tamponi.actions.Tamponi;
import org.aspcfs.modules.tamponi.base.Ticket;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.RequestUtils;

import com.darkhorseventures.framework.actions.ActionContext;

public class CaniPadronaliTamponi extends CFSModule {
	
	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "canipadronali-tamponi-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		
		try {
			db = this.getConnection(context);
			SystemStatus systemStatus = this.getSystemStatus(context);
			
			Tamponi t = new Tamponi () ;
			t.executeCommandAdd(context,db);
			

			// Load the organization
			String temporgId = context.getRequest().getParameter("orgId");
			int id_prop = Integer.parseInt(temporgId);
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
		 	
			String idC = context.getRequest().getParameter("idC");
			Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(idC)) ;
			int idAsl = -1 ;
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
				context.getRequest().setAttribute("siteId",idAsl) ;
				context.getRequest().setAttribute("OrgDetails", proprietario);   

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "AddCaniPadronali", "Ticket Add");
		if (context.getRequest().getParameter("actionSource") != null) {
			context.getRequest().setAttribute("actionSource",
					context.getRequest().getParameter("actionSource"));
			return "AddTicketOK";
		}
		context.getRequest().setAttribute("systemStatus",this.getSystemStatus(context));
		return ("AddOK");
	}
	
	public String executeCommandReopenTicket(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-tamponi-edit")) {
			return ("PermissionError");
		}
	Integer resultCount = -1 ; 
		try
		{
			Tamponi t = new Tamponi();
			t.executeCommandReopenTicket(context);
			resultCount = (Integer)context.getRequest().getAttribute("resultCount");

			
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
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
	
	public String executeCommandUpdateTicket(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-tamponi-edit")) {
			return ("PermissionError");
		}
		Connection db = null;
		int resultCount = 0;
		boolean isValid = true;


		try {
			db = this.getConnection(context);

			Tamponi t = new Tamponi () ;
			t.executeCommandUpdateTicket(context,db); 
			
			resultCount = (Integer) context.getRequest().getAttribute("resultCount")	;
			isValid		= (Boolean) context.getRequest().getAttribute("isValid")		;
			Integer orgId = (Integer)context.getRequest().getAttribute("OrgId")			;
			
			String temporgId = context.getRequest().getParameter("orgId");
			int id_prop = Integer.parseInt(temporgId);
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			String idC = context.getRequest().getParameter("idControlloUfficiale");
			Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(idC)) ;
			int idAsl = -1 ;
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
				context.getRequest().setAttribute("siteId",idAsl) ;
				context.getRequest().setAttribute("OrgDetails", proprietario);   
			//check permission to record
			
			addModuleBean(context, "CaniPadronali CU", "CaniPadronali CU");
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

			return "DettaglioOK";
		
	}
	
	

	public String executeCommandInsert(ActionContext context)
			throws SQLException {
		if (!(hasPermission(context, "canipadronali-tamponi-add"))) {
			return ("PermissionError");
		}
		Connection db = null;
		boolean recordInserted = false;
		boolean contactRecordInserted = false;
		boolean isValid = true;
		
		try {
			db = this.getConnection(context);

			
			Ticket newTic = (Ticket) context.getFormBean();
			String temporgId = context.getRequest().getParameter("orgId");
			String assetId = context.getRequest().getParameter("assetId");
			int id_prop = Integer.parseInt(temporgId);
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			String idC = context.getRequest().getParameter("idC");
			Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(idC)) ;
			int idAsl = -1 ;
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
				context.getRequest().setAttribute("siteId",idAsl) ;
				context.getRequest().setAttribute("OrgDetails", proprietario);   
			
			context.getRequest().setAttribute("ragione_sociale",proprietario.getName());
			Tamponi t = new Tamponi () ;
			t.executeCommandInsert(context,db); 
			isValid = (Boolean)  context.getRequest().getAttribute("isValid");
			recordInserted = (Boolean)  context.getRequest().getAttribute("recordInserted");
	          
		} catch (Exception e) 
		{
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
		
			retPage = "InsertOK";


			return (retPage);// ("InsertOK");
		}
		return (executeCommandAdd(context));
	}

	/**
	 * Loads the ticket for modification
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandModifyTicket(ActionContext context) {
		if (!hasPermission(context, "canipadronali-tamponi-edit")) {
			return ("PermissionError");
		}
		Connection db = null;
		
		try {
			db = this.getConnection(context);
			
			Tamponi t = new Tamponi () ;
			t.executeCommandModifyTicket(context,db);
			 
			Integer orgId = (Integer) context.getRequest().getAttribute("OrgId");
			String temporgId = context.getRequest().getParameter("orgId");
			int id_prop = Integer.parseInt(temporgId);
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			String idC = context.getRequest().getParameter("idC");
			Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(idC)) ;
			int idAsl = -1 ;
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
				context.getRequest().setAttribute("siteId",idAsl) ;
				context.getRequest().setAttribute("OrgDetails", proprietario);   
			
			//check permission to record
			
			addModuleBean(context, "CaniPadronali CU", "CaniPadronali CU");
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return "ModifyOK";
		
	}
	
	public String executeCommandTicketDetails(ActionContext context) {
		if (!hasPermission(context, "canipadronali-tamponi-view")) {
			return ("PermissionError");
		}
		String retPag  	= "";
		Connection db 	= null;
		try
		{
		
			db = this.getConnection(context);
			Tamponi t = new Tamponi();
			t.executeCommandTicketDetails(context,db);
			
			Integer orgId = (Integer) context.getRequest().getAttribute("orgId");
			
			retPag = "DettaglioOK";
			// Load the organization for the header
		
			String temporgId = context.getRequest().getParameter("orgId");
			int id_prop = Integer.parseInt(temporgId);
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(context.getRequest().getAttribute("idC")+"")) ;
			int idAsl = -1 ;
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
				context.getRequest().setAttribute("siteId",idAsl) ;
				context.getRequest().setAttribute("OrgDetails", proprietario);   
			
			addModuleBean(context, "CaniPadronali CU", "CaniPadronali CU");
		} 
		catch (Exception errorMessage) 
		{
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} 
		finally 
		{
			this.freeConnection(context, db);
		}
		// return getReturn(context, "TicketDetails");
		return retPag;
	}
	
	public String executeCommandConfirmDelete(ActionContext context) {
		if (!hasPermission(context, "canipadronali-tamponi-delete")) {
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
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(ticket.getIdControlloUfficiale())) ;
			int idAsl = -1 ;
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
				context.getRequest().setAttribute("siteId",idAsl) ;
				context.getRequest().setAttribute("OrgDetails", proprietario);   
			
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
					"javascript:window.location.href='CaniPadronaliTamponi.do?command=DeleteTicket&id="
							+ id
							+ "&orgId="
							+ orgId
							+ "&forceDelete=true"
							+ RequestUtils.addLinkParams(context.getRequest(),
									"popup|popupType|actionId") + "'");
			htmlDialog.addButton(systemStatus.getLabel("button.cancel"),
					"javascript:parent.window.close()");
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
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandDeleteTicket(ActionContext context) {
		if (!hasPermission(context, "canipadronali-tamponi-delete")) {
			return ("PermissionError");
		}
		Boolean recordDeleted = false;
		Connection db = null;
		// Parameters
		int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
		
		try {
			db = this.getConnection(context);
			String temporgId = context.getRequest().getParameter("orgId");
			int id_prop = Integer.parseInt(temporgId);
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(context.getRequest().getAttribute("idControllo")+"")) ;
			int idAsl = -1 ;
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
				context.getRequest().setAttribute("siteId",idAsl) ;
				context.getRequest().setAttribute("OrgDetails", proprietario);   
			Tamponi t = new Tamponi ();
			t.executeCommandDeleteTicket(context,db);
			
			
			
			String id_controllo = (String)context.getRequest().getAttribute("idControllo");
			recordDeleted = (Boolean) context.getRequest().getAttribute("recordDeleted");			
			
			if (recordDeleted) {
				
				String inline = context.getRequest().getParameter("popupType");
				context.getRequest().setAttribute("refreshUrl","AccountVigilanza.do?command=TicketDetails&id="+id_controllo+"&orgId="+ orgId+ (inline != null&& "inline".equals(inline.trim()) ? "&popup=true": ""));
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
	
	public String executeCommandChiudi(ActionContext context) {
		if (!(hasPermission(context, "canipadronali-tamponi-edit"))) {
			return ("PermissionError");
		}
		int resultCount = -1;
		Connection db = null;
	
		try {
			db = this.getConnection(context);
			
			Tamponi t = new Tamponi () ;
			t.executeCommandChiudi(context,db);
			
			resultCount 	= (Integer) context.getRequest().getAttribute("resultCount");
		    Integer OrgId	= (Integer) context.getRequest().getAttribute("OrgId");
			
			//check permission to record
			
			
		
			

				      if (resultCount == -1) {
				    	  return (executeCommandTicketDetails(context));
				      } else if (resultCount == 1) {
				    	 
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

}
