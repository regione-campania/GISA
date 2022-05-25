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
package org.aspcfs.modules.gestioneanagrafica.base;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.darkhorseventures.framework.beans.GenericBean;

public class Anagrafica extends GenericBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2580181645261889750L;

	//private static final long serialVersionUID = 1L;

  private static Logger log = Logger.getLogger(org.aspcfs.modules.operatoriprivati.base.Organization.class);


static {
    if (System.getProperty("DEBUG") != null) {
      log.setLevel(Level.DEBUG);
    }
  }
 
  private int altId = -1;
  private String name = "";
  private int siteId = -1;
  private int tipologia = -1;
  private String accountNumber = "";
	
  public Anagrafica() { }


public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public int getSiteId() {
	return siteId;
}

public void setSiteId(int siteId) {
	this.siteId = siteId;
}

public int getTipologia() {
	return tipologia;
}

public void setTipologia(int tipologia) {
	this.tipologia = tipologia;
}

public String getAccountNumber() {
	return accountNumber;
}

public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
}

public int getAltId() {
	return altId;
}


public void setAltId(int altId) {
	this.altId = altId;
}

public Anagrafica(Connection db, int altId) throws SQLException {
		// TODO Auto-generated constructor stub
		try 
		{
			PreparedStatement pst = db.prepareStatement("select * from anagrafica.anagrafica_cerca_anagrafica_per_cu(?)");
			pst.setInt(1, altId);
			ResultSet rs = pst.executeQuery() ;
			if (rs.next())
			{
				this.altId = rs.getInt("riferimento_id");
				this.siteId = rs.getInt("id_asl");
				this.name = rs.getString("ragione_sociale");
				this.tipologia = rs.getInt("tipologia");
				this.accountNumber = rs.getString("n_reg");
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		
	}

}  