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
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.molluschibivalvi.base.Concessionario;
import org.aspcfs.modules.molluschibivalvi.base.OrganizationAddress;
import org.aspcfs.modules.molluschibivalvi.base.ConcessionarioList;
import org.aspcfs.utils.AjaxCalls;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.Limit;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesa.view.html.editor.HtmlCellEditor;

import com.darkhorseventures.framework.actions.ActionContext;

public class Concessionari extends CFSModule{



	public String executeCommandDefault(ActionContext context)
	{
		return executeCommandSearchForm(context);

	}


	public String executeCommandSearchForm(ActionContext context) {
		if (!(hasPermission(context, "concessionari-concessionari-view"))) {
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
		if (!hasPermission(context, "concessionari-concessionari-view")) {
			return ("PermissionError");
		}
		ConcessionarioList organizationList = new ConcessionarioList();
		organizationList.setTipologia(211);
		addModuleBean(context, "View Accounts", "Search Results");
		PagedListInfo searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
		searchListInfo.setLink("Concessionari.do?command=Search");
		searchListInfo.setListView("all");
		Connection db = null;
		try {
			db = this.getConnection(context);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", siteList);

		

			searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
			searchListInfo.setLink("Concessionari.do?command=Search");
			organizationList.setPagedListInfo(searchListInfo);
			organizationList.setTipologia(211) ;
			searchListInfo.setSearchCriteria(organizationList, context);
			organizationList.buildList(db);
			context.getRequest().setAttribute("OrgList", organizationList);
			context.getSession().setAttribute("previousSearchType", "accounts");
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
		if (!hasPermission(context, "concessionari-concessionari-add")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {

			db = this.getConnection(context);
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", siteList);

			Concessionario newOrg = (Concessionario) context.getFormBean();
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
		if (!hasPermission(context, "concessionari-concessionari-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		Concessionario insertedOrg = null;
		Integer orgId = null;
		Concessionario newOrg = (Concessionario) context.getFormBean();
		newOrg.setEnteredBy(getUserId(context));
		newOrg.setModifiedBy(getUserId(context));
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = user.getUserRecord().getIp();
		newOrg.setIpEntered(ip);
		newOrg.setIpModified(ip);
		try 
		{
			db = this.getConnection(context);
			newOrg.setRequestItems(context);
			if (this.getUserSiteId(context) != -1) 
			{
				if (newOrg.getId() == -1) {
					newOrg.setSiteId(this.getUserSiteId(context));
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
				
				insertedOrg = new Concessionario(db, newOrg.getId());
				context.getRequest().setAttribute("OrgDetails", insertedOrg);
				
				Concessionario temp = new Concessionario(db, newOrg.getId());
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
		if (!hasPermission(context, "concessionari-concessionari-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Concessionario newOrg = null;
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
			newOrg = new Concessionario(db, tempid);

			// Dopo l'inserimento riconverti
			Iterator it_coords = newOrg.getAddressList().iterator();
			while (it_coords.hasNext()) {

				org.aspcfs.modules.molluschibivalvi.base.OrganizationAddress thisAddress = (org.aspcfs.modules.molluschibivalvi.base.OrganizationAddress) it_coords.next();
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
					//}
				}
			}
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", siteList);
			if(context.getRequest().getParameter("IdZona")!=null)
				context.getRequest().setAttribute("idZona", context.getRequest().getParameter("IdZona"));


		} catch (SQLException e) {

			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "View Account Details");
		context.getRequest().setAttribute("OrgDetails", newOrg);
		if(context.getRequest().getParameter("popUp")!=null)
			return "DetailsPopUpOk";
		return getReturn(context, "Details");

	}

	public String executeCommandClassificaSpecchioAcqueo(ActionContext context) {
		if (!hasPermission(context, "concessionari-concessionari-edit")) {
			return ("PermissionError");
		}
		
		
		String orgid = context.getRequest().getParameter("orgId");
		context.getRequest().setAttribute("orgId", orgid);
		int tempid = Integer.parseInt(orgid);
		Connection db = null;
		Concessionario newOrg = null;
		try {
			db = this.getConnection(context);
			newOrg = new Concessionario(db, tempid);
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
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", siteList);

			LookupList zoneProduzione = new LookupList(db, "lookup_zone_di_produzione");
			zoneProduzione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("ZoneProduzione", zoneProduzione);

			LookupList classi = new LookupList(db, "lookup_classi_acque");
			zoneProduzione.addItem(-1, "-- SELEZIONA VOCE --");
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
		if (context.getRequest().getParameter("popup") != null) 
		{
			return ("PopupModifyOK");
		} else 
		{
			return ("ModifyClassificazioneOK");
		}
	}
	
	public String executeCommandModify(ActionContext context) {
		if (!hasPermission(context, "concessionari-concessionari-edit")) {
			return ("PermissionError");
		}
		
		
		String orgid = context.getRequest().getParameter("orgId");
		context.getRequest().setAttribute("orgId", orgid);
		int tempid = Integer.parseInt(orgid);
		Connection db = null;
		Concessionario newOrg = null;
		try {
			db = this.getConnection(context);
			newOrg = new Concessionario(db, tempid);
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
			
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteIdList", siteList);

		
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
		if (!(hasPermission(context, "concessionari-concessionari-edit"))) {
			return ("PermissionError");
		}
		Connection db = null;
		int resultCount = -1;
		boolean isValid = false;
		Concessionario newOrg = (Concessionario) context.getFormBean();
		newOrg.setId(Integer.parseInt(context.getParameter("id")));
		Concessionario oldOrg = null;
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
				resultCount = newOrg.update(db,context);
			}
			oldOrg = new Concessionario(db, newOrg.getId());
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
	
	
	 public String executeCommandListaConcessionari( ActionContext context )
		{
			String ret = "ListaConcessionariOK";
			final int org_id = Integer.parseInt(context.getRequest().getParameter("orgId"));
			
//			if (!hasPermission(context, "molluschi-concessionari-view"))
//			{
//				return "PermissionError";
//			}
			
			UserBean user = (UserBean)context.getRequest().getSession().getAttribute("User");
			Connection db = null;
		    RowSetDynaClass	rsdc = null;
			
			try
			{
				db = getConnection( context );
				
				ConcessionarioList list = new ConcessionarioList();
				
				final LookupList asl = new LookupList(db,"lookup_site_id");
				PreparedStatement	stat	= null ;
				list.setSiteId(user.getSiteId());
				ResultSet rs	=  list.queryList(db, stat);
				 rsdc	= new RowSetDynaClass(rs);
				
				TableFacade tf = null;
				
				tf = TableFacadeFactory.createTableFacade( "5", context.getRequest() );
				tf.setItems( rsdc.getRows());
				tf.setColumnProperties( "site_id", "name", "nome_rappresentante", "codice_fiscale", "edit" );
				
				
				tf.setStateAttr("restore");
				
				Limit limit = tf.getLimit();
				
				if( limit.isExported() )
				{
					tf.render();
					return "ListOK";
				}
				else
				{

					tf.getTable().getRow().getColumn( "name" ).setTitle( "Ragione Sociale" );
					tf.getTable().getRow().getColumn( "nome_rappresentante" ).setTitle( "Legale Rappresentante" );
					tf.getTable().getRow().getColumn( "site_id" ).setTitle( "asl" );
					tf.getTable().getRow().getColumn( "codice_fiscale" ).setTitle( "Codice Fiscale Impresa" );
					
					
					tf.getTable().getRow().getColumn( "edit" ).setTitle( "Azione" );
					HtmlColumn cg = (HtmlColumn) tf.getTable().getRow().getColumn("edit");
					
					
					HtmlColumn cg2 = (HtmlColumn) tf.getTable().getRow().getColumn("site_id");
					cg2.getCellRenderer().setCellEditor( 
			        		new CellEditor()
			        		{	
			        			public Object getValue(Object item, String property, int rowCount)
			        			{
			        				
									String idAsl		= (String) (new HtmlCellEditor()).getValue(item, "site_id", rowCount);
									
									if(idAsl!=null && !"".equals(idAsl))
										return asl.getSelectedValue(Integer.parseInt(idAsl));
									return "" ;
			        				//return "<a href=\"MolluschiBivalvi.do?command=AggiungiConcessionario&orgId=" + org_id + "&idConcessionario=" + idOperatore +  
			        					//"\" onclick=\"return confirm('Sei sicuro di voler aggiungere l\'operatore selezionato?');\">Aggiungi</a>"; 
					        	}
			        		}
			        
			    );
					
					cg2.getFilterRenderer().setFilterEditor(new DroplistFilterEditor());
					
					cg.getCellRenderer().setCellEditor( 
				        		new CellEditor()
				        		{	
				        			public Object getValue(Object item, String property, int rowCount)
				        			{
				        				
										String idOperatore		= (String) (new HtmlCellEditor()).getValue(item, "org_id", rowCount);
										
										return "<a href=\"javascript:openaddConcessionario(" + org_id + "," + idOperatore +")\" >Aggiungi</a>";
				        				//return "<a href=\"MolluschiBivalvi.do?command=AggiungiConcessionario&orgId=" + org_id + "&idConcessionario=" + idOperatore +  
				        					//"\" onclick=\"return confirm('Sei sicuro di voler aggiungere l\'operatore selezionato?');\">Aggiungi</a>"; 
						        	}
				        		}
				        
				    );
					
					
					
				}
				
				String tabella = tf.render();
				context.getRequest().setAttribute( "tabella", tabella );
				
				LookupList siteList = new LookupList(db, "lookup_site_id");
				siteList.addItem( 0, "Regione");
				context.getRequest().setAttribute("SiteList", siteList);
			      
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				this.freeConnection( context, db );
			}
			
			return ret;
		}
}
