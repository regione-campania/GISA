/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.izs.apicoltura.apimovimentazione.ws;

import it.izs.apicoltura.apianagraficaazienda.ws.Apiazienda;
import it.izs.apicoltura.apianagraficaazienda.ws.InsertApiAzienda;
import it.izs.apicoltura.apianagraficaazienda.ws.InsertApiAziendaResponse;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.aspcfs.modules.util.imports.ApplicationProperties;

import ext.aspcfs.modules.apiari.base.ModelloC;
import ext.aspcfs.modules.apiari.base.Operatore;
import ext.aspcfs.modules.apiari.base.WSBdnLog;



public class WsApiAnagraficaMovimentazioni {
	
	private ApiingEndpoint serviceAnagraficaAziende =null ;
	SOAPAutenticazioneWS autenticazione = null ;


	public WsApiAnagraficaMovimentazioni()
	{
		ApiingEndpointService service = new ApiingEndpointService();

		serviceAnagraficaAziende = service.getApiingEndpointPort();
		autenticazione = new SOAPAutenticazioneWS();
		autenticazione.setUsername(ApplicationProperties.getProperty("USERNAME_BDAPI"));
		autenticazione.setPassword(ApplicationProperties.getProperty("PASSWORD_BDAPI"));
		autenticazione.setRuoloCodice(ApplicationProperties.getProperty("RUOLO_CODICE_BDAPI"));
		autenticazione.setRuoloValoreCodice(ApplicationProperties.getProperty("RUOLO_VALORE_BDAPI"));
	}
	
	
	
	public Apiing insert(ModelloC movimentazione,Connection db,int idutente) throws it.izs.apicoltura.apimovimentazione.ws.BusinessWsException_Exception
	{

		WSBdnLog logWS = new WSBdnLog();
		
		Apiing aziendaResp = null;
		Apiing apiIngTo = new Apiing();
		apiIngTo.setApiattAziendaCodice(movimentazione.getCodiceAziendaDestinazione());
		apiIngTo.setApiProgressivo(Integer.parseInt(movimentazione.getProgressivoApiarioDestinazione()));
		apiIngTo.setProvApiattAziendaCodice(movimentazione.getCodiceAziendaOrigine());
		apiIngTo.setProvApiProgressivo(Integer.parseInt(movimentazione.getProgressivoApiarioOrigine()));
		apiIngTo.setNumAlveari(movimentazione.getNumApiariSpostati());
		apiIngTo.setNumApiRegine(0);
		apiIngTo.setNumPacchiDapi(0);
		apiIngTo.setNumSciami(0);
		
		try {

			GregorianCalendar c = new GregorianCalendar();
			
			c.setTime(movimentazione.getDataMovimentazione());
			XMLGregorianCalendar dtApertura = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

			apiIngTo.setDtIngresso(dtApertura);								

		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(movimentazione.getIdTipoMovimentazione()==1)
			apiIngTo.setApimotingCodice("C");
		else
			apiIngTo.setApimotingCodice("N");
		
		InsertApiing ingressoMovimentazione = new InsertApiing();
		ingressoMovimentazione.setApiingTO(apiIngTo);

		logWS.setParametri(apiIngTo);
		try {
			logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));

			InsertApiingResponse responce = serviceAnagraficaAziende.insertApiing(ingressoMovimentazione, autenticazione);

			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("OK");
			logWS.insertBdnLogWs();
			aziendaResp = responce.getApiingTO();
			

		} catch (it.izs.apicoltura.apimovimentazione.ws.BusinessWsException_Exception e) {

			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			String errore ="";
			
			 errore = e.getFaultInfo().getMessage()+" : "+e.getFaultInfo().getResult().getErrore();
			
			for (it.izs.apicoltura.apimovimentazione.ws.FieldError error : e.getFaultInfo().getResult().getErrors())
			{
				errore +="["+error.getField()+": "+error.getMessage()+ "]" ;
	
			}
			
			
			logWS.setNoteEsito(errore);
			
			logWS.insertBdnLogWs();
			throw e ;
		}
		return aziendaResp;

	}


}
