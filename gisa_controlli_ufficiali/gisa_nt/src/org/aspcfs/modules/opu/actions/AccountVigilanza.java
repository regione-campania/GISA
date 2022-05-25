/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.opu.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.HttpMultiPartParser;

import org.aspcf.modules.controlliufficiali.base.AziendeZootFields;
import org.aspcf.modules.controlliufficiali.base.Piano;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.Role;

import java.text.ParseException;
import java.util.*;

import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.GestoreComunicazioniBdu;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.LineaProduttivaList;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.utils.DbiBdu;
import org.aspcfs.utils.DwrPreaccettazione;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;



/**
 * Maintains Tickets related to an Account
 *
 * @author chris
 * @version $Id: AccountTickets.java,v 1.13 2002/12/24 14:53:03 mrajkowski Exp
 *          $
 * @created August 15, 2001
 */
public final class AccountVigilanza extends CFSModule {

	
	/**
	 * ACTION DI AGGIUNTA DI UN NUOVO CONTROLLO UFFICIALE
	 * 	
	 * @param context
	 * @return
	 */
	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "opu-vigilanza-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		String popup = ""; 
		
		try {
			db = this.getConnection(context);
		
			// Process request parameters
		

			String tempStabId = context.getRequest().getParameter("orgId");
			if (tempStabId == null) {
				tempStabId = ""
					+ (Integer) context.getRequest().getAttribute("orgId");
			}
			// String iter = context.getRequest().getParameter("tipo");
			Integer stabid = null;

		

			if (tempStabId != null ) {
				try {stabid = Integer.parseInt(tempStabId);} catch (Exception e) {}
			}
			if (context.getRequest().getParameter("idMacchinetta")!=null)
				context.getRequest().setAttribute("idMacchinetta",context.getRequest().getParameter("idMacchinetta"));
			
			else
				context.getRequest().setAttribute("idMacchinetta","");
			
			if (stabid == null || stabid<=0){
				String tempAltId = context.getRequest().getParameter("altId");
				if (tempAltId == null) {
					tempAltId = ""
							+ (Integer) context.getRequest().getAttribute("altId");
				}
				Integer altid = null;
				if (tempAltId != null) {
					altid = Integer.parseInt(tempAltId);
				}
				stabid = altid-20000000; //sviluppare in futuro una funzione per ricavare l'id originale dall'alt_id
				context.getRequest().setAttribute("orgId", stabid);
			}

			Stabilimento newStabilimento = new Stabilimento(db,  stabid);
			newStabilimento.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("OrgDetails", newStabilimento);
			
//			Operatore operatore = new Operatore () ;
//			operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());
//
//			operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());	
//			context.getRequest().setAttribute("Operatore", operatore);

			
			context.getRequest().setAttribute("siteId", newStabilimento.getIdAsl());
			context.getRequest().setAttribute("tipologia", org.aspcfs.modules.troubletickets.base.Ticket.TIPO_OPU);

			boolean isTrasporto = false;
			String[] codiciTrasportoContoTerzi = {"MS.090-TCT-T", "MS.090-MS.090.100-852ITAA002", "MS.090-MS.090.100-852ITAA003"};
			boolean isOsm = false;
			
			LineaProduttivaList listaLinee = newStabilimento.getListaLineeProduttive();
			for (int i = 0; i<listaLinee.size(); i++){
				LineaProduttiva linea = (LineaProduttiva) listaLinee.get(i);
				if (Arrays.asList(codiciTrasportoContoTerzi).contains(linea.getCodice())) {
					isTrasporto = true;
					//break;
				}
				if (linea.getCodice().startsWith("MG") || linea.getCodice().startsWith("MR") ) {
					isOsm = true;
					//break;
				}
			}
			
			boolean isMobile = (newStabilimento.getTipoAttivita()==2);
			
			if (isTrasporto || isMobile)
				context.getRequest().setAttribute("tipoDest","Autoveicolo");
			else
				context.getRequest().setAttribute("tipoDest","Es. Commerciale");
			
			if (isOsm){	
				boolean modificato = false;
				PreparedStatement pst = db.prepareStatement("select * from is_osm_modificato(?, ?)");
				pst.setInt(1, newStabilimento.getIdStabilimento());
				pst.setString(2, "opu_stabilimento");
				ResultSet rs = pst.executeQuery();
				if (rs.next())
					modificato = rs.getBoolean(1);
				
				if (modificato){
					context.getRequest().setAttribute("Messaggio", "Attenzione. Si sta tentando di inserire un CU su uno stabilimento con linee OSM non aggiornato con la banca dati SINVSA. Si prega di effettuare un invio tramite la funzione GESTIONE INVIO OSM presente nel dettaglio scheda e riprovare.");
					context.getRequest().setAttribute("stabId", newStabilimento.getIdStabilimento());
					org.aspcfs.modules.gestioneanagrafica.actions.GestioneAnagraficaAction sa = new org.aspcfs.modules.gestioneanagrafica.actions.GestioneAnagraficaAction();
					return sa.executeCommandDetails(context);
				}
			}
			
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza c = new  org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			c.executeCommandAdd(context,db);
			//Load the organization
			
			String currentDate = getCurrentDateAsString(context);
			context.getRequest().setAttribute("currentDate", currentDate);
			context.getRequest().setAttribute(
					"systemStatus", this.getSystemStatus(context));
			
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		
		context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));
		context.getRequest().setAttribute("popup", popup);
	    if(context.getRequest().getParameter("popup") != null && context.getRequest().getParameter("popup") != ""){
			return ("PopupAddOK");
		}
		else {
			 UserBean user = (UserBean)context.getSession().getAttribute("User");
			 String nameContext=context.getRequest().getServletContext().getServletContextName();
				if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA)
					return getReturn(context, "AddTipo2");
				return getReturn(context, "Add");		}
		
		
		
	}



	public String executeCommandInsert(ActionContext context) throws SQLException, ParseException {
		if (!(hasPermission(context, "opu-vigilanza-add"))) 
		{
			return ("PermissionError");
		}
		context.getRequest().setAttribute("reload", "false");
		String retPage = "InsertOK";
		String popup = "";
		Connection db = null;
		boolean recordInserted = false;
		boolean contactRecordInserted = false;
		boolean isValid = true;
		SystemStatus systemStatus = this.getSystemStatus(context);
		
		Ticket newTicket = null;
		try
		{
			db = this.getConnection(context);
			String save = context.getRequest().getParameter("Save");
			Ticket newTic = (Ticket) context.getFormBean();
			

			String tempStabId = context.getRequest().getParameter("idStabilimentoopu");
			if (tempStabId == null) {
				tempStabId = ""
					+ (Integer) context.getRequest().getAttribute("idStabilimentoopu");
			}
			// String iter = context.getRequest().getParameter("tipo");
			Integer stabid = null;

		

			if (tempStabId != null) {
				stabid = Integer.parseInt(tempStabId);
			}
			
			

			Stabilimento newStabilimento = new Stabilimento(db,  stabid);
			newStabilimento.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("OrgDetails", newStabilimento);
			
			if (newStabilimento.getTipoAttivita()==2){
				String idTarga = context.getRequest().getParameter("mobile_targa");
				if (idTarga!=null && !idTarga.equals("null") && !idTarga.equals(""))
					newTic.setIdTarga(idTarga);
			}
			
			
			Operatore operatore = new Operatore () ;
			operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());

			operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());	
			context.getRequest().setAttribute("Operatore", operatore);

			
			context.getRequest().setAttribute("siteId", newStabilimento.getIdAsl());
			context.getRequest().setAttribute("tipologia", org.aspcfs.modules.troubletickets.base.Ticket.TIPO_OPU);
			newTic.setIdStabilimento(stabid);
			newTic.setCompanyName(operatore.getRagioneSociale());
			newTic.setUo(context.getRequest().getParameterValues("uo"));
			context.getRequest().setAttribute("name", operatore.getRagioneSociale());
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();

			controlliGeneric.executeCommandInsert(context,db);
			recordInserted = (Boolean) context.getRequest().getAttribute("inserted");
			isValid = (Boolean) context.getRequest().getAttribute("isValid");
			context.getRequest().setAttribute("idMacchinetta",context.getRequest().getParameter("idMacchinetta"));
			if (recordInserted && isValid) {


				Ticket ticInserito = (Ticket) context.getRequest().getAttribute("TicketDetails");

				

				
						
				String tipo_richiesta = newTic.getTipo_richiesta();
				tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);

				retPage = "InsertOK";
			}
			else
			{
				context.getRequest().setAttribute("orgId", stabid);
				return executeCommandAdd(context);
			}
			

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);		}
		
	    if(context.getRequest().getParameter("popup") != null && context.getRequest().getParameter("popup") != ""){
			return ("PopupCloseOK");
		}
		else {
			return ( retPage );//("InsertOK");
		}
		 
	}
	
	/**
	 * Loads the ticket for modification
	 *
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandModifyTicket(ActionContext context) {
		if (!hasPermission(context, "opu-vigilanza-edit")) {
			return ("PermissionError");
		}
		Connection db = null;
		Ticket newTic = null;


		try 
		{
			db = this.getConnection(context);
			String ticketId = context.getRequest().getParameter("id");

			if (context.getRequest().getParameter("companyName") == null) 
			{
				newTic = new Ticket(db, Integer.parseInt(ticketId));

			} else 
			{
				newTic = (Ticket) context.getFormBean();
			}
			
			//gestione della modifica del controllo ufficale
			boolean isModificabile = true;
			PreparedStatement pst = db.prepareStatement("select * from get_lista_sottoattivita(?, ?, ?)");
			pst.setInt(1, newTic.getId());
			pst.setInt(2, 2);
			pst.setBoolean(3, false);
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				int idCampione = rs.getInt("ticketid");
				//Preaccettazione
				DwrPreaccettazione dwrPreacc = new DwrPreaccettazione();
				String result = dwrPreacc.Preaccettazione_RecuperaCodPreaccettazione(String.valueOf(idCampione));
				JSONObject jsonObj;
				jsonObj = new JSONObject(result);
				String codicePreaccettazione = jsonObj.getString("codice_preaccettazione");
				if (!codicePreaccettazione.equalsIgnoreCase("")){
					isModificabile = false;
				}
				
			}
			UserBean user = (UserBean)context.getSession().getAttribute("User");
			if (user.getUserRecord().getRoleId()!=Role.RUOLO_ORSA && user.getUserRecord().getRoleId()!=Role.HD_1LIVELLO && user.getUserRecord().getRoleId()!=Role.HD_2LIVELLO ){
				if (!isModificabile){
					context.getRequest().setAttribute("Error", "Controllo non modificabile perche' associato ad un codice preaccettazione");
					return(executeCommandTicketDetails(context));
				}
			}
			
			//if (!isModificabile){
			//	context.getRequest().setAttribute("Error", "Controllo non modificabile perche' associato ad un codice preaccettazione");
			//	return(executeCommandTicketDetails(context));
			//}
			//gestione della modifica del controllo ufficiale

			String tempStabId = context.getRequest().getParameter("stabId");
			if (tempStabId == null) {
				tempStabId = ""
					+ (Integer) context.getRequest().getAttribute("orgId");
			}
			// String iter = context.getRequest().getParameter("tipo");
			Integer stabid = null;

			

			if (tempStabId != null) {
				stabid = Integer.parseInt(tempStabId);
			}
			
			Stabilimento newStabilimento = new Stabilimento(db,  stabid);
			newStabilimento.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("OrgDetails", newStabilimento);
			
			Operatore operatore = new Operatore () ;
			operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());

			operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());	
			context.getRequest().setAttribute("Operatore", operatore);

			
			context.getRequest().setAttribute("siteId", newStabilimento.getIdAsl());
			context.getRequest().setAttribute("tipologia", org.aspcfs.modules.troubletickets.base.Ticket.TIPO_OPU);
			context.getRequest().setAttribute("TicketDetails", newTic);

			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			controlliGeneric.executeCommandModifyTicket(context,db);
			context.getRequest().setAttribute("ViewLdA","si");
		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		//return "ModifyautorizzazionetrasportoanimaliviviOK";
		 UserBean user = (UserBean)context.getSession().getAttribute("User");
		 String nameContext=context.getRequest().getServletContext().getServletContextName();
			if (user.getUserRecord().getGruppo_ruolo()==Role.GRUPPO_ALTRE_AUTORITA )
				return getReturn(context, "ModifyTipo2");
			return getReturn(context, "Modify");		//return getReturn(context, "ModifyTicket");
	}
	

	/**
	 * Update the specified ticket
	 *
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandUpdateTicket(ActionContext context) {
		if (!hasPermission(context, "opu-vigilanza-edit")) {
			return ("PermissionError");
		}
		Connection db = null;
		int resultCount = 0;
		boolean isValid = true;


		Ticket newTic = (Ticket) context.getFormBean();

		if(context.getRequest().getParameter("codici_selezionabili")!=null){
			newTic.setCodiceAteco(context.getRequest().getParameter("codici_selezionabili"));
		}

		newTic.setCodiceAllerta(context.getRequest().getParameter("idAllerta"));
		
		if("1".equals(context.getRequest().getParameter("ncrilevate")))
			newTic.setNcrilevate(true);
		else
			newTic.setNcrilevate(false);


		newTic.setId(Integer.parseInt(context.getRequest().getParameter("id")));

		try {


			db = this.getConnection(context);

		
			String tempStabId = context.getRequest().getParameter("idStabilimentoopu");
			if (tempStabId == null) {
				tempStabId = ""
					+ (Integer) context.getRequest().getAttribute("idStabilimentoopu");
			}
			// String iter = context.getRequest().getParameter("tipo");
			Integer stabid = null;

			

			if (tempStabId != null) {
				stabid = Integer.parseInt(tempStabId);
			}
			
			Stabilimento newStabilimento = new Stabilimento(db,  stabid);
			newStabilimento.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("OrgDetails", newStabilimento);
			
			if (newStabilimento.getTipoAttivita()==2){
				String idTarga = context.getRequest().getParameter("mobile_targa");
				if (idTarga!=null && !idTarga.equals("null") && !idTarga.equals(""))
					newTic.setIdTarga(idTarga);
			}
			
			Operatore operatore = new Operatore () ;
			operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());

			operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());	
			context.getRequest().setAttribute("Operatore", operatore);

			
			context.getRequest().setAttribute("siteId",newStabilimento.getIdAsl());
			context.getRequest().setAttribute("tipologia",org.aspcfs.modules.troubletickets.base.Ticket.TIPO_OPU);
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			controlliGeneric.executeCommandUpdateTicket(context,db);
			resultCount = (Integer) context.getRequest().getAttribute("resultCount");
			isValid = (Boolean) context.getRequest().getAttribute("isValid");
			if (!isValid)
			{
				context.getRequest().setAttribute("orgId", stabid);
				return executeCommandModifyTicket(context);
			}
			
			
			DbiBdu.aggiorna_controlo_canile(context, db, newTic.getIdStabilimento(), newTic.getId(), newTic.getAssignedDate(), newTic.getTipoControllo(), newTic.getClosed(), newTic.getTrashedDate());
			DbiBdu.aggiorna_controlo_operatore_commerciale(context, db, newTic.getIdStabilimento(), newTic.getId(), newTic.getAssignedDate(), newTic.getTipoControllo(), newTic.getClosed(), newTic.getTrashedDate());

			
			 try {
					checkControlloBenessere(context, db, newTic.getId());
					} catch (Exception e) {
						e.printStackTrace();
					}	
			 
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		if (resultCount == 1 && isValid) {
			//return "UpdateOK";
			return "DettaglioOK";
		}
		return (executeCommandDettaglio(context));
	}
	
	
	
	/**
	 * Update the specified ticket
	 *
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandSupervisiona(ActionContext context) {
		
		Connection db = null;
		int resultCount = 0;
		boolean isValid = true;


		try {


			db = this.getConnection(context);
			
			String tempStabId = context.getRequest().getParameter("idStabilimento");
			
			int stabid = -1 ;
			if (tempStabId != null) {
				stabid = Integer.parseInt(tempStabId);
			}
			Stabilimento newStabilimento = new Stabilimento(db,  stabid);
			
			
			newStabilimento.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("OrgDetails", newStabilimento);

			
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			controlliGeneric.executeCommandSupervisiona(context, db);
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		if (resultCount == 1 && isValid) {
			//return "UpdateOK";
			return "DettaglioOK";
		}
		context.getRequest().setAttribute("idStabilimentoopu", context.getParameter("idStabilimento"));
		return (executeCommandTicketDetails(context));
	}
	
	public String executeCommandConfirmDelete(ActionContext context) {
		if (!hasPermission(context, "opu-vigilanza-delete")) {
			return ("PermissionError");
		}

		Connection db = null ;
		try 
		{	  
			db = this.getConnection(context);
			
			  String id = context.getRequest().getParameter("id");
		      Ticket ticket = new Ticket(db, Integer.parseInt(id));
			     Stabilimento stab = new Stabilimento (db,ticket.getIdStabilimento()); 
			     stab.getPrefissoAction(context.getAction().getActionName());
			     
			     org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			context.getRequest().setAttribute("url_confirm", stab.getAction()+"Vigilanza.do?command=DeleteTicket&id="+ticket.getId()+"&idStabilimentoopu="+ticket.getIdStabilimento());
			controlliGeneric.executeCommandConfirmDelete(context,db);

			return ("ConfirmDeleteOK");
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} 
		finally
		{
			this.freeConnection(context, db);
		} 
	}

	
	public String executeCommandDeleteTicket(ActionContext context) {
		if (!hasPermission(context, "opu-vigilanza-delete")) {
			return ("PermissionError");
		}
		boolean recordDeleted = false;
		Connection db = null;

	

		try {
			db = this.getConnection(context);


			

			String tempStabId = context.getRequest().getParameter("idStabilimentoopu");
			if (tempStabId == null) {
				tempStabId = ""
					+ (Integer) context.getRequest().getAttribute("idStabilimentoopu");
			}
			// String iter = context.getRequest().getParameter("tipo");
			Integer stabid = null;

		

			if (tempStabId != null) {
				stabid = Integer.parseInt(tempStabId);
			}
			
			Stabilimento newStabilimento = new Stabilimento(db,  stabid);
			newStabilimento.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("OrgDetails", newStabilimento);
			
			Operatore operatore = new Operatore () ;
			operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());

			operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());	
			context.getRequest().setAttribute("Operatore", operatore);

			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			context.getRequest().setAttribute("url", newStabilimento.getAction()+".do?command=ViewVigilanza&stabId="+newStabilimento.getIdStabilimento()+"&opId="+newStabilimento.getIdOperatore());

			controlliGeneric.executeCommandDeleteTicket(context,db);
			recordDeleted = (Boolean) context.getRequest().getAttribute("recordDeleted");


		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			errorMessage.printStackTrace();
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

	/**
	 * Re-opens a ticket
	 *
	 * @param context Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandReopenTicket(ActionContext context) {
		if (!hasPermission(context, "opu-vigilanza-edit")) {
			return ("PermissionError");
		}
		int resultCount = -1;
Connection db = null ;
		try {
			db = this.getConnection(context);
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			controlliGeneric.executeCommandReopenTicket(context,db);
			context.getRequest().setAttribute("stabId",context.getParameter("stabId"));
			resultCount = (Integer)context.getRequest().getAttribute("resultCount");
			Ticket  thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("id")));
			context.getRequest().setAttribute("idStabilimentoopu", ""+thisTicket.getIdStabilimento());
			
			DbiBdu.apri_controlo_canile (context, db, thisTicket.getIdStabilimento(), thisTicket.getId(), thisTicket.getAssignedDate(), thisTicket.getTipoControllo(), thisTicket.getClosed(), thisTicket.getTrashedDate());
			DbiBdu.apri_controlo_operatore_commerciale(context, db, thisTicket.getIdStabilimento(), thisTicket.getId(), thisTicket.getAssignedDate(), thisTicket.getTipoControllo(), thisTicket.getClosed(), thisTicket.getTrashedDate());


		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
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
	
	/**
	 * Load the ticket details tab
	 *
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandTicketDetails(ActionContext context) {
		if (!hasPermission(context, "opu-vigilanza-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		//Parameters
		String ticketId = null;
		ticketId = context.getRequest().getParameter("id");
		if (ticketId == null)
			ticketId = (String) context.getRequest().getAttribute("id");
		String retPag = null;
		PreparedStatement pst = null;
		String sql = null;
		String flagMod5 = null;
		ResultSet rs = null;
		try {
			db = this.getConnection(context);
			// Load the ticket
			int i = 0;

			
			String tempStabId = context.getRequest().getParameter("idStabilimentoopu");
			if (tempStabId == null) {
				tempStabId = 
					 (String) context.getRequest().getAttribute("idStabilimentoopu");
			}
			// String iter = context.getRequest().getParameter("tipo");
			
			if (tempStabId == null) {
				tempStabId =  context.getRequest().getParameter("stabId");
			}
			
			Integer stabid = null;


			if (tempStabId != null) {
				stabid = Integer.parseInt(tempStabId);
			}
			
			Stabilimento newStabilimento = new Stabilimento(db,  stabid);
			newStabilimento.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("OrgDetails", newStabilimento);
			
			Operatore operatore = new Operatore () ;
			operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());

			operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());	
			context.getRequest().setAttribute("Operatore", operatore);

			
			
			//Read flag for mod5 from ticket table
			sql = "select flag_mod5 as flag from ticket where ticketid = ? ";
			pst=db.prepareStatement(sql);
			pst.setInt(++i,Integer.parseInt(ticketId));
			rs = pst.executeQuery();
			if(rs.next()){
				 flagMod5 = rs.getString("flag");
			}
			
			try {
			checkControlloBenessere(context, db, Integer.parseInt(ticketId));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
						
			context.getRequest().setAttribute("siteId",newStabilimento.getIdAsl());
			context.getRequest().setAttribute("tipologia",org.aspcfs.modules.troubletickets.base.Ticket.TIPO_OPU);
			context.getRequest().setAttribute("bozza", flagMod5);
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			controlliGeneric.executeCommandTicketDetails(context,db);
			
			GestoreComunicazioniBdu gBdu = new GestoreComunicazioniBdu();
			context.getRequest().setAttribute("isPresenteRegistrazione", gBdu.isPresenteRegistrazione(Integer.parseInt(ticketId)));

			retPag = "DettaglioOK";


		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		//return getReturn(context, "TicketDetails");
		return retPag;
	}
	
	

	/**
	 * Description of the Method
	 *
	 * @param context Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandRestoreTicket(ActionContext context) {

		if (!(hasPermission(context, "opu-vigilanza-edit"))) {
			return ("PermissionError");
		}
		boolean recordUpdated = false;
		Connection db = null ;
		try 
		{	
			db = this.getConnection(context);
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			controlliGeneric.executeCommandReopenTicket(context,db);
			recordUpdated = (Boolean) context.getRequest().getAttribute("recordUpdated");

		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);

		}
		if (recordUpdated) {
			return (executeCommandTicketDetails(context));
		} else {
			context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
			return ("UserError");
		}
	}








	//aggiunto da d.dauria
	public String executeCommandDeleteListaDistribuzione(ActionContext context) {
		if (!hasPermission(context, "opu-vigilanza-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		Ticket newTic = null;
		try {

			String filePath = this.getPath(context, "accounts");
			//Process the form data
			HttpMultiPartParser multiPart = new HttpMultiPartParser();
			multiPart.setUsePathParam(false);
			multiPart.setUseUniqueName(true);
			multiPart.setUseDateForFolder(true);
			multiPart.setExtensionId(getUserId(context));
			HashMap parts = multiPart.parseData(context.getRequest(), filePath);
			context.getRequest().setAttribute("parts", parts);
			db = this.getConnection(context);

			String gotoInsert = (String)parts.get("gotoPage");
		
			Integer tempid = null;
			Integer stabid = null;
		

			String tempStabId = context.getRequest().getParameter("orgId");
			if (tempStabId == null) {
				tempStabId = ""
					+ (Integer) context.getRequest().getAttribute("orgId");
			}
			// String iter = context.getRequest().getParameter("tipo");

		
			if (tempStabId != null) {
				stabid = Integer.parseInt(tempStabId);
			}
			
			Stabilimento newStabilimento = new Stabilimento(db,  stabid);
			newStabilimento.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("OrgDetails", newStabilimento);
			
			Operatore operatore = new Operatore () ;
			operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());

			operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());	
			context.getRequest().setAttribute("Operatore", operatore);

			
			SystemStatus systemStatus = this.getSystemStatus(context);
			context.getRequest().setAttribute("siteId", newStabilimento.getIdAsl());
			context.getRequest().setAttribute("tipologia", org.aspcfs.modules.troubletickets.base.Ticket.TIPO_OPU);
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			controlliGeneric.executeCommandDeleteListaDistribuzione(context,db);

			if(!"insert".equals(gotoInsert))
			{
				return "ModifyOK";
			}
			else
			{

				if (((String)parts.get("actionSource")) != null) {
					context.getRequest().setAttribute("actionSource", (String)parts.get("actionSource"));
					return "AddTicketOK";
				}
				context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));
				return ("AddOK");
			}

		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
		
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	}


	public String executeCommandUploadListaDistribuzione(ActionContext context) {
		if (!hasPermission(context, "opu-vigilanza-add")) {
			return ("PermissionError");
		}
		Connection db = null;

		try {

			String filePath = this.getPath(context, "accounts");
			//Process the form data
			HttpMultiPartParser multiPart = new HttpMultiPartParser();
			multiPart.setUsePathParam(false);
			multiPart.setUseUniqueName(true);
			multiPart.setUseDateForFolder(true);
			multiPart.setExtensionId(getUserId(context));
			HashMap parts = multiPart.parseData(context.getRequest(), filePath);
			context.getRequest().setAttribute("parts", parts);
			db = this.getConnection(context);
			
			

			String tempStabId = context.getRequest().getParameter("orgId");
			if (tempStabId == null) {
				tempStabId = ""
					+ (Integer) context.getRequest().getAttribute("orgId");
			}
			// String iter = context.getRequest().getParameter("tipo");
			Integer stabid = null;

			

			if (tempStabId != null) {
				stabid = Integer.parseInt(tempStabId);
			}
			
			Stabilimento newStabilimento = new Stabilimento(db,  stabid);
			newStabilimento.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("OrgDetails", newStabilimento);
			
			Operatore operatore = new Operatore () ;
			operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());

			operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());	
			context.getRequest().setAttribute("Operatore", operatore);

			
			SystemStatus systemStatus = this.getSystemStatus(context);
			context.getRequest().setAttribute("siteId", newStabilimento.getIdAsl());
			context.getRequest().setAttribute("tipologia", org.aspcfs.modules.troubletickets.base.Ticket.TIPO_OPU);
			org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric =new  org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
			controlliGeneric.executeCommandUploadListaDistribuzione(context,db);


			String gotoInsert 		=(String)parts.get("gotoPage");
			if("insert".equals(gotoInsert))
			{
				if (((String)parts.get("actionSource")) != null) {
					context.getRequest().setAttribute(
							"actionSource", (String)parts.get("actionSource"));
					return "AddTicketOK";
				}
				context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));
				return ("AddOK");
			}
			else
			{
				return "ModifyOK";
			}

		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);

			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	}


	





	









	public String executeCommandDettaglio(ActionContext context) {
		if (!hasPermission(context, "opu-vigilanza-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		Ticket newTic = null;
		String ticketId = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		try {
			String fromDefectDetails = context.getRequest().getParameter("defectCheck");
			if (fromDefectDetails == null || "".equals(fromDefectDetails.trim())) {
				fromDefectDetails = (String) context.getRequest().getAttribute("defectCheck");
			}

			// Parameters
			ticketId = context.getRequest().getParameter("id");
			// Reset the pagedLists since this could be a new visit to this ticket
			deletePagedListInfo(context, "TicketDocumentListInfo");
			deletePagedListInfo(context, "SunListInfo");
			deletePagedListInfo(context, "TMListInfo");
			deletePagedListInfo(context, "CSSListInfo");
			deletePagedListInfo(context, "TicketsFolderInfo");
			deletePagedListInfo(context, "TicketTaskListInfo");
			deletePagedListInfo(context, "ticketPlanWorkListInfo");
			db = this.getConnection(context);
			// Load the ticket
			newTic = new Ticket();
			newTic.queryRecord(db, Integer.parseInt(ticketId));

			//find record permissions for portal users
			if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
				return ("PermissionError");
			}

		
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList EsitoCampione = new LookupList(db, "lookup_sanzioni_amministrative");
			EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("EsitoCampione", EsitoCampione);


			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			LookupList SanzioniPenali = new LookupList(db, "lookup_sanzioni_penali");
			SanzioniPenali.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SanzioniPenali", SanzioniPenali);

			LookupList Sequestri = new LookupList(db, "lookup_sequestri");
			Sequestri.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Sequestri", Sequestri);

			 org.aspcfs.modules.campioni.base.TicketList ticList = new org.aspcfs.modules.campioni.base.TicketList();
		        int passedId = newTic.getOrgId();
		        ticList.setOrgId(passedId);
		        ticList.buildListControlli(db, passedId, ticketId);
		        context.getRequest().setAttribute("TicList", ticList);
		        
		         
		        
		        org.aspcfs.modules.tamponi.base.TicketList tamponiList = new org.aspcfs.modules.tamponi.base.TicketList();
		        int pasId = newTic.getOrgId();
		        tamponiList.setOrgId(passedId);
		        tamponiList.buildListControlli(db, pasId, ticketId);
		        context.getRequest().setAttribute("TamponiList", tamponiList);
			
			  if (ticList.size()== 0 && tamponiList.size()==0)
		      {
		    	  newTic.setControllo_chiudibile(true);
		      }
		      else
		      {
		    	  boolean campioni_chiusi = true ;
		    	  boolean tamponi_chiusi = true ;
		    	  Iterator itCampioni = ticList.iterator();
		    	  while (itCampioni.hasNext())
		    	  {
		    		  org.aspcfs.modules.campioni.base.Ticket campione = (org.aspcfs.modules.campioni.base.Ticket)itCampioni.next() ;
		    		  if (campione.getClosed()== null)
		    		  {
		    			  campioni_chiusi = false ;
		    			  break ;
		    		  }
		    		  
		          }
		    	  Iterator itTamponi = tamponiList.iterator();
		    	  while (itTamponi.hasNext())
		    	  {
		    		  org.aspcfs.modules.tamponi.base.Ticket tampone = (org.aspcfs.modules.tamponi.base.Ticket)itTamponi.next() ;
		    		  if (tampone.getClosed()== null)
		    		  {
		    			  tamponi_chiusi = false ;
		    			  break ;
		    		  }
		    		  
		          }
		    	  if (campioni_chiusi == true && tamponi_chiusi == true)
		    	  {
		    		  newTic.setControllo_chiudibile(true);
		    	  }
		    	  else
		    	  {
		    		  newTic.setControllo_chiudibile(false);
		    	  }
		    	  
		      }
			
			
			  try {
					checkControlloBenessere(context, db, Integer.parseInt(ticketId));
					} catch (Exception e) {
						e.printStackTrace();
					}	
	
			
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("TicketDetails", newTic);
		addRecentItem(context, newTic);
		addModuleBean(context, "ViewTickets", "View Tickets");
		String retPage = "DettaglioOK";
		String tipo_richiesta = newTic.getTipo_richiesta();
		tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);
		retPage = "DettaglioOK";

		return ( retPage );
	}






	public String executeCommandChiudi(ActionContext context)
	{
		if (!(hasPermission(context, "opu-vigilanza-edit"))){
			return ("PermissionError");
		}
		int resultCount = -1;
		Connection db = null;
		Ticket thisTicket = null;
		try {
			db = this.getConnection(context);
			thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("id")));
			Ticket oldTicket = new Ticket(db, thisTicket.getId());

			Integer idT = thisTicket.getId();
			String idCU = idT.toString(); 

			thisTicket.setClosed(new Timestamp(System.currentTimeMillis()));

		


			String ticketId = context.getRequest().getParameter("id");

			String chiudiSenzaLista = (String) context.getRequest().getAttribute("ChiudiSenzaLista");


			org.aspcfs.modules.campioni.base.TicketList ticList = new org.aspcfs.modules.campioni.base.TicketList();
			int passedId = thisTicket.getIdStabilimento();
			ticList.setIdStabilimento(passedId);
			ticList.buildListControlli(db, passedId, ticketId);
			
			
			
		

			org.aspcfs.modules.tamponi.base.TicketList tamponiList = new org.aspcfs.modules.tamponi.base.TicketList();
			int pasId = thisTicket.getIdStabilimento();
			tamponiList.setIdStabilimento(passedId);
			tamponiList.buildListControlli(db, pasId, ticketId);




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
			
			
			org.aspcfs.modules.prvvedimentinc.base.TicketList ticListProvvedimenti = new org.aspcfs.modules.prvvedimentinc.base.TicketList();
			int passId = thisTicket.getIdStabilimento();
			ticListProvvedimenti.setIdStabilimento(passId);
			ticListProvvedimenti.buildListControlli(db, passId, ticketId);
			Iterator provvedimentiIterator=ticListProvvedimenti.iterator();


			while(provvedimentiIterator.hasNext()){

				org.aspcfs.modules.prvvedimentinc.base.Ticket tic = (org.aspcfs.modules.prvvedimentinc.base.Ticket) provvedimentiIterator.next();

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


			if(thisTicket.isNcrilevate()==true){
				org.aspcfs.modules.nonconformita.base.TicketList nonCList = new org.aspcfs.modules.nonconformita.base.TicketList();
				int passIdN = thisTicket.getIdStabilimento();
				nonCList.setIdStabilimento(passedId);
				nonCList.buildListControlli(db, passIdN, ticketId);

				Iterator ncIterator=nonCList.iterator();

				while(ncIterator.hasNext()){

					org.aspcfs.modules.nonconformita.base.Ticket tic = (org.aspcfs.modules.nonconformita.base.Ticket) ncIterator.next();

					if(tic.getClosed()==null){
						flag=1;

						break;

					}

				}

			}
			
			//NC di terzi
			org.aspcfs.modules.altriprovvedimenti.base.TicketList nonCList = new org.aspcfs.modules.altriprovvedimenti.base.TicketList();
			int passIdN = thisTicket.getOrgId();
			nonCList.setOrgId(passedId);
			nonCList.buildListControlli(db, passIdN, ticketId);
			Iterator ncIterator=nonCList.iterator();
			while(ncIterator.hasNext()){
				org.aspcfs.modules.altriprovvedimenti.base.Ticket tic = (org.aspcfs.modules.altriprovvedimenti.base.Ticket) ncIterator.next();
				if(tic.getClosed()==null){
					flag=1;

					break;

				}

			}
			//FIne nc di terzi

			if(thisTicket.getTipoCampione()==5){

				org.aspcfs.checklist.base.AuditList audit = new org.aspcfs.checklist.base.AuditList();
				int AuditOrgId = thisTicket.getIdStabilimento();
				String idTi = thisTicket.getPaddedId();
				audit.setOrgId(-1);

				audit.buildListControlli(db, AuditOrgId, idTi);

				Iterator itAudit=audit.iterator();

				if(!itAudit.hasNext()){

					flag=2;

				}else{

					while(itAudit.hasNext()){

						org.aspcfs.checklist.base.Audit auditTemp = (org.aspcfs.checklist.base.Audit) itAudit.next();
						
						
						Stabilimento newStabilimento = new Stabilimento(db,  thisTicket.getIdStabilimento());
						context.getRequest().setAttribute("OrgDetails", newStabilimento);

						if(thisTicket.isCategoriaisAggiornata()==false)
						{
							flag=2;
							break;
						}

					}
				}
			}

			String attivitaCollegate=context.getRequest().getParameter("altro");

			if(attivitaCollegate==null){
				if(flag==1 || flag==2){

					context.getRequest().setAttribute("Chiudi", ""+flag);
					return (executeCommandTicketDetails(context));

				}
			}
			context.getRequest().setAttribute("idStabilimentoopu", ""+thisTicket.getIdStabilimento()+"");
			if(flag==0){
				thisTicket.setModifiedBy(getUserId(context));
				
				thisTicket.setClosed_nolista(chiudiSenzaLista);
				
				resultCount = thisTicket.chiudi(db);
				
				DbiBdu.chiudi_controlo_canile(context, db, thisTicket.getOrgId(), thisTicket.getId(), thisTicket.getAssignedDate(), thisTicket.getTipoControllo(), thisTicket.getClosed(), thisTicket.getTrashedDate());
				DbiBdu.chiudi_controlo_operatore_commerciale(context, db, thisTicket.getOrgId(), thisTicket.getId(), thisTicket.getAssignedDate(), thisTicket.getTipoControllo(), thisTicket.getClosed(), thisTicket.getTrashedDate());

			}
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

	public String executeCommandChiudiTemp(ActionContext context)
	{
		if (!(hasPermission(context, "opu-vigilanza-edit"))){
			return ("PermissionError");
		}
		int resultCount = -1;
		Connection db = null;
		Ticket thisTicket = null;
		try {
			db = this.getConnection(context);
			thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("id")));
			Ticket oldTicket = new Ticket(db, thisTicket.getId());

			Integer idT = thisTicket.getId();

		
				thisTicket.setModifiedBy(getUserId(context));
				resultCount = thisTicket.chiudiTemp(db);
			
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
	public String executeCommandChiudiTutto(ActionContext context) {
//		public String executeCommandChiudi(ActionContext context) {
				if (!(hasPermission(context, "opu-vigilanza-edit"))) {
				return ("PermissionError");
			}
				
			int resultCount = -1;
			Connection db = null;
			//Ticket thisTicket = null;
			
			Ticket thisTicket = null;
			Ticket oldTicket = null;
			
			try {
				db = this.getConnection(context);
				
//				thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("id")));
//				Ticket oldTicket = new Ticket(db, thisTicket.getId());
				
				thisTicket =  new org.aspcfs.modules.vigilanza.base.Ticket(db, Integer.parseInt(context.getRequest().getParameter("id")));
				oldTicket =  thisTicket;

				
				if (!isChiudibileInPresenzaCheckList(thisTicket, db)){
					context.getRequest().setAttribute("Chiudi", "" + 5);
					return (executeCommandTicketDetails(context));
				}
				
				context.getRequest().setAttribute("idStabilimentoopu", ""+thisTicket.getIdStabilimento()+"");
				
				org.aspcf.modules.controlliufficiali.action.AccountVigilanza controlliGeneric = new org.aspcf.modules.controlliufficiali.action.AccountVigilanza();
				int flag=controlliGeneric.executeCommandChiudiTutto(context,db, thisTicket, oldTicket);

				if ( flag==4) {
					thisTicket.setModifiedBy(getUserId(context));
					resultCount = thisTicket.chiudiTemp(db);
					//return (executeCommandTicketDetails(context));
				}
				
				//if (flag == 0 || flag == 1 || flag == 2 || flag==3) {
				if (flag == 0 || flag == 1 || flag==3) {
					thisTicket.setModifiedBy(getUserId(context));
					resultCount = thisTicket.chiudi(db);
					DbiBdu.chiudi_controlo_canile(context, db, thisTicket.getOrgId(), thisTicket.getId(), thisTicket.getAssignedDate(), thisTicket.getTipoControllo(), thisTicket.getClosed(), thisTicket.getTrashedDate());
					DbiBdu.chiudi_controlo_operatore_commerciale(context, db, thisTicket.getOrgId(), thisTicket.getId(), thisTicket.getAssignedDate(), thisTicket.getTipoControllo(), thisTicket.getClosed(), thisTicket.getTrashedDate());

				}
				if (resultCount == -1) {
					context.getRequest().setAttribute("Chiudi", "" + flag);
					return (executeCommandTicketDetails(context));
				} else if (resultCount == 1) {
					thisTicket.queryRecord(db, thisTicket.getId());
					this.processUpdateHook(context, oldTicket, thisTicket);
					context.getRequest().setAttribute("Chiudi", "" + flag);
					return (executeCommandTicketDetails(context));
				}
			} catch (Exception errorMessage) {
				errorMessage.printStackTrace();
				context.getRequest().setAttribute("Error", errorMessage);
				return ("SystemError");
			} finally {
				this.freeConnection(context, db);
			}
			context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
			return ("UserError");
		}

private void checkControlloBenessere(ActionContext context, Connection db, int ticketId) throws SQLException{
	/* Controlli sul benessere */
	
	String sql = null;
	PreparedStatement pst = null;
	
	//Estraggo dati dalla linea del controllo
	sql = "select l.decodifica_tipo_produzione_bdn, l.decodifica_codice_orientamento_bdn, l.decodifica_specie_bdn, l.livello, l.id_attivita "
          + " from linee_attivita_controlli_ufficiali olacu "
          + " join opu_relazione_stabilimento_linee_produttive rel on olacu.id_linea_attivita = rel.id "
          + " join ml8_linee_attivita_nuove_materializzata l on l.id_nuova_linea_attivita = rel.id_linea_produttiva "
          + " where olacu.id_controllo_ufficiale   = ? and olacu.trashed_date is null ";
	pst = db.prepareStatement(sql);
	pst.setInt(1, ticketId);
	ResultSet rs1 = pst.executeQuery();
	String allev = "";
	String specieAllev= "";
	int idAttivita = -1;
	int livello = -1;
	// Il CU riguarda una linea che prevede le checklist? 
	
	if (rs1.next()) {
		allev = rs1.getString("decodifica_tipo_produzione_bdn");
		specieAllev = rs1.getString("decodifica_specie_bdn");
		idAttivita = rs1.getInt("id_attivita");
		livello = rs1.getInt("livello");
	}
	
	//La linea del controllo e' una foglia? Estraggo dati dal terzo livello
	if (livello>3){
	
		sql = "select decodifica_tipo_produzione_bdn, decodifica_codice_orientamento_bdn, decodifica_specie_bdn "
		          + " from ml8_linee_attivita_nuove_materializzata "
		         + " where livello = 3 and id_attivita   = ?  ";
			pst = db.prepareStatement(sql);
			pst.setInt(1, idAttivita);
			ResultSet rs2 = pst.executeQuery();
			// Il CU riguarda una linea che prevede le checklist? 
			
			if (rs2.next()) {
				allev = rs2.getString("decodifica_tipo_produzione_bdn");
				specieAllev = rs2.getString("decodifica_specie_bdn");
			}
	}
	
	if (allev!=null && !"".equals(allev)) {
	
	// e' un controllo sul benessere animale??

	sql = " select ticketid,'benessere' as tipo_scheda from ticket t left join tipocontrolloufficialeimprese tc on tc.idcontrollo = t.ticketid and tc.enabled "
			+ " left join lookup_piano_monitoraggio lpm on lpm.code = tc.pianomonitoraggio where t.trashed_date is null and t.tipologia = 3 and "
			+ " lpm.codice_interno  in  ('982', '983')  and t.ticketid = ? ";

	// Aggiungo condizionalita'
	sql = sql
			+ " UNION "
			+ " select ticketid,'condizionalita' as tipo_scheda from ticket t left join tipocontrolloufficialeimprese tc on tc.idcontrollo = t.ticketid and tc.enabled left join lookup_tipo_ispezione on tc.tipoispezione =lookup_tipo_ispezione.code  "
			+ " where t.trashed_date is null and t.tipologia = 3 and tc.pianomonitoraggio = -1 and tc.id_lookup_condizionalita = 5 and lookup_tipo_ispezione.codice_interno ilike '47a' "
			+ " and t.ticketid = ?";
	
	pst = db.prepareStatement(sql);
	pst.setInt(1, ticketId);
	pst.setInt(2, ticketId);
	ResultSet rs = pst.executeQuery();
	boolean controlloBenessere = false;
	String checkScheda = null;
	HashMap<Integer, Boolean> hashSchede = new HashMap<>();

	if (rs.next()) {
		String tipo_scheda = rs.getString(2);
		if (tipo_scheda.equalsIgnoreCase("benessere"))
			controlloBenessere = true;
	}

	if (controlloBenessere) {
		sql = null;
		sql = " select * from chk_bns_mod_ist left join lookup_chk_bns_mod mod on mod.code = id_alleg where idcu = ? and bozza = false and id_alleg not in (15,20, 22) and trashed_date is null";
		pst = null;
		pst = db.prepareStatement(sql);
		pst.setInt(1,ticketId);
		rs = pst.executeQuery();
		checkScheda = "nd";
		while (rs.next()) {
			checkScheda = "esiste";
			hashSchede.put(rs.getInt("codice_specie"), rs.getBoolean("bozza"));
		}

	}

	boolean nonConfCondizionalita = false;
	String conformita = "false";
	String sql2 = " select  count(*) as totale from  chk_bns_mod_ist i left join chk_bns_risposte r on r.idmodist = i.id "
			+ " left join ticket t on t.ticketid = i.idcu and t.tipologia = 3 "
			+ " where domanda_non_pertinente <> true and r.esito = false and i.id_alleg = 15 and t.trashed_date is null"
			+ " and i.trashed_date is null and i.idcu = ? ";
	PreparedStatement pst2 = null;
	ResultSet rs2 = null;
	pst2 = db.prepareStatement(sql2);
	pst2.setInt(1, ticketId);
	rs2 = pst2.executeQuery();

	if (rs2.next()) {
		if (rs2.getInt("totale") > 0) {
			nonConfCondizionalita = true;
			conformita = "true";
		}
	}

	context.getRequest().setAttribute("shedaBenessere", checkScheda);
	context.getRequest().setAttribute("conformitaB11", conformita);
	context.getRequest().setAttribute("hashSchede", hashSchede);
	context.getRequest().setAttribute("specieAllev", specieAllev);

}
	else
		context.getRequest().setAttribute("shedaBenessere", null);

}

private boolean isChiudibileInPresenzaCheckList(Ticket newTicket, Connection db) throws SQLException{
	boolean isChiudibile = true;
	
	//Estraggo dati dalla linea del controllo
	String	sql = "select l.decodifica_tipo_produzione_bdn, l.decodifica_codice_orientamento_bdn, l.decodifica_specie_bdn, l.livello, l.id_attivita "
	          + " from linee_attivita_controlli_ufficiali olacu "
	          + " join opu_relazione_stabilimento_linee_produttive rel on olacu.id_linea_attivita = rel.id "
	          + " join ml8_linee_attivita_nuove_materializzata l on l.id_nuova_linea_attivita = rel.id_linea_produttiva "
	          + " where olacu.id_controllo_ufficiale   = ? and olacu.trashed_date is null";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, newTicket.getId());
		ResultSet rs1 = pst.executeQuery();
		String allev = "";
		String specieAllev= "";
		int idAttivita = -1;
		int livello = -1;
		// Il CU riguarda una linea che prevede le checklist? 
		
		if (rs1.next()) {
			allev = rs1.getString("decodifica_tipo_produzione_bdn");
			specieAllev = rs1.getString("decodifica_specie_bdn");
			idAttivita = rs1.getInt("id_attivita");
			livello = rs1.getInt("livello");
		}
		
		//La linea del controllo e' una foglia? Estraggo dati dal terzo livello
		if (livello>3){
		
			sql = "select decodifica_tipo_produzione_bdn, decodifica_codice_orientamento_bdn, decodifica_specie_bdn "
			          + " from ml8_linee_attivita_nuove_materializzata "
			         + " where livello = 3 and id_attivita   = ?  ";
				pst = db.prepareStatement(sql);
				pst.setInt(1, idAttivita);
				ResultSet rs2 = pst.executeQuery();
				// Il CU riguarda una linea che prevede le checklist? 
				
				if (rs2.next()) {
					allev = rs2.getString("decodifica_tipo_produzione_bdn");
					specieAllev = rs2.getString("decodifica_specie_bdn");
				}
		}
		
		if (allev!=null && !"".equals(allev)) {
	
			String[] codiciInterniBenessere = {"982", "983"} ;
			String codiciInterniBenessereCondizionalita = "1483" ;
			String codiceInterno = "";
		
		boolean flagCondiz = false ;
		boolean schedaChiusa = false;
		
		for(Piano p : newTicket.getPianoMonitoraggio()) {
			codiceInterno= p.getCodice_interno();
			flagCondiz = p.isFlagCondizionalita();
			
			if (Arrays.asList(codiciInterniBenessere).contains(codiceInterno)) {	
				 sql = null;
					sql = " select * from chk_bns_mod_ist left join lookup_chk_bns_mod mod on mod.code = id_alleg where idcu = ? and bozza = false and id_alleg not in (15,20,22) and trashed_date is null";
					pst = null;
					pst = db.prepareStatement(sql);
					pst.setInt(1, newTicket.getId());
					ResultSet rs = pst.executeQuery();
					
					while (rs.next()) {
						schedaChiusa = true;
					}
					if (!schedaChiusa)
						return false;
			  }
			
			  if (codiciInterniBenessereCondizionalita.contains(codiceInterno) && flagCondiz==true && newTicket.getTipo_ispezione_condizionalita().keySet().contains(Ticket.TIPO_CONDIZIONALISTA_ATTO_B11)){
				  boolean nonConfCondizionalita = false;
					String sql2 = "select * from  chk_bns_mod_ist i left join chk_bns_risposte r on r.idmodist = i.id  left join ticket t on t.ticketid = i.idcu and t.tipologia = 3  where bozza is false and i.id_alleg in (15,20, 22) and t.trashed_date is null and i.trashed_date is null and i.idcu = ?";
					PreparedStatement pst2 = null;
					ResultSet rs2 = null;
					pst2 = db.prepareStatement(sql2);
					pst2.setInt(1, newTicket.getId());
					rs2 = pst2.executeQuery();

					if (rs2.next()) {
						schedaChiusa = true;
					}
					if (!schedaChiusa)
						return false;
		   }
	}
		
	return isChiudibile;
	
}
		else
			return true;
}

public String executeCommandChiudiInAssenzaDiNc(ActionContext context) {
	Connection db = null;
	Ticket thisTicket = null;

	try {
		db = this.getConnection(context);
		thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("id")));

		// // Controllo se esiste un'istanza
		// String queryIstanza =
		// "select * from chk_bns_mod_ist where idcu = ? and trashed_date is null";
		// PreparedStatement ps1 = db.prepareStatement(queryIstanza);
		// ps1.setInt(1, thisTicket.getId());
		// ResultSet rs1 = ps1.executeQuery();
		// while (rs1.next()){
		// // Controllo se la lista ha punteggio maggiore di zero
		// int idIstanza = rs1.getInt("id");
		// }

		String query = "select * from get_punteggio_totale_chk_bns_animale(?)";
		PreparedStatement ps1 = db.prepareStatement(query);
		ps1.setInt(1, thisTicket.getId());
		ResultSet rs1 = ps1.executeQuery();
		int tot = -1, totA = 0, totB = 0, totC = 0;
		if (rs1.next()) {
			totA = rs1.getInt("totalea");
			totB = rs1.getInt("totaleb");
			totC = rs1.getInt("totalec");
			tot = totA + totB + totC;
		}

		String query2 = "select * from get_non_conformita_b11(?)";
		PreparedStatement ps2 = db.prepareStatement(query2);
		ps2.setInt(1, thisTicket.getId());
		ResultSet rs2 = ps2.executeQuery();
		int totb11 = -1;
		if (rs2.next()) {
			totb11 = rs2.getInt("totale");
		}

		if (tot > 0) {
			context.getRequest()
					.setAttribute(
							"ErroreAccountVigilanza",
							"Impossibile chiudere il controllo ufficiale. Risultano inserite delle liste di riscontro per il benessere animale con irregolarita' rilevate.");
			return executeCommandTicketDetails(context);
		} else if (totb11 > 0) {
			context.getRequest()
					.setAttribute(
							"ErroreAccountVigilanza",
							"Impossibile chiudere il controllo ufficiale. Risulta compilata la checklist condizionalita' b11 con non conformita' rilevate.");
			return executeCommandTicketDetails(context);
		} else {
			context.getRequest().setAttribute("ChiudiSenzaLista", "true");
			return executeCommandChiudi(context);
		}

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		this.freeConnection(context, db);
	}
	return "null";

}
}
