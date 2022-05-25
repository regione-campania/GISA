/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrazioniAnimali.base;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Furetto;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.GestoreConnessioni;

public class EventoReimmissione extends Evento {
	
	
	public static final int idTipologiaDB = 23;
	private int id = -1;
	private int idEvento = -1;
	
	private java.sql.Timestamp dataReimmissione;
	private int idComuneReimmissione = -1;
	private String luogoReimmissione ="";
	private int idDetentorePrecedente = -1;
	private int idDetentore = -1;  
	
	public int getIdDetentore() {
		return idDetentore;
	}



	public void setIdDetentore(int idDetentore) {
		this.idDetentore = idDetentore;
	}
	
	public void setIdDetentore(String idDetentore) {
		this.idDetentore = new Integer(idDetentore);
	}



	public int getIdDetentorePrecedente() {
		return idDetentorePrecedente;
	}



	public void setIdDetentorePrecedente(int idDetentorePrecedente) {
		this.idDetentorePrecedente = idDetentorePrecedente;
	}



	public EventoReimmissione() {
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
	public java.sql.Timestamp getDataReimmissione() {
		return dataReimmissione;
	}
	public void setDataReimmissione(java.sql.Timestamp dataReimmissione) {
		this.dataReimmissione = dataReimmissione;
	}
	public void setDataReimmissione(String data) {
		this.dataReimmissione = DateUtils.parseDateStringNew(data, "dd/MM/yyyy");
	}
	public int getIdComuneReimmissione() {
		return idComuneReimmissione;
	}
	public void setIdComuneReimmissione(int idComuneReimmissione) {
		this.idComuneReimmissione = idComuneReimmissione;
	}
	public void setIdComuneReimmissione(String idComuneReimmissione) {
		this.idComuneReimmissione = new Integer (idComuneReimmissione).intValue();
	}
	public String getLuogoReimmissione() {
		return luogoReimmissione;
	}
	public void setLuogoReimmissione(String luogoReimmissione) {
		this.luogoReimmissione = luogoReimmissione;
	}
	
	
	
	public boolean insert(Connection db) throws SQLException {
		 
		 StringBuffer sql = new StringBuffer();
	  
	    try {
	    	
	    	
	    	super.insert(db);
	    	idEvento = super.getIdEvento();
	    	
		     
		      
		      
		      id = DatabaseUtils.getNextSeq(db, "evento_reimmissione_id_seq");
		     // sql.append("INSERT INTO animale (");
		      

		      
		      
		      
		      sql.append("INSERT INTO evento_reimmissione(" +
		      		" id_evento, data_reimmissione, id_detentore_precedente, id_detentore_reimmissione");
		    		 
		      
		 
		    
       if (idComuneReimmissione > -1 ){
     	  sql.append(", id_comune_reimmissione");
       }
       
       if (luogoReimmissione != null && !("").equals(luogoReimmissione)){
     	  sql.append(",luogo");
       }

       

		
	
       
       sql.append(")VALUES(?,?, ?, ?");
       
       if (idComuneReimmissione > -1 ){
      	  sql.append(", ?");
        }
        
        if (luogoReimmissione != null && !("").equals(luogoReimmissione)){
      	  sql.append(", ?");
        }
       

       
       sql.append(")");

		      int i = 0;
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		      
		    
		      
		  
		    	  pst.setInt(++i, idEvento);
		      
		      
		      
		    	  pst.setTimestamp(++i, dataReimmissione);
		    	  
		    	  
		    	  pst.setInt(++i, idDetentorePrecedente);
		        
		    	  
		    	  pst.setInt(++i, idDetentore);
				    
		          if (idComuneReimmissione > -1 ){
		         	 pst.setInt(++i, idComuneReimmissione);
		           }
		           
		           if (luogoReimmissione != null && !("").equals(luogoReimmissione)){
		         	  pst.setString(++i, luogoReimmissione);
		           }
		             
		      
		      pst.execute();
		      pst.close();

		      this.id = DatabaseUtils.getCurrVal(db, "evento_reimmissione_id_seq", id);
		      
		  
		   	    } catch (SQLException e) {
		   	    	
		   	      throw new SQLException(e.getMessage());
		   	    } finally {
		   	    
		   	    }
		   	    return true;
		   	  
		   	 }

	
	
	
	  public EventoReimmissione (ResultSet rs) 
		// TODO Auto-generated constructor stub
	 throws SQLException {
		    buildRecord(rs);
		  }
	  
	  protected void buildRecord(ResultSet rs) throws SQLException {
		  
		  super.buildRecord(rs);
		  this.idEvento = rs.getInt("idevento");
		  this.dataReimmissione = rs.getTimestamp("data_reimmissione");
		  this.idComuneReimmissione = rs.getInt("id_comune_reimmissione");
		  this.luogoReimmissione = rs.getString("luogo");
		  this.idDetentorePrecedente = rs.getInt("id_detentore_precedente");
		  this.idDetentore = rs.getInt("id_detentore_reimmissione");
		  
		 

		  
		  
	  }
	  
	  
		public EventoReimmissione(Connection db, int idEventoPadre) throws SQLException {

			//super(db, idEventoPadre);

			PreparedStatement pst = db
					.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_reimmissione f on (e.id_evento = f.id_evento) where e.id_evento = ?");
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
	  
	  

		
		public void GetUltimaReimmissione(Connection db, int idAnimale) throws SQLException {

			// super(db, idEventoPadre);

			PreparedStatement pst = db
					.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_reimmissione f on (e.id_evento = f.id_evento) where e.id_animale = ? and e.id_evento = " +
							"(select max (id_evento) from evento where id_animale = ? and id_tipologia_evento = ?)  ");
			pst.setInt(1, idAnimale);
			pst.setInt(2, idAnimale);
			pst.setInt(3, EventoReimmissione.idTipologiaDB);
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
		
		
		public Operatore getColoniaDetentore() throws UnknownHostException { //Canile detentore  dopo il trasferimento
			
			Connection conn = null;
			int idColonia = -1;
			Operatore operatore = null;
			try {
				conn = GestoreConnessioni.getConnection();
	//
//				String dbName = ApplicationProperties.getProperty("dbnameBdu");
//				String username = ApplicationProperties.getProperty("usernameDbbdu");
//				String pwd = ApplicationProperties.getProperty("passwordDbbdu");
//				String host = InetAddress.getByName("hostDbBdu").getHostAddress();
//				conn = DbUtil.getConnection(dbName, username, pwd, host);


			
				idColonia = this.getIdDetentore();
					

				

				if (idColonia != -1 && idColonia != 0) {
					//System.out.println(idColonia);
					operatore = new Operatore();
					operatore
							.queryRecordOperatorebyIdLineaProduttiva(conn,
									idColonia);
				}

				//GestoreConnessioni.freeConnection(conn);
//				DbUtil.chiudiConnessioneJDBC(null, null, conn);
//				conn = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				GestoreConnessioni.freeConnection(conn);
			}

			return operatore;
		}
		
		public EventoReimmissione salvaRegistrazione(int userId,
				int userRole, int userAsl, Animale thisAnimale, Connection db)
				throws Exception {
			try {

				int idComune = -1;
				super.salvaRegistrazione(userId, userRole, userAsl, thisAnimale, db);
				
				Animale oldAnimale = new Animale(db, this.getIdAnimale());
				this.setIdDetentorePrecedente(thisAnimale.getIdDetentore());
				if (this.getIdDetentore() < 0)
					this.setIdDetentore(thisAnimale.getIdProprietario());
				
				Operatore proprietario = new Operatore();
				proprietario.queryRecordOperatorebyIdLineaProduttiva(db, thisAnimale.getIdProprietario());
				if (proprietario.getIdOperatore() > 0){
					Stabilimento stab = (Stabilimento)proprietario.getListaStabilimenti().get(0);
					idComune = stab.getSedeOperativa().getComune();
					
				}
				
				this.setIdComuneReimmissione(idComune);
				
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
				
				switch (this.getSpecieAnimaleId()) {
				case Cane.idSpecie:
				
				Cane thisCane = (Cane) thisAnimale;
				if (this.getIdDetentore() > 0)
					thisCane.setIdDetentore(this.getIdDetentore()); // Aggiorno
				else 
					thisCane.setIdDetentore(thisCane.getIdProprietario());
				// al nuovo
				// detentore
				thisCane.setFlagRiCattura(true);
				thisCane.setFlagReimmesso(false);	
				
				thisAnimale = thisCane;
				break;
				
				case Gatto.idSpecie:
					Gatto thisGatto = (Gatto) thisAnimale;
					if (this.getIdDetentore() > 0)
						thisGatto.setIdDetentore(this.getIdDetentore()); // Aggiorno
					else
						thisGatto.setIdDetentore(thisGatto.getIdProprietario()); 
					// al nuovo
					// detentore
					thisGatto.setFlagRiCattura(true);
					thisGatto.setFlagReimmesso(false);	
					
					thisAnimale = thisGatto;
					break;
					
//				case Furetto.idSpecie:
//					Furetto thisFuretto = (Furetto) thisAnimale;
//					thisFuretto.setIdDetentore(this.getIdDetentore()); // Aggiorno
//					// al nuovo
//					// detentore
//					thisFuretto.setFlagRiCattura(true);
//					thisFuretto.setFlagReimmesso(false);	
//					
//					thisAnimale = thisFuretto;
//					break;
				}
			
			
				
				aggiornaFlagFuoriDominioAsl(db, thisAnimale,  userAsl,  oldAnimale);
				aggiornaStatoAnimale(db, thisAnimale);

			} catch (Exception e) {
				throw e;
			}

			return this;

		}
		
		public EventoReimmissione build(ResultSet rs) throws Exception {
			try {

				super.build(rs);
				buildRecord(rs);

			} catch (Exception e) {
				throw e;
			}
			return this;
		}
}
