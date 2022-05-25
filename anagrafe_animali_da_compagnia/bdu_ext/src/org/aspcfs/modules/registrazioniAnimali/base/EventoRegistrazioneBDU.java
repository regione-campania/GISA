/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrazioniAnimali.base;

import java.sql.*;

import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

public class EventoRegistrazioneBDU extends Evento {

	public static final int idTipologiaDB = 1;
	public static final int idTipologiaRegistrazioneAFuoriRegione = 49; //Sto registrando direttamente un animale a un proprietario e detentore fuori regione

	private java.sql.Timestamp dataRegistrazione;
	private int idProprietario = -1;
	private int idProprietarioProvenienza = -1;
	private int idDetentore = -1;
	private int id;
	private int idEvento;
	private int idStato;
	
	private String  provenienza_origine="";
	
	private String  regione_ritrovamento="";
	private String  provincia_ritrovamento="";
	private String  comune_ritrovamento="";
	private String  indirizzo_ritrovamento="";
	private String  data_ritrovamento="";
	
	private boolean flag_anagrafe_estera=false;
	private String  nazione_estera="";
	private String  nazione_estera_note="";
	
	private boolean  flag_anagrafe_fr=false;
	private String  regione_anagrafe_fr="";
	private String  regione_anagrafe_fr_note="";
	
	private boolean  flag_acquisto_online=false;
	private String  sito_web_acquisto="";
	private String  sito_web_acquisto_note="";

	
	public int getIdProprietarioProvenienza() {
		return idProprietarioProvenienza;
	}

	public void setIdProprietarioProvenienza(int idProprietarioProvenienza) {
		this.idProprietarioProvenienza = idProprietarioProvenienza;
	}
	
	public boolean isFlag_anagrafe_estera() {
		return flag_anagrafe_estera;
	}

	public void setFlag_anagrafe_estera(boolean flag_anagrafe_estera) {
		this.flag_anagrafe_estera = flag_anagrafe_estera;
	}

	public String getNazione_estera() {
		return nazione_estera;
	}

	public void setNazione_estera(String nazione_estera) {
		this.nazione_estera = nazione_estera;
	}

	public String getNazione_estera_note() {
		return nazione_estera_note;
	}

	public void setNazione_estera_note(String nazione_estera_note) {
		this.nazione_estera_note = nazione_estera_note;
	}
	
	public boolean isFlag_acquisto_online() {
		return flag_acquisto_online;
	}

	public void setFlag_acquisto_online(boolean flag_acquisto_online) {
		this.flag_acquisto_online = flag_acquisto_online;
	}

	public String getProvenienza_origine() {
		return provenienza_origine;
	}

	public void setProvenienza_origine(String provenienza_origine) {
		this.provenienza_origine = provenienza_origine;
	}
	
	public String getSito_web_acquisto_note() {
		return sito_web_acquisto_note;
	}

	public void setSito_web_acquisto_note(String sito_web_acquisto_note) {
		this.sito_web_acquisto_note = sito_web_acquisto_note;
	}
	
	public String getSito_web_acquisto() {
		return sito_web_acquisto;
	}

	public void setSito_web_acquisto(String sito_web_acquisto) {
		this.sito_web_acquisto = sito_web_acquisto;
	}


	public String getRegione_anagrafe_fr_note() {
		return regione_anagrafe_fr_note;
	}

	public void setRegione_anagrafe_fr_note(String regione_anagrafe_fr_note) {
		this.regione_anagrafe_fr_note = regione_anagrafe_fr_note;
	}

	public boolean isFlag_anagrafe_fr() {
		return flag_anagrafe_fr;
	}

	public void setFlag_anagrafe_fr(boolean flag_anagrafe_fr) {
		this.flag_anagrafe_fr = flag_anagrafe_fr;
	}

	public String getRegione_anagrafe_fr() {
		return regione_anagrafe_fr;
	}

	public void setRegione_anagrafe_fr(String regione_anagrafe_fr) {
		this.regione_anagrafe_fr = regione_anagrafe_fr;
	}


	
	public String getRegione_ritrovamento() {
		return regione_ritrovamento;
	}

	public void setRegione_ritrovamento(String regione_ritrovamento) {
		this.regione_ritrovamento = regione_ritrovamento;
	}

	public String getProvincia_ritrovamento() {
		return provincia_ritrovamento;
	}

	public void setProvincia_ritrovamento(String provincia_ritrovamento) {
		this.provincia_ritrovamento = provincia_ritrovamento;
	}

	public String getComune_ritrovamento() {
		return comune_ritrovamento;
	}

	public void setComune_ritrovamento(String comune_ritrovamento) {
		this.comune_ritrovamento = comune_ritrovamento;
	}

	public String getIndirizzo_ritrovamento() {
		return indirizzo_ritrovamento;
	}

	public void setIndirizzo_ritrovamento(String indirizzo_ritrovamento) {
		this.indirizzo_ritrovamento = indirizzo_ritrovamento;
	}

	public String getData_ritrovamento() {
		return data_ritrovamento;
	}

	public void setData_ritrovamento(String data_ritrovamento) {
		this.data_ritrovamento = data_ritrovamento;
	}



	private boolean flagAttivitaItinerante = false;
	private int idComuneAttivitaItinerante = -1;
	private String luogoAttivitaItinerante = "";
	private Timestamp dataAttivitaItinerante = null;
	
	
	/**
	 * @return the data_evento
	 */

	public void setDataRegistrazione(String dataRegistrazione) {
		this.dataRegistrazione = DateUtils.parseDateStringNew(
				dataRegistrazione, "dd/MM/yyyy");
	}

	public java.sql.Timestamp getDataRegistrazione() {
		return dataRegistrazione;
	}

	public void setDataRegistrazione(java.sql.Timestamp dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	public EventoRegistrazioneBDU() {
		super();
		//this.setIdTipologiaEvento(idT)
		// TODO Auto-generated constructor stub
	}

	public EventoRegistrazioneBDU(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}
	

	public int getIdProprietario() {
		return idProprietario;
	}

	public void setIdProprietario(int idProprietario) {
		this.idProprietario = idProprietario;
	}
	
	public void setIdProprietario(String idProprietario) {
		this.idProprietario = new Integer (idProprietario).intValue();
	}

	public int getIdDetentore() {
		return idDetentore;
	}

	public void setIdDetentore(int idDetentore) {
		this.idDetentore = idDetentore;
	}
	
	public void setIdDetentore(String idDetentore) {
		this.idDetentore = new Integer(idDetentore).intValue();
	}
	
	
	

	public boolean isFlagAttivitaItinerante() {
		return flagAttivitaItinerante;
	}

	public void setFlagAttivitaItinerante(boolean flagAttivitaItinerante) {
		this.flagAttivitaItinerante = flagAttivitaItinerante;
	}
	

	
	public void setFlagAttivitaItinerante(String flagAttivitaItinerante) {
		this.flagAttivitaItinerante = DatabaseUtils
				.parseBoolean(flagAttivitaItinerante);
	}

	public int getIdComuneAttivitaItinerante() {
		return idComuneAttivitaItinerante;
	}

	public void setIdComuneAttivitaItinerante(int idComuneAttivitaItinerante) {
		this.idComuneAttivitaItinerante = idComuneAttivitaItinerante;
	}
	
	public void setIdComuneAttivitaItinerante(String idComuneAttivitaItinerante) {
		this.idComuneAttivitaItinerante = Integer.valueOf(idComuneAttivitaItinerante);
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

	
	protected void buildRecord(ResultSet rs) throws SQLException {

		super.buildRecord(rs);
		this.idEvento = rs.getInt("idevento");
		this.dataRegistrazione = rs.getTimestamp("data_registrazione");
		this.idProprietario = rs.getInt("id_proprietario");
		this.idDetentore = rs.getInt("id_detentore_registrazione_bdu");
		this.idStato= rs.getInt("id_stato");
		this.idComuneAttivitaItinerante = rs.getInt("id_comune_attivita_itinerante");
		this.flagAttivitaItinerante = rs.getBoolean("flag_attivita_itinerante");
		this.dataAttivitaItinerante = rs.getTimestamp("data_attivita_itinerante");
		this.luogoAttivitaItinerante = rs.getString("luogo_attivita_itinerante");

		this.idProprietarioProvenienza = rs.getInt("id_proprietario_provenienza");
		this.regione_ritrovamento = rs.getString("regione_ritrovamento");

		this.comune_ritrovamento = rs.getString("comune_ritrovamento");
		this.provincia_ritrovamento = rs.getString("provincia_ritrovamento");
		this.indirizzo_ritrovamento = rs.getString("indirizzo_ritrovamento");
		this.data_ritrovamento = rs.getString("data_ritrovamento");
		
		this.flag_anagrafe_fr=rs.getBoolean("flag_anagrafe_fr");
		this.regione_anagrafe_fr=rs.getString("regione_anagrafe_fr");
		this.regione_anagrafe_fr_note=rs.getString("regione_anagrafe_fr_note");
		

		this.flag_anagrafe_estera=rs.getBoolean("flag_anagrafe_estera");
		this.nazione_estera=rs.getString("nazione_estera");
		this.nazione_estera_note=rs.getString("nazione_estera_note");

		this.flag_acquisto_online=rs.getBoolean("flag_acquisto_online");
		this.sito_web_acquisto=rs.getString("sito_web_acquisto");
		this.sito_web_acquisto_note=rs.getString("sito_web_acquisto_note");
		
		this.provenienza_origine=rs.getString("provenienza_origine");
		// buildSede(rs);
		// buildRappresentanteLegale(rs);

	}

	protected void buildRecord(ResultSet rs, Connection db) throws SQLException {

		super.buildRecord(rs);
		this.idEvento = rs.getInt("idevento");
		this.dataRegistrazione = rs.getTimestamp("data_registrazione");
		this.idProprietario = rs.getInt("id_proprietario");
		this.idDetentore = rs.getInt("id_detentore_registrazione_bdu");
		this.idStato= rs.getInt("id_stato");
		this.idComuneAttivitaItinerante = rs.getInt("id_comune_attivita_itinerante");
		this.flagAttivitaItinerante = rs.getBoolean("flag_attivita_itinerante");
		this.dataAttivitaItinerante = rs.getTimestamp("data_attivita_itinerante");
		this.luogoAttivitaItinerante = rs.getString("luogo_attivita_itinerante");

		this.idProprietarioProvenienza = rs.getInt("id_proprietario_provenienza");
		this.regione_ritrovamento = rs.getString("regione_ritrovamento");
		
		if (rs.getString("comune_ritrovamento")!=null && !rs.getString("comune_ritrovamento").equals("")){
			PreparedStatement pst = db
					.prepareStatement("	select c.nome, l.description from comuni1 c join lookup_province l on l.code::integer=c.cod_provincia::integer where c.id = ?");
			pst.setInt(1, Integer.parseInt(rs.getString("comune_ritrovamento")));
			ResultSet rs1 = DatabaseUtils.executeQuery(db, pst);
			if (rs1.next()) {
				this.comune_ritrovamento = rs.getString(1);
				this.provincia_ritrovamento = rs.getString(2);
			}
		}else{
		  this.comune_ritrovamento = rs.getString("comune_ritrovamento");
		  this.provincia_ritrovamento = rs.getString("provincia_ritrovamento");
		}
		this.indirizzo_ritrovamento = rs.getString("indirizzo_ritrovamento");
		this.data_ritrovamento = rs.getString("data_ritrovamento");
		
		this.flag_anagrafe_fr=rs.getBoolean("flag_anagrafe_fr");
		if (rs.getString("regione_anagrafe_fr")!=null && !rs.getString("regione_anagrafe_fr").equals("")){
			PreparedStatement pst = db
					.prepareStatement("	select description from lookup_regioni where code = ?");
			pst.setInt(1, Integer.parseInt(rs.getString("regione_anagrafe_fr")));
			ResultSet rs1 = DatabaseUtils.executeQuery(db, pst);
			if (rs1.next()) {
				this.regione_anagrafe_fr = rs.getString(1);
			}
		}else{
		  this.regione_anagrafe_fr=rs.getString("regione_anagrafe_fr");
		}
		
		this.flag_anagrafe_estera=rs.getBoolean("flag_anagrafe_estera");
		if (rs.getString("nazione_estera")!=null && !rs.getString("nazione_estera").equals("")){
			PreparedStatement pst = db
					.prepareStatement("	select description from lookup_nazioni where code = ?");
			pst.setInt(1, Integer.parseInt(rs.getString("nazione_estera")));
			ResultSet rs1 = DatabaseUtils.executeQuery(db, pst);
			if (rs1.next()) {
				this.nazione_estera = rs.getString(1);
			}
		}else{
		  this.nazione_estera=rs.getString("nazione_estera");
		}
		this.nazione_estera_note=rs.getString("nazione_estera_note");

		
		
		
		// buildSede(rs);
		// buildRappresentanteLegale(rs);

	}

	
	
	
	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {
		
			super.insert(db);
			idEvento = super.getIdEvento();
			

			id = DatabaseUtils
					.getNextSeq(db, "evento_registrazione_BDU_id_seq");
			// sql.append("INSERT INTO animale (");

			sql
					.append("INSERT INTO evento_registrazione_BDU(id_evento, data_registrazione, id_proprietario, id_detentore_registrazione_bdu,"
							+ "flag_attivita_itinerante, id_comune_attivita_itinerante, luogo_attivita_itinerante, data_attivita_itinerante,"
							+ "id_proprietario_provenienza, regione_ritrovamento, provincia_ritrovamento, comune_ritrovamento, indirizzo_ritrovamento, "
							+ "data_ritrovamento, flag_anagrafe_fr, regione_anagrafe_fr, regione_anagrafe_fr_note,flag_anagrafe_estera, nazione_estera, nazione_estera_note, "
							+ "flag_acquisto_online, sito_web_acquisto, sito_web_acquisto_note, provenienza_origine) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idEvento);
			pst.setTimestamp(++i, dataRegistrazione);
			pst.setInt(++i, idProprietario);
			pst.setInt(++i, idDetentore);
			pst.setBoolean(++i, flagAttivitaItinerante);
			pst.setInt(++i, idComuneAttivitaItinerante);
			pst.setString(++i, luogoAttivitaItinerante);
			pst.setTimestamp(++i, dataAttivitaItinerante);
			
			pst.setInt(++i, idProprietarioProvenienza);
			pst.setString(++i, regione_ritrovamento);
			pst.setString(++i, provincia_ritrovamento);
			pst.setString(++i, comune_ritrovamento);
			pst.setString(++i, indirizzo_ritrovamento);
			pst.setString(++i, data_ritrovamento);
		
			pst.setBoolean(++i, flag_anagrafe_fr);
			pst.setString(++i, regione_anagrafe_fr);
			pst.setString(++i, regione_anagrafe_fr_note);

			pst.setBoolean(++i, flag_anagrafe_estera);
			pst.setString(++i, nazione_estera);
			pst.setString(++i, nazione_estera_note);

			pst.setBoolean(++i, flag_acquisto_online);
			pst.setString(++i, sito_web_acquisto);
			pst.setString(++i, sito_web_acquisto_note);
			
			pst.setString(++i, provenienza_origine);
			
			pst.execute();
			pst.close();

			this.id = DatabaseUtils.getCurrVal(db,
					"evento_registrazione_BDU_id_seq", id);

		} catch (SQLException e) {
	
			throw new SQLException(e.getMessage());
		} finally {
		
		}
		return true;

	}

	public int getTipologiaDB(Gatto animale, Connection db) {

		switch (animale.getIdSpecie()) {
		case Cane.idSpecie: {
			return idTipologiaDB;

		}

		case (Gatto.idSpecie): {
			// Se detentore è colonia deve essere registrato come randagio,
			// l'equivalente per un cane di una registrazione con cattura
			try {
				
				Operatore proprietario = new Operatore();
				proprietario.queryRecordOperatorebyIdLineaProduttiva(db, animale.getIdProprietario());
				Stabilimento stab = (Stabilimento) proprietario
						.getListaStabilimenti().get(0);
				LineaProduttiva linea = (LineaProduttiva) stab
						.getListaLineeProduttive().get(0);
				
				Operatore det = new Operatore();
				det.queryRecordOperatorebyIdLineaProduttiva(db, animale.getIdDetentore());
				Stabilimento stabDet = (Stabilimento) det.getListaStabilimenti().get(0);
				LineaProduttiva lpDet = (LineaProduttiva) stabDet.getListaLineeProduttive().get(0);
				if (linea.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneColonia 
						|| (linea.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindaco && lpDet.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile) ||
						(linea.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindaco && lpDet.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneColonia)
						|| (linea.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindaco && lpDet.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile)) {
					return 30; // "Registrazione gatto detentore colonia"
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		default:
			return idTipologiaDB;
		}

	}
	
	public EventoRegistrazioneBDU(Connection db, int idEventoPadre) throws SQLException {

	//	super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_registrazione_bdu f on (e.id_evento = f.id_evento) where e.id_evento = ?");
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
	
	
	public static  EventoRegistrazioneBDU getEventoRegistrazione(Connection db, int idAnimale) throws SQLException {

		//	super(db, idEventoPadre);
		
		    EventoRegistrazioneBDU reg = new EventoRegistrazioneBDU();

			PreparedStatement pst = db
					.prepareStatement("Select f.*, e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento  from animale a left join  evento e on  (a.id = e.id_animale) left join evento_registrazione_bdu f on (e.id_evento = f.id_evento) where a.id = ? and e.id_tipologia_evento = 1");
			pst.setInt(1, idAnimale);
			ResultSet rs = DatabaseUtils.executeQuery(db, pst);
			if (rs.next()) {
				reg.buildRecord(rs);
			}


			if (idAnimale == -1) {
				throw new SQLException(Constants.NOT_FOUND_ERROR);
			}

			rs.close();
			pst.close();
			
			return reg;
		}

	public void setIdStato(int idStato) {
		this.idStato = idStato;
	}

	public int getIdStato() {
		return idStato;
	}
	
	
	
	public EventoRegistrazioneBDU updateOrigine(Connection db) throws SQLException{
		


		StringBuffer sql = new StringBuffer();
		try {

		super.update(db);

			sql.append("UPDATE evento_registrazione_bdu "+
				" SET id_proprietario_provenienza = ?, regione_ritrovamento = ?, provincia_ritrovamento = ?, comune_ritrovamento = ?, "
				+ "indirizzo_ritrovamento = ?, data_ritrovamento = ?, flag_anagrafe_fr = ?, regione_anagrafe_fr = ?, "
				+ "regione_anagrafe_fr_note = ?,flag_anagrafe_estera = ?, nazione_estera = ?, nazione_estera_note = ?, "
			   	+ "flag_acquisto_online = ?, sito_web_acquisto = ?, sito_web_acquisto_note = ?, provenienza_origine = ? where id_evento= ?");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idProprietarioProvenienza);
			pst.setString(++i, regione_ritrovamento);
			pst.setString(++i, provincia_ritrovamento);
			pst.setString(++i, comune_ritrovamento);
			pst.setString(++i, indirizzo_ritrovamento);
			pst.setString(++i, data_ritrovamento);
		
			pst.setBoolean(++i, flag_anagrafe_fr);
			pst.setString(++i, regione_anagrafe_fr);
			pst.setString(++i, regione_anagrafe_fr_note);

			pst.setBoolean(++i, flag_anagrafe_estera);
			pst.setString(++i, nazione_estera);
			pst.setString(++i, nazione_estera_note);

			pst.setBoolean(++i, flag_acquisto_online);
			pst.setString(++i, sito_web_acquisto);
			pst.setString(++i, sito_web_acquisto_note);
			
			pst.setString(++i, provenienza_origine);
 
			pst.setInt(++i, idEvento);
		
			pst.executeUpdate();
			pst.close();


		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
		
		}
		return this;

		
	}

	
	

	public boolean updateStato(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {

			idEvento = super.getIdEvento();
			

			id = DatabaseUtils
					.getNextSeq(db, "evento_registrazione_BDU_id_seq");
			// sql.append("INSERT INTO animale (");

			sql
					.append("UPDATE evento_registrazione_BDU set id_stato = ? where id_evento=?");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idStato);
			pst.setInt(++i, idEvento);
		
			pst.executeUpdate();
			pst.close();

			

		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return true;

	}
	
	
	public EventoRegistrazioneBDU updateDatiProprietarioDetentore(Connection db) throws SQLException{
		


		StringBuffer sql = new StringBuffer();
		try {

		super.update(db);

			sql
					.append("UPDATE evento_registrazione_bdu "+
							  " SET id_proprietario = ?, id_detentore_registrazione_bdu = ? " +
								      " where id_evento= ?");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idProprietario);
			pst.setInt(++i, idDetentore);
			pst.setInt(++i, idEvento);
		
			pst.executeUpdate();
			pst.close();


		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
		
		}
		return this;

		
	}
	
	public void setValoriDefaultOrigine(){
		this.idProprietarioProvenienza = -1;
		this.provenienza_origine="";
		
		this.regione_ritrovamento="";
		this.provincia_ritrovamento="";
		this.comune_ritrovamento="";
		this.indirizzo_ritrovamento="";
		this.data_ritrovamento="";
		
		this.flag_anagrafe_estera=false;
		this.nazione_estera="";
		this.nazione_estera_note="";
		
		this.flag_anagrafe_fr=false;
		this.regione_anagrafe_fr="";
		this.regione_anagrafe_fr_note="";
		
		this.flag_acquisto_online=false;
		this.sito_web_acquisto="";
		this.sito_web_acquisto_note="";
	}
	
	public EventoRegistrazioneBDU build(ResultSet rs) throws Exception {
		try {

			super.build(rs);
			buildRecord(rs);

		} catch (Exception e) {
			throw e;
		}
		return this;
	}
	
}
