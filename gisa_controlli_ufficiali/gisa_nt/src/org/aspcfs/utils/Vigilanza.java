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
package org.aspcfs.utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

import org.aspcf.modules.controlliufficiali.base.Organization;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.oia.base.OiaNodo;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.vigilanza.base.MotivoIspezione;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;



/**
 * Maintains Tickets related to an Account
 *
 * @author chris
 * @version $Id: AccountTickets.java,v 1.13 2002/12/24 14:53:03 mrajkowski Exp
 *          $
 * @created August 15, 2001
 */
public final class Vigilanza extends CFSModule {

	public String executeCommandTicketDetails(ActionContext context) {
		
		int idControllo = -1;
		int tipologiaOperatore = -1;
		int orgId = -1;
		int idStabilimento = -1;
		int idApiario = -1;
		int altId = -1;
	
		Connection db = null;

		String ticketId = null;
		ticketId = context.getRequest().getParameter("id");
		if (ticketId == null)
			ticketId = (String) context.getRequest().getAttribute("id");

		PreparedStatement pst = null;
		String sql = null;
		ResultSet rs = null;
		try {
			db = this.getConnection(context);
			// Load the ticket
			sql="select * from get_dettaglio_cu(?)";
			pst = db.prepareStatement(sql);
			pst.setInt(1, Integer.parseInt(ticketId));
			rs = pst.executeQuery();
			
			while (rs.next()){
				idControllo = rs.getInt("ticketid");
				tipologiaOperatore = rs.getInt("tipologia_operatore");
				orgId = rs.getInt("org_id");
				idStabilimento = rs.getInt("id_stabilimento");
				idApiario = rs.getInt("id_apiario");
				altId = rs.getInt("alt_Id");
			}
			
			 org.aspcfs.modules.troubletickets.base.Ticket t = new org.aspcfs.modules.troubletickets.base.Ticket();
			 t.setId(idControllo);
			 t.setOrgId(orgId);
			 t.setIdStabilimento(idStabilimento);
			 t.setIdApiario(idApiario);
			 t.setAltId(altId);
			 t.setTipologia_operatore(tipologiaOperatore);
	
			 context.getRequest().setAttribute("TicketDetails", t);
		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		//return getReturn(context, "TicketDetails");
		
		return "DettaglioOK";
	}
	
public String executeCommandCampioneDetails(ActionContext context) {
		
		int idControllo = -1;
		int idCampione = -1;
		int tipologiaOperatore = -1;
		int orgId = -1;
		int idStabilimento = -1;
		int idApiario = -1;
		int altId = -1;
	
		Connection db = null;

		String ticketId = null;
		ticketId = context.getRequest().getParameter("id");
		if (ticketId == null)
			ticketId = (String) context.getRequest().getAttribute("id");
		
		try {idCampione = Integer.parseInt(ticketId);} catch (Exception e){}

		PreparedStatement pst = null;
		String sql = null;
		ResultSet rs = null;
		try {
			db = this.getConnection(context);
			// Load the ticket
			sql="select * from get_dettaglio_cu_da_campione(?)";
			pst = db.prepareStatement(sql);
			pst.setInt(1, Integer.parseInt(ticketId));
			rs = pst.executeQuery();
			
			while (rs.next()){
				idControllo = rs.getInt("ticketid");
				tipologiaOperatore = rs.getInt("tipologia_operatore");
				orgId = rs.getInt("org_id");
				idStabilimento = rs.getInt("id_stabilimento");
				idApiario = rs.getInt("id_apiario");
				altId = rs.getInt("alt_Id");
			}
			
			 org.aspcfs.modules.troubletickets.base.Ticket t = new org.aspcfs.modules.troubletickets.base.Ticket();
			 t.setId(idControllo);
			 t.setOrgId(orgId);
			 t.setIdStabilimento(idStabilimento);
			 t.setIdApiario(idApiario);
			 t.setAltId(altId);
			 t.setTipologia_operatore(tipologiaOperatore);
	
			 context.getRequest().setAttribute("TicketDetails", t);
			 context.getRequest().setAttribute("idCampione", String.valueOf(idCampione));

		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		//return getReturn(context, "TicketDetails");
		
		return "DettaglioCampioneOK";
	}
	
	
	public String executeCommandAddMotivoCU(ActionContext context) {
		
		int idControlloUfficiale = -1;
		try { idControlloUfficiale = Integer.parseInt(context.getRequest().getParameter("idControlloUfficiale")); } catch (Exception e) {}
		if (idControlloUfficiale==-1)
			try { idControlloUfficiale = Integer.parseInt((String)context.getRequest().getAttribute("idControlloUfficiale")); } catch (Exception e) {}
		
		if (idControlloUfficiale==-1)
			return "Error";
		int tipologiaOperatore = -1;
		
		Calendar calCorrente = GregorianCalendar.getInstance();
		Date dataCorrente = new Date(System.currentTimeMillis());
		int tolleranzaGiorni = Integer.parseInt(org.aspcfs.modules.vigilanza.blocchicu.ApplicationProperties.getProperty("TOLLERANZA_MODIFICA_CU"));
		dataCorrente.setDate(dataCorrente.getDate()- tolleranzaGiorni);
		calCorrente.setTime(new Timestamp(dataCorrente.getTime()));
		int anno_corrente = calCorrente.get(Calendar.YEAR);
		
		ArrayList<MotivoIspezione> listaMotiviIspezione = new ArrayList<MotivoIspezione>();

		Connection db = null;

		try {
				db = this.getConnection(context);
				
				Ticket cu = new Ticket(db,idControlloUfficiale);
				
				//Motivi
			
				PreparedStatement pst = null;
				ResultSet rs = null;
		
				// Load the ticket
				pst = db.prepareStatement("select tipologia_operatore from get_dettaglio_cu(?)");
				pst.setInt(1, idControlloUfficiale);
				rs = pst.executeQuery();
				
				while (rs.next()){
					tipologiaOperatore = rs.getInt("tipologia_operatore");
				}
			
				pst = db.prepareStatement("select * from get_motivi_cu_per_aggiunta_motivo(?, ?, ?);"); 
				pst.setInt(1, tipologiaOperatore);
				pst.setInt(2, anno_corrente);
				pst.setInt(3, idControlloUfficiale);
				rs = pst.executeQuery();
				
				while (rs.next()){
					MotivoIspezione motivo = new MotivoIspezione();
					motivo.setIdMotivoIspezione(rs.getInt("id_tipo_ispezione"));
					motivo.setIdPiano(rs.getInt("id_piano"));
					motivo.setDescrizioneMotivoIspezione(rs.getString("descrizione_tipo_ispezione"));
					motivo.setDescrizionePiano(rs.getString("descrizione_piano"));
					motivo.setCodiceInternoMotivoIspezione(rs.getString("codice_int_tipo_ispe"));
					motivo.setCodiceInternoPiano(rs.getString("codice_int_piano"));
					listaMotiviIspezione.add(motivo);
					}
				
			 context.getRequest().setAttribute("idControlloUfficiale", String.valueOf(idControlloUfficiale));
			 context.getRequest().setAttribute("listaMotiviIspezione", listaMotiviIspezione);
			 context.getRequest().setAttribute("Errore", context.getRequest().getParameter("Errore"));

			 
			 // Per conto di
			 
			 
			 int idAsl = cu.getSiteId();
			 
				ArrayList<OiaNodo> nodi = new ArrayList<OiaNodo>();

					db = this.getConnection(context);
					UserBean user=(UserBean)context.getSession().getAttribute("User");
					int idAslnodo = idAsl > 0 ? idAsl : user.getSiteId() ;
					HashMap<Integer,ArrayList<OiaNodo>> strutture = (HashMap<Integer,ArrayList<OiaNodo>>) context.getServletContext().getAttribute("StruttureOIA");
					if(idAslnodo>0)
					{
						ArrayList<OiaNodo> nodiTemp = strutture.get(idAslnodo);
						if (nodiTemp != null)
							nodi = nodiTemp;
					}
					else
					{
						Iterator<Integer> itK = strutture.keySet().iterator();
						while (itK.hasNext())
						{
							int k = itK.next();
							if (k!=8) { //NON CARICO I NODI REGIONALI
							ArrayList<OiaNodo> nodiTemp = strutture.get(k);
							for(OiaNodo nodotemp : nodiTemp)
							{
								nodi.add(nodi.size(), nodotemp);
							}
							}
						}
					}
					
					if (user.getSiteId()<=0){
						ArrayList<OiaNodo> nodiTemp = strutture.get(8);
						for(OiaNodo nodotemp : nodiTemp)
						{
							nodi.add(nodi.size(), nodotemp);
						}			
						}
					
					LookupList SiteIdList = new LookupList();
					SiteIdList.setTable("lookup_site_id");
					SiteIdList.buildListWithEnabled(db);
					SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
					context.getRequest().setAttribute("SiteIdList", SiteIdList);
					
				
				
				context.getRequest().setAttribute("StrutturaAsl", nodi);
			 	 
			 
			 

		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		
		return "AddMotivoCUOK";
		}
	
public String executeCommandInsertMotivoCU(ActionContext context) {
		
		int idControllo = -1;
		try { idControllo = Integer.parseInt(context.getRequest().getParameter("idControlloUfficiale")); } catch (Exception e) {}
		
		if (idControllo==-1)
			return "Error";
		
		context.getRequest().setAttribute("idControlloUfficiale", String.valueOf(idControllo));

		Connection db = null;

		try {
				db = this.getConnection(context);
				
			
				PreparedStatement pst = null;
				ResultSet rs = null;
		
				String[] idMotiviIspezione = context.getRequest().getParameterValues("idMotivoIspezione");
				String[] idPiani = context.getRequest().getParameterValues("idPiano");
				String[] perContoDi = context.getRequest().getParameterValues("perContoDi");
				
				int sizeMotivi = idMotiviIspezione.length;
				int sizePiani = idPiani.length;
				int sizePerContoDi = perContoDi.length;

				if (sizeMotivi!=sizePiani || sizePiani!=sizePerContoDi || sizeMotivi!=sizePerContoDi){
					context.getRequest().setAttribute("Errore", "Errore generico.");
					return executeCommandAddMotivoCU(context);
				}
				
				for (int i = 0; i<sizeMotivi; i++){
					pst = db.prepareStatement("select * from get_motivi_cu_insert_motivo ( ?, ?, ?, ?, ?, ?)");
					pst.setInt(1, idControllo);
					pst.setInt(2, Integer.parseInt(idMotiviIspezione[i]));
					pst.setInt(3, Integer.parseInt(idPiani[i])>0 ? Integer.parseInt(idPiani[i]) : -1);
					pst.setInt(4, Integer.parseInt(perContoDi[i]));
					pst.setString(5, "Motivo inserito tramite funzione di AGGIUNGI PIANO/ATTIVITA da utente "+getUserId(context) + " in data ");
					pst.setInt(6, getUserId(context));
					pst.execute();

				}
	
		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		
		return "InsertMotivoCUOK";
		}
	
public String executeCommandDetailsMotivoCU(ActionContext context) {
	
	Connection db = null;

	String jsonMotivi = "";
	
	String idControllo = null;
	idControllo = context.getRequest().getParameter("idControllo");
	if (idControllo == null)
		idControllo = (String) context.getRequest().getAttribute("idControllo");
	

	PreparedStatement pst = null;
	String sql = null;
	ResultSet rs = null;
	try {
		db = this.getConnection(context);
		// Load the ticket
		sql="select * from get_motivi_controllo_ufficiale(?)";
		pst = db.prepareStatement(sql);
		pst.setInt(1, Integer.parseInt(idControllo));
		rs = pst.executeQuery();
		
		while (rs.next()){
			jsonMotivi = rs.getString(1);
			}
		
		context.getRequest().setAttribute("jsonMotivi", jsonMotivi);

	} catch (Exception errorMessage) {
		errorMessage.printStackTrace();
		context.getRequest().setAttribute("Error", errorMessage);
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}
	//return getReturn(context, "TicketDetails");
	
	return "DetailsMotivoCUOK";
}
	
}
