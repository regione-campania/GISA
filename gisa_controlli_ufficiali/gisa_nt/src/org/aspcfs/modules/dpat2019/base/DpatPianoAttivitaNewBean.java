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

import org.aspcfs.modules.dpat2019.base.DpatPianoAttivitaNewBeanInterface;

public class DpatPianoAttivitaNewBean extends DpatPianoAttivitaNewBeanInterface<DpatIndicatoreNewBean> {

	 
	private static final long serialVersionUID = 1L;
	
	 
	
	public ArrayList<DpatPianoAttivitaNewBean> buildList(Connection conn, ResultSet rs,boolean nonscaduti, boolean withChilds) throws Exception
	{
		ArrayList<DpatPianoAttivitaNewBean> toRet = new ArrayList<DpatPianoAttivitaNewBean>();
		
		while(rs.next())
		{
			toRet.add(build(conn,rs,nonscaduti,withChilds));
		}
		
		return toRet;
	}
	
	
	
	
	public ArrayList<DpatPianoAttivitaNewBean> searchVersioniPerCodiceRaggruppamento(Connection conn, Integer codRaggruppamento, boolean withChilds, int anno) throws Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<DpatPianoAttivitaNewBean> toRet = null;
		try
		{
			/*prendo tutti quelli con stesso codice raggruppamento, ma siccome stanno in join con gli indicatori, se non metto la distinct sull'id fisico avrei un'entry
			 * per ciascuno degli indicatori agganciati allo stesso nodo
			 */
			
			
			pst = conn.prepareStatement("select * from (select distinct on (a.id) a.*, least(a.data_scadenza, b.data_scadenza) as min_scadenza from dpat_indicatore_new b join dpat_piano_attivita_new a on b.id_piano_attivita = a.id where a.stato != 1 and b.stato != 1 and a.cod_raggruppamento = ? ) c "
					+ " where anno = ?   order by c.min_scadenza");
			pst.setInt(1, codRaggruppamento);
			pst.setInt(2,anno);
			rs = pst.executeQuery();
			toRet =  buildList(conn, rs,false, withChilds);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try { pst.close(); } catch(Exception ex){}
			try { rs.close(); } catch(Exception ex){}
		}
		return toRet;
	}
	
	
	public DpatPianoAttivitaNewBean buildByOid(Connection conn, Integer oid, boolean nonscaduti, boolean withChilds)
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		DpatPianoAttivitaNewBean toRet = null;
		String query = "select * from dpat_piano_attivita_new where stato != 1 and id = ? "+(nonscaduti ? " and data_scadenza is null": "");
		
		try
		{
			pst = conn.prepareStatement(query);
			pst.setInt(1, oid.intValue());
			 
			rs = pst.executeQuery();
			if(rs.next())
			{
				toRet = build(conn,rs,nonscaduti,withChilds);
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
	
	
	public  DpatPianoAttivitaNewBean build(Connection conn, ResultSet rs,boolean nonscaduti, boolean withChilds) throws Exception 
	{
		DpatPianoAttivitaNewBean toRet = new DpatPianoAttivitaNewBean();
		PreparedStatement pst = null;
		ResultSet rs1 = null;
		try
		{
			toRet.setOid(rs.getLong("id"));
			toRet.setCodiceRaggruppamento(rs.getInt("cod_raggruppamento"));
			toRet.setOidSezione(rs.getLong("id_sezione"));
			toRet.setAnno(rs.getInt("anno"));
			toRet.setDescrizione(rs.getString("descrizione"));
			toRet.setOrdine(rs.getInt("ordinamento"));
			toRet.setScadenza(rs.getTimestamp("data_scadenza"));
			toRet.setCodiceEsame(rs.getString("codice_esame"));
//			toRet.setCodiceAlias(rs.getString("codice_alias"));
			toRet.setTipoAttivita(rs.getString("tipo_attivita"));
			toRet.setCodiceInternoPiano(rs.getInt("codice_interno_piano"));
			toRet.setCodiceInternoAttivita(rs.getInt("codice_interno_attivita"));
			toRet.setAliasPiano(rs.getString("alias_piano"));
			toRet.setAliasAttivita(rs.getString("alias_attivita"));
			toRet.setCodiceAliasAttivita(rs.getString("codice_alias_attivita"));
			toRet.setStato(rs.getInt("stato"));
			
			String query = null;
			
				query = "select * from dpat_indicatore_new where stato != 1 and id_piano_attivita = ? "+(nonscaduti ? " and data_scadenza is null": "")+" order by ordinamento ";
			
			
			pst = conn.prepareStatement(query);
			pst.setInt(1, toRet.getOid().intValue());
			
			rs1 = pst.executeQuery();
			
			if(withChilds)
				toRet.setIndicatoriFigli(new DpatIndicatoreNewBean().buildList(conn,rs1));
			
			/*per la descrizione del path dagli antenati ad esso */
			DpatSezioneNewBean sezioneMadre = new DpatSezioneNewBean().buildByOid(conn, rs.getInt("id_sezione"), false,false);
			String descrSezMadre = sezioneMadre.getDescrizione();
			toRet.setDescrSezMadre(descrSezMadre);
			
		}
		catch(Exception ex){
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
	
	/*dato oid piano attivita ritorna il dpatindicatore figlio con l'ordine massimo */
	public DpatPianoAttivitaNewBean searchLastChildOf(Connection db, Integer oidSezione,boolean nonscaduti) throws Exception {
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		String query =  "select * from dpat_piano_attivita_new where stato != 1 and id_sezione = ? "+(nonscaduti ? " and data_scadenza is null ": " ")+" order by ordinamento desc";
		try
		{
			pst = db.prepareStatement(query);
			pst.setInt(1, oidSezione);
			
			rs = pst.executeQuery();
			rs.next();
			DpatPianoAttivitaNewBean toRet = new DpatPianoAttivitaNewBean().build(db, rs,nonscaduti,true);
			return toRet;
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
			try{rs.close();}catch(Exception ex){}
		}
	}
	
}
