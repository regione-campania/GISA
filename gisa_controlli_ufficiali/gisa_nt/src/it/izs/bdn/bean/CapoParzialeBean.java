/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.izs.bdn.bean;

import java.rmi.RemoteException;

import it.izs.bdn.action.utilsXML;
import it.izs.bdn.webservices.WsAnagraficaCapoQryLocator;
import it.izs.bdn.webservices.WsAnagraficaCapoQrySoapStub;
import it.izs.bdn.webservices.WsAziendeQryLocator;
import it.izs.bdn.webservices.WsAziendeQrySoapStub;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

import org.apache.axis.message.SOAPHeaderElement;
import org.aspcfs.modules.util.imports.ApplicationProperties;

public class CapoParzialeBean {
	
	private final static int COD_ERR_NotInBDN     		  =  1;
	private final static int COD_ERR_ServiceException     = -1;
	private final static int COD_ERR_SOAPException        = -2;
	private final static int COD_ERR_RemoteException      = -3;
	private final static int COD_ERR_NullPointerException = -4;
	private final static int COD_ERR_Exception            = -5;
	private final static int COD_ERR_NonPrevisto          = -6;
	
	
	private String codice_azienda       ;
	private String specie_capo          ;
	private String sesso_capo           ;
	private String data_nascita_capo 	;
	private String razza				;
	private String istatAsl             ;
	private String comune               ;
	
	private String errore1              ;
	private String errore2              ;
	private int cod_errore				;
	
	public String getCodice_azienda() {
		return codice_azienda;
	}
	public void setCodice_azienda(String codiceAzienda) {
		codice_azienda = codiceAzienda;
	}
	public String getSpecie_capo() {
		return specie_capo;
	}
	public void setSpecie_capo(String specieCapo) {
		specie_capo = specieCapo;
	}
	public String getSesso_capo() {
		return sesso_capo;
	}
	public void setSesso_capo(String sessoCapo) {
		sesso_capo = sessoCapo;
	}
	public String getData_nascita_capo() {
		return data_nascita_capo;
	}
	public void setData_nascita_capo(String dataNascitaCapo) {
		data_nascita_capo = dataNascitaCapo;
	}
	public String getRazza() {
		return razza;
	}
	public void setRazza(String razza) {
		this.razza = razza;
	}
	public String getIstatAsl() {
		return istatAsl;
	}
	public void setIstatAsl(String istatAsl) {
		this.istatAsl = istatAsl;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	
	
	public String getErrore1() {
		return errore1;
	}
	public void setErrore1(String errore1) {
		this.errore1 = errore1;
	}
	public String getErrore2() {
		return errore2;
	}
	public void setErrore2(String errore2) {
		this.errore2 = errore2;
	}
	public int getCod_errore() {
		return cod_errore;
	}
	public void setCod_errore(int codErrore) {
		cod_errore = codErrore;
	}
	public static CapoParzialeBean getCapoParzialeBean(String codice_capo) {
		
		CapoParzialeBean cpbReturn = new CapoParzialeBean();
		
		// DEFINIZIONE CREDENZIALI DI ACCESSO AL WEBSERVICES
		String userName = ApplicationProperties.getProperty("USERNAME_WS_BDN");
		String passWord = ApplicationProperties.getProperty("PASSWORD_WS_BDN");
		
		// DEFINIZIONE NODI DA RECUPERARE PER IL CAPO //
		String nodo_capo_razza_id 	   = "RAZZA_CODICE";
		String nodo_capo_sesso 		   = "SESSO";
		String nodo_capo_data_nascita  = "DT_NASCITA";
		String nodo_capo_azieda_codice = "AZIENDA_CODICE";
		String nodo_capo_codice_specie = "SPE_CODICE";
		String nodo_capo_errore 	   = "error_info";
		
		// DEFINIZIONE VALORI DA RECUPERARE PER IL CAPO //
		String valori_capo_razza_id 	    = null;
		String valori_capo_sesso 		  	= null;
		String valori_capo_data_nascita  	= null;
		String valori_capo_azieda_codice 	= null;
		String valori_capo_codice_specie 	= null;
		String valori_capo_errore		 	= null;
		
		// DEFINIZIONE NODI DA RECUPERARE PER L'AZIENDA //
		String nodo_azienda_asl_codice	   = "ASL_CODICE";
		String nodo_azienda_comune_codice  = "COM_CODICE";
		String nodo_azienda_errore 	   	   = "error_info";
		
		// DEFINIZIONE NODI DA RECUPERARE PER L'AZIENDA //
		String valori_azienda_asl_codice   = null;
		String valori_azienda_comune_codice= null;
		String valori_azienda_errore	   = null;

		// DEFINIZIONE DOCUMENTO RISPOSTA DA WEBSERVICES
		String documento_xml_costruito_capo    = null; 
		String documento_xml_costruito_azienda = null; 
		
		WsAnagraficaCapoQrySoapStub stub_WsAnagraficaCapoQry;
		WsAziendeQrySoapStub stub_WsAziendeQry;
		
		if (codice_capo==null) {
			cpbReturn.setErrore1("[CapoParzialeBean.getCapoParzialeBean()] - CODICE CAPO NON INSERITO");
		} else {
				try {
					stub_WsAnagraficaCapoQry = (WsAnagraficaCapoQrySoapStub) (new WsAnagraficaCapoQryLocator().getwsAnagraficaCapoQrySoap());
					stub_WsAziendeQry = (WsAziendeQrySoapStub) (new WsAziendeQryLocator().getwsAziendeQrySoap());
					// DEFINIZIONE DELL'HEADER PER L'AUTENTICAZIONE
					SOAPHeaderElement header = new SOAPHeaderElement(new QName("http://bdr.izs.it/webservices", "SOAPAutenticazione", "web"));
					SOAPElement node = header.addChildElement("username");
					node.addTextNode(userName);
					SOAPElement node2 = header.addChildElement("password");
					node2.addTextNode(passWord);
					
					// INSERIMENTO HEADER NEGLI STUB
					stub_WsAnagraficaCapoQry.setHeader(header);
					stub_WsAziendeQry.setHeader(header);
					
					// RECUPERO RISPOSTA WEBSERVICES getCapo_STR
					documento_xml_costruito_capo = stub_WsAnagraficaCapoQry.getCapo_STR(codice_capo);
					
					// RECUPERO VALORI DALLA RISPOSTA di getCapo_STR
					valori_capo_sesso 		     =  utilsXML.getValoreNodoXML(documento_xml_costruito_capo, nodo_capo_sesso		   );
					valori_capo_razza_id 	     =  utilsXML.getValoreNodoXML(documento_xml_costruito_capo, nodo_capo_razza_id	   );
					valori_capo_data_nascita     =  utilsXML.getValoreNodoXML(documento_xml_costruito_capo, nodo_capo_data_nascita );
					valori_capo_azieda_codice    =  utilsXML.getValoreNodoXML(documento_xml_costruito_capo, nodo_capo_azieda_codice);
					valori_capo_codice_specie    =  utilsXML.getValoreNodoXML(documento_xml_costruito_capo, nodo_capo_codice_specie);
					valori_capo_errore		     =  utilsXML.getValoreNodoXML(documento_xml_costruito_capo, nodo_capo_errore       );
					
					// RECUPERO RISPOSTA WEBSERVICES getAzienda_STR
					if (valori_capo_azieda_codice != null) { 
						documento_xml_costruito_azienda = stub_WsAziendeQry.getAzienda_STR(valori_capo_azieda_codice);
						
						// RECUPERO RISPOSTA WEBSERVICES getAzienda_STR
						valori_azienda_asl_codice    = utilsXML.getValoreNodoXML(documento_xml_costruito_azienda,  nodo_azienda_asl_codice 	 );
						valori_azienda_comune_codice = utilsXML.getValoreNodoXML(documento_xml_costruito_azienda,  nodo_azienda_comune_codice);
						valori_azienda_errore		 =  utilsXML.getValoreNodoXML(documento_xml_costruito_azienda, nodo_azienda_errore       );
					}
					
					cpbReturn.setSesso_capo(valori_capo_sesso);
					cpbReturn.setRazza(valori_capo_razza_id);
					cpbReturn.setData_nascita_capo(valori_capo_data_nascita);
					cpbReturn.setCodice_azienda(valori_capo_azieda_codice);
					cpbReturn.setSpecie_capo(valori_capo_codice_specie);
					
					cpbReturn.setIstatAsl(valori_azienda_asl_codice);
					cpbReturn.setComune(valori_azienda_comune_codice);
					
					if (valori_capo_errore!=null ) {				
						if (!valori_capo_errore.equals("1") ) 			
							cpbReturn.setErrore1(valori_capo_errore);
						else
							cpbReturn.setErrore1(null);
					}
					
					if (valori_azienda_errore!=null ) {
						if (!valori_azienda_errore.equals("1") ) 		
							cpbReturn.setErrore2(valori_azienda_errore);
						else
							cpbReturn.setErrore2(null);
					}
					
				} catch (ServiceException e) {
					cpbReturn = new CapoParzialeBean();
					cpbReturn.setCod_errore(COD_ERR_ServiceException);
					cpbReturn.setErrore1("[CapoParzialeBean.getCapoParzialeBean()] - E' occorso un errore ServiceException : " + e );
					//cpbReturn = null;
					//e.printStackTrace();
				} catch (SOAPException e) {
					cpbReturn = new CapoParzialeBean();
					cpbReturn.setCod_errore(COD_ERR_SOAPException);
					cpbReturn.setErrore1("[CapoParzialeBean.getCapoParzialeBean()] - E' occorso un errore SOAPException : " + e );
					//cpbReturn = null;
					//e.printStackTrace();
				} catch (RemoteException e) {
					cpbReturn = new CapoParzialeBean();
					cpbReturn.setCod_errore(COD_ERR_RemoteException);
					cpbReturn.setErrore1("[CapoParzialeBean.getCapoParzialeBean()] - E' occorso un errore RemoteException : " + e );
					//cpbReturn = null;
					//e.printStackTrace();
				} catch (java.lang.NullPointerException e) {
					cpbReturn = new CapoParzialeBean();
					cpbReturn.setCod_errore(COD_ERR_NullPointerException);
					cpbReturn.setErrore1("[CapoParzialeBean.getCapoParzialeBean()] - E' occorso un errore NullPointerException : " + e );
					//cpbReturn = null;
					//e.printStackTrace();
				} catch (Exception e) {
					cpbReturn = new CapoParzialeBean();
					cpbReturn.setCod_errore(COD_ERR_Exception);
					cpbReturn.setErrore1("[CapoParzialeBean.getCapoParzialeBean()] - E' occorso un errore Exception : " + e );
					//cpbReturn = null;
					//e.printStackTrace();
				}
		}

		if ( cpbReturn.getErrore1() == null && cpbReturn.getErrore2() == null ){}
		else if ( cpbReturn.getErrore1() != null ) {
			if(cpbReturn.getErrore1().contains("NON PRESENTE IN ANAGRAFE")){
				cpbReturn.setCod_errore(COD_ERR_NotInBDN);
				cpbReturn.setErrore1("[CapoParzialeBean.getCapoParzialeBean()] - Capo non presente in anagrafica BDN");
			}
			else{
				cpbReturn.setCod_errore(COD_ERR_NonPrevisto);
			}
		} else if ( cpbReturn.getErrore2() != null ) {
			cpbReturn.setCod_errore(COD_ERR_NonPrevisto);
		}
		
		return cpbReturn;
	}
	
	
	
}
