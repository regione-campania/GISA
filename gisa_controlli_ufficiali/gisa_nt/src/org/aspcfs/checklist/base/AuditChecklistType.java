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

import com.darkhorseventures.framework.beans.GenericBean;

public class AuditChecklistType extends GenericBean {
  
	private static final long serialVersionUID = 7960673071596670620L;
	int checklistTypeId;
	int auditId;
	int valoreRange;
	String operazione = null;
	String nota = "";
	



	private boolean is_abilitato = true;
	public int getChecklistTypeId() {
		return checklistTypeId;
	}
	
	public void setChecklistTypeId(int checklistTypeId) {
		this.checklistTypeId = checklistTypeId;
	}
	
	public int getAuditId() {
		return auditId;
	}
	
	public void setAuditId(int auditId) {
		this.auditId = auditId;
	}
	
	public int getValoreRange() {
		return valoreRange;
	}
	
	public void setValoreRange(int valoreRange) {
		this.valoreRange = valoreRange;
	}
	
	public String getOperazione() {
		return operazione;
	}
	
	public void setOperazione(String operazione) {
		this.operazione = operazione;
	}
	
	public String getNota() {
		return nota;
	}
	
	public void setNota(String nota) {
		this.nota = nota;
	}
	
	//costruttore
	public AuditChecklistType(){
	
	}
	
  public boolean insert(Connection db) throws SQLException {
	StringBuffer sql = new StringBuffer();
			
	try {
	  
	  sql.append(" INSERT INTO audit_checklist_type (checklist_type_id, audit_id, valore_range, operazione, nota,is_abilitato) VALUES (?,?,?,?,?,?) ");
	
	  int i = 0;
	  PreparedStatement pst = db.prepareStatement( sql.toString() );
	  pst.setInt(++i, this.getChecklistTypeId());
	  pst.setInt(++i, this.getAuditId());
	  pst.setInt(++i, this.getValoreRange());
	  pst.setString(++i, this.getOperazione());
	  pst.setString(++i, this.getNota());
	  pst.setBoolean(++i, this.isIs_abilitato());
	  pst.execute();
	  pst.close();
	
	} catch (SQLException e){
	  
	  throw new SQLException(e.getMessage());
	} finally {
	 
    }
	return true;
  }

  public boolean isIs_abilitato() {
	return is_abilitato;
}

public void setIs_abilitato(boolean is_abilitato) {
	this.is_abilitato = is_abilitato;
}

public static ArrayList<AuditChecklistType> queryRecord(Connection db, int auditId) throws SQLException {
	ArrayList<AuditChecklistType> ret = new ArrayList<AuditChecklistType>();
		  
	PreparedStatement pst = null;
	ResultSet rs = null;
	pst = db.prepareStatement("SELECT audit_checklist_type.* FROM audit_checklist_type, checklist_type WHERE audit_checklist_type.checklist_type_id = checklist_type.code AND audit_checklist_type.audit_id = ? ORDER BY checklist_type_id" );
	pst.setInt(1, auditId);

	rs = pst.executeQuery();
	
	while (rs.next()) {
	  ret.add( buildRecord(rs) );
    }
	  
	rs.close();
    pst.close();
    return ret;
  }
  
  public int update(Connection db, boolean override) throws SQLException {
	  int resultCount = 0;
	  	  
	  if ("null".equalsIgnoreCase(operazione))
		  operazione = null ;
	  
	  //A valle del tt 16542 sono stati aboliti i tasti + e -. Se c'e'' da sottrarre o aggiungere si vede nel valoreRange in quanto il valore puo' essere sia positivo che negativo
	  operazione="+";
	  PreparedStatement pst = null;
	  StringBuffer sql = new StringBuffer();
	  sql.append("UPDATE audit_checklist_type  SET "); 
	  sql.append("valore_range = ?, ");
	  sql.append("operazione = ?, ");
	  sql.append("nota = ? , ");
	  sql.append("is_abilitato = ? ");
	  sql.append(" WHERE audit_id = ? AND checklist_type_id = ? ");
      int i = 0;
	  pst = db.prepareStatement(sql.toString());
	  pst.setInt( ++i, valoreRange );
	  pst.setString( ++i, operazione );
	  pst.setString( ++i, nota );
	  pst.setBoolean( ++i, is_abilitato );
	  pst.setInt( ++i, auditId ); 
	  pst.setInt( ++i, checklistTypeId ); 
	  //pst.setInt(++i, checklistTypeId);
	  
      resultCount = pst.executeUpdate();
	  pst.close();
      return resultCount;
    }
  
  public static String query(Connection db, int auditId) throws SQLException {
		ArrayList<AuditChecklist> ret = new ArrayList<AuditChecklist>();
		String nota = "";
		PreparedStatement pst = null;
		ResultSet rs = null;
		pst = db.prepareStatement("SELECT audit_checklist_type.nota FROM audit_checklist_type WHERE audit_checklist_type.audit_id = auditId" );
		pst.setInt(1, auditId);
		rs = pst.executeQuery();
		
		while (rs.next()) {
		  
		  nota = rs.getString("nota");
	    }
		  
		rs.close();
	    pst.close();
	    return nota;
	  }
	
  private static AuditChecklistType buildRecord(ResultSet rs) throws SQLException {
    //audit_checklist_type table
	AuditChecklistType temp = new AuditChecklistType();
	
	temp.setChecklistTypeId(rs.getInt("checklist_type_id" ));
	temp.setAuditId(rs.getInt("audit_id"));
	temp.setIs_abilitato(rs.getBoolean("is_abilitato"));
	temp.setValoreRange(rs.getInt("valore_range"));
	temp.setOperazione(rs.getString("operazione"));
	temp.setNota(rs.getString("nota"));
	return temp;
  }
  
  
  
  public void delete(Connection db) throws SQLException {
    boolean commit = db.getAutoCommit();
	try {
	  if (commit) {
	    db.setAutoCommit(false);
	  }
	  
	  PreparedStatement pst = db.prepareStatement("DELETE FROM audit_checklist_type WHERE audit_id = ? ");
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
  
}
