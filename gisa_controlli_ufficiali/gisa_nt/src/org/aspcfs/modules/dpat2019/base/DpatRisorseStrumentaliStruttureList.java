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
import java.util.Vector;

import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.modules.dpat2019.base.oia.OiaNodo;

public class DpatRisorseStrumentaliStruttureList extends Vector implements SyncableList {
	

	
	
	private int idAreaSel;
	
	
	public int getIdAreaSel() {
		return idAreaSel;
	}

	public void setIdAreaSel(int idAreaSel) {
		this.idAreaSel = idAreaSel;
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
	
	public void buildList(Connection db,int idStrumentoCalcolo,int idAsl,int anno)
	{
		PreparedStatement pst = null;
		ResultSet rs = null ;
		try 
		{
			
			String filtro = "" ;
		
				
			String sql = "select * from (select distinct on (a.codice_interno_fk) a.codice_interno_fk as cifk  ,a.* from ( select dpat_risorse_strumentali_strutture.*,nodi.data_scadenza ,nodi.descrizione as descrizione_struttura ,nodi.codice_interno_fk,nodi.pathdes as descrizione_lunga," +
					"nodi.flag_sian_veterinari " +
					"from dpat_strutture_asl nodi left join dpat_risorse_strumentali_strutture on nodi.codice_interno_fk=dpat_risorse_strumentali_strutture.id_struttura and  id_risorse_strumentali = ? " +
					"where  ( (nodi.stato = 1 and disabilitato= false) or nodi.stato=2) and nodi.id_asl=? and nodi.anno=? ";
			
			if (idAreaSel>0)
			 {
				 sql+=" and ( (nodi.id=? or nodi.id_padre=?) and nodi.tipologia_struttura!=13 and nodi.tipologia_struttura!=14) ";
			 }
			
			sql+= "order by " +
					"nodi.flag_sian_veterinari," +
					"id_struttura ) a ";
			
			
			sql+="ORDER BY a.codice_interno_fk, a.data_scadenza) aa order by aa.flag_sian_veterinari||';'||aa.id_struttura";
			 pst=db.prepareStatement(sql);
			pst.setInt(1, idStrumentoCalcolo);
			pst.setInt(2, idAsl);

			pst.setInt(3, anno);
			if (idAreaSel>0)
			 {
				 pst.setInt(4, idAreaSel);
				 pst.setInt(5, idAreaSel);
			 }

		 
			rs = pst.executeQuery();
			while (rs.next()) 
			{
				DpatRisorseStrumentaliStruttura struttura = new DpatRisorseStrumentaliStruttura(rs);
				this.add(struttura);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		} 
		
		
		
	}
	
	

	
	
}
