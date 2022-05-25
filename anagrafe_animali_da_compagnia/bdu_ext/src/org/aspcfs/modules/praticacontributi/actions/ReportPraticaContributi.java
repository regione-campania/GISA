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
import java.util.HashMap;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.richiestecontributi.base.ListaCani;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class ReportPraticaContributi extends CFSModule{

	
	public String executeCommandDefault(ActionContext context) {
		String search = (String)context.getSession().getAttribute("startSearch"); 
		if ( search  != null )
		{
			if (Boolean.parseBoolean(search)) {
				cleanSession(context);
			}
		}
		return "(executeCommandReport(context))";
	}
	
	private void cleanSession(ActionContext context) {
		
	//	context.getSession().removeAttribute("dataDecreto");
	
	}
	
	public String executeCommandReport(ActionContext context) {
		
		Connection db = null;
		try{
			
			db = getConnection(context);
			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
			if (thisUser.getRoleId()==22){ //AMMINISTRAOTRE REGIONE
			 	  context.getRequest().setAttribute("AmministratoreRegione", thisUser);
		    }
			 buildFormElements(context, db);
			
		}
		catch(Exception e){
		//	 logger.severe("[CANINA] - EXCEPTION nella action executeCommandReport della classe ReportPraticaContributi");
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}
		finally{
			this.freeConnection(context, db);
		}

		return ("ProgettoOK");
	}

	
	public void buildFormElements(ActionContext context, Connection db) throws SQLException {
		SystemStatus thisSystem = this.getSystemStatus(context);
	
		LookupList aslList = new LookupList(db, "lookup_asl_rif");
	    aslList.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));
	    context.getRequest().setAttribute("aslRifList", aslList);
	    
	    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
	    
	    String[] x=new String[7];
	    x[0]="Napoli 1 Centro";
	    x[1]="Napoli 2 Nord";
	    x[2]="Napoli 3 Sud";
	    x[3]="Avellino";
	    x[4]="Caserta";
	    x[5]="Benevento";
	    x[6]="Salerno";

	    String qry2="select * from pratiche_contributi  where asl= ? ";
    
	    //hashmap per l'elenco dei progetti
	    HashMap<Integer, String> hashProgetti =null;
	    HashMap<Integer, HashMap<Integer, String>> hashAslProgetti = new HashMap<Integer, HashMap<Integer,String>>();
	    if(thisUser.getSiteId()==0 || thisUser.getRoleId()==24){
	    	
	    	for(int i=201;i<=207;i++){
	    		
	    		hashProgetti = new HashMap<Integer, String>();
	    		
 	    		PreparedStatement pst2=db.prepareStatement(qry2);
 	    		pst2.setInt(1,i);
 	    		
 	    		if (thisUser.getUserRecord().getIdLineaProduttivaRiferimento()>0){
 	      			pst2.setInt(2,thisUser.getSiteId());
 	      		}
 	      	
 	    		ResultSet rs2=pst2.executeQuery();
 	    		
 	      		while(rs2.next()){
 	      			hashProgetti.put(rs2.getInt("id"), rs2.getString("descrizione").replaceAll("'", ""));
 	      		}
 	      		
 	      		hashAslProgetti.put(i, hashProgetti);
 	      }
	    }
	    else {
	    	
		 	   if(thisUser.getSiteId()>0){
	    	
		 		  hashProgetti = new HashMap<Integer, String>();
		 			PreparedStatement pst2=db.prepareStatement(qry2);
		       		pst2.setInt(1,thisUser.getSiteId());
	 	      		if (thisUser.getUserRecord().getIdLineaProduttivaRiferimento()>0){
	 	      			pst2.setInt(2,thisUser.getUserRecord().getIdLineaProduttivaRiferimento());	
	 	      		}

	 	       		ResultSet rs2=pst2.executeQuery();
	 	       		int i=1;
		       		while(rs2.next()){
		       			hashProgetti.put(rs2.getInt("id"), rs2.getString("descrizione").replaceAll("'", "")+" decreto n° "+rs2.getString("numero_decreto")+" del "+rs2.getTimestamp("data_decreto"));
		       		}
		       		hashAslProgetti.put(thisUser.getSiteId(), hashProgetti);
		       		
		 	   }	    	
	    	
	    	
	    	
	    }

	    context.getRequest().setAttribute("hashProgetti", hashProgetti);
	    context.getRequest().setAttribute("hashAslProgetti", hashAslProgetti);
	}
	
	public String executeCommandAvviaEstrazione(ActionContext context) {
		
		if (!hasPermission(context, "report-cani-progetti-sterilizzazione-view")) {
		      return ("PermissionError");
		}
	
		Connection db = null;
	    
		//recupero l'asl
		int asl= Integer.valueOf(context.getRequest().getParameter("aslRif"));
		//recupero dell' id del progetto
		int id=Integer.valueOf(context.getRequest().getParameter("selectProgetto"));
		
		ListaCani listaCani;
		try{
		
			  db = this.getConnection(context);
		  
			  listaCani= new ListaCani();
			  listaCani.getListaReport(db, asl,id);
		 	
		}
		catch (Exception e) {
		      e.printStackTrace();
		      context.getRequest().setAttribute("Error", e);
		      return ("SystemError");
		}
		finally{
			 this.freeConnection(context, db);
		      System.gc();
		}
		context.getRequest().setAttribute("listaCani", listaCani);
		return "estrazioneOk";
	}
	
}
