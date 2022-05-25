/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;

import it.us.web.bean.BGuiView;
import it.us.web.bean.UserOperation;
import it.us.web.dao.GuiViewDAO;
import it.us.web.exceptions.AuthorizationException;

public class LogOperazioniList extends GenericAction {

	@Override
	public void can() throws AuthorizationException, Exception {
		BGuiView gui = GuiViewDAO.getView( "LOG_OPERAZIONI", "FUNZIONI", "LIST" );
		can( gui, "w" );
	}

	@Override
	public void setSegnalibroDocumentazione() {
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}

	
	@Override
	public void execute() throws Exception {
		String username = (String) req.getParameter("username");
		String dateStart = (String) req.getParameter("dateStart");
		String dateEnd = (String) req.getParameter("dateEnd");
		ArrayList<UserOperation> op_list = new ArrayList<UserOperation>();
		
		Context ctx;
		Connection db = null;
		try {
			ctx = new InitialContext();
			javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/storico");
			db = ds.getConnection();
			if (db!=null){
				UserOperation o = new UserOperation();
				op_list = o.buildList(db, username, dateStart, dateEnd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db!=null){
				try {
					db.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		req.setAttribute("op_list", op_list);
		gotoPage("/jsp/logOperazioni/list.jsp");
	}
}
