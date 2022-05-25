/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.nosciagins;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.gestioneanagrafica.base.OggettoPerStorico;
import org.aspcfs.modules.opu.base.GestoreComunicazioniBdu;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.suap.actions.InterfValidazioneRichieste;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class GisaNoSciaGINS extends CFSModule {
	
	public static final int OP_NUOVO_STAB = 1; 
	public static final int OP_AMPLIAMENTO = 2;
	public static final int OP_CESSAZIONE=3;
	public static final int OP_VARIAZIONE = 4;
	public static final int OP_SOSPENSIONE = 5;
	public static final int OP_MODIFICA_STATO_LUOGHI = 6;
	public static final int OP_MODIFICA_TRASFERIMENTO_SEDE = 7;
	public static final int OP_MODIFICA_RIATTIVAZIONE_LINEE_SOSPESE = 8;
	public static final int OP_VARIAZIONI_INFORMAZIONI_SCHEDE_SUPPLEMENTARI = 9;
	public static final int OP_AMPLIAMENTO_LUOGHI = 10;
	public static final int OP_TRASFORMAZIONE = 11;
	public static final int OP_ERRATA_CORRIGE_MOD_ANAG = 12;
	public static final int OP_ERRATA_CORRIGE_MOD_LINEE = 13;
	public static final int OP_AGGIUNGI_LINEA_PREGRESSA = 14;

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

    public String executeCommandInsert(ActionContext context) throws Exception{

        Connection db = null;
        final Logger log = Logger.getLogger(GisaNoSciaGINS.class);
        
        Map<String, String[]> parameterMap = context.getRequest().getParameterMap();
        //parameterMap.get("partita_iva"); esempio di accesso
        
        Map<String, String> campiFissi = new HashMap<String, String>();
        Map<String, String> campiEstesi = new HashMap<String, String>();
        String valore_campo;
        String chiave_campo;
        String numeroPratica = "";
		int idTipologiaPratica = -1;
		int idComunePratica = -1;
        
        for (String key: parameterMap.keySet()){
        	valore_campo = parameterMap.get(key)[0].trim();
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
        	else if (chiave_campo.equalsIgnoreCase("numeroPratica"))
			{
				if(valore_campo.equalsIgnoreCase("")){
					valore_campo = null;
				}
				numeroPratica = valore_campo;
			}
			else if (chiave_campo.equalsIgnoreCase("idTipologiaPratica"))
			{
				if(valore_campo.equalsIgnoreCase("")){
					valore_campo = "-1";
				}
				idTipologiaPratica = Integer.parseInt(valore_campo);
			}
			
			else if (chiave_campo.equalsIgnoreCase("idComunePratica"))
			{
				if(valore_campo.equalsIgnoreCase("")){
					valore_campo = "-1";
				}
				idComunePratica = Integer.parseInt(valore_campo);
			}
        	
        }
        
        int userId =  getUserId(context);
        System.out.println("userid: " + userId);
        
        try{
	        db = this.getConnection(context);
	        String sql = "";
	        PreparedStatement st = null;
	        ResultSet rs = null;
	       	
        	//chiamare dbi insert no scia gins e 
	        //passare (campiFissi, campiEstesi, userId, idTipologiaPratica, numeroPratica, idComunePratica)
	        sql = "select * from public.insert_noscia_gins(?,?,?,?,?,?)";
	    	st = db.prepareStatement(sql);
	    	st.setObject(1, campiFissi);
	    	st.setObject(2, campiEstesi);
	        st.setInt(3, userId);
	        st.setInt(4, idTipologiaPratica);
	        st.setString(5, numeroPratica);
	        st.setInt(6, idComunePratica);
	        
	        System.out.println(st);
	        
	        rs = st.executeQuery();
	        
	        String id_stabilimento = "";
	        
	        while(rs.next()){
	        	id_stabilimento = rs.getString("insert_noscia_gins");
	        	org.aspcfs.modules.opu.base.LineaProduttivaList listaLineeProduttive = new org.aspcfs.modules.opu.base.LineaProduttivaList();
	        	listaLineeProduttive.setIdStabilimento(Integer.parseInt(id_stabilimento));
				listaLineeProduttive.buildList(db);
	        	
	        	InterfValidazioneRichieste interf = new InterfValidazioneRichieste();
	        	interf.scaricaCampiAggiuntiviPerTutteLeLineeRichiestaValidata(userId, Integer.parseInt(id_stabilimento), null, db, listaLineeProduttive);
	       }
	       
	        //Propagazione
	        if (idTipologiaPratica==OP_NUOVO_STAB || idTipologiaPratica==OP_AMPLIAMENTO){
	        	GestoreComunicazioniBdu gBdu = new GestoreComunicazioniBdu();
	        	gBdu.inserisciNuovaSciaBdu(Integer.parseInt(id_stabilimento));
	        }
	        
	        salvaStoricoEvento(db,Integer.parseInt(id_stabilimento),null);
	        
	        
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
    
    private void salvaStoricoEvento(Connection db, int stabId, OggettoPerStorico stab_pre_modifica) throws SQLException, IndirizzoNotFoundException{
    	
    	//recupero altId OSA
    	String sql = "select alt_id from opu_stabilimento where id = ?";
    	PreparedStatement st = db.prepareStatement(sql);
        st.setInt(1, stabId);
        ResultSet rs = st.executeQuery();
        int altId = -1;
    	while (rs.next()){
    		altId = rs.getInt("alt_id");
    	}
    	OggettoPerStorico stab_post_modifica = new OggettoPerStorico(altId, db);
    	
    	//recupero ultimo evento sull OSA 
    	sql = "select id from eventi_su_osa where alt_id = ? order by entered desc limit 1";
    	st = db.prepareStatement(sql);
    	st.setInt(1, altId);
    	rs = st.executeQuery();
    	int id_evento = -1;
    	while (rs.next()){
    		id_evento = rs.getInt("id");
    	}
    	
    	//salvo lo stato dell OSA prima e dopo l evento effettuato
    	sql = "update eventi_su_osa set pre_evento = ?, post_evento = ? where id =? ";
    	st = db.prepareStatement(sql);
    	if(stab_pre_modifica == null){
    		st.setBytes(1, stab_post_modifica.oggettoToStream());
    	} else {
    		st.setBytes(1, stab_pre_modifica.oggettoToStream());
    	}
    	st.setBytes(2, stab_post_modifica.oggettoToStream());
    	st.setInt(3, id_evento);
    	st.execute();
    	
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

}
