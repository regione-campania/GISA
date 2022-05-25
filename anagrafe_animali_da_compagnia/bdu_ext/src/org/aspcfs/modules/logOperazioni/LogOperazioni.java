/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.logOperazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.UserOperation;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;

public class LogOperazioni extends CFSModule {

	
	public String executeCommandHome(ActionContext context){
		return "HomeOK";
	}
	
	public String executeCommandSearch(ActionContext context){
		String username = context.getRequest().getParameter("searchusername");
		String date_start = context.getRequest().getParameter("searchtimestampdatestart");
		String date_end = context.getRequest().getParameter("searchtimestampdateend");
		context.getRequest().setAttribute("searchusername", username);
		context.getRequest().setAttribute("searchtimestampdatestart", date_start);
		context.getRequest().setAttribute("searchtimestampdateend", date_end);
		
		Connection db = null;
		
		
		try {
			db = GestoreConnessioni.getConnectionStorico(context);
			LogOperazioniList op_list = new LogOperazioniList();
			op_list.setUsername(username);
			op_list.setDatestart(DateUtils.parseDateStringNew(date_start, "dd/MM/yyyy"));
			op_list.setDateend(DateUtils.parseDateStringNew(date_end, "dd/MM/yyyy"));
			
			PagedListInfo searchListInfo = this.getPagedListInfo(context,"op_list");
			searchListInfo.setLink("LogOperazioni.do?command=Search");
			searchListInfo.setListView("all");
			op_list.setPagedListInfo(searchListInfo);	
			op_list.setMinerOnly(false);
			op_list.setTypeId(searchListInfo.getFilterKey("listFilter1"));
			op_list.setStageId(searchListInfo.getCriteriaValue("searchcodeStageId"));
			searchListInfo.setSearchCriteria(op_list, context);
			
			op_list.buildList(db,context.getServletContext().getInitParameter("context_starting"));

			context.getRequest().setAttribute("op_list", op_list);
			
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			if (db!=null)
				GestoreConnessioni.freeConnectionStorico(db);
		}
		return "ListOK";
	}
	
	public String executeCommandDetails(ActionContext context){
		String idOp = context.getRequest().getParameter("idOp");
		UserOperation uo = new UserOperation();
		Connection db = null;
		try {
			db = GestoreConnessioni.getConnectionStorico(context);
			uo.buildRecord(idOp, db, context.getServletContext().getInitParameter("context_starting"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (db!=null)
				GestoreConnessioni.freeConnectionStorico(db);
		}
		context.getRequest().setAttribute("UODetails", uo);
		
		String username = context.getRequest().getParameter("searchusername");
		String date_start = context.getRequest().getParameter("searchtimestampdatestart");
		String date_end = context.getRequest().getParameter("searchtimestampdateend");
		context.getRequest().setAttribute("searchusername", username);
		context.getRequest().setAttribute("searchtimestampdatestart", date_start);
		context.getRequest().setAttribute("searchtimestampdateend", date_end);
		
		return "DetailsOK";
	}
}
