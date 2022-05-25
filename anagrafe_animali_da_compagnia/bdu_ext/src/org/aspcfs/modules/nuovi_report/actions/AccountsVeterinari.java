/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.nuovi_report.actions;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Hashtable;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;


import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;


public class AccountsVeterinari extends CFSModule{

	public String executeCommandDefault(ActionContext context)
	{
		if (!hasPermission(context, "nuovi-report-veterinari-view")) {
		      return ("PermissionError");
		}
		return (executeCommandProva(context));
	}
	
	public String executeCommandProva(ActionContext context) {

		Connection db = null;

		try {
			db = getConnection(context);
			
			
		} catch (Exception e) {	
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("Nuovi_Report");
	}
	
	//costruisce la lookup dei cani anagrafati per il primo report
	public String executeCommandFiltro(ActionContext context) {

		//controllo sui permessi
		if (!hasPermission(context, "report-veterinari-anagrafati-view")) {
		      return ("PermissionError");
		}
		  
		Connection conn = null;
		try {
		  //connessione al db
			conn = this.getConnection(context);
			
			buildFormElements( context,  conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			this.freeConnection(context, conn);
		}
		return "filtro";
	}
	
	//costruisce la lookup
	public void buildFormElements(ActionContext context, Connection db) throws SQLException {
		SystemStatus thisSystem = this.getSystemStatus(context);
		
		
		LookupList aslList = new LookupList(db, "lookup_asl_rif");
	    aslList.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));
	    context.getRequest().setAttribute("aslRifList", aslList);
	    
	    ArrayList<String> comuneList=new ArrayList<String>();
	    Hashtable<String, ArrayList<String>> hashtable = new Hashtable<String,ArrayList<String>>();
	    String[] x=new String[7];
	    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
	    x[0]="Napoli 1 Centro";
	    x[1]="Napoli 2 Nord";
	    x[2]="Napoli 3 Sud";
	    x[3]="Avellino";
	    x[4]="Caserta";
	    x[5]="Benevento";
	    x[6]="Salerno";
	    String qry2="select comune from comuni join lookup_asl_rif on (codiceistatasl=codiceistat) where code=?";
	    if(thisUser.getSiteId()==-1){
	    	String qry3="select asl from access a left join contact c on a.user_id=c.user_id where a.role_id=24 and a.user_id=?";
			
	    	PreparedStatement pst3=db.prepareStatement(qry3);
			pst3.setInt(1,thisUser.getUserId());
			ResultSet rs3=pst3.executeQuery();
			int i=0;
			if(rs3.next()){
			   i=rs3.getInt("asl");
			}
			String qry4="select description from lookup_asl_rif where code=?";
	    	PreparedStatement pst4=db.prepareStatement(qry4);
			pst4.setInt(1,i);
			ResultSet rs4=pst4.executeQuery();
			if(rs4.next()){
			//	thisUser.setNuovaAsl(rs4.getString("description"));
			}
				
		//	thisUser.setNuovaAslId(i);
			ArrayList<String> tmp=new ArrayList<String>();
    		PreparedStatement pst2=db.prepareStatement(qry2);
      		pst2.setInt(1,i);
      		ResultSet rs2=pst2.executeQuery();
       		int j=0;
      		while(rs2.next()){
  				tmp.add("'"+rs2.getString("comune").replaceAll("'", "")+"'");
  			
  				j++;
  			}
    
      		context.getRequest().setAttribute("lista", tmp);
 
		}
	    context.getRequest().setAttribute("hashtable", hashtable);
	}
	public String executeCommandSterilizza(ActionContext context) {

		if (!hasPermission(context, "report-veterinari-sterilizzati-view")) {
		      return ("PermissionError");
		}
		  
		Connection conn = null;
		try {
		  //connessione al db
			conn = this.getConnection(context);
			
			buildFormElements( context,  conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			this.freeConnection(context, conn);
		}
		return "sterilizzato";
	}
		
}
	
