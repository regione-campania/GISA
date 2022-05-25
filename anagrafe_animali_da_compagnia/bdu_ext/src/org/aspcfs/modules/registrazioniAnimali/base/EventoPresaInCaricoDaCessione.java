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
import java.util.HashMap;

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

public class EventoPresaInCaricoDaCessione extends Evento {

	public static final int idTipologiaDB = 15;
	public static final int idTipologiaDBOperatoreCommerciale = 34;
	public static final int idTipologiaDBRandagioAPrivato = 44;

	public EventoPresaInCaricoDaCessione() {
		super();
		// TODO Auto-generated constructor stub
	}

	private int id = -1;
	private int idEvento = -1;
	private java.sql.Timestamp dataPresaCessione;
	private int idProprietario = -1;
	private int idAsl = -1;

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

	public java.sql.Timestamp getDataPresaCessione() {
		return dataPresaCessione;
	}

	public void setDataPresaCessione(java.sql.Timestamp dataPresaCessione) {
		this.dataPresaCessione = dataPresaCessione;
	}

	public void setDataPresaCessione(String dataPresaCessione) {
		this.dataPresaCessione = DateUtils.parseDateStringNew(
				dataPresaCessione, "dd/MM/yyyy");
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

	public int getIdAsl() {
		return idAsl;
	}

	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}

	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {

			super.insert(db);
			idEvento = super.getIdEvento();

			id = DatabaseUtils.getNextSeq(db,
					"evento_presa_in_carico_cessione_id_seq");
			// sql.append("INSERT INTO animale (");

			sql
					.append("INSERT INTO evento_presa_in_carico_cessione("
							+ "id_evento, data_presa_in_carico, id_nuovo_proprietario_presa_cessione, id_asl  ");

			sql.append(")VALUES(?," );
					
					if (dataPresaCessione != null) {
						sql.append("?, ");
					} else {
						sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
					}
					
					
					sql.append("?,?");

			sql.append(")");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, idEvento);

			if (dataPresaCessione != null)
				pst.setTimestamp(++i, dataPresaCessione);
			

			pst.setInt(++i, idProprietario);

			pst.setInt(++i, idAsl);

			pst.execute();
			pst.close();

			this.id = DatabaseUtils.getCurrVal(db,
					"evento_presa_in_carico_cessione_id_seq", id);

		

		} catch (SQLException e) {
		
			throw new SQLException(e.getMessage());
		} finally {
	
		}
		return true;

	}

	public EventoPresaInCaricoDaCessione(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		super.buildRecord(rs);
		this.idEvento = rs.getInt("idevento");
		this.id = rs.getInt("id");
		this.dataPresaCessione = rs.getTimestamp("data_presa_in_carico");
		this.idProprietario = rs.getInt("id_nuovo_proprietario_presa_cessione");
		this.idAsl = rs.getInt("id_asl");

	}

	public EventoPresaInCaricoDaCessione(Connection db, int idEventoPadre)
			throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select *, e.id_evento as idevento, e.id_asl as idaslinserimentoevento from evento e left join evento_presa_in_carico_cessione f on (e.id_evento = f.id_evento) where e.id_evento = ?");
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

	public Operatore getNuovoProprietario() throws UnknownHostException {

		Connection conn = null;
		int idOperatore = -1;
		Operatore operatore = null;
		try {
			
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
				operatore.queryRecordOperatorebyIdLineaProduttiva(conn,
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
//			finally{
//			DbUtil.chiudiConnessioneJDBC(null, null, conn);
//		}

		return operatore;
	}

	public HashMap aggiungiRegistrazione(Cane thisCane, Connection db) {

		HashMap<Integer, Cane> map = new HashMap<Integer, Cane>();
		int tipologiaRegistrazione = -1;

		// thisCane = new Cane(db, thisAnimale.getIdAnimale());
		thisCane.setIdProprietario(this.getIdProprietario());
		thisCane.setIdDetentore(this.getIdProprietario());
		
		this.setIdStatoOriginale(thisCane.getStato());

		// carico.setIdProprietario(thisAnimale.getIdProprietario());
		// carico.setIdAsl(carico.getIdAsl()); // PRENDERE NUOVA ASL
		// DA
		// PROPRIETARIO DEL CANE E
		// INSERIRE FKEY SU ID ASL E
		// LOOKUP_ASL_RIF

		try {
			this.insert(db);
			
//			// Chiudo l'evento di cessione corrispondente a questa presa
//			// in
//			// carico
			
			EventoCessione cessioneInCorso = new EventoCessione();
			cessioneInCorso.GetCessioneApertaByIdAnimale(db, this.getIdAnimale());
			
			String selectEventoCessione = "update evento_cessione cessione set flag_accettato = true where id_evento = ? ";
			PreparedStatement pst = db.prepareStatement(selectEventoCessione);
			pst.setInt(1, cessioneInCorso.getIdEvento());
			pst.execute();
			
//			+ "where cessione.id in (select evento_cessione.id from evento join evento_cessione "
//			+ " on (evento.id_evento = evento_cessione.id_evento) where evento.id_animale = ? and cessione.flag_accettato = false )";
//
//	PreparedStatement pst = db.prepareStatement(selectEventoCessione);
//	pst.setInt(1, this.getIdAnimale());
//	pst.execute();
			
		
			

//			// Chiudo l'evento di cessione corrispondente a questa presa
//			// in
//			// carico
//			String selectEventoCessione = "update evento_cessione cessione set flag_accettato = true "
//					+ "where cessione.id in (select evento_cessione.id from evento join evento_cessione "
//					+ " on (evento.id_evento = evento_cessione.id_evento) where evento.id_animale = ? and cessione.flag_accettato = false )";
//
//			PreparedStatement pst = db.prepareStatement(selectEventoCessione);
//			pst.setInt(1, this.getIdAnimale());
//			pst.execute();

			Operatore soggettoAdded = new Operatore();
			soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, this
					.getIdProprietario());

			// Controllo se viene dal circuito commerciale
			if (thisCane.isFlagCircuitoCommerciale()) {
				Stabilimento stab = (Stabilimento) soggettoAdded
						.getListaStabilimenti().get(0);
				LineaProduttiva lp = (LineaProduttiva) stab
						.getListaLineeProduttive().get(0);
				if (lp.getIdRelazioneAttivita() != LineaProduttiva.IdAggregazioneOperatoreCommerciale) {

					if (thisCane.getIdSpecie() == Cane.idSpecie)
						thisCane.setFlagCircuitoCommerciale(false);
					else if (thisCane.getIdSpecie() == Gatto.idSpecie)
						thisCane.setFlagCircuitoCommerciale(false);

					tipologiaRegistrazione = this.idTipologiaDB;
				} else {
					tipologiaRegistrazione = this.idTipologiaDBOperatoreCommerciale;
				}
			} else {
				tipologiaRegistrazione = EventoPresaInCaricoDaCessione.idTipologiaDB;
			}

			// Aggiorno dettagli animale e stato in base all'evento inserito
			RegistrazioniWKF wkf = new RegistrazioniWKF();
			wkf.setIdStato(thisCane.getStato());
			wkf.setIdRegistrazione(tipologiaRegistrazione);
			wkf.getProssimoStatoDaStatoPrecedenteERegistrazione(db);
			thisCane.setStato(wkf.getIdProssimoStato());

			// Aggiorno lo stato del cane
			thisCane.setStato(wkf.getIdProssimoStato());
			thisCane.updateStato(db);

		} catch (Exception e) {
			e.printStackTrace();
		}

		map.put(tipologiaRegistrazione, thisCane);

		return map;

	}
	
	
	public HashMap aggiungiRegistrazione(Gatto thisGatto, Connection db) {

		HashMap<Integer, Gatto> map = new HashMap<Integer, Gatto>();
		int tipologiaRegistrazione = -1;

		// thisCane = new Cane(db, thisAnimale.getIdAnimale());
		thisGatto.setIdProprietario(this.getIdProprietario());
		thisGatto.setIdDetentore(this.getIdProprietario());
		this.setIdStatoOriginale(thisGatto.getStato());

		// carico.setIdProprietario(thisAnimale.getIdProprietario());
		// carico.setIdAsl(carico.getIdAsl()); // PRENDERE NUOVA ASL
		// DA
		// PROPRIETARIO DEL CANE E
		// INSERIRE FKEY SU ID ASL E
		// LOOKUP_ASL_RIF

		try {
			this.insert(db);

			// Chiudo l'evento di cessione corrispondente a questa presa
			// in
			// carico
//			String selectEventoCessione = "update evento_cessione cessione set flag_accettato = true "
//					+ "where cessione.id in (select evento_cessione.id from evento join evento_cessione "
//					+ " on (evento.id_evento = evento_cessione.id_evento) where evento.id_animale = ? and cessione.flag_accettato = false )";
//
//			PreparedStatement pst = db.prepareStatement(selectEventoCessione);
//			pst.setInt(1, this.getIdAnimale());
//			pst.execute();
			
			
			EventoCessione cessioneInCorso = new EventoCessione();
			cessioneInCorso.GetCessioneApertaByIdAnimale(db, this.getIdAnimale());
			
			String selectEventoCessione = "update evento_cessione cessione set flag_accettato = true where id_evento = ? ";
			PreparedStatement pst = db.prepareStatement(selectEventoCessione);
			pst.setInt(1, cessioneInCorso.getIdEvento());
			pst.execute();

			Operatore soggettoAdded = new Operatore();
			soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, this
					.getIdProprietario());

			// Controllo se viene dal circuito commerciale
			if (thisGatto.isFlagCircuitoCommerciale()) {
				Stabilimento stab = (Stabilimento) soggettoAdded
						.getListaStabilimenti().get(0);
				LineaProduttiva lp = (LineaProduttiva) stab
						.getListaLineeProduttive().get(0);
				if (lp.getIdRelazioneAttivita() != LineaProduttiva.IdAggregazioneOperatoreCommerciale) {

					if (thisGatto.getIdSpecie() == Cane.idSpecie)
						thisGatto.setFlagCircuitoCommerciale(false);
					else if (thisGatto.getIdSpecie() == Gatto.idSpecie)
						thisGatto.setFlagCircuitoCommerciale(false);

					tipologiaRegistrazione = this.idTipologiaDB;
				} else {
					tipologiaRegistrazione = this.idTipologiaDBOperatoreCommerciale;
				}
			} else {
				tipologiaRegistrazione = EventoPresaInCaricoDaCessione.idTipologiaDB;
			}

			// Aggiorno dettagli animale e stato in base all'evento inserito
			RegistrazioniWKF wkf = new RegistrazioniWKF();
			wkf.setIdStato(thisGatto.getStato());
			wkf.setIdRegistrazione(tipologiaRegistrazione);
			wkf.getProssimoStatoDaStatoPrecedenteERegistrazione(db);
			thisGatto.setStato(wkf.getIdProssimoStato());

			// Aggiorno lo stato del cane
			thisGatto.setStato(wkf.getIdProssimoStato());
			thisGatto.updateStato(db);

		} catch (Exception e) {
			e.printStackTrace();
		}

		map.put(tipologiaRegistrazione, thisGatto);

		return map;

	}

	/*
	 * public Gatto aggiungiRegistrazione(Gatto thisGatto) {
	 * 
	 * boolean ok = false;
	 * 
	 * 
	 * 
	 * 
	 * thisGatto = new Gatto(db, thisAnimale.getIdAnimale());
	 * thisGatto.setIdProprietario(carico.getIdProprietario());
	 * thisGatto.setIdDetentore(carico.getIdProprietario());
	 * 
	 * // carico.setIdProprietario(thisAnimale.getIdProprietario()); //
	 * carico.setIdAsl(carico.getIdAsl()); // PRENDERE NUOVA ASL // DA //
	 * PROPRIETARIO DEL CANE E // INSERIRE FKEY SU ID ASL E // LOOKUP_ASL_RIF
	 * carico.insert(db);
	 * 
	 * // Chiudo l'evento di cessione corrispondente a questa presa // in //
	 * carico String selectEventoCessione =
	 * "update evento_cessione cessione set flag_accettato = true " +
	 * "where cessione.id in (select evento_cessione.id from evento join evento_cessione "
	 * +
	 * " on (evento.id_evento = evento_cessione.id_evento) where evento.id_animale = ? and cessione.flag_accettato = false )"
	 * ;
	 * 
	 * PreparedStatement pst = db.prepareStatement(selectEventoCessione);
	 * pst.setInt(1, carico.getIdAnimale()); pst.execute();
	 * 
	 * Operatore soggettoAdded = new Operatore();
	 * soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db, carico
	 * .getIdProprietario());
	 * 
	 * // Controllo se viene dal circuito commerciale if
	 * (thisAnimale.isFlagCircuitoCommerciale()) { Stabilimento stab =
	 * (Stabilimento) soggettoAdded .getListaStabilimenti().get(0);
	 * LineaProduttiva lp = (LineaProduttiva) stab
	 * .getListaLineeProduttive().get(0); if (lp.getIdRelazioneAttivita() !=
	 * LineaProduttiva.IdAggregazioneOperatoreCommerciale) {
	 * 
	 * if (thisAnimale.getIdSpecie() == Cane.idSpecie)
	 * thisCane.setFlagCircuitoCommerciale(false); else if
	 * (thisAnimale.getIdSpecie() == Gatto.idSpecie)
	 * thisGatto.setFlagCircuitoCommerciale(false);
	 * 
	 * tipologiaRegistrazione = carico.idTipologiaDB; } else {
	 * tipologiaRegistrazione = carico.idTipologiaDBOperatoreCommerciale; } }
	 * else { tipologiaRegistrazione =
	 * EventoPresaInCaricoDaCessione.idTipologiaDB; } break;
	 * 
	 * return thisCane;
	 * 
	 * }
	 */
	
	public EventoPresaInCaricoDaCessione salvaRegistrazione(int userId,
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
			 // presa in
			// carico da
			// cessione
		

			thisAnimale.setIdProprietario(this.getIdProprietario());
			thisAnimale.setIdDetentore(this.getIdProprietario());

			 // PRENDERE NUOVA ASL
			// DA
			// PROPRIETARIO DEL CANE E
			// INSERIRE FKEY SU ID ASL E
			// LOOKUP_ASL_RIF
			this.insert(db);
			

			// Chiudo l'evento di cessione corrispondente a questa presa
			// in
			// carico

			EventoCessione cessioneInCorso = new EventoCessione();
			cessioneInCorso.GetCessioneApertaByIdAnimale(db,
					thisAnimale.getIdAnimale());

			Operatore oldProprietario = new Operatore();
			oldProprietario.queryRecordOperatorebyIdLineaProduttiva(db,
					cessioneInCorso.getIdVecchioProprietario());

			Stabilimento stabPropOld = (Stabilimento) oldProprietario
					.getListaStabilimenti().get(0);
			LineaProduttiva lpPropOld = (LineaProduttiva) stabPropOld
					.getListaLineeProduttive().get(0);

			cessioneInCorso.setFlagAccettato(true);
			cessioneInCorso.updateAccettazione(db);

			Operatore soggettoAdded = new Operatore();
			soggettoAdded.queryRecordOperatorebyIdLineaProduttiva(db,
					this.getIdProprietario());

			Stabilimento stab = (Stabilimento) soggettoAdded
					.getListaStabilimenti().get(0);
			LineaProduttiva lp = (LineaProduttiva) stab
					.getListaLineeProduttive().get(0);

			// Controllo se viene dal circuito commerciale
			if (thisAnimale.isFlagCircuitoCommerciale()) {

				if ((lp.getIdRelazioneAttivita() != LineaProduttiva.IdAggregazioneOperatoreCommerciale && 
						lp.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneImportatore) ) {

					// if (thisAnimale.getIdSpecie() == Cane.idSpecie)
					thisAnimale.setFlagCircuitoCommerciale(false);
					// else if (thisAnimale.getIdSpecie() ==
					// Gatto.idSpecie)
					// thisGatto.setFlagCircuitoCommerciale(false);

					this.setIdTipologiaEvento(EventoPresaInCaricoDaCessione.idTipologiaDB) ;
				} else {
					this.setIdTipologiaEvento( EventoPresaInCaricoDaCessione.idTipologiaDBOperatoreCommerciale) ;
				
				}
			} else if (lpPropOld.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindaco
					&& lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazionePrivato) {
				this.setIdTipologiaEvento( EventoPresaInCaricoDaCessione.idTipologiaDBRandagioAPrivato) ;
			

			}

			else {
				this.setIdTipologiaEvento( EventoPresaInCaricoDaCessione.idTipologiaDB) ;
			}
	
		
			
			aggiornaFlagFuoriDominioAsl(db, thisAnimale,  userAsl,  oldAnimale);
			aggiornaStatoAnimale(db, thisAnimale);

		} catch (Exception e) {
			throw e;
		}

		return this;

	}
	
	public EventoPresaInCaricoDaCessione build(ResultSet rs) throws Exception {
		try {

			super.build(rs);
			buildRecord(rs);

		} catch (Exception e) {
			throw e;
		}
		return this;
	}

}
