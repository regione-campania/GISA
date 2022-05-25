/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrazioniAnimali.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Furetto;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.partitecommerciali_ext.base.PartitaCommerciale;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.LookupList;

public class EventoInserimentoVaccinazioni extends Evento {

	// table evento_inserimento_vaccino

	public static final int idTipologiaDB = 36;

	public static final int antirabbica = 1;
	public static final int leishmaniosi = 2;

	private int id = -1;
	private int idEvento = -1;
	private java.sql.Timestamp dataVaccinazione;
	private int idTipoVaccino = -1; // Lookup lookup_tipologia_vaccino
	private int idTipologiaVaccinoInoculato = -1;
	private String numeroLottoVaccino;
	private String nomeVaccino = "";
	private String produttoreVaccino = "";
	private java.sql.Timestamp dataScadenzaVaccino;
	private String veterinarioEsecutoreNonAccreditato = "";
	private int idVeterinarioEsecutoreAccreditato = -1;
	
	
	
	

	public EventoInserimentoVaccinazioni() {
		super();
		super.setIdTipologiaEvento(idTipologiaDB);
		// TODO Auto-generated constructor stub
	}

	
	
	public String getProduttoreVaccino() {
		return produttoreVaccino;
	}



	public void setProduttoreVaccino(String produttoreVaccino) {
		this.produttoreVaccino = produttoreVaccino;
	}



	public java.sql.Timestamp getDataScadenzaVaccino() {
		return dataScadenzaVaccino;
	}



	public void setDataScadenzaVaccino(java.sql.Timestamp dataScadenzaVaccino) {
		this.dataScadenzaVaccino = dataScadenzaVaccino;
	}

	
	public void setDataScadenzaVaccino(String dataScadenzaVaccino) {
		this.dataScadenzaVaccino = DateUtils.parseDateStringNew(dataScadenzaVaccino,
		"dd/MM/yyyy");;
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

	public java.sql.Timestamp getDataVaccinazione() {
		return dataVaccinazione;
	}

	public void setDataVaccinazione(java.sql.Timestamp dataVaccinazione) {
		this.dataVaccinazione = dataVaccinazione;
	}

	public void setDataVaccinazione(String dataVaccinazione) {
		this.dataVaccinazione = DateUtils.parseDateStringNew(dataVaccinazione,
				"dd/MM/yyyy");
	}

	public String getNomeVaccino() {
		return nomeVaccino;
	}

	public void setNomeVaccino(String nomeVaccino) {
		this.nomeVaccino = nomeVaccino;
	}

	public int getIdTipoVaccino() {
		return idTipoVaccino;
	}

	public void setIdTipoVaccino(int idTipoVaccino) {
		this.idTipoVaccino = idTipoVaccino;
	}
	
	public int getIdTipologiaVaccinoInoculato() {
		return idTipologiaVaccinoInoculato;
	}

	public void setIdTipologiaVaccinoInoculato(int idTipologiaVaccinoInoculato) {
		this.idTipologiaVaccinoInoculato = idTipologiaVaccinoInoculato;
	}
	

	public void setIdTipoVaccino(String idTipoVaccino) {
		this.idTipoVaccino = new Integer(idTipoVaccino).intValue();
	}
	
	public void setIdTipologiaVaccinoInoculato(String idTipologiaVaccinoInoculato) {
		if (idTipologiaVaccinoInoculato!=null){
			this.idTipologiaVaccinoInoculato = new Integer(idTipologiaVaccinoInoculato).intValue();
		}
	}

	public String getNumeroLottoVaccino() {
		return numeroLottoVaccino;
	}

	public void setNumeroLottoVaccino(String numeroLottoVaccino) {
		this.numeroLottoVaccino = numeroLottoVaccino;
	}
	
	
	

	public String getVeterinarioEsecutoreNonAccreditato() {
		return veterinarioEsecutoreNonAccreditato;
	}



	public void setVeterinarioEsecutoreNonAccreditato(
			String veterinarioEsecutoreNonAccreditato) {
		this.veterinarioEsecutoreNonAccreditato = veterinarioEsecutoreNonAccreditato;
	}







	public int getIdVeterinarioEsecutoreAccreditato() {
		return idVeterinarioEsecutoreAccreditato;
	}



	public void setIdVeterinarioEsecutoreAccreditato(
			int idVeterinarioEsecutoreAccreditato) {
		this.idVeterinarioEsecutoreAccreditato = idVeterinarioEsecutoreAccreditato;
	}
	
	public void setIdVeterinarioEsecutoreAccreditato(
			String idVeterinarioEsecutoreAccreditato) {
		this.idVeterinarioEsecutoreAccreditato = new Integer(idVeterinarioEsecutoreAccreditato);
	}
	
	
	public String getVeterinarioEsecutore(){
			Connection conn = null;
			String veterinario_nome_cognome = "";
			try{
				
			if (this.getVeterinarioEsecutoreNonAccreditato() != null && !("").equals(this.getVeterinarioEsecutoreNonAccreditato())){
				veterinario_nome_cognome = this.getVeterinarioEsecutoreNonAccreditato();
			}else{
				 conn = GestoreConnessioni.getConnection();
				 LookupList lista_veterinari = new LookupList(conn, "elenco_veterinari");
				 veterinario_nome_cognome = lista_veterinari.getSelectedValue(this.getIdVeterinarioEsecutoreAccreditato());
			}
				}catch (Exception e) {
				// TODO: handle exception
				}finally{
					if (conn != null)
						GestoreConnessioni.freeConnection(conn);
			}
				return veterinario_nome_cognome;
	}



	public boolean insert(Connection db) throws SQLException {
		
		StringBuffer sql = new StringBuffer();
		try {
		
			super.insert(db);
			idEvento = super.getIdEvento();

			id = DatabaseUtils.getNextSeq(db,
					"evento_inserimento_vaccino_id_seq");
			// sql.append("INSERT INTO animale (");

			sql
					.append(" INSERT INTO evento_inserimento_vaccino (id_evento, data_inserimento_vaccinazione ");

			if (idTipoVaccino != -1) {
				sql.append(", id_tipo_vaccino");
			}
			
			if (idTipologiaVaccinoInoculato != -1) {
				sql.append(", id_tipologia_vaccino_inoculato");
			}

			if (numeroLottoVaccino != null && !("").equals(numeroLottoVaccino)) {
				sql.append(",numero_lotto_vaccino");
			}

			if (nomeVaccino != null && !("").equals(nomeVaccino)) {
				sql.append(", nome_vaccino");
			}
			
			if (dataScadenzaVaccino != null){
				sql.append(", data_scadenza_vaccino");
			}
			
			if (produttoreVaccino != null && !("").equals(produttoreVaccino)){
				sql.append(", produttore_vaccino");
			}
			
			if (idVeterinarioEsecutoreAccreditato > 0){
				sql.append(", id_veterinario_accreditato");
			}
			
			if (veterinarioEsecutoreNonAccreditato != null && !("").equals(veterinarioEsecutoreNonAccreditato)){
				sql.append(", veterinario_non_accreditato");
			}

			sql.append(")VALUES (?,?");

			if (idTipoVaccino != -1) {
				sql.append(", ?");
			}
			
			if (idTipologiaVaccinoInoculato != -1) {
				sql.append(", ?");
			}

			if (numeroLottoVaccino != null && !("").equals(numeroLottoVaccino)) {
				sql.append(", ?");
			}

			if (nomeVaccino != null && !("").equals(nomeVaccino)) {
				sql.append(", ?");
			}
			
			if (dataScadenzaVaccino != null){
				sql.append(", ?");
			}
			
			if (produttoreVaccino != null && !("").equals(produttoreVaccino)){
				sql.append(", ?");
			}
			
			if (idVeterinarioEsecutoreAccreditato > 0){
				sql.append(", ?");
			}
			
			if (veterinarioEsecutoreNonAccreditato != null && !("").equals(veterinarioEsecutoreNonAccreditato)){
				sql.append(", ?");
			}

			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idEvento);

			pst.setTimestamp(++i, dataVaccinazione);

			if (idTipoVaccino != -1) {
				pst.setInt(++i, idTipoVaccino);
			}
			
			if (idTipologiaVaccinoInoculato != -1) {
				pst.setInt(++i, idTipologiaVaccinoInoculato);
			}

			if (numeroLottoVaccino != null && !("").equals(numeroLottoVaccino)) {
				pst.setString(++i, numeroLottoVaccino);
			}

			if (nomeVaccino != null && !("").equals(nomeVaccino)) {
				pst.setString(++i, nomeVaccino);
			}
			
			if (dataScadenzaVaccino != null){
				pst.setTimestamp(++i, dataScadenzaVaccino);
			}
			
			if (produttoreVaccino != null && !("").equals(produttoreVaccino)){
				pst.setString(++i, produttoreVaccino);
			}
			
			if (idVeterinarioEsecutoreAccreditato > 0){
				pst.setInt(++i, idVeterinarioEsecutoreAccreditato);
			}
			
			if (veterinarioEsecutoreNonAccreditato != null && !("").equals(veterinarioEsecutoreNonAccreditato)){
				pst.setString(++i, veterinarioEsecutoreNonAccreditato);
			}

			pst.execute();
			pst.close();

			this.id = DatabaseUtils.getCurrVal(db,
					"evento_inserimento_vaccino_id_seq", id);

		
		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
		
		}
		return true;

	}

	public EventoInserimentoVaccinazioni(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		super.buildRecord(rs);
		this.idEvento = rs.getInt("idevento");
		this.dataVaccinazione = rs
				.getTimestamp("data_inserimento_vaccinazione");
		this.idTipoVaccino = rs.getInt("id_tipo_vaccino");
		this.idTipologiaVaccinoInoculato = rs.getInt("id_tipologia_vaccino_inoculato");
		this.numeroLottoVaccino = rs.getString("numero_lotto_vaccino");
		this.nomeVaccino = rs.getString("nome_vaccino");
		this.dataScadenzaVaccino = rs.getTimestamp("data_scadenza_vaccino");
		this.produttoreVaccino = rs.getString("produttore_vaccino");
		this.idVeterinarioEsecutoreAccreditato = rs.getInt("id_veterinario_accreditato");
		this.veterinarioEsecutoreNonAccreditato = rs.getString("veterinario_non_accreditato");

		// buildSede(rs);
		// buildRappresentanteLegale(rs);

	}

	public EventoInserimentoVaccinazioni(Connection db, int idEventoPadre)
			throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_inserimento_vaccino f on (e.id_evento = f.id_evento) where e.id_evento = ?");
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

	public static EventoInserimentoVaccinazioni getUltimaVaccinazioneDaTipo(
			Connection db, String microchip, String tatuaggio, int tipologiaRegistrazione)
			throws SQLException {
		EventoInserimentoVaccinazioni vaccinazione = new EventoInserimentoVaccinazioni();

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_inserimento_vaccino f on (e.id_evento = f.id_evento)"
						+ " where (e.microchip = ? or e.microchip = ?) and e.id_tipologia_evento  = ? and e.trashed_date is null and e.data_cancellazione is null and f.id_tipo_vaccino = ? and data_inserimento_vaccinazione ="
						+ "(Select max(data_inserimento_vaccinazione)  from evento e left join evento_inserimento_vaccino f on (e.id_evento = f.id_evento) where (e.microchip = ? or e.microchip = ? ) and e.trashed_date is null and e.data_cancellazione is null and e.id_tipologia_evento  = ? and f.id_tipo_vaccino = ?  ) limit 1");

		int i = 0;
		pst.setString(++i, microchip);
		pst.setString(++i, tatuaggio);
		pst.setInt(++i, idTipologiaDB);
		pst.setInt(++i, tipologiaRegistrazione);

		pst.setString(++i, microchip);
		pst.setString(++i, tatuaggio);
		pst.setInt(++i, idTipologiaDB);
		pst.setInt(++i, tipologiaRegistrazione);
	//	System.out.println(pst.toString());
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) {
			vaccinazione.buildRecord(rs);
		}

		rs.close();
		pst.close();

		return vaccinazione;
	}

	
	public static EventoInserimentoVaccinazioni getUltimaVaccinazioneDaTipo(
			Connection db, int idAnimale, int tipologiaRegistrazione)
			throws SQLException {
		EventoInserimentoVaccinazioni vaccinazione = new EventoInserimentoVaccinazioni();

		//Vecchia query
		/*PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_inserimento_vaccino f on (e.id_evento = f.id_evento)"
						+ " where e.id_animale = ? and e.id_tipologia_evento  = ? and e.trashed_date is null and e.data_cancellazione is null and f.id_tipo_vaccino = ? and data_inserimento_vaccinazione ="
						+ "(Select max(data_inserimento_vaccinazione)  from evento e left join evento_inserimento_vaccino f on (e.id_evento = f.id_evento) where e.id_animale = ? and e.trashed_date is null and e.data_cancellazione is null and e.id_tipologia_evento  = ?  and f.id_tipo_vaccino = ? ) limit 1");
		*/
		
		PreparedStatement pst = db
				.prepareStatement("WITH CTE AS ( " +
				" Select max(data_inserimento_vaccinazione) as max_date   " +
				" from evento e " +
				" left join evento_inserimento_vaccino f on (e.id_evento = f.id_evento) " +
				" where e.id_animale = ?  " +
				" and e.trashed_date is null and e.data_cancellazione is null and e.id_tipologia_evento  = ?  " +
				"  and f.id_tipo_vaccino =?  " +
				" ) " +
				" Select distinct on (data_inserimento_vaccinazione) data_inserimento_vaccinazione, *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento " +
				" from evento e left join evento_inserimento_vaccino f on (e.id_evento = f.id_evento) " +
				" where e.id_animale = ?  " +
				" and e.id_tipologia_evento  = ?  " +
				" and e.trashed_date is null " +
				" and e.data_cancellazione is null and f.id_tipo_vaccino = ?  " +
				" and data_inserimento_vaccinazione = (select * from CTE)");
		
		
		int i = 0;
		pst.setInt(++i, idAnimale);
		pst.setInt(++i, idTipologiaDB);
		pst.setInt(++i, tipologiaRegistrazione);
		
		pst.setInt(++i, idAnimale);
		pst.setInt(++i, idTipologiaDB);
		pst.setInt(++i, tipologiaRegistrazione);
		
		System.out.println(pst.toString());
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) {
			vaccinazione.buildRecord(rs);
		}

		rs.close();
		pst.close();

		return vaccinazione;
	}

	
	public EventoInserimentoVaccinazioni salvaRegistrazione(int userId,
			int userRole, int userAsl, Animale thisAnimale, Connection db)
			throws Exception {
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


			thisAnimale.setDataVaccino(this
					.getDataVaccinazione());
			thisAnimale.setNumeroLottoVaccino(this
					.getNumeroLottoVaccino());

			this.insert(db);

		
			aggiornaFlagFuoriDominioAsl(db, thisAnimale,  userAsl,  oldAnimale);
			aggiornaStatoAnimale(db, thisAnimale);

		} catch (Exception e) {
			throw e;
		}

		return this;

	}
	
	public EventoInserimentoVaccinazioni build(ResultSet rs) throws Exception{
		try{	
			
			super.build(rs);
			buildRecord(rs);
		
		}catch (Exception e){
			throw e;
		}
	return this;
		}
}
