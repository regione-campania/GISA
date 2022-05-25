/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrotrasgressori.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.registrotrasgressori.base.AnagraficaPagatore;
import org.aspcfs.modules.registrotrasgressori.base.NotificaPagatore;
import org.aspcfs.modules.registrotrasgressori.base.Pagamento;
import org.aspcfs.modules.registrotrasgressori.utils.PagoPaUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;

/**
 * Servlet implementation class ReloadUtenti
 */
public class ControllaScadenzePagoPA extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger("MainLogger");

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ControllaScadenzePagoPA() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ConnectionElement ce = null;
		ConnectionPool cp = null;
		Connection db = null;
		JSONObject jsonFinale = new JSONObject();
		JSONArray jsonAvvisi = new JSONArray();

		try {
			ApplicationPrefs prefs = (ApplicationPrefs) request.getSession().getServletContext().getAttribute("applicationPrefs");
			String ceHost = prefs.get("GATEKEEPER.URL");
			String ceUser = prefs.get("GATEKEEPER.USER");
			String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");

			ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
			cp = (ConnectionPool) request.getServletContext().getAttribute("ConnectionPool");
			db = cp.getConnection(ce, null);

			PagoPaUtil.salvaStoricoOperazioniAutomatiche(db, -1, -1, "", "", "[ControllaScadenzePagoPA] Inizio.");

			// GESTIONE PROROGA AVVISI IN SCADENZA
			
			PreparedStatement pst = db.prepareStatement("select * from pagopa_get_avvisi_in_scadenza();");
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				Pagamento p = new Pagamento(db, rs.getInt("id_pagamento"));

				AnagraficaPagatore trasgressore = new AnagraficaPagatore(db, p.getIdSanzione(), "T");
				AnagraficaPagatore obbligato = new AnagraficaPagatore(db, p.getIdSanzione(), "O");

				AnagraficaPagatore questoPagatore = null;
				AnagraficaPagatore altroPagatore = null;

				String tipologiaQuestoPagatore = null;
				String tipologiaAltroPagatore = null;

				if (p.getPagatore().getId() == trasgressore.getId()){
					questoPagatore = trasgressore;
					altroPagatore = obbligato;
					tipologiaQuestoPagatore = "TRASGRESSORE";
					tipologiaAltroPagatore = "OBBLIGATO";
				}
				else if (p.getPagatore().getId() == obbligato.getId()){
					questoPagatore = obbligato;
					altroPagatore = trasgressore;
					tipologiaQuestoPagatore = "OBBLIGATO";
					tipologiaAltroPagatore = "TRASGRESSORE";
				}

				//SE ESISTONO ENTRAMBI I PAGATORI
				if (trasgressore.getId() > 0 && obbligato.getId() > 0) {

					//QUESTO PAGATORE: RACCOMANDATA NON AGGIORNATA; ALTRO PAGATORE: IMMEDIATA
					if ("I".equals(altroPagatore.getNotifica().getTipoNotifica()) && ("R".equals(questoPagatore.getNotifica().getTipoNotifica()) && !questoPagatore.getNotifica().isNotificaAggiornata())) {

						String nuovaDataNotifica = null;
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date parsedDate = dateFormat.parse(altroPagatore.getNotifica().getDataNotifica());
						Timestamp dataPartenzaTimestamp = new java.sql.Timestamp(parsedDate.getTime());
						Calendar cal = Calendar.getInstance();
						cal.setTime(dataPartenzaTimestamp);
						cal.add(Calendar.DAY_OF_MONTH, 100);
						Timestamp dataTimestampNew = new Timestamp(cal.getTime().getTime());
						nuovaDataNotifica = new SimpleDateFormat("yyyy-MM-dd").format(dataTimestampNew).toString();

						NotificaPagatore.update(db, questoPagatore.getId(), p.getIdSanzione(), questoPagatore.getNotifica().getTipoNotifica(), nuovaDataNotifica, -1);

						String messaggio = "";
						String vecchiaDataScadenza = p.getDataScadenza();
						p.setDataScadenza(PagoPaUtil.calcolaDataScadenza(nuovaDataNotifica, "R", p.getTipoPagamento(), p.getTipoRiduzione(), -1));
						PagoPaUtil.salvaStorico(db, -1, p.getIdSanzione(), p.getId(), "[ControllaScadenzePagoPA] Tentativo di aggiornamento data scadenza.");
						PagoPaUtil.salvaStoricoOperazioniAutomatiche(db, p.getIdSanzione(), p.getId(), "", "", "[ControllaScadenzePagoPA] Tentativo di aggiornamento data scadenza.");
						p.update(db, -1);
						p.aggiornaDovuto(db, -1);
						if (p.getEsitoInvio().equalsIgnoreCase("OK")) {
							messaggio += "[ControllaScadenzePagoPA] [" + tipologiaAltroPagatore + " notificato tramite Contestazione Immediata, " + tipologiaQuestoPagatore + " non notificato] Data scadenza " + tipologiaQuestoPagatore + " aggiornata.";
						} else {
							messaggio += "[ControllaScadenzePagoPA] [" + tipologiaAltroPagatore + " notificato tramite Contestazione Immediata, " + tipologiaQuestoPagatore + " non notificato] Data scadenza " + tipologiaQuestoPagatore + " non aggiornata. Motivazione: " + PagoPaUtil.fixDescrizioneErrore(p.getDescrizioneErrore()) + ".";
							p.setDataScadenza(vecchiaDataScadenza);
							p.update(db, -1);
						}
						PagoPaUtil.salvaStoricoOperazioniAutomatiche(db, p.getIdSanzione(), p.getId(), vecchiaDataScadenza, p.getDataScadenza(), messaggio);

						JSONObject jsonAvviso = new JSONObject();
						jsonAvviso.put("Id sanzione", p.getIdSanzione());
						jsonAvviso.put("IUV", p.getIdentificativoUnivocoVersamento());
						jsonAvviso.put("Vecchia data scadenza", vecchiaDataScadenza);
						jsonAvviso.put("Nuova data scadenza", p.getDataScadenza());
						jsonAvviso.put("Esito operazione", p.getEsitoInvio());
						jsonAvviso.put("Messaggio", messaggio);
						jsonAvvisi.put(jsonAvviso);

					}

					// QUESTO PAGATORE: RACCOMANDATA NON AGGIORNATA; ALTRO PAGATORE: PEC
					if ("P".equals(altroPagatore.getNotifica().getTipoNotifica()) && ("R".equals(questoPagatore.getNotifica().getTipoNotifica()) && !questoPagatore.getNotifica().isNotificaAggiornata())) {

						String nuovaDataNotifica = null;
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date parsedDate = dateFormat.parse(altroPagatore.getNotifica().getDataNotifica());
						Timestamp dataPartenzaTimestamp = new java.sql.Timestamp(parsedDate.getTime());
						Calendar cal = Calendar.getInstance();
						cal.setTime(dataPartenzaTimestamp);
						cal.add(Calendar.DAY_OF_MONTH, 100);
						Timestamp dataTimestampNew = new Timestamp(cal.getTime().getTime());
						nuovaDataNotifica = new SimpleDateFormat("yyyy-MM-dd").format(dataTimestampNew).toString();

						NotificaPagatore.update(db, questoPagatore.getId(), p.getIdSanzione(), questoPagatore.getNotifica().getTipoNotifica(), nuovaDataNotifica, -1);

						String messaggio = "";
						String vecchiaDataScadenza = p.getDataScadenza();
						p.setDataScadenza(PagoPaUtil.calcolaDataScadenza(nuovaDataNotifica, "R", p.getTipoPagamento(), p.getTipoRiduzione(), -1));
						PagoPaUtil.salvaStorico(db, -1, p.getIdSanzione(), p.getId(), "[ControllaScadenzePagoPA] Tentativo di aggiornamento data scadenza.");
						PagoPaUtil.salvaStoricoOperazioniAutomatiche(db, p.getIdSanzione(), p.getId(), "", "", "[ControllaScadenzePagoPA] Tentativo di aggiornamento data scadenza.");
						p.update(db, -1);
						p.aggiornaDovuto(db, -1);
						if (p.getEsitoInvio().equalsIgnoreCase("OK")) {
							messaggio += "[ControllaScadenzePagoPA] [" + tipologiaAltroPagatore + " notificato tramite PEC, " + tipologiaQuestoPagatore + " non notificato] Data scadenza aggiornata.";
						} else {
							messaggio += "[ControllaScadenzePagoPA] [" + tipologiaAltroPagatore + " notificato tramite PEC, " + tipologiaQuestoPagatore + " non notificato] Data scadenza non aggiornata. Motivazione: " + PagoPaUtil.fixDescrizioneErrore(p.getDescrizioneErrore()) + ".";
							p.setDataScadenza(vecchiaDataScadenza);
							p.update(db, -1);
						}
						PagoPaUtil.salvaStoricoOperazioniAutomatiche(db, p.getIdSanzione(), p.getId(), vecchiaDataScadenza, p.getDataScadenza(), messaggio);

						JSONObject jsonAvviso = new JSONObject();
						jsonAvviso.put("Id sanzione", p.getIdSanzione());
						jsonAvviso.put("IUV", p.getIdentificativoUnivocoVersamento());
						jsonAvviso.put("Vecchia data scadenza", vecchiaDataScadenza);
						jsonAvviso.put("Nuova data scadenza", p.getDataScadenza());
						jsonAvviso.put("Esito operazione", p.getEsitoInvio());
						jsonAvviso.put("Messaggio", messaggio);
						jsonAvvisi.put(jsonAvviso);

					}

					// QUESTO PAGATORE: RACCOMANDATA NON AGGIORNATA; ALTRO PAGATORE: RACCOMANDATA AGGIORNATA
					if (("R".equals(altroPagatore.getNotifica().getTipoNotifica()) && altroPagatore.getNotifica().isNotificaAggiornata()) && ("R".equals(questoPagatore.getNotifica().getTipoNotifica()) && !questoPagatore.getNotifica().isNotificaAggiornata())) {

						String messaggio = "";
						PagoPaUtil.salvaStorico(db, -1, p.getIdSanzione(), p.getId(), "[ControllaScadenzePagoPA] Tentativo di annullamento IUV.");
						PagoPaUtil.salvaStoricoOperazioniAutomatiche(db, p.getIdSanzione(), p.getId(), "", "", "[ControllaScadenzePagoPA] Tentativo di annullamento IUV.");
						p.annullaDovuto(db, -1);
						if (p.getEsitoInvio().equalsIgnoreCase("OK")) {
							p.delete(db, -1);
							messaggio += "[ControllaScadenzePagoPA] [" + tipologiaAltroPagatore + " notificato tramite Raccomandata, " + tipologiaQuestoPagatore + " non notificato] Avviso di pagamento annullato.";
						} else
							messaggio += "[ControllaScadenzePagoPA] [" + tipologiaAltroPagatore + " notificato tramite Raccomandata, " + tipologiaQuestoPagatore + " non notificato] Avviso di pagamento non annullato. Motivazione: " + PagoPaUtil.fixDescrizioneErrore(p.getDescrizioneErrore()) + ".";
						PagoPaUtil.salvaStoricoOperazioniAutomatiche(db, p.getIdSanzione(), p.getId(), "", "", messaggio);

						JSONObject jsonAvviso = new JSONObject();
						jsonAvviso.put("Id sanzione", p.getIdSanzione());
						jsonAvviso.put("IUV", p.getIdentificativoUnivocoVersamento());
						jsonAvviso.put("Esito operazione", p.getEsitoInvio());
						jsonAvviso.put("Messaggio", messaggio);
						jsonAvvisi.put(jsonAvviso);

					}

					// QUESTO PAGATORE: RACCOMANDATA NON AGGIORNATA; ALTRO PAGATORE: RACCOMANDATA NON AGGIORNATA
					if (("R".equals(altroPagatore.getNotifica().getTipoNotifica()) && !altroPagatore.getNotifica().isNotificaAggiornata()) && ("R".equals(questoPagatore.getNotifica().getTipoNotifica()) && !questoPagatore.getNotifica().isNotificaAggiornata())) {

						String nuovaDataNotifica = null;
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date parsedDate = dateFormat.parse(questoPagatore.getNotifica().getDataNotifica());
						Timestamp dataPartenzaTimestamp = new java.sql.Timestamp(parsedDate.getTime());
						Calendar cal = Calendar.getInstance();
						cal.setTime(dataPartenzaTimestamp);
						cal.add(Calendar.DAY_OF_MONTH, 100);
						Timestamp dataTimestampNew = new Timestamp(cal.getTime().getTime());
						nuovaDataNotifica = new SimpleDateFormat("yyyy-MM-dd").format(dataTimestampNew).toString();

						NotificaPagatore.update(db, questoPagatore.getId(), p.getIdSanzione(), questoPagatore.getNotifica().getTipoNotifica(), nuovaDataNotifica, -1);

						String messaggio = "";
						String vecchiaDataScadenza = p.getDataScadenza();
						p.setDataScadenza(PagoPaUtil.calcolaDataScadenza(nuovaDataNotifica, "R", p.getTipoPagamento(), p.getTipoRiduzione(), -1));
						PagoPaUtil.salvaStorico(db, -1, p.getIdSanzione(), p.getId(), "[ControllaScadenzePagoPA] Tentativo di aggiornamento data scadenza.");
						PagoPaUtil.salvaStoricoOperazioniAutomatiche(db, p.getIdSanzione(), p.getId(), "", "", "[ControllaScadenzePagoPA] Tentativo di aggiornamento data scadenza.");
						p.update(db, -1);
						p.aggiornaDovuto(db, -1);
						if (p.getEsitoInvio().equalsIgnoreCase("OK")) {
							messaggio += "[ControllaScadenzePagoPA] [" + tipologiaAltroPagatore + " non notificato, " + tipologiaQuestoPagatore + " non notificato] Data scadenza aggiornata.";
						} else {
							messaggio += "[ControllaScadenzePagoPA] [" + tipologiaAltroPagatore + " non notificato, " + tipologiaQuestoPagatore + " non notificato] Data scadenza non aggiornata. Motivazione: " + PagoPaUtil.fixDescrizioneErrore(p.getDescrizioneErrore()) + ".";
							p.setDataScadenza(vecchiaDataScadenza);
							p.update(db, -1);
						}
						PagoPaUtil.salvaStoricoOperazioniAutomatiche(db, p.getIdSanzione(), p.getId(), vecchiaDataScadenza, p.getDataScadenza(), messaggio);

						JSONObject jsonAvviso = new JSONObject();
						jsonAvviso.put("Id sanzione", p.getIdSanzione());
						jsonAvviso.put("IUV", p.getIdentificativoUnivocoVersamento());
						jsonAvviso.put("Vecchia data scadenza", vecchiaDataScadenza);
						jsonAvviso.put("Nuova data scadenza", p.getDataScadenza());
						jsonAvviso.put("Esito operazione", p.getEsitoInvio());
						jsonAvviso.put("Messaggio", messaggio);
						jsonAvvisi.put(jsonAvviso);

					}


				}

			}
			
			// GESTIONE ANNULLAMENTO AVVISI SCADUTI
			
			pst = db.prepareStatement("select * from pagopa_get_avvisi_scaduti();");
			rs = pst.executeQuery();

			while (rs.next()) {

				Pagamento p = new Pagamento(db, rs.getInt("id_pagamento"));

				AnagraficaPagatore trasgressore = new AnagraficaPagatore(db, p.getIdSanzione(), "T");
				AnagraficaPagatore obbligato = new AnagraficaPagatore(db, p.getIdSanzione(), "O");

				String tipologiaQuestoPagatore = null;

				if (p.getPagatore().getId() == trasgressore.getId()){
					tipologiaQuestoPagatore = "TRASGRESSORE";
				}
				else if (p.getPagatore().getId() == obbligato.getId()){
					tipologiaQuestoPagatore = "OBBLIGATO";
				}

				String messaggio = "";
				PagoPaUtil.salvaStorico(db, -1, p.getIdSanzione(), p.getId(), "[ControllaScadenzePagoPA] Tentativo di annullamento IUV.");
				PagoPaUtil.salvaStoricoOperazioniAutomatiche(db, p.getIdSanzione(), p.getId(), "", "", "[ControllaScadenzePagoPA] Tentativo di annullamento IUV.");
				p.annullaDovuto(db, -1);
				if (p.getEsitoInvio().equalsIgnoreCase("OK")) {
					p.delete(db, -1);
					messaggio += "[ControllaScadenzePagoPA] [" + tipologiaQuestoPagatore + " scaduto] Avviso di pagamento annullato.";
				} else
					messaggio += "[ControllaScadenzePagoPA] [" + tipologiaQuestoPagatore + " scaduto] Avviso di pagamento non annullato. Motivazione: " + PagoPaUtil.fixDescrizioneErrore(p.getDescrizioneErrore()) + ".";
				PagoPaUtil.salvaStoricoOperazioniAutomatiche(db, p.getIdSanzione(), p.getId(), "", "", messaggio);

				JSONObject jsonAvviso = new JSONObject();
				jsonAvviso.put("Id sanzione", p.getIdSanzione());
				jsonAvviso.put("IUV", p.getIdentificativoUnivocoVersamento());
				jsonAvviso.put("Esito operazione", p.getEsitoInvio());
				jsonAvviso.put("Messaggio", messaggio);
				jsonAvvisi.put(jsonAvviso);
			}

			jsonFinale.put("Numero avvisi coinvolti", jsonAvvisi.length());       

			PagoPaUtil.salvaStoricoOperazioniAutomatiche(db, -1, -1, "", "", "[ControllaScadenzePagoPA] Fine.");

		} catch (Exception e) {
			logger.severe("[ControllaScadenzePagoPA] Si e' verificata un'eccezione nel controllo delle scadenze.");
			e.printStackTrace();

			jsonFinale.put("Esito operazione", "KO");       
			jsonFinale.put("Errore", e.getMessage());
		} finally {
			if (cp != null) {
				if (db != null) {
					cp.free(db, null);
				}
			}
			jsonFinale.put("Lista avvisi", jsonAvvisi);
			response.setContentType("Application/JSON");
			response.getWriter().write(jsonFinale.toString());
		}
	}
}