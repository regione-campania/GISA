/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrazioniAnimali.actions;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.MiddleServlet;
import org.aspcfs.controller.SecurityHook;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.AnimaleList;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Furetto;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.registrazioniAnimali.base.Evento;
import org.aspcfs.modules.registrazioniAnimali.base.EventoAdozioneFuoriAsl;
import org.aspcfs.modules.registrazioniAnimali.base.EventoCessione;
import org.aspcfs.modules.registrazioniAnimali.base.EventoInserimentoVaccinazioni;
import org.aspcfs.modules.registrazioniAnimali.base.EventoList;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRegistrazioneBDU;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRilascioPassaporto;
import org.aspcfs.modules.registrazioniAnimali.base.RegistrazioniWKF;
import org.aspcfs.modules.sinaaf.Sinaaf;
import org.aspcfs.modules.system.base.Site;
import org.aspcfs.modules.system.base.SiteList;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.FileAesKeyException;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;

public class ProfilassiRabbia extends CFSModule {

	
	public String executeCommandDefault(ActionContext context) {
		
		if (hasPermission(context, "vaccinazione_anti_rabbia_inserimento-view")) {
			
			return executeCommandAdd(context);
	}else{
		return executeCommandPrepareGeneraTitolazioneAnticorpiRabbia(context);
	}
		
	}
	
	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "vaccinazione_anti_rabbia_inserimento-add")) {
			
				return ("PermissionError");
		}


		Connection db = null;
		try {
			db = this.getConnection(context);


			LookupList tipoVaccinoInoculato = new LookupList(db,
					"lookup_tipologia_vaccino_inoculato");
			context.getRequest().setAttribute("tipoVaccinoInoculato",
					tipoVaccinoInoculato);
			

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return getReturn(context, "Add");
		
	}
	
	public String executeCommandInsert(ActionContext context) throws SQLException {
		if (!hasPermission(context, "vaccinazione_anti_rabbia_inserimento-add")) {
			
				return ("PermissionError");
		}


		Connection db = null;
		try {
			
			db = this.getConnection(context);
			db.setAutoCommit(false);
			
			EventoInserimentoVaccinazioni vaccinazioni = (EventoInserimentoVaccinazioni) context
			.getRequest().getAttribute(
					"EventoInserimentoVaccinazioni");
			
			vaccinazioni.setEnteredby(this.getUserId(context));
			vaccinazioni.setModifiedby(this.getUserId(context));
			
			Animale thisAnimale = new Animale(db, vaccinazioni.getMicrochip());
			vaccinazioni.setIdAnimale(thisAnimale.getIdAnimale());
			vaccinazioni.setIdAslRiferimento(thisAnimale.getIdAslRiferimento()); //ASL INSERIMENTO EVENTO UGUALE ASL ANIMALE ???
			vaccinazioni.setIdStatoOriginale(thisAnimale.getStato());
			vaccinazioni.setIdProprietarioCorrente(thisAnimale.getIdProprietario());
			vaccinazioni.setIdDetentoreCorrente(thisAnimale.getIdDetentore());
			vaccinazioni.setSpecieAnimaleId(thisAnimale.getIdSpecie());
			vaccinazioni.setIdTipologiaVaccinoInoculato(context.getRequest().getParameter("idTipologiaVaccinoInoculato"));
			thisAnimale.setDataVaccino(vaccinazioni.getDataVaccinazione());
			thisAnimale.setNumeroLottoVaccino(vaccinazioni.getNumeroLottoVaccino());
			thisAnimale.update(db);
			vaccinazioni.insert(db);
			
			if(ApplicationProperties.getProperty("abilitato_sinaaf").equals("true"))
			{
				String esitoInvioSinaaf[] = new Sinaaf().inviaInSinaaf(db, getUserId(context),vaccinazioni.getIdEvento()+"", "evento");
				context.getRequest().setAttribute("messaggio", esitoInvioSinaaf[0]);
				context.getRequest().setAttribute("errore", esitoInvioSinaaf[1]);
			}
			context.getRequest().setAttribute("animale", thisAnimale);
			context.getRequest().setAttribute("avvisoCertificati", "Attenzione, non potrai più stampare questo certificato dopo aver abbandonato la pagina");
			


		} catch (Exception e) {
			db.rollback();
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			db.setAutoCommit(true);
			db.close();
			this.freeConnection(context, db);
			
		}
		

		return getReturn(context, "StampaCertificati");
		
	}
	
	public String executeCommandSearchForm12(ActionContext context){
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = context.getIpAddress();
		String username = user.getUserRecord().getUsername(); 
		context.getRequest().setAttribute("currentUser", username);
		int ruolo = user.getRoleId();
		if (ruolo!= Integer.valueOf(ApplicationProperties.getProperty("ID_RUOLO_LLP")) && ruolo != Integer.valueOf(ApplicationProperties.getProperty("UNINA")) ) //SOLO VETERINARIO PRIVATO
			return ("PermissionError");
	
		// Bypass search form for portal users
		if (isPortalUser(context)) {
			// return (executeCommandSearch(context));
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = getConnection(context);

			PagedListInfo eventiListInfo = this.getPagedListInfo(context,
					"eventiListInfo");
			eventiListInfo.setCurrentLetter("");
			eventiListInfo.setCurrentOffset(0);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			LookupList specieList = new LookupList(db, "lookup_specie");
			specieList.addItem(-1, "--Tutti--");
			context.getRequest().setAttribute("specieList", specieList);

			LookupList registrazioniList = new LookupList(db,
					"lookup_tipologia_registrazione");
			registrazioniList.addItem(-1, "--Tutte le registrazioni--");
			context.getRequest().setAttribute("registrazioniList",
					registrazioniList);

			Calendar cal = new GregorianCalendar();
			int annoCorrenteInt = cal.get(Calendar.YEAR);
			String annoCorrente = new Integer(annoCorrenteInt).toString();
			// int annoCorrenteInt = Integer.parseInt(annoCorrente);
			LookupList annoList = new LookupList(); // creo una nuova lookup

			annoList.addItem(annoCorrenteInt - 3, String
					.valueOf(annoCorrenteInt - 3));
			annoList.addItem(annoCorrenteInt - 2, String
					.valueOf(annoCorrenteInt - 2));
			annoList.addItem(annoCorrenteInt - 1, String
					.valueOf(annoCorrenteInt - 1));
			annoList.addItem(annoCorrenteInt, annoCorrente);
			annoList.addItem(-1, "Tutti");

			context.getRequest().setAttribute("annoList", annoList);

			// buildFormElements(context, db);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Search Evento", "Evento Search");
		
		
		
		return ("SearchForm12OK");
}
	
	public String executeCommandSearch12(ActionContext context) throws UnknownHostException {
		

		PagedListInfo EventiListInfo = this.getPagedListInfo(context,
				"EventiListInfo");
		EventiListInfo.setLink("ProfilassiRabbia.do?command=Search12");
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = this.getConnection(context);
			EventoList listaEventi = new EventoList();
			
			if(context.getRequest().getParameter("limit") == null ){
			listaEventi.setPagedListInfo(EventiListInfo);
		//	listaEventi.setPagedListInfo(EventiListInfo);
			listaEventi.setMinerOnly(false);
			listaEventi.setTypeId(EventiListInfo.getFilterKey("listFilter1"));
			}

			if (context.getRequest().getParameter("type") != null) {

				if (("out").equals((String) context.getRequest().getParameter(
						"type"))) {
					listaEventi
							.setId_asl_vecchio_proprietario(getUserAsl(context));
				} else if (("in").equals((String) context.getRequest()
						.getParameter("type"))) {
					listaEventi
							.setId_asl_nuovo_proprietario(getUserAsl(context));
				}
			}

			if (context.getRequest().getParameter("stato") != null
					&& ("opened").equals((String) context.getRequest()
							.getParameter("stato"))) {
				listaEventi.setGet_only_opened(true);
			}

			listaEventi.setStageId(EventiListInfo
					.getCriteriaValue("searchcodeStageId"));
			EventiListInfo.setSearchCriteria(listaEventi, context);
			
			if (listaEventi.getEventoda()!=null)
				context.getRequest().setAttribute("da", listaEventi.getEventoda().toString());
			if (listaEventi.getEventoa()!=null)
				context.getRequest().setAttribute("a", listaEventi.getEventoa().toString());

			UserBean user = (UserBean) context.getSession().getAttribute("User");
			String ip = context.getIpAddress();
			String username = user.getUserRecord().getUsername(); 
			int user_id = user.getUserId();
			context.getRequest().setAttribute("currentUser", username);
			
			int idSpecie=-1;
			int idAsl  = -1;
			if (context.getRequest().getParameter("searchcodeidSpecieAnimale")!=null)
				idSpecie = new Integer(context.getRequest().getParameter("searchcodeidSpecieAnimale"));
			
//			if (context.getRequest().getParameter("idAslInserimentoEventoBDU")!=null)
//				idAsl = new Integer(context.getRequest().getParameter("idAslInserimentoEventoBDU"));
			
			/*String dataDa = context.getRequest().getParameter("searchtimestampeventoda");
			String dataA = context.getRequest().getParameter("searchtimestampeventoa");
		
			DateFormat sdf = new SimpleDateFormat("dd/MM/yy");
			Date date = sdf.parse(dataDa);
			Timestamp timeStampDataDa = new  Timestamp(date.getTime());
			
			date = sdf.parse(dataA);
			Timestamp timeStampDataA = new  Timestamp(date.getTime());*/
		
			listaEventi.setIdSpecieAnimale(idSpecie); //SOLO SPECIE SELEZIONATA
			listaEventi.setIdTipologiaEvento(36); //SOLO VACCINAZIONI
			listaEventi.setTipoVaccinazione(1); //SOLO ANTIRABBIA
			listaEventi.setId_utente_inserimento(user_id); //SOLO UTENTE CORRENTE
			//listaEventi.setIdAslInserimentoEventoBDU(idAsl);
			listaEventi.buildList(db);
			
			//Genero lista animali associata agli eventi 
			AnimaleList animaleList = new AnimaleList();
			Iterator j = listaEventi.iterator();
		    if ( j.hasNext() ) {
		       int i =0;
		      while (j.hasNext()) {
		        i++;
		        Evento thisEvento = (Evento)j.next();
		        Animale thisAnimale = new Animale(db, thisEvento.getIdAnimale());
		        animaleList.add(thisAnimale);
		      }}
		    context.getRequest().setAttribute("animaleList", animaleList);
			context.getRequest().setAttribute("listaEventi", listaEventi);

			LookupList registrazioniList = new LookupList(db,
					"lookup_tipologia_registrazione");
			registrazioniList.addItem(-1, "--Tutte le registrazioni--");
			context.getRequest().setAttribute("registrazioniList",
					registrazioniList);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- Tutte le asl --");
			context.getRequest().setAttribute("AslList", siteList);
			
			LookupList razzaList = new LookupList(db, "lookup_razza");
			context.getRequest().setAttribute("razzaList", razzaList);
			
			
			LookupList provinceList = new LookupList(db, "lookup_province");
			provinceList.addItem(-1, "");
			context.getRequest().setAttribute("provinceList", provinceList);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		
		if ((String) context.getRequest().getParameter("cert") != null 
				&& !("").equals((String) context.getRequest().getParameter("cert")) && 
				("true").equals((String) context.getRequest().getParameter("cert"))){
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
			//String HOST_CORRENTE= ApplicationProperties.getProperty("APP_HOST_CANINA");
			/*String SERVER_BDU  	=  InetAddress.getByName("hostAppBduPublic").getHostAddress();
			String APPLICATION_PORT = prefs.get("APPLICATION.PORT");
			String APPLICATION_NAME = prefs.get("APPLICATION.NAME");
			String url = "http://".concat(SERVER_BDU).concat(":").concat(APPLICATION_PORT).concat("/").concat(APPLICATION_NAME).concat("/");
			context.getRequest().setAttribute("SERVER_BDU", url);*/
			return getReturn(context, "Mod12");
		}
		return ("List12OK");
	}
	
	public String executeCommandListaMailASL(ActionContext context){

		String mail_ce= ApplicationProperties.getProperty("MAIL_CE1");
		String mail_na1= ApplicationProperties.getProperty("MAIL_NA1");
		String mail_na2= ApplicationProperties.getProperty("MAIL_NA2");
		String mail_na3= ApplicationProperties.getProperty("MAIL_NA3");
		String mail_av= ApplicationProperties.getProperty("MAIL_AV1");
		String mail_bn= ApplicationProperties.getProperty("MAIL_BN1");
		String mail_sa= ApplicationProperties.getProperty("MAIL_SA1");
		context.getRequest().setAttribute("mail_ce", mail_ce);
		context.getRequest().setAttribute("mail_na1", mail_na1);
		context.getRequest().setAttribute("mail_na2", mail_na2);
		context.getRequest().setAttribute("mail_na3", mail_na3);
		context.getRequest().setAttribute("mail_av", mail_av);
		context.getRequest().setAttribute("mail_bn", mail_bn);
		context.getRequest().setAttribute("mail_sa", mail_sa);
		return ("ListaMailASLOk");
	
	}
	
	public String executeCommandPrintCertificatoVaccinazioneAntiRabbia(ActionContext context){

		if (!hasPermission(context, "vaccinazione_anti_rabbia_inserimento-add")) {
			return ("PermissionError");
		}

		Connection db = null;
		int idAnimale = -1;
		int idSpecie = -1;

		try {
			//String HOST_CORRENTE = context.getRequest().getLocalAddr();
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
			//String HOST_CORRENTE= ApplicationProperties.getProperty("APP_HOST_CANINA");
		/*	String SERVER_BDU  	=  InetAddress.getByName("hostAppBduPublic").getHostAddress();
			String APPLICATION_PORT = prefs.get("APPLICATION.PORT");
			String APPLICATION_NAME = prefs.get("APPLICATION.NAME");

			String url = "http://".concat(SERVER_BDU).concat(":").concat(APPLICATION_PORT).concat("/").concat(APPLICATION_NAME).concat("/");

			context.getRequest().setAttribute("SERVER_BDU", url);*/

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

				EventoInserimentoVaccinazioni vaccinazione = EventoInserimentoVaccinazioni.getUltimaVaccinazioneDaTipo(db, idAnimale, EventoInserimentoVaccinazioni.antirabbica);
				context.getRequest().setAttribute("dati_vaccinazione", vaccinazione);
			
				UserBean utenteInserimento = new UserBean();
	            User u = new User(db, vaccinazione.getEnteredby());
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
				
				context.getRequest().setAttribute("animale", thisAnimale);



		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "viewCertificatoVaccinazioneEffettuata");

	}
	
	public String executeCommandPrintRichiestaCampioniRabbia(
			ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina-documenti-view")) {
			return ("PermissionError");
		}

		Connection db = null;

		Cane thisCane = null;
		Gatto thisGatto = null;
		Furetto thisFuretto = null;
		int idAnimale = -1;
		int idSpecie = -1;
		String microchip = "";

		try {

			db = this.getConnection(context);

		//	String HOST_CORRENTE = context.getRequest().getLocalAddr();
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
			//String HOST_CORRENTE= ApplicationProperties.getProperty("APP_HOST_CANINA");
		/*	String SERVER_BDU  	=  InetAddress.getByName("hostAppBduPublic").getHostAddress();
			String APPLICATION_PORT = prefs.get("APPLICATION.PORT");
			String APPLICATION_NAME = prefs.get("APPLICATION.NAME");

			String url = "http://".concat(SERVER_BDU).concat(":").concat(
					APPLICATION_PORT).concat("/").concat(APPLICATION_NAME)
					.concat("/");

			context.getRequest().setAttribute("SERVER_BDU", url); */

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("aslList", siteList);

			SystemStatus systemStatus = this.getSystemStatus(context);
			String idAnimaleString = (String) context.getRequest()
			.getParameter("idAnimale");
			String idSpecieString = (String) context.getRequest().getParameter(
			"idSpecie");

			if (idAnimaleString != null && !("").equals(idAnimaleString)) {
				idAnimale = new Integer(idAnimaleString).intValue();
			}

			if (idSpecieString != null && !("").equals(idSpecieString)) {
				idSpecie = new Integer(idSpecieString).intValue();
			}
			
			
			Animale thisAnimale = new Animale();
			
			if (idAnimale == -1){
				 microchip = (context.getRequest().getParameter("microchip") != null) ? (String) context.getRequest().getParameter("microchip") : "";
			}
			
			if (microchip != null && !("").equals(microchip)){
				thisAnimale = new Animale(db, microchip);
				idAnimale = thisAnimale.getIdAnimale();
				idSpecie = thisAnimale.getIdSpecie();
				
			}

			switch (idSpecie) {
			case 1: {
				thisCane = new Cane(db, idAnimale);
				context.getRequest().setAttribute("Cane", thisCane);
				thisAnimale = thisCane;

				break;
			}

			case 2: {
				thisGatto = new Gatto(db, idAnimale);
				context.getRequest().setAttribute("Gatto", thisGatto);
				thisAnimale = thisGatto;
				break;
			}
			case 3: {
				thisFuretto = new Furetto(db, idAnimale);
				context.getRequest().setAttribute("Furetto", thisFuretto);
				thisAnimale = thisFuretto;
				break;
			}

			default: {
				break;
			}
			}

			EventoRegistrazioneBDU dati_registrazione = new EventoRegistrazioneBDU();
			dati_registrazione =  EventoRegistrazioneBDU.getEventoRegistrazione(db, idAnimale);
			context.getRequest().setAttribute("dati_registrazione",
					dati_registrazione);

			EventoInserimentoVaccinazioni vaccinazione_rabbia_ultima = EventoInserimentoVaccinazioni
			.getUltimaVaccinazioneDaTipo(db,
					thisAnimale.getIdAnimale(), 1); // Mettere
			// tipo_vacc_rabbia
			// = 1 in file di
			// properties
			context.getRequest().setAttribute("dati_vaccinazione",
					vaccinazione_rabbia_ultima);

			Operatore proprietario = new Operatore();
			/*
			 * proprietario.queryRecordOperatorebyIdLineaProduttiva(db,
			 * dati_registrazione.getIdProprietario());
			 * context.getRequest().setAttribute("proprietario", proprietario);
			 */

			Operatore detentore = new Operatore();
			/*
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

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "viewRichiestaCampioniRabbia");

	}
	
	public String executeCommandPrepareGeneraTitolazioneAnticorpiRabbia(
			ActionContext context) {

		if (!hasPermission(context, "vaccinazione_anti_rabbia_titolazione_anticorpi-view")) {
			return ("PermissionError");
		}
		
		Connection db = null;
		try {
			db = this.getConnection(context);

			

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		
		
		return getReturn(context, "microchipTitolazione");
		
	}
	
	
	

	
	
}
