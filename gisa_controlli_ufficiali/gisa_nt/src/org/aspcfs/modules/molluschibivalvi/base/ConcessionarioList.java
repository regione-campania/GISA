/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.molluschibivalvi.base;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;
public class ConcessionarioList extends Vector {

		private static Logger log = Logger.getLogger(org.aspcfs.modules.molluschibivalvi.base.ConcessionarioList.class);

		private int id ;
		private String name ;
		private String titolare_nome ;
		private String titolare_cognome;

		private String cfTitolare;
		private Vector comuni=new Vector()	;
		private java.sql.Timestamp entered = null		;
		private java.sql.Timestamp modified = null	;
		private int enteredBy 						;
		private int modifiedBy 						;
		private int tipologia 						;
		private int siteId		;
		private String ipEntered ;
		private String ipModified ;
		protected PagedListInfo pagedListInfo = null;
		private int idZona ;
		
		
		
		public int getIdZona() {
			return idZona;
		}


		public void setIdZona(int idZona) {
			this.idZona = idZona;
		}


		public PagedListInfo getPagedListInfo() {
			return pagedListInfo;
		}


		public void setPagedListInfo(PagedListInfo pagedListInfo) {
			this.pagedListInfo = pagedListInfo;
		}
		
		public String getIpEntered() {
			return ipEntered;
		}
		public void setIpEntered(String ipEntered) {
			this.ipEntered = ipEntered;
		}
		public String getIpModified() {
			return ipModified;
		}
		public void setIpModified(String ipModified) {
			this.ipModified = ipModified;
		}
		public int getSiteId() {
			return siteId;
		}
		public void setSiteId(int siteId) {
			this.siteId = siteId;
		}
		
		public void setSiteId(String siteId) {
			if (siteId != null && !"".equalsIgnoreCase(siteId))
			this.siteId = Integer.parseInt(siteId);
		}
		
		public java.sql.Timestamp getEntered() {
			return entered;
		}
		public void setEntered(java.sql.Timestamp entered) {
			this.entered = entered;
		}
		public java.sql.Timestamp getModified() {
			return modified;
		}
		public void setModified(java.sql.Timestamp modified) {
			this.modified = modified;
		}
		public int getEnteredBy() {
			return enteredBy;
		}
		public void setEnteredBy(int enteredBy) {
			this.enteredBy = enteredBy;
		}
		public int getModifiedBy() {
			return modifiedBy;
		}
		public void setModifiedBy(int modifiedBy) {
			this.modifiedBy = modifiedBy;
		}
		public int getTipologia() {
			return tipologia;
		}
		public void setTipologia(int tipologia) {
			this.tipologia = tipologia;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public void setId(String id) {
			this.id = Integer.parseInt(id);
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public String getTitolare_nome() {
			return titolare_nome;
		}
		public void setTitolare_nome(String titolare_nome) {
			this.titolare_nome = titolare_nome;
		}
		public String getTitolare_cognome() {
			return titolare_cognome;
		}
		public void setTitolare_cognome(String titolare_cognome) {
			this.titolare_cognome = titolare_cognome;
		}
		public String getCfTitolare() {
			return cfTitolare;
		}
		public void setCfTitolare(String cfTitolare) {
			this.cfTitolare = cfTitolare;
		}
	
		public Concessionario getObject(ResultSet rs) throws SQLException {
			Concessionario thisOrganization = new Concessionario(rs);
			return thisOrganization;
		}
		
		protected void buildResources(Connection db) throws SQLException {
		    Iterator i = this.iterator();
		    while (i.hasNext()) {
		      org.aspcfs.modules.molluschibivalvi.base.Concessionario thisOrganization = (org.aspcfs.modules.molluschibivalvi.base.Concessionario ) i.next();
		      thisOrganization.getAddressList().buildList(db);
		     
		    }
		  }
		
		public void buildList(Connection db) throws SQLException {
		    PreparedStatement pst = null;
		    ResultSet rs = queryList(db, pst);
		    while (rs.next()) 
		    {
		    	Concessionario thisOrganization = this.getObject(rs);
		    	OrganizationAddressList lista = new OrganizationAddressList();
		    	lista.setOrgId(thisOrganization.getId());
		    	lista.buildList(db);
		    	thisOrganization.setAddressList(lista);
		        this.add(thisOrganization);
		     
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
			sqlCount.append("SELECT  COUNT(distinct o.*) AS recordcount " +
							"FROM organization o " +
							"left join organization_address oa on (oa.org_id = o.org_id) " +
							"left join concessionari_associati_zone_in_concessione c_associati on (c_associati.id_concessionario = o.org_id) " +
							" WHERE o.org_id >= 0 and tipologia = 211 and o.trashed_date is null ");
			createFilter(db, sqlFilter);
			if (pagedListInfo != null) {
				//Get the total number of records matching filter
				pst = db.prepareStatement(sqlCount.toString() +sqlFilter.toString());
				items = prepareFilter(pst);
				rs = pst.executeQuery();
				if (rs.next()) {
					int maxRecords = rs.getInt("recordcount");
					pagedListInfo.setMaxRecords(maxRecords);
				}
				rs.close();
				pst.close();

				//Determine the offset, based on the filter, for the first record to show
				if (!pagedListInfo.getCurrentLetter().equals("")) {
					pst = db.prepareStatement(sqlCount.toString() +sqlFilter.toString() +"AND " + DatabaseUtils.toLowerCase(db) + "(o.name) < ? ");
					items = prepareFilter(pst);
					pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
					rs = pst.executeQuery();
					if (rs.next()) 
					{
						int offsetCount = rs.getInt("recordcount");
						pagedListInfo.setCurrentOffset(offsetCount);
					}
					rs.close();
					pst.close();
				}

				//Determine column to sort by
				pagedListInfo.setColumnToSortBy("o.name");
				pagedListInfo.appendSqlTail(db, sqlOrder);
			} else 
			{
				sqlOrder.append("ORDER BY o.name ");
			}

			if (pagedListInfo != null) 
			{
				pagedListInfo.appendSqlSelectHead(db, sqlSelect);
			} else 
			{
				sqlSelect.append("SELECT ");
			}
			sqlSelect.append(
			        "distinct o.*, " +
			        "oa.city as o_city, oa.state as o_state, oa.postalcode as o_postalcode," +
			        "oa.county as o_county,oa.addrline1,oa.latitude,oa.longitude " +
			        "FROM organization o "  + 
			        "LEFT JOIN organization_address oa ON (o.org_id = oa.org_id) " +
					"left join concessionari_associati_zone_in_concessione c_associati on (c_associati.id_concessionario = o.org_id) " +
					"where tipologia = 211  and  o.trashed_date is null " );

			pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
			items = prepareFilter(pst);
			if (pagedListInfo != null) {
				pagedListInfo.doManualOffset(db, pst);
			}
			rs = DatabaseUtils.executeQuery(db, pst, log);
			if (pagedListInfo != null) {
				pagedListInfo.doManualOffset(db, rs);
			}
			return rs;
		}
		
		
		protected void createFilter(Connection db, StringBuffer sqlFilter) {
			
			
			if(idZona > 0)
			{
				sqlFilter.append(" and c_associati.id_zona = ? ");
			}
			if(siteId > 0)
			{
				sqlFilter.append(" and site_id = ? ");
			}

			if(name!=null && !"".equals(name))
			{
				sqlFilter.append(" and name ilike ? ");
			}
			if(titolare_nome!=null && !"".equals(titolare_nome))
			{
				sqlFilter.append(" and nome_rappresentante ilike ? ");
			}

		
		}


		protected int prepareFilter(PreparedStatement pst) throws SQLException {
			int i = 0;
			
			if(idZona > 0)
			{
				pst.setInt(++i, idZona);
			}

			if(siteId > 0)
			{
				pst.setInt(++i, siteId);
			}

			if(name!=null && !"".equals(name))
			{
				pst.setString(++i, name);
			}
			if(titolare_nome!=null && !"".equals(titolare_nome))
			{
				pst.setString(++i, titolare_nome);
			}

			return i;
		}

		

	}

