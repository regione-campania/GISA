/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Furetto;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.actions.OperatoreAction;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.DbUtil;
import org.aspcfs.utils.EsitoControllo;
import org.aspcfs.utils.GestoreConnessioni;

import com.darkhorseventures.framework.beans.GenericBean;

public class LineaProduttiva extends GenericBean {

	public final static int idAttivitaCanile = 4; // da
													// opu_lookup_attivita_linee_produttive_aggregazioni
	public final static int idAggregazioneCanile = 5; // da
														// opu_relazione_attivita_produttive_aggregazioni

	public final static int idAggregazioneSindaco = 3; // da
														// opu_relazione_attivita_produttive_aggregazioni
	public final static int idAttivitaSindaco = 2; // da
													// opu_lookup_attivita_linee_produttive_aggregazioni

	public final static int idAttivitaOperatoreCommerciale = 5; // da
																// opu_lookup_attivita_linee_produttive_aggregazioni
	public final static int IdAggregazioneOperatoreCommerciale = 6;// da
																	// opu_relazione_attivita_produttive_aggregazioni

	public final static int idAttivitaImportatore = 3; // da
														// opu_lookup_attivita_linee_produttive_aggregazioni
	public final static int idAggregazioneImportatore = 4; // da
															// opu_relazione_attivita_produttive_aggregazioni

	public final static int idAttivitaColonia = 7; // da
													// opu_lookup_attivita_linee_produttive_aggregazioni
	public final static int idAggregazioneColonia = 8; // da
														// opu_relazione_attivita_produttive_aggregazioni

	public final static int idAttivitaSindacoFR = 6; // da
														// opu_lookup_attivita_linee_produttive_aggregazioni
	public final static int idAggregazioneSindacoFR = 7; // da
															// opu_relazione_attivita_produttive_aggregazioni

	public final static int idAttivitaPrivato = 1; // da
													// opu_lookup_attivita_linee_produttive_aggregazioni
	public final static int idAggregazionePrivato = 1; // da
														// opu_relazione_attivita_produttive_aggregazioni

	public LineaProduttiva() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String categoria;
	private String attivita;
	private String macrocategoria;
	private int idMacroarea;
	private int idCategoria;
	private int idAttivita;
	private int id;
	private Timestamp dataInizio;
	private Timestamp dataFine;
	private int stato;
	private String autorizzazione;
	private int idRelazioneAttivita;
	private String telefono1;
	private String telefono2;
	private String mail1;
	private String mail2;
	private String fax;

	private String noteInternalUseOnly = "";

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	public String getMail1() {
		return mail1;
	}

	public void setMail1(String mail1) {
		this.mail1 = mail1;
	}

	public String getMail2() {
		return mail2;
	}

	public void setMail2(String mail2) {
		this.mail2 = mail2;
	}

	public int getIdRelazioneAttivita() {
		return idRelazioneAttivita;
	}

	public void setIdRelazioneAttivita(int idRelazioneAttivita) {
		this.idRelazioneAttivita = idRelazioneAttivita;
	}

	public void setIdRelazioneAttivita(String idRelazioneAttivita) {
		this.idRelazioneAttivita = new Integer(idRelazioneAttivita).intValue();
	}

	public String getAutorizzazione() {
		return autorizzazione;
	}

	public void setAutorizzazione(String autorizzazione) {
		this.autorizzazione = autorizzazione;
	}

	public Timestamp getDataInizio() {

		return dataInizio;
	}

	public void setDataInizio(Timestamp dataInizio) {
		this.dataInizio = dataInizio;
	}

	public void setDataInizio(String dataInizio) throws ParseException {
		if (dataInizio != null && !dataInizio.equals("")) {
			// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.dataInizio = DateUtils.parseDateStringNew(dataInizio, "dd/MM/yyyy");

		}

	}

	public Timestamp getDataFine() {

		return dataFine;
	}

	public String getDataFineasString() {
		String dataFine = "";
		if (this.dataFine != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dataFine = sdf.format(new Date(this.dataFine.getTime()));

		}
		return dataFine;
	}

	public String getDataInizioasString() {
		String dataInizio = "";
		if (this.dataInizio != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dataInizio = sdf.format(new Date(this.dataInizio.getTime()));

		}
		return dataInizio;
	}

	public void setDataFine(Timestamp dataFine) {
		this.dataFine = dataFine;
	}

	public void setDataFine(String dataFine) throws ParseException {
		if (dataFine != null && !dataFine.equals("")) {
			// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.dataFine = DateUtils.parseDateStringNew(dataFine, "dd/MM/yyyy");

		}

	}

	public String getMacrocategoria() {
		return macrocategoria;
	}

	public void setMacrocategoria(String macrocategoria) {
		this.macrocategoria = macrocategoria;
	}

	public int getStato() {
		return stato;
	}

	public void setStato(int stato) {
		this.stato = stato;
	}

	public void setStato(String stato) {
		this.stato = new Integer(stato).intValue();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setId(String id) {
		this.id = new Integer(id).intValue();
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getAttivita() {
		return attivita;
	}

	public void setAttivita(String attivita) {
		this.attivita = attivita;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public void setIdCategoria(String idCategoria) {
		this.idCategoria = new Integer(idCategoria).intValue();
	}

	public int getIdAttivita() {
		return idAttivita;
	}

	public void setIdAttivita(int idAttivita) {
		this.idAttivita = idAttivita;
	}

	public void setIdAttivita(String idAttivita) {
		this.idAttivita = new Integer(idAttivita).intValue();
	}

	public String getNoteInternalUseOnly() {
		return noteInternalUseOnly;
	}

	public void setNoteInternalUseOnly(String noteInternalUseOnly) {
		this.noteInternalUseOnly = noteInternalUseOnly;
	}

	public int getIdMacroarea() {
		return idMacroarea;
	}

	public void setIdMacroarea(int idMacroarea) {
		this.idMacroarea = idMacroarea;
	}

	public void setIdMacroarea(String idMacroarea) {
		this.idMacroarea = new Integer(idMacroarea).intValue();
	}

	public LineaProduttiva(Connection db, int id) {
		queryRecord(db, id);
	}

	public void queryRecord(Connection db, int id) {
		String sql = "select distinct rslp.autorizzazione, opu_relazione_attivita_produttive_aggregazioni.id as id,opu_lookup_macrocategorie_linee_produttive.description as macro,opu_lookup_macrocategorie_linee_produttive.code as id_macrocategoria, "
				+ "opu_lookup_aggregazioni_linee_produttive.description as aggregazione,opu_lookup_aggregazioni_linee_produttive.code as id_categoria ,opu_lookup_attivita_linee_produttive_aggregazioni.description as attivita ,opu_lookup_attivita_linee_produttive_aggregazioni.code as id_attivita "
				+ "from opu_lookup_attivita_linee_produttive_aggregazioni "
				+ "join opu_relazione_attivita_produttive_aggregazioni on opu_lookup_attivita_linee_produttive_aggregazioni.code = opu_relazione_attivita_produttive_aggregazioni.id_attivita_aggregazione "
				+ "join  opu_lookup_aggregazioni_linee_produttive on opu_lookup_aggregazioni_linee_produttive.code = opu_relazione_attivita_produttive_aggregazioni.id_aggregazione "
				+ "join opu_lookup_macrocategorie_linee_produttive on opu_lookup_macrocategorie_linee_produttive.code = opu_lookup_aggregazioni_linee_produttive.id_macrocategorie_linee_produttive "
				+ "left join opu_relazione_stabilimento_linee_produttive rslp on rslp.id_linea_produttiva = opu_relazione_attivita_produttive_aggregazioni.id "
				+

				"where opu_relazione_attivita_produttive_aggregazioni.id = ? and rslp.trashed_date is null "
				+ "order by opu_lookup_attivita_linee_produttive_aggregazioni.code";
		try {
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			this.idRelazioneAttivita = id;
			if (rs.next())
				buildRecord(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void queryRecordStabilimento(Connection db, int id) {
		String sql = "select rslp.id_stabilimento,rslp.data_inizio,rslp.data_fine,rslp.stato,opu_relazione_attivita_produttive_aggregazioni.id,opu_lookup_macrocategorie_linee_produttive.description as macro,opu_lookup_macrocategorie_linee_produttive.code as id_macrocategoria, "
				+ "opu_lookup_aggregazioni_linee_produttive.description as aggregazione,opu_lookup_aggregazioni_linee_produttive.code as id_categoria ,opu_lookup_attivita_linee_produttive_aggregazioni.description as attivita ,opu_lookup_attivita_linee_produttive_aggregazioni.code as id_attivita "
				+ "from opu_lookup_attivita_linee_produttive_aggregazioni "
				+ "join opu_relazione_attivita_produttive_aggregazioni on opu_lookup_attivita_linee_produttive_aggregazioni.code = opu_relazione_attivita_produttive_aggregazioni.id_attivita_aggregazione "
				+ "join  opu_lookup_aggregazioni_linee_produttive on opu_lookup_aggregazioni_linee_produttive.code = opu_relazione_attivita_produttive_aggregazioni.id_aggregazione "
				+ "join opu_lookup_macrocategorie_linee_produttive on opu_lookup_macrocategorie_linee_produttive.code = opu_lookup_aggregazioni_linee_produttive.id_macrocategorie_linee_produttive "
				+ "left opu_join relazione_stabilimento_linee_produttive rslp on rslp.id_linea_produttiva = opu_relazione_attivita_produttive_aggregazioni.id "
				+ "where opu_lookup_attivita_linee_produttive_aggregazioni.code = ?" +

				"order by lookup_attivita_linee_produttive_aggregazioni.code";

		try {
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				buildRecordStabilimento(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void buildRecord(ResultSet rs) throws SQLException {
		try {
			this.id = rs.getInt("id");
			this.macrocategoria = rs.getString("macro");
			this.idMacroarea = rs.getInt("id_macrocategoria");
			this.idAttivita = rs.getInt("id_attivita");
			this.idCategoria = rs.getInt("id_categoria");
			this.categoria = rs.getString("aggregazione");
			this.attivita = rs.getString("attivita");

			// this.autorizzazione = rs.getString("autorizzazione");

		} catch (SQLException e) {
			throw (e);
		}
	}

	public void buildRecordStabilimento(ResultSet rs) throws SQLException {
		try {
			// this.id = rs.getInt("id_relazione_attivita");
			this.id = rs.getInt("id");
			this.macrocategoria = rs.getString("macro");
			this.idMacroarea = rs.getInt("id_macrocategoria");
			this.idAttivita = rs.getInt("id_attivita");
			this.idCategoria = rs.getInt("id_categoria");
			this.categoria = rs.getString("aggregazione");
			this.attivita = rs.getString("attivita");
			this.dataInizio = rs.getTimestamp("data_inizio");
			this.setDataFine(rs.getTimestamp("data_fine"));
			this.stato = rs.getInt("stato");
			this.autorizzazione = rs.getString("autorizzazione");
			this.idRelazioneAttivita = rs.getInt("id_relazione_attivita");
			this.telefono1 = rs.getString("telefono1");
			this.telefono2 = rs.getString("telefono2");
			this.mail1 = rs.getString("mail1");
			this.mail2 = rs.getString("mail2");
			this.fax = rs.getString("fax");
			this.noteInternalUseOnly = rs.getString("note_internal_use_only");

		} catch (SQLException e) {
			throw (e);
		}
	}

	public LineaProduttiva(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	public LineaProduttiva(ResultSet rs, boolean f) throws SQLException {
		buildRecordStabilimento(rs);
	}

	public static boolean verificaLineaProduttiva(int idLinea1, int idLinea2, int idLineaProduttivaToMatch1,
			int idLineaProduttivaToMatch2) {

		boolean ret = false;
		Connection conn = null;
		PreparedStatement pst = null;

		try {

			// String dbName = ApplicationProperties.getProperty("dbnameBdu");
			// String username =
			// ApplicationProperties.getProperty("usernameDbbdu");
			// String pwd =ApplicationProperties.getProperty("passwordDbbdu");
			// String host =
			// InetAddress.getByName("hostDbBdu").getHostAddress();
			//
			//
			// conn = DbUtil.getConnection(dbName, username,
			// pwd, host);
if (idLinea1 > 0 && idLinea2 > 0){
			Operatore op1 = new Operatore();
			conn = GestoreConnessioni.getConnection();
			op1.queryRecordOperatorebyIdLineaProduttiva(conn, idLinea1);
			Stabilimento stab = (Stabilimento) op1.getListaStabilimenti().get(0);
			LineaProduttiva lp1 = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
			if (lp1.getIdAttivita() == idLineaProduttivaToMatch1) {
				ret = true;
			}

			op1 = new Operatore();
			op1.queryRecordOperatorebyIdLineaProduttiva(conn, idLinea2);
			stab = (Stabilimento) op1.getListaStabilimenti().get(0);
			lp1 = (LineaProduttiva) stab.getListaLineeProduttive().get(0);

			if (lp1.getIdAttivita() == idLineaProduttivaToMatch2) {
				ret = true;
			}
}else {return true;}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			GestoreConnessioni.freeConnection(conn);
		}

		// DbUtil.chiudiConnessioneJDBC(pst, conn);
		return ret;
	}

	public static boolean verificaLineaProduttiva(int idLinea1, int idLinea2) {

		boolean ret = false;
		Connection conn = null;
		PreparedStatement pst = null;

		try {

			// String dbName = ApplicationProperties.getProperty("dbnameBdu");
			// String username =
			// ApplicationProperties.getProperty("usernameDbbdu");
			// String pwd =ApplicationProperties.getProperty("passwordDbbdu");
			// String host =
			// InetAddress.getByName("hostDbBdu").getHostAddress();

			//
			// conn = DbUtil.getConnection(dbName, username,
			// pwd, host);

			Operatore op1 = new Operatore();
			conn = GestoreConnessioni.getConnection();
			op1.queryRecordOperatorebyIdLineaProduttiva(conn, idLinea1);
			Stabilimento stab = (Stabilimento) op1.getListaStabilimenti().get(0);
			LineaProduttiva lp1 = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
			if (lp1.getIdAttivita() == idAttivitaSindaco || lp1.getIdAttivita() == idAttivitaSindacoFR) {
				ret = true;
			}

			op1 = new Operatore();
			op1.queryRecordOperatorebyIdLineaProduttiva(conn, idLinea2);
			stab = (Stabilimento) op1.getListaStabilimenti().get(0);
			lp1 = (LineaProduttiva) stab.getListaLineeProduttive().get(0);

			if (lp1.getIdAttivita() == idAttivitaCanile) {
				ret = true;
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			GestoreConnessioni.freeConnection(conn);
		}

		// DbUtil.chiudiConnessioneJDBC(pst, conn);
		return ret;
	}

	public static EsitoControllo verificaLineaProduttivaPerCatture(int idLinea1, int idLinea2, boolean checkCattura) {

		boolean ret = false;
		Connection conn = null;
		PreparedStatement pst = null;
		int lineaProdProprietario = -1;
		int lineaProdDetentore = -1;
		String errore = "";
		EsitoControllo esito = new EsitoControllo();

		if (idLinea2 <= 0) { // Non ho scelto un detentore diverso dal
								// proprietario
			idLinea2 = idLinea1;
		}

		try {

			// String dbName = ApplicationProperties.getProperty("dbnameBdu");
			// String username =
			// ApplicationProperties.getProperty("usernameDbbdu");
			// String pwd =ApplicationProperties.getProperty("passwordDbbdu");
			// String host =
			// InetAddress.getByName("hostDbBdu").getHostAddress();

			// conn = DbUtil.getConnection(dbName, username,
			// pwd, host);

			Operatore op1 = new Operatore();
			conn = GestoreConnessioni.getConnection();
			op1.queryRecordOperatorebyIdLineaProduttiva(conn, idLinea1);
			Stabilimento stab = (Stabilimento) op1.getListaStabilimenti().get(0);
			LineaProduttiva lp1 = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
			lineaProdProprietario = lp1.getIdAttivita();

			op1 = new Operatore();
			op1.queryRecordOperatorebyIdLineaProduttiva(conn, idLinea2);
			stab = (Stabilimento) op1.getListaStabilimenti().get(0);
			lp1 = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
			lineaProdDetentore = lp1.getIdAttivita();

			if ((lineaProdProprietario == idAttivitaSindaco || lineaProdProprietario == idAttivitaSindacoFR)
					&& lineaProdDetentore == 4 && !checkCattura) {
				ret = false;
				errore = "Hai selezionato un proprietario di tipo sindaco e un detentore di tipo canile, inserisci cortesemente le informazioni di cattura. \r\n";

			} else if (checkCattura
					&& !(lineaProdProprietario == idAttivitaSindaco || lineaProdProprietario == idAttivitaSindacoFR)) {
				ret = false;
				errore = "Non puoi selezionare la cattura se non selezioni un proprietario di tipo sindaco e un detentore di tipo canile \r\n";
			} else {
				ret = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// DbUtil.chiudiConnessioneJDBC(pst, conn);
			GestoreConnessioni.freeConnection(conn);
		}

		if (!ret) {
			esito.setIdEsito(-1);
			esito.setDescrizione(errore);
		} else {
			esito.setIdEsito(1);
			esito.setDescrizione(errore);
		}
		return esito;

	}

	public static boolean verificaProprietarioSindacoFuoriRegione(int idLinea1) {

		boolean ret = true;
		Connection conn = null;
		PreparedStatement pst = null;
		int lineaProdProprietario = -1;

		try {

			// String dbName = ApplicationProperties.getProperty("dbnameBdu");
			// String username =
			// ApplicationProperties.getProperty("usernameDbbdu");
			// String pwd =ApplicationProperties.getProperty("passwordDbbdu");
			// String host =
			// InetAddress.getByName("hostDbBdu").getHostAddress();
			//
			// conn = DbUtil.getConnection(dbName, username,
			// pwd, host);
if (idLinea1 > 0){
			Operatore op1 = new Operatore();
			conn = GestoreConnessioni.getConnection();
			op1.queryRecordOperatorebyIdLineaProduttiva(conn, idLinea1);
			Stabilimento stab = (Stabilimento) op1.getListaStabilimenti().get(0);
			LineaProduttiva lp1 = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
			lineaProdProprietario = lp1.getIdAttivita();

			if (lineaProdProprietario != idAttivitaSindacoFR)
				ret = false;
}else{return false;}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// DbUtil.chiudiConnessioneJDBC(pst, conn);
			GestoreConnessioni.freeConnection(conn);
		}
		return ret;
	}

	public static boolean verificaDimensioneColonia(int idLineaDetentore) {
		boolean ret = true;
		Connection conn = null;
		PreparedStatement pst = null;

		try {

			// String dbName = ApplicationProperties.getProperty("dbnameBdu");
			// String username =
			// ApplicationProperties.getProperty("usernameDbbdu");
			// String pwd =ApplicationProperties.getProperty("passwordDbbdu");
			// String host =
			// InetAddress.getByName("hostDbBdu").getHostAddress();

			// conn = DbUtil.getConnection(dbName, username,
			// pwd, host);

			// conn = GestoreConnessioni.getConnection();

			// Operatore proprietario = new Operatore();
			conn = GestoreConnessioni.getConnection();
			// proprietario.queryRecordOperatorebyIdLineaProduttiva(conn,
			// idLineaProprietario);
			// Stabilimento stab = (Stabilimento)
			// proprietario.getListaStabilimenti().get(0);
			// LineaProduttiva lp1 = (LineaProduttiva)
			// stab.getListaLineeProduttive().get(0);
			//
			// if (lp1.getIdRelazioneAttivita() ==
			// LineaProduttiva.idAggregazioneColonia &&
			// ((ColoniaInformazioni)lp1).getNrGattiDaCensire() == 0){
			// ret = false;
			// }

			Operatore detentore = new Operatore();
			detentore.queryRecordOperatorebyIdLineaProduttiva(conn, idLineaDetentore);
			Stabilimento stabDet = (Stabilimento) detentore.getListaStabilimenti().get(0);
			LineaProduttiva lpDet = (LineaProduttiva) stabDet.getListaLineeProduttive().get(0);

			if (lpDet.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneColonia
					&& ((ColoniaInformazioni) lpDet).getNrGattiDaCensire() == 0) {
				ret = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			// DbUtil.chiudiConnessioneJDBC(pst, conn);
			GestoreConnessioni.freeConnection(conn);
		}
		return ret;
	}

	public boolean verificaProprietarioSpecie(int idSpecie, int idLineaProprietario, int idLineaDetentore) {

		// CONTROLLO, IN CASO DI PROPRIETARIO SINDACO, SE HO SELEZIONATO
		// CORRETTAMENTE UN CANILE
		boolean ret = true;
		Connection conn = null;
		PreparedStatement pst = null;

		try {

			// String dbName = ApplicationProperties.getProperty("dbnameBdu");
			// String username =
			// ApplicationProperties.getProperty("usernameDbbdu");
			// String pwd =ApplicationProperties.getProperty("passwordDbbdu");
			// String host =
			// InetAddress.getByName("hostDbBdu").getHostAddress();
			//
			//
			// conn = DbUtil.getConnection(dbName, username,
			// pwd, host);

			conn = GestoreConnessioni.getConnection();

			Operatore proprietario = new Operatore();
			proprietario.queryRecordOperatorebyIdLineaProduttiva(conn, idLineaProprietario);
			Stabilimento stab = (Stabilimento) proprietario.getListaStabilimenti().get(0);
			LineaProduttiva lp1 = (LineaProduttiva) stab.getListaLineeProduttive().get(0);

			Operatore detentore = new Operatore();
			detentore.queryRecordOperatorebyIdLineaProduttiva(conn, idLineaDetentore);
			Stabilimento stabDet = (Stabilimento) detentore.getListaStabilimenti().get(0);
			LineaProduttiva lpDet = (LineaProduttiva) stabDet.getListaLineeProduttive().get(0);

			// if ((idSpecie == Cane.idSpecie || idSpecie == Furetto.idSpecie)
			// &&
			if (lp1.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindaco
					&& lpDet.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneCanile) {
				ret = false;
			}
			// }

			// if ((idSpecie == Gatto.idSpecie || idSpecie == Furetto.idSpecie)
			// &&
			// (lp1.getIdRelazioneAttivita() ==
			// LineaProduttiva.idAggregazioneCanile
			// || lpDet.getIdRelazioneAttivita() ==
			// LineaProduttiva.idAggregazioneCanile)){
			// ret = false;
			// }

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// DbUtil.chiudiConnessioneJDBC(pst, conn);
			GestoreConnessioni.freeConnection(conn);
		}
		return ret;

	}

	public String getDescrizioneAttivita(Connection db) {
		String descrizione = "";
		String query = "select att.description from opu_lookup_attivita_linee_produttive_aggregazioni att "
				+ "left join opu_relazione_attivita_produttive_aggregazioni rel on "
				+ "(att.code = rel.id_attivita_aggregazione) where rel.id = ?";
		try {
			PreparedStatement pst = db.prepareStatement(query);
			pst.setInt(1, this.getIdRelazioneAttivita());

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				descrizione = rs.getString("description");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return descrizione;
	}

	public boolean insert(Connection db) throws SQLException {
		// TODO Auto-generated method stub
		// System.out.println("insert lp padre");

		return true;

	}

	public boolean aggiornaRecapiti(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {

			PreparedStatement pst = db
					.prepareStatement("Update opu_relazione_stabilimento_linee_produttive set telefono1=?, telefono2=?, mail1=?, fax=?, autorizzazione=?, note_internal_use_only = ? where "
							+ "id = ? ");

			int i = 0;
			pst.setString(++i, telefono1);
			pst.setString(++i, telefono2);
			pst.setString(++i, mail1);
			pst.setString(++i, fax);
			pst.setString(++i, autorizzazione);
			pst.setString(++i, noteInternalUseOnly);
			pst.setInt(++i, id);
			int resultCount = pst.executeUpdate();
			pst.close();


		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		} finally {
			/*
			 */
		}

		return true;
	}

	public boolean salvaModificheLinea(Connection db, String telefono1, String autorizzazione,
			String noteInternalUseOnly, LineaProduttiva newLinea, int idRelazione, int idUtente) throws SQLException {
		StringBuffer sql = new StringBuffer("");
		// Controllo cambiamenti dati operatore

		if (telefono1 == null && newLinea.getTelefono1() != null && !newLinea.getTelefono1().equals("")) {
			sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
					+ idRelazione + "," + "'Linea telefono'" + ", ? , ?, " + idUtente + ", now());");
		} else if (telefono1 != null && (!telefono1.equals(newLinea.getTelefono1()))) {
			sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
					+ idRelazione + "," + "'Linea telefono'" + ", ? , ?, " + idUtente + ", now());");
		}
		if (autorizzazione == null && newLinea.getAutorizzazione() != null && !newLinea.getAutorizzazione().equals("")) {
			sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
					+ idRelazione + "," + "'Linea autorizzazione'" + ", ? , ?, " + idUtente + ", now());");
		} else if (autorizzazione != null && (!autorizzazione.equals(newLinea.getAutorizzazione()))) {
			sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
					+ idRelazione + "," + "'Linea autorizzazione'" + ", ? , ?, " + idUtente + ", now());");
		}
		if (noteInternalUseOnly == null && newLinea.getNoteInternalUseOnly() != null
				&& !newLinea.getNoteInternalUseOnly().equals("")) {
			sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
					+ idRelazione + "," + "'Note interne'" + ", ? , ?, " + idUtente + ", now());");
		} else if (noteInternalUseOnly != null && (!noteInternalUseOnly.equals(newLinea.getNoteInternalUseOnly()))) {
			sql.append("insert into opu_operatore_storico (id_relazione, nome_campo_modificato, valore_precedente, nuovo_valore, id_utente_modifica, data_modifica) values("
					+ idRelazione + "," + "'Note interne'" + ", ? , ?, " + idUtente + ", now());");
		}
		try {
			PreparedStatement pst = db.prepareStatement(sql.toString());
			int i = 0;

			if (telefono1 == null && newLinea.getTelefono1() != null && !newLinea.getTelefono1().equals("")) {
				pst.setString(++i, "null");
				pst.setString(++i, newLinea.getTelefono1());
			} else if (telefono1 != null && (!telefono1.equals(newLinea.getTelefono1()))) {
				pst.setString(++i, telefono1);
				pst.setString(++i, newLinea.getTelefono1());
			}

			if (autorizzazione == null && newLinea.getAutorizzazione() != null
					&& !newLinea.getAutorizzazione().equals("")) {
				pst.setString(++i, autorizzazione);
				pst.setString(++i, newLinea.getAutorizzazione());
			} else if (autorizzazione != null && (!autorizzazione.equals(newLinea.getAutorizzazione()))) {
				pst.setString(++i, autorizzazione);
				pst.setString(++i, newLinea.getAutorizzazione());
			}

			if (noteInternalUseOnly == null && newLinea.getNoteInternalUseOnly() != null
					&& !newLinea.getNoteInternalUseOnly().equals("")) {
				pst.setString(++i, noteInternalUseOnly);
				pst.setString(++i, newLinea.getNoteInternalUseOnly());
			} else if (noteInternalUseOnly != null && (!noteInternalUseOnly.equals(newLinea.getNoteInternalUseOnly()))) {
				pst.setString(++i, noteInternalUseOnly);
				pst.setString(++i, newLinea.getNoteInternalUseOnly());
			}

			if (pst != null)
				pst.execute();
			pst.close();

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		} finally {
			/*
			 */
			// GestoreConnessioni.freeConnection(db);
			// DbUtil.chiudiConnessioneJDBC(null, null, db);
		}

		return true;
	}

	public void chiudi(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer("");
		sql.append("Update opu_relazione_stabilimento_linee_produttive set data_fine = ? where id = ? ");

		PreparedStatement pst = db.prepareStatement(sql.toString());
		int i = 0;
		pst.setTimestamp(++i, this.getDataFine());
		pst.setInt(++i, this.id);

		pst.execute();

		/**
		 * Aggiorno anche la data chiusura in opu_informazioni_canile o
		 * opu_informazioni_importatore
		 **/
		StringBuffer sqlInformazioni = new StringBuffer();
		StringBuffer verificaEsistenza = new StringBuffer();
		StringBuffer insertInformazioni = new StringBuffer();

		switch (this.getIdRelazioneAttivita()) {
		case idAggregazioneCanile:
			verificaEsistenza
					.append("select * from opu_informazioni_canile where id_relazione_stabilimento_linea_produttiva = ? ");
			sqlInformazioni
					.append("Update opu_informazioni_canile set data_chiusura = ? where id_relazione_stabilimento_linea_produttiva = ? ");
			insertInformazioni
					.append("INSERT INTO opu_informazioni_canile(id_relazione_stabilimento_linea_produttiva, data_chiusura)  VALUES (?, ?)");
			break;

		case idAggregazioneImportatore:
			verificaEsistenza
					.append("select * from opu_informazioni_importatore where id_relazione_stabilimento_lp = ? ");
			sqlInformazioni
					.append("Update opu_informazioni_importatore set data_chiusura = ? where id_relazione_stabilimento_lp = ? ");
			insertInformazioni
					.append("INSERT INTO opu_informazioni_importatore(id_relazione_stabilimento_lp, data_chiusura) VALUES (?, ?);");
			break;

		case IdAggregazioneOperatoreCommerciale:
			verificaEsistenza
					.append("select * from opu_informazioni_commerciali where id_relazione_stabilimento_lp = ? ");
			sqlInformazioni
					.append("Update opu_informazioni_commerciali set data_chiusura = ? where id_relazione_stabilimento_lp = ? ");
			insertInformazioni
					.append("INSERT INTO opu_informazioni_commerciali(id_relazione_stabilimento_lp, data_chiusura) VALUES (?, ?);");
			break;

		default:
			break;
		}

		PreparedStatement pstInformazioni = null;

		// Controllo se esiste entry in opu_informazioni_***
		PreparedStatement pstVerifica = db.prepareStatement(verificaEsistenza.toString());
		i = 0;
		pstVerifica.setInt(++i, this.id);
		ResultSet rs = pstVerifica.executeQuery();

		if (rs.next()) {
			pstInformazioni = db.prepareStatement(sqlInformazioni.toString());
			i = 0;
			pstInformazioni.setTimestamp(++i, this.getDataFine());
			pstInformazioni.setInt(++i, this.id);
			pstInformazioni.execute();

		} else {
			PreparedStatement pstAgg = db.prepareStatement(insertInformazioni.toString());
			i = 0;
			pstAgg.setInt(++i, this.id);
			pstAgg.setTimestamp(++i, this.getDataFine());
			pstAgg.execute();

		}

		GestoreComunicazioniGisa gisa = new GestoreComunicazioniGisa();

		switch (this.getIdRelazioneAttivita()) {
		case idAggregazioneCanile:
			gisa.chiudiCanileInGisa(db, this);
			break;

		case idAggregazioneImportatore:
			gisa.chiudiOperatoreCommercialeInGisa(db, this);
			break;

		case IdAggregazioneOperatoreCommerciale:
			gisa.chiudiOperatoreCommercialeInGisa(db, this);
			break;

		default:
			break;
		}

	}

	public void riapri(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer("");
		sql.append("Update opu_relazione_stabilimento_linee_produttive set data_fine = ? where id = ? ");

		PreparedStatement pst = db.prepareStatement(sql.toString());
		int i = 0;
		pst.setTimestamp(++i, null);
		pst.setInt(++i, this.id);

		pst.execute();

		/**
		 * Aggiorno anche la data chiusura in opu_informazioni_canile o
		 * opu_informazioni_importatore
		 **/
		StringBuffer sqlInformazioni = new StringBuffer();
		StringBuffer verificaEsistenza = new StringBuffer();
		StringBuffer insertInformazioni = new StringBuffer();

		switch (this.getIdRelazioneAttivita()) {
		case idAggregazioneCanile:
			verificaEsistenza
					.append("select * from opu_informazioni_canile where id_relazione_stabilimento_linea_produttiva = ? ");
			sqlInformazioni
					.append("Update opu_informazioni_canile set data_chiusura = ? where id_relazione_stabilimento_linea_produttiva = ? ");
			insertInformazioni
					.append("INSERT INTO opu_informazioni_canile(id_relazione_stabilimento_linea_produttiva, data_chiusura)  VALUES (?, ?)");
			break;

		case idAggregazioneImportatore:
			verificaEsistenza
					.append("select * from opu_informazioni_importatore where id_relazione_stabilimento_lp = ? ");
			sqlInformazioni
					.append("Update opu_informazioni_importatore set data_chiusura = ? where id_relazione_stabilimento_lp = ? ");
			insertInformazioni
					.append("INSERT INTO opu_informazioni_importatore(id_relazione_stabilimento_lp, data_chiusura) VALUES (?, ?);");
			break;

		case IdAggregazioneOperatoreCommerciale:
			verificaEsistenza
					.append("select * from opu_informazioni_commerciali where id_relazione_stabilimento_lp = ? ");
			sqlInformazioni
					.append("Update opu_informazioni_commerciali set data_chiusura = ? where id_relazione_stabilimento_lp = ? ");
			insertInformazioni
					.append("INSERT INTO opu_informazioni_commerciali(id_relazione_stabilimento_lp, data_chiusura) VALUES (?, ?);");
			break;

		default:
			break;
		}

		PreparedStatement pstInformazioni = null;

		// Controllo se esiste entry in opu_informazioni_***
		PreparedStatement pstVerifica = db.prepareStatement(verificaEsistenza.toString());
		i = 0;
		pstVerifica.setInt(++i, this.id);
		ResultSet rs = pstVerifica.executeQuery();

		if (rs.next()) {
			pstInformazioni = db.prepareStatement(sqlInformazioni.toString());
			i = 0;
			pstInformazioni.setTimestamp(++i, null);
			pstInformazioni.setInt(++i, this.id);
			pstInformazioni.execute();

		} else {
			PreparedStatement pstAgg = db.prepareStatement(insertInformazioni.toString());
			i = 0;
			pstAgg.setInt(++i, this.id);
			pstAgg.setTimestamp(++i, null);
			pstAgg.execute();

		}
		GestoreComunicazioniGisa gisa = new GestoreComunicazioniGisa();

		switch (this.getIdRelazioneAttivita()) {
		case idAggregazioneCanile:
			gisa.apriCanileleInGisa(db, this);
			break;

		case idAggregazioneImportatore:
			gisa.apriOperatoreCommercialeInGisa(db, this);
			break;

		case IdAggregazioneOperatoreCommerciale:
			gisa.apriOperatoreCommercialeInGisa(db, this);
			break;

		default:
			break;
		}

	}

}
