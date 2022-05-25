/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.molluschibivalvi.base;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;

import org.aspcfs.utils.DatabaseUtils;


public class HistoryClassificazione implements Serializable{
	
	private int id ;
	private String numDecreto ;
	private Timestamp dataClassificazione ;
	private Timestamp dataFineClassificazione ;
	private Timestamp dataRiattivazione ;

	private Timestamp dataProvvedimento ;
	private int classificazione ;
	private HistoryClassificazioneList listaProvvedimenti = new HistoryClassificazioneList();
	private int tipoZona ;
	private int enteredby ;
	private Timestamp entered ;
	private int idZona ;
	private HashMap<Integer, String> listaMolluschi = new HashMap<Integer, String>() ;
	
	private String motivi = "";
	private HashMap<Integer, String> listaMotivi = new HashMap<Integer, String>() ;
	
	public HistoryClassificazione(){
		
	}
	public void queryRecord(Connection db , int idProvvedimento)
	{
		try {
			ResultSet rs = db.prepareStatement("select * from decreto_classificazione_molluschi where id = "+idProvvedimento).executeQuery();
			if(rs.next())
			buildRecord(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public HistoryClassificazioneList getListaProvvedimenti() {
		return listaProvvedimenti;
	}
	public void setListaProvvedimenti(HistoryClassificazioneList listaProvvedimenti) {
		this.listaProvvedimenti = listaProvvedimenti;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public HistoryClassificazione(ResultSet rs)
	{
		buildRecord(rs);
	}
	public String getNumDecreto() {
		return numDecreto;
	}
	public void setNumDecreto(String numDecreto) {
		this.numDecreto = numDecreto;
	}
	public Timestamp getDataClassificazione() {
		return dataClassificazione;
	}
	public void setDataClassificazione(Timestamp dataClassificazione) {
		this.dataClassificazione = dataClassificazione;
	}
	
	
	
	public Timestamp getDataRiattivazione() {
		return dataRiattivazione;
	}
	public void setDataRiattivazione(Timestamp dataRiattivazione) {
		this.dataRiattivazione = dataRiattivazione;
	}
	public Timestamp getDataProvvedimento() {
		return dataProvvedimento;
	}
	public void setDataProvvedimento(Timestamp dataProvvedimento) {
		this.dataProvvedimento = dataProvvedimento;
	}
	public Timestamp getDataFineClassificazione() {
		return dataFineClassificazione;
	}
	public void setDataFineClassificazione(Timestamp dataFineClassificazione) {
		this.dataFineClassificazione = dataFineClassificazione;
	}
	public int getClassificazione() {
		return classificazione;
	}
	public void setClassificazione(int classificazione) {
		this.classificazione = classificazione;
	}
	public int getTipoZona() {
		return tipoZona;
	}
	public void setTipoZona(int tipoZona) {
		this.tipoZona = tipoZona;
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
	public int getIdZona() {
		return idZona;
	}
	public void setIdZona(int idZona) {
		this.idZona = idZona;
	}
	public HashMap<Integer, String> getListaMolluschi() {
		return listaMolluschi;
	}
	public void setListaMolluschi(HashMap<Integer, String> listaMolluschi) {
		this.listaMolluschi = listaMolluschi;
	}
	
	public void buildRecord(ResultSet rs)
	{
		try{rs.findColumn("id"); id = rs.getInt("id");} catch (SQLException e) {}
		try{rs.findColumn("classe"); classificazione = rs.getInt("classe");} catch (SQLException e) {}
		try{rs.findColumn("tipo_zona_produzione"); tipoZona = rs.getInt("tipo_zona_produzione");} catch (SQLException e) {}
		try{rs.findColumn("id_zona"); idZona = rs.getInt("id_zona");} catch (SQLException e) {}
		try{rs.findColumn("enteredby"); enteredby = rs.getInt("enteredby");} catch (SQLException e) {}
		try{rs.findColumn("num_decreto"); numDecreto = rs.getString("num_decreto");} catch (SQLException e) {}
		try{rs.findColumn("motivi"); setMotivi(rs.getString(""));} catch (SQLException e) {}
		try{rs.findColumn("data_classificazione"); dataClassificazione = rs.getTimestamp("data_classificazione");} catch (SQLException e) {}
		try{rs.findColumn("data_fine_classificazione"); dataFineClassificazione = rs.getTimestamp("data_fine_classificazione");} catch (SQLException e) {}
		try{rs.findColumn("data_provvedimento"); dataProvvedimento = rs.getTimestamp("data_provvedimento");} catch (SQLException e) {}
		try{rs.findColumn("data_riattivazione"); dataRiattivazione = rs.getTimestamp("data_riattivazione");} catch (SQLException e) {}
		try{rs.findColumn("entered"); entered = rs.getTimestamp("entered");} catch (SQLException e) {}
	}
	public HashMap<Integer, String> getListaMotivi() {
		return listaMotivi;
	}
	public void setListaMotivi(HashMap<Integer, String> listaMotivi) {
		this.listaMotivi = listaMotivi;
	}
	public String getMotivi() {
		return motivi;
	}
	public void setMotivi(String motivi) {
		this.motivi = motivi;
	}
	

}
