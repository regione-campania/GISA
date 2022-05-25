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
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.DwrUtil;
import org.aspcfs.utils.EsitoControllo;
import org.aspcfs.utils.web.LookupList;

public class EventoInserimentoMicrochip extends Evento {
	
	public static final int idTipologiaDB = 3;
	public static final int idTipologiaDBSecondoMicrochip = 38;
	
	public static final int idTipoMicrochipPrimo = 1;
	public static final int idTipoMicrochipSecondo = 1;
	
	private int id;
	private java.sql.Timestamp dataInserimentoMicrochip;
	private String numeroMicrochipAssegnato;
	private int idVeterinarioPrivatoInserimentoMicrochip = -1;
	private int idEvento;
	private int tipoMicrochip = idTipoMicrochipPrimo;

	
	
	
	 public java.sql.Timestamp getDataInserimentoMicrochip() {
		return dataInserimentoMicrochip;
	}
	public void setDataInserimentoMicrochip(
			java.sql.Timestamp dataInserimentoMicrochip) {
		this.dataInserimentoMicrochip = dataInserimentoMicrochip;
	}
	
	public void setDataInserimentoMicrochip(String dataInserimentoMicrochip) {
		this.dataInserimentoMicrochip = DateUtils.parseDateStringNew(
				dataInserimentoMicrochip, "dd/MM/yyyy");
	}
	public String getNumeroMicrochipAssegnato() {
		return numeroMicrochipAssegnato;
	}
	public void setNumeroMicrochipAssegnato(String numeroMicrochipAssegnato) {
		this.numeroMicrochipAssegnato = numeroMicrochipAssegnato;
	}
	public int getIdVeterinarioPrivatoInserimentoMicrochip() {
		return idVeterinarioPrivatoInserimentoMicrochip;
	}
	public void setIdVeterinarioPrivatoInserimentoMicrochip(
			int idVeterinarioPrivatoInserimentoMicrochip) {
		this.idVeterinarioPrivatoInserimentoMicrochip = idVeterinarioPrivatoInserimentoMicrochip;
	}
	public void setIdVeterinarioPrivatoInserimentoMicrochip(
			String idVeterinarioPrivatoInserimentoMicrochip) {
		this.idVeterinarioPrivatoInserimentoMicrochip = new Integer(idVeterinarioPrivatoInserimentoMicrochip).intValue();
	}
	public int getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public int getTipoMicrochip() {
		return tipoMicrochip;
	}
	
	public void setTipoMicrochip(int tipoMicrochip) {
		this.tipoMicrochip = tipoMicrochip;
	}
	
	public void setTipoMicrochip(String tipoMicrochip) {
		this.tipoMicrochip = new Integer(tipoMicrochip).intValue();
	}
	
	public static ArrayList getFields(Connection db){
		 
		 ArrayList fields = new ArrayList();
		 HashMap fields1 = new HashMap();
		 fields1.put("name", "data_inserimento_microchip");
		 fields1.put("type", "data");
		 fields1.put("label", "Data inserimento microchip");
		 fields.add(fields1);
		 
		 fields1 = new HashMap();
		 fields1.put("name", "numero_microchip_assegnato");
		 fields1.put("type", "text");
		 fields1.put("label", "Numero microchip");			 
		 fields.add(fields1);
		 

/*		
		 fields1 = new HashMap();
		 fields1.put("name", "id_veterinario_privato_inserimento_microchip");
		 fields1.put("type", "select");
		 fields1.put("label", "Veterinario inserimento");
		 fields.add(fields1);*/
		 
		
		 
		 
		 return fields;
	 }
	 
	
	public EventoInserimentoMicrochip(){
		super();
	}
	 
	 public boolean insert(Connection db) throws SQLException {
		 
		 StringBuffer sql = new StringBuffer();
	    try {
	   
		    	super.insert(db);
		    	idEvento = super.getIdEvento();
		      
		      id = DatabaseUtils.getNextSeq(db, "evento_inserimento_microchip_id_seq");
		     // sql.append("INSERT INTO animale (");
		      

		      
		      
		      
		      sql.append(" INSERT INTO evento_inserimento_microchip(id_evento, data_inserimento_microchip, numero_microchip_assegnato ");
		    		 
		      if (idVeterinarioPrivatoInserimentoMicrochip != -1){
		    	  sql.append(", id_veterinario_privato_inserimento_microchip");
		      }
		      
		      if (tipoMicrochip > -1){
		    	  sql.append(", id_tipologia_microchip");
		      }
		 
		      sql.append(")VALUES (?,?,?");
		      
		      if (idVeterinarioPrivatoInserimentoMicrochip != -1){
		    	  sql.append(",?");
		      }
		      
		      if (tipoMicrochip > -1){
		    	  sql.append(", ?");
		      }
		      
		      sql.append(")");
		      

        

		      int i = 0;
		      PreparedStatement pst = db.prepareStatement(sql.toString());
		      
		    
		      
		  
		    	  pst.setInt(++i, idEvento);
		      
		      
		      
		    	  pst.setTimestamp(++i, dataInserimentoMicrochip);
		          pst.setString(++i, numeroMicrochipAssegnato);
				    
			      if (idVeterinarioPrivatoInserimentoMicrochip != -1){
			    	 pst.setInt(++i, idVeterinarioPrivatoInserimentoMicrochip);
			      }
			      
			      if (tipoMicrochip > -1){
			    	  pst.setInt(++i, tipoMicrochip);
			      }

		      
		      pst.execute();
		      pst.close();

		      this.id = DatabaseUtils.getCurrVal(db, "evento_inserimento_microchip_id_seq", id);
		      
		    
		    	   
		   	    } catch (SQLException e) {
		   	    
		   	      throw new SQLException(e.getMessage());
		   	    } finally {
		   	   
		   	    }
		   	    return true;
		   	  
		   	 }
	 
	 
		public EventoInserimentoMicrochip(ResultSet rs) throws SQLException {
			buildRecord(rs);
		}

		protected void buildRecord(ResultSet rs) throws SQLException {

			super.buildRecord(rs);
			this.idEvento = rs.getInt("idevento");
			this.dataInserimentoMicrochip = rs.getTimestamp("data_inserimento_microchip");
			this.numeroMicrochipAssegnato = rs.getString("numero_microchip_assegnato");
			this.idVeterinarioPrivatoInserimentoMicrochip = rs
					.getInt("id_veterinario_privato_inserimento_microchip");
			this.tipoMicrochip = rs.getInt("id_tipologia_microchip");


			// buildSede(rs);
			// buildRappresentanteLegale(rs);

		}
		
		public EventoInserimentoMicrochip(Connection db, int idEventoPadre) throws SQLException {

			//	super(db, idEventoPadre);

				PreparedStatement pst = db
						.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_inserimento_microchip f on (e.id_evento = f.id_evento) where e.id_evento = ?");
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
		
		public void GetMicrochipAttivoByIdAnimale(Connection db, int idAnimale) throws SQLException {

			// super(db, idEventoPadre);

			PreparedStatement pst = db
					.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_inserimento_microchip f on (e.id_evento = f.id_evento) where e.id_animale = ? and f.id_tipologia_microchip = 1 and e.data_cancellazione is null");
			pst.setInt(1, idAnimale);
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

		public int update(Connection conn) throws SQLException {
			try {
				super.update(conn);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int result = -1;
			try {
				
				StringBuffer sql = new StringBuffer();

				sql.append("update evento_inserimento_microchip set numero_microchip_assegnato = ?, data_inserimento_microchip = ?, id_veterinario_privato_inserimento_microchip = ? where id_evento = ?");
				PreparedStatement pst = conn.prepareStatement(sql.toString());

				int i = 0;
				pst.setString(++i, this.numeroMicrochipAssegnato);
				pst.setTimestamp(++i, this.dataInserimentoMicrochip);
				pst.setInt(++i, this.idVeterinarioPrivatoInserimentoMicrochip);
				pst.setInt(++i, this.getIdEvento());
				
				result = pst.executeUpdate();
				pst.close();
				
				}catch (SQLException e) {
				
				throw new SQLException(e.getMessage());
			} 

			return result;
		}
		
		
		public EventoInserimentoMicrochip salvaRegistrazione(int userId, int userRole, int userAsl, Animale thisAnimale, Connection db) throws Exception{
		try{	
			
			super.salvaRegistrazione(userId, userRole, userAsl, thisAnimale, db);
			 
			Animale oldAnimale = new Animale(db, this.getIdAnimale());

			this.insert(db);
		//	Animale thisAnimale = new Animale();


			switch (this.getSpecieAnimaleId()) {
			case Cane.idSpecie:
				thisAnimale = new Cane(db,
						this.getIdAnimale());
				thisAnimale.setTatuaggio(this
						.getNumeroMicrochipAssegnato());
				thisAnimale.setDataTatuaggio(this
						.getDataInserimentoMicrochip());
				break;
			case Gatto.idSpecie:
				thisAnimale = new Gatto(db,
						this.getIdAnimale());
				thisAnimale.setTatuaggio(this
						.getNumeroMicrochipAssegnato());
				thisAnimale.setDataTatuaggio(this
						.getDataInserimentoMicrochip());
				break;
			case Furetto.idSpecie:
				thisAnimale = new Furetto(db,
						this.getIdAnimale());
				thisAnimale.setTatuaggio(this
						.getNumeroMicrochipAssegnato());
				thisAnimale.setDataTatuaggio(this
						.getDataInserimentoMicrochip());
				break;
			default:
				break;
			}
			
			
			aggiornaFlagFuoriDominioAsl(db, thisAnimale,  userAsl,  oldAnimale);
			aggiornaStatoAnimale(db, thisAnimale);
			
			EsitoControllo esitoTatu = null;
			EsitoControllo esitoMc = null;
			PreparedStatement pst = null;
			
			if (thisAnimale.getMicrochip() != null && !("").equals(thisAnimale.getMicrochip())) 
				esitoMc = DwrUtil.verificaInserimentoAnimale(thisAnimale.getMicrochip(), userId);

			if (thisAnimale.getTatuaggio() != null && !("").equals(thisAnimale.getTatuaggio())) 
				esitoTatu = DwrUtil.verificaInserimentoAnimale(thisAnimale.getTatuaggio(), userId);
			
			if (esitoMc.getIdEsito() == 1 || esitoMc.getIdEsito() == 4 )
			{
				pst = db.prepareStatement("update microchips set id_animale =? ,id_specie = ? where microchip =? ");
				pst.setInt(1, thisAnimale.getIdAnimale());
				pst.setInt(2, thisAnimale.getIdSpecie());
				pst.setString(3, thisAnimale.getMicrochip());
				pst.execute();
			}
			if (esitoTatu != null && (esitoTatu.getIdEsito() == 1 || esitoTatu.getIdEsito() == 4)) 
			{
				pst = db.prepareStatement("update microchips set id_animale =? ,flag_secondo_microchip = true,id_specie = ? where microchip =? ");
				pst.setInt(1, thisAnimale.getIdAnimale());
				pst.setInt(2, thisAnimale.getIdSpecie());
				pst.setString(3, thisAnimale.getTatuaggio());
				pst.execute();
			}
			
			if (pst != null) 
			{
				pst.close();
			}
		
		}catch (Exception e){
			throw e;
		}
	return this;
		}
		
		public EventoInserimentoMicrochip build(ResultSet rs) throws Exception{
			try{	
				
				super.build(rs);
				buildRecord(rs);
			
			}catch (Exception e){
				throw e;
			}
		return this;
			}
		
}
