/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.noscia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.gestioneanagrafica.base.OggettoPerStorico;
import org.aspcfs.modules.opu.actions.StabilimentoAction;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.ricercaunica.base.RicercaList;
import org.aspcfs.modules.ricercaunica.base.RicercaOpu;
import org.aspcfs.modules.variazionestati.base.LineaVariazione;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;

public class GisaNoScia extends CFSModule {

    public String executeCommandDefault(ActionContext context) {
        Connection db = null;

        try{
        	 db = this.getConnection(context);
        	 String sql = "select * from public.get_linea_attivita_noscia()";
         	 PreparedStatement st = db.prepareStatement(sql);
             ResultSet rs = st.executeQuery();
             ArrayList<LineaAttivita> linee = new ArrayList<LineaAttivita>();
             
             while(rs.next()){
            	 LineaAttivita linea = new LineaAttivita(rs.getString("path_descrizione"), rs.getString("codice_attivita"));
            	 linee.add(linea);
             }
             context.getRequest().setAttribute("listLinee", linee);
             
        }catch (SQLException e) {
        	e.printStackTrace();
        } finally {
            this.freeConnection(context, db);
        }
      
       
        return "DefaultOK";

    }

    public String executeCommandChoose(ActionContext context){

        Connection db = null;
        
        try{
	        db = this.getConnection(context);
	
	        String codice = context.getRequest().getParameter("codice_univoco_ml");
	
	        context.getRequest().setAttribute("codiceLinea", codice);
	        
	    	String sql = "select * from public.get_linea_attivita_noscia() where codice_attivita = ?";
	    	PreparedStatement st = db.prepareStatement(sql);
	        st.setString(1, codice);
	        ResultSet rs = st.executeQuery();
	        
	        String desc_linea = "";
	        
	        while(rs.next()){
	        	desc_linea = rs.getString("path_descrizione");
	        }
	        LineaAttivita linea = new LineaAttivita(desc_linea, codice);
	        //cerco tutti i componenti della GUI della linea di attivita
	        linea.cercaMetadati(db); 
	        List<Metadato> listaElementi = new ArrayList<Metadato>();
	        listaElementi = linea.getMetadati();
	        context.getRequest().setAttribute("lineaattivita", listaElementi);
	        context.getRequest().setAttribute("id_asl_stab", getUserSiteId(context));
        }catch (SQLException e) {
        	e.printStackTrace();
        } finally {
            this.freeConnection(context, db);
        }
      
        
        return "ChooseOK_0";
    }

    public String executeCommandInsert(ActionContext context) throws NumberFormatException, IndirizzoNotFoundException{

        Connection db = null;
        final Logger log = Logger.getLogger(GisaNoScia.class);
        
        Map<String, String[]> parameterMap = context.getRequest().getParameterMap();
        //parameterMap.get("partita_iva"); esempio di accesso
        
        Map<String, String> campiFissi = new HashMap<String, String>();
        Map<String, String> campiEstesi = new HashMap<String, String>();
        String valore_campo;
        String chiave_campo;
        for (String key: parameterMap.keySet()){
        	
        	if (parameterMap.get(key).length<=1)
        	   	valore_campo = parameterMap.get(key)[0].trim();
        	else {
        		valore_campo = "";
        		for (int i = 0; i<parameterMap.get(key).length; i++)
        			if (parameterMap.get(key)[i]!=null && !parameterMap.get(key)[i].trim().equals(""))
        				valore_campo += parameterMap.get(key)[i].trim()+",";
        		if (valore_campo.endsWith(","))
        			valore_campo = valore_campo.substring(0, valore_campo.length()-1);
        	}
        	chiave_campo = key.trim();
        	if (chiave_campo.startsWith("_b_"))
        	{
        		if(valore_campo.equalsIgnoreCase("")){
        			valore_campo = null;
        		}
        		campiFissi.put(chiave_campo.replaceFirst("_b_", ""), valore_campo);
        	}
        	else if (chiave_campo.startsWith("_x_"))
        	{
        		if(valore_campo.equalsIgnoreCase("")){
        			valore_campo = null;
        		}
        		campiEstesi.put(chiave_campo.replaceFirst("_x_", ""), valore_campo);
        	}
        	
        }
        
        System.out.println("\n");
        
        int userId =  getUserId(context);
        System.out.println("userid: " + userId);
        
        try{
	        db = this.getConnection(context);
	        String sql = "";
	        PreparedStatement st = null;
	        ResultSet rs = null;
	        	
        	//chiamare dbi insert no scia e passare (campiFissi, campiEstesi, userId)
	        sql = "select * from public.insert_noscia(?,?,?)";
	    	st = db.prepareStatement(sql);
	    	st.setObject(1, campiFissi);
	    	st.setObject(2, campiEstesi);
	        st.setInt(3, userId);
	        
	        System.out.println(st);
	        
	        rs = st.executeQuery();
	        
	        String id_stabilimento = "";
	        
	        while(rs.next()){
	        	id_stabilimento = rs.getString("insert_noscia");
	        }
	        
	        OggettoPerStorico stab_pre_modifica = new OggettoPerStorico();
	        //OggettoPerStorico stab_pre_modifica = new OggettoPerStorico(Integer.parseInt(id_stabilimento), db);
	        stab_pre_modifica.salvaStoricoEvento(db,Integer.parseInt(id_stabilimento),null);
	        
	        System.out.println("stabilimento inserito: " + id_stabilimento);	        
	        context.getRequest().setAttribute("id_stabilimento", id_stabilimento);
	      
	        return "DetailsNosciaOK";
	        
	        
	        
        }catch (SQLException e) {
        	e.printStackTrace();
        	context.getRequest().setAttribute("erroreNoScia", 
        			"Errore inserimento linea no scia: contattare hd I livello!!!<br>" + e.toString());
            return "ErrorPageNoscia";
        } finally {
            this.freeConnection(context, db);
        }  

    }
    
    public String executeCommandSchedacessazione(ActionContext context) throws IndirizzoNotFoundException{
    	
    	int altId = -1;
    	int stabId = -1;
    	
    	try {altId = Integer.parseInt(context.getRequest().getParameter("altId"));} catch (Exception e){}
    	context.getRequest().setAttribute("altId", String.valueOf(altId));
    	
    	try {stabId = Integer.parseInt(context.getRequest().getParameter("stabId"));} catch (Exception e){}
    	context.getRequest().setAttribute("stabId", String.valueOf(stabId));
    	
    	Connection db = null;  
        
        try{
            db = this.getConnection(context);
            org.aspcfs.modules.opu.base.Stabilimento stab = null;
            
            if (stabId>0)
            	stab = new org.aspcfs.modules.opu.base.Stabilimento(db, stabId);
            else if (altId>0)
            	stab = new org.aspcfs.modules.opu.base.Stabilimento(db, altId, true);
            
    		context.getRequest().setAttribute("StabilimentoDettaglio", stab);
    		context.getRequest().setAttribute("idAsl", String.valueOf(stab.getIdAsl()));
    		LookupList statoLab = new LookupList(db, "lookup_stato_lab");
    		context.getRequest().setAttribute("ListaStati", statoLab);
         
        }catch (SQLException e) {
        	e.printStackTrace();
        } finally {
            this.freeConnection(context, db);
        }

        return "Cessazione"; 
    }
    
    public String executeCommandCessazione(ActionContext context) throws IndirizzoNotFoundException{
    	
    	int altId = -1;
    	int stabId = -1;
    	
    	try {altId = Integer.parseInt(context.getRequest().getParameter("altId"));} catch (Exception e){}
    	context.getRequest().setAttribute("altId", String.valueOf(altId));
    	
    	try {stabId = Integer.parseInt(context.getRequest().getParameter("stabId"));} catch (Exception e){}
    	context.getRequest().setAttribute("stabId", String.valueOf(stabId));
    	
    	Map<String, String[]> parameterMap = context.getRequest().getParameterMap();
        //parameterMap.get("partita_iva"); esempio di accesso

    	Map<String, String> campiFissi = new HashMap<String, String>();
    	Map<String, String> campiEstesi = new HashMap<String, String>();
    	String valore_campo;
    	String chiave_campo;
    	
    	if(parameterMap.get("cessa_stabilimento") != null){
    		for (String key: parameterMap.keySet()){
    			valore_campo = parameterMap.get(key)[0].trim();
    			chiave_campo = key.trim();
    			if (chiave_campo.startsWith("id_rel_stab_lp_"))
    			{
    				campiFissi.put(chiave_campo, valore_campo);
    			}
    			else if (chiave_campo.startsWith("data_cessazione_"))
    			{
    			
    				valore_campo = parameterMap.get("data_cessazione")[0].trim();
    				campiFissi.put(chiave_campo, valore_campo);
    			}
    			else if (chiave_campo.startsWith("cessa_linea_note_"))
    			{
    				valore_campo = parameterMap.get("cessa_stabilimento_note")[0].trim();
    				campiFissi.put(chiave_campo, valore_campo);
    			}
    			
    		}
    	}else{
    		for (String key: parameterMap.keySet()){
    			valore_campo = parameterMap.get(key)[0].trim();
    			chiave_campo = key.trim();
    			if (chiave_campo.startsWith("id_rel_stab_lp_"))
    			{
    				campiFissi.put(chiave_campo, valore_campo);
    			}
    			else if (chiave_campo.startsWith("data_cessazione_"))
    			{
    				if(valore_campo.equalsIgnoreCase("")){
    					valore_campo = null;
    				}
    				campiFissi.put(chiave_campo, valore_campo);
    			}
    			else if (chiave_campo.startsWith("cessa_linea_note_"))
    			{
    				campiFissi.put(chiave_campo, valore_campo);
    			}
    			
    		}
    	}


    	Connection db = null;
        
        try{
            db = this.getConnection(context);

        	String sql = "select * from public.cessazione_no_scia(?, ?, ?, ?)";
        	PreparedStatement st = db.prepareStatement(sql);
        	st.setObject(1, campiFissi);
        	st.setObject(2, campiEstesi);
            st.setInt(3, getUserId(context));
            st.setInt(4, altId);
            System.out.println(st);
      
            st.executeQuery();
            
            context.getRequest().setAttribute("id_stabilimento", stabId);
            return "DetailsNosciaOK"; 

        }catch (SQLException e) {
        	e.printStackTrace();
        	context.getRequest().setAttribute("erroreNoScia", 
    				"Errore in fase di cessazione: contattare hd I livello!!!<br>" + e.toString());
    		return "ErrorPageNoscia";
        } finally {
            this.freeConnection(context, db);
        }
         
    }   
    
    public String executeCommandSearchForm(ActionContext context) {
		if (!(hasPermission(context, "gestione_noscia-view"))) {
			return ("PermissionError");
		}

		//Bypass search form for portal users
		if (isPortalUser(context)) {
			return (executeCommandSearch(context));
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = getConnection(context);
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "--Tutti--");
			siteList.removeElementByCode(16);
			context.getRequest().setAttribute("SiteList", siteList);
		    //mostra linee di attivita filtro di ricerca
		     String sql = "select * from public.get_linea_attivita_noscia()";
         	 PreparedStatement st = db.prepareStatement(sql);
             ResultSet rs = st.executeQuery();
             ArrayList<LineaAttivita> linee = new ArrayList<LineaAttivita>();
             
             while(rs.next()){
            	 LineaAttivita linea = new LineaAttivita(rs.getString("path_descrizione"), rs.getString("codice_attivita"));
            	 linee.add(linea);
             }
             context.getRequest().setAttribute("listLinee", linee);
			
			this.deletePagedListInfo(context, "SearchOpuListInfo");
			//reset the offset and current letter of the paged list in order to make sure we search ALL accounts
			PagedListInfo orgListInfo = this.getPagedListInfo(context, "SearchOpuListInfo");
			orgListInfo.setCurrentLetter("");
			orgListInfo.setCurrentOffset(0);

			

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Search Accounts", "Accounts Search");
		return ("SearchOK");
	}

    public String executeCommandSearch(ActionContext context) {
		if (!hasPermission(context, "gestione_noscia-view")) {
			return ("PermissionError");
		}


		RicercaList organizationList = new RicercaList();
		//Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context, "SearchOpuListInfo");
		searchListInfo.setLink("GisaNoScia.do?command=Search");

		Connection db = null;
		try {
			db = this.getConnection(context);	      

			searchListInfo.setSearchCriteria(organizationList, context);     
			organizationList.setPagedListInfo(searchListInfo);
			//	organizationList.setEscludiInDomanda(true);
			//	organizationList.setEscludiRespinti(true);
			organizationList.setIdAsl(context.getRequest().getParameter("searchaslSedeProduttiva"));
			organizationList.buildListNoScia(db);


			//organizationList.setCodiceFiscale(context.getParameter("searchCodiceFiscale"));

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "--Tutti--");
			siteList.removeElementByCode(16);
			context.getRequest().setAttribute("SiteList", siteList);
			

			context.getRequest().setAttribute("StabilimentiList", organizationList);

			return "ListOK";


		} catch (Exception e) {
			//Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}



	}
    
    

}
