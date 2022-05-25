/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.opu.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.accounts.base.ComuniAnagrafica;
import org.aspcfs.modules.accounts.base.Provincia;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.IndirizzoList;
import org.aspcfs.modules.opu.base.SoggettoFisico;
import org.aspcfs.modules.opu.base.SoggettoList;
import org.aspcfs.utils.web.PagedListInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import com.darkhorseventures.database.ConnectionPool;
 
/**
 * Servlet implementation class ServletComuni
 */
public class ServletComuni extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	/** 
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletComuni() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	/*protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json");
		JSONArray json_arr=new JSONArray();
		ArrayList<ComuniAnagrafica> listaComuni = null ;
		String inRegione = request.getParameter("inRegione");
		if(inRegione.equals("si"))
		{
			listaComuni= (ArrayList<ComuniAnagrafica> ) getServletContext().getAttribute("ListaComuniInRegione");
		}
		else
		{
			listaComuni = (ArrayList<ComuniAnagrafica> ) getServletContext().getAttribute("ListaComuniFuoriRegione");
		}

		JSONObject json_obj = null ;
		try {
		for (ComuniAnagrafica c : listaComuni)
		{

				json_obj=new JSONObject(c.getHashmap());

			 json_arr.put(json_obj);
		}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 response.getWriter().println(json_arr);

	}*/


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		JSONArray json_arr=null;
		Connection db = null;
		ConnectionPool cp = null ;
		try
		{
		ApplicationPrefs applicationPrefs = (ApplicationPrefs) getServletContext().getAttribute(
		"applicationPrefs");

		ApplicationPrefs prefs = (ApplicationPrefs) getServletContext().getAttribute("applicationPrefs");
		String ceDriver = prefs.get("GATEKEEPER.DRIVER");
		String ceHost = prefs.get("GATEKEEPER.URL");
		String ceUser = prefs.get("GATEKEEPER.USER");
		String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");

//		ConnectionElement ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
//		ce.setDriver(ceDriver);

		cp = (ConnectionPool)getServletContext().getAttribute("ConnectionPool");
		db = cp.getConnection(null);
		response.setContentType("application/json");
		String inRegione = request.getParameter("inRegione");
		String estero = request.getParameter("estero");
		String tipoRichiesta = "";
			
			String combo = request.getParameter("combo");
			if (combo == null)
				combo = (String) request.getAttribute("combo");
		if(combo.equalsIgnoreCase("searchcodeIdComune") || combo.equalsIgnoreCase("addressLegaleCity") ||  combo.equalsIgnoreCase("comune_ritrovamento"))
		{
			tipoRichiesta = "2";
		}
		if(combo.equalsIgnoreCase("searchcodeIdprovincia") || combo.equalsIgnoreCase("addressLegaleCountry") || combo.equalsIgnoreCase("inregione") || combo.equalsIgnoreCase("provincia_ritrovamento"))
		{
			tipoRichiesta = "1";
		}
		if(combo.equalsIgnoreCase("via") || combo.equalsIgnoreCase("addressLegaleLine1"))
		{
			tipoRichiesta = "3" ;
		}
		if(combo.equalsIgnoreCase("codFiscaleSoggetto"))
		{
			tipoRichiesta = "4" ;
		}
		if (combo.equalsIgnoreCase("5")){
			tipoRichiesta = "5";
		}
		
		
		switch(Integer.parseInt(tipoRichiesta))
		{
		case 1 : // richiesta province
		{
			json_arr = new JSONArray();
			String nomeStart = request.getParameter("nome");
			Provincia p = new Provincia();
			ArrayList<Provincia> listaProvince = p.getProvince(db, nomeStart,inRegione,estero);
			JSONObject json_obj = null ;
			try {
			for (Provincia c : listaProvince)
			{

					json_obj=new JSONObject(c.getHashmap());

				 json_arr.put(json_obj);
			}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			 
			 break ;

		}
		case 2: // richiesta comuni
		{
			json_arr = new JSONArray();
			ComuniAnagrafica comuni = new ComuniAnagrafica();
			String idProvincia = request.getParameter("idProvincia");
			String nomeStart = request.getParameter("nome");
			String idAslString = request.getParameter("idAsl");
			int idAsl = -1;
			if (idAslString != null && !("").equals(idAslString))
				idAsl = Integer.parseInt(idAslString);
			ArrayList<ComuniAnagrafica> listaComuni = comuni.getComuni(db, Integer.parseInt(idProvincia),nomeStart,inRegione, idAsl,estero);
			JSONObject json_obj = null ;
			try {
			for (ComuniAnagrafica c : listaComuni)
			{

					json_obj=new JSONObject(c.getHashmap());

				 json_arr.put(json_obj);
			}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			 break ;

		}
		case 3 :
		{ //richiesta indirizzi
			String nomeStart = request.getParameter("nome");

			IndirizzoList listIndirizzi = new IndirizzoList();
			listIndirizzi.setIdComune(Integer.parseInt(request.getParameter("idComune")));
			listIndirizzi.setStartComune(nomeStart);
			
			listIndirizzi.buildList(db);
			json_arr = new JSONArray();
			JSONObject json_obj = null ;
			try {
			for (Object c : listIndirizzi)
			{
				Indirizzo temp = (Indirizzo)c ;

					json_obj=new JSONObject(temp.getHashmap());

				 json_arr.put(json_obj);
			}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			break ;
		}
		case 4 :
		{
			String nomeStart = request.getParameter("nome");

			SoggettoList listaSoggetti = new SoggettoList();
			listaSoggetti.setCodFiscale(nomeStart);
			
			listaSoggetti.setPagedListInfo(this.getPagedListInfo(request.getSession(), "ListInfoSoggetti", false));
			listaSoggetti.buildList(db);
			json_arr = new JSONArray();
			JSONObject json_obj = null ;
			try {
			for (Object c : listaSoggetti)
			{
				SoggettoFisico temp = (SoggettoFisico)c ;

					json_obj=new JSONObject(temp.getHashmap());

				 json_arr.put(json_obj);
			}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break ;
		
		}
		
		
		case 5: // richiesta comuni per asl
		{
			json_arr = new JSONArray();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("codice", "-1");
			map.put("descrizione", "Tutti");
			JSONObject json_obj=new JSONObject(map);
			json_arr.put(json_obj);
			ComuniAnagrafica comuni = new ComuniAnagrafica();
			String idAsl = request.getParameter("idAsl");
			
			ArrayList<ComuniAnagrafica> listaComuni = comuni.getComuniByIdAsl(db, Integer.parseInt(idAsl));
			try {
			for (ComuniAnagrafica c : listaComuni)
			{

					json_obj=new JSONObject(c.getHashmap());

				 json_arr.put(json_obj);
			}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			 break ;

		}
		
		
		}
		}
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			
			cp.closeConnection(db, null);
		}
		

		response.getWriter().println(json_arr.toString().replaceAll(",}", "}"));

	}
	
	private PagedListInfo getPagedListInfo(HttpSession session, String viewName, boolean setParams) {
	    PagedListInfo tmpInfo = (PagedListInfo) session.getAttribute(
	        viewName);
	    if (tmpInfo == null || tmpInfo.getId() == null) {
	      tmpInfo = new PagedListInfo();
	      tmpInfo.setId(viewName);
	      session.setAttribute(viewName, tmpInfo);
	    }
	    
	    return tmpInfo;
	  }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
