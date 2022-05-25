/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.controlliufficiali.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import org.aspcfs.modules.nonconformita.base.ElementoNonConformita;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.products.base.CustomerProduct;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.registrotrasgressori.base.AnagraficaPagatore;
import org.aspcfs.modules.sanzioni.base.Competenza;
import org.aspcfs.modules.sanzioni.base.Ticket;
import org.aspcfs.modules.sintesis.base.SintesisOperatoreMercato;
import org.aspcfs.modules.troubletickets.base.TicketCategory;
//import org.aspcfs.modules.troubletickets.base.TicketCategoryList;
import org.aspcfs.modules.troubletickets.base.TicketDefect;
import org.aspcfs.modules.troubletickets.base.TicketDefectList;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class Sanzioni  extends CFSModule {


	public String executeCommandToRegistroOrdonanze(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-sanzioni-add")) {
			return ("PermissionError");
		}


		return "ToRegistroOrdonanzeOK" ;	
	}

	public String executeCommandAdd(ActionContext context) {
		
		Connection db = null;
		Ticket newTic = null;
		try {
			db = this.getConnection(context);
			SystemStatus systemStatus = this.getSystemStatus(context);
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
			context.getRequest().setAttribute("ListaNorme",listaNorme);
			
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
			
			LookupList SanzioniAutoritaCompetenti = new LookupList(db, "lookup_sanzioni_autorita_competenti");
			SanzioniAutoritaCompetenti.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SanzioniAutoritaCompetenti", SanzioniAutoritaCompetenti);
			
			LookupList SanzioniEntiDestinatari = new LookupList(db, "lookup_sanzioni_enti_destinatari");
			SanzioniEntiDestinatari.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SanzioniEntiDestinatari", SanzioniEntiDestinatari);

			Organization thisOrganization = new Organization(db, Integer.parseInt(id));
//			if (context.getParameter("orgId")==null && context.getParameter("stabId")!=null)
//			{
//				thisOrganization.setIdStabilimento(Integer.parseInt(context.getParameter("stabId")));
//				thisOrganization.setOrgId(-1);
//				thisOrganization.setIdApiario(Integer.parseInt(context.getParameter("stabId")));
//
//			}
//			else if (context.getParameter("altId")!=null )
//			{
//				thisOrganization.setAltId(Integer.parseInt(context.getParameter("altId")));
//				thisOrganization.setOrgId(-1);
//			}
			context.getRequest().setAttribute("OrgDetails", thisOrganization);
			context.getRequest().setAttribute("TipoNC",context.getRequest().getParameter("tipoNc"));
			
			Organization organizationSanzionata = null;
			if (context.getParameter("orgIdSanzionata")!=null && !context.getParameter("orgIdSanzionata").equals("") && !context.getParameter("orgIdSanzionata").equals("null"))
			{
				int orgIdSanzionata = Integer.parseInt(context.getParameter("orgIdSanzionata"));
				
				//int tipologiaOp = Integer.parseInt(context.getParameter("tipologiaOp"));
				
				int tipologiaOp = -1;
				
				if (context.getParameter("tipologiaOp")!=null)
					tipologiaOp = Integer.parseInt(context.getParameter("tipologiaOp"));
				else {
					PreparedStatement pst = db.prepareStatement("select tipologia_operatore from ricerche_anagrafiche_old_materializzata where riferimento_id  = ? and riferimento_id_nome_col = 'org_id' limit 1");
					pst.setInt(1, orgIdSanzionata);
					ResultSet rs = pst.executeQuery();
					if (rs.next())
						tipologiaOp = rs.getInt("tipologia_operatore");
				}
				
				organizationSanzionata = new Organization();
				organizationSanzionata.queryRecord(db, orgIdSanzionata,tipologiaOp);
				context.getRequest().setAttribute("OrgDetailsSanzionata", organizationSanzionata);
				
				if (orgIdSanzionata==-999)
					context.getRequest().setAttribute("OrgDetailsFuoriRegione", "true");
			}
			
			Stabilimento operatoreMercatoSanzionato = null;
			if (context.getParameter("relOperatoreMercato")!=null && !context.getParameter("relOperatoreMercato").equals("") && !context.getParameter("relOperatoreMercato").equals("null") && !context.getParameter("relOperatoreMercato").equals("-1"))
			{
				int relOperatoreMercato = Integer.parseInt(context.getParameter("relOperatoreMercato"));
				SintesisOperatoreMercato operatore = new SintesisOperatoreMercato(db, relOperatoreMercato);
				operatoreMercatoSanzionato = operatore.getOpuStabilimento();
				context.getRequest().setAttribute("OperatoreMercatoDetailsSanzionato", operatoreMercatoSanzionato);
			}
		
				AnagraficaPagatore pagatoreDefault = new AnagraficaPagatore(); 
				if (organizationSanzionata!=null && organizationSanzionata.getName()!=null)
					pagatoreDefault.recuperaDatiDefault(db, organizationSanzionata);
				else if (thisOrganization!=null && thisOrganization.getName()!=null)
					pagatoreDefault.recuperaDatiDefault(db, thisOrganization);
				context.getRequest().setAttribute("PagatoreDefault", pagatoreDefault);


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
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = user.getUserRecord().getIp();
		newTic.setIpEntered(ip);
		newTic.setIpModified(ip);
		newTic.setNumVerbaleSequestro(context.getParameter("verbalesequestro"));
		newTic.setSequestroRiduzione(context.getParameter("seqRiduzioneApplicata"));
		String id = context.getRequest().getParameter("idC");
		context.getRequest().setAttribute("idC",id);
		String idControlloUfficiale = (String.valueOf(Integer.parseInt(id)));
		while (idControlloUfficiale.length() < 6) {
			idControlloUfficiale = "0" + idControlloUfficiale;
		}
		newTic.setIdControlloUfficiale(idControlloUfficiale);
		String id2 = context.getRequest().getParameter("idNC");
		context.getRequest().setAttribute("idNC",id2);
		context.getRequest().setAttribute("dataC", ""+newTic.getAssignedDate());
		newTic.setTipo_nc(Integer.parseInt(context.getRequest().getParameter("tipoNc")));
		
		
		
//		if (context.getParameter("stabId") != null && !context.getParameter("stabId").equals("null") &&  newTic.getIdApiario() > 0){
//			newTic.setIdStabilimento(0);
//		}else if(context.getParameter("stabId") != null && !context.getParameter("stabId").equals("null")) {
//			newTic.setIdStabilimento(Integer.parseInt(context.getParameter("stabId")));
//		}
			
		
		
		

		



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
		if(context.getRequest().getParameter("trasgressore").equals("Altro"))
		newTic.setTrasgressore(context.getRequest().getParameter("trasgressorealtro"));
		else
			newTic.setTrasgressore(context.getRequest().getParameter("trasgressore"));

		
		if(!context.getRequest().getParameter("obbligatoinSolido").equals("Altro"))
			newTic.setObbligatoinSolido(context.getRequest().getParameter("obbligatoinSolido"));
			else
				newTic.setObbligatoinSolido(context.getRequest().getParameter("obbligatoinSolidoAltro"));
		
//		if(!context.getRequest().getParameter("obbligatoinSolido2").equals("Altro"))
//			newTic.setObbligatoinSolido2(context.getRequest().getParameter("obbligatoinSolido2"));
//			else
//				newTic.setObbligatoinSolido2(context.getRequest().getParameter("obbligatoinSolidoAltro23"));
//		
//		
//		if(!context.getRequest().getParameter("obbligatoinSolido3").equals("Altro"))
//			newTic.setObbligatoinSolido3(context.getRequest().getParameter("obbligatoinSolido3"));
//			else
//				newTic.setObbligatoinSolido3(context.getRequest().getParameter("obbligatoinSolidoAltro3"));
//		

	
		if(!context.getRequest().getParameter("pagamento").equals(""))
			newTic.setPagamento(Double.parseDouble(context.getRequest().getParameter("pagamento")));
	
		try {
			db = this.getConnection(context);
			
			org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket();
			cu.queryRecord(db, Integer.parseInt(id));
			
			if (cu.getAltId()>0)
			{
				newTic.setAltId(cu.getAltId());
			}
			
			if (cu.getIdApiario()>0)
			{
				newTic.setIdStabilimento(0);
				newTic.setIdApiario(cu.getIdApiario());
				newTic.setOrgId(0);
			}
			else
			{
				if (cu.getIdStabilimento()>0)
				{
					newTic.setIdStabilimento(cu.getIdStabilimento());
					newTic.setIdApiario(0);
					newTic.setOrgId(0);
				}
				else
				{
					if (cu.getIdStabilimento()>0)
					{
						newTic.setIdStabilimento(0);
						newTic.setIdApiario(0);
						newTic.setOrgId(newTic.getOrgId());
					}
				}
			}

			
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
//			org.aspcfs.modules.vigilanza.base.Ticket cu =new org.aspcfs.modules.vigilanza.base.Ticket(db,Integer.parseInt(idControlloUfficiale));
			newTic.setAssignedDate(cu.getAssignedDate());

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
			int count = 0;
			isValid = this.validateObject(context, db, newTic) && isValid;
			if (isValid) {
				newTic.setTestoAppoggio("Inserimento"); //aggiunto da d.dauria
				recordInserted = newTic.insert(db,context);
				newTic.insertAzioniNonConforme(db, context.getRequest().getParameterValues("Provvedimenti"));
				//Gestione norme violate multiple con lookup NEW
				if(context.getRequest().getParameterValues("listaNorme") != null){
								
					if(context.getRequest().getParameter("tipoNC") == null || !context.getRequest().getParameter("tipoNC").equals("10"))
						count = newTic.disableInsertNormeViolate(db, context.getRequest().getParameterValues("listaNorme"));
					else
						newTic.insertNormeViolate(db, context.getRequest().getParameterValues("listaNorme"));
					//newTic.insertNormeViolate(db, context.getRequest().getParameterValues("listaNorme"));
				}
				if(count == 0){
					//restituire eccezione in sanzioni
					//newTic.cancellaDiffida(db,newTic.getOrgId());
				}
			}


			if (recordInserted) {
				
				gestisciAnagraficaPagatori(db, newTic, context);
				gestisciCompetenza(db, newTic, context);
 
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
		return executeCommandAdd(context); 
	}



	private void gestisciAnagraficaPagatori(Connection db, Ticket sanzione, ActionContext context) throws SQLException {

		int idSanzione = sanzione.getId();
		String trasgressore = sanzione.getTrasgressore();
		String obbligato = sanzione.getObbligatoinSolido();
		
		String tipoPagatoreT = context.getRequest().getParameter("tipoPagatoreT");
		String pivaT = context.getRequest().getParameter("pivaT");
		String nomeT = context.getRequest().getParameter("nomeT");
		String indirizzoT = context.getRequest().getParameter("indirizzoT");
		String civicoT = context.getRequest().getParameter("civicoT");
		String capT = context.getRequest().getParameter("capT");
		String comuneT = context.getRequest().getParameter("comuneT");
		String provinciaT = context.getRequest().getParameter("provinciaT");
		String nazioneT = context.getRequest().getParameter("nazioneT");
		String mailT = context.getRequest().getParameter("mailT");
		String telefonoT = context.getRequest().getParameter("telefonoT");

		AnagraficaPagatore pagatoreT = new AnagraficaPagatore(tipoPagatoreT, pivaT, nomeT, indirizzoT, civicoT, capT, comuneT, provinciaT, nazioneT, mailT, telefonoT);
		
		if (pagatoreT.isVuoto())
			pagatoreT.recuperaDatiDefault(db, idSanzione);
		
		if (!trasgressore.equalsIgnoreCase("Nessuno"))
			pagatoreT.upsert(db, idSanzione, "T", getUserId(context));
		
		String tipoPagatoreO = context.getRequest().getParameter("tipoPagatoreO");
		String pivaO = context.getRequest().getParameter("pivaO");
		String nomeO = context.getRequest().getParameter("nomeO");
		String indirizzoO = context.getRequest().getParameter("indirizzoO");
		String civicoO = context.getRequest().getParameter("civicoO");
		String capO = context.getRequest().getParameter("capO");
		String comuneO = context.getRequest().getParameter("comuneO");
		String provinciaO = context.getRequest().getParameter("provinciaO");
		String nazioneO = context.getRequest().getParameter("nazioneO");
		String mailO = context.getRequest().getParameter("mailO");
		String telefonoO = context.getRequest().getParameter("telefonoO");

		AnagraficaPagatore pagatoreO = new AnagraficaPagatore(tipoPagatoreO, pivaO, nomeO, indirizzoO, civicoO, capO, comuneO, provinciaO, nazioneO, mailO, telefonoO);
		
		if (pagatoreO.isVuoto())
			pagatoreO.recuperaDatiDefault(db, idSanzione);
		
		if (!obbligato.equalsIgnoreCase("Nessuno"))
			pagatoreO.upsert(db, idSanzione, "O", getUserId(context));
		
	}

	private void gestisciCompetenza(Connection db, Ticket sanzione, ActionContext context) throws SQLException {

		int idSanzione = sanzione.getId();
		int idAutoritaCompetente = -1;
		int idEnteDestinatario = -1;
		String descrizioneAutoritaCompetente = null;
		String descrizioneEnteDestinatario = null;
		
		try { idAutoritaCompetente = Integer.parseInt(context.getRequest().getParameter( "idAutoritaCompetente" ) ); } catch (Exception e) {}
		try { idEnteDestinatario = Integer.parseInt(context.getRequest().getParameter( "idEnteDestinatario" ) ); } catch (Exception e) {}
		try { descrizioneAutoritaCompetente = context.getRequest().getParameter( "descrizioneAutoritaCompetente" ); } catch (Exception e) {}
		try { descrizioneEnteDestinatario = context.getRequest().getParameter( "descrizioneEnteDestinatario" ); } catch (Exception e) {}

		Competenza comp = new Competenza();
		comp.setIdSanzione(idSanzione);
		comp.setIdAutoritaCompetente(idAutoritaCompetente);
		comp.setIdEnteDestinatario(idEnteDestinatario);
		comp.setDescrizioneAutoritaCompetente(descrizioneAutoritaCompetente);
		comp.setDescrizioneEnteDestinatario(descrizioneEnteDestinatario);
		comp.upsert(db, getUserId(context));
		
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
		//	Organization thisOrganization = new Organization(db, newTic.getOrgId());
			Organization thisOrganization = new Organization();
			thisOrganization.queryRecord(db, newTic.getOrgId());
			context.getRequest().setAttribute("OrgDetails", thisOrganization);
			org.aspcfs.modules.vigilanza.base.Ticket cu =new org.aspcfs.modules.vigilanza.base.Ticket(db,Integer.parseInt(id));
			newTic.setAssignedDate(cu.getAssignedDate());

			LookupList Provvedimenti = new LookupList();

		
			
			org.aspcfs.modules.nonconformita.base.Ticket nc = new org.aspcfs.modules.nonconformita.base.Ticket(db,newTic.getId_nonconformita());
			if (nc.getId()>0 && nc.getTipologia()==8){
			ArrayList<ElementoNonConformita> listancGravi = nc.getNon_conformita_gravi();
			
			
			
			for(ElementoNonConformita enc :listancGravi)
			{
				
				LookupElement el = new LookupElement();
				el.setCode(enc.getId_nc());
				el.setDescription(enc.getDescrizione_nc());
				Provvedimenti.add(el);
			}
			}
			else {
				org.aspcfs.modules.altriprovvedimenti.base.Ticket ncAltri = new org.aspcfs.modules.altriprovvedimenti.base.Ticket(db,newTic.getId_nonconformita());
				context.getRequest().setAttribute("ncAltri", ncAltri);
				
				Organization organizationSanzionata = new Organization();
				organizationSanzionata.queryRecord(db, ncAltri.getIdImpresaSanzionata(), ncAltri.getIdTipologiaImpresaSanzionata());
				context.getRequest().setAttribute("OrgDetailsSanzionata", organizationSanzionata);
				
				ArrayList<org.aspcfs.modules.altriprovvedimenti.base.ElementoNonConformita> listancGravi = ncAltri.getNon_conformita_gravi();
				for(org.aspcfs.modules.altriprovvedimenti.base.ElementoNonConformita enc :listancGravi)
				{
					
					LookupElement el = new LookupElement();
					el.setCode(enc.getId_nc());
					el.setDescription(enc.getDescrizione_nc());
					Provvedimenti.add(el);
				}
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
		newTic.setNumVerbaleSequestro(context.getParameter("verbalesequestro"));
		newTic.setSequestroRiduzione(context.getParameter("seqRiduzioneApplicata"));
		
		if(context.getRequest().getParameter("trasgressore").equals("Altro"))
			newTic.setTrasgressore(context.getRequest().getParameter("trasgressorealtro"));
			else
				newTic.setTrasgressore(context.getRequest().getParameter("trasgressore"));
		
		newTic.setIdControlloUfficiale(id);
		
		if(!context.getRequest().getParameter("obbligatoinSolido").equals("Altro"))
			newTic.setObbligatoinSolido(context.getRequest().getParameter("obbligatoinSolido"));
			else
				newTic.setObbligatoinSolido(context.getRequest().getParameter("obbligatoinSolidoAltro"));

		
//		if(!context.getRequest().getParameter("obbligatoinSolido2").equals("Altro"))
//			newTic.setObbligatoinSolido2(context.getRequest().getParameter("obbligatoinSolido2"));
//			else
//				newTic.setObbligatoinSolido2(context.getRequest().getParameter("obbligatoinSolidoAltro2"));
//
//		
//		if(!context.getRequest().getParameter("obbligatoinSolido3").equals("Altro"))
//			newTic.setObbligatoinSolido3(context.getRequest().getParameter("obbligatoinSolido3"));
//			else
//				newTic.setObbligatoinSolido3(context.getRequest().getParameter("obbligatoinSolidoAltro3"));

		
		newTic.setNormaviolata(context.getRequest().getParameter("normaviolata"));
		if(!context.getRequest().getParameter("pagamento").equals(""))
			newTic.setPagamento(Double.parseDouble(context.getRequest().getParameter("pagamento")));


		try {
			db = this.getConnection(context);

			Ticket oldTic = new Ticket(db, newTic.getId());



			//Get the previousTicket, update the ticket, then send both to a hook
			Ticket previousTicket = new Ticket(db, Integer.parseInt(context.getParameter("id")));
			newTic.setModifiedBy(getUserId(context));
			//newTic.setSiteId(Organization.getOrganizationSiteId(db, newTic.getOrgId()));
			isValid = this.validateObject(context, db, newTic) && isValid;
			if (isValid) {
				newTic.setTestoAppoggio("Modifica"); //aggiunto da d.dauria  
				resultCount = newTic.update(db);
				newTic.setOrgId(oldTic.getOrgId());
				newTic.updateAzioniNonConforme(db, context.getRequest().getParameterValues("Provvedimenti"));
				if(context.getRequest().getParameterValues("listaNorme") != null){
					newTic.updateNormeViolate(db, context.getRequest().getParameterValues("listaNorme"));
					if(context.getRequest().getParameter("tipoNC") == null || !context.getRequest().getParameter("tipoNC").equals("10")){
					newTic.disableDiffida(db, context.getRequest().getParameterValues("listaNorme"));
					}
				}
			}

			Integer idtampone = newTic.getId();

			//				BeanSaver.save( null, newTic, newTic.getId(),
			//			      		"ticket", context, db, null, "O.S.A: Modifica Sanzioni", idtampone.toString() );


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

			//find record permissions for portal users



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

			LookupList SanzioniAutoritaCompetenti = new LookupList(db, "lookup_sanzioni_autorita_competenti");
			SanzioniAutoritaCompetenti.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SanzioniAutoritaCompetenti", SanzioniAutoritaCompetenti);
			
			LookupList SanzioniEntiDestinatari = new LookupList(db, "lookup_sanzioni_enti_destinatari");
			SanzioniEntiDestinatari.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SanzioniEntiDestinatari", SanzioniEntiDestinatari);


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
		return retPag;
	}

}
