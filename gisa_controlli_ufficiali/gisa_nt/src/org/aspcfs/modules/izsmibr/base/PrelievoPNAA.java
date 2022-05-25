/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.izsmibr.base;

import it.izs.ws.WsPost;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Hex;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.aspcfs.modules.base.Address;
import org.aspcfs.modules.opu.base.RicercheAnagraficheTab;
import org.aspcfs.modules.opu.util.StabilimentoImportUtil;
import org.aspcfs.modules.stabilimenti.base.Organization;
import org.aspcfs.apps.transfer.DataImportHandler;
import org.aspcfs.modules.sintesis.util.StabilimentoSintesisUtil;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.DateUtils;

import com.sun.javafx.scene.EnteredExitedHandler;

public class PrelievoPNAA {
	Logger logger = Logger.getLogger("MainLogger");


	private String 	assistCodFiscale	;
	private String 	emailResponsabileCampionamento	;
	private String 	destCampPrelevato	;
	private String 	confezionamentoPNAA2021v1	;
	private String 	ragSocDittaProduttrice	;
	private String 	indirizzoDittaProduttrice	;
	private String 	spCatDestAlim2021v1	;
	private String 	metProd2021v1	;
	private String 	nomeCommercialeMangime	;
	private String 	stProd2021v1	;
	private String 	stProd2021Altro	;
	private String 	ragSocRespEtichettatura	;
	private String 	indirizzoRespEtichettatura	;
	private String 	stProduzione	;
	private String 	dtProduzione	;
	private String 	dtScadenza	;
	private String 	numLottoProd	;
	private String 	dimPartita	;
	private String 	ingredientiReg63	;
	private String 	vopeCartellino	;
	private String 	vopeModEsecCamp	;
	private String 	vopeNumPuntiSacchi	;
	private String 	vopeNumCampioniEffettivi	;
	private String 	vopePesoVolumePuntiSacchi	;
	private String 	vopeOperazioniCampioneGlobale	;
	private String 	vopePesoVolumeCampioneGlobale	;
	private String 	vopeCampioneGlobaleRidotto	;
	private String 	vopePesoVolumeCampioneRidotto	;
	private String 	vopeOperazioniCampioniFinali	;
	private String 	vopeNumCampioniFinali	;
	private String 	vopePesoVolumeMinimoCampioniFinali	;
	private String 	vopeDichiarazioniPropDet	;
	private String 	vopeNumCopieVerbaleInviate	;
	private String 	vopeDataInvioVerbali	;
	private String 	vopeNumCopieVerbaleCustodite	;
	private String 	vopeNumCampioniFinaliCustoditi	;
	private String 	vopeNomeCognomeCustode	;
	private String 	vopeTipoCustodiaCampioneFinale	;
	private String 	vopeRinunciaCampioniFinali	;
	private String 	vopeSequestoPartitaLotto	;
	private String 	foodexCodice	;
	private String 	numAliquote	;
	private String 	numUnitaCampionarie	;
	private String 	progressivoCampione	;
	private String 	contaminanteCodice	;
	private String 	cun	;
	private String 	dataPrelievo	;
	private String 	dataVerbale	;
	private String 	laboratorioCodice	;
	private String 	luogoPrelievoCodice	;
	private String 	metodoCampionamentoCodice	;
	private String 	motivoCodice	;
	private String 	numeroScheda	;
	private String 	numeroVerbale	;
	private String 	pianoCodice	;
	private String 	prelCodFiscale	;
	private String 	tipoImpresa	;
	
	private int idImport = -1;
	private int id = -1;
	private int enteredBy;
	private Timestamp entered;
	private String esito = null;
	
	public void completaDaFoglio (Row nextRow){
		Iterator<Cell> cellIterator = nextRow.cellIterator();

		int i = 0;
		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			switch(i){
			case 0: setAssistCodFiscale(getValore(cell)); break;
			case 1: setEmailResponsabileCampionamento(getValore(cell)); break;
			case 2: setDestCampPrelevato(getValore(cell)); break;
			case 3: setConfezionamentoPNAA2021v1(getValore(cell)); break;
			case 4: setRagSocDittaProduttrice(getValore(cell)); break;
			case 5: setIndirizzoDittaProduttrice(getValore(cell)); break;
			case 6: setSpCatDestAlim2021v1(getValore(cell)); break;
			case 7: setMetProd2021v1(getValore(cell)); break;
			case 8: setNomeCommercialeMangime(getValore(cell)); break;
			case 9: setStProd2021v1(getValore(cell)); break;
			case 10: setStProd2021Altro(getValore(cell)); break;
			case 11: setRagSocRespEtichettatura(getValore(cell)); break;
			case 12: setIndirizzoRespEtichettatura(getValore(cell)); break;
			case 13: setStProduzione(getValore(cell)); break;
			case 14: setDtProduzione(getValore(cell)); break;
			case 15: setDtScadenza(getValore(cell)); break;
			case 16: setNumLottoProd(getValore(cell)); break;
			case 17: setDimPartita(getValore(cell)); break;
			case 18: setIngredientiReg63(getValore(cell)); break;
			case 19: setVopeCartellino(getValore(cell)); break;
			case 20: setVopeModEsecCamp(getValore(cell)); break;
			case 21: setVopeNumPuntiSacchi(getValore(cell)); break;
			case 22: setVopeNumCampioniEffettivi(getValore(cell)); break;
			case 23: setVopePesoVolumePuntiSacchi(getValore(cell)); break;
			case 24: setVopeOperazioniCampioneGlobale(getValore(cell)); break;
			case 25: setVopePesoVolumeCampioneGlobale(getValore(cell)); break;
			case 26: setVopeCampioneGlobaleRidotto(getValore(cell)); break;
			case 27: setVopePesoVolumeCampioneRidotto(getValore(cell)); break;
			case 28: setVopeOperazioniCampioniFinali(getValore(cell)); break;
			case 29: setVopeNumCampioniFinali(getValore(cell)); break;
			case 30: setVopePesoVolumeMinimoCampioniFinali(getValore(cell)); break;
			case 31: setVopeDichiarazioniPropDet(getValore(cell)); break;
			case 32: setVopeNumCopieVerbaleInviate(getValore(cell)); break;
			case 33: setVopeDataInvioVerbali(getValore(cell)); break;
			case 34: setVopeNumCopieVerbaleCustodite(getValore(cell)); break;
			case 35: setVopeNumCampioniFinaliCustoditi(getValore(cell)); break;
			case 36: setVopeNomeCognomeCustode(getValore(cell)); break;
			case 37: setVopeTipoCustodiaCampioneFinale(getValore(cell)); break;
			case 38: setVopeRinunciaCampioniFinali(getValore(cell)); break;
			case 39: setVopeSequestoPartitaLotto(getValore(cell)); break;
			case 40: setFoodexCodice(getValore(cell)); break;
			case 41: setNumAliquote(getValore(cell)); break;
			case 42: setNumUnitaCampionarie(getValore(cell)); break;
			case 43: setProgressivoCampione(getValore(cell)); break;
			case 44: setContaminanteCodice(getValore(cell)); break;
			case 45: setCun(getValore(cell)); break;
			case 46: setDataPrelievo(getValore(cell)); break;
			case 47: setDataVerbale(getValore(cell)); break;
			case 48: setLaboratorioCodice(getValore(cell)); break;
			case 49: setLuogoPrelievoCodice(getValore(cell)); break;
			case 50: setMetodoCampionamentoCodice(getValore(cell)); break;
			case 51: setMotivoCodice(getValore(cell)); break;
			case 52: setNumeroScheda(getValore(cell)); break;
			case 53: setNumeroVerbale(getValore(cell)); break;
			case 54: setPianoCodice(getValore(cell)); break;
			case 55: setPrelCodFiscale(getValore(cell)); break;
			case 56: setTipoImpresa(getValore(cell)); break;
			}
			i++; 
		}
	}
	
	public void insert(Connection db) throws SQLException{

		PreparedStatement pst = db.prepareStatement("INSERT INTO import_pnaa("
				+ "assist_cod_fiscale, email_responsabile_campionamento, dest_camp_prelevato, confezionamento_pnaa_2021_v1, rag_soc_ditta_produttrice, "
				+ "indirizzo_ditta_produttrice, sp_cat_dest_alim_2021_v1, met_prod2021v1, nome_commerciale_mangime, st_prod_2021_v1, "
				+ "st_prod_2021_altro, rag_soc_resp_etichettatura, indirizzo_resp_etichettatura, st_produzione, dt_produzione, "
				+ "dt_scadenza, num_lotto_prod, dim_partita, ingredienti_reg_63, vope_cartellino, "
				+ "vope_mod_esec_camp, vope_num_punti_sacchi, vope_num_campioni_effettivi, vope_peso_volume_punti_sacchi, vope_operazioni_campione_globale, "
				+ "vope_peso_volume_campione_globale, vope_campione_globale_ridotto, vope_peso_volume_campione_ridotto, vope_operazioni_campioni_finali, vope_num_campioni_finali, "
				+ "vope_peso_volume_minimo_campioni_finali, vope_dichiarazioni_prop_det, vope_num_copie_verbale_inviate, vope_data_invio_verbali, vope_num_copie_verbale_custodite, "
				+ "vope_num_campioni_finali_custoditi, vope_nome_cognome_custode, vope_tipo_custodia_campione_finale, vope_rinuncia_campioni_finali, vope_sequesto_partita_lotto, "
				+ "foodex_codice, num_aliquote, num_unita_campionarie, progressivo_campione, contaminante_codice, "
				+ "cun, data_prelievo, data_verbale, laboratorio_codice, luogo_prelievo_codice, "
				+ "metodo_campionamento_codice, motivo_codice, numero_scheda, numero_verbale, piano_codice, "
				+ "prel_cod_fiscale, tipo_impresa, enteredby, id_import) "
				+ "VALUES (?, ?, ?, ?, ?,  "
				+ "?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?) returning id as id_inserito");

		int i = 0;

		pst.setString(++i,  assistCodFiscale);
		pst.setString(++i,  emailResponsabileCampionamento);
		pst.setString(++i,  destCampPrelevato);
		pst.setString(++i,  confezionamentoPNAA2021v1);
		pst.setString(++i,  ragSocDittaProduttrice);
		
		pst.setString(++i,  indirizzoDittaProduttrice);
		pst.setString(++i,  spCatDestAlim2021v1);
		pst.setString(++i,  metProd2021v1);
		pst.setString(++i,  nomeCommercialeMangime);
		pst.setString(++i,  stProd2021v1);
		
		pst.setString(++i,  stProd2021Altro);
		pst.setString(++i,  ragSocRespEtichettatura);
		pst.setString(++i,  indirizzoRespEtichettatura);
		pst.setString(++i,  stProduzione);
		pst.setString(++i,  dtProduzione);
		
		pst.setString(++i,  dtScadenza);
		pst.setString(++i,  numLottoProd);
		pst.setString(++i,  dimPartita);
		pst.setString(++i,  ingredientiReg63);
		pst.setString(++i,  vopeCartellino);
		
		pst.setString(++i,  vopeModEsecCamp);
		pst.setString(++i,  vopeNumPuntiSacchi);
		pst.setString(++i,  vopeNumCampioniEffettivi);
		pst.setString(++i,  vopePesoVolumePuntiSacchi);
		pst.setString(++i,  vopeOperazioniCampioneGlobale);
		
		pst.setString(++i,  vopePesoVolumeCampioneGlobale);
		pst.setString(++i,  vopeCampioneGlobaleRidotto);
		pst.setString(++i,  vopePesoVolumeCampioneRidotto);
		pst.setString(++i,  vopeOperazioniCampioniFinali);
		pst.setString(++i,  vopeNumCampioniFinali);
		
		pst.setString(++i,  vopePesoVolumeMinimoCampioniFinali);
		pst.setString(++i,  vopeDichiarazioniPropDet);
		pst.setString(++i,  vopeNumCopieVerbaleInviate);
		pst.setString(++i,  vopeDataInvioVerbali);
		pst.setString(++i,  vopeNumCopieVerbaleCustodite);
		
		pst.setString(++i,  vopeNumCampioniFinaliCustoditi);
		pst.setString(++i,  vopeNomeCognomeCustode);
		pst.setString(++i,  vopeTipoCustodiaCampioneFinale);
		pst.setString(++i,  vopeRinunciaCampioniFinali);
		pst.setString(++i,  vopeSequestoPartitaLotto);
		
		pst.setString(++i,  foodexCodice);
		pst.setString(++i,  numAliquote);
		pst.setString(++i,  numUnitaCampionarie);
		pst.setString(++i,  progressivoCampione);
		pst.setString(++i,  contaminanteCodice);
		
		pst.setString(++i,  cun);
		pst.setString(++i,  dataPrelievo);
		pst.setString(++i,  dataVerbale);
		pst.setString(++i,  laboratorioCodice);
		pst.setString(++i,  luogoPrelievoCodice);
		
		pst.setString(++i,  metodoCampionamentoCodice);
		pst.setString(++i,  motivoCodice);
		pst.setString(++i,  numeroScheda);
		pst.setString(++i,  numeroVerbale);
		pst.setString(++i,  pianoCodice);
		
		pst.setString(++i,  prelCodFiscale);
		pst.setString(++i,  tipoImpresa);
		pst.setInt(++i, enteredBy);
		pst.setInt(++i, idImport);
		
		ResultSet rs = pst.executeQuery();

		if (rs.next())
			this.id = rs.getInt("id_inserito");
	}


	public PrelievoPNAA(){
	}

	public PrelievoPNAA(ResultSet rs) throws SQLException{
		buildRecord(rs);
	}


	public PrelievoPNAA(Connection db, int id) throws SQLException{

		PreparedStatement pst = db.prepareStatement("select * from import_pnaa where id = ?");
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			buildRecord(rs);
		}
	}

	private void buildRecord(ResultSet rs) throws SQLException{
		
		assistCodFiscale = rs.getString("assist_cod_fiscale");
		emailResponsabileCampionamento = rs.getString("email_responsabile_campionamento");
		destCampPrelevato = rs.getString("dest_camp_prelevato");
		confezionamentoPNAA2021v1 = rs.getString("confezionamento_pnaa_2021_v1");
		ragSocDittaProduttrice = rs.getString("rag_soc_ditta_produttrice");
		indirizzoDittaProduttrice = rs.getString("indirizzo_ditta_produttrice");
		spCatDestAlim2021v1 = rs.getString("sp_cat_dest_alim_2021_v1");
		metProd2021v1 = rs.getString("met_prod2021v1");
		nomeCommercialeMangime = rs.getString("nome_commerciale_mangime");
		stProd2021v1 = rs.getString("st_prod_2021_v1");
		stProd2021Altro = rs.getString("st_prod_2021_altro");
		ragSocRespEtichettatura = rs.getString("rag_soc_resp_etichettatura");
		indirizzoRespEtichettatura = rs.getString("indirizzo_resp_etichettatura");
		stProduzione = rs.getString("st_produzione");
		dtProduzione = rs.getString("dt_produzione");
		dtScadenza = rs.getString("dt_scadenza");
		numLottoProd = rs.getString("num_lotto_prod");
		dimPartita = rs.getString("dim_partita");
		ingredientiReg63 = rs.getString("ingredienti_reg_63");
		vopeCartellino = rs.getString("vope_cartellino");
		vopeModEsecCamp = rs.getString("vope_mod_esec_camp");
		vopeNumPuntiSacchi = rs.getString("vope_num_punti_sacchi");
		vopeNumCampioniEffettivi = rs.getString("vope_num_campioni_effettivi");
		vopePesoVolumePuntiSacchi = rs.getString("vope_peso_volume_punti_sacchi");
		vopeOperazioniCampioneGlobale = rs.getString("vope_operazioni_campione_globale");
		vopePesoVolumeCampioneGlobale = rs.getString("vope_peso_volume_campione_globale");
		vopeCampioneGlobaleRidotto = rs.getString("vope_campione_globale_ridotto");
		vopePesoVolumeCampioneRidotto = rs.getString("vope_peso_volume_campione_ridotto");
		vopeOperazioniCampioniFinali = rs.getString("vope_operazioni_campioni_finali");
		vopeNumCampioniFinali = rs.getString("vope_num_campioni_finali");
		vopePesoVolumeMinimoCampioniFinali = rs.getString("vope_peso_volume_minimo_campioni_finali");
		vopeDichiarazioniPropDet = rs.getString("vope_dichiarazioni_prop_det");
		vopeNumCopieVerbaleInviate = rs.getString("vope_num_copie_verbale_inviate");
		vopeDataInvioVerbali = rs.getString("vope_data_invio_verbali");
		vopeNumCopieVerbaleCustodite = rs.getString("vope_num_copie_verbale_custodite");
		vopeNumCampioniFinaliCustoditi = rs.getString("vope_num_campioni_finali_custoditi");
		vopeNomeCognomeCustode = rs.getString("vope_nome_cognome_custode");
		vopeTipoCustodiaCampioneFinale = rs.getString("vope_tipo_custodia_campione_finale");
		vopeRinunciaCampioniFinali = rs.getString("vope_rinuncia_campioni_finali");
		vopeSequestoPartitaLotto = rs.getString("vope_sequesto_partita_lotto");
		foodexCodice = rs.getString("foodex_codice");
		numAliquote = rs.getString("num_aliquote");
		numUnitaCampionarie = rs.getString("num_unita_campionarie");
		progressivoCampione = rs.getString("progressivo_campione");
		contaminanteCodice = rs.getString("contaminante_codice");
		cun = rs.getString("cun");
		dataPrelievo = rs.getString("data_prelievo");
		dataVerbale = rs.getString("data_verbale");
		laboratorioCodice = rs.getString("laboratorio_codice");
		luogoPrelievoCodice = rs.getString("luogo_prelievo_codice");
		metodoCampionamentoCodice = rs.getString("metodo_campionamento_codice");
		motivoCodice = rs.getString("motivo_codice");
		numeroScheda = rs.getString("numero_scheda");
		numeroVerbale = rs.getString("numero_verbale");
		pianoCodice = rs.getString("piano_codice");
		prelCodFiscale = rs.getString("prel_cod_fiscale");
		tipoImpresa = rs.getString("tipo_impresa");


		idImport = rs.getInt("id_import");
		id = rs.getInt("id");
		enteredBy = rs.getInt("enteredby");
		entered = rs.getTimestamp("entered");	
		
		esito = rs.getString("esito");

	}

	public static int getMaxIdImport(Connection db) throws SQLException {
		int idMax = -1;
		PreparedStatement pst = db.prepareStatement("select COALESCE(max(id_import), 0) as max from import_pnaa");
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			idMax = rs.getInt("max");
		return idMax;
	}

	public void invio(Connection db, int userId) throws SQLException {	

		WsPost ws = new WsPost();
		String envelope = null;
		String response = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		logger.info(" ------ INVIO DATI PNAA A BDN id  "+id+" -----");
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_PNAA_BDN"));

		pst = db.prepareStatement("select * from get_chiamata_ws_insert_prelievo_pnaa(?, ?, ?)");
		pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_BDN"));
		pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_BDN"));
		pst.setInt(3, id);
		logger.info(" ------ INVIO DATI PNAA A BDN dbi: "+pst.toString()+" -----");
		rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);

		ws.setWsRequest(envelope);
		response= ws.post(db, userId);
		aggiornaEsito(db, response);

	}
	
	public void aggiornaEsito(Connection db, String response) throws SQLException {	

		PreparedStatement pst = null;
		pst = db.prepareStatement("update import_pnaa set esito = ? where id = ?");
		pst.setString(1, response);
		pst.setInt(2, id);
		pst.executeUpdate();
	}

	public static ArrayList<PrelievoPNAA> buildListaInviiMassivi(Connection db) throws SQLException {
		ArrayList<PrelievoPNAA> listaInvii = new ArrayList<PrelievoPNAA>();
		PreparedStatement pst = db.prepareStatement("select distinct on(id_import) id_import, * from import_pnaa order by id_import desc");
		ResultSet rs = pst.executeQuery();
		while(rs.next()){
			PrelievoPNAA invioPNAA = new PrelievoPNAA(rs);
			listaInvii.add(invioPNAA);
		}
		return listaInvii;
	}
	
	public static ArrayList<PrelievoPNAA> buildListaInvii(Connection db, int idImport) throws SQLException {
		ArrayList<PrelievoPNAA> listaInvii = new ArrayList<PrelievoPNAA>();
		PreparedStatement pst = db.prepareStatement("select * from import_pnaa where id_import = ? order by id asc");
		pst.setInt(1, idImport);
		ResultSet rs = pst.executeQuery();
		while(rs.next()){
			PrelievoPNAA invioPNAA = new PrelievoPNAA(rs);
			listaInvii.add(invioPNAA);
		}
		return listaInvii;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public String getAssistCodFiscale() {
		return assistCodFiscale;
	}

	public void setAssistCodFiscale(String assistCodFiscale) {
		this.assistCodFiscale = assistCodFiscale;
	}

	public String getEmailResponsabileCampionamento() {
		return emailResponsabileCampionamento;
	}

	public void setEmailResponsabileCampionamento(String emailResponsabileCampionamento) {
		this.emailResponsabileCampionamento = emailResponsabileCampionamento;
	}

	public String getDestCampPrelevato() {
		return destCampPrelevato;
	}

	public void setDestCampPrelevato(String destCampPrelevato) {
		this.destCampPrelevato = destCampPrelevato;
	}

	public String getConfezionamentoPNAA2021v1() {
		return confezionamentoPNAA2021v1;
	}

	public void setConfezionamentoPNAA2021v1(String confezionamentoPNAA2021v1) {
		this.confezionamentoPNAA2021v1 = confezionamentoPNAA2021v1;
	}

	public String getRagSocDittaProduttrice() {
		return ragSocDittaProduttrice;
	}

	public void setRagSocDittaProduttrice(String ragSocDittaProduttrice) {
		this.ragSocDittaProduttrice = ragSocDittaProduttrice;
	}

	public String getIndirizzoDittaProduttrice() {
		return indirizzoDittaProduttrice;
	}

	public void setIndirizzoDittaProduttrice(String indirizzoDittaProduttrice) {
		this.indirizzoDittaProduttrice = indirizzoDittaProduttrice;
	}

	public String getSpCatDestAlim2021v1() {
		return spCatDestAlim2021v1;
	}

	public void setSpCatDestAlim2021v1(String spCatDestAlim2021v1) {
		this.spCatDestAlim2021v1 = spCatDestAlim2021v1;
	}

	public String getMetProd2021v1() {
		return metProd2021v1;
	}

	public void setMetProd2021v1(String metProd2021v1) {
		this.metProd2021v1 = metProd2021v1;
	}

	public String getNomeCommercialeMangime() {
		return nomeCommercialeMangime;
	}

	public void setNomeCommercialeMangime(String nomeCommercialeMangime) {
		this.nomeCommercialeMangime = nomeCommercialeMangime;
	}

	public String getStProd2021v1() {
		return stProd2021v1;
	}

	public void setStProd2021v1(String stProd2021v1) {
		this.stProd2021v1 = stProd2021v1;
	}

	public String getStProd2021Altro() {
		return stProd2021Altro;
	}

	public void setStProd2021Altro(String stProd2021Altro) {
		this.stProd2021Altro = stProd2021Altro;
	}

	public String getRagSocRespEtichettatura() {
		return ragSocRespEtichettatura;
	}

	public void setRagSocRespEtichettatura(String ragSocRespEtichettatura) {
		this.ragSocRespEtichettatura = ragSocRespEtichettatura;
	}

	public String getIndirizzoRespEtichettatura() {
		return indirizzoRespEtichettatura;
	}

	public void setIndirizzoRespEtichettatura(String indirizzoRespEtichettatura) {
		this.indirizzoRespEtichettatura = indirizzoRespEtichettatura;
	}

	public String getStProduzione() {
		return stProduzione;
	}

	public void setStProduzione(String stProduzione) {
		this.stProduzione = stProduzione;
	}

	public String getDtProduzione() {
		return dtProduzione;
	}

	public void setDtProduzione(String dtProduzione) {
		this.dtProduzione = dtProduzione;
	}

	public String getDtScadenza() {
		return dtScadenza;
	}

	public void setDtScadenza(String dtScadenza) {
		this.dtScadenza = dtScadenza;
	}

	public String getNumLottoProd() {
		return numLottoProd;
	}

	public void setNumLottoProd(String numLottoProd) {
		this.numLottoProd = numLottoProd;
	}

	public String getDimPartita() {
		return dimPartita;
	}

	public void setDimPartita(String dimPartita) {
		this.dimPartita = dimPartita;
	}

	public String getIngredientiReg63() {
		return ingredientiReg63;
	}

	public void setIngredientiReg63(String ingredientiReg63) {
		this.ingredientiReg63 = ingredientiReg63;
	}

	public String getVopeCartellino() {
		return vopeCartellino;
	}

	public void setVopeCartellino(String vopeCartellino) {
		this.vopeCartellino = vopeCartellino;
	}

	public String getVopeModEsecCamp() {
		return vopeModEsecCamp;
	}

	public void setVopeModEsecCamp(String vopeModEsecCamp) {
		this.vopeModEsecCamp = vopeModEsecCamp;
	}

	public String getVopeNumPuntiSacchi() {
		return vopeNumPuntiSacchi;
	}

	public void setVopeNumPuntiSacchi(String vopeNumPuntiSacchi) {
		this.vopeNumPuntiSacchi = vopeNumPuntiSacchi;
	}

	public String getVopeNumCampioniEffettivi() {
		return vopeNumCampioniEffettivi;
	}

	public void setVopeNumCampioniEffettivi(String vopeNumCampioniEffettivi) {
		this.vopeNumCampioniEffettivi = vopeNumCampioniEffettivi;
	}

	public String getVopePesoVolumePuntiSacchi() {
		return vopePesoVolumePuntiSacchi;
	}

	public void setVopePesoVolumePuntiSacchi(String vopePesoVolumePuntiSacchi) {
		this.vopePesoVolumePuntiSacchi = vopePesoVolumePuntiSacchi;
	}

	public String getVopeOperazioniCampioneGlobale() {
		return vopeOperazioniCampioneGlobale;
	}

	public void setVopeOperazioniCampioneGlobale(String vopeOperazioniCampioneGlobale) {
		this.vopeOperazioniCampioneGlobale = vopeOperazioniCampioneGlobale;
	}

	public String getVopePesoVolumeCampioneGlobale() {
		return vopePesoVolumeCampioneGlobale;
	}

	public void setVopePesoVolumeCampioneGlobale(String vopePesoVolumeCampioneGlobale) {
		this.vopePesoVolumeCampioneGlobale = vopePesoVolumeCampioneGlobale;
	}

	public String getVopeCampioneGlobaleRidotto() {
		return vopeCampioneGlobaleRidotto;
	}

	public void setVopeCampioneGlobaleRidotto(String vopeCampioneGlobaleRidotto) {
		this.vopeCampioneGlobaleRidotto = vopeCampioneGlobaleRidotto;
	}

	public String getVopePesoVolumeCampioneRidotto() {
		return vopePesoVolumeCampioneRidotto;
	}

	public void setVopePesoVolumeCampioneRidotto(String vopePesoVolumeCampioneRidotto) {
		this.vopePesoVolumeCampioneRidotto = vopePesoVolumeCampioneRidotto;
	}

	public String getVopeOperazioniCampioniFinali() {
		return vopeOperazioniCampioniFinali;
	}

	public void setVopeOperazioniCampioniFinali(String vopeOperazioniCampioniFinali) {
		this.vopeOperazioniCampioniFinali = vopeOperazioniCampioniFinali;
	}

	public String getVopeNumCampioniFinali() {
		return vopeNumCampioniFinali;
	}

	public void setVopeNumCampioniFinali(String vopeNumCampioniFinali) {
		this.vopeNumCampioniFinali = vopeNumCampioniFinali;
	}

	public String getVopePesoVolumeMinimoCampioniFinali() {
		return vopePesoVolumeMinimoCampioniFinali;
	}

	public void setVopePesoVolumeMinimoCampioniFinali(String vopePesoVolumeMinimoCampioniFinali) {
		this.vopePesoVolumeMinimoCampioniFinali = vopePesoVolumeMinimoCampioniFinali;
	}

	public String getVopeDichiarazioniPropDet() {
		return vopeDichiarazioniPropDet;
	}

	public void setVopeDichiarazioniPropDet(String vopeDichiarazioniPropDet) {
		this.vopeDichiarazioniPropDet = vopeDichiarazioniPropDet;
	}

	public String getVopeNumCopieVerbaleInviate() {
		return vopeNumCopieVerbaleInviate;
	}

	public void setVopeNumCopieVerbaleInviate(String vopeNumCopieVerbaleInviate) {
		this.vopeNumCopieVerbaleInviate = vopeNumCopieVerbaleInviate;
	}

	public String getVopeDataInvioVerbali() {
		return vopeDataInvioVerbali;
	}

	public void setVopeDataInvioVerbali(String vopeDataInvioVerbali) {
		this.vopeDataInvioVerbali = vopeDataInvioVerbali;
	}

	public String getVopeNumCopieVerbaleCustodite() {
		return vopeNumCopieVerbaleCustodite;
	}

	public void setVopeNumCopieVerbaleCustodite(String vopeNumCopieVerbaleCustodite) {
		this.vopeNumCopieVerbaleCustodite = vopeNumCopieVerbaleCustodite;
	}

	public String getVopeNumCampioniFinaliCustoditi() {
		return vopeNumCampioniFinaliCustoditi;
	}

	public void setVopeNumCampioniFinaliCustoditi(String vopeNumCampioniFinaliCustoditi) {
		this.vopeNumCampioniFinaliCustoditi = vopeNumCampioniFinaliCustoditi;
	}

	public String getVopeNomeCognomeCustode() {
		return vopeNomeCognomeCustode;
	}

	public void setVopeNomeCognomeCustode(String vopeNomeCognomeCustode) {
		this.vopeNomeCognomeCustode = vopeNomeCognomeCustode;
	}

	public String getVopeTipoCustodiaCampioneFinale() {
		return vopeTipoCustodiaCampioneFinale;
	}

	public void setVopeTipoCustodiaCampioneFinale(String vopeTipoCustodiaCampioneFinale) {
		this.vopeTipoCustodiaCampioneFinale = vopeTipoCustodiaCampioneFinale;
	}

	public String getVopeRinunciaCampioniFinali() {
		return vopeRinunciaCampioniFinali;
	}

	public void setVopeRinunciaCampioniFinali(String vopeRinunciaCampioniFinali) {
		this.vopeRinunciaCampioniFinali = vopeRinunciaCampioniFinali;
	}

	public String getVopeSequestoPartitaLotto() {
		return vopeSequestoPartitaLotto;
	}

	public void setVopeSequestoPartitaLotto(String vopeSequestoPartitaLotto) {
		this.vopeSequestoPartitaLotto = vopeSequestoPartitaLotto;
	}

	public String getFoodexCodice() {
		return foodexCodice;
	}

	public void setFoodexCodice(String foodexCodice) {
		this.foodexCodice = foodexCodice;
	}

	public String getNumAliquote() {
		return numAliquote;
	}

	public void setNumAliquote(String numAliquote) {
		this.numAliquote = numAliquote;
	}

	public String getNumUnitaCampionarie() {
		return numUnitaCampionarie;
	}

	public void setNumUnitaCampionarie(String numUnitaCampionarie) {
		this.numUnitaCampionarie = numUnitaCampionarie;
	}

	public String getProgressivoCampione() {
		return progressivoCampione;
	}

	public void setProgressivoCampione(String progressivoCampione) {
		this.progressivoCampione = progressivoCampione;
	}

	public String getContaminanteCodice() {
		return contaminanteCodice;
	}

	public void setContaminanteCodice(String contaminanteCodice) {
		this.contaminanteCodice = contaminanteCodice;
	}

	public String getCun() {
		return cun;
	}

	public void setCun(String cun) {
		this.cun = cun;
	}

	public String getDataPrelievo() {
		return dataPrelievo;
	}

	public void setDataPrelievo(String dataPrelievo) {
		this.dataPrelievo = dataPrelievo;
	}

	public String getDataVerbale() {
		return dataVerbale;
	}

	public void setDataVerbale(String dataVerbale) {
		this.dataVerbale = dataVerbale;
	}

	public String getLaboratorioCodice() {
		return laboratorioCodice;
	}

	public void setLaboratorioCodice(String laboratorioCodice) {
		this.laboratorioCodice = laboratorioCodice;
	}

	public String getLuogoPrelievoCodice() {
		return luogoPrelievoCodice;
	}

	public void setLuogoPrelievoCodice(String luogoPrelievoCodice) {
		this.luogoPrelievoCodice = luogoPrelievoCodice;
	}

	public String getMetodoCampionamentoCodice() {
		return metodoCampionamentoCodice;
	}

	public void setMetodoCampionamentoCodice(String metodoCampionamentoCodice) {
		this.metodoCampionamentoCodice = metodoCampionamentoCodice;
	}

	public String getMotivoCodice() {
		return motivoCodice;
	}

	public void setMotivoCodice(String motivoCodice) {
		this.motivoCodice = motivoCodice;
	}

	public String getNumeroScheda() {
		return numeroScheda;
	}

	public void setNumeroScheda(String numeroScheda) {
		this.numeroScheda = numeroScheda;
	}

	public String getNumeroVerbale() {
		return numeroVerbale;
	}

	public void setNumeroVerbale(String numeroVerbale) {
		this.numeroVerbale = numeroVerbale;
	}

	public String getPianoCodice() {
		return pianoCodice;
	}

	public void setPianoCodice(String pianoCodice) {
		this.pianoCodice = pianoCodice;
	}

	public String getPrelCodFiscale() {
		return prelCodFiscale;
	}

	public void setPrelCodFiscale(String prelCodFiscale) {
		this.prelCodFiscale = prelCodFiscale;
	}

	public String getTipoImpresa() {
		return tipoImpresa;
	}

	public void setTipoImpresa(String tipoImpresa) {
		this.tipoImpresa = tipoImpresa;
	}

	public int getIdImport() {
		return idImport;
	}

	public void setIdImport(int idImport) {
		this.idImport = idImport;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}

	public Timestamp getEntered() {
		return entered;
	}

	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	private String getValore(Cell cell) {
		String valore = "";
		int type = cell.getCellType();
		
		if (type == Cell.CELL_TYPE_STRING)
			valore = cell.getStringCellValue();
		else if (HSSFDateUtil.isCellDateFormatted(cell)){
			Date date = cell.getDateCellValue();
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	        valore = formatter.format(date);
		}
		else if (type == Cell.CELL_TYPE_NUMERIC)
			valore = String.valueOf(cell.getNumericCellValue());
		return valore;		
		
	}
}
