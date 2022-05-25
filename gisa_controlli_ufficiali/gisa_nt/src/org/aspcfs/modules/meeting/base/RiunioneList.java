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

public class RiunioneList extends Vector implements SyncableList {



	protected PagedListInfo pagedListInfo = null;


	private String nome ;
	private String titolo ;
	private String cognome ;
	
	private Timestamp data ;
	private int stato ;
	
	
	private Timestamp dataFrom ;
	private Timestamp dataTo ;
	
	private String contesto ;

	private int espresso;
	private int userId ;

	
	public String getContesto() {
		return contesto;
	}

	public void setContesto(String contesto) {
		this.contesto = contesto;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Timestamp getData() {
		return data;
	}

	public void setData(Timestamp data) {
		this.data = data;
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
		
		if (contesto!=null && !"".equals(contesto) && ! "altro".equalsIgnoreCase(contesto) && ! "tutti".equalsIgnoreCase(contesto))	
		{
			sqlFilter.append(" and r.contesto ilike ?");
		}
		else
		{
			if (contesto!=null && !"".equals(contesto) &&    "altro".equalsIgnoreCase(contesto))
			sqlFilter.append(" and r.contesto not ilike ? and r.contesto not ilike ? and r.contesto not ilike ?");
		}
		if (nome!=null && !"".equals(nome))	
		{
			sqlFilter.append(" and p.nome ilike ?");
		}
		
		if (cognome!=null && !"".equals(cognome))
		{
			sqlFilter.append(" and p.cognome ilike ?");
		}
		
		if (titolo!=null && !"".equals(titolo))
		{
			sqlFilter.append(" and r.titolo ilike ?");
		}
		
		if (data!=null)
		{
			sqlFilter.append(" and r.data = ?");
		}
		
		if (stato>0)
		{
			sqlFilter.append(" and r.stato = ?");
		}
		
		if (dataFrom!=null)
		{
			sqlFilter.append(" and r.data>= ?");
		}
		if (dataTo!=null)
		{
			sqlFilter.append(" and r.data<= ?");
		}
	
	}
	
	protected int prepareFilter(PreparedStatement pst) throws SQLException 
	{
		int i = 0;
		
		
		if (contesto!=null && !"".equals(contesto) && ! "altro".equalsIgnoreCase(contesto) && ! "tutti".equalsIgnoreCase(contesto))	
		{
			pst.setString(++i, contesto);
		}
		else
		{
			if (contesto!=null && !"".equals(contesto) &&    "altro".equalsIgnoreCase(contesto))
			{
			pst.setString(++i, "gisa");
			pst.setString(++i, "bdu/vam");
			pst.setString(++i, "di.ge.mon.");
			}
		}
		
		if (contesto!=null && !"".equals(contesto))	
		{
			
		}
		
		if (nome!=null && !"".equals(nome))
		{
			pst.setString(++i, nome);
		}
		
		if (cognome!=null && !"".equals(cognome))
		{
			pst.setString(++i, cognome );
		}
		
		if (titolo!=null && !"".equals(titolo))
		{
			pst.setString(++i, titolo);
		}
		
		if (data!=null)
		{
			pst.setTimestamp(++i,data );
		}
		
		if (stato>0)
		{
			pst.setInt(++i, stato);
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
		sqlOrder.append(" group by  r.id, r.titolo, r.descrizione_breve, r.data, r.luogo, r.entered, r.enteredby, r.stato, r.trashed_date,r.contesto ");
		
		sqlCount.append("select count(r.*) as recordcount from riunioni r  "+
				"where r.trashed_Date is null ");
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

			pagedListInfo.setColumnToSortBy("r.data");
			pagedListInfo.setSortOrder("desc");
		
			pagedListInfo.appendSqlTail(db, sqlOrder);

		} else {
			sqlOrder.append("");
		}	

		//Need to build a base SQL statement for returning records
	
	
		sqlSelect.append("select r.contesto,r.id, r.titolo, r.descrizione_breve, r.data, r.luogo, r.entered, r.enteredby, r.stato, r.trashed_date,string_agg(nominativo,';') as lista_partecipanti from riunioni r join riunioni_partecipanti rp on rp.id_riunione = r.id  "+
				"where r.trashed_Date is null  ");
		
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
			Riunione riunione = this.getObject(rs);
			
			riunione.buildListaRilasci(db);
			
			String listaPart = rs.getString("lista_partecipanti");
			String[] arrayPartecipanti = listaPart.split(";");
			
			riunione.setListaPartecipanti(arrayPartecipanti);
			
			if (espresso < 0)
				this.add(riunione);
			else {
					riunione.buildListaReferenti(db);
					for (ReferenteApprovazione ref : riunione.getListaReferenti())
					{
						if (userId == ref.getReferente().getUserId()){
							if (ref.getDataApprovazione()!=null && espresso == 1)
								this.add(riunione);
							else if (ref.getDataApprovazione()==null && espresso == 2)
								this.add(riunione);
						}
					}
					pagedListInfo.setMaxRecords(this.size());
					}
		
			}
		}
	
	
	public Riunione getObject(ResultSet rs) throws SQLException {
		  
		Riunione st = new Riunione() ;
		st.loadResultSet(rs);
		return st ;
	}

	public int getEspresso() {
		return espresso;
	}

	public void setEspresso(int espresso) {
		this.espresso = espresso;
	}
	
	public void setEspresso(String espresso) {
		try { this.espresso = Integer.parseInt(espresso);} catch (Exception e){};
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public void setUserId(String userId) {
		try { this.userId = Integer.parseInt(userId);} catch (Exception e){};
	}
	
	
	
	

}
