/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.fromresttogisa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.aspcfs.modules.suap.base.Stabilimento;

public class UtilAccessoDb {

	Logger logger = Logger.getLogger(UtilAccessoDb.class);
	
	// prima di fare il paragone elimina il padding sia al paragonato che a
	// quello con cui paragonare
	public static int getIdComuneFromIstatComuneECodeProvincia(Connection db, String istatComune, String istatProvincia) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		int toRet = -1;
		try {
			pst = db.prepareStatement("select id from comuni1 where istat::integer::text = ?::integer::text and cod_provincia::integer::text = ?::integer::text");
			pst.setString(1, istatComune);
			pst.setString(2, istatProvincia);
			rs = pst.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			try {
				rs.close();
			} catch (Exception e) {
			}
			try {
				rs.close();
			} catch (Exception e) {
			}
		}
		return toRet;
	}

	public static int getIdComuneFromDescr(Connection db, String descr) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		int toRet = -1;
		try {
			pst = db.prepareStatement("select id from comuni1 where lower(nome) = lower(?)");
			pst.setString(1, descr);
			rs = pst.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			try {
				rs.close();
			} catch (Exception e) {
			}
			try {
				rs.close();
			} catch (Exception e) {
			}
		}
		return toRet;
	}

	public static String getAslFromIdComune(Connection db, int idComune) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		String toRet = null;
		try {
			pst = db.prepareStatement("select codiceistatasl from comuni1 where id = ?");
			pst.setInt(1, idComune);
			rs = pst.executeQuery();
			rs.next();
			toRet = rs.getString(1);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
			}
			try {
				rs.close();
			} catch (Exception e) {
			}
		}
		return toRet;
	}

	public static String getCodeProvinciaNoPaddingFromNome(Connection db, String descr) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		String toRet = null;
		try {
			//pst = db.prepareStatement("select code from lookup_province where lower(description) = lower(?)");
			//Modifica della query per accettare il campo provincia come descrizione e sigla provincia
			pst = db.prepareStatement("select code from lookup_province where lower(description) = lower(?) or lower(cod_provincia) = lower(?)");
			pst.setString(1, descr);
			pst.setString(2, descr);
			rs = pst.executeQuery();
			rs.next();
			toRet = rs.getInt(1) + "";
		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			try {
				rs.close();
			} catch (Exception e) {
			}
			try {
				rs.close();
			} catch (Exception e) {
			}
		}
		return toRet;
	}

	// fa la traduzione usando la tabella di mapping della descrizione xsd
	// fornendo la descrizione per il db
	public static String getValorePerDbDaValoreXmlDaTabellaMappingGenerale(Connection db, String descrizione) {
		String toRet = descrizione;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try { // eccezione nel caso in cui e' un valore non presente sulla
				// tabella di mapping (potrebbe essere nel caso in cui sia
				// uguale a quello del db)
			pst = db.prepareStatement("select valore_db from tabella_mapping_descriz_to_xsd where lower(valore_xsd) = lower(?)");
			pst.setString(1, descrizione);
			rs = pst.executeQuery();
			rs.next();
			toRet = rs.getString(1);
		} catch (Exception ex) {
			// ex.printStackTrace();
			System.out.println("IL VALORE MAPPED NON ESISTE NEL DB: USO VALORE ORIGINALE");

		} finally {
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pst.close();
			} catch (Exception ex) {
			}
		}
		return toRet;

	}

	// da un id dalla tabella di lookup associata data la descrizione, passando
	// eventualmente per la tabella di mapping xsd-db delle descrizioni
	public static String ottieniIdDaDescrizioneUsandoTabellaLookup(Connection db, String nomeTabellaLookup,
			String nomeCampoId, String nomeCampoDescrizione, String descrizione, boolean tramiteTabellaMapping)

	{
		String toRet = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {

			if (tramiteTabellaMapping) {
				descrizione = getValorePerDbDaValoreXmlDaTabellaMappingGenerale(db, descrizione);
			}

			pst = db.prepareStatement("select " + nomeCampoId + " from " + nomeTabellaLookup + " where lower("
					+ nomeCampoDescrizione + ") = lower(?) ");

			pst.setString(1, descrizione);
			rs = pst.executeQuery();
			rs.next();
			toRet = rs.getString(1);
		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pst.close();
			} catch (Exception ex) {
			}
		}
		return toRet;
	}

	// ritorna idtoponimo,via,numerocivico
	public static String[] normalizzaIndirizzo(Connection db, String indirizzoDenorm) {

		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = db.prepareStatement("select * from normalizzazione_indirizzo(?)");
			pst.setString(1, indirizzoDenorm);
			rs = pst.executeQuery();
			rs.next();
			String[] toRet = new String[3];
			toRet[0] = rs.getInt(1) + "";
			toRet[1] = rs.getString(3);
			toRet[2] = rs.getInt(4) + "";
			return toRet;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pst.close();
			} catch (Exception ex) {
			}
		}
	}

	// ritorna i codici attivita col valore booleano associato che indica se
	// quella linea puo' essere specificata per il tipo di attivita passata
	public static HashMap<String, Boolean> controllaSetLineeAttivitaOnTipoAttivita(Connection db, int idTipoAttivita,
			ArrayList<String> codiciAttivita) {

		HashMap<String, Boolean> toRet = new HashMap<String, Boolean>();

		PreparedStatement pst = null;
		ResultSet rs = null;
		String query = "select * from master_list_suap_tipo_attivita "
				+ "where id_master_list_suap = ? and id_lookup_tipo_attivita = ?";

		for (String codiceAttivita : codiciAttivita) {
			Boolean isValid = false;
			try {
				pst = db.prepareStatement(query);
				pst.setInt(1, Integer.parseInt(codiceAttivita));
				pst.setInt(2, idTipoAttivita);
				rs = pst.executeQuery();
				if (rs.next()) // se trova almeno un entry per quella linea
								// produttiva associato a quel tipo_attivita
				{
					isValid = true;
				}
				pst.close();
				rs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					pst.close();
				} catch (Exception ex) {
				}
				try {
					rs.close();
				} catch (Exception ex) {
				}
			}
			toRet.put(codiceAttivita, isValid);
		}

		return toRet;

	}


	public int[] inserisciDatiScia(Connection db, HashMap<String, Object> parametriSciaComeDaXml) {
		
		PreparedStatement pst = null;
		ResultSet rs =null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try
		{
		
			String tipoOperazione = (String) parametriSciaComeDaXml.get("tipo_operazione");
			int idTipoOperazione = Integer.parseInt(ottieniIdDaDescrizioneUsandoTabellaLookup(db, "suap_lookup_tipo_richiesta", "code", "description", tipoOperazione, true));
			
			logger.info("INTERFACCIA GISA PER WS REST > *INIZIO* INSERIMENTO RICHIESTA DI TIPO (?) ".replace("?",idTipoOperazione+"")+tipoOperazione);
			
			//per prima cosa inserisco residenza rapp sede legale
			String indirizzoDenorm = (String) parametriSciaComeDaXml.get("ind_rapp_sede_legale");
			String[] temp = normalizzaIndirizzo(db,indirizzoDenorm);
			int idToponimo = -1;
			int civico = -1;
			String indirizzoNorm = "";
			
			if(temp != null)
			{
				indirizzoNorm = temp[1];
				idToponimo = Integer.parseInt(temp[0]);
				civico = Integer.parseInt(temp[2]);
			}
			
			String capResidenza = (String) parametriSciaComeDaXml.get("cap_residenza_rapp_sede_legale");
			String comuneResidenzaDescr = (String) parametriSciaComeDaXml.get("comune_residenza_rapp_sede_legale");
			int idComuneResidenza = getIdComuneFromDescr(db,comuneResidenzaDescr);
			String descrProvinciaResid = (String) parametriSciaComeDaXml.get("provincia_residenza_rapp_sede_legale");
			String idProvinciaResid = getCodeProvinciaNoPaddingFromNome(db, descrProvinciaResid);
			String descrNazioneResid = (String) parametriSciaComeDaXml.get("nazione_residenza_rapp_sede_legale");
			double lat = 0.0;
			double lon = 0.0;
			pst = db.prepareStatement("select * from public_functions.suap_richiesta_insert_indirizzo(?,?,?,?,?,?,?,?,?,?)");
			int u=0;
			
			pst.setInt(++u,idToponimo);
			pst.setString(++u,civico+"");
			pst.setString(++u, indirizzoNorm);
			pst.setString(++u, capResidenza);
			pst.setInt(++u, idComuneResidenza);
			pst.setString(++u, idProvinciaResid);
			pst.setString(++u, descrNazioneResid);
			pst.setDouble(++u, lat);
			pst.setDouble(++u, lon);
			pst.setString(++u, comuneResidenzaDescr);
			
			int idIndirizzo = -1;
			rs = pst.executeQuery();
			if(!rs.next() || (idIndirizzo = rs.getInt(1))<=0)
			{
				//vuol dire che l'inserimento e fallito
				logger.info("INTERFACCIA GISA PER WS REST > *ERRORE* NELL'INSERIMENTO INDIRIZZO SOGGETTO FISICO");
				throw new Exception();
			}
			logger.info("INTERFACCIA GISA PER WS REST > INSERITO INDIRIZZO (PER SOGG FISICO LEGALE RAPP) CON ID "+idIndirizzo);
			
			//poi inserisco soggetto fisico del rappresentante usando residenza rapp sede legale
			
			pst.close();
			rs.close();
			
			String nomeRapp = (String) parametriSciaComeDaXml.get("nome_rapp_sede_legale");
			String cognome = (String) parametriSciaComeDaXml.get("cognome_rapp_sede_legale");
			char sex = ((String) parametriSciaComeDaXml.get("sesso_rapp_sede_legale")).length() > 0 ? ((String) parametriSciaComeDaXml.get("sesso_rapp_sede_legale")).charAt(0) : 'M';
			String dataNascStr = (String) parametriSciaComeDaXml.get("data_nascita_rapp_sede_legale");
			Timestamp dataNsc = null;
			try
			{
				dataNsc = new Timestamp(sdf.parse(dataNascStr).getTime());
			}
			catch(Exception ex){}
			
			String descrComuneNasc = (String) parametriSciaComeDaXml.get("comune_nascita_rapp_sede_legale");
			String codFiscale = (String) parametriSciaComeDaXml.get("cf_rapp_sede_legale");
			
			int enteredBy = Integer.parseInt((String) parametriSciaComeDaXml.get("idUser"));
			int modifiedBy =  Integer.parseInt((String) parametriSciaComeDaXml.get("idUser"));;
			String telefono = "";
			String provNascita = null;
			String fax = "";
			String email = "";
			String documentoIdentita = "";
			
			pst = db.prepareStatement("select * from public_functions.suap_richiesta_insert_soggetto_fisico(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			u = 0;
			pst.setString(++u,cognome);
			pst.setString(++u,nomeRapp);
			pst.setTimestamp(++u, dataNsc);
			pst.setString(++u, descrComuneNasc);
			pst.setString(++u, provNascita);
			pst.setString(++u, codFiscale);
			pst.setInt(++u, idIndirizzo );
			pst.setInt(++u, enteredBy );
			pst.setInt(++u, modifiedBy );
			pst.setString(++u,(sex+"").toUpperCase());
			pst.setString(++u,telefono);
			pst.setString(++u,fax);
			pst.setString(++u,email);
			pst.setString(++u,documentoIdentita);
			
			
			int idSoggetto = -1;
			rs = pst.executeQuery();
			if(!rs.next() || (idSoggetto = rs.getInt(1))<=0){
				logger.info("INTERFACCIA GISA PER WS REST > *ERRORE* NELL'INSERIMENTO SOGGETTO FISICO");
				throw new Exception();
			}
			logger.info("INTERFACCIA GISA PER WS REST > INSERITO SOGG FISICO (LEGALE RAPP) CON ID "+idSoggetto);
		
			
			//poi inserisco indirizzo sede legale
			
			int civicoSedeLeg = -1;
			int idToponimoSedeLeg = -1;
			String indSedeLegaleNorm = "";
			String indSedeLegaleDenorm = (String) parametriSciaComeDaXml.get("ind_sede_legale");
			temp = normalizzaIndirizzo(db, indSedeLegaleDenorm);
			if(temp != null)
			{
				civicoSedeLeg = Integer.parseInt(temp[2]);
				idToponimoSedeLeg = Integer.parseInt(temp[0]);
				indSedeLegaleNorm = temp[1];
			}
			 
			String capSedeLegale = (String) parametriSciaComeDaXml.get("cap_sede_legale");
			String descrComuneSedeLeg = (String) parametriSciaComeDaXml.get("comune_sede_legale");
			 
			String provSedeLeg = (String) parametriSciaComeDaXml.get("prov_sede_legale");
			String codeProvSedeLegNoPadding = getCodeProvinciaNoPaddingFromNome(db, provSedeLeg);
			
			//se esiste l'istat legale (istat sede legale) nel file xml, allora lo uso assieme al codice provincia
			//per identificare univocamente l'id comune (nb quello nel file potrebbe essere padded, quello estratto per la provincia no)
			String istatLegale = (String) parametriSciaComeDaXml.get("istat_legale");
			int idComuneSedeLeg = -1;
			if(istatLegale != null && istatLegale.trim().length()>0)
			{
				idComuneSedeLeg = getIdComuneFromIstatComuneECodeProvincia(db, istatLegale, codeProvSedeLegNoPadding);
			}
			else
			{
				idComuneSedeLeg = getIdComuneFromDescr(db, descrComuneSedeLeg);
			}
			
			 
			
			
			String nazioneSedeLegale = null;	
			double latSedeLeg = 0.0;
			double lonSedeLeg = 0.0;
			
			pst.close();
			rs.close();
			
			pst = db.prepareStatement("select * from public_functions.suap_richiesta_insert_indirizzo(?,?,?,?,?,?,?,?,?,?)");
			u=0;
			
			pst.setInt(++u,idToponimoSedeLeg);
			pst.setString(++u,civicoSedeLeg+"");
			pst.setString(++u, indSedeLegaleNorm);
			pst.setString(++u, capSedeLegale);
			pst.setInt(++u, idComuneSedeLeg);
			pst.setString(++u, codeProvSedeLegNoPadding);
			pst.setString(++u, nazioneSedeLegale);
			pst.setDouble(++u, latSedeLeg);
			pst.setDouble(++u, lonSedeLeg);
			pst.setString(++u, descrComuneSedeLeg);
			
			int idIndSedeLegale = -1;
			rs = pst.executeQuery();
			if(!rs.next() || (idIndSedeLegale = rs.getInt(1))<=0 )
			{
				logger.info("INTERFACCIA GISA PER WS REST > *ERRORE* NELL'INSERIMENTO INDIRIZZO SEDE LEGALE");
				throw new Exception();
			}
			logger.info("INTERFACCIA GISA PER WS REST > INSERITO INDIRIZZO PER SEDE LEGALE CON ID "+idIndSedeLegale);
			
			//inserisco impresa richiesta usando sede legale e soggetto fisico
			
			String tipoOperazioneInXml = (String) parametriSciaComeDaXml.get("tipo_operazione");
//			String idTipoOperazione = XmlUtilityPerServizioRest.ottieniIdDaDescrizioneUsandoTabellaLookup(db,"suap_lookup_tipo_richiesta","code","description",tipoOperazioneInXml,true);
			
			String tipoImpresaESocietaXml = (String) parametriSciaComeDaXml.get("tipo_impresa_societa");
			String tipoImpresaSuXml = tipoImpresaESocietaXml.split("\\|")[0];
			String idtipoImpresaSuDb =ottieniIdDaDescrizioneUsandoTabellaLookup(db,"lookup_opu_tipo_impresa","code","description",tipoImpresaSuXml,false);
			String tipoSocietaSuXml = "";
			String idTipoSocieta = "-1";
			if(tipoImpresaESocietaXml.split("\\|").length > 1)
			{
				tipoSocietaSuXml = tipoImpresaESocietaXml.split("\\|")[1];
				idTipoSocieta = ottieniIdDaDescrizioneUsandoTabellaLookup(db,"lookup_opu_tipo_impresa_societa","code","description",tipoSocietaSuXml,true);
				
			}
			String ragioneSociale = (String) parametriSciaComeDaXml.get("ragione_sociale");
			String pIva = (String) parametriSciaComeDaXml.get("partita_iva");
			String codFiscaleImpres = (String) parametriSciaComeDaXml.get("cf_impresa");
			String domicilioDigitale = (String) parametriSciaComeDaXml.get("domicilio_digitale");
			
			int enteredByPerImpresa = Integer.parseInt((String) parametriSciaComeDaXml.get("idUser"));
			int modifiedByPerImpresa = Integer.parseInt((String) parametriSciaComeDaXml.get("idUser"));
			
			pst.close();
			rs.close();
			pst = db.prepareStatement("select * from public_functions.suap_richiesta_insert_impresa(?,?,?,?,?,?,?,?,?,?,?)");
			u = 0;
			pst.setInt(++u, /*Integer.parseInt(*/idTipoOperazione/*)*/);
			pst.setInt(++u, Integer.parseInt(idtipoImpresaSuDb));
			pst.setInt(++u, Integer.parseInt(idTipoSocieta));
			pst.setString(++u, ragioneSociale);
			pst.setString(++u, codFiscaleImpres);
			pst.setString(++u, pIva);
			pst.setInt(++u, enteredByPerImpresa);
			pst.setInt(++u, modifiedByPerImpresa);
			pst.setString(++u, domicilioDigitale);	
			pst.setInt(++u, idIndSedeLegale);
			pst.setInt(++u, idSoggetto);
			
			int idImpresa = -1;
			rs = pst.executeQuery();
			
			if(!rs.next() || (idImpresa = rs.getInt(1))<=0 )
			{
				logger.info("INTERFACCIA GISA PER WS REST > *ERRORE* NELL'INSERIMENTO IMPRESA");
				throw new Exception();
			}
			logger.info("INTERFACCIA GISA PER WS REST > INSERITA IMPRESA CON ID "+idImpresa);
			 
			
			
			
			//inserisco indirizzo stabilimento
			
			int idToponimoStab = -1;
			int civicoStab = -1;
			String indStabNorm = "";
			String indStabDenorm = (String) parametriSciaComeDaXml.get("ind_stab");
			temp = normalizzaIndirizzo(db, indStabDenorm);
			if(temp != null)
			{
				indStabNorm = temp[1];
				idToponimoStab = Integer.parseInt(temp[0]);
				civicoStab = Integer.parseInt(temp[2]);
			}
			
			String nazioneStab = null;
			String capStab = (String) parametriSciaComeDaXml.get("cap_stab");
			String descrComuneStab = (String) parametriSciaComeDaXml.get("comune_stab");
			
			
			String provSedeStab = (String) parametriSciaComeDaXml.get("prov_stab");
			String codeProvSedeStabNoPadding = getCodeProvinciaNoPaddingFromNome(db, provSedeStab);
			int idComuneStab = -1;
			String istatOperativo = (String) parametriSciaComeDaXml.get("istat_operativo");
			
			if(istatOperativo != null && istatOperativo.trim().length()>0)
			{
				idComuneStab = getIdComuneFromIstatComuneECodeProvincia(db, istatOperativo, codeProvSedeStabNoPadding);
			}
			else
			{
				idComuneStab = getIdComuneFromDescr(db, descrComuneStab);
			}
			
 
			
			double latStab = Double.parseDouble((String) parametriSciaComeDaXml.get("latitudine"));
			double lonStab = Double.parseDouble((String) parametriSciaComeDaXml.get("longitudine"));
			
			pst.close();
			rs.close();
			
			pst = db.prepareStatement("select * from public_functions.suap_richiesta_insert_indirizzo(?,?,?,?,?,?,?,?,?,?)");
			u=0;
			
			pst.setInt(++u,idToponimoStab);
			pst.setString(++u,civicoStab+"");
			pst.setString(++u, indStabNorm);
			pst.setString(++u, capStab);
			pst.setInt(++u, idComuneStab);
			pst.setString(++u, codeProvSedeStabNoPadding);
			pst.setString(++u, nazioneStab);
			pst.setDouble(++u, latStab);
			pst.setDouble(++u, lonStab);
			pst.setString(++u, descrComuneStab);
			
			int idIndStab = -1;
			
			rs = pst.executeQuery();
			if(!rs.next() || (idIndStab = rs.getInt(1)) <= 0)
			{
				logger.info("INTERFACCIA GISA PER WS REST > *ERRORE* NELL'INSERIMENTO INDIRIZZO STABILIMENTO");
				throw new Exception();
			}
			logger.info("INTERFACCIA GISA PER WS REST > INSERITO INDIRIZZO PER STABILIMENTO ID "+idIndStab);
		 
			
			//Inserisco dati sullo stabilimento (unico per richiesta xml)
			
			
			String carattereDescr = (String) parametriSciaComeDaXml.get("carattere");
			int idCarattere = Integer.parseInt(ottieniIdDaDescrizioneUsandoTabellaLookup(db, "opu_lookup_tipologia_carattere", "code", "description", carattereDescr, true));
			String tipoAttivitaDescr = (String) parametriSciaComeDaXml.get("tipo_attivita");
			int idTipoAttivita = Integer.parseInt(ottieniIdDaDescrizioneUsandoTabellaLookup(db, "opu_lookup_tipologia_attivita", "code", "description", tipoAttivitaDescr, true));
			boolean cessazioneGlobale = Boolean.parseBoolean((String) parametriSciaComeDaXml.get("isOperazioneGlobale"));
			boolean sospensioneGlobale = false; //NB: LA SOSPENSIONE NON E' MAI EFFETTUATA TRAMITE IL SERVIZIO REST (QUINDI QUESTO TAG CHE INDICA SE E' GLOBALE O MENO, NON HA REALE UTILIZZO) 
			String telefonoStab = "";
			String partitaIvaVariazione = (String) parametriSciaComeDaXml.get("partita_iva_variazione") == null ? "" : (String) parametriSciaComeDaXml.get("partita_iva_variazione"); //se si tratta di variazione registrazione, questo sicuramente e stato fornito nell'xml
			Date today = new Date();
			Timestamp dataRicezioneRichiesta = new Timestamp(today.getTime());
			String numeroRegistrazioneVariazione = (String) parametriSciaComeDaXml.get("numero_registrazione_variazione") == null ? "" : (String) parametriSciaComeDaXml.get("numero_registrazione_variazione");
			String dataInizioAttivitaStr = (String) parametriSciaComeDaXml.get("data_inizio_attivita");
			Timestamp dataInizAttTmst = null;
			try
			{
				dataInizAttTmst = new Timestamp(sdf.parse(dataInizioAttivitaStr).getTime());
			}
			catch(Exception ex)
			{
				
			}
			
			String dataFineAttivitaStr = (String) parametriSciaComeDaXml.get("data_fine_attivita");
			Timestamp dataFineAttTmst = null;
			try
			{
				dataFineAttTmst = new Timestamp(sdf.parse(dataFineAttivitaStr).getTime());
			}
			catch(Exception ex){}
			
			
			String domicilioDigitaleStab = "";
			Timestamp dataInizioSospensione = null;
			int enteredByPerStab = -1;
			int modifiedByPerStab = -1;
			
			
			/*
			 * Il codice ASL E' PRESO DA:
			 * -comune se e' presente lo stabilimento
			 * -comune sede legale se e' attivita' mobile (perche' non esiste stab)
			 * -comune rapp legale se e' attivita' mobile e impresa individuale (che non ha sede legale)
			 *
			 */
			String codiceAslStab = null;
			
			if(idTipoAttivita == IDENTIFICATIVI_DB.ATT_MOBILE.getData()) //mobile
			{
				if(Integer.parseInt(idtipoImpresaSuDb) == IDENTIFICATIVI_DB.IMPRESA_INDIVIDUALE.getData())
				{
					//il codice asl e' quello del rapp legale
					codiceAslStab = getAslFromIdComune(db,idComuneResidenza);
				}
				else
				{
					//il codice asl e' quello della sede legale
					codiceAslStab = getAslFromIdComune(db,idComuneSedeLeg);
				}
			}
			else
			{	//altrimenti e' quello dello stabilimento
				 codiceAslStab = getAslFromIdComune(db, idComuneStab);
			}
 		
			
			
			 
			
			int tipoRegRic = IDENTIFICATIVI_DB.TIPO_REG_RIC_REGISTRABILI.getData(); //SEMPRE REGISTRABILI
			int categoriaQuattroTipo = IDENTIFICATIVI_DB.CATEGORIA_INSERIMENTO_CAT4.getData(); //SEMPRE CATEGORIA 4
			
			
			
			pst.close();
			rs.close();
 	 
			pst = db.prepareStatement("select * from public_functions.suap_richiesta_insert_stabilimento(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
			
			u = 0;
			 
			pst.setInt(++u, idCarattere);
			pst.setInt(++u, idTipoAttivita);
			pst.setBoolean(++u, cessazioneGlobale);
			pst.setBoolean(++u, sospensioneGlobale );
			pst.setString(++u, telefonoStab);
			pst.setString(++u, partitaIvaVariazione);
			pst.setTimestamp(++u, dataRicezioneRichiesta);
			pst.setString(++u, numeroRegistrazioneVariazione);
			pst.setTimestamp(++u, dataInizAttTmst);
			pst.setTimestamp(++u, dataFineAttTmst );
			pst.setTimestamp(++u, dataInizioSospensione);
			pst.setInt(++u, Integer.parseInt(codiceAslStab)); 
			pst.setInt(++u, enteredByPerStab);
			pst.setInt(++u, modifiedByPerStab);
			pst.setInt(++u, idImpresa);
			pst.setInt(++u, idIndStab);
			pst.setInt(++u,tipoRegRic);
			pst.setInt(++u,categoriaQuattroTipo);
			 
			
			rs = pst.executeQuery();
			int idStab = -1;
			if(!rs.next() || (idStab = rs.getInt(1)) <= 0)
			{
				logger.info("INTERFACCIA GISA PER WS REST > *ERRORE* NELL'INSERIMENTO STABILIMENTO");
				throw new Exception();
			}
			
			logger.info("INTERFACCIA GISA PER WS REST > INSERITO STABILIMENTO CON ID "+idStab);
		
			
			//inserisco linee
			
			pst.close();
			rs.close();
			

			String query = "INSERT INTO suap_ric_scia_relazione_stabilimento_linee_produttive "
					+" (id_stabilimento,id_linea_produttiva,data_inizio,data_fine,stato,modified,modifiedby,enabled)"
					+" values(?,?,?,?,0,current_timestamp,?,true)";
			
			String[] lineeToIns = (String[]) parametriSciaComeDaXml.get("lineeToIns");
			if(lineeToIns != null) //allo stato attuale possono essere null quando si tratta di operazioni che non prevedono l'obbligo sull'inserimento delle linee
			{
			
				for(String idLinea : lineeToIns)
				{
					 
					
						u = 0;
						
						pst = db.prepareStatement(query);
						
						//pst = db.prepareStatement("select * from public_functions.suap_richiesta_insert_linea_produttiva(?,?,?,?,?)");
						
						pst.setInt(++u,idStab);
						pst.setInt(++u,Integer.parseInt(idLinea));
						pst.setTimestamp(++u,dataInizAttTmst);
						pst.setTimestamp(++u,dataFineAttTmst);
						pst.setInt(++u,Integer.parseInt((String) parametriSciaComeDaXml.get("idUser")));
						//--METTERE GESTIONE E VALORE DI RITORNO RISULTATO DBI INSERIMENTO LINEA QUI
						
						//pst.executeQuery();
						int ret = pst.executeUpdate();
						if(ret != 1)
						{
							logger.info("INTERFACCIA GISA PER WS REST > *ERRORE* NELL'INSERIMENTO LINEA ATTIVITA");
							throw new Exception();
						}
						else
						{
							logger.info("INTERFACCIA GISA PER WS REST > INSERITA LINEA ATTIVITA CON ID "+ idLinea );
						}
						
						pst.close();
						rs.close();
					 
				}
				
			}
			
			
			
			
			//se si trattava di apicoltura INOLTRE...
			if(tipoAttivitaDescr.equalsIgnoreCase("apicoltura"))
			{
				logger.info("INTERFACCIA GISA PER WS REST > SI TRATTA DI TIPO DI ATTIVITA \"APICOLTURA\". CHIAMATA ALLA DBI \"suap_insert_attivita_apicoltura_da_richiesta\"" );
				pst.close();
				rs.close();
				try //la grabbo qui localmente poiche questa e un'operazione che non annulla l'inserimento della richiesta (anche se comunque la richiesta inserita sara non valida)
				{
					pst= db.prepareStatement("select * from public_functions.suap_insert_attivita_apicoltura_da_richiesta( "
							+ "?, ?)");
					pst.setInt(1, idImpresa);
					pst.setInt(2, Integer.parseInt((String) parametriSciaComeDaXml.get("idUser")));
					rs = pst.executeQuery();
					rs.next();
					if(rs.getBoolean(1) == false)
					{
						logger.info("INTERFACCIA GISA PER WS REST > LA CHIAMATA ALLA DBI \"suap_insert_attivita_apicoltura_da_richiesta\" NON AVUTO ESITO POSITIVO" );
					}
					else
					{
						logger.info("INTERFACCIA GISA PER WS REST > LA CHIAMATA ALLA DBI \"suap_insert_attivita_apicoltura_da_richiesta\" HA AVUTO ESITO POSITIVO" );
					}
				}
				catch(Exception ex)
				{	ex.printStackTrace();
					logger.info("INTERFACCIA GISA PER WS REST > LA CHIAMATA ALLA DBI \"suap_insert_attivita_apicoltura_da_richiesta\" NON AVUTO ESITO POSITIVO" );
				}
				
			}
			
			//l'id stab associato alla scia (quello ritornato dalla dbi) e diverso da quello usato dal documentale
			Stabilimento stab0 = new Stabilimento( );
			stab0.queryRecordStabilimentoIdOperatore(db, idImpresa);
			
			return new int[]{idImpresa,idStab,stab0.getAltId()};
			 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.info("INTERFACCIA GISA PER WS REST > TENTATIVO DI INSERIMENTO RICHIESTA *FALLITO*");
			return new int[]{-1,-1,-1};
		}
		finally
		{
			try{rs.close();}catch(Exception ex){}
			try{pst.close();}catch(Exception ex){}
		}
		
		
	}


	 

}
