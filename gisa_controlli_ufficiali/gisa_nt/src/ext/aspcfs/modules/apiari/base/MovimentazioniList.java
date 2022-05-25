/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package ext.aspcfs.modules.apiari.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import org.aspcfs.modules.abusivismi.base.Organization;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import com.sun.accessibility.internal.resources.accessibility;

import ext.aspcfs.modules.apicolture.actions.Movimentazioni;

public class MovimentazioniList extends  Vector implements SyncableList {
	
	
	  protected PagedListInfo pagedListInfo = null;
	  
	 private String codiceAziendaOrigine ;
	 private String codiceAziendaDestinazione ;
	 private String denominazioneAziendaOrigine ;
	 private String progressivoApiarioOrigine;
	 private String cfProprietarioOrigine;
	  private int stato ;
	  private int accettazioneDestinatario ;
	  private ArrayList<Integer> tipoMovimentazione  ;
	  private int idAsl ;
	 private int idMovimentazione;
	  
	  
	  private String codiceAziendaSearch ;
	  private int progressivoApiarioSearch;
	  
	  
	   
	  
	  
	   public String getCodiceAziendaSearch() {
		return codiceAziendaSearch;
	}





	public void setCodiceAziendaSearch(String codiceAziendaSearch) {
		this.codiceAziendaSearch = codiceAziendaSearch;
	}





	public int getProgressivoApiarioSearch() {
		return progressivoApiarioSearch;
	}



	public void setProgressivoApiarioSearch(String progressivoApiarioSearch) {
		if(progressivoApiarioSearch!=null && !"".equals(progressivoApiarioSearch))
			this.progressivoApiarioSearch = Integer.parseInt(progressivoApiarioSearch);
	}
	

	public void setProgressivoApiarioSearch(int progressivoApiarioSearch) {
		this.progressivoApiarioSearch = progressivoApiarioSearch;
	}





	public int getIdAsl() {
		return idAsl;
	}





	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}
	
	public int getIdMovimentazione() {
		return idMovimentazione;
	}





	public void setIdMovimentazione(int idMovimentazione) {
		this.idMovimentazione = idMovimentazione;
	}





	public void setPagedListInfo(PagedListInfo tmp) {
	     this.pagedListInfo = tmp;
	   }
	
	   
	 

	
	 public int getStato() {
		return stato;
	}





	public void setStato(int stato) {
		this.stato = stato;
	}
	
	 public ArrayList<Integer> getTipoMovimentazione() {
			return tipoMovimentazione;
		}





		public void setTipoMovimentazione(ArrayList<Integer> tipoMovimentazione) {
			this.tipoMovimentazione = tipoMovimentazione;
		}
		

		 public Integer getAccettazioneDestinatario() {
				return accettazioneDestinatario;
			}





			public void setAccettazioneDestinatario(Integer accettazioneDestinatario) {
				this.accettazioneDestinatario = accettazioneDestinatario;
			}





	public String getCodiceAziendaOrigine() {
		return codiceAziendaOrigine;
	}
	
	public String getCodiceAziendaDestinazione() {
		return codiceAziendaDestinazione;
	}




	public void setCodiceAziendaOrigine(String codiceAziendaOrigine) {
		this.codiceAziendaOrigine = codiceAziendaOrigine;
	}
	
	public void setCodiceAziendaDestinazione(String codiceAziendaDestinazione) {
		this.codiceAziendaDestinazione = codiceAziendaDestinazione;
	}





	public String getDenominazioneAziendaOrigine() {
		return denominazioneAziendaOrigine;
	}





	public void setDenominazioneAziendaOrigine(String denominazioneAziendaOrigine) {
		this.denominazioneAziendaOrigine = denominazioneAziendaOrigine;
	}





	public String getProgressivoApiarioOrigine() {
		return progressivoApiarioOrigine;
	}





	public void setProgressivoApiarioOrigine(String progressivoApiarioOrigine) {
		this.progressivoApiarioOrigine = progressivoApiarioOrigine;
	}





	public String getCfProprietarioOrigine() {
		return cfProprietarioOrigine;
	}





	public void setCfProprietarioOrigine(String cfProprietarioOrigine) {
		this.cfProprietarioOrigine = cfProprietarioOrigine;
	}





	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}





	public void buildList(Connection db) throws SQLException {
		    PreparedStatement pst = null;
		    
		    ResultSet rs = queryList(db, pst);
		    while (rs.next()) {
		      ModelloC thisMov = new ModelloC();
		      
		      thisMov.buildRecord(rs);
		      thisMov.setApiarioOrigine(new Stabilimento(db, thisMov.getIdStabilimentoOrigine(),false));
//		      if (thisMov.getIdStabilimentoOrigine()>0)
//		      {
//		    	  Stabilimento apiarioOrigine = new Stabilimento(db, thisMov.getIdStabilimentoOrigine());
//		    	  thisMov.setApiarioOrigine(apiarioOrigine);
//		      }
//		      
//		      if (thisMov.getIdStabilimentoDestinazione()>0)
//		      {
//		    	  Stabilimento apiarioDestinazione = new Stabilimento(db, thisMov.getIdStabilimentoDestinazione());
//		    	  thisMov.setApiarioDestinazione(apiarioDestinazione);
//		      }
//		      
		      
		       this.add(thisMov);
		      
		    }
		    rs.close();
		    if (pst != null) {
		      pst.close();
		    }
		   
		  }
	
	public void buildListStorico(Connection db) throws SQLException {
	    PreparedStatement pst = null;
	    
	    ResultSet rs = queryListStorico(db, pst);
	    while (rs.next()) {
	      ModelloC thisMov = new ModelloC();
	      
	      thisMov.buildRecord(rs);
	      thisMov.setApiarioOrigine(new Stabilimento(db, thisMov.getIdStabilimentoOrigine(),false));
//	      if (thisMov.getIdStabilimentoOrigine()>0)
//	      {
//	    	  Stabilimento apiarioOrigine = new Stabilimento(db, thisMov.getIdStabilimentoOrigine());
//	    	  thisMov.setApiarioOrigine(apiarioOrigine);
//	      }
//	      
//	      if (thisMov.getIdStabilimentoDestinazione()>0)
//	      {
//	    	  Stabilimento apiarioDestinazione = new Stabilimento(db, thisMov.getIdStabilimentoDestinazione());
//	    	  thisMov.setApiarioDestinazione(apiarioDestinazione);
//	      }
//	      
	      
	       this.add(thisMov);
	      
	    }
	    rs.close();
	    if (pst != null) {
	      pst.close();
	    }
	   
	  }


	
	public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
	    ResultSet rs = null;
	    int items = -1;

	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();

	    //Need to build a base SQL statement for counting records
	    sqlCount.append(
	        "SELECT COUNT(*) AS recordcount " +
	        "FROM apicoltura_movimentazioni mov "+
"left join apicoltura_apiari   apiari on apiari.id = mov.id_stabilimento_apiario_origine " +
"left join apicoltura_apiari   apiari_dest on apiari_dest.id = mov.id_stabilimento_apiario_destinazione " +
"left join apicoltura_imprese imp on imp.id = apiari.id_operatore " +
"WHERE mov.trashed_date is null  ");

	    createFilter(db, sqlFilter);

	    if (pagedListInfo != null) {
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(
	          sqlCount.toString() +
	          sqlFilter.toString());
	      items = prepareFilter(pst);
	      
	      System.out.println("QUERYYYYY: " + pst.toString());
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        int maxRecords = rs.getInt("recordcount");
	        pagedListInfo.setMaxRecords(maxRecords);
	      }
	      rs.close();
	      pst.close();

	  
	      sqlOrder.append("ORDER BY mov.sincronizzato_bdn, mov.data_movimentazione desc ");

	      //Determine column to sort by
	      pagedListInfo.appendSqlTail(db, sqlOrder);

	      //Optimize SQL Server Paging
	      //sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
	    } else {
	      sqlOrder.append("ORDER BY mov.sincronizzato_bdn, mov.data_movimentazione desc ");
	    }

	    //Need to build a base SQL statement for returning records
	    if (pagedListInfo != null) {
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT ");
	    }
	    sqlSelect.append(" mov.*    FROM apicoltura_movimentazioni mov "+
"left join apicoltura_apiari   apiari 	  on apiari.id = mov.id_stabilimento_apiario_origine " +
"left join apicoltura_apiari   apiari_dest on apiari_dest.id = mov.id_stabilimento_apiario_destinazione " +
"left join apicoltura_imprese imp on imp.id = apiari.id_operatore " +
"WHERE mov.trashed_date is null  ");
	       
	    
	    pst = db.prepareStatement(
	        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	    items = prepareFilter(pst);
	    
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
	    rs = pst.executeQuery();
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	    }
	    return rs;
	  }
	
	
	
	public ResultSet queryListStorico(Connection db, PreparedStatement pst) throws SQLException {
	    ResultSet rs = null;
	    int items = -1;

	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();

	    //Need to build a base SQL statement for counting records
	    sqlCount.append(
	        "SELECT COUNT(*) AS recordcount " +
	        "FROM apicoltura_movimentazioni_storico mov "+
"left join apicoltura_apiari   apiari on apiari.id = mov.id_stabilimento_apiario_origine " +
"left join apicoltura_apiari   apiari_dest on apiari_dest.id = mov.id_stabilimento_apiario_destinazione " +
"left join apicoltura_imprese imp on imp.id = apiari.id_operatore " +
"WHERE mov.trashed_date is null  ");

	    createFilterStorico(db, sqlFilter);

	    if (pagedListInfo != null) {
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(
	          sqlCount.toString() +
	          sqlFilter.toString());
	      items = prepareFilter(pst);
	      
	      System.out.println("QUERYYYYY: " + pst.toString());
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        int maxRecords = rs.getInt("recordcount");
	        pagedListInfo.setMaxRecords(maxRecords);
	      }
	      rs.close();
	      pst.close();

	  

	      //Determine column to sort by
	      pagedListInfo.setDefaultSort("mov.data_movimentazione", null);
	      pagedListInfo.appendSqlTail(db, sqlOrder);

	      //Optimize SQL Server Paging
	      //sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
	    } else {
	      sqlOrder.append("ORDER BY mov.data_movimentazione ");
	    }

	    //Need to build a base SQL statement for returning records
	    if (pagedListInfo != null) {
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT ");
	    }
	    sqlSelect.append(" mov.*    FROM apicoltura_movimentazioni_storico mov "+
"left join apicoltura_apiari   apiari 	  on apiari.id = mov.id_stabilimento_apiario_origine " +
"left join apicoltura_apiari   apiari_dest on apiari_dest.id = mov.id_stabilimento_apiario_destinazione " +
"left join apicoltura_imprese imp on imp.id = apiari.id_operatore " +
"WHERE mov.trashed_date is null  ");
	       
	    pst = db.prepareStatement(
	        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	    items = prepareFilter(pst);
	    
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
	    rs = pst.executeQuery();
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	    }
	    return rs;
	  }


	  /**
	   *  Builds a base SQL where statement for filtering records to be used by
	   *  sqlSelect and sqlCount
	   *
	   * @param  sqlFilter  Description of Parameter
	   * @param  db         Description of the Parameter
	   * @since             1.2
	   */
	  protected void createFilter(Connection db, StringBuffer sqlFilter) {
	    if (sqlFilter == null) {
	      sqlFilter = new StringBuffer();
	    }
	    
	    
	    if( (codiceAziendaSearch!=null && !"".equalsIgnoreCase(codiceAziendaSearch)) && progressivoApiarioSearch>0)
	    {
	    	sqlFilter.append(" and  ((mov.codice_azienda_origine ilike ? and mov.id_tipo_movimentazione = 4 ) or (mov.codice_azienda_origine ilike ? and progressivo_apiario_origine =? ) or (mov.codice_azienda_destinazione ilike ? and mov.id_tipo_movimentazione = 4  ) or (mov.codice_azienda_destinazione ilike ? and progressivo_apiario_destinazione =?  ) )");
	    }
	    if (idAsl>0)
	    {
	    	sqlFilter.append(" and (apiari.id_Asl ="+idAsl + " or apiari_dest.id_Asl = " + idAsl + ")");
	    }
	    
	    if (stato>0)
	    {
	    	sqlFilter.append(" and mov.stato ="+stato);
	    }
	    if (accettazioneDestinatario>0)
	    {
	    	sqlFilter.append(" and mov.accettazione_destinatario ="+accettazioneDestinatario);
	    }
	    if(tipoMovimentazione!=null)
	    {
	    	sqlFilter.append(" and mov.id_tipo_movimentazione in ( " );

			Iterator<Integer> tipoMovimentazioneToInclude = tipoMovimentazione.iterator();
			boolean primo = true;
			while(tipoMovimentazioneToInclude.hasNext())
			{
				sqlFilter.append(((primo)?(""):(" , "))  + tipoMovimentazioneToInclude.next() ); 
				primo = false;
			}
			
			sqlFilter.append(" ) " );
		
	    }
	    if (codiceAziendaOrigine!=null && !"".equalsIgnoreCase(codiceAziendaOrigine) && codiceAziendaDestinazione!=null && !"".equalsIgnoreCase(codiceAziendaDestinazione))
	    {
	    	sqlFilter.append(" and (codice_azienda_origine ilike ? or  codice_azienda_destinazione ilike ?)");
	    }
	    else
	    {
	    	if (codiceAziendaOrigine!=null && !"".equalsIgnoreCase(codiceAziendaOrigine))
	    		sqlFilter.append(" and codice_azienda_origine ilike ? ");
	    
	    	if (codiceAziendaDestinazione!=null && !"".equalsIgnoreCase(codiceAziendaDestinazione))
	    		sqlFilter.append(" and codice_azienda_destinazione ilike ? ");
	    }
	    
	    if (progressivoApiarioOrigine!=null && !"".equalsIgnoreCase(progressivoApiarioOrigine))
	    	sqlFilter.append(" and progressivo_apiario_origine ilike ? ");
	    
	    if (denominazioneAziendaOrigine!=null && !"".equalsIgnoreCase(denominazioneAziendaOrigine))
	    	sqlFilter.append(" and imp.ragione_sociale ilike ? ");
	    
	    
	    if (cfProprietarioOrigine!=null && !"".equalsIgnoreCase(cfProprietarioOrigine))
	    	sqlFilter.append(" and imp.codice_fiscale_impresa ilike ? ");
	  
	  }
	  
	  
	  protected void createFilterStorico(Connection db, StringBuffer sqlFilter) {
		    if (sqlFilter == null) {
		      sqlFilter = new StringBuffer();
		    }
		    
		    
		    if (idMovimentazione>0)
		    {
		    	sqlFilter.append(" and (mov.id_movimentazione ="+idMovimentazione + " or mov.id_movimentazione = " + idMovimentazione + ")");
		    }
		    if( (codiceAziendaSearch!=null && !"".equalsIgnoreCase(codiceAziendaSearch)) && progressivoApiarioSearch>0)
		    {
		    	sqlFilter.append(" and  ((mov.codice_azienda_origine ilike ? and progressivo_apiario_origine =? ) or (mov.codice_azienda_destinazione ilike ? and progressivo_apiario_destinazione =?  ) )");
		    }
		    if (idAsl>0)
		    {
		    	sqlFilter.append(" and (apiari.id_Asl ="+idAsl + " or apiari_dest.id_Asl = " + idAsl + ")");
		    }
		    
		    if (stato>0)
		    {
		    	sqlFilter.append(" and mov.stato ="+stato);
		    }
		    if (accettazioneDestinatario>0)
		    {
		    	sqlFilter.append(" and mov.accettazione_destinatario ="+accettazioneDestinatario);
		    }
		    if(tipoMovimentazione!=null)
		    {
		    	sqlFilter.append(" and mov.id_tipo_movimentazione in ( " );

				Iterator<Integer> tipoMovimentazioneToInclude = tipoMovimentazione.iterator();
				boolean primo = true;
				while(tipoMovimentazioneToInclude.hasNext())
				{
					sqlFilter.append((primo)?(""):(" , ")  + tipoMovimentazioneToInclude.next() ); 
					primo = false;
				}
				
				sqlFilter.append(" ) " );
			
		    }
		    if (codiceAziendaOrigine!=null && !"".equalsIgnoreCase(codiceAziendaOrigine))
		    	sqlFilter.append(" and codice_azienda_origine ilike ? ");
		    
		    if (codiceAziendaDestinazione!=null && !"".equalsIgnoreCase(codiceAziendaDestinazione))
		    	sqlFilter.append(" and codice_azienda_destinazione ilike ? ");
		    
		    if (progressivoApiarioOrigine!=null && !"".equalsIgnoreCase(progressivoApiarioOrigine))
		    	sqlFilter.append(" and progressivo_apiario_origine ilike ? ");
		    
		    if (denominazioneAziendaOrigine!=null && !"".equalsIgnoreCase(denominazioneAziendaOrigine))
		    	sqlFilter.append(" and imp.ragione_sociale ilike ? ");
		    
		    
		    if (cfProprietarioOrigine!=null && !"".equalsIgnoreCase(cfProprietarioOrigine))
		    	sqlFilter.append(" and imp.codice_fiscale_impresa ilike ? ");
		  
		  }
	  
	  protected int prepareFilter(PreparedStatement pst) throws SQLException {
		    int i = 0;
		    
		    
		    if( (codiceAziendaSearch!=null && !"".equalsIgnoreCase(codiceAziendaSearch)) && progressivoApiarioSearch>0)
		    {
		    	
		    	pst.setString(++i, codiceAziendaSearch);
		    	pst.setString(++i, codiceAziendaSearch);
		    	pst.setString(++i, progressivoApiarioSearch+"");
		    	
		    	pst.setString(++i, codiceAziendaSearch);
		    	pst.setString(++i, codiceAziendaSearch);
		    	pst.setString(++i, progressivoApiarioSearch+"");

		    	
		    }
		    
		    if (codiceAziendaOrigine!=null && !"".equalsIgnoreCase(codiceAziendaOrigine))
		    	pst.setString(++i, codiceAziendaOrigine);
		    
		    if (codiceAziendaDestinazione!=null && !"".equalsIgnoreCase(codiceAziendaDestinazione))
		    	pst.setString(++i, codiceAziendaDestinazione);
		    
		    if (progressivoApiarioOrigine!=null && !"".equalsIgnoreCase(progressivoApiarioOrigine))
		    	pst.setString(++i, progressivoApiarioOrigine);
		    
		    if (denominazioneAziendaOrigine!=null && !"".equalsIgnoreCase(denominazioneAziendaOrigine))
		    	pst.setString(++i, denominazioneAziendaOrigine);
		    
		    
		    if (cfProprietarioOrigine!=null && !"".equalsIgnoreCase(cfProprietarioOrigine))
		    	pst.setString(++i, cfProprietarioOrigine);
		    
		    
		    return i;
	  }


	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getUniqueField() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setLastAnchor(Timestamp arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setLastAnchor(String arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setNextAnchor(Timestamp arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setNextAnchor(String arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setSyncType(int arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setSyncType(String arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
