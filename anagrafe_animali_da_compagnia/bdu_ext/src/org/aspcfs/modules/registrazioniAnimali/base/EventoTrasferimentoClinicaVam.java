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
import java.sql.Timestamp;

import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.Cane;
import org.aspcfs.modules.anagrafe_animali.base.Furetto;
import org.aspcfs.modules.anagrafe_animali.base.Gatto;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.praticacontributi.base.Pratica;
import org.aspcfs.utils.DatabaseUtils;

public class EventoTrasferimentoClinicaVam extends Evento {
	
	public static final int idTipologiaDB = 62;
	
	private int id = -1;
	private int idEvento = -1;
	private Timestamp dataTrasferimentoAClinica = null;
	private int idClinicaVamOrigine = -1;
	private int idClinicaVamDestinazione = -1;
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
	public int getIdClinicaVamOrigine() {
		return idClinicaVamOrigine;
	}
	public void setIdClinicaVamOrigine(int idClinicaVamOrigine) {
		this.idClinicaVamOrigine = idClinicaVamOrigine;
	}
	public int getIdClinicaVamDestinazione() {
		return idClinicaVamDestinazione;
	}
	public void setIdClinicaVamDestinazione(int idClinicaVamDestinazione) {
		this.idClinicaVamDestinazione = idClinicaVamDestinazione;
	}
	
	
	
	
	
	public Timestamp getDataTrasferimentoAClinica() {
		return dataTrasferimentoAClinica;
	}
	public void setDataTrasferimentoAClinica(Timestamp dataTrasferimentoAClinica) {
		this.dataTrasferimentoAClinica = dataTrasferimentoAClinica;
	}
	
	
	
public boolean insert(Connection db) throws SQLException {
		
		StringBuffer sql = new StringBuffer();
		try {

			
			super.insert(db);
			idEvento = super.getIdEvento();

			

			id = DatabaseUtils.getNextSeq(db, "evento_trasferimento_clinica_id_seq");
			// sql.append("INSERT INTO animale (");

			sql
					.append(" INSERT INTO evento_trasferimento_clinica(id_evento, data_trasferimento_a_clinica ");

			if (idClinicaVamOrigine > 0) {
				sql.append(", id_clinica_origine");
			}

			if (idClinicaVamDestinazione != -1) {
				sql.append(",id_clinica_destinazione");
			}

			

			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idEvento);

			pst.setTimestamp(++i, dataTrasferimentoAClinica);
			if (idClinicaVamOrigine > 0) {
				pst.setInt(++i, idClinicaVamOrigine);
			}

			if (idClinicaVamDestinazione != -1) {
				pst.setInt(++i, idClinicaVamDestinazione);
			}

			pst.execute();
			pst.close();

			this.id = DatabaseUtils.getCurrVal(db,
					"evento_trasferimento_clinica_id_seq", id);

			

		} catch (SQLException e) {
			
			throw new SQLException(e.getMessage());
		} finally {
			
		}
		return true;

	}
	
	
	public EventoTrasferimentoClinicaVam salvaRegistrazione(int userId, int userRole, int userAsl, Animale thisAnimale, Connection db) throws Exception{
		try{	
			
			super.salvaRegistrazione(userId, userRole, userAsl, thisAnimale, db);
			 
			Animale oldAnimale = new Animale(db, this.getIdAnimale());
			
			switch (this.getSpecieAnimaleId()) {
			case Cane.idSpecie:
				thisAnimale = new Cane(db,
						this.getIdAnimale());
				break;
			case Gatto.idSpecie:
				thisAnimale = new Gatto(db,
						this.getIdAnimale());
				break;
			case Furetto.idSpecie:
				thisAnimale = new Furetto(db,
						this.getIdAnimale());
				break;
			default:
				break;
			}
			

			this.insert(db);
			
			
			aggiornaFlagFuoriDominioAsl(db, thisAnimale,  userAsl,  oldAnimale);
			aggiornaStatoAnimale(db, thisAnimale);
		
		}catch (Exception e){
			throw e;
		}
	return this;
		}
		
		
		public EventoTrasferimentoClinicaVam build(ResultSet rs) throws Exception{
			try{	
				
				super.build(rs);
				buildRecord(rs);
			
			}catch (Exception e){
				throw e;
			}
		return this;
			}
		
		protected void buildRecord(ResultSet rs) throws SQLException {

			super.buildRecord(rs);
			this.idEvento = rs.getInt("idevento");
			this.dataTrasferimentoAClinica = rs.getTimestamp("data_trasferimento_a_clinica");
			this.idClinicaVamOrigine = rs.getInt("id_clinica_origine");
			this.idClinicaVamDestinazione = rs.getInt("id_clinica_destinazione");

			// buildSede(rs);
			// buildRappresentanteLegale(rs);

		}
		
		
		
	

}
