/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.aspcfs.modules.registrazioniAnimali.base.EventoAdozioneDaCanile;
import org.aspcfs.modules.registrazioniAnimali.base.EventoAdozioneDaColonia;
import org.aspcfs.modules.registrazioniAnimali.base.EventoAdozioneFuoriAsl;
import org.aspcfs.modules.registrazioniAnimali.base.EventoCattura;
import org.aspcfs.modules.registrazioniAnimali.base.EventoCessione;
import org.aspcfs.modules.registrazioniAnimali.base.EventoModificaResidenza;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRegistrazioneBDU;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRestituzioneACanile;
import org.aspcfs.modules.registrazioniAnimali.base.EventoRientroFuoriStato;
import org.aspcfs.modules.registrazioniAnimali.base.EventoTrasferimento;
import org.aspcfs.modules.registrazioniAnimali.base.EventoTrasferimentoFuoriStato;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DbUtil;
import org.aspcfs.utils.GestoreConnessioni;
import org.aspcfs.utils.web.LookupList;

public class RegistrazioneOperatore {
	
	private int idRegistrazione = -1;
	private int idRelazioneStabilimentoLineaProduttiva = -1;
	private java.sql.Timestamp entered;
	private java.sql.Timestamp modified;
	private int modifiedby = -1;
	private int enteredby = -1;
	private int idTipologiaRegistrazioneOperatore = -1;
	private int idAslInserimentoRegistrazione = -1;
	private int idTipologiaOperatore = -1;
	private String note = "";	
	private String denominazioneOperatore = "";

		
		
	
	
	
	





	public int getIdRegistrazione() {
		return idRegistrazione;
	}



	public void setIdRegistrazione(int idRegistrazione) {
		this.idRegistrazione = idRegistrazione;
	}



	public int getIdRelazioneStabilimentoLineaProduttiva() {
		return idRelazioneStabilimentoLineaProduttiva;
	}



	public void setIdRelazioneStabilimentoLineaProduttiva(
			int idRelazioneStabilimentoLineaProduttiva) {
		this.idRelazioneStabilimentoLineaProduttiva = idRelazioneStabilimentoLineaProduttiva;
	}
	

	public void setIdRelazioneStabilimentoLineaProduttiva(
			String idRelazioneStabilimentoLineaProduttiva) {
		this.idRelazioneStabilimentoLineaProduttiva = new Integer(idRelazioneStabilimentoLineaProduttiva);
	}



	public java.sql.Timestamp getEntered() {
		return entered;
	}



	public void setEntered(java.sql.Timestamp entered) {
		this.entered = entered;
	}



	public java.sql.Timestamp getModified() {
		return modified;
	}



	public void setModified(java.sql.Timestamp modified) {
		this.modified = modified;
	}



	public int getModifiedby() {
		return modifiedby;
	}



	public void setModifiedby(int modifiedby) {
		this.modifiedby = modifiedby;
	}



	public int getEnteredby() {
		return enteredby;
	}



	public void setEnteredby(int enteredby) {
		this.enteredby = enteredby;
	}



	public int getIdTipologiaRegistrazioneOperatore() {
		return idTipologiaRegistrazioneOperatore;
	}



	public void setIdTipologiaRegistrazioneOperatore(
			int idTipologiaRegistrazioneOperatore) {
		this.idTipologiaRegistrazioneOperatore = idTipologiaRegistrazioneOperatore;
	}



	public int getIdAslInserimentoRegistrazione() {
		return idAslInserimentoRegistrazione;
	}



	public void setIdAslInserimentoRegistrazione(int idAslInserimentoRegistrazione) {
		this.idAslInserimentoRegistrazione = idAslInserimentoRegistrazione;
	}



	public int getIdTipologiaOperatore() {
		return idTipologiaOperatore;
	}



	public void setIdTipologiaOperatore(int idTipologiaOperatore) {
		this.idTipologiaOperatore = idTipologiaOperatore;
	}



	public String getNote() {
		return note;
	}



	public void setNote(String note) {
		this.note = note;
	}
	
	



		public String getDenominazioneOperatore() {
		return denominazioneOperatore;
	}



	public void setDenominazioneOperatore(String denominazioneOperatore) {
		this.denominazioneOperatore = denominazioneOperatore;
	}



		public  RegistrazioneOperatore() {
			// TODO Auto-generated constructor stub
		}
		
		public  RegistrazioneOperatore(ResultSet rs, Connection db) throws SQLException {
			// TODO Auto-generated constructor stub
			this.buildRecord(rs, db);
		}
		


		protected void buildRecord(ResultSet rs, Connection db) throws SQLException {
			
			this.idRegistrazione = rs.getInt("id_registrazione_operatore");
			this.idRelazioneStabilimentoLineaProduttiva = rs.getInt("id_rel_stab_lp");
			this.entered = rs.getTimestamp("data_inserimento_registrazione_reg");
			this.modified = rs.getTimestamp("data_modifica_registrazione_reg");
			this.modifiedby = rs.getInt("modifiedby");
			this.enteredby = rs.getInt("enteredby");
			this.idTipologiaRegistrazioneOperatore = rs.getInt("id_tipologia_registrazione");
			this.idAslInserimentoRegistrazione = rs.getInt("id_asl_inserimento_registrazione");
			
			try {
				rs.findColumn("ragione_sociale");
				this.denominazioneOperatore = rs.getString("ragione_sociale");
			} catch (SQLException sqlex) {
				// System.out.println("not found");
			}
			

			
		}

		public boolean insert(Connection db) throws SQLException {
		//	
			StringBuffer sql = new StringBuffer();
			

			//	idEvento = DatabaseUtils.getNextSeq(db, "evento_id_evento_seq");
				// sql.append("INSERT INTO animale (");

				sql.append("INSERT INTO registrazione_operatore(" + "id_rel_stab_lp, ");

				sql
						.append("entered, modified, enteredby, modifiedby, id_tipologia_registrazione, id_asl_inserimento_registrazione, id_tipologia_proprietario, note");

              

				sql.append(") VALUES ( ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?");



				sql.append(")");

				int i = 0;
				PreparedStatement pst = db.prepareStatement(sql.toString());

				pst.setInt(++i, idRelazioneStabilimentoLineaProduttiva);
				pst.setInt(++i, enteredby);
				pst.setInt(++i, modifiedby);
				pst.setInt(++i, idTipologiaRegistrazioneOperatore);
				pst.setInt(++i, idAslInserimentoRegistrazione);
				pst.setInt(++i, idTipologiaOperatore);
				pst.setString(++i, note);
				

				pst.execute();
				pst.close();

				this.idRegistrazione = DatabaseUtils.getCurrVal(db,
						"registrazione_operatore_id_seq", idRegistrazione);


			
			
			return true;

		}

		// restituisce la data della registrazione in base alla tipologia d evento
		// che stiamo considerando
		public java.sql.Timestamp getDataRegistrazione() {
			
			switch (this.getIdTipologiaRegistrazioneOperatore()) {
			
			case RegistrazioneModificaDimensioneColonia.idTipologia : {
				return  ((RegistrazioneModificaDimensioneColonia) this).getDataCambioDimensioneColonia();				
			}
				
				

			case RegistrazioneModificaIndirizzoOperatore.idTipologia : {
				return  ((RegistrazioneModificaIndirizzoOperatore) this).getDataModificaResidenza();	
			}
			
			case RegistrazioneAccettazioneModificaResidenzaOperatore.idTipologia : {
				return  ((RegistrazioneAccettazioneModificaResidenzaOperatore) this).getDataPresaModificaResidenza();	
			}
				
			}

			
			return this.entered;
		}
		
		
		public String getOrigine() {
			
			switch (this.getIdTipologiaRegistrazioneOperatore()) {
			
			case RegistrazioneModificaDimensioneColonia.idTipologia : {
				return  Integer.toString(((RegistrazioneModificaDimensioneColonia) this).getDimensioneDaModificare());				
			}
				
				

			case RegistrazioneModificaIndirizzoOperatore.idTipologia : {
				
			
				return  ((RegistrazioneModificaIndirizzoOperatore) this).getIndirizzoOrigine();	
			}
			
			case RegistrazioneAccettazioneModificaResidenzaOperatore.idTipologia : {
				return  "";	
			}
				
			}

			
			return "";
		}
		
		
public String getDestinazione() {
			
			switch (this.getIdTipologiaRegistrazioneOperatore()) {
			
			case RegistrazioneModificaDimensioneColonia.idTipologia : {
				return  Integer.toString(((RegistrazioneModificaDimensioneColonia) this).getNuovaDimensione());				
			}			
				

			case RegistrazioneModificaIndirizzoOperatore.idTipologia : {
				
				
				return  ((RegistrazioneModificaIndirizzoOperatore) this).getIndirizzoDestinatario();	
			}
			
			case RegistrazioneAccettazioneModificaResidenzaOperatore.idTipologia : {
				return  "";	
			}
				
			}

			
			return "";
		}


		public String getStato(){
			String toReturn = "--";
			
			if (this.getIdTipologiaRegistrazioneOperatore() == RegistrazioneModificaIndirizzoOperatore.idTipologia ){
				toReturn = "No";
				if (!((RegistrazioneModificaIndirizzoOperatore) this).isModificaCompletata())
					toReturn = "Si";
			}
			
			
			return toReturn;
		}
		

		
		public RegistrazioneOperatore(Connection db, int idEventoToSearch) throws SQLException {
			if (idEventoToSearch == -1) {
				throw new SQLException("Invalid Account");
			}

			PreparedStatement pst = db
					.prepareStatement("Select *, id_evento as idevento, id_asl as idaslinserimentoevento from evento e where e.id_evento = ?");
			pst.setInt(1, idEventoToSearch);
			ResultSet rs = DatabaseUtils.executeQuery(db, pst);
			
			if (rs.next()) {
				this.buildRecord(rs, db);


			}



			rs.close();
			pst.close();
		}
		
		
		public Operatore getIdProprietarioOriginarioRegistrazione() { //Attenzione restituisce l'oggetto operatore, non l'id
			Connection conn = null;
			int idOperatore = -1;
			Operatore proprietarioDestinatarioRegistrazione = null;
			try {
//				String dbName = ApplicationProperties.getProperty("dbnameBdu");
//				String username = ApplicationProperties
//						.getProperty("usernameDbbdu");
//				String pwd = ApplicationProperties.getProperty("passwordDbbdu");
//				String host = InetAddress.getByName("hostDbBdu").getHostAddress();
//
//				conn = DbUtil.getConnection(dbName, username, pwd, host);
				
				conn =GestoreConnessioni.getConnection();
				
	/*			System.out.println("Proprietario:   IdEvento =   " + this.idEvento
						+ "  IdTipologia=   " + this.idTipologiaEvento);*/
				switch (this.getIdTipologiaRegistrazioneOperatore()) {
				
				
				case RegistrazioneModificaIndirizzoOperatore.idTipologia: {
					idOperatore = ((RegistrazioneModificaIndirizzoOperatore)this).getIdRelazioneStabilimentoLineaProduttiva();
					break;
				}
				

				
				////////
		
				
				default: {
					idOperatore = -1;
					break;
				}
				}

				if (idOperatore != -1 && idOperatore != 0) {
				//	
					proprietarioDestinatarioRegistrazione = new Operatore();
					proprietarioDestinatarioRegistrazione
							.queryRecordOperatorebyIdLineaProduttiva(conn,
									idOperatore);
				}


			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				//DbUtil.chiudiConnessioneJDBC(null, null, conn);
				GestoreConnessioni.freeConnection(conn);
			}

			return proprietarioDestinatarioRegistrazione;
		}
		
		
		


		
	}



