/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrazioniAnimali.actions;

import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Furetto;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.CanilePienoException;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Municipalita;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.RegistrazioneModificaIndirizzoOperatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.partitecommerciali.base.PartitaCommerciale;
import org.aspcfs.modules.passaporti.base.Passaporto;
import org.aspcfs.modules.praticacontributi.base.Pratica;
import org.aspcfs.modules.praticacontributi.base.PraticaDWR;
import org.aspcfs.modules.praticacontributi.base.PraticaList;
import org.aspcfs.modules.registrazioniAnimali.base.Evento;
import org.aspcfs.modules.registrazioniAnimali.base.EventoAdozioneDaCanile;
import org.aspcfs.modules.registrazioniAnimali.base.EventoAdozioneDaColonia;
import org.aspcfs.modules.registrazioniAnimali.base.EventoAdozioneDistanza;
import org.aspcfs.modules.registrazioniAnimali.base.EventoAdozioneFuoriAsl;
import org.aspcfs.modules.registrazioniAnimali.base.EventoAggressione;
import org.aspcfs.modules.registrazioniAnimali.base.EventoBloccoAnimale;
import org.aspcfs.modules.registrazioniAnimali.base.EventoCambioDetentore;
import org.aspcfs.modules.registrazioniAnimali.base.EventoCattura;
import org.aspcfs.modules.registrazioniAnimali.base.EventoCessione;
import org.aspcfs.modules.registrazioniAnimali.base.EventoCessioneImport;
import org.aspcfs.modules.registrazioniAnimali.base.EventoDecesso;
import org.aspcfs.modules.registrazioniAnimali.base.EventoEsitoControlli;
import org.aspcfs.modules.registrazioniAnimali.base.EventoFurto;
import org.aspcfs.modules.registrazioniAnimali.base.EventoInserimentoMicrochip;
import org.aspcfs.modules.registrazioniAnimali.base.EventoInserimentoVaccinazioni;
import org.aspcfs.modules.registrazioniAnimali.base.EventoList;
import org.aspcfs.modules.registrazioniAnimali.base.EventoModificaResidenza;
import org.aspcfs.modules.registrazioniAnimali.base.EventoMorsicatura;
import org.aspcfs.modules.registrazioniAnimali.base.EventoPrelievoDNA;
import org.aspcfs.modules.registrazioniAnimali.base.EventoPresaCessioneImport;
import org.aspcfs.modules.registrazioniAnimali.base.EventoPresaInCaricoDaAdozioneFuoriAsl;
import org.aspcfs.modules.registrazioniAnimali.base.EventoPresaInCaricoDaCessione;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRegistrazioneBDU;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRegistrazioneEsitoControlliCommerciali;
import org.aspcfs.modules.registrazioniAnimali.base.EventoReimmissione;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRestituzioneACanile;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRestituzioneAslOrigine;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRientroFuoriRegione;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRientroFuoriStato;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRilascioPassaporto;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRitrovamento;
import org.aspcfs.modules.registrazioniAnimali.base.EventoSbloccoAnimale;
import org.aspcfs.modules.registrazioniAnimali.base.EventoSmarrimento;
import org.aspcfs.modules.registrazioniAnimali.base.EventoSterilizzazione;
import org.aspcfs.modules.registrazioniAnimali.base.EventoTrasferimento;
import org.aspcfs.modules.registrazioniAnimali.base.EventoTrasferimentoCanile;
import org.aspcfs.modules.registrazioniAnimali.base.EventoTrasferimentoFuoriRegione;
import org.aspcfs.modules.registrazioniAnimali.base.EventoTrasferimentoFuoriRegioneSoloProprietario;
import org.aspcfs.modules.registrazioniAnimali.base.EventoTrasferimentoFuoriStato;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRitrovamentoNonDenunciato;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRestituzioneAProprietario;
import org.aspcfs.modules.registrazioniAnimali.base.EventoTrasferimentoSindaco;
import org.aspcfs.modules.registrazioniAnimali.base.RegistrazioniWKF;
import org.aspcfs.modules.schedaMorsicatura.base.SchedaMorsicatura;
import org.aspcfs.modules.schedaMorsicatura.base.SchedaMorsicaturaRecords;
import org.aspcfs.modules.sinaaf.Sinaaf;
import org.aspcfs.modules.ws.WsPost;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.EsitoControllo;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.MediaType;

public class RegistrazioniAnimale extends CFSModule {

	public String executeCommandDefault(ActionContext context) {

		return executeCommandSearchForm(context);
	}

	public String executeCommandDashboard(ActionContext context) {
		if (!hasPermission(context, "anagrafe_canina_registrazioni-view")) {
			if (!hasPermission(context, "anagrafe_canina_registrazioni-view")) {
				return ("PermissionError");
			}
		}

		Connection db = null;
		try {
			db = this.getConnection(context);
			EventoList listaEventi = new EventoList();

			listaEventi.setFlag_richiesta_contributo_regionale(true);
			listaEventi.setId_asl_registrazione(0);
			listaEventi.setId_utente_inserimento(12);
			listaEventi.buildList(db);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return ("DashboardOK");
	}

	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "anagrafe_canina_registrazioni-view")) {
			if (!hasPermission(context, "anagrafe_canina_registrazioni-view")) {
				return ("PermissionError");
			}
		}

		UserBean user = (UserBean) context.getSession().getAttribute("User");
		
		if(context.getRequest().getAttribute("ErrorBlocco")!=null && !((String)context.getRequest().getAttribute("ErrorBlocco")).equals(""))
			context.getRequest().setAttribute("ErrorBlocco", (String)context.getRequest().getAttribute("ErrorBlocco"));


		if (context.getRequest().getAttribute("ErroreWKF") != null
				&& !("").equals(context.getRequest().getAttribute("ErroreWKF"))) {
			// Ritorno da insert con errore nel wkf, devo notificarlo all'utente
			context.getRequest().setAttribute("ErroreWKF",
					context.getRequest().getAttribute("ErroreWKF"));
			// return ("AddOK");
		}
		
		
		
				context.getRequest().setAttribute("fromPassaporto",context.getRequest().getParameter("fromPassaporto"));

		Connection db = null;
		try {
			db = this.getConnection(context);

			Animale thisAnimaleNewInfo = null;
			thisAnimaleNewInfo = (Animale) context.getRequest().getAttribute(
					"animale");
			Animale thisAnimaleOldInfo = (Animale) context.getRequest()
					.getAttribute("oldAnimale");

			Animale thisAnimale = null;

			if (thisAnimaleNewInfo == null) {
				int idAnimale = 0;
				if ((String) context.getRequest().getParameter("idAnimale") != null
						&& !"".equals((String) context.getRequest()
								.getParameter("idAnimale"))) {
					idAnimale = new Integer((String) context.getRequest()
							.getParameter("idAnimale")).intValue();
					thisAnimale = new Animale(db, idAnimale);
				}
				if ((String) context.getRequest().getParameter("microchip") != null && !"".equals((String) context.getRequest().getParameter("microchip"))) 
				{
					// idAnimale = new Integer((String) context.getRequest()
					// .getParameter("idAnimale")).intValue();
					thisAnimale = new Animale(db, ((String) context.getRequest().getParameter("microchip")).replaceAll(" ", ""));
				}

			} else {
				thisAnimale = thisAnimaleNewInfo;
				// thisAnimale = new Animale(db,
				// thisAnimaleNewInfo.getIdAnimale());
			}

			if (thisAnimaleOldInfo == null) {
				thisAnimaleOldInfo = thisAnimale; // SE NN VENGO Da insert lo
													// metto uguale a quello del
													// db
			}

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "--Nessuna asl--");
			context.getRequest().setAttribute("AslList", siteList);

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, 1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			context.getRequest().setAttribute("comuniList", comuniList);

			LookupList provinceList = new LookupList(db, "lookup_province");
			context.getRequest().setAttribute("provinceList", provinceList);

			LookupList razzaList = new LookupList(db, "lookup_razza");
			context.getRequest().setAttribute("razzaList", razzaList);

			context.getRequest().setAttribute("oldAnimale", thisAnimaleOldInfo);
			context.getRequest().setAttribute("animale", thisAnimale);
			context.getRequest().setAttribute("animalenewinfo",
					thisAnimaleNewInfo);

			RegistrazioniWKF wk = new RegistrazioniWKF();
			wk.buildWkfDati(
					context,
					thisAnimale,
					hasPermission(context,
							"anagrafe_canina_registrazioni_pregresse-add"),
					user, isUgualeAslAnimaleAslUtente(context, thisAnimale), db);

			/*
			 * wk.setIdStato(thisAnimale.getStato());
			 * wk.setIdSpecie(thisAnimale.getIdSpecie());
			 * wk.setFlagIncludiHd(hasPermission(context,
			 * "anagrafe_canina_registrazioni_pregresse-add"));
			 * wk.setCheckSoloRegistrazioniEffettuabiliFuoriAsl
			 * ((!isUgualeAslAnimaleAslUtente(context, thisAnimale) &&
			 * !thisAnimale.isFlagUltimaOperazioneFuoriDominioAsl())); //SE HO
			 * UN ANIMALE LA CUI ULTIMA OPERAZIONE E' STATA ESEGUITA DA FUORI
			 * ASL E L'UTENTE CORRENTE E' DI QUELLA ASL ALLORA ABILITO SOLO LE
			 * POSSIBILI REGISTRAZIONI
			 * wk.setCheckSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl
			 * (thisAnimale.isFlagUltimaOperazioneFuoriDominioAsl());
			 * wk.setFlagIsUtenteInCaricoPerFuoriDominio
			 * (thisAnimale.getIdAslUltimaOperazioneFuoriDominioAsl() > 0 &&
			 * thisAnimale.getIdAslUltimaOperazioneFuoriDominioAsl() ==
			 * getUserAsl(context));
			 * wk.setFlagIsAslProprietariaConAnimaleFuoriDominioAsl
			 * (thisAnimale.isFlagUltimaOperazioneFuoriDominioAsl() &&
			 * thisAnimale.getIdAslRiferimento() == getUserAsl(context));
			 * wk.setFlagIsAslDiversaConAnimaleFuoriDominioAsl
			 * (thisAnimale.isFlagUltimaOperazioneFuoriDominioAsl() &&
			 * thisAnimale.getIdAslRiferimento() != getUserAsl(context) &&
			 * thisAnimale.getIdAslUltimaOperazioneFuoriDominioAsl() !=
			 * getUserAsl(context) );
			 * 
			 * 
			 * if (thisAnimale.getTatuaggio() != null &&
			 * !("").equals(thisAnimale.getTatuaggio())) {
			 * wk.setFlagHasSecondoMicrochip(true); }
			 * 
			 * if (thisAnimale.getNumeroPassaporto() != null &&
			 * !("").equals(thisAnimale.getNumeroPassaporto())) {
			 * wk.setFlagHasPassaporto(true); }
			 * 
			 * if (thisAnimale.cercaRitrovamentoNonDenunciatoAperto(db) > 0) {
			 * wk.setFlagHasRitrovamentoNonDenunciato(true); }
			 * 
			 * if (thisAnimale.isFlagDetenutoInCanileDopoRitrovamento()){
			 * wk.setFlagHasDetenzioneInCanileDopoRitrovamento(true); //il cane
			 * è stato ritrovato e affidato a un canile, devo poter restituire a
			 * prop }
			 * 
			 * if (thisAnimale.getIdSpecie() == Cane.idSpecie &&
			 * thisAnimale.getFlagPrelievoDnaEffettuato(db) == true){
			 * wk.setFlagPrelievoDnaEffetuato(true);
			 * 
			 * }
			 */
			ArrayList regTodo = wk.getRegistrazioniCodeDaStato(db);
			ArrayList reg = wk.getRegistrazioniDaStato(db);

			boolean contains = true;
			String idTipologia = null;
			if (context.getRequest().getParameter("idTipologiaEvento") != null
					&& !("").equals((String) context.getRequest().getParameter(
							"idTipologiaEvento"))
					|| context.getRequest().getAttribute("idTipologiaEvento") != null
					&& !("").equals((String) context.getRequest().getAttribute(
							"idTipologiaEvento"))) {
				// context.getRequest().setAttribute("fixed", "fixed");
				idTipologia = (context.getRequest().getParameter(
						"idTipologiaEvento") != null) ? ((String) context
						.getRequest().getParameter("idTipologiaEvento"))
						: ((String) context.getRequest().getAttribute(
								"idTipologiaEvento"));
				if (!(regTodo.contains(new Integer(idTipologia).intValue()))) {
					contains = false;

				}
			}
			if (contains) {

				if (idTipologia != null) {
					Evento dettagli_base = new Evento();
					dettagli_base.setIdTipologiaEvento(idTipologia);
					context.getRequest().setAttribute("Evento", dettagli_base);
				}
				LookupList registrazioniList = new LookupList(reg, -1);
				registrazioniList.addItem(-1, "--Seleziona--");
				context.getRequest().setAttribute("registrazioniList",
						registrazioniList);

				LookupList statoList = new LookupList(db,
						"lookup_tipologia_stato");
				context.getRequest().setAttribute("statoList", statoList);

				EventoCessione cessioneInCorso = new EventoCessione();
				cessioneInCorso.GetCessioneApertaByIdAnimale(db,
						thisAnimale.getIdAnimale());
				context.getRequest().setAttribute("cessioneaperta",
						cessioneInCorso);

				EventoAdozioneFuoriAsl adozioneIncorso = new EventoAdozioneFuoriAsl();
				adozioneIncorso.GetAdozioneApertaByIdAnimale(db,
						thisAnimale.getIdAnimale());
				context.getRequest().setAttribute("adozioneaperta",
						adozioneIncorso);

				EventoRilascioPassaporto passaportoInCorso = new EventoRilascioPassaporto();
				passaportoInCorso.GetPassaportoAttivoByIdAnimale(db,
						thisAnimale.getIdAnimale());
				context.getRequest().setAttribute("passaportoInCorso",
						passaportoInCorso);

				EventoReimmissione reimmissionePrecedente = new EventoReimmissione();
				reimmissionePrecedente.GetUltimaReimmissione(db,
						thisAnimale.getIdAnimale());
				context.getRequest().setAttribute("reimmissionePrecedente",
						reimmissionePrecedente);
				
				
				EventoBloccoAnimale blocco = new EventoBloccoAnimale();
				blocco.getUltimoBlocco(db,
						thisAnimale.getIdAnimale());
//				context.getRequest().setAttribute("reimmissionePrecedente",
//						reimmissionePrecedente);

				String datatocheck = null;
				String labeldatatocheck = "";

				if (thisAnimale.isFlagDecesso()) {

					EventoDecesso decesso = new EventoDecesso();
					decesso.getEventoDecesso(db, thisAnimale.getIdAnimale());
					context.getRequest().setAttribute("decesso", decesso);
					datatocheck = new SimpleDateFormat("dd/MM/yyyy")
							.format(decesso.getDataDecesso());
					labeldatatocheck = "Data Decesso";

				} else if (cessioneInCorso.getIdEvento() > 0) {
					datatocheck = new SimpleDateFormat("dd/MM/yyyy")
							.format(cessioneInCorso.getDataCessione());
					labeldatatocheck = "Data Cessione";
				} else if(idTipologia != null && !("").equals(idTipologia) &&
						Integer.valueOf(idTipologia) == EventoSbloccoAnimale.idTipologiaDB && blocco.getId() > 0){
					datatocheck = new SimpleDateFormat("dd/MM/yyyy")
					.format(blocco.getDataBlocco());
			labeldatatocheck = "Data Blocco";
				}
				else {
					EventoTrasferimentoFuoriRegione trasferimentoFR = new EventoTrasferimentoFuoriRegione();
					trasferimentoFR.getEventoTrasferimentoFuoriRegione(db,
							thisAnimale.getIdAnimale());
					if (trasferimentoFR.getIdEvento() > 0) {
						datatocheck = new SimpleDateFormat("dd/MM/yyyy")
								.format(trasferimentoFR
										.getDataTrasferimentoFuoriRegione());
						labeldatatocheck = "Data Trasferimento Fuori Regione";
					}
				}

				context.getRequest().setAttribute("datatocheck", datatocheck);
				context.getRequest().setAttribute("labeldatatocheck",
						labeldatatocheck);

				// Action di provenienza
				String servletPath = context.getRequest().getServletPath();
				String actionFrom = servletPath.substring(
						servletPath.indexOf("/") + 1,
						servletPath.indexOf(".do"));
				context.getRequest().setAttribute("actionFrom", actionFrom);
				
				
				//Calcolo data ultima registrazione
				EventoList listaEventi = new EventoList();
				listaEventi.setId_animale(thisAnimale.getIdAnimale());
				listaEventi.buildList(db);
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			    long dataUltimaRegistrazione =-1;
			    long enteredUltimaRegistrazione =-1;
				Iterator<Evento>  j = listaEventi.iterator();
				if ( j.hasNext() ) 
				{
					int i =0;
				    while (j.hasNext()) 
				    {
				        i++;
				        Evento thisEvento = (Evento)j.next();
				        long dataThisRegistrazione = (thisEvento.getDataRegistrazione()!=null)?(thisEvento.getDataRegistrazione().getTime()):(thisEvento.getEntered().getTime());
				        Date dataThisRegistrazioneDate = new Date(dataThisRegistrazione);
				        Date dataThisRegistrazioneDate2 = sdf.parse(sdf.format(dataThisRegistrazioneDate));
				        dataThisRegistrazione = dataThisRegistrazioneDate2.getTime();
				        
				        long enteredThisRegistrazione = thisEvento.getEntered().getTime();
				        if (dataThisRegistrazione>dataUltimaRegistrazione || (dataThisRegistrazione==dataUltimaRegistrazione && enteredThisRegistrazione>enteredUltimaRegistrazione))
				        {
				        	dataUltimaRegistrazione=dataThisRegistrazione;
				        	enteredUltimaRegistrazione=enteredThisRegistrazione;
				        }
				    }  
				}
				
				context.getRequest().setAttribute("dataUltimaRegistrazione", sdf.format(new Date(dataUltimaRegistrazione)));
				  
			} else {
				context.getRequest()
						.setAttribute("ErroreWKF",
								"Registrazione non possibile per il microchip selezionato");
				return "AddNoNAVOK";
			}

		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		if ((String) context.getRequest().getParameter("caller") != null && !("").equals((String) context.getRequest().getParameter("caller"))){
			return ("AddNoNAVOK");
		}

		return getReturn(context, "Add");
		// return ("AddOK");
	}

	// Mi serve per aprire il dett animale nelle cessioni incompiute e rimanere
	// nel tab giusto

	public String executeCommandDetailsAnimale(ActionContext context) {
		if (!hasPermission(context, "anagrafe_canina-anagrafe_canina-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Animale thisAnimale = null;
		try {

			String tempanimaleId = context.getRequest().getParameter(
					"animaleId");
			if (tempanimaleId == null) {
				tempanimaleId = (String) context.getRequest().getAttribute(
						"animaleId");
			}

			String specieAnimaleId = context.getRequest().getParameter(
					"idSpecie");
			if (specieAnimaleId == null) {
				specieAnimaleId = (String) context.getRequest().getAttribute(
						"idSpecie");
			}

			Integer tempid = null;

			int idPartita = -1;

			if (tempanimaleId != null) {
				tempid = Integer.parseInt(tempanimaleId);

				db = this.getConnection(context);
				// costruisco la lookup per recuperare la tipologia
				LookupList lookupSpecie = new LookupList(db, "lookup_specie");
				context.getRequest().setAttribute("LookupSpecie", lookupSpecie);
				RegistrazioniWKF wkf = new RegistrazioniWKF();

				switch ((Integer.parseInt(specieAnimaleId))) {
				case Cane.idSpecie: {
					Cane thisCane = new Cane(db, tempid);
					context.getRequest()
							.setAttribute("caneDettaglio", thisCane);
					context.getRequest().setAttribute("animaleDettaglio",
							(Animale) thisCane);
					wkf.setIdStato(thisCane.getStato());
					wkf.setIdSpecie(thisCane.getIdSpecie());
					wkf.checkPossibilitaRegistrazioni(db);
					context.getRequest().setAttribute("wkf", wkf);
					idPartita = thisCane.getIdPartitaCircuitoCommerciale();
					break;
				}
				case Gatto.idSpecie: {

					Gatto thisGatto = new Gatto(db, tempid);
					context.getRequest().setAttribute("gattoDettaglio",
							thisGatto);
					context.getRequest().setAttribute("animaleDettaglio",
							(Animale) thisGatto);
					wkf.setIdStato(thisGatto.getStato());
					wkf.setIdSpecie(thisGatto.getIdSpecie());
					wkf.checkPossibilitaRegistrazioni(db);
					context.getRequest().setAttribute("wkf", wkf);
					idPartita = thisGatto.getIdPartitaCircuitoCommerciale();
					// thisAnimale = new Cane(db, tempid);
					break;
				}
				case Furetto.idSpecie: {

					Furetto thisFuretto = new Furetto(db, tempid);
					context.getRequest().setAttribute("furettoDettaglio",
							thisFuretto);
					context.getRequest().setAttribute("animaleDettaglio",
							(Animale) thisFuretto);
					wkf.setIdStato(thisFuretto.getStato());
					wkf.setIdSpecie(thisFuretto.getIdSpecie());
					wkf.checkPossibilitaRegistrazioni(db);
					context.getRequest().setAttribute("wkf", wkf);
					idPartita = thisFuretto.getIdPartitaCircuitoCommerciale();
					// thisAnimale = new Cane(db, tempid);
					break;
				}
				}

			}

			if (idPartita > -1) {
				PartitaCommerciale partita = new PartitaCommerciale(db,
						idPartita);
				context.getRequest().setAttribute("partita", partita);
			}

			// lookups

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, -1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			context.getRequest().setAttribute("comuniList", comuniList);

			LookupList tagliaList = new LookupList(db, "lookup_taglia");
			tagliaList.addItem(-1,
					systemStatus.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("tagliaList", tagliaList);

			LookupList siteList = new LookupList();
			siteList.setShowDisabledFlag(true);
			siteList.setTable("lookup_asl_rif");
			siteList.buildList(db);
			// siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);

			LookupList regioniList = new LookupList(db, "lookup_regione");
			regioniList.addItem(-1,
					systemStatus.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("regioniList", regioniList);

			LookupList razzaList = new LookupList(db, "lookup_razza");
			razzaList.addItem(-1,
					systemStatus.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("razzaList", razzaList);

			LookupList mantelloList = new LookupList(db, "lookup_mantello");
			mantelloList.addItem(-1,
					systemStatus.getLabel("calendar.none.4dashes"));
			// assetManufacturerList.remove(assetManufacturerList.get("N.D."));
			context.getRequest().setAttribute("mantelloList", mantelloList);

			LookupList statoList = new LookupList(db, "lookup_tipologia_stato");
			context.getRequest().setAttribute("statoList", statoList);

			LookupList esitoControlloList = new LookupList(db,
					"lookup_esito_controlli");
			context.getRequest().setAttribute("esitoControlloList",
					esitoControlloList);

			LookupList esitoControlloLabList = new LookupList(db,
					"lookup_esito_controlli_laboratorio");
			context.getRequest().setAttribute("esitoControlloLabList",
					esitoControlloLabList);

			LookupList specieList = new LookupList();
			specieList.setTable("lookup_specie");
			specieList.buildList(db);
			// specieList.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("specieList", specieList);

			// Lista pratiche contributi
			PraticaList listaP = new PraticaList();
			ArrayList<PraticaDWR> listaPratiche = listaP.getListPratiche(db);
			LookupList praticheContributi = new LookupList(listaPratiche, -1);
			context.getRequest().setAttribute("listaPratiche",
					praticheContributi);

			LookupList veterinari = new LookupList(db, "elenco_veterinari");
			veterinari.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("veterinariList", veterinari);

			// Action di provenienza
			String servletPath = context.getRequest().getServletPath();
			String actionFrom = servletPath.substring(
					servletPath.indexOf("/") + 1, servletPath.indexOf(".do"));
			context.getRequest().setAttribute("actionFrom", actionFrom);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}

		return getReturn(context, "DetailsAnimale");
	}

	public String executeCommandInsert(ActionContext context) throws SQLException {
		if (!hasPermission(context, "anagrafe_canina_registrazioni-add")) {
			return ("PermissionError");
		}
		
		Animale thisAnimale = null;
		Cane thisCane = null;
		Gatto thisGatto = null;
		Furetto thisFuretto = null;
		// Dati comuni a tutti gli eventi

		Evento dettagli_base = (Evento) context.getRequest().getAttribute("Evento");
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		// SE VENGO DA VAM POTREBBE ESSERE VUOTO (SOLO NEL CASO STO RICARICANDO
		// LA PAGINA, AD ESEMPIO DOPO AVER SCELTO PROPRIETARIO
		if (dettagli_base.getIdTipologiaEvento() <= 0) {
			int idTipologiaVam = -1;
			if (context.getRequest().getParameter("idTipologiaEventoVam") != null && !("").equals(context.getRequest().getParameter("idTipologiaEventoVam"))) {
				idTipologiaVam = new Integer(context.getRequest().getParameter("idTipologiaEventoVam"));
				dettagli_base.setIdTipologiaEvento(idTipologiaVam);
				context.getRequest().setAttribute("idTipologiaEvento",Integer.toString(dettagli_base.getIdTipologiaEvento()));
			}
		}
		// Evento evento_to_add = (Evento) context.getFormBean();
		dettagli_base.setEnteredby(getUserId(context));

		Connection db = null;
		try {
			db = this.getConnection(context);
			dettagli_base.setEnteredby(this.getUserId(context));

			thisAnimale = (Animale) context.getRequest().getAttribute("Animale");

			if (thisAnimale == null) {
				thisAnimale = new Animale(db, dettagli_base.getIdAnimale());
			}

			// DATI ANIMALE ATTUALI; PER POPOLARE INFO DI EVENTUALI
			// REGISTRAZIONI E PER VISUALIZZARE INFO CORRETTE
			Animale oldAnimale = new Animale(db, dettagli_base.getIdAnimale());
			if (context.getRequest().getParameter("idProprietario") != null && !context.getRequest().getParameter("idProprietario").equals("")
					&& !("-1").equals(context.getRequest().getParameter("idProprietario"))) {
				Operatore soggettoAdded = new Operatore();
				soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db,new Integer(context.getRequest().getParameter("idProprietario")).intValue());
				
				if((dettagli_base.getIdTipologiaEvento()==EventoCessione.idTipologiaDB || dettagli_base.getIdTipologiaEvento()==EventoAdozioneFuoriAsl.idTipologiaDB) && thisAnimale.getIdAslRiferimento()==((Stabilimento) soggettoAdded.getListaStabilimenti().get(0)).getIdAsl())
				{
					context.getRequest().setAttribute("ErroreWKF", "Registrazione non possibile: selezionare un proprietario con asl diversa da quella dell'animale");
					return executeCommandAdd(context);
				}
				
				

				thisAnimale.setProprietario(soggettoAdded);
//				thisAnimale.setIdProprietario(soggettoAdded.getRappLegale().getIdSoggetto());
			}
			
			

			if (context.getRequest().getParameter("idDetentore") != null && !context.getRequest().getParameter("idDetentore").equals("")
					&& !("-1").equals(context.getRequest().getParameter("idDetentore"))) {

				Operatore soggettoAdded = new Operatore();
				soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db,new Integer(context.getRequest().getParameter("idDetentore")).intValue());

				thisAnimale.setDetentore(soggettoAdded);
				//thisAnimale.setIdDetentore(soggettoAdded.getIdOperatore());
			}

			if (context.getRequest().getParameter("doContinue") != null	&& !context.getRequest().getParameter("doContinue").equals("")
					&& context.getRequest().getParameter("doContinue").equals("false")) {

				if (dettagli_base.getIdTipologiaEvento() > 0){
				Evento registrazione = (Evento) context.getRequest().getAttribute(ApplicationProperties.getProperty(String.valueOf(dettagli_base.getIdTipologiaEvento())));

				context.getRequest().setAttribute(ApplicationProperties.getProperty(String.valueOf(dettagli_base.getIdTipologiaEvento())), registrazione);
				}

				context.getRequest().setAttribute("animale", thisAnimale);
				context.getRequest().setAttribute("oldAnimale", oldAnimale);
				context.getRequest().setAttribute("Evento", dettagli_base);
				context.getRequest().setAttribute("Cane", thisCane);
				context.getRequest().setAttribute("Gatto", thisGatto);
				context.getRequest().setAttribute("Furetto", thisFuretto);
				
				if(dettagli_base.getIdTipologiaEvento()==EventoRilascioPassaporto.idTipologiaDB || dettagli_base.getIdTipologiaEvento()==EventoRilascioPassaporto.idTipologiaRinnovoDB)
				{
					context.getRequest().setAttribute("dataScadenzaAntirabbica", Animale.dataScadenzaAntirabbica(thisAnimale.getMicrochip())); 
				}

				return executeCommandAdd(context);
			}
			// RICONTROLLO POSSIBILITa' di registrazioni per evitare + invii,
			// introduco nuovamente controllo su ASL
			// tasto indietro ecc

			RegistrazioniWKF wk = new RegistrazioniWKF();
			wk.buildWkfDati(context, thisAnimale, hasPermission(context, "anagrafe_canina_registrazioni_pregresse-add"), user, isUgualeAslAnimaleAslUtente(context, thisAnimale), db);
			
			ArrayList reg = wk.getRegistrazioniCodeDaStato(db);

			if (reg.contains(dettagli_base.getIdTipologiaEvento())) {
				int tipologiaRegistrazione = 0;
				thisCane = null;

				/**
				 * Se il wkf è ok controllo se asl utente loggato = asl animale.
				 * (CONTROLLO ELIMINATO CON INSERIMENTO FUORI ASL IN WKF) Solo
				 * in un caso può non essere uguale: quando il cane è detenuto
				 * in un canile dell' asl dell'utente loggato e per tale canile
				 * esiste un progetto di sterilizzazione aperto
				 */


				EventoSterilizzazione sterilizzazione = (EventoSterilizzazione) context.getRequest().getAttribute("EventoSterilizzazione");
				
				if(context.getRequest().getParameterValues("veterinari")!=null)
				{
					String[] veterinari = (String[])context.getRequest().getParameterValues("veterinari");
					if(veterinari.length>0)
						sterilizzazione.setVeterinarioAsl1(Integer.parseInt(veterinari[0]));
					if(veterinari.length>1)
						sterilizzazione.setVeterinarioAsl2(Integer.parseInt(veterinari[1]));
				}
				
				/**
				 * 1 - Detentore se è canile 2 - Se è dell'asl dell'utente
				 * loggato 3 - Se esiste un progetto aperto per il canile *
				 */
				if (sterilizzazione.isFlagContributoRegionale()) {
					Operatore detentore = thisAnimale.getDetentore();
					Stabilimento stab = null;
					LineaProduttiva lineaP = null;
					if (detentore != null && detentore.getIdOperatore() > 0) {
						stab = (Stabilimento) detentore.getListaStabilimenti().get(0);
						lineaP = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
					}

					Pratica praticaContributi = new Pratica(db, sterilizzazione.getIdProgettoSterilizzazioneRichiesto());

					sterilizzazione = (EventoSterilizzazione) context.getRequest().getAttribute("EventoSterilizzazione");
					if (praticaContributi.getIdTipologiaPratica() == Pratica.idPraticaCanile) {
						if (lineaP.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile && stab.getIdAsl() == this.getUserAsl(context)) { // Detentore
							// se è canile e se	 è dell'asl dell'utente loggato
							if (!thisAnimale.checkContributoPerDetentoreCanile(user) && !thisAnimale.checkContributoPerDetentoreCanileStessaAsl(user)) { // SE
								// ESISTE PROGETTO APERTO
								context.getRequest().setAttribute("animale", thisAnimale);
								context.getRequest().setAttribute("oldAnimale", oldAnimale);
								context.getRequest().setAttribute("Evento", dettagli_base);
								context.getRequest().setAttribute("Cane", thisCane);
								context.getRequest().setAttribute("Gatto", thisGatto);
								context.getRequest().setAttribute("Furetto", thisFuretto);
								context.getRequest().setAttribute("ErroreWKF", "Nessun progetto valido per il canile in questione");
								return executeCommandAdd(context);
							} else if (sterilizzazione.getIdProgettoSterilizzazioneRichiesto() < 0) {
								context.getRequest().setAttribute("animale", thisAnimale);
								context.getRequest().setAttribute("oldAnimale", oldAnimale);
								context.getRequest().setAttribute("Evento", dettagli_base);
								context.getRequest().setAttribute("Cane", thisCane);
								context.getRequest().setAttribute("Gatto", thisGatto);
								context.getRequest().setAttribute("Furetto", thisFuretto);
								context.getRequest().setAttribute("ErroreWKF", "Puoi inserire una sterilizzazione per l'animale solo se selezioni il progetto relativo al canile");
								return executeCommandAdd(context);
							}
						} else {
							context.getRequest().setAttribute("animale", thisAnimale);
							context.getRequest().setAttribute("oldAnimale", oldAnimale);
							context.getRequest().setAttribute("Evento", dettagli_base);
							context.getRequest().setAttribute("Cane", thisCane);
							context.getRequest().setAttribute("Gatto", thisGatto);
							context.getRequest().setAttribute("Furetto", thisFuretto);
							context.getRequest().setAttribute("ErroreWKF", "Cane non detenuto in canile o canile di asl non di competenza per l'utente");
							return executeCommandAdd(context);
						}
					}
				}

				Evento registrazioneDaInserire = (Evento) context.getRequest().getAttribute(ApplicationProperties.getProperty(String.valueOf(dettagli_base.getIdTipologiaEvento())));

				// CONTROLLO SE EVENTUALMENTE SI STA FACENDO UN'OPERAZIONE NON CONSENTITA PER IL BLOCCO DEL CANILE IN DATA REGISTRAZIONE 
				boolean controllaSeBloccato=false;
				String esito="";
				Operatore soggettoAdded = new Operatore();
				int idTipologiaDetentore = -1; 
				int id_det = -1;
				String data_registrazione="";
				
				if (registrazioneDaInserire.getIdTipologiaEvento()==EventoCambioDetentore.idTipologiaDB){
					id_det=((EventoCambioDetentore)registrazioneDaInserire).getIdDetentore();
					soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, id_det);
					idTipologiaDetentore = ((LineaProduttiva) ((Stabilimento) soggettoAdded.getListaStabilimenti()
							.get(0)).getListaLineeProduttive().get(0)).getIdRelazioneAttivita();
					if(idTipologiaDetentore==5){ 
						controllaSeBloccato=true;
						data_registrazione=((EventoCambioDetentore)registrazioneDaInserire).getDataModificaDetentore().toString();
					}							
				}else if(registrazioneDaInserire.getIdTipologiaEvento()==EventoCessione.idTipologiaDB){
					id_det=((EventoCessione)registrazioneDaInserire).getIdProprietario(); 
					if(id_det>0)
					{ 
						soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, id_det);
						idTipologiaDetentore = ((LineaProduttiva) ((Stabilimento) soggettoAdded.getListaStabilimenti()
								.get(0)).getListaLineeProduttive().get(0)).getIdRelazioneAttivita();
						if(idTipologiaDetentore==5){ 
							controllaSeBloccato=true;
							data_registrazione=((EventoCessione)registrazioneDaInserire).getDataCessione().toString();
						}
					}
				}else if(registrazioneDaInserire.getIdTipologiaEvento()==EventoPresaInCaricoDaCessione.idTipologiaDB){
					if(((EventoPresaInCaricoDaCessione)registrazioneDaInserire).getIdProprietario()>0){
						id_det=((EventoPresaInCaricoDaCessione)registrazioneDaInserire).getIdProprietario();
						if(id_det>0)
						{
							soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, id_det);
							idTipologiaDetentore = ((LineaProduttiva) ((Stabilimento) soggettoAdded.getListaStabilimenti()
									.get(0)).getListaLineeProduttive().get(0)).getIdRelazioneAttivita();
							if(idTipologiaDetentore==5){ 
								controllaSeBloccato=true;
								data_registrazione=((EventoPresaInCaricoDaCessione)registrazioneDaInserire).getDataPresaCessione().toString();
							}	
						}
					}
				}else if(registrazioneDaInserire.getIdTipologiaEvento()==EventoTrasferimento.idTipologiaDB){
					id_det=((EventoTrasferimento)registrazioneDaInserire).getIdProprietario();
					soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, id_det);
					idTipologiaDetentore = ((LineaProduttiva) ((Stabilimento) soggettoAdded.getListaStabilimenti()
							.get(0)).getListaLineeProduttive().get(0)).getIdRelazioneAttivita();
					if(idTipologiaDetentore==5){ 
						controllaSeBloccato=true;
						data_registrazione=((EventoTrasferimento)registrazioneDaInserire).getDataTrasferimento().toString();
					}							
				}else if(registrazioneDaInserire.getIdTipologiaEvento()==EventoTrasferimentoCanile.idTipologiaDB){
					id_det=((EventoTrasferimentoCanile)registrazioneDaInserire).getIdDetentore();
					soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, id_det);
					idTipologiaDetentore = ((LineaProduttiva) ((Stabilimento) soggettoAdded.getListaStabilimenti()
							.get(0)).getListaLineeProduttive().get(0)).getIdRelazioneAttivita();
					if(idTipologiaDetentore==5){ 
						controllaSeBloccato=true;
						data_registrazione=((EventoTrasferimentoCanile)registrazioneDaInserire).getDataTrasferimentoCanile().toString();
					}							
				}else if(registrazioneDaInserire.getIdTipologiaEvento()==EventoRitrovamento.idTipologiaDB){
					if(id_det>0)
					{
						id_det=((EventoRitrovamento)registrazioneDaInserire).getIdDetentore();
						soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, id_det);
						idTipologiaDetentore = ((LineaProduttiva) ((Stabilimento) soggettoAdded.getListaStabilimenti()
								.get(0)).getListaLineeProduttive().get(0)).getIdRelazioneAttivita();
						if(idTipologiaDetentore==5){ 
							controllaSeBloccato=true;
							data_registrazione=((EventoRitrovamento)registrazioneDaInserire).getDataRitrovamento().toString();
						}
					}
				}else if(registrazioneDaInserire.getIdTipologiaEvento()==EventoRitrovamentoNonDenunciato.idTipologiaDB){
					if(id_det>0)
					{
						id_det=((EventoRitrovamentoNonDenunciato)registrazioneDaInserire).getIdDetentore();
						soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, id_det);
						idTipologiaDetentore = ((LineaProduttiva) ((Stabilimento) soggettoAdded.getListaStabilimenti()
								.get(0)).getListaLineeProduttive().get(0)).getIdRelazioneAttivita();
						if(idTipologiaDetentore==5){ 
							controllaSeBloccato=true;
							data_registrazione=((EventoRitrovamentoNonDenunciato)registrazioneDaInserire).getDataRitrovamentoNd().toString();
						}
					}
				}else if(registrazioneDaInserire.getIdTipologiaEvento()==24){//EventoCattura.idTipologiaDB){
					id_det=((EventoCattura)registrazioneDaInserire).getIdDetentore();
					soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, id_det);
					idTipologiaDetentore = ((LineaProduttiva) ((Stabilimento) soggettoAdded.getListaStabilimenti()
							.get(0)).getListaLineeProduttive().get(0)).getIdRelazioneAttivita();
					if(idTipologiaDetentore==5){ 
						controllaSeBloccato=true;
						data_registrazione=((EventoCattura)registrazioneDaInserire).getDataCattura().toString();
					}							
				}else if(registrazioneDaInserire.getIdTipologiaEvento()==61){//EventoAdozioneDaCanile.idTipologiaDB){
					id_det=((EventoAdozioneDaCanile)registrazioneDaInserire).getIdProprietario();
					soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, id_det);
					idTipologiaDetentore = ((LineaProduttiva) ((Stabilimento) soggettoAdded.getListaStabilimenti()
							.get(0)).getListaLineeProduttive().get(0)).getIdRelazioneAttivita();
					if(idTipologiaDetentore==5){ 
						controllaSeBloccato=true;
						data_registrazione=((EventoAdozioneDaCanile)registrazioneDaInserire).getDataAdozione().toString();
					}							
				}
				
				
				if(controllaSeBloccato){
					esito=soggettoAdded.controlloRegistrazioneInCanileBloccato(id_det, db,data_registrazione);
					boolean occupazione = true;
					// CONTROLLO ANCHE SE IL CANILE E' PIENO
					if(esito.equals("") )
					{
						String dataNascitaString = null;
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						if(thisAnimale.getDataNascita()!=null)
							dataNascitaString = sdf.format(thisAnimale.getDataNascita());
						if(oldAnimale.getIdDetentore()<0 || id_det!=oldAnimale.getIdDetentore())
							occupazione =  Stabilimento.checkOccupazione(id_det, thisAnimale.getIdTaglia(), dataNascitaString);
					}
					
					if(!esito.equals("") || !occupazione )
					{
						context.getRequest().setAttribute("animale", thisAnimale);
						context.getRequest().setAttribute("oldAnimale", oldAnimale);
						context.getRequest().setAttribute("Evento", dettagli_base);
						context.getRequest().setAttribute("Cane", thisCane);
						context.getRequest().setAttribute("Gatto", thisGatto);
						context.getRequest().setAttribute("Furetto", thisFuretto);
						context.getRequest().setAttribute("ErrorBlocco", !esito.equals("") ? esito : "ATTENZIONE! Operazione non consentita poichè il canile risulta essere pieno.");
						return executeCommandAdd(context);
					}
					
				}
				
				
				



				
				if (dettagli_base.getIdTipologiaEvento() == EventoSterilizzazione.idTipologiaDB) {

					// CONTROLLI PRATICA CONTRIBUTI

					boolean praticaSceltaOK = true;
					if (sterilizzazione.getIdProgettoSterilizzazioneRichiesto() > 0) {
						boolean catturato = false;
						boolean contributoDisponibile = false;
						if (thisAnimale.isRandagio(db)) {
							catturato = true;
						}
						contributoDisponibile = Animale.checkContributo(db, thisAnimale.getMicrochip());
						if (contributoDisponibile) {
							// thisAsset.setId_pratica_contributi(id_pratica);
							Pratica praticaContributi = new Pratica(db, sterilizzazione.getIdProgettoSterilizzazioneRichiesto());
							Stabilimento stab = (Stabilimento) thisAnimale.getProprietario().getListaStabilimenti().get(0);
							LineaProduttiva lineaP = (LineaProduttiva) stab.getListaLineeProduttive().get(0);

							int id_comune_proprietario = stab.getSedeOperativa().getComune();
							if ((catturato)) {
								// int trovato=praticaContributi.controlli_pratica(db, id_pratica,thisAsset.getComuneCattura(),getUserAsl(context));
								if (thisAnimale.getIdSpecie() == Cane.idSpecie && praticaContributi.getCaniRestantiCatturati() <= 0) {
									praticaSceltaOK = false;
									context.getRequest().setAttribute("praticaError", "Per il progetto scelto non ci sono posti disponibili per i cani catturati");
								} else if (thisAnimale.getIdSpecie() == Gatto.idSpecie	&& praticaContributi.getGattiRestantiCatturati() <= 0) {
									praticaSceltaOK = false;
									context.getRequest().setAttribute("praticaError", "Per il progetto scelto non ci sono posti disponibili per i gatti catturati");
								} else if (id_comune_proprietario > 0 && !praticaContributi.getComuniElenco().contains(id_comune_proprietario)) {
									Stabilimento stabDet = (Stabilimento) thisAnimale.getDetentore().getListaStabilimenti().get(0);
									LineaProduttiva lineaDet = (LineaProduttiva) stabDet.getListaLineeProduttive().get(0);
									if (!praticaContributi.getCaniliElenco().contains(lineaDet.getId())) {
										praticaSceltaOK = false;
										context.getRequest().setAttribute("praticaError", "Il comune di cattura non corrisponde con quelli del progetto");
									}
								}
							} else {
								// recupero il comune del proprietario
								stab = (Stabilimento) thisAnimale.getDetentore().getListaStabilimenti().get(0);
								lineaP = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
								if (thisAnimale.getIdSpecie() == Cane.idSpecie	&& praticaContributi.getCaniRestantiPadronali() <= 0) {
									praticaSceltaOK = false;
									context.getRequest().setAttribute("praticaError", "Per il progetto scelto non ci sono posti disponibili per i cani padronali");
								} else if (thisAnimale.getIdSpecie() == Gatto.idSpecie	&& praticaContributi.getGattiRestantiPadronali() <= 0) {
									praticaSceltaOK = false;
									context.getRequest().setAttribute("praticaError", "Per il progetto scelto non ci sono posti disponibili per i gatti padronali");
								} else if (!praticaContributi.getComuniElenco().contains(id_comune_proprietario)
										&& !praticaContributi.getCaniliElenco()	.contains(lineaP.getId())) {
									praticaSceltaOK = false;
									context.getRequest().setAttribute("praticaError", "Il comune del proprietario non corrisponde con quelli del progetto");
								}
							}
						}
					}
					if (!praticaSceltaOK) {
						context.getRequest().setAttribute("EventoSterilizzazione", sterilizzazione);
						context.getRequest().setAttribute("animale", thisAnimale);
						context.getRequest().setAttribute("oldAnimale", oldAnimale);
						context.getRequest().setAttribute("Evento", dettagli_base);
						context.getRequest().setAttribute("Cane", thisCane);
						context.getRequest().setAttribute("Gatto", thisGatto);
						context.getRequest().setAttribute("Furetto", thisFuretto);
						return executeCommandAdd(context);
					}
				}
				
				
				
				if (dettagli_base.getIdTipologiaEvento() == EventoMorsicatura.idTipologiaDB) 
				{
					if(context.getRequest().getParameterValues("veterinariMorsicatura")!=null)
					{
						String[] veterinari = (String[])context.getRequest().getParameterValues("veterinariMorsicatura");
						if(veterinari.length>0)
						{
							EventoMorsicatura morsicatura = (EventoMorsicatura)registrazioneDaInserire;
							morsicatura.setVeterinari(veterinari);
							registrazioneDaInserire=morsicatura;
						}
							
					}
				} 
				else if (dettagli_base.getIdTipologiaEvento() == EventoAggressione.idTipologiaDB) 
				{
					if(context.getRequest().getParameterValues("veterinariAggressione")!=null)
					{
						String[] veterinari = (String[])context.getRequest().getParameterValues("veterinariAggressione");
						if(veterinari.length>0)
						{
							EventoAggressione aggressione = (EventoAggressione)registrazioneDaInserire;
							aggressione.setVeterinari(veterinari);
							registrazioneDaInserire=aggressione;
						}
							
					}
				}
				
				/*INIZIO TRANSAZIONE*/
				if (db.getAutoCommit()==true)
					db.setAutoCommit(false);
				 
				if(registrazioneDaInserire.getIdTipologiaEvento()==EventoMorsicatura.idTipologiaDB)
					registrazioneDaInserire = registrazioneDaInserire.salvaRegistrazione(this.getUserId(context), this.getUserRole(context), this.getUserAsl(context), thisAnimale, db,context);
				else
					registrazioneDaInserire = registrazioneDaInserire.salvaRegistrazione(this.getUserId(context), this.getUserRole(context), this.getUserAsl(context), thisAnimale, db);
				
				if(ApplicationProperties.getProperty("abilitato_sinaaf").equals("true"))
				{
					String esitoInvioSinaaf[] = new Sinaaf().inviaInSinaaf(db, getUserId(context),registrazioneDaInserire.getIdEvento()+"", "evento");
					context.getRequest().setAttribute("messaggio", esitoInvioSinaaf[0]);
					context.getRequest().setAttribute("errore", esitoInvioSinaaf[1]);
				}
				
				if(registrazioneDaInserire.getIdTipologiaEvento()==EventoMorsicatura.idTipologiaDB || registrazioneDaInserire.getIdTipologiaEvento()==EventoAggressione.idTipologiaDB)
				{
					if(registrazioneDaInserire.contenutaInRegistroCaniAggressivi(db))
						context.getRequest().setAttribute("registrazionecontenutaInRegistroCaniAggressivi","true"); 
					
				}
				context.getRequest().setAttribute("tipologiaEvento", String.valueOf(registrazioneDaInserire.getIdTipologiaEvento()));
				context.getRequest().setAttribute("id", String.valueOf(registrazioneDaInserire.getIdEvento()));
				
				if (db.getAutoCommit()==false)
					db.commit();
				// switch (dettagli_base.getIdTipologiaEvento()) {}
			} else {
				context.getRequest().setAttribute("ErroreWKF", "Registrazione non possibile");
				return executeCommandAdd(context);
			}

		} catch (CanilePienoException e){
			context.getRequest().setAttribute("animale", thisAnimale);
			context.getRequest().setAttribute("Evento", dettagli_base);
			context.getRequest().setAttribute("Cane", thisCane);
			context.getRequest().setAttribute("Gatto", thisGatto);
			context.getRequest().setAttribute("Furetto", thisFuretto);
			context.getRequest().setAttribute("ErroreWKF", e.getMessage());
			if (db.getAutoCommit()==false)
				db.rollback();
			return executeCommandAdd(context);
		}catch (Exception e) {
			//db.rollback();
			if (db.getAutoCommit()==false)
				db.rollback();
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e.getMessage());
			return ("SystemError");
		}finally {
			if (db.getAutoCommit()==false)
				db.setAutoCommit(true);
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("animaleId", context.getRequest().getParameter("idAnimale"));
		context.getRequest().setAttribute("idSpecie", context.getRequest().getParameter("specieAnimaleId"));
		// SE VENGO DA VAM VADO AL DETTAGLIO REGISTRAZIONE
		if (context.getSession().getAttribute("caller") != null && !("").equals(context.getSession().getAttribute("caller")) 
				&& (ApplicationProperties.getProperty("VAM_ID")).equals(context.getSession().getAttribute("caller"))) {
			return "InsertFromVAMOK";
		}
		return getReturn(context, "Insert");
		// return ("InsertOK");
	}

	public String executeCommandDetails(ActionContext context) {
		if (!hasPermission(context, "anagrafe_canina_lista_registrazioni-view")) {

			return ("PermissionError");

		}

		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;

		try {

			db = this.getConnection(context);
			String idTipologiaRegistrazione = (String) context.getRequest()
					.getParameter("tipologiaEvento");
			if (idTipologiaRegistrazione == null)
				idTipologiaRegistrazione = (String) context.getRequest()
						.getAttribute("tipologiaEvento");
			int idTipologiaRegistrazioneInt = new Integer(
					idTipologiaRegistrazione).intValue();
			String idEvento = (String) context.getRequest().getParameter("id");
			if (idEvento == null)
				idEvento = (String) context.getRequest().getAttribute("id");
			int idEventoInt = new Integer(idEvento).intValue();

			Evento thisEvento = Evento.getEventoFromTipologiaId(db,
					idTipologiaRegistrazioneInt, idEventoInt);
			context.getRequest().setAttribute("evento", thisEvento);

			LookupList registrazioniList = new LookupList(db,
					"lookup_tipologia_registrazione");
			registrazioniList.addItem(-1, "--Tutte le registrazioni--");
			context.getRequest().setAttribute("registrazioniList",
					registrazioniList);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "--Nessuna asl--");
			context.getRequest().setAttribute("AslList", siteList);

			LookupList causeDecessoList = new LookupList(db,
					"lookup_tipologia_decesso");
			context.getRequest().setAttribute("causeDecessoList",
					causeDecessoList);

			LookupList tipoSoggettoSterilizz = new LookupList(db,
					"lookup_tipologia_soggetto_sterilizzante");
			context.getRequest().setAttribute("tipoSoggettoSterilizz",
					tipoSoggettoSterilizz);

			LookupList tipoVaccinazione = new LookupList(db,
					"lookup_tipologia_vaccino");
			context.getRequest().setAttribute("tipoVaccinazione",
					tipoVaccinazione);
			
			LookupList tipoVaccinoInoculato = new LookupList(db,
					"lookup_tipologia_vaccino_inoculato");
			context.getRequest().setAttribute("tipoVaccinoInoculato",
					tipoVaccinoInoculato);

			LookupList continentiList = new LookupList(db, "lookup_continenti");
			context.getRequest().setAttribute("continentiList", continentiList);
			
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
			
			LookupList categorieVittimaAggressione = new LookupList(db,"lookup_categoria_vittima_aggressione");
			context.getRequest().setAttribute("categorieVittimaAggressione", categorieVittimaAggressione);
			
			LookupList taglieVittima = new LookupList(db,"lookup_taglia_vittima");
			context.getRequest().setAttribute("taglieVittima", taglieVittima);
			
			
			if(thisEvento.getIdTipologiaEvento()==EventoMorsicatura.idTipologiaDB && ((EventoMorsicatura)thisEvento).getIdSchedaMorsicatura()>0)
			{
				SchedaMorsicatura scheda = new SchedaMorsicatura();
				scheda = scheda.getById(db, ((EventoMorsicatura)thisEvento).getIdSchedaMorsicatura());
				SchedaMorsicaturaRecords records = new SchedaMorsicaturaRecords();
				scheda.setRecords(records.getByIdScheda(db, scheda.getId()));
				context.getRequest().setAttribute("scheda", scheda);
			}
			
			PraticaList c = new PraticaList();
			ArrayList<PraticaDWR> listaPratiche = c.getListPratiche(db);
			LookupList praticheContributi = new LookupList(listaPratiche, -1);
			context.getRequest().setAttribute("listaPratiche",
					praticheContributi);

			LookupList provinceList = new LookupList(db, "lookup_province");
			context.getRequest().setAttribute("provinceList", provinceList);

			ComuniAnagrafica com = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = com.buildList(db, -1, -1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			// comuniList.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("comuniList", comuniList);

			LookupList nazioniList = new LookupList(db,	"lookup_nazioni");
			nazioniList.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("nazioniList", nazioniList);
			
			LookupList regioniList = new LookupList(db, "lookup_regione");
			regioniList.addItem(-1,
					systemStatus.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("regioniList", regioniList);

			// Lookup esito
			LookupList esitoList = new LookupList(db, "lookup_esito_controlli");
			esitoList.addItem(-1,
					systemStatus.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("esitoList", esitoList);

			// Lookup esito controlli documentali
			LookupList esitoDocumentaliList = new LookupList(db,
					"lookup_esito_controlli");
			esitoList.addItem(-1,
					systemStatus.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("esitoDocumentaliList",
					esitoDocumentaliList);

			// Lookup esito controlli laboratorio
			LookupList esitoLabList = new LookupList(db,
					"lookup_esito_controlli_laboratorio");
			esitoList.addItem(-1,
					systemStatus.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("esitoLabList", esitoLabList);

			// Lookup esito controlli documentali
			LookupList controlliCommercialiDecisione = new LookupList(db,
					"lookup_decisione_esito_controllo_commerciale");
			esitoList.addItem(-1,
					systemStatus.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("controlliCommercialiDecisione",
					controlliCommercialiDecisione);

			// Lookup veterinari sterilizzanti
			LookupList veterinariList = new LookupList(db, "elenco_veterinari");
			esitoList.addItem(-1,
					systemStatus.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("veterinariList", veterinariList);
			
			LookupList veterinariAslList = new LookupList(db, "elenco_veterinari_asl_with_group_asl");
			context.getRequest().setAttribute("veterinariAslList", veterinariAslList);

			// Lookup veterinari chippatori
			LookupList veterinariChippatoriList = new LookupList(db,
					"elenco_veterinari_chippatori_with_asl_select_grouping");
			esitoList.addItem(-1,
					systemStatus.getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("veterinariChippatoriList",
					veterinariChippatoriList);

			LookupList statoList = new LookupList(db, "lookup_tipologia_stato");
			context.getRequest().setAttribute("statoList", statoList);
			
			Municipalita mun = new Municipalita();
			ArrayList<Municipalita> listaMun = mun.getListaByIdComune(db, -1);
			LookupList municipalitaList = new LookupList(listaMun, -1);
			// comuniList.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("municipalitaList", municipalitaList);
			
			LookupList listaMedicoEsecutore = new LookupList(db, "lookup_medico_esecutore");
			context.getRequest().setAttribute("listaMedicoEsecutore", listaMedicoEsecutore);
			
			LookupList listaInterventoEseguito = new LookupList(db, "lookup_intervento_eseguito");
			context.getRequest().setAttribute("listaInterventoEseguito", listaInterventoEseguito);
			
			LookupList listaCausale = new LookupList(db, "lookup_causale");
			context.getRequest().setAttribute("listaCausale", listaCausale);
			
			if(ApplicationProperties.getProperty("abilitato_sinaaf").equals("true"))
			{
				context.getRequest().setAttribute("ws", new WsPost(db,idEvento,"evento",ApplicationProperties.getProperty("END_POINT_SINAAF")));
			
				if(context.getRequest().getParameter("errore")!=null && !context.getRequest().getParameter("errore").equals("") && !context.getRequest().getParameter("errore").equals("null"))
					context.getRequest().setAttribute("Error", context.getRequest().getParameter("errore"));
				if(context.getRequest().getParameter("messaggio")!=null && !context.getRequest().getParameter("messaggio").equals("") && !context.getRequest().getParameter("messaggio").equals("null"))
					context.getRequest().setAttribute("messaggio", context.getRequest().getParameter("messaggio"));
			}
			

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		if (context.getSession().getAttribute("caller") != null && !("").equals(context.getSession().getAttribute("caller")) 
				&& (ApplicationProperties.getProperty("VAM_ID")).equals(context.getSession().getAttribute("caller"))) 
		{

			return "DetailsNONAVOK";
		}

		return getReturn(context, "Details");

		// return ("DetailsOK");
		
		
		
		
	}

	public String executeCommandSearchForm(ActionContext context) {

		if (!hasPermission(context, "anagrafe_canina_registrazioni-view")) {

			return ("PermissionError");

		}

		// Bypass search form for portal users
		if (isPortalUser(context)) {
			// return (executeCommandSearch(context));
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = getConnection(context);

			this.deletePagedListInfo(context, "EventiListInfo");
			PagedListInfo eventiListInfo = this.getPagedListInfo(context,
					"EventiListInfo");
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

			annoList.addItem(annoCorrenteInt - 3,
					String.valueOf(annoCorrenteInt - 3));
			annoList.addItem(annoCorrenteInt - 2,
					String.valueOf(annoCorrenteInt - 2));
			annoList.addItem(annoCorrenteInt - 1,
					String.valueOf(annoCorrenteInt - 1));
			annoList.addItem(annoCorrenteInt, annoCorrente);
			annoList.addItem(-1, "Tutti");

			context.getRequest().setAttribute("annoList", annoList);

			Calendar now = Calendar.getInstance();
			int year = (now.get(Calendar.YEAR));
			String anno = String.valueOf(year);

			context.getRequest().setAttribute("daDefault", "01/01/" + anno);

			context.getRequest().setAttribute("aDefault", "31/12/" + anno);
			
			
			

			// buildFormElements(context, db);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Search Evento", "Evento Search");
		return ("SearchOK");

	}

	// public String executeCommandSearchForm12(ActionContext context){
	// UserBean user = (UserBean) context.getSession().getAttribute("User");
	// String ip = context.getIpAddress();
	// String username = user.getUserRecord().getUsername();
	// context.getRequest().setAttribute("currentUser", username);
	// int ruolo = user.getRoleId();
	// if (ruolo!=VETERINARIO_PRIVATO) //SOLO VETERINARIO PRIVATO
	// return ("PermissionError");
	//
	// // Bypass search form for portal users
	// if (isPortalUser(context)) {
	// // return (executeCommandSearch(context));
	// }
	// SystemStatus systemStatus = this.getSystemStatus(context);
	// Connection db = null;
	// try {
	// db = getConnection(context);
	//
	// PagedListInfo eventiListInfo = this.getPagedListInfo(context,
	// "eventiListInfo");
	// eventiListInfo.setCurrentLetter("");
	// eventiListInfo.setCurrentOffset(0);
	//
	// LookupList siteList = new LookupList(db, "lookup_asl_rif");
	// siteList.addItem(-1, "-- SELEZIONA VOCE --");
	// context.getRequest().setAttribute("AslList", siteList);
	//
	// LookupList specieList = new LookupList(db, "lookup_specie");
	// specieList.addItem(-1, "--Tutti--");
	// context.getRequest().setAttribute("specieList", specieList);
	//
	// LookupList registrazioniList = new LookupList(db,
	// "lookup_tipologia_registrazione");
	// registrazioniList.addItem(-1, "--Tutte le registrazioni--");
	// context.getRequest().setAttribute("registrazioniList",
	// registrazioniList);
	//
	// Calendar cal = new GregorianCalendar();
	// int annoCorrenteInt = cal.get(Calendar.YEAR);
	// String annoCorrente = new Integer(annoCorrenteInt).toString();
	// // int annoCorrenteInt = Integer.parseInt(annoCorrente);
	// LookupList annoList = new LookupList(); // creo una nuova lookup
	//
	// annoList.addItem(annoCorrenteInt - 3, String
	// .valueOf(annoCorrenteInt - 3));
	// annoList.addItem(annoCorrenteInt - 2, String
	// .valueOf(annoCorrenteInt - 2));
	// annoList.addItem(annoCorrenteInt - 1, String
	// .valueOf(annoCorrenteInt - 1));
	// annoList.addItem(annoCorrenteInt, annoCorrente);
	// annoList.addItem(-1, "Tutti");
	//
	// context.getRequest().setAttribute("annoList", annoList);
	//
	// // buildFormElements(context, db);
	// } catch (Exception e) {
	// context.getRequest().setAttribute("Error", e);
	// return ("SystemError");
	// } finally {
	// this.freeConnection(context, db);
	// }
	// addModuleBean(context, "Search Evento", "Evento Search");
	//
	//
	//
	// return ("SearchForm12OK");
	// }

	public String executeCommandSearch(ActionContext context) {
		if (!hasPermission(context, "anagrafe_canina_registrazioni-view")) {
			if (!hasPermission(context, "anagrafe_canina_registrazioni-view")) {
				return ("PermissionError");
			}
		}
		
		PagedListInfo EventiListInfo = this.getPagedListInfo(context,
				"EventiListInfo");
		String servletPath = context.getRequest().getServletPath();
		String userAction = servletPath.substring(servletPath.indexOf("/") + 1,
				servletPath.indexOf(".do"));
		EventiListInfo.setLink(userAction + ".do?command=Search");
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = this.getConnection(context);
			EventoList listaEventi = new EventoList();
			listaEventi.setPagedListInfo(EventiListInfo);
			listaEventi.setPagedListInfo(EventiListInfo);
			listaEventi.setMinerOnly(false);
			listaEventi.setTypeId(EventiListInfo.getFilterKey("listFilter1"));

			if (context.getRequest().getParameter("searchexacttype") != null
					|| EventiListInfo.getSearchOptionValue("searchexacttype") != null) {

				String inOutCessioni = (context.getRequest().getParameter(
						"searchexacttype") != null) ? context.getRequest()
						.getParameter("searchexacttype") : EventiListInfo
						.getSearchOptionValue("searchexacttype");
				if (("out").equals(inOutCessioni)) {
					listaEventi
							.setId_asl_vecchio_proprietario(getUserAsl(context));

				} else if (("in").equals(inOutCessioni)) {
					listaEventi
							.setId_asl_nuovo_proprietario(getUserAsl(context));
				}

			}

			if (context.getRequest().getParameter("searchexactstato") != null
					|| EventiListInfo.getSearchOptionValue("searchexactstato") != null) {
				String stato = (context.getRequest().getParameter(
						"searchexactstato") != null) ? context.getRequest()
						.getParameter("searchexactstato") : EventiListInfo
						.getSearchOptionValue("searchexactstato");
				if (("opened").equals(stato)) {
					listaEventi.setGet_only_opened(true);
				}
			}

			listaEventi.setStageId(EventiListInfo
					.getCriteriaValue("searchcodeStageId"));
			EventiListInfo.setSearchCriteria(listaEventi, context);
			
			listaEventi.setPagedListInfo(EventiListInfo);

			listaEventi.buildList(db);

			context.getRequest().setAttribute("listaEventi", listaEventi);

			LookupList registrazioniList = new LookupList(db,
					"lookup_tipologia_registrazione");
			registrazioniList.addItem(-1, "--Tutte le registrazioni--");
			context.getRequest().setAttribute("registrazioniList",
					registrazioniList);

			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			// Action di provenienza
			// String servletPath = context.getRequest().getServletPath();
			String actionFrom = servletPath.substring(
					servletPath.indexOf("/") + 1, servletPath.indexOf(".do"));
			context.getRequest().setAttribute("actionFrom", actionFrom);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return ("ListOK");
	}

	// public String executeCommandSearch12(ActionContext context) {
	//
	//
	// PagedListInfo EventiListInfo = this.getPagedListInfo(context,
	// "EventiListInfo");
	// EventiListInfo.setLink("RegistrazioniAnimale.do?command=Search12");
	// SystemStatus systemStatus = this.getSystemStatus(context);
	// Connection db = null;
	// try {
	// db = this.getConnection(context);
	// EventoList listaEventi = new EventoList();
	// listaEventi.setPagedListInfo(EventiListInfo);
	// listaEventi.setPagedListInfo(EventiListInfo);
	// listaEventi.setMinerOnly(false);
	// listaEventi.setTypeId(EventiListInfo.getFilterKey("listFilter1"));
	//
	// if (context.getRequest().getParameter("type") != null) {
	//
	// if (("out").equals((String) context.getRequest().getParameter(
	// "type"))) {
	// listaEventi
	// .setId_asl_vecchio_proprietario(getUserAsl(context));
	// } else if (("in").equals((String) context.getRequest()
	// .getParameter("type"))) {
	// listaEventi
	// .setId_asl_nuovo_proprietario(getUserAsl(context));
	// }
	// }
	//
	// if (context.getRequest().getParameter("stato") != null
	// && ("opened").equals((String) context.getRequest()
	// .getParameter("stato"))) {
	// listaEventi.setGet_only_opened(true);
	// }
	//
	// listaEventi.setStageId(EventiListInfo
	// .getCriteriaValue("searchcodeStageId"));
	// EventiListInfo.setSearchCriteria(listaEventi, context);
	//
	// if (listaEventi.getEventoda()!=null)
	// context.getRequest().setAttribute("da",
	// listaEventi.getEventoda().toString());
	// if (listaEventi.getEventoa()!=null)
	// context.getRequest().setAttribute("a",
	// listaEventi.getEventoa().toString());
	//
	// UserBean user = (UserBean) context.getSession().getAttribute("User");
	// String ip = context.getIpAddress();
	// String username = user.getUserRecord().getUsername();
	// int user_id = user.getUserId();
	// context.getRequest().setAttribute("currentUser", username);
	//
	// /*String dataDa =
	// context.getRequest().getParameter("searchtimestampeventoda");
	// String dataA =
	// context.getRequest().getParameter("searchtimestampeventoa");
	//
	// DateFormat sdf = new SimpleDateFormat("dd/MM/yy");
	// Date date = sdf.parse(dataDa);
	// Timestamp timeStampDataDa = new Timestamp(date.getTime());
	//
	// date = sdf.parse(dataA);
	// Timestamp timeStampDataA = new Timestamp(date.getTime());*/
	//
	// listaEventi.setIdTipologiaEvento(36); //SOLO VACCINAZIONI
	// listaEventi.setTipoVaccinazione(1); //SOLO ANTIRABBIA
	// listaEventi.setId_utente_inserimento(user_id); //SOLO UTENTE CORRENTE
	// listaEventi.buildList(db);
	//
	// //Genero lista animali associata agli eventi
	// AnimaleList animaleList = new AnimaleList();
	// Iterator j = listaEventi.iterator();
	// if ( j.hasNext() ) {
	// int i =0;
	// while (j.hasNext()) {
	// i++;
	// Evento thisEvento = (Evento)j.next();
	// Animale thisAnimale = new Animale(db, thisEvento.getIdAnimale());
	// animaleList.add(thisAnimale);
	// }}
	// context.getRequest().setAttribute("animaleList", animaleList);
	// context.getRequest().setAttribute("listaEventi", listaEventi);
	//
	// LookupList registrazioniList = new LookupList(db,
	// "lookup_tipologia_registrazione");
	// registrazioniList.addItem(-1, "--Tutte le registrazioni--");
	// context.getRequest().setAttribute("registrazioniList",
	// registrazioniList);
	//
	// LookupList siteList = new LookupList(db, "lookup_asl_rif");
	// siteList.addItem(-1, "-- SELEZIONA VOCE --");
	// context.getRequest().setAttribute("AslList", siteList);
	//
	// LookupList razzaList = new LookupList(db, "lookup_razza");
	// context.getRequest().setAttribute("razzaList", razzaList);
	//
	// } catch (Exception e) {
	// context.getRequest().setAttribute("Error", e);
	// return ("SystemError");
	// } finally {
	// this.freeConnection(context, db);
	// }
	//
	//
	// if ((String) context.getRequest().getParameter("cert") != null
	// && !("").equals((String) context.getRequest().getParameter("cert")) &&
	// ("true").equals((String) context.getRequest().getParameter("cert"))){
	// ApplicationPrefs prefs = (ApplicationPrefs)
	// context.getServletContext().getAttribute("applicationPrefs");
	// String HOST_CORRENTE=
	// ApplicationProperties.getProperty("APP_HOST_CANINA");
	// String SERVER_BDU = HOST_CORRENTE;String APPLICATION_PORT =
	// prefs.get("APPLICATION.PORT");
	// String APPLICATION_NAME = prefs.get("APPLICATION.NAME");
	// String url =
	// "http://".concat(SERVER_BDU).concat(":").concat(APPLICATION_PORT).concat("/").concat(APPLICATION_NAME).concat("/");
	// context.getRequest().setAttribute("SERVER_BDU", url);
	// return getReturn(context, "Mod12");
	// }
	// return ("List12OK");
	// }

	// public String executeCommandListaMailASL(ActionContext context){
	//
	// String mail_ce="mailtemp@mail.it";
	// String mail_na1="mailtemp@mail.it";
	// String mail_na2="mailtemp@mail.it";
	// String mail_na3="mailtemp@mail.it";
	// String mail_av="mailtemp@mail.it";
	// String mail_bn="mailtemp@mail.it";
	// String mail_sa="mailtemp@mail.it";
	// context.getRequest().setAttribute("mail_ce", mail_ce);
	// context.getRequest().setAttribute("mail_na1", mail_na1);
	// context.getRequest().setAttribute("mail_na2", mail_na2);
	// context.getRequest().setAttribute("mail_na3", mail_na3);
	// context.getRequest().setAttribute("mail_av", mail_av);
	// context.getRequest().setAttribute("mail_bn", mail_bn);
	// context.getRequest().setAttribute("mail_sa", mail_sa);
	// return ("ListaMailASLOk");
	//
	// }
	public String executeCommandPresaCessioneAutomatica(ActionContext context) {
		if (!hasPermission(context, "anagrafe_canina_registrazioni-add")) {
			if (!hasPermission(context, "anagrafe_canina_registrazioni-add")) {
				return ("PermissionError");
			}
		}

		Connection db = null;
		try {

			db = this.getConnection(context);
			String[] en = context.getRequest().getParameterValues("toImport");

			for (int i = 0; i < en.length; i++) {
				EventoCessione cessioneToImport = new EventoCessione(db,
						new Integer(en[i]));
				EventoPresaInCaricoDaCessione carico = new EventoPresaInCaricoDaCessione();
				carico.setEnteredby(this.getUserId(context));
				carico.setIdAslRiferimento(this.getUserAsl(context));
				Animale thisAnimale = null;
				Operatore opToInsert = new Operatore();

				// Controllo se ho settato un proprietario già anagrafato
				if (cessioneToImport.getIdProprietario() > 0) {
					opToInsert.queryRecordOperatorebyIdLineaProduttiva(db,
							cessioneToImport.getIdProprietario());
					if (opToInsert.getIdOperatore() > 0) // ESISTE DAVVERO,
															// SAREBBE ANCHE
															// INUTILE QSTO
															// CONTROLLO
						carico.setIdProprietario(cessioneToImport
								.getIdProprietario());
				} else {

					opToInsert.setCodFiscale(cessioneToImport
							.getCodiceFiscale());
					opToInsert.setPartitaIva(cessioneToImport
							.getCodiceFiscale());

					boolean exist = opToInsert.checkEsistenzaLineaProduttiva(
							db, LineaProduttiva.idAttivitaPrivato);

					if (exist) {
						// Recupero l'operatore
						Operatore op = new Operatore();
						op.setCodFiscale(cessioneToImport.getCodiceFiscale());
						op.setPartitaIva(cessioneToImport.getCodiceFiscale());
						int idRelazione = op
								.getIdLineaProduttivaIfExists(db, 1);
						carico.setIdProprietario(idRelazione);

					} else {
						EsitoControllo esito = opToInsert
								.importProprietarioPrivatoDaEventoCessione(
										cessioneToImport.getIdAnimale(),
										this.getUserId(context));
						if (esito.getIdEsito() > 0) {
							carico.setIdProprietario(esito.getIdEsito());
						}
					}
				}

				if (carico.getIdProprietario() > 0) {

					carico.setSpecieAnimaleId(cessioneToImport
							.getSpecieAnimaleId());
					carico.setIdTipologiaEvento(EventoPresaInCaricoDaCessione.idTipologiaDB);
					carico.setMicrochip(cessioneToImport.getMicrochip());
					carico.setIdAnimale(cessioneToImport.getIdAnimale());

					if (cessioneToImport.getSpecieAnimaleId() == Cane.idSpecie) {
						Cane thisCane = new Cane(db,
								cessioneToImport.getIdAnimale());
						HashMap map = carico
								.aggiungiRegistrazione(thisCane, db);

					} else if (cessioneToImport.getSpecieAnimaleId() == Gatto.idSpecie) {
						Gatto thisGatto = new Gatto(db,
								cessioneToImport.getIdAnimale());
						HashMap map = carico.aggiungiRegistrazione(thisGatto,
								db);
					}

				//	System.out.println("aa");
				}
			}

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return ("AcceptedOK");
	}

	public String executeCommandPrepareDeleteRegistrazione(ActionContext context)
			throws UnknownHostException {
		if (!hasPermission(context, "anagrafe_canina-anagrafe_canina-edit")) {
			return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		try {
			db = this.getConnection(context);

			UserBean user = (UserBean) context.getSession()
					.getAttribute("User");
			context.getRequest().setAttribute("User", user);

			String id_evento = context.getRequest().getParameter(
					"registrazioneId");
			context.getRequest().setAttribute("registrazioneId", id_evento);

			String id_tipologia_registrazione = context.getRequest()
					.getParameter("tipologiaRegistrazioneId");
			context.getRequest().setAttribute("tipologiaRegistrazioneId",
					id_tipologia_registrazione);

			int id_animale = Integer.parseInt(context.getRequest()
					.getParameter("animaleId"));
			Animale thisAnimale = new Animale(db, id_animale);
			context.getRequest().setAttribute("animale", thisAnimale);
			
			
			LookupList statoList = new LookupList(db, "lookup_tipologia_stato");
			context.getRequest().setAttribute("statoList", statoList);

			RegistrazioniWKF wk = new RegistrazioniWKF();
			ArrayList reg = wk.getRegistrazioniSpecie(db);
			LookupList registrazioniList = new LookupList(reg, -1);
			registrazioniList.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("registrazioniList",
					registrazioniList);

			int id_evento_int = Integer.parseInt(id_evento);
			Evento thisEvento = new Evento(db, id_evento_int);
			context.getRequest().setAttribute("thisEvento", thisEvento);
			
			
			int idStatoOriginale = thisEvento.getIdStatoOriginale();
			boolean isStatoDecesso = idStatoOriginale==39 || idStatoOriginale==10 || idStatoOriginale==11 || idStatoOriginale==24 || idStatoOriginale==40 || idStatoOriginale==7
					 || idStatoOriginale==38 || idStatoOriginale==41 || idStatoOriginale==45 || idStatoOriginale==54 || idStatoOriginale==82 || idStatoOriginale==73;
			
			if(id_tipologia_registrazione.equals("9") && isStatoDecesso)
				context.getRequest().setAttribute("statoInconsistente", "true");

			// Controllo se la registrazione è aperta su vam
			// In tal caso impedisco la cancellazione
			// String dbName = ApplicationProperties.getProperty("dbnameVam");
			// String username =
			// ApplicationProperties.getProperty("usernameDbVam");
			// String pwd =ApplicationProperties.getProperty("passwordDbVam");
			// String host =
			// InetAddress.getByName("hostDbVam").getHostAddress();
			//
			// int registrazione=-1;
			// Connection dbvam = DbUtil.getConnection(dbName, username, pwd,
			// host);
			// PreparedStatement pst = null;
			// StringBuffer sql = new StringBuffer();
			// sql.append("SELECT * from public_functions.ricerca_registrazione(?)");
			// int i = 0;
			// pst = dbvam.prepareStatement(sql.toString());
			// pst.setInt(++i, -1);
			//
			// ResultSet result = pst.executeQuery();
			// while (result.next()){ // process results one row at a time
			// registrazione= result.getInt("ricerca_registrazione");
			// }
			// if (registrazione>0){
			// if (System.getProperty("DEBUG") != null)
			// System.out.println("ERRORE VAM");
			// context.getRequest().setAttribute("errore",
			// "Impossibile eliminare la registrazione da BDU in quanto risulta presente anche in VAM.");
			// return ("AnimalError");
			// }

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return "SystemError";
		} finally {
			this.freeConnection(context, db);
		}
		return "prepareDeleteRegistrazioneOk";
	}

	public String executeCommandDeleteRegistrazione(ActionContext context)
			throws Exception {
		if (!hasPermission(context, "anagrafe_canina-anagrafe_canina-edit")) {
			return ("PermissionError");
		}

		String id_evento = context.getRequest().getParameter("idRegistrazione");
		String id_tipologia_evento = context.getRequest().getParameter(
				"idTipologiaRegistrazione");
		String tipoEvento = context.getRequest().getParameter("tipoEvento");
		String id_utente = context.getRequest().getParameter(
				"utenteCancellazione");
		String id_data = context.getRequest().getParameter("dataCancellazione");
		String id_animale_string = context.getRequest().getParameter(
				"idAnimale");
		String note_cancellazione = context.getRequest().getParameter(
				"noteCancellazione");

		int id_animale = Integer.parseInt(id_animale_string);
		int idEvento = Integer.parseInt(id_evento);
		int idUtente = Integer.parseInt(id_utente);
		int tipologiaRegistrazione = Integer.parseInt(id_tipologia_evento);

		Connection db = null;
		try {
			db = this.getConnection(context);
			db.setAutoCommit(false);
			Animale thisAnimale = new Animale(db, id_animale);
			Evento eventoDaCancellare = new Evento(db, idEvento);
			String note_interne_animale = thisAnimale.getNoteInternalUseOnly();
			if (note_interne_animale == null
					|| note_interne_animale.equals("null"))
				note_interne_animale = "";
			String note_interne_evento = thisAnimale.getNoteInternalUseOnly();
			if (note_interne_evento == null
					|| note_interne_evento.equals("null"))
				note_interne_evento = "";

			if (eventoDaCancellare.isFlagFuoriDominioAsl()
					&& thisAnimale.isFlagUltimaOperazioneFuoriDominioAsl()
					&& eventoDaCancellare.getIdEvento() == thisAnimale
							.getIdRegistrazioneUltimaOperazioneFuoriDominioAsl()) {
				thisAnimale.setFlagUltimaOperazioneFuoriDominioAsl(false);
				thisAnimale
						.setIdRegistrazioneUltimaOperazioneFuoriDominioAsl(-1);
				thisAnimale.setIdAslUltimaOperazioneFuoriDominioAsl(-1);
				thisAnimale.setIdDetentoreUltimaOperazioneFuoriDominioAsl(-1);
				thisAnimale.setIdTipologiaUltimaOperazioneFuoriDominioAsl(-1);
			}

			/*
			 * //SE LA REGISTRAZIONE MODIFICA LO STATO, AGGIORNALO if
			 * (tipologiaRegistrazione!= EventoMorsicatura.idTipologiaDB &&
			 * tipologiaRegistrazione !=
			 * EventoInserimentoMicrochip.idTipologiaDBSecondoMicrochip &&
			 * tipologiaRegistrazione !=
			 * EventoInserimentoVaccinazioni.idTipologiaDB &&
			 * tipologiaRegistrazione != EventoTrasferimento.idTipologiaDB &&
			 * tipologiaRegistrazione != EventoTrasferimentoCanile.idTipologiaDB
			 * && tipologiaRegistrazione !=
			 * EventoRilascioPassaporto.idTipologiaDB && tipologiaRegistrazione
			 * != EventoEsitoControlli.idTipologiaDB && tipologiaRegistrazione
			 * != EventoRegistrazioneEsitoControlliCommerciali.idTipologiaDB &&
			 * tipologiaRegistrazione != EventoCattura.idTipologiaDB &&
			 * tipologiaRegistrazione != EventoCambioDetentore.idTipologiaDB){
			 * RegistrazioniWKF r_wkf = new RegistrazioniWKF();
			 * r_wkf.setIdStato(thisAnimale.getStato());
			 * r_wkf.setIdRegistrazione(tipologiaRegistrazione);
			 * thisAnimale.setStato
			 * ((r_wkf.getPrecedenteStatoDaStatoAttualeERegistrazione
			 * (db)).getIdProssimoStato()); }
			 */

			// AGGIORNAMENTO DETTAGLI ANIMALE

			// leggo il tipo di evento
			switch (tipologiaRegistrazione) {

			case EventoRitrovamento.idTipologiaDB: {
				// ritrovamento: aggiorno proprietario e detentore
				EventoRitrovamento ritrovamento = new EventoRitrovamento(db,
						eventoDaCancellare.getIdEvento());
				Operatore oldProprietario = new Operatore();
				Operatore oldDetentore = new Operatore();
				oldProprietario.queryRecordOperatorebyIdLineaProduttiva(db,
						ritrovamento.getIdProprietarioOld());
				oldDetentore.queryRecordOperatorebyIdLineaProduttiva(db,
						ritrovamento.getIdDetentoreOld());
				thisAnimale.setProprietario(oldProprietario);
				thisAnimale.setIdProprietario(ritrovamento
						.getIdProprietarioOld());
				thisAnimale.update(db);
				aggiornamentoFlagFurtoSmarrito(db,ritrovamento.getIdStatoOriginale(), thisAnimale);

				if (thisAnimale.getIdSpecie() == Cane.idSpecie) {
					Cane thisCane = new Cane(db, thisAnimale.getIdAnimale());
					thisCane.setDetentore(oldDetentore);
					thisCane.setIdDetentore(ritrovamento.getIdDetentoreOld());
					thisCane.update(db);
				} else if (thisAnimale.getIdSpecie() == Gatto.idSpecie) {
					Gatto thisGatto = new Gatto(db, thisAnimale.getIdAnimale());
					thisGatto.setDetentore(oldDetentore);
					thisGatto.setIdDetentore(ritrovamento.getIdDetentoreOld());
					thisGatto.update(db);
				} else if (thisAnimale.getIdSpecie() == Furetto.idSpecie) {
					Furetto thisFuretto = new Furetto(db,
							thisAnimale.getIdAnimale());
					thisFuretto.setDetentore(oldDetentore);
					thisFuretto
							.setIdDetentore(ritrovamento.getIdDetentoreOld());
					thisFuretto.update(db);
				}
				break;
			}
			case EventoSmarrimento.idTipologiaDB: {
				// smarrimento: aggiorno flag smarrimento
				thisAnimale.setFlagSmarrimento(false);
				break;
			}
			case EventoAdozioneDaCanile.idTipologiaDB: {
				// adozione da canile: SOLO CANE aggiorno proprietario e
				// detentore
				int id_evento_int = Integer.parseInt(id_evento);
				EventoAdozioneDaCanile adozione = new EventoAdozioneDaCanile(
						db, id_evento_int);
				Operatore oldProprietario = new Operatore();
				Operatore oldDetentore = new Operatore();
				oldProprietario.queryRecordOperatorebyIdLineaProduttiva(db,
						adozione.getIdVecchioProprietario());
				oldDetentore.queryRecordOperatorebyIdLineaProduttiva(db,
						adozione.getIdVecchioDetentore());
				thisAnimale.setProprietario(oldProprietario);
				thisAnimale.setIdProprietario(adozione
						.getIdVecchioProprietario());
				thisAnimale.setIdDetentore(adozione.getIdVecchioDetentore());
				thisAnimale.setDetentore(oldDetentore);
				thisAnimale.updateStato(db);
				break;
			}

			case EventoAdozioneDaColonia.idTipologiaDB: {
				// adozione da colonia: SOLO GATTO aggiorno proprietario e
				// detentore
				int id_evento_int = Integer.parseInt(id_evento);
				EventoAdozioneDaColonia adozione = new EventoAdozioneDaColonia(
						db, id_evento_int);
				Operatore oldProprietario = new Operatore();
				Operatore oldDetentore = new Operatore();
				oldProprietario.queryRecordOperatorebyIdLineaProduttiva(db,
						adozione.getIdVecchioProprietario());
				oldDetentore.queryRecordOperatorebyIdLineaProduttiva(db,
						adozione.getIdVecchioDetentore());
				thisAnimale.setProprietario(oldProprietario);
				thisAnimale.setIdProprietario(adozione
						.getIdVecchioProprietario());
				thisAnimale.setIdDetentore(adozione.getIdVecchioDetentore());
				thisAnimale.setDetentore(oldDetentore);
				thisAnimale.updateStato(db);
				break;
			}

			case EventoRestituzioneACanile.idTipologiaDB: {
				// restituzione a canile: SOLO CANE aggiorno proprietario e
				// detentore
				int id_evento_int = Integer.parseInt(id_evento);
				EventoRestituzioneACanile restituzione = new EventoRestituzioneACanile(
						db, id_evento_int);
				Operatore oldProprietario = new Operatore();
				Operatore oldDetentore = new Operatore();
				oldProprietario.queryRecordOperatorebyIdLineaProduttiva(db,
						restituzione.getIdProprietarioDaRestituzione());
				oldDetentore = oldProprietario;
				thisAnimale.setProprietario(oldProprietario);
				thisAnimale.setIdProprietario(restituzione
						.getIdProprietarioDaRestituzione());
				thisAnimale.setIdDetentore(restituzione
						.getIdProprietarioDaRestituzione());
				thisAnimale.setDetentore(oldProprietario);
				thisAnimale.updateStato(db);
				break;
			}

			case EventoRestituzioneAProprietario.idTipologiaDB: {
				// restituzione a proprietario
				int id_evento_int = Integer.parseInt(id_evento);
				EventoRestituzioneAProprietario restituzione = new EventoRestituzioneAProprietario(
						db, id_evento_int);
				// Cerco se c'è un ritrovamento non denunciato chiuso per
				// riaprirlo
				EventoRitrovamentoNonDenunciato ritrovamento = new EventoRitrovamentoNonDenunciato();
				ritrovamento.GetRitrovamentoNonDenunciatoApertoByIdAnimale(db,
						thisAnimale.getIdAnimale());
				if (ritrovamento != null) {
					ritrovamento.setFlagAperto(false);
					ritrovamento.updateFlagAperto(db);
				}

				break;
			}
			case EventoRitrovamentoNonDenunciato.idTipologiaDB: {
				// restituzione a proprietario
				int id_evento_int = Integer.parseInt(id_evento);
				EventoRitrovamentoNonDenunciato ritrovamento = new EventoRitrovamentoNonDenunciato(
						db, id_evento_int);
				// riapro il ritrovamento
				ritrovamento.setFlagAperto(true);
				ritrovamento.updateFlagAperto(db);
				Operatore oldDetentore = new Operatore();
				oldDetentore.queryRecordOperatorebyIdLineaProduttiva(db,
						ritrovamento.getIdDetentoreOldNd());
				thisAnimale.setIdDetentore(ritrovamento.getIdDetentoreOldNd());
				thisAnimale.setDetentore(oldDetentore);
				thisAnimale.updateStato(db);

				break;
			}
			case EventoCessione.idTipologiaDB: {
				// cessione: aggiorno proprietario e detentore
				int id_evento_int = Integer.parseInt(id_evento);
				EventoCessione cessione = new EventoCessione(db, id_evento_int);
				cessione.setFlagAccettato(true);
				cessione.updateAccettazione(db);
				
				if(cessione.getIdVecchioProprietario()>0)
				{
					Operatore oldProprietario = new Operatore();
					oldProprietario.queryRecordOperatorebyIdLineaProduttiva(db, cessione.getIdVecchioProprietario());
					thisAnimale.setProprietario(oldProprietario);
				}
				else
					thisAnimale.setProprietario(null);
				thisAnimale.setIdProprietario(cessione.getIdVecchioProprietario());
				
				if(cessione.getIdVecchioDetentore()>0)
				{
					Operatore oldDetentore = new Operatore();
					oldDetentore.queryRecordOperatorebyIdLineaProduttiva(db, cessione.getIdVecchioDetentore());
					thisAnimale.setDetentore(oldDetentore);
				}
				else
					thisAnimale.setDetentore(null);
				thisAnimale.setIdDetentore(cessione.getIdVecchioDetentore());
				
				thisAnimale.setIdAslRiferimento(cessione
						.getIdAslVecchioProprietario());
				thisAnimale.updateStato(db);

				break;
			}
			
			case EventoCessione.idTipologiaDB_obsoleto: {
				System.out.println("CANCELLAZIONE OLD CESSIONE");
				// cessione: aggiorno proprietario e detentore
				int id_evento_int = Integer.parseInt(id_evento);
				EventoCessione cessione = new EventoCessione(db, id_evento_int);
				cessione.setFlagAccettato(false);
				cessione.updateAccettazione(db);
				
				if(cessione.getIdVecchioProprietario()>0)
				{
					Operatore oldProprietario = new Operatore();
					oldProprietario.queryRecordOperatorebyIdLineaProduttiva(db, cessione.getIdVecchioProprietario());
					thisAnimale.setProprietario(oldProprietario);
				}
				else
					thisAnimale.setProprietario(null);
				thisAnimale.setIdProprietario(cessione.getIdVecchioProprietario());
				
				if(cessione.getIdVecchioDetentore()>0)
				{
					Operatore oldDetentore = new Operatore();
					oldDetentore.queryRecordOperatorebyIdLineaProduttiva(db, cessione.getIdVecchioDetentore());
					thisAnimale.setDetentore(oldDetentore);
				}
				else
					thisAnimale.setDetentore(null);
				thisAnimale.setIdDetentore(cessione.getIdVecchioDetentore());
				
				thisAnimale.setIdAslRiferimento(cessione
						.getIdAslVecchioProprietario());
				thisAnimale.updateStato(db);
				

				break;
			}

			case EventoPresaInCaricoDaCessione.idTipologiaDB: {
				// cessione: aggiorno proprietario e detentore dell'animale e
				// riapro l'ultima cessione chiusa
				Operatore oldProprietario = new Operatore();
				Operatore oldDetentore = new Operatore();
				thisAnimale.setIdDetentore(-1);
				thisAnimale.setIdProprietario(-1);
				thisAnimale.setProprietario(oldProprietario);
				thisAnimale.setDetentore(oldDetentore);
				thisAnimale.updateStato(db);

				EventoCessione cessioneChiusa = new EventoCessione();
				cessioneChiusa.GetUltimaCessioneChiusaByIdAnimale(db,
						thisAnimale.getIdAnimale());
				cessioneChiusa.setFlagAccettato(false);
				cessioneChiusa.updateAccettazione(db);

				break;
			}

			case EventoTrasferimento.idTipologiaDB: {
				// trasferimento: aggiorno proprietario e detentore
				int id_evento_int = Integer.parseInt(id_evento);
				EventoTrasferimento trasferimento = new EventoTrasferimento(db,
						id_evento_int);
				Operatore oldProprietario = new Operatore();
				Operatore oldDetentore = new Operatore();
				oldProprietario.queryRecordOperatorebyIdLineaProduttiva(db,
						trasferimento.getIdVecchioProprietario());
				oldDetentore.queryRecordOperatorebyIdLineaProduttiva(db,
						trasferimento.getIdVecchioDetentore());
				thisAnimale.setProprietario(oldProprietario);
				thisAnimale.setIdProprietario(trasferimento
						.getIdVecchioProprietario());
				thisAnimale.setDetentore(oldDetentore);
				thisAnimale.setIdDetentore(trasferimento
						.getIdVecchioDetentore());
				thisAnimale.updateStato(db);
				break;
			}
			
			case EventoTrasferimentoSindaco.idTipologiaDB: {
				// trasferimento: aggiorno proprietario e detentore
				int id_evento_int = Integer.parseInt(id_evento);
				EventoTrasferimentoSindaco trasferimento = new EventoTrasferimentoSindaco(db,
						id_evento_int);
				Operatore oldProprietario = new Operatore();
				Operatore oldDetentore = new Operatore();
				oldProprietario.queryRecordOperatorebyIdLineaProduttiva(db,trasferimento.getIdVecchioProprietario());
				oldDetentore.queryRecordOperatorebyIdLineaProduttiva(db,trasferimento.getIdVecchioDetentore());
				thisAnimale.setProprietario(oldProprietario);
				thisAnimale.setIdProprietario(trasferimento.getIdVecchioProprietario());
				thisAnimale.setDetentore(oldDetentore);
				thisAnimale.setIdDetentore(trasferimento.getIdVecchioDetentore());
				thisAnimale.updateStato(db);
				break;
			}

			case EventoTrasferimentoFuoriRegioneSoloProprietario.idTipologiaDB: {
				// trasferimento fr solo proprietario: aggiorno proprietario
				int id_evento_int = Integer.parseInt(id_evento);
				EventoTrasferimentoFuoriRegioneSoloProprietario trasferimento = new EventoTrasferimentoFuoriRegioneSoloProprietario(
						db, id_evento_int);
				Operatore oldProprietario = new Operatore();
				oldProprietario.queryRecordOperatorebyIdLineaProduttiva(db,
						trasferimento.getIdVecchioProprietario());
				thisAnimale.setProprietario(oldProprietario);
				thisAnimale.setIdProprietario(trasferimento
						.getIdVecchioProprietario());
				thisAnimale.update(db);
				break;
			}

			case EventoRientroFuoriRegione.idTipologiaDB: {
				// rientro fuori regione: aggiorno proprietario, detentore e
				// flag FR (SOLO CANE?)
				int id_evento_int = Integer.parseInt(id_evento);
				EventoRientroFuoriRegione rientro = new EventoRientroFuoriRegione(
						db, id_evento_int);
				Operatore oldProprietario = new Operatore();
				Operatore oldDetentore = new Operatore();
				thisAnimale.setProprietario(oldProprietario);
				thisAnimale.setIdProprietario(-1);
				thisAnimale.setDetentore(oldDetentore);
				thisAnimale.setIdDetentore(-1);
				thisAnimale.setIdRegione(rientro.getIdRegioneDa());
				thisAnimale.updateStato(db);

				if (thisAnimale.getIdSpecie() == Cane.idSpecie) {
					Cane thisCane = new Cane(db, thisAnimale.getIdAnimale());
					thisCane.setFlagFuoriRegione(true);
					thisCane.update(db);
				}

				break;
			}
			case EventoCambioDetentore.idTipologiaDB: {
				// cambio detentore: aggiorno detentore
				int id_evento_int = Integer.parseInt(id_evento);
				EventoCambioDetentore cambio = new EventoCambioDetentore(db,
						id_evento_int);

				Operatore oldDetentore = new Operatore();
				oldDetentore.queryRecordOperatorebyIdLineaProduttiva(db,
						cambio.getIdVecchioDetentore());

				thisAnimale.setDetentore(oldDetentore);
				thisAnimale.setIdDetentore(cambio.getIdVecchioDetentore());
				thisAnimale.updateStato(db);
				break;
			}
			case EventoCattura.idTipologiaDB: {
				// DA FARE? LA CATTURA NON PUO' ESSERE ELIMINATA DA UN RANDAGIO

				break;
			}

			case EventoCattura.idTipologiaDBRicattura: {
				// ricattura: aggiorno flag ricattura. Come aggiornare
				// detentore?
				int id_evento_int = Integer.parseInt(id_evento);
				Evento evento = new Evento(db, id_evento_int);
				EventoCattura ricattura = new EventoCattura(db, id_evento_int);
//				int reimmissione_id = ricattura.cercaUltimaReimmissione(db,
//						evento.getIdAnimale());
//				EventoReimmissione reimmissione = new EventoReimmissione(db,
//						reimmissione_id);

//				Operatore oldDetentore = new Operatore();
//				oldDetentore.queryRecordOperatorebyIdLineaProduttiva(db,
//						reimmissione.getIdDetentorePrecedente());
				// COME RECUPERARE IL VECCHIO DETENTORE??

			//	thisAnimale.setDetentore(oldDetentore);
				thisAnimale.setIdDetentore(ricattura.getIdProprietario());
				thisAnimale.updateStato(db);
				if (thisAnimale.getIdSpecie() == Cane.idSpecie) {
					Cane thisCane = new Cane(db, thisAnimale.getIdAnimale());
					thisCane.setFlagRiCattura(false);
					thisCane.updateStato(db);
				}
				if (thisAnimale.getIdSpecie() == Gatto.idSpecie) {
					Gatto thisGatto = new Gatto(db, thisAnimale.getIdAnimale());
					thisGatto.setFlagRiCattura(false);
					thisGatto.updateStato(db);
				}
				break;
			}

			case EventoTrasferimentoCanile.idTipologiaDB: {
				// trasferimentocanile: (SOLO CANE) aggiorno detentore
				int id_evento_int = Integer.parseInt(id_evento);
				EventoTrasferimentoCanile cambio = new EventoTrasferimentoCanile(
						db, id_evento_int);

				// Operatore oldDetentore = new Operatore();
				// oldDetentore.queryRecordOperatorebyIdLineaProduttiva(db,
				// cambio.getIdCanileOld());
				// thisAnimale.setDetentore(oldDetentore);
				thisAnimale.setIdDetentore(cambio.getIdCanileOld());
				thisAnimale.updateStato(db);

				break;
			}

			case EventoAdozioneDistanza.idTipologiaDB: {
				// DA FARE?
				break;
			}
			case EventoReimmissione.idTipologiaDB: {
				// reimmissione: aggiorno flag reimmesso
				int id_evento_int = Integer.parseInt(id_evento);
				//Evento evento = new Evento(db, id_evento_int);
				EventoReimmissione reimmissione = new EventoReimmissione(db, id_evento_int);
				if (thisAnimale.getIdSpecie() == Cane.idSpecie) {
					Cane thisCane = new Cane(db, thisAnimale.getIdAnimale());
					thisCane.setIdDetentore(reimmissione.getIdDetentorePrecedente());
					thisCane.setFlagReimmesso(false);
					thisCane.update(db);
					thisAnimale = thisCane;
				}else if (thisAnimale.getIdSpecie() == Gatto.idSpecie){
					Gatto thisGatto = new Gatto(db, thisAnimale.getIdAnimale());
					thisGatto.setIdDetentore(reimmissione.getIdDetentorePrecedente());
					thisGatto.setFlagReimmesso(false);
					thisGatto.updateStato(db);
					thisAnimale = thisGatto;
				}

				break;
			}

			case EventoMorsicatura.idTipologiaDB: {
				// morsicatura: (SOLO CANE) aggiorno flag morsicatura e data
				// morso
				Cane thisCane = new Cane(db, thisAnimale.getIdAnimale());
				thisCane.setFlagMorsicatore(thisAnimale.cercaMorsicatura(db,idEvento)>0);
				thisCane.setDataMorso("");
				thisCane.updateStato(db);
				break;
			}
			case EventoAggressione.idTipologiaDB: {
				Cane thisCane = new Cane(db, thisAnimale.getIdAnimale());
				thisCane.updateStato(db);
				thisAnimale.updateFlagAggressivo(db,thisAnimale.cercaAggressione(db,idEvento)>0);
				break;
			}
			case EventoSterilizzazione.idTipologiaDB: {
				// sterilizzazione: aggiorno data sterilizzazione e flag
				// sterilizzato
				thisAnimale.setDataSterilizzazione("");
				thisAnimale.setFlagSterilizzazione(false);
				thisAnimale.setIdPraticaContributi(-1);
				// SCALARE DA PRATICA?

				int id_evento_int = Integer.parseInt(id_evento);
				EventoSterilizzazione ster = new EventoSterilizzazione(db,
						id_evento_int);
				if (ster.isFlagContributoRegionale()) {
					Pratica praticaContributi = new Pratica(db,
							ster.getIdProgettoSterilizzazioneRichiesto());
					if (thisAnimale.isRandagio(db)) {
						//Flusso 251
						if(praticaContributi.getIdTipologiaPratica()!=3)
						{
							praticaContributi.aggiornaCatturatiIncremento(db,praticaContributi.getId(),thisAnimale.getIdSpecie());
						}
						else
						{
							Pratica.aggiornaMaschiFemmina(db, praticaContributi.getId(), thisAnimale.getSesso(), -1);
						}
						
						
					} else {
						
						
						if(praticaContributi.getIdTipologiaPratica()!=3)
						{
							praticaContributi.aggiornaPadronaliIncremento(db,
									praticaContributi.getId(),
									thisAnimale.getIdSpecie());
						}
						else
						{
							Pratica.aggiornaMaschiFemmina(db, praticaContributi.getId(), thisAnimale.getSesso(), -1);
						}

					}

				}

				break;
			}
			case EventoDecesso.idTipologiaDB: {
				// decesso: aggiorno flag decesso
				thisAnimale.setFlagDecesso(false);
				break;
			}
			case EventoFurto.idTipologiaDB: {
				// DA FARE? SEMBRA AGGIORNARE SOLO LO STATO
				thisAnimale.setFlagFurto(false);
				break;
			}
			case EventoTrasferimentoFuoriStato.idTipologiaDB: {
				// trasferimento fuori stato: aggiorno proprietario e detentore
				int id_evento_int = Integer.parseInt(id_evento);
				EventoTrasferimentoFuoriStato trasferimento = new EventoTrasferimentoFuoriStato(
						db, id_evento_int);
				Operatore oldProprietario = new Operatore();
				Operatore oldDetentore = new Operatore();
				oldProprietario.queryRecordOperatorebyIdLineaProduttiva(db,
						trasferimento.getIdVecchioProprietario());
				oldDetentore.queryRecordOperatorebyIdLineaProduttiva(db,
						trasferimento.getIdVecchioDetentore());
				thisAnimale.setProprietario(oldProprietario);
				thisAnimale.setIdProprietario(trasferimento
						.getIdVecchioProprietario());
				thisAnimale.setDetentore(oldDetentore);
				thisAnimale.setIdDetentore(trasferimento
						.getIdVecchioDetentore());
				thisAnimale.updateStato(db);

				break;
			}
			case EventoTrasferimentoFuoriRegione.idTipologiaDB: {
				// trasferimento fr: aggiorno prprietario, detentore e flag fr
				int id_evento_int = Integer.parseInt(id_evento);
				EventoTrasferimentoFuoriRegione trasferimento = new EventoTrasferimentoFuoriRegione(
						db, id_evento_int);
				Operatore oldProprietario = new Operatore();
				Operatore oldDetentore = new Operatore();
				oldProprietario.queryRecordOperatorebyIdLineaProduttiva(db,
						trasferimento.getIdVecchioProprietario());
				oldDetentore.queryRecordOperatorebyIdLineaProduttiva(db,
						trasferimento.getIdVecchioDetentore());
				thisAnimale.setProprietario(oldProprietario);
				thisAnimale.setIdProprietario(trasferimento
						.getIdVecchioProprietario());
				thisAnimale.setDetentore(oldDetentore);
				thisAnimale.setIdDetentore(trasferimento
						.getIdVecchioDetentore());
				thisAnimale.setIdAslRiferimento(trasferimento
						.getIdAslVecchioProprietario());
				thisAnimale.updateStato(db);

				if (thisAnimale.getIdSpecie() == Cane.idSpecie) {
					Cane thisCane = new Cane(db, thisAnimale.getIdAnimale());
					thisCane.setFlagFuoriRegione(false);
					thisCane.update(db);
				}
				break;
			}
			case EventoRientroFuoriStato.idTipologiaDB: {
				// rientro fs: aggiorno proprietario e detentore a -1
				thisAnimale.setIdProprietario(-1);
				thisAnimale.setIdDetentore(-1);
				thisAnimale.updateStato(db);

				break;
			}
			case EventoInserimentoVaccinazioni.idTipologiaDB: {
				// vaccinazioni: aggiorno data vaccino, numero lotto vaccino
				if (thisAnimale.getIdSpecie() == Cane.idSpecie) {
					Cane thisCane = new Cane(db, thisAnimale.getIdAnimale());
					thisCane.setDataVaccino("");
					thisCane.setNumeroLottoVaccino("");
					thisCane.update(db);
				} else if (thisAnimale.getIdSpecie() == Gatto.idSpecie) {
					Gatto thisGatto = new Gatto(db, thisAnimale.getIdAnimale());
					thisGatto.setDataVaccino("");
					thisGatto.setNumeroLottoVaccino("");
					thisGatto.update(db);
				} else if (thisAnimale.getIdSpecie() == Furetto.idSpecie) {
					Furetto thisFuretto = new Furetto(db,
							thisAnimale.getIdAnimale());
					thisFuretto.update(db);
				}

				break;
			}
			case EventoInserimentoMicrochip.idTipologiaDBSecondoMicrochip: {
				PreparedStatement pstTat = db.prepareStatement("update microchips set id_animale = null ,flag_secondo_microchip = false ,id_specie = null where microchip = ? ");
				pstTat.setString(1, thisAnimale.getTatuaggio());
				pstTat.execute();
				pstTat.close();
				
				// inserimento tatuaggio: aggiorno tatuaggio e data tatuaggio
				thisAnimale.setTatuaggio("");
				thisAnimale.setDataTatuaggio("");
				thisAnimale.update(db);
				
				
				break;
			}

			case EventoRilascioPassaporto.idTipologiaDB: {
				// rilascio passaporto: aggiorno numero e data passaporto
				thisAnimale.setNumeroPassaporto("");
				thisAnimale.setDataRilascioPassaporto("");
				thisAnimale.updateStato(db);
				EventoRilascioPassaporto eventoPassaporto = new EventoRilascioPassaporto(db, idEvento);
				Passaporto p = new Passaporto().load(eventoPassaporto.getNumeroPassaporto(), db);
				p.setPassaportoNonUtilizzato(db);
				break;
			}
			case EventoRilascioPassaporto.idTipologiaRinnovoDB: 
			{
				EventoRilascioPassaporto eventoPassaporto = new EventoRilascioPassaporto(db, idEvento);
				Passaporto p = new Passaporto().load(eventoPassaporto.getNumeroPassaporto(), db);
				//Se p è diverso da null vuol dire che non sta in banca dati (vecchia registrazione)
				if(p!=null)
					p.setPassaportoNonUtilizzato(db);
				break;
			}
			case EventoEsitoControlli.idTipologiaDB: {
				// esito controlli: (SOLO CANE) aggiorno flag, date ed esiti
				if (thisAnimale.getIdSpecie() == Cane.idSpecie) {
					EventoEsitoControlli controlli = new EventoEsitoControlli();
					controlli.setDataEhrlichiosi("");
					controlli.setFlagEhrlichiosi(false);
					controlli.setEsitoEhrlichiosi(-1);
					controlli.setDataRickettiosi("");
					controlli.setFlagEhrlichiosi(false);
					controlli.setEsitoRickettiosi(-1);
					Cane thisCane = new Cane(db, thisAnimale.getIdAnimale());
					thisCane.updateControlliCane(db, controlli);
				}
				break;
			}

			case EventoRegistrazioneEsitoControlliCommerciali.idTipologiaDB: {
				// esito controlli commerciali: aggiorno date, flag ed esiti
				if (thisAnimale.getIdSpecie() == Cane.idSpecie) {
					EventoRegistrazioneEsitoControlliCommerciali controlli = new EventoRegistrazioneEsitoControlliCommerciali();
					controlli.setDataEsitoControlloDocumentale("");
					controlli.setDataEsitoControlloFisico("");
					controlli.setDataEsitoControlloIdentita("");
					controlli.setDataEsitoControlloLaboratorio("");
					controlli.setDataInserimentoControlli("");
					controlli.setFlagPresenzaEsitoControlloDocumentale(false);
					controlli.setFlagPresenzaEsitoControlloFisico(false);
					controlli.setFlagPresenzaEsitoControlloIdentita(false);
					controlli.setFlagPresenzaEsitoControlloLaboratorio(false);
					controlli.setIdEsitoControlloDocumentale(-1);
					controlli.setIdEsitoControlloFisico(-1);
					controlli.setIdEsitoControlloIdentita(-1);
					controlli.setIdEsitoControlloLaboratorio(-1);
					thisAnimale.updateControlliCommerciali(db, controlli, true);
				}
				break;
			}
			case EventoAdozioneFuoriAsl.idTipologiaDB: {
				// adozione fuori asl: risetto proprietario, detentore e asl

				int id_evento_int = Integer.parseInt(id_evento);
				EventoAdozioneFuoriAsl adozionefa = new EventoAdozioneFuoriAsl(
						db, id_evento_int);
				Operatore oldProprietario = new Operatore();
				Operatore oldDetentore = new Operatore();
				oldProprietario.queryRecordOperatorebyIdLineaProduttiva(db,
						adozionefa.getIdVecchioProprietario());
				oldDetentore.queryRecordOperatorebyIdLineaProduttiva(db,
						adozionefa.getIdVecchioDetentore());
				thisAnimale.setProprietario(oldProprietario);
				thisAnimale.setIdProprietario(adozionefa
						.getIdVecchioProprietario());
				thisAnimale.setIdDetentore(adozionefa.getIdVecchioDetentore());
				thisAnimale.setDetentore(oldDetentore);
				thisAnimale.setIdAslRiferimento(adozionefa
						.getIdAslVecchioProprietario());
				thisAnimale.updateStato(db);

				break;
			}
			
			case EventoAdozioneFuoriAsl.idTipologiaDB_obsoleto: {
				// adozione fuori asl: risetto proprietario, detentore e asl

				int id_evento_int = Integer.parseInt(id_evento);
				EventoAdozioneFuoriAsl adozionefa = new EventoAdozioneFuoriAsl(
						db, id_evento_int);
				Operatore oldProprietario = new Operatore();
				Operatore oldDetentore = new Operatore();
				oldProprietario.queryRecordOperatorebyIdLineaProduttiva(db,
						adozionefa.getIdVecchioProprietario());
				oldDetentore.queryRecordOperatorebyIdLineaProduttiva(db,
						adozionefa.getIdVecchioDetentore());
				thisAnimale.setProprietario(oldProprietario);
				thisAnimale.setIdProprietario(adozionefa
						.getIdVecchioProprietario());
				thisAnimale.setIdDetentore(adozionefa.getIdVecchioDetentore());
				thisAnimale.setDetentore(oldDetentore);
				thisAnimale.setIdAslRiferimento(adozionefa
						.getIdAslVecchioProprietario());
				thisAnimale.updateStato(db);

				break;
			}

			case EventoPresaInCaricoDaAdozioneFuoriAsl.idTipologiaDB: {
				// presa in carico adozione fuori asl: risetto proprietario,
				// detentore a -1

				Operatore oldProprietario = new Operatore();
				Operatore oldDetentore = new Operatore();
				thisAnimale.setIdDetentore(-1);
				thisAnimale.setIdProprietario(-1);
				thisAnimale.setProprietario(oldProprietario);
				thisAnimale.setDetentore(oldDetentore);
				thisAnimale.updateStato(db);
				
				EventoAdozioneFuoriAsl adozioneAccettata = new EventoAdozioneFuoriAsl();
				adozioneAccettata.GetUltimaAdozioneChiusaByIdAnimale(db,
						thisAnimale.getIdAnimale());
				adozioneAccettata.setFlagAccettato(false);
				adozioneAccettata.updateAccettazione(db);

				break;
			}
			case EventoModificaResidenza.idTipologiaDB: { // DA SVILUPPARE
				// modifica residenza

				// Recupero info sull'evento
				int id_evento_int = Integer.parseInt(id_evento);
				EventoModificaResidenza modificaResidenza = new EventoModificaResidenza(
						db, id_evento_int);

				// Recupero info sull'operatore da ripristinare
				Operatore operatoreToModify = new Operatore();

				// Creo registrazione vuota
				RegistrazioneModificaIndirizzoOperatore dettagli_registrazione = new RegistrazioneModificaIndirizzoOperatore();

				if (modificaResidenza
						.getIdTipologiaSoggettoDestinatarioModifica() == EventoModificaResidenza.modificaProprietario) {
					operatoreToModify = thisAnimale.getProprietario();
					dettagli_registrazione
							.setIdRelazioneStabilimentoLineaProduttiva(thisAnimale
									.getIdProprietario());
				} else if (modificaResidenza
						.getIdTipologiaSoggettoDestinatarioModifica() == EventoModificaResidenza.modificaDetentore) {
					operatoreToModify = thisAnimale.getDetentore();
					dettagli_registrazione
							.setIdRelazioneStabilimentoLineaProduttiva(thisAnimale
									.getIdDetentore());
				}

				// Recupero vecchio indirizzo
				Indirizzo oldIndirizzo = modificaResidenza.getOldIndirizzo();

				// Ripristino asl su animale se sto cancellando la modifica del
				// detentore
				/*
				 * if
				 * (modificaResidenza.getIdTipologiaSoggettoDestinatarioModifica
				 * () == EventoModificaResidenza.modificaDetentore){
				 * thisAnimale.setIdAslRiferimento(oldIndirizzo.getIdAsl());
				 * thisAnimale.updateStato(db); }
				 */

				// Ripristino dati sull'operatore

				dettagli_registrazione
						.setIdComuneModificaResidenza(oldIndirizzo.getComune());
				dettagli_registrazione
						.setIdProvinciaModificaResidenza(oldIndirizzo
								.getIdProvincia());
				dettagli_registrazione.setVia(oldIndirizzo.getVia());
				dettagli_registrazione.setIdNuovoIndirizzo(oldIndirizzo
						.getIdIndirizzo());
				dettagli_registrazione
						.setDataModificaResidenza(modificaResidenza
								.getDataModificaResidenza());
				dettagli_registrazione.setCancellaRegistrazione(true);
				dettagli_registrazione.ModificaOperatore(db, operatoreToModify,
						getUserId(context));
				thisAnimale.setIdAslRiferimento(oldIndirizzo.getIdAsl());
				thisAnimale.updateStato(db);

				break;
			}
			case EventoPrelievoDNA.idTipologiaDB: {
				// prelievo dna: aggiorno flag
				Cane thisCane = new Cane(db, thisAnimale.getIdAnimale());
				thisCane.setFlagPrelievoDnaEffettuato(false);
				thisCane.updateStato(db);

				// COMUNICO CANCELLAZIONE PRELIEVO A PORTALE DNA
				EventoPrelievoDNA prelievo = new EventoPrelievoDNA(db,
						eventoDaCancellare.getIdEvento());
				prelievo.aggiornaStatoConvocato(thisAnimale.getMicrochip(),
						EventoPrelievoDNA.daConvocare,
						prelievo.getDataPrelievo());
				break;
			}

			case EventoCessioneImport.idTipologiaDB: {
				// cessione
				int id_evento_int = Integer.parseInt(id_evento);
				EventoCessioneImport cessione = new EventoCessioneImport(db,
						id_evento_int);

				Operatore oldProprietario = null;
				if(cessione.getIdVecchioProprietario()>0)
				{
					
					
					oldProprietario.queryRecordOperatorebyIdLineaProduttiva(db,cessione.getIdVecchioProprietario());
				}
				
				Operatore oldDetentore = null;
				if(cessione.getIdVecchioDetentore()>0)
				{
					
					oldDetentore.queryRecordOperatorebyIdLineaProduttiva(db,cessione.getIdVecchioDetentore());
				}
				
				thisAnimale.setProprietario(oldProprietario);
				thisAnimale.setIdProprietario(cessione.getIdVecchioProprietario());
				thisAnimale.setDetentore(oldDetentore);
				thisAnimale.setIdDetentore(cessione.getIdVecchioDetentore());
				thisAnimale.setIdAslRiferimento(cessione
						.getIdAslVecchioProprietario());
				thisAnimale.updateStato(db);

				// }
				if (thisAnimale.getIdSpecie() == Gatto.idSpecie) {
				}
				if (thisAnimale.getIdSpecie() == Furetto.idSpecie) {
				}

				// VEDERE SE IN SEGUITO BISOGNA SCEGLIERE ANCHE IL
				// DETENTORE;
				// PER ORA LO IMPOSTO AL PROPRIETARIO
				break;

			}

			case EventoPresaCessioneImport.idTipologiaDB: {
				// presa in
				// carico da
				// cessione
				int id_evento_int = Integer.parseInt(id_evento);
				EventoCessioneImport carico = new EventoCessioneImport(db,
						id_evento_int);

				Operatore oldProprietario = new Operatore();
				Operatore oldDetentore = new Operatore();
				thisAnimale.setIdDetentore(-1);
				thisAnimale.setIdProprietario(-1);
				thisAnimale.setProprietario(oldProprietario);
				thisAnimale.setDetentore(oldDetentore);
				thisAnimale.updateStato(db);

				EventoCessioneImport cessioneImportChiusa = new EventoCessioneImport();
				cessioneImportChiusa.GetCessioneApertaByIdAnimale(db,
						thisAnimale.getIdAnimale());
				cessioneImportChiusa.setFlagAccettato(true);
				cessioneImportChiusa.updateAccettazione(db);

				// Chiudo l'evento di cessione corrispondente a questa presa
				// in
				// carico

				break;

			}
			}

			// FINE AGGIORNAMENTO DETTAGLI ANIMALE

			// Aggiornamento evento con dati cancellazione
			eventoDaCancellare.setIdUtenteCancellazione(idUtente);
			eventoDaCancellare.setNoteCancellazione(note_cancellazione);
			eventoDaCancellare.setNoteInternalUseOnly(note_interne_evento
					+ " Cancellata con note: " + note_cancellazione);
			eventoDaCancellare.cancellaEvento(db);

			// Aggiornamento note interne animale (= note cancellazione evento)
			// thisAnimale.setNoteInternalUseOnly(note_interne_animale +
			// " Cancellata registrazione "+ tipoEvento.toUpperCase()+" ("+
			// eventoDaCancellare.getIdEvento()+ ": "+note_cancellazione+")");
			// thisAnimale.update(db);

			/*
			 * //AGGIORNO LA TABELLA EVENTO CON LE INFO CANCELLAZIONE
			 * sql.append(
			 * "UPDATE evento SET note_cancellazione = ?, id_utente_cancellazione = ?, data_cancellazione = ?::timestamp where id_evento = ?"
			 * ); int i = 0; PreparedStatement pst =
			 * db.prepareStatement(sql.toString()); pst.setString(++i,
			 * note_cancellazione); pst.setInt(++i,
			 * Integer.parseInt(id_utente)); pst.setTimestamp(++i, data_date);
			 * pst.setInt(++i, Integer.parseInt(id_evento));
			 * pst.executeUpdate();
			 */

			thisAnimale.setStato(eventoDaCancellare.getIdStatoOriginale());
			thisAnimale.updateStato(db);

			context.getRequest().setAttribute("thisEvento", eventoDaCancellare);
			
			
			// Eseguo la cancellazione in VAM
			cancellaRegistrazioneVam(idEvento,note_cancellazione);
			

			RegistrazioniWKF wk = new RegistrazioniWKF();
			ArrayList reg = wk.getRegistrazioniSpecie(db);
			LookupList registrazioniList = new LookupList(reg, -1);
			registrazioniList.addItem(-1, "--Seleziona--");
			context.getRequest().setAttribute("registrazioniList",
					registrazioniList);

			LookupList statoList = new LookupList(db, "lookup_tipologia_stato");
			context.getRequest().setAttribute("statoList", statoList);

		} catch (SQLException e) {
			db.rollback();
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.setAutoCommit(true);
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("deleteOk", "OK");
		context.getRequest().setAttribute("User", id_utente);
		context.getRequest().setAttribute("dataCancellazione", id_data);
		context.getRequest().setAttribute("noteCancellazione",
				note_cancellazione);
		return "prepareDeleteRegistrazioneOk";
	}

	public String executeCommandReloadListaRegistrazioni(ActionContext context) {
		if (!hasPermission(context, "anagrafe_canina-anagrafe_canina-edit")) {
			return ("PermissionError");
		}
		return "deleteRegistrazioneOk";
	}

	private void cancellaRegistrazioneVam(int idEvento, String noteCancellazione) throws SQLException {
		
		Connection conn = null;
		PreparedStatement pst = null;

		try {

			conn = GestoreConnessioni.getConnectionVam("");
			pst = conn.prepareStatement("select * from public_functions.cancella_attivita_bdr(?, ?)");
			
			pst.setInt(1,  idEvento);
			pst.setString(2, noteCancellazione);
			ResultSet rs = pst.executeQuery();


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnectionVam(conn);
			// DbUtil.chiudiConnessioneJDBC(rs, pst, conn);

		}

	}
	
	
	
	private void aggiornamentoFlagFurtoSmarrito(Connection db, int idStatoOriginale, Animale thisAnimale) throws Exception
	{
		if(idStatoOriginale>0)
		{
			ArrayList<Integer> statiFurto = new ArrayList<Integer>();
			statiFurto.add(52);
			statiFurto.add(4);
			statiFurto.add(17);
			statiFurto.add(16);
			statiFurto.add(18);
			statiFurto.add(21);
			statiFurto.add(43);
			ArrayList<Integer> statiSmarrito = new ArrayList<Integer>();
			statiSmarrito.add(13);
			statiSmarrito.add(15);
			statiSmarrito.add(12);
			statiSmarrito.add(14);
			statiSmarrito.add(44);
			statiSmarrito.add(55);
			statiSmarrito.add(74);
			statiSmarrito.add(78);
			
			if(idStatoOriginale>0 && statiFurto.contains(idStatoOriginale))
				thisAnimale.setFlagFurto(true);
			if(idStatoOriginale>0 && statiSmarrito.contains(idStatoOriginale))
				thisAnimale.setFlagSmarrimento(true);
			if(idStatoOriginale>0 && (statiSmarrito.contains(idStatoOriginale) || statiFurto.contains(idStatoOriginale)))
				thisAnimale.updateFlag(db);
		}
	}
	
}
