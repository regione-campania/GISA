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


public class DatiStatoSanitarioBdn {

	int id = -1;
	
	String aslCodice; //asl_codice
	String aziCodice; //azi_codice
	int gspCodice; //gsp_codice
	int malCodice; //crit_codice
	int qsaCodice;  //data_contollo
	String dtInizioValidita;  //data_contollo
	String idBdn;  //data_contollo
	
	int enteredby= -1;
	Timestamp entered = null;
	boolean enabled = false;
	int idAzienda = -1;
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


	public int getIdAzienda() {
		return idAzienda;
	}


	public void setIdAzienda(int idAzienda) {
		this.idAzienda = idAzienda;
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
	
	public int getMalCodice() {
		return malCodice;
	}

	public void setMalCodice(int malCodice) {
		this.malCodice = malCodice;
	}
	
	public int getQsaCodice() {
		return qsaCodice;
	}

	public void setQsaCodice(int qsaCodice) {
		this.qsaCodice = qsaCodice;
	}

	public String getDtInizioValidita() {
		return dtInizioValidita;
	}

	public void setDtInizioValidita(String dtInizioValidita) {
		this.dtInizioValidita = dtInizioValidita;
	}
	
	private void buildRecord(ResultSet rs) throws SQLException
	{
			id = rs.getInt("id");
			idBdn = rs.getString("id_bdn");
			aslCodice = rs.getString("asl_codice");
			aziCodice = rs.getString("azi_codice");
			gspCodice = rs.getInt("gsp_codice");
			malCodice = rs.getInt("mal_codice");
			qsaCodice = rs.getInt("qsa_codice");
			enabled = rs.getBoolean("enabled");
			entered = rs.getTimestamp("entered");
			enteredby = rs.getInt("enteredby");
			dataInvio = rs.getTimestamp("data_invio");
			idAzienda = rs.getInt("id_azienda");
			dtInizioValidita = rs.getString("dt_inizio_validita");
	}
	
	public DatiStatoSanitarioBdn() throws SQLException
	{
	}
	
	public DatiStatoSanitarioBdn(Connection db, int idAzienda) throws SQLException
	{
		PreparedStatement pst = db.prepareStatement("select * from acquacoltura_dati_stato_sanitario_bdn where id_azienda = ? and enabled and data_cancellazione is null ");
		pst.setInt(1, idAzienda);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			buildRecord(rs);
	}
	
	public void getUltimoStatoSanitarioInviato(Connection db, int idAzienda) throws SQLException
	{
		//Si prende l'ultimo invio fatto in bdn poichè per cancellare lo stato sanitario in bdn ho bisogno dei dati che stanno in bdn 
		//e non quelli eventualmente modificati dopo l'invio e rimasti in Gisa

		PreparedStatement pst = db.prepareStatement("select * from acquacoltura_dati_stato_sanitario_bdn where id_azienda = ? and id_bdn is not null and data_cancellazione is null order by entered desc limit 1 ");
		pst.setInt(1, idAzienda);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			buildRecord(rs);
	}
	
	
	public void insert(Connection db) throws SQLException 
	{
		
		PreparedStatement pst = db.prepareStatement("update acquacoltura_dati_stato_sanitario_bdn set enabled = false where id_azienda = ? and enabled = true");
		pst.setInt(1, idAzienda);
		pst.executeUpdate();
		
		pst = db.prepareStatement("insert into acquacoltura_dati_stato_sanitario_bdn ("
				+ "asl_codice, azi_codice, mal_codice, gsp_codice, qsa_codice, dt_inizio_validita, enteredby,  id_azienda) "
				+ "values ("
				+ "?, ?, ?, ?, ?, ?, ?, ? ) returning id as id_inserito;");
		
		int i = 0;
		pst.setString(++i, aslCodice);
		pst.setString(++i, aziCodice);
		pst.setInt(++i, malCodice);
		pst.setInt(++i, gspCodice);
		pst.setInt(++i, qsaCodice);
		pst.setString(++i, dtInizioValidita);
		pst.setInt(++i, enteredby);
		pst.setInt(++i, idAzienda);
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			id = rs.getInt("id_inserito");
		}
		
		
	}
	
public void updateDataInvio(Connection db, String idBdn) throws SQLException
{
		
		PreparedStatement pst = db.prepareStatement("update acquacoltura_dati_stato_sanitario_bdn set data_invio = now(), id_bdn = ?  where id = ?");
		pst.setString(1, idBdn);
		pst.setInt(2, id);
		pst.executeUpdate();

	}

	public void updateDataCancellazione(Connection db) throws SQLException
	{
		
		PreparedStatement pst = db.prepareStatement("update acquacoltura_dati_stato_sanitario_bdn set data_cancellazione = now() where id = ?");
		pst.setInt(1, id);
		pst.executeUpdate();

	}





}

