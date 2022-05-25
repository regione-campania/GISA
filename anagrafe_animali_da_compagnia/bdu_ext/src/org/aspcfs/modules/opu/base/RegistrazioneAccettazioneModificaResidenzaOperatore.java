/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.AnimaleList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.registrazioniAnimali.base.EventoModificaResidenza;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.DwrUtil;

public class RegistrazioneAccettazioneModificaResidenzaOperatore extends RegistrazioneOperatore {
	
	public static final int idTipologia = 2;
	
	private int id = -1;
	//private int idRelazioneStabilimentoLineaProduttiva = -1;
	private int idRegistrazioneOperatore = -1;
	private int idIdentificativoRegistrazioneModificaResidenza = 1;

	private java.sql.Timestamp dataPresaModificaResidenza;


	
//	
//	private java.sql.Timestamp entered;
//	private java.sql.Timestamp modified;
//	private int modifiedby = -1;
//	private int enteredby = -1;


	public RegistrazioneAccettazioneModificaResidenzaOperatore() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	


//	public java.sql.Timestamp getEntered() {
//		return entered;
//	}
//
//
//
//
//	public void setEntered(java.sql.Timestamp entered) {
//		this.entered = entered;
//	}
//
//
//
//
//	public java.sql.Timestamp getModified() {
//		return modified;
//	}
//
//
//
//
//	public void setModified(java.sql.Timestamp modified) {
//		this.modified = modified;
//	}
//
//
//
//
//	public int getModifiedby() {
//		return modifiedby;
//	}
//
//
//
//
//	public void setModifiedby(int modifiedby) {
//		this.modifiedby = modifiedby;
//	}
//
//
//
//
//	public int getEnteredby() {
//		return enteredby;
//	}
//
//
//
//
//	public void setEnteredby(int enteredby) {
//		this.enteredby = enteredby;
//	}
//



	public int getIdRegistrazioneOperatore() {
		return idRegistrazioneOperatore;
	}




	public void setIdRegistrazioneOperatore(int idRegistrazioneOperatore) {
		this.idRegistrazioneOperatore = idRegistrazioneOperatore;
	}




	public int getIdIdentificativoRegistrazioneModificaResidenza() {
		return idIdentificativoRegistrazioneModificaResidenza;
	}




	public void setIdIdentificativoRegistrazioneModificaResidenza(
			int idIdentificativoRegistrazioneModificaResidenza) {
		this.idIdentificativoRegistrazioneModificaResidenza = idIdentificativoRegistrazioneModificaResidenza;
	}
	
	public void setIdIdentificativoRegistrazioneModificaResidenza(
			String idIdentificativoRegistrazioneModificaResidenza) {
		this.idIdentificativoRegistrazioneModificaResidenza = new Integer (idIdentificativoRegistrazioneModificaResidenza);
	}



//
//	public int getIdRelazioneStabilimentoLineaProduttiva() {
//		return idRelazioneStabilimentoLineaProduttiva;
//	}
//
//
//
//
//	public void setIdRelazioneStabilimentoLineaProduttiva(
//			int idRelazioneStabilimentoLineaProduttiva) {
//		this.idRelazioneStabilimentoLineaProduttiva = idRelazioneStabilimentoLineaProduttiva;
//	}
//	
//	public void setIdRelazioneStabilimentoLineaProduttiva(
//			String idRelazioneStabilimentoLineaProduttiva) {
//		this.idRelazioneStabilimentoLineaProduttiva = new Integer(idRelazioneStabilimentoLineaProduttiva);
//	}




	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public java.sql.Timestamp getDataPresaModificaResidenza() {
		return dataPresaModificaResidenza;
	}


	public void setDataPresaModificaResidenza(
			java.sql.Timestamp dataPresaModificaResidenza) {
		this.dataPresaModificaResidenza = dataPresaModificaResidenza;
	}

	
	public void setDataPresaModificaResidenza(String dataPresaModificaResidenza) {
		this.dataPresaModificaResidenza = DateUtils.parseDateStringNew(dataPresaModificaResidenza, "dd/MM/yyyy");
	}

	



	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();
	//	try {
			super.insert(db);
			idRegistrazioneOperatore = super.getIdRegistrazione();
			

			id = DatabaseUtils.getNextSeq(db,
					"evento_presa_modifica_residenza_operatore_id_seq");
			// sql.append("INSERT INTO animale (");

			sql
					.append(" INSERT INTO evento_presa_modifica_residenza_operatore" +
							" (data_presa_modifica_residenza,  id_rel_stab_lp_pm, id_registrazione_modifica_residenza, id_registrazione ");

		

			sql.append(")VALUES (?,   ?, ?, ?");

			
			
			
			sql.append(" )");
			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

		

			pst.setTimestamp(++i, dataPresaModificaResidenza);
			

			pst.setInt(++i, super.getIdRelazioneStabilimentoLineaProduttiva());

			pst.setInt(++i, idIdentificativoRegistrazioneModificaResidenza);
			pst.setInt(++i, this.getIdRegistrazioneOperatore());
		//	System.out.println("Query inserimento presa modifica  " +pst.toString());

			pst.execute();
			pst.close();

			this.id = DatabaseUtils.getCurrVal(db,
					"evento_presa_modifica_residenza_operatore_id_seq", id);


	//	} catch (SQLException e) {
//			}
//			throw new SQLException(e.getMessage());
//		} finally {
//			}
//		}
		return true;

	}

	public RegistrazioneAccettazioneModificaResidenzaOperatore(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	protected void buildRecord(ResultSet rs) throws SQLException {

		super.buildRecord(rs, null);
	    this.id = rs.getInt("id_presa_modifica");
		this.dataPresaModificaResidenza = rs.getTimestamp("data_presa_modifica_residenza");
		this.idRegistrazioneOperatore = rs.getInt("id_registrazione");

		


		// buildSede(rs);
		// buildRappresentanteLegale(rs);

	}

	public RegistrazioneAccettazioneModificaResidenzaOperatore(Connection db, int idEventoPadre)
			throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select * from evento_presa_modifica_residenza_operatore f  where f.id = ?");
		pst.setInt(1, id);
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

	public Operatore ModificaOperatore(Connection db) throws Exception {
		// TODO
	
		Operatore opToModify =  new Operatore();
		Indirizzo newIndirizzo = new Indirizzo();

		//try {
			
			//Operatore opToModify = new Operatore();
			opToModify.queryRecordOperatorebyIdLineaProduttiva(db, this.getIdRelazioneStabilimentoLineaProduttiva());

			RegistrazioneModificaIndirizzoOperatore regModifica = new RegistrazioneModificaIndirizzoOperatore();
			regModifica.setIdRelazioneStabilimentoLineaProduttiva(this.getIdRelazioneStabilimentoLineaProduttiva());
			regModifica.getUltimaModificaResidenzaOperatore(db);
			 
			this.setIdIdentificativoRegistrazioneModificaResidenza(regModifica.getIdRegistrazioneOperatore());
	//		System.out.println("Settato id richiesta modifica residenza " + regModifica.getIdRegistrazioneOperatore() );
			regModifica.chiudiModificaResidenza(db);
		//	System.out.println("CHIUSA REGISTRAZIONE MODIFICA RESIDENZA CON ID " + regModifica.getId() );
			//newIndirizzo = new Indirizzo(db, regModifica.getIdNuovoIndirizzo());
			
//			if (newIndirizzo.getIdIndirizzo() > 0){  //HO UN INDIRIZZO IN REGIONE
//			// Recupero info asl
//			Object[] asl;
//			asl = DwrUtil.getValoriAsl(newIndirizzo.getComune());
//			if (asl != null && asl.length > 0) {
//
//				Object[] aslVal = (Object[]) asl[0];
//				if (aslVal != null && aslVal.length > 0)
//					newIndirizzo.setIdAsl((Integer) aslVal[0]);
//			}
//			}

			//Se è privato, sindaco, sindaco FR va aggiornata anche la sede legale che coincide con la residenza e indirizzo del sog fisico
			
		
			Stabilimento stab = (Stabilimento) opToModify
					.getListaStabilimenti().get(0);
//			this.idVecchioIndirizzo = stab.getSedeOperativa().getIdIndirizzo();
//			stab.setSedeOperativa(newIndirizzo);
//			stab.setIdAsl(newIndirizzo.getIdAsl());
//			stab.updateSedeOperativa(db);
			
			stab.setModificaResidenzaFuoriAslInCorso(db, false);
			
//			LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
//			if (lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazionePrivato ||
//					lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindaco ||
//					lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindacoFR){
//				newIndirizzo.setTipologiaSede(1);
//				opToModify.getListaSediOperatore().add(newIndirizzo);
//				opToModify.aggiornaRelazioneSede(db, opToModify.getSedeLegale(), newIndirizzo);
//				SoggettoFisico rappLegale = opToModify.getRappLegale();
//				rappLegale.setIndirizzo(newIndirizzo);
//				rappLegale.updateSoloIndirizzo(db);
//			}
//			
//			opToModify.aggiornaVistaMaterializzata(db);
			
			//Gli animalii hanno già sl correttamente settata
/*			AnimaleList listaAnimali = new AnimaleList();
			listaAnimali.setId_proprietario(lp.getId());
			listaAnimali.setBuildProprietario(false);
			listaAnimali.buildList(db);
			
            Animale.updateAslAnimaliProprietario(listaAnimali, stab.getIdAsl(), db);*/

			// Inserimento registrazione
			this.insert(db);
			
//		} catch (Exception e) {
//			// TODO: handle exception
//		}

	//Aggiornare cani 
		
		
		return opToModify;

	}
	
	

	
	




	



}
