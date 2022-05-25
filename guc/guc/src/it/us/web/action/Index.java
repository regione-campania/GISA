/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action;

import it.us.web.action.endpointconnector.ConfigAction;
import it.us.web.bean.endpointconnector.EndPointConnectorList;
import it.us.web.exceptions.AuthorizationException;

public class Index extends GenericAction
{
	
	
	public Index() {		
	}
	
	@Override
	public void can() throws AuthorizationException
	{

	}

	public EndPointConnectorList inizializzaEndPointConnector(){
	//Da mettere in session all'avvio?
		  ConfigAction ac = new ConfigAction();
		  EndPointConnectorList listaEndPointConnector = ac.getEndPointConnector();
		  return listaEndPointConnector;
	}

	@Override
	public void execute() throws Exception
	{
		
		req.setAttribute("test", req.getParameter("test"));
		
		System.out.println("GUC *** Costruisco e metto in sessione EndPointConnector.");
		EndPointConnectorList listaEndPointConnector = inizializzaEndPointConnector();
		req.getSession().setAttribute("listaEndPointConnector", listaEndPointConnector);
		System.out.println("GUC *** Messo in sessione EndPointConnector *** size: "+listaEndPointConnector.size());
		
		
		if( utente == null )
		{
			gotoPage("/jsp/guc/index.jsp" );
		}
		else
		{	gotoPage("/jsp/guc/pannello.jsp");
		//	goToAction( new Home() );
		}
		
	}

}
