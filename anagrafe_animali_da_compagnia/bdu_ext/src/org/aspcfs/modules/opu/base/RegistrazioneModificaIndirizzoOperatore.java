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






import org.aspcfs.modules.accounts.base.Provincia;
import org.aspcfs.modules.accounts.base.Regione;
import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.AnimaleList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.DwrUtil;

public class RegistrazioneModificaIndirizzoOperatore extends RegistrazioneOperatore {

	
	public static final int idTipologia = 1;
	
	private int id = -1;
	private int idRegistrazioneOperatore = -1;
	//private int idRelazioneStabilimentoLineaProduttiva = -1;

	private java.sql.Timestamp dataModificaResidenza;

	private int idVecchioIndirizzo = -1;
	private int idNuovoIndirizzo = -1;
	private int idRegione = -1;
	private int idComuneModificaResidenza = -1;
	private int idProvinciaModificaResidenza = -1;
	private String via = "";
	private String cap = "";
	private int idAslDestinataria = -1;
	


	private boolean inRegione = true; // Default trasferimento in regione
	private boolean cancellaRegistrazione = false;
	

	
	private boolean modificaCompletata = false;
	
	private String indirizzoOrigine =  "";
	private String indirizzoDestinatario = "";
	

	public String getIndirizzoOrigine() {
		return indirizzoOrigine;
	}






	public void setIndirizzoOrigine(String indirizzoOrigine) {
		this.indirizzoOrigine = indirizzoOrigine;
	}






	public String getIndirizzoDestinatario() {
		return indirizzoDestinatario;
	}






	public void setIndirizzoDestinatario(String indirizzoDestinatario) {
		this.indirizzoDestinatario = indirizzoDestinatario;
	}






	public RegistrazioneModificaIndirizzoOperatore() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	

	
	
	public boolean isModificaCompletata() {
		return modificaCompletata;
	}



	public int getIdAslDestinataria() {
		return idAslDestinataria;
	}






	public void setIdAslDestinataria(int idAslDestinataria) {
		this.idAslDestinataria = idAslDestinataria;
	}



	public void setModificaCompletata(boolean modificaCompletata) {
		this.modificaCompletata = modificaCompletata;
	}






	public int getIdRegione() {
		return idRegione;
	}




	public void setIdRegione(int idRegione) {
		this.idRegione = idRegione;
	}
	
	public void setIdRegione(String idRegione) {
		this.idRegione = new Integer(idRegione).intValue();
	}



	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}
	
	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}



	public int getIdComuneModificaResidenza() {
		return idComuneModificaResidenza;
	}

	public void setIdComuneModificaResidenza(int idComuneModificaResidenza) {
		this.idComuneModificaResidenza = idComuneModificaResidenza;
	}
	
	public void setIdComuneModificaResidenza(String idComuneModificaResidenza) {
		this.idComuneModificaResidenza = new Integer (idComuneModificaResidenza);
	}

	public int getIdProvinciaModificaResidenza() {
		return idProvinciaModificaResidenza;
	}

	public void setIdProvinciaModificaResidenza(int idProvinciaModificaResidenza) {
		this.idProvinciaModificaResidenza = idProvinciaModificaResidenza;
	}
	public void setIdProvinciaModificaResidenza(String idProvinciaModificaResidenza) {
		this.idProvinciaModificaResidenza = new Integer (idProvinciaModificaResidenza);
	}
	
	
	

	public int getIdRegistrazioneOperatore() {
		return idRegistrazioneOperatore;
	}






	public void setIdRegistrazioneOperatore(int idRegistrazioneOperatore) {
		this.idRegistrazioneOperatore = idRegistrazioneOperatore;
	}






	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public java.sql.Timestamp getDataModificaResidenza() {
		return dataModificaResidenza;
	}

	public void setDataModificaResidenza(
			java.sql.Timestamp dataModificaResidenza) {
		this.dataModificaResidenza = dataModificaResidenza;
	}
	
	
	public void setDataModificaResidenza(String dataModificaResidenza) {
		this.dataModificaResidenza = DateUtils.parseDateStringNew(dataModificaResidenza, "dd/MM/yyyy");
	}


	public int getIdVecchioIndirizzo() {
		return idVecchioIndirizzo;
	}

	public void setIdVecchioIndirizzo(int idVecchioIndirizzo) {
		this.idVecchioIndirizzo = idVecchioIndirizzo;
	}

	public int getIdNuovoIndirizzo() {
		return idNuovoIndirizzo;
	}

	public void setIdNuovoIndirizzo(int idNuovoIndirizzo) {
		this.idNuovoIndirizzo = idNuovoIndirizzo;
	}

	
	public void setIdNuovoIndirizzo(String idNuovoIndirizzo) {
		this.idNuovoIndirizzo = new Integer(idNuovoIndirizzo);
	}
	
	
	public boolean isInRegione() {
		return inRegione;
	}

	public void setInRegione(boolean inRegione) {
		this.inRegione = inRegione;
	}
	
	public void setInRegione(String inRegione) {
		this.inRegione = DatabaseUtils.parseBoolean(inRegione);
	}
	


	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();
		try {

			super.insert(db);
			idRegistrazioneOperatore = super.getIdRegistrazione();
			
			
			id = DatabaseUtils.getNextSeq(db,
					"evento_modifica_residenza_operatore_id_seq");
			// sql.append("INSERT INTO animale (");

			sql
					.append(" INSERT INTO evento_modifica_residenza_operatore" +
							" (data_modifica_residenza,    modifica_completata, id_rel_stab_lp_mr, id_registrazione ");

			if (idVecchioIndirizzo > 0) {
				sql.append(", id_vecchio_indirizzo");
			}

			if (idNuovoIndirizzo > 0) {
				sql.append(", id_nuovo_indirizzo");
			}

			sql.append(", in_regione");
			
			
			if (idRegione > 0){
				sql.append(", id_regione");
			}
			
			if (idAslDestinataria > 0){
				sql.append(", id_asl_destinataria");
			}

			sql.append(")VALUES (?,   ?, ?, ?");

			if (idVecchioIndirizzo > 0) {
				sql.append(", ?");
			}

			if (idNuovoIndirizzo > 0) {
				sql.append(", ?");
			}

			sql.append(", ?");
			
			if (idRegione > 0){
				sql.append(", ?");
			}
			
			if (idAslDestinataria > 0){
				sql.append(", ?");
			}

			
			
			sql.append(" )");
			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

		

			pst.setTimestamp(++i, dataModificaResidenza);
			

			pst.setBoolean(++i, modificaCompletata);
			pst.setInt(++i, super.getIdRelazioneStabilimentoLineaProduttiva());

			pst.setInt(++i, this.getIdRegistrazioneOperatore());
			

			if (idVecchioIndirizzo > 0) {
				pst.setInt(++i, idVecchioIndirizzo);
			}

			if (idNuovoIndirizzo > 0) {
				pst.setInt(++i, idNuovoIndirizzo);
			}

			pst.setBoolean(++i, inRegione);
			
			if (idRegione > 0){
				pst.setInt(++i, idRegione);
			}
			
			if (idAslDestinataria > 0){
				pst.setInt(++i, idAslDestinataria);
			}

			pst.execute();
			pst.close();

			this.id = DatabaseUtils.getCurrVal(db,
					"evento_modifica_residenza_operatore_id_seq", id);


		} catch (SQLException e) {
			
			e.printStackTrace();
			//throw new SQLException(e.getMessage());
		} finally {
		}
		return true;

	}

	public RegistrazioneModificaIndirizzoOperatore(ResultSet rs) throws SQLException {
		buildRecord(rs, null);
	}
	
	public RegistrazioneModificaIndirizzoOperatore(ResultSet rs, Connection db) throws SQLException {
		buildRecord(rs, db);
	}

	protected void buildRecord(ResultSet rs, Connection db) throws SQLException {

		super.buildRecord(rs, null);
	
		this.dataModificaResidenza = rs.getTimestamp("data_modifica_residenza");
		this.id = rs.getInt("id_modifica_residenza");
		this.inRegione = rs.getBoolean("in_regione");
		this.idNuovoIndirizzo = rs.getInt("id_nuovo_indirizzo");
		this.idVecchioIndirizzo = rs.getInt("id_vecchio_indirizzo");
		this.idRegistrazioneOperatore = rs.getInt("id_registrazione");
		this.modificaCompletata = rs.getBoolean("modifica_completata");
		this.idRegione = rs.getInt("id_regione");
		this.idAslDestinataria = rs.getInt("id_asl_destinataria");
		
		
		if (db != null){
			
	if(this.getIdTipologiaRegistrazioneOperatore() ==  RegistrazioneModificaIndirizzoOperatore.idTipologia) {
		Indirizzo oldInd = new Indirizzo();
		
		try{

		
		
		 oldInd = new Indirizzo(db, ((RegistrazioneModificaIndirizzoOperatore) this).getIdVecchioIndirizzo());
		}catch (Exception e) {
			// TODO: handle exception
		}
		indirizzoOrigine =   oldInd.toString();	
	}
	
	
	if(this.getIdTipologiaRegistrazioneOperatore() ==  RegistrazioneModificaIndirizzoOperatore.idTipologia) {
		Indirizzo newInd = new Indirizzo();
		
		try{

		
		
		 newInd = new Indirizzo(db, ((RegistrazioneModificaIndirizzoOperatore) this).getIdNuovoIndirizzo());
		}catch (Exception e) {
			// TODO: handle exception
		}
		indirizzoDestinatario =   newInd.toString();	
		if(indirizzoDestinatario==null || indirizzoDestinatario.equals(""))
		{
			Regione r = new Regione(db, idRegione);
			indirizzoDestinatario = r.getLabel();
		}
	}
		}


		// buildSede(rs);
		// buildRappresentanteLegale(rs);

	}

	public RegistrazioneModificaIndirizzoOperatore(Connection db, int idEventoPadre)
			throws SQLException {

		// super(db, idEventoPadre);

		PreparedStatement pst = db
				.prepareStatement("Select * from evento_modifica_residenza f  where f.id = ?");
		pst.setInt(1, id);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst);
		if (rs.next()) {
			buildRecord(rs, db);
		}

		if (idEventoPadre == -1) {
			throw new SQLException(Constants.NOT_FOUND_ERROR);
		}

		rs.close();
		pst.close();
	}

	public Operatore ModificaOperatore(Connection db, Operatore opToModify, int idUtenteModifica) throws Exception {
		// TODO
	
		//Operatore opToModify = null;
		Indirizzo newIndirizzo = new Indirizzo();
		int idAsl = -1;

		try {
			
			
			if (this.inRegione == false)
			{
				//LO DEVO MANDARE FUORI REGIONE
				this.setModificaCompletata(true);  //Non c'è bisogno di accettazione
				
			}

			if (this.getIdNuovoIndirizzo() > 0) {
				// INDIRIZZO ESISTENTE, LO RECUPERO DAL DB
				newIndirizzo = new Indirizzo(db, this.getIdNuovoIndirizzo());
			}

			else 
			{

				// INDIRIZZO NON ESISTENTE, LO DEVO CREARE

				newIndirizzo.setComune(this.getIdComuneModificaResidenza());
				newIndirizzo.setCap(this.getCap());
				newIndirizzo
						.setProvincia(String.valueOf(this.getIdProvinciaModificaResidenza()));
				if(this.getCap()== null || this.getCap().equals("") || this.getCap().equalsIgnoreCase("null"))
					newIndirizzo.setCap(ComuniAnagrafica.getCap(db, this.getIdComuneModificaResidenza()));
				else
					newIndirizzo.setCap(this.getCap());
				newIndirizzo.setVia(this.getVia());
				newIndirizzo.setIdProvincia(this.getIdProvinciaModificaResidenza());

				newIndirizzo.insert(db);

				this.idNuovoIndirizzo = newIndirizzo.getIdIndirizzo();

			}
			
			if(this.getIdProvinciaModificaResidenza()>0)
			{
				Regione r = new Regione();
				r.getByProvincia(db, this.getIdProvinciaModificaResidenza());
				this.setIdRegione(r.getIdRegione());
			}
			
			
			if (newIndirizzo.getIdIndirizzo() > 0){  //HO UN INDIRIZZO IN REGIONE
			// Recupero info asl
			Object[] asl;
			asl = DwrUtil.getValoriAsl(newIndirizzo.getComune());
			
			if (asl != null && asl.length > 0) {

				Object[] aslVal = (Object[]) asl[0];
				if (aslVal != null && aslVal.length > 0)
					newIndirizzo.setIdAsl((Integer) aslVal[0]);
				idAsl = (Integer) aslVal[0];
			}
			}else {
				newIndirizzo.setIdAsl(new Integer(ApplicationProperties.getProperty("ID_ASL_FUORI_REGIONE")));
				idAsl = new Integer(ApplicationProperties.getProperty("ID_ASL_FUORI_REGIONE"));
			}

			//Se è privato, sindaco, sindaco FR va aggiornata anche la sede legale che coincide con la residenza e indirizzo del sog fisico
			
		
			Stabilimento stab = (Stabilimento) opToModify
					.getListaStabilimenti().get(0);
			this.idVecchioIndirizzo = stab.getSedeOperativa().getIdIndirizzo();
			int idAslOld = stab.getIdAsl();
			
			//GLI AGGIORNAMENTI OPERATORE LI FACCIO ALL'ACCETTAZIONE per gli extra asl 
			// -- MODIFICA 12/11/13: L'indirizzo va cambiato subito anche per i fuori asl
			
		//	if (inRegione == false){
				stab.setSedeOperativa(newIndirizzo);
				stab.setIdAsl(newIndirizzo.getIdAsl());
				if (!inRegione)
					stab.setFlagFuoriRegione(true);
				else
					stab.setFlagFuoriRegione(false);
				stab.updateSedeOperativa(db);
				
				LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
				if (lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazionePrivato ||
						lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindaco ||
						lp.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindacoFR){
					newIndirizzo.setTipologiaSede(1);
					opToModify.getListaSediOperatore().add(newIndirizzo);
					opToModify.setModifiedBy(idUtenteModifica);
					opToModify.aggiornaRelazioneSede(db, opToModify.getSedeLegale(), newIndirizzo);
					SoggettoFisico rappLegale = opToModify.getRappLegale();
					rappLegale.setModifiedBy(idUtenteModifica);
					rappLegale.setIndirizzo(newIndirizzo);
					rappLegale.updateSoloIndirizzo(db);
					
				//	opToModify.aggiornaVistaMaterializzata(db);
				}
				
				opToModify.aggiornaVistaMaterializzata(db);
			
		//	}else{
				//SE NON STO CANCELLANDO LA REGISTRAZIONE
			if (!cancellaRegistrazione){
				if (inRegione && (stab.getSedeOperativa().getIdAsl() != idAslOld)) //Mi serve controllo su asl xkè da inserimento registrazione su animale può essere anche una modifica intra asl
					stab.setModificaResidenzaFuoriAslInCorso(db, true); 
				else
					this.setModificaCompletata(true);
				}
			else //SE STO CANCELLANDO LA REGISTRAZIONE
				stab.setModificaResidenzaFuoriAslInCorso(db, false);
				//MODIFICA RESIDENZA DA ACCETTARE DA ASL DESTINAZIONE
		//	}
			
			AnimaleList listaAnimali = new AnimaleList();
			listaAnimali.setId_proprietario(this.getIdRelazioneStabilimentoLineaProduttiva());
			listaAnimali.setBuildProprietario(true);
			listaAnimali.setFlagDecesso(false);
			listaAnimali.setFlagFurto(false);
			listaAnimali.setFlagSmarrimento(false);
			listaAnimali.buildList(db);
			
            //SE STO CANCELLANDO LA REGISTRAZIONE, NON PROSEGUIRE
            if (!cancellaRegistrazione){
            	this.setIdAslDestinataria(idAsl);
            	// Inserimento registrazione
            	this.insert(db);
			}
            
            
            Animale.updateAslAnimaliProprietario(listaAnimali, newIndirizzo.getIdAsl(), db, idUtenteModifica, this.getIdAslInserimentoRegistrazione(), inRegione, this.getIdRegione(), this.getDataModificaResidenza(), this.getDenominazioneOperatore());

			
		} catch (Exception e) {
			

			e.printStackTrace();
			throw e;
		}

	
		
		
		return opToModify;

	}
	
	
	public void chiudiModificaResidenza(Connection db){
		
		String chiudiRegistrazioneFuoriAsl = "update evento_modifica_residenza_operatore set modifica_completata = true where id_modifica_residenza = ?";
		PreparedStatement pst;
		try {
			pst = db.prepareStatement(chiudiRegistrazioneFuoriAsl);
			pst.setInt(1, this.getId());
			pst.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public RegistrazioneModificaIndirizzoOperatore getUltimaModificaResidenzaOperatore(Connection db){
		//RegistrazioneModificaIndirizzoOperatore reg = new RegistrazioneModificaIndirizzoOperatore();
		
		try{
			String query = "select r.id as id_registrazione_operatore,  r.entered as data_inserimento_registrazione_reg, r.modified as data_modifica_registrazione_reg, * from registrazione_operatore r left join evento_modifica_residenza_operatore e on (r.id = e.id_registrazione) where r.id_rel_stab_lp = " + this.getIdRelazioneStabilimentoLineaProduttiva() + 
			" and e.modifica_completata = false and r.data_cancellazione is null ";
			PreparedStatement pst =  db.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()){
				buildRecord(rs, db);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return this;
		
	}






	public void setCancellaRegistrazione(boolean cancellaRegistrazione) {
		this.cancellaRegistrazione = cancellaRegistrazione;
	}


	public boolean isCancellaRegistrazione() {
		return cancellaRegistrazione;
	}



	

}
