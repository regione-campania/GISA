/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.riproduzioneanimale.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.riproduzioneanimale.base.OrganizationAddress;
import org.aspcfs.modules.accounts.utils.AccountsUtil;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.base.Constants;


import org.aspcfs.modules.diffida.base.Ticket;
import org.aspcfs.modules.diffida.base.TicketList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.riproduzioneanimale.base.OrganizationList;
import org.aspcfs.modules.riproduzioneanimale.base.Organization;
import org.aspcfs.modules.riproduzioneanimale.base.OrganizationStazioniList;
import org.aspcfs.modules.riproduzioneanimale.base.Stazione;
import org.aspcfs.utils.AjaxCalls;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;









import com.darkhorseventures.framework.actions.ActionContext;

public class RiproduzioneAnimale extends CFSModule{


	public String executeCommandDefault(ActionContext context)
	{
		return executeCommandSearchForm(context);

	}
	public String executeCommandViewVigilanza(ActionContext context) {
		if (!hasPermission(context, "riproduzioneanimale-vigilanza-view")) {
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
		ticketListInfo.setLink("Accounts.do?command=ViewVigilanza&orgId="
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


	public String executeCommandSearchForm(ActionContext context) {
		if (!(hasPermission(context, "riproduzioneanimale-riproduzioneanimale-view"))) {
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
			context.getRequest().setAttribute("SiteList", siteList);
			
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
		if (!hasPermission(context, "riproduzioneanimale-riproduzioneanimale-view")) {
			return ("PermissionError");
		}
		
		
		String source = (String) context.getRequest().getParameter("source");
		String name = (String) context.getRequest().getParameter("searchAccountName");
		String comune =  (String) context.getRequest().getParameter("searchAccountCity");
		String addressType = context.getRequest().getParameter("addess_type_op");
		    
		OrganizationList organizationList = new OrganizationList();
		organizationList.setTipologia(8);
		addModuleBean(context, "View Accounts", "Search Results");
		PagedListInfo searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
		searchListInfo.setLink("RiproduzioneAnimale.do?command=Search");
		searchListInfo.setListView("all");
		Connection db = null;
		try {
			db = this.getConnection(context);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);
			
		    if(addressType!=null && !addressType.equals("")){
		    	    organizationList.setAddressType(addressType);
		    }
			
			if(name != null) {
		    	  organizationList.setAccountName(name);
		    	  organizationList.setName(name);
		     }
		   	     
		     if (comune != null){
		    	  organizationList.setAccountCity(comune);
		    	  organizationList.setCity(comune);
		    }
		  	   

		    organizationList.setSiteId(this.getUserSiteId(context));
			organizationList.setPagedListInfo(searchListInfo);

			searchListInfo.setSearchCriteria(organizationList, context);
			organizationList.buildList(db);
			
			
			context.getRequest().setAttribute("OrgList", organizationList);
			context.getSession().setAttribute("previousSearchType", "riproduzioneanimale");
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
		if (!hasPermission(context, "riproduzioneanimale-riproduzioneanimale-add")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {

			db = this.getConnection(context);
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);

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

	public String executeCommandInsert(ActionContext context)
	throws SQLException {
		if (!hasPermission(context, "riproduzioneanimale-riproduzioneanimale-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		Organization insertedOrg = null;
		Stazione insertedStation = null;
		Integer orgId = null;
		Organization newOrg = (Organization) context.getFormBean();
		newOrg.setCodice_fiscale((String) context.getRequest().getParameter("codice_fiscale"));
		newOrg.setPartita_iva((String) context.getRequest().getParameter("partita_iva"));
		newOrg.setNome_rappresentante((String) context.getRequest().getParameter("nome_rappresentante"));
		newOrg.setDate2((String) context.getRequest().getParameter("date2"));
		Stazione stazione = new Stazione();
		
		String tipoScelta = ((String) context.getRequest().getParameter("tipoStruttura"));
		
		if(tipoScelta != null){
			if(tipoScelta.equals("monta_equina_attive")){
				stazione.setMonta_equina_attive(true);
				newOrg.setTipologia_strutt("STAZ MONTA EQUINA");
			}
			else if(tipoScelta.equals("monta_bovina_attive")){
				stazione.setMonta_bovine_attive(true);
				newOrg.setTipologia_strutt("STAZ MONTA BOVINA");
			}
			else if(tipoScelta.equals("stazione_inseminazione_equine")){
				stazione.setStazione_inseminazione_equine(true);
				newOrg.setTipologia_strutt("STAZ INSEMINAZ EQUINE");
			}
			else if(tipoScelta.equals("centro_produzione_sperma")){
				stazione.setCentro_produzione_sperma(true);
				newOrg.setTipologia_strutt("CENTRO PROD SPERMA");
			}
			else if(tipoScelta.equals("centro_produzione_embrioni")){
				stazione.setCentro_produzione_embrioni(true);
				newOrg.setTipologia_strutt("CENTRO PROD EMBRIONI");
			}
			else if(tipoScelta.equals("gruppo_raccolta_embrioni")){
				stazione.setGruppo_raccolta_embrioni(true);
				newOrg.setTipologia_strutt("GRUPPO RACCOLTA EMBRIONI");
			}
			else if(tipoScelta.equals("recapiti_autorizzati")){
				stazione.setRecapiti_autorizzati(true);
				newOrg.setTipologia_strutt("RECAPITO AUTORIZZATO");
			}
			
		}
		
		stazione.setCodice_legge_30((String)context.getRequest().getParameter("codice_legge_30"));
		stazione.setScadenza_aut(((String)context.getRequest().getParameter("scadenza_aut")));
		stazione.setProvv_aut((String)context.getRequest().getParameter("provv_aut"));
		stazione.setRazza((String)context.getRequest().getParameter("razza"));
		stazione.setSede((String)context.getRequest().getParameter("sede"));
		stazione.setScadenza_aut((String) context.getRequest().getParameter("date2"));
		
		newOrg.setEnteredBy(getUserId(context));
		newOrg.setModifiedBy(getUserId(context));
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = user.getUserRecord().getIp();
		newOrg.setIpEntered(ip);
		newOrg.setIpModified(ip);
		
		//st.setCodice_legge_30(codice_legge_30);
		try 
		{
			db = this.getConnection(context);
			newOrg.setRequestItems(context);
			
			if (this.getUserSiteId(context) != -1) 
			{
				if (newOrg.getId() <= 0) {
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
				if (thisAddress.getLatitude() != 0&& thisAddress.getLongitude() != 0) 
				{
					coords = this.convert2Wgs84UTM33N(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()), db);
					thisAddress.setLatitude(coords[1]);
					thisAddress.setLongitude(coords[0]);
				}

			}
				
	
			if (isValid) 
			{
				recordInserted = newOrg.insert(db,context);
				
			}
			if (recordInserted) 
			{
				
				insertedOrg = new Organization(db, newOrg.getId());
				stazione.insert(db, newOrg.getId(),context);
				
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

	
	public String executeCommandExport(ActionContext context) throws IOException {
		if (!hasPermission(context, "riproduzioneanimale-riproduzioneanimale-view")) {
			return ("PermissionError");
		}
		Connection db = null ;
		
		try {
			
			
			db = this.getConnection(context);
			
			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			
			 
			String filtro = "" ;
			if (user.getSiteId()>0)
			{
				filtro+= " and o.site_id = ? " ;
			}
			
			String query = "select  " +
			"o.org_id , name as ragione_sociale ,asl.description as asl ,partita_iva, codice_fiscale,date2 as data_scadenza_autorizzazione,tipologia_strutt as tipologia_struttura " +
			",numaut as numero_autorizzazione,c.provv_aut as provvedimento_autorizzazione,codice_legge_30,tipo_attivita,sede,razza,scadenza_aut, nome_rappresentante, " +
			"case when " +
			"max(cu.assigned_date) is null then 'Ex Ante' " +
			"when max(cu.assigned_date) is not null then ''||max(cu.assigned_date)end as data_categorizzazione, " +
			"o.categoria_rischio " +
			"from organization o " +
			"LEFT JOIN centri_riproduzione_animale c  ON (o.org_id = c.org_id)  " +
			"left join lookup_site_id asl on o.site_id = asl.code  " +
			"left join ticket cu on cu.org_id = o.org_id and cu.provvedimenti_prescrittivi = 5 and cu.tipologia=3 and cu.trashed_date is null and cu.isaggiornata_categoria=true " +
			"where o.tipologia = 8 and o.trashed_date is null " +filtro +
			"group by " +
			"o.org_id , name ,asl.description  ,partita_iva, codice_fiscale,date2 ,tipologia_strutt " +
			",numaut ,c.provv_aut ,codice_legge_30,tipo_attivita,sede,razza,scadenza_aut,case when cu.ticketid is null then 'Ex Ante' " +
			"when cu.ticketid >0 then 'Categorizzato' :: text " +
			"end " ;

			
			PreparedStatement pst = db.prepareStatement(query) ;
			if (user.getSiteId()>0)
			{
				pst.setInt(1, user.getSiteId());
			}
			ResultSet rs = pst.executeQuery() ;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			
		    
		    //BufferedWriter bw12 = new BufferedWriter(new FileWriter(f));
		    
		 	dynamicHeader(rs, bos);
		 	while (rs.next())
		 	{
		 		this.printRow(rs, bos);
		 	}
		 	
		    context.getResponse().setContentType( "application/csv" );
		    context.getResponse().setHeader( "Content-Disposition","attachment; filename=\"centri_strutture_riproduzione_animale.csv" + "\";" ); 
		    ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		    int lunghezza;
			byte[] buffer = new byte[1024];
		    while ((lunghezza = bis.read(buffer)) > 0){
		    	context.getResponse().getOutputStream().write(buffer, 0, lunghezza);
		    
		    }
			

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			freeConnection(context, db);
		}
		return "-none-";
		
	}
	 private void dynamicHeader (ResultSet rs , ByteArrayOutputStream sout) throws IOException, SQLException {
		  
		  //Lo String Buffer
		  StringBuffer sb = new StringBuffer(); 
		  
		  //Serve ad ottenere i meta-dati del ResultSet
		  ResultSetMetaData rsmd=rs.getMetaData();
		  
		  //Il numero di attributi
		  int columnNumber=rsmd.getColumnCount();
		  
		 
			 
		  for (int i=1;i<=columnNumber;i++) {
		
		
			sb.append( rsmd.getColumnName(i) +";");
		 }
		 
		  		sb.append("\n");
			
			
		  sout.write( sb.toString().getBytes() );
		  
		  sout.flush();
		  
	  }
	public String executeCommandDetails(ActionContext context) {
		if (!hasPermission(context, "riproduzioneanimale-riproduzioneanimale-view")) {
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
			newOrg = new Organization(db, tempid);
			 //Caricamento Diffide
			Ticket t = new Ticket();
			context.getRequest().setAttribute("DiffideList", t.getDiffide(db,false,null,null,newOrg.getOrgId(),null));
			context.getRequest().setAttribute("DiffideListStorico", t.getDiffide(db,true,null,null,newOrg.getOrgId(),null));
	       
			// Dopo l'inserimento riconverti
			Iterator it_coords = newOrg.getAddressList().iterator();
			while (it_coords.hasNext()) {

				org.aspcfs.modules.riproduzioneanimale.base.OrganizationAddress thisAddress = (org.aspcfs.modules.riproduzioneanimale.base.OrganizationAddress) it_coords.next();
				if (thisAddress.getLatitude() != 0 && thisAddress.getLongitude() != 0) 
				{

					String spatial_coords[] = null;
					spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()), db);
//					if (Double.parseDouble(spatial_coords[0].replace(',', '.')) < 39.988475 || Double.parseDouble(spatial_coords[0].replace(',', '.')) > 41.503754) {
//						AjaxCalls ajaxCall = new AjaxCalls();
//						String[] coordinate = ajaxCall.getCoordinate(thisAddress.getStreetAddressLine1(), thisAddress.getCity(), thisAddress.getState(),thisAddress.getZip(), ""+ thisAddress.getLatitude(), ""+ thisAddress.getLongitude(), "");
//						thisAddress.setLatitude(coordinate[1]);
//						thisAddress.setLongitude(coordinate[0]);
//					} else {
						thisAddress.setLatitude(spatial_coords[0]);
						thisAddress.setLongitude(spatial_coords[1]);
				//	}
				}
			}
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);


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
	
	
	public String executeCommandCessazioneAttivita(ActionContext context) throws ParseException
	{
	 
	    int orgId = Integer.parseInt(context.getParameter("idAnagrafica"));
		String dataFineString = context.getParameter("dataCessazioneAttivita");
		Timestamp dataFine = null;	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		dataFine = new Timestamp(sdf.parse(dataFineString).getTime());
		String note = context.getParameter("noteCessazione");
		int userId = getUserId(context);
	  
		Connection db = null;
		try
		{
			db=super.getConnection(context);
			Organization org = new Organization(db,orgId);
			AccountsUtil.cessazioneAttivita(db, orgId, dataFine, note, userId);
			context.getRequest().setAttribute("OrgDetails", org);
		}
		catch(SQLException e)
		{
			
		}
			finally{
				super.freeConnection(context, db);
			}
		return "InsertOK";
	}

	public String executeCommandModify(ActionContext context) {
		if (!hasPermission(context, "riproduzioneanimale-riproduzioneanimale-edit")) {
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

				org.aspcfs.modules.riproduzioneanimale.base.OrganizationAddress thisAddress = (org.aspcfs.modules.riproduzioneanimale.base.OrganizationAddress) it_coords.next();
				if (thisAddress.getLatitude() != 0 && thisAddress.getLongitude() != 0) {
					String spatial_coords[] = null;
					spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(thisAddress.getLatitude()),Double.toString(thisAddress.getLongitude()), db);
					if (1==2 && Double.parseDouble(spatial_coords[0].replace(',','.')) < 39.988475 || Double.parseDouble(spatial_coords[0].replace(',', '.')) > 41.503754) 
					{
						AjaxCalls ajaxCall = new AjaxCalls();
						String[] coordinate = ajaxCall.getCoordinate(thisAddress.getStreetAddressLine1(), thisAddress.getCity(), thisAddress .getState(), thisAddress.getZip(), "" + thisAddress.getLatitude(), "" + thisAddress.getLongitude(), "");
						thisAddress.setLatitude(coordinate[1]);
						thisAddress.setLongitude(coordinate[0]);
					} 
					else 
					{
						thisAddress.setLatitude(spatial_coords[0]);
						thisAddress.setLongitude(spatial_coords[1]);
					}

				}
			}
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);

			
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
		if (!(hasPermission(context, "riproduzioneanimale-riproduzioneanimale-edit"))) {
			return ("PermissionError");
		}
		Connection db = null;
		int resultCount = -1;
		boolean isValid = false;
		Organization newOrg = (Organization) context.getFormBean();
		newOrg.setId(Integer.parseInt(context.getParameter("orgId")));
		Organization oldOrg = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		newOrg.setModifiedBy(getUserId(context));
		newOrg.setEnteredBy(getUserId(context));
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = user.getUserRecord().getIp();
		newOrg.setIpEntered(ip);
		newOrg.setIpModified(ip);
		newOrg.setCodice_fiscale((String) context.getRequest().getParameter("codice_fiscale"));
		newOrg.setPartita_iva((String) context.getRequest().getParameter("partita_iva"));
		newOrg.setNome_rappresentante((String) context.getRequest().getParameter("nome_rappresentante"));
		newOrg.setDate2((String) context.getRequest().getParameter("date2"));
		
		
		Stazione stazione = new Stazione();
		
		String tipoScelta = ((String) context.getRequest().getParameter("tipoStruttura"));
		
		if(tipoScelta != null){
			if(tipoScelta.equals("monta_equina_attive")){
				stazione.setMonta_equina_attive(true);
				newOrg.setTipologia_strutt("STAZ MONTA EQUINA");
			}
			else if(tipoScelta.equals("monta_bovina_attive")){
				stazione.setMonta_bovine_attive(true);
				newOrg.setTipologia_strutt("STAZ MONTA BOVINA");
			}
			else if(tipoScelta.equals("stazione_inseminazione_equine")){
				stazione.setStazione_inseminazione_equine(true);
				newOrg.setTipologia_strutt("STAZ INSEMINAZ EQUINE");
			}
			else if(tipoScelta.equals("centro_produzione_sperma")){
				stazione.setCentro_produzione_sperma(true);
				newOrg.setTipologia_strutt("CENTRO PROD SPERMA");
			}
			else if(tipoScelta.equals("centro_produzione_embrioni")){
				stazione.setCentro_produzione_embrioni(true);
				newOrg.setTipologia_strutt("CENTRO PROD EMBRIONI");
			}
			else if(tipoScelta.equals("gruppo_raccolta_embrioni")){
				stazione.setGruppo_raccolta_embrioni(true);
				newOrg.setTipologia_strutt("GRUPPO RACCOLTA EMBRIONI");
			}
			else if(tipoScelta.equals("recapiti_autorizzati")){
				stazione.setRecapiti_autorizzati(true);
				newOrg.setTipologia_strutt("RECAPITO AUTORIZZATO");
			}
			
		}
		
		stazione.setCodice_legge_30((String)context.getRequest().getParameter("codice_legge_30"));
		stazione.setScadenza_aut(((String)context.getRequest().getParameter("scadenza_aut")));
		stazione.setProvv_aut((String)context.getRequest().getParameter("provv_aut"));
		stazione.setRazza((String)context.getRequest().getParameter("razza"));
		stazione.setSede((String)context.getRequest().getParameter("sede"));
		stazione.setScadenza_aut((String) context.getRequest().getParameter("date2"));
		stazione.setId(Integer.parseInt(context.getRequest().getParameter("id")));
		
		
		try {
			db = this.getConnection(context);
			newOrg.setRequestItems(context);

			isValid = this.validateObject(context, db, newOrg);

			Iterator it = newOrg.getAddressList().iterator();
			while (it.hasNext()) 
			{
				org.aspcfs.modules.riproduzioneanimale.base.OrganizationAddress thisAddress = (org.aspcfs.modules.riproduzioneanimale.base.OrganizationAddress) it.next();
				String[] coords = null;
				if (thisAddress.getLatitude() != 0 && thisAddress.getLongitude() != 0) 
				{
					coords = this.convert2Wgs84UTM33N(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()), db);
					thisAddress.setLatitude(coords[1]);
					thisAddress.setLongitude(coords[0]);
				}
			}
			if (isValid) {
				resultCount = newOrg.update(db,context);
				stazione.update(db, newOrg.getId());
				
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

	private void printRow( ResultSet rs, ByteArrayOutputStream sout) throws IOException
	  {
		  StringBuffer sb = new StringBuffer(); 
		  
		 try 
		 {
			int count = rs.getMetaData().getColumnCount();
			for (int i = 1 ; i<=count; i++)
			{
				
				switch(rs.getMetaData().getColumnType(i))
				{
				
				case java.sql.Types.BOOLEAN :
				{
					sb.append(""+nullCheck(rs.getBoolean(i))+";" );
					break;
				}
				case java.sql.Types.CHAR :
				{
					sb.append(""+nullCheckString(rs.getString(i))+";" );
					break;
				}
				case java.sql.Types.DATE :
				{
					sb.append(""+nullCheck(rs.getDate(i))+";" );
					break;
				}
				case java.sql.Types.DOUBLE :
				{
					sb.append(""+nullCheck(rs.getDouble(i))+";" );
					break;
				}
				case java.sql.Types.FLOAT :
				{
					sb.append(""+nullCheck(rs.getFloat(i))+";" );
					break;
				}
				case java.sql.Types.INTEGER :
				{
					sb.append(""+nullCheck(rs.getInt(i))+";" );
					break;
				}
				case java.sql.Types.LONGNVARCHAR :
				{
					sb.append(""+nullCheckString(rs.getString(i))+";" );
					break;
				}
				case java.sql.Types.LONGVARCHAR :
				{
					sb.append(""+nullCheckString(rs.getString(i))+";" );
					break;
				}
				case java.sql.Types.NCHAR :
				{
					sb.append(""+nullCheckString(rs.getString(i)) +";");
					break;
				}
				case java.sql.Types.NUMERIC :
				{
					sb.append(""+nullCheck(rs.getInt(i))+";" );
					break;
				}
				case java.sql.Types.NVARCHAR :
				{
					sb.append(""+nullCheckString(rs.getString(i))+";" );
					break;
				}
				case java.sql.Types.TIMESTAMP :
				{
					sb.append(""+nullCheck(rs.getTimestamp(i))+";" );
					break;
				}
				case java.sql.Types.VARCHAR :
				{
					sb.append(""+nullCheckString(rs.getString(i))+";" );
					break;
				}
				default : System.out.println(rs.getMetaData().getColumnType(i));
				
				}
				
				
			}
			sb.append("\n");
			sout.write(sb.toString().getBytes());
			
			
				sout.flush();
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	  }
		 
		 private Object nullCheck( Object obj )
		  {
			
		  	return (obj == null ||(obj!=null && obj.equals(""))) ? ("N.D") : (obj);
		  }
		  private Object nullCheckString( Object obj )
		  {
			 
		  	return (obj == null ||(obj!=null && StringUtils.toHtml2((String)obj).equals(""))) ? ("N.D") : (StringUtils.toHtml(((String)obj).trim()));
		  }
		  
		  
		  
		  public String executeCommandTrash(ActionContext context) {
			    if (!hasPermission(context, "riproduzioneanimale-riproduzioneanimale-delete")) {
			      return ("PermissionError");
			    }
			    boolean recordUpdated = false;
			    Connection db = null;
			    try {
			        db = this.getConnection(context);
			        int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
			        String note = context.getRequest().getParameter("note");

			        //check permission to record
			        if (!isRecordAccessPermitted(context, db, orgId)) {
			          return ("PermissionError");
			        }
			       
			        // NOTE: these may have different options later
			        recordUpdated = AccountsUtil.deleteCentralizzato(db, orgId, note, this.getUserId(context));
			      
			   
			      } catch (Exception e) {
			      context.getRequest().setAttribute(
			          "refreshUrl", "RiproduzioneAnimale.do?command=Search");
			      context.getRequest().setAttribute("Error", e);
			      return ("SystemError");
			    } finally {
			      this.freeConnection(context, db);
			    }
			    addModuleBean(context, "Accounts", "Delete Account");
			    if (recordUpdated) {
			      context.getRequest().setAttribute(
			          "refreshUrl", "RiproduzioneAnimale.do?command=Search");
			      if ("list".equals(context.getRequest().getParameter("return"))) {
			        return executeCommandSearch(context);
			      }
			      return "DeleteOK";
			    } else {
			      return (executeCommandSearch(context));
			    }
			  }
		  
		  public String executeCommandConfirmDelete(ActionContext context) {
			    if (!hasPermission(context, "riproduzioneanimale-riproduzioneanimale-delete")) {
			      return ("PermissionError");
			    }
			    Connection db = null;
			    HtmlDialog htmlDialog = new HtmlDialog();
			    int orgId = -1;
			    SystemStatus systemStatus = this.getSystemStatus(context);
			    if (context.getRequest().getParameter("id") != null) {
			    	orgId = Integer.parseInt(context.getRequest().getParameter("id"));
			    	}
			    try {
			        db = this.getConnection(context);
			        //check permission to record
			        if (!AccountsUtil.isCancellabile(db, orgId)){
			      	  htmlDialog.addMessage("<font color=\"red\"><b>Impossibile proseguire nella cancellazione. Sono presenti CU associati.</b></font>");
			            htmlDialog.addMessage("<br/>");
			            htmlDialog.addMessage("<input type=\"button\" value=\"CHIUDI\" onClick=\"javascript:parent.window.close()\"/>");
			        }
			        else {
			        htmlDialog.addMessage("<form action=\"RiproduzioneAnimale.do?command=Trash&auto-populate=true\" method=\"post\">");
			        htmlDialog.addMessage("<b>Note cancellazione</b>: <textarea required id=\"note\" name=\"note\" rows=\"2\" cols=\"40\"></textarea>");
			        htmlDialog.addMessage("<br/>");
			        htmlDialog.addMessage("<input type=\"submit\" value=\"ELIMINA\"/>");
			        htmlDialog.addMessage("&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ");
			        htmlDialog.addMessage("<input type=\"button\" value=\"ANNULLA\" onClick=\"javascript:parent.window.close()\"/>");
			        htmlDialog.addMessage("<input type=\"hidden\" id=\"action\" name=\"action\" value=\"delete\"/>");
			        htmlDialog.addMessage("<input type=\"hidden\" id=\"forceDelete\" name=\"forceDelete\" value=\"true\"/>");
			        htmlDialog.addMessage("<input type=\"hidden\" id=\"orgId\" name=\"orgId\" value=\""+orgId+"\"/>");
			        htmlDialog.addMessage("</form>");
			        }
			      } catch (Exception e) {
			      context.getRequest().setAttribute("Error", e);
			      return ("SystemError");
			    } finally {
			      this.freeConnection(context, db);
			    }
			    context.getSession().setAttribute("Dialog", htmlDialog);
			    return ("ConfirmDeleteOK");
			  }
		  
		  
		  
		  
		  
		  
		  
		  
		  
	  }



