/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.vigilanza.base;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.aspcfs.modules.base.SyncableList;

import com.darkhorseventures.framework.beans.GenericBean;

public class ComponenteNucleoIspettivoList extends ArrayList implements SyncableList {

	private int id_tipo_controllo_uff_imprese ;
	
	
		
	public int getId_tipo_controllo_uff_imprese() {
		return id_tipo_controllo_uff_imprese;
	}
	public void setId_tipo_controllo_uff_imprese(int id_tipo_controllo_uff_imprese) {
		this.id_tipo_controllo_uff_imprese = id_tipo_controllo_uff_imprese;
	}
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getUniqueField() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLastAnchor(Timestamp arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLastAnchor(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setNextAnchor(Timestamp arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setNextAnchor(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setSyncType(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setSyncType(String arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void creaFiltro (StringBuffer filtro)
	{
		if (id_tipo_controllo_uff_imprese >0)
		{
			filtro.append(" and id_tipo_controllo_uff_imprese = ? ");
		}
	}
	
	public void preparaFiltro (PreparedStatement pst) throws SQLException
	{
		int i =0 ;
		if (id_tipo_controllo_uff_imprese >0)
		{
			pst.setInt(++i, id_tipo_controllo_uff_imprese);
		}
	}
	public void buildList (Connection db) throws SQLException
	{
		/*StringBuffer sql =  new StringBuffer();
		StringBuffer sqlFiltro =  new StringBuffer();
		creaFiltro(sqlFiltro);
		
		sql.append("select * from nucleo_ispettivo_controlli_ufficiali where 1=1 ");
		PreparedStatement pst = db.prepareStatement(sql.toString()+sqlFiltro.toString());
		preparaFiltro(pst);
		
		ResultSet rs = pst.executeQuery();
		while (rs.next())
		{
			ComponenteNucleoIspettivo comp = new ComponenteNucleoIspettivo();
			comp.buildRecord(rs);
			this.add(comp);
			
		}*/
		
		
		 
	}
	




}
