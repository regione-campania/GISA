/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.guc;

import it.us.web.action.GenericAction;
import it.us.web.action.Home;
import it.us.web.bean.BUtente;
import it.us.web.bean.endpointconnector.EndPoint;
import it.us.web.bean.endpointconnector.EndPointConnector;
import it.us.web.bean.endpointconnector.EndPointConnectorList;
import it.us.web.bean.endpointconnector.Operazione;
import it.us.web.bean.guc.Asl;
import it.us.web.bean.guc.LogReloadUtente;
import it.us.web.bean.guc.Utente;
import it.us.web.constants.Sql;
import it.us.web.dao.AslDAO;
import it.us.web.dao.UtenteDAO;
import it.us.web.dao.guc.LogUtenteDAO;
import it.us.web.dao.guc.UtenteGucDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.guc.DbUtil;
import it.us.web.util.guc.UrlUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

public class Allinea extends GenericAction
{
	
	Logger logger = Logger.getLogger(Allinea.class);

	@Override
	public void can() throws AuthorizationException 
	{
		try
		{
		isLogged();
		}
		catch(AuthorizationException e)
		{
			logger.info("Non Autenticato");
			throw e ;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception {
	    String ipBdu = "dbBDUL";
	    String ipVam = "dbVAML";
	    String ipGisa = "dbGISAL"; 
	    String ipGisaExt = "dbGISAL";
	    
		String queryGISA = "select g.id, g.username, g.password, gr.ruolo_string from guc_utenti g join guc_ruoli gr on gr.id_utente=g.id where g.enabled and gr.endpoint ilike '%Gisa' and gr.ruolo_integer > 0 and g.username not similar to '%cni_%' and lower(trim(username)) not in (select lower(trim(username)) from dblink('host="
				+ ipGisa
				+ " dbname=gisa user=postgres password=postgres', ' SELECT username FROM access ') AS table2 (username char(150))) order by g.modified desc";
		String queryGISA_EXT = "select g.id, g.username, g.password, gr.ruolo_string from guc_utenti g join guc_ruoli gr on gr.id_utente=g.id where g.enabled and gr.endpoint ilike '%Gisa_%' and gr.ruolo_integer > 0 and g.username not similar to '%cni_%'  and lower(trim(username)) not in (select lower(trim(username)) from dblink('host="
				+ ipGisaExt
				+ " dbname=gisa user=postgres password=postgres', ' SELECT username FROM access_ext ') AS table2 (username char(150))) order by g.modified desc";
		String queryBDU = "select g.id, g.username, g.password, gr.ruolo_string from guc_utenti g join guc_ruoli gr on gr.id_utente=g.id where g.enabled and gr.endpoint ilike '%bdu%' and gr.ruolo_integer > 0 and lower(trim(username)) not in (select lower(trim(username)) from dblink('host="
				+ ipBdu
				+ " dbname=bdu user=postgres password=postgres', ' SELECT username FROM access ') AS table2 (username char(150))) order by g.modified desc";
		String queryVAM = "select g.id, g.username, g.password, gr.ruolo_string from guc_utenti g join guc_ruoli gr on gr.id_utente=g.id where g.enabled and gr.endpoint ilike '%vam%' and gr.ruolo_integer > 0 and lower(trim(username)) not in (select lower(trim(username)) from dblink('host="
				+ ipVam
				+ " dbname=vam user=postgres password=postgres', ' SELECT username FROM utenti_super ') AS table2 (username char(150))) order by g.modified desc";
		String queryUser = "";
		String ruolo = "";
		int int_endpoint = 0;
		// CONFIGURAZIONE ENDPOINT: 1="bdu"; 2="Gisa"; 3="Gisa_ext"; 4="Vam";
		// 5="Importatori"; 6="Guc"; 7="Digemon"
		String allinea = stringaFromRequest("allinea");
		
		if (allinea.equals("allineaGISA")) {
			queryUser = queryGISA;
			ruolo = "Gisa";
			int_endpoint = 2;
		} else if (allinea.equals("allineaGISA_EXT")) {
			queryUser = queryGISA_EXT;
			ruolo = "Gisa_ext";
			int_endpoint = 3;
		} else if (allinea.equals("allineaBDU")) {
			queryUser = queryBDU;
			ruolo = "bdu";
			int_endpoint = 1;
		} else if (allinea.equals("allineaVAM")) {
			queryUser = queryVAM;
			ruolo = "Vam";
			int_endpoint = 4;
		}

		boolean okEndpoint = true;
		String erroreEndpoint = "";
		String erroreReload = "";
		String utentiAllineati = "";
		String urlReloadUtenti = "";

		String messaggio = "";
		int userDaAllineare = 0;
		int userAllineati = 0;

		try {
			PreparedStatement stat = db.prepareStatement(queryUser);
			ResultSet rs = stat.executeQuery();
			List id = new ArrayList();
			while (rs.next()) {
				// System.out.println("TROVATO : " + rs.getString("username"));
				id.add(rs.getInt("id"));
			}
			userDaAllineare = id.size();

			EndPointConnectorList listaEndPointConnector = new EndPointConnectorList();
			listaEndPointConnector.creaLista(db);

			EndPointConnector epcAccreditoSuap = null;

			Connection conn = null;

			// EndPointConnector epc = (EndPointConnector)
			// listaEndPointConnectorOperazione.get(i);
			EndPointConnector epc = new EndPointConnector();
			epc.getByEndPointOperazione(db, int_endpoint, Operazione.INSERTUTENTE);

			try {
				String dbDatasource = epc.getEndPoint().getDataSourceSlave();
				urlReloadUtenti = epc.getUrlReloadUtenti();
				if (epc.getEndPoint().getId() == EndPoint.GISA_EXT) {
					epcAccreditoSuap = listaEndPointConnector.getByIdOperazioneIdEndPoint(Operazione.ACCREDITASUAP, epc
							.getEndPoint().getId());
				}
				String QQQuery = epc.getSql();
				conn = DbUtil.ottieniConnessioneJDBC(epc.getEndPoint().getDataSourceSlave());

				for (int i = 0; i < userDaAllineare; i++) {
					Utente u = null;
					List<Utente> utentiList = UtenteGucDAO.listaUtentibyId(db, (int) id.get(i));

					if (utentiList.size() > 0) {
						u = utentiList.get(0);
						u.setRuoloId(u.getHashRuoli().get(ruolo).getRuoloInteger());
					}

					System.out.println("### CERCO DI ALLINEARE : " + u.getUsername());

					// GESTIONE UNICA per guc 2.1
					switch (epc.getEndPoint().getId()) {
					// case "endpointDBVam" :
					case EndPoint.VAM: {
						if (u.getClinicheVam().size() > 0) {
							for (int j = 0; j < u.getClinicheVam().size(); j++) {
								u.setClinicaId(u.getClinicheVam().get(j).getIdClinica());
								okEndpoint = this.gestioneInserimentoUtente(epc, u, conn);
							}
						} else {
							u.setClinicaId(-1);
							okEndpoint = this.gestioneInserimentoUtente(epc, u, conn);
						}
						break;
					}
					// case "endpointDBGisa" :
					case EndPoint.GISA: {
						if (u.getStruttureGisa().size() > 0) {
							for (int j = 0; j < u.getStruttureGisa().size(); j++) {
								u.setStrutturagisaId(u.getStruttureGisa().get(j).getIdStruttura());
								okEndpoint = this.gestioneInserimentoUtente(epc, u, conn);

							}
						} else {
							u.setStrutturagisaId(-1);
							okEndpoint = this.gestioneInserimentoUtente(epc, u, conn);
						}
						break;
					}
					// case "endpointDBGisa" :
					case EndPoint.GISA_EXT: {
						okEndpoint = this.gestioneInserimentoUtente(epc, u, conn);
						okEndpoint = this.gestioneAccreditamentoSuap(epcAccreditoSuap, u, conn);

						break;
					}
					// case "endpointDBBdu" :
					case EndPoint.BDU: {
						if (u.getCaniliBdu().size() > 0) {
							for (int j = 0; j < u.getCaniliBdu().size(); j++) {
								u.setCanilebduId(u.getCaniliBdu().get(j).getIdCanile());
								okEndpoint = this.gestioneInserimentoUtente(epc, u, conn);
							}
						} else {
							u.setCanilebduId(-1);
							okEndpoint = this.gestioneInserimentoUtente(epc, u, conn);
						}
						break;
					}
					// case "endpointDBImportatori" :
					case EndPoint.IMPORTATORI: {
						if (u.getImportatori().size() > 0) {
							for (int j = 0; j < u.getImportatori().size(); j++) {
								u.setId_importatore(u.getImportatori().get(j).getIdImportatore());
								okEndpoint = this.gestioneInserimentoUtente(epc, u, conn);
							}
						} else {
							u.setId_importatore(-1);
							okEndpoint = this.gestioneInserimentoUtente(epc, u, conn);
						}
						break;
					}
					case EndPoint.DIGEMON: {
						okEndpoint = this.gestioneInserimentoUtente(epc, u, conn);
						break;
					}
					default:

						break;
					}
					if (okEndpoint){
						userAllineati++;
						utentiAllineati+=" - "+u.getUsername()+"\n";
					}
					// super.DBI = "";

					if (!okEndpoint) {
						logger.error("Errore durante l'inserimento dell'utente " + u.getUsername() + " nell'endpoint "
								+ epc.getEndPoint().getNome());
						erroreEndpoint += " - "+u.getUsername() + "\n";
					} else {
						if (urlReloadUtenti != null && !urlReloadUtenti.equals("")) {
							urlReloadUtenti = urlReloadUtenti + "" + u.getUsername(); // RELOAD
																						// PUNTUALE
							String resp = null;
							try {
								resp = UrlUtil.getUrlResponse(urlReloadUtenti);
							} catch (Exception ee) {
								logger.warn("Attenzione hostname per ReloadUtenti non riconosciuto");
							}
							logger.info("Reload Utenti - Url: " + urlReloadUtenti + " - Esito: " + resp);
							urlReloadUtenti = "";
							if (resp == null || !resp.equalsIgnoreCase("OK")) {
								erroreReload = "Errore durante il reload utenti sull'ENDPOINT";
										
							}
						}
					}
					//if(i==8) break;
				}
			} catch (Exception excEndpoint) {
				okEndpoint = false;
				logger.error("Attenzione Si e'' verificato un errore Su EndPoint " + epc.getEndPoint().getNome());
			} finally {
				DbUtil.chiudiConnessioneJDBC(null, conn);
			}

		} catch (SQLException e) {
			logger.error("", e);
		}

		//if (erroreEndpoint != null && !erroreEndpoint.equals("")) {
		//	setErrore(erroreEndpoint);
		//} else {
		if(!erroreEndpoint.equals("")) erroreEndpoint = "Errore durante l'inserimento degli utenti:\n"+ erroreEndpoint;
			setMessaggio("Allineati "+userAllineati+" su " + userDaAllineare + " in "+ ruolo.toUpperCase()+"\n"+utentiAllineati+"\n<font color='orange'>"+erroreReload+"</font>\n<font color='red'>"+erroreEndpoint+"</font>");
			//setErrore("Allineati "+userAllineati+" su " + userDaAllineare + " in "+ ruolo.toUpperCase()+"\n\n"+erroreReload+"\n\n"+erroreEndpoint);
		//}

		goToAction(new Home());
	}
	

}
