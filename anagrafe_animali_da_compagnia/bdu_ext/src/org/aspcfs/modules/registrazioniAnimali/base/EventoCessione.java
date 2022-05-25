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

import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Furetto;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.DbUtil;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.MethodsUtils;
import org.aspcfs.utils.web.LookupList;
//import org.aspcfs.utils.GestoreConnessioni;
import org.omg.CORBA.DATA_CONVERSION;

/**
 * 
 * @author Veronica Evento di cessione: l'asl di destinazione può essere diversa
 *         dall'asl di partenza ma il trasferimento è interno alla regione
 *         Campania.
 * 
 */

public class EventoCessione extends Evento {

	public static final int idTipologiaDB = 59;
	public static final int idTipologiaDB_obsoleto = 7;

	public EventoCessione() {
		
		super();
		this.setIdTipologiaEvento(idTipologiaDB); 
		// TODO Auto-generated constructor stub
	}

	private int id = -1;
	private int idEvento = -1;
	private java.sql.Timestamp dataCessione;
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
	
	
	private Operatore operatoreDestinatario = new Operatore();
	private Operatore operatoreMittente = new Operatore();
	
	private String destinatarioCessioneVecchiaCanina = "";
	private String destinazioneTestuale = "";
	
	
	//Campo che mi dice se questa cessione è importabile automaticamente
	
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
	
	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
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

	public java.sql.Timestamp getDataCessione() {
		return dataCessione;
	}

	public void setDataCessione(java.sql.Timestamp dataCessione) {
		this.dataCessione = dataCessione;
	}

	public void setDataCessione(String dataCessione) {
		this.dataCessione = DateUtils.parseDateStringNew(dataCessione,
				"dd/MM/yyyy");
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
	
	
	

	public String getDestinatarioCessioneVecchiaCanina() {
		return destinatarioCessioneVecchiaCanina;
	}

	public void setDestinatarioCessioneVecchiaCanina(
			String destinatarioCessioneVecchiaCanina) {
		this.destinatarioCessioneVecchiaCanina = destinatarioCessioneVecchiaCanina;
	}

	public boolean isCanAccept() {
		return canAccept;
	}

	public void setCanAccept(boolean canAccept) {
		this.canAccept = canAccept;
	}

	
	
	
	

	public Operatore getOperatoreDestinatario() {
		return operatoreDestinatario;
	}

	public void setOperatoreDestinatario(Operatore operatoreDestinatario) {
		this.operatoreDestinatario = operatoreDestinatario;
	}

	public Operatore getOperatoreMittente() {
		return operatoreMittente;
	}

	public void setOperatoreMittente(Operatore operatoreMittente) {
		this.operatoreMittente = operatoreMittente;
	}
	
	

	public String getDestinazioneTestuale() {
		return destinazioneTestuale;
	}

	public void setDestinazioneTestuale(String destinazioneTestuale) {
		this.destinazioneTestuale = destinazioneTestuale;
	}

	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();
		
		try {


			super.insert(db);
			idEvento = super.getIdEvento();

			id = DatabaseUtils.getNextSeq(db, "evento_cessione_id_seq");
			// sql.append("INSERT INTO animale (");

			sql
					.append("INSERT INTO evento_cessione("
							+ "id_evento, data_cessione, id_nuovo_proprietario_cessione, id_vecchio_proprietario_cessione  ");

			if (idNuovoDetentore > -1) {
				sql.append(",id_nuovo_detentore_cessione ");
			}
			
			if (flagAccettato ) {
				sql.append(",flag_accettato ");
			}


			if (idVecchioDetentore > -1) {
				sql.append(",id_vecchio_detentore_cessione ");
			}

			if (idAslNuovoProprietario > -1) {
				sql.append(",id_asl_nuovo_proprietario_cessione ");
			}

			if (idAslVecchioProprietario > -1) {
				sql.append(",id_asl_vecchio_proprietario_cessione ");
			}

			if (nome != null && !("").equals(nome)) {
				sql.append(", nome_proprietario_a_cessione ");
			}

			if (cognome != null && !("").equals(cognome)) {
				sql.append(", cognome_proprietario_a_cessione ");
			}

			if (codiceFiscale != null && !("").equals(codiceFiscale)) {
				sql.append(", codice_fiscale_proprietario_a_cessione ");
			}
			
			if (sesso != null && !("").equals(sesso)) {
				sql.append(", sesso_proprietario_a_cessione ");
			}
			
			if (cap != null && !("").equals(cap)) {
				sql.append(", cap_proprietario_a_cessione ");
			}

			if (dataNascita != null) {
				sql.append(", data_nascita_proprietario_a_cessione ");
			}

			if (luogoNascita != null && !("").equals(luogoNascita)) {
				sql.append(", luogo_nascita_proprietario_a_cessione ");
			}

			if (indirizzo != null && !("").equals(indirizzo)) {
				sql.append(", indirizzo_proprietario_a_cessione ");
			}

			if (idProvincia != -1) {
				sql.append(", id_provincia_proprietario_a_cessione ");
			}

			if (idComune != -1) {
				sql.append(", id_comune_proprietario_a_cessione ");
			}
			
			if(docIdentita != null && !("").equals(docIdentita)){
				sql.append(", documento_identita_proprietario_a_cessione ");
			}
			
			if (numeroTelefono != null && !("").equals(numeroTelefono)){
				sql.append(", numero_telefono_proprietario_a_cessione ");
			}

			sql.append(")VALUES(?,?,?,?");

			if (idNuovoDetentore > -1) {
				sql.append(",? ");
			}
			
			if (flagAccettato) {
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
			
			if(docIdentita != null && !("").equals(docIdentita)){
				sql.append(", ? ");
			}
			
			if (numeroTelefono != null && !("").equals(numeroTelefono)){
				sql.append(", ? ");
			}

			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idEvento);

			pst.setTimestamp(++i, dataCessione);

			pst.setInt(++i, idProprietario);

			pst.setInt(++i, idVecchioProprietario);

			if (idNuovoDetentore > -1) {
				pst.setInt(++i, idNuovoDetentore);
			}
			
			if (flagAccettato) {
				pst.setBoolean(++i, flagAccettato);
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
			
			if(docIdentita != null && !("").equals(docIdentita)){
				pst.setString(++i, docIdentita);
			}
			
			if (numeroTelefono != null && !("").equals(numeroTelefono)){
				pst.setString(++i, numeroTelefono);
			}

			pst.execute();
			pst.close();

			this.id = DatabaseUtils
					.getCurrVal(db, "evento_cessione_id_seq", id);

			

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return true;

	}

	public EventoCessione(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}
	
	public EventoCessione(ResultSet rs, Connection db) throws SQLException {
		buildRecord(rs, db);
	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		super.buildRecord(rs);
		this.idEvento = rs.getInt("idevento");
		this.id = rs.getInt("id");
		this.dataCessione = rs.getTimestamp("data_cessione");
		this.idProprietario = rs.getInt("id_nuovo_proprietario_cessione");
		this.idVecchioProprietario = rs
				.getInt("id_vecchio_proprietario_cessione");
		this.idNuovoDetentore = rs.getInt("id_nuovo_detentore_cessione");
		this.idVecchioDetentore = rs.getInt("id_vecchio_detentore_cessione");
		this.idAslNuovoProprietario = rs
				.getInt("id_asl_nuovo_proprietario_cessione");
		this.idAslVecchioProprietario = rs
				.getInt("id_asl_vecchio_proprietario_cessione");
		this.flagAccettato = rs.getBoolean("flag_accettato");
		this.nome = rs.getString("nome_proprietario_a_cessione");
		//System.out.println(this.nome);
		this.cognome = rs.getString("cognome_proprietario_a_cessione");
		this.codiceFiscale = rs
				.getString("codice_fiscale_proprietario_a_cessione");
		this.sesso = rs.getString("sesso_proprietario_a_cessione");
		this.cap = rs.getString("cap_proprietario_a_cessione");
		this.dataNascita = rs
				.getTimestamp("data_nascita_proprietario_a_cessione");
		this.luogoNascita = rs
				.getString("luogo_nascita_proprietario_a_cessione");
		this.indirizzo = rs.getString("indirizzo_proprietario_a_cessione");
		this.idProvincia = rs.getInt("id_provincia_proprietario_a_cessione");
		this.idComune = rs.getInt("id_comune_proprietario_a_cessione");
		this.docIdentita = rs.getString("documento_identita_proprietario_a_cessione");
		this.numeroTelefono = rs.getString("numero_telefono_proprietario_a_cessione");
		
		if (this.getIdProprietario() > 0 || (this.checkPossibilitaImportAutomaticoProprietario() == null || ("").equals(this.checkPossibilitaImportAutomaticoProprietario())) ){
			this.canAccept = true;
		}
		
		this.destinatarioCessioneVecchiaCanina = rs.getString("nome_cognome_destinaziione_vecchia_canina");

	}
	
	protected void buildRecord(ResultSet rs, Connection db) throws SQLException {

		super.buildRecord(rs);
		this.idEvento = rs.getInt("idevento");
		this.id = rs.getInt("id");
		this.dataCessione = rs.getTimestamp("data_cessione");
		this.idProprietario = rs.getInt("id_nuovo_proprietario_cessione");
		this.idVecchioProprietario = rs
				.getInt("id_vecchio_proprietario_cessione");
		this.idNuovoDetentore = rs.getInt("id_nuovo_detentore_cessione");
		this.idVecchioDetentore = rs.getInt("id_vecchio_detentore_cessione");
		this.idAslNuovoProprietario = rs
				.getInt("id_asl_nuovo_proprietario_cessione");
		this.idAslVecchioProprietario = rs
				.getInt("id_asl_vecchio_proprietario_cessione");
		this.flagAccettato = rs.getBoolean("flag_accettato");
		this.nome = rs.getString("nome_proprietario_a_cessione");
		//System.out.println(this.nome);
		this.cognome = rs.getString("cognome_proprietario_a_cessione");
		this.codiceFiscale = rs
				.getString("codice_fiscale_proprietario_a_cessione");
		this.sesso = rs.getString("sesso_proprietario_a_cessione");
		this.cap = rs.getString("cap_proprietario_a_cessione");
		this.dataNascita = rs
				.getTimestamp("data_nascita_proprietario_a_cessione");
		this.luogoNascita = rs
				.getString("luogo_nascita_proprietario_a_cessione");
		this.indirizzo = rs.getString("indirizzo_proprietario_a_cessione");
		this.idProvincia = rs.getInt("id_provincia_proprietario_a_cessione");
		this.idComune = rs.getInt("id_comune_proprietario_a_cessione");
		this.docIdentita = rs.getString("documento_identita_proprietario_a_cessione");
		this.numeroTelefono = rs.getString("numero_telefono_proprietario_a_cessione");
		
		if (this.getIdProprietario() > 0 || (this.checkPossibilitaImportAutomaticoProprietario() == null || ("").equals(this.checkPossibilitaImportAutomaticoProprietario())) ){
			this.canAccept = true;
		}
		
		this.destinatarioCessioneVecchiaCanina = rs.getString("nome_cognome_destinaziione_vecchia_canina");
		
		if (this.getIdProprietario()  > 0) {
			//	
			Operatore	proprietarioDestinatarioRegistrazione = new Operatore();
				proprietarioDestinatarioRegistrazione
						.queryRecordOperatorebyIdLineaProduttiva(db,
								this.getIdProprietario());
				this.operatoreDestinatario = proprietarioDestinatarioRegistrazione;
			}else{
				
				  if (this.getNome() != null && !("").equals(this.getNome())){
					if (this.getNome() != null && !("").equals(this.getNome()))
						destinazioneTestuale = "Nome: ".concat(this.getNome());
					if (this.getCognome() != null && !("").equals(this.getCognome()))
						destinazioneTestuale = destinazioneTestuale.concat(" Cognome: ").concat(this.getCognome());
					if (this.getCodiceFiscale() != null && !("").equals(this.getCodiceFiscale())) 
						destinazioneTestuale = destinazioneTestuale.concat(" C.F.: ").concat(this.getCodiceFiscale());
					if (this.getIdComune() > 0){
						ComuniAnagrafica c = new ComuniAnagrafica();
						ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db,
								-1, 1);
						LookupList comuniList = new LookupList(listaComuni, -1);
						destinazioneTestuale = destinazioneTestuale.concat(" Comune residenza:  ").concat(comuniList.getSelectedValue(this.getIdComune()));
					}if (this.getIndirizzo() != null ){
						destinazioneTestuale = destinazioneTestuale.concat(" Via residenza:  ").concat(this.getIndirizzo());
					}if (this.getIdProvincia() > 0){
						LookupList provinceList = new LookupList(db, "lookup_province");
						destinazioneTestuale = destinazioneTestuale.concat(" Provincia residenza:  ").concat(provinceList.getSelectedValue(this.getIdProvincia()));
					}
				}
				
				
				
				
				
				
				
			}
		
		if (this.getIdVecchioProprietario()  > 0) {
			//	
			Operatore	proprietarioVecchioRegistrazione = new Operatore();
			proprietarioVecchioRegistrazione
						.queryRecordOperatorebyIdLineaProduttiva(db,
								this.getIdVecchioProprietario());
				this.operatoreMittente = proprietarioVecchioRegistrazione;
			}


	}

	public EventoCessione(Connection db, int idEventoPadre) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_cessione f on (e.id_evento = f.id_evento) where e.id_evento = ?");
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
	
	public void GetCessioneApertaByIdAnimale(Connection db, int idAnimale) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e inner join evento_cessione f on (e.id_evento = f.id_evento) where e.id_animale = ? and f.flag_accettato = false and e.data_cancellazione is null and e.trashed_date is null");
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
	
	//usata per recuperare la cessione da riaprire a seguito della cancellazione di una presa in carico
	public void GetUltimaCessioneChiusaByIdAnimale(Connection db, int idAnimale) throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_cessione f on (e.id_evento = f.id_evento) where e.id_animale = ? and f.flag_accettato = true and e.data_cancellazione is null and e.trashed_date is null");
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

	public Operatore getNuovoProprietario() throws UnknownHostException {

		Connection conn = null;
		int idOperatore = -1;
		Operatore operatore = null;
		try {
			Thread t = Thread.currentThread(); conn = GestoreConnessioni.getConnection();

//			String dbName = ApplicationProperties.getProperty("dbnameBdu");
//			String username = ApplicationProperties.getProperty("usernameDbbdu");
//			String pwd = ApplicationProperties.getProperty("passwordDbbdu");
//			String host = InetAddress.getByName("hostDbBdu").getHostAddress();
//
//			conn = DbUtil.getConnection(dbName, username, pwd, host);


			idOperatore = this.getIdProprietario();

			if (idOperatore != -1 && idOperatore != 0) {
				//
				operatore = new Operatore();
				operatore.queryRecordOperatorebyIdLineaProduttiva(conn,
						idOperatore);
			}

			//GestoreConnessioni.freeConnection(conn);
//
//			DbUtil.chiudiConnessioneJDBC(null, null, conn);
//			conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
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

//			String dbName = ApplicationProperties.getProperty("dbnameBdu");
//			String username = ApplicationProperties.getProperty("usernameDbbdu");
//			String pwd = ApplicationProperties.getProperty("passwordDbbdu");
//			String host = InetAddress.getByName("hostDbBdu").getHostAddress();
//
//			conn = DbUtil.getConnection(dbName, username, pwd, host);


			idOperatore = this.getIdVecchioProprietario();

			if (idOperatore != -1 && idOperatore != 0) {
				//
				operatore = new Operatore();
				operatore.queryRecordOperatorebyIdLineaProduttiva(conn,
						idOperatore);
			}

			//GestoreConnessioni.freeConnection(conn);

//			DbUtil.chiudiConnessioneJDBC(null, null, conn);
//			conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			GestoreConnessioni.freeConnection(conn);
		}

		return operatore;
	}
	
	
	
	public String checkPossibilitaImportAutomaticoProprietario(){
		
		String campo_mancante = "";
		
		if (this.getCodiceFiscale() == null || ("").equals(this.getCodiceFiscale())){
			campo_mancante = "codice fiscale";
		}
		
		if (this.getSesso() == null
				|| ("").equals(this.getSesso()) || ("-1").equals(this.getSesso())) {
			campo_mancante = "sesso";
		}
		
		if (this.getNome() == null
				|| ("").equals(this.getNome())) {
			campo_mancante = "nome";
		}

		if (this.getCognome() == null
				|| ("").equals(this.getCognome())) {
			campo_mancante = "cognome";
		}

		if (this.getIdComune() < 0) {
			campo_mancante = "comune residenza";
		}

		if (this.getIdProvincia() < 0) {
			campo_mancante = "provincia residenza";
		}

		if (this.getIndirizzo() == null
				|| ("").equals(this.getIndirizzo())) {
			campo_mancante = "indirizzo residenza";
		}
		
		return campo_mancante;
	}
	public int updateAccettazione(Connection conn) {
		int result = -1;

		try {
			StringBuffer sql = new StringBuffer();

			sql.append("update evento_cessione set flag_accettato = ? where id_evento=?");
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
	
	public EventoCessione salvaRegistrazione(int userId,
			int userRole, int userAsl, Animale thisAnimale, Connection db)
			throws Exception 
	{
		if(Boolean.parseBoolean(ApplicationProperties.getProperty("attivazioneCessioneOneStep"))) 
			return salvaRegistrazioneOneStep(userId, userRole, userAsl, thisAnimale, db);
		else
			return salvaRegistrazioneOld(userId, userRole, userAsl, thisAnimale, db);
		
	}
	
	
	public EventoCessione salvaRegistrazioneOld(int userId,
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
			

			this.setIdVecchioProprietario(oldAnimale
					.getIdProprietario());
			this.setIdAslVecchioProprietario(oldAnimale
					.getIdAslRiferimento());

			// if (oldAnimale.getIdSpecie() == Cane.idSpecie) {

			this.setIdVecchioDetentore(oldAnimale.getIdDetentore());

			// Detentore
			/*
			 * thisCane = new Cane(db, oldAnimale.getIdAnimale()); if
			 * (thisCane.getDetentore() != null) {
			 * cessione.setIdVecchioDetentore(thisCane
			 * .getDetentore().getRappLegale() .getIdSoggetto());
			 * 
			 * }
			 */
			if (this.getIdProprietario() != -1) {

				// thisCane.setIdDetentore(cessione
				// .getIdProprietario());
				// thisCane.setIdDetentore(-1);

				Operatore nuovoProprietario = new Operatore();
				nuovoProprietario
						.queryRecordOperatorebyIdLineaProduttiva(db,
								this.getIdProprietario());

				Stabilimento sedeProprietario = (Stabilimento) nuovoProprietario
						.getListaStabilimenti().get(0);

				thisAnimale.setIdAslRiferimento(sedeProprietario
						.getIdAsl()); // PRENDERE
				// NUOVA
				// ASL
				// DA
				// PROPRIETARIO
				// SELEZIONATO
				this.setIdAslNuovoProprietario(sedeProprietario
						.getIdAsl());
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

			}

			thisAnimale.setIdDetentore(-1);
			thisAnimale.setIdProprietario(-1);



			// VEDERE SE IN SEGUITO BISOGNA SCEGLIERE ANCHE IL
			// DETENTORE;
			// PER ORA LO IMPOSTO AL PROPRIETARIO

			this.insert(db);

			
			aggiornaFlagFuoriDominioAsl(db, thisAnimale,  userAsl,  oldAnimale);
			aggiornaStatoAnimale(db, thisAnimale);

		} catch (Exception e) {
			throw e;
		}

		return this;

	}
	
	public EventoCessione salvaRegistrazioneOneStep(int userId,
			int userRole, int userAsl, Animale thisAnimale, Connection db)
			throws Exception {
		try {

			super.salvaRegistrazione(userId, userRole, userAsl, thisAnimale, db);
			
			Animale oldAnimale = new Animale(db, this.getIdAnimale());

			switch (this.getSpecieAnimaleId())
			{
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
			

			this.setIdVecchioProprietario(oldAnimale.getIdProprietario());
			this.setIdAslVecchioProprietario(oldAnimale.getIdAslRiferimento());
			this.setIdVecchioDetentore(oldAnimale.getIdDetentore());
			this.setFlagAccettato(true);
			
			Operatore nuovoProprietario = new Operatore();
			nuovoProprietario.queryRecordOperatorebyIdLineaProduttiva(db,this.getIdProprietario());

			Stabilimento sedeProprietario = (Stabilimento) nuovoProprietario.getListaStabilimenti().get(0);

			thisAnimale.setIdAslRiferimento(sedeProprietario.getIdAsl()); 
			this.setIdAslNuovoProprietario(sedeProprietario.getIdAsl());

			thisAnimale.setIdDetentore(this.getIdProprietario());
			thisAnimale.setIdProprietario(this.getIdProprietario());

			this.insert(db);

			
			aggiornaFlagFuoriDominioAsl(db, thisAnimale,  userAsl,  oldAnimale);
			aggiornaStatoAnimale(db, thisAnimale);

		} catch (Exception e) {
			throw e;
		}

		return this;

	}
	
	public EventoCessione build(ResultSet rs) throws Exception{
		try{	
			
			super.build(rs);
			buildRecord(rs);
		
		}catch (Exception e){
			throw e;
		}
	return this;
		}
}
