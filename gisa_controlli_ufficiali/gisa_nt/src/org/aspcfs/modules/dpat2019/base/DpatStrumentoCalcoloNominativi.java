/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.dpat2019.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.utils.DatabaseUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.darkhorseventures.framework.beans.GenericBean;
import com.google.gson.JsonObject;

public class DpatStrumentoCalcoloNominativi extends GenericBean {
	private int id ;
	private int idStrumentoCalcoloStruttura ;
	private int idAnagraficaNominativo ;
	private User nominativo=new User() ;
	
	private int idLookupQualifica ;
	private int somma_parziale ; 
	
	private Timestamp dataScadenza ;
	
	private int codiceInternoFK ;
	
	private int stato = -1;
	
	public User getNominativo() {
		return nominativo;
	}
	public void setNominativo(User nominativo) {
		if (nominativo!=null)
		this.nominativo = nominativo;
	}
	
	
	public int getStato() {
		return stato;
	}
	public void setStato(int stato) {
		this.stato = stato;
	}
	public int getCodiceInternoFK() {
		return codiceInternoFK;
	}
	public void setCodiceInternoFK(int codiceInternoFK) {
		this.codiceInternoFK = codiceInternoFK;
	}
	public Timestamp getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(Timestamp dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdStrumentoCalcoloStruttura() {
		return idStrumentoCalcoloStruttura;
	}
	public void setIdStrumentoCalcoloStruttura(int idStrumentoCalcoloStruttura) {
		this.idStrumentoCalcoloStruttura = idStrumentoCalcoloStruttura;
	}
	public int getIdAnagraficaNominativo() {
		return idAnagraficaNominativo;
	}
	public void setIdAnagraficaNominativo(int idAnagraficaNominativo) {
		this.idAnagraficaNominativo = idAnagraficaNominativo;
	}
	public int getIdLookupQualifica() {
		return idLookupQualifica;
	}
	public void setIdLookupQualifica(int idLookupQualifica) {
		this.idLookupQualifica = idLookupQualifica;
	}
	
	
	private int modifiedBy ;
	private int enteredBy ;
	
	
	public int getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public int getEnteredBy() {
		return enteredBy;
	}
	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}
	public void builRecord(ResultSet rs) throws SQLException
	{
		id = rs.getInt("id");
		idAnagraficaNominativo = rs.getInt("id_anagrafica_nominativo");
		idLookupQualifica = rs.getInt("id_lookup_qualifica");
		ResultSetMetaData rsmd = rs.getMetaData();
	    int columns = rsmd.getColumnCount();
	    for (int x = 1; x <= columns; x++) {
	        if ("somma".equals(rsmd.getColumnName(x))) {
	            somma_parziale = rs.getInt("somma");
	        }
	    }

	}
	public DpatStrumentoCalcoloNominativi ()
	{
		
	}
	
	
	public DpatStrumentoCalcoloNominativi (ResultSet rs) throws SQLException
	{
		this.builRecord(rs);
		
	}
//	public String toString()
//	{
//		
//		JSONObject obj = new JSONObject();
//		JSONArray array = new JSONArray(); 
//		JSONObject o = new JSONObject(st.getHashmapStabilimento());
//		array.put(o);
//		
//		String txt = "";
//		if (fattoriIncidentiSuCarico!= null )
//		{
//			txt = fattoriIncidentiSuCarico.replaceAll("'", "").replaceAll("\"", "").replaceAll("\n", "");
//			
//		}
//		String ret = "" ;
//		ret += "{_id_:_"+id+"_," ;
//		obj.put("_id_", id);
//		ret += "_idStrumentoCalcoloStruttura_:_"+idStrumentoCalcoloStruttura +"_," ;
//		obj.put("_idStrumentoCalcoloStruttura_", idStrumentoCalcoloStruttura);
//		ret += "_idAnagraficaNominativo_:_"+ idAnagraficaNominativo+"_," ;
//		obj.put("_idAnagraficaNominativo_", idAnagraficaNominativo);
//		ret += "_idLookupQualifica_:_"+idLookupQualifica +"_," ;
//		obj.put("_idLookupQualifica_", idLookupQualifica);
//		ret += "_caricoLavoroAnnuale_:_"+caricoLavoroAnnuale +"_," ;
//		obj.put("_caricoLavoroAnnuale_", caricoLavoroAnnuale);
//		ret += "_fattoriIncidentiSuCarico_:_"+txt+"_," ;
//		obj.put("_fattoriIncidentiSuCarico_", txt);
//		ret += "_percentualeDaSottrarre_:_"+percentualeDaSottrarre +"_," ;
//		obj.put("_percentualeDaSottrarre_", percentualeDaSottrarre);
//		ret += "_caricoEffettivoAnnuale_:_"+caricoEffettivoAnnuale +"_," ;
//		obj.put("_caricoEffettivoAnnuale_", caricoEffettivoAnnuale);
//		ret+="_anagrafica_:"+nominativo.toString()+"}";
//		obj.put("_anagrafica_", nominativo.toJson());
//		JSONArray array = new JSONArray(); 
//		array.put(obj);
//		return array.toString().replace(",}", "}") ;
//		
//	}
	
	public String toString()
	{
		String ret = "" ;
		ret += "{_id_:_"+id+"_," ;
		ret += "_idStrumentoCalcoloStruttura_:_"+idStrumentoCalcoloStruttura +"_," ;
		ret += "_idAnagraficaNominativo_:_"+ idAnagraficaNominativo+"_," ;
		ret += "_idLookupQualifica_:_"+idLookupQualifica +"_," ;
		ret+="_anagrafica_:"+nominativo.toString2()+"}";
		return ret ;
		
	}
	public void insert(Connection db)
	{
		PreparedStatement pst = null;
		
		try
		{
			
			int id = DatabaseUtils.getNextInt(db, "dpat_strumento_calcolo_nominativi_", "id", 1);
			int statoCalcolato =1 ;
			if (stato>=0)
				 statoCalcolato = stato%2 ;
			String sql = "INSERT INTO dpat_strumento_calcolo_nominativi(id,id_anagrafica_nominativo, id_lookup_qualifica,id_dpat_strumento_calcolo_strutture,entered_by,entered , stato , codice_interno_fk)VALUES (?,?, ?, ?,?,current_timestamp,?,?);" ;
			pst =db.prepareStatement(sql);
			pst.setInt(1, id);

			pst.setInt(2, idAnagraficaNominativo);
			pst.setInt(3, idLookupQualifica);
			pst.setInt(4, idStrumentoCalcoloStruttura);
			pst.setInt(5, enteredBy);
			pst.setInt(6,statoCalcolato);
			if (codiceInternoFK>0)
				pst.setInt(7, codiceInternoFK);
			else
				pst.setInt(7, id);
			
			pst.execute();
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void insertConfig(Connection db)
	{
		PreparedStatement pst = null;
		
		try
		{
			
			String verifica = " select * from dpat_strumento_calcolo_nominativi_temp where id_anagrafica_nominativo ="+idAnagraficaNominativo;
			pst =db.prepareStatement(verifica);
			ResultSet rs = pst.executeQuery();
			if (!rs.next())
			{
			String sql = "INSERT INTO dpat_strumento_calcolo_nominativi_temp (id_anagrafica_nominativo, id_lookup_qualifica,id_dpat_strumento_calcolo_strutture)VALUES (?, ?, ?);" ;
			pst =db.prepareStatement(sql);
			pst.setInt(1, idAnagraficaNominativo);
			pst.setInt(2, idLookupQualifica);
			pst.setInt(3, idStrumentoCalcoloStruttura);
			
			
			pst.execute();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	public void queryRecord(Connection db,int id)
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			
			String sql = "select id,id_anagrafica_nominativo,id_lookup_qualifica" +
					",id_dpat_strumento_calcolo_strutture" +
					" from  dpat_strumento_calcolo_nominativi where id = ? and trashed_date is null" ;
			pst =db.prepareStatement(sql);
			pst.setInt(1, id);
			rs =  pst.executeQuery();
			if (rs.next())
				this.builRecord(rs);
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	public void queryRecordConfig(Connection db,int id,SystemStatus system)
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			
			String sql = "select id,id_anagrafica_nominativo,id_lookup_qualifica" +
					",id_dpat_strumento_calcolo_strutture" +
					" from  dpat_strumento_calcolo_nominativi_temp where id = ? and trashed_date is null" ;
			pst =db.prepareStatement(sql);
			pst.setInt(1, id);
			rs =  pst.executeQuery();
			if (rs.next())
			{
				this.builRecord(rs);
				nominativo =  system.getUser(idAnagraficaNominativo);
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	public void delete(Connection db)
	{
		PreparedStatement pst = null;
		
		try
		{
			String sql = "update dpat_strumento_calcolo_nominativi set trashed_Date =current_date where id = ?" ;
			pst =db.prepareStatement(sql);
			pst.setInt(1, id);
			pst.execute();
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void deleteConfig(Connection db)
	{
		PreparedStatement pst = null;
		
		try
		{
			String sql = "update dpat_strumento_calcolo_nominativi_temp set trashed_Date =current_date where id = ?" ;
			pst =db.prepareStatement(sql);
			pst.setInt(1, id);
			pst.execute();
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	public void congelaRisorsa(Connection db) throws SQLException
	{
		String update = "update dpat_strumento_calcolo_nominativi set stato = 2 where id = ?" ;
		PreparedStatement pst = null ;
		pst=  db.prepareStatement(update);
		pst.setInt(1, this.getId());
		pst.execute();
		
	
	}
	
	
	public void update(Connection db)
	{
		PreparedStatement pst = null;
		
		try
		{
			
			
			String sql = "UPDATE dpat_strumento_calcolo_nominativi SET data_scadenza=?,modified=current_timestamp,modified_by = ? WHERE id =?" ;
			pst =db.prepareStatement(sql);
			pst.setTimestamp(1, dataScadenza);
			pst.setInt(2, modifiedBy);
			
			pst.setInt(3, id);
			pst.execute();
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	public DpatStrumentoCalcoloNominativi (Connection db,int id,SystemStatus system)
	{
		this.queryRecord(db, id);
		nominativo = system.getUser(idAnagraficaNominativo);
		
	}
	
	public void updateConfig(Connection db)
	{
		PreparedStatement pst = null;
		
		try
		{
			String sql = "UPDATE dpat_strumento_calcolo_nominativi_temp SET id_anagrafica_nominativo=?,id_lookup_qualifica=?, id_dpat_strumento_calcolo_strutture=? WHERE id =?" ;
			pst =db.prepareStatement(sql);
			pst.setInt(1, idAnagraficaNominativo);
			pst.setInt(2, idLookupQualifica);
			pst.setInt(3, idStrumentoCalcoloStruttura);
			
			pst.setInt(4, id);
			pst.execute();
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	public int getSomma_parziale() {
		return somma_parziale;
	}
	public void setSomma_parziale(int somma_parziale) {
		this.somma_parziale = somma_parziale;
	}
	
	
}
