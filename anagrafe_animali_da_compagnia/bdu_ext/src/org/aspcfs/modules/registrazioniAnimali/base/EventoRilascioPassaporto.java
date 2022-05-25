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
import org.aspcfs.modules.passaporti.base.Passaporto;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.EsitoControllo;
import org.aspcfs.utils.GestoreConnessioni;

public class EventoRilascioPassaporto extends Evento {

	public static final int idTipologiaDB = 6;
	public static final int idTipologiaRinnovoDB = 48;
	// Dati evento rilascio passaporto
	private int id = -1;
	private int idEvento = -1;
	private java.sql.Timestamp dataRilascioPassaporto;
	private java.sql.Timestamp dataScadenzaPassaporto;
	private String numeroPassaporto;
	private boolean flagRinnovo = false;
	private boolean flagPassaportoAttivo = true;
	private boolean flagSmarrimento = false;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFlagSmarrimento(String flagSmarrimento) {
		this.flagSmarrimento = DatabaseUtils.parseBoolean(flagSmarrimento);
	}

	public boolean isFlagSmarrimento() {
		return flagSmarrimento;
	}

	public void setFlagSmarrimento(boolean flagSmarrimento) {
		this.flagSmarrimento = flagSmarrimento;
	}

	public boolean isFlagRinnovo() {
		return flagRinnovo;
	}

	public void setFlagRinnovo(boolean flagRinnovo) {
		this.flagRinnovo = flagRinnovo;
	}

	public boolean isFlagPassaportoAttivo() {
		return flagPassaportoAttivo;
	}

	public void setFlagPassaportoAttivo(boolean flagPassaportoAttivo) {
		this.flagPassaportoAttivo = flagPassaportoAttivo;
	}

	public int getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public java.sql.Timestamp getDataRilascioPassaporto() {
		return dataRilascioPassaporto;
	}

	public void setDataRilascioPassaporto(java.sql.Timestamp dataRilascioPassaporto) {
		this.dataRilascioPassaporto = dataRilascioPassaporto;
	}

	public void setDataRilascioPassaporto(String data) {
		this.dataRilascioPassaporto = DateUtils.parseDateStringNew(data, "dd/MM/yyyy");
	}

	public java.sql.Timestamp getDataScadenzaPassaporto() {
		return dataScadenzaPassaporto;
	}

	public void setDataScadenzaPassaporto(java.sql.Timestamp dataScadenzaPassaporto) {
		this.dataScadenzaPassaporto = dataScadenzaPassaporto;
	}

	public void setDataScadenzaPassaporto(String dataScadenzaPassaporto) {
		this.dataScadenzaPassaporto = DateUtils.parseDateStringNew(dataScadenzaPassaporto, "dd/MM/yyyy");
	}

	public String getNumeroPassaporto() {
		return numeroPassaporto;
	}

	public void setNumeroPassaporto(String numeroPassaporto) {
		this.numeroPassaporto = numeroPassaporto;
	}

	public boolean insert(Connection db) throws SQLException {
		
		StringBuffer sql = new StringBuffer();
		try {
			
			
			

			super.insert(db);
			idEvento = super.getIdEvento();

			id = DatabaseUtils.getNextSeq(db, "evento_rilascio_passaporto_id_seq");
			// sql.append("INSERT INTO animale (");

			sql.append("INSERT INTO evento_rilascio_passaporto( id_evento, data_rilascio_passaporto, flag_smarrimento, flag_rinnovo, flag_passaporto_attuale ");

			if (dataScadenzaPassaporto != null) {
				sql.append(", data_scadenza_passaporto ");
			}

			if (numeroPassaporto != null && !"".equals(numeroPassaporto)) {
				sql.append(",numero_passaporto ");
			}

			sql.append(")VALUES(?, ?, ?, ?, ?");

			if (dataScadenzaPassaporto != null) {
				sql.append(", ? ");
			}

			if (numeroPassaporto != null && !"".equals(numeroPassaporto)) {
				sql.append(",?");
			}

			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idEvento);

			pst.setTimestamp(++i, dataRilascioPassaporto);

			pst.setBoolean(++i, flagSmarrimento);
			pst.setBoolean(++i, flagRinnovo);
			pst.setBoolean(++i, flagPassaportoAttivo);

			if (dataScadenzaPassaporto != null) {
				pst.setTimestamp(++i, dataScadenzaPassaporto);
			}

			if (numeroPassaporto != null && !"".equals(numeroPassaporto)) {
				pst.setString(++i, numeroPassaporto);
			}

			pst.execute();
			pst.close();

			this.id = DatabaseUtils.getCurrVal(db, "evento_rilascio_passaporto_id_seq", id);

			

		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
		
		}
		return true;

	}

	public EventoRilascioPassaporto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EventoRilascioPassaporto(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		super.buildRecord(rs);
		this.idEvento = rs.getInt("idevento");
		this.id = rs.getInt("id");
		this.dataRilascioPassaporto = rs.getTimestamp("data_rilascio_passaporto");
		this.dataScadenzaPassaporto = rs.getTimestamp("data_scadenza_passaporto");
		this.numeroPassaporto = rs.getString("numero_passaporto");
		this.flagPassaportoAttivo = rs.getBoolean("flag_passaporto_attuale");

		this.flagRinnovo = rs.getBoolean("flag_rinnovo");
		this.flagSmarrimento = rs.getBoolean("flag_smarrimento");

	}

	/*
	 * public static ArrayList getFields(){
	 * 
	 * ArrayList fields = new ArrayList(); HashMap fields1 = new HashMap();
	 * fields1.put("name", "dataRilascioPassaporto"); fields1.put("type",
	 * "data"); fields1.put("label", "Data rilascio passaporto");
	 * fields.add(fields1);
	 * 
	 * 
	 * 
	 * fields1 = new HashMap(); fields1.put("name", "numeroPassaporto");
	 * fields1.put("type", "text"); fields1.put("label", "Numero passaporto");
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

	public EventoRilascioPassaporto(Connection db, int idEventoPadre) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_rilascio_passaporto f on (e.id_evento = f.id_evento) where e.id_evento = ?");
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

	public void GetPassaportoAttivoByIdAnimale(Connection db, int idAnimale) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_rilascio_passaporto f on (e.id_evento = f.id_evento) where e.id_animale = ? and f.flag_passaporto_attuale = true");
		pst.setInt(1, idAnimale);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) {
			buildRecord(rs);
		}

		if (idAnimale == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		rs.close();
		pst.close();

	}

	public int updateRegistrazione(Connection conn) {
		int result = -1;

		try {
			StringBuffer sql = new StringBuffer();

			sql.append("update evento_rilascio_passaporto set flag_passaporto_attuale = ? where id = ?");
			PreparedStatement pst = conn.prepareStatement(sql.toString());

			int i = 0;
			pst.setBoolean(++i, this.flagPassaportoAttivo);
			pst.setInt(++i, this.getId());

			result = pst.executeUpdate();
			pst.close();

		} catch (Exception e) {
			System.out.println(e);
		}

		return result;
	}

	public int update(Connection conn) throws SQLException {
		try {
			super.update(conn);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int result = -1;
		try {
			
			StringBuffer sql = new StringBuffer();

			sql.append("update evento_rilascio_passaporto set numero_passaporto = ?, data_rilascio_passaporto = ?, data_scadenza_passaporto = ? where id_evento = ?");
			PreparedStatement pst = conn.prepareStatement(sql.toString());

			int i = 0;
			pst.setString(++i, this.numeroPassaporto);
			pst.setTimestamp(++i, this.dataRilascioPassaporto);
			pst.setTimestamp(++i, this.dataScadenzaPassaporto);
			pst.setInt(++i, this.getIdEvento());

			result = pst.executeUpdate();
			pst.close();
			
		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		}

		return result;
	}

	public EventoRilascioPassaporto salvaRegistrazione(int userId, int userRole, int userAsl, Animale thisAnimale,
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

			if (this.getIdTipologiaEvento() == EventoRilascioPassaporto.idTipologiaRinnovoDB) {

				// SETTO COME NON ATTIVA ULTIMA REGISTRAZIONE DI RILASCIO
				// PASSAPORTO O RICONOSCIMENTO PASSAPORTO
				EventoRilascioPassaporto oldRegistrazionePassaporto = new EventoRilascioPassaporto();
				oldRegistrazionePassaporto.GetPassaportoAttivoByIdAnimale(db, thisAnimale.getIdAnimale());
				if (oldRegistrazionePassaporto.getIdEvento() > 0){
					oldRegistrazionePassaporto.setFlagPassaportoAttivo(false);
					oldRegistrazionePassaporto.updateRegistrazione(db);
				}else{
					EventoRiconoscimentoPassaporto riconoscimento = new EventoRiconoscimentoPassaporto();
					riconoscimento.GetPassaportoAttivoByIdAnimale(db, thisAnimale.getIdAnimale());
					if (riconoscimento.getIdEvento() > 0){
						riconoscimento.setFlagPassaportoAttivo(false);
						riconoscimento.updateRegistrazione(db);
					}
				}

				this.setFlagRinnovo(true);

			}
			this.insert(db);
			thisAnimale.setDataRilascioPassaporto(this.getDataRilascioPassaporto());
			thisAnimale.setNumeroPassaporto(this.getNumeroPassaporto());
			thisAnimale.setDataScadenzaPassaporto(this.getDataScadenzaPassaporto());
			
			//Aggiorno BANCA DATI APRIORI PASSAPORTI
			
			Passaporto thisPassaporto = new Passaporto();
			
			thisPassaporto = thisPassaporto.load(this.getNumeroPassaporto(), db);
			if (thisPassaporto != null && thisPassaporto.getId() > 0){
			thisPassaporto.setIdUtenteUtilizzo(this.getEnteredby()); 
			thisPassaporto.setIdAnimale(this.getIdAnimale());
			thisPassaporto.setIdSpecie(this.getSpecieAnimaleId());
			thisPassaporto.setPassaportoUtilizzato(db);
			}

			aggiornaFlagFuoriDominioAsl(db, thisAnimale, userAsl, oldAnimale);
			aggiornaStatoAnimale(db, thisAnimale);

		} catch (Exception e) {
			throw e;
		}

		return this;

	}
	
	public EventoRilascioPassaporto build(ResultSet rs) throws Exception {
		try {

			super.build(rs);
			buildRecord(rs);

		} catch (Exception e) {
			throw e;
		}
		return this;
	}
	
	//Flusso 238
	public static EsitoControllo checkValorePassaporto(String valore, int idAsl, int idAnimale,Integer idCanile, Integer idStabilimentoGisa)
	{
		EsitoControllo controllo = new EsitoControllo();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pst = null;

		try {
			conn = GestoreConnessioni.getConnection();
			
			
	
				pst = conn
						.prepareStatement("select * from public_functions.check_passaporto('"
								+ valore + "'," + idAsl + ", "+ idAnimale +",?,?)");
				
				if(idCanile==null)
					pst.setObject(1, null);
				else
					pst.setInt(1, idCanile);
				
				if(idStabilimentoGisa==null)
					pst.setObject(2, null);
				else
					pst.setInt(2, idStabilimentoGisa);
				rs = pst.executeQuery();
				
				if (rs.next()) {
					controllo.setIdEsito(rs.getInt(1));
					controllo.setDescrizione(rs.getString(2));
				}


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(conn);
			// DbUtil.chiudiConnessioneJDBC(rs, pst, conn);

		}

	
		return controllo;
	}
	
	
	
	
	/*
	public static EsitoControllo checkValorePassaporto(String valore, int idAsl, int idAnimale)
	{
		EsitoControllo controllo = new EsitoControllo();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pst = null;

		try {
			conn = GestoreConnessioni.getConnection();

			pst = conn
					.prepareStatement("select * from public_functions.check_passaporto('"
							+ valore + "'," + idAsl + ", "+ idAnimale +")");
			
			rs = pst.executeQuery();
			
			if (rs.next()) {
				controllo.setIdEsito(rs.getInt(1));
				controllo.setDescrizione(rs.getString(2));
			}


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(conn);
			// DbUtil.chiudiConnessioneJDBC(rs, pst, conn);

		}

	
		return controllo;
	}
	 * */
}
