/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.scortefarmaci.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ScortaAllevamento {
	
	private int id =-1;
	private String codAzienda;
	private String idFiscaleAllevamento;
	private int enteredBy = -1;
	private Timestamp entered;
	private String tipoScortaCodice;
	private String scortaNumAutorizzazione;
	private String scortaDataInizio;
	private String scortaDataFine;
	private String aslCodice;
	private String lovscortaId;
	
	private ArrayList<VeterinarioAllevamento> veterinariList = new ArrayList<VeterinarioAllevamento>();
	
public ScortaAllevamento (ResultSet rs) throws SQLException{
	buildRecord(rs);
}

private void buildRecord(ResultSet rs) throws SQLException{
	this.id = rs.getInt("id");
	this.codAzienda = rs.getString("cod_azienda");
	this.idFiscaleAllevamento = rs.getString("id_fiscale_allevamento");
	this.enteredBy = rs.getInt("enteredby");
	this.entered = rs.getTimestamp("entered");
	this.tipoScortaCodice = rs.getString("tipo_scorta_codice");
	this.scortaNumAutorizzazione = rs.getString("scorta_num_autorizzazione");
	this.scortaDataInizio = rs.getString("scorta_data_inizio");
	this.scortaDataFine = rs.getString("scorta_data_fine");
	this.aslCodice = rs.getString("asl_codice");
	this.lovscortaId = rs.getString("lovscorta_id");
}

public ScortaAllevamento (){
	
}

public ScortaAllevamento (Connection db, int id) throws SQLException{
	StringBuffer sql = new StringBuffer();
	sql.append("select * from scorta_farmaci where id = ?");
	int i = 0;
	PreparedStatement pst;
	pst = db.prepareStatement(sql.toString());
	pst.setInt(++i, id);
	ResultSet rs = pst.executeQuery();
	while (rs.next()) {
		buildRecord(rs);
		setVeterinariList(db);
	}
}


public void insert (Connection db) throws SQLException{

	delete(db, this.getCodAzienda(), this.getIdFiscaleAllevamento());
	
	StringBuffer sql = new StringBuffer();
	sql.append("INSERT INTO scorta_farmaci("
			+ "cod_azienda, id_fiscale_allevamento, entered, enteredby, tipo_scorta_codice, scorta_num_autorizzazione, scorta_data_inizio, scorta_data_fine, asl_codice, lovscorta_id "
			+ ") values ("
			+ "?, ?, now(), ?, ?, ?, ?, ?, ?, ? "
			+ ") returning id as id_inserito; ");
	int i = 0;
	PreparedStatement pst;
	try {
		pst = db.prepareStatement(sql.toString());
	
		pst.setString(++i, this.getCodAzienda() );
		pst.setString(++i, this.getIdFiscaleAllevamento() );
		pst.setInt(++i, this.getEnteredBy());
		pst.setString(++i, this.getTipoScortaCodice());
		pst.setString(++i, this.getScortaNumAutorizzazione());
		pst.setString(++i, this.getScortaDataInizio());
		pst.setString(++i, this.getScortaDataFine());
		pst.setString(++i, this.getAslCodice());
		pst.setString(++i, this.getLovscortaId());
		ResultSet rs = pst.executeQuery();
		
		if (rs.next()){
			this.id = rs.getInt("id_inserito");
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public int getEnteredBy() {
	return enteredBy;
}

public void setEnteredBy(int enteredBy) {
	this.enteredBy = enteredBy;
}

public Timestamp getEntered() {
	return entered;
}

public void setEntered(Timestamp entered) {
	this.entered = entered;
}

public String getTipoScortaCodice() {
	return tipoScortaCodice;
}

public void setTipoScortaCodice(String tipoScortaCodice) {
	this.tipoScortaCodice = tipoScortaCodice;
}

public String getScortaNumAutorizzazione() {
	return scortaNumAutorizzazione;
}

public void setScortaNumAutorizzazione(String scortaNumAutorizzazione) {
	this.scortaNumAutorizzazione = scortaNumAutorizzazione;
}

public String getScortaDataInizio() {
	return scortaDataInizio;
}

public void setScortaDataInizio(String scortaDataInizio) {
	this.scortaDataInizio = scortaDataInizio;
}



public void queryRecordByCodici(Connection db, String codAzienda, String idFiscaleAllevamento) throws SQLException {
	StringBuffer sql = new StringBuffer();
	sql.append("select * from scorta_farmaci where cod_azienda = ? and id_fiscale_allevamento = ? and trashed_date is null");
	int i = 0;
	PreparedStatement pst;
	pst = db.prepareStatement(sql.toString());
	pst.setString(++i, codAzienda);
	pst.setString(++i, idFiscaleAllevamento);
	ResultSet rs = pst.executeQuery();
	if (rs.next()){
		buildRecord(rs);
		setVeterinariList(db);
	}
}

public void delete(Connection db, String codAzienda, String idFiscaleAllevamento) throws SQLException {
	StringBuffer sql = new StringBuffer();
	sql.append("update scorta_farmaci set trashed_date = now() where cod_azienda = ? and id_fiscale_allevamento = ? and trashed_date is null");
	int i = 0;
	PreparedStatement pst;
	pst = db.prepareStatement(sql.toString());
	pst.setString(++i, codAzienda);
	pst.setString(++i, idFiscaleAllevamento);	
	pst.executeUpdate();

}

public void delete(Connection db) throws SQLException {
	StringBuffer sql = new StringBuffer();
	sql.append("update scorta_farmaci set trashed_date = now() where id = ?");
	int i = 0;
	PreparedStatement pst;
	pst = db.prepareStatement(sql.toString());
	pst.setInt(++i, id);
	pst.executeUpdate();
}

public void rollback(Connection db) throws SQLException {
	StringBuffer sql = new StringBuffer();
	PreparedStatement pst;
	int i = 0;
	
	sql.append("update scorta_farmaci set trashed_date = now() where id = ?");
	i = 0;
	pst = db.prepareStatement(sql.toString());
	pst.setInt(++i, id);
	pst.executeUpdate();
	
	sql = new StringBuffer();
	sql.append("update scorta_farmaci set trashed_date = null where id in (select id from scorta_farmaci where trashed_date is not null and cod_azienda = ? and id_fiscale_allevamento = ? and id <> ? order by trashed_date desc limit 1) ");
	i = 0;
	pst = db.prepareStatement(sql.toString());
	pst.setString(++i, codAzienda);
	pst.setString(++i, idFiscaleAllevamento);
	pst.setInt(++i, id);
	pst.executeUpdate();
	
	
		
}

public void generaNumAutorizzazione(Connection db, String codAzienda, String idFiscaleAllevamento) throws SQLException {
	StringBuffer sql = new StringBuffer();
	sql.append("select * from genera_num_autorizzazione_scorta_farmaci(?, ?)");
	int i = 0;
	PreparedStatement pst;
	pst = db.prepareStatement(sql.toString());
	pst.setString(++i, codAzienda);
	pst.setString(++i, idFiscaleAllevamento);
	ResultSet rs = pst.executeQuery();	
	while (rs.next())
		this.scortaNumAutorizzazione = rs.getString(1);
}

public void updateLovscortaId(Connection db) throws SQLException {
	StringBuffer sql = new StringBuffer();
	sql.append("update scorta_farmaci set lovscorta_id = ? where id = ?");
	PreparedStatement pst;
	pst = db.prepareStatement(sql.toString());
	pst.setString(1, lovscortaId);
	pst.setInt(2,  id);
	pst.executeUpdate();
}



public String getCodAzienda() {
	return codAzienda;
}

public void setCodAzienda(String codAzienda) {
	this.codAzienda = codAzienda;
}

public String getIdFiscaleAllevamento() {
	return idFiscaleAllevamento;
}

public void setIdFiscaleAllevamento(String idFiscaleAllevamento) {
	this.idFiscaleAllevamento = idFiscaleAllevamento;
}


public String getLovscortaId() {
	return lovscortaId;
}

public void setLovscortaId(String lovscortaId) {
	this.lovscortaId = lovscortaId;
}

public ArrayList<VeterinarioAllevamento> getVeterinariList() {
	return veterinariList;
}

public void setVeterinariList(ArrayList<VeterinarioAllevamento> veterinariList) {
	this.veterinariList = veterinariList;
}

public void setVeterinariList(Connection db) throws SQLException {
	StringBuffer sql = new StringBuffer();
	sql.append("select * from scorta_farmaci_veterinari where cod_azienda = ? and id_fiscale_allevamento = ? and trashed_date is null");
	int i = 0;
	PreparedStatement pst;
	pst = db.prepareStatement(sql.toString());
	pst.setString(++i, codAzienda);
	pst.setString(++i, idFiscaleAllevamento);
	ResultSet rs = pst.executeQuery();
	while (rs.next()){
		VeterinarioAllevamento vet = new VeterinarioAllevamento(rs);
		veterinariList.add(vet);
	}
		
}

public String getScortaDataFine() {
	return scortaDataFine;
}

public void setScortaDataFine(String scortaDataFine) {
	this.scortaDataFine = scortaDataFine;
}

public String getAslCodice() {
	return aslCodice;
}

public void setAslCodice(String aslCodice) {
	this.aslCodice = aslCodice;
}


}
