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

import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.GestoreConnessioni;


public class EventoAdozioneDistanza  extends Evento{
	
	
	





		public static final int idTipologiaDB = 32;

		private int id = -1;
		private int idEvento = -1;
		private java.sql.Timestamp dataAdozioneDistanza;
		private int idProprietario = -1; //privato adozione
		private int idVecchioProprietario = -1; // sindaco
		private int idDetentore = -1; // canile (non cambia con l'adozione a distanza)


		public EventoAdozioneDistanza() {
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


		public java.sql.Timestamp getDataAdozioneDistanza() {
			return dataAdozioneDistanza;
		}

		public void setDataAdozioneDistanza(java.sql.Timestamp dataAdozioneDistanza) {
			this.dataAdozioneDistanza = dataAdozioneDistanza;
		}

		public void setDataAdozioneDistanza(String dataAdozione) {
			this.dataAdozioneDistanza = DateUtils.parseDateStringNew(dataAdozione,
					"dd/MM/yyyy");
		}

 

		public int getIdDetentore() {
			return idDetentore;
		}

		public void setIdDetentore(int idDetentore) {
			this.idDetentore = idDetentore;
		}

		public int getIdProprietario() {
			return idProprietario;
		}

		public void setIdProprietario(int idProprietario) {
			this.idProprietario = idProprietario;
		}

		public void setIdProprietario(String idProprietario) {
			this.idProprietario = new Integer(idProprietario).intValue();
		}

		public int getIdVecchioProprietario() {
			return idVecchioProprietario;
		}

		public void setIdVecchioProprietario(int idVecchioProprietario) {
			this.idVecchioProprietario = idVecchioProprietario;
		}

		public void setIdVecchioProprietario(String idVecchioProprietario) {
			this.idVecchioProprietario = new Integer(idVecchioProprietario)
					.intValue();
		}


		public boolean insert(Connection db) throws SQLException {

			StringBuffer sql = new StringBuffer();
			
			try {
				
				super.insert(db);
				idEvento = super.getIdEvento();

			
				
				
				id = DatabaseUtils.getNextSeq(db,
						"evento_adozione_distanza_id_seq");
				// sql.append("INSERT INTO animale (");

				sql.append(" INSERT INTO evento_adozione_distanza("
						+ "id_evento, data_adozione_distanza");

				if (idProprietario > -1) {
					sql.append(", id_proprietazio_adozione_distanza");
				}


				if (idVecchioProprietario > -1) { //sindaco?

					sql.append(",id_vecchio_proprietario_adozione_distanza");
				}

				if (idDetentore > -1) {
					sql.append(", id_detentore_adozione_distanza");
				}

				sql.append(")VALUES(?,?");


				if (idProprietario > -1) {
					sql.append(",?");
				}

				if (idVecchioProprietario > -1) {

					sql.append(",?");
				}

				if (idDetentore > -1) {
					sql.append(", ?");
				}

				sql.append(")");

				int i = 0;
				PreparedStatement pst = db.prepareStatement(sql.toString());

				pst.setInt(++i, idEvento);

				pst.setTimestamp(++i, dataAdozioneDistanza);

				if (idProprietario > -1) {
					pst.setInt(++i, idProprietario);
				}

				if (idVecchioProprietario > -1) {

					pst.setInt(++i, idVecchioProprietario);
				}

				if (idDetentore > -1) {
					pst.setInt(++i, idDetentore);
				}
				

				pst.execute();
				pst.close();

				this.id = DatabaseUtils.getCurrVal(db,
						"evento_adozione_distanza_id_seq", id);

			} catch (SQLException e) {
				
				throw new SQLException(e.getMessage());
			} finally {
			}
			return true;

		}

		public EventoAdozioneDistanza(ResultSet rs) throws SQLException {
			buildRecord(rs);
		}

		protected void buildRecord(ResultSet rs) throws SQLException {

			super.buildRecord(rs);
			this.idEvento = rs.getInt("idevento");
			this.dataAdozioneDistanza = rs.getTimestamp("data_adozione_distanza");
			this.idProprietario = rs.getInt("id_proprietazio_adozione_distanza");
			this.idVecchioProprietario = rs.getInt("id_vecchio_proprietario_adozione_distanza");
			this.idDetentore = rs.getInt("id_detentore_adozione_distanza");

			// buildSede(rs);
			// buildRappresentanteLegale(rs);

		}
		
		public EventoAdozioneDistanza(Connection db, int idEventoPadre) throws SQLException {

		//	super(db, idEventoPadre);

			PreparedStatement pst = db
					.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_adozione_distanza f on (e.id_evento = f.id_evento) where e.id_evento = ?");
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

		
		public Operatore getDetentore() {
			
			Connection conn = null;
			int idOperatore = -1;
			Operatore operatore = null;
			try {
				conn = GestoreConnessioni.getConnection();


			
					idOperatore = this.getIdDetentore();
					

				

				if (idOperatore != -1 && idOperatore != 0) {
					
					operatore = new Operatore();
					operatore
							.queryRecordOperatorebyIdLineaProduttiva(conn,
									idOperatore);
				}

				GestoreConnessioni.freeConnection(conn);
				conn = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return operatore;
		}
		
		
		public Operatore getSindacoProvenienza() {
			
			Connection conn = null;
			int idOperatore = -1;
			Operatore operatore = null;
			try {
				conn = GestoreConnessioni.getConnection();


			
					idOperatore = this.getIdVecchioProprietario();
					

				

				if (idOperatore != -1 && idOperatore != 0) {
					
					operatore = new Operatore();
					operatore
							.queryRecordOperatorebyIdLineaProduttiva(conn,
									idOperatore);
				}

				GestoreConnessioni.freeConnection(conn);
				conn = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return operatore;
		}
		
		
		public Operatore getNuovoProprietario() {
			
			Connection conn = null;
			int idOperatore = -1;
			Operatore operatore = null;
			try {
				conn = GestoreConnessioni.getConnection();


			
					idOperatore = this.getIdProprietario();
					

				

				if (idOperatore != -1 && idOperatore != 0) {
					
					operatore = new Operatore();
					operatore
							.queryRecordOperatorebyIdLineaProduttiva(conn,
									idOperatore);
				}

				GestoreConnessioni.freeConnection(conn);
				conn = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return operatore;
		}
		
		
		public EventoAdozioneDistanza build(ResultSet rs) throws Exception{
			try{	
				
				super.build(rs);
				buildRecord(rs);
			
			}catch (Exception e){
				throw e;
			}
		return this;
			}
	}



