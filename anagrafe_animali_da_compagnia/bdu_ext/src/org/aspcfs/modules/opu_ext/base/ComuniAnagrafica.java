/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu_ext.base;

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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ComuniAnagrafica {
	
  private String codice = null; 	
  private String descrizione = null;
  private int idAsl ;
  private String descrizioneAsl ;
  public static final int CODICE_REGIONE_CAMPANIA = 15 ;
  public static final int IN_REGIONE = 1 ;
  public static final int FUORI_REGIONE = 2 ;
  public static final int TUTTI = 3 ;
  public ComuniAnagrafica() {
  }

  
  
  public int getIdAsl() {
	return idAsl;
}



public void setIdAsl(int idAsl) {
	this.idAsl = idAsl;
}



public String getDescrizioneAsl() {
	return descrizioneAsl!= null && ! descrizioneAsl.equalsIgnoreCase("null") ? descrizioneAsl : "";
}



public void setDescrizioneAsl(String descrizioneAsl) {
	this.descrizioneAsl = descrizioneAsl;
}



public ComuniAnagrafica(Connection db, String comune) throws SQLException {
    queryRecord(db, comune);
  }


public  ComuniAnagrafica(Connection db, int idComune) throws SQLException {
    PreparedStatement st = null;
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT * FROM comuni1 c WHERE c.id = ? ");
    st = db.prepareStatement(sql.toString());
    st.setInt( 1, idComune);
    rs = st.executeQuery();
    
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    st.close();
  }

public void buildRecord(ResultSet rs) throws SQLException{
	this.idAsl = rs.getInt("codiceistatasl");
	this.descrizione = rs.getString("nome");
	
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
    sql.append("SELECT codice FROM comuni c WHERE c.comune = ? ");
    st = db.prepareStatement(sql.toString());
    st.setString( 1, comune );
    rs = st.executeQuery();
    
    if (rs.next()) {
      codice = rs.getString(1);
    }
    rs.close();
    st.close();
  }
  
  public ArrayList<ComuniAnagrafica> buildList(Connection db, int asl,int tipoSelezione) throws SQLException {
	    PreparedStatement st = null;
	    ResultSet rs = null;
	    StringBuffer sql = new StringBuffer();
	    ArrayList<ComuniAnagrafica>  lista = new ArrayList<ComuniAnagrafica> ();
	    if(tipoSelezione==this.IN_REGIONE)
	    {
	    	if(asl!=-1)
	    		sql.append("SELECT id,nome FROM comuni1 c , lookup_site_id asl WHERE c.codiceistatasl =asl.codiceistat and asl.code = ? order by nome");
	    	else
	    		sql.append("SELECT id,nome FROM comuni1 c , lookup_site_id asl WHERE c.codiceistatasl =asl.codiceistat order by nome");

	    }
	    else
	    {
	    	if(tipoSelezione == this.FUORI_REGIONE)
	    	{
	    		sql.append("SELECT id,nome FROM comuni1  where  cod_regione != '"+CODICE_REGIONE_CAMPANIA+"' order by nome");

	    	}
	    	else
	    	{
	    		if(asl!=-1)
		    		sql.append("SELECT id,nome FROM comuni1 c , lookup_site_id asl WHERE c.codiceistatasl =asl.codiceistat and asl.code = ? union SELECT id,nome FROM comuni1  where  cod_regione != '"+CODICE_REGIONE_CAMPANIA+"' order by nome");
		    	else
		    		sql.append("SELECT id,nome FROM comuni1 c left join lookup_site_id asl on c.codiceistatasl =asl.codiceistat order by nome");
	    		
	    	}
	    }
	    
	    st = db.prepareStatement(sql.toString());
	    if(tipoSelezione==this.IN_REGIONE)
	    {
	    if(asl!=-1){
		   st.setInt(1,asl);
		 //  st.setString(2, "15");//regione campania
	    }
	    }
	    else
	    {
	    	if(tipoSelezione == this.TUTTI)
	    	{
	    		 if(asl!=-1){
	    			   st.setInt(1,asl);
	    		    }
	    	}
	    }
	    rs = st.executeQuery();
	    
	   while (rs.next()) {
	      codice = rs.getString(1);
	      descrizione = rs.getString(2);
	      ComuniAnagrafica c = new ComuniAnagrafica();
	      c.setCodice(codice);
	      c.setDescrizione(descrizione);
	      lista.add(c);
	    }
	    rs.close();
	    st.close();
	    return lista;
	  }
  
  
  
  public ArrayList<ComuniAnagrafica> buildList_all(Connection db, int asl) throws SQLException {
	    PreparedStatement st = null;
	    ResultSet rs = null;
	    StringBuffer sql = new StringBuffer();
	    ArrayList<ComuniAnagrafica>  lista = new ArrayList<ComuniAnagrafica> ();

	    sql.append("SELECT id,nome FROM comuni1 c order by nome");

	    
	    st = db.prepareStatement(sql.toString());
	   
	    rs = st.executeQuery();
	    
	   while (rs.next()) {
	      codice = rs.getString(1);
	      descrizione = rs.getString(2);
	      ComuniAnagrafica c = new ComuniAnagrafica();
	      c.setCodice(codice);
	      c.setDescrizione(descrizione);
	      lista.add(c);
	    }
	    rs.close();
	    st.close();
	    return lista;
	  }
  
  
public String getDescrizione() {
	return descrizione;
}

public void setDescrizione(String descrizione) {
	this.descrizione = descrizione;
}


public static boolean isInteger( String input )  
{  
   try  
   {  
      Integer.parseInt( input );  
      return true;  
   }  
   catch( Exception e )  
   {  
      return false;  
   }  
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

public ArrayList<ComuniAnagrafica> getComuni (Connection db , int idProvincia)
{
	ArrayList<ComuniAnagrafica> listaComuni =new ArrayList<ComuniAnagrafica>();
	
	String sql = "select id,nome from comuni1 where 1=1 ";
	if (idProvincia !=-1)
	{
		sql += " and cod_provincia::int = ?" ; 
	}
	else
	{
		
		sql += " and cod_regione::int = "+this.CODICE_REGIONE_CAMPANIA ;
	}
	
	sql += "order by nome ";
	try
	{
		PreparedStatement pst = db.prepareStatement(sql);
		if (idProvincia !=-1)
		{
			pst.setInt(1, idProvincia);
		}
		ResultSet rs = pst.executeQuery();
		while (rs.next())
		{
			int codice = rs.getInt("id");
			String descrizione = rs.getString("nome");
			ComuniAnagrafica p = new ComuniAnagrafica();
			p.setCodice(""+codice);
			p.setDescrizione(descrizione);
			listaComuni.add(p);
		}
	
	}
	catch(SQLException e)
	{
		e.printStackTrace();
	}
	
	return listaComuni ;
	
	
}


public ArrayList<ComuniAnagrafica> getComuni (Connection db , int idProvincia,String nome,String inRegione)
{
	ArrayList<ComuniAnagrafica> listaComuni =new ArrayList<ComuniAnagrafica>();
	
	String sql = "select id,nome,asl.description,asl.code from comuni1 " +
			"left join lookup_site_id asl on (comuni1.codiceistatasl)::int = asl.codiceistat::int   and asl.enabled=true " +
			" where 1=1 ";
	if (idProvincia !=-1)
	{
		sql += " and cod_provincia::int = ?" ; 
	}
	
	if((inRegione!=null && inRegione.equalsIgnoreCase("si")) )
	{
		sql += " and cod_regione::int = "+ComuniAnagrafica.CODICE_REGIONE_CAMPANIA ;
	}
	
	sql += " and nome ilike ? ";
	
	sql += "order by nome limit 30";
	try
	{
		PreparedStatement pst = db.prepareStatement(sql);
		if (idProvincia !=-1)
		{
			pst.setInt(1, idProvincia);
			pst.setString(2, "%"+nome+"%");
		}
		else
		{
			pst.setString(1, "%"+nome+"%");
		}
		ResultSet rs = pst.executeQuery();
		while (rs.next())
		{
			int codice = rs.getInt("id");
			String descrizione = rs.getString("nome");
			ComuniAnagrafica p = new ComuniAnagrafica();
			p.setIdAsl(rs.getInt("code"));
			p.setDescrizioneAsl(rs.getString("description"));
			p.setCodice(""+codice);
			p.setDescrizione(descrizione);
			listaComuni.add(p);
		}
	
	}
	catch(SQLException e)
	{
		e.printStackTrace();
	}
	
	return listaComuni ;
	
	
}

public static String getCap(Connection db, int idComune){
	String cap = "";
	
	String query = "select cap from comuni1 where id = " + idComune;
	try{
	PreparedStatement pst = db.prepareStatement(query);
	ResultSet rs = pst.executeQuery();
	if (rs.next()){
		cap = rs.getString("cap");
	}
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return cap;
	
	
}


}


