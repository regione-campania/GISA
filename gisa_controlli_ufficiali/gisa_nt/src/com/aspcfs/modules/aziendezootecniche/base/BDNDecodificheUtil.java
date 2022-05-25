/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package com.aspcfs.modules.aziendezootecniche.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BDNDecodificheUtil {
	
	public static String getDescrizioneComuneByCodice(Connection db,String istat,String siglaProvincia) throws SQLException
	{
		String descrComune = "" ;
		String sql ="select c.* from comuni1 c join lookup_province p on p.code=c.cod_provincia::int where p.cod_provincia =? and c.cod_comune =?";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setString(1, siglaProvincia);
		pst.setString(2, istat);
		ResultSet rs = pst.executeQuery();
		if(rs.next())
		{
			descrComune=rs.getString("nome");
		}
		return descrComune;
		
	}
	
	public static String getDescrizioneComuneByCodiceECap(Connection db,String istat,String cap) throws SQLException
	{
		String descrComune = "" ;
		String sql ="select c.* from comuni1 c join lookup_province p on p.code=c.cod_provincia::int where c.cap =? and c.cod_comune =?";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setString(1, cap);
		pst.setString(2, istat);
		ResultSet rs = pst.executeQuery();
		if(rs.next())
		{
			descrComune=rs.getString("nome");
		}
		return descrComune;
		
	}
	
	public static int getAslByComuneCodiceAzienda(Connection db,String istat,String siglaProvincia) throws SQLException
	{
		int idAsl = -1 ;
		String sql ="select c.codiceistatasl::int from comuni1 c join lookup_province p on p.code=c.cod_provincia::int where p.cod_provincia =? and c.cod_comune =?";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setString(1, siglaProvincia);
		pst.setString(2, istat);
		ResultSet rs = pst.executeQuery();
		if(rs.next())
		{
			idAsl=rs.getInt("codiceistatasl");
		}
		return idAsl;
		
	}
	
	public static String getProvinciaByCodiceAzienda(String codiceazienda) 
	{
		String siglaProvincia = "" ;
		
		siglaProvincia = codiceazienda.substring(3, 5);
		return siglaProvincia;
		
	}
	
	public static String getSpecieAllevata (String specie_allevata,Connection db) throws SQLException
	{
		String sel = "select description from lookup_specie_allevata where short_description = ? ";
		String specieAllev = null;
		PreparedStatement pst = db.prepareStatement(sel );
		pst.setString(1, specie_allevata);
		ResultSet rs = pst.executeQuery(); 
		if (rs.next())
		{
			specieAllev = rs.getString(1);
		}
		



		return specieAllev;
	}
	
	public static String   getOrientamentoProduzione(Connection db,String descrSpecie,String codiceTipoProd,String codiceOrientamentoProd) throws SQLException
	{
		String sel = "select descrizione_tipo_produzione,descrizione_codice_orientamento from " +
				" orientamenti_produttivi where  tipo_produzione ilike ? and codice_orientamento ilike ? and specie_allevata ilike ? ";

		String orientamentoProduttivoDescrizione = "" ;
		PreparedStatement pst = db.prepareStatement(sel );
		pst.setString(1, codiceTipoProd);
		pst.setString(2, codiceOrientamentoProd);
		pst.setString(3, descrSpecie);
		ResultSet rs = pst.executeQuery(); 
		if (rs.next())
		{
			orientamentoProduttivoDescrizione = rs.getString("descrizione_codice_orientamento");
		}return orientamentoProduttivoDescrizione;
	
	}
	
	public  static String   getTipologiaProduzione(Connection db,String descrSpecie,String codiceTipoProd,String codiceOrientamentoProd) throws SQLException
	{
		String sel = "select descrizione_tipo_produzione,descrizione_codice_orientamento from " +
				" orientamenti_produttivi where  tipo_produzione ilike ? and codice_orientamento ilike ? and specie_allevata ilike ? ";

		String tipoProduzione = "" ;
		PreparedStatement pst = db.prepareStatement(sel );
		pst.setString(1, codiceTipoProd);
		pst.setString(2, codiceOrientamentoProd);
		pst.setString(3, descrSpecie);
		ResultSet rs = pst.executeQuery(); 
		if (rs.next())
		{
			tipoProduzione = rs.getString("descrizione_tipo_produzione");
		}
	return tipoProduzione;
	}
	
	public static void main(String[] args)
	{
		System.out.println(getProvinciaByCodiceAzienda("098na009"));
	}

}
