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

public class FocolaioDecorso extends GenericBean
{
	private String specie = "";
	private int abbattuti = 0;
	private int guariti = 0;
	private int totaleMalati = 0;
	private int smarriti = 0;
	private int morti = 0;
	private int sani = 0;
	private int esistenti = 0;
	private int focolaioId = -1;
	private int denunciaId = -1;
	
	
	
	
	public int getAbbattuti() {
		return abbattuti;
	}

	public void setAbbattuti(int abbattuti) {
		this.abbattuti = abbattuti;
	}
	
	public void setAbbattuti(String abbattuti)
	{
		this.abbattuti = Integer.parseInt(abbattuti);
	}
	

	public int getGuariti() {
		return guariti;
	}

	public void setGuariti(int guariti) {
		this.guariti = guariti;
	}

	public void setGuariti(String guariti)
	{
		this.guariti = Integer.parseInt(guariti);
	}
	
	
	public int getTotaleMalati() {
		return totaleMalati;
	}

	public void setTotaleMalati(int totaleMalati) {
		this.totaleMalati = totaleMalati;
	}
	
	public void setTotaleMalati(String totaleMalati)
	{
		this.totaleMalati = Integer.parseInt(totaleMalati);
	}
	

	public int getSmarriti() {
		return smarriti;
	}

	public void setSmarriti(int smarriti) {
		this.smarriti = smarriti;
	}

	public void setSmarriti(String smarriti)
	{
		this.smarriti = Integer.parseInt(smarriti);
	}
	
	
	public int getSani() {
		return sani;
	}

	public void setSani(int sani) {
		this.sani = sani;
	}
	
	public void setSani(String sani)
	{
		this.sani = Integer.parseInt(sani);
	}
	

	public int getEsistenti() {
		return esistenti;
	}

	public void setEsistenti(int esistenti) {
		this.esistenti = esistenti;
	}
	
	public void setEsistenti(String esistenti)
	{
		this.esistenti = Integer.parseInt(esistenti);
	}
	

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
	
	public FocolaioDecorso()
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
		    		  "INSERT INTO FOCOLAIODECORSO (focolaio_id,specie," +
		    		  " morti,abbattuti,guariti,totale_malati,smarriti,sani,esistenti)");
		     
		      sql.append("VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		      
		      int i = 0;
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		      pst.setInt(++i, this.focolaioId);
		      pst.setString(++i, this.specie);
		      pst.setInt(++i, this.morti);
		      pst.setInt(++i, this.abbattuti);
		      pst.setInt(++i, this.guariti);
		      pst.setInt(++i, this.totaleMalati);
		      pst.setInt(++i, this.smarriti);
		      pst.setInt(++i, this.sani);
		      pst.setInt(++i, this.esistenti);
		      
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
		      
		   
		      
		      sql.append(
		    		  "update  FOCOLAIODECORSO set " );
		    		   
		    			  sql.append("specie = ?"); 
		    		      
		    		
		    		  
		    			   sql.append(",morti = ?");
            			   sql.append(",abbattuti = ?");
    	    			   sql.append(",guariti = ?");
            			   sql.append(",totale_malati = ?");
		    			   sql.append(",smarriti = ?");
		    			   sql.append(",sani = ?");
		    			   sql.append(",esistenti = ?");
		    			   
		    			   
		    		  sql.append(" where focolaio_id = ? and denuncia_id = ?");
		    			  
		    		  
		      int i = 0;
		     
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		      
		      
		        pst.setString(++i, this.specie);
    	        pst.setInt(++i, this.morti);
  	            pst.setInt(++i, this.abbattuti);
  	            pst.setInt(++i, this.guariti);
                pst.setInt(++i, this.totaleMalati);
                pst.setInt(++i, this.smarriti);
                pst.setInt(++i, this.sani);
                pst.setInt(++i, this.esistenti);
                
                pst.setInt(++i, focolaio_id);
		        pst.setInt(++i, denuncia_id);
		      
		      
		      pst.execute();
		       
		    		  
		     
		      
		    
		     
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

	

