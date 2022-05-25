/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.servlet;

import it.us.web.action.GenericAction;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bootstrap extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	final Logger logger = LoggerFactory.getLogger(Bootstrap.class);
       
    public Bootstrap() {
        super();
    }

	@Override
	public void init(ServletConfig config) throws ServletException {
		
		try{
			String fileXML = config.getServletContext().getRealPath("WEB-INF/endpoint-connector.xml");
			
			GenericAction.setFactory( DocumentBuilderFactory.newInstance() );
			GenericAction.getFactory().setValidating(false);
			GenericAction.setBuilder( GenericAction.getFactory().newDocumentBuilder() );
			GenericAction.setDoc( GenericAction.getBuilder().parse(new File(fileXML)) );
			GenericAction.setXpathfactory( XPathFactory.newInstance() );
			GenericAction.setXpath( GenericAction.getXpathfactory().newXPath() );
			
			  
		}
		catch(Exception e){
			logger.error("Eccezione durante la lettura dell'XML: " + e.getMessage());
			e.printStackTrace();
		}
		
	}

	

}
