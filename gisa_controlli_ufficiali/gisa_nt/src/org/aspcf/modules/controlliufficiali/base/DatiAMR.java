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


public class DatiAMR {

	int id = -1;
	
	String numVerbaleAMR;
	String oraInizioPrelievo;
	String oraFinePrelievo;
	String codiceFiscaleRappresentante;
	String telefono;
	String idFiscaleProprietario;
	String ragioneSociale;
	String indirizzo;
	String locale;
	String dataAccasamento;
	String numRegistrazioneProvenienza;
	String capacita;
	String codiceFiscaleVeterinario;
	
	int enteredby= -1;
	Timestamp entered = null;
	boolean enabled = false;
	int idControllo = -1;
	Timestamp dataInvio = null;

	
	private void buildRecord(ResultSet rs) throws SQLException{
	
			id = rs.getInt("id");
			numVerbaleAMR = rs.getString("num_verbale_amr");
			oraInizioPrelievo = rs.getString("ora_inizio_prelievo");
			oraFinePrelievo = rs.getString("ora_fine_prelievo");
			codiceFiscaleRappresentante = rs.getString("codice_fiscale_rappresentante");
			telefono = rs.getString("telefono");
			idFiscaleProprietario = rs.getString("id_fiscale_proprietario");
			ragioneSociale = rs.getString("ragione_sociale");
			indirizzo = rs.getString("indirizzo");
			locale = rs.getString("locale");
			dataAccasamento = rs.getString("data_accasamento");
			numRegistrazioneProvenienza = rs.getString("num_registrazione_provenienza");
			capacita = rs.getString("capacita");
			codiceFiscaleVeterinario = rs.getString("codice_fiscale_veterinario");
			enabled = rs.getBoolean("enabled");
			entered = rs.getTimestamp("entered");
			enteredby = rs.getInt("enteredby");
			dataInvio = rs.getTimestamp("data_invio");

			idControllo = rs.getInt("id_controllo");
	
	}
	
	public DatiAMR() throws SQLException{
		}
	
	public DatiAMR(Connection db, int idControllo) throws SQLException{
		PreparedStatement pst = db.prepareStatement("select * from dati_amr where id_controllo = ? and enabled");
		pst.setInt(1, idControllo);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			buildRecord(rs);
	}
	
	public void insert(Connection db) throws SQLException {
		
		PreparedStatement pst = db.prepareStatement("update dati_amr set enabled = false where id_controllo = ? and enabled = true");
		pst.setInt(1, idControllo);
		pst.executeUpdate();
		
		pst = db.prepareStatement("insert into dati_amr ("
				+ "num_verbale_amr, ora_inizio_prelievo, ora_fine_prelievo, codice_fiscale_rappresentante, telefono, "
				+ "id_fiscale_proprietario, ragione_sociale, indirizzo, locale, codice_fiscale_veterinario,  "
				+ "data_accasamento, num_registrazione_provenienza, capacita,  enteredby,  id_controllo) "
				+ "values ("
				+ "?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?) returning id as id_inserito;");
		
		int i = 0;
		pst.setString(++i, numVerbaleAMR);
		pst.setString(++i, oraInizioPrelievo);
		pst.setString(++i, oraFinePrelievo);
		pst.setString(++i, codiceFiscaleRappresentante);
		pst.setString(++i, telefono);
		pst.setString(++i, idFiscaleProprietario);
		pst.setString(++i, ragioneSociale);
		pst.setString(++i, indirizzo);
		pst.setString(++i, locale);
		pst.setString(++i,  codiceFiscaleVeterinario);
		pst.setString(++i, dataAccasamento);
		pst.setString(++i, numRegistrazioneProvenienza);
		pst.setString(++i, capacita);
		pst.setInt(++i, enteredby);
		pst.setInt(++i, idControllo);
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			id = rs.getInt("id_inserito");
		}
		
		
	}
	
public String getCodiceFiscaleVeterinario() {
		return codiceFiscaleVeterinario;
	}

	public void setCodiceFiscaleVeterinario(String codiceFiscaleVeterinario) {
		this.codiceFiscaleVeterinario = codiceFiscaleVeterinario;
	}

public void updateDataInvio(Connection db) throws SQLException {
		
		PreparedStatement pst = db.prepareStatement("update dati_amr set data_invio = now() where id = ?");
		pst.setInt(1, id);
		pst.executeUpdate();

	}


public int getId() {
	return id;
}


public void setId(int id) {
	this.id = id;
}


public String getOraInizioPrelievo() {
	return oraInizioPrelievo;
}


public void setOraInizioPrelievo(String oraInizioPrelievo) {
	this.oraInizioPrelievo = oraInizioPrelievo;
}


public String getOraFinePrelievo() {
	return oraFinePrelievo;
}


public void setOraFinePrelievo(String oraFinePrelievo) {
	this.oraFinePrelievo = oraFinePrelievo;
}


public String getCodiceFiscaleRappresentante() {
	return codiceFiscaleRappresentante;
}


public void setCodiceFiscaleRappresentante(String codiceFiscaleRappresentante) {
	this.codiceFiscaleRappresentante = codiceFiscaleRappresentante;
}


public String getTelefono() {
	return telefono;
}


public void setTelefono(String telefono) {
	this.telefono = telefono;
}

public String getIdFiscaleProprietario() {
	return idFiscaleProprietario;
}


public void setIdFiscaleProprietario(String idFiscaleProprietario) {
	this.idFiscaleProprietario = idFiscaleProprietario;
}


public String getRagioneSociale() {
	return ragioneSociale;
}


public void setRagioneSociale(String ragioneSociale) {
	this.ragioneSociale = ragioneSociale;
}


public String getIndirizzo() {
	return indirizzo;
}


public void setIndirizzo(String indirizzo) {
	this.indirizzo = indirizzo;
}


public String getLocale() {
	return locale;
}


public void setLocale(String locale) {
	this.locale = locale;
}


public String getDataAccasamento() {
	return dataAccasamento;
}


public void setDataAccasamento(String dataAccasamento) {
	this.dataAccasamento = dataAccasamento;
}


public String getCapacita() {
	return capacita;
}


public void setCapacita(String capacita) {
	this.capacita = capacita;
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


public String getNumVerbaleAMR() {
	return numVerbaleAMR;
}


public void setNumVerbaleAMR(String numVerbaleAMR) {
	this.numVerbaleAMR = numVerbaleAMR;
}

public String getNumRegistrazioneProvenienza() {
	return numRegistrazioneProvenienza;
}

public void setNumRegistrazioneProvenienza(String numRegistrazioneProvenienza) {
	this.numRegistrazioneProvenienza = numRegistrazioneProvenienza;
}
		
	}

