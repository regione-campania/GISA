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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.accounts.base.ComuniAnagrafica;
import org.aspcfs.modules.accounts.base.Provincia;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.IndirizzoList;
import org.aspcfs.modules.opu.base.SoggettoFisico;
import org.aspcfs.modules.opu.base.SoggettoList;
import org.aspcfs.utils.PopolaCombo;
import org.aspcfs.utils.web.PagedListInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import com.darkhorseventures.database.ConnectionElement;
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
		
		UserBean user = (UserBean) request.getSession().getAttribute("User");

		ApplicationPrefs prefs = (ApplicationPrefs) getServletContext().getAttribute("applicationPrefs");
		String ceDriver = prefs.get("GATEKEEPER.DRIVER");
		String ceHost = prefs.get("GATEKEEPER.URL");
		String ceUser = prefs.get("GATEKEEPER.USER");
		String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");

		ConnectionElement ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
		ce.setDriver(ceDriver);

		cp = (ConnectionPool)getServletContext().getAttribute("ConnectionPool");
		db = cp.getConnection(ce,null);
		response.setContentType("application/json");
		String inRegione = request.getParameter("inRegione");
		String tipoRichiesta = "";
		String nazione = request.getParameter("nazione");
		String combo = request.getParameter("combo");
		String tipoAttivita ="";
		String tipoImpresa ="";
		if (request.getParameter("tipoAttivita")!=null && !((String)request.getParameter("tipoAttivita")).equals(""))
			tipoAttivita=(String)request.getParameter("tipoAttivita");			
		if (request.getParameter("tipo_impresa")!=null && !((String)request.getParameter("tipo_impresa")).equals(""))
			tipoImpresa=(String)request.getParameter("tipo_impresa");			
			
			if (combo == null)
				combo = (String) request.getAttribute("combo");
		if(combo.equalsIgnoreCase("searchcodeIdComune") || combo.equalsIgnoreCase("searchcodeIdComuneStab") || combo.equalsIgnoreCase("searchcodeIdComuneSL") || combo.equalsIgnoreCase("comuneNascita")  || combo.equalsIgnoreCase("addressLegaleCity") ||combo.equalsIgnoreCase("addressLegaleCityStab")  )
		{
			tipoRichiesta = "2";
		}
		if(combo.equalsIgnoreCase("searchcodeIdprovinciaSL") || combo.equalsIgnoreCase("searchcodeIdprovinciaStab") || combo.equalsIgnoreCase("searchcodeIdprovincia")|| combo.equalsIgnoreCase("addressLegaleCountryStab")  || combo.equalsIgnoreCase("addressLegaleCountry") || combo.equalsIgnoreCase("inregione"))
		{
			tipoRichiesta = "1";
		}
		if(combo.equalsIgnoreCase("viaSL") || combo.equalsIgnoreCase("viaStab") || combo.equalsIgnoreCase("via") || combo.equalsIgnoreCase("addressLegaleLine1")  || combo.equalsIgnoreCase("addressLegaleLine1Stab") )
		{
			tipoRichiesta = "3" ;
		}
		if(combo.startsWith("codFiscaleSoggetto"))
		{
			tipoRichiesta = "4" ;
		}
		if (combo.equalsIgnoreCase("5")){
			tipoRichiesta = "5";
		}
		if (combo.equalsIgnoreCase("partitaIva"))
			tipoRichiesta = "6";
			
		
		
		switch(Integer.parseInt(tipoRichiesta))
		{
		case 1 : // richiesta province
		{
			
			
			String idProvincia = request.getParameter("idProvincia");
			String nomeStart = request.getParameter("nome");
			String idAslString = request.getParameter("idAsl");
			int idAsl = -1;
			if (idAslString != null && !("").equals(idAslString))
				idAsl = Integer.parseInt(idAslString);
			
			// Se l'impresa e fissa, arrivo qua, quindi non c'e bisogno di controllare il tipoImpresa
			if (combo.equalsIgnoreCase("searchcodeIdprovinciaStab")){
				if (idAslString != null && !("").equals(idAslString))
					idAsl = Integer.parseInt(idAslString);
			}else{
				idAsl=-1;
			}

			// Caso comune di residenza
			if (combo.equalsIgnoreCase("addressLegaleCountry")){
				// Se l'attivita e fissa, valgono tutte le asl (l'asl di appartenenza la stabilisce il comune dello stabilimento)
				if (tipoAttivita.equals("1"))
					idAsl=-1;
				else{
					// altrimenti controllo se e un'impresa individuale (tipoImpresa=1), in quel caso, l'asl di appartenenza dipende dal comune di residenza
					if (!tipoImpresa.equals("") && tipoImpresa.equals("1")){
						if (idAslString != null && !("").equals(idAslString))
							idAsl = Integer.parseInt(idAslString);
						else
							idAsl=-1;
				
					}else
						idAsl=-1;
			}
			}
			
			// Controllo comune della sede legale
			if (combo.equalsIgnoreCase("searchcodeIdprovincia")){
				// Se l'attivita e fissa, valgono tutte le asl (l'asl di appartenenza la stabilisce il comune dello stabilimento)
				if (tipoAttivita.equals("1"))
					idAsl=-1;
				else{
					// altrimenti controllo se e un'impresa individuale (tipoImpresa=1), in quel caso, l'asl di appartenenza dipende dal comune di residenza
					//if (!tipoImpresa.equals("") && tipoImpresa.equals("1"))
						if (idAslString != null && !("").equals(idAslString))
							idAsl = Integer.parseInt(idAslString);
						else
							idAsl=-1;
				
				}
			}
			
			
			json_arr = new JSONArray();
			Provincia p = new Provincia();
			ArrayList<Provincia> listaProvince = p.getProvince(db, nomeStart,inRegione,nazione,idAsl);
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
			String comune = "";
		
			
			String idProvincia = request.getParameter("idProvincia");
			String nomeStart = request.getParameter("nome");
			String idAslString = request.getParameter("idAsl");
			int idAsl = -1;
			if (idAslString != null && !("").equals(idAslString))
				idAsl = Integer.parseInt(idAslString);
			
			// Se l'impresa e fissa, arrivo qua, quindi non c'e bisogno di controllare il tipoImpresa
			if (combo.equalsIgnoreCase("searchcodeIdComuneStab")){
				if (idAslString != null && !("").equals(idAslString))
					idAsl = Integer.parseInt(idAslString);
			}else{
				idAsl=-1;
			}

			// Caso comune di residenza
			if (combo.equalsIgnoreCase("addressLegaleCity")){
				// Se l'attivita e fissa, valgono tutte le asl (l'asl di appartenenza la stabilisce il comune dello stabilimento)
				if (tipoAttivita.equals("1"))
					idAsl=-1;
				else{
					// altrimenti controllo se e un'impresa individuale (tipoImpresa=1), in quel caso, l'asl di appartenenza dipende dal comune di residenza
					if (!tipoImpresa.equals("") && tipoImpresa.equals("1")){
						if (idAslString != null && !("").equals(idAslString))
							idAsl = Integer.parseInt(idAslString);
						else
							idAsl=-1;
				
					}else
						idAsl=-1;
			}
			}
			
			// Controllo comune della sede legale
			if (combo.equalsIgnoreCase("searchcodeIdComune")){
				// Se l'attivita e fissa, valgono tutte le asl (l'asl di appartenenza la stabilisce il comune dello stabilimento)
				if (tipoAttivita.equals("1"))
					idAsl=-1;
				else{
					// altrimenti controllo se e un'impresa individuale (tipoImpresa=1), in quel caso, l'asl di appartenenza dipende dal comune di residenza
					//if (!tipoImpresa.equals("") && tipoImpresa.equals("1"))
						if (idAslString != null && !("").equals(idAslString))
							idAsl = Integer.parseInt(idAslString);
						else
							idAsl=-1;
				
				}
			}

			
			
			
			ArrayList<ComuniAnagrafica> listaComuni = null ;
//			if (user.getUserRecord().getSuap()!=null)
//				listaComuni = comuni.getComune(db,Integer.parseInt(idProvincia),user.getUserRecord().getSuap().getComune());
//			else
			if (request.getParameter("idProvincia").equals(""))
			{
				idProvincia ="-1";
			}
			 listaComuni = comuni.getComuni(db,Integer.parseInt(idProvincia),nomeStart,inRegione, idAsl,nazione);
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
			String idComune =  request.getParameter("idComune") ;
			if (idComune.equals(""))
			{
				idComune = "-1";
			}
			listIndirizzi.setIdComune(Integer.parseInt(idComune));
			listIndirizzi.setStartComune(nomeStart);
			listIndirizzi.setToponimo(Integer.parseInt(request.getParameter("toponimo")));
			listIndirizzi.buildList(db);
			
			
			List<Indirizzo> lista = new ArrayList<Indirizzo>();
			for (Object c : listIndirizzi)
			{
				boolean trovato = false;
				Indirizzo temp = (Indirizzo)c ;
				
				for (Indirizzo operatore : lista)
				{
					
					if (temp.compareTo(operatore,temp)==0)
					{
						trovato = true ;
						break  ;
						
					}
				
				}
				
				if (trovato==false)
				{
					lista.add(temp);
					
				}
			}
			
					
			json_arr = new JSONArray();
			JSONObject json_obj = null ;
			try {
			for (Indirizzo c : lista)
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
		case 4 :
		{
			String nomeStart = request.getParameter("nome");
			if (nomeStart!=null && nomeStart.equals(""))
				nomeStart = request.getParameter("codFiscaleSoggettoinput");
			
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
		
		case 6: // richiesta partite iva
		{
			json_arr = new JSONArray();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("codice", "-1");
			map.put("descrizione", "Tutti");
			JSONObject json_obj=new JSONObject(map);
			json_arr.put(json_obj);
			
			String nomeStart = request.getParameter("nome");
			ArrayList<String> listaComuni = PopolaCombo.getPartitaIva(nomeStart);
			try {
			for (String c : listaComuni)
			{

					json_obj=new JSONObject();
					json_obj.put("codice", c);
					json_obj.put("descrizione", c);

				 json_arr.put(json_obj);
			}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			 break ;

		}
		
		
		}
		}
		 catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			
			cp.free(db,null);
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
