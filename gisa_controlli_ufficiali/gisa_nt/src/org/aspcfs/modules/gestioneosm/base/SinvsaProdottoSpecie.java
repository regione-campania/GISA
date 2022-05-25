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

public class SinvsaProdottoSpecie {

private Timestamp data;
	
	private int idProdottoSpecie;
	private String esitoProdottoSpecie;
	
	private int idUtente;
	private int riferimentoId;
	private String riferimentoIdNomeTab;
	private String codiceMacroarea;
	private String codiceAggregazione;
	private String codiceAttivita;

	private String lineaDescrizione;

	
	public SinvsaProdottoSpecie(ResultSet rs) throws SQLException {
	buildRecord(rs);
	}

	private void buildRecord(ResultSet rs) throws SQLException {
		data = rs.getTimestamp("data");
		idProdottoSpecie = rs.getInt("id_prodotto_specie");
		esitoProdottoSpecie = rs.getString("esito_prodotto_specie");
		idUtente = rs.getInt("id_utente");
		riferimentoId = rs.getInt("riferimento_id");
		riferimentoIdNomeTab = rs.getString("riferimento_id_nome_tab");
		codiceMacroarea = rs.getString("codice_macroarea");
		codiceAggregazione = rs.getString("codice_aggregazione");
		codiceAttivita = rs.getString("codice_attivita");
		lineaDescrizione = rs.getString("linea_descrizione");	
	}

	public Timestamp getData() {
		return data;
	}

	public void setData(Timestamp data) {
		this.data = data;
	}

	public int getIdProdottoSpecie() {
		return idProdottoSpecie;
	}

	public void setIdProdottoSpecie(int idProdottoSpecie) {
		this.idProdottoSpecie = idProdottoSpecie;
	}

	public String getEsitoProdottoSpecie() {
		return esitoProdottoSpecie;
	}

	public void setEsitoProdottoSpecie(String esitoProdottoSpecie) {
		this.esitoProdottoSpecie = esitoProdottoSpecie;
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

	public String getLineaDescrizione() {
		return lineaDescrizione;
	}

	public void setLineaDescrizione(String lineaDescrizione) {
		this.lineaDescrizione = lineaDescrizione;
	}

	public String getCodiceAttivita() {
		return codiceAttivita;
	}

	public void setCodiceAttivita(String codiceAttivita) {
		this.codiceAttivita = codiceAttivita;
	}

	
public void insertEsiti(Connection db, int userId, int idImportMassivo) throws SQLException {
		
		PreparedStatement pst = null;
		int i = 0;
		
		String sqlUpdate = "update sinvsa_osm_prodotto_specie_esiti set trashed_date = now() where trashed_date is null and riferimento_id_nome_tab = ? and riferimento_id = ? and codice_macroarea = ? and codice_aggregazione = ? and codice_attivita = ?";
		pst = db.prepareStatement(sqlUpdate);
		pst.setString(++i, riferimentoIdNomeTab);
		pst.setInt(++i, riferimentoId);
		pst.setString(++i, codiceMacroarea);
		pst.setString(++i, codiceAggregazione);
		pst.setString(++i, codiceAttivita);
		pst.executeUpdate();
		
		i = 0;
		String sqlInsert = "insert into sinvsa_osm_prodotto_specie_esiti (id_import_massivo, riferimento_id_nome_tab, riferimento_id, codice_macroarea, codice_aggregazione, codice_attivita, id_utente, id_prodotto_specie, esito_prodotto_specie) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		pst = db.prepareStatement(sqlInsert);
		pst.setInt(++i, idImportMassivo);
		pst.setString(++i, riferimentoIdNomeTab);
		pst.setInt(++i, riferimentoId);
		pst.setString(++i, codiceMacroarea);
		pst.setString(++i, codiceAggregazione);
		pst.setString(++i, codiceAttivita);
		pst.setInt(++i, userId);
		pst.setInt(++i, idProdottoSpecie);
		pst.setString(++i, esitoProdottoSpecie);
		pst.execute();
		
	}
	
}
