/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.izs.bdn.bean;

import it.izs.bdn.action.utilsXML;
import it.izs.bdn.webservices.WsAnagraficaCapoQryLocator;
import it.izs.bdn.webservices.WsAnagraficaCapoQrySoapStub;
import it.izs.bdn.webservices.WsAziendeQryLocator;
import it.izs.bdn.webservices.WsAziendeQrySoapStub;
import it.izs.ws.WsPost;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

import org.apache.axis.message.SOAPHeaderElement;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.GestoreConnessioni;
import org.directwebremoting.extend.LoginRequiredException;

public class InfoPersonaBean {

	private String cf ; 
	private String nominativo ; 
	private String indirizzo ; 
	private String telefono ; 
	private String cap ; 
	private String comune ;
	private String siglaProvincia ; 
	
	
	
	
	public String getCf() {
		return cf;
	}




	public void setCf(String cf) {
		this.cf = cf;
	}




	public String getNominativo() {
		return nominativo;
	}




	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}




	public String getIndirizzo() {
		return indirizzo;
	}




	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}




	public String getTelefono() {
		return telefono;
	}




	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}




	public String getCap() {
		return cap;
	}




	public void setCap(String cap) {
		this.cap = cap;
	}




	public String getComune() {
		return comune;
	}




	public void setComune(String comune) {
		this.comune = comune;
	}




	public String getSiglaProvincia() {
		return siglaProvincia;
	}




	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}


	
	public void insert(Connection db)
	{
		
			
			String insertScipt = "insert into operatori_allevamenti (cf,nominativo,indirizzo,cap,comune,prov)"
					+" values (?,?,?,?,?,?)";
			
			try
			{
				
				
				PreparedStatement pst = db.prepareStatement(insertScipt);
				pst.setString(1, this.cf);
				pst.setString(2, nominativo);
				pst.setString(3, indirizzo);
				pst.setString(4, cap);
				pst.setString(5, comune);
				pst.setString(6, siglaProvincia);
				
				pst.execute();
				
			}
			catch(SQLException e)
			{
				
			
			
		}
	}
	
	public  InfoPersonaBean getListaAziende(String cf) {

		InfoPersonaBean infoAllReturn = new InfoPersonaBean();

		Logger logger = Logger.getLogger("MainLogger");

		// DEFINIZIONE CREDENZIALI DI ACCESSO AL WEBSERVICES
		String userName = ApplicationProperties.getProperty("USERNAME_WS_BDN");
		String passWord = ApplicationProperties.getProperty("PASSWORD_WS_BDN");



		// DEFINIZIONE DOCUMENTO RISPOSTA DA WEBSERVICES
		String documento_xml_costruito_allevamento    = null; 
		String get_capi_allevamento_response = null; 

		WsAziendeQrySoapStub stub_WsAziendeQry;
		WsAnagraficaCapoQrySoapStub stub_wsCapoQry;
		String ID_FISCALE = "ID_FISCALE" ;
		String COGN_NOME = "COGN_NOME" ;
		String INDIRIZZO = "INDIRIZZO" ;
		String CAP = "CAP" ;
		String LOCALITA= "LOCALITA" ;
		String COM_CODICE = "COM_CODICE" ;
		
		Connection db = null ;
		
		try {
			db = GestoreConnessioni.getConnection();
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
			documento_xml_costruito_allevamento = stub_WsAziendeQry.getPersona_STR(cf)		;	
			
			
			String ID_FISCALE_VAL =  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,ID_FISCALE  );
			String COGN_NOME_VAL =  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,COGN_NOME  );
			String INDIRIZZO_VAL =  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,INDIRIZZO  );
			String CAP_VAL =  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,CAP  );
			String LOCALITA_VAL =  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,LOCALITA  );
			String COM_CODICE_VAL =  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,COM_CODICE  );
		
			
		
			String selectComune = "select comuni1.nome ,lp.cod_provincia from comuni1 left join lookup_province lp on lp.code = comuni1.cod_provincia::int  where cap =? and cod_comune = ?";
			PreparedStatement pst = db.prepareStatement(selectComune);
			pst.setString(1, CAP_VAL);
			pst.setString(2, COM_CODICE_VAL);
//			
			String comune  = ""	; 
			String siglaProvincia = "" 	; 
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				comune = rs.getString(1)+"";
				siglaProvincia = rs.getString(2);
			}
			
			infoAllReturn.setNominativo(COGN_NOME_VAL);
			infoAllReturn.setCf(ID_FISCALE_VAL);
			infoAllReturn.setIndirizzo(INDIRIZZO_VAL);
			infoAllReturn.setComune(comune);
			infoAllReturn.setSiglaProvincia(siglaProvincia);
			infoAllReturn.setCap(CAP_VAL);
			
			
			
			// RECUPERO VALORI DALLA RISPOSTA di getAllevamento_STR
//			errorDesc			=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_descrizione_errore  );

			
//			val_denominazione_asl 	=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento, nodo_allevamento_denominazione_asl );

			System.out.println(documento_xml_costruito_allevamento);

			
			


			

			//if (valori_allevamento_errore!=null ) {				
			//if (!valori_allevamento_errore.equals("1") ) 			
			//infoAllReturn.setErrore(valori_allevamento_errore);
			//else
			//infoAllReturn.setErrore(null);
			//}

		} catch (ServiceException e) {
			
			//cpbReturn = null;
			//e.printStackTrace();
		}  catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LoginRequiredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}


		

		return infoAllReturn;
	}
	
	
	
	public  InfoPersonaBean getListaAziende(String cf,Connection db, int idUtente) {

		InfoPersonaBean infoAllReturn = new InfoPersonaBean();

		Logger logger = Logger.getLogger("MainLogger");

		// DEFINIZIONE CREDENZIALI DI ACCESSO AL WEBSERVICES
		String userName = ApplicationProperties.getProperty("USERNAME_WS_BDN");
		String passWord = ApplicationProperties.getProperty("PASSWORD_WS_BDN");



		// DEFINIZIONE DOCUMENTO RISPOSTA DA WEBSERVICES
		String get_capi_allevamento_response = null; 

		WsAziendeQrySoapStub stub_WsAziendeQry;
		WsAnagraficaCapoQrySoapStub stub_wsCapoQry;
		String ID_FISCALE = "ID_FISCALE" ;
		String COGN_NOME = "COGN_NOME" ;
		String INDIRIZZO = "INDIRIZZO" ;
		String CAP = "CAP" ;
		String LOCALITA= "LOCALITA" ;
		String COM_CODICE = "COM_CODICE" ;
		
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
			WsPost wsPost = new WsPost(db, WsPost.ENDPOINT_ALLEVAMENTI_PERSONA,WsPost.AZIONE_GET);
			
			HashMap<String, Object> campiInput = new HashMap<>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			campiInput.put("ser:p_persona_idfiscale",cf);
					
			wsPost.setCampiInput(campiInput);
			wsPost.costruisciEnvelope(db);
			String response = wsPost.post(db, idUtente);
			response=response.replaceAll("&lt;", "<");
			response=response.replaceAll("&gt;", ">");
			System.out.println("response: " + response);
			
			String idPersonaRestituito = utilsXML.getValoreNodoXML(response,"PERSONE_ID");
			
			
			String ID_FISCALE_VAL =  utilsXML.getValoreNodoXML(response,ID_FISCALE  );
			String COGN_NOME_VAL =  utilsXML.getValoreNodoXML(response,COGN_NOME  );
			String INDIRIZZO_VAL =  utilsXML.getValoreNodoXML(response,INDIRIZZO  );
			String CAP_VAL =  utilsXML.getValoreNodoXML(response,CAP  );
			String LOCALITA_VAL =  utilsXML.getValoreNodoXML(response,LOCALITA  );
			String COM_CODICE_VAL =  utilsXML.getValoreNodoXML(response,COM_CODICE  );
		
			
		
			String selectComune = "select comuni1.nome ,lp.cod_provincia from comuni1 left join lookup_province lp on lp.code = comuni1.cod_provincia::int  where cap =? and cod_comune = ?";
			PreparedStatement pst = db.prepareStatement(selectComune);
			pst.setString(1, CAP_VAL);
			pst.setString(2, COM_CODICE_VAL);
//			
			String comune  = ""	; 
			String siglaProvincia = "" 	; 
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				comune = rs.getString(1)+"";
				siglaProvincia = rs.getString(2);
			}
			
			infoAllReturn.setNominativo(COGN_NOME_VAL);
			infoAllReturn.setCf(ID_FISCALE_VAL);
			infoAllReturn.setIndirizzo(INDIRIZZO_VAL);
			infoAllReturn.setComune(comune);
			infoAllReturn.setSiglaProvincia(siglaProvincia);
			infoAllReturn.setCap(CAP_VAL);
			
			System.out.println(response);

		} catch (ServiceException e) {
			
			//cpbReturn = null;
			//e.printStackTrace();
		}  catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LoginRequiredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 


		

		return infoAllReturn;
	}

	
}
