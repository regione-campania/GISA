/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.sintesis.base;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SintesisStoricoSoggettoFisico {
	
	private int id =-1;
	private int idUtente = -1;
	private Timestamp dataModifica;
	
	private int idSoggettoFisico;
	
	private String nomeOld;
	private String nomeNew;
	
	private String cognomeOld;
	private String cognomeNew;
	
	private String sessoOld;
	private String sessoNew;
	
	private int nazioneNascitaOld;
	private int nazioneNascitaNew;
	
	private String comuneNascitaOld;
	private String comuneNascitaNew;

	private String codiceFiscaleOld;
	private String codiceFiscaleNew;
	
	private int idIndirizzoOld; 
	private int idIndirizzoNew;
	
	private SintesisIndirizzo indirizzoOld;
	private SintesisIndirizzo indirizzoNew;
	
	private Timestamp dataNascitaOld;
	private Timestamp dataNascitaNew;

	private String emailOld;
	private String emailNew;
	
	
public SintesisStoricoSoggettoFisico (ResultSet rs, Connection db) throws SQLException{
	buildRecord(rs);
	setIndirizzoOld(db);
	setIndirizzoNew(db);
	
}

private void setIndirizzoOld(Connection db) throws SQLException {
	SintesisIndirizzo ind = new SintesisIndirizzo(db, idIndirizzoOld);
	this.indirizzoOld = ind;	
}

private void setIndirizzoNew(Connection db) throws SQLException {
	SintesisIndirizzo ind = new SintesisIndirizzo(db, idIndirizzoNew);
	this.indirizzoNew = ind;		
}


private void buildRecord(ResultSet rs) throws SQLException{
	this.id = rs.getInt("id");
	this.idUtente = rs.getInt("id_utente");
	this.dataModifica = rs.getTimestamp("data_modifica");
	this.nomeOld = rs.getString("nome_old");
	this.nomeNew = rs.getString("nome_new");
	this.cognomeOld = rs.getString("cognome_old");
	this.cognomeNew = rs.getString("cognome_new");
	this.sessoOld = rs.getString("sesso_old");
	this.sessoNew = rs.getString("sesso_new");
	this.dataNascitaOld = rs.getTimestamp("data_nascita_old");
	this.dataNascitaNew = rs.getTimestamp("data_nascita_new");
	this.nazioneNascitaOld = rs.getInt("nazione_nascita_old");
	this.nazioneNascitaNew = rs.getInt("nazione_nascita_new");
	this.comuneNascitaOld = rs.getString("comune_nascita_old");
	this.comuneNascitaNew = rs.getString("comune_nascita_new");
	this.emailOld = rs.getString("email_old");
	this.emailNew = rs.getString("email_new");
	this.idIndirizzoOld = rs.getInt("indirizzo_id_old");
	this.idIndirizzoNew = rs.getInt("indirizzo_id_new");
	this.codiceFiscaleOld = rs.getString("codice_fiscale_old");
	this.codiceFiscaleNew = rs.getString("codice_fiscale_new");
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public int getIdUtente() {
	return idUtente;
}

public void setIdUtente(int idUtente) {
	this.idUtente = idUtente;
}

public Timestamp getDataModifica() {
	return dataModifica;
}

public void setDataModifica(Timestamp dataModifica) {
	this.dataModifica = dataModifica;
}


public int getIdIndirizzoOld() {
	return idIndirizzoOld;
}

public void setIdIndirizzoOld(int idIndirizzoOld) {
	this.idIndirizzoOld = idIndirizzoOld;
}

public int getIdIndirizzoNew() {
	return idIndirizzoNew;
}

public void setIdIndirizzoNew(int idIndirizzoNew) {
	this.idIndirizzoNew = idIndirizzoNew;
}

public SintesisIndirizzo getIndirizzoOld() {
	return indirizzoOld;
}

public void setIndirizzoOld(SintesisIndirizzo indirizzoOld) {
	this.indirizzoOld = indirizzoOld;
}

public SintesisIndirizzo getIndirizzoNew() {
	return indirizzoNew;
}

public void setIndirizzoNew(SintesisIndirizzo indirizzoNew) {
	this.indirizzoNew = indirizzoNew;
}


public int getIdSoggettoFisico() {
	return idSoggettoFisico;
}

public void setIdSoggettoFisico(int idSoggettoFisico) {
	this.idSoggettoFisico = idSoggettoFisico;
}

public String getNomeOld() {
	return nomeOld;
}

public void setNomeOld(String nomeOld) {
	this.nomeOld = nomeOld;
}

public String getNomeNew() {
	return nomeNew;
}

public void setNomeNew(String nomeNew) {
	this.nomeNew = nomeNew;
}

public String getCognomeOld() {
	return cognomeOld;
}

public void setCognomeOld(String cognomeOld) {
	this.cognomeOld = cognomeOld;
}

public String getCognomeNew() {
	return cognomeNew;
}

public void setCognomeNew(String cognomeNew) {
	this.cognomeNew = cognomeNew;
}

public String getSessoOld() {
	return sessoOld;
}

public void setSessoOld(String sessoOld) {
	this.sessoOld = sessoOld;
}

public String getSessoNew() {
	return sessoNew;
}

public void setSessoNew(String sessoNew) {
	this.sessoNew = sessoNew;
}

public int getNazioneNascitaOld() {
	return nazioneNascitaOld;
}

public void setNazioneNascitaOld(int nazioneNascitaOld) {
	this.nazioneNascitaOld = nazioneNascitaOld;
}

public int getNazioneNascitaNew() {
	return nazioneNascitaNew;
}

public void setNazioneNascitaNew(int nazioneNascitaNew) {
	this.nazioneNascitaNew = nazioneNascitaNew;
}

public String getComuneNascitaOld() {
	return comuneNascitaOld;
}

public void setComuneNascitaOld(String comuneNascitaOld) {
	this.comuneNascitaOld = comuneNascitaOld;
}

public String getComuneNascitaNew() {
	return comuneNascitaNew;
}

public void setComuneNascitaNew(String comuneNascitaNew) {
	this.comuneNascitaNew = comuneNascitaNew;
}

public String getCodiceFiscaleOld() {
	return codiceFiscaleOld;
}

public void setCodiceFiscaleOld(String codiceFiscaleOld) {
	this.codiceFiscaleOld = codiceFiscaleOld;
}

public String getCodiceFiscaleNew() {
	return codiceFiscaleNew;
}

public void setCodiceFiscaleNew(String codiceFiscaleNew) {
	this.codiceFiscaleNew = codiceFiscaleNew;
}

public Timestamp getDataNascitaOld() {
	return dataNascitaOld;
}

public void setDataNascitaOld(Timestamp dataNascitaOld) {
	this.dataNascitaOld = dataNascitaOld;
}

public Timestamp getDataNascitaNew() {
	return dataNascitaNew;
}

public void setDataNascitaNew(Timestamp dataNascitaNew) {
	this.dataNascitaNew = dataNascitaNew;
}

public String getEmailOld() {
	return emailOld;
}

public void setEmailOld(String emailOld) {
	this.emailOld = emailOld;
}

public String getEmailNew() {
	return emailNew;
}

public void setEmailNew(String emailNew) {
	this.emailNew = emailNew;
}

public SintesisStoricoSoggettoFisico (){
	
}


}
