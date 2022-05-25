/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestionecu.base;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcfs.modules.vigilanza.base.ComponenteNucleoIspettivo;

import com.darkhorseventures.framework.beans.GenericBean;

public class Linea extends GenericBean {

	private String macroarea;
	private String aggregazione;
	private String attivita;
	private String codiceMacroarea;
	private String codiceAggregazione;
	private String codiceAttivita;
	private int idIstanza;
	private String numeroLinea;

	public Linea() { 

	}

	public Linea(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	public Linea(Connection db, int riferimentoId, String riferimentoIdNomeTab, int idLinea) throws SQLException {
		String select = "select * from public.get_anagrafica_linee_by_id(?,?,?, -1);";
		PreparedStatement pst = null ;
		ResultSet rs = null;
		pst = db.prepareStatement(select);
		pst.setInt(1, riferimentoId);
		pst.setString(2, riferimentoIdNomeTab);
		pst.setInt(3, idLinea);
		rs = pst.executeQuery();
		if (rs.next()){
			buildRecord(rs);
		}
		}

	private void buildRecord(ResultSet rs) throws SQLException{

		this.macroarea = rs.getString("macroarea");
		this.aggregazione = rs.getString("aggregazione");
		this.attivita = rs.getString("attivita");
		this.codiceMacroarea = rs.getString("codice_macroarea");
		this.codiceAggregazione = rs.getString("codice_aggregazione");
		this.codiceAttivita = rs.getString("codice_attivita");
		this.idIstanza = rs.getInt("id_linea");
		this.numeroLinea = rs.getString("n_linea");
	}

	public static ArrayList<Linea> buildLista(Connection db, int riferimentoId, String riferimentoIdNomeTab, int tecnicaId) {
		ArrayList<Linea> lista = new ArrayList<Linea>();
		try
		{
			String select = "select * from public.get_anagrafica_linee_by_id(?, ?, -1, ?);";
			PreparedStatement pst = null ;
			ResultSet rs = null;
			pst = db.prepareStatement(select);
			pst.setInt(1, riferimentoId);
			pst.setString(2, riferimentoIdNomeTab);
			pst.setInt(3, tecnicaId);
			rs = pst.executeQuery();
			while (rs.next()){
				Linea linea = new Linea(rs);
				lista.add(linea);
			}
		}
		catch(SQLException e)
		{	e.printStackTrace();
		}
		return lista;
	}

	public String getMacroarea() {
		return macroarea;
	}

	public void setMacroarea(String macroarea) {
		this.macroarea = macroarea;
	}

	public String getAggregazione() {
		return aggregazione;
	}

	public void setAggregazione(String aggregazione) {
		this.aggregazione = aggregazione;
	}

	public String getAttivita() {
		return attivita;
	}

	public void setAttivita(String attivita) {
		this.attivita = attivita;
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

	public String getCodiceAttivita() {
		return codiceAttivita;
	}

	public void setCodiceAttivita(String codiceAttivita) {
		this.codiceAttivita = codiceAttivita;
	}

	public int getIdIstanza() {
		return idIstanza;
	}

	public void setIdIstanza(int idIstanza) {
		this.idIstanza = idIstanza;
	}

	public String getNumeroLinea() {
		return numeroLinea;
	}

	public void setNumeroLinea(String numeroLinea) {
		this.numeroLinea = numeroLinea;
	}

}
