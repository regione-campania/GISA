/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.izs.apicoltura.apianagraficaattivita.ws;

import it.izs.bdn.anagrafica.ws.WsApiAnagraficaPersoneBdn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.aspcfs.modules.util.imports.ApplicationProperties;

import com.sun.xml.internal.ws.fault.ServerSOAPFaultException;

import ext.aspcfs.modules.apiari.base.Operatore;
import ext.aspcfs.modules.apiari.base.Stabilimento;
import ext.aspcfs.modules.apiari.base.StabilimentoVariazioneCensimento;
import ext.aspcfs.modules.apiari.base.WSBdnLog;

public class WsApiAnagraficaVariazioniCen {
	
	  private final static Logger log = Logger.getLogger(it.izs.apicoltura.apianagraficaattivita.ws.WsApiAnagraficaVariazioniCen.class);

	ApicenEndpoint endPointCensimenti = null ;
	SOAPAutenticazioneWS autenticazione = null ;
	
	public WsApiAnagraficaVariazioniCen()
	{
		ApicenEndpointService service = new ApicenEndpointService();
		endPointCensimenti = service.getApicenEndpointPort();
		autenticazione = new SOAPAutenticazioneWS();
		autenticazione.setUsername(ApplicationProperties.getProperty("USERNAME_BDAPI"));
		autenticazione.setPassword(ApplicationProperties.getProperty("PASSWORD_BDAPI"));
		autenticazione.setRuoloCodice(ApplicationProperties.getProperty("RUOLO_CODICE_BDAPI"));
		autenticazione.setRuoloValoreCodice(ApplicationProperties.getProperty("RUOLO_VALORE_BDAPI"));
		

		
	}
	
	public Apicen insertApiAnagraficaCensimento(Stabilimento attivitaApicoltura,Connection db,int idUtente) throws Exception
	{

		log.info("##WS.API.ANAGRAFICA.VARIAZIONE.CENSIMENTO## CHIAMATO SERVIZIO");

		WSBdnLog logWS = new WSBdnLog();
		logWS.setIdUtente(idUtente);
		logWS.setIdOggetto(attivitaApicoltura.getIdStabilimento());
		logWS.setNomeTabella("apicoltura_apiari");
		logWS.setTipoOperazione(WSBdnLog.TYPE_WS_INSERT_VAR_CEN);
		logWS.setIdVariazione(attivitaApicoltura.getIdVariazioneEseguita());
		logWS.setTabellaVariazione("apicoltura_consistenzai");
		logWS.setNomerServizio("insertApiCensimento");
		
		Apicen variazioneCensimenti = new Apicen();
		variazioneCensimenti.setApiApiattAziendaCodice(attivitaApicoltura.getOperatore().getCodiceAzienda());
		variazioneCensimenti.setApiProgressivo(Integer.parseInt(attivitaApicoltura.getProgressivoBDA()));
		variazioneCensimenti.setNumAlveari(attivitaApicoltura.getNumAlveari());
		variazioneCensimenti.setNumSciami(attivitaApicoltura.getNumSciami());

		if (attivitaApicoltura.getData_assegnazione_censimento()!=null)
		{
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(attivitaApicoltura.getData_assegnazione_censimento());
			XMLGregorianCalendar dtApertura=null;
			try 
			{
				dtApertura = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			} 
			catch (DatatypeConfigurationException e1) 
			{
				logWS.setEsito("KO");
				logWS.setNoteEsito("ERRORE : "+e1.getMessage());
				logWS.insertBdnLogWs();
			}
			variazioneCensimenti.setDtCensimento(dtApertura);
		}
		
		InsertApiCensimento censimento = new InsertApiCensimento();
		censimento.setApiCensimentoTO(variazioneCensimenti);

		try 
		{
			logWS.setParametri(variazioneCensimenti);
			logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));
			InsertApiCensimentoResponse response =  endPointCensimenti.insertApiCensimento(censimento, autenticazione);
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("OK");
			logWS.insertBdnLogWs();
			
			response.getApiCensimentoTO();
			log.info("##WS.API.ANAGRAFICA.VARIAZIONE.CENSIMENTO## CHIAMATO SERVIZIO OK");
			
			return response.getApiCensimentoTO();

		} 
		catch (BusinessWsException_Exception e) 
		{
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
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
			
			logWS.insertBdnLogWs();
			
			log.info("##WS.API.ANAGRAFICA.VARIAZIONE.CENSIMENTO## CHIAMATO SERVIZIO KO");

			throw new Exception(e);
			
		}
		catch (ServerSOAPFaultException e) {
			// TODO Auto-generated catch block
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			logWS.setNoteEsito("ERRORE : "+e.getMessage());
			logWS.insertBdnLogWs();
			
			log.info("##WS.API.ANAGRAFICA.VARIAZIONE.CENSIMENTO## CHIAMATO SERVIZIO KO");

			throw e;
			
		}
	}
	
	
	
	public void updateApiAnagraficaCensimento(Stabilimento attivitaApicoltura,Connection db,int idUtente)
	{

		log.info("##WS.API.ANAGRAFICA.VARIAZIONE.CENSIMENTO## CHIAMATO SERVIZIO");

		WSBdnLog logWS = new WSBdnLog();
		logWS.setIdUtente(idUtente);
		logWS.setIdOggetto(attivitaApicoltura.getIdStabilimento());
		logWS.setNomeTabella("apicoltura_apiari");
		logWS.setTipoOperazione(WSBdnLog.TYPE_WS_INSERT_VAR_CEN);
		logWS.setIdVariazione(attivitaApicoltura.getIdVariazioneEseguita());
		logWS.setTabellaVariazione("apicoltura_consistenzai");
		logWS.setNomerServizio("updateApiCensimento");
		
		Apicen variazioneCensimenti = new Apicen();
		variazioneCensimenti.setApiApiattAziendaCodice(attivitaApicoltura.getOperatore().getCodiceAzienda());
		variazioneCensimenti.setApiProgressivo(Integer.parseInt(attivitaApicoltura.getProgressivoBDA()));
		variazioneCensimenti.setNumAlveari(attivitaApicoltura.getNumAlveari());
		variazioneCensimenti.setNumSciami(attivitaApicoltura.getNumSciami());

		if (attivitaApicoltura.getData_assegnazione_censimento()!=null)
		{
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(attivitaApicoltura.getData_assegnazione_censimento());
		XMLGregorianCalendar dtApertura=null;
		try {
			
			dtApertura = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e1) {
			// TODO Auto-generated catch block
			logWS.setEsito("KO");
			logWS.setNoteEsito("ERRORE : "+e1.getMessage());
			logWS.insertBdnLogWs();
		}
		variazioneCensimenti.setDtCensimento(dtApertura);
		}
		variazioneCensimenti.setApicenId(attivitaApicoltura.getIdVariazioneEseguita());
		
		UpdateApiCensimento censimento = new UpdateApiCensimento();
		censimento.setApiCensimentoTO(variazioneCensimenti);

		try {
			logWS.setParametri(variazioneCensimenti);
			logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));
			UpdateApiCensimentoResponse response =  endPointCensimenti.updateApiCensimento(censimento, autenticazione);
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("OK");
			logWS.insertBdnLogWs();
			
			 response.getApiCensimentoTO();
			log.info("##WS.API.ANAGRAFICA.VARIAZIONE.CENSIMENTO## CHIAMATO SERVIZIO OK");

		} catch (BusinessWsException_Exception e) {
			// TODO Auto-generated catch block
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
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
			
			logWS.insertBdnLogWs();
			
			log.info("##WS.API.ANAGRAFICA.VARIAZIONE.CENSIMENTO## CHIAMATO SERVIZIO KO");

		
			
		}
		catch (ServerSOAPFaultException e) {
			// TODO Auto-generated catch block
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			logWS.setNoteEsito("ERRORE : "+e.getMessage());
			logWS.insertBdnLogWs();
			
			log.info("##WS.API.ANAGRAFICA.VARIAZIONE.CENSIMENTO## CHIAMATO SERVIZIO KO");

		
			
		}


	
		
	}
	
	
	
	public String deleteApiAnagraficaCensimento(StabilimentoVariazioneCensimento censimento,Connection db,int idUtente) throws DatatypeConfigurationException
	{

		log.info("##WS.API.ANAGRAFICA.VARIAZIONE.CENSIMENTO## CHIAMATO SERVIZIO");

		WSBdnLog logWS = new WSBdnLog();
		logWS.setIdUtente(idUtente);
		logWS.setIdOggetto(censimento.getId());
		logWS.setNomeTabella("apicoltura_apiari");
		logWS.setTipoOperazione(WSBdnLog.TYPE_WS_INSERT_VAR_CEN);
		logWS.setTabellaVariazione("apicoltura_consistenzai");
		logWS.setNomerServizio("deleteApiCensimento");
		
		Apicen risposta = new Apicen();
			
		SearchApiCensimentoByPk cen = new SearchApiCensimentoByPk();
		cen.setApicenId(censimento.getIdBda());
		SearchApiCensimentoByPkResponse responseCenByPk =  endPointCensimenti.searchApiCensimentoByPk(cen, autenticazione);
		risposta =  responseCenByPk.getApiCensimentoTO();
		
		DeleteApiCensimento delCensimento = new DeleteApiCensimento();
		delCensimento.setApiCensimentoTO(risposta);

		try {
			logWS.setParametri(risposta);
			logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));
			DeleteApiCensimentoResponse response =  endPointCensimenti.deleteApiCensimento(delCensimento, autenticazione);
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("OK");
			logWS.insertBdnLogWs();
			
			 response.getApiCensimentoTO();
			log.info("##WS.API.ANAGRAFICA.VARIAZIONE.CENSIMENTO## CHIAMATO SERVIZIO OK");
			return "OK";

		} catch (BusinessWsException_Exception e) {
			// TODO Auto-generated catch block
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
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
			
			logWS.insertBdnLogWs();
			
			log.info("##WS.API.ANAGRAFICA.VARIAZIONE.CENSIMENTO## CHIAMATO SERVIZIO KO");
			
			return errore;
		
			
		}
		catch (ServerSOAPFaultException e) {
			// TODO Auto-generated catch block
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			logWS.setNoteEsito("ERRORE : "+e.getMessage());
			logWS.insertBdnLogWs();
			
			log.info("##WS.API.ANAGRAFICA.VARIAZIONE.CENSIMENTO## CHIAMATO SERVIZIO KO");
			return e.getMessage();
		
			
		}
	}
	
	public List<Apicen> search(String codiceAzienda, int progressivoApiario, Timestamp dataCensimento, String flagCensUfficiale, Connection db) throws Exception 
	{
		log.info("##WS.API.ANAGRAFICA.RICERCA.CENSIMENTO## CHIAMATO SERVIZIO");

		XMLGregorianCalendar dtCensimento=null;
		GregorianCalendar c = new GregorianCalendar();
		
		List<Apicen> risposta = new ArrayList<Apicen>();
		try
		{
			Apicen apiCen = new Apicen();
		
			c.setTime(dataCensimento);
			dtCensimento = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			apiCen.setDtCensimento(dtCensimento);
			apiCen.setApiProgressivo(progressivoApiario);
			apiCen.setApiApiattAziendaCodice(codiceAzienda);
			apiCen.setFlagCensUfficiale(flagCensUfficiale);
		
			SearchApiCensimento censimento = new SearchApiCensimento();
			censimento.setApiCensimentoSearchTO(apiCen);
		
			SearchApiCensimentoResponse response =  endPointCensimenti.searchApiCensimento(censimento, autenticazione);
			
			risposta =  response.getApiCensimentoTO();
			return risposta ;
			

		} 
		catch (BusinessWsException_Exception e) 
		{
			
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
			

			
			log.info("##WS.API.ANAGRAFICA.VARIAZIONE.APIARIO## CHIAMATO SERVIZIO KO");
			throw e ;
			
		}
		catch (ServerSOAPFaultException e) 
		{
			log.info("##WS.API.ANAGRAFICA.VARIAZIONE.APIARIO## CHIAMATO SERVIZIO KO");
			throw e ;
		}

		catch(Exception e)
		{
			throw e;
		}
	}

}
