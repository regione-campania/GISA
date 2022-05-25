/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.dpat2019.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Sezione extends SezioneInterface<PianoAttivita>
{
	public ArrayList<Integer> statiCong = new ArrayList<Integer>();
	public String statiCongString = null;
	public ArrayList<Integer> statiPreCong = new ArrayList<Integer>();
	public String statiPreCongString = null;
	 
	
	public  ArrayList<Sezione> buildList(Connection conn, ResultSet rs,boolean nonscaduti,boolean withChilds, String statoFigli, String statoSezione) throws Exception
	{
		ArrayList<Sezione> toRet = new ArrayList<Sezione>();
		
		while(rs.next())
		{
			toRet.add(build(conn,rs, nonscaduti,withChilds, statoFigli, statoSezione));
		}
		
		return toRet;
	}
	
	public  Sezione buildByOid(Connection conn, Integer oid, boolean nonscaduti,boolean withChilds, String stato, String statoFigli, String statoSezione) throws Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		Sezione toRet = null;
		String query =  "select * from dpat_sez_new where (? is null or stato::text = ANY (string_to_array(?, ',')) ) and id = ? "+(nonscaduti ? " and data_scadenza is null" : "");
		
		try
		{
			pst = conn.prepareStatement(query);
			pst.setString(1, stato);
			pst.setString(2, (stato==null)?("-1"):(stato));
			pst.setInt(3, oid.intValue());
			rs = pst.executeQuery();
			if(rs.next())
			{
				toRet = build(conn,rs,nonscaduti,withChilds,statoFigli,statoSezione);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
			try{rs.close();}catch(Exception ex){}
		}
		return toRet;
	}
	
	
	public  String getDescrizione(Connection conn, Integer oid, boolean nonscaduti,boolean withChilds, String stato, String statoFigli, String statoSezione) throws Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		String toRet = null;
		String query =  "select descrizione from dpat_sez_new where (? is null or stato::text = ANY (string_to_array(?, ',')) ) and id = ? "+(nonscaduti ? " and data_scadenza is null" : "");
		
		try
		{
			pst = conn.prepareStatement(query);
			pst.setString(1, stato);
			pst.setString(2, (stato==null)?("-1"):(stato));
			pst.setInt(3, oid.intValue());
			rs = pst.executeQuery();
			if(rs.next())
			{
				toRet = rs.getString("descrizione");
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
			try{rs.close();}catch(Exception ex){}
		}
		return toRet;
	}
	
	public Sezione()
	{
		statiCong.add(0);
		statiCong.add(2);
		statiCongString = statiCong.toString().replaceAll(" ","").replaceAll("[\\[\\]]","");
		statiPreCong.add(1);
		statiPreCongString = statiCong.toString().replaceAll(" ","").replaceAll("[\\[\\]]","");
	
	}
	
	public Sezione build(Connection conn, ResultSet rs, boolean nonscaduti, boolean withChilds, String statoFigli, String statoSezione) throws Exception 
	{
		PreparedStatement pst = null;
		ResultSet rs1 = null;
		Sezione toRet = new Sezione();
		
		try
		{
			
			toRet.setOid(rs.getLong("id"));
			toRet.setCodiceRaggruppamento(rs.getInt("cod_raggruppamento"));
			toRet.setAnno(rs.getInt("anno"));
			toRet.setDescrizione(rs.getString("descrizione"));
			toRet.setOrdine(rs.getInt("ordinamento"));
			toRet.setScadenza(rs.getTimestamp("data_scadenza"));
			toRet.setColor(rs.getString("color"));
			 
			String query = "select * from dpat_piano_attivita_new where stato != 1 and id_sezione = ? "+(nonscaduti ? " and data_scadenza is null": "")+" order by ordinamento";
			pst = conn.prepareStatement(query);
			pst.setInt(1, toRet.getOid().intValue());
			 
			rs1 = pst.executeQuery();
			
			if(withChilds)
				toRet.setPianiAttivitaFigli(new PianoAttivita().buildList(conn,rs1,nonscaduti,true, statoFigli, statoSezione));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
			try{rs1.close();}catch(Exception ex){}
		}
		return toRet;
	}
	
	
	
	public boolean isCongelato(Connection db, int anno)
	{
		Boolean isCongelato = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			pst = db.prepareStatement("select distinct stato != 1 from dpat_sez_new where data_scadenza is null and anno = ?");
			pst.setInt(1, anno);
			rs = pst.executeQuery();
			if(rs.next())
			{
				isCongelato = rs.getBoolean(1);
			}
			/*e ora faccio lo stesso per i templates, cioe' quelli in precongelamento */
			pst.close();
			rs.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
			try{rs.close();}catch(Exception ex){}
		}
		return isCongelato;
	}
	
	
}
