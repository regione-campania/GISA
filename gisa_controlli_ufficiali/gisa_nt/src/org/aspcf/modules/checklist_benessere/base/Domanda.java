/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.checklist_benessere.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcf.modules.report.util.ApplicationProperties;

import com.darkhorseventures.framework.beans.GenericBean;

public class Domanda extends GenericBean {
										 	

  private static final long serialVersionUID = 1L;
  private int code;
  private String description = "";
  private int level;
  private boolean enabled;
  private int idcap; //chiave esterna alla chk_bns_cap
  private String tipoDomanda ="";
  
  private int classews;
  
	public String getTipoDomanda() {
	return tipoDomanda;
}
public void setTipoDomanda(String tipoDomanda) {
	this.tipoDomanda = tipoDomanda;
}


	public int getClassews() {
	return classews;
}
public void setClassews(int classews) {
	this.classews = classews;
}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public int getIdCap() {
		return idcap;
	}
	public void setIdCap(int idMod) {
		this.idcap = idMod;
	}
  
	
	public static ArrayList<Domanda> buildRecordDomande(Connection db, int capitolo) throws SQLException {
		    
			ArrayList<Domanda> domande = new ArrayList<Domanda>();
			ResultSet rs = null;
			try{
				 String qry = ApplicationProperties.getProperty("GET_CHK_BNS_DOM_BY_ID");
				 PreparedStatement pst = db.prepareStatement(qry);
				 pst.setInt(1,capitolo);
				 rs=pst.executeQuery();
				 while(rs.next()){
					 
					 int codice = rs.getInt("code");
					 String descrizione = rs.getString("description");
					 Domanda d = new Domanda();
					 d.setCode(codice);
					 d.setDescription(descrizione);
					 d.setIdCap(capitolo);
					 d.setClassews(rs.getInt("classe_ws"));
					 d.setTipoDomanda(rs.getString("tipo_domanda"));
					 domande.add(d);
				 }
				 
				 pst.close();
				 rs.close();
				  
			 } catch ( SQLException e) {
			      throw new SQLException(e.getMessage());
			    }
			
			return domande;
			}
	
	
	public static ArrayList<Domanda> buildRecordDomandeGiaInseriti(Connection db, int capitolo, int idMod) throws SQLException {
	    
		ArrayList<Domanda> domande = new ArrayList<Domanda>();
		ResultSet rs = null;
		try{
			 String qry = "select * from chk_bns_risposte where idmodist = ? and idcap = ? order by idcap,iddom";
			 PreparedStatement pst = db.prepareStatement(qry);
			 pst.setInt(1,idMod);
			 pst.setInt(2,capitolo);
			 rs=pst.executeQuery();
			 while(rs.next()){
				 
				 String descrizione = rs.getString("descdom");
				 Domanda d = new Domanda();
				 d.setDescription(descrizione);
				 d.setIdCap(capitolo);
				 domande.add(d);
			 }
			  
		 } catch ( SQLException e) {
		      throw new SQLException(e.getMessage());
		    }
		return domande;
		}
  
} 
