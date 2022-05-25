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
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.opu.base.*;
import org.aspcfs.modules.actions.CFSModule;

import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.cessazionevariazione.base.Ticket;
import org.aspcfs.utils.DataVolturaException;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;

/**
 * Maintains Tickets related to an Account
 *
 * @author chris
 * @version $Id: AccountTickets.java,v 1.13 2002/12/24 14:53:03 mrajkowski Exp
 *          $
 * @created August 15, 2001
 */
public final class AccountCessazionevariazione extends CFSModule {

	/**
	 * Sample action for prototyping, by including a specified page
	 *
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandDefault(ActionContext context) {
		String module = context.getRequest().getParameter("module");
		String includePage = context.getRequest().getParameter("include");
		context.getRequest().setAttribute("IncludePage", includePage);
		addModuleBean(context, module, module);
		return getReturn(context, "Include");
	}


	/**
	 * Load the ticket details tab
	 *
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandTicketDetails(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-cessazionevariazione-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		//Parameters
		String ticketId = context.getRequest().getParameter("id");
		String retPag = null;
		try {
			db = this.getConnection(context);
			// Load the ticket
			Ticket newTic = new Ticket();
			SystemStatus systemStatus = this.getSystemStatus(context);
			newTic.setSystemStatus(systemStatus);
			newTic.queryRecord(db, Integer.parseInt(ticketId));
			newTic.popolaVoltureAslCoinvolte(db);
			//find record permissions for portal users
			if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
				return ("PermissionError");
			}


			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);


			

			context.getRequest().setAttribute("TicketDetails", newTic);

			retPag = "Details_" + newTic.getTipo_richiesta() + "OK";

			addRecentItem(context, newTic);
			// Load the organization for the header
			Stabilimento thisOrganization = new Stabilimento(db, newTic.getIdStabilimento());
			context.getRequest().setAttribute("OrgDetails", thisOrganization);
			
			
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

	public String executeCommandCessazionevariazioneDetails(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-cessazionevariazione-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		//Parameters
		String ticketId = context.getRequest().getParameter("id");
		try {
			db = this.getConnection(context);
			// Load the ticket
			org.aspcfs.modules.cessazionevariazione.base.Ticket newTic = new org.aspcfs.modules.cessazionevariazione.base.Ticket();
			SystemStatus systemStatus = this.getSystemStatus(context);
			newTic.setSystemStatus(systemStatus);
			newTic.queryRecord(db, Integer.parseInt(ticketId));

			//find record permissions for portal users
			if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
				return ("PermissionError");
			}


			LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
			TipoLocale.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoLocale", TipoLocale);

			LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
			TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoStruttura", TipoStruttura);


			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);


			context.getRequest().setAttribute("TicketDetails", newTic);
			addRecentItem(context, newTic);
			// Load the organization for the header
			Stabilimento thisOrganization = new Stabilimento(db, newTic.getIdStabilimento());
			context.getRequest().setAttribute("OrgDetails", thisOrganization);
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
		return getReturn(context, "TicketDetails");
	}

	public String executeCommandAddTicket(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-cessazionevariazione-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		Ticket newTic = null;
		Stabilimento newOrg = null;
		Operatore op = null ;
		String retPag = "";
		//Parameters
		String temporgId = context.getRequest().getParameter("stabId");
		String opId = context.getRequest().getParameter("opId");
		int tempid = Integer.parseInt(temporgId);
		int tmpopid = Integer.parseInt(opId);
		try {
			db = this.getConnection(context);
			//find record permissions for portal users

			SystemStatus systemStatus = this.getSystemStatus(context);

			LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
			TipoLocale.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoLocale", TipoLocale);

			LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
			TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoStruttura", TipoStruttura);


			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);


			//Organization for header
			newOrg = new Stabilimento(db, tempid);
			op = new Operatore ();
			op.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());
			op.queryRecordOperatore(db, tmpopid);
			//Ticket
			newTic = (Ticket) context.getFormBean();

			newTic.setIdStabilimento(tempid);

			//newOrg.setComuni2(db, ( (UserBean) context.getSession().getAttribute("User")).getSiteId() );
			addModuleBean(context, "View Accounts", "Add a Ticket");
			context.getRequest().setAttribute("OrgDetails", newOrg);
			context.getRequest().setAttribute("Operatore", op);


			//getting current date in mm/dd/yyyy format
			String currentDate = getCurrentDateAsString(context);
			context.getRequest().setAttribute("currentDate", currentDate);
			context.getRequest().setAttribute(
					"systemStatus", this.getSystemStatus(context));
			String tipo_richiesta = context.getRequest().getParameter( "tipo_richiesta" );
			retPag = "Add_" + tipo_richiesta + "OK";
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		//return getReturn(context, "AddTicket");
		return retPag;
	}


	public String executeCommandInsertTicket(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-cessazionevariazione-add")) {
			return ("PermissionError");
		}
		context.getRequest().setAttribute("dataVolturaError", null);
		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = true;
		Ticket newTicket = null;
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = user.getUserRecord().getIp();
		try {

			db = this.getConnection(context);

			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
			TipoLocale.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoLocale", TipoLocale);

			LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
			TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoStruttura", TipoStruttura);


			//Display account name in the header
			String temporgId = context.getRequest().getParameter("stabId");
			String tmpopid = context.getRequest().getParameter("opId");

			int tempid = Integer.parseInt(temporgId);
			int opid = Integer.parseInt(tmpopid);

			

			Stabilimento newOrg = new Stabilimento(db, tempid);
			Operatore operatore = new Operatore ();
			
			operatore.queryRecordOperatore(db, opid);
			context.getRequest().setAttribute("Operatore", operatore);
			org.aspcfs.modules.cessazionevariazione.base.Ticket voltura = new org.aspcfs.modules.cessazionevariazione.base.Ticket();
			String idV= newOrg.getIdVoltura(db, newOrg.getIdStabilimento());
			if(idV==null){

				Ticket datiIniziali = new Ticket();
				//	     datiIniziali.setId(Integer.parseInt(newOrg.getIdVoltura(db, newOrg.getOrgId())));
				datiIniziali.setOperazione("Avvio Attivita");
				datiIniziali.setProblem("Avvio Attivita");
				datiIniziali.setIdStabilimento(newOrg.getIdStabilimento());
				datiIniziali.setModifiedBy(getUserId(context));
				datiIniziali.setEnteredBy(getUserId(context));
				datiIniziali.setName(operatore.getRagioneSociale());
				datiIniziali.setBanca(operatore.getRagioneSociale());
				datiIniziali.setPartitaIva(operatore.getPartitaIva());
				datiIniziali.setTipo_richiesta("autorizzazione_trasporto_animali_vivi");
				datiIniziali.setClosed(new Timestamp(System.currentTimeMillis()));
				datiIniziali.setIpEntered(ip);
				datiIniziali.setCodiceFiscaleRappresentante("Avvio Attivita");
				datiIniziali.setIpModified(ip);
				datiIniziali.setIdRappresentanteLegaleOperatore(operatore.getRappLegale().getId_opu_soggetto_fisico_storico());
				datiIniziali.setIdRappresentanteLegaleStabilimento(newOrg.getRappLegale().getId_opu_soggetto_fisico_storico());
				datiIniziali.insert(db,context);
			}

		
			SoggettoFisico soggettoAdded = new SoggettoFisico(context.getRequest());
			Indirizzo add = soggettoAdded.getIndirizzo();
			soggettoAdded.setIndirizzo(add);
			newOrg.setRappLegale(soggettoAdded);
			SoggettoFisico soggettoAdded2 = new SoggettoFisico(context.getRequest());
			Indirizzo add2 = soggettoAdded.getIndirizzo();
			soggettoAdded2.setIndirizzo(add2);
			operatore.setRappLegale(soggettoAdded2);

			int idRappresentanteOperatoreStorico = -1 ;
			int idRappresentanteStabilimentoStorico = -1 ;
			if(context.getRequest().getParameter("addressLegaleLine1Stab") != null)
			{
				soggettoAdded = new SoggettoFisico();
				soggettoAdded.popolaBeanRappresentanteStabilimentoVoltura(context.getRequest());
				SoggettoFisico soggettoEsistente = soggettoAdded.verificaSoggetto(db);

				/*se il soggetto non esiste lo aggiungo */
				if(soggettoEsistente == null || soggettoEsistente.getIdSoggetto() <=0)
				{
					idRappresentanteStabilimentoStorico = soggettoAdded.insert(db,context);
					newOrg.setRappLegale(soggettoAdded);
				}
				else
				{
					/*se esiste */

					if ( soggettoEsistente.getIdSoggetto() >0)
					{
						Indirizzo indirizzoAdded = soggettoAdded.getIndirizzo();
						Indirizzo indirizzoEsistente = soggettoEsistente.getIndirizzo() ;

						if ("si".equalsIgnoreCase(context.getParameter("sovrascriviStab")))
						{
							soggettoEsistente.setNome(soggettoAdded.getNome());
							soggettoEsistente.setCognome(soggettoAdded.getCognome());
							soggettoEsistente.setDataNascita(soggettoAdded.getDataNascita());
							soggettoEsistente.setComuneNascita(soggettoAdded.getComuneNascita());


							soggettoEsistente.setDocumentoIdentita(soggettoAdded.getDocumentoIdentita());
							soggettoEsistente.setFax(soggettoAdded.getFax());
							soggettoEsistente.setIndirizzo(indirizzoAdded);
							soggettoEsistente.setProvinciaNascita(soggettoAdded.getProvinciaNascita());
							soggettoEsistente.setTelefono1(soggettoAdded.getTelefono1());
							soggettoEsistente.setTelefono2(soggettoAdded.getTelefono2());
							soggettoEsistente.setEmail(soggettoAdded.getEmail());
							soggettoEsistente.setModifiedBy(user.getUserId());
							soggettoEsistente.setIpModifiedBy(user.getUserRecord().getIp());


							idRappresentanteStabilimentoStorico = soggettoEsistente.update(db,context);

							newOrg.setRappLegale(soggettoEsistente);
							/*temporaneamente prendo quello che ho */

						}
						else
						{
							idRappresentanteStabilimentoStorico = soggettoEsistente.getId_opu_soggetto_fisico_storico();
							
						}

					}

				}
			}

			if(context.getRequest().getParameter("addressLegaleLine1") != null)
			{
				soggettoAdded = new SoggettoFisico(context.getRequest());
				SoggettoFisico soggettoEsistente = soggettoAdded.verificaSoggetto(db);

				/*se il soggetto non esiste lo aggiungo */
				if(soggettoEsistente == null || soggettoEsistente.getIdSoggetto() <=0)
				{
					idRappresentanteOperatoreStorico  =soggettoAdded.insert(db,context);
					operatore.setRappLegale(soggettoAdded);
				}
				else
				{
					/*se esiste */

					if ( soggettoEsistente.getIdSoggetto() >0)
					{
						Indirizzo indirizzoAdded = soggettoAdded.getIndirizzo();
						Indirizzo indirizzoEsistente = soggettoEsistente.getIndirizzo() ;

						if ("si".equalsIgnoreCase(context.getParameter("sovrascrivi")))
						{
							soggettoEsistente.setNome(soggettoAdded.getNome());
							soggettoEsistente.setCognome(soggettoAdded.getCognome());
							soggettoEsistente.setDataNascita(soggettoAdded.getDataNascita());
							soggettoEsistente.setComuneNascita(soggettoAdded.getComuneNascita());


							soggettoEsistente.setDocumentoIdentita(soggettoAdded.getDocumentoIdentita());
							soggettoEsistente.setFax(soggettoAdded.getFax());
							soggettoEsistente.setIndirizzo(indirizzoAdded);
							soggettoEsistente.setProvinciaNascita(soggettoAdded.getProvinciaNascita());
							soggettoEsistente.setTelefono1(soggettoAdded.getTelefono1());
							soggettoEsistente.setTelefono2(soggettoAdded.getTelefono2());
							soggettoEsistente.setEmail(soggettoAdded.getEmail());
							soggettoEsistente.setModifiedBy(user.getUserId());
							soggettoEsistente.setIpModifiedBy(user.getUserRecord().getIp());


							idRappresentanteOperatoreStorico  = soggettoEsistente.update(db,context);

							operatore.setRappLegale(soggettoEsistente);
							/*temporaneamente prendo quello che ho */

						}
						else
						{
							idRappresentanteOperatoreStorico = soggettoEsistente.getId_opu_soggetto_fisico_storico();
						}

					}

				}
			}
			
			Ticket newTic = (Ticket) context.getFormBean();
			newTic.setOperazione("Voltura");
			newTic.setProblem("Voltura");
			newTic.setCodiceFiscaleRappresentante("Voltura");
			newTic.setTipo_richiesta( context.getRequest().getParameter( "tipo_richiesta" ) );
			newTic.setIpEntered(ip);
			newTic.setIpModified(ip);
			newTic.setEnteredBy(getUserId(context));
			newTic.setModifiedBy(getUserId(context));
			newTic.setIdRappresentanteLegaleOperatore(idRappresentanteOperatoreStorico);
			newTic.setIdRappresentanteLegaleStabilimento(idRappresentanteStabilimentoStorico);
			newTic.setIdStabilimento(newOrg.getIdStabilimento());
			
			
			 SoggettoFisico rappImpresa =  new SoggettoFisico();
			 rappImpresa.queryRecordStorico(db, idRappresentanteOperatoreStorico);
		     SoggettoFisico rapStab =  new SoggettoFisico();
		     rapStab.queryRecordStorico(db, idRappresentanteStabilimentoStorico);
		     newTic.setRappresentateImpresa(rappImpresa);
		     newTic.setRappresentateStabilimento(rapStab);
		     operatore.setRagioneSociale(newTic.getName());
		     operatore.setPartitaIva(newTic.getPartitaIva());
		     
		     int idAsl = newOrg.getIdAsl() ;
		     StabilimentoList listaStab = operatore.getListaStabilimenti();
		     Iterator<Stabilimento> itLista = listaStab.iterator();
		     boolean flagAslDiverse = false ;
		     while (itLista.hasNext())
		     {
		    	 Stabilimento thisStab = itLista.next();
		    	 if (idAsl != thisStab.getIdAsl())
		    	 {
		    		 flagAslDiverse = true ;
		    		 break ;
		    	 }
		     }
		     
		     if (flagAslDiverse==false)
		    	 operatore.esegui_voltura(db);
		   
		    
		     newOrg.esegui_voltura(db);
		     context.getRequest().setAttribute("OrgDetails", newOrg);



			isValid = this.validateObject(context, db, newTic) && isValid;
			if (isValid) {
				
				 if (flagAslDiverse==true)
				 {
					 newTic.setProblem("In Approvazione");
					 newTic.setOperazione("In Approvazione");

					 
				 }
				 recordInserted = newTic.insert(db,context);
				 newTic.rinsertRichiestaVoltura(db, listaStab, idAsl);
				 
			}

			if (recordInserted) {
				//Reload the ticket for the details page... redundant to do here
				newTicket = new Ticket(db, newTic.getId());
				context.getRequest().setAttribute("TicketDetails", newTicket);

			}
			addModuleBean(context, "View Accounts", "Ticket Insert ok");

		} 
		catch (DataVolturaException dve) {
			context.getRequest().setAttribute("dataVolturaError", dve);
			return (executeCommandAddTicket(context));
		}
		catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		if (recordInserted) {
			//return getReturn(context, "InsertTicket");
			return "Details_" + newTicket.getTipo_richiesta() + "OK";
		}
		return (executeCommandAddTicket(context));
	}

	
	public String executeCommandViewCessazionevariazioneRichieste(ActionContext context) throws SQLException, IndirizzoNotFoundException {
		if (!hasPermission(context,
				"accounts-accounts-cessazionevariazione-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		org.aspcfs.modules.cessazionevariazione.base.TicketList ticList = new org.aspcfs.modules.cessazionevariazione.base.TicketList();
		
		// 
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		int passedId =-1;
		
		if (context.getRequest().getParameter("stabId")!=null)
		{
		passedId = Integer.parseInt(context.getRequest().getParameter("stabId"));
		}
		else
		{
			if (context.getRequest().getAttribute("stabId")!=null)
				passedId = Integer.parseInt((String)context.getRequest().getAttribute("stabId"));
		}
		
		
		int opId =-1;
		if (context.getRequest().getParameter(
		"opId")!=null)
		{
		opId= Integer.parseInt(context.getRequest().getParameter(
		"opId"));
		}
		else
		{
			if (context.getRequest().getAttribute(
			"opId")!=null)
			{
			opId= Integer.parseInt((String)context.getRequest().getAttribute(
			"opId"));
			}
		}
		
		// Prepare PagedListInfo
		PagedListInfo ticketListInfo = this.getPagedListInfo(context,
				"AccountTicketInfo", "t.entered", "desc");
		ticketListInfo.setLink("OpuCessazioneVariazione.do?command=ViewCessazionevariazioneRichieste&stabId="+ passedId+"&opId="+opId);
		ticList.setPagedListInfo(ticketListInfo);
		try {
			db = this.getConnection(context);
			// find record permissions for portal users
			
			
			LookupList AslList = new LookupList(db,"lookup_site_id");
			context.getRequest().setAttribute("AslList", AslList);
			
			ticList.buildListRichiesteVolture(db, opId, user.getSiteId());
			context.getRequest().setAttribute("TicList", ticList);
			addModuleBean(context, "View Accounts", "Accounts View");
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "ViewCessazionevariazioneRichieste");
	}
	
	
	
	public String executeCommandConfermaVoltura(ActionContext context) throws SQLException, IndirizzoNotFoundException {
		if (!hasPermission(context, "accounts-accounts-cessazionevariazione-add")) {
			return ("PermissionError");
		}
		context.getRequest().setAttribute("dataVolturaError", null);
		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = true;
		Ticket newTicket = null;
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = user.getUserRecord().getIp();
		try {

			db = this.getConnection(context);

			LookupList SiteIdList = new LookupList(db, "lookup_site_id");
			SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", SiteIdList);

			LookupList TipoLocale = new LookupList(db, "lookup_tipo_locale");
			TipoLocale.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoLocale", TipoLocale);

			LookupList TipoStruttura = new LookupList(db, "lookup_tipo_struttura");
			TipoStruttura.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("TipoStruttura", TipoStruttura);


			//Display account name in the header
			String temporgId = context.getRequest().getParameter("stabId");
			String tmpopid = context.getRequest().getParameter("opId");
			String idVolt = context.getRequest().getParameter("idAllerta");

			context.getRequest().setAttribute("opId", tmpopid)	;
			context.getRequest().setAttribute("stabId", tmpopid)	;
			int tempid = Integer.parseInt(temporgId);
			int opid = Integer.parseInt(tmpopid);
			int idVoltura = Integer.parseInt(idVolt) ;
			
			Ticket newTic = new Ticket (db,idVoltura);

			Operatore operatore = new Operatore ();
			operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());

			operatore.queryRecordOperatore(db, opid);
			
			org.aspcfs.modules.cessazionevariazione.base.Ticket voltura = new org.aspcfs.modules.cessazionevariazione.base.Ticket();
			
			int idAsl = Integer.parseInt(context.getParameter("idAslVoltura"));
		
			

			int idRappresentanteOperatoreStorico = -1 ;
			int idRappresentanteStabilimentoStorico = -1 ;
			// approvo la voltura per l'asl idAsl
			boolean flagaggiornaImpresa = newTic.approvaVolturaOpuAsl(db, idAsl);
			
			// verifico se tutte le asl sono state approvate , riporto le modifiche sull'impresa
			if (flagaggiornaImpresa == true)
			{
			newTic.setOperazione("Voltura");
			
			newTic.updateStaoVoltura(db);
			
			SoggettoFisico rappLegale = operatore.getRappLegale();
			SoggettoFisico rappLegaleVoltura = newTic.getRappresentateImpresa();
			rappLegaleVoltura.setIdSoggetto(rappLegale.getIdSoggetto());
			rappLegaleVoltura.update(db,context);
			
			operatore.setRagioneSociale(newTic.getName());
			operatore.setPartitaIva(newTic.getPartitaIva());
			operatore.setRappLegale(rappLegaleVoltura);
			operatore.esegui_voltura(db);
			
			}
			
			
		     

			context.getRequest().setAttribute("Operatore", operatore);
			if (recordInserted) {
				//Reload the ticket for the details page... redundant to do here
				newTicket = new Ticket(db, newTic.getId());
				context.getRequest().setAttribute("TicketDetails", newTicket);

			}
			addModuleBean(context, "View Accounts", "Ticket Insert ok");

		} 
		
		catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		
		return (executeCommandViewCessazionevariazioneRichieste(context));
	}
}
