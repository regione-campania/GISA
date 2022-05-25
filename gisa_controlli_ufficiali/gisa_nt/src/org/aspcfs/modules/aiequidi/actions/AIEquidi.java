/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.aiequidi.actions;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.aiequidi.base.Aiequidi;
import org.aspcfs.modules.aiequidi.base.AiequidiList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.ExportType;
import org.jmesa.limit.Limit;
import org.jmesa.view.editor.FilterEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.editor.DroplistFilterEditor;

import com.darkhorseventures.framework.actions.ActionContext;

public final class AIEquidi extends CFSModule
{

	public String executeCommandDefault(ActionContext context)
	{
		this.deletePagedListInfo(context, "SearchListEquidiInfo");
		return executeCommandList(context);
	}

	public String executeCommandList( ActionContext context )
	{
		if ( !hasPermission(context, "aiequidi-view") )
		{
			return ("PermissionError");
		}

		AiequidiList organizationList = new AiequidiList();


		// Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context,
		"SearchListEquidiInfo");
		searchListInfo.setLink("AIEquidi.do?command=List");
		searchListInfo.setListView("all");
		SystemStatus systemStatus = this.getSystemStatus(context);
		// Need to reset any sub PagedListInfos since this is a new account
		this.resetPagedListInfo(context);
		Connection db = null;
		try {
			db = this.getConnection(context);

					// Display list of accounts if user chooses not to list contacts
			if (!"true".equals(searchListInfo.getCriteriaValue("searchContacts"))) {
				if ("contacts".equals(context.getSession().getAttribute(
				"previousSearchType"))) {
					this.deletePagedListInfo(context, "SearchListEquidiInfo");
					searchListInfo = this.getPagedListInfo(context,
					"SearchListEquidiInfo");
					searchListInfo.setLink("AIEquidi.do?command=List");
				}
				// Build the organization list
				organizationList.setPagedListInfo(searchListInfo);
				searchListInfo.setSearchCriteria(organizationList, context);
				organizationList.buildList(db);

				context.getRequest().setAttribute("OrgList", organizationList);
				context.getSession().setAttribute("previousSearchType",
				"accounts");

				return "ListOK";
			} else {
				if ("accounts".equals(context.getSession().getAttribute(
				"previousSearchType"))) {
					this.deletePagedListInfo(context, "SearchListEquidiInfo");
					searchListInfo = this.getPagedListInfo(context,
					"SearchListEquidiInfo");
					searchListInfo.setLink("AIEquidi.do?command=List");
				}



				context.getSession().setAttribute("previousSearchType",
				"contacts");
				return ("ListOK");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}
	private void resetPagedListInfo(ActionContext context) {
		
		this.deletePagedListInfo(context, "AccountFolderInfo");
		this.deletePagedListInfo(context, "RptListInfo");
		this.deletePagedListInfo(context, "OpportunityPagedInfo");
		this.deletePagedListInfo(context, "AccountTicketInfo");
		this.deletePagedListInfo(context, "AutoGuideAccountInfo");
		this.deletePagedListInfo(context, "RevenueListInfo");
		this.deletePagedListInfo(context, "AccountDocumentInfo");
		this.deletePagedListInfo(context, "ServiceContractListInfo");
		this.deletePagedListInfo(context, "AssetListInfo");
		this.deletePagedListInfo(context, "AccountProjectInfo");
		this.deletePagedListInfo(context, "orgHistoryListInfo");
		

	}

	public String executeCommandListMobile( ActionContext context ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{


		Connection		db				= null;

		ArrayList<Aiequidi> listaEquidi = new ArrayList<Aiequidi>();
		try
		{
			db = getConnection(context);
			if(context.getRequest().getParameter("idCapo")!=null)
			{
				String query = "select num_rapporto,num_accettazione,citta,ragione_sociale,codazie,nomin_utente,anno,num_capi_prelevati,num_acc_progressivo_campione,data_fine_prova,to_char(data_fine_prova,'dd/MM/yyyy') as data_fine_prova_string,data_prelievo,to_char(data_prelievo,'dd/MM/yyyy') as data_prelievo_string,data_accettazione,to_char(data_accettazione,'dd/MM/yyyy') as data_accettazione_string,esito,risultato,id_capo  from a_i_equidi where id_capo ilike ?";
				PreparedStatement pst = db.prepareStatement(query);
				pst.setString(1, "%"+context.getRequest().getParameter("idCapo").trim()+"%");
				ResultSet rs = pst.executeQuery() ;
				while (rs.next())
				{

					Aiequidi tmp = new Aiequidi();
					listaEquidi.add(tmp);

				}

				context.getRequest().setAttribute("ListaEquidi", listaEquidi);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);

		}
		return "ListMobileOK";

	}

}
