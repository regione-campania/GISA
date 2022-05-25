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
import java.sql.Timestamp;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

public class EventoRestituzioneAslOrigine extends Evento {

	public static final int idTipologiaDB = 55;

	private int id = -1;
	private int idEvento = -1;
	private Timestamp dataRestituzioneAslOrigine;
	private String noteRestituzione = "";
	private int idCanileOrigine = -1;

	public EventoRestituzioneAslOrigine() {
		super();
		// TODO Auto-generated constructor stub
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

	public Timestamp getDataRestituzioneAslOrigine() {
		return dataRestituzioneAslOrigine;
	}

	public void setDataRestituzioneAslOrigine(Timestamp dataRestituzioneAslOrigine) {
		this.dataRestituzioneAslOrigine = dataRestituzioneAslOrigine;
	}

	public void setDataRestituzioneAslOrigine(String dataRestituzioneAslOrigine) {
		this.dataRestituzioneAslOrigine = DateUtils.parseDateStringNew(dataRestituzioneAslOrigine, "dd/MM/yyyy");
	}

	public String getNoteRestituzione() {
		return noteRestituzione;
	}

	public void setNoteRestituzione(String noteRestituzione) {
		this.noteRestituzione = noteRestituzione;
	}

	public int getIdCanileOrigine() {
		return idCanileOrigine;
	}

	public void setIdCanileOrigine(int idCanileOrigine) {
		this.idCanileOrigine = idCanileOrigine;
	}

	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {

		
	    	  
			super.insert(db);
			idEvento = super.getIdEvento();

			

			id = DatabaseUtils.getNextSeq(db, "evento_restituzione_asl_origine_id_seq");

			sql.append("INSERT INTO evento_restituzione_asl_origine(id_evento, data_restituzione_asl, note_restituzione, id_canile_da ");

			sql.append(")VALUES(?,?,?,?");

			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idEvento);
			pst.setTimestamp(++i, dataRestituzioneAslOrigine);
			pst.setString(++i, noteRestituzione);
			pst.setInt(++i, idCanileOrigine);

			pst.execute();
			pst.close();

			this.id = DatabaseUtils.getCurrVal(db, "evento_restituzione_asl_origine_id_seq", id);

			

		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}

		return true;

	}

	public EventoRestituzioneAslOrigine(ResultSet rs)
	// TODO Auto-generated constructor stub
			throws SQLException {
		buildRecord(rs);
	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		super.buildRecord(rs);
		this.idEvento = rs.getInt("idevento");
		this.dataRestituzioneAslOrigine = rs.getTimestamp("data_restituzione_asl");
		this.noteRestituzione = rs.getString("note_restituzione");
		this.idCanileOrigine = rs.getInt("id_canile_da");

	}

	public EventoRestituzioneAslOrigine(Connection db, int idEventoPadre) throws SQLException {

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e "
						+ "left join evento_restituzione_asl_origine f on (e.id_evento = f.id_evento) where e.id_evento = ?");
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

	public EventoRestituzioneAslOrigine build(ResultSet rs) throws Exception {
		try {

			super.build(rs);
			buildRecord(rs);

		} catch (Exception e) {
			throw e;
		}
		return this;
	}

}
