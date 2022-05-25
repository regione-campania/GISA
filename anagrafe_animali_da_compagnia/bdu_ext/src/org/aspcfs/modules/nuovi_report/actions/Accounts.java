/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.nuovi_report.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.Hashtable;

import javax.servlet.http.HttpServletResponse;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.nuovi_report.base.Filtro;
import org.aspcfs.modules.nuovi_report.util.StampaPdf;

import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;

public class Accounts extends CFSModule{

	public String executeCommandDefault(ActionContext context)
	{
		if (!hasPermission(context, "nuovi-report-view")) {
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
		if (!hasPermission(context, "cani-anagrafati-view")) {
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
	
	
	//costruisce la lookup dei cani anagrafati per il primo report
	public String executeCommandFiltroAnagrafatiRev2(ActionContext context) {

		//controllo sui permessi
		if (!hasPermission(context, "cani-anagrafati_rev2-view")) {
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
		return "filtro2";
	}
	
	//Recupera i dati dalla form e setta il bean con i dati relativi ai cani anagrafati
	public String executeCommandSearchList(ActionContext context) {
		if (!hasPermission(context, "cani-anagrafati-view")) {
			    return ("PermissionError");
	      }
		  
	    // UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
	       
	      Connection db = null;
	      try {
	        db = this.getConnection(context);
	    	String descrizioneAsl = "";
	    	String da             = null;
	    	String al             = null;
	    	Connection conn       = null;
	    	String vivo=null;
	    	String reg			  = null; 
			boolean check=false;
			//inizializzazione della connessione al db
			conn = this.getConnection(context);
	
			buildFormElements(context, conn);
			
			//instanzia il bean
			Filtro f = new Filtro();
			
			HttpServletResponse res = context.getResponse();
			
			//recupero dei dati di input
			String x=context.getRequest().getParameter("regione");
			if (x!=null)
				check=true;
			else 
				check=false;
			f.setRegione(check);
			if (check==false){
			
				String asl = context.getParameter("aslRif");
				f.setId_asl(Integer.parseInt(asl));
				if(!(f.getId_asl()==-1)){
								descrizioneAsl = f.calcoloASL(conn);
								f.setAsl(descrizioneAsl);
				}
				else {
						f.setAsl("TUTTE");
				}
			}
			else
			f.setAsl("FUORI REGIONE");
			vivo=context.getRequest().getParameter("reportType");
			reg=context.getRequest().getParameter("reportDetails");
			da = context.getRequest().getParameter("date_start");
			al = context.getRequest().getParameter("date_end");
			
			f.setEntered(da);
			f.setFine(al);
			f.setStato(vivo);
			
			f.setDetails(reg);
			
			//esecuzione della query
			ResultSet rs = f.queryRecord2(conn);
			
			if (rs.next())
		    		f.setTotCani(rs.getInt(1));
				
			StampaPdf s = new StampaPdf(context,f);
			s.stampaChip(context, f);
			
	      } catch (Exception e) {
	        context.getRequest().setAttribute("Error", e);
	        return ("SystemError");
	      } finally {
	        this.freeConnection(context, db);
	      }
	      return getReturn(context, "file");
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
	   if(thisUser.getSiteId()==0){
		   for(int i=201;i<=207;i++){
			   		ArrayList<String> tmp=new ArrayList<String>();
		    		PreparedStatement pst2=db.prepareStatement(qry2);
		      		pst2.setInt(1,i);
		      		ResultSet rs2=pst2.executeQuery();
	    	
		      		while(rs2.next()){
		      				//tmp.add("'"+rs2.getString("comune").replaceAll("'", "")+"'");
		      				tmp.add("\""+rs2.getString("comune")+"\"");
		    		      	
		      		}
		      		String tempo="[";
		      		for(String cc:tmp){
		      				tempo=tempo+cc+",";
	    		
		      		}
		      		tempo=tempo.substring(0, tempo.length()-1)+"]";
		      		hashtable.put(""+i,tmp);
		  }
	    }
	   else{
		   if(thisUser.getSiteId()>0){
			   
			  ArrayList<String> tmp2=new ArrayList<String>();
			   PreparedStatement pst2=db.prepareStatement(qry2);
	      		pst2.setInt(1,thisUser.getSiteId());
	      		ResultSet rs2=pst2.executeQuery();
	      		int i=0;
	      		while(rs2.next()){
      				//tmp2.add("'"+rs2.getString("comune").replaceAll("'", "")+"'");
	      			  tmp2.add("\""+rs2.getString("comune")+"\"");
		      		
      				i++;
      				
      			}
	      		context.getRequest().setAttribute("lista", tmp2);
	    	   }
		
	   }
	    context.getRequest().setAttribute("hashtable", hashtable);
	}

	
		/*Cattura*/
	public String executeCommandCattura(ActionContext context) {

		Connection db = null;

		try {
			db = getConnection(context);
			buildFormElements(context, db);
			
		} catch (Exception e) {	
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("catturato");
	}
	
	
	
	//Genera la lista degli impianti trovati tramite la funzione di ricerca
	public String executeCommandCatturati(ActionContext context) {
		if (!hasPermission(context, "cani-catturati-view")) {
			    return ("PermissionError");
	      }
		  
	      String parentId = context.getRequest().getParameter("parentId");
          AssetList assetList = new AssetList();
	      
	      UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
	       
	      //Prepare pagedListInfo
	      PagedListInfo assetListInfo = this.getPagedListInfo(context, "AssetListInfo");
	      Connection db = null;
	      try {
	        db = this.getConnection(context);
	    	String descrizioneAsl = "";
	    	Connection conn       = null;
	    	
			//inizializzazione della connessione al db
			conn = this.getConnection(context);
			

			//buildFormElements(context, conn);
			
			//instanzia il bean
			Filtro f = new Filtro();
			
			HttpServletResponse res = context.getResponse();
			SystemStatus thisSystem = this.getSystemStatus(context);
			
			LookupList aslList = new LookupList(db, "lookup_asl_rif");
		    aslList.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));
		    context.getRequest().setAttribute("aslRifList", aslList);
		  
			//recupero dei dati di input
		    //String asl = context.getParameter("aslRif");
			String asl = context.getParameter("aslRif");
			f.setId_asl(Integer.parseInt(asl));
			descrizioneAsl = f.calcoloASL(conn);
			f.setAsl(descrizioneAsl);
			String 	da = context.getRequest().getParameter("date_start");
			f.setEntered(da);
			String 	al = context.getRequest().getParameter("date_end");
			f.setFine(al);
			String vivo=context.getRequest().getParameter("reportType");
			f.setStato(vivo);
			String tmp=context.getRequest().getParameter("prova");
			if(!(tmp==null)){
				if(tmp.equals("--Nessuno--")){
					tmp=null;
				}
				else f.setComuneCattura(tmp);
			}
			//esecuzione della query
			ResultSet rs = f.queryRecord5(conn);
			
			if (rs.next())
				
		    		f.setTotCaniCatt(rs.getInt(1));
				
			StampaPdf s = new StampaPdf(context,f);
			s.stampaCatt(context, f);
			
		  } catch (Exception e) {
	        context.getRequest().setAttribute("Error", e);
	        return ("SystemError");
	      } finally {
	        this.freeConnection(context, db);
	      }
	      return "-none-";
	      //getReturn(context, "AssetsSearchList");
	}
	
	public String executeCommandSterilizza(ActionContext context) {

		if (!hasPermission(context, "cani-sterilizzati-view")) {
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
	
	
	//Genera la lista degli impianti trovati tramite la funzione di ricerca
	public String executeCommandSterilizzati(ActionContext context) {
		if (!hasPermission(context, "cani-sterilizzati-view")) {
			    return ("PermissionError");
	      }
		  
	      Connection db = null;
	      try {
	        db = this.getConnection(context);
	    	String descrizioneAsl = "";
	    	//inizializzazione della connessione al db
			db = this.getConnection(context);
			String cattura;
			String stato;
			String contributo;
			buildFormElements(context, db);
			
			//instanzia il bean
			Filtro f = new Filtro();
			
			HttpServletResponse res = context.getResponse();
			
			//recupero dei dati di input
		    String asl = context.getParameter("aslRif");
		    f.setId_asl(Integer.parseInt(asl));
			descrizioneAsl = f.calcoloASL(db);
			f.setAsl(descrizioneAsl);
			String 	da = context.getRequest().getParameter("date_start");
			f.setEntered(da);
			String 	al = context.getRequest().getParameter("date_end");
			f.setFine(al);
			cattura=context.getRequest().getParameter("reportCattura");
			f.setCattura(cattura);
			stato=context.getRequest().getParameter("reportType");
			
			f.setStatoC(stato);
			contributo = context.getRequest().getParameter("reportContributo");
			f.setContributo(contributo);
			//esecuzione della query
			ResultSet rs = f.queryRecord6(db);
			
			if (rs.next()){
					f.setTotCaniSter(rs.getInt(2));
					f.setTotCaniSterCatt(rs.getInt(3));
					f.setTotCaniSterReimmessi(rs.getInt(4));
					f.setTotCaniSterAdottati(rs.getInt(5));
				}
			StampaPdf s = new StampaPdf(context,f);
			s.stampaSter(context, f);
			
		  } catch (Exception e) {
	        context.getRequest().setAttribute("Error", e);
	        return ("SystemError");
	      } finally {
	        this.freeConnection(context, db);
	      }
	      return getReturn(context, "AssetsSearchList");
	}
	
	public String executeCommandCessione(ActionContext context) {

		if (!hasPermission(context, "cessioni-view")) {
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
		return "cessione_avvenuta";
	}
	public String executeCommandRestituzione(ActionContext context) {

		if (!hasPermission(context, "cani-restituiti-view")) {
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
		return "restituito";
	}
	
	public String executeCommandRestituiti(ActionContext context) {
		if (!hasPermission(context, "cani-restituiti-view")) {
		    return ("PermissionError");
      }
	    Connection db = null;
	      try {
	    	   db = this.getConnection(context);
	    	   String descrizioneAsl = "";
		    	String da             = null;
		    	String al             = null;
		    	String comune 		  = null;
		    	db = this.getConnection(context);
		    	
				buildFormElements(context, db);
				
				//instanzia il bean
				Filtro f = new Filtro();
				
				HttpServletResponse res = context.getResponse();
				String asl = context.getParameter("aslRif");
				f.setId_asl(Integer.parseInt(asl));
				if(!(f.getId_asl()==-1)){
					descrizioneAsl = f.calcoloASL(db);
					f.setAsl(descrizioneAsl);
				}
				else {
					f.setAsl("TUTTE");
				}
				da = context.getRequest().getParameter("date_start");
				al = context.getRequest().getParameter("date_end");
				comune= context.getParameter("prova");
				if (!(comune==null)){
					if (comune.equals("--Nessuno--")){
						comune=null;
					}
					else f.setComuneProprietario(comune);
				}
				f.setEntered(da);
				f.setFine(al);
				//f.setComuneProprietario(comune);
				
				ResultSet rs = f.queryRecord8(db);
				if (rs.next())
		    		f.setTotCani(rs.getInt(1));
				
			StampaPdf s = new StampaPdf(context,f);
			s.stampaRes(context, f);
		
	      } catch (Exception e) {
        context.getRequest().setAttribute("Error", e);
        return ("SystemError");
      } finally {
        this.freeConnection(context, db);
      }
      return getReturn(context, "file");
	
	}
	public String executeCommandSmarrimento(ActionContext context) {

		if (!hasPermission(context, "cani-smarriti-view")) {
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
		return "smarrito";
	}
	
	public String executeCommandSmarriti(ActionContext context) {
		if (!hasPermission(context, "cani-smarriti-view")) {
			    return ("PermissionError");
	      }
		  
	      Connection db = null;
	      try {
	        db = this.getConnection(context);
	    	String descrizioneAsl = "";
	    	
			//inizializzazione della connessione al db
			db = this.getConnection(context);
	
			buildFormElements(context, db);
			
			//instanzia il bean
			Filtro f = new Filtro();
			
			HttpServletResponse res = context.getResponse();
			
			//recupero dei dati di input
			
		    String asl = context.getParameter("aslRif");
		    f.setId_asl(Integer.parseInt(asl));
			descrizioneAsl = f.calcoloASL(db);
			f.setAsl(descrizioneAsl);
			String 	da = context.getRequest().getParameter("date_start");
			f.setEntered(da);
			String 	al = context.getRequest().getParameter("date_end");
			f.setFine(al);
			//ResultSet rs = f.queryRecord6(db);
			
			//if (rs.next())
				
		    	//	f.setTotCaniSmar(rs.getInt(1));
				
			StampaPdf s = new StampaPdf(context,f);
			s.stampaSmar(context, f);
			
		  } catch (Exception e) {
	        context.getRequest().setAttribute("Error", e);
	        return ("SystemError");
	      } finally {
	        this.freeConnection(context, db);
	      }
	      return getReturn(context, "AssetsSearchList");
	}
	
	public String executeCommandStampaCessioni(ActionContext context) {
		if (!hasPermission(context, "cessioni-view")) {
			    return ("PermissionError");
	      }
		  
	      String parentId = context.getRequest().getParameter("parentId");
          AssetList assetList = new AssetList();
	      
	      UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
	       
	      //Prepare pagedListInfo
	      PagedListInfo assetListInfo = this.getPagedListInfo(context, "AssetListInfo");
	      Connection db = null;
	      try {
	        db = this.getConnection(context);
	    	String descrizioneAsl = "";
	    	Connection conn       = null;
	    	
			//inizializzazione della connessione al db
			conn = this.getConnection(context);
	
			buildFormElements(context, conn);
			
			//instanzia il bean
			Filtro f = new Filtro();
			
			HttpServletResponse res = context.getResponse();
			
			//recupero dei dati di input
		    String asl = context.getParameter("aslRif");
		    f.setId_asl(Integer.parseInt(asl));
			descrizioneAsl = f.calcoloASL(conn);
			f.setAsl(descrizioneAsl);
			String 	da = context.getRequest().getParameter("date_start");
			f.setEntered(da);
			String 	al = context.getRequest().getParameter("date_end");
			f.setFine(al);
			String vivo=context.getRequest().getParameter("reportType");
			f.setStato(vivo);
			//esecuzione della query
			ResultSet rs = f.queryRecord7(conn);
			
			if (rs.next())
				
		    		f.setTotCessioni(rs.getInt(1));
				
			StampaPdf s = new StampaPdf(context,f);
			s.stampaCessioni(context, f);
			
		  } catch (Exception e) {
	        context.getRequest().setAttribute("Error", e);
	        return ("SystemError");
	      } finally {
	        this.freeConnection(context, db);
	      }
	      return getReturn(context, "AssetsSearchList");
	}
	
	}
	