/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.molluschibivalvi.base;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Concessione implements Serializable {
	
	private String numConcessione			 ;
	private Timestamp dataConcessione 		;
	private Timestamp dataScadenza 			;
	private int idZona ;
	private int idConcessionario ;
	private int enteredBy 						;
	private Concessionario concessionario ;
	private Organization zona ;
	private boolean enabled ;
	private Timestamp trashed ;
	
	private String numeroDecreto ;
	private Timestamp dataDecreto ;
	
	private Timestamp dataSospensione ;
	private int idSospensione;
	
	
	
	
	public Timestamp getDataSospensione() {
		return dataSospensione;
	}
	public void setDataSospensione(Timestamp dataSospensione) {
		this.dataSospensione = dataSospensione;
	}
	public int getIdSospensione() {
		return idSospensione;
	}
	public void setIdSospensione(int idSospensione) {
		this.idSospensione = idSospensione;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Timestamp getTrashed() {
		return trashed;
	}
	public void setTrashed(Timestamp trashed) {
		this.trashed = trashed;
	}
	public Organization getZona() {
		return zona;
	}
	public void setZona(Organization zona) {
		this.zona = zona;
	}
	public Concessionario getConcessionario() {
		return concessionario;
	}
	public void setConcessionario(Concessionario concessionario) {
		this.concessionario = concessionario;
	}
	public int getEnteredBy() {
		return enteredBy;
	}
	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}
	public int getIdConcessionario() {
		return idConcessionario;
	}
	public void setIdConcessionario(int idConcessionario) {
		this.idConcessionario = idConcessionario;
	}
	
	public void setIdConcessionario(String idConcessionario) {
		this.idConcessionario = Integer.parseInt(idConcessionario);
	}
	public String getNumConcessione() {
		return numConcessione;
	}
	public void setNumConcessione(String numConcessione) {
		this.numConcessione = numConcessione;
	}
	public Timestamp getDataConcessione() {
		return dataConcessione;
	}
	public void setDataConcessione(Timestamp dataConcessione) {
		this.dataConcessione = dataConcessione;
	}
	
	public String getDataConcessioneasString() {
		String dataConcessione = "";
		if (this.dataConcessione != null )
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dataConcessione = sdf.format(new Date (this.dataConcessione.getTime()));
			
		}
		return dataConcessione ;
	}
	

	public String getDataSospensioneasString() {
		String dataSospensione = "";
		if (this.dataSospensione != null )
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dataSospensione = sdf.format(new Date (this.dataSospensione.getTime()));
			
		}
		return dataSospensione ;
	}
	
	public String getDataScadenzaasString() {
		String dataScadenza = "";
		if (this.dataScadenza != null )
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dataScadenza = sdf.format(new Date (this.dataScadenza.getTime()));
			
		}
		return dataScadenza ;
	}
	
	
	public String getNumeroDecreto() {
		return numeroDecreto;
	}
	public void setNumeroDecreto(String numeroDecreto) {
		this.numeroDecreto = numeroDecreto;
	}
	public Timestamp getDataDecreto() {
		return dataDecreto;
	}
	public String getDataDecretoasString() {
		String dataDecreto = "";
		if (this.dataDecreto != null )
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dataDecreto = sdf.format(new Date (this.dataDecreto.getTime()));
			
		}
		return dataDecreto ;
	}
	
	public void setDataDecreto(Timestamp dataDecreto) {
			this.dataDecreto = dataDecreto;
			
		
	}
	public void setDataSospensione(String dataSospensione) throws ParseException {
		if (dataSospensione != null && !"".equals(dataSospensione))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.dataSospensione = new Timestamp(sdf.parse(dataSospensione).getTime());
			
		}
	}
	public void setDataDecreto(String dataDecreto) throws ParseException {
		if (dataDecreto != null && !"".equals(dataDecreto))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.dataDecreto = new Timestamp(sdf.parse(dataDecreto).getTime());
			
		}
	}
	public void setDataConcessione(String dataConcessione) throws ParseException {
		if (dataConcessione != null && !"".equals(dataConcessione))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.dataConcessione = new Timestamp(sdf.parse(dataConcessione).getTime());
			
		}
		
	}
	
	public void setDataScadenza(String dataScadenza) throws ParseException {
		if (dataScadenza != null && !"".equals(dataScadenza))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.dataScadenza = new Timestamp(sdf.parse(dataScadenza).getTime());
			
		}
		
	}
	
	public Timestamp getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(Timestamp dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	public int getIdZona() {
		return idZona;
	}
	public void setIdZona(int idZona) {
		this.idZona = idZona;
	}
	
	public void setIdZona(String idZona) {
		this.idZona =Integer.parseInt( idZona);
	}
	
	public void store (Connection db) throws SQLException 
	{
		
		String update = "update concessionari_associati_zone_in_concessione set enabled = false where id_concessionario = ? and id_zona = ? and trashed_date is null ";
		String sql = "INSERT INTO concessionari_associati_zone_in_concessione (id_concessionario, id_zona,  entered_by,num_concessione,data_scadenza,data_concessione,enabled,numero_decreto,data_decreto,data_sospensione,id_sospensione) VALUES (?, ?,?,?,?,?,true,?,?,?,?);";
		PreparedStatement pst2 = db.prepareStatement(update);
		pst2.setInt(1, idConcessionario);
		pst2.setInt(2, idZona);
		pst2.execute();
		
		PreparedStatement pst = db.prepareStatement(sql);
		
		pst.setInt(1, idConcessionario);
		pst.setInt(2, idZona);
		pst.setInt(3, enteredBy);
		pst.setString(4, numConcessione);
		pst.setTimestamp(5, dataScadenza);
		pst.setTimestamp(6, dataConcessione);
		pst.setString(7, numeroDecreto);
		pst.setTimestamp(8, dataDecreto);
		pst.setTimestamp(9, dataSospensione);
		pst.setInt(10, idSospensione);

		pst.execute();
	}
	public static void delete(Connection db, int idConcessionario,int idZona) throws SQLException 
	{
		String sql = "update  concessionari_associati_zone_in_concessione set trashed_Date = current_date where id_concessionario = ? and id_zona = ?";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, idConcessionario);
		pst.setInt(2, idZona);
		pst.execute();
		
	}

}
