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
import java.sql.Connection;
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

import javax.xml.xpath.XPathConstants;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Rollback extends GenericAction
{
	private static final Logger logger = LoggerFactory.getLogger( Rollback.class );

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

			List<Utente> utentiL1  =  UtenteGucDAO.listaUtentibyId(db, id);
			if( utentiL1.size() > 0 ){
				utentePreModifica =  utentiL1.get(0);
				System.out.println("Trovato utente preModifica");
			}


			List<Utente> utentiL = UtenteGucDAO.listaUtentibyId(db, id);//persistence.createCriteria(Utente.class).add(Restrictions.eq("id", id)).list();
			if( utentiL.size() > 0 ){
				u = utentiL.get(0);


				//Controllo univocita'' dell'username su guc_utenti
				boolean unique = false;


				if (unique==false){

					u.setModified(new Date());
					u.setModifiedBy(utente.getId());
					BUtente   BBButente        = (BUtente) req.getSession().getAttribute( "utente" );
					UtenteGucDAO.rollback(db, u);
					//LogUtenteDAO.loggaUtente(db, u, "Rollback Variazione Profilo",BBButente);

					/*INSERIMENTO IN GUC*/



					EndPointConnectorList listaEndPointConnector = (EndPointConnectorList) req.getSession().getAttribute("listaEndPointConnector");
					EndPointConnectorList listaEndPointConnectorOperazione = listaEndPointConnector.getByIdOperazione(Operazione.CHECKESISTENZAUTENTE); 
					EndPointConnector epcRollback = null;

					Connection conn = null;
		
					TreeMap<String,Ruolo> t1 = u.getHashRuoli();
					TreeMap<String,Ruolo> t2 = utentePreModifica.getHashRuoli();
					
					String urlReloadUtenti = "";

					  for(int i = 0; i <listaEndPointConnectorOperazione.size(); i++){
						  EndPointConnector epc = (EndPointConnector) listaEndPointConnectorOperazione.get(i);
							 if (epc.getEndPoint().getId()!=EndPoint.GUC) {
								 okEndpoint = true;
						urlReloadUtenti = "";
						try{
							
									 
							String QQQuery = epc.getSql();
							String dataSource = epc.getEndPoint().getDataSourceSlave();
							System.out.println("QQQQ ROLLBACK "+QQQuery);
									 
						
							conn = DbUtil.ottieniConnessioneJDBC(dataSource);
							
							
							esisteUtente = this.gestioneEsistenzaUtente(epc, u, conn);
							
							
															
								TreeMap treeMap_old = new TreeMap();
					        	TreeMap treeMap_new = new TreeMap();
					        	treeMap_old.putAll(utentePreModifica.getExtOption());
					        	treeMap_new.putAll(u.getExtOption());
					        	HashMap<String,String> extEndpoint_old = new HashMap<String,String>();
					        	HashMap<String,String> extEndpoint_new = new HashMap<String,String>();
					        	extEndpoint_old = (HashMap<String,String>)treeMap_old.get(epc.getEndPoint().getNome());
					        	extEndpoint_new = (HashMap<String,String>)treeMap_new.get(epc.getEndPoint().getNome());

					        	
								
									
//									Element operationModifyUtente = (Element)xpath.evaluate("//endpoint[@name='" + nomeEndpoint + "']/operation[@name='ModifyProfiloUtente']",doc, XPathConstants.NODE);
									//Element operationModifyUtente = (Element)xpath.evaluate("//endpoint[@name='" + nomeEndpoint + "']/operation[@name='RollBackProfiloUtente']",doc, XPathConstants.NODE);
					        	
					        		epcRollback = listaEndPointConnector.getByIdOperazioneIdEndPoint(Operazione.ROLLBACKPROFILOUTENTE, epc.getIdEndPoint()); 


									if	(esisteUtente){								
									
									  //  okEndpoint = this.gestioneAggiornamentoUtente(operationModifyUtente, operationType, u, conn);
										okEndpoint = rollback_profilo(u, epcRollback, conn);	
										urlReloadUtenti = epcRollback.getUrlReloadUtenti();
										query.put(epcRollback.getEndPoint().getNome().toUpperCase(), super.DBI); super.DBI="";

									} 
									
									System.out.println("Aggiornato ruolo su "+epcRollback.getEndPoint().getNome());
								
						
						}
						catch(Exception excEndpoint){
							okEndpoint = false;
							excEndpoint.printStackTrace();
						}
						finally{
							DbUtil.chiudiConnessioneJDBC(null, conn);
						}
						
						if(!okEndpoint){
							if (epc.getEndPoint().getId()!= EndPoint.GUC){
							logger.error("Errore durante la modifica dell'utente " + u.getUsername() + " nell'endpoint " + epc.getEndPoint().getNome());
							erroreEndpoint = erroreEndpoint + "Errore durante la modifica dell'utente in " + epc.getEndPoint().getNome() + "\n" ;
							}
						}else {
							if (urlReloadUtenti!=null && !urlReloadUtenti.equals("")){ 
								if (!utentePreModifica.getUsername().equals(u.getUsername())){ //NEL CASO CAMBIO L'USERNAME FACCIO LA RELOAD SIA SUL VECCHIO USERNAME CHE SUL NUOVO
									String urlReloadUtentiPre = null; 
									urlReloadUtentiPre = urlReloadUtenti+""+utentePreModifica.getUsername()+";-;"+u.getUsername();  //RELOAD PUNTUALE
									String resp = UrlUtil.getUrlResponse(urlReloadUtentiPre);
									logger.info("Reload Utenti (VECCHIA USERNAME) - Url: " + urlReloadUtentiPre + " - Esito: " + resp);
									urlReloadUtentiPre = "";
									if (resp==null || !resp.equalsIgnoreCase("OK")){
										erroreEndpoint = erroreEndpoint + "Errore durante il reload (VECCHIA USERNAME) dell'utente in " + epc.getEndPoint().getNome() + "\n";
									}
								}	
								urlReloadUtenti = urlReloadUtenti+""+u.getUsername();  //RELOAD PUNTUALE
								String resp = null ;
								try
								{
									resp = UrlUtil.getUrlResponse(urlReloadUtenti);
								}
								catch(Exception ee)
								{
									logger.error("Errore  In Reload Utenti : "+ee.getMessage());
								}
								
								/**
								 * LOG  DELLA CHIAMATA RELOAD UTENTE
								 */
								LogReloadUtente logReload = new LogReloadUtente();
								logReload.setUrl_invocata(urlReloadUtenti);
								logReload.setResponse(resp);
								logReload.setUsername(u.getUsername());
								logReload.setEndpoint(epc.getEndPoint().getNome());
								logReload.setId_utente(u.getId());
								logReload.setTipo_op("edit");
								logReload.insert(db);
								
								logger.info("Reload Utenti - Url: " + urlReloadUtenti + " - Esito: " + resp);
								urlReloadUtenti = "";
								if (resp==null || !resp.equalsIgnoreCase("OK")){
									//erroreEndpoint = erroreEndpoint + "Errore durante il reload dell'utente in " + epc.getEndPoint().getNome() + "\n";
								}
							} 
						}
					}
				}
					if(erroreEndpoint != null && !erroreEndpoint.equals("")){
						setErrore(erroreEndpoint);
					} else{
						u.setQuery(query);
						setMessaggio("Utente aggiornato con successo"); 
						LogUtenteDAO.loggaUtente(db, u, "Rollback Variazione Profilo",BBButente);
					}
					redirectTo("guc.Detail.us?id=" + u.getId());	

				}
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
	
	
	
	private Boolean rollback_profilo(Utente u,EndPointConnector epc, Connection conn) throws Exception{
		Boolean okEndpoint = false;
		okEndpoint = this.gestioneRollbackProfilo(epc, u, conn);
		
		/*
		switch (epc.getEndPoint().getId()) {
		//case "endpointDBVam" : 
		case EndPoint.VAM : 
				okEndpoint = this.gestioneRollbackProfilo(epc, u, conn);
			break;
			
		//case "endpointDBGisa" : 
		case EndPoint.GISA : 
							okEndpoint = this.gestioneRollbackProfilo(epc, u, conn);
			
			break;
		case EndPoint.GISA_EXT : 
			okEndpoint = this.gestioneRollbackProfilo(epc, u, conn);

			break;
		// BASTA IL PRECECENTE (dbGISAL)	
		//case "endpointDBGisa_ext" : 
		//	okEndpoint = this.gestioneRollbackProfilo(operationCambioProfilo, operationType, u, conn, nomeEndpoint);
			
		//	break;
			
		//case "endpointDBBdu" : 
		case EndPoint.BDU : 
				okEndpoint = this.gestioneRollbackProfilo(epc, u, conn);
			
			break;
			
		//case "endpointDBImportatore" : 
		case EndPoint.IMPORTATORI : 
				okEndpoint = this.gestioneRollbackProfilo(epc, u, conn);
			
			break;
			
		default :
			
			break;
		}
		*/
		return okEndpoint;
	}


	public String getListaModificheStrutt() {
		return listaModificheStrutt;
	}

	public void setListaModificheStrutt(String listaModificheStrutt) {
		this.listaModificheStrutt = listaModificheStrutt;
	}
}
