/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.anagrafe_animali.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.anagrafe_animali.gestione_modifiche.CampoModificato;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.CanilePienoException;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.praticacontributi.base.Pratica;
import org.aspcfs.modules.registrazioniAnimali.base.Evento;
import org.aspcfs.modules.registrazioniAnimali.base.EventoAllontanamento;
import org.aspcfs.modules.registrazioniAnimali.base.EventoInserimentoVaccinazioni;
import org.aspcfs.modules.registrazioniAnimali.base.EventoList;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRegistrazioneBDU;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRegistrazioneEsitoControlliCommerciali;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRientroFuoriRegione;
import org.aspcfs.modules.registrazioniAnimali.base.EventoTrasferimentoFuoriRegione;
import org.aspcfs.modules.registrazioniAnimali.base.RegistrazioniWKF;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.DbUtil;
import org.aspcfs.utils.EsitoControllo;
import org.aspcfs.utils.GestoreConnessioni;
//import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.beans.GenericBean;

public class Animale extends GenericBean {

	private static Logger log = Logger
			.getLogger(org.aspcfs.modules.anagrafe_animali.base.Animale.class);
	static {
		if (System.getProperty("DEBUG") != null) {
			log.setLevel(Level.DEBUG);
		}
	}
	private int idAslRiferimento = -1; // asl_rif
	private int idProprietario;
	private int idDetentore;

	private int idAnimale;
	private java.sql.Timestamp dataNascita;
	private int idSpecie = 1; // id_specie
	private int idTipoMantello = -1;
	private int idTaglia = -1;
	private String sesso;
	private String nome;
	private String microchip;
	private int idVeterinarioMicrochip = -1;
	private java.sql.Timestamp dataMicrochip;

	private String segniParticolari; // DESCRIPTION (SEGNI PARTICOLARI)
	private String nota2; // NOTES
	private int idRazza;
	private boolean flagDataNascitaPresunta; // data_presunta
	private Timestamp dataRegistrazione;
	private Timestamp dataSterilizzazione;
	private boolean flagSterilizzazione = false;
	private boolean flagMancataOrigine = false;
	private boolean flagContributoRegionale;
	private java.sql.Timestamp dataInserimento;
	private int idUtenteInserimento;
	private java.sql.Timestamp dataModifica;
	private int idUtenteModifica;
	private java.sql.Timestamp dataCancellazione;
	private int idUtenteCancellazione;
	private Timestamp dataRilascioPassaporto;
	private Timestamp dataScadenzaPassaporto;
	private String numeroPassaporto;
	private Operatore proprietarioProvenienza = new Operatore();
	private Operatore proprietario = new Operatore();
	private Operatore detentore = new Operatore();
	private boolean flagDecesso = false;
	private boolean flagSmarrimento = false;
	private boolean flagFurto = false;
	private String tatuaggio;
	private java.sql.Timestamp dataTatuaggio;
	private boolean flagCattura = false;

	private int idPraticaContributi = -1;

	private boolean contributoPagato = false;

	private int idRegione = -1; // Regione per trasferimento fuori regione
	private int idProprietarioUltimoTrasferimentoFRegione = -1; // Conservo l'id
	// del
	// proprietario
	// precedente a
	// ultimo
	// trasferimento
	// fuori regione

	// private boolean flagProvenienzaFuoriRegione = false;
	// private int idRegioneProvenienza = -1;

	private int idContinente = -1;
	private int idProprietarioUltimoTrasferimentoFStato = -1;

	private int stato = 1; // Stato di Start del workflow
	private String note;

	private int idDetentoreUltimoTrasferimentoFStato = -1; // CONSERVO DETENTORE
	// PRIMA DI ULTIMO
	// TRASFERIMENTO F
	// STATO

	private int idDetentoreUltimoTrasferimentoFRegione = -1; // Conservo l'id

	// del detentore
	// precedente a
	// ultimo
	// trasferimento
	// fuori regione

	private int idComuneCattura = -1; // NON VIENE POPOLATO MAI, SOLO
										// NELL'ESTRAZIONE DEGLI ANIMALE DALLA
										// VISTA estrazione_animali_contributi
										// PER LE PRATICHE CONTRIB

	private Timestamp dataVaccino; // antirabbia
	private String numeroLottoVaccino; // antirabbia
	private Timestamp dataScadenzaVaccino; // antirabbia
	private String nomeVaccino; // antirabbia
	private String produttoreVaccino; // antirabbia

	private boolean flagDetenutoInCanileDopoRitrovamento = false;
	private int origineInserimento = 1; // Default BDU

	private boolean flagUltimaOperazioneFuoriDominioAsl = false;
	private int idAslUltimaOperazioneFuoriDominioAsl = -1;
	private int idRegistrazioneUltimaOperazioneFuoriDominioAsl = -1;
	private int idDetentoreUltimaOperazioneFuoriDominioAsl = -1;
	private int idStatoUltimaOperazioneFuoriDominioAsl = -1;
	private int idTipologiaUltimaOperazioneFuoriDominioAsl = -1;
	
	
	private boolean flagBloccato = false;
	private int idUltimaRegistrazioneBlocco = -1;
	
	
	private boolean flagValidato = false;

	public Timestamp getDataVaccino() {
		return dataVaccino;
	}

	public void setDataVaccino(Timestamp dataVaccino) {
		this.dataVaccino = dataVaccino;
	}

	public void setIdComuneCattura(int idComuneCattura) {
		this.idComuneCattura = idComuneCattura;
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

	public boolean isFlagDetenutoInCanileDopoRitrovamento() {
		return flagDetenutoInCanileDopoRitrovamento;
	}

	public boolean getFlagDetenutoInCanileDopoRitrovamento() {
		return flagDetenutoInCanileDopoRitrovamento;
	}

	
	public void setFlagDetenutoInCanileDopoRitrovamento(
			boolean flagDetenutoInCanileDopoRitrovamento) {
		this.flagDetenutoInCanileDopoRitrovamento = flagDetenutoInCanileDopoRitrovamento;
	}

	public void setDataVaccino(String dataVaccino) {
		this.dataVaccino =   DateUtils.parseDateStringNew(dataVaccino, "dd/MM/yyyy");
	}

	public void setDataScadenzaVaccino(String dataScadenzaVaccino) {
		this.dataScadenzaVaccino = DateUtils.parseDateStringNew(dataScadenzaVaccino, "dd/MM/yyyy"); 
	}

	public String getNumeroLottoVaccino() {
		return numeroLottoVaccino;
	}

	public void setNumeroLottoVaccino(String numeroLottoVaccino) {
		this.numeroLottoVaccino = numeroLottoVaccino;
	}

	public boolean isFlagCattura() {
		return flagCattura;
	}

	public boolean getFlagCattura() {
		return flagCattura;
	}

	
	public void setFlagCattura(boolean flagCattura) {
		this.flagCattura = flagCattura;
	}

	
	public boolean getFlagMancataOrigine() {
		return flagMancataOrigine;
	}	
	
	public boolean isFlagMancataOrigine() {
		return flagMancataOrigine;
	}

	public void setFlagMancataOrigine(boolean flagMancataOrigine) {
		this.flagMancataOrigine = flagMancataOrigine;
	}

	public void setIdDetentore(int idDetentore) {
		this.idDetentore = idDetentore;
	}

	public void setIdDetentore(String idDetentore) {
		this.idDetentore = new Integer(idDetentore);
	}

	public void setDetentore(Operatore detentore) {
		this.detentore = detentore;
	}

	public int getIdDetentoreUltimoTrasferimentoFRegione() {
		return idDetentoreUltimoTrasferimentoFRegione;
	}

	public void setIdDetentoreUltimoTrasferimentoFRegione(
			int idDetentoreUltimoTrasferimentoFRegione) {
		this.idDetentoreUltimoTrasferimentoFRegione = idDetentoreUltimoTrasferimentoFRegione;
	}

	public void setIdDetentoreUltimoTrasferimentoFRegione(
			String idDetentoreUltimoTrasferimentoFRegione) {
		this.idDetentoreUltimoTrasferimentoFRegione = Integer
				.valueOf(idDetentoreUltimoTrasferimentoFRegione);
	}

	public Operatore getDetentore() {
		return detentore;
	}

	public int getIdDetentoreUltimoTrasferimentoFStato() {
		return idDetentoreUltimoTrasferimentoFStato;
	}

	public void setIdDetentoreUltimoTrasferimentoFStato(
			int idDetentoreUltimoTrasferimentoFStato) {
		this.idDetentoreUltimoTrasferimentoFStato = idDetentoreUltimoTrasferimentoFStato;
	}
	
	


	public boolean isFlagBloccato() {
		return flagBloccato;
	}

	public boolean getFlagBloccato() {
		return flagBloccato;
	}

	
	public void setFlagBloccato(boolean flagBloccato) {
		this.flagBloccato = flagBloccato;
	}

	public int getIdUltimaRegistrazioneBlocco() {
		return idUltimaRegistrazioneBlocco;
	}

	public void setIdUltimaRegistrazioneBlocco(int idUltimaRegistrazioneBlocco) {
		this.idUltimaRegistrazioneBlocco = idUltimaRegistrazioneBlocco;
	}



	public boolean isFlagValidato() {
		return flagValidato;
	}

	public boolean getFlagValidato() {
		return flagValidato;
	}

	
	public void setFlagValidato(boolean flagValidato) {
		this.flagValidato = flagValidato;
	}



	// Per il circuito commerciale
	private boolean flagCircuitoCommerciale = false; // Indica se l'animale
	// appartiene al
	// circuito commerciale
	private int idPartitaCircuitoCommerciale = -1;
	private boolean flagPresenzaEsitoControlloIdentita = false;
	private java.sql.Timestamp dataEsitoControlloIdentita = null;
	private int idEsitoControlloIdentita = -1;

	private boolean flagPresenzaEsitoControlloDocumentale = false;
	private java.sql.Timestamp dataEsitoControlloDocumentale = null;
	private int idEsitoControlloDocumentale = -1;

	private boolean flagPresenzaEsitoControlloFisico = false;
	private java.sql.Timestamp dataEsitoControlloFisico = null;
	private int idEsitoControlloFisico = -1;

	private boolean flagPresenzaEsitoControlloLaboratorio = false;
	private java.sql.Timestamp dataEsitoControlloLaboratorio = null;
	private int idEsitoControlloLaboratorio = -1;

	private boolean esitoLeishAnnoCorrente = false;

	private boolean flagVincoloCommerciale = false;

	private String noteInternalUseOnly = "";

	private String nomeCognomeProprietario = ""; // PER EVITARE NELLE LISTE
	// ANIMALI SEMPRE LA
	// COSTRUZIONE DEL PROP
	private String nomeCognomeDetentore = ""; // // PER EVITARE NELLE LISTE
	// ANIMALI SEMPRE LA COSTRUZIONE
	// DEL DET
	private int idComuneProprietario = -1; // // PER EVITARE NELLE LISTE ANIMALI

	// SEMPRE LA COSTRUZIONE DEL PROPR,
	// MI SERVE IL COMUNE IN ELENCO CANI
	// CANILE

	private int idComuneDetentore = -1; // // PER LISTA ANIMALI PRATICHE
										// CONTRIBUTI --COMUNE COLONIA
	
	//Informazioni attività itinerante
	private boolean flagAttivitaItinerante = false;
	private int idComuneAttivitaItinerante = -1;
	private String luogoAttivitaItinerante = "";
	private Timestamp dataAttivitaItinerante = null;
	
	
	

	public boolean isFlagAttivitaItinerante() {
		return flagAttivitaItinerante;
	}
	
	public boolean getFlagAttivitaItinerante() {
		return flagAttivitaItinerante;
	}

	public void setFlagAttivitaItinerante(boolean flagAttivitaItinerante) {
		this.flagAttivitaItinerante = flagAttivitaItinerante;
	}
	
	public void setFlagAttivitaItinerante(String flagAttivitaItinerante) {
		this.flagAttivitaItinerante =  DatabaseUtils
				.parseBoolean(flagAttivitaItinerante);
	}

	public int getIdComuneAttivitaItinerante() {
		return idComuneAttivitaItinerante;
	}

	public void setIdComuneAttivitaItinerante(int idComuneAttivitaItinerante) {
		this.idComuneAttivitaItinerante = idComuneAttivitaItinerante;
	}

	public String getLuogoAttivitaItinerante() {
		return luogoAttivitaItinerante;
	}

	public void setLuogoAttivitaItinerante(String luogoAttivitaItinerante) {
		this.luogoAttivitaItinerante = luogoAttivitaItinerante;
	}

	public Timestamp getDataAttivitaItinerante() {
		return dataAttivitaItinerante;
	}

	public void setDataAttivitaItinerante(Timestamp dataAttivitaItinerante) {
		this.dataAttivitaItinerante = dataAttivitaItinerante;
	}
	
	public void setDataAttivitaItinerante(String dataAttivitaItinerante) {
		this.dataAttivitaItinerante = 	DateUtils.parseDateStringNew(
				dataAttivitaItinerante, "dd/MM/yyyy");;
	}
	
	public void setIdComuneAttivitaItinerante(String idComuneAttivitaItinerante) {
		this.idComuneAttivitaItinerante = Integer.valueOf(idComuneAttivitaItinerante);
	}

	public int getIdComuneProprietario() {
		return idComuneProprietario;
	}

	public void setIdComuneProprietario(int idComuneProprietario) {
		this.idComuneProprietario = idComuneProprietario;
	}

	public int getIdPartitaCircuitoCommerciale() {
		return idPartitaCircuitoCommerciale;
	}

	// public boolean isFlagProvenienzaFuoriRegione() {
	// return flagProvenienzaFuoriRegione;
	// }
	//
	// public void setFlagProvenienzaFuoriRegione(
	// boolean flagProvenienzaFuoriRegione) {
	// this.flagProvenienzaFuoriRegione = flagProvenienzaFuoriRegione;
	// }
	//
	// public void setFlagProvenienzaFuoriRegione(
	// String flagProvenienzaFuoriRegione) {
	// this.flagProvenienzaFuoriRegione = DatabaseUtils
	// .parseBoolean(flagProvenienzaFuoriRegione);
	// }
	//
	// public int getIdRegioneProvenienza() {
	// return idRegioneProvenienza;
	// }
	//
	// public void setIdRegioneProvenienza(int idRegioneProvenienza) {
	// this.idRegioneProvenienza = idRegioneProvenienza;
	// }
	//
	// public void setIdRegioneProvenienza(String idRegioneProvenienza) {
	// this.idRegioneProvenienza = new Integer(idRegioneProvenienza)
	// .intValue();
	// }

	public Timestamp getDataMicrochip() {
		return dataMicrochip;
	}

	public void setDataMicrochip(Timestamp dataMicrochip) {
		this.dataMicrochip = dataMicrochip;
	}

	public void setDataMicrochip(String dataMicrochip) {
		this.dataMicrochip =  DateUtils.parseDateStringNew(dataMicrochip, "dd/MM/yyyy");
	}

	public void setIdPartitaCircuitoCommerciale(int idPartitaCircuitoCommerciale) {
		this.idPartitaCircuitoCommerciale = idPartitaCircuitoCommerciale;
	}

	public void setIdPartitaCircuitoCommerciale(
			String idPartitaCircuitoCommerciale) {
		this.idPartitaCircuitoCommerciale = new Integer(
				idPartitaCircuitoCommerciale).intValue();
	}

	public boolean isFlagPresenzaEsitoControlloIdentita() {
		return flagPresenzaEsitoControlloIdentita;
	}

	public boolean getFlagPresenzaEsitoControlloIdentita() {
		return flagPresenzaEsitoControlloIdentita;
	}

	public void setFlagPresenzaEsitoControlloIdentita(
			boolean flagPresenzaEsitoControlloIdentita) {
		this.flagPresenzaEsitoControlloIdentita = flagPresenzaEsitoControlloIdentita;
	}

	public void setFlagPresenzaEsitoControlloIdentita(
			String flagPresenzaEsitoControlloIdentita) {
		this.flagPresenzaEsitoControlloIdentita = DatabaseUtils
				.parseBoolean(flagPresenzaEsitoControlloIdentita);
	}

	public java.sql.Timestamp getDataEsitoControlloIdentita() {
		return dataEsitoControlloIdentita;
	}

	public void setDataEsitoControlloIdentita(
			java.sql.Timestamp dataEsitoControlloIdentita) {
		this.dataEsitoControlloIdentita = dataEsitoControlloIdentita;
	}

	public void setDataEsitoControlloIdentita(String dataEsitoControlloIdentita) {
		this.dataEsitoControlloIdentita = DateUtils.parseDateStringNew(
				dataEsitoControlloIdentita, "dd/MM/yyyy");
	}

	public int getIdEsitoControlloIdentita() {
		return idEsitoControlloIdentita;
	}

	public int getIdVeterinarioMicrochip() {
		return idVeterinarioMicrochip;
	}

	public void setIdVeterinarioMicrochip(int idVeterinarioMicrochip) {
		this.idVeterinarioMicrochip = idVeterinarioMicrochip;
	}

	public void setIdVeterinarioMicrochip(String idVeterinarioMicrochip) {
		this.idVeterinarioMicrochip = new Integer(idVeterinarioMicrochip)
				.intValue();
	}

	public void setIdEsitoControlloIdentita(int idEsitoControlloIdentita) {
		this.idEsitoControlloIdentita = idEsitoControlloIdentita;
	}

	public void setIdEsitoControlloIdentita(String idEsitoControlloIdentita) {
		this.idEsitoControlloIdentita = new Integer(idEsitoControlloIdentita)
				.intValue();
	}

	public boolean isFlagPresenzaEsitoControlloDocumentale() {
		return flagPresenzaEsitoControlloDocumentale;
	}

	
	public String getNoteInternalUseOnly() {
		return noteInternalUseOnly;
	}

	public void setNoteInternalUseOnly(String noteInternalUseOnly) {
		this.noteInternalUseOnly = noteInternalUseOnly;
	}

	public boolean getFlagPresenzaEsitoControlloDocumentale() {
		return flagPresenzaEsitoControlloDocumentale;
	}

	public void setFlagPresenzaEsitoControlloDocumentale(
			boolean flagPresenzaControlloDocumentale) {
		this.flagPresenzaEsitoControlloDocumentale = flagPresenzaControlloDocumentale;
	}

	public void setFlagPresenzaEsitoControlloDocumentale(
			String flagPresenzaControlloDocumentale) {
		this.flagPresenzaEsitoControlloDocumentale = DatabaseUtils
				.parseBoolean(flagPresenzaControlloDocumentale);
	}

	public int getIdProprietarioUltimoTrasferimentoFRegione() {
		return idProprietarioUltimoTrasferimentoFRegione;
	}

	public void setIdProprietarioUltimoTrasferimentoFRegione(
			int idProprietarioUltimoTrasferimentoFRegione) {
		this.idProprietarioUltimoTrasferimentoFRegione = idProprietarioUltimoTrasferimentoFRegione;
	}

	public void setIdProprietarioUltimoTrasferimentoFRegione(
			String idProprietarioUltimoTrasferimentoFRegione) {
		this.idProprietarioUltimoTrasferimentoFRegione = new Integer(
				idProprietarioUltimoTrasferimentoFRegione).intValue();
	}

	public int getIdProprietarioUltimoTrasferimentoFStato() {
		return idProprietarioUltimoTrasferimentoFStato;
	}

	public void setIdProprietarioUltimoTrasferimentoFStato(
			int idProprietarioUltimoTrasferimentoFStato) {
		this.idProprietarioUltimoTrasferimentoFStato = idProprietarioUltimoTrasferimentoFStato;
	}

	public void setIdProprietarioUltimoTrasferimentoFStato(
			String idProprietarioUltimoTrasferimentoFStato) {
		this.idProprietarioUltimoTrasferimentoFStato = new Integer(
				idProprietarioUltimoTrasferimentoFStato).intValue();
	}

	public java.sql.Timestamp getDataEsitoControlloDocumentale() {
		return dataEsitoControlloDocumentale;
	}

	public void setDataEsitoControlloDocumentale(
			String dataEsitoControlloDocumentale) {
		this.dataEsitoControlloDocumentale = DateUtils.parseDateStringNew(
				dataEsitoControlloDocumentale, "dd/MM/yyyy");
	}

	public void setDataEsitoControlloDocumentale(
			java.sql.Timestamp dataEsitoControlloDocumentale) {
		this.dataEsitoControlloDocumentale = dataEsitoControlloDocumentale;
	}

	public int getIdEsitoControlloDocumentale() {
		return idEsitoControlloDocumentale;
	}

	public void setIdEsitoControlloDocumentale(int idEsitoControlloDocumentale) {
		this.idEsitoControlloDocumentale = idEsitoControlloDocumentale;
	}

	public void setIdEsitoControlloDocumentale(
			String idEsitoControlloDocumentale) {
		this.idEsitoControlloDocumentale = new Integer(
				idEsitoControlloDocumentale).intValue();
	}

	public boolean isFlagPresenzaEsitoControlloFisico() {
		return flagPresenzaEsitoControlloFisico;
	}

	public boolean getFlagPresenzaEsitoControlloFisico() {
		return flagPresenzaEsitoControlloFisico;
	}

	public void setFlagPresenzaEsitoControlloFisico(
			boolean flagPresenzaControlloFisico) {
		this.flagPresenzaEsitoControlloFisico = flagPresenzaControlloFisico;
	}

	public void setFlagPresenzaEsitoControlloFisico(
			String flagPresenzaControlloFisico) {
		this.flagPresenzaEsitoControlloFisico = DatabaseUtils
				.parseBoolean(flagPresenzaControlloFisico);
	}

	public java.sql.Timestamp getDataEsitoControlloFisico() {
		return dataEsitoControlloFisico;
	}

	public void setDataEsitoControlloFisico(
			java.sql.Timestamp dataEsitoControlloFisico) {
		this.dataEsitoControlloFisico = dataEsitoControlloFisico;
	}

	public void setDataEsitoControlloFisico(String dataEsitoControlloFisico) {
		this.dataEsitoControlloFisico = DateUtils.parseDateStringNew(
				dataEsitoControlloFisico, "dd/MM/yyyy");
	}

	public int getIdEsitoControlloFisico() {
		return idEsitoControlloFisico;
	}

	public void setIdEsitoControlloFisico(int idEsitoControlloFisico) {
		this.idEsitoControlloFisico = idEsitoControlloFisico;
	}

	public void setIdEsitoControlloFisico(String idEsitoControlloFisico) {
		this.idEsitoControlloFisico = new Integer(idEsitoControlloFisico)
				.intValue();
	}

	public boolean isFlagPresenzaEsitoControlloLaboratorio() {
		return flagPresenzaEsitoControlloLaboratorio;
	}

	public boolean getFlagPresenzaEsitoControlloLaboratorio() {
		return flagPresenzaEsitoControlloLaboratorio;
	}

	public void setFlagPresenzaEsitoControlloLaboratorio(
			boolean flagPresenzaControlloLaboratorio) {
		this.flagPresenzaEsitoControlloLaboratorio = flagPresenzaControlloLaboratorio;
	}

	public void setFlagPresenzaEsitoControlloLaboratorio(
			String flagPresenzaControlloLaboratorio) {
		this.flagPresenzaEsitoControlloLaboratorio = DatabaseUtils
				.parseBoolean(flagPresenzaControlloLaboratorio);
	}

	public java.sql.Timestamp getDataEsitoControlloLaboratorio() {
		return dataEsitoControlloLaboratorio;
	}

	public void setDataEsitoControlloLaboratorio(
			java.sql.Timestamp dataEsitoControlloLaboratorio) {
		this.dataEsitoControlloLaboratorio = dataEsitoControlloLaboratorio;
	}

	public void setDataEsitoControlloLaboratorio(
			String dataEsitoControlloLaboratorio) {
		this.dataEsitoControlloLaboratorio = DateUtils.parseDateStringNew(
				dataEsitoControlloLaboratorio, "dd/MM/yyyy");
	}

	public int getIdEsitoControlloLaboratorio() {
		return idEsitoControlloLaboratorio;
	}

	public void setIdEsitoControlloLaboratorio(int idEsitoControlloLaboratorio) {
		this.idEsitoControlloLaboratorio = idEsitoControlloLaboratorio;
	}

	public void setIdEsitoControlloLaboratorio(
			String idEsitoControlloLaboratorio) {
		this.idEsitoControlloLaboratorio = new Integer(
				idEsitoControlloLaboratorio).intValue();
	}

	public boolean isFlagVincoloCommerciale() {
		return flagVincoloCommerciale;
	}

	public boolean getFlagVincoloCommerciale() {
		return flagVincoloCommerciale;
	}

	public void setFlagVincoloCommerciale(boolean flagVincoloCommerciale) {
		this.flagVincoloCommerciale = flagVincoloCommerciale;
	}

	public void setFlagVincoloCommerciale(String flagVincoloCommerciale) {
		this.flagVincoloCommerciale = DatabaseUtils
				.parseBoolean(flagVincoloCommerciale);
	}

	public boolean isEsitoLeishAnnoCorrente() {
		return esitoLeishAnnoCorrente;
	}
	
	public boolean getEsitoLeishAnnoCorrente() {
		return esitoLeishAnnoCorrente;
	}

	public void setEsitoLeishAnnoCorrente(boolean esitoLeishAnnoCorrente) {
		this.esitoLeishAnnoCorrente = esitoLeishAnnoCorrente;
	}

	public String getTatuaggio() {
		return tatuaggio;
	}

	/*
	 * public String getTatuaggio() { Connection con = null; String tatuaggio =
	 * ""; try { con = GestoreConnessioni.getConnection(); switch (idSpecie) {
	 * case Cane.idSpecie: Cane cane = new Cane(con, this.idAnimale); tatuaggio
	 * = cane.getTatuaggio(); break;
	 * 
	 * case Gatto.idSpecie: Gatto gatto = new Gatto(con, this.idAnimale);
	 * tatuaggio = gatto.getTatuaggio();
	 * 
	 * default: return ""; } } catch (Exception e) { // TODO: handle exception }
	 * finally { GestoreConnessioni.freeConnection(con); } return tatuaggio; }
	 */

	public java.sql.Timestamp getDataTatuaggio() {
		return dataTatuaggio;
	}

	public void setDataTatuaggio(java.sql.Timestamp dataTatuaggio) {
		this.dataTatuaggio = dataTatuaggio;
	}

	public void setDataTatuaggio(String dataTatuaggio) {
		this.dataTatuaggio = DateUtils.parseDateStringNew(dataTatuaggio,
				"dd/MM/yyyy");
	}

	public void setTatuaggio(String tatuaggio) {
		this.tatuaggio = tatuaggio;
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		Animale.log = log;
	}

	public int getIdAnimale() {
		return idAnimale;
	}

	public void setIdAnimale(int idAnimale) {
		this.idAnimale = idAnimale;
	}

	public void setIdAnimale(String idAnimale) {
		this.idAnimale = new Integer(idAnimale).intValue();
	}

	public java.sql.Timestamp getDataRegistrazione() {
		return dataRegistrazione;
	}

	public void setDataRegistrazione(String dataRegistrazione) {
		this.dataRegistrazione = DateUtils.parseDateStringNew(
				dataRegistrazione, "dd/MM/yyyy");
	}

	public int getIdProprietario() {
		return idProprietario;
	}

	public void setIdProprietario(int idProprietario) {
		this.idProprietario = idProprietario;
	}

	public void setIdProprietario(String idProprietario) {
		this.idProprietario = new Integer(idProprietario).intValue();
	}

	public java.sql.Timestamp getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(java.sql.Timestamp dataNascita) {
		this.dataNascita = dataNascita;
	}

	public void setDataNascita(String data) {
		this.dataNascita = DateUtils.parseDateStringNew(data, "dd/MM/yyyy");
	}

	public int getIdSpecie() {
		return idSpecie;
	}

	public void setIdSpecie(int idSpecie) {
		this.idSpecie = idSpecie;
	}

	public void setIdSpecie(String id) {
		this.idSpecie = new Integer(id).intValue();
	}

	public int getIdTipoMantello() {
		return idTipoMantello;
	}

	public int getIdTaglia() {
		return idTaglia;
	}

	public void setIdTaglia(int idTaglia) {
		this.idTaglia = idTaglia;
	}

	public void setIdTaglia(String idTaglia) {
		this.idTaglia = new Integer(idTaglia);
	}

	public String getDecodificaIdTaglia(Connection db) {
		LookupList tagliaList = new LookupList();
		try {

			tagliaList.setTable("lookup_taglia");
			tagliaList.buildList(db);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return tagliaList.getSelectedValue(this.getIdTaglia());
	}
	
	public String getDecodificaIdAslRiferimento(Connection db) {
		LookupList aslList = new LookupList();
		try {

			aslList.setTable("lookup_asl_rif");
			aslList.buildList(db);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return aslList.getSelectedValue(this.getIdAslRiferimento());
	}
	
	

	public String getDecodificaIdTipoMantello(Connection db) {
		LookupList mantelloList = new LookupList();
		try {

			mantelloList.setTable("lookup_mantello");
			mantelloList.setIdSpecie(idSpecie);
			mantelloList.buildList(db);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mantelloList.getSelectedValue(this.getIdTipoMantello());
	}
	
	public String getDecodificaIdProprietario(Connection db) {
		Operatore prop = new Operatore();
		try {

			prop.queryRecordOperatorebyIdLineaProduttiva(db, idProprietario);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop.getRagioneSociale();
	}
	
	public String getDecodificaIdDetentore(Connection db) {
		Operatore det = new Operatore();
		try {

			det.queryRecordOperatorebyIdLineaProduttiva(db, idDetentore);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return det.getRagioneSociale();
	}

	public void setIdTipoMantello(int idTipoMantello) {
		this.idTipoMantello = idTipoMantello;
	}

	public void setIdTipoMantello(String id) {
		this.idTipoMantello = new Integer(id).intValue();
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getNome() {
		return nome;
	}

	public int getIdComuneDetentore() {
		return idComuneDetentore;
	}

	public void setIdComuneDetentore(int idComuneDetentore) {
		this.idComuneDetentore = idComuneDetentore;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMicrochip() {
		return microchip;
	}

	public void setMicrochip(String microchip) {
		this.microchip = microchip;
	}

	// public java.sql.Timestamp getDataMicrochip() {
	// return dataMicrochip;
	// }
	//
	// public void setDataMicrochip(java.sql.Timestamp dataMicrochip) {
	// this.dataMicrochip = dataMicrochip;
	// }
	//
	// public void setDataMicrochip(String data) {
	// this.dataMicrochip = DateUtils.parseDateStringNew(data, "dd/MM/yyyy");
	// }

	public String getSegniParticolari() {
		return segniParticolari;
	}

	public boolean isContributoPagato() {
		return contributoPagato;
	}

	public boolean getContributoPagato() {
		return contributoPagato;
	}

	public void setContributoPagato(boolean contributoPagato) {
		this.contributoPagato = contributoPagato;
	}

	public void setSegniParticolari(String segniParticolari) {
		this.segniParticolari = segniParticolari;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNota2() {
		return nota2;
	}

	public void setNota2(String nota2) {
		this.nota2 = nota2;
	}

	public java.sql.Timestamp getDataRilascioPassaporto() {
		return dataRilascioPassaporto;
	}

	public void setDataRilascioPassaporto(
			java.sql.Timestamp dataRilascioPassaporto) {
		this.dataRilascioPassaporto = dataRilascioPassaporto;
	}

	public void setDataRilascioPassaporto(String data) {
		this.dataRilascioPassaporto = DateUtils.parseDateStringNew(data,
				"dd/MM/yyyy");
	}
	
	
	
	public java.sql.Timestamp getDataScadenzaPassaporto() {
		return dataScadenzaPassaporto;
	}

	public void setDataScadenzaPassaporto(
			java.sql.Timestamp dataRilascioPassaporto) {
		this.dataScadenzaPassaporto = dataRilascioPassaporto;
	}

	public void setDataScadenzaPassaporto(String data) {
		this.dataScadenzaPassaporto = DateUtils.parseDateStringNew(data,
				"dd/MM/yyyy");
	}

	//
	// public String getDescrizioneStato() {
	// return descrizioneStato;
	// }
	//
	// public void setDescrizioneStato(String descrizioneStato) {
	// this.descrizioneStato = descrizioneStato;
	// }
	//
	//
	//
	public String getNumeroPassaporto() {
		return numeroPassaporto;
	}

	public Timestamp getDataSterilizzazione() {
		return dataSterilizzazione;
	}

	public void setDataSterilizzazione(Timestamp dataSterilizzazione) {
		this.dataSterilizzazione = dataSterilizzazione;
	}

	public void setDataSterilizzazione(String dataSterilizzazione) {
		this.dataSterilizzazione =  DateUtils.parseDateStringNew(dataSterilizzazione, "dd/MM/yyyy");
		if (this.dataSterilizzazione != null) {
			setFlagSterilizzazione(true);
		}
	}

	public boolean isFlagSterilizzazione() {
		return flagSterilizzazione;
	}

	public boolean getFlagSterilizzazione() {
		return flagSterilizzazione;
	}

	public void setFlagSterilizzazione(boolean flagSterilizzazione) {
		this.flagSterilizzazione = flagSterilizzazione;
	}

	public void setFlagSterilizzazione(String flagSterilizzazione) {
		this.flagSterilizzazione = DatabaseUtils
				.parseBoolean(flagSterilizzazione);
	}

	public boolean isFlagContributoRegionale() {
		return flagContributoRegionale;
	}

	public boolean getFlagContributoRegionale() {
		return flagContributoRegionale;
	}

	public void setFlagContributoRegionale(boolean flagContributoRegionale) {
		this.flagContributoRegionale = flagContributoRegionale;
	}

	public void setFlagContributoRegionale(String flagContributoRegionale) {
		this.flagContributoRegionale = DatabaseUtils
				.parseBoolean(flagContributoRegionale);
	}

	public boolean isFlagDecesso() {
		return flagDecesso;
	}

	public boolean getFlagDecesso() {
		return flagDecesso;
	}

	public void setFlagDecesso(boolean flagDecesso) {
		this.flagDecesso = flagDecesso;
	}

	public void setFlagDecesso(String flagDecesso) {
		this.flagDecesso = DatabaseUtils.parseBoolean(flagDecesso);
	}

	public boolean isFlagSmarrimento() {
		return flagSmarrimento;
	}

	public boolean getFlagSmarrimento() {
		return flagSmarrimento;
	}

	public void setFlagSmarrimento(boolean flagSmarrimento) {
		this.flagSmarrimento = flagSmarrimento;
	}

	public void setFlagSmarrimento(String flagSmarrimento) {
		this.flagSmarrimento = DatabaseUtils.parseBoolean(flagSmarrimento);
	}

	public boolean isFlagFurto() {
		return flagFurto;
	}

	public boolean getFlagFurto() {
		return flagFurto;
	}
	
	public void setFlagFurto(boolean flagFurto) {
		this.flagFurto = flagFurto;
	}

	public void setNumeroPassaporto(String numeroPassaporto) {
		this.numeroPassaporto = numeroPassaporto;
	}

	public int getIdRazza() {
		return idRazza;
	}

	public String getNomeCognomeProprietario() {
		return nomeCognomeProprietario;
	}

	public void setNomeCognomeProprietario(String nomeCognomeProprietario) {
		this.nomeCognomeProprietario = nomeCognomeProprietario;
	}

	public String getNomeCognomeDetentore() {
		return nomeCognomeDetentore;
	}

	public void setNomeCognomeDetentore(String nomeCognomeDetentore) {
		this.nomeCognomeDetentore = nomeCognomeDetentore;
	}

	public String getDecodificaIdRazza(Connection db) {
		LookupList razzaList = new LookupList();
		try {

			razzaList.setTable("lookup_razza");
			razzaList.setIdSpecie(idSpecie);
			razzaList.buildList(db);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return razzaList.getSelectedValue(this.getIdRazza());
	}

	public void setIdRazza(int idRazza) {
		this.idRazza = idRazza;
	}

	public void setIdRazza(String id) {
		this.idRazza = new Integer(id).intValue();
	}

	public boolean isFlagDataNascitaPresunta() {
		return flagDataNascitaPresunta;
	}

	public boolean getFlagDataNascitaPresunta() {
		return flagDataNascitaPresunta;
	}

	public void setFlagDataNascitaPresunta(boolean flagDataNascitaPresunta) {
		this.flagDataNascitaPresunta = flagDataNascitaPresunta;
	}

	public void setFlagDataNascitaPresunta(String tmp) {
		this.flagDataNascitaPresunta = DatabaseUtils.parseBoolean(tmp);
	}

	// public boolean isFlagControlloDocumentale() {
	// return flagControlloDocumentale;
	// }
	//
	// public void setFlagControlloDocumentale(boolean flagControlloDocumentale)
	// {
	// this.flagControlloDocumentale = flagControlloDocumentale;
	// }
	//
	// public void setFlagControlloDocumentale(String tmp) {
	// this.flagControlloDocumentale = DatabaseUtils.parseBoolean(tmp);
	// }
	//
	// public boolean isFlagControlloIdentita() {
	// return flagControlloIdentita;
	// }
	//
	// public void setFlagControlloIdentita(boolean flagControlloIdentita) {
	// this.flagControlloIdentita = flagControlloIdentita;
	// }
	//
	// public void setFlagControlloIdentita(String tmp) {
	// this.flagControlloIdentita = DatabaseUtils.parseBoolean(tmp);
	// }
	//
	// public boolean isFlagControlloFisico() {
	// return flagControlloFisico;
	// }
	//
	// public void setFlagControlloFisico(boolean flagControlloFisico) {
	// this.flagControlloFisico = flagControlloFisico;
	// }
	//
	// public void setFlagControlloFisico(String tmp) {
	// this.flagControlloFisico = DatabaseUtils.parseBoolean(tmp);
	// }
	//
	// public boolean isFlagControlloLaboratorio() {
	// return flagControlloLaboratorio;
	// }
	//
	// public void setFlagControlloLaboratorio(boolean flagControlloLaboratorio)
	// {
	// this.flagControlloLaboratorio = flagControlloLaboratorio;
	// }
	//
	// public void setFlagControlloLaboratorio(String tmp) {
	// this.flagControlloLaboratorio = DatabaseUtils.parseBoolean(tmp);
	// }
	//

	//
	// public java.sql.Timestamp getDataAggiornamentoStato() {
	// return dataAggiornamentoStato;
	// }
	//
	// public void setDataAggiornamentoStato(java.sql.Timestamp
	// dataAggiornamentoStato) {
	// this.dataAggiornamentoStato = dataAggiornamentoStato;
	// }
	//
	// public void setDataAggiornamentoStato(String data) {
	// this.dataAggiornamentoStato = DateUtils.parseDateStringNew(data,
	// "dd/MM/yyyy");
	// }
	//
	// public java.sql.Timestamp getDataControlloDocumentale() {
	// return dataControlloDocumentale;
	// }
	//
	// public void setDataControlloDocumentale(
	// java.sql.Timestamp dataControlloDocumentale) {
	// this.dataControlloDocumentale = dataControlloDocumentale;
	// }
	//
	// public void setDataControlloDocumentale(String data) {
	// this.dataControlloDocumentale = DateUtils.parseDateStringNew(data,
	// "dd/MM/yyyy");
	// }
	//
	// public java.sql.Timestamp getDataControlloIdentita() {
	// return dataControlloIdentita;
	// }
	//
	// public void setDataControlloIdentità(java.sql.Timestamp
	// dataControlloIdentita) {
	// this.dataControlloIdentita = dataControlloIdentita;
	// }
	//
	// public void setDataControlloIdentita(String data) {
	// this.dataControlloIdentita = DateUtils.parseDateStringNew(data,
	// "dd/MM/yyyy");
	// }
	//
	// public java.sql.Timestamp getDataControlloFisico() {
	// return dataControlloFisico;
	// }
	//
	// public void setDataControlloFisico(java.sql.Timestamp
	// dataControlloFisico) {
	// this.dataControlloFisico = dataControlloFisico;
	// }
	//
	// public void setDataControlloFisico(String data) {
	// this.dataControlloFisico = DateUtils.parseDateStringNew(data,
	// "dd/MM/yyyy");
	// }
	//
	// public java.sql.Timestamp getDataControlloLaboratorio() {
	// return dataControlloLaboratorio;
	// }
	//
	// public void setDataControlloLaboratorio(
	// java.sql.Timestamp dataControlloLaboratorio) {
	// this.dataControlloLaboratorio = dataControlloLaboratorio;
	// }
	//
	// public void setDataControlloLaboratorio(String data) {
	// this.dataControlloLaboratorio = DateUtils.parseDateStringNew(data,
	// "dd/MM/yyyy");
	// }
	//
	//
	// public ListaControlliAnimale getControlliAnimale() {
	// return controlliAnimale;
	// }
	//
	// public void setControlliAnimale(ListaControlliAnimale controlliAnimale) {
	// this.controlliAnimale = controlliAnimale;
	// }

	public boolean isFlagCircuitoCommerciale() {
		return flagCircuitoCommerciale;
	}


	public boolean getFlagCircuitoCommerciale() {
		return flagCircuitoCommerciale;
	}
	
	public void setFlagCircuitoCommerciale(boolean flagCircuitoCommerciale) {
		this.flagCircuitoCommerciale = flagCircuitoCommerciale;
	}

	public void setFlagCircuitoCommerciale(String flagCircuitoCommerciale) {
		this.flagCircuitoCommerciale = DatabaseUtils
				.parseBoolean(flagCircuitoCommerciale);
	}

	public int getIdPraticaContributi() {
		return idPraticaContributi;
	}

	public void setIdPraticaContributi(int idPraticaContributi) {
		this.idPraticaContributi = idPraticaContributi;
	}

	public void setIdPraticaContributi(String idPraticaContributi) {
		this.idPraticaContributi = new Integer(idPraticaContributi).intValue();
	}

	public int getStato() {
		return stato;
	}

	public void setStato(int stato) {
		this.stato = stato;
	}

	public void setStato(String stato) {
		this.stato = (new Integer(stato)).intValue();
	}

	public int getIdAslRiferimento() {
		return idAslRiferimento;
	}

	public void setIdAslRiferimento(int idAslRiferimento) {
		this.idAslRiferimento = idAslRiferimento;
	}

	public void setIdAslRiferimento(String id) {
		this.idAslRiferimento = new Integer(id).intValue();
	}

	public java.sql.Timestamp getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(java.sql.Timestamp dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public void setDataInserimento(String data) {
		this.dataInserimento = DateUtils.parseDateStringNew(data, "dd/MM/yyyy");
	}

	public int getIdUtenteInserimento() {
		return idUtenteInserimento;
	}

	public void setIdUtenteInserimento(int idUtenteInserimento) {
		this.idUtenteInserimento = idUtenteInserimento;
	}

	public void setIdUtenteInserimento(String id) {
		this.idUtenteInserimento = new Integer(id).intValue();
	}

	public java.sql.Timestamp getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(java.sql.Timestamp dataModifica) {
		this.dataModifica = dataModifica;
	}

	public void setDataModifica(String data) {
		this.dataModifica = DateUtils.parseDateStringNew(data, "dd/MM/yyyy");
	}

	public int getIdUtenteModifica() {
		return idUtenteModifica;
	}

	public void setIdUtenteModifica(int idUtenteModifica) {
		this.idUtenteModifica = idUtenteModifica;
	}

	public void setIdUtenteModifica(String id) {
		this.idUtenteModifica = new Integer(id).intValue();
	}

	public java.sql.Timestamp getDataCancellazione() {
		return dataCancellazione;
	}

	public void setDataCancellazione(java.sql.Timestamp dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public void setDataCancellazione(String data) {
		this.dataCancellazione = DateUtils.parseDateStringNew(data,
				"dd/MM/yyyy");
	}

	public int getIdUtenteCancellazione() {
		return idUtenteCancellazione;
	}

	public void setIdUtenteCancellazione(int idUtenteCancellazione) {
		this.idUtenteCancellazione = idUtenteCancellazione;
	}

	public void setIdUtenteCancellazione(String id) {
		this.idUtenteCancellazione = new Integer(id).intValue();
	}

	// public int getIdPraticaContributi() {
	// return idPraticaContributi;
	// }
	//
	// public void setIdPraticaContributi(int idPraticaContributi) {
	// this.idPraticaContributi = idPraticaContributi;
	// }
	//
	//
	//
	// public int getIdPraticaContributiSeparata() {
	// return idPraticaContributiSeparata;
	// }
	//
	// public void setIdPraticaContributiSeparata(int
	// idPraticaContributiSeparata) {
	// this.idPraticaContributiSeparata = idPraticaContributiSeparata;
	// }

	public Operatore getProprietario() {
		return proprietario;
	}

	public void setProprietario(Operatore proprietario) {
		this.proprietario = proprietario;
	}
	
	public Operatore getProprietarioProvenienza() {
		return proprietarioProvenienza;
	}

	public void setProprietarioProvenienza(Operatore proprietarioProvenienza) {
		this.proprietarioProvenienza = proprietarioProvenienza;
	}

	// public SoggettoFisico getVeterinarioChip() {
	// return veterinarioChip;
	// }
	//
	// public void setVeterinarioChip(SoggettoFisico veterinarioChip) {
	// this.veterinarioChip = veterinarioChip;
	// }
	//
	public void setDataRegistrazione(java.sql.Timestamp dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	//
	//
	//
	// public java.sql.Timestamp getDataControlloRickettsiosi() {
	// return dataControlloRickettsiosi;
	// }
	//
	// public void setDataControlloRickettsiosi(
	// java.sql.Timestamp dataControlloRickettsiosi) {
	// this.dataControlloRickettsiosi = dataControlloRickettsiosi;
	// }
	//
	// public void setEsitoControlloEhrlichiosi(String
	// esitoControlloEhrlichiosi){
	// this.esitoControlloEhrlichiosi = new
	// Integer(esitoControlloEhrlichiosi).intValue();
	// }
	//
	// public int getEsitoControlloEhrlichiosi() {
	// return esitoControlloEhrlichiosi;
	// }
	//
	// public void setEsitoControlloEhrlichiosi(int esitoControlloEhrlichiosi) {
	// this.esitoControlloEhrlichiosi = esitoControlloEhrlichiosi;
	// }
	//
	//
	//
	// public LeishList getEsitiLeish() {
	// return esitiLeish;
	// }
	//
	// public void setEsitiLeish(LeishList esitiLeish) {
	// this.esitiLeish = esitiLeish;
	// }
	//
	// public void setDataControlloIdentita(java.sql.Timestamp
	// dataControlloIdentita) {
	// this.dataControlloIdentita = dataControlloIdentita;
	// }

	public Animale() {
		super();
	}

	public Timestamp getDataScadenzaVaccino() {
		return dataScadenzaVaccino;
	}

	public void setDataScadenzaVaccino(Timestamp dataScadenzaVaccino) {
		this.dataScadenzaVaccino = dataScadenzaVaccino;
	}

	public String getNomeVaccino() {
		return nomeVaccino;
	}

	public void setNomeVaccino(String nomeVaccino) {
		this.nomeVaccino = nomeVaccino;
	}

	public String getProduttoreVaccino() {
		return produttoreVaccino;
	}

	public void setProduttoreVaccino(String produttoreVaccino) {
		this.produttoreVaccino = produttoreVaccino;
	}

	public int getIdRegione() {
		return idRegione;
	}

	public void setIdRegione(int idRegione) {
		this.idRegione = idRegione;
	}

	public void setIdRegione(String idRegione) {
		this.idRegione = new Integer(idRegione).intValue();
	}

	public int getIdContinente() {
		return idContinente;
	}

	public void setIdContinente(int idContinente) {
		this.idContinente = idContinente;
	}

	public void setIdContinente(String idContinente) {
		this.idContinente = new Integer(idContinente).intValue();
	}

	public boolean isFlagUltimaOperazioneFuoriDominioAsl() {
		return flagUltimaOperazioneFuoriDominioAsl;
	}
	
	public boolean getFlagUltimaOperazioneFuoriDominioAsl() {
		return flagUltimaOperazioneFuoriDominioAsl;
	}

	public void setFlagUltimaOperazioneFuoriDominioAsl(
			boolean flagUltimaOperazioneFuoriDominioAsl) {
		this.flagUltimaOperazioneFuoriDominioAsl = flagUltimaOperazioneFuoriDominioAsl;
	}

	public int getIdAslUltimaOperazioneFuoriDominioAsl() {
		return idAslUltimaOperazioneFuoriDominioAsl;
	}

	public void setIdAslUltimaOperazioneFuoriDominioAsl(
			int idAslUltimaOperazioneFuoriDominioAsl) {
		this.idAslUltimaOperazioneFuoriDominioAsl = idAslUltimaOperazioneFuoriDominioAsl;
	}

	public int getIdRegistrazioneUltimaOperazioneFuoriDominioAsl() {
		return idRegistrazioneUltimaOperazioneFuoriDominioAsl;
	}

	public void setIdRegistrazioneUltimaOperazioneFuoriDominioAsl(
			int idRegistrazioneUltimaOperazioneFuoriDominioAsl) {
		this.idRegistrazioneUltimaOperazioneFuoriDominioAsl = idRegistrazioneUltimaOperazioneFuoriDominioAsl;
	}

	public int getIdDetentoreUltimaOperazioneFuoriDominioAsl() {
		return idDetentoreUltimaOperazioneFuoriDominioAsl;
	}

	public void setIdDetentoreUltimaOperazioneFuoriDominioAsl(
			int idDetentoreUltimaOperazioneFuoriDominioAsl) {
		this.idDetentoreUltimaOperazioneFuoriDominioAsl = idDetentoreUltimaOperazioneFuoriDominioAsl;
	}

	public int getIdStatoUltimaOperazioneFuoriDominioAsl() {
		return idStatoUltimaOperazioneFuoriDominioAsl;
	}

	public void setIdStatoUltimaOperazioneFuoriDominioAsl(
			int idStatoUltimaOperazioneFuoriDominioAsl) {
		this.idStatoUltimaOperazioneFuoriDominioAsl = idStatoUltimaOperazioneFuoriDominioAsl;
	}

	public int getIdTipologiaUltimaOperazioneFuoriDominioAsl() {
		return idTipologiaUltimaOperazioneFuoriDominioAsl;
	}

	public void setIdTipologiaUltimaOperazioneFuoriDominioAsl(
			int idTipologiaUltimaOperazioneFuoriDominioAsl) {
		this.idTipologiaUltimaOperazioneFuoriDominioAsl = idTipologiaUltimaOperazioneFuoriDominioAsl;
	}

	public boolean insert(Connection db)
			throws SQLIntegrityConstraintViolationException, SQLException {

		StringBuffer sql = new StringBuffer();

		if(idAnimale<=0)
			idAnimale = DatabaseUtils.getNextSeqPostgres(db, "animale_id_seq");
		// sql.append("INSERT INTO animale (");

		sql.append("INSERT INTO animale( ");

		if (idAnimale > -1) {
			sql.append("id, ");
		}

		sql.append("microchip,nome, data_nascita, flag_data_nascita_presunta,"
				+ "sesso, id_specie, id_razza, id_tipo_mantello, id_proprietario,"
				+ "id_asl_riferimento,data_registrazione,utente_inserimento,utente_modifica,"
				+ "flag_contributo_regionale,flag_decesso,flag_smarrimento,flag_sterilizzazione, id_detentore, flag_detenuto_in_canile_dopo_ritrovamento, "
				+ "flag_ultima_operazione_eseguita_fuori_dominio_asl, id_asl_fuori_dominio_ultima_registrazione, "
				+ "id_evento_ultima_registrazione_fuori_dominio, id_detentore_ultima_registrazione_fuori_dominio, id_stato_ultima_registrazione_fuori_dominio, "
				+ "id_tipologia_registrazione_fuori_dominio_asl");

		if (segniParticolari != null) {
			sql.append(",segni_particolari");
		}
		if (note != null) {
			sql.append(",note");
		}

		if (dataSterilizzazione != null) {
			sql.append(",data_sterilizzazione");
		}
		if (dataRilascioPassaporto != null) {
			sql.append(",data_rilascio_passaporto");
		}
		if (numeroPassaporto != null) {
			sql.append(",numero_passaporto");
		}
		if (tatuaggio != null) {
			sql.append(",tatuaggio");
		}

		if (dataTatuaggio != null) {
			sql.append(", data_tatuaggio");
		}

		if (stato > -1) {
			sql.append(", stato");
		}

		if (idPartitaCircuitoCommerciale > -1) {
			sql.append(", id_partita_circuito_commerciale");
		}

		if (flagPresenzaEsitoControlloDocumentale) {
			sql.append(", flag_controllo_documentale");
		}
		if (flagPresenzaEsitoControlloFisico) {
			sql.append(", flag_controllo_fisico");
		}
		if (flagPresenzaEsitoControlloLaboratorio) {
			sql.append(", flag_controllo_laboratorio");
		}
		if (flagPresenzaEsitoControlloIdentita) {
			sql.append(", flag_controllo_identita");
		}

		if (idEsitoControlloDocumentale > 0) {
			sql.append(", id_esito_controllo_documentale");
		}
		if (idEsitoControlloFisico > 0) {
			sql.append(", id_esito_controllo_fisico");
		}
		if (idEsitoControlloLaboratorio > 0) {
			sql.append(", id_esito_controllo_laboratorio");
		}
		if (idEsitoControlloIdentita > 0) {
			sql.append(", id_esito_controllo_identita");
		}

		if (dataEsitoControlloDocumentale != null) {
			sql.append(", data_esito_controllo_documentale");
		}
		if (dataEsitoControlloFisico != null) {
			sql.append(", data_esito_controllo_fisico");
		}
		if (dataEsitoControlloLaboratorio != null) {
			sql.append(", data_esito_controllo_laboratorio");
		}
		if (dataEsitoControlloIdentita != null) {
			sql.append(", data_esito_controllo_identita");
		}

		if (idProprietarioUltimoTrasferimentoFRegione > 0) {
			sql.append(", id_proprietario_ultimo_trasferimento_a_regione");
		}

		if (idProprietarioUltimoTrasferimentoFStato > 0) {
			sql.append(", id_proprietario_ultimo_trasferimento_a_stato");
		}

		if (flagCircuitoCommerciale) {
			sql.append(", flag_circuito_commerciale");
		}

		if (idVeterinarioMicrochip > 0) {
			sql.append(", id_veterinario_microchip");
		}

		if (dataMicrochip != null) {
			sql.append(", data_microchip");
		}

		if (noteInternalUseOnly != null && !("").equals(noteInternalUseOnly)) {
			sql.append(", note_internal_use_only");
		}

		if (idTaglia > 0) {
			sql.append(", id_taglia");
		}

		if (dataVaccino != null) {
			sql.append(",vaccino_data");
		}
		if (numeroLottoVaccino != null) {
			sql.append(",vaccino_numero_lotto");
		}

		if (nomeVaccino != null) {
			sql.append(",vaccino_nome");
		}

		if (produttoreVaccino != null) {
			sql.append(",vaccino_produttore");
		}

		if (dataScadenzaVaccino != null) {
			sql.append(",vaccino_data_scadenza");
		}
		
		if (flagAttivitaItinerante){
			sql.append(", flag_attivita_itinerante");
		}
		
		if (idComuneAttivitaItinerante > 0){
			sql.append(", id_comune_attivita_itinerante");
		}
		
		if (luogoAttivitaItinerante != null && !("").equals(luogoAttivitaItinerante)){
			sql.append(", luogo_attivita_itinerante");
		}
		
		if (dataAttivitaItinerante != null){
			sql.append(", data_attivita_itinerante");
		}
		
		
		if (flagValidato){
			sql.append(", flag_validato");
		}
		

		sql.append(", origine_registrazione");
		
		sql.append(", flag_mancata_origine");

		sql.append(")");

		sql.append("VALUES ( ");

		if (idAnimale > -1) {
			sql.append("?, ");
		}

		sql.append(" " +

		" ?, ?," + " ?, ?, ?, " + "?, ?,?, " + "?, ?, ?,"
				+ "?,?,?,false,false,?,?,?,?,?,?,?,?,?");
		/*
		 * ?, ?," + "?, ?, ?, " + "?, ?, ?, " + "" + "?, ?, ?, " + "?, ?, ?, " +
		 * "?, ?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?, " + "?, ?, " + "?, ?, ?," +
		 * "?, ?, ?, ?" + "");
		 */
		// if (dataRilascioPassaporto != null){
		// sql.append(", ? ");
		// }
		//
		// if(numeroPassaporto != null && !numeroPassaporto.equals("")){
		// sql.append(", ?");
		// }
		if (segniParticolari != null) {
			sql.append(",?");
		}
		if (note != null) {
			sql.append(",?");
		}

		if (dataSterilizzazione != null) {
			sql.append(",?");
		}
		if (dataRilascioPassaporto != null) {
			sql.append(",?");
		}
		if (numeroPassaporto != null) {
			sql.append(",?");
		}
		if (tatuaggio != null) {
			sql.append(",?");
		}

		if (dataTatuaggio != null) {
			sql.append(", ?");
		}

		if (stato > -1) {
			sql.append(",?");
		}

		if (idPartitaCircuitoCommerciale > 0) {
			sql.append(", ?");
		}

		if (flagPresenzaEsitoControlloDocumentale) {
			sql.append(", ?");
		}
		if (flagPresenzaEsitoControlloFisico) {
			sql.append(", ?");
		}
		if (flagPresenzaEsitoControlloLaboratorio) {
			sql.append(", ?");
		}
		if (flagPresenzaEsitoControlloIdentita) {
			sql.append(", ?");
		}

		if (idEsitoControlloDocumentale > 0) {
			sql.append(", ?");
		}
		if (idEsitoControlloFisico > 0) {
			sql.append(", ?");
		}
		if (idEsitoControlloLaboratorio > 0) {
			sql.append(", ?");
		}
		if (idEsitoControlloIdentita > 0) {
			sql.append(", ?");
		}

		if (dataEsitoControlloDocumentale != null) {
			sql.append(", ?");
		}
		if (dataEsitoControlloFisico != null) {
			sql.append(", ?");
		}
		if (dataEsitoControlloLaboratorio != null) {
			sql.append(", ?");
		}
		if (dataEsitoControlloIdentita != null) {
			sql.append(", ?");
		}

		if (idProprietarioUltimoTrasferimentoFRegione > 0) {
			sql.append(", ?");
		}

		if (idProprietarioUltimoTrasferimentoFStato > 0) {
			sql.append(", ?");
		}

		if (flagCircuitoCommerciale) {
			sql.append(", ?");
		}

		if (idVeterinarioMicrochip > 0) {
			sql.append(", ?");
		}

		if (dataMicrochip != null) {
			sql.append(", ?");
		}

		if (noteInternalUseOnly != null && !("").equals(noteInternalUseOnly)) {
			sql.append(", ?");
		}

		if (idTaglia > 0) {
			sql.append(", ?");
		}

		if (dataVaccino != null) {
			sql.append(", ?");
		}
		if (numeroLottoVaccino != null) {
			sql.append(", ?");
		}

		if (nomeVaccino != null) {
			sql.append(", ?");
		}

		if (produttoreVaccino != null) {
			sql.append(", ?");
		}

		if (dataScadenzaVaccino != null) {
			sql.append(", ?");
		}
		
		
		if (flagAttivitaItinerante){
			sql.append(", ?");
		}
		
		if (idComuneAttivitaItinerante > 0){
			sql.append(", ?");
		}
		
		if (luogoAttivitaItinerante != null && !("").equals(luogoAttivitaItinerante)){
			sql.append(", ?");
		}
		
		if (dataAttivitaItinerante != null){
			sql.append(", ?");
		}
		
		
		if (flagValidato){
			sql.append(", ?");
		}
		

		sql.append(", ?, ?");

		sql.append(")");

		int i = 0;
		PreparedStatement pst = db.prepareStatement(sql.toString());

		if (idAnimale > 0) {
			pst.setInt(++i, idAnimale);
		}

		pst.setString(++i, microchip);

		pst.setString(++i, nome);
		pst.setTimestamp(++i, dataNascita);
		pst.setBoolean(++i, flagDataNascitaPresunta);
		pst.setString(++i, sesso);
		pst.setInt(++i, idSpecie);
		pst.setInt(++i, idRazza);
		pst.setInt(++i, idTipoMantello);
		pst.setInt(++i, idProprietario);
		pst.setInt(++i, idAslRiferimento);
		pst.setTimestamp(++i, dataRegistrazione);
		pst.setInt(++i, idUtenteInserimento);
		pst.setInt(++i, idUtenteInserimento); // UTENTE MODIFICA = UTENTE
												// INSERIMENTO
		pst.setBoolean(++i, flagContributoRegionale);
		pst.setBoolean(++i, flagSterilizzazione);

		pst.setInt(++i, idDetentore);
		pst.setBoolean(++i, flagDetenutoInCanileDopoRitrovamento);
		pst.setBoolean(++i, flagUltimaOperazioneFuoriDominioAsl);
		pst.setInt(++i, idAslUltimaOperazioneFuoriDominioAsl);
		pst.setInt(++i, idRegistrazioneUltimaOperazioneFuoriDominioAsl);
		pst.setInt(++i, idDetentoreUltimaOperazioneFuoriDominioAsl);
		pst.setInt(++i, idStatoUltimaOperazioneFuoriDominioAsl);
		pst.setInt(++i, idTipologiaUltimaOperazioneFuoriDominioAsl);

		if (segniParticolari != null) {
			pst.setString(++i, segniParticolari);
		}

		if (note != null) {
			pst.setString(++i, note);
		}

		if (dataSterilizzazione != null) {
			pst.setTimestamp(++i, dataSterilizzazione);
		}

		if (dataRilascioPassaporto != null) {
			pst.setTimestamp(++i, dataRilascioPassaporto);
		}
		if (numeroPassaporto != null) {
			pst.setString(++i, numeroPassaporto);
		}
		if (tatuaggio != null) {
			pst.setString(++i, tatuaggio);
		}

		if (dataTatuaggio != null) {
			pst.setTimestamp(++i, dataTatuaggio);
		}

		if (stato > 0) {
			pst.setInt(++i, stato);
		}

		if (idPartitaCircuitoCommerciale > 0) {
			pst.setInt(++i, idPartitaCircuitoCommerciale);
		}

		if (flagPresenzaEsitoControlloDocumentale) {
			pst.setBoolean(++i, flagPresenzaEsitoControlloDocumentale);
		}
		if (flagPresenzaEsitoControlloFisico) {
			pst.setBoolean(++i, flagPresenzaEsitoControlloFisico);
		}
		if (flagPresenzaEsitoControlloLaboratorio) {
			pst.setBoolean(++i, flagPresenzaEsitoControlloLaboratorio);
		}
		if (flagPresenzaEsitoControlloIdentita) {
			pst.setBoolean(++i, flagPresenzaEsitoControlloIdentita);
		}

		if (idEsitoControlloDocumentale > 0) {
			pst.setInt(++i, idEsitoControlloDocumentale);
		}
		if (idEsitoControlloFisico > 0) {
			pst.setInt(++i, idEsitoControlloFisico);
		}
		if (idEsitoControlloLaboratorio > 0) {
			pst.setInt(++i, idEsitoControlloLaboratorio);
		}
		if (idEsitoControlloIdentita > 0) {
			pst.setInt(++i, idEsitoControlloIdentita);
		}

		if (dataEsitoControlloDocumentale != null) {
			pst.setTimestamp(++i, dataEsitoControlloDocumentale);
		}
		if (dataEsitoControlloFisico != null) {
			pst.setTimestamp(++i, dataEsitoControlloFisico);
		}
		if (dataEsitoControlloLaboratorio != null) {
			pst.setTimestamp(++i, dataEsitoControlloLaboratorio);
		}
		if (dataEsitoControlloIdentita != null) {
			pst.setTimestamp(++i, dataEsitoControlloIdentita);
		}

		if (idProprietarioUltimoTrasferimentoFRegione > 0) {
			pst.setInt(++i, idProprietarioUltimoTrasferimentoFRegione);
		}

		if (idProprietarioUltimoTrasferimentoFStato > 0) {
			pst.setInt(++i, idProprietarioUltimoTrasferimentoFStato);
		}

		if (flagCircuitoCommerciale) {
			pst.setBoolean(++i, flagCircuitoCommerciale);
		}

		if (idVeterinarioMicrochip > 0) {
			pst.setInt(++i, idVeterinarioMicrochip);
		}

		if (dataMicrochip != null) {
			pst.setTimestamp(++i, dataMicrochip);
		}

		if (noteInternalUseOnly != null && !("").equals(noteInternalUseOnly)) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"dd/MM/yyyy hh:mm:ss");
			pst.setString(++i,
					noteInternalUseOnly + " [" + dateFormat.format(new Date())
							+ "] ");

		}

		if (idTaglia > 0) {
			pst.setInt(++i, idTaglia);
		}

		if (dataVaccino != null) {
			pst.setTimestamp(++i, dataVaccino);
		}
		if (numeroLottoVaccino != null) {
			pst.setString(++i, numeroLottoVaccino);
		}

		if (nomeVaccino != null) {
			pst.setString(++i, nomeVaccino);
		}

		if (produttoreVaccino != null) {
			pst.setString(++i, produttoreVaccino);
		}

		if (dataScadenzaVaccino != null) {
			pst.setTimestamp(++i, dataScadenzaVaccino);
		}
		
		
		if (flagAttivitaItinerante){
			pst.setBoolean(++i, flagAttivitaItinerante);
		}
		
		if (idComuneAttivitaItinerante > 0){
			pst.setInt(++i, idComuneAttivitaItinerante);
		}
		
		if (luogoAttivitaItinerante != null && !("").equals(luogoAttivitaItinerante)){
			pst.setString(++i, luogoAttivitaItinerante);
		}
		
		if (dataAttivitaItinerante != null){
			pst.setTimestamp(++i, dataAttivitaItinerante);
		}
		
		
		if (flagValidato){
			pst.setBoolean(++i, flagValidato);
		}

		pst.setInt(++i, origineInserimento);
		
		pst.setBoolean(++i, flagMancataOrigine);

		pst.execute();
		pst.close();

		this.idAnimale = DatabaseUtils.getCurrVal(db, "animale_id_seq",
				idAnimale);

		return true;

	}

	public Animale(Connection db, int idAnimale) throws SQLException {
		if (idAnimale == -1) {
			throw new SQLException("Invalid Account");
		}

		PreparedStatement pst = db
				.prepareStatement("Select *, date_part('year',  age(data_nascita)) >=1  and date_part('year',  age(data_nascita)) <=8 as eta_rientrante_piano_leishmania  from animale where id = ?");
		pst.setInt(1, idAnimale);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) {
			this.buildRecord(rs);
			if (rs.getInt("id_proprietario") > 0) {
				proprietario = new Operatore();
				proprietario.queryRecordOperatorebyIdLineaProduttiva(db,
						rs.getInt("id_proprietario"));
			}

			if (rs.getInt("id_detentore") > 0) {
				detentore = new Operatore();
				detentore.queryRecordOperatorebyIdLineaProduttiva(db,
						rs.getInt("id_detentore"));
			}
			
			
			this.settaProprietarioOrigine(db,idAnimale);
			
			

			// if (rs.getInt("id_specie") == 1)//cane
			// {
			// //costruisco lista esiti
			//
			// esitiLeish.setIdAnimale(rs.getInt("id"));
			// esitiLeish.buildList(db);
			// }

			this.setProprietario(proprietario);
			this.setDetentore(detentore);

		}

//		if (idAnimale == -1) {
//			throw new SQLException(Constants.NOT_FOUND_ERROR);
//		}

		rs.close();
		pst.close();
	}

	public void settaNuovoProprietarioOrigine(Connection db, int idPr) throws SQLException {
		// TODO Auto-generated method stub
		EventoRegistrazioneBDU reg = EventoRegistrazioneBDU.getEventoRegistrazione(db,	this.idAnimale);
		//if(reg.getIdProprietarioProvenienza()>-1){
			Operatore soggettoProvenienzaAdded = new Operatore();
			soggettoProvenienzaAdded.queryRecordOperatorebyIdLineaProduttiva(db, idPr);
			this.setProprietarioProvenienza(soggettoProvenienzaAdded);
		//}
	}

	
	public void settaProprietarioOrigine(Connection db, int idAnimale) throws SQLException {
		// TODO Auto-generated method stub
		EventoRegistrazioneBDU reg = EventoRegistrazioneBDU.getEventoRegistrazione(db,	idAnimale);
		if(reg.getIdProprietarioProvenienza()>-1){
			Operatore soggettoProvenienzaAdded = new Operatore();
			soggettoProvenienzaAdded.queryRecordOperatorebyIdLineaProduttiva(db, reg.getIdProprietarioProvenienza());
			this.setProprietarioProvenienza(soggettoProvenienzaAdded);
		}
	}

	public Animale(Connection db, String microchip) throws SQLException {
		if (idAnimale == -1) {
			throw new SQLException("Invalid Account");
		}

		PreparedStatement pst = db
				.prepareStatement("Select * from animale where (microchip = ? or tatuaggio = ?) and data_cancellazione is null");
		pst.setString(1, microchip);
		pst.setString(2, microchip);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) {
			this.buildRecord(rs);
			if (rs.getInt("id_proprietario") > 0) {
				proprietario = new Operatore();
				proprietario.queryRecordOperatorebyIdLineaProduttiva(db,
						rs.getInt("id_proprietario"));
			}

			if (rs.getInt("id_detentore") > 0) {
				detentore = new Operatore();
				detentore.queryRecordOperatorebyIdLineaProduttiva(db,
						rs.getInt("id_detentore"));
			}

			// if (rs.getInt("id_specie") == 1)//cane
			// {
			// //costruisco lista esiti
			//
			// esitiLeish.setIdAnimale(rs.getInt("id"));
			// esitiLeish.buildList(db);
			// }

			this.setProprietario(proprietario);
			this.setDetentore(detentore);

		}

		if (idAnimale == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		rs.close();
		pst.close();
	}
	
	public Animale(Connection db, String microchip, String tatuaggio) throws SQLException 
	{
		PreparedStatement pst = db.prepareStatement("Select * from animale where (microchip = ? or tatuaggio = ? ) and trashed_date is null and data_cancellazione is null");
		pst.setString(1, microchip);
		pst.setString(2, tatuaggio);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) 
		{
			this.buildRecord(rs);
			if (rs.getInt("id_proprietario") > 0) 
			{
				proprietario = new Operatore();
				proprietario.queryRecordOperatorebyIdLineaProduttiva(db,rs.getInt("id_proprietario"));
			}

			if (rs.getInt("id_detentore") > 0) 
			{
				detentore = new Operatore();
				detentore.queryRecordOperatorebyIdLineaProduttiva(db,rs.getInt("id_detentore"));
			}

			this.setProprietario(proprietario);
			this.setDetentore(detentore);

		}

		rs.close();
		pst.close();
	}

	public Animale(ResultSet rs) throws SQLException {
		this.buildRecord(rs);
	}

	private void buildRecord(ResultSet rs) throws SQLException {

		this.idAnimale = rs.getInt("id");
		this.nome = rs.getString("nome");
		this.idAslRiferimento = rs.getInt("id_asl_riferimento");
		this.dataNascita = rs.getTimestamp("data_nascita");
		this.dataRegistrazione = rs.getTimestamp("data_registrazione");
		this.microchip = rs.getString("microchip");
		this.idRazza = rs.getInt("id_razza");
		this.idTipoMantello = rs.getInt("id_tipo_mantello");
		this.idSpecie = rs.getInt("id_specie");
		this.sesso = (rs.getString("sesso"));
		this.idUtenteInserimento = rs.getInt("utente_inserimento");
		this.dataInserimento = rs.getTimestamp("data_inserimento");
		this.idUtenteModifica = rs.getInt("utente_modifica");
		this.dataModifica = rs.getTimestamp("data_modifica");
		this.idUtenteCancellazione = rs.getInt("utente_cancellazione");
		this.dataCancellazione = rs.getTimestamp("data_cancellazione");
		this.flagContributoRegionale = rs
				.getBoolean("flag_contributo_regionale");
		this.tatuaggio = rs.getString("tatuaggio");
		this.dataTatuaggio = rs.getTimestamp("data_tatuaggio");
		this.flagDecesso = rs.getBoolean("flag_decesso");
		try {
			this.flagFurto = rs.getBoolean("flag_furto");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}

		
		this.flagDataNascitaPresunta = rs
				.getBoolean("flag_data_nascita_presunta");
		this.flagSmarrimento = rs.getBoolean("flag_smarrimento");
		this.flagSterilizzazione = rs.getBoolean("flag_sterilizzazione");
		this.dataRilascioPassaporto = rs
				.getTimestamp("data_rilascio_passaporto");
		if (rs.getString("numero_passaporto") != null)
			this.numeroPassaporto = rs.getString("numero_passaporto").trim();
		else
			this.numeroPassaporto = rs.getString("numero_passaporto");
		try {
			rs.findColumn("data_scadenza_passaporto");
			this.dataScadenzaPassaporto = rs.getTimestamp("data_scadenza_passaporto");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		this.segniParticolari = rs.getString("segni_particolari");
		this.note = rs.getString("note");
		this.stato = rs.getInt("stato");
		this.idProprietario = rs.getInt("id_proprietario");
		this.idDetentore = rs.getInt("id_detentore");
		this.idRegione = rs.getInt("id_regione");
		this.idContinente = rs.getInt("id_continente");
		// this.idComuneCattura = rs.getInt("id_comune_cattura");
		// this.flagCatturato = rs.getBoolean("flag_catturato");
		this.flagVincoloCommerciale = rs.getBoolean("flag_vincolato");

		// this.numeroPassaporto = rs.getString("passaporto_numero");
		// this.dataRilascioPassaporto =
		// rs.getTimestamp("passaporto_data_rilascio");

		this.dataVaccino = rs.getTimestamp("vaccino_data");
		this.numeroLottoVaccino = rs.getString("vaccino_numero_lotto");
		this.nomeVaccino = rs.getString("vaccino_nome");
		this.produttoreVaccino = rs.getString("vaccino_produttore");
		this.dataScadenzaVaccino = rs.getTimestamp("vaccino_data_scadenza");
		this.dataSterilizzazione = rs.getTimestamp("data_sterilizzazione");
		this.idPraticaContributi = rs.getInt("id_pratica_contributi");
		this.idProprietarioUltimoTrasferimentoFRegione = rs
				.getInt("id_proprietario_ultimo_trasferimento_a_regione");
		this.idProprietarioUltimoTrasferimentoFStato = rs
				.getInt("id_proprietario_ultimo_trasferimento_a_stato");
		this.idDetentoreUltimoTrasferimentoFStato = rs
				.getInt("id_detentore_ultimo_trasferimento_a_stato");
		this.idDetentoreUltimoTrasferimentoFRegione = rs
				.getInt("id_detentore_ultimo_trasferimento_a_regione");
		this.flagCircuitoCommerciale = rs
				.getBoolean("flag_circuito_commerciale");
		this.idVeterinarioMicrochip = rs.getInt("id_veterinario_microchip");
		this.dataMicrochip = rs.getTimestamp("data_microchip");

		try {
			rs.findColumn("note_internal_use_only");
			this.noteInternalUseOnly = rs.getString("note_internal_use_only");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}

		try {
			rs.findColumn("situazione_esito_leish");
			this.esitoLeishAnnoCorrente = rs
					.getBoolean("situazione_esito_leish");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}

		// Controlli commerciali

		this.flagPresenzaEsitoControlloDocumentale = rs
				.getBoolean("flag_controllo_documentale");
		this.idEsitoControlloDocumentale = rs
				.getInt("id_esito_controllo_documentale");
		this.dataEsitoControlloDocumentale = rs
				.getTimestamp("data_esito_controllo_documentale");

		this.flagPresenzaEsitoControlloFisico = rs
				.getBoolean("flag_controllo_fisico");
		this.idEsitoControlloFisico = rs.getInt("id_esito_controllo_fisico");
		this.dataEsitoControlloFisico = rs
				.getTimestamp("data_esito_controllo_fisico");

		this.flagPresenzaEsitoControlloLaboratorio = rs
				.getBoolean("flag_controllo_laboratorio");
		this.idEsitoControlloLaboratorio = rs
				.getInt("id_esito_controllo_laboratorio");
		this.dataEsitoControlloLaboratorio = rs
				.getTimestamp("data_esito_controllo_laboratorio");

		this.flagPresenzaEsitoControlloIdentita = rs
				.getBoolean("flag_controllo_identita");
		this.idEsitoControlloIdentita = rs
				.getInt("id_esito_controllo_identita");
		this.dataEsitoControlloIdentita = rs
				.getTimestamp("data_esito_controllo_identita");
		this.idPartitaCircuitoCommerciale = rs
				.getInt("id_partita_circuito_commerciale");
		this.idTaglia = rs.getInt("id_taglia");

		try {
			this.setFlagDetenutoInCanileDopoRitrovamento(rs
					.getBoolean("flag_detenuto_in_canile_dopo_ritrovamento"));
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}

		try {
			rs.findColumn("ragione_sociale_prop");
			this.nomeCognomeProprietario = rs.getString("ragione_sociale_prop");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}

		try {
			rs.findColumn("id_comune_proprietario");
			this.idComuneProprietario = rs.getInt("id_comune_proprietario");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}

		try {
			rs.findColumn("ragione_sociale_det");
			this.nomeCognomeDetentore = rs.getString("ragione_sociale_det");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}

		try {
			rs.findColumn("origine_registrazione");
			this.origineInserimento = rs.getInt("origine_registrazione");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}

		try {
			rs.findColumn("flag_ultima_operazione_eseguita_fuori_dominio_asl");
			this.flagUltimaOperazioneFuoriDominioAsl = rs
					.getBoolean("flag_ultima_operazione_eseguita_fuori_dominio_asl");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}

		try {
			rs.findColumn("id_asl_fuori_dominio_ultima_registrazione");
			this.idAslUltimaOperazioneFuoriDominioAsl = rs
					.getInt("id_asl_fuori_dominio_ultima_registrazione");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}

		try {
			rs.findColumn("id_evento_ultima_registrazione_fuori_dominio");
			this.idRegistrazioneUltimaOperazioneFuoriDominioAsl = rs
					.getInt("id_evento_ultima_registrazione_fuori_dominio");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}

		try {
			rs.findColumn("id_detentore_ultima_registrazione_fuori_dominio");
			this.idDetentoreUltimaOperazioneFuoriDominioAsl = rs
					.getInt("id_detentore_ultima_registrazione_fuori_dominio");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}

		try {
			rs.findColumn("id_stato_ultima_registrazione_fuori_dominio");
			this.idStatoUltimaOperazioneFuoriDominioAsl = rs
					.getInt("id_stato_ultima_registrazione_fuori_dominio");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		try {
			rs.findColumn("id_tipologia_registrazione_fuori_dominio_asl");
			this.idTipologiaUltimaOperazioneFuoriDominioAsl = rs
					.getInt("id_tipologia_registrazione_fuori_dominio_asl");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		
		try {
			rs.findColumn("flag_bloccato");
			this.flagBloccato = rs
					.getBoolean("flag_bloccato");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("id_ultima_registrazione_blocco");
			this.idUltimaRegistrazioneBlocco = rs
					.getInt("id_ultima_registrazione_blocco");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		
		try {
			rs.findColumn("id_comune_attivita_itinerante");
			this.idComuneAttivitaItinerante = rs.getInt("id_comune_attivita_itinerante");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("flag_attivita_itinerante");
			this.flagAttivitaItinerante = rs.getBoolean("flag_attivita_itinerante");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("data_attivita_itinerante");
			this.dataAttivitaItinerante = rs.getTimestamp("data_attivita_itinerante");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		try {
			rs.findColumn("luogo_attivita_itinerante");
			this.luogoAttivitaItinerante = rs.getString("luogo_attivita_itinerante");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		
		
		try {
			rs.findColumn("flag_validato");
			this.flagValidato = rs
					.getBoolean("flag_validato");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
		

		try {
			rs.findColumn("flag_mancata_origine");
			this.flagMancataOrigine = rs
					.getBoolean("flag_mancata_origine");
		} catch (SQLException sqlex) {
			// System.out.println("not found");
		}
	
		

	}

	public void updateStato(Connection con) throws Exception {
		
		PreparedStatement pst;
		boolean occupazione = true;
		try {
			
			
			Animale oldAnimale = new Animale(con, this.getIdAnimale());
			
			/*if (oldAnimale.getIdProprietario() != this.getIdProprietario() && this.getIdProprietario() > 0 && oldAnimale.getIdProprietario() > 0) {
				occupazione =  Stabilimento.checkOccupazione(this.getIdProprietario(), this.getIdTaglia());
			}
			if (occupazione){*/
			if(ApplicationProperties.getProperty("blocco_canili_occupati").equals("true"))	{
				if (oldAnimale.getIdDetentore() != this.getIdDetentore() && this.getIdDetentore() > 0 && oldAnimale.getIdDetentore() > 0){
					String dataNascitaString = null;
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					if(this.getDataNascita()!=null)
						dataNascitaString = sdf.format(this.getDataNascita());
					occupazione =  Stabilimento.checkOccupazione(this.getIdDetentore(), this.getIdTaglia(), dataNascitaString);
					}	
				/*} */
				
				if (!occupazione){
					throw new CanilePienoException("canile pieno");
				}
			}
			else
				occupazione = false;
				
			pst = con
					.prepareStatement("Update animale set stato = ?, tatuaggio = ?, data_tatuaggio = ?,  flag_decesso = ?, flag_sterilizzazione = ?, flag_contributo_regionale = ?, flag_smarrimento = ?, flag_furto = ?, "
							+ "id_proprietario = ?, data_rilascio_passaporto = ?, numero_passaporto = ?, data_scadenza_passaporto = ?,  id_asl_riferimento = ?, id_regione = ?, id_continente = ?, "
							+ " "
							+ "id_pratica_contributi = ?, flag_vincolato = ?, data_sterilizzazione = ?, id_proprietario_ultimo_trasferimento_a_regione = ?, id_proprietario_ultimo_trasferimento_a_stato = ?, "
							+ "id_detentore_ultimo_trasferimento_a_stato = ?, id_detentore_ultimo_trasferimento_a_regione = ?,  flag_circuito_commerciale = ?, id_detentore = ?, id_taglia = ?, "
							+ "flag_detenuto_in_canile_dopo_ritrovamento = ?, vaccino_data = ?, "
							+ "flag_ultima_operazione_eseguita_fuori_dominio_asl = ?,   id_asl_fuori_dominio_ultima_registrazione = ?, "
							+ "id_evento_ultima_registrazione_fuori_dominio = ?, id_detentore_ultima_registrazione_fuori_dominio = ?, id_stato_ultima_registrazione_fuori_dominio = ?, id_tipologia_registrazione_fuori_dominio_asl = ?, "
							+ "flag_bloccato = ?, id_ultima_registrazione_blocco = ? where id = "
							+ this.getIdAnimale());

			int i = 0;
			pst.setInt(++i, stato);
			pst.setString(++i, tatuaggio);
			pst.setTimestamp(++i, dataTatuaggio);
			// pst.setInt(++i, idAslRiferimento);
			pst.setBoolean(++i, flagDecesso);
			pst.setBoolean(++i, flagSterilizzazione);
			pst.setBoolean(++i, flagContributoRegionale);
			pst.setBoolean(++i, flagSmarrimento);
			pst.setBoolean(++i, flagFurto);
			pst.setInt(++i, idProprietario);
			// pst.setInt(++i, idDetentore);
			pst.setTimestamp(++i, dataRilascioPassaporto);
			pst.setString(++i, numeroPassaporto);
			pst.setTimestamp(++i, dataScadenzaPassaporto);
			pst.setInt(++i, idAslRiferimento);
			pst.setInt(++i, idRegione);
			pst.setInt(++i, idContinente);
			// pst.setBoolean(++i, flagCatturato);
			// pst.setInt(++i, idComuneCattura);
			pst.setInt(++i, idPraticaContributi);
			pst.setBoolean(++i, flagVincoloCommerciale);
			pst.setTimestamp(++i, dataSterilizzazione);
			pst.setInt(++i, idProprietarioUltimoTrasferimentoFRegione);
			pst.setInt(++i, idProprietarioUltimoTrasferimentoFStato);
			pst.setInt(++i, idDetentoreUltimoTrasferimentoFStato);
			pst.setInt(++i, idDetentoreUltimoTrasferimentoFRegione);
			pst.setBoolean(++i, flagCircuitoCommerciale);
			pst.setInt(++i, idDetentore);
			pst.setInt(++i, idTaglia);
			pst.setBoolean(++i, flagDetenutoInCanileDopoRitrovamento);
			pst.setTimestamp(++i, dataVaccino);
			pst.setBoolean(++i, flagUltimaOperazioneFuoriDominioAsl);
			pst.setInt(++i, idAslUltimaOperazioneFuoriDominioAsl);
			pst.setInt(++i, idRegistrazioneUltimaOperazioneFuoriDominioAsl);
			pst.setInt(++i, idDetentoreUltimaOperazioneFuoriDominioAsl);
			pst.setInt(++i, idStatoUltimaOperazioneFuoriDominioAsl);
			pst.setInt(++i, idTipologiaUltimaOperazioneFuoriDominioAsl);
			pst.setBoolean(++i, flagBloccato);
			pst.setInt(++i, idUltimaRegistrazioneBlocco);

			int resultCount = pst.executeUpdate();
			pst.close();
			
		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		}
	}
	
	
public void updateFlag(Connection con) throws Exception 
{
		
		PreparedStatement pst;
		try 
		{
			pst = con.prepareStatement("Update animale set flag_smarrimento = ?, flag_furto = ? where id = " + this.getIdAnimale());

			int i = 0;
			pst.setBoolean(++i, flagSmarrimento);
			pst.setBoolean(++i, flagFurto);
			pst.executeUpdate();
			pst.close();
			
		} 
		catch (SQLException e) 
		{
			throw new SQLException(e.getMessage());
		}
	}

public void updateFlagDecessoDufficio(Connection con,UserBean user) throws Exception 
{
		
		PreparedStatement pst;
		try 
		{
			
			
			
			pst = con.prepareStatement("update animale set  utente_modifica = ?, note_internal_use_only = concat(note_internal_use_only,' ***** Decesso d''ufficio generato da utente con id ', ?, ' e username ', ?  ), data_modifica = current_timestamp,  flag_smarrimento = ?, flag_furto = ?, flag_decesso = ?, stato = ? where id = ?");

			pst.setInt(1, user.getUserId());
			pst.setInt(2, user.getUserId());
			pst.setString(3, user.getUsername());
			pst.setBoolean(4, flagSmarrimento);
			pst.setBoolean(5, flagFurto);
			pst.setBoolean(6, flagDecesso);
			pst.setInt(7, stato);
			pst.setInt(8, this.getIdAnimale());
			pst.executeUpdate();
			pst.close();
			
		} 
		catch (SQLException e) 
		{
			throw new SQLException(e.getMessage());
		}
	}

public void updateFlagAggressivo(Connection con,boolean flag) throws Exception 
{
		
		PreparedStatement pst;
		try 
		{
			pst = con.prepareStatement("Update cane set flag_aggressivo = ? where id_animale = " + this.getIdAnimale());

			pst.setBoolean(1, flag);
			int i = 0;
			pst.executeUpdate();
			pst.close();
			
		} 
		catch (SQLException e) 
		{
			throw new SQLException(e.getMessage());
		}
	}
	
public void updateStatoSenzaControlloCanilePieno(Connection con) throws Exception {
		
		PreparedStatement pst;
		try {
			
			pst = con
					.prepareStatement("Update animale set id_detentore = ?, stato = ? where id = "
							+ this.getIdAnimale());

			int i = 0;
			pst.setInt(++i, idDetentore);
			pst.setInt(++i, stato);

			int resultCount = pst.executeUpdate();
			pst.close();
			
		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		}
	}
	
public void aggiornaStatoAnimaleSenzaControlloCanilePieno(Connection con) throws Exception {
		
		PreparedStatement pst;
		boolean occupazione = true;
		try {
			
			
			Animale oldAnimale = new Animale(con, this.getIdAnimale());
			
			pst = con.prepareStatement("Update animale set stato = ?, tatuaggio = ?, data_tatuaggio = ?,  flag_decesso = ?, flag_sterilizzazione = ?, flag_contributo_regionale = ?, flag_smarrimento = ?, flag_furto = ?, "
							+ "id_proprietario = ?, data_rilascio_passaporto = ?, numero_passaporto = ?, data_scadenza_passaporto = ?,  id_asl_riferimento = ?, id_regione = ?, id_continente = ?, "
							+ " "
							+ "id_pratica_contributi = ?, flag_vincolato = ?, data_sterilizzazione = ?, id_proprietario_ultimo_trasferimento_a_regione = ?, id_proprietario_ultimo_trasferimento_a_stato = ?, "
							+ "id_detentore_ultimo_trasferimento_a_stato = ?, id_detentore_ultimo_trasferimento_a_regione = ?,  flag_circuito_commerciale = ?, id_detentore = ?, id_taglia = ?, "
							+ "flag_detenuto_in_canile_dopo_ritrovamento = ?, vaccino_data = ?, "
							+ "flag_ultima_operazione_eseguita_fuori_dominio_asl = ?,   id_asl_fuori_dominio_ultima_registrazione = ?, "
							+ "id_evento_ultima_registrazione_fuori_dominio = ?, id_detentore_ultima_registrazione_fuori_dominio = ?, id_stato_ultima_registrazione_fuori_dominio = ?, id_tipologia_registrazione_fuori_dominio_asl = ?, "
							+ "flag_bloccato = ?, id_ultima_registrazione_blocco = ? where id = "
							+ this.getIdAnimale());

			int i = 0;
			pst.setInt(++i, stato);
			pst.setString(++i, tatuaggio);
			pst.setTimestamp(++i, dataTatuaggio);
			// pst.setInt(++i, idAslRiferimento);
			pst.setBoolean(++i, flagDecesso);
			pst.setBoolean(++i, flagSterilizzazione);
			pst.setBoolean(++i, flagContributoRegionale);
			pst.setBoolean(++i, flagSmarrimento);
			pst.setBoolean(++i, flagFurto);
			pst.setInt(++i, idProprietario);
			// pst.setInt(++i, idDetentore);
			pst.setTimestamp(++i, dataRilascioPassaporto);
			pst.setString(++i, numeroPassaporto);
			pst.setTimestamp(++i, dataScadenzaPassaporto);
			pst.setInt(++i, idAslRiferimento);
			pst.setInt(++i, idRegione);
			pst.setInt(++i, idContinente);
			// pst.setBoolean(++i, flagCatturato);
			// pst.setInt(++i, idComuneCattura);
			pst.setInt(++i, idPraticaContributi);
			pst.setBoolean(++i, flagVincoloCommerciale);
			pst.setTimestamp(++i, dataSterilizzazione);
			pst.setInt(++i, idProprietarioUltimoTrasferimentoFRegione);
			pst.setInt(++i, idProprietarioUltimoTrasferimentoFStato);
			pst.setInt(++i, idDetentoreUltimoTrasferimentoFStato);
			pst.setInt(++i, idDetentoreUltimoTrasferimentoFRegione);
			pst.setBoolean(++i, flagCircuitoCommerciale);
			pst.setInt(++i, idDetentore);
			pst.setInt(++i, idTaglia);
			pst.setBoolean(++i, flagDetenutoInCanileDopoRitrovamento);
			pst.setTimestamp(++i, dataVaccino);
			pst.setBoolean(++i, flagUltimaOperazioneFuoriDominioAsl);
			pst.setInt(++i, idAslUltimaOperazioneFuoriDominioAsl);
			pst.setInt(++i, idRegistrazioneUltimaOperazioneFuoriDominioAsl);
			pst.setInt(++i, idDetentoreUltimaOperazioneFuoriDominioAsl);
			pst.setInt(++i, idStatoUltimaOperazioneFuoriDominioAsl);
			pst.setInt(++i, idTipologiaUltimaOperazioneFuoriDominioAsl);
			pst.setBoolean(++i, flagBloccato);
			pst.setInt(++i, idUltimaRegistrazioneBlocco);

			int resultCount = pst.executeUpdate();
			pst.close();
			
		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		}
	}
	
	
public void updateTaglia(Connection con, int idTaglia, String motivazione) throws Exception {
		
		PreparedStatement pst;
		pst = con.prepareStatement("Update animale set id_taglia = ?, data_modifica=now(), note_internal_use_only = concat(note_internal_use_only, ?, ' - vecchia taglia: ', ?) where id = ? ");

		int i = 0;
		pst.setInt(++i, idTaglia);
		pst.setString(++i, motivazione);
		pst.setInt(++i, this.idTaglia);
		pst.setInt(++i, idAnimale);
		

		int resultCount = pst.executeUpdate();
		pst.close();
		
	}
	
	public boolean checkAslAnimaleUtenteOrRoleHd(UserBean user) {
		return (this.getIdAslRiferimento() == user.getSiteId()
				|| user.getRoleId() == new Integer(
						ApplicationProperties.getProperty("ID_RUOLO_HD1")) || user
					.getRoleId() == new Integer(
				ApplicationProperties.getProperty("ID_RUOLO_HD2")));
	}

	public boolean checkAslAnimaleUtenteOFuoriRegione(UserBean user) {

		if (!this.getProprietario().checkModificaResidenzaStabilimento()) {
			return true;
			// return (this.getIdAslRiferimento() ==
			// Constants.ID_ASL_FUORI_REGIONE || this
			// .getIdAslRiferimento() == user.getSiteId());
			// MODIFICATO CHE CONTROLLA SOLO CAMBIO RESIDENZA IN CORSO DOPO LA
			// POSSIBILITA'
			// DI REGISRAZIONI PER ASL DIVERSA DA ASL UTENTE
		} else
			return false;
	}

	public boolean checkIdUtenteAnimale(UserBean user) {
		return (this.getIdUtenteInserimento() == user.getUserId());
	}

	public boolean checkVetModifica(UserBean user) {
		if (this.checkIdUtenteAnimale(user)) {
			int interv = 9000; // Default 15 minuti
			Properties prop = anagrafeUtilis.load("anagrafe.properties");
			String intervallo = (prop != null) ? prop
					.getProperty("veterinari_intervallo_modifica") : null;
			if (intervallo != null && !("").equals(intervallo)) {
				interv = new Integer(intervallo).intValue();
			}
			long time = System.currentTimeMillis();
			long entered = this.getDataInserimento().getTime();

			long ris = time - entered;
			if (ris > interv) {
				return false;
			} else {
				return true;
			}

		} else {
			return false;
		}

	}

	public static boolean checkContributo(Connection db, String microchip)
			throws SQLException {
		int rit = -1;
		PreparedStatement pst = null;
		ResultSet rs = null;
		pst = db.prepareStatement("select id from contributi_lista_univocita where microchip = ?");
		pst.setString(1, microchip.trim());

		rs = pst.executeQuery();

		if (rs.next()) {
			rit = rs.getInt("id");
		}
		rs.close();
		pst.close();

		if (rit == -1) {

			return true;
		} else {

			return false;
		}
	}

	public void setContributoPagato(Connection db, int idPratica)
			throws SQLException {
		int rit = -1;
		PreparedStatement pst = null;
		ResultSet rs = null;
		pst = db.prepareStatement("select id from contributi_lista_univocita where microchip = ? and id_richiesta_contributi= ? ");
		pst.setString(1, this.getMicrochip().trim());
		pst.setInt(2, idPratica);

		rs = pst.executeQuery();

		if (rs.next()) {
			this.contributoPagato = true;
		}
		rs.close();
		pst.close();

	}

	public void setContributoPagato(Connection db) throws SQLException {
		int rit = -1;
		PreparedStatement pst = null;
		ResultSet rs = null;
		pst = db.prepareStatement("select id from contributi_lista_univocita where microchip = ?");
		pst.setString(1, this.getMicrochip().trim());
		// pst.setInt(2, idPratica);

		rs = pst.executeQuery();

		if (rs.next()) {
			this.contributoPagato = true;
		}
		rs.close();
		pst.close();

	}

	public void updateControlliCommerciali(Connection db,
			EventoRegistrazioneEsitoControlliCommerciali esiti, boolean update) {

		this.flagPresenzaEsitoControlloDocumentale = esiti
				.isFlagPresenzaEsitoControlloDocumentale();
		this.flagPresenzaEsitoControlloFisico = esiti
				.isFlagPresenzaEsitoControlloFisico();
		this.flagPresenzaEsitoControlloIdentita = esiti
				.isFlagPresenzaEsitoControlloIdentita();
		this.flagPresenzaEsitoControlloLaboratorio = esiti
				.isFlagPresenzaEsitoControlloLaboratorio();

		this.dataEsitoControlloDocumentale = esiti
				.getDataEsitoControlloDocumentale();
		this.dataEsitoControlloFisico = esiti.getDataEsitoControlloFisico();
		this.dataEsitoControlloIdentita = esiti.getDataEsitoControlloIdentita();
		this.dataEsitoControlloLaboratorio = esiti
				.getDataEsitoControlloLaboratorio();

		this.idEsitoControlloDocumentale = esiti
				.getIdEsitoControlloDocumentale();
		this.idEsitoControlloFisico = esiti.getIdEsitoControlloFisico();
		this.idEsitoControlloIdentita = esiti.getIdEsitoControlloIdentita();
		this.idEsitoControlloLaboratorio = esiti
				.getIdEsitoControlloLaboratorio();

		try {
			PreparedStatement pst = db
					.prepareStatement("UPDATE animale "
							+ "SET flag_controllo_documentale=?, flag_controllo_fisico=?,"
							+ " flag_controllo_laboratorio=?, flag_controllo_identita=?, id_esito_controllo_documentale=?,"
							+ " id_esito_controllo_fisico=?, id_esito_controllo_identita=?, id_esito_controllo_laboratorio=?, "
							+ " data_esito_controllo_documentale=?, data_esito_controllo_fisico=?, "
							+ " data_esito_controllo_identita=?, data_esito_controllo_laboratorio=? where id = "
							+ this.getIdAnimale());
			int i = 0;

			pst.setBoolean(++i, flagPresenzaEsitoControlloDocumentale);
			pst.setBoolean(++i, flagPresenzaEsitoControlloFisico);
			pst.setBoolean(++i, flagPresenzaEsitoControlloLaboratorio);
			pst.setBoolean(++i, flagPresenzaEsitoControlloIdentita);

			pst.setInt(++i, idEsitoControlloDocumentale);
			pst.setInt(++i, idEsitoControlloFisico);
			pst.setInt(++i, idEsitoControlloIdentita);
			pst.setInt(++i, idEsitoControlloLaboratorio);

			pst.setTimestamp(++i, dataEsitoControlloDocumentale);
			pst.setTimestamp(++i, dataEsitoControlloFisico);
			pst.setTimestamp(++i, dataEsitoControlloIdentita);
			pst.setTimestamp(++i, dataEsitoControlloLaboratorio);
			pst.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	

	public int getIdDetentore() {
		return idDetentore;
	}

	public int update(Connection conn) throws SQLException {
		int result = -1;
		
		try {
			
			StringBuffer sql = new StringBuffer();

			sql.append("UPDATE animale "
					+ "SET nome=?, data_nascita=?, sesso=?, "
					+ "id_specie=?, id_razza=?, id_tipo_mantello=?, id_proprietario=?, "
					+ " id_regione_provenienza=?, "
					+ " note=?, segni_particolari=?, data_modifica=?, utente_modifica=?, "
					+ " flag_data_nascita_presunta=?, tatuaggio=?, data_tatuaggio = ?, id_taglia = ?, numero_passaporto=?, data_rilascio_passaporto=?, vaccino_data = ?, " +
					"vaccino_numero_lotto = ?, id_detentore = ?, id_asl_riferimento = ?");

			if (noteInternalUseOnly != null
					&& !("").equals(noteInternalUseOnly)) {
				sql.append(", note_internal_use_only = ?");
			}
			if (microchip != null && !("").equals(microchip)) {
				sql.append(", microchip = ?");
			}
			if (dataMicrochip != null && !("").equals(dataMicrochip)) {
				sql.append(", data_microchip = ?");
			}
			if (idVeterinarioMicrochip > -1) {
				sql.append(", id_veterinario_microchip = ?");
			}
			if (dataSterilizzazione != null
					&& !("").equals(dataSterilizzazione)) {
				sql.append(", data_sterilizzazione = ?");
			}

			sql.append("  where id = ?");

			PreparedStatement pst = conn.prepareStatement(sql.toString());

			int i = 0;
			pst.setString(++i, this.getNome());
			pst.setTimestamp(++i, this.getDataNascita());
			pst.setString(++i, this.getSesso());
			pst.setInt(++i, this.getIdSpecie());
			pst.setInt(++i, this.getIdRazza());
			pst.setInt(++i, this.getIdTipoMantello());
			pst.setInt(++i, this.getIdProprietario());
			pst.setInt(++i, this.getIdRegione());
			pst.setString(++i, this.getNote());
			pst.setString(++i, this.getSegniParticolari());
			pst.setTimestamp(++i, new java.sql.Timestamp(Calendar.getInstance()
					.getTime().getTime()));
			pst.setInt(++i, this.getIdUtenteModifica());
			pst.setBoolean(++i, this.isFlagDataNascitaPresunta());
			pst.setString(++i, this.getTatuaggio());
			pst.setTimestamp(++i, this.getDataTatuaggio());
			pst.setInt(++i, this.getIdTaglia());
			pst.setString(++i, this.getNumeroPassaporto());
			pst.setTimestamp(++i, this.getDataRilascioPassaporto());
			pst.setTimestamp(++i, this.dataVaccino);
			pst.setString(++i, this.numeroLottoVaccino);
			pst.setInt(++i, this.idDetentore);
			pst.setInt(++i, this.idAslRiferimento);

			if (noteInternalUseOnly != null
					&& !("").equals(noteInternalUseOnly)) {

				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"dd/MM/yyyy hh:mm:ss");
				pst.setString(
						++i,
						noteInternalUseOnly + " ["
								+ dateFormat.format(new Date()) + "] ");
			}
			if (microchip != null && !("").equals(microchip)) {
				pst.setString(++i, microchip);
			}
			if (dataMicrochip != null && !("").equals(dataMicrochip)) {
				pst.setTimestamp(++i, dataMicrochip);
			}
			if (idVeterinarioMicrochip > -1) {
				pst.setInt(++i, idVeterinarioMicrochip);
			}
			if (dataSterilizzazione != null
					&& !("").equals(dataSterilizzazione)) {
				pst.setTimestamp(++i, dataSterilizzazione);
			}
			pst.setInt(++i, this.getIdAnimale());
			result = pst.executeUpdate();

			pst.close();
			
		} catch (Exception e) {
			
			throw new SQLException(e.getMessage());

		}

		return result;
	}

	public ArrayList<CampoModificato> checkModifiche(Connection db,
			Animale animale) {

		Field[] campi = this.getClass().getSuperclass().getDeclaredFields();

		// System.out.println("padre");

		ArrayList<CampoModificato> nomiCampiModificati = new ArrayList<CampoModificato>();

		Method metodo = null;
		Method metodoDecodifica = null;

		for (int i = 0; i < campi.length; i++) {
			int k = 0;

			String nameToUpperCase = (campi[i].getName().substring(0, 1)
					.toUpperCase() + campi[i].getName().substring(1,
					campi[i].getName().length()));

		//	System.out.println("Nome in animale " + nameToUpperCase);
			if (!(nameToUpperCase.equals("Proprietario")
					|| nameToUpperCase.equals("DataInserimento")
					|| nameToUpperCase.equals("DataModifica")
					|| nameToUpperCase.equals("Nota2")
					|| nameToUpperCase.equals("IdUtenteInserimento")
					|| nameToUpperCase.equals("IdUtenteModifica")
					|| nameToUpperCase.equals("Detentore") || nameToUpperCase
						.equals("OrigineInserimento"))) {
				// (nameToUpperCase.equals("IdTipoMantello")){
				try {
					metodo = this.getClass().getSuperclass()
							.getMethod("get" + nameToUpperCase, null);
				} catch (NoSuchMethodException exc) {
					exc.printStackTrace();
				}

				if (metodo != null)
					try {
						if (((metodo.invoke(animale) != null && metodo
								.invoke(this) != null)
								&& !(metodo.invoke(animale).equals(metodo
										.invoke(this))) && this.checkNotEmpty(
								metodo, animale))
								|| (metodo.invoke(animale) == null && metodo
										.invoke(this) != null)
								|| (metodo.invoke(animale) != null && metodo
										.invoke(this) == null)
								&& this.checkNotEmpty(metodo, animale)) {
							CampoModificato campo = new CampoModificato();
							try {
								metodoDecodifica = null;
								Class[] params = new Class[1];

								Class c = java.sql.Connection.class;
								params[0] = c;
								metodoDecodifica = this
										.getClass()
										.getSuperclass()
										.getMethod(
												"getDecodifica"
														+ nameToUpperCase,
												params);
							} catch (NoSuchMethodException exc) {
//								System.out
//										.println(nameToUpperCase
//												+ ":(ANIMALE) non c'è bisogno di decodifica");
							}
							if (metodoDecodifica != null) {
								Object[] args = new Object[1];
								args[0] = db;

								campo.setValorePrecedenteStringa((String) metodoDecodifica
										.invoke(animale, args));
								campo.setValoreModificatoStringa((String) metodoDecodifica
										.invoke(this, args));
							} else {
								campo.setValorePrecedenteStringa(metodo.invoke(
										animale).toString());
								campo.setValoreModificatoStringa(metodo.invoke(
										this).toString());
							}
							campo.setNomeCampo(nameToUpperCase);
							campo.setValorePrecedente(metodo.invoke(animale)
									.toString());
							campo.setValoreModificato(metodo.invoke(this)
									.toString());
							nomiCampiModificati.add(campo);
							k++;
							if (System.getProperty("DEBUG") != null)
								System.out.println("Campo modificato:  "
										+ nameToUpperCase);
						}

					} catch (Exception ecc) {
						if (System.getProperty("DEBUG") != null)
							System.out.println("ecc ANIMALE  "
									+ nameToUpperCase + "   " + metodo + "   "
									+ metodoDecodifica + "   " + ecc);
					}
			}

		}
		return nomiCampiModificati;
	}

	public void saveFieldsDb(Connection db) {

		try {

			Field[] campiFiglio = this.getClass().getDeclaredFields();
			Field[] campi = this.getClass().getSuperclass().getDeclaredFields();

			for (int k = 0; k < campi.length; k++) {
			 PreparedStatement pst1 = 	db
						.prepareStatement("Select * from lista_campi_classi where nome_campo = ? ");
			 pst1.setString(1, campi[k].getName().substring(0, 1)
						.toUpperCase()
						+ campi[k].getName().substring(1,
								campi[k].getName().length()));
			 
			 ResultSet rs = pst1.executeQuery();
			 
			 if (!rs.next()){
				
				PreparedStatement pst = db
						.prepareStatement("Insert into lista_campi_classi (nome_campo, tipo_campo, nome_classe) values (?, ?, ?)");
				pst.setString(1, campi[k].getName().substring(0, 1)
						.toUpperCase()
						+ campi[k].getName().substring(1,
								campi[k].getName().length()));
				pst.setString(2, campi[k].getType().getSimpleName());

				pst.setString(3, this.getClass().getSuperclass().getName());
				pst.executeUpdate();
				pst.close();
			 }
			}

			for (int k = 0; k < campiFiglio.length; k++) {
				
				 PreparedStatement pst1 = 	db
							.prepareStatement("Select * from lista_campi_classi where nome_campo = ? ");
				 pst1.setString(1, campi[k].getName().substring(0, 1)
							.toUpperCase()
							+ campi[k].getName().substring(1,
									campi[k].getName().length()));
				 
				 ResultSet rs = pst1.executeQuery();
				 
				 if (!rs.next()){
				PreparedStatement pst = db
						.prepareStatement("Insert into lista_campi_classi (nome_campo, tipo_campo, nome_classe) values (?, ?, ?)");
				pst.setString(1, campiFiglio[k].getName().substring(0, 1)
						.toUpperCase()
						+ campiFiglio[k].getName().substring(1,
								campiFiglio[k].getName().length()));
				pst.setString(2, campiFiglio[k].getType().getSimpleName());
				pst.setString(3, this.getClass().getName());
				pst.executeUpdate();
				pst.close();
				 }
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// vede se un campo è -1 e l'altro 0, il che significa che sn vuoit
	public boolean checkNotEmpty(Method metod, Animale animale) {
		boolean ret = true;
		if (System.getProperty("DEBUG") != null)
			System.out.println(metod.getName());
		if (metod.getReturnType().isInstance(Integer.class)
				|| metod.getReturnType().equals(int.class)) {
			try {
				if ((((Integer) metod.invoke(this)) == -1 && ((Integer) metod
						.invoke(animale)) == 0)
						|| (((Integer) (metod.invoke(this)) == 0 && ((Integer) metod
								.invoke(animale) == -1)))) {
					ret = false;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		return ret;
	}

	public String getNomeSpecieAnimale() {
		String nome = "";
		switch (this.getIdSpecie()) {
		case Cane.idSpecie:
			return "Cane";
		case Gatto.idSpecie:
			return "Gatto";
		case Furetto.idSpecie:
			return "Furetto";
		}

		return "";
	}

	public static String verificaRicovero(String microchip,
			String dataRegistrazione) throws SQLException {

		//java.sql.Timestamp dataReg = DatabaseUtils
				//.parseDateToTimestamp(dataRegistrazione);

		String ret = "";
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pst = null;

		try {

			// String dbName = ApplicationProperties.getProperty("dbnameVam");
			// String username = ApplicationProperties
			// .getProperty("usernameDbVam");
			// String pwd = ApplicationProperties.getProperty("passwordDbVam");
			// String host =
			// InetAddress.getByName("hostDbVam").getHostAddress();
			//
			// conn = DbUtil.getConnection(dbName, username, pwd, host);

			conn = GestoreConnessioni.getConnectionVam("");

			pst = conn
					.prepareStatement("select * from public_functions.is_ricoverato_return_cc('"
							+ microchip + "','" + dataRegistrazione + "')");
			rs = pst.executeQuery();
			if (rs.next()) {
				ret = rs.getString("is_ricoverato_return_cc");
			}

//			System.out.println("Animale con microchip " + microchip
//					+ " ricoverato in vam alla data " + dataReg + ":" + ret);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnectionVam(conn);
			// DbUtil.chiudiConnessioneJDBC(rs, pst, conn);

		}

		return ret;

	}

	public boolean isRandagio(Connection db) {
		boolean isRandagio = false;
		try {

			// Gatto gatto = new Gatto(db, this.idAnimale);
			Operatore prop = new Operatore();
			prop.queryRecordOperatorebyIdLineaProduttiva(db,
					this.getIdProprietario());
			Stabilimento stabProp = (Stabilimento) prop.getListaStabilimenti()
					.get(0);
			LineaProduttiva lp = (LineaProduttiva) stabProp
					.getListaLineeProduttive().get(0);

			Operatore det = new Operatore();
			det.queryRecordOperatorebyIdLineaProduttiva(db,
					this.getIdDetentore());
			Stabilimento stab = (Stabilimento) det.getListaStabilimenti()
					.get(0);
			LineaProduttiva lpDet = (LineaProduttiva) stab
					.getListaLineeProduttive().get(0);

			switch (this.idSpecie) {
			case Cane.idSpecie: { // è randagio se è stato catturato
				Cane cane = new Cane(db, this.idAnimale);
				isRandagio = (cane.getFlagCattura()
						|| (lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindaco && lpDet
								.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile)
						|| (lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindacoFR && lpDet
								.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile) || (lp
						.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindaco && lpDet
						.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindaco));
				break;
			}
			case Gatto.idSpecie: { // e' randagio se sta in colonia

				if ((lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneColonia)
						|| (lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindaco && lpDet
								.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile)
						|| (lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindaco && lpDet
								.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneColonia)
						|| (lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindaco && lpDet
								.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindaco)) {
					isRandagio = true;
					break;
				}
			}

			default:
				isRandagio = false;
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isRandagio;
	}

	/*
	 * Registro la lista dei cani per i quali ho generato un modulo richiesta
	 * campioni dal modulo barcode cani in canile
	 */

	public boolean insertMicrochipStampaMassiva(Connection db,
			UserBean userEnteredBy, int numeroProgressivo) throws SQLException {
		PreparedStatement pst = null;

		pst = db.prepareStatement("INSERT INTO stampa_massiva_cani "
				+ " (nome_file, enteredby, "
				+ "modifiedby , microchip, asl_rif,  canile_id, controlli, numero_progressivo, username, role_id, role_description  "
				+ ") VALUES ( ?,?,?,?,?,?,?,?,?,?,? )");

		int i = 0;

		pst.setString(++i, "Codici_aBarre" + numeroProgressivo + ".pdf");
		pst.setInt(++i, userEnteredBy.getUserId());
		pst.setInt(++i, userEnteredBy.getUserId());
		pst.setString(++i, this.microchip);
		pst.setInt(++i, this.idAslRiferimento);
		pst.setInt(++i, this.getIdDetentore());
		pst.setBoolean(++i, false);
		pst.setInt(++i, numeroProgressivo);
		pst.setString(++i, userEnteredBy.getUsername());
		pst.setInt(++i, userEnteredBy.getRoleId());
		pst.setString(++i, userEnteredBy.getRole());
		pst.execute();

		pst.close();

		return true;

	}

	public static int getProgressivoCampione(Connection db) throws SQLException {
		ResultSet rs = null;
		int progressive = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT nextval('codice_stampa_seq') ");
		Statement pst = db.createStatement();
		rs = pst.executeQuery(sql.toString());

		if (rs.next()) {
			// Progressivo per quell'ASL
			progressive = rs.getInt(1);
		}

		pst.close();
		// Il codice del progressivo ASL viene ritornato
		return progressive;

	}

	public boolean checkContributoPerDetentoreCanileStessaAsl(UserBean user) {
		boolean canAddRegistrazioneConContributo = false;
		int idAsl = -1;
		boolean isCanile = false;
		if (this.idSpecie == Cane.idSpecie && !this.flagSterilizzazione
				&& !this.isFlagDecesso()) { // Se
			// è
			// un
			// cane
			// nn
			// sterilizzato
			if (proprietario != null && proprietario.getIdOperatore() > 0) {
				Stabilimento stab = (Stabilimento) proprietario
						.getListaStabilimenti().get(0);
				idAsl = stab.getIdAsl();
			}
			Connection conn = null;
			if (idAsl != 0 && idAsl != 14) { // Se
				// l'asl
				// del
				// proprietario
				// è
				// diversa
				// da
				// quella
				// dell'operatore
				// asl
				// ed
				// è
				// diversa
				// da
				// fuori
				// regione
				try {

					conn = GestoreConnessioni.getConnection();

					// String dbName =
					// ApplicationProperties.getProperty("dbnameBdu");
					// String username = ApplicationProperties
					// .getProperty("usernameDbbdu");
					// String pwd =
					// ApplicationProperties.getProperty("passwordDbbdu");
					// String host =
					// InetAddress.getByName("hostDbBdu").getHostAddress();
					//
					// conn = DbUtil.getConnection(dbName, username, pwd, host);

					Cane thisCane = new Cane(conn, idAnimale);
					if (thisCane.getDetentore() != null
							&& thisCane.getDetentore().getIdOperatore() > 0) {
						Stabilimento stabDetentore = (Stabilimento) thisCane
								.getDetentore().getListaStabilimenti().get(0);
						LineaProduttiva lp = (LineaProduttiva) ((Stabilimento) thisCane
								.getDetentore().getListaStabilimenti().get(0))
								.getListaLineeProduttive().get(0);
						if (stabDetentore.getIdAsl() == user.getSiteId()
								&& lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile) {
							// IL CANE E' DETENUTO IN UN CANILE DELL'ASL
							// DELL'UTENTE
							isCanile = true;
						}
					}

					if (isCanile) {
						Pratica pr = new Pratica();
						// Esiste una pratica per quel canile?
						int count = pr.buildListFromDet(conn,
								thisCane.getIdDetentore());
						if (count > 0) { // Esiste una pratica per il canile
							canAddRegistrazioneConContributo = true;
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				} finally {
					// DbUtil.chiudiConnessioneJDBC(null, null, conn);
					GestoreConnessioni.freeConnection(conn);
				}
			}
		}

		return canAddRegistrazioneConContributo;
	}

	/**
	 * Questo metodo controlla se l'animale non è sterilizzato, nel qual caso un
	 * operatore di asl diversa da quella del cane può inserire una
	 * registrazione di sterilizzazione nel caso l'animale sia detenuto in un
	 * canile dell'asl dell'operatore e contemporaneamente per quel canile siano
	 * presenti progetti di sterilizzazione aperti. Valido solo per i cani in
	 * canile, non serve per i gatti
	 * 
	 * @param user
	 * @return
	 */

	public boolean checkContributoPerDetentoreCanile(UserBean user) {
		boolean canAddRegistrazioneConContributo = false;
		int idAsl = -1;
		boolean isCanile = false;
		if (this.idSpecie == Cane.idSpecie && !this.flagSterilizzazione
				&& !this.isFlagDecesso()) { // Se
			// è
			// un
			// cane
			// nn
			// sterilizzato
			if (proprietario != null && proprietario.getIdOperatore() > 0) {
				Stabilimento stab = (Stabilimento) proprietario
						.getListaStabilimenti().get(0);
				idAsl = stab.getIdAsl();
			}
			Connection conn = null;
			if (idAsl != 0 && idAsl != 14 && user.getSiteId() != idAsl) { // Se
				// l'asl
				// del
				// proprietario
				// è
				// diversa
				// da
				// quella
				// dell'operatore
				// asl
				// ed
				// è
				// diversa
				// da
				// fuori
				// regione
				try {

					conn = GestoreConnessioni.getConnection();

					// String dbName =
					// ApplicationProperties.getProperty("dbnameBdu");
					// String username = ApplicationProperties
					// .getProperty("usernameDbbdu");
					// String pwd =
					// ApplicationProperties.getProperty("passwordDbbdu");
					// String host =
					// InetAddress.getByName("hostDbBdu").getHostAddress();
					//
					// conn = DbUtil.getConnection(dbName, username, pwd, host);

					Cane thisCane = new Cane(conn, idAnimale);
					if (thisCane.getDetentore() != null
							&& thisCane.getDetentore().getIdOperatore() > 0) {
						Stabilimento stabDetentore = (Stabilimento) thisCane
								.getDetentore().getListaStabilimenti().get(0);
						LineaProduttiva lp = (LineaProduttiva) ((Stabilimento) thisCane
								.getDetentore().getListaStabilimenti().get(0))
								.getListaLineeProduttive().get(0);
						if (stabDetentore.getIdAsl() == user.getSiteId()
								&& lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile) {
							// IL CANE E' DETENUTO IN UN CANILE DELL'ASL
							// DELL'UTENTE
							isCanile = true;
						}
					}

					if (isCanile) {
						Pratica pr = new Pratica();
						// Esiste una pratica per quel canile?
						int count = pr.buildListFromDet(conn,
								thisCane.getIdDetentore());
						if (count > 0) { // Esiste una pratica per il canile
							canAddRegistrazioneConContributo = true;
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				} finally {
					// DbUtil.chiudiConnessioneJDBC(null, null, conn);
					GestoreConnessioni.freeConnection(conn);
				}
			}
		}

		return canAddRegistrazioneConContributo;
	}

	/**
	 * Questo metodo setta l'asl dell'animale in base all'asl del proprietario
	 * scelto per i ruoli LLP e ANAGRAFE CANINA in quanto tali ruoli possono
	 * associare all'animale un proprietario esterno alla propria asl
	 * 
	 * @param user
	 * @return
	 */
	public static boolean setAslRiferimentoAnimaleByRole(UserBean user) {
		boolean setAslProprietario = false;

		if (user.getRoleId() == new Integer(
				ApplicationProperties.getProperty("ID_RUOLO_LLP"))
				|| user.getRoleId() == new Integer(
						ApplicationProperties.getProperty("UNINA"))
				// || user.getRoleId() == new Integer(ApplicationProperties
				// .getProperty("ID_RUOLO_ANAGRAFE_CANINA"))
				|| user.getRoleId() == new Integer(
						ApplicationProperties.getProperty("ID_RUOLO_HD1"))
				|| user.getRoleId() == new Integer(
						ApplicationProperties.getProperty("ID_RUOLO_HD2")))
			setAslProprietario = true;

		return setAslProprietario;

	}

	public void cancella(Connection db) throws Exception {

		int result = this.cancellaRegistrazioni(db);

		if (result > 0) {
			StringBuffer update_to_cancel = new StringBuffer();

			update_to_cancel
					.append("Update animale set data_cancellazione = CURRENT_TIMESTAMP, utente_cancellazione = ?");

			if (this.noteInternalUseOnly != null
					&& !("").equals(noteInternalUseOnly)) {
				update_to_cancel.append(", note_internal_use_only = ?");
			}

			update_to_cancel.append(" where id = ?");

			PreparedStatement pst = db.prepareStatement(update_to_cancel
					.toString());
			int i = 0;
			pst.setInt(++i, this.idUtenteCancellazione);

			if (this.noteInternalUseOnly != null
					&& !("").equals(noteInternalUseOnly)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"dd/MM/yyyy hh:mm:ss");
				pst.setString(
						++i,
						noteInternalUseOnly + " ["
								+ dateFormat.format(new Date()) + "] ");
			}

			pst.setInt(++i, this.idAnimale);

			pst.execute();
		}

	}

	private int cancellaRegistrazioni(Connection db) throws Exception {

		StringBuffer update_to_cancel = new StringBuffer();

		update_to_cancel
				.append("Update evento set trashed_date = CURRENT_TIMESTAMP, id_utente_modifica = ?");

		if (this.noteInternalUseOnly != null
				&& !("").equals(noteInternalUseOnly)) {
			update_to_cancel.append(", note_internal_use_only = ?");
		}

		update_to_cancel.append("where id_animale = ?");

		PreparedStatement pst = db
				.prepareStatement(update_to_cancel.toString());
		int i = 0;
		pst.setInt(++i, this.idUtenteCancellazione);

		if (this.noteInternalUseOnly != null
				&& !("").equals(noteInternalUseOnly)) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"dd/MM/yyyy hh:mm:ss");
			pst.setString(++i,
					noteInternalUseOnly + " [" + dateFormat.format(new Date())
							+ "] ");
		}

		pst.setInt(++i, this.idAnimale);

		int h = pst.executeUpdate();

		return h;

	}

	public Animale getAnimaleDaSpecie(Connection db) {
		Animale toReturn = new Animale();
		try {
			switch (this.getIdSpecie()) {

			case Cane.idSpecie:
				return (Cane) this;

			case Gatto.idSpecie:
				return (Gatto) this;

			case Furetto.idSpecie:
				return (Furetto) this;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this;
	}

	public static void updateAslAnimaliProprietario(
			AnimaleList listaAnimaliDaAggiornare, int idAsl, Connection con,
			int idUtenteModifica, int idAslUtente, boolean inregione,
			int idRegione) {

		try {

			for (int i = 0; i < listaAnimaliDaAggiornare.size(); i++) {
				Animale animaleDaAgg = (Animale) listaAnimaliDaAggiornare
						.get(i);
				animaleDaAgg.setIdAslRiferimento(idAsl);
				if (inregione == false) { // DEVO AGGIORNARE LO STATO DEI CANI A
					// FUORI REGIONE

					EventoTrasferimentoFuoriRegione trasf = new EventoTrasferimentoFuoriRegione();
					trasf.setEnteredby(idUtenteModifica);
					trasf.setModifiedby(idUtenteModifica);

					trasf.setIdAnimale(animaleDaAgg.getIdAnimale());
					trasf.setMicrochip(animaleDaAgg.getMicrochip());
					trasf.setIdRegioneA(idRegione);
					trasf.setIdAslRiferimento(idAslUtente);

					trasf.insert(con);
					RegistrazioniWKF rg_wkf = new RegistrazioniWKF();
					rg_wkf.setIdStato(animaleDaAgg.getStato());
					rg_wkf.setIdRegistrazione(EventoTrasferimentoFuoriRegione.idTipologiaDB);
					rg_wkf.getProssimoStatoDaStatoPrecedenteERegistrazione(con);
					animaleDaAgg.setStato(rg_wkf.getIdProssimoStato());

				}
				animaleDaAgg.updateAsl(con);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void updateAsl(Connection con) {
		try {
			String queryUpdate = "update animale set id_asl_riferimento = ?, stato = ? where id = ?";
			PreparedStatement pst = con.prepareStatement(queryUpdate);
			pst.setInt(1, this.getIdAslRiferimento());
			pst.setInt(2, this.getStato());
			pst.setInt(3, this.getIdAnimale());

			pst.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void updateRegione(Connection con) {
		try {
			String queryUpdate = "update animale set id_regione = ? where id = ?";
			PreparedStatement pst = con.prepareStatement(queryUpdate);
			pst.setInt(1, this.getIdRegione());
			pst.setInt(2, this.getIdAnimale());

			pst.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public int cercaRitrovamentoNonDenunciatoAperto(Connection db)
			throws SQLException {
		int eventoAperto = -1;
		StringBuffer sql = new StringBuffer();
		
		try {
			
			sql.append("SELECT rit_nd.id_evento as TROVATO FROM evento_ritrovamento_non_denunciato rit_nd left join evento e on e.id_evento = rit_nd.id_evento where e.id_animale = ? AND rit_nd.flag_ritrovamento_aperto=true AND e.data_cancellazione is null ");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, idAnimale);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				eventoAperto = rs.getInt("TROVATO");
			}
			rs.close();
			pst.close();

			

		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return eventoAperto;

	}
	
	
	public int cercaAggressione(Connection db,int idEvento)
			throws SQLException {
		int eventoAperto = -1;
		StringBuffer sql = new StringBuffer();
		
		try {
			
			sql.append("SELECT agg.id_evento as TROVATO FROM evento_aggressione agg left join evento e on e.id_evento = agg.id_evento where e.id_animale = ? AND e.data_cancellazione is null AND e.trashed_date is null and (? is null or ? <> e.id_evento)");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, idAnimale);
			pst.setInt(++i, idEvento);
			pst.setInt(++i, idEvento);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				eventoAperto = rs.getInt("TROVATO");
			}
			rs.close();
			pst.close();

			

		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return eventoAperto;

	}
	
	public int cercaMorsicatura(Connection db,int idEvento)
			throws SQLException {
		int eventoAperto = -1;
		StringBuffer sql = new StringBuffer();
		
		try {
			
			sql.append("SELECT m.id_evento as TROVATO FROM evento_morsicatura m left join evento e on e.id_evento = m.id_evento where e.id_animale = ? AND e.data_cancellazione is null AND e.trashed_date is null  and (? is null or ? <> e.id_evento) ");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, idAnimale);
			pst.setInt(++i, idEvento);
			pst.setInt(++i, idEvento);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				eventoAperto = rs.getInt("TROVATO");
			}
			rs.close();
			pst.close();

			

		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return eventoAperto;

	}

	/*
	 * public int aggiornaRegistrazionePassaporto(Connection conn) { int result
	 * = -1;
	 * 
	 * try { StringBuffer sql = new StringBuffer(); //Aggiornamento
	 * evento_rilascio_passaportosql.append(
	 * "update evento_rilascio_passaporto set numero_passaporto = ?, data_rilascio_passaporto = ? where id_evento = (select id_evento from evento where id_animale = ? and id_tipologia_evento = ? and data_cancellazione is null) and flag_passaporto_attuale=true;"
	 * ); PreparedStatement pst = conn.prepareStatement(sql.toString()); int i =
	 * 0; pst.setString(++i, this.getNumeroPassaporto()); pst.setTimestamp(++i,
	 * this.getDataRilascioPassaporto()); pst.setInt(++i, this.getIdAnimale());
	 * pst.setInt(++i, EventoRilascioPassaporto.idTipologiaDB); result =
	 * pst.executeUpdate(); if (result>0) { StringBuffer sql2= new
	 * StringBuffer(); //Aggiornamento eventosql2.append(
	 * "update evento set modified=now(), id_utente_modifica = ? where id_animale= ? and id_tipologia_evento = ? and data_cancellazione is null and id_evento = (select id_evento from evento_rilascio_passaporto where id_animale = ? and id_tipologia_evento = ? and data_cancellazione is null and flag_passaporto_attuale=true) "
	 * ); PreparedStatement pst2 = conn.prepareStatement(sql2.toString()); i=0;
	 * pst2.setInt(++i, this.getIdUtenteModifica()); pst2.setInt(++i,
	 * this.getIdAnimale()); pst2.setInt(++i,
	 * EventoRilascioPassaporto.idTipologiaDB);
	 * 
	 * pst2.setInt(++i, this.getIdAnimale()); pst2.setInt(++i,
	 * EventoRilascioPassaporto.idTipologiaDB);
	 * 
	 * result = pst2.executeUpdate(); pst.close(); } } catch (Exception e) {
	 * System.out.println(e); }
	 * 
	 * return result; }
	 */

	public static EsitoControllo verificaMCperRegRabbia(String mc)
			throws SQLException, UnknownHostException {
		Connection db = null;
		ResultSet rs = null;
		CallableStatement cs = null;

		EsitoControllo esito = new EsitoControllo();
		try {
			boolean can = true;
			String descrizioneErrore = "";

			// String dbName = ApplicationProperties.getProperty("dbnameBdu");
			// String username = ApplicationProperties
			// .getProperty("usernameDbbdu");
			// String pwd = ApplicationProperties.getProperty("passwordDbbdu");
			// String host =
			// InetAddress.getByName("hostDbBdu").getHostAddress();
			//
			// db = DbUtil.getConnection(dbName, username, pwd, host);

			db = GestoreConnessioni.getConnection();

			// Verifico che l'animale esiste e non è cancellato
			Animale thisAnimale = new Animale(db, mc);
			if (thisAnimale.getIdAnimale() <= 0) {
				can = false;
				descrizioneErrore = "Animale non anagrafato in banca dati, per favore verifica i tuoi dati";

			} else if (thisAnimale.getIdProprietario() <=  0 ) {
				can = false;
				descrizioneErrore = "Animale non assegnato correttamente ad un proprietario, si prega di risolvere la situazione rivolgendosi all'asl di competenza";
			}
			else{
				// Controllo ultima vaccinazione anti rabbia
				EventoInserimentoVaccinazioni vaccinazioneAntiRabbiaUltima = EventoInserimentoVaccinazioni
						.getUltimaVaccinazioneDaTipo(db, mc, thisAnimale.getTatuaggio(), 1);
				if (vaccinazioneAntiRabbiaUltima.getIdEvento() > 0) {// esiste
					// una
					// vacc
					// antirabbica
					Timestamp dataUltimaVaccinazione = vaccinazioneAntiRabbiaUltima
							.getDataVaccinazione();
					java.util.Date date = new java.util.Date();
					Timestamp now = new Timestamp(date.getTime());

					long diff = now.getTime()
							- dataUltimaVaccinazione.getTime();
					int days = (int) (diff / 86400000);
					if (days < 90) {
						can = false;
						descrizioneErrore = "Esiste già una vaccinazione per il microchip "
								+ mc
								+ " effettuata da meno di 90 giorni , verifica i tuoi dati";
					}
				} else {
					can = true;
				}

			}

			if (can) {
				esito.setIdEsito(1);
			} else {
				esito.setIdEsito(-1);
				esito.setDescrizione(descrizioneErrore);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			// DbUtil.chiudiConnessioneJDBC(rs, cs, db);
			GestoreConnessioni.freeConnection(db);
		}
		return esito;
	}
	
	
	public static EsitoControllo verificaMCperRegRabbiaDataEffettivaVaccinazione(String mc, String dataVaccinazione)
			throws SQLException, UnknownHostException {
		Connection db = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		
		Timestamp nowVaccinazione = null;
		
		EsitoControllo esito = new EsitoControllo();
		try {
			boolean can = true;
			String descrizioneErrore = "";
			try{	
			    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			    Date parsedDate = dateFormat.parse(dataVaccinazione);
			    nowVaccinazione = new java.sql.Timestamp(parsedDate.getTime());
			}catch(Exception e){
				e.printStackTrace();
			}

			// String dbName = ApplicationProperties.getProperty("dbnameBdu");
			// String username = ApplicationProperties
			// .getProperty("usernameDbbdu");
			// String pwd = ApplicationProperties.getProperty("passwordDbbdu");
			// String host =
			// InetAddress.getByName("hostDbBdu").getHostAddress();
			//
			// db = DbUtil.getConnection(dbName, username, pwd, host);

			db = GestoreConnessioni.getConnection();

			// Verifico che l'animale esiste e non è cancellato
			mc = mc.trim();
			Animale thisAnimale = new Animale(db, mc);
			if (thisAnimale.getIdAnimale() <= 0) {
				can = false;
				descrizioneErrore = "Animale non anagrafato in banca dati, per favore verifica i tuoi dati";

			} else if (thisAnimale.getIdProprietario() <=  0 ) {
				can = false;
				descrizioneErrore = "Animale non assegnato correttamente ad un proprietario, si prega di risolvere la situazione rivolgendosi all'asl di competenza";
			} else if (thisAnimale.isFlagDecesso()) {
				can = false;
				descrizioneErrore = "Animale risulta deceduto in BDU, non puoi inserire una registrazione di prelievo";
			}else if (thisAnimale.isFlagSmarrimento()){
				can = false;
				descrizioneErrore = "Animale in stato smarrito, si prega di risolvere la situazione rivolgendosi all'asl di competenza";
				
			}else if (thisAnimale.isFlagFurto()){
				can = false;
				descrizioneErrore = "Animale in stato di furto, si prega di risolvere la situazione rivolgendosi all'asl di competenza";
				
			}
			else{
				// Controllo ultima vaccinazione anti rabbia
				EventoInserimentoVaccinazioni vaccinazioneAntiRabbiaUltima = EventoInserimentoVaccinazioni
						.getUltimaVaccinazioneDaTipo(db, mc, thisAnimale.getTatuaggio(), 1);
				if (vaccinazioneAntiRabbiaUltima.getIdEvento() > 0) {// esiste
					// una
					// vacc
					// antirabbica
					Timestamp dataUltimaVaccinazione = vaccinazioneAntiRabbiaUltima
							.getDataVaccinazione();
					
				//	java.util.Date date = new java.util.Date();
				
				//	Timestamp now = new Timestamp(date.getTime());

					long diff = nowVaccinazione.getTime()
							- dataUltimaVaccinazione.getTime();
					int days = (int) (diff / 86400000);
					if (days < 90) {
						can = false;
						descrizioneErrore = "Esiste già una vaccinazione per il microchip "
								+ mc
								+ " effettuata da meno di 90 giorni , verifica i tuoi dati";
						
						}
				} else {
					can = true;
				}

			}

			if (can){

				//Recupero evento registrazione
				EventoRegistrazioneBDU reg = EventoRegistrazioneBDU.getEventoRegistrazione(db,	thisAnimale.getIdAnimale());
				boolean provenienzaFR = false;
				switch(thisAnimale.getIdSpecie()){
				case Cane.idSpecie: {
					Cane cane = new Cane(db, thisAnimale.getIdAnimale());
					provenienzaFR = (cane.getIdRegioneProvenienza()>0) ? true : false;
				break;
				}
				case Gatto.idSpecie: { // e' randagio se sta in colonia
					Gatto gatto = new Gatto(db, thisAnimale.getIdAnimale());
					provenienzaFR = (gatto.getIdRegioneProvenienza()>0) ? true : false;
					break;
				}
				}
				boolean saltaControlli = false;
				
				//Se proviene dalla regione O e' stato anagrafato all'estero O e' stato anagrafato fuori regione
				System.out.println("Provenienza animale: "+reg.getProvenienza_origine() + " " + (reg.getProvenienza_origine()!=null && !reg.getProvenienza_origine().equals("") && !reg.getProvenienza_origine().contains("in")));
				System.out.println("Flag anagrafe estera: "+reg.isFlag_anagrafe_estera());
				System.out.println("Flag anagrafe fr: "+reg.isFlag_anagrafe_fr());
				System.out.println("Provenienza fr: "+ provenienzaFR);
				if ((reg.getProvenienza_origine()!=null && !reg.getProvenienza_origine().equals("") && !reg.getProvenienza_origine().contains("in")) || reg.isFlag_anagrafe_estera() || reg.isFlag_anagrafe_fr() || provenienzaFR )
					saltaControlli = true;
				System.out.println("dataMicrochip: "+ thisAnimale.getDataMicrochip());
				System.out.println("nowVaccinazione: "+ nowVaccinazione);

				if (!saltaControlli && (thisAnimale.getDataMicrochip()!= null && nowVaccinazione.before(thisAnimale.getDataMicrochip()))) {
					System.out.println("ERRORE DATE MICROCHIP < VACCINAZIONE");
					can = false;
					descrizioneErrore = "La data vaccinazione inserita risulta antecedente alla data chippatura del microchip "+ mc + ", verifica i tuoi dati";
			} }
			
			if (can) {
				esito.setIdEsito(1);
			} else {
				esito.setIdEsito(-1);
				esito.setDescrizione(descrizioneErrore);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			// DbUtil.chiudiConnessioneJDBC(rs, cs, db);
			GestoreConnessioni.freeConnection(db);
		}
		return esito;
	}
	
	

	public static EsitoControllo verificaMCperPassaportoRegRabbia(String mc, String tatuaggio,
			String data) throws SQLException, UnknownHostException {
		Connection db = null;
		ResultSet rs = null;
		CallableStatement cs = null;

		EsitoControllo esito = new EsitoControllo();
		try {
			boolean can = true;
			String descrizioneErrore = "";

			// String dbName = ApplicationProperties.getProperty("dbnameBdu");
			// String username = ApplicationProperties
			// .getProperty("usernameDbbdu");
			// String pwd = ApplicationProperties.getProperty("passwordDbbdu");
			// String host =
			// InetAddress.getByName("hostDbBdu").getHostAddress();
			//
			// db = DbUtil.getConnection(dbName, username, pwd, host);

			db = GestoreConnessioni.getConnection();

			// Verifico che l'animale esiste e non è cancellato
			Animale thisAnimale = new Animale(db, mc);
			if (thisAnimale.getIdAnimale() <= 0) {
				can = false;
				descrizioneErrore = "Animale non anagrafato in banca dati, per favore verifica i tuoi dati";

			} else {
				// Controllo ultima vaccinazione anti rabbia
				EventoInserimentoVaccinazioni vaccinazioneAntiRabbiaUltima = EventoInserimentoVaccinazioni
						.getUltimaVaccinazioneDaTipo(db, mc, tatuaggio, 1);
				if (vaccinazioneAntiRabbiaUltima.getIdEvento() > 0) {// esiste
					// una
					// vacc
					// antirabbica
					Timestamp dataUltimaVaccinazione = vaccinazioneAntiRabbiaUltima.getDataVaccinazione();
					int idTipoVaccinoInoculato = vaccinazioneAntiRabbiaUltima.getIdTipologiaVaccinoInoculato();
					int daysToControl = getDaysTipologiaVaccinoInoculato(db, idTipoVaccinoInoculato);
					
					Date d = new Date(dataUltimaVaccinazione.getTime());
					Calendar d1 = Calendar.getInstance();
					d1.setTime(d);
					d1.add(Calendar.DAY_OF_MONTH, daysToControl);
					Date d2 = new Date(d1.getTimeInMillis());
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					esito.setDataRegistrazioneAnimale(sdf.format(d2));

					// java.util.Date date = new java.util.Date();
					// Timestamp now = new Timestamp(date.getTime());
					// Timestamp now = Timestamp.valueOf(data);

					Timestamp now = null;
					try {
						java.util.Date date = sdf.parse(data);
						now = new java.sql.Timestamp(date.getTime());
					} catch (Exception e) {
						// TODO: handle exception
					}
					long diff = now.getTime()
							- dataUltimaVaccinazione.getTime();
					int days = (int) (diff / 86400000);
					if (days < daysToControl && days >= 0) {
						can = false;
						descrizioneErrore = "Esiste già una vaccinazione per il microchip "
								+ mc
								+ " effettuata nell'anno corrente, verifica i tuoi dati";
					} else if (days < 0 || days > 365) {
						descrizioneErrore = "Non esiste una vaccinazione antirabbica per il microchip "
								+ mc
								+ " effettuata nell'anno corrente. Puoi inserirla prima di proseguire";
					}
				} else {
					can = true;
					descrizioneErrore = "Non esiste una vaccinazione antirabbica per il microchip "
							+ mc
							+ " effettuata. Puoi inserirla prima di proseguire";

				}

			}

			if (can) {
				esito.setIdEsito(1);
				esito.setDescrizione(descrizioneErrore);
			} else {
				esito.setIdEsito(-1);
				esito.setDescrizione(descrizioneErrore);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			// DbUtil.chiudiConnessioneJDBC(rs, cs, db);
			GestoreConnessioni.freeConnection(db);
		}
		return esito;
	}

	public static EsitoControllo verificaMCperTitolazioneRabbia(String mc,
			int userId, boolean checkAnnoCorrente) throws SQLException,
			UnknownHostException {

		// checkAnnoCorrente true controllo anche esistenza vaccinazione anno
		// corrente, false no
		Connection db = null;
		ResultSet rs = null;
		CallableStatement cs = null;

		EsitoControllo esito = new EsitoControllo();
		try {
			boolean can = true;
			String descrizioneErrore = "";

			// String dbName = ApplicationProperties.getProperty("dbnameBdu");
			// String username = ApplicationProperties
			// .getProperty("usernameDbbdu");
			// String pwd = ApplicationProperties.getProperty("passwordDbbdu");
			// String host =
			// InetAddress.getByName("hostDbBdu").getHostAddress();
			//
			// db = DbUtil.getConnection(dbName, username, pwd, host);

			db = GestoreConnessioni.getConnection();

			// Verifico che l'animale esiste e non è cancellato
			Animale thisAnimale = new Animale(db, mc);
			if (thisAnimale.getIdAnimale() <= 0) {
				can = false;
				descrizioneErrore = "Animale non anagrafato in banca dati, per favore verifica i tuoi dati";

			} else if (checkAnnoCorrente) {
				// Controllo ultima vaccinazione anti rabbia
				EventoInserimentoVaccinazioni vaccinazioneAntiRabbiaUltima = EventoInserimentoVaccinazioni
						.getUltimaVaccinazioneDaTipo(db, mc, thisAnimale.getTatuaggio(), 1);
				if (vaccinazioneAntiRabbiaUltima.getIdEvento() > 0) {// esiste
					// una
					// vacc
					// antirabbica

					Timestamp dataUltimaVaccinazione = vaccinazioneAntiRabbiaUltima
							.getDataVaccinazione();
					java.util.Date date = new java.util.Date();
					Timestamp now = new Timestamp(date.getTime());

					long diff = now.getTime()
							- dataUltimaVaccinazione.getTime();
					int days = (int) (diff / 86400000);
					if (days > 365) {
						can = false;
						descrizioneErrore = "Non esiste una vaccinazione per il microchip "
								+ mc
								+ " effettuata nell'anno corrente, verifica i tuoi dati";
					}

					User user = new User(db, userId);
					if (user.getRoleId() == new Integer(
							ApplicationProperties.getProperty("ID_RUOLO_LLP"))) {
						// DEVO VERIFICARE CHE SI TRATTA DI UN MC VACCINATO DA
						// LLP
						if ((vaccinazioneAntiRabbiaUltima.getEnteredby() != userId)) {
							can = false;
							descrizioneErrore = "Non hai inserito nessuna registrazione di vaccinazione anti rabbia per il mc  "
									+ mc + ". Verifica i tuoi dati.";
						}
					} else if (thisAnimale.getIdAslRiferimento() != user
							.getSiteId()) {
						can = false;
						descrizioneErrore = "Il microchip  "
								+ mc
								+ " non appartiene alla tua asl, verifica cortesemente i tuoi dati";

					}

				} else {
					can = false;
					descrizioneErrore = "Nessuna registrazione di vaccinazione antirabbica per il microchip "
							+ mc + " è stata registrata nel sistema.";
				}

			}

			if (can) {
				esito.setIdEsito(1);
			} else {
				esito.setIdEsito(-1);
				esito.setDescrizione(descrizioneErrore);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			// DbUtil.chiudiConnessioneJDBC(rs, cs, db);
			GestoreConnessioni.freeConnection(db);
		}
		return esito;
	}

	public boolean equals(Object o) {
		if (!(o instanceof Animale))
			return false;
		return (microchip.equals(((Animale) o).getMicrochip()) && idAnimale == ((Animale) o)
				.getIdAnimale());
	}

	public int getIdComuneCattura() {
		return idComuneCattura;
	}

	public static EsitoControllo verificaEsistenzaMC(String mc)
			throws SQLException, UnknownHostException {
		Connection db = null;
		ResultSet rs = null;
		CallableStatement cs = null;

		EsitoControllo esito = new EsitoControllo();
		try {
			boolean can = true;
			String descrizioneErrore = "";
			String dataRegistrazioneAnimale = null;

			db = GestoreConnessioni.getConnection();

			// Verifico che l'animale esiste e non è cancellato
			Animale thisAnimale = new Animale(db, mc);
			if (thisAnimale.getIdAnimale() <= 0) {
				can = false;
				descrizioneErrore = "Animale non anagrafato in banca dati, per favore verifica i tuoi dati";

			} else if (thisAnimale.isFlagSmarrimento()) {
				can = false;
				descrizioneErrore = "Animale risulta smarrito in Banca dati, bisogna regolarizzare la posizione prima dell'inserimento del prelievo";
			} else if (thisAnimale.isFlagDecesso()) {
				can = false;
				descrizioneErrore = "Animale risulta deceduto in BDU, non puoi inserire una registrazione di prelievo";
			} else if (thisAnimale.isFlagFurto()) {
				can = false;
				descrizioneErrore = "Animale risulta con registrazione di furto in BDU,  bisogna regolarizzare la posizione prima dell'inserimento del prelievo";
			}

			else {
				can = true;
				if (thisAnimale.getDataRegistrazione() != null)
					dataRegistrazioneAnimale = new SimpleDateFormat(
							"dd/MM/yyyy").format(thisAnimale
							.getDataRegistrazione());
			}

			if (can) {
				esito.setIdEsito(1);
				esito.setDataRegistrazioneAnimale(dataRegistrazioneAnimale);
			} else {
				esito.setIdEsito(-1);
				esito.setDescrizione(descrizioneErrore);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			// DbUtil.chiudiConnessioneJDBC(rs, cs, db);
			GestoreConnessioni.freeConnection(db);
		}
		return esito;
	}
	
	
	public static EsitoControllo verificaEsistenzaMCNonSterilizzato(String mc) throws SQLException, UnknownHostException {
		Connection db = null;
		ResultSet rs = null;
		CallableStatement cs = null;

		EsitoControllo esito = new EsitoControllo();
		try 
		{
			boolean can = true;
			String descrizioneErrore = "";
			String dataRegistrazioneAnimale = null;

			db = GestoreConnessioni.getConnection();

			// Verifico che l'animale esiste e non è cancellato
			Animale thisAnimale = new Animale(db, mc);
			if (thisAnimale.getIdAnimale() <= 0) {
				can = false;
				descrizioneErrore = "Animale non anagrafato in banca dati, per favore verifica i tuoi dati";

			} 
			/*else if (thisAnimale.getIdSpecie()!=1) {
				can = false;
				descrizioneErrore = "Impossibile inserire gatti o furetti nel progetto di sterilizzazione.";
			} */
			else if (thisAnimale.isFlagSterilizzazione()) {
				can = false;
				descrizioneErrore = "Animale risulta già sterilizzato, impossibile inserire una sterilizzazione";
			} 
			else if (thisAnimale.isFlagSmarrimento()) {
				can = false;
				descrizioneErrore = "Animale risulta smarrito in Banca dati, bisogna regolarizzare la posizione prima dell'inserimento della sterilizzazione";
			} else if (thisAnimale.isFlagDecesso()) {
				can = false;
				descrizioneErrore = "Animale risulta deceduto in BDU, non puoi inserire una registrazione di sterilizzazione";
			} else if (thisAnimale.isFlagFurto()) {
				can = false;
				descrizioneErrore = "Animale risulta con registrazione di furto in BDU,  bisogna regolarizzare la posizione prima dell'inserimento della sterilizzazione";
			}

			else {
				can = true;
				if (thisAnimale.getDataRegistrazione() != null)
					dataRegistrazioneAnimale = new SimpleDateFormat(
							"dd/MM/yyyy").format(thisAnimale
							.getDataRegistrazione());
			}

			if (can) {
				esito.setIdEsito(1);
				esito.setDataRegistrazioneAnimale(dataRegistrazioneAnimale);
				esito.setComune(((Stabilimento)thisAnimale.getProprietario().getListaStabilimenti().get(0)).getSedeOperativa().getDescrizioneComune());
				esito.setComuneId(((Stabilimento)thisAnimale.getProprietario().getListaStabilimenti().get(0)).getSedeOperativa().getComune());
				esito.setSesso(thisAnimale.getSesso());
			} else {
				esito.setIdEsito(-1);
				esito.setDescrizione(descrizioneErrore);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			// DbUtil.chiudiConnessioneJDBC(rs, cs, db);
			GestoreConnessioni.freeConnection(db);
		}
		return esito;
	}

	/**
	 * Restituisce informazioni di inserimento del record - Utente, Data,
	 * Sistema
	 * 
	 * @throws UnknownHostException
	 */
	public String getInformazioniInserimentoRecord()
			throws UnknownHostException {
		String toReturn = "";
		// String dbName = ApplicationProperties.getProperty("dbnameBdu");
		// String username = ApplicationProperties
		// .getProperty("usernameDbbdu");
		// String pwd = ApplicationProperties.getProperty("passwordDbbdu");
		// String host = InetAddress.getByName("hostDbBdu").getHostAddress();
		Connection db = null;
		try {

			// db = DbUtil.getConnection(dbName, username, pwd, host);
			db = GestoreConnessioni.getConnection();
			User user = new User();
			user.setBuildContact(true);
			if (this.getIdUtenteInserimento() > 0){
				user.buildRecord(db, this.getIdUtenteInserimento());

			LookupList listaSistemi = new LookupList(db, "lookup_sistemi");
			toReturn = user.getContact().getNameFull()
					+ " "
					+ new SimpleDateFormat("dd/MM/yyyy").format(this
							.getDataInserimento())
					+ " (Origine inserimento: "
					+ listaSistemi.getSelectedValue(this
							.getOrigineInserimento()) + ")";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// DbUtil.chiudiConnessioneJDBC(null, db);
			GestoreConnessioni.freeConnection(db);
		}

		return toReturn;

	}

	// public int getIdComuneCattura(){
	//
	// int idComuneCattura = -1;
	// Connection conn = null;
	// try {
	//
	//
	// String dbName = ApplicationProperties.getProperty("dbnameBdu");
	// String username = ApplicationProperties
	// .getProperty("usernameDbbdu");
	// String pwd = ApplicationProperties.getProperty("passwordDbbdu");
	// String host = InetAddress.getByName("hostDbBdu").getHostAddress();
	//
	// conn = DbUtil.getConnection(dbName, username, pwd, host);
	//
	//
	// switch (this.idSpecie) {
	// case 1:
	// Cane thisCane = new Cane(conn, this.idAnimale);
	// idComuneCattura = thisCane.getIdComuneCattura();
	// break;
	//
	// case 2:
	// Gatto thisGatto = new Gatto(conn, this.idAnimale);
	// idComuneCattura = thisGatto.getIdComuneCattura();
	// break;
	// }
	// }catch (Exception e) {
	// e.printStackTrace();
	// }finally{
	// DbUtil.chiudiConnessioneJDBC(null, conn);
	// }
	//
	// return idComuneCattura;
	// }

	public boolean getFlagPrelievoDnaEffettuato(Connection db) {
		boolean flag = false;
		try {
			Cane thisCane = new Cane(db, this.getIdAnimale());
			if (thisCane.isFlagPrelievoDnaEffettuato()) {
				flag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return flag;
	}
	
	public boolean getFlagAllontanamentoEffettuato(Connection db) {
		boolean flag = false;
		EventoAllontanamento all = new EventoAllontanamento();
		all.buildEventoByIdAnimale(db, this.getIdAnimale());
		if (all.getIdEvento()>0)
			flag = true;
		return flag;
	}

	// METODI UTILIZZATI NELLA PRATICA CONTRIBUTI

	public void setNomeCognomeProprietario(Connection db) throws SQLException {
		String select = "select ragione_sociale from opu_operatori_denormalizzati where id_rel_stab_lp = ?";
		PreparedStatement pst = db.prepareStatement(select);

		pst.setInt(1, this.getIdProprietario());
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			this.nomeCognomeProprietario = rs.getString("ragione_sociale");
		}

	}

	public void setNomeCognomeDetentore(Connection db) throws SQLException {
		String select = "select ragione_sociale from opu_operatori_denormalizzati where id_rel_stab_lp = ?";
		PreparedStatement pst = db.prepareStatement(select);

		pst.setInt(1, this.getIdDetentore());
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			this.nomeCognomeDetentore = rs.getString("ragione_sociale");
		}

	}
	
	
	public boolean hasRegistrazioniModificaProprietario() throws SQLException{
		
	
		boolean hasRegistrazioniModificaProprietario = false;
		Connection bduDB = null;
	
	try{
		bduDB = GestoreConnessioni.getConnection();
		
		EventoList listaRegistrazioniAnimale = new EventoList();
		listaRegistrazioniAnimale.setId_animale(this.idAnimale);
		listaRegistrazioniAnimale.buildList(bduDB);
		
		ArrayList<String> listaRegistrazioniModificanoProprietario = ApplicationProperties
				.getListProperty("registrazioni_modifica_proprietario");
		
		
		Iterator<Evento> i = listaRegistrazioniAnimale.iterator();
		
		while (i.hasNext()){
			
			Evento thisEvento = (Evento) i.next();
			if (listaRegistrazioniModificanoProprietario
						.contains(String.valueOf(thisEvento
								.getIdTipologiaEvento()))){
				hasRegistrazioniModificaProprietario = true;
				break; //Ho trovato una registrazione di modifica proprietario, interrompo il ciclo
			}
			
		}
	}catch(SQLException e){
		throw e;
	}finally{
		GestoreConnessioni.freeConnection(bduDB);
	}
		
		
		
		return hasRegistrazioniModificaProprietario;
	}

	
	public void updateValidazione(Connection con) throws SQLException {
		
		String update = "update animale set flag_validato = true where id = ?";
		
		PreparedStatement pst = con.prepareStatement(update);
		pst.setInt(1, this.getIdAnimale());
		pst.execute();
		
	}
	
	
	public static void updateAslAnimaliProprietario(
			AnimaleList listaAnimaliDaAggiornare, int idAsl, Connection con,
			int idUtenteModifica, int idAslUtente, boolean inregione,
			int idRegione, Timestamp data, String nuovoProprietario) {

		try {

			for (int i = 0; i < listaAnimaliDaAggiornare.size(); i++) {
				Animale animaleDaAgg = (Animale) listaAnimaliDaAggiornare
						.get(i);
				boolean animaleFuoriRegione = animaleDaAgg.getIdAslRiferimento()==14;
				animaleDaAgg.setIdAslRiferimento(idAsl);
				if (inregione == false) { // DEVO AGGIORNARE LO STATO DEI CANI A
					// FUORI REGIONE

					EventoTrasferimentoFuoriRegione trasf = new EventoTrasferimentoFuoriRegione();
					trasf.setDataTrasferimentoFuoriRegione(data);
					trasf.setDatiProprietarioFuoriRegione(nuovoProprietario);
					trasf.setEnteredby(idUtenteModifica);
					trasf.setModifiedby(idUtenteModifica);

					trasf.setIdAnimale(animaleDaAgg.getIdAnimale());
					trasf.setMicrochip(animaleDaAgg.getMicrochip());
					trasf.setIdRegioneA(idRegione);
					trasf.setIdAslRiferimento(idAslUtente);
					trasf.setIdStatoOriginale(animaleDaAgg.getStato());
					
					trasf.setIdDetentoreCorrente(animaleDaAgg.getIdDetentore());
					trasf.setIdProprietarioCorrente(animaleDaAgg.getIdProprietario());

					trasf.setSpecieAnimaleId(animaleDaAgg.getIdSpecie());
					
					trasf.setIdAslVecchioDetentore(animaleDaAgg.getIdAslRiferimento());
					trasf.setIdVecchioDetentore(animaleDaAgg.getIdDetentore());
					
					trasf.setIdAslVecchioProprietario(animaleDaAgg.getIdAslRiferimento());
					trasf.setIdVecchioProprietario(animaleDaAgg.getIdProprietario());
					
				
animaleDaAgg.getIdDetentoreUltimoTrasferimentoFRegione();
					trasf.insert(con);
					RegistrazioniWKF rg_wkf = new RegistrazioniWKF();
					rg_wkf.setIdStato(animaleDaAgg.getStato());
					rg_wkf.setIdRegistrazione(EventoTrasferimentoFuoriRegione.idTipologiaDB);
					rg_wkf.getProssimoStatoDaStatoPrecedenteERegistrazione(con);
					animaleDaAgg.setStato(rg_wkf.getIdProssimoStato());
					animaleDaAgg.setIdRegione(idRegione);

				}
				//Se la nuova asl è in regione si deve fare il rientro solo se l'animale proveniva da fuori regione
				else if(animaleFuoriRegione)
				{
					EventoRientroFuoriRegione rientro = new EventoRientroFuoriRegione();
					rientro.setDataRientroFR(data);
					rientro.setIdAsl(14);
					rientro.setIdProprietario(animaleDaAgg.getProprietario().getId_linea_produttiva());
					rientro.setIdDetentore(animaleDaAgg.getDetentore().getId_linea_produttiva());
					rientro.setIdRegioneDa(animaleDaAgg.getIdRegione());
					
					rientro.setEnteredby(idUtenteModifica);
					rientro.setModifiedby(idUtenteModifica);

					rientro.setIdAnimale(animaleDaAgg.getIdAnimale());
					rientro.setMicrochip(animaleDaAgg.getMicrochip());
					rientro.setIdAslRiferimento(idAsl);

					rientro.insert(con);
					RegistrazioniWKF rg_wkf = new RegistrazioniWKF();
					rg_wkf.setIdStato(animaleDaAgg.getStato());
					rg_wkf.setIdRegistrazione(EventoRientroFuoriRegione.idTipologiaDB);
					rg_wkf.getProssimoStatoDaStatoPrecedenteERegistrazione(con);
					animaleDaAgg.setStato(rg_wkf.getIdProssimoStato());

				
				}
				
				animaleDaAgg.updateStato(con);
				animaleDaAgg.updateAsl(con);
				animaleDaAgg.updateRegione(con);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	
	
	
	
	
	
	public static int  getDaysTipologiaVaccinoInoculato(Connection db, int idTipoVaccinoInoculato) throws SQLException
	{
		int days = 365;
		PreparedStatement pst = db.prepareStatement("select days from lookup_tipologia_vaccino_inoculato where code = ? ");
		pst.setInt(1, idTipoVaccinoInoculato);
		ResultSet rs = pst.executeQuery();
		
		if (rs.next())
		{
			days = rs.getInt("days");
		}
			
		return days;
	}
	
	public static String dataScadenzaAntirabbica(String mc) throws SQLException, UnknownHostException {
		Connection db = null;

		try 
		{
			db = GestoreConnessioni.getConnection();

			//Questo metodo non viene chiamato da nessuno, pertanto non faccio la modifica relativa all'aggiunta del parametro tatuaggio al metodo getUltimaVaccinazioneDaTipo
			//Passo null giusto per far compilare la classe
			EventoInserimentoVaccinazioni vaccinazioneAntiRabbiaUltima = EventoInserimentoVaccinazioni.getUltimaVaccinazioneDaTipo(db, mc, null,1);
			if (vaccinazioneAntiRabbiaUltima.getIdEvento() > 0) 
			{
				Timestamp dataUltimaVaccinazione = vaccinazioneAntiRabbiaUltima.getDataVaccinazione();
				long dataUltimaVaccinazioneMillis = dataUltimaVaccinazione.getTime();
				int idTipoVaccinoInoculato = vaccinazioneAntiRabbiaUltima.getIdTipologiaVaccinoInoculato();
				int daysToControl = getDaysTipologiaVaccinoInoculato(db, idTipoVaccinoInoculato);
									
				
				
				Calendar data = Calendar.getInstance();
				data.setTimeInMillis(dataUltimaVaccinazioneMillis);
				data.add(Calendar.DAY_OF_YEAR, daysToControl);
				
				Timestamp dataScadenza = new Timestamp(data.getTimeInMillis());
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String dataScadenzaString = sdf.format(dataScadenza);
				return dataScadenzaString;
			} 
			else 
			{
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			// DbUtil.chiudiConnessioneJDBC(rs, cs, db);
			GestoreConnessioni.freeConnection(db);
		}
		return null;
	}
	
	
	public boolean getRicadentePianoLeishmania(Connection db, int idEvento) throws SQLException
	{
		boolean ricadente = false;
		PreparedStatement pst = db.prepareStatement(" Select e.id_proprietario_corrente, e.id_detentore_corrente, date_part('year',  age(data_nascita)) >=1  and date_part('year',  age(data_nascita)) <=8 as eta_rientrante_piano_leishmania " + 
													" from animale a, evento e, evento_prelievo_leish e_prel_leish " +
													" where e.id_evento = ? and e_prel_leish.id_evento = e.id_evento and e.id_animale = a.id ");
		pst.setInt(1, idEvento);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) 
		{
			if (rs.getInt("id_proprietario_corrente") > 0) 
			{
				proprietario = new Operatore();
				proprietario.queryRecordOperatorebyIdLineaProduttiva(db,
						rs.getInt("id_proprietario_corrente"));
				
				Stabilimento stab = (Stabilimento)proprietario.getListaStabilimenti().get(0);
				LineaProduttiva linea = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
				int idLinea = linea.getIdRelazioneAttivita();
				
				detentore = new Operatore();
				detentore.queryRecordOperatorebyIdLineaProduttiva(db, rs.getInt("id_detentore_corrente"));
				
				Stabilimento stab_det = (Stabilimento)detentore.getListaStabilimenti().get(0);
				LineaProduttiva linea_det = (LineaProduttiva) stab_det.getListaLineeProduttive().get(0);
				int idLinea_det = linea_det.getIdRelazioneAttivita();
				ricadente = rs.getBoolean("eta_rientrante_piano_leishmania") && (idLinea==LineaProduttiva.idAggregazioneCanile || idLinea==LineaProduttiva.idAggregazioneSindaco) && idLinea_det==LineaProduttiva.idAggregazioneCanile;

			}

		}

		rs.close();
		pst.close();
		
		return ricadente;
	
	}
	
	
}
