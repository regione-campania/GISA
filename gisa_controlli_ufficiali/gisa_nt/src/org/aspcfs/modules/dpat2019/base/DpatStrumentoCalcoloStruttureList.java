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
import java.util.Vector;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.modules.dpat2019.base.oia.OiaNodo;

public class DpatStrumentoCalcoloStruttureList extends Vector implements SyncableList {

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
	
	
	private int idAreaSelezionata ;
	
	
	
	
	public int getIdAreaSelezionata() {
		return idAreaSelezionata;
	}

	public void setIdAreaSelezionata(int idAreaSelezionata) {
		this.idAreaSelezionata = idAreaSelezionata;
	}

	public void buildList(Connection db,int idStrumentoCalcolo,SystemStatus system)
	{
		PreparedStatement pst = null;
		ResultSet rs = null ;

		
		
//		ArrayList<DpatStrumentoCalcoloStruttura> elencoStruttureComplesse = new ArrayList<DpatStrumentoCalcoloStruttura>();
		
		
		String sql = "select * from ("
				+ "select distinct on (a.codice_interno_fk) a.codice_interno_fk as cifk  ,a.* from ( ";
		 sql += "select oia_nodo.* ,o.tipologia,o.org_id,regexp_replace(oia_nodo.descrizione_struttura, E'[\\n\\r]+', ' ', 'g' ) as descrizione_struttura,oia_nodo.id_asl,oia_nodo.pathid||';' " +
				",tipooia.description as descrizione_tipologia_struttura,descrizione_area_struttura as descrizione_area_struttura_complessa "
				+ "from dpat_strutture_asl oia_nodo "
				+ "join organization o on o.site_id = oia_nodo.id_asl and o.tipologia = 6 " +
				"LEFT JOIN lookup_tipologia_nodo_oia tipooia ON oia_nodo.tipologia_struttura = tipooia.code " +
				" where  oia_nodo.id_strumento_calcolo = "+idStrumentoCalcolo+" and disabilitato = false order by oia_nodo.pathid||';'||oia_nodo.id  ";
		 sql +=")a ORDER BY a.codice_interno_fk, a.data_scadenza ) aa "
		 		+ " where  1=1";
		 
		 if (idAreaSelezionata>0)
		 {
			 sql+=" and ( id=? or id_padre=?) ";
		 }
		 
		 		sql+= " order by livello ,tipologia_struttura desc , aa.pathid||';'||aa.codice_interno_fk   " ;
				 
				 
				 try {
			pst = db.prepareStatement(sql);
			
			if (idAreaSelezionata>0)
			 {
				pst.setInt(1, idAreaSelezionata);
				pst.setInt(2, idAreaSelezionata);
			 }
			
			rs = pst.executeQuery();

			while(rs.next()){
				OiaNodo struttura = new OiaNodo();
				struttura.loadResultSet(rs);
			
				
				struttura.getListaNominativi().buildList(db,struttura.getCodiceInternoFK(),system);
				this.add(struttura);
			}
		
			pst.close();
			rs.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public void buildListSenzaNominativi(Connection db,int idStrumentoCalcolo,SystemStatus system)
	{
		PreparedStatement pst = null;
		ResultSet rs = null ;

		
		
//		ArrayList<DpatStrumentoCalcoloStruttura> elencoStruttureComplesse = new ArrayList<DpatStrumentoCalcoloStruttura>();
		
		
		String sql = "select * from (select distinct on (a.codice_interno_fk) a.codice_interno_fk as cifk  ,a.* from ( ";
		 sql += "select oia_nodo.* ,o.tipologia,o.org_id,regexp_replace(oia_nodo.descrizione_struttura, E'[\\n\\r]+', ' ', 'g' ) as descrizione_struttura,oia_nodo.id_asl,oia_nodo.pathid||';' " +
				",tipooia.description as descrizione_tipologia_struttura from dpat_strutture_asl oia_nodo join organization o on o.site_id = oia_nodo.id_asl and o.tipologia = 6 " +
				"LEFT JOIN lookup_tipologia_nodo_oia tipooia ON oia_nodo.tipologia_struttura = tipooia.code " +
				" where  oia_nodo.id_strumento_calcolo = "+idStrumentoCalcolo+" and disabilitato = false order by oia_nodo.pathid||';'||oia_nodo.id  ";
		 sql +=")a ORDER BY a.codice_interno_fk, a.data_scadenza ) aa order by aa.pathid||';'||aa.codice_interno_fk  " ;
				 
				 
				 try {
			pst = db.prepareStatement(sql);
			rs = pst.executeQuery();

			while(rs.next()){
				OiaNodo struttura = new OiaNodo();
				struttura.loadResultSet(rs);
			
				this.add(struttura);
			}
		
			pst.close();
			rs.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	

	
	
}
