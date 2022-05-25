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

import org.aspcfs.modules.dpat2019.base.oia.OiaNodo;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class DpatRisorseStrumentali extends GenericBean {
	
	private int id ;
	private int anno ;
	private int idAsl ;
	private Timestamp entered;
	private Timestamp modified;
	private int enteredby ;
	private int modifiedby ;
	private String flagSianVet ;
	private boolean completo ;
	
	private int idArea;
	
	
	private OiaNodo strutturaAmbito;
	
	
	public int getIdArea() {
		return idArea;
	}
	public void setIdArea(int idArea) {
		this.idArea = idArea;
	}
	public boolean isCompleto() {
		return completo;
	}
	public void setCompleto(boolean completo) {
		this.completo = completo;
	}

	private DpatRisorseStrumentaliStruttureList listaStrutture = new DpatRisorseStrumentaliStruttureList() ;
	
	
	public DpatRisorseStrumentaliStruttureList getListaStrutture() {
		return listaStrutture;
	}
	public void setListaStrutture(DpatRisorseStrumentaliStruttureList listaStrutture) {
		this.listaStrutture = listaStrutture;
	}
	
	 
	public String getFlagSianVet() {
		return flagSianVet;
	}
	public void setFlagSianVet(String flagSianVet) {
		this.flagSianVet = flagSianVet;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	public int getIdAsl() {
		return idAsl;
	}
	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
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
	public int getEnteredby() {
		return enteredby;
	}
	public void setEnteredby(int enteredby) {
		this.enteredby = enteredby;
	}
	public int getModifiedby() {
		return modifiedby;
	}
	public void setModifiedby(int modifiedby) {
		this.modifiedby = modifiedby;
	}
	
	public void builRecord(ResultSet rs) throws SQLException
	{
		id = rs.getInt("id");
		idAsl = rs.getInt("id_asl");
		anno = rs.getInt("anno");
		entered = rs.getTimestamp("entered");
		modified = rs.getTimestamp("modified");
		enteredby = rs.getInt("enteredby");
		modifiedby = rs.getInt("modifiedby");
		completo = rs.getBoolean("completo");
	}
	
	public DpatRisorseStrumentali (Connection db,int idAsl,int anno,int area) throws SQLException
	{
		ResultSet rs = this.queryRecord(db,idAsl,anno);
		if (rs.next())
		{
			this.builRecord(rs);
			this.idArea=area;
			this.listaStrutture.setIdAreaSel(area);
			this.listaStrutture.buildList(db, this.id,this.idAsl,this.anno);
			if (area>0)
				strutturaAmbito = new OiaNodo(db, area);
		}
		
		
		
	} 
	
	
	
	public OiaNodo getStrutturaAmbito() {
		return strutturaAmbito;
	}
	public void setStrutturaAmbito(OiaNodo strutturaAmbito) {
		this.strutturaAmbito = strutturaAmbito;
	}
	public DpatRisorseStrumentali () throws SQLException
	{
		
	}  
		
	public DpatRisorseStrumentali (ResultSet rs) throws SQLException
	{
		this.builRecord(rs);
		
	}
	public ResultSet queryList(Connection db,int idAsl)
	{
		PreparedStatement pst =  null ;
		ResultSet rs = null ;
		try
		{
			String sql = "select * from dpat_risorse_strumentali where id_asl = ? ";
			pst = db.prepareStatement(sql);
			pst.setInt(1, idAsl);
			rs = pst.executeQuery() ;
			
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return rs ;
	}
	
	
	public ArrayList<DpatRisorseStrumentali> getList(Connection db) throws SQLException
	{
		ArrayList<DpatRisorseStrumentali> lista = new ArrayList<DpatRisorseStrumentali>();
		ResultSet rs = queryList(db, idAsl);
		while (rs.next()){
			DpatRisorseStrumentali dpatSC = new DpatRisorseStrumentali(rs);
			lista.add(dpatSC);
		}
		return lista ;
	}
	
	public ResultSet queryRecord(Connection db,int idAsl,int anno)
	{
		PreparedStatement pst =  null ;
		ResultSet rs = null ;
		try
		{
			String sql = "select * from dpat_risorse_strumentali where id_asl = ? and anno = ? ";
			pst = db.prepareStatement(sql);
			pst.setInt(1, idAsl);
		 	pst.setInt(2, anno);
			rs = pst.executeQuery() ;
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return rs ;
	}
	
	
	
	public void insertIstanzaDpatRS(Connection db,ActionContext context) throws SQLException
	{
		try
		{
		String insert = "INSERT INTO dpat_risorse_strumentali(anno, id_asl, entered, modified, enteredby, modifiedby, id) VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement pst = db.prepareStatement(insert);
	     id = DatabaseUtils.getNextSeq(db, context, "dpat_risorse_strumentali", "id");
	     int i = 0 ;
			pst.setInt(++i, anno);
			pst.setInt(++i, idAsl);
			pst.setTimestamp(++i, entered);
			pst.setTimestamp(++i, modified);
			pst.setInt(++i, enteredby);
			pst.setInt(++i, modifiedby);
			pst.setInt(++i, id);
			pst.execute();
		}
		catch(SQLException e)
		{
			db.rollback();
			e.printStackTrace();
			
		}
		
		
		
		
	}
	
	
	public void insertDpatRS(Connection db,ActionContext context,int idArea) throws SQLException
	{
		try
		{
			db.setAutoCommit(false);
		
	

		
		
		PreparedStatement pst = null ;
		
		String inserStrutture = "INSERT INTO dpat_risorse_strumentali_strutture(id_risorse_strumentali, id_struttura, num_auto ,id_attrezzature_campionamenti , num_computer_senza_adsl , num_computer_con_adsl ,num_notebook_non_connessi ,num_notebook_connessi , num_telefoni,num_termometri_tarati ,num_stampanti ,altro_descrizione,quantita_altro ) (select ?,id,0,-1,0,0,0,0,0,0,0,'',0 from dpat_strutture_asl where id_asl = ? and n_livello >1 and disabilitato=false and anno = ? and stato in (2) and  case when idArea >0 then (id=? or id_padre=?) else 1=1 end )";
		pst = db.prepareStatement(inserStrutture);
		pst.setInt(1, this.id);
		pst.setInt(2, this.idAsl);
		pst.setInt(3, this.anno);
		
		if (idArea>0)
		{
			pst.setInt(4, idArea);
			pst.setInt(5, idArea);

		}
		pst.execute();
		db.commit(); 
		  
		}
		catch(SQLException e)
		{
			db.rollback();
			e.printStackTrace();
			
		}
		finally 
		{
			db.setAutoCommit(true);
		}
		
		
		
	}
	
}
