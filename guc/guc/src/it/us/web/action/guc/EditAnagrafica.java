/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.guc;

import it.us.web.action.GenericAction;
import it.us.web.action.Home;
import it.us.web.action.endpointconnector.ConfigAction;
import it.us.web.bean.BUtente;
import it.us.web.bean.endpointconnector.EndPoint;
import it.us.web.bean.endpointconnector.EndPointConnector;
import it.us.web.bean.endpointconnector.EndPointConnectorList;
import it.us.web.bean.endpointconnector.Operazione;
import it.us.web.bean.guc.Canile;
import it.us.web.bean.guc.Clinica;
import it.us.web.bean.guc.Importatori;
import it.us.web.bean.guc.LogReloadUtente;
import it.us.web.bean.guc.Ruolo;
import it.us.web.bean.guc.Struttura;
import it.us.web.bean.guc.Utente;
import it.us.web.constants.ExtendedOptions;
import it.us.web.dao.AslDAO;
import it.us.web.dao.guc.LogUtenteDAO;
import it.us.web.dao.guc.RuoloDAO;
import it.us.web.dao.guc.UtenteGucDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.guc.DbUtil;
import it.us.web.util.guc.GUCEndpoint;
import it.us.web.util.guc.GUCOperationType;
import it.us.web.util.guc.PasswordHash;
import it.us.web.util.guc.UrlUtil;
import it.us.web.util.guc.Utility;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.security.Key;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.xpath.XPathConstants;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import sun.misc.BASE64Encoder;

public class EditAnagrafica extends GenericAction
{
	private static final Logger logger = LoggerFactory.getLogger( EditAnagrafica.class );
	static byte[] keyValue = new byte[] { 'U', 'S', '9', '5', '6', '0', '0', '3', '1', '.', 'd','o', 'd', 'i', 'c', 'i' };

	private String listaModificheStrutt = "";

	@Override
	public void can() throws AuthorizationException
	{
		isLogged();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception{

		HashMap<String, String> query=new HashMap<String, String>();
		Utente u = null;
		Utente utentePreModifica = null;
		boolean okEndpoint;
		boolean esisteUtente;
		String erroreEndpoint = "";

		
		
		try{

			int id = interoFromRequest("id");
			String origPassword =  stringaFromRequest("password1");

			List<Utente> utentiL1  =  UtenteGucDAO.listaUtentibyId(db, id);
			if( utentiL1.size() > 0 ){
				utentePreModifica =  utentiL1.get(0);
				System.out.println("Trovato utente preModifica");

				u = utentiL1.get(0);

				BeanUtils.populate(u, req.getParameterMap());
				if(u.isNewPassword()){
					u.setPassword(PasswordHash.encrypt( stringaFromRequest("password1") ));
					u.setPasswordEncrypted( Utility.getInstance().encrypt( stringaFromRequest("password1") ) );
				}
					u.setModified(new Date());
					u.setModifiedBy(utente.getId());
					
					
					BUtente   BBButente        = (BUtente) req.getSession().getAttribute( "utente" );
					UtenteGucDAO.updateAnagraficaUtente(db, u);
					//LogUtenteDAO.loggaUtente(db, u, "Modifica",BBButente);

					//Modifica utente nei vari endpoint
					
					EndPointConnectorList listaEndPointConnector = (EndPointConnectorList) req.getSession().getAttribute("listaEndPointConnector");
					EndPointConnectorList listaEndPointConnectorOperazione = listaEndPointConnector.getByIdOperazione(Operazione.CHECKESISTENZAUTENTE); 
					
					EndPointConnector epcModificaUtente = null;

					
					Connection conn = null;
					String urlReloadUtenti = "";
					
					  for(int i = 0; i <listaEndPointConnectorOperazione.size(); i++){
						  EndPointConnector epc = (EndPointConnector) listaEndPointConnectorOperazione.get(i);
						 if (epc.getEndPoint().getId()!=EndPoint.GUC) {
						
						okEndpoint = true;
						urlReloadUtenti = "";
					
						try {
						
						String QQQuery = epc.getSql();
						
						System.out.println("QQQ EDITANAGRAFICA "+QQQuery);
						String dataSource = epc.getEndPoint().getDataSourceSlave();		
						

						conn = DbUtil.ottieniConnessioneJDBC(dataSource);
						

						esisteUtente = this.gestioneEsistenzaUtente(epc, u,	conn);
						
						epcModificaUtente = listaEndPointConnector.getByIdOperazioneIdEndPoint(Operazione.MODIFYANAGRAFICAUTENTE, epc.getEndPoint().getId()); 

						if (esisteUtente) {
							
							okEndpoint = update_anagrafica_Utente(u, epcModificaUtente, conn);
							urlReloadUtenti =epcModificaUtente.getUrlReloadUtenti();
							query.put(epcModificaUtente.getEndPoint().getNome().toUpperCase(), super.DBI); super.DBI="";
						}

						System.out.println("Aggiornato Anagrafica utente su " + (epcModificaUtente.getEndPoint().getNome()));

					} catch (Exception excEndpoint) {
						okEndpoint = false;
						excEndpoint.printStackTrace();
					} finally {
						DbUtil.chiudiConnessioneJDBC(null, conn);
					}

						if(!okEndpoint){
							if (epc.getEndPoint().getId()!= EndPoint.GUC){
							logger.error("Errore durante la modifica dell'utente " + u.getUsername() + " nell'endpoint " + epcModificaUtente.getEndPoint().getNome());
							erroreEndpoint = erroreEndpoint + "Errore durante la modifica dell'utente in " + epcModificaUtente.getEndPoint().getNome() + "\n" ;
							}
						}
//							else {
//							if (urlReloadUtenti!=null && !urlReloadUtenti.equals("")){ 
//								if (!utentePreModifica.getUsername().equals(u.getUsername())){ //NEL CASO CAMBIO L'USERNAME FACCIO LA RELOAD SIA SUL VECCHIO USERNAME CHE SUL NUOVO
//									String urlReloadUtentiPre = null; 
//									urlReloadUtentiPre = urlReloadUtenti+""+utentePreModifica.getUsername()+";-;"+u.getUsername();  //RELOAD PUNTUALE
//									String resp = UrlUtil.getUrlResponse(urlReloadUtentiPre);
//									logger.info("Reload Utenti (VECCHIA USERNAME) - Url: " + urlReloadUtentiPre + " - Esito: " + resp);
//									urlReloadUtentiPre = "";
//									if (resp==null || !resp.equalsIgnoreCase("OK")){
//										erroreEndpoint = erroreEndpoint + "Errore durante il reload (VECCHIA USERNAME) dell'utente in " + epcModificaUtente.getEndPoint().getNome() + "\n";
//									}
//								}	
//								urlReloadUtenti = urlReloadUtenti+""+u.getUsername();  //RELOAD PUNTUALE
//								System.out.println("**** URL RELOAD UTENTI **** : "+urlReloadUtenti);
//								String resp =null ;
//								try
//								{
//								 resp = UrlUtil.getUrlResponse(urlReloadUtenti);
//								}catch(UnknownHostException ec)
//								{
//									logger.info("Unknown host "+urlReloadUtenti);
//								}
//								/**
//								 * LOG  DELLA CHIAMATA RELOAD UTENTE
//								 */
//								LogReloadUtente logReload = new LogReloadUtente();
//								logReload.setUrl_invocata(urlReloadUtenti);
//								logReload.setResponse(resp);
//								logReload.setUsername(u.getUsername());
//								logReload.setEndpoint(epcModificaUtente.getEndPoint().getNome());
//								logReload.setId_utente(u.getId());
//								logReload.setTipo_op("edit");
//								logReload.insert(db);
//
//								logger.info("Reload Utenti - Url: " + urlReloadUtenti + " - Esito: " + resp);
//								urlReloadUtenti = "";
//								if (resp==null || !resp.equalsIgnoreCase("OK")){
//									erroreEndpoint = erroreEndpoint + "Errore durante il reload dell'utente in " + epcModificaUtente.getEndPoint().getNome() + "\n";
//								}
//							} 
//						}
					} }

					if(erroreEndpoint != null && !erroreEndpoint.equals("")){
						setErrore(erroreEndpoint);
					} else{
						u.setQuery(query);
						LogUtenteDAO.loggaUtente(db, u, "Modifica",BBButente,"");
						setMessaggio("Utente aggiornato con successo"); 
						salvaStorico(db, u, origPassword, (String)req.getRemoteAddr());
					}
					redirectTo("guc.Detail.us?id=" + u.getId());	
				

			}
			else{
				setErrore("Utente con id " + id + " non trovato");
				goToAction(new Home());
			}
		}
		catch(Exception e){
			e.printStackTrace();
			setErrore("Errore durante la modifica dell'utente");
			req.setAttribute("UserRecord", u);
			goToAction(new ToEditAnagrafica());
		}

	}

	


	private Boolean update_anagrafica_Utente(Utente u, EndPointConnector epc, Connection conn) throws Exception{
		Boolean okEndpoint = false;
		okEndpoint = this.gestioneAggiornamentoAnagraficaUtente(epc, u, conn);
		return okEndpoint;
	}



//	private Boolean insert_update_anagrafica_Utente(Utente u, String dbHost, Element operationInsert, Element operationModify, GUCOperationType operationType, Connection conn, String command, Element operationDisableUtente, Element operationCheckEsistenzaUtenteByStruttura, Element operationEnableUtenteByStruttura, String nomeEndpoint) throws Exception{
//		Boolean okEndpoint = false;
//		switch (dbHost) {
//		//case "endpointDBVam" : 
//		case "dbVAML" : 
//		{
//				if (u.isEnabled()){
//				if (u.getClinicheVam().size()>0){
//					if (!command.equals("insert")){
//						okEndpoint = this.gestioneAggiornamentoAnagraficaUtente(operationModify, operationType, u, conn, nomeEndpoint);
//						this.gestioneDisabilitazioneUtente(operationDisableUtente, operationType, u, conn);
//					}
//					for (int j=0; j<u.getClinicheVam().size(); j++){
//						u.setClinicaId(u.getClinicheVam().get(j).getIdClinica());
//						okEndpoint = this.gestioneEsistenzaUtenteByStruttura(operationCheckEsistenzaUtenteByStruttura, operationType, u, conn, u.getClinicheVam().get(j).getIdClinica());
//						if (okEndpoint)
//							okEndpoint = this.gestioneAbilitazioneUtenteByStruttura(operationEnableUtenteByStruttura, operationType, u, conn, u.getClinicheVam().get(j).getIdClinica());
//						else 
//							okEndpoint = this.gestioneInserimentoUtente(operationInsert, operationType, u, conn,nomeEndpoint);	
//					}
//				} else {
//					u.setClinicaId(-1);
//					if (command.equals("insert"))
//						okEndpoint = this.gestioneInserimentoUtente(operationInsert, operationType, u, conn, nomeEndpoint);
//					else
//						okEndpoint =  this.gestioneAggiornamentoAnagraficaUtente(operationModify, operationType, u, conn,nomeEndpoint);	
//				} 
//			}else if (!command.equals("insert"))
//				okEndpoint = this.gestioneAggiornamentoAnagraficaUtente(operationModify, operationType, u, conn, nomeEndpoint);	
//			break;
//		}
//		//case "endpointDBGisa" : 
//		case "dbGISAL" : {
//			if(nomeEndpoint.equalsIgnoreCase(""+GUCEndpoint.Gisa)){
//				if (u.isEnabled()){
//				if (u.getStruttureGisa().size()>0){
//					if (!command.equals("insert")){
//						okEndpoint = this.gestioneAggiornamentoAnagraficaUtente(operationModify, operationType, u, conn,nomeEndpoint);
//						this.gestioneDisabilitazioneUtente(operationDisableUtente, operationType, u, conn);
//					}
//					for (int j=0; j<u.getStruttureGisa().size(); j++){
//						u.setStrutturagisaId(u.getStruttureGisa().get(j).getIdStruttura());
//						okEndpoint = this.gestioneEsistenzaUtenteByStruttura(operationCheckEsistenzaUtenteByStruttura, operationType, u, conn, u.getStruttureGisa().get(j).getIdStruttura());
//						if (okEndpoint)
//							okEndpoint = this.gestioneAbilitazioneUtenteByStruttura(operationEnableUtenteByStruttura, operationType, u, conn, u.getStruttureGisa().get(j).getIdStruttura());
//						else
//							okEndpoint = this.gestioneInserimentoUtente(operationInsert, operationType, u, conn,nomeEndpoint);		
//					}
//				} else {
//					u.setStrutturagisaId(-1);
//					if (command.equals("insert"))
//						okEndpoint = this.gestioneInserimentoUtente(operationInsert, operationType, u, conn,nomeEndpoint);
//					else
//						okEndpoint = this.gestioneAggiornamentoAnagraficaUtente(operationModify, operationType, u, conn,nomeEndpoint);	
//				}
//			}else if (!command.equals("insert"))
//				okEndpoint = this.gestioneAggiornamentoAnagraficaUtente(operationModify, operationType, u, conn,nomeEndpoint);	
//			
//			}
//			
//			if(nomeEndpoint.equalsIgnoreCase(""+GUCEndpoint.Gisa_ext))
//			{
//				
//				if (command.equals("insert"))
//					okEndpoint = this.gestioneInserimentoUtente(operationInsert, operationType, u, conn, nomeEndpoint);
//				else
//					okEndpoint = this.gestioneAggiornamentoAnagraficaUtente(operationModify, operationType, u, conn, nomeEndpoint);	
//				
//			}
//			
//			break;}
//		//case "endpointDBBdu" : 
//		case "dbBDUL" : 
//		{
//				if (u.isEnabled()){
//				if (u.getCaniliBdu().size()>0){
//					if (!command.equals("insert")){
//						okEndpoint = this.gestioneAggiornamentoAnagraficaUtente(operationModify, operationType, u, conn,nomeEndpoint);
//						this.gestioneDisabilitazioneUtente(operationDisableUtente, operationType, u, conn);
//					}
//					for (int j=0; j<u.getCaniliBdu().size(); j++){
//						u.setCanilebduId(u.getCaniliBdu().get(j).getIdCanile());
//						okEndpoint = this.gestioneEsistenzaUtenteByStruttura(operationCheckEsistenzaUtenteByStruttura, operationType, u, conn, u.getCaniliBdu().get(j).getIdCanile());
//						if (okEndpoint)
//							okEndpoint = this.gestioneAbilitazioneUtenteByStruttura(operationEnableUtenteByStruttura, operationType, u, conn, u.getCaniliBdu().get(j).getIdCanile());
//						else
//							okEndpoint = this.gestioneInserimentoUtente(operationInsert, operationType, u, conn, nomeEndpoint);		
//					}
//				} else {
//					u.setCanilebduId(-1);
//					if (command.equals("insert"))
//						okEndpoint = this.gestioneInserimentoUtente(operationInsert, operationType, u, conn, nomeEndpoint);
//					else
//						okEndpoint = this.gestioneAggiornamentoAnagraficaUtente(operationModify, operationType, u, conn, nomeEndpoint);	
//				}
//			}else if (!command.equals("insert"))
//				okEndpoint = this.gestioneAggiornamentoAnagraficaUtente(operationModify, operationType, u, conn, nomeEndpoint);	
//			break;
//		}
//		//case "endpointDBImportatore" : 
//		case "dbIMPORTATORIL" : 
//		{
//				if (u.isEnabled()){
//				if (u.getImportatori().size()>0){
//					if (!command.equals("insert")){
//						okEndpoint = this.gestioneAggiornamentoAnagraficaUtente(operationModify, operationType, u, conn, nomeEndpoint);
//						this.gestioneDisabilitazioneUtente(operationDisableUtente, operationType, u, conn);
//					}
//					for (int j=0; j<u.getImportatori().size(); j++){
//						u.setId_importatore(u.getImportatori().get(j).getIdImportatore());
//						okEndpoint = this.gestioneEsistenzaUtenteByStruttura(operationCheckEsistenzaUtenteByStruttura, operationType, u, conn, u.getImportatori().get(j).getIdImportatore());
//						if (okEndpoint)
//							okEndpoint = this.gestioneAbilitazioneUtenteByStruttura(operationEnableUtenteByStruttura, operationType, u, conn, u.getImportatori().get(j).getIdImportatore());
//						else
//							okEndpoint = this.gestioneInserimentoUtente(operationInsert, operationType, u, conn, nomeEndpoint);		
//					}
//				} else {
//					u.setId_importatore(-1);
//					if (command.equals("insert"))
//						okEndpoint = this.gestioneInserimentoUtente(operationInsert, operationType, u, conn, nomeEndpoint);
//					else
//						okEndpoint = this.gestioneAggiornamentoAnagraficaUtente(operationModify, operationType, u, conn, nomeEndpoint);	
//				}
//			}else if (!command.equals("insert"))
//				okEndpoint = this.gestioneAggiornamentoAnagraficaUtente(operationModify, operationType, u, conn, nomeEndpoint);
//			break;
//		}
//		default :
//		{
//			if (command.equals("insert"))
//				okEndpoint = this.gestioneInserimentoUtente(operationInsert, operationType, u, conn, nomeEndpoint);
//			else
//				okEndpoint = this.gestioneAggiornamentoAnagraficaUtente(operationModify, operationType, u, conn, nomeEndpoint);	
//			break;
//		}
//		}
//		return okEndpoint;
//	}








	

	public String getListaModificheStrutt() {
		return listaModificheStrutt;
	}

	public void setListaModificheStrutt(String listaModificheStrutt) {
		this.listaModificheStrutt = listaModificheStrutt;
	}
	
	private void salvaStorico(Connection db, Utente u, String origPassword, String ip) {
		String pwdNewEncrypted;
		try {
			pwdNewEncrypted = encryptPassword(origPassword);
			PreparedStatement pst = db.prepareStatement("insert into storico_cambio_password(id_utente, ip_modifica,  data_modifica, nuova_password, nuova_password_decript) values (?, ?, now(), ?,  ?);");
			int i = 0;
			pst.setInt(++i, u.getId());
			pst.setString(++i, ip);
			pst.setString(++i, u.getPassword());
			pst.setString(++i, pwdNewEncrypted);
			pst.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String encryptPassword(String password) throws Exception{
		// byte[] keyValue = new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't', 'S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' };
		String encrypteToken = encrypt(password, keyValue);
		return encrypteToken;
	 }
	
	public static String encrypt(String plainText, byte[] encryptionKey) throws Exception {
		  Key key = generateKey(encryptionKey);
	      Cipher c = Cipher.getInstance("AES");
	      c.init(Cipher.ENCRYPT_MODE, key);
	      byte[] encVal = c.doFinal(plainText.getBytes());
	      String encryptedValue = new BASE64Encoder().encode(encVal);
	      return encryptedValue;
	  }
	private static Key generateKey(byte[] keyValue) throws Exception {
	    Key key = new SecretKeySpec(keyValue, "AES");
	    return key;
	}
}
