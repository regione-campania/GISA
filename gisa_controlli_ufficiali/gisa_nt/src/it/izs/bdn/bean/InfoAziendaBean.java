/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.izs.bdn.bean;

import it.izs.bdn.action.utilsXML;
import it.izs.bdn.webservices.GetPersonaResponseGetPersonaResult;
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

public class InfoAziendaBean {
	
	
	
	String cod_azienda ;
	String indirizzo_Azienda ;
	String cap_azienda;
	String localita;
	String cod_comune_azienda;
	String prov_sede_azienda;
	Double latitudine;
	Double longidutine;
	Timestamp dat_apertura_azienda;
	String comuneAziendaCalcolato ;
	String siglaProvinciaAziendaCalcolato ;
	String nomeComuneAzienda;
	String errorId ;
	
	
	
	
	public String getErrorId() {
		return errorId;
	}




	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}




	public String getComuneAziendaCalcolato() {
		return comuneAziendaCalcolato;
	}




	public void setComuneAziendaCalcolato(String comuneAziendaCalcolato) {
		this.comuneAziendaCalcolato = comuneAziendaCalcolato;
	}




	public String getSiglaProvinciaAziendaCalcolato() {
		return siglaProvinciaAziendaCalcolato;
	}




	public void setSiglaProvinciaAziendaCalcolato(String siglaProvinciaAziendaCalcolato) {
		this.siglaProvinciaAziendaCalcolato = siglaProvinciaAziendaCalcolato;
	}




	public String getCod_azienda() {
		return cod_azienda;
	}




	public void setCod_azienda(String cod_azienda) {
		this.cod_azienda = cod_azienda;
	}




	public String getIndirizzo_Azienda() {
		return indirizzo_Azienda;
	}




	public void setIndirizzo_Azienda(String indirizzo_Azienda) {
		this.indirizzo_Azienda = indirizzo_Azienda;
	}




	public String getCap_azienda() {
		return cap_azienda;
	}




	public void setCap_azienda(String cap_azienda) {
		this.cap_azienda = cap_azienda;
	}




	public String getLocalita() {
		return localita;
	}




	public void setLocalita(String localita) {
		this.localita = localita;
	}




	public String getCod_comune_azienda() {
		return cod_comune_azienda;
	}




	public void setCod_comune_azienda(String cod_comune_azienda) {
		this.cod_comune_azienda = cod_comune_azienda;
	}




	public String getProv_sede_azienda() {
		return prov_sede_azienda;
	}




	public void setProv_sede_azienda(String prov_sede_azienda) {
		this.prov_sede_azienda = prov_sede_azienda;
	}




	public Double getLatitudine() {
		return latitudine;
	}




	public void setLatitudine(Double latitudine) {
		this.latitudine = latitudine;
	}




	public Double getLongidutine() {
		return longidutine;
	}




	public void setLongidutine(Double longidutine) {
		this.longidutine = longidutine;
	}




	public Timestamp getDat_apertura_azienda() {
		return dat_apertura_azienda;
	}




	public void setDat_apertura_azienda(Timestamp dat_apertura_azienda) {
		this.dat_apertura_azienda = dat_apertura_azienda;
	}



	public  void insert(Connection db)
	{
		
		String insertScipt = "insert into aziende (cod_azienda,indrizzo_azienda,cap_azienda,localita,cod_comune_azienda,prov_sede_azienda,latitudine,longitudine,dat_apertura_azienda)"
				+" values (?,?,?,?,?,?,?,?,?)";
		
		try
		{
			if(this.errorId==null || (errorId!=null && "".equals(errorId) || "null".equalsIgnoreCase(errorId)))
			{
			
			PreparedStatement pst = db.prepareStatement(insertScipt);
			pst.setString(1, this.cod_azienda);
			pst.setString(2, indirizzo_Azienda);
			pst.setString(3, cap_azienda);
			pst.setString(4, indirizzo_Azienda);
			pst.setString(5, comuneAziendaCalcolato);
			pst.setString(6, prov_sede_azienda);
			if(latitudine!=null)
				pst.setDouble(7, latitudine);
			else
				pst.setDouble(7, 0);
			
			if(longidutine!=null)
				pst.setDouble(8, longidutine);
			else
				pst.setDouble(8, 0);
			pst.setTimestamp(9, dat_apertura_azienda);
			pst.execute();
			}
		}
		catch(SQLException e)
		{
			
		}
		
	}

	public  InfoAziendaBean getInfoAllevamentoBean(String codice_azienda) {

		InfoAziendaBean infoAllReturn = new InfoAziendaBean();

		Logger logger = Logger.getLogger("MainLogger");

		// DEFINIZIONE CREDENZIALI DI ACCESSO AL WEBSERVICES
		String userName = ApplicationProperties.getProperty("USERNAME_WS_BDN");
		String passWord = ApplicationProperties.getProperty("PASSWORD_WS_BDN");



		// DEFINIZIONE DOCUMENTO RISPOSTA DA WEBSERVICES
		String documento_xml_costruito_allevamento    = null; 
		String get_capi_allevamento_response = null; 

		WsAziendeQrySoapStub stub_WsAziendeQry;
		WsAnagraficaCapoQrySoapStub stub_wsCapoQry;
		String nodo_id_azienda = "AZIENDA_ID" ;
		String nodo_id_codice_azienda = "CODICE" ;
		String nodo_latitudine = "LATITUDINE" ;
		String nodo_id_longitudine = "LONGITUDINE" ;
		String nodo_cap= "CAP" ;
		String nodo_indirizzo = "INDIRIZZO" ;
		String nodo_comune_codice_tre_cifre = "COM_CODICE" ;
		String nodo_Asl_codice = "ASL_CODICE";
		String nodo_data_apertura = "DT_APERTURA" ;
		String nodo_istat_asl = "ASL_CODICE" ;
		String nodo_errore_id ="id";
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
			
			documento_xml_costruito_allevamento = stub_WsAziendeQry.getAzienda_STR(codice_azienda)		;	
			
			String cap =  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_cap  );
			String dataApertura =  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_data_apertura  );
			String codiceComune =  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_comune_codice_tre_cifre  );
			String indirizzo =  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_indirizzo  );
			String latitudine =  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_latitudine  );
			String longitudine =  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_id_longitudine  );
			String istatAsl =  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_istat_asl  );
			String errorId			=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_errore_id  );

			
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
				Timestamp dtApertura = new Timestamp(sdf.parse(dataApertura).getTime());
		
			String insertScipt = "insert int oziende (cod_azienda,indirizzo_azienda,cap_azienda,localita,cod_comune_azienda,prov_sede_azienda,latituine,longitudine,data_apertura_azienda)"
								+" values (?,?,?,?,?,?,?,?,?)";
				
			String selectComune = "select istat::int ,lp.cod_provincia, nome  from comuni1 left join lookup_province lp on lp.code = comuni1.cod_provincia::int  where reverse_cap ilike '%" +  cap +  "%' and cod_comune = ?";
			PreparedStatement pst = db.prepareStatement(selectComune);
			pst.setString(1, codiceComune);
			
			String comune  = ""	; 
			String siglaProvincia = "" 	; 
			String nomeComune = "";
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				comune = rs.getInt(1)+"";
				siglaProvincia = rs.getString(2);
				nomeComune = rs.getString(3);
			}
			
			infoAllReturn.setErrorId(errorId);
			infoAllReturn.setCap_azienda(cap);
			infoAllReturn.setCod_azienda(codice_azienda);
			infoAllReturn.setCod_comune_azienda(codiceComune);
			infoAllReturn.setDat_apertura_azienda(dtApertura);
			infoAllReturn.setIndirizzo_Azienda(indirizzo);
			if (latitudine!=null)
				infoAllReturn.setLatitudine(Double.parseDouble(latitudine));
			if (longitudine!=null)
				infoAllReturn.setLongidutine(Double.parseDouble(longitudine));
			infoAllReturn.setProv_sede_azienda(siglaProvincia);
			infoAllReturn.setComuneAziendaCalcolato(comune);
			infoAllReturn.setNomeComuneAzienda(nomeComune);

			
			
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
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LoginRequiredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}


		

		return infoAllReturn;
	}
	
	
	
	public  InfoAziendaBean getInfoAllevamentoBean(String codice_azienda,Connection db, int idUtente) {

		InfoAziendaBean infoAllReturn = new InfoAziendaBean();

		Logger logger = Logger.getLogger("MainLogger");

		// DEFINIZIONE CREDENZIALI DI ACCESSO AL WEBSERVICES
		String userName = ApplicationProperties.getProperty("USERNAME_WS_BDN");
		String passWord = ApplicationProperties.getProperty("PASSWORD_WS_BDN");



		// DEFINIZIONE DOCUMENTO RISPOSTA DA WEBSERVICES
		String get_capi_allevamento_response = null; 

		WsAziendeQrySoapStub stub_WsAziendeQry;
		WsAnagraficaCapoQrySoapStub stub_wsCapoQry;
		String nodo_id_azienda = "AZIENDA_ID" ;
		String nodo_id_codice_azienda = "CODICE" ;
		String nodo_latitudine = "LATITUDINE" ;
		String nodo_id_longitudine = "LONGITUDINE" ;
		String nodo_cap= "CAP" ;
		String nodo_indirizzo = "INDIRIZZO" ;
		String nodo_comune_codice_tre_cifre = "COM_CODICE";
		String nodo_provincia_codice = "PRO_CODICE" ;
		String nodo_Asl_codice = "ASL_CODICE";
		String nodo_data_apertura = "DT_APERTURA" ;
		String nodo_istat_asl = "ASL_CODICE" ;
		String nodo_errore_id ="id";
		 
		    
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
			
			WsPost wsPost = new WsPost(db, WsPost.ENDPOINT_ALLEVAMENTI_AZIENDA,WsPost.AZIONE_GET);
			
			HashMap<String, Object> campiInput = new HashMap<>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			campiInput.put("ser:p_azienda_codice",codice_azienda);
					
			wsPost.setCampiInput(campiInput);
			wsPost.costruisciEnvelope(db);
			String response = wsPost.post(db, idUtente);
			response=response.replaceAll("&lt;", "<");
			response=response.replaceAll("&gt;", ">");
			System.out.println("response: " + response);
			
			String idAllevamentoRestituito = utilsXML.getValoreNodoXML(response,"AZIENDA_ID");
			
			
			
			String cap =  utilsXML.getValoreNodoXML(response,nodo_cap  );
			String dataApertura =  utilsXML.getValoreNodoXML(response,nodo_data_apertura  );
			String codiceComune =  utilsXML.getValoreNodoXML(response,nodo_comune_codice_tre_cifre  );
			String codiceProvincia =  utilsXML.getValoreNodoXML(response,nodo_provincia_codice  );
			String indirizzo =  utilsXML.getValoreNodoXML(response,nodo_indirizzo  );
			String latitudine =  utilsXML.getValoreNodoXML(response,nodo_latitudine  );
			String longitudine =  utilsXML.getValoreNodoXML(response,nodo_id_longitudine  );
			String istatAsl =  utilsXML.getValoreNodoXML(response,nodo_istat_asl  );
			String errorId			=  utilsXML.getValoreNodoXML(response,nodo_errore_id  );

			Timestamp dtApertura = new Timestamp(sdf.parse(dataApertura).getTime());
		
			String insertScipt = "insert int oziende (cod_azienda,indirizzo_azienda,cap_azienda,localita,cod_comune_azienda,prov_sede_azienda,latituine,longitudine,data_apertura_azienda)"
								+" values (?,?,?,?,?,?,?,?,?)";
				
			String selectComune = "select istat::int ,lp.cod_provincia, nome  from comuni1 left join lookup_province lp on lp.code = comuni1.cod_provincia::int  where (reverse_cap ilike '%" +  cap +  "%' or lp.cod_provincia ilike ?) and cod_comune = ?";
			PreparedStatement pst = db.prepareStatement(selectComune);
			pst.setString(1, codiceProvincia);
			pst.setString(2, codiceComune);
			
			String comune  = ""	; 
			String siglaProvincia = "" 	; 
			String nomeComune = "";
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				comune = rs.getInt(1)+"";
				siglaProvincia = rs.getString(2);
				nomeComune = rs.getString(3);
			}
			
			infoAllReturn.setErrorId(errorId);
			infoAllReturn.setCap_azienda(cap);
			infoAllReturn.setCod_azienda(codice_azienda);
			infoAllReturn.setCod_comune_azienda(codiceComune);
			infoAllReturn.setDat_apertura_azienda(dtApertura);
			infoAllReturn.setIndirizzo_Azienda(indirizzo);
			if (latitudine!=null)
				infoAllReturn.setLatitudine(Double.parseDouble(latitudine));
			if (longitudine!=null)
				infoAllReturn.setLongidutine(Double.parseDouble(longitudine));
			infoAllReturn.setProv_sede_azienda(siglaProvincia);
			infoAllReturn.setComuneAziendaCalcolato(comune);
			infoAllReturn.setNomeComuneAzienda(nomeComune);

			System.out.println(response);

			

		} catch (ServiceException e) {
			
		} catch (SOAPException e) {
			e.printStackTrace();
		} catch (LoginRequiredException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		

		return infoAllReturn;
	}
	
	
	
	public  InfoAziendaBean getListaAziende(String codice_azienda) {

		InfoAziendaBean infoAllReturn = new InfoAziendaBean();

		Logger logger = Logger.getLogger("MainLogger");

		// DEFINIZIONE CREDENZIALI DI ACCESSO AL WEBSERVICES
		String userName = ApplicationProperties.getProperty("USERNAME_WS_BDN");
		String passWord = ApplicationProperties.getProperty("PASSWORD_WS_BDN");



		// DEFINIZIONE DOCUMENTO RISPOSTA DA WEBSERVICES
		String documento_xml_costruito_allevamento    = null; 
		String get_capi_allevamento_response = null; 

		WsAziendeQrySoapStub stub_WsAziendeQry;
		WsAnagraficaCapoQrySoapStub stub_wsCapoQry;
		String nodo_id_azienda = "AZIENDA_ID" ;
		String nodo_id_codice_azienda = "CODICE" ;
		String nodo_latitudine = "LATITUDINE" ;
		String nodo_id_longitudine = "LONGITUDINE" ;
		String nodo_cap= "CAP" ;
		String nodo_indirizzo = "INDIRIZZO" ;
		String nodo_comune_codice_tre_cifre = "COM_CODICE" ;
		String nodo_Asl_codice = "ASL_CODICE";
		String nodo_data_apertura = "DT_APERTURA" ;
		String nodo_istat_asl = "ASL_CODICE" ;
		String nodo_errore_id ="id";
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
			
			
			
			String cap =  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_cap  );
			String dataApertura =  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_data_apertura  );
			String codiceComune =  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_comune_codice_tre_cifre  );
			String indirizzo =  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_indirizzo  );
			String latitudine =  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_latitudine  );
			String longitudine =  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_id_longitudine  );
			String istatAsl =  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_istat_asl  );
			String errorId			=  utilsXML.getValoreNodoXML(documento_xml_costruito_allevamento,nodo_errore_id  );

			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
				Timestamp dtApertura = new Timestamp(sdf.parse(dataApertura).getTime());
		
			String selectComune = "select istat::int ,lp.cod_provincia from comuni1 left join lookup_province lp on lp.code = comuni1.cod_provincia::int  where (codiceistatasl_old =?  or codiceistatasl =?) and cod_comune = ?";
			PreparedStatement pst = db.prepareStatement(selectComune);
			pst.setString(1, istatAsl.replace("R", ""));
			pst.setString(2, istatAsl.replace("R", ""));
			pst.setString(3, codiceComune);
			
			String comune  = ""	; 
			String siglaProvincia = "" 	; 
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				comune = rs.getInt(1)+"";
				siglaProvincia = rs.getString(2);
			}
			
			infoAllReturn.setErrorId(errorId);
			infoAllReturn.setCap_azienda(cap);
			infoAllReturn.setCod_azienda(codice_azienda);
			infoAllReturn.setCod_comune_azienda(codiceComune);
			infoAllReturn.setDat_apertura_azienda(dtApertura);
			infoAllReturn.setIndirizzo_Azienda(indirizzo);
			infoAllReturn.setLatitudine(Double.parseDouble(latitudine));
			infoAllReturn.setLongidutine(Double.parseDouble(longitudine));
			infoAllReturn.setProv_sede_azienda(siglaProvincia);
			infoAllReturn.setComuneAziendaCalcolato(comune);
			
			
			
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
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally
		{
			GestoreConnessioni.freeConnection(db);
		}


		

		return infoAllReturn;
	}




	public String getNomeComuneAzienda() {
		return nomeComuneAzienda;
	}




	public void setNomeComuneAzienda(String nomeComuneAzienda) {
		this.nomeComuneAzienda = nomeComuneAzienda;
	}

	public void sincronizzaBdn(Connection db,int orgId) throws ParseException 
	{
		try 
		{

			PreparedStatement pst = null;
			int i = 0;
			 
			//AGGIORNO AZIENDE
			String updateAziende = "update aziende set indrizzo_azienda= ?, cod_comune_azienda = ?, prov_sede_azienda = (select cod_provincia from lookup_province where codistat in (select cod_provincia from comuni1 where istat::integer = ?::integer)), cap_azienda = ?, longitudine = ?, latitudine = ? where cod_azienda = ? ";
			pst = db.prepareStatement(updateAziende) ;
			pst.setString(++i, indirizzo_Azienda!=null ? indirizzo_Azienda.trim() : "");
			pst.setString(++i, comuneAziendaCalcolato!=null ? comuneAziendaCalcolato.trim() : "");
			pst.setString(++i, comuneAziendaCalcolato!=null ? comuneAziendaCalcolato.trim() : "");
			pst.setString(++i, cap_azienda!=null ? cap_azienda.trim() : "");
			if(longidutine!=null && !latitudine.equals(""))
				pst.setDouble(++i, longidutine);
			else
				pst.setObject(++i, null);
			if(latitudine!=null && !latitudine.equals(""))
				pst.setDouble(++i, latitudine);
			else
				pst.setObject(++i, null);
			pst.setString(++i, cod_azienda.trim());
			pst.executeUpdate();

		}
		catch(SQLException e)
		{

		}
	}
	

}
