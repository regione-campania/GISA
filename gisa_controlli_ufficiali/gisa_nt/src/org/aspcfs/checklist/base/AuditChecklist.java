/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.checklist.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.aspcfs.modules.base.Parameter;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.CustomLookupElement;

import com.darkhorseventures.framework.beans.GenericBean;

public class AuditChecklist extends GenericBean {
  
	private static final long serialVersionUID = -1857992578468292055L;
	int checklistId=-1;
	int auditId;
	Boolean risposta = null;
	int punti;
	String stato = "" ;
	public int getChecklistId() {
		return checklistId;
	}
	
	public boolean getChecklistIdRisposta(String parentId) {
		return getRisposta();
	}
	
	public void setChecklistId(int checklistId) {
		this.checklistId = checklistId;
	}
	
	public int getAuditId() {
		return auditId;
	}
	
	public void setAuditId(int auditId) {
		this.auditId = auditId;
	}
	
	public boolean isRisposta() {
		return risposta;
	}
	
	public Boolean getRisposta() {
		return risposta;
	}
	
	public void setRisposta(Boolean risposta) {
		this.risposta = risposta;
	}
	
	public int getPunti() {
		return punti;
	}
	
	public void setPunti(int punti) {
		this.punti = punti;
	}
	
	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	//costruttore
	public AuditChecklist() {
		
	}
	
	public int update(Connection db, boolean override) throws SQLException {
		
		  int resultCount = 0;
		  	  
		  PreparedStatement pst = null;
		  StringBuffer sql = new StringBuffer();
		  sql.append("UPDATE audit_checklist  SET "); 
		  sql.append("risposta = ?, ");
		  sql.append("punti = ?,stato = ? ");
		  sql.append(" WHERE audit_id = ?  and checklist_id = ?");
	      int i = 0;
		  pst = db.prepareStatement(sql.toString());
		  pst.setBoolean(++i, risposta);
		  pst.setInt(++i, punti);
		  pst.setString(++i, stato);
		  pst.setInt(++i, auditId);
		  pst.setInt(++i, checklistId);
		  		  
	      resultCount = pst.executeUpdate();
		  pst.close();
	      return resultCount;
	      
	    }

  public boolean insert(Connection db) throws SQLException {
	StringBuffer sql = new StringBuffer();
	boolean doCommit = false;
			
	try {
	  
	  //sql.append(" INSERT INTO audit_checklist (checklist_id, audit_id, risposta, punti) VALUES (?,?,?,?) ");
	  sql.append(" INSERT INTO audit_checklist (checklist_id, audit_id, punti,stato) VALUES (?,?,?,?) ");
		
	  int i = 0;
	  PreparedStatement pst = db.prepareStatement( sql.toString() );
	  pst.setInt( ++i, this.getChecklistId() );
	  pst.setInt( ++i, this.getAuditId() );
	  //pst.setBoolean( ++i, this.getRisposta() );
	  pst.setInt( ++i, this.getPunti() );
	  pst.setString(++i,stato);
	  pst.execute();
	  pst.close();
	
	} catch (SQLException e){
	 
	  throw new SQLException(e.getMessage());
	} finally {
	 
    }
	return true;
  }
  
  
  public boolean insert_modify(Connection db) throws SQLException {
		StringBuffer sql = new StringBuffer();
		boolean doCommit = false;
				
		try {
		  if(doCommit = db.getAutoCommit()){
			 db.setAutoCommit(false);
		  }
		  //sql.append(" INSERT INTO audit_checklist (checklist_id, audit_id, risposta, punti) VALUES (?,?,?,?) ");
		  sql.append(" INSERT INTO audit_checklist (checklist_id, audit_id,risposta , punti,stato) VALUES (?,?,?,?,?) ");
			
		  int i = 0;
		  PreparedStatement pst = db.prepareStatement( sql.toString() );
		  pst.setInt( ++i, this.getChecklistId() );
		  pst.setInt( ++i, this.getAuditId() );
		  pst.setBoolean( ++i, this.getRisposta() );
		  pst.setInt( ++i, this.getPunti() );
		  pst.setString(++i,stato);
		  pst.execute();
		  pst.close();
		  if (doCommit){
		    db.commit();
		  }
		} catch (SQLException e){
		  if (doCommit){
			db.rollback();
		  }
		  throw new SQLException(e.getMessage());
		} finally {
		  if (doCommit){
		    db.setAutoCommit(true);
		  }
	    }
		return true;
	  }

  public static ArrayList<AuditChecklist> queryRecord(Connection db, int auditId) throws SQLException {
	ArrayList<AuditChecklist> ret = new ArrayList<AuditChecklist>();
		  
	PreparedStatement pst = null;
	ResultSet rs = null;
	pst = db.prepareStatement("SELECT audit_checklist.* FROM audit_checklist, checklist WHERE audit_checklist.checklist_id = checklist.id AND audit_checklist.audit_id = ? ORDER BY checklist.level" );
	pst.setInt(1, auditId);
	rs = pst.executeQuery();
	
	while (rs.next()) {
	  ret.add( buildRecord(rs) );
    }
	  
	rs.close();
    pst.close();
    return ret;
  }
	
  private static AuditChecklist buildRecord(ResultSet rs) throws SQLException {
    //audit_checklist table
	AuditChecklist temp = new AuditChecklist();
	temp.setAuditId( rs.getInt( "audit_id" ) );
	temp.setChecklistId( rs.getInt( "checklist_id" ) );
	temp.setRisposta( rs.getBoolean( "risposta" ) );
	temp.setPunti( rs.getInt( "punti" ) );
	temp.setStato(rs.getString("stato"));
	return temp;
  }
  
  public void delete(Connection db) throws SQLException {
    boolean commit = db.getAutoCommit();
	try {
	  if (commit) {
	    db.setAutoCommit(false);
	  }
	  
	  PreparedStatement pst = db.prepareStatement("DELETE FROM audit_checklist WHERE audit_id = ? ");
	  pst.setInt(1, this.getAuditId());
	  pst.execute();
	  pst.close();
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
  }
  
  public void deleteRisposta(Connection db) throws SQLException {
	    boolean commit = db.getAutoCommit();
		try {
		  if (commit) {
		    db.setAutoCommit(false);
		  }
		  
		  PreparedStatement pst = db.prepareStatement("DELETE FROM audit_checklist WHERE checklist_id = ? ");
		  pst.setInt(1, this.checklistId);
		  pst.execute();
		  pst.close();
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
	  }
  
  public boolean isPresent(Connection db, int checkListId) throws SQLException {
	  boolean ret = false;
	  PreparedStatement pst = null;
	  ResultSet rs = null;
	  pst = db.prepareStatement("SELECT * FROM audit_checklist ac WHERE ac.checklist_id ="+ checkListId);
	  rs = pst.executeQuery();
	  if (rs.next()) {
	    ret = true;
	  }
	  rs.close();
	  pst.close();
	  return ret;
  }
  
  public static boolean presentIs(ArrayList<AuditChecklist> db, int checkListId) throws SQLException {
	  boolean result = false; 
	  Iterator audiCkList=db.iterator();
	  while(audiCkList.hasNext())
      { 
    AuditChecklist audiCkListTemp= (AuditChecklist)audiCkList.next();
    if(checkListId == audiCkListTemp.getChecklistId()){
  	  
      		  result = true;
	  
  }
 }
	return result;
 }
}