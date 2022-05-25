/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.trasportoanimali.base;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

public class Stato implements Serializable{
	private int id = -1;
	private int orgId = -1;
	private String stato = "Attivo";
	private int enteredBy = -1;
	private java.sql.Timestamp entered = null;
	
	public void setOrgId(String tmp) {
	    this.setOrgId(Integer.parseInt(tmp));
	  }
	 public int getOrgId() {
		    return orgId;
		  }
	 public void setOrgId(int tmp) {
		    this.orgId = tmp;
		    
		  }
	 public void setStato(String tmp) {
		    this.stato = tmp;
		  }
	 public String getStato() {
		    return stato;
		  }
	public void setEnteredBy(int tmp) {
	    this.enteredBy = tmp;
	  }
	public void setEnteredBy(String tmp) {
	    this.enteredBy = Integer.parseInt(tmp);
	  }
	 public int getEnteredBy() {
		    return enteredBy;
		  }
	 public java.sql.Timestamp getEntered() {
		    return entered;
		  }
	 public void setEntered(String tmp) {
		    this.entered = DateUtils.parseTimestampString(tmp);
		  }
	 public void setEntered(java.sql.Timestamp tmp) {
		    this.entered = tmp;
		  }
	 
	 public String getEnteredString() {
		    String tmp = "";
		    try {
		      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(
		          entered);
		    } catch (NullPointerException e) {
		    }
		    return tmp;
		  }
	 public Stato (int org_id, String stato, java.sql.Timestamp enter, int chi){
			
			this.setOrgId(org_id);
			this.setStato(stato);
			this.setEntered(enter);
			this.setEnteredBy(chi);
								
		}
	 public Stato (){
			
			}
		
	 public boolean insert(Connection db,int org_id) throws SQLException {
		 boolean doCommit = false;
		 
			try{
			
				if (doCommit = db.getAutoCommit()) {
			        db.setAutoCommit(false);
			      }
			      
			      
			      
			      id = DatabaseUtils.getNextSeqTipo(db, "stato_impresa_id_seq");
			     
				String sql="INSERT INTO stato_impresa (id,org_id,stato,entered,enteredby) VALUES "+
				" (?,?,?,?,?)";
				
				java.sql.PreparedStatement pst= db.prepareStatement(sql);
				pst.setInt(1, id);
				pst.setInt(2, org_id);	
				pst.setString(3, stato);
				DatabaseUtils.setTimestamp(
				            pst, 4, new Timestamp(System.currentTimeMillis()));
				pst.setInt(5, enteredBy);
							
				
				pst.execute();
				pst.close();
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
			      return true;
			
				
			
			
		}
		

}
