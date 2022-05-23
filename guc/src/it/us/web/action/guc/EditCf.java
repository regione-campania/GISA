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

public class EditCf extends GenericAction
{
	private static final Logger logger = LoggerFactory.getLogger( EditAnagrafica.class );

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

				u = utentiL1.get(0);

				if(UtenteGucDAO.listaUtentibyCf(db,stringaFromRequest("codiceFiscale") ,u.getNome(),u.getCognome()).size() > 0)
				{
					setErrore("Codice fiscale gia'' usato per altri utenti con nome e cognome diversi.");
					req.setAttribute("UserRecord", u);
					goToAction(new ToEditCf());
				}
				else
				{
					BeanUtils.populate(u, req.getParameterMap());
					u.setCodiceFiscale(stringaFromRequest("codiceFiscale") );
					u.setModified(new Date());
					u.setModifiedBy(utente.getId());
					
				}
					
					
					
					BUtente   BBButente        = (BUtente) req.getSession().getAttribute( "utente" );

					//Modifica utente nei vari endpoint
					
					EndPointConnectorList listaEndPointConnector = (EndPointConnectorList) req.getSession().getAttribute("listaEndPointConnector");
					EndPointConnectorList listaEndPointConnectorOperazione = listaEndPointConnector.getByIdOperazione(Operazione.CHECKESISTENZAUTENTE); 
					
					EndPointConnector epcModificaUtente = null;

					
					Connection conn = null;
					String urlReloadUtenti = "";
					
					  for(int i = 0; i <listaEndPointConnectorOperazione.size(); i++){
						  EndPointConnector epc = (EndPointConnector) listaEndPointConnectorOperazione.get(i);
					
						  okEndpoint = true;
						urlReloadUtenti = "";
					
						try {
						
						String QQQuery = epc.getSql();
						
						System.out.println("QQQ ESISTENZA UTENTE "+QQQuery);
						String dataSource = epc.getEndPoint().getDataSourceSlave();		
						

						conn = DbUtil.ottieniConnessioneJDBC(dataSource);
						

						esisteUtente = this.gestioneEsistenzaUtente(epc, u,	conn);
						
						epcModificaUtente = listaEndPointConnector.getByIdOperazioneIdEndPoint(Operazione.MODIFICACODICEFISCALE, epc.getEndPoint().getId()); 

						if (esisteUtente) {
							
							okEndpoint = update_cf_Utente(u, epcModificaUtente, conn);
							urlReloadUtenti =epcModificaUtente.getUrlReloadUtenti();
							query.put(epcModificaUtente.getEndPoint().getNome().toUpperCase(), super.DBI); super.DBI="";
						}

						System.out.println("Aggiornato cf utente su " + (epcModificaUtente.getEndPoint().getNome()));

					} catch (Exception excEndpoint) {
						okEndpoint = false;
						excEndpoint.printStackTrace();
					} finally {
						DbUtil.chiudiConnessioneJDBC(null, conn);
					}

						if(!okEndpoint){
							if (epc.getEndPoint().getId()!= EndPoint.GUC){
							logger.error("Errore durante la modifica cf dell'utente " + u.getUsername() + " nell'endpoint " + epcModificaUtente.getEndPoint().getNome());
							erroreEndpoint = erroreEndpoint + "Errore durante la modifica dell'utente in " + epcModificaUtente.getEndPoint().getNome() + "\n" ;
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
										erroreEndpoint = erroreEndpoint + "Errore durante il reload (VECCHIA USERNAME) dell'utente in " + epcModificaUtente.getEndPoint().getNome() + "\n";
									}
								}	
								urlReloadUtenti = urlReloadUtenti+""+u.getUsername();  //RELOAD PUNTUALE
								System.out.println("**** URL RELOAD UTENTI **** : "+urlReloadUtenti);
								String resp =null ;
								try
								{
								 resp = UrlUtil.getUrlResponse(urlReloadUtenti);
								}catch(Exception ec)
								{
									logger.info("Unknown host "+urlReloadUtenti);
								}
								/**
								 * LOG  DELLA CHIAMATA RELOAD UTENTE
								 */
								LogReloadUtente logReload = new LogReloadUtente();
								logReload.setUrl_invocata(urlReloadUtenti);
								logReload.setResponse(resp);
								logReload.setUsername(u.getUsername());
								logReload.setEndpoint(epcModificaUtente.getEndPoint().getNome());
								logReload.setId_utente(u.getId());
								logReload.setTipo_op("edit_cf");
								logReload.insert(db);

								logger.info("Reload Utenti - Url: " + urlReloadUtenti + " - Esito: " + resp);
								urlReloadUtenti = "";
								if (resp==null || !resp.equalsIgnoreCase("OK")){
								//	erroreEndpoint = erroreEndpoint + "Errore durante il reload dell'utente in " + epcModificaUtente.getEndPoint().getNome() + "\n";
								}
							} 
						}
					}

					if(erroreEndpoint != null && !erroreEndpoint.equals("")){
						setErrore(erroreEndpoint);
					} else{
						u.setQuery(query);
						LogUtenteDAO.loggaUtente(db, u, "Modifica",BBButente,"");
						setMessaggio("Utente aggiornato con successo"); 
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
			goToAction(new ToEditCf());
		}

	}

	


	private Boolean update_cf_Utente(Utente u, EndPointConnector epc, Connection conn) throws Exception{
		Boolean okEndpoint = false;
		okEndpoint = this.gestioneAggiornamentoCfUtente(epc, u, conn);
		return okEndpoint;
	}



}
