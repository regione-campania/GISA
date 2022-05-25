/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.dpat2019.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;


//DAVIDE somma ui e' stato rifattorizzato insieme ai getter e setter come int (da double)
import com.darkhorseventures.framework.beans.GenericBean;

public class DpatStrutturaIndicatore extends GenericBean {

	private static final long serialVersionUID = -7432779662523081409L;

	private int id;
	private int idDpat;
	private int idStruttura;
	private int idIndicatore;
	private int ui;
	private int somma_ui;
	private int entered_by;
	private int modified_by;
	
	private Timestamp entered;
	private Timestamp modified;
	
	private int idSezione;
	private int idPiano;
	private int idAttivita;
	
	private String descrAttivita;
	private String descrIndicatore;
	private String descrSezione;
	private String descrPiano;
	private String descrStruttura;
	
	private Boolean enabled;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdDpat() {
		return idDpat;
	}

	public void setIdDpat(int idDpat) {
		this.idDpat = idDpat;
	}

	public int getIdStruttura() {
		return idStruttura;
	}

	public void setIdStruttura(int idStruttura) {
		this.idStruttura = idStruttura;
	}

	public int getIdIndicatore() {
		return idIndicatore;
	}

	public void setIdIndicatore(int idIndicatore) {
		this.idIndicatore = idIndicatore;
	}

	public int getUi() {
		return ui;
	}

	public void setUi(int ui) {
		this.ui = ui;
	}

	public int getSomma_ui() {
		return somma_ui;
	}

	public void setSomma_ui(int somma_ui) {
		this.somma_ui = somma_ui;
		
	}

	public int getEntered_by() {
		return entered_by;
	}

	public void setEntered_by(int entered_by) {
		this.entered_by = entered_by;
	}

	public int getModified_by() {
		return modified_by;
	}

	public void setModified_by(int modified_by) {
		this.modified_by = modified_by;
	}

	public Timestamp getEntered() {
		return entered;
	}

	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}

	public Timestamp getModified() {
		return modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}

	public String getDescrAttivita() {
		return descrAttivita;
	}

	public void setDescrAttivita(String descrAttivita) {
		this.descrAttivita = descrAttivita;
	}

	public String getDescrIndicatore() {
		return descrIndicatore;
	}

	public void setDescrIndicatore(String descrIndicatore) {
		this.descrIndicatore = descrIndicatore;
	}

	public String getDescrSezione() {
		return descrSezione;
	}

	public void setDescrSezione(String descrSezione) {
		this.descrSezione = descrSezione;
	}

	public String getDescrPiano() {
		return descrPiano;
	}

	public void setDescrPiano(String descrPiano) {
		this.descrPiano = descrPiano;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getDescrizioneById(int id, String tabName ,Connection db){
		String description ="";
		try
		{
			String sql = "select description from "+tabName+" where id="+id;
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next()){
				description = rs.getString("description");
			}
			rs.close();
			pst.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return description;
	}
	
	public void builRecord(int id,Connection db){
		try
		{
			String sql = "select * from dpat_struttura_indicatore where id="+id+" and enabled=true";
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next()){
				this.setId(id);
				this.setIdDpat(rs.getInt("id_dpat"));
				this.setIdStruttura(rs.getInt("id_struttura"));
				this.setIdIndicatore(rs.getInt("id_indicatore"));
				this.setIdSezione(Integer.parseInt(rs.getString("descr_sezione")));
				this.setIdPiano(Integer.parseInt(rs.getString("descr_piano")));
				this.setIdAttivita(Integer.parseInt(rs.getString("descr_attivita")));
				this.setUi(rs.getInt("ui"));

				this.setDescrSezione(this.getDescrizioneById(Integer.parseInt(rs.getString("descr_sezione")), "dpat_sezione", db));
				this.setDescrPiano(this.getDescrizioneById(Integer.parseInt(rs.getString("descr_piano")), "dpat_piano", db));
				this.setDescrAttivita(this.getDescrizioneById(Integer.parseInt(rs.getString("descr_attivita")), "dpat_attivita", db));
				this.setDescrIndicatore(this.getDescrizioneById(Integer.parseInt(rs.getString("descr_indicatore")), "dpat_indicatore", db));
				this.setDescrStruttura(rs.getString("descr_struttura"));
					
				this.setEntered(rs.getTimestamp("entered"));
				this.setModified(rs.getTimestamp("modified"));
				this.setEntered_by(rs.getInt("entered_by"));
				this.setModified_by(rs.getInt("modified_by"));
				this.setEnabled(rs.getBoolean("enabled"));
				this.setSomma_ui(((int) Double.parseDouble(rs.getString("somma_ui"))));			
			}
			rs.close();
			pst.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	
	public void loadResultSet(ResultSet rs,Connection db) throws SQLException
	{
		this.setId(id);
		this.setIdDpat(rs.getInt("id_dpat"));
		this.setIdStruttura(rs.getInt("id_struttura"));
		this.setIdIndicatore(rs.getInt("id_indicatore"));
		this.setIdSezione(Integer.parseInt(rs.getString("descr_sezione")));
		this.setIdPiano(Integer.parseInt(rs.getString("descr_piano")));
		this.setIdAttivita(Integer.parseInt(rs.getString("descr_attivita")));
		this.setUi(rs.getInt("ui"));

		this.setDescrSezione(this.getDescrizioneById(Integer.parseInt(rs.getString("descr_sezione")), "dpat_sezione", db));
		this.setDescrPiano(this.getDescrizioneById(Integer.parseInt(rs.getString("descr_piano")), "dpat_piano", db));
		this.setDescrAttivita(this.getDescrizioneById(Integer.parseInt(rs.getString("descr_attivita")), "dpat_attivita", db));
		this.setDescrIndicatore(this.getDescrizioneById(Integer.parseInt(rs.getString("descr_indicatore")), "dpat_indicatore", db));
		this.setDescrStruttura(rs.getString("descr_struttura"));
			
		this.setEntered(rs.getTimestamp("entered"));
		this.setModified(rs.getTimestamp("modified"));
		this.setEntered_by(rs.getInt("entered_by"));
		this.setModified_by(rs.getInt("modified_by"));
		this.setEnabled(rs.getBoolean("enabled"));
//		this.setSomma_ui(rs.getString("somma_ui"));		
		this.setSomma_ui(((int) Double.parseDouble(rs.getString("somma_ui"))));	
	
	}
	
	public HashMap<Integer,HashMap<Integer, DpatStrutturaIndicatore>> buildLista (Connection db, int idDpat,int idAreaSelezionata){
		HashMap<Integer,HashMap<Integer, DpatStrutturaIndicatore>> dsiList = new HashMap<Integer,HashMap<Integer, DpatStrutturaIndicatore>>();
		try
		{
			String sql = "select * from dpat_struttura_indicatore where id_dpat="+idDpat+" and enabled=true";
			if (idAreaSelezionata>0)
				sql+="  and id_struttura =?";
			PreparedStatement pst = db.prepareStatement(sql);
			if (idAreaSelezionata>0)
				pst.setInt(1, idAreaSelezionata);
			
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				DpatStrutturaIndicatore dsi = new DpatStrutturaIndicatore();
				dsi.loadResultSet(rs, db);
				
				HashMap<Integer, DpatStrutturaIndicatore> valori = dsiList.get(dsi.getIdStruttura());
				if (valori!= null)
				{
					valori.put(dsi.getIdIndicatore(), dsi);
					dsiList.put(dsi.getIdStruttura(), valori);
				}
				else
				{
					valori = new HashMap<Integer, DpatStrutturaIndicatore>();
					valori.put(dsi.getIdIndicatore(), dsi);
					dsiList.put(dsi.getIdStruttura(), valori);
				}
				
//				dsi.builRecord(rs.getInt("id"), db);
				
			}
			rs.close();
			pst.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return dsiList;
	}

	public int getIdSezione() {
		return idSezione;
	}

	public void setIdSezione(int idSezione) {
		this.idSezione = idSezione;
	}

	public int getIdPiano() {
		return idPiano;
	}

	public void setIdPiano(int idPiano) {
		this.idPiano = idPiano;
	}

	public int getIdAttivita() {
		return idAttivita;
	}

	public void setIdAttivita(int idAttivita) {
		this.idAttivita = idAttivita;
	}

	public String getDescrStruttura() {
		return descrStruttura;
	}

	public void setDescrStruttura(String descrStruttura) {
		this.descrStruttura = descrStruttura;
	}
}
