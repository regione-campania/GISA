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
import java.util.ArrayList;
import java.util.HashMap;

import org.aspcfs.modules.accounts.base.ComuniAnagrafica;
import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Furetto;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.LookupList;

public class EventoDecesso extends Evento{
	
	public static final int idTipologiaDB = 9;
	
	private int id = -1;
	private int idEvento = -1;
	
	private java.sql.Timestamp dataDecesso;
	private boolean flagDecessoPresunto = false;
	private int idCausaDecesso = -1;
	private String luogoDecesso;
	
	//NUOVI CAMPI OBBLIGATORI DECESSO CR NUOVA GESTIONE DECESSO DIC 2013
	private String indirizzoDecesso = "";
	private int idProvinciaDecesso = -1;
	private int idComuneDecesso = -1;
	
	
	
	
	
	public EventoDecesso() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public boolean isFlagDecessoPresunto() {
		return flagDecessoPresunto;
	}
	public void setFlagDecessoPresunto(boolean flagDecessoPresunto) {
		this.flagDecessoPresunto = flagDecessoPresunto;
	}
	
	public void setFlagDecessoPresunto(String flagDecessoPresunto) {
		this.flagDecessoPresunto = DatabaseUtils.parseBoolean(flagDecessoPresunto);
	}
	
	public int getIdCausaDecesso() {
		return idCausaDecesso;
	}
	public void setIdCausaDecesso(int idCausaDecesso) {
		this.idCausaDecesso = idCausaDecesso;
	}
	public void setIdCausaDecesso(String idCausaDecesso) {
		this.idCausaDecesso = (new Integer (idCausaDecesso) ).intValue();;
	}
	
	
	public String getLuogoDecesso() {
		return luogoDecesso;
	}
	public void setLuogoDecesso(String luogoDecesso) {
		this.luogoDecesso = luogoDecesso;
	}
	public static int getIdTipologiaDB() {
		return idTipologiaDB;
	}

	public void setDataDecesso(String dataDecesso) {
		this.dataDecesso = DateUtils.parseDateStringNew(dataDecesso, "dd/MM/yyyy");
	}
	
	 public java.sql.Timestamp getDataDecesso() {
		return dataDecesso;
	}
	public void setDataDecesso(java.sql.Timestamp dataDecesso) {
		this.dataDecesso = dataDecesso;
	}
	
	
	
	



	public String getIndirizzoDecesso() {
		return indirizzoDecesso;
	}


	public void setIndirizzoDecesso(String indirizzoDecesso) {
		this.indirizzoDecesso = indirizzoDecesso;
	}


	public int getIdProvinciaDecesso() {
		return idProvinciaDecesso;
	}


	public void setIdProvinciaDecesso(int idProvinciaDecesso) {
		this.idProvinciaDecesso = idProvinciaDecesso;
	}
	
	public void setIdProvinciaDecesso(String idProvinciaDecesso) {
		this.idProvinciaDecesso = new Integer (idProvinciaDecesso);
	}


	public int getIdComuneDecesso() {
		return idComuneDecesso;
	}


	public void setIdComuneDecesso(int idComuneDecesso) {
		this.idComuneDecesso = idComuneDecesso;
	}
	
	public void setIdComuneDecesso(String idComuneDecesso) {
		this.idComuneDecesso = new Integer(idComuneDecesso);
	}



	public boolean insert(Connection db) throws SQLException {
		 
		 StringBuffer sql = new StringBuffer();
	    try {
	  
	    	super.insert(db);
	    	idEvento = super.getIdEvento();
	    	
		    
		      
		      
		      id = DatabaseUtils.getNextSeq(db, "evento_decesso_id_seq");
		     // sql.append("INSERT INTO animale (");
		      

		      
		      
		      
		      sql.append(" INSERT INTO evento_decesso(id_evento, data_decesso, flag_decesso_presunto");
		    		 
		      
		 
		    
          if (idCausaDecesso != -1 ){
        	  sql.append(", id_causa_decesso");
          }
          
          if (luogoDecesso != null && !"".equals(luogoDecesso)){
        	  sql.append(",luogo_decesso");
          }
          
          if (idProvinciaDecesso > 0){
        	  sql.append(", id_provincia_decesso");
          }
          
          if (idComuneDecesso > 0){
        	  sql.append(", id_comune_decesso");
          }

          if (indirizzoDecesso != null && !("").equals(indirizzoDecesso)){
        	  sql.append(", indirizzo_decesso");
          }
		      
	
          
          sql.append(")VALUES(?,?,?");
          
          if (idCausaDecesso != -1 ){
        	  sql.append(",?");
          }
          
          if (luogoDecesso != null && !"".equals(luogoDecesso)){
        	  sql.append(",?");
          }
          
          if (idProvinciaDecesso > 0){
        	  sql.append(", ?");
          }
          
          if (idComuneDecesso > 0){
        	  sql.append(", ?");
          }

          if (indirizzoDecesso != null && !("").equals(indirizzoDecesso)){
        	  sql.append(", ?");
          }
          

          
          sql.append(")");

		      int i = 0;
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		      
		    
		      
		  
		    	  pst.setInt(++i, idEvento);
		      
		      
		      
		    	  pst.setTimestamp(++i, dataDecesso);
		          pst.setBoolean(++i, flagDecessoPresunto);
				    
		          if (idCausaDecesso != -1 ){
		        	 pst.setInt(++i, idCausaDecesso);
		          }
		          
		          if (luogoDecesso != null && !"".equals(luogoDecesso)){
		        	  pst.setString(++i, luogoDecesso);
		          }
		          
		          if (idProvinciaDecesso > 0){
		        	  pst.setInt(++i, idProvinciaDecesso);
		          }
		          
		          if (idComuneDecesso > 0){
		        	  pst.setInt(++i, idComuneDecesso);
		          }

		          if (indirizzoDecesso != null && !("").equals(indirizzoDecesso)){
		        	  pst.setString(++i, indirizzoDecesso);
		          }

		      
		      pst.execute();
		      pst.close();

		      this.id = DatabaseUtils.getCurrVal(db, "evento_decesso_id_seq", id);
		      
		  
		    	   
		   	    } catch (SQLException e) {
		   	   
		   	      throw new SQLException(e.getMessage());
		   	    } finally {
		   	   
		   	    }
		   	    return true;
		   	  
		   	 }

	    

	
	
	
	
/*	 public static ArrayList getFields(Connection db){
		 
		 ArrayList fields = new ArrayList();
		 HashMap fields1 = new HashMap();
		 
		 
		 fields1.put("name", "dataDecesso");
		 fields1.put("type", "data");
		 fields1.put("label", "Data decesso");
		 fields.add(fields1);
		 
		 
		 
		 String html = "";
		 try {

			LookupList decessoList = new LookupList(db, "lookup_tipologia_decesso");
			decessoList.addItem(-1, "--Seleziona--");
			 html = 	decessoList.getHtmlSelect("idCausaDecesso", -1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 fields1 = new HashMap();
		 fields1.put("name", "idCausaDecesso");
		 fields1.put("type", "select");
		 fields1.put("label", "Causa decesso");
		 fields1.put("html", html);
		 fields.add(fields1);
		 
		 
		 fields1 = new HashMap();
		 fields1.put("name", "luogoDecesso");
		 fields1.put("type", "text");
		 fields1.put("label", "Luogo decesso");			 
		 fields.add(fields1);
		 
		 
		 
		 fields1 = new HashMap();
		 fields1.put("name", "flagDecessoPresunto");
		 fields1.put("type", "checkbox");
		 fields1.put("label", "Decesso presunto");			 
		 fields.add(fields1);
		

		 
		
		 
		 
		 return fields;
	 }*/
	
	
	 protected void buildRecord(ResultSet rs) throws SQLException {
		  
		  super.buildRecord(rs);
		  this.idEvento = rs.getInt("idevento");
		  this.dataDecesso = rs.getTimestamp("data_decesso");
		  this.flagDecessoPresunto = rs.getBoolean("flag_decesso_presunto");
		  this.idCausaDecesso = rs.getInt("id_causa_decesso");
		  this.luogoDecesso = rs.getString("luogo_decesso");
		  this.idProvinciaDecesso = rs.getInt("id_provincia_decesso");
		  this.idComuneDecesso = rs.getInt("id_comune_decesso");
		  this.indirizzoDecesso = rs.getString("indirizzo_decesso");
		  
		  
		//  buildSede(rs);
		//  buildRappresentanteLegale(rs);

		  
		  
	  }
	
	public EventoDecesso(ResultSet rs) throws SQLException {
	    buildRecord(rs);
	  }
	
	public EventoDecesso(Connection db, int idEventoPadre) throws SQLException {

	//	super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_decesso f on (e.id_evento = f.id_evento) where e.id_evento = ?");
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
	
	
	public void getEventoDecesso(Connection db, int idAnimale) throws SQLException {

		//	super(db, idEventoPadre);

			PreparedStatement pst = db
					.prepareStatement("Select f.*, e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento  from animale a left join  evento e on  (a.id = e.id_animale) left join evento_decesso f on (e.id_evento = f.id_evento) where a.id = ? and e.id_tipologia_evento = ? " +
							"and e.data_cancellazione is null");
			pst.setInt(1, idAnimale);
			pst.setInt(2, idTipologiaDB);
			ResultSet rs = DatabaseUtils.executeQuery(db, pst);
			if (rs.next()) {
				buildRecord(rs);
			}


			if (idAnimale == -1) {
				throw new SQLException(Constants.NOT_FOUND_ERROR);
			}

			rs.close();
			pst.close();
		}
	
	
	public boolean insertUfficio(Connection db) throws SQLException {
		 
	 StringBuffer sql = new StringBuffer();
	    try {
		      
		      id = DatabaseUtils.getNextSeq(db, "evento_decesso_id_seq");
		     // sql.append("INSERT INTO animale (");
		      sql.append(" INSERT INTO evento_decesso(id_evento, data_decesso, flag_decesso_presunto");
		    		 
		    if (idCausaDecesso != -1 ){
       	  sql.append(", id_causa_decesso");
         }
         
         if (luogoDecesso != null && !"".equals(luogoDecesso)){
       	  sql.append(",luogo_decesso");
         }
         

		      
	
         
         sql.append(")VALUES(?,?,?");
         
         if (idCausaDecesso != -1 ){
       	  sql.append(",?");
         }
         
         if (luogoDecesso != null && !"".equals(luogoDecesso)){
       	  sql.append(",?");
         }
         

         
         sql.append(")");

		      int i = 0;
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		      
		    
		      
		  
		    	  pst.setInt(++i, idEvento);
		      
		      
		      
		    	  pst.setTimestamp(++i, dataDecesso);
		          pst.setBoolean(++i, flagDecessoPresunto);
				    
		          if (idCausaDecesso != -1 ){
		        	 pst.setInt(++i, idCausaDecesso);
		          }
		          
		          if (luogoDecesso != null && !"".equals(luogoDecesso)){
		        	  pst.setString(++i, luogoDecesso);
		          }

		      
		      pst.execute();
		      pst.close();

		      this.id = DatabaseUtils.getCurrVal(db, "evento_decesso_id_seq", id);
	   
		   	    } catch (SQLException e) {
		   	     
		   	      throw new SQLException(e.getMessage());
		   	    } finally {
		   	  
		   	    }
		   	    return true;
		   	  
		   	 }
	
	public EventoDecesso salvaRegistrazione(int userId,
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
			thisAnimale.setFlagDecesso(true);
			
			aggiornaFlagFuoriDominioAsl(db, thisAnimale,  userAsl,  oldAnimale);
			aggiornaStatoAnimale(db, thisAnimale);

	

		} catch (Exception e) {
			throw e;
		}

		return this;

	}
	
	
	public EventoDecesso build(ResultSet rs) throws Exception{
		try{	
			
			super.build(rs);
			buildRecord(rs);
		
		}catch (Exception e){
			throw e;
		}
	return this;
		}
}
