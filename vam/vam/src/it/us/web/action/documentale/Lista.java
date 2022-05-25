/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.documentale;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.us.web.action.GenericAction;
import it.us.web.bean.remoteBean.Documenti;
import it.us.web.bean.vam.Animale;
import it.us.web.bean.vam.CartellaClinica;
import it.us.web.dao.hibernate.Persistence;
import it.us.web.dao.hibernate.PersistenceFactory;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.Configuratore;
import it.us.web.util.json.JSONArray;
import it.us.web.util.properties.Application;

public class Lista extends GenericAction {

	
	public void can() throws AuthorizationException
	{
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}

	public void execute() throws Exception
	{
		String tipo 	 = stringaFromRequest("tipo");
		int idSpecie 	 = interoFromRequest("idSpecie");
		int idAnimale 	 = interoFromRequest("idAnimale");
		String microchip = stringaFromRequest("microchip");
		Integer id 	     = null;
		
		if(tipo.equals("stampaCc"))
		{
			Animale animale 	= cc.getAccettazione().getAnimale();
			idSpecie 			= animale.getLookupSpecie().getId();
			idAnimale 			= animale.getId();
			id					= cc.getId();
		}
		else
		{
			id = -1;
		}

		HttpURLConnection conn = null;
	//	String url = "http://" + InetAddress.getByName(Application.get("DOCUMENTALE_URL")).getHostAddress() + ":" + Application.get("DOCUMENTALE_PORT") + "/" + Application.get("DOCUMENTALE_APPLICATION_NAME") + "/ListaDocs";
		//String url = "http://" + Configuratore.getSrvDOCUMENTALEL().getHostAddress() + ":" + Application.get("DOCUMENTALE_PORT") + "/" + Application.get("DOCUMENTALE_APPLICATION_NAME") + "/ListaDocs";
		String url = "http://" + ((HashMap<String, InetAddress>)req.getServletContext().getAttribute("hosts")).get("srvDOCUMENTALEL").getHostAddress() + ":" + Application.get("DOCUMENTALE_PORT") + "/" + Application.get("DOCUMENTALE_APPLICATION_NAME") + "/ListaDocs";

		
		
		//STAMPE
		System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());

		URL obj;
		
			obj = new URL(url);
		
		conn = (HttpURLConnection) obj.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		StringBuffer requestParams = new StringBuffer();
		requestParams.append("tipoCertificato");
		requestParams.append("=").append(tipo);
		requestParams.append("&");
		requestParams.append("idAnimale_VAM");
		requestParams.append("=").append(idAnimale);
		requestParams.append("&");
		requestParams.append("cc");
		requestParams.append("=").append(id);
		requestParams.append("&");
		requestParams.append("app_name");
		requestParams.append("=").append("vam");
		

		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(requestParams.toString());
		wr.flush();
		System.out.println("Conn: "+conn.toString());
		conn.getContentLength();
				
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
		//String inputLine; 
		StringBuffer html = new StringBuffer();
		if (in != null) {
			html.append(in.readLine()); }
			in.close();
			JSONArray jo = new JSONArray(html.toString());
			
			 ArrayList<String> listaDocs = new ArrayList<String>();
			 for(int i = 0 ; i < jo.length(); i++){
				    String riga = jo.get(i).toString();
				       listaDocs.add(riga);
				}
		
		
		
		req.setAttribute("listaDocumenti", 	listaDocs);
		req.setAttribute("tipo", 			tipo);
		req.setAttribute("idSpecie", 			idSpecie);
		req.setAttribute("idAnimale", 			idAnimale);
		req.setAttribute("microchip", 			microchip);
		req.setAttribute("action", stringaFromRequest("action"));
		
		gotoPage( "popup" , "/jsp/documentale/list.jsp" );
	}
}

