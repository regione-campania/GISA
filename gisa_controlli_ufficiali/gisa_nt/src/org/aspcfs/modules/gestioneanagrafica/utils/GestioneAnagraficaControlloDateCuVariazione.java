/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneanagrafica.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.utils.GestoreConnessioni;
import org.directwebremoting.extend.LoginRequiredException;

import com.darkhorseventures.framework.actions.ActionContext;

public class GestioneAnagraficaControlloDateCuVariazione extends CFSModule{
	
	public String executeCommandSearch(ActionContext context) throws IOException{
			
			String output = "";
			String id_cu = "";
			String data_variazione = "";
			id_cu = context.getRequest().getParameter("id_cu");
			data_variazione = context.getRequest().getParameter("data_variazione");
			
			String sql = "select count(*) as esito "
					+ "from ticket "
					+ "where ticketid = ? "
					+ "and to_char(assigned_date, 'YYYY-MM-DD')::timestamp without time zone <= ?::timestamp without time zone";
			Connection db = null;
			try{
				db = GestoreConnessioni.getConnection();
				PreparedStatement pst = db.prepareStatement(sql);
				pst.setInt(1, Integer.parseInt(id_cu));
				pst.setString(2, data_variazione);
				ResultSet rs= pst.executeQuery();
				
				System.out.println("query verifica da cu vs variazione " + pst);
				while(rs.next())
				{
					if(rs.getInt("esito") > 0){
						output = "1";
					} else {
						output = "0";
					}
					
				}
			
			}catch(LoginRequiredException e)
			{
				throw e;
			}catch(Exception e)
			{
				e.printStackTrace();		
			}
			finally
			{
				GestoreConnessioni.freeConnection(db);
			}
			
			PrintWriter writer = context.getResponse().getWriter();
			writer.print("{\"esito\":\""+ output +"\"}");
			writer.close();
			return "";	
			
		}
}
