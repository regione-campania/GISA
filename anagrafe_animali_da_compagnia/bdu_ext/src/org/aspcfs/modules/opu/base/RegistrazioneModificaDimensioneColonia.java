/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;




public class RegistrazioneModificaDimensioneColonia extends RegistrazioneOperatore {
	public static final int idTipologia = 3;
	
	//private int idRelazioneStabilimentoLineaProduttiva = -1;
	private int idRegistrazioneOperatore = -1;
	
	private int id = -1;
	private java.sql.Timestamp dataCambioDimensioneColonia;
	private int nuovaDimensione;
	private int dimensioneDaModificare;
	
//	private java.sql.Timestamp entered;
//	private java.sql.Timestamp modified;
//	private int modifiedby = -1;
//	private int enteredby = -1;


//	public java.sql.Timestamp getEntered() {
//		return entered;
//	}
//
//
//
//
//
//
//	public void setEntered(java.sql.Timestamp entered) {
//		this.entered = entered;
//	}
//
//
//
//
//
//
//	public java.sql.Timestamp getModified() {
//		return modified;
//	}
//
//
//
//
//
//
//	public void setModified(java.sql.Timestamp modified) {
//		this.modified = modified;
//	}
//
//
//
//
//
//
//	public int getModifiedby() {
//		return modifiedby;
//	}
//
//
//
//
//
//
//	public void setModifiedby(int modifiedby) {
//		this.modifiedby = modifiedby;
//	}
//
//
//
//
//
//
//	public int getEnteredby() {
//		return enteredby;
//	}
//
//
//
//
//
//
//	public void setEnteredby(int enteredby) {
//		this.enteredby = enteredby;
//	}
//





	public RegistrazioneModificaDimensioneColonia() {
		super();
		// TODO Auto-generated constructor stub
	}



	


	public int getIdRegistrazioneOperatore() {
		return idRegistrazioneOperatore;
	}






	public void setIdRegistrazioneOperatore(int idRegistrazioneOperatore) {
		this.idRegistrazioneOperatore = idRegistrazioneOperatore;
	}






	public int getId() {
		return id;
	}






	public void setId(int id) {
		this.id = id;
	}






	public java.sql.Timestamp getDataCambioDimensioneColonia() {
		return dataCambioDimensioneColonia;
	}






	public void setDataCambioDimensioneColonia(
			java.sql.Timestamp dataCambioDimensioneColonia) {
		this.dataCambioDimensioneColonia = dataCambioDimensioneColonia;
	}
	
	
	public void setDataCambioDimensioneColonia(
			String dataCambioDimensioneColonia) {
		this.dataCambioDimensioneColonia = DateUtils.parseDateStringNew(dataCambioDimensioneColonia, "dd/MM/yyyy");;
	}
	
	
	






	public int getNuovaDimensione() {
		return nuovaDimensione;
	}






	public void setNuovaDimensione(int nuovaDimensione) {
		this.nuovaDimensione = nuovaDimensione;
	}
	
	public void setNuovaDimensione(String nuovaDimensione) {
		this.nuovaDimensione = Integer.parseInt(nuovaDimensione);
	}






	public int getDimensioneDaModificare() {
		return dimensioneDaModificare;
	}






	public void setDimensioneDaModificare(int dimensioneDaModificare) {
		this.dimensioneDaModificare = dimensioneDaModificare;
	}
	
	public void setDimensioneDaModificare(String dimensioneDaModificare) {
		this.dimensioneDaModificare = Integer.parseInt(dimensioneDaModificare);
	}
	
	
	






	public boolean insert(Connection db) throws SQLException {
		 
		 StringBuffer sql = new StringBuffer();
	    try {
	    
		      
		      super.insert(db);
		      idRegistrazioneOperatore = super.getIdRegistrazione();
		      id = DatabaseUtils.getNextSeq(db, "evento_modifica_dimensione_colonia_id_seq");
		     // sql.append("INSERT INTO animale (");
		      

		      sql.append("INSERT INTO evento_modifica_dimensione_colonia(data_modifica_dimensione, id_registrazione ");
		    		 
	          if ( dimensioneDaModificare > -1){
	        	 sql.append(", dimensione_da_modificare" );
	          }
	          
	          if (nuovaDimensione > -1){
	        	  sql.append(", nuova_dimensione " );
	          }
	          
	         

         
         sql.append(")VALUES(?, ?");
         
         if ( dimensioneDaModificare > -1){
        	 sql.append(", ?" );
          }
          
          if (nuovaDimensione > -1){
        	  sql.append(", ? " );
          }
          
        
         
         sql.append(")");

		      int i = 0;
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		      
		    pst.setTimestamp(++i, dataCambioDimensioneColonia);
		    pst.setInt(++i, this.getIdRegistrazioneOperatore());
		         
		         if ( dimensioneDaModificare > -1){
		        	 pst.setInt(++i, dimensioneDaModificare);
		          }
		          
		          if (nuovaDimensione > -1){
		        	  pst.setInt(++i, nuovaDimensione);
		          }
		          
		         

		      pst.execute();
		      pst.close();

		      this.id = DatabaseUtils.getCurrVal(db, "evento_modifica_dimensione_colonia_id_seq", id);
		      
		    	   
		   	    } catch (SQLException e) {
		   	      throw new SQLException(e.getMessage());
		   	    } finally {
		   	    }
		   	    return true;
		   	  
		   	 }
		




	public RegistrazioneModificaDimensioneColonia (ResultSet rs) 
		// TODO Auto-generated constructor stub
	 throws SQLException {
		    buildRecord(rs);
		  }
	  
	  protected void buildRecord(ResultSet rs) throws SQLException {
		  
		  super.buildRecord(rs, null);
		  this.id = rs.getInt("id_modifica_dim_colonia");
		  this.dataCambioDimensioneColonia = rs.getTimestamp("data_modifica_dimensione");
		  this.nuovaDimensione = rs.getInt("nuova_dimensione");
		  this.dimensioneDaModificare = rs.getInt("dimensione_da_modificare");
		  this.idRegistrazioneOperatore = rs.getInt("id_registrazione");
		  
//		  this.entered = rs.getTimestamp("data_inserimento_registrazione");
//		  this.modified = rs.getTimestamp("data_modifica_registrazione");
//		  this.modifiedby = rs.getInt("utente_inserimento");
//		  this.enteredby = rs.getInt("utente_modifica");
		  
		  


		  
		  
	  }
	  

				
}

