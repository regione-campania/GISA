/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.guc;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import it.us.web.action.GenericAction;
import it.us.web.action.Home;
import it.us.web.action.endpointconnector.ConfigAction;
import it.us.web.bean.endpointconnector.EndPointConnector;
import it.us.web.bean.endpointconnector.EndPointConnectorList;
import it.us.web.bean.endpointconnector.Operazione;
import it.us.web.bean.guc.Asl;
import it.us.web.bean.guc.Utente;
import it.us.web.dao.guc.LogUtenteDAO;
import it.us.web.dao.guc.UtenteGucDAO;
import it.us.web.db.ApplicationProperties;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.guc.DbUtil;
import it.us.web.util.guc.GUCEndpoint;
import it.us.web.util.guc.GUCOperationType;

public class ToEnable extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{
		isLogged();
	}

	@SuppressWarnings("unchecked")
	@Override
	/*public void execute() throws Exception
	{
		int id = interoFromRequest("id");
		Utente u = null;
		List<Utente> utentiList = UtenteGucDAO.listaUtentibyId(db, id);//persistence.createCriteria(Utente.class).add(Restrictions.eq("id", id)).list();
		
		if( utentiList.size() > 0 ){
			u = utentiList.get(0);
			req.setAttribute("UserRecord", u);
			
			gotoPage( "/jsp/guc/enable.jsp" );
		}
		else{
			setErrore("Utente con id " + id + " non trovato");
			goToAction(new Home());
		}
		
	}*/
	
	public void execute() throws Exception
	{
		
		Utente u = null;
		boolean okEndpoint;
		String check_login_ok;
		String erroreEndpoint = "";
		int id = interoFromRequest("id");
		//Verifica esistenza utente in GUC
		List<Utente> utentiList = UtenteGucDAO.listaUtentibyId(db, id);//persistence.createCriteria(Utente.class).add(Restrictions.eq("id", id)).list();
		if( utentiList.size() > 0 ){
			u = utentiList.get(0);
			req.setAttribute("UserRecord", u);
			
			costruisciListaRuoli();
			
			ArrayList <String> endpoint_login_ok =  new ArrayList<>();
			ArrayList <String> endpoint_login_ko =  new ArrayList<>();
			
			EndPointConnectorList listaEndPointConnector = (EndPointConnectorList) req.getSession().getAttribute("listaEndPointConnector");
			EndPointConnectorList listaEndPointConnectorOperazione = listaEndPointConnector.getByIdOperazione(Operazione.CHECKENABLEUTENTE); 
			
			for(int i = 0; i <listaEndPointConnectorOperazione.size(); i++){
				  EndPointConnector epc = (EndPointConnector) listaEndPointConnectorOperazione.get(i);
				  
			Connection conn = null;
				okEndpoint = true;
				try{
					String dataSource = epc.getEndPoint().getDataSourceSlave();
				
						conn = DbUtil.ottieniConnessioneJDBC(dataSource);
					

					//Se l'endpoint e'' configurato correttamente
					if(conn!=null)
					{
						String timeout = ApplicationProperties.getProperty("timeout");
						check_login_ok = this.gestioneLastLoginUtente(epc, u, conn, timeout);
						
						if(check_login_ok.equals("KO")){
							//NON ENTRANO nell'endpoint da n mesi
							endpoint_login_ko.add(epc.getEndPoint().getNome());
						}
						
						req.setAttribute("endpoints_ko", endpoint_login_ko);
						req.setAttribute("timeout", timeout);
					}
				}//Fine try
				catch(Exception excEndpoint){
					okEndpoint = false;
					excEndpoint.printStackTrace();
				}
				finally {
					DbUtil.chiudiConnessioneJDBC(null, conn);
				}
			
			}//Fine FOR
		}//FINE IF utente esiste
		else{
			setErrore("Utente con id " + id + " non trovato");
			goToAction(new Home());
		}
			
		gotoPage( "/jsp/guc/enable.jsp" );
		
	}


}
