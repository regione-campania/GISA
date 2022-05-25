/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RicercheAnagraficheTab {
	
	public static void inserOpu(Connection db,int idStanilimento)
	{
		String sql = "select opu_insert_into_ricerche_anagrafiche_old_materializzata(?)";
		try
		{
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, idStanilimento);
			pst.execute();
		}
		catch(SQLException e)
		{
			
		}
	}
	
	
	public static void insertAllevamentoNuovoModello(Connection db,int altId)
	{
		String sql = "select allevamenti_insert_into_ricerche_anagrafiche_old_materializzata_all(?)";
		try
		{
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, altId);
			pst.execute();
		}
		catch(SQLException e)
		{
			
		}
	}


	
	public static void inserOrganization(Connection db,int orgId)
	{
		String sql = "select org_insert_into_ricerche_anagrafiche_old_materializzata(?)";
		try
		{
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, orgId);
			pst.execute();
		}
		catch(SQLException e)
		{
			
		}
	}
	
	public static void insertAtriStabilimentiOrganization(Connection db,int orgId)
	{
		String sql = "delete from ricerche_anagrafiche_old_materializzata where riferimento_id = ? and riferimento_id_nome = ?";
		String sql2 = "insert into ricerche_anagrafiche_old_materializzata select * from ricerca_anagrafiche where riferimento_id = ? and riferimento_id_nome = ?;";
		try
		{
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, orgId);
			pst.setString(2, "orgId");
			pst.execute();
			
			pst = db.prepareStatement(sql2);
			pst.setInt(1, orgId);
			pst.setString(2, "orgId");
			pst.execute();
		}
		catch(SQLException e)
		{
			
		}
	}
	
	public static void inserRichiesta(Connection db,int altId)
	{
		String sql = "select ric_insert_into_ricerche_anagrafiche_old_materializzata(?)";
		try
		{
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, altId);
			pst.execute();
		}
		catch(SQLException e)
		{
			
		}
	}
	
	
	  

	  
	
	public static void inserApi(Connection db,int idStanilimento)
	{
		String sql = "select api_insert_into_ricerche_anagrafiche_old_materializzata(?)";
		try
		{
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, idStanilimento);
			pst.execute();
		}
		catch(Exception e)
		{
			System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] ECCEZIONE IN INSERT API " + ""); 
			e.printStackTrace();
		}
	}
	
	public void deleteOrganization(Connection db,int orgId)
	{
		String sql = "select org_delete_from_ricerche_anagrafiche_old_materializzata(?)";
		try
		{
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, orgId);
			pst.execute();
		}
		catch(SQLException e)
		{
			
		}
	}

	public static void insertSintesis(Connection db,int idStabilimento)
	{
		String sql = "select sintesis_insert_into_ricerche_anagrafiche_old_materializzata(?)";
		try
		{
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, idStabilimento);
			System.out.println("ESEGUO REFRESH VISTA SINTESIS CHIAMANDO FUNZIONE "+pst.toString());
			pst.execute();
		}
		catch(SQLException e)
		{
			
		}
	}
	
	public static void inserAnagrafica(Connection db,int idStabilimento)
	{
		String sql = "select anagrafica.anagrafica_insert_into_ricerche_anagrafiche_old_materializzata((select alt_id from anagrafica.stabilimenti where id = ? limit 1))";
		try
		{
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, idStabilimento);
			pst.execute();
		}
		catch(SQLException e)
		{
			
		}
	}
}
