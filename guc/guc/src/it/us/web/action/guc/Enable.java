/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.guc;

import it.us.web.action.GenericAction;
import it.us.web.action.Home;
import it.us.web.action.endpointconnector.ConfigAction;
import it.us.web.bean.endpointconnector.EndPointConnector;
import it.us.web.bean.endpointconnector.EndPointConnectorList;
import it.us.web.bean.endpointconnector.Operazione;
import it.us.web.bean.guc.Utente;
import it.us.web.dao.guc.LogUtenteDAO;
import it.us.web.dao.guc.UtenteGucDAO;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.guc.DbUtil;
import it.us.web.util.guc.GUCEndpoint;
import it.us.web.util.guc.GUCOperationType;
import it.us.web.util.guc.UrlUtil;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import javax.xml.xpath.XPathConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Enable extends GenericAction
{
	
	private static final Logger logger = LoggerFactory.getLogger( Enable.class );

	@Override
	public void can() throws AuthorizationException
	{
		isLogged();
	}


	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
		
		Utente u = null;
		boolean okEndpoint;
		boolean esisteUtente;
		String erroreEndpoint = "";
		int id = interoFromRequest("id");
		String tipo = stringaFromRequest("tipo");
		String endpoint = stringaFromRequest("endpoint");
		String urlReloadUtenti="";
		//Recupero l'utente
		List<Utente> utentiList = UtenteGucDAO.listaUtentibyId(db, id);//persistence.createCriteria(Utente.class).add(Restrictions.eq("id", id)).list();
		if( utentiList.size() > 0 ){
			u = utentiList.get(0);
			req.setAttribute("UserRecord", u);
		
			EndPointConnectorList listaEndPointConnector = (EndPointConnectorList) req.getSession().getAttribute("listaEndPointConnector");
			EndPointConnector epcEnable = listaEndPointConnector.getByIdOperazioneNomeEndPoint(Operazione.ENABLEUTENTE, endpoint); 
			EndPointConnector epcDisable = listaEndPointConnector.getByIdOperazioneNomeEndPoint(Operazione.DISABLEUTENTE, endpoint); 
			EndPointConnector epcOperazione = null;
			
				  
			Connection conn = null;
			String esito = null;
			okEndpoint = true;
			try{
				
				if(tipo.equals("abilita")){
					epcOperazione = epcEnable;
				}else {
					epcOperazione = epcDisable;
				}
						
				
						urlReloadUtenti = epcOperazione.getUrlReloadUtenti();
						String dataSource = epcOperazione.getEndPoint().getDataSourceSlave();
						conn = DbUtil.ottieniConnessioneJDBC(dataSource);
						
						esito = this.gestioneDisabilitaUtente(epcOperazione, u, conn);
						
						//se per questo endpoint l'utente esiste ha senso andarlo ad attivare/disattivare
						if(esito.equals("OK")){
							setMessaggio("Utente con username " + u.getUsername() + (tipo.equals("abilita") ? " riattivato" : " disabilitato") +  " con successo" );
						}
						
					}
					catch(Exception excEndpoint){
						okEndpoint = false;
						excEndpoint.printStackTrace();
					}
					finally {
						DbUtil.chiudiConnessioneJDBC(null, conn);
					}
					
					if(!okEndpoint){
						logger.error("Errore durante " + (u.isEnabled() ? "l'attivazione":"la disattivazione") + " dell'utente " + u.getUsername() + " nell'endpoint " + epcOperazione.getEndPoint().getNome());
						erroreEndpoint = erroreEndpoint + "Errore durante " + (u.isEnabled() ? "l'attivazione":"la disattivazione") + " dell'utente in " + epcOperazione.getEndPoint().getNome() + "\n" ;
					}
					else {
						if (urlReloadUtenti!=null && !urlReloadUtenti.equals("")){
							urlReloadUtenti = urlReloadUtenti+""+u.getUsername();	//RELOAD PUNTUALE
							String resp = UrlUtil.getUrlResponse(urlReloadUtenti);
							logger.info("Reload Utenti - Url: " + urlReloadUtenti + " - Esito: " + resp);
							urlReloadUtenti = "";
							if (resp==null || !resp.equalsIgnoreCase("OK")){
							//	erroreEndpoint = erroreEndpoint + "Errore durante il reload dell'utente in " + epcOperazione.getEndPoint().getNome() + "\n";
							}
						} 
					}
					
		}//fine if
				if(erroreEndpoint != null && !erroreEndpoint.equals("")){
					setErrore(erroreEndpoint);
				}
				
				
				redirectTo("Home.us?");
				
			}

}
