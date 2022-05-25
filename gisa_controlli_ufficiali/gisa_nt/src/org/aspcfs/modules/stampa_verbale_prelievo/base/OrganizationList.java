/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.stampa_verbale_prelievo.base;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.stampa_verbale_prelievo.base.Organization;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Import;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.modules.relationships.base.Relationship;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.Vector;


/**
 *  Contains a list of organizations... currently used to build the list from
 *  the database with any of the parameters to limit the results.
 *
 * @author     mrajkowski
 * @created    August 30, 2001
 * @version    $Id: OrganizationList.java,v 1.2 2001/08/31 17:33:32 mrajkowski
 *      Exp $
 */
public class OrganizationList extends Vector<Organization> implements SyncableList {
	
	Logger logger = Logger.getLogger("MainLogger");

  private static Logger log = Logger.getLogger(org.aspcfs.modules.accounts.base.OrganizationList.class);
  static {
    if (System.getProperty("DEBUG") != null) {
      log.setLevel(Level.DEBUG);
    }
  }
  
  protected String HtmlJsEvent = "";
  public final static int TRUE = 1;
  public final static int FALSE = 0;
  private boolean directBill = false;
  private static final long serialVersionUID = 2268314721560915731L;
  
  protected PagedListInfo pagedListInfo = null;

  protected String name = null;
  protected int orgId = -1;
  protected int orgSiteId = -1;
  
  private String city = null;
  private String state = null;
  private String addrline1 = null;
  private String country = null;
  private String partitaIva =null;
  private String tipologia_operatore = null;
  private String codiceFiscale =null;
  
  protected Boolean minerOnly = null;
  public final static String tableName = "organization";
  
  protected int includeEnabled = TRUE;
  public final static String uniqueField = "org_id";
  protected java.sql.Timestamp lastAnchor = null;
  protected java.sql.Timestamp nextAnchor = null;
  protected int syncType = Constants.NO_SYNC;
  
  private boolean escludiAcque = false;
  
  
public void setMinerOnly(boolean tmp) {
	    this.minerOnly = new Boolean(tmp);
   }
  
   /**
   *  Sets the includeEnabled attribute of the OrganizationList object
   *
   * @param  includeEnabled  The new includeEnabled value
   */
  public void setIncludeEnabled(int includeEnabled) {
    this.includeEnabled = includeEnabled;
  }

	
	public String getTipologia_operatore() {
		return tipologia_operatore;
	}

	public void setTipologia_operatore(String tipologia_operatore) {
		this.tipologia_operatore = tipologia_operatore;
	}
	
	public String getPartitaIva() {
		return partitaIva;
	}
  
   public void setPartitaIva (String partitaIva) {
	  this.partitaIva=partitaIva;
   }
  
   public String getCodiceFiscale() {
	  return codiceFiscale;
   }
  
   
  public void setCodiceFiscale (String codiceFiscale) {
	  this.codiceFiscale=codiceFiscale;
  }
 
	public void setPagedListInfo(PagedListInfo tmp) {
		this.pagedListInfo = tmp;
	}

	public void setOrgSiteId(int orgSiteId) {
		this.orgSiteId = orgSiteId;
	}


	public void setOrgSiteId(String orgSiteId) {
		this.orgSiteId = Integer.parseInt(orgSiteId);
	}


	public int getOrgSiteId() {
		return orgSiteId;
	}
	
	public void setName(String tmp) {
		this.name = tmp;
	}

	public void setAccountName(String tmp) {
		this.name = tmp;
	}

	public String getAccountName() {
	    return name;
	}

	public void setOrgId(int tmp) {
		this.orgId = tmp;
	}

	public void setOrgId(String tmp) {
		this.orgId = Integer.parseInt(tmp);
	}

	public int getOrgId() {
		return orgId;
	}

	public String getCity() {
		return city;
	}

  
	public void setCity(String tmp) {
		this.city = tmp;
	}

	public String getAccountCity() {
		return city;
	}

	public void setAccountCity(String tmp) {
		this.city = tmp;
	}

	public void setAccountAddrline1(String tmp) {
		this.addrline1 = tmp;
	}
	
	public void setAccountState(String tmp) {
		this.state = tmp;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String tmp) {
		this.state = tmp;
	}

	public String getAccountState() {
		return state;
	}

	public void setAccountOtherState(String tmp) {
		this.state = tmp;
	}

	public String getCountry() {
		return country;
	}

	
	public void setCountry(String tmp) {
		this.country = tmp;
	}
	

  /**
   *  Gets the accountCountry attribute of the OrganizationList object
   *
   * @return    The accountCountry value
   */
  public String getAccountCountry() {
    return country;
  }

  /**
   *  Sets the accountCountry attribute of the OrganizationList object
   *
   * @param  tmp  The new accountCountry value
   */
  	public void setAccountCountry(String tmp) {
  		this.country = tmp;
  	}

  	public void setSearchText(String tmp) {
  		this.name = tmp;
  	}


  	
  	public String getTableName() {
  		// TODO Auto-generated method stub
  		return tableName;
  	}

  	public String getUniqueField() {
  		// TODO Auto-generated method stub
  		return null;
  	}

  	public void setLastAnchor(java.sql.Timestamp tmp) {
  	    this.lastAnchor = tmp;
  	  }


  	  /**
  	   *  Sets the lastAnchor attribute of the OrganizationList object
  	   *
  	   * @param  tmp  The new lastAnchor value
  	   */
  	  public void setLastAnchor(String tmp) {
  	    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  	  }


  	  /**
  	   *  Sets the nextAnchor attribute of the OrganizationList object
  	   *
  	   * @param  tmp  The new nextAnchor value
  	   */
  	  public void setNextAnchor(java.sql.Timestamp tmp) {
  	    this.nextAnchor = tmp;
  	  }


  	  /**
  	   *  Sets the nextAnchor attribute of the OrganizationList object
  	   *
  	   * @param  tmp  The new nextAnchor value
  	   */
  	  public void setNextAnchor(String tmp) {
  	    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  	  }


  	  /**
  	   *  Sets the syncType attribute of the OrganizationList object
  	   *
  	   * @param  tmp  The new syncType value
  	   */
  	  public void setSyncType(int tmp) {
  	    this.syncType = tmp;
  	  }

	
  	
  /**
   *  Gets the searchText(name) attribute of the OrganizationList object
   *
   * @return    The searchText(name) value
   */
  public String getSearchText() {
    return name;
  }
  
  public java.sql.Timestamp getLastAnchor() {
		return lastAnchor;
	}

  public java.sql.Timestamp getNextAnchor() {
		return nextAnchor;
	}

  public int getSyncType() {
		return syncType;
 }

  public String getHtmlSelect(String selectName, int defaultKey) {
	    HtmlSelect orgListSelect = new HtmlSelect();

	    Iterator i = this.iterator();
	    while (i.hasNext()) {
	      Organization thisOrg = (Organization) i.next();
	      orgListSelect.addItem(
	          thisOrg.getOrgId(),
	          thisOrg.getName());
	    }

	    if (!(this.getHtmlJsEvent().equals(""))) {
	      orgListSelect.setJsEvent(this.getHtmlJsEvent());
	    }

	    return orgListSelect.getHtml(selectName, defaultKey);
	  }
  
  
  /**
   *  Gets the HtmlSelectDefaultNone attribute of the OrganizationList object
   *
   * @param  selectName  Description of Parameter
   * @param  thisSystem  Description of the Parameter
   * @return             The HtmlSelectDefaultNone value
   */
  public String getHtmlSelectDefaultNone(SystemStatus thisSystem, String selectName) {
    return getHtmlSelectDefaultNone(thisSystem, selectName, -1);
  }

  
  /**
   *  Gets the htmlSelectDefaultNone attribute of the OrganizationList object
   *
   * @param  selectName  Description of the Parameter
   * @param  defaultKey  Description of the Parameter
   * @param  thisSystem  Description of the Parameter
   * @return             The htmlSelectDefaultNone value
   */
  public String getHtmlSelectDefaultNone(SystemStatus thisSystem, String selectName, int defaultKey) {
    HtmlSelect orgListSelect = new HtmlSelect();
    orgListSelect.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));

    Iterator i = this.iterator();
    while (i.hasNext()) {
      Organization thisOrg = (Organization) i.next();
      orgListSelect.addItem(
          thisOrg.getOrgId(),
          thisOrg.getName());
    }

    if (!(this.getHtmlJsEvent().equals(""))) {
      orgListSelect.setJsEvent(this.getHtmlJsEvent());
    }

    return orgListSelect.getHtml(selectName, defaultKey);
  }
  
  /**
   *  Gets the HtmlJsEvent attribute of the OrganizationList object
   *
   * @return    The HtmlJsEvent value
   */
  public String getHtmlJsEvent() {
    return HtmlJsEvent;
  }
  
  
  public void buildList(Connection db) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = queryList(db, pst);
	    while (rs.next()) {
	      Organization thisOrganization = this.getObject(rs);
	      this.add(thisOrganization);
	    }
	    rs.close();
	    if (pst != null) {
	      pst.close();
	    }
	   buildResources(db);
	  }

  
  protected void buildResources(Connection db) throws SQLException {
	    Iterator i = this.iterator();
	    while (i.hasNext()) {
	    	Organization thisOrganization = (Organization) i.next();
	    	thisOrganization.getAddressList().buildList(db);
	    }
  }
  
  public Organization getObject(ResultSet rs) throws SQLException {
	  Organization thisOrganization = new Organization(rs);
    return thisOrganization;
  }
  
  public ResultSet queryList (Connection db, PreparedStatement pst) throws SQLException  {
		
	  ResultSet rs = null;
	  StringBuffer sqlSelect = new StringBuffer();
	  StringBuffer sqlFilter = new StringBuffer();
	  StringBuffer sqlCount = new StringBuffer();
	  StringBuffer sqlOrder = new StringBuffer();
	  

	  sqlCount.append(" SELECT COUNT(*) AS recordcount " +
	        " FROM organization o " +
	        " LEFT JOIN organization_address oa5 on (o.org_id = oa5.org_id and oa5.address_type = 5) "+
	        " LEFT JOIN organization_address oa7 on (o.org_id = oa7.org_id and oa7.address_type = 7) " +
	        " WHERE o.org_id > 0 and trashed_date is null ");
	  
	  if (escludiAcque)
		  sqlCount.append(" and o.tipologia<>14 ");
	 
	  //Gestire i filtri
	  createFilter(db, sqlFilter);
	  
	  if (pagedListInfo != null) {
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(
	          sqlCount.toString() +
	          sqlFilter.toString());
	      
	      prepareFilter(pst);
	      	      
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        int maxRecords = rs.getInt("recordcount");
	        pagedListInfo.setMaxRecords(maxRecords);
	      }
	      rs.close();
	      pst.close();
	 
	      pagedListInfo.appendSqlTail(db, sqlOrder);

	  }
	 
	  sqlSelect.append(
	        " SELECT distinct(o.org_id), site_id, l.description as asl, o.name, " +
	        " case when (btrim(o.codice_fiscale) = ''::text or o.codice_fiscale is null ) then o.partita_iva::text "+
	        " when (btrim(o.partita_iva) = ''::text or o.partita_iva is null) then o.codice_fiscale::text " +
	        " when o.codice_fiscale is not null then o.codice_fiscale::text "+
	        " when o.partita_iva is not null then o.partita_iva::text "+
	        " else 'N.D' end as codice_fiscale, partita_iva, " +
	        " CASE WHEN o.tipologia = 3 THEN COALESCE(oa5.city, 'N.D.'::character varying) " +
            " WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa5.city, 'N.D.'::character varying) " +
            " WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa7.city, 'N.D.'::character varying) " +
            " ELSE COALESCE(oa5.city, oa7.city,'N.D.'::character varying) "+
            " END AS comune, "+ 
	        " o.tipologia, " +
	        " t.description as tipologia_operatore, o.trashed_date " +
	        " FROM organization o " +
	        " LEFT JOIN organization_address oa5 on (o.org_id = oa5.org_id and oa5.address_type = 5) "+
	        " LEFT JOIN organization_address oa7 on (o.org_id = oa7.org_id and oa7.address_type = 7) " +
	        " LEFT JOIN lookup_site_id l on l.code = o.site_id "+
	        " LEFT JOIN lookup_tipologia_operatore t on t.code = o.tipologia and t.enabled "+
	        " WHERE TRUE and o.org_id > 0 and o.trashed_date is null "
	    ); 
	  
	  	if (!this.getTipologia_operatore().equals("-1")){
	  		sqlSelect.append(" and o.tipologia="+this.getTipologia_operatore()+" ");
	  	}
	  	
	  	 if (escludiAcque)
	  		sqlSelect.append(" and o.tipologia<>14 ");
	 
	  	String filter = sqlFilter.toString();	    
	 	pst = db.prepareStatement(sqlSelect.toString() + filter +sqlOrder.toString());
	    prepareFilter(pst);
	    	    				
	   
	     
	    rs = pst.executeQuery();
	    
	    return rs;
	  }

  private void createFilter(Connection db, StringBuffer sqlFilter) {
		
	  if (sqlFilter == null) {
	      sqlFilter = new StringBuffer();
	    }
	  
	   //Filtro per Asl
	    if(orgSiteId!=-1 && orgSiteId !=-2){
	    	sqlFilter.append(" AND site_id = ? ");
	    }
	    
	    if (name != null && !(name.equals(""))) {
	        if (name.indexOf("%") >= 0) {
	          sqlFilter.append(
	              " AND " + DatabaseUtils.toLowerCase(db) + "(o.name) ILIKE ? ");
	        } else {
	          sqlFilter.append(
	              " AND " + DatabaseUtils.toLowerCase(db) + "(o.name) = ? ");
	        }
	     }
	    
	    if (city != null && (!city.equals("")) ) {
	        if (city.indexOf("%") >= 0) {
	        	sqlFilter.append(" AND (coalesce(oa5.city,oa7.city) ILIKE ?) ");

	        } else {
	        	sqlFilter.append(" AND (coalesce(oa5.city,oa7.city) = ? )");

	        }
	    }
	   
	    if ( (codiceFiscale != null && !codiceFiscale.equals("")) || (partitaIva != null && !partitaIva.equals("")) ) {
	        if (codiceFiscale.indexOf("%") >= 0) {
	          sqlFilter.append(
	              " AND (" + DatabaseUtils.toLowerCase(db) + "(codice_fiscale) ILIKE ? OR " + DatabaseUtils.toLowerCase(db) +"(partita_iva) ILIKE ?)");
	        } else {
	          sqlFilter.append(
	              " AND (" + DatabaseUtils.toLowerCase(db) + "(codice_fiscale) = ? OR " + DatabaseUtils.toLowerCase(db) +"(partita_iva) = ?)");
	        }
	     }  
	    
  	}
  
  
  private void prepareFilter(PreparedStatement pst) throws SQLException {
		 
	  	int i = 0;
		 
		 if(orgSiteId!=-1 && orgSiteId!=-2){
			 pst.setInt(++i, orgSiteId);
		 }
		 
		 if (name != null && !"".equals(name)) {
		      pst.setString(++i, name.toLowerCase());
		 }
		  
		 if (city != null && !(city.equals(""))) {
			 pst.setString(++i, city.toLowerCase().replaceAll(" ", "%"));
		 }
		 
		 if (codiceFiscale != null && !"".equals(codiceFiscale)) {
		      pst.setString(++i, codiceFiscale.toLowerCase());
		      pst.setString(++i, codiceFiscale.toLowerCase());
		 }
		 
		 if (partitaIva != null && !"".equals(partitaIva)) {
			 pst.setString(++i, partitaIva.toLowerCase());
		     pst.setString(++i, partitaIva.toLowerCase());
		 }
		 	
  }

@Override
public void setSyncType(String arg0) {
	// TODO Auto-generated method stub
	
}

public boolean isEscludiAcque() {
	return escludiAcque;
}

public void setEscludiAcque(boolean escludiAcque) {
	this.escludiAcque = escludiAcque;
}
		 
  
  

  
  
 
  }
  
  