/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.servlet;

import it.us.web.action.Action;
import it.us.web.action.GenericAction;
import it.us.web.action.Index;
import it.us.web.bean.BGuiView;
import it.us.web.bean.BUtente;
import it.us.web.bean.remoteBean.RegistrazioniCaninaResponse;
import it.us.web.bean.vam.lookup.LookupMantelli;
import it.us.web.bean.vam.lookup.LookupRazze;
import it.us.web.bean.vam.lookup.LookupTaglie;
import it.us.web.constants.Specie;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.hibernate.PersistenceFactory;
import it.us.web.exceptions.ActionNotValidException;
import it.us.web.exceptions.NotLoggedException;
import it.us.web.permessi.Permessi;
import it.us.web.util.DateUtils;
import it.us.web.util.FloatConverter;
import it.us.web.util.MyDoubleConverter;
import it.us.web.util.MyIntegerConverter;
import it.us.web.util.properties.Application;
import it.us.web.util.properties.Message;
import it.us.web.util.vam.CaninaRemoteUtil;
import it.us.web.util.vam.FelinaRemoteUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;

public class MessaggioHome extends HttpServlet
{
	private static final long serialVersionUID = -8397394451535054535L;

	public void init(ServletConfig config) throws ServletException 
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(new File(config.getServletContext().getRealPath("jsp/messaggi/messaggio.txt"))));

		    String mes = "" ;
		    mes = br.readLine();
		    while (mes != null && ! "".equals(mes))
		    {
		            mes +=mes ;
		            mes = br.readLine();
		    }
		    br.close();
		    
		    Timestamp dataUltimaModifica = new Timestamp(System.currentTimeMillis());
	        config.getServletContext().setAttribute("MessaggioHome", dataUltimaModifica+"&&<b>"+mes+"</b>");
		} 
		catch (FileNotFoundException e) 
		{
	            e.printStackTrace();
	    } 
		catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	    }
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	
	}
	
}
