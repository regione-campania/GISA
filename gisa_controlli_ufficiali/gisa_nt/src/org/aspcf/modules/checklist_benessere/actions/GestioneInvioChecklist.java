/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package org.aspcf.modules.checklist_benessere.actions;
import it.izs.ws.WsPost;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.aspcf.modules.checklist_benessere.util.ChecklistUtil;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.json.JSONArray;
import org.json.JSONObject;

import com.darkhorseventures.framework.actions.ActionContext;


public final class GestioneInvioChecklist extends CFSModule {
	Logger logger = Logger.getLogger("MainLogger");

	public String executeCommandInviaChecklist(ActionContext context) {

		Connection db = null;

		int idIstanza = Integer.parseInt(context.getRequest().getParameter("id"));
		int idControllo = Integer.parseInt(context.getRequest().getParameter("idControllo"));

		String esitoInvio[] = {"", "", "", ""};

		try {
			db = this.getConnection(context);

			esitoInvio = gestioneInvioChecklist(db, idIstanza, -1, getUserId(context));

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e); 
			e.printStackTrace();
		} finally{
			freeConnection(context, db);
		}

		context.getRequest().setAttribute("idControllo", String.valueOf(idControllo));
		context.getRequest().setAttribute("idBdn", esitoInvio[1]);
		context.getRequest().setAttribute("esitoImport", esitoInvio[2]);
		context.getRequest().setAttribute("descrizioneErrore", esitoInvio[3]);

		return "InvioOK";

	}

	private String[] gestioneInvioChecklist(Connection db, int idIstanza, int idInvioMassivo, int idUtente) throws SQLException{

		int idBdn = -1;
		String esitoImport = null;
		String descrizioneErrore = null;

		String response = "";

		System.out.println(" ------ INVIO DATI A BDN id istanza "+idIstanza+" -----"); 

		// ----------------------------------------- CHECKLIST

		response= invioChecklist(db, idUtente, idIstanza);

		if (response==null || response.equals("")){
			esitoImport = "KO";
			descrizioneErrore = "NESSUNA RISPOSTA DALLA COOPERAZIONE APPLICATIVA";
		}
		else {

			try {idBdn = Integer.parseInt(ChecklistUtil.estraiDaPattern("<caId>", "</caId>", response));} catch (Exception e) {}
			try {descrizioneErrore = ChecklistUtil.estraiDaPattern("<faultstring>", "</faultstring>", response);} catch (Exception e) {}

			if (idBdn>0 || descrizioneErrore.equalsIgnoreCase("Record presente in anagrafe"))
				esitoImport = "OK";
			else
				esitoImport = "KO";
		}

		//AGGIORNA ISTANZA: ID BDN, ESITO, DESCRIZIONE ERRORE, DATA, ID UTENTE
		aggiornaDatiInvioIstanza(db, idIstanza, idBdn, esitoImport, descrizioneErrore, idInvioMassivo, idUtente);

		//SE ESITO = KO, AGGIORNA ISTANZA: BOZZA TRUE
		if (esitoImport==null || !esitoImport.equals("OK"))
			aggiornaStatoIstanza(db, idIstanza, true, idUtente);

		String esitoInvio[] = {String.valueOf(idIstanza), String.valueOf(idBdn), esitoImport, descrizioneErrore};
		return esitoInvio;

	}

	private void aggiornaStatoIstanza(Connection db, int idIstanza, boolean bozza, int userId) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from chk_bns_mod_ist_aggiorna_stato(?, ?, ?)");
		int i = 0;
		pst.setInt(++i, idIstanza);
		pst.setBoolean(++i, bozza);
		pst.setInt(++i, userId);
		pst.executeQuery();
	}

	private void aggiornaDatiInvioIstanza(Connection db, int idIstanza, int idBdn, String esitoImport, String descrizioneErrore, int idInvioMassivo, int userId) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from chk_bns_mod_ist_aggiorna_invio(?, ?, ?, ?, ?, ?)");
		int i = 0; 
		pst.setInt(++i, idIstanza);
		pst.setInt(++i, idBdn);
		pst.setString(++i, esitoImport);
		pst.setString(++i, descrizioneErrore);
		pst.setInt(++i, idInvioMassivo);
		pst.setInt(++i, userId);
		pst.executeQuery();
	}

	private String invioChecklist(Connection db, int userId, int idIstanza) throws SQLException{

		WsPost ws = new WsPost();
		String envelope = null;
		String response = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		logger.info(" ------ INVIO DATI invioChecklist A BDN id istanza "+idIstanza+" -----");
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_BA_BDN"));

		pst = db.prepareStatement("select * from get_chiamata_ws_insert_ba_sa(?, ?, ?)");
		pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_BDN"));
		pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_BDN"));
		pst.setInt(3, idIstanza);
		logger.info(" ------ INVIO DATI invioChecklist A BDN dbi: "+pst.toString()+" -----");
		rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);

		ws.setWsRequest(envelope);
		response= ws.post(db, userId);

		return response;
	}


	public String executeCommandInvioMassivo(ActionContext context) {

		String dataInizioBA = context.getRequest().getParameter("searchtimestampInizio");
		String dataFineBA = context.getRequest().getParameter("searchtimestampFine");

		String dataInizioSA = context.getRequest().getParameter("searchtimestampInizio_b11");
		String dataFineSA = context.getRequest().getParameter("searchtimestampFine_b11");

		int totInvii = 0;
		int totInviiOK = 0;
		int totInviiKO = 0;
		int idInvioMassivo = -1;

		Connection db = null;

		try {
			db = this.getConnection(context);
			PreparedStatement pst = null;
			ResultSet rs = null;
			if (dataInizioBA!=null) {

				pst = db.prepareStatement("select log_invio_massivo_ba_sa_inserisci from log_invio_massivo_ba_sa_inserisci(?, ?, ?, ?)");
				pst.setString(1, "ba");
				pst.setString(2, dataInizioBA);
				pst.setString(3, dataFineBA);
				pst.setInt(4, getUserId(context));

				rs = pst.executeQuery();
				if (rs.next())
					idInvioMassivo = rs.getInt("log_invio_massivo_ba_sa_inserisci");

				pst = db.prepareStatement("select * from bdn_cu_chiusi_benessere_animale where 1 = 1 and data_controllo between to_date(?,'DD/MM/YYYY') and to_date(?, 'DD/MM/YYYY')");
				pst.setString(1, dataInizioBA);
				pst.setString(2, dataFineBA);
			}
			else {

				pst = db.prepareStatement("select log_invio_massivo_ba_sa_inserisci from log_invio_massivo_ba_sa_inserisci(?, ?, ?, ?)");
				pst.setString(1, "sa");
				pst.setString(2, dataInizioSA);
				pst.setString(3, dataFineSA);
				pst.setInt(4, getUserId(context));

				rs = pst.executeQuery();
				if (rs.next())
					idInvioMassivo = rs.getInt("log_invio_massivo_ba_sa_inserisci");

				pst = db.prepareStatement("select * from bdn_cu_chiusi_sicurezza_alimentare_b11 where 1 = 1 and data_controllo between to_date(?,'DD/MM/YYYY') and to_date(?, 'DD/MM/YYYY')");
				pst.setString(1, dataInizioSA);
				pst.setString(2, dataFineSA);
			}

			rs = pst.executeQuery();
			while (rs.next()){
				totInvii++;

				String esitoInvio[] = {"", "", "", ""};
				esitoInvio = gestioneInvioChecklist(db, rs.getInt("id_chk_bns_mod_ist"), idInvioMassivo, getUserId(context));

				if ( esitoInvio[2]!=null && esitoInvio[2].equals("OK"))
					totInviiOK++;
				else
					totInviiKO++;

			}

			pst = db.prepareStatement("select * from log_invio_massivo_ba_sa_aggiorna(?, ?, ?, ?)");
			pst.setInt(1, idInvioMassivo);
			pst.setInt(2, totInvii);
			pst.setInt(3, totInviiOK);
			pst.setInt(4, totInviiKO);
			pst.executeQuery();

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e); 
			e.printStackTrace();
		} finally{
			freeConnection(context, db);
		}

		context.getRequest().setAttribute("idInvioMassivo", idInvioMassivo);
		return executeCommandDettaglioInvioMassivo(context);

	}

	public String executeCommandDettaglioInvioMassivo(ActionContext context) {

		Connection db = null;

		int idInvioMassivo = -1;

		try {idInvioMassivo = Integer.parseInt(context.getRequest().getParameter("idInvioMassivo"));} catch (Exception e) {}

		if (idInvioMassivo == -1)
			idInvioMassivo = (Integer) context.getRequest().getAttribute("idInvioMassivo");

		ArrayList<String> listaInvii = new ArrayList<String>();
		int totInvii = 0;
		int totInviiOK = 0;
		int totInviiKO = 0;
		String tipoBaSa = null;
		String dataDa = null;
		String dataA = null;

		try {
			db = this.getConnection(context);
			PreparedStatement pst = null;
			ResultSet rs = null;

			pst = db.prepareStatement("select * from log_invio_massivo_ba_sa where id = ?");
			pst.setInt(1, idInvioMassivo);
			rs = pst.executeQuery();
			if (rs.next()) {
				totInvii = rs.getInt("tot_invii");
				totInviiOK = rs.getInt("tot_ok");
				totInviiKO = rs.getInt("tot_ko");
				tipoBaSa = rs.getString("tipo_ba_sa");
				dataDa = rs.getString("data_da");
				dataA = rs.getString("data_a");

			}

			pst = db.prepareStatement("select log.data_invio, ist.idcu, t.assigned_date, o.account_number, log.tipo_checklist, log.esito, log.descrizione_errore, log.id_utente from log_invio_massivo_ba_sa massivo join log_invio_ba_sa log on massivo.id = log.id_invio_massivo join chk_bns_mod_ist ist on ist.id = log.id_chk_bns_mod_ist join ticket t on t.ticketid = ist.idcu join organization o on o.org_id = t.org_id where massivo.id = ?");
			pst.setInt(1, idInvioMassivo);
			rs = pst.executeQuery();
			while (rs.next()) {
				String result = rs.getString("data_invio")+";;"+rs.getString("idcu")+";;"+rs.getString("assigned_date")+";;"+rs.getString("account_number")+";;"+rs.getString("tipo_checklist")+";;"+rs.getString("esito")+";;"+rs.getString("descrizione_errore")+";;"+rs.getString("id_utente");
				listaInvii.add(result);
			}
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e); 
			e.printStackTrace();
		} finally{
			freeConnection(context, db);
		}

		context.getRequest().setAttribute("listaInvii", listaInvii);
		context.getRequest().setAttribute("totInvii", String.valueOf(totInvii));
		context.getRequest().setAttribute("totInviiOK", String.valueOf(totInviiOK));
		context.getRequest().setAttribute("totInviiKO", String.valueOf(totInviiKO));
		context.getRequest().setAttribute("tipoBaSa", tipoBaSa);
		context.getRequest().setAttribute("dataDa", dataDa);
		context.getRequest().setAttribute("dataA", dataA);
		context.getRequest().setAttribute("idInvioMassivo", String.valueOf(idInvioMassivo));

		String layout = context.getRequest().getParameter("layout");

		if (layout!= null && layout.equals("style"))
			return "DettaglioInvioMassivoStyleOK";
		else
			return "DettaglioInvioMassivoOK";

	}

	public String executeCommandInviaChecklistBiosicurezza(ActionContext context) {

		Connection db = null;

		int idIstanza = Integer.parseInt(context.getRequest().getParameter("id"));
		int idControllo = Integer.parseInt(context.getRequest().getParameter("idControllo"));

		String esitoInvio[] = {"", "", "", ""};

		try {
			db = this.getConnection(context);

			esitoInvio = gestioneInvioChecklistBiosicurezza(db, idIstanza, -1, getUserId(context)); 

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e); 
			e.printStackTrace();
		} finally{
			freeConnection(context, db);
		}

		context.getRequest().setAttribute("idControllo", String.valueOf(idControllo));
		context.getRequest().setAttribute("idEsitoClassyfarm", esitoInvio[1]);
		context.getRequest().setAttribute("descrizioneErroreClassyfarm", esitoInvio[2]);
		context.getRequest().setAttribute("descrizioneMessaggioClassyfarm", esitoInvio[3]);

		return "InvioBiosicurezzaOK";

	}

	private String[] gestioneInvioChecklistBiosicurezza(Connection db, int idIstanza, int idInvioMassivo, int idUtente) throws SQLException{

		int idEsitoClassyfarm = -1;
		String descrizioneErroreClassyfarm = "";
		String descrizioneMessaggioClassyfarm = "";

		String response = "";

		System.out.println(" ------ INVIO DATI A CLASSYFARM id istanza "+idIstanza+" -----"); 

		// ----------------------------------------- CHECKLIST

		response= invioChecklistBiosicurezza(db, idUtente, idIstanza);

		if (response==null || response.equals("")){
			idEsitoClassyfarm = -1;
			descrizioneErroreClassyfarm = "NESSUNA RISPOSTA DALLA COOPERAZIONE APPLICATIVA";
		}
		else {
			JSONObject jsonEsito;
			try {
				jsonEsito = new JSONObject(response);

				try {idEsitoClassyfarm = (Integer) jsonEsito.get("esito");} catch (Exception e) {}
				try {descrizioneMessaggioClassyfarm = (String) jsonEsito.get("message");} catch (Exception e) {}
				try {
					JSONArray jsonErrore = (JSONArray) jsonEsito.get("Errore");
					descrizioneErroreClassyfarm = jsonErrore.toString();
				} catch (Exception e) {}
			}
			catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}	
		
		//Fixo esito se il messaggio dice che la checklist e' gia' presente in classyfarm
		if (descrizioneMessaggioClassyfarm.contains("already exist. Change operation type for update it."))
			idEsitoClassyfarm = 0;		
		
		//AGGIORNA ISTANZA: ID BDN, ESITO, DESCRIZIONE ERRORE, DATA, ID UTENTE
		aggiornaDatiInvioIstanzaBiosicurezza(db, idIstanza, idEsitoClassyfarm, descrizioneErroreClassyfarm, descrizioneMessaggioClassyfarm, idInvioMassivo, idUtente);

		//SE ESITO = KO, AGGIORNA ISTANZA: BOZZA TRUE
		if (idEsitoClassyfarm!=0)
			aggiornaStatoIstanzaBiosicurezza(db, idIstanza, true, idUtente);

		String esitoInvio[] = {String.valueOf(idIstanza), String.valueOf(idEsitoClassyfarm), descrizioneErroreClassyfarm, descrizioneMessaggioClassyfarm};
		return esitoInvio;
	}

	private String invioChecklistBiosicurezza(Connection db, int userId, int idIstanza) throws SQLException{

		WsPost ws = new WsPost();
		String envelope = null;
		String response = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		String token = gestioneGenerazioneTokenClassyfarm(db, userId);

		logger.info(" ------ INVIO DATI invioChecklist A CLASSYFARM id istanza "+idIstanza+" -----");
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_CLASSYFARM_SEND_CHECKLIST"));

		pst = db.prepareStatement("select * from get_chiamata_ws_insert_biosicurezza(?)");
		pst.setInt(1, idIstanza);

		logger.info(" ------ INVIO DATI invioChecklist A CLASSYFARM dbi: "+pst.toString()+" -----");
		rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);

		ws.setWsRequest(envelope);
		response= ws.postJSONWithApiKeyToken(db, userId, token); 

		return response;
	}
	
	public String executeCommandInviaChecklistFarmacosorveglianza(ActionContext context) {

		Connection db = null;

		int idIstanza = Integer.parseInt(context.getRequest().getParameter("id"));
		int idControllo = Integer.parseInt(context.getRequest().getParameter("idControllo"));

		String esitoInvio[] = {"", "", "", ""};

		try {
			db = this.getConnection(context);

			esitoInvio = gestioneInvioChecklistFarmacosorveglianza(db, idIstanza, -1, getUserId(context)); 

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e); 
			e.printStackTrace();
		} finally{
			freeConnection(context, db);
		}

		context.getRequest().setAttribute("idControllo", String.valueOf(idControllo));
		context.getRequest().setAttribute("idEsitoClassyfarm", esitoInvio[1]);
		context.getRequest().setAttribute("descrizioneErroreClassyfarm", esitoInvio[2]);
		context.getRequest().setAttribute("descrizioneMessaggioClassyfarm", esitoInvio[3]);

		return "InvioFarmacosorveglianzaOK";

	}

	private String[] gestioneInvioChecklistFarmacosorveglianza(Connection db, int idIstanza, int idInvioMassivo, int idUtente) throws SQLException{

		int idEsitoClassyfarm = -1;
		String descrizioneErroreClassyfarm = "";
		String descrizioneMessaggioClassyfarm = "";

		String response = "";

		System.out.println(" ------ INVIO DATI A CLASSYFARM id istanza "+idIstanza+" -----"); 

		// ----------------------------------------- CHECKLIST

		response= invioChecklistFarmacosorveglianza(db, idUtente, idIstanza);

		if (response==null || response.equals("")){
			idEsitoClassyfarm = -1;
			descrizioneErroreClassyfarm = "NESSUNA RISPOSTA DALLA COOPERAZIONE APPLICATIVA";
		}
		else {
			JSONObject jsonEsito;
			try {
				jsonEsito = new JSONObject(response);

				try {idEsitoClassyfarm = (Integer) jsonEsito.get("esito");} catch (Exception e) {}
				try {descrizioneMessaggioClassyfarm = (String) jsonEsito.get("message");} catch (Exception e) {}
				try {
					JSONArray jsonErrore = (JSONArray) jsonEsito.get("Errore");
					descrizioneErroreClassyfarm = jsonErrore.toString();
				} catch (Exception e) {}
			}
			catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}	
		
		//Fixo esito se il messaggio dice che la checklist e' gia' presente in classyfarm
		if (descrizioneMessaggioClassyfarm.contains("already exist. Change operation type for update it."))
			idEsitoClassyfarm = 0;
				
		//AGGIORNA ISTANZA: ID BDN, ESITO, DESCRIZIONE ERRORE, DATA, ID UTENTE
		aggiornaDatiInvioIstanzaFarmacosorveglianza(db, idIstanza, idEsitoClassyfarm, descrizioneErroreClassyfarm, descrizioneMessaggioClassyfarm, idInvioMassivo, idUtente);

		//SE ESITO = KO, AGGIORNA ISTANZA: BOZZA TRUE
		if (idEsitoClassyfarm!=0)
			aggiornaStatoIstanzaFarmacosorveglianza(db, idIstanza, true, idUtente);

		String esitoInvio[] = {String.valueOf(idIstanza), String.valueOf(idEsitoClassyfarm), descrizioneErroreClassyfarm, descrizioneMessaggioClassyfarm};
		return esitoInvio;
	}

	private String invioChecklistFarmacosorveglianza(Connection db, int userId, int idIstanza) throws SQLException{

		WsPost ws = new WsPost();
		String envelope = null;
		String response = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		String token = gestioneGenerazioneTokenClassyfarm(db, userId);

		logger.info(" ------ INVIO DATI invioChecklist A CLASSYFARM id istanza "+idIstanza+" -----");
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_CLASSYFARM_SEND_CHECKLIST"));

		pst = db.prepareStatement("select * from get_chiamata_ws_insert_farmacosorveglianza(?)");
		pst.setInt(1, idIstanza);

		logger.info(" ------ INVIO DATI invioChecklist A CLASSYFARM dbi: "+pst.toString()+" -----");
		rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);

		ws.setWsRequest(envelope);
		response= ws.postJSONWithApiKeyToken(db, userId, token); 

		return response;
	}
	
	

	private String gestioneGenerazioneTokenClassyfarm(Connection db, int userId) throws SQLException {

		WsPost ws = new WsPost();
		String envelope = null;
		String response = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		logger.info(" ------ INVIO DATI generazioneToken A CLASSYFARM -----");
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_CLASSYFARM_LOGIN"));

		pst = db.prepareStatement("select * from get_chiamata_ws_login_classyfarm(?, ?)");
		pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_CLASSYFARM"));
		pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_CLASSYFARM"));
		logger.info(" ------ INVIO DATI generazioneToken A CLASSYFARM dbi: "+pst.toString()+" -----");
		rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);

		ws.setWsRequest(envelope);
		response= ws.post(db, userId);

		String token = "";

		try {
			JSONObject jsonResponse = new JSONObject(response);
			token = (String) jsonResponse.get("Token");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info(" ------ INVIO DATI generazioneToken A CLASSYFARM token prodotto: "+token+" -----");

		return token;
	}
	
	private void aggiornaStatoIstanzaBiosicurezza(Connection db, int idIstanza, boolean bozza, int userId) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from biosicurezza_ist_aggiorna_stato(?, ?, ?)");
		int i = 0;
		pst.setInt(++i, idIstanza);
		pst.setBoolean(++i, bozza);
		pst.setInt(++i, userId);
		pst.executeQuery();
	}
	
	private void aggiornaDatiInvioIstanzaBiosicurezza(Connection db, int idIstanza, int idEsitoClassyfarm, String descrizioneErroreClassyfarm, String descrizioneMessaggioClassyfarm, int idInvioMassivo, int userId) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from biosicurezza_ist_aggiorna_invio(?, ?, ?, ?, ?, ?)");
		int i = 0; 
		pst.setInt(++i, idIstanza);
		pst.setInt(++i, idEsitoClassyfarm);
		pst.setString(++i, descrizioneErroreClassyfarm);
		pst.setString(++i, descrizioneMessaggioClassyfarm);
		pst.setInt(++i, idInvioMassivo);
		pst.setInt(++i, userId);
		pst.executeQuery();
	}
	
	private void aggiornaStatoIstanzaFarmacosorveglianza(Connection db, int idIstanza, boolean bozza, int userId) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from farmacosorveglianza_ist_aggiorna_stato(?, ?, ?)");
		int i = 0;
		pst.setInt(++i, idIstanza);
		pst.setBoolean(++i, bozza);
		pst.setInt(++i, userId);
		pst.executeQuery();
	}
	
	private void aggiornaDatiInvioIstanzaFarmacosorveglianza(Connection db, int idIstanza, int idEsitoClassyfarm, String descrizioneErroreClassyfarm, String descrizioneMessaggioClassyfarm, int idInvioMassivo, int userId) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from farmacosorveglianza_ist_aggiorna_invio(?, ?, ?, ?, ?, ?)");
		int i = 0; 
		pst.setInt(++i, idIstanza);
		pst.setInt(++i, idEsitoClassyfarm);
		pst.setString(++i, descrizioneErroreClassyfarm);
		pst.setString(++i, descrizioneMessaggioClassyfarm);
		pst.setInt(++i, idInvioMassivo);
		pst.setInt(++i, userId);
		pst.executeQuery();
	}
}
