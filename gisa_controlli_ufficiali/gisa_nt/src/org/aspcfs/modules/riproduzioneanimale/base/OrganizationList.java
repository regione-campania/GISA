/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.riproduzioneanimale.base;

import org.apache.log4j.Logger;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.riproduzioneanimale.base.Organization;
import org.aspcfs.modules.riproduzioneanimale.base.OrganizationAddressList;
import org.aspcfs.modules.relationships.base.Relationship;
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;


public class OrganizationList extends Vector {

	private static Logger log = Logger.getLogger(org.aspcfs.modules.riproduzioneanimale.base.OrganizationList.class);
	protected PagedListInfo pagedListInfo = null;
	private int orgId = -1						;
	private String name = ""					;
	private int siteId = -1						;
	private java.sql.Timestamp entered = null	;
	private java.sql.Timestamp modified = null	;
	private int enteredBy 						;
	private int modifiedBy 						;
	private int tipologia 						;
	private String comune ;
	
	private String accountName;
	private String accountCity;
	private int addressType ;
	private String city;

	
	
	public String getComune() {
		return comune;
	}


	public void setComune(String comune) {
		this.comune = comune;
	}

	public void setAddressType(String addressType) {
	    this.addressType = Integer.parseInt(addressType);
	  }
	public void setAddressType(Integer addressType) {
		this.addressType = addressType;
	}
  

	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}


	public void setPagedListInfo(PagedListInfo pagedListInfo) {
		this.pagedListInfo = pagedListInfo;
	}


	public int getOrgId() {
		return orgId;
	}


	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}


	public String getName() {
		return name;
	}

	public void setAccountName(String name2) {
		// TODO Auto-generated method stub
		this.accountName = name2;
	}

	public void setName(String name) {
		this.name = name;
	}


	public void setCity(String city) {
		this.city = city;
	}
	
	public int getSiteId() {
		return siteId;
	}


	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = Integer.parseInt(siteId);
	}

	public void setAccountCity(String city) {
		// TODO Auto-generated method stub
		this.accountCity = city;
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

	public Organization getObject(ResultSet rs) throws SQLException {
		Organization thisOrganization = new Organization(rs);
		return thisOrganization;
	}
	
	protected void buildResources(Connection db) throws SQLException {
	    Iterator i = this.iterator();
	    while (i.hasNext()) {
	      org.aspcfs.modules.riproduzioneanimale.base.Organization thisOrganization = (org.aspcfs.modules.riproduzioneanimale.base.Organization ) i.next();
	      thisOrganization.getAddressList().buildList(db);
	     
	    }
	  }
	
	public void buildList(Connection db) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = queryList(db, pst);
	    while (rs.next()) 
	    {
	    	Organization thisOrganization = this.getObject(rs);
	    	OrganizationAddressList lista = new OrganizationAddressList();
	    	
	    	lista.setOrgId(thisOrganization.getId());
	    	lista.buildList(db);
	    	thisOrganization.setAddressList(lista);
	    	
	    	OrganizationStazioniList stazioni = new OrganizationStazioniList();
	    	stazioni.setOrg_id(thisOrganization.getId());
	    	stazioni.buildList(db);
	    	thisOrganization.setStazioniList(stazioni);
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
						"left join organization_address oa on (oa.org_id = o.org_id) " );
		sqlCount.append(" WHERE o.org_id >= 0 and tipologia = 8 ");
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
		        "oa.county as o_county,oa.addrline1,oa.latitude,oa.longitude  " +
		        "FROM organization o "  + 
		        "LEFT JOIN organization_address oa ON (o.org_id = oa.org_id) where tipologia = 8 ");
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
		
		
		//Name
	    if (accountName != null && (!accountName.equals("")) ) {
	        if (accountName.indexOf("%") >= 0) {
	        	 sqlFilter.append("AND o.name ILIKE ? ");
	        } else {
	          sqlFilter.append("AND o.name = ? ");
	        }
	    }
	     
	    //City legale
	    if (accountCity != null && (!accountCity.equals("")) ) {
	        if (accountCity.indexOf("%") >= 0) {
	        	sqlFilter.append(" AND (oa.city ILIKE ? ");
		    	sqlFilter.append(" OR oa.addrline1 ILIKE ? ) ");

	        } else {
	        	sqlFilter.append(" AND (oa.city = ? ");
		    	sqlFilter.append(" OR oa.addrline1 = ? ) ");

	        }
	    }
	    
	    if(siteId > -1) {
	    	sqlFilter.append("AND o.site_id = ? ");
	    }
	
	}


	protected int prepareFilter(PreparedStatement pst) throws SQLException {
		int i = 0;
		
		//Name
	    if (accountName != null && (!accountName.equals("")) ) {
		      pst.setString(++i, accountName.toLowerCase().replaceAll(" ", "%"));
		}
	    
	    //AccountCityLeg
	    if (city != null && !(city.equals(""))) {
		      pst.setString(++i, city.toLowerCase().replaceAll(" ", "%"));
		      pst.setString(++i, city.toLowerCase().replaceAll(" ", "%"));
		}
	    
	    if(siteId > -1) {
	    	pst.setInt(++i, siteId);
	    }
		
		return i;
	}
	


	
	

}

