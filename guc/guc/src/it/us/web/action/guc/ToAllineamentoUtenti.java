/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.guc;

import it.us.web.action.GenericAction;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.guc.GUCEndpoint;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ToAllineamentoUtenti extends GenericAction
{

	@Override
	public void can() throws AuthorizationException
	{
		isLogged();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception{
		
		try{
			NodeList endpointList = (NodeList)xpath.evaluate("//endpoint", doc, XPathConstants.NODESET);
			Element endpointElem = null;
			GUCEndpoint nomeEndpoint = null;
			List<String> endpoints = new ArrayList<String>();
			
			for(int i = 0; i < endpointList.getLength(); i++){
				endpointElem = (Element)endpointList.item(i);
				nomeEndpoint = GUCEndpoint.valueOf( endpointElem.getAttribute("name") );
				endpoints.add(nomeEndpoint.toString());
			}
			
			req.setAttribute("endpoints", endpoints);
			
			gotoPage( "/jsp/guc/allineamentoUtenti.jsp" );
		}
		catch(Exception excEndpoint){
			setErrore(excEndpoint.getMessage());
			excEndpoint.printStackTrace();
			goToAction(new ToAllineamentoUtenti());
		}
		
	}
	

}
