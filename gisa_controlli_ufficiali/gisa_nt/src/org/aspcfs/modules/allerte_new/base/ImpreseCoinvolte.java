/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.allerte_new.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

public class ImpreseCoinvolte {
	
	private int idAslcoinvolta = -1;
	private ArrayList<String> impreseCoinvolte = new ArrayList<String>();
	private ArrayList<String> indirizziImpreseCoinvolte = new ArrayList<String>();
	private int idAsl = -1;
	
	public int getIdAsl() {
		return idAsl;
	}
	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}
	public ArrayList<String> getIndirizziImpreseCoinvolte() {
		return indirizziImpreseCoinvolte;
	}
	public void setIndirizziImpreseCoinvolte(
			ArrayList<String> indirizziImpreseCoinvolte) {
		this.indirizziImpreseCoinvolte = indirizziImpreseCoinvolte;
	}
	public int getIdAslcoinvolta() {
		return idAslcoinvolta;
	}
	public void setIdAslcoinvolta(int idAslcoinvolta) {
		this.idAslcoinvolta = idAslcoinvolta;
	}
	public ArrayList<String> getImpreseCoinvolte() {
		return impreseCoinvolte;
	}
	public void setImpreseCoinvolte(
			ArrayList<String> impreseCoinvolte) {
		this.impreseCoinvolte = impreseCoinvolte;
	}
	
	public void store (Connection db) throws SQLException{
		String sql = "insert into allerte_imprese_coinvolte (idaslcoinvolte ,impresa,indirizzo,idasl) values (?,?,?,?)";
		PreparedStatement pst = db.prepareStatement(sql);
		
		int i =0;
		for(String ragioneSociale : impreseCoinvolte)
		{
			pst.setInt(1, idAslcoinvolta);
			pst.setString(2, ragioneSociale);
			pst.setString(3, indirizziImpreseCoinvolte.get(i));
			pst.setInt(4, idAsl);
			
			pst.execute();
			i++;
			
		}
		
		
	}
	
	public static ImpreseCoinvolte load(Connection db,int idaslcoinvolte) throws SQLException
	{
	
		ImpreseCoinvolte temp = new ImpreseCoinvolte();
		temp.setIdAslcoinvolta(idaslcoinvolte);
		String sql = " select idaslcoinvolte ,impresa,indirizzo,idasl from allerte_imprese_coinvolte where idaslcoinvolte ="+idaslcoinvolte;
		ArrayList<String> impreseCoinvolte = new ArrayList<String>();
		ArrayList<String> indirizziImpreseCoinvolte = new ArrayList<String>();
		
		
		PreparedStatement pst = db.prepareStatement(sql);
		ResultSet rs= pst.executeQuery();
		
		
		while (rs.next())
		{
			String ragioneSociale = rs.getString("impresa");
			String indirizzo = rs.getString("indirizzo");
			impreseCoinvolte.add(ragioneSociale);
			indirizziImpreseCoinvolte.add(indirizzo);
			temp.setIdAsl(rs.getInt("idasl"));
		}
		temp.setImpreseCoinvolte(impreseCoinvolte);
		
		temp.setIndirizziImpreseCoinvolte(indirizziImpreseCoinvolte);
		return temp;
		
	}
		
	
	
	public static Hashtable<String, ImpreseCoinvolte> getImpreseCoinvoteAllAsl(Connection db,int id_allerta) throws SQLException
	{
		Hashtable<String, ImpreseCoinvolte>	ret		= new Hashtable<String, ImpreseCoinvolte>();
		
		String sql ="SELECT ic.idaslcoinvolte ,lsi.description FROM allerte_imprese_coinvolte ic, allerte_asl_coinvolte ac, lookup_site_id lsi "
					+"WHERE ic.idaslcoinvolte=ac.id and ac.id_asl = lsi.code AND id_allerta = ? and ac.enabled"; 
		
		PreparedStatement pst= db.prepareStatement(sql);
		pst.setInt( 1, id_allerta );
		ResultSet rs=pst.executeQuery();
		while(rs.next())
		{
			ret.put(rs.getString("description"), ImpreseCoinvolte.load(db, rs.getInt("idaslcoinvolte")));
			
		}
		
		return ret;
	}
	
	public static Hashtable<String, ImpreseCoinvolte> getImpreseCoinvoteAllAslByIdLdd(Connection db, int id_ldd, int id_allerta) throws SQLException
	{
		Hashtable<String, ImpreseCoinvolte>	ret		= new Hashtable<String, ImpreseCoinvolte>();
		
		String sql ="SELECT ic.idaslcoinvolte ,lsi.description FROM allerte_imprese_coinvolte ic, allerte_asl_coinvolte ac, lookup_site_id lsi "
					+"WHERE ic.idaslcoinvolte=ac.id and ac.id_asl = lsi.code AND id_allerta = ? and id_ldd = ? and ac.enabled"; 
		
		PreparedStatement pst= db.prepareStatement(sql);
		pst.setInt( 1, id_allerta );
		pst.setInt( 2, id_ldd );
		ResultSet rs=pst.executeQuery();
		while(rs.next())
		{
			ret.put(rs.getString("description"), ImpreseCoinvolte.load(db, rs.getInt("idaslcoinvolte")));
			
		}
		
		return ret;
	}
	
	public void delete(Connection db) throws SQLException{
		String sql = "delete from allerte_imprese_coinvolte where idaslcoinvolte = ?";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setInt(1, idAslcoinvolta);
		pst.execute();
		
	}
	
	public static ArrayList<String> convertiInLista(String [] array)
	{
		ArrayList<String> lista = new ArrayList<String>();
		if(array!=null)
		{
		for (int i =0; i < array.length; i++)
		{
			lista.add(array[i]);
		}
		}
		return lista;
	}
	
}
