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

import org.aspcfs.modules.dpat2019.base.DpatIndicatoreNewBeanAbstract;

public class DpatIndicatoreNewBeanPreCong extends DpatIndicatoreNewBeanAbstract implements Cloneable {

	private static final long serialVersionUID = 1L;
	
	public  ArrayList<DpatIndicatoreNewBeanPreCong> buildList(Connection conn, ResultSet rs  ) throws Exception
	{
		ArrayList<DpatIndicatoreNewBeanPreCong> toRet = new ArrayList<DpatIndicatoreNewBeanPreCong>();
		
		while(rs.next())
		{
			toRet.add(build(conn,rs ));
		}
		
		return toRet;
	}
	
	 
	
	public  DpatIndicatoreNewBeanPreCong build(Connection conn, ResultSet rs ) throws Exception 
	{
		DpatIndicatoreNewBeanPreCong toRet = new DpatIndicatoreNewBeanPreCong();
		toRet.setOid(rs.getLong("id"));
		toRet.setCodiceRaggruppamento(rs.getInt("cod_raggruppamento"));
		toRet.setOidPianoAttivita(rs.getLong("id_piano_attivita"));
		toRet.setAnno(rs.getInt("anno"));
		toRet.setDescrizione(rs.getString("descrizione"));
		toRet.setStato(rs.getInt("stato"));
		toRet.setOrdine(rs.getInt("ordinamento"));
		toRet.setCodiceEsame(rs.getString("codice_esame"));
		toRet.setScadenza(rs.getTimestamp("data_scadenza"));
		toRet.setTipoItemDpat(rs.getInt("tipo_item_dpat"));
		toRet.setRendic_per_campioni(rs.getBoolean("rendic_per_campioni"));
//		toRet.setCodiceAlias(rs.getString("codice_alias"));
		
		toRet.setCodiceInternoIndicatore(rs.getInt("codice_interno_indicatore"));
		toRet.setAliasIndicatore(rs.getString("alias_indicatore"));
		toRet.setCodiceInternoPianiGestioneCu(rs.getInt("codice_interno_piani_gestione_cu"));
		toRet.setCodiceInternoAttivitaGestioneCu(rs.getString("codice_interno_attivita_gestione_cu"));
		toRet.setCodiceInternoUnivocoTipoAttivitaGestioneCu(rs.getString("codice_interno_univoco_tipo_attivita_gestione_cu"));
		toRet.setCodiceAliasIndicatore(rs.getString("codice_alias_indicatore"));
		
		
		/*per costruire la stringa del path completo per comodita' */
		DpatPianoAttivitaNewBeanPreCong pianoAttivitaPadre = new DpatPianoAttivitaNewBeanPreCong().buildByOid(conn, rs.getInt("id_piano_attivita"), false,false);
		String descrSezMadre = pianoAttivitaPadre.getDescrSezMadre();
		String descrPianoAttivitaPadre = pianoAttivitaPadre.getDescrizione();
		toRet.setDescrSezMadre(descrSezMadre);
		toRet.setDescrPianoAttivitaPadre(descrPianoAttivitaPadre);
		return toRet;
	}
	
	 
 
	
	public  DpatIndicatoreNewBeanPreCong buildByOid(Connection conn, Integer oid,boolean nonscaduti)
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		DpatIndicatoreNewBeanPreCong toRet = null;
		
		try
		{
			pst = conn.prepareStatement("select * from dpat_indicatore_new where id = ? "+(nonscaduti ? " and data_scadenza is null": "") );
			pst.setInt(1, oid.intValue());
			rs = pst.executeQuery();
			if(rs.next())
			{
				toRet = build(conn,rs );
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
	
	
	
	
	
	
	public  void deleteByOid(Connection conn, int id) throws Exception {
		PreparedStatement pst = null;
		
		try
		{
			pst = conn.prepareStatement("delete from dpat_indicatore_new  where id = ? ");
			pst.setInt(1, id);
			pst.executeUpdate();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
		}
		
	}
	
	 
	
	 
	public  int deleteDummyBrother(Connection db, DpatIndicatoreNewBeanAbstract indicatoreRiferimento) throws Exception 
	{
		 String query = "delete from dpat_indicatore_new where descrizione ilike 'INDICATORE DI DEFAULT DA SOSTITUIRE' and anno = ? and id_piano_attivita = ? and stato = 1 ";
			
			  
			  
			 PreparedStatement pst = null;
			 ResultSet rs = null;
			 
			 try
			 {
				  
				 
				 pst = db.prepareStatement(query);
				 pst.setInt(1, indicatoreRiferimento.getAnno());
				 pst.setInt(2, indicatoreRiferimento.getOidPianoAttivita().intValue());
				 int deleted = pst.executeUpdate();
				 return deleted;
				 
			 }
			 catch(Exception ex)
			 {
				 ex.printStackTrace();
				 throw ex;
			 }
			 finally
			 {
				 try{pst.close();} catch(Exception ex){}
				 
			 }
		
	}

	
	/*dato oid piano attivita ritorna il dpatindicatore figlio con l'ordine massimo */
	public  DpatIndicatoreNewBeanPreCong searchLastChildOf(Connection db, Integer oidPianoAttivita,boolean nonscaduti) throws Exception {
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try
		{
			pst = db.prepareStatement("select * from dpat_indicatore_new where id_piano_attivita = ? "+(nonscaduti ? " and data_scadenza is null ": " ")+" and stato = 1  order by ordinamento desc");
			pst.setInt(1, oidPianoAttivita);
			rs = pst.executeQuery();
			rs.next();
			DpatIndicatoreNewBeanPreCong toRet = new DpatIndicatoreNewBeanPreCong().build(db, rs);
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



	@Override
	public ArrayList<? extends DpatIndicatoreNewBeanAbstract> searchVersioniPerCodiceRaggruppamento(Connection conn,
			Integer codiceRaggruppamento, int anno) throws Exception {
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<DpatIndicatoreNewBeanPreCong> toRet = null;
		
		
		try
		{
			pst = conn.prepareStatement("select b.*, least(a.data_scadenza, b.data_scadenza) as min_scadenza from dpat_indicatore_new b join dpat_piano_attivita_new a on b.id_piano_attivita = a.id where a.stato = 1 and b.stato = 1 and b.cod_raggruppamento = ? and b.anno = ? order by min_scadenza ");
			pst.setInt(1, codiceRaggruppamento);
			pst.setInt(2, anno);
			rs = pst.executeQuery();
			toRet =  buildList(conn, rs);
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






	@Override
	public Integer spostaCompetenzeFromTo(Long oidFrom, Long oidTo,Connection conn)  throws Exception
	{
		return 0;
		/*per i modelli in stato pre-congelamento non esistono competenze associate poiche' sono associabili soltanto ai congelati */
		
	}



	
}
