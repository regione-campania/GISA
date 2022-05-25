/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.richiesteerratacorrige.base;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;


public class RichiestaErrataCorrige {
	
	private int id = -1;
	private int riferimentoId = -1;
	private String riferimentoIdNomeTab="";
	private int idAsl = -1;
	private int idUtente = -1;
	private Timestamp data = null;
	private String note = null;
	private String telefono = null;
	private String mail = null;
	private String headerDocumento="";
	private int idLookupMotivoCorrezione = -1;
	
	private ArrayList<RichiestaErrataCorrigeCampo> campi = new ArrayList<RichiestaErrataCorrigeCampo>();
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRiferimentoId() {
		return riferimentoId;
	}

	public void setRiferimentoId(int riferimentoId) {
		this.riferimentoId = riferimentoId;
	}

	public String getRiferimentoIdNomeTab() {
		return riferimentoIdNomeTab;
	}

	public void setRiferimentoIdNomeTab(String riferimentoIdNomeTab) {
		this.riferimentoIdNomeTab = riferimentoIdNomeTab;
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public Timestamp getData() {
		return data;
	}

	public void setData(Timestamp data) {
		this.data = data;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getHeaderDocumento() {
		return headerDocumento;
	}

	public void setHeaderDocumento(String headerDocumento) {
		this.headerDocumento = headerDocumento;
	}

	public int getIdAsl() {
		return idAsl;
	}

	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}

	public int getIdLookupMotivoCorrezione() {
		return idLookupMotivoCorrezione;
	}

	public void setIdLookupMotivoCorrezione(int idLookupMotivoCorrezione) {
		this.idLookupMotivoCorrezione = idLookupMotivoCorrezione;
	}
	public void setIdLookupMotivoCorrezione(String idLookupMotivoCorrezione) {
		try {
		this.idLookupMotivoCorrezione = Integer.parseInt(idLookupMotivoCorrezione); 
		} catch (Exception e) {}
	}

	public String getTelefono() {
		return telefono;
	}
 
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public ArrayList<RichiestaErrataCorrigeCampo> getCampi() {
		return campi;
	}

	public void setCampi(ArrayList<RichiestaErrataCorrigeCampo> campi) {
		this.campi = campi;
	}

	public RichiestaErrataCorrige() {
 
	}
	
	public RichiestaErrataCorrige(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}
	
	private void buildRecord(ResultSet rs) throws SQLException{
		this.id = rs.getInt("id");
		this.riferimentoId = rs.getInt("riferimento_id");
		this.riferimentoIdNomeTab = rs.getString("riferimento_id_nome_tab");
		this.idAsl = rs.getInt("id_asl");
		this.idUtente = rs.getInt("id_utente");
		this.data = rs.getTimestamp("data");
		this.note = rs.getString("note");
		this.telefono = rs.getString("telefono");
		this.mail = rs.getString("mail");
		this.idLookupMotivoCorrezione = rs.getInt("id_richieste_errata_corrige_lookup_motivo_correzione");
		this.headerDocumento = rs.getString("header_documento");
	}

	public RichiestaErrataCorrige(Connection db, int id2) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from richieste_errata_corrige where id = ?");
		pst.setInt(1, id2);
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			buildRecord(rs);
			setCampi(db);
		}
		
	}

	public void setCampi(Connection db) throws SQLException {
		ArrayList<RichiestaErrataCorrigeCampo> campi = new ArrayList<RichiestaErrataCorrigeCampo>(); 
		PreparedStatement pst = db.prepareStatement("select * from richieste_errata_corrige_campi where id_richieste_errata_corrige = ? order by id asc");
		pst.setInt(1, this.id);
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			RichiestaErrataCorrigeCampo campo = new RichiestaErrataCorrigeCampo(rs);
			campi.add(campo);
		}
		setCampi(campi);
	}

	public void insert(Connection db) throws SQLException {
		PreparedStatement pst = db.prepareStatement("insert into richieste_errata_corrige (riferimento_id, riferimento_id_nome_tab, id_asl, id_utente, data, id_richieste_errata_corrige_lookup_motivo_correzione, note, telefono, mail) values (?, ?, ?, ?, now(), ?, ?, ?, ?) returning id as id_inserito");
		int i = 0;
		pst.setInt(++i, riferimentoId);
		pst.setString(++i, riferimentoIdNomeTab);
		pst.setInt(++i, idAsl);
		pst.setInt(++i, idUtente);
		pst.setInt(++i, idLookupMotivoCorrezione);
		pst.setString(++i, note);
		pst.setString(++i, telefono);
		pst.setString(++i, mail);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			this.id = rs.getInt("id_inserito");
	}

	public void updateHeaderDocumento(Connection db) throws SQLException {
		PreparedStatement pst = db.prepareStatement("update richieste_errata_corrige set header_documento = ? where id = ?");
		int i = 0;
		pst.setString(++i, headerDocumento);
		pst.setInt(++i, id);
		pst.executeUpdate();		
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) { 
		this.mail = mail;
	}
	




}




	

