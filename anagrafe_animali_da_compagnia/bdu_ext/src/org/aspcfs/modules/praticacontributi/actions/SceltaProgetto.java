/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.praticacontributi.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.login.beans.UserBean;
//import org.aspcfs.modules.praticaContributiGestioneSeparata.base.PraticaNew;
import org.aspcfs.modules.praticacontributi.base.Pratica;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class SceltaProgetto extends CFSModule{
	
	public String executeCommandDashboardScelta(ActionContext context) {
		   /* if (!hasPermission(context, "-view")) {
			      if (!hasPermission(context, "-view")) {
			        return ("PermissionError");
			      }
			     
			    }
			*/	 
		 Connection		db		= null;
		 Pratica thisOrg = (Pratica) context.getFormBean();
		// PraticaNew thisOrg2= (PraticaNew) context.getFormBean();
		 try{
			  //connessione al db
			  db = getConnection(context);
			  buildFormElements(context, db);
			  context.getRequest().setAttribute("pratica", thisOrg);
			//  context.getRequest().setAttribute("pratica2", thisOrg2);
			  String currentDate = getCurrentDateAsString(context);
			  context.getRequest().setAttribute("currentDate", currentDate);


		 }
		 catch (Exception e) {
			  //An error occurred, go to generic error message page
			  e.printStackTrace();
			  context.getRequest().setAttribute("Error", e);
			 // logger.severe("[CANINA] - Si è verificata una eccezione mell'inserimento di una pratica ");
			  return ("SystemError");
		}
		finally
		{
		      this.freeConnection(context, db);
			  System.gc();
		}
		     return ("DashboardSceltaOK");
	}
	
	
	public void buildFormElements(ActionContext context, Connection db) throws SQLException {
		SystemStatus thisSystem = this.getSystemStatus(context);
		
		LookupList aslList = new LookupList(db, "lookup_asl_rif");
	    aslList.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));
	    context.getRequest().setAttribute("aslRifList", aslList);

	    
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
	
	    
	    String qry2="select comune from comuni join lookup_asl_rif on (codiceistatasl=codiceistat) where code=? order by comune ";

	    String qry1="select o.org_id,o.name from organization o left join account_type_levels l on l.org_id=o.org_id where l.type_id=11 and  o.trashed_date is null  and  o.data_chiusura_canile is null and o.asl_rif= ? ";

	    HashMap<Integer, HashMap<Integer, String>> hashAslCanili = new HashMap<Integer, HashMap<Integer,String>>();
	   	HashMap<Integer, String> hashCanili =null;
	    
	    if(thisUser.getSiteId()<=0){
	    	
	 	   for(int i=201;i<=207;i++){
	 		   		ArrayList<String> tmp=new ArrayList<String>();
	 	    		PreparedStatement pst2=db.prepareStatement(qry2);
	 	      		pst2.setInt(1,i);
	 	      		ResultSet rs2=pst2.executeQuery();
	     	
	 	      		while(rs2.next()){
	 	      				tmp.add("\""+rs2.getString("comune")+"\"");
	 	      		}
	 	      		String tempo="[";
	 	      		for(String cc:tmp){
	 	      				tempo=tempo+cc+",";
	     		
	 	      		}
	 	      		tempo=tempo.substring(0, tempo.length()-1)+"]";
	 	      		hashtable.put(""+i,tmp);
     		

	 			   	hashCanili = new HashMap<Integer, String>();
	 			    PreparedStatement pst1=db.prepareStatement(qry1);
	 			    pst1.setInt(1,i);
	 			    ResultSet rs1=pst1.executeQuery(); 
	 	    
	 		  		while(rs1.next()){
	 	      			hashCanili.put(rs1.getInt("org_id"), rs1.getString("name").replaceAll("'", ""));
	 	      		}
	 	      		hashAslCanili.put(i, hashCanili);
	 	  }
	 	  System.out.println("prima "+hashtable.size());
	     }
	    else{
	 	   if(thisUser.getSiteId()>0){
	 		   
	 		  ArrayList<String> tmp2=new ArrayList<String>();
	 		   PreparedStatement pst2=db.prepareStatement(qry2);
	       		pst2.setInt(1,thisUser.getSiteId());
	       		ResultSet rs2=pst2.executeQuery();
	       		int i=0;
	       		while(rs2.next()){
	 				tmp2.add("\""+rs2.getString("comune")+"\"");
	 				i++;
	 			}
	       		context.getRequest().setAttribute("lista", tmp2);
	       		
	     	   }
	 	  System.out.println("seconda "+hashtable.size());
	    }
	     context.getRequest().setAttribute("hashtable", hashtable);
	     context.getRequest().setAttribute("hashCanili", hashCanili);
	     context.getRequest().setAttribute("hashAslCanili", hashAslCanili);
	     
	}
	
}


