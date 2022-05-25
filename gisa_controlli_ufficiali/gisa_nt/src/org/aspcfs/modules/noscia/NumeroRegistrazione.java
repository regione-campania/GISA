/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.noscia;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.utils.GestoreConnessioni;
import org.directwebremoting.extend.LoginRequiredException;

import com.darkhorseventures.framework.actions.ActionContext;

public class NumeroRegistrazione extends CFSModule{
	
	public String executeCommandGet(ActionContext context) throws IOException {
		String output = "";
		String comume = context.getRequest().getParameter("comune");
		
		//assegnare l output della query a numero_registrazione
		String numero_registrazione = "";
		
		String sql = "select * from genera_numero_registrazione_da_comune(?) num_reg";
		Connection db = null;
		try{
			db = GestoreConnessioni.getConnection();
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, Integer.parseInt(comume));
			ResultSet rs= pst.executeQuery();
			
			System.out.println("query genera numero registrazione " + pst);
			while(rs.next())
			{
				numero_registrazione = rs.getString("num_reg");		 
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
		
		output = "{\"numero_registrazione\": \"" + numero_registrazione + "\"}";
		
		PrintWriter writer = context.getResponse().getWriter();
		writer.print(output);
		writer.close();
		return "";
	}
	
}
