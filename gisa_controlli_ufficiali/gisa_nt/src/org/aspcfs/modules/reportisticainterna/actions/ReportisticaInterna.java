/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.reportisticainterna.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.reportisticainterna.base.Report;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public final class ReportisticaInterna extends CFSModule {

	public String executeCommandDefault(ActionContext context) {

		if (!(hasPermission(context, "reportistica-interna-view"))) {
			return ("PermissionError");
		}

		Connection db = null;
		try {
			db = this.getConnection(context);
			
			ArrayList<Report> listaReport = new ArrayList<Report>();
			listaReport = Report.getElenco(db);
			context.getRequest().setAttribute("listaReport", listaReport);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.removeElementByCode(16);
			//siteList.addItem(300, "TUTTE");
			context.getRequest().setAttribute("SiteList", siteList);
		
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("DefaultOK");
	}

	 public String executeCommandReport(ActionContext context) {

			if (!(hasPermission(context, "reportistica-interna-view"))) {
				return ("PermissionError");
			}

			Connection db = null;
			try {
				db = this.getConnection(context);
				
				int idReport = Integer.parseInt(context.getRequest().getParameter("idReport")); 
				int idAsl = Integer.parseInt(context.getRequest().getParameter("idAsl")); 
				
				Report report = new Report(db, idReport);
				report.eseguiQueryCount(db, idAsl); 
				context.getRequest().setAttribute("report", report); 
				
				LookupList siteList = new LookupList(db, "lookup_site_id");
				siteList.removeElementByCode(16);
				//siteList.addItem(300, "TUTTE"); 
				context.getRequest().setAttribute("SiteList", siteList);
				
				context.getRequest().setAttribute("idAsl", String.valueOf(idAsl)); 

			} catch (Exception errorMessage) {
				context.getRequest().setAttribute("Error", errorMessage);
				return ("SystemError");
			} finally {
				this.freeConnection(context, db);
			}
			return ("ReportOK");
		}
	 
	 public String executeCommandLista(ActionContext context) {

			if (!(hasPermission(context, "reportistica-interna-view"))) {
				return ("PermissionError");
			}

			Connection db = null;
			try {
				db = this.getConnection(context);
				
					
				int idReport = Integer.parseInt(context.getRequest().getParameter("idReport")); 
				int idAsl = Integer.parseInt(context.getRequest().getParameter("idAsl")); 
				String fonte = context.getRequest().getParameter("fonte"); 

				Report report = new Report(db, idReport);
				report.eseguiQuery(db, idAsl, fonte);
				context.getRequest().setAttribute("report", report); 
				
				LookupList siteList = new LookupList(db, "lookup_site_id"); 
				siteList.removeElementByCode(16);
				//siteList.addItem(300, "TUTTE"); 
				context.getRequest().setAttribute("SiteList", siteList);
				
				context.getRequest().setAttribute("idAsl", String.valueOf(idAsl)); 

			} catch (Exception errorMessage) {
				context.getRequest().setAttribute("Error", errorMessage);
				return ("SystemError");
			} finally {
				this.freeConnection(context, db);
			}
			return ("ReportListaOK");
		}
	 
	 public String executeCommandListaNonPresenti(ActionContext context) {

			if (!(hasPermission(context, "reportistica-interna-view"))) {
				return ("PermissionError");
			}

			Connection db = null;
			try {
				db = this.getConnection(context);
				
					
				int idReport = Integer.parseInt(context.getRequest().getParameter("idReport")); 
				int idAsl = Integer.parseInt(context.getRequest().getParameter("idAsl")); 
				String fonte = context.getRequest().getParameter("fonte"); 

				Report report = new Report(db, idReport);
				report.eseguiQueryRecordNonPresenti(db, idAsl, fonte);
				context.getRequest().setAttribute("report", report); 
				
				LookupList siteList = new LookupList(db, "lookup_site_id");
				siteList.removeElementByCode(16);
				//siteList.addItem(300, "TUTTE"); 
				context.getRequest().setAttribute("SiteList", siteList);
				
				context.getRequest().setAttribute("idAsl", String.valueOf(idAsl)); 

			} catch (Exception errorMessage) {
				context.getRequest().setAttribute("Error", errorMessage);
				return ("SystemError");
			} finally {
				this.freeConnection(context, db);
			}
			return ("ReportListaOK");
		}
	
}

