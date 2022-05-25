/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.login;

import it.us.web.action.GenericAction;
import it.us.web.bean.BUtente;
import it.us.web.bean.SuperUtente;
import it.us.web.dao.UtenteDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.properties.Application;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

public class Login extends GenericAction {

	@Override
	public void can() throws AuthorizationException {

	}

	@Override
	public void setSegnalibroDocumentazione() {
		setSegnalibroDocumentazione("login");
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception {
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("java:comp/env/jdbc/vamM");
		connection = ds.getConnection();
		GenericAction.aggiornaConnessioneApertaSessione(req);

		String system = (String) session.getAttribute("system");

		double lat = 0.0;
		double lon = 0.0;
		String errore_geo = "";

		if (utente != null) {
			Enumeration<String> e = session.getAttributeNames();
			while (e.hasMoreElements()) {
				session.removeAttribute(e.nextElement());
			}
			utente = null;
		}

		session.setAttribute("system", (system == null) ? ("vam") : (system));
		
		
		JSONObject jsonObj = Application.checkBrowser(req.getHeader("User-Agent"));
		String msg = "" ;
		if (jsonObj!=null)
		{
			if (jsonObj.getString("esito").equals("1"))
			{
				msg = jsonObj.getString("msg");
				setErrore(msg);
				
				gotoPage("homePageVAM", "/jsp/public/indexV.jsp");
			}
			
			if (jsonObj.getString("esito").equals("2"))
			{
				 msg = jsonObj.getString("msg");
				 setStringRequest("Messaggio",msg);
			}
			

			
		}

		// Si è scelto di scollegare l'utente già presente in context
		boolean scollegareUtenteContext = req.getParameter("scollegareUtenteContext") != null;
		SuperUtente su = null;
		if (scollegareUtenteContext && (BUtente) session.getAttribute("utenteVecchioContext") != null) {
			su = ((BUtente) session.getAttribute("utenteVecchioContext")).getSuperutente();
		} else {
			String un = req.getParameter("utente");
			String pw = req.getParameter("password");
			lat = doubleFromRequest("access_position_lat");
			lon = doubleFromRequest("access_position_lon");
			errore_geo = stringaFromRequest("access_position_err");
			su = UtenteDAO.superAuthenticate(un, pw, connection);
		}

		//CERCO DI ENTRARE
		
		//CHECK LOCK
			if (CheckLock.checkLocked(connection, req.getRemoteAddr(), stringaFromRequest("utente"))){
				setErrore("Accesso bloccato a causa dei troppi tentativi falliti. Attendere circa 3 minuti per riprovare.");
				String un = stringaFromRequest("utente");
				String ip = req.getRemoteAddr();
				GenericAction.writeLoginFault(un, ip, "Autenticazione fallita");
				gotoPage("homePageVAM", "/jsp/public/indexV.jsp");
				
			}
		//FINE CHECK LOCK
			else {
		if (!scollegareUtenteContext) {
			if (su == null || su.getUtenti() == null || su.getUtenti().size() == 0) { 

				//LOGIN ERRATA
				
				//CHECK LOCK
				CheckLock.incLock(connection, req.getRemoteAddr(), stringaFromRequest("utente"));
				//FINE CHECK LOCK
				
				setErrore("Autenticazione fallita");
				String un = stringaFromRequest("utente");
				String ip = req.getRemoteAddr();
				GenericAction.writeLoginFault(un, ip, "Autenticazione fallita");

				redirectTo("Index.us?entrypointSinantropi=urlDiretto");
			}

			else if (!esisteUtenteContext(su.getUsername())) {

				/**
				 * GESTIONE GEOLOCALIZZAZIONE
				 */
				su.setAccessPositionErr(errore_geo);

				if (lat > 0.0 && lon > 0.0) {
					su.setAccessPositionLat(lat);
					su.setAccessPositionLon(lon);
					if (errore_geo == null || ("").equals(errore_geo))
						su.setAccessPositionErr("Comunicazione RealTime");

				}

				// su.setAccessPositionErr(errore_geo);
				// CHECK LAST LOGIN
				// String nome_tabella = Application.get("NOME_TABELLA");
				// String nome_colonna = Application.get("NOME_COLONNA");
				// String timeout = Application.get("TIMEOUT");
				if (su.getLastLogin() != null && !su.getLastLogin().equals("")) {
					String timeout = Application.get("timeout");
					String s = new SimpleDateFormat("dd/MM/yyyy").format(su.getLastLogin());
					int time = Integer.parseInt(timeout);
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.MONTH, -time);
					Timestamp calTime = new Timestamp(cal.getTimeInMillis());
					/*
					 * String sql =
					 * "SELECT count(*) as tot, to_char("+nome_colonna
					 * +",'dd-mm-yyyy') as ultimo_accesso from " +nome_tabella
					 * +" a where a.username = '"+su.getUsername()+
					 * "' and a.trashed_date is null and enabled and "
					 * +nome_colonna+" >= (now() - "+timeout
					 * +") group by "+nome_colonna; boolean accesso = false;
					 * List<Object[]> result =
					 * persistence.createSQLQuery(sql).list(); for(Object []
					 * obj: result){ if(((BigInteger) obj[0]).intValue() > 0){
					 * accesso = true; } }
					 */
					// Se non può loggarsi
					if (su.getLastLogin().before(calTime)) {
						// if(!accesso){
						// String sql2 =
						// "SELECT to_char("+nome_colonna+",'dd-mm-yyyy') as ultimo_accesso from "
						// +nome_tabella
						// +" a where a.username = '"+su.getUsername()+"' and a.trashed_date is null and enabled";
						// List<Object[]> result2 =
						// persistence.createSQLQuery(sql2).list();
						setErrore("ATTENZIONE! NON PUOI ACCEDERE AL SISTEMA IN QUANTO IL TUO ACCOUNT RISULTA DISATTIVATO. "
								+ "IL TUO ULTIMO ACCESSO RISALE AL GIORNO "
								+ s
								+ ". PER ESSERE RIATTIVATO, SI PREGA DI CONTATTARE IL SERVIZIO DI HD I LIVELLO.");

						gotoPage("homePageVAM", "/jsp/public/indexV.jsp");
					} else {

						java.util.Date date = new java.util.Date();
						su.setLastLogin(new Timestamp(date.getTime()));
						getPersistence().update(su);

						if (su.getUtenti().size() == 1) {
							BUtente utente = su.getUtenti().get(0);

							/**
							 * GESTIONE GEOLOCALIZZAZIONE
							 */
							utente.setAccessPositionErr(errore_geo);
							if (lat > 0.0 && lon > 0.0) {
								utente.setAccessPositionLat(lat);
								utente.setAccessPositionLon(lon);
								if (errore_geo == null || ("").equals(errore_geo))
									utente.setAccessPositionErr("Comunicazione RealTime");
							}

							getPersistence().update(utente);
							inserisciUtenteContext(su, session, null);
							session.setAttribute("utente", utente);
							// settaSessionFunzioniConcesse(su.getUtenti().get(
							// 0 ) , session);
							redirectTo("Index.us?entrypointSinantropi=urlDiretto");
						} else {
							session.setAttribute("su", su);
							session.setAttribute("password", stringaFromRequest("password"));
							gotoPage("public", "/jsp/ballot-screen.jsp");
						}

					}

				} else {

					java.util.Date date = new java.util.Date();
					su.setLastLogin(new Timestamp(date.getTime()));
					getPersistence().update(su);
					
					//LOGIN CORRETTA
					
					//CHECK LOCK
					CheckLock.resetLock(connection, req.getRemoteAddr(), stringaFromRequest("utente"));
					//FINE CHECK LOCK

					if (su.getUtenti().size() == 1) {
						BUtente utente = su.getUtenti().get(0);

						/**
						 * GESTIONE GEOLOCALIZZAZIONE
						 */
						utente.setAccessPositionErr(errore_geo);
						if (lat > 0.0 && lon > 0.0) {
							utente.setAccessPositionLat(lat);
							utente.setAccessPositionLon(lon);
							if (errore_geo == null || ("").equals(errore_geo))
								utente.setAccessPositionErr("Comunicazione RealTime");
						}

						UtenteDAO.setAccessPosition(utente,connection );
						
						inserisciUtenteContext(su, session, null);
						session.setAttribute("utente", utente);
						// settaSessionFunzioniConcesse(su.getUtenti().get( 0 )
						// , session);
						redirectTo("Index.us?entrypointSinantropi=urlDiretto");
					} else {
						session.setAttribute("su", su);
						session.setAttribute("password", stringaFromRequest("password"));
						gotoPage("public", "/jsp/ballot-screen.jsp");
					}
				}
			} else {
				try {
					session.setAttribute("utenteVecchioContext", getSessioneUtenteContext(su.getUsername())
							.getAttribute("utente"));
				} catch (IllegalStateException e) {
					session.setAttribute("utenteVecchioContext", null);
				}
				session.setAttribute("su", su);
				req.setAttribute("utente", stringaFromRequest("utente"));
				req.setAttribute("password", stringaFromRequest("password"));
				if (su.getUtenti().size() == 1) {
					gotoPage("public", "/jsp/gestioneUtenteContext.jsp");
				} else if (su.getUtenti().size() > 1) {
					gotoPage("public", "/jsp/ballot-screen.jsp");
				}
			}
		} else {
			session.setAttribute("utenteVecchioContext", null);
			HttpSession sessioneVecchia = (HttpSession) getSessioneUtenteContext(stringaFromRequest("utente"));
			try {
				sessioneVecchia.setAttribute("utente", null);
				sessioneVecchia.setAttribute("funzioniConcesse", null);
				if (sessioneVecchia.getId() != session.getId())
					sessioneVecchia.invalidate();
			} catch (IllegalStateException e) {

			}
			eliminaUtenteContext(stringaFromRequest("utente"));
			// Devo ricostruire il bean SuperUtente perchè quello in session che
			// si chiama utenteVecchioContext ha la sessione scaduta
			SuperUtente superUtente = UtenteDAO.superAuthenticateNoPassword(stringaFromRequest("utente"), connection);

			//LOGIN CORRETTA
			
			//CHECK LOCK
			CheckLock.resetLock(connection, req.getRemoteAddr(), stringaFromRequest("utente"));
			//FINE CHECK LOCK
			
			if (superUtente.getUtenti().size() == 1) {
				
				inserisciUtenteContext(superUtente, session, null);
				session.setAttribute("utente", superUtente.getUtenti().get(0));
				// settaSessionFunzioniConcesse(superUtente.getUtenti().get( 0 )
				// , session);
				redirectTo("Index.us?entrypointSinantropi=urlDiretto");
			} else {
				if (!scollegareUtenteContext) {
					session.setAttribute("su", superUtente);
					gotoPage("public", "/jsp/ballot-screen.jsp");
				} else {
					session.setAttribute("su", superUtente);
					redirectTo("login.Ballot.us?id=" + req.getParameter("id"));
				}
			}
		}
	}
	}
}
