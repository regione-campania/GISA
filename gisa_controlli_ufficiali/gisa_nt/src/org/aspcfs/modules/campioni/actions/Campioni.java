/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.campioni.actions;

/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */


import com.darkhorseventures.framework.actions.Action;
import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.servlets.ControllerServlet;

import org.aspcf.modules.controlliufficiali.base.ModCampioni;
import org.aspcf.modules.controlliufficiali.base.Piano;
import org.aspcf.modules.report.util.Filtro;
import org.aspcf.modules.report.util.StampaPdf;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanList;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.CategoryEditor;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.GenericControlliUfficiali;





import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.molluschibivalvi.base.Coordinate;
import org.aspcfs.modules.molluschibivalvi.base.CoordinateMolluschiBivalvi;
import org.aspcfs.modules.products.base.CustomerProduct;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationHistory;
import org.aspcfs.modules.accounts.base.OrganizationHistoryList;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.modules.campioni.base.Analita;
import org.aspcfs.modules.campioni.base.Pnaa;
import org.aspcfs.modules.campioni.base.SpecieAnimali;
import org.aspcfs.modules.campioni.base.Ticket;
import org.aspcfs.modules.campioni.util.CampioniUtil;
import org.aspcfs.utils.ControlliUfficialiUtil;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DwrPreaccettazione;
import org.aspcfs.utils.PopolaCombo;
import org.aspcfs.utils.RispostaDwrCodicePiano;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletResponse;

/**
 * Maintains Tickets related to an Account
 *
 * @author chris
 * @version $Id: AccountTickets.java,v 1.13 2002/12/24 14:newTic.upda53:03 mrajkowski Exp $
 * @created August 15, 2001
 */
public final class Campioni extends CFSModule {


public int tipologiaOperatore = -1;


        public int getTipologiaOperatore() {
	return tipologiaOperatore;
}




public void setTipologiaOperatore(int tipologiaOperatore) {
	this.tipologiaOperatore = tipologiaOperatore;
}




		/**
         * Re-opens a ticket
         *
         * @param context
         *            Description of the Parameter
         * @return Description of the Return Value
         */
        public String executeCommandReopenTicket(ActionContext context, Connection db) {

                int resultCount = -1;
                
                Ticket thisTicket = null;
                Ticket oldTicket = null;
                try {
                        
                        thisTicket = new Ticket(db, Integer.parseInt(context.getRequest()
                                        .getParameter("id")));
                        oldTicket = new Ticket(db, thisTicket.getId());
                        //check permission to record
                        /*if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
                                return ("PermissionError");
                        }*/
                        thisTicket.setModifiedBy(getUserId(context));
                        resultCount = thisTicket.reopen(db);
                        thisTicket.queryRecord(db, thisTicket.getId());
                        context.getRequest().setAttribute("Ticket", thisTicket);
                        

                        context.getRequest().setAttribute("resultCount", resultCount);
                        if (resultCount == 1)
                        {
                                this.processUpdateHook(context, oldTicket, thisTicket);
                        }
                        
                        

                } catch (Exception errorMessage)
                {
                        context.getRequest().setAttribute("Error", errorMessage);

                }

                return "";
        }




        public String executeCommandViewSchedaValutazioneComportamenta(ActionContext context,Connection db) {

        
                try {
                        
                        String ticketId = context.getRequest().getParameter("id");
                        Ticket newTic = new Ticket();
                        
                        SystemStatus systemStatus = this.getSystemStatus(context);
                        newTic.setSystemStatus(systemStatus);
                        newTic.queryRecord(db, Integer.parseInt(ticketId));
                        
                        
                        
                }
                catch(SQLException e)
                {
                        e.printStackTrace() ;
                }
                
                return "";

        }


        public String executeCommandTicketDetails(ActionContext context,Connection db) {

        
                // Parameters
                String ticketId = context.getRequest().getParameter("id");
                if (ticketId== null)
                {
                        ticketId = (String) context.getRequest().getAttribute("idCampione");
                }
                if(context.getRequest().getAttribute("Messaggio")!=null)
                {

                        context.getRequest().setAttribute("Messaggio", context.getRequest().getAttribute("Messaggio"));
                }

                if(context.getRequest().getAttribute("Messaggio2")!=null)
                {

                        context.getRequest().setAttribute("Messaggio2", context.getRequest().getAttribute("Messaggio2"));
                }

                String retPag = null;
                try {
                        
                        // Load the ticket
                        Ticket newTic = new Ticket();
                        
                        if (context.getRequest().getAttribute("ActionString")!=null)
                        	newTic.setAction(""+context.getRequest().getAttribute("ActionName"));
                        
                        SystemStatus systemStatus = this.getSystemStatus(context);
                        newTic.setSystemStatus(systemStatus);
                        newTic.queryRecord(db, Integer.parseInt(ticketId));
                        newTic.setTipologia_operatore(DatabaseUtils.getTipologiaOperatore(db, newTic.getTipologia_operatore(), newTic.getAltId()));
                        context.getRequest().setAttribute("orgId",newTic.getOrgId());
                        // find record permissions for portal users

                        org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket(db,Integer.parseInt(newTic.getIdControlloUfficiale()));
                        context.getRequest().setAttribute("CU", cu);
                        String scelta = ApplicationProperties.getProperty("HTML_PDF");
                        context.getRequest().setAttribute("html_or_pdf",scelta );
                        
                
                        String id_controllo=newTic.getIdControlloUfficiale();
                        while(id_controllo.startsWith("0")){

                                id_controllo=id_controllo.substring(1);
                        }
                        LookupList lookup_tipo_ispezione = new LookupList();
                        lookup_tipo_ispezione.setTable("lookup_tipo_ispezione");
                        lookup_tipo_ispezione.buildListWithEnabled(db);

                        context.getRequest().setAttribute("Motivazione",
                                        lookup_tipo_ispezione);
                        
                        LookupList quesiti_diagn = new LookupList(db, "quesiti_diagnostici_sigla");
                context.getRequest().setAttribute("QuesitiList", quesiti_diagn);

                        
                        LookupList lookup_piani12= new LookupList();
                        lookup_piani12.setTableName("lookup_piano_monitoraggio");
                        lookup_piani12.buildListWithEnabled(db);
                        LookupList lookup_piani= new LookupList(db,
                        "lookup_piano_monitoraggio");

                        context.getRequest().setAttribute("Piani",
                                        lookup_piani);
                        context.getRequest().setAttribute("Piani2",
                                        lookup_piani12);

                        LookupList EsitoCampione = new LookupList(db,
                        "lookup_esito_campione");
                        EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
                        context.getRequest().setAttribute("EsitoCampione", EsitoCampione);

                        LookupList DestinatarioCampione = new LookupList(db,
                        "lookup_destinazione_campione");
                        DestinatarioCampione.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("DestinatarioCampione",
                                        DestinatarioCampione);


                        // aggiunto da d.dauria


                        // aggiunto da d.dauria
                        LookupList conseguenzepositivita = new LookupList(db,
                        "lookup_conseguenze_positivita");
                        conseguenzepositivita.addItem(-1, "-- SELEZIONA VOCE --");
                        context.getRequest().setAttribute("ConseguenzePositivita",
                                        conseguenzepositivita);


                        // aggiunto da d.dauria
                        LookupList responsabilitapositivita = new LookupList(db,
                        "lookup_responsabilita_positivita");
                        responsabilitapositivita.addItem(-1, "-- SELEZIONA VOCE --");
                        context.getRequest().setAttribute("ResponsabilitaPositivita",
                                        responsabilitapositivita);

                        
                        LookupList SiteIdList = new LookupList(db, "lookup_site_id");
                        SiteIdList.addItem(-1, "-- SELEZIONA VOCE --");
                        SiteIdList.addItem(-2, "-- TUTTI --"
                        );
                        context.getRequest().setAttribute("SiteIdList", SiteIdList);

                        if (tipologiaOperatore>0)
                        	newTic.setTipologia_operatore(tipologiaOperatore);
                        
                        context.getRequest().setAttribute("TicketDetails", newTic);
                        context.getRequest().setAttribute("idC", id_controllo);
                        
                        //Gestione SIN nei diversi moduli
                        //VERIFICA SE LA SCHEDA E' STATA INVIATA O MENO
                          String select = "select id_bdn from scheda_campioni_sin where A12 = ?";
                    PreparedStatement pst = db.prepareStatement(select);
                    pst.setString(1, newTic.getIdentificativo());
                    ResultSet rs = pst.executeQuery();
                    int id_bdn = 0;
                    while(rs.next()){
                            id_bdn = rs.getInt("id_bdn");
                    }
                if (id_bdn > 0) {
                        context.getRequest().setAttribute("messaggio_sin","La scheda SIN risulta gia' inviata per questo campione.");
                }
                
                        context.getRequest().setAttribute("link_visibile", "no");
                
                        String piani_sin = ApplicationProperties.getProperty("MOD_SIN");
                        context.getRequest().setAttribute("mod_sin", piani_sin );
                        
                        
                //La visibilita' dei link e' legata al piano
                if(lookup_piani12.getSelectedValue(newTic.getMotivazione_piano_campione()).contains("57 A")){
                        //Ovicaprini
                        context.getRequest().setAttribute("link_mod_a","si");
                        context.getRequest().setAttribute("tipoSin","latteA");
                }
                else {
                        context.getRequest().setAttribute("link_mod_a","no");
                        context.getRequest().setAttribute("link_mod_c","no");
                }
                
                if(lookup_piani12.getSelectedValue(newTic.getMotivazione_piano_campione()).contains("57 B")){
                        //mitili ovvero molluschi
                        context.getRequest().setAttribute("link_mod_b","si");
                        context.getRequest().setAttribute("tipoSin","latteB");
                }
                else {
                        context.getRequest().setAttribute("link_mod_b","no");
                }
                
                /*
                 * con i nuovi piani c1 e c2, d1 e d2..avremo
                 *
                 * */
                if(piani_sin.equals("NEW")){
                
                        if (lookup_piani12.getSelectedValue(newTic.getMotivazione_piano_campione()).contains("57 C1")){
                                //Pescato
                                context.getRequest().setAttribute("link_mod_c1","si");
                                context.getRequest().setAttribute("tipoSin","latteC1");
                        }else {
                                context.getRequest().setAttribute("link_mod_c1","no");
                        }
                            
                        if (lookup_piani12.getSelectedValue(newTic.getMotivazione_piano_campione()).contains("57 C2")){
                                
                                context.getRequest().setAttribute("link_mod_c2","si");
                                context.getRequest().setAttribute("tipoSin","latteC2");
                                
                        }else {
                                context.getRequest().setAttribute("link_mod_c2","no");
                        }
                        
                        if (lookup_piani12.getSelectedValue(newTic.getMotivazione_piano_campione()).contains("57 D")){
                                
                                context.getRequest().setAttribute("link_mod_d","si");
                                context.getRequest().setAttribute("tipoSin","latteD");
                        }else {
                                context.getRequest().setAttribute("link_mod_d","no");
                        }
                      
                        
                } else {
                        
                        if (lookup_piani12.getSelectedValue(newTic.getMotivazione_piano_campione()).contains("57 C")){
                                //Pescato
                                context.getRequest().setAttribute("link_mod_d","si");
                        }else {
                                context.getRequest().setAttribute("link_mod_d","no");
                        }
                
                        if (lookup_piani12.getSelectedValue(newTic.getMotivazione_piano_campione()).contains("57 D")){
                                context.getRequest().setAttribute("link_mod_c","si");
                        }else {
                                context.getRequest().setAttribute("link_mod_c","no");
                        }
                        
                }
                
              //PER COMPLETARE CAMPI VUOTI DEI CAMPIONI
                        ticketId = context.getRequest().getParameter("id");
                        String flag = "-1";
                        String motivazione = null,numero_verbale = null,data_prelievo = null,matrice = null,analita = null;

                        String sql = "select motivazione_campione,location,assigned_date from ticket where ticketid=?";
                        pst=db.prepareStatement(sql);
                        pst.setInt(1,Integer.parseInt(ticketId));
                        rs = pst.executeQuery();
                        if(rs.next()){
                                if(rs.getInt(1)>0)
                                        motivazione = String.valueOf(rs.getInt(1));
                                else
                                        motivazione="";
                                numero_verbale = rs.getString(2);
                                data_prelievo = rs.getString("assigned_date");
                        }
                        
                        sql = "select id_matrice from matrici_campioni where id_campione=?";
                        pst=db.prepareStatement(sql);
                        pst.setInt(1,Integer.parseInt(ticketId));
                        rs = pst.executeQuery();
                        if(rs.next()){
                                matrice = String.valueOf(rs.getInt(1));
                        }
                        
                        sql = "select analiti_id from analiti_campioni where id_campione=?";
                        pst=db.prepareStatement(sql);
                        pst.setInt(1,Integer.parseInt(ticketId));
                        rs = pst.executeQuery();
                        if(rs.next()){
                                analita = String.valueOf(rs.getInt(1));
                        }
                                
                        //ELENCO MOTIVAZIONI
                        ArrayList<String> elencoMotivazioni = new ArrayList<String>();
                        if(motivazione==null || motivazione.equals("")){
                                sql = "select id_controllo_ufficiale from ticket where ticketid=?";
                                pst=db.prepareStatement(sql);
                                pst.setInt(1,Integer.parseInt(ticketId));
                                rs = pst.executeQuery();
                                String idc=null;
                                if(rs.next()){
                                        idc = rs.getString(1);
                                }
                                
                                sql = "select tipoispezione,pianomonitoraggio,bpi from tipocontrolloufficialeimprese where idcontrollo=?";
                                String sql2= "";
                                PreparedStatement pst2;
                                ResultSet rs2;
                                pst=db.prepareStatement(sql);
                                pst.setInt(1,Integer.parseInt(idc));
                                rs = pst.executeQuery();
                                String tipo=null,piano=null,audit=null,description=null;
                                while(rs.next()){
                                        tipo=String.valueOf(rs.getInt(1));
                                        if(!tipo.equals("-1")){
                                                if(tipo.equals("2")){
                                                        sql2="select description from lookup_piano_monitoraggio where code=?";
                                                        piano=String.valueOf(rs.getInt(2));
                                                        pst2=db.prepareStatement(sql2);
                                                        pst2.setInt(1, Integer.parseInt(piano));
                                                        rs2 = pst2.executeQuery();
                                                        while(rs2.next()){
                                                                description=rs2.getString("description");
                                                        }
                                                        elencoMotivazioni.add(piano+"---"+description);
                                                }
                                                else{
                                                        sql2="select description from lookup_tipo_ispezione where code=?";
                                                        pst2=db.prepareStatement(sql2);
                                                        pst2.setInt(1, Integer.parseInt(tipo));
                                                        rs2 = pst2.executeQuery();
                                                        while(rs2.next()){
                                                                description=rs2.getString("description");
                                                        }
                                                        elencoMotivazioni.add(tipo+"---"+description);
                                                }
                                        }
                                }
                                elencoMotivazioni.add("24---CLASSIFICAZIONE");
                                elencoMotivazioni.add("41---SOSPETTA CONTAMINAZIONE");
                                elencoMotivazioni.add("42---SOSPETTA MALATTIA INFETTIVA/PARASSITARIA");
                                elencoMotivazioni.add("22---SOSPETTO PRESENZA N.C.");
                                elencoMotivazioni.add("43---SOSPETTO TRATTAMENTO FARMACOLOGICO");
                        }
                        
                        if (motivazione==null || numero_verbale==null || data_prelievo==null || matrice==null || analita==null ||
                                motivazione.equals("") || numero_verbale.equals("") || data_prelievo.equals("") || matrice.equals("") || analita.equals("")){
                                flag="1";
                                context.getRequest().setAttribute("flag", flag);
                                context.getRequest().setAttribute("ck_mot", motivazione);
                                context.getRequest().setAttribute("ck_nv", numero_verbale);
                                context.getRequest().setAttribute("ck_dp", data_prelievo);
                                context.getRequest().setAttribute("ck_mat", matrice);
                                context.getRequest().setAttribute("ck_an", analita);
                                context.getSession().setAttribute("elencoMotivazioni", elencoMotivazioni);
                        }
                        
                        HashMap<Integer,String> matrici= newTic.getMatrici();
                        Iterator<Integer> itMatrici = matrici.keySet().iterator();
                        int i = 0 ;
                        boolean trovato = false;
                        String descrizione = "";
                        while(itMatrici.hasNext())
                        {
                                i++ ;
                                int chiave = itMatrici.next();
                                descrizione += newTic.getMatrici().get(chiave);
                                if(descrizione.toLowerCase().contains("mangim")){
                                        trovato = true;
                                }
                        }
                        
                        
                        //Gestione
                        String msg = "";
                        if(newTic.getMotivazione_piano_campione() > 0){
                                msg = lookup_piani12.getSelectedValue(newTic.getMotivazione_piano_campione());
                        } else if (newTic.getMotivazione_campione() > 0 ){
                                if(newTic.getMotivazione_campione() == 22 || newTic.getMotivazione_campione() == 9 || newTic.getMotivazione_campione()==41 || newTic.getMotivazione_campione()==42 || newTic.getMotivazione_campione()==43 )
                                        msg = "sospetto";
                        }
                        
                        String idCUstring = newTic.getIdControlloUfficiale();
                        int idCU = -1;
                        if (idCUstring!=null && !idCUstring.equals(""))
                        	idCU = Integer.parseInt(idCUstring);
                        org.aspcf.modules.controlliufficiali.base.Organization org = new org.aspcf.modules.controlliufficiali.base.Organization(db,idCU);
                         
                        
                        if( (msg.equals("sospetto") && trovato) || cercaPNAA(db, newTic.getMotivazione_piano_campione(),idCU)) {
                                
                                
                                context.getRequest().setAttribute("gestione_pnaa", "true");
                        }
                        else {
                                context.getRequest().setAttribute("gestione_pnaa", "false");
                        }
                        
                        //NEW GESTIONE DELLA SPECIE PER I PIANI PNBA, PNAA E PNR
                        //kiave = nomeCampo
                    	// value = hashmap<String ,String>
                    	//kiave = valorecampo
             
                    	recuperaSpeciePiani(newTic.getId(), newTic, db);
                    	context.getRequest().setAttribute("listaCampiAggiuntivi", newTic.getListaCampiAggiuntivi());
                    	LookupList specie = new LookupList(db, "lookup_specie_pnaa");
          		        context.getRequest().setAttribute("SpecieCategoria", specie);
          		        
          		        //Controllo se e' gia' stata salvata la scheda
          		    //    controllaEsistenzaSchedaModulo(db, newTic.getId(), context);
                        
                        //|| .getCode()== 22  || el.getCode()==24 || el.getCode()==41 || el.getCode()==42 || el.getCode()==43) && el.isEnabled()==false)
                
                        //retPag = "DettaglioOK";

                        addRecentItem(context, newTic);
                        addModuleBean(context, "View Accounts", "View Tickets");
                        // Reset any pagedLists since this could be a new visit to this
                        // ticket
                        deletePagedListInfo(context, "AccountTicketsFolderInfo");
                        deletePagedListInfo(context, "AccountTicketDocumentListInfo");
                        deletePagedListInfo(context, "AccountTicketTaskListInfo");
                        deletePagedListInfo(context, "accountTicketPlanWorkListInfo");
//                        TicketCategoryList ticketCategoryList = new TicketCategoryList();
//                        ticketCategoryList.setEnabledState(Constants.TRUE);
//                        ticketCategoryList.setSiteId(newTic.getSiteId());
//                        ticketCategoryList.setExclusiveToSite(true);
//                        ticketCategoryList.buildList(db);
//                        context.getRequest().setAttribute("ticketCategoryList",
//                                        ticketCategoryList);        
                } catch (Exception errorMessage) {
                        context.getRequest().setAttribute("Error", errorMessage);

                }
                // return getReturn(context, "TicketDetails");
                return "";
        }
        
        public static void recuperaSpeciePiani(int idCampione, Ticket campione , Connection db){
    		
    		try {
    			  	
    		  	PreparedStatement pst = db.prepareStatement
    					("SELECT * FROM campioni_fields_value join campioni_html_fields v2 on v2.id = id_campioni_html_fields  " +
    							"WHERE id_campione = ?");
    			
    			pst.setInt(1, idCampione);
    			ResultSet rs = pst.executeQuery();
    			
    			while(rs.next()){
    				
    				if (campione.getListaCampiAggiuntivi().get(rs.getString("nome_campo"))!= null)
    				{
    					HashMap<String, String> valori_x_nome_campo = campione.getListaCampiAggiuntivi().get(rs.getString("nome_campo")) ;
    					
    					if (rs.getBoolean("multiple")==false)
    						valori_x_nome_campo.put(rs.getString("nome_campo"), rs.getString("valore_campione"));
    					else
    						valori_x_nome_campo.put(rs.getString("nome_campo")+"."+rs.getString("valore_campione"), rs.getString("valore_campione"));

    					campione.getListaCampiAggiuntivi().put(rs.getString("nome_campo"), valori_x_nome_campo);
    				}
    				else
    				{
    					HashMap<String, String> valori_x_nome_campo = new HashMap<String, String>();
    					
    					if (rs.getBoolean("multiple")==false)
    						valori_x_nome_campo.put(rs.getString("nome_campo"), rs.getString("valore_campione"));
    					else
    						valori_x_nome_campo.put(rs.getString("nome_campo")+"."+rs.getString("valore_campione"), rs.getString("valore_campione"));

    					campione.getListaCampiAggiuntivi().put(rs.getString("nome_campo"), valori_x_nome_campo);
    				}
    						
    			}
    				
    			
    		} catch (Exception e){
    			e.printStackTrace();
    		
    		}
    		
    	}
        

        public String executeCommandViewCompletaCampione(ActionContext context) {
                String retPag;
                String id = (String)context.getRequest().getParameter("id");
                String orgId = (String)context.getRequest().getParameter("orgId");
                String motivazione = (String)context.getRequest().getParameter("ck_mot");
                String numero_verbale = (String)context.getRequest().getParameter("ck_nv");
                String data_prelievo = (String)context.getRequest().getParameter("ck_dp");
                String matrice = (String)context.getRequest().getParameter("ck_mat");
                String analita = (String)context.getRequest().getParameter("ck_an");
                ArrayList<String> elencoMotivazioni = (ArrayList<String>) context.getSession().getAttribute("elencoMotivazioni");
                String input = (String)context.getRequest().getParameter("input");
                        
                Connection db = null;
        
                        
                        Ticket newTic = new Ticket();
                        
                        try {
                                db = this.getConnection(context);
                                SystemStatus systemStatus = this.getSystemStatus(context);
                                newTic.setSystemStatus(systemStatus);
                                newTic.queryRecord(db, Integer.parseInt(id));
                        } catch (SQLException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                        finally
                        {
                                this.freeConnection(context, db);
                        }
                
                context.getRequest().setAttribute("TicketDetails", newTic);
                retPag = "ViewCompletaCampione";
                return retPag;
        }
        
        public String executeCommandUpdateCompletaCampione(ActionContext context) {
                
                Ticket newTic = (Ticket) context.getRequest().getAttribute("TicketDetails");

                String id = (String)context.getRequest().getParameter("id");
                String orgId = (String)context.getRequest().getParameter("orgId");
                
                String motivazione = (String)context.getRequest().getParameter("id_mot");
                String motivazione_descr = (String)context.getRequest().getParameter("descr_mot");
                
                String numero_verbale = (String)context.getRequest().getParameter("numero_verbale_add");
                String data_prelievo = (String)context.getRequest().getParameter("data_prelievo_add");
                
                String idmatrice = (String)context.getRequest().getParameter("idMatrice_1");
                String matrice_path = (String)context.getRequest().getParameter("path_1");
                String noteMatrici = (String)context.getRequest().getParameter("noteMatrici");
                
                String input = (String)context.getRequest().getParameter("input");
                
                String n = (String)context.getRequest().getParameter("size");
                ArrayList<String> analiti = new ArrayList<String>();
                if (n!=null && Integer.parseInt(n)>0) {
                        for (int i=0;i<Integer.parseInt(n);i++){
                                String idanalita = (String)context.getRequest().getParameter("analitiId_"+(i+1));
                                String analita = (String)context.getRequest().getParameter("pathAnaliti_"+(i+1));
                                String noteAnalisi = (String)context.getRequest().getParameter("noteAnalisi");
                                analiti.add(i,idanalita+"---"+analita+"---"+noteAnalisi);
                                idanalita=null; analita=null;
                        }
                }
                Connection db = null;
                try {
                        db = this.getConnection(context);
                        LookupList quesiti_diagn = new LookupList(db, "quesiti_diagnostici_sigla");
                context.getRequest().setAttribute("QuesitiList", quesiti_diagn);
                        
                        String sql = "update ticket set location=? where ticketid="+id;
                        PreparedStatement pst = db.prepareStatement(sql);
                        
                        if (numero_verbale!=null){
                                pst.setString(1,numero_verbale);
                                pst.executeUpdate();
                        }
                        
                        sql = "update ticket set assigned_date=? where ticketid="+id;
                        pst = db.prepareStatement(sql);
                        if (data_prelievo!=null) {
                                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                Date date = null;
                                try {
                                        date = dateFormat.parse(data_prelievo);
                                } catch (ParseException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                }
                                long time = date.getTime();
                                Timestamp ts = new Timestamp(time);
                                pst.setTimestamp(1, ts);
                                pst.executeUpdate();
                        }                        
                        
                        if (motivazione!=null && !motivazione.equals("-2")) {
                                
                                String nomeTabella="";
                                pst = db.prepareStatement("select short_description from quesiti_diagnostici_sigla where code="+motivazione);
                                ResultSet rs = pst.executeQuery();
                                while(rs.next()){
                                        nomeTabella = rs.getString("short_description");
                                }
                                if(nomeTabella.equals("lookup_piano_monitoraggio") && nomeTabella!=null){
                                        sql = "update ticket set motivazione_campione=2, motivazione_piano_campione=? where ticketid="+id;
                                } else if(nomeTabella.equals("lookup_tipo_ispezione") && nomeTabella!=null){
                                        sql = "update ticket set motivazione_campione=? where ticketid="+id;
                                } else { //non in vista SIGLA (NO CODICE ESAME)
                                        String description = "";
                                        pst = db.prepareStatement("select description from lookup_piano_monitoraggio where code="+motivazione);
                                        rs = pst.executeQuery();
                                        while(rs.next()){
                                                description = rs.getString("description");
                                        }
                                        if (description.equals(motivazione_descr)){
                                                sql = "update ticket set motivazione_campione=2, motivazione_piano_campione=? where ticketid="+id;
                                        }
                                        else {
                                                sql = "update ticket set motivazione_campione=? where ticketid="+id;
                                        }
                                }
                                        
                                pst = db.prepareStatement(sql);
                                pst.setInt(1, Integer.parseInt(motivazione));
                                pst.executeUpdate();
                        }
                        else motivazione=null;
                        
                        sql = "insert into matrici_campioni (id_campione,id_matrice,cammino,note) values (?,?,?,?)";
                        if (idmatrice!=null) {
                                pst = db.prepareStatement(sql);
                                pst.setInt(1,Integer.parseInt(id));
                                pst.setInt(2, Integer.parseInt(idmatrice));
                                pst.setString(3, matrice_path);
                                if (noteMatrici.equals(""))
                                        pst.setString(4, "");
                                else
                                        pst.setString(4, noteMatrici);
                                pst.executeUpdate();
                        }
                        
                        sql = "insert into analiti_campioni (id_campione,analiti_id,cammino,note) values (?,?,?,?)";
                        if (n!=null && Integer.parseInt(n)>0) {
                                for (int i=0;i<Integer.parseInt(n);i++){
                                        pst = db.prepareStatement(sql);
                                        String split[]=analiti.get(i).split("---");
                                        pst.setInt(1,Integer.parseInt(id));
                                        pst.setInt(2, Integer.parseInt(split[0]));
                                        pst.setString(3, split[1]);
                                        if (split.length==3)
                                                pst.setString(4, split[2]);
                                        else
                                                pst.setString(4, "");
                                        pst.executeUpdate();
                                }
                        }
                } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                finally {
                        this.freeConnection(context, db);
                }                
                context.getRequest().setAttribute("id", id);
                context.getRequest().setAttribute("orgId", orgId);
                context.getRequest().setAttribute("input", input);
                context.getRequest().setAttribute("TicketDetails",newTic);

                return "ViewDetails";
        }
        
        public String executeCommandViewElencoCampioniPrenotati(ActionContext context){
                if (!hasPermission(context, "accounts-accounts-campioniprenotati-view")) {
                        return ("PermissionError");
                }
                String op = context.getRequest().getParameter("op");
                context.getRequest().setAttribute("op", op);
                ArrayList<String> data = new ArrayList<String>();
                ArrayList<String> idCU = new ArrayList<String>();
                ArrayList<String> elencoCU = new ArrayList<String>();
                Connection db = null;
                Organization newOrg = null;
                int passedId = Integer.parseInt(context.getRequest().getParameter("orgId"));
                try {
                        db = this.getConnection(context);
                        newOrg = new Organization(db, passedId);
                        
                        //LISTA CAMPIONI PRENOTATI
                        PreparedStatement pst = db.prepareStatement("select ticketid from ticket where trashed_date is null and id_controllo_ufficiale='-1' and tipologia=2 and org_id =" +passedId);
                        ResultSet rs = pst.executeQuery();
                        while (rs.next()){
                                data.add(rs.getObject(1).toString());
                        }
                        
                        //LISTA CU APERTI, NON CANCELLATI, NON IN SORVEGLIANZA E NON AUDIT
                        pst = db.prepareStatement("select id_controllo_ufficiale from ticket where closed is null and tipologia=3 and trashed_date is null and provvedimenti_prescrittivi!=5 and provvedimenti_prescrittivi!=3 and org_id="+passedId+" order by id_controllo_ufficiale");
                        rs = pst.executeQuery();
                        while (rs.next()){
                                idCU.add(rs.getString(1));
                        }
                        
                        //ID MOTIVAZIONE PIANO DEL CAMPIONE (N.D. se non settata)
                        for (int i=0; i<data.size(); i++){
                                pst = db.prepareStatement("select motivazione_piano_campione,motivazione_campione from ticket where tipologia=2 and ticketid="+data.get(i)+" and org_id="+passedId);
                                rs = pst.executeQuery();
                
                                while (rs.next()){
                                        String m1 = rs.getString(1); if (m1==null) m1="N.D.";
                                        String m2 = rs.getString(2); if (m2==null) m2="N.D.";
                                        data.set(i, data.get(i)+"---"+m1+"---"+m2);
                                }
                                
                                //DESCRIPTION PER VISUALIZZAZIONE
                                String split3[] = data.get(i).split("---");
                                
                                if(!split3[1].equals("N.D.")){
                                        pst = db.prepareStatement("select description from lookup_piano_monitoraggio where code="+split3[1]);
                                        rs = pst.executeQuery();
                                        if (rs.next()){data.set(i, data.get(i)+"---"+rs.getString(1));}
                                }
                                else {
                                        if (!split3[2].equals("N.D.")){
                                                pst = db.prepareStatement("select description from lookup_tipo_ispezione where code="+split3[2]);
                                                rs = pst.executeQuery();
                                                if (rs.next()){data.set(i, data.get(i)+"---"+rs.getString(1));}
                                        }
                                        else {data.set(i, data.get(i)+"---N.D.");}
                                }
                        }
                        
                        //FILTRA ELENCO CU PER CAMPIONE
                        String split[];
                        String listCU = "";
                        String app = "";
                        for (int i=0;i<idCU.size()-1;i++)
                                listCU=listCU+Integer.parseInt(idCU.get(i))+",";
                        if(idCU.size()>=1){
                                listCU = listCU+idCU.get(idCU.size()-1);
                        }
                        if(idCU.size()>0){
                                for (int i=0; i<data.size(); i++){
                                        app="";
                                        split = data.get(i).split("---");
                                        if (!split[1].equals("N.D.")){
                                                pst = db.prepareStatement("select distinct idcontrollo from tipocontrolloufficialeimprese where pianomonitoraggio="+split[1]+" and idcontrollo in ("+listCU+")");
                                                rs = pst.executeQuery();
                                                while(rs.next()){
                                                        app=rs.getObject(1).toString()+"---"+app;
                                                }
                                                if (app.equals("")){ //c'e' motivazione ma nn ci sono CU corrispondenti (no lista CU)
                                                        String app2="N.D.";
                                                        elencoCU.add(app2);
                                                }
                                                else {                                //ci sono CU con la stessa motivazione
                                                        elencoCU.add(app);
                                                        app="";
                                                }
                                        }
                                        else {
                                                if (!split[2].equals("N.D.")){
                                                        pst = db.prepareStatement("select distinct t.id_controllo_ufficiale from ticket t join tipocontrolloufficialeimprese tc on t.ticketid=tc.idcontrollo join lookup_tipo_ispezione lti on lti.code=tc.tipoispezione where t.tipologia = 3 and code="+split[2]+" and org_id="+passedId+" and t.ticketid in ("+listCU+")");
                                                        rs = pst.executeQuery();
                                                        while(rs.next()){
                                                                app=rs.getObject(1).toString()+"---"+app;
                                                        }
                                                        if (app.equals("")){ //c'e' motivazione ma nn ci sono CU corrispondenti (no lista CU)
                                                                String app2="N.D.";
                                                                elencoCU.add(i, app2);
                                                        }
                                                        else {                                //ci sono CU con la stessa motivazione
                                                                elencoCU.add(app);
                                                                app="";
                                                        }
                                                }
                                                else { //non c'e' motivazione (imposta tutti i CU)
                                                        String app2="";
                                                        for (int l=0;l<idCU.size()-1;l++)
                                                                app2=idCU.get(l)+"---"+app2;
                                                        app2=app2+idCU.get(idCU.size()-1);
                                                        elencoCU.add(app2);
                                                }
                                        }
                                }                
                        }        
                 } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                finally {
                        this.freeConnection(context, db);
                }
                context.getRequest().setAttribute("elencoCU", elencoCU);
                context.getRequest().setAttribute("data", data);
                context.getRequest().setAttribute("idCU", idCU);
                context.getRequest().setAttribute("OrgDetails", newOrg);
                return "ViewElencoCampioniPrenotati";
        }
        
        public String executeCommandUpdateElencoCampioniPrenotati(ActionContext context){
                String idCU,ckbox;
                int n = Integer.parseInt((String) context.getRequest().getParameter("NElem"));
                String op = context.getRequest().getParameter("op");
                context.getRequest().setAttribute("op", op);
                Connection db = null;
                Organization newOrg = null;
                int passedId = Integer.parseInt(context.getRequest().getParameter("orgId"));
                ArrayList<String> data = new ArrayList<String>();
                String s;
                String split[];
                try {
                        db = this.getConnection(context);
                        newOrg = new Organization(db, passedId);
                        PreparedStatement pst = null;
                        for (int i=0;i<n;i++){
                                s = (String) context.getRequest().getParameter("data"+i);
                                idCU = (String) context.getRequest().getParameter("CU"+i);
                                ckbox = (String) context.getRequest().getParameter("ckbox"+i);
                                data.add(s);
                
                                if (ckbox!=null && !idCU.equals("N.D.")){
                                        split = data.get(i).split("---");
                                        pst = db.prepareStatement("update ticket set id_controllo_ufficiale=? where org_id=? and ticketid=?");
                                        pst.setString(1, idCU);
                                        pst.setInt(2, passedId);
                                        pst.setInt(3, Integer.parseInt(split[0]));
                                        pst.executeUpdate();
                                        
                                        pst = db.prepareStatement("update barcode_osa set ticket_id=? where org_id=? and id_campione='"+split[0]+"'");
                                        pst.setInt(1, Integer.parseInt(idCU));
                                        pst.setInt(2, passedId);
                                        pst.executeUpdate();

                                        idCU=null;
                                        split=null;                                        
                                }
                        }
                } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                finally {
                        this.freeConnection(context, db);
                }
                context.getRequest().setAttribute("OrgDetails", newOrg);
                executeCommandViewElencoCampioniPrenotati(context);
                return "ViewElencoCampioniPrenotati";
        }

        public String executeCommandViewElencoPrenotazioni(ActionContext context){
                /*Gestione del permesso...campioni-prenotazione-view*/


                String orgId = (String)context.getRequest().getParameter("orgId");
                Connection db = null;
                try {
                        db = this.getConnection(context);
                        // Load the ticket

                        org.aspcfs.modules.campioni.base.TicketList ticList = new org.aspcfs.modules.campioni.base.TicketList();
                        int passedId = Integer.parseInt(orgId);
                        ticList.setOrgId(passedId);
                        ticList.buildListCampioniPrenotati(db, passedId);
                        context.getRequest().setAttribute("orgId", orgId);
                        context.getRequest().setAttribute("TicList", ticList);

                }catch(SQLException ex){
                        ex.printStackTrace();
                }

                return "PrenotaDetailsOK";

        }




        /**
         * Description of the Method
         *
         * @param context
         *            Description of the Parameter
         * @return Description of the Return Value
         */
        public String executeCommandRestoreTicket(ActionContext context) {

                boolean recordUpdated = false;
                Connection db = null;
                Ticket thisTicket = null;
                try {
                        db = this.getConnection(context);
                        thisTicket = new Ticket(db, Integer.parseInt(context.getRequest().getParameter("id")));
                        //check permission to record
                        if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
                                return ("PermissionError");
                        }
                        thisTicket.setModifiedBy(getUserId(context));
                        recordUpdated = thisTicket.updateStatus(db, false, this.getUserId(context));
                } catch (Exception errorMessage) {
                        context.getRequest().setAttribute("Error", errorMessage);

                } finally {
                        this.freeConnection(context, db);
                }
                context.getRequest().setAttribute("recordUpdated",recordUpdated);

                return "";
        }






        /**
         * Update the specified ticket
         *
         * @param context
         *            Description of Parameter
         * @return Description of the Returned Value
         */
        public String executeCommandUpdateTicket(ActionContext context) {

                
                int resultCount = 0;
                Connection db = null ;
                int catCount = 0;
                TicketCategory thisCat = null;
                boolean catInserted = false;
                boolean isValid = true;

                Ticket newTic = (Ticket) context.getFormBean();
                newTic.setEsitoCampione(Integer.parseInt(context.getRequest().getParameter("EsitoCampione")));
                newTic.setTxt_desc_non_accettato(context.getRequest().getParameter("txt_desc_non_accettato"));
                newTic.setId(Integer.parseInt(context.getRequest().getParameter("id")));
                if (context.getRequest().getParameter("microchipMatriciCanili")!=null)
                        newTic.setMicrochip( context.getRequest().getParameter("microchipMatriciCanili") );

                if(context.getRequest().getParameter("animalinonalimentarivalue") != null) {
                        newTic.setAnimaliNonAlimentariCombo(Integer.parseInt(context.getRequest().getParameter("animalinonalimentarivalue")));
                }



                try {
                        
                        db = this.getConnection(context);
                        Ticket oldTic = new Ticket(db, newTic.getId());
                        

                        if(context.getRequest().getParameter("idControlloUfficiale")!=null){
                                newTic.setIdControlloUfficiale(context.getRequest().getParameter("idControlloUfficiale"));
                                String id_controllo=newTic.getIdControlloUfficiale();
                                while(id_controllo.startsWith("0")){

                                        id_controllo=id_controllo.substring(1);
                                }

                                context.getRequest().setAttribute("idC", id_controllo);


                        }
                        
                        String scelta = ApplicationProperties.getProperty("HTML_PDF");
                        context.getRequest().setAttribute("html_or_pdf",scelta );
                        
                        SystemStatus systemStatus = this.getSystemStatus(context);
                        LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
                        TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
                        context.getRequest().setAttribute("TipoCampione", TipoCampione);

                        LookupList quesiti_diagn = new LookupList(db, "quesiti_diagnostici_sigla");
                quesiti_diagn.addItem(-1, "-- SELEZIONA VOCE --");
                context.getRequest().setAttribute("QuesitiList", quesiti_diagn);
                        
                        LookupList EsitoCampione = new LookupList(db,
                        "lookup_esito_campione");
                        EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
                        context.getRequest().setAttribute("EsitoCampione", EsitoCampione);


                        /*LookupList lookup_tipo_ispezione = new LookupList(db,
                        "lookup_tipo_ispezione");

                        context.getRequest().setAttribute("Motivazione",
                                        lookup_tipo_ispezione);
                         */
                        
                        LookupList lookup_tipo_ispezione = new LookupList();
                        lookup_tipo_ispezione.setTable("lookup_tipo_ispezione");
                        lookup_tipo_ispezione.buildListWithEnabled(db);
                        
                        org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket();
                        cu.queryRecord(db, Integer.parseInt(context.getRequest().getParameter("idControlloUfficiale")));
                        
                        HashMap<Integer, String> tipo_ispezone_valori = cu.getTipoIspezione();


                        int size = lookup_tipo_ispezione.size();
                        int ind = 0 ;
                        for ( ind = 0 ; ind<size;ind++)
                        {
                                LookupElement el = (LookupElement)lookup_tipo_ispezione.get(ind) ;
                                if ((tipo_ispezone_valori.containsKey(el.getCode())==false) &&  
                                		!el.getCodiceInterno().equalsIgnoreCase("22a") &&
                                		!el.getCodiceInterno().equalsIgnoreCase("24a")&& 
                                		!el.getCodiceInterno().equalsIgnoreCase("41a") &&
                                		!el.getCodiceInterno().equalsIgnoreCase("42a") && 
                                		!el.getCodiceInterno().equalsIgnoreCase("43a"))
                                {
                                        lookup_tipo_ispezione.remove(ind);
                                        size-=1 ;
                                        ind =-1 ;
                                }
                                

                        }
                        
                        for ( ind = 0 ; ind<size;ind++)
                        {
                                LookupElement el = (LookupElement)lookup_tipo_ispezione.get(ind) ;
                                if( (el.getCodiceInterno().equalsIgnoreCase("22a")  || el.getCodiceInterno().equalsIgnoreCase("24a") ||
                                		el.getCodiceInterno().equalsIgnoreCase("41a") || el.getCodiceInterno().equalsIgnoreCase("42a") |el.getCodiceInterno().equalsIgnoreCase("43a")) 
                                		&& el.isEnabled()==false)
                                {
                                        lookup_tipo_ispezione.remove(ind);
                                        el.setEnabled(true);
                                        lookup_tipo_ispezione.add(ind, el);
                                }
                        }
                        
                        lookup_tipo_ispezione.addItem(-1,
                                        "-- SELEZIONA VOCE --");
                        context.getRequest().setAttribute("Motivazione", lookup_tipo_ispezione);
                        
                        
                        
                        LookupList lookup_piani12= new LookupList();
                        lookup_piani12.setTableName("lookup_piano_monitoraggio");
                        lookup_piani12.buildListWithEnabled(db);
                        LookupList lookup_piani= new LookupList(db,
                        "lookup_piano_monitoraggio");

                        context.getRequest().setAttribute("Piani",
                                        lookup_piani);
                        context.getRequest().setAttribute("Piani2",
                                        lookup_piani12);



                        // aggiunto da d.dauria
                        LookupList conseguenzepositivita = new LookupList(db,
                        "lookup_conseguenze_positivita");
                        conseguenzepositivita.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("ConseguenzePositivita",
                                        conseguenzepositivita);


                        // aggiunto da d.dauria
                        LookupList responsabilitapositivita = new LookupList(db,
                        "lookup_responsabilita_positivita");
                        responsabilitapositivita.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("ResponsabilitaPositivita",
                                        responsabilitapositivita);


                        LookupList DestinatarioCampione = new LookupList(db,
                        "lookup_destinazione_campione");
                        DestinatarioCampione.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("DestinatarioCampione",
                                        DestinatarioCampione);

                        LookupList SiteIdList = new LookupList(db, "lookup_site_id");
                        SiteIdList.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        SiteIdList.addItem(-2, "-- TUTTI --"
                        );
                        context.getRequest().setAttribute("SiteIdList", SiteIdList);


                        //Gestione SIN nei diversi moduli
                        //VERIFICA SE LA SCHEDA E' STATA INVIATA O MENO
                          String select = "select id_bdn from scheda_campioni_sin where A12 = ?";
                    PreparedStatement pst = db.prepareStatement(select);
                    pst.setString(1, newTic.getIdentificativo());
                    ResultSet rs = pst.executeQuery();
                    int id_bdn = 0;
                    while(rs.next()){
                            id_bdn = rs.getInt("id_bdn");
                    }
                if (id_bdn > 0) {
                        context.getRequest().setAttribute("messaggio_sin","La scheda SIN risulta gia' inviata per questo campione.");
                }
                
                        context.getRequest().setAttribute("link_visibile", "no");
                
                        String piani_sin = ApplicationProperties.getProperty("MOD_SIN");
                        context.getRequest().setAttribute("mod_sin", piani_sin );
                        
                        
//                //La visibilita' dei link e' legata al piano
//                if(lookup_piani12.getSelectedValue(newTic.getMotivazione_piano_campione()).contains("57 A")){
//                        //Ovicaprini
//                        context.getRequest().setAttribute("link_mod_a","si");
//                
//                }
//                else {
//                        context.getRequest().setAttribute("link_mod_a","no");
//                        context.getRequest().setAttribute("link_mod_c","no");
//                }
//                
//                if(lookup_piani12.getSelectedValue(newTic.getMotivazione_piano_campione()).contains("57 B")){
//                        //mitili ovvero molluschi
//                        context.getRequest().setAttribute("link_mod_b","si");
//                }
//                else {
//                        context.getRequest().setAttribute("link_mod_b","no");
//                }
//                
//                /*
//                 * con i nuovi piani c1 e c2, d1 e d2..avremo
//                 *
//                 * */
//                if(piani_sin.equals("NEW")){
//                
//                        if (lookup_piani12.getSelectedValue(newTic.getMotivazione_piano_campione()).contains("57 C1")){
//                                //Pescato
//                                context.getRequest().setAttribute("link_mod_c1","si");
//                        }else {
//                                context.getRequest().setAttribute("link_mod_c1","no");
//                        }
//                            
//                        if (lookup_piani12.getSelectedValue(newTic.getMotivazione_piano_campione()).contains("57 C2")){
//                                
//                                context.getRequest().setAttribute("link_mod_c2","si");
//                                
//                        }else {
//                                context.getRequest().setAttribute("link_mod_c2","no");
//                        }
//                        
//                        if (lookup_piani12.getSelectedValue(newTic.getMotivazione_piano_campione()).contains("57 D")){
//                                
//                                context.getRequest().setAttribute("link_mod_d","si");
//                        }else {
//                                context.getRequest().setAttribute("link_mod_d","no");
//                        }
//                      
//                        
//                } else {
//                        
//                        if (lookup_piani12.getSelectedValue(newTic.getMotivazione_piano_campione()).contains("57 C")){
//                                //Pescato
//                                context.getRequest().setAttribute("link_mod_d","si");
//                        }else {
//                                context.getRequest().setAttribute("link_mod_d","no");
//                        }
//                
//                        if (lookup_piani12.getSelectedValue(newTic.getMotivazione_piano_campione()).contains("57 D")){
//                                context.getRequest().setAttribute("link_mod_c","si");
//                        }else {
//                                context.getRequest().setAttribute("link_mod_c","no");
//                        }
//                        
//                }
//                        
//                        
//                        //check permission to record
//
//
//                        if(context.getRequest().getParameter("TipoSpecie_latte")!=null){
//                                newTic.setTipSpecie_latte(Integer.parseInt(context.getRequest().getParameter("TipoSpecie_latte")));
//
//                        }
//                        if(context.getRequest().getParameter("TipoSpecie_uova")!=null){
//                                newTic.setTipSpecie_uova(Integer.parseInt(context.getRequest().getParameter("TipoSpecie_uova")));
//
//                        }

                        // Get the previousTicket, update the ticket, then send both to a
                        // hook
                        Ticket previousTicket = new Ticket(db, Integer.parseInt(context
                                        .getParameter("id")));
                        newTic.setModifiedBy(getUserId(context));
                        newTic.setSiteId(Integer.parseInt(context.getRequest().getParameter("siteId")));
        
                        isValid = this.validateObject(context, db, newTic) && isValid;





                        if (isValid) {
                                newTic.setTestoAppoggio("Modifica"); //aggiunto da d.dauria                                
                                resultCount = newTic.update(db);

                        }
                        if (resultCount == 1) {
                                newTic.queryRecord(db, newTic.getId());
                                processUpdateHook(context, previousTicket, newTic);
                                
                        }


                
                } catch (Exception e) {
                        context.getRequest().setAttribute("Error", e);
                        e.printStackTrace();

                }
                finally
                {
                        this.freeConnection(context, db);
                }

                context.getRequest().setAttribute("resultCount", resultCount);
                context.getRequest().setAttribute("isValid", isValid);


                return "-none-" ;
        }
        
        
        
        
public String executeCommandUpdateTicketEsito(ActionContext context) {

                
                int resultCount = 0;
                Connection db = null ;
                int catCount = 0;
                TicketCategory thisCat = null;
                boolean catInserted = false;
                boolean isValid = true;

                Ticket newTic = new Ticket();
                newTic.setId(Integer.parseInt(context.getRequest().getParameter("id")));
                
                //setto la data_accettazione
                String codiceAccettazione = context.getRequest().getParameter("cause");
                newTic.setCause(codiceAccettazione);
                String dataAccettazione = context.getRequest().getParameter("dataAccettazione");
                newTic.setDataAccettazione(dataAccettazione);
                
                int idAnalita = Integer.parseInt(context.getParameter("id_analita"));


                try {
                        
                        db = this.getConnection(context);
                                        
                        SystemStatus systemStatus = this.getSystemStatus(context);
                        
                        Analita thisAnalita = new Analita();
                        
                        boolean esito_allarme_rapido =DatabaseUtils.parseBoolean(context.getParameter("esito_allarme_rapido"));
                        thisAnalita.setEsito_allarme_rapido(esito_allarme_rapido);
                        
                        Timestamp esito_data = DatabaseUtils.parseTimestamp2(context.getParameter("esito_data"));
                        thisAnalita.setEsito_data(esito_data);
                        
                        int esito_id = Integer.parseInt(context.getParameter("esito_id"));
                        thisAnalita.setEsito_id(esito_id);
                        
                        String esito_motivazione_respingimento = context.getParameter("esito_motivazione_respingimento");
                        thisAnalita.setEsito_motivazione_respingimento(esito_motivazione_respingimento);
                        
                        String esito_note_esame =context.getParameter("esito_note_esame");
                        thisAnalita.setEsito_note_esame(esito_note_esame);
                        
                        int esito_punteggio  =Integer.parseInt(context.getParameter("esito_punteggio"));;
                        thisAnalita.setEsito_punteggio(esito_punteggio);
                        
                        int esito_responsabilita_positivita =Integer.parseInt(context.getParameter("esito_responsabilita_positivita"));
                        thisAnalita.setEsito_responsabilita_positivita(esito_responsabilita_positivita);
                        
                        boolean esito_segnalazione_informazioni = DatabaseUtils.parseBoolean(context.getParameter("esito_segnalazione_informazioni"));
                        thisAnalita.setEsito_segnalazione_informazioni(esito_segnalazione_informazioni);

                        if (isValid) {
                                newTic.setTestoAppoggio("Modifica"); //aggiunto da d.dauria                                
                                thisAnalita.updateEsito(db, idAnalita, newTic.getId());
                                newTic.aggiornaPunteggio(db, newTic.getId());
                                newTic.updateDataAccettazione(db, codiceAccettazione, newTic.getDataAccettazione(), newTic.getId());

                        }
                        context.getRequest().setAttribute("IdAnalita", context.getRequest().getParameter("id_analita"));

                        context.getRequest().setAttribute("EsitoAggiornato", "OK");
                
                } catch (Exception e) {
                        context.getRequest().setAttribute("Error", e);
                        e.printStackTrace();

                }
                finally
                {
                        this.freeConnection(context, db);
                }

                context.getRequest().setAttribute("resultCount", resultCount);
                context.getRequest().setAttribute("isValid", isValid);


                return "AddEsitoOK" ;
        }


public static String executeCommandUpdateTicketEsiti(ActionContext context, Connection db) {
        
            int resultCount = 0;
           // Connection db = null ;
            int catCount = 0;
            TicketCategory thisCat = null;
            boolean catInserted = false;
            boolean isValid = true;
   
            Ticket newTic = new Ticket();
            newTic.setId(Integer.parseInt(context.getRequest().getParameter("id")));
            
            //setto la data_accettazione
            String codiceAccettazione = context.getRequest().getParameter("cause");
            newTic.setCause(codiceAccettazione);
            String dataAccettazione = context.getRequest().getParameter("dataAccettazione");
            newTic.setDataAccettazione(dataAccettazione);
            String esitoNoteEsame = context.getRequest().getParameter("esito_note_esame");
            newTic.setEsitoNoteEsame(esitoNoteEsame);
            String dataRisultato = context.getRequest().getParameter("dataRisultato");
            newTic.setDataRisultato(dataRisultato);
            
            String analiti = context.getRequest().getParameter("numAnaliti");
            int numAnaliti = 0;
            
            if (analiti!=null && !analiti.equals("null") && !analiti.equals(""))
            	numAnaliti = Integer.parseInt(analiti);
         
            try {
                    
               //     db = this.getConnection(context);
               //     SystemStatus systemStatus = this.getSystemStatus(context);
               
                    Ticket oldTic = new Ticket(db, newTic.getId());
                    newTic.setTipologia_operatore(oldTic.getTipologia_operatore());
                    
                    
                    for (int i = 1; i<=numAnaliti; i++){
                    
                    		Analita thisAnalita = new Analita();
                    		int idAnalita = Integer.parseInt(context.getParameter("idAnalita_"+i));
                    		
                    		String esito_allarme_rapido =context.getParameter("esito_allarme_rapido_"+i);
                    		thisAnalita.setEsito_allarme_rapido(esito_allarme_rapido);
                    		
                    		String esito_data = context.getParameter("esito_data_"+i);
                    		thisAnalita.setEsito_data(esito_data);
                    		
                    		String esito_id = context.getParameter("esito_id_"+i);
                    		thisAnalita.setEsito_id(esito_id);
                    		
                    		String esito_motivazione_respingimento = context.getParameter("esito_motivazione_respingimento_"+i);
                    		thisAnalita.setEsito_motivazione_respingimento(esito_motivazione_respingimento);
                    		
                    		String[] esito_nonconforme_nonc_list = context.getRequest().getParameterValues("esito_nonconforme_nonc_"+i);
                    		String esito_nonconforme_nonc = null;
                    		if (esito_nonconforme_nonc_list!=null && esito_nonconforme_nonc_list.length>0){
                    			esito_nonconforme_nonc = Arrays.toString(esito_nonconforme_nonc_list);
                    			esito_nonconforme_nonc = esito_nonconforme_nonc.substring(1, esito_nonconforme_nonc.length()-1);
                    		}
                    		thisAnalita.setEsito_nonconforme_nonc(esito_nonconforme_nonc);  
                    		
                    	//	String esito_note_esame =context.getParameter("esito_note_esame_"+i);
                    	//	thisAnalita.setEsito_note_esame(esito_note_esame);
                    		
                    		String esito_punteggio  =context.getParameter("esito_punteggio_"+i);
                    		thisAnalita.setEsito_punteggio(esito_punteggio);
                    		
                            String esito_responsabilita_positivita =context.getParameter("esito_responsabilita_positivita_"+i);
                            thisAnalita.setEsito_responsabilita_positivita(esito_responsabilita_positivita);
                            
                            String esito_segnalazione_informazioni = context.getParameter("esito_segnalazione_informazioni_"+i);
                            thisAnalita.setEsito_segnalazione_informazioni(esito_segnalazione_informazioni);
                            
                            if (isValid) {
                            newTic.setTestoAppoggio("Modifica"); //aggiunto da d.dauria                                
                            thisAnalita.updateEsito(db, idAnalita, newTic.getId());
                     } 
                            }
                    
                    if (isValid) {
                    newTic.aggiornaPunteggio(db, newTic.getId());
                    newTic.updateInfoEsito(db, codiceAccettazione, newTic.getDataAccettazione(), esitoNoteEsame, newTic.getDataRisultato(), newTic.getId());
                    }

                    context.getRequest().setAttribute("EsitoAggiornato", "OK");
                    context.getRequest().setAttribute("resultCount", resultCount);
                    context.getRequest().setAttribute("isValid", isValid);
                    context.getRequest().setAttribute("TicketDetails", newTic);
                    
//                  String ret = newTic.getURlDettaglio()+"Campioni.do?command=TicketDetails&idC="+newTic.getIdControlloUfficiale()+"&id="+newTic.getId()+"&orgId="+newTic.getOrgId();
//                  Action ac =  (Action) ControllerServlet.actions.get(newTic.getURlDettaglio()+"Campioni");
//                  Class classRef = Class.forName(ac.getActionClassName());
//                  Method method = classRef.getMethod(
//                           "executeCommand" + "TicketDetails", new Class[]{context.getClass()});
//                  String result = (String) method.invoke(classRef, new Object[]{context});
//                  return result+newTic.getURlDettaglio();
//                   // return (executeCommandTicketDetails(context,db));
            
            } catch (Exception e) {
                    context.getRequest().setAttribute("Error", e);
                    e.printStackTrace();

            }
            finally
            {
       //             this.freeConnection(context, db);
            }
            return "";
            
    }

public static String executeCommandRiapriTicketEsiti(ActionContext context, Connection db) throws SQLException {
    
    Ticket newTic = new Ticket();
    newTic.setId(Integer.parseInt(context.getRequest().getParameter("id")));
    newTic.setInformazioniLaboratorioChiuso(false);
    newTic.updateInfoEsitoCampioneChiuso(db, newTic.getId(), newTic.isInformazioniLaboratorioChiuso());
    return "";
    
}

        // aggiunto da d.dauria
        public String executeCommandAdd(ActionContext context,Connection db) {

                
                Ticket newTic = null;
                try {
                        
                        SystemStatus systemStatus = this.getSystemStatus(context);
                        String idC = context.getRequest().getParameter("idC");
                        
                        org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket();
                        cu.queryRecord(db, Integer.parseInt(idC));
                        //da decommentare in sviluppo per testare la preaccettazione e il recupero della linea.
                        cu.queryRecordIdLineaAttivitaNew(db, Integer.parseInt(idC));
                        context.getRequest().setAttribute("CU", cu);

                        LookupList lookup_tipo_ispezione = new LookupList();
                        lookup_tipo_ispezione.setTable("lookup_tipo_ispezione");
                        lookup_tipo_ispezione.buildListWithEnabled(db);                        
                        
                        HashMap<Integer, String> tipo_ispezone_valori = cu.getTipoIspezione();


                        int size = lookup_tipo_ispezione.size();
                        int ind = 0 ;
                        for ( ind = 0 ; ind<size;ind++)
                        {
                                LookupElement el = (LookupElement)lookup_tipo_ispezione.get(ind) ;
                                
                               
                                		 if ((tipo_ispezone_valori.containsKey(el.getCode())==false) &&  
                                         		!el.getCodiceInterno().equalsIgnoreCase("22a") &&
                                         		!el.getCodiceInterno().equalsIgnoreCase("24a")&& 
                                         		!el.getCodiceInterno().equalsIgnoreCase("41a") &&
                                         		!el.getCodiceInterno().equalsIgnoreCase("42a") && 
                                         		!el.getCodiceInterno().equalsIgnoreCase("43a"))
                                		
                                {
                                        lookup_tipo_ispezione.remove(ind);
                                        size-=1 ;
                                        ind =-1 ;
                                }else
                                {
                                	if (el.getCodiceInterno().equalsIgnoreCase("24a")&& context.getServletContext().getAttribute("SUFFISSO_TAB_ACCESSI")!= null && "_ext".equalsIgnoreCase((String)context.getServletContext().getAttribute("SUFFISSO_TAB_ACCESSI")))
                                	{
                                		lookup_tipo_ispezione.remove(ind);
                                        size-=1 ;
                                        ind =-1 ;
                                	}
                                }
                                

                        }
                        
                        for ( ind = 0 ; ind<size;ind++)
                        {
                                LookupElement el = (LookupElement)lookup_tipo_ispezione.get(ind) ;
                                if( (el.getCode()== 22  || el.getCode()==24 || el.getCode()==41 || el.getCode()==42 || el.getCode()==43) && el.isEnabled()==false)
                                {
                                        lookup_tipo_ispezione.remove(ind);
                                        el.setEnabled(true);
                                        lookup_tipo_ispezione.add(ind, el);
                                }
                        }
                        
                       
                        
                        lookup_tipo_ispezione.addItem(-1,
                                        "-- SELEZIONA VOCE --");
                        context.getRequest().setAttribute("Motivazione", lookup_tipo_ispezione);

                        
                        
                        String sqlPiani ="(select" +
                         " lp.* " +
                        "from tipocontrolloufficialeimprese t " +
                        "join lookup_piano_monitoraggio lp on lp.code = t.pianomonitoraggio  and t.enabled = true " +
                        "where idcontrollo = "+cu.getId()+")  "  ;
                        LookupList lookup_piani = new LookupList(db,sqlPiani,true);
                        ArrayList<Piano> piani_selezionati = cu.getPianoMonitoraggio();

                        
                        size = lookup_piani.size();
                        int i = 0 ;
                        boolean trovato = false ;
                        for ( i = 0 ; i<size;i++)
                        {
                                LookupElement el = (LookupElement)lookup_piani.get(i) ;
                                trovato = false ;
                                for (Piano p : piani_selezionati)
                                {
                                        if (p.getId()==el.getCode())
                                        {
                                                trovato = true ;
                                                break ;
                                        }
                                        
                                        


                                }
                                if (trovato == false)
                                {
                                        lookup_piani.remove(i);
                                        size-=1 ;
                                        i=-1;

                                }

                        }


                        lookup_piani.addItem(-1,
                                        "-- SELEZIONA VOCE --");
                        context.getRequest().setAttribute("PianiMonitoraggio", lookup_piani);

                        LookupList DestinatarioCampione = new LookupList(db,
                        "lookup_destinazione_campione");
                        DestinatarioCampione.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("DestinatarioCampione",
                                        DestinatarioCampione);

                        LookupList EsitoCampione = new LookupList(db,
                        "lookup_esito_campione");
                        EsitoCampione.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("EsitoCampione", EsitoCampione);

                        
                        
                        LookupList listaSpecie = new LookupList(db,
                        "lookup_specie_pnaa");
                        listaSpecie.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        listaSpecie.setMultiple(true);
                        listaSpecie.setSelectSize(7);
                        context.getRequest().setAttribute("ListaSpecie", listaSpecie);
                        
                        LookupList listaProdotti = new LookupList(db,
                                        "lookup_prodotti_pnaa");
                        listaProdotti.addItem(-1, systemStatus
                                                        .getLabel("calendar.none.4dashes"));
                        listaProdotti.setMultiple(true);
                        listaProdotti.setSelectSize(7);
                        context.getRequest().setAttribute("ListaProdotti", listaProdotti);
                        
                        LookupList categorieBovine = new LookupList(db,
                        "lookup_categorie_specie_animali");
                        categorieBovine.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        categorieBovine.removeElementByLevel(129);
                        context.getRequest().setAttribute("CategorieBovine", categorieBovine);
                        
                        LookupList categorieBufaline = new LookupList(db,
                        "lookup_categorie_specie_animali");
                        categorieBufaline.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        categorieBufaline.removeElementByLevel(121);
                        context.getRequest().setAttribute("CategorieBufaline", categorieBufaline);
                        
                        
                        
                        
                        // aggiunto da d.dauria
                        LookupList conseguenzepositivita = new LookupList(db,
                        "lookup_conseguenze_positivita");
                        conseguenzepositivita.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("ConseguenzePositivita",
                                        conseguenzepositivita);


                        // aggiunto da d.dauria
                        LookupList responsabilitapositivita = new LookupList(db,
                        "lookup_responsabilita_positivita");
                        responsabilitapositivita.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("ResponsabilitaPositivita",
                                        responsabilitapositivita);

                        String idControlloUfficiale = context.getRequest().getParameter("idControllo");
                        context.getRequest().setAttribute("idControllo",
                                        idControlloUfficiale);
                        context.getRequest().setAttribute("idC",
                                        idC);



                        org.aspcfs.modules.vigilanza.base.Ticket tic=new org.aspcfs.modules.vigilanza.base.Ticket(db,Integer.parseInt(idC));
                        ArrayList<String> pianoScelto=new ArrayList<String>();
                        if(tic.getTipoCampione()==4){

                        		LookupList tipoispezionelookup = new LookupList(db,"lookup_tipo_ispezione");

                                Iterator<Integer> itTipoIspezione = tic.getTipoIspezione().keySet().iterator();
                                while (itTipoIspezione.hasNext())
                                {
                                        int idTipo = itTipoIspezione.next() ;
                                        String codInterno = tipoispezionelookup.getElementfromValue(idTipo).getCodiceInterno();
                                        if(codInterno.equalsIgnoreCase("2a"))
                                        {
                                                Iterator<Integer> kiave=tic.getLisaElementipianoMonitoraggio_ispezioni().keySet().iterator();

                                                while(kiave.hasNext()){

                                                        pianoScelto.add("Piano Monitoraggio : "+tic.getLisaElementipianoMonitoraggio_ispezioni().get(kiave.next()));
                                                }

                                        }
                                        else
                                        {
                                                String TipoIspezione = tic.getTipoIspezione().get(idTipo);
                                                pianoScelto.add(TipoIspezione);
                                        }
                                }



                        }

                        if(!pianoScelto.equals(""))
                                context.getRequest().setAttribute("Piano", pianoScelto);



                        context.getRequest().setAttribute("dataC", context.getRequest().getParameter("dataC"));

                        LookupList SiteIdList = new LookupList(db, "lookup_site_id");
                        SiteIdList.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        SiteIdList.addItem(-2, "-- TUTTI --"
                        );
                        context.getRequest().setAttribute("SiteIdList", SiteIdList);



                                newTic = new Ticket();

                        // aggiunte queste istruzioni

                        // istruzioni aggiunte

                        // getting current date in mm/dd/yyyy format
                        String currentDate = getCurrentDateAsString(context);
                        context.getRequest().setAttribute("currentDate", currentDate);
                        context.getRequest().setAttribute("systemStatus",
                                        this.getSystemStatus(context));
                } catch (Exception e) {
                        context.getRequest().setAttribute("Error", e);
                        

                } finally {
                        this.freeConnection(context, db);
                }
                addModuleBean(context, "AddTicket", "Ticket Add");
                if (context.getRequest().getParameter("actionSource") != null) {
                        context.getRequest().setAttribute("actionSource",
                                        context.getRequest().getParameter("actionSource"));
                        return "AddTicketOK";
                }
                context.getRequest().setAttribute("systemStatus",
                                this.getSystemStatus(context));
                return "" ;
        }

        public String executeCommandInsert(ActionContext context,Connection db)
        throws SQLException {

                String barcode="";
                String barcode_new = "";
                ArrayList<String> arr = new ArrayList<String>();
                boolean recordInserted = false;
                boolean genera = false;

                boolean isValid = true;
                SystemStatus systemStatus = this.getSystemStatus(context);

                Ticket newTicket = null;

                String idCU = context.getRequest().getParameter("idC");
                context.getRequest().setAttribute("idC",idCU);
                String motivazione_campione = null;
                String motivazione_piano_campione = null;
                String nomeTabella = null; //lookup_piano_monitoraggio || lookup_tipo_ispezione

                Ticket newTic = null;

                if (idCU.equals("null")) {
                        idCU = "-1";
                        //motivazione_campione = "-1";
                        //motivazione_piano_campione = "-1";
                        
                        motivazione_campione = context.getRequest().getParameter("quesiti_diagnostici_sigla");
                        motivazione_piano_campione = context.getRequest().getParameter("quesiti_diagnostici_sigla");
                        

                        if (!motivazione_campione.equals("-1")){
                                PreparedStatement pst = db.prepareStatement("select short_description from quesiti_diagnostici_sigla where code="+motivazione_campione);
                                ResultSet rs = pst.executeQuery();
                                while(rs.next()){
                                        nomeTabella = rs.getString("short_description");
                                }
                                if(nomeTabella.equals("lookup_piano_monitoraggio") && nomeTabella!=null){
                                        motivazione_campione="2";
                                } else if(nomeTabella.equals("lookup_tipo_ispezione") && nomeTabella!=null){
                                        motivazione_piano_campione="";
                                }
                                rs.close();
                                pst.close();

                        }
                
                        context.getRequest().setAttribute("motivazione_campione", motivazione_campione);
                        context.getRequest().setAttribute("motivazione_piano_campione", motivazione_piano_campione);
                        
                        
                        newTic =  new Ticket();        
                        newTic.setOrgId(context.getRequest().getParameter("orgId"));
//                        newTic.setLocation(context.getRequest().getParameter("location"));
                        newTic.setAssignedDate(context.getRequest().getParameter("assignedDate"));
                        newTic.setCause(context.getRequest().getParameter("cause"));
                        newTic.setProblem(context.getRequest().getParameter("problem"));
                        newTic.setDataAccettazione(context.getRequest().getParameter("dataAccettazione"));
                        newTic.setNoteAnalisi(context.getRequest().getParameter("noteAnalisi"));
                        newTic.setNoteAlimenti(context.getRequest().getParameter("noteMatrici"));
                        
                    
                        
                        //GENERA BARCODE PER IL CAMPIONE
                        String tipo = context.getRequest().getParameter("tipo");
                        String orgId = context.getRequest().getParameter("orgId");
//                        ModCampioni campione = new ModCampioni();
//                        barcode=campione.generaCodice(db, Integer.parseInt(tipo), Integer.parseInt(orgId),0);
//                        newTic.setLocation(barcode);
                        genera=true;
                }
                else {
                        newTic = (Ticket) context.getFormBean();
                        if (newTic.getLocation().equals("AUTOMATICO") || newTic.getLocation()==null) {genera=true;}
                        motivazione_campione = context.getRequest().getParameter("motivazione_campione");
                        motivazione_piano_campione = context.getRequest().getParameter("motivazione_piano_campione");
                        newTic.setNoteAlimenti(context.getRequest().getParameter("noteMatrici"));
                }

                
                
                for (int p = 0; p<10; p++) {
	                if(context.getParameter("campionePuntoPrelievo_"+p)!=null){
	                	int campionePuntoPrelievo = Integer.parseInt(context.getParameter("campionePuntoPrelievo_"+p));
	                    CoordinateMolluschiBivalvi c = new CoordinateMolluschiBivalvi(db, campionePuntoPrelievo);
	                    newTic.getListaCoordinateCampione().add(c);
	                }
                }
                        
              
                /*gestione specie campioni per piano pnaa con matrice mangimi*/
                
                ArrayList<SpecieAnimali> listaspecie = new ArrayList<SpecieAnimali>();
                String[] specieArrayRequest = context.getRequest().getParameterValues("specie_animali");
                if (specieArrayRequest!=null)
                {
                        for (int i = 0 ; i<specieArrayRequest.length;i++)
                        {
                                if (Integer.parseInt(specieArrayRequest[i])>0)
                                {
                                        SpecieAnimali specie = new SpecieAnimali();
                                        specie.setIdSpecie(Integer.parseInt(specieArrayRequest[i]));
                                        
                                        //Modifica: La categoria non e' piu gestita come una lookup_separata ma e' una informazione
                                        //impilicita della specie
                                        /*if (context.getParameter("specie_categoria_"+specieArrayRequest[i])!=null && Integer.parseInt(context.getParameter("specie_categoria_"+specieArrayRequest[i]))>0)
                                        {
                                                specie.setIdCategoria(Integer.parseInt(context.getParameter("specie_categoria_"+specieArrayRequest[i])));
                                        }*/
                                        
                                        listaspecie.add(specie);
                                }
                        }
                        newTic.setListaSpecieAnimali(listaspecie);
                }
                
                
                /*fine gestione specie campioni per piano pnaa con matrice mangimi*/
                
      /*gestione prodotti campioni per piano pnaa */        
                ArrayList<String> listaprodotti = new ArrayList<String>();
                String[] prodottiArrayRequest = context.getRequest().getParameterValues("lista_prodotti");
                if (prodottiArrayRequest!=null)
                {
                        for (int i = 0 ; i<prodottiArrayRequest.length;i++)
                        {
                                if (Integer.parseInt(prodottiArrayRequest[i])>0)
                                {
                                        String prod = prodottiArrayRequest[i];
                                        listaprodotti.add(prod);
                                }
                        }
                        newTic.setListaProdottiPnaa(listaprodotti);
                }
                
                
                /*fine gestione prodotti campioni per piano pnaa */
                
                newTic.setMotivazione_campione(Integer.parseInt(motivazione_campione));
                RispostaDwrCodicePiano risposta= ControlliUfficialiUtil.getCodiceInternoPianoMonitoraggio(db, "lookup_tipo_ispezione", Integer.parseInt(motivazione_campione));
                if (risposta.getCodiceInterno()!=null && risposta.getCodiceInterno().equals("2a"))
                        newTic.setMotivazione_piano_campione(Integer.parseInt(motivazione_piano_campione));
                newTic.setNoteAlimenti(context.getRequest().getParameter("noteMatrici"));
                UserBean user = (UserBean) context.getSession().getAttribute("User");
                String ip = user.getUserRecord().getIp();
                newTic.setIpEntered(ip);
                newTic.setIpModified(ip);
                //newTic.setMotivazione(context.getRequest().getParameter("motivazione"));
                //newTic.setNoteMotivazione(context.getRequest().getParameter("noteMotivazione"));
                String site =  context.getRequest().getParameter("siteId");
                if(site.equals("201")){
                        site = "AV";        
                }else if(site.equals("202")){
                        site = "BN";
                }else if(site.equals("203")){
                        site = "CE";
                }else if(site.equals("204")){
                        site = "NA1";
                }else if(site.equals("205")){
                        site = "NA2N";
                }else if(site.equals("206")){
                        site = "NA3S";
                }else if(site.equals("207")){
                        site = "SA";
                }else{
                        if(site.equals("16")){
                                site = "FuoriRegione";
                        }
                }
                if(context.getRequest().getParameter("EsitoTampone")!=null && ! context.getRequest().getParameter("EsitoTampone").equals(""))
                        newTic.setEsitoCampione(Integer.parseInt(context.getRequest().getParameter("EsitoTampone")));
                String noteAlimenti="";

                /**
                 *        SETTA I CAMPI PER IL TIPO DI ALIMENTO DEL CAMPIONE SELEZIONATO DALL'UTENTE
                 */
                //GenericControlliUfficiali.setParameterTipiAlimenti(context, null, newTic, "campioni");

                if(context.getRequest().getParameter("microchipMatriciCanili")!=null && !context.getRequest().getParameter("microchipMatriciCanili").equals("")){

                        newTic.setMicrochip( context.getRequest().getParameter("microchipMatriciCanili") );
                }

                if(context.getRequest().getParameter("noteMatriciCanili")!=null && !context.getRequest().getParameter("noteMatriciCanili").equals("")){

                        noteAlimenti=context.getRequest().getParameter("noteMatriciCanili");
                }

                if(context.getRequest().getParameter("segnalazione")!=null)
                        newTic.setSegnalazione(true);
                String idControllo = context.getRequest().getParameter("idControlloUfficiale");
                String idC = context.getRequest().getParameter("idC");

                if(idControllo.equals("N.D")){
                        idControllo = "-1";
                        newTic.setDestinatarioCampione("IZSM");

                }

                newTic.setIdControlloUfficiale(idControllo);

                if((context.getRequest().getParameter("punteggio") !=null  && !context.getRequest().getParameter("punteggio").equals("")))
                        newTic.setPunteggio(context.getRequest().getParameter("punteggio"));


                newTic.setTipo_richiesta(context.getRequest().getParameter(
                "tipo_richiesta"));
                newTic.setEnteredBy(getUserId(context));
                newTic.setModifiedBy(getUserId(context));
                if(newTic.getIdStabilimento() > 0 && newTic.getIdApiario() > 0)
                	newTic.setIdStabilimento(0);
                
                try {
                

                        LookupList SiteIdList = new LookupList(db, "lookup_site_id");
                        SiteIdList.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("SiteIdList", SiteIdList);

                        LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
                        TipoCampione.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("TipoCampione", TipoCampione);

                        LookupList EsitoCampione = new LookupList(db,
                        "lookup_esito_campione");
                        EsitoCampione.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("EsitoCampione", EsitoCampione);


                        LookupList lookup_molluschi = new LookupList(db,
                        "lookup_molluschi");
                        lookup_molluschi.addItem(-1,
                        "-- SELEZIONA VOCE --");
                        context.getRequest().setAttribute("lookupmolluschi",
                                        lookup_molluschi);
                        // aggiunto da d.dauria


                        // aggiunto da d.dauria
                        LookupList conseguenzepositivita = new LookupList(db,
                        "lookup_conseguenze_positivita");
                        conseguenzepositivita.addItem(-1,systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("ConseguenzePositivita",
                                        conseguenzepositivita);


                        // aggiunto da d.dauria
                        LookupList responsabilitapositivita = new LookupList(db,
                        "lookup_responsabilita_positivita");
                        responsabilitapositivita.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("ResponsabilitaPositivita",
                                        responsabilitapositivita);

                        LookupList DestinatarioCampione = new LookupList(db,
                        "lookup_destinazione_campione");
                        DestinatarioCampione.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("DestinatarioCampione",
                                        DestinatarioCampione);

                

                        if (context.getParameter("check_specie_mangimi")!=null)
                        {
                                newTic.setCheck_specie_mangimi(true);
                        }
                        if (context.getParameter("check_circuito_ogm")!=null)
                        {
                                newTic.setCheck_circuito_ogm(context.getParameter("check_circuito_ogm"));
                        }
                        
                        if (newTic.getOrgId() > 0) {
                                newTic.setSiteId( context.getRequest().getParameter("siteId"));
                        }
                        
                        if (newTic.getLocation()!=null && !newTic.getLocation().equals("") && !newTic.getLocation().equalsIgnoreCase("automatico"))
                        	newTic.setLocation_new(ModCampioni.nuovoCodice(newTic.getLocation(), newTic.getSiteId()));
                        
                        isValid = this.validateObject(context, db, newTic) && isValid;
                        if (isValid) {




                                recordInserted = newTic.insert(db,context);
                                
                                /*INSERT TIPO CAMPIONE*/
                                if(context.getRequest().getParameter("size")!=null)
                                {
                                        int size = Integer.parseInt(context.getRequest().getParameter("size"));
                                        for (int i = 1 ; i<=size; i++)
                                        {
                                                if(context.getRequest().getParameter("analitiId_"+i)!= null && !context.getRequest().getParameter("analitiId_"+i).equals(""))
                                                {
                                                        int idFoglia = Integer.parseInt(context.getRequest().getParameter("analitiId_"+i));
                                                        String path = context.getRequest().getParameter("pathAnaliti_"+i);
                                                        newTic.insertAnaliti(db, idFoglia, path);
                                                        arr.add(path);
                                                }
                                        }
                                }


                                if(context.getRequest().getParameter("size1")!=null)
                                {
                                        int size = Integer.parseInt(context.getRequest().getParameter("size1"));
                                        for (int i = 1 ; i<=size; i++)
                                        {
                                                if(context.getRequest().getParameter("idMatrice_"+i)!= null && !context.getRequest().getParameter("idMatrice_"+i).equals(""))
                                                {
                                                        int idFoglia = Integer.parseInt(context.getRequest().getParameter("idMatrice_"+i));
                                                        String path = context.getRequest().getParameter("path_"+i);
                                                        newTic.insertMatrici(db, idFoglia, path);
                                                }

                                        }
                                }




                        }


                        if (recordInserted) {

                                if(idC != null && !idC.equals("null")){
                                        idC = idC;
                                }
                                else {
                                        idC = "-1";
                                }

                                org.aspcfs.modules.vigilanza.base.Ticket controlloUff = new org.aspcfs.modules.vigilanza.base.Ticket(db, Integer.parseInt(idC));
                                int puntiV = controlloUff.getPunteggio();
                                int puntiN = puntiV + newTic.getPunteggio();

                                controlloUff.setPunteggio(puntiN);
                                controlloUff.update(db);
                                // Prepare the ticket for the response
                                newTicket = new Ticket(db, newTic.getId());
//                                context.getRequest().setAttribute("TicketDetails", newTicket);



                        } else {
                                if (newTic.getOrgId() != -1) {
                                        Organization thisOrg = new Organization(db, newTic
                                                        .getOrgId());
                                        newTic.setCompanyName(thisOrg.getName());
                                }
                        }

                        LookupList quesiti_diagn = new LookupList(db, "quesiti_diagnostici_sigla");
                quesiti_diagn.addItem(-1, "-- SELEZIONA VOCE --");
                context.getRequest().setAttribute("QuesitiList", quesiti_diagn);

                        String ragione_sociale = (String)context.getRequest().getAttribute("ragioneSociale");

        
                        context.getRequest().setAttribute("recordInserted", recordInserted);
                        context.getRequest().setAttribute("isValid", isValid);
                
                        if (idCU.equals("-1")) {
                                //CONTESTO SIGLA (GENERAZIONE BARCODE AUTOMATICA)
                                int suffix =0;
                                for (int i=0;i<arr.size();i++){
                                        if(arr.get(i).contains("BATTERIOLOGICO")){
                                                suffix=2;
                                                break;
                                        }
                                        if(arr.get(i).contains("CHIMICO")){
                                                suffix=3;
                                                break;
                                        }
                                }
                                if (suffix == 0) { //se non sono selezionati analiti prendi il tipo di verbale dalla jsp
                                        suffix = Integer.parseInt(context.getRequest().getParameter("tipo"));
                                }
                                String id_campione = String.valueOf(newTic.getId());
                              
                    			
                                ModCampioni campione = new ModCampioni();
                                String orgId = context.getRequest().getParameter("orgId");
                                barcode=campione.generaCodice(db, suffix, Integer.parseInt(orgId),Integer.parseInt(id_campione));
                                barcode_new = campione.getBarcode_new();
                                String sql = "update ticket set location='"+barcode+"', location_new = '"+barcode_new+"' where ticketid="+id_campione;
                                PreparedStatement pst = db.prepareStatement(sql);
                                pst.executeUpdate();
                                pst.close();
                                newTicket.setLocation(barcode);
                        }
                        else if (genera==true){ //CONTESTO CLASSICO (GENERAZIONE BARCODE AUTOMATICA)
                                int suffix =0;
                                for (int i=0;i<arr.size();i++){
                                        if(arr.get(i).contains("BATTERIOLOGICO")){
                                                suffix=2;
                                                break;
                                        }
                                        if(arr.get(i).contains("CHIMICO")){
                                                suffix=3;
                                                break;
                                        }
                                        
                                        if(newTicket.getTipologia_operatore() == 211 || newTicket.getTipologia_operatore() == 201 ){
                                                suffix=1;
                                                break;
                                        }
                                }
                                
                                String id_campione = String.valueOf(newTic.getId());
                                String orgId = context.getRequest().getParameter("orgId");
                                //if (suffix>0){
                                ModCampioni campione = new ModCampioni();
                                //barcode=campione.generaCodice(db);
                                if(orgId != null){
                                	barcode=campione.generaCodice(db, suffix, Integer.parseInt(orgId),Integer.parseInt(id_campione));
                                }else {
                                	String stabId = (String)context.getRequest().getAttribute("stabId");
                                	if(stabId !=null){
                                		barcode=campione.generaCodice(db, suffix, Integer.parseInt(stabId),Integer.parseInt(id_campione));
                                	} else {
                                		stabId = Integer.toString(newTicket.getIdApiario());
                                		barcode=campione.generaCodice(db, suffix, Integer.parseInt(stabId),Integer.parseInt(id_campione));
                                	}
                                }
                                barcode_new = campione.getBarcode_new();
                                String sql = "update ticket set location='"+barcode+"', location_new = '"+barcode_new+"' where ticketid="+id_campione;
                                PreparedStatement pst = db.prepareStatement(sql);
                                pst.executeUpdate();
                                pst.close();                                        
                                newTicket.setLocation(barcode);
                                //}
                        }
                        else     
                        		if(isValid && context.getRequest().getParameter("location") != null)
                        			newTicket.setLocation(context.getRequest().getParameter("location"));
                        
                        if(isValid)
                        	CampioniUtil.insertSchedaPNA(db, newTicket, context);
                      
                      //parte che riguarda l'associazione tra codice ci preaccettazione e campione.
                        if(context.getRequest().getParameter("codicePreaccettazione") != null){
                        	
                            String codice_preaccettazione = context.getRequest().getParameter("codicePreaccettazione").toString();
                            String id_campione_preaccettazione = String.valueOf(newTicket.getId());
                            
            				DwrPreaccettazione dwrAssociacampione = new DwrPreaccettazione();
            				String result = dwrAssociacampione.Preaccettazione_Associacampione(id_campione_preaccettazione, String.valueOf(getUserId(context)), codice_preaccettazione);
            				JSONObject jsonObj;
            				jsonObj = new JSONObject(result);
                			System.out.println(jsonObj.getString("messaggio"));
                                            			
                        }
                      
                        context.getRequest().setAttribute("TicketDetails", newTicket);
                        addModuleBean(context, "ViewTickets", "Ticket Insert ok");
                } catch (Exception e) {
                        e.printStackTrace();
                        context.getRequest().setAttribute("Error", e);
                        return ("SystemError");
                }
                return "" ;
        }

        /**
         * Loads the ticket for modification
         *
         * @param context
         *            Description of Parameter
         * @return Description of the Returned Value
         */
        public String executeCommandModifyTicket(ActionContext context) {

                Connection db = null ;
                Ticket newTic = null;
                // Parameters
                String ticketId = context.getRequest().getParameter("id");
                SystemStatus systemStatus = this.getSystemStatus(context);
                try {
                        db=this.getConnection(context);
                        
                        User user = this.getUser(context, this.getUserId(context));
                        // Load the ticket
                                newTic = new Ticket(db, Integer.parseInt(ticketId));
                        
                        newTic.setIdControlloUfficiale(context.getRequest().getParameter("idControlloUfficiale"));
                        //check permission to record
                        
                        /* ************************************************************************************************************* */
                        LookupList lookup_tipo_ispezione = new LookupList();
                        lookup_tipo_ispezione.setTable("lookup_tipo_ispezione");
                        lookup_tipo_ispezione.buildListWithEnabled(db);
                        
                        org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket();
                        cu.queryRecord(db, Integer.parseInt(context.getRequest().getParameter("idControlloUfficiale")));
                        
                        HashMap<Integer, String> tipo_ispezone_valori = cu.getTipoIspezione();


                        int size = lookup_tipo_ispezione.size();
                        int ind = 0 ;
                        for ( ind = 0 ; ind<size;ind++)
                        {
                                LookupElement el = (LookupElement)lookup_tipo_ispezione.get(ind) ;
                               

                       		 if ((tipo_ispezone_valori.containsKey(el.getCode())==false) &&  
                                		!el.getCodiceInterno().equalsIgnoreCase("22a") &&
                                		!el.getCodiceInterno().equalsIgnoreCase("24a")&& 
                                		!el.getCodiceInterno().equalsIgnoreCase("41a") &&
                                		!el.getCodiceInterno().equalsIgnoreCase("42a") && 
                                		!el.getCodiceInterno().equalsIgnoreCase("43a"))
                                {
                                        lookup_tipo_ispezione.remove(ind);
                                        size-=1 ;
                                        ind =-1 ;
                                }
                                

                        }
                        
                        for ( ind = 0 ; ind<size;ind++)
                        {
                                LookupElement el = (LookupElement)lookup_tipo_ispezione.get(ind) ;
                                if ((el.isEnabled()) &&
                                		(
                                el.getCodiceInterno().equalsIgnoreCase("22a") ||                        		el.getCodiceInterno().equalsIgnoreCase("24a")&& 
                        		el.getCodiceInterno().equalsIgnoreCase("41a") ||
                        		el.getCodiceInterno().equalsIgnoreCase("42a") ||
                        		el.getCodiceInterno().equalsIgnoreCase("43a")))
                                {
                                        lookup_tipo_ispezione.remove(ind);
                                        el.setEnabled(true);
                                        lookup_tipo_ispezione.add(ind, el);
                                }
                        }
                        
                        lookup_tipo_ispezione.addItem(-1,
                                        "-- SELEZIONA VOCE --");
                        context.getRequest().setAttribute("Motivazione", lookup_tipo_ispezione);
                        /* ************************************************************************************** */
                        
                        /*LookupList lookup_tipo_ispezione = new LookupList(db,
                        "lookup_tipo_ispezione");

                        context.getRequest().setAttribute("Motivazione",
                                        lookup_tipo_ispezione);
                         */
                        LookupList quesiti_diagn = new LookupList(db, "quesiti_diagnostici_sigla");
                quesiti_diagn.addItem(-1, "-- SELEZIONA VOCE --");
                context.getRequest().setAttribute("QuesitiList", quesiti_diagn);
                        
                        LookupList lookup_piani= new LookupList(db,
                                        "lookup_piano_monitoraggio",true);

                        context.getRequest().setAttribute("Piani",
                                        lookup_piani);


                        LookupList SiteIdList = new LookupList(db, "lookup_site_id");
                        SiteIdList.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        SiteIdList.addItem(-2, "-- TUTTI --"
                        );
                        context.getRequest().setAttribute("SiteIdList", SiteIdList);

                        LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
                        TipoCampione.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("TipoCampione", TipoCampione);

                        LookupList EsitoCampione = new LookupList(db,
                        "lookup_esito_campione");
                        EsitoCampione.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("EsitoCampione", EsitoCampione);



                        // aggiunto da d.dauria
                        LookupList conseguenzepositivita = new LookupList(db,
                        "lookup_conseguenze_positivita");
                        conseguenzepositivita.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("ConseguenzePositivita",
                                        conseguenzepositivita);


                        // aggiunto da d.dauria
                        LookupList responsabilitapositivita = new LookupList(db,
                        "lookup_responsabilita_positivita");
                        responsabilitapositivita.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("ResponsabilitaPositivita",
                                        responsabilitapositivita);


                        String id_controllo = context.getRequest().getParameter("idC");
                        while(id_controllo.startsWith("0")){

                                id_controllo=id_controllo.substring(1);
                        }

                        context.getRequest().setAttribute("idC", id_controllo);

                        LookupList DestinatarioCampione = new LookupList(db,
                        "lookup_destinazione_campione");
                        DestinatarioCampione.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("DestinatarioCampione",
                                        DestinatarioCampione);

                        context.getRequest().setAttribute("TicketDetails", newTic);


                        context.getRequest().setAttribute("orgId", newTic.getOrgId());
                } catch (Exception errorMessage) {
                        errorMessage.printStackTrace();
                        context.getRequest().setAttribute("Error", errorMessage);
                        return ("SystemError");
                }
                finally
                {
                        this.freeConnection(context, db);
                }

                return "AddEsitoOK";
        }
        
        
        public String executeCommandModifyTicketEsito(ActionContext context) {

                Connection db = null ;
                Ticket newTic = null;
                // Parameters
                String ticketId = context.getRequest().getParameter("id");
                SystemStatus systemStatus = this.getSystemStatus(context);
                try {
                        db=this.getConnection(context);
                        
                        User user = this.getUser(context, this.getUserId(context));
                        // Load the ticket
                                newTic = new Ticket(db, Integer.parseInt(ticketId));
                        
                        newTic.setIdControlloUfficiale(context.getRequest().getParameter("idControlloUfficiale"));
                        //check permission to record
                        
                        /* ************************************************************************************************************* */
                        LookupList lookup_tipo_ispezione = new LookupList();
                        lookup_tipo_ispezione.setTable("lookup_tipo_ispezione");
                        lookup_tipo_ispezione.buildListWithEnabled(db);
                        
                        org.aspcfs.modules.vigilanza.base.Ticket cu = new org.aspcfs.modules.vigilanza.base.Ticket();
                        cu.queryRecord(db, Integer.parseInt(context.getRequest().getParameter("idControlloUfficiale")));
                        

                        context.getRequest().setAttribute("IdAnalita", context.getRequest().getParameter("id_analita"));
                        int size = lookup_tipo_ispezione.size();
                        int ind = 0 ;
                        
                        LookupList quesiti_diagn = new LookupList(db, "quesiti_diagnostici_sigla");
                quesiti_diagn.addItem(-1, "-- SELEZIONA VOCE --");
                context.getRequest().setAttribute("QuesitiList", quesiti_diagn);
                        
                        LookupList lookup_piani= new LookupList(db,
                                        "lookup_piano_monitoraggio",true);

                        context.getRequest().setAttribute("Piani",
                                        lookup_piani);


                        LookupList SiteIdList = new LookupList(db, "lookup_site_id");
                        SiteIdList.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        SiteIdList.addItem(-2, "-- TUTTI --"
                        );
                        context.getRequest().setAttribute("SiteIdList", SiteIdList);

                        LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
                        TipoCampione.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("TipoCampione", TipoCampione);

                        LookupList EsitoCampione = new LookupList(db,
                        "lookup_esito_campione");
                        EsitoCampione.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("EsitoCampione", EsitoCampione);



                        // aggiunto da d.dauria
                        LookupList conseguenzepositivita = new LookupList(db,
                        "lookup_conseguenze_positivita");
                        conseguenzepositivita.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("ConseguenzePositivita",
                                        conseguenzepositivita);


                        // aggiunto da d.dauria
                        LookupList responsabilitapositivita = new LookupList(db,
                        "lookup_responsabilita_positivita");
                        responsabilitapositivita.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("ResponsabilitaPositivita",
                                        responsabilitapositivita);


                        LookupList DestinatarioCampione = new LookupList(db,
                        "lookup_destinazione_campione");
                        DestinatarioCampione.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("DestinatarioCampione",
                                        DestinatarioCampione);

                        context.getRequest().setAttribute("TicketDetails", newTic);


                        context.getRequest().setAttribute("orgId", newTic.getOrgId());
                } catch (Exception errorMessage) {
                        errorMessage.printStackTrace();
                        context.getRequest().setAttribute("Error", errorMessage);
                        return ("SystemError");
                }
                finally
                {
                        this.freeConnection(context, db);
                }

                return "AddEsitoOK";
        }

        /**
         * Description of the Method
         *
         * @param context
         *            Description of Parameter
         * @return Description of the Returned Value
         */
        public String executeCommandModify(ActionContext context) {
                if (!hasPermission(context, "accounts-accounts-edit")) {
                        return ("PermissionError");
                }
                Connection db = null;
                Ticket newTic = null;
                // Parameters
                String ticketId = context.getRequest().getParameter("id");
                SystemStatus systemStatus = this.getSystemStatus(context);
                try {
                        db = this.getConnection(context);
                        User user = this.getUser(context, this.getUserId(context));
                        // Load the ticket
        
                                newTic = new Ticket();
                                newTic.queryRecord(db, Integer.parseInt(ticketId));
                

                        // find record permissions for portal users
                        if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
                                return ("PermissionError");
                        }

                        

                

                        LookupList quesiti_diagn = new LookupList(db, "quesiti_diagnostici_sigla");
                quesiti_diagn.addItem(-1, "-- SELEZIONA VOCE --");
                context.getRequest().setAttribute("QuesitiList", quesiti_diagn);
                        
                

                        LookupList SiteIdList = new LookupList(db, "lookup_site_id");
                        SiteIdList.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("SiteIdList", SiteIdList);

                        LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
                        TipoCampione.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        TipoCampione.setDisabled(true);
                        context.getRequest().setAttribute("TipoCampione", TipoCampione);

                        LookupList EsitoCampione = new LookupList(db,
                        "lookup_esito_campione");
                        EsitoCampione.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("EsitoCampione", EsitoCampione);

                        LookupList DestinatarioCampione = new LookupList(db,
                        "lookup_destinazione_campione");
                        DestinatarioCampione.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("DestinatarioCampione",
                                        DestinatarioCampione);

                        Organization orgDetails = new Organization(db, newTic.getOrgId());
                        context.getRequest().setAttribute("OrgDetails", orgDetails);

                        context.getRequest().setAttribute("TicketDetails", newTic);
                        addRecentItem(context, newTic);

                        // getting current date in mm/dd/yyyy format
                        String currentDate = getCurrentDateAsString(context);
                        context.getRequest().setAttribute("currentDate", currentDate);
                } catch (Exception errorMessage) {
                        context.getRequest().setAttribute("Error", errorMessage);
                        return ("SystemError");
                } finally {
                        this.freeConnection(context, db);
                }
                context.getRequest().setAttribute("TicketDetails", newTic);
                addModuleBean(context, "ViewTickets", "View Tickets");

                String retPage = "Modify";
                

                return getReturn(context, retPage);
        }

        /**
         * Description of the Method
         *
         * @param context
         *            Description of Parameter
         * @return Description of the Returned Value
         */
        public String executeCommandUpdate(ActionContext context) {
                if (!hasPermission(context, "accounts-accounts-edit")) {
                        return ("PermissionError");
                }
                int resultCount = 0;
                int catCount = 0;
                TicketCategory thisCat = null;
                boolean catInserted = false;
                boolean isValid = true;
                Ticket newTic = (Ticket) context.getFormBean();
                Connection db = null;
                SystemStatus systemStatus = this.getSystemStatus(context);
                try {
                        db = this.getConnection(context);
                        //check permission to record
                        if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
                                return ("PermissionError");
                        }
                        Organization orgDetails = new Organization(db, newTic.getOrgId());

                        LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
                        TipoCampione.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("TipoCampione", TipoCampione);

                        LookupList DestinatarioCampione = new LookupList(db,
                        "lookup_destinazione_campione");
                        DestinatarioCampione.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("DestinatarioCampione",
                                        DestinatarioCampione);

                        LookupList EsitoCampione = new LookupList(db,
                        "lookup_esito_campione");
                        EsitoCampione.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("EsitoCampione", EsitoCampione);

                        
                        for (int p = 0; p<10; p++) {
        	                if(context.getParameter("campionePuntoPrelievo_"+p)!=null){
        	                	int campionePuntoPrelievo = Integer.parseInt(context.getParameter("campionePuntoPrelievo_"+p));
        	                    CoordinateMolluschiBivalvi c = new CoordinateMolluschiBivalvi(db, campionePuntoPrelievo);
        	                    newTic.getListaCoordinateCampione().add(c);
        	                }
                        }
                         
                        newTic.setModifiedBy(getUserId(context));
                        if(context.getRequest().getParameter("segnalazione")!=null)
                                newTic.setSegnalazione(true);
                        // Get the previousTicket, update the ticket, then send both to a
                        // hook
                        Ticket previousTicket = new Ticket(db, newTic.getId());
                
                        newTic.setSiteId(Organization.getOrganizationSiteId(db, newTic
                                        .getOrgId()));
                        isValid = this.validateObject(context, db, newTic) && isValid;
                        if (isValid) {
                                newTic.setTestoAppoggio("Modifica"); //aggiunto da d.dauria
                                resultCount = newTic.update(db);


                                if (resultCount == 1) {
                                        newTic.queryRecord(db, newTic.getId());
                                        processUpdateHook(context, previousTicket, newTic);
                                }
                        }

                } catch (Exception e) {
                        context.getRequest().setAttribute("Error", e);
                        return ("SystemError");
                } finally {
                        this.freeConnection(context, db);
                }
                if (resultCount == 1) {
                        if (context.getRequest().getParameter("return") != null
                                        && context.getRequest().getParameter("return").equals(
                                        "list")) {
                                return (executeCommandDettaglio(context));
                        }
                        return getReturn(context, "Update");
                }
                return (executeCommandModify(context));
        }

        public String executeCommandDettaglio(ActionContext context) {
                if (!hasPermission(context, "accounts-accounts-view")) {
                        return ("PermissionError");
                }
                Connection db = null;
                Ticket newTic = null;
                String ticketId = null;
                SystemStatus systemStatus = this.getSystemStatus(context);
                try {
                        String fromDefectDetails = context.getRequest().getParameter(
                        "defectCheck");
                        if (fromDefectDetails == null
                                        || "".equals(fromDefectDetails.trim())) {
                                fromDefectDetails = (String) context.getRequest().getAttribute(
                                "defectCheck");
                        }

                        // Parameters
                        ticketId = context.getRequest().getParameter("id");
                        // Reset the pagedLists since this could be a new visit to this
                        // ticket
                        deletePagedListInfo(context, "TicketDocumentListInfo");
                        deletePagedListInfo(context, "SunListInfo");
                        deletePagedListInfo(context, "TMListInfo");
                        deletePagedListInfo(context, "CSSListInfo");
                        deletePagedListInfo(context, "TicketsFolderInfo");
                        deletePagedListInfo(context, "TicketTaskListInfo");
                        deletePagedListInfo(context, "ticketPlanWorkListInfo");
                        db = this.getConnection(context);
                        // Load the ticket
                        newTic = new Ticket();
                        newTic.queryRecord(db, Integer.parseInt(ticketId));

                        // find record permissions for portal users
                        if (!isRecordAccessPermitted(context, db, newTic.getOrgId())) {
                                return ("PermissionError");
                        }
                        
                        
                        // Load the ticket state

                        LookupList TipoCampione = new LookupList(db, "lookup_provvedimenti");
                        TipoCampione.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("TipoCampione", TipoCampione);

                        LookupList EsitoCampione = new LookupList(db,
                        "lookup_sanzioni_amministrative");
                        EsitoCampione.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("EsitoCampione", EsitoCampione);

                        LookupList lookup_molluschi = new LookupList(db,
                        "lookup_molluschi");
                        lookup_molluschi.addItem(-1,
                        "-- SELEZIONA VOCE --");
                        context.getRequest().setAttribute("lookupmolluschi",
                                        lookup_molluschi);

                        LookupList lookup_specie_alimento = new LookupList(db,
                        "lookup_specie_alimento");
                        lookup_specie_alimento.addItem(-1,
                        "-- SELEZIONA VOCE --");
                        context.getRequest().setAttribute("lookupSpecieAlimento",
                                        lookup_specie_alimento);

                        LookupList lookup_tipologia_alimento = new LookupList(db,
                        "lookup_tipologia_alimento");
                        lookup_tipologia_alimento.addItem(-1,
                        "-- SELEZIONA VOCE --");
                        context.getRequest().setAttribute("lookupTipologiaAlimento",
                                        lookup_tipologia_alimento);

                        LookupList SiteIdList = new LookupList(db, "lookup_site_id");
                        SiteIdList.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("SiteIdList", SiteIdList);

                        LookupList SanzioniPenali = new LookupList(db,
                        "lookup_sanzioni_penali");
                        SanzioniPenali.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("SanzioniPenali", SanzioniPenali);

                        LookupList Sequestri = new LookupList(db, "lookup_sequestri");
                        Sequestri.addItem(-1, systemStatus
                                        .getLabel("calendar.none.4dashes"));
                        context.getRequest().setAttribute("Sequestri", Sequestri);
                        

                        
                } catch (Exception e) {
                        context.getRequest().setAttribute("Error", e);
                        return ("SystemError");
                } finally {
                        this.freeConnection(context, db);
                }
                context.getRequest().setAttribute("TicketDetails", newTic);
                addRecentItem(context, newTic);
                addModuleBean(context, "ViewTickets", "View Tickets");

                String retPage = "DettaglioOK";
                String tipo_richiesta = newTic.getTipo_richiesta();
                tipo_richiesta = (tipo_richiesta == null) ? ("") : (tipo_richiesta);

                retPage = "DettaglioOK";

                

                return (retPage);
        }

        public String executeCommandChiudi(ActionContext context) {
                if (!(hasPermission(context, "accounts-accounts-campioni-edit"))) {
                        return ("PermissionError");
                }
                int resultCount = -1;
                Connection db = null;
                Ticket thisTicket = null;
                try {
                        db = this.getConnection(context);
                        thisTicket = new Ticket(db, Integer.parseInt(context.getRequest()
                                        .getParameter("id")));
                        Ticket oldTicket = new Ticket(db, thisTicket.getId());
                        //check permission to record
                        //if (!isRecordAccessPermitted(context, db, thisTicket.getOrgId())) {
                        if (!(hasPermission(context, "accounts-accounts-campioni-edit"))) {
                                return ("PermissionError");
                        }
                        thisTicket.setModifiedBy(getUserId(context));
                        resultCount = thisTicket.chiudi(db);
                        if (resultCount == 0)
                        {
                                context.getRequest().setAttribute("Messaggio", "Chiusura non avvenuta assicurarsi di aver inserito l' Esito");
                        }
                        thisTicket.getIdControlloUfficiale();
                        String padd="000000";
                        String id_controllo=thisTicket.getIdControlloUfficiale();
                        while(id_controllo.startsWith("0")){

                                id_controllo=id_controllo.substring(1);
                        }

                        context.getRequest().setAttribute("idC", id_controllo);






                        /*
                         * Inifio Giuseppe
                         *
                         */


                        org.aspcfs.modules.vigilanza.base.Ticket thisTicketV = new org.aspcfs.modules.vigilanza.base.Ticket(
                                        db, Integer.parseInt(id_controllo));


                        String ticketId = context.getRequest().getParameter("id");



                        org.aspcfs.modules.campioni.base.TicketList ticList = new org.aspcfs.modules.campioni.base.TicketList();
                        int passedId = thisTicketV.getOrgId();
                        ticList.setOrgId(passedId);
                        ticList.buildListControlli(db, passedId, id_controllo);


                        org.aspcfs.modules.tamponi.base.TicketList tamponiList = new org.aspcfs.modules.tamponi.base.TicketList();
                        int pasId = thisTicketV.getOrgId();
                        tamponiList.setOrgId(passedId);
                        tamponiList.buildListControlli(db, pasId, id_controllo);




                        Iterator campioniIterator=ticList.iterator();
                        Iterator tamponiIterator=tamponiList.iterator();



                        int flag=0;
                        while(campioniIterator.hasNext()){

                                org.aspcfs.modules.campioni.base.Ticket tic = (org.aspcfs.modules.campioni.base.Ticket) campioniIterator.next();

                                if(tic.getClosed()==null){
                                        flag=1;

                                        break;

                                }

                        }


                        while(tamponiIterator.hasNext()){

                                org.aspcfs.modules.tamponi.base.Ticket tic = (org.aspcfs.modules.tamponi.base.Ticket) tamponiIterator.next();

                                if(tic.getClosed()==null){
                                        flag=1;

                                        break;
                                }

                        }




                        if(thisTicketV.isNcrilevate()==true){
                                org.aspcfs.modules.nonconformita.base.TicketList nonCList = new org.aspcfs.modules.nonconformita.base.TicketList();
                                int passIdN = thisTicketV.getOrgId();
                                nonCList.setOrgId(passedId);
                                nonCList.buildListControlli(db, passIdN, id_controllo);

                                Iterator ncIterator=nonCList.iterator();

                                while(ncIterator.hasNext()){

                                        org.aspcfs.modules.nonconformita.base.Ticket tic = (org.aspcfs.modules.nonconformita.base.Ticket) ncIterator.next();

                                        if(tic.getClosed()==null){
                                                flag=1;

                                                break;

                                        }

                                }

                        }






                        if(thisTicketV.getTipoCampione()==5){

                                org.aspcfs.modules.audit.base.AuditList audit = new org.aspcfs.modules.audit.base.AuditList();
                                int AuditOrgId = thisTicketV.getOrgId();
                                String idTi = thisTicketV.getPaddedId();
                                audit.setOrgId(AuditOrgId);

                                audit.buildListControlli(db, AuditOrgId, idTi);

                                Iterator itAudit=audit.iterator();

                                if(!itAudit.hasNext()){

                                        flag=2;

                                }else{

                                        while(itAudit.hasNext()){

                                                org.aspcfs.modules.audit.base.Audit auditTemp = (org.aspcfs.modules.audit.base.Audit) itAudit.next();
                                                Organization orgDetails = new Organization(db,thisTicketV.getOrgId());

                                                if(thisTicketV.isCategoriaisAggiornata()==false){
                                                        flag=2;

                                                        break;

                                                }

                                        }}
                        }







                        String attivitaCollegate=context.getRequest().getParameter("altro");

                

                        if(attivitaCollegate==null){
                                if(flag==1 || flag==2){
                                        context.getRequest().setAttribute("Chiudi", ""+flag);
                                        return (executeCommandTicketDetails(context,db));

                                }
                        }


                        String chiudiCu = context.getRequest().getParameter("chiudiCu");
                        if(flag==0 ){
                                if(chiudiCu !=null)
                                {
                                        thisTicketV.setModifiedBy(getUserId(context));
                                        resultCount = thisTicketV.chiudi(db);
                                }else
                                {
                                        context.getRequest().setAttribute("Messaggio2", "Attivita collegate al cu chiuse");
                                }
                        }

                        if (resultCount == -1) {
                                return (executeCommandTicketDetails(context,db));
                        } else if (resultCount == 1) {
                                thisTicket.queryRecord(db, thisTicket.getId());
                                this.processUpdateHook(context, oldTicket, thisTicket);



                                return (executeCommandTicketDetails(context,db));
                        }



                } catch (Exception errorMessage) {
                        context.getRequest().setAttribute("Error", errorMessage);
                        return ("SystemError");
                } finally {
                        this.freeConnection(context, db);
                }
                context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
                return ("UserError");

        }


        public String executeCommandStampaScheda(ActionContext context) {
                /*if (!hasPermission(context, "cani-smarriti-view")) {
                            return ("PermissionError");
              }
                 */
                Connection db = null;
                try {
                        db = this.getConnection(context);
                        String descrizioneAsl = "";

                        //inizializzazione della connessione al db
                        db = this.getConnection(context);
                        //instanzia il bean
                        Filtro f = new Filtro();
                        HttpServletResponse res = context.getResponse();

                        //recupero dei dati di input

                        String id_campione = context.getParameter("idCampione");
                        f.setIdCampione(Integer.parseInt(id_campione));
                        ResultSet rs = f.queryRecord_campioni(db);

                        if (rs.next())
                        {
                                f.setRagioneSociale(rs.getString("ragione_sociale"));
                                f.setNumVerbale(rs.getString("num_verbale"));
                        }        
                        StampaPdf s = new StampaPdf(context,f);
                        s.stampaVerbaleCampioneBattereologico(context, f,id_campione);


                } catch (Exception e) {
                        e.printStackTrace();
                        context.getRequest().setAttribute("Error", e);
                        return ("SystemError");
                } finally {
                        this.freeConnection(context, db);
                }
                return ("-none-");//getReturn(context, "AssetsSearchList");
        }
        
        private boolean cercaPNAA (Connection db, int idPiano,int idCU) throws SQLException{
        	
        	boolean esito = false;
        	esito = PopolaCombo.hasEventoMotivoCU("isPnaa", idPiano, -1);
        	return esito;
        	
        }
        
        public static boolean controllaEsistenzaSchedaModulo (Connection db, int idCampione){
            
            StringBuffer sqlSelect = new StringBuffer();
            PreparedStatement pst = null;
            boolean esito = false;
                       
            sqlSelect.append("SELECT * from moduli_campioni_fields_value where id_campione = ? " +
                            "and enabled = true");
            try {
                    pst = db.prepareStatement(sqlSelect.toString());
            
            pst.setInt(1, idCampione);
            ResultSet rs = pst.executeQuery();
            
          if (rs.next())
                  esito = true;
          
            } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
           return esito;
    }
}

