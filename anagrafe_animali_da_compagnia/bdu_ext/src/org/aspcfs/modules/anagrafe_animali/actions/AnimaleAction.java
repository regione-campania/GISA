/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.anagrafe_animali.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.log4j.Logger;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.DNA.base.ListaConvocazione;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.AnimaleList;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Furetto;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.anagrafe_animali.base.Leish;
import org.aspcfs.modules.anagrafe_animali.base.LeishList;
import org.aspcfs.modules.anagrafe_animali.base.RegistroUnico;
import org.aspcfs.modules.anagrafe_animali.base.RegistroUnicoList;
import org.aspcfs.modules.anagrafe_animali.gestione_modifiche.CampoModificato;
import org.aspcfs.modules.anagrafe_animali.gestione_modifiche.ModificaStatica;
import org.aspcfs.modules.anagrafe_animali.gestione_modifiche.ModificaStaticaList;
import org.aspcfs.modules.anagrafe_animali.base.Regresso;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.gestioneDocumenti.actions.GestioneAllegatiUpload;
import org.aspcfs.modules.gestioneDocumenti.actions.GestioneDocumenti;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.opu_ext.base.Circoscrizione;
import org.aspcfs.modules.partitecommerciali.base.PartitaCommerciale;
import org.aspcfs.modules.passaporti.base.Passaporto;
import org.aspcfs.modules.praticacontributi.base.Pratica;
import org.aspcfs.modules.praticacontributi.base.PraticaDWR;
import org.aspcfs.modules.praticacontributi.base.PraticaList;
import org.aspcfs.modules.registrazioniAnimali.base.Evento;
import org.aspcfs.modules.registrazioniAnimali.base.EventoAdozioneDaCanile;
import org.aspcfs.modules.registrazioniAnimali.base.EventoAdozioneDaColonia;
import org.aspcfs.modules.registrazioniAnimali.base.EventoAdozioneFuoriAsl;
import org.aspcfs.modules.registrazioniAnimali.base.EventoAggressione;
import org.aspcfs.modules.registrazioniAnimali.base.EventoAllontanamento;
import org.aspcfs.modules.registrazioniAnimali.base.EventoCattura;
import org.aspcfs.modules.registrazioniAnimali.base.EventoCessione;
import org.aspcfs.modules.registrazioniAnimali.base.EventoDecesso;
import org.aspcfs.modules.registrazioniAnimali.base.EventoEsitoControlli;
import org.aspcfs.modules.registrazioniAnimali.base.EventoFurto;
import org.aspcfs.modules.registrazioniAnimali.base.EventoInserimentoMicrochip;
import org.aspcfs.modules.registrazioniAnimali.base.EventoInserimentoVaccinazioni;
import org.aspcfs.modules.registrazioniAnimali.base.EventoList;
import org.aspcfs.modules.registrazioniAnimali.base.EventoMorsicatura;
import org.aspcfs.modules.registrazioniAnimali.base.EventoMutilazione;
import org.aspcfs.modules.registrazioniAnimali.base.EventoPrelievoDNA;
import org.aspcfs.modules.registrazioniAnimali.base.EventoPrelievoLeishmania;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRegistrazioneBDU;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRiconoscimentoPassaporto;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRilascioPassaporto;
import org.aspcfs.modules.registrazioniAnimali.base.EventoSmarrimento;
import org.aspcfs.modules.registrazioniAnimali.base.EventoSterilizzazione;
import org.aspcfs.modules.registrazioniAnimali.base.EventoTrasferimentoFuoriRegione;
import org.aspcfs.modules.registrazioniAnimali.base.RegistrazioniWKF;
import org.aspcfs.modules.richiestecontributi.actions.ContributiSterilizzazioni;
import org.aspcfs.modules.schedaAdozioneCani.base.SchedaAdozione;
import org.aspcfs.modules.schedaAdozioneCani.base.Valutazione;
import org.aspcfs.modules.schedaMorsicatura.base.SchedaMorsicatura;
import org.aspcfs.modules.schedaMorsicatura.base.SchedaMorsicaturaRecords;
import org.aspcfs.modules.sinaaf.Sinaaf;
import org.aspcfs.modules.ws.WsPost;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.CFSFileReader;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DbUtil;
import org.aspcfs.utils.DwrUtil;
import org.aspcfs.utils.EsitoControllo;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.MethodsUtils;
import org.aspcfs.utils.XLSUtils;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.postgresql.util.PSQLException;

import com.darkhorseventures.framework.actions.ActionContext;
import com.oreilly.servlet.MultipartRequest;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class AnimaleAction extends CFSModule {

	private final static Logger log = Logger.getLogger(org.aspcfs.modules.anagrafe_animali.actions.AnimaleAction.class);

	/*
	 * private static final int CANE = Cane.idSpecie; private static final int
	 * GATTO = Gatto.idSpecie; private static final int FURETTO =
	 * Furetto.idSpecie;
	 */

	public String executeCommandDefault(ActionContext context) {
		if (hasPermission(context, "anagrafe_canina-view"))
			return executeCommandSearchForm(context);
		else
			return executeCommandAdd(context);
	}

	public String executeCommandAdd(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina-anagrafe_canina-add")) {
			return ("PermissionError");
		}

		SystemStatus systemStatus = this.getSystemStatus(context);
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		Connection db = null;
		try {
			Thread t = Thread.currentThread();
			db = this.getConnection(context);

			int idSpecie = Cane.idSpecie;
			int idProprietario = -1;
			Animale thisAnimale = (Animale) context.getRequest().getAttribute("animale");

			if (thisAnimale != null) {
				idSpecie = thisAnimale.getIdSpecie();
			} else {
				if ((String) context.getRequest().getParameter("idSpecie") != null
						&& !("-1").equals((String) context.getRequest().getParameter("idSpecie"))) {
					idSpecie = new Integer((String) context.getRequest().getParameter("idSpecie")).intValue();
				}
				if ((String) context.getRequest().getParameter("idLinea") != null
						&& !("-1").equals((String) context.getRequest().getParameter("idLinea"))) {
					idProprietario = new Integer((String) context.getRequest().getParameter("idLinea"));
				}
			}

			// if ((String) context.getRequest().getParameter("microchip") !=
			// null
			// && !"".equals((String) context.getRequest()
			// .getParameter("microchip"))) {
			// //idAnimale = new Integer((String) context.getRequest()
			// // .getParameter("idAnimale")).intValue();
			// thisAnimale = new Animale();
			// thisAnimale.setMicrochip((String) context.getRequest()
			// .getParameter("microchip"));
			// }

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			LookupList regioniList = new LookupList(db, "lookup_regione");
			regioniList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			regioniList.removeElementByCode(15);
			context.getRequest().setAttribute("regioniList", regioniList);

			LookupList specieList = new LookupList();
			specieList.setTable("lookup_specie");
			specieList.buildList(db);
			// specieList.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("specieList", specieList);

			LookupList razzaList = new LookupList();
			razzaList.setTable("lookup_razza");
			razzaList.setIdSpecie(idSpecie);
			razzaList.buildList(db);
			razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("razzaList", razzaList);

			// Lookup esito
			LookupList esitoList = new LookupList(db, "lookup_esito_controlli");
			esitoList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("esitoList", esitoList);

			LookupList tagliaList = new LookupList(db, "lookup_taglia");
			tagliaList.addItemTaglia(-1, systemStatus.getLabel("calendar.none.4dashes"), systemStatus.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("tagliaList", tagliaList);

			LookupList mantelloList = new LookupList();
			mantelloList.setTable("lookup_mantello");
			mantelloList.setIdSpecie(idSpecie);
			mantelloList.buildList(db);
			mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("mantelloList", mantelloList);

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, 1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("comuniList", comuniList);
			
			ComuniAnagrafica c_all = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni_all = c.buildList(db, -1, -1);
			LookupList comuniList_all = new LookupList(listaComuni_all, -1);
			comuniList_all.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("comuniList_all", comuniList_all);

			LookupList nazioniList = new LookupList(db,	"lookup_nazioni");
			nazioniList.addItem(-1, "--Seleziona--");
			nazioniList.removeElementByCode(106);
			context.getRequest().setAttribute("nazioniList", nazioniList);
			
		
			LookupList provinceList = new LookupList(db, "lookup_province");
			provinceList.addItem(-1, "Seleziona provincia");
			context.getRequest().setAttribute("provinceList", provinceList);

			//LookupList veterinari = new LookupList(db, "elenco_veterinari_chippatori");// ,
																						// user.getSiteId(),
			LookupList veterinari = new LookupList(db, "elenco_veterinari_chippatori_with_asl_select_grouping");
			veterinari.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("veterinariList", veterinari);

			LookupList veterinariPrivati = new LookupList(db, "elenco_veterinari");
			veterinariPrivati.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("veterinariPrivatiList", veterinariPrivati);

			if(context.getRequest().getAttribute("ErrorBlocco")!=null && !((String)context.getRequest().getAttribute("ErrorBlocco")).equals(""))
				context.getRequest().setAttribute("ErrorBlocco", (String)context.getRequest().getAttribute("ErrorBlocco"));
			
			Operatore proprietario = new Operatore();
			if (idProprietario > 0)
				proprietario.queryRecordOperatorebyIdLineaProduttiva(db, idProprietario);

			switch (idSpecie) {

			case Cane.idSpecie: {

				Cane newCane = (Cane) context.getRequest().getAttribute("Cane");

				newCane.setIdSpecie(idSpecie);

				if (idProprietario > -1) {
					newCane.setIdProprietario(idProprietario);
					newCane.setIdDetentore(idProprietario);
					newCane.setProprietario(proprietario);
					newCane.setDetentore(proprietario);
				}

				if (((String) context.getSession().getAttribute("caller") != null
						&& !("").equals((String) context.getSession().getAttribute("caller")) && (ApplicationProperties
							.getProperty("VAM_ID").equals((String) context.getSession().getAttribute("caller"))))) {
					if ((String) context.getRequest().getParameter("microchip") != null
							&& !"".equals((String) context.getRequest().getParameter("microchip"))) {
						newCane.setMicrochip((String) context.getRequest().getParameter("microchip"));
					}
				}
				context.getRequest().setAttribute("Cane", newCane);
				break;
			}
			case Gatto.idSpecie: {
				Gatto newGatto = (Gatto) context.getRequest().getAttribute("Gatto");
				newGatto.setIdSpecie(idSpecie);

				if (idProprietario > -1) {
					newGatto.setIdProprietario(idProprietario);
					newGatto.setIdDetentore(idProprietario);
					newGatto.setProprietario(proprietario);
					newGatto.setDetentore(proprietario);
				}
				context.getRequest().setAttribute("Gatto", newGatto);
				break;
			}
			case Furetto.idSpecie: {
				Furetto newFuretto = (Furetto) context.getRequest().getAttribute("Furetto");
				newFuretto.setIdSpecie(idSpecie);

				if (idProprietario > -1) {
					newFuretto.setIdProprietario(idProprietario);
					newFuretto.setIdDetentore(idProprietario);
					newFuretto.setProprietario(proprietario);
					newFuretto.setDetentore(proprietario);
				}
				context.getRequest().setAttribute("Furetto", newFuretto);
				break;
			}
			default:
				break;
			}

			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		String opId = context.getRequest().getParameter("idLinea");
		String origine = context.getRequest().getParameter("origine");
		if (opId != null && !opId.equals("")) {
			context.getRequest().setAttribute("opId", opId);
			context.getRequest().setAttribute("origine", origine);
		}
		
		context.getRequest().setAttribute("dataOggi",(String) context.getSession().getAttribute("caller"));
		
		
		/**************** PARAMETRI EVENTUALE ORIGINE ANIMALE ***************/
		context.getRequest().setAttribute("origine_da",(String) context.getRequest().getParameter("origine_da"));
		context.getRequest().setAttribute("tipo_origine",(String) context.getRequest().getParameter("tipo_origine"));
				
		context.getRequest().setAttribute("regione_ritrovamento",(String) context.getRequest().getParameter("regione_ritrovamento"));
		context.getRequest().setAttribute("provincia_ritrovamento",(String) context.getRequest().getParameter("provincia_ritrovamento"));
		context.getRequest().setAttribute("comune_ritrovamento",(String) context.getRequest().getParameter("comune_ritrovamento"));
		context.getRequest().setAttribute("indirizzo_ritrovamento",(String) context.getRequest().getParameter("indirizzo_ritrovamento"));
		context.getRequest().setAttribute("data_ritrovamento",(String) context.getRequest().getParameter("data_ritrovamento"));

		context.getRequest().setAttribute("flagFuoriRegione",(String) context.getRequest().getParameter("flagFuoriRegione"));
		context.getRequest().setAttribute("idRegioneProvenienza",(String) context.getRequest().getParameter("idRegioneProvenienza"));
		context.getRequest().setAttribute("noteAnagrafeFr",(String) context.getRequest().getParameter("noteAnagrafeFr"));

		context.getRequest().setAttribute("flagSenzaOrigine",(String) context.getRequest().getParameter("flagSenzaOrigine"));
		
		context.getRequest().setAttribute("idNazioneProvenienza",(String) context.getRequest().getParameter("idNazioneProvenienza"));
		context.getRequest().setAttribute("noteNazioneProvenienza",(String) context.getRequest().getParameter("noteNazioneProvenienza"));
		
		context.getRequest().setAttribute("flagAcquistoOnline",(String) context.getRequest().getParameter("flagAcquistoOnline"));
		context.getRequest().setAttribute("sitoWebAcquisto",(String) context.getRequest().getParameter("sitoWebAcquisto"));
		context.getRequest().setAttribute("noteAcquistoOnline",(String) context.getRequest().getParameter("noteAcquistoOnline"));
		/********************************************************************/


		addModuleBean(context, "Add Account", "Accounts Add");
		context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));
		// if a different module reuses this action then do a explicit return
		if (context.getRequest().getParameter("actionSource") != null) {
			return getReturn(context, "AddAccount");
		}

		if ((String) context.getSession().getAttribute("caller") != null
				&& !("").equals((String) context.getSession().getAttribute("caller"))
				&& (ApplicationProperties.getProperty("VAM_ID").equals((String) context.getSession().getAttribute(
						"caller")))) {

			return ("AddNoNAVOK");
		}
		
		context.getRequest().setAttribute("dataOggi", new Date());

		String currentDate = getCurrentDateAsString(context);
		context.getRequest().setAttribute("currentDate", currentDate);
		
		return getReturn(context, "Add");
	}

	public String executeCommandInsert(ActionContext context) throws SQLException {

		if (!hasPermission(context, "anagrafe_canina-anagrafe_canina-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		PreparedStatement pst = null;
		boolean recordInserted = false;
		boolean isValid = false;
		Animale insertedAnimale = null;

		boolean contributoDisponibile = false;// se l'animale è associabile alla
		// pratica contributi
		// selezionata
		boolean contributoRichiesto = false;

		boolean catturato = false;
		EventoCattura cattura = null;

		// Integer orgId = null;
		Animale newAnimale = (Animale) context.getRequest().getAttribute("Animale");
		Cane newCane = (Cane) context.getRequest().getAttribute("Cane");
		Gatto newGatto = (Gatto) context.getRequest().getAttribute("Gatto");
		Furetto newFuretto = (Furetto) context.getRequest().getAttribute("Furetto");

		Animale thisAnimale = null;

		int id_pratica = -1;

		contributoRichiesto = false;
		Pratica praticaContributi = null;
		boolean praticaSceltaOK = true;

		try {

			EsitoControllo esitoTatu = null;
			EsitoControllo esitoMc = null;

			UserBean user = (UserBean) context.getSession().getAttribute("User");
			String ip = user.getUserRecord().getIp();

			newAnimale.setIdUtenteInserimento(user.getUserId());

			if (newAnimale.getMicrochip() != null && !("").equals(newAnimale.getMicrochip())) {
				esitoMc = DwrUtil.verificaInserimentoAnimale(newAnimale.getMicrochip(), user.getUserId());
			}

			if (newAnimale.getTatuaggio() != null && !("").equals(newAnimale.getTatuaggio())) {
				esitoTatu = DwrUtil.verificaInserimentoAnimale(newAnimale.getTatuaggio(), user.getUserId());
			}

			db = this.getConnection(context);

			if (newAnimale.getIdSpecie() != Furetto.idSpecie
					&& (context.getRequest().getParameter("flagCattura") == null
							|| ("").equals(context.getRequest().getParameter("flagCattura")) || !("on").equals(context
							.getRequest().getParameter("flagCattura")))) {
				newCane.setFlagCattura(false);
				newCane.setIdComuneCattura(-1);
				newCane.setLuogoCattura("");
				newCane.setVerbaleCattura("");
				newCane.setDataCattura("");
			}

			switch (newAnimale.getIdSpecie()) {
			case Cane.idSpecie: {
				thisAnimale = (Animale) newCane;
				break;
			}
			case Gatto.idSpecie: {
				thisAnimale = (Animale) newGatto;
				break;
			}
			case Furetto.idSpecie: {
				thisAnimale = (Animale) newFuretto;
				break;
			}
			}

			if (context.getRequest().getParameter("idProprietario") != null
					&& !context.getRequest().getParameter("idProprietario").equals("")
					&& !context.getRequest().getParameter("idProprietario").equals("-1")) {

				Operatore soggettoAdded = new Operatore();
				soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, new Integer(context.getRequest()
						.getParameter("idProprietario")).intValue());

				thisAnimale.setProprietario(soggettoAdded);

			}

			
			
			if (context.getRequest().getParameter("idProprietarioProvenienza") != null
				&& !context.getRequest().getParameter("idProprietarioProvenienza").equals("-1")
				//&& context.getRequest().getParameter("nomeProprietarioProvenienza")!= null
				//&& !context.getRequest().getParameter("nomeProprietarioProvenienza").equals("")
				){
					Operatore soggettoProvenienzaAdded = new Operatore();
					soggettoProvenienzaAdded.queryRecordOperatorebyIdLineaProduttiva(db, new Integer(context.getRequest()
					.getParameter("idProprietarioProvenienza")).intValue());

					thisAnimale.setProprietarioProvenienza(soggettoProvenienzaAdded);
			}
			
			/**************** PARAMETRI EVENTUALE ORIGINE ANIMALE ***************/
			context.getRequest().setAttribute("origine_da",(String) context.getRequest().getParameter("origine_da"));
			context.getRequest().setAttribute("tipo_origine",(String) context.getRequest().getParameter("tipo_origine"));
					
			context.getRequest().setAttribute("regione_ritrovamento",(String) context.getRequest().getParameter("regione_ritrovamento"));
			context.getRequest().setAttribute("provincia_ritrovamento",(String) context.getRequest().getParameter("provincia_ritrovamento"));
			context.getRequest().setAttribute("comune_ritrovamento",(String) context.getRequest().getParameter("comune_ritrovamento"));
			context.getRequest().setAttribute("indirizzo_ritrovamento",(String) context.getRequest().getParameter("indirizzo_ritrovamento"));
			context.getRequest().setAttribute("data_ritrovamento",(String) context.getRequest().getParameter("data_ritrovamento"));

			context.getRequest().setAttribute("flagSenzaOrigine",(String) context.getRequest().getParameter("flagSenzaOrigine"));
			
			context.getRequest().setAttribute("flagFuoriRegione",(String) context.getRequest().getParameter("flagFuoriRegione"));
			context.getRequest().setAttribute("idRegioneProvenienza",(String) context.getRequest().getParameter("idRegioneProvenienza"));
			context.getRequest().setAttribute("noteAnagrafeFr",(String) context.getRequest().getParameter("noteAnagrafeFr"));

			
			context.getRequest().setAttribute("idNazioneProvenienza",(String) context.getRequest().getParameter("idNazioneProvenienza"));
			context.getRequest().setAttribute("noteNazioneProvenienza",(String) context.getRequest().getParameter("noteNazioneProvenienza"));
			
			context.getRequest().setAttribute("flagAcquistoOnline",(String) context.getRequest().getParameter("flagAcquistoOnline"));
			context.getRequest().setAttribute("sitoWebAcquisto",(String) context.getRequest().getParameter("sitoWebAcquisto"));
			context.getRequest().setAttribute("noteAcquistoOnline",(String) context.getRequest().getParameter("noteAcquistoOnline"));
			/********************************************************************/

			switch (newAnimale.getIdSpecie()) {
			case Cane.idSpecie: {
				// thisAnimale = (Animale)newCane;
				/****
				 * MODIFICA Se il proprietario è diverso da "Canile", allora il
				 * detentore dovrà essere uguale al proprietario
				 * ***/
				int idTipologiaOperatore = 0;
				if (thisAnimale.getProprietario().getIdOperatore() != 0)
					idTipologiaOperatore = ((LineaProduttiva) ((Stabilimento) thisAnimale.getProprietario()
							.getListaStabilimenti().get(0)).getListaLineeProduttive().get(0)).getIdRelazioneAttivita();

				if (context.getRequest().getParameter("idDetentore") != null
						&& !context.getRequest().getParameter("idDetentore").equals("")
						&& !context.getRequest().getParameter("idDetentore").equals("-1")) {
					Operatore soggettoAdded = new Operatore();

					soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, new Integer(context.getRequest()
							.getParameter("idDetentore")).intValue());
					int idTipologiaDetentore = ((LineaProduttiva) ((Stabilimento) soggettoAdded.getListaStabilimenti()
							.get(0)).getListaLineeProduttive().get(0)).getIdRelazioneAttivita();

					if (idTipologiaOperatore != 5 && idTipologiaDetentore != 5) {
						newCane.setDetentore(thisAnimale.getProprietario());
						newCane.setIdDetentore(new Integer(context.getRequest().getParameter("idProprietario"))
								.intValue());
					} else {
						newCane.setDetentore(soggettoAdded);
					}

				} else if (thisAnimale.getProprietario() != null) {
					newCane.setDetentore(thisAnimale.getProprietario());
					newCane.setIdDetentore(new Integer(context.getRequest().getParameter("idProprietario")).intValue());
				}
				/*************/

				if (context.getRequest().getParameter("flagSindacoFuoriRegione") != null
						&& !("").equals(context.getRequest().getParameter("flagSindacoFuoriRegione"))
						&& ("on").equals(context.getRequest().getParameter("flagSindacoFuoriRegione"))) {

					// Sindaco fuori regione, detentore canile in regione, l'asl
					// diventa quella del detentore
					Stabilimento stabDet = (Stabilimento) newCane.getDetentore().getListaStabilimenti().get(0);
					newCane.setIdAslRiferimento(stabDet.getIdAsl());

				}

				if (context.getRequest().getParameter("idVeterinarioMicrochip") != null
						&& !context.getRequest().getParameter("idVeterinarioMicrochip").equals("")
						&& !("-1").equals(context.getRequest().getParameter("idVeterinarioMicrochip"))) {

					Operatore soggettoAdded = new Operatore();

					soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, new Integer(context.getRequest()
							.getParameter("idVeterinarioMicrochip")).intValue());

					newCane.setIdVeterinarioMicrochip(new Integer((String) context.getRequest().getParameter(
							"idVeterinarioMicrochip")));
					// newCane.setVeterinarioChip(soggettoAdded);
				}

				break;

			}
			case Gatto.idSpecie: {
				// thisAnimale = (Animale)newGatto;
				int idTipologiaOperatore = 0;
				if (thisAnimale.getProprietario().getIdOperatore() != 0)
					idTipologiaOperatore = ((LineaProduttiva) ((Stabilimento) thisAnimale.getProprietario()
							.getListaStabilimenti().get(0)).getListaLineeProduttive().get(0)).getIdRelazioneAttivita();

				if (context.getRequest().getParameter("idDetentore") != null
						&& !context.getRequest().getParameter("idDetentore").equals("")
						&& !context.getRequest().getParameter("idDetentore").equals("-1")) {
					Operatore soggettoAdded = new Operatore();
					soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, new Integer(context.getRequest()
							.getParameter("idDetentore")).intValue());
					int idTipologiaDetentore = ((LineaProduttiva) ((Stabilimento) soggettoAdded.getListaStabilimenti()
							.get(0)).getListaLineeProduttive().get(0)).getIdRelazioneAttivita();

					if (idTipologiaOperatore != 5 && idTipologiaDetentore != 5) {
						newGatto.setDetentore(thisAnimale.getProprietario());
						newGatto.setIdDetentore(new Integer(context.getRequest().getParameter("idProprietario"))
								.intValue());
					} else {
						newGatto.setDetentore(soggettoAdded);
					}

				} else if (thisAnimale.getProprietario() != null) {
					newGatto.setDetentore(thisAnimale.getProprietario());
					newGatto.setIdDetentore(new Integer(context.getRequest().getParameter("idProprietario")).intValue());
				}

				if (context.getRequest().getParameter("flagSindacoFuoriRegione") != null
						&& !("").equals(context.getRequest().getParameter("flagSindacoFuoriRegione"))
						&& ("on").equals(context.getRequest().getParameter("flagSindacoFuoriRegione"))) {

					// Sindaco fuori regione, detentore canile in regione, l'asl
					// diventa quella del detentore
					Stabilimento stabDet = (Stabilimento) newGatto.getDetentore().getListaStabilimenti().get(0);
					newGatto.setIdAslRiferimento(stabDet.getIdAsl());

				}

				break;

			}
			case Furetto.idSpecie: {
				// thisAnimale = (Animale)newGatto;int idTipologiaOperatore=0;
				int idTipologiaOperatore = 0;
				if (thisAnimale.getProprietario().getIdOperatore() != 0)
					idTipologiaOperatore = ((LineaProduttiva) ((Stabilimento) thisAnimale.getProprietario()
							.getListaStabilimenti().get(0)).getListaLineeProduttive().get(0)).getIdRelazioneAttivita();

				if (context.getRequest().getParameter("idDetentore") != null
						&& !context.getRequest().getParameter("idDetentore").equals("")
						&& !context.getRequest().getParameter("idDetentore").equals("-1")) {
					Operatore soggettoAdded = new Operatore();
					soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, new Integer(context.getRequest()
							.getParameter("idDetentore")).intValue());
					int idTipologiaDetentore = ((LineaProduttiva) ((Stabilimento) soggettoAdded.getListaStabilimenti()
							.get(0)).getListaLineeProduttive().get(0)).getIdRelazioneAttivita();

					if (idTipologiaOperatore != 5 && idTipologiaDetentore != 5) {
						newFuretto.setDetentore(thisAnimale.getProprietario());
						newFuretto.setIdDetentore(new Integer(context.getRequest().getParameter("idProprietario"))
								.intValue());
					} else {
						newFuretto.setDetentore(soggettoAdded);
					}

				} else if (thisAnimale.getProprietario() != null) {
					newFuretto.setDetentore(thisAnimale.getProprietario());
					newFuretto.setIdDetentore(new Integer(context.getRequest().getParameter("idProprietario"))
							.intValue());
				}

				if (context.getRequest().getParameter("flagSindacoFuoriRegione") != null
						&& !("").equals(context.getRequest().getParameter("flagSindacoFuoriRegione"))
						&& ("on").equals(context.getRequest().getParameter("flagSindacoFuoriRegione"))) {

					// Sindaco fuori regione, detentore canile in regione, l'asl
					// diventa quella del detentore
					Stabilimento stabDet = (Stabilimento) newFuretto.getDetentore().getListaStabilimenti().get(0);
					newFuretto.setIdAslRiferimento(stabDet.getIdAsl());

				}

				break;

			}
			}
			EventoRegistrazioneBDU reg_bdu_ = (EventoRegistrazioneBDU) context.getRequest().getAttribute(
					"EventoRegistrazioneBDU");
			
			
			// CONTROLLO SE NELLA DATA DI AGGIUNTA ANIMALE IL CANILE E' BLOCCATO
			if (context.getRequest().getParameter("idDetentore") != null
					&& !context.getRequest().getParameter("idDetentore").equals("")
					&& !context.getRequest().getParameter("idDetentore").equals("-1")) {
				int id_det=new Integer(context.getRequest().getParameter("idDetentore")).intValue();
				Operatore soggettoAdded = new Operatore();
				soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, id_det);
				int idTipologiaDetentore = ((LineaProduttiva) ((Stabilimento) soggettoAdded.getListaStabilimenti()
						.get(0)).getListaLineeProduttive().get(0)).getIdRelazioneAttivita();
				String esito="";
				if(idTipologiaDetentore==5 && reg_bdu_.getDataRegistrazione()!=null){
					esito=soggettoAdded.controlloRegistrazioneInCanileBloccato(id_det, db, reg_bdu_.getDataRegistrazione().toString());
				}
					
				if(!esito.equals("")){
					context.getRequest().setAttribute("Cane", newCane);
					context.getRequest().setAttribute("Gatto", newGatto);
					context.getRequest().setAttribute("Furetto", newFuretto);
					context.getRequest().setAttribute("animale", newAnimale);
					context.getRequest().setAttribute("ErrorBlocco", esito);
					return executeCommandAdd(context);
				}

			
			}


			if (context.getRequest().getParameter("doContinue") != null
					&& !context.getRequest().getParameter("doContinue").equals("")
					&& context.getRequest().getParameter("doContinue").equals("false")) {

				context.getRequest().setAttribute("Cane", newCane);
				context.getRequest().setAttribute("Gatto", newGatto);
				context.getRequest().setAttribute("Furetto", newFuretto);
				context.getRequest().setAttribute("animale", newAnimale);

				return executeCommandAdd(context);
			}

			if (esitoMc == null
					|| (esitoMc.getIdEsito() == 2
							|| (esitoMc.getIdEsito() == 4 && (user.getRoleId() != new Integer(
									ApplicationProperties.getProperty("ID_RUOLO_HD1"))
									&& user.getRoleId() != new Integer(
											ApplicationProperties.getProperty("ID_RUOLO_HD2"))
									&& user.getRoleId() != new Integer(
											ApplicationProperties.getProperty("ID_RUOLO_AMMINISTRATORE_ASL"))
									&& user.getRoleId() != new Integer(
											ApplicationProperties.getProperty("ID_RUOLO_REFERENTE_ASL")) && user
									.getRoleId() != new Integer(
									ApplicationProperties.getProperty("ID_RUOLO_ANAGRAFE_CANINA")))) || (esitoMc
							.getIdEsito() == 3 && user.getRoleId() == 24))) {
				context.getRequest().setAttribute("Cane", newCane);
				context.getRequest().setAttribute("Gatto", newGatto);
				context.getRequest().setAttribute("Furetto", newFuretto);
				context.getRequest().setAttribute("ErroreMicrochip", esitoMc.getDescrizione());
				context.getRequest().setAttribute("animale", newAnimale);

				return executeCommandAdd(context);

			}

			if (esitoTatu != null
					&& (esitoTatu.getIdEsito() == 2 || esitoTatu.getIdEsito() == 4 || (esitoTatu.getIdEsito() == 3 && user
							.getRoleId() == 24))) {
				context.getRequest().setAttribute("Cane", newCane);
				context.getRequest().setAttribute("Gatto", newGatto);
				context.getRequest().setAttribute("Furetto", newFuretto);
				context.getRequest().setAttribute("ErroreTatuaggio", esitoTatu.getDescrizione());
				context.getRequest().setAttribute("animale", newAnimale);

				return executeCommandAdd(context);

			}

			/**
			 * CONTROLLI PRATICA CONTRIBUTI
			 */
			if (context.getRequest().getParameter("flagSterilizzazione") != null
					&& !("").equals(context.getRequest().getParameter("flagSterilizzazione"))
					&& ("on").equals(context.getRequest().getParameter("flagSterilizzazione"))) {

				EventoSterilizzazione sterilizzazione = (EventoSterilizzazione) context.getRequest().getAttribute(
						"EventoSterilizzazione");

				if (sterilizzazione.isFlagContributoRegionale()) {
					contributoRichiesto = true;
				}

				// Se è catturato
				/*
				 * if (context.getRequest().getParameter("flagCattura") != null
				 * && !("").equals(context.getRequest().getParameter(
				 * "flagCattura")) &&
				 * ("on").equals(context.getRequest().getParameter(
				 * "flagCattura"))) { catturato = true; }
				 */

				if (thisAnimale.isRandagio(db)) {
					catturato = true;
				}

				if (contributoRichiesto) {
					contributoDisponibile = Animale.checkContributo(db, thisAnimale.getMicrochip());

					if (contributoDisponibile) {
						if (context.getRequest().getParameter("idProgettoSterilizzazioneRichiesto") != null) {
							id_pratica = Integer.valueOf(context.getRequest().getParameter(
									"idProgettoSterilizzazioneRichiesto"));
							// thisAsset.setId_pratica_contributi(id_pratica);
							praticaContributi = new Pratica(db, id_pratica);

							if ((catturato)) {
								// int
								// trovato=praticaContributi.controlli_pratica(db,
								// id_pratica,
								// thisAsset.getComuneCattura(),getUserAsl(context));
								if (thisAnimale.getIdSpecie() == Cane.idSpecie
										&& praticaContributi.getCaniRestantiCatturati() <= 0) {
									praticaSceltaOK = false;
									context.getRequest()
											.setAttribute("praticaError",
													"Per il progetto scelto non ci sono posti disponibili per i cani catturati");
								} else if (thisAnimale.getIdSpecie() == Gatto.idSpecie
										&& praticaContributi.getGattiRestantiCatturati() <= 0) {
									praticaSceltaOK = false;
									context.getRequest()
											.setAttribute("praticaError",
													"Per il progetto scelto non ci sono posti disponibili per i gatti catturati");
								} else if ((String) context.getRequest().getParameter("idComuneCattura") != null
										&& !praticaContributi.getComuniElenco().contains(
												new Integer((String) context.getRequest().getParameter(
														"idComuneCattura")).intValue())) {
									Stabilimento stab = (Stabilimento) thisAnimale.getDetentore()
											.getListaStabilimenti().get(0);
									LineaProduttiva lineaP = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
									if (!praticaContributi.getCaniliElenco().contains(lineaP.getId())) {

										praticaSceltaOK = false;
										context.getRequest().setAttribute("praticaError",
												"Il comune di cattura non corrisponde con quelli del progetto");
									}

								}
							} else {
								// recupero il comune del proprietario
								Stabilimento stab = (Stabilimento) thisAnimale.getProprietario().getListaStabilimenti()
										.get(0);
								LineaProduttiva lineaP = (LineaProduttiva) stab.getListaLineeProduttive().get(0);

								int id_comune_proprietario = stab.getSedeOperativa().getComune();
								// String comune_proprietario=
								// thisAsset.recupero_comune(db,
								// thisAsset.getOrgId());
								// int
								// trovato=praticaContributi.controlli_pratica(db,
								// id_pratica,
								// comune_proprietario,getUserAsl(context));
								// Prendo il detentore. Devo distinguere se sto
								// aggiungendo un cane o un gatto
								if (thisAnimale.getIdSpecie() == Cane.idSpecie) {
									stab = (Stabilimento) newCane.getDetentore().getListaStabilimenti().get(0);
								} else if (thisAnimale.getIdSpecie() == Gatto.idSpecie) {
									stab = (Stabilimento) newGatto.getDetentore().getListaStabilimenti().get(0);
								} else if (thisAnimale.getIdSpecie() == Furetto.idSpecie) {
									stab = (Stabilimento) newFuretto.getDetentore().getListaStabilimenti().get(0);
								}
								lineaP = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
								if (thisAnimale.getIdSpecie() == Cane.idSpecie
										&& praticaContributi.getCaniRestantiPadronali() <= 0) {
									praticaSceltaOK = false;
									context.getRequest()
											.setAttribute("praticaError",
													"Per il progetto scelto non ci sono posti disponibili per i cani padronali");
								} else if (thisAnimale.getIdSpecie() == Gatto.idSpecie
										&& praticaContributi.getGattiRestantiPadronali() <= 0) {
									praticaSceltaOK = false;
									context.getRequest()
											.setAttribute("praticaError",
													"Per il progetto scelto non ci sono posti disponibili per i gatti padronali");
								} else if (!praticaContributi.getComuniElenco().contains(id_comune_proprietario)
										&& !praticaContributi.getCaniliElenco().contains(lineaP.getId())) {
									praticaSceltaOK = false;
									context.getRequest().setAttribute("praticaError",
											"Il comune del proprietario non corrisponde con quelli del progetto");
								}
							}
						}
					}
				} else {
					// se il contributo non è stato richiesto setto a true la
					// disponibilità del contributo per bypassare successivi
					// controlli
					contributoDisponibile = true;
				}

				if (!praticaSceltaOK) {
					context.getRequest().setAttribute("animale", newAnimale);
					return executeCommandAdd(context);
				}

			}

			isValid = this.validateObject(context, db, thisAnimale);

			if (isValid) {


				thisAnimale.setIdUtenteInserimento(user.getUserId());

				if (thisAnimale.isFlagAttivitaItinerante()) {
					thisAnimale.setDataAttivitaItinerante(thisAnimale.getDataMicrochip());
				}
				
				
				if(context.getRequest().getParameter("flagSenzaOrigine")!=null && ((String)context.getRequest().getParameter("flagSenzaOrigine")).equals("on")){
					thisAnimale.setFlagMancataOrigine(true);
					
				}  

				int idAnimale = DatabaseUtils.getNextSeqPostgres(db, "animale_id_seq");
				
				if(thisAnimale.isFlagMancataOrigine()){
					GestioneDocumenti g = new GestioneDocumenti();
					g.aggiornaIdAnimaleMicrochip(idAnimale,thisAnimale.getMicrochip(),context);
				}
				
				thisAnimale.setIdAnimale(idAnimale);
				recordInserted = thisAnimale.insert(db);
					
				if (contributoRichiesto && praticaSceltaOK) {
					if (catturato) {
						praticaContributi.aggiornaCatturati(db, id_pratica, thisAnimale.getIdSpecie());
					} else {
						praticaContributi.aggiornaPadronali(db, id_pratica, thisAnimale.getIdSpecie());
					}
				}
				
				if (context.getRequest().getParameter("idProgettoSterilizzazioneRichiesto") != null) 
				{
					if(praticaContributi==null)
					{
						id_pratica = Integer.valueOf(context.getRequest().getParameter("idProgettoSterilizzazioneRichiesto"));
						praticaContributi = new Pratica(db, id_pratica);
					}
					if(praticaContributi.getIdTipologiaPratica()==3)
						Pratica.aggiornaMaschiFemmina(db, praticaContributi.getId(), thisAnimale.getSesso(), 1);
				}

				if (thisAnimale.getIdSpecie() == Gatto.idSpecie) {

					// Aggiorno gatti appartenenti a colonia
					int idRelazioneDetentore = ((LineaProduttiva) ((Stabilimento) newGatto.getDetentore()
							.getListaStabilimenti().get(0)).getListaLineeProduttive().get(0)).getIdRelazioneAttivita();
					int idRelazioneProprietario = ((LineaProduttiva) ((Stabilimento) newGatto.getProprietario()
							.getListaStabilimenti().get(0)).getListaLineeProduttive().get(0)).getIdRelazioneAttivita();

				}

				if ((esitoMc.getIdEsito() == 1 || esitoMc.getIdEsito() == 4) && recordInserted == true) {
					pst = db.prepareStatement("update microchips set id_animale =? ,id_specie = ? where microchip =? ");
					pst.setInt(1, thisAnimale.getIdAnimale());
					pst.setInt(2, thisAnimale.getIdSpecie());
					pst.setString(3, thisAnimale.getMicrochip());
					pst.execute();
				}
				if (esitoTatu != null && (esitoTatu.getIdEsito() == 1 || esitoTatu.getIdEsito() == 4)
						&& recordInserted == true) {
					pst = db.prepareStatement("update microchips set id_animale =? ,flag_secondo_microchip = true,id_specie = ? where microchip =? ");
					pst.setInt(1, thisAnimale.getIdAnimale());
					pst.setInt(2, thisAnimale.getIdSpecie());
					pst.setString(3, thisAnimale.getTatuaggio());
					pst.execute();
				}

				int tipologiaRegistrazione = 0;
				// Registrazioni

				// Registrazione di inserimento

				EventoRegistrazioneBDU reg_bdu = (EventoRegistrazioneBDU) context.getRequest().getAttribute(
						"EventoRegistrazioneBDU");

				// Caso vet privati o ruolo anagrafecanina
				if ((user.getSiteId() > 0 && user.getSiteId() != thisAnimale.getIdAslRiferimento())) {
					reg_bdu.setIdAslRiferimento(user.getSiteId());
				} else if (user.getSiteId() < 0) {
					reg_bdu.setIdAslRiferimento(thisAnimale.getIdAslRiferimento());
				}

				if (reg_bdu.isFlagAttivitaItinerante()) {
					reg_bdu.setDataAttivitaItinerante(thisAnimale.getDataMicrochip());
				}
				reg_bdu.setEnteredby(this.getUserId(context));
				reg_bdu.setIdTipologiaEvento(reg_bdu.idTipologiaDB);
				reg_bdu.setIdAnimale(thisAnimale.getIdAnimale());
				reg_bdu.setSpecieAnimaleId(thisAnimale.getIdSpecie());
				reg_bdu.setIdProprietarioCorrente(thisAnimale.getIdProprietario());
				reg_bdu.setIdDetentoreCorrente(thisAnimale.getIdDetentore());
		
				
				// CONTROLLO SELEZIONE ORIGINE ANIMALE
				//String anagrafe_fuori_nazione=(String)context.getRequest().getParameter("flagFuoriNazione");
				String anagrafe_fuori_regione=(String)context.getRequest().getParameter("flagFuoriRegione");
				String acquisto_online=(String)context.getRequest().getParameter("flagAcquistoOnline");
				String origine=(String)context.getRequest().getParameter("origine_da");

				
				if(origine!=null){
					if(origine.equals("nazione_estera")){
						reg_bdu.setFlag_anagrafe_estera(true);  
						reg_bdu.setNazione_estera((String)context.getRequest().getParameter("idNazioneProvenienza"));
						reg_bdu.setNazione_estera_note((String)context.getRequest().getParameter("noteNazioneProvenienza"));
					}else{
						reg_bdu.setProvenienza_origine(origine);
						String tipo_origine=(String)context.getRequest().getParameter("tipo_origine");
						if(tipo_origine!=null){
							if(tipo_origine.equals("soggetto_fisico")){
								if (context.getRequest().getParameter("idProprietarioProvenienza") != null	&& !context.getRequest().getParameter("idProprietarioProvenienza").equals("-1"))
									reg_bdu.setIdProprietarioProvenienza(Integer.parseInt((String)context.getRequest().getParameter("idProprietarioProvenienza")));
								}else{
									reg_bdu.setRegione_ritrovamento(context.getRequest().getParameter("regione_ritrovamento"));
									reg_bdu.setProvincia_ritrovamento(context.getRequest().getParameter("provincia_ritrovamento"));
									reg_bdu.setComune_ritrovamento(context.getRequest().getParameter("comune_ritrovamento"));
									reg_bdu.setIndirizzo_ritrovamento(context.getRequest().getParameter("indirizzo_ritrovamento"));
									reg_bdu.setData_ritrovamento(context.getRequest().getParameter("data_ritrovamento"));
								}						
						}
					}
				}
				
				if (anagrafe_fuori_regione != null && anagrafe_fuori_regione.equals("on")){
					reg_bdu.setFlag_anagrafe_fr(true);  
					reg_bdu.setRegione_anagrafe_fr((String)context.getRequest().getParameter("idRegioneProvenienza"));
					reg_bdu.setRegione_anagrafe_fr_note((String)context.getRequest().getParameter("noteAnagrafeFr"));
				}		
				
				if (acquisto_online != null && acquisto_online.equals("on")){
					reg_bdu.setFlag_acquisto_online(true);  
					reg_bdu.setSito_web_acquisto((String)context.getRequest().getParameter("sitoWebAcquisto"));
					reg_bdu.setSito_web_acquisto_note((String)context.getRequest().getParameter("noteAcquistoOnline"));
				}


				reg_bdu.insert(db);

				/**
				 * SE IL RUOLO E' VET PRIVATO, PUo' ANAGRAFARE ANIMALI A
				 * PROPRIETARI / DETENTORI FUORI REGIONE, NEL QUAL CASO IL CANE
				 * DEVE RISULTARE DIRETTAMENTE UN FUORI REGIONE, ESSERE INVIATO
				 * COMUNQUE NELLE ESTRAZIONI ALLA BDN E SU DI ESSO NON DEVE
				 * ESSERE POSSIBILE INSERIRE ULTERIORI REGISTRAZIONI
				 */
				Operatore proprietario = thisAnimale.getProprietario();
				Operatore detentore = thisAnimale.getDetentore();
				Stabilimento stabPropr = (Stabilimento) proprietario.getListaStabilimenti().get(0);
				Stabilimento stabDet = (Stabilimento) detentore.getListaStabilimenti().get(0);

				if (stabPropr.getIdAsl() == new Integer(ApplicationProperties.getProperty("ID_ASL_FUORI_REGIONE"))
						&& stabDet.getIdAsl() == new Integer(ApplicationProperties.getProperty("ID_ASL_FUORI_REGIONE"))) {
					tipologiaRegistrazione = EventoRegistrazioneBDU.idTipologiaRegistrazioneAFuoriRegione;
				} else {

					if (thisAnimale.getIdSpecie() == Gatto.idSpecie) {
						tipologiaRegistrazione = reg_bdu.getTipologiaDB(newGatto, db);
					} else {
						tipologiaRegistrazione = EventoRegistrazioneBDU.idTipologiaDB;
					}
				}

				// Registrazione di cattura per i cani e i gatti
				if (thisAnimale.getIdSpecie() != Furetto.idSpecie
						&& (context.getRequest().getParameter("flagCattura") != null
								&& !("").equals(context.getRequest().getParameter("flagCattura")) && ("on")
									.equals(context.getRequest().getParameter("flagCattura")))) {

					catturato = true;
					cattura = (EventoCattura) context.getRequest().getAttribute("EventoCattura");
					// Caso vet privati o ruolo anagrafecanina
					if ((user.getSiteId() > 0 && user.getSiteId() != thisAnimale.getIdAslRiferimento())) {
						cattura.setIdAslRiferimento(user.getSiteId());
					} else if (user.getSiteId() < 0) {
						cattura.setIdAslRiferimento(thisAnimale.getIdAslRiferimento());
					}
					cattura.setIdAnimale(thisAnimale.getIdAnimale());
					cattura.setIdTipologiaEvento(cattura.idTipologiaDB);
					cattura.setEnteredby(this.getUserId(context));
					cattura.setIdProprietarioSindaco((String) context.getRequest().getParameter("idProprietario"));
					cattura.setSpecieAnimaleId(thisAnimale.getIdSpecie());
					cattura.insert(db);
					// thisAnimale.setFlagCatturato(true);
					// thisAnimale.setIdComuneCattura(cattura.getIdComuneCattura());
					tipologiaRegistrazione = cattura.idTipologiaDB;
				}

				if (context.getRequest().getParameter("microchip") != null
						&& !("").equals(context.getRequest().getParameter("microchip"))) {
					EventoInserimentoMicrochip microchip = (EventoInserimentoMicrochip) context.getRequest()
							.getAttribute("EventoInserimentoMicrochip");
					// Caso vet privati o ruolo anagrafecanina
					if ((user.getSiteId() > 0 && user.getSiteId() != thisAnimale.getIdAslRiferimento())) {
						microchip.setIdAslRiferimento(user.getSiteId());
					} else if (user.getSiteId() < 0) {
						microchip.setIdAslRiferimento(thisAnimale.getIdAslRiferimento());
					}
					microchip.setIdAnimale(thisAnimale.getIdAnimale());
					microchip.setEnteredby(this.getUserId(context));
					microchip.setIdTipologiaEvento(microchip.idTipologiaDB);
					microchip.setSpecieAnimaleId(thisAnimale.getIdSpecie());
					microchip.setDataInserimentoMicrochip((String) context.getRequest().getParameter("dataMicrochip"));
					microchip.setNumeroMicrochipAssegnato((String) context.getRequest().getParameter("microchip"));
					microchip.setIdVeterinarioPrivatoInserimentoMicrochip(thisAnimale.getIdVeterinarioMicrochip());
					microchip.setIdProprietarioCorrente(thisAnimale.getIdProprietario());
					microchip.setIdDetentoreCorrente(thisAnimale.getIdDetentore());
					microchip.insert(db);

				}
				
				String esitoInvioSinaaf[] = null;
				//La metto dopo l'inserimento microchip perchè serve sapere il microchip da tavella evento_inserimento_microchip
				if(ApplicationProperties.getProperty("abilitato_sinaaf").equals("true"))
				{
				 esitoInvioSinaaf = new Sinaaf().inviaInSinaaf(db, getUserId(context),reg_bdu.getIdEvento()+"", "evento");
				if(esitoInvioSinaaf[0]!=null)
					context.getRequest().setAttribute("messaggio", "Registrazione di iscrizione animale: " + esitoInvioSinaaf[0]);
				if(esitoInvioSinaaf[1]!=null)
					context.getRequest().setAttribute("Error", "Registrazione di iscrizione animale: " + esitoInvioSinaaf[1]);
				}

				if ((String) context.getRequest().getParameter("dataVaccino") != null
						&& !("").equals((String) context.getRequest().getParameter("dataVaccino"))) {
					EventoInserimentoVaccinazioni rabbia = (EventoInserimentoVaccinazioni) context.getRequest()
							.getAttribute("EventoInserimentoVaccinazioni");

					if ((user.getSiteId() > 0 && user.getSiteId() != thisAnimale.getIdAslRiferimento())) {
						rabbia.setIdAslRiferimento(user.getSiteId());
					} else if (user.getSiteId() < 0) {
						rabbia.setIdAslRiferimento(thisAnimale.getIdAslRiferimento());
					}

					rabbia.setIdTipoVaccino(EventoInserimentoVaccinazioni.antirabbica);
					rabbia.setDataVaccinazione((String) context.getRequest().getParameter("dataVaccino"));
					rabbia.setDataScadenzaVaccino((String) context.getRequest().getParameter("dataScadenzaVaccino"));
					rabbia.setNomeVaccino((String) context.getRequest().getParameter("nomeVaccino"));
					rabbia.setProduttoreVaccino((String) context.getRequest().getParameter("produttoreVaccino"));
					rabbia.setIdAnimale(thisAnimale.getIdAnimale());
					rabbia.setEnteredby(getUserId(context));
					rabbia.setModifiedby(getUserId(context));
					rabbia.setIdTipologiaEvento(rabbia.idTipologiaDB);
					rabbia.setSpecieAnimaleId(thisAnimale.getIdSpecie());
					rabbia.setIdProprietarioCorrente(thisAnimale.getIdProprietario());
					rabbia.setIdDetentoreCorrente(thisAnimale.getIdDetentore());
					rabbia.insert(db);
					
					if(ApplicationProperties.getProperty("abilitato_sinaaf").equals("true"))
					{
					esitoInvioSinaaf = new Sinaaf().inviaInSinaaf(db, getUserId(context),rabbia.getIdEvento()+"", "evento");
					if(esitoInvioSinaaf[0]!=null)
						context.getRequest().setAttribute("messaggio", "Registrazione di iscrizione animale" + esitoInvioSinaaf[0]);
					if(esitoInvioSinaaf[1]!=null)
						context.getRequest().setAttribute("Error", "Registrazione di iscrizione animale" + esitoInvioSinaaf[1]);
					}

				}
				/*
				 * else if (thisAnimale.getIdSpecie() == Gatto.idSpecie){
				 * Operatore proprietario = thisAnimale.getProprietario();
				 * Stabilimento stab = (Stabilimento)
				 * proprietario.getListaStabilimenti().get(0); LineaProduttiva
				 * lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
				 * if (lp.getIdAttivita() == LineaProduttiva.idAttivitaColonia){
				 * lp.getIdRelazioneAttivita(); //Se ho scelto colonia il gatto
				 * deve essere randagio tipologiaRegistrazione =
				 * cattura.idTipologiaDB; }
				 * 
				 * }
				 */

				if (context.getRequest().getParameter("flagMorsicatore") != null
						&& !("").equals(context.getRequest().getParameter("flagMorsicatore"))) {
					EventoMorsicatura morsicatura = (EventoMorsicatura) context.getRequest().getAttribute(
							"EventoMorsicatura");

					// Caso vet privati o ruolo anagrafecanina
					if ((user.getSiteId() > 0 && user.getSiteId() != thisAnimale.getIdAslRiferimento())) {
						morsicatura.setIdAslRiferimento(user.getSiteId());
					} else if (user.getSiteId() < 0) {
						morsicatura.setIdAslRiferimento(thisAnimale.getIdAslRiferimento());
					}

					morsicatura.setIdAnimale(thisAnimale.getIdAnimale());
					morsicatura.setEnteredby(this.getUserId(context));
					morsicatura.setIdTipologiaEvento(morsicatura.idTipologiaDB);
					morsicatura.setSpecieAnimaleId(thisAnimale.getIdSpecie());
					morsicatura.setIdProprietarioCorrente(thisAnimale.getIdProprietario());
					morsicatura.setIdDetentoreCorrente(thisAnimale.getIdDetentore());
					morsicatura.insert(db);
					
					if(ApplicationProperties.getProperty("abilitato_sinaaf").equals("true"))
					{
					esitoInvioSinaaf = new Sinaaf().inviaInSinaaf(db, getUserId(context),morsicatura.getIdEvento()+"", "evento");
					if(esitoInvioSinaaf[0]!=null)
						context.getRequest().setAttribute("messaggio", "Registrazione di iscrizione animale" + esitoInvioSinaaf[0]);
					if(esitoInvioSinaaf[1]!=null)
						context.getRequest().setAttribute("Error", "Registrazione di iscrizione animale" + esitoInvioSinaaf[1]);
					}
					
					Cane cane = new Cane(db, thisAnimale.getIdAnimale());
					// cane.setFlagMorsicatore(true);
					// cane.setDataMorso1(morsicatura.getDataMorso());
					cane.updateStato(db);
					// non è necessario aggiornare il wkf

				}

				/**
				 * IN APPLICAZIONE CR 2015 BANCA DATI A PRIORI PASSAPORTI NON SI
				 * DEVE GENERARE UNA REGISTRAZIONE DI RILASCIO PASSAPORTO IN
				 * FASE DI INSERIMENTO ANAGRAFICA PERCHE TRATTASI DI PASSAPORTI
				 * NON ASSEGNATI DALLA REGIONE CAMPANIA. PER QUESTO IL VALORE
				 * NON DEVE ESSERE TRA QUELLI ASSEGNATI ALLA REGIONE.
				 */
				
				
				/**
				 * MODIFICA RICHIESTA DA DANTE IN DATA 01/07/2015:
				 * AL POSTO DELLA REGISTRAZIONE DI RILASCIO BISOGNA INSERIRE
				 * UNA DI RICONOSCIMENTO PASSAPORTO EVENTUALMENTE NASCOSTA
				 * DALLA LISTA DELLE REGISTRAZIONI VISIBILI ALL'UTENTE FINALE
				 */

				if (context.getRequest().getParameter("numeroPassaporto") != null
						&& !("").equals(context.getRequest().getParameter("numeroPassaporto"))) {
					
					
					EventoRiconoscimentoPassaporto riconoscimentoPassaporto = (EventoRiconoscimentoPassaporto) context.getRequest().getAttribute(
							"EventoRiconoscimentoPassaporto");

					/*
					 * / Caso vet privati o ruolo anagrafecanina
					 */
					if ((user.getSiteId() > 0 && user.getSiteId() != thisAnimale.getIdAslRiferimento())) {
						riconoscimentoPassaporto.setIdAslRiferimento(user.getSiteId());
					} else if (user.getSiteId() < 0) {
						riconoscimentoPassaporto.setIdAslRiferimento(thisAnimale.getIdAslRiferimento());
					}

					riconoscimentoPassaporto.setIdAnimale(thisAnimale.getIdAnimale());
					riconoscimentoPassaporto.setIdTipologiaEvento(EventoRiconoscimentoPassaporto.idTipologiaDB);
					riconoscimentoPassaporto.setSpecieAnimaleId(thisAnimale.getIdSpecie());
					riconoscimentoPassaporto.setEnteredby(this.getUserId(context));
					riconoscimentoPassaporto.setFlagPassaportoAttivo(true);
					riconoscimentoPassaporto.setIdProprietarioCorrente(thisAnimale.getIdProprietario());
					riconoscimentoPassaporto.setIdDetentoreCorrente(thisAnimale.getIdDetentore());
					riconoscimentoPassaporto.insert(db);

					thisAnimale.setDataRilascioPassaporto(riconoscimentoPassaporto.getDataRilascioPassaporto());
					thisAnimale.setNumeroPassaporto(riconoscimentoPassaporto.getNumeroPassaporto());
					thisAnimale.setDataScadenzaPassaporto(riconoscimentoPassaporto.getDataScadenzaPassaporto());
					thisAnimale.updateStato(db);

										
					
				}

				if ((newCane.getDataControlloEhrlichiosi() != null && newCane.getEsitoControlloEhrlichiosi() > -1)
						|| (newCane.getDataControlloRickettsiosi() != null && newCane.getEsitoControlloRickettsiosi() > -1)) {
					EventoEsitoControlli controlli = (EventoEsitoControlli) context.getRequest().getAttribute(
							"EventoEsitoControlli");

					// Caso vet privati o ruolo anagrafecanina
					if ((user.getSiteId() > 0 && user.getSiteId() != thisAnimale.getIdAslRiferimento())) {
						controlli.setIdAslRiferimento(user.getSiteId());
					} else if (user.getSiteId() < 0) {
						controlli.setIdAslRiferimento(thisAnimale.getIdAslRiferimento());
					}

					controlli.setDataEsito(reg_bdu.getDataRegistrazione());
					controlli.setEnteredby(this.getUserId(context));
					controlli.setMicrochip(thisAnimale.getMicrochip());
					controlli.setFlagEhrlichiosi(newCane.isFlagControlloEhrlichiosi());
					controlli.setDataEhrlichiosi(newCane.getDataControlloEhrlichiosi());
					controlli.setEsitoEhrlichiosi(newCane.getEsitoControlloEhrlichiosi());

					controlli.setFlagRickettiosi(newCane.isFlagControlloRickettsiosi());
					controlli.setDataRickettiosi(newCane.getDataControlloRickettsiosi());
					controlli.setEsitoRickettiosi(newCane.getEsitoControlloRickettsiosi());

					controlli.setIdAnimale(thisAnimale.getIdAnimale());
					controlli.setIdTipologiaEvento(EventoEsitoControlli.idTipologiaDB);
					controlli.setSpecieAnimaleId(thisAnimale.getIdSpecie());
					controlli.setIdProprietarioCorrente(thisAnimale.getIdProprietario());
					controlli.setIdDetentoreCorrente(thisAnimale.getIdDetentore());
					controlli.insert(db);
				}

				// Aggiorno wkf privato / randagio
				RegistrazioniWKF r_wkf = new RegistrazioniWKF();
				r_wkf.setIdStato(thisAnimale.getStato());
				r_wkf.setIdRegistrazione(tipologiaRegistrazione);
				thisAnimale.setStato((r_wkf.getProssimoStatoDaStatoPrecedenteERegistrazione(db)).getIdProssimoStato());
				thisAnimale.updateStato(db);

				// Registrazione di sterilizzazione
				if (context.getRequest().getParameter("flagSterilizzazione") != null
						&& !("").equals(context.getRequest().getParameter("flagSterilizzazione"))
						&& ("on").equals(context.getRequest().getParameter("flagSterilizzazione"))) {

					EventoSterilizzazione sterilizzazione = (EventoSterilizzazione) context.getRequest().getAttribute(
							"EventoSterilizzazione");

					// Caso vet privati o ruolo anagrafecanina
					if ((user.getSiteId() > 0 && user.getSiteId() != thisAnimale.getIdAslRiferimento())) {
						sterilizzazione.setIdAslRiferimento(user.getSiteId());
					} else if (user.getSiteId() < 0) {
						sterilizzazione.setIdAslRiferimento(thisAnimale.getIdAslRiferimento());
					}

					sterilizzazione.setIdAnimale(thisAnimale.getIdAnimale());
					sterilizzazione.setIdTipologiaEvento(sterilizzazione.idTipologiaDB);
					int idAslProprietario = ((Stabilimento) thisAnimale.getProprietario().getListaStabilimenti().get(0))
							.getIdAsl();
					sterilizzazione.setIdAslProprietario(idAslProprietario);
					sterilizzazione.setSpecieAnimaleId(thisAnimale.getIdSpecie());
					sterilizzazione.setIdProprietarioCorrente(thisAnimale.getIdProprietario());
					sterilizzazione.setIdDetentoreCorrente(thisAnimale.getIdDetentore());
					sterilizzazione.setEnteredby(this.getUserId(context));
					sterilizzazione.setIdStatoOriginale(thisAnimale.getStato());
					if(sterilizzazione.getIdProgettoSterilizzazioneRichiesto()>0)
						sterilizzazione.setFlagContributoRegionale(true);
					sterilizzazione.insert(db);
					
					if(ApplicationProperties.getProperty("abilitato_sinaaf").equals("true"))
					{
					esitoInvioSinaaf = new Sinaaf().inviaInSinaaf(db, getUserId(context),sterilizzazione.getIdEvento()+"", "evento");
					if(esitoInvioSinaaf[0]!=null)
						context.getRequest().setAttribute("messaggio", "Registrazione di iscrizione animale" + esitoInvioSinaaf[0]);
					if(esitoInvioSinaaf[1]!=null)
						context.getRequest().setAttribute("Error", "Registrazione di iscrizione animale" + esitoInvioSinaaf[1]);
					}
					
					
					// Aggiorno stato wkf
					r_wkf.setIdStato(thisAnimale.getStato());
					r_wkf.setIdRegistrazione(sterilizzazione.idTipologiaDB);
					thisAnimale.setStato((r_wkf.getProssimoStatoDaStatoPrecedenteERegistrazione(db))
							.getIdProssimoStato());
					thisAnimale.setFlagSterilizzazione(true);
					thisAnimale.setIdPraticaContributi(sterilizzazione.getIdProgettoSterilizzazioneRichiesto());
					thisAnimale.updateStato(db);
				}
				reg_bdu.setIdStato(thisAnimale.getStato());
				reg_bdu.updateStato(db);
				// AGGIORNARE STATO IN TAB

				context.getRequest().setAttribute("idSpecie", thisAnimale.getIdSpecie());

				context.getRequest().setAttribute("CustomerSatisfactionOnDetail", "OK");

			}
			/*
			 * Parametri necessari per l'invocazione della jsp go_to_detail.jsp
			 * invocata quando l'inserimento va a buon fine("InsertOK")
			 */
			context.getRequest().setAttribute("commandD", "AnimaleAction.do?command=Details");
			context.getRequest().setAttribute("animale_id", "&orgId=" + thisAnimale.getIdAnimale());

		} catch (PSQLException e) {
			

			// IN CASO DI CREAZIONE INDICE, IPOTESI PER ORA ESCLUSA DATA LA
			// PRESENZA GIa' DI DUPLICATI
			if (("23505").equals(e.getSQLState()))
				log.error("ERRORE: TENTATIVO INSERIMENTO MC DUPLICATO, BLOCCATO DA TRIGGER!! MC: "
						+ newAnimale.getMicrochip() + " TATUAGGIO: " + newAnimale.getTatuaggio() + " INSERITO DA: "
						+ thisAnimale.getIdUtenteInserimento());

			e.printStackTrace();
			context.getRequest().setAttribute("Error",
					"Si è verificato un problema con l'inserimento dell'animale. Riprovare");
			return ("SystemError");

		}
		
		
		catch (ConnectException e) {
			
			e.printStackTrace();
			
			return executeCommandAdd(context);

		}
		
		catch (Exception e) {
			
			e.printStackTrace();
			
			db.rollback();
			
			context.getRequest().setAttribute("Error",
					"Si è verificato un problema con l'inserimento dell'animale. Riprovare");
			return ("SystemError");

		}

		 finally {

			db.close();
			this.freeConnection(context, db, pst);

		}

		/*
		 * catch (Exception errorMessage) {
		 * 
		 * errorMessage.printStackTrace();
		 * context.getRequest().setAttribute("Error", errorMessage); return
		 * ("SystemError"); } finally { this.freeConnection(context, db); }
		 */
		addModuleBean(context, "View Accounts", "Accounts Insert ok");
		if (recordInserted) {
			String target = context.getRequest().getParameter("target");
			if (context.getRequest().getParameter("popup") != null
					&& !("false").equals((String) context.getRequest().getParameter("popup"))) {
				return ("ClosePopupOK");
			}

		}

		context.getRequest().setAttribute("animaleId", new Integer(thisAnimale.getIdAnimale()).toString());
		context.getRequest().setAttribute("idSpecie", new Integer(thisAnimale.getIdSpecie()).toString());

		context.getRequest().setAttribute("SalvaeClona", context.getParameter("saveandclone"));

		context.getRequest().setAttribute("dataOggi", new Date());
		
		return (executeCommandDetails(context));

		// return ("InsertOK");

	}

	public String executeCommandSearchForm(ActionContext context) {

		if (!(hasPermission(context, "anagrafe_canina-anagrafe_canina-view"))) {
			return ("PermissionError");
		}

		UserBean user = (UserBean) context.getSession().getAttribute("User");

		// Bypass search form for portal users
		if (isPortalUser(context)) {
			// return (executeCommandSearch(context));
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		// Connection dbvam = null;
		try {
			Thread t = Thread.currentThread();
			db = this.getConnection(context);
			// dbvam = getConnectionVam(context);

			/*
			 * String query = "select * from accettazione"; PreparedStatement
			 * pst = dbvam.prepareStatement(query); ResultSet rs =
			 * pst.executeQuery(); while (rs.next()){
			 * System.out.println(rs.getInt("id")); }
			 */

			UserList listaUtenti = new UserList();
			listaUtenti.setBuildContact(false);
			listaUtenti.setSiteId(this.getUserAsl(context));
			listaUtenti.buildList(db);

			ArrayList<User> utenti = new ArrayList<User>();

			for (int i = 0; i < listaUtenti.size(); i++) {
				utenti.add((User) listaUtenti.get(i));

			}

			LookupList utentiL = new LookupList(utenti, -1);
			utentiL.addItem(-1, "-- SELEZIONA --");
			context.getRequest().setAttribute("utentiList", utentiL);

			int idSpecie = Cane.idSpecie;

			if (context.getRequest().getAttribute("idSpecie") != null) {
				idSpecie = new Integer((String) context.getRequest().getAttribute("idSpecie")).intValue();
			}

			PagedListInfo animaliListInfo = this.getPagedListInfo(context, "SearchAnimaListInfo");
			animaliListInfo.setCurrentLetter("");
			animaliListInfo.setCurrentOffset(0);
			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			LookupList regioniList = new LookupList(db, "lookup_regione");
			regioniList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("regioniList", regioniList);

			LookupList specieList = new LookupList(db, "lookup_specie");
			specieList.addItem(-1, "--Tutti--");
			context.getRequest().setAttribute("specieList", specieList);

			/*
			 * LookupList razzaList = new LookupList(db, "lookup_razza");
			 * razzaList.addItem(-1, systemStatus
			 * .getLabel("calendar.none.4dashes")); //
			 * assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			 * context.getRequest().setAttribute("razzaList", razzaList);
			 */

			PagedListInfo toOrderLookup = new PagedListInfo();
			toOrderLookup.setColumnToSortBy("description");
			toOrderLookup.setItemsPerPage(-1); // PER VISUALIZZARLI TUTTI

			LookupList razzaList = new LookupList();
			razzaList.setPagedListInfo(toOrderLookup);
			razzaList.setTable("lookup_razza");
			razzaList.setIdSpecie(idSpecie);
			razzaList.buildList(db);
			razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("razzaList", razzaList);

			LookupList tagliaList = new LookupList(db, "lookup_taglia");
			tagliaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("tagliaList", tagliaList);

			LookupList mantelloList = new LookupList();
			mantelloList.setPagedListInfo(toOrderLookup);
			mantelloList.setIdSpecie(idSpecie);
			mantelloList.setTable("lookup_mantello");
			mantelloList.buildList(db);
			mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));

			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("mantelloList", mantelloList);

			LookupList statoList = new LookupList(db, "lookup_tipologia_stato");
			statoList.addItem(-1, "--Tutti--");
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("statoList", statoList);

			Calendar cal = new GregorianCalendar();
			int annoCorrenteInt = cal.get(Calendar.YEAR);
			String annoCorrente = new Integer(annoCorrenteInt).toString();
			// int annoCorrenteInt = Integer.parseInt(annoCorrente);
			LookupList annoList = new LookupList(); // creo una nuova lookup

			annoList.addItem(annoCorrenteInt - 3, String.valueOf(annoCorrenteInt - 3));
			annoList.addItem(annoCorrenteInt - 2, String.valueOf(annoCorrenteInt - 2));
			annoList.addItem(annoCorrenteInt - 1, String.valueOf(annoCorrenteInt - 1));
			annoList.addItem(annoCorrenteInt, annoCorrente);
			annoList.addItem(-1, "Tutti");
			annoList.setDefaultValue(annoCorrenteInt);

			context.getRequest().setAttribute("annoList", annoList);

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, user.getSiteId(), 1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("comuniList", comuniList);
			// buildFormElements(context, db);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
			// this.freeConnectionVam(context, dbvam);
		}
		addModuleBean(context, "Search Animale", "Animali Search");
		return ("SearchOK");

	}

	public String executeCommandSearch(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina-anagrafe_canina-view")) {
			return ("PermissionError");
		}

		UserBean user = (UserBean) context.getSession().getAttribute("User");
		AnimaleList animaleList = new AnimaleList();

		addModuleBean(context, "View Accounts", "Search Results");
		if ("animaliProprietari".equals(context.getSession().getAttribute("previousSearchType"))) { // resetto
																									// se
																									// ho
																									// i
																									// dati
																									// di
																									// ricerca
			// provenienti dalla lista animali
			// di proprietario
			// reset pagedListInfo
			this.deletePagedListInfo(context, "animaliListInfo");
		}

		// Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context, "animaliListInfo");
		searchListInfo.setLink("AnimaleAction.do?command=Search");
		searchListInfo.setListView("all");
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			Thread t = Thread.currentThread();
			db = this.getConnection(context);

			LookupList lookupSpecie = new LookupList(db, "lookup_specie");
			context.getRequest().setAttribute("LookupSpecie", lookupSpecie);
			// Build the organization list
			animaleList.setPagedListInfo(searchListInfo);
			animaleList.setMinerOnly(false);
			animaleList.setTypeId(searchListInfo.getFilterKey("listFilter1"));

			animaleList.setStageId(searchListInfo.getCriteriaValue("searchcodeStageId"));
			animaleList.setBuildProprietario(false);
			//
			// String vivente_deceduto = (String)
			// context.getRequest().getParameter("stato_viventi_deceduti");
			//
			// if (vivente_deceduto != null && new Integer(vivente_deceduto) > 0
			// ){
			// if ( new Integer(vivente_deceduto) == 1){
			// animaleList.setFlagDecesso(false);
			// }else if (new Integer(vivente_deceduto) == 2)
			// animaleList.setFlagDecesso(true);
			// }

			HashMap mapSaved = new HashMap();
			if (this.getUserRole(context) == new Integer(ApplicationProperties.getProperty("ID_RUOLO_UTENTE_COMUNE"))) {
				mapSaved = searchListInfo.getSavedCriteria();
				ComuniAnagrafica c = new ComuniAnagrafica();
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, this.getUserAsl(context), 1);
				LookupList comuniList = new LookupList(listaComuni, -1);
				animaleList.setFiltroProprietarioDetentoreComune(true);
				searchListInfo.setSavedCriteria(mapSaved);
			}

			searchListInfo.setSearchCriteria(animaleList, context);
			
			if (context.getRequest().getParameter("doContinue") != null
					&& !context.getRequest().getParameter("doContinue").equals("")
					&& context.getRequest().getParameter("doContinue").equals("false")) {
				context.getRequest().setAttribute("idSpecie", context.getRequest().getParameter("searchcodeidSpecie"));
				return executeCommandSearchForm(context);
			}

			if ("my".equals(searchListInfo.getListView())) {
				// operatoreList.setOwnerId(this.getUserId(context));
			}
			if (animaleList.getIdAsl() == Constants.INVALID_SITE) {
				animaleList.setIdAsl(this.getUserSiteId(context));
				// organizationList.setIncludeOrganizationWithoutSite(false);
			} else if (animaleList.getIdAsl() == -1) {
				// TUTTE LE ASLanimaleList.setIncludeAllAsl(true);
			}
			// fetching criterea for account status (active, disabled or
			// any)
			// int enabled = searchListInfo.getFilterKey("listFilter2");

			animaleList.buildList(db);

			context.getRequest().setAttribute("animaleList", animaleList);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			LookupList razzaList = new LookupList(db, "lookup_razza");
			razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("razzaList", razzaList);

			LookupList mantelloList = new LookupList(db, "lookup_mantello");
			mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("mantelloList", mantelloList);

			LookupList statoList = new LookupList(db, "lookup_tipologia_stato");
			context.getRequest().setAttribute("statoList", statoList);
			context.getRequest().setAttribute("statoList", statoList);

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, -1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("comuniList", comuniList);

			context.getSession().setAttribute("previousSearchType", "animali");

			// Action di provenienza
			String servletPath = context.getRequest().getServletPath();
			String actionFrom = servletPath.substring(servletPath.indexOf("/") + 1, servletPath.indexOf(".do"));
			context.getRequest().setAttribute("actionFrom", actionFrom);

			return "ListOK";

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}

	public String executeCommandDetails(ActionContext context) {
		if (!hasPermission(context, "anagrafe_canina-anagrafe_canina-view")) {
			return ("PermissionError");
		}

		UserBean user = (UserBean) context.getSession().getAttribute("User");

		Connection db = null;
		Connection dbBdu = null;
		int idSpecie = 1; // default cane
		String microchip = "";
		Animale thisAnimale = new Animale();
		SystemStatus systemStatus = this.getSystemStatus(context);

		try {

			String tempanimaleId = context.getRequest().getParameter("animaleId");
			if (tempanimaleId == null) {
				tempanimaleId = (String) context.getRequest().getAttribute("animaleId");
			}

			String specieAnimaleId = context.getRequest().getParameter("idSpecie");
			if (specieAnimaleId == null) {
				specieAnimaleId = (String) context.getRequest().getAttribute("idSpecie");
			}

			if (!(specieAnimaleId == null) && !("").equals(specieAnimaleId)) {
				idSpecie = Integer.parseInt(specieAnimaleId);
			}

			microchip = (String) context.getRequest().getParameter("microchip");

			Integer tempid = -1;

			int idPartita = -1;

			if (tempanimaleId != null || microchip != null) {

				if (tempanimaleId != null)
					tempid = Integer.parseInt(tempanimaleId);

				if (context.getRequest().getParameter("origine") != null
						&& ("partite_commerciali").equals(context.getRequest().getParameter("origine"))){
					db = this.getConnectionImportatori(context);
					dbBdu = this.getConnection(context);
					}
				else{
					db = this.getConnection(context);
					dbBdu = db;
				}
					
				
				
				// costruisco la lookup per recuperare la tipologia
				LookupList lookupSpecie = new LookupList(db, "lookup_specie");
				context.getRequest().setAttribute("LookupSpecie", lookupSpecie);
				RegistrazioniWKF wkf = new RegistrazioniWKF();
				// wkf.setFlagIncludiHd(hasPermission(context,
				// "anagrafe_canina_registrazioni_pregresse-add"));

				switch (idSpecie) {
				case Cane.idSpecie: {
					Cane thisCane = new Cane();
					if (tempid > 0) {
						thisCane = new Cane(db, tempid);
					} else {
						thisCane = new Cane(db, microchip);
					}

					context.getRequest().setAttribute("caneDettaglio", thisCane);
					context.getRequest().setAttribute("animaleDettaglio", (Animale) thisCane);
					thisAnimale = thisCane;
					idPartita = thisCane.getIdPartitaCircuitoCommerciale();
					break;
				}
				case Gatto.idSpecie: {

					Gatto thisGatto = new Gatto(db, tempid);
					context.getRequest().setAttribute("gattoDettaglio", thisGatto);
					context.getRequest().setAttribute("animaleDettaglio", (Animale) thisGatto);
					idPartita = thisGatto.getIdPartitaCircuitoCommerciale();
					thisAnimale = thisGatto;
					// thisAnimale = new Cane(db, tempid);
					break;
				}
				case Furetto.idSpecie: {

					Furetto thisFuretto = new Furetto(db, tempid);
					context.getRequest().setAttribute("furettoDettaglio", thisFuretto);
					context.getRequest().setAttribute("animaleDettaglio", (Animale) thisFuretto);
					idPartita = thisFuretto.getIdPartitaCircuitoCommerciale();
					thisAnimale = thisFuretto;
					// thisAnimale = new Cane(db, tempid);
					break;
				}
				}

				if (context.getRequest().getParameter("origine") == null
						|| !("partite_commerciali").equals(context.getRequest().getParameter("origine"))) {
					wkf.buildWkfDati(context, thisAnimale,
							hasPermission(context, "anagrafe_canina_registrazioni_pregresse-add"), user,
							isUgualeAslAnimaleAslUtente(context, thisAnimale), db);
					wkf.checkPossibilitaRegistrazioni(db);
					context.getRequest().setAttribute("wkf", wkf);
				}

			}

			if (idPartita > -1) {
				PartitaCommerciale partita = new PartitaCommerciale(dbBdu, idPartita);
				context.getRequest().setAttribute("partita", partita);
			}
			
			
			SchedaAdozione scheda = new SchedaAdozione();
			ArrayList<SchedaAdozione> schede = scheda.getByIdAnimale(db, thisAnimale.getIdAnimale());
			context.getRequest().setAttribute("schede", schede);
			if(schede.size()>0)
			{
				Valutazione valutazione = scheda.getValutazione(db,thisAnimale.getIdAnimale());
				context.getRequest().setAttribute("valutazione", valutazione);
			}	
			
			ArrayList<SchedaMorsicatura> schedeMorsi = new SchedaMorsicatura().getByIdAnimale(dbBdu, thisAnimale.getIdAnimale());
			context.getRequest().setAttribute("schedeMorsi", schedeMorsi);
			if(!schedeMorsi.isEmpty())
			{
				SchedaMorsicatura schedaMorso = schedeMorsi.get(0);
				org.aspcfs.modules.schedaMorsicatura.base.Valutazione valutazioneMorso = schedaMorso.getValutazione(db,schedaMorso.getId());
				context.getRequest().setAttribute("valutazioneMorso", valutazioneMorso);
			}	
			
				

			EventoRegistrazioneBDU reg_bdu = new EventoRegistrazioneBDU();
			try {
				reg_bdu=reg_bdu.getEventoRegistrazione(db, thisAnimale.getIdAnimale());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			context.getRequest().setAttribute("evento", reg_bdu);
			
			// lookups
			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(dbBdu, -1, -1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			context.getRequest().setAttribute("comuniList", comuniList);

			LookupList provinceList = new LookupList(db, "lookup_province");
			provinceList.addItem(-1, "Seleziona provincia");
			context.getRequest().setAttribute("provinceList", provinceList);
			
			LookupList nazioniList = new LookupList(db,	"lookup_nazioni");
			nazioniList.addItem(-1, "--Seleziona--");
			nazioniList.removeElementByCode(106);
			context.getRequest().setAttribute("nazioniList", nazioniList);
			
			LookupList tagliaList = new LookupList(dbBdu, "lookup_taglia");
			tagliaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("tagliaList", tagliaList);

			LookupList siteList = new LookupList();
			siteList.setShowDisabledFlag(true);
			siteList.setTable("lookup_asl_rif");
			siteList.buildList(dbBdu);
			context.getRequest().setAttribute("SiteList", siteList);

			LookupList regioniList = new LookupList(dbBdu, "lookup_regione");
			regioniList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("regioniList", regioniList);

			LookupList razzaList = new LookupList(dbBdu, "lookup_razza");
			razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("razzaList", razzaList);

			LookupList mantelloList = new LookupList(dbBdu, "lookup_mantello");
			mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("mantelloList", mantelloList);

			LookupList statoList = new LookupList(dbBdu, "lookup_tipologia_stato");
			context.getRequest().setAttribute("statoList", statoList);

			LookupList esitoControlloList = new LookupList(dbBdu, "lookup_esito_controlli");
			context.getRequest().setAttribute("esitoControlloList", esitoControlloList);

			LookupList esitoControlloLabList = new LookupList(dbBdu, "lookup_esito_controlli_laboratorio");
			context.getRequest().setAttribute("esitoControlloLabList", esitoControlloLabList);

			LookupList specieList = new LookupList();
			specieList.setTable("lookup_specie");
			specieList.buildList(dbBdu);
			context.getRequest().setAttribute("specieList", specieList);

			// Lista pratiche contributi
			PraticaList listaP = new PraticaList();
			ArrayList<PraticaDWR> listaPratiche = listaP.getListPratiche(dbBdu);
			LookupList praticheContributi = new LookupList(listaPratiche, -1);
			context.getRequest().setAttribute("listaPratiche", praticheContributi);

			LookupList veterinari = new LookupList(dbBdu, "elenco_veterinari_chippatori_with_asl_select_grouping");
			veterinari.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("veterinariList", veterinari);

			// Action di provenienza
			String servletPath = context.getRequest().getServletPath();
			String actionFrom = servletPath.substring(servletPath.indexOf("/") + 1, servletPath.indexOf(".do"));
			context.getRequest().setAttribute("actionFrom", actionFrom);

			context.getRequest().setAttribute("origine", (String) context.getRequest().getParameter("origine"));

			// Registrazione ultima di vaccinazione antirabbia
			thisAnimale = new Animale(dbBdu, tempid);
			String microchip_to_search = (thisAnimale.getMicrochip() != null && !("")
					.equals(thisAnimale.getMicrochip())) ? thisAnimale.getMicrochip() : thisAnimale.getTatuaggio();
			// EventoInserimentoVaccinazioni vaccinazioneRabbia =
			// EventoInserimentoVaccinazioni.getUltimaVaccinazioneDaTipo(db,
			// microchip_to_search, EventoInserimentoVaccinazioni.antirabbica);
			EventoInserimentoVaccinazioni vaccinazioneRabbia = EventoInserimentoVaccinazioni
					.getUltimaVaccinazioneDaTipo(dbBdu, thisAnimale.getIdAnimale(),
							EventoInserimentoVaccinazioni.antirabbica);

			context.getRequest().setAttribute("dati_antirabbica", vaccinazioneRabbia);

			// Dati passaporto
			EventoRilascioPassaporto rilascioPassaporto = new EventoRilascioPassaporto();
			rilascioPassaporto.GetPassaportoAttivoByIdAnimale(dbBdu, thisAnimale.getIdAnimale());
			context.getRequest().setAttribute("rilascioPassaporto", rilascioPassaporto);

			// Registrazione cessione aperta
			EventoCessione cessione = new EventoCessione();
			cessione.GetCessioneApertaByIdAnimale(dbBdu, thisAnimale.getIdAnimale());
			context.getRequest().setAttribute("dati_cessione", cessione);

			context.getRequest().setAttribute("dati_antirabbica", vaccinazioneRabbia);

			EventoPrelievoLeishmania prelievoLeish = new EventoPrelievoLeishmania();
			prelievoLeish.getUltimoPrelievo(thisAnimale.getMicrochip(), dbBdu);

			context.getRequest().setAttribute("prelievoLeish", prelievoLeish);
			
			if(ApplicationProperties.getProperty("abilitato_sinaaf").equals("true"))
			{
				context.getRequest().setAttribute("ws", new WsPost(db,reg_bdu.getIdEvento()+"","evento",ApplicationProperties.getProperty("END_POINT_SINAAF")));
				if(context.getRequest().getParameter("errore")!=null && !context.getRequest().getParameter("errore").equals("") && !context.getRequest().getParameter("errore").equals("null"))
					context.getRequest().setAttribute("Error", context.getRequest().getParameter("errore"));
				if(context.getRequest().getParameter("messaggio")!=null && !context.getRequest().getParameter("messaggio").equals("") && !context.getRequest().getParameter("messaggio").equals("null"))
					context.getRequest().setAttribute("messaggio", context.getRequest().getParameter("messaggio"));
			}
			
			if (context.getRequest().getAttribute("SalvaeClona") != null
					&& "1".equalsIgnoreCase("" + context.getRequest().getAttribute("SalvaeClona"))) {
				veterinari = new LookupList(dbBdu, "elenco_veterinari_chippatori_with_asl_select_grouping");
				veterinari.addItem(-1, "--Seleziona--");
				context.getRequest().setAttribute("veterinariList", veterinari);

				siteList = new LookupList(dbBdu, "lookup_asl_rif");
				siteList.addItem(-1, "-- SELEZIONA VOCE --");
				context.getRequest().setAttribute("AslList", siteList);

				
				LookupList mantelloList1 = new LookupList();
				mantelloList1.setTable("lookup_mantello");
				mantelloList1.setIdSpecie(thisAnimale.getIdSpecie());
				mantelloList1.buildList(db);
				mantelloList1.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("mantelloList", mantelloList1);

				LookupList razzaList1 = new LookupList();
				razzaList1.setTable("lookup_razza");
				razzaList1.setIdSpecie(thisAnimale.getIdSpecie());
				razzaList1.buildList(db);
				razzaList1.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("razzaList", razzaList1);

				
				
				context.getRequest().setAttribute("SalvaeClona", "OK");
				context.getRequest().setAttribute("idSpecie", context.getRequest().getAttribute("idSpecie"));
				
				context.getRequest().setAttribute("dataOggi", new Date());
				
				return getReturn(context, "Add");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (context.getRequest().getParameter("origine") != null
					&& ("partite_commerciali").equals(context.getRequest().getParameter("origine"))){
				this.freeConnectionImportatori(context, db);
				this.freeConnection(context, dbBdu);
			}
			else{
				this.freeConnection(context, db);
			}

				

		}

		if(thisAnimale.isFlagMancataOrigine()){
			context.getRequest().setAttribute("mancataOrigine", "true");
		}
		
		if ((String) context.getSession().getAttribute("caller") != null 
				&& !("").equals((String) context.getSession().getAttribute("caller")) && (ApplicationProperties.getProperty("VAM_ID").equals((String) context.getSession().getAttribute("caller")))) {
		
			return ("DetailsNoNAVOK");
		}

		return getReturn(context, "Details");
	}

	public String executeCommandViewEsitiLeishmania(ActionContext context) {
		if (!hasPermission(context, "anagrafe_canina-anagrafe_canina-view")) {
			return ("PermissionError");
		}
		Connection db = null;

		String animaleId = context.getRequest().getParameter("animaleId");

		try {
			Thread t = Thread.currentThread();
			db = this.getConnection(context);
			LeishList lista_esiti = new LeishList();

			PagedListInfo esitiList = this.getPagedListInfo(context, "EsitiLeishList");
			esitiList.setLink("AnimaleAction.do?command=ViewEsitiLeishmania&animaleId=" + animaleId);
			lista_esiti.setPagedListInfo(esitiList);
			lista_esiti.setIdAnimale(Integer.parseInt(animaleId));
			lista_esiti.buildList(db);
			context.getRequest().setAttribute("ListaEsitiLeish", lista_esiti);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return getReturn(context, "ViewEsitiLeish");
	}

	public String executeCommandScegliOperatore(ActionContext context) {

		Connection db = null;
		try {
			Thread t = Thread.currentThread();
			db = this.getConnection(context);
			int idLineaProduttiva = -1;
			Operatore soggetto = new Operatore();
			if ((String) context.getRequest().getParameter("opId") != null) {
				idLineaProduttiva = new Integer((String) context.getRequest().getParameter("opId")).intValue();
			} else {
				idLineaProduttiva = new Integer((String) context.getRequest().getAttribute("opId")).intValue();
			}
			
			if(idLineaProduttiva>=10000000){
				context.getRequest().setAttribute("IdOrigineOperatore", idLineaProduttiva);
			}else{
				soggetto.queryRecordOperatorebyIdLineaProduttiva(db, idLineaProduttiva);
				context.getRequest().setAttribute("OperatoreAdded", soggetto);
			}
			
			if (context.getRequest().getParameter("tipologiaSoggetto") != null
					&& !"".equals(context.getRequest().getParameter("tipologiaSoggetto")))
				context.getRequest().setAttribute("TipologiaSoggetto",
						context.getRequest().getParameter("tipologiaSoggetto"));
			else
				context.getRequest().setAttribute("TipologiaSoggetto",
						context.getRequest().getAttribute("TipologiaSoggetto"));

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
			this.deletePagedListInfo(context, "SearchOrgListInfo");
		}
		return ("ClosePopupOK");
	}

	public String executeCommandListaRegistrazioni(ActionContext context) {

		Connection db = null;

		try {

			Cane dettaglioCane = null;
			Gatto dettaglioGatto = null;
			Furetto dettaglioFuretto = null;

			Thread t = Thread.currentThread();
			db = this.getConnection(context);

			String tempanimaleId = context.getRequest().getParameter("animaleId");
			if (tempanimaleId == null) {
				tempanimaleId = (String) context.getRequest().getAttribute("animaleId");
			}

			String specieAnimaleId = context.getRequest().getParameter("idSpecie");
			if (specieAnimaleId == null) {
				specieAnimaleId = (String) context.getRequest().getAttribute("idSpecie");
			}

			Integer tempid = null;

			if (tempanimaleId != null) {
				tempid = Integer.parseInt(tempanimaleId);
			}

			EventoList listaEventi = new EventoList();
			listaEventi.setId_animale(tempid);

			HashMap mapSaved = new HashMap();
			if (this.getUserRole(context) == new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP"))) {
				listaEventi.setId_utente_inserimento(this.getUserId(context));
			}
			listaEventi.buildList(db);

			context.getRequest().setAttribute("listaEventi", listaEventi);

			for (int i = 0; i < listaEventi.size(); i++) {
				Evento evento = (Evento) listaEventi.get(i);
				if (evento.getIdTipologiaEvento() == EventoSterilizzazione.idTipologiaDB) {
					EventoSterilizzazione ster = new EventoSterilizzazione(db, evento.getIdEvento());
					if (ster.isFlagContributoRegionale()) {
						Pratica praticaContributi = new Pratica(db, ster.getIdProgettoSterilizzazioneRichiesto());
						context.getRequest().setAttribute("praticacontributi", praticaContributi);
					}
				}
			}

			Animale thisAnimale = new Animale(db, tempid);
			context.getRequest().setAttribute("animaleDettaglio", thisAnimale);

			// registrazioni list
			RegistrazioniWKF wk = new RegistrazioniWKF();

			// Controllo la possibilità di aggiungere registrazioni pregresse
			wk.setFlagIncludiHd(hasPermission(context, "anagrafe_canina_registrazioni_pregresse-add"));

			ArrayList reg = wk.getRegistrazioniSpecie(db);
			LookupList registrazioniList = new LookupList(reg, -1);
			registrazioniList.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("registrazioniList", registrazioniList);

			if (thisAnimale.getIdSpecie() == Cane.idSpecie) {
				dettaglioCane = new Cane(db, tempid);
				context.getRequest().setAttribute("cane", dettaglioCane);
				wk.setIdStato(dettaglioCane.getStato());
				wk.setIdSpecie(dettaglioCane.getIdSpecie());

			} else if (thisAnimale.getIdSpecie() == Gatto.idSpecie) {
				dettaglioGatto = new Gatto(db, tempid);
				context.getRequest().setAttribute("gatto", dettaglioGatto);
				wk.setIdStato(dettaglioGatto.getStato());
				wk.setIdSpecie(dettaglioGatto.getIdSpecie());
			} else if (thisAnimale.getIdSpecie() == Furetto.idSpecie) {
				dettaglioFuretto = new Furetto(db, tempid);
				context.getRequest().setAttribute("furetto", dettaglioFuretto);
				wk.setIdStato(dettaglioFuretto.getStato());
				wk.setIdSpecie(dettaglioFuretto.getIdSpecie());
			}

			wk.checkPossibilitaRegistrazioni(db);

			context.getRequest().setAttribute("wkf", wk);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("DettaglioRegistrazioniOK");
	}

	public String executeCommandPrepareUpdate(ActionContext context) {
		if (!hasPermission(context, "anagrafe_canina-anagrafe_canina-edit")) {
			return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Animale thisAnimale = null;
		try {
			Thread t = Thread.currentThread();
			db = this.getConnection(context);
			thisAnimale = (Animale) context.getRequest().getAttribute("animale");
			if (thisAnimale == null) {
				String tempanimaleId = context.getRequest().getParameter("animaleId");
				if (tempanimaleId == null) {
					tempanimaleId = (String) context.getRequest().getAttribute("animaleId");
				}

				Integer tempid = null;

				if (tempanimaleId != null) {
					tempid = Integer.parseInt(tempanimaleId);

					thisAnimale = new Animale(db, tempid);
				}
			}
			if (context.getRequest().getParameter("idProprietarioProvenienza") != null	&& !context.getRequest().getParameter("idProprietarioProvenienza").equals("-1")){
				thisAnimale.settaNuovoProprietarioOrigine(db,Integer.parseInt((String)context.getRequest().getParameter("idProprietarioProvenienza")));
			}			
			context.getRequest().setAttribute("animale", thisAnimale);
			Animale oldAnimale = new Animale(db, thisAnimale.getIdAnimale());
			if (context.getRequest().getParameter("idProprietarioProvenienza") != null	&& !context.getRequest().getParameter("idProprietarioProvenienza").equals("-1")){
				oldAnimale.settaNuovoProprietarioOrigine(db,Integer.parseInt((String)context.getRequest().getParameter("idProprietarioProvenienza")));
			}

			context.getRequest().setAttribute("oldAnimale", oldAnimale);

			boolean fr=false;
			boolean fr_sindaco=false;
			int fr_id=-1;
			
			switch ((thisAnimale.getIdSpecie())) {
			case Cane.idSpecie: {
				Cane thisCane = new Cane(db, thisAnimale.getIdAnimale());
				context.getRequest().setAttribute("Cane", thisCane);
				fr= thisCane.isFlagFuoriRegione(); fr_id= thisCane.getIdRegioneProvenienza();
				fr_sindaco=thisCane.isFlagSindacoFuoriRegione();
				break;
			}
			case Gatto.idSpecie: {

				Gatto thisGatto = new Gatto(db, thisAnimale.getIdAnimale());
				context.getRequest().setAttribute("Gatto", thisGatto);
				fr= thisGatto.isFlagFuoriRegione(); fr_id= thisGatto.getIdRegioneProvenienza();
				fr_sindaco=thisGatto.isFlagSindacoFuoriRegione();
				break;
			}
			case Furetto.idSpecie: {

				Furetto thisFuretto = new Furetto(db, thisAnimale.getIdAnimale());
				context.getRequest().setAttribute("Furetto", thisFuretto);
				break;
			}
			}

			if (thisAnimale.getIdPraticaContributi() > -1) {
				Pratica pratica_contributi = new Pratica(db, thisAnimale.getIdPraticaContributi());
				context.getRequest().setAttribute("praticacontributi", pratica_contributi);
			}

			// lookups

			LookupList tagliaList = new LookupList(db, "lookup_taglia");
			tagliaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("tagliaList", tagliaList);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			LookupList regioniList = new LookupList(db, "lookup_regione");
			regioniList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("regioniList", regioniList);

			/*
			 * LookupList razzaList = new LookupList(db, "lookup_razza");
			 * razzaList.addItem(-1, systemStatus
			 * .getLabel("calendar.none.4dashes")); //
			 * assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			 * context.getRequest().setAttribute("razzaList", razzaList);
			 */

			LookupList razzaList = new LookupList();
			razzaList.setTable("lookup_razza");
			razzaList.setIdSpecie(thisAnimale.getIdSpecie());
			razzaList.buildList(db);
			razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("razzaList", razzaList);

			LookupList mantelloList = new LookupList();
			mantelloList.setTable("lookup_mantello");
			mantelloList.setIdSpecie(thisAnimale.getIdSpecie());
			mantelloList.buildList(db);
			mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("mantelloList", mantelloList);

			LookupList statoList = new LookupList(db, "lookup_tipologia_stato");
			context.getRequest().setAttribute("statoList", statoList);

			LookupList esitoControlloList = new LookupList(db, "lookup_esito_controlli");
			context.getRequest().setAttribute("esitoControlloList", esitoControlloList);

			LookupList specieList = new LookupList();
			specieList.setTable("lookup_specie");
			specieList.buildList(db);
			// specieList.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("specieList", specieList);

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, -1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			context.getRequest().setAttribute("comuniList", comuniList);
			
			ComuniAnagrafica c_all = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni_all = c.buildList(db, -1, -1);
			LookupList comuniList_all = new LookupList(listaComuni_all, -1);
			comuniList_all.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("comuniList_all", comuniList_all);

			LookupList nazioniList = new LookupList(db,	"lookup_nazioni");
			nazioniList.addItem(-1, "--Seleziona--");
			nazioniList.removeElementByCode(106);
			context.getRequest().setAttribute("nazioniList", nazioniList);
			
		
			LookupList provinceList = new LookupList(db, "lookup_province");
			provinceList.addItem(-1, "Seleziona provincia");
			context.getRequest().setAttribute("provinceList", provinceList);


			// Lista veterinari chippatori

			LookupList veterinari = new LookupList(db, "elenco_veterinari_chippatori_with_asl_select_grouping");
			
			veterinari.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("veterinariList", veterinari);

			// Dati passaporto

			EventoRilascioPassaporto rilascioPassaporto = new EventoRilascioPassaporto();
			rilascioPassaporto.GetPassaportoAttivoByIdAnimale(db, thisAnimale.getIdAnimale());
			context.getRequest().setAttribute("rilascioPassaporto", rilascioPassaporto);
			
			// Dati evento registrazione bdu
			EventoRegistrazioneBDU reg = EventoRegistrazioneBDU.getEventoRegistrazione(db,	thisAnimale.getIdAnimale());

			if (context.getRequest().getParameter("doContinue") != null
					&& !context.getRequest().getParameter("doContinue").equals("")
					&& context.getRequest().getParameter("doContinue").equals("false")) {
				/**************** PARAMETRI EVENTUALE ORIGINE ANIMALE ***************/
				context.getRequest().setAttribute("origine_da",(String) context.getRequest().getParameter("origine_da"));
				context.getRequest().setAttribute("tipo_origine",(String) context.getRequest().getParameter("tipo_origine"));
						
				context.getRequest().setAttribute("regione_ritrovamento",(String) context.getRequest().getParameter("regione_ritrovamento"));
				context.getRequest().setAttribute("provincia_ritrovamento",(String) context.getRequest().getParameter("provincia_ritrovamento"));
				context.getRequest().setAttribute("comune_ritrovamento",(String) context.getRequest().getParameter("comune_ritrovamento"));
				context.getRequest().setAttribute("indirizzo_ritrovamento",(String) context.getRequest().getParameter("indirizzo_ritrovamento"));
				context.getRequest().setAttribute("data_ritrovamento",(String) context.getRequest().getParameter("data_ritrovamento"));
	
				context.getRequest().setAttribute("flagFuoriRegione",(String) context.getRequest().getParameter("flagFuoriRegione"));
				context.getRequest().setAttribute("idRegioneProvenienza",(String) context.getRequest().getParameter("idRegioneProvenienza"));
				context.getRequest().setAttribute("noteAnagrafeFr",(String) context.getRequest().getParameter("noteAnagrafeFr"));
	
				context.getRequest().setAttribute("flagSenzaOrigine",(String) context.getRequest().getParameter("flagSenzaOrigine"));
				
				context.getRequest().setAttribute("idNazioneProvenienza",(String) context.getRequest().getParameter("idNazioneProvenienza"));
				context.getRequest().setAttribute("noteNazioneProvenienza",(String) context.getRequest().getParameter("noteNazioneProvenienza"));
				
				context.getRequest().setAttribute("flagAcquistoOnline",(String) context.getRequest().getParameter("flagAcquistoOnline"));
				context.getRequest().setAttribute("sitoWebAcquisto",(String) context.getRequest().getParameter("sitoWebAcquisto"));
				context.getRequest().setAttribute("noteAcquistoOnline",(String) context.getRequest().getParameter("noteAcquistoOnline"));
				/********************************************************************/
			}else{
				boolean or=false;
				if(thisAnimale.isFlagMancataOrigine()){ or=true;
					context.getRequest().setAttribute("flagSenzaOrigine","on");
				}
				if(!reg.getProvenienza_origine().equals("")){ or=true;
					context.getRequest().setAttribute("origine_da",reg.getProvenienza_origine());
					if(reg.getIdProprietarioProvenienza()>0){
						context.getRequest().setAttribute("tipo_origine","soggetto_fisico");
						
					}else{
						context.getRequest().setAttribute("tipo_origine","ritrovamento");
						context.getRequest().setAttribute("provincia_ritrovamento",reg.getProvincia_ritrovamento());
						context.getRequest().setAttribute("comune_ritrovamento",reg.getComune_ritrovamento());
						context.getRequest().setAttribute("indirizzo_ritrovamento",reg.getIndirizzo_ritrovamento());
						context.getRequest().setAttribute("data_ritrovamento",reg.getData_ritrovamento());
					}
				}
				if(reg.isFlag_anagrafe_estera()){ or=true;
					context.getRequest().setAttribute("origine_da","nazione_estera");
					context.getRequest().setAttribute("idNazioneProvenienza",reg.getNazione_estera());
					context.getRequest().setAttribute("noteNazioneProvenienza",reg.getNazione_estera_note());
				}
				if(reg.isFlag_anagrafe_fr()){ or=true;
					context.getRequest().setAttribute("flagFuoriRegione","on");
					context.getRequest().setAttribute("idRegioneProvenienza",reg.getRegione_anagrafe_fr());
					context.getRequest().setAttribute("noteAnagrafeFr",reg.getRegione_anagrafe_fr_note());
				}
				if(reg.isFlag_acquisto_online()){ or=true;
					context.getRequest().setAttribute("flagAcquistoOnline","on");
					context.getRequest().setAttribute("sitoWebAcquisto",reg.getSito_web_acquisto());
					context.getRequest().setAttribute("noteAcquistoOnline",reg.getSito_web_acquisto_note());

				}
				
				if(fr && fr_id > -1 && !or){	
					context.getRequest().setAttribute("flagFuoriRegione","on");
					context.getRequest().setAttribute("idRegioneProvenienza",fr_id);
					context.getRequest().setAttribute("noteAnagrafeFr",(fr_sindaco ? "Proprietario Sindaco" : ""));
					 }
				
				
			}

			
			
			User utenteInserimento = new User(db, thisAnimale.getIdUtenteInserimento());
			context.getRequest().setAttribute("utenteInserimento", utenteInserimento);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		return getReturn(context, "PrepareUpdate");
	}

	public String executeCommandUpdate(ActionContext context) throws SQLException {

		if (!hasPermission(context, "anagrafe_canina-anagrafe_canina-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		PreparedStatement pstMc = null;
		boolean recordInserted = false;
		boolean isValid = false;

		// Integer orgId = null;
		Animale newAnimale = (Animale) context.getRequest().getAttribute("Animale");
		Cane newCane = (Cane) context.getRequest().getAttribute("Cane");
		Gatto newGatto = (Gatto) context.getRequest().getAttribute("Gatto");
		Furetto newFuretto = (Furetto) context.getRequest().getAttribute("Furetto");
		Animale thisAnimale = null;

		try {

			UserBean user = (UserBean) context.getSession().getAttribute("User");
			String ip = user.getUserRecord().getIp();

			Thread t = Thread.currentThread();
			db = this.getConnection(context);
			switch (newAnimale.getIdSpecie()) {
			case Cane.idSpecie: {
				thisAnimale = (Animale) newCane;
				break;
			}
			case Gatto.idSpecie: {
				thisAnimale = (Animale) newGatto;
				break;
			}
			case Furetto.idSpecie: {
				thisAnimale = (Animale) newFuretto;
				break;
			}
			}
			thisAnimale.setIdUtenteModifica(user.getUserId());
			if (context.getRequest().getParameter("idProprietario") != null
					&& !context.getRequest().getParameter("idProprietario").equals("")
					&& !context.getRequest().getParameter("idProprietario").equals("-1")) {

				Operatore soggettoAdded = new Operatore();
				soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, new Integer(context.getRequest()
						.getParameter("idProprietario")).intValue());

				thisAnimale.setProprietario(soggettoAdded);

				Stabilimento stab = (Stabilimento) soggettoAdded.getListaStabilimenti().get(0);
				thisAnimale.setIdAslRiferimento(stab.getIdAsl());

			}

			if (context.getRequest().getParameter("idDetentore") != null
					&& !context.getRequest().getParameter("idDetentore").equals("")
					&& !context.getRequest().getParameter("idDetentore").equals("-1")
					&& Integer.parseInt(context.getRequest().getParameter("idDetentore")) == (thisAnimale
							.getIdDetentore())) {
				Operatore soggettoAdded = new Operatore();
				soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, new Integer(context.getRequest()
						.getParameter("idDetentore")).intValue());
				Stabilimento stab = (Stabilimento) soggettoAdded.getListaStabilimenti().get(0);
				LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
				
				
				
				// Stefano Squitieri 30/08/2016 - Tolto l'if in quanto ci siamo accorti che facendo una modifica qualsiasi che non impatta sul detentore
				// su un animale avente proprietario e detentore(privato) distinti veniva sovrascritto il detentore con il proprietario
				/*if (lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazionePrivato) {
					thisAnimale.setDetentore(thisAnimale.getProprietario());
					thisAnimale.setIdDetentore(new Integer(context.getRequest().getParameter("idProprietario"))
							.intValue());
				} else {*/
					thisAnimale.setDetentore(soggettoAdded);
					thisAnimale
							.setIdDetentore(new Integer(context.getRequest().getParameter("idDetentore")).intValue());
				//}
				// newCane.setDetentore(soggettoAdded);
			}
			//

			if (context.getRequest().getParameter("doContinue") != null
					&& !context.getRequest().getParameter("doContinue").equals("")
					&& context.getRequest().getParameter("doContinue").equals("false")) {

				context.getRequest().setAttribute("Cane", newCane);
				context.getRequest().setAttribute("Gatto", newGatto);
				context.getRequest().setAttribute("Furetto", newFuretto);
				context.getRequest().setAttribute("animale", thisAnimale);

				return executeCommandPrepareUpdate(context);
			}

			isValid = this.validateObject(context, db, thisAnimale);

			if (isValid) {

				ArrayList<CampoModificato> campi_modificati = new ArrayList<CampoModificato>();

				// Recupero i campi modificati per poterli salvare nel db per
				// tenere traccia delle modifiche
				Animale oldAnimale = new Animale(db, thisAnimale.getIdAnimale());
				switch (oldAnimale.getIdSpecie()) {
				case Cane.idSpecie: {
					campi_modificati = thisAnimale.checkModifiche(db, new Cane(db, thisAnimale.getIdAnimale()));
					break;
				}
				case Gatto.idSpecie: {
					campi_modificati = thisAnimale.checkModifiche(db, new Gatto(db, thisAnimale.getIdAnimale()));
					break;
				}
				case Furetto.idSpecie: {
					campi_modificati = thisAnimale.checkModifiche(db, new Furetto(db, thisAnimale.getIdAnimale()));
					break;
				}

				}

				// Registro le modifiche
				ModificaStatica modificaStatica = new ModificaStatica();

				modificaStatica.setIdAnimale(thisAnimale.getIdAnimale());
				modificaStatica.setIdSpecie(thisAnimale.getIdSpecie());
				modificaStatica.setIdUtente(user.getUserId());
				modificaStatica.setDataModifica(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
				modificaStatica.setNrCampiModificati(campi_modificati.size());
				modificaStatica.setListaCampiModificati(campi_modificati);
				modificaStatica.setMotivazioneModifica((String) context.getRequest()
						.getParameter("motivazioneModifica"));

				modificaStatica.insert(db);

				// Controllo se è stato inserito un nuovo microchip per creare
				// la registrazione associata
				String microchipOriginale = context.getRequest().getParameter("microchipOriginale");
				String dataMicrochipOriginale = context.getRequest().getParameter("dataMicrochipOriginale");
				int idVeterinarioMicrochipOriginale = new Integer(context.getRequest().getParameter(
						"idVeterinarioMicrochipOriginale"));
				// SE L'ANIMALE NON AVEVA MICROCHIP, CREA REGISTRAZIONE
				if (microchipOriginale != thisAnimale.getMicrochip()) // HD ha
																		// modificato
																		// microchip
				{

					if (microchipOriginale == null || ("").equals(microchipOriginale)) {
						// Inserisco la registrazione di inserimento microchip
						int result_insert = -1;
						EventoInserimentoMicrochip insert_microchip = new EventoInserimentoMicrochip();
						insert_microchip.setMicrochip(thisAnimale.getMicrochip());
						insert_microchip.setNumeroMicrochipAssegnato(thisAnimale.getMicrochip());
						insert_microchip.setDataInserimentoMicrochip(thisAnimale.getDataMicrochip());
						insert_microchip.setIdVeterinarioPrivatoInserimentoMicrochip(thisAnimale
								.getIdVeterinarioMicrochip());
						insert_microchip.setIdAnimale(thisAnimale.getIdAnimale());
						insert_microchip.setIdStatoOriginale(thisAnimale.getStato());
						insert_microchip.setIdTipologiaEvento(EventoInserimentoMicrochip.idTipologiaDB);
						insert_microchip.setSpecieAnimaleId(thisAnimale.getIdSpecie());
						insert_microchip.setEnteredby(user.getUserId());
						insert_microchip.setModifiedby(user.getUserId());
						insert_microchip.setIdAslRiferimento(thisAnimale.getIdAslRiferimento());
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
						insert_microchip
								.setNoteInternalUseOnly(("["
										+ dateFormat.format(new Date())
										+ "] "
										+ "Evento generato automaticamente a seguito di inserimento microchip dalla maschera di modifica. (Utente "
										+ user.getUserId() + ")"));
						insert_microchip.insert(db);

						// aggiorno la tabella microchips
						int resultUpdate = -1;
						pstMc = db
								.prepareStatement("update microchips set id_animale = ? ,flag_secondo_microchip = false ,id_specie = ? where microchip =? ");
						pstMc.setInt(1, thisAnimale.getIdAnimale());
						pstMc.setInt(2, thisAnimale.getIdSpecie());
						pstMc.setString(3, thisAnimale.getMicrochip());
						resultUpdate = pstMc.executeUpdate();
						// pstMc.close();
					} else {

						int resultUpdate = -1;
						pstMc = db
								.prepareStatement("update microchips set id_animale = -1 ,flag_secondo_microchip = false ,id_specie = -1 where microchip =? and trashed_date is null ");
						pstMc.setString(1, microchipOriginale);
						resultUpdate = pstMc.executeUpdate();
						// pstMc.close();

						// Devo aggiornare evento, microchips a priori,
						// CONTRIBUTI, vam, GISA
						EventoInserimentoMicrochip eventoInsMicrochip = new EventoInserimentoMicrochip();
						eventoInsMicrochip.GetMicrochipAttivoByIdAnimale(db, thisAnimale.getIdAnimale());
						eventoInsMicrochip.setNumeroMicrochipAssegnato(thisAnimale.getMicrochip());
						// eventoInsMicrochip.setNoteInternalUseOnly("Registrazione modificata da help desk, riferimento nr modifica"
						// + modificaStatica.getIdModifica());

						eventoInsMicrochip.setMicrochip(thisAnimale.getMicrochip());
						eventoInsMicrochip.setModifiedby(this.getUserId(context));

						if(context.getRequest().getParameter("dataMicrochip")!=null &&  !((String) context.getRequest().getParameter("dataMicrochip")).equals(""))
							eventoInsMicrochip.setDataInserimentoMicrochip((String) context.getRequest().getParameter("dataMicrochip"));
						else
							eventoInsMicrochip.setDataInserimentoMicrochip(thisAnimale.getDataMicrochip());
						
						eventoInsMicrochip.update(db);

						pstMc = db
								.prepareStatement("update microchips set id_animale = ? ,flag_secondo_microchip = false ,id_specie = ? where microchip =? and trashed_date is null ");
						pstMc.setInt(1, thisAnimale.getIdAnimale());
						pstMc.setInt(2, thisAnimale.getIdSpecie());
						pstMc.setString(3, thisAnimale.getMicrochip());
						resultUpdate = pstMc.executeUpdate();
						pstMc.close();

						ContributiSterilizzazioni.updateMicrochip(db, thisAnimale.getMicrochip(), microchipOriginale);

						// Thread t = Thread.currentThread();

						Connection connectionVam = GestoreConnessioni.getConnectionVam(MethodsUtils.getNomeMetodo(t));

						String insert = "select * from public_functions.aggiorna_microchip(?, ?)";

						int i = 0;
						PreparedStatement pst = connectionVam.prepareStatement(insert);
						pst.setString(++i, microchipOriginale);
						pst.setString(++i, thisAnimale.getMicrochip());

						pst.execute();
						pst.close();
						connectionVam.close();

						// Aggiornamento GISA
						// String dbName =
						// ApplicationProperties.getProperty("dbnameGisa");
						// String username =
						// ApplicationProperties.getProperty("usernameDbGisa");
						// String pwd
						// =ApplicationProperties.getProperty("passwordDbGisa");
						// String host =
						// InetAddress.getByName("hostDbGisa").getHostAddress();
						//
						//
						// Connection dbGisa = DbUtil.getConnection(dbName,
						// username,
						// pwd, host);

						Connection dbGisa = GestoreConnessioni.getConnectionGisa();

						insert = "select * from aggiorna_microchip(?, ?)";

						i = 0;
						pst = dbGisa.prepareStatement(insert);
						pst.setString(++i, microchipOriginale);
						pst.setString(++i, thisAnimale.getMicrochip());

						pst.execute();
						pst.close();

						dbGisa.close();

						// Aggiornamento esiti Leish

						Leish.aggiornaMicrochipEsito(db, microchipOriginale, thisAnimale.getMicrochip());

						// Aggiorno riferimento mcs in tabella evento x tutti
						// gli eventi fino ad ora inseriti

						EventoList listaEventi = new EventoList();
						listaEventi.setId_animale(thisAnimale.getIdAnimale());
						listaEventi.buildList(db);

						Iterator iterator = listaEventi.iterator();

						while (iterator.hasNext()) {
							SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
							Evento eventoCorrente = (Evento) iterator.next();
							eventoCorrente.setMicrochip(thisAnimale.getMicrochip());
							eventoCorrente.setModifiedby(user.getUserId());
							eventoCorrente.setNoteInternalUseOnly("[" + dateFormat.format(new Date())
									+ "] Registrazione modificata da help desk, riferimento nr modifica"
									+ modificaStatica.getIdModifica());
							eventoCorrente.update(db);
						}

					}

					// se il valore restituito è 0, il Mc non è presente e va
					// inserito (ELIMINATO INSERIMENTO IN TABELLA MCs A PRIORI)
					// if (resultUpdate==0){
					// int i=0;
					// pstMc = db
					// .prepareStatement("insert into microchips values (?,?,6,-1,?,?,true,true,null,false,null,?, ?, false, false);");
					// pstMc.setString(++i, thisAnimale.getMicrochip());
					// pstMc.setInt(++i, thisAnimale.getIdAslRiferimento());
					// pstMc.setInt(++i, user.getUserId());
					// pstMc.setInt(++i, user.getUserId());
					// pstMc.setInt(++i, thisAnimale.getIdAnimale());
					// pstMc.setInt(++i, thisAnimale.getIdSpecie());
					// pstMc.execute();
					// pstMc.close();
					// }
				}
				// posso aggiornare solo il veterinario chippatore
				else if (idVeterinarioMicrochipOriginale != thisAnimale.getIdVeterinarioMicrochip()) {
					EventoInserimentoMicrochip update_microchip = new EventoInserimentoMicrochip();
					update_microchip.GetMicrochipAttivoByIdAnimale(db, thisAnimale.getIdAnimale());
					// update_microchip.setMicrochip(thisAnimale.getMicrochip());
					// update_microchip.setNumeroMicrochipAssegnato(thisAnimale.getMicrochip());
					// update_microchip.setDataInserimentoMicrochip(thisAnimale.getDataMicrochip());
					update_microchip.setIdVeterinarioPrivatoInserimentoMicrochip(thisAnimale
							.getIdVeterinarioMicrochip());
					update_microchip.setIdAnimale(thisAnimale.getIdAnimale());
					// insert_microchip.setIdStatoOriginale(thisAnimale.getStato());
					// insert_microchip.setIdTipologiaEvento(EventoInserimentoMicrochip.idTipologiaDB);
					// insert_microchip.setSpecieAnimaleId(thisAnimale.getIdSpecie());
					// insert_microchip.setEnteredby(user.getUserId());
					update_microchip.setModifiedby(user.getUserId());
					// insert_microchip.setIdAslRiferimento(thisAnimale.getIdAslRiferimento());
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
					String oldNoteInternalUseOnly = update_microchip.getNoteInternalUseOnly();
					if (oldNoteInternalUseOnly == null)
						oldNoteInternalUseOnly = "";
					update_microchip
							.setNoteInternalUseOnly(("["
									+ dateFormat.format(new Date())
									+ "] "
									+ "Evento modificato automaticamente a seguito di modifica veterinario chippatore (da "
									+ idVeterinarioMicrochipOriginale + " a " + thisAnimale.getIdVeterinarioMicrochip()
									+ ") dalla maschera di modifica. (Utente " + user.getUserId() + ")" + oldNoteInternalUseOnly));
					if (update_microchip.getIdEvento() > 0)
						update_microchip.update(db);
				}
				/*
				 * else if
				 * (!microchipOriginale.equals(thisAnimale.getMicrochip())){
				 * EventoInserimentoMicrochip update_microchip = new
				 * EventoInserimentoMicrochip();
				 * update_microchip.GetMicrochipAttivoByIdAnimale(db,
				 * thisAnimale.getIdAnimale());
				 * update_microchip.setMicrochip(thisAnimale.getMicrochip());
				 * update_microchip
				 * .setNumeroMicrochipAssegnato(thisAnimale.getMicrochip());
				 * update_microchip
				 * .setDataInserimentoMicrochip(thisAnimale.getDataMicrochip());
				 * update_microchip
				 * .setIdVeterinarioPrivatoInserimentoMicrochip(thisAnimale
				 * .getIdVeterinarioMicrochip());
				 * update_microchip.setIdAnimale(thisAnimale.getIdAnimale());
				 * //insert_microchip
				 * .setIdStatoOriginale(thisAnimale.getStato());
				 * //insert_microchip
				 * .setIdTipologiaEvento(EventoInserimentoMicrochip
				 * .idTipologiaDB);
				 * //insert_microchip.setSpecieAnimaleId(thisAnimale
				 * .getIdSpecie());
				 * //insert_microchip.setEnteredby(user.getUserId());
				 * update_microchip.setModifiedby(user.getUserId());
				 * //insert_microchip
				 * .setIdAslRiferimento(thisAnimale.getIdAslRiferimento());
				 * update_microchip.setNoteInternalUseOnly((
				 * "Evento modificato d'ufficio a seguito di modifica microchip dalla maschera di modifica."
				 * )); update_microchip.update(db); }
				 */

				// Controllo se è cambiato il passaporto per aggiornare anche la
				// registrazione associata
				// String passaportoOriginale =
				// context.getRequest().getParameter("numeroPassaportoOriginale");
				// String dataPassaportoOriginale =
				// context.getRequest().getParameter("dataRilascioPassaportoOriginale");
				int result = 1;
				// SE L'ANIMALE AVEVA PASSAPORTO E DATA PASSAPORTO, CONTROLLA
				// CAMBIAMENTI, ALTRIMENTI AGGIORNA
				if (thisAnimale.getNumeroPassaporto() != null) {
					result = -1;
					String dataScadenzaPassaporto = context.getRequest().getParameter("dataScadenzaPassaporto");
					EventoRilascioPassaporto rilascio = new EventoRilascioPassaporto();
					rilascio.GetPassaportoAttivoByIdAnimale(db, thisAnimale.getIdAnimale());

					if (rilascio.getIdEvento() > 0) {
						if (!thisAnimale.getNumeroPassaporto().equals(rilascio.getNumeroPassaporto())
								|| !thisAnimale.getDataRilascioPassaporto()
										.equals(rilascio.getDataRilascioPassaporto())
								|| !dataScadenzaPassaporto.equals(rilascio.getDataScadenzaPassaporto())) {
							rilascio.setNumeroPassaporto(thisAnimale.getNumeroPassaporto());
							rilascio.setDataRilascioPassaporto(thisAnimale.getDataRilascioPassaporto());
							rilascio.setDataScadenzaPassaporto(dataScadenzaPassaporto);
							rilascio.setModifiedby(user.getUserId());

							SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
							String oldNoteInternalUseOnly = rilascio.getNoteInternalUseOnly();
							if (oldNoteInternalUseOnly == null)
								oldNoteInternalUseOnly = "";
							rilascio.setNoteInternalUseOnly(("["
									+ dateFormat.format(new Date())
									+ "] "
									+ "Evento modificato automaticamente a seguito di modifica passaporto dalla maschera di modifica. (Utente "
									+ user.getUserId() + ")" + oldNoteInternalUseOnly));
							result = rilascio.update(db);
						}
					}
				}

				String dataSterilizzazioneOriginale = context.getRequest().getParameter("dataSterilizzazioneOriginale");
				// SE E' CAMBIATA LA DATA DI STERILIZZAZIONE, AGGIORNA L'EVENTO
				if (dataSterilizzazioneOriginale != null && !dataSterilizzazioneOriginale.equals("")
						&& !dataSterilizzazioneOriginale.equals("null"))
					if (!thisAnimale.getDataSterilizzazione().toString().equals(dataSterilizzazioneOriginale)) {
						result = -1;
						EventoSterilizzazione ster = new EventoSterilizzazione();
						ster.GetSterilizzazioneByIdAnimale(db, thisAnimale.getIdAnimale());
						if (ster.getIdEvento() > 0) {
							ster.setDataSterilizzazione(thisAnimale.getDataSterilizzazione());
							ster.setModifiedby(user.getUserId());

							SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
							String oldNoteInternalUseOnly = ster.getNoteInternalUseOnly();
							if (oldNoteInternalUseOnly == null)
								oldNoteInternalUseOnly = "";
							ster.setNoteInternalUseOnly(("["
									+ dateFormat.format(new Date())
									+ "] "
									+ "Evento modificato automaticamente a seguito di modifica data sterilizzazione (da "
									+ dataSterilizzazioneOriginale + "  a " + thisAnimale.getDataSterilizzazione()
									+ ") dalla maschera di modifica. (Utente " + user.getUserId() + ")" + oldNoteInternalUseOnly));
							result = ster.update(db);
						}
					}

				if (context.getRequest().getParameter("aggiorna_dati_iscrizione") != null) {
					// Devo aggiornare i dati di prima Iscrizione e tutti i dati
					// su proprietario / detentore corrente relativi alla storia
					// dell'animale

					EventoRegistrazioneBDU reg_bdu = (EventoRegistrazioneBDU) context.getRequest().getAttribute(
							"EventoRegistrazioneBDU");

					EventoRegistrazioneBDU reg = EventoRegistrazioneBDU.getEventoRegistrazione(db,
							thisAnimale.getIdAnimale());
					reg.setIdProprietarioCorrente(thisAnimale.getIdProprietario());
					reg.setIdDetentoreCorrente(thisAnimale.getIdDetentore());
					reg.setIdProprietario(thisAnimale.getIdProprietario());
					reg.setIdDetentore(thisAnimale.getIdDetentore());
					// reg.setNoteInternalUseOnly("Registrazione modificata da help desk in modifica animale");
					reg.setModifiedby(user.getUserId());
					reg.updateDatiProprietarioDetentore(db);

					// DEVO INOLTRE MODIFICARE DETENTORE CORRENTE E PROPRIETARIO
					// CORRENTE DI TUTTI GLI EVENTI FINO AD ORA INSERITI

					EventoList listaEventi = new EventoList();
					listaEventi.setId_animale(thisAnimale.getIdAnimale());
					listaEventi.buildList(db);

					Iterator i = listaEventi.iterator();

					while (i.hasNext()) {
						Evento eventoCorrente = (Evento) i.next();
						eventoCorrente.setIdProprietarioCorrente(thisAnimale.getIdProprietario());
						eventoCorrente.setIdDetentoreCorrente(thisAnimale.getIdDetentore());
						eventoCorrente.setModifiedby(user.getUserId());
						eventoCorrente
								.setNoteInternalUseOnly("Registrazione modificata da help desk, riferimento nr modifica "
										+ modificaStatica.getIdModifica());
						eventoCorrente.update(db);
					}

				}
				
				// CONTROLLO SELEZIONE ORIGINE ANIMALE
				EventoRegistrazioneBDU reg_bdu = EventoRegistrazioneBDU.getEventoRegistrazione(db,
						thisAnimale.getIdAnimale());
				reg_bdu.setValoriDefaultOrigine();
				//String anagrafe_fuori_nazione=(String)context.getRequest().getParameter("flagFuoriNazione");
				String anagrafe_fuori_regione=(String)context.getRequest().getParameter("flagFuoriRegione");
				String acquisto_online=(String)context.getRequest().getParameter("flagAcquistoOnline");
				String origine=(String)context.getRequest().getParameter("origine_da");
				
				if(origine!=null){
					if(origine.equals("nazione_estera")){
						reg_bdu.setFlag_anagrafe_estera(true);  
						reg_bdu.setNazione_estera((String)context.getRequest().getParameter("idNazioneProvenienza"));
						reg_bdu.setNazione_estera_note((String)context.getRequest().getParameter("noteNazioneProvenienza"));
					}else{
						reg_bdu.setProvenienza_origine(origine);
						String tipo_origine=(String)context.getRequest().getParameter("tipo_origine");
						if(tipo_origine!=null){
							if(tipo_origine.equals("soggetto_fisico")){
								if (context.getRequest().getParameter("idProprietarioProvenienza") != null	&& !context.getRequest().getParameter("idProprietarioProvenienza").equals("-1"))
									reg_bdu.setIdProprietarioProvenienza(Integer.parseInt((String)context.getRequest().getParameter("idProprietarioProvenienza")));
								}else{
									reg_bdu.setRegione_ritrovamento(context.getRequest().getParameter("regione_ritrovamento"));
									reg_bdu.setProvincia_ritrovamento(context.getRequest().getParameter("provincia_ritrovamento"));
									reg_bdu.setComune_ritrovamento(context.getRequest().getParameter("comune_ritrovamento"));
									reg_bdu.setIndirizzo_ritrovamento(context.getRequest().getParameter("indirizzo_ritrovamento"));
									reg_bdu.setData_ritrovamento(context.getRequest().getParameter("data_ritrovamento"));
								}						
						}
					}
				}
				
				if (anagrafe_fuori_regione != null && anagrafe_fuori_regione.equals("on")){
					reg_bdu.setFlag_anagrafe_fr(true);  
					reg_bdu.setRegione_anagrafe_fr((String)context.getRequest().getParameter("idRegioneProvenienza"));
					reg_bdu.setRegione_anagrafe_fr_note((String)context.getRequest().getParameter("noteAnagrafeFr"));
				}		
				
				if (acquisto_online != null && acquisto_online.equals("on")){
					reg_bdu.setFlag_acquisto_online(true);  
					reg_bdu.setSito_web_acquisto((String)context.getRequest().getParameter("sitoWebAcquisto"));
					reg_bdu.setSito_web_acquisto_note((String)context.getRequest().getParameter("noteAcquistoOnline"));
				}

				reg_bdu.updateOrigine(db);

				
				

				thisAnimale.update(db);
				
				if(ApplicationProperties.getProperty("abilitato_sinaaf").equals("true"))
				{
				String esitoInvioSinaaf[] = new Sinaaf().inviaInSinaaf(db, getUserId(context),reg_bdu.getIdEvento()+"", "evento");
				if(esitoInvioSinaaf[0]!=null)
					context.getRequest().setAttribute("messaggio", esitoInvioSinaaf[0]);
				if(esitoInvioSinaaf[1]!=null)
					context.getRequest().setAttribute("Error", esitoInvioSinaaf[1]);
				}
				
				context.getRequest().setAttribute("idSpecie", new Integer(thisAnimale.getIdSpecie()).toString());
				context.getRequest().setAttribute("animaleId", new Integer(thisAnimale.getIdAnimale()).toString());

			}
			/*
			 * Parametri necessari per l'invocazione della jsp go_to_detail.jsp
			 * invocata quando l'inserimento va a buon fine("InsertOK")
			 */
			context.getRequest().setAttribute("commandD", "AnimaleAction.do?command=Details");
			context.getRequest().setAttribute("animale_id", "&orgId=" + thisAnimale.getIdAnimale());

		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			db.rollback();
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			// db.close();
			this.freeConnection(context, db, pstMc);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");
		if (recordInserted) {
			String target = context.getRequest().getParameter("target");
			if (context.getRequest().getParameter("popup") != null) {
				return ("ClosePopupOK");
			}

		}

		return (executeCommandDetails(context));

		// return ("InsertOK");

	}

	public String executeCommandListaModificheStatiche(ActionContext context) {

		Connection db = null;

		try {

			// Prepare pagedListInfo
			PagedListInfo searchListInfo = this.getPagedListInfo(context, "modificheListInfo");
			searchListInfo.setLink("AnimaleAction.do?command=ListaModificheStatiche");
			searchListInfo.setListView("all");

			Thread t = Thread.currentThread();
			db = this.getConnection(context);

			String tempanimaleId = context.getRequest().getParameter("animaleId");
			if (tempanimaleId == null) {
				tempanimaleId = (String) context.getRequest().getAttribute("animaleId");
			}

			String specieAnimaleId = context.getRequest().getParameter("idSpecie");
			if (specieAnimaleId == null) {
				specieAnimaleId = (String) context.getRequest().getAttribute("idSpecie");
			}

			Integer tempid = null;

			if (tempanimaleId != null) {
				tempid = Integer.parseInt(tempanimaleId);
			}

			ModificaStaticaList listaModifiche = new ModificaStaticaList();
			listaModifiche.setIdAnimale(tempid);
			listaModifiche.buildList(db);

			context.getRequest().setAttribute("listaModifiche", listaModifiche);

			Animale thisAnimale = new Animale(db, tempid);
			context.getRequest().setAttribute("animaleDettaglio", thisAnimale);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("ListaModificheOK");
	}

	public String executeCommandDetailsModifica(ActionContext context) {
		if (!hasPermission(context, "anagrafe_canina-anagrafe_canina-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);

		try {

			String idModifica = context.getRequest().getParameter("idModifica");
			if (idModifica == null) {
				idModifica = (String) context.getRequest().getAttribute("idModifica");
			}

			Integer tempid = null;

			if (idModifica != null) {
				tempid = Integer.parseInt(idModifica);

				Thread t = Thread.currentThread();
				db = this.getConnection(context);

				ModificaStatica thisModifica = new ModificaStatica(db, tempid);
				context.getRequest().setAttribute("modifica", thisModifica);

				Animale thisAnimale = new Animale(db, thisModifica.getIdAnimale());
				context.getRequest().setAttribute("animaleDettaglio", thisAnimale);

			}

			// lookups

			LookupList tagliaList = new LookupList(db, "lookup_taglia");
			tagliaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("tagliaList", tagliaList);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);

			LookupList regioniList = new LookupList(db, "lookup_regione");
			regioniList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("regioniList", regioniList);

			LookupList razzaList = new LookupList(db, "lookup_razza");
			razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("razzaList", razzaList);

			LookupList mantelloList = new LookupList(db, "lookup_mantello");
			mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("mantelloList", mantelloList);

			LookupList statoList = new LookupList(db, "lookup_tipologia_stato");
			context.getRequest().setAttribute("statoList", statoList);

			LookupList esitoControlloList = new LookupList(db, "lookup_esito_controlli");
			context.getRequest().setAttribute("esitoControlloList", esitoControlloList);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		return getReturn(context, "DetailsModifica");
	}

	/**
	 * 
	 * @param context
	 *            Salva a DB gli attributi della classe
	 */
	public String executeCommandSaveFields(ActionContext context) {
		if (!hasPermission(context, "anagrafe_canina-anagrafe_canina-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;

		Animale thisAnimale = null;

		try {
			Thread t = Thread.currentThread();
			db = this.getConnection(context);

			thisAnimale = new Animale(db, 5699880);

			Cane cane = new Cane(db, 5699880);

			thisAnimale.saveFieldsDb(db);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return "OK";
	}

	public String executeCommandPrintRichiestaIscrizione(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina-documenti-view")) {
			return ("PermissionError");
		}

		Connection db = null;

		Cane thisCane = null;
		Gatto thisGatto = null;
		Furetto thisFuretto = null;
		int idAnimale = -1;
		int idSpecie = -1;

		try {

			Thread t = Thread.currentThread();
			db = this.getConnection(context);

			// String HOST_CORRENTE = context.getRequest().getLocalAddr();

			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
			// String HOST_CORRENTE=
			// ApplicationProperties.getProperty("APP_HOST_CANINA");
			/*
			 * String SERVER_BDU =
			 * InetAddress.getByName("hostAppBduPublic").getHostAddress();
			 * String APPLICATION_PORT = prefs.get("APPLICATION.PORT"); String
			 * APPLICATION_NAME = prefs.get("APPLICATION.NAME");
			 * 
			 * String url =
			 * "http://".concat(SERVER_BDU).concat(":").concat(APPLICATION_PORT
			 * ).concat("/").concat(APPLICATION_NAME).concat("/");
			 * 
			 * url="";context.getRequest().setAttribute("SERVER_BDU", url);
			 * 
			 * System.out.println(url);
			 */
			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			if ((String) context.getRequest().getParameter("isEmpty") == null) {

				SystemStatus systemStatus = this.getSystemStatus(context);
				String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
				String idSpecieString = (String) context.getRequest().getParameter("idSpecie");

				if (idAnimaleString != null && !("").equals(idAnimaleString)) {
					idAnimale = new Integer(idAnimaleString).intValue();
				}

				if (idSpecieString != null && !("").equals(idSpecieString)) {
					idSpecie = new Integer(idSpecieString).intValue();
				}

				switch (idSpecie) {
				case 1: {
					thisCane = new Cane(db, idAnimale);
					context.getRequest().setAttribute("Cane", thisCane);

					break;
				}

				case 2: {
					thisGatto = new Gatto(db, idAnimale);
					context.getRequest().setAttribute("Gatto", thisGatto);
					break;
				}
				case 3: {
					thisFuretto = new Furetto(db, idAnimale);
					context.getRequest().setAttribute("Furetto", thisFuretto);
					break;
				}
				default: {
					break;
				}
				}

				EventoRegistrazioneBDU dati_registrazione = new EventoRegistrazioneBDU();
				dati_registrazione = dati_registrazione.getEventoRegistrazione(db, idAnimale);

				context.getRequest().setAttribute("eventoF", dati_registrazione);

				
				LineaProduttiva linea_proprietario = new LineaProduttiva(db, dati_registrazione.getIdProprietario());
				context.getRequest().setAttribute("linea_proprietario", linea_proprietario);

				Operatore proprietario = new Operatore();
				proprietario.queryRecordOperatorebyIdLineaProduttiva(db, dati_registrazione.getIdProprietario());
				context.getRequest().setAttribute("proprietario", proprietario);

				LookupList specieList = new LookupList(db, "lookup_specie");
				specieList.addItem(-1, "--Tutti--");
				context.getRequest().setAttribute("specieList", specieList);

				LookupList razzaList = new LookupList();
				razzaList.setTable("lookup_razza");
				razzaList.setIdSpecie(idSpecie);
				razzaList.buildList(db);
				razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("razzaList", razzaList);

				LookupList tagliaList = new LookupList(db, "lookup_taglia");
				tagliaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("tagliaList", tagliaList);

				LookupList mantelloList = new LookupList(db, "lookup_mantello");
				mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("mantelloList", mantelloList);

				ComuniAnagrafica c = new ComuniAnagrafica();
				// PER ORA PRENDO TUTTI I COMUNI E NON SOLO QUELLI RELATIVI
				// ALL'ASL
				// UTENTE
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, -1);
				LookupList comuniList = new LookupList(listaComuni, -1);
				comuniList.addItem(-1, "");
				context.getRequest().setAttribute("comuniList", comuniList);
				
				LookupList provinceList = new LookupList(db, "lookup_province");
				provinceList.addItem(-1, "Seleziona provincia");
				context.getRequest().setAttribute("provinceList", provinceList);
				
				LookupList regioniList = new LookupList(db, "lookup_regione");
				regioniList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				regioniList.removeElementByCode(15);
				context.getRequest().setAttribute("regioniList", regioniList);
				
				ComuniAnagrafica c_all = new ComuniAnagrafica();
				ArrayList<ComuniAnagrafica> listaComuni_all = c.buildList(db, -1, -1);
				LookupList comuniList_all = new LookupList(listaComuni_all, -1);
				comuniList_all.addItem(-1, "--Seleziona--");
				context.getRequest().setAttribute("comuniList_all", comuniList_all);

				LookupList nazioniList = new LookupList(db,	"lookup_nazioni");
				nazioniList.addItem(-1, "--Seleziona--");
				nazioniList.removeElementByCode(106);
				context.getRequest().setAttribute("nazioniList", nazioniList);


			}

			else {
				return getReturn(context, "viewRichiestaPrimaIscrizioneEmpty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "viewRichiestaPrimaIscrizione");

	}

	public String executeCommandPrintCertificatoIscrizione(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina-documenti-view")) 
		{
			return ("PermissionError");
		}

		Connection db = null;

		Cane thisCane = null;
		Gatto thisGatto = null;
		Furetto thisFuretto = null;
		int idAnimale = -1;
		int idSpecie = -1;

		try 
		{
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
			Thread t = Thread.currentThread();
			db = this.getConnection(context);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			if ((String) context.getRequest().getParameter("isEmpty") == null) 
			{

				SystemStatus systemStatus = this.getSystemStatus(context);
				String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
				String idSpecieString = (String) context.getRequest().getParameter("idSpecie");

				if (idAnimaleString != null && !("").equals(idAnimaleString)) 
				{
					idAnimale = new Integer(idAnimaleString).intValue();
				}

				if (idSpecieString != null && !("").equals(idSpecieString)) 
				{
					idSpecie = new Integer(idSpecieString).intValue();
				}

				Animale thisAnimale = new Animale(db, idAnimale);

				switch (idSpecie) 
				{
				case 1: 
				{
					thisCane = new Cane(db, idAnimale);
					context.getRequest().setAttribute("Cane", thisCane);

					break;
				}

				case 2: 
				{
					thisGatto = new Gatto(db, idAnimale);
					context.getRequest().setAttribute("Gatto", thisGatto);
					break;
				}
				case 3: 
				{
					thisFuretto = new Furetto(db, idAnimale);
					context.getRequest().setAttribute("Furetto", thisFuretto);
					break;
				}

				default: 
				{
					break;
				}
				}

				Operatore proprietario = new Operatore();
				proprietario.queryRecordOperatorebyIdLineaProduttiva(db, thisAnimale.getIdProprietario());
				context.getRequest().setAttribute("proprietario", proprietario);

				Operatore detentore = new Operatore();
				detentore.queryRecordOperatorebyIdLineaProduttiva(db, thisAnimale.getIdDetentore());
				context.getRequest().setAttribute("detentore", detentore);

				LookupList specieList = new LookupList(db, "lookup_specie");
				specieList.addItem(-1, "--Tutti--");
				context.getRequest().setAttribute("specieList", specieList);

				LookupList razzaList = new LookupList();
				razzaList.setTable("lookup_razza");
				razzaList.setIdSpecie(idSpecie);
				razzaList.buildList(db);
				razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("razzaList", razzaList);

				LookupList tagliaList = new LookupList(db, "lookup_taglia");
				tagliaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("tagliaList", tagliaList);

				LookupList mantelloList = new LookupList(db, "lookup_mantello");
				mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("mantelloList", mantelloList);

				ComuniAnagrafica c = new ComuniAnagrafica();
				// PER ORA PRENDO TUTTI I COMUNI E NON SOLO QUELLI RELATIVI
				// ALL'ASL
				// UTENTE
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, 1);
				LookupList comuniList = new LookupList(listaComuni, -1);
				comuniList.addItem(-1, "");
				context.getRequest().setAttribute("comuniList", comuniList);

			}

			else 
			{
				return getReturn(context, "viewCertificatoPrimaIscrizioneEmpty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "viewCertificatoPrimaIscrizione");

	}
	
	
	
	
	public String executeCommandPrintSchedaAdozioneCani(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina-documenti-view")) {
			return ("PermissionError");
		}

		Connection db = null;

		Cane thisCane = null;
		Gatto thisGatto = null;
		Furetto thisFuretto = null;
		int idAnimale = -1;
		int idSpecie = -1;

		try 
		{
			// String HOST_CORRENTE = context.getRequest().getLocalAddr();
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
			Thread t = Thread.currentThread();
			db = this.getConnection(context);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			SystemStatus systemStatus = this.getSystemStatus(context);
			String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
			String idSpecieString = (String) context.getRequest().getParameter("idSpecie");

			if (idAnimaleString != null && !("").equals(idAnimaleString)) 
			{
				idAnimale = new Integer(idAnimaleString).intValue();
			}

			if (idSpecieString != null && !("").equals(idSpecieString)) 
			{
				idSpecie = new Integer(idSpecieString).intValue();
			}

			Animale thisAnimale = new Animale(db, idAnimale);

			switch (idSpecie) 
			{
			case 1: 
			{
				thisCane = new Cane(db, idAnimale);
				context.getRequest().setAttribute("Cane", thisCane);

				break;
			}

			case 2: 
			{
				thisGatto = new Gatto(db, idAnimale);
				context.getRequest().setAttribute("Gatto", thisGatto);
				break;
			}
			case 3: 
			{
				thisFuretto = new Furetto(db, idAnimale);
				context.getRequest().setAttribute("Furetto", thisFuretto);
				break;
			}

			default: 
			{
				break;
			}
			}

			Operatore proprietario = new Operatore();
			proprietario.queryRecordOperatorebyIdLineaProduttiva(db, thisAnimale.getIdProprietario());
			context.getRequest().setAttribute("proprietario", proprietario);

			Operatore detentore = new Operatore();
			detentore.queryRecordOperatorebyIdLineaProduttiva(db, thisAnimale.getIdDetentore());
			context.getRequest().setAttribute("detentore", detentore);

			LookupList specieList = new LookupList(db, "lookup_specie");
			specieList.addItem(-1, "--Tutti--");
			context.getRequest().setAttribute("specieList", specieList);

			SchedaAdozione scheda = new SchedaAdozione();
			ArrayList<SchedaAdozione> schede = scheda.getByIdAnimale(db, thisAnimale.getIdAnimale());
			context.getRequest().setAttribute("schede", schede);
			if(schede.size()>0)
			{
				Valutazione valutazione = scheda.getValutazione(db,thisAnimale.getIdAnimale());
				context.getRequest().setAttribute("valutazione", valutazione);
			}	



		} catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			this.freeConnection(context, db);
		}
		return getReturn(context, "viewSchedaAdozioneCani");

	}
	
	
	public String executeCommandPrintSchedaMorsicatura(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina-documenti-view")) 
		{
			return ("PermissionError");
		}

		Connection db = null;

		Cane thisCane = null;
		Gatto thisGatto = null;
		Furetto thisFuretto = null;
		int idAnimale = -1;
		int idSpecie = -1;

		try 
		{
			// String HOST_CORRENTE = context.getRequest().getLocalAddr();
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
			Thread t = Thread.currentThread();
			db = this.getConnection(context);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			SystemStatus systemStatus = this.getSystemStatus(context);
			String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
			String idSpecieString = (String) context.getRequest().getParameter("idSpecie");
			String idSchedaString = (String) context.getRequest().getParameter("idScheda");

			if (idAnimaleString != null && !("").equals(idAnimaleString)) 
			{
				idAnimale = new Integer(idAnimaleString).intValue();
			}
			
			if (idSpecieString != null && !("").equals(idSpecieString)) 
			{
				idSpecie = new Integer(idSpecieString).intValue();
			}

			Animale thisAnimale = new Animale(db, idAnimale);

			switch (idSpecie) 
			{
			case 1: 
			{
				thisCane = new Cane(db, idAnimale);
				context.getRequest().setAttribute("Cane", thisCane);

				break;
			}

			case 2: 
			{
				thisGatto = new Gatto(db, idAnimale);
				context.getRequest().setAttribute("Gatto", thisGatto);
				break;
			}
			case 3: 
			{
				thisFuretto = new Furetto(db, idAnimale);
				context.getRequest().setAttribute("Furetto", thisFuretto);
				break;
			}

			default: 
			{
				break;
			}
			}

			Operatore proprietario = new Operatore();
			proprietario.queryRecordOperatorebyIdLineaProduttiva(db, thisAnimale.getIdProprietario());
			context.getRequest().setAttribute("proprietario", proprietario);

			Operatore detentore = new Operatore();
			detentore.queryRecordOperatorebyIdLineaProduttiva(db, thisAnimale.getIdDetentore());
			context.getRequest().setAttribute("detentore", detentore);

			LookupList specieList = new LookupList(db, "lookup_specie");
			specieList.addItem(-1, "--Tutti--");
			context.getRequest().setAttribute("specieList", specieList);

			ArrayList<SchedaMorsicatura> schede = new SchedaMorsicatura().getByIdAnimale(db, idAnimale);
			
			//if exist schedamorsicatura per il cane
			if(!schede.isEmpty())
			{
				SchedaMorsicatura scheda = schede.get(0);
				ArrayList<SchedaMorsicaturaRecords> records = new SchedaMorsicaturaRecords().getByIdScheda(db, scheda.getId());
				scheda.setRecords(records);
				context.getRequest().setAttribute("scheda", scheda);
				
				org.aspcfs.modules.schedaMorsicatura.base.Valutazione valutazione = scheda.getValutazione(db,scheda.getId());
				context.getRequest().setAttribute("valutazione", valutazione);
			}

		} catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			this.freeConnection(context, db);
		}
		return getReturn(context, "viewSchedaMorsicatura");

	}
	
	
	public String executeCommandPrintAutocertificazioneMancataOrigineAnimale(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina-documenti-view")) {
			return ("PermissionError");
		}

		Connection db = null;

		Cane thisCane = null;
		Gatto thisGatto = null;
		Furetto thisFuretto = null;
		int idAnimale = -1;
		int idSpecie = -1;

		try {
			// String HOST_CORRENTE = context.getRequest().getLocalAddr();
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
			Thread t = Thread.currentThread();
			db = this.getConnection(context);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			if ((String) context.getRequest().getParameter("isEmpty") == null) {

				SystemStatus systemStatus = this.getSystemStatus(context);
				String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
				String idSpecieString = (String) context.getRequest().getParameter("idSpecie");
				
				String microchip = (String) context.getRequest().getParameter("microchip");
				String idProprietario = (String) context.getRequest().getParameter("idLinea");
				
				
				
				if (idAnimaleString != null && !("").equals(idAnimaleString)) {
					idAnimale = new Integer(idAnimaleString).intValue();
				}

				if (idSpecieString != null && !("").equals(idSpecieString)) {
					idSpecie = new Integer(idSpecieString).intValue();
				}

				
				if(idAnimale>-1){
					Animale thisAnimale = new Animale(db, idAnimale);
	
				
					switch (idSpecie) {
					case 1: {
						thisCane = new Cane(db, idAnimale);
						context.getRequest().setAttribute("Cane", thisCane);
	
						break;
					}
	
					case 2: {
						thisGatto = new Gatto(db, idAnimale);
						context.getRequest().setAttribute("Gatto", thisGatto);
						break;
					}
					case 3: {
						thisFuretto = new Furetto(db, idAnimale);
						context.getRequest().setAttribute("Furetto", thisFuretto);
						break;
					}
	
					default: {
						break;
					}
					}
	
					Operatore proprietario = new Operatore();
					proprietario.queryRecordOperatorebyIdLineaProduttiva(db, thisAnimale.getIdProprietario());
					context.getRequest().setAttribute("proprietario", proprietario);
	
					Operatore detentore = new Operatore();
					detentore.queryRecordOperatorebyIdLineaProduttiva(db, thisAnimale.getIdDetentore());
					context.getRequest().setAttribute("detentore", detentore);
					
				}else if(microchip!=null && !microchip.equals("-1") && idProprietario!=null && !idProprietario.equals("-1")){
					Operatore proprietario = new Operatore();
					proprietario.queryRecordOperatorebyIdLineaProduttiva(db, Integer.parseInt(idProprietario));
					context.getRequest().setAttribute("proprietario", proprietario);
	
					context.getRequest().setAttribute("microchip", microchip);
				}

				LookupList specieList = new LookupList(db, "lookup_specie");
				specieList.addItem(-1, "--Tutti--");
				context.getRequest().setAttribute("specieList", specieList);

				LookupList razzaList = new LookupList();
				razzaList.setTable("lookup_razza");
				razzaList.setIdSpecie(idSpecie);
				razzaList.buildList(db);
				razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("razzaList", razzaList);

				LookupList tagliaList = new LookupList(db, "lookup_taglia");
				tagliaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("tagliaList", tagliaList);

				LookupList mantelloList = new LookupList(db, "lookup_mantello");
				mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("mantelloList", mantelloList);

				ComuniAnagrafica c = new ComuniAnagrafica();
				// PER ORA PRENDO TUTTI I COMUNI E NON SOLO QUELLI RELATIVI
				// ALL'ASL
				// UTENTE
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, 1);
				LookupList comuniList = new LookupList(listaComuni, -1);
				comuniList.addItem(-1, "");
				context.getRequest().setAttribute("comuniList", comuniList);

			}

			else {
				return getReturn(context, "viewCertificatoPrimaIscrizioneEmpty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "viewAutocertificazioneMancataOrigineAnimale");

	}

	
	

	public String executeCommandPrintRichiestaAdozione(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina-documenti-view")) {
			return ("PermissionError");
		}

		Connection db = null;

		Cane thisCane = null;
		Gatto thisGatto = null;
		Furetto thisFuretto = null;
		int idAnimale = -1;
		int idSpecie = -1;

		try {

			Thread t = Thread.currentThread();
			db = this.getConnection(context);

			// String HOST_CORRENTE = context.getRequest().getLocalAddr();

			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
			// String HOST_CORRENTE=
			// ApplicationProperties.getProperty("APP_HOST_CANINA");
			/*
			 * String SERVER_BDU =
			 * InetAddress.getByName("hostAppBduPublic").getHostAddress();
			 * String APPLICATION_PORT = prefs.get("APPLICATION.PORT"); String
			 * APPLICATION_NAME = prefs.get("APPLICATION.NAME");
			 * 
			 * String url =
			 * "http://".concat(SERVER_BDU).concat(":").concat(APPLICATION_PORT
			 * ).concat("/").concat(APPLICATION_NAME).concat("/");
			 * 
			 * url="";context.getRequest().setAttribute("SERVER_BDU", url);
			 */
			// System.out.println(url);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			if ((String) context.getRequest().getParameter("isEmpty") == null) {

				SystemStatus systemStatus = this.getSystemStatus(context);
				String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
				String idSpecieString = (String) context.getRequest().getParameter("idSpecie");

				if (idAnimaleString != null && !("").equals(idAnimaleString)) {
					idAnimale = new Integer(idAnimaleString).intValue();
				}

				Animale thisAnimale = new Animale(db, idAnimale);
				context.getRequest().setAttribute("animale", thisAnimale);

				Operatore proprietario = new Operatore();
				Operatore vecchioProprietario = new Operatore();
				Operatore vecchioDetentore = new Operatore();
				int idEvento = new Integer(context.getRequest().getParameter("idEvento"));

				Evento evento = new Evento(db, idEvento);
				context.getRequest().setAttribute("evento", evento);
				if (evento.getIdTipologiaEvento() == EventoAdozioneFuoriAsl.idTipologiaDB || evento.getIdTipologiaEvento() == EventoAdozioneFuoriAsl.idTipologiaDB_obsoleto) {
					EventoAdozioneFuoriAsl dati_registrazione_adozione = new EventoAdozioneFuoriAsl(db, idEvento);
					// dati_registrazione_adozione.GetAdozioneApertaByIdAnimale(db,
					// idAnimale);

					if (dati_registrazione_adozione.getIdProprietario() > 0) {
						proprietario.queryRecordOperatorebyIdLineaProduttiva(db,
								dati_registrazione_adozione.getIdProprietario());
					}
					if (dati_registrazione_adozione.getIdVecchioProprietario() > 0)
						vecchioProprietario.queryRecordOperatorebyIdLineaProduttiva(db,
								dati_registrazione_adozione.getIdVecchioProprietario());
					if (dati_registrazione_adozione.getIdVecchioDetentore() > 0)
						vecchioDetentore.queryRecordOperatorebyIdLineaProduttiva(db,
								dati_registrazione_adozione.getIdVecchioDetentore());
					context.getRequest().setAttribute("dati_registrazione_adozione", dati_registrazione_adozione);
				}

				else if (evento.getIdTipologiaEvento() == EventoAdozioneDaCanile.idTipologiaDB) {
					EventoAdozioneDaCanile dati_registrazione_adozione = new EventoAdozioneDaCanile(db, idEvento);
					// dati_registrazione_adozione.GetAdozioneApertaByIdAnimale(db,
					// idAnimale);

					if (dati_registrazione_adozione.getIdProprietario() > 0) {
						proprietario.queryRecordOperatorebyIdLineaProduttiva(db,
								dati_registrazione_adozione.getIdProprietario());
					}
					if (dati_registrazione_adozione.getIdVecchioProprietario() > 0)
						vecchioProprietario.queryRecordOperatorebyIdLineaProduttiva(db,
								dati_registrazione_adozione.getIdVecchioProprietario());
					if (dati_registrazione_adozione.getIdVecchioDetentore() > 0)
						vecchioDetentore.queryRecordOperatorebyIdLineaProduttiva(db,
								dati_registrazione_adozione.getIdVecchioDetentore());
					context.getRequest()
							.setAttribute("dati_registrazione_adozione_canile", dati_registrazione_adozione);
				}

				context.getRequest().setAttribute("proprietario", proprietario);
				context.getRequest().setAttribute("vecchioProprietario", vecchioProprietario);
				context.getRequest().setAttribute("vecchioDetentore", vecchioDetentore);

				LookupList specieList = new LookupList(db, "lookup_specie");
				specieList.addItem(-1, "--Tutti--");
				context.getRequest().setAttribute("specieList", specieList);

				LookupList razzaList = new LookupList();
				razzaList.setTable("lookup_razza");
				razzaList.setIdSpecie(idSpecie);
				razzaList.buildList(db);
				razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("razzaList", razzaList);

				LookupList tagliaList = new LookupList(db, "lookup_taglia");
				tagliaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("tagliaList", tagliaList);

				LookupList mantelloList = new LookupList(db, "lookup_mantello");
				mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("mantelloList", mantelloList);

				ComuniAnagrafica c = new ComuniAnagrafica();
				// PER ORA PRENDO TUTTI I COMUNI E NON SOLO QUELLI RELATIVI
				// ALL'ASL
				// UTENTE
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, -1);
				LookupList comuniList = new LookupList(listaComuni, -1);
				comuniList.addItem(-1, "");

				context.getRequest().setAttribute("comuniList", comuniList);
				// System.out.println(comuniList.getSelectedValue(7335));

				LookupList provinceList = new LookupList(db, "lookup_province");
				provinceList.addItem(-1, "Seleziona provincia");
				context.getRequest().setAttribute("provinceList", provinceList);

			}

			else {
				return getReturn(context, "viewRichiestaAdozioneEmpty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "viewRichiestaAdozione");

	}
	
	
	public String executeCommandPrintRichiestaAdozioneVersoAssocCanili(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina-documenti-view")) {
			return ("PermissionError");
		}

		Connection db = null;

		Cane thisCane = null;
		Gatto thisGatto = null;
		Furetto thisFuretto = null;
		int idAnimale = -1;
		int idSpecie = -1;

		try {

			Thread t = Thread.currentThread();
			db = this.getConnection(context);

			// String HOST_CORRENTE = context.getRequest().getLocalAddr();

			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
			// String HOST_CORRENTE=
			// ApplicationProperties.getProperty("APP_HOST_CANINA");
			/*
			 * String SERVER_BDU =
			 * InetAddress.getByName("hostAppBduPublic").getHostAddress();
			 * String APPLICATION_PORT = prefs.get("APPLICATION.PORT"); String
			 * APPLICATION_NAME = prefs.get("APPLICATION.NAME");
			 * 
			 * String url =
			 * "http://".concat(SERVER_BDU).concat(":").concat(APPLICATION_PORT
			 * ).concat("/").concat(APPLICATION_NAME).concat("/");
			 * 
			 * url="";context.getRequest().setAttribute("SERVER_BDU", url);
			 */
			// System.out.println(url);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			if ((String) context.getRequest().getParameter("isEmpty") == null) {

				SystemStatus systemStatus = this.getSystemStatus(context);
				String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
				String idSpecieString = (String) context.getRequest().getParameter("idSpecie");

				if (idAnimaleString != null && !("").equals(idAnimaleString)) {
					idAnimale = new Integer(idAnimaleString).intValue();
				}

				Animale thisAnimale = new Animale(db, idAnimale);
				context.getRequest().setAttribute("animale", thisAnimale);

				Operatore proprietario = new Operatore();
				Operatore vecchioProprietario = new Operatore();
				Operatore vecchioDetentore = new Operatore();
				int idEvento = new Integer(context.getRequest().getParameter("idEvento"));

				Evento evento = new Evento(db, idEvento);
				context.getRequest().setAttribute("evento", evento);

				EventoAdozioneDaCanile dati_registrazione_adozione = new EventoAdozioneDaCanile(db, idEvento);

				if (dati_registrazione_adozione.getIdProprietario() > 0) {
					proprietario.queryRecordOperatorebyIdLineaProduttiva(db,
							dati_registrazione_adozione.getIdProprietario());
				}
				if (dati_registrazione_adozione.getIdVecchioProprietario() > 0)
					vecchioProprietario.queryRecordOperatorebyIdLineaProduttiva(db,
							dati_registrazione_adozione.getIdVecchioProprietario());
				if (dati_registrazione_adozione.getIdVecchioDetentore() > 0)
					vecchioDetentore.queryRecordOperatorebyIdLineaProduttiva(db,
							dati_registrazione_adozione.getIdVecchioDetentore());
				context.getRequest()
						.setAttribute("dati_registrazione_adozione_canile", dati_registrazione_adozione);

				context.getRequest().setAttribute("proprietario", proprietario);
				context.getRequest().setAttribute("vecchioProprietario", vecchioProprietario);
				context.getRequest().setAttribute("vecchioDetentore", vecchioDetentore);

				LookupList specieList = new LookupList(db, "lookup_specie");
				specieList.addItem(-1, "--Tutti--");
				context.getRequest().setAttribute("specieList", specieList);

				LookupList razzaList = new LookupList();
				razzaList.setTable("lookup_razza");
				razzaList.setIdSpecie(idSpecie);
				razzaList.buildList(db);
				razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("razzaList", razzaList);

				LookupList tagliaList = new LookupList(db, "lookup_taglia");
				tagliaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("tagliaList", tagliaList);

				LookupList mantelloList = new LookupList(db, "lookup_mantello");
				mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("mantelloList", mantelloList);

				ComuniAnagrafica c = new ComuniAnagrafica();
				// PER ORA PRENDO TUTTI I COMUNI E NON SOLO QUELLI RELATIVI
				// ALL'ASL
				// UTENTE
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, -1);
				LookupList comuniList = new LookupList(listaComuni, -1);
				comuniList.addItem(-1, "");

				context.getRequest().setAttribute("comuniList", comuniList);
				// System.out.println(comuniList.getSelectedValue(7335));

				LookupList provinceList = new LookupList(db, "lookup_province");
				provinceList.addItem(-1, "Seleziona provincia");
				context.getRequest().setAttribute("provinceList", provinceList);

			}

			else {
				return getReturn(context, "viewRichiestaAdozioneEmpty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "viewRichiestaAdozioneVersoAssocCanili");

	}

	public String executeCommandPrintRichiestaAdozioneColonia(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina-documenti-view")) {
			return ("PermissionError");
		}

		Connection db = null;

		Cane thisCane = null;
		Gatto thisGatto = null;
		Furetto thisFuretto = null;
		int idAnimale = -1;
		int idSpecie = -1;

		try {

			Thread t = Thread.currentThread();
			db = this.getConnection(context);

			// String HOST_CORRENTE = context.getRequest().getLocalAddr();

			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
			// String HOST_CORRENTE=
			// ApplicationProperties.getProperty("APP_HOST_CANINA");
			/*
			 * String SERVER_BDU =
			 * InetAddress.getByName("hostAppBduPublic").getHostAddress();
			 * String APPLICATION_PORT = prefs.get("APPLICATION.PORT"); String
			 * APPLICATION_NAME = prefs.get("APPLICATION.NAME");
			 * 
			 * String url =
			 * "http://".concat(SERVER_BDU).concat(":").concat(APPLICATION_PORT
			 * ).concat("/").concat(APPLICATION_NAME).concat("/");
			 * 
			 * url="";context.getRequest().setAttribute("SERVER_BDU", url);
			 * 
			 * System.out.println(url);
			 */
			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			if ((String) context.getRequest().getParameter("isEmpty") == null) {

				SystemStatus systemStatus = this.getSystemStatus(context);
				String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
				String idSpecieString = (String) context.getRequest().getParameter("idSpecie");

				if (idAnimaleString != null && !("").equals(idAnimaleString)) {
					idAnimale = new Integer(idAnimaleString).intValue();
				}

				Animale thisAnimale = new Animale(db, idAnimale);
				context.getRequest().setAttribute("animale", thisAnimale);

				Operatore proprietario = new Operatore();
				Operatore vecchioProprietario = new Operatore();
				Operatore vecchioDetentore = new Operatore();
				int idEvento = new Integer(context.getRequest().getParameter("idEvento"));

				Evento evento = new Evento(db, idEvento);
				context.getRequest().setAttribute("evento", evento);
				if (evento.getIdTipologiaEvento() == EventoAdozioneFuoriAsl.idTipologiaDB) {
					EventoAdozioneFuoriAsl dati_registrazione_adozione = new EventoAdozioneFuoriAsl(db, idEvento);
					// dati_registrazione_adozione.GetAdozioneApertaByIdAnimale(db,
					// idAnimale);

					if (dati_registrazione_adozione.getIdProprietario() > 0) {
						proprietario.queryRecordOperatorebyIdLineaProduttiva(db,
								dati_registrazione_adozione.getIdProprietario());
					}
					if (dati_registrazione_adozione.getIdVecchioProprietario() > 0)
						vecchioProprietario.queryRecordOperatorebyIdLineaProduttiva(db,
								dati_registrazione_adozione.getIdVecchioProprietario());
					if (dati_registrazione_adozione.getIdVecchioDetentore() > 0)
						vecchioDetentore.queryRecordOperatorebyIdLineaProduttiva(db,
								dati_registrazione_adozione.getIdVecchioDetentore());
					context.getRequest().setAttribute("dati_registrazione_adozione", dati_registrazione_adozione);
				}

				else if (evento.getIdTipologiaEvento() == EventoAdozioneDaColonia.idTipologiaDB || 
						//Adozione verso associazioni/canili
						evento.getIdTipologiaEvento() == 61) {
					EventoAdozioneDaColonia dati_registrazione_adozione = new EventoAdozioneDaColonia(db, idEvento);
					// dati_registrazione_adozione.GetAdozioneApertaByIdAnimale(db,
					// idAnimale);

					if (dati_registrazione_adozione.getIdProprietario() > 0) {
						proprietario.queryRecordOperatorebyIdLineaProduttiva(db,
								dati_registrazione_adozione.getIdProprietario());
					}
					if (dati_registrazione_adozione.getIdVecchioProprietario() > 0)
						vecchioProprietario.queryRecordOperatorebyIdLineaProduttiva(db,
								dati_registrazione_adozione.getIdVecchioProprietario());
					if (dati_registrazione_adozione.getIdVecchioDetentore() > 0)
						vecchioDetentore.queryRecordOperatorebyIdLineaProduttiva(db,
								dati_registrazione_adozione.getIdVecchioDetentore());
					context.getRequest().setAttribute("dati_registrazione_adozione_colonia",
							dati_registrazione_adozione);
				}

				context.getRequest().setAttribute("proprietario", proprietario);
				context.getRequest().setAttribute("vecchioProprietario", vecchioProprietario);
				context.getRequest().setAttribute("vecchioDetentore", vecchioDetentore);

				LookupList specieList = new LookupList(db, "lookup_specie");
				specieList.addItem(-1, "--Tutti--");
				context.getRequest().setAttribute("specieList", specieList);

				LookupList razzaList = new LookupList();
				razzaList.setTable("lookup_razza");
				razzaList.setIdSpecie(idSpecie);
				razzaList.buildList(db);
				razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("razzaList", razzaList);

				LookupList tagliaList = new LookupList(db, "lookup_taglia");
				tagliaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("tagliaList", tagliaList);

				LookupList mantelloList = new LookupList(db, "lookup_mantello");
				mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("mantelloList", mantelloList);

				ComuniAnagrafica c = new ComuniAnagrafica();
				// PER ORA PRENDO TUTTI I COMUNI E NON SOLO QUELLI RELATIVI
				// ALL'ASL
				// UTENTE
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, -1);
				LookupList comuniList = new LookupList(listaComuni, -1);
				comuniList.addItem(-1, "");

				context.getRequest().setAttribute("comuniList", comuniList);
				// System.out.println(comuniList.getSelectedValue(7335));

				LookupList provinceList = new LookupList(db, "lookup_province");
				provinceList.addItem(-1, "Seleziona provincia");
				context.getRequest().setAttribute("provinceList", provinceList);
			}

			else {
				return getReturn(context, "viewRichiestaAdozioneColoniaEmpty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "viewRichiestaAdozioneColonia");

	}

	public String executeCommandPrintRichiestaCampioni(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina-documenti-view")) {
			return ("PermissionError");
		}

		Connection db = null;

		Cane thisCane = null;
		Gatto thisGatto = null;
		int idAnimale = -1;
		int idSpecie = -1;
		int idEvento = -1;
		String microchip = "";

		try {

			Thread t = Thread.currentThread();
			db = this.getConnection(context);

			// String HOST_CORRENTE = context.getRequest().getLocalAddr();
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
			// String HOST_CORRENTE=
			// ApplicationProperties.getProperty("APP_HOST_CANINA");
			/*
			 * String SERVER_BDU =
			 * InetAddress.getByName("hostAppBduPublic").getHostAddress();
			 * String APPLICATION_PORT = prefs.get("APPLICATION.PORT"); String
			 * APPLICATION_NAME = prefs.get("APPLICATION.NAME");
			 * 
			 * String url =
			 * "http://".concat(SERVER_BDU).concat(":").concat(APPLICATION_PORT
			 * ).concat("/").concat(APPLICATION_NAME).concat("/");
			 * 
			 * url="";context.getRequest().setAttribute("SERVER_BDU", url);
			 */
			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			if ((String) context.getRequest().getParameter("isEmpty") == null) {

				SystemStatus systemStatus = this.getSystemStatus(context);
				String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
				String idSpecieString = (String) context.getRequest().getParameter("idSpecie");
				String idEventoPrelievoString = (String) context.getRequest().getParameter("idEvento");
				microchip = (String) context.getRequest().getParameter("microchip");

				if (idAnimaleString != null && !("").equals(idAnimaleString)) {
					idAnimale = new Integer(idAnimaleString).intValue();
				}

				if (idSpecieString != null && !("").equals(idSpecieString)) {
					idSpecie = new Integer(idSpecieString).intValue();
				}

				if (idEventoPrelievoString != null && !("").equals(idEventoPrelievoString)) {
					idEvento = new Integer(idEventoPrelievoString).intValue();
				}

				switch (idSpecie) {
				case 1: {
					if (idAnimale > -1)
					{
						thisCane = new Cane(db, idAnimale);
						context.getRequest().setAttribute("ricadentePianoLeishmania", thisCane.getRicadentePianoLeishmania(db, idEvento));
					}
					else if (!microchip.equals(""))
						thisCane = new Cane(db, microchip);
					context.getRequest().setAttribute("Cane", thisCane);

					break;
				}

				case 2: {
					thisGatto = new Gatto(db, idAnimale);
					context.getRequest().setAttribute("Gatto", thisGatto);
					context.getRequest().setAttribute("ricadentePianoLeishmania", thisGatto.getRicadentePianoLeishmania(db, idEvento));
					break;
				}

				default: {
					context.getRequest().setAttribute("ricadentePianoLeishmania", false);
					break;
				}
				}

				EventoRegistrazioneBDU dati_registrazione = new EventoRegistrazioneBDU();
				dati_registrazione.getEventoRegistrazione(db, idAnimale);
				context.getRequest().setAttribute("dati_registrazione", dati_registrazione);

				EventoPrelievoLeishmania prelievo = new EventoPrelievoLeishmania();

				if (idEvento > 0) {

					prelievo = new EventoPrelievoLeishmania(db, idEvento);
				} else {
					prelievo.getUltimoPrelievo(microchip, db);
				}
				context.getRequest().setAttribute("dati_prelievo", prelievo);

				int idVeterinarioEsecutore = (prelievo.getIdVeterinarioAsl() > 0) ? prelievo.getIdVeterinarioAsl()
						: prelievo.getIdVeterinarioLLPP();

				User veterinarioEsecutore = new User();
				if (idVeterinarioEsecutore > 0) {
					veterinarioEsecutore.setBuildContact(true);
					veterinarioEsecutore.buildRecord(db, idVeterinarioEsecutore);
				}
				context.getRequest().setAttribute("veterinarioEsecutore", veterinarioEsecutore);
				
				
				
				
				/*
				 * Operatore proprietario = new Operatore(); /*
				 * proprietario.queryRecordOperatorebyIdLineaProduttiva(db,
				 * dati_registrazione.getIdProprietario());
				 * context.getRequest().setAttribute("proprietario",
				 * proprietario);
				 */

				/*
				 * Operatore detentore = new Operatore(); /*
				 * detentore.queryRecordOperatorebyIdLineaProduttiva(db,
				 * dati_registrazione.getIdDetentore());
				 * context.getRequest().setAttribute("detentore", detentore);
				 */

				LookupList specieList = new LookupList(db, "lookup_specie");
				specieList.addItem(-1, "--Tutti--");
				context.getRequest().setAttribute("specieList", specieList);

				LookupList razzaList = new LookupList();
				razzaList.setTable("lookup_razza");
				razzaList.setIdSpecie(idSpecie);
				razzaList.buildList(db);
				razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("razzaList", razzaList);

				LookupList tagliaList = new LookupList(db, "lookup_taglia");
				tagliaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("tagliaList", tagliaList);

				LookupList mantelloList = new LookupList(db, "lookup_mantello");
				mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("mantelloList", mantelloList);

				ComuniAnagrafica c = new ComuniAnagrafica();
				// PER ORA PRENDO TUTTI I COMUNI E NON SOLO QUELLI RELATIVI
				// ALL'ASL
				// UTENTE
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, 1);
				LookupList comuniList = new LookupList(listaComuni, -1);
				comuniList.addItem(-1, "");
				context.getRequest().setAttribute("comuniList", comuniList);

			}

			else {
				return getReturn(context, "viewRichiestaCampioni");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "viewRichiestaCampioni");

	}
	
	
	
	public String executeCommandPrintCertificatoTrasferimentoFuoriRegione(ActionContext context) 
	{
		if (!hasPermission(context, "anagrafe_canina-documenti-view")) 
			return ("PermissionError");

		Connection db = null;

		Cane thisCane = null;
		Gatto thisGatto = null;
		int idAnimale=-1, idSpecie=-1, idEvento=-1;
		String microchip = "";

		try 
		{
			db = this.getConnection(context);

			//Caricamento lookup
			LookupList razzaList = new LookupList();
			razzaList.setTable("lookup_razza");
			razzaList.setIdSpecie(idSpecie);
			razzaList.buildList(db);
			context.getRequest().setAttribute("razzaList", razzaList);

			context.getRequest().setAttribute("aslList", new LookupList(db, "lookup_asl_rif"));
			context.getRequest().setAttribute("tagliaList",  new LookupList(db, "lookup_taglia"));
			context.getRequest().setAttribute("mantelloList", new LookupList(db, "lookup_mantello"));
			context.getRequest().setAttribute("regioniList", new LookupList(db, "lookup_regione"));
			
			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, 3);
			context.getRequest().setAttribute("comuniList", new LookupList(listaComuni, -1));
			//Fine caricamento lookup
			
			if ((String) context.getRequest().getParameter("isEmpty") == null) 
			{
				//Caricamento info animale
				String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
				String idSpecieString = (String) context.getRequest().getParameter("idSpecie");
				String idEventoString = (String) context.getRequest().getParameter("idEvento");
				microchip = (String) context.getRequest().getParameter("microchip");

				if (idAnimaleString != null && !("").equals(idAnimaleString)) 
					idAnimale = new Integer(idAnimaleString).intValue();

				if (idSpecieString != null && !("").equals(idSpecieString)) 
					idSpecie = new Integer(idSpecieString).intValue();

				if (idEventoString != null && !("").equals(idEventoString)) 
					idEvento = new Integer(idEventoString).intValue();

				switch (idSpecie) 
				{
					case 1: 
					{
						if (idAnimale > -1)
							thisCane = new Cane(db, idAnimale);
						else if (!microchip.equals(""))
							thisCane = new Cane(db, microchip);
						context.getRequest().setAttribute("Cane", thisCane);
						break;
					}
	
					case 2: 
					{
						thisGatto = new Gatto(db, idAnimale);
						context.getRequest().setAttribute("Gatto", thisGatto);
						break;
					}
	
					default: 
					{
						break;
					}
				}
				//Fine caricamento info animale

				//Caricamento info trasferimento
				EventoTrasferimentoFuoriRegione trasferimentoFuoriRegione = new EventoTrasferimentoFuoriRegione();

				if (idEvento > 0) 
					trasferimentoFuoriRegione = new EventoTrasferimentoFuoriRegione(db, idEvento);
				context.getRequest().setAttribute("trasferimentoFuoriRegione", trasferimentoFuoriRegione);
				
				Operatore vecchioProprietario = new Operatore(db, trasferimentoFuoriRegione.getIdVecchioProprietario());
				context.getRequest().setAttribute("vecchioProprietario", vecchioProprietario);
				
				context.getRequest().setAttribute("vecchioDetentore", ((trasferimentoFuoriRegione.getIdVecchioProprietario()==trasferimentoFuoriRegione.getIdVecchioDetentore())?(vecchioProprietario):(new Operatore(db, trasferimentoFuoriRegione.getIdVecchioDetentore()))));
				//Fine caricamento info trasferimento
			}
			else 
				return getReturn(context, "viewCertificatoTrasferimentoFuoriRegione");

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			this.freeConnection(context, db);
		}
		return getReturn(context, "viewCertificatoTrasferimentoFuoriRegione");

	}

	// public String executeCommandPrintRichiestaCampioniRabbia(
	// ActionContext context) {
	//
	// if (!hasPermission(context, "anagrafe_canina-documenti-view")) {
	// return ("PermissionError");
	// }
	//
	// Connection db = null;
	//
	// Cane thisCane = null;
	// Gatto thisGatto = null;
	// Furetto thisFuretto = null;
	// int idAnimale = -1;
	// int idSpecie = -1;
	//
	// try {
	//
	// db = this.getConnection(context);
	//
	// // String HOST_CORRENTE = context.getRequest().getLocalAddr();
	// ApplicationPrefs prefs = (ApplicationPrefs)
	// context.getServletContext().getAttribute("applicationPrefs");
	// String HOST_CORRENTE=
	// ApplicationProperties.getProperty("APP_HOST_CANINA");
	// String SERVER_BDU = HOST_CORRENTE;
	// String APPLICATION_PORT = prefs.get("APPLICATION.PORT");
	// String APPLICATION_NAME = prefs.get("APPLICATION.NAME");
	//
	// String url = "http://".concat(SERVER_BDU).concat(":").concat(
	// APPLICATION_PORT).concat("/").concat(APPLICATION_NAME)
	// .concat("/");
	//
	// url="";context.getRequest().setAttribute("SERVER_BDU", url);
	//
	// LookupList siteList = new LookupList(db, "lookup_asl_rif");
	// siteList.addItem(-1, "-- SELEZIONA VOCE --");
	// context.getRequest().setAttribute("aslList", siteList);
	//
	// SystemStatus systemStatus = this.getSystemStatus(context);
	// String idAnimaleString = (String) context.getRequest()
	// .getParameter("idAnimale");
	// String idSpecieString = (String) context.getRequest().getParameter(
	// "idSpecie");
	//
	// if (idAnimaleString != null && !("").equals(idAnimaleString)) {
	// idAnimale = new Integer(idAnimaleString).intValue();
	// }
	//
	// if (idSpecieString != null && !("").equals(idSpecieString)) {
	// idSpecie = new Integer(idSpecieString).intValue();
	// }
	// Animale thisAnimale = new Animale();
	//
	// switch (idSpecie) {
	// case 1: {
	// thisCane = new Cane(db, idAnimale);
	// context.getRequest().setAttribute("Cane", thisCane);
	// thisAnimale = thisCane;
	//
	// break;
	// }
	//
	// case 2: {
	// thisGatto = new Gatto(db, idAnimale);
	// context.getRequest().setAttribute("Gatto", thisGatto);
	// thisAnimale = thisGatto;
	// break;
	// }
	// case 3: {
	// thisFuretto = new Furetto(db, idAnimale);
	// context.getRequest().setAttribute("Furetto", thisFuretto);
	// thisAnimale = thisFuretto;
	// break;
	// }
	//
	// default: {
	// break;
	// }
	// }
	//
	// EventoRegistrazioneBDU dati_registrazione = new EventoRegistrazioneBDU();
	// dati_registrazione.getEventoRegistrazione(db, idAnimale);
	// context.getRequest().setAttribute("dati_registrazione",
	// dati_registrazione);
	//
	// EventoInserimentoVaccinazioni vaccinazione_rabbia_ultima =
	// EventoInserimentoVaccinazioni
	// .getUltimaVaccinazioneDaTipo(db,
	// thisAnimale.getMicrochip(), 1); // Mettere
	// // tipo_vacc_rabbia
	// // = 1 in file di
	// // properties
	// context.getRequest().setAttribute("dati_vaccinazione",
	// vaccinazione_rabbia_ultima);
	//
	// Operatore proprietario = new Operatore();
	// /*
	// * proprietario.queryRecordOperatorebyIdLineaProduttiva(db,
	// * dati_registrazione.getIdProprietario());
	// * context.getRequest().setAttribute("proprietario", proprietario);
	// */
	//
	// Operatore detentore = new Operatore();
	// /*
	// * detentore.queryRecordOperatorebyIdLineaProduttiva(db,
	// * dati_registrazione.getIdDetentore());
	// * context.getRequest().setAttribute("detentore", detentore);
	// */
	//
	// LookupList specieList = new LookupList(db, "lookup_specie");
	// specieList.addItem(-1, "--Tutti--");
	// context.getRequest().setAttribute("specieList", specieList);
	//
	// LookupList razzaList = new LookupList();
	// razzaList.setTable("lookup_razza");
	// razzaList.setIdSpecie(idSpecie);
	// razzaList.buildList(db);
	// razzaList.addItem(-1, systemStatus
	// .getLabel("calendar.none.4dashes"));
	// // assetManufacturerList.remove(assetManufacturerList.get("N.D."));
	// context.getRequest().setAttribute("razzaList", razzaList);
	//
	// LookupList tagliaList = new LookupList(db, "lookup_taglia");
	// tagliaList.addItem(-1, systemStatus
	// .getLabel("calendar.none.4dashes"));
	// // assetManufacturerList.remove(assetManufacturerList.get("N.D."));
	// context.getRequest().setAttribute("tagliaList", tagliaList);
	//
	// LookupList mantelloList = new LookupList(db, "lookup_mantello");
	// mantelloList.addItem(-1, systemStatus
	// .getLabel("calendar.none.4dashes"));
	// // assetManufacturerList.remove(assetManufacturerList.get("N.D."));
	// context.getRequest().setAttribute("mantelloList", mantelloList);
	//
	// ComuniAnagrafica c = new ComuniAnagrafica();
	// // PER ORA PRENDO TUTTI I COMUNI E NON SOLO QUELLI RELATIVI ALL'ASL
	// // UTENTE
	// ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, 1);
	// LookupList comuniList = new LookupList(listaComuni, -1);
	// comuniList.addItem(-1, "");
	// context.getRequest().setAttribute("comuniList", comuniList);
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// this.freeConnection(context, db);
	// }
	// return getReturn(context, "viewRichiestaCampioniRabbia");
	//
	// }

	public String executeCommandRegresso(ActionContext context) {
		if (!hasPermission(context, "cani-regresso-view")) {
			return ("PermissionError");
		}
		Connection db = null;

		try {
			Thread t = Thread.currentThread();
			db = this.getConnection(context);

			// buildFormElements(context, db);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("RegressoOK");
	}

	public String executeCommandCercaRegresso(ActionContext context) {
		if (!hasPermission(context, "cani-regresso-view")) {
			return ("PermissionError");
		}
		Connection db = null;

		String microchip = context.getRequest().getParameter("serialNumber");

		try {
			Thread t = Thread.currentThread();
			db = this.getConnection(context);

			microchip = ((microchip == null) || (microchip.trim().length() == 0)) ? (null) : (microchip.trim());
			logger.config("[BDU] - microchip " + microchip);
			Vector<Regresso> regressoList = Regresso.get(db, microchip);

			context.getRequest().setAttribute("regressoList", regressoList);

			// buildFormElements(context, db);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return ("CercaRegressoOK");
	}

	public String executeCommandPrepareDelete(ActionContext context) {
		if (!hasPermission(context, "anagrafe_canina-anagrafe_canina-delete")) {
			return ("PermissionError");
		}

		int idAnimale = -1;
		Animale thisAnimale = null;
		Connection db = null;
		try {

			Thread t = Thread.currentThread();
			db = this.getConnection(context);

			String idAnimaleString = (String) context.getRequest().getParameter("animaleId");

			if (idAnimaleString != null && !("").equals(idAnimaleString)) {
				idAnimale = new Integer(idAnimaleString);
			}

			if (idAnimale > 0) {
				thisAnimale = new Animale(db, idAnimale);
			} else {
				context.getRequest().setAttribute("Error", "Si è verificato un errore con la tua richiesta");
				return ("SystemError");
			}
			// }catch (Exception e) {
			// e.printStackTrace();
			// }finally {
			// this.freeConnection(context, db);
			// }

			// INIZIO CONTROLLO SU CONTRIBUTI PAGATI E REGISTRAZIONI APERTE
			// try {
			// if (thisAnimale.checkContributo(db,
			// thisAnimale.getMicrochip())==true){
			// context.getRequest().setAttribute("Error",
			// "Impossibile eliminare l'animale poichè presenta dei contributi pagati.");
			// return ("SystemError");}

			EventoList listaEventi = new EventoList();
			listaEventi.setId_animale(idAnimale);
			listaEventi.buildList(db);
			int regAperte = 0;
			String listaRegistrazioni = "";
			RegistrazioniWKF wk = new RegistrazioniWKF();
			ArrayList reg = wk.getRegistrazioniSpecie(db);
			LookupList registrazioniList = new LookupList(reg, -1);
			for (int i = 0; i < listaEventi.size(); i++) {
				Evento evento = (Evento) listaEventi.get(i);
				// se l'evento non è dei tipi possibili contestualmente
				// all'inserimento, ferma la cancellazione
				if (evento.getIdTipologiaEvento() != EventoRegistrazioneBDU.idTipologiaDB
						&& evento.getIdTipologiaEvento() != EventoSterilizzazione.idTipologiaDB
						&& evento.getIdTipologiaEvento() != EventoInserimentoMicrochip.idTipologiaDB
						&& evento.getIdTipologiaEvento() != EventoInserimentoMicrochip.idTipologiaDBSecondoMicrochip
						&& evento.getIdTipologiaEvento() != EventoCattura.idTipologiaDB
						&& evento.getIdTipologiaEvento() != EventoRilascioPassaporto.idTipologiaDB
						&& evento.getIdTipologiaEvento() != EventoInserimentoVaccinazioni.idTipologiaDB
						&& evento.getIdTipologiaEvento() != EventoEsitoControlli.idTipologiaDB) {
					regAperte++;
					listaRegistrazioni = listaRegistrazioni + evento.getIdEvento() + ": "
							+ registrazioniList.getSelectedValue(evento.getIdTipologiaEvento()) + "\n";
				}
			}
			if (regAperte > 0) {
				context.getRequest().setAttribute(
						"errore",
						"Impossibile eliminare l'animale poichè presenta " + regAperte
								+ " registrazioni associate.\n\n" + listaRegistrazioni
								+ "\n Cancellarle prima di riprovare.");
				return ("AnimalError");
			}

			// FINE CONTROLLO SU CONTRIBUTI PAGATI E REGISTRAZIONI APERTE

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("thisAnimale", thisAnimale);
		return getReturn(context, "PrepareDelete");
	}

	public String executeCommandDelete(ActionContext context) {
		if (!hasPermission(context, "anagrafe_canina-anagrafe_canina-delete")) {
			return ("PermissionError");
		}

		int idAnimale = -1;
		Animale thisAnimale = null;
		Connection db = null;
		Connection dbvam = null;
		Connection dbgisa = null;

		PreparedStatement pst = null;
		ResultSet result = null;
		try {

			Thread t = Thread.currentThread();
			db = this.getConnection(context);

			String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");

			if (idAnimaleString != null && !("").equals(idAnimaleString)) {
				idAnimale = new Integer(idAnimaleString);
			}

			if (idAnimale > 0) {
				thisAnimale = new Animale(db, idAnimale);
			} else {
				context.getRequest().setAttribute("Error", "Si è verificato un errore con la tua richiesta");
				return ("SystemError");
			}
			
			String identificativo = "";
			if (thisAnimale.getMicrochip() != null && !thisAnimale.getMicrochip().equals(""))
				identificativo = thisAnimale.getMicrochip();
			else
				identificativo = thisAnimale.getTatuaggio();
			
			
			
			
			// CONTROLLO SU GISA
			
			String cuPresenza = "";

				dbgisa = this.getConnectionGisa(context);

				StringBuffer sql = new StringBuffer();
				sql.append("SELECT * from public.check_presenza_identificativo_cu(?)");
				int i = 0;
				pst = dbgisa.prepareStatement(sql.toString());
				pst.setString(++i, identificativo);

				result = pst.executeQuery();
				while (result.next()) { // process results one row at a time
					cuPresenza = result.getString(1);
				}
				result.close();
				pst.close();
				
				if (cuPresenza!=null && !cuPresenza.equals("")) {
					if (System.getProperty("DEBUG") != null)
						System.out.println("ERRORE GISA");
					context.getRequest()
							.setAttribute("errore",
									"Impossibile eliminare l'animale da BDU in quanto presenta CU associati in GISA ("+cuPresenza+")");
					return ("AnimalError");
				}
			

			// CONTROLLO SU VAM
		
			int eliminato = 0;

			if (Animale.checkContributo(db, identificativo)) {

				// String dbName =
				// ApplicationProperties.getProperty("dbnameVam");
				// String username =
				// ApplicationProperties.getProperty("usernameDbVam");
				// String pwd
				// =ApplicationProperties.getProperty("passwordDbVam");
				// String host =
				// InetAddress.getByName("hostDbVam").getHostAddress();

				// dbvam = DbUtil.getConnection(dbName, username,
				// pwd, host);

				dbvam = this.getConnectionVam(context);

				sql = new StringBuffer();
				sql.append("SELECT * from public_functions.elimina_animale(?)");
				i = 0;
				pst = dbvam.prepareStatement(sql.toString());
				pst.setString(++i, identificativo);

				result = pst.executeQuery();
				while (result.next()) { // process results one row at a time
					eliminato = result.getInt("elimina_animale");
				}
				result.close();
				pst.close();
				// dbvam.close();

				// SE NON HO POTUTO CANCELLARLO DA VAM, ESCI
				// 1: ELIMINATO DA VAM
				// -1 : NON PRESENTE SU VAM, POSSO CANCELLARLO DA BDU
				// -2: CONTROLLI APERTI SU VAM. NON CANCELLARE
				// System.out.println("Valore restituito da VAM: "+eliminato);
				if (eliminato == -2) {
					if (System.getProperty("DEBUG") != null)
						System.out.println("ERRORE VAM");
					context.getRequest()
							.setAttribute("errore",
									"Impossibile eliminare l'animale da BDU in quanto presenta accettazioni aperte in VAM. Cancellarle prima di riprovare.");
					return ("AnimalError");
				}
				
			
				
				
				
				
				
				// CANCELLA
				thisAnimale.setNoteInternalUseOnly((String) context.getRequest().getParameter("noteInternalUseOnly"));
				thisAnimale.setIdUtenteCancellazione(this.getUserId(context));
				thisAnimale.cancella(db);
				context.getRequest().setAttribute("close", "si");
				
				//VERIFICA SE CI SONO PASSAPORTI ASSOCIATI, NEL CASO  DISATTIVA L'ASSOCIAZIONE DALL'ANIMALE

				String queryPassaporti="select " +
				"an.numero_passaporto an_pass, " +
				"erp.numero_passaporto ric_pass, " +
				"erp1.numero_passaporto ril_pass " +
				"from evento e " +
				"left join evento_rilascio_passaporto erp1 on erp1.id_evento = e.id_evento  " +
				"left join evento_riconoscimento_passaporto erp on erp.id_evento = e.id_evento " +
				"inner join animale an on an.id =e.id_animale  " +
				"where " +
				" an.microchip = e.microchip and an.id ='"+ idAnimale+ "'" +
				" and e.id_tipologia_evento in (6,48,64)" ;
				 
				ArrayList<String> listaPass= new ArrayList<String>();
				String pass="";
				PreparedStatement pstPass = db.prepareStatement(queryPassaporti.toString());
				
				ResultSet rsPass= pstPass.executeQuery();
				while (rsPass.next()) { // process results one row at a time
			

				if ( rsPass.getString("an_pass")!=null && !rsPass.getString("an_pass").equals("") && !listaPass.contains(rsPass.getString("an_pass") ))
					listaPass.add(rsPass.getString("an_pass"));

				if ( rsPass.getString("ric_pass")!=null && !rsPass.getString("ric_pass").equals("") && !listaPass.contains(rsPass.getString("ric_pass") ))
					listaPass.add(rsPass.getString("ric_pass"));

				if ( rsPass.getString("ril_pass")!=null && !rsPass.getString("ril_pass").equals("")&& !listaPass.contains(rsPass.getString("ril_pass") ))
					listaPass.add(rsPass.getString("ril_pass"));
				
				
				}
				
				for (int j=0;j< listaPass.size() ; j++){
					if(j==0) pass=   "'" + listaPass.get(j) + "'";
					else pass=  pass + ","+ "'" + listaPass.get(j) + "'";
				}
				rsPass.close();
				if(!pass.equals("")){
					Passaporto.setPassaportiNonUtilizzati(db, pass, idAnimale);
				}
				
				
				
				

				PreparedStatement pstMc = db
						.prepareStatement("update microchips set id_animale = null ,flag_secondo_microchip = false ,id_specie = null where microchip =? ");
				// pst.setInt(1, thisAnimale.getIdAnimale());
				// pst.setInt(2, thisAnimale.getIdSpecie());
				pstMc.setString(1, thisAnimale.getMicrochip());
				pstMc.execute();
				pstMc.close();

				if (thisAnimale.getTatuaggio() != null && !("").equals(thisAnimale.getTatuaggio())) {
					PreparedStatement pstTat = db
							.prepareStatement("update microchips set id_animale = null ,flag_secondo_microchip = false ,id_specie = null where microchip =? ");
					// pst.setInt(1, thisAnimale.getIdAnimale());
					// pst.setInt(2, thisAnimale.getIdSpecie());
					pstTat.setString(1, thisAnimale.getTatuaggio());
					pstTat.execute();
					pstTat.close();
				}
				
				

				// ELIMINA TUTTE LE REGISTRAZIONI DELL'ANIMALE
				Evento reg = new Evento();
				reg.deleteListaRegistrazioni(db, thisAnimale.getIdAnimale(), this.getUserId(context));
			} else {
				if (System.getProperty("DEBUG") != null)
					System.out.println("ERRORE VAM");
				context.getRequest()
						.setAttribute(
								"Error",
								"Si è verificato un errore con la cancellazione. Questo microchip rientra in un progetto di sterilizzazione per cui ha già ricevuto il pagamento del contributo.");
				return ("SystemError");
			}

		} catch (Exception e) {
			e.printStackTrace();
			

		} finally {
			this.freeConnectionVam(context, dbvam);
			this.freeConnection(context, db);

		}

		return getReturn(context, "PrepareDelete");
		//return executeCommandSearchForm(context);
	}

	public String executeCommandPrintDichiarazioneDecesso(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina-documenti-view")) {
			return ("PermissionError");
		}

		Connection db = null;

		int idAnimale = -1;
		int idSpecie = -1;

		try {
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
			// String HOST_CORRENTE=
			// ApplicationProperties.getProperty("APP_HOST_CANINA");
			/*
			 * String SERVER_BDU =
			 * InetAddress.getByName("hostAppBduPublic").getHostAddress();
			 * String APPLICATION_PORT = prefs.get("APPLICATION.PORT"); String
			 * APPLICATION_NAME = prefs.get("APPLICATION.NAME");
			 * 
			 * String url =
			 * "http://".concat(SERVER_BDU).concat(":").concat(APPLICATION_PORT
			 * ).concat("/").concat(APPLICATION_NAME).concat("/");
			 * 
			 * url="";context.getRequest().setAttribute("SERVER_BDU", url);
			 */
			Thread t = Thread.currentThread();
			db = this.getConnection(context);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			if ((String) context.getRequest().getParameter("isEmpty") == null) {

				SystemStatus systemStatus = this.getSystemStatus(context);
				String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
				String idSpecieString = (String) context.getRequest().getParameter("idSpecie");

				if (idAnimaleString != null && !("").equals(idAnimaleString)) {
					idAnimale = new Integer(idAnimaleString).intValue();
				}

				if (idSpecieString != null && !("").equals(idSpecieString)) {
					idSpecie = new Integer(idSpecieString).intValue();
				}

				Animale thisAnimale = new Animale(db, idAnimale);
				context.getRequest().setAttribute("thisAnimale", thisAnimale);

				EventoDecesso dati_decesso = new EventoDecesso();
				dati_decesso.getEventoDecesso(db, idAnimale);
				context.getRequest().setAttribute("dati_decesso", dati_decesso);

				LookupList causeDecessoList = new LookupList(db, "lookup_tipologia_decesso");
				context.getRequest().setAttribute("causeDecessoList", causeDecessoList);

				LookupList specieList = new LookupList(db, "lookup_specie");
				specieList.addItem(-1, "--Tutti--");
				context.getRequest().setAttribute("specieList", specieList);

				LookupList razzaList = new LookupList();
				razzaList.setTable("lookup_razza");
				razzaList.setIdSpecie(idSpecie);
				razzaList.buildList(db);
				razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("razzaList", razzaList);

				LookupList tagliaList = new LookupList(db, "lookup_taglia");
				tagliaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("tagliaList", tagliaList);

				LookupList mantelloList = new LookupList(db, "lookup_mantello");
				mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("mantelloList", mantelloList);

				ComuniAnagrafica c = new ComuniAnagrafica();
				// PER ORA PRENDO TUTTI I COMUNI E NON SOLO QUELLI RELATIVI
				// ALL'ASL
				// UTENTE
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, 1);
				LookupList comuniList = new LookupList(listaComuni, -1);
				comuniList.addItem(-1, "");
				context.getRequest().setAttribute("comuniList", comuniList);

			}

			else {
				return getReturn(context, "viewDichiarazioneDecessoEmpty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "viewDichiarazioneDecesso");

	}

	public String executeCommandPrintCertificatoDecesso(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina-documenti-view")) {
			return ("PermissionError");
		}

		Connection db = null;

		Cane thisCane = null;
		Gatto thisGatto = null;
		Furetto thisFuretto = null;
		int idAnimale = -1;
		int idSpecie = -1;

		try {
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
			// String HOST_CORRENTE=
			// ApplicationProperties.getProperty("APP_HOST_CANINA");
			/*
			 * String SERVER_BDU =
			 * InetAddress.getByName("hostAppBduPublic").getHostAddress();
			 * String APPLICATION_PORT = prefs.get("APPLICATION.PORT"); String
			 * APPLICATION_NAME = prefs.get("APPLICATION.NAME");
			 * 
			 * String url =
			 * "http://".concat(SERVER_BDU).concat(":").concat(APPLICATION_PORT
			 * ).concat("/").concat(APPLICATION_NAME).concat("/");
			 * 
			 * url="";context.getRequest().setAttribute("SERVER_BDU", url);
			 */
			db = this.getConnection(context);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			if ((String) context.getRequest().getParameter("isEmpty") == null) {

				SystemStatus systemStatus = this.getSystemStatus(context);
				String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
				String idSpecieString = (String) context.getRequest().getParameter("idSpecie");

				if (idAnimaleString != null && !("").equals(idAnimaleString)) {
					idAnimale = new Integer(idAnimaleString).intValue();
				}

				if (idSpecieString != null && !("").equals(idSpecieString)) {
					idSpecie = new Integer(idSpecieString).intValue();
				}

				Animale thisAnimale = new Animale(db, idAnimale);
				context.getRequest().setAttribute("thisAnimale", thisAnimale);

				EventoDecesso dati_decesso = new EventoDecesso();
				dati_decesso.getEventoDecesso(db, idAnimale);
				context.getRequest().setAttribute("dati_decesso", dati_decesso);

				EventoRegistrazioneBDU dati_registrazione = new EventoRegistrazioneBDU();
				dati_registrazione.getEventoRegistrazione(db, idAnimale);
				context.getRequest().setAttribute("dati_registrazione", dati_registrazione);

				LineaProduttiva linea_proprietario = new LineaProduttiva(db, thisAnimale.getIdProprietario());
				LineaProduttiva linea_detentore = new LineaProduttiva(db, thisAnimale.getIdDetentore());
				context.getRequest().setAttribute("linea_proprietario", linea_proprietario);
				context.getRequest().setAttribute("linea_detentore", linea_detentore);

				Operatore proprietario = new Operatore();
				proprietario.queryRecordOperatorebyIdLineaProduttiva(db, thisAnimale.getIdProprietario());
				context.getRequest().setAttribute("proprietario", proprietario);

				Operatore detentore = new Operatore();
				detentore.queryRecordOperatorebyIdLineaProduttiva(db, thisAnimale.getIdDetentore());
				context.getRequest().setAttribute("detentore", detentore);

				LookupList specieList = new LookupList(db, "lookup_specie");
				specieList.addItem(-1, "--Tutti--");
				context.getRequest().setAttribute("specieList", specieList);

				LookupList razzaList = new LookupList();
				razzaList.setTable("lookup_razza");
				razzaList.setIdSpecie(idSpecie);
				razzaList.buildList(db);
				razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("razzaList", razzaList);

				LookupList tagliaList = new LookupList(db, "lookup_taglia");
				tagliaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("tagliaList", tagliaList);

				LookupList mantelloList = new LookupList(db, "lookup_mantello");
				mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("mantelloList", mantelloList);

				ComuniAnagrafica c = new ComuniAnagrafica();
				// PER ORA PRENDO TUTTI I COMUNI E NON SOLO QUELLI RELATIVI
				// ALL'ASL
				// UTENTE
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, 1);
				LookupList comuniList = new LookupList(listaComuni, -1);
				comuniList.addItem(-1, "");
				context.getRequest().setAttribute("comuniList", comuniList);
			}

			else {
				return getReturn(context, "viewCertificatoDecessoEmpty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "viewCertificatoDecesso");

	}

	public String executeCommandPrintDichiarazioneSmarrimento(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina-documenti-view")) {
			return ("PermissionError");
		}

		Connection db = null;

		int idAnimale = -1;
		int idSpecie = -1;
		int idEvento = -1;

		try {
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
			// String HOST_CORRENTE=
			// ApplicationProperties.getProperty("APP_HOST_CANINA");
			/*
			 * String SERVER_BDU =
			 * InetAddress.getByName("hostAppBduPublic").getHostAddress();
			 * String APPLICATION_PORT = prefs.get("APPLICATION.PORT"); String
			 * APPLICATION_NAME = prefs.get("APPLICATION.NAME");
			 * 
			 * String url =
			 * "http://".concat(SERVER_BDU).concat(":").concat(APPLICATION_PORT
			 * ).concat("/").concat(APPLICATION_NAME).concat("/");
			 * 
			 * url="";context.getRequest().setAttribute("SERVER_BDU", url);
			 */
			db = this.getConnection(context);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			if ((String) context.getRequest().getParameter("isEmpty") == null) {

				SystemStatus systemStatus = this.getSystemStatus(context);
				String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
				String idSpecieString = (String) context.getRequest().getParameter("idSpecie");
				String idEventoString = (String) context.getRequest().getParameter("idEvento");

				if (idEventoString != null && !("").equals(idEventoString)) {
					idEvento = new Integer(idEventoString).intValue();
				}
				
				if (idAnimaleString != null && !("").equals(idAnimaleString)) {
					idAnimale = new Integer(idAnimaleString).intValue();
				}

				if (idSpecieString != null && !("").equals(idSpecieString)) {
					idSpecie = new Integer(idSpecieString).intValue();
				}

				Animale thisAnimale = new Animale(db, idAnimale);
				context.getRequest().setAttribute("thisAnimale", thisAnimale);

				EventoSmarrimento dati_smarrimento = new EventoSmarrimento();
				dati_smarrimento.getEventoSmarrimento(db, idEvento);
				context.getRequest().setAttribute("dati_smarrimento", dati_smarrimento);

				LookupList specieList = new LookupList(db, "lookup_specie");
				specieList.addItem(-1, "--Tutti--");
				context.getRequest().setAttribute("specieList", specieList);

				LookupList razzaList = new LookupList();
				razzaList.setTable("lookup_razza");
				razzaList.setIdSpecie(idSpecie);
				razzaList.buildList(db);
				razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("razzaList", razzaList);

				LookupList tagliaList = new LookupList(db, "lookup_taglia");
				tagliaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("tagliaList", tagliaList);

				LookupList mantelloList = new LookupList(db, "lookup_mantello");
				mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("mantelloList", mantelloList);

				ComuniAnagrafica c = new ComuniAnagrafica();
				// PER ORA PRENDO TUTTI I COMUNI E NON SOLO QUELLI RELATIVI
				// ALL'ASL
				// UTENTE
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, 1);
				LookupList comuniList = new LookupList(listaComuni, -1);
				comuniList.addItem(-1, "");
				context.getRequest().setAttribute("comuniList", comuniList);

			}

			else {
				return getReturn(context, "viewDichiarazioneSmarrimentoEmpty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "viewDichiarazioneSmarrimento");

	}

	public String executeCommandPrintCertificatoSmarrimento(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina-documenti-view")) {
			return ("PermissionError");
		}

		Connection db = null;

		int idAnimale = -1;
		int idSpecie = -1;
		int idEvento = -1;

		try {
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
			// String HOST_CORRENTE=
			// ApplicationProperties.getProperty("APP_HOST_CANINA");
			/*
			 * String SERVER_BDU =
			 * InetAddress.getByName("hostAppBduPublic").getHostAddress();
			 * String APPLICATION_PORT = prefs.get("APPLICATION.PORT"); String
			 * APPLICATION_NAME = prefs.get("APPLICATION.NAME");
			 * 
			 * String url =
			 * "http://".concat(SERVER_BDU).concat(":").concat(APPLICATION_PORT
			 * ).concat("/").concat(APPLICATION_NAME).concat("/");
			 * 
			 * url="";context.getRequest().setAttribute("SERVER_BDU", url);
			 */
			db = this.getConnection(context);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			if ((String) context.getRequest().getParameter("isEmpty") == null) {

				SystemStatus systemStatus = this.getSystemStatus(context);
				String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
				String idSpecieString = (String) context.getRequest().getParameter("idSpecie");
				String idEventoString = (String) context.getRequest().getParameter("idEvento");

				if (idAnimaleString != null && !("").equals(idAnimaleString)) {
					idAnimale = new Integer(idAnimaleString).intValue();
				}
				
				if (idEventoString != null && !("").equals(idEventoString)) {
					idEvento = new Integer(idEventoString).intValue();
				}

				if (idSpecieString != null && !("").equals(idSpecieString)) {
					idSpecie = new Integer(idSpecieString).intValue();
				}

				Animale thisAnimale = new Animale(db, idAnimale);
				context.getRequest().setAttribute("thisAnimale", thisAnimale);

				EventoSmarrimento dati_smarrimento = new EventoSmarrimento();
				dati_smarrimento.getEventoSmarrimento(db, idEvento);
				context.getRequest().setAttribute("dati_smarrimento", dati_smarrimento);

				EventoRegistrazioneBDU dati_registrazione = new EventoRegistrazioneBDU();
				dati_registrazione.getEventoRegistrazione(db, idAnimale);
				context.getRequest().setAttribute("dati_registrazione", dati_registrazione);

				LineaProduttiva linea_proprietario = new LineaProduttiva(db, thisAnimale.getIdProprietario());
				LineaProduttiva linea_detentore = new LineaProduttiva(db, thisAnimale.getIdDetentore());
				context.getRequest().setAttribute("linea_proprietario", linea_proprietario);
				context.getRequest().setAttribute("linea_detentore", linea_detentore);

				Operatore proprietario = new Operatore();
				proprietario.queryRecordOperatorebyIdLineaProduttiva(db, thisAnimale.getIdProprietario());
				context.getRequest().setAttribute("proprietario", proprietario);

				Operatore detentore = new Operatore();
				detentore.queryRecordOperatorebyIdLineaProduttiva(db, thisAnimale.getIdDetentore());
				context.getRequest().setAttribute("detentore", detentore);

				LookupList specieList = new LookupList(db, "lookup_specie");
				specieList.addItem(-1, "--Tutti--");
				context.getRequest().setAttribute("specieList", specieList);

				LookupList razzaList = new LookupList();
				razzaList.setTable("lookup_razza");
				razzaList.setIdSpecie(idSpecie);
				razzaList.buildList(db);
				razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("razzaList", razzaList);

				LookupList tagliaList = new LookupList(db, "lookup_taglia");
				tagliaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("tagliaList", tagliaList);

				LookupList mantelloList = new LookupList(db, "lookup_mantello");
				mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("mantelloList", mantelloList);

				ComuniAnagrafica c = new ComuniAnagrafica();
				// PER ORA PRENDO TUTTI I COMUNI E NON SOLO QUELLI RELATIVI
				// ALL'ASL
				// UTENTE
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, 1);
				LookupList comuniList = new LookupList(listaComuni, -1);
				comuniList.addItem(-1, "");
				context.getRequest().setAttribute("comuniList", comuniList);
			}

			else {
				return getReturn(context, "viewCertificatoSmarrimentoEmpty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "viewCertificatoSmarrimento");

	}

	public String executeCommandPrintDichiarazioneFurto(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina-documenti-view")) {
			return ("PermissionError");
		}

		Connection db = null;

		int idAnimale = -1;
		int idSpecie = -1;

		try {
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");

			db = this.getConnection(context);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			if ((String) context.getRequest().getParameter("isEmpty") == null) {

				SystemStatus systemStatus = this.getSystemStatus(context);
				String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
				String idSpecieString = (String) context.getRequest().getParameter("idSpecie");

				if (idAnimaleString != null && !("").equals(idAnimaleString)) {
					idAnimale = new Integer(idAnimaleString).intValue();
				}

				if (idSpecieString != null && !("").equals(idSpecieString)) {
					idSpecie = new Integer(idSpecieString).intValue();
				}

				Animale thisAnimale = new Animale(db, idAnimale);
				context.getRequest().setAttribute("thisAnimale", thisAnimale);

				EventoFurto dati_furto = new EventoFurto();
				dati_furto.getEventoFurto(db, idAnimale);
				context.getRequest().setAttribute("dati_furto", dati_furto);

				LookupList specieList = new LookupList(db, "lookup_specie");
				specieList.addItem(-1, "--Tutti--");
				context.getRequest().setAttribute("specieList", specieList);

				LookupList razzaList = new LookupList();
				razzaList.setTable("lookup_razza");
				razzaList.setIdSpecie(idSpecie);
				razzaList.buildList(db);
				razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("razzaList", razzaList);

				LookupList tagliaList = new LookupList(db, "lookup_taglia");
				tagliaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("tagliaList", tagliaList);

				LookupList mantelloList = new LookupList(db, "lookup_mantello");
				mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("mantelloList", mantelloList);

				ComuniAnagrafica c = new ComuniAnagrafica();
				// PER ORA PRENDO TUTTI I COMUNI E NON SOLO QUELLI RELATIVI
				// ALL'ASL
				// UTENTE
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, 1);
				LookupList comuniList = new LookupList(listaComuni, -1);
				comuniList.addItem(-1, "");
				context.getRequest().setAttribute("comuniList", comuniList);

			}

			else {
				return getReturn(context, "viewDichiarazioneSmarrimentoEmpty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "viewDichiarazioneFurto");

	}

	public String executeCommandPrintCertificatoFurto(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina-documenti-view")) {
			return ("PermissionError");
		}

		Connection db = null;

		int idAnimale = -1;
		int idSpecie = -1;

		try {
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");

			db = this.getConnection(context);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			if ((String) context.getRequest().getParameter("isEmpty") == null) {

				SystemStatus systemStatus = this.getSystemStatus(context);
				String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
				String idSpecieString = (String) context.getRequest().getParameter("idSpecie");

				if (idAnimaleString != null && !("").equals(idAnimaleString)) {
					idAnimale = new Integer(idAnimaleString).intValue();
				}

				if (idSpecieString != null && !("").equals(idSpecieString)) {
					idSpecie = new Integer(idSpecieString).intValue();
				}

				Animale thisAnimale = new Animale(db, idAnimale);
				context.getRequest().setAttribute("thisAnimale", thisAnimale);

				EventoFurto dati_furto = new EventoFurto();
				dati_furto.getEventoFurto(db, idAnimale);
				context.getRequest().setAttribute("dati_smarrimento", dati_furto);

				EventoRegistrazioneBDU dati_registrazione = new EventoRegistrazioneBDU();
				dati_registrazione.getEventoRegistrazione(db, idAnimale);
				context.getRequest().setAttribute("dati_registrazione", dati_registrazione);

				LineaProduttiva linea_proprietario = new LineaProduttiva(db, thisAnimale.getIdProprietario());
				LineaProduttiva linea_detentore = new LineaProduttiva(db, thisAnimale.getIdDetentore());
				context.getRequest().setAttribute("linea_proprietario", linea_proprietario);
				context.getRequest().setAttribute("linea_detentore", linea_detentore);

				Operatore proprietario = new Operatore();
				proprietario.queryRecordOperatorebyIdLineaProduttiva(db, thisAnimale.getIdProprietario());
				context.getRequest().setAttribute("proprietario", proprietario);

				Operatore detentore = new Operatore();
				detentore.queryRecordOperatorebyIdLineaProduttiva(db, thisAnimale.getIdDetentore());
				context.getRequest().setAttribute("detentore", detentore);

				LookupList specieList = new LookupList(db, "lookup_specie");
				specieList.addItem(-1, "--Tutti--");
				context.getRequest().setAttribute("specieList", specieList);

				LookupList razzaList = new LookupList();
				razzaList.setTable("lookup_razza");
				razzaList.setIdSpecie(idSpecie);
				razzaList.buildList(db);
				razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("razzaList", razzaList);

				LookupList tagliaList = new LookupList(db, "lookup_taglia");
				tagliaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("tagliaList", tagliaList);

				LookupList mantelloList = new LookupList(db, "lookup_mantello");
				mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("mantelloList", mantelloList);

				ComuniAnagrafica c = new ComuniAnagrafica();
				// PER ORA PRENDO TUTTI I COMUNI E NON SOLO QUELLI RELATIVI
				// ALL'ASL
				// UTENTE
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, 1);
				LookupList comuniList = new LookupList(listaComuni, -1);
				comuniList.addItem(-1, "");
				context.getRequest().setAttribute("comuniList", comuniList);
			}

			else {
				return getReturn(context, "viewCertificatoSmarrimentoEmpty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "viewCertificatoFurto");

	}

	public String executeCommandGestisciDecessi(ActionContext context) {
		if (!hasPermission(context, "anagrafe_canina_registrazioni-add")) {
			return ("PermissionError");
		}
		return "gestisciDecessiOk";
	}
	
	public String executeCommandRegistroUnico(ActionContext context)
	{
		if (!hasPermission(context, "registro_unico_aggressori-view")) 
			return ("PermissionError");
		
		Connection db = null;
		try 
		{
			db = this.getConnection(context);
		
			Integer year = RegistroUnico.getMinAnno(db);
			if(year==null)
				year = Calendar.getInstance().get(Calendar.YEAR);
			context.getRequest().setAttribute("anno", String.valueOf(year));
			context.getRequest().setAttribute("annoCorrente", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
		 }
		 catch (SQLException e) 
		 {
			 e.printStackTrace();
		 } 
		 finally 
		 {
			 this.freeConnection(context, db);
		 }
         return "registroUnicoOk";
	}
	
	public String executeCommandDetailRegistroUnico(ActionContext context)
	{
		if (!hasPermission(context, "registro_unico_aggressori-view")) 
			return ("PermissionError");
		
		Connection db = null;
		try 
		{
			db = this.getConnection(context);
			
			int idEvento = Integer.parseInt(context.getRequest().getParameter("idEvento"));
		
			RegistroUnico reg = new RegistroUnico(db, idEvento);
			context.getRequest().setAttribute("registro", reg);
			
		 }
		 catch (SQLException e) 
		 {
			 e.printStackTrace();
		 } 
		 finally 
		 {
			 this.freeConnection(context, db);
		 }
         return "registroUnicoDetailOk";
	}
	
	
	public String executeCommandSearchRegistroUnico(ActionContext context)
	{
		if (!hasPermission(context, "registro_unico_aggressori-view")) 
			return ("PermissionError");
		
		Connection db = null;
		
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		
		try 
		{
			db = this.getConnection(context);
			
			int anno = Integer.parseInt(context.getRequest().getParameter("anno"));
			int asl = user.getSiteId();
			
			
			
			RegistroUnicoList registri = new RegistroUnicoList();

			PagedListInfo registriListInfo = this.getPagedListInfo(context, "registriListInfo");
			registriListInfo.setLink("AnimaleAction.do?command=SearchRegistroUnico&anno=" + anno);
			RegistroUnico reg = new RegistroUnico();
			reg.setPagedListInfo(registriListInfo);
			registri = reg.getRegistri(db, anno, asl);
			context.getRequest().setAttribute("registri",registri);
		 }
		 catch (SQLException e) 
		 {
			 e.printStackTrace();
		 } 
		 finally 
		 {
			 this.freeConnection(context, db);
		 }
         return "registroUnicoListOk";
	}

	public String executeCommandGestisciDecessiTrovaAnimali(ActionContext context) throws IndirizzoNotFoundException {
		if (!hasPermission(context, "anagrafe_canina_registrazioni-add")) {
			return ("PermissionError");
		}

		String range_anni = context.getRequest().getParameter("anni");
		int range_anni_int = Integer.parseInt(range_anni);

		// GENERA DATA DI NASCITA DA RICERCARE (ODIERNA - RANGE_ANNI)
		Calendar cal = GregorianCalendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		cal.set(Calendar.YEAR, year - range_anni_int);
		Timestamp data_nascita_ricerca = new Timestamp(cal.getTimeInMillis());

		// CONNESSIONE A DB
		Connection db = null;
		try {
			db = this.getConnection(context);

			LookupList statoList = new LookupList(db, "lookup_tipologia_stato");
			context.getRequest().setAttribute("statoList", statoList);

			UserBean user = (UserBean) context.getSession().getAttribute("User");
			AnimaleList animaleList = new AnimaleList();
			animaleList.setIdAsl(user.getSiteId());
			animaleList.setAnnoNascita(year - range_anni_int);
			animaleList.setFlagDecesso(false);
			animaleList.setBuildProprietario(false);
			if(context.getRequest().getParameter("inCessione")!=null)
			{
				ArrayList<Integer> statiDaIncludere = new ArrayList<Integer>();
				statiDaIncludere.add(27);
				statiDaIncludere.add(5);
				statiDaIncludere.add(22);
				statiDaIncludere.add(29);
				statiDaIncludere.add(51);
				statiDaIncludere.add(75);
				statiDaIncludere.add(76);
				animaleList.setStatiDaIncludere(statiDaIncludere);
			}
			if(context.getRequest().getParameter("ragioneSocProp")!=null && !context.getRequest().getParameter("ragioneSocProp").equals(""))
			{
				animaleList.setDenominazioneProprietario("%" + context.getRequest().getParameter("ragioneSocProp") + "%");
			}
			if(context.getRequest().getParameter("ragioneSocDet")!=null && !context.getRequest().getParameter("ragioneSocDet").equals(""))
			{
				animaleList.setDenominazioneDetentore("%" + context.getRequest().getParameter("ragioneSocDet") + "%");
			}
			animaleList.buildList(db);
			// ARRAYLIST SU REQUEST
			context.getRequest().setAttribute("listaAnimali", animaleList);
			context.getRequest().setAttribute("range_anni", range_anni.toString());

			// DATI UTENTE SU REQUEST
			context.getRequest().setAttribute("User", user);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		return "gestisciDecessiTrovaAnimaliOk";
	}

	public String executeCommandGestisciDecessiUfficio(ActionContext context) throws Exception {
		if (!hasPermission(context, "anagrafe_canina_registrazioni-add")) {
			return ("PermissionError");
		}
		AnimaleList animali = new AnimaleList();
		String range_anni = context.getRequest().getParameter("rangeAnni");

		// PRENDO DIMENSIONE DELLA LISTA
		String listaSize = context.getRequest().getParameter("listaSize");
		int MAX = Integer.parseInt(listaSize);

		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String username = user.getUserRecord().getUsername();
		int userid = user.getUserRecord().getId();
		int userasl = user.getUserRecord().getSiteId();

		Calendar cal = GregorianCalendar.getInstance();
		Timestamp data_decesso = new Timestamp(cal.getTimeInMillis());

		Connection db = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			db = this.getConnection(context);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			String useraslnome;
			if (userasl > -1)
				useraslnome = siteList.getSelectedValue(userasl);
			else
				useraslnome = "Nessuna ASL";

			for (int i = 0; i < MAX; i++) {
				
				
				String id = context.getRequest().getParameter("id_" + i);
				String specie = context.getRequest().getParameter("specie_" + i);
				String microchip = context.getRequest().getParameter("microchip_" + i);
				String tatuaggio = context.getRequest().getParameter("tatuaggio_" + i);
				String nome = context.getRequest().getParameter("nome_" + i);
				String decesso = context.getRequest().getParameter("decesso_" + i);
				String dataNascita = context.getRequest().getParameter("dataNascita_" + i);
				String stato = context.getRequest().getParameter("stato_" + i);
				int statoInt = -1;
				if(stato!=null && !stato.equals(""))
				{
					statoInt = Integer.parseInt(stato);
				}

				if (decesso != null) { // SE LA CHECKBOX "DECESSO D'UFFICIO" E'
										// ON
					
					int idAnimale = Integer.parseInt(id);
					Animale thisAnimale = new Animale();
					thisAnimale.setIdAnimale(idAnimale);
					thisAnimale.setNome(nome);
					thisAnimale.setTatuaggio(tatuaggio);
					thisAnimale.setDataNascita(dataNascita);
					thisAnimale.setStato(statoInt);
					if(specie!=null && !specie.equals(""))
						thisAnimale.setIdSpecie(Integer.parseInt(specie));
					animali.add(thisAnimale);
					// GENERA L'EVENTO GENERICO
					Evento thisEvento = new Evento();
					thisEvento.setIdAnimale(id);
					thisEvento.setSpecieAnimaleId(specie);
					thisEvento.setMicrochip(microchip);
					thisEvento.setIdAslRiferimento(userasl);
					thisEvento.setEnteredby(userid);
					thisEvento.setIdStatoOriginale(statoInt);
					thisEvento.setIdTipologiaEvento(EventoDecesso.idTipologiaDB);
					thisEvento.setNoteInternalUseOnly("Decesso d'ufficio generato da utente " + username + " ("
							+ userid + ") ASL: " + useraslnome + " (" + userasl + ")");
					thisEvento.insert(db);

					// GENERA L'EVENTO DECESSO
					int idEvento = -1;
					EventoDecesso thisDecesso = new EventoDecesso();
					thisDecesso.setDataDecesso(data_decesso);
					thisDecesso.setFlagDecessoPresunto(true);
					thisDecesso.setNoteInternalUseOnly("Decesso d'ufficio generato da utente " + username + " ("
							+ userid + ") ASL: " + useraslnome + " (" + userasl + ")");
					thisDecesso.setLuogoDecesso("Sistema");
					thisDecesso.setIdCausaDecesso(13); // 13: DECESSO D'UFFICIO
					thisDecesso.setIdEvento(idEvento);
					thisDecesso.insertUfficio(db);

					// AGGIORNA LO STATO DELL'ANIMALE

					if(statoInt>0)
					{
					pst = db.prepareStatement("Select s.code as stato from lookup_tipologia_stato s where s.isgroup is false and lower(s.description) = (select lower(s2.description) || '/deceduto' from lookup_tipologia_stato s2 where s2.isgroup is false and s2.code = ?)");
					pst.setInt(1, statoInt);
					rs = DatabaseUtils.executeQuery(db, pst);
					if (rs.next()) 
						thisAnimale.setStato(rs.getInt("stato"));
					}
					
					thisAnimale.setFlagDecesso(true);
					thisAnimale.setFlagSmarrimento(false);
					thisAnimale.setFlagFurto(false);
					thisAnimale.updateFlagDecessoDufficio(db,user);

				}

			}
			LookupList statoList = new LookupList(db, "lookup_tipologia_stato");
			context.getRequest().setAttribute("statoList", statoList);
			context.getRequest().setAttribute("listaAnimali", animali);
			context.getRequest().setAttribute("range_anni", range_anni.toString());
			context.getRequest().setAttribute("reload", "ok");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return "gestisciDecessiTrovaAnimaliOk";
	}

	public String executeCommandEstrazionePeriodicaCaniInCanile(ActionContext context) {

		if (!(hasPermission(context, "estrazioneMensile-view"))) {
			return ("PermissionError");
		}
		Connection db = null;
		RowSetDynaClass rsdc = null;
		try {
			db = getConnection(context);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			String fileName = "Estrazione_Totale_Cani_in_canile" + sdf.format(new Date()) + ".xls";

			// int asl =
			// Integer.parseInt(context.getRequest().getParameter("aslRif"));

			String sql = "Select * from totali_canili ";

			PreparedStatement stat = db.prepareStatement(sql);
			// esecuzione della query
			ResultSet rs = stat.executeQuery();

			rsdc = new RowSetDynaClass(rs);
			HttpServletResponse res = context.getResponse();
			res.setContentType("application/xls");
			res.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
			ServletOutputStream sout = res.getOutputStream();

			sout.write("<table border=1>".getBytes());

			List<DynaBean> l = rsdc.getRows();

			// stampa i nomi delle colonne
			XLSUtils.dynamicHeader(rs, sout);

			for (int i = 0; i < l.size(); i++) {
				// Stampa ogni riga sul file
				XLSUtils.dynamicRow(l.get(i), rs, sout);
			}
			sout.write("</table>".getBytes());
			// svuota lo standard di output
			sout.flush();

		} catch (Exception e) {
			logger.severe("[CANINA] - EXCEPTION nella action executeCommandEstrazionePeriodicaCaniInCanile della classe EstrazioneMensile");
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");

		} finally {
			this.freeConnection(context, db);
			System.gc();

		}

		return "-none-";
	}

	public String executeCommandPrintObblighiProprietario(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina-documenti-view")) {
			return ("PermissionError");
		}

		Connection db = null;

		int idAnimale = -1;
		int idSpecie = -1;
		Animale thisAnimale = null;
		try {
			db = this.getConnection(context);

			SystemStatus systemStatus = this.getSystemStatus(context);
			String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
			if (idAnimaleString != null && !("").equals(idAnimaleString)) {
				idAnimale = new Integer(idAnimaleString).intValue();
			}
			thisAnimale = new Animale(db, idAnimale);

			context.getRequest().setAttribute("animale", thisAnimale);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		return "viewRichiestaObblighiProprietarioOK";
	}

	public String executeCommandLoadFileVerificaEsistenzaMicrochip(ActionContext context) {
		if (!hasPermission(context, "verifica_presenza_microchip-view")) {
			return ("PermissionError");
		}

		return "UploadPrepareOK";
	}

	public String executeCommandUploadListaMCVerificaEsistenza(ActionContext context) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String timeToLog = sdf.format(new Date(System.currentTimeMillis()));
		HttpServletRequest req = context.getRequest();
		Connection db = null;

		if (!hasPermission(context, "verifica_presenza_microchip-view")) {
			return ("PermissionError");
		}

		try {
			db = getConnection(context);
			MultipartRequest multi = null;
			int maxUploadSize = 50000000;
			multi = new MultipartRequest(req, ".", maxUploadSize);
			File myFileT = multi.getFile("microchips");
			if (myFileT != null) {
				BufferedReader input = new BufferedReader(new FileReader(myFileT));

				AnimaleList listaAnimali = new AnimaleList();
				listaAnimali.setIncludeTrashed(true);
				String temp = null;

				String sql1 = input.readLine();

				ArrayList<String> arrayValori = new ArrayList<String>();
				arrayValori.add(sql1);
				int count = 0;
				while ((temp = input.readLine()) != null) {
					count++;
					// sql1=sql1+","+ temp +"'";
					arrayValori.add(temp);
					if (count > 250) {
						String Messaggio = "Il file ha una dimensione maggiore di quella prevista, segmentarlo in più file";
						context.getRequest().setAttribute("messaggio", Messaggio);
						context.getRequest().setAttribute("risultato", listaAnimali);
						input.close();
						return "ElencoOK";
					}
				}
				HashMap<String, Animale> hm = new HashMap<String, Animale>();
				if (arrayValori.size() > 0) {
					Animale thisAnimale = new Animale();
					listaAnimali.setListaMicrochip(arrayValori);
					listaAnimali.setBuildProprietario(false);
					listaAnimali.buildList(db);

					ArrayList<String> arrayValoriTrovati = new ArrayList<String>();

					for (int g = 0; g < listaAnimali.size(); g++) {

						if (arrayValori.contains(((Animale) listaAnimali.get(g)).getMicrochip())) {
							arrayValori.remove(((Animale) listaAnimali.get(g)).getMicrochip());
							arrayValoriTrovati.add(((Animale) listaAnimali.get(g)).getMicrochip());
							thisAnimale = (Animale) listaAnimali.get(g);
							thisAnimale.setContributoPagato(db);
							if (thisAnimale.getDataCancellazione() == null)
								hm.put("MC presente in banca dati_" + thisAnimale.getMicrochip(), thisAnimale);
							else
								hm.put("MC trovato in banca dati ma eliminato logicamente_"
										+ thisAnimale.getMicrochip(), thisAnimale);

						} else if (arrayValoriTrovati.contains(((Animale) listaAnimali.get(g)).getMicrochip())) {
							thisAnimale = (Animale) listaAnimali.get(g);
							thisAnimale.setContributoPagato(db);
							if (thisAnimale.getDataCancellazione() == null)
								hm.put("MC presente in banca dati_" + thisAnimale.getMicrochip(), thisAnimale);
							else
								hm.put("MC trovato in banca dati ma eliminato logicamente_"
										+ thisAnimale.getMicrochip(), thisAnimale);

						}

					}

					if (arrayValori.size() > 0) {
						for (int s = 0; s < arrayValori.size(); s++) {
							thisAnimale = new Animale();
							thisAnimale.setMicrochip(arrayValori.get(s));
							hm.put("MC non presente in banca dati_" + thisAnimale.getMicrochip(), thisAnimale);
						}
					}

				}

				// lookup
				LookupList siteList = new LookupList(db, "lookup_asl_rif");
				siteList.addItem(-1, "-- SELEZIONA VOCE --");
				context.getRequest().setAttribute("AslList", siteList);

				LookupList specieList = new LookupList(db, "lookup_specie");
				specieList.addItem(-1, "--Tutti--");
				context.getRequest().setAttribute("specieList", specieList);

				PraticaList listaP = new PraticaList();
				ArrayList<PraticaDWR> listaPratiche = listaP.getListPratiche(db);
				LookupList praticheContributi = new LookupList(listaPratiche, -1);
				context.getRequest().setAttribute("listaPratiche", praticheContributi);

				context.getRequest().setAttribute("messaggio", "Risultato");
				context.getRequest().setAttribute("map", hm);
				input.close();
			} else {
				String Messaggio = "Seleziona un file per la verifica dei microchips";
				context.getRequest().setAttribute("messaggio", Messaggio);
				return "ElencoOK";
			}
		} catch (Exception e) {
			context.getRequest().setAttribute("errore", e.getMessage());
			logger.severe(timeToLog + " [CANINA] - User_id=" + getUserId(context)
					+ " Errore nella verifica della presenza dei microchip in BDR");
			// e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		return "ElencoOK";
	}

	public String executeCommandPrintCertificatoPrelievo(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina-documenti-view")) {
			return ("PermissionError");
		}

		Connection db = null;

		Cane thisCane = null;
		Gatto thisGatto = null;
		Furetto thisFuretto = null;
		int idAnimale = -1;
		int idSpecie = -1;

		try {
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
			// String HOST_CORRENTE=
			// ApplicationProperties.getProperty("APP_HOST_CANINA");
			/*
			 * String SERVER_BDU =
			 * InetAddress.getByName("hostAppBduPublic").getHostAddress();
			 * String APPLICATION_PORT = prefs.get("APPLICATION.PORT"); String
			 * APPLICATION_NAME = prefs.get("APPLICATION.NAME");
			 * 
			 * String url =
			 * "http://".concat(SERVER_BDU).concat(":").concat(APPLICATION_PORT
			 * ).concat("/").concat(APPLICATION_NAME).concat("/");
			 * 
			 * url="";context.getRequest().setAttribute("SERVER_BDU", url);
			 */
			db = this.getConnection(context);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			if ((String) context.getRequest().getParameter("isEmpty") == null) {

				SystemStatus systemStatus = this.getSystemStatus(context);
				String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
				String idSpecieString = (String) context.getRequest().getParameter("idSpecie");

				if (idAnimaleString != null && !("").equals(idAnimaleString)) {
					idAnimale = new Integer(idAnimaleString).intValue();
				}

				if (idSpecieString != null && !("").equals(idSpecieString)) {
					idSpecie = new Integer(idSpecieString).intValue();
				}

				Animale thisAnimale = new Animale(db, idAnimale);
				context.getRequest().setAttribute("thisAnimale", thisAnimale);

				EventoPrelievoDNA dati_prelievo = new EventoPrelievoDNA();
				dati_prelievo.getEventoPrelievo(db, idAnimale);
				context.getRequest().setAttribute("dati_prelievo", dati_prelievo);

				EventoRegistrazioneBDU dati_registrazione = new EventoRegistrazioneBDU();
				dati_registrazione.getEventoRegistrazione(db, idAnimale);
				context.getRequest().setAttribute("dati_registrazione", dati_registrazione);

				LineaProduttiva linea_proprietario = new LineaProduttiva(db, thisAnimale.getIdProprietario());
				LineaProduttiva linea_detentore = new LineaProduttiva(db, thisAnimale.getIdDetentore());
				context.getRequest().setAttribute("linea_proprietario", linea_proprietario);
				context.getRequest().setAttribute("linea_detentore", linea_detentore);

				Operatore proprietario = new Operatore();
				proprietario.queryRecordOperatorebyIdLineaProduttiva(db, thisAnimale.getIdProprietario());
				context.getRequest().setAttribute("proprietario", proprietario);

				Operatore detentore = new Operatore();
				detentore.queryRecordOperatorebyIdLineaProduttiva(db, thisAnimale.getIdDetentore());
				context.getRequest().setAttribute("detentore", detentore);

				LookupList specieList = new LookupList(db, "lookup_specie");
				specieList.addItem(-1, "--Tutti--");
				context.getRequest().setAttribute("specieList", specieList);

				LookupList razzaList = new LookupList();
				razzaList.setTable("lookup_razza");
				razzaList.setIdSpecie(idSpecie);
				razzaList.buildList(db);
				razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("razzaList", razzaList);

				LookupList tagliaList = new LookupList(db, "lookup_taglia");
				tagliaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("tagliaList", tagliaList);

				LookupList mantelloList = new LookupList(db, "lookup_mantello");
				mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("mantelloList", mantelloList);

				ComuniAnagrafica c = new ComuniAnagrafica();
				// PER ORA PRENDO TUTTI I COMUNI E NON SOLO QUELLI RELATIVI
				// ALL'ASL
				// UTENTE
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, 1);
				LookupList comuniList = new LookupList(listaComuni, -1);
				comuniList.addItem(-1, "");
				context.getRequest().setAttribute("comuniList", comuniList);

				// Lista veterinari
				User utente = new User();
				utente.setBuildContact(true);
				utente.buildRecord(db, dati_prelievo.getIdVeterinario());
				context.getRequest().setAttribute("veterinario", utente.getContact());

			}

			else {
				return getReturn(context, "viewCertificatoPrelievoDNAEmpty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "viewCertificatoPrelievoDNA");

	}

	public String executeCommandPrintMicrochips(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina-documenti-view")) {
			return ("PermissionError");
		}

		Connection db = null;
		ArrayList<String> microchips = new ArrayList<String>();

		try {
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");

			// db = this.getConnection(context);

			CFSFileReader fileReader = new CFSFileReader("C:/microchips.csv", 8);
			fileReader.setColumnDelimiter(",");

			/*
			 * String SERVER_BDU =
			 * InetAddress.getByName("hostAppBduPublic").getHostAddress();
			 * String APPLICATION_PORT = prefs.get("APPLICATION.PORT"); String
			 * APPLICATION_NAME = prefs.get("APPLICATION.NAME");
			 * 
			 * String url =
			 * "http://".concat(SERVER_BDU).concat(":").concat("8080"
			 * ).concat("/").concat("bdu").concat("/");
			 * 
			 * url="";context.getRequest().setAttribute("SERVER_BDU", url);
			 */// header
				// MODIFICATO PER SNELLIRE L'IMPORT
			CFSFileReader.Record record = null; // fileReader.nextLine();

			// header = record.data;

			// add the header to the log file

			// recordError(null, record.line, 0);

			int first = 0;
			ArrayList thisRecord = new ArrayList();

			while ((record = fileReader.nextLine()) != null) {

				// get the record
				thisRecord = record.data;

				// get the line and pad it if necessary for missing
				// columns

				// RECUPERO TUTTI I MICROCHIPS DA BDU

				// TODO PRENDERE DA PROPERTIER

				String microchip = thisRecord.get(0).toString().replace("\"", "").trim();
				microchips.add(microchip);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		// System.out.println(microchips.size());

		context.getRequest().setAttribute("microchips", microchips);

		return getReturn(context, "viewCertificatoBarcode");

	}

	public String executeCommandImportNumeroProvinciaIscrizioneOrdineLLPP(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina-documenti-view")) {
			return ("PermissionError");
		}

		Connection db = null;

		try {
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");

			// db = this.getConnection(context);

			this.getClass().getResource("elenco_iscritti.csv");
			File file_to_read = new File("elenco_iscritti.csv");
			file_to_read.getPath();
			CFSFileReader fileReader = new CFSFileReader(this.getClass().getResource("elenco_iscritti.csv").getPath(),
					8);
			fileReader.setColumnDelimiter(",");

			// String SERVER_BDU =
			// InetAddress.getByName("hostAppBduPublic").getHostAddress();
			// String APPLICATION_PORT = prefs.get("APPLICATION.PORT");
			// String APPLICATION_NAME = prefs.get("APPLICATION.NAME");
			//
			// String url =
			// "http://".concat(SERVER_BDU).concat(":").concat("8080").concat("/").concat("bdu").concat("/");
			//
			// url="";context.getRequest().setAttribute("SERVER_BDU", url);
			// header
			// MODIFICATO PER SNELLIRE L'IMPORT
			CFSFileReader.Record record = null; // fileReader.nextLine();

			// header = record.data;

			// add the header to the log file

			// recordError(null, record.line, 0);

			int first = 0;
			ArrayList thisRecord = new ArrayList();
			int recordCount = 1;
			String line = null;
			BufferedWriter fos = null;

			File errorFile = new File("veterinari_" + "_error.csv");
			fos = new BufferedWriter(new FileWriter(errorFile));

			while ((record = fileReader.nextLine()) != null) {

				// get the record
				thisRecord = record.data;

				line = fileReader.padLine(record.line, 1 - thisRecord.size());
				++recordCount;
				// System.out.println(recordCount);

				if (thisRecord.get(0) != null && !("").equals(thisRecord.get(0))
						&& !("cognome").equals(thisRecord.get(0))) {

					String codice_fiscale = thisRecord.get(3).toString().replace("\"", "").trim();
					if (codice_fiscale != null && !("").equals(codice_fiscale)) {
						Connection con = GestoreConnessioni.getConnection();

						String nr_iscrizione = thisRecord.get(4).toString().replace("\"", "").trim();
						String provincia_iscrizione = thisRecord.get(5).toString().replace("\"", "").trim();
						int id_provincia = -1;
						if (("AV").equals(provincia_iscrizione)) {
							id_provincia = 64;
						}
						if (("NA").equals(provincia_iscrizione)) {
							id_provincia = 63;
						}
						if (("CE").equals(provincia_iscrizione)) {
							id_provincia = 61;
						}
						if (("SA").equals(provincia_iscrizione)) {
							id_provincia = 65;
						}

						StringBuffer query = new StringBuffer();
						query.append("update contact set id_provincia_iscrizione_albo_vet_privato = ?,   nr_iscrione_albo_vet_privato = ? where codice_fiscale = ?");
						PreparedStatement pst = con.prepareStatement(query.toString());
						pst.setInt(1, id_provincia);
						pst.setString(2, nr_iscrizione);
						pst.setString(3, codice_fiscale);

						int i = pst.executeUpdate();

						GestoreConnessioni.freeConnection(con);

						if (i > 0) {
							recordError("OK: LP  " + codice_fiscale + ", aggiornato ", line, recordCount, fos);
						} else {
							recordError("NOK: LP  " + codice_fiscale + ", aggiornato ", line, recordCount, fos);
						}
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		return "-none-";

	}

	public String executeCommandImportNumeroProvinciaIscrizioneOrdineLLPPNoCF(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina-documenti-view")) {
			return ("PermissionError");
		}

		Connection db = null;

		try {
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");

			// db = this.getConnection(context);

			this.getClass().getResource("elenco_iscritti_bn.csv");
			CFSFileReader fileReader = new CFSFileReader(this.getClass().getResource("elenco_iscritti_bn.csv")
					.getPath(), 8);
			fileReader.setColumnDelimiter(",");

			// String SERVER_BDU =
			// InetAddress.getByName("hostAppBduPublic").getHostAddress();
			// String APPLICATION_PORT = prefs.get("APPLICATION.PORT");
			// String APPLICATION_NAME = prefs.get("APPLICATION.NAME");
			//
			// String url =
			// "http://".concat(SERVER_BDU).concat(":").concat("8080").concat("/").concat("bdu").concat("/");
			//
			// url="";context.getRequest().setAttribute("SERVER_BDU", url);
			// header
			// MODIFICATO PER SNELLIRE L'IMPORT
			CFSFileReader.Record record = null; // fileReader.nextLine();

			// header = record.data;

			// add the header to the log file

			// recordError(null, record.line, 0);

			int first = 0;
			ArrayList thisRecord = new ArrayList();
			int recordCount = 1;
			String line = null;
			BufferedWriter fos = null;

			File errorFile = new File("veterinari_bn_" + "_error.csv");
			fos = new BufferedWriter(new FileWriter(errorFile));

			while ((record = fileReader.nextLine()) != null) {

				// get the record
				thisRecord = record.data;

				line = fileReader.padLine(record.line, 1 - thisRecord.size());
				++recordCount;
				// System.out.println(recordCount);

				if (thisRecord.get(0) != null && !("").equals(thisRecord.get(0))
						&& !("cognome").equals(thisRecord.get(0))) {

					// String codice_fiscale = thisRecord.get(3)
					// .toString().replace("\"", "").trim();

					String nr_iscrizione = thisRecord.get(4).toString().replace("\"", "").trim();
					if (nr_iscrizione != null && !("").equals(nr_iscrizione)) {
						Connection con = GestoreConnessioni.getConnection();

						// String nr_iscrizione =
						// thisRecord.get(4).toString().replace("\"",
						// "").trim();
						String provincia_iscrizione = thisRecord.get(5).toString().replace("\"", "").trim();
						int id_provincia = -1;
						if (("AV").equals(provincia_iscrizione)) {
							id_provincia = 64;
						}
						if (("NA").equals(provincia_iscrizione)) {
							id_provincia = 63;
						}
						if (("CE").equals(provincia_iscrizione)) {
							id_provincia = 61;
						}
						if (("SA").equals(provincia_iscrizione)) {
							id_provincia = 65;
						}
						if (("BN").equals(provincia_iscrizione)) {
							id_provincia = 62;
						}

						String nome = thisRecord.get(2).toString().replace("\"", "").trim();
						String cognome = thisRecord.get(1).toString().replace("\"", "").trim();

						nome = nome.trim();
						cognome = cognome.trim();

						StringBuffer query = new StringBuffer();
						query.append("update contact set id_provincia_iscrizione_albo_vet_privato = ?,   nr_iscrione_albo_vet_privato = ? where namefirst ilike ? and namelast ilike ? and enabled = true");
						PreparedStatement pst = con.prepareStatement(query.toString());
						pst.setInt(1, id_provincia);
						pst.setString(2, nr_iscrizione);
						pst.setString(3, nome);
						pst.setString(4, cognome);

						int i = pst.executeUpdate();

						GestoreConnessioni.freeConnection(con);

						if (i > 0) {
							recordError("OK: LP  " + nome + " - " + cognome + " aggiornato ", line, recordCount, fos);
						} else {
							recordError("NOK: LP  " + nome + " - " + cognome + " aggiornato ", line, recordCount, fos);
						}
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);

		}

		return "-none-";

	}

	private void recordError(String error, String line, int lineNumber, BufferedWriter fos) {
		try {

			// if (lineNumber > 0)
			// this.idStato = terminato_con_errori;
			// log errors in the temp file created under $FILELIBRARY/_imports/
			if (lineNumber == 0) {
				// header:append error column
				line += "," + "\"_Descrizione_Errore\"";
			} else if (lineNumber == -1) {
				// general error, mostly before import started
				line += error;
			} else if (lineNumber > 0) {
				// append the error
				line += ",\"" + error + "\"";

				// a record has failed, increment the failure count
				// this.incrementTotalFailedRecords();
			}

			// add next line character
			// TODO: Change this to a CUSTOM row delimiter if import type is
			// CUSTOM
			line += "\n";

			fos.write(line);
			fos.flush();
		} catch (IOException e) {
			// import should not fail because of logging error
			e.printStackTrace();
		}
	}

	public String executeCommandSearchFormEisitLeish(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina_registrazioni-view")) {

			return ("PermissionError");

		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = getConnection(context);

			this.deletePagedListInfo(context, "esitiLeishListInfo");
			PagedListInfo eventiListInfo = this.getPagedListInfo(context, "esitiLeishListInfo");
			eventiListInfo.setCurrentLetter("");
			eventiListInfo.setCurrentOffset(0);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			siteList.removeElementByLevel(13);
			context.getRequest().setAttribute("AslList", siteList);

			// Parametro ricerca non necessario
			// LookupList specieList = new LookupList(db, "lookup_specie");
			// specieList.addItem(-1, "--Tutti--");
			// context.getRequest().setAttribute("specieList", specieList);

			LookupList esitoList = new LookupList(db, "lookup_esito_leishmania");
			esitoList.addItem(-1, "--Tutti--");
			context.getRequest().setAttribute("esitoList", esitoList);

			Calendar cal = new GregorianCalendar();
			int annoCorrenteInt = cal.get(Calendar.YEAR);
			String annoCorrente = new Integer(annoCorrenteInt).toString();
			// int annoCorrenteInt = Integer.parseInt(annoCorrente);
			LookupList annoList = new LookupList(); // creo una nuova lookup

			annoList.addItem(annoCorrenteInt - 3, String.valueOf(annoCorrenteInt - 3));
			annoList.addItem(annoCorrenteInt - 2, String.valueOf(annoCorrenteInt - 2));
			annoList.addItem(annoCorrenteInt - 1, String.valueOf(annoCorrenteInt - 1));
			annoList.addItem(annoCorrenteInt, annoCorrente);
			annoList.addItem(-1, "Tutti");

			context.getRequest().setAttribute("annoList", annoList);

			Calendar now = Calendar.getInstance();
			int year = (now.get(Calendar.YEAR));
			String anno = String.valueOf(year);

			context.getRequest().setAttribute("daDefault", "01/01/" + anno);

			context.getRequest().setAttribute("aDefault", "31/12/" + anno);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		// addModuleBean(context, "Search Evento", "Evento Search");

		addModuleBean(context, "Anagrafe Animali", "AnimaleAction");

		getReturn(context, "SearchEsitiLeish");
		return ("SearchEsitiLeishOK");

	}

	public String executeCommandSearchEsitiLeish(ActionContext context) {
		if (!hasPermission(context, "anagrafe_canina_registrazioni-view")) {
			if (!hasPermission(context, "anagrafe_canina_registrazioni-view")) {
				return ("PermissionError");
			}
		}
		PagedListInfo EventiListInfo = this.getPagedListInfo(context, "esitiLeishListInfo");
		String servletPath = context.getRequest().getServletPath();
		String userAction = servletPath.substring(servletPath.indexOf("/") + 1, servletPath.indexOf(".do"));
		EventiListInfo.setLink(userAction + ".do?command=SearchEsitiLeish");
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = this.getConnection(context);
			LeishList listaEventi = new LeishList();
			listaEventi.setPagedListInfo(EventiListInfo);
			listaEventi.setPagedListInfo(EventiListInfo);

			EventiListInfo.setSearchCriteria(listaEventi, context);
			listaEventi.setPagedListInfo(EventiListInfo);

			listaEventi.buildList(db);

			context.getRequest().setAttribute("ListaEsitiLeish", listaEventi);

			LookupList registrazioniList = new LookupList(db, "lookup_tipologia_registrazione");
			registrazioniList.addItem(-1, "--Tutte le registrazioni--");
			context.getRequest().setAttribute("registrazioniList", registrazioniList);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			// Action di provenienza
			// String servletPath = context.getRequest().getServletPath();
			String actionFrom = servletPath.substring(servletPath.indexOf("/") + 1, servletPath.indexOf(".do"));
			context.getRequest().setAttribute("actionFrom", actionFrom);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return getReturn(context, "ViewEsitiLeish");

	}

	// public String
	// executeCommandPrepareCambioProprietarioDetentore(ActionContext context){
	//
	// if (!hasPermission(context, "anagrafe_canina_operazioni_hd-add")) {
	//
	// return ("PermissionError");
	//
	// }
	//
	// return getReturn(context, "ViewEsitiLeish");
	//
	// }

	public String executeCommandPrepareAddPhoto(ActionContext context) {
		// if (!hasPermission(context, "anagrafe_canina_registrazioni-view")) {
		// if (!hasPermission(context, "anagrafe_canina_registrazioni-view")) {
		// return ("PermissionError");
		// }
		// }

		Connection db = null;
		try {

			db = this.getConnection(context);
			String idAnimale = context.getRequest().getParameter("idAnimale");

			if (idAnimale != null && !("").equals(idAnimale)) {
				int idAnimaleInt = Integer.valueOf(idAnimale);
				Animale thisAnimale = new Animale(db, idAnimaleInt);
				context.getRequest().setAttribute("animale", thisAnimale);
			}

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return getReturn(context, "UploadPhoto");

	}
	
	
	public String executeCommandAddAccMultipla(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina-anagrafe_canina-add")) {
			return ("PermissionError");
		}

		SystemStatus systemStatus = this.getSystemStatus(context);
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		Connection db = null;
		try {
			Thread t = Thread.currentThread();
			db = this.getConnection(context);

			Operatore soggetto = new Operatore();
			int idLineaProduttiva = -1;
			if ((String) context.getRequest().getParameter("opId") != null) {
				idLineaProduttiva = new Integer((String) context.getRequest().getParameter("opId")).intValue();
			} else {
				idLineaProduttiva = new Integer((String) context.getRequest().getAttribute("opId")).intValue();
			}
			soggetto.queryRecordOperatorebyIdLineaProduttiva(db, idLineaProduttiva);

			context.getRequest().setAttribute("OperatoreAdded", soggetto);
			if (context.getRequest().getParameter("tipologiaSoggetto") != null
					&& !"".equals(context.getRequest().getParameter("tipologiaSoggetto")))
				context.getRequest().setAttribute("TipologiaSoggetto",
						context.getRequest().getParameter("tipologiaSoggetto"));
			else
				context.getRequest().setAttribute("TipologiaSoggetto",
						context.getRequest().getAttribute("TipologiaSoggetto"));
			

			int idSpecie = Cane.idSpecie;
			int idProprietario = -1;
			Animale thisAnimale = (Animale) context.getRequest().getAttribute("animale");

			
			
			if (thisAnimale != null) {
				idSpecie = thisAnimale.getIdSpecie();
			} else {
				if ((String) context.getRequest().getParameter("idSpecie") != null
						&& !("-1").equals((String) context.getRequest().getParameter("idSpecie"))) {
					idSpecie = new Integer((String) context.getRequest().getParameter("idSpecie")).intValue();
				}
				if ((String) context.getRequest().getParameter("idLinea") != null
						&& !("-1").equals((String) context.getRequest().getParameter("idLinea"))) {
					idProprietario = new Integer((String) context.getRequest().getParameter("idLinea"));
				}
			}

			// if ((String) context.getRequest().getParameter("microchip") !=
			// null
			// && !"".equals((String) context.getRequest()
			// .getParameter("microchip"))) {
			// //idAnimale = new Integer((String) context.getRequest()
			// // .getParameter("idAnimale")).intValue();
			// thisAnimale = new Animale();
			// thisAnimale.setMicrochip((String) context.getRequest()
			// .getParameter("microchip"));
			// }

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			LookupList regioniList = new LookupList(db, "lookup_regione");
			regioniList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("regioniList", regioniList);

			LookupList specieList = new LookupList();
			specieList.setTable("lookup_specie");
			specieList.buildList(db);
			// specieList.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("specieList", specieList);

			LookupList razzaList = new LookupList();
			razzaList.setTable("lookup_razza");
			razzaList.setIdSpecie(idSpecie);
			razzaList.buildList(db);
			razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("razzaList", razzaList);

			// Lookup esito
			LookupList esitoList = new LookupList(db, "lookup_esito_controlli");
			esitoList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("esitoList", esitoList);

			LookupList tagliaList = new LookupList(db, "lookup_taglia");
			tagliaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("tagliaList", tagliaList);

			LookupList mantelloList = new LookupList();
			mantelloList.setTable("lookup_mantello");
			mantelloList.setIdSpecie(idSpecie);
			mantelloList.buildList(db);
			mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("mantelloList", mantelloList);

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, 1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("comuniList", comuniList);
			
			ComuniAnagrafica c_all = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni_all = c.buildList(db, -1, -1);
			LookupList comuniList_all = new LookupList(listaComuni_all, -1);
			comuniList_all.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("comuniList_all", comuniList_all);

			LookupList nazioniList = new LookupList(db,	"lookup_nazioni");
			nazioniList.addItem(-1, "--Seleziona--");
			nazioniList.removeElementByCode(106);
			context.getRequest().setAttribute("nazioniList", nazioniList);
			
		
			LookupList provinceList = new LookupList(db, "lookup_province");
			provinceList.addItem(-1, "Seleziona provincia");
			context.getRequest().setAttribute("provinceList", provinceList);

			LookupList veterinari = new LookupList(db, "elenco_veterinari_chippatori");// ,
																						// user.getSiteId(),
																						// 1);
			veterinari.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("veterinariList", veterinari);

			LookupList veterinariPrivati = new LookupList(db, "elenco_veterinari");
			veterinariPrivati.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("veterinariPrivatiList", veterinariPrivati);
			
			if(context.getRequest().getAttribute("ErrorBlocco")!=null && !((String)context.getRequest().getAttribute("ErrorBlocco")).equals(""))
				context.getRequest().setAttribute("ErrorBlocco", (String)context.getRequest().getAttribute("ErrorBlocco"));

			Operatore proprietario = new Operatore();
			if (idProprietario > 0)
				proprietario.queryRecordOperatorebyIdLineaProduttiva(db, idProprietario);

			switch (idSpecie) {

			case Cane.idSpecie: {

				Cane newCane = (Cane) context.getRequest().getAttribute("Cane");

				newCane.setIdSpecie(idSpecie);

				if (idProprietario > -1) {
					newCane.setIdProprietario(idProprietario);
					newCane.setIdDetentore(idProprietario);
					newCane.setProprietario(proprietario);
					newCane.setDetentore(proprietario);
				}

				if (((String) context.getSession().getAttribute("caller") != null
						&& !("").equals((String) context.getSession().getAttribute("caller")) && (ApplicationProperties
							.getProperty("VAM_ID").equals((String) context.getSession().getAttribute("caller"))))) {
					if ((String) context.getRequest().getParameter("microchip") != null
							&& !"".equals((String) context.getRequest().getParameter("microchip"))) {
						newCane.setMicrochip((String) context.getRequest().getParameter("microchip"));
					}
				}
				context.getRequest().setAttribute("Cane", newCane);
				break;
			}
			case Gatto.idSpecie: {
				Gatto newGatto = (Gatto) context.getRequest().getAttribute("Gatto");
				newGatto.setIdSpecie(idSpecie);

				if (idProprietario > -1) {
					newGatto.setIdProprietario(idProprietario);
					newGatto.setIdDetentore(idProprietario);
					newGatto.setProprietario(proprietario);
					newGatto.setDetentore(proprietario);
				}
				context.getRequest().setAttribute("Gatto", newGatto);
				break;
			}
			case Furetto.idSpecie: {
				Furetto newFuretto = (Furetto) context.getRequest().getAttribute("Furetto");
				newFuretto.setIdSpecie(idSpecie);

				if (idProprietario > -1) {
					newFuretto.setIdProprietario(idProprietario);
					newFuretto.setIdDetentore(idProprietario);
					newFuretto.setProprietario(proprietario);
					newFuretto.setDetentore(proprietario);
				}
				context.getRequest().setAttribute("Furetto", newFuretto);
				break;
			}
			default:
				break;
			}

			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		String opId = context.getRequest().getParameter("idLinea");
		String origine = context.getRequest().getParameter("origine");
		if (opId != null && !opId.equals("")) {
			context.getRequest().setAttribute("opId", opId);
			context.getRequest().setAttribute("origine", origine);
		}

		
		context.getRequest().setAttribute("dataOggi",(String) context.getSession().getAttribute("caller"));
		
		
		/**************** PARAMETRI EVENTUALE ORIGINE ANIMALE ***************/
		context.getRequest().setAttribute("origine_da",(String) context.getRequest().getParameter("origine_da"));
		context.getRequest().setAttribute("tipo_origine",(String) context.getRequest().getParameter("tipo_origine"));
				
		context.getRequest().setAttribute("regione_ritrovamento",(String) context.getRequest().getParameter("regione_ritrovamento"));
		context.getRequest().setAttribute("provincia_ritrovamento",(String) context.getRequest().getParameter("provincia_ritrovamento"));
		context.getRequest().setAttribute("comune_ritrovamento",(String) context.getRequest().getParameter("comune_ritrovamento"));
		context.getRequest().setAttribute("indirizzo_ritrovamento",(String) context.getRequest().getParameter("indirizzo_ritrovamento"));
		context.getRequest().setAttribute("data_ritrovamento",(String) context.getRequest().getParameter("data_ritrovamento"));

		context.getRequest().setAttribute("flagFuoriRegione",(String) context.getRequest().getParameter("flagFuoriRegione"));
		context.getRequest().setAttribute("idRegioneProvenienza",(String) context.getRequest().getParameter("idRegioneProvenienza"));
		context.getRequest().setAttribute("noteAnagrafeFr",(String) context.getRequest().getParameter("noteAnagrafeFr"));

		context.getRequest().setAttribute("flagSenzaOrigine",(String) context.getRequest().getParameter("flagSenzaOrigine"));
		
		context.getRequest().setAttribute("idNazioneProvenienza",(String) context.getRequest().getParameter("idNazioneProvenienza"));
		context.getRequest().setAttribute("noteNazioneProvenienza",(String) context.getRequest().getParameter("noteNazioneProvenienza"));
		
		context.getRequest().setAttribute("flagAcquistoOnline",(String) context.getRequest().getParameter("flagAcquistoOnline"));
		context.getRequest().setAttribute("sitoWebAcquisto",(String) context.getRequest().getParameter("sitoWebAcquisto"));
		context.getRequest().setAttribute("noteAcquistoOnline",(String) context.getRequest().getParameter("noteAcquistoOnline"));
		/********************************************************************/
		
		
		addModuleBean(context, "Add Account", "Accounts Add");
		context.getRequest().setAttribute("systemStatus", this.getSystemStatus(context));
		// if a different module reuses this action then do a explicit return
		if (context.getRequest().getParameter("actionSource") != null) {
			return getReturn(context, "AddAccount");
		}

		if ((String) context.getSession().getAttribute("caller") != null
				&& !("").equals((String) context.getSession().getAttribute("caller"))
				&& (ApplicationProperties.getProperty("VAM_ID").equals((String) context.getSession().getAttribute(
						"caller")))) {

			return ("AddNoNAVAccMultiplaOK");
		}

		context.getRequest().setAttribute("dataOggi", new Date());
		
		return getReturn(context, "AddAccMultipla");
	}
	
	
	public String executeCommandInsertAccMultipla(ActionContext context) throws SQLException {

		if (!hasPermission(context, "anagrafe_canina-anagrafe_canina-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		PreparedStatement pst = null;
		boolean recordInserted = false;
		boolean isValid = false;
		Animale insertedAnimale = null;

		boolean contributoDisponibile = false;// se l'animale è associabile alla
		// pratica contributi
		// selezionata
		boolean contributoRichiesto = false;

		boolean catturato = false;
		EventoCattura cattura = null;
		
		context.getRequest().setAttribute("stopSubmit", context.getRequest().getParameter("stopSubmit"));
		context.getRequest().setAttribute("dataRegistrazione", context.getRequest().getParameter("dataRegistrazione"));
		context.getRequest().setAttribute("opId", context.getRequest().getParameter("opId"));
		
		context.getRequest().setAttribute("idSpecieAccMultipla", context.getRequest().getParameter("idSpecieAccMultipla"));
		context.getRequest().setAttribute("razzaAccMultipla", context.getRequest().getParameter("razzaAccMultipla"));
		context.getRequest().setAttribute("sessoAccMultipla", context.getRequest().getParameter("sessoAccMultipla"));
		context.getRequest().setAttribute("dataNascitaAccMultipla", context.getRequest().getParameter("dataNascitaAccMultipla"));
		context.getRequest().setAttribute("mantelloAccMultipla", context.getRequest().getParameter("mantelloAccMultipla"));
		context.getRequest().setAttribute("tagliaAccMultipla", context.getRequest().getParameter("tagliaAccMultipla"));
		context.getRequest().setAttribute("dataChippaturaAccMultipla", context.getRequest().getParameter("dataChippaturaAccMultipla"));
		context.getRequest().setAttribute("opIdDetentore", context.getRequest().getParameter("opIdDetentore"));
		context.getRequest().setAttribute("mcAccMultipla", context.getRequest().getParameter("mcAccMultipla"));
		

		// Integer orgId = null;
		Animale newAnimale = (Animale) context.getRequest().getAttribute("Animale");
		Cane newCane = (Cane) context.getRequest().getAttribute("Cane");
		Gatto newGatto = (Gatto) context.getRequest().getAttribute("Gatto");
		Furetto newFuretto = (Furetto) context.getRequest().getAttribute("Furetto");

		if(newAnimale!=null)
		{
			if(context.getRequest().getParameter("microchip")!=null && !context.getRequest().getParameter("microchip").equals(""))
				newAnimale.setMicrochip(context.getRequest().getParameter("microchip"));
			else
				newAnimale.setMicrochip(context.getRequest().getParameter("mcAccMultipla"));
			if(context.getRequest().getParameter("dataRegistrazione")!=null && !context.getRequest().getParameter("dataRegistrazione").equals(""))
				newAnimale.setDataRegistrazione(context.getRequest().getParameter("dataRegistrazione"));
			else
				newAnimale.setDataRegistrazione(context.getRequest().getParameter("dataRegistrazioneAccMultipla"));
			if(context.getRequest().getParameter("idRazza")!=null && !context.getRequest().getParameter("idRazza").equals("") && !context.getRequest().getParameter("idRazza").equals("-1"))
				newAnimale.setIdRazza(context.getRequest().getParameter("idRazza"));
			else
				newAnimale.setIdRazza(context.getRequest().getParameter("razzaAccMultipla"));
			if(context.getRequest().getParameter("idSpecie")!=null && !context.getRequest().getParameter("idSpecie").equals("") && !context.getRequest().getParameter("idSpecie").equals("-1"))
				newAnimale.setIdSpecie(context.getRequest().getParameter("idSpecie"));
			else
				newAnimale.setIdSpecie(context.getRequest().getParameter("idSpecieAccMultipla"));
			if(context.getRequest().getParameter("sesso")!=null)
				newAnimale.setSesso(context.getRequest().getParameter("sesso"));
			else
				newAnimale.setSesso(context.getRequest().getParameter("sessoAccMultipla"));
			if(context.getRequest().getParameter("idTipoMantello")!=null && !context.getRequest().getParameter("idTipoMantello").equals("")  && !context.getRequest().getParameter("idTipoMantello").equals("-1"))
				newAnimale.setIdTipoMantello(context.getRequest().getParameter("idTipoMantello"));
			else
				newAnimale.setIdTipoMantello(context.getRequest().getParameter("mantelloAccMultipla"));
			if(context.getRequest().getParameter("idTaglia")!=null && !context.getRequest().getParameter("idTaglia").equals("")  && !context.getRequest().getParameter("idTaglia").equals("-1"))
				newAnimale.setIdTaglia(context.getRequest().getParameter("idTaglia"));
			else
				newAnimale.setIdTaglia(context.getRequest().getParameter("tagliaAccMultipla"));
			if(context.getRequest().getParameter("dataNascita")!=null && !context.getRequest().getParameter("dataNascita").equals(""))
				newAnimale.setDataNascita(context.getRequest().getParameter("dataNascita"));
			else
				newAnimale.setDataNascita(context.getRequest().getParameter("dataNascitaAccMultipla"));
			if(context.getRequest().getParameter("dataMicrochip")!=null  && !context.getRequest().getParameter("dataMicrochip").equals(""))
				newAnimale.setDataMicrochip(context.getRequest().getParameter("dataMicrochip"));
			else
				newAnimale.setDataMicrochip(context.getRequest().getParameter("dataChippaturaAccMultipla"));
		}
		Animale thisAnimale = null;

		int id_pratica = -1;

		contributoRichiesto = false;
		Pratica praticaContributi = null;
		boolean praticaSceltaOK = true;

		try {

			EsitoControllo esitoTatu = null;
			EsitoControllo esitoMc = null;

			UserBean user = (UserBean) context.getSession().getAttribute("User");
			String ip = user.getUserRecord().getIp();

			newAnimale.setIdUtenteInserimento(user.getUserId());

			if (newAnimale.getMicrochip() != null && !("").equals(newAnimale.getMicrochip())) {
				esitoMc = DwrUtil.verificaInserimentoAnimale(newAnimale.getMicrochip(), user.getUserId());
			}

			if (newAnimale.getTatuaggio() != null && !("").equals(newAnimale.getTatuaggio())) {
				esitoTatu = DwrUtil.verificaInserimentoAnimale(newAnimale.getTatuaggio(), user.getUserId());
			}

			db = this.getConnection(context);

			if (newAnimale.getIdSpecie() != Furetto.idSpecie
					&& (context.getRequest().getParameter("flagCattura") == null
							|| ("").equals(context.getRequest().getParameter("flagCattura")) || !("on").equals(context
							.getRequest().getParameter("flagCattura")))) {
				newCane.setFlagCattura(false);
				newCane.setIdComuneCattura(-1);
				newCane.setLuogoCattura("");
				newCane.setVerbaleCattura("");
				newCane.setDataCattura("");
			}
			
			
			if(newAnimale!=null && newAnimale.getIdSpecie()==Cane.idSpecie)
			{
				newCane.setDataRegistrazione(newAnimale.getDataRegistrazione());
				newCane.setIdRazza(newAnimale.getIdRazza());
				newCane.setIdTaglia(newAnimale.getIdTaglia());
				newCane.setDataMicrochip(newAnimale.getDataMicrochip());
				newCane.setDataNascita(newAnimale.getDataNascita());
				newCane.setSesso(newAnimale.getSesso());
				newCane.setIdTipoMantello(newAnimale.getIdTipoMantello());
				newCane.setMicrochip(newAnimale.getMicrochip());
			}
			else if(newAnimale!=null && newAnimale.getIdSpecie()==Gatto.idSpecie)
			{
				newGatto.setDataRegistrazione(newAnimale.getDataRegistrazione());
				newGatto.setIdRazza(newAnimale.getIdRazza());
				newGatto.setIdTaglia(newAnimale.getIdTaglia());
				newGatto.setDataMicrochip(newAnimale.getDataMicrochip());
				newGatto.setDataNascita(newAnimale.getDataNascita());
				newGatto.setSesso(newAnimale.getSesso());
				newGatto.setIdTipoMantello(newAnimale.getIdTipoMantello());
				newGatto.setMicrochip(newAnimale.getMicrochip());
			}
			else if(newAnimale!=null && newAnimale.getIdSpecie()==Furetto.idSpecie)
			{
				newFuretto.setDataRegistrazione(newAnimale.getDataRegistrazione());
				newFuretto.setIdRazza(newAnimale.getIdRazza());
				newFuretto.setIdTaglia(newAnimale.getIdTaglia());
				newFuretto.setDataMicrochip(newAnimale.getDataMicrochip());
				newFuretto.setDataNascita(newAnimale.getDataNascita());
				newFuretto.setSesso(newAnimale.getSesso());
				newFuretto.setIdTipoMantello(newAnimale.getIdTipoMantello());
				newFuretto.setMicrochip(newAnimale.getMicrochip());
			}

			switch (newAnimale.getIdSpecie()) {
			case Cane.idSpecie: {
				thisAnimale = (Animale) newCane;
				break;
			}
			case Gatto.idSpecie: {
				thisAnimale = (Animale) newGatto;
				break;
			}
			case Furetto.idSpecie: {
				thisAnimale = (Animale) newFuretto;
				break;
			}
			}

			if (context.getRequest().getParameter("idProprietario") != null
					&& !context.getRequest().getParameter("idProprietario").equals("")
					&& !context.getRequest().getParameter("idProprietario").equals("-1")) {

				Operatore soggettoAdded = new Operatore();
				soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, new Integer(context.getRequest()
						.getParameter("idProprietario")).intValue());

				thisAnimale.setProprietario(soggettoAdded);

			}
			
			
			if (context.getRequest().getParameter("idProprietarioProvenienza") != null
					&& !context.getRequest().getParameter("idProprietarioProvenienza").equals("-1")
					//&& context.getRequest().getParameter("nomeProprietarioProvenienza")!= null
					//&& !context.getRequest().getParameter("nomeProprietarioProvenienza").equals("")
					){
						Operatore soggettoProvenienzaAdded = new Operatore();
						soggettoProvenienzaAdded.queryRecordOperatorebyIdLineaProduttiva(db, new Integer(context.getRequest()
						.getParameter("idProprietarioProvenienza")).intValue());

						thisAnimale.setProprietarioProvenienza(soggettoProvenienzaAdded);
				}
				
				/**************** PARAMETRI EVENTUALE ORIGINE ANIMALE ***************/
				context.getRequest().setAttribute("origine_da",(String) context.getRequest().getParameter("origine_da"));
				context.getRequest().setAttribute("tipo_origine",(String) context.getRequest().getParameter("tipo_origine"));
						
				context.getRequest().setAttribute("regione_ritrovamento",(String) context.getRequest().getParameter("regione_ritrovamento"));
				context.getRequest().setAttribute("provincia_ritrovamento",(String) context.getRequest().getParameter("provincia_ritrovamento"));
				context.getRequest().setAttribute("comune_ritrovamento",(String) context.getRequest().getParameter("comune_ritrovamento"));
				context.getRequest().setAttribute("indirizzo_ritrovamento",(String) context.getRequest().getParameter("indirizzo_ritrovamento"));
				context.getRequest().setAttribute("data_ritrovamento",(String) context.getRequest().getParameter("data_ritrovamento"));

				context.getRequest().setAttribute("flagSenzaOrigine",(String) context.getRequest().getParameter("flagSenzaOrigine"));
				
				context.getRequest().setAttribute("flagFuoriRegione",(String) context.getRequest().getParameter("flagFuoriRegione"));
				context.getRequest().setAttribute("idRegioneProvenienza",(String) context.getRequest().getParameter("idRegioneProvenienza"));
				context.getRequest().setAttribute("noteAnagrafeFr",(String) context.getRequest().getParameter("noteAnagrafeFr"));

				
				context.getRequest().setAttribute("idNazioneProvenienza",(String) context.getRequest().getParameter("idNazioneProvenienza"));
				context.getRequest().setAttribute("noteNazioneProvenienza",(String) context.getRequest().getParameter("noteNazioneProvenienza"));
				
				context.getRequest().setAttribute("flagAcquistoOnline",(String) context.getRequest().getParameter("flagAcquistoOnline"));
				context.getRequest().setAttribute("sitoWebAcquisto",(String) context.getRequest().getParameter("sitoWebAcquisto"));
				context.getRequest().setAttribute("noteAcquistoOnline",(String) context.getRequest().getParameter("noteAcquistoOnline"));
				/********************************************************************/
				
				

			switch (newAnimale.getIdSpecie()) {
			case Cane.idSpecie: {
				// thisAnimale = (Animale)newCane;
				/****
				 * MODIFICA Se il proprietario è diverso da "Canile", allora il
				 * detentore dovrà essere uguale al proprietario
				 * ***/
				int idTipologiaOperatore = 0;
				if (thisAnimale.getProprietario().getIdOperatore() != 0)
					idTipologiaOperatore = ((LineaProduttiva) ((Stabilimento) thisAnimale.getProprietario()
							.getListaStabilimenti().get(0)).getListaLineeProduttive().get(0)).getIdRelazioneAttivita();

				if (context.getRequest().getParameter("idDetentore") != null
						&& !context.getRequest().getParameter("idDetentore").equals("")
						&& !context.getRequest().getParameter("idDetentore").equals("-1")) {
					Operatore soggettoAdded = new Operatore();

					soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, new Integer(context.getRequest()
							.getParameter("idDetentore")).intValue());
					int idTipologiaDetentore = ((LineaProduttiva) ((Stabilimento) soggettoAdded.getListaStabilimenti()
							.get(0)).getListaLineeProduttive().get(0)).getIdRelazioneAttivita();

					if (idTipologiaOperatore != 5 && idTipologiaDetentore != 5) {
						newCane.setDetentore(thisAnimale.getProprietario());
						newCane.setIdDetentore(new Integer(context.getRequest().getParameter("idProprietario"))
								.intValue());
					} else {
						newCane.setDetentore(soggettoAdded);
					}

				} else if (thisAnimale.getProprietario() != null) {
					newCane.setDetentore(thisAnimale.getProprietario());
					newCane.setIdDetentore(new Integer(context.getRequest().getParameter("idProprietario")).intValue());
				}
				/*************/

				if (context.getRequest().getParameter("flagSindacoFuoriRegione") != null
						&& !("").equals(context.getRequest().getParameter("flagSindacoFuoriRegione"))
						&& ("on").equals(context.getRequest().getParameter("flagSindacoFuoriRegione"))) {

					// Sindaco fuori regione, detentore canile in regione, l'asl
					// diventa quella del detentore
					Stabilimento stabDet = (Stabilimento) newCane.getDetentore().getListaStabilimenti().get(0);
					newCane.setIdAslRiferimento(stabDet.getIdAsl());

				}

				if (context.getRequest().getParameter("idVeterinarioMicrochip") != null
						&& !context.getRequest().getParameter("idVeterinarioMicrochip").equals("")
						&& !("-1").equals(context.getRequest().getParameter("idVeterinarioMicrochip"))) {

					Operatore soggettoAdded = new Operatore();

					soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, new Integer(context.getRequest()
							.getParameter("idVeterinarioMicrochip")).intValue());

					newCane.setIdVeterinarioMicrochip(new Integer((String) context.getRequest().getParameter(
							"idVeterinarioMicrochip")));
					// newCane.setVeterinarioChip(soggettoAdded);
				}

				break;

			}
			case Gatto.idSpecie: {
				// thisAnimale = (Animale)newGatto;
				int idTipologiaOperatore = 0;
				if (thisAnimale.getProprietario().getIdOperatore() != 0)
					idTipologiaOperatore = ((LineaProduttiva) ((Stabilimento) thisAnimale.getProprietario()
							.getListaStabilimenti().get(0)).getListaLineeProduttive().get(0)).getIdRelazioneAttivita();

				if (context.getRequest().getParameter("idDetentore") != null
						&& !context.getRequest().getParameter("idDetentore").equals("")
						&& !context.getRequest().getParameter("idDetentore").equals("-1")) {
					Operatore soggettoAdded = new Operatore();
					soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, new Integer(context.getRequest()
							.getParameter("idDetentore")).intValue());
					int idTipologiaDetentore = ((LineaProduttiva) ((Stabilimento) soggettoAdded.getListaStabilimenti()
							.get(0)).getListaLineeProduttive().get(0)).getIdRelazioneAttivita();

					if (idTipologiaOperatore != 5 && idTipologiaDetentore != 5) {
						newGatto.setDetentore(thisAnimale.getProprietario());
						newGatto.setIdDetentore(new Integer(context.getRequest().getParameter("idProprietario"))
								.intValue());
					} else {
						newGatto.setDetentore(soggettoAdded);
					}

				} else if (thisAnimale.getProprietario() != null) {
					newGatto.setDetentore(thisAnimale.getProprietario());
					newGatto.setIdDetentore(new Integer(context.getRequest().getParameter("idProprietario")).intValue());
				}

				if (context.getRequest().getParameter("flagSindacoFuoriRegione") != null
						&& !("").equals(context.getRequest().getParameter("flagSindacoFuoriRegione"))
						&& ("on").equals(context.getRequest().getParameter("flagSindacoFuoriRegione"))) {

					// Sindaco fuori regione, detentore canile in regione, l'asl
					// diventa quella del detentore
					Stabilimento stabDet = (Stabilimento) newGatto.getDetentore().getListaStabilimenti().get(0);
					newGatto.setIdAslRiferimento(stabDet.getIdAsl());

				}

				break;

			}
			case Furetto.idSpecie: {
				// thisAnimale = (Animale)newGatto;int idTipologiaOperatore=0;
				int idTipologiaOperatore = 0;
				if (thisAnimale.getProprietario().getIdOperatore() != 0)
					idTipologiaOperatore = ((LineaProduttiva) ((Stabilimento) thisAnimale.getProprietario()
							.getListaStabilimenti().get(0)).getListaLineeProduttive().get(0)).getIdRelazioneAttivita();

				if (context.getRequest().getParameter("idDetentore") != null
						&& !context.getRequest().getParameter("idDetentore").equals("")
						&& !context.getRequest().getParameter("idDetentore").equals("-1")) {
					Operatore soggettoAdded = new Operatore();
					soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, new Integer(context.getRequest()
							.getParameter("idDetentore")).intValue());
					int idTipologiaDetentore = ((LineaProduttiva) ((Stabilimento) soggettoAdded.getListaStabilimenti()
							.get(0)).getListaLineeProduttive().get(0)).getIdRelazioneAttivita();

					if (idTipologiaOperatore != 5 && idTipologiaDetentore != 5) {
						newFuretto.setDetentore(thisAnimale.getProprietario());
						newFuretto.setIdDetentore(new Integer(context.getRequest().getParameter("idProprietario"))
								.intValue());
					} else {
						newFuretto.setDetentore(soggettoAdded);
					}

				} else if (thisAnimale.getProprietario() != null) {
					newFuretto.setDetentore(thisAnimale.getProprietario());
					newFuretto.setIdDetentore(new Integer(context.getRequest().getParameter("idProprietario"))
							.intValue());
				}

				if (context.getRequest().getParameter("flagSindacoFuoriRegione") != null
						&& !("").equals(context.getRequest().getParameter("flagSindacoFuoriRegione"))
						&& ("on").equals(context.getRequest().getParameter("flagSindacoFuoriRegione"))) {

					// Sindaco fuori regione, detentore canile in regione, l'asl
					// diventa quella del detentore
					Stabilimento stabDet = (Stabilimento) newFuretto.getDetentore().getListaStabilimenti().get(0);
					newFuretto.setIdAslRiferimento(stabDet.getIdAsl());

				}

				break;

			}
			}

			if (context.getRequest().getParameter("doContinue") != null
					&& !context.getRequest().getParameter("doContinue").equals("")
					&& context.getRequest().getParameter("doContinue").equals("false")) {

				context.getRequest().setAttribute("Cane", newCane);
				context.getRequest().setAttribute("Gatto", newGatto);
				context.getRequest().setAttribute("Furetto", newFuretto);
				context.getRequest().setAttribute("animale", newAnimale);

				return executeCommandAddAccMultipla(context);
			}

			if (esitoMc == null
					|| (esitoMc.getIdEsito() == 2
							|| (esitoMc.getIdEsito() == 4 && (user.getRoleId() != new Integer(
									ApplicationProperties.getProperty("ID_RUOLO_HD1"))
									&& user.getRoleId() != new Integer(
											ApplicationProperties.getProperty("ID_RUOLO_HD2"))
									&& user.getRoleId() != new Integer(
											ApplicationProperties.getProperty("ID_RUOLO_AMMINISTRATORE_ASL"))
									&& user.getRoleId() != new Integer(
											ApplicationProperties.getProperty("ID_RUOLO_REFERENTE_ASL")) && user
									.getRoleId() != new Integer(
									ApplicationProperties.getProperty("ID_RUOLO_ANAGRAFE_CANINA")))) || (esitoMc
							.getIdEsito() == 3 && user.getRoleId() == 24))) {
				context.getRequest().setAttribute("Cane", newCane);
				context.getRequest().setAttribute("Gatto", newGatto);
				context.getRequest().setAttribute("Furetto", newFuretto);
				context.getRequest().setAttribute("ErroreMicrochip", esitoMc.getDescrizione());
				context.getRequest().setAttribute("animale", newAnimale);

				return executeCommandAddAccMultipla(context);

			}

			if (esitoTatu != null
					&& (esitoTatu.getIdEsito() == 2 || esitoTatu.getIdEsito() == 4 || (esitoTatu.getIdEsito() == 3 && user
							.getRoleId() == 24))) {
				context.getRequest().setAttribute("Cane", newCane);
				context.getRequest().setAttribute("Gatto", newGatto);
				context.getRequest().setAttribute("Furetto", newFuretto);
				context.getRequest().setAttribute("ErroreTatuaggio", esitoTatu.getDescrizione());
				context.getRequest().setAttribute("animale", newAnimale);

				return executeCommandAddAccMultipla(context);

			}

			/**
			 * CONTROLLI PRATICA CONTRIBUTI
			 */
			if (context.getRequest().getParameter("flagSterilizzazione") != null
					&& !("").equals(context.getRequest().getParameter("flagSterilizzazione"))
					&& ("on").equals(context.getRequest().getParameter("flagSterilizzazione"))) {

				EventoSterilizzazione sterilizzazione = (EventoSterilizzazione) context.getRequest().getAttribute(
						"EventoSterilizzazione");

				if (sterilizzazione.isFlagContributoRegionale()) {
					contributoRichiesto = true;
				}

				// Se è catturato
				/*
				 * if (context.getRequest().getParameter("flagCattura") != null
				 * && !("").equals(context.getRequest().getParameter(
				 * "flagCattura")) &&
				 * ("on").equals(context.getRequest().getParameter(
				 * "flagCattura"))) { catturato = true; }
				 */

				if (thisAnimale.isRandagio(db)) {
					catturato = true;
				}

				if (contributoRichiesto) {
					contributoDisponibile = Animale.checkContributo(db, thisAnimale.getMicrochip());

					if (contributoDisponibile) {
						if (context.getRequest().getParameter("idProgettoSterilizzazioneRichiesto") != null) {
							id_pratica = Integer.valueOf(context.getRequest().getParameter(
									"idProgettoSterilizzazioneRichiesto"));
							// thisAsset.setId_pratica_contributi(id_pratica);
							praticaContributi = new Pratica(db, id_pratica);

							if ((catturato)) {
								// int
								// trovato=praticaContributi.controlli_pratica(db,
								// id_pratica,
								// thisAsset.getComuneCattura(),getUserAsl(context));
								if (thisAnimale.getIdSpecie() == Cane.idSpecie
										&& praticaContributi.getCaniRestantiCatturati() <= 0) {
									praticaSceltaOK = false;
									context.getRequest()
											.setAttribute("praticaError",
													"Per il progetto scelto non ci sono posti disponibili per i cani catturati");
								} else if (thisAnimale.getIdSpecie() == Gatto.idSpecie
										&& praticaContributi.getGattiRestantiCatturati() <= 0) {
									praticaSceltaOK = false;
									context.getRequest()
											.setAttribute("praticaError",
													"Per il progetto scelto non ci sono posti disponibili per i gatti catturati");
								} else if ((String) context.getRequest().getParameter("idComuneCattura") != null
										&& !praticaContributi.getComuniElenco().contains(
												new Integer((String) context.getRequest().getParameter(
														"idComuneCattura")).intValue())) {
									Stabilimento stab = (Stabilimento) thisAnimale.getDetentore()
											.getListaStabilimenti().get(0);
									LineaProduttiva lineaP = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
									if (!praticaContributi.getCaniliElenco().contains(lineaP.getId())) {

										praticaSceltaOK = false;
										context.getRequest().setAttribute("praticaError",
												"Il comune di cattura non corrisponde con quelli del progetto");
									}

								}
							} else {
								// recupero il comune del proprietario
								Stabilimento stab = (Stabilimento) thisAnimale.getProprietario().getListaStabilimenti()
										.get(0);
								LineaProduttiva lineaP = (LineaProduttiva) stab.getListaLineeProduttive().get(0);

								int id_comune_proprietario = stab.getSedeOperativa().getComune();
								// String comune_proprietario=
								// thisAsset.recupero_comune(db,
								// thisAsset.getOrgId());
								// int
								// trovato=praticaContributi.controlli_pratica(db,
								// id_pratica,
								// comune_proprietario,getUserAsl(context));
								// Prendo il detentore. Devo distinguere se sto
								// aggiungendo un cane o un gatto
								if (thisAnimale.getIdSpecie() == Cane.idSpecie) {
									stab = (Stabilimento) newCane.getDetentore().getListaStabilimenti().get(0);
								} else if (thisAnimale.getIdSpecie() == Gatto.idSpecie) {
									stab = (Stabilimento) newGatto.getDetentore().getListaStabilimenti().get(0);
								} else if (thisAnimale.getIdSpecie() == Furetto.idSpecie) {
									stab = (Stabilimento) newFuretto.getDetentore().getListaStabilimenti().get(0);
								}
								lineaP = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
								if (thisAnimale.getIdSpecie() == Cane.idSpecie
										&& praticaContributi.getCaniRestantiPadronali() <= 0) {
									praticaSceltaOK = false;
									context.getRequest()
											.setAttribute("praticaError",
													"Per il progetto scelto non ci sono posti disponibili per i cani padronali");
								} else if (thisAnimale.getIdSpecie() == Gatto.idSpecie
										&& praticaContributi.getGattiRestantiPadronali() <= 0) {
									praticaSceltaOK = false;
									context.getRequest()
											.setAttribute("praticaError",
													"Per il progetto scelto non ci sono posti disponibili per i gatti padronali");
								} else if (!praticaContributi.getComuniElenco().contains(id_comune_proprietario)
										&& !praticaContributi.getCaniliElenco().contains(lineaP.getId())) {
									praticaSceltaOK = false;
									context.getRequest().setAttribute("praticaError",
											"Il comune del proprietario non corrisponde con quelli del progetto");
								}
							}
						}
					}
				} else {
					// se il contributo non è stato richiesto setto a true la
					// disponibilità del contributo per bypassare successivi
					// controlli
					contributoDisponibile = true;
				}

				if (!praticaSceltaOK) {
					context.getRequest().setAttribute("animale", newAnimale);
					return executeCommandAddAccMultipla(context);
				}

			}

			isValid = this.validateObject(context, db, thisAnimale);

			if (isValid) {


				thisAnimale.setIdUtenteInserimento(user.getUserId());

				if (thisAnimale.isFlagAttivitaItinerante()) {
					thisAnimale.setDataAttivitaItinerante(thisAnimale.getDataMicrochip());
				}

				recordInserted = thisAnimale.insert(db);

				if (contributoRichiesto && praticaSceltaOK) {
					if (catturato) {
						praticaContributi.aggiornaCatturati(db, id_pratica, thisAnimale.getIdSpecie());
					} else {
						praticaContributi.aggiornaPadronali(db, id_pratica, thisAnimale.getIdSpecie());
					}
				}

				if (thisAnimale.getIdSpecie() == Gatto.idSpecie) {

					// Aggiorno gatti appartenenti a colonia
					int idRelazioneDetentore = ((LineaProduttiva) ((Stabilimento) newGatto.getDetentore()
							.getListaStabilimenti().get(0)).getListaLineeProduttive().get(0)).getIdRelazioneAttivita();
					int idRelazioneProprietario = ((LineaProduttiva) ((Stabilimento) newGatto.getProprietario()
							.getListaStabilimenti().get(0)).getListaLineeProduttive().get(0)).getIdRelazioneAttivita();

					// Controllo se è gatto e se ha proprietario o detentore
					// colonia
					/*
					 * if (idRelazioneDetentore ==
					 * LineaProduttiva.idAggregazioneColonia ||
					 * idRelazioneProprietario ==
					 * LineaProduttiva.idAggregazioneColonia) { Stabilimento
					 * stab = (Stabilimento) newGatto
					 * .getDetentore().getListaStabilimenti().get(0);
					 * LineaProduttiva lineaP = (LineaProduttiva) stab
					 * .getListaLineeProduttive().get(0); String
					 * queryUpdateDatiColonia = ""; if (thisAnimale.getSesso()
					 * == "F") { queryUpdateDatiColonia =
					 * "UPDATE opu_informazioni_colonia set  totale_identificati_sterilizzati = totale_identificati_sterilizzati + 1,  "
					 * +
					 * "totale_femmine= totale_femmine + 1 WHERE  id_relazione_stabilimento_linea_produttiva=?"
					 * ; } else { queryUpdateDatiColonia =
					 * "UPDATE opu_informazioni_colonia  totale_identificati_sterilizzati = totale_identificati_sterilizzati + 1,  "
					 * +
					 * " totale_maschi = totale_maschi + 1 WHERE  id_relazione_stabilimento_linea_produttiva=?"
					 * ; }
					 * 
					 * PreparedStatement pst = db
					 * .prepareStatement(queryUpdateDatiColonia); int i = 0;
					 * pst.setInt(++i, lineaP.getId());
					 * 
					 * pst.execute();
					 * 
					 * }
					 */
				}

				if ((esitoMc.getIdEsito() == 1 || esitoMc.getIdEsito() == 4) && recordInserted == true) {
					pst = db.prepareStatement("update microchips set id_animale =? ,id_specie = ? where microchip =? ");
					pst.setInt(1, thisAnimale.getIdAnimale());
					pst.setInt(2, thisAnimale.getIdSpecie());
					pst.setString(3, thisAnimale.getMicrochip());
					pst.execute();
				}
				if (esitoTatu != null && (esitoTatu.getIdEsito() == 1 || esitoTatu.getIdEsito() == 4)
						&& recordInserted == true) {
					pst = db.prepareStatement("update microchips set id_animale =? ,flag_secondo_microchip = true,id_specie = ? where microchip =? ");
					pst.setInt(1, thisAnimale.getIdAnimale());
					pst.setInt(2, thisAnimale.getIdSpecie());
					pst.setString(3, thisAnimale.getTatuaggio());
					pst.execute();
				}

				int tipologiaRegistrazione = 0;
				// Registrazioni

				// Registrazione di inserimento

				EventoRegistrazioneBDU reg_bdu = (EventoRegistrazioneBDU) context.getRequest().getAttribute(
						"EventoRegistrazioneBDU");
				
				String esitoInvioSinaaf[] = null;
				if(ApplicationProperties.getProperty("abilitato_sinaaf").equals("true"))
				{
				esitoInvioSinaaf = new Sinaaf().inviaInSinaaf(db, getUserId(context),reg_bdu.getIdEvento()+"", "evento");
				if(esitoInvioSinaaf[0]!=null)
					context.getRequest().setAttribute("messaggio", "Registrazione di iscrizione animale" + esitoInvioSinaaf[0]);
				if(esitoInvioSinaaf[1]!=null)
					context.getRequest().setAttribute("Error", "Registrazione di iscrizione animale" + esitoInvioSinaaf[1]);
				}
				
				
				// Caso vet privati o ruolo anagrafecanina
				if ((user.getSiteId() > 0 && user.getSiteId() != thisAnimale.getIdAslRiferimento())) {
					reg_bdu.setIdAslRiferimento(user.getSiteId());
				} else if (user.getSiteId() < 0) {
					reg_bdu.setIdAslRiferimento(thisAnimale.getIdAslRiferimento());
				}

				if (reg_bdu.isFlagAttivitaItinerante()) {
					reg_bdu.setDataAttivitaItinerante(thisAnimale.getDataMicrochip());
				}
				reg_bdu.setEnteredby(this.getUserId(context));
				reg_bdu.setIdTipologiaEvento(reg_bdu.idTipologiaDB);
				reg_bdu.setIdAnimale(thisAnimale.getIdAnimale());
				reg_bdu.setSpecieAnimaleId(thisAnimale.getIdSpecie());
				reg_bdu.setIdProprietarioCorrente(thisAnimale.getIdProprietario());
				reg_bdu.setIdDetentoreCorrente(thisAnimale.getIdDetentore());
				
				// CONTROLLO SELEZIONE ORIGINE ANIMALE
				//String anagrafe_fuori_nazione=(String)context.getRequest().getParameter("flagFuoriNazione");
				String anagrafe_fuori_regione=(String)context.getRequest().getParameter("flagFuoriRegione");
				String acquisto_online=(String)context.getRequest().getParameter("flagAcquistoOnline");
				String origine=(String)context.getRequest().getParameter("origine_da");

				
				if(origine!=null){
					if(origine.equals("nazione_estera")){
						reg_bdu.setFlag_anagrafe_estera(true);  
						reg_bdu.setNazione_estera((String)context.getRequest().getParameter("idNazioneProvenienza"));
						reg_bdu.setNazione_estera_note((String)context.getRequest().getParameter("noteNazioneProvenienza"));
					}else{
						reg_bdu.setProvenienza_origine(origine);
						String tipo_origine=(String)context.getRequest().getParameter("tipo_origine");
						if(tipo_origine!=null){
							if(tipo_origine.equals("soggetto_fisico")){
								if (context.getRequest().getParameter("idProprietarioProvenienza") != null	&& !context.getRequest().getParameter("idProprietarioProvenienza").equals("-1"))
									reg_bdu.setIdProprietarioProvenienza(Integer.parseInt((String)context.getRequest().getParameter("idProprietarioProvenienza")));
								}else{
									reg_bdu.setRegione_ritrovamento(context.getRequest().getParameter("regione_ritrovamento"));
									reg_bdu.setProvincia_ritrovamento(context.getRequest().getParameter("provincia_ritrovamento"));
									reg_bdu.setComune_ritrovamento(context.getRequest().getParameter("comune_ritrovamento"));
									reg_bdu.setIndirizzo_ritrovamento(context.getRequest().getParameter("indirizzo_ritrovamento"));
									reg_bdu.setData_ritrovamento(context.getRequest().getParameter("data_ritrovamento"));
								}						
						}
					}
				}
				
				if (anagrafe_fuori_regione != null && anagrafe_fuori_regione.equals("on")){
					reg_bdu.setFlag_anagrafe_fr(true);  
					reg_bdu.setRegione_anagrafe_fr((String)context.getRequest().getParameter("idRegioneProvenienza"));
					reg_bdu.setRegione_anagrafe_fr_note((String)context.getRequest().getParameter("noteAnagrafeFr"));
				}		
				
				if (acquisto_online != null && acquisto_online.equals("on")){
					reg_bdu.setFlag_acquisto_online(true);  
					reg_bdu.setSito_web_acquisto((String)context.getRequest().getParameter("sitoWebAcquisto"));
					reg_bdu.setSito_web_acquisto_note((String)context.getRequest().getParameter("noteAcquistoOnline"));
				}
				
				
				reg_bdu.insert(db);

				/**
				 * SE IL RUOLO E' VET PRIVATO, PUo' ANAGRAFARE ANIMALI A
				 * PROPRIETARI / DETENTORI FUORI REGIONE, NEL QUAL CASO IL CANE
				 * DEVE RISULTARE DIRETTAMENTE UN FUORI REGIONE, ESSERE INVIATO
				 * COMUNQUE NELLE ESTRAZIONI ALLA BDN E SU DI ESSO NON DEVE
				 * ESSERE POSSIBILE INSERIRE ULTERIORI REGISTRAZIONI
				 */
				Operatore proprietario = thisAnimale.getProprietario();
				Operatore detentore = thisAnimale.getDetentore();
				Stabilimento stabPropr = (Stabilimento) proprietario.getListaStabilimenti().get(0);
				Stabilimento stabDet = (Stabilimento) detentore.getListaStabilimenti().get(0);

				if (stabPropr.getIdAsl() == new Integer(ApplicationProperties.getProperty("ID_ASL_FUORI_REGIONE"))
						&& stabDet.getIdAsl() == new Integer(ApplicationProperties.getProperty("ID_ASL_FUORI_REGIONE"))) {
					tipologiaRegistrazione = EventoRegistrazioneBDU.idTipologiaRegistrazioneAFuoriRegione;
				} else {

					if (thisAnimale.getIdSpecie() == Gatto.idSpecie) {
						tipologiaRegistrazione = reg_bdu.getTipologiaDB(newGatto, db);
					} else {
						tipologiaRegistrazione = EventoRegistrazioneBDU.idTipologiaDB;
					}
				}

				// Registrazione di cattura per i cani e i gatti
				if (thisAnimale.getIdSpecie() != Furetto.idSpecie
						&& (context.getRequest().getParameter("flagCattura") != null
								&& !("").equals(context.getRequest().getParameter("flagCattura")) && ("on")
									.equals(context.getRequest().getParameter("flagCattura")))) {

					catturato = true;
					cattura = (EventoCattura) context.getRequest().getAttribute("EventoCattura");
					// Caso vet privati o ruolo anagrafecanina
					if ((user.getSiteId() > 0 && user.getSiteId() != thisAnimale.getIdAslRiferimento())) {
						cattura.setIdAslRiferimento(user.getSiteId());
					} else if (user.getSiteId() < 0) {
						cattura.setIdAslRiferimento(thisAnimale.getIdAslRiferimento());
					}
					cattura.setIdAnimale(thisAnimale.getIdAnimale());
					cattura.setIdTipologiaEvento(cattura.idTipologiaDB);
					cattura.setEnteredby(this.getUserId(context));
					cattura.setIdProprietarioSindaco((String) context.getRequest().getParameter("idProprietario"));
					cattura.setSpecieAnimaleId(thisAnimale.getIdSpecie());
					cattura.insert(db);
					// thisAnimale.setFlagCatturato(true);
					// thisAnimale.setIdComuneCattura(cattura.getIdComuneCattura());
					tipologiaRegistrazione = cattura.idTipologiaDB;
				}

				if (context.getRequest().getParameter("microchip") != null
						&& !("").equals(context.getRequest().getParameter("microchip"))) {
					EventoInserimentoMicrochip microchip = (EventoInserimentoMicrochip) context.getRequest()
							.getAttribute("EventoInserimentoMicrochip");
					// Caso vet privati o ruolo anagrafecanina
					if ((user.getSiteId() > 0 && user.getSiteId() != thisAnimale.getIdAslRiferimento())) {
						microchip.setIdAslRiferimento(user.getSiteId());
					} else if (user.getSiteId() < 0) {
						microchip.setIdAslRiferimento(thisAnimale.getIdAslRiferimento());
					}
					microchip.setIdAnimale(thisAnimale.getIdAnimale());
					microchip.setEnteredby(this.getUserId(context));
					microchip.setIdTipologiaEvento(microchip.idTipologiaDB);
					microchip.setSpecieAnimaleId(thisAnimale.getIdSpecie());
					microchip.setDataInserimentoMicrochip((String) context.getRequest().getParameter("dataMicrochip"));
					microchip.setNumeroMicrochipAssegnato((String) context.getRequest().getParameter("microchip"));
					microchip.setIdVeterinarioPrivatoInserimentoMicrochip(thisAnimale.getIdVeterinarioMicrochip());
					microchip.setIdProprietarioCorrente(thisAnimale.getIdProprietario());
					microchip.setIdDetentoreCorrente(thisAnimale.getIdDetentore());
					microchip.insert(db);

				}

				if ((String) context.getRequest().getParameter("dataVaccino") != null
						&& !("").equals((String) context.getRequest().getParameter("dataVaccino"))) {
					EventoInserimentoVaccinazioni rabbia = (EventoInserimentoVaccinazioni) context.getRequest()
							.getAttribute("EventoInserimentoVaccinazioni");

					if ((user.getSiteId() > 0 && user.getSiteId() != thisAnimale.getIdAslRiferimento())) {
						rabbia.setIdAslRiferimento(user.getSiteId());
					} else if (user.getSiteId() < 0) {
						rabbia.setIdAslRiferimento(thisAnimale.getIdAslRiferimento());
					}

					rabbia.setIdTipoVaccino(EventoInserimentoVaccinazioni.antirabbica);
					rabbia.setDataVaccinazione((String) context.getRequest().getParameter("dataVaccino"));
					rabbia.setDataScadenzaVaccino((String) context.getRequest().getParameter("dataScadenzaVaccino"));
					rabbia.setNomeVaccino((String) context.getRequest().getParameter("nomeVaccino"));
					rabbia.setProduttoreVaccino((String) context.getRequest().getParameter("produttoreVaccino"));
					rabbia.setIdAnimale(thisAnimale.getIdAnimale());
					rabbia.setEnteredby(getUserId(context));
					rabbia.setModifiedby(getUserId(context));
					rabbia.setIdTipologiaEvento(rabbia.idTipologiaDB);
					rabbia.setSpecieAnimaleId(thisAnimale.getIdSpecie());
					rabbia.setIdProprietarioCorrente(thisAnimale.getIdProprietario());
					rabbia.setIdDetentoreCorrente(thisAnimale.getIdDetentore());
					rabbia.insert(db);
					
					if(ApplicationProperties.getProperty("abilitato_sinaaf").equals("true"))
					{
					esitoInvioSinaaf = new Sinaaf().inviaInSinaaf(db, getUserId(context),rabbia.getIdEvento()+"", "evento");
					if(esitoInvioSinaaf[0]!=null)
						context.getRequest().setAttribute("messaggio", "Registrazione di iscrizione animale" + esitoInvioSinaaf[0]);
					if(esitoInvioSinaaf[1]!=null)
						context.getRequest().setAttribute("Error", "Registrazione di iscrizione animale" + esitoInvioSinaaf[1]);
					}

				}
				/*
				 * else if (thisAnimale.getIdSpecie() == Gatto.idSpecie){
				 * Operatore proprietario = thisAnimale.getProprietario();
				 * Stabilimento stab = (Stabilimento)
				 * proprietario.getListaStabilimenti().get(0); LineaProduttiva
				 * lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
				 * if (lp.getIdAttivita() == LineaProduttiva.idAttivitaColonia){
				 * lp.getIdRelazioneAttivita(); //Se ho scelto colonia il gatto
				 * deve essere randagio tipologiaRegistrazione =
				 * cattura.idTipologiaDB; }
				 * 
				 * }
				 */

				
				System.out.println("IMPORTANTE PER DEBUGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG: " + context.getRequest().getParameter("flagMorsicatore") != null);
				System.out.println("IMPORTANTE PER DEBUG: " + context.getRequest().getParameter("flagMorsicatore"));
				
				if (context.getRequest().getParameter("flagMorsicatore") != null
						&& !("").equals(context.getRequest().getParameter("flagMorsicatore"))) {
					EventoMorsicatura morsicatura = (EventoMorsicatura) context.getRequest().getAttribute(
							"EventoMorsicatura");
					System.out.println("IMPORTANTE PER DEBUG: 1" );
					// Caso vet privati o ruolo anagrafecanina
					if ((user.getSiteId() > 0 && user.getSiteId() != thisAnimale.getIdAslRiferimento())) {
						morsicatura.setIdAslRiferimento(user.getSiteId());
					} else if (user.getSiteId() < 0) {
						morsicatura.setIdAslRiferimento(thisAnimale.getIdAslRiferimento());
					}
					morsicatura.setIdAnimale(thisAnimale.getIdAnimale());
					morsicatura.setEnteredby(this.getUserId(context));
					morsicatura.setIdTipologiaEvento(morsicatura.idTipologiaDB);
					morsicatura.setSpecieAnimaleId(thisAnimale.getIdSpecie());
					morsicatura.setIdProprietarioCorrente(thisAnimale.getIdProprietario());
					morsicatura.setIdDetentoreCorrente(thisAnimale.getIdDetentore());
					morsicatura.insert(db);
					
					if(ApplicationProperties.getProperty("abilitato_sinaaf").equals("true"))
					{
					esitoInvioSinaaf = new Sinaaf().inviaInSinaaf(db, getUserId(context),morsicatura.getIdEvento()+"", "evento");
					if(esitoInvioSinaaf[0]!=null)
						context.getRequest().setAttribute("messaggio", "Registrazione di iscrizione animale" + esitoInvioSinaaf[0]);
					if(esitoInvioSinaaf[1]!=null)
						context.getRequest().setAttribute("Error", "Registrazione di iscrizione animale" + esitoInvioSinaaf[1]);
					}
					
					Cane cane = new Cane(db, thisAnimale.getIdAnimale());
					// cane.setFlagMorsicatore(true);
					// cane.setDataMorso1(morsicatura.getDataMorso());
					cane.updateStato(db);
					// non è necessario aggiornare il wkf

				}

				/**
				 * IN APPLICAZIONE CR 2015 BANCA DATI A PRIORI PASSAPORTI NON SI
				 * DEVE GENERARE UNA REGISTRAZIONE DI RILASCIO PASSAPORTO IN
				 * FASE DI INSERIMENTO ANAGRAFICA PERCHE TRATTASI DI PASSAPORTI
				 * NON ASSEGNATI DALLA REGIONE CAMPANIA. PER QUESTO IL VALORE
				 * NON DEVE ESSERE TRA QUELLI ASSEGNATI ALLA REGIONE.
				 */

				if (context.getRequest().getParameter("numeroPassaporto") != null
						&& !("").equals(context.getRequest().getParameter("numeroPassaporto"))) {/*
																								 * EventoRilascioPassaporto
																								 * passaporto
																								 * =
																								 * (
																								 * EventoRilascioPassaporto
																								 * )
																								 * context
																								 * .
																								 * getRequest
																								 * (
																								 * )
																								 * .
																								 * getAttribute
																								 * (
																								 * "EventoRilascioPassaporto"
																								 * )
																								 * ;
																								 * 
																								 * /
																								 * /
																								 * Caso
																								 * vet
																								 * privati
																								 * o
																								 * ruolo
																								 * anagrafecanina
																								 * if
																								 * (
																								 * (
																								 * user
																								 * .
																								 * getSiteId
																								 * (
																								 * )
																								 * >
																								 * 0
																								 * &&
																								 * user
																								 * .
																								 * getSiteId
																								 * (
																								 * )
																								 * !=
																								 * thisAnimale
																								 * .
																								 * getIdAslRiferimento
																								 * (
																								 * )
																								 * )
																								 * )
																								 * {
																								 * passaporto
																								 * .
																								 * setIdAslRiferimento
																								 * (
																								 * user
																								 * .
																								 * getSiteId
																								 * (
																								 * )
																								 * )
																								 * ;
																								 * }
																								 * else
																								 * if
																								 * (
																								 * user
																								 * .
																								 * getSiteId
																								 * (
																								 * )
																								 * <
																								 * 0
																								 * )
																								 * {
																								 * passaporto
																								 * .
																								 * setIdAslRiferimento
																								 * (
																								 * thisAnimale
																								 * .
																								 * getIdAslRiferimento
																								 * (
																								 * )
																								 * )
																								 * ;
																								 * }
																								 * 
																								 * 
																								 * passaporto
																								 * .
																								 * setIdAnimale
																								 * (
																								 * thisAnimale
																								 * .
																								 * getIdAnimale
																								 * (
																								 * )
																								 * )
																								 * ;
																								 * passaporto
																								 * .
																								 * setIdTipologiaEvento
																								 * (
																								 * EventoRilascioPassaporto
																								 * .
																								 * idTipologiaDB
																								 * )
																								 * ;
																								 * passaporto
																								 * .
																								 * setSpecieAnimaleId
																								 * (
																								 * thisAnimale
																								 * .
																								 * getIdSpecie
																								 * (
																								 * )
																								 * )
																								 * ;
																								 * passaporto
																								 * .
																								 * setEnteredby
																								 * (
																								 * this
																								 * .
																								 * getUserId
																								 * (
																								 * context
																								 * )
																								 * )
																								 * ;
																								 * passaporto
																								 * .
																								 * setFlagPassaportoAttivo
																								 * (
																								 * true
																								 * )
																								 * ;
																								 * passaporto
																								 * .
																								 * setIdProprietarioCorrente
																								 * (
																								 * thisAnimale
																								 * .
																								 * getIdProprietario
																								 * (
																								 * )
																								 * )
																								 * ;
																								 * passaporto
																								 * .
																								 * setIdDetentoreCorrente
																								 * (
																								 * thisAnimale
																								 * .
																								 * getIdDetentore
																								 * (
																								 * )
																								 * )
																								 * ;
																								 * passaporto
																								 * .
																								 * insert
																								 * (
																								 * db
																								 * )
																								 * ;
																								 * 
																								 * thisAnimale
																								 * .
																								 * setDataRilascioPassaporto
																								 * (
																								 * passaporto
																								 * .
																								 * getDataRilascioPassaporto
																								 * (
																								 * )
																								 * )
																								 * ;
																								 * thisAnimale
																								 * .
																								 * setNumeroPassaporto
																								 * (
																								 * passaporto
																								 * .
																								 * getNumeroPassaporto
																								 * (
																								 * )
																								 * )
																								 * ;
																								 * thisAnimale
																								 * .
																								 * updateStato
																								 * (
																								 * db
																								 * )
																								 * ;
																								 */
				}

				if ((newCane.getDataControlloEhrlichiosi() != null && newCane.getEsitoControlloEhrlichiosi() > -1)
						|| (newCane.getDataControlloRickettsiosi() != null && newCane.getEsitoControlloRickettsiosi() > -1)) {
					EventoEsitoControlli controlli = (EventoEsitoControlli) context.getRequest().getAttribute(
							"EventoEsitoControlli");

					// Caso vet privati o ruolo anagrafecanina
					if ((user.getSiteId() > 0 && user.getSiteId() != thisAnimale.getIdAslRiferimento())) {
						controlli.setIdAslRiferimento(user.getSiteId());
					} else if (user.getSiteId() < 0) {
						controlli.setIdAslRiferimento(thisAnimale.getIdAslRiferimento());
					}

					controlli.setDataEsito(reg_bdu.getDataRegistrazione());
					controlli.setEnteredby(this.getUserId(context));
					controlli.setMicrochip(thisAnimale.getMicrochip());
					controlli.setFlagEhrlichiosi(newCane.isFlagControlloEhrlichiosi());
					controlli.setDataEhrlichiosi(newCane.getDataControlloEhrlichiosi());
					controlli.setEsitoEhrlichiosi(newCane.getEsitoControlloEhrlichiosi());

					controlli.setFlagRickettiosi(newCane.isFlagControlloRickettsiosi());
					controlli.setDataRickettiosi(newCane.getDataControlloRickettsiosi());
					controlli.setEsitoRickettiosi(newCane.getEsitoControlloRickettsiosi());

					controlli.setIdAnimale(thisAnimale.getIdAnimale());
					controlli.setIdTipologiaEvento(EventoEsitoControlli.idTipologiaDB);
					controlli.setSpecieAnimaleId(thisAnimale.getIdSpecie());
					controlli.setIdProprietarioCorrente(thisAnimale.getIdProprietario());
					controlli.setIdDetentoreCorrente(thisAnimale.getIdDetentore());
					controlli.insert(db);
				}

				// Aggiorno wkf privato / randagio
				RegistrazioniWKF r_wkf = new RegistrazioniWKF();
				r_wkf.setIdStato(thisAnimale.getStato());
				r_wkf.setIdRegistrazione(tipologiaRegistrazione);
				thisAnimale.setStato((r_wkf.getProssimoStatoDaStatoPrecedenteERegistrazione(db)).getIdProssimoStato());
				thisAnimale.updateStato(db);

				// Registrazione di sterilizzazione
				if (context.getRequest().getParameter("flagSterilizzazione") != null
						&& !("").equals(context.getRequest().getParameter("flagSterilizzazione"))
						&& ("on").equals(context.getRequest().getParameter("flagSterilizzazione"))) {

					EventoSterilizzazione sterilizzazione = (EventoSterilizzazione) context.getRequest().getAttribute(
							"EventoSterilizzazione");

					// Caso vet privati o ruolo anagrafecanina
					if ((user.getSiteId() > 0 && user.getSiteId() != thisAnimale.getIdAslRiferimento())) {
						sterilizzazione.setIdAslRiferimento(user.getSiteId());
					} else if (user.getSiteId() < 0) {
						sterilizzazione.setIdAslRiferimento(thisAnimale.getIdAslRiferimento());
					}

					sterilizzazione.setIdAnimale(thisAnimale.getIdAnimale());
					sterilizzazione.setIdTipologiaEvento(sterilizzazione.idTipologiaDB);
					int idAslProprietario = ((Stabilimento) thisAnimale.getProprietario().getListaStabilimenti().get(0))
							.getIdAsl();
					sterilizzazione.setIdAslProprietario(idAslProprietario);
					sterilizzazione.setSpecieAnimaleId(thisAnimale.getIdSpecie());
					sterilizzazione.setIdProprietarioCorrente(thisAnimale.getIdProprietario());
					sterilizzazione.setIdDetentoreCorrente(thisAnimale.getIdDetentore());
					sterilizzazione.setEnteredby(this.getUserId(context));
					sterilizzazione.insert(db);
					
					if(ApplicationProperties.getProperty("abilitato_sinaaf").equals("true"))
					{
					esitoInvioSinaaf = new Sinaaf().inviaInSinaaf(db, getUserId(context),sterilizzazione.getIdEvento()+"", "evento");
					if(esitoInvioSinaaf[0]!=null)
						context.getRequest().setAttribute("messaggio", "Registrazione di iscrizione animale" + esitoInvioSinaaf[0]);
					if(esitoInvioSinaaf[1]!=null)
						context.getRequest().setAttribute("Error", "Registrazione di iscrizione animale" + esitoInvioSinaaf[1]);
					}
					
					// Aggiorno stato wkf
					r_wkf.setIdStato(thisAnimale.getStato());
					r_wkf.setIdRegistrazione(sterilizzazione.idTipologiaDB);
					thisAnimale.setStato((r_wkf.getProssimoStatoDaStatoPrecedenteERegistrazione(db))
							.getIdProssimoStato());
					thisAnimale.setFlagSterilizzazione(true);
					thisAnimale.setIdPraticaContributi(sterilizzazione.getIdProgettoSterilizzazioneRichiesto());
					thisAnimale.updateStato(db);
				}
				reg_bdu.setIdStato(thisAnimale.getStato());
				reg_bdu.updateStato(db);
				// AGGIORNARE STATO IN TAB

				context.getRequest().setAttribute("idSpecie", thisAnimale.getIdSpecie());

				context.getRequest().setAttribute("CustomerSatisfactionOnDetail", "OK");

			}
			/*
			 * Parametri necessari per l'invocazione della jsp go_to_detail.jsp
			 * invocata quando l'inserimento va a buon fine("InsertOK")
			 */
			context.getRequest().setAttribute("commandD", "AnimaleAction.do?command=Details");
			context.getRequest().setAttribute("animale_id", "&orgId=" + thisAnimale.getIdAnimale());

		} catch (PSQLException e) {
			

			// IN CASO DI CREAZIONE INDICE, IPOTESI PER ORA ESCLUSA DATA LA
			// PRESENZA GIa' DI DUPLICATI
			if (("23505").equals(e.getSQLState()))
				log.error("ERRORE: TENTATIVO INSERIMENTO MC DUPLICATO, BLOCCATO DA TRIGGER!! MC: "
						+ newAnimale.getMicrochip() + " TATUAGGIO: " + newAnimale.getTatuaggio() + " INSERITO DA: "
						+ thisAnimale.getIdUtenteInserimento());

			e.printStackTrace();
			context.getRequest().setAttribute("Error",
					"Si è verificato un problema con l'inserimento dell'animale. Riprovare");
			return ("SystemError");

		}

		catch (Exception e) {
			
			e.printStackTrace();
			context.getRequest().setAttribute("Error",
					"Si è verificato un problema con l'inserimento dell'animale. Riprovare");
			return ("SystemError");

		} finally {

			db.close();
			this.freeConnection(context, db, pst);

		}

		/*
		 * catch (Exception errorMessage) {
		 * 
		 * errorMessage.printStackTrace();
		 * context.getRequest().setAttribute("Error", errorMessage); return
		 * ("SystemError"); } finally { this.freeConnection(context, db); }
		 */
		addModuleBean(context, "View Accounts", "Accounts Insert ok");
		if (recordInserted) {
			String target = context.getRequest().getParameter("target");
			if (context.getRequest().getParameter("popup") != null
					&& !("false").equals((String) context.getRequest().getParameter("popup"))) {
				return ("ClosePopupOK");
			}

		}

		context.getRequest().setAttribute("animaleId", new Integer(thisAnimale.getIdAnimale()).toString());
		context.getRequest().setAttribute("idSpecie", new Integer(thisAnimale.getIdSpecie()).toString());

		context.getRequest().setAttribute("SalvaeClona", context.getParameter("saveandclone"));

		return (executeCommandDetails(context));

		// return ("InsertOK");

	}
	
	
	public String executeCommandPrintDocumentoMutilazione(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina-documenti-view")) {
			return ("PermissionError");
		}

		Connection db = null;

		int idAnimale = -1;
		int idSpecie = -1;
		int idEvento = -1;
		
		try {
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");

			db = this.getConnection(context);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			if ((String) context.getRequest().getParameter("isEmpty") == null) {

				SystemStatus systemStatus = this.getSystemStatus(context);
				String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
				String idSpecieString = (String) context.getRequest().getParameter("idSpecie");
				String idEventoString = (String) context.getRequest().getParameter("idEvento");

				if (idAnimaleString != null && !("").equals(idAnimaleString)) {
					idAnimale = new Integer(idAnimaleString).intValue();
				}

				if (idSpecieString != null && !("").equals(idSpecieString)) {
					idSpecie = new Integer(idSpecieString).intValue();
				}

				if (idEventoString != null && !("").equals(idEventoString)) {
					idEvento = new Integer(idEventoString).intValue();
				}
				
				Animale thisAnimale = new Animale(db, idAnimale);
				context.getRequest().setAttribute("thisAnimale", thisAnimale);

				EventoMutilazione dati_mutilazione = new EventoMutilazione(db, idEvento);
				context.getRequest().setAttribute("dati_mutilazione", dati_mutilazione);

				LookupList specieList = new LookupList(db, "lookup_specie");
				specieList.addItem(-1, "--Tutti--");
				context.getRequest().setAttribute("specieList", specieList);

				LookupList razzaList = new LookupList();
				razzaList.setTable("lookup_razza");
				razzaList.setIdSpecie(idSpecie);
				razzaList.buildList(db);
				razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("razzaList", razzaList);

				LookupList tagliaList = new LookupList(db, "lookup_taglia");
				tagliaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("tagliaList", tagliaList);

				LookupList mantelloList = new LookupList(db, "lookup_mantello");
				mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("mantelloList", mantelloList);

				ComuniAnagrafica c = new ComuniAnagrafica();
				// PER ORA PRENDO TUTTI I COMUNI E NON SOLO QUELLI RELATIVI
				// ALL'ASL
				// UTENTE
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, 1);
				LookupList comuniList = new LookupList(listaComuni, -1);
				comuniList.addItem(-1, "");
				context.getRequest().setAttribute("comuniList", comuniList);
				
				LookupList listaMedicoEsecutore = new LookupList(db, "lookup_medico_esecutore");
				context.getRequest().setAttribute("listaMedicoEsecutore", listaMedicoEsecutore);
				
				LookupList listaInterventoEseguito = new LookupList(db, "lookup_intervento_eseguito");
				context.getRequest().setAttribute("listaInterventoEseguito", listaInterventoEseguito);
				
				LookupList listaCausale = new LookupList(db, "lookup_causale");
				context.getRequest().setAttribute("listaCausale", listaCausale);

				// 	Lookup veterinari sterilizzanti
				LookupList veterinariList = new LookupList(db, "elenco_veterinari");
				context.getRequest().setAttribute("veterinariList", veterinariList);
				
				LookupList veterinariAslList = new LookupList(db, "elenco_veterinari_asl_with_group_asl");
				context.getRequest().setAttribute("veterinariAslList", veterinariAslList);
				
				Operatore proprietario = new Operatore();
				proprietario.queryRecordOperatorebyIdLineaProduttiva(db, dati_mutilazione.getIdProprietarioCorrente());
				context.getRequest().setAttribute("proprietario", proprietario);

				Operatore detentore = new Operatore();
				detentore.queryRecordOperatorebyIdLineaProduttiva(db, dati_mutilazione.getIdDetentoreCorrente());
				context.getRequest().setAttribute("detentore", detentore);
			}

			else {
				return getReturn(context, "viewDocumentoMutilazioneEmpty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "viewDocumentoMutilazione");

	}
	
	public String executeCommandPrintDocumentoAllontanamento(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina-documenti-view")) {
			return ("PermissionError");
		}

		Connection db = null;

		int idAnimale = -1;
		int idSpecie = -1;
		int idEvento = -1;
		
		try {
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");

			db = this.getConnection(context);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			if ((String) context.getRequest().getParameter("isEmpty") == null) {

				SystemStatus systemStatus = this.getSystemStatus(context);
				String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
				String idSpecieString = (String) context.getRequest().getParameter("idSpecie");
				String idEventoString = (String) context.getRequest().getParameter("idEvento");

				if (idAnimaleString != null && !("").equals(idAnimaleString)) {
					idAnimale = new Integer(idAnimaleString).intValue();
				}

				if (idSpecieString != null && !("").equals(idSpecieString)) {
					idSpecie = new Integer(idSpecieString).intValue();
				}

				if (idEventoString != null && !("").equals(idEventoString)) {
					idEvento = new Integer(idEventoString).intValue();
				}
				
				Animale thisAnimale = new Animale(db, idAnimale);
				context.getRequest().setAttribute("thisAnimale", thisAnimale);

				EventoAllontanamento dati_allontanamento = new EventoAllontanamento(db, idEvento);
				context.getRequest().setAttribute("dati_allontanamento", dati_allontanamento);

				LookupList specieList = new LookupList(db, "lookup_specie");
				specieList.addItem(-1, "--Tutti--");
				context.getRequest().setAttribute("specieList", specieList);

				LookupList razzaList = new LookupList();
				razzaList.setTable("lookup_razza");
				razzaList.setIdSpecie(idSpecie);
				razzaList.buildList(db);
				razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("razzaList", razzaList);

				LookupList tagliaList = new LookupList(db, "lookup_taglia");
				tagliaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("tagliaList", tagliaList);

				LookupList mantelloList = new LookupList(db, "lookup_mantello");
				mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("mantelloList", mantelloList);

				ComuniAnagrafica c = new ComuniAnagrafica();
				// PER ORA PRENDO TUTTI I COMUNI E NON SOLO QUELLI RELATIVI
				// ALL'ASL
				// UTENTE
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, 1);
				LookupList comuniList = new LookupList(listaComuni, -1);
				comuniList.addItem(-1, "");
				context.getRequest().setAttribute("comuniList", comuniList);
				
				LookupList listaMedicoEsecutore = new LookupList(db, "lookup_medico_esecutore");
				context.getRequest().setAttribute("listaMedicoEsecutore", listaMedicoEsecutore);
				
				LookupList listaCausale = new LookupList(db, "lookup_causale");
				context.getRequest().setAttribute("listaCausale", listaCausale);

				// 	Lookup veterinari sterilizzanti
				LookupList veterinariList = new LookupList(db, "elenco_veterinari");
				context.getRequest().setAttribute("veterinariList", veterinariList);
				
				LookupList veterinariAslList = new LookupList(db, "elenco_veterinari_asl_with_group_asl");
				context.getRequest().setAttribute("veterinariAslList", veterinariAslList);
				
				Operatore proprietario = new Operatore();
				proprietario.queryRecordOperatorebyIdLineaProduttiva(db, dati_allontanamento.getIdProprietarioCorrente());
				context.getRequest().setAttribute("proprietario", proprietario);

				Operatore detentore = new Operatore();
				detentore.queryRecordOperatorebyIdLineaProduttiva(db, dati_allontanamento.getIdDetentoreCorrente());
				context.getRequest().setAttribute("detentore", detentore);

			}

			else {
				return getReturn(context, "viewDocumentoAllontanamentoEmpty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "viewDocumentoAllontanamento");

	}
	
	
	public String executeCommandPrintCertificatoMorsicatura(ActionContext context) 
	{

		if (!hasPermission(context, "anagrafe_canina-documenti-view")) 
			return ("PermissionError");

		Connection db = null;

		int idAnimale = -1;
		int idSpecie = -1;
		int idEvento = -1;
		
		try 
		{
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");

			db = this.getConnection(context);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			if ((String) context.getRequest().getParameter("isEmpty") == null) 
			{
				SystemStatus systemStatus = this.getSystemStatus(context);
				String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
				String idSpecieString = (String) context.getRequest().getParameter("idSpecie");
				String idEventoString = (String) context.getRequest().getParameter("idEvento");

				if (idAnimaleString != null && !("").equals(idAnimaleString)) 
					idAnimale = new Integer(idAnimaleString).intValue();

				if (idSpecieString != null && !("").equals(idSpecieString)) 
					idSpecie = new Integer(idSpecieString).intValue();

				if (idEventoString != null && !("").equals(idEventoString)) 
					idEvento = new Integer(idEventoString).intValue();
				
				Animale thisAnimale = new Animale(db, idAnimale);
				context.getRequest().setAttribute("thisAnimale", thisAnimale);

				EventoMorsicatura dati_morsicatura = new EventoMorsicatura(db, idEvento);
				context.getRequest().setAttribute("dati_morsicatura", dati_morsicatura);

				LookupList razzaList = new LookupList();
				razzaList.setTable("lookup_razza");
				razzaList.setIdSpecie(idSpecie);
				razzaList.buildList(db);
				razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("razzaList", razzaList);

				LookupList tagliaList = new LookupList(db, "lookup_taglia");
				tagliaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("tagliaList", tagliaList);

				LookupList mantelloList = new LookupList(db, "lookup_mantello");
				mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("mantelloList", mantelloList);

				ComuniAnagrafica c = new ComuniAnagrafica();
				// PER ORA PRENDO TUTTI I COMUNI E NON SOLO QUELLI RELATIVI
				// ALL'ASL
				// UTENTE
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, 1);
				LookupList comuniList = new LookupList(listaComuni, -1);
				comuniList.addItem(-1, "");
				context.getRequest().setAttribute("comuniList", comuniList);
				

				Operatore proprietario = new Operatore();
				proprietario.queryRecordOperatorebyIdLineaProduttiva(db, dati_morsicatura.getIdProprietarioCorrente());
				context.getRequest().setAttribute("proprietario", proprietario);

				Operatore detentore = new Operatore();
				detentore.queryRecordOperatorebyIdLineaProduttiva(db, dati_morsicatura.getIdDetentoreCorrente());
				context.getRequest().setAttribute("detentore", detentore);
				
				
				LookupList tipologieMorso = new LookupList(db,"lookup_tipologia_morso");
				context.getRequest().setAttribute("tipologieMorso", tipologieMorso);
				
				LookupList tipologieMorsoRipetuto = new LookupList(db,"lookup_tipologia_morso_ripetuto");
				context.getRequest().setAttribute("tipologieMorsoRipetuto", tipologieMorsoRipetuto);
				
				LookupList tipologieRilievi = new LookupList(db,"lookup_tipologia_rilievi_sull_aggressore");
				context.getRequest().setAttribute("tipologieRilievi", tipologieRilievi);
				
				LookupList tipologieAnalisiGestione = new LookupList(db,"lookup_tipologia_analisi_gestione");
				context.getRequest().setAttribute("tipologieAnalisiGestione", tipologieAnalisiGestione);
				
				LookupList prevedibilitaEvento = new LookupList(db,"lookup_prevedibilita_evento");
				context.getRequest().setAttribute("prevedibilitaEvento", prevedibilitaEvento);
				
				LookupList taglieAggressore = new LookupList(db,"lookup_taglia_aggressore");
				context.getRequest().setAttribute("taglieAggressore", taglieAggressore);
				
				LookupList categorieVittima = new LookupList(db,"lookup_categoria_vittima");
				context.getRequest().setAttribute("categorieVittima", categorieVittima);
				
				LookupList taglieVittima = new LookupList(db,"lookup_taglia_vittima");
				context.getRequest().setAttribute("taglieVittima", taglieVittima);
				
				PreparedStatement pst = db.prepareStatement("Select * from controlli_ufficiali_cani_aggressori where ticketid = ?");
				
				
				if(dati_morsicatura.getTipologia()==2)
				{
					SchedaMorsicatura scheda = new SchedaMorsicatura().getById(db, dati_morsicatura.getIdSchedaMorsicatura());
					ArrayList<SchedaMorsicaturaRecords> records = new SchedaMorsicaturaRecords().getByIdScheda(db, scheda.getId());
					scheda.setRecords(records);
					context.getRequest().setAttribute("scheda", scheda);
				}
			}

			else {
				return getReturn(context, "viewDocumentoMorsicaturaEmpty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "viewDocumentoMorsicatura");

	}
	
	public String executeCommandPrintCertificatoAggressione(ActionContext context) 
	{

		if (!hasPermission(context, "anagrafe_canina-documenti-view")) 
			return ("PermissionError");

		Connection db = null;

		int idAnimale = -1;
		int idSpecie = -1;
		int idEvento = -1;
		
		try 
		{
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");

			db = this.getConnection(context);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			if ((String) context.getRequest().getParameter("isEmpty") == null) 
			{

				SystemStatus systemStatus = this.getSystemStatus(context);
				String idAnimaleString = (String) context.getRequest().getParameter("idAnimale");
				String idSpecieString = (String) context.getRequest().getParameter("idSpecie");
				String idEventoString = (String) context.getRequest().getParameter("idEvento");

				if (idAnimaleString != null && !("").equals(idAnimaleString)) 
					idAnimale = new Integer(idAnimaleString).intValue();

				if (idSpecieString != null && !("").equals(idSpecieString)) 
					idSpecie = new Integer(idSpecieString).intValue();

				if (idEventoString != null && !("").equals(idEventoString)) 
					idEvento = new Integer(idEventoString).intValue();
				
				Animale thisAnimale = new Animale(db, idAnimale);
				context.getRequest().setAttribute("thisAnimale", thisAnimale);

				EventoAggressione dati_aggressione = new EventoAggressione(db, idEvento);
				context.getRequest().setAttribute("dati_aggressione", dati_aggressione);

				LookupList specieList = new LookupList(db, "lookup_specie");
				specieList.addItem(-1, "--Tutti--");
				context.getRequest().setAttribute("specieList", specieList);

				LookupList razzaList = new LookupList();
				razzaList.setTable("lookup_razza");
				razzaList.setIdSpecie(idSpecie);
				razzaList.buildList(db);
				razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("razzaList", razzaList);

				LookupList tagliaList = new LookupList(db, "lookup_taglia");
				tagliaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("tagliaList", tagliaList);

				LookupList mantelloList = new LookupList(db, "lookup_mantello");
				mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("mantelloList", mantelloList);

				ComuniAnagrafica c = new ComuniAnagrafica();
				// PER ORA PRENDO TUTTI I COMUNI E NON SOLO QUELLI RELATIVI
				// ALL'ASL
				// UTENTE
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, 1);
				LookupList comuniList = new LookupList(listaComuni, -1);
				comuniList.addItem(-1, "");
				context.getRequest().setAttribute("comuniList", comuniList);
				
				Operatore proprietario = new Operatore();
				proprietario.queryRecordOperatorebyIdLineaProduttiva(db, dati_aggressione.getIdProprietarioCorrente());
				context.getRequest().setAttribute("proprietario", proprietario);

				Operatore detentore = new Operatore();
				detentore.queryRecordOperatorebyIdLineaProduttiva(db, dati_aggressione.getIdDetentoreCorrente());
				context.getRequest().setAttribute("detentore", detentore);
				
				LookupList tipologieMorso = new LookupList(db,"lookup_tipologia_morso");
				context.getRequest().setAttribute("tipologieMorso", tipologieMorso);
				
				LookupList tipologieMorsoRipetuto = new LookupList(db,"lookup_tipologia_morso_ripetuto");
				context.getRequest().setAttribute("tipologieMorsoRipetuto", tipologieMorsoRipetuto);
				
				LookupList tipologieRilievi = new LookupList(db,"lookup_tipologia_rilievi_sull_aggressore");
				context.getRequest().setAttribute("tipologieRilievi", tipologieRilievi);
				
				LookupList tipologieAnalisiGestione = new LookupList(db,"lookup_tipologia_analisi_gestione");
				context.getRequest().setAttribute("tipologieAnalisiGestione", tipologieAnalisiGestione);
				
				LookupList prevedibilitaEvento = new LookupList(db,"lookup_prevedibilita_evento");
				context.getRequest().setAttribute("prevedibilitaEvento", prevedibilitaEvento);
				
				LookupList taglieAggressore = new LookupList(db,"lookup_taglia_aggressore");
				context.getRequest().setAttribute("taglieAggressore", taglieAggressore);
				
				LookupList categorieVittima = new LookupList(db,"lookup_categoria_vittima_aggressione");
				context.getRequest().setAttribute("categorieVittima", categorieVittima);
				
				LookupList taglieVittima = new LookupList(db,"lookup_taglia_vittima");
				context.getRequest().setAttribute("taglieVittima", taglieVittima);
				
			}

			else {
				return getReturn(context, "viewDocumentoAggressioneEmpty");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "viewDocumentoAggressione");

	}

	public String executeCommandPrintRegistroUnico(ActionContext context)
	{
		if (!hasPermission(context, "registro_unico_aggressori-view")) 
			return ("PermissionError");

		Connection db = null;

		int idAnimale = -1;
		int idSpecie = -1;
		int idEvento = -1;

		try 
		{
			db = this.getConnection(context);
			
			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			idEvento = Integer.parseInt(context.getRequest().getParameter("idEvento"));
			
			RegistroUnico reg = new RegistroUnico(db, idEvento);
			context.getRequest().setAttribute("registro", reg );
			
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		return getReturn(context, "viewDocumentoRegistroUnico");

	}
	
	
	
	public String executeCommandUpdateStatoDocumento(ActionContext context) throws SQLException, IOException 
	{
		
		if (!hasPermission(context, "file_commissione_diritti_animali-view")) 
			return ("PermissionError");

		int idFile = -1;
		if (context.getRequest().getParameter("idFile") != null)
			idFile = Integer.parseInt(context.getRequest().getParameter("idFile"));

		String nuovoStato = context.getRequest().getParameter("nuovoStato");

		Connection db = null;
			
		try 
		{
			db = this.getConnection(context);
			
			PreparedStatement pst = db.prepareStatement("INSERT INTO stati_file_commissione (id, stato) VALUES (?, ?) ON CONFLICT (id) DO UPDATE SET stato = ?");
			pst.setInt(1, idFile);
			pst.setString(2, nuovoStato);
			pst.setString(3, nuovoStato);
			pst.executeUpdate();
			pst.close();
			
		}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				this.freeConnection(context, db);
			}

			
			String messaggioPost = "Stato file aggiornato con successo!";
			context.getRequest().setAttribute("messaggioPost", messaggioPost);

		GestioneAllegatiUpload gest = new GestioneAllegatiUpload();
		return gest.executeCommandListaFileCommissione(context);

	}
	
	
	public String executeCommandPrintSterilizzazione(ActionContext context){

		if (!hasPermission(context, "anagrafe_canina-documenti-view")) 
			return ("PermissionError");

		Connection db = null;
		int idAnimale = -1;
		int idSpecie = -1;

		try {
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");

			db = this.getConnection(context);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);


		

				SystemStatus systemStatus = this.getSystemStatus(context);
				String idAnimaleString = (String)context.getRequest().getParameter("idAnimale");
				String idSpecieString = (String)context.getRequest().getParameter("idSpecie");

				if (idAnimaleString != null && !("").equals(idAnimaleString)){
					idAnimale = new Integer(idAnimaleString).intValue();
				}

				if (idSpecieString != null && !("").equals(idSpecieString)){
					idSpecie = new Integer(idSpecieString).intValue();
				}
				else
				{
					Animale an = new Animale(db, idAnimale);
					idSpecie = an.getIdSpecie();
				}
				
				Animale thisAnimale = new Animale(db, idAnimale);

				EventoSterilizzazione sterilizzazione = new EventoSterilizzazione();
				sterilizzazione.GetSterilizzazioneByIdAnimale(db, idAnimale);
				context.getRequest().setAttribute("dati_sterilizzazione", sterilizzazione);
				
				Pratica pratica = new Pratica(db, sterilizzazione.getIdProgettoSterilizzazioneRichiesto());
				context.getRequest().setAttribute("pratica", pratica);
			
				UserBean utenteInserimento = new UserBean();
	            User u = new User(db, sterilizzazione.getEnteredby());
	            u.setBuildContact(true);
	            u.setBuildContactDetails(true);
	            u.buildResources(db);
	            utenteInserimento.setIdRange(u.getIdRange());
	            utenteInserimento.setUserRecord(u);
	            utenteInserimento.setUserId(u.getId());
	            utenteInserimento.setActualUserId(u.getId());
	            utenteInserimento.setClientType(context.getRequest());
	            context.getRequest().setAttribute("User", utenteInserimento);
	            
				LookupList specieList = new LookupList(db, "lookup_specie");
				specieList.addItem(-1, "--Tutti--");
				context.getRequest().setAttribute("specieList", specieList);

				LookupList razzaList = new LookupList();
				razzaList.setTable("lookup_razza");
				razzaList.setIdSpecie(idSpecie);
				razzaList.buildList(db);
				razzaList.addItem(-1, systemStatus
						.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("razzaList", razzaList);

				LookupList tagliaList = new LookupList(db, "lookup_taglia");
				tagliaList.addItem(-1, systemStatus
						.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("tagliaList", tagliaList);

				LookupList mantelloList = new LookupList(db, "lookup_mantello");
				mantelloList.addItem(-1, systemStatus
						.getLabel("calendar.none.4dashes"));
				// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("mantelloList", mantelloList);

				ComuniAnagrafica c = new ComuniAnagrafica();
				// PER ORA PRENDO TUTTI I COMUNI E NON SOLO QUELLI RELATIVI ALL'ASL
				// UTENTE
				ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, 1);
				LookupList comuniList = new LookupList(listaComuni, -1);
				comuniList.addItem(-1, "");
				context.getRequest().setAttribute("comuniList", comuniList);
				
				
				
				LookupList provinceList = new LookupList(db, "lookup_province");
				provinceList.addItem(-1, "");
				context.getRequest().setAttribute("provinceList", provinceList);
				
				context.getRequest().setAttribute("proprietario", thisAnimale.getProprietario());
				context.getRequest().setAttribute("detentore", thisAnimale.getDetentore());
				context.getRequest().setAttribute("thisAnimale", thisAnimale);



		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "viewCertificatoSterilizzazioneEffettuata");

	}

}
