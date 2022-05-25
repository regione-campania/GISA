/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.guc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;

import it.us.web.action.GenericAction;
import it.us.web.bean.guc.Utente;
import it.us.web.dao.guc.UtenteGucDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.guc.DbUtil;
import it.us.web.util.guc.GUCOperationType;
import it.us.web.util.guc.UrlUtil;

import javax.xml.xpath.XPathConstants;

import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class AllineamentoUtenti extends GenericAction
{
	private static final Logger logger = LoggerFactory.getLogger( EditAnagrafica.class );

	@Override
	public void can() throws AuthorizationException
	{
		isLogged();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception{
		
		Connection conn = null;
		
/*		try{
			String endpoint = req.getParameter("endpoint");
			
			GUCOperationType operationType = null;
			
			//Disabilitazione di tutti gli utenti dell'endpoint selezionato
			Element operationDisabilitazioneGlobaleUtenti = (Element)xpath.evaluate("//endpoint[@name='" + endpoint + "']/operation[@name='DisableAllUtenti']",doc, XPathConstants.NODE);
			operationType = GUCOperationType.valueOf(operationDisabilitazioneGlobaleUtenti.getAttribute("type"));
			boolean okDisableAllUtenti = this.gestioneDisabilitazioneGlobaleUtenti(operationDisabilitazioneGlobaleUtenti, operationType);
			
			if(!okDisableAllUtenti){
				throw new Exception("Errore durante la disabilitazione degli utenti dell'endpoint " + endpoint);
			}
			
			//Ricavo la lista di tutti gli utenti presenti in guc per quell'endpoint
			ArrayList<Integer> listaIdUtenti = UtenteGucDAO.getIdUtentibyEndpoint(db, endpoint);
			
			//Per ogni utente verifico l'esistenza sull'endpoint, 
			//in caso positivo aggiorno le sue informazioni, 
			//in caso negativo lo inserisco.
			
			Utente u = null;
			boolean esisteUtente = false;
			boolean okUtente = false;
			String erroreUtente = "";
			Element operationCheckEsistenzaUtente = (Element)xpath.evaluate("//endpoint[@name='" + endpoint + "']/operation[@name='CheckEsistenzaUtente']",doc, XPathConstants.NODE);
			Element operationModifyUtente = (Element)xpath.evaluate("//endpoint[@name='" + endpoint + "']/operation[@name='ModifyUtente']",doc, XPathConstants.NODE);
			Element operationInsertUtente = (Element)xpath.evaluate("//endpoint[@name='" + endpoint + "']/operation[@name='InsertUtente']",doc, XPathConstants.NODE);
			
			String dbHost = operationModifyUtente.getElementsByTagName("db_host").item(0) != null ? 
							operationModifyUtente.getElementsByTagName("db_host").item(0).getTextContent() : null;
			String dbName = operationModifyUtente.getElementsByTagName("db_name").item(0) != null ? 
							operationModifyUtente.getElementsByTagName("db_name").item(0).getTextContent() : null;
			String dbUser = operationModifyUtente.getElementsByTagName("db_user").item(0) != null ? 
							operationModifyUtente.getElementsByTagName("db_user").item(0).getTextContent() : null;
			String dbPwd  = operationModifyUtente.getElementsByTagName("db_pwd").item(0) != null ? 
							operationModifyUtente.getElementsByTagName("db_pwd").item(0).getTextContent() : null;
			
			if(dbHost != null && dbName != null && dbUser != null && dbPwd != null ){
				conn = DbUtil.ottieniConnessioneJDBC(dbUser, dbPwd, dbHost, dbName);
			}
			
			int i = 0;
			for(int id : listaIdUtenti){
				i++;
				try{
					u = UtenteGucDAO.listaUtentibyId(db, id).get(0);
					
					operationType = GUCOperationType.valueOf(operationCheckEsistenzaUtente.getAttribute("type"));
					esisteUtente = this.gestioneEsistenzaUtente(operationCheckEsistenzaUtente, operationType, u, conn);
					
					u.setRuoloId(u.getHashRuoli().get(endpoint).getRuoloInteger());
					
					if(esisteUtente){
						operationType = GUCOperationType.valueOf(operationModifyUtente.getAttribute("type"));
						okUtente = this.gestioneAggiornamentoUtente(operationModifyUtente, operationType, u, conn, false);
					}
					else if(u.isEnabled()){
						operationType = GUCOperationType.valueOf(operationInsertUtente.getAttribute("type"));
						okUtente = this.gestioneInserimentoUtentePostUpdate(operationInsertUtente, operationType, u, conn, false);
					}
				}
				catch(Exception excEndpoint){
					okUtente = false;
					excEndpoint.printStackTrace();
				}
				
				if(!okUtente){
					logger.error("Errore durante la modifica dell'utente " + u.getUsername() + " nell'endpoint " + endpoint);
					erroreUtente = erroreUtente + "Errore durante la modifica dell'utente " + u.getUsername() + " nell'endpoint " + endpoint + "\n" ;
				}
				
				
			}
			
			//Reload degli utenti
			String urlReloadUtenti = operationModifyUtente.getElementsByTagName("url_reload_utenti").item(0) != null ?
									 operationModifyUtente.getElementsByTagName("url_reload_utenti").item(0).getTextContent() : null;
			if(urlReloadUtenti != null && !urlReloadUtenti.equals("")){
				try{
					String resp = UrlUtil.getUrlResponse(urlReloadUtenti);
					logger.info("Reload Utenti - Url: " + urlReloadUtenti + " - Esito: " + resp);
				}
				catch(Exception e){
					erroreUtente = "Errore durante il Reload Utenti per l'endpoint " + endpoint + "\n\n" + erroreUtente;
					logger.error("Errore durante il Reload Utenti per l'endpoint " + endpoint);
					e.printStackTrace();
				}
			}
			
			if(erroreUtente != null && !erroreUtente.equals("")){
				setErrore(erroreUtente);
			}
			else{
				setMessaggio("Allineamento con l'endpoint " + endpoint + " effettuato con successo");
			}
		
		}
		catch(Exception excEndpoint){
			setErrore(excEndpoint.getMessage());
			excEndpoint.printStackTrace();
		}
		finally{
			DbUtil.chiudiConnessioneJDBC(null, conn);
		} */
		
/*		boolean okDisableAllUtenti = false;
		boolean okUtente = false;
		Boolean flag = true;
		Savepoint save = null;
		
			String endpoint = req.getParameter("endpoint");
			GUCOperationType operationType = null;
			
			Utente u = null;
			boolean esisteUtente = false;
			Element operationCheckEsistenzaUtente = (Element)xpath.evaluate("//endpoint[@name='" + endpoint + "']/operation[@name='CheckEsistenzaUtente']",doc, XPathConstants.NODE);
			Element operationModifyUtente = (Element)xpath.evaluate("//endpoint[@name='" + endpoint + "']/operation[@name='ModifyUtente']",doc, XPathConstants.NODE);
			Element operationInsertUtente = (Element)xpath.evaluate("//endpoint[@name='" + endpoint + "']/operation[@name='InsertUtente']",doc, XPathConstants.NODE);
			Element operationDisabilitazioneGlobaleUtenti = (Element)xpath.evaluate("//endpoint[@name='" + endpoint + "']/operation[@name='DisableAllUtenti']",doc, XPathConstants.NODE);
			
			String dbHost = operationModifyUtente.getElementsByTagName("db_host").item(0) != null ? 
							operationModifyUtente.getElementsByTagName("db_host").item(0).getTextContent() : null;
			String dbName = operationModifyUtente.getElementsByTagName("db_name").item(0) != null ? 
							operationModifyUtente.getElementsByTagName("db_name").item(0).getTextContent() : null;
			String dbUser = operationModifyUtente.getElementsByTagName("db_user").item(0) != null ? 
							operationModifyUtente.getElementsByTagName("db_user").item(0).getTextContent() : null;
			String dbPwd  = operationModifyUtente.getElementsByTagName("db_pwd").item(0) != null ? 
							operationModifyUtente.getElementsByTagName("db_pwd").item(0).getTextContent() : null;
			
			if(dbHost != null && dbName != null && dbUser != null && dbPwd != null ){
				conn = DbUtil.ottieniConnessioneJDBC(dbUser, dbPwd, dbHost, dbName);
			}
			
			if (conn!=null){			
				conn.setAutoCommit(false);
				save = conn.setSavepoint("preDisabled"); 
			}
			
			//Disabilitazione di tutti gli utenti dell'endpoint selezionato
			operationType = GUCOperationType.valueOf(operationDisabilitazioneGlobaleUtenti.getAttribute("type"));
			okDisableAllUtenti = this.gestioneDisabilitazioneGlobaleUtenti(operationDisabilitazioneGlobaleUtenti, operationType,conn);
			
			if(okDisableAllUtenti){
				//Ricavo la lista di tutti gli utenti presenti in guc per quell'endpoint
				ArrayList<Integer> listaIdUtenti = UtenteGucDAO.getIdUtentibyEndpoint(db, endpoint);
				
				//Per ogni utente verifico l'esistenza sull'endpoint, 
				//in caso positivo aggiorno le sue informazioni, 
				//in caso negativo lo inserisco.

				int i = 0;
				for(int id : listaIdUtenti){
					i++;
					u = UtenteGucDAO.listaUtentibyId(db, id).get(0);
						
					operationType = GUCOperationType.valueOf(operationCheckEsistenzaUtente.getAttribute("type"));
					esisteUtente = this.gestioneEsistenzaUtente(operationCheckEsistenzaUtente, operationType, u, conn);
						
					u.setRuoloId(u.getHashRuoli().get(endpoint).getRuoloInteger());
						
					if(esisteUtente){
							operationType = GUCOperationType.valueOf(operationModifyUtente.getAttribute("type"));
							okUtente = this.gestioneAggiornamentoUtente(operationModifyUtente, operationType, u, conn, false);
							if (!okUtente){
								flag=false;
								break;
							}
					}
					else if(u.isEnabled()){
							operationType = GUCOperationType.valueOf(operationInsertUtente.getAttribute("type"));
							okUtente = this.gestioneInserimentoUtentePostUpdate(operationInsertUtente, operationType, u, conn, false);
							if (!okUtente){
								flag=false;
								break;
							}
					}
				}
			}

			if (flag==true && okDisableAllUtenti==true){
				if (conn!=null){
					conn.commit();
				}
				//Reload degli utenti
				String erroreUtente = "";
				operationModifyUtente = (Element)xpath.evaluate("//endpoint[@name='" + endpoint + "']/operation[@name='ModifyUtente']",doc, XPathConstants.NODE);
				String urlReloadUtenti = operationModifyUtente.getElementsByTagName("url_reload_utenti").item(0) != null ?
										 operationModifyUtente.getElementsByTagName("url_reload_utenti").item(0).getTextContent() : null;
				if(urlReloadUtenti != null && !urlReloadUtenti.equals("")){
					try{
						String resp = UrlUtil.getUrlResponse(urlReloadUtenti);
						logger.info("Reload Utenti - Url: " + urlReloadUtenti + " - Esito: " + resp);
					}
					catch(Exception e){
						erroreUtente = "Errore durante il Reload Utenti per l'endpoint " + endpoint + "\n\n" + erroreUtente;
						logger.error("Errore durante il Reload Utenti per l'endpoint " + endpoint);
						e.printStackTrace();
					}
				}
				
				if(erroreUtente != null && !erroreUtente.equals("")){
					setErrore(erroreUtente);
				}
				else{
					setMessaggio("Allineamento con l'endpoint " + endpoint + " effettuato con successo");
				}
			}
			else {
				setMessaggio("Allineamento non e'' avvenuto. I dati dell'endpoint sono rimasti inalterati.");
				conn.rollback(save);
			}
			if (conn!=null){
				conn.setAutoCommit(true);
				DbUtil.chiudiConnessioneJDBC(null, conn);
			}
		
		goToAction(new ToAllineamentoUtenti()); */
		
	}
	
	

}
