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
package org.aspcfs.modules.farmacosorveglianza.base;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.iteam.utils.ProjectUtils;
import com.zeroio.webdav.utils.ICalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.controller.ObjectValidator;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.actionplans.base.*;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.communications.base.RecipientList;
import org.aspcfs.modules.contacts.base.CallList;


import org.aspcfs.modules.farmacosorveglianza.actions.Farmacosorveglianza;
import org.aspcfs.modules.mycfs.base.OrganizationEventList;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.pipeline.base.OpportunityList;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.modules.servicecontracts.base.ServiceContractList;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;

import java.sql.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.Vector;

/**
 * @author     chris
 * @created    July 12, 2001
 * @version    $Id: Organization.java,v 1.82.2.1 2004/07/26 20:46:39 kbhoopal
 *      Exp $
 */
public class Prescrizioni extends GenericBean {

  private static Logger log = Logger.getLogger(org.aspcfs.modules.farmacosorveglianza.base.Prescrizioni.class);
  static {
    if (System.getProperty("DEBUG") != null) {
      log.setLevel(Level.DEBUG);
    }
  }
  
  protected double YTD = 0;
  private int idPrescrizione = -1;
  private int orgId = -1;
  private int idFarmacia = -1;
  private int idFarmaco1 = -1;
  private int idFarmaco2 = -1;
  private String veterinarioN = null;
  private String allevamentoN = null;
  private String veterinarioC = null;
  private String farmaciaN = null;
  private String farmaco1 = null;
  private String farmaco2 = null;
  private int idVeterinario = -1;
  private int quantitaFarmaco1 = -1;
  private int quantitaFarmaco2 = -1;
  private String note1 = null;
  private String note2 = null;
  private java.sql.Timestamp dataPrescrizione = null;
  private int enteredBy = -1;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private java.sql.Timestamp trashedDate = null;
  private int siteId = -1;
  private java.sql.Timestamp dataDispensazione = null;

  /**
   *  Constructor for the Organization object, creates an empty Organization
   *
   * @since    1.0
   */
  public Prescrizioni() { }

  private static Timestamp parseDate( String date )
  {
 	 Timestamp ret = null;
 	 
 	 SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
 	 try 
 	 {
			ret = new Timestamp( sdf.parse( date ).getTime() );
 	 }
 	 catch (ParseException e)
 	 {
			e.printStackTrace();
 	 }
 	 
 	 return ret;
  }

  /**
   *  Constructor for the Organization object
   *
   * @param  rs                Description of Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   * @throws  SQLException     Description of Exception
   */
  public Prescrizioni(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }
  
  public Prescrizioni(Connection db, int idPrescrizione) throws SQLException {
	
	  
    if (idPrescrizione == -1) {
      throw new SQLException("Invalid Account");
    } 
    PreparedStatement pst = db.prepareStatement(
    		
        "SELECT o.*, fcie.ragione_sociale, far1.nome_commerciale, far2.nome_commerciale, vet.cognome, vet.nome, al.name " +
        "FROM prescrizioni2 o " +
        "LEFT JOIN farmacie fcie ON (fcie.id_farmacia = o.id_farmacia) " +
        "LEFT JOIN farmaci2 far1 ON (far1.id_farmaco = o.id_farmaco1) " +
        "LEFT JOIN farmaci2 far2 ON (far2.id_farmaco = o.id_farmaco2) " +
        "LEFT JOIN veterinari2 vet ON (vet.id_veterinario = o.id_veterinario) " +
        "LEFT JOIN organization al ON (al.org_id = o.org_id) " +
        "WHERE o.id_prescrizione = ? " );
    pst.setInt(1, idPrescrizione);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst, log);
    if (rs.next()) {
      buildRecord(rs);
      veterinarioC = rs.getString(22);
      veterinarioN = rs.getString(23);
      farmaciaN = rs.getString(19);
      farmaco1 = rs.getString(20);
      farmaco2 = rs.getString(21);
      allevamentoN = rs.getString(24);
      
    }
    rs.close();
    pst.close();
    if (idPrescrizione == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }
     
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
  
  public int getId() {
	    return idVeterinario;
	  }
  public void setOrgId(int tmp) {
	    this.orgId = tmp;
	  }
  
  public void setVeterinarioN(String tmp) {
	    this.veterinarioN = tmp;
	  }

  public String getVeterinarioN() {
	    return veterinarioN;
	  }
  
  public void setAllevamentoN(String tmp) {
	    this.allevamentoN = tmp;
	  }

public String getAllevamentoN() {
	    return allevamentoN;
	  }


  public String getVeterinarioC() {
		    return veterinarioC;
		  }
  public void setVeterinarioC(String tmp) {
	    this.veterinarioC = tmp;
	  }
  
  public void setFarmaciaN(String tmp) {
	    this.farmaciaN = tmp;
	  }

public String getFarmaciaN() {
	    return farmaciaN;
	  }

public String getFarmaco1() {
		    return farmaco1;
		  }
public void setFarmaco1(String tmp) {
	    this.farmaco1 = tmp;
	  }
public String getFarmaco2() {
    return farmaco2;
  }
public void setFarmaco2(String tmp) {
this.farmaco2 = tmp;
}

  public void setOrgId(String tmp) {
	    this.setOrgId(Integer.parseInt(tmp));
	  }
  
  public int getOrgId() {
	    return orgId;
	  }
  
  public void setIdPrescrizione(int tmp) {
	    this.idPrescrizione = tmp;
	  }

public void setIdPrescrizione(String tmp) {
	    this.setIdPrescrizione(Integer.parseInt(tmp));
	  }

public int getIdPrescrizione() {
	    return idPrescrizione;
	  }

public void setQuantitaFarmaco1(int tmp) {
    this.quantitaFarmaco1 = tmp;
  }

public void setQuantitaFarmaco1(String tmp) {
    this.setQuantitaFarmaco1(Integer.parseInt(tmp));
  }

public int getQuantitaFarmaco1() {
    return quantitaFarmaco1;
  }

public void setQuantitaFarmaco2(int tmp) {
    this.quantitaFarmaco2 = tmp;
  }

public void setQuantitaFarmaco2(String tmp) {
    this.setQuantitaFarmaco2(Integer.parseInt(tmp));
  }


public int getIdFarmaco2() {
    return idFarmaco2;
  }

public int getQuantitaFarmaco2() {
    return quantitaFarmaco2;
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

  /**
   *  Sets the Modified attribute of the Organization object
   *
   * @param  tmp  The new Modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the entered attribute of the Organization object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }

  public void setDataPrescrizione(String tmp) {
	  this.dataPrescrizione = DateUtils.parseDateStringNew(tmp, "dd/MM/yyyy");
	  }
  
  public void setDataPrescrizione(java.sql.Timestamp tmp) {
	    this.dataPrescrizione = tmp;
	  }
  
  public void setDataDispensazione(String tmp) {
	  this.dataDispensazione = DateUtils.parseDateStringNew(tmp, "dd/MM/yyyy");
	  }

public void setDataDispensazione(java.sql.Timestamp tmp) {
	    this.dataDispensazione = tmp;
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
  public void setYTD(double YTD) {
    this.YTD = YTD;
  }

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
  
  public void setNote2(String tmp) {
	    this.note2 = tmp;
	  }


  public void setIdFarmaco1(int tmp) {
    this.idFarmaco1 = tmp;
  }

  public void setIdFarmaco2(int tmp) {
	    this.idFarmaco2 = tmp;
	  }
  
  public void setSiteId(int idTip) {
	    this.siteId = idTip;
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
  
  public void setIdFarmaco1(String tmp) {
	    this.idFarmaco1 = Integer.parseInt(tmp);
	  }
public int getIdFarmaco1() {
	    return idFarmaco1;
	  }
  
  public void setIdFarmacia(int tmp) {
	    this.idFarmacia = tmp;
	  }

	public void setIdFarmacia(String tmp) {
	    this.setIdFarmacia(Integer.parseInt(tmp));
	  }

	public int getIdFarmacia() {
	    return idFarmacia;
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

  public void setIdVeterinario(int tmp) {
	    this.idVeterinario = tmp;
	  }
  
  public void setIdVeterinario(String tmp) {
	    this.setIdVeterinario(Integer.parseInt(tmp));
	  }

  public int getIdVeterinario() {
	    return idVeterinario;
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


  /**
   *  Gets the Modified attribute of the Organization object
   *
   * @return    The Modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }

  public java.sql.Timestamp getDataPrescrizione() {
	    return dataPrescrizione;
	  }


	  
 public java.sql.Timestamp getDataDispensazione() {
	    return dataDispensazione;
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
  public String getNote2() {
    return note2;
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
    if (this.getIdVeterinario() == -1) {
      throw new SQLException("Prescrizione ID not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    boolean success = false;

    sql.append(
        "UPDATE prescrizioni2 set enabled = ? " +
        "WHERE id_prescrizione = ? ");

    sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setBoolean(++i, false);
    pst.setInt(++i, idPrescrizione);

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
    if (this.getIdVeterinario() == -1) {
      throw new SQLException("Prescrizione ID not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    boolean success = false;

    sql.append(
        "UPDATE prescrizioni2 SET enabled = ? " +
        "WHERE id_prescrizione = ? ");
    sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setBoolean(++i, true);
    pst.setInt(++i, idPrescrizione);
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
        "SELECT id_farmacia, id_farmaco1, id_farmaco2 " +
        "FROM prescrizioni2 " +
        "WHERE " + DatabaseUtils.toLowerCase(db) + "(prescrizioni.id_prescrizione) = ? ";
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
      
      idPrescrizione =  DatabaseUtils.getNextSeq(db,context, "prescrizioni2","id_prescrizione");

      sql.append(
          "INSERT INTO prescrizioni2 (");
      if (idPrescrizione > -1) {
        sql.append("id_prescrizione, ");
      }
      if (orgId > -1) {
          sql.append("org_id, ");
        }
      if (idFarmacia > -1) {
          sql.append("id_farmacia, ");
        }
      if (idFarmaco1 > -1) {
          sql.append("id_farmaco1, ");
        }
      if (idFarmaco2 > -1) {
          sql.append("id_farmaco2, ");
        }
      if (idVeterinario > -1) {
          sql.append("id_veterinario, ");
        }
      if (quantitaFarmaco1 > -1) {
          sql.append("quantita_farmaco1, ");
        }
      if (quantitaFarmaco2 > -1) {
          sql.append("quantita_farmaco2, ");
        }
      if (note1 != null) {
          sql.append("note1, ");
        }
      if (note2 != null) {
          sql.append("note2, ");
        }
      if (dataPrescrizione != null) {
          sql.append("data_prescrizione, ");
        }
      if (siteId > -1) {
          sql.append("site_id, ");
        }
      if (dataDispensazione != null) {
          sql.append("data_dispensazione, ");
        }
      if (entered != null) {
        sql.append("entered, ");
      }
      if (modified != null) {
        sql.append("modified, ");
      }
      
      sql.append("enteredBy, modifiedBy, trashed_date");
      
      sql.append(")");
      sql.append("VALUES (");
      if (idPrescrizione > -1) {
          sql.append("?, ");
        }
        if (orgId > -1) {
            sql.append("?, ");
          }
        if (idFarmacia > -1) {
            sql.append("?, ");
          }
        if (idFarmaco1 > -1) {
            sql.append("?, ");
          }
        if (idFarmaco2 > -1) {
            sql.append("?, ");
          }
        if (idVeterinario > -1) {
            sql.append("?, ");
          }
        if (quantitaFarmaco1 > -1) {
            sql.append("?, ");
          }
        if (quantitaFarmaco2 > -1) {
            sql.append("?, ");
          }
        if (note1 != null) {
            sql.append("?, ");
          }
        if (note2 != null) {
            sql.append("?, ");
          }
        if (dataPrescrizione != null) {
            sql.append("?, ");
          }
        if (siteId > -1) {
            sql.append("?, ");
          }
        if (dataDispensazione != null) {
            sql.append("?, ");
          }
        if (entered != null) {
          sql.append("?, ");
        }
        if (modified != null) {
          sql.append("?, ");
        }
        
        sql.append("?, ?, ?");
       
      
      sql.append(")");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      if (idPrescrizione > -1) {
        pst.setInt(++i, idPrescrizione);
      }
      if (orgId > -1) {
          pst.setInt(++i, orgId);
        }
      if (idFarmacia > -1) {
          pst.setInt(++i, idFarmacia);
        }
      if (idFarmaco1 > -1) {
          pst.setInt(++i, idFarmaco1);
        }
      if (idFarmaco2 > -1) {
          pst.setInt(++i, idFarmaco2);
        }
      if (idVeterinario > -1) {
          pst.setInt(++i, idVeterinario);
        }
      if (quantitaFarmaco1 > -1) {
          pst.setInt(++i, quantitaFarmaco1);
        }
      if (quantitaFarmaco2 > -1) {
          pst.setInt(++i, quantitaFarmaco2);
        }
      if (note1 != null) {
          pst.setString(++i, note1);
        }
      if (note2 != null) {
          pst.setString(++i, note2);
        }
     
      if (dataPrescrizione != null) {
      DatabaseUtils.setTimestamp(pst, ++i, this.getDataPrescrizione());
      }
      if (siteId > -1) {
          pst.setInt(++i, siteId);
          }
      if (dataDispensazione != null) {
          DatabaseUtils.setTimestamp(pst, ++i, this.getDataDispensazione());
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
      pst.execute();
      pst.close();
    
      //this.update(db, true);
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
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public int update(Connection db) throws SQLException {
    int i = -1;
    boolean doCommit = false;
    try {
      if (doCommit = db.getAutoCommit()) {
        db.setAutoCommit(false);
      }
      i = this.update(db, false);
      
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
        "UPDATE prescrizioni2 " +
        "SET org_id = ?, id_farmacia = ?, id_farmaco1 = ?, id_farmaco2 = ?, " +
        "id_veterinario = ?, quantita_farmaco1 = ?, quantita_farmaco2 = ?, note1 = ?, note2 = ?, data_prescrizione = ?, ");

    if (!override) {
      sql.append("modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }

    sql.append(
        "modifiedby = ?, " );

    sql.append(
        "trashed_date = ?, data_dispensazione = ?, site_id = ? ");
    
    sql.append("WHERE id_prescrizione = ? ");
    if (!override) {
      sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
    }

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, orgId);
    pst.setInt(++i, idFarmacia);
    pst.setInt(++i, idFarmaco1);
    pst.setInt(++i, idFarmaco2);
    pst.setInt(++i, idVeterinario);
    pst.setInt(++i, quantitaFarmaco1);
    pst.setInt(++i, quantitaFarmaco2);
    pst.setString(++i, note1);
    pst.setString(++i, note2);
    DatabaseUtils.setTimestamp(pst, ++i, this.getDataPrescrizione());
    pst.setInt(++i, this.getModifiedBy());
    DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
    DatabaseUtils.setTimestamp(pst, ++i, this.getDataDispensazione());
    DatabaseUtils.setInt(pst, ++i, siteId);
    pst.setInt(++i, idPrescrizione);
    
    if (!override && this.getModified() != null) {
      pst.setTimestamp(++i, this.getModified());
    }
 	  
    resultCount = pst.executeUpdate();
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
        "UPDATE Veterinari " +
        "SET nome = ? " +
        "WHERE id_Veterinario = 0 ");
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
    if (this.getIdVeterinario() == -1) {
      throw new SQLException("Veterinario ID not specified.");
    }
    boolean commit = db.getAutoCommit();
    try { 
      if (commit) {
        db.setAutoCommit(false);
      }
 
      Statement st = db.createStatement();
      st.executeUpdate(
          "DELETE FROM Veterinari WHERE id_Veterinario = " + this.getIdVeterinario());
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
          
          "DELETE FROM Veterinari " +
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
    out.append("ID: " + this.getIdPrescrizione() + "\r\n");
    return out.toString();
  }


  /**
   *  Description of the Method
   *
   * @param  rs             Description of Parameter
   * @throws  SQLException  Description of Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    
    this.setIdPrescrizione(rs.getInt("id_prescrizione"));
    orgId = rs.getInt("org_id");
    idFarmacia = rs.getInt("id_farmacia");
    idFarmaco1 = rs.getInt("id_farmaco1");
    idFarmaco2 = rs.getInt("id_farmaco2");
    idVeterinario = rs.getInt("id_veterinario");
    quantitaFarmaco1 = rs.getInt("quantita_farmaco1");
    quantitaFarmaco2 = rs.getInt("quantita_farmaco2");
    note1 = rs.getString("note1");
    note2 = rs.getString("note2");
    dataPrescrizione = rs.getTimestamp("data_prescrizione");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    trashedDate = rs.getTimestamp("trashed_date");
    siteId = DatabaseUtils.getInt(rs, "site_id");
    dataDispensazione = rs.getTimestamp("data_dispensazione");
    veterinarioC = rs.getString("cognome");
    veterinarioN = rs.getString("nome");
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
    if (idPrescrizione > -1) {
      description += "IdPrescrizione: " + idPrescrizione;
    }

    if (user != null && user.getContact() != null) {
      description += "\\nOwner: " + user.getContact().getNameFirstLast();
    }

    //write the event
    webcal.append("BEGIN:VEVENT" + CRLF);
    
    webcal.append("END:VEVENT" + CRLF);

    return webcal.toString();
  }


public void setIdFarmaco2(String tmp) {
	    this.idFarmaco2 = Integer.parseInt(tmp);
	  }

}