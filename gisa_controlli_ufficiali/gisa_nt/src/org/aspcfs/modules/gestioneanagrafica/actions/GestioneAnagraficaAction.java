/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneanagrafica.actions;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.batik.css.engine.value.StringValue;
import org.apache.log4j.Logger;
import org.aspcf.modules.controlliufficiali.base.Organization;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.actions.Accounts;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.diffida.base.Ticket;
import org.aspcfs.modules.distributori.base.Distrubutore;
import org.aspcfs.modules.gestioneDocumenti.actions.GestioneAllegatiGins;
import org.aspcfs.modules.gestioneanagrafica.base.Asl;
import org.aspcfs.modules.gestioneanagrafica.base.Comune;
import org.aspcfs.modules.gestioneanagrafica.base.EsitoOperazione;
import org.aspcfs.modules.gestioneanagrafica.base.LdaImportParafarmacie;
import org.aspcfs.modules.gestioneanagrafica.base.LineaVariazione;
import org.aspcfs.modules.gestioneanagrafica.base.OggettoPerStorico;
import org.aspcfs.modules.gestioneanagrafica.base.OperazioneSuOsa;
import org.aspcfs.modules.gestioneml.base.LivelloAggiuntivo;
import org.aspcfs.modules.gestionepratiche.base.*;
import org.aspcfs.modules.lineeattivita.base.LineeAttivita;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.noscia.GisaNoScia;
import org.aspcfs.modules.noscia.LineaAttivita;
import org.aspcfs.modules.noscia.Metadato;
import org.aspcfs.modules.opu.actions.StabilimentoAction;
import org.aspcfs.modules.opu.base.GestoreComunicazioniBdu;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.LineeMobiliHtmlFields;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.RicercheAnagraficheTab;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.opu.base.Storico;
import org.aspcfs.modules.opu.util.StabilimentoImportUtil;
import org.aspcfs.modules.suap.actions.InterfValidazioneRichieste;
import org.aspcfs.modules.suap.base.LineaProduttivaCampoEsteso;
import org.aspcfs.modules.suap.base.PecMailSender;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.InvalidFileException;
import org.aspcfs.utils.InvioMassivoDistributori;
import org.aspcfs.utils.LeggiFile;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.PopolaCombo;

import com.daffodilwoods.daffodildb.server.serversystem.User;
import com.darkhorseventures.framework.actions.ActionContext;
import com.oreilly.servlet.MultipartRequest;

public class GestioneAnagraficaAction extends CFSModule {
	
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
	public static final int OP_ASSOCIA_PRATICA_SUAP = 15;
	public static final int OP_AGGIORNA_DATI_EX_IMPORT = 16;
	public static final int OP_VARIAZIONE_SEDE_LEGALE = 17;
	public static final int OP_CONVERGENZA = 18;
	

public String executeCommandGestioneGisaDellaPratica(ActionContext context) throws IndirizzoNotFoundException{
	
	String numeroPratica = "";
	int tipoPratica = -1;
	String dataPratica = "";
	int comunePratica = -1;
	String descTipoPratica = "";
	String id_causale = "-1";
	String altId = "-1";
	
	try {tipoPratica =  Integer.parseInt(context.getParameter("tipoPratica").toString());} catch (Exception e){}
	context.getRequest().setAttribute("tipoPratica", String.valueOf(tipoPratica));
	
	try {dataPratica = context.getParameter("dataPratica").toString();} catch (Exception e){}
	context.getRequest().setAttribute("dataPratica", dataPratica);
	
	try {numeroPratica = context.getParameter("numeroPratica").toString();} catch (Exception e){}
	context.getRequest().setAttribute("numeroPratica", numeroPratica);
	
	try {comunePratica =  Integer.parseInt(context.getParameter("comunePratica").toString());} catch (Exception e){}
	context.getRequest().setAttribute("comunePratica", String.valueOf(comunePratica));
	
	try {id_causale = context.getRequest().getParameter("causalePratica");} catch (Exception e){}
    context.getRequest().setAttribute("id_causale", id_causale);
	
    try {altId = context.getRequest().getParameter("altId");} catch (Exception e){}
    context.getRequest().setAttribute("altId", altId);
    
    /*
	if (tipoPratica == GestioneAnagraficaAction.OP_NUOVO_STAB)
		descTipoPratica="AVVIO DELL'ATTIVITA'"; 
	else if (tipoPratica == GestioneAnagraficaAction.OP_AMPLIAMENTO)
		descTipoPratica="AGGIUNZIONE LINEA D'ATTIVITA";
	else if (tipoPratica == GestioneAnagraficaAction.OP_CESSAZIONE)
		descTipoPratica="CESSAZIONE (LINEE O INTERO STABILIMENTO)";
	else if (tipoPratica == GestioneAnagraficaAction.OP_VARIAZIONE)
		descTipoPratica="SUBINGRESSO";
	else if (tipoPratica == GestioneAnagraficaAction.OP_SOSPENSIONE)
		descTipoPratica="SOSPENSIONE TEMPORANEA";
	else if (tipoPratica == GestioneAnagraficaAction.OP_MODIFICA_STATO_LUOGHI)
		descTipoPratica="VARIAZIONI SIGNIFICATIVE ALLO STATO DEI LUOGHI";
	else if (tipoPratica == GestioneAnagraficaAction.OP_MODIFICA_TRASFERIMENTO_SEDE)
		descTipoPratica="TRASFERIMENTO DI SEDE";
	else if (tipoPratica == GestioneAnagraficaAction.OP_MODIFICA_RIATTIVAZIONE_LINEE_SOSPESE)
		descTipoPratica="RIATTIVAZIONE DELLE ATTIVITA TEMPORANEAMENTE SOSPESE";
	else if (tipoPratica == GestioneAnagraficaAction.OP_VARIAZIONI_INFORMAZIONI_SCHEDE_SUPPLEMENTARI)
		descTipoPratica="VARIAZIONI INFORMAZIONI SCHEDE SUPPLEMENTARI";
	else if (tipoPratica == GestioneAnagraficaAction.OP_AMPLIAMENTO_LUOGHI)
		descTipoPratica="AMPLIAMENTO";
	else if (tipoPratica == GestioneAnagraficaAction.OP_TRASFORMAZIONE)
		descTipoPratica="TRASFORMAZIONE (SOLO PANIFICI)";
	else if (tipoPratica == GestioneAnagraficaAction.OP_VARIAZIONE_SEDE_LEGALE)
		descTipoPratica="VARIAZIONE SEDE LEGALE (SEDE FISSA)";
	
	context.getRequest().setAttribute("descTipoPratica", descTipoPratica);
	*/
	
	Connection db = null;
	
	try{
        db = this.getConnection(context);
        Comune c = new Comune();
        c.getDatiById(db, comunePratica);
        context.getRequest().setAttribute("comuneTesto",c.getNome());
        context.getRequest().setAttribute("idprovinciain", c.getCod_provincia());
    	context.getRequest().setAttribute("desc_provincia", c.getDesc_provincia());

    	Richiesta richiesta = new Richiesta();
    	richiesta.getTipoRichiesta(db);
    	
    	for(int i=0; i<richiesta.getRichieste().size(); i++){
    		if(richiesta.getRichieste().get(i).getCode() == tipoPratica){
    			descTipoPratica = richiesta.getRichieste().get(i).getLong_description();
    		}
    	}
    	
    	context.getRequest().setAttribute("descTipoPratica", descTipoPratica);
    	
	}catch (SQLException e) {
    	e.printStackTrace();
    } finally {
        this.freeConnection(context, db);
    }
	
	return "GestioneGisaDellaPratica";
}
	

public String executeCommandAssociaPraticaSuap(ActionContext context) throws IndirizzoNotFoundException {
	
	int altId = -1;
	int stabId = -1;
	String numeroPratica = "";
	int comunePratica = -1;
	
	try {altId = Integer.parseInt(context.getRequest().getParameter("altId"));} catch (Exception e){}
	
	try {numeroPratica = context.getParameter("numeroPratica").toString();} catch (Exception e){}
	context.getRequest().setAttribute("numeroPratica", numeroPratica);
	
	try {comunePratica =  Integer.parseInt(context.getParameter("comunePratica").toString());} catch (Exception e){}
	context.getRequest().setAttribute("comunePratica", String.valueOf(comunePratica));
	
	Connection db = null;
	    
	    try{
	        db = this.getConnection(context);
	        
	        org.aspcfs.modules.opu.base.Stabilimento stab = null;
	        
	        if (stabId>0)
	        	stab = new org.aspcfs.modules.opu.base.Stabilimento(db, stabId);
	        else if (altId>0)
	        	stab = new org.aspcfs.modules.opu.base.Stabilimento(db, altId, true);
	        
	        stab.associaPraticaSuap(db, numeroPratica, comunePratica, getUserId(context)); //non starebbe meglio nelle pratiche?
			context.getRequest().setAttribute("altId", String.valueOf(altId));
	    	
	    }catch (SQLException e) {
	    	e.printStackTrace();
	    } finally {
	        this.freeConnection(context, db);
	    }
	
	return "AssociaPratica";
}


public String executeCommandEliminaScheda(ActionContext context) {
	
	int altId = -1;
	int stabId = -1;
	
	try {
		
		altId = Integer.parseInt(context.getRequest().getParameter("altId"));
		} catch (Exception e){}
	
	try {
		
		stabId = Integer.parseInt(context.getRequest().getParameter("stabId"));} catch (Exception e){}
	
	Connection db = null;
	    
	    try{
	        db = this.getConnection(context);
	        Stabilimento s = null;
	        if(stabId > 0)
	        {
	        	s = new Stabilimento(db,stabId);
	        }
	        else {
	        	s = new Stabilimento(db,altId,true);
	        }
	        s.eliminaAnagrafica(db, getUserId(context)); //gia' presente metodo per stab_id va bene uguale???
	    	RicercheAnagraficheTab.inserOpu(db, s.getIdStabilimento());
	        
	    	
	    }catch (SQLException e) {
	    	e.printStackTrace();
	    } catch (IndirizzoNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	        this.freeConnection(context, db);
	    }
	
	return "EliminaSchedaOk";
}


	public String executeCommandChoose(ActionContext context){
		
	    Connection db = null;
	    
	    try{
	        db = this.getConnection(context);
	
	        //String codice = context.getRequest().getParameter("codice_univoco_ml");
	        String codice = "TEST-SCIA";
	        context.getRequest().setAttribute("codiceLinea", codice);
	        
	        String numeroPratica = "";
	    	String tipoPratica = "";
	    	String dataPratica = ""; 
	    	int comunePratica = -1;
	    	String id_causale = "-1";
	    	String urlRedirectOK = "";
	    	String urlRedirectKO = "";
	    	String motivoInserimento = "";
	    	String apicoltura = "";
	    	
	    	try {numeroPratica = context.getRequest().getParameter("numeroPratica");} catch (Exception e){}
	        context.getRequest().setAttribute("numeroPratica", numeroPratica);
	        
	        context.getRequest().setAttribute("idAggiuntaPratica", System.currentTimeMillis() + (new Random().nextInt(10000)));
	        
	        try{ tipoPratica = context.getRequest().getParameter("tipoPratica");} catch (Exception e){}
	        context.getRequest().setAttribute("tipoPratica", tipoPratica);
	        
	        try{ dataPratica = context.getRequest().getParameter("dataPratica");} catch (Exception e){}
	        context.getRequest().setAttribute("dataPratica", dataPratica);
	        
	        try{ comunePratica = Integer.parseInt(context.getRequest().getParameter("comunePratica"));} catch (Exception e){}
	        context.getRequest().setAttribute("comunePratica", String.valueOf(comunePratica));
	        
	        try {id_causale = context.getRequest().getParameter("causalePratica");} catch (Exception e){}
	        if(id_causale == null){
	        	id_causale = "-1";
	        }
	        context.getRequest().setAttribute("id_causale", id_causale);
	        
	        try{ urlRedirectOK = context.getRequest().getParameter("urlRedirectOK");} catch (Exception e){}
	        if(urlRedirectOK == null){
	        	urlRedirectOK = "";
	        }
	        context.getRequest().setAttribute("urlRedirectOK", urlRedirectOK);
	        
	        try{ urlRedirectKO = context.getRequest().getParameter("urlRedirectKO");} catch (Exception e){}
	        if(urlRedirectKO == null){
	        	urlRedirectKO = "";
	        }
	        context.getRequest().setAttribute("urlRedirectKO", urlRedirectKO);
	        
	        try{ motivoInserimento = context.getRequest().getParameter("motivoInserimento");} catch (Exception e){}
	        if(motivoInserimento == null){
	        	motivoInserimento = "";
	        }
	        context.getRequest().setAttribute("motivoInserimento", motivoInserimento);
	        
	        try{ apicoltura = context.getRequest().getParameter("apicoltura");} catch (Exception e){}
	        if(apicoltura == null){
	        	apicoltura = "";
	        }
	        context.getRequest().setAttribute("apicoltura", apicoltura);
	        	        
	        LineaAttivita linea = new LineaAttivita();
	        linea.getPathDesc(db,codice); //ha il path
	        linea.setCodice_univoco_ml(codice); //ha il codice
	   
	        //LineaAttivita linea = new LineaAttivita(desc_linea, codice); 

	        //cerco tutti i componenti della GUI della linea di attivita
	        linea.cercaMetadati(db); 
	        List<Metadato> listaElementi = new ArrayList<Metadato>();
	        listaElementi = linea.getMetadati();
	        context.getRequest().setAttribute("lineaattivita", listaElementi);
	        context.getRequest().setAttribute("id_asl_stab", getUserSiteId(context));
	        
	     
	        if(comunePratica != -1){
	        	Comune c = new Comune();
	   	        c.getDatiById(db, comunePratica);
	   	        context.getRequest().setAttribute("comuneTesto", c.getNome());
	        	context.getRequest().setAttribute("idprovinciain", c.getCod_provincia());
	        	context.getRequest().setAttribute("desc_provincia", c.getDesc_provincia());
	        	context.getRequest().setAttribute("asl_stab_da_inserire", c.getAsl().getDescription());
	        } else {
	        	if( getUserSiteId(context) > 0){
	        		Asl asl_stab_da_inserire = new Asl();
	        		asl_stab_da_inserire.popolaAslById(db, getUserSiteId(context));
	        		context.getRequest().setAttribute("asl_stab_da_inserire", asl_stab_da_inserire.getDescription());      		
	        	} else {
	        		context.getRequest().setAttribute("asl_stab_da_inserire", "-1");
	        	}
	        }
	        
	        if(apicoltura.equalsIgnoreCase("1")){
	        	return "ChooseOK_2";
	        }
	        
	        
	        if(!urlRedirectOK.equalsIgnoreCase("")){
	        	String toponimo_stabilimento = "";
	        	try{ toponimo_stabilimento = context.getRequest().getParameter("toponimo_stabilimento");} catch (Exception e){}
		        if(toponimo_stabilimento == null){
		        	toponimo_stabilimento = "";
		        }
		        context.getRequest().setAttribute("toponimo_stabilimento", toponimo_stabilimento);
		        
	        	String via_stabilimento = "";
	        	try{ via_stabilimento = context.getRequest().getParameter("via_stabilimento");} catch (Exception e){}
		        if(via_stabilimento == null){
		        	via_stabilimento = "";
		        }
		        context.getRequest().setAttribute("via_stabilimento", via_stabilimento);
		        
	        	String civico_stabilimento = "";
	        	try{ civico_stabilimento = context.getRequest().getParameter("civico_stabilimento");} catch (Exception e){}
		        if(civico_stabilimento == null){
		        	civico_stabilimento = "";
		        }
		        context.getRequest().setAttribute("civico_stabilimento", civico_stabilimento);
		        
	        	String cap_stabilimento = "";
	        	try{ cap_stabilimento = context.getRequest().getParameter("cap_stabilimento");} catch (Exception e){}
		        if(cap_stabilimento == null){
		        	cap_stabilimento = "";
		        }
		        context.getRequest().setAttribute("cap_stabilimento", cap_stabilimento);
	        	
	        	String comune_stabilimento = "";
	        	try{ comune_stabilimento = context.getRequest().getParameter("comune_stabilimento");} catch (Exception e){}
		        if(comune_stabilimento == null){
		        	comune_stabilimento = "";
		        }
		        context.getRequest().setAttribute("comune_stabilimento", comune_stabilimento);
	        	
	        	String provincia_stabilimento = "";
	        	try{ provincia_stabilimento = context.getRequest().getParameter("provincia_stabilimento");} catch (Exception e){}
		        if(provincia_stabilimento == null){
		        	provincia_stabilimento = "";
		        }
		        context.getRequest().setAttribute("provincia_stabilimento", provincia_stabilimento);
	        	
	        	String provinciaIdStabilimento = "";
	        	try{ provinciaIdStabilimento = context.getRequest().getParameter("provinciaIdStabilimento");} catch (Exception e){}
		        if(provinciaIdStabilimento == null){
		        	provinciaIdStabilimento = "";
		        }
		        context.getRequest().setAttribute("provinciaIdStabilimento", provinciaIdStabilimento);
	        	
	        	String comuneIdStabilimento = "";
	        	try{ comuneIdStabilimento = context.getRequest().getParameter("comuneIdStabilimento");} catch (Exception e){}
		        if(comuneIdStabilimento == null){
		        	comuneIdStabilimento = "";
		        }
		        context.getRequest().setAttribute("comuneIdStabilimento", comuneIdStabilimento);
	        	
	        	String topIdStabilimento = "";
	        	try{ topIdStabilimento = context.getRequest().getParameter("topIdStabilimento");} catch (Exception e){}
		        if(topIdStabilimento == null){
		        	topIdStabilimento = "";
		        }
		        context.getRequest().setAttribute("topIdStabilimento", topIdStabilimento);
	        	
	        	return "ChooseOK_1";
	        }
	        
	    }catch (SQLException e) {
	    	e.printStackTrace();
	    } finally {
	        this.freeConnection(context, db);
	    }
	  
	    
	    return "ChooseOK_0";
	}
	
	
	public String executeCommandScegliTemplate(ActionContext context){
		
	    Connection db = null;
	    
	    try{
	        db = this.getConnection(context);
	
	        String id_linea_ml = context.getRequest().getParameter("id_linea_ml"); 	        
	        
	        LineaAttivita linea = new LineaAttivita();

	        String codice = linea.getCodiceDaIdLinea(db, Integer.parseInt(id_linea_ml));
	        context.getRequest().setAttribute("codiceLinea", codice);
	        
	        linea.getPathDesc(db,codice); //ha il path
	        linea.setCodice_univoco_ml(codice); //ha il codice 

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
	    
	    return "Scelta_template_ok";
	}
	
	
	public String executeCommandInsert(ActionContext context){
		
	    Connection db = null;
	    final Logger log = Logger.getLogger(GisaNoScia.class);
	    
	    Map<String, String[]> parameterMap = context.getRequest().getParameterMap();
	    //parameterMap.get("partita_iva"); esempio di accesso
	
		Map<String, String> campiFissi = new HashMap<String, String>();
		Map<String, String> campiEstesi = new HashMap<String, String>();
		ArrayList<String> array_linee_indice = new ArrayList<String>();
		Map<String, String> campiFissiTemp = new HashMap<String, String>();
		ArrayList<String> lista_id_stabilimenti = new ArrayList<String>();
		String valore_campo;
		String chiave_campo;
		String numeroPratica = "";
		int idTipologiaPratica = -1;
		int idComunePratica = -1;
		String url_redirect_ok = "";
		
		String tipo_linee_attivita = context.getRequest().getParameter("_b_tipo_linee_attivita");

		for (String key: parameterMap.keySet()){
			valore_campo = parameterMap.get(key)[0].trim();
			chiave_campo = key.trim();
			if (chiave_campo.startsWith("_b_") && (!chiave_campo.startsWith("_b_lineaattivita_")))
			{
				if(valore_campo.equalsIgnoreCase("")){
					valore_campo = null;
				}
				campiFissi.put(chiave_campo.replaceFirst("_b_", ""), valore_campo);
			}
			else if(chiave_campo.startsWith("_b_lineaattivita_") && chiave_campo.endsWith("_tipo_attivita")){
				array_linee_indice.add(chiave_campo.replaceAll("_tipo_attivita", "") + ";;" + valore_campo);
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
			
			else if (chiave_campo.equalsIgnoreCase("url_redirect_ok"))
			{
				url_redirect_ok = valore_campo;
			}
			
		}
		
		try
		{
			
			db = this.getConnection(context);
			
			String stabid = context.getRequest().getParameter("alt_id");
		
			org.aspcfs.modules.accounts.base.Organization org = null;
			if(idTipologiaPratica == OP_AGGIORNA_DATI_EX_IMPORT){
				org = new org.aspcfs.modules.accounts.base.Organization(db, Integer.parseInt(stabid));
			}
			
			if(idTipologiaPratica == OP_AGGIORNA_DATI_EX_IMPORT && context.getRequest().getSession().getAttribute("context")!=null && org.getTipologia()!=801 && org.getTipologia()!=802)//se 802 non deve entrare
				Accounts.UpdateLineePregresse((ActionContext)context.getRequest().getSession().getAttribute("context"),db,""+stabid,1,getUserId(context));
			else if(idTipologiaPratica == OP_AGGIORNA_DATI_EX_IMPORT && context.getRequest().getSession().getAttribute("context")!=null && org.getTipologia()==801)//se 802 non deve entrare
				Accounts.UpdateLineePregresse((ActionContext)context.getRequest().getSession().getAttribute("context"),db,""+stabid,3,getUserId(context));
			
			
		
			int userId =  getUserId(context);
			System.out.println("userid: " + userId);
		
			//gestione dati linee fisse
			campiFissiTemp = campiFissi;
			boolean flag_fisse = false;
			for(int i = 0; i < array_linee_indice.size(); i++){
				
				if(array_linee_indice.get(i).split(";;")[1].equals("1")){
					for (String key: parameterMap.keySet()){
						valore_campo = parameterMap.get(key)[0].trim();
						chiave_campo = key.trim();
						
						if(chiave_campo.startsWith(array_linee_indice.get(i).split(";;")[0])){
							
							if(valore_campo.equalsIgnoreCase("")){
								valore_campo = null;
							}
							campiFissiTemp.put(chiave_campo.replaceFirst("_b_", ""), valore_campo);
						}
					}
					flag_fisse = true;
				}
				
			}
			
			boolean flag_pregresse = Boolean.parseBoolean(context.getRequest().getParameter("flag_pregresse"));
			
			if(idTipologiaPratica == OP_AGGIORNA_DATI_EX_IMPORT && context.getRequest().getSession().getAttribute("context")!=null && ( org.getTipologia()==1  || org.getTipologia()==801 ))
			{
				
				Iterator<LineeAttivita> linee_attivita = LineeAttivita.load_linee_attivita_per_org_id_per_import(stabid+"", db).iterator();
				int i=0;
				while(linee_attivita.hasNext())
				{
					LineeAttivita linea = linee_attivita.next();
					campiFissiTemp.put("lineaattivita_"+i+"_codice_univoco_ml", linea.getCodice_istat());
					campiFissiTemp.put("idLineaVecchiaMasterlist" + i, linea.getIdAttivita()+"");
					campiFissiTemp.put("idLineaVecchia"+i, linea.getId()+"");
					i++;
				}
				flag_pregresse = false;
			}
			else if(idTipologiaPratica == OP_AGGIORNA_DATI_EX_IMPORT && context.getRequest().getSession().getAttribute("context")!=null && org.getTipologia()!=1 && org.getTipologia()!=802) //escludere 802
			{
				Iterator<LineeAttivita> linee_attivita = LineeAttivita.load_linee_attivita_per_org_id_per_import(stabid+"", db).iterator();
				int i=0;
				while(linee_attivita.hasNext())
				{
					LineeAttivita linea = linee_attivita.next();
					LineaProduttiva lp = new LineaProduttiva(db, Integer.parseInt(((ActionContext)context.getRequest().getSession().getAttribute("context")).getRequest().getParameter("idLineaProduttiva" )));
					campiFissiTemp.put("lineaattivita_"+i+"_codice_univoco_ml", lp.getCodice());
					campiFissiTemp.put("idLineaVecchiaMasterlist" + i, lp.getIdAttivita()+"");
					campiFissiTemp.put("idLineaVecchia"+i, lp.getId()+"");
					i++;
				}
				flag_pregresse = false;
			}
			
			String id_stabilimento = "";
			//inserisci stabilimento con linee fisse (se presenti)
			Stabilimento s = new Stabilimento();

			if(flag_fisse){
			
				id_stabilimento = s.inserisci_stabilimento(db, campiFissiTemp, campiEstesi, userId, idTipologiaPratica, numeroPratica, idComunePratica);
				
				//Propagazione
		        if (idTipologiaPratica==OP_NUOVO_STAB || idTipologiaPratica==OP_AMPLIAMENTO){
		        	GestoreComunicazioniBdu gBdu = new GestoreComunicazioniBdu();
		        	gBdu.inserisciNuovaSciaBdu(Integer.parseInt(id_stabilimento));
		        }
		        
		        allega_file_errata_corrige(context, db, Integer.parseInt(id_stabilimento));
		        if(idTipologiaPratica != OP_AGGIORNA_DATI_EX_IMPORT){
			        OggettoPerStorico stab_pre_modifica = new OggettoPerStorico();
			        stab_pre_modifica.salvaStoricoEvento(db,Integer.parseInt(id_stabilimento),null);
		        }
				lista_id_stabilimenti.add(id_stabilimento); //aggiungi id stabilimento a lista stab inseriti da passare a componente output 
			}
			
			
			for(int i = 0; i < array_linee_indice.size(); i++){
				//inserisci stabilimento con linea mobili (se presenti)
				if(array_linee_indice.get(i).split(";;")[1].equals("2")){
					campiFissiTemp = campiFissi;
					for (String key: parameterMap.keySet()){
						valore_campo = parameterMap.get(key)[0].trim();
						chiave_campo = key.trim();
						
						if(chiave_campo.startsWith(array_linee_indice.get(i).split(";;")[0])){
							
							if(valore_campo.equalsIgnoreCase("")){
								valore_campo = null;
							}
							campiFissiTemp.put(chiave_campo.replaceFirst("_b_", ""), valore_campo);
						}
					}
					id_stabilimento = s.inserisci_stabilimento(db, campiFissiTemp, campiEstesi, userId, idTipologiaPratica, numeroPratica, idComunePratica);
					
					//Propagazione
			        if (idTipologiaPratica==OP_NUOVO_STAB || idTipologiaPratica==OP_AMPLIAMENTO){
			        	GestoreComunicazioniBdu gBdu = new GestoreComunicazioniBdu();
			        	gBdu.inserisciNuovaSciaBdu(Integer.parseInt(id_stabilimento));
			        }
			        
			        allega_file_errata_corrige(context, db, Integer.parseInt(id_stabilimento));
			        if(idTipologiaPratica != OP_AGGIORNA_DATI_EX_IMPORT){
			        	OggettoPerStorico stab_pre_modifica = new OggettoPerStorico();
			        	stab_pre_modifica.salvaStoricoEvento(db,Integer.parseInt(id_stabilimento),null);
			        }
					//aggiungi id stabilimento a lista stab inseriti da passare a componente output 
					lista_id_stabilimenti.add(id_stabilimento);
				}
			}
			System.out.println("id stabilimento inserito: " + lista_id_stabilimenti.get(0));	        
	        context.getRequest().setAttribute("id_stabilimento", String.valueOf(lista_id_stabilimenti.get(0)));
	        context.getRequest().setAttribute("url_redirect_ok", url_redirect_ok);
	        
	        Stabilimento stabNew = new Stabilimento(db, Integer.parseInt(lista_id_stabilimenti.get(0)));
	        
	        if(!url_redirect_ok.equalsIgnoreCase("")){
	        	stabNew.escludiDaRicerca(db, Integer.parseInt(lista_id_stabilimenti.get(0)));
	        }
	        
	        if(idTipologiaPratica == OP_AGGIORNA_DATI_EX_IMPORT)
		    {  	
	        	int altId = Integer.parseInt(context.getRequest().getParameter("alt_id"));
	        	stabNew.setOrgId(altId);
				StabilimentoImportUtil.importaOperatoriMercatoIttico(db, altId, Integer.parseInt(lista_id_stabilimenti.get(0)));
				StabilimentoImportUtil.cancellaOrganization(db, altId);
				
				stabNew.setTipoVecchiaOrg(org.getTipologia());
				
				/* non esiste piu questa tipologia
				if (org.getTipologia()==3) 
				{
					StabilimentoImportUtil.importaMacellazioni(db, org, stabNew);
				}
				*/
				
				GestoreComunicazioniBdu gBdu = new GestoreComunicazioniBdu();
				if(org.getOrgIdCanina()>0)
				{
					gBdu.aggiornaIdStabilimentoGisaBdu(Integer.parseInt(lista_id_stabilimenti.get(0)), org.getOrgIdCanina());
				}
				else
				{
					gBdu.inserisciNuovaSciaBdu(Integer.parseInt(lista_id_stabilimenti.get(0)));
				}
				
				int i=0;
				int j = 0;
				
				//IMPORT CONTROLLI
				if(stabNew.getTipoVecchiaOrg() != 802) {
					stabNew.importaControlliUfficialiCuSenzaLinea(db,userId);
		    	}
				for(LineaProduttiva lp : (Vector<LineaProduttiva>) stabNew.getListaLineeProduttive())
				{
					
					
					lp.setIdVecchiaLinea(context.getRequest().getParameter("_b_idLineaVecchia"+i));
					String numRegistrazioneOld = context.getRequest().getParameter("numRegistrazioneOld"+i);

					String sql1 = "select * from opu_relazione_stabilimento_linee_produttive where id_stabilimento = ? and id_linea_produttiva = ?";
					PreparedStatement pst1 = db.prepareStatement(sql1);
					pst1.setInt(1, stabNew.getIdStabilimento());
					pst1.setInt(2, lp.getIdRelazioneAttivita());
					ResultSet rs = pst1.executeQuery();
					if (rs.next())
					{
					
						int id = rs.getInt("id");

						if ( tipo_linee_attivita.equals("1")) 
							stabNew.importaControlliUfficialiCompletaDati(db, lp.getIdVecchiaLinea(), id, lp.isPrincipale(), lp.getCodice(),userId);
						else 
						{
								stabNew.setIdRelStabLpMobile(id);
								stabNew.importaTarga(db, stabNew.getTargaImportata(), stabNew.getIdTipoVeicolo() + "", id);
								stabNew.importaControlliUfficialionTarga(db, lp.getIdVecchiaLinea(), id, lp.isPrincipale(),stabNew.getTargaImportata(), lp.getCodice(),userId);
						}

					} 
					
					j = stabNew.aggiornaLinee(db, org, lp, stabNew, altId,context,(ActionContext)context.getRequest().getSession().getAttribute("context"),i,j);
					
					HashMap<Integer,ArrayList<LineaProduttivaCampoEsteso>> valoriCampiEstesi = costruisciValoriCampiEstesi(context,db,lp.getIdRelazioneAttivita());
					
					
					if (valoriCampiEstesi != null && tipo_linee_attivita.equals("1"))
					{
						for (Integer idLineeMobiliHtmlFields : valoriCampiEstesi.keySet()) 
						{
							
							PreparedStatement pst0 = db.prepareStatement("select * from public.dbi_insert_campi_estesi(?, ?, ?, -1, ?, -1, -1)");
	
							int idRelStabLinea = lp.getId_rel_stab_lp();
	
							pst0.setInt(1, idRelStabLinea);
							pst0.setInt(2, idLineeMobiliHtmlFields);
							pst0.setString(3, null);
							pst0.setInt(4, userId);
							pst0.execute();
							pst0.close();
	
						}
					}		
							
					i++;
					
				}
				
				stabNew.aggiornaCategoriaRischio(db,org);
				stabNew.aggiornaInfoOp(db, org, userId, flag_pregresse, stabNew, altId);
				//FINE IMPORT CONTROLLI
				
				//salvo in storico operazioni stabilimento
				OggettoPerStorico stab_pre_modifica = new OggettoPerStorico();
	        	stab_pre_modifica.salvaStoricoEvento(db,stabNew.getIdStabilimento(),null);				
				
				//context.getRequest().setAttribute("idStab", stabNew.getIdStabilimento());
		        //new StabilimentoAction().executeCommandDetails(context);
		        //return "DetailsOpuOK";
		        context.getRequest().setAttribute("id_stabilimento", String.valueOf(stabNew.getIdStabilimento()));
		    	
		     }
	        
	        return "DetailsInsertOK";
		    
	    
		}catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("erroreInsert", 
					"Errore inserimento stabilimento: contattare hd I livello!!!<br>" + e.toString());
			return "ErrorPageInsert";
        } finally {
            this.freeConnection(context, db);
        }  
	
    }
	
	
	public String executeCommandImportParafarmacie(ActionContext context) throws IndirizzoNotFoundException{
		
		Connection db = null;
	    
	    try
	    {	
	    	int altId = -1;
	        try { altId = Integer.parseInt(context.getRequest().getParameter("altId")); } catch (Exception e) {}
	        context.getRequest().setAttribute("altId", String.valueOf(altId));
	        
	        ArrayList<LdaImportParafarmacie> linee = new ArrayList<LdaImportParafarmacie>();
	        
	        db = this.getConnection(context);
	        String sql = "select ml8.id_nuova_linea_attivita as id, "
	        		+ "	ml8.codice as codice, "
	        		+ " upper(concat(ln.description, '-> ', ml8.path_descrizione)) as path_descrizione,"
	        		+ " ln.description as description "
	        		+ " from ml8_linee_attivita_nuove_materializzata ml8 "
	        		+ " join opu_lookup_norme_master_list ln on code = ml8.id_norma "
	        		+ " where ml8.codice in ('FVET-COID-CI', 'FARMDEP-DF-DF', 'FARM-FARM-FARMVET') "
	        		+ " and ml8.rev = (select max(rev) from ml8_linee_attivita_nuove_materializzata) "
	        		+ " order by path_descrizione; ";
        	PreparedStatement st = db.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
	        while(rs.next()){
	        	LdaImportParafarmacie linea = new LdaImportParafarmacie(
	        			rs.getInt("id"), 
	        			rs.getString("path_descrizione")
	        			);
	        	linee.add(linea);
	        }
	        context.getRequest().setAttribute("listLinee", linee);
  
	    }
	    catch (SQLException e) 
	    {
	    	e.printStackTrace();
	    } 
	    finally 
	    {
	        this.freeConnection(context, db);
	    }
		
		return "ImportParafarmacieTemp";
	}
	
	//RITA	
	public String executeCommandImport(ActionContext context) throws IndirizzoNotFoundException
	{
	    Connection db = null;
	    
	    try
	    {
	    	context.getRequest().getSession().setAttribute("context", null);
	    	
	        db = this.getConnection(context);
	        
	        int altId = -1;
	        try { altId = Integer.parseInt(context.getRequest().getParameter("altId")); }  catch (Exception e) {}
	        
        	Stabilimento s  = new Stabilimento();
	        LinkedHashMap<String, String> valoriAnagrafica = new LinkedHashMap<String, String>();
	        valoriAnagrafica = s.getValoriAnagraficaImport(db,altId);
	        context.getRequest().setAttribute("ValoriAnagrafica", valoriAnagrafica);

	    	String id_causale = "-1";
	    	
	    	int stabId = -1;
	    	int idAsl = -1;
	    	
	    	String id_linea_paraf = "";
	    	LdaImportParafarmacie linea_in_input = new LdaImportParafarmacie();
        	try { id_linea_paraf = context.getRequest().getParameter("id_linea"); } catch (Exception e) {}
        	if(id_linea_paraf == null)
        		id_linea_paraf = "";
        	
	        try { id_causale = context.getRequest().getParameter("causalePratica"); } catch (Exception e) {}
	        
	        if(id_causale == null)
	        	id_causale = "-1";
	        context.getRequest().setAttribute("id_causale", id_causale);
	        
	    	try {stabId = Integer.parseInt(context.getRequest().getParameter("stabId"));} catch (Exception e){}
	    	context.getRequest().setAttribute("stabId", String.valueOf(stabId));
	    	
	    	try {idAsl = Integer.parseInt(context.getRequest().getAttribute("idAsl").toString());} catch (Exception e){}
	    	
	    	org.aspcfs.modules.accounts.base.Organization org = new org.aspcfs.modules.accounts.base.Organization(db, altId);
	        
	    	String tipologia_operatore = "";
	 		context.getRequest().setAttribute("StabilimentoDettaglio", org);
	 		context.getRequest().setAttribute("idAsl", String.valueOf(org.getIdAsl()));
	 		context.getRequest().setAttribute("statoStabilimento", String.valueOf(org.getIdStato(db)));
	 		
	        String tipoSede = "1";
	        if((org.getTipologia()==151 || org.getTipologia()== 802 || org.getTipologia()== 152 || org.getTipologia()== 10 || org.getTipologia()== 20 || org.getTipologia()== 2 || org.getTipologia()== 800 || org.getTipologia()== 801) ||
	        		 (org.getTipologia() == 1 && (org.getTipoDest().equals( "Es. Commerciale") || org.getTipoDest().trim().length() == 0 || org.getTipoDest().equals("Distributori") )))
	        	tipoSede="1";
	        else if (org.getTipologia() == 1 && org.getTipoDest().equals("Autoveicolo"))
	        	tipoSede="2";

	        context.getRequest().setAttribute("tipo_linee_attivita", tipoSede);

	        if(org.getTipologia() != 802){
	        		        
		        ArrayList<LineeAttivita> linee_attivita = LineeAttivita.load_linee_attivita_per_org_id_per_import(altId+"", db);
				context.getRequest().setAttribute("linee_attivita",linee_attivita);
				
				context.getRequest().setAttribute("flag_pregresse", "false");
				
				for (int i = 0; i < linee_attivita.size(); i++) 
				{
					LineeAttivita l = linee_attivita.get(i);
					if (!l.isMappato()) 
						context.getRequest().setAttribute("flag_pregresse", "true");
				}
	        } else if(org.getTipologia() == 802){
	        	
	        	ArrayList<LineeAttivita> linee_attivita = new ArrayList<LineeAttivita>();
	        	LineeAttivita linea_da_passare = new LineeAttivita();
	        	linea_da_passare.setId(altId);
	        	linea_da_passare.setMappato(true);
	        	linea_da_passare.setIdAttivita(Integer.parseInt(id_linea_paraf));
	        	linea_da_passare.setCodice_istat(linea_in_input.getCodiceUnivoco(db, Integer.parseInt(id_linea_paraf)));
	        	linee_attivita.add(linea_da_passare);
	        	
	        	tipologia_operatore = String.valueOf(org.getTipologia());
	        	
	        	context.getRequest().setAttribute("linee_attivita",linee_attivita);
	        	context.getRequest().setAttribute("flag_pregresse", "false");
	        	context.getRequest().setAttribute("desc_linea_import_paraf", 
	        			linea_in_input.getDescrizioneLinea(db, Integer.parseInt(id_linea_paraf)));
	        }
	        
	        String codice = "TEST-SCIA";
	        String desc_linea = "";
	        //non serve la query per la desc_linea perche in questo caso si tratta di SCIA
	        String flag_scia_or_noscia = "SCIA";
	        
	        if(tipoSede.equalsIgnoreCase("1")){
	        	codice = "SCIA-FISSO";
	        } else if(tipoSede.equalsIgnoreCase("2")){
	        	codice = "SCIA-MOBILE";
	        }
	        
	        if(!id_linea_paraf.equalsIgnoreCase("")){
	        	if(linea_in_input.isNoScia(db, Integer.parseInt(id_linea_paraf))){
	        		flag_scia_or_noscia = "NOSCIA";
	        	}
	        	codice = linea_in_input.getCodiceUnivoco(db, Integer.parseInt(id_linea_paraf));
	        	desc_linea = linea_in_input.getDescrizioneLinea(db, Integer.parseInt(id_linea_paraf));
	        	desc_linea = desc_linea.replaceAll("->", "<br>->");
	        	context.getRequest().setAttribute("descr_linea_paraf", desc_linea);
	        }
	        
	        context.getRequest().setAttribute("flag_scia_or_noscia", flag_scia_or_noscia);
	        
	        context.getRequest().setAttribute("codiceLinea", codice);
 
	        LineaAttivita linea = new LineaAttivita(desc_linea, codice);
	        //cerco tutti i componenti della GUI della linea di attivita
	        linea.cercaMetadati(db); 
	        List<Metadato> listaElementi = new ArrayList<Metadato>();
	        listaElementi = linea.getMetadati();
	        context.getRequest().setAttribute("lineaattivita", listaElementi);
	        context.getRequest().setAttribute("id_asl_stab", getUserSiteId(context));
	        context.getRequest().setAttribute("id_causale", "5"); //causale 5 - altro
	        context.getRequest().setAttribute("tipoPratica", "16"); //tipo operazione 16 - aggiorna dati
	        context.getRequest().setAttribute("altId", String.valueOf(altId));
	        context.getRequest().setAttribute("tipologia_operatore", tipologia_operatore);
	        String data_pratica = (new SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date()));
	        context.getRequest().setAttribute("data_pratica", data_pratica);
	        
	    }
	    catch (SQLException e) 
	    {
	    	e.printStackTrace();
	    } 
	    finally 
	    {
	        this.freeConnection(context, db);
	    }
	  

	    //return "SchedaModify";
	    return "Import";
	}
		
	public String executeCommandDetails(ActionContext context) throws IndirizzoNotFoundException{
		
		context.getRequest().setAttribute("Messaggio", (String)context.getRequest().getAttribute("Messaggio"));
		
		int altId = -1;
		int stabId = -1;
		
		try {altId = (Integer) context.getRequest().getAttribute("altId");} catch (Exception e){}
		if (altId==-1)
			try {altId = Integer.parseInt(context.getRequest().getParameter("altId"));} catch (Exception e){}
		//context.getRequest().setAttribute("altId", String.valueOf(altId));
		
		try {stabId = (Integer) context.getRequest().getAttribute("stabId");} catch (Exception e){}
		if (stabId==-1)
			try {stabId = Integer.parseInt(context.getRequest().getParameter("stabId"));} catch (Exception e){}
		//context.getRequest().setAttribute("stabId", String.valueOf(stabId));
		
		Connection db = null;  
	    
	    try{
	        db = this.getConnection(context);
	        org.aspcfs.modules.opu.base.Stabilimento stab = null;
	        
	        if (stabId>0)
	        	stab = new org.aspcfs.modules.opu.base.Stabilimento(db, stabId);
	        else if (altId>0)
	        	stab = new org.aspcfs.modules.opu.base.Stabilimento(db, altId, true);

	        //gestione sincronizzazione dbu
			if(stabId > 0 || altId > 0){
				String sincronizza = context.getParameter("sincronizzaBdu");
				if(sincronizza!=null)
				{
					GestoreComunicazioniBdu gBdu = new GestoreComunicazioniBdu();
					gBdu.inserisciNuovaSciaBdu(stab.getIdStabilimento());
					stab.setCodiceErroreSuap("Sincronizzazione Avvenuta Con succeso");					
				}
				String sincronizzaMq = context.getParameter("sincronizzaBduMq");
				if(sincronizzaMq!=null)
				{
					int idRelCanile = Integer.parseInt(context.getParameter("idRelCanile"));
					GestoreComunicazioniBdu gBdu = new GestoreComunicazioniBdu();
					int superficie = stab.getSuperficieMqCanile(db, idRelCanile);
					System.out.println("Tentativo di sincronizzazione: id rel" + idRelCanile+ " superficie "+superficie);
					if (superficie >=0) 
					{
						gBdu.variazioneSuperficie(stab.getIdStabilimento(),superficie, getUserId(context));
						stab.setCodiceErroreSuap("Sincronizzazione Avvenuta Con succeso");
					}
					else 
					{
						stab.setCodiceErroreSuap("Sincronizzazione NON AVVENUTA - Valore non consentito nel campo MQ.");
					}
				}
			}
			
			 //Caricamento Diffide
			Ticket t = new Ticket();
			context.getRequest().setAttribute("DiffideList", t.getDiffide(db,false,stab.getIdStabilimento(),null,null,null,null)); 
			context.getRequest().setAttribute("DiffideListStorico", t.getDiffide(db,true,stab.getIdStabilimento(),null,null,null,null));
			
			//calcolo il numero di cu legati a questo osa
			int numero_cu = 0;
			numero_cu = t.contaCU(db,stab.getIdStabilimento(),stab.getAltId());
			context.getRequest().setAttribute("Numero_cu", String.valueOf(numero_cu));
			
			//calcolo il numero di pratiche legate a questo osa
			int numero_pratiche = 0;
			Pratica pratica = new Pratica();
			numero_pratiche = pratica.contaPratiche(db,stab.getIdStabilimento());
        	context.getRequest().setAttribute("Numero_pratiche", String.valueOf(numero_pratiche));
        	
        	int numero_linee_aggiungibili = stab.numeroLineeAggiungibili(
        			db, 
        			stab.getIdStabilimento(), 
        			getUser(context, getUserId(context)).getId_tipo_gruppo_ruolo());         	
        	context.getRequest().setAttribute("numero_linee_aggiungibili", String.valueOf(numero_linee_aggiungibili));
        	
        	context.getRequest().setAttribute("id_stabilimento", stab.getIdStabilimento());
        	context.getRequest().setAttribute("stabId", String.valueOf(stab.getIdStabilimento()));
        	context.getRequest().setAttribute("altId", String.valueOf(stab.getAltId()));
        	stab.getDatiByAltId(db, stab.getAltId());  
        	context.getRequest().setAttribute("StabilimentoDettaglio", stab);
        	
	    }catch (SQLException e) {
	    	e.printStackTrace();
	    } finally {
	        this.freeConnection(context, db);
	    }
		
	    //return "DetailsInsertOK";
	    //return "DetailsOK";
	    return getReturn(context, "Details");
	    
	}
	
	public String executeCommandDetailsPopup(ActionContext context) throws IndirizzoNotFoundException{
		
		int altId = -1;
		
		try {altId = Integer.parseInt(context.getRequest().getParameter("altId"));} catch (Exception e){}
		context.getRequest().setAttribute("altId", String.valueOf(altId));
		
		return "PopupDetailsOK";
	}
	
	public String executeCommandTemplateDetails(ActionContext context) throws IndirizzoNotFoundException{
		
		int altId = -1;
		int stabId = -1;
		String descrizione_asl_print = "";
		String tipo_attivita_stabilimento = "";
		
		try {altId = Integer.parseInt(context.getRequest().getParameter("altId"));} catch (Exception e){}
		context.getRequest().setAttribute("altId", String.valueOf(altId));
		
		try {stabId = Integer.parseInt(context.getRequest().getParameter("stabId"));} catch (Exception e){}
	
		Connection db = null; 
	    
	    try{
	        db = this.getConnection(context); 
	        Stabilimento s = null;
	        
	        if (stabId>0)
	        	s = new Stabilimento(db, stabId);
	        else if (altId>0)
	        	s = new Stabilimento(db, altId, true);
	        if (altId>0){
	        	
	        	s.getDatiByAltId(db, altId);
	        	context.getRequest().setAttribute("descrizione_asl_print", s.getAsl());
	    		context.getRequest().setAttribute("tipo_attivita_stabilimento", s.getTipoAttivitaDesc());
	        }

	        LinkedHashMap<String, String> valoriAnagrafica = new LinkedHashMap<String, String>();
	        valoriAnagrafica = s.getValoriAnagrafica(db,altId);
	        context.getRequest().setAttribute("ValoriAnagrafica", valoriAnagrafica);	
	        
	        LinkedHashMap<String, String> valoriLinee = new LinkedHashMap<String, String>();
	        valoriLinee = s.getValoriAnagraficaLinee(db, altId);	        
	        context.getRequest().setAttribute("ValoriLinee", valoriLinee);

	        LinkedHashMap<String, String> valoriExtra = new LinkedHashMap<String, String>();
	        valoriExtra = s.getValoriAnagraficaExtra(db,altId);
			context.getRequest().setAttribute("ValoriExtra", valoriExtra);
			
	        LinkedHashMap<String, String> valoriEstesi = new LinkedHashMap<String, String>();
	        valoriEstesi = s.getValoriAnagraficaEstesi(db,altId);
			context.getRequest().setAttribute("ValoriEstesi", valoriEstesi); 
	        
	        String codice = "TEST-SCIA";
	        
	        String desc_linea = "";
	        //CONTROLLO SE QUELLA LINEA E' NO SCIA
        	LineaProduttiva lin = (LineaProduttiva) s.getListaLineeProduttive().get(0);
        	if(lin.getFlags().isNoScia())
        	{
        		codice = lin.getCodice();  
        		desc_linea = lin.getNorma() + "-> " + lin.getDescrizione_linea_attivita();
        		System.out.println("codice linea: " + codice);
        	} else {
        		codice = s.RecuperaTemplateOsaDaLinea(db, lin);
        		if(codice.equalsIgnoreCase("") && s.getTipoAttivita() == 1){
        			codice = "SCIA-FISSO";
        		} else if(codice.equalsIgnoreCase("") && s.getTipoAttivita() == 2){
        			codice = "SCIA-MOBILE";
        		}
        	}

	        context.getRequest().setAttribute("codiceLinea",codice);
	        //l.getLineaByCodice(db,codice);
	        LineaAttivita linea = new LineaAttivita(desc_linea, codice);
	        //cerco tutti i componenti della GUI della linea di attivita
	        linea.cercaMetadati(db); 
	        List<Metadato> listaElementi = new ArrayList<Metadato>();
	        listaElementi = linea.getMetadati();
	        context.getRequest().setAttribute("lineaattivita", listaElementi);
	        
	        String id_info_specifica = null;
	        try {id_info_specifica = context.getRequest().getParameter("id_info_specifica");} catch (Exception e){}
	        
	        LinkedHashMap<String, String> valoriInfoSpecifica = new LinkedHashMap<String, String>();	        
	        
	        if(id_info_specifica != null){
	        	valoriInfoSpecifica = s.getValoriInfoSpecifica(db,Integer.parseInt(id_info_specifica));				
	        } else {
	        	id_info_specifica = "";
	        }
	        
	        context.getRequest().setAttribute("ValoriInfoSpecifica", valoriInfoSpecifica);
	        System.out.println("id informazione specifica: " + id_info_specifica);	
	        context.getRequest().setAttribute("id_info_specifica", id_info_specifica);
	        
	    }catch (SQLException e) {
	    	e.printStackTrace();
	    } finally {
	        this.freeConnection(context, db);
	    }
	
	    return "TemplateDetailsOK";
	}	
		
	
	
	public String executeCommandModify(ActionContext context) throws IndirizzoNotFoundException{
		
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
         
	    }catch (SQLException e) {
	    	e.printStackTrace();
	    } finally {
	        this.freeConnection(context, db);
	    }
		
	    //return "ModifyOK";
	    return executeCommandTemplateModify(context);
	}
	
public String executeCommandTemplateModify(ActionContext context) throws IndirizzoNotFoundException{
		
		int altId = -1;
		
		try {altId = Integer.parseInt(context.getRequest().getParameter("altId"));} catch (Exception e){}
		context.getRequest().setAttribute("altId", String.valueOf(altId));
	
		int idAsl = -1;
		
		try {idAsl = Integer.parseInt(context.getRequest().getAttribute("idAsl").toString());} catch (Exception e){}
		
		Connection db = null; 
	    
	    try{
	        db = this.getConnection(context); 
	        
	        Stabilimento s = new Stabilimento(db,altId,true);
	        LinkedHashMap<String, String> valoriAnagrafica = new LinkedHashMap<String, String>();
	        valoriAnagrafica = s.getValoriAnagraficaModifica(db,altId);
	        context.getRequest().setAttribute("ValoriAnagrafica", valoriAnagrafica);
	        
	        String codice = "TEST-SCIA";

	        String desc_linea = "";
	        String flag_scia_or_noscia = "SCIA";
	        //CONTROLLO SE QUELLA LINEA E' NO SCIA
        	LineaProduttiva lin = (LineaProduttiva) s.getListaLineeProduttive().get(0);
        	if(lin.getFlags().isNoScia())
        	{	
        		flag_scia_or_noscia = "NOSCIA";
        		codice = lin.getCodice();
        		desc_linea = lin.getNorma() + "-> " + lin.getDescrizione_linea_attivita();
        	} else {
        		codice = s.RecuperaTemplateOsaDaLinea(db, lin);
        		if(codice.equalsIgnoreCase("") && s.getTipoAttivita() == 1){
        			codice = "SCIA-FISSO";
        		} else if(codice.equalsIgnoreCase("") && s.getTipoAttivita() == 2){
        			codice = "SCIA-MOBILE";
        		}
        	}
	        
	        context.getRequest().setAttribute("codiceLinea", codice);
	        LineaAttivita linea = new LineaAttivita(desc_linea, codice);
	        //cerco tutti i componenti della GUI della linea di attivita
	        linea.cercaMetadati(db); 
	        List<Metadato> listaElementi = new ArrayList<Metadato>();
	        listaElementi = linea.getMetadati();
	        context.getRequest().setAttribute("lineaattivita", listaElementi);
	        context.getRequest().setAttribute("id_asl_stab", idAsl);
	        context.getRequest().setAttribute("id_causale", "2");
	        context.getRequest().setAttribute("tipoPratica", "12");
	        context.getRequest().setAttribute("tipo_linee_attivita", s.getTipoAttivita());
	        context.getRequest().setAttribute("flag_scia_or_noscia", flag_scia_or_noscia);
	        context.getRequest().setAttribute("idAggiuntaPratica", System.currentTimeMillis() + (new Random().nextInt(10000)));
	        

	    }catch (SQLException e) {
	    	e.printStackTrace();
	    } finally {
	        this.freeConnection(context, db);
	    }
	
	    return "SchedaModify";
	}

public String executeCommandModifyGeneric(ActionContext context) throws IndirizzoNotFoundException{
	
	int altId = -1;
	int stabId = -1;
	
	try {altId = Integer.parseInt(context.getRequest().getParameter("altId"));} catch (Exception e){}
	context.getRequest().setAttribute("altId", String.valueOf(altId));
	
	try {stabId = Integer.parseInt(context.getRequest().getParameter("stabId"));} catch (Exception e){}
	context.getRequest().setAttribute("stabId", String.valueOf(stabId));
	
	String numeroPratica = "";
	String tipoPratica = "";
	String dataPratica = ""; 
	int comunePratica = -1;
	String id_causale = "-1";
	String output = "";
	
	try {numeroPratica = context.getRequest().getParameter("numeroPratica");} catch (Exception e){}
    context.getRequest().setAttribute("numeroPratica", numeroPratica);
    
    context.getRequest().setAttribute("idAggiuntaPratica", System.currentTimeMillis() + (new Random().nextInt(10000)));
    
    try{ tipoPratica = context.getRequest().getParameter("tipoPratica");} catch (Exception e){}
    
    
    try{ dataPratica = context.getRequest().getParameter("dataPratica");} catch (Exception e){}
    context.getRequest().setAttribute("dataPratica", dataPratica);
    
    try {comunePratica = Integer.parseInt(context.getRequest().getParameter("comunePratica"));} catch (Exception e){}
	context.getRequest().setAttribute("comunePratica", String.valueOf(comunePratica));
	
	try {id_causale = context.getRequest().getParameter("causalePratica");} catch (Exception e){}

    if(id_causale == null){
    	id_causale = "-1";
    }
    
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
		LookupList statoLab = new LookupList(db, "lookup_stato_lab_view_gestione_anagrafica");
		context.getRequest().setAttribute("ListaStati", statoLab);
		
		int numero_linee_aggiungibili = stab.numeroLineeAggiungibili(
    			db, 
    			stab.getIdStabilimento(), 
    			getUser(context, getUserId(context)).getId_tipo_gruppo_ruolo());         	
    	context.getRequest().setAttribute("numero_linee_aggiungibili", String.valueOf(numero_linee_aggiungibili));
		
	    switch(context.getRequest().getParameter("operazione")){
	    	case "ampliamento":
	    		//inteso come aggiunzione linea di attivita
	    		output = "Ampliamento";
	    		tipoPratica = String.valueOf(GestioneAnagraficaAction.OP_AMPLIAMENTO);
	    		break;
	    	
	    	case "cessazione":
	    		output = "Cessazione";
	    		tipoPratica = String.valueOf(GestioneAnagraficaAction.OP_CESSAZIONE);
	    		break;
	    		
	    	case "cessazioneufficio":
	    		output = "CessazioneUfficio";
	    		tipoPratica = String.valueOf(GestioneAnagraficaAction.OP_CESSAZIONE);
	    		break;
	    	
	    	case "lineapregressa":
	    		output = "AggiungiLineaPregressa";
	    		tipoPratica = String.valueOf(GestioneAnagraficaAction.OP_AGGIUNGI_LINEA_PREGRESSA);
	    		id_causale = "2";
	    		break;
	    	
	    	case "modifylinee":
	    		//modifica linea attivita scia
	    		output = "LineeModify";
	    		tipoPratica = String.valueOf(GestioneAnagraficaAction.OP_ERRATA_CORRIGE_MOD_LINEE);
	    		id_causale = "2";
	    		break;
	    	
	    	case "modifylineanoscia":
	    		//modifica linea attivita noscia
	    		output = "LineaNosciaModify";
	    		tipoPratica = String.valueOf(GestioneAnagraficaAction.OP_ERRATA_CORRIGE_MOD_LINEE);
	    		id_causale = "2";
	    		break;
	    		
	    	case "trasferimentoSede":
	    		Comune c = new Comune(stab.getSedeOperativa().getComune());
	    		c.getDatiById(db, stab.getSedeOperativa().getComune());
    	        context.getRequest().setAttribute("provincia_id_stabilimento", c.getCod_provincia());
    	        context.getRequest().setAttribute("provincia_stabilimento", c.getDesc_provincia());
    	        context.getRequest().setAttribute("comune_id_stabilimento", Integer.valueOf(c.getId()));
    	        context.getRequest().setAttribute("comune_stabilimento", c.getNome());
    	             
    			output = "TrasferimentoSede";
    			tipoPratica = String.valueOf(GestioneAnagraficaAction.OP_MODIFICA_TRASFERIMENTO_SEDE);
	    		
	    		break;
	    		
	    	case "variazioneSedeLegale":
	    		 //verificare il riempimento dell'indirizzo della sede legale... TEST
	    		 String toponimo = stab.getOperatore().getSedeLegale().getDescrizioneToponimo();
	    		 String via = stab.getOperatore().getSedeLegale().getVia();
	    		 String civico = stab.getOperatore().getSedeLegale().getCivico();
	    		 String cap = stab.getOperatore().getSedeLegale().getCap();
	    		 String provincia_id = stab.getOperatore().getSedeLegale().getProvincia();
	    		 String provincia = stab.getOperatore().getSedeLegale().getDescrizione_provincia();
	    		 String comune_id = String.valueOf(stab.getOperatore().getSedeLegale().getComune());
	    		 String comune = stab.getOperatore().getSedeLegale().getDescrizioneComune(); //dovrebbe derivare da Comuni1
	    		 String latitudine = String.valueOf(stab.getOperatore().getSedeLegale().getLatitudine());
	    		 String longitudine = String.valueOf(stab.getOperatore().getSedeLegale().getLongitudine());

	    		 context.getRequest().setAttribute("toponimo_sede_legale", toponimo);
	 	         context.getRequest().setAttribute("via_sede_legale", via);
	 	         context.getRequest().setAttribute("civico_sede_legale", civico);
	 	         context.getRequest().setAttribute("cap_sede_legale", cap);
	 	         context.getRequest().setAttribute("provincia_id_sede_legale", provincia_id);
	 	         context.getRequest().setAttribute("provincia_sede_legale",provincia);
	 	         context.getRequest().setAttribute("comune_id_sede_legale", comune_id);
	 	         context.getRequest().setAttribute("comune_sede_legale",comune); 	        	
	 	         context.getRequest().setAttribute("latitudine_sede_legale", latitudine);
	 	         context.getRequest().setAttribute("longitudine_sede_legale",longitudine);
	 	        
	 			output = "CambioSedeLegale";
	 			tipoPratica = String.valueOf(GestioneAnagraficaAction.OP_VARIAZIONE_SEDE_LEGALE);
	    		break;
	    		
	    	case "ampliamentoFisico":
	    		output = "AmpliamentoFisico";
	    		tipoPratica = String.valueOf(GestioneAnagraficaAction.OP_AMPLIAMENTO_LUOGHI);
	    		break;
	    		
	    	case "trasformazione":
	    		//solo per stabilimenti con linea panificio
	    		output = "Trasformazione";
	    		tipoPratica = String.valueOf(GestioneAnagraficaAction.OP_TRASFORMAZIONE);
	    		break;
	    }
  
     
    }catch (SQLException e) {
    	e.printStackTrace();
    } finally {
        this.freeConnection(context, db);
    }
    
    context.getRequest().setAttribute("id_causale", id_causale);
    context.getRequest().setAttribute("tipoPratica", tipoPratica);
    
    return output; 
}

public String executeCommandTemplateVariazione(ActionContext context) throws IndirizzoNotFoundException{
	
	int altId = -1;
	int stabId = -1;
	int idAsl = -1;
	
	try {altId = Integer.parseInt(context.getRequest().getParameter("altId"));} catch (Exception e){}
	context.getRequest().setAttribute("altId", String.valueOf(altId));
	
	try {stabId = Integer.parseInt(context.getRequest().getParameter("stabId"));} catch (Exception e){}
	context.getRequest().setAttribute("stabId", String.valueOf(stabId));
	
	String numeroPratica = "";
	String tipoPratica = "";
	String dataPratica = "";
	int comunePratica = -1;
	String id_causale = "-1";
	
	try {numeroPratica = context.getRequest().getParameter("numeroPratica");} catch (Exception e){}
    context.getRequest().setAttribute("numeroPratica", numeroPratica);
    
    try{ tipoPratica = context.getRequest().getParameter("tipoPratica");} catch (Exception e){}
    if(tipoPratica == null){
    	tipoPratica = String.valueOf(GestioneAnagraficaAction.OP_VARIAZIONE);
    }
    context.getRequest().setAttribute("tipoPratica", tipoPratica);
    
    try{ dataPratica = context.getRequest().getParameter("dataPratica");} catch (Exception e){}
    context.getRequest().setAttribute("dataPratica", dataPratica);
    
    try{ comunePratica = Integer.parseInt(context.getRequest().getParameter("comunePratica"));} catch (Exception e){}
    context.getRequest().setAttribute("comunePratica", String.valueOf(comunePratica));
	
    try {id_causale = context.getRequest().getParameter("causalePratica");} catch (Exception e){}

    if(id_causale == null){
    	id_causale = "-1"; 
    }
    context.getRequest().setAttribute("id_causale", id_causale);
    
	Connection db = null;  
    
    try{
        db = this.getConnection(context); 
        
        Stabilimento stab = null;
        
        if (stabId>0)
        	stab = new Stabilimento(db, stabId);
        else if (altId>0)
        	stab = new Stabilimento(db, altId, true);
        
        Comune c = new Comune();
        c.getDatiById(db,comunePratica);

        if(comunePratica != -1){
        	c.getDatiById(db,comunePratica);
        	context.getRequest().setAttribute("comuneTesto", c.getNome());
        	context.getRequest().setAttribute("idprovinciain", c.getCod_provincia());
        	context.getRequest().setAttribute("desc_provincia", c.getDesc_provincia());
        }        
        
		context.getRequest().setAttribute("StabilimentoDettaglio", stab);
		context.getRequest().setAttribute("idAsl", String.valueOf(stab.getIdAsl()));
		try {idAsl = stab.getIdAsl();} catch (Exception e){}
        
        String codice = "TEST-SCIA";

        String desc_linea = "";
        //CONTROLLO SE QUELLA LINEA E' NO SCIA
    	LineaProduttiva lin = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
    	if(lin.getFlags().isNoScia())
    	{
    		codice = lin.getCodice();
    		desc_linea = lin.getNorma() + "-> " + lin.getDescrizione_linea_attivita();
    	} else {
    		codice = stab.RecuperaTemplateOsaDaLinea(db, lin);
    		if(codice.equalsIgnoreCase("") && stab.getTipoAttivita() == 1){
    			codice = "SCIA-FISSO";
    		} else if(codice.equalsIgnoreCase("") && stab.getTipoAttivita() == 2){
    			codice = "SCIA-MOBILE";
    		}
    	}
        
    	System.out.println("codice linea template: " +  codice);
        context.getRequest().setAttribute("codiceLinea", codice);
        LineaAttivita linea = new LineaAttivita(desc_linea, codice);
      
        //cerco tutti i componenti della GUI della linea di attivita
        linea.cercaMetadati(db); 
        List<Metadato> listaElementi = new ArrayList<Metadato>();
        listaElementi = linea.getMetadati();
        context.getRequest().setAttribute("lineaattivita", listaElementi);
        context.getRequest().setAttribute("id_asl_stab", idAsl);
        context.getRequest().setAttribute("idAggiuntaPratica", System.currentTimeMillis() + (new Random().nextInt(10000)));
        
        //nel caso di osa senza sede fissa
        if(stab.getTipoAttivita() == 2){
        	context.getRequest().setAttribute("tipo_impresa", String.valueOf(stab.getOperatore().getTipo_societa() ));
            return "CambioSoggettoFisico";
        }
        
        LinkedHashMap<String, String> valoriAnagrafica = new LinkedHashMap<String, String>();
        valoriAnagrafica = stab.getValoriAnagraficaModifica(db,altId);
        context.getRequest().setAttribute("ValoriAnagrafica", valoriAnagrafica);
     

    }catch (SQLException e) {
    	e.printStackTrace();
    } finally {
        this.freeConnection(context, db);
    }
    
    return "VariazioneTitolarita";
}


public String executeCommandAmpliamento(ActionContext context) throws IndirizzoNotFoundException, IOException{
	
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
	String numeroPratica = "";
	int idTipologiaPratica = -1;
	int idComunePratica = -1;
	
	parameterMap.get("numeroPratica")[0].trim();
	parameterMap.get("idTipologiaPratica");
	
	if(parameterMap.get("idTipologiaPratica")[0].trim().equalsIgnoreCase("")){
		idTipologiaPratica = -1;
	}else{
		idTipologiaPratica = Integer.parseInt(parameterMap.get("idTipologiaPratica")[0].trim());
	}
	
	if(parameterMap.get("numeroPratica")[0].trim().equalsIgnoreCase("")){
		numeroPratica = null;
	} else {
		numeroPratica = parameterMap.get("numeroPratica")[0].trim();
	}
	
	if(parameterMap.get("idComunePratica")[0].trim().equalsIgnoreCase("")){
		idComunePratica = -1;
	}else{
		idComunePratica = Integer.parseInt(parameterMap.get("idComunePratica")[0].trim());
	}
	
	
	for (String key: parameterMap.keySet()){
		valore_campo = parameterMap.get(key)[0].trim();
		chiave_campo = key.trim();
		if (chiave_campo.startsWith("_b_"))
		{
			campiFissi.put(chiave_campo.replaceFirst("_b_", ""), valore_campo);
		}
		
	}

	Connection db = null;
    
    try{
        db = this.getConnection(context);
        
        OggettoPerStorico stab_pre_modifica = new OggettoPerStorico(altId, db);
        OperazioneSuOsa osa = new OperazioneSuOsa();
        String risultato_query="";
        risultato_query = osa.insertAmpliamento(db, campiFissi, campiEstesi,getUserId(context), altId, idTipologiaPratica,numeroPratica,idComunePratica);
        
        EsitoOperazione esitoOperazione = new EsitoOperazione(risultato_query);
  		
  		if(esitoOperazione.getEsito()==1){
  			
  			//Propagazione
	        if (idTipologiaPratica==OP_NUOVO_STAB || idTipologiaPratica==OP_AMPLIAMENTO){
	        	GestoreComunicazioniBdu gBdu = new GestoreComunicazioniBdu();
	        	gBdu.inserisciNuovaSciaBdu(esitoOperazione.getIdStabilimento());
	        }
  			
  			allega_file_errata_corrige(context, db, esitoOperazione.getIdStabilimento());
  			stab_pre_modifica.salvaStoricoEvento(db,esitoOperazione.getIdStabilimento(),stab_pre_modifica);
  			
  			context.getRequest().setAttribute("id_stabilimento", String.valueOf(esitoOperazione.getIdStabilimento()));
  			
  			return "DetailsInsertOK";
  			
  		}else{
  			context.getRequest().setAttribute("erroreInsert",esitoOperazione.getMessaggio());
  			return "ErrorPageInsert";
  		}
        

    }catch (SQLException e) {
    	e.printStackTrace();
    	context.getRequest().setAttribute("erroreInsert", 
				"Errore in fase di aggiunzione linea: contattare hd I livello!!!<br>" + e.toString());
		return "ErrorPageInsert";
    } finally {
        this.freeConnection(context, db);
    }
     
}



public String executeCommandVisualizzaStoricoEvento(ActionContext context){
	
	int id_evento = -1;
	int id_tipo_evento = -1;
	
	try {id_evento = Integer.parseInt(context.getRequest().getParameter("id_evento"));} catch (Exception e){}
	context.getRequest().setAttribute("id_evento", String.valueOf(id_evento));
	
	try {id_tipo_evento = Integer.parseInt(context.getRequest().getParameter("id_tipo_evento"));} catch (Exception e){}
	context.getRequest().setAttribute("id_tipo_evento", String.valueOf(id_tipo_evento));
	
	Connection db = null;
    try{
	    db = this.getConnection(context);
		
		//recupero gli stream tramite query sul db per l'evento passato
        OperazioneSuOsa osa = new OperazioneSuOsa();
        osa.getDettaglioEvento(db, id_evento);
			
		OggettoPerStorico oggetto_stab_pre_modifica = new OggettoPerStorico();
		oggetto_stab_pre_modifica.streamToOggetto(osa.getStab_pre_modifica());
		OggettoPerStorico oggetto_stab_post_modifica = new OggettoPerStorico();
		oggetto_stab_post_modifica.streamToOggetto(osa.getStab_post_modifica());
		
		context.getSession().setAttribute("stab_pre_modifica", oggetto_stab_pre_modifica);
		context.getSession().setAttribute("stab_post_modifica", oggetto_stab_post_modifica);
		
		return "SpecificaStoricoOperazione";
	}catch (SQLException e) {
		e.printStackTrace();
		context.getRequest().setAttribute("erroreInsert", 
				"Errore in fase di cessazione: contattare hd I livello!!!<br>" + e.toString());
		return "ErrorPageInsert";
	}finally {
	    this.freeConnection(context, db);
	}
}

public String executeCommandTemplateDetailsStorico(ActionContext context) throws IndirizzoNotFoundException{
	
	int tipo_oggetto_da_mostrare = -1;
	
	try {tipo_oggetto_da_mostrare =  Integer.parseInt(context.getRequest().getParameter("oggetto_storico_stab"));} catch (Exception e){}
	
	OggettoPerStorico stab_da_mostrare = null;
	
	if(tipo_oggetto_da_mostrare == 0){
		stab_da_mostrare = (OggettoPerStorico) context.getSession().getAttribute("stab_pre_modifica");
		context.getSession().removeAttribute("stab_pre_modifica");
	} else if (tipo_oggetto_da_mostrare == 1){
		stab_da_mostrare = (OggettoPerStorico) context.getSession().getAttribute("stab_post_modifica");
		context.getSession().removeAttribute("stab_post_modifica");
	}
	
	context.getRequest().setAttribute("altId", String.valueOf(stab_da_mostrare.getAltId()));
	context.getRequest().setAttribute("descrizione_asl_print", stab_da_mostrare.getDescrizione_asl_print());
	context.getRequest().setAttribute("tipo_attivita_stabilimento", stab_da_mostrare.getTipo_attivita_stabilimento());
	context.getRequest().setAttribute("ValoriAnagrafica", stab_da_mostrare.getValoriAnagrafica());
	context.getRequest().setAttribute("ValoriLinee", stab_da_mostrare.getValoriLinee());
	context.getRequest().setAttribute("ValoriExtra", stab_da_mostrare.getValoriExtra());
	context.getRequest().setAttribute("ValoriEstesi", stab_da_mostrare.getValoriEstesi());
    context.getRequest().setAttribute("codiceLinea", stab_da_mostrare.getCodiceLinea());
    context.getRequest().setAttribute("lineaattivita", stab_da_mostrare.getLineaattivita());
    
    return "TemplateDetailsOK";
}	


public String executeCommandGestioneSospendiRiattiva(ActionContext context) throws IndirizzoNotFoundException, IOException{
	
	int altId = -1;
	int stabId = -1;
	
	try {altId = Integer.parseInt(context.getRequest().getParameter("altId"));} catch (Exception e){}
	context.getRequest().setAttribute("altId", String.valueOf(altId));
	
	try {stabId = Integer.parseInt(context.getRequest().getParameter("stabId"));} catch (Exception e){}
	context.getRequest().setAttribute("stabId", String.valueOf(stabId));
	
	Map<String, String[]> parameterMap = context.getRequest().getParameterMap();

	Map<String, String> campiFissi = new HashMap<String, String>();
	Map<String, String> campiEstesi = new HashMap<String, String>();
	String valore_campo;
	String chiave_campo;
	String numeroPratica = "";
	int idTipologiaPratica = -1;
	int idComunePratica = -1;
	
	parameterMap.get("numeroPratica")[0].trim();
	parameterMap.get("idTipologiaPratica");
	
	if(parameterMap.get("idTipologiaPratica")[0].trim().equalsIgnoreCase("")){
		idTipologiaPratica = -1;
	}else{
		idTipologiaPratica = Integer.parseInt(parameterMap.get("idTipologiaPratica")[0].trim());
	}
	
	if(parameterMap.get("numeroPratica")[0].trim().equalsIgnoreCase("")){
		numeroPratica = null;
	} else {
		numeroPratica = parameterMap.get("numeroPratica")[0].trim();
	}
	
	if(parameterMap.get("idComunePratica")[0].trim().equalsIgnoreCase("")){
		idComunePratica = -1;
	}else{
		idComunePratica = Integer.parseInt(parameterMap.get("idComunePratica")[0].trim());
	}
	
	
	for (String key: parameterMap.keySet()){
		valore_campo = parameterMap.get(key)[0].trim();
		chiave_campo = key.trim();
		if (chiave_campo.startsWith("_b_"))
		{
			campiFissi.put(chiave_campo.replaceFirst("_b_", ""), valore_campo);
		}
		
	}

	Connection db = null;
    
    try{
        db = this.getConnection(context);
        
        OggettoPerStorico stab_pre_modifica = new OggettoPerStorico(altId, db);
        OperazioneSuOsa osa = new OperazioneSuOsa();
        String risultato_query = osa.sospendiRiattiva(db,campiFissi,campiEstesi, getUserId(context),altId,idTipologiaPratica,numeroPratica,idComunePratica);
    	 		
  		EsitoOperazione esitoOperazione = new EsitoOperazione(risultato_query);
  		
  		if(esitoOperazione.getEsito()==1){

  			allega_file_errata_corrige(context, db, esitoOperazione.getIdStabilimento());
  			stab_pre_modifica.salvaStoricoEvento(db,esitoOperazione.getIdStabilimento(),stab_pre_modifica);
  			
  			context.getRequest().setAttribute("id_stabilimento", String.valueOf(esitoOperazione.getIdStabilimento()));
  			return "DetailsInsertOK";
  		}else{
  			context.getRequest().setAttribute("erroreInsert",esitoOperazione.getMessaggio());
  			return "ErrorPageInsert";
  		}
        

    }catch (SQLException e) {
    	e.printStackTrace();
    	context.getRequest().setAttribute("erroreInsert", 
				"Errore in fase di variazione linee: contattare hd I livello!!!<br>" + e.toString());
		return "ErrorPageInsert";
    } finally {
        this.freeConnection(context, db);
    }
     
}

public String executeCommandCessazione(ActionContext context) throws IndirizzoNotFoundException, IOException{
	
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
	String numeroPratica = "";
	int idTipologiaPratica = -1;
	int idComunePratica = -1;
	
	parameterMap.get("numeroPratica")[0].trim();
	parameterMap.get("idTipologiaPratica");
	
	if(parameterMap.get("idTipologiaPratica")[0].trim().equalsIgnoreCase("")){
		idTipologiaPratica = -1;
	}else{
		idTipologiaPratica = Integer.parseInt(parameterMap.get("idTipologiaPratica")[0].trim());
	}
	
	if(parameterMap.get("numeroPratica")[0].trim().equalsIgnoreCase("")){
		numeroPratica = null;
	} else {
		numeroPratica = parameterMap.get("numeroPratica")[0].trim();
	}
	
	if(parameterMap.get("idComunePratica")[0].trim().equalsIgnoreCase("")){
		idComunePratica = -1;
	}else{
		idComunePratica = Integer.parseInt(parameterMap.get("idComunePratica")[0].trim());
	}
	
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
	
	for (String key: parameterMap.keySet()){
		valore_campo = parameterMap.get(key)[0].trim();
		chiave_campo = key.trim();
		if (chiave_campo.startsWith("_b_"))
		{
			campiFissi.put(chiave_campo.replaceFirst("_b_", ""), valore_campo);
		}
		
	}


	Connection db = null;
    
    try{
        db = this.getConnection(context);

        OggettoPerStorico stab_pre_modifica = new OggettoPerStorico(altId, db);
        OperazioneSuOsa osa = new OperazioneSuOsa();
        String risultato_query = osa.cessazioneGestioneAnagrafica(db,campiFissi,campiEstesi, getUserId(context), altId, idTipologiaPratica,numeroPratica,idComunePratica);
  		EsitoOperazione esitoOperazione = new EsitoOperazione(risultato_query);
  		
  		if(esitoOperazione.getEsito()==1){
  			
  			allega_file_errata_corrige(context, db, esitoOperazione.getIdStabilimento());
  			stab_pre_modifica.salvaStoricoEvento(db,esitoOperazione.getIdStabilimento(),stab_pre_modifica);
  			
  			context.getRequest().setAttribute("id_stabilimento", String.valueOf(esitoOperazione.getIdStabilimento()));
  			return "DetailsInsertOK";
  			
  		}else{
  			context.getRequest().setAttribute("erroreInsert",esitoOperazione.getMessaggio());
  			return "ErrorPageInsert";
  		} 

    }catch (SQLException e) {
    	e.printStackTrace();
    	context.getRequest().setAttribute("erroreInsert", 
				"Errore in fase di cessazione: contattare hd I livello!!!<br>" + e.toString());
		return "ErrorPageInsert";
    } finally {
        this.freeConnection(context, db);
    }
     
}

public String executeCommandVariazioneTitolarita(ActionContext context) throws IndirizzoNotFoundException, IOException{
	
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
		else if (chiave_campo.equalsIgnoreCase("alt_id"))
		{
			if(valore_campo.equalsIgnoreCase("")){
				valore_campo = "-1";
			}
			altId = Integer.parseInt(valore_campo);
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

	Connection db = null;
    
    try{
        db = this.getConnection(context);
        
        OggettoPerStorico stab_pre_modifica = new OggettoPerStorico(altId, db);

        OperazioneSuOsa osa = new OperazioneSuOsa();
        String risultato_query="";
        risultato_query = osa.insertVariazioneTitolarita(db, campiFissi, campiEstesi,getUserId(context), altId, idTipologiaPratica,numeroPratica,idComunePratica);
        
  		EsitoOperazione esitoOperazione = new EsitoOperazione(risultato_query);
  		
  		if(esitoOperazione.getEsito()==1){
  			
  			org.aspcfs.modules.opu.base.Stabilimento stab = null;
  			stab = new org.aspcfs.modules.opu.base.Stabilimento(db, esitoOperazione.getIdStabilimento());
  	        GestoreComunicazioniBdu gBdu = new GestoreComunicazioniBdu();
  			gBdu.variazioneOperatoreBduGestioneAnagrafica(esitoOperazione.getIdStabilimento(), stab, getUserId(context));
  			
  			allega_file_errata_corrige(context, db, esitoOperazione.getIdStabilimento());
  			stab_pre_modifica.salvaStoricoEvento(db,esitoOperazione.getIdStabilimento(),stab_pre_modifica);
  			
  			context.getRequest().setAttribute("id_stabilimento", String.valueOf(esitoOperazione.getIdStabilimento()));
  			return "DetailsInsertOK";
  			
  		}else{
  			context.getRequest().setAttribute("erroreInsert",esitoOperazione.getMessaggio());
  			return "ErrorPageInsert";
  		}

    }catch (SQLException e) {
    	e.printStackTrace();
    	context.getRequest().setAttribute("erroreInsert", 
				"Errore in fase di variazione titolarita: contattare hd I livello!!!<br>" + e.toString());
		return "ErrorPageInsert";
    } finally {
        this.freeConnection(context, db);
    }
     
}


public String executeCommandVariazioneTitolaritaCambioSoggettoFisico(ActionContext context) throws IndirizzoNotFoundException, IOException{
	
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
		else if (chiave_campo.equalsIgnoreCase("alt_id"))
		{
			if(valore_campo.equalsIgnoreCase("")){
				valore_campo = "-1";
			}
			altId = Integer.parseInt(valore_campo);
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

	Connection db = null;
    
    try{
        db = this.getConnection(context);

        OggettoPerStorico stab_pre_modifica = new OggettoPerStorico(altId, db);
        OperazioneSuOsa osa = new OperazioneSuOsa();
        String risultato_query = "";
        risultato_query= osa.insertVariazioneTitolaritaCambioSoggetto(db, campiFissi, campiEstesi,getUserId(context), altId, idTipologiaPratica,numeroPratica,idComunePratica);
        
  		EsitoOperazione esitoOperazione = new EsitoOperazione(risultato_query);
  	
  		
  		if(esitoOperazione.getEsito()==1){
  			
  			allega_file_errata_corrige(context, db, esitoOperazione.getIdStabilimento());
  			stab_pre_modifica.salvaStoricoEvento(db,esitoOperazione.getIdStabilimento(),stab_pre_modifica);
  			
  			context.getRequest().setAttribute("id_stabilimento", String.valueOf(esitoOperazione.getIdStabilimento()));
  			return "DetailsInsertOK";
  			
  		}else{
  			context.getRequest().setAttribute("erroreInsert",esitoOperazione.getMessaggio());
  			return "ErrorPageInsert";
  		}

    }catch (SQLException e) {
    	e.printStackTrace();
    	context.getRequest().setAttribute("erroreInsert", 
				"Errore in fase di variazione titolarita cambio soggetto fisico: contattare hd I livello!!!<br>" + e.toString());
		return "ErrorPageInsert";
    } finally {
        this.freeConnection(context, db);
    }
     
}


public String executeCommandImportDistributori(ActionContext context) throws SQLException, IOException {
	HttpServletRequest req = context.getRequest();
	String action = req.getParameter("action");
	Connection db = null;
	
	MultipartRequest multi = null;
	
	int maxUploadSize = 50000000;
	
//	String filePath = this.getPath(context, "riunioni");
	
	String filePath = getWebInfPath(context,"tmp_riunioni");

	multi = new MultipartRequest(req, filePath, maxUploadSize,"UTF-8");

	UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
	String stabId = multi.getParameter("stabId");
	int stabIdInt = Integer.parseInt(stabId);
	int lda_rel_stab = Integer.parseInt(multi.getParameter("ldaStabId"));
	int lda_macroarea = Integer.parseInt(multi.getParameter("ldaMacroId"));
	InvioMassivoDistributori invioMassivo = new InvioMassivoDistributori();
	try {

		db = getConnection(context);
		
		
			
		File myFileT = multi.getFile("file1");
		FileInputStream fiStream = new FileInputStream(myFileT);
		// BufferedReader input = new BufferedReader(new
		// FileReader(myFileT));

		BufferedReader input = new BufferedReader(new FileReader(myFileT));

		
		invioMassivo.setInviato_da(thisUser.getUserId());
		invioMassivo.setData(LeggiFile.getData());
		invioMassivo.setStab_id(stabIdInt);
		invioMassivo.setId_rel_stab_lp(lda_rel_stab);
	
		 LeggiFile.leggiCampiDistributoriOpuCSV(context, db, myFileT, thisUser.getUserId(), lda_macroarea, lda_rel_stab, stabIdInt,invioMassivo,lda_macroarea);
		
		 ArrayList<InvioMassivoDistributori> listaInvii = invioMassivo.getListaImportDistributori(db, context);
		 context.getRequest().setAttribute("listaInvii", listaInvii);
		 ArrayList<Distrubutore> listaRecordKO = invioMassivo.getAllRecordsKo(db);
		 context.getRequest().setAttribute("listaRecordKO", listaRecordKO);

	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ParseException e) {
		
		if(invioMassivo.getId()>0)
		{
			invioMassivo.setEsito("KO - "+e.getMessage());
			invioMassivo.aggiornaEsito(db);
		}
		
	} catch (InvalidFileException e) {
		
		invioMassivo.setEsito("KO - "+e.getMessage());
		invioMassivo.aggiornaEsito(db);
		
		e.printStackTrace();
		
		context.getRequest().setAttribute("Error", e.getMessage());
	}finally {
		this.freeConnection(context, db);
	}
	
	
	context.getRequest().setAttribute("idStab", stabIdInt);
	context.getRequest().setAttribute("stabId", stabIdInt);
	context.getRequest().setAttribute("InvioMassivo", invioMassivo);
	return executeCommandGestioneMobile(context);

}



public String executeCommandTrasferimentoSedeOperativa(ActionContext context) throws IndirizzoNotFoundException, IOException{
	
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
		else if (chiave_campo.equalsIgnoreCase("alt_id"))
		{
			if(valore_campo.equalsIgnoreCase("")){
				valore_campo = "-1";
			}
			altId = Integer.parseInt(valore_campo);
		}	
	}
	
	parameterMap.get("numeroPratica")[0].trim();
	parameterMap.get("idTipologiaPratica");
	
	if(parameterMap.get("idTipologiaPratica")[0].trim().equalsIgnoreCase("")){
		idTipologiaPratica = -1;
	}else{
		idTipologiaPratica = Integer.parseInt(parameterMap.get("idTipologiaPratica")[0].trim());
	}
	
	if(parameterMap.get("numeroPratica")[0].trim().equalsIgnoreCase("")){
		numeroPratica = null;
	} else {
		numeroPratica = parameterMap.get("numeroPratica")[0].trim();
	}
	
	if(parameterMap.get("idComunePratica")[0].trim().equalsIgnoreCase("")){
		idComunePratica = -1;
	}else{
		idComunePratica = Integer.parseInt(parameterMap.get("idComunePratica")[0].trim());
	}

	Connection db = null;
    
    try{
        db = this.getConnection(context);
        OggettoPerStorico stab_pre_modifica = new OggettoPerStorico(altId, db);
        OperazioneSuOsa osa = new OperazioneSuOsa();
        String risultato_query = "";
        risultato_query= osa.insertTrasferimentoSedeOperativa(db, campiFissi, campiEstesi,getUserId(context), altId, idTipologiaPratica,numeroPratica,idComunePratica);
     
  		EsitoOperazione esitoOperazione = new EsitoOperazione(risultato_query);
  		
  		if(esitoOperazione.getEsito()==1){
  			
  			org.aspcfs.modules.opu.base.Stabilimento stab_old = new org.aspcfs.modules.opu.base.Stabilimento(db, altId, true);
	        
	        org.aspcfs.modules.opu.base.Stabilimento stab_new = new org.aspcfs.modules.opu.base.Stabilimento(db, esitoOperazione.getIdStabilimento());
	        
	        org.aspcfs.modules.gestioneanagrafica.utils.GestioneAnagraficaInvioMail.invioMailTrasferimentoSedeStabilimento(db,stab_old,stab_new,getUserId(context));
  			
	        allega_file_errata_corrige(context, db, esitoOperazione.getIdStabilimento());
	        stab_pre_modifica.salvaStoricoEvento(db,esitoOperazione.getIdStabilimento(),stab_pre_modifica); //verificare la chiamata
	        
	        context.getRequest().setAttribute("id_stabilimento", String.valueOf(esitoOperazione.getIdStabilimento()));
  			//return executeCommandDetails(context);
  			return "DetailsInsertOK";
	        
  		}else{
  			context.getRequest().setAttribute("erroreInsert",esitoOperazione.getMessaggio());
  			return "ErrorPageInsert";
  		} 

    }catch (SQLException e) {
    	e.printStackTrace();
    	context.getRequest().setAttribute("erroreInsert", 
				"Errore in fase di traferimento sede operativa: contattare hd I livello!!!<br>" + e.toString());
		return "ErrorPageInsert";
    } finally {
        this.freeConnection(context, db);
    }
     
}

public String executeCommandCambioSedeLegale(ActionContext context) throws IndirizzoNotFoundException, IOException{
	
	int altId = -1;
	int stabId = -1;
	
	try {altId = Integer.parseInt(context.getRequest().getParameter("altId"));} catch (Exception e){}
	context.getRequest().setAttribute("altId", String.valueOf(altId));
	
	try {stabId = Integer.parseInt(context.getRequest().getParameter("stabId"));} catch (Exception e){}
	context.getRequest().setAttribute("stabId", String.valueOf(stabId));
	
	Map<String, String[]> parameterMap = context.getRequest().getParameterMap();

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
		else if (chiave_campo.equalsIgnoreCase("alt_id"))
		{
			if(valore_campo.equalsIgnoreCase("")){
				valore_campo = "-1";
			}
			altId = Integer.parseInt(valore_campo);
		}	
	}
	
	
	if(parameterMap.get("idTipologiaPratica")[0].trim().equalsIgnoreCase("")){
		idTipologiaPratica = -1;
	}else{
		idTipologiaPratica = Integer.parseInt(parameterMap.get("idTipologiaPratica")[0].trim());
	}
	
	if(parameterMap.get("numeroPratica")[0].trim().equalsIgnoreCase("")){
		numeroPratica = null;
	} else {
		numeroPratica = parameterMap.get("numeroPratica")[0].trim();
	}
	
	if(parameterMap.get("idComunePratica")[0].trim().equalsIgnoreCase("")){
		idComunePratica = -1;
	}else{
		idComunePratica = Integer.parseInt(parameterMap.get("idComunePratica")[0].trim());
	}

	Connection db = null;
    
    try{
        db = this.getConnection(context);
        
        org.aspcfs.modules.opu.base.Stabilimento stab = null;
        
        if (stabId>0)
        	stab = new org.aspcfs.modules.opu.base.Stabilimento(db, stabId);
        else if (altId>0)
        	stab = new org.aspcfs.modules.opu.base.Stabilimento(db, altId, true);

        //recupero dati vecchia sede legale
        String oldAslid = String.valueOf(stab.getSedeOperativa().getIdAsl());
        String oldAslDesc = stab.getSedeOperativa().getDescrizioneAsl();
        //recuperare i dati indirizzo da opu_operatore e non da stabilimento
        String oldIndirizzoSedeLegale = "";

        String toponimo = stab.getOperatore().getSedeLegale().getDescrizioneToponimo();
		String via = stab.getOperatore().getSedeLegale().getVia();
		String civico = stab.getOperatore().getSedeLegale().getCivico();
		String cap = stab.getOperatore().getSedeLegale().getCap();
		String provincia = stab.getOperatore().getSedeLegale().getDescrizione_provincia();
		String comune = stab.getOperatore().getSedeLegale().getDescrizioneComune(); //dovrebbe derivare da Comuni1

		oldIndirizzoSedeLegale = toponimo + " " + via + " " + civico + " " + cap + " " + comune + " " + provincia;
        
        OggettoPerStorico stab_pre_modifica = new OggettoPerStorico(altId, db);
        String risultato_query = "";
        OperazioneSuOsa osa = new OperazioneSuOsa();
        risultato_query= osa.insertCambioSedeLegale(db, campiFissi, campiEstesi,getUserId(context), altId, idTipologiaPratica,numeroPratica,idComunePratica);
     
  		EsitoOperazione esitoOperazione = new EsitoOperazione(risultato_query);
  		
  		if(esitoOperazione.getEsito()==1){
  			
  			//gestione invio mail   
	        stab = new org.aspcfs.modules.opu.base.Stabilimento(db, esitoOperazione.getIdStabilimento());
	        
	        //recupero dati nuova sede legale (li prendo da stabilimento perche per i senza sede fissa sono uguali a
	        //quelli dell operatore)
	        String ragione_sociale = stab.getOperatore().getRagioneSociale();
	        String num_registrazione = stab.getNumero_registrazione();
	        String newAslid = String.valueOf(stab.getSedeOperativa().getIdAsl());
	        String newAslDesc = stab.getSedeOperativa().getDescrizioneAsl();
	        //recuperare i dati indirizzo da opu_operatore e non da stabilimento
	        String newIndirizzoSedeLegale = "";

	        toponimo = stab.getOperatore().getSedeLegale().getDescrizioneToponimo();
			via = stab.getOperatore().getSedeLegale().getVia();
			civico = stab.getOperatore().getSedeLegale().getCivico();
			cap = stab.getOperatore().getSedeLegale().getCap();
			provincia = stab.getOperatore().getSedeLegale().getDescrizione_provincia();
			comune = stab.getOperatore().getSedeLegale().getDescrizioneComune(); //dovrebbe derivare da Comuni1

			newIndirizzoSedeLegale = toponimo + " " + via + " " + civico + " " + cap + " " + comune + " " + provincia;
	        //chiamo metodo invio mail
	        org.aspcfs.modules.gestioneanagrafica.utils.GestioneAnagraficaInvioMail.invioMailCambioSedeLegaleOperatore(db, ragione_sociale, oldAslid, oldAslDesc,
	        		oldIndirizzoSedeLegale, num_registrazione, newAslid, newAslDesc, newIndirizzoSedeLegale, getUserId(context));
	        
	        GestoreComunicazioniBdu gBdu = new GestoreComunicazioniBdu();
  			gBdu.variazioneOperatoreBduGestioneAnagrafica(esitoOperazione.getIdStabilimento(), stab, getUserId(context));
  			
	        allega_file_errata_corrige(context, db, esitoOperazione.getIdStabilimento());
	        stab_pre_modifica.salvaStoricoEvento(db,esitoOperazione.getIdStabilimento(),stab_pre_modifica);
	        
	        context.getRequest().setAttribute("id_stabilimento", String.valueOf(esitoOperazione.getIdStabilimento()));
  			
  			return "DetailsInsertOK";
	        
  		}else{
  			context.getRequest().setAttribute("erroreInsert",esitoOperazione.getMessaggio());
  			return "ErrorPageInsert";
  		} 

    }catch (SQLException e) {
    	e.printStackTrace();
    	context.getRequest().setAttribute("erroreInsert", 
				"Errore in fase di cambio sede legale: contattare hd I livello!!!<br>" + e.toString());
		return "ErrorPageInsert";
    } finally {
        this.freeConnection(context, db);
    }
     
}

public String executeCommandAmpliamentoFisico(ActionContext context) throws IndirizzoNotFoundException, IOException{
	
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
		else if (chiave_campo.equalsIgnoreCase("alt_id"))
		{
			if(valore_campo.equalsIgnoreCase("")){
				valore_campo = "-1";
			}
			altId = Integer.parseInt(valore_campo);
		}	
	}
	
	parameterMap.get("numeroPratica")[0].trim();
	parameterMap.get("idTipologiaPratica");
	
	if(parameterMap.get("idTipologiaPratica")[0].trim().equalsIgnoreCase("")){
		idTipologiaPratica = -1;
	}else{
		idTipologiaPratica = Integer.parseInt(parameterMap.get("idTipologiaPratica")[0].trim());
	}
	
	if(parameterMap.get("numeroPratica")[0].trim().equalsIgnoreCase("")){
		numeroPratica = null;
	} else {
		numeroPratica = parameterMap.get("numeroPratica")[0].trim();
	}
	
	if(parameterMap.get("idComunePratica")[0].trim().equalsIgnoreCase("")){
		idComunePratica = -1;
	}else{
		idComunePratica = Integer.parseInt(parameterMap.get("idComunePratica")[0].trim());
	}

	Connection db = null;
    
    try{
        db = this.getConnection(context);

        OggettoPerStorico stab_pre_modifica = new OggettoPerStorico(altId, db);
	 	String risultato_query = "";
	    OperazioneSuOsa osa = new OperazioneSuOsa();
	    risultato_query= osa.insertAmpliamentoFisico(db, campiFissi, campiEstesi,getUserId(context), altId, idTipologiaPratica,numeroPratica,idComunePratica);
	     
  		EsitoOperazione esitoOperazione = new EsitoOperazione(risultato_query);

  		if(esitoOperazione.getEsito()==1){
  			
  			allega_file_errata_corrige(context, db, esitoOperazione.getIdStabilimento());
  			stab_pre_modifica.salvaStoricoEvento(db,esitoOperazione.getIdStabilimento(),stab_pre_modifica);
  			
  			context.getRequest().setAttribute("id_stabilimento", String.valueOf(esitoOperazione.getIdStabilimento()));
  			
  			return "DetailsInsertOK";
  		}else{
  			context.getRequest().setAttribute("erroreInsert",esitoOperazione.getMessaggio());
  			return "ErrorPageInsert";
  		} 

    }catch (SQLException e) {
    	e.printStackTrace();
    	context.getRequest().setAttribute("erroreInsert", 
				"Errore in fase di AMPLIAMENTO FISICO DEL LOCALE: contattare hd I livello!!!<br>" + e.toString());
		return "ErrorPageInsert";
    } finally {
        this.freeConnection(context, db);
    }
     
}


public String executeCommandAddLineaPregressa(ActionContext context) throws IndirizzoNotFoundException, IOException{

	int altId = -1;
	int stabId = -1;
	
	try {altId = Integer.parseInt(context.getRequest().getParameter("altId"));} catch (Exception e){}
	context.getRequest().setAttribute("altId", String.valueOf(altId));
	
	try {stabId = Integer.parseInt(context.getRequest().getParameter("stabId"));} catch (Exception e){}
	context.getRequest().setAttribute("stabId", String.valueOf(stabId));
	
	int linea_attivita = -1;
	try {linea_attivita = Integer.parseInt(context.getRequest().getParameter("linea_attivita"));} catch (Exception e){}
	
	
	String data_inizio = context.getRequest().getParameter("data_inizio");
	if(data_inizio == null)
		data_inizio = "";
	
	String data_fine = context.getRequest().getParameter("data_fine");
	if(data_fine == null)
		data_fine = "";
	
	String cun = context.getRequest().getParameter("cun_linea_pregressa");
	if(cun == null){
		cun = "";
	}
	
	Map<String, String[]> parameterMap = context.getRequest().getParameterMap();
	Map<String, String> campiFissi = new HashMap<String, String>();
	String valore_campo;
	String chiave_campo;
	for (String key: parameterMap.keySet()){
		valore_campo = parameterMap.get(key)[0].trim();
		chiave_campo = key.trim();
		if (chiave_campo.startsWith("_b_"))
		{
			campiFissi.put(chiave_campo.replaceFirst("_b_", ""), valore_campo);
		}
		
	}
	
	
	Connection db = null;
	
    try{
        db = this.getConnection(context);
        
        OggettoPerStorico stab_pre_modifica = new OggettoPerStorico(altId, db);
	 	String risultato_query = "";
	    OperazioneSuOsa osa = new OperazioneSuOsa();
	    risultato_query = osa.insertLineaPregressa(db, campiFissi, stabId, linea_attivita, 
	    										   data_inizio, data_fine,cun.trim(), getUserId(context));
		
		EsitoOperazione esitoOperazione = new EsitoOperazione(risultato_query);

  		if(esitoOperazione.getEsito()==1){
  			
  			allega_file_errata_corrige(context, db, esitoOperazione.getIdStabilimento());
  			stab_pre_modifica.salvaStoricoEvento(db,esitoOperazione.getIdStabilimento(),stab_pre_modifica);
  			
  			context.getRequest().setAttribute("id_stabilimento", String.valueOf(esitoOperazione.getIdStabilimento()));
  			
  			return "DetailsInsertOK";
  		}else{
  			context.getRequest().setAttribute("erroreInsert",esitoOperazione.getMessaggio());
  			return "ErrorPageInsert";
  		} 

    }catch (SQLException e) {
    	e.printStackTrace();
    	context.getRequest().setAttribute("erroreInsert", 
				"Errore aggiungi linea pregressa: contattare hd I livello!!!<br>" + e.toString());
		return "ErrorPageInsert";
    } finally {
        this.freeConnection(context, db);
    }
     
}

public String executeCommandAddGestioneSospendiLinee(ActionContext context) throws IndirizzoNotFoundException {
	
	int altId = -1;
	int stabId = -1;
	ArrayList<LineaVariazione> listaLineeVariazione = new ArrayList<LineaVariazione>();
	
	try {altId = Integer.parseInt(context.getRequest().getParameter("altId"));} catch (Exception e){}
	context.getRequest().setAttribute("altId", String.valueOf(altId));
	
	try {stabId = Integer.parseInt(context.getRequest().getParameter("stabId"));} catch (Exception e){}
	context.getRequest().setAttribute("stabId", String.valueOf(stabId));
	
	String numeroPratica = "";
	String tipoPratica = "";
	String dataPratica = ""; 
	int comunePratica = -1;
	String id_causale = "-1";
	String output = "";
	
	try {numeroPratica = context.getRequest().getParameter("numeroPratica");} catch (Exception e){}
    context.getRequest().setAttribute("numeroPratica", numeroPratica);
    
    try{ tipoPratica = context.getRequest().getParameter("tipoPratica");} catch (Exception e){}
    
    
    try{ dataPratica = context.getRequest().getParameter("dataPratica");} catch (Exception e){}
    context.getRequest().setAttribute("dataPratica", dataPratica);
    
    try {comunePratica = Integer.parseInt(context.getRequest().getParameter("comunePratica"));} catch (Exception e){}
	context.getRequest().setAttribute("comunePratica", String.valueOf(comunePratica));
	
	try {id_causale = context.getRequest().getParameter("causalePratica");} catch (Exception e){}

    if(id_causale == null){
    	id_causale = "-1";
    }
	
	Connection db = null;
	    
	    try{
	        db = this.getConnection(context);
	        
	        org.aspcfs.modules.opu.base.Stabilimento stab = null;
	        
	        if (stabId>0)
	        	stab = new org.aspcfs.modules.opu.base.Stabilimento(db, stabId);
	        else if (altId>0)
	        	stab = new org.aspcfs.modules.opu.base.Stabilimento(db, altId, true);
	        
			context.getRequest().setAttribute("StabilimentoDettaglio", stab);
			
			LookupList ListaStati = new LookupList(db,"lookup_stato_lab");
			
			for(int i = 0; i < stab.getListaLineeProduttive().size(); i++ ){
				LineaProduttiva l = (LineaProduttiva) stab.getListaLineeProduttive().get(i);
				LineaVariazione lineaDaAggiungere;
				//in precedenza tale operazione era consentita solo per gli 852
				if (l.getStato() == 0){
					lineaDaAggiungere = new LineaVariazione(l.getId_rel_stab_lp(), l.getStato(), l.getDescrizione_linea_attivita(), stab.getIdStabilimento());
					lineaDaAggiungere.setLista_cu(db);
					lineaDaAggiungere.setId_stato_secondo_livello(db);
					lineaDaAggiungere.setDesc_stato_lp(ListaStati.getSelectedValue( l.getStato()));
					lineaDaAggiungere.setDataUltimaVariazione(db);
					if(lineaDaAggiungere.getDataUltimaVariazione().equalsIgnoreCase("")){
						lineaDaAggiungere.setDataUltimaVariazione(l.getDataInizioString());
					}
					listaLineeVariazione.add(lineaDaAggiungere);	
				}
			}
			
			context.getRequest().setAttribute("ListaLinee",listaLineeVariazione);
			
			LookupList ListaOperazioni = new LookupList(db,"lookup_variazione_stato_operazioni");
			context.getRequest().setAttribute("ListaOperazioni", ListaOperazioni);
			context.getRequest().setAttribute("id_causale", id_causale);
			tipoPratica = String.valueOf(GestioneAnagraficaAction.OP_SOSPENSIONE);
			context.getRequest().setAttribute("tipoPratica", tipoPratica);
			context.getRequest().setAttribute("idAggiuntaPratica", System.currentTimeMillis() + (new Random().nextInt(10000)));
			
	    }catch (SQLException e) {
	    	e.printStackTrace();
	    } finally {
	        this.freeConnection(context, db);
	    }
	
	return "GestioneSospendiLinee";
}

public String executeCommandAddGestioneRiattivaLinee(ActionContext context) throws IndirizzoNotFoundException {
	
	int altId = -1;
	int stabId = -1;
	ArrayList<LineaVariazione> listaLineeVariazione = new ArrayList<LineaVariazione>();
	
	try {altId = Integer.parseInt(context.getRequest().getParameter("altId"));} catch (Exception e){}
	context.getRequest().setAttribute("altId", String.valueOf(altId));
	
	try {stabId = Integer.parseInt(context.getRequest().getParameter("stabId"));} catch (Exception e){}
	context.getRequest().setAttribute("stabId", String.valueOf(stabId));
	
	String numeroPratica = "";
	String tipoPratica = "";
	String dataPratica = ""; 
	int comunePratica = -1;
	String id_causale = "-1";
	String output = "";
	
	try {numeroPratica = context.getRequest().getParameter("numeroPratica");} catch (Exception e){}
    context.getRequest().setAttribute("numeroPratica", numeroPratica);
    
    try{ tipoPratica = context.getRequest().getParameter("tipoPratica");} catch (Exception e){}
    
    
    try{ dataPratica = context.getRequest().getParameter("dataPratica");} catch (Exception e){}
    context.getRequest().setAttribute("dataPratica", dataPratica);
    
    try {comunePratica = Integer.parseInt(context.getRequest().getParameter("comunePratica"));} catch (Exception e){}
	context.getRequest().setAttribute("comunePratica", String.valueOf(comunePratica));
	
	try {id_causale = context.getRequest().getParameter("causalePratica");} catch (Exception e){}

    if(id_causale == null){
    	id_causale = "-1";
    }
	
	Connection db = null;
	    
	    try{
	        db = this.getConnection(context);
	        
	        org.aspcfs.modules.opu.base.Stabilimento stab = null;
	        
	        if (stabId>0)
	        	stab = new org.aspcfs.modules.opu.base.Stabilimento(db, stabId);
	        else if (altId>0)
	        	stab = new org.aspcfs.modules.opu.base.Stabilimento(db, altId, true);
	        
			context.getRequest().setAttribute("StabilimentoDettaglio", stab);
			
			LookupList ListaStati = new LookupList(db,"lookup_stato_lab");
			
			for(int i = 0; i < stab.getListaLineeProduttive().size(); i++ ){
				LineaProduttiva l = (LineaProduttiva) stab.getListaLineeProduttive().get(i);
				LineaVariazione lineaDaAggiungere;
				//in precedenza tale operazione era consentita solo per gli 852
				if (l.getStato() == 2){
					lineaDaAggiungere = new LineaVariazione(l.getId_rel_stab_lp(), l.getStato(), l.getDescrizione_linea_attivita(), stab.getIdStabilimento());
					lineaDaAggiungere.setLista_cu(db);
					lineaDaAggiungere.setId_stato_secondo_livello(db);
					lineaDaAggiungere.setDesc_stato_lp(ListaStati.getSelectedValue( l.getStato()));
					lineaDaAggiungere.setDataUltimaVariazione(db);
					if(lineaDaAggiungere.getDataUltimaVariazione().equalsIgnoreCase("")){
						lineaDaAggiungere.setDataUltimaVariazione(l.getDataInizioString());
					}
					
					if(id_causale.equalsIgnoreCase("1") && lineaDaAggiungere.getId_stato_secondo_livello() == 2){
						listaLineeVariazione.add(lineaDaAggiungere);	
					} else if(!id_causale.equalsIgnoreCase("1")){
						listaLineeVariazione.add(lineaDaAggiungere);
					}
					
				}
			}
			
			context.getRequest().setAttribute("ListaLinee",listaLineeVariazione);
			
			LookupList ListaOperazioni = new LookupList(db,"lookup_variazione_stato_operazioni");
			context.getRequest().setAttribute("ListaOperazioni", ListaOperazioni);
			context.getRequest().setAttribute("id_causale", id_causale);
			tipoPratica = String.valueOf(GestioneAnagraficaAction.OP_MODIFICA_RIATTIVAZIONE_LINEE_SOSPESE);
			context.getRequest().setAttribute("tipoPratica", tipoPratica);
			context.getRequest().setAttribute("idAggiuntaPratica", System.currentTimeMillis() + (new Random().nextInt(10000)));
			
	    }catch (SQLException e) {
	    	e.printStackTrace();
	    } finally {
	        this.freeConnection(context, db);
	    }
	
	return "GestioneRiattivaLinee";
}

public String executeCommandGestioneMobile(ActionContext context) 
{
	if (!hasPermission(context, "gestioneanagrafica-gestioneanagrafica-datiaggiuntivi-view")) 
	{
		return ("PermissionError");
	}
	Connection db = null;
	SystemStatus systemStatus = this.getSystemStatus(context);
	org.aspcfs.modules.opu.base.Stabilimento newStabilimento = null;
	try 
	{
		String tempAltId = context.getRequest().getParameter("altId");
		if (tempAltId == null) 
		{
			tempAltId = ""	+ (Integer) context.getRequest().getAttribute("altId");
		}
		Integer altId = null;
		try	{altId = Integer.parseInt(tempAltId);} catch (Exception e){}
		
		String tempStabId = context.getRequest().getParameter("stabId");
		if (tempStabId == null) 
		{
			tempStabId = ""	+ (Integer) context.getRequest().getAttribute("stabId");
		}
		Integer stabId = null;
		try	{stabId = Integer.parseInt(tempStabId);} catch (Exception e){}
		
		db = this.getConnection(context);	
		
		if (altId!=null && altId>0)
			newStabilimento = new org.aspcfs.modules.opu.base.Stabilimento(db,  altId,true);
		else if (stabId!=null && stabId > 0)
			newStabilimento = new org.aspcfs.modules.opu.base.Stabilimento(db,  stabId);
		
		newStabilimento.getPrefissoAction(context.getAction().getActionName());
		context.getRequest().setAttribute("StabilimentoDettaglio", newStabilimento);

		Operatore operatore = new Operatore () ;
		org.aspcf.modules.controlliufficiali.base.Organization org = new org.aspcf.modules.controlliufficiali.base.Organization();
		org.setName(operatore.getRagioneSociale());
		operatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());
		operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());	
		context.getRequest().setAttribute("Operatore", operatore);

		LookupList OperazioniList = new LookupList(db,"lookup_suap_operazioni");
		context.getRequest().setAttribute("OperazioniList", OperazioniList);

		Storico sto = new Storico();
		Vector storicoList = new Vector();
		storicoList = sto.cercaStoricoPratica(db, newStabilimento.getIdStabilimento());
		context.getRequest().setAttribute("listaStorico", storicoList);

		context.getRequest().setAttribute("op", context.getRequest().getParameter("op"));
		context.getRequest().setAttribute("listaLineeProduttive", newStabilimento.getListaLineeProduttive());


  		return getReturn(context, "GestioneMobile");

	} catch (Exception e) {
		e.printStackTrace();
		// Go through the SystemError process
		context.getRequest().setAttribute("Error", e);
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}
}


public String executeCommandInserisciDettaglioMobile(ActionContext context) {
	if (!hasPermission(context, "gestioneanagrafica-gestioneanagrafica-datiaggiuntivi-add")) {
		return getReturn(context, "PermissionError");
	
	}
	Connection db = null;
	SystemStatus systemStatus = this.getSystemStatus(context);
	Stabilimento newStabilimento = null;
	try {
		String tempStabId = context.getRequest().getParameter("stabId");
		if (tempStabId == null) {
			tempStabId = ""	+ (Integer) context.getRequest().getAttribute("idStab");
		}
		Integer stabid = null;
		if (tempStabId != null) {
			stabid = Integer.parseInt(tempStabId);
		}
		
		String lda_macroarea=context.getRequest().getParameter("ldaMacroId");
		String lda_rel_stab=context.getRequest().getParameter("ldaStabId");
		
		db = this.getConnection(context);	
	

	LineeMobiliHtmlFields newModulo = new LineeMobiliHtmlFields();
	try {
		newModulo.insertDettaglioMobile( db, Integer.parseInt(lda_macroarea), Integer.parseInt(lda_rel_stab), stabid, context);
	}
	catch (Exception e) {
		context.getRequest().setAttribute("messaggioOk", "Errore inserimento dato. Controllare i valori inseriti. ");
		return executeCommandGestioneMobile(context);
	}		RicercheAnagraficheTab.inserOpu(db, stabid);
		
		
		//newPnaa.update(db, idCampione);	
		}catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return getReturn(context, "SystemError");

		} finally {
			this.freeConnection(context, db);
		}
	//context.getRequest().setAttribute("idCampione", String.valueOf(idCampione));
	//context.getRequest().setAttribute("orgId", orgId);
	//context.getRequest().setAttribute("tipo", tipoAnalita);
	context.getRequest().setAttribute("messaggioOk", "Inserimento avvenuto con successo.");
	//return getReturn(context, "AggiuntaDettaglioMobile");
	return executeCommandGestioneMobile(context);

}

public String executeCommandDismettiMobileDaLinea(ActionContext context) {
	if (!hasPermission(context, "datiaggiuntivi-edit")) {
		return ("PermissionError");
	}
	Connection db = null;
	SystemStatus systemStatus = this.getSystemStatus(context);
	String esito = "";
	//Stabilimento newStabilimento = null;
	try {
		//String tempStabId = context.getRequest().getParameter("stabId");
		//if (tempStabId == null) {
		//	tempStabId = ""	+ (Integer) context.getRequest().getAttribute("idStab");
		//}
		//Integer stabid = null;
		//if (tempStabId != null) {
		//	stabid = Integer.parseInt(tempStabId);
		//}
		//String lda_macroarea=context.getRequest().getParameter("ldaMacroId");
		String lda_rel_stab=context.getRequest().getParameter("ldaStabId");
		String indice=context.getRequest().getParameter("indice");
		String tempStabId=context.getRequest().getParameter("stabId");
		String note=context.getRequest().getParameter("note");
		String data=context.getRequest().getParameter("data");

		Integer stabid = null;
		if (tempStabId != null) 
		{
			stabid = Integer.parseInt(tempStabId);
		}
		
		db = this.getConnection(context);	
		esito = LineeMobiliHtmlFields.dismissioneDettaglioMobile(db,Integer.parseInt(indice),Integer.parseInt(lda_rel_stab), note, data, context);
		RicercheAnagraficheTab.inserOpu(db, stabid);

	}catch (Exception e) {
		e.printStackTrace();
		context.getRequest().setAttribute("Error", e);
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}
	context.getRequest().setAttribute("messaggioOk", esito);
	//return getReturn(context, "AggiuntaDettaglioMobile");
	return executeCommandGestioneMobile(context);
}



public String executeCommandModificaDettaglioMobile(ActionContext context) 
{
	if (!hasPermission(context, "gestioneanagrafica-gestioneanagrafica-datiaggiuntivi-add")) 
	{
		return getReturn(context, "PermissionError");
	
	}
	Connection db = null;
	SystemStatus systemStatus = this.getSystemStatus(context);
	Stabilimento newStabilimento = null;
	try 
	{
		String tempStabId = context.getRequest().getParameter("stabId");
		if (tempStabId == null) 
		{
			tempStabId = ""	+ (Integer) context.getRequest().getAttribute("idStab");
		}
		Integer stabid = null;
		if (tempStabId != null) 
		{
			stabid = Integer.parseInt(tempStabId);
		}
		
		String lda_macroarea=context.getRequest().getParameter("ldaMacroId");
		String lda_rel_stab=context.getRequest().getParameter("ldaStabId");
		
		db = this.getConnection(context);	
	

		LineeMobiliHtmlFields newModulo = new LineeMobiliHtmlFields();
		newModulo.updateDettaglioMobile( db, Integer.parseInt(lda_macroarea), Integer.parseInt(lda_rel_stab), stabid, context);
		RicercheAnagraficheTab.inserOpu(db, stabid);
		
		GestoreComunicazioniBdu gBdu = new GestoreComunicazioniBdu();
		gBdu.aggiornaDatiEstesi(Integer.parseInt(lda_rel_stab));
		
		//newPnaa.update(db, idCampione);	
		}catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return getReturn(context, "SystemError");

		} finally {
			this.freeConnection(context, db);
		}
	context.getRequest().setAttribute("messaggioOk", "Modifica avvenuta con successo.");
	return executeCommandGestioneMobile(context);
}


public String executeCommandPreparazioneLineaMobile(ActionContext context) {   
	if (!hasPermission(context, "gestioneanagrafica-gestioneanagrafica-datiaggiuntivi-add")) {
		return ("PermissionError");
	}
	Connection db = null;
	SystemStatus systemStatus = this.getSystemStatus(context);
	Stabilimento newStabilimento = null;
	UserBean thisUser = (UserBean) context.getSession().getAttribute("User"); 
	try {
		String tempStabId = context.getRequest().getParameter("stabId");
		if (tempStabId == null) {
			tempStabId = ""	+ (Integer) context.getRequest().getAttribute("idStab");
		}
		Integer stabid = null;
		if (tempStabId != null) {
			stabid = Integer.parseInt(tempStabId);
		}
		String lda_macroarea=context.getRequest().getParameter("ldaMacroId");
		String lda_rel_stab=context.getRequest().getParameter("ldaStabId");
		
		db = this.getConnection(context);	
		
		newStabilimento = new Stabilimento(db,  stabid);
		newStabilimento.getPrefissoAction(context.getAction().getActionName());
		context.getRequest().setAttribute("StabilimentoDettaglio", newStabilimento);
		
			
		Iterator it = newStabilimento.getListaLineeProduttive().iterator();
		while (it.hasNext())
		{
			LineaProduttiva lp =(LineaProduttiva) it.next();
			if(lp.getId()==Integer.parseInt(lda_macroarea))
			{
				context.getRequest().setAttribute("consentiUploadFile", lp.getConsentiUploadFile());
				context.getRequest().setAttribute("consentiValoriMultipli", lp.getConsentiValoriMultipli());
				break;
			}
			
		}

		Operatore operatore = new Operatore () ;
		operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());	
		context.getRequest().setAttribute("Operatore", operatore);
		
		

		
		//ComuniAnagrafica com = null ;
		int idComune = -1 ;

		String indirizzo_hid="";
		String comune_hid="";
		String provincia_hid="";
		String asl_hid="";
		String cap_hid="";

		
		//INDIVIDUALE
		if (operatore.getTipo_impresa()==1){
			//Mobile
			if (newStabilimento.getTipoAttivita()==2 || newStabilimento.getTipoAttivita()==3){
				//codice individuale mobile: RESIDENZA
				//com = new ComuniAnagrafica(db, operatore.getRappLegale().getIndirizzo().getComune());
				idComune = operatore.getRappLegale().getIndirizzo().getComune() ;
				indirizzo_hid=((operatore.getRappLegale().getIndirizzo().getDescrizioneToponimo()!=null && !operatore.getRappLegale().getIndirizzo().getDescrizioneToponimo().equalsIgnoreCase("null")) ? operatore.getRappLegale().getIndirizzo().getDescrizioneToponimo() : "VIA")+" "+operatore.getRappLegale().getIndirizzo().getVia();				
				comune_hid=operatore.getRappLegale().getIndirizzo().getDescrizioneComune();
				provincia_hid=operatore.getRappLegale().getIndirizzo().getDescrizione_provincia();
				asl_hid=operatore.getRappLegale().getIndirizzo().getDescrizioneAsl();
				cap_hid=operatore.getRappLegale().getIndirizzo().getCap();				
			}
			else if (newStabilimento.getTipoAttivita()==1){
				//codice individuale fissa: OPERATIVA
				//com = new ComuniAnagrafica(db, newStabilimento.getSedeOperativa().getComune());
				idComune = newStabilimento.getSedeOperativa().getComune();
				indirizzo_hid=((newStabilimento.getSedeOperativa().getDescrizioneToponimo()!=null && !newStabilimento.getSedeOperativa().getDescrizioneToponimo().equalsIgnoreCase("null")) ? newStabilimento.getSedeOperativa().getDescrizioneToponimo() : "VIA")+" "+newStabilimento.getSedeOperativa().getVia();
				comune_hid=newStabilimento.getSedeOperativa().getDescrizioneComune();
				provincia_hid=newStabilimento.getSedeOperativa().getDescrizione_provincia();
				asl_hid=newStabilimento.getSedeOperativa().getDescrizioneAsl();
				cap_hid=newStabilimento.getSedeOperativa().getCap();

			}
		}
		//FISSA
		else if (newStabilimento.getTipoAttivita()==1){
			//codice fissa: operativa
			//com = new ComuniAnagrafica(db, newStabilimento.getSedeOperativa().getComune());
			idComune = newStabilimento.getSedeOperativa().getComune();
			indirizzo_hid=((newStabilimento.getSedeOperativa().getDescrizioneToponimo()!=null && !newStabilimento.getSedeOperativa().getDescrizioneToponimo().equalsIgnoreCase("null")) ? newStabilimento.getSedeOperativa().getDescrizioneToponimo() : "VIA")+" "+newStabilimento.getSedeOperativa().getVia();
			comune_hid=newStabilimento.getSedeOperativa().getDescrizioneComune();
			provincia_hid=newStabilimento.getSedeOperativa().getDescrizione_provincia();
			asl_hid=newStabilimento.getSedeOperativa().getDescrizioneAsl();
			cap_hid=newStabilimento.getSedeOperativa().getCap();
		}
		//MOBILE
		else if (newStabilimento.getTipoAttivita()==2 || newStabilimento.getTipoAttivita()==3){
			//codice mobile: legale
			//com = new ComuniAnagrafica(db, operatore.getSedeLegale().getComune());
			idComune = operatore.getSedeLegale().getComune();
			indirizzo_hid=((operatore.getSedeLegale().getDescrizioneToponimo()!=null && !operatore.getSedeLegale().getDescrizioneToponimo().equalsIgnoreCase("null")) ? operatore.getSedeLegale().getDescrizioneToponimo() : "VIA")+" "+operatore.getSedeLegale().getVia();
			comune_hid=operatore.getSedeLegale().getDescrizioneComune();
			provincia_hid=operatore.getSedeLegale().getDescrizione_provincia();
			asl_hid=operatore.getSedeLegale().getDescrizioneAsl();
			cap_hid=operatore.getSedeLegale().getCap();
			
		}

	  	context.getRequest().setAttribute("indirizzohid", indirizzo_hid);
	  	context.getRequest().setAttribute("comunehid", comune_hid);
	  	context.getRequest().setAttribute("provinciahid", provincia_hid);
	  	context.getRequest().setAttribute("aslhid", asl_hid);
	  	context.getRequest().setAttribute("caphid", cap_hid);

		
		
		

		// Costruzione campi in base al tipo di attivita'
		LinkedHashMap<String, ArrayList<LineeMobiliHtmlFields>> campi = new LinkedHashMap<String, ArrayList<LineeMobiliHtmlFields>>();
  		String query="";
  		query = "select campi.*, v.valore_campo as valore_campo,v.indice as indice "+
  				" from linee_mobili_html_fields campi "+
  				" join linee_mobili_fields_value v on v.id_linee_mobili_html_fields = campi.id and (v.id_opu_rel_stab_linea = ? or id_rel_stab_linea = ?) and v.enabled "+
  				" where campi.id_linea= ? and campi.enabled order by ordine_campo asc ";
		PreparedStatement pst = db.prepareStatement(query);
		pst.setInt(1, Integer.parseInt(lda_rel_stab));
		pst.setInt(2, Integer.parseInt(lda_rel_stab));
  		pst.setInt(3, Integer.parseInt(lda_macroarea));
  		ResultSet rs = pst.executeQuery();
  		while (rs.next()){
  			LineeMobiliHtmlFields campo = new LineeMobiliHtmlFields();
  			campo.buildRecord(db, rs);
  			
  			if  (!campi.containsKey(campo.getNome_campo())){ // Se non e' presente la chiave
  				ArrayList<LineeMobiliHtmlFields> campoArray = new ArrayList<LineeMobiliHtmlFields>(); //Crea array vuoto
  				campoArray.add(campo); //Aggiungi campo
  				campi.put(campo.getNome_campo(), campoArray); //Aggiungi array con nuovo elemento
  			}
  			else
  				campi.get(campo.getNome_campo()).add(campo);
  		}
  		pst.close();
  		LineeMobiliHtmlFields c = new LineeMobiliHtmlFields();
  		LinkedHashMap<String, String> ris = c.costruisciHtmlDaHashMap(db, campi,thisUser.getRoleId());
  		// Form inerente agli inserimenti
	  	context.getRequest().setAttribute("campiHash", ris);
	  	
	  	
	  	
	  		// Costruzione campi in base al tipo di attivita' SENZA VALORE
	 		LinkedHashMap<String, ArrayList<LineeMobiliHtmlFields>> campiSenzaValore = new LinkedHashMap<String, ArrayList<LineeMobiliHtmlFields>>();
	   		query = "select campi.*, '' as valore_campo,'' as indice "+
	   				" from linee_mobili_html_fields campi "+
	   				" where campi.id_linea= ? and campi.enabled order by ordine_campo asc ";
	 		PreparedStatement pstSenzaValore = db.prepareStatement(query);
	   		pstSenzaValore.setInt(1, Integer.parseInt(lda_macroarea));
	   		ResultSet rsSenzaValore = pstSenzaValore.executeQuery();
	   		while (rsSenzaValore.next()){
	   			LineeMobiliHtmlFields campo = new LineeMobiliHtmlFields();
	   			campo.buildRecord(db, rsSenzaValore);
	   			
	   			if  (!campiSenzaValore.containsKey(campo.getNome_campo())){ // Se non e' presente la chiave
	   				ArrayList<LineeMobiliHtmlFields> campoArray = new ArrayList<LineeMobiliHtmlFields>(); //Crea array vuoto
	   				campoArray.add(campo); //Aggiungi campo
	   				campiSenzaValore.put(campo.getNome_campo(), campoArray); //Aggiungi array con nuovo elemento
	   			}
	   			else
	   				campiSenzaValore.get(campo.getNome_campo()).add(campo);
	   		}
	   		pstSenzaValore.close();
	  	LinkedHashMap<String, String> risSenzaValore = c.costruisciHtmlDaHashMap(db, campiSenzaValore,thisUser.getRoleId(), stabid); 
	  	context.getRequest().setAttribute("campiHashSenzaValore", risSenzaValore);
		
		// Form inerente alla visualizzazione / eliminazione
	  	ArrayList<LineeMobiliHtmlFields> campoArray = new ArrayList<LineeMobiliHtmlFields>(); //Crea array vuoto
	  	
	  	//LinkedHashMap<String, ArrayList<LineeMobiliHtmlFields>> campiVisualize = new LinkedHashMap<String, ArrayList<LineeMobiliHtmlFields>>();	
		query = "select campi.*, mod.valore_campo, mod.indice,mod.data_modifica,mod.id_utente_modifica,mod.data_inserimento,mod.id_utente_inserimento  from linee_mobili_html_fields campi "+   
				" JOIN linee_mobili_fields_value mod on mod.id_linee_mobili_html_fields = campi.id and (mod.id_rel_stab_linea = ? or mod.id_opu_rel_stab_linea = ?)  "+ 
				" where campi.id_linea =  ? and (mod.enabled = true or mod.enabled is null) and campi.enabled=true order by mod.indice,campi.ordine_campo asc ";

		pst = db.prepareStatement(query);
		pst.setInt(1, Integer.parseInt(lda_rel_stab)); //1
		pst.setInt(2, Integer.parseInt(lda_rel_stab)); //1
		pst.setInt(3, Integer.parseInt(lda_macroarea)); //2
		rs = pst.executeQuery();
		while (rs.next()){
			LineeMobiliHtmlFields campo = new LineeMobiliHtmlFields();
			campo.buildRecord(db, rs);
			campoArray.add(campo); //Aggiungi campo
		}
		pst.close();
		context.getRequest().setAttribute("listaElementi", campoArray);
				
		context.getRequest().setAttribute("ldaMacroId", lda_macroarea);
		context.getRequest().setAttribute("ldaStabId", lda_rel_stab);
		context.getRequest().setAttribute("stabId", stabid);
		
	  	//Variabile che conta i campi del dettagli
	 	context.getRequest().setAttribute("numeroCampi", ris.size());

	 	LookupList lookup_tipo_alimento_distributore = new LookupList(db,"lookup_tipo_alimento_distributore");
		context.getRequest().setAttribute("TipoAlimentoDistributore", lookup_tipo_alimento_distributore);

		LookupList lookup_tipo_mobili = new LookupList(db,"lookup_tipo_mobili");
		context.getRequest().setAttribute("TipoMobili", lookup_tipo_mobili);
				
		return getReturn(context, "AggiuntaDettaglioMobile");

	} catch (Exception e) {
		e.printStackTrace();
		// Go through the SystemError process
		context.getRequest().setAttribute("Error", e);
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}
}


public String executeCommandEliminaMobileDaLinea(ActionContext context) {
	if (!hasPermission(context, "gestioneanagrafica-gestioneanagrafica-datiaggiuntivi-add")) {
		return ("PermissionError");
	}
	Connection db = null;
	SystemStatus systemStatus = this.getSystemStatus(context);
	//Stabilimento newStabilimento = null;
	try {
		//String tempStabId = context.getRequest().getParameter("stabId");
		//if (tempStabId == null) {
		//	tempStabId = ""	+ (Integer) context.getRequest().getAttribute("idStab");
		//}
		//Integer stabid = null;
		//if (tempStabId != null) {
		//	stabid = Integer.parseInt(tempStabId);
		//}
		//String lda_macroarea=context.getRequest().getParameter("ldaMacroId");
		String lda_rel_stab=context.getRequest().getParameter("ldaStabId");
		String indice=context.getRequest().getParameter("indice");
		String tempStabId=context.getRequest().getParameter("stabId");
		Integer stabid = null;
		if (tempStabId != null) 
		{
			stabid = Integer.parseInt(tempStabId);
		}
		
		db = this.getConnection(context);	
		LineeMobiliHtmlFields.eliminazioneLogicaDettaglioMobile(db,Integer.parseInt(indice),Integer.parseInt(lda_rel_stab),context);
		RicercheAnagraficheTab.inserOpu(db, stabid);

	}catch (Exception e) {
		e.printStackTrace();
		context.getRequest().setAttribute("Error", e);
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}
	context.getRequest().setAttribute("messaggioOk", "Eliminazione avvenuta con successo.");
	//return getReturn(context, "AggiuntaDettaglioMobile");
	return executeCommandGestioneMobile(context);
}

public String executeCommandUpdate(ActionContext context){
	
    Connection db = null;
    final Logger log = Logger.getLogger(GisaNoScia.class);
    
    Map<String, String[]> parameterMap = context.getRequest().getParameterMap();
    //parameterMap.get("partita_iva"); esempio di accesso

	Map<String, String> campiFissi = new HashMap<String, String>();
	Map<String, String> campiEstesi = new HashMap<String, String>();
	String valore_campo;
	String chiave_campo;
	int altId = -1;

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
		else if (chiave_campo.equalsIgnoreCase("alt_id"))
		{
			if(valore_campo.equalsIgnoreCase("")){
				valore_campo = "-1";
			}
			altId = Integer.parseInt(valore_campo);
		}
		
	}
	
	System.out.println("\n");
	
	int userId =  getUserId(context);
	System.out.println("userid: " + userId);
	
	String numeroPratica = "";
	try {numeroPratica = context.getRequest().getParameter("_b_numero_pratica");} catch (Exception e){}
	if(numeroPratica == null){
		numeroPratica = "";
	}
	
	try{
	    db = this.getConnection(context);
	    
	    OggettoPerStorico stab_pre_modifica = new OggettoPerStorico(altId, db);
	    OperazioneSuOsa osa = new OperazioneSuOsa();
	    String id_stabilimento = "";
	    id_stabilimento= osa.modifyGestioneAnagrafica(db,campiFissi,campiEstesi,userId,altId);
	    
	    System.out.println("stabilimento modificato: " + id_stabilimento);	
        
    	allega_file_errata_corrige(context, db, Integer.parseInt(id_stabilimento));
        stab_pre_modifica.salvaStoricoEvento(db, Integer.parseInt(id_stabilimento),stab_pre_modifica);
        
        context.getRequest().setAttribute("id_stabilimento", id_stabilimento);
        return "DetailsInsertOK";
    
	}catch (Exception e) {
		e.printStackTrace();
		context.getRequest().setAttribute("erroreInsert", 
				"Errore inserimento stabilimento: contattare hd I livello!!!<br>" + e.toString());
		return "ErrorPageInsert";
    } finally {
        this.freeConnection(context, db);
    }  

}


public String executeCommandStoricoOperazioni(ActionContext context) throws IndirizzoNotFoundException{ 
	
	int altId = -1;
	int stabId = -1;
	
	try {altId = (Integer) context.getRequest().getAttribute("altId");} catch (Exception e){}
	if (altId==-1)
		try {altId = Integer.parseInt(context.getRequest().getParameter("altId"));} catch (Exception e){}
	
	try {stabId = (Integer) context.getRequest().getAttribute("stabId");} catch (Exception e){}
	if (stabId==-1)
		try {stabId = Integer.parseInt(context.getRequest().getParameter("stabId"));} catch (Exception e){}
  
	
	ArrayList<OperazioneSuOsa> lista_operazioni = new ArrayList<OperazioneSuOsa>();
	
	Connection db = null; 
    
    try{
    	db = this.getConnection(context);
    	org.aspcfs.modules.opu.base.Stabilimento stab = null;
		
		if (stabId>0)
        	stab = new org.aspcfs.modules.opu.base.Stabilimento(db, stabId);
        else if (altId>0)
        	stab = new org.aspcfs.modules.opu.base.Stabilimento(db, altId, true);
    	
    	OperazioneSuOsa osa = new OperazioneSuOsa();
    	context.getRequest().setAttribute("altId", String.valueOf(stab.getAltId()));
    	lista_operazioni = osa.getListaOperazioniStabilimento(db, stab.getAltId());
    	context.getRequest().setAttribute("ListaOperazioni", lista_operazioni);
    	context.getRequest().setAttribute("StabilimentoDettaglio", stab);
 	
    }catch (SQLException e) {
    	e.printStackTrace();
    } finally {
        this.freeConnection(context, db);
    }

    return "StoricoOperazioniOK";
}	

public String executeCommandUpdateLinee(ActionContext context){
	
    Map<String, String[]> parameterMap = context.getRequest().getParameterMap();
    String valore_campo;
	String chiave_campo;
	Map<String, String> campiFissi = new HashMap<String, String>();
	int idUtente = getUserId(context);
    Connection db = null;
  	int altId = Integer.parseInt(context.getRequest().getParameter("altId"));
  	 	
 	try{
	    db = this.getConnection(context);
	    
	    Stabilimento stab = new Stabilimento(db, altId, true);
	  	int sizeLinee = stab.getListaLineeProduttive().size();
	 	for (int i = 0; i<sizeLinee; i++){
	  		String cb = context.getRequest().getParameter("checkmodificalinea_"+i);
	  		if (cb!=null && cb.equals("si")){
	  			
		  			String idRel = null;
		  			String idLinea = null;
		  			String dataInizio = null;
		  			String dataFine = null;
		  			String tipoCarattere = null;
		  			String stato = null;
		  			String cun = null;
		  			
		  			try {idRel = context.getRequest().getParameter("id_rel_stab_lp_"+i);} catch (Exception e){};
		  			campiFissi.put("linea_" + i + "_id_rel_stab_lp", idRel);
		  			
		  			try {idLinea = context.getRequest().getParameter("id_linea_produttiva_"+i);} catch (Exception e){};
		  			campiFissi.put("linea_" + i + "_id_linea_produttiva", idLinea);
		  			
		  			try {dataInizio = context.getRequest().getParameter("data_inizio_"+i);} catch (Exception e){};
		  			campiFissi.put("linea_" + i + "_data_inizio", dataInizio);
		  			
		  			try {dataFine = context.getRequest().getParameter("data_fine_"+i);} catch (Exception e){};
		  			campiFissi.put("linea_" + i + "_data_fine", dataFine);
		  			
		  			try {tipoCarattere = context.getRequest().getParameter("tipo_carattere_"+i);} catch (Exception e){};
		  			campiFissi.put("linea_" + i + "_tipo_carattere", tipoCarattere);
		  			
		  			try {stato = context.getRequest().getParameter("stato_"+i);} catch (Exception e){};
		  			campiFissi.put("linea_" + i + "_stato", stato);
		  			
		  			try {cun = context.getRequest().getParameter("cun_"+i);} catch (Exception e){};
		  			campiFissi.put("linea_" + i + "_cun", cun);
	  			}
	  	}
	 	
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
	 	}
	 	
	 	OggettoPerStorico stab_pre_modifica = new OggettoPerStorico(altId, db);

	 	OperazioneSuOsa osa = new OperazioneSuOsa();
        String id_stabilimento = "";
        id_stabilimento = osa.modificaLinee(db,campiFissi, idUtente,altId);
	 	
        allega_file_errata_corrige(context, db, Integer.parseInt(id_stabilimento));
        stab_pre_modifica.salvaStoricoEvento(db, Integer.parseInt(id_stabilimento),stab_pre_modifica);
        
	 	context.getRequest().setAttribute("id_stabilimento", id_stabilimento);
			
		return "DetailsInsertOK";
	    
    
	}catch (Exception e) {
		e.printStackTrace();
		context.getRequest().setAttribute("erroreInsert", 
				"Errore modifica linea: contattare hd I livello!!!<br>" + e.toString());
		return "ErrorPageInsert";
    } finally {
        this.freeConnection(context, db);
    }  

}

//in sospeso (viene sostituita la gestione di OPU per i CU)
public String executeCommandViewVigilanza(ActionContext context) throws SQLException, IndirizzoNotFoundException {
    if (!hasPermission(context, "gestioneanagrafica-gestioneanagrafica-vigilanza-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    org.aspcfs.modules.vigilanza.base.TicketList ticList = new org.aspcfs.modules.vigilanza.base.TicketList();
    Organization newOrg = null;
    
    db = this.getConnection(context);
    org.aspcfs.modules.opu.base.Stabilimento stab = null;
    
    
    //Process request parameters
    int passedId = Integer.parseInt(context.getRequest().getParameter("altId"));
    
    stab = new org.aspcfs.modules.opu.base.Stabilimento(db, passedId, true);
    
    String sql = "update ticket set alt_id = ? where id_stabilimento =? ";
	PreparedStatement st = db.prepareStatement(sql);
	
	st.setInt(1, passedId);
	st.setInt(2, stab.getIdStabilimento());
	st.execute();
    
    //Prepare PagedListInfo
    PagedListInfo ticketListInfo = this.getPagedListInfo(
        context, "AccountTicketInfo", "t.entered", "desc");
    ticketListInfo.setLink(
        "GestioneAnagraficaAction.do?command=ViewVigilanza&altId=" + passedId);
    ticList.setPagedListInfo(ticketListInfo);
    try {
      db = this.getConnection(context);
      //find record permissions for portal users
      if (!isRecordAccessPermitted(context, db, passedId)) {
        return ("PermissionError");
      }
      
      SystemStatus systemStatus = this.getSystemStatus(context);
		LookupList TipoCampione = new LookupList(db, 
				"lookup_tipo_controllo");
		TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("TipoCampione", TipoCampione);

      newOrg = new Organization(db, passedId,"");
      ticList.setAltId(passedId);
      
      UserBean thisUser = (UserBean) context.getSession().getAttribute("User"); 
		if (thisUser.getRoleId()==Role.RUOLO_CRAS)
			ticList.setIdRuolo(thisUser.getRoleId());
		
      ticList.buildList(db);
      context.getRequest().setAttribute("TicList", ticList);
      context.getRequest().setAttribute("OrgDetails", newOrg);
      addModuleBean(context, "View Accounts", "Accounts View");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "ViewVigilanza");
  }




	public static HashMap<Integer, ArrayList<LineaProduttivaCampoEsteso>> costruisciValoriCampiEstesi(ActionContext context,Connection db, int idRelazioneAttivita) throws SQLException {
		 
		
		PreparedStatement pst1 = db.prepareStatement("select * from linee_mobili_html_fields where id_linea = ? and enabled ");
		pst1.setInt(1,idRelazioneAttivita);
		ResultSet rs1 = pst1.executeQuery();
		HashMap<Integer,ArrayList<LineaProduttivaCampoEsteso>> valoriCampiEstesi = new HashMap<Integer,ArrayList<LineaProduttivaCampoEsteso> >(); //la chiave e il nome del dom, il valore e un array dove la prima stringa e il valore, la seconda l'id su html fields
		while(rs1.next())
		{
			int idHtmlField = rs1.getInt("id");
			
			
			LineaProduttivaCampoEsteso  campoEsteso = new LineaProduttivaCampoEsteso();
			campoEsteso.setIdFieldHtml(idHtmlField);
			campoEsteso.setNomeCampo(rs1.getString("nome_campo"));
			campoEsteso.setTipoCampo(rs1.getString("tipo_Campo"));
			campoEsteso.setNomeTabella(rs1.getString("tabella_lookup"));
			
			try
			{
				System.out.println("-----------------------");
				System.out.println(campoEsteso.getNomeCampo()+","+campoEsteso.getValore());
			}
			catch(Exception ex)
			{
				
			}
			
			String nomeCampoEstesoNellaForm = rs1.getString("nome_campo")+idRelazioneAttivita;
			//lo prendo dal multipart
			String[] values = context.getRequest().getParameterValues(nomeCampoEstesoNellaForm);
			
			if(values == null && rs1.getString("tipo_campo") != null && rs1.getString("tipo_campo").equalsIgnoreCase("checkbox") )
			{ //nb: un checkbox non checked non arriva proprio nel form !
				
				if (valoriCampiEstesi.get(idHtmlField)!=null)
				{
					campoEsteso.setValore("false");
					ArrayList<LineaProduttivaCampoEsteso> listaValori = valoriCampiEstesi.get(idHtmlField);
					listaValori.add(campoEsteso);
					valoriCampiEstesi.put(idHtmlField, listaValori);
					
	//				valoriCampiEstesi.put(idHtmlField,new String[]{"false",idHtmlField+""}); //perche in tal caso e un checkbox che dovrebbe esserci, ma non essendo arrivato vuol dire che non era checked
				}
				else
				{
					ArrayList<LineaProduttivaCampoEsteso> listaValori = new ArrayList<LineaProduttivaCampoEsteso>();
					campoEsteso.setValore("false");
					listaValori.add(campoEsteso);
					valoriCampiEstesi.put(idHtmlField, listaValori);
					
				}
					
				
				}
			else if(values != null)
			{
				if (valoriCampiEstesi.get(idHtmlField)!=null)
				{
					ArrayList<LineaProduttivaCampoEsteso> listaValori = valoriCampiEstesi.get(idHtmlField);
					for (int j = 0 ; j <values.length;j++)
					{
						if (campoEsteso.getNomeTabella()!=null && !"".equals(campoEsteso.getNomeTabella()))
						{
							LookupList lookup = new LookupList(db,campoEsteso.getNomeTabella());
							campoEsteso.setValore(lookup.getSelectedValue(Integer.parseInt(values[j])));
							
						}
						else
						{
						campoEsteso.setValore(values[j]);
						}
						listaValori.add(campoEsteso);
						
					}
					valoriCampiEstesi.put(idHtmlField, listaValori);
					
				}
				else
				{
					ArrayList<LineaProduttivaCampoEsteso> listaValori = new ArrayList<LineaProduttivaCampoEsteso>();
					for (int j = 0 ; j <values.length;j++)
					{
						if (campoEsteso.getNomeTabella()!=null && !"".equals(campoEsteso.getNomeTabella()))
						{
							
							 campoEsteso = new LineaProduttivaCampoEsteso();
								campoEsteso.setIdFieldHtml(idHtmlField);
								campoEsteso.setNomeCampo(rs1.getString("nome_campo"));
								campoEsteso.setTipoCampo(rs1.getString("tipo_Campo"));
								campoEsteso.setNomeTabella(rs1.getString("tabella_lookup"));
							LookupList lookup = new LookupList(db,campoEsteso.getNomeTabella());
							campoEsteso.setValore(lookup.getSelectedValue(Integer.parseInt(values[j])));
							
						}
						else
						{
						campoEsteso.setValore(values[j]);
						}
						listaValori.add(campoEsteso);
						
					}
					valoriCampiEstesi.put(idHtmlField, listaValori);
					
				}
				
			}
			else
			{
				
				if (valoriCampiEstesi.get(idHtmlField)!=null)
				{
					ArrayList<LineaProduttivaCampoEsteso> listaValori = valoriCampiEstesi.get(idHtmlField);
					 campoEsteso = new LineaProduttivaCampoEsteso();
						campoEsteso.setIdFieldHtml(idHtmlField);
						campoEsteso.setNomeCampo(rs1.getString("nome_campo"));
						campoEsteso.setTipoCampo(rs1.getString("tipo_Campo"));
						campoEsteso.setNomeTabella(rs1.getString("tabella_lookup"));
						campoEsteso.setValore("");
						listaValori.add(campoEsteso);
					valoriCampiEstesi.put(idHtmlField, listaValori);
					
				}
				else
				{
					ArrayList<LineaProduttivaCampoEsteso> listaValori = new ArrayList<LineaProduttivaCampoEsteso>();
					
						
							
							 campoEsteso = new LineaProduttivaCampoEsteso();
								campoEsteso.setIdFieldHtml(idHtmlField);
								campoEsteso.setNomeCampo(rs1.getString("nome_campo"));
								campoEsteso.setTipoCampo(rs1.getString("tipo_Campo"));
								campoEsteso.setNomeTabella(rs1.getString("tabella_lookup"));
								
							campoEsteso.setValore("");
					
						listaValori.add(campoEsteso);
					
					valoriCampiEstesi.put(idHtmlField, listaValori);
					
				}
				
			}
			
			
		}
		
		return valoriCampiEstesi;
		
	}

	
	public String executeCommandTrasformazione(ActionContext context) throws IndirizzoNotFoundException, IOException{
		
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
		String numeroPratica = "";
		int idTipologiaPratica = -1;
		int idComunePratica = -1;
		
		parameterMap.get("numeroPratica")[0].trim();
		parameterMap.get("idTipologiaPratica");
		
		if(parameterMap.get("idTipologiaPratica")[0].trim().equalsIgnoreCase("")){
			idTipologiaPratica = -1;
		}else{
			idTipologiaPratica = Integer.parseInt(parameterMap.get("idTipologiaPratica")[0].trim());
		}
		
		if(parameterMap.get("numeroPratica")[0].trim().equalsIgnoreCase("")){
			numeroPratica = null;
		} else {
			numeroPratica = parameterMap.get("numeroPratica")[0].trim();
		}
		
		if(parameterMap.get("idComunePratica")[0].trim().equalsIgnoreCase("")){
			idComunePratica = -1;
		}else{
			idComunePratica = Integer.parseInt(parameterMap.get("idComunePratica")[0].trim());
		}
		
		
		for (String key: parameterMap.keySet()){
			valore_campo = parameterMap.get(key)[0].trim();
			chiave_campo = key.trim();
			if (chiave_campo.startsWith("_b_"))
			{
				campiFissi.put(chiave_campo.replaceFirst("_b_", ""), valore_campo);
			}
			
		}

		Connection db = null;
	    
	    try{
	        db = this.getConnection(context);
	        
	        OggettoPerStorico stab_pre_modifica = new OggettoPerStorico(altId, db);
	        OperazioneSuOsa osa = new OperazioneSuOsa();
	        String risultato_query="";
	        risultato_query = osa.insertTrasformazioneLinea(db, campiFissi, campiEstesi,getUserId(context), altId, idTipologiaPratica,numeroPratica,idComunePratica);
	        
	        EsitoOperazione esitoOperazione = new EsitoOperazione(risultato_query);
	  		
	  		if(esitoOperazione.getEsito()==1){
	  			
	  			allega_file_errata_corrige(context, db, esitoOperazione.getIdStabilimento());
	  			stab_pre_modifica.salvaStoricoEvento(db,esitoOperazione.getIdStabilimento(),stab_pre_modifica);
	  			
	  			context.getRequest().setAttribute("id_stabilimento", String.valueOf(esitoOperazione.getIdStabilimento()));
	  			
	  			return "DetailsInsertOK";
	  			
	  		}else{
	  			context.getRequest().setAttribute("erroreInsert",esitoOperazione.getMessaggio());
	  			return "ErrorPageInsert";
	  		}
	        

	    }catch (SQLException e) {
	    	e.printStackTrace();
	    	context.getRequest().setAttribute("erroreInsert", 
					"Errore in fase di operazione trasformazione: contattare hd I livello!!!<br>" + e.toString());
			return "ErrorPageInsert";
	    } finally {
	        this.freeConnection(context, db);
	    }
	     
	}
	
	private void allega_file_errata_corrige(ActionContext context, Connection db, int id_stab) throws SQLException, IndirizzoNotFoundException, IOException{
		
		String id_causale_pratica = "";
		try {id_causale_pratica = context.getRequest().getParameter("_b_id_causale");} catch (Exception e){}
		if(id_causale_pratica == null){
			id_causale_pratica = "";
		}
		
		//solo se causale pratica uguale errata corrige si entra in questo pezzo
		if(id_causale_pratica.trim().equalsIgnoreCase("2")){
			
			String numeroPratica = "";
			try {numeroPratica = context.getRequest().getParameter("_b_numero_pratica");} catch (Exception e){}
			if(numeroPratica == null){
				numeroPratica = "";
			}
			
			String idAggiuntaPratica = "";
			try {idAggiuntaPratica = context.getRequest().getParameter("idAggiuntaPratica");} catch (Exception e){}
			if(idAggiuntaPratica == null){
				idAggiuntaPratica = "";
			}
			
			String codici_allegati = "richiesta_errata_corrige";
			
			Stabilimento s = null;
			s = new Stabilimento(db, id_stab);
			LineaProduttiva lin = (LineaProduttiva) s.getListaLineeProduttive().get(0);
			//se si tratta di uno stabilimento scia si prosegue 
			if(!lin.getFlags().isNoScia()){
				int id_comune_allegato = -1;
				if( s.getSedeOperativa().getComune() > 0){
					id_comune_allegato = s.getSedeOperativa().getComune();
				} else if(s.getOperatore().getSedeLegale().getComune() > 0) {
					id_comune_allegato = s.getOperatore().getSedeLegale().getComune();
				}
				
				if(!idAggiuntaPratica.trim().equalsIgnoreCase("") && !numeroPratica.trim().equalsIgnoreCase("")){
					GestioneAllegatiGins.aggiornaNumeroPratica(getUserId(context), idAggiuntaPratica, 
							numeroPratica, codici_allegati, String.valueOf(id_comune_allegato));
				}
			}
		}
		
	
	}
	
	
	public String executeCommandInsertApicoltura(ActionContext context){
		
	    Connection db = null;
	    
	    Map<String, String[]> parameterMap = context.getRequest().getParameterMap();
	    //parameterMap.get("partita_iva"); esempio di accesso
	
		Map<String, String> campiFissi = new HashMap<String, String>();
		Map<String, String> campiEstesi = new HashMap<String, String>();
		ArrayList<String> array_linee_indice = new ArrayList<String>();
		Map<String, String> campiFissiTemp = new HashMap<String, String>();
		String valore_campo;
		String chiave_campo;
		String numeroPratica = "";
		int idTipologiaPratica = -1;
		int idComunePratica = -1;

		for (String key: parameterMap.keySet()){
			valore_campo = parameterMap.get(key)[0].trim();
			chiave_campo = key.trim();
			if (chiave_campo.startsWith("_b_") && (!chiave_campo.startsWith("_b_lineaattivita_")))
			{
				if(valore_campo.equalsIgnoreCase("")){
					valore_campo = null;
				}
				campiFissi.put(chiave_campo.replaceFirst("_b_", ""), valore_campo);
			}
			else if(chiave_campo.startsWith("_b_lineaattivita_") && chiave_campo.endsWith("_tipo_attivita")){
				array_linee_indice.add(chiave_campo.replaceAll("_tipo_attivita", "") + ";;" + valore_campo);
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
		
		try
		{
			
			db = this.getConnection(context);

			String id_stab_richiesta_apicoltura = "";
			int userId =  getUserId(context);
			System.out.println("userid: " + userId);
		
			campiFissiTemp = campiFissi;
			for(int i = 0; i < array_linee_indice.size(); i++){
				
				for (String key: parameterMap.keySet()){
					valore_campo = parameterMap.get(key)[0].trim();
					chiave_campo = key.trim();
					
					if(chiave_campo.startsWith(array_linee_indice.get(i).split(";;")[0])){
						
						if(valore_campo.equalsIgnoreCase("")){
							valore_campo = null;
						}
						campiFissiTemp.put(chiave_campo.replaceFirst("_b_", ""), valore_campo);
					}
				}
				
			}			
			
			Stabilimento s = new Stabilimento();
			
			//metodo per inserimento richiesta apicoltura			
			id_stab_richiesta_apicoltura = s.inserisci_richiesta_apicoltura(db, campiFissiTemp, campiEstesi, userId, idTipologiaPratica, numeroPratica, idComunePratica);
			
			//gestire invio mail
			HashMap<String,String> configs = new HashMap<String,String>();
			configs.put("mail.smtp.starttls.enable",ApplicationProperties.getProperty("mail.smtp.starttls.enable"));
			configs.put("mail.smtp.auth", ApplicationProperties.getProperty("mail.smtp.auth"));
			configs.put("mail.smtp.host", ApplicationProperties.getProperty("mail.smtp.host"));
			configs.put("mail.smtp.port", ApplicationProperties.getProperty("mail.smtp.port"));
			configs.put("mail.smtp.ssl.enable",ApplicationProperties.getProperty("mail.smtp.ssl.enable"));
			configs.put("mail.smtp.ssl.protocols", "tlsv1.2");
			configs.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			configs.put("mail.smtp.socketFactory.fallback", "false");
			
			PecMailSender sender = new PecMailSender(configs,ApplicationProperties.getProperty("username"), ApplicationProperties.getProperty("password"));
			String msg = "Gentile Utente, le comunichiamo che e stato abilitato all'inserimento dei dati riguardanti la propria attivita di apicoltura nella banca dati apistica regionale.";
			
			String destinatario = parameterMap.get("_b_email_impresa")[0].trim();
			if( ! ApplicationProperties.getProperty("ambiente").equalsIgnoreCase("ufficiale"))
			{
				destinatario = "gisadev@usmail.it";
			}

			try {
				sender.sendMail("BDAR-ABILITAZIONE CREDENZIALI", msg, "gisasuap@cert.izsmportici.it",destinatario , null);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//valutare vecchio salvataggi storico
			String[] infoUtente = {"-1", "", ""};
			infoUtente[0] =  String.valueOf(getUserId(context));
			infoUtente[1] = ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserRecord().getSuap().getCodiceFiscaleRichiedente();
			infoUtente[2] = ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserRecord().getSuap().getCodiceFiscaleDelegato();
			gestioneStoricoApicoltura(Integer.parseInt(id_stab_richiesta_apicoltura), Storico.INSERIMENTO_SCIA, infoUtente, "", db);
			gestioneStoricoApicoltura(Integer.parseInt(id_stab_richiesta_apicoltura), Storico.REGISTRAZIONE_NON_DISPONIBILE , infoUtente, "", db);
			
			System.out.println("id richiesta apicoltura inserita: " + id_stab_richiesta_apicoltura);	        
	        
	        return "InsertRichiestaApicolturaOK";
		    
	    
		}catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("erroreInsert", 
					"Errore inserimento richiesta apicoltura: contattare hd I livello!!!<br>" + e.toString());
			return "ErrorPageInsert";
        } finally {
            this.freeConnection(context, db);
        }  
	
    }
	
	private void gestioneStoricoApicoltura(int id_stab, int idOperazione, String[] infoUtente, String note, Connection db){
		Storico storico = new Storico();
		storico.setIdStabilimento(id_stab);
		storico.setIdOperazione(idOperazione);
		storico.setIdUtente(infoUtente[0]);
		storico.setCodFiscaleUtenteRichiedente(infoUtente[1]);
		storico.setCodFiscaleUtenteDelegato(infoUtente[2]);
		storico.setNote(note);
		storico.insert(db);
	}
	
	public String executeCommandSetCodiceSINVSA(ActionContext context) throws SQLException, IndirizzoNotFoundException {
		String codice = context.getRequest().getParameter("codice-sinvsa"); 
		String dataCodice = context.getRequest().getParameter("data-codice-sinvsa");
		int riferimentoId = Integer.parseInt(context.getRequest().getParameter("riferimento-id")); 
		String riferimentoId_nomeTab = context.getRequest().getParameter("riferimento-id-nome-tab");
		int userId = Integer.parseInt(context.getRequest().getParameter("user-id"));
		
		context.getRequest().setAttribute("stabId", riferimentoId);
		
		try {
			PopolaCombo.setCodiceSINVSA(codice, dataCodice, riferimentoId, riferimentoId_nomeTab, userId);
		} catch (SQLException e) {
			throw e;
		}
		
		try {
			return this.executeCommandDetails(context);
		} catch (IndirizzoNotFoundException e) {
			throw e;
		}
	}
	
	
}
