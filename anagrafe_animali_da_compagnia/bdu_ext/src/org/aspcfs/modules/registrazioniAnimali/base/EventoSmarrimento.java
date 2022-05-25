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
import java.util.ArrayList;
import java.util.HashMap;

import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Furetto;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

public class EventoSmarrimento extends Evento {

	public static final int idTipologiaDB = 11;
	private int id = -1;
	private java.sql.Timestamp dataSmarrimento;
	private String luogoSmarrimento;
	private double importoSmarrimento = 0.0;
	private boolean flagPresenzaImportoSmarrimento = false;
	private int idEvento;

	public java.sql.Timestamp getDataSmarrimento() {
		return dataSmarrimento;
	}

	public void setDataSmarrimento(java.sql.Timestamp dataSmarrimento) {
		this.dataSmarrimento = dataSmarrimento;
	}

	public String getLuogoSmarrimento() {
		return luogoSmarrimento;
	}

	public void setLuogoSmarrimento(String luogoSmarrimento) {
		this.luogoSmarrimento = luogoSmarrimento;
	}

	public double getImportoSmarrimento() {
		return importoSmarrimento;
	}

	public void setImportoSmarrimento(double importoSmarrimento) {
		this.importoSmarrimento = importoSmarrimento;
	}

	public void setImportoSmarrimento(String importoSmarrimento) {
		this.importoSmarrimento = new Double(importoSmarrimento);
	}

	public boolean isFlagPresenzaImportoSmarrimento() {
		return flagPresenzaImportoSmarrimento;
	}

	public void setFlagPresenzaImportoSmarrimento(boolean flagPresenzaImportoSmarrimento) {
		this.flagPresenzaImportoSmarrimento = flagPresenzaImportoSmarrimento;
	}

	public void setFlagPresenzaImportoSmarrimento(String flagPresenzaImportoSmarrimento) {
		this.flagPresenzaImportoSmarrimento = DatabaseUtils.parseBoolean(flagPresenzaImportoSmarrimento);
	}

	public static int getIdTipologiaDB() {
		return idTipologiaDB;
	}

	public void setDataSmarrimento(String data) {
		this.dataSmarrimento = DateUtils.parseDateStringNew(data, "dd/MM/yyyy");
	}

	public int getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean insert(Connection db) throws SQLException {
		
		StringBuffer sql = new StringBuffer();
		try {
			
			

			super.insert(db);
			idEvento = super.getIdEvento();

			id = DatabaseUtils.getNextSeq(db, "evento_smarrimento_id_seq");
			// sql.append("INSERT INTO animale (");

			sql.append("INSERT INTO evento_smarrimento(id_evento, data_smarrimento ");

			if (luogoSmarrimento != null && !"".equals(luogoSmarrimento)) {
				sql.append(", luogo_smarrimento");
			}

			if (flagPresenzaImportoSmarrimento && importoSmarrimento != 0.0) {
				sql.append(", importo_smarrimento");
			}

			sql.append(",flag_importo_smarrimento )");

			sql.append("VALUES(?,?");

			if (luogoSmarrimento != null && !"".equals(luogoSmarrimento)) {
				sql.append(",?");
			}

			if (flagPresenzaImportoSmarrimento && importoSmarrimento != 0.0) {
				sql.append(",?");
			}

			sql.append(",?)");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idEvento);

			pst.setTimestamp(++i, dataSmarrimento);

			if (luogoSmarrimento != null && !"".equals(luogoSmarrimento)) {
				pst.setString(++i, luogoSmarrimento);
			}

			if (flagPresenzaImportoSmarrimento && importoSmarrimento != 0.0) {
				pst.setDouble(++i, importoSmarrimento);
			}

			pst.setBoolean(++i, flagPresenzaImportoSmarrimento);

			pst.execute();
			pst.close();

			this.id = DatabaseUtils.getCurrVal(db, "evento_smarrimento_id_seq", id);

			

		} catch (Exception e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return true;

	}

	/*
	 * public static ArrayList getFields(Connection db){
	 * 
	 * ArrayList fields = new ArrayList(); HashMap fields1 = new HashMap();
	 * fields1.put("name", "dataSmarrimento"); fields1.put("type", "data");
	 * fields1.put("label", "Data smarrimento"); fields.add(fields1);
	 * 
	 * 
	 * 
	 * fields1 = new HashMap(); fields1.put("name", "luogoSmarrimento");
	 * fields1.put("type", "text"); fields1.put("label",
	 * "Luogo dello smarrimento"); fields.add(fields1);
	 * 
	 * 
	 * fields1 = new HashMap(); fields1.put("name", "importoSmarrimento");
	 * fields1.put("type", "text"); fields1.put("label", "Sanzione");
	 * fields.add(fields1);
	 * 
	 * 
	 * fields1 = new HashMap(); fields1.put("name",
	 * "flagPresenzaImportoSmarrimento"); fields1.put("type", "checkbox");
	 * fields1.put("label", "Presenza importo smarrimento");
	 * fields.add(fields1);
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * return fields; }
	 */

	public EventoSmarrimento(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	public EventoSmarrimento() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		super.buildRecord(rs);
		this.idEvento = rs.getInt("idevento");
		this.id = rs.getInt("id");
		this.dataSmarrimento = rs.getTimestamp("data_smarrimento");
		this.luogoSmarrimento = rs.getString("luogo_smarrimento");
		this.importoSmarrimento = rs.getDouble("importo_smarrimento");
		this.flagPresenzaImportoSmarrimento = rs.getBoolean("flag_importo_smarrimento");

		// buildSede(rs);
		// buildRappresentanteLegale(rs);

	}

	public EventoSmarrimento(Connection db, int idEventoPadre) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_smarrimento f on (e.id_evento = f.id_evento) where e.id_evento = ?");
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

	public void getEventoSmarrimento(Connection db, int id) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select f.*, e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento  from animale a left join  evento e on  (a.id = e.id_animale) left join evento_smarrimento f on (e.id_evento = f.id_evento) where e.id_evento = ? and e.id_tipologia_evento = ?");
		pst.setInt(1, id);
		pst.setInt(2, idTipologiaDB);
		
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) {
			buildRecord(rs);
		}

		if (id == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		rs.close();
		pst.close();
	}

	public EventoSmarrimento salvaRegistrazione(int userId, int userRole, int userAsl, Animale thisAnimale,
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

			this.insert(db);

			thisAnimale.setFlagSmarrimento(true);

			aggiornaFlagFuoriDominioAsl(db, thisAnimale, userAsl, oldAnimale);
			aggiornaStatoAnimale(db, thisAnimale);

		} catch (Exception e) {
			throw e;
		}

		return this;

	}
	
	public EventoSmarrimento build(ResultSet rs) throws Exception {
		try {

			super.build(rs);
			buildRecord(rs);

		} catch (Exception e) {
			throw e;
		}
		return this;
	}

}
