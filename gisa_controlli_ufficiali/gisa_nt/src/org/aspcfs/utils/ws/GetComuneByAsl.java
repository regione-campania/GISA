/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils.ws;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.gestioneanagrafica.base.Comune;
import org.aspcfs.modules.noscia.dao.ComuneDAO;

import com.darkhorseventures.framework.actions.ActionContext;
import com.google.gson.Gson;



public class GetComuneByAsl extends CFSModule{


    public String executeCommandSearch(ActionContext context) throws Exception {
        
        Gson gson = new Gson();
        Connection db = null;
        String json = "";

        
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, String[]>  parameterMap = context.getRequest().getParameterMap();
        
        Comune comune = new Comune(parameterMap);
        ArrayList<Comune> comuni  = new ArrayList<>();
        
        
        try{
             ComuneDAO comuneDAO = new ComuneDAO(comune);
             comuni = comuneDAO.getItems(db);
          } catch (Exception errorMessage) {
              context.getRequest().setAttribute("Error", errorMessage);
              return ("SystemError");
            } finally {
              this.freeConnection(context, db);
            }
        
        if(comuni.size()>0)
        {
            map.put("status", "OK");
            map.put("comuni", comuni);
            
            json = gson.toJson(map);        
        }
        else
        {
            map.put("status", "KO");
             json = gson.toJson(map);
            
        }
        
        PrintWriter writer = context.getResponse().getWriter();
        writer.print(json);
        writer.close();

        return "";
        
    }

}
