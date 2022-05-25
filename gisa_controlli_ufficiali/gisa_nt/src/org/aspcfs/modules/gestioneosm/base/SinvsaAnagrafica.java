/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneosm.base;

import it.izs.ws.WsPost;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.aspcfs.modules.gestioneosm.util.OSMUtil;
import org.aspcfs.modules.util.imports.ApplicationProperties;

import com.darkhorseventures.framework.beans.GenericBean;

public class SinvsaAnagrafica extends GenericBean {
	
	Logger logger = Logger.getLogger("MainLogger");

private Timestamp data;
	

	private int idPersona;
	private String esitoPersona;

	private int idSedeOperativa;
	private String esitoSedeOperativa;

	private int idSedeLegale;
	private String esitoSedeLegale;

	private int idImpresa;
	private String esitoImpresa;
	
	private int idUtente;
	private int riferimentoId;
	private String riferimentoIdNomeTab;

	private String cunSinvsa;
	
	private String impresaRagioneSociale;
	private String impresaPartitaIva;

	private ArrayList<SinvsaSezioneAttivita> listaSezioneAttivita = new ArrayList<SinvsaSezioneAttivita>();
	
	public SinvsaAnagrafica() {
		
	}
	
	public SinvsaAnagrafica(Connection db, int riferimentoId, String riferimentoIdNomeTab) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from sinvsa_osm_anagrafica_view s LEFT JOIN sinvsa_osm_anagrafica_esiti e on (s.riferimento_id = e.riferimento_id and s.riferimento_id_nome_tab = e.riferimento_id_nome_tab and e.trashed_date is null) where s.riferimento_id = ? and s.riferimento_id_nome_tab = ?");
		pst.setInt(1, riferimentoId);
		pst.setString(2, riferimentoIdNomeTab);
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			buildRecord(rs);
			setListaSezioneAttivita(db);
		}
	}

	public SinvsaAnagrafica(int riferimentoId, String riferimentoIdNomeTab) {
		this.riferimentoId = riferimentoId;
		this.riferimentoIdNomeTab = riferimentoIdNomeTab;
	}

	private void buildRecord(ResultSet rs) throws SQLException {
		data = rs.getTimestamp("data");
		esitoPersona = rs.getString("esito_persona");
		esitoSedeOperativa = rs.getString("esito_sede_operativa");
		esitoSedeLegale = rs.getString("esito_sede_legale");
		esitoImpresa = rs.getString("esito_impresa");
		idUtente = rs.getInt("id_utente");
		riferimentoId = rs.getInt("riferimento_id");
		riferimentoIdNomeTab = rs.getString("riferimento_id_nome_tab");
		idPersona = rs.getInt("id_persona");
		idImpresa = rs.getInt("id_impresa");
		idSedeLegale = rs.getInt("id_sede_legale");
		idSedeOperativa = rs.getInt("id_sede_operativa");
		cunSinvsa = rs.getString("cun_sinvsa");
		impresaRagioneSociale = rs.getString("impresa_ragione_sociale");
		impresaPartitaIva = rs.getString("impresa_partita_iva");

	}

	private void setListaSezioneAttivita(Connection db) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from sinvsa_osm_sezione_attivita_view a LEFT JOIN sinvsa_osm_sezione_attivita_esiti e on (a.codice_macroarea = e.codice_macroarea and a.codice_aggregazione = e.codice_aggregazione and a.riferimento_id = e.riferimento_id and a.riferimento_id_nome_tab = e.riferimento_id_nome_tab and e.trashed_date is null) where a.riferimento_id = ? and a.riferimento_id_nome_tab = ?");
		pst.setInt(1, riferimentoId);
		pst.setString(2, riferimentoIdNomeTab);
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			SinvsaSezioneAttivita sezioneAttivita = new SinvsaSezioneAttivita(rs);
			sezioneAttivita.setListaProdottoSpecie(db);
			listaSezioneAttivita.add(sezioneAttivita);
		}
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public Timestamp getData() {
		return data;
	}

	public void setData(Timestamp data) {
		this.data = data;
	}

	public int getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}

	public String getEsitoPersona() {
		return esitoPersona;
	}

	public void setEsitoPersona(String esitoPersona) {
		this.esitoPersona = esitoPersona;
	}

	public int getIdSedeOperativa() {
		return idSedeOperativa;
	}

	public void setIdSedeOperativa(int idSedeOperativa) {
		this.idSedeOperativa = idSedeOperativa;
	}

	public String getEsitoSedeOperativa() {
		return esitoSedeOperativa;
	}

	public void setEsitoSedeOperativa(String esitoSedeOperativa) {
		this.esitoSedeOperativa = esitoSedeOperativa;
	}

	public int getIdSedeLegale() {
		return idSedeLegale;
	}

	public void setIdSedeLegale(int idSedeLegale) {
		this.idSedeLegale = idSedeLegale;
	}

	public String getEsitoSedeLegale() {
		return esitoSedeLegale;
	}

	public void setEsitoSedeLegale(String esitoSedeLegale) {
		this.esitoSedeLegale = esitoSedeLegale;
	}

	public int getIdImpresa() {
		return idImpresa;
	}

	public void setIdImpresa(int idImpresa) {
		this.idImpresa = idImpresa;
	}

	public String getEsitoImpresa() {
		return esitoImpresa;
	}

	public void setEsitoImpresa(String esitoImpresa) {
		this.esitoImpresa = esitoImpresa;
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public int getRiferimentoId() {
		return riferimentoId;
	}

	public void setRiferimentoId(int riferimentoId) {
		this.riferimentoId = riferimentoId;
	}

	public String getRiferimentoIdNomeTab() {
		return riferimentoIdNomeTab;
	}

	public void setRiferimentoIdNomeTab(String riferimentoIdNomeTab) {
		this.riferimentoIdNomeTab = riferimentoIdNomeTab;
	}

	public String getCunSinvsa() {
		return cunSinvsa;
	}

	public void setCunSinvsa(String cunSinvsa) {
		this.cunSinvsa = cunSinvsa;
	}

	public String getImpresaRagioneSociale() {
		return impresaRagioneSociale;
	}

	public void setImpresaRagioneSociale(String impresaRagioneSociale) {
		this.impresaRagioneSociale = impresaRagioneSociale;
	}

	public String getImpresaPartitaIva() {
		return impresaPartitaIva;
	}

	public void setImpresaPartitaIva(String impresaPartitaIva) {
		this.impresaPartitaIva = impresaPartitaIva;
	}

	public ArrayList<SinvsaSezioneAttivita> getListaSezioneAttivita() {
		return listaSezioneAttivita;
	}

	public void setListaSezioneAttivita(ArrayList<SinvsaSezioneAttivita> listaSezioneAttivita) {
		this.listaSezioneAttivita = listaSezioneAttivita;
	}

	
	public void invio(Connection db, int userId, int idImportMassivo) throws SQLException{
		 
		int id = -1;
		int idNew = -1;
		String faultString ="";
		String response = "";
		
		System.out.println(" ------ INVIO OSM A SINVSA ["+riferimentoIdNomeTab+"/"+riferimentoId +"]-----"); 

		// ----------------------------------------- PERSONA

		response= searchPersona(db, userId, riferimentoId, riferimentoIdNomeTab, true);

		id = -1;

		try {id = Integer.parseInt(OSMUtil.estraiDaPattern("<osaId>", "</osaId>", response, 1));} catch (Exception e) {}

		if (id>0)
			setIdPersona(id);

		idNew = -1;
		faultString = "";
		
		if (getIdPersona() > 0) 
			response= updatePersona(db, userId, riferimentoId, riferimentoIdNomeTab, getIdPersona(), true);
		else
			response= insertPersona(db, userId, riferimentoId, riferimentoIdNomeTab, true);

		try {idNew = Integer.parseInt(OSMUtil.estraiDaPattern("<return xmlns=\"\">", "</return>", response, 1));} catch (Exception e) {}
		try {faultString = OSMUtil.estraiDaPattern("<faultstring xmlns=\"\">", "</faultstring>", response, 1);} catch (Exception e) {}

		if (faultString.equalsIgnoreCase("NESSUNA MODIFICA") || faultString.equalsIgnoreCase("RECORD PRESENTE IN ANAGRAFE") || (id>0 && idNew<=0))
			idNew = id;

		setIdPersona(idNew);
		setEsitoPersona(response);

		if (getIdPersona()>0){

			// ----------------------------------------- SEDE LEGALE

			response= searchSedeLegale(db, userId, riferimentoId, riferimentoIdNomeTab, true);

			id = -1;

			try {id = Integer.parseInt(OSMUtil.estraiDaPattern("<sliId>", "</sliId>", response, 1));} catch (Exception e) {}

			setIdSedeLegale(id);

			idNew = -1;
			faultString = "";
			if (getIdSedeLegale() > 0) 
				response= updateSedeLegale(db, userId, riferimentoId, riferimentoIdNomeTab, getIdSedeLegale(), true);
			else
				response= insertSedeLegale(db, userId, riferimentoId, riferimentoIdNomeTab, true);

			try {idNew = Integer.parseInt(OSMUtil.estraiDaPattern("<return xmlns=\"\">", "</return>", response, 1));} catch (Exception e) {}
			try {faultString = OSMUtil.estraiDaPattern("<faultstring xmlns=\"\">", "</faultstring>", response, 1);} catch (Exception e) {}

			if (faultString.equalsIgnoreCase("NESSUNA MODIFICA") || faultString.equalsIgnoreCase("RECORD PRESENTE IN ANAGRAFE") || (id>0 && idNew<=0))
				idNew = id;

			setIdSedeLegale(idNew);
			setEsitoSedeLegale(response);

			if (getIdSedeLegale()>0){

				// ----------------------------------------- SEDE OPERATIVA

				response= searchSedeOperativa(db, userId, riferimentoId, riferimentoIdNomeTab, true);

				id = -1;

				try {id = Integer.parseInt(OSMUtil.estraiDaPattern("<spiId>", "</spiId>", response, 1));} catch (Exception e) {}
				try {setCunSinvsa(OSMUtil.estraiDaPattern("<spiCun>", "</spiCun>", response, 1));} catch (Exception e) {}

				setIdSedeOperativa(id);

				idNew = -1;
				faultString = "";
				if (getIdSedeOperativa() > 0) 
					response= updateSedeOperativa(db, userId, riferimentoId, riferimentoIdNomeTab, getIdSedeOperativa(), getCunSinvsa(), true);
				else
					response= insertSedeOperativa(db, userId, riferimentoId, riferimentoIdNomeTab, true);

				try {idNew = Integer.parseInt(OSMUtil.estraiDaPattern("<return xmlns=\"\">", "</return>", response, 1));} catch (Exception e) {}
				try {faultString = OSMUtil.estraiDaPattern("<faultstring xmlns=\"\">", "</faultstring>", response, 1);} catch (Exception e) {}

				if (faultString.equalsIgnoreCase("NESSUNA MODIFICA") || faultString.equalsIgnoreCase("RECORD PRESENTE IN ANAGRAFE") || (id>0 && idNew<=0))
					idNew = id;

				setIdSedeOperativa(idNew);
				setEsitoSedeOperativa(response);

				response= searchSedeOperativaByPk(db, userId, riferimentoId, riferimentoIdNomeTab, idNew, true);

				setCunSinvsa(OSMUtil.estraiDaPattern("<spiCun>", "</spiCun>", response, 1));

				if (getIdSedeOperativa()>0){

					// ----------------------------------------- IMPRESA

					response= searchRegistrazioniImprese(db, userId, riferimentoId, riferimentoIdNomeTab, getCunSinvsa(), true);

					id = -1;

					try {id = Integer.parseInt(OSMUtil.estraiDaPattern("<registrId>", "</registrId>", response, 1));} catch (Exception e) {}

					setIdImpresa(id);

					idNew = -1;
					faultString = "";
					if (getIdImpresa() > 0) 
						response= updateRegistrazioniImprese(db, userId, riferimentoId, riferimentoIdNomeTab, getIdImpresa(), true);
					else
						response= insertRegistrazioniImprese(db, userId, riferimentoId, riferimentoIdNomeTab, getCunSinvsa(), true);

					try {idNew = Integer.parseInt(OSMUtil.estraiDaPattern("<return xmlns=\"\">", "</return>", response, 1));} catch (Exception e) {}
					try {faultString = OSMUtil.estraiDaPattern("<faultstring xmlns=\"\">", "</faultstring>", response, 1);} catch (Exception e) {}

					if (faultString.equalsIgnoreCase("NESSUNA MODIFICA") || faultString.equalsIgnoreCase("RECORD PRESENTE IN ANAGRAFE")  || (id>0 && idNew<=0))
						idNew = id;

					setIdImpresa(idNew);
					setEsitoImpresa(response);

					if (getIdImpresa()>0){
						
						
						for (int i = 0; i<getListaSezioneAttivita().size(); i++){
							// ----------------------------------------- ATTIVITA
							SinvsaSezioneAttivita sezAtt = (SinvsaSezioneAttivita) getListaSezioneAttivita().get(i);
							
							response= searchSezioniAttivitaImpresa(db, userId, riferimentoId, riferimentoIdNomeTab, sezAtt.getCodiceMacroarea(), sezAtt.getCodiceAggregazione(), getCunSinvsa(), true);

							id = -1;

							try {id = Integer.parseInt(OSMUtil.estraiDaPattern("<saiId>", "</saiId>", response, 1));} catch (Exception e) {}

							sezAtt.setIdSezioneAttivita(id);

							idNew = -1;
							faultString ="";
							if (sezAtt.getIdSezioneAttivita() > 0) 
								response= updateSezioniAttivitaImpresa(db, userId, riferimentoId, riferimentoIdNomeTab, sezAtt.getCodiceMacroarea(), sezAtt.getCodiceAggregazione(), sezAtt.getIdSezioneAttivita(), getCunSinvsa(), true);
							else
								response= insertSezioniAttivitaImpresa(db, userId, riferimentoId, riferimentoIdNomeTab, sezAtt.getCodiceMacroarea(), sezAtt.getCodiceAggregazione(), getCunSinvsa(), true);

							try {idNew = Integer.parseInt(OSMUtil.estraiDaPattern("<return xmlns=\"\">", "</return>", response, 1));} catch (Exception e) {}
							try {faultString = OSMUtil.estraiDaPattern("<faultstring xmlns=\"\">", "</faultstring>", response, 1);} catch (Exception e) {}

							if (faultString.equalsIgnoreCase("NESSUNA MODIFICA") || faultString.equalsIgnoreCase("RECORD PRESENTE IN ANAGRAFE")  || (id>0 && idNew<=0))
								idNew = id;

							sezAtt.setIdSezioneAttivita(idNew);
							sezAtt.setEsitoSezioneAttivita(response);
							
							sezAtt.insertEsiti(db, userId, idImportMassivo);
							
							for (int j = 0; j<sezAtt.getListaProdottoSpecie().size(); j++){
								// ----------------------------------------- PRODOTTO SPECIE
								SinvsaProdottoSpecie prodSpe = (SinvsaProdottoSpecie) sezAtt.getListaProdottoSpecie().get(j);
								
								response= searchSezioniAttivitaImpresa(db, userId, riferimentoId, riferimentoIdNomeTab, prodSpe.getCodiceMacroarea(), prodSpe.getCodiceAggregazione(), prodSpe.getCodiceAttivita(), getCunSinvsa(), true);
							
								id = -1;
								try {id = Integer.parseInt(OSMUtil.estraiDaPattern("<psaiId>", "</psaiId>", response, 1));} catch (Exception e) {}
								prodSpe.setIdProdottoSpecie(id);
															
								idNew = -1;
								faultString ="";
								if (prodSpe.getIdProdottoSpecie() > 0){ 
									//response= updateProdottoSpecie(db, userId, riferimentoId, riferimentoIdNomeTab, prodSpe.getCodiceMacroarea(), prodSpe.getCodiceAggregazione(), prodSpe.getCodiceAttivita(), getCunSinvsa(), prodSpe.getIdProdottoSpecie(), true);
								}
								else
									response= insertProdottoSpecie(db, userId, riferimentoId, riferimentoIdNomeTab, prodSpe.getCodiceMacroarea(), prodSpe.getCodiceAggregazione(), prodSpe.getCodiceAttivita(), getCunSinvsa(), true);

								try {idNew = Integer.parseInt(OSMUtil.estraiDaPattern("<return xmlns=\"\">", "</return>", response, 1));} catch (Exception e) {}
								try {faultString = OSMUtil.estraiDaPattern("<faultstring xmlns=\"\">", "</faultstring>", response, 1);} catch (Exception e) {}

								if (faultString.equalsIgnoreCase("NESSUNA MODIFICA") || faultString.equalsIgnoreCase("RECORD PRESENTE IN ANAGRAFE") || (id>0 && idNew<=0))
									idNew = id;

								prodSpe.setIdProdottoSpecie(idNew);
								prodSpe.setEsitoProdottoSpecie(response);
								
								prodSpe.insertEsiti(db, userId, idImportMassivo);
							
							}
							

						}

						}}}}

		insertEsiti(db, userId, idImportMassivo);

	}
	

	public void insertEsiti(Connection db, int userId, int idImportMassivo) throws SQLException {
		
		PreparedStatement pst = null;
		int i = 0;
		
		String sqlUpdate = "update sinvsa_osm_anagrafica_esiti set trashed_date = now() where trashed_date is null and riferimento_id = ? and riferimento_id_nome_tab = ?";
		pst = db.prepareStatement(sqlUpdate);
		pst.setInt(++i, riferimentoId);
		pst.setString(++i, riferimentoIdNomeTab);
		pst.executeUpdate();
		
		i = 0;
		String sqlInsert = "insert into sinvsa_osm_anagrafica_esiti (id_import_massivo, riferimento_id_nome_tab, riferimento_id, id_utente, id_persona, esito_persona, id_sede_legale, esito_sede_legale, id_sede_operativa, esito_sede_operativa, id_impresa, esito_impresa, cun_sinvsa) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		pst = db.prepareStatement(sqlInsert);
		pst.setInt(++i, idImportMassivo);
		pst.setString(++i, riferimentoIdNomeTab);
		pst.setInt(++i, riferimentoId);
		pst.setInt(++i, userId);
		pst.setInt(++i, idPersona);
		pst.setString(++i, esitoPersona);
		pst.setInt(++i, idSedeLegale);
		pst.setString(++i, esitoSedeLegale);
		pst.setInt(++i, idSedeOperativa);
		pst.setString(++i, esitoSedeOperativa);
		pst.setInt(++i, idImpresa);
		pst.setString(++i, esitoImpresa);
		pst.setString(++i, cunSinvsa);
		pst.execute();
		
	}
	
	
	private String searchPersona(Connection db, int userId, int riferimentoId, String riferimentoIdNomeTab, boolean invia) throws SQLException{
		WsPost ws = new WsPost();
		String envelope = null;
		String response = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		logger.info(" ------ INVIO DATI searchPersone A SINVSA ["+riferimentoIdNomeTab+"/"+riferimentoId+"] -----"); 

		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_OSM_PERSONE"));

		pst = db.prepareStatement("select * from get_chiamata_ws_search_osm_persona(?, ?, ?, ?)");
		pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_BDN"));
		pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_BDN"));
		pst.setInt(3, riferimentoId);
		pst.setString(4, riferimentoIdNomeTab);
		rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);

		if (!invia)
			return envelope;

		ws.setWsRequest(envelope);
		response= ws.post(db, userId);
		return response;
	}

	private String updatePersona(Connection db, int userId, int riferimentoId, String riferimentoIdNomeTab, int idPersona, boolean invia) throws SQLException{
		WsPost ws = new WsPost();
		String envelope = null;
		String response = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		logger.info(" ------ INVIO DATI updatePersona A SINVSA ["+riferimentoIdNomeTab+"/"+riferimentoId+"] -----"); 
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_OSM_PERSONE"));

		pst = db.prepareStatement("select * from get_chiamata_ws_update_osm_persona(?, ?, ?, ?, ?)");
		pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_BDN"));
		pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_BDN"));
		pst.setInt(3, riferimentoId);
		pst.setString(4, riferimentoIdNomeTab);
		pst.setInt(5, idPersona);
		rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);

		if (!invia)
			return envelope;

		ws.setWsRequest(envelope);
		response= ws.post(db, userId);

		return response;
	}

	private String insertPersona(Connection db, int userId, int riferimentoId, String riferimentoIdNomeTab, boolean invia) throws SQLException{
		WsPost ws = new WsPost();
		String envelope = null;
		String response = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		logger.info(" ------ INVIO DATI insertPersona A SINVSA ["+riferimentoIdNomeTab+"/"+riferimentoId+"] -----"); 
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_OSM_PERSONE"));

		pst = db.prepareStatement("select * from get_chiamata_ws_insert_osm_persona(?, ?, ?, ?)");
		pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_BDN"));
		pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_BDN"));
		pst.setInt(3, riferimentoId);
		pst.setString(4, riferimentoIdNomeTab);
		rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);

		if (!invia)
			return envelope;

		ws.setWsRequest(envelope);
		response= ws.post(db, userId);

		return response;
	}

	private String searchSedeLegale(Connection db, int userId, int riferimentoId, String riferimentoIdNomeTab, boolean invia) throws SQLException{
		WsPost ws = new WsPost();
		String envelope = null;
		String response = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		logger.info(" ------ INVIO DATI searchSediLegali A SINVSA ["+riferimentoIdNomeTab+"/"+riferimentoId+"] -----"); 
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_OSM_IMPRESE"));

		pst = db.prepareStatement("select * from get_chiamata_ws_search_osm_sede_legale(?, ?, ?, ?)");
		pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_BDN"));
		pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_BDN"));
		pst.setInt(3, riferimentoId);
		pst.setString(4, riferimentoIdNomeTab);
		rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);

		if (!invia)
			return envelope;

		ws.setWsRequest(envelope);
		response= ws.post(db, userId);

		return response;
	}

	private String updateSedeLegale(Connection db, int userId, int riferimentoId, String riferimentoIdNomeTab, int idSedeLegale, boolean invia) throws SQLException{
		WsPost ws = new WsPost();
		String envelope = null;
		String response = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		logger.info(" ------ INVIO DATI updateSedeLegale A SINVSA ["+riferimentoIdNomeTab+"/"+riferimentoId+"] -----"); 
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_OSM_IMPRESE"));

		pst = db.prepareStatement("select * from get_chiamata_ws_update_osm_sede_legale(?, ?, ?, ?, ?)");
		pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_BDN"));
		pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_BDN"));
		pst.setInt(3, riferimentoId);
		pst.setString(4, riferimentoIdNomeTab);
		pst.setInt(5, idSedeLegale);
		rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);

		if (!invia)
			return envelope;

		ws.setWsRequest(envelope);
		response= ws.post(db, userId);

		return response;
	}

	private String insertSedeLegale(Connection db, int userId, int riferimentoId, String riferimentoIdNomeTab, boolean invia) throws SQLException{
		WsPost ws = new WsPost();
		String envelope = null;
		String response = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		logger.info(" ------ INVIO DATI insertSedeLegale A SINVSA ["+riferimentoIdNomeTab+"/"+riferimentoId+"] -----"); 
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_OSM_IMPRESE"));

		pst = db.prepareStatement("select * from get_chiamata_ws_insert_osm_sede_legale(?, ?, ?, ?)");
		pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_BDN"));
		pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_BDN"));
		pst.setInt(3, riferimentoId);
		pst.setString(4, riferimentoIdNomeTab);
		rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);

		if (!invia)
			return envelope;

		ws.setWsRequest(envelope);
		response= ws.post(db, userId);

		return response;
	}


	private String searchSedeOperativa(Connection db, int userId, int riferimentoId, String riferimentoIdNomeTab, boolean invia) throws SQLException{
		WsPost ws = new WsPost();
		String envelope = null;
		String response = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		logger.info(" ------ INVIO DATI searchSediOperative A SINVSA ["+riferimentoIdNomeTab+"/"+riferimentoId+"] -----"); 
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_OSM_IMPRESE"));

		pst = db.prepareStatement("select * from get_chiamata_ws_search_osm_sede_operativa(?, ?, ?, ?)");
		pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_BDN"));
		pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_BDN"));
		pst.setInt(3, riferimentoId);
		pst.setString(4, riferimentoIdNomeTab);
		rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);

		if (!invia)
			return envelope;

		ws.setWsRequest(envelope);
		response= ws.post(db, userId);	

		return response;
	}

	private String searchSedeOperativaByPk(Connection db, int userId, int riferimentoId, String riferimentoIdNomeTab, int idSedeOperativa, boolean invia) throws SQLException{
		WsPost ws = new WsPost();
		String envelope = null;
		String response = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		logger.info(" ------ INVIO DATI searchSedeoperativaByPk A SINVSA ["+riferimentoIdNomeTab+"/"+riferimentoId+"] -----"); 
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_OSM_IMPRESE"));

		pst = db.prepareStatement("select * from get_chiamata_ws_search_osm_sede_operativa_by_pk(?, ?, ?)");
		pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_BDN"));
		pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_BDN"));
		pst.setInt(3, idSedeOperativa);
		rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);

		if (!invia)
			return envelope;

		ws.setWsRequest(envelope);
		response= ws.post(db, userId);	

		return response;
	}

	private String updateSedeOperativa(Connection db, int userId, int riferimentoId, String riferimentoIdNomeTab, int idSedeOperativa, String cunSinvsa, boolean invia) throws SQLException{
		WsPost ws = new WsPost();
		String envelope = null;
		String response = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		logger.info(" ------ INVIO DATI updateSedeOperativa A SINVSA ["+riferimentoIdNomeTab+"/"+riferimentoId+"] -----"); 
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_OSM_IMPRESE"));

		pst = db.prepareStatement("select * from get_chiamata_ws_update_osm_sede_operativa(?, ?, ?, ?, ?, ?)");
		pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_BDN"));
		pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_BDN"));
		pst.setInt(3, riferimentoId);
		pst.setString(4, riferimentoIdNomeTab);
		pst.setInt(5, idSedeOperativa);
		pst.setString(6, cunSinvsa);
		rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);

		if (!invia)
			return envelope;

		ws.setWsRequest(envelope);
		response= ws.post(db, userId);

		return response;
	}

	private String insertSedeOperativa(Connection db, int userId, int riferimentoId, String riferimentoIdNomeTab, boolean invia) throws SQLException{
		WsPost ws = new WsPost();
		String envelope = null;
		String response = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		logger.info(" ------ INVIO DATI insertSedeOperativa A SINVSA ["+riferimentoIdNomeTab+"/"+riferimentoId+"] -----"); 
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_OSM_IMPRESE"));

		pst = db.prepareStatement("select * from get_chiamata_ws_insert_osm_sede_operativa(?, ?, ?, ?)");
		pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_BDN"));
		pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_BDN"));
		pst.setInt(3, riferimentoId);
		pst.setString(4, riferimentoIdNomeTab);
		rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);

		if (!invia)
			return envelope;

		ws.setWsRequest(envelope);
		response= ws.post(db, userId);

		return response;
	}

	private String searchRegistrazioniImprese(Connection db, int userId, int riferimentoId, String riferimentoIdNomeTab, String cunSinvsa, boolean invia) throws SQLException{
		WsPost ws = new WsPost();
		String envelope = null;
		String response = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		logger.info(" ------ INVIO DATI searchRegistrazioniimprese A SINVSA ["+riferimentoIdNomeTab+"/"+riferimentoId+"] -----"); 
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_OSM_REGISTRAZIONI_IMPRESE"));

		pst = db.prepareStatement("select * from get_chiamata_ws_search_osm_impresa(?, ?, ?, ?, ?)");
		pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_BDN"));
		pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_BDN"));
		pst.setInt(3, riferimentoId);
		pst.setString(4, riferimentoIdNomeTab);
		pst.setString(5, cunSinvsa);
		rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);

		if (!invia)
			return envelope;

		ws.setWsRequest(envelope);
		response= ws.post(db, userId);

		return response;
	}

	private String updateRegistrazioniImprese(Connection db, int userId, int riferimentoId, String riferimentoIdNomeTab, int idImpresa, boolean invia) throws SQLException{
		//Non esiste il ws!
		return "";
	}

	private String insertRegistrazioniImprese(Connection db, int userId, int riferimentoId, String riferimentoIdNomeTab, String cunSinvsa, boolean invia) throws SQLException{
		WsPost ws = new WsPost();
		String envelope = null;
		String response = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		logger.info(" ------ INVIO DATI insertRegistrazioniimprese A SINVSA ["+riferimentoIdNomeTab+"/"+riferimentoId+"] -----"); 
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_OSM_REGISTRAZIONI_IMPRESE"));

		pst = db.prepareStatement("select * from get_chiamata_ws_insert_osm_impresa(?, ?, ?, ?, ?)");
		pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_BDN"));
		pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_BDN"));
		pst.setInt(3, riferimentoId);
		pst.setString(4, riferimentoIdNomeTab);
		pst.setString(5, cunSinvsa);
		rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);

		if (!invia)
			return envelope;

		ws.setWsRequest(envelope);
		response= ws.post(db, userId);

		return response;
	}
	
	private String searchSezioniAttivitaImpresa(Connection db, int userId, int riferimentoId, String riferimentoIdNomeTab, String codiceMacroarea, String codiceAggregazione, String cunSinvsa, boolean invia) throws SQLException{
		WsPost ws = new WsPost();
		String envelope = null;
		String response = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		logger.info(" ------ INVIO DATI searchSezioniattivitaimpresa A SINVSA ["+riferimentoIdNomeTab+"/"+riferimentoId+"/"+codiceMacroarea+"/"+codiceAggregazione+"] -----"); 
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_OSM_SEZIONI_ATTIVITA_IMPRESA"));

		pst = db.prepareStatement("select * from get_chiamata_ws_search_osm_sezione_attivita(?, ?, ?, ?, ?)");
		pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_BDN"));
		pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_BDN"));
		pst.setInt(3, riferimentoId);
		pst.setString(4, riferimentoIdNomeTab);
		pst.setString(5, cunSinvsa);
		rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);

		if (!invia)
			return envelope;

		ws.setWsRequest(envelope);
		response= ws.post(db, userId);
		
		int numOccorrenze = OSMUtil.contaOccorrenze("<sezioniattivitaimpresa>", response);
		
		for (int i = 1; i<= numOccorrenze; i++){
			String responseParsed = OSMUtil.estraiDaPattern("<sezioniattivitaimpresa>", "</sezioniattivitaimpresa>", response, i);
			
			String sezCodice =  OSMUtil.estraiDaPattern("<sezCodice>", "</sezCodice>", responseParsed, 1);
			String attCodice =  OSMUtil.estraiDaPattern("<attCodice>", "</attCodice>", responseParsed, 1);
			
			if (sezCodice.equals(codiceMacroarea) && attCodice.equals(codiceAggregazione)) {
				return responseParsed;
			}
			
		}
	
		return "";
	}
	
	private String searchSezioniAttivitaImpresa(Connection db, int userId, int riferimentoId, String riferimentoIdNomeTab, String codiceMacroarea, String codiceAggregazione, String codiceAttivita, String cunSinvsa, boolean invia) throws SQLException{
		WsPost ws = new WsPost();
		String envelope = null;
		String response = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		logger.info(" ------ INVIO DATI searchSezioniattivitaimpresa A SINVSA ["+riferimentoIdNomeTab+"/"+riferimentoId+"/"+codiceMacroarea+"/"+codiceAggregazione+"] -----"); 
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_OSM_SEZIONI_ATTIVITA_IMPRESA"));

		pst = db.prepareStatement("select * from get_chiamata_ws_search_osm_sezione_attivita(?, ?, ?, ?, ?)");
		pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_BDN"));
		pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_BDN"));
		pst.setInt(3, riferimentoId);
		pst.setString(4, riferimentoIdNomeTab);
		pst.setString(5, cunSinvsa);
		rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);

		if (!invia)
			return envelope;

		ws.setWsRequest(envelope);
		response= ws.post(db, userId);
		
		int numOccorrenzeSezioniAttivita = OSMUtil.contaOccorrenze("<return xmlns=\"\">", response);
		
		for (int i = 1; i<= numOccorrenzeSezioniAttivita; i++){
			String responseParsed = OSMUtil.estraiDaPattern("<return xmlns=\"\">", "</return>", response, i);
			
			String sezCodice =  OSMUtil.estraiDaPattern("<sezCodice>", "</sezCodice>", responseParsed, 1);
			String attCodice =  OSMUtil.estraiDaPattern("<attCodice>", "</attCodice>", responseParsed, 1);
			
			int numOccorrenzePsp = OSMUtil.contaOccorrenze("<psp>", responseParsed);
			for (int j = 1; j<= numOccorrenzePsp; j++){
				String responseParsed2 = OSMUtil.estraiDaPattern("<psp>", "</psp>", responseParsed, j);
				String pspCodice =  OSMUtil.estraiDaPattern("<pspCodice>", "</pspCodice>", responseParsed2, 1);
	
				if (sezCodice.equals(codiceMacroarea) && attCodice.equals(codiceAggregazione) && pspCodice.equals(codiceAttivita)) {
					return responseParsed2;
				}
			
		}
		}
		return "";
	}

	private String updateSezioniAttivitaImpresa(Connection db, int userId, int riferimentoId, String riferimentoIdNomeTab, String codiceMacroarea, String codiceAggregazione, int idSezioneAttivita, String cunSinvsa, boolean invia) throws SQLException{
		WsPost ws = new WsPost();
		String envelope = null;
		String response = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		logger.info(" ------ INVIO DATI updateSezioniattivitaimpresa A SINVSA ["+riferimentoIdNomeTab+"/"+riferimentoId+"/"+idSezioneAttivita+"] -----"); 
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_OSM_SEZIONI_ATTIVITA_IMPRESA"));

		pst = db.prepareStatement("select * from get_chiamata_ws_update_osm_sezione_attivita(?, ?, ?, ?, ?, ?, ?, ?)");
		pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_BDN"));
		pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_BDN"));
		pst.setInt(3, riferimentoId);
		pst.setString(4, riferimentoIdNomeTab);
		pst.setString(5, codiceMacroarea);
		pst.setString(6, codiceAggregazione);
		pst.setInt(7, idSezioneAttivita);
		pst.setString(8, cunSinvsa);
		rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);

		if (!invia)
			return envelope;

		ws.setWsRequest(envelope);
		response= ws.post(db, userId);

		return response;
	}

	private String insertSezioniAttivitaImpresa(Connection db, int userId, int riferimentoId, String riferimentoIdNomeTab, String codiceMacroarea, String codiceAggregazione, String cunSinvsa, boolean invia) throws SQLException{
		WsPost ws = new WsPost();
		String envelope = null;
		String response = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		logger.info(" ------ INVIO DATI insertSezioniattivitaimpresa A SINVSA ["+riferimentoIdNomeTab+"/"+riferimentoId+"/"+codiceMacroarea+"/"+codiceAggregazione+"] -----"); 
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_OSM_SEZIONI_ATTIVITA_IMPRESA"));

		pst = db.prepareStatement("select * from get_chiamata_ws_insert_osm_sezione_attivita(?, ?, ?, ?, ?, ?, ?)");
		pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_BDN"));
		pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_BDN"));
		pst.setInt(3, riferimentoId);
		pst.setString(4, riferimentoIdNomeTab);
		pst.setString(5, codiceMacroarea);
		pst.setString(6, codiceAggregazione);
		pst.setString(7, cunSinvsa);
		rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);

		if (!invia)
			return envelope;

		ws.setWsRequest(envelope);
		response= ws.post(db, userId);

		return response;
	}
	
	
	private String insertProdottoSpecie(Connection db, int userId, int riferimentoId, String riferimentoIdNomeTab, String codiceMacroarea, String codiceAggregazione, String codiceAttivita, String cunSinvsa, boolean invia) throws SQLException {
		WsPost ws = new WsPost();
		String envelope = null;
		String response = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		logger.info(" ------ INVIO DATI insertPspSezioniattivitaimpresa A SINVSA ["+riferimentoIdNomeTab+"/"+riferimentoId+"/"+codiceMacroarea+"/"+codiceAggregazione+"/"+codiceAttivita+"] -----"); 
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_OSM_SEZIONI_ATTIVITA_IMPRESA"));

		pst = db.prepareStatement("select * from get_chiamata_ws_insert_osm_psp_sezione_attivita(?, ?, ?, ?, ?, ?, ?, ?)");
		pst.setString(1, ApplicationProperties.getProperty("USERNAME_WS_BDN"));
		pst.setString(2, ApplicationProperties.getProperty("PASSWORD_WS_BDN"));
		pst.setInt(3, riferimentoId);
		pst.setString(4, riferimentoIdNomeTab);
		pst.setString(5, codiceMacroarea);
		pst.setString(6, codiceAggregazione);
		pst.setString(7, codiceAttivita);
		pst.setString(8, cunSinvsa);
		rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);

		if (!invia)
			return envelope;

		ws.setWsRequest(envelope);
		response= ws.post(db, userId);

		return response;
	
	}
	
	
	
	
	
	
	
	
	
}
