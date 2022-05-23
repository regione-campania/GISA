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

public class AddRuolo extends GenericAction
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

		HashMap<String, String> query=new HashMap<String, String>();
	
		boolean okEndpoint;
		String erroreEndpoint = "";
		String successoEndPoint= "";
		
		try{

					//Inserimento ruolo nei vari endpoint
					
					EndPointConnectorList listaEndPointConnector = (EndPointConnectorList) req.getSession().getAttribute("listaEndPointConnector");
					EndPointConnectorList listaEndPointConnectorOperazione = listaEndPointConnector.getByIdOperazione(Operazione.INSERTRUOLO); 
				
					Connection conn = null;
					
					  for(int i = 0; i <listaEndPointConnectorOperazione.size(); i++){
						
						  EndPointConnector epc = (EndPointConnector) listaEndPointConnectorOperazione.get(i);
					
						  boolean epSelezionato = false;
						  okEndpoint = true;
					
						try { 
						Ruolo r = new Ruolo();
						r.buildFromRequest(req, epc.getIdEndPoint());
						if (r.isSelected()){
							epSelezionato = true;
							System.out.println("Provo a inserire ruolo su " + (epc.getEndPoint().getNome()));
							String QQQuery = epc.getSql();
							System.out.println("QQQ INSERIMENTO RUOLO "+QQQuery);
							String dataSource = epc.getEndPoint().getDataSourceSlave();		
							conn = DbUtil.ottieniConnessioneJDBC(dataSource);	
							okEndpoint = insert_ruolo(epc, r, conn);
							query.put(epc.getEndPoint().getNome().toUpperCase(), super.DBI); super.DBI="";
							System.out.println("Inserito ruolo su " + (epc.getEndPoint().getNome()));
						}

					} catch (Exception excEndpoint) {
						okEndpoint = false;
						excEndpoint.printStackTrace();
					} finally {
						DbUtil.chiudiConnessioneJDBC(null, conn);
					}
						
					if (epSelezionato){
						if(!okEndpoint){
							logger.error("Errore durante l'inserimento del ruolo nell'endpoint " + epc.getEndPoint().getNome());
							erroreEndpoint = erroreEndpoint + "Errore durante l'inserimento del ruolo (gia'' esistente) nell'endpoint " + epc.getEndPoint().getNome() + "\n" ;
						}else {
							successoEndPoint = successoEndPoint + "Ruolo inserito con successo nell'endpoint " + epc.getEndPoint().getNome() + "\n";
						}
					}
					}

					if(erroreEndpoint != null && !erroreEndpoint.equals("")){
						setErrore(erroreEndpoint);
					} 
					if(successoEndPoint != null && !successoEndPoint.equals("")){
						setMessaggio(successoEndPoint); 
					}
					redirectTo("guc.ToAddRuolo.us");	
		}
		catch(Exception e){
			e.printStackTrace();
			setErrore("Errore durante l'inserimento del ruolo");
			goToAction(new ToAddRuolo());
		}

	}

	


	private Boolean insert_ruolo (EndPointConnector epc, Ruolo r, Connection conn) throws Exception{
		Boolean okEndpoint = false;
		okEndpoint = this.gestioneInserimentoRuolo(epc, r, conn);
		return okEndpoint;
	}



}
