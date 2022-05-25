/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.allevamenti.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.aspcfs.utils.web.PagedListInfo;

public class CapoAllevamento  extends org.aspcfs.modules.troubletickets.base.TicketList {
	
	private String matricola 		;
	private String specie			;
	private String razza			;
	private Timestamp dataNascita	;
	private String sesso			;
	private String codice_azienda			;
	private String id_allevamento			;
	
	
	public String getCodice_azienda() {
		return codice_azienda;
	}
	public void setCodice_azienda(String codice_azienda) {
		this.codice_azienda = codice_azienda;
	}
	public String getId_allevamento() {
		return id_allevamento;
	}
	public void setId_allevamento(String id_allevamento) {
		this.id_allevamento = id_allevamento;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getMatricola() {
		return matricola;
	}
	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}
	public String getSpecie() {
		return specie;
	}
	public void setSpecie(String specie) {
		this.specie = specie;
	}
	public String getRazza() {
		return razza;
	}
	public void setRazza(String razza) {
		this.razza = razza;
	}
	public Timestamp getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Timestamp dataNascita) {
		this.dataNascita = dataNascita;
	}
	
	public CapoAllevamento() throws SQLException
	{
		
	}
	
	public CapoAllevamento(ResultSet rs) throws SQLException
	{

		String sesso_s = "";
		String matricola = rs.getString("matricola");
		String razza = rs.getString("razza_capo");
		String sepcie = rs.getString("specie_capo");
		Timestamp dataNascita = rs.getTimestamp("data_nascita");
		boolean sesso = rs.getBoolean("maschio");
		if (sesso == true)
		{
			sesso_s = "MASCHIO";
		}
		else
		{
			sesso_s = "FEMMINA";
		}

		this.setMatricola(matricola)		;
		this.setRazza(razza)				;
		this.setSpecie(sepcie)				;
		this.setDataNascita(dataNascita)	;
		this.setSesso(sesso_s)				;
	}
	
	public void buildList(Connection db,String richiesto_report) throws SQLException  {
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    int items = -1;
	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	    //Need to build a base SQL statement for counting records
	    sqlCount.append("select count(matricola) as recordcount" +
  				" from anagrafica_capi_allevamenti "+
  				" where codice_azienda = ? and id_allevamento = ?  ");
	    try
	    {
	     if (pagedListInfo != null) {
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(
	          sqlCount.toString() );
	      pst.setString(1,codice_azienda);
		  pst.setInt(2, Integer.parseInt(id_allevamento));
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        int maxRecords = rs.getInt("recordcount");
	        pagedListInfo.setMaxRecords(maxRecords);
	      }
	      rs.close();
	      pst.close();
	      // Declare default sort, if unset
	      pagedListInfo.setDefaultSort("t.entered", null);
	      //Determine the offset, based on the filter, for the first record to show
	      if (pagedListInfo.getMode() == PagedListInfo.DETAILS_VIEW && id > 0) {
	        String direction = null;
	        if ("desc".equalsIgnoreCase(pagedListInfo.getSortOrder())) {
	          direction = ">";
	        } else {
	          direction = "<";
	        }
	       
	        pst = db.prepareStatement( sqlCount.toString()+sqlOrder.toString() );
	     
	        pst.setInt(++items, id);
	        rs = pst.executeQuery();
	        if (rs.next()) {
	          int offsetCount = rs.getInt("recordcount");
	          pagedListInfo.setCurrentOffset(offsetCount);
	        }
	        rs.close();
	        pst.close();
	      }
	      //Determine the offset
	      pagedListInfo.appendSqlTail(db, sqlOrder);
	    } 

	    //Need to build a base SQL statement for returning records
	    if (pagedListInfo != null) {
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT ");
	    }
	    sqlSelect.append("anagrafica_capi_allevamenti.matricola,anagrafica_capi_allevamenti.maschio,anagrafica_capi_allevamenti.codice_azienda,anagrafica_capi_allevamenti.id_allevamento,anagrafica_capi_allevamenti.data_nascita,anagrafica_capi_allevamenti.razza,anagrafica_capi_allevamenti.specie , m_lookup_razze.description as razza_capo, specie.description as specie_capo " +
  				" from anagrafica_capi_allevamenti left join m_lookup_razze on (anagrafica_capi_allevamenti.razza = m_lookup_razze.text_code ) " +
  				" left join lookup_specie_allevata specie on (anagrafica_capi_allevamenti.specie =  specie.short_description ) " +
  				" where "+
  				"  codice_azienda = ? and id_allevamento = ?  ");
	        
	    	if (richiesto_report.equals("SI"))
	    	{
	    		sqlOrder = new StringBuffer(sqlOrder.toString().replace("LIMIT " + pagedListInfo.getItemsPerPage()+" OFFSET "+pagedListInfo.getCurrentOffset(),""));
	    	}
	    pst = db.prepareStatement( sqlSelect.toString()+sqlOrder.toString());
	  pst.setString(1,codice_azienda);
	  pst.setInt(2, Integer.parseInt(id_allevamento));
	  
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
			if (System.getProperty("DEBUG") != null) {
			}
	    rs = pst.executeQuery();
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	    }
	    while (rs.next()) {
	      CapoAllevamento thisTicket = new CapoAllevamento(rs);
	      	
	     
	      this.add(thisTicket);
	    }
	    rs.close();
	    pst.close();
	    }
		catch(SQLException e)
		{
			throw e ;
		}
	    }
	
	/*public void buildList(Connection db,String richiesto_report) throws SQLException  {
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    int items = -1;
	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	    //Need to build a base SQL statement for counting records
	    sqlCount.append("select count(m_capi_bdn.matricola) as recordcount" +
  				" from m_capi_bdn "+
  				" where matricola in (select matricola_in from ingressi_uscite_capi_view" +
  				" where data_uscita is null and datadecesso is null and datafurto is null "+
  				" and azienda_in = ? and id_allevamento_in = ? ) ");
	    try
	    {
	     if (pagedListInfo != null) {
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(
	          sqlCount.toString() );
	      pst.setString(1,codice_azienda);
		  pst.setString(2, id_allevamento);
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        int maxRecords = rs.getInt("recordcount");
	        pagedListInfo.setMaxRecords(maxRecords);
	      }
	      rs.close();
	      pst.close();
	      // Declare default sort, if unset
	      pagedListInfo.setDefaultSort("t.entered", null);
	      //Determine the offset, based on the filter, for the first record to show
	      if (pagedListInfo.getMode() == PagedListInfo.DETAILS_VIEW && id > 0) {
	        String direction = null;
	        if ("desc".equalsIgnoreCase(pagedListInfo.getSortOrder())) {
	          direction = ">";
	        } else {
	          direction = "<";
	        }
	       
	        pst = db.prepareStatement( sqlCount.toString()+sqlOrder.toString() );
	     
	        pst.setInt(++items, id);
	        rs = pst.executeQuery();
	        if (rs.next()) {
	          int offsetCount = rs.getInt("recordcount");
	          pagedListInfo.setCurrentOffset(offsetCount);
	        }
	        rs.close();
	        pst.close();
	      }
	      //Determine the offset
	      pagedListInfo.appendSqlTail(db, sqlOrder);
	    } 

	    //Need to build a base SQL statement for returning records
	    if (pagedListInfo != null) {
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT ");
	    }
	    sqlSelect.append("m_capi_bdn.* , m_lookup_razze.description as razza_capo, specie.description as specie_capo " +
  				" from m_capi_bdn left join m_lookup_razze on (m_capi_bdn.razza = m_lookup_razze.text_code ) " +
  				" left join lookup_specie_allevata specie on (m_capi_bdn.specie =  specie.short_description ) " +
  				" where matricola in (select matricola_in from ingressi_uscite_capi_view" +
  				" where data_uscita is null and datadecesso is null and datafurto is null "+
  				" and azienda_in = ? and id_allevamento_in = ? ) ");
	        
	    	if (richiesto_report.equals("SI"))
	    	{
	    		sqlOrder = new StringBuffer(sqlOrder.toString().replace("LIMIT " + pagedListInfo.getItemsPerPage()+" OFFSET "+pagedListInfo.getCurrentOffset(),""));
	    	}
	    pst = db.prepareStatement( sqlSelect.toString()+sqlOrder.toString());
	  pst.setString(1,codice_azienda);
	  pst.setString(2, id_allevamento);
	  
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
			if (System.getProperty("DEBUG") != null) {
			}
	    rs = pst.executeQuery();
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	    }
	    while (rs.next()) {
	      CapoAllevamento thisTicket = new CapoAllevamento(rs);
	      	
	     
	      this.add(thisTicket);
	    }
	    rs.close();
	    pst.close();
	    }
		catch(SQLException e)
		{
			
			throw e ;
		}
	    }*/
	 
	
}
