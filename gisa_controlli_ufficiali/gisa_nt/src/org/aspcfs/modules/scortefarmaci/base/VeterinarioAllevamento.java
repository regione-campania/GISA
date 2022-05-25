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

public class VeterinarioAllevamento {
	
	private int id =-1;
	private String codAzienda;
	private String idFiscaleAllevamento;
	private int enteredBy = -1;
	private Timestamp entered;
	private String tipoScortaCodice;
	private String vetPersIdFiscale;
	private String flagResponsabile;
	private String scovetId;


	
public VeterinarioAllevamento (ResultSet rs) throws SQLException{
	buildRecord(rs);
}

private void buildRecord(ResultSet rs) throws SQLException{
	this.id = rs.getInt("id");
	this.codAzienda = rs.getString("cod_azienda");
	this.idFiscaleAllevamento = rs.getString("id_fiscale_allevamento");
	this.enteredBy = rs.getInt("enteredby");
	this.entered = rs.getTimestamp("entered");
	this.tipoScortaCodice = rs.getString("tipo_scorta_codice");
	this.vetPersIdFiscale = rs.getString("vet_pers_id_fiscale");
	this.flagResponsabile = rs.getString("flag_responsabile");
	this.scovetId = rs.getString("scovet_id");

}

public VeterinarioAllevamento (){
	
}

public VeterinarioAllevamento (Connection db, int id) throws SQLException{
	StringBuffer sql = new StringBuffer();
	sql.append("select * from scorta_farmaci_veterinari where id = ?");
	int i = 0;
	PreparedStatement pst;
	pst = db.prepareStatement(sql.toString());
	pst.setInt(++i, id);
	ResultSet rs = pst.executeQuery();
	while (rs.next())
		buildRecord(rs);
}


public void insert (Connection db) throws SQLException{
	
	delete(db);

	StringBuffer sql = new StringBuffer();
	sql.append("INSERT INTO scorta_farmaci_veterinari("
			+ "cod_azienda, id_fiscale_allevamento, entered, enteredby, tipo_scorta_codice, vet_pers_id_fiscale, flag_responsabile, scovet_id "
			+ ") values ("
			+ "?, ?, now(), ?, ?, ?, ?, ? "
			+ ") returning id as id_inserito; ");
	int i = 0;
	PreparedStatement pst;
	try {
		pst = db.prepareStatement(sql.toString());
	
		pst.setString(++i, this.getCodAzienda() );
		pst.setString(++i, this.getIdFiscaleAllevamento() );
		pst.setInt(++i, this.getEnteredBy());
		pst.setString(++i, this.getTipoScortaCodice());
		pst.setString(++i, this.getVetPersIdFiscale());
		pst.setString(++i, this.getFlagResponsabile());
		pst.setString(++i, this.getScovetId());
		ResultSet rs = pst.executeQuery();
		
		if (rs.next())
			this.id = rs.getInt("id_inserito");
		
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


public void delete(Connection db) throws SQLException {
	StringBuffer sql = new StringBuffer();
	sql.append("update scorta_farmaci_veterinari set trashed_date = now() where id = ?");
	int i = 0;
	PreparedStatement pst;
	pst = db.prepareStatement(sql.toString());
	pst.setInt(++i, id);
	pst.executeUpdate();

}


public void updateScovetId(Connection db) throws SQLException {
	StringBuffer sql = new StringBuffer();
	sql.append("update scorta_farmaci_veterinari set scovet_id = ? where id = ?");
	PreparedStatement pst;
	pst = db.prepareStatement(sql.toString());
	pst.setString(1, scovetId);
	pst.setInt(2,  id);
	pst.executeUpdate();
}

public void rollback(Connection db) throws SQLException {
	StringBuffer sql = new StringBuffer();
	PreparedStatement pst;
	int i = 0;
	
	sql.append("update scorta_farmaci_veterinari set trashed_date = now() where id = ?");
	i = 0;
	pst = db.prepareStatement(sql.toString());
	pst.setInt(++i, id);
	pst.executeUpdate();
	
	sql = new StringBuffer();
	sql.append("update scorta_farmaci_veterinari set trashed_date = null where id in (select id from scorta_farmaci_veterinari where trashed_date is not null and cod_azienda = ? and id_fiscale_allevamento = ? and id <> ? order by trashed_date desc limit 1) ");
	i = 0;
	pst = db.prepareStatement(sql.toString());
	pst.setString(++i, codAzienda);
	pst.setString(++i, idFiscaleAllevamento);
	pst.setInt(++i, id);
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


public String getScovetId() {
	return scovetId;
}

public void setScovetId(String scovetId) {
	this.scovetId = scovetId;
}

public String getVetPersIdFiscale() {
	return vetPersIdFiscale;
}

public void setVetPersIdFiscale(String vetPersIdFiscale) {
	this.vetPersIdFiscale = vetPersIdFiscale;
}

public String getFlagResponsabile() {
	return flagResponsabile;
}

public void setFlagResponsabile(String flagResponsabile) {
	this.flagResponsabile = flagResponsabile;
}

public boolean esisteGia(Connection db) throws SQLException { 
	StringBuffer sql = new StringBuffer();
	sql.append("select * from scorta_farmaci_veterinari where vet_pers_id_fiscale = ? and cod_azienda = ? and id_fiscale_allevamento = ? and id <> ?");
	int i = 0;
	PreparedStatement pst;
	pst = db.prepareStatement(sql.toString());
	pst.setString(++i, vetPersIdFiscale);
	pst.setString(++i, codAzienda);
	pst.setString(++i, idFiscaleAllevamento);
	pst.setInt(++i, id);
	ResultSet rs = pst.executeQuery();
	if (rs.next())
		return true;
	return false;
}

}
