/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.izs.apicoltura.apianagraficaattivita.ws;

import it.izs.apicoltura.apianagraficaazienda.ws.Apiazienda;
import it.izs.apicoltura.apianagraficaazienda.ws.UpdateApiAzienda;
import it.izs.apicoltura.apianagraficaazienda.ws.UpdateApiAziendaResponse;
import it.izs.bdn.action.utilsXML;
import it.izs.bdn.anagrafica.ws.WsApiAnagraficaPersoneBdn;
import it.izs.ws.WsPost;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.aspcfs.modules.gestoriacquenew.base.EccezioneDati;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.GestoreConnessioni;

import com.darkhorseventures.framework.actions.ActionContext;
import com.sun.xml.internal.ws.fault.ServerSOAPFaultException;

import ext.aspcfs.modules.apiari.base.Operatore;
import ext.aspcfs.modules.apiari.base.Stabilimento;
import ext.aspcfs.modules.apiari.base.WSBdnLog;

public class WsApiAnagraficaApi {
	
	  private final static Logger log = Logger.getLogger(it.izs.apicoltura.apianagraficaattivita.ws.WsApiAnagraficaApi.class);

	ApiEndpoint endPointAnagraficaApi = null ;
	SOAPAutenticazioneWS autenticazione = null ;
	
	public WsApiAnagraficaApi()
	{
		ApiEndpointService service = new ApiEndpointService();
		endPointAnagraficaApi = service.getApiEndpointPort();
		autenticazione = new SOAPAutenticazioneWS();
		autenticazione.setUsername(ApplicationProperties.getProperty("USERNAME_BDAPI"));
		autenticazione.setPassword(ApplicationProperties.getProperty("PASSWORD_BDAPI"));
		autenticazione.setRuoloCodice(ApplicationProperties.getProperty("RUOLO_CODICE_BDAPI"));
		autenticazione.setRuoloValoreCodice(ApplicationProperties.getProperty("RUOLO_VALORE_BDAPI"));
		
	}
	
	public List<Api> search(Stabilimento attivitaApicoltura,Connection db,int idUtente) throws BusinessWsException_Exception 
	{
		log.info("##WS.API.ANAGRAFICA.VARIAZIONE.APIARIO## CHIAMATO SERVIZIO");

		List<Api> risposta = new ArrayList<Api>();
		try
		{
		WSBdnLog logWS = new WSBdnLog();
		logWS.setIdUtente(idUtente);
		logWS.setIdOggetto(attivitaApicoltura.getIdStabilimento());
		logWS.setNomeTabella("apicoltura_apiari");
		logWS.setTipoOperazione(WSBdnLog.TYPE_WS_INSERT_API);
		logWS.setNomerServizio("insertApiario");
		Api api = new Api();
		
		
		if(attivitaApicoltura.getProgressivoBDA()!=null && !"".equalsIgnoreCase(attivitaApicoltura.getProgressivoBDA()))
			api.setProgressivo(Integer.parseInt(attivitaApicoltura.getProgressivoBDA()));
		
		
		api.setApiattAziendaCodice(attivitaApicoltura.getOperatore().getCodiceAzienda());
		api.setNumAlveari(attivitaApicoltura.getNumAlveari());
		api.setNumSciami(attivitaApicoltura.getNumSciami());
		if (attivitaApicoltura.getDetentore()!=null)
			api.setDetenIdFiscale(attivitaApicoltura.getDetentore().getCodFiscale());
		
		if(attivitaApicoltura.getSedeOperativa()!=null)
		{
		api.setComIstat(attivitaApicoltura.getSedeOperativa().getCodiceIstatComune());
		api.setComProSigla(attivitaApicoltura.getSedeOperativa().getSiglaProvincia());
		api.setCap(attivitaApicoltura.getSedeOperativa().getCap());
		api.setIndirizzo(attivitaApicoltura.getSedeOperativa().getVia());
		api.setLocalita(attivitaApicoltura.getSedeOperativa().getVia());
		api.setComDescrizione(attivitaApicoltura.getSedeOperativa().getDescrizioneComune());
		}
		else
		{
			api.setComDescrizione(attivitaApicoltura.getSedeOperativa().getDescrizioneComune());
			
		}
		

		
		
		SearchApi apiario = new SearchApi();
		apiario.setApiarioSearchTO(api);
		
		logWS.setParametri(api);

		try {
			logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));
			SearchApiResponse response =  endPointAnagraficaApi.searchApi(apiario, autenticazione);
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("OK");
			logWS.insertBdnLogWs();
			
			
			 risposta =  response.getApiarioTO();
			return risposta ;
			

		} catch (BusinessWsException_Exception e) {
			
			String errore ="";
			if (e instanceof BusinessWsException_Exception)
			{
				BusinessWsException_Exception ee = (BusinessWsException_Exception)e;
			 errore = ee.getFaultInfo().getMessage()+" : "+ee.getFaultInfo().getResult().getErrore();
			
			for (FieldError error : ee.getFaultInfo().getResult().getErrors())
			{
				errore +="["+error.getField()+": "+error.getMessage()+ "]" ;
	
			}
			}
			
			logWS.setNoteEsito(errore);

			
			log.info("##WS.API.ANAGRAFICA.VARIAZIONE.APIARIO## CHIAMATO SERVIZIO KO");
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			//logWS.insertBdnLogWs();
			logWS.insertBdnLogWsErrore();
			
			throw e ;
			
		}
		catch (ServerSOAPFaultException e) {
			// TODO Auto-generated catch block
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			logWS.setNoteEsito("ERRORE : "+e.getMessage());
			//logWS.insertBdnLogWs();
			logWS.insertBdnLogWsErrore();
			
			log.info("##WS.API.ANAGRAFICA.VARIAZIONE.APIARIO## CHIAMATO SERVIZIO KO");
			throw e ;
		
			
		}

		}catch(Exception e)
		{
			throw e;
		}
	


	}
	
	
	
	public Api searchByPk(Stabilimento attivitaApicoltura,Connection db,int idUtente) throws BusinessWsException_Exception 
	{
		log.info("##WS.API.ANAGRAFICA.VARIAZIONE.APIARIO## CHIAMATO SERVIZIO");

		Api risposta = new Api();
		try
		{
		WSBdnLog logWS = new WSBdnLog();
		logWS.setIdUtente(idUtente);
		logWS.setIdOggetto(attivitaApicoltura.getIdStabilimento());
		logWS.setNomeTabella("apicoltura_apiari");
		logWS.setTipoOperazione(WSBdnLog.TYPE_WS_INSERT_API);
		logWS.setNomerServizio("insertApiario");
		Api api = new Api();
		api.setApiId(attivitaApicoltura.getIdBda());
		
		SearchApiByPk apiario = new SearchApiByPk();
		apiario.setApiId(api.getApiId());
		
		logWS.setParametri(api);

		try {
			logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));
			SearchApiByPkResponse response =  endPointAnagraficaApi.searchApiByPk(apiario, autenticazione);
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("OK");
			logWS.insertBdnLogWs();
			
			
			 risposta =  response.getApiarioTO();
			return risposta ;
			

		} 
		catch (ServerSOAPFaultException e) {
			// TODO Auto-generated catch block
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			logWS.setNoteEsito("ERRORE : "+e.getMessage());
			//logWS.insertBdnLogWs();
			logWS.insertBdnLogWsErrore();
			
			log.info("##WS.API.ANAGRAFICA.VARIAZIONE.APIARIO## CHIAMATO SERVIZIO KO");
			throw e ;
		
			
		}

		}catch(Exception e)
		{
			throw e;
		}
	


	}
	
	
	
	
	
	
	
	
	
	
	public List<Api> searchApiariFuoriRegione() throws BusinessWsException_Exception 
	{
		log.info("##WS.API.ANAGRAFICA.VARIAZIONE.APIARIO## CHIAMATO SERVIZIO");

		List<Api> listaApiariFr = new ArrayList<Api>();
		try
		{	
			ElencoApiariApicoltoriFuoriRegione param = new ElencoApiariApicoltoriFuoriRegione();
			ElencoApiariApicoltoriFuoriRegioneResponse toResult =  endPointAnagraficaApi.elencoApiariApicoltoriFuoriRegione(param, autenticazione);
			listaApiariFr = toResult.getApiarioTO();
			
			
			
			
		}catch(Exception e)
		{
			throw e;
		}
		
		return listaApiariFr ;
	


	}
	
	public Api insertApiAnagraficaApiario(Stabilimento attivitaApicoltura,Connection db,int idUtente, ActionContext context) throws SQLException, EccezioneDati 
	{
		/*log.info("##WS.API.ANAGRAFICA.VARIAZIONE.APIARIO## CHIAMATO SERVIZIO");

		Api api = new Api();
		Api risposta =  null;
		
		try
		{
		WSBdnLog logWS = new WSBdnLog();
		logWS.setIdUtente(idUtente);
		logWS.setIdOggetto(attivitaApicoltura.getIdStabilimento());
		logWS.setNomeTabella("apicoltura_apiari");
		logWS.setTipoOperazione(WSBdnLog.TYPE_WS_INSERT_API);
		logWS.setNomerServizio("insertApiario");
		
		
		api.setProgressivo(Integer.parseInt(attivitaApicoltura.getProgressivoBDA()));
		
		
		api.setApiattAziendaCodice(attivitaApicoltura.getOperatore().getCodiceAzienda());
		api.setNumAlveari(attivitaApicoltura.getNumAlveari());
		api.setNumSciami(attivitaApicoltura.getNumSciami());
		api.setDetenIdFiscale(attivitaApicoltura.getDetentore().getCodFiscale());
		
		
		api.setComIstat(attivitaApicoltura.getSedeOperativa().getCodiceIstatComune());
		api.setComProSigla(attivitaApicoltura.getSedeOperativa().getSiglaProvincia());
		api.setCap(attivitaApicoltura.getSedeOperativa().getCap());
		api.setIndirizzo(attivitaApicoltura.getSedeOperativa().getVia());
		api.setLocalita(attivitaApicoltura.getSedeOperativa().getVia());
		api.setApiAslCodice(attivitaApicoltura.getAslRomaBdn());
		
		
		
		
		
		
		NumberFormat formattatore = NumberFormat.getNumberInstance(Locale.CANADA); // cultura predefinita

		// imposto il formattatore con le specifiche desiderate
		formattatore.setMaximumFractionDigits(6); // max 6 cifre decimali dopo la virgola
		
		api.setLatitudine(Double.parseDouble(formattatore.format(attivitaApicoltura.getSedeOperativa().getLatitudine())));
		api.setLongitudine(Double.parseDouble(formattatore.format(attivitaApicoltura.getSedeOperativa().getLongitudine())));
		
		
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(attivitaApicoltura.getDataApertura());
		XMLGregorianCalendar dtApertura=null;
		try {
			
			dtApertura = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		api.setDtApertura(dtApertura);
		
		
		try {
			PreparedStatement pst = db.prepareStatement("select codice_bdn from  apicoltura_lookup_classificazione where code = ? ");

			pst.setInt(1, attivitaApicoltura.getIdApicolturaClassificazione());
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				api.setClassificazione(rs.getString(1));
			

			
			
			pst = db.prepareStatement("select codice_bdn from  apicoltura_lookup_sottospecie where code = ? ");
			pst.setInt(1, attivitaApicoltura.getIdApicolturaSottospecie());
			rs = pst.executeQuery();
			if (rs.next())
				api.setApisotspeCodice(rs.getString(1));
			
			pst = db.prepareStatement("select codice_bdn from  apicoltura_lookup_modalita where code = ? ");
			pst.setInt(1, attivitaApicoltura.getIdApicolturaModalita());
			rs = pst.executeQuery();
			if (rs.next())
				api.setApimodallCodice(rs.getString(1));
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		
		
		InsertApiario apiario = new InsertApiario();
		apiario.setApiarioTO(api);
		
		logWS.setParametri(api);

		try {
			logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));
			InsertApiarioResponse response =  endPointAnagraficaApi.insertApiario(apiario, autenticazione);
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("OK");
			logWS.insertBdnLogWs();
			
			
			risposta =  response.getApiarioTO();
			
			log.info("##WS.API.ANAGRAFICA.VARIAZIONE.APIARIO## CHIAMATO SERVIZIO OK");

		} catch (BusinessWsException_Exception e) {
			
			String errore ="";
			if (e instanceof BusinessWsException_Exception)
			{
				BusinessWsException_Exception ee = (BusinessWsException_Exception)e;
			 errore = ee.getFaultInfo().getMessage()+" : "+ee.getFaultInfo().getResult().getErrore();
			
			for (FieldError error : ee.getFaultInfo().getResult().getErrors())
			{
				errore +="["+error.getField()+": "+error.getMessage()+ "]" ;
	
			}
			}
			
			logWS.setNoteEsito(errore);

			
			log.info("##WS.API.ANAGRAFICA.VARIAZIONE.APIARIO## CHIAMATO SERVIZIO KO");
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			//logWS.insertBdnLogWs();
			logWS.insertBdnLogWsErrore();
			
			throw e ;
			
		}
		catch (ServerSOAPFaultException e) {
			// TODO Auto-generated catch block
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			logWS.setNoteEsito("ERRORE : "+e.getMessage());
			//logWS.insertBdnLogWs();
			logWS.insertBdnLogWsErrore();
			
			log.info("##WS.API.ANAGRAFICA.VARIAZIONE.APIARIO## CHIAMATO SERVIZIO KO");
			throw e ;
		
			
		}

		}catch(Exception e)
		{
			throw e;
		}
	
		return risposta;*/
		
		
		
		NumberFormat formattatore = NumberFormat.getNumberInstance(Locale.CANADA); // cultura predefinita

		// imposto il formattatore con le specifiche desiderate
		formattatore.setMaximumFractionDigits(6); // max 6 cifre decimali dopo la virgola
		
		WsPost wsPost = new WsPost(db, WsPost.ENDPOINT_API,WsPost.AZIONE_INSERT);
		
		HashMap<String, Object> campiInput = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		campiInput.put("apiattAziendaCodice",attivitaApicoltura.getOperatore().getCodiceAzienda());
		campiInput.put("progressivo",Integer.parseInt(attivitaApicoltura.getProgressivoBDA()));
		campiInput.put("detenIdFiscale",attivitaApicoltura.getDetentore().getCodFiscale());
		campiInput.put("numAlveari",attivitaApicoltura.getNumAlveari());
		campiInput.put("numSciami",attivitaApicoltura.getNumSciami());
		campiInput.put("comIstat",attivitaApicoltura.getSedeOperativa().getCodiceIstatComune());
		campiInput.put("comProSigla",attivitaApicoltura.getSedeOperativa().getSiglaProvincia());
		campiInput.put("localita",attivitaApicoltura.getSedeOperativa().getVia());
		campiInput.put("apiAslCodice",attivitaApicoltura.getAslRomaBdn());
		campiInput.put("indirizzo",attivitaApicoltura.getSedeOperativa().getVia());
		campiInput.put("latitudine",Double.parseDouble(formattatore.format(attivitaApicoltura.getSedeOperativa().getLatitudine())));
		campiInput.put("longitudine",Double.parseDouble(formattatore.format(attivitaApicoltura.getSedeOperativa().getLongitudine())));
		campiInput.put("cap",attivitaApicoltura.getSedeOperativa().getCap());
		campiInput.put("dtApertura",sdf.format(attivitaApicoltura.getDataApertura())+"T00:00:00.0");
				
				
		PreparedStatement pst = db.prepareStatement("select codice_bdn from  apicoltura_lookup_classificazione where code = ? ");

		pst.setInt(1, attivitaApicoltura.getIdApicolturaClassificazione());
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			campiInput.put("classificazione",rs.getString(1));
		
		pst = db.prepareStatement("select codice_bdn from  apicoltura_lookup_sottospecie where code = ? ");
		pst.setInt(1, attivitaApicoltura.getIdApicolturaSottospecie());
		rs = pst.executeQuery();
		if (rs.next())
			campiInput.put("apisotspeCodice",rs.getString(1));
		
		pst = db.prepareStatement("select codice_bdn from  apicoltura_lookup_modalita where code = ? ");
		pst.setInt(1, attivitaApicoltura.getIdApicolturaModalita());
		rs = pst.executeQuery();
		if (rs.next())
			campiInput.put("apimodallCodice",rs.getString(1));
		
		
		wsPost.setCampiInput(campiInput);
		wsPost.costruisciEnvelope(db);
		String response = wsPost.post(db, idUtente);
		String idApiario = utilsXML.getValoreNodoXML(response,"apiId");
		
		if(idApiario==null || idApiario.equals(""))
		{
			System.out.println("Errore nell'invio in bdn dell'apiario");
			String messaggio = utilsXML.getValoreNodoXML(response,"faultstring");
			throw new EccezioneDati("Errore nell'invio in bdn: " + ((messaggio!=null)?(messaggio):("")) );
		}
		else
		{
			Api apiario = new Api();
			apiario.setApiId(Integer.parseInt(idApiario));
			return apiario;
		}
		
	}
	
	public String modificaModalitaAllevamento(Stabilimento attivitaApicoltura,Connection db,int idUtente) throws BusinessWsException_Exception 
	{
		log.info("##WS.API.ANAGRAFICA.VARIAZIONE.APIARIO## CHIAMATO SERVIZIO");

		
		try
		{
		WSBdnLog logWS = new WSBdnLog();
		logWS.setIdUtente(idUtente);
		logWS.setIdOggetto(attivitaApicoltura.getIdStabilimento());
		logWS.setNomeTabella("apicoltura_apiari");
		logWS.setTipoOperazione(WSBdnLog.TYPE_WS_INSERT_API);
		logWS.setNomerServizio("updateApiario");
		Api api = searchByPk(attivitaApicoltura, db, idUtente);
		
		
		
		
		
		
		try {
			PreparedStatement pst = db.prepareStatement("select codice_bdn from  apicoltura_lookup_modalita where code = ? ");
			pst.setInt(1, attivitaApicoltura.getIdApicolturaModalita());
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				api.setApimodallCodice(rs.getString(1));
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return e1.getMessage();
		}
		
		
		UpdateApiario apiario = new UpdateApiario();
		apiario.setApiarioTO(api);
		
		logWS.setParametri(api);

		try {
			logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));
			UpdateApiarioResponse response =  endPointAnagraficaApi.updateApiario(apiario, autenticazione);
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("OK");
			logWS.insertBdnLogWs();
			
			
			log.info("##WS.API.ANAGRAFICA.VARIAZIONE.APIARIO## CHIAMATO SERVIZIO OK");
		} catch (BusinessWsException_Exception e) {
			
			String errore ="";
			if (e instanceof BusinessWsException_Exception)
			{
				BusinessWsException_Exception ee = (BusinessWsException_Exception)e;
			 errore = ee.getFaultInfo().getMessage()+" : "+ee.getFaultInfo().getResult().getErrore();
			
			for (FieldError error : ee.getFaultInfo().getResult().getErrors())
			{
				errore +="["+error.getField()+": "+error.getMessage()+ "]" ;
	
			}
			}
			
			logWS.setNoteEsito(errore);

			
			log.info("##WS.API.ANAGRAFICA.VARIAZIONE.APIARIO## CHIAMATO SERVIZIO KO");
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			//logWS.insertBdnLogWs();
			logWS.insertBdnLogWsErrore();
			
			return errore;
			
		}
		catch (ServerSOAPFaultException e) {
			// TODO Auto-generated catch block
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			logWS.setNoteEsito("ERRORE : "+e.getMessage());
			//logWS.insertBdnLogWs();
			logWS.insertBdnLogWsErrore();
			
			log.info("##WS.API.ANAGRAFICA.VARIAZIONE.APIARIO## CHIAMATO SERVIZIO KO");
			return e.getMessage();
		
			
		}

		}catch(Exception e)
		{
			return e.getMessage();
		}
		return "OK";
	


	}
	
	public String modificaApiAnagraficaApiario(Stabilimento apiarioInput,Connection db,int idUtente) throws Exception 
	{
		log.info("##WS.API.ANAGRAFICA.VARIAZIONE.APIARIO## CHIAMATO SERVIZIO");
		
		WSBdnLog logWS = new WSBdnLog();
		logWS.setIdUtente(idUtente);
		logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));
		
		
		Api api = new Api();
		api.setApiId(apiarioInput.getIdBda());
		api.setProgressivo(Integer.parseInt(apiarioInput.getProgressivoBDA()));
		api.setApiattAziendaCodice(apiarioInput.getOperatore().getCodiceAzienda());
		api.setNumAlveari(apiarioInput.getNumAlveari());
		api.setNumSciami(apiarioInput.getNumSciami());
		api.setDetenIdFiscale(apiarioInput.getDetentore().getCodFiscale());
		api.setComIstat(apiarioInput.getSedeOperativa().getCodiceIstatComune());
		api.setComProSigla(apiarioInput.getSedeOperativa().getSiglaProvincia());
		api.setCap(apiarioInput.getSedeOperativa().getCap());
		api.setIndirizzo(apiarioInput.getSedeOperativa().getVia());
		api.setLocalita(apiarioInput.getSedeOperativa().getVia());
		
		NumberFormat formattatore = NumberFormat.getNumberInstance(Locale.CANADA); // cultura predefinita

		// imposto il formattatore con le specifiche desiderate
		formattatore.setMaximumFractionDigits(6); // max 6 cifre decimali dopo la virgola
		
		api.setLatitudine(Double.parseDouble(formattatore.format(apiarioInput.getSedeOperativa().getLatitudine())));
		api.setLongitudine(Double.parseDouble(formattatore.format(apiarioInput.getSedeOperativa().getLongitudine())));
		
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(apiarioInput.getDataApertura());
		GregorianCalendar cChiusura = new GregorianCalendar();
		if (apiarioInput.getDataChiusura()!=null)
			cChiusura.setTime(apiarioInput.getDataChiusura());
		XMLGregorianCalendar dtApertura=null;
		XMLGregorianCalendar dtChiusura=null;
		dtApertura = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		dtChiusura = DatatypeFactory.newInstance().newXMLGregorianCalendar(cChiusura);
		
		api.setDtApertura(dtApertura);
		api.setDtChiusura(dtChiusura);
		
		PreparedStatement pst = db.prepareStatement("select codice_bdn from  apicoltura_lookup_classificazione where code = ? ");

		pst.setInt(1, apiarioInput.getIdApicolturaClassificazione());
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			api.setClassificazione(rs.getString(1));
			
		pst = db.prepareStatement("select codice_bdn from  apicoltura_lookup_sottospecie where code = ? ");
		pst.setInt(1, apiarioInput.getIdApicolturaSottospecie());
		rs = pst.executeQuery();
		if (rs.next())
			api.setApisotspeCodice(rs.getString(1));
			
		pst = db.prepareStatement("select codice_bdn from  apicoltura_lookup_modalita where code = ? ");
		pst.setInt(1, apiarioInput.getIdApicolturaModalita());
		rs = pst.executeQuery();
		if (rs.next())
			api.setApimodallCodice(rs.getString(1));
		
		try 
		{
			UpdateApiario apiario = new UpdateApiario();
			apiario.setApiarioTO(api);
			
			logWS.setIdOggetto(apiarioInput.getIdApiario());
			logWS.setNomeTabella("apicoltura_apiari");
			logWS.setTipoOperazione(WSBdnLog.TYPE_WS_UPDATE_APIARIO);
			logWS.setNomerServizio("updateApiario");
			logWS.setParametri(apiario);
			UpdateApiarioResponse response =  endPointAnagraficaApi.updateApiario(apiario, autenticazione);
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("OK");
			logWS.setNoteEsito(response.getApiarioTO().toString());
			logWS.insertBdnLogWs();
			
			if (apiarioInput.getDataChiusura()!=null)
			{
				pst = db.prepareStatement("update apicoltura_apiari set data_cessazione = ?, modified_by = ? where  id = ? ");
				pst.setTimestamp(1,apiarioInput.getDataChiusura() );
				pst.setInt(2,apiarioInput.getModifiedBy() );
				pst.setInt(3, apiarioInput.getIdStabilimento());
				pst.execute();
			}
			log.info("##WS.API.ANAGRAFICA.VARIAZIONE.APIARIO## CHIAMATO SERVIZIO OK");
			return "OK";
		} 
		catch (BusinessWsException_Exception e) 
		{
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			String errore ="";
			
			errore = e.getFaultInfo().getMessage()+" : "+e.getFaultInfo().getResult().getErrore();
			
			for (it.izs.apicoltura.apianagraficaattivita.ws.FieldError error : e.getFaultInfo().getResult().getErrors())
			{
				errore +="["+error.getField()+": "+error.getMessage()+ "]" ;
	
			}
			
			logWS.setNoteEsito(errore);
			logWS.insertBdnLogWs();
			return errore;
		}
	}
	
	
	public String cessazioneApiAnagraficaApiario(Stabilimento attivitaApicoltura,Connection db,int idUtente) throws BusinessWsException_Exception 
	{
		log.info("##WS.API.ANAGRAFICA.VARIAZIONE.APIARIO## CHIAMATO SERVIZIO");

		
		try
		{
		WSBdnLog logWS = new WSBdnLog();
		logWS.setIdUtente(idUtente);
		logWS.setIdOggetto(attivitaApicoltura.getIdStabilimento());
		logWS.setNomeTabella("apicoltura_apiari");
		logWS.setTipoOperazione(WSBdnLog.TYPE_WS_INSERT_API);
		logWS.setNomerServizio("updateApiario");
		Stabilimento attivitaApicolturaToSerch = new Stabilimento();
		attivitaApicolturaToSerch.setIdBda(attivitaApicoltura.getIdBda());
		attivitaApicolturaToSerch.setIdStabilimento(attivitaApicoltura.getIdStabilimento());
		Api api = searchByPk(attivitaApicolturaToSerch, db, idUtente);
		if(api==null)
			return "Nessun apiario trovato in BDN";
		
		NumberFormat formattatore = NumberFormat.getNumberInstance(Locale.CANADA); // cultura predefinita

		// imposto il formattatore con le specifiche desiderate
		formattatore.setMaximumFractionDigits(6); // max 6 cifre decimali dopo la virgola
		
		GregorianCalendar cChiusura = new GregorianCalendar();
		if (attivitaApicoltura.getDataChiusura()!=null)
			cChiusura.setTime(attivitaApicoltura.getDataChiusura());
		XMLGregorianCalendar dtChiusura=null;
		try {
			
			dtChiusura = DatatypeFactory.newInstance().newXMLGregorianCalendar(cChiusura);
		} catch (DatatypeConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		api.setDtChiusura(dtChiusura);
		
		UpdateApiario apiario = new UpdateApiario();
		apiario.setApiarioTO(api);
		
		logWS.setParametri(api);

		try {
			logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));
			UpdateApiarioResponse response =  endPointAnagraficaApi.updateApiario(apiario, autenticazione);
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("OK");
			logWS.insertBdnLogWs();
			
			
			try {
				if (attivitaApicoltura.getDataChiusura()!=null){
				PreparedStatement pst = db.prepareStatement("update apicoltura_apiari set stato = 3, data_cessazione = ?, modified_by = ? where  id = ? ");
				pst.setTimestamp(1,attivitaApicoltura.getDataChiusura() );
				pst.setInt(2,attivitaApicoltura.getModifiedBy() );
				pst.setInt(3, attivitaApicoltura.getIdStabilimento());
				pst.execute();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return e.getMessage();
			}
			
			log.info("##WS.API.ANAGRAFICA.VARIAZIONE.APIARIO## CHIAMATO SERVIZIO OK");
		} catch (BusinessWsException_Exception e) {
			
			String errore ="";
			if (e instanceof BusinessWsException_Exception)
			{
				BusinessWsException_Exception ee = (BusinessWsException_Exception)e;
			 errore = ee.getFaultInfo().getMessage()+" : "+ee.getFaultInfo().getResult().getErrore();
			
			for (FieldError error : ee.getFaultInfo().getResult().getErrors())
			{
				errore +="["+error.getField()+": "+error.getMessage()+ "]" ;
	
			}
			}
			
			logWS.setNoteEsito(errore);

			
			log.info("##WS.API.ANAGRAFICA.VARIAZIONE.APIARIO## CHIAMATO SERVIZIO KO");
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			//logWS.insertBdnLogWs();
			logWS.insertBdnLogWsErrore();
			
			return errore;
			
		}
		catch (ServerSOAPFaultException e) {
			// TODO Auto-generated catch block
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			logWS.setNoteEsito("ERRORE : "+e.getMessage());
			//logWS.insertBdnLogWs();
			logWS.insertBdnLogWsErrore();
			
			log.info("##WS.API.ANAGRAFICA.VARIAZIONE.APIARIO## CHIAMATO SERVIZIO KO");
			return e.getMessage();
		
			
		}

		}catch(Exception e)
		{
			return e.getMessage();
		}
		return "OK";
	


	}
	
	
	public Api delete(Stabilimento attivitaApicoltura,Connection db,int idUtente) throws BusinessWsException_Exception 
	{
		log.info("##WS.API.ANAGRAFICA.DELETE.APIARIO## CHIAMATO SERVIZIO");

		Api risposta = new Api();
		try
		{
		WSBdnLog logWS = new WSBdnLog();
		logWS.setIdUtente(idUtente);
		logWS.setIdOggetto(attivitaApicoltura.getIdStabilimento());
		logWS.setNomeTabella("apicoltura_apiari");
		logWS.setTipoOperazione(WSBdnLog.TYPE_WS_DELETE_API);
		logWS.setNomerServizio("deleteApiario");
		
		Api api = new Api();
				
		if(attivitaApicoltura.getProgressivoBDA()!=null && !"".equalsIgnoreCase(attivitaApicoltura.getProgressivoBDA()))
			api.setProgressivo(Integer.parseInt(attivitaApicoltura.getProgressivoBDA()));
				
		api.setApiattAziendaCodice(attivitaApicoltura.getOperatore().getCodiceAzienda());
		api.setNumAlveari(attivitaApicoltura.getNumAlveari());
		api.setNumSciami(attivitaApicoltura.getNumSciami());
		if (attivitaApicoltura.getDetentore()!=null)
			api.setDetenIdFiscale(attivitaApicoltura.getDetentore().getCodFiscale());
		
		if(attivitaApicoltura.getSedeOperativa()!=null)
		{
		api.setComIstat(attivitaApicoltura.getSedeOperativa().getCodiceIstatComune());
		api.setComProSigla(attivitaApicoltura.getSedeOperativa().getSiglaProvincia());
		api.setCap(attivitaApicoltura.getSedeOperativa().getCap());
		api.setIndirizzo(attivitaApicoltura.getSedeOperativa().getVia());
		api.setLocalita(attivitaApicoltura.getSedeOperativa().getVia());
		api.setComDescrizione(attivitaApicoltura.getSedeOperativa().getDescrizioneComune());
		}
		else
		{
			api.setComDescrizione(attivitaApicoltura.getSedeOperativa().getDescrizioneComune());
			
		}
		api.setApiId(attivitaApicoltura.getIdBda());
		DeleteApiario apiario = new DeleteApiario();
		apiario.setApiarioTO(api);
		
		logWS.setParametri(api);

		try {
			logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));
			DeleteApiarioResponse response =  endPointAnagraficaApi.deleteApiario(apiario, autenticazione);
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("OK");
			logWS.insertBdnLogWs();
			
			risposta =  response.getApiarioTO();
			return risposta ;
			

		} catch (BusinessWsException_Exception e) {
			
			String errore ="";
			if (e instanceof BusinessWsException_Exception)
			{
				BusinessWsException_Exception ee = (BusinessWsException_Exception)e;
			 errore = ee.getFaultInfo().getMessage()+" : "+ee.getFaultInfo().getResult().getErrore();
			
			for (FieldError error : ee.getFaultInfo().getResult().getErrors())
			{
				errore +="["+error.getField()+": "+error.getMessage()+ "]" ;
	
			}
			}
			
			logWS.setNoteEsito(errore);

			
			log.info("##WS.API.ANAGRAFICA.DELETE.APIARIO## CHIAMATO SERVIZIO KO");
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			//logWS.insertBdnLogWs();
			logWS.insertBdnLogWsErrore();
			
			throw e ;
			
		}
		catch (ServerSOAPFaultException e) {
			// TODO Auto-generated catch block
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			logWS.setNoteEsito("ERRORE : "+e.getMessage());
			//logWS.insertBdnLogWs();
			logWS.insertBdnLogWsErrore();
			
			log.info("##WS.API.ANAGRAFICA.DELETE.APIARIO## CHIAMATO SERVIZIO KO");
			throw e ;
		
			
		}

		}catch(Exception e)
		{
			throw e;
		}
	
	}

	
	
}
