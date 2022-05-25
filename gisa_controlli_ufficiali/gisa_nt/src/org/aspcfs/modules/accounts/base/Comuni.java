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

import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class Comuni {
	
  private String codice = null; 	
  private String comune = null; 

  public Comuni() { 
  }

  public Comuni(Connection db, String comune) throws SQLException {
    queryRecord(db, comune);
  }

  public String getCodice() {
	return codice;
}

public void setCodice(String codice) {
	this.codice = codice;
}

  public void queryRecord(Connection db, String comune) throws SQLException {
    PreparedStatement st = null;
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT codice FROM comuni c WHERE c.notused is null and c.comune = ? ");
    st = db.prepareStatement(sql.toString());
    st.setString( 1, comune );
    rs = st.executeQuery();
    
    if (rs.next()) {
      codice = rs.getString(1);
    }
    rs.close();
    st.close();
  }
  
  
  
  public ArrayList<Comuni> buildList(Connection db, int asl) throws SQLException {
	    PreparedStatement st = null;
	    ResultSet rs = null;
	    StringBuffer sql = new StringBuffer();
	    ArrayList<Comuni>  lista = new ArrayList<Comuni> ();
	    if(asl!=-1)
	    sql.append("SELECT id,nome FROM comuni1 c , lookup_site_id asl WHERE c.codiceistatasl =asl.codiceistat and asl.code = ? order by nome");
	    else
	    	sql.append("SELECT id,nome FROM comuni1 c , lookup_site_id asl WHERE  c.codiceistatasl =asl.codiceistat order by nome");
	    
	    st = db.prepareStatement(sql.toString());
	   
	    if(asl!=-1)
		   st.setInt(1,asl);
	    rs = st.executeQuery();
	    
	   while (rs.next()) {
	      codice = rs.getString(1);
	      comune = rs.getString(2);
	      Comuni c = new Comuni();
	      c.setCodice(codice);
	      c.setComune(comune);
	      lista.add(c);
	    }
	    rs.close();
	    st.close();
	    return lista;
	  }
  
  
  
  public String getIdAsl(Connection db ,Integer id)
	{
	  String idAsl = null;
		
		String sql = " select codiceistatasl from comuni1 where id = ?  ";
		
		try
		{
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				idAsl = rs.getString("codiceistatasl");
			}
		
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return idAsl;
	}
  
  
public String getComune() {
	return comune;
}

public void setComune(String comune) {
	this.comune = comune;
}

}

