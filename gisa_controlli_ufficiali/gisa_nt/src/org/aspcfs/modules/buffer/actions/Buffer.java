/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.buffer.actions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.allerte.base.AllerteAslHistory;
import org.aspcfs.modules.allerte.base.AllerteHistory;
import org.aspcfs.modules.allerte.base.AslCoinvolte;
import org.aspcfs.modules.allerte.base.ImpreseCoinvolte;
import org.aspcfs.modules.allerte.base.Ticket;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.GenericControlliUfficiali;
import org.aspcfs.modules.buffer.base.BufferList;
import org.aspcfs.modules.buffer.base.Comune;
import org.aspcfs.modules.login.beans.UserBean;
//import org.aspcfs.modules.troubletickets.base.TicketCategoryList;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;

public class Buffer  extends CFSModule {



	public String executeCommandSearch(ActionContext context) {
		if (!hasPermission(context, "buffer-buffer-view")) {
			return ("PermissionError");
		}
		PagedListInfo bufferListInfo = this.getPagedListInfo(context, "BufferListInfo");

		Connection db = null;
		User user = this.getUser(context, this.getUserId(context));
		BufferList bufferList = new BufferList();
		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
		String dataEvento = (String)context.getRequest().getParameter("searchtimestampDataEvento");

		String stato = (String)context.getRequest().getParameter("searchcodeStato");
		if( stato != null )
		{
			bufferList.setStato(stato);
		}

		bufferListInfo.setColumnToSortBy("data_evento");
		bufferListInfo.setSortOrder("desc");
		bufferList.setDataEvento(dataEvento);
		bufferListInfo.setLink("Buffer.do?command=Search");
		bufferList.setPagedListInfo(bufferListInfo);
		bufferListInfo.setSearchCriteria(bufferList, context);
		SystemStatus systemStatus = this.getSystemStatus(context);
		
		String[] comuniCoinvolti = context.getRequest().getParameterValues("comuniCoinvolti");
		if (comuniCoinvolti != null)
		for (int i = 0 ; i < comuniCoinvolti.length ; i ++)
		{
			Comune c = new Comune();
			c.setId(Integer.parseInt(comuniCoinvolti[i]));
			bufferList.getListaComuni().add(c);
		}

		try 
		{
			db = this.getConnection(context);

			bufferList.buildList(db);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("BufferList", bufferList);
		addModuleBean(context, "SearchTickets", "Search Tickets");
		return ("ResultsOK");
	}

	
	public String executeCommandSearchForm(ActionContext context) {
		if (!hasPermission(context, "buffer-buffer-view")) {
			return ("PermissionError");
		}
		PagedListInfo bufferListInfo = this.getPagedListInfo(context, "BufferListInfo");

		Connection db = null;
	

		try 
		{
			db = this.getConnection(context);

			
			Comune c = new Comune() ;
			LookupList lookupComuni= new LookupList(c.getComuni(db),true);
			
			context.getRequest().setAttribute("ListaComuni",lookupComuni);
			
			LookupList lookupBufferStato = new LookupList(db,"lookup_buffer_stato");
			lookupBufferStato.addItem(-1,"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("ListaStato",lookupBufferStato);
		
			
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "SearchTickets", "Search Tickets");
		return ("SearchFormOK");
	}

	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "buffer-buffer-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		org.aspcfs.modules.buffer.base.Buffer newBuffer = null;
		try {
			db = this.getConnection(context);

			SystemStatus systemStatus = this.getSystemStatus(context);
			Comune c = new Comune() ;
			LookupList lookupComuni= new LookupList(c.getComuni(db),true);
			
			context.getRequest().setAttribute("ListaComuni",lookupComuni);
			
			LookupList lookupBufferStato = new LookupList(db,"lookup_buffer_stato");
			lookupBufferStato.addItem(-1,"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("ListaStato",lookupBufferStato);

			context.getRequest().setAttribute(
					"systemStatus", this.getSystemStatus(context));
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		String retPage = "AddOK";
		return ( retPage );
	}



	/**
	 * Description of the Method
	 *
	 * @param context Description of Parameter
	 * @return Description of the Returned Value
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public String executeCommandInsert(ActionContext context) {
		if (!(hasPermission(context, "buffer-buffer-add"))) {
			return ("PermissionError");
		}
		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = true;

		org.aspcfs.modules.buffer.base.Buffer  newBuffer  = (org.aspcfs.modules.buffer.base.Buffer) context.getFormBean();
		newBuffer.setEnteredby(this.getUserId(context));
		newBuffer.setModifiedby(this.getUserId(context));
		try {
			db = this.getConnection(context);
			String[] comuniCoinvolti = context.getRequest().getParameterValues("comuniCoinvolti");
			for (int i = 0 ; i < comuniCoinvolti.length ; i ++)
			{
				Comune c = new Comune();
				c.setId(Integer.parseInt(comuniCoinvolti[i]));
				newBuffer.getListaComuni().add(c);
			}
			newBuffer.insert(db,context);
			
			context.getRequest().setAttribute("idBuffer", newBuffer.getId()+"");

		}
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

			return executeCommandDetails(context);

			
	}
	
	public String executeCommandUpdate(ActionContext context) {
		if (!(hasPermission(context, "buffer-buffer-edit"))) {
			return ("PermissionError");
		}
		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = true;

		org.aspcfs.modules.buffer.base.Buffer  newBuffer  = (org.aspcfs.modules.buffer.base.Buffer) context.getFormBean();
		newBuffer.setEnteredby(this.getUserId(context));
		newBuffer.setModifiedby(this.getUserId(context));
		try {
			db = this.getConnection(context);
			String[] comuniCoinvolti = context.getRequest().getParameterValues("comuniCoinvolti");
			for (int i = 0 ; i < comuniCoinvolti.length ; i ++)
			{
				Comune c = new Comune();
				c.setId(Integer.parseInt(comuniCoinvolti[i]));
				newBuffer.getListaComuni().add(c);
			}
			newBuffer.update(db,context);
			
			context.getRequest().setAttribute("idBuffer", newBuffer.getId()+"");

		}
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

			return executeCommandDetails(context);

			
	}
	
	
	public String executeCommandModificaStato(ActionContext context) {
		if (!(hasPermission(context, "buffer-buffer-edit"))) {
			return ("PermissionError");
		}
		Connection db = null;

		
		try {
			db = this.getConnection(context);

			String bufferId =context.getParameter("id");
			String stato =context.getParameter("idStato");
			if (bufferId == null)
			{
				bufferId = (String )context.getRequest().getAttribute("idBuffer");
			}

				org.aspcfs.modules.buffer.base.Buffer bufferDetails = new org.aspcfs.modules.buffer.base.Buffer(db,Integer.parseInt(bufferId));
				bufferDetails.setStato (stato) ;
				
				bufferDetails.update(db,context);
				context.getRequest().setAttribute("BufferDetails", bufferDetails);
		}
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

			return executeCommandDetails(context);

			
	}


	public String executeCommandDetails(ActionContext context) {
		if (!(hasPermission(context, "buffer-buffer-view"))) {
			return ("PermissionError");
		}

		Connection db = null;
		String bufferId =context.getParameter("id");
		
		//String bufferId = (String )context.getRequest().getAttribute("idBuffer");
		if (bufferId == null)
		{
			bufferId = (String )context.getRequest().getAttribute("idBuffer");
		}

		try {
			db = this.getConnection(context);

			org.aspcfs.modules.buffer.base.Buffer bufferDetails = new org.aspcfs.modules.buffer.base.Buffer(db,Integer.parseInt(bufferId));
			context.getRequest().setAttribute("BufferDetails", bufferDetails);

		}
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		
		return "DetailsOK";
		
	}
	
	public String executeCommandModify(ActionContext context) {
		if (!(hasPermission(context, "buffer-buffer-edit"))) {
			return ("PermissionError");
		}


		Connection db = null;
		
		String bufferId =context.getParameter("id");
		if (bufferId == null)
		{
			bufferId = (String )context.getRequest().getAttribute("idBuffer");
		}

		try {
			db = this.getConnection(context);
			
			Comune c = new Comune() ;
			LookupList lookupComuni= new LookupList(c.getComuni(db),true);
			
			context.getRequest().setAttribute("ListaComuni",lookupComuni);
			
			LookupList lookupBufferStato = new LookupList(db,"lookup_buffer_stato");
			lookupBufferStato.addItem(-1,"-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("ListaStato",lookupBufferStato);

			org.aspcfs.modules.buffer.base.Buffer bufferDetails = new org.aspcfs.modules.buffer.base.Buffer(db,Integer.parseInt(bufferId));
			context.getRequest().setAttribute("BufferDetails", bufferDetails);

		}
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		
		return "ModifyOK";
		
	}





}
