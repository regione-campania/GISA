/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.izsmibr.service;



import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

import org.aspcfs.modules.izsmibr.base.GisaDsEsitoIBRA;
import  org.aspcfs.modules.izsmibr.service.*;
import org.apache.axis.message.SOAPHeaderElement;
import org.aspcfs.modules.util.imports.ApplicationProperties;

public class GisaWsIbr {

	

	public static void sendRecordIBRBDN(String recordXML,GisaDsEsitoIBRA record) throws Exception  {

		WsPianoIBRSoapStub stub_piano = null;

		try {
			
			String userName = ApplicationProperties.getProperty("USERNAME_WS_BDN");
			String passWord = ApplicationProperties.getProperty("PASSWORD_WS_BDN");
			
			stub_piano = (WsPianoIBRSoapStub) (new WsPianoIBRLocator().getwsPianoIBRSoap());

			SOAPHeaderElement header = new SOAPHeaderElement(new QName("http://bdr.izs.it/webservices", "SOAPAutenticazione", "web"));
			SOAPElement node = header.addChildElement("username");
			node.addTextNode(userName);
			SOAPElement node2 = header.addChildElement("password");
			node2.addTextNode(passWord);
			// INSERIMENTO HEADER NEGLI STUB
			stub_piano.setHeader(header);
			// RECUPERO RISPOSTA WEBSERVICES getAllevamento_STR
//			recordXML="<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><dsESITO_IBR_IUS><PARAMETERS_LIST><P_EIBR_ID>0</P_EIBR_ID><P_CODICE_ISTITUTO>03</P_CODICE_ISTITUTO><P_CODICE_SEDE_DIAGNOSTICA>01</P_CODICE_SEDE_DIAGNOSTICA><P_ANNO_ACCETTAZIONE>2015</P_ANNO_ACCETTAZIONE><P_NUMERO_ACCETTAZIONE>159215</P_NUMERO_ACCETTAZIONE><P_CODICE_AZIENDA>023BN156</P_CODICE_AZIENDA><P_ID_FISCALE_PROPRIETARIO>MZZNNL85H49A783A</P_ID_FISCALE_PROPRIETARIO><P_SPECIE_ALLEVATA>0121</P_SPECIE_ALLEVATA><P_CODICE_CAPO>IT041990056835</P_CODICE_CAPO><P_DATA_PRELIEVO>2015-12-30</P_DATA_PRELIEVO><P_DATA_ESITO>2016-01-11</P_DATA_ESITO><P_ESITO_QUALITATIVO>NEGATIVO</P_ESITO_QUALITATIVO></PARAMETERS_LIST></dsESITO_IBR_IUS>]]>";
//			System.out.println("Recurd Inviato "+recordXML);
//			System.out.println("----------------------------------------------------------------------------------");
			InsertEsitoPrelievoIBRResponseInsertEsitoPrelievoIBRResult risultato = stub_piano.insertEsitoPrelievoIBR(recordXML);
			
			record.setTracciato_record_risposta(risultato.get_any()[0].getAsString());
			
			if (risultato.get_any()[0].getAsDOM().getElementsByTagName("des")!=null && "".equals(risultato.get_any()[0].getAsDOM().getElementsByTagName("des").item(0).getTextContent().trim()) )
				{record.setEsito_invio("OK");
				
				record.getRecord().getPARAMETERSLIST().get(0).setPEIBRID(new BigDecimal(risultato.get_any()[0].getAsDOM().getElementsByTagName("EIBR_ID").item(0).getTextContent()));
				}
			else
			{
				record.setEsito_invio("KO");
				record.setErrore(risultato.get_any()[0].getAsDOM().getElementsByTagName("error").item(0).getTextContent());
			}
			System.out.println("----------------------------------------------------------------------------------");
			

	} catch (SOAPException e) {
		
		throw e ;
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		throw e ;
	} catch (ServiceException e) {
		// TODO Auto-generated catch block
		throw e ;
	}

}

}
