/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.molluschibivalvi.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.molluschibivalvi.base.OrganizationAddress;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.diffida.base.Ticket;
import org.aspcfs.modules.diffida.base.TicketList;
import org.aspcfs.modules.molluschibivalvi.base.Concessionario;
import org.aspcfs.modules.molluschibivalvi.base.ConcessionarioList;
import org.aspcfs.modules.molluschibivalvi.base.Concessione;
import org.aspcfs.modules.molluschibivalvi.base.ConcessioniList;
import org.aspcfs.modules.molluschibivalvi.base.CoordinateMolluschiBivalvi;
import org.aspcfs.modules.molluschibivalvi.base.HistoryClassificazione;
import org.aspcfs.modules.molluschibivalvi.base.HistoryClassificazioneList;
import org.aspcfs.modules.molluschibivalvi.base.OrganizationList;
import org.aspcfs.modules.molluschibivalvi.base.Organization;
import org.aspcfs.modules.stabilimenti.base.OperatoriAssociatiMercatoIttico;
import org.aspcfs.utils.AjaxCalls;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;









import sun.nio.cs.HistoricallyNamedCharset;

import com.darkhorseventures.framework.actions.ActionContext;

public class Accounts extends CFSModule{


	public String executeCommandDefault(ActionContext context)
	{
		return executeCommandSearchForm(context);

	}
	
	private String getCamminoById(Connection db , int idMatrice)
	{
		
		String selMatrice = 
			"WITH RECURSIVE recursetree(id, parent_ids, nome_foglia ) AS (  " +
			"SELECT matrice_id,nome,livello,id_padre,CAST(id_padre As varchar(1000)) as path_id ,CAST(nome As varchar(1000)) As path_desc   " +
			"FROM matrici WHERE id_padre = -1   " +
			"UNION ALL   " +
			"SELECT   " +
			"t.matrice_id,t.nome,t.livello,t.id_padre,   " +
			"CAST(rt.path_id || ';' || t.id_padre As varchar(1000)),CAST(rt.path_desc || '->' || t.nome As varchar(1000)) As path_desc    " +
			"FROM matrici t   " +
			"JOIN recursetree rt ON rt.id = t.id_padre where t.matrice_id =?  " +
			")   " +
			"SELECT * FROM recursetree where id = ?";
		
		
		String cammino = "" ;
		try
		{
			PreparedStatement pst = db.prepareStatement(selMatrice);
			pst.setInt(1, idMatrice) ;
			pst.setInt(2, idMatrice) ;
			ResultSet rs = pst.executeQuery() ;
			if(rs.next())
			{
				cammino = rs.getString("path_desc"); 
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return cammino ; 
	}
	
	public String executeCommandListConcessioniScadenza(ActionContext context) {
		if (!(hasPermission(context, "molluschibivalvi-scadenziario-view"))) {
			return ("PermissionError");
		}
		addModuleBean(context, "Home", "");
		if (getUserId(context) == 0) {
			return ("MaintenanceModeOK");
		}
		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

		Connection db = null;



		try
		{
			db = getConnection(context);
			UserBean user = (UserBean)context.getSession().getAttribute("User");
			final LookupList asl = new LookupList(db,"lookup_site_id");
			PreparedStatement	stat	= null ;
			ConcessioniList list = new ConcessioniList();
			PagedListInfo searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
			searchListInfo.setLink("MolluschiBivalvi.do?command=ListConcessioniScadenza");
			searchListInfo.setListView("all");
			list.setSiteId(user.getSiteId());
			list.setInScadenza(true);
			list.buildList(db);
			
			context.getRequest().setAttribute( "LitaConcessioni", list );
			
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}
		finally
		{
			
			freeConnection(context, db);
		}

		return "ListConcessioniOK";
	}
	
	

	public String executeCommandHistoryClassificazioni(ActionContext context) {
		if (!(hasPermission(context, "molluschibivalvi-history-classificazioni-view"))) {
			return ("PermissionError");
		}
		addModuleBean(context, "Home", "");
		if (getUserId(context) == 0) {
			return ("MaintenanceModeOK");
		}
		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
		User thisRec = thisUser.getUserRecord();

		Connection db = null;

		

		try
		{
			db = getConnection(context);
			UserBean user = (UserBean)context.getSession().getAttribute("User");
		
			
			Organization orgDetails = new Organization(db,Integer.parseInt(context.getParameter("idZona")));
			HistoryClassificazioneList list = new HistoryClassificazioneList();
		
			list.setIdZona(Integer.parseInt(context.getParameter("idZona")));
			list.buildList(db);
			
			orgDetails.setListaDecreti(list);
			
			context.getRequest().setAttribute( "OrgDetails", orgDetails );
			
			LookupList zoneProduzione = new LookupList(db, "lookup_zone_di_produzione");
			zoneProduzione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("ZoneProduzione", zoneProduzione);

			LookupList classi = new LookupList(db, "lookup_classi_acque");
			classi.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Classificazione", classi);
			
			LinkedHashMap<Integer, String> storicoStati = new LinkedHashMap();
			PreparedStatement pst = db.prepareStatement("select * from storico_stati_molluschi where id_zona ="+context.getParameter("idZona")+" order by entered desc");
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				int id = rs.getInt("id");
				String valori = rs.getString("operazione")+";;"+rs.getString("numero_decreto")+";;"+rs.getString("data")+";;"+rs.getString("data_provvedimento");
				storicoStati.put(id, valori);
			}
			context.getRequest().setAttribute("storicoStati", storicoStati);

			context.getRequest().setAttribute("orgId", context.getParameter("idZona"));
			
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}
		finally
		{
			
			freeConnection(context, db);
		}

		
		String tipo = context.getRequest().getParameter("tipo");
		if(tipo!=null && tipo.equals("sanitaria"))
			return "HistorySanitariaOK";
		return "HistoryClassificazioniOK";
	}
	
	public String executeCommandViewCampioni(ActionContext context) {
		if (!(hasPermission(context, "molluschibivalvi-campioni-globali-view"))) {
			return ("PermissionError");
		}
		addModuleBean(context, "Home", "");
		if (getUserId(context) == 0) {
			return ("MaintenanceModeOK");
		}
		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
		User thisRec = thisUser.getUserRecord();

		Connection db = null;

		

		try
		{
			db = getConnection(context);
			UserBean user = (UserBean)context.getSession().getAttribute("User");
			
			org.aspcfs.modules.campioni.base.TicketList campioni = new org.aspcfs.modules.campioni.base.TicketList();
			campioni.setOrgId(context.getRequest().getParameter("idZona"));
			campioni.buildListbyOrgId(db,campioni.getOrgId());
			
			context.getRequest().setAttribute("CampioniList",campioni) ;
			LookupList EsitoCampione = new LookupList(db,
			"lookup_esito_campione");
	
	context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}
		finally
		{
			
			freeConnection(context, db);
		}

		return "CampioniListOK";
	}
	
	public String executeCommandHistoryConcessioni(ActionContext context) {
		if (!(hasPermission(context, "molluschibivalvi-history-classificazioni-view"))) {
			return ("PermissionError");
		}
		addModuleBean(context, "Home", "");
		if (getUserId(context) == 0) {
			return ("MaintenanceModeOK");
		}
		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
		User thisRec = thisUser.getUserRecord();

		Connection db = null;

		

		try
		{
			db = getConnection(context);
			UserBean user = (UserBean)context.getSession().getAttribute("User");
		
			ConcessioniList list = new ConcessioniList();
			PagedListInfo searchListInfo = this.getPagedListInfo(context, "SearchHistoryOrgListInfo");
			searchListInfo.setLink("MolluschiBivalvi.do?command=HistoryConcessioni&idZona="+context.getParameter("idZona"));
			searchListInfo.setListView("all");
			list.setIdZona(Integer.parseInt(context.getParameter("idZona")));
			list.setTrashed(true);
			list.setEnabled(false);
			list.buildList(db);
			
			context.getRequest().setAttribute( "HistoryConcessioni", list );
			LookupList zoneProduzione = new LookupList(db, "lookup_zone_di_produzione");
			zoneProduzione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("ZoneProduzione", zoneProduzione);

			LookupList classi = new LookupList(db, "lookup_classi_acque");
			classi.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Classificazione", classi);
			context.getRequest().setAttribute("orgId", context.getParameter("idZona"));

		}
		catch ( Exception e )
		{
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}
		finally
		{
			
			freeConnection(context, db);
		}

		return "HistoryConcessioniOK";
	}
	
	
	public String executeCommandViewVigilanza(ActionContext context) {
		if (!hasPermission(context, "accounts-accounts-vigilanza-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		org.aspcfs.modules.vigilanza.base.TicketList ticList = new org.aspcfs.modules.vigilanza.base.TicketList();
		Organization newOrg = null;
		// Process request parameters
		int passedId = Integer.parseInt(context.getRequest().getParameter(
				"orgId"));

		// Prepare PagedListInfo
		PagedListInfo ticketListInfo = this.getPagedListInfo(context,
				"AccountTicketInfo", "t.assigned_date", "desc");
		ticketListInfo.setLink("MolluschiBivalvi.do?command=ViewVigilanza&orgId="
				+ passedId);
		ticList.setPagedListInfo(ticketListInfo);
		try {

			db = this.getConnection(context);
			SystemStatus systemStatus = this.getSystemStatus(context);
			LookupList TipoCampione = new LookupList(db,
					"lookup_tipo_controllo");
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList AuditTipo = new LookupList(db, "lookup_audit_tipo");
			AuditTipo.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AuditTipo", AuditTipo);

			LookupList TipoAudit = new LookupList(db, "lookup_tipo_audit");
			TipoAudit.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoAudit", TipoAudit);

			LookupList TipoIspezione = new LookupList(db,
					"lookup_tipo_ispezione");
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);

			org.aspcfs.modules.vigilanza.base.TicketList controlliList = new org.aspcfs.modules.vigilanza.base.TicketList();
			controlliList.setOrgId(passedId);
			/*
			 * int punteggioAccumulato =
			 * controlliList.buildListControlliUltimiAnni(db, passedId);
			 * context.getRequest().setAttribute("punteggioUltimiAnni",
			 * punteggioAccumulato);
			 */
			LookupList EsitoCampione = new LookupList(db,
					"lookup_esito_campione");
			EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
			// find record permissions for portal users
			if (!isRecordAccessPermitted(context, db, passedId)) {
				return ("PermissionError");
			}
			newOrg = new Organization(db, passedId);
			ticList.setOrgId(passedId);
			if (newOrg.isTrashed()) {
				ticList.setIncludeOnlyTrashed(true);
			}
			
			UserBean thisUser = (UserBean) context.getSession().getAttribute("User"); 
			if (thisUser.getRoleId()==Role.RUOLO_CRAS)
				ticList.setIdRuolo(thisUser.getRoleId());
			
			ticList.buildList(db);

			context.getRequest().setAttribute("TicList", ticList);
			context.getRequest().setAttribute("OrgDetails", newOrg);
			addModuleBean(context, "View Accounts", "Accounts View");
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			errorMessage.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "ViewVigilanza");
	}
	
	public String executeCommandAddConcessione(ActionContext context) {
		
		
		// Process request parameters
		int idZona	 = Integer.parseInt(context.getRequest().getParameter("orgId"));
		int idConcessionario = Integer.parseInt(context.getRequest().getParameter("idConcessionario"));
		Connection db = null;
		try {
			db = getConnection(context);
			Organization o = new Organization(db,idZona);
			Concessionario concessionario = new Concessionario(db,idConcessionario);
			context.getRequest().setAttribute("OrgDetails", o);
			context.getRequest().setAttribute("Concessionario", concessionario);
		}
		catch(SQLException e){
			e.printStackTrace();
			
		}
		finally
		{
			
			freeConnection(context, db);
		}
		return "addConcessionarioOK";
	}
	
	

	public String executeCommandSearchForm(ActionContext context) {
		if (!(hasPermission(context, "molluschi-molluschi-view"))) {
			return ("PermissionError");
		}
		if (isPortalUser(context)) {
			return (executeCommandSearch(context));
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = getConnection(context);
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", siteList);

			LookupList statiClassificazione = new LookupList(db, "lookup_stato_classificazione");
			statiClassificazione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("StatiClassificazione", statiClassificazione);
			
			LookupList zoneProduzione = new LookupList(db, "lookup_zone_di_produzione");
			zoneProduzione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("ZoneProduzione", zoneProduzione);

			LookupList classi = new LookupList(db, "lookup_classi_acque");
			classi.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Classificazione", classi);
			
			HashMap<Integer, String> tipoMoll = new HashMap<Integer, String>();
			String selMoll = "select matrice_id,nome from matrici where id_padre = 289  and nuova_gestione = true and enabled = true order by nome";
			PreparedStatement pst = db.prepareStatement(selMoll);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next())
			{
				tipoMoll.put(rs.getInt(1), rs.getString(2));
			}
		
			context.getRequest().setAttribute("Molluschi", tipoMoll);
			
			org.aspcfs.modules.accounts.base.Comuni c = new org.aspcfs.modules.accounts.base.Comuni();
			UserBean user = (UserBean) context.getSession().getAttribute("User");
			ArrayList<org.aspcfs.modules.accounts.base.Comuni> listaComuni = c.buildList(db, user.getSiteId());
			
			LookupList comuniList = new LookupList(listaComuni);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);

			PagedListInfo orgListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
			orgListInfo.setCurrentLetter("");
			orgListInfo.setCurrentOffset(0);

		} 
		catch (Exception e) 
		{
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Search Accounts", "Accounts Search");
		return ("SearchOK");
	}

	public String executeCommandSearch(ActionContext context) {
		if (!hasPermission(context, "molluschi-molluschi-view")) {
			return ("PermissionError");
		}
		OrganizationList organizationList = new OrganizationList();
		organizationList.setTipologia(201);
		addModuleBean(context, "View Accounts", "Search Results");
		PagedListInfo searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
		searchListInfo.setLink("MolluschiBivalvi.do?command=Search");
		searchListInfo.setListView("all");
		Connection db = null;
		try {
			db = this.getConnection(context);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", siteList);

			LookupList zoneProduzione = new LookupList(db, "lookup_zone_di_produzione");
			zoneProduzione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("ZoneProduzione", zoneProduzione);
			
			LookupList classi = new LookupList(db, "lookup_classi_acque");
			context.getRequest().setAttribute("Classificazione", classi);
			
			LookupList statiClassificazione = new LookupList(db, "lookup_stato_classificazione");
			context.getRequest().setAttribute("StatiClassificazione", statiClassificazione);

			searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
			searchListInfo.setLink("MolluschiBivalvi.do?command=Search");
			if (context.getParameter("scadenzario")!=null)
			{
				organizationList.setTipoMolluschi(2);
				organizationList.setScadenzario(true);
				searchListInfo.setColumnToSortBy("o.data_fine_classificazione");
				searchListInfo.setLink("MolluschiBivalvi.do?command=Search&scadenzario=true");
				searchListInfo.setListView("all");
			}else
			{
				searchListInfo.setColumnToSortBy("o.site_id,oa.city, o.name");
			}
			
			organizationList.setSpecieMolluschi(context.getRequest().getParameterValues("molluschi"));
			organizationList.setOnlyCun(context.getRequest().getParameter("onlyCun"));
			organizationList.setPagedListInfo(searchListInfo);
			organizationList.setTipologia(201) ;
			searchListInfo.setSearchCriteria(organizationList, context);
			organizationList.buildList(db);
			context.getRequest().setAttribute("OrgList", organizationList);
			context.getSession().setAttribute("previousSearchType", "accounts");
			if (context.getParameter("scadenzario")!=null)
			{
				return "ListScadenzarioOK";
			}
			return "ListOK";

		} catch (Exception e) {
			//Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	}
	
	


	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "molluschi-molluschi-add")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {

			db = this.getConnection(context);
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", siteList);
			
			/*PADRE MOLLUSCHI BIVALVI*/
			HashMap<Integer, String> tipoMoll = new HashMap<Integer, String>();
			String selMoll = "select matrice_id,nome from matrici where id_padre = 289  and nuova_gestione = true and enabled = true order by nome";
			PreparedStatement pst = db.prepareStatement(selMoll);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next())
			{
				tipoMoll.put(rs.getInt(1), rs.getString(2));
			}
		
			context.getRequest().setAttribute("Molluschi", tipoMoll);
			LookupList zoneProduzione = new LookupList(db, "lookup_zone_di_produzione");
			zoneProduzione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("ZoneProduzione", zoneProduzione);
			zoneProduzione.removeElementByLevel(1); // zone in concessione

			LookupList classi = new LookupList(db, "lookup_classi_acque");
			classi.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Classificazione", classi);
			Organization newOrg = (Organization) context.getFormBean();
			newOrg.setComuni2(db, ((UserBean) context.getSession().getAttribute("User")).getSiteId());

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Add Account", "Accounts Add");
		context.getRequest().setAttribute("systemStatus",
				this.getSystemStatus(context));
		// if a different module reuses this action then do a explicit return
		if (context.getRequest().getParameter("actionSource") != null) {
			return getReturn(context, "AddAccount");
		}

		return getReturn(context, "Add");
	}

	private HashMap<Integer, String>  buildTipoMolluschibyRequest(ActionContext context,Connection db)
	{
		
		String [] molluschiSelezionati = context.getRequest().getParameterValues("molluschi");
		HashMap<Integer, String> molluschi = new HashMap<Integer, String>();
		if(molluschiSelezionati != null) {
			for (int i = 0 ; i <molluschiSelezionati.length ; i++)
			{
				int idMollusco = Integer.parseInt(molluschiSelezionati[i]) ;
				String cammino = getCamminoById(db,idMollusco);
				molluschi.put(idMollusco, cammino);
			}
		
		}
		return molluschi ;
		
	}
	public String executeCommandInsert(ActionContext context)
	throws SQLException {
		if (!hasPermission(context, "molluschi-molluschi-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		Organization insertedOrg = null;
		Integer orgId = null;
		Organization newOrg = (Organization) context.getFormBean();
		newOrg.setEnteredBy(getUserId(context));
		newOrg.setModifiedBy(getUserId(context));
		newOrg.setIdMasterListSuap(Organization.ID_ML_MOLLUSCHI);
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = user.getUserRecord().getIp();
		newOrg.setIpEntered(ip);
		newOrg.setIpModified(ip);
		
		if (newOrg.getTipoMolluschi()== 4 || newOrg.getTipoMolluschi()== 5 )
			newOrg.setStatoClassificazione(-1);
//		else
//			newOrg.setStatoClassificazione(0);
		
		
		
		try 
		{
			db = this.getConnection(context);
			newOrg.setTipoMolluschiInZone(buildTipoMolluschibyRequest(context,db));
			newOrg.setRequestItems(context);
			if (this.getUserSiteId(context) != -1) 
			{
				if (newOrg.getId() == -1) {
					newOrg.setSiteId(this.getUserSiteId(context));
				} else 
				{
					if (!isRecordAccessPermitted(context, db, newOrg.getId())) 
					{
						return ("PermissionError");
					}
				}
			}
			isValid = this.validateObject(context, db, newOrg);
			OrganizationAddress so = null;
			OrganizationAddress sedeLegale = null;
			OrganizationAddress sedeMobile = null;
			Iterator it = newOrg.getAddressList().iterator();

			while (it.hasNext()) {
				OrganizationAddress thisAddress = (OrganizationAddress) it.next();
				
				if (thisAddress.getType() == 5) {
					so = thisAddress;
				}

				// RICHIAMO METODO PER CONVERSIONE COORDINATE
				String[] coords = null;
				ArrayList<CoordinateMolluschiBivalvi> coordinate = thisAddress.getListaCoordinate();
				for(int i=0;i<coordinate.size(); i++)
				{
					CoordinateMolluschiBivalvi coordinata = coordinate.get(i);
				if (coordinata.getLatitude() != 0 && coordinata.getLongitude() != 0) 
				{
					coords = this.convert2Wgs84UTM33N(Double.toString(coordinata.getLatitude()), Double.toString(coordinata.getLongitude()), db);
					coordinata.setLatitude(coords[1]);
					coordinata.setLongitude(coords[0]);
					coordinate.remove(i);
					coordinate.add(i, coordinata);
					
					
				}
				}
				thisAddress.setListaCoordinate(coordinate);

			}
			if (isValid) 
			{
				recordInserted = newOrg.insert(db,context);
			}
			if (recordInserted) 
			{
				
				insertedOrg = new Organization(db, newOrg.getId());
				context.getRequest().setAttribute("OrgDetails", insertedOrg);
				
				Organization temp = new Organization(db, newOrg.getId());
						}

		} catch (Exception errorMessage) {

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");
		if (recordInserted) {
			return ("InsertOK");
		}

		return (executeCommandAdd(context));
	}

	public String executeCommandDetails(ActionContext context) {
		if (!hasPermission(context, "molluschi-molluschi-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Organization newOrg = null;
		try {

			String temporgId = context.getRequest().getParameter("orgId");
			if (temporgId == null) {
				temporgId = (String) context.getRequest().getAttribute("orgId");
			}
			Integer tempid = null;
			if (temporgId != null) 
			{
				tempid = Integer.parseInt(temporgId);
			} 
			else
			{
				tempid = (Integer) context.getSession().getAttribute("orgIdf5");
			}

			db = this.getConnection(context);

			if (!isRecordAccessPermitted(context, db, tempid)) {
				return ("PermissionError");
			}
			
			
			aggiornaStatoClassificazione(db, tempid);
			
			
			
			
			newOrg = new Organization(db, tempid);

			//Caricamento Diffide
			Ticket t = new Ticket();
			context.getRequest().setAttribute("DiffideList", t.getDiffide(db,false,null,null,newOrg.getOrgId(),null));
			context.getRequest().setAttribute("DiffideListStorico", t.getDiffide(db,true,null,null,newOrg.getOrgId(),null));

			// Dopo l'inserimento riconverti
			Iterator it_coords = newOrg.getAddressList().iterator();
			while (it_coords.hasNext()) {

				org.aspcfs.modules.molluschibivalvi.base.OrganizationAddress thisAddress = (org.aspcfs.modules.molluschibivalvi.base.OrganizationAddress) it_coords.next();
				ArrayList<CoordinateMolluschiBivalvi> listacoordinate = thisAddress.getListaCoordinate() ;
				for (int i = 0 ; i < listacoordinate.size(); i++ )
				{
					CoordinateMolluschiBivalvi coordinata = listacoordinate.get(i);
					String spatial_coords[] = null;
					spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(coordinata.getLatitude()),Double.toString(coordinata.getLongitude()), db);
					
				if (coordinata.getLatitude() != 0 && coordinata.getLongitude() != 0) 
				{
					coordinata.setLatitude(spatial_coords[0]);
					coordinata.setLongitude(spatial_coords[1]);
					listacoordinate.remove(i);
					listacoordinate.add(i,coordinata);
					
				}
				}
				thisAddress.setListaCoordinate(listacoordinate);
			}
			
			
			
			
			LookupList lookup_rifiuto_classificazione = new LookupList(db, "lookup_motivazione_rifuto_classificazione");
			lookup_rifiuto_classificazione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("RifiutoClassificazione", lookup_rifiuto_classificazione);
			
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", siteList);

			LookupList zoneProduzione = new LookupList(db, "lookup_zone_di_produzione");
			zoneProduzione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("ZoneProduzione", zoneProduzione);

			LookupList classi = new LookupList();
			classi.setTable("lookup_classi_acque");
			classi.buildListWithEnabled(db);
			classi.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Classificazione", classi);
			
			LookupList statiClassificazione = new LookupList(db, "lookup_stato_classificazione");
			context.getRequest().setAttribute("StatiClassificazione", statiClassificazione);



		} catch (SQLException e) {

			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "View Account Details");
		context.getRequest().setAttribute("OrgDetails", newOrg);

		return getReturn(context, "Details");

	}

	public String executeCommandClassificaSpecchioAcqueo(ActionContext context) {
		if (!hasPermission(context, "molluschi-molluschi-edit")) {
			return ("PermissionError");
		}
		
		
		String orgid = context.getRequest().getParameter("orgId");
		context.getRequest().setAttribute("orgId", orgid);
		
		String scelta = context.getRequest().getParameter("scelta");
		context.getRequest().setAttribute("scelta", scelta);
		
		int tempid = Integer.parseInt(orgid);
		Connection db = null;
		Organization newOrg = null;
		try {
			db = this.getConnection(context);
			newOrg = new Organization(db, tempid);
			Iterator it_coords = newOrg.getAddressList().iterator();
			while (it_coords.hasNext()) {

				org.aspcfs.modules.molluschibivalvi.base.OrganizationAddress thisAddress = (org.aspcfs.modules.molluschibivalvi.base.OrganizationAddress) it_coords.next();
				if (thisAddress.getLatitude() != 0 && thisAddress.getLongitude() != 0) {
					String spatial_coords[] = null;
					spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(thisAddress.getLatitude()),Double.toString(thisAddress.getLongitude()), db);
//					if (Double.parseDouble(spatial_coords[0].replace(',','.')) < 39.988475 || Double.parseDouble(spatial_coords[0].replace(',', '.')) > 41.503754) 
//					{
//						AjaxCalls ajaxCall = new AjaxCalls();
//						String[] coordinate = ajaxCall.getCoordinate(thisAddress.getStreetAddressLine1(), thisAddress.getCity(), thisAddress .getState(), thisAddress.getZip(), "" + thisAddress.getLatitude(), "" + thisAddress.getLongitude(), "");
//						thisAddress.setLatitude(coordinate[1]);
//						thisAddress.setLongitude(coordinate[0]);
//					} 
//					else 
//					{
						thisAddress.setLatitude(spatial_coords[0]);
						thisAddress.setLongitude(spatial_coords[1]);
					//}

				}
			}
			
			/*PADRE MOLLUSCHI BIVALVI*/
			HashMap<Integer, String> tipoMoll = new HashMap<Integer, String>();
			String selMoll = "select matrice_id,nome from matrici where id_padre = 289  and nuova_gestione = true and enabled = true order by nome ";
			PreparedStatement pst = db.prepareStatement(selMoll);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next())
			{
				tipoMoll.put(rs.getInt(1), rs.getString(2));
			}
			
			context.getRequest().setAttribute("Molluschi", tipoMoll);
			
			
			HashMap<Integer, String> tipoMotivi = new HashMap<Integer, String>();
			String selMotivi = "select code, description from lookup_classi_acque where enabled and level>0";
			PreparedStatement pst2 = db.prepareStatement(selMotivi);
			ResultSet rs2 = pst2.executeQuery() ;
			while (rs2.next())
			{
				tipoMotivi.put(rs2.getInt(1), rs2.getString(2));
			}
			context.getRequest().setAttribute("Motivi", tipoMotivi);
			
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", siteList);

			LookupList zoneProduzione = new LookupList(db, "lookup_zone_di_produzione");
			zoneProduzione.addItem(-1, "-- SELEZIONA VOCE --");
			zoneProduzione.removeElementByLevel(4);
			zoneProduzione.removeElementByLevel(5);
			context.getRequest().setAttribute("ZoneProduzione", zoneProduzione);

			LookupList statiClassificazione = new LookupList(db, "lookup_stato_classificazione");
			
//			if (newOrg.getStatoClassificazione()!=1 && newOrg.getStatoClassificazione()!=2){
//				statiClassificazione.removeElementByCode(1);
//				statiClassificazione.removeElementByCode(2);
//			}
			
		
//			if (newOrg.getStatoClassificazione()==0){
//				statiClassificazione.removeElementByCode(1);
//				statiClassificazione.removeElementByCode(2);
//			}
//			else if (newOrg.getStatoClassificazione()==1){
//				statiClassificazione.removeElementByCode(0);
//				statiClassificazione.removeElementByCode(2);
//			}
//			else if (newOrg.getStatoClassificazione()==2){
//				statiClassificazione.removeElementByCode(0);
//				statiClassificazione.removeElementByCode(1);
//			}
//			else if (newOrg.getStatoClassificazione()==3){
//				statiClassificazione.removeElementByCode(1);
//				statiClassificazione.removeElementByCode(2);
//			}
//			else if (newOrg.getStatoClassificazione()==4){
//				statiClassificazione.removeElementByCode(0);
//				statiClassificazione.removeElementByCode(1);
//				statiClassificazione.removeElementByCode(2);
//				statiClassificazione.removeElementByCode(3);
//		}
			
			statiClassificazione.removeElementByCode(1);
			statiClassificazione.removeElementByCode(2);
			
			if (scelta!=null && scelta.equals("attiva")){
				statiClassificazione.removeElementByCode(3);
				statiClassificazione.removeElementByCode(4);
			}
			else if (scelta!=null && scelta.equals("sospendi")){
				statiClassificazione.removeElementByCode(0);
				statiClassificazione.removeElementByCode(4);
			}
			else if (scelta!=null && scelta.equals("revoca")){
				statiClassificazione.removeElementByCode(0);
				statiClassificazione.removeElementByCode(3);
			}
			
			context.getRequest().setAttribute("StatiClassificazione", statiClassificazione);
			
			
			if (newOrg.getIdUltimoProvvedimento()>0)
			{
				HistoryClassificazione provvedimento = new HistoryClassificazione();
				provvedimento.queryRecord(db,newOrg.getIdUltimoProvvedimento());
				context.getRequest().setAttribute("Provvedumento", provvedimento);
			}
			
			
			LookupList classi = new LookupList(db, "lookup_classi_acque");
			classi.addItem(-1, "-- SELEZIONA VOCE --");
			if (context.getParameter("sospensione")!=null)
				classi.removeElementByLevel(0);
			else
					classi.removeElementByLevel(1);
				
					
			
			context.getRequest().setAttribute("Classificazione", classi);

			
			
			if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
				return ("PermissionError");
			}

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Account Modify");
		context.getRequest().setAttribute("OrgDetails", newOrg);
		
		String toRet = "";
		
		if (context.getRequest().getParameter("popup") != null) 
			toRet =  ("PopupModifyOK");
		else 
			{
				if (context.getParameter("sospensione")!=null)
					toRet =  ("ModifyClassificazioneSospensioneOK");
				else
					if (context.getParameter("classificazione")!=null){
						if (context.getParameter("tipo").equals("riclassifica"))
							toRet =  ("ModifyClassificazioneOK");
						else if (context.getParameter("tipo").equals("newrevocasospensione")) 
							toRet = ("NewRevocaSospensioneOK");
						else if (context.getParameter("tipo").equals("newriclassifica")) 
							toRet = ("NewRiclassificazioneOK");
					}
				else
					toRet =  ("RiattivazioneOK");
		}
		
		return toRet;
	}
	
	public String executeCommandSospendiConcessione(ActionContext context) {
		
		// Process request parameters
		int idZona	 = Integer.parseInt(context.getRequest().getParameter("orgId"));
		int idConcessionario = Integer.parseInt(context.getRequest().getParameter("idConcessionario"));
		Connection db = null;
		try {
			db = getConnection(context);
			Organization o = new Organization(db,idZona);
			Concessionario concessionario = new Concessionario(db,idConcessionario);
			ConcessioniList concessione = new ConcessioniList();
			concessione.setIdZona(o.getId());
			concessione.setIdConcessionario(concessionario.getId());
			concessione.buildList(db);
			o.setListaConcessionari(concessione);
			context.getRequest().setAttribute("OrgDetails", o);
			concessionario.setListaConcessioni(concessione);
			context.getRequest().setAttribute("Concessionario", concessionario);

			LookupList classi = new LookupList(db, "lookup_classi_acque");
			classi.addItem(-1, "-- SELEZIONA VOCE --");
				classi.removeElementByLevel(0);
				context.getRequest().setAttribute("Classificazione", classi);

				
		
		}
		catch(SQLException e){
			e.printStackTrace();
			
		}
		finally
		{
			
			freeConnection(context, db);
		}
		
			
			
					return ("SospensioneConcessionarioOK");
		
	}
	
	
	
	
	
	
	public String executeCommandModify(ActionContext context) {
		if (!hasPermission(context, "molluschi-molluschi-edit")) {
			return ("PermissionError");
		}
		
		
		String orgid = context.getRequest().getParameter("orgId");
		context.getRequest().setAttribute("orgId", orgid);
		int tempid = Integer.parseInt(orgid);
		Connection db = null;
		Organization newOrg = null;
		try {
			db = this.getConnection(context);
			newOrg = new Organization(db, tempid);
			Iterator it_coords = newOrg.getAddressList().iterator();
			while (it_coords.hasNext()) {

				org.aspcfs.modules.molluschibivalvi.base.OrganizationAddress thisAddress = (org.aspcfs.modules.molluschibivalvi.base.OrganizationAddress) it_coords.next();
				ArrayList<CoordinateMolluschiBivalvi> listacoordinate = thisAddress.getListaCoordinate();
				for(int i = 0 ; i <listacoordinate.size();i++)
				{
					CoordinateMolluschiBivalvi coord = listacoordinate.get(i);
					String spatial_coords[] = null;
					spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(coord.getLatitude()), Double.toString(coord.getLongitude()), db);
//					if (Double.parseDouble(spatial_coords[0].replace(',','.')) < 39.988475 || Double.parseDouble(spatial_coords[0].replace(',', '.')) > 41.503754) 
//					{
//						AjaxCalls ajaxCall = new AjaxCalls();
//						String[] coordinate = ajaxCall.getCoordinate(
//								thisAddress.getStreetAddressLine1(),
//								thisAddress.getCity(), thisAddress
//										.getState(), thisAddress.getZip(),
//								"" + thisAddress.getLatitude(), ""
//										+ thisAddress.getLongitude(), "");
//						thisAddress.setLatitude(coordinate[1]);
//						thisAddress.setLongitude(coordinate[0]);
//					}else {
						coord.setLatitude(spatial_coords[0]);
						coord.setLongitude(spatial_coords[1]);
					//}
					
					
					/*if (coord.getLatitude() != 0 && coord.getLongitude() != 0) {
					String spatial_coords[] = null;
					spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(coord.getLatitude()),Double.toString(coord.getLongitude()), db);
					
					coord.setLatitude(spatial_coords[0]);
					coord.setLongitude(spatial_coords[1]);
					
				}*/
					listacoordinate.remove(i);
					listacoordinate.add(i,coord);
				}
			}
			
			
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", siteList);

			LookupList zoneProduzione = new LookupList(db, "lookup_zone_di_produzione");
			zoneProduzione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("ZoneProduzione", zoneProduzione);

			/*PADRE MOLLUSCHI BIVALVI*/
			HashMap<Integer, String> tipoMoll = new HashMap<Integer, String>();
			String selMoll = "select matrice_id,nome from matrici where id_padre = 289  and nuova_gestione = true and enabled = true ";
			PreparedStatement pst = db.prepareStatement(selMoll);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next())
			{
				tipoMoll.put(rs.getInt(1), rs.getString(2));
			}
			
			context.getRequest().setAttribute("Molluschi", tipoMoll);
			
			LookupList classi = new LookupList(db, "lookup_classi_acque");
			classi.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("Classificazione", classi);
			newOrg.setComuni2(db, ((UserBean) context.getSession().getAttribute("User")).getSiteId());
			if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
				return ("PermissionError");
			}

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Account Modify");
		context.getRequest().setAttribute("OrgDetails", newOrg);
		if (context.getRequest().getParameter("popup") != null) 
		{
			return ("PopupModifyOK");
		} else 
		{
			return ("ModifyOK");
		}
	}

	public String executeCommandUpdate(ActionContext context)
	throws SQLException {
		if (!(hasPermission(context, "molluschi-molluschi-edit"))) {
			return ("PermissionError");
		}
		Connection db = null;
		int resultCount = -1;
		boolean isValid = false;
		Organization newOrg = (Organization) context.getFormBean();
		newOrg.setId(Integer.parseInt(context.getParameter("id")));
		
		Organization oldOrg = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		newOrg.setModifiedBy(getUserId(context));
		newOrg.setEnteredBy(getUserId(context));
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = user.getUserRecord().getIp();
		newOrg.setIpEntered(ip);
		newOrg.setIpModified(ip);
	
		try {
			db = this.getConnection(context);
			newOrg.setRequestItems(context);
			newOrg.setTipoMolluschiInZone(buildTipoMolluschibyRequest(context,db));
			isValid = this.validateObject(context, db, newOrg);

			Iterator it = newOrg.getAddressList().iterator();
			while (it.hasNext()) {
				OrganizationAddress thisAddress = (OrganizationAddress) it.next();
				
				

				// RICHIAMO METODO PER CONVERSIONE COORDINATE
				String[] coords = null;
				ArrayList<CoordinateMolluschiBivalvi> coordinate = thisAddress.getListaCoordinate();
				for(int i=0;i<coordinate.size(); i++)
				{
					CoordinateMolluschiBivalvi coordinata = coordinate.get(i);
				if (coordinata.getLatitude() != 0 && coordinata.getLongitude() != 0) 
				{
					coords = this.convert2Wgs84UTM33N(Double.toString(coordinata.getLatitude()), Double.toString(coordinata.getLongitude()), db);
					coordinata.setLatitude(coords[1]);
					coordinata.setLongitude(coords[0]);
					coordinate.remove(i);
					coordinate.add(i, coordinata);
					
					//thisAddress.setLatitude(coords[1]);
					//thisAddress.setLongitude(coords[0]);
				}
				}
				thisAddress.setListaCoordinate(coordinate);

			}
			if (isValid) {
				resultCount = newOrg.update(db,context);
			}
			oldOrg = new Organization(db, newOrg.getId());
			context.getRequest().setAttribute("OrgDetails", oldOrg);

		} catch (Exception errorMessage) 
		{
			context.getRequest().setAttribute("Error", errorMessage);
			errorMessage.printStackTrace();
			return ("SystemError");

		} 
		finally 
		{
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Modify Account");
		return ("UpdateOK");
	}
	
	
	public String executeCommandUpdateSospensioneConcessione(ActionContext context)
	throws SQLException {
		if (!(hasPermission(context, "molluschi-molluschi-edit"))) {
			return ("PermissionError");
		}
		Connection db = null;
		int resultCount = -1;
		boolean isValid = false;
		Organization newOrg = (Organization) context.getFormBean();
		newOrg.setId(Integer.parseInt(context.getParameter("id")));
		Organization oldOrg = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		newOrg.setModifiedBy(getUserId(context));
		newOrg.setEnteredBy(getUserId(context));
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = user.getUserRecord().getIp();
		newOrg.setIpEntered(ip);
		newOrg.setIpModified(ip);
		newOrg.setEnteredBy(user.getUserId());
		
		newOrg.setTipoMolluschi(context.getParameter("tipoMolluschi"));
		try {
			db = this.getConnection(context);
			newOrg.setRequestItems(context);
			newOrg.setTipoMolluschiInZone(buildTipoMolluschibyRequest(context,db));
			isValid = this.validateObject(context, db, newOrg);

			Iterator it = newOrg.getAddressList().iterator();
			while (it.hasNext()) 
			{
				org.aspcfs.modules.molluschibivalvi.base.OrganizationAddress thisAddress = (org.aspcfs.modules.molluschibivalvi.base.OrganizationAddress) it.next();
				String[] coords = null;
				if (thisAddress.getLatitude() != 0 && thisAddress.getLongitude() != 0) 
				{
					coords = this.convert2Wgs84UTM33N(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()), db);
					thisAddress.setLatitude(coords[1]);
					thisAddress.setLongitude(coords[0]);
				}
			}
			if (isValid) {
				resultCount = newOrg.updateClassificazioneProvvedimento(db, true,context);
			}
			oldOrg = new Organization(db, newOrg.getId());
			context.getRequest().setAttribute("OrgDetails", oldOrg);
			context.getRequest().setAttribute("updateOk", "Ok");
			/*PADRE MOLLUSCHI BIVALVI*/
			HashMap<Integer, String> tipoMoll = new HashMap<Integer, String>();
			String selMoll = "select matrice_id,nome from matrici where id_padre = 289  and nuova_gestione = true and enabled = true order by nome ";
			PreparedStatement pst = db.prepareStatement(selMoll);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next())
			{
				tipoMoll.put(rs.getInt(1), rs.getString(2));
			}
			
			context.getRequest().setAttribute("Molluschi", tipoMoll);
			

		} catch (Exception errorMessage) 
		{
			context.getRequest().setAttribute("Error", errorMessage);
			errorMessage.printStackTrace();
			return ("SystemError");

		} 
		finally 
		{
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Modify Account");
		return ("UpdateClassificazioneOK");
	}
	
	
	public String executeCommandUpdateClassificazione(ActionContext context)
	throws SQLException, ParseException {
		if (!(hasPermission(context, "molluschi-molluschi-edit"))) {
			return ("PermissionError");
		}
		Connection db = null;
		int resultCount = -1;
		boolean isValid = false;
		
		
		Organization newOrg = (Organization) context.getFormBean();
				newOrg.setId(Integer.parseInt(context.getParameter("id")));
		Organization oldOrg = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		newOrg.setModifiedBy(getUserId(context));
		newOrg.setEnteredBy(getUserId(context));
		
		if (context.getParameter("idProvvedimento")!=null)
			newOrg.setIdUltimoProvvedimento(Integer.parseInt(context.getParameter("idProvvedimento")));
		
		if (context.getParameter("statiClassificazione")!=null){
			newOrg.setStatoClassificazione(Integer.parseInt(context.getParameter("statiClassificazione")));
			if (newOrg.getStatoClassificazione() == Organization.STATO_CLASSIFICAZIONE_REVOCATO){
				newOrg.setDataRevoca(context.getParameter("dataRevoca"));
				newOrg.setDataSospensione("");
			}
			else if (newOrg.getStatoClassificazione() == Organization.STATO_CLASSIFICAZIONE_SOSPESO){
				newOrg.setDataSospensione(context.getParameter("dataSospensione"));
				newOrg.setDataRevoca("");
			}
			else {
				newOrg.setDataSospensione("");
				newOrg.setDataRevoca("");
			}
			
		}
		
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = user.getUserRecord().getIp();
		newOrg.setIpEntered(ip);
		newOrg.setIpModified(ip);
		newOrg.setEnteredBy(user.getUserId());
		if (context.getParameter("oldClasse")!=null)
			newOrg.setOldClasse(Integer.parseInt(context.getParameter("oldClasse")));
		
		
//		if (context.getParameter("provvedimentiRestrittivi")!=null)
//			newOrg.setProvvedimentiRestrittivi(Integer.parseInt(context.getParameter("provvedimentiRestrittivi")));
		
		if (context.getParameter("tipoMolluschi")!=null)
			newOrg.setTipoMolluschi(context.getParameter("tipoMolluschi"));
		try {
			db = this.getConnection(context);
			Organization orgOld = new Organization(db, newOrg.getId());
			newOrg.setTipoMolluschiOld(orgOld.getTipoMolluschi());
			newOrg.setRequestItems(context);
			if (context.getParameter("tipoMolluschi")!=null)
				newOrg.setTipoMolluschiInZone(buildTipoMolluschibyRequest(context,db));
			else
				newOrg.setTipoMolluschiInZone(orgOld.getTipoMolluschiInZone());
			
			newOrg.setDataProvvedimento(context.getParameter("dataProvvedimento"));
			
			//motivi sospensione selezionati
			String[] motivi = context.getRequest().getParameterValues("motivi");
			//motivi sospensione che andranno disabilitati
			ArrayList<Integer> motiviDaCancellare = new ArrayList<Integer>();
			//motivi sospensione che andranno aggiunti
			ArrayList<Integer> motiviDaAggiungere = new ArrayList<Integer>();
			if (motivi!=null && motivi.length>0){
				for (Integer key : orgOld.getTipoMotiviInZone().keySet()) {
					motiviDaCancellare.add(key);
				}
				for (int i = 0; i<motivi.length; i++){
					int motivo = Integer.parseInt(motivi[i]);
					int indexMotivo = motiviDaCancellare.indexOf(motivo);
					if (indexMotivo>-1)
						motiviDaCancellare.remove(indexMotivo);
					if (!orgOld.getTipoMotiviInZone().containsKey(motivo)){
						motiviDaAggiungere.add(motivo);	
					}
				}
			}		

			isValid = this.validateObject(context, db, newOrg);
			oldOrg = new Organization(db,newOrg.getId());
			
			OrganizationAddress oa =  (OrganizationAddress) oldOrg.getAddressList().get(0);
			int addressId = oa.getId();
			
			Iterator it = newOrg.getAddressList().iterator();
			while (it.hasNext()) 
			{
				org.aspcfs.modules.molluschibivalvi.base.OrganizationAddress thisAddress = (org.aspcfs.modules.molluschibivalvi.base.OrganizationAddress) it.next();
				String[] coords = null;
				if (thisAddress.getLatitude() != 0 && thisAddress.getLongitude() != 0) 
				{
					coords = this.convert2Wgs84UTM33N(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()), db);
					thisAddress.setLatitude(coords[1]);
					thisAddress.setLongitude(coords[0]);
				}
			}
			if (isValid) {
				
				String provv = context.getParameter("updateProvvedimento");
				String gestioneClassificazione = context.getParameter("gestioneClassificazione");
				String riattivazione = context.getParameter("riattivazione");

				if (provv!=null)
					resultCount = newOrg.updateGestioneSanitaria(db, true,context, motiviDaAggiungere);
				else if (gestioneClassificazione!=null)
					resultCount = newOrg.updateGestioneClassificazione(db, true,context, addressId);
				else
					if(riattivazione!=null)
					{
						
						resultCount = newOrg.riattivazione(db, true,context);
						newOrg.svuotaMotivi(db, getUserId(context));

					}
					else

					resultCount = newOrg.updateClassificazione(db, true,context);

				newOrg.gestisciMotiviSospensione(db, motiviDaAggiungere, motiviDaCancellare, getUserId(context));
				
				if(newOrg.getProvvedimentiRestrittivi()>4) // sospendsione
				{
					String tipoSospensione = context.getRequest().getParameter("tipoSospensione");
					if (tipoSospensione==null || !tipoSospensione.toLowerCase().contains("declassamento")){
						// NON DECLASSIFICARE ANCHE CONCESSIONARI SE DECLASSIFICATA ZONA DI PRODUZIONE 	VERBALE 24/06/14				
				ConcessioniList listaConcessioni = new ConcessioniList();
				listaConcessioni.setIdZona(newOrg.getId());
				listaConcessioni.buildList(db);
				if (listaConcessioni.size()>0)
				{
					Iterator<Concessione> itcon = listaConcessioni.iterator();
					while (itcon.hasNext())
					{
						Concessione thisconcessione = itcon.next();
						thisconcessione.setIdSospensione(newOrg.getClasse());
						thisconcessione.setDataSospensione(newOrg.getDataClassificazione());	
						thisconcessione.store(db);
					
				}
				}
					} 
					}
				else
				{
					if (newOrg.getClasse()<4 && oldOrg.getProvvedimentiRestrittivi()>4)
					{
						
						boolean sospesi = true ;
						ConcessioniList listaConcessioni = new ConcessioniList();
						listaConcessioni.setIdZona(newOrg.getId());
						listaConcessioni.buildList(db);
						if (listaConcessioni.size()>0)
						{
							Iterator<Concessione> itcon = listaConcessioni.iterator();
							while (itcon.hasNext())
							{
								Concessione thisconcessione = itcon.next();
								if (thisconcessione.getIdSospensione()<=0){
									sospesi=false;
									break ;
								}
							
						}
						}
						
						if (sospesi==true)
						{
							Iterator<Concessione> itcon = listaConcessioni.iterator();
							while (itcon.hasNext())
							{
								Concessione thisconcessione = itcon.next();
								thisconcessione.setDataSospensione((Timestamp)null);
								thisconcessione.setIdSospensione(0);
								thisconcessione.store(db);
							
						}
							
						}
						
					}
					
					
				}
			}
			oldOrg = new Organization(db, newOrg.getId());
			context.getRequest().setAttribute("OrgDetails", oldOrg);
			context.getRequest().setAttribute("updateOk", "Ok");
			/*PADRE MOLLUSCHI BIVALVI*/
			HashMap<Integer, String> tipoMoll = new HashMap<Integer, String>();
			String selMoll = "select matrice_id,nome from matrici where id_padre = 289  and nuova_gestione = true and enabled = true order by nome ";
			PreparedStatement pst = db.prepareStatement(selMoll);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next())
			{
				tipoMoll.put(rs.getInt(1), rs.getString(2));
			}
			
			context.getRequest().setAttribute("Molluschi", tipoMoll);
			

		} catch (Exception errorMessage) 
		{
			context.getRequest().setAttribute("Error", errorMessage);
			errorMessage.printStackTrace();
			return ("SystemError");

		} 
		finally 
		{
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Modify Account");
		return ("UpdateClassificazioneOK");
	}
	
	 public String executeCommandEliminaConcessionario(ActionContext context){
			Connection db = null;
			int org_id = Integer.parseInt(context.getRequest().getParameter("orgId"));

			try {
				db = getConnection(context);
				
				int id = Integer.parseInt(context.getRequest().getParameter("id"));
				Concessione.delete(db,id,org_id);
				Organization thisOrg = new Organization();
				thisOrg.setId(org_id);
				context.getRequest().setAttribute("OrgDetails", thisOrg);

							
			} catch (Exception e) {	
				context.getRequest().setAttribute("Error", e);
				e.printStackTrace();
				return ("SystemError");
			} finally {
				this.freeConnection(context, db);
			}
			 return executeCommandDetails(context);
			
		}
	public String executeCommandAggiungiConcessionario(ActionContext context){
		Connection db = null;
			

		try {
			db = getConnection(context);
			
			Concessionario nuovoOperatore = new Concessionario();
			Concessione concessione = new Concessione();
			concessione.setIdZona(Integer.parseInt(context.getParameter("idZona")));
			concessione.setIdConcessionario(context.getParameter("idConcessionario"));
			concessione.setDataConcessione(context.getParameter("dataConcessione"));
			concessione.setDataScadenza(context.getParameter("dataScadenza"));
			concessione.setNumConcessione(context.getParameter("numConcessione"));
			concessione.setDataDecreto(context.getParameter("dataDecreto"));
			concessione.setNumeroDecreto(context.getParameter("numeroDecreto"));
			
			concessione.setDataSospensione(context.getParameter("dataSospensione"));
			concessione.setIdSospensione(Integer.parseInt(context.getParameter("idSospensione")));
			
			concessione.setEnteredBy(getUserId(context));
			concessione.store(db);
			Organization thisOrg = new Organization(db,Integer.parseInt(context.getParameter("idZona")));
			
			context.getRequest().setAttribute("OrgDetails", thisOrg);
			Concessionario concessionario = new Concessionario();
			concessionario.setId(concessione.getIdConcessionario());
			context.getRequest().setAttribute("Concessionario", concessionario);
			
			context.getRequest().setAttribute("Inserito", "OK");
			
			if(concessione.getIdSospensione()>0)
			{
				boolean sospendi = true ;
				
			ConcessioniList listaConcessioni =  new ConcessioniList();
				listaConcessioni.setIdZona(thisOrg.getId());
listaConcessioni.buildList(db);
			if (listaConcessioni.size()>0)
			{
				Iterator<Concessione> itcon = listaConcessioni.iterator();
				while (itcon.hasNext())
				{
					Concessione thisconcessione = itcon.next();
					if (thisconcessione.getIdSospensione()<=0)
					{
						sospendi = false ;
						break ;
					}
				}
				if (sospendi == true )
				{
					thisOrg.setProvvedimentiRestrittivi(concessione.getIdSospensione());
					thisOrg.setDataProvvedimento(concessione.getDataSospensione());
					thisOrg.updateClassificazioneProvvedimento(db, true,context);
				}
				
			}
			
			
				
				
			}
			
						
		} catch (Exception e) {	
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("aggiungiConcessionarioOK");
		
	}

/*
	private String[] convert2Wgs84UTM33N(String latitude, String longitude,
			Connection conn) throws SQLException {

		String[] ret = null;
		String sql = "select 	X \n" + "( \n" + "	transform \n" + "	(  \n"
		+ "		geomfromtext \n" + "		(  \n" + "			'POINT( "
		+ longitude
		+ " "
		+ latitude
		+ " )', 4326 \n"
		+ "	 \n"
		+ "		), 32633 \n"
		+ "	) \n"
		+ ") AS x, \n"
		+ "Y \n"
		+ "( \n"
		+ "	transform \n"
		+ "	(  \n"
		+ "		geomfromtext \n"
		+ "		(  \n"
		+ "			'POINT( "
		+ longitude
		+ " "
		+ latitude
		+ " )', 4326 \n"
		+ "	 \n" + "		), 32633 \n" + "	) \n" + ") AS y \n";

		try {

			PreparedStatement stat = conn.prepareStatement(sql);
			// stat.setString( 1, wgs84[0] );
			// stat.setString( 2, wgs84[1] );
			// stat.setString( 3, wgs84[0] );
			// stat.setString( 4, wgs84[1] );

			ResultSet res = stat.executeQuery();
			if (res.next()) {
				ret = new String[2];
				ret[0] = res.getString("x");
				ret[1] = res.getString("y");

			}
			res.close();
			stat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	private String[] converToWgs84UTM33NInverter(String latitudine,
			String longitudine, Connection db) throws SQLException {
		String lat = "";
		String lon = "";
		String[] coord = new String[2];
		String sql1 = "select         X \n" + "( \n" + "        transform \n"
		+ "        (  \n" + "                geomfromtext \n"
		+ "                (  \n" + "                        'POINT("
		+ latitudine
		+ " "
		+ longitudine
		+ ")', 32633 \n"
		+ "         \n"
		+ "                ), 4326 \n"
		+ "        ) \n"
		+ ") AS x, \n"
		+ "Y \n"
		+ "( \n"
		+ "        transform \n"
		+ "        (  \n"
		+ "                geomfromtext \n"
		+ "                (  \n"
		+ "                        'POINT("
		+ latitudine
		+ " "
		+ longitudine
		+ ")', 32633 \n"
		+ "         \n"
		+ "                ), 4326 \n"
		+ "        ) \n"
		+ ") AS y \n";

		try {

			PreparedStatement stat1 = db.prepareStatement(sql1);
			ResultSet res1 = stat1.executeQuery();
			if (res1.next()) {
				lat = res1.getString("y");
				lon = res1.getString("x");
				coord[0] = lat;
				coord[1] = lon;

			}
			res1.close();
			stat1.close();

		} catch (SQLException e) {
			throw e ;
		}

		return coord;

	} */

	
	public String executeCommandUpdateNewClassificazione(ActionContext context)
			throws SQLException, ParseException {
				if (!(hasPermission(context, "molluschi-molluschi-edit"))) {
					return ("PermissionError");
				}
				Connection db = null;
				int resultCount = -1;
				boolean isValid = false;
				
				
				Organization newOrg = (Organization) context.getFormBean();
				newOrg.setId(Integer.parseInt(context.getParameter("id")));
				Organization oldOrg = null;
				SystemStatus systemStatus = this.getSystemStatus(context);
				newOrg.setModifiedBy(getUserId(context));
				newOrg.setEnteredBy(getUserId(context));
			
				UserBean user = (UserBean) context.getSession().getAttribute("User");
				String ip = user.getUserRecord().getIp();
				newOrg.setIpEntered(ip);
				newOrg.setIpModified(ip);
				newOrg.setEnteredBy(user.getUserId());
				if (context.getParameter("oldClasse")!=null)
					newOrg.setOldClasse(Integer.parseInt(context.getParameter("oldClasse")));
				
				newOrg.setTipoMolluschi(context.getParameter("tipoMolluschi"));
				try {
					db = this.getConnection(context);
					Organization orgOld = new Organization(db, newOrg.getId());
					newOrg.setTipoMolluschiOld(orgOld.getTipoMolluschi());
					newOrg.setRequestItems(context);
					newOrg.setTipoMolluschiInZone(buildTipoMolluschibyRequest(context,db));
					newOrg.setCun(context.getParameter("cun"));
					
					newOrg.setNumClassificazione(context.getParameter("numClassificazione"));
					newOrg.setDataClassificazione(context.getParameter("dataClassificazione"));

					isValid = this.validateObject(context, db, newOrg);
					oldOrg = new Organization(db,newOrg.getId());
					
					if (oldOrg.getStatoClassificazione()== -1)
						newOrg.setStatoClassificazione(0);
					else
						newOrg.setStatoClassificazione(oldOrg.getStatoClassificazione());
					
					OrganizationAddress oa =  (OrganizationAddress) oldOrg.getAddressList().get(0);
					int addressId = oa.getId();
					
					Iterator it = newOrg.getAddressList().iterator();
					while (it.hasNext()) 
					{
						org.aspcfs.modules.molluschibivalvi.base.OrganizationAddress thisAddress = (org.aspcfs.modules.molluschibivalvi.base.OrganizationAddress) it.next();
						String[] coords = null;
						if (thisAddress.getLatitude() != 0 && thisAddress.getLongitude() != 0) 
						{
							coords = this.convert2Wgs84UTM33N(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()), db);
							thisAddress.setLatitude(coords[1]);
							thisAddress.setLongitude(coords[0]);
						}
					}
					if (isValid) {
						resultCount = newOrg.updateNewRiclassificazione(db, true,context, addressId);
					}
					
					oldOrg = new Organization(db, newOrg.getId());
					context.getRequest().setAttribute("OrgDetails", oldOrg);
					context.getRequest().setAttribute("updateOk", "Ok");
					/*PADRE MOLLUSCHI BIVALVI*/
					HashMap<Integer, String> tipoMoll = new HashMap<Integer, String>();
					String selMoll = "select matrice_id,nome from matrici where id_padre = 289  and nuova_gestione = true and enabled = true order by nome ";
					PreparedStatement pst = db.prepareStatement(selMoll);
					ResultSet rs = pst.executeQuery() ;
					while (rs.next())
					{
						tipoMoll.put(rs.getInt(1), rs.getString(2));
					}
					
					context.getRequest().setAttribute("Molluschi", tipoMoll);
					

				} catch (Exception errorMessage) 
				{
					context.getRequest().setAttribute("Error", errorMessage);
					errorMessage.printStackTrace();
					return ("SystemError");

				} 
				finally 
				{
					this.freeConnection(context, db);
				}
				addModuleBean(context, "View Accounts", "Modify Account");
				return ("UpdateClassificazioneOK");
			}

	
	public String executeCommandUpdateNewRevocaSospensione(ActionContext context)
			throws SQLException, ParseException {
		if (!(hasPermission(context, "molluschi-molluschi-edit"))) {
			return ("PermissionError");
		}
		Connection db = null;
		int resultCount = -1;
		boolean isValid = false;
		
		String operazione = context.getParameter("scelta");
		String dataProvvedimento = context.getParameter("dataProvvedimento");
		String numeroDecreto = context.getParameter("numClassificazione");
		String dataRevoca = context.getParameter("dataRevoca");
		String dataSospensione = context.getParameter("dataSospensione");

		
		Organization newOrg = (Organization) context.getFormBean();
		newOrg.setId(Integer.parseInt(context.getParameter("id")));
		Organization oldOrg = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		newOrg.setModifiedBy(getUserId(context));
		newOrg.setEnteredBy(getUserId(context));
		
	
		if (context.getParameter("statiClassificazione")!=null){
			newOrg.setStatoClassificazione(Integer.parseInt(context.getParameter("statiClassificazione")));
			if (newOrg.getStatoClassificazione() == Organization.STATO_CLASSIFICAZIONE_REVOCATO){
				newOrg.setDataRevoca(context.getParameter("dataRevoca"));
				newOrg.setDataSospensione("");
				newOrg.setNumeroDecretoSospensioneRevoca(numeroDecreto);
				newOrg.setDataProvvedimentoSospensioneRevoca(dataProvvedimento);
			}
			else if (newOrg.getStatoClassificazione() == Organization.STATO_CLASSIFICAZIONE_SOSPESO){
				newOrg.setDataSospensione(context.getParameter("dataSospensione"));
				newOrg.setDataRevoca("");
				newOrg.setNumeroDecretoSospensioneRevoca(numeroDecreto);
				newOrg.setDataProvvedimentoSospensioneRevoca(dataProvvedimento);
	}
			else {
				newOrg.setDataSospensione("");
				newOrg.setDataRevoca("");
				newOrg.setNumeroDecretoSospensioneRevoca("");
				newOrg.setDataProvvedimentoSospensioneRevoca("");
			}
			
		}
		
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = user.getUserRecord().getIp();
		newOrg.setIpEntered(ip);
		newOrg.setIpModified(ip);
		newOrg.setEnteredBy(user.getUserId());
		if (context.getParameter("oldClasse")!=null)
			newOrg.setOldClasse(Integer.parseInt(context.getParameter("oldClasse")));
		
		
		//newOrg.setTipoMolluschi(context.getParameter("tipoMolluschi"));
		try {
			db = this.getConnection(context);
			Organization orgOld = new Organization(db, newOrg.getId());
			newOrg.setTipoMolluschiOld(orgOld.getTipoMolluschi());
			newOrg.setRequestItems(context);
			newOrg.setTipoMolluschiInZone(orgOld.getTipoMolluschiInZone());
			//newOrg.setDataProvvedimento(context.getParameter("dataProvvedimento"));
		
			isValid = this.validateObject(context, db, newOrg);
			oldOrg = new Organization(db,newOrg.getId());
			
		
			if (isValid) {
				resultCount = newOrg.updateNewRevocaSospensione(db, true,context);
				gestioneStoricoStati(oldOrg, newOrg, operazione, numeroDecreto, (dataSospensione!=null && !dataSospensione.equals("")) ? dataSospensione : (dataRevoca!=null && !dataRevoca.equals("")) ? dataRevoca : "" , dataProvvedimento, getUserId(context),  db);

				
			}
			
			oldOrg = new Organization(db, newOrg.getId());
			context.getRequest().setAttribute("OrgDetails", oldOrg);
			context.getRequest().setAttribute("updateOk", "Ok");
			/*PADRE MOLLUSCHI BIVALVI*/
			HashMap<Integer, String> tipoMoll = new HashMap<Integer, String>();
			String selMoll = "select matrice_id,nome from matrici where id_padre = 289  and nuova_gestione = true and enabled = true order by nome ";
			PreparedStatement pst = db.prepareStatement(selMoll);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next())
			{
				tipoMoll.put(rs.getInt(1), rs.getString(2));
			}
			
			context.getRequest().setAttribute("Molluschi", tipoMoll);
			

		} catch (Exception errorMessage) 
		{
			context.getRequest().setAttribute("Error", errorMessage);
			errorMessage.printStackTrace();
			return ("SystemError");

		} 
		finally 
		{
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Modify Account");
		return ("UpdateClassificazioneOK");
	}
	
	public String executeCommandUpdateLocalita(ActionContext context) {
		if (!(hasPermission(context, "molluschi-molluschi-view"))) {
			return ("PermissionError");
		}
		if (isPortalUser(context)) {
			return (executeCommandSearch(context));
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = getConnection(context);
			
			int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
			String nome = context.getRequest().getParameter("nomeLocalita");
			int userId = getUserId(context);
			Organization org = new Organization (db, orgId);
			org.updateNomeLocalita(db, nome, userId);
			
		
		context.getRequest().setAttribute("orgId", String.valueOf(orgId));

		} 
		catch (Exception e) 
		{
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return executeCommandDetails(context);
	}
	
	
	private void gestioneStoricoStati(Organization oldOrg, Organization newOrg, String operazione, String numeroDecreto, String data, String dataProvvedimento, int userId, Connection db) throws SQLException{
		
		PreparedStatement pst = db.prepareStatement("insert into storico_stati_molluschi(stato_old, stato_new, operazione, numero_decreto, data, data_provvedimento, id_zona, enteredby, entered) values (?, ?, ?, ?, ?, ?, ?, ?, now())");
		int i = 0;
		
		pst.setInt(++i, oldOrg.getStatoClassificazione());
		pst.setInt(++i, newOrg.getStatoClassificazione());
		pst.setString(++i, operazione);
		pst.setString(++i, numeroDecreto);
		pst.setTimestamp (++i,  DateUtils.parseDateStringNew(data, "dd/MM/yyyy"));
		pst.setTimestamp(++i,  DateUtils.parseDateStringNew(dataProvvedimento, "dd/MM/yyyy"));
		pst.setInt(++i, newOrg.getId());
		pst.setInt(++i, userId);
		pst.executeUpdate();	
		
		
	}
	
	private void aggiornaStatoClassificazione(Connection db, int orgid) throws SQLException{
		PreparedStatement pst = db.prepareStatement("select * from aggiorna_stati_molluschi(?)");
		pst.setInt(1, orgid);
		pst.executeQuery();
	}
}
