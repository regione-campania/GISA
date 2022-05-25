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
import java.util.List;

import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.GestoreConnessioni;

import com.sun.xml.internal.ws.fault.ServerSOAPFaultException;

import ext.aspcfs.modules.apiari.base.Indirizzo;
import ext.aspcfs.modules.apiari.base.SoggettoFisico;
import ext.aspcfs.modules.apiari.base.WSBdnLog;



public class WsApiAnagraficaPersoneBdn {
	
	private PersoneEndpoint endPointPersone = null ;
	SOAPAutenticazioneWS autenticazione = null ;
	
	
	public WsApiAnagraficaPersoneBdn()
	{
		PersoneEndpointService service = new PersoneEndpointService();
		endPointPersone = service.getPersoneEndpointPort();
		
	
		autenticazione = new SOAPAutenticazioneWS();
		autenticazione.setUsername(ApplicationProperties.getProperty("USERNAME_BDAPI"));
		autenticazione.setPassword(ApplicationProperties.getProperty("PASSWORD_BDAPI"));
		autenticazione.setRuoloCodice(ApplicationProperties.getProperty("RUOLO_CODICE_BDAPI"));
		autenticazione.setRuoloValoreCodice(ApplicationProperties.getProperty("RUOLO_VALORE_BDAPI"));
	}

	
	public Persone searchPersone(SoggettoFisico soggettoFisico,Connection db,int idUtente) throws BusinessWsException_Exception
	{
		
		
		WSBdnLog logWS = new WSBdnLog();
		logWS.setIdUtente(idUtente);
		logWS.setIdOggetto(soggettoFisico.getIdSoggetto());
		logWS.setNomeTabella("opu_soggetto_fisico");
		logWS.setTipoOperazione(WSBdnLog.TYPE_WS_SEARCH_PERSONA);
		
		Persone persona = new Persone();
		persona.setIdFiscale(soggettoFisico.getCodFiscale().toUpperCase());
		
		
		Persone p = new Persone();
		p.setIdFiscale(soggettoFisico.getCodFiscale().toUpperCase());
		
		
		SearchPersone searchBean = new SearchPersone();
		searchBean.setPersoneSearchTO(p);
		try {
			logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));
			SearchPersoneResponse response = endPointPersone.searchPersone(searchBean, autenticazione);
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("OK");
			logWS.setNomerServizio("searchPersone");
			logWS.setParametri(p);
			logWS.insertBdnLogWs();
			List<Persone >risposta = response.getPersoneTO();
			
			if (risposta.size()>0){
				
			return risposta.get(0) ;	
			}
		} catch (BusinessWsException_Exception e) {
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			String errore ="";
			
			 errore = e.getFaultInfo().getMessage()+" : "+e.getFaultInfo().getResult().getErrore();
			
			for (FieldError error : e.getFaultInfo().getResult().getErrors())
			{
				errore +="["+error.getField()+": "+error.getMessage()+ "]" ;
	
			}
			
			logWS.setNomerServizio("searchPersone");
			logWS.setParametri(p);
			
			logWS.setNoteEsito(errore);
			//logWS.insertBdnLogWs();
			logWS.insertBdnLogWsErrore();
			
			throw e ;
		}
		catch (ServerSOAPFaultException e) {
			// TODO Auto-generated catch block
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			logWS.setNoteEsito("ERRORE : "+e.getMessage());
		//	logWS.insertBdnLogWs();
			logWS.insertBdnLogWsErrore();
			
			throw e ;
		
			
		}
		return null;
		
	}
	
	public Persone searchPersone(String cf) throws BusinessWsException_Exception
	{
		
		
	
		Persone persona = new Persone();
		persona.setIdFiscale(cf.toUpperCase());
		
		
		Persone p = new Persone();
		p.setIdFiscale(cf.toUpperCase());
		
		
		SearchPersone searchBean = new SearchPersone();
		searchBean.setPersoneSearchTO(p);
		try {
			
			SearchPersoneResponse response = endPointPersone.searchPersone(searchBean, autenticazione);
			
			List<Persone >risposta = response.getPersoneTO();
			
			if (risposta.size()>0){
				
			return risposta.get(0) ;	
			}
		} catch (BusinessWsException_Exception e) {
			
			String errore ="";
			
			 errore = e.getFaultInfo().getMessage()+" : "+e.getFaultInfo().getResult().getErrore();
			
			for (FieldError error : e.getFaultInfo().getResult().getErrors())
			{
				errore +="["+error.getField()+": "+error.getMessage()+ "]" ;
	
			}
			
			
			
			throw e ;
		}
		catch (ServerSOAPFaultException e) {
			// TODO Auto-generated catch block
		
			
			throw e ;
		
			
		}
		return null;
		
	}
	
	public Persone searchPersone(String cf,Connection db,int idUtente) throws BusinessWsException_Exception
	{
		
		
	
		Persone persona = new Persone();
		persona.setIdFiscale(cf.toUpperCase());
		
		
		Persone p = new Persone();
		p.setIdFiscale(cf.toUpperCase());
		
		
		SearchPersone searchBean = new SearchPersone();
		searchBean.setPersoneSearchTO(p);
		try {
			
			SearchPersoneResponse response = endPointPersone.searchPersone(searchBean, autenticazione);
			
			List<Persone >risposta = response.getPersoneTO();
			
			if (risposta.size()>0){
				
			return risposta.get(0) ;	
			}
		} catch (BusinessWsException_Exception e) {
			
			String errore ="";
			
			 errore = e.getFaultInfo().getMessage()+" : "+e.getFaultInfo().getResult().getErrore();
			
			for (FieldError error : e.getFaultInfo().getResult().getErrors())
			{
				errore +="["+error.getField()+": "+error.getMessage()+ "]" ;
	
			}
			
			
			
			throw e ;
		}
		catch (ServerSOAPFaultException e) {
			// TODO Auto-generated catch block
		
			
			throw e ;
		
			
		}
		return null;
		
	}
	
	private String getCodiceAslOld(Connection db,int comune) throws SQLException
	{
		String codiceAslOld = "" ;
		
		PreparedStatement pst = db.prepareStatement("select codiceistatasl_old from comuni1 where id="+comune);
		java.sql.ResultSet rs = pst.executeQuery();
		if (rs.next())
			codiceAslOld="R"+rs.getString(1);
		
		
		
		return codiceAslOld ; 
	}
	
	private String getCodiceAslNuovo(Connection db,int comune) throws SQLException
	{
		String codiceAslOld = "" ;
		
		PreparedStatement pst = db.prepareStatement("select codiceasl_bdn from comuni1 where id="+comune);
		java.sql.ResultSet rs = pst.executeQuery();
		if (rs.next())
			codiceAslOld="R"+rs.getString(1);
		
		
		
		return codiceAslOld ; 
	}
	
	public Persone insertAnagraficaPersona(SoggettoFisico soggettoFisico,Connection db,int idUtente) throws BusinessWsException_Exception, SQLException
	{
		
		
		WSBdnLog logWS = new WSBdnLog();
		logWS.setIdUtente(idUtente);
		logWS.setIdOggetto(soggettoFisico.getIdSoggetto());
		logWS.setNomeTabella("opu_soggetto_fisico");
		logWS.setTipoOperazione(WSBdnLog.TYPE_WS_INSERT_PERSONA);
		logWS.setNomerServizio("insertPersona");
		Persone risposta = null;

		Persone persona = new Persone();
		try {
		risposta = searchPersone(soggettoFisico, db, idUtente);
		
		if (risposta!=null)
			return risposta;
		
		
		persona.setIdFiscale(soggettoFisico.getCodFiscale().toUpperCase());
		persona.setCognome(soggettoFisico.getCognome());
		persona.setNome(soggettoFisico.getNome());
		persona.setCap(soggettoFisico.getIndirizzo().getCap());
		persona.setComIstat(soggettoFisico.getIndirizzo().getCodiceIstatComune());
		persona.setIndirizzo(soggettoFisico.getIndirizzo().getVia());
		persona.setAslCodice(soggettoFisico.getIndirizzo().getCodiceAsl());
		persona.setAslCodice(getCodiceAslNuovo(db,soggettoFisico.getIndirizzo().getComune()));
		persona.setProSigla(soggettoFisico.getIndirizzo().getSiglaProvincia());
		
		persona.setStCodice("IT");
		
		persona.setCognNome(soggettoFisico.getCognome() + " " + soggettoFisico.getNome());
		persona.setLocalita(soggettoFisico.getIndirizzo().getVia());
		System.out.println("WsApiAnagraficaPersoneBdn fix telefono");
		persona.setTelefono(soggettoFisico.getTelefono1()); // Come ha fatto a funzionare dal 2018 con "setLocalita" e' un mistero della fede 
		persona.setEmail(soggettoFisico.getEmail());
		persona.setStNome("ITALIA");
		persona.setComDescrizione(soggettoFisico.getIndirizzo().getDescrizioneComune());
		persona.setProNome(soggettoFisico.getIndirizzo().getDescrizione_provincia());
		
		InsertPersone insertPersona = new InsertPersone();
		insertPersona.setPersoneTO(persona);
		logWS.setParametri(persona);

			
			logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));
			InsertPersoneResponse response = endPointPersone.insertPersone(insertPersona, autenticazione);
			risposta = response.getPersoneTO();
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("OK");
			logWS.insertBdnLogWs();
			
		
		} catch (BusinessWsException_Exception e) {
			
			
			 if( e.getFaultInfo().getResult().getElencoCodici()!= null && e.getFaultInfo().getResult().getElencoCodici().contains("S001"))
			{
			InsertPersone insertPersona = new InsertPersone();
			
			persona.setAslCodice(getCodiceAslNuovo(db,soggettoFisico.getIndirizzo().getComune()));
			insertPersona.setPersoneTO(persona);
			
			
			InsertPersoneResponse response = endPointPersone.insertPersone(insertPersona, autenticazione);
			risposta = response.getPersoneTO();
			if ( risposta!=null)
				return risposta ;
			}
			
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");

			String errore ="";
			
			 errore = e.getFaultInfo().getMessage()+" : "+e.getFaultInfo().getResult().getErrore();
			
			for (FieldError error : e.getFaultInfo().getResult().getErrors())
			{
				errore +="["+error.getField()+": "+error.getMessage()+ "nomeServizio: insertPersone]" ;
	
			}
			//logWS.insertBdnLogWs();
			logWS.insertBdnLogWsErrore();
			
			throw e ;
		}
		catch (ServerSOAPFaultException e) {
			
			 
				InsertPersone insertPersona = new InsertPersone();
				
				persona.setAslCodice(getCodiceAslNuovo(db,soggettoFisico.getIndirizzo().getComune()));
				insertPersona.setPersoneTO(persona);
				
				
				InsertPersoneResponse response = endPointPersone.insertPersone(insertPersona, autenticazione);
				risposta = response.getPersoneTO();
				if ( risposta!=null)
					return risposta ;
				
			// TODO Auto-generated catch block
			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("KO");
			logWS.setNoteEsito("ERRORE : "+e.getMessage());
			//logWS.insertBdnLogWs();
			logWS.insertBdnLogWsErrore();
			
			throw e ;

		
			
		}
		return risposta;
		
		
	}
	
	
	
	
	public String updateAnagraficaPersona(SoggettoFisico soggettoFisico,Connection db,int idUtente) throws SQLException
	{
		
		
		WSBdnLog logWS = new WSBdnLog();
		logWS.setIdUtente(idUtente);
		logWS.setIdOggetto(soggettoFisico.getIdSoggetto());
		logWS.setNomeTabella("opu_soggetto_fisico");
		logWS.setTipoOperazione(WSBdnLog.TYPE_WS_UPDATE_PERSONA);
		logWS.setNomerServizio("updatePersona");

		Persone risposta = null;
		Persone persona = new Persone();
		try 
		{
			persona = searchPersone(soggettoFisico, db, idUtente);
			
			persona.setIdFiscale(soggettoFisico.getCodFiscale().toUpperCase());
			persona.setCognome(soggettoFisico.getCognome());
			persona.setNome(soggettoFisico.getNome());
			persona.setCap(soggettoFisico.getIndirizzo().getCap());
			persona.setComIstat(soggettoFisico.getIndirizzo().getCodiceIstatComune());
			persona.setIndirizzo(soggettoFisico.getIndirizzo().getVia());
			persona.setAslCodice(soggettoFisico.getIndirizzo().getCodiceAsl());
			persona.setAslCodice(getCodiceAslNuovo(db,soggettoFisico.getIndirizzo().getComune()));
			persona.setProSigla(soggettoFisico.getIndirizzo().getSiglaProvincia());
			persona.setComDescrizione(soggettoFisico.getIndirizzo().getComuneTesto());
			persona.setEmail(soggettoFisico.getEmail());
			persona.setStCodice("IT");
			
			UpdatePersone updatePersona = new UpdatePersone();
			updatePersona.setPersoneTO(persona);
			logWS.setParametri(persona);

			
			logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));
			UpdatePersoneResponse response = endPointPersone.updatePersone(updatePersona, autenticazione);
			risposta = response.getPersoneTO();
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
				errore +="["+error.getField()+": "+error.getMessage()+ "nomeServizio: updatePersone]" ;
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
			
			String errore = e.getFault().toString();
			
			return errore;
			
		}
		return null;
	}
	


}
