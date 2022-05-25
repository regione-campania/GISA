/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.documentale;

import it.us.web.action.GenericAction;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.properties.Application;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.HashMap;

public class GestisciFile extends GenericAction {

	
	public void can() throws AuthorizationException
	{
	}
	
	@Override
	public void setSegnalibroDocumentazione()
	{
		setSegnalibroDocumentazione("cc");
	}
	
	@Override
	public void canClinicaCessata() throws AuthorizationException
	{
	}

	public void execute() throws Exception
	{
		
				String codDocumento 	 = stringaFromRequest("codDocumento");
				String operazione 	 = stringaFromRequest("operazione");

				String idAutopsiaString 	 = stringaFromRequest("idAutopsia");
				String idAccettazioneString 	 = stringaFromRequest("idAccettazione");
				String idIstopatologicoString 	 = stringaFromRequest("idIstopatologico");
				
				if (idAccettazioneString==null){
					idAutopsiaString = (String) req.getAttribute("idAutopsia");
				}
				if (idAccettazioneString==null){
					idAccettazioneString = (String) req.getAttribute("idAccettazione");
				}
				if (idIstopatologicoString==null){
					idIstopatologicoString = (String) req.getAttribute("idIstopatologico");
				}
				
				int idAutopsia = -1;
				int idAccettazione = -1;
				int idIstopatologico = -1;
				
				
				try {idAutopsia = Integer.parseInt(idAutopsiaString);} catch (Exception e){}
				try {idAccettazione = Integer.parseInt(idAccettazioneString);} catch (Exception e){}
				try {idIstopatologico = Integer.parseInt(idIstopatologicoString);} catch (Exception e){}

				
				int idUser = -1;
				if (utente != null)
					idUser = utente.getId();
				
				HttpURLConnection conn = null;
				String url = "http://" + ((HashMap<String, InetAddress>)req.getServletContext().getAttribute("hosts")).get("srvDOCUMENTALEL").getHostAddress() + ":" + Application.get("DOCUMENTALE_PORT") + "/" + Application.get("DOCUMENTALE_APPLICATION_NAME") + "/GestioneAllegati";

				//STAMPE
				System.out.println("\nUrl generato(chiamata a servlet): "+url.toString());

				URL obj;
				
					obj = new URL(url);
				
				conn = (HttpURLConnection) obj.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("GET");
				StringBuffer requestParams = new StringBuffer();
				requestParams.append("idHeader");
				requestParams.append("=").append(codDocumento);
				requestParams.append("&");
				requestParams.append("idAutopsia");
				requestParams.append("=").append(idAutopsia);
				requestParams.append("&");
				requestParams.append("idAccettazione");
				requestParams.append("=").append(idAccettazione);
				requestParams.append("&");
				requestParams.append("idIstopatologico");
				requestParams.append("=").append(idIstopatologico);
				requestParams.append("&");
				requestParams.append("provenienza");
				requestParams.append("=").append("vam");
				requestParams.append("&");
				requestParams.append("operazione");
				requestParams.append("=").append(operazione);
				requestParams.append("&");
				requestParams.append("idUtente");
				requestParams.append("=").append(idUser);
				

				OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
				wr.write(requestParams.toString());
				wr.flush();
				System.out.println("Conn: "+conn.toString());
				conn.getContentLength();
				
				req.setAttribute("idAutopsia", 			String.valueOf(idAutopsia));
				req.setAttribute("idAccettazione", 		String.valueOf(idAccettazione));
				req.setAttribute("idIstopatologico", 	String.valueOf(idIstopatologico));
				
				String messaggioPost = "File cancellato.";
				req.setAttribute("messaggioPost", messaggioPost);
				
				String readonly 	 = stringaFromRequest("readonly");
				req.setAttribute("readonly", 	readonly);

				goToAction(new ListaAllegati());
				
	}
	


}

