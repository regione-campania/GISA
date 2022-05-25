/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.izs.apicoltura.apianagraficaazienda.ws;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.messaging.Endpoint;
import javax.xml.namespace.QName;

import org.aspcfs.modules.util.imports.ApplicationProperties;

import jdk.nashorn.internal.runtime.ListAdapter;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

import ext.aspcfs.modules.apiari.base.Operatore;
import ext.aspcfs.modules.apiari.base.WSBdnLog;
import it.izs.apicoltura.apianagraficaattivita.ws.Apiatt;
import it.izs.apicoltura.apianagraficaattivita.ws.BusinessWsException_Exception;
import it.izs.apicoltura.apianagraficaattivita.ws.FieldError;
import it.izs.apicoltura.apianagraficaattivita.ws.UpdateApiAttivita;
import it.izs.apicoltura.apianagraficaattivita.ws.UpdateApiAttivitaResponse;
import it.izs.sinsa.services.Prelievi;

public class WsApiAnagraficaAziende {

	private ApiaziendaEndpoint serviceAnagraficaAziende =null ;
	SOAPAutenticazioneWS autenticazione = null ;


	public WsApiAnagraficaAziende()
	{
		ApiaziendaEndpointService service = new ApiaziendaEndpointService();

		serviceAnagraficaAziende = service.getApiaziendaEndpointPort();
		autenticazione = new SOAPAutenticazioneWS();
		autenticazione.setUsername(ApplicationProperties.getProperty("USERNAME_BDAPI"));
		autenticazione.setPassword(ApplicationProperties.getProperty("PASSWORD_BDAPI"));
		autenticazione.setRuoloCodice(ApplicationProperties.getProperty("RUOLO_CODICE_BDAPI"));
		autenticazione.setRuoloValoreCodice(ApplicationProperties.getProperty("RUOLO_VALORE_BDAPI"));
	}

	public List<Apiazienda> getCodiceDisponibile(Connection db,String comuneIstat,String provinciaSigla,String suffisso,int idUtente)
	{

		WSBdnLog logWS = new WSBdnLog();
		logWS.setIdUtente(idUtente);
		logWS.setIdOggetto(-1);
		logWS.setNomeTabella("");
		logWS.setTipoOperazione(WSBdnLog.TYPE_WS_SEARCH_AZIENDA);

		Apiazienda azienda = new Apiazienda();

		/**
		 * Settare i filtri
		 */
		List<Apiazienda> listaAziende = new ArrayList<Apiazienda>();

		ElencoCodiciDisponibili elencocodici = new ElencoCodiciDisponibili();
		elencocodici.setComIstat(comuneIstat);
		elencocodici.setComProSigla(provinciaSigla);
		elencocodici.setSuffisso(suffisso);
		
		try {
			logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));

			ElencoCodiciDisponibiliResponse response = serviceAnagraficaAziende.elencoCodiciDisponibili(elencocodici, autenticazione);
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("OK");
			logWS.setParametri(elencocodici);
			logWS.insertBdnLogWs();
			listaAziende = response.getApiAziendaTO();

		} catch (it.izs.apicoltura.apianagraficaazienda.ws.BusinessWsException_Exception e) {

			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			logWS.setNoteEsito("ERRORE: "+e.getLocalizedMessage()+ " INFO : "+e.getFaultInfo().getMessage());
			logWS.insertBdnLogWs();
		}
		return listaAziende;

	}


	public List<Apiazienda> searchCodiceEsistente(String comuneIstat,String provinciaSigla,Connection db,int idutente,String cfProprietario)
	{

		WSBdnLog logWS = new WSBdnLog();
		logWS.setIdUtente(idutente);
		logWS.setIdOggetto(-1);
		logWS.setNomeTabella("");
		logWS.setTipoOperazione(WSBdnLog.TYPE_WS_SEARCH_AZIENDA);

		Apiazienda azienda = new Apiazienda();

		/**
		 * Settare i filtri
		 */
		List<Apiazienda> listaAziende = new ArrayList<Apiazienda>();

		ElencoCodiciAssegnati elencocodici = new ElencoCodiciAssegnati();
		elencocodici.setComIstat(comuneIstat);
		elencocodici.setComProSigla(provinciaSigla);
		elencocodici.setIdFiscale(cfProprietario.toUpperCase());

		try {
			logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));

			ElencoCodiciAssegnatiResponse response = serviceAnagraficaAziende.elencoCodiciAssegnati(elencocodici, autenticazione);
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("OK");
			logWS.setParametri(elencocodici);
			logWS.insertBdnLogWs();
			listaAziende = response.getApiAziendaTO();

		} catch (it.izs.apicoltura.apianagraficaazienda.ws.BusinessWsException_Exception e) {

			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			String errore ="";
			
			 errore = e.getFaultInfo().getMessage()+" : "+e.getFaultInfo().getResult().getErrore();
			
			for (it.izs.apicoltura.apianagraficaazienda.ws.FieldError error : e.getFaultInfo().getResult().getErrors())
			{
				errore +="["+error.getField()+": "+error.getMessage()+ "]" ;
	
			}
			
			
			logWS.setNoteEsito(errore);
			
			
			logWS.insertBdnLogWs();
		}
		return listaAziende;

	}
	
	
	public void liberaCodice(String codice,int idUtente,Connection db) throws it.izs.apicoltura.apianagraficaazienda.ws.BusinessWsException_Exception
	{

		
		WSBdnLog logWS = new WSBdnLog();
		logWS.setIdUtente(idUtente);
		logWS.setIdOggetto(-1);
		logWS.setNomeTabella("");
		logWS.setTipoOperazione(WSBdnLog.TYPE_WS_SEARCH_AZIENDA);
		
		SearchApiAzienda searchApi = new SearchApiAzienda();
		Apiazienda azienda = new Apiazienda();

		
		azienda.setCodice(codice);
		try {
			logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));

			
			SearchApiAzienda search = new SearchApiAzienda();
			search.setAziendaSearchTO(azienda);
			
			SearchApiAziendaResponse responce = serviceAnagraficaAziende.searchApiAzienda(search, autenticazione);
			
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("OK");
			logWS.setParametri(azienda);
			logWS.insertBdnLogWs();
			
			if (responce.getApiAziendaTO()!=null)
			{
				DeleteApiAzienda del = new DeleteApiAzienda();
				
				if (responce.getApiAziendaTO().size()>0)
				{
				del.setApiAziendaTO(responce.getApiAziendaTO().get(0));
				
				serviceAnagraficaAziende.deleteApiAzienda(del, autenticazione);
				}
				
				WSBdnLog logWS1 = new WSBdnLog();
				logWS1.setIdUtente(idUtente);
				logWS1.setIdOggetto(-1);
				logWS1.setNomeTabella("");
				logWS1.setTipoOperazione(WSBdnLog.TYPE_WS_DELETE_AZIENDA);
				logWS1.setDataInvio(new Timestamp(System.currentTimeMillis()));
				logWS1.setDataRisposta(new Timestamp(System.currentTimeMillis()));
				logWS1.setEsito("OK");
				logWS1.setNomerServizio("deleteApiAzienda");
				logWS1.setParametri(azienda);
				logWS1.insertBdnLogWs();
				
				
			}
			
			
		

		} catch (it.izs.apicoltura.apianagraficaazienda.ws.BusinessWsException_Exception e) {

			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			String errore ="";
			
			 errore = e.getFaultInfo().getMessage()+" : "+e.getFaultInfo().getResult().getErrore();
			
			for (it.izs.apicoltura.apianagraficaazienda.ws.FieldError error : e.getFaultInfo().getResult().getErrors())
			{
				errore +="["+error.getField()+": "+error.getMessage()+ "]" ;
	
			}
			
			
			logWS.setNoteEsito(errore);
			logWS.insertBdnLogWs();
			throw e ;
			
		}
	}

	public Apiazienda search(Operatore op,Connection db,int idutente) throws it.izs.apicoltura.apianagraficaazienda.ws.BusinessWsException_Exception
	{

		WSBdnLog logWS = new WSBdnLog();
		logWS.setIdUtente(idutente);
		logWS.setIdOggetto(-1);
		logWS.setNomeTabella("");
		logWS.setTipoOperazione(WSBdnLog.TYPE_WS_SEARCH_AZIENDA);
		Apiazienda toRet = null;
		Apiazienda azienda = new Apiazienda();

		/**
		 * Settare i filtri
		 */
		azienda.setCodice(op.getCodiceAzienda());
		azienda.setComIstat(op.getSedeLegale().getCodiceIstatComune());
		azienda.setComProSigla(op.getSedeLegale().getSiglaProvincia());
	
//		azienda.setAslCodice(op.getSedeLegale().getCodiceAsl());		
//		
		SearchApiAzienda search = new SearchApiAzienda();
		search.setAziendaSearchTO(azienda);
		List<Apiazienda> listaAziende = new ArrayList<Apiazienda>();
		try {
			logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));

			SearchApiAziendaResponse responce = serviceAnagraficaAziende.searchApiAzienda(search, autenticazione);
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("OK");
			logWS.setParametri(azienda);
			logWS.insertBdnLogWs();
			listaAziende = responce.getApiAziendaTO();
			if (listaAziende.size()>0)
				toRet = listaAziende.get(0);

		} catch (it.izs.apicoltura.apianagraficaazienda.ws.BusinessWsException_Exception e) {

			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			String errore ="";
			
			 errore = e.getFaultInfo().getMessage()+" : "+e.getFaultInfo().getResult().getErrore();
			
			for (it.izs.apicoltura.apianagraficaazienda.ws.FieldError error : e.getFaultInfo().getResult().getErrors())
			{
				errore +="["+error.getField()+": "+error.getMessage()+ "]" ;
	
			}
			
			
			logWS.setNoteEsito(errore);
			logWS.insertBdnLogWs();
			
		}
		return toRet;

	}


	public Apiazienda insert(Operatore op,Connection db,int idutente) throws it.izs.apicoltura.apianagraficaazienda.ws.BusinessWsException_Exception
	{

		WSBdnLog logWS = new WSBdnLog();
		logWS.setIdUtente(idutente);
		logWS.setIdOggetto(-1);
		logWS.setNomeTabella("");
		logWS.setTipoOperazione(WSBdnLog.TYPE_WS_SEARCH_AZIENDA);
		Apiazienda aziendaResp = null;
		logWS.setNomerServizio("insertAzienda");
		Apiazienda azienda = new Apiazienda();

		/**
		 * Settare i filtri
		 */
		azienda.setComIstat(op.getSedeLegale().getCodiceIstatComune());
		azienda.setComProSigla(op.getSedeLegale().getSiglaProvincia());
		azienda.setCodice(op.getCodiceAzienda() );
		try {

			GregorianCalendar c = new GregorianCalendar();
			
			c.setTime(op.getDataInizio());
			XMLGregorianCalendar dtApertura = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

			azienda.setDtApertura(dtApertura);								

		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		azienda.setAslCodice(op.getSedeLegale().getCodiceAsl());
		aziendaResp = search(op, db, idutente);
		if (aziendaResp!=null)
			return aziendaResp;
		
		
		InsertApiAzienda insertAzienda = new InsertApiAzienda();
		insertAzienda.setApiAziendaTO(azienda);

		logWS.setParametri(azienda);
		try {
			logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));

			InsertApiAziendaResponse responce = serviceAnagraficaAziende.insertApiAzienda(insertAzienda, autenticazione);

			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("OK");
			logWS.insertBdnLogWs();
			aziendaResp = responce.getApiAziendaTO();
			

		} catch (it.izs.apicoltura.apianagraficaazienda.ws.BusinessWsException_Exception e) {

			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			String errore ="";
			
			 errore = e.getFaultInfo().getMessage()+" : "+e.getFaultInfo().getResult().getErrore();
			
			for (it.izs.apicoltura.apianagraficaazienda.ws.FieldError error : e.getFaultInfo().getResult().getErrors())
			{
				errore +="["+error.getField()+": "+error.getMessage()+ "]" ;
	
			}
			
			
			logWS.setNoteEsito(errore);
			
			logWS.insertBdnLogWs();
			throw e ;
		}
		return aziendaResp;

	}
	
	
	
	
	
	
	public String update(Operatore op,Connection db,int idutente) throws it.izs.apicoltura.apianagraficaazienda.ws.BusinessWsException_Exception
	{

		WSBdnLog logWS = new WSBdnLog();
		logWS.setIdUtente(idutente);
		logWS.setIdOggetto(-1);
		logWS.setNomeTabella("");
		logWS.setTipoOperazione(WSBdnLog.TYPE_WS_SEARCH_AZIENDA);
		Apiazienda aziendaResp = null;
		logWS.setNomerServizio("updateAzienda");
		Apiazienda azienda = new Apiazienda();

		/**
		 * Settare i filtri
		 */
		azienda.setComIstat(op.getSedeLegale().getCodiceIstatComune());
		azienda.setComProSigla(op.getSedeLegale().getSiglaProvincia());
		azienda.setCodice(op.getCodiceAzienda() );
		azienda.setAziendaId(op.getIdBda());
		try {

			GregorianCalendar c = new GregorianCalendar();
			GregorianCalendar cFine = new GregorianCalendar();
			
			c.setTime(op.getDataInizio());
			cFine.setTime(op.getDataChiusura());
			XMLGregorianCalendar dtApertura = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			XMLGregorianCalendar dtChiusura = DatatypeFactory.newInstance().newXMLGregorianCalendar(cFine);

			azienda.setDtApertura(dtApertura);	
			azienda.setDtChiusura(dtChiusura);	

		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		azienda.setAslCodice(op.getSedeLegale().getCodiceAsl());
		aziendaResp = search(op, db, idutente);
		
		
		UpdateApiAzienda updateAzienda = new UpdateApiAzienda();
		updateAzienda.setApiAziendaTO(azienda);

		logWS.setParametri(azienda);
		try {
			logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));

			UpdateApiAziendaResponse responce = serviceAnagraficaAziende.updateApiAzienda(updateAzienda, autenticazione);

			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("OK");
			logWS.insertBdnLogWs();
			aziendaResp = responce.getApiAziendaTO();
			

		} catch (it.izs.apicoltura.apianagraficaazienda.ws.BusinessWsException_Exception e) {

			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			String errore ="";
			
			 errore = e.getFaultInfo().getMessage()+" : "+e.getFaultInfo().getResult().getErrore();
			
			for (it.izs.apicoltura.apianagraficaazienda.ws.FieldError error : e.getFaultInfo().getResult().getErrors())
			{
				errore +="["+error.getField()+": "+error.getMessage()+ "]" ;
	
			}
			
			
			logWS.setNoteEsito(errore);
			
			logWS.insertBdnLogWs();
			return errore;
		}
		return "OK";

	}
	
	public String cessa(Operatore op,Connection db,int idutente) throws Exception
	{
		WSBdnLog logWS = new WSBdnLog();
		logWS.setIdUtente(idutente);
		logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));
		
		Apiazienda az = new Apiazienda();
		
		try
		{
			az = search(op, db, idutente);
			if(az==null)
				return "Azienda non presente in bdn";
			
			GregorianCalendar cFine = new GregorianCalendar();
			cFine.setTime(op.getDataChiusura());
			XMLGregorianCalendar dtChiusura = DatatypeFactory.newInstance().newXMLGregorianCalendar(cFine);
			az.setDtChiusura(dtChiusura);	
	
			UpdateApiAzienda updateAzienda = new UpdateApiAzienda();
			updateAzienda.setApiAziendaTO(az);
	
			logWS.setIdOggetto(op.getIdOperatore());
			logWS.setNomeTabella("apicoltura_imprese");
			logWS.setTipoOperazione(WSBdnLog.TYPE_WS_UPDATE_AZIENDA);
			logWS.setNomerServizio("updateApiAzienda");
			logWS.setParametri(az);
			logWS.setNomeTabella("apicoltura_imprese");
			UpdateApiAziendaResponse responce = serviceAnagraficaAziende.updateApiAzienda(updateAzienda, autenticazione);
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("OK");
			logWS.setNoteEsito(responce.getApiAziendaTO().toString());
			logWS.insertBdnLogWs();
			return "OK";
		}
		catch (it.izs.apicoltura.apianagraficaazienda.ws.BusinessWsException_Exception e) 
		{
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			String errore ="";
			
			errore = e.getFaultInfo().getMessage()+" : "+e.getFaultInfo().getResult().getErrore();
			
			for (it.izs.apicoltura.apianagraficaazienda.ws.FieldError error : e.getFaultInfo().getResult().getErrors())
			{
				errore +="["+error.getField()+": "+error.getMessage()+ "]" ;
	
			}
			
			logWS.setNoteEsito(errore);
			logWS.insertBdnLogWs();
			return errore;
		}

	}

	
	public static void main(String[] args) {



		//		Apiazienda aziendaTo = new Apiazienda();
		//
		//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//		aziendaTo.setCodice("050AV207");
		//
		//
		//		try {
		//
		//			GregorianCalendar c = new GregorianCalendar();
		//			c.setTime(sdf.parse("2005-01-01"));
		//			XMLGregorianCalendar dtApertura = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		//
		//			aziendaTo.setDtApertura(dtApertura);								
		//
		//		} catch (DatatypeConfigurationException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		} catch (ParseException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		//
		//		aziendaTo.setIndirizzo("via test");
		//
		//		aziendaTo.setComIstat("64050");
		//		aziendaTo.setComProSigla("AV");
		//
		//
		//		InsertApiAzienda insertAzienda = new InsertApiAzienda();
		//		insertAzienda.setApiAziendaTO(aziendaTo);
		//
		//		try {
		//
		//			InsertApiAziendaResponse response =  serviceAnagraficaAziende.insertApiAzienda(insertAzienda, autenticazione);
		//			Apiazienda aziendaRet= response.getApiAziendaTO();
		//
		//
		//
		//
		//		} catch (BusinessWsException_Exception e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}



	}

}
