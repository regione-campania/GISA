/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Report;

import com.darkhorseventures.framework.actions.ActionContext;

public class ReportPreaccettazione extends CFSModule{
	
	public String executeCommandReport(ActionContext context) throws ParseException {
        Connection db = null;

        try{
        	 db = this.getConnection(context);
        	 String sql = "select report from preaccettazione.report_interno_codici_preaccettazione()";
         	 PreparedStatement st = db.prepareStatement(sql);
             ResultSet rs = st.executeQuery();
             
             JSONArray report_preac = null;
             
             while(rs.next()){
            	 report_preac = new JSONArray(rs.getString("report"));
             }
             
             context.getRequest().setAttribute("report_preac", report_preac);
             
        }catch (SQLException e) {
        	e.printStackTrace();
        } finally {
            this.freeConnection(context, db);
        }

        return "ReportPreaccettazioneOK";

    }

	public String executeCommandSearchForm(ActionContext context) throws ParseException {
       
        return "SearchPreaccettazione";

    }
	
	public String executeCommandSearch(ActionContext context) throws ParseException {
	    
		Map<String, String[]> parameterMap = context.getRequest().getParameterMap();
	    //parameterMap.get("partita_iva"); esempio di accesso
		
		Map<String, String> filtriRicerca = new HashMap<String, String>();
		String valore_campo;
		String chiave_campo;
		
		for (String key: parameterMap.keySet()){
			valore_campo = parameterMap.get(key)[0].trim();
			chiave_campo = key.trim();
			if (chiave_campo.startsWith("_b_"))
			{
				filtriRicerca.put(chiave_campo.replaceFirst("_b_", ""), valore_campo);
			}
		}
		
		Connection db = null;
		
        try{
        	 db = this.getConnection(context);
        	 String sql = "select * from preaccettazione.search_preaccettazione(?)";
         	 PreparedStatement st = db.prepareStatement(sql);
         	 st.setObject(1, filtriRicerca);
         	 System.out.println(st);
         	 
             ResultSet rs = st.executeQuery();
             
             JSONArray lista_preac = null;
             
             while(rs.next()){
            	 lista_preac = new JSONArray(rs.getString("search_preaccettazione"));
             }
             
             context.getRequest().setAttribute("lista_preac", lista_preac);
             
        }catch (SQLException e) {
        	e.printStackTrace();
        } finally {
            this.freeConnection(context, db);
        }
		
        return "SearchPreaccettazioneOk";

    }
	
	public String executeCommandDiagnosticaPreaccettazione(ActionContext context) throws ParseException {
	       
        return "DiagnosticaPreaccettazione";

    }
	
	public String executeCommandDiagnosticaPreaccettazioneGeneraPreaccettazione(ActionContext context) throws ParseException {
	       
        return "DiagnosticaPreaccettazioneGeneraPreaccettazione";

    }
	
	public String executeCommandDiagnosticaPreaccettazioneAssociaCampione(ActionContext context) throws ParseException {
	       
        return "DiagnosticaPreaccettazioneAssociaCampione";

    }
	
	public String executeCommandDiagnosticaPreaccettazioneListaCodici(ActionContext context) throws ParseException {
	       
        return "DiagnosticaPreaccettazioneListaCodici";

    }
	
	public String executeCommandDiagnosticaPreaccettazioneRecuperaCodiceDaCmp(ActionContext context) throws ParseException {
	       
        return "DiagnosticaPreaccettazioneRecuperaCodiceDaCmp";

    }
	
	
}
