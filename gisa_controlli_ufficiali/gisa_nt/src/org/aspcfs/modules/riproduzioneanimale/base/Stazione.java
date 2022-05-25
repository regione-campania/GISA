/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.riproduzioneanimale.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.molluschibivalvi.base.OrganizationAddress;
import org.aspcfs.modules.molluschibivalvi.base.OrganizationAddressList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class Stazione extends GenericBean{

	private static Logger log = Logger.getLogger(org.aspcfs.modules.riproduzioneanimale.base.Stazione.class);

	private int id ;
	private String provv_aut ;
	private String codice_legge_30;
	private String codice_azienda;
	private String tipo_attivita;
	private String sede;
	private String razza;
	private String scadenza_aut;		
	private int org_id;
	
	private boolean monta_equina_attive;
	private boolean stazione_inseminazione_equine;
	private boolean monta_bovina_attive;
	private boolean centro_produzione_sperma;
	private boolean centro_produzione_embrioni;
	private boolean gruppo_raccolta_embrioni;
	private boolean recapiti_autorizzati;
	
	
	public int getOrg_id() {
		return org_id;
	}
	public void setOrg_id(int org_id) {
		this.org_id = org_id;
	}
	public boolean isMonta_equina_attive() {
		return monta_equina_attive;
	}
	public void setMonta_equina_attive(boolean monta_equina_attive) {
		this.monta_equina_attive = monta_equina_attive;
	}
	public boolean isStazione_inseminazione_equine() {
		return stazione_inseminazione_equine;
	}
	public void setStazione_inseminazione_equine(
			boolean stazione_inseminazione_equine) {
		this.stazione_inseminazione_equine = stazione_inseminazione_equine;
	}
	public boolean isMonta_bovine_attive() {
		return monta_bovina_attive;
	}
	public void setMonta_bovine_attive(boolean monta_bovine_attive) {
		this.monta_bovina_attive = monta_bovine_attive;
	}
	public boolean isCentro_produzione_sperma() {
		return centro_produzione_sperma;
	}
	public void setCentro_produzione_sperma(boolean centro_produzione_sperma) {
		this.centro_produzione_sperma = centro_produzione_sperma;
	}
	public boolean isCentro_produzione_embrioni() {
		return centro_produzione_embrioni;
	}
	public void setCentro_produzione_embrioni(boolean centro_produzione_embrioni) {
		this.centro_produzione_embrioni = centro_produzione_embrioni;
	}
	public boolean isGruppo_raccolta_embrioni() {
		return gruppo_raccolta_embrioni;
	}
	public void setGruppo_raccolta_embrioni(boolean gruppo_raccolta_embrioni) {
		this.gruppo_raccolta_embrioni = gruppo_raccolta_embrioni;
	}
	public boolean isRecapiti_autorizzati() {
		return recapiti_autorizzati;
	}
	public void setRecapiti_autorizzati(boolean recapiti_autorizzati) {
		this.recapiti_autorizzati = recapiti_autorizzati;
	}
	

	public Stazione() {
		
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProvv_aut() {
		return provv_aut;
	}
	public void setProvv_aut(String provv_aut) {
		this.provv_aut = provv_aut;
	}
	public String getCodice_legge_30() {
		return codice_legge_30;
	}
	public void setCodice_legge_30(String codice_legge_30) {
		this.codice_legge_30 = codice_legge_30;
	}
	public String getCodice_azienda() {
		return codice_azienda;
	}
	public void setCodice_azienda(String codice_azienda) {
		this.codice_azienda = codice_azienda;
	}
	public String getTipo_attivita() {
		return tipo_attivita;
	}
	public void setTipo_attivita(String tipo_attivita) {
		this.tipo_attivita = tipo_attivita;
	}
	public String getSede() {
		return sede;
	}
	public void setSede(String sede) {
		this.sede = sede;
	}
	public String getRazza() {
		return razza;
	}
	public void setRazza(String razza) {
		this.razza = razza;
	}
	public String getScadenza_aut() {
		return scadenza_aut;
	}
	public void setScadenza_aut(String scadenza_aut) {
		this.scadenza_aut = scadenza_aut;
	}
	public java.sql.Timestamp getEntered() {
		return entered;
	}
	public void setEntered(java.sql.Timestamp entered) {
		this.entered = entered;
	}
	public java.sql.Timestamp getModified() {
		return modified;
	}
	public void setModified(java.sql.Timestamp modified) {
		this.modified = modified;
	}
	public int getEnteredBy() {
		return enteredBy;
	}
	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}
	public int getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getIpEntered() {
		return ipEntered;
	}
	public void setIpEntered(String ipEntered) {
		this.ipEntered = ipEntered;
	}
	public String getIpModified() {
		return ipModified;
	}
	public void setIpModified(String ipModified) {
		this.ipModified = ipModified;
	}
	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}
	public void setPagedListInfo(PagedListInfo pagedListInfo) {
		this.pagedListInfo = pagedListInfo;
	}
	private java.sql.Timestamp entered = null		;
	private java.sql.Timestamp modified = null	;
	private int enteredBy 						;
	private int modifiedBy 						;
	private String ipEntered ;
	private String ipModified ;
	protected PagedListInfo pagedListInfo = null;
	
	public Stazione(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}
	

	protected void buildRecord(ResultSet rs) throws SQLException {

		id 		= 	rs.getInt("id");
		provv_aut = rs.getString("provv_aut");
		codice_legge_30 = rs.getString("codice_legge_30");
		codice_azienda = rs.getString("codice_azienda");
		sede = rs.getString("sede");
		razza = rs.getString("razza");
		scadenza_aut = rs.getString("scadenza_aut");		
		monta_equina_attive = rs.getBoolean("monta_equina_attive");
	    stazione_inseminazione_equine = rs.getBoolean("stazione_inseminazione_equine");
		monta_bovina_attive = rs.getBoolean("monta_bovina_attive");
		centro_produzione_sperma = rs.getBoolean("centro_produzione_sperma");
		centro_produzione_embrioni = rs.getBoolean("centro_produzione_embrioni");
		gruppo_raccolta_embrioni = rs.getBoolean("gruppo_raccolta_embrioni");
	    recapiti_autorizzati = rs.getBoolean("recapiti_autorizzati");
		//modified		=	rs.getTimestamp("modified");
		//modifiedBy	=	rs.getInt("modifiedby");
		//entered 		= 	rs.getTimestamp("entered");
		//enteredBy		=	rs.getInt("enteredby");
		//ipEntered			 	= 	rs.getString("ip_entered");
		//ipModified 			= 	rs.getString("ip_modified");	
	}
	
	public boolean insert(Connection db, int orgId,ActionContext context) throws SQLException {
	    StringBuffer sql = new StringBuffer();
	    boolean doCommit = false;
	    try {
	      modifiedBy = enteredBy;
	      
	      if (doCommit = db.getAutoCommit()) {
	        db.setAutoCommit(false);
	      }
	      	
	      id = DatabaseUtils.getNextSeqInt(db, context,"centri_riproduzione_animale","id");
	     
	      sql.append(
	          "INSERT INTO centri_riproduzione_animale (id, ");
	      
	      if (provv_aut != null) {
		        sql.append("provv_aut, ");
		  }
	      
	      if (codice_legge_30 != null) {
		        sql.append("codice_legge_30, ");
		  }
	      
	      if (razza != null) {
		        sql.append("razza, ");
		  }
	      
	      if (scadenza_aut != null) {
		        sql.append("scadenza_aut, ");
		  }
	      
	      if (sede != null) {
		        sql.append("sede, ");
		  }
	      
	      if (monta_equina_attive) {
		        sql.append("monta_equina_attive, ");
		  }
	      
	      if (monta_bovina_attive) {
		        sql.append("monta_bovina_attive, ");
		  }
	      
	      if (centro_produzione_embrioni) {
		        sql.append("centro_produzione_embrioni, ");
		  }
	      
	      if (centro_produzione_sperma) {
		        sql.append("centro_produzione_sperma, ");
		  }
	      
	      if (stazione_inseminazione_equine) {
		        sql.append("stazione_inseminazione_equine, ");
		  }
	      
	      if (recapiti_autorizzati) {
		        sql.append("recapiti_autorizzati, ");
		  }
	      
	      if (gruppo_raccolta_embrioni) {
		        sql.append("gruppo_raccolta_embrioni, ");
		  }
	      
	      sql.append("org_id");
	      
	      sql.append(")");
	      sql.append(" VALUES (?, ");
	         
	      if (provv_aut != null) {
		        sql.append("?, ");
		  }
	      
	      if (codice_legge_30 != null) {
		        sql.append("?, ");
		  }
	      
	      if (razza != null) {
		        sql.append("?, ");
		  }
	      
	      if (scadenza_aut != null) {
		        sql.append("?, ");
		  }
	      
	      if (sede != null) {
		        sql.append("?, ");
		  }
	      
	      if (monta_equina_attive) {
		        sql.append("?, ");
		  }
	      
	      if (monta_bovina_attive) {
		        sql.append("?, ");
		  }
	      
	      if (centro_produzione_embrioni) {
		        sql.append("?, ");
		  }
	      
	      if (centro_produzione_sperma) {
		        sql.append("?, ");
		  }
	      
	      if (stazione_inseminazione_equine) {
		        sql.append("?, ");
		  }
	      
	      if (recapiti_autorizzati) {
		        sql.append("?, ");
		  }
	      
	      if (gruppo_raccolta_embrioni) {
		        sql.append("?, ");
		  }
	         
	      sql.append("?)");
	      int i = 0;
	      PreparedStatement pst = db.prepareStatement(sql.toString());
	      pst.setInt(++i, id);
	     
	    
	      if (provv_aut != null) {
	    	  pst.setString(++i, provv_aut);
		  }
		      
	      if (codice_legge_30 != null) {
	    	  pst.setString(++i, codice_legge_30);
		  }
	      
	      if (razza != null) {
	    	  pst.setString(++i, razza);
		  }
	      
	      if (scadenza_aut != null) {
	    	  pst.setString(++i, scadenza_aut);
		  }
	      
	      if (sede != null) {
	    	  pst.setString(++i, sede);
		  }
	      
	      if (monta_equina_attive) {
	    	  pst.setBoolean(++i, true);
		  }
	      
	      if (monta_bovina_attive) {
	    	  pst.setBoolean(++i, true);
		  }
	      
	      if (centro_produzione_embrioni) {
	    	  pst.setBoolean(++i, true);
		  }
	      
	      if (centro_produzione_sperma) {
	    	  pst.setBoolean(++i, true);
		  }
	      
	      if (stazione_inseminazione_equine) {
	    	  pst.setBoolean(++i, true);
		  }
	      
	      if (recapiti_autorizzati) {
	    	  pst.setBoolean(++i, true);
		  }
	      
	      if (gruppo_raccolta_embrioni) {
	    	  pst.setBoolean(++i, true);
		  }
	      
	      pst.setInt(++i, orgId);
	      
	      pst.execute();
	      pst.close();
	     
	     
	      //this.update(db, true);
	      if (doCommit) {
	        db.commit();
	      }
	    } catch (SQLException e) {
	    	e.printStackTrace();
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
	
	public int update(Connection db, int orgId) throws SQLException {
		int resultCount = 0;
		int i = 0 ;
		StringBuffer sql = new StringBuffer();
		sql.append(	"UPDATE centri_riproduzione_animale set " );
				
	    if (provv_aut != null) {
	    	sql.append("provv_aut = ?, ");
	    }
		      
		if (codice_legge_30 != null) {
			sql.append("codice_legge_30 = ?, ");
		}
		      
		if (razza != null) {
			sql.append("razza = ?, ");
		}
		      
		if (scadenza_aut != null) {
			sql.append("scadenza_aut = ?, ");
		}
		      
	    if (sede != null) {
			sql.append("sede = ?, ");
	    }
		      
		if (monta_equina_attive) {
			sql.append("monta_equina_attive = ? ");
		}
		      
		if (monta_bovina_attive) {
			sql.append("monta_bovina_attive = ? ");
	    }
		      
		if (centro_produzione_embrioni) {
			sql.append("centro_produzione_embrioni = ? ");
		}
		      
		if (centro_produzione_sperma) {
			sql.append("centro_produzione_sperma = ? ");
		}
		      
		if (stazione_inseminazione_equine) {
			sql.append("stazione_inseminazione_equine = ? ");
		}
		      
		if (recapiti_autorizzati) {
			sql.append("recapiti_autorizzati = ? ");
		}
		      
		if (gruppo_raccolta_embrioni) {
			sql.append("gruppo_raccolta_embrioni = ? ");
		}
		      
		sql.append("where org_id = ? and id = ? ");
		      		
		 PreparedStatement pst = db.prepareStatement(sql.toString());
	    
	      if (provv_aut != null) {
	    	  pst.setString(++i, provv_aut);
		  }
		      
	      if (codice_legge_30 != null) {
	    	  pst.setString(++i, codice_legge_30);
		  }
	      
	      if (razza != null) {
	    	  pst.setString(++i, razza);
		  }
	      
	      if (scadenza_aut != null) {
	    	  pst.setString(++i, scadenza_aut);
		  }
	      
	      if (sede != null) {
	    	  pst.setString(++i, sede);
		  }
	      
	      if (monta_equina_attive) {
	    	  pst.setBoolean(++i, true);
		  }
	      
	      if (monta_bovina_attive) {
	    	  pst.setBoolean(++i, true);
		  }
	      
	      if (centro_produzione_embrioni) {
	    	  pst.setBoolean(++i, true);
		  }
	      
	      if (centro_produzione_sperma) {
	    	  pst.setBoolean(++i, true);
		  }
	      
	      if (stazione_inseminazione_equine) {
	    	  pst.setBoolean(++i, true);
		  }
	      
	      if (recapiti_autorizzati) {
	    	  pst.setBoolean(++i, true);
		  }
	      
	      if (gruppo_raccolta_embrioni) {
	    	  pst.setBoolean(++i, true);
		  }
	      
	      pst.setInt(++i, orgId);
	      pst.setInt(++i, id);
	
		  pst.execute();
		  return resultCount;
	}
	
	
	
	
}
