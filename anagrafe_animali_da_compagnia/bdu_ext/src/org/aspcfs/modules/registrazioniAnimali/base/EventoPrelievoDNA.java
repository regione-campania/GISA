/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrazioniAnimali.base;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Furetto;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.DbUtil;
import org.aspcfs.utils.GestoreConnessioni;

public class EventoPrelievoDNA extends Evento{

	
	public static final int idTipologiaDB = 50;
	
	public static final int daConvocare = 1;
	public static final int convocatoNonPresentato = 2;
	public static final int convocatoMaEscluso = 3;
	public static final int presentato = 4;
	
	private java.sql.Timestamp dataPrelievo;
	private int id;
	private int idEvento;
	private int idVeterinario = -1;

	/**
	 * @return the data_evento
	 */




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






	public static int getIdTipologiaDB() {
		return idTipologiaDB;
	}






	public java.sql.Timestamp getDataPrelievo() {
		return dataPrelievo;
	}


	
	public void setDataPrelievo(String dataPrelievo){
		this.dataPrelievo = DateUtils.parseDateStringNew(dataPrelievo, "dd/MM/yyyy");
	}




	public void setDataPrelievo(java.sql.Timestamp dataPrelievo) {
		this.dataPrelievo = dataPrelievo;
	}






	public int getIdVeterinario() {
		return idVeterinario;
	}






	public void setIdVeterinario(int idVeterinario) {
		this.idVeterinario = idVeterinario;
	}
	
	public void setIdVeterinario(String idVeterinario) {
		this.idVeterinario = Integer.parseInt(idVeterinario);
	}







	public EventoPrelievoDNA() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	  
	  public EventoPrelievoDNA(ResultSet rs) throws SQLException {
		    buildRecord(rs);
		  }
	  
	  protected void buildRecord(ResultSet rs) throws SQLException {
		  
		  super.buildRecord(rs);
		  this.idEvento = rs.getInt("idevento");
		  this.dataPrelievo = rs.getTimestamp("data_prelievo");
		  this.idVeterinario = rs.getInt("id_veterinario");
		  
		  
		//  buildSede(rs);
		//  buildRappresentanteLegale(rs);

		  
		  
	  }
	  
	  
		public EventoPrelievoDNA(Connection db, int idEventoPadre) throws SQLException {

		//	super(db, idEventoPadre);

			PreparedStatement pst = db
					.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_prelievo_dna f on (e.id_evento = f.id_evento) where e.id_evento = ?");
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


		 public boolean insert(Connection db) throws SQLException{
			
			 StringBuffer sql = new StringBuffer();
		    try {
		    
		    	  
		    	super.insert(db);
		    	idEvento = super.getIdEvento();
			     
			      
			      
			      id = DatabaseUtils.getNextSeq(db, "evento_prelievo_dna_id_seq");
			     // sql.append("INSERT INTO animale (");
			      

			      
			      
			      
			      sql.append("INSERT INTO evento_prelievo_dna(id_evento, data_prelievo, id_veterinario) VALUES (?,?,?) ");

		
	         

			      int i = 0;
			      PreparedStatement pst = db.prepareStatement(sql.toString());
			      
			    
			      
			  
			    	  pst.setInt(++i, idEvento);
			    	  pst.setTimestamp(++i, dataPrelievo);
			    	  pst.setInt(++i, idVeterinario);
	
			      
			      pst.execute();
			      pst.close();

			      this.id = DatabaseUtils.getCurrVal(db, "evento_prelievo_dna_id_seq", id);
			      
			   
			    	   
			   	    } catch (SQLException e) {
			   	    
			   	      throw new SQLException(e.getMessage());
			   	    } finally {
			   	   
			   	    }
			   	    return true;
			   	  

		 }

		 public boolean aggiornaStatoConvocato(String microchip, int stato, Timestamp data_prelievo) throws SQLException, UnknownHostException{
				
			 //Portale
//			String dbName = ApplicationProperties.getProperty("IMPORTATORIDBNAME");
// 			String username = ApplicationProperties.getProperty("IMPORTATORIDBUSER");
// 			String pwd =ApplicationProperties.getProperty("IMPORTATORIDBPWD");
// 			String port =ApplicationProperties.getProperty("IMPORTATORIDBPORT");
// 			String host = InetAddress.getByName("hostDbImportatori").getHostAddress();
// 			
// 			host = host + ':' +port;
//
// 			Connection db = DbUtil.getConnection(dbName, username,
// 					pwd, host);
			 
			 
			 Connection db = GestoreConnessioni.getConnectionImportatori();
			 
			 StringBuffer sql = new StringBuffer();
		    try {
		    	
		    	 sql.append("select * from aggiorna_stato_convocato_microchip (?,?,?) ");

		    	 int i = 0;
			      PreparedStatement pst = db.prepareStatement(sql.toString());
			      
			      pst.setString(++i, microchip);
			      pst.setInt(++i, stato);
			      pst.setTimestamp(++i, data_prelievo);
			      pst.execute();
			      pst.close();

//			 
			    	   
			   	    } catch (SQLException e) {
			   	     
			   	      throw new SQLException(e.getMessage());
			   	    } finally {
			   	   
			   	      GestoreConnessioni.freeConnectionVam(db);
			   	    }
			   	    return true;
			   	  

		 }
		 

         public void getEventoPrelievo(Connection db, int idAnimale) throws SQLException {

                        //        super(db, idEventoPadre);

                                PreparedStatement pst = db
                                                .prepareStatement("Select f.*, e.*, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento  from animale a left join  evento e on  (a.id = e.id_animale) left join evento_prelievo_dna f on (e.id_evento = f.id_evento) where a.id = ? and e.id_tipologia_evento = ? " +
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
         
         
         
     	public EventoPrelievoDNA salvaRegistrazione(int userId,
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

				// Aggiorno informazioni sul portale
				this.aggiornaStatoConvocato(thisAnimale.getMicrochip(),
						EventoPrelievoDNA.presentato,
						this.getDataPrelievo());

		

				Cane thisCane = (Cane) thisAnimale;
				thisCane.setFlagPrelievoDnaEffettuato(true);

				
				thisAnimale = thisCane;
		
			
    		
    			
    			aggiornaFlagFuoriDominioAsl(db, thisAnimale,  userAsl,  oldAnimale);
    			aggiornaStatoAnimale(db, thisAnimale);

    		} catch (Exception e) {
    			throw e;
    		}

    		return this;

    	}

     	
    	public EventoPrelievoDNA build(ResultSet rs) throws Exception{
    		try{	
    			
    			super.build(rs);
    			buildRecord(rs);
    		
    		}catch (Exception e){
    			throw e;
    		}
    	return this;
    		}

}
