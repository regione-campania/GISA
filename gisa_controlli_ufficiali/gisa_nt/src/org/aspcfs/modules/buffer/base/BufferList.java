/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.buffer.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

public class BufferList  extends org.aspcfs.modules.troubletickets.base.TicketList
{
	
	private int stato ;
	private Timestamp dataStato ;
	private Timestamp dataEvento ;
	protected PagedListInfo pagedListInfo = null;
	private ArrayList<Comune> listaComuni = new ArrayList<Comune>();
	
	
	public ArrayList<Comune> getListaComuni() {
		return listaComuni;
	}
	public void setListaComuni(ArrayList<Comune> listaComuni) {
		this.listaComuni = listaComuni;
	}
	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}
	public void setPagedListInfo(PagedListInfo pagedListInfo) {
		this.pagedListInfo = pagedListInfo;
	}
	public int getStato() {
		return stato;
	}
	public void setStato(int stato) {
		this.stato = stato;
	}
	public void setStato(String stato) {
		this.stato = Integer.parseInt(stato);
	}
	public Timestamp getDataStato() {
		return dataStato;
	}
	public void setDataStato(Timestamp dataStato) {
		this.dataStato = dataStato;
	}
	public Timestamp getDataEvento() {
		return dataEvento;
	}
	public void setDataEvento(Timestamp dataEvento) {
		this.dataEvento = dataEvento;
	}
	public void setDataEvento(String dataEvento) {
		this.dataEvento = DatabaseUtils.parseDateToTimestamp(dataEvento, Locale.ITALIAN);;
	}
	

	
	public void createFilter(StringBuffer sqlFilter, Connection db) {
		
		
	    if (stato > 0) {
		      sqlFilter.append("AND stato = ? ");
		}
	    if (dataStato != null) {
		      sqlFilter.append("AND dataStato = ? ");
		} 
	    
	    if (dataEvento != null) {
		      sqlFilter.append("AND dataEvento = ? ");
		} 
	    
	    if (listaComuni!= null && listaComuni.size()>0)
		{
	    	String filtro_comuni = "" ;
	    	for (Comune c : listaComuni)
	    	{
	    		filtro_comuni += c.getId() + "," ;
	    	}
			 sqlFilter.append("AND b.id in (select id_buffer from buffer_comuni_coinvolti where id_comune in ("+filtro_comuni.substring(0, filtro_comuni.length()-1)+")) ");
		}
	}
	

	public int prepareFilter(PreparedStatement pst) throws SQLException {
	    int i = 0;
	    if (stato > 0) {
		      pst.setInt(++i, stato);
		}
	    if (dataStato != null) {
	    	pst.setTimestamp(++i, dataStato);		
	    	} 
	    
	    if (dataEvento != null) {
	    	pst.setTimestamp(++i, dataEvento);		
		} 
	    return i ;
	}
	
	 public void buildList(Connection db) throws SQLException {
		    PreparedStatement pst = null;
		    ResultSet rs = null;
		    int items = -1;
		    StringBuffer sqlSelect = new StringBuffer();
		    StringBuffer sqlCount = new StringBuffer();
		    StringBuffer sqlFilter = new StringBuffer();
		    StringBuffer sqlOrder = new StringBuffer();
		    //Need to build a base SQL statement for counting records
		    sqlCount.append(
		        "SELECT COUNT(*) AS recordcount " +
		        "FROM buffer b where trashed_Date is null ");
		    createFilter(sqlFilter, db);
		    if (pagedListInfo != null) {
		     
		      pst = db.prepareStatement( sqlCount.toString() + sqlFilter.toString());
		      items = prepareFilter(pst);
		      rs = pst.executeQuery();
		      if (rs.next()) {
		        int maxRecords = rs.getInt("recordcount");
		        pagedListInfo.setMaxRecords(maxRecords);
		      }
		      rs.close();
		      pst.close();
		      // Declare default sort, if unset
		      //pagedListInfo.setDefaultSort("codice_buffer", "desc");
		      pagedListInfo.setColumnToSortBy("codice_buffer");
		      //Determine the offset, based on the filter, for the first record to show
		      
		      //Determine the offset
		      pagedListInfo.appendSqlTail(db, sqlOrder);
		    } else {
		      sqlOrder.append("ORDER BY codice_buffer desc ");
		    }

		    //Need to build a base SQL statement for returning records
		    if (pagedListInfo != null) {
		      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		    } else {
		      sqlSelect.append("SELECT ");
		    }
		    sqlSelect.append(
		        "b.*,lbs.description as stato_desc " +
		        "from buffer b join lookup_buffer_stato lbs on lbs.code = b.stato " +
		        "WHERE b.trashed_date is null ");

		    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		    items = prepareFilter(pst);
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
		      Buffer thisbuffer = new Buffer(rs);
		      thisbuffer.queryRecordComuni(db);
		      this.add(thisbuffer);
		    }
		    rs.close();
		    pst.close();
		  
		  }

	
	

}
