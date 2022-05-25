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
import it.us.web.bean.SuperUtente;
import it.us.web.dao.GuiViewDAO;
import it.us.web.dao.SuperUtenteDAO;
import it.us.web.dao.UtenteDAO;
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

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class SettaFunzioniConcesse extends HttpServlet
{
	private static final long serialVersionUID = -8397394451535054535L;

	public void init(ServletConfig config) throws ServletException 
	{
		Connection connection = null;
		try
		{
			
		ServletContext context = config.getServletContext();
		Context ctx = new InitialContext();
		javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/vamM");
		connection = ds.getConnection();
		
		HashMap<Integer, HashMap<String, String>> funzioniConcesse       = new HashMap<Integer, HashMap<String, String>>();
		HashMap<String, String> 				  funzioniConcesseUtente = null;
		HashMap<Integer, String> ruoliUtenti       = new HashMap<Integer, String>();
		String ruoloUtente = null;

		//Vector<BGuiView> funzioniAll 		= null;
		
		
			Iterator<BUtente> utentiAll = null;
			ArrayList<BUtente> utenti = getUtenti(connection);
			if(!utenti.isEmpty())
				utentiAll = utenti.iterator();
			//funzioniAll 			= GuiViewDAO.getAll();
		
		connection.close();
			
		while(utentiAll.hasNext())
		{
			Connection connectionUtente = ds.getConnection();
			funzioniConcesseUtente 	= new HashMap<String, String>();
			ruoloUtente = "";
			BUtente tempU 			= utentiAll.next();
			//Se non è già stato inserito 
			if(funzioniConcesse.get(tempU.getSuperutente().getId())==null && tempU.getEnabled())
			{
				String sql = "select cap.subject_name as funzione " +
						"from capability_permission cap_per, capability cap, category_secureobject cat_sec " +
						"where cap_per.permissions_name = 'w' and " +
						"cap_per.capabilities_id = cap.id and " +
						"cap.category_name = cat_sec.categories_name and " +
						"cat_sec.secureobjects_name = ?" ;
			
				PreparedStatement st = connectionUtente.prepareStatement(sql);
				st.setString(1, tempU.getId()+"");
				ResultSet rs = st.executeQuery();
			
				while( rs.next())
				{
					//if (!context.getAttribute("dbMode").equals("slave")){
						funzioniConcesseUtente.put(rs.getString(1), "true");	
					/*}
					else if (!rs.getString(1).contains("ADD") && !rs.getString(1).contains("EDIT") && !rs.getString(1).contains("DELETE")) {
						funzioniConcesseUtente.put(rs.getString(1), "true");
					} */
				}
				
				
				
				
//				context.setAttribute("ambiente", Application.get("ambiente"));
//				context.setAttribute("dbMode", context.getAttribute("dbMode"));
				
				ruoloUtente = tempU.getRuoloByTalos();
				
				
				/*while(iterFunzioniAll.hasNext())
				{
					BGuiView tempF = iterFunzioniAll.next();
					if(Permessi.can(persistence, tempU, tempF,"w"))
						funzioniConcesseUtente.put(tempF.getKey(), "true");
				}*/
				funzioniConcesse.put(tempU.getSuperutente().getId(), funzioniConcesseUtente);
				ruoliUtenti.put(tempU.getSuperutente().getId(), ruoloUtente);
			}
			connectionUtente.close();
		}
		context.setAttribute( "funzioniConcesse", funzioniConcesse);
		context.setAttribute( "ruoliUtenti",      ruoliUtenti);
		
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	
	}
	
	
	
	private static ArrayList<BUtente> getUtenti(Connection connection) throws SQLException
	{
		ArrayList<BUtente> utenti = new ArrayList<BUtente>();
		String sql = "select id, enabled, superutente from utenti where trashed_date is null " ;
		PreparedStatement st = connection.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
		{
			BUtente utente = new BUtente();
			utente.setId(rs.getInt("id"));
			utente.setEnabled(rs.getBoolean("enabled"));
			SuperUtente superUtente = new SuperUtente();
			superUtente.setId(rs.getInt("superutente"));
			utente.setSuperutente(superUtente);
			utenti.add(utente);
		}
		return utenti;
	}
	
}
