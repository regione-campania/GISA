/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.dpat2019.base;


import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.aspcfs.modules.dpat2019.action.Dpat;
import org.aspcfs.modules.dpat2019.base.DpatWrapperSezioniBean;
import org.aspcfs.modules.dpat2019.base.DpatPianoAttivitaNewBeanPreCong;
 


public class Indicatore extends IndicatoreInterface implements Cloneable {

	public ArrayList<Integer> statiCong = new ArrayList<Integer>();
	public String statiCongString = null;
	public ArrayList<Integer> statiPreCong = new ArrayList<Integer>();
	public String statiPreCongString = null;
	
	public Indicatore()
	{
		statiCong.add(0);
		statiCong.add(2);
		statiCongString = statiCong.toString().replaceAll(" ","").replaceAll("[\\[\\]]","");
		statiPreCong.add(1);
		statiPreCongString = statiCong.toString().replaceAll(" ","").replaceAll("[\\[\\]]","");
	}

	
	public  ArrayList<Indicatore> buildList(Connection conn, ResultSet rs ) throws Exception 
	{
		ArrayList<Indicatore> toRet = new ArrayList<Indicatore>();
		
		while(rs.next())
		{
			toRet.add(build(conn,rs ));
		}
		
		return toRet;
	}
	
	public  Indicatore build(Connection conn, ResultSet rs ) throws Exception 
	{
		Indicatore toRet = new Indicatore();
		toRet.setOid(rs.getLong("id"));
		toRet.setCodiceRaggruppamento(rs.getInt("cod_raggruppamento"));
		toRet.setOidPianoAttivita(rs.getLong("id_piano_attivita"));
		toRet.setAnno(rs.getInt("anno"));
		toRet.setTipoItemDpat(rs.getInt("tipo_item_dpat"));
		toRet.setRendic_per_campioni(rs.getBoolean("rendic_per_campioni"));
		toRet.setDescrizione(rs.getString("descrizione"));
		toRet.setStato(rs.getInt("stato"));
		toRet.setOrdine(rs.getInt("ordinamento"));
		toRet.setCodiceEsame(rs.getString("codice_esame"));
		toRet.setScadenza(rs.getTimestamp("data_scadenza"));
		
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
		toRet.setCodiceAliasPadre(pianoAttivitaPadre.getCodiceAliasAttivita());
		if(pianoAttivitaPadre.getOid()!=null)
			toRet.setIdPianoAttivita(pianoAttivitaPadre.getOid().intValue());
		return toRet;
	}
	
	 
	public  ArrayList<Indicatore> searchVersioniPerCodiceRaggruppamento(Connection conn, Integer codRaggruppamento, int anno) throws SQLException
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<Indicatore> toRet = null;
		
		
		try
		{
			pst = conn.prepareStatement("select b.*, least(a.data_scadenza, b.data_scadenza) as min_scadenza from dpat_indicatore_new b join dpat_piano_attivita_new a on b.id_piano_attivita = a.id where b.cod_raggruppamento = ? and b.anno  = ? order by least(a.data_scadenza, b.data_scadenza) is not null, min_scadenza  desc ");
			pst.setInt(1, codRaggruppamento);
			pst.setInt(2,anno);
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
	
	public  Indicatore buildByOid(Connection conn, Integer oid,boolean nonscaduti)
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		Indicatore toRet = null;
		String query = "select * from dpat_indicatore_new where id = ?  "+(nonscaduti ? " and data_scadenza is null": "") ;
		
		try
		{
			pst = conn.prepareStatement(query);
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
	
	 
	
	
	/*inserisco dummy child per piano attivita specificato soltanto se effettivamente questo non ha figli */
	public Integer insertDummyChildPerPianoAttivitaScelto(Connection db, Integer anno, Long pianoAttivitaPadre,Integer statoInput, Integer userId) throws Exception
	{
		 
		 String query1 = null; 
		 String query2 = null; 
		 PreparedStatement pst = null;
		 ResultSet rs = null;
		 
		try
		{
			pst = db.prepareStatement("select tipo_attivita from dpat_piano_attivita_new where id = ?");
			pst.setInt(1, pianoAttivitaPadre.intValue());
			rs = pst.executeQuery();
			rs.next();
			String tipoAttivita = rs.getString(1);
			
			rs.close();
			pst.close();
			
			query1 = "select count(*), max(stato) from dpat_indicatore_new where id_piano_attivita = ? and data_scadenza is NULL and (stato = ? or stato is null)";
			query2 = "insert into dpat_indicatore_new(id_piano_attivita,anno,descrizione,ordinamento,data_scadenza,stato,codice_esame,alias_indicatore,entered,modified,entered_by,modified_by)"
				 		+ " values(?, ?, ?, ?, NULL, ?,'','DEFAULT',now(),now(),?,?) returning id";
			
			pst = db.prepareStatement(query1);
			pst.setInt(1,pianoAttivitaPadre.intValue() );
			pst.setInt(2,statoInput );
			pst.setInt(3,statoInput );
			pst.setInt(4,userId );
			pst.setInt(5,userId );
			 
			rs = pst.executeQuery();
			rs.next();
			int totFigli = rs.getInt(1);
			int statoPadre = rs.getInt(2);
			pst.close();
			rs.close();
			
			 
			 if(totFigli > 0) /*ha gia' figli attivi */
			{
				return -1;
			}
			
			/*se lo stato del padre e' 1, allora e' uno anche il figlio, altrimenti...*/
			 int stato = statoInput;
			 if(statoInput!=1)
				 stato = statoPadre == 1 ? 1 :getStatoDopoModifica(db, anno);
			 
			 
			 pst = db.prepareStatement(query2);
			 pst.setInt(1, pianoAttivitaPadre.intValue());
			 pst.setInt(2, anno);
			 pst.setString(3, "INDICATORE DI DEFAULT DA SOSTITUIRE");
			 pst.setInt(4, 0);
			 pst.setInt(5, stato);
			 
			 pst.execute();
			 
			 /*ottengo id fisico del dummy inserito e lo ritorno */
			  
			 rs = pst.getResultSet();
			 rs.next();
			 Integer idFisicoDummy = rs.getInt("id");
			 System.out.println("IL PIANO ATTIVITA SPECIFICATO ("+pianoAttivitaPadre+") AVEVA 0 FIGLI ATTIVI >> AGGIUNTO DUMMY CHILD" );
			 return idFisicoDummy;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			try{ pst.close(); }catch(Exception ex){}
			try{ rs.close(); }catch(Exception ex){}
		}
	}

	
	/*inserisce dummy child per l'ultimo piano attivita inserito, solo se questo effettivamente non ha figli */
	public  Integer insertDummyChildPerUltimoPianoAttivitaInserito(Connection db,Integer anno,Integer userId) throws Exception 
	
	{
		
		 String query0= null,query1 = null,query= null;
		 Integer idFisicoDummy = null;
		  
		 PreparedStatement pst = null;
		 ResultSet rs = null;
		 
		 
		 
		 
		 
		 	/*Modifica del 29/09/2017 : l'INDICATORE DEVE PORTARSI IL TIPO_ATTIVITA DLE PADRE : SE E' PIANO IL PADRE SARA' PIANo, SE E' ATTIVITA' SARA' ATTIVITA */
			 query0 = "select  id  from dpat_piano_attivita_new where id = (select max(id) from dpat_piano_attivita_new where anno = ?  )  ";
			  query1 =  "select count(*) from dpat_indicatore_new where id_piano_attivita = ?"; /*tutti i figli devono avere stesso tipo attivita quindi la max ha senso p*/
			   query =   "insert into dpat_indicatore_new(id_piano_attivita,anno,descrizione,ordinamento,data_scadenza,stato,codice_esame,alias_indicatore,entered,modified,entered_by,modified_by)"
			 		+ " values(?, ?, ?, ?, NULL, ?,'','DEFAULT',now(),now(),?,?) returning id";
		 
		 
		 
		 try
		 {
			 
			 pst = db.prepareStatement(query0);
			 pst.setInt(1, anno);
			 
			 rs = pst.executeQuery();
			 /*riottengo oid ultimo piano_attivita inserito*/
			 rs.next();
			 int idPianoAttivitaNuovo = rs.getInt(1);
			 pst.close();
			 rs.close();
			 pst = db.prepareStatement(query1);
			 pst.setInt(1, idPianoAttivitaNuovo);
			 
			 rs = pst.executeQuery();
			 rs.next();
			 int numeroFigli = rs.getInt(1);
			 if(numeroFigli > 0) /*ha gia' figli quindi non devo aggiungere il dummy */
				 return -1;
			 
			 /*altrimenti devo aggiunger efiglio dummy del piano attivita */
			 
			pst.close();
			rs.close();
			int stato =  new DpatWrapperSezioniBean().getStatoDopoModifica(db, anno);
			 
			 
			 
			 pst = db.prepareStatement(query);
			 pst.setInt(1, idPianoAttivitaNuovo);
			 pst.setInt(2, anno);
			 pst.setString(3, "INDICATORE DI DEFAULT DA SOSTITUIRE");
			 pst.setInt(4, 0);
			 pst.setInt(5, stato);
			 pst.setInt(6, userId);
			 pst.setInt(7, userId);
			 pst.execute();
			 
			 /*ottengo id fisico del dummy inserito e lo ritorno */
			  
			 rs = pst.getResultSet();rs.next();
			 idFisicoDummy = rs.getInt("id");
			 System.out.println("IL PIANO ATTIVITA SPECIFICATO ("+idPianoAttivitaNuovo+") AVEVA 0 FIGLI >> AGGIUNTO DUMMY CHILD" );
			 return idFisicoDummy;
			 
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 throw ex;
		 }
		 finally
		 {
			 try{pst.close();} catch(Exception ex){}
			 
			 try{rs.close();} catch(Exception ex){}
		 }
		 
				 
	}
	
	
	
	public Integer insertBeforeOrAfter(Connection db, IndicatoreInterface toInsert, String where,Integer statoInput,String statoIncrementaOrdine, int idPianoAttivita, int anno, int ordinamento,Integer userId) throws Exception 
	{
		 boolean autocommit = db.getAutoCommit();
		
		 int u = 0;
		 String descrizione = toInsert.getDescrizione();
		 String codice_esame = toInsert.getCodiceEsame();
		 String alias = toInsert.getAliasIndicatore();
		 Integer codiceInternoIndicatore = toInsert.getCodiceInternoIndicatore();
		 Integer codiceInternoPianiGestioneCu = toInsert.getCodiceInternoPianiGestioneCu();
		 String codiceInternoAttivitaGestioneCu = toInsert.getCodiceInternoAttivitaGestioneCu();
		 String codiceInternoUnivocoTipoAttivitaGestioneCu = toInsert.getCodiceInternoUnivocoTipoAttivitaGestioneCu();
		 String codiceAliasIndicatore = toInsert.getCodiceAliasIndicatore();
		 Integer tipoItemDpat = toInsert.getTipoItemDpat();
		 Boolean rendicPerCampioni = toInsert.getRendic_per_campioni();
		 
		 Integer codRaggruppamento = toInsert.getCodiceRaggruppamento();
		 
		 if(!where.toLowerCase().equals("up"))
			 ordinamento+=1;
		 
		 PreparedStatement pst = null;
		  
		 ResultSet rs = null;
		 
			String queryInsert1 = "insert into dpat_indicatore_new(cod_raggruppamento,id_piano_attivita,anno,descrizione,ordinamento,stato,codice_esame,alias_indicatore,codice_interno_indicatore,codice_interno_piani_gestione_cu,codice_interno_attivita_gestione_cu,codice_interno_univoco_tipo_attivita_gestione_cu,codice_alias_indicatore,entered,modified,entered_by,modified_by,tipo_item_dpat, rendic_per_campioni)"
			 		+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now(),?,?,?,?) returning id";
		 
		 
		 try
		 {
			 db.setAutoCommit(false);
			 
			 Integer stato = statoInput;
			 if(stato==null || stato!=1)
				 stato = new DpatWrapperSezioniBean().getStatoDopoModifica(db,anno);
			 
			 pst = db.prepareStatement(queryInsert1);
			 
			 Dpat.setNullableField(pst, codRaggruppamento, ++u, java.sql.Types.INTEGER);
			 
			 /*shifto tutti i successivi*/
			 new Indicatore().incrementaOrdiniPerPianoAttivita(db,"ordinamento >= "+ordinamento, idPianoAttivita,anno,statoIncrementaOrdine,userId);
			 
			 Dpat.setNullableField(pst, idPianoAttivita, ++u, java.sql.Types.INTEGER);
			 Dpat.setNullableField(pst, anno, ++u, java.sql.Types.INTEGER);
			 Dpat.setNullableField(pst, descrizione, ++u, java.sql.Types.VARCHAR);
			 Dpat.setNullableField(pst, ordinamento, ++u, java.sql.Types.INTEGER);
			 Dpat.setNullableField(pst, stato, ++u, java.sql.Types.INTEGER);
			 Dpat.setNullableField(pst, codice_esame, ++u, java.sql.Types.VARCHAR);
			 Dpat.setNullableField(pst, alias, ++u, java.sql.Types.VARCHAR);
			 Dpat.setNullableField(pst, codiceInternoIndicatore, ++u, java.sql.Types.INTEGER);
			 Dpat.setNullableField(pst, codiceInternoPianiGestioneCu, ++u, java.sql.Types.INTEGER);
			 Dpat.setNullableField(pst, codiceInternoAttivitaGestioneCu, ++u, java.sql.Types.VARCHAR);
			 Dpat.setNullableField(pst, codiceInternoUnivocoTipoAttivitaGestioneCu, ++u, java.sql.Types.VARCHAR);
			 Dpat.setNullableField(pst, codiceAliasIndicatore, ++u, java.sql.Types.VARCHAR);
			 Dpat.setNullableField(pst, userId, ++u, java.sql.Types.INTEGER);
			 Dpat.setNullableField(pst, userId, ++u, java.sql.Types.INTEGER);
			 Dpat.setNullableField(pst, tipoItemDpat, ++u, java.sql.Types.INTEGER);
			 Dpat.setNullableField(pst, rendicPerCampioni, ++u, java.sql.Types.BOOLEAN);
			 
			 pst.execute();
			  
			 db.commit();
			 
			 
			 /*ottengo id inserito*/
			 rs = pst.getResultSet();
			 rs.next();
			 Integer idInserito = rs.getInt("id");
			 
			 return idInserito;
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 db.rollback();
			 throw ex;
		 }
		 finally
		 {
			 try{pst.close();} catch(Exception ex){}
			 
			 
			 try{
				 db.setAutoCommit(autocommit);
			 } catch(Exception ex){ex.printStackTrace();}
		 }
	}
	
	
	
	
	public void incrementaOrdiniPerPianoAttivita(Connection conn, String condition, Integer oidPianoAttivita, Integer anno,String stato,Integer userId)
	{
		PreparedStatement pst = null;
		String query = null;
		
		
			query = "update dpat_indicatore_new set ordinamento = ordinamento+1 ,modified=now(),modified_by=?  where "+condition+" and id_piano_attivita = ? and anno = ? ";
		
		try
		{
			
			pst = conn.prepareStatement(query);
			pst.setInt(1, userId);
			pst.setInt(2, oidPianoAttivita);
			pst.setInt(3, anno);
			
			pst.executeUpdate();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
		}
	}
	
	
	public  void disabilitaByOid(Connection conn, int id,int anno, String stato, String statoFigli, String statoSezione,Integer userId ) throws Exception {
		PreparedStatement pst = null;
		String query = null;
		 
		 
			query = "update dpat_indicatore_new set data_scadenza = CURRENT_TIMESTAMP, stato = ? ,modified=now(),modified_by=? where id = ? ";
		 
		
		try
		{
			pst = conn.prepareStatement(query);
			 
				int newstato = new DpatWrapperSezioniBean().getStatoDopoModifica(conn, anno);
				pst.setInt(1, newstato);
				pst.setInt(2, userId);
				pst.setInt(3, id);
				
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
	
	public   void disabilitaByOid(Connection conn, int id,int anno, Integer userId) throws Exception 
	{
		PreparedStatement pst = null;
		
		try
		{
			 
			
			pst = conn.prepareStatement("update dpat_indicatore_new set data_scadenza = current_timestamp, stato = ? ,modified=now(),modified_by=?  where id = ? ");
			int newstato = new DpatWrapperSezioniBean().getStatoDopoModifica(conn, anno);
			pst.setInt(1, newstato);
			pst.setInt(2, userId);
			pst.setInt(3, id);
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
	
	
	public Integer update(IndicatoreInterface newValue, IndicatoreInterface indicatoreRiferimento, Connection db, String stato, String statoFigli, String statoSezione,Integer statoInput,String statoIncrementaOrdine, boolean congelato,Integer userId) throws Exception {
		
		
		if(true)
		//if(congelato)
		{
		PreparedStatement pst = null;
		Integer newIdFisicoInserito = null;
		 
		
		try
		{
			
		    int newstato = new DpatWrapperSezioniBean().getStatoDopoModifica(db, newValue.getAnno());
		    newValue.setStato(newstato);
		   
		    /*disabilito quello che sto aggiornando */
			 new Indicatore().disabilitaByOid(db, indicatoreRiferimento.getOid().intValue(), newValue.getAnno(), stato, statoFigli, statoSezione, userId);
		    
			/*faccio inserimento di quello "nuovo" (cioe' la versione modificata) dopo quello vecchio (cioe' quello modificato)*/
		     newIdFisicoInserito = new Indicatore().insertBeforeOrAfter(db, newValue, "down", statoInput,statoIncrementaOrdine, indicatoreRiferimento.getOidPianoAttivita().intValue(), indicatoreRiferimento.getAnno(), indicatoreRiferimento.getOrdine(),userId);
			
			 return newIdFisicoInserito;
			 
			
			 
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
		else
		{



			PreparedStatement pst = null;
			String updateQuery = "update dpat_indicatore_new set descrizione = ?, alias_indicatore = ?, codice_alias_indicatore = ?, codice_esame = ?, stato = ?, modified=now(),modified_by=?,tipo_item_dpat = ?,rendic_per_campioni=? where id = ?";
			Integer idUpdated = indicatoreRiferimento.getOid().intValue(); /*visto che l'update e' secco, l'id dell 'elemento aggiornato e' lo stesso ... */
			try
			{
				pst = db.prepareStatement(updateQuery);
				pst.setString(1, newValue.getDescrizione());
				pst.setString(2, newValue.getAliasIndicatore());
				pst.setString(3, newValue.getCodiceAliasIndicatore());
				pst.setString(4, newValue.getCodiceEsame());
				int newstato = 1;
				pst.setInt(5, newstato);
				pst.setInt(6, userId);
				pst.setInt(7, newValue.getTipoItemDpat());
				pst.setBoolean(8, newValue.getRendic_per_campioni());
				pst.setInt(9, indicatoreRiferimento.getOid().intValue());
				pst.executeUpdate();
				return idUpdated;
				
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
		 
		
	}
	
	
	public  int deleteDummyBrother(Connection db, IndicatoreInterface indicatoreRiferimento,String stato) throws Exception 
	{
		

		 String query = null; 
		
			query = "delete from dpat_indicatore_new where descrizione ilike 'INDICATORE DI DEFAULT DA SOSTITUIRE' and anno = ? and id_piano_attivita = ? and (? is null or stato::text = ANY (string_to_array(?, ','))) "; 
			 PreparedStatement pst = null;
			 
			 try
			 {
				 pst = db.prepareStatement(query);
				 pst.setInt(1, indicatoreRiferimento.getAnno());
				 pst.setInt(2, indicatoreRiferimento.getOidPianoAttivita().intValue());
				 pst.setObject(3, (stato==null)?("-1"):(stato));
				 pst.setString(4, (stato==null)?("-1"):(stato));
				  
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
	public  Indicatore searchLastChildOf(Connection db, Integer oidPianoAttivita,boolean nonscaduti) throws Exception {
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		String query = "select * from dpat_indicatore_new where id_piano_attivita = ? "+(nonscaduti ? " and data_scadenza is null ": " ")+" order by ordinamento desc";
		
		try
		{
			pst = db.prepareStatement(query);
			pst.setInt(1, oidPianoAttivita);
			 
			rs = pst.executeQuery();
			Indicatore toRet = new Indicatore();
			if(rs.next())
				toRet = new Indicatore().build(db, rs);
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
	
	
	public Integer spostaCompetenzeFromTo(Long oidFrom, Long oidTo,Connection conn) throws Exception
	{
		 
		
		PreparedStatement pst = null;
		try
		{
			pst = conn.prepareStatement("insert into dpat_competenze_struttura_indicatore(id_struttura,id_indicatore, "
					+ "competenza_attribuita,entered,entered_by,modified,modified_by,enabled,id_dpat,note) "
					+ " select id_struttura,?,competenza_attribuita,entered,entered_by,modified,modified_by,enabled,id_dpat,note "
					+ " from dpat_competenze_struttura_indicatore where id_indicatore = ? ");
			pst.setLong(1,oidTo );
			pst.setLong(2, oidFrom );
			Integer nInseriti = pst.executeUpdate();
			return nInseriti;
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			try{pst.close();}catch(Exception ex){}
		}
	}

	@Override
	public int aggiornaCodiceAliasIndicatorePerTutteVersioni(Connection db, IndicatoreInterface bean, Integer userId)
			throws Exception {
		
		

		PreparedStatement pst = null;
		try
		{
			String nuovoCodIndicatore = bean.getCodiceAliasIndicatore();
			Integer codRaggruppamento = bean.getCodiceRaggruppamento();
			pst = db.prepareStatement("update dpat_indicatore_new set codice_alias_indicatore = ? ,modified=now(),modified_by=? where cod_raggruppamento = ? and stato = 2 ");
			pst.setString(1, nuovoCodIndicatore);
			pst.setInt(2, userId);
			pst.setInt(3, codRaggruppamento);
			
			return pst.executeUpdate();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			try { pst.close(); } catch(Exception ex) {}
		}
	
	
		
	}
	
	
	
	public  int  getStatoDopoModifica(Connection db,int anno) throws Exception
	{
		ResultSet rs = null;
		PreparedStatement pst = null;
		int stato = -1;
		try
		{
			 
			/*logica scelta dello stato*/
			 /*SE IN TUTTO L'ALBERO, PER QUELL'ANNO, ESISTE ALMENO UN NODO IN STATO 2, VUOL DIRE CHE QUELL'ALBERO AD UN CERTO PUNTO E' STATO CONGELATO, QUINDI
			  * QUALUNQUE OPERAZIONE EFFETTUATA SU DI ESSO DEVE FAR ANDARE IL NODO IN STATO 0 (op post congelamento)
			  * SE INVECE NON ESISTONO NODI IN STATO 2 PUO ' ESSERE O PERCHE' E' UN ALBERO IN STATO INTERMEDIO DI MODIFICA (TUTTI 1, pre congelamento) E QUINDI IN TAL CASO
			  * LE OPERAZIONI SONO DI PRE-CONGELAMENTO (QUINDI DEVONO FAR ANDARE IN STATO 1) OPPURE PERCHE' IN POST CONGELAMENTO SI SONO FATTI DIVENTARE TUTTI I NODI DA STATO 2 A STATO0
			  * MA E' POCO PLAUSIBILE POICHE' QUESTO SIGNIFICHEREBBE CHE SONO STATI PUNTUALMENTE MODIFICATI TUTTI I NODI
			  */
			 /*per lo stato */
			
			 pst = db.prepareStatement("select  greatest(max(a.stato), max(b.stato)) as massimo from dpat_piano_attivita_new a join dpat_indicatore_new b on a.id = b.id_piano_attivita where a.anno = ? and a.data_scadenza is null and b.data_scadenza is null");
			 pst.setInt(1, anno);
			 rs = pst.executeQuery();
			 rs.next();
			 int max = rs.getInt(1);
			  stato = max % 2; /* se il massimo e' 2 o e' 0 allora e' modifica post congelamento, altrimenti se il massimo e' 1, sicuramente non esistono anche nodi in 0, quindi op pre congelamento */
			 return stato;
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
	
	public boolean isCongelato(Connection db, int anno)
	{
		Boolean isCongelato = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
			pst = db.prepareStatement("select distinct stato != 1 from dpat_indicatori_new where data_scadenza is null and anno = ?");
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


	public int spostaAssociazioniFromTo(Long idIndicatoreOld, long idIndicatoreNew, boolean mantieniAssociazioni, int userId, Connection db) throws SQLException {
		int tot = 0;
		PreparedStatement pst = db.prepareStatement("select * from dpat_sposta_associazioni_indicatore(?, ?, ?,?)");
		pst.setLong(1, idIndicatoreOld);
		pst.setLong(2, idIndicatoreNew);
		pst.setBoolean(3, mantieniAssociazioni);
		pst.setInt(4, userId);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			tot = rs.getInt(1);
		return tot;
	}
 
	
	
}
