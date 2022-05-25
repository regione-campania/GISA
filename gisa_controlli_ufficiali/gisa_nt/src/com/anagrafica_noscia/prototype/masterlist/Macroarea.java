/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package com.anagrafica_noscia.prototype.masterlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;


public class Macroarea {
	
	public Integer getIdMacroarea() {
		return idMacroarea;
	}
	public void setIdMacroarea(Integer idMacroarea) {
		this.idMacroarea = idMacroarea;
	}
	public String getCodiceSezioneMacroarea() {
		return codiceSezioneMacroarea;
	}
	public void setCodiceSezioneMacroarea(String codiceSezioneMacroarea) {
		this.codiceSezioneMacroarea = codiceSezioneMacroarea;
	}
	public String getCodiceNormaMacroarea() {
		return codiceNormaMacroarea;
	}
	public void setCodiceNormaMacroarea(String codiceNormaMacroarea) {
		this.codiceNormaMacroarea = codiceNormaMacroarea;
	}
	public String getMacroareaNorma() {
		return macroareaNorma;
	}
	public void setMacroareaNorma(String macroareaNorma) {
		this.macroareaNorma = macroareaNorma;
	}
	public String getMacroarea() {
		return macroarea;
	}
	public void setMacroarea(String macroarea) {
		this.macroarea = macroarea;
	}
	
	private Integer idMacroarea;
	private String codiceSezioneMacroarea;
	private String codiceNormaMacroarea;
	private String macroareaNorma;
	private String macroarea;
	private HashMap<Integer,Aggregazione> figli = new HashMap<Integer,Aggregazione>();
	
	public Macroarea(){}
	public Macroarea(ResultSet rs) throws SQLException
	{
		setIdMacroarea(rs.getInt("id_macroarea"));
		setCodiceSezioneMacroarea(rs.getString("codice_sezione_macroarea"));
		setCodiceNormaMacroarea(rs.getString("codice_norma_macroarea"));
		setMacroareaNorma(rs.getString("macroarea_norma"));
		setMacroarea(rs.getString("macroarea"));
	}
	
	public static Macroarea getByOid(Integer oid, Connection conn) throws Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		Macroarea toRet = null;
		
		try
		{
			pst = conn.prepareStatement("select * from MASTER_LIST_DENORM where id_macroarea = ?");
			pst.setInt(1, oid);
			rs = pst.executeQuery();
			rs.next();
			toRet = new Macroarea(rs);
			return toRet;
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
			try{rs.close();}catch(Exception ex){}
		}
	}
	
	public void addFiglio(Aggregazione toAdd)
	{
		this.figli.put(toAdd.getIdAggregazione(),toAdd);
	}
	
	
	
	public JSONObject asJSONOb()
	{

		JSONObject ob = new JSONObject();
		
		
		ob.put("desc", getMacroarea());
		ob.put("id", getIdMacroarea()+"");
		ob.put("campi_estesi", new JSONObject()); /*non e' necessario popolarli qui */
		
		JSONObject figliAsJSONOb = new JSONObject();
		for(Map.Entry<Integer, Aggregazione> figlio : figli.entrySet())
		{
			figliAsJSONOb.put (figlio.getKey()+"", figlio.getValue() .asJSONOb() );
		}
		
		ob.put("figli", figliAsJSONOb); /*no figli oltre terzo livello per ora */
		
		
		return ob;
	
	}
	
}
