/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.controlliufficiali.base;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


public class DatiCuAcquacolturaBdn {

	int id = -1;
	
	String aslCodice; //asl_codice
	String aziCodice; //azi_codice
	String idFiscaleProprietario;  //prop_id_fiscale
	int gspCodice; //gsp_codice
	int critCodice; //crit_codice
	String dataControllo;  //data_contollo
	String idBdn;  //data_contollo
	
	int enteredby= -1;
	Timestamp entered = null;
	boolean enabled = false;
	int idControllo = -1;
	Timestamp dataInvio = null;
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	public String getIdBdn() {
		return idBdn;
	}


	public void setIdBdn(String idBdn) {
		this.idBdn = idBdn;
	}


	public String getIdFiscaleProprietario() {
		return idFiscaleProprietario;
	}


	public void setIdFiscaleProprietario(String idFiscaleProprietario) {
		this.idFiscaleProprietario = idFiscaleProprietario;
	}


	public int getEnteredby() {
		return enteredby;
	}


	public void setEnteredby(int enteredby) {
		this.enteredby = enteredby;
	}


	public Timestamp getEntered() {
		return entered;
	}


	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public int getIdControllo() {
		return idControllo;
	}


	public void setIdControllo(int idControllo) {
		this.idControllo = idControllo;
	}


	public Timestamp getDataInvio() {
		return dataInvio;
	}


	public void setDataInvio(Timestamp dataInvio) {
		this.dataInvio = dataInvio;
	}

	public String getAslCodice() {
		return aslCodice;
	}

	public void setAslCodice(String aslCodice) {
		this.aslCodice = aslCodice;
	}

	public String getAziCodice() {
		return aziCodice;
	}

	public void setAziCodice(String aziCodice) {
		this.aziCodice = aziCodice;
	}

	public int getGspCodice() {
		return gspCodice;
	}

	public void setGspCodice(int gspCodice) {
		this.gspCodice = gspCodice;
	}

	public int getCritCodice() {
		return critCodice;
	}

	public void setCritCodice(int critCodice) {
		this.critCodice = critCodice;
	}

	public String getDataControllo() {
		return dataControllo;
	}

	public void setDataControllo(String dataControllo) {
		this.dataControllo = dataControllo;
	}
	
	
	private void buildRecord(ResultSet rs) throws SQLException
	{
			id = rs.getInt("id");
			idBdn = rs.getString("id_bdn");
			aslCodice = rs.getString("asl_codice");
			aziCodice = rs.getString("azi_codice");
			idFiscaleProprietario = rs.getString("prop_id_fiscale");
			gspCodice = rs.getInt("gsp_codice");
			critCodice = rs.getInt("crit_codice");
			dataControllo = rs.getString("data_controllo");
			enabled = rs.getBoolean("enabled");
			entered = rs.getTimestamp("entered");
			enteredby = rs.getInt("enteredby");
			dataInvio = rs.getTimestamp("data_invio");
			idControllo = rs.getInt("id_controllo");
	}
	
	public DatiCuAcquacolturaBdn() throws SQLException
	{
	}
	
	public DatiCuAcquacolturaBdn(Connection db, int idControllo) throws SQLException
	{
		PreparedStatement pst = db.prepareStatement("select * from acquacoltura_dati_controlli_bdn where id_controllo = ? and enabled and data_cancellazione is null ");
		pst.setInt(1, idControllo);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			buildRecord(rs);
	}
	
	public void getUltimoControlloInviato(Connection db, int idControllo) throws SQLException
	{
		//Si prende l'ultimo invio fatto in bdn poichè per cancellare il controllo in bdn ho bisogno dei dati che stanno in bdn 
		//e non quelli eventualmente modificati dopo l'invio e rimasti in Gisa

		PreparedStatement pst = db.prepareStatement("select * from acquacoltura_dati_controlli_bdn where id_controllo = ? and id_bdn is not null and data_cancellazione is null order by entered desc limit 1 ");
		pst.setInt(1, idControllo);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			buildRecord(rs);
	}
	
	
	public void insert(Connection db) throws SQLException 
	{
		
		PreparedStatement pst = db.prepareStatement("update acquacoltura_dati_controlli_bdn set enabled = false where id_controllo = ? and enabled = true");
		pst.setInt(1, idControllo);
		pst.executeUpdate();
		
		pst = db.prepareStatement("insert into acquacoltura_dati_controlli_bdn ("
				+ "asl_codice, azi_codice, prop_id_fiscale, gsp_codice, crit_codice, data_controllo, enteredby,  id_controllo) "
				+ "values ("
				+ "?, ?, ?, ?, ?, ?, ?, ? ) returning id as id_inserito;");
		
		int i = 0;
		pst.setString(++i, aslCodice);
		pst.setString(++i, aziCodice);
		pst.setString(++i, idFiscaleProprietario);
		pst.setInt(++i, gspCodice);
		pst.setInt(++i, critCodice);
		pst.setString(++i, dataControllo);
		pst.setInt(++i, enteredby);
		pst.setInt(++i, idControllo);
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			id = rs.getInt("id_inserito");
		}
		
		
	}
	
public void updateDataInvio(Connection db, String idBdn) throws SQLException
{
		
		PreparedStatement pst = db.prepareStatement("update acquacoltura_dati_controlli_bdn set data_invio = now(), id_bdn = ?  where id = ?");
		pst.setString(1, idBdn);
		pst.setInt(2, id);
		pst.executeUpdate();

	}

	public void updateDataCancellazione(Connection db) throws SQLException
	{
		
		PreparedStatement pst = db.prepareStatement("update acquacoltura_dati_controlli_bdn set data_cancellazione = now() where id = ?");
		pst.setInt(1, id);
		pst.executeUpdate();

	}





}

