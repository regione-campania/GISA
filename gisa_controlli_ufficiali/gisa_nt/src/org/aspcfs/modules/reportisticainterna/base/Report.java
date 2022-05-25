/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.reportisticainterna.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;

import com.darkhorseventures.framework.beans.GenericBean;

public class Report extends GenericBean {

	private int id = -1;
	private String nome = "";
	private String descrizione = "";
	private String queryNativo = "";
	private String queryDbi = "";
	private int countNativo = -1;
	private int countDbi = -1;
	private String campoDistinctNativo = "";
	private String campoDistinctDbi = "";
	private String resultQuery = "";
	
	public Report(){
		
	}
	
	public Report(Connection db, int id) throws SQLException {
		String sqlSelect = "select * from reportistica_interna_report where id = ?";
		PreparedStatement pstSelect = db.prepareStatement(sqlSelect);
		pstSelect.setInt(1, id);
		ResultSet rsSelect = pstSelect.executeQuery();
		if (rsSelect.next()){
			buildRecord(rsSelect);
		}
	}
	

	public Report(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}


	private void buildRecord(ResultSet rs) throws SQLException {
		 id = rs.getInt("id");
		 nome = rs.getString("nome");
		 descrizione = rs.getString("descrizione");
		 queryNativo = rs.getString("query_nativo");
		 queryDbi = rs.getString("query_dbi");
		 campoDistinctNativo = rs.getString("campo_distinct_nativo");
		 campoDistinctDbi = rs.getString("campo_distinct_dbi");
		 
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	public String getQueryNativo() {
		return queryNativo;
	}


	public void setQueryNativo(String queryNativo) {
		this.queryNativo = queryNativo;
	}


	public String getQueryDbi() {
		return queryDbi;
	}


	public void setQueryDbi(String queryDbi) {
		this.queryDbi = queryDbi;
	}

	public int getCountNativo() {
		return countNativo;
	}

	public void setCountNativo(int countNativo) {
		this.countNativo = countNativo;
	}

	public int getCountDbi() {
		return countDbi;
	}

	public void setCountDbi(int countDbi) {
		this.countDbi = countDbi;
	}

	public static ArrayList<Report> getElenco(Connection db) throws SQLException, IndirizzoNotFoundException {
		ArrayList<Report> lista = new  ArrayList<Report>();
	 		
			PreparedStatement pst = db.prepareStatement("select * from reportistica_interna_report where enabled order by level asc");
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				Report report = new Report(rs);
				lista.add(report);		
			}
		return lista;
}


	public void eseguiQueryCount(Connection db, int idAsl) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		pst = db.prepareStatement("select count(distinct " + campoDistinctNativo + ") from ("+queryNativo+") a");
		pst.setInt(1, idAsl);
		rs = pst.executeQuery();
		while(rs.next())
			countNativo = rs.getInt(1);
		
		pst = db.prepareStatement("select count(distinct " + campoDistinctDbi + ") from ("+queryDbi+") a");
		pst.setInt(1, idAsl);
		rs = pst.executeQuery();
		while(rs.next())
			countDbi = rs.getInt(1);

	}
	
	public void eseguiQueryRecordNonPresenti(Connection db, int idAsl, String fonte) throws SQLException {
		
		String queryUno = "";
		String queryDue = "";
		String campoUno = "";
		String campoDue = "";
		String result = "";

		if (fonte.equalsIgnoreCase("nativo")){
			queryUno = queryNativo;
			queryDue = queryDbi;
			campoUno = campoDistinctNativo;
			campoDue = campoDistinctDbi;
		}
		else if (fonte.equalsIgnoreCase("dbi")){
			queryDue = queryNativo;
			queryUno = queryDbi;
			campoDue = campoDistinctNativo;
			campoUno = campoDistinctDbi;
		}
		
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		pst = db.prepareStatement("select * from ("+queryUno+") aa where " + campoUno + " not in (select distinct " + campoDue + " from ("+queryDue+") bb )");
		pst.setInt(1, idAsl);
		pst.setInt(2, idAsl);
		rs = pst.executeQuery();
		
		ResultSetMetaData rsmd = rs.getMetaData();
		for (int i = 1; i<=rsmd.getColumnCount(); i++)
			result += rsmd.getColumnName(i)+";;";
		result +="::";
		while(rs.next()) {
			for (int i = 1; i<=rsmd.getColumnCount(); i++)
				result += rs.getString(i)+";;";
			result +="::";
	} 
		resultQuery = result;

	}
	
	public void eseguiQuery(Connection db, int idAsl, String fonte) throws SQLException {
		
		String result = "";
	
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		String query = "";

		if (fonte.equalsIgnoreCase("nativo"))
			query = queryNativo;
		else if (fonte.equalsIgnoreCase("dbi"))
			query= queryDbi;
		
		pst = db.prepareStatement(query);
		pst.setInt(1, idAsl);
		rs = pst.executeQuery();
		
		ResultSetMetaData rsmd = rs.getMetaData();
		for (int i = 1; i<=rsmd.getColumnCount(); i++)
			result += rsmd.getColumnName(i)+";;";
		result +="::";
		while(rs.next()) {
			for (int i = 1; i<=rsmd.getColumnCount(); i++)
				result += rs.getString(i)+";;";
			result +="::";
	} 
		resultQuery = result;
	}


	

	public String getResultQuery() {
		return resultQuery;
	}

	public void setResultQuery(String resultQuery) {
		this.resultQuery = resultQuery;
	}

	public String getCampoDistinctNativo() {
		return campoDistinctNativo;
	}

	public void setCampoDistinctNativo(String campoDistinctNativo) {
		this.campoDistinctNativo = campoDistinctNativo;
	}

	public String getCampoDistinctDbi() {
		return campoDistinctDbi;
	}

	public void setCampoDistinctDbi(String campoDistinctDbi) {
		this.campoDistinctDbi = campoDistinctDbi;
	}

			
}
