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
import org.aspcfs.modules.dpat2019.base.DpatPianoAttivitaNewBean;
import org.aspcfs.modules.dpat2019.base.DpatSezioneNewBean;
import org.aspcfs.modules.dpat2019.base.DpatWrapperSezioniBean;
import org.aspcfs.modules.dpat2019.base.DpatIndicatoreNewBeanPreCong;
import org.aspcfs.modules.dpat2019.base.DpatPianoAttivitaNewBeanPreCong;
import org.aspcfs.modules.dpat2019.base.DpatSezioneNewBeanPreCong;
import org.json.JSONArray;
import org.json.JSONObject;

import com.darkhorseventures.framework.beans.GenericBean;

public class PianoAttivita extends PianoAttivitaInterface<Indicatore> {
	
	public PianoAttivita()
	{
		statiCong.add(0);
		statiCong.add(2);
		statiCongString = statiCong.toString().replaceAll(" ","").replaceAll("[\\[\\]]","");
		statiPreCong.add(0);
		statiPreCong.add(2);
		statiPreCongString = statiCong.toString().replaceAll(" ","").replaceAll("[\\[\\]]","");
	}

	 
	private static final long serialVersionUID = 1L;
	public ArrayList<Integer> statiCong = new ArrayList<Integer>();
	public String statiCongString = null;
	public ArrayList<Integer> statiPreCong = new ArrayList<Integer>();
	public String statiPreCongString = null;
	 
	
	public ArrayList<PianoAttivita> buildList(Connection conn, ResultSet rs,boolean nonscaduti, boolean withChilds, String statoFigli, String statoSezione) throws Exception
	{
		ArrayList<PianoAttivita> toRet = new ArrayList<PianoAttivita>();
		
		while(rs.next())
		{
			toRet.add(build(conn,rs,nonscaduti,withChilds,statoFigli,statoSezione));
		}
		
		return toRet;
	}
	
	
	
	
	public ArrayList<PianoAttivita> searchVersioniPerCodiceRaggruppamento(Connection conn, Integer codRaggruppamento, boolean withChilds, int anno, String statoFigli, String statoSezione) throws Exception
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		ArrayList<PianoAttivita> toRet = null;
		try
		{
			/*prendo tutti quelli con stesso codice raggruppamento, ma siccome stanno in join con gli indicatori, se non metto la distinct sull'id fisico avrei un'entry
			 * per ciascuno degli indicatori agganciati allo stesso nodo
			 */
			
			
			pst = conn.prepareStatement("select * from (select distinct on (a.id) a.*, least(a.data_scadenza, b.data_scadenza) as min_scadenza from dpat_indicatore_new b join dpat_piano_attivita_new a on b.id_piano_attivita = a.id where  a.cod_raggruppamento = ? ) c "
					+ " where anno = ?   order by (c.min_scadenza is not null), c.min_scadenza desc");
			pst.setInt(1, codRaggruppamento);
			pst.setInt(2,anno);
			rs = pst.executeQuery();
			toRet =  buildList(conn, rs,false, withChilds, statoFigli, statoSezione);
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
	
	
	public PianoAttivita buildByOid(Connection conn, Integer oid, boolean nonscaduti, boolean withChilds, int anno)
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		PianoAttivita toRet = null;
		String query = "select * from dpat_piano_attivita_new where (? is null or stato::text = ANY (string_to_array(?, ',')) ) and id = ? "+(nonscaduti ? " and data_scadenza is null": "");
		
		Boolean congelato = isCongelato(conn, anno);
		
		try
		{
			pst = conn.prepareStatement(query);
			pst.setString(1, (congelato)?(statiCongString):(null));
			pst.setString(2, (congelato)?(statiCongString):("-1"));
			pst.setInt(3, oid.intValue());
			 
			rs = pst.executeQuery();
			if(rs.next())
			{
				toRet = build(conn,rs,nonscaduti,withChilds,congelato?(statiCongString):(statiPreCongString),congelato?(statiCongString):(null));
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
	
	
	public  PianoAttivita build(Connection conn, ResultSet rs,boolean nonscaduti, boolean withChilds,  String statoFigli,  String statoSezione) throws Exception 
	{
		PianoAttivita toRet = new PianoAttivita();
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
			toRet.setTipoAttivita(rs.getString("tipo_attivita"));
			toRet.setCodiceInternoPiano(rs.getInt("codice_interno_piano"));
			toRet.setCodiceInternoAttivita(rs.getInt("codice_interno_attivita"));
			toRet.setAliasPiano(rs.getString("alias_piano"));
			toRet.setAliasAttivita(rs.getString("alias_attivita"));
			toRet.setCodiceAliasAttivita(rs.getString("codice_alias_attivita"));
			toRet.setStato(rs.getInt("stato"));
			
			String query = "select * from dpat_indicatore_new where id_piano_attivita = ? "+(nonscaduti ? " and data_scadenza is null": "")+" order by ordinamento ";
			
			pst = conn.prepareStatement(query);
			
			pst.setInt(1, toRet.getOid().intValue());
			
			rs1 = pst.executeQuery();
			
			if(withChilds)
				toRet.setIndicatoriFigli(new Indicatore().buildList(conn,rs1));
			
			/*per la descrizione del path dagli antenati ad esso */
			
			Sezione sezioneMadre = new Sezione();
			String descrSezMadre = sezioneMadre.getDescrizione(conn, rs.getInt("id_sezione"), false,false,statoSezione, statoSezione, statoSezione);
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
	
	
	public  Integer insertBeforeOrAfter(Connection db, PianoAttivitaInterface<Indicatore> toInsert, PianoAttivitaInterface<Indicatore> pianoAttivitaRiferimento, String where,Integer statoInput, String statoIncrementaOrdine,Integer userId) throws Exception
	{
		boolean autocommit = false;
		String queryInsert0 = null, queryInsert1 = null;
		Integer oidNuovoAggiunto = null;
		
			queryInsert0 = "insert into dpat_piano_attivita_new(id_sezione,anno,descrizione,ordinamento,data_scadenza,stato,codice_esame,alias_attivita,tipo_attivita,alias_piano,codice_interno_attivita,codice_interno_piano,codice_alias_attivita,entered,modified,entered_by,modified_by)"
			 		+ " values(?,?,?,?,NULL,?,?,?,?,?,?,?,?,now(),now(),?,?) returning id";
			
			queryInsert1 = "insert into dpat_piano_attivita_new(cod_raggruppamento,id_sezione,anno,descrizione,ordinamento,data_scadenza,stato,codice_esame,alias_attivita,tipo_attivita,alias_piano,codice_interno_attivita,codice_interno_piano,codice_alias_attivita,entered,modified,entered_by,modified_by)"
					+ " values(?,?,?,?,?,NULL,?,?,?,?,?,?,?,?,now(),now(),?,?) returning id";
			
		 Integer idSezione = Integer.parseInt(pianoAttivitaRiferimento.getOidSezione()+"");
		 Integer anno = pianoAttivitaRiferimento.getAnno();
		 int ordinamento = 1;
		 int u = 0;
		 
		 
		 String descrizione = toInsert.getDescrizione();
		 String codice_esame = toInsert.getCodiceEsame();
		 String tipoAttivita = toInsert.getTipoAttivita();
		 String aliasAttivita = toInsert.getAliasAttivita();
		 String aliasPiano = toInsert.getAliasPiano();
		 
		 Integer codiceInternoAttivita = toInsert.getCodiceInternoAttivita();
		 Integer codiceInternoPiano = toInsert.getCodiceInternoPiano();
		 String codiceAliasAttivita = toInsert.getCodiceAliasAttivita();
		 
		 Integer codRaggruppamento = toInsert.getCodiceRaggruppamento();
		 
		 PreparedStatement pst = null;
		 ResultSet rs = null;
		 
		 try
		 {
			 autocommit = db.getAutoCommit();
			 db.setAutoCommit(false);
			 
			 Integer stato = statoInput;
			 if(stato==null || stato!=1)
				 stato = new DpatWrapperSezioniBean().getStatoDopoModifica(db,anno);
			 
			 if(where.toLowerCase().equals("up"))
			 {
				 ordinamento = pianoAttivitaRiferimento.getOrdine();
				
				 
			 }
			 else /*down*/
			 {
				 ordinamento = pianoAttivitaRiferimento.getOrdine()+1;
			 }
			 
			 
			 
			 if(codRaggruppamento != null)
			 {
				 
				 pst = db.prepareStatement(queryInsert1);
				 pst.setInt(++u, codRaggruppamento);
				 
			 }
			 else
			 {
				 pst = db.prepareStatement(queryInsert0);
			 }
			 
			 
			 new PianoAttivita().incrementaOrdiniPerSezione (db,"ordinamento >= "+ordinamento, idSezione,anno,statoIncrementaOrdine,userId);
			 
			 Dpat.setNullableField(pst, idSezione, ++u, java.sql.Types.INTEGER);
			 Dpat.setNullableField(pst, anno, ++u, java.sql.Types.INTEGER);
			 Dpat.setNullableField(pst, descrizione, ++u, java.sql.Types.VARCHAR);
			 Dpat.setNullableField(pst, ordinamento, ++u, java.sql.Types.INTEGER);
			 Dpat.setNullableField(pst, stato, ++u, java.sql.Types.INTEGER);
			 Dpat.setNullableField(pst, codice_esame, ++u, java.sql.Types.VARCHAR);
			 Dpat.setNullableField(pst, aliasAttivita, ++u, java.sql.Types.VARCHAR);
			 Dpat.setNullableField(pst, tipoAttivita, ++u, java.sql.Types.VARCHAR);
			 Dpat.setNullableField(pst, aliasPiano, ++u, java.sql.Types.VARCHAR);
			 Dpat.setNullableField(pst, codiceInternoAttivita, ++u, java.sql.Types.INTEGER);
			 Dpat.setNullableField(pst, codiceInternoPiano, ++u, java.sql.Types.INTEGER);
			 Dpat.setNullableField(pst, codiceAliasAttivita, ++u, java.sql.Types.VARCHAR);
			 Dpat.setNullableField(pst, userId, ++u, java.sql.Types.INTEGER);
			 Dpat.setNullableField(pst, userId, ++u, java.sql.Types.INTEGER);
			 
			 pst.execute();
			 rs = pst.getResultSet();
			 rs.next();
			 oidNuovoAggiunto = rs.getInt(1);
			 pst.close();
			 
			 if(toInsert.getCodiceRaggruppamento() != null
					 && toInsert.getIndicatoriFigli() != null && toInsert.getIndicatoriFigli().size() > 0) /*cioe' se non e' inserimento ex novo */
			 {
				 /*la logica e' : gli inserimenti sono verso un fratello. Di volta in volta il fratello e' l'ultimo figlio (non scaduto) che e' stato trasferito (la versione
				  * trasferita, quindi la nuova non la vecchia).
				  *  Continua fino a quando esistono figli da trasferire. Il primo riferimento e' il dummy.
				  */
				 int indiceSuFigli = 0;
				 do
				 {
					 Indicatore        figlioDaTrasferire = (Indicatore) toInsert.getIndicatoriFigli().get(indiceSuFigli++);
					 if(figlioDaTrasferire.getScadenza() != null) /*gia' scaduto quindi non da trasferire */
						 continue;
					 
					 Integer idFisicoFiglioTrasferito = new Indicatore().insertBeforeOrAfter(db, figlioDaTrasferire, "down", statoInput,statoIncrementaOrdine, oidNuovoAggiunto, anno, ordinamento,userId);
					 
					 ordinamento++;
					 Integer competenzeSpostate = figlioDaTrasferire.spostaCompetenzeFromTo(figlioDaTrasferire.getOid(), idFisicoFiglioTrasferito.longValue(),db);
					 System.out.println(">>>>>>DPAT NUOVA GESTIONE: SONO STATE SPOSTATE "+competenzeSpostate+" COMPETENZE (MODELLO 5)");
						
				 }
				 while(indiceSuFigli < toInsert.getIndicatoriFigli().size());
			 }
			 else
			 {
				 Integer oidFisicoDummy = new Indicatore().insertDummyChildPerUltimoPianoAttivitaInserito(db, anno,userId);
				 Indicatore dummyInserito = new Indicatore().buildByOid(db, oidFisicoDummy, true); /*ottengo il dummy inserito */
			 }
			 
			 db.commit();
			 return oidNuovoAggiunto;
			 
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
			 try{rs.close();} catch(Exception ex){}
			 try{
				 db.setAutoCommit(autocommit);
			 } catch(Exception ex){ex.printStackTrace();}
		 }
	}
	
	
	
	
	
	public void incrementaOrdiniPerSezione(Connection conn, String condition, Integer oidSezione, Integer anno, String stato,Integer userId)
	{
		PreparedStatement pst = null;
		String query = "update dpat_piano_attivita_new set ordinamento = ordinamento+1 ,modified=now(),modified_by=?  where "+condition+" and id_sezione = ? and anno = ? ";
		try
		{
			pst = conn.prepareStatement(query);
			pst.setInt(1, userId);
			pst.setInt(2, oidSezione);
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
	
	public  void disabilitaByOid(Connection conn, int id,int anno, Integer userId) throws Exception {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try
		{
				Boolean congelato = isCongelato(conn, anno);
				
				pst = conn.prepareStatement("update dpat_piano_attivita_new set data_scadenza = CURRENT_TIMESTAMP , stato = ? ,modified=now(),modified_by=? where id = ? returning data_scadenza ");
				int newstato = new DpatWrapperSezioniBean().getStatoDopoModifica(conn, anno);
				pst.setInt(1, newstato);
				pst.setInt(2, userId);
				pst.setInt(3, id);
				pst.execute();
				rs = pst.getResultSet();
				rs.next();
				
			/*disattivo tutti i figli non gia' disattivati*/
			PianoAttivita bean =new  PianoAttivita().buildByOid(conn, id,false,true,  anno);
			if(bean.getIndicatoriFigli() != null && bean.getIndicatoriFigli().size() > 0)
			{
				for(IndicatoreInterface figlio : bean.getIndicatoriFigli())
				{
					if(figlio.getScadenza() == null)
					{
						new Indicatore().disabilitaByOid(conn, figlio.getOid().intValue(),anno,userId);
					}
				}
			}
			
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
	/*dato oid piano attivita ritorna il dpatindicatore figlio con l'ordine massimo */
	public PianoAttivita searchLastChildOf(Connection db, Integer oidSezione,boolean nonscaduti,  String statoFigli,  String statoSezione) throws Exception {
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		String query =  "select * from dpat_piano_attivita_new where id_sezione = ? "+(nonscaduti ? " and data_scadenza is null ": " ")+" order by ordinamento desc";
		try
		{
			pst = db.prepareStatement(query);
			pst.setInt(1, oidSezione);
			
			rs = pst.executeQuery();
			rs.next();
			PianoAttivita toRet = new PianoAttivita().build(db, rs,nonscaduti,true, statoFigli, statoSezione);
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
	
	
	
	
public Integer update(PianoAttivitaInterface<Indicatore> newValue ,PianoAttivitaInterface<Indicatore> oldAttivita, Connection db,Integer statoInput, String stato, String statoIncrementaOrdine, String statoSezione, String statoFigli, boolean congelato, Integer userId) throws Exception {
		
		
		 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		 if(true)
         //if(congelato)
         {
		
        	 PreparedStatement pst = null;
      		Integer oidInserito = null;
     		 
     		
      		try
      		{
      			
      			
      		    int newstato = new DpatWrapperSezioniBean().getStatoDopoModifica(db, newValue.getAnno());
      		    newValue.setStato(newstato);
      			
      			/*faccio inserimento di quello "nuovo" (cioe' la versione modificata) dopo quello vecchio (cioe' quello modificato)*/
      			 oidInserito = insertBeforeOrAfter(db, newValue, oldAttivita, "down", statoInput,statoIncrementaOrdine,userId);
      			
      			/*disabilito quello che sto aggiornando, e questo fara' propagare la data disattivazione su tutti i figli non gia' scaduti */
      			 disabilitaByOid(db, oldAttivita.getOid().intValue(), newValue.getAnno(),userId);
      			 System.out.println(">>>>>>DPAT NUOVA GESTIONE: L'UPDATE HA FATTO SI CHE VENISSE CREATA UNA NUOVA VERSIONE E VENISSE DISABILITATA LA VECCHIA CON OID FISICO "+oldAttivita.getOid().intValue()); 
      			
      			 return oidInserito;
      			
      			 
      		}
      		catch(Exception ex)
      		{
      			ex.printStackTrace();
      			throw ex;
      		}
      		
      		
      		
      		
         }
         else
         {
        	 

    		 
    		 
    		 Integer toRet = null;
    		 PreparedStatement pst = null;
    		 String updateQuery = "update dpat_piano_attivita_new set descrizione = ?, alias_piano = ?, alias_attivita = ?, codice_alias_attivita = ?, codice_esame = ?, stato = ? ,modified=now(),modified_by=? where id = ?";

    		 try
    		 {
    			 int newstato = 1;

    			 pst = db.prepareStatement(updateQuery);
    			 pst.setString(1, newValue.getDescrizione());
    			 pst.setString(2, newValue.getAliasPiano());
    			 pst.setString(3, newValue.getAliasAttivita());
    			 pst.setString(4, newValue.getCodiceAliasAttivita());
    			 pst.setString(5, newValue.getCodiceEsame());
    			 pst.setInt(6, newstato);
    			 pst.setInt(7, userId);
    			 pst.setInt(8, oldAttivita.getOid().intValue() );

    			 pst.executeUpdate();
    			 System.out.println(">>>>>>DPAT NUOVA GESTIONE: UPDATE SECCO PER MODELLO IN STATO 1 DPAT PIANO ATTIVITA "+oldAttivita.getOid().intValue());
    			 toRet = oldAttivita.getOid().intValue(); /*l'oid di quello aggiornato e' proprio quell oche torniamo */
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
    		} 
 		
         }
			 
		
	}




@Override
public int aggiornaCodiceAliasAttivitaPerTutteVersioni(Connection db, PianoAttivitaInterface bean, Integer userId)
		throws Exception {
	
	

	PreparedStatement pst = null;
	try
	{
		String nuovoCodiceAliasAttivita = bean.getCodiceAliasAttivita();
		Integer codRaggruppamento = bean.getCodiceRaggruppamento();
		pst = db.prepareStatement("update dpat_piano_attivita_new set codice_alias_attivita = ? ,modified=now(),modified_by=? where cod_raggruppamento = ? and stato != 1 ");
		pst.setString(1, nuovoCodiceAliasAttivita);
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
	
	
	public   void deleteByOid(Connection conn, int id ) throws Exception {
		PreparedStatement pst = null;
		try
		{
			/*disattivo tutti i figli non gia' disattivati*/
			PianoAttivita bean = new PianoAttivita();
			if(bean.getIndicatoriFigli() != null && bean.getIndicatoriFigli().size() > 0)
			{
				for(Indicatore figlio : bean.getIndicatoriFigli())
				{
					if(figlio.getScadenza() == null)
					{
						new Indicatore().deleteByOid(conn, figlio.getOid().intValue());
					}
				}
			}
			
			pst = conn.prepareStatement("delete from dpat_piano_attivita_new where id = ?");
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
			pst = db.prepareStatement("select distinct stato != 1 from dpat_piano_attivita_new where data_scadenza is null and anno = ?");
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
