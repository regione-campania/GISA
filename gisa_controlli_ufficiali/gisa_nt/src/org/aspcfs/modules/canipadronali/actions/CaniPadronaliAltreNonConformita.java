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
import org.aspcfs.modules.altriprovvedimenti.base.Ticket;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.RequestUtils;

import com.darkhorseventures.framework.actions.ActionContext;

public class CaniPadronaliAltreNonConformita extends CFSModule {
	
	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "canipadronali-nonconformita-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		Ticket newTic = null;
		try {
			db = this.getConnection(context);
			String idControlloUfficiale = context.getRequest().getParameter("idControllo");
			String idC = context.getRequest().getParameter("idC");
			context.getRequest().setAttribute("idControllo",idControlloUfficiale);
			context.getRequest().setAttribute("idC",idC);
			org.aspcfs.modules.altriprovvedimenti.actions.AccountNonConformita nonConformita = new org.aspcfs.modules.altriprovvedimenti.actions.AccountNonConformita();
			nonConformita.executeCommandAdd(context,db);
			
			String temporgId = context.getRequest().getParameter("orgId");
			int id_prop = Integer.parseInt(temporgId);
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(idControlloUfficiale)) ;
			int idAsl = -1 ;
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
			context.getRequest().setAttribute("siteId",idAsl) ;
			context.getRequest().setAttribute("OrgDetails", proprietario);


		} catch (Exception e) {
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return ("AddOK");
	}

	public String executeCommandInsert(ActionContext context) throws SQLException {
		if (!(hasPermission(context, "canipadronali-nonconformita-add"))) {
			return ("PermissionError");
		}
		Connection db = null;
		Ticket newTic = (Ticket) context.getFormBean();
		boolean recordInserted = false;
		boolean isValid = true;
		try {
			db = this.getConnection(context);
			//db.commit();
			org.aspcfs.modules.altriprovvedimenti.actions.AccountNonConformita nc = new org.aspcfs.modules.altriprovvedimenti.actions.AccountNonConformita();
			nc.executeCommandInsert(context,db);
			String assetId = context.getRequest().getParameter("assetId");
			context.getRequest().setAttribute("assetId", assetId);

		} catch (Exception e) 
		{
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally 
		{
			this.freeConnection(context, db);
		}
		recordInserted 	= (Boolean) context.getRequest().getAttribute("recordInserted");
		isValid 		= (Boolean) context.getRequest().getAttribute("isValid");
		
		if (recordInserted && isValid) 
		{
			String retPage = "DettaglioOK";
			String tipo_richiesta = newTic.getTipo_richiesta();
			tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);
			retPage = "InsertOK";
			return ( retPage );//("InsertOK"); 
		}
		return (executeCommandAdd(context));
	}



	/**
	 * Loads the ticket for modification
	 *
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandModifyTicket(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-nonconformita-edit")) {
			return ("PermissionError");
		}				
		Connection db = null;

		try {
			db = this.getConnection(context);
			org.aspcfs.modules.altriprovvedimenti.actions.AccountNonConformita nonConformita = new org.aspcfs.modules.altriprovvedimenti.actions.AccountNonConformita();
			nonConformita.executeCommandModifyTicket(context,db);
			
			String temporgId = context.getRequest().getParameter("orgId");
			String idC = context.getRequest().getParameter("idC");

			int id_prop = Integer.parseInt(temporgId);
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(idC)) ;
			int idAsl = -1 ;
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
			context.getRequest().setAttribute("siteId",idAsl) ;
			context.getRequest().setAttribute("OrgDetails", proprietario);


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
	
	public String executeCommandChiudi(ActionContext context)
	{
		if (!(hasPermission(context, "canipadronali-nonconformita-edit"))){
			return ("PermissionError");
		}
		Connection db = null ;
		try
		{
			db = this.getConnection(context);
		org.aspcfs.modules.altriprovvedimenti.actions.AccountNonConformita nonConformita = new org.aspcfs.modules.altriprovvedimenti.actions.AccountNonConformita();
		nonConformita.executeCommandChiudi(context,db);
		
		Integer flag 				= (Integer)context.getRequest().getAttribute("flag");
		
		Integer numSottoAttivita 	= (Integer)context.getRequest().getAttribute("numSottoAttivita");
		if(flag==1  ){
			context.getRequest().setAttribute("Chiudi", "1");
			return (executeCommandTicketDetails(context));

		}

		if(numSottoAttivita != null && numSottoAttivita == 0 ){

			context.getRequest().setAttribute("numSottoAttivita", "0");
			return (executeCommandTicketDetails(context));

		}
		
		if(context.getRequest().getAttribute("attivitaCollegate")==null){
			if(flag==1 || flag==2){
				context.getRequest().setAttribute("Chiudi", ""+flag);
				return (executeCommandTicketDetails(context));

			}
		}
		Integer resultCount 	= (Integer)context.getRequest().getAttribute("resultCount");
		 if (resultCount!=null && resultCount == -1) {
			 return ( executeCommandTicketDetails(context));
		 } else if (resultCount!=null && resultCount == 1) {
			 return (executeCommandTicketDetails(context));
		 }
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		 return ("UserError");
		
	}

	public String executeCommandTicketDetails(ActionContext context) {
		if (!hasPermission(context, "canipadronali-nonconformita-view")) {
			return ("PermissionError");
		}

		Connection db = null;
		String retPag = null;
		try {
			db = this.getConnection(context);
			org.aspcfs.modules.altriprovvedimenti.actions.AccountNonConformita nonconformita = new org.aspcfs.modules.altriprovvedimenti.actions.AccountNonConformita();
			nonconformita.executeCommandTicketDetails(context,db);
			Ticket newTic = (Ticket)context.getRequest().getAttribute("TicketDetails");
			
			retPag = "DettaglioOK";

			
			String temporgId = context.getRequest().getParameter("orgId");
			int id_prop = Integer.parseInt(temporgId);
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			
			Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(newTic.getIdControlloUfficiale())) ;
			int idAsl = -1 ;
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
			context.getRequest().setAttribute("siteId",idAsl) ;
			context.getRequest().setAttribute("OrgDetails", proprietario);

		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		//return getReturn(context, "TicketDetails");
		return retPag;
	}

	
	/**
	 * Confirm the delete operation showing dependencies
	 *
	 * @param context Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandConfirmDelete(ActionContext context) {
		if (!hasPermission(context, "canipadronali-nonconformita-delete")) {
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
			String assetId = context.getRequest().getParameter("assetId");
			int id_prop = Integer.parseInt(temporgId);
			int id_cane = Integer.parseInt(assetId) ;
			DependencyList dependencies = ticket.processDependencies(db);
			//Prepare the dialog based on the dependencies
			HtmlDialog htmlDialog = new HtmlDialog();
			dependencies.setSystemStatus(systemStatus);
			htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
			htmlDialog.addMessage(
					systemStatus.getLabel("confirmdelete.caution") + "\n" + dependencies.getHtmlString());
			/*htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));*/
			htmlDialog.addButton(
					systemStatus.getLabel("global.button.delete"), "javascript:window.location.href='CaniPadronaliNonConformita.do?command=DeleteTicket&assetId="+id_cane+"&id=" + id + "&orgId=" + orgId + "&forceDelete=true" + RequestUtils.addLinkParams(
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
		if (!hasPermission(context, "canipadronali-nonconformita-delete")) {
			return ("PermissionError");
		}
		boolean recordDeleted = false;
		Connection db = null;
		//Parameters
		int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
		String passedId = context.getRequest().getParameter("id");
		try {
			db = this.getConnection(context);
			Ticket thisTic = new Ticket(db, Integer.parseInt(passedId));
			//check permission to record
		
			recordDeleted = thisTic.delete(db, getDbNamePath(context));
			String id_controllo=thisTic.getIdControlloUfficiale();
			while(id_controllo.startsWith("0")){

				id_controllo=id_controllo.substring(1);
			}

			context.getRequest().setAttribute("idC", id_controllo);
			String temporgId = context.getRequest().getParameter("orgId");
			String assetId = context.getRequest().getParameter("assetId");
			int id_prop = Integer.parseInt(temporgId);
			int id_cane = Integer.parseInt(assetId) ;
			
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

			
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
			Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(id_controllo)) ;
			int idAsl = -1 ;
			if (proprietario != null )
				idAsl = proprietario.getIdAsl() ;
			context.getRequest().setAttribute("siteId",idAsl) ;
			context.getRequest().setAttribute("OrgDetails", proprietario);

		
		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
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
	
	public String executeCommandUpdateTicket(ActionContext context) {
		if (!hasPermission(context, "canipadronali-nonconformita-edit")) {
			return ("PermissionError");
		}
		Connection db = null;
		try {db = this.getConnection(context);
			org.aspcfs.modules.altriprovvedimenti.actions.AccountNonConformita nonConformita = new org.aspcfs.modules.altriprovvedimenti.actions.AccountNonConformita();
			nonConformita.executeCommandUpdateTicket(context,db);
			
			String temporgId = context.getRequest().getParameter("orgId");

			int id_prop = Integer.parseInt(temporgId);
			OperazioniCaniDAO op = new OperazioniCaniDAO() ;
			op.setDb(db);
//			Proprietario proprietario = op.dettaglioProprietario(id_prop, Integer.parseInt(id_controllo)) ;
//			int idAsl = -1 ;
//			if (proprietario != null )
//				idAsl = proprietario.getIdAsl() ;
//			context.getRequest().setAttribute("siteId",idAsl) ;
//			context.getRequest().setAttribute("OrgDetails", proprietario);


		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}


		return (executeCommandTicketDetails(context));
	}
}
