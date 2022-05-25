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
package org.aspcfs.modules.parafarmacie.base;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.modules.accounts.base.OrganizationHistoryList;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actionplans.base.ActionPlanWorkList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.parafarmacie.base.OrganizationAddressList;
import org.aspcfs.modules.parafarmacie.base.OrganizationAddress;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityList;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.modules.servicecontracts.base.ServiceContractList;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.contacts.base.CallList;


import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.text.DateFormat;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.Vector;

/**
 * @author     chris
 * @created    July 12, 2001
 * @version    $Id: Organization.java,v 1.82.2.1 2004/07/26 20:46:39 kbhoopal
 *      Exp $
 */

public class Organization extends GenericBean {
  private transient Logger logger = Logger.getLogger("MainLogger");

  private static Logger log = Logger.getLogger(org.aspcfs.modules.parafarmacie.base.Organization.class);
  static {
    if (System.getProperty("DEBUG") != null) {
      log.setLevel(Level.DEBUG);
    }
  }
  
  private String name	;
  	private static final int TIPOLOGIA_FARMACIE = 802 ;
  	private static final long serialVersionUID = 7127566929908851546L;

	private static final int INT = Types.INTEGER;

	private static final int STRING = Types.VARCHAR;

	private static final int DOUBLE = Types.DOUBLE;

	private static final int FLOAT = Types.FLOAT;

	private static final int TIMESTAMP = Types.TIMESTAMP;

	private static final int DATE = Types.DATE;

	private static final int BOOLEAN = Types.BOOLEAN;
  
  //protected double YTD = 0;
  private Vector comuni=new Vector();
  private OrganizationAddressList addressList = new OrganizationAddressList();

  private int tipologia ;
  private String banca;
  private int idFarmacia = -1;
  private String ragioneSociale = null;
  private String citta = null;
  private String provincia = null;
  private java.sql.Timestamp dataCambioStato = null;
  private String stato = null;
  private String indirizzo = null;
  private String numRicIngrosso = "";
  private java.sql.Timestamp dataRicIngrosso = null;
  private java.sql.Timestamp dataRicDettaglio = null;
  private String numRicDettaglio = "";
  private double latitudine = 0.0;
  private double longitudine = 0.0;
  private int siteId = -1;
  private int enteredBy = -1;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private java.sql.Timestamp trashedDate = null;
  private String note1 = null;
  private java.sql.Timestamp dataVerificaAnnua = null;
  private int orgId ;
  private int categoriaRischio ;
  private int accountSize = -1;
  private String ip_entered;
  private String ip_modified ;
  private Timestamp dataProssimoControllo;	
  private Timestamp date2;

   private int statoLab=-1;
   public int getStatoLab(){return this.statoLab;}
   public void setStatoLab(int stato){this.statoLab = stato;}
	
  
	public int getTipologia() {
	return tipologia;
}
	public String getBanca() {
	return banca;
}
	
	
	private Timestamp dataInizio;
	  
	  public Timestamp getDataInizio()
	  {
		  return this.dataInizio;
	  }
	  
	  public void setDataInizio(Timestamp ts)
	  {
		  this.dataInizio = ts;
	  }	
	

public void setTipologia(int tipologia) {
	this.tipologia = tipologia;
}

public void setBanca(String banca) {
	this.banca = banca;
}
	public Timestamp getDataProssimoControllo() {
	return dataProssimoControllo;
	}

	public void setDataProssimoControllo(Timestamp dataProssimoControllo) {
	this.dataProssimoControllo = dataProssimoControllo;
	}

	public String getIp_entered() {
		return ip_entered;
	}

	public void setIp_entered(String ip_entered) {
		this.ip_entered = ip_entered;
	}

	public String getIp_modified() {
		return ip_modified;
	}
	public void setIp_modified(String ip_modified) {
		this.ip_modified = ip_modified;
	}

  
  public int getAccountSize() {
	return accountSize;
}

  

  public String getName() {
	return name;
}



public void setName(String name) {
	this.name = name;
}



public void setAccountSize(String tmp) {
	    this.accountSize = Integer.parseInt(tmp);
	  }



/**
   *  Constructor for the Organization object, creates an empty Organization
   *
   * @since    1.0
   */
  public Organization() { }


  /**
   *  Constructor for the Organization object
   *
   * @param  rs                Description of Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   * @throws  SQLException     Description of Exception
   */
  public Organization(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }
  
  public int getId() {
	    return idFarmacia;
	  }
  
  
  public int getOrgId() {
	return orgId;
}


public void setOrgId(int orgId) {
	this.orgId = orgId;
}


public int getCategoriaRischio() {
	return categoriaRischio;
}


public void setCategoriaRischio(int categoriaRischio) {
	this.categoriaRischio = categoriaRischio;
}


public Organization(Connection db, int id_farmacia) throws SQLException {
	
	  
    if (orgId == -1) {
      throw new SQLException("Invalid Account");
    } 
    PreparedStatement pst = db.prepareStatement(
        "SELECT o.*, oa.* " +
        "FROM organization o left join  organization_address oa  on o.org_id = oa.org_id " +
        "WHERE  o.org_id = ? " );
    pst.setInt(1, id_farmacia);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst, log);
    if (rs.next()) {
      buildRecord(rs);
      
      addressList.setOrgId(this.getOrgId());
      addressList.buildList(db);
    }
    rs.close();
    pst.close();
    if (orgId == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }
     
  }
  

  public Vector getComuni () throws SQLException {
      
    return comuni;
   
  }

  public void setComuni (Connection db) throws SQLException {
		
	  	Statement st = null;
	    ResultSet rs = null;
	    StringBuffer sql = new StringBuffer();
	    
	    //sql.append("select comune from comuni where codiceistatasl= (select codiceistat from lookup_site_id where code= (select site_id from organization where org_id="+ this.getOrgId() + "))");
	    //if(codeUser==-1){
	        sql.append("select comune from comuni order by comune;");
	   /*}else{
	    sql.append("select comune from comuni where codiceistatasl= (select codiceistat from lookup_site_id where code= "+ codeUser + ")");
	    }*/
	    st = db.createStatement();
	    rs = st.executeQuery(sql.toString());
	    
	    while (rs.next()) {
	      comuni.add(rs.getString("comune"));
	    }
	    rs.close();
	    st.close();
	  
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
   *  Sets the trashedDate attribute of the Organization object
   *
   * @param  tmp  The new trashedDate value
   */
  public void setTrashedDate(String tmp) {
    this.trashedDate = DatabaseUtils.parseTimestamp(tmp);
  }

  /**
   *  Gets the trashedDate attribute of the Organization object
   *
   * @return    The trashedDate value
   */
  public Timestamp getTrashedDate() {
    return trashedDate;
  }
  /**
   *  Sets the Entered attribute of the Organization object
   *
   * @param  tmp  The new Entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }
  
  public void setDataCambioStato(java.sql.Timestamp tmp) {
	    this.dataCambioStato = tmp;
	  }
  public void setDataRicIngrosso(java.sql.Timestamp tmp) {
	    this.dataRicIngrosso = tmp;
	  }
  public void setDataRicDettaglio(java.sql.Timestamp tmp) {
	    this.dataRicDettaglio = tmp;
	  }
  public void setDataVerificaAnnua(java.sql.Timestamp tmp) {
	    this.dataVerificaAnnua = tmp;
	  }
  /**
   *  Sets the Modified attribute of the Organization object
   *
   * @param  tmp  The new Modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }

  public void setDataCambioStato(String tmp) {
	    this.dataCambioStato = DateUtils.parseDateStringNew(tmp, "dd/MM/yyyy");
	  }
public void setDataRicIngrosso(String tmp) {
	    this.dataRicIngrosso = DateUtils.parseDateStringNew(tmp, "dd/MM/yyyy");
	  }
public void setDataRicDettaglio(String tmp) {
	    this.dataRicDettaglio = DateUtils.parseDateStringNew(tmp, "dd/MM/yyyy");
	  }

public void setDataVerificaAnnua(String tmp) {
    this.dataVerificaAnnua = DateUtils.parseTimestampString(tmp);
  }

  /**
   *  Sets the entered attribute of the Organization object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the modified attribute of the Organization object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }

  /**
   *  Sets the YTD attribute of the Organization object
   *
   * @param  YTD  The new YTD value
   */
  /*public void setYTD(double YTD) {
    this.YTD = YTD;
  }*/

  /**
   *  Sets the ModifiedBy attribute of the Organization object
   *
   * @param  modifiedBy  The new ModifiedBy value
   */
  public void setModifiedBy(int modifiedBy) {
    this.modifiedBy = modifiedBy;
  }


  /**
   *  Sets the ModifiedBy attribute of the Organization object
   *
   * @param  modifiedBy  The new ModifiedBy value
   */
  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = Integer.parseInt(modifiedBy);
  }

  /**
   *  Sets the Notes attribute of the Organization object
   *
   * @param  tmp  The new Notes value
   */
  public void setNote1(String tmp) {
    this.note1 = tmp;
  }
  
  public void setNumRicDettaglio(String tmp) {
	    this.numRicDettaglio = tmp;
	  }
  
  public void setNumRicIngrosso(String tmp) {
	    this.numRicIngrosso = tmp;
	  }


  public void setIndirizzo(String tmp) {
    this.indirizzo = tmp;
  }


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
  public int getSiteId() {
	    return siteId;
	  }
  
  public void setRagioneSociale(String tmp) {
    this.ragioneSociale = tmp;
  }

  public void setCitta(String tmp) {
    this.citta = tmp;
  }
  
  public void setProvincia(String tmp) {
	    this.provincia = tmp;
	  }
  
  
	  
	  public void setStato(String tmp) {
		    this.stato = tmp;
		  }


 

  public void setLatitudine(double tmp) {
	    this.latitudine = tmp;
	  }
  
  public void setLatitudine(String tmp) {
	    this.latitudine = Double.parseDouble(tmp);
	  }
  
  public void setLongitudine(String tmp) {
	    this.longitudine = Double.parseDouble(tmp);
	  }
  
  public void setLongitudine(double tmp) {
	    this.longitudine = tmp;
	  }

  /**
   *  Sets the Enteredby attribute of the Organization object
   *
   * @param  tmp  The new Enteredby value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
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
  /*public double getYTD() {
    return YTD;
  }*/


  /**
   *  Gets the YTDValue attribute of the Organization object
   *
   * @return    The YTDValue value
   */
  /*public String getYTDValue() {
    double value_2dp = (double) Math.round(YTD * 100.0) / 100.0;
    String toReturn = String.valueOf(value_2dp);
    if (toReturn.endsWith(".0")) {
      toReturn = toReturn.substring(0, toReturn.length() - 2);
    }

    if (Integer.parseInt(toReturn) == 0) {
      toReturn = "";
    }

    return toReturn;
  }*/

  public void setIdFarmacia(int tmp) {
	    this.idFarmacia = tmp;
	    orgId = idFarmacia;
	  }
  
  public void setIdFarmacia(String tmp) {
	    this.setIdFarmacia(Integer.parseInt(tmp));
	  }

  public int getIdFarmacia() {
	    return idFarmacia;
	  }

  /**
   *  Gets the Enabled attribute of the Organization object
   *
   * @return    The Enabled value
   */
  public String getRagioneSociale() {
    return ragioneSociale;
  }

  /**
   *  Gets the types attribute of the Organization object
   *
   * @return    The types value
   */
  public String getIndirizzo() {
    return indirizzo;
  }

  /**
   *  Gets the alertText attribute of the Organization object
   *
   * @return    The alertText value
   */
  public String getCitta() {
    return citta;
  }
  
  public String getProvincia() {
	    return provincia;
	  }
  
 
	  public String getStato() {
		    return stato;
		  }

	
  public String getNumRicIngrosso() {
	    return numRicIngrosso;
	  }


  /**
   *  Gets the EnteredBy attribute of the Organization object
   *
   * @return    The EnteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the Entered attribute of the Organization object
   *
   * @return    The Entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }

  public java.sql.Timestamp getDataCambioStato() {
	    return dataCambioStato;
	  }
  public java.sql.Timestamp getDataRicDettaglio() {
	    return dataRicDettaglio;
	  }
  public java.sql.Timestamp getDataRicIngrosso() {
	    return dataRicIngrosso;
	  }

  public java.sql.Timestamp getDataVerificaAnnua() {
	    return dataVerificaAnnua;
	  }
  /**
   *  Gets the Modified attribute of the Organization object
   *
   * @return    The Modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the ModifiedString attribute of the Organization object
   *
   * @return    The ModifiedString value
   */
  public String getModifiedString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
          modified);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the EnteredString attribute of the Organization object
   *
   * @return    The EnteredString value
   */
  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
          entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the nameSalutation attribute of the Organization object
   *
   * @return    The nameSalutation value
   */
  public String getNote1() {
    return note1;
  }


  /**
   *  Gets the nameFirst attribute of the Organization object
   *
   * @return    The nameFirst value
   */
  public String getNumRicDettaglio() {
    return numRicDettaglio;
  }

  /**
   *  Gets the Industry attribute of the Organization object
   *
   * @return    The quantita' value
   */
  public double getLatitudine() {
    return latitudine;
  }
  
  public double getLongitudine() {
	    return longitudine;
	  }

  /**
   *  Gets the Enteredby attribute of the Organization object
   *
   * @return    The Enteredby value
   */
  public int getEnteredby() {
    return enteredBy;
  }


  /**
   *  Gets the ModifiedBy attribute of the Organization object
   *
   * @return    The ModifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }

  /**
   *  Gets the trashed attribute of the Organization object
   *
   * @return    The trashed value
   */
  public boolean isTrashed() {
    return (trashedDate != null);
  }

 
  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean disable(Connection db) throws SQLException {
    if (this.getIdFarmacia() == -1) {
      throw new SQLException("Farmaco ID not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    boolean success = false;

    sql.append(
        "UPDATE farmaci set enabled = ? " +
        "WHERE id_farmacia = ? ");

    sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setBoolean(++i, false);
    pst.setInt(++i, idFarmacia);

    if(this.getModified() != null){
      pst.setTimestamp(++i, this.getModified());
    }

    int resultCount = pst.executeUpdate();
    pst.close();

    if (resultCount == 1) {
      success = true;
    }

    return success;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean enable(Connection db) throws SQLException {
    if (this.getIdFarmacia() == -1) {
      throw new SQLException("Farmaco ID not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    boolean success = false;

    sql.append(
        "UPDATE farmaci SET enabled = ? " +
        "WHERE id_farmacia = ? ");
    sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setBoolean(++i, true);
    pst.setInt(++i, idFarmacia);
    if(this.getModified() != null){
      pst.setTimestamp(++i, this.getModified());
    }
    int resultCount = pst.executeUpdate();
    pst.close();
    if (resultCount == 1) {
      success = true;
    }
    return success;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @param  checkName      Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean checkIfExists(Connection db, String checkName) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sqlSelect =
        "SELECT ragione_sociale, id_farmacia " +
        "FROM farmacie " +
        "WHERE " + DatabaseUtils.toLowerCase(db) + "(farmacie.ragione_sociale) = ? ";
    int i = 0;
    pst = db.prepareStatement(sqlSelect);
    pst.setString(++i, checkName.toLowerCase());
    rs = pst.executeQuery();
    if (rs.next()) {
      rs.close();
      pst.close();
      return true;
    } else {
      rs.close();
      pst.close();
      return false;
    }
  }
  
  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean insert(Connection db,ActionContext context) throws SQLException {
    StringBuffer sql = new StringBuffer();
    boolean doCommit = false;
    try {
      modifiedBy = enteredBy;
      
      if (doCommit = db.getAutoCommit()) {
        db.setAutoCommit(false);
      }
      orgId = DatabaseUtils.getNextSeqInt(db,context, "organization","org_id");
      
      //idFarmacia = DatabaseUtils.getNextSeq(db, "farmacia_id_seq");
      sql.append(
          "INSERT INTO organization (");
      if (orgId > -1) {
        sql.append("org_id, ");
      }
      if (name != null) {
          sql.append("name, ");
        }
      if (banca != null) {
          sql.append("banca, ");
        }
      if (siteId > -1) {
          sql.append("site_id, ");
        }
      if (indirizzo != null) {
          sql.append("indirizzo, ");
        }
      if (citta != null) {
          sql.append("citta, ");
        }
      if (provincia != null) {
          sql.append("provincia, ");
        }
      
      if (numRicDettaglio != null) {
          sql.append("num_ric_dettaglio, ");
        }
      if (numRicIngrosso != null) {
          sql.append("num_ric_ingrosso, ");
        }
      if (dataCambioStato != null) {
          sql.append("data_cambio_stato, ");
        }
      if (dataRicDettaglio != null) {
          sql.append("data_ric_dettaglio, ");
        }
      if (dataRicIngrosso != null) {
          sql.append("data_ric_ingrosso, ");
        }
      if (dataVerificaAnnua != null) {
          sql.append("data_verifica_annua, ");
        }
      if (stato != null) {
          sql.append("stato, ");
        }
      
      if (note1 != null) {
          sql.append("note1, ");
        }
      if (entered != null) {
        sql.append("entered, ");
      }
      if (modified != null) {
        sql.append("modified, ");
      }
      
      sql.append("enteredby, modifiedby, trashed_date, pregresso,tipologia,ip_entered,ip_modified ");
      
      sql.append(")");
      sql.append(" VALUES (");
      if (orgId > -1) {
        sql.append("?,");
      }
      if (name != null) {
          sql.append("?, ");
        }
      if (banca != null) {
          sql.append("?, ");
        }
      if (siteId > -1) {
          sql.append("?, ");
        }
      if (indirizzo != null) {
          sql.append("?, ");
        }
      if (citta != null) {
          sql.append("?, ");
        }
      if (provincia != null) {
          sql.append("?, ");
        }
      

      if (numRicDettaglio != null) {
          sql.append("?, ");
        }
      if (numRicIngrosso != null) {
          sql.append("?, ");
        }
      if (dataCambioStato != null) {
          sql.append("?, ");
        }
      if (dataRicDettaglio != null) {
          sql.append("?, ");
        }
      if (dataRicIngrosso != null) {
          sql.append("?, ");
        }
      if (dataVerificaAnnua != null) {
          sql.append("?, ");
        }
     
      if (stato != null) {
          sql.append("?, ");
        }
     
      if (note1 != null) {
          sql.append("?, ");
        }
      
      
      
      if (entered != null) {
        sql.append("?, ");
      }
      if (modified != null) {
        sql.append("?, ");
      }
      
      sql.append("?, ?, ?, ?,"+TIPOLOGIA_FARMACIE + ",?,?)");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      if (orgId > -1) {
        pst.setInt(++i, orgId);
      }
      if (name != null) {
          pst.setString(++i, name);
        }
      if (banca != null) {
          pst.setString(++i, banca);
        }
      if (siteId > -1) {
          pst.setInt(++i, siteId);
        }
      if (indirizzo != null) {
          pst.setString(++i, indirizzo);
        }
      if (citta != null) {
          pst.setString(++i, citta);
        }
      if (provincia != null) {
          pst.setString(++i, provincia);
        }
      if (numRicDettaglio != null) {
    	  pst.setString(++i,numRicDettaglio);
        }
      if (numRicIngrosso != null) {
    	  pst.setString(++i,numRicIngrosso);
        }
      if (dataCambioStato != null) {
    	  pst.setTimestamp(++i,dataCambioStato);
        }
      if (dataRicDettaglio != null) {
    	  pst.setTimestamp(++i,dataRicDettaglio);
        }
      if (dataRicIngrosso != null) {
    	  pst.setTimestamp(++i,dataRicIngrosso);
        }
      if (dataVerificaAnnua != null) {
    	  pst.setTimestamp(++i,dataVerificaAnnua);
        }
      
      if (stato != null) {
          pst.setString(++i, stato);
        }
      
      if (note1 != null) {
          pst.setString(++i, note1);
        }
     
      if (entered != null) {
        pst.setTimestamp(++i, entered);
      }
      if (modified != null) {
        pst.setTimestamp(++i, modified);
      }
      pst.setInt(++i, this.getModifiedBy());
      pst.setInt(++i, this.getModifiedBy());
      DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
      pst.setBoolean(++i, false);
      pst.setString(++i, ip_entered);
      pst.setString(++i, ip_modified);
      pst.execute();
      pst.close();
      
     
      Iterator iaddress = getAddressList().iterator();
      while (iaddress.hasNext()) {
    	  
          OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
          //thisAddress.insert(db, this.getOrgId(), this.getEnteredBy());
          
          
          if((thisAddress.getCity()!=null) && !(thisAddress.getCity().equals(""))) {
              thisAddress.process(
                  db, orgId, this.getEnteredBy(), this.getModifiedBy(),context);
              }
        }
      
      
      //this.update(db, true);
      if (doCommit) {
        db.commit();
      }
    } catch (SQLException e) {
    	e.printStackTrace();
      if (doCommit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (doCommit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }

  public static Vector<Organization> load( String ragione_sociale, String citta,
			int asl, Connection db )
	{
		Vector<Organization>	ret		= new Vector<Organization>();
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		ragione_sociale	= (ragione_sociale == null)	? ("") : (ragione_sociale.trim());
		citta		= (citta == null)		? ("") : (citta.trim());
		asl	= (asl == -2)	? (-2) : (asl);
		
		String sql = "SELECT * FROM farmacie ";
		
		try
		{
			boolean where = false;
			
			if( ragione_sociale.length() > 0 )
			{
				sql += ( " WHERE ragione_sociale ILIKE '%" + ragione_sociale.replaceAll( "'", "''" ) + "%' " );
				where = true;
			}
			if( citta.length() > 0 )
			{
				if(!where)
				{
					sql += " WHERE ";
				}
				else
				{
					sql += " AND ";
				}
				sql += ( " citta ILIKE '%" + citta.replaceAll( "'", "''" ) + "%' " );
			}
			if( asl > -1 )
			{
				if(!where)
				{
					sql += " WHERE ";
				}
				else
				{
					sql += " AND ";
				}
				sql += ( " site_id =" + asl + " " );
			}
			
			
			stat = db.prepareStatement( sql );
			
			res		= stat.executeQuery();
			while( res.next() )
			{
				ret.add( loadResultSet( res ) );
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if( res != null )
				{
					res.close();
					res = null;
				}
				
				if( stat != null )
				{
					stat.close();
					stat = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return ret;
	}
  
  private static Organization loadResultSet( ResultSet res ) 
	throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
{
	  Organization ret = new Organization();
	
   Field[]	f = ret.getClass().getDeclaredFields();
   Method[]	m = ret.getClass().getMethods();
   for( int i = 0; i < f.length; i++ )
   {
  	 Method getter	= null;
  	 Method setter	= null;
  	 Field	campo	= f[i];
       String field = f[i].getName();
       for( int j = 0; j < m.length; j++ )
       {
           String met = m[j].getName();
           if( met.equalsIgnoreCase( "GET" + field ) || met.equalsIgnoreCase( "IS" + field ) )
           {
                getter = m[j];
           }
           else if( met.equalsIgnoreCase( "SET" + field ) )
           {
               setter = m[j];
           }
       }
       
       
       if( (getter != null) && (setter != null) && (campo != null) )
       {
      	 Object o = null;
           
           switch ( parseType( campo.getType() ) )
           {
           case INT:
               o = res.getInt( field );
               break;
           case STRING:
               o = res.getString( field );
               break;
           case BOOLEAN:
               o = res.getBoolean( field );
               break;
           case TIMESTAMP:
               o = res.getTimestamp( field );
               break;
           case DATE:
               o = res.getDate( field );
               break;
           case FLOAT:
               o = res.getFloat( field );
               break;
           case DOUBLE:
               o = res.getDouble( field );
               break;
           }
           
           setter.invoke( ret, o );
           
       }
   }
	
	return ret;
}
  
  protected static int parseType(Class<?> type)
  {
      int ret = -1;
      
      String name = type.getSimpleName();
      
      if( name.equalsIgnoreCase( "int" ) || name.equalsIgnoreCase("integer") )
      {
          ret = INT;
      }
      else if( name.equalsIgnoreCase( "string" ) )
      {
          ret = STRING;
      }
      else if( name.equalsIgnoreCase( "double" ) )
      {
          ret = DOUBLE;
      }
      else if( name.equalsIgnoreCase( "float" ) )
      {
          ret = FLOAT;
      }
      else if( name.equalsIgnoreCase( "timestamp" ) )
      {
          ret = TIMESTAMP;
      }
      else if( name.equalsIgnoreCase( "date" ) )
      {
          ret = DATE;
      }
      else if( name.equalsIgnoreCase( "boolean" ) )
      {
          ret = BOOLEAN;
      }
      
      return ret;
  }	

  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public int update(Connection db,ActionContext context) throws SQLException {
    int i = -1;
    boolean doCommit = false;
    try {
      if (doCommit = db.getAutoCommit()) {
        db.setAutoCommit(false);
      }
      i = this.update(db, false);
      
      Iterator iaddress = getAddressList().iterator();
      while (iaddress.hasNext()) {
        OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
        //thisAddress.insert(db, this.getOrgId(), this.getEnteredBy());
        
        //Solo se la provincia viene selezionata allora avviene il salvataggio       
        if(thisAddress.getCity()!=null ) {
        thisAddress.process(
            db, orgId, this.getEnteredBy(), this.getModifiedBy(),context);
        }
        
      }
      
      if (doCommit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (doCommit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (doCommit) {
        db.setAutoCommit(true);
      }
    }
    return i;
  }
  public OrganizationAddressList getAddressList() {
	    return addressList;
	  }

  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @param  override       Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public int update(Connection db, boolean override) throws SQLException {
    int resultCount = 0;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE organization " +
        "SET name = ?, site_id = ?, " +
         "stato =?,  note1 = ?, " +
        "data_cambio_stato = ?, num_ric_ingrosso = ?, data_ric_ingrosso = ?, num_ric_dettaglio =?, " +
        "data_ric_dettaglio =?, data_verifica_annua=?,ip_modified = ? , ");
   
    if (!override) {
      sql.append("modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }

    sql.append(
        "modifiedby = ?" );

   
    
    sql.append("WHERE org_id = ? ");
  
    //"indirizzo = ?, citta = ?,provincia = ?, latitude = ?, longitude = ?,
    
    
   /* Iterator iaddress = getAddressList().iterator();
    while (iaddress.hasNext()) {
      OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
      //thisAddress.insert(db, this.getOrgId(), this.getEnteredBy());
      
      //Solo se la provincia viene selezionata allora avviene il salvataggio       
      if(thisAddress.getCity()!=null ) {
      thisAddress.process(
          db, orgId, this.getEnteredBy(), this.getModifiedBy());
      }
      
    }*/
    
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, ragioneSociale);
    pst.setInt(++i, siteId);
    
    pst.setString(++i, stato);
  
   
    pst.setString(++i, note1);
    DatabaseUtils.setTimestamp(pst, ++i,dataCambioStato);
    pst.setString(++i, numRicIngrosso);  
    DatabaseUtils.setTimestamp(pst, ++i, this.getDataRicIngrosso());
    pst.setString(++i, numRicDettaglio);  
    DatabaseUtils.setTimestamp(pst, ++i, this.getDataRicDettaglio());
    DatabaseUtils.setTimestamp(pst, ++i, this.getDataVerificaAnnua());
    pst.setString(++i, ip_modified);
    pst.setInt(++i, this.getModifiedBy());
    
    pst.setInt(++i, idFarmacia);
    
   
 	  
    resultCount=pst.executeUpdate();
    pst.close();

    return resultCount;
  }


  /**
   *  Renames the organization running this database
   *
   * @param  db             Description of the Parameter
   * @param  newName        Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public static void renameMyCompany(Connection db, String newName) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE farmacie " +
        "SET ragione_sociale = ? " +
        "WHERE id_farmacia = 0 ");
    pst.setString(1, newName);
    pst.execute();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @param  baseFilePath   Description of Parameter
   * @param  context        Description of the Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public boolean delete(Connection db, ActionContext context, String baseFilePath) throws SQLException {
    if (this.getIdFarmacia() == -1) {
      throw new SQLException("Farmacia ID not specified.");
    }
    boolean commit = db.getAutoCommit();
    try { 
      if (commit) {
        db.setAutoCommit(false);
      }
 
      Statement st = db.createStatement();
      st.executeUpdate(
          "DELETE FROM farmacie WHERE id_farmacia = " + this.getIdFarmacia());
      st.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace(System.out);
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }
  
  public void setRequestItems(ActionContext context) {
	   
	    addressList = new OrganizationAddressList(context.getRequest());
	 
	  }

  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  thisImportId   Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public static boolean deleteImportedRecords(Connection db, int thisImportId) throws SQLException {
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst = db.prepareStatement(
          
          "DELETE FROM farmacie " +
          "WHERE import_id = ?");
      pst.setInt(1, thisImportId);
      pst.executeUpdate();
      pst.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }
  
  /**
   *  Description of the Method
   *
   * @return    Description of the Returned Value
   */
  public String toString() {
    StringBuffer out = new StringBuffer();
    out.append("=================================================\r\n");
    out.append("Farmacia Nome: " + this.getRagioneSociale() + "\r\n");
    out.append("ID: " + this.getIdFarmacia() + "\r\n");
    return out.toString();
  }


  /**
   *  Description of the Method
   *
   * @param  rs             Description of Parameter
   * @throws  SQLException  Description of Exception
   */ 
  protected void buildRecord(ResultSet rs) throws SQLException {
     
    this.setIdFarmacia(rs.getInt("org_id"));
    ragioneSociale = rs.getString("name");
    this.tipologia=rs.getInt("tipologia"); //prima era getInt banca (???)
    this.banca=rs.getString("banca"); //prima non c'era (???)
    siteId = rs.getInt("site_id");
    indirizzo = rs.getString("addrline1");
    citta = rs.getString("city");
    stato = rs.getString("stato");
    provincia = rs.getString("state"); 
    latitudine = rs.getDouble("latitude");
    longitudine = rs.getDouble("longitude");
    note1 = rs.getString("note1");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    trashedDate = rs.getTimestamp("trashed_date");
    statoLab = DatabaseUtils.getInt(rs, "stato_lab");
    orgId = idFarmacia;
    name = ragioneSociale;
    numRicDettaglio = rs.getString("num_ric_dettaglio");
    numRicIngrosso = rs.getString("num_ric_ingrosso");
    dataCambioStato = rs.getTimestamp("data_cambio_stato");
    dataRicDettaglio = rs.getTimestamp("data_ric_dettaglio");
    dataRicIngrosso = rs.getTimestamp("data_ric_ingrosso");
    dataVerificaAnnua = rs.getTimestamp("data_verifica_annua");    
    categoriaRischio  = rs.getInt("categoria_rischio");
    if((Integer)categoriaRischio==null)
    {
    	categoriaRischio = 3 ;
    }
    dataProssimoControllo = rs.getTimestamp("prossimo_controllo");
    
    try
    {
    	setDataInizio(rs.getTimestamp("data_inizio"));
    }
    catch(Exception ex)
    {
    	
    }
  }

  public boolean updateStatus(Connection db, ActionContext context, boolean toTrash, int tmpUserId) throws SQLException {
	    int count = 0;

	    boolean commit = true;

	    try {
	      commit = db.getAutoCommit();
	      if (commit) {
	        db.setAutoCommit(false);
	      }
	      StringBuffer sql = new StringBuffer();
	      sql.append(
	          "UPDATE organization " +
	          "SET trashed_date = ?, " +
	          "modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , " +
	          "modifiedby = ? " +
	          "WHERE org_id = ? ");
	      int i = 0;
	      PreparedStatement pst = db.prepareStatement(sql.toString());
	      if (toTrash) {
	        DatabaseUtils.setTimestamp(
	            pst, ++i, new Timestamp(System.currentTimeMillis()));
	      } else {
	        DatabaseUtils.setTimestamp(pst, ++i, (Timestamp) null);
	      }
	      pst.setInt(++i, tmpUserId);
	      pst.setInt(++i, this.getId());
	      count = pst.executeUpdate();
	      pst.close();

	      if (commit) {
	        db.commit();
	      }
	    } catch (SQLException e) {
	      e.printStackTrace();
	      if (commit) {
	        db.rollback();
	      }
	      throw new SQLException(e.getMessage());
	    } finally {
	      if (commit) {
	        db.setAutoCommit(true);
	      }
	    }
	    return true;
	  }

  
  public static int getOrganizationSiteId(Connection db, int tmpOrgId) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    int organizationSiteId = -1;
	    String sqlSelect =
	        "SELECT site_id " +
	        "FROM organization " +
	        "WHERE org_id = ? ";
	    int i = 0;
	    pst = db.prepareStatement(sqlSelect);
	    pst.setInt(++i, tmpOrgId);
	    rs = pst.executeQuery();
	    if (rs.next()) {
	      organizationSiteId = DatabaseUtils.getInt(rs, "site_id");
	    }
	    rs.close();
	    pst.close();
	    return organizationSiteId;
	  }

  /**
   *  Description of the Method
   *
   * @param  tz         Description of the Parameter
   * @param  created    Description of the Parameter
   * @param  alertDate  Description of the Parameter
   * @param  user       Description of the Parameter
   * @param  category   Description of the Parameter
   * @param  type       Description of the Parameter
   * @return            Description of the Return Value
   */
  public String generateWebcalEvent(TimeZone tz, Timestamp created, Timestamp alertDate,
      User user, String category, int type) {

    StringBuffer webcal = new StringBuffer();
    String CRLF = System.getProperty("line.separator");

    String description = "";
    if (ragioneSociale != null) {
      description += "Nome: " + ragioneSociale;
    }

    if (user != null && user.getContact() != null) {
      description += "\\nOwner: " + user.getContact().getNameFirstLast();
    }

    //write the event
    webcal.append("BEGIN:VEVENT" + CRLF);
    
    webcal.append("END:VEVENT" + CRLF);

    return webcal.toString();
  }

//  public void cessazioneAttivita(Connection db,String noteCessazione,int userId) throws SQLException
//	{
//	  PreparedStatement pst = db.prepareStatement("UPDATE organization set modified=current_timestamp,modifiedby=?,date2 = ? ,stato_lab=4,data_cessazione_attivita=?,note_cessazione_attivita=? , cessato = 1, stato = 'Cessato', data_cambio_stato = CURRENT_TIMESTAMP where org_id=?");
//		pst.setInt(1, userId);
//		pst.setTimestamp(2, date2);
//		pst.setTimestamp(3, date2);
//		pst.setString(4, noteCessazione);
//		pst.setInt(5, this.orgId);
//		pst.execute();
//		
//	}
  
  public void setDate2(String tmp) {
		this.date2 = DatabaseUtils.parseDateToTimestamp(tmp);
	}
  public void SetDate2(Timestamp date2){this.date2 = date2;}
  public Timestamp getDate2(){return this.date2;}
}