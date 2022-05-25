/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package com.aspcfs.modules.aziendezootecniche.base;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import it.izs.apicoltura.apianagraficaattivita.ws.SOAPAutenticazioneWS;
import it.izs.bdn.action.utilsXML;
import it.izs.bdn.anagrafica.ws.AvidetattUk;
import it.izs.bdn.anagrafica.ws.Azienda;
import it.izs.bdn.anagrafica.ws.AziendaEndpoint;
import it.izs.bdn.anagrafica.ws.AziendaEndpointService;
import it.izs.bdn.anagrafica.ws.BusinessWsException_Exception;
import it.izs.bdn.anagrafica.ws.Persone;
import it.izs.bdn.anagrafica.ws.SearchAzienda;
import it.izs.bdn.anagrafica.ws.SearchAziendaResponse;
import it.izs.bdn.anagrafica.ws.SearchDettaglioAttivitaByAziendaCodice;
import it.izs.bdn.anagrafica.ws.SearchDettaglioAttivitaByAziendaCodiceResponse;
import it.izs.bdn.anagrafica.ws.SearchUnitaProduttiva;
import it.izs.bdn.anagrafica.ws.SearchUnitaProduttivaResponse;
import it.izs.bdn.anagrafica.ws.Unipro;
import it.izs.bdn.anagrafica.ws.UniproEndpoint;
import it.izs.bdn.anagrafica.ws.UniproEndpointService;
import it.izs.bdn.anagrafica.ws.WsApiAnagraficaPersoneBdn;
import it.izs.bdn.bean.InfoAllevamentoBean;
import it.izs.bdn.webservices.GetAziendaResponseGetAziendaResult;
import it.izs.bdn.webservices.Get_Capi_AllevamentoResponseGet_Capi_AllevamentoResult;
import it.izs.bdn.webservices.SOAPAutenticazione;
import it.izs.bdn.webservices.WsAnagraficaCapoQryLocator;
import it.izs.bdn.webservices.WsAnagraficaCapoQrySoapStub;
import it.izs.bdn.webservices.WsAziendeQryLocator;
import it.izs.bdn.webservices.WsAziendeQrySoapStub;

import javax.servlet.ServletContext;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

import org.apache.axis.message.SOAPHeaderElement;
import org.aspcfs.modules.campioni.base.SpecieAnimali;
import org.aspcfs.modules.suap.utils.AttivitaProduttive;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.xml.sax.SAXException;

import com.darkhorseventures.framework.actions.ActionContext;
import com.oracle.xmlns.internal.webservices.jaxws_databinding.XmlResponseWrapper;


/**
 * 
 * @author Daniele
 *
 */
public class IstanzaAllevamentoBdn {

	private final static int COD_ERR_NotInBDN     		  =  1;
	private final static int COD_ERR_ServiceException     = -1;
	private final static int COD_ERR_SOAPException        = -2;
	private final static int COD_ERR_RemoteException      = -3;
	private final static int COD_ERR_NullPointerException = -4;
	private final static int COD_ERR_Exception            = -5;
	private final static int COD_ERR_NonPrevisto          = -6;


	private int idAsl;
	/*DATI ALLEVAMENTO*/
	private String errore	            ;
	private int cod_errore				;
	private String idAllevamento ; 
	private String idFiscaleAllevamento ;
	private String denominazione ; 
	private String dataInizioAttivita ; 
	private String codiceAsl ; 
	private String codiceazienda ;
	private String codiceSpecie ;
	private String descrizioneSpecie;
	private String idFiscaleProprietario;
	private String denominazioneProprietario ; 
	private String idFiscaleDetentore;
	private String denominazioneDetentore ;
	private String codiceOrientamentoProduttivo;
	private String descrizioneOrientamentoProduttivo ;
	private String descrizioneTipoProduzione;
	private String codiceTipoProduzione ;
	private String dataInizioResponsabilita ;
	private String dataFineAttivita ;
	private String indirizzoAllevmanentoVia ;
	private String indirizzoAllevamentoCap ;
	private String indirizzoAllevamentoCodComune ;
	private String indirizzoAllevamentoDescrizioneComune ;
	private String indirizzoAllevamentoSiglaProvincia ;
	private String dataInizioValiditaRecord ;
	private String dataInizioFineValiditaRecord ;
	private String note ;

	/*DATI AZIENDA ALLEVAMENTO*/
	private String aziendaLatitudine;
	private String aziendaLongitudine;
	private String aziendaCap ; 
	private String aziendaIndirizzo;
	private String aziendaDataApertura ;
	private String aziendaDataChiusura ;
	private String aziendaCodice ;

	private String aziendaCodiceAsl;
	private String aziendaCodiceComune;
	private String aziendDescrizioneComune;
	private String aziendaDataInizioValiditaRecord;
	private String aziendaDataFineValiditaRecord;

	/*DATI PROPRIETARIO*/

	private String proprietarioNominativo ;
	private String proprietarioIndirizzoVia ;
	private String proprietarioIndirizzoCap;
	private String proprietarioIndirizzoCodiceComune ;
	private String proprietarioIndirizzoDescrizioneComune ;

	private String proprietarioIdFiscale;

	/*DATI DETENTORE*/

	private String detentoreNominativo ;
	private String detentoreIndirizzoVia ;
	private String detentoreIndirizzoCap;
	private String detentoreIndirizzoCodiceComune ;
	private String detentoreIndirizzoDescrizioneComune ;
	private String detentoreIdFiscale;



	public String getDescrizioneTipoProduzione() {
		return descrizioneTipoProduzione;
	}
	public void setDescrizioneTipoProduzione(String descrizioneTipoProduzione) {
		this.descrizioneTipoProduzione = descrizioneTipoProduzione;
	}
	public int getIdAsl() {
		return idAsl;
	}
	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}
	public String getIndirizzoAllevamentoDescrizioneComune() {
		return indirizzoAllevamentoDescrizioneComune;
	}
	public void setIndirizzoAllevamentoDescrizioneComune(String indirizzoAllevamentoDescrizioneComune) {
		this.indirizzoAllevamentoDescrizioneComune = indirizzoAllevamentoDescrizioneComune;
	}
	public String getAziendDescrizioneComune() {
		return aziendDescrizioneComune;
	}
	public void setAziendDescrizioneComune(String aziendDescrizioneComune) {
		this.aziendDescrizioneComune = aziendDescrizioneComune;
	}
	public String getProprietarioIndirizzoDescrizioneComune() {
		return proprietarioIndirizzoDescrizioneComune;
	}
	public void setProprietarioIndirizzoDescrizioneComune(String proprietarioIndirizzoDescrizioneComune) {
		this.proprietarioIndirizzoDescrizioneComune = proprietarioIndirizzoDescrizioneComune;
	}
	public String getDetentoreIndirizzoDescrizioneComune() {
		return detentoreIndirizzoDescrizioneComune;
	}
	public void setDetentoreIndirizzoDescrizioneComune(String detentoreIndirizzoDescrizioneComune) {
		this.detentoreIndirizzoDescrizioneComune = detentoreIndirizzoDescrizioneComune;
	}
	public String getProprietarioIdFiscale() {
		return proprietarioIdFiscale;
	}
	public void setProprietarioIdFiscale(String proprietarioIdFiscale) {
		this.proprietarioIdFiscale = proprietarioIdFiscale;
	}
	public String getDetentoreIdFiscale() {
		return detentoreIdFiscale;
	}
	public void setDetentoreIdFiscale(String detentoreIdFiscale) {
		this.detentoreIdFiscale = detentoreIdFiscale;
	}
	public String getAziendaDataChiusura() {
		return aziendaDataChiusura;
	}
	public void setAziendaDataChiusura(String aziendaDataChiusura) {
		this.aziendaDataChiusura = aziendaDataChiusura;
	}
	public String getAziendaCodice() {
		return aziendaCodice;
	}
	public void setAziendaCodice(String aziendaCodice) {
		this.aziendaCodice = aziendaCodice;
	}
	public String getAziendaLatitudine() {
		return aziendaLatitudine;
	}
	public void setAziendaLatitudine(String aziendaLatitudine) {
		this.aziendaLatitudine = aziendaLatitudine;
	}
	public String getAziendaLongitudine() {
		return aziendaLongitudine;
	}
	public void setAziendaLongitudine(String aziendaLongitudine) {
		this.aziendaLongitudine = aziendaLongitudine;
	}
	public String getAziendaCap() {
		return aziendaCap;
	}
	public void setAziendaCap(String aziendaCap) {
		this.aziendaCap = aziendaCap;
	}
	public String getAziendaIndirizzo() {
		return aziendaIndirizzo;
	}
	public void setAziendaIndirizzo(String aziendaIndirizzo) {
		this.aziendaIndirizzo = aziendaIndirizzo;
	}
	public String getAziendaDataApertura() {
		return aziendaDataApertura;
	}
	public void setAziendaDataApertura(String aziendaDataApertura) {
		this.aziendaDataApertura = aziendaDataApertura;
	}
	public String getAziendaCodiceAsl() {
		return aziendaCodiceAsl;
	}
	public void setAziendaCodiceAsl(String aziendaCodiceAsl) {
		this.aziendaCodiceAsl = aziendaCodiceAsl;
	}
	public String getAziendaCodiceComune() {
		return aziendaCodiceComune;
	}
	public void setAziendaCodiceComune(String aziendaCodiceComune) {
		this.aziendaCodiceComune = aziendaCodiceComune;
	}
	public String getAziendaDataInizioValiditaRecord() {
		return aziendaDataInizioValiditaRecord;
	}
	public void setAziendaDataInizioValiditaRecord(String aziendaDataInizioValiditaRecord) {
		this.aziendaDataInizioValiditaRecord = aziendaDataInizioValiditaRecord;
	}
	public String getAziendaDataFineValiditaRecord() {
		return aziendaDataFineValiditaRecord;
	}
	public void setAziendaDataFineValiditaRecord(String aziendaDataFineValiditaRecord) {
		this.aziendaDataFineValiditaRecord = aziendaDataFineValiditaRecord;
	}
	public String getProprietarioNominativo() {
		return proprietarioNominativo;
	}
	public void setProprietarioNominativo(String proprietarioNominativo) {
		this.proprietarioNominativo = proprietarioNominativo;
	}
	public String getProprietarioIndirizzoVia() {
		return proprietarioIndirizzoVia;
	}
	public void setProprietarioIndirizzoVia(String proprietarioIndirizzoVia) {
		this.proprietarioIndirizzoVia = proprietarioIndirizzoVia;
	}
	public String getProprietarioIndirizzoCap() {
		return proprietarioIndirizzoCap;
	}
	public void setProprietarioIndirizzoCap(String proprietarioIndirizzoCap) {
		this.proprietarioIndirizzoCap = proprietarioIndirizzoCap;
	}
	public String getProprietarioIndirizzoCodiceComune() {
		return proprietarioIndirizzoCodiceComune;
	}
	public void setProprietarioIndirizzoCodiceComune(String proprietarioIndirizzoCodiceComune) {
		this.proprietarioIndirizzoCodiceComune = proprietarioIndirizzoCodiceComune;
	}
	public String getDetentoreNominativo() {
		return detentoreNominativo;
	}
	public void setDetentoreNominativo(String detentoreNominativo) {
		this.detentoreNominativo = detentoreNominativo;
	}
	public String getDetentoreIndirizzoVia() {
		return detentoreIndirizzoVia;
	}
	public void setDetentoreIndirizzoVia(String detentoreIndirizzoVia) {
		this.detentoreIndirizzoVia = detentoreIndirizzoVia;
	}
	public String getDetentoreIndirizzoCap() {
		return detentoreIndirizzoCap;
	}
	public void setDetentoreIndirizzoCap(String detentoreIndirizzoCap) {
		this.detentoreIndirizzoCap = detentoreIndirizzoCap;
	}
	public String getDetentoreIndirizzoCodiceComune() {
		return detentoreIndirizzoCodiceComune;
	}
	public void setDetentoreIndirizzoCodiceComune(String detentoreIndirizzoCodiceComune) {
		this.detentoreIndirizzoCodiceComune = detentoreIndirizzoCodiceComune;
	}
	public String getIndirizzoAllevmanentoVia() {
		return indirizzoAllevmanentoVia;
	}
	public void setIndirizzoAllevmanentoVia(String indirizzoAllevmanentoVia) {
		this.indirizzoAllevmanentoVia = indirizzoAllevmanentoVia;
	}
	public String getIndirizzoAllevamentoCap() {
		return indirizzoAllevamentoCap;
	}
	public void setIndirizzoAllevamentoCap(String indirizzoAllevamentoCap) {
		this.indirizzoAllevamentoCap = indirizzoAllevamentoCap;
	}
	public String getIndirizzoAllevamentoCodComune() {
		return indirizzoAllevamentoCodComune;
	}
	public void setIndirizzoAllevamentoCodComune(String indirizzoAllevamentoCodComune) {
		this.indirizzoAllevamentoCodComune = indirizzoAllevamentoCodComune;
	}
	public String getIndirizzoAllevamentoSiglaProvincia() {
		return indirizzoAllevamentoSiglaProvincia;
	}
	public void setIndirizzoAllevamentoSiglaProvincia(String indirizzoAllevamentoSiglaProvincia) {
		this.indirizzoAllevamentoSiglaProvincia = indirizzoAllevamentoSiglaProvincia;
	}
	public String getDataInizioValiditaRecord() {
		return dataInizioValiditaRecord;
	}
	public void setDataInizioValiditaRecord(String dataInizioValiditaRecord) {
		this.dataInizioValiditaRecord = dataInizioValiditaRecord;
	}
	public String getDataInizioFineValiditaRecord() {
		return dataInizioFineValiditaRecord;
	}
	public void setDataInizioFineValiditaRecord(String dataInizioFineValiditaRecord) {
		this.dataInizioFineValiditaRecord = dataInizioFineValiditaRecord;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCodiceOrientamentoProduttivo() {
		return codiceOrientamentoProduttivo;
	}
	public void setCodiceOrientamentoProduttivo(String codiceOrientamentoProduttivo) {
		this.codiceOrientamentoProduttivo = codiceOrientamentoProduttivo;
	}
	public String getDescrizioneOrientamentoProduttivo() {
		return descrizioneOrientamentoProduttivo;
	}
	public void setDescrizioneOrientamentoProduttivo(String descrizioneOrientamentoProduttivo) {
		this.descrizioneOrientamentoProduttivo = descrizioneOrientamentoProduttivo;
	}
	public String getCodiceTipoProduzione() {
		return codiceTipoProduzione;
	}
	public void setCodiceTipoProduzione(String codiceTipoProduzione) {
		this.codiceTipoProduzione = codiceTipoProduzione;
	}
	public String getDataInizioResponsabilita() {
		return dataInizioResponsabilita;
	}
	public void setDataInizioResponsabilita(String dataInizioResponsabilita) {
		this.dataInizioResponsabilita = dataInizioResponsabilita;
	}
	public String getDataFineAttivita() {
		return dataFineAttivita;
	}
	public void setDataFineAttivita(String dataFineAttivita) {
		this.dataFineAttivita = dataFineAttivita;
	}
	public String getIdAllevamento() {
		return idAllevamento;
	}
	public void setIdAllevamento(String idAllevamento) {
		this.idAllevamento = idAllevamento;
	}
	public String getIdFiscaleAllevamento() {
		return idFiscaleAllevamento;
	}
	public void setIdFiscaleAllevamento(String idFiscaleAllevamento) {
		this.idFiscaleAllevamento = idFiscaleAllevamento;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getDataInizioAttivita() {
		return dataInizioAttivita;
	}
	public void setDataInizioAttivita(String dataInizioAttivita) {
		this.dataInizioAttivita = dataInizioAttivita;
	}
	public String getCodiceAsl() {
		return codiceAsl;
	}
	public void setCodiceAsl(String codiceAsl) {
		this.codiceAsl = codiceAsl;
	}
	public String getCodiceazienda() {
		return codiceazienda;
	}
	public void setCodiceazienda(String codiceazienda) {
		this.codiceazienda = codiceazienda;
	}
	public String getCodiceSpecie() {
		return codiceSpecie;
	}
	public void setCodiceSpecie(String codiceSpecie) {
		this.codiceSpecie = codiceSpecie;
	}
	public String getDescrizioneSpecie() {
		return descrizioneSpecie;
	}
	public void setDescrizioneSpecie(String descrizioneSpecie) {
		this.descrizioneSpecie = descrizioneSpecie;
	}
	public String getIdFiscaleProprietario() {
		return idFiscaleProprietario;
	}
	public void setIdFiscaleProprietario(String idFiscaleProprietario) {
		this.idFiscaleProprietario = idFiscaleProprietario;
	}
	public String getDenominazioneProprietario() {
		return denominazioneProprietario;
	}
	public void setDenominazioneProprietario(String denominazioneProprietario) {
		this.denominazioneProprietario = denominazioneProprietario;
	}
	public String getIdFiscaleDetentore() {
		return idFiscaleDetentore;
	}
	public void setIdFiscaleDetentore(String idFiscaleDetentore) {
		this.idFiscaleDetentore = idFiscaleDetentore;
	}
	public String getDenominazioneDetentore() {
		return denominazioneDetentore;
	}
	public void setDenominazioneDetentore(String denominazioneDetentore) {
		this.denominazioneDetentore = denominazioneDetentore;
	}
	private static String xmlResp ;

	public String getErrore() {
		return errore;
	}
	public void setErrore(String errore) {
		this.errore = errore;
	}

	public int getCod_errore() {
		return cod_errore;
	}
	public void setCod_errore(int codErrore) {
		cod_errore = codErrore;
	}

	public String getXmlResp() {
		return xmlResp;
	}
	public void setXmlResp(String xmlResp) {
		this.xmlResp = xmlResp;
	}

	public static ArrayList<IstanzaAllevamentoBdn> findAllevamentiAvi(Connection db,String codice_azienda,String denominazione,String cod_specie) {


		ArrayList<IstanzaAllevamentoBdn> listaAllevamenti = new ArrayList<IstanzaAllevamentoBdn>();
		Logger logger = Logger.getLogger("MainLogger");

		// DEFINIZIONE CREDENZIALI DI ACCESSO AL WEBSERVICES
		String userName = ApplicationProperties.getProperty("USERNAME_WS_BDN");
		String passWord = ApplicationProperties.getProperty("PASSWORD_WS_BDN");



		SearchUnitaProduttivaResponse responseUnitaProduttiva = null ;

		UniproEndpoint stub_WsAziendeAviQry;

		int numRecord = 0;



		try {

			stub_WsAziendeAviQry = (UniproEndpoint) (new UniproEndpointService().getUniproEndpointPort());

			it.izs.bdn.anagrafica.ws.SOAPAutenticazioneWS autenticazione = new it.izs.bdn.anagrafica.ws.SOAPAutenticazioneWS();
			autenticazione.setUsername(ApplicationProperties.getProperty("USERNAME_BDAPI"));
			autenticazione.setPassword(ApplicationProperties.getProperty("PASSWORD_BDAPI"));
			autenticazione.setRuoloCodice(ApplicationProperties.getProperty("RUOLO_CODICE_BDAPI"));
			autenticazione.setRuoloValoreCodice(ApplicationProperties.getProperty("RUOLO_VALORE_BDAPI"));
			SearchUnitaProduttiva searchUnita = new SearchUnitaProduttiva();
			Unipro unita = new Unipro();
			unita.setAziendaCodice(codice_azienda);
			searchUnita.setUnitaProduttivaSearchTO(unita);


			responseUnitaProduttiva = stub_WsAziendeAviQry.searchUnitaProduttiva(searchUnita, autenticazione);



			AziendaEndpoint endPoindSearchAziende = null ; 

			endPoindSearchAziende = (AziendaEndpoint)(new AziendaEndpointService()).getAziendaEndpointPort();
			// RECUPERO VALORI DALLA RISPOSTA di findallevamento_str

			SearchAzienda searchAzienda = new SearchAzienda();
			Azienda azienda = new Azienda();
			azienda.setCodice(codice_azienda);
			searchAzienda.setAziendaSearchTO(azienda);
			SearchAziendaResponse responseAzienda= endPoindSearchAziende.searchAzienda(searchAzienda, autenticazione);
			try {

				for (Unipro re : responseUnitaProduttiva.getUnitaProduttivaTO())
				{
					
					
					SearchDettaglioAttivitaByAziendaCodice searchDet = new SearchDettaglioAttivitaByAziendaCodice();
					
					AvidetattUk det = new AvidetattUk();
					det.setAziendaCodice(codice_azienda);
					searchDet.setDettaglioAttivitaUkSearchTO(det);
					SearchDettaglioAttivitaByAziendaCodiceResponse respDet =  stub_WsAziendeAviQry.searchDettaglioAttivitaByAziendaCodice(searchDet, autenticazione);
					
					det = respDet.getDettaglioAttivitaUkTO().get(0);
					
					IstanzaAllevamentoBdn infoAllReturn = new IstanzaAllevamentoBdn();

					infoAllReturn.setIdAllevamento(re.getUniproId()+"");
					infoAllReturn.setIdFiscaleAllevamento(re.getPropIdFiscale());
					infoAllReturn.setDenominazione(re.getDenominazione());
					infoAllReturn.setDataInizioAttivita(re.getDtInizioAttivita()+"");
					infoAllReturn.setCodiceAsl(responseAzienda.getAziendaTO().get(0).getAslCodice());
					infoAllReturn.setCodiceazienda(responseAzienda.getAziendaTO().get(0).getCodice());
					infoAllReturn.setCodiceSpecie("1"+det.getSpeallCodice());
					infoAllReturn.setDescrizioneSpecie(det.getSpeallDescrizione());
					infoAllReturn.setIdFiscaleProprietario(re.getPropIdFiscale());	
					infoAllReturn.setIdFiscaleDetentore(det.getDetenIdFiscale());
					infoAllReturn.setDenominazioneDetentore(det.getDetenCognNome());
					infoAllReturn.setDenominazioneProprietario(re.getPropCognNome());

					listaAllevamenti.add(infoAllReturn);


				}

			} catch (java.lang.NullPointerException e) {
				IstanzaAllevamentoBdn infoAllReturn = new IstanzaAllevamentoBdn();
				infoAllReturn.setCod_errore(COD_ERR_NullPointerException);
				infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore NullPointerException : " + e );
				listaAllevamenti.add(infoAllReturn);
				e.printStackTrace();
			} catch (Exception e) {
				IstanzaAllevamentoBdn infoAllReturn = new IstanzaAllevamentoBdn();
				infoAllReturn.setCod_errore(COD_ERR_Exception);
				infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore Exception : " + e );
				listaAllevamenti.add(infoAllReturn);
				e.printStackTrace();
			}



		} catch (BusinessWsException_Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	



		return listaAllevamenti;
	}


	public static ArrayList<IstanzaAllevamentoBdn> findAllevamenti(Connection db,String codice_azienda,String denominazione,String cod_specie) {


		ArrayList<IstanzaAllevamentoBdn> listaAllevamenti = new ArrayList<IstanzaAllevamentoBdn>();
		Logger logger = Logger.getLogger("MainLogger");

		// DEFINIZIONE CREDENZIALI DI ACCESSO AL WEBSERVICES
		String userName = ApplicationProperties.getProperty("USERNAME_WS_BDN");
		String passWord = ApplicationProperties.getProperty("PASSWORD_WS_BDN");



		// DEFINIZIONE NODI DA RECUPERARE PER L'ALLEVAMENTO //


		String nodo_allev_id = "ALLEV_ID" ;
		String nodo_id_fiscale = "ID_FISCALE";
		String nodo_denominazione = "DENOMINAZIONE";
		String nodo_data_inizio_attivita = "DT_INIZIO_ATTIVITA";
		String nodo_data_codice_asl= "CODICE_ASL";
		String nodo_data_codice_azienda= "AZIENDA_CODICE";
		String nodo_data_codice_specie= "SPE_CODICE";
		String nodo_data_descr_specie= "SPE_DESCRIZIONE";
		String nodo_data_proprietario= "ID_FISCALE_PROP";
		String nodo_denominazione_prop = "DENOMINAZIONE_PROP";
		String nodo_data_detentore= "ID_FISCALE_DETEN";
		String nodo_denominazione_detentore = "DENOMINAZIONE_DETEN";




		String val_allev_id = "" ;
		String val_id_fiscale = "";
		String val_denominazione = "";
		String val_data_inizio_attivita = "";
		String val_data_codice_asl= "";
		String val_data_codice_azienda= "";
		String val_data_codice_specie= "";
		String val_data_descr_specie= "";
		String val_data_proprietario= "";
		String val_denominazione_prop = "";
		String val_data_detentore= "";
		String val_denominazione_detentore = "";

		String documento_xml_costruito_allevamento = "" ;

		WsAziendeQrySoapStub stub_WsAziendeQry;

		int numRecord = 0;

		WsAnagraficaCapoQrySoapStub stub_wsCapoQry;
		try {
			//
			stub_WsAziendeQry = (WsAziendeQrySoapStub) (new WsAziendeQryLocator().getwsAziendeQrySoap());

			//STUB PER NUMERO DEI CAPI IN ALLEVAMENT.O
			stub_wsCapoQry = (WsAnagraficaCapoQrySoapStub) (new WsAnagraficaCapoQryLocator().getwsAnagraficaCapoQrySoap()); 
			// DEFINIZIONE DELL'HEADER PER L'AUTENTICAZIONE
			SOAPHeaderElement header = new SOAPHeaderElement(new QName("http://bdr.izs.it/webservices", "SOAPAutenticazione", "web"));

			SOAPElement node = header.addChildElement("username");
			node.addTextNode(userName);
			SOAPElement node2 = header.addChildElement("password");
			node2.addTextNode(passWord);

			// INSERIMENTO HEADER NEGLI STUB
			stub_WsAziendeQry.setHeader(header);
			stub_wsCapoQry.setHeader(header);
			// RECUPERO RISPOSTA WEBSERVICES findallevamento_str

			if (denominazione.contains("'"))
				denominazione = "";
			documento_xml_costruito_allevamento = stub_WsAziendeQry.findAllevamento_STR(codice_azienda,denominazione,cod_specie);		
			xmlResp=documento_xml_costruito_allevamento;

			// RECUPERO VALORI DALLA RISPOSTA di findallevamento_str


			try {

				numRecord = utilsXML.getNumeroNodiXML(documento_xml_costruito_allevamento, nodo_allev_id);

				for (int i = 0 ; i < numRecord ; i++)
				{

					IstanzaAllevamentoBdn infoAllReturn = new IstanzaAllevamentoBdn();

					val_allev_id		=  utilsXML.getValueNodoXML(documento_xml_costruito_allevamento,nodo_allev_id,i );
					val_id_fiscale		=  utilsXML.getValueNodoXML(documento_xml_costruito_allevamento,nodo_id_fiscale,i );
					val_denominazione		=  utilsXML.getValueNodoXML(documento_xml_costruito_allevamento,nodo_denominazione,i );
					val_data_inizio_attivita		=  utilsXML.getValueNodoXML(documento_xml_costruito_allevamento,nodo_data_inizio_attivita,i );
					val_data_codice_asl		=  utilsXML.getValueNodoXML(documento_xml_costruito_allevamento,nodo_data_codice_asl,i );
					val_data_codice_azienda		=  utilsXML.getValueNodoXML(documento_xml_costruito_allevamento,nodo_data_codice_azienda,i );
					val_data_codice_specie		=  utilsXML.getValueNodoXML(documento_xml_costruito_allevamento,nodo_data_codice_specie,i );
					val_data_descr_specie		=  utilsXML.getValueNodoXML(documento_xml_costruito_allevamento,nodo_data_descr_specie,i );
					val_data_proprietario		=  utilsXML.getValueNodoXML(documento_xml_costruito_allevamento,nodo_data_proprietario,i );
					val_denominazione_prop		=  utilsXML.getValueNodoXML(documento_xml_costruito_allevamento,nodo_denominazione_prop,i );
					val_data_detentore		=  utilsXML.getValueNodoXML(documento_xml_costruito_allevamento,nodo_data_detentore,i );
					val_denominazione_detentore		=  utilsXML.getValueNodoXML(documento_xml_costruito_allevamento,nodo_denominazione_detentore,i );

					infoAllReturn.setIdAllevamento(val_allev_id);
					infoAllReturn.setIdFiscaleAllevamento(val_id_fiscale);
					infoAllReturn.setDenominazione(val_denominazione);
					infoAllReturn.setDataInizioAttivita(val_data_inizio_attivita);
					infoAllReturn.setCodiceAsl(val_data_codice_asl);
					infoAllReturn.setCodiceazienda(val_data_codice_azienda);
					infoAllReturn.setCodiceSpecie(val_data_codice_specie);
					infoAllReturn.setDescrizioneSpecie(val_data_descr_specie);
					infoAllReturn.setIdFiscaleProprietario(val_data_proprietario);	
					infoAllReturn.setIdFiscaleDetentore(val_data_detentore);
					infoAllReturn.setDenominazioneDetentore(val_denominazione_detentore);
					infoAllReturn.setDenominazioneProprietario(val_denominazione_prop);

					if (infoAllReturn!=null && infoAllReturn.getErrore() != null ) {
						if(infoAllReturn.getErrore().contains("CODICE AZIENDA NON PRESENTE IN ANAGRAFE")){
							infoAllReturn.setCod_errore(COD_ERR_NotInBDN);
							infoAllReturn.setErrore("[CapoParzialeBean.getCapoParzialeBean()] - AZIENDA NON PRESENTE IN ANAGRAFE");
						}
						else{
							infoAllReturn.setCod_errore(COD_ERR_NonPrevisto);
						}
					}

					listaAllevamenti.add(infoAllReturn);


				}
			} catch (SAXException e1) {
				// TODO Auto-generated catch block

			} catch (IOException e1) {
				// TODO Auto-generated catch block

			}


		} catch (ServiceException e) {
			IstanzaAllevamentoBdn infoAllReturn = new IstanzaAllevamentoBdn();
			infoAllReturn.setCod_errore(COD_ERR_ServiceException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore ServiceException : " + e );

			listaAllevamenti.add(infoAllReturn);
			//cpbReturn = null;
			e.printStackTrace();
		} catch (SOAPException e) {
			IstanzaAllevamentoBdn infoAllReturn = new IstanzaAllevamentoBdn();
			infoAllReturn.setCod_errore(COD_ERR_SOAPException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore SOAPException : " + e );
			listaAllevamenti.add(infoAllReturn);
			e.printStackTrace();
		} catch (RemoteException e) {
			IstanzaAllevamentoBdn infoAllReturn = new IstanzaAllevamentoBdn();
			infoAllReturn.setCod_errore(COD_ERR_RemoteException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore RemoteException : " + e );
			listaAllevamenti.add(infoAllReturn);
			e.printStackTrace();
		} catch (java.lang.NullPointerException e) {
			IstanzaAllevamentoBdn infoAllReturn = new IstanzaAllevamentoBdn();
			infoAllReturn.setCod_errore(COD_ERR_NullPointerException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore NullPointerException : " + e );
			listaAllevamenti.add(infoAllReturn);
			e.printStackTrace();
		} catch (Exception e) {
			IstanzaAllevamentoBdn infoAllReturn = new IstanzaAllevamentoBdn();
			infoAllReturn.setCod_errore(COD_ERR_Exception);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore Exception : " + e );
			listaAllevamenti.add(infoAllReturn);
			e.printStackTrace();
		}




		return listaAllevamenti;
	}


	public static IstanzaAllevamentoBdn getAllevamento(String codice_azienda,String id_fiscale,String cod_specie) {

		IstanzaAllevamentoBdn infoAllReturn = new IstanzaAllevamentoBdn();

		Logger logger = Logger.getLogger("MainLogger");

		// DEFINIZIONE CREDENZIALI DI ACCESSO AL WEBSERVICES
		String userName = ApplicationProperties.getProperty("USERNAME_WS_BDN");
		String passWord = ApplicationProperties.getProperty("PASSWORD_WS_BDN");


		String nodo_errore_id 			= "id";
		String nodo_descrizione_errore 			= "des";
		String nodo_allev_id = "ALLEV_ID" ;
		String nodo_id_fiscale = "ID_FISCALE";
		String nodo_denominazione = "DENOMINAZIONE";
		String nodo_data_inizio_attivita = "DT_INIZIO_ATTIVITA";
		String nodo_data_codice_asl= "CODICE_ASL";
		String nodo_data_codice_azienda= "AZIENDA_CODICE";
		String nodo_data_codice_specie= "SPE_CODICE";
		String nodo_data_descr_specie= "SPE_DESCRIZIONE";
		String nodo_data_proprietario= "PROP_ID_FISCALE";
		String nodo_denominazione_prop = "DENOMINAZIONE_PROP";
		String nodo_data_detentore= "DETEN_ID_FISCALE";
		String nodo_denominazione_detentore = "DENOMINAZIONE_DETEN";
		String nodo_flag_carne_latte = "FLAG_CARNE_LATTE";
		String nodo_orientamento_descrizione = "ORIENTAMENTO_DESCR";
		String nodo_tipo_prod_codice = "TIP_PROD_CODICE";
		String nodo_dt_inizio_responsabilita = "DT_INIZIO_RESPONSABILITA";
		String nodo_data_fine_attivita = "DT_FINE_ATTIVITA";
		String nodo_indirizzo = "INDIRIZZO";
		String nodo_cap = "CAP";
		String nodo_codice_comune= "COM_CODICE";
		String nodo_codice_provincia= "PRO_CODICE";
		String nodo_data_inizio_validita= "DT_INIVAL";
		String nodo_data_fine_validita= "DT_FINVAL";
		String nodo_note ="NOTE";


		String errorId= "";
		String errorDesc = "" ;

		String val_allev_id = "" ;
		String val_id_fiscale = "";
		String val_denominazione = "";
		String val_data_inizio_attivita = "";
		String val_data_codice_asl= "";
		String val_data_codice_azienda= "";
		String val_data_codice_specie= "";
		String val_data_descr_specie= "";
		String val_data_proprietario= "";
		String val_denominazione_prop = "";
		String val_data_detentore= "";
		String val_denominazione_detentore = "";

		String val_flag_carne_latte = "";
		String val_orientamento_descrizione = "";
		String val_tipo_prod_codice = "";
		String val_dt_inizio_responsabilita = "";
		String val_data_fine_attivita = "";


		String val_indirizzo = "";
		String val_cap = "";
		String val_codice_comune= "";
		String val_codice_provincia= "";
		String val_data_inizio_validita= "";
		String val_data_fine_validita= "";
		String val_note ="";

		// DEFINIZIONE DOCUMENTO RISPOSTA DA WEBSERVICES
		String documento_xml_costruito_allevamento    = null; 
		String get_capi_allevamento_response = null; 

		WsAziendeQrySoapStub stub_WsAziendeQry;
		WsAnagraficaCapoQrySoapStub stub_wsCapoQry;
		try {
			//
			stub_WsAziendeQry = (WsAziendeQrySoapStub) (new WsAziendeQryLocator().getwsAziendeQrySoap());
			//STUB PER NUMERO DEI CAPI IN ALLEVAMENTO
			stub_wsCapoQry = (WsAnagraficaCapoQrySoapStub) (new WsAnagraficaCapoQryLocator().getwsAnagraficaCapoQrySoap()); 
			// DEFINIZIONE DELL'HEADER PER L'AUTENTICAZIONE
			SOAPHeaderElement header = new SOAPHeaderElement(new QName("http://bdr.izs.it/webservices", "SOAPAutenticazione", "web"));

			SOAPElement node = header.addChildElement("username");
			node.addTextNode(userName);
			SOAPElement node2 = header.addChildElement("password");
			node2.addTextNode(passWord);

			// INSERIMENTO HEADER NEGLI STUB
			stub_WsAziendeQry.setHeader(header);
			stub_wsCapoQry.setHeader(header);
			// RECUPERO RISPOSTA WEBSERVICES getAllevamento_STR

			documento_xml_costruito_allevamento = stub_WsAziendeQry.getAllevamento_STR(codice_azienda,id_fiscale,cod_specie);			
			// RECUPERO VALORI DALLA RISPOSTA di getAllevamento_STR
			errorId				=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_errore_id  );
			errorDesc			=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_descrizione_errore  );

			val_allev_id		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_allev_id);
			val_id_fiscale		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_id_fiscale);
			val_denominazione		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_denominazione);
			val_data_inizio_attivita		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_data_inizio_attivita);
			val_data_codice_asl		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_data_codice_asl);
			val_data_codice_azienda		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_data_codice_azienda);
			val_data_codice_specie		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_data_codice_specie);
			val_data_descr_specie		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_data_descr_specie);
			val_data_proprietario		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_data_proprietario);
			val_denominazione_prop		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_denominazione_prop);
			val_data_detentore		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_data_detentore);
			val_denominazione_detentore		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_denominazione_detentore);

			val_flag_carne_latte		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_flag_carne_latte);
			val_orientamento_descrizione		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_orientamento_descrizione);
			val_tipo_prod_codice		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_tipo_prod_codice);
			val_data_fine_attivita		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_data_fine_attivita);

			val_indirizzo		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_indirizzo);
			val_cap		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_cap);
			val_codice_comune		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_codice_comune);
			val_dt_inizio_responsabilita		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_dt_inizio_responsabilita);
			val_codice_provincia		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_codice_provincia);
			val_data_inizio_validita		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_data_inizio_validita);
			val_data_fine_validita		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_data_fine_validita);
			val_note		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_note);




			infoAllReturn.setIdAllevamento(val_allev_id);
			infoAllReturn.setIdFiscaleAllevamento(val_id_fiscale);
			infoAllReturn.setDenominazione(val_denominazione);
			infoAllReturn.setDataInizioAttivita(val_data_inizio_attivita);
			infoAllReturn.setCodiceAsl(val_data_codice_asl);
			infoAllReturn.setCodiceazienda(val_data_codice_azienda);
			infoAllReturn.setCodiceSpecie(val_data_codice_specie);
			infoAllReturn.setDescrizioneSpecie(val_data_descr_specie);
			infoAllReturn.setIdFiscaleProprietario(val_data_proprietario);	
			infoAllReturn.setIdFiscaleDetentore(val_data_detentore);
			infoAllReturn.setDenominazioneDetentore(val_denominazione_detentore);
			infoAllReturn.setDenominazioneProprietario(val_denominazione_prop);
			infoAllReturn.setCodiceOrientamentoProduttivo(val_flag_carne_latte);
			infoAllReturn.setDescrizioneOrientamentoProduttivo(val_orientamento_descrizione);
			infoAllReturn.setCodiceTipoProduzione(val_tipo_prod_codice);
			infoAllReturn.setDataInizioResponsabilita(val_dt_inizio_responsabilita);
			infoAllReturn.setDataFineAttivita(val_data_fine_attivita);
			infoAllReturn.setIndirizzoAllevmanentoVia(val_indirizzo);
			infoAllReturn.setIndirizzoAllevamentoCodComune(val_codice_comune);
			infoAllReturn.setIndirizzoAllevamentoCap(val_cap);
			infoAllReturn.setIndirizzoAllevamentoSiglaProvincia(val_codice_provincia);
			infoAllReturn.setDataInizioValiditaRecord(val_data_inizio_validita);
			infoAllReturn.setDataInizioFineValiditaRecord(val_data_fine_validita);
			infoAllReturn.setNote(val_note);
			/*RECUPERO DATI AZIENDA*/
			getInfoAzienda(infoAllReturn);
			/*RECUPERO DATI PROPRIETARIO*/
			getInfoPersona(infoAllReturn, infoAllReturn.getIdFiscaleProprietario(), true);
			/*RECUPERO DATI DETENTORE*/
			getInfoPersona(infoAllReturn, infoAllReturn.getIdFiscaleDetentore(), false);

			if (!"".equals(errorId))
			{
				infoAllReturn.setCod_errore(1);
				infoAllReturn.setErrore(errorDesc);
			}
			//SETs DEL BEAN RESTITUITO



		} catch (ServiceException e) {
			infoAllReturn = new IstanzaAllevamentoBdn();
			infoAllReturn.setCod_errore(COD_ERR_ServiceException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore ServiceException : " + e );
			e.printStackTrace();
			//cpbReturn = null;
			//e.printStackTrace();
		} catch (SOAPException e) {
			infoAllReturn = new IstanzaAllevamentoBdn();
			infoAllReturn.setCod_errore(COD_ERR_SOAPException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore SOAPException : " + e );
			e.printStackTrace();
			//cpbReturn = null;
			//e.printStackTrace();
		} catch (RemoteException e) {
			infoAllReturn = new IstanzaAllevamentoBdn();
			infoAllReturn.setCod_errore(COD_ERR_RemoteException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore RemoteException : " + e );
			e.printStackTrace();
			//cpbReturn = null;
			//e.printStackTrace();
		} catch (java.lang.NullPointerException e) {
			e.printStackTrace();
			infoAllReturn = new IstanzaAllevamentoBdn();
			infoAllReturn.setCod_errore(COD_ERR_NullPointerException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore NullPointerException : " + e );
			e.printStackTrace();
			//cpbReturn = null;
			//e.printStackTrace();
		} catch (Exception e) {
			infoAllReturn = new IstanzaAllevamentoBdn();
			infoAllReturn.setCod_errore(COD_ERR_Exception);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore Exception : " + e );
			e.printStackTrace();
			//cpbReturn = null;
			//e.printStackTrace();
		}


		if ( infoAllReturn.getErrore() != null ) {
			if(infoAllReturn.getErrore().contains("CODICE AZIENDA NON PRESENTE IN ANAGRAFE")){
				infoAllReturn.setCod_errore(COD_ERR_NotInBDN);
				infoAllReturn.setErrore("[CapoParzialeBean.getCapoParzialeBean()] - AZIENDA NON PRESENTE IN ANAGRAFE");
			}
			else{
				infoAllReturn.setCod_errore(COD_ERR_NonPrevisto);
			}
		}

		return infoAllReturn;
	}


	
	


	public static IstanzaAllevamentoBdn getAllevamentoAvi(String codice_azienda,String id_fiscale,String cod_specie) {

		IstanzaAllevamentoBdn infoAllReturn = new IstanzaAllevamentoBdn();

		Logger logger = Logger.getLogger("MainLogger");

		
		SearchUnitaProduttivaResponse responseUnitaProduttiva = null ;

		UniproEndpoint stub_WsAziendeAviQry;

		int numRecord = 0;



		try {

			stub_WsAziendeAviQry = (UniproEndpoint) (new UniproEndpointService().getUniproEndpointPort());

			it.izs.bdn.anagrafica.ws.SOAPAutenticazioneWS autenticazione = new it.izs.bdn.anagrafica.ws.SOAPAutenticazioneWS();
			autenticazione.setUsername(ApplicationProperties.getProperty("USERNAME_BDAPI"));
			autenticazione.setPassword(ApplicationProperties.getProperty("PASSWORD_BDAPI"));
			autenticazione.setRuoloCodice(ApplicationProperties.getProperty("RUOLO_CODICE_BDAPI"));
			autenticazione.setRuoloValoreCodice(ApplicationProperties.getProperty("RUOLO_VALORE_BDAPI"));
			SearchUnitaProduttiva searchUnita = new SearchUnitaProduttiva();
			Unipro unita = new Unipro();
			unita.setAziendaCodice(codice_azienda);
			unita.setPropIdFiscale(id_fiscale);
			if(cod_specie.startsWith("1"))
				cod_specie=cod_specie.substring(1);
			
			unita.setSpeallCodice(cod_specie);
			searchUnita.setUnitaProduttivaSearchTO(unita);

			responseUnitaProduttiva = stub_WsAziendeAviQry.searchUnitaProduttiva(searchUnita, autenticazione);

			unita = responseUnitaProduttiva.getUnitaProduttivaTO().get(0);
			
			AziendaEndpoint endPoindSearchAziende = null ; 

			endPoindSearchAziende = (AziendaEndpoint)(new AziendaEndpointService()).getAziendaEndpointPort();
			// RECUPERO VALORI DALLA RISPOSTA di findallevamento_str

			SearchAzienda searchAzienda = new SearchAzienda();
			Azienda azienda = new Azienda();
			azienda.setCodice(codice_azienda);
			searchAzienda.setAziendaSearchTO(azienda);
			SearchAziendaResponse responseAzienda= endPoindSearchAziende.searchAzienda(searchAzienda, autenticazione);
			// RECUPERO VALORI DALLA RISPOSTA di getAllevamento_STR
			
			azienda = responseAzienda.getAziendaTO().get(0);

			SearchDettaglioAttivitaByAziendaCodice searchDet = new SearchDettaglioAttivitaByAziendaCodice();
			
			AvidetattUk det = new AvidetattUk();
			det.setAziendaCodice(codice_azienda);
			searchDet.setDettaglioAttivitaUkSearchTO(det);
			SearchDettaglioAttivitaByAziendaCodiceResponse respDet =  stub_WsAziendeAviQry.searchDettaglioAttivitaByAziendaCodice(searchDet, autenticazione);
			
			det = respDet.getDettaglioAttivitaUkTO().get(0);


			infoAllReturn.setIdAllevamento(unita.getUniproId()+"");
			infoAllReturn.setIdFiscaleAllevamento(unita.getPropIdFiscale());
			infoAllReturn.setDenominazione(unita.getDenominazione());
			infoAllReturn.setDataInizioAttivita(unita.getDtInizioAttivita()+"");
			infoAllReturn.setCodiceAsl(azienda.getAslCodice());
			infoAllReturn.setCodiceazienda(unita.getAziendaCodice());
			infoAllReturn.setCodiceSpecie("1"+det.getSpeallCodice());
			infoAllReturn.setDescrizioneSpecie(det.getSpeallDescrizione());
			infoAllReturn.setIdFiscaleProprietario(unita.getPropIdFiscale());	
			infoAllReturn.setIdFiscaleDetentore(det.getDetenIdFiscale());
			infoAllReturn.setDenominazioneDetentore(det.getDetenCognNome());
			infoAllReturn.setDenominazioneProprietario(unita.getPropCognNome());
			infoAllReturn.setCodiceOrientamentoProduttivo(det.getOriproCodice());
			infoAllReturn.setDescrizioneOrientamentoProduttivo(det.getOriproDescrizione());
			infoAllReturn.setCodiceTipoProduzione(unita.getTipattCodice());
			infoAllReturn.setDataInizioResponsabilita(unita.getDtInizioAttivita()+"");
			infoAllReturn.setDataFineAttivita(unita.getDtFineAttivita()+"");
			infoAllReturn.setIndirizzoAllevmanentoVia(azienda.getIndirizzo());
			infoAllReturn.setIndirizzoAllevamentoCodComune(azienda.getComIstat());
			infoAllReturn.setIndirizzoAllevamentoCap(azienda.getComCap());
			infoAllReturn.setIndirizzoAllevamentoSiglaProvincia(azienda.getComProSigla());
			infoAllReturn.setIdFiscaleDetentore(det.getDetenIdFiscale());
//			infoAllReturn.setDataInizioValiditaRecord(azienda.get);
//			infoAllReturn.setDataInizioFineValiditaRecord(val_data_fine_validita);
//			infoAllReturn.setNote(val_note);
			/*RECUPERO DATI AZIENDA*/
			
			infoAllReturn.setAziendaCap(azienda.getComCap());
			infoAllReturn.setAziendaCodiceAsl(azienda.getAslCodice());
			infoAllReturn.setAziendaCodiceComune(azienda.getComIstat());
			infoAllReturn.setAziendaDataApertura(azienda.getDtApertura()+"");
			infoAllReturn.setAziendaIndirizzo(azienda.getIndirizzo());
			infoAllReturn.setAziendaLatitudine(azienda.getLatitudine());
			infoAllReturn.setAziendaLongitudine(azienda.getLongitudine());
			infoAllReturn.setAziendaCodice(azienda.getCodice());
			infoAllReturn.setAziendaDataChiusura(azienda.getDtChiusura()+"");
			
			/*RECUPERO DATI PROPRIETARIO*/
			getInfoPersona(infoAllReturn, infoAllReturn.getIdFiscaleProprietario(), true);
			if(infoAllReturn.getProprietarioNominativo()==null || "".equals(infoAllReturn.getProprietarioNominativo()))
				getInfoPersonaApi(infoAllReturn, infoAllReturn.getIdFiscaleProprietario(), true);
			/*RECUPERO DATI DETENTORE*/
			getInfoPersona(infoAllReturn, infoAllReturn.getIdFiscaleDetentore(), false);
			if(infoAllReturn.getDetentoreNominativo()==null || "".equals(infoAllReturn.getDetentoreNominativo()))
				getInfoPersonaApi(infoAllReturn, infoAllReturn.getIdFiscaleDetentore(), false);
			
		
			//SETs DEL BEAN RESTITUITO



		} catch (java.lang.NullPointerException e) {
			e.printStackTrace();
			infoAllReturn = new IstanzaAllevamentoBdn();
			infoAllReturn.setCod_errore(COD_ERR_NullPointerException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore NullPointerException : " + e );
			e.printStackTrace();
			//cpbReturn = null;
			//e.printStackTrace();
		} catch (Exception e) {
			infoAllReturn = new IstanzaAllevamentoBdn();
			infoAllReturn.setCod_errore(COD_ERR_Exception);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore Exception : " + e );
			e.printStackTrace();
			//cpbReturn = null;
			//e.printStackTrace();
		}




		return infoAllReturn;
	}


	public static void getInfoAzienda(IstanzaAllevamentoBdn infoAllReturn ) {


		Logger logger = Logger.getLogger("MainLogger");

		// DEFINIZIONE CREDENZIALI DI ACCESSO AL WEBSERVICES
		String userName = ApplicationProperties.getProperty("USERNAME_WS_BDN");
		String passWord = ApplicationProperties.getProperty("PASSWORD_WS_BDN");



		String nodo_azienda_id = "AZIENDA_ID" ;
		String nodo_azienda_codice = "CODICE" ;
		String nodo_azienda_latitudine = "LONGITUDINE" ;
		String nodo_azienda_longitudine = "LATITUDINE" ;
		String nodo_azienda_cap = "CAP" ;
		String nodo_azienda_indirizzo = "INDIRIZZO" ;
		String nodo_azienda_codice_comune = "COM_CODICE" ;
		String nodo_azienda_data_apertura = "DT_APERTURA" ;
		String nodo_azienda_data_chiusura = "DT_CHIUSURA" ;

		String nodo_azienda_asl_codice = "ASL_CODICE" ;
		String nodo_azienda_data_inizio_val_record = "DT_INIVAL" ;
		String nodo_azienda_data_fine_val_record = "DT_FINVAL" ;

		String val_azienda_id = "" ;
		String val_azienda_codice = "" ;
		String val_azienda_latitudine = "" ;
		String val_azienda_longitudine = "" ;
		String val_azienda_cap = "" ;
		String val_azienda_indirizzo = "" ;
		String val_azienda_codice_comune = "" ;
		String val_azienda_data_apertura = "" ;
		String val_azienda_asl_codice = "" ;
		String val_azienda_data_inizio_val_record = "" ;
		String val_azienda_data_fine_val_record = "" ;
		String val_azienda_data_chiusura = "";


		// DEFINIZIONE DOCUMENTO RISPOSTA DA WEBSERVICES
		String documento_xml_costruito_allevamento    = null; 
		String get_capi_allevamento_response = null; 

		WsAziendeQrySoapStub stub_WsAziendeQry;
		WsAnagraficaCapoQrySoapStub stub_wsCapoQry;
		try {
			//
			stub_WsAziendeQry = (WsAziendeQrySoapStub) (new WsAziendeQryLocator().getwsAziendeQrySoap());
			//STUB PER NUMERO DEI CAPI IN ALLEVAMENTO
			stub_wsCapoQry = (WsAnagraficaCapoQrySoapStub) (new WsAnagraficaCapoQryLocator().getwsAnagraficaCapoQrySoap()); 
			// DEFINIZIONE DELL'HEADER PER L'AUTENTICAZIONE
			SOAPHeaderElement header = new SOAPHeaderElement(new QName("http://bdr.izs.it/webservices", "SOAPAutenticazione", "web"));

			SOAPElement node = header.addChildElement("username");
			node.addTextNode(userName);
			SOAPElement node2 = header.addChildElement("password");
			node2.addTextNode(passWord);

			// INSERIMENTO HEADER NEGLI STUB
			stub_WsAziendeQry.setHeader(header);
			stub_wsCapoQry.setHeader(header);
			// RECUPERO RISPOSTA WEBSERVICES getAllevamento_STR

			documento_xml_costruito_allevamento = stub_WsAziendeQry.getAzienda_STR(infoAllReturn.getCodiceazienda());
			// RECUPERO VALORI DALLA RISPOSTA di getAllevamento_STR
			val_azienda_id				=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_azienda_id  );
			val_azienda_codice				=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_azienda_codice  );
			val_azienda_latitudine				=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_azienda_latitudine  );
			val_azienda_longitudine				=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_azienda_longitudine  );
			val_azienda_cap				=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_azienda_cap  );
			val_azienda_indirizzo				=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_azienda_indirizzo  );
			val_azienda_codice_comune				=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_azienda_codice_comune  );
			val_azienda_data_apertura				=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_azienda_data_apertura  );
			val_azienda_asl_codice				=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_azienda_asl_codice  );
			val_azienda_data_inizio_val_record				=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_azienda_data_inizio_val_record  );
			val_azienda_data_fine_val_record				=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_azienda_data_fine_val_record  );
			val_azienda_data_chiusura = utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_azienda_data_chiusura  );



			infoAllReturn.setAziendaCap(val_azienda_cap);
			infoAllReturn.setAziendaCodiceAsl(val_azienda_asl_codice);
			infoAllReturn.setAziendaCodiceComune(val_azienda_codice_comune);
			infoAllReturn.setAziendaDataApertura(val_azienda_data_apertura);
			infoAllReturn.setAziendaDataFineValiditaRecord(val_azienda_data_fine_val_record);
			infoAllReturn.setAziendaDataInizioValiditaRecord(val_azienda_data_inizio_val_record);
			infoAllReturn.setAziendaIndirizzo(val_azienda_indirizzo);
			infoAllReturn.setAziendaLatitudine(val_azienda_latitudine);
			infoAllReturn.setAziendaLongitudine(val_azienda_longitudine);
			infoAllReturn.setAziendaCodice(val_azienda_codice);
			infoAllReturn.setAziendaDataChiusura(val_azienda_data_chiusura);




		} catch (ServiceException e) {
			infoAllReturn = new IstanzaAllevamentoBdn();
			infoAllReturn.setCod_errore(COD_ERR_ServiceException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore ServiceException : " + e );
			e.printStackTrace();
			//cpbReturn = null;
			//e.printStackTrace();
		} catch (SOAPException e) {
			infoAllReturn = new IstanzaAllevamentoBdn();
			infoAllReturn.setCod_errore(COD_ERR_SOAPException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore SOAPException : " + e );
			e.printStackTrace();
			//cpbReturn = null;
			//e.printStackTrace();
		} catch (RemoteException e) {
			infoAllReturn = new IstanzaAllevamentoBdn();
			infoAllReturn.setCod_errore(COD_ERR_RemoteException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore RemoteException : " + e );
			e.printStackTrace();
			//cpbReturn = null;
			//e.printStackTrace();
		} catch (java.lang.NullPointerException e) {
			e.printStackTrace();
			infoAllReturn = new IstanzaAllevamentoBdn();
			infoAllReturn.setCod_errore(COD_ERR_NullPointerException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore NullPointerException : " + e );
			e.printStackTrace();
			//cpbReturn = null;
			//e.printStackTrace();
		} catch (Exception e) {
			infoAllReturn = new IstanzaAllevamentoBdn();
			infoAllReturn.setCod_errore(COD_ERR_Exception);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore Exception : " + e );
			e.printStackTrace();
			//cpbReturn = null;
			//e.printStackTrace();
		}


		if ( infoAllReturn.getErrore() != null ) {
			if(infoAllReturn.getErrore().contains("CODICE AZIENDA NON PRESENTE IN ANAGRAFE")){
				infoAllReturn.setCod_errore(COD_ERR_NotInBDN);
				infoAllReturn.setErrore("[CapoParzialeBean.getCapoParzialeBean()] - AZIENDA NON PRESENTE IN ANAGRAFE");
			}
			else{
				infoAllReturn.setCod_errore(COD_ERR_NonPrevisto);
			}
		}

	}
	
	
	public static void getInfoPersonaApi(IstanzaAllevamentoBdn infoAllReturn,String idFiscalePersona,boolean flagProprietario ) throws BusinessWsException_Exception {



			WsApiAnagraficaPersoneBdn p = new WsApiAnagraficaPersoneBdn();
			 Persone  pp = p.searchPersone(idFiscalePersona);

			if(flagProprietario)
			{
				infoAllReturn.setProprietarioIndirizzoCap(pp.getCap());
				infoAllReturn.setProprietarioIndirizzoCodiceComune(pp.getComIstat());
				infoAllReturn.setProprietarioNominativo(pp.getNome()+" "+pp.getCognNome());
				infoAllReturn.setProprietarioIndirizzoVia(pp.getIndirizzo());
				infoAllReturn.setProprietarioIdFiscale(pp.getIdFiscale());

			}
			else
			{
				infoAllReturn.setDetentoreIndirizzoCap(pp.getCap());
				infoAllReturn.setDetentoreIndirizzoCodiceComune(pp.getComIstat());
				infoAllReturn.setDetentoreNominativo(pp.getNome()+" "+pp.getCognNome());
				infoAllReturn.setDetentoreIndirizzoVia(pp.getIndirizzo());
				infoAllReturn.setDetentoreIdFiscale(pp.getIdFiscale());
			}







		
	}


	public static void getInfoPersona(IstanzaAllevamentoBdn infoAllReturn,String idFiscalePersona,boolean flagProprietario ) {


		Logger logger = Logger.getLogger("MainLogger");

		// DEFINIZIONE CREDENZIALI DI ACCESSO AL WEBSERVICES
		String userName = ApplicationProperties.getProperty("USERNAME_WS_BDN");
		String passWord = ApplicationProperties.getProperty("PASSWORD_WS_BDN");



		String nodo_persona_id_fiscale = "ID_FISCALE" ;
		String nodo_persona_nominativo = "COGN_NOME" ;
		String nodo_persona_indirizzo = "INDIRIZZO" ;
		String nodo_persona_cap = "CAP" ;
		String nodo_persona_codice_comune = "COM_CODICE" ;


		String val_persona_id_fiscale = "" ;
		String val_persona_nominativo = "" ;
		String val_persona_indirizzo = "" ;
		String val_persona_cap = "" ;
		String val_persona_codice_comune = "" ;



		// DEFINIZIONE DOCUMENTO RISPOSTA DA WEBSERVICES
		String documento_xml_costruito_allevamento    = null; 
		String get_capi_allevamento_response = null; 

		WsAziendeQrySoapStub stub_WsAziendeQry;
		WsAnagraficaCapoQrySoapStub stub_wsCapoQry;
		try {
			//
			stub_WsAziendeQry = (WsAziendeQrySoapStub) (new WsAziendeQryLocator().getwsAziendeQrySoap());
			//STUB PER NUMERO DEI CAPI IN ALLEVAMENTO
			stub_wsCapoQry = (WsAnagraficaCapoQrySoapStub) (new WsAnagraficaCapoQryLocator().getwsAnagraficaCapoQrySoap()); 
			// DEFINIZIONE DELL'HEADER PER L'AUTENTICAZIONE
			SOAPHeaderElement header = new SOAPHeaderElement(new QName("http://bdr.izs.it/webservices", "SOAPAutenticazione", "web"));

			SOAPElement node = header.addChildElement("username");
			node.addTextNode(userName);
			SOAPElement node2 = header.addChildElement("password");
			node2.addTextNode(passWord);

			// INSERIMENTO HEADER NEGLI STUB
			stub_WsAziendeQry.setHeader(header);
			stub_wsCapoQry.setHeader(header);
			// RECUPERO RISPOSTA WEBSERVICES getAllevamento_STR

			documento_xml_costruito_allevamento = stub_WsAziendeQry.getPersona_STR(idFiscalePersona);
			// RECUPERO VALORI DALLA RISPOSTA di getAllevamento_STR
			val_persona_id_fiscale				=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_persona_id_fiscale  );
			val_persona_nominativo				=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_persona_nominativo  );
			val_persona_indirizzo				=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_persona_indirizzo  );
			val_persona_cap				=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_persona_cap  );
			val_persona_codice_comune				=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_persona_codice_comune  );

			if(flagProprietario)
			{
				infoAllReturn.setProprietarioIndirizzoCap(val_persona_cap);
				infoAllReturn.setProprietarioIndirizzoCodiceComune(val_persona_codice_comune);
				infoAllReturn.setProprietarioNominativo(val_persona_nominativo);
				infoAllReturn.setProprietarioIndirizzoVia(val_persona_indirizzo);
				infoAllReturn.setProprietarioIdFiscale(val_persona_id_fiscale);

			}
			else
			{
				infoAllReturn.setDetentoreIndirizzoCap(val_persona_cap);
				infoAllReturn.setDetentoreIndirizzoCodiceComune(val_persona_codice_comune);
				infoAllReturn.setDetentoreNominativo(val_persona_nominativo);
				infoAllReturn.setDetentoreIndirizzoVia(val_persona_indirizzo);
				infoAllReturn.setDetentoreIdFiscale(val_persona_id_fiscale);
			}







		} catch (ServiceException e) {
			infoAllReturn = new IstanzaAllevamentoBdn();
			infoAllReturn.setCod_errore(COD_ERR_ServiceException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore ServiceException : " + e );
			e.printStackTrace();
			//cpbReturn = null;
			//e.printStackTrace();
		} catch (SOAPException e) {
			infoAllReturn = new IstanzaAllevamentoBdn();
			infoAllReturn.setCod_errore(COD_ERR_SOAPException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore SOAPException : " + e );
			e.printStackTrace();
			//cpbReturn = null;
			//e.printStackTrace();
		} catch (RemoteException e) {
			infoAllReturn = new IstanzaAllevamentoBdn();
			infoAllReturn.setCod_errore(COD_ERR_RemoteException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore RemoteException : " + e );
			e.printStackTrace();
			//cpbReturn = null;
			//e.printStackTrace();
		} catch (java.lang.NullPointerException e) {
			e.printStackTrace();
			infoAllReturn = new IstanzaAllevamentoBdn();
			infoAllReturn.setCod_errore(COD_ERR_NullPointerException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore NullPointerException : " + e );
			e.printStackTrace();
			//cpbReturn = null;
			//e.printStackTrace();
		} catch (Exception e) {
			infoAllReturn = new IstanzaAllevamentoBdn();
			infoAllReturn.setCod_errore(COD_ERR_Exception);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore Exception : " + e );
			e.printStackTrace();
			//cpbReturn = null;
			//e.printStackTrace();
		}


		if ( infoAllReturn.getErrore() != null ) {
			if(infoAllReturn.getErrore().contains("CODICE AZIENDA NON PRESENTE IN ANAGRAFE")){
				infoAllReturn.setCod_errore(COD_ERR_NotInBDN);
				infoAllReturn.setErrore("[CapoParzialeBean.getCapoParzialeBean()] - AZIENDA NON PRESENTE IN ANAGRAFE");
			}
			else{
				infoAllReturn.setCod_errore(COD_ERR_NonPrevisto);
			}
		}

	}









}

