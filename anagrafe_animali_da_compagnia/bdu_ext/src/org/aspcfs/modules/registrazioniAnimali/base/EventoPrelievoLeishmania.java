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
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;

public class EventoPrelievoLeishmania extends Evento {
	
	
	public static final int idTipologiaDB = 54;
	
	
	private int id = -1;
	private int idEvento = -1;
	private java.sql.Timestamp dataPrelievoLeishamania;
	private int idVeterinarioAsl = -1;
	private int idVeterinarioLLPP = -1;
	
	
	
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
	public java.sql.Timestamp getDataPrelievoLeishamania() {
		return dataPrelievoLeishamania;
	}
	public void setDataPrelievoLeishamania(
			java.sql.Timestamp dataPrelievoLeishamania) {
		this.dataPrelievoLeishamania = dataPrelievoLeishamania;
	}
	
	
	public void setDataPrelievoLeishamania(String dataPrelievo) {
		this.dataPrelievoLeishamania = DateUtils.parseDateStringNew(dataPrelievo,
				"dd/MM/yyyy");
	}
	


	public int getIdVeterinarioAsl() {
		return idVeterinarioAsl;
	}
	public void setIdVeterinarioAsl(int idVeterinarioAsl) {
		this.idVeterinarioAsl = idVeterinarioAsl;
	}
	
	public void setIdVeterinarioAsl(String idVeterinario) {
		if (idVeterinario != null)
			this.idVeterinarioAsl = Integer.parseInt(idVeterinario);
	}
	public int getIdVeterinarioLLPP() {
		return idVeterinarioLLPP;
	}
	public void setIdVeterinarioLLPP(int idVeterinarioLLPP) {
		this.idVeterinarioLLPP = idVeterinarioLLPP;
	}
	public void setIdVeterinarioLLPP(String idVeterinario) {
		if (idVeterinario != null)
			this.idVeterinarioLLPP = Integer.parseInt(idVeterinario);
	}
	
	
	public EventoPrelievoLeishmania() {
		super();
		super.setIdTipologiaEvento(idTipologiaDB);
	}
	
	public boolean insert(Connection db) throws SQLException {
		
		StringBuffer sql = new StringBuffer();
	  
	    try {
	    
	    	  
	    	super.insert(db);
	    	idEvento = super.getIdEvento();
	    	
		     
		      
		      id = DatabaseUtils.getNextSeq(db, "evento_prelievo_leish_id_seq");

		      
		      sql.append("INSERT INTO evento_prelievo_leish(id_evento, data_prelievo_leish ");
		      
		      if (idVeterinarioAsl > 0)
		    	  sql.append(" , id_veterinario_asl");
		      
		      if (idVeterinarioLLPP > 0)
		    	  sql.append(" , id_veterinario_llpp");

		      sql.append(")VALUES(?,?");
		      
		      if (idVeterinarioAsl > 0)
		    	  sql.append(" , ?");
		      
		      if (idVeterinarioLLPP > 0)
		    	  sql.append(" , ?");
        
		      sql.append(")");

		      int i = 0;
		      PreparedStatement pst = db.prepareStatement(sql.toString());

		      pst.setInt(++i, idEvento);    
		      pst.setTimestamp(++i, dataPrelievoLeishamania);
		      
		      if (idVeterinarioAsl > 0)
		    	  pst.setInt(++i, idVeterinarioAsl);
		      
		      if (idVeterinarioLLPP > 0)
		    	  pst.setInt(++i, idVeterinarioLLPP);
		     
		    	  
		      pst.execute();
		      pst.close();

		      this.id = DatabaseUtils.getCurrVal(db, "evento_prelievo_leish_id_seq", id);
		      
		    
		    	   
		   	    } catch (SQLException e) {
		   	    	e.printStackTrace();
		   	    
		   	      throw new SQLException(e.getMessage());
		   	    } finally {
		   	    	
		   	    }
	    
		   	    return true;
		   	  
		   	 }
		

	public EventoPrelievoLeishmania (ResultSet rs) 
		// TODO Auto-generated constructor stub
	 throws SQLException {
		    buildRecord(rs);
		  }
	
	
	  
	  protected void buildRecord(ResultSet rs) throws SQLException {
		  
		  super.buildRecord(rs);
		  this.idEvento = rs.getInt("id_evento");
		  this.dataPrelievoLeishamania = rs.getTimestamp("data_prelievo_leish");
		  this.idVeterinarioAsl = rs.getInt("id_veterinario_asl");
		  this.idVeterinarioLLPP = rs.getInt("id_veterinario_llpp");

		  
		  
	  }
	  
	  
		public EventoPrelievoLeishmania(Connection db, int idEventoPadre) throws SQLException {

		//	super(db, idEventoPadre);

			PreparedStatement pst = db
					.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e " +
							"left join evento_prelievo_leish f on (e.id_evento = f.id_evento) where e.id_evento = ?");
			pst.setInt(1, idEventoPadre);
			ResultSet rs = DatabaseUtils.executeQuery(db, pst);
			if (rs.next()) {
				buildRecord(rs);
			}

			if (idEventoPadre == -1) {
			//	throw new SQLException(Constants.NOT_FOUND_ERROR);
			}

			rs.close();
			pst.close();
		}
		
		
		public static java.sql.Timestamp getDataPrimoPrelievoUtente(Connection db, String microchip, int idUtenteInserimento) throws SQLException {

			//	super(db, idEventoPadre);
			
			    java.sql.Timestamp dataPrelievo = null;

				PreparedStatement pst = db
						.prepareStatement("Select MIN (data_prelievo_leish) as data_primo_prelievo_leish from evento e " +
								"left join evento_prelievo_leish f on (e.id_evento = f.id_evento) where e.id_utente_inserimento = ? and e.microchip = ? and e.trashed_date is null and e.data_cancellazione is null");
				pst.setInt(1, idUtenteInserimento);
				pst.setString(2, microchip);
				ResultSet rs = DatabaseUtils.executeQuery(db, pst);
				
				if (rs.next()) {
					dataPrelievo = rs.getTimestamp("data_primo_prelievo_leish");
				}


				rs.close();
				pst.close();
				
				return dataPrelievo;
			}
		
		
		public static java.sql.Timestamp getDataPrimoPrelievoUtente(Connection db, String microchip) throws SQLException {

			//	super(db, idEventoPadre);
			
			    java.sql.Timestamp dataPrelievo = null;

				PreparedStatement pst = db
						.prepareStatement("Select MAX (data_prelievo_leish) as data_ultimo_prelievo_leish from evento_prelievo_leish f " +
								"left join evento e on (e.id_evento = f.id_evento) where  e.microchip = ? and e.trashed_date is null and e.data_cancellazione is null");
			
				pst.setString(1, microchip);
				ResultSet rs = DatabaseUtils.executeQuery(db, pst);
				
				if (rs.next()) {
					dataPrelievo = rs.getTimestamp("data_ultimo_prelievo_leish");
				}


				rs.close();
				pst.close();
				
				
				
				return dataPrelievo;
			}
		
		
		public  EventoPrelievoLeishmania getUltimoPrelievo(String microchip, Connection db) throws SQLException {
			
			
			
			java.sql.Timestamp dataUltimoPrelievo = getDataPrimoPrelievoUtente(db, microchip);
			
			PreparedStatement pst = db
					.prepareStatement("Select *, e.id_evento as idevento,  e.id_asl as idaslinserimentoevento from evento_prelievo_leish f " +
							"left join evento e on (e.id_evento = f.id_evento) where  e.microchip = ? and f.data_prelievo_leish = ? and e.trashed_date is null and e.data_cancellazione is null");
		
			pst.setString(1, microchip);
			pst.setTimestamp(2, dataUltimoPrelievo);
			ResultSet rs = DatabaseUtils.executeQuery(db, pst);
			
			if (rs.next()) {
				buildRecord(rs);
			}


			rs.close();
			pst.close();
			
			return this;
			
		}
		
		
		public EventoPrelievoLeishmania salvaRegistrazione(int userId,
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


				if (this.getIdVeterinarioAsl() < 0) {
					this.setIdVeterinarioAsl(userId);
				}

				
				this.insert(db);

				
				aggiornaFlagFuoriDominioAsl(db, thisAnimale,  userAsl,  oldAnimale);
				aggiornaStatoAnimale(db, thisAnimale);

			} catch (Exception e) {
				throw e;
			}

			return this;

		}
		
		
		public EventoPrelievoLeishmania build(ResultSet rs) throws Exception{
			try{	
				
				super.build(rs);
				buildRecord(rs);
			
			}catch (Exception e){
				throw e;
			}
		return this;
			}
	

}
