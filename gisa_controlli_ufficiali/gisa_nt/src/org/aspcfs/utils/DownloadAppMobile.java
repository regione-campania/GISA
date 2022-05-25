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

import org.aspcfs.modules.actions.CFSModule;
import org.json.JSONArray;

import com.darkhorseventures.framework.actions.ActionContext;

public class DownloadAppMobile extends CFSModule{
	
	public String  executeCommandSuiteAppMobile(ActionContext context) {
		return "SuiteAppMobile";
	}
	
	public String executeCommandAppMobileIosPreaccettazionesigla(ActionContext context) {
		
		Connection db = null;

        try{
        	 String ambiente = org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("ambiente");
        	 String codice_riscatto_app = "";
        	 
        	 if(ambiente.equalsIgnoreCase("ufficiale")){
        		 db = this.getConnection(context);
            	 String sql = "select download_app_mobile from download_app_mobile(?, ?);";
             	 PreparedStatement st = db.prepareStatement(sql);
             	 st.setInt(1, 1);
             	 st.setInt(2, getUserId(context));
                 ResultSet rs = st.executeQuery();

                 while(rs.next()){
                	 codice_riscatto_app = rs.getString("download_app_mobile");
                 }
        	 } else {
        		 codice_riscatto_app = "ambientedemo";
        	 }
        	 
             context.getRequest().setAttribute("codice_riscatto_app", codice_riscatto_app);
             
        }catch (SQLException e) {
        	e.printStackTrace();
        } finally {
            this.freeConnection(context, db);
        }
		
		return "AppMobileIosPreaccettazionesiglaOK";
	}
	
	
	public String executeCommandRigeneraAppMobileIosPreaccettazionesigla(ActionContext context) {
		
		Connection db = null;

        try{
        	String ambiente = org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("ambiente");
       	 	String codice_riscatto_app = "";
       	 
       	 	if(ambiente.equalsIgnoreCase("ufficiale")){
        	
				db = this.getConnection(context);
				String sql = "select riassegnazione_codice_app_mobile from riassegnazione_codice_app_mobile(?, ?);";
				PreparedStatement st = db.prepareStatement(sql);
				st.setInt(1, 1);
				st.setInt(2, getUserId(context));
				ResultSet rs = st.executeQuery();
				 
				while(rs.next()){
					codice_riscatto_app = rs.getString("riassegnazione_codice_app_mobile");
				}
       	 	} else {
       	 		codice_riscatto_app = "ambientedemo";
       	 	}
            context.getRequest().setAttribute("codice_riscatto_app", codice_riscatto_app);
             
        }catch (SQLException e) {
        	e.printStackTrace();
        } finally {
            this.freeConnection(context, db);
        }
		
		return "AppMobileIosPreaccettazionesiglaOK";
	}
	
public String executeCommandAppMobileIosGisaWebGis(ActionContext context) {
		
		Connection db = null;

        try{
        	String ambiente = org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("ambiente");
       	 	String codice_riscatto_app = "";
       	 
       	 	if(ambiente.equalsIgnoreCase("ufficiale")){
	        	 db = this.getConnection(context);
	        	 String sql = "select download_app_mobile from download_app_mobile(?, ?);";
	         	 PreparedStatement st = db.prepareStatement(sql);
	         	 st.setInt(1, 2); //id app gisa webgis = 2
	         	 st.setInt(2, getUserId(context));
	             ResultSet rs = st.executeQuery();

	             while(rs.next()){
	            	 codice_riscatto_app = rs.getString("download_app_mobile");
	             }
       	 	} else {
       	 		codice_riscatto_app = "ambientedemo";
       	 	}
            context.getRequest().setAttribute("codice_riscatto_app", codice_riscatto_app);
             
        }catch (SQLException e) {
        	e.printStackTrace();
        } finally {
            this.freeConnection(context, db);
        }
		
		return "AppMobileIosGisaWebGisOK";
	}
	
	
	public String executeCommandRigeneraAppMobileIosGisaWebGis(ActionContext context) {
		
		Connection db = null;

        try{
        	String ambiente = org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("ambiente");
       	 	String codice_riscatto_app = "";
       	 
       	 	if(ambiente.equalsIgnoreCase("ufficiale")){
	        	 db = this.getConnection(context);
	        	 String sql = "select riassegnazione_codice_app_mobile from riassegnazione_codice_app_mobile(?, ?);";
	         	 PreparedStatement st = db.prepareStatement(sql);
	         	 st.setInt(1, 2); //id app gisa webgis = 2
	         	 st.setInt(2, getUserId(context));
	             ResultSet rs = st.executeQuery();
	          
	             while(rs.next()){
	            	 codice_riscatto_app = rs.getString("riassegnazione_codice_app_mobile");
	             }
       	 	} else {
       	 		codice_riscatto_app = "ambientedemo";
       	 	}
            context.getRequest().setAttribute("codice_riscatto_app", codice_riscatto_app);
             
        }catch (SQLException e) {
        	e.printStackTrace();
        } finally {
            this.freeConnection(context, db);
        }
		
		return "AppMobileIosGisaWebGisOK";
	}

}
