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
package org.aspcfs.modules.stabilimenti.base;

import org.aspcfs.modules.base.Address;
import org.aspcfs.modules.stabilimenti.storico.CampoModificato;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.StateSelect;

import com.daffodilwoods.daffodildb.server.sql99.dql.tableexpression.groupbyclause.concatenatedgrouping;
import com.darkhorseventures.framework.actions.ActionContext;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;

/**
 * Builds an address for an organization using a custom query that extends the
 * fields and methods of a typical Address.
 *
 * @author mrajkowski
 * @version $Id: OrganizationAddress.java,v 1.7 2002/09/20 14:52:45 chris Exp
 *          $
 * @created September 1, 2001
 */
public class OrganizationAddress extends org.aspcfs.modules.accounts.base.OrganizationAddress {

  /**
   * Constructor for the OrganizationAddress object
   *
   * @since 1.1
   */
  public OrganizationAddress() {
  }

  /**
   * Constructor for the OrganizationAddress object
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   * @since 1.1
   */
  public OrganizationAddress(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }

  /**
   * Constructor for the OrganizationAddress object
   *
   * @param db        Description of Parameter
   * @param addressId Description of Parameter
   * @throws SQLException Description of Exception
   * @since 1.1
   */
  public OrganizationAddress(Connection db, String addressId) throws
      SQLException {
    queryRecord(db, Integer.parseInt(addressId));
  }

  /**
   * Constructor for the OrganizationAddress object
   *
   * @param db        Description of the Parameter
   * @param addressId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public OrganizationAddress(Connection db, int addressId) throws SQLException {
    queryRecord(db, addressId);
  }

  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param addressId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int addressId) throws SQLException {
    Statement st = null;
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();

    sql.append(
        "SELECT c.address_id, c.org_id, c.address_type, c.addrline1, c.addrline1,  " +
        "c.addrline2, c.addrline3, c.addrline4, c.city, c.state, c.country, c.postalcode, c.county, c.latitude, c.longitude, c.entered, c.enteredby, " +
        "c.modified, c.modifiedby, c.primary_address, l.description " +
        "FROM organization_address c, lookup_orgaddress_types l " +
        "WHERE c.address_type = l.code " +
        "AND address_id = " + addressId + " ");
    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    st.close();
    if (this.getId() == -1) {
      throw new SQLException("Address record not found.");
    }
  }

  /**
   * Description of the Method
   *
   * @param db         Description of the Parameter
   * @param orgId      Description of the Parameter
   * @param enteredBy  Description of the Parameter
   * @param modifiedBy Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void process(Connection db, int orgId, int enteredBy, int modifiedBy,ActionContext context) throws
      SQLException {
    if (this.getEnabled() == true) {
      if (this.getId() == -1) {
        this.setOrgId(orgId);
        this.setEnteredBy(enteredBy);
        this.setModifiedBy(modifiedBy);
        this.insert(db,context);
      } else {
        this.setModifiedBy(modifiedBy);
        this.update(db, modifiedBy);
      }
    } else {
      this.delete(db);
    }
  }

  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db,ActionContext context) throws SQLException {
    insert(db, this.getOrgId(), this.getEnteredBy(),context);
  }

  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param orgId     Description of the Parameter
   * @param enteredBy Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db, int orgId, int enteredBy,ActionContext context) throws
      SQLException {
    //hardcode	  
	  if (this.getCountry()==null || this.getCountry().equalsIgnoreCase(""))
		  this.setCountry("ITALIA");  
    StringBuffer sql = new StringBuffer();
    this.setId(
        DatabaseUtils.getNextSeq(db, context,"organization_address","address_id"));
    

    int id = getId();
    sql.append(
        "INSERT INTO organization_address " +
        "(org_id, address_type, addrline1, addrline2, addrline3, addrline4, city, state, postalcode, country, county, latitude, longitude, primary_address, ");
    if (id > -1) {
      sql.append("address_id, ");
    }
    if (this.getEntered() != null) {
      sql.append("entered, ");
    }
    if (this.getModified() != null) {
      sql.append("modified, ");
    }
    sql.append("enteredBy, modifiedBy ) ");
    sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,");
    if (id > -1) {
      sql.append("?,");
    }
    if (this.getEntered() != null) {
      sql.append("?, ");
    }
    if (this.getModified() != null) {
      sql.append("?, ");
    }
    sql.append("?, ?) ");

    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());

    if (this.getOrgId() > -1) {
      pst.setInt(++i, this.getOrgId());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    if (this.getType() > -1) {
      pst.setInt(++i, this.getType());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    pst.setString(++i, this.getStreetAddressLine1());
    pst.setString(++i, this.getStreetAddressLine2());
    pst.setString(++i, this.getStreetAddressLine3());
    pst.setString(++i, this.getStreetAddressLine4());
    pst.setString(++i, this.getCity());
    pst.setString(++i, this.getState());
    pst.setString(++i, this.getZip());
    pst.setString(++i, this.getCountry());
    pst.setString(++i, this.getCounty());
    pst.setDouble(++i, this.getLatitude());
    pst.setDouble(++i, this.getLongitude());
    pst.setBoolean(++i, this.getPrimaryAddress());
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (this.getEntered() != null) {
      pst.setTimestamp(++i, this.getEntered());
    }
    if (this.getModified() != null) {
      pst.setTimestamp(++i, this.getModified());
    }

    pst.setInt(++i, this.getEnteredBy());
    pst.setInt(++i, this.getModifiedBy());
    pst.execute();
    pst.close();

   }

  /**
   * Description of the Method
   *
   * @param db         Description of Parameter
   * @param modifiedBy Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void update(Connection db, int modifiedBy) throws SQLException {
	//hardcode
	this.setCountry("ITALIA");  
    PreparedStatement pst = db.prepareStatement(
        "UPDATE organization_address " +
        "SET address_type = ?, addrline1 = ?, addrline2 = ?, addrline3 = ?, addrline4 = ?, city = ?, state = ?, postalcode = ?, country = ?, county = ?, latitude = ?, longitude =? , primary_address = ?, " +
        "modifiedby = ?, modified = CURRENT_TIMESTAMP " +
        "WHERE address_id = ? ");
    int i = 0;
    if (this.getType() > -1) {
      pst.setInt(++i, this.getType());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    pst.setString(++i, this.getStreetAddressLine1());
    pst.setString(++i, this.getStreetAddressLine2());
    pst.setString(++i, this.getStreetAddressLine3());
    pst.setString(++i, this.getStreetAddressLine4());
    pst.setString(++i, this.getCity());
    pst.setString(++i, this.getState());
    pst.setString(++i, this.getZip());
    pst.setString(++i, this.getCountry());
    pst.setString(++i, this.getCounty());
    pst.setDouble(++i, this.getLatitude());
    pst.setDouble(++i, this.getLongitude());
    pst.setBoolean(++i, this.getPrimaryAddress());
    pst.setInt(++i, modifiedBy);
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
  }

  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void delete(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM organization_address " +
        "WHERE address_id = ? ");
    int i = 0;
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
  }
  
  public ArrayList<CampoModificato> checkModifiche(Connection db, OrganizationAddress org) throws SQLException {

		
		//Field[] campi = this.getClass().getDeclaredFields();
		Field[] campi = this.getClass().getSuperclass().getDeclaredFields();

		ArrayList<CampoModificato> nomiCampiModificati = new ArrayList<CampoModificato>();

		Method metodo = null;
	
		for (int i = 0; i < campi.length; i++) {
			
			int k = 0;
			
			String nameToUpperCase = (campi[i].getName().substring(0, 1).toUpperCase() + campi[i].getName().substring(1,campi[i].getName().length()));
			//Escludere in questo if i campi che non vogliamo loggare
			if (!(nameToUpperCase.equals("SerialVersionUID")
					|| nameToUpperCase.equals("Entered")
					|| nameToUpperCase.equals("SiteId")
					|| nameToUpperCase.equals("Modified") || nameToUpperCase
					.equals("OrgId"))) {
				// (nameToUpperCase.equals("IdTipoMantello")){
				
				//verifica se gia' esistono i campi della classe 
				PreparedStatement pst = db.prepareStatement("select count(*) as recordcount from lista_campi_classi where nome_classe = ? and nome_campo = ? ");
				pst.setString(1, this.getClass().getName());
				pst.setString(2, campi[i].getName().substring(0, 1).toUpperCase() + campi[i].getName().substring(1, campi[i].getName().length()));
				ResultSet rs = pst.executeQuery();
				
				int campiPresenti = 0;
				while(rs.next()){
					campiPresenti = rs.getInt("recordcount");
				}
				
				if(campiPresenti == 0){
					PreparedStatement pst2 = db.prepareStatement("Insert into lista_campi_classi (nome_campo, tipo_campo, nome_classe) values (?, ?, ?)");
					pst2.setString(1, campi[i].getName().substring(0, 1).toUpperCase() + campi[i].getName().substring(1, campi[i].getName().length()));
					pst2.setString(2, campi[i].getType().getSimpleName());
					pst2.setString(3, this.getClass().getName());
					pst2.executeUpdate();
					pst2.close();
				}	
				
				try {
					metodo = this.getClass().getMethod("get" + nameToUpperCase, null);
				} catch (NoSuchMethodException exc) {
				}

				if (metodo != null)
					try {
						if (((metodo.invoke(org) != null && metodo.invoke(this) != null) 
								&& (metodo.invoke(org) != "" && metodo.invoke(this) != "") 
								&& (!metodo.invoke(org).equals("") && !metodo.invoke(this).equals("") )
								&& !(metodo.invoke(org).equals(metodo.invoke(this))))
								|| (metodo.invoke(org) == null && metodo.invoke(this) != null)
								|| (metodo.invoke(org) != null && metodo.invoke(this) == null)) {
							
							CampoModificato campo = new CampoModificato();
							
							if(metodo.invoke(org) == null){
								
								campo.setValorePrecedenteStringa("NON SETTATO");
								campo.setValorePrecedente("NON SETTATO");
							}
							else {
								
								campo.setValorePrecedenteStringa(metodo.invoke(org).toString());
								campo.setValorePrecedente(metodo.invoke(org).toString());
							}
							
							if(metodo.invoke(this) == null){
								
								campo.setValoreModificatoStringa("NON SETTATO");
								campo.setValoreModificato("NON SETTATO");
							}
							else {
								
								campo.setValoreModificatoStringa(metodo.invoke(this).toString());
								campo.setValoreModificato(metodo.invoke(this).toString());
							}
							
							campo.setNomeCampo(nameToUpperCase);
							
							
							
							nomiCampiModificati.add(campo);
							k++;
						}

					} catch (Exception ecc) {
					}
					finally{
						
						rs.close();
					}
			}

		}
		
		return nomiCampiModificati;
	}
  
  

}
