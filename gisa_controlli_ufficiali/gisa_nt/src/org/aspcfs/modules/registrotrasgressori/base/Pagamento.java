/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrotrasgressori.base;

import it.izs.ws.WsPost;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Logger;

import org.apache.commons.lang.StringEscapeUtils;
import org.aspcfs.modules.registrotrasgressori.utils.PagoPaUtil;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.json.JSONObject;

public class Pagamento  {
	
	Logger logger = Logger.getLogger("MainLogger");

	private int id = -1;
	private int idSanzione = -1;
	private AnagraficaPagatore pagatore = null;
	
	private int indice = -1;
	
	private String tipoPagamento = null;
	private String dataScadenza = null;
	private String tipoVersamento = null;
	private String identificativoUnivocoDovuto = null;
	private String importoSingoloVersamento = null;
	private String identificativoTipoDovuto = null; 
	private String causaleVersamento = null;
	private String datiSpecificiRiscossione = null;
	private String tipoRiduzione = null;
	
	private int numeroRate = -1;
	
	private int enteredBy;
	private int modifiedBy;
	private int inviatoBy;
	private boolean inviato = false;
	private Timestamp dataInvio = null;
	private String esitoInvio = null;
	private String descrizioneErrore = null;
	private String statoPagamento = null;
	
	private String identificativoUnivocoVersamento = null;
	private String urlFileAvviso = null;
	private Timestamp dataGenerazioneIuv = null;

	private String denominazioneAttestante = null;
	private String denominazioneBeneficiario = null;
	private String esitoSingoloPagamento = null;
	private String identificativoUnivocoRiscossione = null;
	private String singoloImportoPagato = null;
	private String dataEsitoSingoloPagamento = null;
	private String codiceEsitoPagamento = null;
	
	private String codiceAvviso = null;
	private boolean aggiornatoConPagoPa = false;
	
	public static final String PAGAMENTO_IN_CORSO = "PAGAMENTO IN CORSO";
	public static final String PAGAMENTO_COMPLETATO = "PAGAMENTO COMPLETATO";
	public static final String PAGAMENTO_NON_INIZIATO = "PAGAMENTO NON INIZIATO";
	
	public Pagamento(){
		
	}

	public Pagamento(ResultSet rs, Connection db) throws SQLException {
		buildRecord(rs, db);
	}
	
	private void buildRecord(ResultSet rs, Connection db) throws SQLException {
		
	this.id = rs.getInt("id");
	this.idSanzione = rs.getInt("id_sanzione");
	
	AnagraficaPagatore pag = new AnagraficaPagatore(db, rs.getInt("id_pagatore"));
	this.pagatore = pag;
	
	this.indice = rs.getInt("indice");
	
	this.tipoPagamento = rs.getString("tipo_pagamento");
	this.dataScadenza = rs.getString("data_scadenza");
	this.tipoVersamento = rs.getString("tipo_versamento");
	this.identificativoUnivocoDovuto = rs.getString("identificativo_univoco_dovuto");
	this.importoSingoloVersamento = rs.getString("importo_singolo_versamento");
	this.identificativoTipoDovuto = rs.getString("identificativo_tipo_dovuto"); 
	this.causaleVersamento = rs.getString("causale_versamento");
	this.datiSpecificiRiscossione = rs.getString("dati_specifici_riscossione");
	this.tipoRiduzione = rs.getString("tipo_riduzione");
	
	this.numeroRate = rs.getInt("numero_rate");
	
	this.enteredBy = rs.getInt("entered_by");
	this.modifiedBy = rs.getInt("modified_by");
	this.inviatoBy = rs.getInt("inviato_by");
	this.inviato = rs.getBoolean("inviato");
	this.dataInvio = rs.getTimestamp("data_invio");
	this.esitoInvio = rs.getString("esito_invio");
	this.descrizioneErrore = rs.getString("descrizione_errore");

	this.identificativoUnivocoVersamento = rs.getString("identificativo_univoco_versamento");
	this.urlFileAvviso = rs.getString("url_file_avviso");
	this.dataGenerazioneIuv = rs.getTimestamp("data_generazione_iuv");

	this.statoPagamento = rs.getString("stato_pagamento");
	
	this.denominazioneAttestante = rs.getString("denominazione_attestante");
	this.denominazioneBeneficiario = rs.getString("denominazione_beneficiario");
	this.esitoSingoloPagamento = rs.getString("esito_singolo_pagamento");
	this.identificativoUnivocoRiscossione = rs.getString("identificativo_univoco_riscossione");
	this.singoloImportoPagato = rs.getString("singolo_importo_pagato");
	this.dataEsitoSingoloPagamento = rs.getString("data_esito_singolo_pagamento");
	this.codiceEsitoPagamento = rs.getString("codice_esito_pagamento");
	
	this.codiceAvviso = rs.getString("codice_avviso");
	this.aggiornatoConPagoPa = rs.getBoolean("aggiornato_con_pagopa");

	}


	public Pagamento(Connection db, int idPagamento) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from pagopa_pagamenti where id = ?");
		pst.setInt(1, idPagamento);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			buildRecord(rs, db);
	}


	public Pagamento(Connection db, String IUV) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from pagopa_pagamenti where identificativo_univoco_versamento = ?");
		pst.setString(1, IUV);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			buildRecord(rs, db);	}

	public Logger getLogger() {
		return logger;
	}


	public void setLogger(Logger logger) {
		this.logger = logger;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getIdSanzione() {
		return idSanzione;
	}


	public void setIdSanzione(int idSanzione) {
		this.idSanzione = idSanzione;
	}

	public AnagraficaPagatore getPagatore() {
		return pagatore;
	}


	public void setPagatore(AnagraficaPagatore pagatore) {
		this.pagatore = pagatore;
	}


	public int getIndice() {
		return indice;
	}


	public void setIndice(int indice) {
		this.indice = indice;
	}


	public String getTipoPagamento() {
		return tipoPagamento;
	}


	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}


	public String getDataScadenza() {
		return dataScadenza;
	}


	public void setDataScadenza(String dataScadenza) {
		this.dataScadenza = dataScadenza;
	}


	public String getTipoVersamento() {
		return tipoVersamento;
	}


	public void setTipoVersamento(String tipoVersamento) {
		this.tipoVersamento = tipoVersamento;
	}


	public String getIdentificativoUnivocoDovuto() {
		return identificativoUnivocoDovuto;
	}


	public void setIdentificativoUnivocoDovuto(String identificativoUnivocoDovuto) {
		this.identificativoUnivocoDovuto = identificativoUnivocoDovuto;
	}


	public String getImportoSingoloVersamento() {
		return importoSingoloVersamento;
	}


	public void setImportoSingoloVersamento(String importoSingoloVersamento) {
		this.importoSingoloVersamento = importoSingoloVersamento;
	}


	public String getIdentificativoTipoDovuto() {
		return identificativoTipoDovuto;
	}


	public void setIdentificativoTipoDovuto(String identificativoTipoDovuto) {
		this.identificativoTipoDovuto = identificativoTipoDovuto;
	}


	public String getCausaleVersamento() {
		return causaleVersamento;
	}


	public void setCausaleVersamento(String causaleVersamento) {
		this.causaleVersamento = causaleVersamento;
	}


	public String getDatiSpecificiRiscossione() {
		return datiSpecificiRiscossione;
	}


	public String getDescrizioneErrore() {
		return descrizioneErrore;
	}


	public void setDescrizioneErrore(String descrizioneErrore) {
		this.descrizioneErrore = descrizioneErrore;
	}


	public void setDatiSpecificiRiscossione(String datiSpecificiRiscossione) {
		this.datiSpecificiRiscossione = datiSpecificiRiscossione;
	}


	public int getEnteredBy() {
		return enteredBy;
	}


	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}


	public int getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public int getInviatoBy() {
		return inviatoBy;
	}


	public void setInviatoBy(int inviatoBy) {
		this.inviatoBy = inviatoBy;
	}


	public boolean isInviato() {
		return inviato;
	}


	public void setInviato(boolean inviato) {
		this.inviato = inviato;
	}


	public Timestamp getDataInvio() {
		return dataInvio;
	}


	public void setDataInvio(Timestamp dataInvio) {
		this.dataInvio = dataInvio;
	}


	public String getEsitoInvio() {
		return esitoInvio;
	}


	public void setEsitoInvio(String esitoInvio) {
		this.esitoInvio = esitoInvio;
	}


	public String getIdentificativoUnivocoVersamento() {
		return identificativoUnivocoVersamento;
	}


	public void setIdentificativoUnivocoVersamento(String identificativoUnivocoVersamento) {
		this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
	}


	public String getUrlFileAvviso() {
		return urlFileAvviso;
	}


	public void setUrlFileAvviso(String urlFileAvviso) {
		this.urlFileAvviso = urlFileAvviso;
	}
	
	public static ArrayList<Pagamento> getListaPagamenti (Connection db, int idSanzione, int userId) throws SQLException{
		ArrayList<Pagamento> listaPagamenti = new ArrayList<Pagamento>();
		PreparedStatement pst = db.prepareStatement("select * from pagopa_pagamenti p where p.id_sanzione = ? and p.trashed_date is null order by p.id_pagatore asc, p.indice asc");
		pst.setInt(1, idSanzione);
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			Pagamento pagamento = new Pagamento(rs, db);
			
//			if (!Pagamento.PAGAMENTO_COMPLETATO.equalsIgnoreCase(pagamento.getStatoPagamento()))
//				pagamento.chiediPagati(db, userId);
//			else if (Pagamento.PAGAMENTO_COMPLETATO.equalsIgnoreCase(pagamento.getStatoPagamento()))
//				pagamento.annullaAltriPagamenti(db, userId);
			
			listaPagamenti.add(pagamento);
		}
		return listaPagamenti;
	}

	public static ArrayList<Pagamento> getListaAltriPagamenti (Connection db, int idSanzione, int idPagamento, int userId) throws SQLException{
		ArrayList<Pagamento> listaPagamenti = new ArrayList<Pagamento>();
		PreparedStatement pst = db.prepareStatement("select * from pagopa_pagamenti p where p.id_sanzione = ? and p.id <> ? and (p.tipo_pagamento = 'NO' and p.indice = (select indice from pagopa_pagamenti where id = ?) or (p.tipo_pagamento='PV')) and p.trashed_date is null order by p.id_pagatore asc, p.indice asc");
		pst.setInt(1, idSanzione);
		pst.setInt(2, idPagamento);
		pst.setInt(3, idPagamento);

		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			Pagamento pagamento = new Pagamento(rs, db);
			
			listaPagamenti.add(pagamento);
		}
		return listaPagamenti;
	}

	public String getStatoPagamento() {
		return statoPagamento;
	}

	public void setStatoPagamento(String statoPagamento) {
		this.statoPagamento = statoPagamento;
	}

	public String getDenominazioneAttestante() {
		return denominazioneAttestante;
	}

	public void setDenominazioneAttestante(String denominazioneAttestante) {
		this.denominazioneAttestante = denominazioneAttestante;
	}

	public String getDenominazioneBeneficiario() {
		return denominazioneBeneficiario;
	}

	public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {
		this.denominazioneBeneficiario = denominazioneBeneficiario;
	}

	public String getEsitoSingoloPagamento() {
		return esitoSingoloPagamento;
	}

	public void setEsitoSingoloPagamento(String esitoSingoloPagamento) {
		this.esitoSingoloPagamento = esitoSingoloPagamento;
	}

	public String getIdentificativoUnivocoRiscossione() {
		return identificativoUnivocoRiscossione;
	}

	public void setIdentificativoUnivocoRiscossione(String identificativoUnivocoRiscossione) {
		this.identificativoUnivocoRiscossione = identificativoUnivocoRiscossione;
	}

	public void insert(Connection db, int idUtente) throws SQLException, ParseException {
		PreparedStatement pst = db.prepareStatement("select * from pagopa_genera_pagamento(?, ?, ?, ?, ?, ?, ?, ?, ?)");  
		int i = 0;
		pst.setInt(++i, idSanzione);
		pst.setInt(++i, pagatore.getId());
		pst.setString(++i,  importoSingoloVersamento);
		pst.setString(++i, dataScadenza);
		pst.setString(++i, tipoPagamento);
		pst.setString(++i, tipoRiduzione);
		pst.setInt(++i, indice);
		pst.setInt(++i, numeroRate);
		pst.setInt(++i, idUtente); 
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			JSONObject json = new JSONObject(rs.getString(1));
			this.id = (int) json.get("idInserito");
			this.identificativoUnivocoDovuto = (String) json.get("IUD");
			
		}
	}


	public void importaDovuto(Connection db, int userId) throws SQLException {
		WsPost ws = new WsPost();
		String envelope = null;
		String response = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		logger.info(" ------ INVIO DATI importaDovuto A PAGOPA id pagamento "+id+" -----"); 
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_PAGOPA")); 

		pst = db.prepareStatement("select * from get_chiamata_ws_pagopa_importa_dovuto(?, ?, ?)");
		pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_PAGOPA"));
		pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_PAGOPA"));
		pst.setInt(3, id);
		rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);
		
		ws.setWsRequest(envelope);
		ws.setTIMEOUT_SECONDS(Integer.parseInt(ApplicationProperties.getProperty("TIMEOUT_SECONDS_PAGOPA")));
		response= ws.post(db, userId);
		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}

		updateEsitoImportaDovuto(db, response, userId);
		
		PagoPaUtil.salvaStorico(db, userId, idSanzione, id, "ImportaDovuto");

	}

	public void aggiornaDovuto(Connection db, int userId) throws SQLException{
		WsPost ws = new WsPost();
		String envelope = null;
		String response = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		logger.info(" ------ INVIO DATI aggiornaDovuto A PAGOPA id pagamento "+id+" -----"); 
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_PAGOPA")); 

		pst = db.prepareStatement("select * from get_chiamata_ws_pagopa_importa_dovuto_aggiorna(?, ?, ?)");
		pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_PAGOPA"));
		pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_PAGOPA"));
		pst.setInt(3, id);
		rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);
		
		ws.setWsRequest(envelope);
		ws.setTIMEOUT_SECONDS(Integer.parseInt(ApplicationProperties.getProperty("TIMEOUT_SECONDS_PAGOPA")));
		response= ws.post(db, userId);
		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}

		updateEsitoAggiornaDovuto(db, response, userId);
		
		PagoPaUtil.salvaStorico(db, userId, idSanzione, id, "AggiornaDovuto");
	}
	
	public void annullaDovuto(Connection db, int userId) throws SQLException{ 
		
		if (identificativoUnivocoVersamento!=null) {
			WsPost ws = new WsPost();
			String envelope = null;
			String response = null;
			PreparedStatement pst = null;
			ResultSet rs = null;
	
			logger.info(" ------ INVIO DATI annullaDovuto A PAGOPA id pagamento "+id+" -----"); 
			ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_PAGOPA")); 
	
			pst = db.prepareStatement("select * from get_chiamata_ws_pagopa_importa_dovuto_annulla(?, ?, ?)");
			pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_PAGOPA"));
			pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_PAGOPA"));
			pst.setInt(3, id);
			rs = pst.executeQuery();
			while (rs.next())
				envelope = rs.getString(1);
			
			ws.setWsRequest(envelope);
			ws.setTIMEOUT_SECONDS(Integer.parseInt(ApplicationProperties.getProperty("TIMEOUT_SECONDS_PAGOPA")));
			response= ws.post(db, userId);
			try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}

			updateEsitoAnnullaDovuto(db, response, userId);
			
			PagoPaUtil.salvaStorico(db, userId, idSanzione, id, "AnnullaDovuto");
		}
		else {
			this.esitoInvio="OK";
		}
}
	
	public void chiediPagati(Connection db, int userId) throws SQLException{
		WsPost ws = new WsPost();
		String envelope = null;
		String response = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		logger.info(" ------ INVIO DATI ChiediPagatiConRicevuta A PAGOPA id pagamento "+id+" -----"); 
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_PAGOPA")); 

		pst = db.prepareStatement("select * from get_chiamata_ws_pagopa_chiedi_pagati(?, ?, ?)");
		pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_PAGOPA"));
		pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_PAGOPA"));
		pst.setInt(3, id);
		rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);
		
		ws.setWsRequest(envelope);
		ws.setTIMEOUT_SECONDS(Integer.parseInt(ApplicationProperties.getProperty("TIMEOUT_SECONDS_PAGOPA")));
		response= ws.post(db, userId);
		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		
		updateEsitoChiediPagati(db, response, userId);
		PagoPaUtil.salvaStorico(db, userId, idSanzione, id, "ChiediPagati");
	}
	
	public void updateEsitoImportaDovuto(Connection db, String response, int userId) throws SQLException {
		PreparedStatement pst = null;
		if (!inviato && response!=null){
			esitoInvio = getTagValue(response, "esito");
			if (esitoInvio.equalsIgnoreCase("OK")){
				if (identificativoUnivocoVersamento==null)
					identificativoUnivocoVersamento = getTagValue(response, "identificativoUnivocoVersamento");
				if (urlFileAvviso==null)
					urlFileAvviso = getTagValue(response, "urlFileAvviso");
	
				inviato = true;
			} else {
				descrizioneErrore = getTagValue(response, "description");
				if (descrizioneErrore== null || descrizioneErrore.equals(""))
					descrizioneErrore = getTagValue(response, "faultString");
}
		}
			
		pst = db.prepareStatement("update pagopa_pagamenti set esito_invio = ?, descrizione_errore = ?, inviato = ?, identificativo_univoco_versamento = ?, url_file_avviso = ?,  inviato_by = ?, data_invio = now(), note_hd = concat(note_hd, ';', '[', now(), '] [ImportaDovuto] Esito invio aggiornato a " + esitoInvio + " da utente "+userId+"') where id = ?");
		int i = 0;
		pst.setString(++i, esitoInvio);
		pst.setString(++i, descrizioneErrore);
		pst.setBoolean(++i, inviato);
		pst.setString(++i, identificativoUnivocoVersamento);
		pst.setString(++i, urlFileAvviso);
		pst.setInt(++i, userId);
		pst.setInt(++i, id); 
		pst.executeUpdate();
		
		if (urlFileAvviso != null && !"".equals(urlFileAvviso)){
			try {
				PagoPaUtil.inviaADocumentale("", StringEscapeUtils.unescapeHtml(urlFileAvviso), idSanzione, userId);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (identificativoUnivocoVersamento != null && !identificativoUnivocoVersamento.equals("")){
			pst = db.prepareStatement("update pagopa_pagamenti set codice_avviso = ?, data_generazione_iuv = now(), note_hd = concat(note_hd, ';', '[', now(), '] Data generazione IUV modificata da utente "+userId+"') where id = ?");
			pst.setString(1, "3"+identificativoUnivocoVersamento);
			pst.setInt(2, id); 
			pst.executeUpdate();	
		}
	}
	
	public void updateEsitoAggiornaDovuto(Connection db, String response, int userId) throws SQLException {
		PreparedStatement pst = null;
		if (response!=null){
			esitoInvio = getTagValue(response, "esito");
			if (esitoInvio.equalsIgnoreCase("OK")){} else {
				descrizioneErrore = getTagValue(response, "description");
				if (descrizioneErrore== null || descrizioneErrore.equals(""))
					descrizioneErrore = getTagValue(response, "faultString");
			}
		}
			
		pst = db.prepareStatement("update pagopa_pagamenti set esito_invio = ?, descrizione_errore = ?, inviato_by = ?, modified_by = ?, data_invio = now(), note_hd = concat(note_hd, ';', '[', now(), '] [AggiornaDovuto] Esito invio aggiornato a " + esitoInvio + " da utente "+userId+"')  where id = ?");
		int i = 0;
		pst.setString(++i, esitoInvio);
		pst.setString(++i, descrizioneErrore);
		pst.setInt(++i, userId);
		pst.setInt(++i, userId);
		pst.setInt(++i, id); 
		pst.executeUpdate();	
	}

	public void updateEsitoAnnullaDovuto(Connection db, String response, int userId) throws SQLException {
		PreparedStatement pst = null;
		if (response!=null){
			esitoInvio = getTagValue(response, "esito");
			if (esitoInvio.equalsIgnoreCase("OK")){} else {
				descrizioneErrore = getTagValue(response, "description");
				if (descrizioneErrore== null || descrizioneErrore.equals(""))
					descrizioneErrore = getTagValue(response, "faultString");
			}
		}
			
		pst = db.prepareStatement("update pagopa_pagamenti set esito_invio = ?, descrizione_errore = ?, note_hd = concat(note_hd, ';', '[', now(), '] [AnnullaDovuto] Esito invio aggiornato a " + esitoInvio + " da utente "+userId+"') where id = ?");
		int i = 0;
		pst.setString(++i, esitoInvio);
		pst.setString(++i, descrizioneErrore);
		pst.setInt(++i, id); 
		pst.executeUpdate();	
	}
	
	public void updateEsitoChiediPagati(Connection db, String response, int userId) throws SQLException {
		PreparedStatement pst = null;
		String faultCode = null; 
		
		if (response!=null && !response.equals("")){
			statoPagamento = getTagValue(response, "faultString");
			faultCode = getTagValue(response, "faultCode");
			
			String rt = getTagValue(response, "rt");
			byte[] decodedBytes = Base64.getDecoder().decode(rt);
			String decodedString = new String(decodedBytes);
			
			decodedString = decodedString.replaceAll("pay_i:", "");

			denominazioneAttestante = getTagValue(decodedString, "denominazioneAttestante");
			denominazioneBeneficiario = getTagValue(decodedString, "denominazioneBeneficiario");
			esitoSingoloPagamento = getTagValue(decodedString, "esitoSingoloPagamento");
			identificativoUnivocoRiscossione = getTagValue(decodedString, "identificativoUnivocoRiscossione");
			singoloImportoPagato = getTagValue(decodedString, "singoloImportoPagato");
			dataEsitoSingoloPagamento = getTagValue(decodedString, "dataEsitoSingoloPagamento");
			codiceEsitoPagamento = getTagValue(decodedString, "codiceEsitoPagamento");

			if ((faultCode!=null && faultCode.equals("PAA_PAGAMENTO_NON_INIZIATO")))
				statoPagamento = PAGAMENTO_NON_INIZIATO;
			else if ((faultCode!=null && faultCode.equals("PAA_PAGAMENTO_IN_CORSO")))
				statoPagamento = PAGAMENTO_IN_CORSO;
			else if ((statoPagamento==null || statoPagamento.equals("")) && (codiceEsitoPagamento!=null && codiceEsitoPagamento.equals("0"))) 
				statoPagamento = PAGAMENTO_COMPLETATO;
			
			pst = db.prepareStatement("update pagopa_pagamenti set aggiornato_con_pagopa = true where id = ?");
			int i = 0;
			pst.setInt(++i, id); 
			pst.executeUpdate();	
	}
		else {
			pst = db.prepareStatement("update pagopa_pagamenti set aggiornato_con_pagopa = false where id = ?");
			int i = 0;
			pst.setInt(++i, id); 
			pst.executeUpdate();	
			}
		
		if (statoPagamento!=null && !statoPagamento.equals("")) {
			pst = db.prepareStatement("update pagopa_pagamenti set stato_pagamento = ?, denominazione_attestante = ?, denominazione_beneficiario = ?, esito_singolo_pagamento = ?, codice_esito_pagamento = ?, identificativo_univoco_riscossione = ?, singolo_importo_pagato = ?, data_esito_singolo_pagamento = ?, note_hd = concat(note_hd, ';', '[', now(), '] [ChiediPagati] Stato pagamento aggiornato a " + statoPagamento + " da utente "+userId+"') where id = ?");
			int i = 0;
			pst.setString(++i, statoPagamento);
			pst.setString(++i, denominazioneAttestante);
			pst.setString(++i, denominazioneBeneficiario);
			pst.setString(++i, esitoSingoloPagamento);
			pst.setString(++i, codiceEsitoPagamento);
			pst.setString(++i, identificativoUnivocoRiscossione);
			pst.setString(++i, singoloImportoPagato);
			pst.setString(++i, dataEsitoSingoloPagamento);
			pst.setInt(++i, id); 
			pst.executeUpdate();	
			
			if (statoPagamento.equals(PAGAMENTO_COMPLETATO))
				annullaAltriPagamenti(db, userId);
	}
	}
	
	private void annullaAltriPagamenti(Connection db, int userId) throws SQLException {
		ArrayList<Pagamento> listaPagamenti = new ArrayList<Pagamento>();
		listaPagamenti = Pagamento.getListaAltriPagamenti(db, idSanzione, id, userId);
		if (listaPagamenti.size()>0) {
			PagoPaUtil.salvaStorico(db, userId, idSanzione, -1, "Tentativo di annullamento massivo IUV ad eccezione di quello pagato");
	
			for (int i = 0; i<listaPagamenti.size(); i++){
				Pagamento p = (Pagamento) listaPagamenti.get(i);
				p.annullaDovuto(db, userId);
				if ((p.getEsitoInvio().equalsIgnoreCase("OK") || (p.getEsitoInvio().equalsIgnoreCase("KO") && p.getDescrizioneErrore().contains("Dovuto non presente o in pagamento nel database"))))
					p.delete(db, userId);
			}
		}
	}

	public static String getTagValue(String xml, String tagName){
		String esito = "";
	    try {esito =  xml.split("<"+tagName+">")[1].split("</"+tagName+">")[0]; } catch (Exception e) {}
	    return esito;
	}

	public void update(Connection db, int userId) throws SQLException {
		PreparedStatement pst = null;
		pst = db.prepareStatement("update pagopa_pagamenti set data_scadenza = ?, modified_by = ?, note_hd = concat(note_hd, ';', '[', now(), '] Data scadenza modificata da ', data_scadenza, ' a " + dataScadenza + " da utente "+userId+"') where id = ?"); 
		int i = 0;
		pst.setString(++i, dataScadenza);
		pst.setInt(++i, userId); 
		pst.setInt(++i, id); 
		pst.executeUpdate();	
			
	}
	
	public void delete(Connection db, int userId) throws SQLException {
		PreparedStatement pst = null;
		pst = db.prepareStatement("update pagopa_pagamenti set trashed_date = now(), modified_by = ?  where id = ?");
		int i = 0;
		pst.setInt(++i, userId); 
		pst.setInt(++i, id); 
		pst.executeUpdate();	
		
	}

	public String getSingoloImportoPagato() {
		return singoloImportoPagato;
	}

	public void setSingoloImportoPagato(String singoloImportoPagato) {
		this.singoloImportoPagato = singoloImportoPagato;
	}

	public String getDataEsitoSingoloPagamento() {
		return dataEsitoSingoloPagamento;
	}

	public void setDataEsitoSingoloPagamento(String dataEsitoSingoloPagamento) {
		this.dataEsitoSingoloPagamento = dataEsitoSingoloPagamento;
	}

	public Timestamp getDataGenerazioneIuv() {
		return dataGenerazioneIuv;
	}

	public void setDataGenerazioneIuv(Timestamp dataGenerazioneIuv) {
		this.dataGenerazioneIuv = dataGenerazioneIuv;
	}

	public String getTipoRiduzione() {
		return tipoRiduzione;
	}

	public void setTipoRiduzione(String tipoRiduzione) {
		this.tipoRiduzione = tipoRiduzione;
	}

	public String getCodiceAvviso() {
		return codiceAvviso;
	}

	public void setCodiceAvviso(String codiceAvviso) {
		this.codiceAvviso = codiceAvviso;
	}

	public boolean isAggiornatoConPagoPa() {
		return aggiornatoConPagoPa;
	}

	public void setAggiornatoConPagoPa(boolean aggiornatoConPagoPa) {
		this.aggiornatoConPagoPa = aggiornatoConPagoPa;
	}

	public int getNumeroRate() {
		return numeroRate;
	}

	public void setNumeroRate(int numeroRate) {
		this.numeroRate = numeroRate;
	}

	public String getCodiceEsitoPagamento() {
		return codiceEsitoPagamento;
	}

	public void setCodiceEsitoPagamento(String codiceEsitoPagamento) {
		this.codiceEsitoPagamento = codiceEsitoPagamento;
	}

}


