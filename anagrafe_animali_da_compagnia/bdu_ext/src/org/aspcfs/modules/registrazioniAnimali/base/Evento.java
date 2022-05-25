/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrazioniAnimali.base;

/**
 * 
 */


import java.net.UnknownHostException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.sinaaf.Sinaaf;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.MethodsUtils;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class Evento extends GenericBean {
	
	private final static Logger log = Logger.getLogger(org.aspcfs.controller.ContextListener.class);

	private int idAnimale = -1;
	private int idEvento;
	private String microchip;
	private int specieAnimaleId = -1;
	private java.sql.Timestamp entered;
	private java.sql.Timestamp modified;
	private int modifiedby = -1;
	private int enteredby = -1;
	private int idTipologiaEvento;
	private int idAslRiferimento = -1;
	private Timestamp dataCancellazione = null;
	private int idUtenteCancellazione=-1;
	private String note = "";
	private String noteCancellazione;
	private String noteInternalUseOnly = "";
	private int idStatoOriginale=-1;
	
	private int idProprietarioCorrente = -1;
	private int idDetentoreCorrente = -1;
	
	private int origineInserimento = 1; //Default BDU
	
	private boolean flagRegistrazioneForzata = false;
	private boolean flagFuoriDominioAsl = false;
	
	public String getNoteCancellazione() {
		return noteCancellazione;
	}

	public void setNoteCancellazione(String noteCancellazione) {
		this.noteCancellazione = noteCancellazione;
	}
	
	
	
	public int getOrigineInserimento() {
		return origineInserimento;
	}

	public void setOrigineInserimento(int origineInserimento) {
		this.origineInserimento = origineInserimento;
	}
	
	
	public void setOrigineInserimento(String origineInserimento) {
		this.origineInserimento = new Integer(origineInserimento);
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNoteInternalUseOnly() {
		return noteInternalUseOnly;
	}

	public void setNoteInternalUseOnly(String noteInternalUseOnly) {
		this.noteInternalUseOnly = noteInternalUseOnly;
	}

	public Evento() {

	}
	
	

	public int getIdProprietarioCorrente() {
		return idProprietarioCorrente;
	}

	public void setIdProprietarioCorrente(int idProprietarioCorrente) {
		this.idProprietarioCorrente = idProprietarioCorrente;
	}
	public void setIdProprietarioCorrente(String idProprietarioCorrente) {
		this.idProprietarioCorrente = new Integer(idProprietarioCorrente);
	}

	public int getIdDetentoreCorrente() {
		return idDetentoreCorrente;
	}

	public void setIdDetentoreCorrente(int idDetentoreCorrente) {
		this.idDetentoreCorrente = idDetentoreCorrente;
	}
	public void setIdDetentoreCorrente(String idDetentoreCorrente) {
		this.idDetentoreCorrente = new Integer (idDetentoreCorrente);
	}
	
	

	/**
	 * @return the entered
	 */
	public java.sql.Timestamp getEntered() {
		return entered;
	}

	/**
	 * @param entered
	 *            the entered to set
	 */
	public void setEntered(java.sql.Timestamp entered) {
		this.entered = entered;
	}

	public void setEntered(String data) {
		this.entered = DateUtils.parseDateStringNew(data, "dd/MM/yyyy");
	}

	/**
	 * @return the modified
	 */
	public java.sql.Timestamp getModified() {
		return modified;
	}

	/**
	 * @param modified
	 *            the modified to set
	 */
	public void setModified(java.sql.Timestamp modified) {
		this.modified = modified;
	}

	public void setModified(String dataModifica) {
		this.modified = DateUtils
				.parseDateStringNew(dataModifica, "dd/MM/yyyy");
	}

	/**
	 * @return the modifiedby
	 */
	public int getModifiedby() {
		return modifiedby;
	}

	/**
	 * @param modifiedby
	 *            the modifiedby to set
	 */
	public void setModifiedby(int modifiedby) {
		this.modifiedby = modifiedby;
	}

	/**
	 * @return the enteredby
	 */
	public int getEnteredby() {
		return enteredby;
	}

	/**
	 * @param enteredby
	 *            the enteredby to set
	 */
	public void setEnteredby(int enteredby) {
		this.enteredby = enteredby;
	}

	/**
	 * @return the microchip
	 */
	public String getMicrochip() {
		return microchip;
	}

	/**
	 * @param microchip
	 *            the microchip to set
	 */
	public void setMicrochip(String microchip) {
		this.microchip = microchip.trim();
	}

	public int getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public int getIdAslRiferimento() {
		return idAslRiferimento;
	}

	public void setIdAslRiferimento(int idAslRiferimento) {
		this.idAslRiferimento = idAslRiferimento;
	}

	public void setIdAslRiferimento(String idAslRiferimento) {
		this.idAslRiferimento = new Integer(idAslRiferimento).intValue();
	}

	public int getIdAnimale() {
		return idAnimale;
	}

	public int getSpecieAnimaleId() {
		return specieAnimaleId;
	}

	public void setSpecieAnimaleId(int specieAnimaleId) {
		this.specieAnimaleId = specieAnimaleId;
	}

	public void setSpecieAnimaleId(String specieAnimaleId) {
		this.specieAnimaleId = new Integer(specieAnimaleId).intValue();
	}

	public void setIdAnimale(int idAnimale) {
		this.idAnimale = idAnimale;
	}

	public void setIdAnimale(String idAnimale) {
		this.idAnimale = new Integer(idAnimale).intValue();
	}

	public int getIdTipologiaEvento() {
		return idTipologiaEvento;
	}

	public void setIdTipologiaEvento(int idTipologiaEvento) {
		this.idTipologiaEvento = idTipologiaEvento;
	}

	public void setIdTipologiaEvento(String idTipologiaEvento) {
		this.idTipologiaEvento = new Integer(idTipologiaEvento).intValue();
	}
	
	public Timestamp getDataCancellazione(){
		return dataCancellazione;
	}
	public int getIdUtenteCancellazione(){
		return idUtenteCancellazione;
	}
	public void setIdUtenteCancellazione(int idUtente){
		idUtenteCancellazione=idUtente;
	}
	
	


	public boolean isFlagFuoriDominioAsl() {
		return flagFuoriDominioAsl;
	}

	public void setFlagFuoriDominioAsl(boolean flagFuoriDominioAsl) {
		this.flagFuoriDominioAsl = flagFuoriDominioAsl;
	}

	public boolean isFlagRegistrazioneForzata() {
		return flagRegistrazioneForzata;
	}

	public void setFlagRegistrazioneForzata(boolean flagRegistrazioneForzata) {
		this.flagRegistrazioneForzata = flagRegistrazioneForzata;
	}
	
	public void setFlagRegistrazioneForzata(String flagRegistrazioneForzata) {
		this.flagRegistrazioneForzata = DatabaseUtils
		.parseBoolean(flagRegistrazioneForzata);
	}


	protected void buildRecord(ResultSet rs) throws SQLException {
		
		this.idAnimale = rs.getInt("id_animale");
		this.idEvento = rs.getInt("idevento");
		this.entered = rs.getTimestamp("entered");
		this.enteredby = rs.getInt("id_utente_inserimento");
		this.modified = rs.getTimestamp("modified");
		this.modifiedby = rs.getInt("id_utente_modifica");
		this.microchip = rs.getString("microchip");
		this.idTipologiaEvento = rs.getInt("id_tipologia_evento");
		this.idAslRiferimento = rs.getInt("idaslinserimentoevento");
		this.specieAnimaleId = rs.getInt("id_specie_animale");
		this.noteInternalUseOnly = rs.getString("note_internal_use_only");
		this.dataCancellazione = rs.getTimestamp("data_cancellazione");
		this.idUtenteCancellazione = rs.getInt("id_utente_cancellazione");
		this.noteCancellazione = rs.getString("note_cancellazione");
		this.idStatoOriginale = rs.getInt("id_stato_originale");
		this.note = rs.getString("note");
		this.idProprietarioCorrente = rs.getInt("id_proprietario_corrente");
        this.idDetentoreCorrente = rs.getInt("id_detentore_corrente");
        this.origineInserimento = rs.getInt("origine_registrazione");
        this.flagRegistrazioneForzata = rs.getBoolean("inserimento_registrazione_forzato");
        this.flagFuoriDominioAsl = rs.getBoolean("flag_operazione_eseguita_fuori_dominio_asl");
	}

	public boolean insert(Connection db) throws SQLException {
		
		log.info("Inserendo registrazione "+idTipologiaEvento +" per microchip " + microchip);
		
		
		if (System.getProperty("DEBUG") != null) 
		log.info("Inserendo registrazione "+idTipologiaEvento +" per microchip " + microchip);
		StringBuffer sql = new StringBuffer();
		
		try {
		

		//	idEvento = DatabaseUtils.getNextSeq(db, "evento_id_evento_seq");
			// sql.append("INSERT INTO animale (");

			sql.append("INSERT INTO evento(" + "id_animale, ");

			sql
					.append("microchip, entered, modified, id_utente_inserimento, id_tipologia_evento, id_specie_animale, id_proprietario_corrente, " +
							"id_detentore_corrente, inserimento_registrazione_forzato, flag_operazione_eseguita_fuori_dominio_asl");

			if (modifiedby > -1) {
				sql.append(", id_utente_modifica");
			}

			if (idAslRiferimento > -1) {
				sql.append(", id_asl");
			}
			
			if (noteInternalUseOnly != null && !("").equals(noteInternalUseOnly)){
				sql.append(", note_internal_use_only");
			}
			
			if (idStatoOriginale > 0){
				sql.append(", id_stato_originale");
			}
			
			if (note != null && !("").equals(note)){
				sql.append(", note");
			}
			
			
			sql.append(", origine_registrazione");

			sql.append(") VALUES ( ?, ?,");

			if (entered != null) {
				sql.append("?, ");
			} else {
				sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
			}

			if (modified != null) {
				sql.append("?, ");
			} else {
				sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
			}

			sql.append(" ?, ?, ?, ?, ?, ?, ?");

			if (modifiedby > -1) {
				sql.append(",?");
			}

			if (idAslRiferimento > -1) {
				sql.append(", ?");
			}
			
			if (noteInternalUseOnly != null && !("").equals(noteInternalUseOnly)){
				sql.append(", ?");
			}
			
			if (idStatoOriginale > 0){
				sql.append(", ?");
			}
			
			
			if (note != null && !("").equals(note)){
				sql.append(", ?");
			}
			
			sql.append(", ?");


			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idAnimale);

			pst.setString(++i, microchip);

			if (entered != null) {
				pst.setTimestamp(++i, entered);
			}

			if (modified != null) {
				pst.setTimestamp(++i, modified);
			}

			pst.setInt(++i, enteredby);
			pst.setInt(++i, idTipologiaEvento);
			pst.setInt(++i, specieAnimaleId);
			pst.setInt(++i, idProprietarioCorrente);
			pst.setInt(++i, idDetentoreCorrente);
			pst.setBoolean(++i, flagRegistrazioneForzata);
			pst.setBoolean(++i, flagFuoriDominioAsl);

			if (modifiedby > -1) {
				pst.setInt(++i, modifiedby);
			}

			if (idAslRiferimento > -1) {
				pst.setInt(++i, idAslRiferimento);
			}
			
			if (noteInternalUseOnly != null && !("").equals(noteInternalUseOnly)){
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");				
				pst.setString(++i, noteInternalUseOnly + " [" + dateFormat.format(new Date()) +"] ");
			}
			
			if (idStatoOriginale > 0){
				pst.setInt(++i, idStatoOriginale);
			}
			
			
			
			if (note != null && !("").equals(note)){
				pst.setString(++i, note);
			}
			
			
			pst.setInt(++i, origineInserimento);
			

			pst.execute();
			pst.close();

			this.idEvento = DatabaseUtils.getCurrVal(db,
					"evento_id_evento_seq", idEvento);
			
		} catch (Exception e) {
			
			throw new SQLException(e.getMessage());
		} finally {
		
		}
		return true;

	}

	
	// restituisce la data della registrazione in base alla tipologia d evento
	// che stiamo considerando
	public java.sql.Timestamp getDataRegistrazione() {
		
		System.out.println("Sono in getdataregistrazione...........");

		if (this.getIdTipologiaEvento() == EventoRegistrazioneBDU.idTipologiaDB) {
			return ((EventoRegistrazioneBDU) this).getDataRegistrazione();

		}

		if (this.getIdTipologiaEvento() == EventoSmarrimento.idTipologiaDB) {
			return ((EventoSmarrimento) this).getDataSmarrimento();
		}
		if (this.getIdTipologiaEvento() == EventoPresaInCaricoDaAdozioneFuoriAsl.idTipologiaDB) {
			return ((EventoPresaInCaricoDaAdozioneFuoriAsl) this).getDataPresaAdozione();
		}
		if (this.getIdTipologiaEvento() == EventoCattura.idTipologiaDB) {
			return ((EventoCattura) this).getDataCattura();
		}
		if (this.getIdTipologiaEvento() == EventoPrelievoLeishmania.idTipologiaDB) {
			return ((EventoPrelievoLeishmania) this).getDataPrelievoLeishamania();
		}
		if (this.getIdTipologiaEvento() == EventoCattura.idTipologiaDBRicattura) {
			return ((EventoCattura) this).getDataCattura();
		}
		if (this.getIdTipologiaEvento() == EventoMorsicatura.idTipologiaDB) {
			return ((EventoMorsicatura) this).getDataMorso();
		}
		if (this.getIdTipologiaEvento() == EventoAggressione.idTipologiaDB) {
			return ((EventoAggressione) this).getDataAggressione();
		}
		if (this.getIdTipologiaEvento() == EventoRilascioPassaporto.idTipologiaDB || this.getIdTipologiaEvento() == EventoRilascioPassaporto.idTipologiaRinnovoDB) {
			return ((EventoRilascioPassaporto) this)
					.getDataRilascioPassaporto();
		}
		if (this.getIdTipologiaEvento() == EventoDecesso.idTipologiaDB) {
			return ((EventoDecesso) this).getDataDecesso();
		}
		if (this.getIdTipologiaEvento() == EventoRegistrazioneEsitoControlliCommerciali.idTipologiaDB) {
			return ((EventoRegistrazioneEsitoControlliCommerciali) this)
					.getDataInserimentoControlli();
		}
		if (this.getIdTipologiaEvento() == EventoEsitoControlli.idTipologiaDB) {
			return ((EventoEsitoControlli) this).getDataEsito();
		}
		if (this.getIdTipologiaEvento() == EventoReimmissione.idTipologiaDB) {
			return ((EventoReimmissione) this).getDataReimmissione();
		}
		if (this.getIdTipologiaEvento() == EventoAdozioneDaCanile.idTipologiaDB  || this.getIdTipologiaEvento() == EventoAdozioneDaCanile.idTipologiaAdozionePerCaniliDB) {
			return ((EventoAdozioneDaCanile) this).getDataAdozione();
		}
		if (this.getIdTipologiaEvento() == EventoCambioDetentore.idTipologiaDB) {
			return ((EventoCambioDetentore) this).getDataModificaDetentore();
		}
		if (this.getIdTipologiaEvento() == EventoRestituzioneAProprietario.idTipologiaDB) {
			return ((EventoRestituzioneAProprietario) this).getDataRestituzione();
		}
		if (this.getIdTipologiaEvento() == EventoCessione.idTipologiaDB) {
			return ((EventoCessione) this).getDataCessione();
		}
		if (this.getIdTipologiaEvento() == EventoInserimentoMicrochip.idTipologiaDB) {
			return ((EventoInserimentoMicrochip) this)
					.getDataInserimentoMicrochip();
		}
		if (this.getIdTipologiaEvento() == EventoInserimentoMicrochip.idTipologiaDBSecondoMicrochip) {
			return ((EventoInserimentoMicrochip) this).getDataInserimentoMicrochip();
		}
		if (this.getIdTipologiaEvento() == EventoRestituzioneACanile.idTipologiaDB) {
			return ((EventoRestituzioneACanile) this)
					.getDataRestituzioneCanile();
		}
		if (this.getIdTipologiaEvento() == EventoPresaInCaricoDaCessione.idTipologiaDB || this.getIdTipologiaEvento() == EventoPresaInCaricoDaCessione.idTipologiaDBOperatoreCommerciale || this.getIdTipologiaEvento() == EventoPresaInCaricoDaCessione.idTipologiaDBRandagioAPrivato) {
			return ((EventoPresaInCaricoDaCessione) this)
					.getDataPresaCessione();
		}
		if (this.getIdTipologiaEvento() == EventoTrasferimento.idTipologiaDB) {
			return ((EventoTrasferimento) this).getDataTrasferimento();
		}
		if (this.getIdTipologiaEvento() == EventoTrasferimentoFuoriRegione.idTipologiaDB) {
			return ((EventoTrasferimentoFuoriRegione) this)
					.getDataTrasferimentoFuoriRegione();
		}
		if (this.getIdTipologiaEvento() == EventoSterilizzazione.idTipologiaDB) {
			return ((EventoSterilizzazione) this).getDataSterilizzazione();
		}
		if (this.getIdTipologiaEvento() ==  EventoEsitoControlli.idTipologiaDB){
			return ((EventoEsitoControlli) this).getDataEsito();
			}
		
		if (this.getIdTipologiaEvento() ==  EventoRitrovamento.idTipologiaDB){
			return ((EventoRitrovamento) this).getDataRitrovamento();
			}
		if (this.getIdTipologiaEvento() ==  EventoRitrovamentoNonDenunciato.idTipologiaDB){
			return ((EventoRitrovamentoNonDenunciato) this).getDataRitrovamentoNd();
			}
		if (this.getIdTipologiaEvento() ==  EventoRientroFuoriStato.idTipologiaDB){
			return ((EventoRientroFuoriStato) this).getDataRientroFuoriStato();
			}
		if (this.getIdTipologiaEvento() ==  EventoRientroFuoriRegione.idTipologiaDB){
			return ((EventoRientroFuoriRegione) this).getDataRientroFR();
			}
		if (this.getIdTipologiaEvento() == EventoInserimentoVaccinazioni.idTipologiaDB){
			return  ((EventoInserimentoVaccinazioni) this).getDataVaccinazione();
		}
		if (this.getIdTipologiaEvento() == EventoTrasferimentoCanile.idTipologiaDB){
			return  ((EventoTrasferimentoCanile) this).getDataTrasferimentoCanile();
		}
		
		if (this.getIdTipologiaEvento() == EventoAdozioneDaColonia.idTipologiaDB){
			return  ((EventoAdozioneDaColonia) this).getDataAdozioneColonia();
		}
		
		if (this.getIdTipologiaEvento() == EventoAdozioneFuoriAsl.idTipologiaDB){
			return  ((EventoAdozioneFuoriAsl) this).getDataAdozioneFuoriAsl(); 
		}
		
		if (this.getIdTipologiaEvento() == EventoAdozioneFuoriAsl.idTipologiaDB_obsoleto){
			return  ((EventoAdozioneFuoriAsl) this).getDataAdozioneFuoriAsl(); 
		}
		
		if (this.getIdTipologiaEvento() == EventoBloccoAnimale.idTipologiaDB){
			return ((EventoBloccoAnimale)this).getDataBlocco();
		}
		
		if (this.getIdTipologiaEvento() == EventoSbloccoAnimale.idTipologiaDB){
			return ((EventoSbloccoAnimale)this).getDataSblocco();
		}
		
		if (this.getIdTipologiaEvento() == EventoMutilazione.idTipologiaDB){
			return ((EventoMutilazione)this).getDataMutilazione();
		}
		if (this.getIdTipologiaEvento() == EventoAllontanamento.idTipologiaDB){
			return ((EventoAllontanamento)this).getDataAllontanamento();
		}
		
		return this.getEntered();
	}
	
	public Operatore getIdProprietarioOriginarioRegistrazione() { //Attenzione restituisce l'oggetto operatore, non l'id
		Connection conn = null;
		int idOperatore = -1;
		Operatore proprietarioDestinatarioRegistrazione = null;
		try {
//			String dbName = ApplicationProperties.getProperty("dbnameBdu");
//			String username = ApplicationProperties
//					.getProperty("usernameDbbdu");
//			String pwd = ApplicationProperties.getProperty("passwordDbbdu");
//			String host = InetAddress.getByName("hostDbBdu").getHostAddress();
//
//			conn = DbUtil.getConnection(dbName, username, pwd, host);
			Thread t = Thread.currentThread();
			conn = GestoreConnessioni.getConnection();

/*			System.out.println("Proprietario:   IdEvento =   " + this.idEvento
					+ "  IdTipologia=   " + this.idTipologiaEvento);*/
			switch (this.getIdTipologiaEvento()) {
			
			
			case EventoAdozioneDaCanile.idTipologiaDB: {
				idOperatore = ((EventoAdozioneDaCanile)this).getIdVecchioProprietario();
				break;
			}
			
			case EventoSterilizzazione.idTipologiaDB: {
				idOperatore = ((EventoSterilizzazione)this).getIdProprietarioCorrente();
				break;
			}
			
			case EventoInserimentoMicrochip.idTipologiaDB: {
				idOperatore = ((EventoInserimentoMicrochip)this).getIdProprietarioCorrente();
				break;
			}
			
			case EventoDecesso.idTipologiaDB: {
				idOperatore = ((EventoDecesso)this).getIdProprietarioCorrente();
				break;
			}
			
			
			case EventoAdozioneDaColonia.idTipologiaDB: {
				idOperatore = ((EventoAdozioneDaColonia)this).getIdVecchioProprietario();
				break;
			}
			
			
			case EventoCattura.idTipologiaDB: {
				idOperatore = ((EventoCattura)this).getIdProprietario();
				break;
			}
			
			case EventoCattura.idTipologiaDBRicattura: {
				idOperatore = ((EventoCattura)this).getIdProprietario();
				break;
			}
			
			case EventoCessione.idTipologiaDB: {
				idOperatore = ((EventoCessione) this).getIdVecchioProprietario();
				break;
			}
			
			
			case EventoRegistrazioneBDU.idTipologiaDB:{
				idOperatore = ((EventoRegistrazioneBDU)this).getIdProprietarioCorrente();
				break;
			}
			
			

			case EventoRestituzioneACanile.idTipologiaDB:{
				idOperatore = ((EventoRestituzioneACanile)this).getIdProprietarioDaRestituzione();
				break;
			}
			
			
			case EventoTrasferimento.idTipologiaDB: {
				idOperatore = ((EventoTrasferimento)this).getIdVecchioProprietario();
				break;
			}
			
			case EventoTrasferimentoFuoriRegione.idTipologiaDB: {
				idOperatore = ((EventoTrasferimentoFuoriRegione)this).getIdVecchioProprietario();
				break;
			}
			
			case EventoTrasferimentoFuoriStato.idTipologiaDB: {
				idOperatore = ((EventoTrasferimentoFuoriStato)this).getIdVecchioProprietario();
				break;
			}
			
			
			//PER IL RIENTRO FUORI STATO NELLA COLONNA PROP DET VISUALIZZO IL NUOVO PROP/DET
			case EventoRientroFuoriStato.idTipologiaDB: {
				idOperatore = ((EventoRientroFuoriStato)this).getIdProprietario();
				break;
			}
			
			
			case EventoAdozioneFuoriAsl.idTipologiaDB: {
				idOperatore = ((EventoAdozioneFuoriAsl)this).getIdVecchioProprietario();
				break;
			}
			
			case EventoAdozioneFuoriAsl.idTipologiaDB_obsoleto: {
				idOperatore = ((EventoAdozioneFuoriAsl)this).getIdVecchioProprietario();
				break;
			}
			
			////////
	
			
			default: {
				idOperatore = -1;
				break;
			}
			}

			if (idOperatore != -1 && idOperatore != 0) {
			//	
				proprietarioDestinatarioRegistrazione = new Operatore();
				proprietarioDestinatarioRegistrazione
						.queryRecordOperatorebyIdLineaProduttiva(conn,
								idOperatore);
			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//DbUtil.chiudiConnessioneJDBC(null, null, conn);
			GestoreConnessioni.freeConnection(conn);
			
		}

		return proprietarioDestinatarioRegistrazione;
	}
	

	public Operatore getIdProprietarioProvenienzaOp() { //Attenzione restituisce l'oggetto operatore, non l'id
		Connection conn = null;
		int idOperatore = -1;
		Operatore proprietarioProvenienza = null;
		try {
			Thread t = Thread.currentThread();
			conn = GestoreConnessioni.getConnection();
			switch (this.getIdTipologiaEvento()) {
			
			
			case EventoRegistrazioneBDU.idTipologiaDB:{
				idOperatore = ((EventoRegistrazioneBDU)this).getIdProprietarioProvenienza();
				break;
			}
	
			
			default: {
				idOperatore = -1;
				break;
			}
			}

			if (idOperatore != -1 && idOperatore != 0) {
			//	
				proprietarioProvenienza = new Operatore();
				proprietarioProvenienza
						.queryRecordOperatorebyIdLineaProduttiva(conn,
								idOperatore);
			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//DbUtil.chiudiConnessioneJDBC(null, null, conn);
			GestoreConnessioni.freeConnection(conn);
			
		}

		return proprietarioProvenienza;
	}

	
	public Operatore getIdDetentoreOriginarioRegistrazione(){
		 //Attenzione restituisce l'oggetto operatore, non l'id
		Connection conn = null;
		int idOperatore = -1;
		Operatore proprietarioDestinatarioRegistrazione = null;
		try {
			
//			String dbName = ApplicationProperties.getProperty("dbnameBdu");
//			String username = ApplicationProperties
//					.getProperty("usernameDbbdu");
//			String pwd = ApplicationProperties.getProperty("passwordDbbdu");
//			String host = InetAddress.getByName("hostDbBdu").getHostAddress();
//
//			conn = DbUtil.getConnection(dbName, username, pwd, host);
			Thread t = Thread.currentThread();
			conn = GestoreConnessioni.getConnection();

/*			System.out.println("Proprietario:   IdEvento =   " + this.idEvento
					+ "  IdTipologia=   " + this.idTipologiaEvento);*/
			switch (this.getIdTipologiaEvento()) {
			
			case EventoAdozioneDaCanile.idTipologiaDB: {
				idOperatore = ((EventoAdozioneDaCanile)this).getIdVecchioDetentore();
				break;
			}
			
			case EventoSterilizzazione.idTipologiaDB: {
				idOperatore = ((EventoSterilizzazione)this).getIdDetentoreCorrente();
				break;
			}
			
			case EventoInserimentoMicrochip.idTipologiaDB: {
				idOperatore = ((EventoInserimentoMicrochip)this).getIdProprietarioCorrente();
				break;
			}
			
			case EventoAdozioneDaColonia.idTipologiaDB: {
				idOperatore = ((EventoAdozioneDaColonia)this).getIdVecchioDetentore();
				break;
			}
			
			case EventoDecesso.idTipologiaDB: {
				idOperatore = ((EventoDecesso)this).getIdDetentoreCorrente();
				break;
			}
			
			case EventoCambioDetentore.idTipologiaDB: {
				idOperatore = ((EventoCambioDetentore)this).getIdVecchioDetentore();
				break;
			}
			
			case EventoRestituzioneAProprietario.idTipologiaDB: {
				idOperatore = ((EventoRestituzioneAProprietario)this).getIdVecchioDetentore();
				break;
			}
			
			case EventoCattura.idTipologiaDB: {
				idOperatore = ((EventoCattura)this).getIdDetentore();
				break;
			}
			
			case EventoCattura.idTipologiaDBRicattura: {
				idOperatore = ((EventoCattura)this).getIdProprietario();
				break;
			}
			
			
			
			case EventoCessione.idTipologiaDB: {
				idOperatore = ((EventoCessione)this).getIdVecchioDetentore();
				break;
			
			}
			
			

			case EventoRegistrazioneBDU.idTipologiaDB:{
				idOperatore = ((EventoRegistrazioneBDU)this).getIdDetentoreCorrente();
				break;
			}
			
			case EventoTrasferimento.idTipologiaDB: {
				idOperatore = ((EventoTrasferimento)this).getIdVecchioDetentore();
				break;
			}
			
			case EventoTrasferimentoSindaco.idTipologiaDB: {
				idOperatore = ((EventoTrasferimentoSindaco)this).getIdVecchioDetentore();
				break;
			}
			

			case EventoTrasferimentoFuoriRegione.idTipologiaDB: {
				idOperatore = ((EventoTrasferimentoFuoriRegione)this).getIdVecchioDetentore();
				break;
			}
			
			
			case EventoTrasferimentoFuoriStato.idTipologiaDB: {
				idOperatore = ((EventoTrasferimentoFuoriStato)this).getIdVecchioDetentore();
				break;
			}
			
			
			//PER IL RIENTRO FUORI STATO NELLA COLONNA PROP DET VISUALIZZO IL NUOVO PROP/DET
			case EventoRientroFuoriStato.idTipologiaDB: {
				idOperatore = ((EventoRientroFuoriStato)this).getIdProprietario();
				break;
			}
			
			case EventoReimmissione.idTipologiaDB: {
				idOperatore = ((EventoReimmissione)this).getIdDetentorePrecedente();
				break;
			}
			
			
			case EventoAdozioneFuoriAsl.idTipologiaDB: {
				idOperatore = ((EventoAdozioneFuoriAsl)this).getIdVecchioDetentore();
				break;
			}
			
			case EventoAdozioneFuoriAsl.idTipologiaDB_obsoleto: {
				idOperatore = ((EventoAdozioneFuoriAsl)this).getIdVecchioDetentore();
				break;
			}
			
			
			/*case EventoRestituzioneAslOrigine.idTipologiaDB: {
				idOperatore = ((EventoRestituzioneAslOrigine)this).getIdCanileOrigine();
				break;
			}*/
			
			
			////////
		
			
			default: {
				idOperatore = -1;
				break;
			}
			}

			if (idOperatore != -1 && idOperatore != 0) {
				//
				proprietarioDestinatarioRegistrazione = new Operatore();
				proprietarioDestinatarioRegistrazione
						.queryRecordOperatorebyIdLineaProduttiva(conn,
								idOperatore);
			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//DbUtil.chiudiConnessioneJDBC(null, null, conn);
			GestoreConnessioni.freeConnection(conn);
			
			conn = null;
		}

		return proprietarioDestinatarioRegistrazione;
	
	}
	public Operatore getIdProprietarioDestinatarioRegistrazione() { //Attenzione restituisce l'oggetto operatore, non l'id
		Connection conn = null;
		int idOperatore = -1;
		Operatore proprietarioDestinatarioRegistrazione = null;
		try {
			
//			String dbName = ApplicationProperties.getProperty("dbnameBdu");
//			String username = ApplicationProperties
//					.getProperty("usernameDbbdu");
//			String pwd = ApplicationProperties.getProperty("passwordDbbdu");
//			String host = InetAddress.getByName("hostDbBdu").getHostAddress();
//
//			conn = DbUtil.getConnection(dbName, username, pwd, host);
			Thread t = Thread.currentThread();
			conn = GestoreConnessioni.getConnection();

		/*	System.out.println("Proprietario:   IdEvento =   " + this.idEvento
					+ "  IdTipologia=   " + this.idTipologiaEvento);*/
			switch (this.getIdTipologiaEvento()) {
			
			
			case EventoAdozioneDaCanile.idTipologiaDB: {
				idOperatore = ((EventoAdozioneDaCanile)this).getId();
				break;
			}
			
			case EventoAdozioneDaColonia.idTipologiaDB: {
				idOperatore = ((EventoAdozioneDaColonia)this).getIdProprietario();
				break;
			}
			
			
			case EventoCessione.idTipologiaDB: {
				idOperatore = ((EventoCessione)this).getIdProprietario();
				break;
			}
			
			
			
			case EventoPresaInCaricoDaCessione.idTipologiaDB:{
				idOperatore = ((EventoPresaInCaricoDaCessione)this).getIdProprietario();
				break;
			}
			
			
			case EventoPresaInCaricoDaAdozioneFuoriAsl.idTipologiaDB:{
				idOperatore = ((EventoPresaInCaricoDaAdozioneFuoriAsl)this).getIdProprietario();
				break;
			}
			
			case EventoRestituzioneACanile.idTipologiaDB:{
				idOperatore = ((EventoRestituzioneACanile)this).getIdProprietario();
				break;
			}
			
			
			case EventoTrasferimento.idTipologiaDB: {
				idOperatore = ((EventoTrasferimento)this).getIdProprietario();
				break;
			}
			
			case EventoTrasferimentoCanile.idTipologiaDB: {
				idOperatore = ((EventoTrasferimentoCanile)this).getIdDetentore();
				break;
			}
			
			
			case EventoCattura.idTipologiaDBRicattura : {
			idOperatore = ((EventoCattura)this).getIdDetentore();
			break;
		}
			
			/////
			
//			case EventoRegistrazioneBDU.idTipologiaDB: {
//				idOperatore = ((EventoRegistrazioneBDU) this).getIdProprietario();
//				break;
//
//			}
//			
//			case EventoTrasferimento.idTipologiaDB: {
//				idOperatore = ((EventoTrasferimento) this).getIdProprietario();
//				break;
//
//			}
//			case EventoCessione.idTipologiaDB: {
//				idOperatore = ((EventoCessione) this).getIdVecchioProprietario();
//				break;
//			}
//			case EventoPresaInCaricoDaCessione.idTipologiaDB: {
//				idOperatore = ((EventoPresaInCaricoDaCessione) this)
//						.getIdProprietario();
//				break;
//			}
//			case EventoAdozioneDaCanile.idTipologiaDB: {
//				idOperatore = ((EventoAdozioneDaCanile) this)
//						.getIdProprietario();
//				break;
//			}
//			case EventoRestituzioneACanile.idTipologiaDB: {
//				idOperatore = ((EventoRestituzioneACanile) this)
//						.getIdProprietario();
//				break;
//			}
//			
//			case EventoAdozioneDaColonia.idTipologiaDB: {
//				idOperatore = ((EventoAdozioneDaColonia) this)
//						.getIdProprietario();
//				break;
//			}
//			
//			case EventoRitrovamento.idTipologiaDB: {
//				idOperatore = ((EventoRitrovamento) this).getIdProprietario();
//				break;
//			}
//			case EventoTrasferimentoFuoriRegione.idTipologiaDB: {
//				idOperatore = ((EventoTrasferimentoFuoriRegione) this).getIdVecchioProprietario();
//				break;
//			}
//			case EventoRientroFuoriRegione.idTipologiaDB: {
//				idOperatore = ((EventoRientroFuoriRegione)this).getIdProprietario();
//				break;
//			}
//			case EventoCattura.idTipologiaDB : {
//				idOperatore = ((EventoCattura)this).getIdProprietario();
//				break;
//			}
//			case EventoRientroFuoriStato.idTipologiaDB : {
//				idOperatore = ((EventoRientroFuoriStato)this).getIdProprietario();
//				break;
//			}

			default: {
				idOperatore = -1;
				break;
			}
			}

			if (idOperatore != -1 && idOperatore != 0) {
				//
				proprietarioDestinatarioRegistrazione = new Operatore();
				proprietarioDestinatarioRegistrazione
						.queryRecordOperatorebyIdLineaProduttiva(conn,
								idOperatore);
			}

		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		//	DbUtil.chiudiConnessioneJDBC(null, null, conn);
			GestoreConnessioni.freeConnection(conn);
			conn = null;
		}

		return proprietarioDestinatarioRegistrazione;
	}

	public Operatore getIdDetentoreDestinatarioRegistrazione() { //restituisce l'oggetto operatore
		Connection conn = null;
		int idOperatore = -1;
		Operatore proprietarioDestinatarioRegistrazione = null;
		try {
			
			
//			String dbName = ApplicationProperties.getProperty("dbnameBdu");
//			String username = ApplicationProperties
//					.getProperty("usernameDbbdu");
//			String pwd = ApplicationProperties.getProperty("passwordDbbdu");
//			String host = InetAddress.getByName("hostDbBdu").getHostAddress();
//			conn = DbUtil.getConnection(dbName, username, pwd, host);
			
			Thread t = Thread.currentThread();
			conn = GestoreConnessioni.getConnection();
			
			
			//conn = GestoreConnessioni.getConnection();

		//	System.out.println("Detentore:   IdEvento =   " + this.idEvento
				//	+ "  IdTipologia=   " + this.idTipologiaEvento);

			switch (this.getIdTipologiaEvento()) {
			
			case EventoAdozioneDaCanile.idTipologiaDB: {
				idOperatore = ((EventoAdozioneDaCanile)this).getId();
				break;
			}
			
			
			case EventoAdozioneDaColonia.idTipologiaDB: {
				idOperatore = ((EventoAdozioneDaColonia)this).getIdProprietario();
				break;
			}
			
			case EventoCambioDetentore.idTipologiaDB: {
				idOperatore = ((EventoCambioDetentore)this).getIdDetentore();
				break;
			}
			case EventoRestituzioneAProprietario.idTipologiaDB: {
				idOperatore = ((EventoRestituzioneAProprietario)this).getIdDetentore();
				break;
			}
			
			
			case EventoCessione.idTipologiaDB: {
				idOperatore = ((EventoCessione)this).getIdNuovoDetentore();
				break;
			}
			
			
			case EventoPresaInCaricoDaCessione.idTipologiaDB:{
				idOperatore = ((EventoPresaInCaricoDaCessione)this).getIdProprietario();
				break;
			}
			
			
			case EventoTrasferimento.idTipologiaDB: {
				idOperatore = ((EventoTrasferimento)this).getIdDetentore();
				break;
			}
			
			
//			
//			
//			
//			/////////////////////////
//			
//			case EventoRegistrazioneBDU.idTipologiaDB: {
//				idOperatore = ((EventoRegistrazioneBDU) this).getIdDetentore();
//				break;
//
//			}
//			
//			case EventoTrasferimento.idTipologiaDB: {
//				idOperatore = ((EventoTrasferimento) this).getIdDetentore();
//				break;
//
//			}
//			case EventoCessione.idTipologiaDB: {
//				idOperatore = ((EventoCessione) this).getIdNuovoDetentore();
//				break;
//			}
//				/*
//				 * case EventoPresaInCaricoDaCessione.idTipologiaDB:{
//				 * idOperatore = ((EventoPresaInCaricoDaCessione) this).get;
//				 * break; }
//				 */
//			case EventoAdozioneDaCanile.idTipologiaDB: {
//				idOperatore = ((EventoAdozioneDaCanile) this)
//						.getIdProprietario();
//				break;
//			}
//			
//			case EventoAdozioneDaColonia.idTipologiaDB: {
//				idOperatore = ((EventoAdozioneDaColonia) this)
//						.getIdProprietario();
//				break;
//			}
//			
//			case EventoRitrovamento.idTipologiaDB: {
//				idOperatore = ((EventoRitrovamento) this).getIdDetentore();
//				break;
//			}case EventoCambioDetentore.idTipologiaDB: {
//				idOperatore = ((EventoCambioDetentore) this).getIdDetentore();
//				break;
//			}
//			case EventoRestituzioneACanile.idTipologiaDB: {
//				idOperatore = ((EventoRestituzioneACanile) this)
//						.getIdDetentore();
//				break;
//			}
//			case EventoTrasferimentoFuoriRegione.idTipologiaDB: {
//				idOperatore = ((EventoTrasferimentoFuoriRegione) this).getIdVecchioDetentore();
//				break;
//			}
//			case EventoTrasferimentoCanile.idTipologiaDB: {
//				idOperatore = ((EventoTrasferimentoCanile) this).getIdDetentore();
//				break;
//			}
//			case EventoRientroFuoriRegione.idTipologiaDB: {
//				idOperatore = ((EventoRientroFuoriRegione)this).getIdDetentore();
//				break;
//			}
//			case EventoCattura.idTipologiaDB : {
//				idOperatore = ((EventoCattura)this).getIdDetentore();
//				break;
//			}
//			case EventoRientroFuoriStato.idTipologiaDB : {
//				idOperatore = ((EventoRientroFuoriStato)this).getIdDetentore();
//				break;
//			}
			
			default: {
				idOperatore = -1;
				break;
			}
			}

			if (idOperatore != -1 && idOperatore != 0) {
			//	
				proprietarioDestinatarioRegistrazione = new Operatore();
				proprietarioDestinatarioRegistrazione
						.queryRecordOperatorebyIdLineaProduttiva(conn,
								idOperatore);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//DbUtil.chiudiConnessioneJDBC(null, null, conn);
			GestoreConnessioni.freeConnection(conn);
			conn = null;
		}

		return proprietarioDestinatarioRegistrazione;
	}
	
	
	public static Evento getEventoFromTipologiaId(Connection db, int idTipologia, int idEvento){
	
		
		
		Evento evento = null;
		try{
			
	
			
			
		evento = (Evento)	Class.forName("org.aspcfs.modules.registrazioniAnimali.base."+ApplicationProperties.getProperty(String
					.valueOf(idTipologia))).getConstructor(Connection.class, int.class ).newInstance(new Object[] { db, idEvento });
			
		
			
//		switch (idTipologia) {
//
//		case EventoRegistrazioneBDU.idTipologiaDB:
//
//		{
//			EventoRegistrazioneBDU evento = new EventoRegistrazioneBDU(db, idEvento);
//			return evento;
//			
//		}
//		
//		case EventoInserimentoMicrochip.idTipologiaDB:
//
//		{
//			EventoInserimentoMicrochip evento = new EventoInserimentoMicrochip(db, idEvento);
//			return evento;
//			
//		}
//		
//		case EventoInserimentoMicrochip.idTipologiaDBSecondoMicrochip:
//
//		{
//			EventoInserimentoMicrochip evento = new EventoInserimentoMicrochip(db, idEvento);
//			return evento;
//			
//		}
//
//		case EventoSterilizzazione.idTipologiaDB:
//
//		{
//			EventoSterilizzazione evento = new EventoSterilizzazione(db, idEvento);
//			return evento;
//		}
//
//		case EventoFurto.idTipologiaDB:
//
//		{
//			EventoFurto evento = new EventoFurto(db, idEvento);
//			return evento;
//		}
//
//		case EventoDecesso.idTipologiaDB:
//
//		{
//			EventoDecesso evento = new EventoDecesso(db, idEvento);
//			return evento;
//		}
//
//		case EventoRitrovamento.idTipologiaDB:
//
//		{
//			EventoRitrovamento evento = new EventoRitrovamento(db, idEvento);
//			return evento;
//		}
//		case EventoRitrovamentoNonDenunciato.idTipologiaDB:
//
//		{
//			EventoRitrovamentoNonDenunciato evento = new EventoRitrovamentoNonDenunciato(db, idEvento);
//			return evento;
//		}
//
//		case EventoSmarrimento.idTipologiaDB: {
//			EventoSmarrimento evento = new EventoSmarrimento(db, idEvento);
//			return evento;
//		}
//
//		case EventoCattura.idTipologiaDB:
//
//		{
//			EventoCattura evento = new EventoCattura(db, idEvento);
//			return evento;
//		}
//
//		case EventoCattura.idTipologiaDBRicattura:
//
//		{
//			EventoCattura evento = new EventoCattura(db, idEvento);
//			return evento;
//		}
//
//		case EventoRilascioPassaporto.idTipologiaDB: {
//			EventoRilascioPassaporto evento = new EventoRilascioPassaporto(db, idEvento);
//			return evento;
//		}
//		
//		case EventoRilascioPassaporto.idTipologiaRinnovoDB: {
//			EventoRilascioPassaporto evento = new EventoRilascioPassaporto(db, idEvento);
//			return evento;
//		}
//
//		
//		//TODO FARE DA QUA
//		
//		case EventoAdozioneDaCanile.idTipologiaDB: {
//			EventoAdozioneDaCanile evento = new EventoAdozioneDaCanile(db, idEvento);
//			return evento;
//
//		}
//
//		case EventoRestituzioneACanile.idTipologiaDB: {
//			EventoRestituzioneACanile evento = new EventoRestituzioneACanile(db, idEvento);
//			return evento;
//
//		}
//
//		case EventoCessione.idTipologiaDB: {
//			EventoCessione evento = new EventoCessione(db, idEvento);
//			return evento;
//
//		}
//		
//		case EventoAdozioneDaColonia.idTipologiaDB: {
//			EventoAdozioneDaColonia evento = new EventoAdozioneDaColonia(db, idEvento);
//			return evento;
//
//		}
//		
//		
//		case EventoPresaInCaricoDaCessione.idTipologiaDB: {
//			EventoPresaInCaricoDaCessione evento = new EventoPresaInCaricoDaCessione(db, idEvento);
//			return evento;
//
//		}
//		case EventoTrasferimento.idTipologiaDB: {
//			EventoTrasferimento evento = new EventoTrasferimento(db, idEvento);
//			return evento;
//		}
//		
//		case EventoTrasferimentoCanile.idTipologiaDB: {
//			EventoTrasferimentoCanile evento = new EventoTrasferimentoCanile(db, idEvento);
//			return evento;
//		}
//		case EventoTrasferimentoFuoriRegione.idTipologiaDB: {
//			EventoTrasferimentoFuoriRegione evento = new EventoTrasferimentoFuoriRegione(db, idEvento);
//			return evento;
//		}
//		case EventoRientroFuoriRegione.idTipologiaDB: {
//			EventoRientroFuoriRegione evento = new EventoRientroFuoriRegione(db, idEvento);
//			return evento;
//		}
//		
//		case EventoTrasferimentoFuoriStato.idTipologiaDB: {
//			EventoTrasferimentoFuoriStato evento = new EventoTrasferimentoFuoriStato(db, idEvento);
//			return evento;
//		}
//		
//		
//		case EventoCambioDetentore.idTipologiaDB: {
//			EventoCambioDetentore evento = new EventoCambioDetentore(db, idEvento);
//			return evento;
//		}
//		case EventoRestituzioneAProprietario.idTipologiaDB: {
//			EventoRestituzioneAProprietario evento = new EventoRestituzioneAProprietario(db, idEvento);
//			return evento;
//		}
//		case EventoRestituzioneAProprietario.idTipologiaRestituzioneACanileDB: {
//			EventoRestituzioneAProprietario evento = new EventoRestituzioneAProprietario(db, idEvento);
//			return evento;
//		}
//		case EventoMorsicatura.idTipologiaDB: {
//			EventoMorsicatura evento = new EventoMorsicatura(db, idEvento);
//			return evento;
//		}
//		case EventoEsitoControlli.idTipologiaDB: {
//			EventoEsitoControlli evento = new EventoEsitoControlli(db, idEvento);
//			return evento;
//		}
//		case EventoReimmissione.idTipologiaDB: {
//			EventoReimmissione evento = new EventoReimmissione(db, idEvento);
//			return evento;
//		}
//
//		case EventoRegistrazioneEsitoControlliCommerciali.idTipologiaDB: {
//			EventoRegistrazioneEsitoControlliCommerciali evento = new EventoRegistrazioneEsitoControlliCommerciali(db, idEvento);
//			return evento;
//		}
//		
//		case EventoAdozioneDistanza.idTipologiaDB: {
//			EventoAdozioneDistanza evento = new EventoAdozioneDistanza(db, idEvento);
//			return evento;
//		}
//		case EventoInserimentoVaccinazioni.idTipologiaDB: {
//			EventoInserimentoVaccinazioni evento = new EventoInserimentoVaccinazioni(db, idEvento);
//			return evento;
//		}
//		
//		case EventoTrasferimentoFuoriRegioneSoloProprietario.idTipologiaDB: {
//			EventoTrasferimentoFuoriRegioneSoloProprietario evento = new EventoTrasferimentoFuoriRegioneSoloProprietario(db, idEvento);
//			return evento;
//		}
//		case EventoRientroFuoriStato.idTipologiaDB: {
//			EventoRientroFuoriStato evento = new EventoRientroFuoriStato(db, idEvento);
//			return evento;
//		}
//		
//		case EventoAdozioneFuoriAsl.idTipologiaDB: {
//			EventoAdozioneFuoriAsl evento = new EventoAdozioneFuoriAsl(db, idEvento);
//			return evento;
//		}
//		
//		case EventoPresaInCaricoDaAdozioneFuoriAsl.idTipologiaDB: {
//			EventoPresaInCaricoDaAdozioneFuoriAsl evento = new EventoPresaInCaricoDaAdozioneFuoriAsl(db, idEvento);
//			return evento;
//		}
//		
//		
//		case EventoModificaResidenza.idTipologiaDB:
//
//		{
//			EventoModificaResidenza evento = new EventoModificaResidenza(db, idEvento);
//			return evento;
//			
//		}
//		
//		
//		case EventoPrelievoDNA.idTipologiaDB:
//
//		{
//			EventoPrelievoDNA evento = new EventoPrelievoDNA(db, idEvento);
//			return evento;
//			
//		}
//		
//		
//		case EventoCessioneImport.idTipologiaDB:
//
//		{
//			EventoCessioneImport evento = new EventoCessioneImport(db, idEvento);
//			return evento;
//			
//		}
//		
//		
//		case EventoPresaCessioneImport.idTipologiaDB:
//
//		{
//			EventoPresaCessioneImport evento = new EventoPresaCessioneImport(db, idEvento);
//			return evento;
//			
//		}
//		
//		
//		case EventoPrelievoLeishmania.idTipologiaDB:
//
//		{
//			EventoPrelievoLeishmania evento = new EventoPrelievoLeishmania(db, idEvento);
//			return evento;
//			
//		}
//		
//		case EventoRestituzioneAslOrigine.idTipologiaDB:
//
//		{
//			EventoRestituzioneAslOrigine evento = new EventoRestituzioneAslOrigine(db, idEvento);
//			return evento;
//			
//		}
//		
//		
//		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return evento;
	}
	
	
	public Evento(Connection db, int idEventoToSearch) throws SQLException {
		if (idEventoToSearch == -1) {
			throw new SQLException("Invalid Account");
		}

		PreparedStatement pst = db
				.prepareStatement("Select *, id_evento as idevento, id_asl as idaslinserimentoevento from evento e where e.id_evento = ?");
		pst.setInt(1, idEventoToSearch);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		
		if (rs.next()) {
			this.buildRecord(rs);


		}

		if (idEventoToSearch == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		rs.close();
		pst.close();
	}
	
	
	
	public String getDestinazione() {
		Connection conn = null;
		String destinazione = "---";

		try {
			Thread t = Thread.currentThread();
			conn = GestoreConnessioni.getConnection();

//			String dbName = ApplicationProperties.getProperty("dbnameBdu");
//			String username = ApplicationProperties
//					.getProperty("usernameDbbdu");
//			String pwd = ApplicationProperties.getProperty("passwordDbbdu");
//			String host = InetAddress.getByName("hostDbBdu").getHostAddress();
//
//			conn = DbUtil.getConnection(dbName, username, pwd, host);

			switch (this.getIdTipologiaEvento()) {
			
			case EventoTrasferimentoFuoriRegione.idTipologiaDB: {
				
				
				
				LookupList regioni = new LookupList(conn, "lookup_regione");
				destinazione = regioni.getSelectedValue( ((EventoTrasferimentoFuoriRegione) this).getIdRegioneA());
				break;

			}
			
			case EventoReimmissione.idTipologiaDB: {
				
			
				EventoReimmissione reimm = (EventoReimmissione)this;
					if (reimm.getIdDetentore() > 0)	{
						Operatore destinatario = new Operatore();
						destinatario.queryRecordOperatorebyIdLineaProduttiva(conn, reimm.getIdDetentore());
						destinazione = destinatario.getRagioneSociale();
				}
				break;

			}
			
			
			case EventoRientroFuoriRegione.idTipologiaDB: {
				
				
				
				LookupList siteList = new LookupList(conn, "lookup_asl_rif");
				destinazione = siteList.getSelectedValue( ((EventoRientroFuoriRegione) this).getIdAsl());
				break;

			}
			
			case EventoRientroFuoriStato.idTipologiaDB: {
				
				
				
				LookupList siteList = new LookupList(conn, "lookup_continenti");
				destinazione = siteList.getSelectedValue( ((EventoRientroFuoriStato) this).getIdContinenteDa());
				break;

			}
			
			case EventoCessione.idTipologiaDB: {
				EventoCessione cessione = (EventoCessione)this;
				if (cessione.getIdProprietario() > 0){  //Ho un proprietario destinatario già censito!!
					Operatore destinatarioCessione = new Operatore();
					destinatarioCessione.queryRecordOperatorebyIdLineaProduttiva(conn, cessione.getIdProprietario());
					destinazione = destinatarioCessione.getRagioneSociale();
				} else  if (cessione.getNome() != null && !("").equals(cessione.getNome())){
					if (cessione.getNome() != null && !("").equals(cessione.getNome()))
						destinazione = "Nome: ".concat(cessione.getNome());
					if (cessione.getCognome() != null && !("").equals(cessione.getCognome()))
						destinazione = destinazione.concat(", Cognome: ").concat(cessione.getCognome());
					if (cessione.getCodiceFiscale() != null && !("").equals(cessione.getCodiceFiscale())) 
						destinazione = destinazione.concat(", C.F.: ").concat(cessione.getCodiceFiscale());
					if (cessione.getSesso() != null && !("").equals(cessione.getSesso())) 
						destinazione = destinazione.concat(", Sesso: ").concat(cessione.getSesso());
					if (cessione.getDocIdentita() != null && !("").equals(cessione.getDocIdentita())) 
						destinazione = destinazione.concat(", Documento d'identità: ").concat(cessione.getDocIdentita());
					if (cessione.getNumeroTelefono() != null && !("").equals(cessione.getNumeroTelefono())) 
						destinazione = destinazione.concat(", Telefono: ").concat(cessione.getNumeroTelefono());
					if (cessione.getLuogoNascita() != null && !("").equals(cessione.getLuogoNascita())) 
						destinazione = destinazione.concat(", Luogo nascita: ").concat(cessione.getLuogoNascita());
					if (cessione.getDataNascita() != null && !("").equals(cessione.getDataNascita())) 
					{
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						destinazione = destinazione.concat(", Data nascita: ").concat(sdf.format(cessione.getDataNascita()));
					}
					if (cessione.getIdComune() > 0){
						ComuniAnagrafica c = new ComuniAnagrafica();
						ArrayList<ComuniAnagrafica> listaComuni = c.buildList(conn,
								-1, 1);
						LookupList comuniList = new LookupList(listaComuni, -1);
						destinazione = destinazione.concat("<br/> Comune residenza:  ").concat(comuniList.getSelectedValue(cessione.getIdComune()));
					}if (cessione.getIndirizzo() != null ){
						destinazione = destinazione.concat(", Via residenza:  ").concat(cessione.getIndirizzo());
					}
					if (cessione.getCap() != null && !("").equals(cessione.getCap())) 
						destinazione = destinazione.concat(", Cap: ").concat(cessione.getCap());
					if (cessione.getIdProvincia() > 0){
						LookupList provinceList = new LookupList(conn, "lookup_province");
						destinazione = destinazione.concat(", Provincia residenza:  ").concat(provinceList.getSelectedValue(cessione.getIdProvincia()));
					}
				}else destinazione = (cessione.getDestinatarioCessioneVecchiaCanina() != null) ? cessione.getDestinatarioCessioneVecchiaCanina() : "---";
				break;
			}
			
			
			case EventoCessione.idTipologiaDB_obsoleto: {
				EventoCessione cessione = (EventoCessione)this;
				if (cessione.getIdProprietario() > 0){  //Ho un proprietario destinatario già censito!!
					Operatore destinatarioCessione = new Operatore();
					destinatarioCessione.queryRecordOperatorebyIdLineaProduttiva(conn, cessione.getIdProprietario());
					destinazione = destinatarioCessione.getRagioneSociale();
				} else  if (cessione.getNome() != null && !("").equals(cessione.getNome())){
					if (cessione.getNome() != null && !("").equals(cessione.getNome()))
						destinazione = "Nome: ".concat(cessione.getNome());
					if (cessione.getCognome() != null && !("").equals(cessione.getCognome()))
						destinazione = destinazione.concat(", Cognome: ").concat(cessione.getCognome());
					if (cessione.getCodiceFiscale() != null && !("").equals(cessione.getCodiceFiscale())) 
						destinazione = destinazione.concat(", C.F.: ").concat(cessione.getCodiceFiscale());
					if (cessione.getSesso() != null && !("").equals(cessione.getSesso())) 
						destinazione = destinazione.concat(", Sesso: ").concat(cessione.getSesso());
					if (cessione.getDocIdentita() != null && !("").equals(cessione.getDocIdentita())) 
						destinazione = destinazione.concat(", Documento d'identità: ").concat(cessione.getDocIdentita());
					if (cessione.getNumeroTelefono() != null && !("").equals(cessione.getNumeroTelefono())) 
						destinazione = destinazione.concat(", Telefono: ").concat(cessione.getNumeroTelefono());
					if (cessione.getLuogoNascita() != null && !("").equals(cessione.getLuogoNascita())) 
						destinazione = destinazione.concat(", Luogo nascita: ").concat(cessione.getLuogoNascita());
					if (cessione.getDataNascita() != null && !("").equals(cessione.getDataNascita())) 
					{
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						destinazione = destinazione.concat(", Data nascita: ").concat(sdf.format(cessione.getDataNascita()));
					}
					if (cessione.getIdComune() > 0){
						ComuniAnagrafica c = new ComuniAnagrafica();
						ArrayList<ComuniAnagrafica> listaComuni = c.buildList(conn,
								-1, 1);
						LookupList comuniList = new LookupList(listaComuni, -1);
						destinazione = destinazione.concat("<br/> Comune residenza:  ").concat(comuniList.getSelectedValue(cessione.getIdComune()));
					}if (cessione.getIndirizzo() != null ){
						destinazione = destinazione.concat(", Via residenza:  ").concat(cessione.getIndirizzo());
					}
					if (cessione.getCap() != null && !("").equals(cessione.getCap())) 
						destinazione = destinazione.concat(", Cap: ").concat(cessione.getCap());
					if (cessione.getIdProvincia() > 0){
						LookupList provinceList = new LookupList(conn, "lookup_province");
						destinazione = destinazione.concat(", Provincia residenza:  ").concat(provinceList.getSelectedValue(cessione.getIdProvincia()));
					}
				}else destinazione = (cessione.getDestinatarioCessioneVecchiaCanina() != null) ? cessione.getDestinatarioCessioneVecchiaCanina() : "---";
				break;
			}
			
			
			
			case EventoAdozioneFuoriAsl.idTipologiaDB: {
				EventoAdozioneFuoriAsl adozionefa = (EventoAdozioneFuoriAsl)this;
				if (adozionefa.getIdProprietario() > 0){  //Ho un proprietario destinatario già censito!!
					Operatore destinatarioCessione = new Operatore();
					destinatarioCessione.queryRecordOperatorebyIdLineaProduttiva(conn, adozionefa.getIdProprietario());
					destinazione = destinatarioCessione.getRagioneSociale();
				} else  if (adozionefa.getNome() != null && !("").equals(adozionefa.getNome())){
					if (adozionefa.getNome() != null && !("").equals(adozionefa.getNome()))
						destinazione = "Nome: ".concat(adozionefa.getNome());
					if (adozionefa.getCognome() != null && !("").equals(adozionefa.getCognome()))
						destinazione = destinazione.concat(" Cognome: ").concat(adozionefa.getCognome());
					if (adozionefa.getCodiceFiscale() != null && !("").equals(adozionefa.getCodiceFiscale())) 
						destinazione = destinazione.concat(" C.F.: ").concat(adozionefa.getCodiceFiscale());
					if (adozionefa.getSesso() != null && !("").equals(adozionefa.getSesso())) 
						destinazione = destinazione.concat(" Sesso: ").concat(adozionefa.getSesso());
					if (adozionefa.getLuogoNascita() != null && !("").equals(adozionefa.getLuogoNascita())) 
						destinazione = destinazione.concat(" Comune nascita: ").concat(adozionefa.getLuogoNascita());
					if (adozionefa.getIdComune() > 0) {
						ComuniAnagrafica c = new ComuniAnagrafica();
						ArrayList<ComuniAnagrafica> listaComuni = c.buildList(conn, -1, -1);
						LookupList comuniList = new LookupList(listaComuni, -1);
						destinazione = destinazione.concat(" Comune residenza : ").concat(comuniList.getSelectedValue(adozionefa.getIdComune()));
					}
					if (adozionefa.getCap() != null && !("").equals(adozionefa.getCap())) 
						destinazione = destinazione.concat(" Cap: ").concat(adozionefa.getCap());
					if (adozionefa.getNumeroTelefono() != null && !("").equals(adozionefa.getNumeroTelefono())) 
						destinazione = destinazione.concat(" Numero telefono: ").concat(adozionefa.getNumeroTelefono());
				}
				break;
			}
			
			case EventoAdozioneFuoriAsl.idTipologiaDB_obsoleto: {
				EventoAdozioneFuoriAsl adozionefa = (EventoAdozioneFuoriAsl)this;
				if (adozionefa.getIdProprietario() > 0){  //Ho un proprietario destinatario già censito!!
					Operatore destinatarioCessione = new Operatore();
					destinatarioCessione.queryRecordOperatorebyIdLineaProduttiva(conn, adozionefa.getIdProprietario());
					destinazione = destinatarioCessione.getRagioneSociale();
				} else  if (adozionefa.getNome() != null && !("").equals(adozionefa.getNome())){
					if (adozionefa.getNome() != null && !("").equals(adozionefa.getNome()))
						destinazione = "Nome: ".concat(adozionefa.getNome());
					if (adozionefa.getCognome() != null && !("").equals(adozionefa.getCognome()))
						destinazione = destinazione.concat(" Cognome: ").concat(adozionefa.getCognome());
					if (adozionefa.getCodiceFiscale() != null && !("").equals(adozionefa.getCodiceFiscale())) 
						destinazione = destinazione.concat(" C.F.: ").concat(adozionefa.getCodiceFiscale());
					if (adozionefa.getSesso() != null && !("").equals(adozionefa.getSesso())) 
						destinazione = destinazione.concat(" Sesso: ").concat(adozionefa.getSesso());
					if (adozionefa.getLuogoNascita() != null && !("").equals(adozionefa.getLuogoNascita())) 
						destinazione = destinazione.concat(" Comune nascita: ").concat(adozionefa.getLuogoNascita());
					if (adozionefa.getIdComune() > 0) {
						ComuniAnagrafica c = new ComuniAnagrafica();
						ArrayList<ComuniAnagrafica> listaComuni = c.buildList(conn, -1, -1);
						LookupList comuniList = new LookupList(listaComuni, -1);
						destinazione = destinazione.concat(" Comune residenza : ").concat(comuniList.getSelectedValue(adozionefa.getIdComune()));
					}
					if (adozionefa.getCap() != null && !("").equals(adozionefa.getCap())) 
						destinazione = destinazione.concat(" Cap: ").concat(adozionefa.getCap());
					if (adozionefa.getNumeroTelefono() != null && !("").equals(adozionefa.getNumeroTelefono())) 
						destinazione = destinazione.concat(" Numero telefono: ").concat(adozionefa.getNumeroTelefono());
				}
				break;
			}
			
			
			
			
			
			}
			


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			GestoreConnessioni.freeConnection(conn);
		//	DbUtil.chiudiConnessioneJDBC(null, null, conn);
			conn = null;
		}

		return destinazione;
	}

	public void setIdStatoOriginale(int idStatoOriginale) {
		this.idStatoOriginale = idStatoOriginale;
	}
	
	public void setIdStatoOriginale(String idStatoOriginale) {
		this.idStatoOriginale = new Integer(idStatoOriginale);
	}

	public int getIdStatoOriginale() {
		return idStatoOriginale;
	}

	public boolean deleteListaRegistrazioni(Connection db, int idAnimale, int idUtente) throws SQLException {
		
		StringBuffer sql = new StringBuffer();
		try {
			

		//	idEvento = DatabaseUtils.getNextSeq(db, "evento_id_evento_seq");
			// sql.append("INSERT INTO animale (");

			sql.append("UPDATE evento set data_cancellazione = now(), id_utente_cancellazione = ?, note_cancellazione=? where id_animale = ? and data_cancellazione is null");
			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, idUtente);
			pst.setString(++i, "Cancellata d'ufficio a seguito dell'eliminazione dell'animale "+idAnimale);
			pst.setInt(++i, idAnimale);
			pst.executeUpdate();
			pst.close();


		} catch (SQLException e) {
		
			throw new SQLException(e.getMessage());
		} finally {
		
		}
		return true;

	}
	
	public int update(Connection db) throws SQLException {
		
			
		StringBuffer sql = new StringBuffer();
		int result = -1;
		try {

		//	idEvento = DatabaseUtils.getNextSeq(db, "evento_id_evento_seq");
			// sql.append("INSERT INTO animale (");

			sql.append("UPDATE evento SET ");

			sql.append(" modified=now() ");
			if(ApplicationProperties.getProperty("abilitato_sinaaf").equals("true"))
			{
				sql.append(", modified_sinaaf=now() ");
			}
			
			if (modifiedby > -1) {
				sql.append(", id_utente_modifica = ? ");
			}

			if (idAslRiferimento > -1) {
				sql.append(", id_asl = ? ");
			}
			
			if (noteInternalUseOnly != null && !("").equals(noteInternalUseOnly)){
				sql.append(", note_internal_use_only = note_internal_use_only || ? ");
			}
			
			if (idStatoOriginale > 0){
				sql.append(", id_stato_originale = ? ");
			}
			
			if (note != null && !("").equals(note)){
				sql.append(", note = ? ");
			}
			
			if (microchip != null && !("").equals(microchip)){
				sql.append(", microchip = ? ");
			}
			if (idProprietarioCorrente > 0){
				sql.append(", id_proprietario_corrente = ? ");
			}
			if (idDetentoreCorrente > 0){
				sql.append(", id_detentore_corrente = ? ");
			}

			sql.append(" where id_evento = ? ");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			if (modifiedby > -1) {
				pst.setInt(++i, modifiedby);
			}

			if (idAslRiferimento > -1) {
				pst.setInt(++i, idAslRiferimento);
				}
			
			if (noteInternalUseOnly != null && !("").equals(noteInternalUseOnly)){
				pst.setString(++i, noteInternalUseOnly);
			}
			
			if (idStatoOriginale > 0){
				pst.setInt(++i, idStatoOriginale);
			}
			
			if (note != null && !("").equals(note)){
				pst.setString(++i, note);
			}
			
			if (microchip != null && !("").equals(microchip)){
				pst.setString(++i, microchip);
			}
			if (idProprietarioCorrente > 0){
				pst.setInt(++i, idProprietarioCorrente);
			}
			if (idDetentoreCorrente > 0){
				pst.setInt(++i, idDetentoreCorrente);
			}
			
			pst.setInt(++i, idEvento);

			result = pst.executeUpdate();
			pst.close();

		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return result;

	}
	
	
	/**
	 * Restituisce informazioni di inserimento del record - Utente, Data, Sistema
	 * @throws UnknownHostException 
	 */
	public String getInformazioniInserimentoRecord() throws UnknownHostException{
		String toReturn = "";
//		String dbName = ApplicationProperties.getProperty("dbnameBdu");
//		String username = ApplicationProperties
//				.getProperty("usernameDbbdu");
//		String pwd = ApplicationProperties.getProperty("passwordDbbdu");
//		String host = InetAddress.getByName("hostDbBdu").getHostAddress();
		Connection db = null;
		try{

//		db = DbUtil.getConnection(dbName, username, pwd, host);
			
		Thread t = Thread.currentThread();
		db = GestoreConnessioni.getConnection();
		User user = new User();
		user.setBuildContact(true);
		if (this.getEnteredby()>-1)
			user.buildRecord(db, this.getEnteredby());
		else
			return "";
		
		LookupList listaSistemi = new LookupList(db, "lookup_sistemi");
		toReturn = user.getContact().getNameFull() + " " + new SimpleDateFormat("dd/MM/yyyy").format(this.getEntered())  + " (Origine inserimento: " + listaSistemi.getSelectedValue(this.getOrigineInserimento()) + ")" ;
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			//DbUtil.chiudiConnessioneJDBC(null, db);
			GestoreConnessioni.freeConnection(db);
		}
		
		
		return toReturn;
		
	}
	
	
	
	public int cancellaEvento(Connection db) throws SQLException {
	//	if (System.getProperty("DEBUG") != null) 
			
		StringBuffer sql = new StringBuffer();
		int result = -1;
		try {
		

		//	idEvento = DatabaseUtils.getNextSeq(db, "evento_id_evento_seq");
			// sql.append("INSERT INTO animale (");

			sql.append("UPDATE evento SET ");

			sql.append(" data_cancellazione=now() ");

			if (idUtenteCancellazione > -1) {
				sql.append(", id_utente_cancellazione = ? ");
			}
			if (noteCancellazione != null && !("").equals(noteCancellazione)){
				sql.append(", note_cancellazione = ? ");
			}
			if (noteInternalUseOnly != null && !("").equals(noteInternalUseOnly)){
				sql.append(", note_internal_use_only = ? ");
			}
			
			sql.append(" where id_evento = ? ");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			if (idUtenteCancellazione > -1) {
				pst.setInt(++i, idUtenteCancellazione);
			}

			if (noteCancellazione != null && !("").equals(noteCancellazione)){
				pst.setString(++i, noteCancellazione);
			}		
			if (noteInternalUseOnly != null && !("").equals(noteInternalUseOnly)){
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				pst.setString(++i, noteInternalUseOnly + " ["+ dateFormat.format(new Date()) + "] ");
			}
			
			pst.setInt(++i, idEvento);

			result = pst.executeUpdate();
			pst.close();


		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
		
		}
		return result;

	}
	
	public void updateDominioFuoriAsl(Connection db) throws SQLException{

		StringBuffer sql = new StringBuffer();
		int result = -1;
		
		try {
			
		//	idEvento = DatabaseUtils.getNextSeq(db, "evento_id_evento_seq");
			// sql.append("INSERT INTO animale (");

			sql.append("UPDATE evento SET flag_operazione_eseguita_fuori_dominio_asl = true where id_evento = ? ");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			
			pst.setInt(++i, idEvento);

			result = pst.executeUpdate();
			pst.close();

			

		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}
	
		
	}
	
	public Evento salvaRegistrazione(int userId, int userRole, int userAsl, Animale thisAnimale, Connection db) throws Exception{
		return salvaRegistrazione( userId,  userRole,  userAsl,  thisAnimale,  db, null);
	}
	
	public Evento salvaRegistrazione(int userId, int userRole, int userAsl, Animale thisAnimale, Connection db,ActionContext context) throws Exception{
		//System.out.println("p"); 
		this.setEnteredby(userId);
		this.setModifiedby(userId);		
		this.setSpecieAnimaleId(thisAnimale.getIdSpecie());
		
		
		//CONTROLLO SE SI TRATTA DI EVENTO FUORI DOMINIO ASL
		if (thisAnimale.getIdAslRiferimento() != Integer
				.parseInt(ApplicationProperties
						.getProperty("ID_ASL_FUORI_REGIONE"))
				&& thisAnimale.getIdAslRiferimento() != userAsl
				&& userRole != Integer
						.parseInt(ApplicationProperties
								.getProperty("ID_RUOLO_HD1"))
				&& userRole != Integer
						.parseInt(ApplicationProperties
								.getProperty("ID_RUOLO_HD2")))
			flagFuoriDominioAsl = true;
		
		return this;
		
	}
	
	
	public Evento aggiornaFlagFuoriDominioAsl(Connection db, Animale thisAnimale, int userAsl, Animale oldAnimale) throws Exception{
	try{
		ArrayList<String> listaRegistrazioniModificanoDominioAslAnimaleDa = ApplicationProperties
				.getListProperty("registrazioni_da_fuori_dominio");
		
		if (flagFuoriDominioAsl)
			//	&& this.getIdTipologiaEvento() != EventoRestituzioneAslOrigine.idTipologiaDB
			//	&& getUserRole(context) != Integer
				//		.parseInt(ApplicationProperties
					//			.getProperty("ID_RUOLO_HD1"))
		//		&& getUserRole(context) != Integer
		//				.parseInt(ApplicationProperties
						//		.getProperty("ID_RUOLO_HD2")))
			{
			
			this.updateDominioFuoriAsl(db);
			ArrayList<String> listaRegistrazioniModificanoDominioAslAnimaleA = ApplicationProperties
					.getListProperty("registrazioni_a_fuori_dominio");
			
		//	if (thisCane != null) {

				if (listaRegistrazioniModificanoDominioAslAnimaleA
						.contains(String.valueOf(this
								.getIdTipologiaEvento()))) {
					thisAnimale.setFlagUltimaOperazioneFuoriDominioAsl(true);
					thisAnimale.setIdAslUltimaOperazioneFuoriDominioAsl(userAsl);
					thisAnimale.setIdDetentoreUltimaOperazioneFuoriDominioAsl(oldAnimale
							.getIdDetentore());
					thisAnimale.setIdRegistrazioneUltimaOperazioneFuoriDominioAsl(this
							.getIdEvento());
					thisAnimale.setIdStatoUltimaOperazioneFuoriDominioAsl(oldAnimale
							.getStato());
					thisAnimale.setIdTipologiaUltimaOperazioneFuoriDominioAsl(this.getIdTipologiaEvento());

				} else if (listaRegistrazioniModificanoDominioAslAnimaleDa
						.contains(String.valueOf(this
								.getIdTipologiaEvento()))) {

					thisAnimale.setFlagUltimaOperazioneFuoriDominioAsl(false);
					thisAnimale.setIdAslUltimaOperazioneFuoriDominioAsl(-1);
					thisAnimale.setIdDetentoreUltimaOperazioneFuoriDominioAsl(-1);
					thisAnimale.setIdRegistrazioneUltimaOperazioneFuoriDominioAsl(-1);
					thisAnimale.setIdStatoUltimaOperazioneFuoriDominioAsl(-1);
					thisAnimale.setIdTipologiaUltimaOperazioneFuoriDominioAsl(-1);
				}
		//	} else if (thisGatto != null) {
//				if (listaRegistrazioniModificanoDominioAslAnimaleA
//						.contains(String.valueOf(dettagli_base
//								.getIdTipologiaEvento()))) {
//					thisGatto
//							.setFlagUltimaOperazioneFuoriDominioAsl(true);
//					thisGatto
//							.setIdAslUltimaOperazioneFuoriDominioAsl(getUserAsl(context));
//					thisGatto
//							.setIdDetentoreUltimaOperazioneFuoriDominioAsl(oldAnimale
//									.getIdDetentore());
//					thisGatto
//							.setIdRegistrazioneUltimaOperazioneFuoriDominioAsl(dettagli_base
//									.getIdEvento());
//					thisGatto
//							.setIdStatoUltimaOperazioneFuoriDominioAsl(oldAnimale
//									.getStato());
//					thisGatto.setIdTipologiaUltimaOperazioneFuoriDominioAsl(dettagli_base.getIdTipologiaEvento());
//				} else if (listaRegistrazioniModificanoDominioAslAnimaleDa
//						.contains(String.valueOf(dettagli_base
//								.getIdTipologiaEvento()))) {
//
//					thisGatto
//							.setFlagUltimaOperazioneFuoriDominioAsl(false);
//					thisGatto
//							.setIdAslUltimaOperazioneFuoriDominioAsl(-1);
//					thisGatto
//							.setIdDetentoreUltimaOperazioneFuoriDominioAsl(-1);
//					thisGatto
//							.setIdRegistrazioneUltimaOperazioneFuoriDominioAsl(-1);
//					thisGatto
//							.setIdStatoUltimaOperazioneFuoriDominioAsl(-1);
//					thisGatto.setIdTipologiaUltimaOperazioneFuoriDominioAsl(-1);
//				}
//
//			} else if (thisFuretto != null) {
//				if (listaRegistrazioniModificanoDominioAslAnimaleA
//						.contains(String.valueOf(dettagli_base
//								.getIdTipologiaEvento()))) {
//					thisFuretto
//							.setFlagUltimaOperazioneFuoriDominioAsl(true);
//					thisFuretto
//							.setIdAslUltimaOperazioneFuoriDominioAsl(getUserAsl(context));
//					thisFuretto
//							.setIdDetentoreUltimaOperazioneFuoriDominioAsl(oldAnimale
//									.getIdDetentore());
//					thisFuretto
//							.setIdRegistrazioneUltimaOperazioneFuoriDominioAsl(dettagli_base
//									.getIdEvento());
//					thisFuretto
//							.setIdStatoUltimaOperazioneFuoriDominioAsl(oldAnimale
//									.getStato());
//					thisFuretto.setIdTipologiaUltimaOperazioneFuoriDominioAsl(dettagli_base.getIdTipologiaEvento());
//				} else if (listaRegistrazioniModificanoDominioAslAnimaleDa
//						.contains(String.valueOf(dettagli_base
//								.getIdTipologiaEvento()))) {
//
//					thisFuretto
//							.setFlagUltimaOperazioneFuoriDominioAsl(false);
//					thisFuretto
//							.setIdAslUltimaOperazioneFuoriDominioAsl(-1);
//					thisFuretto
//							.setIdDetentoreUltimaOperazioneFuoriDominioAsl(-1);
//					thisFuretto
//							.setIdRegistrazioneUltimaOperazioneFuoriDominioAsl(-1);
//					thisFuretto
//							.setIdStatoUltimaOperazioneFuoriDominioAsl(-1);
//					thisFuretto.setIdTipologiaUltimaOperazioneFuoriDominioAsl(-1);
//				}
//
//			} else {
//				if (listaRegistrazioniModificanoDominioAslAnimaleA
//						.contains(String.valueOf(dettagli_base
//								.getIdTipologiaEvento()))) {
//					thisAnimale
//							.setFlagUltimaOperazioneFuoriDominioAsl(true);
//					thisAnimale
//							.setIdAslUltimaOperazioneFuoriDominioAsl(getUserAsl(context));
//					thisAnimale
//							.setIdDetentoreUltimaOperazioneFuoriDominioAsl(oldAnimale
//									.getIdDetentore());
//					thisAnimale
//							.setIdRegistrazioneUltimaOperazioneFuoriDominioAsl(dettagli_base
//									.getIdEvento());
//					thisAnimale
//							.setIdStatoUltimaOperazioneFuoriDominioAsl(oldAnimale
//									.getStato());
//					thisAnimale.setIdTipologiaUltimaOperazioneFuoriDominioAsl(dettagli_base.getIdTipologiaEvento());
//				} else if (listaRegistrazioniModificanoDominioAslAnimaleDa
//						.contains(String.valueOf(dettagli_base
//								.getIdTipologiaEvento()))) {
//
//					thisAnimale
//							.setFlagUltimaOperazioneFuoriDominioAsl(false);
//					thisAnimale
//							.setIdAslUltimaOperazioneFuoriDominioAsl(-1);
//					thisAnimale
//							.setIdDetentoreUltimaOperazioneFuoriDominioAsl(-1);
//					thisAnimale
//							.setIdRegistrazioneUltimaOperazioneFuoriDominioAsl(-1);
//					thisAnimale
//							.setIdStatoUltimaOperazioneFuoriDominioAsl(-1);
//					thisAnimale.setIdTipologiaUltimaOperazioneFuoriDominioAsl(dettagli_base.getIdTipologiaEvento());
//				}
//			}

		}
		else if (listaRegistrazioniModificanoDominioAslAnimaleDa
				.contains(String.valueOf(this
						.getIdTipologiaEvento()))) 
		{
			thisAnimale.setFlagUltimaOperazioneFuoriDominioAsl(false);
			thisAnimale.setIdAslUltimaOperazioneFuoriDominioAsl(-1);
			thisAnimale.setIdDetentoreUltimaOperazioneFuoriDominioAsl(-1);
			thisAnimale.setIdRegistrazioneUltimaOperazioneFuoriDominioAsl(-1);
			thisAnimale.setIdStatoUltimaOperazioneFuoriDominioAsl(-1);
			thisAnimale.setIdTipologiaUltimaOperazioneFuoriDominioAsl(-1);
		}
	}
		catch(Exception e){
		throw e;
	}
		
		
		return this;
	}
	
	
	public void aggiornaStatoAnimale(Connection db, Animale thisAnimale) throws Exception {
		
		RegistrazioniWKF wkf = new RegistrazioniWKF();
		wkf.setIdStato(thisAnimale.getStato());
		wkf.setIdRegistrazione(this.getIdTipologiaEvento());
		wkf.getProssimoStatoDaStatoPrecedenteERegistrazione(db);
		thisAnimale.setStato(wkf.getIdProssimoStato());


		if (thisAnimale != null) {
			// Aggiorno lo stato del cane
		//	thisAnimale.setStato(wkf.getIdProssimoStato());
			thisAnimale.updateStato(db);
		}

//		} else if (thisGatto != null) {
//			thisGatto.setStato(wkf.getIdProssimoStato());
//			thisGatto.updateStato(db);
//
//		} else if (thisFuretto != null) {
//			thisFuretto.setStato(wkf.getIdProssimoStato());
//			thisFuretto.updateStato(db);
//
//		} else {
//			thisAnimale.updateStato(db);
//		}
		
	}
	
	
public void aggiornaStatoAnimaleSenzaControlloCanilePieno(Connection db, Animale thisAnimale) throws Exception {
		
		RegistrazioniWKF wkf = new RegistrazioniWKF();
		wkf.setIdStato(thisAnimale.getStato());
		wkf.setIdRegistrazione(this.getIdTipologiaEvento());
		wkf.getProssimoStatoDaStatoPrecedenteERegistrazione(db);
		thisAnimale.setStato(wkf.getIdProssimoStato());


		if (thisAnimale != null) {
			// Aggiorno lo stato del cane
		//	thisAnimale.setStato(wkf.getIdProssimoStato());
			thisAnimale.updateStatoSenzaControlloCanilePieno(db);
		}

//		} else if (thisGatto != null) {
//			thisGatto.setStato(wkf.getIdProssimoStato());
//			thisGatto.updateStato(db);
//
//		} else if (thisFuretto != null) {
//			thisFuretto.setStato(wkf.getIdProssimoStato());
//			thisFuretto.updateStato(db);
//
//		} else {
//			thisAnimale.updateStato(db);
//		}
		
	}
	
	
public void aggiornaStatoAnimaleAdozioneFuoriRegione(Connection db, Animale thisAnimale) throws Exception 
{
	//Se randagio
	if(thisAnimale.getStato()==3)
		thisAnimale.setStato(34);
	//Se randagio/sterilizzato
	else if(thisAnimale.getStato()==9)
		thisAnimale.setStato(35);

		if (thisAnimale != null) {
			thisAnimale.updateStato(db);
		}

	
	}
	
	
	public Evento build(ResultSet rs) throws Exception{
	//	System.out.println("p"); 
		buildRecord(rs);		
		return this;
		
	}
	
	public boolean contenutaInRegistroCaniAggressivi(Connection db) throws SQLException 
	{

		
		boolean contenuta = false;
		PreparedStatement pst = db.prepareStatement(" Select count(*) >0 as contenuta from registro_unico_cani_rischio_elevato_aggressivita where id_evento = ? ");
		pst.setInt(1, idEvento);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);

		if(rs.next())
			contenuta = rs.getBoolean("contenuta");


		rs.close();
		pst.close();
		
		return contenuta;
}
	
	
}
