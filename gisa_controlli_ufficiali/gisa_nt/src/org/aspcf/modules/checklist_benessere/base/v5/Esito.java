/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.checklist_benessere.base.v5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.darkhorseventures.framework.beans.GenericBean;

public class Esito extends GenericBean {

  private static final long serialVersionUID = 1L;
 
  private int id;
  private String description;
  private String shortDescription;
  
  public Esito(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

  
  private void buildRecord(ResultSet rs) throws SQLException {
	this.id = rs.getInt("id");
	this.description = rs.getString("description");
	this.shortDescription = rs.getString("short_description");
}


public Esito(Connection db, String shortDescription) throws SQLException {

	PreparedStatement pst = db.prepareStatement("select * from chk_bns_domande_v5_esiti where short_description ilike ?");
	pst.setString (1, shortDescription);
	ResultSet rs = pst.executeQuery();
	if (rs.next()){
		buildRecord(rs);
	}
	
}


public int getId() {
	return id;
}


public void setId(int id) {
	this.id = id;
}


public String getDescription() {
	return description;
}


public void setDescription(String description) {
	this.description = description;
}


public String getShortDescription() {
	return shortDescription;
}


public void setShortDescription(String shortDescription) {
	this.shortDescription = shortDescription;
}
  
  
} 
