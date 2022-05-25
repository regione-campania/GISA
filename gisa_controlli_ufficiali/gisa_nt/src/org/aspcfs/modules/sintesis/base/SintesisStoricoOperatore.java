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

public class SintesisStoricoOperatore {
	
	private int id =-1;
	private int idUtente = -1;
	private Timestamp dataModifica;
	
	private int idOperatore;
	
	private String domicilioDigitaleOld;
	private String domicilioDigitaleNew;

	private int tipoImpresaOld;
	private int tipoImpresaNew;
	
	private int tipoSocietaOld;
	private int tipoSocietaNew;

	private int idIndirizzoOld; 
	private int idIndirizzoNew;
	
	private SintesisIndirizzo indirizzoOld;
	private SintesisIndirizzo indirizzoNew;
	
	
public SintesisStoricoOperatore (ResultSet rs, Connection db) throws SQLException{
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
	this.tipoImpresaOld = rs.getInt("tipo_impresa_old");
	this.tipoImpresaNew = rs.getInt("tipo_impresa_new");
	this.tipoSocietaOld = rs.getInt("tipo_societa_old");
	this.tipoSocietaNew = rs.getInt("tipo_societa_new");
	this.idIndirizzoOld = rs.getInt("id_indirizzo_old");
	this.idIndirizzoNew = rs.getInt("id_indirizzo_new");
	this.domicilioDigitaleOld = rs.getString("domicilio_digitale_old");
	this.domicilioDigitaleNew = rs.getString("domicilio_digitale_new");
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


public int getIdOperatore() {
	return idOperatore;
}

public void setIdOperatore(int idOperatore) {
	this.idOperatore = idOperatore;
}

public String getDomicilioDigitaleOld() {
	return domicilioDigitaleOld;
}

public void setDomicilioDigitaleOld(String domicilioDigitaleOld) {
	this.domicilioDigitaleOld = domicilioDigitaleOld;
}

public String getDomicilioDigitaleNew() {
	return domicilioDigitaleNew;
}

public void setDomicilioDigitaleNew(String domicilioDigitaleNew) {
	this.domicilioDigitaleNew = domicilioDigitaleNew;
}

public int getTipoImpresaOld() {
	return tipoImpresaOld;
}

public void setTipoImpresaOld(int tipoImpresaOld) {
	this.tipoImpresaOld = tipoImpresaOld;
}

public int getTipoImpresaNew() {
	return tipoImpresaNew;
}

public void setTipoImpresaNew(int tipoImpresaNew) {
	this.tipoImpresaNew = tipoImpresaNew;
}

public int getTipoSocietaOld() {
	return tipoSocietaOld;
}

public void setTipoSocietaOld(int tipoSocietaOld) {
	this.tipoSocietaOld = tipoSocietaOld;
}

public int getTipoSocietaNew() {
	return tipoSocietaNew;
}

public void setTipoSocietaNew(int tipoSocietaNew) {
	this.tipoSocietaNew = tipoSocietaNew;
}

public SintesisStoricoOperatore (){
	
}


}
