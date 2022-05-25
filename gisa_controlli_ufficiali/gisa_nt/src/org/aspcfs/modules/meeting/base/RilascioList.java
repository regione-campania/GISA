/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.meeting.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.web.PagedListInfo;

public class RilascioList extends Vector implements SyncableList {



	protected PagedListInfo pagedListInfo = null;


	private String oggetto ;
	private String altro ;
	private int noteIdContesto;
	private Timestamp data ;
	private Timestamp dataFrom ;
	private Timestamp dataTo ;
	

	

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getAltro() {
		return altro;
	}

	public void setAltro(String altro) {
		this.altro = altro;
	}

	public Timestamp getDataFrom() {
		return dataFrom;
	}

	public void setDataFrom(Timestamp dataFrom) {
		this.dataFrom = dataFrom;
	}
	
	
	public void setDataFrom(String dataFrom) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (dataFrom!=null && !"".equals(dataFrom))
		{
			this.dataFrom = new Timestamp(sdf.parse(dataFrom).getTime());
		}
		
	}
	public Timestamp getDataTo() {
		return dataTo;
	}

	public void setDataTo(Timestamp dataTo) {
		this.dataTo = dataTo;
	}
	
	public void setDataTo(String dataTo) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (dataTo!=null && !"".equals(dataTo))
		{
			this.dataTo = new Timestamp(sdf.parse(dataTo).getTime());
		}
		
	}


	public Timestamp getData() {
		return data;
	}

	public void setData(Timestamp data) {
		this.data = data;
	}

	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}

	public void setPagedListInfo(PagedListInfo pagedListInfo) {
		this.pagedListInfo = pagedListInfo;
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
	
	
	protected void createFilter(Connection db, StringBuffer sqlFilter) 
	{
		//andAudit( sqlFilter );
		if (sqlFilter == null) 
		{
			sqlFilter = new StringBuffer();
		}
		
		if (oggetto!=null && !"".equals(oggetto))	
		{
			sqlFilter.append(" and oggetto ilike ?");
		}
		
		if (noteIdContesto>0)
		{
			sqlFilter.append(" and note_id_contesto = ? ");
		}
		
		if (altro!=null && !"".equals(altro))
		{
			sqlFilter.append(" and (note_modulo ilike ? or note_funzione ilike ? or note_note ilike ?)");
		}
		
		if (data!=null)
		{
			sqlFilter.append(" and data = ?");
		}
		
		if (dataFrom!=null)
		{
			sqlFilter.append(" and data>= ?");
		}
		if (dataTo!=null)
		{
			sqlFilter.append(" and data<= ?");
		}
	
	}
	
	protected int prepareFilter(PreparedStatement pst) throws SQLException 
	{
		int i = 0;
		
		
		if (oggetto!=null && !"".equals(oggetto))
		{
			pst.setString(++i, oggetto);
		}
		
		if (noteIdContesto>0)
		{
			pst.setInt(++i, noteIdContesto);
		}
		
		if (altro!=null && !"".equals(altro))
		{
			pst.setString(++i, "%"+altro+"%" );
			pst.setString(++i, "%"+altro+"%" );
			pst.setString(++i, "%"+altro+"%" );
		}
		
		if (data!=null)
		{
			pst.setTimestamp(++i,data );
		}
		
		if (dataFrom!=null)
		{
			pst.setTimestamp(++i, dataFrom);
		}
		if (dataTo!=null)
		{
			pst.setTimestamp(++i, dataTo);
		}
		
		return i ;
	}
	
	public ResultSet queryList(Connection db,PreparedStatement pst)
	{
		ResultSet rs = null ;
		
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();
		sqlOrder.append(" group by  id, oggetto, note_id_contesto, note_modulo, note_funzione, note_note, data, entered, enteredby, trashed_date ");
		
		sqlCount.append("select count(*) as recordcount from rilasci  "+
				"where trashed_Date is null ");
		createFilter(db, sqlFilter);

		try
		{
		if (pagedListInfo != null) {
			//Get the total number of records matching filter
			pst = db.prepareStatement(sqlCount.toString() +sqlFilter.toString());
			// UnionAudit(sqlFilter,db);
			items = prepareFilter(pst);


			rs = pst.executeQuery(); 
			if (rs.next()) {
				int maxRecords = rs.getInt("recordcount");
				pagedListInfo.setMaxRecords(maxRecords);
			}
			rs.close();
			pst.close();

			pagedListInfo.setColumnToSortBy("data");
			pagedListInfo.setSortOrder("desc");
		
			pagedListInfo.appendSqlTail(db, sqlOrder);

		} else {
			sqlOrder.append("");
		}	

		//Need to build a base SQL statement for returning records
	
	
		sqlSelect.append("select id, oggetto, note_id_contesto, note_modulo, note_funzione, note_note, data, entered, enteredby, trashed_date from rilasci  "+
				"where trashed_Date is null  ");
		
		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() +  sqlOrder.toString());
		items = prepareFilter(pst);	
		
		rs = pst.executeQuery();
		if (pagedListInfo != null) { 	 	
			pagedListInfo.doManualOffset(db, rs);
		}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return rs ;
		
		
	}
	
	public void buildList(Connection db) throws SQLException {
		PreparedStatement pst = null;

		ResultSet rs = queryList(db, pst);
		while (rs.next()) {
			Rilascio rilascio = this.getObject(rs);
			this.add(rilascio);
			}
		}
	
	
	public Rilascio getObject(ResultSet rs) throws SQLException {
		  
		Rilascio st = new Rilascio() ;
		st.loadResultSet(rs);
		return st ;
	}

	public int getNoteIdContesto() {
		return noteIdContesto;
	}

	public void setNoteIdContesto(int noteIdContesto) {
		this.noteIdContesto = noteIdContesto;
	}

	public void setNoteIdContesto(String noteIdContesto) {
		try { this.noteIdContesto = Integer.parseInt(noteIdContesto);} catch (Exception e) {};
	}
	
	
	
	

}
