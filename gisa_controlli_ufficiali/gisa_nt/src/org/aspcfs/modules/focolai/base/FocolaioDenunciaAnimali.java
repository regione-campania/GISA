/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.focolai.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Vector;

import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.beans.GenericBean;

public class FocolaioDenunciaAnimali extends GenericBean
{
	private String specie = "";
	private int complessivo = 0;
	private int natiStalla = 0;
	private int introdotti = 0;
	private int ammalati = 0;
	private int morti = 0;
	private int focolaioId = -1;
	private int denunciaId = -1;
	
	
	public void setDenunciaId(String denunciaId)
	{
		this.denunciaId = Integer.parseInt(denunciaId);
	}
	
	public int getDenunciaId() {
		return denunciaId;
	}

	public void setDenunciaId(int denunciaId) {
		this.denunciaId = denunciaId;
	}

	public void setFocolaioId(String focolaioId)
	{
		this.focolaioId = Integer.parseInt(focolaioId);
	}
	
	public int getFocolaioId() {
		return focolaioId;
	}
	public void setFocolaioId(int focolaioId) {
		this.focolaioId = focolaioId;
	}
	public String getSpecie() {
		return specie;
	}
	public void setSpecie(String specie) {
		try {
			this.specie = specie;
		} catch (Exception e) {
			
		}
	}
	public int getComplessivo() {
		return complessivo;
	}
	public void setComplessivo(int complessivo) {
		this.complessivo = complessivo;
	}
	public void setComplessivo(String complessivo) {
	      try {
			this.complessivo = Integer.parseInt(complessivo);
		} catch (NumberFormatException e) {
		
		}
	    }
	public int getNatiStalla() {
		return natiStalla;
	}
	public void setNatiStalla(int natiStalla) {
		this.natiStalla = natiStalla;
	}
	public void setNatiStalla(String natiStalla) {
	      try {
			this.natiStalla = Integer.parseInt(natiStalla);
		} catch (NumberFormatException e) {
			
		}
	    }
	public int getIntrodotti() {
		return introdotti;
	}
	public void setIntrodotti(int introdotti) {
		this.introdotti = introdotti;
	}
	public void setIntrodotti(String introdotti) {
	      try {
			this.introdotti = Integer.parseInt(introdotti);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			
		}
	    }
	public int getAmmalati() {
		return ammalati;
	}
	public void setAmmalati(int ammalati) {
		this.ammalati = ammalati;
	}
	public void setAmmalati(String ammalati) {
	      try {
			this.ammalati = Integer.parseInt(ammalati);
		} catch (NumberFormatException e) {
		
		}
	    }
	public int getMorti() {
		return morti;
	}
	public void setMorti(int morti) {
		this.morti = morti;
	}
	public void setMorti(String morti) {
	      try {
			this.morti = Integer.parseInt(morti);
		} catch (NumberFormatException e) {
			
		}
	    }
	
	public FocolaioDenunciaAnimali()
	{
		
	}
	
	//inserimento
	public boolean insert(Connection db) throws SQLException {
		    StringBuffer sql = new StringBuffer();
		    boolean commit = db.getAutoCommit();
		    try {
		      if (commit) {
		        db.setAutoCommit(false);
		      }
		      
		      sql.append(
		    		  "INSERT INTO FOCOLAIDENUNCIAANIMALI (focolaio_id,specie," +
		    		  " complessivo, nati_stalla, introdotti, ammalati, morti)");
		     
		      sql.append("VALUES( ?, ?, ?, ?, ?, ?, ?)");
		      
		      int i = 0;
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		      pst.setInt(++i, this.focolaioId);
		      pst.setString(++i, this.specie);
		      pst.setInt(++i, this.complessivo);
		      pst.setInt(++i, this.natiStalla);
		      pst.setInt(++i, this.introdotti);
		      pst.setInt(++i, ammalati);
		      pst.setInt(++i, this.morti);
		      
		      pst.execute();
		    		  
		      int id = 0;
		     // id = DatabaseUtils.getCurrVal(db, "focolai_focolaioid_seq", id);
		      
		    
		     
		    } catch (SQLException e) {
		       e.printStackTrace();
		    } finally {
		      if (commit) {
		        db.setAutoCommit(true);
		      }
		    }
		    return true;
		  }

	
	//update
	public boolean update(Connection db,int focolaio_id,int denuncia_id) throws SQLException {
		    StringBuffer sql = new StringBuffer();
		    boolean commit = db.getAutoCommit();
		    try {
		      if (commit) {
		        db.setAutoCommit(false);
		      }
		      
		      int m=0;
		      
		      sql.append(
		    		  "update  FOCOLAIDENUNCIAANIMALI set " );
		    		  if( !this.getSpecie().equals("") ) 			    
		    		  { 
		    			  sql.append("specie = ?"); 
		    		      m++;  //per controllare se la query va bene  
		    		  }
		    		  
		    			    sql.append(",complessivo = ?");
		    			   
		    		 
		    			    sql.append(",nati_stalla = ?");
		    			   
		    		  
		    			   sql.append(",introdotti = ?");
		    			 
		    		  
		    			   sql.append(",ammalati = ?");
		    			  
		    		 
		    			   sql.append(",morti = ?");
		    			 
		    		  sql.append(" where focolaio_id = ? and denuncia_id = ?");
		    			  
		    		  
		      int i = 0;
		     if( m > 0)
		     {	 
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		      
		      if( !this.getSpecie().equals("") ) 
		        pst.setString(++i, this.specie);
		      
		      
		        pst.setInt(++i, this.complessivo);
		      
		        pst.setInt(++i, this.natiStalla);
		      
			    pst.setInt(++i, this.introdotti);

		       pst.setInt(++i, this.ammalati);
		      
		       pst.setInt(++i, this.morti);
		   
		      pst.setInt(++i, focolaio_id);
		      pst.setInt(++i, denuncia_id);
		      
		      
		      pst.execute();
		     }  
		    		  
		     
		      
		    
		     
		    } catch (SQLException e) {
		       e.printStackTrace();
		    } finally {
		      if (commit) {
		        db.setAutoCommit(true);
		      }
		    }
		    return true;
		  }
	 
		
		
	}

	

