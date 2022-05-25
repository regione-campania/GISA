/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.controlliufficiali.base;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.aspcfs.modules.base.Constants;

import com.darkhorseventures.framework.beans.GenericBean;


public class AziendeZootFields extends GenericBean {
	
	int num_tot_animali_presenti = 0;   
	int cap_max_animali = 0;
	int num_animali_isp = 0;
	int num_tot_capannoni = 0;
	int num_tot_capannoni_isp = 0;
	int num_capannoni_con_gabbie = 0;
	int num_capannoni_non_gabbie = 0;
	int num_tot_vitelli_inf_8_settimane = 0;
	int num_tot_box = 0;
	int num_tot_box_isp = 0;
	int num_verri = 0;
	int cap_max_verri = 0;
	int num_scrofe_scrofette = 0;
	int cap_max_scrofe_scrofette = 0;
	int num_lattonzoli = 0;
	int cap_max_lattonzoli = 0;
	int num_suinetti = 0;
	int cap_max_suinetti = 0;
	int num_suini_al_grasso = 0;
	int cap_max_suini_al_grasso = 0;
	int num_verri_isp = 0;
	int num_scrofe_scrofette_isp = 0;
	int num_lattonzoli_isp = 0;
	int num_suinetti_isp = 0;
	int num_suini_al_grasso_isp = 0;
	int id_allevamento = 0;
	int id_controllo = 0;   
	int id;
	
	String metodo_allevamento = "";
	
	public AziendeZootFields() {}

	public int getNum_tot_animali_presenti() {
		return num_tot_animali_presenti;
	}

	public void setNum_tot_animali_presenti(int num_tot_animali_presenti) {
		this.num_tot_animali_presenti = num_tot_animali_presenti;
	}

	public int getCap_max_animali() {
		return cap_max_animali;
	}

	public void setCap_max_animali(int cap_max_animali) {
		this.cap_max_animali = cap_max_animali;
	}

	public int getNum_animali_isp() {
		return num_animali_isp;
	}

	public void setNum_animali_isp(int num_animali_isp) {
		this.num_animali_isp = num_animali_isp;
	}

	public int getNum_tot_capannoni() {
		return num_tot_capannoni;
	}

	public void setNum_tot_capannoni(int num_tot_capannoni) {
		this.num_tot_capannoni = num_tot_capannoni;
	}

	public int getNum_tot_capannoni_isp() {
		return num_tot_capannoni_isp;
	}

	public void setNum_tot_capannoni_isp(int num_tot_capannoni_isp) {
		this.num_tot_capannoni_isp = num_tot_capannoni_isp;
	}

	public int getNum_capannoni_con_gabbie() {
		return num_capannoni_con_gabbie;
	}

	public void setNum_capannoni_con_gabbie(int num_capannoni_con_gabbie) {
		this.num_capannoni_con_gabbie = num_capannoni_con_gabbie;
	}

	public int getNum_capannoni_non_gabbie() {
		return num_capannoni_non_gabbie;
	}

	public void setNum_capannoni_non_gabbie(int num_capannoni_non_gabbie) {
		this.num_capannoni_non_gabbie = num_capannoni_non_gabbie;
	}

	public int getNum_tot_vitelli_inf_8_settimane() {
		return num_tot_vitelli_inf_8_settimane;
	}

	public void setNum_tot_vitelli_inf_8_settimane(
			int num_tot_vitelli_inf_8_settimane) {
		this.num_tot_vitelli_inf_8_settimane = num_tot_vitelli_inf_8_settimane;
	}

	public int getNum_tot_box() {
		return num_tot_box;
	}

	public void setNum_tot_box(int num_tot_box) {
		this.num_tot_box = num_tot_box;
	}

	public int getNum_tot_box_isp() {
		return num_tot_box_isp;
	}

	public void setNum_tot_box_isp(int num_tot_box_isp) {
		this.num_tot_box_isp = num_tot_box_isp;
	}

	public int getNum_verri() {
		return num_verri;
	}

	public void setNum_verri(int num_verri) {
		this.num_verri = num_verri;
	}

	public int getCap_max_verri() {
		return cap_max_verri;
	}

	public void setCap_max_verri(int cap_max_verri) {
		this.cap_max_verri = cap_max_verri;
	}

	public int getNum_scrofe_scrofette() {
		return num_scrofe_scrofette;
	}

	public void setNum_scrofe_scrofette(int num_scrofe_scrofette) {
		this.num_scrofe_scrofette = num_scrofe_scrofette;
	}

	public int getCap_max_scrofe_scrofette() {
		return cap_max_scrofe_scrofette;
	}

	public void setCap_max_scrofe_scrofette(int cap_max_scrofe_scrofette) {
		this.cap_max_scrofe_scrofette = cap_max_scrofe_scrofette;
	}

	public int getNum_lattonzoli() {
		return num_lattonzoli;
	}

	public void setNum_lattonzoli(int num_lattonzoli) {
		this.num_lattonzoli = num_lattonzoli;
	}

	public int getCap_max_lattonzoli() {
		return cap_max_lattonzoli;
	}

	public void setCap_max_lattonzoli(int cap_max_lattonzoli) {
		this.cap_max_lattonzoli = cap_max_lattonzoli;
	}

	public int getNum_suinetti() {
		return num_suinetti;
	}

	public void setNum_suinetti(int num_suinetti) {
		this.num_suinetti = num_suinetti;
	}

	public int getCap_max_suinetti() {
		return cap_max_suinetti;
	}

	public void setCap_max_suinetti(int cap_max_suinetti) {
		this.cap_max_suinetti = cap_max_suinetti;
	}

	public int getNum_suini_al_grasso() {
		return num_suini_al_grasso;
	}

	public void setNum_suini_al_grasso(int num_suini_al_grasso) {
		this.num_suini_al_grasso = num_suini_al_grasso;
	}

	public int getCap_max_suini_al_grasso() {
		return cap_max_suini_al_grasso;
	}

	public void setCap_max_suini_al_grasso(int cap_max_suini_al_grasso) {
		this.cap_max_suini_al_grasso = cap_max_suini_al_grasso;
	}

	public int getNum_verri_isp() {
		return num_verri_isp;
	}

	public void setNum_verri_isp(int num_verri_isp) {
		this.num_verri_isp = num_verri_isp;
	}

	public int getNum_scrofe_scrofette_isp() {
		return num_scrofe_scrofette_isp;
	}

	public void setNum_scrofe_scrofette_isp(int num_scrofe_scrofette_isp) {
		this.num_scrofe_scrofette_isp = num_scrofe_scrofette_isp;
	}

	public int getNum_lattonzoli_isp() {
		return num_lattonzoli_isp;
	}

	public void setNum_lattonzoli_isp(int num_lattonzoli_isp) {
		this.num_lattonzoli_isp = num_lattonzoli_isp;
	}

	public int getNum_suinetti_isp() {
		return num_suinetti_isp;
	}

	public void setNum_suinetti_isp(int num_suinetti_isp) {
		this.num_suinetti_isp = num_suinetti_isp;
	}

	public int getNum_suini_al_grasso_isp() {
		return num_suini_al_grasso_isp;
	}

	public void setNum_suini_al_grasso_isp(int num_suini_al_grasso_isp) {
		this.num_suini_al_grasso_isp = num_suini_al_grasso_isp;
	}

	public int getId_allevamento() {
		return id_allevamento;
	}

	public void setId_allevamento(int id_allevamento) {
		this.id_allevamento = id_allevamento;
	}

	public int getId_controllo() {
		return id_controllo;
	}

	public void setId_controllo(int id_controllo) {
		this.id_controllo = id_controllo;
	}

	public String getMetodo_allevamento() {
		return metodo_allevamento;
	}

	public void setMetodo_allevamento(String metodo_allevamento) {
		this.metodo_allevamento = metodo_allevamento;
	}

	public void insert(Connection db) throws SQLException {
		// TODO Auto-generated method stub
		boolean commit = db.getAutoCommit();

		try {
		StringBuffer sql = new StringBuffer();
		
		
			
			if (commit) {
				db.setAutoCommit(false);
			}
			
			sql.append(
					"INSERT INTO aziende_zootecniche_fields (id_allevamento, id_controllo " );
			 
			
			if (num_tot_animali_presenti >= 0)
			{
				sql.append(", num_tot_animali_presenti ");
			}  
			if (cap_max_animali >= 0)
			{
				sql.append(", cap_max_animali ");
			}
			if (num_animali_isp >= 0)
			{
				sql.append(", num_animali_isp ");
			}
			if (num_tot_capannoni >= 0)
			{
				sql.append(", num_tot_capannoni ");
			}
			if (num_tot_capannoni_isp >= 0)
			{
				sql.append(", num_tot_capannoni_isp ");
			}
			if (num_capannoni_con_gabbie >= 0)
			{
				sql.append(", num_capannoni_con_gabbie ");
			}
			if (num_capannoni_non_gabbie >= 0)
			{
				sql.append(", num_capannoni_non_gabbie ");
			}
			if (num_tot_vitelli_inf_8_settimane >= 0)
			{
				sql.append(", num_tot_vitelli_inf_8_settimane ");
			}
			if (num_tot_box >= 0)
			{
				sql.append(", num_tot_box ");
			}
			if (num_tot_box_isp >= 0)
			{
				sql.append(", num_tot_box_isp ");
			}
			if (num_verri >= 0)
			{
				sql.append(", num_verri ");
			}
			if (num_verri_isp >= 0)
			{
				sql.append(", num_verri_isp ");
			}
			if (cap_max_verri >= 0)
			{
				sql.append(", cap_max_verri ");
			}
			if (num_scrofe_scrofette >= 0)
			{
				sql.append(", num_scrofe_scrofette ");
			}
			if (cap_max_scrofe_scrofette >= 0)
			{
				sql.append(",cap_max_scrofe_scrofette ");
			}
			if (num_lattonzoli >= 0)
			{
				sql.append(", num_lattonzoli ");
			}
			if (cap_max_lattonzoli >= 0)
			{
				sql.append(", cap_max_lattonzoli ");
			}
			if (num_suinetti >= 0)
			{
				sql.append(", num_suinetti ");
			}
			if (cap_max_suinetti >= 0)
			{
				sql.append(", cap_max_suinetti ");
			}
			if (num_suini_al_grasso >= 0)
			{
				sql.append(", num_suini_al_grasso ");
			}
			if (cap_max_suini_al_grasso >= 0)
			{
				sql.append(", cap_max_suini_al_grasso ");
			}
			
			if (num_scrofe_scrofette_isp >= 0)
			{
				sql.append(", num_scrofe_scrofette_isp ");
			}
			if (num_lattonzoli_isp >= 0)
			{
				sql.append(", num_lattonzoli_isp ");
			}
			if (num_suinetti_isp >= 0)
			{
				sql.append(", num_suinetti_isp ");
			}
			if (num_suini_al_grasso_isp >= 0)
			{
				sql.append(", num_suini_al_grasso_isp ");
			}
			
			if (metodo_allevamento != null && !metodo_allevamento.equals(""))
			{
				sql.append(", metodo_allevamento ");
			}


			sql.append(")");
			sql.append("VALUES (?, ? ");
			
			if (num_tot_animali_presenti >= 0)
			{
				sql.append(", ? ");
			}  
			if (cap_max_animali >= 0)
			{
				sql.append(", ? ");
			}
			if (num_animali_isp >= 0)
			{
				sql.append(", ? ");
			}
			if (num_tot_capannoni >= 0)
			{
				sql.append(", ? ");
			}
			if (num_tot_capannoni_isp >= 0)
			{
				sql.append(", ? ");
			}
			if (num_capannoni_con_gabbie >= 0)
			{
				sql.append(", ? ");
			}
			if (num_capannoni_non_gabbie >= 0)
			{
				sql.append(", ? ");
			}
			if (num_tot_vitelli_inf_8_settimane >= 0)
			{
				sql.append(", ? ");
			}
			if (num_tot_box >= 0)
			{
				sql.append(", ? ");
			}
			if (num_tot_box_isp >= 0)
			{
				sql.append(", ?  ");
			}
			if (num_verri >= 0)
			{
				sql.append(", ? ");
			}
			if (num_verri_isp >= 0)
			{
				sql.append(", ? ");
			}
			if (cap_max_verri >= 0)
			{
				sql.append(", ?  ");
			}
			if (num_scrofe_scrofette >= 0)
			{
				sql.append(", ?  ");
			}
			if (cap_max_scrofe_scrofette >= 0)
			{
				sql.append(", ?  ");
			}
			if (num_lattonzoli >= 0)
			{
				sql.append(", ? ");
			}
			if (cap_max_lattonzoli >= 0)
			{
				sql.append(", ? ");
			}
			if (num_suinetti >= 0)
			{
				sql.append(", ? ");
			}
			if (cap_max_suinetti >= 0)
			{
				sql.append(", ? ");
			}
			if (num_suini_al_grasso >= 0)
			{
				sql.append(", ? ");
			}
			if (cap_max_suini_al_grasso >= 0)
			{
				sql.append(", ? ");
			}
			
			if (num_scrofe_scrofette_isp >= 0)
			{
				sql.append(", ? ");
			}
			if (num_lattonzoli_isp >= 0)
			{
				sql.append(", ? ");
			}
			if (num_suinetti_isp >= 0)
			{
				sql.append(", ? ");
			}
			if (num_suini_al_grasso_isp >= 0)
			{
				sql.append(", ? ");
			}
			if (metodo_allevamento != null && !metodo_allevamento.equals(""))
			{
				sql.append(", ? ");
			}
			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, this.id_allevamento);
			pst.setInt(++i, this.id_controllo);

			if (num_tot_animali_presenti >= 0)
			{
				pst.setInt(++i, this.num_tot_animali_presenti);
			}  
			if (cap_max_animali >= 0)
			{
				pst.setInt(++i, this.cap_max_animali);
			}
			if (num_animali_isp >= 0)
			{
				pst.setInt(++i, this.num_animali_isp);
			}
			if (num_tot_capannoni >= 0)
			{
				pst.setInt(++i, this.num_tot_capannoni);
			}
			if (num_tot_capannoni_isp >= 0)
			{
				pst.setInt(++i, this.num_tot_capannoni_isp);
			}
			if (num_capannoni_con_gabbie >= 0)
			{
				pst.setInt(++i, this.num_capannoni_con_gabbie);
			}
			if (num_capannoni_non_gabbie >= 0)
			{
				pst.setInt(++i, this.num_capannoni_non_gabbie);
			}
			if (num_tot_vitelli_inf_8_settimane >= 0)
			{
				pst.setInt(++i, this.num_tot_vitelli_inf_8_settimane);
			}
			if (num_tot_box >= 0)
			{
				pst.setInt(++i, this.num_tot_box);
			}
			if (num_tot_box_isp >= 0)
			{
				pst.setInt(++i, this.num_tot_box_isp);
			}
			if (num_verri >= 0)
			{
				pst.setInt(++i, this.num_verri);
			}
			if (num_verri_isp >= 0)
			{
				pst.setInt(++i, this.num_verri_isp);
			}
			if (cap_max_verri >= 0)
			{
				pst.setInt(++i, this.cap_max_verri);
			}
			if (num_scrofe_scrofette >= 0)
			{
				pst.setInt(++i, this.num_scrofe_scrofette);
			}
			if (cap_max_scrofe_scrofette >= 0)
			{
				pst.setInt(++i, this.cap_max_scrofe_scrofette);
			}
			if (num_lattonzoli >= 0)
			{
				pst.setInt(++i, this.num_lattonzoli);
			}
			if (cap_max_lattonzoli >= 0)
			{
				pst.setInt(++i, this.cap_max_lattonzoli);
			}
			if (num_suinetti >= 0)
			{
				pst.setInt(++i, this.num_suinetti);
			}
			if (cap_max_suinetti >= 0)
			{
				pst.setInt(++i, this.cap_max_suinetti);
			}
			if (num_suini_al_grasso >= 0)
			{
				pst.setInt(++i, this.num_suini_al_grasso);
			}
			if (cap_max_suini_al_grasso >= 0)
			{
				pst.setInt(++i, this.cap_max_suini_al_grasso);
			}
			
			if (num_scrofe_scrofette_isp >= 0)
			{
				pst.setInt(++i, this.num_scrofe_scrofette_isp);
			}
			if (num_lattonzoli_isp >= 0)
			{
				pst.setInt(++i, this.num_lattonzoli_isp);
			}
			if (num_suinetti_isp >= 0)
			{
				pst.setInt(++i, this.num_suinetti_isp);
			}
			if (num_suini_al_grasso_isp >= 0)
			{
				pst.setInt(++i, this.num_suini_al_grasso_isp);
			}
			if (metodo_allevamento != null  && !metodo_allevamento.equals(""))
			{
				pst.setString(++i, this.metodo_allevamento);			
			}
			
			//
			
			pst.execute();
			pst.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			if (commit) {
				db.rollback();
			}
			throw new SQLException(e.getMessage());
		} finally {
			if (commit) {
				db.setAutoCommit(true);
			}
		}
		
		
		
	}
	
	public void queryRecord(Connection db, int id) throws SQLException {
		if (id == -1) {
			this.id=-1 ;
		}
		else
		{
			
		PreparedStatement pst = db.prepareStatement(
				"select * from aziende_zootecniche_fields where id_controllo = ?");

		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			buildRecord(rs);
				
		}
			
		rs.close();
		pst.close();
		if (this.id == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}
		
	}
	
	}
	
	
	
	public void queryRecordbyOrgId(Connection db, int id) throws SQLException {
		if (id == -1) {
			this.id=-1 ;
		}
		else
		{
			
		PreparedStatement pst = db.prepareStatement(
				" select * from aziende_zootecniche_fields where id_controllo in (select max(id_controllo) from aziende_zootecniche_fields where id_allevamento = ?	" +
				" group by id_controllo ) order by id_controllo desc");

		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			//buildRecordSpecie(rs, specie);
			buildRecord(rs);	
		}
			
		rs.close();
		pst.close();
		if (this.id == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}
		
	}
	
	}
	
	public void queryRecordbyAltId(Connection db, int altId) throws SQLException {
		if (id == -1) {
			this.id=-1 ;
		}
		else
		{
			
		PreparedStatement pst = db.prepareStatement(
				" select * from aziende_zootecniche_fields where id_controllo in (select max(id_controllo) from aziende_zootecniche_fields where id_allevamento = ?	" +
				" group by id_controllo ) order by id_controllo desc");

		pst.setInt(1, altId);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			//buildRecordSpecie(rs, specie);
			buildRecord(rs);	
		}
			
		rs.close();
		pst.close();
		if (this.id == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}
		
	}
	
	}
	
	protected void buildRecord(ResultSet rs) throws SQLException {
	
		this.setId_controllo(rs.getInt("id_controllo"));
		num_tot_animali_presenti = rs.getInt("num_tot_animali_presenti");   
		cap_max_animali = rs.getInt("cap_max_animali");
		num_animali_isp = rs.getInt("num_animali_isp");
		num_tot_capannoni = rs.getInt("num_tot_capannoni");
		num_tot_capannoni_isp = rs.getInt("num_tot_capannoni_isp");
		num_capannoni_con_gabbie = rs.getInt("num_capannoni_con_gabbie");
		num_capannoni_non_gabbie = rs.getInt("num_capannoni_non_gabbie");
		num_tot_vitelli_inf_8_settimane = rs.getInt("num_tot_vitelli_inf_8_settimane");
		num_tot_box = rs.getInt("num_tot_box");
		num_tot_box_isp = rs.getInt("num_tot_box_isp");
		num_verri = rs.getInt("num_verri");
		cap_max_verri = rs.getInt("cap_max_verri");
		num_scrofe_scrofette = rs.getInt("num_scrofe_scrofette");
		cap_max_scrofe_scrofette = rs.getInt("cap_max_scrofe_scrofette");
		num_lattonzoli = rs.getInt("num_lattonzoli");
		cap_max_lattonzoli = rs.getInt("cap_max_lattonzoli");
		num_suinetti = rs.getInt("num_suinetti");
		cap_max_suinetti = rs.getInt("cap_max_suinetti");
		num_suini_al_grasso = rs.getInt("num_suini_al_grasso");
		cap_max_suini_al_grasso = rs.getInt("cap_max_suini_al_grasso");
		num_verri_isp = rs.getInt("num_verri_isp");
		num_scrofe_scrofette_isp = rs.getInt("num_scrofe_scrofette_isp");
		num_lattonzoli_isp = rs.getInt("num_lattonzoli_isp");
		num_suinetti_isp = rs.getInt("num_suinetti_isp");
		num_suini_al_grasso_isp = rs.getInt("num_suini_al_grasso_isp");
		id_allevamento = rs.getInt("id_allevamento");
		metodo_allevamento = rs.getString("metodo_allevamento");
		id = rs.getInt("id"); 
		
	}
	
	
	
	public void saveFieldsZoot(Connection db) throws SQLException  {
		
		
		PreparedStatement pst1 = db.prepareStatement("delete from aziende_zootecniche_fields where id_controllo = "+id_controllo);
		pst1.execute();
		
		this.insert(db);
	
	}
	
	
}
