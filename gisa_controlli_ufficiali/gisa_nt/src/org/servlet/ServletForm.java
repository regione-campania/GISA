/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.servlet;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.login.beans.UserBean;

import org.aspcfs.utils.GestoreConnessioni;
import org.json.JSONArray;
import org.json.JSONObject;
import com.darkhorseventures.database.ConnectionPool;

/**
 * Servlet implementation class ServletForm
 */
public class ServletForm extends HttpServlet {
        private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletForm() {
        super();
        // TODO Auto-generated constructor stub
    }

        /**
         * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
         */
        protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        	
        	Connection db = null;
        	PreparedStatement pst = null;
            JSONArray json_arr=null;
            json_arr = new JSONArray();

    		try {
    			
//    			String dbName = ApplicationProperties.getProperty("dbnameBdu");
//    			String username = ApplicationProperties.getProperty("usernameDbbdu");
//    			String pwd =ApplicationProperties.getProperty("passwordDbbdu");
//    			String host = InetAddress.getByName("hostDbBdu").getHostAddress();
//    			
//
//    			db = DbUtil.getConnection(dbName, username,
//    					pwd, host);
    			
    			
    			db = GestoreConnessioni.getConnection();
    			
    			
		
                // TODO Auto-generated method stub
               // System.out.println("nella servlet");

                String idPiano = request.getParameter("motivazione_piano_campione");
                String idControllo = request.getParameter("idControllo");
                
                        // String test = "<input type= \"text\" value=\"ihih\" />";
                JSONObject json_obj = null ;
             
                	ArrayList fields = new ArrayList();
                	
              
                	
                	fields = GestoreEventi.getFieldsByEventoId(db, new Integer(idPiano), new Integer(idControllo));
                	
                	
                	for (int i = 0; i < fields.size(); i++){
                		json_obj=new JSONObject(((HashMap)fields.get(i)));
                		 json_arr.put(json_obj);
                	}
                	
                	
        }catch (Exception e){
                System.out.println(e);
        }finally {
        	GestoreConnessioni.freeConnection(db);
		}
        
       
		//GestoreConnessioni.freeConnection(db);
		//db = null;
        response.getWriter().println(json_arr);
        }
        /**
         * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
         */
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                // TODO Auto-generated method stub
                System.out.println("nella servlet GET");
        }

        /**
         * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
         */
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                // TODO Auto-generated method stub
        }

}