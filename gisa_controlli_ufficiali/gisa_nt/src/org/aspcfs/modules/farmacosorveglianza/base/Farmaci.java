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
public class Farmaci extends GenericBean {

  private static Logger log = Logger.getLogger(org.aspcfs.modules.farmacosorveglianza.base.Farmaci.class);
  static {
    if (System.getProperty("DEBUG") != null) {
      log.setLevel(Level.DEBUG);
    }
  }
  protected double YTD = 0;
  private int idFarmaco = -1;
  private int idTiposomministrazione = -1;
  private int idConfezione = -1;
  private String nomeCommerciale = null;
  private String principioAttivo = null;
  private String tipoSomministrazione = null;
  private String ditta = null;
  private String gruppoMerceologico = null;
  private String note1 = null;
  private String note2 = null;
  private int quantita = -1;
  private int enteredBy = -1;
  private double prezzo = -1;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private int modifiedBy = -1;
  private Timestamp trashedDate = null;

  /**
   *  Constructor for the Organization object, creates an empty Organization
   *
   * @since    1.0
   */
  public Farmaci() { }


  /**
   *  Constructor for the Organization object
   *
   * @param  rs                Description of Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   * @throws  SQLException     Description of Exception
   */
  public Farmaci(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }
  
  public Farmaci(Connection db, int id_farmaco) throws SQLException {
	
	  
    if (id_farmaco == -1) {
      throw new SQLException("Invalid Account");
    } 
    PreparedStatement pst = db.prepareStatement(
        "SELECT o.* " +
        "FROM farmaci2 o " +
        "WHERE o.id_farmaco = ? " );
    pst.setInt(1, id_farmaco);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst, log);
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (idFarmaco == -1) {
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


  public void setTipoSomministrazione(String tmp) {
    this.tipoSomministrazione = tmp;
  }


  /**
   *  Sets the account size attribute of the Organization object
   *
   * @param  tmp  The new account size value
   */
  public void setQuantita(String tmp) {
    this.quantita = Integer.parseInt(tmp);
  }

  public void setQuantita(int tmp) {
	    this.quantita = tmp;
	  }
  
  public void setPrincipioAttivo(String tmp) {
    this.principioAttivo = tmp;
  }

  public void setNomeCommerciale(String tmp) {
    this.nomeCommerciale = tmp;
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
	    return idFarmaco;
	  }

  
  public void setIdFarmaco(int tmp) {
	    this.idFarmaco = tmp;
	  }
  
  public void setIdFarmaco(String tmp) {
	    this.setIdFarmaco(Integer.parseInt(tmp));
	  }

  public int getIdFarmaco() {
	    return idFarmaco;
	  }
  
  public void setIdConfezione(int tmp) {
	    this.idConfezione = tmp;
	  }

public void setIdConfezione(String tmp) {
	    this.setIdConfezione(Integer.parseInt(tmp));
	  }

public int getIdConfezione() {
	    return idConfezione;
	  }
  public void setIdTiposomministrazione(int tmp) {
	    this.idTiposomministrazione = tmp;
	  }

public void setIdTiposomministrazione(String tmp) {
	    this.setIdTiposomministrazione(Integer.parseInt(tmp));
	  }

public int getIdTiposomministrazione() {
	    return idTiposomministrazione;
	  }

public void setPrezzo(double tmp) {
    this.prezzo = tmp;
  }

public void setPrezzo(String tmp) {
    this.setPrezzo(Double.parseDouble(tmp));
  }

public double getPrezzo() {
    return prezzo;
  }

public void setDitta(String tmp) {
	    this.ditta = tmp;
  }

public String getDitta() {
    return ditta;
  }
public void setGruppoMerceologico(String tmp) {
    this.gruppoMerceologico = tmp;
}

public String getGruppoMerceologico() {
return gruppoMerceologico;
}

  /**
   *  Gets the Enabled attribute of the Organization object
   *
   * @return    The Enabled value
   */
  public String getNomeCommerciale() {
    return nomeCommerciale;
  }

  /**
   *  Gets the types attribute of the Organization object
   *
   * @return    The types value
   */
  public String getPrincipioAttivo() {
    return principioAttivo;
  }

  /**
   *  Gets the alertText attribute of the Organization object
   *
   * @return    The alertText value
   */
  public String getTipoSomministrazione() {
    return tipoSomministrazione;
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
   *  Gets the Industry attribute of the Organization object
   *
   * @return    The quantita' value
   */
  public int getQuantita() {
    return quantita;
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
    if (this.getIdFarmaco() == -1) {
      throw new SQLException("Farmaco ID not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    boolean success = false;

    sql.append(
        "UPDATE farmaci set enabled = ? " +
        "WHERE id_farmaco = ? ");

    sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setBoolean(++i, false);
    pst.setInt(++i, idFarmaco);

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
    if (this.getIdFarmaco() == -1) {
      throw new SQLException("Farmaco ID not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    boolean success = false;

    sql.append(
        "UPDATE farmaci2 SET enabled = ? " +
        "WHERE id_farmaco = ? ");
    sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setBoolean(++i, true);
    pst.setInt(++i, idFarmaco);
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
        "SELECT nome_commerciale, id_farmaco " +
        "FROM farmaci2 " +
        "WHERE " + DatabaseUtils.toLowerCase(db) + "(farmaci2.nome_commerciale) = ? ";
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
      
      idFarmaco =  DatabaseUtils.getNextSeq(db,context, "farmaci2","id_farmaco");

      sql.append(
          "INSERT INTO farmaci2 (");
      if (idFarmaco > -1) {
        sql.append("id_farmaco, ");
      }
      if (nomeCommerciale != null) {
          sql.append("nome_commerciale, ");
        }
      if (principioAttivo != null) {
          sql.append("principio_attivo, ");
        }
      if (tipoSomministrazione != null) {
          sql.append("tipo_somministrazione, ");
        }
      if (quantita > -1) {
          sql.append("quantita, ");
        }
      if (note1 != null) {
          sql.append("note1, ");
        }
      if (note2 != null) {
          sql.append("note2, ");
        }
      if (ditta != null) {
          sql.append("ditta, ");
        }
      if (gruppoMerceologico != null) {
          sql.append("gruppo_merceologico, ");
        }
      if (idTiposomministrazione > -1) {
          sql.append("id_tiposomministrazione, ");
        }
      if (idConfezione > -1) {
          sql.append("id_confezione, ");
        }
      if (prezzo > -1) {
          sql.append("prezzo, ");
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
      if (idFarmaco > -1) {
        sql.append("?,");
      }
      if (nomeCommerciale != null) {
          sql.append("?, ");
        }
     
      if (principioAttivo != null) {
          sql.append("?, ");
        }
      if (tipoSomministrazione != null) {
          sql.append("?, ");
        }
      if (quantita > -1) {
          sql.append("?, ");
        }
      if (note1 != null) {
          sql.append("?, ");
        }
      if (note2 != null) {
          sql.append("?, ");
        }
      if (ditta != null) {
    	  sql.append("?, ");
        }
      if (gruppoMerceologico != null) {
    	  sql.append("?, ");
        }
      if (idTiposomministrazione > -1) {
    	  sql.append("?, ");
        }
      if (idConfezione > -1) {
    	  sql.append("?, ");
        }
      if (prezzo > -1) {
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
      if (idFarmaco > -1) {
        pst.setInt(++i, idFarmaco);
      }
      if (nomeCommerciale != null) {
          pst.setString(++i, nomeCommerciale);
        }
      if (principioAttivo != null) {
          pst.setString(++i, principioAttivo);
        }
      if (tipoSomministrazione != null) {
          pst.setString(++i, tipoSomministrazione);
        }
      if (quantita > -1) {
          pst.setInt(++i, quantita);
        }
      if (note1 != null) {
          pst.setString(++i, note1);
        }
      if (note2 != null) {
          pst.setString(++i, note2);
        }
      if (ditta != null) {
    	  pst.setString(++i, ditta);
        }
      if (gruppoMerceologico != null) {
    	  pst.setString(++i, gruppoMerceologico);
        }
      if (idTiposomministrazione > -1) {
    	  pst.setInt(++i, idTiposomministrazione);
        }
      if (idConfezione > -1) {
    	  pst.setInt(++i, idConfezione);
        }
      if (prezzo > -1) {
    	  pst.setDouble(++i, prezzo);
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

  public static Vector<Farmaci> load( String nome_commerciale, String citta,
			int asl, Connection db )
	{
		Vector<Farmaci>	ret		= new Vector<Farmaci>();
		PreparedStatement			stat	= null;
		ResultSet					res		= null;
		
		nome_commerciale	= (nome_commerciale == null)	? ("") : (nome_commerciale.trim());
		citta		= (citta == null)		? ("") : (citta.trim());
		asl	= (asl == -2)	? (-2) : (asl);
		
		String sql = "SELECT * FROM farmaci2 ";
		
		try
		{
			boolean where = false;
			
			if( nome_commerciale.length() > 0 )
			{
				sql += ( " WHERE nome_commerciale ILIKE '%" + nome_commerciale.replaceAll( "'", "''" ) + "%' " );
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
        "UPDATE farmaci2 " +
        "SET nome_commerciale = ?, principio_attivo = ?, " +
        "tipo_somministrazione = ?, quantita = ?, note1 = ?, note2 = ?, ditta = ?, id_tiposomministrazione = ?, id_confezione = ?, prezzo = ?, gruppo_merceologico = ?, ");

    if (!override) {
      sql.append("modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }

    sql.append(
        "modifiedby = ?, " );

    sql.append(
        "trashed_date = ? ");
    
    sql.append("WHERE id_farmaco = ? ");
    if (!override) {
      sql.append("AND modified " + ((this.getModified() == null)?"IS NULL ":"= ? "));
    }

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setString(++i, nomeCommerciale);
    pst.setString(++i, principioAttivo);
    pst.setString(++i, tipoSomministrazione);
    pst.setInt(++i, quantita);
    pst.setString(++i, note1);
    pst.setString(++i, note2);
    pst.setString(++i, ditta);
    pst.setInt(++i, idTiposomministrazione);
    pst.setInt(++i, idConfezione);
    pst.setDouble(++i, prezzo);
    pst.setString(++i, gruppoMerceologico);
    pst.setInt(++i, this.getModifiedBy());
    DatabaseUtils.setTimestamp(pst, ++i, this.getTrashedDate());
    pst.setInt(++i, idFarmaco);
    
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
        "UPDATE farmaci2 " +
        "SET nome_commerciale = ? " +
        "WHERE id_farmaco = 0 ");
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
    if (this.getIdFarmaco() == -1) {
      throw new SQLException("Farmaco ID not specified.");
    }
    boolean commit = db.getAutoCommit();
    try { 
      if (commit) {
        db.setAutoCommit(false);
      }
 
      Statement st = db.createStatement();
      st.executeUpdate(
          "DELETE FROM farmaci2 WHERE id_farmaco = " + this.getIdFarmaco());
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
          
          "DELETE FROM farmaci2 " +
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
    out.append("Farmaco Nome: " + this.getNomeCommerciale() + "\r\n");
    out.append("ID: " + this.getIdFarmaco() + "\r\n");
    return out.toString();
  }


  /**
   *  Description of the Method
   *
   * @param  rs             Description of Parameter
   * @throws  SQLException  Description of Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    
    this.setIdFarmaco(rs.getInt("id_farmaco"));
    nomeCommerciale = rs.getString("nome_commerciale");
    principioAttivo = rs.getString("principio_attivo");
    tipoSomministrazione = rs.getString("tipo_somministrazione");
    quantita = rs.getInt("quantita");
    note1 = rs.getString("note1");
    note2 = rs.getString("note2");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    trashedDate = rs.getTimestamp("trashed_date");
    ditta = rs.getString("ditta");
    gruppoMerceologico = rs.getString("gruppo_merceologico");
    idTiposomministrazione = rs.getInt("id_tiposomministrazione");
    idConfezione = rs.getInt("id_confezione");
    prezzo = rs.getDouble("prezzo");
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
    if (nomeCommerciale != null) {
      description += "Nome: " + nomeCommerciale;
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