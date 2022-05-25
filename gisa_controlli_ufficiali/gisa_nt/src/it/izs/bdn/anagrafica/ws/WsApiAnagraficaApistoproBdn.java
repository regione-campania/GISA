/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.izs.bdn.anagrafica.ws;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.GestoreConnessioni;

import com.sun.xml.internal.ws.fault.ServerSOAPFaultException;

import ext.aspcfs.modules.apiari.base.Indirizzo;
import ext.aspcfs.modules.apiari.base.Operatore;
import ext.aspcfs.modules.apiari.base.SoggettoFisico;
import ext.aspcfs.modules.apiari.base.WSBdnLog;



public class WsApiAnagraficaApistoproBdn 
{
	
	private ApistoproEndpoint endPointApistopro = null ;
	SOAPAutenticazioneWS autenticazione = null ;
	
	
	public WsApiAnagraficaApistoproBdn()
	{
		ApistoproEndpointService service = new ApistoproEndpointService();
		endPointApistopro = service.getApistoproEndpointPort();
		
	
		autenticazione = new SOAPAutenticazioneWS();
		autenticazione.setUsername(ApplicationProperties.getProperty("USERNAME_BDAPI"));
		autenticazione.setPassword(ApplicationProperties.getProperty("PASSWORD_BDAPI"));
		autenticazione.setRuoloCodice(ApplicationProperties.getProperty("RUOLO_CODICE_BDAPI"));
		autenticazione.setRuoloValoreCodice(ApplicationProperties.getProperty("RUOLO_VALORE_BDAPI"));
	}

	public String insertPersonaStorico(SoggettoFisico soggettoFisico,Operatore azienda, Timestamp dataInizioAttivita, Connection db,int idUtente) throws DatatypeConfigurationException
	{
		WSBdnLog logWS = new WSBdnLog();
		logWS.setIdUtente(idUtente);
		logWS.setIdOggetto(soggettoFisico.getIdSoggetto());
		logWS.setNomeTabella("opu_soggetto_fisico");
		logWS.setTipoOperazione(WSBdnLog.TYPE_WS_INSERT_PERSONA_STORICO);
		logWS.setNomerServizio("insertPersonaStorico");
		Apistopro risposta = null;

		Apistopro persona = new Apistopro();
		try 
		{
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(dataInizioAttivita);
			XMLGregorianCalendar dtInizioAttivita = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			
			persona.setDtInizioAttivita(dtInizioAttivita);
			persona.setPropIdFiscale(soggettoFisico.getCodFiscale().toUpperCase());
			persona.setApiattAziendaCodice(azienda.getCodiceAzienda());
		
			InsertApistopro insertPersonaStorico = new InsertApistopro();
			insertPersonaStorico.setStoricoProprietarioTO(persona);
			logWS.setParametri(persona);

			
			logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));
			InsertApistoproResponse response = endPointApistopro.insertStoricoProprietario(insertPersonaStorico, autenticazione);
			risposta = response.getStoricoProprietarioTO();
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("OK");
			logWS.insertBdnLogWs();
		} 
		catch (BusinessWsException_Exception e) 
		{
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			logWS.setNoteEsito("ERRORE: " + e.getMessage());

			String errore = e.getFaultInfo().getMessage()+" : "+e.getFaultInfo().getResult().getErrore();
			for (FieldError error : e.getFaultInfo().getResult().getErrors())
			{
				errore +="["+error.getField()+": "+error.getMessage()+ "nomeServizio: insertApistopro]" ;
			}
			logWS.insertBdnLogWsErrore();
			
			return errore;
		}
		catch (ServerSOAPFaultException e) 
		{
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			logWS.setNoteEsito("ERRORE : "+e.getMessage());
			logWS.insertBdnLogWsErrore();
			
			String errore = e.getMessage();
			
			return errore;
		}
		return null;
	}
}
