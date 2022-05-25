/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.dpat2019.action;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.hslf.model.Sheet;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.dpat2019.base.DpatIstanza;
import org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcolo;
import org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcoloNominativi;
import org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcoloNominativiList;
import org.aspcfs.modules.dpat2019.base.DpatStruttura;
import org.aspcfs.modules.dpat2019.base.oia.OiaNodo;
import org.aspcfs.modules.gestioneDocumenti.actions.GestioneDocumentiDPAT;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.UserUtils;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.itextpdf.text.pdf.codec.Base64.OutputStream;

public class DpatSDC extends CFSModule {


	
	public String executeCommandInitNuovoAnno(ActionContext context) 
	{ 
		Connection db = null;
		ArrayList<DpatIstanza> anniList = new ArrayList<DpatIstanza>();
		try 
		{
			db = this.getConnection(context);
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "--Seleziona--");
			siteList.remove(siteList.size() - 1);

			int annoCorrente = Integer.parseInt((String)context.getParameter("annoCorrente"));
			PreparedStatement pst = db.prepareStatement("select * from dpat_duplica_strumento_calcolo(?,?)");
			pst.setInt(1, annoCorrente);
			pst.setInt(2, annoCorrente+1);
			pst.execute();
			
			
			// String sql = "select anno from dpat group by anno order by anno";
			String sql = "select *  from dpat_istanza where trashed_date is null   order by anno desc";
			pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) 
			{
				DpatIstanza ist = new DpatIstanza();
				ist.buildRecord(rs);
				anniList.add(ist);
			}
			
			
			rs.close();
			pst.close();
			context.getRequest().setAttribute("anniList", anniList);
			context.getRequest().setAttribute("siteList", siteList);
			context.getRequest().setAttribute("annoCorrente", annoCorrente+"");
			
			LookupList lookupTipologia = new LookupList();
			lookupTipologia.addItem(-1,  "-- SELEZIONA VOCE --");
			lookupTipologia.addItem(13,  "STRUTTURA COMPLESSA");
			lookupTipologia.addItem(14,  "STRUTTURA SEMPLICE DIPARTIMENTALE");
			context.getRequest().setAttribute("lookupTipologia", lookupTipologia);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			super.freeConnection(context, db);
		}

		return "DpatHomeRegOK"; /*home.jsp */
	}
	
	
	public String executeCommandGetStatoAvanzamento(ActionContext context) 
	{ 
		Connection db = null;
		ArrayList<DpatStruttura> strutture = new ArrayList<DpatStruttura>();
		try 
		{
			db = this.getConnection(context);
			
			int annoCorrente = Integer.parseInt((String)context.getParameter("annoCorrente"));
			
			String sql = "select * from dpat_get_stato_avanzamento_organigramma(?, null) ";
			
			
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, annoCorrente);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) 
			{
				DpatStruttura struttura = new DpatStruttura();
				struttura.setStato(rs.getInt("id_stato"));
				struttura.setAsl_stringa(rs.getString("asl"));
				struttura.setDescrizione_lunga(rs.getString("descrizione_struttura"));
				struttura.setId_asl(rs.getInt("id_asl"));
				struttura.setDescrizioneStato(rs.getString("stato"));
				strutture.add(struttura);
			}
			
			rs.close();
			pst.close();
			context.getRequest().setAttribute("strutture", strutture);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			super.freeConnection(context, db);
		}

		return "DpatStatoAvanzamentoOrganigrammaOK"; /*home.jsp */
	}
	
	
	public String executeCommandAddModify(ActionContext context){
		Connection db = null;
		UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");
		int idAsl = -1;
		DpatStrumentoCalcolo dpatStrumentoCalcolo = null ;
		int annoCorrente =  -1 ;
		try {
			
			db = this.getConnection(context);
			idAsl = Integer.parseInt(context.getRequest().getParameter("idAsl"));
			String annoString=  context.getRequest().getParameter("anno") ;
			
			Calendar calCorrente = GregorianCalendar.getInstance();
			Date dataCorrente = new Date(System.currentTimeMillis());
			int tolleranzaGiorni = Integer.parseInt(org.aspcfs.modules.vigilanza.blocchicu.ApplicationProperties.getProperty("TOLLERANZA_MODIFICA_CU"));
			dataCorrente.setDate(dataCorrente.getDate()- tolleranzaGiorni);
			calCorrente.setTime(new Timestamp(dataCorrente.getTime()));
			
			annoCorrente = calCorrente.get(Calendar.YEAR);
			
			int anno;
			if(annoString==null)
				anno = annoCorrente;
			else
				anno = Integer.parseInt(annoString);
			
			String idArea = "" ;
			
			
			idArea = context.getRequest().getParameter("combo_area");
			int idStrutturaAreaSelezionata = -1 ;
			if (idArea!=null && !"".equals(idArea))
				idStrutturaAreaSelezionata = Integer.parseInt(idArea);
			else 
			{
				if (context.getRequest().getAttribute("idArea")!=null)
					idStrutturaAreaSelezionata = (Integer)context.getRequest().getAttribute("idArea");
			}
			
			context.getRequest().setAttribute("idStrutturaAreaSelezionata", idStrutturaAreaSelezionata);
			
			PreparedStatement pst = null ;
			ResultSet rs = null ;
			
			LookupList listaAsl = new LookupList(db,"lookup_site_id");
			context.getRequest().setAttribute("ListaAsl", listaAsl);
			
			
			ArrayList<User> utentiInDpatperIdAsl =  UserUtils.getUserFromDpat(context.getRequest(),idAsl);
			context.getRequest().setAttribute( "ListaUtenti", utentiInDpatperIdAsl );
			
			Timestamp current_date = new Timestamp(System.currentTimeMillis());
			dpatStrumentoCalcolo = new DpatStrumentoCalcolo(db,idAsl,anno,this.getSystemStatus(context),idStrutturaAreaSelezionata);
			dpatStrumentoCalcolo.setIdStrutturaAreaSelezionata(idStrutturaAreaSelezionata);
			if (dpatStrumentoCalcolo.getId()<=0 && getUserSiteId(context)>0 )
			{ 
				dpatStrumentoCalcolo.setIdAsl(idAsl);
				dpatStrumentoCalcolo.setAnno(anno);
				dpatStrumentoCalcolo.setEntered(current_date);
				dpatStrumentoCalcolo.setModified(current_date);
				dpatStrumentoCalcolo.setEnteredby(utente.getUserId());
				dpatStrumentoCalcolo.setModifiedby(utente.getUserId());
				//dpatStrumentoCalcolo.inserDpatStrumentoCalcolo(db,context); // la configurazione e' avvenuta manualmente a partire dalle strutture complesse dell'anno precedente
				dpatStrumentoCalcolo = new DpatStrumentoCalcolo(db,idAsl,anno,this.getSystemStatus(context),idStrutturaAreaSelezionata);

			}

			 pst = db.prepareStatement("select id from dpat_strutture_asl where n_livello=1 and id_asl="+idAsl);
			 rs = pst.executeQuery();
			int id_nonno = -1;
			while (rs.next()){
				id_nonno = rs.getInt("id");
			}
			context.getRequest().setAttribute("id_nonno", id_nonno);
			LookupList qualifiche = new LookupList(db,"lookup_qualifiche");
			qualifiche.addItem(-1, "Seleziona Qualifica") ;
			context.getRequest().setAttribute("Qualifica", qualifiche);


			LookupList lookupTipologia = new LookupList(db, "lookup_tipologia_nodo_oia");
			
			lookupTipologia.addItem(-1,  "-- SELEZIONA VOCE --");
			lookupTipologia.removeElementByCode(40);
			context.getRequest().setAttribute("lookupTipologia", lookupTipologia);
			
			LookupList lookupTipologia2 = new LookupList();
			lookupTipologia2.setShowDisabledFlag(false);
			lookupTipologia2.setTableName("lookup_tipologia_nodo_oia");
			lookupTipologia2.buildListWithEnabled(db);
			
			context.getRequest().setAttribute("lookupTipologia", lookupTipologia);
			context.getRequest().setAttribute("lookupTipologia2", lookupTipologia2);

			context.getRequest().setAttribute("DpatSDC", dpatStrumentoCalcolo);
			
			
	

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}	
		
		//&& dpatStrumentoCalcolo.getStrutturaAmbito().getIdUtenteEdit() == utente.getUserId()
		if (idAsl>0   && dpatStrumentoCalcolo.getAnno()>=annoCorrente && hasPermission(context, "dpat-edit")  )
			return ("DpatSDCAddModifyOK"); /*VISUALIZZAZIONE UTENTE ASL*/
		
		context.getRequest().setAttribute("anno", annoCorrente+"");
		return executeCommandDetails(context); /*VISUALIZZAZIONE UTENTE SENZA ASL*/
	}
	
	
	
	

	


	public String executeCommandDetails(ActionContext context){
		Connection db = null;
		UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");
		int idAsl = -1;
		DpatStrumentoCalcolo dpatStrumentoCalcolo = null ;
		try {
			db = this.getConnection(context);
			idAsl = Integer.parseInt(context.getRequest().getParameter("idAsl"));
			Timestamp current_date = new Timestamp(System.currentTimeMillis());
			String anno=  context.getRequest().getParameter("anno") ;
			if (anno == null)
				anno= (String)context.getRequest().getAttribute("anno");

			
			String idArea = "" ;
			
			int idStrutturaAreaSelezionata = -1 ;

			if (context.getRequest().getAttribute("DpatSDC")!=null)
			{
				dpatStrumentoCalcolo = (DpatStrumentoCalcolo)context.getRequest().getAttribute("DpatSDC");
				idStrutturaAreaSelezionata=dpatStrumentoCalcolo.getIdStrutturaAreaSelezionata();
			}
			if (idStrutturaAreaSelezionata<=0 && context.getRequest().getParameter("combo_area")!=null)
			{
				idArea = context.getRequest().getParameter("combo_area");
				if (idArea!=null && !"".equals(idArea))
					idStrutturaAreaSelezionata = Integer.parseInt(idArea);
			}
			else
			{
				idStrutturaAreaSelezionata = (Integer)context.getRequest().getAttribute("idStrutturaAreaSelezionata");
			}
			
			
			dpatStrumentoCalcolo = new DpatStrumentoCalcolo(db,idAsl,Integer.parseInt(anno),this.getSystemStatus(context),idStrutturaAreaSelezionata);

			LookupList qualifiche = new LookupList(db,"lookup_qualifiche");
			qualifiche.addItem(-1, "Seleziona Qualifica") ;
			context.getRequest().setAttribute("Qualifica", qualifiche);


			LookupList listaAsl = new LookupList(db,"lookup_site_id");
			context.getRequest().setAttribute("ListaAsl", listaAsl);
			
			
			LookupList lookupTipologia = new LookupList(db, "lookup_tipologia_nodo_oia");
			lookupTipologia.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("lookupTipologia", lookupTipologia);
			context.getRequest().setAttribute("DpatSDC", dpatStrumentoCalcolo);
			
			LookupList lookupTipologia2 = new LookupList();
			lookupTipologia2.setShowDisabledFlag(false);
			lookupTipologia2.setTableName("lookup_tipologia_nodo_oia");
			lookupTipologia2.buildListWithEnabled(db);
			
			context.getRequest().setAttribute("lookupTipologia2", lookupTipologia2);

			
			String sql = "select id from dpat where id_asl="+idAsl+" and anno="+anno;
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			int idDpat = -1;
			while (rs.next()){
				idDpat = rs.getInt("id");  
			}
			org.aspcfs.modules.dpat2019.base.Dpat d = new org.aspcfs.modules.dpat2019.base.Dpat();
			d.builRecordSdc(idDpat, db,this.getSystemStatus(context));
			context.getRequest().setAttribute("dpat", d);
			pst.close();

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}	

		// SERVER DOCUMENTALE
		String layout = context.getRequest().getParameter("layout");
		if (layout!=null && layout.equals("style"))
			return ("DpatSDCDetailStyleOK");
//SERVER DOCUMENTALE

	return ("DpatSDCDetailOK");


	}


	public String executeCommandSaveOiaStruttura(ActionContext context){
		Connection db = null;
		UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");
		int tipologia =-1 ;
		
		try {
			db = this.getConnection(context);
			int idAsl = Integer.parseInt(context.getRequest().getParameter("idAsl").trim());
			int nLivello = Integer.parseInt(context.getRequest().getParameter("n_livello").trim());
			int anno = Integer.parseInt(context.getRequest().getParameter("anno").trim());
			int id_padre =-1;
			if (context.getRequest().getParameter("id_padre")!=null && !context.getRequest().getParameter("id_padre").equals("null") && !context.getRequest().getParameter("id_padre").equals(""))
				id_padre=Integer.parseInt(context.getRequest().getParameter("id_padre").trim());
			if (context.getRequest().getParameter("tipologia_struttura")!=null)
				tipologia = Integer.parseInt(context.getRequest().getParameter("tipologia_struttura").trim());
			String descrizioneLunga = context.getParameter("descrizione");

			context.getRequest().setAttribute("idAsl", idAsl);
			context.getRequest().setAttribute("anno", anno);
			int id = 0;
			if(context.getParameter("idStruttura")!=null && !context.getParameter("idStruttura").equals("") && !context.getParameter("idStruttura").equals("null"))
				id = Integer.parseInt(context.getParameter("idStruttura"));
			OiaNodo nodoStruttura = new OiaNodo();

			int idArea=-1 ;
			String areaId = context.getRequest().getParameter("id_area_sel");
			if (areaId!=null)
				idArea = Integer.parseInt(areaId);
			int tipoOperazione = Integer.parseInt(context.getParameter("tipoOperazione"));
			
			DpatStrumentoCalcolo sdc = null;
			if(idArea>0)
			{
				sdc = new DpatStrumentoCalcolo(db, idAsl, anno, this.getSystemStatus(context), idArea);
				context.getRequest().setAttribute("SDC", sdc);
			}
			
			int i = 0 ;
			PreparedStatement pst = null;
			if(id<=0)
			{
				 pst = db.prepareStatement("select * from dpat_insert_struttura(?, ?,?, ?,?,?,?,null)");
				 pst.setInt(++i,anno);	
			}
			else
			{
				if(tipoOperazione==1) //Modifica
					pst = db.prepareStatement("select * from dpat_update_struttura(?, ?,?, ?,?,?,?)");
				else //Disabilita
					pst = db.prepareStatement("select * from dpat_disabilita_struttura(?, ?,?, ?,?,?,?)");
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				pst.setInt(++i,id);	
				pst.setTimestamp(++i, new Timestamp (sdf.parse(context.getParameter("dataScadenza")).getTime()));
			}
			
			pst.setInt(++i,idAsl);
			pst.setInt(++i,tipologia);
			pst.setString(++i,descrizioneLunga);
			pst.setInt(++i,getUserId(context));	
			pst.setInt(++i,id_padre);
			if(id<=0)
			{
				if(sdc==null)
					sdc = new DpatStrumentoCalcolo(db, idAsl, anno, this.getSystemStatus(context), idArea);
				if(sdc.getStato()==1)
					pst.setInt(++i,sdc.getStato());
				else
				{
					if(tipologia==13 || tipologia ==14)
						pst.setInt(++i,0);
					else
					{
						OiaNodo padre = new OiaNodo(db, id_padre);
						pst.setInt(++i,padre.getStato());
					}
				}
			}
			System.out.println("DPAT INSERT STRUTTURA: " + pst.toString());
			pst.execute();
			
			if(tipologia==13 || tipologia ==14)
			{
				LookupList siteList = new LookupList(db, "lookup_site_id");
				siteList.addItem(-1, "--Seleziona--");
				siteList.remove(siteList.size() - 1);
				context.getRequest().setAttribute("siteList", siteList);
				
				ArrayList<DpatIstanza> anniList = new ArrayList<DpatIstanza>();
				String sql = "select *  from dpat_istanza where trashed_date is null   order by anno desc";
				pst = db.prepareStatement(sql);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {

					DpatIstanza ist = new DpatIstanza();
					ist.buildRecord(rs);
					anniList.add(ist);
				}
				rs.close();
				pst.close();
				context.getRequest().setAttribute("anniList", anniList);
			}
			else
			{
				HashMap<Integer,ArrayList<org.aspcfs.modules.oia.base.OiaNodo>> strutture_asl = (HashMap<Integer,ArrayList<org.aspcfs.modules.oia.base.OiaNodo>>)context.getServletContext().getAttribute("StruttureOIA");
				
				Calendar calCorrente = GregorianCalendar.getInstance();
				Date dataCorrente = new Date(System.currentTimeMillis());
				int tolleranzaGiorni = Integer.parseInt(org.aspcfs.modules.vigilanza.blocchicu.ApplicationProperties.getProperty("TOLLERANZA_MODIFICA_CU"));
				dataCorrente.setDate(dataCorrente.getDate()- tolleranzaGiorni);
				calCorrente.setTime(new Timestamp(dataCorrente.getTime()));
				int anno_corrente = calCorrente.get(Calendar.YEAR);
				
				org.aspcfs.modules.oia.base.OiaNodo nodo =new org.aspcfs.modules.oia.base.OiaNodo();
				strutture_asl.put(idAsl, nodo.loadbyidAsl(""+idAsl,anno_corrente, db));
				context.getServletContext().setAttribute("StruttureOIA",strutture_asl);
			}
			
			
			
		} 
		catch (Exception e) 
		{
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} 
		finally 
		{
			this.freeConnection(context, db);
		}	
		if(tipologia==13 || tipologia ==14)
		{
			LookupList lookupTipologia = new LookupList();
			lookupTipologia.addItem(-1,  "-- SELEZIONA VOCE --");
			lookupTipologia.addItem(13,  "STRUTTURA COMPLESSA");
			lookupTipologia.addItem(14,  "STRUTTURA SEMPLICE DIPARTIMENTALE");
			context.getRequest().setAttribute("lookupTipologia", lookupTipologia);
			return "DpatHomeRegOK";
		}
			else
			return "DpatSDCAddNominativoOK";
	}


	
	public String executeCommandSaveNominativo(ActionContext context){
		Connection db = null;
		UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");
		try {
			db = this.getConnection(context);
		
			int idStruttura = Integer.parseInt(context.getRequest().getParameter("struttura").trim());
			int idAsl = Integer.parseInt(context.getRequest().getParameter("idAsl").trim());
			int anno = Integer.parseInt(context.getRequest().getParameter("anno").trim());
			context.getRequest().setAttribute("idAsl", idAsl);
			context.getRequest().setAttribute("anno", anno);
			
			SystemStatus system =  this.getSystemStatus(context);
			
			
			int idArea=-1 ;
			String areaId = context.getRequest().getParameter("id_area_sel");
			if (areaId!=null)
				idArea = Integer.parseInt(areaId);
			DpatStrumentoCalcolo sdc = new DpatStrumentoCalcolo(db, idAsl, anno, this.getSystemStatus(context), idArea);
			context.getRequest().setAttribute("SDC", sdc);
			
			String[] utenti = context.getRequest().getParameterValues("utente");
			
			for(int i=0;i<utenti.length;i++)
			{
				User user  =system.getUser(Integer.parseInt(utenti[i]));
				
				DpatStrumentoCalcoloNominativi nominativoDpat = new DpatStrumentoCalcoloNominativi();
				int id= Integer.parseInt(context.getParameter("id"));
				
				/**
				 * FACCIO SCADERE IL RECORD ATTUALE
				 */
				Timestamp dataScadenza = new Timestamp(System.currentTimeMillis());
				nominativoDpat.queryRecord(db, id);
				nominativoDpat.setDataScadenza(dataScadenza);
				nominativoDpat.setModifiedBy(getUserId(context));
				nominativoDpat.update(db);
			
				/**
				 * CLONO IL RECORD CON I PARAMETRI NUOVI
				 */
				nominativoDpat.setEnteredBy(getUserId(context));
				nominativoDpat.setIdLookupQualifica(user.getRoleId());
				nominativoDpat.setIdStrumentoCalcoloStruttura(idStruttura);
				nominativoDpat.setIdAnagraficaNominativo(Integer.parseInt(utenti[i]));
				nominativoDpat.insert(db);
				
				
			}

			/*RICOSRUISCO L'OGGETTO DOPO L'AGGIORNAMENTO SULLA STRUTTURA*/
			 sdc = new DpatStrumentoCalcolo(db, idAsl, anno, this.getSystemStatus(context), idArea);
			context.getRequest().setAttribute("SDC", sdc);
		} 
		catch (Exception e) 
		{
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} 
		finally 
		{
			this.freeConnection(context, db);
		}	
		return ("DpatSDCAddNominativoOK");
		}
	 

	


	public String executeCommandDeleteStruttura(ActionContext context){
		Connection db = null;
		UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");
		try {
			db = this.getConnection(context);
			int idAsl = Integer.parseInt(context.getRequest().getParameter("idAsl"));
			int anno = Integer.parseInt(context.getRequest().getParameter("anno"));
			context.getRequest().setAttribute("idAsl", idAsl);
			context.getRequest().setAttribute("anno", anno);
			int id= Integer.parseInt(context.getParameter("id"));

			OiaNodo strutturaDpat = new OiaNodo(db,id,this.getSystemStatus(context));
//			strutturadpat2019.delete(db);
//
//						DpatStrumentoCalcoloStruttura struttura = new DpatStrumentoCalcoloStruttura(db,strutturadpat2019.getId());
//						struttura.aggiornaDatiStruttura(db);


		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}	
		return ("DpatSDCAddNominativoOK");
	}


	public String executeCommandDeleteNominativo(ActionContext context){
		Connection db = null;
		UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");
		try {
			db = this.getConnection(context);
			int idAsl = Integer.parseInt(context.getRequest().getParameter("idAsl"));
			int anno = Integer.parseInt(context.getRequest().getParameter("anno"));
			context.getRequest().setAttribute("idAsl", idAsl);
			context.getRequest().setAttribute("anno", anno);
			int id= Integer.parseInt(context.getParameter("id"));

			DpatStrumentoCalcoloNominativi nominativoDpat = new DpatStrumentoCalcoloNominativi(db,id,this.getSystemStatus(context));
			nominativoDpat.delete(db);

			int idArea=-1 ;
			String areaId = context.getRequest().getParameter("id_area_sel");
			if (areaId!=null)
				idArea = Integer.parseInt(areaId);
			DpatStrumentoCalcolo sdc = new DpatStrumentoCalcolo(db, idAsl, anno, this.getSystemStatus(context), idArea);
			context.getRequest().setAttribute("SDC", sdc);
			
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}	
		return ("DpatSDCAddNominativoOK");
	}
  
	
	
	//RIAPERTURA DELLO SDC
	public String executeCommandReopenClosedSDC(ActionContext context){
		Connection db = null;
		String id = (String)context.getRequest().getParameter("id");
		String idAsl = (String)context.getRequest().getParameter("idAsl");
		String anno = (String)context.getRequest().getParameter("anno");
		String query = "update dpat_strumento_calcolo set completo=false where id=? and anno=? and id_asl=?";
		try { 
			db = this.getConnection(context);
			PreparedStatement pst = db.prepareStatement(query);
			pst.setInt(1, Integer.parseInt(id));
			pst.setInt(2, Integer.parseInt(anno));
			pst.setInt(3, Integer.parseInt(idAsl));
			pst.executeUpdate();
			pst.close();
			
			query =  "update dpat set strutture = null where id_asl=? and anno=?";
			PreparedStatement pst2 = db.prepareStatement(query);
			pst2.setInt(1, Integer.parseInt(idAsl));
			pst2.setInt(2, Integer.parseInt(anno));
			pst2.executeUpdate();

			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.freeConnection(context, db);
		}	
		return executeCommandAddModify(context);
	}
	
	
	
	public String executeCommandCongelaStruttureReportistica(ActionContext context) {
		UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");
		String idDpat = (String)context.getRequest().getParameter("id");
		String idAsl = (String)context.getRequest().getParameter("idAsl");
		Connection db = null;
		PreparedStatement pst = null;
		
		try {
			db = this.getConnection(context);
			
			int idStrutturaAreaSelezionata = -1 ;

			
			
		
			
			int annoCorrente = -1 ;
				String anno=  context.getRequest().getParameter("anno") ;
				if (anno == null)
					annoCorrente = GregorianCalendar.getInstance().get(Calendar.YEAR ) ; 
				else
					annoCorrente=Integer.parseInt(anno);
			
				
				pst=db.prepareStatement("select * from dbi_congela_strumento_di_calcolo_reportistica(?,?)");
				pst.setInt(1,annoCorrente);
				pst.setInt(2, utente.getUserId());
				pst.execute();
				
				Dpat dpat = new Dpat();
				return dpat.executeCommandDefault(context);
				
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		
		
		return executeCommandAddModify(context);
	}
	
	
	
	public String executeCommandSalvaDefinitivo(ActionContext context) {
		UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");
		String idDpat = (String)context.getRequest().getParameter("id");
		String idAsl = (String)context.getRequest().getParameter("idAsl");
		Connection db = null;
		PreparedStatement pst = null;
		
		try {
			db = this.getConnection(context);
			
			int idStrutturaAreaSelezionata = -1 ;

			
			
			int asl=Integer.parseInt(idAsl); 
				String idArea = context.getRequest().getParameter("combo_area");
				if (idArea!=null && !"".equals(idArea))
					idStrutturaAreaSelezionata = Integer.parseInt(idArea);
			
			int annoCorrente = -1 ;
				String anno=  context.getRequest().getParameter("anno") ;
				if (anno == null)
					annoCorrente = GregorianCalendar.getInstance().get(Calendar.YEAR ) ; 
				else
					annoCorrente=Integer.parseInt(anno);
			DpatStrumentoCalcolo sdc = new DpatStrumentoCalcolo(db,asl,annoCorrente,this.getSystemStatus(context),idStrutturaAreaSelezionata);
			sdc.setIdStrutturaAreaSelezionata(idStrutturaAreaSelezionata);
			sdc.congelaStrumentoCalcolo(db, utente.getUserId());
			
			
			
			/*ricarico le strutture in memoria per farle vedere nei cu recuperando quelle con stato = 2*/
			HashMap<Integer,ArrayList<org.aspcfs.modules.oia.base.OiaNodo>> strutture_asl =  (HashMap<Integer, ArrayList<org.aspcfs.modules.oia.base.OiaNodo>>) context.getServletContext().getAttribute("StruttureOIA");
			
			
			org.aspcfs.modules.oia.base.OiaNodo n = new org.aspcfs.modules.oia.base.OiaNodo();
			

			Calendar calCorrente = GregorianCalendar.getInstance();
			Date dataCorrente = new Date(System.currentTimeMillis());
			int tolleranzaGiorni = Integer.parseInt(org.aspcfs.modules.vigilanza.blocchicu.ApplicationProperties.getProperty("TOLLERANZA_MODIFICA_CU"));
			dataCorrente.setDate(dataCorrente.getDate()- tolleranzaGiorni);
			calCorrente.setTime(new Timestamp(dataCorrente.getTime()));
			int anno_corrente = calCorrente.get(Calendar.YEAR);
			 System.out.println("Caricamento Strutture Per Conto Di In memoria Anno "+anno_corrente); 
			
			strutture_asl.put(asl, n.loadbyidAsl(idAsl,anno_corrente, db));
			context.getServletContext().setAttribute("StruttureOIA",strutture_asl);

			context.getRequest().setAttribute("idAsl", idAsl);
			context.getRequest().setAttribute("idArea", idStrutturaAreaSelezionata);
				
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		
		
		return executeCommandAddModify(context);
	}
	
	
	
	public String executeCommandGeneraXls(ActionContext context){
		Connection db = null;
		UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");
		int idAsl = -1;
		DpatStrumentoCalcolo dpatStrumentoCalcolo = null ;
		try {
			db = this.getConnection(context);
			idAsl = Integer.parseInt(context.getRequest().getParameter("idAsl"));
			Timestamp current_date = new Timestamp(System.currentTimeMillis());
			String anno=  context.getRequest().getParameter("anno") ;
			if (anno == null)
				anno= (String)context.getRequest().getAttribute("anno");

			
			String idArea = "" ;
			
			int idStrutturaAreaSelezionata = -1 ;
			
			String checkStrutture = context.getParameter("checkStrutture");
			if (context.getRequest().getAttribute("DpatSDC")!=null)
			{
				dpatStrumentoCalcolo = (DpatStrumentoCalcolo)context.getRequest().getAttribute("DpatSDC");
				idStrutturaAreaSelezionata=dpatStrumentoCalcolo.getIdStrutturaAreaSelezionata();
			}
			if (idStrutturaAreaSelezionata<=0 && context.getRequest().getParameter("combo_area")!=null)
			{
				idArea = context.getRequest().getParameter("combo_area");
				if (idArea!=null && !"".equals(idArea))
					idStrutturaAreaSelezionata = Integer.parseInt(idArea);
			}
			else
			{
				idStrutturaAreaSelezionata = (Integer)context.getRequest().getAttribute("idStrutturaAreaSelezionata");
			}
			
			
			if(checkStrutture!=null && "1".equals(checkStrutture))
			{
				idStrutturaAreaSelezionata= -1;
			}
			
			
			dpatStrumentoCalcolo = new DpatStrumentoCalcolo(db,idAsl,Integer.parseInt(anno),this.getSystemStatus(context),idStrutturaAreaSelezionata);

			LookupList qualifiche = new LookupList(db,"lookup_qualifiche");
			qualifiche.addItem(-1, "Seleziona Qualifica") ;
			context.getRequest().setAttribute("Qualifica", qualifiche);


			LookupList listaAsl = new LookupList(db,"lookup_site_id");
			context.getRequest().setAttribute("ListaAsl", listaAsl);
			
			
			LookupList lookupTipologia = new LookupList(db, "lookup_tipologia_nodo_oia");
			lookupTipologia.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("lookupTipologia", lookupTipologia);
			context.getRequest().setAttribute("DpatSDC", dpatStrumentoCalcolo);
			
			LookupList lookupTipologia2 = new LookupList();
			lookupTipologia2.setShowDisabledFlag(false);
			lookupTipologia2.setTableName("lookup_tipologia_nodo_oia");
			lookupTipologia2.buildListWithEnabled(db);
			
			context.getRequest().setAttribute("lookupTipologia2", lookupTipologia2);

			
			String sql = "select id from dpat where id_asl="+idAsl+" and anno="+anno;
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			int idDpat = -1;
			while (rs.next()){
				idDpat = rs.getInt("id");  
			}
			org.aspcfs.modules.dpat2019.base.Dpat d = new org.aspcfs.modules.dpat2019.base.Dpat();
			d.builRecordSdc(idDpat, db,this.getSystemStatus(context));
			context.getRequest().setAttribute("dpat", d);
			pst.close();
			
			String note = context.getParameter("note");
			
			int id = DatabaseUtils.getNextInt(db, "dpat_strumento_calcolo_storico_esportazioni", "id", 1);
			if(id==0)
				id=1 ;
			String insertStorico = "insert into dpat_strumento_calcolo_storico_esportazioni(id ,data ,estratto_da ,id_asl  ,descrizione_struttura ,id_struttura  ,note ,anno,tipo,check_tutte_le_strutture ) values (?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstStorico = db.prepareStatement(insertStorico);
			pstStorico.setInt(1, id);
			pstStorico.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			pstStorico.setInt(3, utente.getUserId());
			pstStorico.setInt(4, dpatStrumentoCalcolo.getIdAsl());
			pstStorico.setString(5, dpatStrumentoCalcolo.getStrutturaAmbito().getDescrizione_lunga());
			pstStorico.setInt(6, dpatStrumentoCalcolo.getStrutturaAmbito().getId());
			pstStorico.setString(8, note);
			pstStorico.setInt(7, dpatStrumentoCalcolo.getStrutturaAmbito().getAnno());
			pstStorico.setString(9, "Modello 4");
			if(checkStrutture!=null)
				pstStorico.setBoolean(10, true);
			else
				pstStorico.setBoolean(10, false);

			
			pstStorico.execute();
			
			GestioneDocumentiDPAT generazionePDF = new GestioneDocumentiDPAT();
			String codDocumento = generazionePDF.generaPdfDpat("DPAT_SDC",idAsl+"",anno,idArea,id,context);
			
			String update = "update dpat_strumento_calcolo_storico_esportazioni set cod_documento=? where id =?";
			pstStorico = db.prepareStatement(update);
			pstStorico.setString(1, codDocumento);
			pstStorico.setInt(2, id);
			pstStorico.execute();
//			GestioneDocumentidpat2019.do?command=GeneraPDF&combo_area='+idArea+'&idAsl='+idAsl+'&anno='+anno+'&tipo='+tipo
			
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet();
			HSSFRow row = sheet.createRow(0);
			
			HSSFCell cell = row.createCell(0);
			cell.setCellValue("STRUTTURA DI APPARTENENZA");
			
			HSSFCell cell10 = row.createCell(1);
			cell10.setCellValue("AMBITO");
			
			
			HSSFCell cel1 = row.createCell(2);
			cel1.setCellValue("NOMINATIVO");
			
			HSSFCell cel2 = row.createCell(3);
			cel2.setCellValue("QUALIFICA");
			
			int i = 1 ;
			
			for ( Object nodo : dpatStrumentoCalcolo.getListaStrutture())
			{
				OiaNodo struttura = (OiaNodo) nodo;
				DpatStrumentoCalcoloNominativiList listaPersone = struttura.getListaNominativi();
				Iterator<DpatStrumentoCalcoloNominativi> persone = listaPersone.iterator();
				
				while ( persone.hasNext())
				{
					
					DpatStrumentoCalcoloNominativi personaCurr = persone.next();
					HSSFRow row_ = sheet.createRow(i);
					HSSFCell cell_0 = row_.createCell(0);
					cell_0.setCellValue(struttura.getDescrizione_lunga());
					
					HSSFCell cell_10 = row_.createCell(1);
					cell_10.setCellValue(struttura.getDescrizioneAreaStruttureComplesse());
					
					HSSFCell cel_1 = row_.createCell(2);
					cel_1.setCellValue(personaCurr.getNominativo().getContact().getNameLast() +" " + personaCurr.getNominativo().getContact().getNameFirst());
					
					HSSFCell cel_2 = row_.createCell(3);
					cel_2.setCellValue(qualifiche.getSelectedValue(personaCurr.getIdLookupQualifica()).toUpperCase());
					i++;
				}
			}
			
			// write it as an excel attachment
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte [] outArray = outByteStream.toByteArray();
			context.getResponse().setContentType("application/vnd.ms-excel");
			context.getResponse().setContentLength(outArray.length);
			context.getResponse().setHeader("Expires:", "0"); // eliminates browser caching
			String nomeFile = "MODELLO_4";
			if(idStrutturaAreaSelezionata>0)
				nomeFile+="_"+dpatStrumentoCalcolo.getStrutturaAmbito().getDescrizione_lunga().replaceAll("\'", "").replaceAll(" ", "_");
			nomeFile+=".xls";
			context.getResponse().setHeader("Content-Disposition", "attachment; filename="+nomeFile);
			java.io.OutputStream outStream = context.getResponse().getOutputStream();
			outStream.write(outArray);
			outStream.flush();
			
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}	

		// SERVER DOCUMENTALE
		return "-none-";


	}
	
	public String executeCommandMessaggioVisualizza(ActionContext context) {
		
		 Connection db = null;
		 String messaggio = "";	
			try {
				db = this.getConnection(context);
				
				PreparedStatement pst = db.prepareStatement("select messaggio from messaggio_mod4 order by entered desc limit 1;");
				ResultSet rs = pst.executeQuery();
				if (rs.next())
					messaggio = rs.getString("messaggio");

				context.getRequest().setAttribute("messaggio", messaggio);
			} catch (Exception errorMessage) {
				context.getRequest().setAttribute("Error", errorMessage);
				return ("SystemError");
			} finally {
				this.freeConnection(context, db);
			}
			
			return "messaggioOK";
		}
	
	public String executeCommandMessaggioModifica(ActionContext context) {
		 
		 if (!hasPermission(context, "messaggio_mod4-add")) {
				return ("PermissionError");
			}
		 
			Connection db = null;
			String messaggio = "";
			try {
				db = this.getConnection(context);
				PreparedStatement pst = db.prepareStatement("select messaggio from messaggio_mod4 order by entered desc limit 1;");
				ResultSet rs = pst.executeQuery();
				if (rs.next())
					messaggio = rs.getString("messaggio");

				context.getRequest().setAttribute("messaggio", messaggio);
				
			} catch (Exception errorMessage) {
				context.getRequest().setAttribute("Error", errorMessage);
				return ("SystemError");
			} finally {
				this.freeConnection(context, db);
			}
			return "messaggioModificaOK";
		}
	
	 public String executeCommandMessaggioAggiorna(ActionContext context) {
		 
		 if (!hasPermission(context, "messaggio_mod4-add")) {
				return ("PermissionError");
			}
		 
			Connection db = null;

			String messaggio = context.getRequest().getParameter("messaggio");
			UserBean utente = (UserBean) context.getRequest().getSession().getAttribute("User");

			
			try {
				db = this.getConnection(context);
				PreparedStatement pst = db.prepareStatement("insert into messaggio_mod4(messaggio, id_utente) values (?, ?);");
				pst.setString(1,  messaggio);
				pst.setInt(2, utente.getUserId());
				pst.execute();
			} catch (Exception errorMessage) {
				context.getRequest().setAttribute("Error", errorMessage);
				return ("SystemError");
			} finally {
				this.freeConnection(context, db);
			}
			
			return executeCommandMessaggioVisualizza(context);
		}
}
