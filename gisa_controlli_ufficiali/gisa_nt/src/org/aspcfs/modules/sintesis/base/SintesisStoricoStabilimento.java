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

public class SintesisStoricoStabilimento {
	
	private int id =-1;
	private int idUtente = -1;
	private Timestamp dataModifica;
	
	private String denominazioneOld;
	private String denominazioneNew;

	private int idStabilimento;
	
	private int idOperatoreOld;
	private int idOperatoreNew;

	private SintesisOperatore operatoreOld;
	private SintesisOperatore operatoreNew;
	
	private int idIndirizzoOld; 
	private int idIndirizzoNew;
	
	private SintesisIndirizzo indirizzoOld;
	private SintesisIndirizzo indirizzoNew;
	
	private int statoOld;
	private int statoNew;
	
	private int idRecord;
	private int idImport;
	
	
public SintesisStoricoStabilimento (ResultSet rs, Connection db) throws SQLException{
	buildRecord(rs);
	setOperatoreOld(db);
	setOperatoreNew(db);
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

private void setOperatoreNew(Connection db) throws SQLException {
	SintesisOperatore op = new SintesisOperatore(db, idOperatoreNew);
	this.operatoreNew = op;
}

private void setOperatoreOld(Connection db) throws SQLException {
	SintesisOperatore op = new SintesisOperatore(db, idOperatoreOld);
	this.operatoreOld = op;
	
}

private void buildRecord(ResultSet rs) throws SQLException{
	this.id = rs.getInt("id");
	this.idUtente = rs.getInt("id_utente");
	this.dataModifica = rs.getTimestamp("data_modifica");
	this.statoOld = rs.getInt("stato_old");
	this.statoNew = rs.getInt("stato_new");
	this.idIndirizzoOld = rs.getInt("id_indirizzo_old");
	this.idIndirizzoNew = rs.getInt("id_indirizzo_new");
	this.idOperatoreOld = rs.getInt("id_operatore_old");
	this.idOperatoreNew = rs.getInt("id_operatore_new");
	this.denominazioneNew = rs.getString("denominazione_new");
	this.denominazioneOld = rs.getString("denominazione_old");
	this.idRecord = rs.getInt("id_sintesis_stabilimenti_import");
	this.idImport = rs.getInt("id_import");



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

public String getDenominazioneOld() {
	return denominazioneOld;
}

public void setDenominazioneOld(String denominazioneOld) {
	this.denominazioneOld = denominazioneOld;
}

public String getDenominazioneNew() {
	return denominazioneNew;
}

public void setDenominazioneNew(String denominazioneNew) {
	this.denominazioneNew = denominazioneNew;
}

public int getIdStabilimento() {
	return idStabilimento;
}

public void setIdStabilimento(int idStabilimento) {
	this.idStabilimento = idStabilimento;
}

public int getIdOperatoreOld() {
	return idOperatoreOld;
}

public void setIdOperatoreOld(int idOperatoreOld) {
	this.idOperatoreOld = idOperatoreOld;
}

public int getIdOperatoreNew() {
	return idOperatoreNew;
}

public void setIdOperatoreNew(int idOperatoreNew) {
	this.idOperatoreNew = idOperatoreNew;
}

public SintesisOperatore getOperatoreOld() {
	return operatoreOld;
}

public void setOperatoreOld(SintesisOperatore operatoreOld) {
	this.operatoreOld = operatoreOld;
}

public SintesisOperatore getOperatoreNew() {
	return operatoreNew;
}

public void setOperatoreNew(SintesisOperatore operatoreNew) {
	this.operatoreNew = operatoreNew;
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

public int getStatoOld() {
	return statoOld;
}

public void setStatoOld(int statoOld) {
	this.statoOld = statoOld;
}

public int getStatoNew() {
	return statoNew;
}

public void setStatoNew(int statoNew) {
	this.statoNew = statoNew;
}

public int getIdRecord() {
	return idRecord;
}

public void setIdRecord(int idRecord) {
	this.idRecord = idRecord;
}

public int getIdImport() {
	return idImport;
}

public void setIdImport(int idImport) {
	this.idImport = idImport;
}

public SintesisStoricoStabilimento (){
	
}


}
