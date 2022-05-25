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
import java.util.ArrayList;

import com.darkhorseventures.framework.beans.GenericBean;

public class DpatSezione extends GenericBean implements Cloneable {

	private static final long serialVersionUID = -9092380054888506648L;
	
	private int id;
	private String description;
	private Boolean enabled;
	private int anno;
	private ArrayList<DpatPiano> elencoPiani = new ArrayList<DpatPiano>();
	
	private int codiceInterno;
	
	
	//Parametri di configurazione/visualizzazione
	private String bgColor;
	
	
	
	
	public int getCodiceInterno() {
		return codiceInterno;
	}
	public void setCodiceInterno(int codiceInterno) {
		this.codiceInterno = codiceInterno;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public ArrayList<DpatPiano> getElencoPiani() {
		return elencoPiani;
	}
	public void setElencoPiani(ArrayList<DpatPiano> elencoPiani) {
		this.elencoPiani = elencoPiani;
	}
	
	
	
	public String getBgColor() {
		return bgColor;
	}
	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}
	public void buildlistPiani(Connection db,int id,int statoConfigurazione,int anno)
	{
		try
		{
			String sql = "" ;
			
			if (statoConfigurazione==2)
				 sql = "select * from (select DISTINCT ON (codice_interno) codice_interno AS codice_interno_,b.* from (select id,data_scadenza, description, id_sezione, enabled,alias,tipo_attivita,codice_interno,ordinamento from dpat_piano where stato in (0,2)   and  id_sezione="+id+"  and anno="+anno+" )b order by codice_interno, data_scadenza)a order by ordinamento";
			else
				 sql = "select * from (select DISTINCT ON (codice_interno) codice_interno AS codice_interno_,b.* from (select id,data_scadenza, description, id_sezione, enabled,alias,tipo_attivita,codice_interno,ordinamento from dpat_piano where stato in (1) and disabilitato=false   and  id_sezione="+id+"  and anno="+anno+" )b order by codice_interno, data_scadenza)a order by ordinamento";
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next())
			{
				DpatPiano p = new DpatPiano();
				p.setId(rs.getInt("id"));
				p.setDescription(rs.getString("description"));
				p.setId_sezione(rs.getInt("id_sezione"));
				p.setEnabled(rs.getBoolean("enabled"));
				p.setAlias(rs.getString("alias"));
				p.setTipoAttivita(rs.getString("tipo_attivita"));
				p.setCodiceInterno(rs.getInt("codice_interno"));
				
				this.elencoPiani.add(p);
			}
			rs.close();
			pst.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	public void buildlistPianiConfiguratore(Connection db,int id,int anno)
	{
		try
		{
			String sql ="select * from (select DISTINCT ON (codice_interno) codice_interno AS codice_interno_,b.* from (select id_,id,data_scadenza, description, id_sezione, enabled,alias,tipo_attivita,codice_interno,ordinamento from dpat_piano where  id_sezione="+id+" and disabilitato=false and anno="+anno+" )b order by codice_interno, data_scadenza)a order by ordinamento";			
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next())
			{
				DpatPiano p = new DpatPiano();
				p.setId(rs.getInt("id"));
				p.setDescription(rs.getString("description"));
				p.setId_sezione(rs.getInt("id_sezione"));
				p.setEnabled(rs.getBoolean("enabled"));
				p.setId_(rs.getInt("id_"));
				p.setAlias(rs.getString("alias"));
				p.setTipoAttivita(rs.getString("tipo_attivita"));
				p.setCodiceInterno(rs.getInt("codice_interno"));
				
				this.elencoPiani.add(p);
			}
			rs.close();
			pst.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public Object clone(){
		try{
			return super.clone();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	
}
