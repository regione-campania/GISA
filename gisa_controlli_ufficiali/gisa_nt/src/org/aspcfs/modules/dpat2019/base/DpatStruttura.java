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

import org.aspcfs.modules.dpat2019.base.oia.OiaNodo;

public class DpatStruttura extends OiaNodo{

	private static final long serialVersionUID = 4863684932367322549L;
	
	private ArrayList<DpatStruttura> lista_nodiDpat = new ArrayList<DpatStruttura>();
	private ArrayList<DpatStrutturaIndicatore> elenco = new ArrayList<DpatStrutturaIndicatore>();
	
	public ArrayList<DpatStruttura> getLista_nodiDpat() {
		return lista_nodiDpat;
	}

	public void setLista_nodiDpat(ArrayList<DpatStruttura> lista_nodi) {
		this.lista_nodiDpat = lista_nodi;
	}
	
	
	public ArrayList<DpatStrutturaIndicatore> getElenco() {
		return elenco;
	}

	public void setElenco(ArrayList<DpatStrutturaIndicatore> elenco) {
		this.elenco = elenco;
	}
	
	public void buildElenco(Connection db, int id, int idDpat,int idAreaSelezionata){
		try
		{			
			String sql = "select * from dpat_struttura_indicatore where enabled=true and id_struttura="+id+" and id_dpat="+idDpat+" ";
			
			if (idAreaSelezionata>0)
				sql+= " and id_struttura=? ";
					sql+="order by descr_sezione,descr_piano,descr_attivita,descr_indicatore";
			PreparedStatement pst = db.prepareStatement(sql);
			if (idAreaSelezionata>0)
				pst.setInt(1, idAreaSelezionata);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next())
			{
				DpatStrutturaIndicatore dsi = new DpatStrutturaIndicatore();
				dsi.builRecord(rs.getInt("id"), db);
				this.elenco.add(dsi);
			}
			rs.close();
			pst.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void builRecord(int id,Connection db){ 
		try 
		{
			String sql = "select * from oia_nodo where id="+id ;
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery() ;
			while (rs.next()){	
				this.setId(id);
				this.setId_padre(rs.getInt("id_padre"));
				this.setIdAsl(rs.getInt("id_asl"));
				this.setDescrizione_lunga(rs.getString("descrizione_lunga"));
				this.setN_livello(rs.getInt("n_livello"));
				this.setEntered(rs.getTimestamp("entered"));
				this.setModified(rs.getTimestamp("modified"));
				this.setEntered_by(rs.getInt("entered_by"));
				this.setModified_by(rs.getInt("modified_by"));
				this.setTrashed_date(rs.getTimestamp("trashed_date"));
				this.setTipologia_struttura(rs.getInt("tipologia_struttura"));
			
				
			}
			rs.close();
			pst.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}

}
