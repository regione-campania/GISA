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
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.aspcfs.modules.util.imports.ApplicationProperties;

import com.sun.xml.internal.ws.fault.ServerSOAPFaultException;

import ext.aspcfs.modules.apiari.base.Operatore;
import ext.aspcfs.modules.apiari.base.Stabilimento;
import ext.aspcfs.modules.apiari.base.WSBdnLog;

public class WsApiAnagraficaVariazioniUbi {
	
	  private final static Logger log = Logger.getLogger(it.izs.apicoltura.apianagraficaattivita.ws.WsApiAnagraficaVariazioniUbi.class);

	ApiubiEndpoint endPointAnagraficaUbicazione = null ;
	SOAPAutenticazioneWS autenticazione = null ;
	
	public WsApiAnagraficaVariazioniUbi()
	{
		ApiubiEndpointService service = new ApiubiEndpointService();
		endPointAnagraficaUbicazione = service.getApiubiEndpointPort();
		autenticazione = new SOAPAutenticazioneWS();
		autenticazione.setUsername(ApplicationProperties.getProperty("USERNAME_BDAPI"));
		autenticazione.setPassword(ApplicationProperties.getProperty("PASSWORD_BDAPI"));
		autenticazione.setRuoloCodice(ApplicationProperties.getProperty("RUOLO_CODICE_BDAPI"));
		autenticazione.setRuoloValoreCodice(ApplicationProperties.getProperty("RUOLO_VALORE_BDAPI"));
		
	}
	
	public void insertApiAnagraficaUbicazione(Stabilimento attivitaApicoltura,Connection db,int idUtente)
	{
		log.info("##WS.API.ANAGRAFICA.VARIAZIONE.UBICAZIONE## CHIAMATO SERVIZIO");

		WSBdnLog logWS = new WSBdnLog();
		logWS.setIdUtente(idUtente);
		logWS.setIdOggetto(attivitaApicoltura.getIdStabilimento());
		logWS.setNomeTabella("apicoltura_apiari");
		logWS.setTipoOperazione(WSBdnLog.TYPE_WS_INSERT_VAR_UBI);
		logWS.setIdVariazione(attivitaApicoltura.getIdVariazioneEseguita());
		logWS.setTabellaVariazione("apicoltura_apiari_variazioni_ubicazione");
			
		
		
		Apiubi variazioneUbicazione = new Apiubi();
		variazioneUbicazione.setApiApiattAziendaCodice(attivitaApicoltura.getOperatore().getCodiceAzienda());
		variazioneUbicazione.setApiProgressivo(Integer.parseInt(attivitaApicoltura.getProgressivoBDA()));
		variazioneUbicazione.setIndirizzo(attivitaApicoltura.getSedeOperativa().getVia());
		variazioneUbicazione.setComIstat(attivitaApicoltura.getSedeOperativa().getCodiceIstatComune());
		variazioneUbicazione.setCap(attivitaApicoltura.getSedeOperativa().getCap());
		variazioneUbicazione.setComProSigla(attivitaApicoltura.getSedeOperativa().getSiglaProvincia());
		variazioneUbicazione.setLatitudine(attivitaApicoltura.getSedeOperativa().getLatitudine());
		variazioneUbicazione.setLongitudine(attivitaApicoltura.getSedeOperativa().getLongitudine());
	

		GregorianCalendar c = new GregorianCalendar();
		c.setTime(attivitaApicoltura.getData_assegnazione_ubicazione());
		XMLGregorianCalendar dtApertura=null;
		try {
			
			dtApertura = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		variazioneUbicazione.setDtInizioValidita(dtApertura);

		InsertApiUbicazione storicoUbi = new InsertApiUbicazione();
		storicoUbi.setApiUbicazioneTO(variazioneUbicazione);

		try {
			logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));

			InsertApiUbicazioneResponse response =  endPointAnagraficaUbicazione.insertApiUbicazione(storicoUbi, autenticazione);
			Apiubi risposta =  response.getApiUbicazioneTO();
			
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("OK");
			logWS.insertBdnLogWs();
			
			log.info("##WS.API.ANAGRAFICA.VARIAZIONE.UBICAZIONE## CHIAMATO SERVIZIO OK");

		} catch (BusinessWsException_Exception e) {
			
			log.info("##WS.API.ANAGRAFICA.VARIAZIONE.UBICAZIONE## CHIAMATO SERVIZIO KO");

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

}
