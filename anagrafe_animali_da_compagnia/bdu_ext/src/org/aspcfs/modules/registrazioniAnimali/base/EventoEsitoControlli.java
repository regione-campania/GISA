/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrazioniAnimali.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Furetto;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

public class EventoEsitoControlli extends Evento {
	
	public static final int idTipologiaDB = 22;
	
	private int id = -1;
	private int idEvento = -1;
	
	private java.sql.Timestamp dataEsito;
	private boolean flagEhrlichiosi = false;
	private java.sql.Timestamp dataEhrlichiosi;
	private int esitoEhrlichiosi = -1;
	
	private boolean flagRickettiosi = false;
	private java.sql.Timestamp dataRickettiosi;
	private int esitoRickettiosi = -1;
	
	
	
	public java.sql.Timestamp getDataEsito() {
		return dataEsito;
	}
	public void setDataEsito(java.sql.Timestamp dataEsito) {
		this.dataEsito = dataEsito;
	}	
	public void setDataEsito(String dataEsito){
		this.dataEsito = DateUtils.parseDateStringNew(dataEsito, "dd/MM/yyyy");
	}
	public boolean isFlagEhrlichiosi() {
		return flagEhrlichiosi;
	}
	public void setFlagEhrlichiosi(boolean flagEhrlichiosi) {
		this.flagEhrlichiosi = flagEhrlichiosi;
	}
	public void setFlagEhrlichiosi(String flag) {
		this.flagEhrlichiosi = DatabaseUtils.parseBoolean(flag);
	}
	public java.sql.Timestamp getDataEhrlichiosi() {
		return dataEhrlichiosi;
	}
	public void setDataEhrlichiosi(java.sql.Timestamp dataEhrlichiosi) {
		this.dataEhrlichiosi = dataEhrlichiosi;
	}
	public void setDataEhrlichiosi(String dataEhr){
		this.dataEhrlichiosi = DateUtils.parseDateStringNew(dataEhr, "dd/MM/yyyy");
	}
	public int getEsitoEhrlichiosi() {
		return esitoEhrlichiosi;
	}
	public void setEsitoEhrlichiosi(int esitoEhrlichiosi) {
		this.esitoEhrlichiosi = esitoEhrlichiosi;
	}
	public void setEsitoEhrlichiosi(String esitoEhrlichiosi) {
		this.esitoEhrlichiosi = new Integer(esitoEhrlichiosi).intValue();
	}
	
	
	public boolean isFlagRickettiosi() {
		return flagRickettiosi;
	}
	public void setFlagRickettiosi(boolean flagRickettiosi) {
		this.flagRickettiosi = flagRickettiosi;
	}
	public void setFlagRickettiosi(String flag) {
		this.flagRickettiosi = DatabaseUtils.parseBoolean(flag);
	}
	public java.sql.Timestamp getDataRickettiosi() {
		return dataRickettiosi;
	}
	public void setDataRickettiosi(java.sql.Timestamp dataRickettiosi) {
		this.dataRickettiosi = dataRickettiosi;
	}
	public void setDataRickettiosi(String dataRic){
		this.dataRickettiosi = DateUtils.parseDateStringNew(dataRic, "dd/MM/yyyy");
	}
	public int getEsitoRickettiosi() {
		return esitoRickettiosi;
	}
	public void setEsitoRickettiosi(int esitoRickettiosi) {
		this.esitoRickettiosi = esitoRickettiosi;
	}
	public void setEsitoRickettiosi(String esitoRickettiosi) {
		this.esitoRickettiosi = new Integer (esitoRickettiosi).intValue();
	}
	
	
	public EventoEsitoControlli() {
		super();

	}
	
	
	public boolean insert(Connection db) throws SQLException {
		 
		 StringBuffer sql = new StringBuffer();
	    try {
	    
	    	super.insert(db);
	    	idEvento = super.getIdEvento();
	    	
	    	
	    	
		      
		      
		      id = DatabaseUtils.getNextSeq(db, "evento_esito_controlli_id_seq");
		     // sql.append("INSERT INTO animale (");
		      

		      
		      
		      
		      sql.append("INSERT INTO evento_esito_controlli(id_evento, data_controllo ");
		    		 
		      if ( flagEhrlichiosi){
		    	  sql.append(", controllo_ehrlichiosi");
		      }
		      
		      if (dataEhrlichiosi != null){
		    	  sql.append(", data_controllo_ehrlichiosi");
		      }
		      
		      if (esitoEhrlichiosi > -1){
		    	  sql.append(", esito_controllo_ehrlichiosi");
		      }
		      
		      if ( flagRickettiosi){
		    	  sql.append(", controllo_rickettiosi");
		      }
		      
		      if (dataRickettiosi != null){
		    	  sql.append(", data_controllo_rickettiosi");
		      }
		      
		      if (esitoRickettiosi > -1){
		    	  sql.append(", esito_controllo_rickettiosi");
		      }
		 
		    
       
		      
	
         
         sql.append(")VALUES(?,?");

	      if ( flagEhrlichiosi){
	    	  sql.append(", ?");
	      }
	      
	      if (dataEhrlichiosi != null){
	    	  sql.append(", ?");
	      }
	      
	      if (esitoEhrlichiosi > -1){
	    	  sql.append(", ?");
	      }
	      
	      if ( flagRickettiosi){
	    	  sql.append(", ?");
	      }
	      
	      if (dataRickettiosi != null){
	    	  sql.append(", ?");
	      }
	      
	      if (esitoRickettiosi > -1){
	    	  sql.append(", ?");
	      }
         
        
         
         sql.append(")");

		      int i = 0;
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		      
		    
		      
		  
		    	  pst.setInt(++i, idEvento);
		      
		      
		      
		    	  pst.setTimestamp(++i, dataEsito);
		    	  
			      if ( flagEhrlichiosi){
			    	  pst.setBoolean(++i, flagEhrlichiosi);
			      }
			      
			      if (dataEhrlichiosi != null){
			    	  pst.setTimestamp(++i, dataEhrlichiosi);
			      }
			      
			      if (esitoEhrlichiosi > -1){
			    	  pst.setInt(++i, esitoEhrlichiosi);
			      }
			      
			      if ( flagRickettiosi){
			    	  pst.setBoolean(++i, flagRickettiosi);
			      }
			      
			      if (dataRickettiosi != null){
			    	  pst.setTimestamp(++i, dataRickettiosi);
			      }
			      
			      if (esitoRickettiosi > -1){
			    	  pst.setInt(++i, esitoRickettiosi);
			      }

		      
		      pst.execute();
		      pst.close();

		      this.id = DatabaseUtils.getCurrVal(db, "evento_esito_controlli_id_seq", id);
		      
		   
		    	   
		   	    } catch (SQLException e) {
		   	    	
		   	      throw new SQLException(e.getMessage());
		   	    } finally {
		   	    
		   	    }
		   	    return true;
		   	  
		   	 }
	
	
	
		
	
	  public EventoEsitoControlli(ResultSet rs) throws SQLException {
		    buildRecord(rs);
		  }
	  
	  protected void buildRecord(ResultSet rs) throws SQLException {
		  
		  super.buildRecord(rs);
		  this.idEvento = rs.getInt("idevento");
		  this.dataEsito = rs.getTimestamp("data_controllo");
		  this.flagEhrlichiosi = rs.getBoolean("controllo_ehrlichiosi");
		  this.dataEhrlichiosi = rs.getTimestamp("data_controllo_ehrlichiosi");
		  this.esitoEhrlichiosi = rs.getInt("esito_controllo_ehrlichiosi");
		  this.flagRickettiosi = rs.getBoolean("controllo_rickettiosi");
		  this.dataRickettiosi = rs.getTimestamp("data_controllo_rickettiosi");
		  this.esitoRickettiosi = rs.getInt("esito_controllo_rickettiosi");
		  


		  
		  
	  }
	  
	  
	  
	  
		public EventoEsitoControlli(Connection db, int idEventoPadre) throws SQLException {

	//		super(db, idEventoPadre);

			PreparedStatement pst = db
					.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_esito_controlli f on (e.id_evento = f.id_evento) where e.id_evento = ?");
			pst.setInt(1, idEventoPadre);
			ResultSet rs = DatabaseUtils.executeQuery(db, pst);
			if (rs.next()) {
				buildRecord(rs);
			}

			if (idEventoPadre == -1) {
				throw new SQLException(Constants.NOT_FOUND_ERROR);
			}

			rs.close();
			pst.close();
		}
		
		
		public EventoEsitoControlli salvaRegistrazione(int userId,
				int userRole, int userAsl, Animale thisAnimale, Connection db)
				throws Exception {
			try {

				super.salvaRegistrazione(userId, userRole, userAsl, thisAnimale, db);
				
				Animale oldAnimale = new Animale(db, this.getIdAnimale());

				switch (this.getSpecieAnimaleId()) {
				case Cane.idSpecie:
					thisAnimale = new Cane(db, this.getIdAnimale());
					break;
				case Gatto.idSpecie:
					thisAnimale = new Gatto(db, this.getIdAnimale());
					break;
				case Furetto.idSpecie:
					thisAnimale = new Furetto(db, this.getIdAnimale());
					break;
				default:
					break;
				}
				
				this.insert(db);

				// TODO Aggiornare esito dei controlli sul cane
				Cane	thisCane = (Cane) thisAnimale;
				thisCane.updateControlliCane(db, this);
				
				thisAnimale = thisCane;
				
				aggiornaFlagFuoriDominioAsl(db, thisAnimale,  userAsl,  oldAnimale);
				aggiornaStatoAnimale(db, thisAnimale);

			} catch (Exception e) {
				throw e;
			}

			return this;

		}
		
		public EventoEsitoControlli build(ResultSet rs) throws Exception{
			try{	
				
				super.build(rs);
				buildRecord(rs);
			
			}catch (Exception e){
				throw e;
			}
		return this;
			}
	

}
