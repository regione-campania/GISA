/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;






import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpSession;
import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import it.us.web.bean.guc.Ruolo;
import it.us.web.bean.guc.Utente;
import it.us.web.dao.UtenteDAO;
import it.us.web.dao.guc.RuoloDAO;
import it.us.web.db.ApplicationProperties;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.guc.DbUtil;
import it.us.web.util.guc.GUCEndpoint;
import it.us.web.util.guc.GUCOperationType;

public class EndpointSca extends GenericAction
{
	
	
	public EndpointSca() {		
	}
	
	@Override
	public void can() throws AuthorizationException
	{

	}

	@Override
	public void execute() throws Exception
	{
		
		 String username = req.getParameter("username");
		 
		 System.out.println("### USERNAME = "+username);
		 
		 if(req.getParameter("username") != null && !req.getParameter("username").equals("")) {
			 //((HashMap<String, HttpSession>) session.getServletContext().getAttribute("utenti")).put(username, session);
			
			 utenteGuc = UtenteDAO.authenticatebyUsername(username, db);	
		 }
		
		if( utenteGuc == null)
		{
			setErrore("L'utente non risulta registrato all'ecosistema GISA");
		}
			
		else {
			session.setAttribute( "utenteGuc", utenteGuc );
			
			 ResultSet rss = RuoloDAO.getEndpointByUsername(username, db,Boolean.parseBoolean(ApplicationProperties.getProperty("login_spid")));
			 if(Boolean.parseBoolean(ApplicationProperties.getProperty("login_spid")))
			 {
				 rss.next();
				 rss = RuoloDAO.getEndpointByCf(rss.getString("codice_fiscale"), db);
			 }
			 
			 ArrayList<String> ruoli = new ArrayList<String>();
			 ArrayList<String> endpoints = new ArrayList<String>();
			 ArrayList<String> usernames = new ArrayList<String>();
			 ArrayList<String> asl = new ArrayList<String>();
			 ArrayList<String> num_registrazione_stab = new ArrayList<String>();
			 ArrayList<String> lista_partita_iva = new ArrayList<String>();
			 ArrayList<String> canilebdu = new ArrayList<String>();
				//RuoloDAO.getRolesByIdUtenteN(utenteGuc.getId(), db);
				//ArrayList<String> endpoints = RuoloDAO.getRolesByIdUtenteN(utenteGuc.getId(), db);
				//ArrayList<String> username = RuoloDAO.getUsernameByEndpoint(utenteGuc.getId(), db);
				
				//Query in caso di esistenza di raggruppamento
			 if (rss.isClosed())
				 System.out.println("ENDSCA.............");

			 while(rss.next())
				{	
					endpoints.add(rss.getString("endpoint") );
					ruoli.add(rss.getString("ruolo_string") );
					usernames.add(rss.getString("username") );
					asl.add(rss.getString("asl"));
					try {
						num_registrazione_stab.add(rss.getString("num_registrazione_stab"));
						lista_partita_iva.add(rss.getString("piva"));
					}
					catch(Exception e) {}
					
					try {
						canilebdu.add(rss.getString("canilebdu_description"));
						}
						catch(Exception e) {}
				}

				req.setAttribute("endpoints", endpoints);
				req.setAttribute("roles", ruoli);
				req.setAttribute("username", usernames);
				req.setAttribute("asl", asl);
				req.setAttribute("num_registrazione_stab", num_registrazione_stab);
				req.setAttribute("lista_partita_iva", lista_partita_iva);
				req.setAttribute("canilebdu", canilebdu);
				
				//Verifica obsolescenza accessi
				NodeList endpointList = (NodeList)xpath.evaluate("//endpoint", doc, XPathConstants.NODESET);
				Element endpointElem = null;
				Element operationCheckEsistenzaUtente = null;
				GUCEndpoint nomeEndpoint = null;
				Connection conn = null;
				String dbHost = null;
				String dbName = null;
				String dbUser = null;
				String dbPwd  = null;
				ArrayList<Integer> esiti = new ArrayList<Integer>();
				String esito="";
				try{
					int i=0;
					while(i<endpoints.size()){
						operationCheckEsistenzaUtente = (Element)xpath.evaluate("//endpoint[@name='" + endpoints.get(i) + "']/operation[@name='CheckEnableUtente']",doc, XPathConstants.NODE);						
						GUCOperationType operationType = GUCOperationType.valueOf(operationCheckEsistenzaUtente.getAttribute("type"));
						dbHost = operationCheckEsistenzaUtente.getElementsByTagName("db_host").item(0) != null ? 
										 operationCheckEsistenzaUtente.getElementsByTagName("db_host").item(0).getTextContent() : null;
								dbName = operationCheckEsistenzaUtente.getElementsByTagName("db_name").item(0) != null ? 
										 operationCheckEsistenzaUtente.getElementsByTagName("db_name").item(0).getTextContent() : null;
								dbUser = operationCheckEsistenzaUtente.getElementsByTagName("db_user").item(0) != null ? 
										 operationCheckEsistenzaUtente.getElementsByTagName("db_user").item(0).getTextContent() : null;
								dbPwd  = operationCheckEsistenzaUtente.getElementsByTagName("db_pwd").item(0) != null ? 
										 operationCheckEsistenzaUtente.getElementsByTagName("db_pwd").item(0).getTextContent() : null;
								
								String	 dbDatasource  = operationCheckEsistenzaUtente.getElementsByTagName("db_datasource").item(0) != null ? 
												 operationCheckEsistenzaUtente.getElementsByTagName("db_datasource").item(0).getTextContent() : null;
								if(dbHost != null && dbName != null && dbUser != null && dbPwd != null ){
									//conn = DbUtil.ottieniConnessioneJDBC(dbUser, dbPwd, dbHost, dbName);
									conn = DbUtil.ottieniConnessioneJDBC(dbDatasource);
									}
							
						String timeout = ApplicationProperties.getProperty("timeout");
						//esito =	this.gestioneLastLoginUtente(operationCheckEsistenzaUtente, operationType, utenteGuc.getUsername(), conn, timeout);
						esito =	this.gestioneLastLoginUtente(operationCheckEsistenzaUtente, operationType, usernames.get(i), conn, timeout);
						if(esito.equals("KO")){
							esiti.add(i);
							
						}else{
							esiti.add(-1);
						}
							
						++i;
						DbUtil.chiudiConnessioneJDBC(null, conn);
					}
					
					if(endpoints.size() <=0){
						if(!Boolean.parseBoolean(ApplicationProperties.getProperty("login_spid")))
							setErrore("ATTENZIONE! L'utente "+utenteGuc.getUsername()+" non ha alcuna applicazione associata per effettuare l'accesso. Si prega di contattare il servizio di HD I livello.");
						else
							setErrore("ATTENZIONE! L'utente con codice fiscale "+utenteGuc.getCodiceFiscale()+" non ha alcuna applicazione associata per effettuare l'accesso. Si prega di contattare il servizio di HD I livello.");
					}
					
					req.setAttribute("errore2","ATTENZIONE! NON PUOI ACCEDERE AL SISTEMA IN QUANTO IL TUO ACCOUNT RISULTA DISATTIVATO PER "
							+ " APPLICAZIONE RUOLO E ASL SELEZIONATI. PER ESSERE RIATTIVATO, SI PREGA DI CONTATTARE IL SERVIZIO DI HD I LIVELLO.");
					
					req.setAttribute("esiti", esiti);
					
							
				}catch(Exception e){
					e.printStackTrace();
				}
				finally {
					DbUtil.chiudiConnessioneJDBC(null, conn);
				}
				//rss.close();
				db.close();
				 if(Boolean.parseBoolean(ApplicationProperties.getProperty("login_spid")))
					 gotoPage("/jsp/sca/sceltaSpidVersion.jsp" ); 
				 else
					 gotoPage("/jsp/sca/scelta.jsp" ); 
		}
		 
		 
		
	}
			
	


public String gestioneLastLoginUtente ( Element operationCheckEsistenzaUtente , GUCOperationType operationType, String username, Connection conn, String timeout){
		
		String ris = "";
		int time = Integer.parseInt(timeout);
		  switch (operationType) {
			case Sql:
				PreparedStatement pst = null;
				ResultSet rs = null;
				try{

					String query = operationCheckEsistenzaUtente.getElementsByTagName("query").item(0).getTextContent();
					
					pst = conn.prepareStatement(query);
					int i = 0;
					
					pst.setString(++i,username);
					//Timeout
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.MONTH, -time);
					pst.setTimestamp(2, new Timestamp(cal.getTimeInMillis()));		
					//System.out.println("timestamp: "+ new Timestamp(cal.getTimeInMillis()) );
					rs = pst.executeQuery();
					
					while (rs.next()){
						ris = rs.getString(1);
					}
					
					pst.close();
					rs.close();
				} catch (Exception e){
					e.printStackTrace();
				}
				
				break;
			case Json:
				ris = "OK";
				break;
				
			default:
				try {
					throw new Exception("[gestioneEsistenzaUtente] - Il tipo di operazione " + operationType + " non e gestito.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		  return ris;
		  
	  }
	

	
}
	
