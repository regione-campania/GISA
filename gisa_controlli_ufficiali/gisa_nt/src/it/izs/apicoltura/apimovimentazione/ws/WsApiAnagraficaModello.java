/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.izs.apicoltura.apimovimentazione.ws;


import it.izs.apicoltura.apianagraficaattivita.ws.Api;
import it.izs.apicoltura.apianagraficaattivita.ws.BusinessWsException_Exception;
import it.izs.apicoltura.apianagraficaattivita.ws.FieldError;
import it.izs.apicoltura.apianagraficaattivita.ws.SearchApi;
import it.izs.apicoltura.apianagraficaattivita.ws.SearchApiByPk;
import it.izs.apicoltura.apianagraficaattivita.ws.SearchApiByPkResponse;
import it.izs.apicoltura.apianagraficaattivita.ws.SearchApiResponse;
import it.izs.apicoltura.apianagraficaazienda.ws.Apiazienda;
import it.izs.apicoltura.apianagraficaazienda.ws.UpdateApiAzienda;
import it.izs.apicoltura.apianagraficaazienda.ws.UpdateApiAziendaResponse;
import it.izs.sinsa.services.BusinessException;
import it.izs.apicoltura.apimovimentazione.ws.Apimodmov.ListaDettaglioModello;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;



















import org.apache.log4j.Logger;
import org.aspcfs.modules.util.imports.ApplicationProperties;

import com.sun.xml.internal.ws.fault.ServerSOAPFaultException;
import com.thoughtworks.xstream.XStream;

import ext.aspcfs.modules.apiari.base.ModelloC;
import ext.aspcfs.modules.apiari.base.Operatore;
import ext.aspcfs.modules.apiari.base.Stabilimento;
import ext.aspcfs.modules.apiari.base.WSBdnLog;



public class WsApiAnagraficaModello {
	
	private ApimodmovEndpoint serviceAnagraficaAziende =null ;
	private ApidetmodEndpoint serviceDetMod =null ;
	SOAPAutenticazioneWS autenticazione = null ;

	 private final static Logger log = Logger.getLogger(WsApiAnagraficaModello.class);

	public WsApiAnagraficaModello()
	{
		ApimodmovEndpointService service = new ApimodmovEndpointService();
		ApidetmodEndpointService serviceDetMod2 = new ApidetmodEndpointService();

		serviceAnagraficaAziende = service.getApimodmovEndpointPort();
		serviceDetMod = serviceDetMod2.getApidetmodEndpointPort();
		autenticazione = new SOAPAutenticazioneWS();
		autenticazione.setUsername(ApplicationProperties.getProperty("USERNAME_BDAPI"));
		autenticazione.setPassword(ApplicationProperties.getProperty("PASSWORD_BDAPI"));
		autenticazione.setRuoloCodice(ApplicationProperties.getProperty("RUOLO_CODICE_BDAPI"));
		autenticazione.setRuoloValoreCodice(ApplicationProperties.getProperty("RUOLO_VALORE_BDAPI"));
	}
	
	
	public static String jaxbObjectToXML(Apidetmod emp) throws JAXBException {

        try {
            JAXBContext context = JAXBContext.newInstance(Apidetmod.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            //for pretty-print XML in JAXB
           // m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            // Write to System.out for debugging
            // m.marshal(emp, System.out);

            // Write to File
            m.marshal(emp,  sw);
            return sw.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            throw e ;
        }
        
    }
	
	
	public static void main(String[] args) throws JAXBException
	{
		Apimodmov apiIngTo = new Apimodmov();
		
		Apidetmod pp = new Apidetmod();
		pp.setDestApiProgressivo( 1);
		pp.setDestApiComIstat("049");
		pp.setDestApiProSigla("NA");
		pp.setDestApiIndirizzo("via prova");
		pp.setNumAlveari(23);
		pp.setNumSciami(0);
		pp.setNumPacchiDapi(0);
		pp.setNumApiRegine(0);
		pp.setDestApiLatitudine(41.12);
		pp.setDestApiLongitudine(14.12);
		
		//ListaDettaglioModello lista = new ListaDettaglioModello();
		//lista.apidetmod = new ArrayList<Apidetmod>();
		//lista.apidetmod.add(pp);
		
		

	  
	    
		
		
		
		
	}
	
	public Apimodmov insert(ModelloC movimentazione,Connection db,int idutente) throws it.izs.apicoltura.apimovimentazione.ws.BusinessWsException_Exception, SQLException
	{

		WSBdnLog logWS = new WSBdnLog();
		
		Apimodmov aziendaResp = null;
		Apimodmov apiIngTo = new Apimodmov();
		apiIngTo.setApiProgressivo(Integer.parseInt(movimentazione.getProgressivoApiarioOrigine()));
		apiIngTo.setApiattAziendaCodice(movimentazione.getCodiceAziendaOrigine());
		apiIngTo.setNumModello(movimentazione.getNumero_modello());
		apiIngTo.setAttestazioneSanitaria((movimentazione.isAttestazioneSanitaria())?("S"):("N"));
		
		if(movimentazione.getIdTipoMovimentazione()==1)
			apiIngTo.setApimotuscCodice("C");
		else if(movimentazione.getIdTipoMovimentazione()==2)
			apiIngTo.setApimotuscCodice("N");
		else
			apiIngTo.setApimotuscCodice("I");
		
		apiIngTo.setDestApiattAziendaCodice(movimentazione.getCodiceAziendaDestinazione());
	
		
		try {

			GregorianCalendar c = new GregorianCalendar();
			
			c.setTime(movimentazione.getData_modello());
			XMLGregorianCalendar dtModello = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

			apiIngTo.setDtModello(dtModello);								

		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		try {

			GregorianCalendar c = new GregorianCalendar();
			
			c.setTime(movimentazione.getDataMovimentazione());
			XMLGregorianCalendar dtUscita = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

			apiIngTo.setDtUscita(dtUscita);								

		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String istat = "" ;
		String sql = "select cod_comune from comuni1 where nome ilike ? ";
		PreparedStatement pst = db.prepareStatement(sql);
		pst.setString(1, movimentazione.getComune_dest());
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			istat = rs.getString(1);
		
		
		
	
		Apidetmod pp = null;
		if(movimentazione.getIdTipoMovimentazione()==2)
		{
			pp = new Apidetmod();
			pp.setDestApiProgressivo( Integer.parseInt(movimentazione.getProgressivoApiarioDestinazione()));
			pp.setDestApiComIstat(istat);
			pp.setDestApiProSigla(movimentazione.getSigla_prov_comune_dest());
			pp.setDestApiComProSigla(movimentazione.getSigla_prov_comune_dest());
			pp.setDestApiIndirizzo(movimentazione.getIndirizzo_dest());
			pp.setNumAlveari(movimentazione.getNumApiariSpostati());
			pp.setNumSciami(0);
			pp.setNumPacchiDapi(0);
			pp.setNumApiRegine(movimentazione.getNumRegineDaSpostare());
			pp.setApidetmodId(0);
			pp.setDestApiComDescrizione(movimentazione.getComune_dest());
			pp.setDestApiLatitudine(movimentazione.getLatitudine_dest());
			pp.setDestApiLongitudine(movimentazione.getLongitudine_dest());
			pp.setDestApiId(movimentazione.getIdBdaApiarioDestinazione());
		}
		
        

		if(apiIngTo.getApimotuscCodice().equals("N"))
		{
			ListaDettaglioModello lista = new ListaDettaglioModello();
			lista.apidetmod = new ArrayList<Apidetmod>();
			lista.apidetmod.add(pp);
			
			apiIngTo.setListaDettaglioModello(lista);
			  XStream xStream = new XStream();
			    String xml = "<![CDATA["+ xStream.toXML(lista.apidetmod).replaceAll("it.izs.apicoltura.apimovimentazione.ws.Apidetmod", "dettaglioModello")+"]]>";
	
			
			apiIngTo.setXmlListaDettaglioModello(xml);
		}
		else
		{
			apiIngTo.setNumAlveari(movimentazione.getNumAlveariDaSpostare());
			apiIngTo.setNumSciami(movimentazione.getNumSciamiDaSpostare());
			apiIngTo.setNumPacchiDapi(movimentazione.getNumPacchiDaSpostare());
			apiIngTo.setNumApiRegine(movimentazione.getNumRegineDaSpostare());
		}
		
		if(apiIngTo.getApimotuscCodice().equals("I"))
		{
			apiIngTo.setDestAziendaComIstat(istat);
			apiIngTo.setDestAziendaComProSigla(movimentazione.getSigla_prov_comune_dest());
			apiIngTo.setDestAziendaIndirizzo(movimentazione.getIndirizzo_dest());
			apiIngTo.setDestAziendaIdFiscale(movimentazione.getCfPartitaApicoltore());
			apiIngTo.setDestAziendaDenominazione(movimentazione.getDenominazioneApicoltore());
			apiIngTo.setRecuperoMaterialeBiologico(movimentazione.getRecuperoMaterialeBiologico());
		}
		
	
		
		
		InsertApimodmov ingressoMovimentazione = new InsertApimodmov();
		ingressoMovimentazione.setApimodmovTO(apiIngTo);

		logWS.setParametri(apiIngTo);
		try {
			logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));

			InsertApimodmovResponse responce = serviceAnagraficaAziende.insertApimodmov(ingressoMovimentazione, autenticazione);

			logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
			logWS.setEsito("OK");
			logWS.insertBdnLogWs();
			aziendaResp = responce.getApimodmovTO();
			

		} catch (it.izs.apicoltura.apimovimentazione.ws.BusinessWsException_Exception  e ) {

			
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
	
	public List<Apimodmov> search(ModelloC modello,Connection db,int idUtente)  throws ServerSOAPFaultException, it.izs.apicoltura.apimovimentazione.ws.BusinessWsException_Exception
	{
		log.info("##WS.API.ANAGRAFICA.SEARCH.MOVIMENTAZIONE## CHIAMATO SERVIZIO");

		List<Apimodmov> risposta = new ArrayList<Apimodmov>();
			WSBdnLog logWS = new WSBdnLog();
			logWS.setIdUtente(idUtente);
			logWS.setIdOggetto(modello.getId());
			logWS.setNomeTabella("apicoltura_movimentazioni");
			logWS.setTipoOperazione(WSBdnLog.TYPE_WS_SEARCH_MOVIMENTAZIONE);
			logWS.setNomerServizio("searchMovimentazione");
			Apimodmov apiModMov = new Apimodmov();
			apiModMov.setDestApiattAziendaCodice(modello.getCodiceAziendaDestinazione());
			apiModMov.setApiattAziendaCodice(modello.getCodiceAziendaOrigine());
			
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(modello.getData_modello());
			XMLGregorianCalendar dtModello=null;
			try 
			{
				dtModello = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			} 
			catch (DatatypeConfigurationException e1) 
			{
				e1.printStackTrace();
			}
			c.setTime(modello.getDataMovimentazione());
			XMLGregorianCalendar dtMovimentazione=null;
			try 
			{
				dtMovimentazione = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			} 
			catch (DatatypeConfigurationException e1) 
			{
				e1.printStackTrace();
			}
			apiModMov.setDtModello(dtModello);
			apiModMov.setDtUscita(dtMovimentazione);
			apiModMov.setNumModello(modello.getNumero_modello());
			
			
			SearchApimodmov apiModMovSearch = new SearchApimodmov();
			apiModMovSearch.setApimodmovSearch(apiModMov);
			
			logWS.setParametri(apiModMov);
	
			try 
			{
				logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));
				SearchApimodmovResponse response =  serviceAnagraficaAziende.searchApimodmov(apiModMovSearch, autenticazione);
				logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
				logWS.setEsito("OK");
				logWS.insertBdnLogWs();
				
				risposta =  response.getApimodmovTO();
				return risposta ;
			} 
			catch (ServerSOAPFaultException e) 
			{
				logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
				logWS.setEsito("KO");
				logWS.setNoteEsito("ERRORE : "+e.getMessage());
				logWS.insertBdnLogWsErrore();
				
				log.info("##WS.API.SEARCH.MOVIMENTAZIONE## CHIAMATO SERVIZIO KO");
				throw e;
			
				
			} 
			catch (it.izs.apicoltura.apimovimentazione.ws.BusinessWsException_Exception e) 
			{
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
				throw e;
			}
			
	

	}
	
	public Apimodmov searchByPk(ModelloC modello,Connection db,int idUtente)  throws ServerSOAPFaultException, it.izs.apicoltura.apimovimentazione.ws.BusinessWsException_Exception
	{
		log.info("##WS.API.ANAGRAFICA.SEARCHBYPK.MOVIMENTAZIONE## CHIAMATO SERVIZIO");

		Apimodmov risposta = new Apimodmov();
			WSBdnLog logWS = new WSBdnLog();
			logWS.setIdUtente(idUtente);
			logWS.setIdOggetto(modello.getId());
			logWS.setNomeTabella("apicoltura_movimentazioni");
			logWS.setTipoOperazione(WSBdnLog.TYPE_WS_SEARCH_MOVIMENTAZIONE);
			logWS.setNomerServizio("searchMovimentazione");
			
			
			SearchApimodmovByPk apiModMovSearch = new SearchApimodmovByPk();
			apiModMovSearch.setApimodmovId(modello.getIdBdn());
			
			logWS.setParametri(apiModMovSearch);
	
			try 
			{
				logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));
				SearchApimodmovByPkResponse response =  serviceAnagraficaAziende.searchApimodmovByPk(apiModMovSearch, autenticazione);
				logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
				logWS.setEsito("OK");
				logWS.insertBdnLogWs();
				
				risposta =  response.getApimodmovTO();
				return risposta ;
			} 
			catch (ServerSOAPFaultException e) 
			{
				logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
				logWS.setEsito("KO");
				logWS.setNoteEsito("ERRORE : "+e.getMessage());
				logWS.insertBdnLogWsErrore();
				
				log.info("##WS.API.SEARCH.MOVIMENTAZIONE## CHIAMATO SERVIZIO KO");
				throw e;
			
				
			} 
	}
	
	
	
	public String valida(ModelloC movimentazione,Connection db,int idutente) throws Exception 
	{

		WSBdnLog logWS = new WSBdnLog();
		logWS.setIdUtente(idutente);
		logWS.setIdOggetto(-1);
		logWS.setNomeTabella("");
		logWS.setTipoOperazione(WSBdnLog.TYPE_WS_UPDATE_MOVIMENTAZIONE);
		Apimodmov movResp = null;
		logWS.setNomerServizio("updateMovimentazione");

		
		WsApiAnagraficaModello serviceMovimentazioni = new WsApiAnagraficaModello();
		ModelloC modello = new ModelloC();
		modello.setIdBdn(movimentazione.getIdBdn());
		Apimodmov movimentazioneBdn = serviceMovimentazioni.searchByPk(modello, db, idutente);
		
		if(movimentazioneBdn==null || movimentazioneBdn.getApimodmovId()<=0)
			throw new Exception("Nessuna o piu movimentazioni trovate");
		
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		
		int apiregine = movimentazioneBdn.getNumApiRegine();
		movimentazioneBdn.setDtStatoRichiesta(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
		movimentazioneBdn.setStatoRichiestaCodice("C");
		movimentazioneBdn.setStatoRichiesta("CONFERMATO");
		movimentazioneBdn.setNumApiRegine(1000);
		
		UpdateApimodmov updateMov = new UpdateApimodmov();
		updateMov.setApimodmovTO(movimentazioneBdn);
		
		logWS.setDataInvio(new Timestamp(System.currentTimeMillis()));

		UpdateApimodmovResponse responce = new UpdateApimodmovResponse();
		try 
		{
			responce = serviceAnagraficaAziende.updateApimodmov(updateMov, autenticazione);
			movimentazioneBdn.setNumApiRegine(apiregine);
			updateMov.setApimodmovTO(movimentazioneBdn);
			responce = serviceAnagraficaAziende.updateApimodmov(updateMov, autenticazione);
		} 
		catch (it.izs.apicoltura.apimovimentazione.ws.BusinessWsException_Exception e) 
		{
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

		logWS.setDataRisposta(new Timestamp(System.currentTimeMillis()));
		logWS.setEsito("OK");
		logWS.insertBdnLogWs();
		movResp = responce.getApimodmovTO();
			

		return "OK";

	}


}
