/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.sintesis.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.aspcfs.utils.DateUtils;

import com.darkhorseventures.framework.beans.GenericBean;

public class SintesisSoggettoFisico extends GenericBean  {

	private int id;
	private String nome;
	private String cognome;
	private String comuneNascita;
	private String codiceFiscale;
	private String sesso;
	private Timestamp dataNascita;
	private int nazioneNascita;

	private SintesisIndirizzo indirizzo;
	private int idIndirizzo;
	private String domicilioDigitale;
	
	private int enteredBy;
	private int modifiedBy;
	private Timestamp entered;
	public int getEnteredBy() {
		return enteredBy;
	}


	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}


	public int getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public Timestamp getEntered() {
		return entered;
	}


	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}


	public Timestamp getModified() {
		return modified;
	}


	public void setModified(Timestamp modified) {
		this.modified = modified;
	}


	private Timestamp modified;
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCognome() {
		return cognome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
	}


	public String getComuneNascita() {
		return comuneNascita;
	}


	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
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


	public Timestamp getDataNascita() {
		return dataNascita;
	}


	public void setDataNascita(Timestamp dataNascita) {
		this.dataNascita = dataNascita;
	}

	public void setDataNascita(String data) {
		this.dataNascita = DateUtils.parseDateStringNew(data, "dd/MM/yyyy");
	}
	
	public SintesisIndirizzo getIndirizzo() {
		return indirizzo;
	}


	public void setIndirizzo(SintesisIndirizzo indirizzo) {
		this.indirizzo = indirizzo;
	}


	public int getIdIndirizzo() {
		return idIndirizzo;
	}


	public void setIdIndirizzo(int idIndirizzo) {
		this.idIndirizzo = idIndirizzo;
	}


	public String getDomicilioDigitale() {
		return domicilioDigitale;
	}


	public void setDomicilioDigitale(String domicilioDigitale) {
		this.domicilioDigitale = domicilioDigitale;
	}


	
	
	public int getNazioneNascita() {
		return nazioneNascita;
	}


	public void setNazioneNascita(int nazioneNascita) {
		this.nazioneNascita = nazioneNascita;
	}
	
	public void setNazioneNascita(String nazioneNascita) {
		try {this.nazioneNascita = Integer.parseInt(nazioneNascita);} catch (Exception e){};
	}


	public SintesisSoggettoFisico(Connection db, int id) throws SQLException {

		PreparedStatement pst = db.prepareStatement("select * from sintesis_soggetto_fisico where id = ?");
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			buildRecord(rs);
			setIndirizzo(db);
		}
	}


	public SintesisSoggettoFisico() {
		// TODO Auto-generated constructor stub
	}


	private void setIndirizzo(Connection db) throws SQLException {
		SintesisIndirizzo ind = new SintesisIndirizzo(db, this.idIndirizzo);
		this.indirizzo = ind;
		
	}


	private void buildRecord(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.nome = rs.getString("nome");
		this.cognome = rs.getString("cognome");
		this.sesso = rs.getString("sesso");
		this.dataNascita = rs.getTimestamp("data_nascita");
		this.comuneNascita = rs.getString("comune_nascita");
		this.nazioneNascita = rs.getInt("nazione_nascita");
		this.codiceFiscale = rs.getString("codice_fiscale");
		this.domicilioDigitale = rs.getString("email");
		this.idIndirizzo = rs.getInt("indirizzo_id");
		
		this.enteredBy = rs.getInt("enteredby");
		this.modifiedBy = rs.getInt("modifiedby");
		this.entered = rs.getTimestamp("entered");
		this.modified = rs.getTimestamp("modified");

		
		}


	public void insertSoggetto(Connection db) throws SQLException {

		int idInsertSoggetto = -1;
		
		String sqlSelect = "select id from sintesis_soggetto_fisico where codice_fiscale ilike ?";
		PreparedStatement pstSelect = db.prepareStatement(sqlSelect);
		pstSelect.setString(1, codiceFiscale);
		
		ResultSet rsSelect = pstSelect.executeQuery();
		if (rsSelect.next())
			idInsertSoggetto = rsSelect.getInt("id");
		
		if (idInsertSoggetto == -1){
			String sqlInsert = "insert into sintesis_soggetto_fisico (nome, cognome, codice_fiscale, data_nascita, comune_nascita, nazione_nascita, sesso, email, indirizzo_id, entered)  values (?, ?, ?, ?, ?, ?, ?, ?, ?, now()) returning id as id_inserito";
			PreparedStatement pstInsert = db.prepareStatement(sqlInsert);
			int i = 0;
			pstInsert.setString(++i, nome);
			pstInsert.setString(++i, cognome);
			pstInsert.setString(++i, codiceFiscale);
			pstInsert.setTimestamp(++i, dataNascita);
			pstInsert.setString(++i, comuneNascita);
			pstInsert.setInt(++i, nazioneNascita);
			pstInsert.setString(++i, sesso);
			pstInsert.setString(++i, domicilioDigitale);
			pstInsert.setInt(++i, idIndirizzo);
			
			ResultSet rsInsert = pstInsert.executeQuery();
			if (rsInsert.next())
				idInsertSoggetto = rsInsert.getInt("id_inserito");
		}
			id = idInsertSoggetto;
			
	}


	public boolean isDiversoCompleta(Connection db, SintesisSoggettoFisico soggetto) {
		boolean idDiverso = false;
		
		String cfOld = this.getCodiceFiscale();
		cfOld = fixString(cfOld);
		String cfNew = soggetto.getCodiceFiscale();
		cfNew = fixString(cfNew);
		
		String nomeOld = this.getNome();
		nomeOld = fixString(nomeOld);
		String nomeNew = soggetto.getNome();
		nomeNew = fixString(nomeNew);
		
		String cognomeOld = this.getCognome();
		cognomeOld = fixString(cognomeOld);
		String cognomeNew = soggetto.getCognome();
		cognomeNew = fixString(cognomeNew);
		
		String sessoOld = this.getSesso();
		sessoOld = fixString(sessoOld);
		String sessoNew = soggetto.getSesso();
		sessoNew = fixString(sessoNew);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dataNascitaOld  = "";
		if (this.getDataNascita()!=null)
			dataNascitaOld = dateFormat.format(this.getDataNascita());
		String dataNascitaNew  = "";
		if (soggetto.getDataNascita()!=null)
				dataNascitaNew = dateFormat.format(soggetto.getDataNascita());
		dataNascitaOld = fixString(dataNascitaOld);
		dataNascitaNew = fixString(dataNascitaNew);
		
		String comuneNascitaOld = this.getComuneNascita();
		comuneNascitaOld = fixString(comuneNascitaOld);
		String comuneNascitaNew = soggetto.getComuneNascita();
		comuneNascitaNew = fixString(comuneNascitaNew);
		
		String domicilioDigitaleOld = this.getDomicilioDigitale();
		domicilioDigitaleOld = fixString(domicilioDigitaleOld);
		String domicilioDigitaleNew = soggetto.getDomicilioDigitale();
		domicilioDigitaleNew = fixString(domicilioDigitaleNew);
		
			if (!(cfOld.equalsIgnoreCase(cfNew))){
				idDiverso = true;
			}
			if (this.getIdIndirizzo()!=soggetto.getIdIndirizzo()){
				idDiverso = true;
			}
			if (!(comuneNascitaOld.equalsIgnoreCase(comuneNascitaNew))){
				idDiverso = true;
			}
			
			if (!(nomeOld.equalsIgnoreCase(nomeNew))){
				idDiverso = true;
			}
			if (!(cognomeOld.equalsIgnoreCase(cognomeNew))){
				idDiverso = true;
			}
			if (!(dataNascitaOld.equalsIgnoreCase(dataNascitaNew))){
				idDiverso = true;
			}
			
			if (!(sessoOld.equalsIgnoreCase(sessoNew))){
				idDiverso = true;
			}
			
			if (!(domicilioDigitaleOld.equalsIgnoreCase(domicilioDigitaleNew))){
				idDiverso = true;
			}
			
			
			return idDiverso;
		}
	
	public void aggiornaSoggettoCompleta(Connection db, int idOperatore) throws SQLException {
		
		//CERCO SE LO STESSO SOGGETTO E' PRESENTE SU ALTRI OPERATORI
		
		PreparedStatement pstSelect = db.prepareStatement("select * from sintesis_rel_operatore_soggetto_fisico where id_soggetto_fisico = ? and id_operatore <> ? and enabled");
		int i = 0;
		pstSelect.setInt(++i, this.id);
		pstSelect.setInt(++i, idOperatore);
		ResultSet rsSelect  = pstSelect.executeQuery();
		
		if (this.id<=0 ||rsSelect.next()) { //SE E' PRESENTE, AGGIORNO LA VECCHIA RELAZIONE, INSERISCO UN NUOVO SOGGETTO, INSERISCO UNA NUOVA RELAZIONE
			
			PreparedStatement pstUpdateRel = db.prepareStatement("update sintesis_rel_operatore_soggetto_fisico set data_fine = now(), enabled = false where enabled and id_soggetto_fisico = ? and id_operatore = ?");
			i = 0;
			pstUpdateRel.setInt(++i, this.id);
			pstUpdateRel.setInt(++i, idOperatore);
			pstUpdateRel.executeUpdate();
			
			PreparedStatement pstInsertSog = db.prepareStatement("insert into sintesis_soggetto_fisico (nome, cognome,comune_nascita, nazione_nascita, data_nascita, sesso, codice_fiscale,  email, indirizzo_id, enteredby) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) returning id as id_inserito");
			i = 0;
			pstInsertSog.setString(++i, nome);
			pstInsertSog.setString(++i, cognome);
			pstInsertSog.setString(++i, comuneNascita);
			pstInsertSog.setInt(++i, nazioneNascita);
			pstInsertSog.setTimestamp(++i, dataNascita);
			pstInsertSog.setString(++i, sesso);
			pstInsertSog.setString(++i, codiceFiscale);
			pstInsertSog.setString(++i, domicilioDigitale);
			pstInsertSog.setInt(++i, idIndirizzo);
			pstInsertSog.setInt(++i, modifiedBy);
			ResultSet rsInsertSog = pstInsertSog.executeQuery();
			
			if (rsInsertSog.next())
				this.id = rsInsertSog.getInt("id_inserito");
			
			PreparedStatement pstInsertRel = db.prepareStatement("insert into sintesis_rel_operatore_soggetto_fisico (data_inizio, id_operatore, enabled, id_soggetto_fisico, tipo_soggetto_fisico) values (now(), ?, true, ?, 1)");
			i = 0;
			pstInsertRel.setInt(++i, idOperatore);
			pstInsertRel.setInt(++i, this.id);
			pstInsertRel.executeUpdate();
			 
		} else { //SE NON E' PRESENTE, AGGIORNO IL SOGGETTO
			PreparedStatement pstUpdateSog = db.prepareStatement("update sintesis_soggetto_fisico set nome = ?, cognome = ?, comune_nascita = ?, nazione_nascita = ?, data_nascita = ?, sesso = ?, codice_fiscale = ?,  email = ?, indirizzo_id = ?, modifiedby = ?, modified = now() where id = ?");
			i = 0;
			pstUpdateSog.setString(++i, nome);
			pstUpdateSog.setString(++i, cognome);
			pstUpdateSog.setString(++i, comuneNascita);
			pstUpdateSog.setInt(++i, nazioneNascita);
			pstUpdateSog.setTimestamp(++i, dataNascita);
			pstUpdateSog.setString(++i, sesso);
			pstUpdateSog.setString(++i, codiceFiscale);
			pstUpdateSog.setString(++i, domicilioDigitale);
			pstUpdateSog.setInt(++i, idIndirizzo);
			pstUpdateSog.setInt(++i, modifiedBy);
			pstUpdateSog.setInt(++i, id);
			pstUpdateSog.executeUpdate();
		}
		
		
		
		
		
		
	}
	

	
private String fixString(String text){
	if (text==null)
		return "";
	return text;
}

}
