/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.stabilimenti.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PermessoVisibilitaStabilimenti {

	private int idRuolo ;
	private String descrizioneRuolo ;
	private ArrayList<Integer> lista_stati = new ArrayList<Integer>();
	public int getIdRuolo() {
		return idRuolo;
	}
	public void setIdRuolo(int idRuolo) {
		this.idRuolo = idRuolo;
	}
	public String getDescrizioneRuolo() {
		return descrizioneRuolo;
	}
	public void setDescrizioneRuolo(String descrizioneRuolo) {
		this.descrizioneRuolo = descrizioneRuolo;
	}
	public ArrayList<Integer> getLista_stati() {
		return lista_stati;
	}
	public void setLista_stati(ArrayList<Integer> lista_stati) {
		this.lista_stati = lista_stati;
	}
	
	public ArrayList<PermessoVisibilitaStabilimenti> getListaRuoliPermessi(Connection db,String contesto) throws SQLException
	{
		ArrayList<PermessoVisibilitaStabilimenti> lista = new ArrayList<PermessoVisibilitaStabilimenti> () ;
		
		try
		{
			String sql = "select role_id,role from role"+contesto+" where enabled = true " ;
			ResultSet rs =  db.prepareStatement(sql).executeQuery();
			while (rs.next())
			{
				PermessoVisibilitaStabilimenti tmp = new PermessoVisibilitaStabilimenti();
				tmp.setIdRuolo(rs.getInt(1));
				tmp.setDescrizioneRuolo(rs.getString(2));
				
				ArrayList<Integer> lista_stati = new ArrayList<Integer>() ;
				String sel_permessi = "select id_ruolo,id_stato from permessi_visibilita_ruoli_stabilimenti where id_ruolo ="+rs.getInt(1) ;
				ResultSet rs_permessi = db.prepareStatement(sel_permessi).executeQuery();
				while (rs_permessi.next())
				{
					lista_stati.add(rs_permessi.getInt(2));
				}
				tmp.setLista_stati(lista_stati);
				lista.add(tmp);
			}
		}
		catch(SQLException e)
		{
			throw e ;
		}
		
		
		return lista ;
		
	}
	
	public PermessoVisibilitaStabilimenti getPermessiRuolo(Connection db,int idRuolo,String contesto) throws SQLException
	{
		PermessoVisibilitaStabilimenti permesso = new PermessoVisibilitaStabilimenti () ;
		
		try
		{
			String sql = "select role_id,role from role"+contesto+" where enabled = true and role_id ="+idRuolo ;
			ResultSet rs =  db.prepareStatement(sql).executeQuery();
			if (rs.next())
			{
				
				permesso.setIdRuolo(rs.getInt(1));
				permesso.setDescrizioneRuolo(rs.getString(2));
				
				ArrayList<Integer> lista_stati = new ArrayList<Integer>() ;
				String sel_permessi = "select id_ruolo,id_stato from permessi_visibilita_ruoli_stabilimenti join lookup_stati_stabilimenti on (id_stato = code) where enabled = true and id_ruolo ="+rs.getInt(1) ;
				ResultSet rs_permessi = db.prepareStatement(sel_permessi).executeQuery();
				while (rs_permessi.next())
				{
					lista_stati.add(rs_permessi.getInt(2));
				}
				lista_stati.add(0);
				permesso.setLista_stati(lista_stati);
			
			}
		}
		catch(SQLException e)
		{
			throw e ;
		}
		
		
		return permesso ;
		
	}
	
	
	
	
	public void savePermessiVisibilita(Connection db , int idRuolo , int idStato ) throws SQLException
	{
		try
		{
			String insert = " insert into permessi_visibilita_ruoli_stabilimenti ( id_ruolo,id_stato ) values (?,? )";
			PreparedStatement pst = db.prepareStatement(insert)	;
			pst.setInt(1, idRuolo);
			pst.setInt(2, idStato);
			pst.execute();
			
		}
		catch(SQLException e)
		{
			throw e ;
		}
		
		
	}
	
	public void deletePermessiVisibilita(Connection db , int idRuolo  ) throws SQLException
	{
		try
		{
			String insert = "delete from permessi_visibilita_ruoli_stabilimenti where id_ruolo = ?";
			PreparedStatement pst = db.prepareStatement(insert)	;
			pst.setInt(1, idRuolo);
			pst.execute();
			
		}
		catch(SQLException e)
		{
			throw e ;
		}
		
		
	}
	
	
	
}
