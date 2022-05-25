/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.stampa_verbale_prelievo.base;


import com.darkhorseventures.framework.beans.GenericBean;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.distributori.base.Distrubutore;
import org.aspcfs.modules.punti_di_sbarco.base.OrganizationAddressList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupList;
import org.omg.CORBA.DATA_CONVERSION;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
/**
 * @author     chris
 * @created    July 12, 2001
 * @version    $Id: Organization.java,v 1.82.2.1 2004/07/26 20:46:39 kbhoopal
 * Exp $
 */
public class Organization extends GenericBean {
										 	
	//static final long serialVersionUID =-6915867402685037717L;

  /**
	 * 
	 */
	private static final long serialVersionUID = -566967668465298257L;
	private static Logger log = Logger.getLogger(org.aspcfs.modules.stampa_verbale_prelievo.base.Organization.class);
	static {
		if (System.getProperty("DEBUG") != null) {
			log.setLevel(Level.DEBUG);
    }
  }
  
  protected double YTD = 0;
  private String errorMessage = "";
  private int orgId = -1;
  private String name = "";
 
  private int siteId = -1;
  
  private boolean minerOnly = false;
  private int enteredBy = -1;
  private Vector comuni=new Vector();
 
  private String accountNumber = "";
  private String city = null;
  private String state = null;
  private String county = null;
  private int tipologia = -1;
  private String stato = null;
 
  private String tipologia_operatore = null;
  private String n_reg = null;
  private String targa = null;
  private String asl = null;
 
  private LookupList types = new LookupList();
 
  String partitaIva = null;
  private String codiceFiscale = null;
  private String tipologiaDesc = null;
  private Timestamp trashedDate = null;
  
  private OrganizationAddressList addressList = new OrganizationAddressList();
  
  public OrganizationAddressList getAddressList() {
	return addressList;
  }
  
  

  public static Logger getLog() {
	return log;
  }

  public Vector getComuni() {
	return comuni;
  }

  public void setComuni(Vector comuni) {
	this.comuni = comuni;
  }

  public String getCounty() {
	return county;
  }

  public void setCounty(String county) {
	this.county = county;
  }

  public static long getSerialVersionUID() {
	return serialVersionUID;
  }

  public boolean isMinerOnly() {
	return minerOnly;
  }

  public String getAccountNumber() {
	return accountNumber;
  }

  public String getCity() {
	return city;
  }

  public String getState() {
	return state;
  }
 
  public String getAsl() {
		return asl;
	}

   public void setAsl(String asl) {
		this.asl = asl;
	}
  

   public int getTipologia() {
		return tipologia;
	}

  public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}
 
  
   public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
	
	public String getTipologiaDesc() {
		return tipologiaDesc;
	}

	public void setTipologiaDesc(String desc) {
		this.tipologiaDesc = desc;
	}

	
	public String getN_reg() {
		return n_reg;
	}

	public void setN_reg(String n_reg) {
		this.n_reg = n_reg;
	}

	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}
  
	private ArrayList<Distrubutore> listaDistributori=new ArrayList<Distrubutore>();

	public ArrayList<Distrubutore> getListaDistributori() {
		return listaDistributori;
	}

	public void setListaDistributori(ArrayList<Distrubutore> listaDistributori) {
		this.listaDistributori = listaDistributori;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

  /**
   *  Sets the trashedDate attribute of the Organization object
   *
   * @param  tmp  The new trashedDate value
   */
  public void setTrashedDate(Timestamp tmp) {
    this.trashedDate = tmp;
  }

  /**
   *  Gets the trashedDate attribute of the Organization object
   *
   * @return    The trashedDate value
   */
  public Timestamp getTrashedDate() {
    return trashedDate;
  }

  public void setErrorMessage(String tmp) {
    this.errorMessage = tmp;
  }

  public void setTypes(LookupList types) {
    this.types = types;
  }

  /**
   *  Sets the YTD attribute of the Organization object
   *
   * @param  YTD  The new YTD value
   */
  public void setYTD(double YTD) {
    this.YTD = YTD;
  }


  /**
   *  Sets the AccountNumber attribute of the Organization obA  9
   *
   * @param  accountNumber  The new AccountNumber value
   */
  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }


  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the siteId attribute of the Organization object
   *
   * @param  siteId  The new siteId value
   */
  public void setSiteId(int siteId) {
    this.siteId = siteId;
  }


  /**
   *  Sets the siteId attribute of the Organization object
   *
   * @param  tmp  The new siteId value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }
  
  /**
   *  Gets the siteId attribute of the Organization object
   *
   * @return    The siteId value
   */
  public int getSiteId() {
    return siteId;
  }
 

  /**
   *  Sets the Miner_only attribute of the Organization object
   *
   * @param  tmp  The new Miner_only value
   */
  public void setMiner_only(String tmp) {
    this.minerOnly = ("true".equalsIgnoreCase(tmp) || "on".equalsIgnoreCase(
        tmp));
  }

  
  /**
   *  Sets the MinerOnly attribute of the Organization object
   *
   * @param  tmp  The new MinerOnly value
   */
  public void setMinerOnly(boolean tmp) {
    this.minerOnly = tmp;
  }


  /**
   *  Sets the MinerOnly attribute of the Organization object
   *
   * @param  tmp  The new MinerOnly value
   */
  public void setMinerOnly(String tmp) {
    this.minerOnly = ("true".equalsIgnoreCase(tmp) || "on".equalsIgnoreCase(
        tmp));
  }


  /**
   *  Sets the EnteredBy attribute of the Organization object
   *
   * @param  tmp  The new EnteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }

  /**
   *  Gets the YTD attribute of the Organization object
   *
   * @return    The YTD value
   */
  public double getYTD() {
    return YTD;
  }


  /**
   *  Gets the YTDValue attribute of the Organization object
   *
   * @return    The YTDValue value
   */
  public String getYTDValue() {
    double value_2dp = (double) Math.round(YTD * 100.0) / 100.0;
    String toReturn = String.valueOf(value_2dp);
    if (toReturn.endsWith(".0")) {
      toReturn = toReturn.substring(0, toReturn.length() - 2);
    }

    if (Integer.parseInt(toReturn) == 0) {
      toReturn = "";
    }

    return toReturn;
  }

  /**
   *  Gets the types attribute of the Organization object
   *
   * @return    The types value
   */
  public LookupList getTypes() {
    return types;
  }


  /**
   *  Gets the EnteredBy attribute of the Organization object
   *
   * @return    The EnteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


	public void setOrgId(int tmp) {
	    this.orgId = tmp;
	 }


	public void setOrgId(String tmp) {
	    this.orgId = Integer.parseInt(tmp);
	}

	public void setCity(String tmp) {
			this.city = tmp;
	}

	public void setState(String tmp) {
		this.state = tmp;
	}

  /**
   *  Gets the ErrorMessage attribute of the Organization object
   *
   * @return    The ErrorMessage value
   */
  public String getErrorMessage() {
    return errorMessage;
  }


  /**
   *  Gets the orgId attribute of the Organization object
   *
   * @return    The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Gets the id attribute of the Organization object
   *
   * @return    The id value
   */
  public int getId() {
    return orgId;
  }


  /**
   *  Gets the Name attribute of the Organization object
   *
   * @return    The Name value
   */
  public String getName() {
    if (name != null && name.trim().length() > 0) {
      return name;
    }
    return this.name;
  }


  /**
   *  Gets the accountNameOnly attribute of the Organization object
   *
   * @return    The accountNameOnly value
   */
  public String getAccountNameOnly() {
    return name;
  }

  public Organization(ResultSet rs) throws SQLException {
	    buildRecord(rs);
	    addressList.setOrgId(this.getOrgId());
  }
  
  public Organization() {
	// TODO Auto-generated constructor stub
}



protected void buildRecord(ResultSet rs) throws SQLException {
	    
	    this.setOrgId(rs.getInt("org_id"));
	    orgId = rs.getInt("org_id");
	    name = rs.getString("name");
	    asl = rs.getString("asl");
	    siteId = rs.getInt("site_id");
	    city = rs.getString("comune");
	    tipologia = rs.getInt("tipologia");
	    tipologiaDesc = rs.getString("tipologia_operatore");
	    codiceFiscale = rs.getString("codice_fiscale");
	    trashedDate = rs.getTimestamp("trashed_date");
} 

  public ResultSet queryGetOsa (Connection db, int orgId) throws SQLException  {
		
	  PreparedStatement pst = null;
	  ResultSet rs = null;
	  StringBuffer sqlSelect = new StringBuffer();
	
	  sqlSelect.append(" SELECT name, org_id, site_id " +
	        " FROM organization o WHERE o.trashed_date is null and o.org_id = ? ");
	  
	  pst = db.prepareStatement(sqlSelect.toString());
	  pst.setInt(1,orgId);
	  rs = pst.executeQuery();
	   
	  return rs;
  }
  
  
}