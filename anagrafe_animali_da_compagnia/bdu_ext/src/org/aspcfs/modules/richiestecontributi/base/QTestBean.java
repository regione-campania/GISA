/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.richiestecontributi.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.actions.ActionContext;

public class QTestBean {
	public String query;
	
//	private static Logger log = Logger.getLogger(org.aspcfs.modules.richiestecontributi.base.ListaCani.class);
	private transient static java.util.logging.Logger logger = java.util.logging.Logger.getLogger("MainLogger");

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
	public ResultSet executeQuery (Connection db, String query,ActionContext context)throws SQLException {
		
		PreparedStatement ps = db.prepareStatement(query);
		ResultSet rs = null;
		try
		{
			 rs = DatabaseUtils.executeQuery(db, ps);
		}
		catch(SQLException e)
		{
			logger.severe("[CANINA] - EXCEPTION nel metodo executeQuery della classe QTestBean");
			context.getRequest().setAttribute("ErroreQ", e.getMessage());
		}
		
		
		return rs;
		
				
	}
}
