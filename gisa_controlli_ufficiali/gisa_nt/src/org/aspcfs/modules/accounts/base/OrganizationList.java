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
package org.aspcfs.modules.accounts.base;

import com.darkhorseventures.framework.actions.ActionContext;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.controller.SystemStatus;
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
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.Vector;
import java.util.ArrayList;

/**
 *  Contains a list of organizations... currently used to build the list from
 *  the database with any of the parameters to limit the results.
 *
 * @author     mrajkowski
 * @created    August 30, 2001
 * @version    $Id: OrganizationList.java,v 1.2 2001/08/31 17:33:32 mrajkowski
 *      Exp $
 */
public class OrganizationList extends Vector implements SyncableList {

  private static Logger log = Logger.getLogger(org.aspcfs.modules.accounts.base.OrganizationList.class);
  static {
    if (System.getProperty("DEBUG") != null) {
      log.setLevel(Level.DEBUG);
    }
  }
  private String codiceAllerta = "";
  public String getCodiceAllerta() {
	return codiceAllerta;
}

public void setCodiceAllerta(String codiceAllerta) {
	this.codiceAllerta = codiceAllerta;
}


protected String[] tipoOperatore ;

  private int categoria = -1;
  private boolean directBill = false;
  private static final long serialVersionUID = 2268314721560915731L;
  public final static int TRUE = 1;
  public final static int FALSE = 0;
  protected int includeEnabled = TRUE;
  private String nomeCorrentista = null;
  private String tipoDest = null;
  public final static String tableName = "organization";
  public final static String uniqueField = "org_id";
  protected java.sql.Timestamp lastAnchor = null;
  protected java.sql.Timestamp nextAnchor = null;
  protected int syncType = Constants.NO_SYNC;
  protected PagedListInfo pagedListInfo = null;
  private int categoriaRischio=-1;
  private Integer addressType=-1;
  private String statoCu ;
  private String istatSecondari;
  
  private boolean ignoraImportOpu = false;
  
  
  
  public String getIstatSecondari() {
	return istatSecondari;
}

public String[] getTipoOperatore() {
	return tipoOperatore;
}

public void setTipoOperatore(String[] tipoOperatore) {
	this.tipoOperatore = tipoOperatore;
}

public void setIstatSecondari(String istatSecondari) {
	this.istatSecondari = istatSecondari;
}

public String getStatoCu() {
	return statoCu;
}

public void setStatoCu(String statoCu) {
	this.statoCu = statoCu;
}

public Integer getCategoriaRischio() {
		return categoriaRischio;
	}

	public void setCategoriaRischio(String categoriaRischio) {
	    this.categoriaRischio = Integer.parseInt(categoriaRischio);
	  }
	public void setCategoriaRischio(Integer categoriaRischio) {
		this.categoriaRischio = categoriaRischio;
	}
	public Integer getAddressType() {
		return addressType;
	}

	public void setDirectBill(boolean tmp) {
	    this.directBill = tmp;
	}
	
	public void setDirectBill(String tmp) {
	    this.directBill = DatabaseUtils.parseBoolean(tmp);
	  }
	
	public boolean getDirectBill() {
	    return directBill;
	  }
	
	public void setAddressType(String addressType) {
	    this.addressType = Integer.parseInt(addressType);
	  }
	public void setAddressType(Integer addressType) {
		this.addressType = addressType;
	}
  private String nomeRappresentante = null;
  private String cognomeRappresentante = null;
  private String codiceFiscaleCorrentista = null;
  protected Boolean minerOnly = null;
  protected int enteredBy = -1;
  protected int tipologia = -1;
  protected String name = null;
  protected int ownerId = -1;
  protected int orgId = -1;
  protected String HtmlJsEvent = "";
  protected boolean showMyCompany = false;
  protected String ownerIdRange = null;
  protected String excludeIds = null;
  protected boolean hasAlertDate = false;
  protected boolean hasExpireDate = false;
  protected String accountNumber = null;
  protected int orgSiteId = -1;
  protected boolean includeOrganizationWithoutSite = false;
  protected int projectId = -1;
  
  private String city = null;
  private String state = null;
  private String country = null;
  protected String postalCode = null;
  protected String assetSerialNumber = null;

  protected int revenueType = 0;
  protected int revenueYear = -1;
  protected int revenueOwnerId = -1;
  protected boolean buildRevenueYTD = false;
  protected java.sql.Timestamp alertRangeStart = null;
  protected java.sql.Timestamp alertRangeEnd = null;

  protected java.sql.Timestamp enteredSince = null;
  protected java.sql.Timestamp enteredTo = null;

  protected int typeId = 0;
  protected String types = null;
  protected String accountSegment = null;
  
  protected int stageId = -1;

  //import filters
  private int importId = -1;
  private int statusId = -1;
  private boolean excludeUnapprovedAccounts = true;
  private java.sql.Timestamp trashedDate = null;
  private boolean includeOnlyTrashed = false;

  //Contact filters
  private String firstName = null;
  private String lastName = null;
  private String contactPhoneNumber = null;
  private String contactCity = null;
  private String contactState = null;
//  private String contactOtherState = null;
  private String contactCountry = null;
  private int cessato = -1;
  private boolean includeAllSites = false;
  
  private String partitaIva =null;
  
  
  
  //Aggiunta
  private String stato = null;
  private String stato_allevamento = null;
  private String stato_impresa = null;
  private String tipologia_operatore = null;
  private String num_aut = null;
  private String n_reg = null;
  private String titolare = null;
  protected String[] norma ;
  
  
  
  public String[] getNorma() {
	return norma;
}

public void setNorma(String[] norma) {
	this.norma = norma;
}

public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getStato_allevamento() {
		return stato_allevamento;
	}

	public void setStato_allevamento(String stato_allevamento) {
		this.stato_allevamento = stato_allevamento;
	}

	public String getStato_impresa() {
		return stato_impresa;
	}

	public void setStato_impresa(String stato_impresa) {
		this.stato_impresa = stato_impresa;
	}

	public String getTipologia_operatore() {
		return tipologia_operatore;
	}

	public void setTipologia_operatore(String tipologia_operatore) {
		this.tipologia_operatore = tipologia_operatore;
	}

	public String getNum_aut() {
		return num_aut;
	}

	public void setNum_aut(String num_aut) {
		this.num_aut = num_aut;
	}

	public String getN_reg() {
		return n_reg;
	}

	public void setN_reg(String n_reg) {
		this.n_reg = n_reg;
	}

	public String getTitolare() {
		return titolare;
	}

	public void setTitolare(String titolare) {
		this.titolare = titolare;
	}


	public void setTipologia(String tipo) {
	    this.tipologia = Integer.parseInt(tipo);
	  }
  
  
  public String getNomeCorrentista() {
		return nomeCorrentista;
	}

	public void setNomeCorrentista(String nomeCorrentista) {
		this.nomeCorrentista = nomeCorrentista;
	}
	
	public String getTipoDest() {
			return tipoDest;
		}

		public void setTipoDest(String tipoDest) {
			this.tipoDest = tipoDest;
		}
  public String getPartitaIva() {
	  return partitaIva;
  }
  
  public void setPartitaIva (String partitaIva) {
	  this.partitaIva=partitaIva;
  }
  public int getCategoria() {
		return categoria;
	}
	
	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = Integer.parseInt( categoria );
	}
	
	private String joinAudit()
	{
		String ret = " ";
		
		if( categoria > -1 )
		{
			ret = " JOIN audit ON (o.org_id = audit.org_id) ";
		}
			
		return ret;
	}
	
	private void andAudit(StringBuffer sqlFilter)
	{
		if( categoria > -1 )
		{
			if(categoria == 3){
				sqlFilter.append( " AND (audit.categoria = ? OR audit.categoria is null OR audit.categoria = -1) " );
			}else{
			sqlFilter.append( " AND audit.categoria = ? " );}
		}
		
	}
	
	private void UnionAudit(StringBuffer sqlFilter)
	{
		
			sqlFilter.append( " UNION select categoria from audit where categoria == -1 OR categoria is null " );
		
		
	}
	
	private int setAudit(PreparedStatement pst, int i) throws SQLException
	{
		  if( categoria > -1 )
		  {
			  pst.setInt( ++i, categoria );
		  }
		  
		  return i;
	}


private String codiceFiscale =null;
  
  public String getCodiceFiscale() {
	  return codiceFiscale;
  }
  
  public void setCodiceFiscale (String codiceFiscale) {
	  this.codiceFiscale=codiceFiscale;
  }
  
  
  
  public String getNomeRappresentante() {
		return nomeRappresentante;
	}
  
  public String getCognomeRappresentante() {
		return cognomeRappresentante;
	}

	public void setCognomeRappresentante(String cognomeRappresentante) {
		this.cognomeRappresentante = cognomeRappresentante;
	}


	  
	public void setNomeRappresentante(String nomeRappresentante) {
		this.nomeRappresentante = nomeRappresentante;
	}
	
	
	
  /**
   * Gets the includeAllSites attribute of the OrganizationList object
   *
   * @return includeAllSites The includeAllSites value
   */
  public boolean isIncludeAllSites() {
    return this.includeAllSites;
  }

  public void setCessato(int tmp) {
	    this.cessato = tmp;
	  }

public void setCessato(String tmp) {
	    this.cessato = Integer.parseInt(tmp);
	  }
public int getCessato() {
    return cessato;
  }
public String getCodiceFiscaleCorrentista() {
	return codiceFiscaleCorrentista;
}

public void setCodiceFiscaleCorrentista(String codiceFiscaleCorrentista) {
	this.codiceFiscaleCorrentista = codiceFiscaleCorrentista;
}

  /**
   * Sets the includeAllSites attribute of the OrganizationList object
   *
   * @param includeAllSites The new includeAllSites value
   */
  public void setIncludeAllSites(boolean includeAllSites) {
    this.includeAllSites = includeAllSites;
  }


  /**
   *  Constructor for the OrganizationList object, creates an empty list. After
   *  setting parameters, call the build method.
   *
   * @since    1.1
   */
  public OrganizationList() { }


  /**
   *  Gets the enteredSince attribute of the OrganizationList object
   *
   * @return    The enteredSince value
   */
  public java.sql.Timestamp getEnteredSince() {
    return enteredSince;
  }


  /**
   *  Sets the enteredSince attribute of the OrganizationList object
   *
   * @param  tmp  The new enteredSince value
   */
  public void setEnteredSince(java.sql.Timestamp tmp) {
    this.enteredSince = tmp;
  }


  /**
   *  Sets the enteredSince attribute of the OrganizationList object
   *
   * @param  tmp  The new enteredSince value
   */
  public void setEnteredSince(String tmp) {
    this.enteredSince = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Gets the enteredTo attribute of the OrganizationList object
   *
   * @return    The enteredTo value
   */
  public java.sql.Timestamp getEnteredTo() {
    return enteredTo;
  }


  /**
   *  Sets the enteredTo attribute of the OrganizationList object
   *
   * @param  tmp  The new enteredTo value
   */
  public void setEnteredTo(java.sql.Timestamp tmp) {
    this.enteredTo = tmp;
  }


  /**
   *  Sets the enteredTo attribute of the OrganizationList object
   *
   * @param  tmp  The new enteredTo value
   */
  public void setEnteredTo(String tmp) {
    this.enteredTo = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Gets the excludeIds attribute of the OrganizationList object
   *
   * @return    The excludeIds value
   */
  public String getExcludeIds() {
    return excludeIds;
  }


  /**
   *  Sets the excludeIds attribute of the OrganizationList object
   *
   * @param  tmp  The new excludeIds value
   */
  public void setExcludeIds(String tmp) {
    this.excludeIds = tmp;
  }


  /**
   *  Gets the types attribute of the OrganizationList object
   *
   * @return    The types value
   */
  public String getTypes() {
    return types;
  }


  /**
   *  Sets the types attribute of the OrganizationList object
   *
   * @param  tmp  The new types value
   */
  public void setTypes(String tmp) {
    this.types = tmp;
  }


  /**
   *  Sets the lastAnchor attribute of the OrganizationList object
   *
   * @param  tmp  The new lastAnchor value
   */
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
   *  Gets the typeId attribute of the OrganizationList object
   *
   * @return    The typeId value
   */
  public int getTypeId() {
    return typeId;
  }


  /**
   *  Sets the typeId attribute of the OrganizationList object
   *
   * @param  typeId  The new typeId value
   */
  public void setTypeId(int typeId) {
    this.typeId = typeId;
  }


  /**
   *  Sets the typeId attribute of the OrganizationList object
   *
   * @param  typeId  The new typeId value
   */
  public void setTypeId(String typeId) {
    this.typeId = Integer.parseInt(typeId);
  }


  /**
   *  Sets the accountSegment attribute of the OrganizationList object
   *
   * @param  tmp  The new accountSegment value
   */
  public void setAccountSegment(String tmp) {
    this.accountSegment = tmp;
  }


  /**
   *  Sets the accountSegment attribute of the OrganizationList object
   *
   * @return    The accountSegment value
   */
  public String getAccountSegment() {
    return accountSegment;
  }
  /**
   *  Sets the stageId attribute of the OrganizationList object
   *
   * @param  tmp  The new stageId  value
   */
  public void setStageId(int tmp) {
    this.stageId = tmp;
  }
  
  /**
   *  Sets the stageId attribute of the OrganizationList object
   *
   * @param  tmp  The new stageId  value
   */
  public void setStageId(String tmp) {
	  if (tmp!=null){
    this.stageId = Integer.parseInt(tmp);
	  }else{this.stageId = -1;}
  }


  /**
   *  Sets the stageId  attribute of the OrganizationList object
   *
   * @return    The stageId  value
   */
  public int getStageId () {
    return stageId;
  }


  /**
   *  Sets the syncType attribute of the OrganizationList object
   *
   * @param  tmp  The new syncType value
   */
  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
  }


  /**
   *  Sets the PagedListInfo attribute of the OrganizationList object. <p>
   *
   *  <p/>
   *
   *  The query results will be constrained to the PagedListInfo parameters.
   *
   * @param  tmp  The new PagedListInfo value
   * @since       1.1
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the MinerOnly attribute of the OrganizationList object to limit the
   *  results to miner only, or non-miner only.
   *
   * @param  tmp  The new MinerOnly value
   * @since       1.1
   */
  public void setMinerOnly(boolean tmp) {
    this.minerOnly = new Boolean(tmp);
  }


  /**
   *  Sets the revenueType attribute of the OrganizationList object
   *
   * @param  tmp  The new revenueType value
   */
  public void setRevenueType(int tmp) {
    this.revenueType = tmp;
  }


  /**
   *  Sets the revenueYear attribute of the OrganizationList object
   *
   * @param  tmp  The new revenueYear value
   */
  public void setRevenueYear(int tmp) {
    this.revenueYear = tmp;
  }


  /**
   *  Sets the alertRangeStart attribute of the OrganizationList object
   *
   * @param  alertRangeStart  The new alertRangeStart value
   */
  public void setAlertRangeStart(java.sql.Timestamp alertRangeStart) {
    this.alertRangeStart = alertRangeStart;
  }


  /**
   *  Sets the alertRangeEnd attribute of the OrganizationList object
   *
   * @param  alertRangeEnd  The new alertRangeEnd value
   */
  public void setAlertRangeEnd(java.sql.Timestamp alertRangeEnd) {
    this.alertRangeEnd = alertRangeEnd;
  }


  /**
   *  Sets the importId attribute of the OrganizationList object
   *
   * @param  tmp  The new importId value
   */
  public void setImportId(int tmp) {
    this.importId = tmp;
  }


  /**
   *  Sets the excludeUnapprovedAccounts attribute of the OrganizationList
   *  object
   *
   * @param  tmp  The new excludeUnapprovedAccounts value
   */
  public void setExcludeUnapprovedAccounts(boolean tmp) {
    this.excludeUnapprovedAccounts = tmp;
  }


  /**
   *  Sets the excludeUnapprovedAccounts attribute of the OrganizationList
   *  object
   *
   * @param  tmp  The new excludeUnapprovedAccounts value
   */
  public void setExcludeUnapprovedAccounts(String tmp) {
    this.excludeUnapprovedAccounts = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the excludeUnapprovedAccounts attribute of the OrganizationList
   *  object
   *
   * @return    The excludeUnapprovedAccounts value
   */
  public boolean getExcludeUnapprovedAccounts() {
    return excludeUnapprovedAccounts;
  }


  /**
   *  Sets the trashedDate attribute of the OrganizationList object
   *
   * @param  tmp  The new trashedDate value
   */
  public void setTrashedDate(java.sql.Timestamp tmp) {
    this.trashedDate = tmp;
  }


  /**
   *  Sets the trashedDate attribute of the OrganizationList object
   *
   * @param  tmp  The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the includeOnlyTrashed attribute of the OrganizationList object
   *
   * @param  tmp  The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(boolean tmp) {
    this.includeOnlyTrashed = tmp;
  }


  /**
   *  Sets the includeOnlyTrashed attribute of the OrganizationList object
   *
   * @param  tmp  The new includeOnlyTrashed value
   */
  public void setIncludeOnlyTrashed(String tmp) {
    this.includeOnlyTrashed = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the trashedDate attribute of the OrganizationList object
   *
   * @return    The trashedDate value
   */
  public java.sql.Timestamp getTrashedDate() {
    return trashedDate;
  }


  /**
   *  Gets the includeOnlyTrashed attribute of the OrganizationList object
   *
   * @return    The includeOnlyTrashed value
   */
  public boolean getIncludeOnlyTrashed() {
    return includeOnlyTrashed;
  }


  /**
   *  Sets the importId attribute of the OrganizationList object
   *
   * @param  tmp  The new importId value
   */
  public void setImportId(String tmp) {
    this.importId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the statusId attribute of the OrganizationList object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the OrganizationList object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the firstName attribute of the OrganizationList object
   *
   * @param  tmp  The new firstName value
   */
  public void setFirstName(String tmp) {
    this.firstName = tmp;
  }


  /**
   *  Sets the lastName attribute of the OrganizationList object
   *
   * @param  tmp  The new lastName value
   */
  public void setLastName(String tmp) {
    this.lastName = tmp;
  }


  /**
   *  Sets the contactPhoneNumber attribute of the OrganizationList object
   *
   * @param  tmp  The new contactPhoneNumber value
   */
  public void setContactPhoneNumber(String tmp) {
    this.contactPhoneNumber = tmp;
  }


  /**
   *  Sets the contactCity attribute of the OrganizationList object
   *
   * @param  tmp  The new contactCity value
   */
  public void setContactCity(String tmp) {
    this.contactCity = tmp;
  }


  /**
   *  Sets the contactState attribute of the OrganizationList object
   *
   * @param  tmp  The new contactState value
   */
  public void setContactState(String tmp) {
    this.contactState = tmp;
  }


  /**
   *  Gets the contactCountry attribute of the OrganizationList object
   *
   * @return    The contactCountry value
   */
  public String getContactCountry() {
    return contactCountry;
  }


  /**
   *  Sets the contactCountry attribute of the OrganizationList object
   *
   * @param  tmp  The new contactCountry value
   */
  public void setContactCountry(String tmp) {
    this.contactCountry = tmp;
  }


  /**
   *  Gets the contactOtherState attribute of the OrganizationList object
   *
   * @return    The contactOtherState value
   */
  public String getContactOtherState() {
    return contactState;
  }


  /**
   *  Sets the contactOtherState attribute of the OrganizationList object
   *
   * @param  tmp  The new contactOtherState value
   */
  public void setContactOtherState(String tmp) {
    this.contactState = tmp;
  }


  /**
   *  Gets the importId attribute of the OrganizationList object
   *
   * @return    The importId value
   */
  public int getImportId() {
    return importId;
  }


  /**
   *  Gets the statusId attribute of the OrganizationList object
   *
   * @return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the firstName attribute of the OrganizationList object
   *
   * @return    The firstName value
   */
  public String getFirstName() {
    return firstName;
  }


  /**
   *  Gets the lastName attribute of the OrganizationList object
   *
   * @return    The lastName value
   */
  public String getLastName() {
    return lastName;
  }


  /**
   *  Gets the contactPhoneNumber attribute of the OrganizationList object
   *
   * @return    The contactPhoneNumber value
   */
  public String getContactPhoneNumber() {
    return contactPhoneNumber;
  }


  /**
   *  Gets the contactCity attribute of the OrganizationList object
   *
   * @return    The contactCity value
   */
  public String getContactCity() {
    return contactCity;
  }


  /**
   *  Gets the contactState attribute of the OrganizationList object
   *
   * @return    The contactState value
   */
  public String getContactState() {
    return contactState;
  }


  /**
   *  Gets the revenueType attribute of the OrganizationList object
   *
   * @return    The revenueType value
   */
  public int getRevenueType() {
    return revenueType;
  }


  /**
   *  Gets the revenueYear attribute of the OrganizationList object
   *
   * @return    The revenueYear value
   */
  public int getRevenueYear() {
    return revenueYear;
  }


  /**
   *  Gets the includeEnabled attribute of the OrganizationList object
   *
   * @return    The includeEnabled value
   */
  public int getIncludeEnabled() {
    return includeEnabled;
  }


  /**
   *  Sets the includeEnabled attribute of the OrganizationList object
   *
   * @param  includeEnabled  The new includeEnabled value
   */
  public void setIncludeEnabled(int includeEnabled) {
    this.includeEnabled = includeEnabled;
  }


  /**
   *  Sets the ShowMyCompany attribute of the OrganizationList object
   *
   * @param  showMyCompany  The new ShowMyCompany value
   */
  public void setShowMyCompany(boolean showMyCompany) {
    this.showMyCompany = showMyCompany;
  }


  /**
   *  Sets the hasAlertDate attribute of the OrganizationList object
   *
   * @param  hasAlertDate  The new hasAlertDate value
   */
  public void setHasAlertDate(boolean hasAlertDate) {
    this.hasAlertDate = hasAlertDate;
  }


  /**
   *  Sets the HtmlJsEvent attribute of the OrganizationList object
   *
   * @param  HtmlJsEvent  The new HtmlJsEvent value
   */
  public void setHtmlJsEvent(String HtmlJsEvent) {
    this.HtmlJsEvent = HtmlJsEvent;
  }


  /**
   *  Gets the accountNumber attribute of the OrganizationList object
   *
   * @return    The accountNumber value
   */
  public String getAccountNumber() {
    return accountNumber;
  }


  /**
   *  Sets the accountNumber attribute of the OrganizationList object
   *
   * @param  accountNumber  The new accountNumber value
   */
  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }


  /**
   *  Gets the projectId attribute of the OrganizationList object
   *
   * @return    The projectId value
   */
  public int getProjectId() {
    return projectId;
  }


  /**
   *  Sets the projectId attribute of the OrganizationList object
   *
   * @param  projectId  The new projectId value
   */
  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }


  /**
   *  Sets the projectId attribute of the OrganizationList object
   *
   * @param  projectId  The new projectId value
   */
  public void setProjectId(String projectId) {
    this.projectId = Integer.parseInt(projectId);
  }


  /**
   *  Sets the EnteredBy attribute of the OrganizationList object to limit
   *  results to those records entered by that user.
   *
   * @param  tmp  The new EnteredBy value
   * @since       1.1
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the orgSiteId attribute of the OrganizationList object
   *
   * @param  orgSiteId  The new orgSiteId value
   */
  public void setOrgSiteId(int orgSiteId) {
    this.orgSiteId = orgSiteId;
  }


  /**
   *  Sets the orgSiteId attribute of the OrganizationList object
   *
   * @param  orgSiteId  The new orgSiteId value
   */
  public void setOrgSiteId(String orgSiteId) {
    this.orgSiteId = Integer.parseInt(orgSiteId);
  }


  /**
   *  
   *
   * @return    The orgSiteId value
   */
  public int getOrgSiteId() {
    return orgSiteId;
  }


  /**
   *  Sets the includeOrganizationWithoutSite attribute of the OrganizationList
   *  object
   *
   * @param  tmp  The new includeOrganizationWithoutSite value
   */
  public void setIncludeOrganizationWithoutSite(boolean tmp) {
    this.includeOrganizationWithoutSite = tmp;
  }


  /**
   *  Sets the includeOrganizationWithoutSite attribute of the OrganizationList
   *  object
   *
   * @param  tmp  The new includeOrganizationWithoutSite value
   */
  public void setIncludeOrganizationWithoutSite(String tmp) {
    this.includeOrganizationWithoutSite = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the includeOrganizationWithoutSite attribute of the OrganizationList
   *  object
   *
   * @return    The includeOrganizationWithoutSite value
   */
  public boolean getIncludeOrganizationWithoutSite() {
    return includeOrganizationWithoutSite;
  }


  /**
   *  Gets the buildRevenueYTD attribute of the OrganizationList object
   *
   * @return    The buildRevenueYTD value
   */
  public boolean getBuildRevenueYTD() {
    return buildRevenueYTD;
  }


  /**
   *  Sets the buildRevenueYTD attribute of the OrganizationList object
   *
   * @param  buildRevenueYTD  The new buildRevenueYTD value
   */
  public void setBuildRevenueYTD(boolean buildRevenueYTD) {
    this.buildRevenueYTD = buildRevenueYTD;
  }


  /**
   *  Sets the ownerIdRange attribute of the OrganizationList object
   *
   * @param  tmp  The new ownerIdRange value
   */
  public void setOwnerIdRange(String tmp) {
    this.ownerIdRange = tmp;
  }


  /**
   *  Sets the Name attribute of the OrganizationList object to limit results to
   *  those records matching the name. Use a % in the name for wildcard
   *  matching.
   *
   * @param  tmp  The new Name value
   * @since       1.1
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the accountName attribute of the OrganizationList object to limit
   *  results to those records matching the name. Use a % in the name for
   *  wildcard matching.
   *
   * @param  tmp  The new accountName value
   * @since       1.1
   */
  public void setAccountName(String tmp) {
    this.name = tmp;
  }

  public String getAccountName() {
	    return name;
  }

  /**
   *  Gets the revenueOwnerId attribute of the OrganizationList object
   *
   * @return    The revenueOwnerId value
   */
  public int getRevenueOwnerId() {
    return revenueOwnerId;
  }


  /**
   *  Building Alert Counts Sets the revenueOwnerId attribute of the
   *  OrganizationList object
   *
   * @param  revenueOwnerId  The new revenueOwnerId value
   */
  public void setRevenueOwnerId(int revenueOwnerId) {
    this.revenueOwnerId = revenueOwnerId;
  }


  /**
   *  Sets the hasExpireDate attribute of the OrganizationList object
   *
   * @param  hasExpireDate  The new hasExpireDate value
   */
  public void setHasExpireDate(boolean hasExpireDate) {
    this.hasExpireDate = hasExpireDate;
  }


  /**
   *  Sets the OwnerId attribute of the OrganizationList object
   *
   * @param  ownerId  The new OwnerId value
   */
  public void setOwnerId(int ownerId) {
    this.ownerId = ownerId;
  }


  /**
   *  Sets the ownerId attribute of the OrganizationList object
   *
   * @param  tmp  The new ownerId value
   */
  public void setOwnerId(String tmp) {
    this.ownerId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the orgId attribute of the OrganizationList object
   *
   * @param  tmp  The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   *  Sets the orgId attribute of the OrganizationList object
   *
   * @param  tmp  The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the tableName attribute of the OrganizationList object
   *
   * @return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the uniqueField attribute of the OrganizationList object
   *
   * @return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   *  Gets the hasAlertDate attribute of the OrganizationList object
   *
   * @return    The hasAlertDate value
   */
  public boolean getHasAlertDate() {
    return hasAlertDate;
  }


  /**
   *  Gets the ownerIdRange attribute of the OrganizationList object
   *
   * @return    The ownerIdRange value
   */
  public String getOwnerIdRange() {
    return ownerIdRange;
  }


  /**
   *  Gets the hasExpireDate attribute of the OrganizationList object
   *
   * @return    The hasExpireDate value
   */
  public boolean getHasExpireDate() {
    return hasExpireDate;
  }


  /**
   *  Gets the ShowMyCompany attribute of the OrganizationList object
   *
   * @return    The ShowMyCompany value
   */
  public boolean getShowMyCompany() {
    return showMyCompany;
  }


  /**
   *  Gets the HtmlJsEvent attribute of the OrganizationList object
   *
   * @return    The HtmlJsEvent value
   */
  public String getHtmlJsEvent() {
    return HtmlJsEvent;
  }


  /**
   *  Gets the OwnerId attribute of the OrganizationList object
   *
   * @return    The OwnerId value
   */
  public int getOwnerId() {
    return ownerId;
  }


  /**
   *  Gets the orgId attribute of the OrganizationList object
   *
   * @return    The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Gets the postalCode attribute of the OrganizationList object
   *
   * @return    The postalCode value
   */
  public String getPostalCode() {
    return postalCode;
  }


  /**
   *  Sets the postalCode attribute of the OrganizationList object
   *
   * @param  tmp  The new postalCode value
   */
  public void setPostalCode(String tmp) {
    this.postalCode = tmp;
  }


  /**
   *  Gets the accountPostalCode attribute of the OrganizationList object
   *
   * @return    The accountPostalCode value
   */
  public String getAccountPostalCode() {
    return postalCode;
  }


  /**
   *  Sets the accountPostalCode attribute of the OrganizationList object
   *
   * @param  tmp  The new accountPostalCode value
   */
  public void setAccountPostalCode(String tmp) {
    this.postalCode = tmp;
  }

  /**
   *  Gets the accountCity attribute of the OrganizationList object
   *
   * @return    The accountCity value
   */
  public String getCity() {
    return city;
  }

  /**
   *  Sets the accountCity attribute of the OrganizationList object
   *
   * @param  tmp  The new accountCity value
   */
  public void setCity(String tmp) {
    this.city = tmp;
  }

  /**
   *  Gets the accountCity attribute of the OrganizationList object
   *
   * @return    The accountCity value
   */
  public String getAccountCity() {
    return city;
  }

  /**
   *  Sets the contactCity attribute of the OrganizationList object
   *
   * @param  tmp  The new accountCity value
   */
  public void setAccountCity(String tmp) {
    this.city = tmp;
  }

  /**
   *  Gets the accountState attribute of the OrganizationList object
   *
   * @return    The accountState value
   */
  public String getState() {
    return state;
  }

  /**
   *  Sets the accountState attribute of the OrganizationList object
   *
   * @param  tmp  The new accountState value
   */
  public void setState(String tmp) {
    this.state = tmp;
  }

  /**
   *  Gets the accountState attribute of the OrganizationList object
   *
   * @return    The accountState value
   */
  public String getAccountState() {
    return state;
  }

  /**
   *  Sets the contactState attribute of the OrganizationList object
   *
   * @param  tmp  The new accountState value
   */
  public void setAccountOtherState(String tmp) {
    this.state = tmp;
  }

  /**
   *  Gets the accountCountry attribute of the OrganizationList object
   *
   * @return    The accountCountry value
   */
  public String getCountry() {
    return country;
  }

  /**
   *  Sets the accountCountry attribute of the OrganizationList object
   *
   * @param  tmp  The new accountCountry value
   */
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

  /**
   *  Gets the assetSerialNumber attribute of the OrganizationList object
   *
   * @return    The assetSerialNumber value
   */
  public String getAssetSerialNumber() {
    return assetSerialNumber;
  }


  /**
   *  Sets the assetSerialNumber attribute of the OrganizationList object
   *
   * @param  tmp  The new assetSerialNumber value
   */
  public void setAssetSerialNumber(String tmp) {
    this.assetSerialNumber = tmp;
  }


  /**
   *  Sets the searchText(name) attribute of the OrganizationList object
   *
   * @param  tmp  The new searchText (name) value
   */
  public void setSearchText(String tmp) {
    this.name = tmp;
  }


  /**
   *  Gets the searchText(name) attribute of the OrganizationList object
   *
   * @return    The searchText(name) value
   */
  public String getSearchText() {
    return name;
  }


  /**
   *  
   *
   * @param  selectName  Description of Parameter
   * @return             The HtmlSelect value
   * @since              1.8
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }


  /**
   *  
   *
   * @param  selectName  Description of Parameter
   * @param  defaultKey  Description of Parameter
   * @return             The HtmlSelect value
   * @since              1.8
   */
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
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void select(Connection db) throws SQLException {
    buildList(db);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  timeZone       Description of the Parameter
   * @param  events         Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public HashMap queryRecordCount(Connection db, TimeZone timeZone, HashMap events) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlTail = new StringBuffer();

    String sqlDate = ((hasAlertDate ? "alertdate" : "") + (hasExpireDate ? "contract_end" : ""));

    createFilter(db, sqlFilter);

    sqlSelect.append(
        "SELECT " + sqlDate + " AS " + DatabaseUtils.addQuotes(db, "date")+ ", count(*) AS nocols " +
        "FROM organization o " +
        "WHERE o.org_id >= 0 ");
    sqlTail.append("GROUP BY " + sqlDate);
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlTail.toString());
    prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      String alertDate = DateUtils.getServerToUserDateString(
          timeZone, DateFormat.SHORT, rs.getTimestamp("date"));
      int thisCount = rs.getInt("nocols");
      if (events.containsKey(alertDate)) {
        int tmpCount = ((Integer) events.get(alertDate)).intValue();
        thisCount += tmpCount;
      }
      events.put(alertDate, new Integer(thisCount));
    }
    rs.close();
    pst.close();
    return events;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildShortList(Connection db) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();

    createFilter(db, sqlFilter);

    sqlSelect.append(
        "SELECT o.org_id, o.name, " +
        (hasAlertDate ? "o.alertdate, " : "") +
        (hasExpireDate ? "o.contract_end, " : "") +
        "o.alert, o.entered, o.enteredby, o.owner " +
        "FROM organization o " +
        "WHERE o.org_id >= 0 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString());
    prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      Organization thisOrg = new Organization();
      thisOrg.setOrgId(rs.getInt("org_id"));
      thisOrg.setName(rs.getString("name"));
      if (hasAlertDate) {
        thisOrg.setAlertDate(rs.getTimestamp("alertdate"));
      }
      if (hasExpireDate) {
        thisOrg.setContractEndDate(rs.getTimestamp("contract_end"));
      }
      thisOrg.setAlertText(rs.getString("alert"));
      thisOrg.setEntered(rs.getTimestamp("entered"));
      thisOrg.setEnteredBy(rs.getInt("enteredby"));
      thisOrg.setOwner(rs.getInt("owner"));
      this.add(thisOrg);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Queries the database, using any of the filters, to retrieve a list of
   *  organizations. The organizations are appended, so build can be run any
   *  number of times to generate a larger list for a report.
   *
   * @param  db             Description of Parameter
   * @throws  SQLException  Description of Exception
   * @since                 1.1
   */

  
  public void buildList(Connection db) throws SQLException {
	    PreparedStatement pst = null;
	    if(city!=null)
	    {
	    	this.city=this.city.replaceAll("%", "").trim();
	    	   this.city=this.city.replaceAll("-", "%").trim();
	  	     
	    	
	      
	    }
	        if(tipoDest!=null)
	            this.tipoDest=this.tipoDest.replaceAll("%", " ").trim();
	    ResultSet rs = queryList(db, pst);
	    while (rs.next()) {
	      Organization thisOrganization = new Organization ();
	      thisOrganization.buildRecordSearch(rs);
	      
	        this.add(thisOrganization);
	     
	    }
	 
	    rs.close();
	    if (pst != null) {
	      pst.close();
	    }
	    buildResources(db);	
	  }
  
  public void buildListRaggruppamentiOperatoreUnico(Connection db,int idOperatore) throws SQLException {
	    PreparedStatement pst = null;

	    ResultSet rs = queryListRaggruppamentiOperatoreUnico(db, pst,idOperatore);
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

  
  public void buildListView(Connection db) throws SQLException  {

	  PreparedStatement pst = null;
	  ResultSet rs = null;
	  StringBuffer sqlSelect = new StringBuffer();
	  StringBuffer sqlFilter = new StringBuffer();
	  StringBuffer sqlCount = new StringBuffer();
	  StringBuffer sqlOrder = new StringBuffer();
	  
	  sqlCount.append(" select count(*) as recordcount from vista_operatori_globale WHERE TRUE ");
	  
	  //Gestire i filtri
	  createFilterView(db, sqlFilter);
	  
	  if (pagedListInfo != null) {
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(
	          sqlCount.toString() +
	          sqlFilter.toString());
	     
	      prepareFilterView(pst);
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
	        " SELECT distinct org_id, asl, tipologia_operatore, " +
	        " ragione_sociale, titolare, comune, provincia, codice_fiscale, "+
	        " partita_iva, codice_fiscale_rappresentante, stato_impresa, descrizione, "+
	        " stato_allevamento, stato, num_aut, n_reg "+
	        " FROM vista_operatori_globale " +
	        " WHERE TRUE ");  
	    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	    prepareFilterView(pst);
	    rs = pst.executeQuery();
	    while (rs.next()) {
	      Organization thisOrg = new Organization();
	      thisOrg.setOrgId(rs.getInt("org_id"));
	      thisOrg.setName(rs.getString("ragione_sociale"));
	      thisOrg.setAsl(rs.getString("asl"));
	      thisOrg.setTipologia_operatore(rs.getString("tipologia_operatore"));
	      thisOrg.setTitolare(rs.getString("titolare"));
	      thisOrg.setCodiceFiscale(rs.getString("codice_fiscale"));
	      thisOrg.setCodiceFiscaleRappresentante(rs.getString("codice_fiscale_rappresentante"));
	      thisOrg.setCity(rs.getString("comune"));
	      thisOrg.setState(rs.getString("provincia"));
	      thisOrg.setN_reg(rs.getString("n_reg"));
	      thisOrg.setNum_aut(rs.getString("num_aut"));
	      thisOrg.setPartitaIva(rs.getString("partita_iva"));
	      thisOrg.setStato_impresa(rs.getString("stato_impresa"));
	      thisOrg.setStato(rs.getString("stato"));
	      thisOrg.setAlertText(rs.getString("descrizione"));
	      thisOrg.setStato_allevamento(rs.getString("stato_allevamento"));
	      this.add(thisOrg);
	    }
	    rs.close();
	    pst.close();
	    
	  
	    
	  }
	  	
  private void prepareFilterView(PreparedStatement pst) throws SQLException {
	 int i = 0;
	 
	 if(orgSiteId!=-1){
		 pst.setInt(++i, orgSiteId);
	 }
	 
	 //Come prendere gli apici?
	 if (name != null && !"".equals(name)) {
	      pst.setString(++i, name.toLowerCase());
	 }
	 
	 if (tipologia > -1) {
		 pst.setInt(++i, tipologia);
	 }
	 
	 if (city != null && !"".equals(city)) {
	      pst.setString(++i, city.toLowerCase());
	 }
	 
	 if (state != null && !"".equals(state)) {
	     pst.setString(++i, state.toLowerCase());
	 }
	 	
	
  }

private void createFilterView(Connection db, StringBuffer sqlFilter) {
	
	  if (sqlFilter == null) {
	      sqlFilter = new StringBuffer();
	    }

	   //Filtro per Asl
	    if(orgSiteId!=-1){
	    	sqlFilter.append("AND asl_rif = ? ");
	    }
	    
	    //Filtro per nome
	    if (name != null && !(name.equals(""))) {
	        if (name.indexOf("%") >= 0) {
	          sqlFilter.append(
	              "AND " + DatabaseUtils.toLowerCase(db) + "(ragione_sociale) ILIKE ? ");
	        } else {
	          sqlFilter.append(
	              "AND " + DatabaseUtils.toLowerCase(db) + "(ragione_sociale) = ? ");
	        }
	     }
	    
	    //Aggiungere filtri anche sullo stato
	    if(tipologia != -1){
	    	sqlFilter.append(" AND tipologia= ?");
	    }
	    
	    
	    if (city != null && !"".equals(city)) {
	        if (city.indexOf("%") >= 0) {
	          sqlFilter.append(" AND " + DatabaseUtils.toLowerCase(db) + "(comune) ILIKE ? ");       
	        } else {
	        	sqlFilter.append(
	  	              "AND " + DatabaseUtils.toLowerCase(db) + "(comune) = ? ");
	        }
	      }

	    if (state != null && !"".equals(state)) {
	      if (state.indexOf("%") >= 0) {
	    	  sqlFilter.append(" AND " + DatabaseUtils.toLowerCase(db) + "(provincia) ILIKE ? ");
	      } else {
	    	  sqlFilter.append(" AND " + DatabaseUtils.toLowerCase(db) + "(provincia) = ? ");
	        }
	    }  
	    
  }

/**
   *  Gets the object attribute of the OrganizationList object
   *
   * @param  rs             Description of the Parameter
   * @return                The object value
   * @throws  SQLException  Description of the Exception
   */
  public Organization getObject(ResultSet rs) throws SQLException {
    Organization thisOrganization = new Organization(rs);
    return thisOrganization;
  }
  public Organization getObjectSearch(ResultSet rs) throws SQLException {
	    Organization thisOrganization = new Organization();
	    thisOrganization.buildRecordSearch(rs);
	    return thisOrganization;
	  }
  
  
  


  /**
   *  This method is required for synchronization, it allows for the resultset
   *  to be streamed with lower overhead
   *
   * @param  db             Description of the Parameter
   * @param  pst            Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
    ResultSet rs = null;
    int items = 0;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records

    sqlCount.append(
            "SELECT  COUNT(distinct o.org_id) AS recordcount " +
            "FROM lista_imprese_852_old o " );
    
    if ((istatSecondari!=null && ! istatSecondari.equals(""))) {
    	sqlCount.append("join la_imprese_linee_attivita lla on (o.org_id = lla.org_id) "); 
    	sqlCount.append("join la_rel_ateco_attivita lraa on (lla.id_rel_ateco_attivita = lraa.id)");
    	sqlCount.append("join lookup_codistat lc on (lc.code = lraa.id_lookup_codistat) ");
    }
    
    
  
    
        sqlCount.append(" WHERE 1=1   ");
 
    createFilter(db, sqlFilter); 
    
    if (pagedListInfo != null) { 
      //Get the total number of records matching filter
      pst = db.prepareStatement(
          sqlCount.toString() +
          sqlFilter.toString());
     // UnionAudit(sqlFilter,db);
       
      items = prepareFilter(pst);
      
      
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();

      //Determine the offset, based on the filter, for the first record to show
 
      sqlOrder.append(" order by o.impresa ");
      //Determine column to sort by
      pagedListInfo.setColumnToSortBy("");
      pagedListInfo.appendSqlTail(db, sqlOrder);
            
      //Optimize SQL Server Paging
      //sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
    } else {
      sqlOrder.append("");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
     
    	 sqlSelect.append(
    		        " distinct o.*" +
    		        "FROM lista_imprese_852_old o "  ); 
    	
    	 	if ((istatSecondari!=null && ! istatSecondari.equals(""))) {
    	 		sqlSelect.append("join la_imprese_linee_attivita lla on (o.org_id = lla.org_id) "); 
    	 		sqlSelect.append("join la_rel_ateco_attivita lraa on (lla.id_rel_ateco_attivita = lraa.id)");
    	 		sqlSelect.append("join lookup_codistat lc on (lc.code = lraa.id_lookup_codistat) ");
    	    }
    	 	
    	 	sqlSelect.append("WHERE 1=1 ");
    	 	
  
      
   
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
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

  
  public ResultSet queryListRaggruppamentiOperatoreUnico(Connection db, PreparedStatement pst,int idOperatore) throws SQLException {
	    ResultSet rs = null;
	    int items = -1;

	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();

	    //Need to build a base SQL statement for counting records

	    sqlCount.append(
	            "SELECT  COUNT( o.org_id) AS recordcount " +
	            "FROM organization o " +
	            "JOIN opu_raggruppamento_operatori ragg on ragg.org_id_organization = o.org_id and ragg.id_opu_operatore_nuovo = ? " +
	            "left join organization_address oa on (oa.org_id = o.org_id " );
	    
	    
	    
	    if (addressType==null || addressType==-1)
	    {
	    	sqlCount.append(" AND oa.address_type=5 )");
	    }
	    else
	    {
	    	sqlCount.append(")");
	    }
	    if ((istatSecondari!=null && ! istatSecondari.equals(""))) {
	    	sqlCount.append("join la_imprese_linee_attivita lla on (o.org_id = lla.org_id) "); 
	    	sqlCount.append("join la_rel_ateco_attivita lraa on (lla.id_rel_ateco_attivita = lraa.id)");
	    	sqlCount.append("join lookup_codistat lc on (lc.code = lraa.id_lookup_codistat) ");
	    }
	       
	           sqlCount.append(" WHERE o.org_id >= 0 ");

	   
	    if (pagedListInfo != null) {
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(
	          sqlCount.toString() +
	          sqlFilter.toString());
	     // UnionAudit(sqlFilter,db);
	      
	      pst.setInt(1, idOperatore);
	      
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        int maxRecords = rs.getInt("recordcount");
	        pagedListInfo.setMaxRecords(maxRecords);
	      }
	      rs.close();
	      pst.close();

	      //Determine the offset, based on the filter, for the first record to show
	      if (!pagedListInfo.getCurrentLetter().equals("")) {
	        pst = db.prepareStatement(
	            sqlCount.toString() +
	            sqlFilter.toString() +
	            "AND " + DatabaseUtils.toLowerCase(db) + "(o.name) < ? ");
	        items = prepareFilter(pst);
	        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
	        rs = pst.executeQuery();
	        if (rs.next()) {
	          int offsetCount = rs.getInt("recordcount");
	          pagedListInfo.setCurrentOffset(offsetCount);
	        }
	        rs.close();
	        pst.close();
	      }

	      //Determine column to sort by
	      pagedListInfo.setColumnToSortBy("o.name");
	      pagedListInfo.appendSqlTail(db, sqlOrder);
	            
	      //Optimize SQL Server Paging
	      //sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
	    } else { 
	      sqlOrder.append("ORDER BY o.name ");
	    }

	    //Need to build a base SQL statement for returning records
	    if (pagedListInfo != null) {
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT ");
	    }
	     
	    	 sqlSelect.append(
	    		        " o.*, " +
	    		        "oa.city as o_city, oa.state as o_state, oa.postalcode as o_postalcode, oa.county as o_county " +
	    		        "FROM organization o "  + 
	    	            "JOIN opu_raggruppamento_operatori ragg on ragg.org_id_organization = o.org_id and ragg.id_opu_operatore_nuovo = ? " +

	    		        "LEFT JOIN organization_address oa ON (o.org_id = oa.org_id ");
	    	 	if (addressType==null || addressType==-1)
	    	    {
	    	 		sqlSelect.append(" AND oa.address_type=5 ) ");
	    	    }
	    	 	else
	    	 	{
	    	 		sqlSelect.append(" ) ");
	    	 		
	    	 	}
	    	 	if ((istatSecondari!=null && ! istatSecondari.equals(""))) {
	    	 		sqlSelect.append("join la_imprese_linee_attivita lla on (o.org_id = lla.org_id) "); 
	    	 		sqlSelect.append("join la_rel_ateco_attivita lraa on (lla.id_rel_ateco_attivita = lraa.id)");
	    	 		sqlSelect.append("join lookup_codistat lc on (lc.code = lraa.id_lookup_codistat) ");
	    	    }
	    	 	
	    	 	sqlSelect.append("WHERE o.org_id >= 0 ");
	    	 	
	  
	      
	  
	    pst = db.prepareStatement(
	        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	    pst.setInt(1, idOperatore);	    
	    
	   
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
	    
	    
	    rs = DatabaseUtils.executeQuery(db, pst, log);
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	    }
	    return rs;
	  }
  
  public ResultSet queryListAdminNa3(Connection db, PreparedStatement pst) throws SQLException {
	    ResultSet rs = null;
	    int items = -1;

	    StringBuffer sqlSelect = new StringBuffer();
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();

	    //Need to build a base SQL statement for counting records
	    sqlCount.append(
	            "SELECT distinct COUNT(*) AS recordcount " +
	            "FROM organization o " );
	        
	        if(codiceAllerta != null){
	        	if(!codiceAllerta.equals("")){
	        		sqlCount.append(" join ticket cufficiali on o.org_id = cufficiali.org_id and cufficiali.codice_allerta ilike '"+codiceAllerta+"' and cufficiali.tipologia=3"); 
	        		
	        	}
	        	
	        }
	           sqlCount.append(" WHERE o.org_id >= 0 ");

	    createFilterAdminNa3(db, sqlFilter);
	    
	    if (pagedListInfo != null) {
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(
	          sqlCount.toString() +
	          sqlFilter.toString());
	     // UnionAudit(sqlFilter,db);
	      
	      items = prepareFilterAdminNa3(pst);
	      
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        int maxRecords = rs.getInt("recordcount");
	        pagedListInfo.setMaxRecords(maxRecords);
	      }
	      rs.close();
	      pst.close();

	      //Determine the offset, based on the filter, for the first record to show
	      if (!pagedListInfo.getCurrentLetter().equals("")) {
	        pst = db.prepareStatement(
	            sqlCount.toString() +
	            sqlFilter.toString() +
	            "AND " + DatabaseUtils.toLowerCase(db) + "(o.name) < ? ");
	        items = prepareFilterAdminNa3(pst);
	        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
	        rs = pst.executeQuery();
	        if (rs.next()) {
	          int offsetCount = rs.getInt("recordcount");
	          pagedListInfo.setCurrentOffset(offsetCount);
	        }
	        rs.close();
	        pst.close();
	      }

	      //Determine column to sort by
	      pagedListInfo.setDefaultSort("o.name", null);
	      pagedListInfo.appendSqlTail(db, sqlOrder);
	            
	      //Optimize SQL Server Paging
	      //sqlFilter.append("AND o.org_id NOT IN (SELECT TOP 10 org_id FROM organization " + sqlOrder.toString());
	    } else {
	      sqlOrder.append("ORDER BY o.name ");
	    }

	    //Need to build a base SQL statement for returning records
	    if (pagedListInfo != null) {
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT ");
	    }
	    if( codiceAllerta != null){
	    	if(!codiceAllerta.equals(""))
	    	{
	    sqlSelect.append(
	        "o.*, " +
	        "ct_owner.namelast as o_namelast, ct_owner.namefirst as o_namefirst, " +
	        "ct_eb.namelast as eb_namelast, ct_eb.namefirst as eb_namefirst, " +
	        "ct_mb.namelast as mb_namelast, ct_mb.namefirst as mb_namefirst, " +
	        "'' as industry_name, '' AS account_size_name, " +
	        "oa.city as o_city, oa.state as o_state, oa.postalcode as o_postalcode, oa.county as o_county, " +
	        "'' as stage_name "+
	        "FROM organization o "  + 
	        "LEFT JOIN contact ct_owner ON (o.owner = ct_owner.user_id) " +
	        "LEFT JOIN contact ct_eb ON (o.enteredby = ct_eb.user_id) " +
	        "LEFT JOIN contact ct_mb ON (o.modifiedby = ct_mb.user_id) " +
	        "LEFT JOIN organization_address oa ON (o.org_id = oa.org_id) " +
	        " join ticket cufficiali on o.org_id = cufficiali.org_id and cufficiali.codice_allerta ilike '"+codiceAllerta+"' and cufficiali.tipologia=3 "+
	        "WHERE o.org_id >= 0 " );
	    	}else{
	    		
	    		 sqlSelect.append(
	     		        "o.*, " +
	     		        "ct_owner.namelast as o_namelast, ct_owner.namefirst as o_namefirst, " +
	     		        "ct_eb.namelast as eb_namelast, ct_eb.namefirst as eb_namefirst, " +
	     		        "ct_mb.namelast as mb_namelast, ct_mb.namefirst as mb_namefirst, " +
	     		        "'' as industry_name, '' AS account_size_name, " +
	     		        "oa.city as o_city, oa.state as o_state, oa.postalcode as o_postalcode, oa.county as o_county, " +
	     		        "'' as stage_name "+
	     		        "FROM organization o "  + 
	     		        "LEFT JOIN contact ct_owner ON (o.owner = ct_owner.user_id) " +
	     		        "LEFT JOIN contact ct_eb ON (o.enteredby = ct_eb.user_id) " +
	     		        "LEFT JOIN contact ct_mb ON (o.modifiedby = ct_mb.user_id) " +
	     		        "LEFT JOIN organization_address oa ON (o.org_id = oa.org_id) " +
	     		        "WHERE o.org_id >= 0 " );
	    		
	    	}
	    }
	    else
	    {
	    	 sqlSelect.append(
	    		        "o.*, " +
	    		        "ct_owner.namelast as o_namelast, ct_owner.namefirst as o_namefirst, " +
	    		        "ct_eb.namelast as eb_namelast, ct_eb.namefirst as eb_namefirst, " +
	    		        "ct_mb.namelast as mb_namelast, ct_mb.namefirst as mb_namefirst, " +
	    		        "'' as industry_name, '' AS account_size_name, " +
	    		        "oa.city as o_city, oa.state as o_state, oa.postalcode as o_postalcode, oa.county as o_county, " +
	    		        "'' as stage_name "+
	    		        "FROM organization o "  + 
	    		        "LEFT JOIN contact ct_owner ON (o.owner = ct_owner.user_id) " +
	    		        "LEFT JOIN contact ct_eb ON (o.enteredby = ct_eb.user_id) " +
	    		        "LEFT JOIN contact ct_mb ON (o.modifiedby = ct_mb.user_id) " +
	    		        "LEFT JOIN organization_address oa ON (o.org_id = oa.org_id) " +
	    		        "WHERE o.org_id >= 0 " );
	    	
	    	
	    }
	   sqlFilter.append(
	    	" AND (oa.address_id IS NULL OR oa.address_id IN ( "+
	    	"SELECT ora.address_id FROM organization_address ora WHERE ora.org_id = o.org_id AND ora.primary_address = ?) "+
	    	"OR oa.address_id IN (SELECT MIN(ctodd.address_id) FROM organization_address ctodd WHERE ctodd.org_id = o.org_id AND "+
	    	" ctodd.org_id NOT IN (SELECT org_id FROM organization_address WHERE organization_address.primary_address = ?))) ");
	      
	   
	    pst = db.prepareStatement(
	        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	    items = prepareFilterAdminNa3(pst);
	    pst.setBoolean(++items, false);
	    pst.setBoolean(++items, false);
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
	    
	    rs = DatabaseUtils.executeQuery(db, pst, log);
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
	  //andAudit( sqlFilter );
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    
    if(tipoOperatore!= null && tipoOperatore.length>0)
    {
    	String filtro = "("; 
    	for (int i=0;i<tipoOperatore.length-1;i++)
    	{
    		filtro+=tipoOperatore[i]+",";
    		
    	}
    	filtro+=tipoOperatore[tipoOperatore.length-1]+")";
    	sqlFilter.append(" and o.tipo_operatore_old in "+filtro);
    }
    else
    {
    	sqlFilter.append(" and o.tipo_operatore_old in (1,2) ");

    }
    
    
    
   /* if(norma!= null && norma.length>0)
    {
    	String filtro = "("; 
    	for (int i=0;i<norma.length-1;i++)
    	{
    		filtro+=norma[i]+",";
    		
    	}
    	filtro+=norma[norma.length-1]+")";
    	sqlFilter.append(" and ln.id_norma in "+filtro);
    }*/
   
    
    if (codiceAllerta != null && ! "".equals(codiceAllerta))
    {
    	
    	if (codiceAllerta.equalsIgnoreCase("%tutte%"))
    	{
    		if ("%aperti%".equals(statoCu))
    		{
    			sqlFilter.append("and o.org_id in (select org_id from ticket where tipologia = 3 and closed is null and  codice_allerta in (select id_allerta from ticket where tipologia = 700 and trashed_date is null ) and trashed_Date is null)");

    		}
    		else
    		{
    			sqlFilter.append("and o.org_id in (select org_id from ticket where tipologia = 3 and closed is not null and  codice_allerta in (select id_allerta from ticket where tipologia = 700 and trashed_date is null ) and trashed_Date is null)");

    		}
    		
    		
    }
    else
    {
    	if ("%aperti%".equals(statoCu))
		{
			sqlFilter.append("and o.org_id in (select org_id from ticket where tipologia = 3 and closed is null and  codice_allerta ilike ? and trashed_Date is null)");
        	
		}
    	else
    	{
    		sqlFilter.append("and o.org_id in (select org_id from ticket where tipologia = 3 and closed is not null and  codice_allerta ilike ? and trashed_Date is null)");
        	
    	}
    		
    }
    	
    }
    	
    	
   


    if(categoriaRischio!=-1){
    	if(categoriaRischio==3){
    		sqlFilter.append("AND (categoria_rischio = 3 OR categoria_rischio = -1)");
    	}else{
    		sqlFilter.append("AND categoria_rischio = ? ");
        }

    }   
    if (nomeCorrentista != null) {
    	sqlFilter.append("AND nome_correntista ILIKE ? ");
    }
    if (!"".equals(codiceFiscaleCorrentista) && codiceFiscaleCorrentista!=null && (istatSecondari==null || istatSecondari.equals(""))) {
    	sqlFilter.append("AND o.cf_correntista LIKE ? ");
    }
    
    if (!"".equals(codiceFiscaleCorrentista) && codiceFiscaleCorrentista!=null && (istatSecondari!=null && ! istatSecondari.equals(""))) {
    	sqlFilter.append("AND ( o.cf_correntista LIKE ? or lc.description ilike ? )  ");
    }
    
   
    
    if (!"".equals(cessato) && cessato != -1) {
    	if (cessato == 1)
    	{
    		sqlFilter.append("AND (o.cessato = 1 or o.data_fine_carattere < now() )");
    	}
    	else
    	{
    		
    		sqlFilter.append("AND (o.cessato = ? and (o.data_fine_carattere IS NULL OR o.data_fine_carattere > now()))");
    	
    	}
    	}
    
    
//    if (tipologia > -1) {
//        sqlFilter.append("AND o.tipologia = ? ");
//    }

    if (name != null) {
      if (name.indexOf("%") >= 0) {
        sqlFilter.append(
            "AND " + DatabaseUtils.toLowerCase(db) + "(o.impresa) ILIKE ? ");
      } else {
        sqlFilter.append(
            "AND " + DatabaseUtils.toLowerCase(db) + "(o.impresa) = ? ");
      }
    }
    



 


    if (accountNumber != null) {
      if (accountNumber.indexOf("%") >= 0)
      {
        sqlFilter.append(
            "AND " + DatabaseUtils.toLowerCase(db) + "(o.num_reg) LIKE ? ");
        
      }
      else
      {
        sqlFilter.append(
            "AND " + DatabaseUtils.toLowerCase(db) + "(o.num_reg) = ? ");
       
      }
    }
    if (codiceFiscale != null) {
       
          sqlFilter.append(
              "AND " + DatabaseUtils.toUpperCase(db) + "(o.codice_fiscale) LIKE '%"+codiceFiscale.toUpperCase()+"%' ");
      } 
    
    if (!"".equals(partitaIva) && partitaIva!=null) {
        if (partitaIva.indexOf("%") >= 0) {
          sqlFilter.append(
              "AND " + DatabaseUtils.toLowerCase(db) + "(o.partita_iva) LIKE ? ");
        } else {
          sqlFilter.append(
              "AND " + DatabaseUtils.toLowerCase(db) + "(o.partita_iva) = ? ");
        }
      }
       
    if ( ( nomeRappresentante != null) && !"".equals( nomeRappresentante ) ) {
    	sqlFilter.append("AND nome_rappresentante ilike ? ");
    }
    if ( ( cognomeRappresentante != null) && !"".equals( cognomeRappresentante ) ) {
    	sqlFilter.append("AND cognome_rappresentante ilike ? ");
    }
    
    if (orgSiteId != -1) {
      sqlFilter.append("AND ( o.id_asl = ? ");
      if (includeOrganizationWithoutSite) {
        sqlFilter.append("OR o.id_asl IS NULL ");
      }
      sqlFilter.append(")");
    }

    if (orgSiteId == -1) {
      if (includeOrganizationWithoutSite) {
        sqlFilter.append("AND o.site_id IS NULL ");
      }
    }


    
    if (city != null && !"-1".equals(city)) {
    	
    		city = "%"+city+"%";
    	sqlFilter.append(" and o.comune ilike ? and o.tipo_attivita = ? ");
    	
      }
    else
    {
    	if (tipoDest != null && !"".equals(tipoDest))
    	{
    		sqlFilter.append(" AND o.tipo_attivita = ? ");
    	}
    }
    
    if (ignoraImportOpu)
    	sqlFilter.append(" AND o.import_opu is not true ");

  }


  
  protected void createFilterAdminNa3(Connection db, StringBuffer sqlFilter) {
	  //andAudit( sqlFilter );
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }


    if(categoriaRischio!=-1){
    	if(categoriaRischio==3){
    		sqlFilter.append("AND (categoria_rischio = 3 OR categoria_rischio = -1)");
    	}else{
    		sqlFilter.append("AND categoria_rischio = ? ");




        }

    }   

       

    if (nomeCorrentista != null) {
    	sqlFilter.append("AND nome_correntista ILIKE ?");
    }
    if (!"".equals(codiceFiscaleCorrentista) && codiceFiscaleCorrentista!=null) {
    	sqlFilter.append("AND o.cf_correntista LIKE ? ");
    }
    if (!"".equals(cessato) && cessato != -1) {
    sqlFilter.append("AND (o.cessato = ?  OR o.data_fine_carattere < now())");
    }
    if (stageId > -1) {
      sqlFilter.append("AND o.stage_id = ? ");
    }
    
    if (minerOnly != null) {
      sqlFilter.append("AND miner_only = ? ");
    }

    if (enteredBy > -1) {
      sqlFilter.append("AND o.enteredby = ? ");
    }
    
    if (tipologia > -1) {
        sqlFilter.append("AND o.tipologia = ? ");
    }
    
    

    if (name != null) {
      if (name.indexOf("%") >= 0) {
        sqlFilter.append(
            "AND " + DatabaseUtils.toLowerCase(db) + "(o.name) ILIKE ? ");
      } else {
        sqlFilter.append(
            "AND " + DatabaseUtils.toLowerCase(db) + "(o.name) = ? ");
      }
    }

   

    if (ownerId > -1) {
      sqlFilter.append("AND o.owner = ? ");
    }

    if (ownerIdRange != null) {
      sqlFilter.append("AND o.owner IN (" + ownerIdRange + ") ");
    }

    if (excludeIds != null) {
      sqlFilter.append("AND o.org_id NOT IN (" + excludeIds + ") ");
    }

    if (types != null) {
      sqlFilter.append(
          "AND o.org_id IN (select atl.org_id from account_type_levels atl where atl.type_id IN (" + types + ")) ");
    }

    if (includeEnabled == TRUE || includeEnabled == FALSE) {
      sqlFilter.append("AND o.enabled = ? ");
    }

    if (!showMyCompany) {
      sqlFilter.append("AND o.org_id != 0 ");
    }

    if (hasAlertDate) {
      sqlFilter.append("AND o.alertdate is not null ");
      if (alertRangeStart != null) {
        sqlFilter.append("AND o.alertdate >= ? ");
      }

      if (alertRangeEnd != null) {
        sqlFilter.append("AND o.alertdate < ? ");
      }
    }

    if (hasExpireDate) {
      sqlFilter.append("AND o.contract_end is not null ");
      if (alertRangeStart != null) {
        sqlFilter.append("AND o.contract_end >= ? ");
      }

      if (alertRangeEnd != null) {
        sqlFilter.append("AND o.contract_end <= ? ");
      }
    }

    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND o.entered > ? ");
      }
      sqlFilter.append("AND o.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND o.modified > ? ");
      sqlFilter.append("AND o.entered < ? ");
      sqlFilter.append("AND o.modified < ? ");
    }

    if (enteredSince != null) {
      sqlFilter.append("AND o.entered >= ? ");
    }
    
    if (enteredTo != null) {
      sqlFilter.append("AND o.entered <= ? ");
    }
    
    if (revenueOwnerId > -1) {
      sqlFilter.append(
          "AND o.org_id in (SELECT org_id from revenue WHERE owner = ?) ");
    }

    if (accountNumber != null) {
      if (accountNumber.indexOf("%") >= 0)
      {
        sqlFilter.append(
            "AND (" + DatabaseUtils.toLowerCase(db) + "(o.account_number) LIKE ? ");
        sqlFilter.append(
                "OR " + DatabaseUtils.toLowerCase(db) + "(o.codice_impresa_interno) LIKE ? ) ");
      }
      else
      {
        sqlFilter.append(
            "AND (" + DatabaseUtils.toLowerCase(db) + "(o.account_number) = ? ");
        sqlFilter.append(
                "OR " + DatabaseUtils.toLowerCase(db) + "(o.codice_impresa_interno) = ? ) ");
      }
    }
    if (codiceFiscale != null) {
       
          sqlFilter.append(
              "AND " + DatabaseUtils.toUpperCase(db) + "(o.codice_fiscale) LIKE '%"+codiceFiscale.toUpperCase()+"%' ");
      }
    
    if (!"".equals(partitaIva) && partitaIva!=null) {
        if (partitaIva.indexOf("%") >= 0) {
          sqlFilter.append(
              "AND " + DatabaseUtils.toLowerCase(db) + "(o.partita_iva) LIKE ? ");
        } else {
          sqlFilter.append(
              "AND " + DatabaseUtils.toLowerCase(db) + "(o.partita_iva) = ? ");
        }
      }
    
    if ( ( nomeRappresentante != null) && !"".equals( nomeRappresentante ) ) {
    	sqlFilter.append("AND nome_rappresentante ilike ? ");
    }
    if ( ( cognomeRappresentante != null) && !"".equals( cognomeRappresentante ) ) {
    	sqlFilter.append("AND cognome_rappresentante ilike ? ");
    }
    
  
      sqlFilter.append("AND ( o.site_id = 9 ");
  

   

    if (importId != -1) {
      sqlFilter.append("AND o.import_id = ? ");
    }

    if (statusId != -1) {
      sqlFilter.append("AND o.status_id = ? ");
    }

    if (firstName != null) {
      if (firstName.indexOf("%") >= 0) {
        sqlFilter.append("AND EXISTS (select contact_id from contact c where " + DatabaseUtils.toLowerCase(db) + "(c.namefirst) LIKE ? AND c.org_id = o.org_id) ");
      } else {
        sqlFilter.append("AND EXISTS (select contact_id from contact c where " + DatabaseUtils.toLowerCase(db) + "(c.namefirst) = ? AND c.org_id = o.org_id) ");
      }
    }

    if (lastName != null) {
      if (lastName.indexOf("%") >= 0) {
        sqlFilter.append("AND EXISTS (select contact_id from contact c where " + DatabaseUtils.toLowerCase(db) + "(c.namelast) LIKE ? AND c.org_id = o.org_id) ");
      } else {
        sqlFilter.append("AND EXISTS (select contact_id from contact c where " + DatabaseUtils.toLowerCase(db) + "(c.namelast) = ? AND c.org_id = o.org_id) ");
      }
    }

    if (contactPhoneNumber != null) {
      if (contactPhoneNumber.indexOf("%") >= 0) {
        sqlFilter.append("AND EXISTS (select contact_id from contact c where c.org_id = o.org_id AND c.contact_id IN (select cp.contact_id from contact_phone cp where " + DatabaseUtils.toLowerCase(db) + "(cp.number) LIKE ?)) ");
      } else {
        sqlFilter.append("AND EXISTS (select contact_id from contact c where c.org_id = o.org_id AND c.contact_id IN (select cp.contact_id from contact_phone cp where " + DatabaseUtils.toLowerCase(db) + "(cp.number) = ?)) ");
      }
    }

    if (contactCity != null && !"-1".equals(contactCity)) {
      if (contactCity.indexOf("%") >= 0) {
        sqlFilter.append("AND EXISTS (select contact_id from contact c where c.org_id = o.org_id AND c.contact_id IN (select ca.contact_id from contact_address ca where " + DatabaseUtils.toLowerCase(db) + "(ca.city) LIKE ?)) ");
      } else {
        sqlFilter.append("AND EXISTS (select contact_id from contact c where c.org_id = o.org_id AND c.contact_id IN (select ca.contact_id from contact_address ca where " + DatabaseUtils.toLowerCase(db) + "(ca.city) = ?)) ");
      }
    }

    if (contactState != null && !"-1".equals(contactState)) {
      if (contactState.indexOf("%") >= 0) {
        sqlFilter.append("AND EXISTS (select contact_id from contact c where c.org_id = o.org_id AND c.contact_id IN (select ca.contact_id from contact_address ca where " + DatabaseUtils.toLowerCase(db) + "(ca.state) LIKE ?)) ");
      } else {
        sqlFilter.append("AND EXISTS (select contact_id from contact c where c.org_id = o.org_id AND c.contact_id IN (select ca.contact_id from contact_address ca where " + DatabaseUtils.toLowerCase(db) + "(ca.state) = ?)) ");
      }
    }
    if (contactCountry != null && !"-1".equals(contactCountry)) {
      sqlFilter.append("AND EXISTS (select contact_id from contact c where c.org_id = o.org_id AND c.contact_id IN (select ca.contact_id from contact_address ca where " + DatabaseUtils.toLowerCase(db) + "(ca.country) = ?)) ");
    }
    if (excludeUnapprovedAccounts) {
      sqlFilter.append("AND (o.status_id IS NULL OR o.status_id = ?) ");
    }

    if (typeId > 0) {
      sqlFilter.append(
          "AND o.org_id IN (select atl.org_id from account_type_levels atl where atl.type_id = ?) ");
    }

    if (orgId > 0) {
      sqlFilter.append("AND o.org_id = ? ");
    }
    if (projectId > 0) {
      sqlFilter.append(
          "AND o.org_id IN (SELECT org_id FROM project_accounts WHERE project_id = ?) ");
    }
    if (includeOnlyTrashed) {
      sqlFilter.append("AND o.trashed_date IS NOT NULL ");
    } else if (trashedDate != null) {
      sqlFilter.append("AND o.trashed_date = ? ");
    } else {
      sqlFilter.append("AND o.trashed_date IS NULL ");
    }
    if (postalCode != null) {
      if (postalCode.indexOf("%") >= 0) {
        sqlFilter.append(
            "AND o.org_id IN (SELECT org_id FROM organization_address " +
            "WHERE " + DatabaseUtils.toLowerCase(db, "postalcode") + " LIKE ? " +
            "AND postalcode IS NOT NULL) ");
      } else {
          sqlFilter.append(
              "AND o.org_id IN (SELECT org_id FROM organization_address " +
              "WHERE " + DatabaseUtils.toLowerCase(db, "postalcode") + " = ? " +
              "AND postalcode IS NOT NULL) ");
      }
    }

    sqlFilter.append(
            "AND o.org_id IN (SELECT org_id FROM organization_address " +
            "WHERE " + DatabaseUtils.toLowerCase(db, "city") + " in ('acerra','casalnuovo di napoli') " +
            "AND city IS NOT NULL) ");
    
    if (city != null && !"-1".equals(city)) {
      if (city.indexOf("%") >= 0) {
        sqlFilter.append(
            "AND o.org_id IN (SELECT org_id FROM organization_address " +
            "WHERE " + DatabaseUtils.toLowerCase(db, "city") + " LIKE ? " +
            "AND city IS NOT NULL) ");
      } else {
        sqlFilter.append(
            "AND o.org_id IN (SELECT org_id FROM organization_address " +
            "WHERE " + DatabaseUtils.toLowerCase(db, "city") + " = ? " +
            "AND city IS NOT NULL) ");
      }
    }

    if (state != null && !"-1".equals(state)) {
      if (state.indexOf("%") >= 0) {
      sqlFilter.append(
          "AND o.org_id IN (SELECT org_id FROM organization_address " +
          "WHERE " + DatabaseUtils.toLowerCase(db, "state") + " LIKE ? " +
          "AND state IS NOT NULL) ");
      } else {
        sqlFilter.append(
            "AND o.org_id IN (SELECT org_id FROM organization_address " +
            "WHERE " + DatabaseUtils.toLowerCase(db, "state") + " = ? " +
            "AND state IS NOT NULL) ");
      }
    }

    if (country != null && !"-1".equals(country)) {
      sqlFilter.append(
          "AND o.org_id IN (SELECT org_id FROM organization_address " +
          "WHERE " + DatabaseUtils.toLowerCase(db, "country") + " = ? " +
          "AND country IS NOT NULL) ");
    }

    if (assetSerialNumber != null) {
      sqlFilter.append(
          "AND o.org_id IN (SELECT a.account_id FROM asset a " +
          "WHERE a.serial_number = ? AND a.trashed_date IS NULL) ");
    }
  }
  
  
  /**
   *  Convenience method to get a list of phone numbers for each contact
   *
   * @param  db             Description of Parameter
   * @throws  SQLException  Description of Exception
   * @since                 1.5
   */
  protected void buildResources(Connection db) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Organization thisOrganization = (Organization) i.next();
      thisOrganization.getPhoneNumberList().buildList(db);
      thisOrganization.getAddressList().buildList(db);
      thisOrganization.getEmailAddressList().buildList(db);
      //if this is an individual account, populate the primary contact record
     
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  newOwner       Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public int reassignElements(Connection db, int newOwner) throws SQLException {
    int total = 0;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Organization thisOrg = (Organization) i.next();
      
    }
    return total;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  newOwner       Description of the Parameter
   * @param  userId         Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public int reassignElements(Connection db, int newOwner, int userId) throws SQLException {
    int total = 0;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Organization thisOrg = (Organization) i.next();
      thisOrg.setModifiedBy(userId);
     
    }
    return total;
  }


  /**
   *  Sets the parameters for the preparedStatement - these items must
   *  correspond with the createFilter statement
   *
   * @param  pst            Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   * @since                 1.2
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    
    if (codiceAllerta != null && ! "".equals(codiceAllerta))
    {
    	
    	if (!codiceAllerta.equalsIgnoreCase("%tutte%"))
    	{
    		pst.setString(++i, codiceAllerta);
    		
    	}
    	
    }
    
    if(categoriaRischio!=3 && categoriaRischio!=-1){

        	  pst.setInt(++i, this.getCategoriaRischio());

    }
   
    if (nomeCorrentista != null) {
  	  pst.setString(++i, this.getNomeCorrentista());
    }
   
    if (!"".equals(codiceFiscaleCorrentista) && codiceFiscaleCorrentista!=null && (istatSecondari==null || istatSecondari.equals(""))) {
    	pst.setString(++i, codiceFiscaleCorrentista);
    }
    else
    {
    	if (!"".equals(codiceFiscaleCorrentista) && codiceFiscaleCorrentista!=null && (istatSecondari!=null && !istatSecondari.equals(""))) {
        	pst.setString(++i, codiceFiscaleCorrentista);
        	pst.setString(++i, codiceFiscaleCorrentista);
        }
    }
    
//    if (!"".equals(codiceFiscaleCorrentista) && codiceFiscaleCorrentista!=null && (istatSecondari!=null && ! istatSecondari.equals(""))) {
//    	pst.setString(++i, codiceFiscaleCorrentista);
//    	pst.setString(++i, codiceFiscaleCorrentista);
//    }
    
    if (!"".equals(cessato) && cessato > -1) {
    
    	if (cessato != 1)
    	{
    		pst.setInt(++i, cessato);
    	}
    	
    }
//    if (tipologia > -1) {
//        pst.setInt(++i, tipologia);
//     }

    if (name != null) {
      pst.setString(++i, name.toLowerCase().replaceAll(" ", "%"));
    }
   
    
    if (accountNumber != null) {
	      pst.setString(++i, accountNumber.toLowerCase());
//	      pst.setString(++i, accountNumber.toLowerCase());
	    }
    
    if (!"".equals(partitaIva) && partitaIva!=null) {
        pst.setString(++i, partitaIva.toLowerCase());
      }
    if ( ( nomeRappresentante != null) && !"".equals( nomeRappresentante ) ) {
  	  pst.setString(++i, this.getNomeRappresentante());
    }
    if ( ( cognomeRappresentante != null) && !"".equals( cognomeRappresentante ) ) {
  	  pst.setString(++i, this.getCognomeRappresentante());
    }    
    if (orgSiteId != -1) {
      pst.setInt(++i, orgSiteId);
    }

   
       
//    if(addressType != null && addressType!=-1)
//    {
//    	pst.setInt(++i, addressType);
//    }
    
    int tipoDestInt = -1 ;
    
    if (tipoDest != null && tipoDest.equalsIgnoreCase("Es. Commerciale"))
	{
		tipoDestInt = 1 ;
	}
	else
	{
		if (tipoDest != null &&  tipoDest.equalsIgnoreCase("Autoveicolo"))
		{
			tipoDestInt = 2 ;
		}
		else
		{
			
			tipoDestInt=3;
		}
	}
    
    if (city != null && !"-1".equals(city)) {
    	   if (city.indexOf("%") >= 0) {
    		   city = city.replaceAll("%", "");
    		   pst.setString(++i, city.toLowerCase());
    		   pst.setInt(++i, tipoDestInt);
    	   }
    	   else
    	   {
    		   pst.setString(++i, city.toLowerCase());
    		   if (tipoDest!= null)
    		    {
    			if (tipoDest.equalsIgnoreCase("Es. Commerciale"))
    			{
    				tipoDestInt = 1 ;
    			}
    			else
    			{
    				if (tipoDest.equalsIgnoreCase("Autoveicolo"))
    				{
    					tipoDestInt = 2 ;
    				}
    				else
    				{
    					
    					tipoDestInt=3;
    				}
    			}
    			}
    		   pst.setInt(++i, tipoDestInt);
    	   } 
      
//      pst.setInt(++i, addressType);
//      pst.setString(++i, tipoDest);
    }
    else
    {
    	if (tipoDest != null && !"".equals(tipoDest))
    	{
    	
    		if (tipoDest.equalsIgnoreCase("Es. Commerciale"))
    		{
    			tipoDestInt = 1 ;
    		}
    		else
    		{
    			if (tipoDest.equalsIgnoreCase("Autoveicolo"))
    			{
    				tipoDestInt = 2 ;
    			}
    			else
    			{
    				
    				tipoDestInt=3;
    			}
    		}
    		
    		
    		pst.setInt(++i, tipoDestInt);
    	}
    }
    
    return i;
  }
  
  
  protected int prepareFilterAdminNa3(PreparedStatement pst) throws SQLException {
	    int i = 0;
	    //i = setAudit( pst, i );
	    if(categoriaRischio!=3 && categoriaRischio!=-1){

	        	  pst.setInt(++i, this.getCategoriaRischio());

	    }
	   
	    if (nomeCorrentista != null) {
	  	  pst.setString(++i, this.getNomeCorrentista());
	    }
	    if (!"".equals(codiceFiscaleCorrentista) && codiceFiscaleCorrentista!=null) {
	    	pst.setString(++i, codiceFiscaleCorrentista);
	    }
	    if (!"".equals(cessato) && cessato > -1) {
	    pst.setInt(++i, cessato);
	    }
	    if (stageId > -1) {
	      pst.setInt(++i, stageId);
	    }
	    
	    if (minerOnly != null) {
	      pst.setBoolean(++i, minerOnly.booleanValue());
	    }

	    if (enteredBy > -1) {
	      pst.setInt(++i, enteredBy);
	    }
	    
	    if (tipologia > -1) {
	        pst.setInt(++i, tipologia);
	     }

	    if (name != null) {
	      pst.setString(++i, name.toLowerCase().replaceAll(" ", "%"));
	    }

	   

	    if (ownerId > -1) {
	      pst.setInt(++i, ownerId);
	    }

	    if (includeEnabled == TRUE) {
	      pst.setBoolean(++i, true);
	    } else if (includeEnabled == FALSE) {
	      pst.setBoolean(++i, false);
	    }

	    if (hasAlertDate) {
	      if (alertRangeStart != null) {
	        pst.setTimestamp(++i, alertRangeStart);
	      }
	      if (alertRangeEnd != null) {
	        pst.setTimestamp(++i, alertRangeEnd);
	      }
	    }

	    if (hasExpireDate) {
	      if (alertRangeStart != null) {
	        pst.setTimestamp(++i, alertRangeStart);
	      }
	      if (alertRangeEnd != null) {
	        pst.setTimestamp(++i, alertRangeEnd);
	      }
	    }
	    if (syncType == Constants.SYNC_INSERTS) {
	      if (lastAnchor != null) {
	        pst.setTimestamp(++i, lastAnchor);
	      }
	      pst.setTimestamp(++i, nextAnchor);
	    }
	    if (syncType == Constants.SYNC_UPDATES) {
	      pst.setTimestamp(++i, lastAnchor);
	      pst.setTimestamp(++i, lastAnchor);
	      pst.setTimestamp(++i, nextAnchor);
	    }

	    if (enteredSince != null) {
	      pst.setTimestamp(++i, enteredSince);
	    }
	    
	    if (enteredTo != null) {
	      pst.setTimestamp(++i, enteredTo);
	    }
	    
	    if (revenueOwnerId > -1) {
	      pst.setInt(++i, revenueOwnerId);
	    }

	    if (accountNumber != null) {
	      pst.setString(++i, accountNumber.toLowerCase());
	      pst.setString(++i, accountNumber.toLowerCase());
	    }
	    if (codiceFiscale != null) {
	        pst.setString(++i, codiceFiscale.toUpperCase());
	      }
	    if (!"".equals(partitaIva) && partitaIva!=null) {
	        pst.setString(++i, partitaIva.toLowerCase());
	      }
	    if ( ( nomeRappresentante != null) && !"".equals( nomeRappresentante ) ) {
	  	  pst.setString(++i, this.getNomeRappresentante());
	    }
	    if ( ( cognomeRappresentante != null) && !"".equals( cognomeRappresentante ) ) {
	  	  pst.setString(++i, this.getCognomeRappresentante());
	    }    
	   
	    if (importId != -1) {
	      pst.setInt(++i, importId);
	    }

	    if (statusId != -1) {
	      pst.setInt(++i, statusId);
	    }

	    if (firstName != null) {
	      pst.setString(++i, firstName.toLowerCase());
	    }

	    if (lastName != null) {
	      pst.setString(++i, lastName.toLowerCase());
	    }

	    if (contactPhoneNumber != null) {
	      pst.setString(++i, contactPhoneNumber.toLowerCase());
	    }

	    if (contactCity != null && !"-1".equals(contactCity)) {
	      pst.setString(++i, contactCity.toLowerCase());
	    }

	    if (contactState != null && !"-1".equals(contactState)) {
	      pst.setString(++i, contactState.toLowerCase());
	    }
	    if (contactCountry != null && !"-1".equals(contactCountry)) {
	      pst.setString(++i, contactCountry.toLowerCase());
	    }
	    if (excludeUnapprovedAccounts) {
	      pst.setInt(++i, Import.PROCESSED_APPROVED);
	    }

	    if (typeId > 0) {
	      pst.setInt(++i, typeId);
	    }

	    if (orgId > 0) {
	      pst.setInt(++i, orgId);
	    }
	    if (projectId > 0) {
	      pst.setInt(++i, projectId);
	    }
	    if (includeOnlyTrashed) {
	      // do nothing
	    } else if (trashedDate != null) {
	      pst.setTimestamp(++i, trashedDate);
	    } else {
	      // do nothing
	    }
	    if (postalCode != null) {
	      pst.setString(++i, postalCode.toLowerCase());
	    }
	    if (city != null && !"-1".equals(city)) {
	      pst.setString(++i, city.toLowerCase());
	    }
	    if (state != null && !"-1".equals(state)) {
	      pst.setString(++i, state.toLowerCase());
	    }
	    if (country != null && !"-1".equals(country)) {
	      pst.setString(++i, country.toLowerCase());
	    }
	    if (assetSerialNumber != null) {
	      pst.setString(++i, assetSerialNumber);
	    }
	    return i;
	  }
  
  
  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  baseFilePath   Description of the Parameter
   * @param  forceDelete    Description of the Parameter
   * @param  context        Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void delete(Connection db, ActionContext context, String baseFilePath, boolean forceDelete) throws SQLException {
    Iterator organizationIterator = this.iterator();
    while (organizationIterator.hasNext()) {
      Organization thisOrganization = (Organization) organizationIterator.next();
      thisOrganization.setContactDelete(true);
      thisOrganization.setRevenueDelete(true);
      thisOrganization.setDocumentDelete(true);
      thisOrganization.setForceDelete(forceDelete);
      thisOrganization.delete(db, context, baseFilePath);
    }
  }


  /**
   *  Gets the parent and leaf Accounts in the application Parent accounts are
   *  the list of root nodes in the organization hierarchies Leaf accounts are
   *  the list of leaf nodes in the organization hierarchies
   *
   * @param  db                Description of the Parameter
   * @param  typeId            Description of the Parameter
   * @param  reciprocal        Description of the Parameter
   * @return                   The parentAccounts value
   * @exception  SQLException  Description of the Exception
   */
  public static HashMap getParentAndLeafAccounts(Connection db, int typeId, boolean reciprocal) throws SQLException {
    HashMap allAccounts = new HashMap();
    HashMap leafAccounts = new HashMap();
    PreparedStatement pst = db.prepareStatement(
        "SELECT org_id FROM organization WHERE trashed_date IS NULL ");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      Integer accountId = new Integer(rs.getInt("org_id"));
      allAccounts.put(accountId, accountId);
      leafAccounts.put(accountId, accountId);
    }
    rs.close();
    pst.close();
    RelationshipList thisList = new RelationshipList();
    thisList.setCategoryIdMapsFrom(Constants.ACCOUNT_OBJECT);
    thisList.setCategoryIdMapsTo(Constants.ACCOUNT_OBJECT);
    thisList.setTypeId(typeId);
    thisList.buildList(db);
    Iterator iter = (Iterator) thisList.keySet().iterator();
    while (iter.hasNext()) {
      String relType = (String) iter.next();
      ArrayList tmpList = (ArrayList) thisList.get(relType);
      Iterator j = tmpList.iterator();
      while (j.hasNext()) {
        Relationship rel = (Relationship) j.next();
        //check for parent accounts to be in the child category
        if (allAccounts.get(new Integer(rel.getObjectIdMapsTo())) != null && reciprocal) {
          allAccounts.remove(new Integer(rel.getObjectIdMapsTo()));
        } else if (allAccounts.get(new Integer(rel.getObjectIdMapsFrom())) != null && !reciprocal) {
          allAccounts.remove(new Integer(rel.getObjectIdMapsFrom()));
        }
        //check for child accounts to be in the parent category
        if (leafAccounts.get(new Integer(rel.getObjectIdMapsFrom())) != null && reciprocal) {
          leafAccounts.remove(new Integer(rel.getObjectIdMapsFrom()));
        } else if (leafAccounts.get(new Integer(rel.getObjectIdMapsTo())) != null && !reciprocal) {
          leafAccounts.remove(new Integer(rel.getObjectIdMapsTo()));
        }
      }
    }
    HashMap combinedAccounts = new HashMap();
    combinedAccounts.put("parentNodes", allAccounts);
    combinedAccounts.put("leafNodes", leafAccounts);
    return combinedAccounts;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  childId           Description of the Parameter
   * @param  skipName          Description of the Parameter
   * @param  existingAccounts  Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */



  /**
   *  Gets the orgById attribute of the OrganizationList object This method
   *  assumes that the value of id is > 0
   *
   * @param  id  The value of id is always greater than 0.
   * @return     returns the matched organization or returns null
   */
  public Organization getOrgById(int id) {
    Organization result = null;
    Iterator iter = (Iterator) this.iterator();
    while (iter.hasNext()) {
      Organization org = (Organization) iter.next();
      if (org.getOrgId() == id) {
        result = org;
        break;
      }
    }
    return result;
  }


public int getTipologia() {
	return tipologia;
}


public void setTipologia(int tipologia) {
	this.tipologia = tipologia;
}

public boolean isIgnoraImportOpu() {
	return ignoraImportOpu;
}

public void setIgnoraImportOpu(boolean ignoraImportOpu) {
	this.ignoraImportOpu = ignoraImportOpu;
}


}

