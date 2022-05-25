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
import java.util.ArrayList;
import java.util.HashMap;

import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Furetto;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.DbUtil;
import org.aspcfs.utils.GestoreConnessioni;
//import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.LookupList;

public class EventoRitrovamento extends Evento {

	public static final int idTipologiaDB = 12;

	private int id = -1;
	private java.sql.Timestamp dataRitrovamento;
	private int idComuneRitrovamento;
	private String luogoRitrovamento;
	private int idProprietario = -1;
	private int idDetentore = -1;
	private int idProprietarioOld = -1;
	private int idDetentoreOld = -1;
	private int idEvento;
	private boolean flagAperto = false;

	public final static int proprietarioOld = 1;
	public final static int proprietarioNew = 2;
	public final static int detentoreOld = 3;
	public final static int detentoreNew = 4;

	public EventoRitrovamento() {
		super();
		// TODO Auto-generated constructor stub
	}

	public java.sql.Timestamp getDataRitrovamento() {
		return dataRitrovamento;
	}

	public void setDataRitrovamento(java.sql.Timestamp dataRitrovamento) {
		this.dataRitrovamento = dataRitrovamento;
	}

	public void setDataRitrovamento(String dataRitrovamento) {
		this.dataRitrovamento = DateUtils.parseDateStringNew(dataRitrovamento, "dd/MM/yyyy");
	}

	public String getLuogoRitrovamento() {
		return luogoRitrovamento;
	}

	public void setLuogoRitrovamento(String luogoRitrovamento) {
		this.luogoRitrovamento = luogoRitrovamento;
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

	public int getIdDetentore() {
		return idDetentore;
	}

	public void setIdDetentore(int idDetentore) {
		this.idDetentore = idDetentore;
	}

	public void setIdDetentore(String idDetentore) {
		this.idDetentore = new Integer(idDetentore).intValue();
	}

	public int getIdProprietarioOld() {
		return idProprietarioOld;
	}

	public void setIdProprietarioOld(int idProprietarioOld) {
		this.idProprietarioOld = idProprietarioOld;
	}

	public void setIdProprietarioOld(String idProprietarioOld) {
		this.idProprietarioOld = new Integer(idProprietarioOld).intValue();
	}

	public int getIdDetentoreOld() {
		return idDetentoreOld;
	}

	public void setIdDetentoreOld(int idDetentoreOld) {
		this.idDetentoreOld = idDetentoreOld;
	}

	public void setIdDetentoreOld(String idDetentoreOld) {
		this.idDetentoreOld = new Integer(idDetentoreOld).intValue();
	}

	public int getIdComuneRitrovamento() {
		return idComuneRitrovamento;
	}

	public void setIdComuneRitrovamento(int idComuneRitrovamento) {
		this.idComuneRitrovamento = idComuneRitrovamento;
	}

	public void setIdComuneRitrovamento(String idComuneRitrovamento) {
		this.idComuneRitrovamento = new Integer(idComuneRitrovamento).intValue();
	}

	public void setFlagAperto(boolean flagAperto) {
		this.flagAperto = flagAperto;
	}

	public boolean isFlagAperto() {
		return flagAperto;
	}

	public static int getIdTipologiaDB() {
		return idTipologiaDB;
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

			id = DatabaseUtils.getNextSeq(db, "evento_ritrovamento_id_seq");
			// sql.append("INSERT INTO animale (");

			sql.append("INSERT INTO evento_ritrovamento(id_evento, data_ritrovamento, flag_aperto ");

			if (luogoRitrovamento != null && !"".equals(luogoRitrovamento)) {
				sql.append(", luogo_ritrovamento");
			}

			if (idComuneRitrovamento > -1) {
				sql.append(", comune_ritrovamento");
			}

			if (idProprietario > -1)
				sql.append(",id_proprietario_dopo_ritrovamento ");

			if (idDetentore > -1) {
				sql.append(",id_detentore_dopo_ritrovamento ");
			}

			if (idProprietarioOld > -1)
				sql.append(",id_proprietario_old ");

			if (idDetentoreOld > -1) {
				sql.append(",id_detentore_old ");
			}

			sql.append(")");

			sql.append("VALUES(?,?,?");

			if (luogoRitrovamento != null && !"".equals(luogoRitrovamento)) {
				sql.append(",?");
			}

			if (idComuneRitrovamento > -1) {
				sql.append(",?");
			}

			if (idProprietario > -1) {
				sql.append(",?");
			}

			if (idDetentore > -1) {
				sql.append(",?");
			}

			if (idProprietarioOld > -1)
				sql.append(",?");

			if (idDetentoreOld > -1) {
				sql.append(",?");
			}

			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idEvento);

			pst.setTimestamp(++i, dataRitrovamento);
			pst.setBoolean(++i, flagAperto);

			if (luogoRitrovamento != null && !"".equals(luogoRitrovamento)) {
				pst.setString(++i, luogoRitrovamento);
			}

			if (idComuneRitrovamento > -1) {
				pst.setInt(++i, idComuneRitrovamento);
			}

			if (idProprietario > -1) {
				pst.setInt(++i, idProprietario);
			}

			if (idDetentore > -1) {
				pst.setInt(++i, idDetentore);
			}

			if (idProprietarioOld > -1) {
				pst.setInt(++i, idProprietarioOld);
			}

			if (idDetentoreOld > -1) {
				pst.setInt(++i, idDetentoreOld);
			}

			pst.execute();
			pst.close();

			this.id = DatabaseUtils.getCurrVal(db, "evento_ritrovamento_id_seq", id);


		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
		
		}
		return true;

	}

	/*
	 * 
	 * public static ArrayList getFields(Connection db){
	 * 
	 * 
	 * 
	 * 
	 * ArrayList fields = new ArrayList(); HashMap fields1 = new HashMap();
	 * 
	 * 
	 * 
	 * String html = ""; try { ComuniAnagrafica c = new ComuniAnagrafica();
	 * ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, -1, -1);
	 * LookupList comuniList = new LookupList(listaComuni, -1);
	 * comuniList.addItem(-1, "--Seleziona--"); html =
	 * comuniList.getHtmlSelect("idComuneRitrovamento", -1); } catch
	 * (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * fields1 = new HashMap(); fields1.put("name", "idComuneRitrovamento");
	 * fields1.put("type", "select"); fields1.put("label",
	 * "Comune ritrovamento"); fields1.put("html", html); fields.add(fields1);
	 * 
	 * 
	 * fields1 = new HashMap(); fields1.put("name", "dataRitrovamento");
	 * fields1.put("type", "data"); fields1.put("label", "Data ritrovamento");
	 * fields.add(fields1);
	 * 
	 * 
	 * 
	 * fields1 = new HashMap(); fields1.put("name", "luogoRitrovamento");
	 * fields1.put("type", "text"); fields1.put("label",
	 * "Luogo del ritrovamento"); fields.add(fields1);
	 * 
	 * 
	 * fields1 = new HashMap(); fields1.put("name", "idNuovoProprietario");
	 * fields1.put("type", "hidden"); fields1.put("label", "");
	 * fields.add(fields1);
	 * 
	 * 
	 * fields1 = new HashMap(); fields1.put("name", "idNuovoDetentore");
	 * fields1.put("type", "hidden"); fields1.put("label", "");
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

	public EventoRitrovamento(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		super.buildRecord(rs);
		this.idEvento = rs.getInt("idevento");
		this.id = rs.getInt("id");
		try
		{
			this.dataRitrovamento = rs.getTimestamp("data_ritrovamento_specifico");
		}
		catch(Exception e)
		{
			this.dataRitrovamento = rs.getTimestamp("data_ritrovamento");
		}
		this.idComuneRitrovamento = rs.getInt("comune_ritrovamento");
		this.luogoRitrovamento = rs.getString("luogo_ritrovamento");
		this.idDetentore = rs.getInt("id_detentore_dopo_ritrovamento");
		this.idProprietario = rs.getInt("id_proprietario_dopo_ritrovamento");
		this.idDetentoreOld = rs.getInt("id_detentore_old");
		this.idProprietarioOld = rs.getInt("id_proprietario_old");
		this.flagAperto = rs.getBoolean("flag_aperto");

		// buildSede(rs);
		// buildRappresentanteLegale(rs);

	}

	public EventoRitrovamento(Connection db, int idEventoPadre) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_ritrovamento f on (e.id_evento = f.id_evento) where e.id_evento = ?");
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

	public Operatore getIdProprietario(int tipoSoggetto) throws UnknownHostException {
		Connection conn = null;
		int idOperatore = -1;
		Operatore operatore = null;
		try {
			conn = GestoreConnessioni.getConnection();
			// String dbName = ApplicationProperties.getProperty("dbnameBdu");
			// String username =
			// ApplicationProperties.getProperty("usernameDbbdu");
			// String pwd = ApplicationProperties.getProperty("passwordDbbdu");
			// String host =
			// InetAddress.getByName("hostDbBdu").getHostAddress();
			//
			// conn = DbUtil.getConnection(dbName, username, pwd, host);

			switch (tipoSoggetto) {

			case proprietarioNew: {
				idOperatore = this.getIdProprietario();
				break;

			}
			case proprietarioOld: {
				idOperatore = this.getIdProprietarioOld();
				break;

			}
			case detentoreNew: {
				idOperatore = this.getIdDetentore();
				break;

			}
			case detentoreOld: {
				idOperatore = this.getIdDetentoreOld();
				break;

			}

			default: {
				idOperatore = -1;
				break;
			}
			}

			if (idOperatore != -1 && idOperatore != 0) {
				
				operatore = new Operatore();
				operatore.queryRecordOperatorebyIdLineaProduttiva(conn, idOperatore);
			}

			// GestoreConnessioni.freeConnection(conn);

			// DbUtil.chiudiConnessioneJDBC(null, null, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(conn);
		}

		return operatore;
	}

	public void GetRitrovamentoApertoByIdAnimale(Connection db, int idAnimale) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("SELECT *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento FROM evento_ritrovamento rit left join evento e on e.id_evento = rit.id_evento where e.id_animale = ? AND rit.flag_aperto=true AND e.data_cancellazione is null ");
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

	public void updateFlagAperto(Connection con) throws SQLException {
		
		PreparedStatement pst;
		try {
			pst = con.prepareStatement("Update evento_ritrovamento set flag_aperto = " + flagAperto
					+ " where id_evento = " + this.getIdEvento());

			int resultCount = pst.executeUpdate();
			pst.close();
			
		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		}
	}

	public EventoRitrovamento salvaRegistrazione(int userId, int userRole, int userAsl, Animale thisAnimale,
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

			EventoRestituzioneAProprietario restituzione = null;
			// recupero vecchio animale per prendere detentore vecchio
			// // Animale oldAnimale = new Animale(db, dettagli_base
			// .getIdAnimale());
			// Mi conservo l'informazione sul vecchio proprietario
			if (oldAnimale.getProprietario() != null) {
				this.setIdProprietarioOld(oldAnimale.getIdProprietario());
			}

			if (this.getIdProprietario() > 0) {
				thisAnimale.setIdProprietario(this.getIdProprietario());
			} else {
				this.setIdProprietario(oldAnimale.getIdProprietario());
			}
			if (oldAnimale.getDetentore() != null && oldAnimale.getDetentore().getIdOperatore() > 0)
				this.setIdDetentoreOld(oldAnimale.getIdDetentore());

			// Detentore
			if (this.getIdDetentore() > 0) { // Ho scelto
				// un nuovo
				// detentore, di tipo canile

				Operatore detentore = new Operatore();
				detentore.queryRecordOperatorebyIdLineaProduttiva(db, this.getIdDetentore());
				Stabilimento stab = (Stabilimento) detentore.getListaStabilimenti().get(0);
				LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
				if (lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile
				// && !thisAnimale.isRandagio(db)
				) {
					thisAnimale.setFlagDetenutoInCanileDopoRitrovamento(true);
				}

				thisAnimale.setIdDetentore(this.getIdDetentore());
				this.setFlagAperto(true);
			}
			// else if (ritrovamento.getIdProprietario() > 0){
			// ritrovamento
			// .setIdDetentore(ritrovamento.getIdProprietario());
			// thisAnimale.setIdDetentore(ritrovamento.getIdDetentore());
			// }
			else {
				this.setIdDetentore(oldAnimale.getIdDetentore());

				// CREO UNA REGISTRAZIONE AUTOMATICA DI RITORNO A
				// PROPRIETARIO NEL CASO IN CUI IL DETENTORE NON VIENE
				// MODIFICATO

				restituzione = new EventoRestituzioneAProprietario();
				restituzione.setEnteredby(userId);
				restituzione.setModifiedby(userId);
				restituzione.setIdAnimale(this.getIdAnimale());
				restituzione.setMicrochip(this.getMicrochip());
				restituzione.setIdProprietarioCorrente(this.getIdProprietarioCorrente());
				restituzione.setIdDetentoreCorrente(this.getIdDetentoreCorrente());
				restituzione.setSpecieAnimaleId(this.getSpecieAnimaleId());
				restituzione.setIdAslRiferimento(this.getIdAslRiferimento());
				restituzione.setIdStatoOriginale(this.getIdStatoOriginale());

				// restituzione = (EventoRestituzioneAProprietario) context
				// .getRequest().getAttribute(
				// "EventoRestituzioneAProprietario"); // GIA'
				// // POPOLATO
				// // CON
				// // I
				// // DETTAGLI
				// // BASE
				restituzione.setIdTipologiaEvento(EventoRestituzioneAProprietario.idTipologiaDB);
				restituzione.setIdDetentore(oldAnimale.getIdDetentore());
				restituzione.setDataRestituzione(this.getDataRitrovamento());
				restituzione.setIdVecchioDetentore(oldAnimale.getIdDetentore());
				this.setFlagFuoriDominioAsl(false); // ELIMINO FUORI DOMINIO ASL
													// IN QUANTO SI TRATTA DI
													// RESTITUZIONE CONTESTUALE
			}

			// ritrovamento.setIdDetentoreOld(oldAnimale.getId)
			this.insert(db);

			if (restituzione != null) {
				restituzione.setIdEventoRitrovamento(this.getIdEvento());
				restituzione.setIdTipologiaEventoRitrovamento(this.idTipologiaDB);
				restituzione.setFlagContestualeRitrovamento(true);
				restituzione.insert(db);
			}

			thisAnimale.setFlagSmarrimento(false);
			thisAnimale.setFlagFurto(false);

			aggiornaFlagFuoriDominioAsl(db, thisAnimale, userAsl, oldAnimale);
			aggiornaStatoAnimale(db, thisAnimale);

		} catch (Exception e) {
			throw e;
		}

		return this;

	}
	
	
	public EventoRitrovamento build(ResultSet rs) throws Exception {
		try {

			super.build(rs);
			buildRecord(rs);

		} catch (Exception e) {
			throw e;
		}
		return this;
	}

}
