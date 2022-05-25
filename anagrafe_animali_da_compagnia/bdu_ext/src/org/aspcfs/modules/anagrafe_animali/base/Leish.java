/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.anagrafe_animali.base;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Leish implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6269259285057368894L;
	/**
	 * 
	 */
	private String identificativo	;
	private Timestamp dataAccettazione	;
	private Timestamp dataPrelievo		;
	private Timestamp dataEsito		;
	private String noteEsito		;
	private String esitoCarattere	;
	private int    assetId 		;
	private Timestamp dataAggiornamento ;
	
	private Animale animale = new Animale();
	
	
	
	public Timestamp getDataAggiornamento() {
		return dataAggiornamento;
	}
	public void setDataAggiornamento(Timestamp dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}
	public void setDataAccettazione(Timestamp dataAccettazione) {
		this.dataAccettazione = dataAccettazione;
	}
	public void setDataPrelievo(Timestamp dataPrelievo) {
		this.dataPrelievo = dataPrelievo;
	}
	public void setDataEsito(Timestamp dataEsito) {
		this.dataEsito = dataEsito;
	}
	public String getIdentificativo() {
		return identificativo;
	}
	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}
	public Timestamp getDataAccettazione() {
		
		return dataAccettazione;
	}
	
	
	public Animale getAnimale() {
		return animale;
	}
	public void setAnimale(Animale animale) {
		this.animale = animale;
	}
	public void setDataAccettazione(String dataAccettazione) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			if (dataAccettazione != null && !dataAccettazione.equals(""))
				this.dataAccettazione = new Timestamp(sdf.parse(dataAccettazione).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Timestamp getDataPrelievo() {
		return dataPrelievo;
	}
	public void setDataPrelievo(String dataPrelievo) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			if (dataPrelievo != null && !dataPrelievo.equals(""))
				this.dataPrelievo = new Timestamp(sdf.parse(dataPrelievo).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Timestamp getDataEsito() {
		return dataEsito;
	}
	public void setDataEsito(String dataEsito) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			if (dataEsito != null && !dataEsito.equals(""))
				this.dataEsito = new Timestamp(sdf.parse(dataEsito).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getNoteEsito() {
		return noteEsito;
	}
	public void setNoteEsito(String noteEsito) {
		this.noteEsito = noteEsito;
	}
	public String getEsitoCarattere() {
		return esitoCarattere;
	}
	public void setEsitoCarattere(String esitoCarattere) {
		this.esitoCarattere = esitoCarattere;
	}
	public int getAssetId() {
		return assetId;
	}
	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}
	
	public Leish(ResultSet rs) throws SQLException
	{
		assetId = rs.getInt("id_animale");
		identificativo = rs.getString("identificativo");
		dataPrelievo = rs.getTimestamp("data_prelievo");
		dataEsito = rs.getTimestamp("data_esito");
		dataAccettazione = rs.getTimestamp("data_accertamento");
		dataAggiornamento = rs.getTimestamp("data_aggiornamento");
		noteEsito = rs.getString("esito_car");
		esitoCarattere = rs.getString("esito");
		
		
	}
	
	
	public static int aggiornaMicrochipEsito(Connection con, String microchipVecchio, String microchipNuovo) throws SQLException{
		
		String update = "update esiti_leishmaniosi set identificativo = ? where identificativo = ? "; 
		PreparedStatement pst = con.prepareStatement(update);
		int i = 0;
		pst.setString(++i, microchipNuovo);
		pst.setString(++i,  microchipVecchio);
		
		int result = pst.executeUpdate();
		
		return result;
		
	}
	
	
	public static String  getUltimoEsitoLeish(Connection con, int idAnimale) throws SQLException{
		String esito = "";
		String sqlSelect = "Select max (data_esito),  esito || ' ' || esito_car as esito  from esiti_leishmaniosi f " +
								" where  f.id_animale = ? group by esito, esito_car ";
		PreparedStatement pst = con.prepareStatement(sqlSelect);
		pst.setInt(1, idAnimale);
		ResultSet rs = pst.executeQuery();
		int i = 0;
		while (rs.next()){
			if (i == 0)
				esito = rs.getString("esito");
			else if (i > 0)
				esito = esito + " - " + rs.getString("esito");
			
			i++;
		}
		
		
		return esito;
	}

}
