/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrazioniAnimali.base;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Furetto;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.RegistrazioneModificaIndirizzoOperatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.DbUtil;
import org.aspcfs.utils.DwrUtil;
import org.aspcfs.utils.GestoreConnessioni;

public class EventoModificaResidenza extends Evento {

	public static final int idTipologiaDB = 43;

	public static final int modificaProprietario = 1;
	public static final int modificaDetentore = 2;

	private int id = -1;
	private int idEvento = -1;
	private java.sql.Timestamp dataModificaResidenza;
	private int idTipologiaSoggettoDestinatarioModifica = 1; // Default
																// proprietario
	private int idVecchioIndirizzo = -1;
	private int idNuovoIndirizzo = -1;

	private int idComuneModificaResidenza = -1;
	private int idProvinciaModificaResidenza = -1;
	private String via = "";
	private String cap = "";
	private boolean inRegione = true; // Default trasferimento in regione

	public EventoModificaResidenza() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}
	
	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public int getIdComuneModificaResidenza() {
		return idComuneModificaResidenza;
	}

	public void setIdComuneModificaResidenza(int idComuneModificaResidenza) {
		this.idComuneModificaResidenza = idComuneModificaResidenza;
	}

	public void setIdComuneModificaResidenza(String idComuneModificaResidenza) {
		this.idComuneModificaResidenza = new Integer(idComuneModificaResidenza);
	}

	public int getIdProvinciaModificaResidenza() {
		return idProvinciaModificaResidenza;
	}

	public void setIdProvinciaModificaResidenza(int idProvinciaModificaResidenza) {
		this.idProvinciaModificaResidenza = idProvinciaModificaResidenza;
	}

	public void setIdProvinciaModificaResidenza(String idProvinciaModificaResidenza) {
		this.idProvinciaModificaResidenza = new Integer(idProvinciaModificaResidenza);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public java.sql.Timestamp getDataModificaResidenza() {
		return dataModificaResidenza;
	}

	public void setDataModificaResidenza(java.sql.Timestamp dataModificaResidenza) {
		this.dataModificaResidenza = dataModificaResidenza;
	}

	public void setDataModificaResidenza(String dataModificaResidenza) {
		this.dataModificaResidenza = DateUtils.parseDateStringNew(dataModificaResidenza, "dd/MM/yyyy");
	}

	public int getIdTipologiaSoggettoDestinatarioModifica() {
		return idTipologiaSoggettoDestinatarioModifica;
	}

	public void setIdTipologiaSoggettoDestinatarioModifica(int idTipologiaSoggettoDestinatarioModifica) {
		this.idTipologiaSoggettoDestinatarioModifica = idTipologiaSoggettoDestinatarioModifica;
	}

	public int getIdVecchioIndirizzo() {
		return idVecchioIndirizzo;
	}

	public void setIdVecchioIndirizzo(int idVecchioIndirizzo) {
		this.idVecchioIndirizzo = idVecchioIndirizzo;
	}

	public int getIdNuovoIndirizzo() {
		return idNuovoIndirizzo;
	}

	public void setIdNuovoIndirizzo(int idNuovoIndirizzo) {
		this.idNuovoIndirizzo = idNuovoIndirizzo;
	}

	public void setIdNuovoIndirizzo(String idNuovoIndirizzo) {
		this.idNuovoIndirizzo = new Integer(idNuovoIndirizzo);
	}

	public boolean isInRegione() {
		return inRegione;
	}

	public void setInRegione(boolean inRegione) {
		this.inRegione = inRegione;
	}

	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
			
	
			super.insert(db);
			idEvento = super.getIdEvento();

			id = DatabaseUtils.getNextSeq(db, "evento_modifica_residenza_id_seq");
			// sql.append("INSERT INTO animale (");

			sql.append(" INSERT INTO evento_modifica_residenza (id_evento, data_modifica_residenza, id_tipologia_soggetto_destinatario_modifica_residenza ");

			if (idVecchioIndirizzo > 0) {
				sql.append(", id_vecchio_indirizzo");
			}

			if (idNuovoIndirizzo > 0) {
				sql.append(", id_nuovo_indirizzo");
			}

			sql.append(", in_regione");

			sql.append(")VALUES (?,?,?");

			if (idVecchioIndirizzo > 0) {
				sql.append(", ?");
			}

			if (idNuovoIndirizzo > 0) {
				sql.append(", ?");
			}

			sql.append(", ?)");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idEvento);

			pst.setTimestamp(++i, dataModificaResidenza);

			pst.setInt(++i, idTipologiaSoggettoDestinatarioModifica);

			if (idVecchioIndirizzo > 0) {
				pst.setInt(++i, idVecchioIndirizzo);
			}

			if (idNuovoIndirizzo > 0) {
				pst.setInt(++i, idNuovoIndirizzo);
			}

			pst.setBoolean(++i, inRegione);

			pst.execute();
			pst.close();

			this.id = DatabaseUtils.getCurrVal(db, "evento_modifica_residenza_id_seq", id);

			
		} catch (SQLException e) {
		
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return true;

	}

	public EventoModificaResidenza(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		super.buildRecord(rs);
		this.idEvento = rs.getInt("idevento");
		this.dataModificaResidenza = rs.getTimestamp("data_modifica_residenza");
		this.idTipologiaSoggettoDestinatarioModifica = rs
				.getInt("id_tipologia_soggetto_destinatario_modifica_residenza");
		this.inRegione = rs.getBoolean("in_regione");
		this.idNuovoIndirizzo = rs.getInt("id_nuovo_indirizzo");
		this.idVecchioIndirizzo = rs.getInt("id_vecchio_indirizzo");

		// buildSede(rs);
		// buildRappresentanteLegale(rs);

	}

	public EventoModificaResidenza(Connection db, int idEventoPadre) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_modifica_residenza f on (e.id_evento = f.id_evento) where e.id_evento = ?");
		pst.setInt(1, idEventoPadre);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) {
			buildRecord(rs);
		}

		if (idEventoPadre == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		rs.close();
		pst.close();
	}

	public Animale insert(Connection db, Cane cane) {
		// TODO
		//System.out.println("cane");
		Operatore opToModify = null;
		Indirizzo newIndirizzo = new Indirizzo();
		if (this.getIdTipologiaSoggettoDestinatarioModifica() == modificaProprietario)
			opToModify = cane.getProprietario();
		else if (this.getIdTipologiaSoggettoDestinatarioModifica() == modificaDetentore)
			opToModify = cane.getDetentore();

		try {

			if (this.getIdNuovoIndirizzo() > 0) {
				// INDIRIZZO ESISTENTE, LO RECUPERO DAL DB
				newIndirizzo = new Indirizzo(db, this.getIdNuovoIndirizzo());
			}

			else {

				// INDIRIZZO NON ESISTENTE, LO DEVO CREARE

				newIndirizzo.setComune(this.getIdComuneModificaResidenza());
				newIndirizzo.setProvincia(String.valueOf(this.getIdProvinciaModificaResidenza()));
				newIndirizzo.setCap(ComuniAnagrafica.getCap(db, this.getIdComuneModificaResidenza()));
				newIndirizzo.setVia(this.getVia());
				newIndirizzo.setIdProvincia(this.getIdProvinciaModificaResidenza());

				// Recupero info asl
				Object[] asl;
				asl = DwrUtil.getValoriAsl(newIndirizzo.getComune());
				if (asl != null && asl.length > 0) {

					Object[] aslVal = (Object[]) asl[0];
					if (aslVal != null && aslVal.length > 0)
						newIndirizzo.setIdAsl((Integer) aslVal[0]);
				}

				newIndirizzo.insert(db);

				idNuovoIndirizzo = newIndirizzo.getIdIndirizzo();

			}

			// Se è privato, sindaco, sindaco FR va aggiornata anche la sede
			// legale che coincide con la residenza

			Stabilimento stab = (Stabilimento) opToModify.getListaStabilimenti().get(0);
			idVecchioIndirizzo = stab.getSedeOperativa().getIdIndirizzo();
			stab.setSedeOperativa(newIndirizzo);
			stab.updateSedeOperativa(db);

			LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
			if (lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazionePrivato
					|| lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindaco
					|| lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindacoFR) {
				newIndirizzo.setTipologiaSede(1);
				opToModify.getListaSediOperatore().add(newIndirizzo);
				opToModify.aggiornaRelazioneSede(db, opToModify.getSedeLegale(), newIndirizzo);
			}

			// Inserimento registrazione
			this.insert(db);

		} catch (Exception e) {
			// TODO: handle exception
		}

		cane.setIdAslRiferimento(newIndirizzo.getIdAsl());
		return cane;

	}

	public Animale insert(Connection db, Gatto gatto) {
		// TODO
		//System.out.println("gatto");

		Operatore opToModify = null;
		Indirizzo newIndirizzo = new Indirizzo();
		if (this.getIdTipologiaSoggettoDestinatarioModifica() == modificaProprietario)
			opToModify = gatto.getProprietario();
		else if (this.getIdTipologiaSoggettoDestinatarioModifica() == modificaDetentore)
			opToModify = gatto.getDetentore();

		try {

			if (this.getIdNuovoIndirizzo() > 0) {
				// INDIRIZZO ESISTENTE, LO RECUPERO DAL DB
				newIndirizzo = new Indirizzo(db, this.getIdNuovoIndirizzo());
			}

			else {

				// INDIRIZZO NON ESISTENTE, LO DEVO CREARE

				newIndirizzo.setComune(this.getIdComuneModificaResidenza());
				newIndirizzo.setProvincia(String.valueOf(this.getIdProvinciaModificaResidenza()));
				newIndirizzo.setCap(ComuniAnagrafica.getCap(db, this.getIdComuneModificaResidenza()));
				newIndirizzo.setVia(this.getVia());
				newIndirizzo.setIdProvincia(this.getIdProvinciaModificaResidenza());

				// Recupero info asl
				Object[] asl;
				asl = DwrUtil.getValoriAsl(newIndirizzo.getComune());
				if (asl != null && asl.length > 0) {

					Object[] aslVal = (Object[]) asl[0];
					if (aslVal != null && aslVal.length > 0)
						newIndirizzo.setIdAsl((Integer) aslVal[0]);
				}

				newIndirizzo.insert(db);

				idNuovoIndirizzo = newIndirizzo.getIdIndirizzo();

			}

			// Se è privato, sindaco, sindaco FR va aggiornata anche la sede
			// legale che coincide con la residenza

			Stabilimento stab = (Stabilimento) opToModify.getListaStabilimenti().get(0);
			idVecchioIndirizzo = stab.getSedeOperativa().getIdIndirizzo();
			stab.setSedeOperativa(newIndirizzo);
			stab.updateSedeOperativa(db);

			LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
			if (lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazionePrivato
					|| lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindaco
					|| lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindacoFR) {
				newIndirizzo.setTipologiaSede(1);
				opToModify.getListaSediOperatore().add(newIndirizzo);
				opToModify.aggiornaRelazioneSede(db, opToModify.getSedeLegale(), newIndirizzo);
			}

			// Inserimento registrazione
			this.insert(db);

		} catch (Exception e) {
			// TODO: handle exception
		}

		gatto.setIdAslRiferimento(newIndirizzo.getIdAsl());
		return gatto;

	}

	public Animale insert(Connection db, Furetto furetto) {
		// TODO
		//System.out.println("furetto");
		//System.out.println("cane");
		Operatore opToModify = null;
		Indirizzo newIndirizzo = new Indirizzo();
		if (this.getIdTipologiaSoggettoDestinatarioModifica() == modificaProprietario)
			opToModify = furetto.getProprietario();
		else if (this.getIdTipologiaSoggettoDestinatarioModifica() == modificaDetentore)
			opToModify = furetto.getDetentore();

		try {

			if (this.getIdNuovoIndirizzo() > 0) {
				// INDIRIZZO ESISTENTE, LO RECUPERO DAL DB
				newIndirizzo = new Indirizzo(db, this.getIdNuovoIndirizzo());
			}

			else {

				// INDIRIZZO NON ESISTENTE, LO DEVO CREARE

				newIndirizzo.setComune(this.getIdComuneModificaResidenza());
				newIndirizzo.setProvincia(String.valueOf(this.getIdProvinciaModificaResidenza()));
				newIndirizzo.setCap(ComuniAnagrafica.getCap(db, this.getIdComuneModificaResidenza()));
				newIndirizzo.setVia(this.getVia());
				newIndirizzo.setIdProvincia(this.getIdProvinciaModificaResidenza());

				// Recupero info asl
				Object[] asl;
				asl = DwrUtil.getValoriAsl(newIndirizzo.getComune());
				if (asl != null && asl.length > 0) {

					Object[] aslVal = (Object[]) asl[0];
					if (aslVal != null && aslVal.length > 0)
						newIndirizzo.setIdAsl((Integer) aslVal[0]);
				}

				newIndirizzo.insert(db);

				idNuovoIndirizzo = newIndirizzo.getIdIndirizzo();

			}

			// Se è privato, sindaco, sindaco FR va aggiornata anche la sede
			// legale che coincide con la residenza

			Stabilimento stab = (Stabilimento) opToModify.getListaStabilimenti().get(0);
			idVecchioIndirizzo = stab.getSedeOperativa().getIdIndirizzo();
			stab.setSedeOperativa(newIndirizzo);
			stab.updateSedeOperativa(db);

			LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
			if (lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazionePrivato
					|| lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindaco
					|| lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindacoFR) {
				newIndirizzo.setTipologiaSede(1);
				opToModify.getListaSediOperatore().add(newIndirizzo);
				opToModify.aggiornaRelazioneSede(db, opToModify.getSedeLegale(), newIndirizzo);
			}
			// Inserimento registrazione
			this.insert(db);

		} catch (Exception e) {
			// TODO: handle exception
		}

		furetto.setIdAslRiferimento(newIndirizzo.getIdAsl());
		return furetto;

	}

	public Indirizzo getOldIndirizzo() throws UnknownHostException {

		Connection conn = null;
		Indirizzo old = null;
		// String dbName = ApplicationProperties.getProperty("dbnameBdu");
		// String username = ApplicationProperties
		// .getProperty("usernameDbbdu");
		// String pwd = ApplicationProperties.getProperty("passwordDbbdu");
		// String host = InetAddress.getByName("hostDbBdu").getHostAddress();

		try {
			conn = GestoreConnessioni.getConnection();
			// conn = DbUtil.getConnection(dbName, username, pwd, host);
			old = new Indirizzo(conn, this.getIdVecchioIndirizzo());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(conn);
		}

		return old;
	}

	public Indirizzo getNewIndirizzo() throws UnknownHostException {

		Connection conn = null;
		Indirizzo old = null;
		// String dbName = ApplicationProperties.getProperty("dbnameBdu");
		// String username = ApplicationProperties
		// .getProperty("usernameDbbdu");
		// String pwd = ApplicationProperties.getProperty("passwordDbbdu");
		// String host = InetAddress.getByName("hostDbBdu").getHostAddress();

		try { 
		conn = 	GestoreConnessioni.getConnection(); //Modificato 2
			old = new Indirizzo(conn, this.getIdNuovoIndirizzo());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(conn);
		}

		return old;
	}

	public EventoModificaResidenza salvaRegistrazione(int userId, int userRole, int userAsl, Animale thisAnimale,
			Connection db) throws Exception {
		try {

			super.salvaRegistrazione(userId, userRole, userAsl, thisAnimale, db);
			
			Animale oldAnimale = new Animale(db, this.getIdAnimale());

			switch (this.getSpecieAnimaleId()) {
			case Cane.idSpecie:
				thisAnimale = new Cane(db, this.getIdAnimale());
				break;
			case Gatto.idSpecie:
				thisAnimale = new Gatto(db, this.getIdAnimale());
				break;
			case Furetto.idSpecie:
				thisAnimale = new Furetto(db, this.getIdAnimale());
				break;
			default:
				break;
			}

			// CREO REGISTRAZIONE DI MODIFICA RESIDENZA SU OPERATORE E
			// INSERISCO ANCHE REGISTRAZIONE SUL CANE

			RegistrazioneModificaIndirizzoOperatore dettagli_registrazione = new RegistrazioneModificaIndirizzoOperatore();
			dettagli_registrazione.setEnteredby(userId);
			dettagli_registrazione.setModifiedby(userId);
			dettagli_registrazione.setDataModificaResidenza(this.getDataModificaResidenza());
			dettagli_registrazione.setIdAslInserimentoRegistrazione(userAsl);
			dettagli_registrazione
					.setIdTipologiaRegistrazioneOperatore(RegistrazioneModificaIndirizzoOperatore.idTipologia);
			dettagli_registrazione.setIdComuneModificaResidenza(this.getIdComuneModificaResidenza());
			dettagli_registrazione.setIdProvinciaModificaResidenza(this.getIdProvinciaModificaResidenza());
			dettagli_registrazione.setInRegione(this.isInRegione());
			
			if(this.getIdComuneModificaResidenza()>0)
			{
				org.aspcfs.modules.opu_ext.base.ComuniAnagrafica comune = new org.aspcfs.modules.opu_ext.base.ComuniAnagrafica(db, this.getIdComuneModificaResidenza());
				if(comune.getIdAsl()>0)
					dettagli_registrazione.setInRegione(comune.getIdAsl()>=201);
			}
			
			dettagli_registrazione.setVia(this.getVia());
			dettagli_registrazione.setCap(this.getCap());
			dettagli_registrazione.setIdNuovoIndirizzo(this.getIdNuovoIndirizzo());

			// dettagli_registrazione.insert(db);
			Operatore operatoreToModify = new Operatore();

			if (this.getIdTipologiaSoggettoDestinatarioModifica() == EventoModificaResidenza.modificaProprietario) {
				operatoreToModify = thisAnimale.getProprietario();
				dettagli_registrazione.setIdRelazioneStabilimentoLineaProduttiva(thisAnimale.getIdProprietario());
			} else if (this.getIdTipologiaSoggettoDestinatarioModifica() == EventoModificaResidenza.modificaDetentore) {
				operatoreToModify = thisAnimale.getDetentore();
				dettagli_registrazione.setIdRelazioneStabilimentoLineaProduttiva(thisAnimale.getIdProprietario());
			}

			dettagli_registrazione.ModificaOperatore(db, operatoreToModify, userId);

			// Devo rimettere asl per far si che in update su animale nn
			// m resta la vecchia
			thisAnimale.setIdAslRiferimento(dettagli_registrazione.getIdAslDestinataria());

			this.setIdNuovoIndirizzo(dettagli_registrazione.getIdNuovoIndirizzo());
			this.setIdVecchioIndirizzo(dettagli_registrazione.getIdVecchioIndirizzo());

			// Registro la modifica nelle tabelle evento degli animali
			this.insert(db);

			aggiornaFlagFuoriDominioAsl(db, thisAnimale, userAsl, oldAnimale);
			//aggiornaStatoAnimale(db, thisAnimale);

		} catch (Exception e) {
			throw e;
		}

		return this;

	}

	public EventoModificaResidenza build(ResultSet rs) throws Exception {
		try {

			super.build(rs);
			buildRecord(rs);

		} catch (Exception e) {
			throw e;
		}
		return this;
	}

}
