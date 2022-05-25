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

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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

public class SettaMantelliRazzeTaglie extends HttpServlet
{
	private static final long serialVersionUID = -8397394451535054535L;

	public void init(ServletConfig config) throws ServletException 
	{
		try
		{
			ServletContext context = config.getServletContext();
			Context ctx = new InitialContext();
			javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/bduS");
			Connection connection = ds.getConnection();
		
		

			Class.forName("org.postgresql.Driver");
			Statement st = null;
			ResultSet rs = null;
			
			List<LookupMantelli> mantelliCane = null;
			List<LookupMantelli> mantelliGatto = null;
			st = connection.createStatement();
			rs = st.executeQuery("select * from public_functions.getmantelli()");
			mantelliCane = new ArrayList<LookupMantelli>();
			mantelliGatto = new ArrayList<LookupMantelli>();
			while(rs.next())
			{
				LookupMantelli mantello = new LookupMantelli();
				mantello.setCane(rs.getBoolean("cane"));
				mantello.setDescription(rs.getString("description"));
				mantello.setEnabled(rs.getBoolean("enabled"));
				mantello.setId(rs.getInt("id"));
				mantello.setLevel(rs.getInt("level"));
				mantello.setGatto(rs.getBoolean("gatto"));
				if(mantello.getCane())
					mantelliCane.add(mantello);
				else if(mantello.getGatto())
					mantelliGatto.add(mantello);
			}
			context.setAttribute("mantelliCane",		mantelliCane);
			context.setAttribute("mantelliGatto",		mantelliGatto);
			
			
			st = null;
			rs = null;
			
			List<LookupRazze> razzeCane  = null;
			List<LookupRazze> razzeGatto = null;
			st = connection.createStatement();
			rs = st.executeQuery("select * from public_functions.getrazze()");
			razzeCane = new ArrayList<LookupRazze>();
			razzeGatto = new ArrayList<LookupRazze>();
			while(rs.next())
			{
				LookupRazze razza = new LookupRazze();
				razza.setCane(rs.getBoolean("cane"));
				razza.setDescription(rs.getString("description"));
				razza.setEnabled(rs.getBoolean("enabled"));
				razza.setId(rs.getInt("id"));
				razza.setLevel(rs.getInt("level"));
				razza.setEnci(rs.getString("enci"));
				razza.setGatto(rs.getBoolean("gatto"));
				if(razza.getCane())
					razzeCane.add(razza);
				else if(razza.getGatto())
					razzeGatto.add(razza);
			}
			
			
			context.setAttribute("razzeCane", 			razzeCane);
			context.setAttribute("razzeGatto", 		  	razzeGatto);
			
			
			
			
			Persistence persistence = null;
			persistence = PersistenceFactory.getPersistenceCanina();
			
			List<LookupTaglie> taglie = null;
			taglie = persistence.createSQLQuery("select * from public_functions.gettaglie()", LookupTaglie.class).list();
			
			PersistenceFactory.closePersistence( persistence, false );
			
			context.setAttribute("taglie", 		 	 	CaninaRemoteUtil.getTaglie(null));
			
			
			
			
			PersistenceFactory.closePersistence(persistence, false);
			connection.close();
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	
	}
	
}
