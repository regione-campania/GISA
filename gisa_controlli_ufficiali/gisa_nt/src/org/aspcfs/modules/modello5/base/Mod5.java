/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.modello5.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.actions.ActionContext;

public class Mod5{
	
	private int id = -1;
	private Timestamp entered = null;
	private int enteredBy = -1;
	private Timestamp modified = null;
	private int modifiedBy = -1;
	
	private boolean bozza = false;
	
	//intestazione
	private String headerServizio = null;
	private String headerPec1 = null;
	private String headerUO = null;
	private String headerPec2 = null;
	
	//cu
	private int controlloId = -1;
	private String controlloAsl = null;
	private int controlloAnno = -1;
	private int controlloGiorno = -1;
	private String controlloMese = null;
	private String controlloOre = null;
	private String controlloNucleo = null;
	private String controlloOggetto = null;
	private String controlloPiano = null;
	private String controlloAttivita = null;
	private String controlloNcFormali = null;
	private String controlloNcSignificative = null;
	private String controlloNcGravi = null;
	private String controlloNcFormaliPunti = null;
	private String controlloNcSignificativePunti = null;
	private String controlloNcGraviPunti = null;
	private String controlloNcFormaliVal = null;
	private String controlloNcSignificativeVal = null;
	private String controlloLinea = null;
	private String controlloDataFine = null;

	//stabilimento
	private String stabilimentoComune = null;
	private String stabilimentoIndirizzo = null;
	private String stabilimentoCivico = null;
	private String stabilimentoCe = null;
	private String stabilimentoNumRegistrazione = null;

	//impresa
	private String operatoreRagioneSociale = null;
	private String operatorePartitaIva = null;
	private String operatoreComune = null;
	private String operatoreIndirizzo = null;
	private String operatoreCivico = null;
	
	//rappresentante legale
	private String rappresentanteNome = null;
	private String rappresentanteComuneNascita = null;
	private String rappresentanteDataNascita = null;
	private String rappresentanteComune = null;
	private String rappresentanteIndirizzo = null;
	private String rappresentanteCivico = null;
	private String rappresentanteDomicilioDigitale = null;

	//presente all'ispezione
	private String presenteNome = null;
	private String presenteComuneNascita = null;
	private String presenteDataNascita = null;
	private String presenteComune= null;
	private String presenteIndirizzo = null;
	private String presenteCivico = null;
	private String presenteDocumento = null;
	private String presenteDocumentoNumeri = null;
	private String presenteModalitaIspezione = null;
	
	//altro
	
	private String	responsabileProcedimento	= null;
	private String	provvedimentiNonImputabili	= null;
	private String	ncFormaliGiorniRisoluzione	= null;
	private String	ncSignificativeGiorniRisoluzione	= null;
	private String	ncGraviContestazione	= null;
	private String	ncGraviAccertamento	= null;
	private String	ncGraviDiffida	= null;
	private String	ncGraviSequestroAmministrativo	= null;
	private String	ncGraviSequestroPenale	= null;
	private String	ncGraviBlocco	= null;
	private String	ncGraviDispone	= null;
	private String	ncGraviContestazioneDesc	= null;
	private String	ncGraviDiffidaDesc	= null;
	private String	ncGraviSequestroAmministrativoDesc	= null;
	private String	ncGraviSequestroPenaleDesc	= null;
	private String	ncGraviBloccoDesc	= null;
	private String	ncGraviDisponeDesc	= null;
	private String	valutazioneRischio	= null;
	private String	presenteDichiarazione	= null;
	private String	note	= null;
	private String  ispezioneVerifica = null;
	private String	azioniDescrizione	= null;
	private String  ncPunteggioTotale = null;

	

	
	public Mod5() {
 
	}
	
	public Mod5(ResultSet rs, Connection db) throws SQLException {
		buildRecord(rs, db);
		}
	
	public Mod5(Connection db, int idControllo) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from get_modello_5(?)");
		pst.setInt(1, idControllo);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst); 
		 while (rs.next()){
				 buildRecord(rs, db);
	}
	}

	private void buildRecord(ResultSet rs, Connection db) throws SQLException {
		
		this.id = rs.getInt("id");
		this.enteredBy = rs.getInt("enteredby");
		this.entered = rs.getTimestamp("entered");
		this.modifiedBy = rs.getInt("modifiedby");
		this.modified = rs.getTimestamp("modified");
		this.bozza = rs.getBoolean("bozza");

		this.headerServizio = rs.getString("header_servizio");
		this.headerPec1 = rs.getString("header_pec1");
		this.headerUO = rs.getString("header_uo");
		this.headerPec2 = rs.getString("header_pec2");
		
		this.controlloId = rs.getInt("controllo_id");
		this.controlloAsl = rs.getString("controllo_asl");
		this.controlloAnno = rs.getInt("controllo_anno");
		this.controlloGiorno = rs.getInt("controllo_giorno");
		this.controlloMese = rs.getString("controllo_mese");
		this.controlloOre = rs.getString("controllo_ore");
		this.controlloNucleo = rs.getString("controllo_nucleo");
		this.controlloOggetto = rs.getString("controllo_oggetto");
		this.controlloPiano = rs.getString("controllo_piano");
		this.controlloAttivita = rs.getString("controllo_attivita");
		this.controlloNcFormali =  rs.getString("controllo_ncformali");
		this.controlloNcSignificative =  rs.getString("controllo_ncsignificative");
		this.controlloNcGravi =  rs.getString("controllo_ncgravi");
		this.controlloNcFormaliPunti =  rs.getString("controllo_ncformalipunti");
		this.controlloNcSignificativePunti =  rs.getString("controllo_ncsignificativepunti");
		this.controlloNcGraviPunti =  rs.getString("controllo_ncgravipunti");
		this.controlloNcFormaliVal =  rs.getString("controllo_ncformalival");
		this.controlloNcSignificativeVal =  rs.getString("controllo_ncsignificativeval");
		this.controlloLinea = rs.getString("controllo_linea");
		this.controlloDataFine = rs.getString("controllo_data_fine");

		this.stabilimentoComune = rs.getString("stabilimento_comune");
		this.stabilimentoIndirizzo = rs.getString("stabilimento_indirizzo");
		this.stabilimentoCivico = rs.getString("stabilimento_civico");
		this.stabilimentoCe = rs.getString("stabilimento_ce");
		this.stabilimentoNumRegistrazione = rs.getString("stabilimento_num_registrazione");

		this.operatoreRagioneSociale = rs.getString("operatore_ragione_sociale");
		this.operatorePartitaIva = rs.getString("operatore_partita_iva");
		this.operatoreComune = rs.getString("operatore_comune");
		this.operatoreIndirizzo = rs.getString("operatore_indirizzo");
		this.operatoreCivico = rs.getString("operatore_civico");
		
		this.rappresentanteNome = rs.getString("rappresentante_nome");
		this.rappresentanteComuneNascita = rs.getString("rappresentante_comune_nascita");
		this.rappresentanteDataNascita = rs.getString("rappresentante_data_nascita");
		this.rappresentanteComune = rs.getString("rappresentante_comune");
		this.rappresentanteIndirizzo = rs.getString("rappresentante_indirizzo");
		this.rappresentanteCivico = rs.getString("rappresentante_civico");
		this.rappresentanteDomicilioDigitale = rs.getString("rappresentante_domicilio_digitale");

		this.presenteNome = rs.getString("presente_nome");
		this.presenteComuneNascita = rs.getString("presente_comune_nascita");
		this.presenteDataNascita = rs.getString("presente_data_nascita");
		this.presenteComune = rs.getString("presente_comune");
		this.presenteIndirizzo = rs.getString("presente_indirizzo");
		this.presenteCivico = rs.getString("presente_civico");
		this.presenteDocumento = rs.getString("presente_documento");
		this.presenteDocumentoNumeri = rs.getString("presente_documento_numeri");
		this.presenteModalitaIspezione = rs.getString("presente_modalita_ispezione");
		
		this.responsabileProcedimento = rs.getString("responsabile_procedimento");
		this.provvedimentiNonImputabili = rs.getString("provvedimenti_non_imputabili");
		this.ncFormaliGiorniRisoluzione = rs.getString("nc_formali_giorni_risoluzione");
		this.ncSignificativeGiorniRisoluzione = rs.getString("nc_significative_giorni_risoluzione");
		this.ncGraviContestazione = rs.getString("nc_gravi_constestazione");
		this.ncGraviAccertamento = rs.getString("nc_gravi_accertamento");
		this.ncGraviDiffida = rs.getString("nc_gravi_diffida");
		this.ncGraviSequestroAmministrativo = rs.getString("nc_gravi_sequestro_amministrativo");
		this.ncGraviSequestroPenale = rs.getString("nc_gravi_sequestro_penale");
		this.ncGraviBlocco = rs.getString("nc_gravi_blocco");
		this.ncGraviDispone = rs.getString("nc_gravi_dispone");
		this.ncGraviContestazioneDesc = rs.getString("nc_gravi_constestazione_desc");
		this.ncGraviDiffidaDesc = rs.getString("nc_gravi_diffida_desc");
		this.ncGraviSequestroAmministrativoDesc = rs.getString("nc_gravi_sequestro_amministrativo_desc");
		this.ncGraviSequestroPenaleDesc = rs.getString("nc_gravi_sequestro_penale_desc");
		this.ncGraviBloccoDesc = rs.getString("nc_gravi_blocco_desc");
		this.ncGraviDisponeDesc = rs.getString("nc_gravi_dispone_desc");
		this.valutazioneRischio = rs.getString("valutazione_rischio");
		this.presenteDichiarazione = rs.getString("presente_dichiarazione");
		this.note = rs.getString("note");
		this.azioniDescrizione = rs.getString("azioni_descrizione");
		this.ncPunteggioTotale = rs.getString("nc_punteggio_totale");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isBozza() {
		return bozza;
	}

	public void setBozza(boolean bozza) {
		this.bozza = bozza;
	}

	public String getHeaderServizio() {
		return headerServizio;
	}

	public void setHeaderServizio(String headerServizio) {
		this.headerServizio = headerServizio;
	}

	public String getHeaderPec1() {
		return headerPec1;
	}

	public void setHeaderPec1(String headerPec1) {
		this.headerPec1 = headerPec1;
	}

	public String getHeaderUO() {
		return headerUO;
	}

	public void setHeaderUO(String headerUO) {
		this.headerUO = headerUO;
	}

	public String getHeaderPec2() {
		return headerPec2;
	}

	public void setHeaderPec2(String headerPec2) {
		this.headerPec2 = headerPec2;
	}

	public int getControlloAnno() {
		return controlloAnno;
	}

	public void setControlloAnno(int controlloAnno) {
		this.controlloAnno = controlloAnno;
	}

	public int getControlloGiorno() {
		return controlloGiorno;
	}

	public void setControlloGiorno(int controlloGiorno) {
		this.controlloGiorno = controlloGiorno;
	}

	public String getControlloMese() {
		return controlloMese;
	}

	public void setControlloMese(String controlloMese) {
		this.controlloMese = controlloMese;
	}

	public String getControlloOre() {
		return controlloOre;
	}

	public void setControlloOre(String controlloOre) {
		this.controlloOre = controlloOre;
	}

	public String getControlloNucleo() {
		return controlloNucleo;
	}

	public void setControlloNucleo(String controlloNucleo) {
		this.controlloNucleo = controlloNucleo;
	}

	public String getControlloLinea() {
		return controlloLinea;
	}

	public void setControlloLinea(String controlloLinea) {
		this.controlloLinea = controlloLinea;
	}

	public String getStabilimentoComune() {
		return stabilimentoComune;
	}

	public void setStabilimentoComune(String stabilimentoComune) {
		this.stabilimentoComune = stabilimentoComune;
	}

	public String getStabilimentoIndirizzo() {
		return stabilimentoIndirizzo;
	}

	public void setStabilimentoIndirizzo(String stabilimentoIndirizzo) {
		this.stabilimentoIndirizzo = stabilimentoIndirizzo;
	}

	public String getStabilimentoCivico() {
		return stabilimentoCivico;
	}

	public void setStabilimentoCivico(String stabilimentoCivico) {
		this.stabilimentoCivico = stabilimentoCivico;
	}

	public String getStabilimentoCe() {
		return stabilimentoCe;
	}

	public void setStabilimentoCe(String stabilimentoCe) {
		this.stabilimentoCe = stabilimentoCe;
	}

	public String getStabilimentoNumRegistrazione() {
		return stabilimentoNumRegistrazione;
	}

	public void setStabilimentoNumRegistrazione(String stabilimentoNumRegistrazione) {
		this.stabilimentoNumRegistrazione = stabilimentoNumRegistrazione;
	}

	public String getOperatoreRagioneSociale() {
		return operatoreRagioneSociale;
	}

	public void setOperatoreRagioneSociale(String operatoreRagioneSociale) {
		this.operatoreRagioneSociale = operatoreRagioneSociale;
	}

	public String getOperatorePartitaIva() {
		return operatorePartitaIva;
	}

	public void setOperatorePartitaIva(String operatorePartitaIva) {
		this.operatorePartitaIva = operatorePartitaIva;
	}

	public String getOperatoreComune() {
		return operatoreComune;
	}

	public void setOperatoreComune(String operatoreComune) {
		this.operatoreComune = operatoreComune;
	}

	public String getOperatoreIndirizzo() {
		return operatoreIndirizzo;
	}

	public void setOperatoreIndirizzo(String operatoreIndirizzo) {
		this.operatoreIndirizzo = operatoreIndirizzo;
	}

	public String getOperatoreCivico() {
		return operatoreCivico;
	}

	public void setOperatoreCivico(String operatoreCivico) {
		this.operatoreCivico = operatoreCivico;
	}

	public String getRappresentanteNome() {
		return rappresentanteNome;
	}

	public void setRappresentanteNome(String rappresentanteNome) {
		this.rappresentanteNome = rappresentanteNome;
	}

	public String getRappresentanteComuneNascita() {
		return rappresentanteComuneNascita;
	}

	public void setRappresentanteComuneNascita(String rappresentanteComuneNascita) {
		this.rappresentanteComuneNascita = rappresentanteComuneNascita;
	}

	public String getRappresentanteDataNascita() {
		return rappresentanteDataNascita;
	}

	public void setRappresentanteDataNascita(String rappresentanteDataNascita) {
		this.rappresentanteDataNascita = rappresentanteDataNascita;
	}

	public String getRappresentanteComune() {
		return rappresentanteComune;
	}

	public void setRappresentanteComune(String rappresentanteComune) {
		this.rappresentanteComune = rappresentanteComune;
	}

	public String getRappresentanteIndirizzo() {
		return rappresentanteIndirizzo;
	}

	public void setRappresentanteIndirizzo(String rappresentanteIndirizzo) {
		this.rappresentanteIndirizzo = rappresentanteIndirizzo;
	}

	public String getRappresentanteCivico() {
		return rappresentanteCivico;
	}

	public void setRappresentanteCivico(String rappresentanteCivico) {
		this.rappresentanteCivico = rappresentanteCivico;
	}

	public String getRappresentanteDomicilioDigitale() {
		return rappresentanteDomicilioDigitale;
	}

	public void setRappresentanteDomicilioDigitale(String rappresentanteDomicilioDigitale) {
		this.rappresentanteDomicilioDigitale = rappresentanteDomicilioDigitale;
	}

	public String getPresenteNome() {
		return presenteNome;
	}

	public void setPresenteNome(String presenteNome) {
		this.presenteNome = presenteNome;
	}

	public String getPresenteComuneNascita() {
		return presenteComuneNascita;
	}

	public void setPresenteComuneNascita(String presenteComuneNascita) {
		this.presenteComuneNascita = presenteComuneNascita;
	}

	public String getPresenteDataNascita() {
		return presenteDataNascita;
	}

	public void setPresenteDataNascita(String presenteDataNascita) {
		this.presenteDataNascita = presenteDataNascita;
	}

	public String getPresenteIndirizzo() {
		return presenteIndirizzo;
	}

	public void setPresenteIndirizzo(String presenteIndirizzo) {
		this.presenteIndirizzo = presenteIndirizzo;
	}

	public String getPresenteCivico() {
		return presenteCivico;
	}

	public void setPresenteCivico(String presenteCivico) {
		this.presenteCivico = presenteCivico;
	}

	public String getPresenteDocumento() {
		return presenteDocumento;
	}

	public void setPresenteDocumento(String presenteDocumento) {
		this.presenteDocumento = presenteDocumento;
	}

	public String getPresenteDocumentoNumeri() {
		return presenteDocumentoNumeri;
	}

	public void setPresenteDocumentoNumeri(String presenteDocumentoNumeri) {
		this.presenteDocumentoNumeri = presenteDocumentoNumeri;
	}

	public String getPresenteModalitaIspezione() {
		return presenteModalitaIspezione;
	}

	public void setPresenteModalitaIspezione(String presenteModalitaIspezione) {
		this.presenteModalitaIspezione = presenteModalitaIspezione;
	}

	public Timestamp getEntered() {
		return entered;
	}

	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}

	public int getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}

	public Timestamp getModified() {
		return modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getControlloDataFine() {
		return controlloDataFine;
	}

	public void setControlloDataFine(String controlloDataFine) {
		this.controlloDataFine = controlloDataFine;
	}

	public int getControlloId() {
		return controlloId;
	}

	public void setControlloId(int controlloId) {
		this.controlloId = controlloId;
	}

	public String getControlloAsl() {
		return controlloAsl;
	}

	public void setControlloAsl(String controlloAsl) {
		this.controlloAsl = controlloAsl;
	}

	public String getPresenteComune() {
		return presenteComune;
	}

	public void setPresenteComune(String presenteComune) {
		this.presenteComune = presenteComune;
	}

	public String getControlloOggetto() {
		return controlloOggetto;
	}

	public void setControlloOggetto(String controlloOggetto) {
		this.controlloOggetto = controlloOggetto;
	}

	public String getControlloPiano() {
		return controlloPiano;
	}

	public void setControlloPiano(String controlloPiano) {
		this.controlloPiano = controlloPiano;
	}

	public String getControlloAttivita() {
		return controlloAttivita;
	}

	public void setControlloAttivita(String controlloAttivita) {
		this.controlloAttivita = controlloAttivita;
	}

	public String getControlloNcFormali() {
		return controlloNcFormali;
	}

	public void setControlloNcFormali(String controlloNcFormali) {
		this.controlloNcFormali = controlloNcFormali;
	}

	public String getControlloNcSignificative() {
		return controlloNcSignificative;
	}

	public void setControlloNcSignificative(String controlloNcSignificative) {
		this.controlloNcSignificative = controlloNcSignificative;
	}

	public String getControlloNcGravi() {
		return controlloNcGravi;
	}

	public void setControlloNcGravi(String controlloNcGravi) {
		this.controlloNcGravi = controlloNcGravi;
	}

	public String getControlloNcFormaliPunti() {
		return controlloNcFormaliPunti;
	}

	public void setControlloNcFormaliPunti(String controlloNcFormaliPunti) {
		this.controlloNcFormaliPunti = controlloNcFormaliPunti;
	}

	public String getControlloNcSignificativePunti() {
		return controlloNcSignificativePunti;
	}

	public void setControlloNcSignificativePunti(String controlloNcSignificativePunti) {
		this.controlloNcSignificativePunti = controlloNcSignificativePunti;
	}

	public String getControlloNcGraviPunti() {
		return controlloNcGraviPunti;
	}

	public void setControlloNcGraviPunti(String controlloNcGraviPunti) {
		this.controlloNcGraviPunti = controlloNcGraviPunti;
	}

	public String getControlloNcFormaliVal() {
		return controlloNcFormaliVal;
	}

	public void setControlloNcFormaliVal(String controlloNcFormaliVal) {
		this.controlloNcFormaliVal = controlloNcFormaliVal;
	}

	public String getControlloNcSignificativeVal() {
		return controlloNcSignificativeVal;
	}

	public void setControlloNcSignificativeVal(String controlloNcSignificativeVal) {
		this.controlloNcSignificativeVal = controlloNcSignificativeVal;
	}

	public String getResponsabileProcedimento() {
		return responsabileProcedimento;
	}

	public void setResponsabileProcedimento(String responsabileProcedimento) {
		this.responsabileProcedimento = responsabileProcedimento;
	}

	public String getProvvedimentiNonImputabili() {
		return provvedimentiNonImputabili;
	}

	public void setProvvedimentiNonImputabili(String provvedimentiNonImputabili) {
		this.provvedimentiNonImputabili = provvedimentiNonImputabili;
	}

	public String getNcFormaliGiorniRisoluzione() {
		return ncFormaliGiorniRisoluzione;
	}

	public void setNcFormaliGiorniRisoluzione(String ncFormaliGiorniRisoluzione) {
		this.ncFormaliGiorniRisoluzione = ncFormaliGiorniRisoluzione;
	}

	public String getNcSignificativeGiorniRisoluzione() {
		return ncSignificativeGiorniRisoluzione;
	}

	public void setNcSignificativeGiorniRisoluzione(String ncSignificativeGiorniRisoluzione) {
		this.ncSignificativeGiorniRisoluzione = ncSignificativeGiorniRisoluzione;
	}

	public String getNcGraviContestazione() {
		return ncGraviContestazione;
	}

	public void setNcGraviContestazione(String ncGraviContestazione) {
		this.ncGraviContestazione = ncGraviContestazione;
	}

	public String getNcGraviAccertamento() {
		return ncGraviAccertamento;
	}

	public void setNcGraviAccertamento(String ncGraviAccertamento) {
		this.ncGraviAccertamento = ncGraviAccertamento;
	}

	public String getNcGraviDiffida() {
		return ncGraviDiffida;
	}

	public void setNcGraviDiffida(String ncGraviDiffida) {
		this.ncGraviDiffida = ncGraviDiffida;
	}

	public String getNcGraviSequestroAmministrativo() {
		return ncGraviSequestroAmministrativo;
	}

	public void setNcGraviSequestroAmministrativo(String ncGraviSequestroAmministrativo) {
		this.ncGraviSequestroAmministrativo = ncGraviSequestroAmministrativo;
	}

	public String getNcGraviSequestroPenale() {
		return ncGraviSequestroPenale;
	}

	public void setNcGraviSequestroPenale(String ncGraviSequestroPenale) {
		this.ncGraviSequestroPenale = ncGraviSequestroPenale;
	}

	public String getNcGraviBlocco() {
		return ncGraviBlocco;
	}

	public void setNcGraviBlocco(String ncGraviBlocco) {
		this.ncGraviBlocco = ncGraviBlocco;
	}

	public String getNcGraviDispone() {
		return ncGraviDispone;
	}

	public void setNcGraviDispone(String ncGraviDispone) {
		this.ncGraviDispone = ncGraviDispone;
	}

	public String getNcGraviContestazioneDesc() {
		return ncGraviContestazioneDesc;
	}

	public void setNcGraviContestazioneDesc(String ncGraviContestazioneDesc) {
		this.ncGraviContestazioneDesc = ncGraviContestazioneDesc;
	}

	public String getNcGraviDiffidaDesc() {
		return ncGraviDiffidaDesc;
	}

	public void setNcGraviDiffidaDesc(String ncGraviDiffidaDesc) {
		this.ncGraviDiffidaDesc = ncGraviDiffidaDesc;
	}

	public String getNcGraviSequestroAmministrativoDesc() {
		return ncGraviSequestroAmministrativoDesc;
	}

	public void setNcGraviSequestroAmministrativoDesc(String ncGraviSequestroAmministrativoDesc) {
		this.ncGraviSequestroAmministrativoDesc = ncGraviSequestroAmministrativoDesc;
	}

	public String getNcGraviSequestroPenaleDesc() {
		return ncGraviSequestroPenaleDesc;
	}

	public void setNcGraviSequestroPenaleDesc(String ncGraviSequestroPenaleDesc) {
		this.ncGraviSequestroPenaleDesc = ncGraviSequestroPenaleDesc;
	}

	public String getNcGraviBloccoDesc() {
		return ncGraviBloccoDesc;
	}

	public void setNcGraviBloccoDesc(String ncGraviBloccoDesc) {
		this.ncGraviBloccoDesc = ncGraviBloccoDesc;
	}

	public String getNcGraviDisponeDesc() {
		return ncGraviDisponeDesc;
	}

	public void setNcGraviDisponeDesc(String ncGraviDisponeDesc) {
		this.ncGraviDisponeDesc = ncGraviDisponeDesc;
	}

	public String getValutazioneRischio() {
		return valutazioneRischio;
	}

	public void setValutazioneRischio(String valutazioneRischio) {
		this.valutazioneRischio = valutazioneRischio;
	}

	public String getPresenteDichiarazione() {
		return presenteDichiarazione;
	}

	public void setPresenteDichiarazione(String presenteDichiarazione) {
		this.presenteDichiarazione = presenteDichiarazione;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAzioniDescrizione() {
		return azioniDescrizione;
	}

	public void setAzioniDescrizione(String azioniDescrizione) {
		this.azioniDescrizione = azioniDescrizione;
	}

	public String getNcPunteggioTotale() {
		return ncPunteggioTotale;
	}

	public void setNcPunteggioTotale(String ncPunteggioTotale) {
		this.ncPunteggioTotale = ncPunteggioTotale;
	}

	public String getIspezioneVerifica() {
		return ispezioneVerifica;
	}

	public void setIspezioneVerifica(String ispezioneVerifica) {
		this.ispezioneVerifica = ispezioneVerifica;
	}

	public void buildDaRequest(ActionContext context) {
		
		this.headerServizio = context.getRequest().getParameter("headerServizio");
		this.headerPec1 = context.getRequest().getParameter("headerPec1");
		this.headerUO = context.getRequest().getParameter("headerUO");
		this.headerPec2 = context.getRequest().getParameter("headerPec2");
		
		this.controlloOre = context.getRequest().getParameter("controlloOre");
		
		this.presenteNome = context.getRequest().getParameter("presenteNome");
		this.presenteComuneNascita = context.getRequest().getParameter("presenteComuneNascita");
		this.presenteDataNascita = context.getRequest().getParameter("presenteDataNascita");
		this.presenteComune = context.getRequest().getParameter("presenteComune");
		this.presenteIndirizzo = context.getRequest().getParameter("presenteIndirizzo");
		this.presenteCivico = context.getRequest().getParameter("presenteCivico");
		this.presenteDocumento = context.getRequest().getParameter("presenteDocumento");
		this.presenteDocumentoNumeri = context.getRequest().getParameter("presenteDocumentoNumeri");
		this.presenteModalitaIspezione = context.getRequest().getParameter("presenteModalitaIspezione");
		
		this.responsabileProcedimento = context.getRequest().getParameter("responsabileProcedimento");
		this.provvedimentiNonImputabili = context.getRequest().getParameter("provvedimentiNonImputabili");
		this.ncFormaliGiorniRisoluzione = context.getRequest().getParameter("ncFormaliGiorniRisoluzione");
		this.ncSignificativeGiorniRisoluzione = context.getRequest().getParameter("ncSignificativeGiorniRisoluzione");
		this.ncGraviContestazione = context.getRequest().getParameter("ncGraviContestazione");
		this.ncGraviAccertamento = context.getRequest().getParameter("ncGraviAccertamento");
		this.ncGraviDiffida = context.getRequest().getParameter("ncGraviDiffida");
		this.ncGraviSequestroAmministrativo = context.getRequest().getParameter("ncGraviSequestroAmministrativo");
		this.ncGraviSequestroPenale = context.getRequest().getParameter("ncGraviSequestroPenale");
		this.ncGraviBlocco = context.getRequest().getParameter("ncGraviBlocco");
		this.ncGraviDispone = context.getRequest().getParameter("ncGraviDispone");
		this.ncGraviContestazioneDesc = context.getRequest().getParameter("ncGraviContestazioneDesc");
		this.ncGraviDiffidaDesc = context.getRequest().getParameter("ncGraviDiffidaDesc");
		this.ncGraviSequestroAmministrativoDesc = context.getRequest().getParameter("ncGraviSequestroAmministrativoDesc");
		this.ncGraviSequestroPenaleDesc = context.getRequest().getParameter("ncGraviSequestroPenaleDesc");
		this.ncGraviBloccoDesc = context.getRequest().getParameter("ncGraviBloccoDesc");
		this.ncGraviDisponeDesc = context.getRequest().getParameter("ncGraviDisponeDesc");
		this.valutazioneRischio = context.getRequest().getParameter("valutazioneRischio");
		this.presenteDichiarazione = context.getRequest().getParameter("presenteDichiarazione");
		this.note = context.getRequest().getParameter("note");
		this.ispezioneVerifica = context.getRequest().getParameter("ispezioneVerifica");
		this.azioniDescrizione = context.getRequest().getParameter("azioniDescrizione");
		this.ncPunteggioTotale = context.getRequest().getParameter("ncPunteggioTotale");
	}

	public void insert(Connection db) throws SQLException {
		PreparedStatement pst = db.prepareStatement("insert into modello_5_values (id_controllo, enteredby, "
				+ "header_servizio, header_pec1, header_uo, header_pec2, "
				+ "controllo_ore, "
				+ "presente_nome, presente_comune_nascita, presente_data_nascita, presente_comune, presente_indirizzo, presente_civico, presente_documento, presente_documento_numeri, presente_modalita_ispezione,"
				+ "responsabile_procedimento, provvedimenti_non_imputabili, nc_formali_giorni_risoluzione, nc_significative_giorni_risoluzione, nc_gravi_constestazione, nc_gravi_accertamento, nc_gravi_diffida, nc_gravi_sequestro_amministrativo, nc_gravi_sequestro_penale, nc_gravi_blocco, nc_gravi_dispone, nc_gravi_constestazione_desc, nc_gravi_diffida_desc, nc_gravi_sequestro_amministrativo_desc, nc_gravi_sequestro_penale_desc, nc_gravi_blocco_desc, nc_gravi_dispone_desc, valutazione_rischio, presente_dichiarazione, note, ispezione_verifica, azioni_descrizione, nc_punteggio_totale "
				+ ") values ( ?, ?, "
				+ "?, ?, ?, ?, "
				+ "?, "
				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) returning id as id_inserito");
		int i = 0;
		pst.setInt(++i, this.controlloId);
		pst.setInt(++i, this.enteredBy);
		pst.setString(++i, this.headerServizio);
		pst.setString(++i, this.headerPec1);
		pst.setString(++i, this.headerUO);
		pst.setString(++i, this.headerPec2);
		pst.setString(++i, this.controlloOre);
		pst.setString(++i, this.presenteNome);
		pst.setString(++i, this.presenteComuneNascita);
		pst.setString(++i, this.presenteDataNascita);
		pst.setString(++i, this.presenteComune);
		pst.setString(++i, this.presenteIndirizzo);
		pst.setString(++i, this.presenteCivico);
		pst.setString(++i, this.presenteDocumento);
		pst.setString(++i, this.presenteDocumentoNumeri);
		pst.setString(++i, this.presenteModalitaIspezione);
		
		pst.setString(++i, this.responsabileProcedimento);
		pst.setString(++i, this.provvedimentiNonImputabili);
		pst.setString(++i, this.ncFormaliGiorniRisoluzione);
		pst.setString(++i, this.ncSignificativeGiorniRisoluzione);
		pst.setString(++i, this.ncGraviContestazione);
		pst.setString(++i, this.ncGraviAccertamento);
		pst.setString(++i, this.ncGraviDiffida);
		pst.setString(++i, this.ncGraviSequestroAmministrativo);
		pst.setString(++i, this.ncGraviSequestroPenale);
		pst.setString(++i, this.ncGraviBlocco);
		pst.setString(++i, this.ncGraviDispone);
		pst.setString(++i, this.ncGraviContestazioneDesc);
		pst.setString(++i, this.ncGraviDiffidaDesc);
		pst.setString(++i, this.ncGraviSequestroAmministrativoDesc);
		pst.setString(++i, this.ncGraviSequestroPenaleDesc);
		pst.setString(++i, this.ncGraviBloccoDesc);
		pst.setString(++i, this.ncGraviDisponeDesc);
		pst.setString(++i, this.valutazioneRischio);
		pst.setString(++i, this.presenteDichiarazione);
		pst.setString(++i, this.note);
		pst.setString(++i, this.ispezioneVerifica);
		pst.setString(++i, this.azioniDescrizione);
		pst.setString(++i, this.ncPunteggioTotale);

		ResultSet rs = pst.executeQuery();
		if (rs.next())
			this.id = rs.getInt("id_inserito");
	}
	
	public void update(Connection db) throws SQLException {
		PreparedStatement pst = db.prepareStatement("update modello_5_values set modifiedby = ?, modified = now(), "
				+ "header_servizio = ?, header_pec1 = ?, header_uo = ?, header_pec2 = ?, "
				+ "controllo_ore = ?, "
				+ "presente_nome = ?, presente_comune_nascita = ?, presente_data_nascita = ?, presente_comune = ?, presente_indirizzo = ?, presente_civico = ?, presente_documento = ?, presente_documento_numeri = ?, presente_modalita_ispezione = ?, "
				+ "responsabile_procedimento = ?, provvedimenti_non_imputabili = ?, nc_formali_giorni_risoluzione = ?, nc_significative_giorni_risoluzione = ?, nc_gravi_constestazione = ?, nc_gravi_accertamento = ?, nc_gravi_diffida = ?, nc_gravi_sequestro_amministrativo = ?, nc_gravi_sequestro_penale = ?, nc_gravi_blocco = ?, nc_gravi_dispone = ?, nc_gravi_constestazione_desc = ?, nc_gravi_diffida_desc = ?, nc_gravi_sequestro_amministrativo_desc = ?, nc_gravi_sequestro_penale_desc = ?, nc_gravi_blocco_desc = ?, nc_gravi_dispone_desc = ?, valutazione_rischio = ?, presente_dichiarazione = ?, note = ?, ispezione_verifica = ?, azioni_descrizione = ?, nc_punteggio_totale = ? "
				+ "where id = ?");
		int i = 0;
		pst.setInt(++i, this.modifiedBy);
		pst.setString(++i, this.headerServizio);
		pst.setString(++i, this.headerPec1);
		pst.setString(++i, this.headerUO);
		pst.setString(++i, this.headerPec2);
		pst.setString(++i, this.controlloOre);
		pst.setString(++i, this.presenteNome);
		pst.setString(++i, this.presenteComuneNascita);
		pst.setString(++i, this.presenteDataNascita);
		pst.setString(++i, this.presenteComune);
		pst.setString(++i, this.presenteIndirizzo);
		pst.setString(++i, this.presenteCivico);
		pst.setString(++i, this.presenteDocumento);
		pst.setString(++i, this.presenteDocumentoNumeri);
		pst.setString(++i, this.presenteModalitaIspezione);
		
		pst.setString(++i, this.responsabileProcedimento);
		pst.setString(++i, this.provvedimentiNonImputabili);
		pst.setString(++i, this.ncFormaliGiorniRisoluzione);
		pst.setString(++i, this.ncSignificativeGiorniRisoluzione);
		pst.setString(++i, this.ncGraviContestazione);
		pst.setString(++i, this.ncGraviAccertamento);
		pst.setString(++i, this.ncGraviDiffida);
		pst.setString(++i, this.ncGraviSequestroAmministrativo);
		pst.setString(++i, this.ncGraviSequestroPenale);
		pst.setString(++i, this.ncGraviBlocco);
		pst.setString(++i, this.ncGraviDispone);
		pst.setString(++i, this.ncGraviContestazioneDesc);
		pst.setString(++i, this.ncGraviDiffidaDesc);
		pst.setString(++i, this.ncGraviSequestroAmministrativoDesc);
		pst.setString(++i, this.ncGraviSequestroPenaleDesc);
		pst.setString(++i, this.ncGraviBloccoDesc);
		pst.setString(++i, this.ncGraviDisponeDesc);
		pst.setString(++i, this.valutazioneRischio);
		pst.setString(++i, this.presenteDichiarazione);
		pst.setString(++i, this.note);
		pst.setString(++i, this.ispezioneVerifica);
		pst.setString(++i, this.azioniDescrizione);
		pst.setString(++i, this.ncPunteggioTotale);
	
		pst.setInt(++i, this.id);
		pst.execute(); 
	}

	public void updateCu(Connection db) throws SQLException {
		PreparedStatement pst = db.prepareStatement("update ticket set flag_mod5 = ? where ticketid = ?");
		int i = 0;
		pst.setString(++i, "false");
		pst.setInt(++i, this.controlloId);
		pst.execute();
	}
	
		
}


 


	

