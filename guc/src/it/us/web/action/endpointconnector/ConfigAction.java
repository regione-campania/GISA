/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.endpointconnector;

import java.sql.SQLException;

import it.us.web.action.GenericAction;
import it.us.web.action.Index;
import it.us.web.bean.endpointconnector.EndPointConnectorList;
import it.us.web.db.DbUtil;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.exceptions.NotLoggedException;



public class ConfigAction extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{
		isLogged();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
		
		EndPointConnectorList listaEndPoint = new EndPointConnectorList();
		listaEndPoint.creaLista(db);
		req.setAttribute("listaEndPoint", listaEndPoint);
		
		gotoPage( "/jsp/endpointconnector/endpointlist.jsp" );
		
	}
	
	public EndPointConnectorList getEndPointConnector()
	{
		EndPointConnectorList listaEndPointConnector = new EndPointConnectorList();
		
		try {
			db = DbUtil.getConnection() ;
		
		listaEndPointConnector.creaLista(db);
		}
		catch (Exception e)
		{
			try {
				DbUtil.close(db);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			req.setAttribute( "errore", e.getMessage() );
			
				try
				{
					GenericAction.goToAction( new Index(), req, res );
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}
			
		}
		return listaEndPointConnector;
		
	}
	

}
