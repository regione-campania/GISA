/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrotrasgressori.base;

import it.izs.ws.WsPost;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.aspcfs.modules.util.imports.ApplicationProperties;

import com.darkhorseventures.framework.actions.ActionContext;

public class PagoPa {
	
	Logger logger = Logger.getLogger("MainLogger");

	private int id = -1;
	private int idTrasgressione = -1;
	private int idSanzione = -1;
	private String tipoIdentificativoUnivoco = null;
	private String codiceIdentificativoUnivoco = null;
	private String anagraficaPagatore = null;
	private String indirizzoPagatore = null;
	private String civicoPagatore = null;
	private String capPagatore = null;
	private String localitaPagatore = null;
	private String provinciaPagatore = null;
	private String nazionePagatore = null;
	private String emailPagatore = null;
	
	private int numeroRate = -1;
	private int rataNumero = -1;
	
	private String tipoPagamento = null;
	private String dataPagamento = null;
	private String tipoVersamento = null;
	private String identificativoUnivocoDovuto = null;
	private String importoSingoloVersamento = null;
	private String identificativoTipoDovuto = null; 
	private String causaleVersamento = null;
	private String datiSpecificiRiscossione = null;
	
	private int enteredBy;
	private int modifiedBy;
	private int inviatoBy;
	private boolean inviato = false;
	private Timestamp dataInvio = null;
	private String esitoInvio = null;
	String identificativoUnivocoVersamento = null;
	String urlFileAvviso = null;

	
	public PagoPa(){
		
	}
	
	public PagoPa(Connection db, int idTrasgressione, boolean isTrasgressione, int userId) throws SQLException {
		PreparedStatement pst = null;
		pst = db.prepareStatement("select * from registro_trasgressori_pago_pa where id_trasgressione = ? and trashed_date is null");
		pst.setInt(1, idTrasgressione);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			buildRecord(rs);
		else
			insert(db, idTrasgressione, userId);
	}

	public PagoPa(Connection db, int id) throws SQLException {
		PreparedStatement pst = null;
		pst = db.prepareStatement("select * from registro_trasgressori_pago_pa where id = ? and trashed_date is null");
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			buildRecord(rs);
		}

	private void insert(Connection db, int idTrasgressione, int userId) throws SQLException {
		PreparedStatement pst = null;
		pst = db.prepareStatement("select * from insert_registro_trasgressori_pago_pa(?, ?) ");
		pst.setInt(1, idTrasgressione);
		pst.setInt(2, userId);
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			this.id = rs.getInt(1);
			load(db);
		}
	}
	
	private void delete(Connection db, int userId) throws SQLException{
		PreparedStatement pst = null;
		pst = db.prepareStatement("update registro_trasgressori_pago_pa set trashed_date = now(), modified_by = ? where id = ?");
		pst.setInt(1, userId);
		pst.setInt(2, id);
		pst.executeUpdate();
	}
	
	
	public void insertRata(Connection db, int userId) throws SQLException {
		PreparedStatement pst = null;
		pst = db.prepareStatement("INSERT INTO registro_trasgressori_pago_pa (id_trasgressione, id_sanzione , tipo_identificativo_univoco,  codice_identificativo_univoco, "
				+ "anagrafica_pagatore, indirizzo_pagatore,  civico_pagatore, cap_pagatore , localita_pagatore , provincia_pagatore ,  nazione_pagatore, email_pagatore , "
				+ "tipo_pagamento, data_pagamento , tipo_versamento ,  identificativo_univoco_dovuto,  importo_singolo_versamento,  identificativo_tipo_dovuto,  causale_versamento, "
				+ "dati_specifici_riscossione , entered_by, identificativo_univoco_versamento, url_file_avviso, numero_rate, rata_numero, inviato ) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) returning id as id_inserito;");
		int i = 0;
		pst.setInt(++i, idTrasgressione);
		pst.setInt(++i, idSanzione);
		pst.setString(++i, tipoIdentificativoUnivoco);
		pst.setString(++i, codiceIdentificativoUnivoco);
		pst.setString(++i, anagraficaPagatore);
		pst.setString(++i, indirizzoPagatore);
		pst.setString(++i, civicoPagatore);
		pst.setString(++i, capPagatore);
		pst.setString(++i, localitaPagatore);
		pst.setString(++i, provinciaPagatore);
		pst.setString(++i, nazionePagatore);
		pst.setString(++i, emailPagatore);
		pst.setString(++i, tipoPagamento);
		pst.setString(++i, dataPagamento);
		pst.setString(++i, tipoVersamento);
		pst.setString(++i, identificativoUnivocoDovuto);
		pst.setString(++i, importoSingoloVersamento);
		pst.setString(++i, identificativoTipoDovuto);
		pst.setString(++i, causaleVersamento);
		pst.setString(++i, datiSpecificiRiscossione);
		pst.setInt(++i, userId);
		pst.setString(++i, identificativoUnivocoVersamento);
		pst.setString(++i, urlFileAvviso);
		pst.setInt(++i, numeroRate);
		pst.setInt(++i, rataNumero);
		pst.setBoolean(++i, inviato);

		ResultSet rs = 	pst.executeQuery();
		if (rs.next()){
			this.id = rs.getInt("id_inserito");
		}
	}
	
	public void load(Connection db) throws SQLException{
		PreparedStatement pst = null;
		pst = db.prepareStatement("select * from registro_trasgressori_pago_pa where id = ? and trashed_date is null");
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			buildRecord(rs);
	}

	private void buildRecord(ResultSet rs) throws SQLException {
		id  =rs.getInt("id");
		idTrasgressione  =rs.getInt("id_trasgressione");
		idSanzione  =rs.getInt("id_sanzione");
		tipoIdentificativoUnivoco = rs.getString("tipo_identificativo_univoco");
		codiceIdentificativoUnivoco = rs.getString("codice_identificativo_univoco");
		anagraficaPagatore = rs.getString("anagrafica_pagatore");
		indirizzoPagatore = rs.getString("indirizzo_pagatore");
		civicoPagatore = rs.getString("civico_pagatore");
		capPagatore = rs.getString("cap_pagatore");
		localitaPagatore = rs.getString("localita_pagatore");
		provinciaPagatore = rs.getString("provincia_pagatore");
		nazionePagatore = rs.getString("nazione_pagatore");
		emailPagatore = rs.getString("email_pagatore");
		
		numeroRate  =rs.getInt("numero_rate");
		rataNumero  =rs.getInt("rata_numero");

		tipoPagamento = rs.getString("tipo_pagamento");
		dataPagamento = rs.getString("data_pagamento");
		tipoVersamento = rs.getString("tipo_versamento");
		identificativoUnivocoDovuto = rs.getString("identificativo_univoco_dovuto");
		importoSingoloVersamento = rs.getString("importo_singolo_versamento");
		identificativoTipoDovuto = rs.getString("identificativo_tipo_dovuto");
		causaleVersamento = rs.getString("causale_versamento");
		datiSpecificiRiscossione = rs.getString("dati_specifici_riscossione");

		enteredBy=rs.getInt("entered_by");
		modifiedBy = rs.getInt("modified_by");
		inviatoBy =rs.getInt("inviato_by");
		inviato = rs.getBoolean("inviato");
		dataInvio = rs.getTimestamp("data_invio");
		esitoInvio = rs.getString("esito_invio");	
		identificativoUnivocoVersamento = rs.getString("identificativo_univoco_versamento");
		urlFileAvviso = rs.getString("url_file_avviso");

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdTrasgressione() {
		return idTrasgressione;
	}

	public void setIdTrasgressione(int idTrasgressione) {
		this.idTrasgressione = idTrasgressione;
	}

	public int getIdSanzione() {
		return idSanzione;
	}

	public void setIdSanzione(int idSanzione) {
		this.idSanzione = idSanzione;
	}

	public String getTipoIdentificativoUnivoco() {
		return tipoIdentificativoUnivoco;
	}

	public void setTipoIdentificativoUnivoco(String tipoIdentificativoUnivoco) {
		this.tipoIdentificativoUnivoco = tipoIdentificativoUnivoco;
	}

	public String getCodiceIdentificativoUnivoco() {
		return codiceIdentificativoUnivoco;
	}

	public void setCodiceIdentificativoUnivoco(String codiceIdentificativoUnivoco) {
		this.codiceIdentificativoUnivoco = codiceIdentificativoUnivoco;
	}

	public String getAnagraficaPagatore() {
		return anagraficaPagatore;
	}

	public void setAnagraficaPagatore(String anagraficaPagatore) {
		this.anagraficaPagatore = anagraficaPagatore;
	}

	public String getIndirizzoPagatore() {
		return indirizzoPagatore;
	}

	public void setIndirizzoPagatore(String indirizzoPagatore) {
		this.indirizzoPagatore = indirizzoPagatore;
	}

	public String getCivicoPagatore() {
		return civicoPagatore;
	}

	public void setCivicoPagatore(String civicoPagatore) {
		this.civicoPagatore = civicoPagatore;
	}

	public String getCapPagatore() {
		return capPagatore;
	}

	public void setCapPagatore(String capPagatore) {
		this.capPagatore = capPagatore;
	}

	public String getLocalitaPagatore() {
		return localitaPagatore;
	}

	public void setLocalitaPagatore(String localitaPagatore) {
		this.localitaPagatore = localitaPagatore;
	}

	public String getProvinciaPagatore() {
		return provinciaPagatore;
	}

	public void setProvinciaPagatore(String provinciaPagatore) {
		this.provinciaPagatore = provinciaPagatore;
	}

	public String getNazionePagatore() {
		return nazionePagatore;
	}

	public void setNazionePagatore(String nazionePagatore) {
		this.nazionePagatore = nazionePagatore;
	}

	public String getEmailPagatore() {
		return emailPagatore;
	}

	public void setEmailPagatore(String emailPagatore) {
		this.emailPagatore = emailPagatore;
	}

	public String getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(String dataPagamento) {
		this.dataPagamento = dataPagamento;
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

	public void buildFromRequest(ActionContext context) {
		tipoIdentificativoUnivoco = context.getRequest().getParameter("tipoIdentificativoUnivoco_"+id);
		codiceIdentificativoUnivoco =context.getRequest().getParameter("codiceIdentificativoUnivoco_"+id);
		anagraficaPagatore = context.getRequest().getParameter("anagraficaPagatore_"+id);
		indirizzoPagatore = context.getRequest().getParameter("indirizzoPagatore_"+id);
		civicoPagatore = context.getRequest().getParameter("civicoPagatore_"+id);
		capPagatore = context.getRequest().getParameter("capPagatore_"+id);
		localitaPagatore = context.getRequest().getParameter("localitaPagatore_"+id);
		provinciaPagatore = context.getRequest().getParameter("provinciaPagatore_"+id);
		nazionePagatore = context.getRequest().getParameter("nazionePagatore_"+id);
		emailPagatore = context.getRequest().getParameter("emailPagatore_"+id);
		
		try {numeroRate = Integer.parseInt(context.getRequest().getParameter("numeroRate_"+id));} catch (Exception e){}
		rataNumero = Integer.parseInt(context.getRequest().getParameter("rataNumero_"+id));

		tipoPagamento = context.getRequest().getParameter("tipoPagamento_"+id);
		dataPagamento = context.getRequest().getParameter("dataPagamento_"+id);
		tipoVersamento = context.getRequest().getParameter("tipoVersamento_"+id); 
		identificativoUnivocoDovuto =context.getRequest().getParameter("identificativoUnivocoDovuto_"+id);
		importoSingoloVersamento =context.getRequest().getParameter("importoSingoloVersamento_"+id);
		identificativoTipoDovuto = context.getRequest().getParameter("identificativoTipoDovuto_"+id);
		causaleVersamento =context.getRequest().getParameter("causaleVersamento_"+id);
		datiSpecificiRiscossione =context.getRequest().getParameter("datiSpecificiRiscossione_"+id);
	}
	
public String wsInserimento (Connection db, int userId) throws SQLException{
	WsPost ws = new WsPost();
	String envelope = null;
	String response = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	logger.info(" ------ INVIO DATI importaDovuto A PAGOPA id pagopa "+id+" -----"); 
	ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_PAGOPA")); 

	pst = db.prepareStatement("select * from get_chiamata_ws_pagopa_importa_dovuto(?, ?, ?)");
	pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_PAGOPA"));
	pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_PAGOPA"));
	pst.setInt(3, id);
	rs = pst.executeQuery();
	while (rs.next())
		envelope = rs.getString(1);
	
	ws.setWsRequest(envelope);
	response= ws.post(db, userId);
	esitoInvio = response;
	updateEsitoWsInserimento(db, userId);

	return response;
}

public void updateEsitoWsInserimento(Connection db, int userId) throws SQLException {
	String esito = null;
	PreparedStatement pst = null;
	if (!inviato && esitoInvio!=null){
		esito = getTagValue(esitoInvio, "esito");
		if (esito.equalsIgnoreCase("OK")){
			if (identificativoUnivocoVersamento==null)
				identificativoUnivocoVersamento = getTagValue(esitoInvio, "identificativoUnivocoVersamento");
			if (urlFileAvviso==null)
				urlFileAvviso = getTagValue(esitoInvio, "urlFileAvviso");

			inviato = true;
		}
	}
		
	pst = db.prepareStatement("update registro_trasgressori_pago_pa set esito_invio = ?, inviato = ?, identificativo_univoco_versamento = ?, url_file_avviso = ?,  inviato_by = ?, data_invio = now() where id = ?");
	pst.setString(1, esitoInvio);
	pst.setBoolean(2, inviato);
	pst.setString(3, identificativoUnivocoVersamento);
	pst.setString(4, urlFileAvviso);
	pst.setInt(5, userId);
	pst.setInt(6, id); 
	pst.executeUpdate();	
}

public void updateEsitoWsAggiornamento(Connection db, int userId) throws SQLException {
	String esito = null;
	PreparedStatement pst = null;
	if (!inviato && esitoInvio!=null){
		esito = getTagValue(esitoInvio, "esito");
		if (esito.equalsIgnoreCase("OK")){
			urlFileAvviso = getTagValue(esitoInvio, "urlFileAvviso");
		}
	}
		
	pst = db.prepareStatement("update registro_trasgressori_pago_pa set esito_invio = ?, url_file_avviso = ?,  inviato_by = ?, data_invio = now() where id = ?");
	pst.setString(1, esitoInvio);
	pst.setString(2, urlFileAvviso);
	pst.setInt(3, userId);
	pst.setInt(4, id); 
	pst.executeUpdate();	
}

public void updateEsitoWsAnnullamento(Connection db, int userId) throws SQLException {
	String esito = null;
	PreparedStatement pst = null;
		esito = getTagValue(esitoInvio, "esito");
		if (esito.equalsIgnoreCase("OK")){
		
	pst = db.prepareStatement("update registro_trasgressori_pago_pa set esito_invio = ?, inviato_by = ?, data_invio = now(), trashed_date = now() where id = ?");
	pst.setString(1, esitoInvio);
	pst.setInt(2, userId);
	pst.setInt(3, id); 
	pst.executeUpdate();	
		}
} 

public String wsAggiornamento(Connection db, int userId) throws SQLException{
	WsPost ws = new WsPost();
	String envelope = null;
	String response = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	logger.info(" ------ INVIO DATI importaDovuto A PAGOPA id pagopa "+id+" -----"); 
	ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_PAGOPA")); 

	pst = db.prepareStatement("select * from get_chiamata_ws_pagopa_importa_dovuto_aggiorna(?, ?, ?)");
	pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_PAGOPA"));
	pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_PAGOPA"));
	pst.setInt(3, id);
	rs = pst.executeQuery();
	while (rs.next())
		envelope = rs.getString(1);
	
	ws.setWsRequest(envelope);
	response= ws.post(db, userId);
	esitoInvio = response;
	updateEsitoWsAggiornamento(db, userId);
	return response;
}

public String wsAnnullamento(Connection db, int userId) throws SQLException{ 
	WsPost ws = new WsPost();
	String envelope = null;
	String response = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	logger.info(" ------ INVIO DATI importaDovuto A PAGOPA id pagopa "+id+" -----"); 
	ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_PAGOPA")); 

	pst = db.prepareStatement("select * from get_chiamata_ws_pagopa_importa_dovuto_annulla(?, ?, ?)");
	pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_PAGOPA"));
	pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_PAGOPA"));
	pst.setInt(3, id);
	rs = pst.executeQuery();
	while (rs.next())
		envelope = rs.getString(1);
	
	ws.setWsRequest(envelope);
	response= ws.post(db, userId);
	esitoInvio = response;
	updateEsitoWsAnnullamento(db, userId);
	return response;
}


public String wsStatoPagamento(Connection db, int userId) throws SQLException{
	WsPost ws = new WsPost();
	String envelope = null;
	String response = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	logger.info(" ------ INVIO DATI ChiediPagatiConRicevuta A PAGOPA id pagopa "+id+" -----"); 
	ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_PAGOPA")); 

	pst = db.prepareStatement("select * from get_chiamata_ws_pagopa_chiedi_pagati(?, ?, ?)");
	pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_PAGOPA"));
	pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_PAGOPA"));
	pst.setInt(3, id);
	rs = pst.executeQuery();
	while (rs.next())
		envelope = rs.getString(1);
	
	ws.setWsRequest(envelope);
	response= ws.post(db, userId);

	return response;
}

public static String getTagValue(String xml, String tagName){
	String esito = "";
    try {esito =  xml.split("<"+tagName+">")[1].split("</"+tagName+">")[0]; } catch (Exception e) {}
    return esito;
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

public int getNumeroRate() {
	return numeroRate;
}

public void setNumeroRate(int numeroRate) {
	this.numeroRate = numeroRate;
}

public int getRataNumero() {
	return rataNumero;
}

public void setRataNumero(int rataNumero) {
	this.rataNumero = rataNumero;
}

public static ArrayList<PagoPa> getListaPagoPa(Connection db, int idTrasgressione, int userId, boolean inserisciSeNonEsiste) throws SQLException {
	ArrayList<PagoPa> listaPagoPa = new ArrayList<PagoPa>();
	PreparedStatement pst = null;
	pst = db.prepareStatement("select * from registro_trasgressori_pago_pa where id_trasgressione = ? and trashed_date is null order by rata_numero asc");
	pst.setInt(1, idTrasgressione);
	ResultSet rs = pst.executeQuery();
	
	boolean esiste = false;
	
	while (rs.next()){
		PagoPa pagopa = new PagoPa();
		pagopa.buildRecord(rs);
		listaPagoPa.add(pagopa);
		esiste = true;
	}
	
	if (!esiste && inserisciSeNonEsiste){
		PagoPa pagopa = new PagoPa(db, idTrasgressione, true, userId);
		listaPagoPa.add(pagopa);
	}
	return listaPagoPa;
}

public String gestioneInserimento(Connection db, int userId) throws SQLException {
	
	String esito = "";
	
	delete(db, userId);
	
	insertRata(db, userId);
	esito = wsInserimento(db, userId);
	
	if (numeroRate>1){
		for (int i = 2; i<=numeroRate; i++) {
		
			PagoPa pa = new PagoPa();
			pa = this;
			pa.setId(-1);
			pa.setRataNumero(i);
			pa.setInviato(false);
			pa.setIdentificativoUnivocoVersamento(null);
			pa.setUrlFileAvviso(null);
			pa.setInviatoBy(-1);
			pa.refreshIdentificativoUnivocoDovuto(db);
			pa.insertRata(db, userId);
			esito+= pa.wsInserimento(db, userId);
	} }
	return esito;
}

private void refreshIdentificativoUnivocoDovuto(Connection db) throws SQLException {
PreparedStatement pst = db.prepareStatement("select * from genera_pagopa_identificativo_dovuto()");	
ResultSet rs = pst.executeQuery();
if (rs.next())
	identificativoUnivocoDovuto = rs.getString(1);
}

public String gestioneAggiornamento(Connection db, int userId) throws SQLException {
	
	String esito = "";
	
	delete(db, userId);
	
	insertRata(db, userId);
	esito = wsAggiornamento(db, userId);
	
	return esito;
}

public String gestioneAnnullamento(Connection db, int userId) throws SQLException {
	
	String esito = "";
	esito = wsAnnullamento(db, userId);
	return esito;
}

public String getTipoPagamento() {
	return tipoPagamento;
}

public void setTipoPagamento(String tipoPagamento) {
	this.tipoPagamento = tipoPagamento;
}
}


