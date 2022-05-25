/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcoloNominativi;
import org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcoloNominativiList;
import org.aspcfs.modules.vigilanza.blocchicu.ApplicationProperties;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.extend.LoginRequiredException;

import com.darkhorseventures.database.ConnectionElement;

public class DwrNucleoIspettivo {




	public static DpatStrumentoCalcoloNominativi[] getcomponentiNucleoIspettivo(int idQualificaRuolo,String idAsl)
	{

		DpatStrumentoCalcoloNominativiList nomList = new DpatStrumentoCalcoloNominativiList();
		nomList.setIdQualifica(idQualificaRuolo);
		Connection db = null;

		try
		{
			WebContext ctx = WebContextFactory.get();
			HttpServletRequest request = ctx.getHttpServletRequest();
			
			ApplicationPrefs prefs = (ApplicationPrefs) request.getSession().getServletContext().getAttribute("applicationPrefs");
			String ceDriver = prefs.get("GATEKEEPER.DRIVER");
			String ceHost = prefs.get("GATEKEEPER.URL");
			String ceUser = prefs.get("GATEKEEPER.USER");
			String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");

			
			ConnectionElement ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
			SystemStatus thisSystem = null; 
			HashMap sessions = null;
			thisSystem = (SystemStatus) ((Hashtable) request.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
			
			
			db = GestoreConnessioni.getConnection()	;
			/*SE NON e CRIUV*/

			int idAslIn = -1 ;
			if (idAsl!= null && !idAsl.equals(""))
				idAslIn=Integer.parseInt(idAsl);
			
			
			boolean inDpat = false ;
			boolean viewLista = false ;
			String sql = "select in_dpat,view_lista_utenti_nucleo_ispettivo from lookup_qualifiche where code = "+idQualificaRuolo;
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				inDpat = rs.getBoolean(1);
				viewLista =  rs.getBoolean(2);
			}
					
			
			Calendar calCorrente = GregorianCalendar.getInstance();
			Date dataCorrente = new Date(System.currentTimeMillis());
			int tolleranzaGiorni = Integer.parseInt(ApplicationProperties.getProperty("TOLLERANZA_MODIFICA_CU"));
			dataCorrente.setDate(dataCorrente.getDate()- tolleranzaGiorni);
			calCorrente.setTime(new Timestamp(dataCorrente.getTime()));
			int anno = calCorrente.get(Calendar.YEAR);
			
			
			
			
			
			ArrayList<User>  listaUtenti = UserUtils.getUserFromRole(request, idAslIn,idQualificaRuolo);
			if(inDpat==true)
			{
				nomList.buildList2(db,idAslIn,anno,thisSystem);
			}

			else
			{
				if (viewLista)
					nomList.buildListNonInDpat(db,idAslIn,idQualificaRuolo,request);



			}

			if (nomList.size()==0 && inDpat==true )
			{
				DpatStrumentoCalcoloNominativi nom =  new DpatStrumentoCalcoloNominativi();
				User u = new User();
				u.getContact().setNameFirst("Nessuna risorsa");
				u.getContact().setNameLast("nel Dpat");
				u.setId(-1);
				u.setPassword("");
				u.setUsername("");
				nom.setNominativo(u);
				

				
				nomList.add(nom);
			}
			



			return nomList.getListaNominativiasArray();

		}catch(LoginRequiredException e)
		{
			throw e;
		}
		catch(SQLException e)
		{
			
		}finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		return null;
	}
	
	
	
	
	
	public static DpatStrumentoCalcoloNominativi[] getcomponentiNucleoIspettivoRistrutturato (Connection db ,int idQualificaRuolo,String idAsl,SystemStatus thisSystem,HttpServletRequest request)
	{

		DpatStrumentoCalcoloNominativiList nomList = new DpatStrumentoCalcoloNominativiList();
		nomList.setIdQualifica(idQualificaRuolo);
		

		try
		{
			
			/*SE NON e CRIUV*/

			int idAslIn = -1 ;
			if (idAsl!= null && !idAsl.equals(""))
				idAslIn=Integer.parseInt(idAsl);
			
			
			boolean inDpat = false ;
			boolean viewLista = false ;
			//String sql = "select in_dpat,view_lista_utenti_nucleo_ispettivo from lookup_qualifiche where code = "+idQualificaRuolo;
			String sql = "select in_dpat, true from lookup_qualifiche where code = "+idQualificaRuolo;
			PreparedStatement pst = db.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				inDpat = rs.getBoolean(1);
				viewLista =  rs.getBoolean(2);
			}
					
			
			Calendar calCorrente = GregorianCalendar.getInstance();
			Date dataCorrente = new Date(System.currentTimeMillis());
			int tolleranzaGiorni = Integer.parseInt(ApplicationProperties.getProperty("TOLLERANZA_MODIFICA_CU"));
			dataCorrente.setDate(dataCorrente.getDate()- tolleranzaGiorni);
			calCorrente.setTime(new Timestamp(dataCorrente.getTime()));
			int anno = calCorrente.get(Calendar.YEAR);
			
			ArrayList<User>  listaUtenti = UserUtils.getUserFromRole(request, idAslIn,idQualificaRuolo);
			if(inDpat==true)
			{
				nomList.buildList2(db,idAslIn,anno,thisSystem);
			}

			else
			{
				if (viewLista)
					nomList.buildListNonInDpat(db,idAslIn,idQualificaRuolo,request);



			}

			if (nomList.size()==0 && inDpat==true )
			{
				DpatStrumentoCalcoloNominativi nom =  new DpatStrumentoCalcoloNominativi();
				User u = new User();
				u.getContact().setNameFirst("Nessuna risorsa");
				u.getContact().setNameLast("nel Dpat");
				u.setId(-1);
				u.setPassword("");
				u.setUsername("");
				nom.setNominativo(u);
								
				nomList.add(nom);
			}
			



			return nomList.getListaNominativiasArray();

		}
		catch(SQLException e)
		{
			
		}finally
		{
			GestoreConnessioni.freeConnection(db);
		}
		return null;
	}

	public static boolean hasVista (int idQualificaRuolo)
	{
		Connection db = null;
		boolean viewLista = true ;
			try
			{
				WebContext ctx = WebContextFactory.get();
				HttpServletRequest request = ctx.getHttpServletRequest();
				
				ApplicationPrefs prefs = (ApplicationPrefs) request.getSession().getServletContext().getAttribute("applicationPrefs");
				String ceDriver = prefs.get("GATEKEEPER.DRIVER");
				String ceHost = prefs.get("GATEKEEPER.URL");
				String ceUser = prefs.get("GATEKEEPER.USER");
				String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");

				
				ConnectionElement ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
				SystemStatus thisSystem = null; 
				HashMap sessions = null;
				thisSystem = (SystemStatus) ((Hashtable) request.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
				db = GestoreConnessioni.getConnection()	;
			
				String sql = "select view_lista_utenti_nucleo_ispettivo from lookup_qualifiche where code = "+idQualificaRuolo;
				PreparedStatement pst = db.prepareStatement(sql);
				ResultSet rs = pst.executeQuery();
				if (rs.next())
				{
					viewLista =  rs.getBoolean(1);
				}
						
			}catch(LoginRequiredException e)
			{
				throw e;
			}
			catch(SQLException e)
			{
				
			}finally
			{
				GestoreConnessioni.freeConnection(db);
			}
			return viewLista;
		}
	
}
