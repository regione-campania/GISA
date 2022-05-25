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
package org.aspcfs.modules.cessazionevariazione.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Iterator;

import org.aspcfs.modules.cessazionevariazione.base.Ticket;
import org.aspcfs.modules.opu.base.SoggettoFisico;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

public class TicketList extends org.aspcfs.modules.troubletickets.base.TicketList
{
	protected int source = -1;
	protected int tipologia = -1;
	protected String tipo_richiesta = null;
	protected int tipoCampione = -1;
	protected int esitoCampione = -1;
	protected int statoVoltura ;
	protected int tipoOperatore ;
	
	
	
	
	
	public int getStatoVoltura() {
		return statoVoltura;
	}
	  
	public int getTipoOperatore() {
		return tipoOperatore;
	}

	public void setTipoOperatore(int tipoOperatore) {
		this.tipoOperatore = tipoOperatore;
	}

	public void setStatoVoltura(int statoVoltura) {
		this.statoVoltura = statoVoltura;
	}

	public String getTipo_richiesta() {
		return tipo_richiesta;
	}
	  
	  public int getTipologia() {
			return tipologia;
		}

	  public int getEsitoCampione() {
			return esitoCampione;
		}

	  public int getTipoCampione() {
			return tipoCampione;
		}

	public void setTipo_richiesta(String tipo_richiesta) {
		this.tipo_richiesta = tipo_richiesta;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}
	
	public void setSource(String source) {
		this.source = Integer.parseInt( source );
	}

	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}
	
	public void setTipologia(String tipologia) {
		this.tipologia = Integer.parseInt( tipologia );
	}
	
	public void setTipoCampione(int tipoCampione) {
		this.tipoCampione = tipoCampione;
	}
	
	public void setTipoCampione(String tipoCampione) {
		this.tipoCampione = Integer.parseInt( tipoCampione );
	}
	
	public void setEsitoCampione(int esitoCampione) {
		this.esitoCampione = esitoCampione;
	}
	
	public void setEsitoCampione(String esitoCampione) {
		this.esitoCampione = Integer.parseInt( esitoCampione );
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
		        "FROM ticket t " +
			    "LEFT JOIN organization o ON (t.org_id = o.org_id) " +
		        
		       
		        "LEFT JOIN asset a ON (t.link_asset_id = a.asset_id) " +
		      
		        "WHERE t.ticketid > 0 AND t.tipologia = 4 ");
		    createFilter(sqlFilter, db);
		    if (pagedListInfo != null) {
		      //Get the total number of records matching filter
		      pst = db.prepareStatement(
		          sqlCount.toString() +
		          sqlFilter.toString());
		      items = prepareFilter(pst);
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
		        String sqlSubCount =
		            "AND " +
		            (pagedListInfo.getColumnToSortBy().equals("t.problem") ? DatabaseUtils.convertToVarChar(
		            db, pagedListInfo.getColumnToSortBy()) : pagedListInfo.getColumnToSortBy()) + " " +
		            direction + " " +
		            "(SELECT " + (pagedListInfo.getColumnToSortBy().equals(
		            "t.problem") ? DatabaseUtils.convertToVarChar(
		            db, pagedListInfo.getColumnToSortBy()) : pagedListInfo.getColumnToSortBy()) + " " +
		            "FROM ticket t WHERE ticketid = ?) ";
		        pst = db.prepareStatement(
		            sqlCount.toString() +
		            sqlFilter.toString() +
		            sqlSubCount);
		        items = prepareFilter(pst);
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
		    } else {
		      sqlOrder.append("ORDER BY t.entered ");
		    }

		    //Need to build a base SQL statement for returning records
		    if (pagedListInfo != null) {
		      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
		    } else {
		      sqlSelect.append("SELECT ");
		    }
		    sqlSelect.append(
		        "t.*,(cc.namefirst ||' '|| cc.namelast) as inserito_da, " +
		        "o.name AS orgname, " +
		        "o.enabled AS orgenabled, " +
		        "o.site_id AS orgsiteid, " +
		        
		        "a.serial_number AS serialnumber, " +
		        "a.manufacturer_code AS assetmanufacturercode, " +
		        "a.vendor_code AS assetvendorcode, " +
		        "a.model_version AS modelversion, " +
		        "a.location AS assetlocation, " +
		        "a.onsite_service_model AS assetonsiteservicemodel  " +
		       
		        "FROM ticket t " +
			  
		        "LEFT JOIN organization o ON (t.org_id = o.org_id) " +
		        "LEFT JOIN contact cc ON (t.enteredby = cc.user_id) " +
		       
		        "LEFT JOIN asset a ON (t.link_asset_id = a.asset_id) " +
		        
		     
		        "WHERE t.ticketid > 0 AND t.tipologia = 4 ");
		    pst = db.prepareStatement(
		        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
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
		      Ticket thisTicket = new Ticket(rs);
		      
		      SoggettoFisico rappImpresa =  new SoggettoFisico();
		      rappImpresa.queryRecordStorico(db, thisTicket.getIdRappresentanteLegaleOperatore());
		      
		      SoggettoFisico rapStab =  new SoggettoFisico();
		      rapStab.queryRecordStorico(db, thisTicket.getIdRappresentanteLegaleStabilimento());
		      
		      thisTicket.setRappresentateImpresa(rappImpresa);
		      thisTicket.setRappresentateStabilimento(rapStab);
		      this.add(thisTicket);
		    }
		    rs.close();
		    pst.close();
		    //Build resources
		    Iterator i = this.iterator();
		    while (i.hasNext()) {
		      Ticket thisTicket = (Ticket) i.next();
		   
		    }
		  }
	
	
	
	
	
	public void buildListRichiesteVolture(Connection db,int idOpuOperatore, int idAsl) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    int items = -1;
	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	    //Need to build a base SQL statement for counting records
	    sqlCount.append(
	        "SELECT COUNT(distinct ticketid) AS recordcount " +
	        "FROM ticket t " +
			"join volture_asl_coinvolte volt on volt.id_voltura = t.ticketid "+
	        "LEFT JOIN organization o ON (t.org_id = o.org_id) " +
	        "LEFT JOIN asset a ON (t.link_asset_id = a.asset_id) " +
	      
	        "WHERE t.ticketid > 0 AND t.tipologia = 4  and volt.approvata=false ");
	    
	  
	    if (idAsl>0)
	    {
	    	sqlCount.append(" and volt.id_asl = ? ");
	    }
	    
	    if (pagedListInfo != null) {
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(
	          sqlCount.toString() +
	          sqlFilter.toString());
	      int i = 0 ;
	      if (idOpuOperatore >0)
		  {
		      pst.setInt(++i, idOpuOperatore);
		  }
	      if (idAsl>0)
		  {
		      pst.setInt(++i, idAsl);
		  }
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
	        String sqlSubCount =
	            "AND " +
	            (pagedListInfo.getColumnToSortBy().equals("t.problem") ? DatabaseUtils.convertToVarChar(
	            db, pagedListInfo.getColumnToSortBy()) : pagedListInfo.getColumnToSortBy()) + " " +
	            direction + " " +
	            "(SELECT " + (pagedListInfo.getColumnToSortBy().equals(
	            "t.problem") ? DatabaseUtils.convertToVarChar(
	            db, pagedListInfo.getColumnToSortBy()) : pagedListInfo.getColumnToSortBy()) + " " +
	            "FROM ticket t WHERE ticketid = ?) ";
	        pst = db.prepareStatement(
	            sqlCount.toString() +
	            sqlSubCount);
	        pst.setInt(1, idAsl);
		      pst.setInt(2, idOpuOperatore);
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
	    } else {
	      sqlOrder.append("ORDER BY t.entered ");
	    }

	    //Need to build a base SQL statement for returning records
	    if (pagedListInfo != null) {
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT distinct ");
	    }
	    sqlSelect.append(
	        "t.*,(cc.namefirst ||' '|| cc.namelast) as inserito_da, " +
	        "o.name AS orgname, " +
	        "o.enabled AS orgenabled, " +
	        "o.site_id AS orgsiteid, " +
	        "a.serial_number AS serialnumber, " +
	        "a.manufacturer_code AS assetmanufacturercode, " +
	        "a.vendor_code AS assetvendorcode, " +
	        "a.model_version AS modelversion, " +
	        "a.location AS assetlocation, " +
	        "a.onsite_service_model AS assetonsiteservicemodel,volt.id_asl  " +
	        "FROM ticket t " +
			"join volture_asl_coinvolte volt on volt.id_voltura = t.ticketid "+
	        "LEFT JOIN organization o ON (t.org_id = o.org_id) " +
	        "LEFT JOIN contact cc ON (t.enteredby = cc.user_id) " +
	        "LEFT JOIN asset a ON (t.link_asset_id = a.asset_id) " +
        	"WHERE t.ticketid > 0 AND t.tipologia = 4  and volt.approvata=false ");
	    
	   
	    
	    if (idAsl>0)
	    {
	    	sqlSelect.append(" and volt.id_asl = ?");
	    }
	    
	    pst = db.prepareStatement(
	        sqlSelect.toString() +  sqlOrder.toString());
	    
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
	    int i = 0 ;
	    if (idOpuOperatore >0)
	    {
		    pst.setInt(++i, idOpuOperatore);
	    }
	      if (idAsl>0)
		    {
		      pst.setInt(++i, idAsl);
		    }
	    rs = pst.executeQuery();
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	    }
	    while (rs.next()) {
	      Ticket thisTicket = new Ticket(rs);
	      thisTicket.setIdAsl(rs.getInt("id_asl"));
	      thisTicket.setIdOpuOperatore(rs.getInt("id_opu_operatore"));
	      SoggettoFisico rappImpresa =  new SoggettoFisico();
	      rappImpresa.queryRecordStorico(db, thisTicket.getIdRappresentanteLegaleOperatore());
	      
	      SoggettoFisico rapStab =  new SoggettoFisico();
	      rapStab.queryRecordStorico(db, thisTicket.getIdRappresentanteLegaleStabilimento());
	      
	      thisTicket.setRappresentateImpresa(rappImpresa);
	      thisTicket.setRappresentateStabilimento(rapStab);
	      this.add(thisTicket);
	    }
	    rs.close();
	    pst.close();
	    //Build resources
	    Iterator j = this.iterator();
	    while (j.hasNext()) {
	      Ticket thisTicket = (Ticket) j.next();
	   
	    }
	  }
	
	
	public void buildListVoltureStabSoaInRichiesta(Connection db) throws SQLException {
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
	        "FROM ticket t " +
	        "LEFT JOIN organization o ON (t.org_id = o.org_id) " +
	      
	     
	        "LEFT JOIN asset a ON (t.link_asset_id = a.asset_id) " +
	       
	        "WHERE t.ticketid > 0 AND t.tipologia = 4 ");
	    createFilter(sqlFilter, db);
	    if (pagedListInfo != null) {
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(
	          sqlCount.toString() +
	          sqlFilter.toString());
	      items = prepareFilter(pst);
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
	        String sqlSubCount =
	            "AND " +
	            (pagedListInfo.getColumnToSortBy().equals("t.problem") ? DatabaseUtils.convertToVarChar(
	            db, pagedListInfo.getColumnToSortBy()) : pagedListInfo.getColumnToSortBy()) + " " +
	            direction + " " +
	            "(SELECT " + (pagedListInfo.getColumnToSortBy().equals(
	            "t.problem") ? DatabaseUtils.convertToVarChar(
	            db, pagedListInfo.getColumnToSortBy()) : pagedListInfo.getColumnToSortBy()) + " " +
	            "FROM ticket t WHERE ticketid = ?) ";
	        pst = db.prepareStatement(
	            sqlCount.toString() +
	            sqlFilter.toString() +
	            sqlSubCount);
	        items = prepareFilter(pst);
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
	    } else {
	      sqlOrder.append("ORDER BY t.entered ");
	    }

	    //Need to build a base SQL statement for returning records
	    if (pagedListInfo != null) {
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT ");
	    }
	    sqlSelect.append(
	        "t.*,(cc.namefirst ||' '|| cc.namelast) as inserito_da, " +
	        "o.name AS orgname, o.tipologia as tipo_operatore," +
	        "o.enabled AS orgenabled, " +
	        "o.site_id AS orgsiteid, " +
	      
	        "a.serial_number AS serialnumber, " +
	        "a.manufacturer_code AS assetmanufacturercode, " +
	        "a.vendor_code AS assetvendorcode, " +
	        "a.model_version AS modelversion, " +
	        "a.location AS assetlocation, " +
	        "a.onsite_service_model AS assetonsiteservicemodel  " +
	       
	        "FROM ticket t " +
	        "LEFT JOIN organization o ON (t.org_id = o.org_id) " +
	        "LEFT JOIN contact cc ON (t.enteredby = cc.user_id) " +
	       
	        "LEFT JOIN asset a ON (t.link_asset_id = a.asset_id) " +
	       
	        "WHERE t.ticketid > 0 and o.tipologia in (3,97) AND t.tipologia = 4 and stato_voltura = 0 and operazione not ilike '%avvio attiv%'");
	    pst = db.prepareStatement(
	        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
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
	      Ticket thisTicket = new Ticket(rs);
	      thisTicket.setTipologia_operatore(rs.getInt("tipo_operatore"));
	      this.add(thisTicket);
	    }
	    rs.close();
	    pst.close();
	    //Build resources
	    Iterator i = this.iterator();
	  
	  }	
	
protected void createFilter(StringBuffer sqlFilter, Connection db) {
    if (source > 0) {
	      sqlFilter.append("AND lu_ts.code = ? ");
	}
    if ((tipo_richiesta != null) && (tipo_richiesta.length() > 0)) {
	      sqlFilter.append("AND t.tipo_richiesta = ? ");
	} 
    if ((tipologia == 4)) {
	      sqlFilter.append("AND t.tipologia = 4 ");
	} 
  
	/*sqlFilter.append("AND t.provvedimenti_prescrittivi = ? ");
	sqlFilter.append("AND t.sanzioni_amministrative = ? ");*/
		
	if (enteredBy > -1) {
	      sqlFilter.append("AND t.enteredby = ? ");
	    }
	    if (description != null) {
	      if (description.indexOf("%") >= 0) {
	        sqlFilter.append(
	            "AND (" + DatabaseUtils.toLowerCase(db) + "(" + DatabaseUtils.convertToVarChar(
	            db, "t.problem") + ") LIKE ?) ");
	      } else {
	        sqlFilter.append(
	            "AND (" + DatabaseUtils.toLowerCase(db) + "(" + DatabaseUtils.convertToVarChar(
	            db, "t.problem") + ") = ?) ");
	      }
	    }
	    if (onlyOpen) {
	      sqlFilter.append("AND t.closed IS NULL ");
	    }
	    if (onlyClosed) {
	      sqlFilter.append("AND t.closed IS NOT NULL ");
	    }
	    if (id > -1) {
	      if (pagedListInfo == null || pagedListInfo.getMode() != PagedListInfo.DETAILS_VIEW) {
	        sqlFilter.append("AND t.ticketid = ? ");
	      }
	    }
	    if (orgId > 0) {
    		sqlFilter.append("AND t.org_id = ? ");
    	}
    	else
    	{
    		if (idStabilimento>0)
    		{
    			sqlFilter.append("AND t.id_stabilimento = ? ");
    		}
    	}
	    if (contactId > -1) {
	      sqlFilter.append("AND t.contact_id = ? ");
	    }
	    if (serviceContractId > -1) {
	      sqlFilter.append("AND t.link_contract_id = ? ");
	    }
	    if (assetId > -1) {
	      sqlFilter.append("AND t.link_asset_id = ? ");
	    }
	    if (buildDepartmentTickets){
	      if (department > 0) {
	        if (unassignedToo) {
	          sqlFilter.append(
	              "AND (t.department_code in (?, 0, -1) OR ((t.assigned_to IS NULL OR t.assigned_to = 0 OR t.assigned_to = -1) AND (t.user_group_id IS NULL OR t.user_group_id = 0 OR t.user_group_id = -1)))");
	        } else {
	          sqlFilter.append("AND t.department_code = ? ");
	        }
	      } else {
	        sqlFilter.append("AND t.department_code IS NULL ");
	      }
	    }
	    if (assignedTo > -1) {
	      sqlFilter.append("AND t.assigned_to = ? ");
	    }
	    if (excludeAssignedTo > -1) {
	      sqlFilter.append("AND (t.assigned_to <> ? OR t.assigned_to IS NULL) ");
	    }
	    if (onlyAssigned) {
	      sqlFilter.append("AND (t.assigned_to > 0 AND t.assigned_to IS NOT NULL) ");
	    }
	    if (onlyUnassigned) {
	      sqlFilter.append(
	          "AND (t.assigned_to IS NULL OR t.assigned_to = 0 OR t.assigned_to = -1) ");
	    }
	    if (severity > 0) {
	      sqlFilter.append("AND t.scode = ? ");
	    }
	    if (priority > 0) {
	      sqlFilter.append("AND t.pri_code = ? ");
	    }
	    if (escalationLevel > 0) {
	      sqlFilter.append("AND t.escalation_level = ? ");
	    }
	    if (accountOwnerIdRange != null) {
	      sqlFilter.append(
	          "AND t.org_id IN (SELECT org_id FROM organization WHERE owner IN (" + accountOwnerIdRange + ")) ");
	    }
	    if (productId != -1) {
	      sqlFilter.append("AND t.product_id = ? ");
	    }
	    if (customerProductId != -1) {
	      sqlFilter.append("AND t.customer_product_id = ? ");
	    }
	    if (onlyWithProducts) {
	      sqlFilter.append("AND t.product_id IS NOT NULL ");
	    }
	    
	  
	

	    if (catCode != -1) {
	      sqlFilter.append("AND t.cat_code = ? ");
	    }
	    if (subCat1 != -1) {
	      sqlFilter.append("AND t.subcat_code1 = ? ");
	    }
	    if (subCat2 != -1) {
	      sqlFilter.append("AND t.subcat_code2 = ? ");
	    }
	    if (subCat3 != -1) {
	      sqlFilter.append("AND t.subcat_code3 = ? ");
	    }
	    if ((!includeAllSites) || (orgId == -1 && contactId == -1 && forProjectUser == -1
	         && id == -1 && serviceContractId == -1 && assetId == -1 && projectId == -1 && userGroupId == -1
	         && inMyUserGroups == -1)) {
	      if (siteId != -1) {
	        sqlFilter.append("AND (t.site_id = ? ");
	        if (!exclusiveToSite) {
	          sqlFilter.append("OR t.site_id IS NULL ");
	        }
	        sqlFilter.append(") ");
	      } else {
		      if (exclusiveToSite) {
						sqlFilter.append("AND t.site_id IS NULL ");
	        }
	      }
	    }
	    if (enteredDateStart != null) {
	      sqlFilter.append("AND t.entered >= ? ");
	    }
	    if (enteredDateEnd != null) {
	      sqlFilter.append("AND t.entered <= ? ");
	    }
	    //Sync API
	    if (syncType == Constants.SYNC_INSERTS) {
	      if (lastAnchor != null) {
	        sqlFilter.append("AND t.entered >= ? ");
	      }
	      sqlFilter.append("AND t.entered < ? ");
	    } else if (syncType == Constants.SYNC_UPDATES) {
	      sqlFilter.append("AND t.modified >= ? ");
	      sqlFilter.append("AND t.entered < ? ");
	      sqlFilter.append("AND t.modified < ? ");
	    } else if (syncType == Constants.SYNC_QUERY) {
	      if (lastAnchor != null) {
	        sqlFilter.append("AND t.entered >= ? ");
	      }
	      if (nextAnchor != null) {
	        sqlFilter.append("AND t.entered < ? ");
	      }
	    } else {
	      //No sync, but still need to factor in age
	      if (minutesOlderThan > 0) {
	        sqlFilter.append("AND t.entered <= ? ");
	      }
	    }
	    if (searchText != null && !searchText.equals("")) {
	      sqlFilter.append(
	          "AND (" + DatabaseUtils.toLowerCase(
	          db, DatabaseUtils.convertToVarChar(db, "t.problem")) + " LIKE ? OR " +
	          DatabaseUtils.toLowerCase(
	          db, DatabaseUtils.convertToVarChar(db, "t." + DatabaseUtils.addQuotes(db, "comment"))) + " LIKE ? OR " +
	          DatabaseUtils.toLowerCase(
	          db, DatabaseUtils.convertToVarChar(db, "t.solution")) + " LIKE ?) ");
	    }
	    if (hasEstimatedResolutionDate) {
	      sqlFilter.append("AND t.est_resolution_date IS NOT NULL ");
	    }
	    if (includeOnlyTrashed) {
	      sqlFilter.append("AND t.trashed_date IS NOT NULL ");
	    } else if (trashedDate != null) {
	      sqlFilter.append("AND t.trashed_date = ? ");
	    } else {
	      sqlFilter.append("AND t.trashed_date IS NULL ");
	    }
	    if (userGroupId != -1) {
	      sqlFilter.append("AND t.user_group_id = ? ");
	    }
	   
	    if (defectId > -1) {
	      sqlFilter.append("AND t.defect_id = ? ");
	    }
	    if (stateId > -1) {
	      sqlFilter.append("AND t.state_id = ? ");
	    }
	    if(statoVoltura>0)
	    {
	    	sqlFilter.append(" and stato_voltura = ?");
	    }
	    
	  }

protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (source > 0) {
    	pst.setInt( ++i, source );
	}
    if ((tipo_richiesta != null) && (tipo_richiesta.length() > 0)) {
	      pst.setString( ++i, tipo_richiesta ); 
	}
    
    if ((tipologia == 4)) {
	      pst.setInt( ++i, tipologia );
	}/*
    if ((tipoCampione == -1)) {
        DatabaseUtils.setInt(pst, ++i, tipoCampione);
        }
        if ((esitoCampione == -1)) {
        	DatabaseUtils.setInt(pst, ++i, esitoCampione);
    	}
    DatabaseUtils.setInt(pst, ++i, tipoCampione);
    DatabaseUtils.setInt(pst, ++i, esitoCampione);*/
    if (enteredBy > -1) {
      pst.setInt(++i, enteredBy);
    }
    if (description != null) {
      pst.setString(++i, description.toLowerCase());
    }
    if (id > -1) {
      if (pagedListInfo == null || pagedListInfo.getMode() != PagedListInfo.DETAILS_VIEW) {
        pst.setInt(++i, id);
      }
    }
    if (orgId > 0) {
        pst.setInt(++i, orgId);
      }
      else
      {
      	if (idStabilimento>0)
      	{
      		 pst.setInt(++i, idStabilimento);
      	}
      }
    if (contactId > -1) {
      pst.setInt(++i, contactId);
    }
    if (serviceContractId > -1) {
      pst.setInt(++i, serviceContractId);
    }
    if (assetId > -1) {
      pst.setInt(++i, assetId);
    }
    if (department > 0) {
      pst.setInt(++i, department);
    }
    if (assignedTo > -1) {
      pst.setInt(++i, assignedTo);
    }
    if (excludeAssignedTo > -1) {
      pst.setInt(++i, excludeAssignedTo);
    }
    if (severity > 0) {
      pst.setInt(++i, severity);
    }
    if (priority > 0) {
      pst.setInt(++i, priority);
    }
    if (escalationLevel > 0) {
      pst.setInt(++i, escalationLevel);
    }
    if (productId > 0) {
      pst.setInt(++i, productId);
    }
    if (customerProductId > 0) {
      pst.setInt(++i, customerProductId);
    }
    if (projectId > 0) {
      pst.setInt(++i, projectId);
    }
    if (forProjectUser > -1) {
      pst.setInt(++i, forProjectUser);
    }
    if (catCode != -1) {
      pst.setInt(++i, catCode);
    }
    if (subCat1 != -1) {
      pst.setInt(++i, subCat1);
    }
    if (subCat2 != -1) {
      pst.setInt(++i, subCat2);
    }
    if (subCat3 != -1) {
      pst.setInt(++i, subCat3);
    }
    if ((!includeAllSites) || (orgId == -1 && contactId == -1 && forProjectUser == -1
         && id == -1 && serviceContractId == -1 && assetId == -1 && projectId == -1 && userGroupId == -1
         && inMyUserGroups == -1)) {
      if (siteId != -1) {
				pst.setInt(++i, siteId);
			}
    }
    if (enteredDateStart != null) {
      DatabaseUtils.setTimestamp(
          pst, ++i, new Timestamp(enteredDateStart.getTime()));
    }
    if (enteredDateEnd != null) {
      DatabaseUtils.setTimestamp(
          pst, ++i, new Timestamp(enteredDateEnd.getTime()));
    }
    //Sync API
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      pst.setTimestamp(++i, nextAnchor);
    } else if (syncType == Constants.SYNC_UPDATES) {
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, nextAnchor);
    } else if (syncType == Constants.SYNC_QUERY) {
      if (lastAnchor != null) {
        java.sql.Timestamp adjustedDate = lastAnchor;
        if (minutesOlderThan > 0) {
          Calendar now = Calendar.getInstance();
          now.setTimeInMillis(lastAnchor.getTime());
          now.add(Calendar.MINUTE, minutesOlderThan - (2 * minutesOlderThan));
          adjustedDate = new java.sql.Timestamp(now.getTimeInMillis());
        }
        pst.setTimestamp(++i, adjustedDate);
      }
      if (nextAnchor != null) {
        java.sql.Timestamp adjustedDate = nextAnchor;
        if (minutesOlderThan > 0) {
          Calendar now = Calendar.getInstance();
          now.setTimeInMillis(nextAnchor.getTime());
          now.add(Calendar.MINUTE, minutesOlderThan - (2 * minutesOlderThan));
          adjustedDate = new java.sql.Timestamp(now.getTimeInMillis());
        }
        pst.setTimestamp(++i, adjustedDate);
      }
    } else {
      //No sync, but still need to factor in age
      if (minutesOlderThan > 0) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, minutesOlderThan - (2 * minutesOlderThan));
        java.sql.Timestamp adjustedDate = new java.sql.Timestamp(
            now.getTimeInMillis());
        pst.setTimestamp(++i, adjustedDate);
      }
    }

    if (searchText != null && !searchText.equals("")) {
      pst.setString(++i, searchText.toLowerCase());
      pst.setString(++i, searchText.toLowerCase());
      pst.setString(++i, searchText.toLowerCase());
    }
    if (includeOnlyTrashed) {
      // do nothing
    } else if (trashedDate != null) {
      pst.setTimestamp(++i, trashedDate);
    } else {
      // do nothing
    }
    if (userGroupId != -1) {
      pst.setInt(++i, userGroupId);
    }
   
    if (defectId > -1) {
      pst.setInt(++i, defectId);
    }
    if (stateId > -1) {
      pst.setInt(++i, stateId);
    }
    
    if(statoVoltura>0)
    {
        pst.setInt(++i, statoVoltura);
    }
    return i;
  }

	
}

