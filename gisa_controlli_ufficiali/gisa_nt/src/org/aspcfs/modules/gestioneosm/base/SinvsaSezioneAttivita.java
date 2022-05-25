/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneosm.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class SinvsaSezioneAttivita {

private Timestamp data;
	
	private int idSezioneAttivita;
	private String esitoSezioneAttivita;
	private ArrayList<SinvsaProdottoSpecie> listaProdottoSpecie = new ArrayList<SinvsaProdottoSpecie>();
	
	private int idUtente;
	private int riferimentoId;
	private String riferimentoIdNomeTab;
	private String codiceMacroarea;
	private String codiceAggregazione;

	private String macroareaDescrizione;
	private String aggregazioneDescrizione;
	
	public SinvsaSezioneAttivita(ResultSet rs) throws SQLException {
	buildRecord(rs);
	}

	private void buildRecord(ResultSet rs) throws SQLException {
		data = rs.getTimestamp("data");
		idSezioneAttivita = rs.getInt("id_sezione_attivita");
		esitoSezioneAttivita = rs.getString("esito_sezione_attivita");
		idUtente = rs.getInt("id_utente");
		riferimentoId = rs.getInt("riferimento_id");
		riferimentoIdNomeTab = rs.getString("riferimento_id_nome_tab");
		codiceMacroarea = rs.getString("codice_macroarea");
		codiceAggregazione = rs.getString("codice_aggregazione");
		macroareaDescrizione = rs.getString("macroarea");
		aggregazioneDescrizione = rs.getString("aggregazione");

	}

	public void setListaProdottoSpecie(Connection db) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from sinvsa_osm_prodotto_specie_view a LEFT JOIN sinvsa_osm_prodotto_specie_esiti e on (a.codice_macroarea = e.codice_macroarea and a.codice_aggregazione = e.codice_aggregazione and a.codice_attivita = e.codice_attivita and a.riferimento_id = e.riferimento_id and a.riferimento_id_nome_tab = e.riferimento_id_nome_tab and e.trashed_date is null) where a.riferimento_id = ? and a.riferimento_id_nome_tab = ? and a.codice_macroarea = ? and a.codice_aggregazione = ?");
		pst.setInt(1, riferimentoId);
		pst.setString(2, riferimentoIdNomeTab);
		pst.setString(3, codiceMacroarea);
		pst.setString(4, codiceAggregazione);
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			SinvsaProdottoSpecie prodottoSpecie = new SinvsaProdottoSpecie(rs);
			listaProdottoSpecie.add(prodottoSpecie);
		}
	}

	public Timestamp getData() {
		return data;
	}

	public void setData(Timestamp data) {
		this.data = data;
	}

	public int getIdSezioneAttivita() {
		return idSezioneAttivita;
	}

	public void setIdSezioneAttivita(int idSezioneAttivita) {
		this.idSezioneAttivita = idSezioneAttivita;
	}

	public String getEsitoSezioneAttivita() {
		return esitoSezioneAttivita;
	}

	public void setEsitoSezioneAttivita(String esitoSezioneAttivita) {
		this.esitoSezioneAttivita = esitoSezioneAttivita;
	}

	public ArrayList<SinvsaProdottoSpecie> getListaProdottoSpecie() {
		return listaProdottoSpecie;
	}

	public void setListaProdottoSpecie(ArrayList<SinvsaProdottoSpecie> listaProdottoSpecie) {
		this.listaProdottoSpecie = listaProdottoSpecie;
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
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

	public String getCodiceMacroarea() {
		return codiceMacroarea;
	}

	public void setCodiceMacroarea(String codiceMacroarea) {
		this.codiceMacroarea = codiceMacroarea;
	}

	public String getCodiceAggregazione() {
		return codiceAggregazione;
	}

	public void setCodiceAggregazione(String codiceAggregazione) {
		this.codiceAggregazione = codiceAggregazione;
	}

	public String getMacroareaDescrizione() {
		return macroareaDescrizione;
	}

	public void setMacroareaDescrizione(String macroareaDescrizione) {
		this.macroareaDescrizione = macroareaDescrizione;
	}

	public String getAggregazioneDescrizione() {
		return aggregazioneDescrizione;
	}

	public void setAggregazioneDescrizione(String aggregazioneDescrizione) {
		this.aggregazioneDescrizione = aggregazioneDescrizione;
	}
	
	public void insertEsiti(Connection db, int userId, int idImportMassivo) throws SQLException {
		
		PreparedStatement pst = null;
		int i = 0;
		
		String sqlUpdate = "update sinvsa_osm_sezione_attivita_esiti set trashed_date = now() where trashed_date is null and riferimento_id_nome_tab = ? and riferimento_id = ? and codice_macroarea = ? and codice_aggregazione = ?";
		pst = db.prepareStatement(sqlUpdate);
		pst.setString(++i, riferimentoIdNomeTab);
		pst.setInt(++i, riferimentoId);
		pst.setString(++i, codiceMacroarea);
		pst.setString(++i, codiceAggregazione);
		pst.executeUpdate();
		
		i = 0;
		String sqlInsert = "insert into sinvsa_osm_sezione_attivita_esiti (id_import_massivo, riferimento_id_nome_tab, riferimento_id, codice_macroarea, codice_aggregazione, id_utente, id_sezione_attivita, esito_sezione_attivita) values (?, ?, ?, ?, ?, ?, ?, ?)";
		pst = db.prepareStatement(sqlInsert);
		pst.setInt(++i, idImportMassivo);
		pst.setString(++i, riferimentoIdNomeTab);
		pst.setInt(++i, riferimentoId);
		pst.setString(++i, codiceMacroarea);
		pst.setString(++i, codiceAggregazione);
		pst.setInt(++i, userId);
		pst.setInt(++i, idSezioneAttivita);
		pst.setString(++i, esitoSezioneAttivita);
		pst.execute();
		
	}
}
