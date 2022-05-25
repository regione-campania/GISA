/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.izs.bdn.bean;

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

import it.izs.apicoltura.apianagraficaattivita.ws.Api;
import it.izs.bdn.action.utilsXML;
import it.izs.bdn.webservices.GetAziendaResponseGetAziendaResult;
import it.izs.bdn.webservices.Get_Capi_AllevamentoResponseGet_Capi_AllevamentoResult;
import it.izs.bdn.webservices.WsAnagraficaCapoQryLocator;
import it.izs.bdn.webservices.WsAnagraficaCapoQrySoapStub;
import it.izs.bdn.webservices.WsAziendeQryLocator;
import it.izs.bdn.webservices.WsAziendeQrySoapStub;
import it.izs.ws.WsPost;
import javax.servlet.ServletContext;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

import org.apache.axis.message.SOAPHeaderElement;
import org.aspcfs.modules.campioni.base.SpecieAnimali;
import org.aspcfs.modules.gestoriacquenew.base.EccezioneDati;
import org.aspcfs.modules.opu.base.RicercheAnagraficheTab;
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
public class InfoAllevamentoBean {

	private final static int COD_ERR_NotInBDN     		  =  1;
	private final static int COD_ERR_ServiceException     = -1;
	private final static int COD_ERR_SOAPException        = -2;
	private final static int COD_ERR_RemoteException      = -3;
	private final static int COD_ERR_NullPointerException = -4;
	private final static int COD_ERR_Exception            = -5;
	private final static int COD_ERR_NonPrevisto          = -6;
	private String cod_specie ;
	private String tipoProduzione ;
	private String codice_azienda       ;
	private String cod_fiscale			;
	private String cod_fiscale_det			;

	private String id_allevamento		;
	private String denominazione		;
	private String telefono				;
	private String note					;

	private String tipo_produzione;
	private String  orientamento_prod ;
	private String aslCodice ;
	private String data_inizio_attivita ;
	private String data_fine_attivita	;

	private String codice_specie        ;
	private String indirizzo            ;
	private String comune               ;
	private String descComune               ;
	private String codComune               ;
	private String cap               ;
	private String provincia			;

	private String flag_carne_latte;
	private String flag_tipo_all ;
	private String num_capi_totali = "0" 		;

	private String errore	            ;
	private int cod_errore				;

	private String nome_proprietario = "" ;
	private String codice_errore = "" ;

	private String codiceTipoAllevamento ;
	private String descrizioneTipoAllevamento ;
	private String codFiscaleProp ;
	private String denominazione_asl ;
	private String latitudine;
	private String longitudine;
	
	
	private int modifiedBy;

	
	public String getDenominazione_asl() {
		return denominazione_asl;
	}
	public void setDenominazione_asl(String denominazione_asl) {
		this.denominazione_asl = denominazione_asl;
	}
	public String getCodFiscaleProp() {
		return codFiscaleProp;
	}
	public void setCodFiscaleProp(String codFiscaleProp) {
		this.codFiscaleProp = codFiscaleProp;
	}
	public String getDescrizioneTipoAllevamento() {
		return descrizioneTipoAllevamento;
	}
	public void setDescrizioneTipoAllevamento(String descrizioneTipoAllevamento) {
		this.descrizioneTipoAllevamento = descrizioneTipoAllevamento;
	}
	public String getCodiceTipoAllevamento() {
		return codiceTipoAllevamento;
	}
	public void setCodiceTipoAllevamento(String codiceTipoAllevamento) {
		this.codiceTipoAllevamento = codiceTipoAllevamento;
	}
	public String getCodice_errore() {
		return codice_errore;
	}
	public void setCodice_errore(String codice_errore) {
		this.codice_errore = codice_errore;
	}
	public String getNome_proprietario() {
		return nome_proprietario;
	}
	public void setNome_proprietario(String nome_proprietario) {
		this.nome_proprietario = nome_proprietario;
	}
	public String getCod_fiscale_det() {
		return cod_fiscale_det;
	}
	public void setCod_fiscale_det(String cod_fiscale_det) {
		this.cod_fiscale_det = cod_fiscale_det;
	}
	public String getTipo_produzione() {
		return tipo_produzione;
	}
	public void setTipo_produzione(String tipo_produzione) {
		this.tipo_produzione = tipo_produzione;
	}
	public String getOrientamento_prod() {
		return orientamento_prod;
	}
	public void setOrientamento_prod(String orientamento_prod) {
		this.orientamento_prod = orientamento_prod;
	}
	public String getTipoProduzione() {
		return tipoProduzione;
	}
	public void setTipoProduzione(String tipoProduzione) {
		this.tipoProduzione = tipoProduzione;
	}
	public String getAslCodice() {
		return aslCodice;
	}
	public void setAslCodice(String aslCodice) {
		this.aslCodice = aslCodice;
	}
	public String getFlag_carne_latte() {
		return flag_carne_latte;
	}
	public void setFlag_carne_latte(String flag_carne_latte) {
		this.flag_carne_latte = flag_carne_latte;
	}
	public String getFlag_tipo_all() {
		return flag_tipo_all;
	}
	public void setFlag_tipo_all(String flag_tipo_all) {
		this.flag_tipo_all = flag_tipo_all;
	}
	public String getCodice_azienda() {
		return codice_azienda;
	}
	public void setCodice_azienda(String codiceAzienda) {
		codice_azienda = codiceAzienda;
	}
	public String getCod_fiscale() {
		return cod_fiscale;
	}
	public void setCod_fiscale(String codFiscale) {
		cod_fiscale = codFiscale;
	}
	public String getId_allevamento() {
		return id_allevamento;
	}
	public void setId_allevamento(String idAllevamento) {
		id_allevamento = idAllevamento;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominaz) {
		denominazione = denominaz;
	}

	public String getCodice_specie() {
		return codice_specie;
	}
	public void setCodice_specie(String codice_specie) {
		this.codice_specie = codice_specie;
	}
	public String getData_inizio_attivita() {
		return data_inizio_attivita;
	}
	public void setData_inizio_attivita(String dataInizioAttivita) {
		data_inizio_attivita = dataInizioAttivita;
	}
	public String getData_fine_attivita() {
		return data_fine_attivita;
	}
	public void setData_fine_attivita(String dataFineAttivita) {
		data_fine_attivita = dataFineAttivita;
	}
	public String getSpecie_allevata() {
		return codice_specie;
	}
	public void setSpecie_allevata(String codiceSpecie) {
		codice_specie = codiceSpecie;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getDescComune() {
		return descComune;
	}
	public void setDescComune(String descComune) {
		this.descComune = descComune;
	}
	public String getCodComune() {
		return codComune;
	}
	public void setCodComune(String codComune) {
		this.codComune = codComune;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getLatitudine() {
		return latitudine;
	}
	public void setLatitudine(String latitudine) {
		this.latitudine = latitudine;
	}
	public String getLongitudine() {
		return longitudine;
	}
	public void setLongitudine(String longitudine) {
		this.longitudine = longitudine;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNum_capi_totali() {
		return num_capi_totali;
	}
	public void setNum_capi_totali(String num_capi_totali) {
		this.num_capi_totali = num_capi_totali;
	}	
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
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
	public static InfoAllevamentoBean getInfoAllevamentoBean(String codice_azienda,String id_fiscale,String cod_specie) {

		System.out.println( codice_azienda + " " + id_fiscale + " " + cod_specie);
		InfoAllevamentoBean infoAllReturn = new InfoAllevamentoBean();

		Logger logger = Logger.getLogger("MainLogger");

		// DEFINIZIONE CREDENZIALI DI ACCESSO AL WEBSERVICES
		String userName = ApplicationProperties.getProperty("USERNAME_WS_BDN");
		String passWord = ApplicationProperties.getProperty("PASSWORD_WS_BDN");


		String nodo_errore_id 			= "id";
		String nodo_descrizione_errore 			= "des";

		String nodo_allevamento_denominazione_asl			= "DENOMINAZIONE_ASL";

		// DEFINIZIONE NODI DA RECUPERARE PER L'ALLEVAMENTO //
		String nodo_allevamento_cod_fiscale 			= "PROP_ID_FISCALE";
		String nodo_allevamento_cod_fiscale_det 			= "DETEN_ID_FISCALE" ;

		String nodo_allevamento_azienda_codice 			= "AZIENDA_CODICE";
		String nodo_allevamento_id_allevamento			= "ALLEV_ID";		
		String nodo_allevamento_denominazione			= "DENOMINAZIONE";
		String nodo_allevamento_indirizzo				= "INDIRIZZO";
		//String nodo_allevamento_telefono				= "TELEFONO";
		//String nodo_allevamento_note					= "NOTE";
		String nodo_allevamento_data_inizio_attivita	= "DT_INIZIO_ATTIVITA";
		String nodo_allevamento_data_fine_attivita		= "DT_FINE_ATTIVITA";

		String nodo_allevamento_comune_codice  			= "COM_CODICE";
		String nodo_allevamento_provincia_codice  		= "PRO_CODICE";
		
		String nodo_tipo_allev_codice			 		= "TIPO_ALLEV_COD";

		//String nodo_allevamento_errore 	  				= "error_info";
		String nodo_numero_capi			  				= "CAPI_PRESENTI";


		String nodo_tipo_prod_codice					= "TIP_PROD_CODICE" ; // ALLEVAMENTO
		String nodo_allevamento_codice_specie 			= "SPE_CODICE";
		String nodo_flag_carne_latte					= "FLAG_CARNE_LATTE";

		String nodo_allevamento_id_fiscale				= "PROP_ID_FISCALE";		



		String nodo_azienda_asl_codice	   				= "ASL_CODICE";
		String nodo_allevamento_det_cod_fiscale 		= "DETEN_ID_FISCALE";
		//String nodo_cap									= "CAP" ;
		String nodo_data_inizio_resp					= "DT_INIZIO_RESPONSABILITA" ;




		// DEFINIZIONE VALORI DA RECUPERARE PER L'ALLEVAMENTO //
		String valori_allevamento_cod_fiscale 			= null;
		String valori_allevamento_azienda_codice 		= null;
		String valori_allevamento_id_allevamento		= null;
		String valori_allevamento_denominazione			= null;
		String valori_allevamento_indirizzo				= null;
		String valori_allevamento_provincia_codice		= null;
		String valori_allevamento_cod_fiscale_det		= null ;
		//String valori_allevamento_telefono				= null;
		//String valori_allevamento_note					= null;
		String valori_allevamento_comune_codice			= null;
		String valori_allevamento_data_inizio_attivita	= null;
		String valori_allevamento_data_fine_attivita	= null;
		String valori_allevamento_codice_specie 		= null;
		//String valori_allevamento_errore	   			= null;
		String valori_numero_capi						= null;
		String valori_capi_errore						= null;
		String valori_flag_carne_latte					= null;
		String valori_tipo_prod_codice					= null;
		String valori_allevamento_tipo_prod_codice 		= null ;
		String valori_nodo_azienda_asl_codice 			=null ;
		String valori_det_cod_fiscale 			= null ;
		//String valori_cap = null ;
		String valori_data_inizio_resp = null ; 
		String errorId =null;
		String errorDesc = null ;
		// DEFINIZIONE NODI DA RECUPERARE PER L'AZIENDA //
		String valori_azienda_asl_codice   = null;
		String val_denominazione_asl = "" ;
		String val_tipo_allevamento = "" ;

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
			System.out.println("documento_xml_costruito_allevamento: " + documento_xml_costruito_allevamento);
			// RECUPERO VALORI DALLA RISPOSTA di getAllevamento_STR
			errorId			=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_errore_id  );
			errorDesc			=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_descrizione_errore  );

			
			val_denominazione_asl 	=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_denominazione_asl );

			valori_allevamento_cod_fiscale			=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_allevamento_id_fiscale  );
			valori_allevamento_azienda_codice		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_allevamento_azienda_codice );
			valori_allevamento_id_allevamento		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_allevamento_id_allevamento );
			valori_allevamento_denominazione		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_denominazione );
			valori_allevamento_indirizzo			=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_indirizzo );
			try{
			valori_allevamento_comune_codice		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_comune_codice);
			}catch(Exception e){}
			valori_allevamento_provincia_codice 	=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_provincia_codice);
			valori_allevamento_cod_fiscale_det 		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_allevamento_cod_fiscale_det);
			//valori_allevamento_telefono				=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_telefono);
			//valori_allevamento_note					=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_note );	
			valori_allevamento_data_inizio_attivita	=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_data_inizio_attivita);
			valori_allevamento_data_fine_attivita	=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_data_fine_attivita);
			valori_allevamento_codice_specie 		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_codice_specie);
			//valori_allevamento_errore    			=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_errore);
			valori_allevamento_tipo_prod_codice    	=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_tipo_prod_codice);
			valori_nodo_azienda_asl_codice    		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_azienda_asl_codice);

			valori_det_cod_fiscale    				=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_det_cod_fiscale);
			//valori_cap    							=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_cap);
			valori_data_inizio_resp    				=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_data_inizio_resp);

			valori_flag_carne_latte					=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_flag_carne_latte);
			valori_tipo_prod_codice					=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_tipo_prod_codice);
			
			val_tipo_allevamento                    =   utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,  nodo_tipo_allev_codice );

			infoAllReturn.setCodice_errore(errorId);
			if (!"".equals(errorId))
			{
				infoAllReturn.setCod_errore(1);
				infoAllReturn.setErrore(errorDesc);
			}
			//SETs DEL BEAN RESTITUITO
			infoAllReturn.setCodice_azienda(valori_allevamento_azienda_codice);
			infoAllReturn.setCod_fiscale(valori_allevamento_cod_fiscale);
			infoAllReturn.setCod_fiscale_det(valori_allevamento_cod_fiscale_det);

			
			infoAllReturn.setDenominazione_asl(val_denominazione_asl);
			infoAllReturn.setId_allevamento(valori_allevamento_id_allevamento);
			infoAllReturn.getidAsl();
			infoAllReturn.setDenominazione(valori_allevamento_denominazione);
			infoAllReturn.setComune(valori_allevamento_comune_codice);
			//infoAllReturn.setTelefono(valori_allevamento_telefono);
			infoAllReturn.setProvincia(valori_allevamento_provincia_codice);
			infoAllReturn.setIndirizzo(valori_allevamento_indirizzo);
			//infoAllReturn.setNote(valori_allevamento_note);
			infoAllReturn.setTipoProduzione(valori_allevamento_tipo_prod_codice);

			infoAllReturn.setSpecie_allevata(valori_allevamento_codice_specie);
			infoAllReturn.setData_inizio_attivita(valori_allevamento_data_inizio_attivita);
			infoAllReturn.setData_fine_attivita(valori_allevamento_data_fine_attivita);
			infoAllReturn.setFlag_carne_latte(valori_flag_carne_latte);
			infoAllReturn.setFlag_tipo_all(valori_tipo_prod_codice);

			infoAllReturn.setAslCodice(valori_nodo_azienda_asl_codice);
			System.out.println("val_tipo_allevamento: " + val_tipo_allevamento);
			infoAllReturn.setCodiceTipoAllevamento(val_tipo_allevamento);


			//RECUPERO RISPOSTA WEBSERVICES get_Capi_Allevamento_STR PER NUMERO DI CAPI

			if( "0121".equals(infoAllReturn.getSpecie_allevata()) ||  "0129".equals(infoAllReturn.getSpecie_allevata()))
			{
				get_capi_allevamento_response = stub_wsCapoQry.getInfoCapiAllevamento_STR(infoAllReturn.getId_allevamento());
				valori_numero_capi = utilsXML.getValoreNodoXML(get_capi_allevamento_response, nodo_numero_capi);

				//			valori_capi_errore = utilsXML.getValoreNodoXML(get_capi_allevamento_response, nodo_allevamento_errore);
				//SETTO IL NUMERO DEI CAPI DELL'ALLEVAMENTO NEL BEAN
				infoAllReturn.setNum_capi_totali(valori_numero_capi);
			}

			//if (valori_allevamento_errore!=null ) {				
			//if (!valori_allevamento_errore.equals("1") ) 			
			//infoAllReturn.setErrore(valori_allevamento_errore);
			//else
			//infoAllReturn.setErrore(null);
			//}

		} catch (ServiceException e) {
			infoAllReturn = new InfoAllevamentoBean();
			infoAllReturn.setCod_errore(COD_ERR_ServiceException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore ServiceException : " + e );
			e.printStackTrace();
			//cpbReturn = null;
			//e.printStackTrace();
		} catch (SOAPException e) {
			infoAllReturn = new InfoAllevamentoBean();
			infoAllReturn.setCod_errore(COD_ERR_SOAPException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore SOAPException : " + e );
			e.printStackTrace();
			//cpbReturn = null;
			//e.printStackTrace();
		} catch (RemoteException e) {
			infoAllReturn = new InfoAllevamentoBean();
			infoAllReturn.setCod_errore(COD_ERR_RemoteException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore RemoteException : " + e );
			e.printStackTrace();
			//cpbReturn = null;
			//e.printStackTrace();
		} catch (java.lang.NullPointerException e) {
			e.printStackTrace();
			infoAllReturn = new InfoAllevamentoBean();
			infoAllReturn.setCod_errore(COD_ERR_NullPointerException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore NullPointerException : " + e );
			e.printStackTrace();
			//cpbReturn = null;
			//e.printStackTrace();
		} catch (Exception e) {
			infoAllReturn = new InfoAllevamentoBean();
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
	
	public static InfoAllevamentoBean getInfoAllevamento(String allevId) {

		System.out.println( "allev_id " + allevId );
		InfoAllevamentoBean infoAllReturn = new InfoAllevamentoBean();

		Logger logger = Logger.getLogger("MainLogger");

		// DEFINIZIONE CREDENZIALI DI ACCESSO AL WEBSERVICES
		String userName = ApplicationProperties.getProperty("USERNAME_WS_BDN");
		String passWord = ApplicationProperties.getProperty("PASSWORD_WS_BDN");


		String nodo_errore_id 			= "id";
		String nodo_descrizione_errore 			= "des";
		
		
		String nodo_allevamento_denominazione_asl			= "DENOMINAZIONE_ASL";

		// DEFINIZIONE NODI DA RECUPERARE PER L'ALLEVAMENTO //
		String nodo_allevamento_cod_fiscale 			= "PROP_ID_FISCALE";
		String nodo_allevamento_cod_fiscale_det 			= "DETEN_ID_FISCALE" ;

		String nodo_allevamento_azienda_codice 			= "AZIENDA_CODICE";
		String nodo_allevamento_id_allevamento			= "ALLEV_ID";		
		String nodo_allevamento_denominazione			= "DENOMINAZIONE";
		String nodo_allevamento_indirizzo				= "INDIRIZZO";
		//String nodo_allevamento_telefono				= "TELEFONO";
		//String nodo_allevamento_note					= "NOTE";
		String nodo_allevamento_data_inizio_attivita	= "DT_INIZIO_ATTIVITA";
		String nodo_allevamento_data_fine_attivita		= "DT_FINE_ATTIVITA";

		String nodo_allevamento_comune_codice  			= "COM_CODICE";
		String nodo_allevamento_provincia_codice  		= "PRO_CODICE";
		
		String nodo_tipo_allev_codice			 		= "TIPO_ALLEV_COD";

		//String nodo_allevamento_errore 	  				= "error_info";
		String nodo_numero_capi			  				= "CAPI_PRESENTI";


		String nodo_tipo_prod_codice					= "TIP_PROD_CODICE" ; // ALLEVAMENTO
		String nodo_allevamento_codice_specie 			= "SPE_CODICE";
		String nodo_flag_carne_latte					= "FLAG_CARNE_LATTE";

		String nodo_allevamento_id_fiscale				= "PROP_ID_FISCALE";		



		String nodo_azienda_asl_codice	   				= "ASL_CODICE";
		String nodo_allevamento_det_cod_fiscale 		= "DETEN_ID_FISCALE";
		//String nodo_cap									= "CAP" ;
		String nodo_data_inizio_resp					= "DT_INIZIO_RESPONSABILITA" ;




		// DEFINIZIONE VALORI DA RECUPERARE PER L'ALLEVAMENTO //
		String valori_allevamento_cod_fiscale 			= null;
		String valori_allevamento_azienda_codice 		= null;
		String valori_allevamento_id_allevamento		= null;
		String valori_allevamento_denominazione			= null;
		String valori_allevamento_indirizzo				= null;
		String valori_allevamento_provincia_codice		= null;
		String valori_allevamento_cod_fiscale_det		= null ;
		//String valori_allevamento_telefono				= null;
		//String valori_allevamento_note					= null;
		//String valori_allevamento_comune_codice			= null;
		String valori_allevamento_data_inizio_attivita	= null;
		String valori_allevamento_data_fine_attivita	= null;
		String valori_allevamento_codice_specie 		= null;
		//String valori_allevamento_errore	   			= null;
		String valori_numero_capi						= null;
		String valori_capi_errore						= null;
		String valori_flag_carne_latte					= null;
		String valori_tipo_prod_codice					= null;
		String valori_allevamento_tipo_prod_codice 		= null ;
		String valori_nodo_azienda_asl_codice 			=null ;
		String valori_det_cod_fiscale 			= null ;
		//String valori_cap = null ;
		String valori_data_inizio_resp = null ; 
		String errorId =null;
		String errorDesc = null ;
		// DEFINIZIONE NODI DA RECUPERARE PER L'AZIENDA //
		String valori_azienda_asl_codice   = null;
		String val_denominazione_asl = "" ;
		String val_tipo_allevamento = "" ;

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
			
			documento_xml_costruito_allevamento = stub_WsAziendeQry.getInfoAllevamento_STR(allevId);
			System.out.println("documento_xml_costruito_allevamento: " + documento_xml_costruito_allevamento);
			// RECUPERO VALORI DALLA RISPOSTA di getAllevamento_STR
			errorId			=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_errore_id  );
			errorDesc			=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_descrizione_errore  );

			
			val_denominazione_asl 	=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_denominazione_asl );

			valori_allevamento_cod_fiscale			=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_allevamento_id_fiscale  );
			valori_allevamento_azienda_codice		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_allevamento_azienda_codice );
			valori_allevamento_id_allevamento		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_allevamento_id_allevamento );
			valori_allevamento_denominazione		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_denominazione );
			valori_allevamento_indirizzo			=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_indirizzo );
			//valori_allevamento_comune_codice		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_comune_codice);
			valori_allevamento_provincia_codice 	=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_provincia_codice);
			valori_allevamento_cod_fiscale_det 		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_allevamento_cod_fiscale_det);
			//valori_allevamento_telefono				=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_telefono);
			//valori_allevamento_note					=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_note );	
			valori_allevamento_data_inizio_attivita	=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_data_inizio_attivita);
			valori_allevamento_data_fine_attivita	=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_data_fine_attivita);
			valori_allevamento_codice_specie 		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_codice_specie);
			//valori_allevamento_errore    			=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_errore);
			valori_allevamento_tipo_prod_codice    	=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_tipo_prod_codice);
			valori_nodo_azienda_asl_codice    		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_azienda_asl_codice);

			valori_det_cod_fiscale    				=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_det_cod_fiscale);
			//valori_cap    							=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_cap);
			valori_data_inizio_resp    				=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_data_inizio_resp);

			valori_flag_carne_latte					=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_flag_carne_latte);
			valori_tipo_prod_codice					=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_tipo_prod_codice);
			
			val_tipo_allevamento                    =   utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,  nodo_tipo_allev_codice );

			infoAllReturn.setCodice_errore(errorId);
			if (!"".equals(errorId))
			{
				infoAllReturn.setCod_errore(1);
				infoAllReturn.setErrore(errorDesc);
			}
			//SETs DEL BEAN RESTITUITO
			infoAllReturn.setCodice_azienda(valori_allevamento_azienda_codice);
			infoAllReturn.setCod_fiscale(valori_allevamento_cod_fiscale);
			infoAllReturn.setCod_fiscale_det(valori_allevamento_cod_fiscale_det);

			
			infoAllReturn.setDenominazione_asl(val_denominazione_asl);
			infoAllReturn.setId_allevamento(valori_allevamento_id_allevamento);
			infoAllReturn.getidAsl();
			infoAllReturn.setDenominazione(valori_allevamento_denominazione);
			//infoAllReturn.setComune(valori_allevamento_comune_codice);
			//infoAllReturn.setTelefono(valori_allevamento_telefono);
			infoAllReturn.setProvincia(valori_allevamento_provincia_codice);
			infoAllReturn.setIndirizzo(valori_allevamento_indirizzo);
			//infoAllReturn.setNote(valori_allevamento_note);
			infoAllReturn.setTipoProduzione(valori_allevamento_tipo_prod_codice);

			infoAllReturn.setSpecie_allevata(valori_allevamento_codice_specie);
			infoAllReturn.setData_inizio_attivita(valori_allevamento_data_inizio_attivita);
			infoAllReturn.setData_fine_attivita(valori_allevamento_data_fine_attivita);
			infoAllReturn.setFlag_carne_latte(valori_flag_carne_latte);
			infoAllReturn.setFlag_tipo_all(valori_tipo_prod_codice);

			infoAllReturn.setAslCodice(valori_nodo_azienda_asl_codice);
			System.out.println("val_tipo_allevamento: " + val_tipo_allevamento);
			infoAllReturn.setCodiceTipoAllevamento(val_tipo_allevamento);


			//RECUPERO RISPOSTA WEBSERVICES get_Capi_Allevamento_STR PER NUMERO DI CAPI

			if( "0121".equals(infoAllReturn.getSpecie_allevata()) ||  "0129".equals(infoAllReturn.getSpecie_allevata()))
			{
				get_capi_allevamento_response = stub_wsCapoQry.getInfoCapiAllevamento_STR(infoAllReturn.getId_allevamento());
				valori_numero_capi = utilsXML.getValoreNodoXML(get_capi_allevamento_response, nodo_numero_capi);

				//			valori_capi_errore = utilsXML.getValoreNodoXML(get_capi_allevamento_response, nodo_allevamento_errore);
				//SETTO IL NUMERO DEI CAPI DELL'ALLEVAMENTO NEL BEAN
				infoAllReturn.setNum_capi_totali(valori_numero_capi);
			}

			//if (valori_allevamento_errore!=null ) {				
			//if (!valori_allevamento_errore.equals("1") ) 			
			//infoAllReturn.setErrore(valori_allevamento_errore);
			//else
			//infoAllReturn.setErrore(null);
			//}

		} catch (ServiceException e) {
			infoAllReturn = new InfoAllevamentoBean();
			infoAllReturn.setCod_errore(COD_ERR_ServiceException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore ServiceException : " + e );
			e.printStackTrace();
			//cpbReturn = null;
			//e.printStackTrace();
		} catch (SOAPException e) {
			infoAllReturn = new InfoAllevamentoBean();
			infoAllReturn.setCod_errore(COD_ERR_SOAPException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore SOAPException : " + e );
			e.printStackTrace();
			//cpbReturn = null;
			//e.printStackTrace();
		} catch (RemoteException e) {
			infoAllReturn = new InfoAllevamentoBean();
			infoAllReturn.setCod_errore(COD_ERR_RemoteException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore RemoteException : " + e );
			e.printStackTrace();
			//cpbReturn = null;
			//e.printStackTrace();
		} catch (java.lang.NullPointerException e) {
			e.printStackTrace();
			infoAllReturn = new InfoAllevamentoBean();
			infoAllReturn.setCod_errore(COD_ERR_NullPointerException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore NullPointerException : " + e );
			e.printStackTrace();
			//cpbReturn = null;
			//e.printStackTrace();
		} catch (Exception e) {
			infoAllReturn = new InfoAllevamentoBean();
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
	
	
	
	
	
	public static InfoAllevamentoBean getInfoAllevamento(String allevId, Connection db, int idUtente) 
	{

		System.out.println( "allev_id " + allevId );
		InfoAllevamentoBean infoAllReturn = new InfoAllevamentoBean();

		String nodo_errore_id 			= "id";
		String nodo_allevamento_denominazione_asl			= "DENOMINAZIONE_ASL";
		String nodo_allevamento_cod_fiscale_det 			= "DETEN_ID_FISCALE" ;
		String nodo_allevamento_cod_fiscale_prop 			= "PROP_ID_FISCALE" ;
		String nodo_allevamento_azienda_codice 			= "AZIENDA_CODICE";
		String nodo_allevamento_id_allevamento			= "ALLEV_ID";		
		String nodo_allevamento_denominazione			= "DENOMINAZIONE";
		String nodo_allevamento_indirizzo				= "INDIRIZZO";
		String nodo_allevamento_data_inizio_attivita	= "DT_INIZIO_ATTIVITA";
		String nodo_allevamento_data_fine_attivita		= "DT_FINE_ATTIVITA";
		String nodo_allevamento_provincia_codice  		= "PRO_CODICE";
		String nodo_tipo_allev_codice			 		= "TIPO_ALLEV_COD";
		String nodo_numero_capi			  				= "CAPI_PRESENTI";
		String nodo_tipo_prod_codice					= "TIP_PROD_CODICE" ; // ALLEVAMENTO
		String nodo_allevamento_codice_specie 			= "SPE_CODICE";
		String nodo_flag_carne_latte					= "FLAG_CARNE_LATTE";
		String nodo_allevamento_id_fiscale				= "ID_FISCALE";		
		String nodo_azienda_asl_codice	   				= "ASL_CODICE";
		String nodo_allevamento_det_cod_fiscale 		= "DETEN_ID_FISCALE";
		String nodo_data_inizio_resp					= "DT_INIZIO_RESPONSABILITA" ;
		String nodo_allevamento_orientamento			= "ORIENTAMENTO_DESCR" ;
		String valori_allevamento_cod_fiscale 			= null;
		String valori_allevamento_azienda_codice 		= null;
		String valori_allevamento_id_allevamento		= null;
		String valori_allevamento_denominazione			= null;
		String valori_allevamento_indirizzo				= null;
		String valori_allevamento_provincia_codice		= null;
		String valori_allevamento_cod_fiscale_det		= null ;
		String valori_allevamento_cod_fiscale_prop		= null ;
		String valori_allevamento_data_inizio_attivita	= null;
		String valori_allevamento_data_fine_attivita	= null;
		String valori_allevamento_codice_specie 		= null;
		String valori_numero_capi						= null;
		String valori_flag_carne_latte					= null;
		String valori_tipo_prod_codice					= null;
		String valori_allevamento_tipo_prod_codice 		= null ;
		String valori_nodo_azienda_asl_codice 			= null ;
		String valori_allevamento_orientamento			= null;
		String valori_det_cod_fiscale 			= null ;
		String valori_data_inizio_resp = null ; 
		String val_denominazione_asl = "" ;
		String val_tipo_allevamento = "" ;

		String get_capi_allevamento_response = null; 

		WsAziendeQrySoapStub stub_WsAziendeQry;
		WsAnagraficaCapoQrySoapStub stub_wsCapoQry;
		
		try 
		{
			//Da eliminare dopo l'eliminazione di tutti i ws tramite java
			String userName = ApplicationProperties.getProperty("USERNAME_WS_BDN");
			String passWord = ApplicationProperties.getProperty("PASSWORD_WS_BDN");
			stub_wsCapoQry = (WsAnagraficaCapoQrySoapStub) (new WsAnagraficaCapoQryLocator().getwsAnagraficaCapoQrySoap()); 
			SOAPHeaderElement header = new SOAPHeaderElement(new QName("http://bdr.izs.it/webservices", "SOAPAutenticazione", "web"));
			SOAPElement node = header.addChildElement("username");
			node.addTextNode(userName);
			SOAPElement node2 = header.addChildElement("password");
			node2.addTextNode(passWord);

			// INSERIMENTO HEADER NEGLI STUB
			stub_wsCapoQry.setHeader(header);
			//Fine - Da eliminare dopo l'eliminazione di tutti i ws tramite java
			
			WsPost wsPost = new WsPost(db, WsPost.ENDPOINT_ALLEVAMENTI,WsPost.AZIONE_GETBYPK);
			
			HashMap<String, Object> campiInput = new HashMap<>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			campiInput.put("ser:p_allev_id",allevId);
					
			wsPost.setCampiInput(campiInput);
			wsPost.costruisciEnvelope(db);
			String response = wsPost.post(db, idUtente);
			response=response.replaceAll("&lt;", "<");
			response=response.replaceAll("&gt;", ">");
			System.out.println("response: " + response);
			
			String idAllevamentoRestituito = utilsXML.getValoreNodoXML(response,"ALLEV_ID");
			
			
			
			if(idAllevamentoRestituito==null || idAllevamentoRestituito.equals(""))
			{
				System.out.println("Errore nella ricerca in bdn dell'allevamento");
				/*String messaggio = utilsXML.getValoreNodoXML(response,"des");
				infoAllReturn.setErrore(messaggio);
				try {
				infoAllReturn.setCod_errore(Integer.parseInt(utilsXML.getValoreNodoXML(response,"id")));
				}
				catch(Exception e ){}
				return infoAllReturn;*/
				String messaggio = utilsXML.getValoreNodoXML(response,nodo_errore_id);
				throw new EccezioneDati("Errore nella ricerca in bdn dell'allevamento: " + ((messaggio!=null)?(messaggio):("")) );
			
			}
			else
			{
				val_denominazione_asl 	=  utilsXML.getValoreNodoXML(response, nodo_allevamento_denominazione_asl );
				valori_allevamento_cod_fiscale			=  utilsXML.getValoreNodoXML(response,nodo_allevamento_id_fiscale  );
				valori_allevamento_azienda_codice		=  utilsXML.getValoreNodoXML(response,nodo_allevamento_azienda_codice );
				valori_allevamento_id_allevamento		=  utilsXML.getValoreNodoXML(response,nodo_allevamento_id_allevamento );
				valori_allevamento_denominazione		=  utilsXML.getValoreNodoXML(response, nodo_allevamento_denominazione );
				valori_allevamento_indirizzo			=  utilsXML.getValoreNodoXML(response, nodo_allevamento_indirizzo );
				valori_allevamento_provincia_codice 	=  utilsXML.getValoreNodoXML(response, nodo_allevamento_provincia_codice);
				valori_allevamento_cod_fiscale_det 		=  utilsXML.getValoreNodoXML(response,nodo_allevamento_cod_fiscale_det);
				valori_allevamento_cod_fiscale_prop 	=  utilsXML.getValoreNodoXML(response,nodo_allevamento_cod_fiscale_prop);
				valori_allevamento_data_inizio_attivita	=  utilsXML.getValoreNodoXML(response, nodo_allevamento_data_inizio_attivita);
				valori_allevamento_data_fine_attivita	=  utilsXML.getValoreNodoXML(response, nodo_allevamento_data_fine_attivita);
				valori_allevamento_codice_specie 		=  utilsXML.getValoreNodoXML(response, nodo_allevamento_codice_specie);
				valori_allevamento_tipo_prod_codice    	=  utilsXML.getValoreNodoXML(response, nodo_tipo_prod_codice);
				valori_nodo_azienda_asl_codice    		=  utilsXML.getValoreNodoXML(response, nodo_azienda_asl_codice);
				valori_det_cod_fiscale    				=  utilsXML.getValoreNodoXML(response, nodo_allevamento_det_cod_fiscale);
				valori_data_inizio_resp    				=  utilsXML.getValoreNodoXML(response, nodo_data_inizio_resp);
				valori_flag_carne_latte					=  utilsXML.getValoreNodoXML(response, nodo_flag_carne_latte);
				valori_tipo_prod_codice					=  utilsXML.getValoreNodoXML(response, nodo_tipo_prod_codice);
				val_tipo_allevamento                    =  utilsXML.getValoreNodoXML(response,  nodo_tipo_allev_codice );
				valori_allevamento_orientamento			=  utilsXML.getValoreNodoXML(response,  nodo_allevamento_orientamento );

			
				infoAllReturn.setCap(utilsXML.getValoreNodoXML(response,  "CAP" ));
				infoAllReturn.setCodice_azienda(valori_allevamento_azienda_codice);
				infoAllReturn.setCod_fiscale(valori_allevamento_cod_fiscale);
				infoAllReturn.setCod_fiscale_det(valori_allevamento_cod_fiscale_det);
				infoAllReturn.setCodFiscaleProp(valori_allevamento_cod_fiscale_prop);
				infoAllReturn.setDenominazione_asl(val_denominazione_asl);
				infoAllReturn.setId_allevamento(valori_allevamento_id_allevamento);
				infoAllReturn.getidAsl();
				infoAllReturn.setDenominazione(valori_allevamento_denominazione);
				infoAllReturn.setProvincia(valori_allevamento_provincia_codice);
				infoAllReturn.setIndirizzo(valori_allevamento_indirizzo);
				infoAllReturn.setTipoProduzione(valori_allevamento_tipo_prod_codice);
				infoAllReturn.setSpecie_allevata( valori_allevamento_codice_specie);
				System.out.println("DATA INIZIOOOOOO 1: " + valori_allevamento_data_inizio_attivita);
				infoAllReturn.setData_inizio_attivita(valori_allevamento_data_inizio_attivita);
				infoAllReturn.setData_fine_attivita(valori_allevamento_data_fine_attivita);
				infoAllReturn.setFlag_carne_latte(valori_flag_carne_latte);
				infoAllReturn.setFlag_tipo_all(valori_tipo_prod_codice);
				infoAllReturn.setAslCodice(valori_nodo_azienda_asl_codice);
				infoAllReturn.setCodiceTipoAllevamento(val_tipo_allevamento);
				infoAllReturn.setCodice_specie(valori_allevamento_codice_specie);
				infoAllReturn.setOrientamento_prod(valori_allevamento_orientamento);
				infoAllReturn.setTipo_produzione(getTipoProduzione(db,valori_allevamento_tipo_prod_codice));
				infoAllReturn.setDescComune(utilsXML.getValoreNodoXML(response,  "COM_CODICE" ), utilsXML.getValoreNodoXML(response,  "CAP" ), db);
				
				
				if( "0121".equals(infoAllReturn.getSpecie_allevata()) ||  "0129".equals(infoAllReturn.getSpecie_allevata()))
				{
					get_capi_allevamento_response = stub_wsCapoQry.getInfoCapiAllevamento_STR(infoAllReturn.getId_allevamento());
					valori_numero_capi = utilsXML.getValoreNodoXML(get_capi_allevamento_response, nodo_numero_capi);
					infoAllReturn.setNum_capi_totali(valori_numero_capi);
				}
			}
		} 
		catch (java.lang.NullPointerException e) 
		{
			e.printStackTrace();
			infoAllReturn = new InfoAllevamentoBean();
			infoAllReturn.setCod_errore(COD_ERR_NullPointerException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore NullPointerException : " + e );
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			infoAllReturn = new InfoAllevamentoBean();
			infoAllReturn.setCod_errore(COD_ERR_Exception);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore Exception : " + e );
			e.printStackTrace();
		}


		if ( infoAllReturn.getErrore() != null ) 
		{
			if(infoAllReturn.getErrore().contains("CODICE AZIENDA NON PRESENTE IN ANAGRAFE"))
			{
				infoAllReturn.setCod_errore(COD_ERR_NotInBDN);
				infoAllReturn.setErrore("[CapoParzialeBean.getCapoParzialeBean()] - AZIENDA NON PRESENTE IN ANAGRAFE");
			}
			else
				infoAllReturn.setCod_errore(COD_ERR_NonPrevisto);
		}

		return infoAllReturn;
	}

	
	

	private static String xmlResp ;
	
	

	public String getXmlResp() {
		return xmlResp;
	}
	public void setXmlResp(String xmlResp) {
		this.xmlResp = xmlResp;
	}
	public static ArrayList<InfoAllevamentoBean> findAllevamenti_old(String codice_azienda,String denominazione,String cod_specie) {


		ArrayList<InfoAllevamentoBean> listaAllevamenti = new ArrayList<InfoAllevamentoBean>();
		Logger logger = Logger.getLogger("MainLogger");

		// DEFINIZIONE CREDENZIALI DI ACCESSO AL WEBSERVICES
		String userName = ApplicationProperties.getProperty("USERNAME_WS_BDN");
		String passWord = ApplicationProperties.getProperty("PASSWORD_WS_BDN");



		// DEFINIZIONE NODI DA RECUPERARE PER L'ALLEVAMENTO //
		
		String nodo_allevamento_denominazione_asl			= "DENOMINAZIONE_ASL";
		String nodo_allevamento_data_fine_attivita			= "DT_FINE_ATTIVITA";
		String nodo_allevamento_prop_denominazione 		= "DENOMINAZIONE_PROP";
		String nodo_allevamento_prop_cod_fiscale 		= "ID_FISCALE_PROP";
		String nodo_allevamento_comune_codice  			= "COM_CODICE";
		String nodo_allevamenti_comune_azienda_codice = "AZIENDA_COM_CODICE";

		String nodo_allevamento_id_allevamento			= "ALLEV_ID";		
		String nodo_allevamento_id_fiscale				= "ID_FISCALE";		
		String nodo_allevamento_denominazione			= "DENOMINAZIONE";
		String nodo_flag_carne_latte					= "FLAG_CARNE_LATTE";
		String nodo_allevamento_data_inizio_attivita	= "DT_INIZIO_ATTIVITA";
		String nodo_allevamento_codice_specie 			= "SPE_CODICE";
		String nodo_allevamento_descrizione_specie 			= "SPE_DESCRIZIONE";
		String nodo_allevamento_det_cod_fiscale 		= "ID_FISCALE_DETEN";
		String nodo_azienda_asl_codice	   				= "ASL_CODICE";

		String nodo_allevamento_det_denominazione 		= "DENOMINAZIONE_DETEN";
		String nodo_tipo_allev_codice			 		= "TIPO_ALLEV_COD";

		String nodo_allevamento_provincia_codice  		= "PRO_CODICE";
		String nodo_allevamento_indirizzo				= "INDIRIZZO";
		String nodo_tipo_allevamento_descrizione	 ="TIPO_ALLEV_DESCR";
		// DEFINIZIONE VALORI DA RECUPERARE PER L'ALLEVAMENTO //
		String val_id_allevamento 	= "" ;
		String valore_comune_azienda_codice = "" ;
		String val_id_Fiscale 		= "" ;
		String val_denominazione 	= "" ;
		String val_carne_latte 	= "" ;
		String val_data_inizio 		="" ;
		String val_codice_asl	="" ;
		String val_provincia ;
		String val_comune ;
		String val_cod_specie = "" ;
		String val_descr_specie = "" ;
		String val_det_cod_fiscale = "" ;
		String val_prop_cod_fiscale = "" ;
		String val_denominazione_det ;
		String val_denominazione_prop = "" ;
		String valori_det_cod_fiscale 			= null ;

		String val_tipo_allevamento_descr = "" ;
		String val_allevamento_data_fine_attivita			= "";
		String val_allevamento_prop_denominazione 		= "";
		String val_allevamento_prop_cod_fiscale 		= "";
		String val_allevamento_det_denominazione = "";

		String valori_nodo_azienda_asl_codice = "" ;
		String val_det_id_fiscale = "" ;
		String val_flag_carne_latte = "" ;
		String val_tipo_allevamento = "" ;

		String valori_allevamento_indirizzo				= null;
		String valori_allevamento_provincia_codice		= null;

		String val_denominazione_asl = "";
		String documento_xml_costruito_allevamento = "" ;
		
		WsAziendeQrySoapStub stub_WsAziendeQry;

		int numRecord = 0;

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
			// RECUPERO RISPOSTA WEBSERVICES findallevamento_str
			
			if (denominazione.contains("'"))
				denominazione = "";
			documento_xml_costruito_allevamento = stub_WsAziendeQry.findAllevamento_STR(codice_azienda,denominazione,cod_specie);		

			
			xmlResp=documento_xml_costruito_allevamento;
			
			// RECUPERO VALORI DALLA RISPOSTA di findallevamento_str


			try {
				numRecord = utilsXML.getNumeroNodiXML(documento_xml_costruito_allevamento, nodo_allevamento_id_allevamento);
			} catch (SAXException e1) {
				// TODO Auto-generated catch block

			} catch (IOException e1) {
				// TODO Auto-generated catch block

			}
			

			
			for (int i = 0 ; i < numRecord ; i++)
			{
				InfoAllevamentoBean infoAllReturn = new InfoAllevamentoBean();
				val_allevamento_data_fine_attivita = "" ;
				val_id_Fiscale		=  utilsXML.getValueNodoXML(documento_xml_costruito_allevamento,nodo_allevamento_id_fiscale,i );
				val_allevamento_prop_cod_fiscale 		=  utilsXML.getValueNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_prop_cod_fiscale,i );
				val_cod_specie		=  utilsXML.getValueNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_codice_specie,i );

				val_denominazione_asl 	=  utilsXML.getValueNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_denominazione_asl,i );
				val_id_allevamento		=  utilsXML.getValueNodoXML(documento_xml_costruito_allevamento,nodo_allevamento_id_allevamento,i  );
				val_denominazione		=  utilsXML.getValueNodoXML(documento_xml_costruito_allevamento,nodo_allevamento_denominazione,i );
				val_descr_specie		=  utilsXML.getValueNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_descrizione_specie,i );
				


				val_allevamento_data_fine_attivita			= utilsXML.getValueNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_data_fine_attivita,i );
				val_allevamento_prop_denominazione 		= utilsXML.getValueNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_prop_denominazione,i );
				val_data_inizio 		= utilsXML.getValueNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_data_inizio_attivita,i );
				valori_nodo_azienda_asl_codice    		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_azienda_asl_codice);
				if (valori_nodo_azienda_asl_codice==null)
					valori_nodo_azienda_asl_codice    		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, "CODICE_ASL");

				val_allevamento_det_denominazione    		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_det_denominazione);

				
				valori_allevamento_indirizzo			=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_indirizzo );
				//valori_allevamento_comune_codice		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_comune_codice);

				valore_comune_azienda_codice = utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,  nodo_allevamenti_comune_azienda_codice );
				valori_allevamento_provincia_codice 	=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_provincia_codice);
				//valori_allevamento_telefono				=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_telefono);
				//valori_allevamento_note					=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_note );	
				//valori_allevamento_errore    			=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_errore);

				valori_det_cod_fiscale    				=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_det_cod_fiscale);
				//valori_cap    							=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_cap);

				val_comune    				=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_comune_codice);

				val_tipo_allevamento_descr =utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_tipo_allevamento_descrizione); 

				

				infoAllReturn.setAslCodice(valori_nodo_azienda_asl_codice);
				infoAllReturn.setId_allevamento(val_id_allevamento);
				infoAllReturn.setDenominazione_asl(val_denominazione_asl);
				//SETs DEL BEAN RESTITUITO
				infoAllReturn.setCodice_azienda(codice_azienda);
				infoAllReturn.setDenominazione(val_denominazione);
				infoAllReturn.setSpecie_allevata(val_descr_specie);
				infoAllReturn.setCodice_specie(val_cod_specie);
				infoAllReturn.setCod_fiscale(val_id_Fiscale);
				infoAllReturn.setNome_proprietario(val_allevamento_prop_denominazione);
				infoAllReturn.setData_fine_attivita(val_allevamento_data_fine_attivita);
				infoAllReturn.setProvincia(valori_allevamento_provincia_codice);
				infoAllReturn.setIndirizzo(valori_allevamento_indirizzo);
				infoAllReturn.setData_inizio_attivita(val_data_inizio);
				infoAllReturn.setComune(valore_comune_azienda_codice);
				infoAllReturn.setDescrizioneTipoAllevamento(val_tipo_allevamento_descr);
				infoAllReturn.setAslCodice(valori_nodo_azienda_asl_codice);
				infoAllReturn.setCodFiscaleProp(val_allevamento_prop_cod_fiscale);


				if (infoAllReturn.getCodice_specie().equals("0131") || infoAllReturn.getCodice_specie().equals("0146"))
				{


					val_det_id_fiscale =   utilsXML.getValueNodoXML(documento_xml_costruito_allevamento,nodo_allevamento_det_cod_fiscale,i );
					val_flag_carne_latte = utilsXML.getValueNodoXML(documento_xml_costruito_allevamento,nodo_flag_carne_latte,i );
					val_tipo_allevamento = utilsXML.getValueNodoXML(documento_xml_costruito_allevamento,nodo_tipo_allev_codice,i );
					


					valori_allevamento_indirizzo			=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_indirizzo );
					//valori_allevamento_comune_codice		=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_comune_codice);
					valori_allevamento_provincia_codice 	=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_provincia_codice);

					infoAllReturn.setTipoProduzione("AL");
					infoAllReturn.setCod_fiscale_det(val_det_id_fiscale);
					infoAllReturn.setFlag_carne_latte(val_flag_carne_latte);
					infoAllReturn.setCodiceTipoAllevamento(val_tipo_allevamento);

					infoAllReturn.setProvincia(valori_allevamento_provincia_codice);
					infoAllReturn.setIndirizzo(valori_allevamento_indirizzo);
					infoAllReturn.setCod_fiscale_det(valori_det_cod_fiscale);

				}
				
				if (infoAllReturn!=null && infoAllReturn.getErrore() != null ) {
					if(infoAllReturn.getErrore().contains("CODICE AZIENDA NON PRESENTE IN ANAGRAFE")){
						infoAllReturn.setCod_errore(COD_ERR_NotInBDN);
						infoAllReturn.setErrore("[CapoParzialeBean.getCapoParzialeBean()] - AZIENDA NON PRESENTE IN ANAGRAFE");
					}
					else{
						infoAllReturn.setCod_errore(COD_ERR_NonPrevisto);
					}
				}
				
				
				if (!"0131".equals(infoAllReturn.getCodice_specie()))
				{

//				

				InfoAllevamentoBean infoAll = getInfoAllevamentoBean(infoAllReturn.getCodice_azienda(), infoAllReturn.getCod_fiscale(), infoAllReturn.getCodice_specie());
				
				infoAll.setAslCodice(infoAllReturn.getAslCodice());
				infoAll.setDenominazione_asl(infoAllReturn.getDenominazione_asl());
				infoAll.getidAsl();
				infoAllReturn.setData_fine_attivita(infoAll.getData_fine_attivita());
				listaAllevamenti.add(infoAll);
				}
				else
					listaAllevamenti.add(infoAllReturn);


			}






		} catch (ServiceException e) {
			InfoAllevamentoBean infoAllReturn = new InfoAllevamentoBean();
			infoAllReturn.setCod_errore(COD_ERR_ServiceException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore ServiceException : " + e );

			listaAllevamenti.add(infoAllReturn);
			//cpbReturn = null;
			e.printStackTrace();
		} catch (SOAPException e) {
			InfoAllevamentoBean infoAllReturn = new InfoAllevamentoBean();
			infoAllReturn.setCod_errore(COD_ERR_SOAPException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore SOAPException : " + e );
			listaAllevamenti.add(infoAllReturn);
			e.printStackTrace();
		} catch (RemoteException e) {
			InfoAllevamentoBean infoAllReturn = new InfoAllevamentoBean();
			infoAllReturn.setCod_errore(COD_ERR_RemoteException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore RemoteException : " + e );
			listaAllevamenti.add(infoAllReturn);
			e.printStackTrace();
		} catch (java.lang.NullPointerException e) {
			InfoAllevamentoBean infoAllReturn = new InfoAllevamentoBean();
			infoAllReturn.setCod_errore(COD_ERR_NullPointerException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore NullPointerException : " + e );
			listaAllevamenti.add(infoAllReturn);
			e.printStackTrace();
		} catch (Exception e) {
			InfoAllevamentoBean infoAllReturn = new InfoAllevamentoBean();
			infoAllReturn.setCod_errore(COD_ERR_Exception);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore Exception : " + e );
			listaAllevamenti.add(infoAllReturn);
			e.printStackTrace();
		}


		

		return listaAllevamenti;
	}
	
	
	public static ArrayList<InfoAllevamentoBean> findAllevamenti(String codice_azienda,String denominazione,String cod_specie) 
	{
		ArrayList<InfoAllevamentoBean> listaAllevamenti = new ArrayList<InfoAllevamentoBean>();
		Logger logger = Logger.getLogger("MainLogger");

		// DEFINIZIONE CREDENZIALI DI ACCESSO AL WEBSERVICES
		String userName = ApplicationProperties.getProperty("USERNAME_WS_BDN");
		String passWord = ApplicationProperties.getProperty("PASSWORD_WS_BDN");

		// DEFINIZIONE NODI DA RECUPERARE PER L'ALLEVAMENTO //
		String nodo_allevamento_denominazione_asl			= "DENOMINAZIONE_ASL";
		String nodo_allevamento_data_fine_attivita			= "DT_FINE_ATTIVITA";
		String nodo_allevamento_prop_denominazione 		= "DENOMINAZIONE_PROP";
		String nodo_allevamento_prop_cod_fiscale 		= "ID_FISCALE_PROP";
		String nodo_allevamenti_comune_azienda_codice = "AZIENDA_COM_CODICE";
		String nodo1 = "TIP_PROD_CODICE";
		String nodo2 = "TIP_PROD_DESCR";

		String nodo_allevamento_id_allevamento			= "ALLEV_ID";		
		String nodo_allevamento_id_fiscale				= "ID_FISCALE";		
		String nodo_allevamento_denominazione			= "DENOMINAZIONE";
		String nodo_flag_carne_latte					= "FLAG_CARNE_LATTE";
		String nodo_allevamento_data_inizio_attivita	= "DT_INIZIO_ATTIVITA";
		String nodo_allevamento_codice_specie 			= "SPE_CODICE";
		String nodo_allevamento_descrizione_specie 			= "SPE_DESCRIZIONE";
		String nodo_allevamento_det_cod_fiscale 		= "ID_FISCALE_DETEN";
		String nodo_azienda_asl_codice	   				= "ASL_CODICE";
		String nodo_tipo_allev_codice			 		= "TIPO_ALLEV_COD";
		String nodo_allevamento_provincia_codice  		= "PRO_CODICE";
		String nodo_allevamento_indirizzo				= "INDIRIZZO";
		String nodo_tipo_allevamento_descrizione	 ="TIPO_ALLEV_DESCR";
		String nodo_codice_azienda	 ="AZIENDA_CODICE";
		
		WsAziendeQrySoapStub stub_WsAziendeQry;
		WsAnagraficaCapoQrySoapStub stub_wsCapoQry;
		
		try 
		{
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
			// RECUPERO RISPOSTA WEBSERVICES findallevamento_str
			
			if (denominazione.contains("'"))
			{
				denominazione = "";
			}
			
			String documento_xml_costruito_allevamento = stub_WsAziendeQry.findAllevamento_STR(codice_azienda,denominazione,cod_specie);		
			
			xmlResp=documento_xml_costruito_allevamento;
			
			int numRecord = 0;
			
			try 
			{
				numRecord = utilsXML.getNumeroNodiXML(documento_xml_costruito_allevamento, nodo_allevamento_id_allevamento);
			} 
			catch (SAXException e1) 
			{
				// TODO Auto-generated catch block

			} 
			catch (IOException e1) 
			{
				// TODO Auto-generated catch block

			}
			
			for (int i = 0 ; i < numRecord ; i++)
			{
				InfoAllevamentoBean infoAllReturn = new InfoAllevamentoBean();

				String valori_nodo_azienda_asl_codice    		=  (utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_azienda_asl_codice)!=null)?
						                                           (utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_azienda_asl_codice)):
						                                           (utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, "CODICE_ASL"));

				infoAllReturn.setAslCodice(valori_nodo_azienda_asl_codice);
				infoAllReturn.setId_allevamento( utilsXML.getValueNodoXML(documento_xml_costruito_allevamento,nodo_allevamento_id_allevamento,i  ));
				infoAllReturn.setDenominazione_asl(utilsXML.getValueNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_denominazione_asl,i ));
				//SETS DEL BEAN RESTITUITO
				infoAllReturn.setCodice_azienda(utilsXML.getValueNodoXML(documento_xml_costruito_allevamento,nodo_codice_azienda,i ));
				infoAllReturn.setDenominazione(utilsXML.getValueNodoXML(documento_xml_costruito_allevamento,nodo_allevamento_denominazione,i ));
				infoAllReturn.setSpecie_allevata(utilsXML.getValueNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_descrizione_specie,i ));
				infoAllReturn.setCodice_specie(utilsXML.getValueNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_codice_specie,i ));
				
				//Faccio cosi' perche' il servizio bdn per i molluschi non ritorna il codice numerico ma "MOL" e quindi all.getSpecie_allevata mi ritorna "MOL"
				if(infoAllReturn.getSpecie_allevata().equalsIgnoreCase("mol"))
				{
					infoAllReturn.setSpecie_allevata("200");
					infoAllReturn.setCodice_specie("200");
				}
				if(infoAllReturn.getSpecie_allevata().equalsIgnoreCase("cro"))
				{
					infoAllReturn.setSpecie_allevata("201");
					infoAllReturn.setCodice_specie("201");
				}
				infoAllReturn.setCod_fiscale(utilsXML.getValueNodoXML(documento_xml_costruito_allevamento,nodo_allevamento_id_fiscale,i ));
				infoAllReturn.setNome_proprietario(utilsXML.getValueNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_prop_denominazione,i ));
				infoAllReturn.setData_fine_attivita(utilsXML.getValueNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_data_fine_attivita,i ));
				infoAllReturn.setProvincia(utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_provincia_codice));
				infoAllReturn.setIndirizzo(utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_indirizzo ));
				infoAllReturn.setData_inizio_attivita(utilsXML.getValueNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_data_inizio_attivita,i ));
				infoAllReturn.setComune(utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,  nodo_allevamenti_comune_azienda_codice ));
				infoAllReturn.setDescrizioneTipoAllevamento(utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_tipo_allevamento_descrizione));
				infoAllReturn.setAslCodice(valori_nodo_azienda_asl_codice);
				infoAllReturn.setCodFiscaleProp(utilsXML.getValueNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_prop_cod_fiscale,i ));

				//Nel vecchio metodo veniva fatto solo se codice_specie era GALLUS o AVICOLI MISTI
				infoAllReturn.setOrientamento_prod(utilsXML.getValueNodoXML(documento_xml_costruito_allevamento, nodo2,i ));
				infoAllReturn.setCod_fiscale_det(utilsXML.getValueNodoXML(documento_xml_costruito_allevamento,nodo_allevamento_det_cod_fiscale,i ));
				infoAllReturn.setFlag_carne_latte(utilsXML.getValueNodoXML(documento_xml_costruito_allevamento,nodo_flag_carne_latte,i ));
				infoAllReturn.setCodiceTipoAllevamento(utilsXML.getValueNodoXML(documento_xml_costruito_allevamento,nodo_tipo_allev_codice,i ));
				infoAllReturn.setProvincia(utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_provincia_codice));
				infoAllReturn.setIndirizzo(utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_indirizzo ));
				infoAllReturn.setCod_fiscale_det(utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_det_cod_fiscale));
				//Fine codice che nel vecchio metodo veniva fatto solo se codice_specie era GALLUS o AVICOLI MISTI
				
				//Gestione errori
				if (infoAllReturn!=null && infoAllReturn.getErrore() != null ) 
				{
					if(infoAllReturn.getErrore().contains("CODICE AZIENDA NON PRESENTE IN ANAGRAFE"))
					{
						infoAllReturn.setCod_errore(COD_ERR_NotInBDN);
						infoAllReturn.setErrore("[CapoParzialeBean.getCapoParzialeBean()] - AZIENDA NON PRESENTE IN ANAGRAFE");
					}
					else
					{
						infoAllReturn.setCod_errore(COD_ERR_NonPrevisto);
					}
				}
				
				listaAllevamenti.add(infoAllReturn);
			}
		} 
		catch (ServiceException e) 
		{
			InfoAllevamentoBean infoAllReturn = new InfoAllevamentoBean();
			infoAllReturn.setCod_errore(COD_ERR_ServiceException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore ServiceException : " + e );
			listaAllevamenti.add(infoAllReturn);
			e.printStackTrace();
		} 
		catch (SOAPException e) 
		{
			InfoAllevamentoBean infoAllReturn = new InfoAllevamentoBean();
			infoAllReturn.setCod_errore(COD_ERR_SOAPException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore SOAPException : " + e );
			listaAllevamenti.add(infoAllReturn);
			e.printStackTrace();
		} 
		catch (RemoteException e) 
		{
			InfoAllevamentoBean infoAllReturn = new InfoAllevamentoBean();
			infoAllReturn.setCod_errore(COD_ERR_RemoteException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore RemoteException : " + e );
			listaAllevamenti.add(infoAllReturn);
			e.printStackTrace();
		} 
		catch (java.lang.NullPointerException e) 
		{
			InfoAllevamentoBean infoAllReturn = new InfoAllevamentoBean();
			infoAllReturn.setCod_errore(COD_ERR_NullPointerException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore NullPointerException : " + e );
			listaAllevamenti.add(infoAllReturn);
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			InfoAllevamentoBean infoAllReturn = new InfoAllevamentoBean();
			infoAllReturn.setCod_errore(COD_ERR_Exception);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore Exception : " + e );
			listaAllevamenti.add(infoAllReturn);
			e.printStackTrace();
		}
		return listaAllevamenti;
	}
	
	
	
	
	public static ArrayList<InfoAllevamentoBean> findAllevamenti(String codice_azienda,String denominazione,String cod_specie, Connection db, int idUtente) 
	{
		ArrayList<InfoAllevamentoBean> listaAllevamenti = new ArrayList<InfoAllevamentoBean>();
		Logger logger = Logger.getLogger("MainLogger");

		// DEFINIZIONE NODI DA RECUPERARE PER L'ALLEVAMENTO //
		String nodo_allevamento_denominazione_asl			= "DENOMINAZIONE_ASL";
		String nodo_allevamento_data_fine_attivita			= "DT_FINE_ATTIVITA";
		String nodo_allevamento_prop_denominazione 		= "DENOMINAZIONE_PROP";
		String nodo_allevamento_prop_cod_fiscale 		= "ID_FISCALE_PROP";
		String nodo_allevamenti_comune_azienda_codice = "AZIENDA_COM_CODICE";

		String nodo_allevamento_id_allevamento			= "ALLEV_ID";		
		String nodo_allevamento_id_fiscale				= "ID_FISCALE";		
		String nodo_allevamento_denominazione			= "DENOMINAZIONE";
		String nodo_flag_carne_latte					= "FLAG_CARNE_LATTE";
		String nodo_allevamento_data_inizio_attivita	= "DT_INIZIO_ATTIVITA";
		String nodo_allevamento_codice_specie 			= "SPE_CODICE";
		String nodo_allevamento_descrizione_specie 			= "SPE_DESCRIZIONE";
		String nodo_allevamento_det_cod_fiscale 		= "ID_FISCALE_DETEN";
		String nodo_azienda_asl_codice	   				= "ASL_CODICE";
		String nodo_tipo_allev_codice			 		= "TIPO_ALLEV_COD";
		String nodo_allevamento_provincia_codice  		= "PRO_CODICE";
		String nodo_allevamento_indirizzo				= "INDIRIZZO";
		String nodo_tipo_allevamento_descrizione	 ="TIPO_ALLEV_DESCR";
		String nodo_codice_azienda	 ="AZIENDA_CODICE";
		
		WsAnagraficaCapoQrySoapStub stub_wsCapoQry;
		
		try 
		{
			if (denominazione.contains("'"))
			{
				denominazione = "";
			}
			
			WsPost wsPost = new WsPost(db, WsPost.ENDPOINT_ALLEVAMENTI,WsPost.AZIONE_GET);
			
			HashMap<String, Object> campiInput = new HashMap<>();
			
			campiInput.put("ser:p_azienda_codice",codice_azienda);
			campiInput.put("ser:p_specie_codice",cod_specie);
			campiInput.put("ser:p_denominazione",denominazione);
					
			wsPost.setCampiInput(campiInput);
			wsPost.costruisciEnvelope(db);
			String response = wsPost.post(db, idUtente);
			response=response.replaceAll("&lt;", "<");
			response=response.replaceAll("&gt;", ">");
			System.out.println("response: " + response);
			
			xmlResp=response;
			
			int numRecord = 0;
			
			try 
			{
				numRecord = utilsXML.getNumeroNodiXML(response, nodo_allevamento_id_allevamento);
			} 
			catch (SAXException e1) 
			{

			} 
			catch (IOException e1) 
			{
			}
			
			for (int i = 0 ; i < numRecord ; i++)
			{
				String idAllevamento = utilsXML.getValueNodoXML(response,"ALLEV_ID",i);
				String orientamentoProd = getOrientamentoProduttivo(idAllevamento, db, idUtente, utilsXML.getValueNodoXML(response, nodo_allevamento_codice_specie,i ), utilsXML.getValueNodoXML(response,nodo_codice_azienda,i ), utilsXML.getValueNodoXML(response,nodo_allevamento_id_fiscale,i ));
				String tipoProduzione = getTipoProduzione(idAllevamento, db, idUtente);
				
				
				
				InfoAllevamentoBean infoAllReturn = new InfoAllevamentoBean();

				String valori_nodo_azienda_asl_codice    		=  (utilsXML.getValoreNodoXML(response, nodo_azienda_asl_codice)!=null)?
						                                           (utilsXML.getValoreNodoXML(response, nodo_azienda_asl_codice)):
						                                           (utilsXML.getValoreNodoXML(response, "CODICE_ASL"));

				infoAllReturn.setAslCodice(valori_nodo_azienda_asl_codice);
				infoAllReturn.setId_allevamento( utilsXML.getValueNodoXML(response,nodo_allevamento_id_allevamento,i  ));
				infoAllReturn.setDenominazione_asl(utilsXML.getValueNodoXML(response, nodo_allevamento_denominazione_asl,i ));
				//SETS DEL BEAN RESTITUITO
				infoAllReturn.setCodice_azienda(utilsXML.getValueNodoXML(response,nodo_codice_azienda,i ));
				infoAllReturn.setDenominazione(utilsXML.getValueNodoXML(response,nodo_allevamento_denominazione,i ));
				infoAllReturn.setSpecie_allevata(utilsXML.getValueNodoXML(response, nodo_allevamento_descrizione_specie,i ));
				infoAllReturn.setCodice_specie(utilsXML.getValueNodoXML(response, nodo_allevamento_codice_specie,i ));
				
				//Faccio cosi' perche' il servizio bdn per i molluschi non ritorna il codice numerico ma "MOL" e quindi all.getSpecie_allevata mi ritorna "MOL"
				if(infoAllReturn.getSpecie_allevata().equalsIgnoreCase("mol"))
				{
					infoAllReturn.setSpecie_allevata("200");
					infoAllReturn.setCodice_specie("200");
				}
				if(infoAllReturn.getSpecie_allevata().equalsIgnoreCase("cro"))
				{
					infoAllReturn.setSpecie_allevata("201");
					infoAllReturn.setCodice_specie("201");
				}
				infoAllReturn.setCod_fiscale(utilsXML.getValueNodoXML(response,nodo_allevamento_id_fiscale,i ));
				infoAllReturn.setNome_proprietario(utilsXML.getValueNodoXML(response, nodo_allevamento_prop_denominazione,i ));
				infoAllReturn.setData_fine_attivita(utilsXML.getValueNodoXML(response, nodo_allevamento_data_fine_attivita,i ));
				infoAllReturn.setProvincia(utilsXML.getValoreNodoXML(response, nodo_allevamento_provincia_codice));
				infoAllReturn.setIndirizzo(utilsXML.getValoreNodoXML(response, nodo_allevamento_indirizzo ));
				infoAllReturn.setData_inizio_attivita(utilsXML.getValueNodoXML(response, nodo_allevamento_data_inizio_attivita,i ));
				infoAllReturn.setComune(utilsXML.getValoreNodoXML(response,  nodo_allevamenti_comune_azienda_codice ));
				infoAllReturn.setDescrizioneTipoAllevamento(utilsXML.getValoreNodoXML(response, nodo_tipo_allevamento_descrizione));
				infoAllReturn.setAslCodice(valori_nodo_azienda_asl_codice);
				infoAllReturn.setCodFiscaleProp(utilsXML.getValueNodoXML(response, nodo_allevamento_prop_cod_fiscale,i ));

				//Nel vecchio metodo veniva fatto solo se codice_specie era GALLUS o AVICOLI MISTI
				infoAllReturn.setOrientamento_prod(orientamentoProd);
				infoAllReturn.setTipoProduzione(getTipoProduzione(db, tipoProduzione));
				infoAllReturn.setCod_fiscale_det(utilsXML.getValueNodoXML(response,nodo_allevamento_det_cod_fiscale,i ));
				infoAllReturn.setFlag_carne_latte(utilsXML.getValueNodoXML(response,nodo_flag_carne_latte,i ));
				infoAllReturn.setCodiceTipoAllevamento(utilsXML.getValueNodoXML(response,nodo_tipo_allev_codice,i ));
				infoAllReturn.setProvincia(utilsXML.getValoreNodoXML(response, nodo_allevamento_provincia_codice));
				infoAllReturn.setIndirizzo(utilsXML.getValoreNodoXML(response, nodo_allevamento_indirizzo ));
				infoAllReturn.setCod_fiscale_det(utilsXML.getValoreNodoXML(response, nodo_allevamento_det_cod_fiscale));
				//Fine codice che nel vecchio metodo veniva fatto solo se codice_specie era GALLUS o AVICOLI MISTI
				
				//Gestione errori
				if (infoAllReturn!=null && infoAllReturn.getErrore() != null ) 
				{
					if(infoAllReturn.getErrore().contains("CODICE AZIENDA NON PRESENTE IN ANAGRAFE"))
					{
						infoAllReturn.setCod_errore(COD_ERR_NotInBDN);
						infoAllReturn.setErrore("[CapoParzialeBean.getCapoParzialeBean()] - AZIENDA NON PRESENTE IN ANAGRAFE");
					}
					else
					{
						infoAllReturn.setCod_errore(COD_ERR_NonPrevisto);
					}
				}
				
				listaAllevamenti.add(infoAllReturn);
			}
		} 
		catch (java.lang.NullPointerException e) 
		{
			InfoAllevamentoBean infoAllReturn = new InfoAllevamentoBean();
			infoAllReturn.setCod_errore(COD_ERR_NullPointerException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore NullPointerException : " + e );
			listaAllevamenti.add(infoAllReturn);
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			InfoAllevamentoBean infoAllReturn = new InfoAllevamentoBean();
			infoAllReturn.setCod_errore(COD_ERR_Exception);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore Exception : " + e );
			listaAllevamenti.add(infoAllReturn);
			e.printStackTrace();
		}
		return listaAllevamenti;
	}


	
	public static String getOrientamentoProduttivo(String allevId, Connection db, int idUtente,String specie,String cod_azienda,String idFiscale) 
	{

		InfoAllevamentoBean infoAllReturn = new InfoAllevamentoBean();

		try 
		{
			
			WsPost wsPost = new WsPost(db, WsPost.ENDPOINT_ALLEVAMENTI,WsPost.AZIONE_GETBYPK);
			
			HashMap<String, Object> campiInput = new HashMap<>();
			
			campiInput.put("ser:p_allev_id",allevId);
					
			wsPost.setCampiInput(campiInput);
			wsPost.costruisciEnvelope(db);
			String response = wsPost.post(db, idUtente);
			if(response.indexOf("ORIENTAMENTO_DESCR")>0)
			{
				String nomeNodo = "ORIENTAMENTO_DESCR";
				int lengthNomeNodo = nomeNodo.length();
				String parentesiAperta = "&lt;";
				String parentesiChiusa = "&gt;";
				int lengthParentesiAperta = parentesiAperta.length();
				int lengthParentesiChiusa = parentesiChiusa.length();
					
				int inizio = response.indexOf(parentesiAperta+nomeNodo)+lengthParentesiAperta+lengthNomeNodo+lengthParentesiChiusa;
				int fine = response.indexOf(parentesiAperta+"/"+nomeNodo);
				return response.substring(inizio , fine);
			}
			else if(specie!=null && !specie.equalsIgnoreCase("200") && !specie.equalsIgnoreCase("0160") && !specie.equalsIgnoreCase("201") )
			{
				String specieCod = null;
				String responseAllevamento = null;
				WsPost ws = new WsPost();  

				ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_ACQUACOLTURA_BDN"));
					
				String envelopeAllevamento = costruisciEnvelope("Allevamento", cod_azienda, idFiscale);
				ws.setWsRequest(envelopeAllevamento);
				responseAllevamento = ws.post(db, idUtente);
				
				String specieOttenuta = null;
				while(specieOttenuta==null || !specieOttenuta.equalsIgnoreCase(specieCod))
				{
					int posizioneInizioReturn = responseAllevamento.indexOf("<return>");
					if(posizioneInizioReturn<0)
						posizioneInizioReturn = responseAllevamento.indexOf("<return xmlns=\"\">");
					
					int posizioneFineReturn = responseAllevamento.indexOf("</return>") + 9;
					
					String responseAllevamentoOttenuto = responseAllevamento.substring(posizioneInizioReturn, posizioneFineReturn);
					
					specieOttenuta = utilsXML.getValoreNodoXML(responseAllevamentoOttenuto,"gspCodice" );
					if(specieOttenuta!=null && specieOttenuta.equalsIgnoreCase(specie))
					{
						responseAllevamento = "<?xml version=\"1.0\" ?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Body><ns2:getAllevamentoResponse xmlns:ns2=\"http://acquacoltura.izs.it/services\">" + responseAllevamentoOttenuto + "></ns2:getAllevamentoResponse></S:Body></S:Envelope>";
						break;
					}
					else
						responseAllevamento = responseAllevamento.substring(0, posizioneInizioReturn) + responseAllevamento.substring(posizioneFineReturn, responseAllevamento.length());
					break;
				}
				
				return utilsXML.getValoreNodoXML(responseAllevamento,"tprDescrizione" );
			}
			else
				return null;
			
		}
		catch (java.lang.NullPointerException e) 
		{
			e.printStackTrace();
			infoAllReturn = new InfoAllevamentoBean();
			infoAllReturn.setCod_errore(COD_ERR_NullPointerException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore NullPointerException : " + e );
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			infoAllReturn = new InfoAllevamentoBean();
			infoAllReturn.setCod_errore(COD_ERR_Exception);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore Exception : " + e );
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	
	public static String getTipoProduzione(String allevId, Connection db, int idUtente) 
	{

		InfoAllevamentoBean infoAllReturn = new InfoAllevamentoBean();

		try 
		{
			WsPost wsPost = new WsPost(db, WsPost.ENDPOINT_ALLEVAMENTI,WsPost.AZIONE_GETBYPK);
			
			HashMap<String, Object> campiInput = new HashMap<>();
			
			campiInput.put("ser:p_allev_id",allevId);
					
			wsPost.setCampiInput(campiInput);
			wsPost.costruisciEnvelope(db);
			String response = wsPost.post(db, idUtente);
			if(response.indexOf("TIP_PROD_CODICE")>0)
			{
				String nomeNodo = "TIP_PROD_CODICE";
				int lengthNomeNodo = nomeNodo.length();
				String parentesiAperta = "&lt;";
				String parentesiChiusa = "&gt;";
				int lengthParentesiAperta = parentesiAperta.length();
				int lengthParentesiChiusa = parentesiChiusa.length();
					
				int inizio = response.indexOf(parentesiAperta+nomeNodo)+lengthParentesiAperta+lengthNomeNodo+lengthParentesiChiusa;
				int fine = response.indexOf(parentesiAperta+"/"+nomeNodo);
				return response.substring(inizio , fine);
			}
			else
				return null;
		} 
		catch (java.lang.NullPointerException e) 
		{
			e.printStackTrace();
			infoAllReturn = new InfoAllevamentoBean();
			infoAllReturn.setCod_errore(COD_ERR_NullPointerException);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore NullPointerException : " + e );
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			infoAllReturn = new InfoAllevamentoBean();
			infoAllReturn.setCod_errore(COD_ERR_Exception);
			infoAllReturn.setErrore("[InfoAllevamentoBean.getInfoAllevamentoBean()] - E' occorso un errore Exception : " + e );
			e.printStackTrace();
		}
		
		return null;
	}



	public   String getComuneFromCodiceAzienda(String codiceAzienda, Connection conn) throws SQLException
	{

		String comune = "N.D";

		if(codiceAzienda!= null && codiceAzienda.trim().length() >= 5){

			String siglaProvincia = codiceAzienda.substring(3, 5);

			HashMap<String, String> codiciIstatProvince = new HashMap<String, String>();
			codiciIstatProvince.put("CE", "61");
			codiciIstatProvince.put("BN", "62");
			codiciIstatProvince.put("NA", "63");
			codiciIstatProvince.put("AV", "64");
			codiciIstatProvince.put("SA", "65");

			String codiceIstatProvincia = "";
			if(codiciIstatProvince.containsKey(siglaProvincia)){

				codiceIstatProvincia = codiciIstatProvince.get(siglaProvincia);

				String codiceIstatComune = codiceIstatProvincia + codiceAzienda.substring(0, 3);

				PreparedStatement pst = null;
				ResultSet rs = null;

				try{
					String query = "select comune from comuni where codiceistatcomune = ?";
					pst = conn.prepareStatement(query);
					pst.setString(1, codiceIstatComune);
					rs = pst.executeQuery();

					if(rs.next()){
						comune = rs.getString(1);
					}



				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
				finally{
					if(rs != null){
						rs.close();
					}
					if(pst != null){
						pst.close();
					}
				}

			}

		}

		return comune;

	}





	public  void setCodiceComune(String codAzienda, Connection db)
	{
		String provincia = this.codice_azienda.substring(3,5);
		if ( this.codice_azienda != null)
		{
			String codcomune = this.codice_azienda.substring(0,3);

			if (provincia.equalsIgnoreCase("ce"))
			{
				this.comune = "061"+codcomune;
			}
			else
			{
				if (provincia.equalsIgnoreCase("bn"))
				{
					this.comune = "062"+codcomune;
				}
				else
				{
					if (provincia.equalsIgnoreCase("na"))
					{
						this.comune = "063"+codcomune;
					}
					else
					{
						if (provincia.equalsIgnoreCase("av"))
						{
							this.comune = "064"+codcomune;
						}
						else
						{
							if (provincia.equalsIgnoreCase("sa"))
							{
								this.comune = "065"+codcomune;
							}
						}
					}
				}

			}
			String sel = "select * from comuni where codiceistatcomune = ?";
			try
			{
				PreparedStatement pst = db.prepareStatement(sel);
				pst.setString(1, this.comune);
				ResultSet rs = pst.executeQuery();
				if (rs.next())
					this.comune=rs.getString("comune");
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}

	}
	
	public  void setDescComune(String codComune, String cap, Connection db)
	{
		String sel = "select nome from comuni1 where cod_comune = ? and cap = ?";
		try
		{
			PreparedStatement pst = db.prepareStatement(sel);
			pst.setString(1, codComune);
			pst.setString(2, cap);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				this.descComune=rs.getString("nome");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

	}



	public  String getSpecieAllevata (String specie_allevata,Connection db) throws SQLException
	{		
		String sel = "select description from lookup_specie_allevata where short_description = ? ";
		
		System.out.println("specie_allevata: "+specie_allevata);
		System.out.println("select getSpecieAllevata: "+sel);
		
		String specieAllev = null;
		PreparedStatement pst = db.prepareStatement(sel );
		pst.setString(1, specie_allevata);
		ResultSet rs = pst.executeQuery(); 
		if (rs.next())
		{
			specieAllev = rs.getString(1);
		}
		else
		{
			specieAllev = "" ;
		}



		return specieAllev;
	}
	
	
	public  String getCodSpecieAllevataByAltCod (String specie_allevata,Connection db) throws SQLException
	{		
		String sel = "select short_description from lookup_specie_allevata where alt_short_description = ? ";
			
		System.out.println("specie_allevata: "+specie_allevata);
		System.out.println("select getSpecieAllevataByAltCod: "+sel);
		
		String specieAllev = null;
		PreparedStatement pst = db.prepareStatement(sel );
		pst.setString(1, specie_allevata);
		ResultSet rs = pst.executeQuery(); 
		if (rs.next())
		{
			specieAllev = rs.getString(1);
		}
		else
		{
			specieAllev = "" ;
		}



		return specieAllev;
	}
	
	public  String getAltShortDescriptionAllevataByShort (String specie_allevata,Connection db) throws SQLException
	{		
		String sel = "select alt_short_description from lookup_specie_allevata where short_description::integer = ?::integer ";
			
		System.out.println("specie_allevata: "+specie_allevata);
		System.out.println("select getSpecieAllevataByAltCod: "+sel);
		
		String specieAllev = null;
		PreparedStatement pst = db.prepareStatement(sel );
		pst.setString(1, specie_allevata);
		ResultSet rs = pst.executeQuery(); 
		if (rs.next())
		{
			specieAllev = rs.getString(1);
		}
		else
		{
			specieAllev = "" ;
		}



		return specieAllev;
	}



	public  void getOrientamentoProduzione(Connection db,String descrSpecie) throws SQLException
	{
		String sel = "select descrizione_tipo_produzione,descrizione_codice_orientamento from " +
				" orientamenti_produttivi where  (? is null or tipo_produzione ilike ?) and codice_orientamento ilike ? and specie_allevata ilike ? ";

		PreparedStatement pst = db.prepareStatement(sel );
		pst.setString(1, this.tipoProduzione);
		pst.setString(2, this.tipoProduzione);
		pst.setString(3, this.flag_carne_latte);
		pst.setString(4, descrSpecie);
		ResultSet rs = pst.executeQuery(); 
		if (rs.next())
		{
			tipo_produzione = rs.getString("descrizione_tipo_produzione");
			orientamento_prod = rs.getString("descrizione_codice_orientamento");
		}
		else
		{
			tipo_produzione = "" ;
			orientamento_prod = "" ;
		}



	}
	
	public static String getTipoProduzione(Connection db, String tipoProduzione) throws SQLException
	{
		String sel = "select descrizione_tipo_produzione from orientamenti_produttivi where   tipo_produzione ilike ? limit 1 ";

		PreparedStatement pst = db.prepareStatement(sel );
		pst.setString(1, tipoProduzione);
		ResultSet rs = pst.executeQuery(); 
		if (rs.next())
		{
			return rs.getString("descrizione_tipo_produzione");
		}
		return null;


	}



	public  void getOrientamentoProduzioneAvicoli(Connection db,String codiceFlagCarneLatte) throws SQLException
	{
		String sel = "select descrizione_tipo_produzione,descrizione_codice_orientamento from " +
				" orientamenti_produttivi where  specie_allevata ilike 'gallus' and codice_orientamento ilike ? ";

		PreparedStatement pst = db.prepareStatement(sel );
		pst.setString(1,codiceFlagCarneLatte.trim());

		ResultSet rs = pst.executeQuery(); 
		if (rs.next())
		{
			tipo_produzione = rs.getString("descrizione_tipo_produzione");
			orientamento_prod = rs.getString("descrizione_codice_orientamento");
		}
		else
		{
			tipo_produzione = "" ;
			orientamento_prod = "" ;
		}



	}
	
	private int idAsl =-1 ;
	
	


	public int getIdAsl() {
		return idAsl;
	}
	public void setIdAsl(int idAsl) {
		this.idAsl = idAsl;
	}
	private int getidAsl()
	{
		int asl = -1 ;
		
//		

		if(this.aslCodice!=null)
		{
			if(this.aslCodice.equalsIgnoreCase("r101") || this.aslCodice.equalsIgnoreCase("r102")){
				asl = 201;

			}else if(this.aslCodice.equalsIgnoreCase("r103")){
				asl = 202;
			}
			else if(this.aslCodice.equalsIgnoreCase("r104") || this.aslCodice.equalsIgnoreCase("r105")){
				asl = 203;
			}
			else if(this.aslCodice.equalsIgnoreCase("r106")){
				asl = 204;
			}
			else if(this.aslCodice.equalsIgnoreCase("r107") || this.aslCodice.equalsIgnoreCase("r108")){
				asl = 205;
			}
			else if(this.aslCodice.equalsIgnoreCase("r109") || this.aslCodice.equalsIgnoreCase("r110")){
				asl = 206;
			}
			else if(this.aslCodice.equalsIgnoreCase("r111") || this.aslCodice.equalsIgnoreCase("r112") || this.aslCodice.equalsIgnoreCase("r113")){
				asl = 207;
			}
			else if(this.aslCodice.equalsIgnoreCase("r201") || this.aslCodice.equalsIgnoreCase("201") ){
				asl = 201;
			}
			else if(this.aslCodice.equalsIgnoreCase("r202") || this.aslCodice.equalsIgnoreCase("202") ){
				asl = 202;
			}
			else if(this.aslCodice.equalsIgnoreCase("r203") || this.aslCodice.equalsIgnoreCase("203") ){
				asl = 203;
			}
			else if(this.aslCodice.equalsIgnoreCase("r204") || this.aslCodice.equalsIgnoreCase("204") ){
				asl = 204;
			}
			else if(this.aslCodice.equalsIgnoreCase("r205") || this.aslCodice.equalsIgnoreCase("205") ){
				asl = 205;
			}
			else if(this.aslCodice.equalsIgnoreCase("r206") || this.aslCodice.equalsIgnoreCase("206") ){
				asl = 206;
			}
			else if(this.aslCodice.equalsIgnoreCase("r207") || this.aslCodice.equalsIgnoreCase("207") ){
				asl = 207;

			}
			idAsl = asl ;
		}
		return asl ;


	}

	
	public void insert(Connection db,ServletContext context) throws ParseException
	{

		try
		{

			String sel = "select org_id from organization where id_allevamento = ? and trashed_date is null";
			PreparedStatement pst1 = db.prepareStatement(sel);
			pst1.setString(1, this.id_allevamento);
			ResultSet rs = pst1.executeQuery();


			int orgId = DatabaseUtils.getNextSeqInt(db, context,"organization","org_id");
			String specieAllev = getSpecieAllevata(this.codice_specie,db) ;

			getOrientamentoProduzione(db,specieAllev);
			setCodiceComune(this.codice_azienda,db);
			String tipologiaStruttura = tipo_produzione ;
			if (rs.next())
			{



				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Timestamp dataFineAttivita=null;
				if (data_fine_attivita != null && !data_fine_attivita.equals(""))
					dataFineAttivita=new Timestamp (sdf.parse(data_fine_attivita).getTime());

				String updte = "update organization set name =? ,codice_fiscale_rappresentante =?, cf_detentore=?,partita_iva=?,orientamento_prod = ?,tipologia_strutt = ?,date2 =?,codice_tipo_allevamento=? where org_id = ?";
				PreparedStatement pst = db.prepareStatement(updte) ;
				
				pst.setString(1, denominazione);
				pst.setString(2, cod_fiscale);
				pst.setString(3, cod_fiscale_det);
				pst.setString(4, cod_fiscale);
				
				pst.setString(5, orientamento_prod);
				pst.setString(6, tipologiaStruttura);
				pst.setTimestamp(7, dataFineAttivita);
				pst.setString(8, codiceTipoAllevamento);
				pst.setInt(9, rs.getInt("org_id"));
				pst.execute();
				
				if (codFiscaleProp!=null && ! "".equals(codFiscaleProp))
				{
				String selOp= "select * from operatori_allevamenti where cf =?";
				pst = db.prepareStatement(selOp);
				pst.setString(1, codFiscaleProp);
				 rs = pst.executeQuery();
				if (!rs.next())
				{
					String insertOp = "insert into operatori_allevamenti ( id_asl,cf,nominativo) values(?,?,?)";
					PreparedStatement pst2 = db.prepareStatement(insertOp);
					pst2.setInt(1, idAsl);
					pst2.setString(2, codFiscaleProp);
					pst2.setString(3, nome_proprietario);
					pst2.execute();
					
				}
				}
			}
			else
			{

				String insert = "insert into organization "+ 
						"( org_id,"+
						"name,account_number,entered,enteredby,modified,modifiedby,enabled,"+
						"site_id,partita_iva,banca,cf_correntista,date1,tipologia,specie_allev,orientamento_prod,"+
						"tipologia_strutt,codice_fiscale_rappresentante, specie_allevamento , id_allevamento,cf_detentore,owner,codice_tipo_allevamento"+
						")"+
						"values"+ 
						"( ?,"+
						"? , ? ,current_date, 1,current_date,1,true," +
						"?,?,?,?,?,2,?,?,?,?,?,?," +
						"?,1,?" +
						");" +
						"insert into organization_address (org_id," +
						"address_type,addrline1,city,state,entered,enteredby,modified,modifiedby" +
						")" +
						"values" +
						"(?," +
						"1,?,?,?,current_date, 1,current_date,1" +
						")" ;

				int idAsl = getidAsl() ;
				
				

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Timestamp dataInizioAttivita= null ;
				{
					dataInizioAttivita=new Timestamp (sdf.parse(this.data_inizio_attivita).getTime());





					PreparedStatement pst = db.prepareStatement(insert) ;

					pst.setInt(1, orgId);
					pst.setString(2, this.denominazione);
					pst.setString(3, this.codice_azienda);
					pst.setInt(4, idAsl);
					pst.setString(5, this.cod_fiscale);
					pst.setString(6, this.denominazione);
					pst.setString(7, this.cod_fiscale);
					pst.setTimestamp(8, dataInizioAttivita);
					pst.setString(9, specieAllev);
					pst.setString(10, orientamento_prod);
					pst.setString(11, tipologiaStruttura);
					pst.setString(12, this.cod_fiscale);
					
					pst.setInt(13, Integer.parseInt(this.codice_specie));
					pst.setString(14, this.id_allevamento);
					pst.setString(15, this.cod_fiscale);
					pst.setString(16, codiceTipoAllevamento);
					pst.setInt(17, orgId);
					pst.setString(18, this.indirizzo);
					pst.setString(19, this.comune);
					pst.setString(20, this.provincia);
					

					pst.execute();
					
					
					if (codFiscaleProp!=null && ! "".equals(codFiscaleProp))
					{
					String selOp= "select * from operatori_allevamenti where cf =?";
					pst = db.prepareStatement(selOp);
					pst.setString(1, codFiscaleProp);
					 rs = pst.executeQuery();
					if (!rs.next())
					{
						String insertOp = "insert into operatori_allevamenti ( id_asl,cf,nominativo) values(?,?,?)";
						PreparedStatement pst2 = db.prepareStatement(insertOp);
						pst2.setInt(1, idAsl);
						pst2.setString(2, codFiscaleProp);
						pst2.setString(3, nome_proprietario);
						pst2.execute();
						
					}
					}
	
				}
				//			else
				//				update(db);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}



	}
	
	public int insert(Connection db,ActionContext context,int idUtente) throws ParseException
	{
		int orgId = -1;
		try
		{

			String sel = "select org_id from organization where id_allevamento = ? and trashed_date is null";
			PreparedStatement pst1 = db.prepareStatement(sel);
			pst1.setString(1, this.id_allevamento);
			ResultSet rs = pst1.executeQuery();


			 orgId = DatabaseUtils.getNextSeqInt(db, context,"organization","org_id");
			String specieAllev = getSpecieAllevata(this.codice_specie,db) ;

			setCodiceComune(this.codice_azienda,db);
			setDescComune(this.codComune, this.cap, db);
			String tipologiaStruttura = tipo_produzione ;
			if (rs.next())
			{

				/*
				 orgId = rs.getInt("org_id");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Timestamp dataFineAttivita=null;
				if (data_fine_attivita != null && !data_fine_attivita.equals(""))
					dataFineAttivita=new Timestamp (sdf.parse(data_fine_attivita).getTime());
				 String updte = "update organization set name =? ,codice_fiscale_rappresentante =?, cf_detentore=?,partita_iva=?,orientamento_prod = ?,tipologia_strutt = ?,date2 =?,codice_tipo_allevamento=? where org_id = ?";
				 
				PreparedStatement pst = db.prepareStatement(updte) ;
				
				pst.setString(1, denominazione);
				pst.setString(2, cod_fiscale);
				pst.setString(3, cod_fiscale_det);
				pst.setString(4, cod_fiscale);
				
				pst.setString(5, orientamento_prod);
				pst.setString(6, tipologiaStruttura);
				pst.setTimestamp(7, dataFineAttivita);
				pst.setString(8, codiceTipoAllevamento);
				pst.setInt(9, rs.getInt("org_id"));
				pst.execute();
				
				*/
				
				//NUOVA VERSIONE AGGIORNAMENTO
				orgId = rs.getInt("org_id");
				PreparedStatement pst = null;
						
				InfoAziendaBean azienda = new InfoAziendaBean();
				InfoAziendaBean aziendaBean = new InfoAziendaBean();
				InfoAziendaBean aziendaRet = aziendaBean.getInfoAllevamentoBean(codice_azienda,db,idUtente);
				
				
				String codAzienda = context.getRequest().getParameter("codAzienda");
				String denominazione = context.getRequest().getParameter("denominazione");
				String dataInizioAttivita = context.getRequest().getParameter("dataInizioAttivita");
				String dataFineAttivita = context.getRequest().getParameter("dataFineAttivita");
				String orientamentoProd = context.getRequest().getParameter("orientamento_prod");
				String specieAllevata = context.getRequest().getParameter("specie");
				String numeroCapiString = context.getRequest().getParameter("numeroCapi");
				String indirizzoLegale = context.getRequest().getParameter("indirizzoLegale");
				String comuneLegale = context.getRequest().getParameter("comuneLegale");
				String indirizzoOperativa = aziendaRet.getIndirizzo_Azienda();
				String codComuneOperativa = aziendaRet.getCod_comune_azienda();
				String istatComuneOperativa = aziendaRet.getComuneAziendaCalcolato();
				String capOperativa = aziendaRet.getCap_azienda();
				String latitudineOperativa = (aziendaRet.getLatitudine()!=null)?(aziendaRet.getLatitudine()+""):("");
				String longitudineOperativa = (aziendaRet.getLatitudine()!=null)?(aziendaRet.getLongidutine()+""):("");
				String codiceFiscaleProp = context.getRequest().getParameter("codiceFiscaleProp");
				String codiceFiscaleDet = context.getRequest().getParameter("codiceFiscaleDet");
				String tipologia_strutt = context.getRequest().getParameter("tipologia_strutt");
				
				
				if (dataFineAttivita != null && !dataFineAttivita.equals("null") && !dataFineAttivita.equals("")) 
				{
					setData_fine_attivita(dataFineAttivita);
				}
				if (dataInizioAttivita != null && !dataInizioAttivita.equals("null") && !dataInizioAttivita.equals("")) 
				{
					setData_inizio_attivita(dataInizioAttivita);
				}
				
				setOrientamento_prod(orientamentoProd);
				setTipo_produzione(tipologia_strutt);
				setCodice_specie(specieAllevata);
				setNum_capi_totali(numeroCapiString);
				setModifiedBy(idUtente);
				setIndirizzo(indirizzoLegale);
				setComune(comuneLegale);
				setCodice_specie(specieAllevata);
				setCodice_azienda(codAzienda);
				setCodFiscaleProp(codiceFiscaleProp);
				setCod_fiscale_det(codiceFiscaleDet);
				setDenominazione(denominazione);
				sincronizzaBdn(db,orgId);
				
				azienda.setCod_azienda(codAzienda);
				azienda.setIndirizzo_Azienda(indirizzoOperativa);
				azienda.setCod_comune_azienda(codComuneOperativa);
				azienda.setComuneAziendaCalcolato(istatComuneOperativa);
				azienda.setCap_azienda(capOperativa);
				if(latitudineOperativa!=null && !latitudineOperativa.equals(""))
					azienda.setLatitudine(Double.parseDouble(latitudineOperativa));
				if(longitudineOperativa!=null && !longitudineOperativa.equals(""))
					azienda.setLongidutine(Double.parseDouble(longitudineOperativa));
				azienda.sincronizzaBdn(db, orgId);
				//FINE NUOVA VERSIONE AGGIORNAMENTO
				
				
				
				context.getRequest().setAttribute("EsitoImport", "Azienda ["+denominazione+"] / ["+cod_fiscale_det+"] aggiornata in anagrafica.");

				if (codFiscaleProp!=null && ! "".equals(codFiscaleProp))
				{
				String selOp= "select * from operatori_allevamenti where cf =?";
				pst = db.prepareStatement(selOp);
				pst.setString(1, codFiscaleProp);
				 rs = pst.executeQuery();
				if (!rs.next())
				{
					String insertOp = "insert into operatori_allevamenti ( id_asl,cf,nominativo) values(?,?,?)";
					PreparedStatement pst2 = db.prepareStatement(insertOp);
					pst2.setInt(1, idAsl);
					pst2.setString(2, codFiscaleProp);
					pst2.setString(3, nome_proprietario);
					pst2.execute();
					
				}
				}
			}
			else
			{

				String insert = "insert into organization "+ 
						"( org_id,"+
						"name,account_number,entered,enteredby,modified,modifiedby,enabled,"+
						"site_id,partita_iva,banca,cf_correntista,date1,date2,tipologia,specie_allev,orientamento_prod,"+
						"tipologia_strutt,codice_fiscale_rappresentante, specie_allevamento , id_allevamento,cf_detentore,owner,codice_tipo_allevamento,miscela"+
						")"+
						"values"+ 
						"( ?,"+
						"? , ? ,current_date, 1,current_date,1,true," +
						"?,?,?,?,?,?,2,?,?,?,?,?,?," +
						"?,1,?,true" +
						");" +
						"insert into organization_address (org_id," +
						"address_type,addrline1,city,state,entered,enteredby,modified,modifiedby,latitude,longitude" +
						")" +
						"values" +
						"(?," +
						"1,?,?,?,current_date, 1,current_date,1,?,?" +
						")" ;

				int idAsl = getidAsl() ;
				
				

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Timestamp dataInizioAttivita= null ;
				Timestamp dataFineAttivita= null ;
				{
					dataInizioAttivita=new Timestamp (sdf.parse(this.data_inizio_attivita).getTime());
					if(this.data_fine_attivita!=null)
						dataFineAttivita=new Timestamp (sdf.parse(this.data_fine_attivita).getTime());





					PreparedStatement pst = db.prepareStatement(insert) ;

					pst.setInt(1, orgId);
					pst.setString(2, this.denominazione);
					pst.setString(3, this.codice_azienda);
					pst.setInt(4, idAsl);
					pst.setString(5, this.cod_fiscale);
					pst.setString(6, this.denominazione);
					pst.setString(7, this.cod_fiscale);
					pst.setTimestamp(8, dataInizioAttivita);
					pst.setTimestamp(9, dataFineAttivita);
					pst.setString(10, specieAllev);
					pst.setString(11, orientamento_prod);
					pst.setString(12, tipologiaStruttura);
					pst.setString(13, this.cod_fiscale);
					
					pst.setInt(14, Integer.parseInt(this.codice_specie));
					pst.setString(15, this.id_allevamento);
					pst.setString(16, this.cod_fiscale);
					pst.setString(17, codiceTipoAllevamento);
					pst.setInt(18, orgId);
					pst.setString(19, this.indirizzo);
					pst.setString(20, this.descComune);
					pst.setString(21, this.provincia);
					if(this.latitudine!=null)
						pst.setDouble(22, Double.parseDouble(this.latitudine));
					else
						pst.setObject(22, null);
					if(this.longitudine!=null)
						pst.setDouble(23, Double.parseDouble(this.longitudine));
					else
						pst.setObject(23, null);
					

					pst.execute();
					context.getRequest().setAttribute("EsitoImport", "Azienda ["+denominazione+"] / ["+codice_azienda+"] importata in anagrafica.");
					
					if (codFiscaleProp!=null && ! "".equals(codFiscaleProp))
					{
					String selOp= "select * from operatori_allevamenti where cf =?";
					pst = db.prepareStatement(selOp);
					pst.setString(1, codFiscaleProp);
					 rs = pst.executeQuery();
					if (!rs.next())
					{
						String insertOp = "insert into operatori_allevamenti ( id_asl,cf,nominativo) values(?,?,?)";
						PreparedStatement pst2 = db.prepareStatement(insertOp);
						pst2.setInt(1, idAsl);
						pst2.setString(2, codFiscaleProp);
						pst2.setString(3, nome_proprietario);
						pst2.execute();
						
					}
					}
	
				}
				//			else
				//				update(db);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

return orgId;

	}


	public void update(Connection db) throws ParseException
	{

		try
		{

			String sel = "select org_id from organization where id_allevamento = ?";
			PreparedStatement pst1 = db.prepareStatement(sel);
			pst1.setString(1, this.id_allevamento);
			ResultSet rs = pst1.executeQuery();
			if ( rs.next())
			{
				int orgId= rs.getInt(1);





				String insert = "update organization "+ 

		"set name = ? ,modified = current_timestamp"+
		",banca= ?,cf_correntista= ? "+
		",codice_fiscale_rappresentante= ?, cf_detentore = ? where org_id =?";

				int idAsl = getidAsl() ;

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Timestamp dataInizioAttivita= null ;
				{
					dataInizioAttivita=new Timestamp (sdf.parse(this.data_inizio_attivita).getTime());

					String specieAllev = getSpecieAllevata(this.codice_specie,db) ;

					getOrientamentoProduzione(db,specieAllev);
					setCodiceComune(this.codice_azienda,db);
					String tipologiaStruttura = tipo_produzione ;



					PreparedStatement pst = db.prepareStatement(insert) ;

					pst.setString(1, this.denominazione);

					pst.setString(2, this.denominazione);
					pst.setString(3, this.cod_fiscale);

					pst.setString(4, this.cod_fiscale);

					pst.setString(5, this.cod_fiscale);
					pst.setInt(6, orgId);


					pst.execute();

				}
			}}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

	}
	
	

	public void sincronizzaBdnSimilOpu(Connection db,int altId,int num_capi) throws ParseException 
	{
		try
		{
			String specieAllev = getSpecieAllevata(this.codice_specie,db) ;

			getOrientamentoProduzione(db,specieAllev);

			String updte = "update opu_istanza_attivita_in_allevamento set data_fine_attivita =?::timestamp,numero_capi=?,  note_hd = concat_ws(';', note_hd, 'Sincronizzato con BDN da utente', ?, now())  where alt_id = ?";
			PreparedStatement pst = db.prepareStatement(updte) ;
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			if (data_fine_attivita != null &&  !"".equals(data_fine_attivita))
			{
				try {
					pst.setTimestamp(2, new Timestamp(sdf.parse(data_fine_attivita).getTime()));
				} catch (ParseException e) {
					sdf = new SimpleDateFormat("yyyy-MM-dd");
					pst.setTimestamp(2, new Timestamp(sdf.parse(data_fine_attivita).getTime()));
				}
			}
			else
				pst.setTime(2, null);
			pst.setInt(3,num_capi);
			
			pst.setInt(4, modifiedBy);

			pst.setInt(5, altId);
			pst.execute();
			
			
			RicercheAnagraficheTab.insertAllevamentoNuovoModello(db, altId);



		}
		catch(SQLException e)
		{

		}
	}
	
	
	public void sincronizzaBdn(Connection db,int orgId) throws ParseException 
	{
		try 
		{

			String note = "Aggiornato da BDN da utente "+modifiedBy+ " con questi dati: [[ DATA INIZIO: "+data_inizio_attivita+"; DATA FINE: "+data_fine_attivita+"; NUMERO CAPI: "+num_capi_totali+"; INDIRIZZO: "+indirizzo+"; COMUNE: "+comune+"]]";
			//AGGIORNO ORGANIZATION
			String updateOrg = "update organization set codice_tipo_allevamento = ?, name = ?, cf_correntista = ?, codice_fiscale_rappresentante = ?, date1= ?::timestamp, date2 =?::timestamp,numero_capi=?, note_hd = concat_ws(';', note_hd, now(), ?), modifiedby = ?, modified = now() where org_id = ?";
			updateOrg = "update organization set partita_iva = ?, codice_tipo_allevamento = ?, name = ?, cf_correntista = ?, codice_fiscale_rappresentante = ?, date1= ?::timestamp, date2 =?::timestamp,numero_capi=?, note_hd = concat_ws(';', note_hd, now(), ?), modifiedby = ?, modified = now(), orientamento_prod = ?, tipologia_strutt = ?, specie_allev = ?, specie_allevamento = ?::integer where org_id = ?";
			PreparedStatement pst = db.prepareStatement(updateOrg) ;
			int i = 0;
			
			pst.setString(++i, cod_fiscale);
			pst.setString(++i, codiceTipoAllevamento);
			pst.setString(++i, denominazione);
			pst.setString(++i, cod_fiscale_det);
			pst.setString(++i, codFiscaleProp);
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if (data_inizio_attivita != null &&  !"".equals(data_inizio_attivita))
			{
				try {
					pst.setTimestamp(++i, new Timestamp(sdf.parse(data_inizio_attivita).getTime()));
				} catch (ParseException e) {
					sdf = new SimpleDateFormat("yyyy-MM-dd");
					pst.setTimestamp(++i, new Timestamp(sdf.parse(data_inizio_attivita).getTime()));
				}
			}
			else
				pst.setTime(++i, null);
			
			if (data_fine_attivita != null &&  !"".equals(data_fine_attivita))
			{
				try {
					pst.setTimestamp(++i, new Timestamp(sdf.parse(data_fine_attivita).getTime()));
				} catch (ParseException e) {
					sdf = new SimpleDateFormat("yyyy-MM-dd");
					pst.setTimestamp(++i, new Timestamp(sdf.parse(data_fine_attivita).getTime()));
				}
			}
			else
				pst.setTime(++i, null);
		
			pst.setInt(++i,Integer.parseInt(num_capi_totali));
			pst.setString(++i, note);
			pst.setInt(++i, modifiedBy);
			pst.setString(++i, orientamento_prod);  
			pst.setString(++i, tipo_produzione);  
			pst.setString(++i, getSpecieAllevata(codice_specie,db)); 
			pst.setString(++i,codice_specie);  
			pst.setInt(++i, orgId); 
			
			System.out.println("QUERY AGGIORNAMENTO ALLEVAMENTO: " + pst.toString());
			pst.executeUpdate();
			 
			//AGGIORNO INDIRIZZO SEDE LEGALE
			String updateAziende = "update organization_address set addrline1 = ?, city = ?, state = (select description from lookup_province where code in (select cod_provincia::integer from comuni1 where nome ilike ?)), postalcode = ?, notes = ?, modifiedby = ? where org_id = ? and address_type = 1 and trasheddate is null";
			pst = db.prepareStatement(updateAziende) ;
			i=0;
			pst.setString(++i, indirizzo!=null ? indirizzo.trim() : "");
			pst.setString(++i, comune!=null ? comune.trim() : "");
			pst.setString(++i, comune!=null ? comune.trim() : "");
			pst.setString(++i, cap!=null ? cap.trim() : "");
			pst.setString(++i, note!=null ? note.trim() : "");
			pst.setInt(++i, modifiedBy);
			pst.setInt(++i, orgId);
			
			System.out.println("QUERY AGGIORNAMENTO ALLEVAMENTO (AZIENDE): " + pst.toString());
			pst.executeUpdate();

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void sincronizzaBdn_OLD(Connection db,int orgId,int num_capi) throws ParseException 
	{
		try
		{
			String specieAllev = getSpecieAllevata(this.codice_specie,db) ;

			getOrientamentoProduzione(db,specieAllev);

			String updte = "update organization set orientamento_prod = ?,date2 =?::timestamp,numero_capi=?, codice_tipo_allevamento="+codiceTipoAllevamento+", note_hd = concat_ws(';', note_hd, 'Sincronizzato con BDN da utente', ?, now())  where org_id = ?";
			PreparedStatement pst = db.prepareStatement(updte) ;
			pst.setString(1, orientamento_prod);
			

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			if (data_fine_attivita != null &&  !"".equals(data_fine_attivita))
			{
				try {
					pst.setTimestamp(2, new Timestamp(sdf.parse(data_fine_attivita).getTime()));
				} catch (ParseException e) {
					sdf = new SimpleDateFormat("yyyy-MM-dd");
					pst.setTimestamp(2, new Timestamp(sdf.parse(data_fine_attivita).getTime()));
				}
			}
			else
				pst.setTime(2, null);
			pst.setInt(3,num_capi);
			
			pst.setInt(4, modifiedBy);

			pst.setInt(5, orgId);
			pst.execute();



		}
		catch(SQLException e)
		{

		}
	}
	
	public void sincronizzaopuBdn(Connection db,int idStabilimento, int idRel) throws ParseException 
	{
		try
		{
			String specieAllev = getSpecieAllevata(this.codice_specie,db) ;
			getOrientamentoProduzione(db,specieAllev);
			
			int idNumeroCapi = -1;
			int idOrientamentoProd = -1;
			int idCodiceTipoAll = -1;
			
			String selectNumeroCapi = "select id from opu_campi_estesi_lda where label_campo ilike 'numero capi' and id_rel_stab_lp = ?";
			PreparedStatement pstNumeroCapi = db.prepareStatement(selectNumeroCapi);
			pstNumeroCapi.setInt(1, idRel);
			ResultSet rsNumeroCapi = pstNumeroCapi.executeQuery();
			if (rsNumeroCapi.next())
			{
				idNumeroCapi = rsNumeroCapi.getInt(1);
			}
			
			String selectOrientamentoProd = "select id from opu_campi_estesi_lda where label_campo ilike 'orientamento prod' and id_rel_stab_lp = ?";
			PreparedStatement pstOrientamentoProd = db.prepareStatement(selectOrientamentoProd);
			pstOrientamentoProd.setInt(1, idRel);
			ResultSet rsOrientamentoProd = pstOrientamentoProd.executeQuery();
			if (rsOrientamentoProd.next())
			{
				idOrientamentoProd = rsOrientamentoProd.getInt(1);
			}
			
			String selectCodiceTipoAll = "select id from opu_campi_estesi_lda where label_campo ilike 'codice tipo allevamento' and id_rel_stab_lp = ?";
			PreparedStatement pstCodiceTipoAll = db.prepareStatement(selectCodiceTipoAll);
			pstCodiceTipoAll.setInt(1, idRel);
			ResultSet rsCodiceTipoAll = pstCodiceTipoAll.executeQuery();
			if (rsCodiceTipoAll.next())
			{
				idCodiceTipoAll = rsCodiceTipoAll.getInt(1);
			}
			
		
			if (idNumeroCapi > 0) {
				String update = "update  opu_campi_estesi_lda set valore_campo = ? where id = ?";
				PreparedStatement pst = db.prepareStatement(update);
				pst.setInt(1, idNumeroCapi);
				pst.setString(2, num_capi_totali);
				pst.executeUpdate();
			}
			else {
				String insert = "insert into opu_campi_estesi_lda (id_rel_stab_lp, label_campo, tipo_campo, valore_campo) values (?, ?, ?, ?)";
				PreparedStatement pst = db.prepareStatement(insert);
				pst.setInt(1, idRel);
				pst.setString(2, "NUMERO CAPI");
				pst.setString(3, "TEXT");
				pst.setString(4, num_capi_totali);
				pst.execute();
			}

			if (idOrientamentoProd > 0) {
				String update = "update  opu_campi_estesi_lda set valore_campo = ? where id = ?";
				PreparedStatement pst = db.prepareStatement(update);
				pst.setInt(1, idOrientamentoProd);
				pst.setString(2, orientamento_prod);
				pst.executeUpdate();
			}
			else {
				String insert = "insert into opu_campi_estesi_lda (id_rel_stab_lp, label_campo, tipo_campo, valore_campo) values (?, ?, ?, ?)";
				PreparedStatement pst = db.prepareStatement(insert);
				pst.setInt(1, idRel);
				pst.setString(2, "ORIENTAMENTO PROD");
				pst.setString(3, "TEXT");
				pst.setString(4, orientamento_prod);
				pst.execute();
			}
	
			if (idCodiceTipoAll > 0) {
				String update = "update  opu_campi_estesi_lda set valore_campo = ? where id = ?";
				PreparedStatement pst = db.prepareStatement(update);
				pst.setInt(1, idCodiceTipoAll);
				pst.setString(2, codiceTipoAllevamento);
				pst.executeUpdate();
			}
			else {
				String insert = "insert into opu_campi_estesi_lda (id_rel_stab_lp, label_campo, tipo_campo, valore_campo) values (?, ?, ?, ?)";
				PreparedStatement pst = db.prepareStatement(insert);
				pst.setInt(1, idRel);
				pst.setString(2, "CODICE TIPO ALLEVAMENTO");
				pst.setString(3, "TEXT");
				pst.setString(4, codiceTipoAllevamento);
				pst.execute();
			}

		}
		catch(SQLException e)
		{

		}
	}


	public void setOrientamento_prod(Connection db){
		String orientamento = "";
		String sql = "  select o.descrizione_codice_orientamento from orientamenti_produttivi o left join lookup_specie_allevata s on s.description = o.specie_allevata  where o.tipo_produzione ilike ? and o.codice_orientamento ilike ? and s.short_description ilike ?";
		PreparedStatement pst;
		try {
			pst = db.prepareStatement(sql);
			pst.setString(1, (tipoProduzione!=null) ? tipoProduzione : "AL");
			pst.setString(2, flag_carne_latte);
			pst.setString(3, codice_specie);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				orientamento = rs.getString(1);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.orientamento_prod= orientamento;
	}
	public int getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	
	private static String costruisciEnvelope(String tipoServizio, String codAzienda, String idFiscale)
	{
		return   "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://acquacoltura.izs.it/services\"><soapenv:Header><ser:SOAPAutorizzazione><ruolo>regione</ruolo></ser:SOAPAutorizzazione><ser:SOAPAutenticazione><password>" + ApplicationProperties.getProperty("PASSWORD_WS_BDN") + "</password><username>" + ApplicationProperties.getProperty("USERNAME_WS_BDN") + "</username></ser:SOAPAutenticazione></soapenv:Header><soapenv:Body><ser:get" + tipoServizio + "><codice>" + codAzienda + "</codice>" + (idFiscale!=null ? "<idFiscaleProp>" + idFiscale + "</idFiscaleProp>" : "") + "</ser:get" + tipoServizio + "></soapenv:Body></soapenv:Envelope>";
	}
	
}
