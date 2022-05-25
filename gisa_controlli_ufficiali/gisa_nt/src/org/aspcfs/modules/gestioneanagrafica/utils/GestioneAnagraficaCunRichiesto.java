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

public class GestioneAnagraficaCunRichiesto extends CFSModule{
	
		public String executeCommandSearch(ActionContext context) throws IOException{
				
			String output = "{}";
			int codiceLinea = Integer.parseInt(context.getRequest().getParameter("codiceLinea"));
			
			
			String sql = "select (string_agg(row_to_json(tab)::text, ',')::json)::text as esito from("
					+ "select cun_obbligatorio::text from public.get_codice_richiesto_ml8(?) cun_obbligatorio) tab";
		
			Connection db = null;
			try{
				db = GestoreConnessioni.getConnection();
				PreparedStatement pst = db.prepareStatement(sql);
				pst.setInt(1, codiceLinea);
				ResultSet rs= pst.executeQuery();
				
				while(rs.next())
				{
					output = rs.getString("esito");		 
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
			writer.print(output);
			writer.close();
			return "";		
		
		}

}
