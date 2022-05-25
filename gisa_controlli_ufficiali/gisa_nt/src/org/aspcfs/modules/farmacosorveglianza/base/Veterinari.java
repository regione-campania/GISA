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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
public class Veterinari extends GenericBean {

  private static Logger log = Logger.getLogger(org.aspcfs.modules.farmacosorveglianza.base.Veterinari.class);
  static {
    if (System.getProperty("DEBUG") != null) {
      log.setLevel(Level.DEBUG);
    }
  }
  protected double YTD = 0;
  private int idVeterinario = -1;
  private String nome = null;
  private String cognome = null;
  private String codiceFiscale = null;
  private String indirizzo = null;
  private String citta = null;
  private String provincia = null;
  private String postalCode = null;
  private String note1 = null;
  private String note2 = null;
  private int tipologiaId = -1;
  private int siteId = -1;
  private int enteredBy = -1;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private Timestamp trashedDate = null;

  /**
   *  Constructor for the Organization object, creates an empty Organization
   *
   * @since    1.0
   */
  public Veterinari() { }

  public void setIndirizzo(String tmp) {
	    this.indirizzo = tmp;
	  }
  /**
   *  Constructor for the Organization object
   *
   * @param  rs                Description of Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   * @throws  SQLException     Description of Exception
   */
  public Veterinari(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }
  
  public Veterinari(Connection db, int id_veterinario) throws SQLException {
	
	  
    if (id_veterinario == -1) {
      throw new SQLException("Invalid Account");
    } 
    PreparedStatement pst = db.prepareStatement(
        "SELECT o.* " +
        "FROM veterinari2 o " +
        "WHERE o.id_veterinario = ? " );
    pst.setInt(1, id_veterinario);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst, log);
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (idVeterinario == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }
     
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
  public void setCitta(String tmp) {
	    this.citta = tmp;
	  }
	  
	  public void setProvincia(String tmp) {
		    this.provincia = tmp;
		  }

	  public void setPostalCode(String tmp) {
		    this.postalCode = tmp;
		  }
	  
	  public void setNome(String tmp) {
		    this.nome = tmp;
		  }

	  public void setCognome(String tmp) {
		    this.cognome = tmp;
		  }
	  
	  public void setCodiceFiscale(String tmp) {
		    this.codiceFiscale = tmp;
		  }
	  
	  public String getPostalCode() {
		    return postalCode;
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

  public int getId() {
	    return idVeterinario;
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
   *  Gets the Enabled attribute of the Organization object
   *
   * @return    The Enabled value
   */
  public String getNome() {
    return nome;
  }

  public String getCognome() {
	    return cognome;
	  }
  /**
   *  Gets the types attribute of the Organization object
   *
   * @return    The types value
   */
  public String getCodiceFiscale() {
    return codiceFiscale;
  }

  /**
   *  Gets the alertText attribute of the Organization object
   *
   * @return    The alertText value
   */

  public void setTipologiaId(int siteId) {
	    this.tipologiaId = tipologiaId;
	  }


	  /**
	   *  Sets the siteId attribute of the Organization object
	   *
	   * @param  tmp  The new siteId value
	   */
  public void setTipologiaId(String tmp) {
	    this.tipologiaId = Integer.parseInt(tmp);
	  }
  public int getTipologiaId() {
	    return tipologiaId;
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
      throw new SQLException("Veterinario ID not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    boolean success = false;

    sql.append(
        "UPDATE veterinari2 set enabled = ? " +
        "WHERE id_veterinario = ? ");

    sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setBoolean(++i, false);
    pst.setInt(++i, idVeterinario);

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
      throw new SQLException("Veterinario ID not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    boolean success = false;

    sql.append(
        "UPDATE veterinari2 SET enabled = ? " +
        "WHERE id_veterinario = ? ");
    sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setBoolean(++i, true);
    pst.setInt(++i, idVeterinario);
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
        "SELECT cognome, id_veterinario " +
        "FROM veterinari2 " +
        "WHERE " + DatabaseUtils.toLowerCase(db) + "(veterinari2.cognome) = ? ";
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
      
      idVeterinario =  DatabaseUtils.getNextSeq(db,context, "veterinari2","id_veterinario");

      sql.append(
          "INSERT INTO veterinari2 (");
      if (idVeterinario > -1) {
        sql.append("id_veterinario, ");
      }
      if (nome != null) {
          sql.append("nome, ");
        }
      if (cognome != null) {
          sql.append("cognome, ");
        }
      if (codiceFiscale != null) {
          sql.append("codice_fiscale, ");
        }
      if (tipologiaId > -1) {
          sql.append("id_tipologia, ");
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
      
      if (postalCode != null) {
          sql.append("postalcode, ");
        }
      if (note1 != null) {
          sql.append("note1, ");
        }
      if (note2 != null) {
          sql.append("note2, ");
        }
      if (siteId > -1) {
          sql.append("site_id, ");
        }
      if (entered != null) {
        sql.append("entered, ");
      }
      if (modified != null) {
        sql.append("modified, ");
      }
      
      sql.append("enteredby, modifiedby, trashed_date");
      
      sql.append(")");
      sql.append(" VALUES (");
      if (idVeterinario > -1) {
        sql.append("?,");
      }
      if (nome != null) {
          sql.append("?, ");
        }
      if (cognome != null) {
          sql.append("?, ");
        }
      if (codiceFiscale != null) {
          sql.append("?, ");
        }
      if (tipologiaId > -1) {
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
      
      if (postalCode != null) {
          sql.append("?, ");
        }
      if (note1 != null) {
          sql.append("?, ");
        }
      if (note2 != null) {
          sql.append("?, ");
        }
      if (siteId > -1) {
          sql.append("?, ");
        }
      if (entered != null) {
        sql.append("?, ");
      }
      if (modified != null) {
        sql.append("?, ");
      }
      
      sql.append("?, ?, ?)");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      if (idVeterinario > -1) {
        pst.setInt(++i, idVeterinario);
      }
      if (nome != null) {
          pst.setString(++i, nome);
        }
      if (cognome != null) {
          pst.setString(++i, cognome);
        }
      if (codiceFiscale != null) {
          pst.setString(++i, codiceFiscale);
        }
      if (tipologiaId > -1) {
          pst.setInt(++i, tipologiaId);
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
      
      if (postalCode != null) {
          pst.setString(++i, postalCode);
        }
      if (note1 != null) {
          pst.setString(++i, note1);
        }
      if (note2 != null) {
          pst.setString(++i, note2);
        }
      if (siteId > -1) {
          pst.setInt(++i, siteId);
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

  public static Vector<Veterinari> load( String cognome, String citta,
			int asl, Connection db )
	{
		Vector<Veterinari>	ret		= new Vector<Veterinari>();
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		cognome	= (cognome == null)	? ("") : (cognome.trim());
		citta		= (citta == null)		? ("") : (citta.trim());
		asl	= (asl == -2)	? (-2) : (asl);
		
		String sql = "SELECT * FROM veterinari2 ";
		
		try
		{
			boolean where = false;
			
			if( cognome.length() > 0 )
			{
				sql += ( " WHERE cognome ILIKE '%" + cognome.replaceAll( "'", "''" ) + "%' " );
				where = true;
			}
			
			stat = db.prepareStatement( sql );
			
			res		= stat.executeQuery();
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
        "UPDATE veterinari2 " +
        "SET cognome = ?, nome = ?, " +
        "codice_fiscale = ?, id_tipologia = ?, indirizzo = ?, citta = ?, provincia = ?, postalcode = ?, note1 = ?, note2 = ?, site_id = ?");

    if (!override) {
      sql.append(",modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }

    sql.append(
        "modifiedby = ?, " );

    sql.append(
        "trashed_date = ? ");
    
    sql.append("WHERE id_veterinario = ? ");
    if (!override) {
      sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
    }

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, cognome);
    pst.setString(++i, nome);
    pst.setString(++i, codiceFiscale);
    pst.setInt(++i, tipologiaId);
    pst.setString(++i, indirizzo);
    pst.setString(++i, citta);
    pst.setString(++i, provincia);
    pst.setString(++i, postalCode);
    pst.setString(++i, note1);
    pst.setString(++i, note2);
    pst.setInt(++i, siteId);
    pst.setInt(++i, this.getModifiedBy());
    DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
    pst.setInt(++i, idVeterinario);
    
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
        "UPDATE veterinari2 " +
        "SET cognome = ? " +
        "WHERE id_veterinario = 0 ");
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
          "DELETE FROM veterinari2 WHERE id_veterinario = " + this.getIdVeterinario());
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
          
          "DELETE FROM veterinari2 " +
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
    out.append("Veterinario Cognome: " + this.getCognome() + "\r\n");
    out.append("ID: " + this.getIdVeterinario() + "\r\n");
    return out.toString();
  }


  /**
   *  Description of the Method
   *
   * @param  rs             Description of Parameter
   * @throws  SQLException  Description of Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    
    this.setIdVeterinario(rs.getInt("id_veterinario"));
    cognome = rs.getString("cognome");
    nome = rs.getString("nome");
    codiceFiscale = rs.getString("codice_fiscale");
    tipologiaId = rs.getInt("id_tipologia");
    indirizzo = rs.getString("indirizzo");
    citta = rs.getString("citta");
    provincia = rs.getString("provincia"); 
    postalCode = rs.getString("postalcode");
    note1 = rs.getString("note1");
    note2 = rs.getString("note2");
    siteId = rs.getInt("site_id");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    trashedDate = rs.getTimestamp("trashed_date");
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
    if (cognome != null) {
      description += "Cognome: " + cognome;
    }

    if (user != null && user.getContact() != null) {
      description += "\\nOwner: " + user.getContact().getNameFirstLast();
    }

    //write the event
    webcal.append("BEGIN:VEVENT" + CRLF);
    
    webcal.append("END:VEVENT" + CRLF);

    return webcal.toString();
  }

}