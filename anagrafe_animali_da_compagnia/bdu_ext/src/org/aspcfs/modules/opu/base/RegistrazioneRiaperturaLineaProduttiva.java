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
import java.text.ParseException;
import java.util.Iterator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;

public class RegistrazioneRiaperturaLineaProduttiva extends RegistrazioneOperatore {

	public static final int idTipologia = 5;
	private int id = -1;
	private int idRegistrazioneOperatore = -1;
	private java.sql.Timestamp dataApertura;
	private int idLineaProduttiva = -1;
	private String motivazioneRiapertura = "";

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

	public java.sql.Timestamp getDataApertura() {
		return dataApertura;
	}

	public void setDataApertura(java.sql.Timestamp dataChiusura) {
		this.dataApertura = dataChiusura;
	}

	public int getIdLineaProduttiva() {
		return idLineaProduttiva;
	}

	public void setIdLineaProduttiva(int idLineaProduttiva) {
		this.idLineaProduttiva = idLineaProduttiva;
	}

	public void setDataApertura(String dataChiusura) {
		this.dataApertura = DateUtils.parseDateStringNew(dataChiusura, "dd/MM/yyyy");
	}

	public String getMotivazioneRiapertura() {
		return motivazioneRiapertura;
	}

	public void setMotivazioneRiapertura(String motivazioneRiapertura) {
		this.motivazioneRiapertura = motivazioneRiapertura;
	}

	public RegistrazioneRiaperturaLineaProduttiva() {

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

		id = DatabaseUtils.getNextSeq(db, "evento_apertura_linea_produtti_id_apertura_linea_produttiva_seq");
		// sql.append("INSERT INTO animale (");

		sql.append(" INSERT INTO evento_apertura_linea_produttiva"
				+ " (data_apertura,  id_registrazione, motivazione_riapertura ");

		sql.append(")VALUES (?, ?, ?");

		sql.append(" )");
		int i = 0;
		PreparedStatement pst = db.prepareStatement(sql.toString());

		pst.setTimestamp(++i, dataApertura);
		pst.setInt(++i, this.getIdRegistrazioneOperatore());
		pst.setString(++i, this.motivazioneRiapertura);

		pst.execute();
		pst.close();

		this.id = DatabaseUtils.getCurrVal(db, "evento_apertura_linea_produtti_id_apertura_linea_produttiva_seq", id);

		try {
			lp.setDataFine((String) null);
		} catch (Exception e) {

		}
		lp.riapri(db);

		/**
		 * Aggiornamento vista dematerializzata
		 */
		thisOperatore.aggiornaVistaMaterializzata(db);

		return true;

	}

	public RegistrazioneRiaperturaLineaProduttiva(ResultSet rs) throws SQLException {
		buildRecord(rs, null);
	}

	public RegistrazioneRiaperturaLineaProduttiva(ResultSet rs, Connection db) throws SQLException {
		buildRecord(rs, db);
	}

	protected void buildRecord(ResultSet rs, Connection db) throws SQLException {

		super.buildRecord(rs, null);

		this.dataApertura = rs.getTimestamp("data_apertura");
		this.id = rs.getInt("id_apertura_linea_produttiva");
		this.idRegistrazioneOperatore = rs.getInt("id_registrazione");
		this.motivazioneRiapertura = rs.getString("motivazione_riapertura");

		// buildSede(rs);
		// buildRappresentanteLegale(rs);

	}

	public RegistrazioneRiaperturaLineaProduttiva(Connection db, int idEventoPadre) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db.prepareStatement("Select * from evento_apertura_linea_produttiva f  where f.id = ?");
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
