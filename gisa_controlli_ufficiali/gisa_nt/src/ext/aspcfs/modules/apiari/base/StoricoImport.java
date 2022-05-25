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
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import org.aspcfs.modules.admin.base.User;
import org.aspcfs.utils.web.PagedListInfo;

public class StoricoImport extends Vector 
{
	
	private int id;
	private int idUtente;
	private String nomeFile;
	private String nomeFileCompleto;
	private String codDocumento;
	private Timestamp dataImport;
	private Integer idOperatore;
	private String erroreInsert;
	private String erroreParsingFile;
	private Boolean esito;
	private Operatore operatore;
	private User utente;
	protected PagedListInfo pagedListInfo = null;
	
	
    public Integer getIdOperatore() 
    {
	   return idOperatore;
	}

	public void setIdOperatore(Integer idOperatore) 
	{
		this.idOperatore = idOperatore;
	}
	
	public Operatore getOperatore() 
    {
	   return operatore;
	}

	public void setOperatore(Operatore operatore) 
	{
		this.operatore = operatore;
	}
	
	public User getUtente() 
    {
	   return utente;
	}

	public void setUser(User utente) 
	{
		this.utente = utente;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getNomeFileCompleto() {
		return nomeFileCompleto;
	}
	public void setNomeFileCompleto(String nomeFileCompleto) {
		this.nomeFileCompleto = nomeFileCompleto;
	}
	public String getCodDocumento() {
		return codDocumento;
	}
	public void setCodDocumento(String codDocumento) {
		this.codDocumento = codDocumento;
	}
	public Timestamp getDataImport() {
		return dataImport;
	}
	public void setDataImport(Timestamp dataImport) {
		this.dataImport = dataImport;
	}
	
	public String getErroreInsert() {
		return erroreInsert;
	}
	public void setErroreInsert(String erroreInsert) {
		this.erroreInsert = erroreInsert;
	}
	public String getErroreParsingFile() {
		return erroreParsingFile;
	}
	public void setErroreParsingFile(String erroreParsingFile) {
		this.erroreParsingFile = erroreParsingFile;
	}
	
	public void setPagedListInfo(PagedListInfo tmp) {
	     this.pagedListInfo = tmp;
	   }
	
	public boolean getEsito() 
    {
	   return esito;
	}

	public void setEsito(boolean esito) 
	{
		this.esito = esito;
	}
	
	
	
	public void buildList(Connection db) throws SQLException 
	{
	    PreparedStatement pst = null;
	    
	    ResultSet rs = queryList(db, pst);
	    while (rs.next()) 
	    {
	      StoricoImport storico = new StoricoImport();
	      
	      storico.buildRecord(rs);
	      storico.setOperatore(new Operatore(db, storico.getIdOperatore()));
	      
	      this.add(storico);
	      
	    }
	    rs.close();
	    if (pst != null) 
	    {
	      pst.close();
	    }
	   
	  }
	
	public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException 
	{
	    ResultSet rs = null;
	    int items = -1;

	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();

	    //Need to build a base SQL statement for counting records
	    
	    sqlCount.append(
	        "SELECT COUNT(*) AS recordcount " +
	        "FROM apicoltura_movimentazioni_api_regine_log_import sto ");

	    createFilter(db, sqlFilter);

	    if (pagedListInfo != null) 
	    {
	      //Get the total number of records matching filter
	      pst = db.prepareStatement( sqlCount.toString() + sqlFilter.toString());
	      items = prepareFilter(pst);
	      
	      System.out.println("QUERYYYYY: " + pst.toString());
	      rs = pst.executeQuery();
	      if (rs.next()) 
	      {
	        int maxRecords = rs.getInt("recordcount");
	        pagedListInfo.setMaxRecords(maxRecords);
	      }
	      rs.close();
	      pst.close();

	      //Determine column to sort by
	      pagedListInfo.setDefaultSort("sto.data_import", null);
	      pagedListInfo.appendSqlTail(db, sqlOrder);

	      //Optimize SQL Server Paging
	      //sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
	    } 
	    else 
	    {
	      sqlOrder.append(" order by sto.data_import desc ");
	    }

	    //Need to build a base SQL statement for returning records
	    if (pagedListInfo != null) 
	    {
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } 
	    else 
	    {
	      sqlSelect.append("SELECT ");
	    }
	    sqlSelect.append(" sto.*    FROM apicoltura_movimentazioni_api_regine_log_import sto " );
	       
	    pst = db.prepareStatement( sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	    items = prepareFilter(pst);
	    
	    if (pagedListInfo != null) 
	    {
	      pagedListInfo.doManualOffset(db, pst);
	    }
	    rs = pst.executeQuery();
	    if (pagedListInfo != null) 
	    {
	      pagedListInfo.doManualOffset(db, rs);
	    }
	    return rs;
	  }
	
	
	 protected int prepareFilter(PreparedStatement pst) throws SQLException 
	 {
	    int i = 0;
	    
	    if(idOperatore!=null && idOperatore>0)
	    {
	    	pst.setInt(++i, idOperatore);
	    }
	    
	    if( id>0)
	    {
	    	pst.setInt(++i, id);
	    }
	    
	    return i;
	  }
	 
	 
	 protected void createFilter(Connection db, StringBuffer sqlFilter) 
	 {
	    if (sqlFilter == null) 
	    {
	      sqlFilter = new StringBuffer();
	    }
	    
	    sqlFilter.append(" where true ");
	    
	    if( idOperatore!=null && idOperatore>0)
	    {
	    	sqlFilter.append(" and  id_operatore = ?  ");
	    }
	    
	    if( id>0)
	    {
	    	sqlFilter.append(" and  id = ?  ");
	    }
	 }
	 
	 public void buildRecord(ResultSet rs) throws SQLException
	 {
	 	this.setId(rs.getInt("id"));
		this.setIdUtente(rs.getInt("id_utente"));
		this.setDataImport(rs.getTimestamp("data_import"));
		this.setNomeFile(rs.getString("nome_file"));
		this.setNomeFileCompleto(rs.getString("nome_file_completo"));
		this.setIdOperatore(rs.getInt("id_operatore"));
		this.setErroreInsert(rs.getString("errore_insert"));
		this.setErroreParsingFile(rs.getString("errore_parsing_file"));
		this.setCodDocumento(rs.getString("cod_documento"));
		this.setEsito(rs.getBoolean("esito"));
	 }
	 
	 
	
}
