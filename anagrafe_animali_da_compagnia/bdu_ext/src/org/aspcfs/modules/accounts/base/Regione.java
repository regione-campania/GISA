/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.accounts.base;




import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupList;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class Regione {
	
  private String value = null; 	
  private String label = null;
  private int idRegione = -1;

  public int getIdRegione() {
	return idRegione;
}








public void setIdRegione(int idRegione) {
	this.idRegione = idRegione;
}


public void setIdRegione(String idRegione) {
	this.idRegione = new Integer(idRegione);
}







public Regione() {
  }

  
  
 




public Regione(Connection db, String regione) throws SQLException {
    queryRecord(db, regione);
  }

public Regione(Connection db, int regione) throws SQLException {
    queryRecord(db, regione);
  }



  public String getValue() {
	return value;
}








public void setValue(String value) {
	this.value = value;
}








public String getLabel() {
	return label;
}








public void setLabel(String label) {
	this.label = label;
}








public void queryRecord(Connection db, String regione) throws SQLException {
    PreparedStatement st = null;
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT codice FROM lookup_regione c WHERE c.description = ? ");
    st = db.prepareStatement(sql.toString());
    st.setString( 1, regione );
    rs = st.executeQuery();
    
    if (rs.next()) {
      value = rs.getString(1);
    }
    rs.close();
    st.close();
  }
  

public void queryRecord(Connection db, int regione) throws SQLException {
    PreparedStatement st = null;
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT * FROM lookup_regione c WHERE c.code = ? ");
    st = db.prepareStatement(sql.toString());
    st.setInt( 1, regione );
    rs = st.executeQuery();
    
    if (rs.next()) {
      label = rs.getString("description");
    }
    rs.close();
    st.close();
  }

  public ArrayList<Regione> buildList(Connection db, String regione) throws SQLException {
	    PreparedStatement st = null;
	    ResultSet rs = null;
	    StringBuffer sql = new StringBuffer();
	    ArrayList<Regione>  lista = new ArrayList<Regione> ();
	  
	    		sql.append("SELECT code, description FROM lookup_regione c where c.description ilike ? order by c.description");
	    	
	    
	    
	    st = db.prepareStatement(sql.toString());
	    
	    st.setString(1, "%"+regione+"%");

	    rs = st.executeQuery();
	    
	   while (rs.next()) {
	      value = rs.getString(2);
	      label = rs.getString(2); //Si può modificare
	      idRegione = rs.getInt(1);
	      Regione c = new Regione();
	      c.setValue(value);
	      c.setLabel(label);
	      c.setIdRegione(idRegione);
	      lista.add(c);
	    }
	    rs.close();
	    st.close();
	    return lista;
	  }
  
  
  
  public ArrayList<Regione> buildList_all(Connection db, int asl) throws SQLException {
	  
	    PreparedStatement st = null;
	    ResultSet rs = null;
	    StringBuffer sql = new StringBuffer();
	    ArrayList<Regione>  lista = new ArrayList<Regione> ();
	  
	    		sql.append("SELECT code, description FROM regione c order by description");
	    	
	    
	    
	    st = db.prepareStatement(sql.toString());

	    rs = st.executeQuery();
	    
	   while (rs.next()) {
	      value = rs.getString(2);
	      label = rs.getString(2);
	      idRegione = rs.getInt(1);
	      Regione c = new Regione();
	      c.setValue(value);
	      c.setLabel(label);
	      lista.add(c);
	    }
	    rs.close();
	    st.close();
	    return lista;
	    
	  }
  
  
  
  public ArrayList<Regione> getByProvincia(Connection db, int idProvincia) throws SQLException {
	  
	    PreparedStatement st = null;
	    ResultSet rs = null;
	    StringBuffer sql = new StringBuffer();
	    ArrayList<Regione>  lista = new ArrayList<Regione> ();
	  
	    		sql.append("SELECT code, description FROM lookup_regione  where code = (select id_regione from lookup_province where code = ?)");
	    	
	    
	    
	    st = db.prepareStatement(sql.toString());
	    
	    st.setInt(1, idProvincia);

	    rs = st.executeQuery();
	    
	   while (rs.next()) {
	      value = rs.getString(2);
	      label = rs.getString(2);
	      idRegione = rs.getInt(1);
	      Regione c = new Regione();
	      c.setValue(value);
	      c.setLabel(label);
	      lista.add(c);
	    }
	    rs.close();
	    st.close();
	    return lista;
	    
	  }
  



 

public HashMap<String, Object> getHashmap() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
{

	HashMap<String, Object> map = new HashMap<String, Object>();
	Field[] campi = this.getClass().getDeclaredFields();
	Method[] metodi = this.getClass().getMethods();
	for (int i = 0 ; i <campi.length; i++)
	{
		String nomeCampo = campi[i].getName();
		
		for (int j=0; j<metodi.length; j++ )
		{
			if(metodi[j].getName().equalsIgnoreCase("GET"+nomeCampo))
			{
				
				map.put(nomeCampo, metodi[j].invoke(this));
			}
			
		}
		
	}
	
	return map ;
	
}











}



