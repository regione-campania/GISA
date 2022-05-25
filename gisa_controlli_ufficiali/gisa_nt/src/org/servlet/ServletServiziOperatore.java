/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.servlet;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
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
import org.aspcfs.modules.schedeCentralizzate.base.ListaOperatori;
import org.aspcfs.modules.schedeCentralizzate.base.SchedaCentralizzata;
import org.aspcfs.utils.PopolaCombo;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;
import com.itextpdf.text.pdf.Barcode128;
 
/**
 * Servlet implementation class ServletComuni
 */
public class ServletServiziOperatore extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String cssScreen = "<style type=\"text/css\"> .dettaglioTabella table{ font-family: Arial, Helvetica, sans-serif;	font-size:12px;	color:#000000;} .dettaglioTabella td{	font-family: Arial, Helvetica, sans-serif;	font-size:12px;	color:#000000;} .grey {	 background-color:#EBEBEB;	 border: 1px solid black;	 }.blue {	 background-color:#e6f3ff;	  border: 1px solid black;	  text-transform:uppercase;	}.layout {	 border: 1px solid black;	 text-transform:uppercase;	} </style> ";
	String cssPrint = "<style type=\"text/css\"> .dettaglioTabella table{ font-family: Arial, Helvetica, sans-serif;	font-size:14px;	color:#000000;} .dettaglioTabella td{	font-family: Arial, Helvetica, sans-serif;	font-size:14px;	color:#000000;} .grey {	 background-color:#EBEBEB;	 border: 1px solid black;	 }.blue {	 background-color:#e6f3ff;	  border: 1px solid black;	  text-transform:uppercase;	}.layout {	 border: 1px solid black;	 text-transform:uppercase;	} </style> ";
	
	/** 
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletServiziOperatore() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String ragioneSociale = request.getParameter("name");
		String comune = request.getParameter("city");
		String partitaIva = request.getParameter("iva");
		String targa = request.getParameter("targa");
		String outputType = request.getParameter("output_type");
		
		
		if (outputType==null || outputType.equals("null") || outputType.equals(""))
			outputType = "html";
		
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
		
		gestisciDettaglio(request,response, db, ragioneSociale, comune, partitaIva, targa, outputType);
		
		}
		 catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			
			cp.free(db,null);
		}
		

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
		
		private String generaRigaJson(String row, String id, String nome, String targa, String comune, String partitaIva, String tipologia, String linkDettaglio){
			nome=fixStringa(nome);
			targa=fixStringa(targa);
			comune=fixStringa(comune);
			partitaIva=fixStringa(partitaIva);
		
			String output ="{id:'"+id+"', nome:'"+nome+"', targa:'"+targa+"', comune:'"+comune+"', partitaIva:'"+partitaIva+"', tipologia: '"+tipologia+"', linkDettaglio: '"+linkDettaglio+"'}";
			return output;
		}
		
		
		protected void gestisciDettaglio(HttpServletRequest request, HttpServletResponse response, Connection db, String ragioneSociale, String comune, String partitaIva, String targa, String outputType) throws ServletException, IOException, ParseException {
			// TODO Auto-generated method stub
			
			ListaOperatori lista = new ListaOperatori();
			lista.setRagioneSociale(ragioneSociale);
			lista.setComune(comune);
			lista.setPartitaIva(partitaIva);
			lista.setTarga(targa);
			lista.popolaScheda(db);
			
			//0: id
			//1: ragione sociale
			//2: targa
			//3: comune
			//4: partita iva
			//5: tipologia
			//6: link dettaglio
			
			if(outputType.equals("html"))
			{
		
			String output = "";
			
			output = "<table class=\"dettaglioTabella\" cellpadding=\"5\" style=\"border-collapse: collapse\" width=\"100%\">";
			
			output = output + "<tr><th class=\"blue\" >#</th>";
			output = output + "<th class=\"blue\" >Id</th>";
			output = output + "<th class=\"blue\" >Ragione sociale</th>";
			output = output + "<th class=\"blue\" >Targa</th>";
			output = output + "<th class=\"blue\" >Comune</th>";
			output = output + "<th class=\"blue\" >Partita IVA</th>";
			output = output + "<th class=\"blue\" >Tipologia</th></tr>";
			
			
			for(Map.Entry<String, String[]> elemento : lista.getListaElementi().entrySet()){
						output = output + "<tr><td class=\"grey\" >"+elemento.getKey() +"</td>";
						output = output + "<td class=\"layout\">"+ elemento.getValue()[0] + "</td>";
						output = output + "<td class=\"layout\">"+ elemento.getValue()[1] + "</td>";
						output = output + "<td class=\"layout\">"+ elemento.getValue()[2] + "</td>";
						output = output + "<td class=\"layout\">"+ elemento.getValue()[3] + "</td>";
						output = output + "<td class=\"layout\">"+ elemento.getValue()[4] + "</td>";
						output = output + "<td class=\"layout\">"+ elemento.getValue()[5] + "</td></tr>";
				
			}
			output = output + "</table>";
			output = cssScreen + output;
			response.getWriter().println(output.toString());
			}
			else if (outputType.equals("xml"))
			{
				String output="";
				
				output = output + "<Dettaglio>";
				output= output + "\n";
						;			
					for(Map.Entry<String, String[]> elemento : lista.getListaElementi().entrySet()){
					output = output + "<id>"+elemento.getValue()[0]+"</id>";
					output= output + "\n";
					output = output + "<nome>"+elemento.getValue()[1]+"</nome>";
					output= output + "\n";
					output = output + "<targa>"+elemento.getValue()[2]+"</targa>";
					output= output + "\n";
					output = output + "<comune>"+elemento.getValue()[3]+"</comune>";
					output= output + "\n";
					output = output + "<partitaIva>"+elemento.getValue()[4]+"</partitaIva>";
					output= output + "\n";
					output = output + "<tipologia>"+elemento.getValue()[5]+"</tipologia>";
					output= output + "\n";
					output = output + "<linkDettaglio>"+elemento.getValue()[6]+"</linkDettaglio>";
					output= output + "\n";
				}
					output = output + "</Dettaglio>";
					response.getWriter().println(output.toString());
			}
			else if (outputType.equals("json"))
			{
				JSONArray json_arr = new JSONArray();
				for(Map.Entry<String, String[]> elemento : lista.getListaElementi().entrySet()){
					JSONObject json_obj = null ;
					String output = generaRigaJson(elemento.getKey(), elemento.getValue()[0],  elemento.getValue()[1],  elemento.getValue()[2],  elemento.getValue()[3],  elemento.getValue()[4], elemento.getValue()[5], elemento.getValue()[6]);
					json_obj=new JSONObject(output);
					json_arr.put(json_obj);
				}
				
				response.setContentType("Application/JSON");
				response.getWriter().println(json_arr.toString().replaceAll(",}", "}"));
	            
			}
			
		}
		
		private String fixStringa(String stringa){
			if (stringa==null)
				return "";
			return 	stringa.replaceAll("'", "").replaceAll("\\\\", "");
		}
		
}
