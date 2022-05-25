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
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.GestoreConnessioni;
//import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.DbUtil;
import org.aspcfs.utils.MethodsUtils;

public class EventoAdozioneFuoriAsl extends Evento {

	public static final int idTipologiaFuoriRegioneDB = 53;
	public static final int idTipologiaDB = 67;
	public static final int idTipologiaDB_obsoleto = 46;

	public EventoAdozioneFuoriAsl() {
		super();
		// TODO Auto-generated constructor stub
	}

	private int id = -1;
	private int idEvento = -1;
	private java.sql.Timestamp dataAdozioneFuoriAsl;
	private int idProprietario = -1;
	private int idVecchioProprietario = -1;
	private int idNuovoDetentore = -1;
	private int idVecchioDetentore = -1;
	private int idAslNuovoProprietario = -1;
	private int idAslVecchioProprietario = -1;

	private boolean flagAccettato = false;

	private String nome = "";
	private String cognome = "";
	private String codiceFiscale = "";
	private String sesso = "";
	private String cap = "";
	private String docIdentita = "";
	private String numeroTelefono = "";
	private java.sql.Timestamp dataNascita;
	private String luogoNascita = "";
	private String indirizzo = "";
	private int idProvincia = -1;
	private int idComune = -1;

	// Campo che mi dice se questa cessione è importabile automaticamente

	private boolean canAccept = false;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public boolean isFlagAccettato() {
		return flagAccettato;
	}

	public void setFlagAccettato(boolean flagAccettato) {
		this.flagAccettato = flagAccettato;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	
	public String getDocIdentita() {
		return docIdentita;
	}

	public void setDocIdentita(String docIdentita) {
		this.docIdentita = docIdentita;
	}

	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}

	public java.sql.Timestamp getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(java.sql.Timestamp dataNascita) {
		this.dataNascita = dataNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = DateUtils.parseDateStringNew(dataNascita,
				"dd/MM/yyyy");
	}

	public String getLuogoNascita() {
		return luogoNascita;
	}

	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public int getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(int id_provincia) {
		this.idProvincia = id_provincia;
	}

	public void setIdProvincia(String id_provincia) {
		this.idProvincia = new Integer(id_provincia).intValue();
	}

	public int getIdComune() {
		return idComune;
	}

	public void setIdComune(int id_comune) {
		this.idComune = id_comune;
	}

	public void setIdComune(String id_comune) {
		this.idComune = new Integer(id_comune).intValue();
	}

	public static int getIdTipologiaDB() {
		return idTipologiaDB;
	}

	public java.sql.Timestamp getDataAdozioneFuoriAsl() {
		return dataAdozioneFuoriAsl;
	}

	public void setDataAdozioneFuoriAsl(java.sql.Timestamp dataAdozioneFuoriAsl) {
		this.dataAdozioneFuoriAsl = dataAdozioneFuoriAsl;
	}

	public void setDataAdozioneFuoriAsl(String dataAdozioneFuoriAsl) {
		this.dataAdozioneFuoriAsl = DateUtils.parseDateStringNew(
				dataAdozioneFuoriAsl, "dd/MM/yyyy");
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

	public int getIdProprietario() {
		return idProprietario;
	}

	public void setIdProprietario(int idProprietario) {
		this.idProprietario = idProprietario;
	}

	public void setIdProprietario(String idProprietario) {
		this.idProprietario = new Integer(idProprietario).intValue();
	}

	public int getIdVecchioProprietario() {
		return idVecchioProprietario;
	}

	public void setIdVecchioProprietario(int idVecchioProprietario) {
		this.idVecchioProprietario = idVecchioProprietario;
	}

	public int getIdNuovoDetentore() {
		return idNuovoDetentore;
	}

	public void setIdNuovoDetentore(int idNuovoDetentore) {
		this.idNuovoDetentore = idNuovoDetentore;
	}

	public int getIdVecchioDetentore() {
		return idVecchioDetentore;
	}

	public void setIdVecchioDetentore(int idVecchioDetentore) {
		this.idVecchioDetentore = idVecchioDetentore;
	}

	public int getIdAslNuovoProprietario() {
		return idAslNuovoProprietario;
	}

	public void setIdAslNuovoProprietario(int idAslNuovoProprietario) {
		this.idAslNuovoProprietario = idAslNuovoProprietario;
	}

	public void setIdAslNuovoProprietario(String idAslNuovoProprietario) {
		this.idAslNuovoProprietario = new Integer(idAslNuovoProprietario)
				.intValue();
	}

	public int getIdAslVecchioProprietario() {
		return idAslVecchioProprietario;
	}

	public void setIdAslVecchioProprietario(int idAslVecchioProprietario) {
		this.idAslVecchioProprietario = idAslVecchioProprietario;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public boolean isCanAccept() {
		return canAccept;
	}

	public void setCanAccept(boolean canAccept) {
		this.canAccept = canAccept;
	}

	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();
		
		try {

			super.insert(db);
			idEvento = super.getIdEvento();

			id = DatabaseUtils.getNextSeq(db,
					"evento_adozione_fuori_asl_id_seq");
			// sql.append("INSERT INTO animale (");

			sql.append("INSERT INTO evento_adozione_fuori_asl("
					+ "id_evento, data_adozione_fa, id_nuovo_proprietario_adozione_fa, id_vecchio_proprietario_adozione_fa, flag_accettato  ");

			if (idNuovoDetentore > -1) {
				sql.append(",id_nuovo_detentore_adozione_fa ");
			}

			if (idVecchioDetentore > -1) {
				sql.append(",id_vecchio_detentore_adozione_fa ");
			}

			if (idAslNuovoProprietario > -1) {
				sql.append(",id_asl_nuovo_proprietario_adozione_fa ");
			}

			if (idAslVecchioProprietario > -1) {
				sql.append(",id_asl_vecchio_proprietario_adozione_fa ");
			}

			if (nome != null && !("").equals(nome)) {
				sql.append(", nome_proprietario_a_adozione_fa ");
			}

			if (cognome != null && !("").equals(cognome)) {
				sql.append(", cognome_proprietario_a_adozione_fa ");
			}

			if (codiceFiscale != null && !("").equals(codiceFiscale)) {
				sql.append(", codice_fiscale_proprietario_a_adozione_fa ");
			}
			
			if (sesso != null && !("").equals(sesso)) {
				sql.append(", sesso_proprietario_a_adozione_fa ");
			}
			
			if (cap != null && !("").equals(cap)) {
				sql.append(", cap_proprietario_a_adozione_fa ");
			}

			if (dataNascita != null) {
				sql.append(", data_nascita_proprietario_a_adozione_fa ");
			}

			if (luogoNascita != null && !("").equals(luogoNascita)) {
				sql.append(", luogo_nascita_proprietario_a_adozione_fa ");
			}

			if (indirizzo != null && !("").equals(indirizzo)) {
				sql.append(", indirizzo_proprietario_a_adozione_fa ");
			}

			if (idProvincia != -1) {
				sql.append(", id_provincia_proprietario_a_adozione_fa ");
			}

			if (idComune != -1) {
				sql.append(", id_comune_proprietario_a_adozione_fa ");
			}

			if (docIdentita != null && !("").equals(docIdentita)) {
				sql.append(", documento_identita_proprietario_a_adozione_fa ");
			}

			if (numeroTelefono != null && !("").equals(numeroTelefono)) {
				sql.append(", numero_telefono_proprietario_a_adozione_fa ");
			}

			sql.append(")VALUES(?,?,?,?,?");

			if (idNuovoDetentore > -1) {
				sql.append(",? ");
			}

			if (idVecchioDetentore > -1) {
				sql.append(",? ");
			}

			if (idAslNuovoProprietario > -1) {
				sql.append(",? ");
			}

			if (idAslVecchioProprietario > -1) {
				sql.append(",? ");
			}

			if (nome != null && !("").equals(nome)) {
				sql.append(",? ");
			}

			if (cognome != null && !("").equals(cognome)) {
				sql.append(",? ");
			}

			if (codiceFiscale != null && !("").equals(codiceFiscale)) {
				sql.append(",? ");
			}
			
			if (sesso != null && !("").equals(sesso)) {
				sql.append(",? ");
			}
			
			if (cap != null && !("").equals(cap)) {
				sql.append(",? ");
			}

			if (dataNascita != null) {
				sql.append(",? ");
			}

			if (luogoNascita != null && !("").equals(luogoNascita)) {
				sql.append(",? ");
			}

			if (indirizzo != null && !("").equals(indirizzo)) {
				sql.append(",? ");
			}

			if (idProvincia != -1) {
				sql.append(",? ");
			}

			if (idComune != -1) {
				sql.append(",? ");
			}

			if (docIdentita != null && !("").equals(docIdentita)) {
				sql.append(", ? ");
			}

			if (numeroTelefono != null && !("").equals(numeroTelefono)) {
				sql.append(", ? ");
			}

			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idEvento);

			pst.setTimestamp(++i, dataAdozioneFuoriAsl);

			pst.setInt(++i, idProprietario);

			pst.setInt(++i, idVecchioProprietario);

			pst.setBoolean(++i, flagAccettato);

			if (idNuovoDetentore > -1) {
				pst.setInt(++i, idNuovoDetentore);
			}

			if (idVecchioDetentore > -1) {
				pst.setInt(++i, idVecchioDetentore);
			}

			if (idAslNuovoProprietario > -1) {
				pst.setInt(++i, idAslNuovoProprietario);
			}

			if (idAslVecchioProprietario > -1) {
				pst.setInt(++i, idAslVecchioProprietario);
			}

			if (nome != null && !("").equals(nome)) {
				pst.setString(++i, nome);
			}

			if (cognome != null && !("").equals(cognome)) {
				pst.setString(++i, cognome);
			}

			if (codiceFiscale != null && !("").equals(codiceFiscale)) {
				pst.setString(++i, codiceFiscale);
			}
			
			if (sesso != null && !("").equals(sesso)) {
				pst.setString(++i, (sesso.equals("1")?("M"):("F")));
			}
			
			if (cap != null && !("").equals(cap)) {
				pst.setString(++i, cap);
			}

			if (dataNascita != null) {
				pst.setTimestamp(++i, dataNascita);
			}

			if (luogoNascita != null && !("").equals(luogoNascita)) {
				pst.setString(++i, luogoNascita);
			}

			if (indirizzo != null && !("").equals(indirizzo)) {
				pst.setString(++i, indirizzo);
			}

			if (idProvincia != -1) {
				pst.setInt(++i, idProvincia);
			}

			if (idComune != -1) {
				pst.setInt(++i, idComune);
			}

			if (docIdentita != null && !("").equals(docIdentita)) {
				pst.setString(++i, docIdentita);
			}

			if (numeroTelefono != null && !("").equals(numeroTelefono)) {
				pst.setString(++i, numeroTelefono);
			}

			pst.execute();
			pst.close();

			this.id = DatabaseUtils.getCurrVal(db,
					"evento_adozione_fuori_asl_id_seq", id);

		} catch (SQLException e) {
		
			throw new SQLException(e.getMessage());
		} finally {
		
		}
		return true;

	}

	public EventoAdozioneFuoriAsl(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		super.buildRecord(rs);
		this.idEvento = rs.getInt("idevento");
		this.id = rs.getInt("id");
		this.dataAdozioneFuoriAsl = rs.getTimestamp("data_adozione_fa");
		this.idProprietario = rs.getInt("id_nuovo_proprietario_adozione_fa");
		this.idVecchioProprietario = rs
				.getInt("id_vecchio_proprietario_adozione_fa");
		this.idNuovoDetentore = rs.getInt("id_nuovo_detentore_adozione_fa");
		this.idVecchioDetentore = rs.getInt("id_vecchio_detentore_adozione_fa");
		this.idAslNuovoProprietario = rs
				.getInt("id_asl_nuovo_proprietario_adozione_fa");
		this.idAslVecchioProprietario = rs
				.getInt("id_asl_vecchio_proprietario_adozione_fa");
		this.flagAccettato = rs.getBoolean("flag_accettato");
		this.nome = rs.getString("nome_proprietario_a_adozione_fa");
	//	System.out.println(this.nome);
		this.cognome = rs.getString("cognome_proprietario_a_adozione_fa");
		this.codiceFiscale = rs
				.getString("codice_fiscale_proprietario_a_adozione_fa");
		this.sesso = rs
				.getString("sesso_proprietario_a_adozione_fa");
		this.cap = rs
				.getString("cap_proprietario_a_adozione_fa");
		this.dataNascita = rs
				.getTimestamp("data_nascita_proprietario_a_adozione_fa");
		this.luogoNascita = rs
				.getString("luogo_nascita_proprietario_a_adozione_fa");
		this.indirizzo = rs.getString("indirizzo_proprietario_a_adozione_fa");
		this.idProvincia = rs.getInt("id_provincia_proprietario_a_adozione_fa");
		this.idComune = rs.getInt("id_comune_proprietario_a_adozione_fa");
		this.docIdentita = rs
				.getString("documento_identita_proprietario_a_adozione_fa");
		this.numeroTelefono = rs
				.getString("numero_telefono_proprietario_a_adozione_fa");

		if (this.getIdProprietario() > 0
				|| (this.checkPossibilitaImportAutomaticoProprietario() == null || ("")
						.equals(this
								.checkPossibilitaImportAutomaticoProprietario()))) {
			this.canAccept = true;
		}

		// this.destinatarioCessioneVecchiaCanina =
		// rs.getString("nome_cognome_destinaziione_vecchia_canina");

	}

	public EventoAdozioneFuoriAsl(Connection db, int idEventoPadre)
			throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_adozione_fuori_asl f on (e.id_evento = f.id_evento) where e.id_evento = ?");
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

	public void GetAdozioneApertaByIdAnimale(Connection db, int idAnimale)
			throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_adozione_fuori_asl f on (e.id_evento = f.id_evento) where e.id_animale = ? and f.flag_accettato = false and e.data_cancellazione is null ");
		pst.setInt(1, idAnimale);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) {
			buildRecord(rs);
		}

		if(this.cap == null || this.cap.equals(""))
			this.cap = ComuniAnagrafica.getCap(db, this.getIdComune());

		if (idAnimale == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		rs.close();
		pst.close();

	}

	public Operatore getNuovoProprietario() throws UnknownHostException {

		Connection conn = null;
		int idOperatore = -1;
		Operatore operatore = null;
		try {

			Thread t = Thread.currentThread(); conn = GestoreConnessioni.getConnection();

			// String dbName = ApplicationProperties.getProperty("dbnameBdu");
			// String username = ApplicationProperties
			// .getProperty("usernameDbbdu");
			// String pwd = ApplicationProperties.getProperty("passwordDbbdu");
			// String host =
			// InetAddress.getByName("hostDbBdu").getHostAddress();

			// conn = DbUtil.getConnection(dbName, username, pwd, host);

			idOperatore = this.getIdProprietario();

			if (idOperatore != -1 && idOperatore != 0) {
				
				operatore = new Operatore();
				operatore.queryRecordOperatorebyIdLineaProduttiva(conn,
						idOperatore);
			}

			// GestoreConnessioni.freeConnection(conn);
			// DbUtil.chiudiConnessioneJDBC(null, null, conn);
			// conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(conn);
		}

		return operatore;
	}

	public Operatore getVecchioProprietario() throws UnknownHostException {

		Connection conn = null;
		int idOperatore = -1;
		Operatore operatore = null;
		try {
			Thread t = Thread.currentThread(); conn = GestoreConnessioni.getConnection();

			// String dbName = ApplicationProperties.getProperty("dbnameBdu");
			// String username = ApplicationProperties
			// .getProperty("usernameDbbdu");
			// String pwd = ApplicationProperties.getProperty("passwordDbbdu");
			// String host =
			// InetAddress.getByName("hostDbBdu").getHostAddress();
			//
			// conn = DbUtil.getConnection(dbName, username, pwd, host);

			idOperatore = this.getIdVecchioProprietario();

			if (idOperatore != -1 && idOperatore != 0) {
				
				operatore = new Operatore();
				operatore.queryRecordOperatorebyIdLineaProduttiva(conn,
						idOperatore);
			}

			// GestoreConnessioni.freeConnection(conn);

			// DbUtil.chiudiConnessioneJDBC(null, null, conn);
			// conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			GestoreConnessioni.freeConnection(conn);
		}

		return operatore;
	}

	public String checkPossibilitaImportAutomaticoProprietario() {

		String campo_mancante = "";

		if (this.getCodiceFiscale() == null
				|| ("").equals(this.getCodiceFiscale())) {
			campo_mancante = "codice fiscale";
		}
		
		if (this.getSesso() == null
				|| ("").equals(this.getSesso()) || ("-1").equals(this.getSesso())) {
			campo_mancante = "sesso";
		}

		if (this.getNome() == null || ("").equals(this.getNome())) {
			campo_mancante = "nome";
		}

		if (this.getCognome() == null || ("").equals(this.getCognome())) {
			campo_mancante = "cognome";
		}

		if (this.getIdComune() < 0) {
			campo_mancante = "comune residenza";
		}

		if (this.getIdProvincia() < 0) {
			campo_mancante = "provincia residenza";
		}

		if (this.getIndirizzo() == null || ("").equals(this.getIndirizzo())) {
			campo_mancante = "indirizzo residenza";
		}

		return campo_mancante;
	}
	
	
	public EventoAdozioneFuoriAsl salvaRegistrazione(int userId,
			int userRole, int userAsl, Animale thisAnimale, Connection db)
			throws Exception 
	{
		if(Boolean.parseBoolean(ApplicationProperties.getProperty("attivazioneCessioneOneStep"))) 
			return salvaRegistrazioneOneStep(userId, userRole, userAsl, thisAnimale, db);
		else
			return salvaRegistrazioneOld(userId, userRole, userAsl, thisAnimale, db);
		
	}

	public EventoAdozioneFuoriAsl salvaRegistrazioneOld(int userId, int userRole,
			int userAsl, Animale thisAnimale, Connection db) throws Exception {
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

			// adozione fuori
			// asl
			boolean fuoriAsl = false;

			this.setIdVecchioProprietario(oldAnimale.getIdProprietario());
			this.setIdAslVecchioProprietario(oldAnimale.getIdAslRiferimento());

			// if (oldAnimale.getIdSpecie() == Cane.idSpecie) {

			this.setIdVecchioDetentore(oldAnimale.getIdDetentore());

			if (this.getIdProprietario() > 0) {

				// thisCane.setIdDetentore(cessione
				// .getIdProprietario());
				// thisCane.setIdDetentore(-1);

				Operatore nuovoProprietario = new Operatore();
				nuovoProprietario.queryRecordOperatorebyIdLineaProduttiva(db,
						this.getIdProprietario());

				Stabilimento sedeProprietario = (Stabilimento) nuovoProprietario
						.getListaStabilimenti().get(0);

				thisAnimale.setIdAslRiferimento(sedeProprietario.getIdAsl()); // PRENDERE
				// NUOVA
				// ASL
				// DA
				// PROPRIETARIO
				// SELEZIONATO
				this.setIdAslNuovoProprietario(sedeProprietario.getIdAsl());

				if (sedeProprietario.getIdAsl() == Integer
						.parseInt(ApplicationProperties
								.getProperty("ID_ASL_FUORI_REGIONE"))) {
					fuoriAsl = true;
				}
				
				//Se l'asl del proprietario è fuori regione
				if(sedeProprietario.getIdAsl() == Integer.parseInt(ApplicationProperties.getProperty("ID_ASL_FUORI_REGIONE")))
				{
					if(oldAnimale.getStato()==3)
						thisAnimale.setStato(34);
					else if(oldAnimale.getStato()==9)
						thisAnimale.setStato(35);
					
				}

				// thisCane.setIdProprietario(cessione
				// .getIdProprietario());

			} else {
				thisAnimale.setIdAslRiferimento(this
						.getIdAslNuovoProprietario()); // Setto
				// all'asl
				// selezionata
				// nella select
				// della
				// maschera

				if (this.getIdAslNuovoProprietario() == Integer
						.parseInt(ApplicationProperties
								.getProperty("ID_ASL_FUORI_REGIONE"))) {
					fuoriAsl = true;
				}

			}

			thisAnimale.setIdDetentore(-1);
			thisAnimale.setIdProprietario(-1);

			// VEDERE SE IN SEGUITO BISOGNA SCEGLIERE ANCHE IL
			// DETENTORE;
			// PER ORA LO IMPOSTO AL PROPRIETARIO

			this.insert(db);

			aggiornaFlagFuoriDominioAsl(db, thisAnimale, userAsl, oldAnimale);
			if(this.getIdAslNuovoProprietario()==14)
				aggiornaStatoAnimaleAdozioneFuoriRegione(db, thisAnimale);
			else
				aggiornaStatoAnimale(db, thisAnimale);

		} catch (Exception e) {
			throw e;
		}

		return this;

	}
	
	
	public EventoAdozioneFuoriAsl salvaRegistrazioneOneStep(int userId, int userRole,
			int userAsl, Animale thisAnimale, Connection db) throws Exception {
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

			// adozione fuori
			// asl
			boolean fuoriAsl = false;

			this.setIdVecchioProprietario(oldAnimale.getIdProprietario());
			this.setIdAslVecchioProprietario(oldAnimale.getIdAslRiferimento());

			// if (oldAnimale.getIdSpecie() == Cane.idSpecie) {

			this.setIdVecchioDetentore(oldAnimale.getIdDetentore());

			if (this.getIdProprietario() > 0) {

				// thisCane.setIdDetentore(cessione
				// .getIdProprietario());
				// thisCane.setIdDetentore(-1);

				Operatore nuovoProprietario = new Operatore();
				nuovoProprietario.queryRecordOperatorebyIdLineaProduttiva(db,
						this.getIdProprietario());

				Stabilimento sedeProprietario = (Stabilimento) nuovoProprietario
						.getListaStabilimenti().get(0);

				thisAnimale.setIdAslRiferimento(sedeProprietario.getIdAsl()); // PRENDERE
				// NUOVA
				// ASL
				// DA
				// PROPRIETARIO
				// SELEZIONATO
				this.setIdAslNuovoProprietario(sedeProprietario.getIdAsl());

				if (sedeProprietario.getIdAsl() == Integer
						.parseInt(ApplicationProperties
								.getProperty("ID_ASL_FUORI_REGIONE"))) {
					fuoriAsl = true;
				}
				
				thisAnimale.setIdDetentore(this.getIdProprietario());
				thisAnimale.setIdProprietario(this.getIdProprietario());
				
				//Se l'asl del proprietario è fuori regione
				if(sedeProprietario.getIdAsl() == Integer.parseInt(ApplicationProperties.getProperty("ID_ASL_FUORI_REGIONE")))
				{
					if(oldAnimale.getStato()==3)
						thisAnimale.setStato(34);
					else if(oldAnimale.getStato()==9)
						thisAnimale.setStato(35);
					
				}

				// thisCane.setIdProprietario(cessione
				// .getIdProprietario());

			} else {
				thisAnimale.setIdAslRiferimento(this
						.getIdAslNuovoProprietario()); // Setto
				// all'asl
				// selezionata
				// nella select
				// della
				// maschera

				if (this.getIdAslNuovoProprietario() == Integer
						.parseInt(ApplicationProperties
								.getProperty("ID_ASL_FUORI_REGIONE"))) {
					fuoriAsl = true;
				}

			}

			// VEDERE SE IN SEGUITO BISOGNA SCEGLIERE ANCHE IL
			// DETENTORE;
			// PER ORA LO IMPOSTO AL PROPRIETARIO

			this.insert(db);

			aggiornaFlagFuoriDominioAsl(db, thisAnimale, userAsl, oldAnimale);
			if(this.getIdAslNuovoProprietario()==14)
				aggiornaStatoAnimaleAdozioneFuoriRegione(db, thisAnimale);
			else
				aggiornaStatoAnimale(db, thisAnimale);

		} catch (Exception e) {
			throw e;
		}

		return this;

	}
	
	public EventoAdozioneFuoriAsl build(ResultSet rs) throws Exception{
		try{	
			
			super.build(rs);
			buildRecord(rs);
		
		}catch (Exception e){
			throw e;
		}
	return this;
		}
	
	
	
	//usata per recuperare la cessione da riaprire a seguito della cancellazione di una presa in carico
		public void GetUltimaAdozioneChiusaByIdAnimale(Connection db, int idAnimale) throws SQLException {

			// super(db, idEventoPadre);

			PreparedStatement pst = db
					.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_adozione_fuori_asl f on (e.id_evento = f.id_evento) where e.id_animale = ? and f.flag_accettato = true and e.data_cancellazione is null and e.trashed_date is null");
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
		
		
		public int updateAccettazione(Connection conn) {
			int result = -1;

			try {
				StringBuffer sql = new StringBuffer();

				sql.append("update evento_adozione_fuori_asl set flag_accettato = ? where id_evento=?");
				PreparedStatement pst = conn.prepareStatement(sql.toString());

				int i = 0;
				pst.setBoolean(++i, this.flagAccettato);
				pst.setInt(++i, this.getIdEvento());
				
				result = pst.executeUpdate();
				pst.close();

			} catch (Exception e) {
				System.out.println(e);
			}

			return result;
		}

}
