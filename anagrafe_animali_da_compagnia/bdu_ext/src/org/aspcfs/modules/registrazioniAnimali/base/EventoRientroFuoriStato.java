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
import org.aspcfs.utils.GestoreConnessioni;
//import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.DbUtil;
import org.aspcfs.utils.MethodsUtils;

public class EventoRientroFuoriStato extends Evento {
	
	public static final int idTipologiaDB = 42; 
	private java.sql.Timestamp dataRientroFuoriStato;
	private int idProprietario = -1;
	private int idDetentore = -1;
	private int idContinenteDa = -1;
	public static final int idTipologiaDBOperatoreCommerciale = 59; //ritorno a operatore commerciale ??
	public static final int idTipologiaDBRandagio = 60;
	public static final int idRegioneCampania = -1;
	private int idAsl = -1;
	private String luogo = "";
	private int id = -1;
	private int idEvento = -1;
		
	
	
	
	
	
	public EventoRientroFuoriStato() {
		super();
		// TODO Auto-generated constructor stub
	}



	public java.sql.Timestamp getDataRientroFuoriStato() {
		return dataRientroFuoriStato;
	}



	public void setDataRientroFuoriStato(java.sql.Timestamp dataRientroFuoriStato) {
		this.dataRientroFuoriStato = dataRientroFuoriStato;
	}


	public void setDataRientroFuoriStato(String data){
		this.dataRientroFuoriStato = DateUtils.parseDateStringNew(data, "dd/MM/yyyy");
	}
	
	public int getIdProprietario() {
		return idProprietario;
	}



	public void setIdProprietario(int idProprietario) {
		this.idProprietario = idProprietario;
	}
	
	public void setIdProprietario(String idProprietario) {
		this.idProprietario = new Integer (idProprietario).intValue();
	}
	


	public int getIdDetentore() {
		return idDetentore;
	}



	public void setIdDetentore(int idDetentore) {
		this.idDetentore = idDetentore;
	}
	
	public void setIdDetentore(String idDetentore) {
		this.idDetentore = new Integer(idDetentore).intValue();
	}

	public EventoRientroFuoriStato(Connection db, int idEventoPadre) throws SQLException {

		//super(db, idEventoPadre);

		
	//	System.out.println("TABELLA EVENTO RIENTRO DA FUORI STATO NON ESISTENTE!");
		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_rientro_da_fuori_stato f on (e.id_evento = f.id_evento) where e.id_evento = ?");
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
	

	public int getIdContinenteDa() {
		return idContinenteDa;
	}



	public void setIdContinenteDa(int idContinenteDa) {
		this.idContinenteDa = idContinenteDa;
	}

	public int getIdAsl() {
		return idAsl;
	}



	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}

	
	public void setIdAsl(String idAsl) {
		this.idAsl = new Integer (idAsl).intValue();
	}

	public String getLuogo() {
		return luogo;
	}



	public void setLuogo(String luogo) {
		this.luogo = luogo;
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



	public boolean insert(Connection db) throws SQLException {
		
		 StringBuffer sql = new StringBuffer();
	    try {
	    
		      
		    	super.insert(db);
		    	idEvento = super.getIdEvento();
		      
		      
		      id = DatabaseUtils.getNextSeq(db, "evento_rientro_da_fuori_stato_id_seq"); //????
		    		      
		      sql.append("INSERT INTO evento_rientro_da_fuori_stato(" +
		      		"id_evento, data_rientro_fuori_stato, id_asl_fuori_stato  ");
		    		 
	          if (  idProprietario > -1){
	        	 sql.append(",id_proprietario_rientro_fuori_stato " );
	          }
	          
	          if (  idDetentore > -1){
		        	 sql.append(",id_detentore_rientro_fuori_stato " );
		          }
	          
   
	          if (idContinenteDa > -1){
	        	  sql.append(",id_continente_da " );
	          }
	          

	          
	          if (luogo != null && !("").equals(luogo)){
	        	  sql.append(", luogo_fuori_stato ");
	          }
	
     
	       sql.append(")VALUES(?,?,?");
	       
	       if (  idProprietario > -1){
	      	 sql.append(",? " );
	        }
	        
	       if (  idDetentore > -1){
        	  sql.append(",? " );
          }   

	       if (idContinenteDa > -1){
        	  sql.append(",? " );
          }
          
          
          if (luogo != null && !("").equals(luogo)){
        	  sql.append(", ? ");
          }
     
     sql.append(")");

		      int i = 0;
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		      
		    
		      
		  
		    	  pst.setInt(++i, idEvento);
		      
		      
		      
		    	  pst.setTimestamp(++i, dataRientroFuoriStato);
		    	  
		    	  pst.setInt(++i, idAsl);
		     
		    	  if (  idProprietario > -1){
				      	 pst.setInt(++i, idProprietario);
				        }
			       
		    	  if (  idDetentore > -1){
		        	  pst.setInt(++i, idDetentore);
		          }  
			        
		          
		          if (idContinenteDa > -1){
		        	  pst.setInt(++i, idContinenteDa);
		          }

		          
		          if (luogo != null && !("").equals(luogo)){
		        	  pst.setString(++i, luogo);
		          }
			         
		      
		      pst.execute();
		      pst.close();

		      this.id = DatabaseUtils.getCurrVal(db, "evento_rientro_da_fuori_stato_id_seq", id);
		      
		    
		    	   
		   	    } catch (SQLException e) {
		   	    	
		   	      throw new SQLException(e.getMessage());
		   	    } finally {
		   	    	
		   	    }
		   	    return true;
		   	  
		   	 }
	
	

	public  EventoRientroFuoriStato(ResultSet rs) throws SQLException {
		buildRecord(rs);
	} 
		  
		  
	  
	protected void buildRecord(ResultSet rs) throws SQLException {
		  
		
		  super.buildRecord(rs);
		  
		  this.idEvento = rs.getInt("idevento");
		  this.id = rs.getInt("id");
		  this.dataRientroFuoriStato = rs.getTimestamp("data_rientro_fuori_stato");
		  this.idAsl = rs.getInt("id_asl_fuori_stato");
		  this.idProprietario = rs.getInt("id_proprietario_rientro_fuori_stato");
		  this.idDetentore = rs.getInt("id_detentore_rientro_fuori_stato");
		  this.idContinenteDa = rs.getInt("id_continente_da");
		  this.luogo = rs.getString("luogo_fuori_stato");
		  
  
	  }
	
	
	
	
	public Operatore getProprietario() throws UnknownHostException {
		
		Connection conn = null;
		int idOperatore = -1;
		Operatore operatore = null;
		try {
			Thread t = Thread.currentThread();
			conn = GestoreConnessioni.getConnection();

//			String dbName = ApplicationProperties.getProperty("dbnameBdu");
//			String username = ApplicationProperties.getProperty("usernameDbbdu");
//			String pwd = ApplicationProperties.getProperty("passwordDbbdu");
//			String host = InetAddress.getByName("hostDbBdu").getHostAddress();
//
//			conn = DbUtil.getConnection(dbName, username, pwd, host);


		
				idOperatore = this.getIdProprietario();
				

			

			if (idOperatore != -1 && idOperatore != 0) {
				
				operatore = new Operatore();
				operatore
						.queryRecordOperatorebyIdLineaProduttiva(conn,
								idOperatore);
			}

			//GestoreConnessioni.freeConnection(conn);
//			DbUtil.chiudiConnessioneJDBC(null, null, conn);
//			conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			GestoreConnessioni.freeConnection(conn);
		}

		return operatore;
	}
	
	public Operatore getDetentore() throws UnknownHostException {
		
		Connection conn = null;
		int idOperatore = -1;
		Operatore operatore = null;
		try {
			//conn = GestoreConnessioni.getConnection();

//			String dbName = ApplicationProperties.getProperty("dbnameBdu");
//			String username = ApplicationProperties.getProperty("usernameDbbdu");
//			String pwd = ApplicationProperties.getProperty("passwordDbbdu");
//			String host = InetAddress.getByName("hostDbBdu").getHostAddress();
//
//			conn = DbUtil.getConnection(dbName, username, pwd, host);


		
				idOperatore = this.getIdDetentore();
				

			

			if (idOperatore != -1 && idOperatore != 0) {
				
				operatore = new Operatore();
				operatore
						.queryRecordOperatorebyIdLineaProduttiva(conn,
								idOperatore);
			}

			//GestoreConnessioni.freeConnection(conn);
//			DbUtil.chiudiConnessioneJDBC(null, null, conn);
//			conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			GestoreConnessioni.freeConnection(conn);
		}

		return operatore;
	}
	
	
	public EventoRientroFuoriStato salvaRegistrazione(int userId,
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


			
			this.setIdContinenteDa(thisAnimale.getIdContinente());

			Operatore soggettoAdded = new Operatore();

			if (this.getIdProprietario() > -1) {
				thisAnimale.setIdProprietario(this
						.getIdProprietario());

				soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(
						db, this.getIdProprietario());
			} else {
				thisAnimale.setIdProprietario(thisAnimale
						.getIdProprietarioUltimoTrasferimentoFStato());
				this.setIdProprietario(thisAnimale
						.getIdProprietarioUltimoTrasferimentoFStato());
				soggettoAdded
						.queryRecordOperatorebyIdLineaProduttiva(
								db,
								thisAnimale
										.getIdProprietarioUltimoTrasferimentoFStato());
			}

			// Controllo se viene dal circuito commerciale

			Stabilimento stab = (Stabilimento) soggettoAdded
					.getListaStabilimenti().get(0);
			LineaProduttiva lp = (LineaProduttiva) stab
					.getListaLineeProduttive().get(0);
			if (lp.getIdRelazioneAttivita() != LineaProduttiva.IdAggregazioneOperatoreCommerciale) {
				thisAnimale.setFlagCircuitoCommerciale(false);
				if (lp.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneSindaco) {
					this.setIdTipologiaEvento(EventoRientroFuoriStato.idTipologiaDB);
				} else {
					this.setIdTipologiaEvento(EventoRientroFuoriStato.idTipologiaDBRandagio);
				}
			} else {
				this.setIdTipologiaEvento(EventoRientroFuoriStato.idTipologiaDBOperatoreCommerciale);
			}

			int idAsl = stab.getIdAsl();
			this.setIdAsl(idAsl);
			thisAnimale.setIdAslRiferimento(idAsl);

			if (this.getIdDetentore() != -1) {
				thisAnimale.setIdDetentore(this.getIdDetentore());
			} else {
				thisAnimale.setIdDetentore(thisAnimale
						.getIdProprietario());
				this.setIdDetentore(thisAnimale
						.getIdProprietario());
//				thisAnimale.setIdDetentore(thisAnimale
//						.getIdDetentoreUltimoTrasferimentoFStato());
//				this.setIdDetentore(thisAnimale
//						.getIdDetentoreUltimoTrasferimentoFStato());
			}

			thisAnimale.setIdContinente(-1);


			this.insert(db);

			
			aggiornaFlagFuoriDominioAsl(db, thisAnimale,  userAsl,  oldAnimale);
			aggiornaStatoAnimale(db, thisAnimale);

		} catch (Exception e) {
			throw e;
		}

		return this;

	}
	
	
	public EventoRientroFuoriStato build(ResultSet rs) throws Exception {
		try {

			super.build(rs);
			buildRecord(rs);

		} catch (Exception e) {
			throw e;
		}
		return this;
	}
}
