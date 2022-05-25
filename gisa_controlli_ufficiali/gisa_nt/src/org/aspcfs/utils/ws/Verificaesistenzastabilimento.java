/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils.ws;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.gestioneanagrafica.base.Stabilimento;
import org.aspcfs.modules.noscia.dao.StabilimentoDAO;

import com.darkhorseventures.framework.actions.ActionContext;
import com.google.gson.Gson;



public class Verificaesistenzastabilimento extends CFSModule
{

	public String  executeCommandSearch(ActionContext context) throws Exception 
	{
		Gson gson = new Gson();
		String json = "";
		Connection db = null;
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String[]> parameterMap = context.getRequest().getParameterMap();
		Stabilimento stab = new Stabilimento(parameterMap);
		
		Boolean exist = null;
		
		try 
		{
		    db = this.getConnection(context);
		    StabilimentoDAO stabDao = new StabilimentoDAO(stab);
		    exist = stabDao.exist(db);
		    
		    
		}
		catch(Exception errorMessage)
		{
		
		}
		finally
		{
		    this.freeConnection(context, db);
		}
				
		if(exist)
		{
			map.put("status", "1");  
		}
		else
		{
			map.put("status", "2");
		}
		
		json = gson.toJson(map);	
		
		PrintWriter writer = context.getResponse().getWriter();
		writer.print(json);
		writer.close();
		
		return "";
		
	}


}
