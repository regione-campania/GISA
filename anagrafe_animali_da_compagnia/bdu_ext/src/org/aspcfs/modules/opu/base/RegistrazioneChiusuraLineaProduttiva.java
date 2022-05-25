/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

public class RegistrazioneChiusuraLineaProduttiva extends RegistrazioneOperatore {

	public static final int idTipologia = 4;
	private int id = -1;
	private int idRegistrazioneOperatore = -1;
	private java.sql.Timestamp dataChiusura;
	private int idLineaProduttiva = -1;
	private String motivazioneChiusura = "";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdRegistrazioneOperatore() {
		return idRegistrazioneOperatore;
	}

	public void setIdRegistrazioneOperatore(int idRegistrazioneOperatore) {
		this.idRegistrazioneOperatore = idRegistrazioneOperatore;
	}

	public java.sql.Timestamp getDataChiusura() {
		return dataChiusura;
	}

	public void setDataChiusura(java.sql.Timestamp dataChiusura) {
		this.dataChiusura = dataChiusura;
	}

	public int getIdLineaProduttiva() {
		return idLineaProduttiva;
	}

	public void setIdLineaProduttiva(int idLineaProduttiva) {
		this.idLineaProduttiva = idLineaProduttiva;
	}

	public void setDataChiusura(String dataChiusura) {
		this.dataChiusura = DateUtils.parseDateStringNew(dataChiusura, "dd/MM/yyyy");
	}

	public String getMotivazioneChiusura() {
		return motivazioneChiusura;
	}

	public void setMotivazioneChiusura(String motivazioneChiusura) {
		this.motivazioneChiusura = motivazioneChiusura;
	}

	public RegistrazioneChiusuraLineaProduttiva() {

		super();
		super.setIdTipologiaRegistrazioneOperatore(idTipologia);
	}

	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();

		Operatore thisOperatore = new Operatore();

		thisOperatore.queryRecordOperatorebyIdLineaProduttiva(db, this.getIdLineaProduttiva());
		Stabilimento stab = (Stabilimento) thisOperatore.getListaStabilimenti().get(0);
		LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
		
		super.setIdTipologiaOperatore(lp.getIdRelazioneAttivita());

		super.insert(db);
		idRegistrazioneOperatore = super.getIdRegistrazione();

		id = DatabaseUtils.getNextSeq(db, "evento_chiusura_linea_produtti_id_chiusura_linea_produttiva_seq");
		// sql.append("INSERT INTO animale (");

		sql.append(" INSERT INTO evento_chiusura_linea_produttiva"
				+ " (data_chiusura,  id_registrazione, motivazione_chiusura ");

		sql.append(")VALUES (?, ?, ?");

		sql.append(" )");
		int i = 0;
		PreparedStatement pst = db.prepareStatement(sql.toString());

		pst.setTimestamp(++i, dataChiusura);
		pst.setInt(++i, this.getIdRegistrazioneOperatore());
		pst.setString(++i, this.motivazioneChiusura);

		pst.execute();
		pst.close();

		this.id = DatabaseUtils.getCurrVal(db, "evento_chiusura_linea_produtti_id_chiusura_linea_produttiva_seq", id);

		lp.setDataFine(dataChiusura);
		lp.chiudi(db);

		/**
		 * Aggiornamento vista dematerializzata
		 */

		thisOperatore.aggiornaVistaMaterializzata(db);

		return true;

	}

	public RegistrazioneChiusuraLineaProduttiva(ResultSet rs) throws SQLException {
		buildRecord(rs, null);
	}

	public RegistrazioneChiusuraLineaProduttiva(ResultSet rs, Connection db) throws SQLException {
		buildRecord(rs, db);
	}

	protected void buildRecord(ResultSet rs, Connection db) throws SQLException {

		super.buildRecord(rs, null);

		this.dataChiusura = rs.getTimestamp("data_chiusura");
		this.id = rs.getInt("id_chiusura_linea_produttiva");
		this.idRegistrazioneOperatore = rs.getInt("id_registrazione");
		this.motivazioneChiusura = rs.getString("motivazione_chiusura");

		// buildSede(rs);
		// buildRappresentanteLegale(rs);

	}

	public RegistrazioneChiusuraLineaProduttiva(Connection db, int idEventoPadre) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db.prepareStatement("Select * from evento_chiusura_linea_produttiva f  where f.id = ?");
		pst.setInt(1, id);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) {
			buildRecord(rs, db);
		}

		if (idEventoPadre == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		rs.close();
		pst.close();
	}

}
