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

//import java.util.List;
import it.us.web.action.GenericAction;
//import it.us.web.action.vam.accettazione.Detail;
//import it.us.web.action.vam.accettazione.StampaAccettazione;
//import it.us.web.action.vam.accettazione.StampaCertificatoDecesso;
//import it.us.web.action.vam.cc.StampaDetail;
//import it.us.web.action.vam.cc.autopsie.StampaReferto;
//import it.us.web.action.vam.cc.autopsie.StampaVerbalePrelievo;
//import it.us.web.action.vam.cc.esamiIstopatologici.StampaIstoMultiplo;
//import it.us.web.action.vam.cc.esamiIstopatologici.StampaIstoSingolo;
//import it.us.web.action.vam.richiesteIstopatologici.StampaIstoSingoloLP;
//import it.us.web.bean.remoteBean.Documenti;
//import it.us.web.bean.vam.Accettazione;
//import it.us.web.bean.vam.Animale;
//import it.us.web.bean.vam.CartellaClinica;
//import it.us.web.dao.hibernate.Persistence;
//import it.us.web.dao.hibernate.PersistenceFactory;
import it.us.web.exceptions.AuthorizationException;
import it.us.web.util.Configuratore;
import it.us.web.util.json.JSONArray;
import it.us.web.util.properties.Application;

public class DownloadLastGenerate extends GenericAction {

	
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
		int idAnimale 	 = interoFromRequest("idAnimale");
		int idAccettazione 	 = interoFromRequest("idAccettazione");
		int idEsame 	 = interoFromRequest("idEsame");
		int idCc 	 = interoFromRequest("idCc");
		int id 			= -1;
		String idAccMultipla = null;
		if (cc!=null){
			id = cc.getId();
		if(tipo.equals("stampaDecesso") || tipo.equals("stampaAcc"))
			id = cc.getAccettazione().getId();
		}
		else
			id = idAccettazione;
		if(tipo.equals("stampaAccMultipla"))
			idAccMultipla = stringaFromRequest("idAccMultipla");
			
		HttpURLConnection conn = null;
		//String url = "http://" + InetAddress.getByName(Application.get("DOCUMENTALE_URL")).getHostAddress() + ":" + Application.get("DOCUMENTALE_PORT") + "/" + Application.get("DOCUMENTALE_APPLICATION_NAME") + "/ListaDocs";
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
				
		String codDocumento=""; 
		String idDocumento="";
		BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
		//String inputLine; 
		StringBuffer html = new StringBuffer();
		if (in != null) {
			html.append(in.readLine()); }
			in.close();
			JSONArray jo = new JSONArray(html.toString());
			
			 ArrayList<String> listaDocs = new ArrayList<String>();
			 for(int i = 0 ; i < 1; i++){
				 if (!jo.isNull(i)){
				    String riga = jo.get(i).toString();
				       listaDocs.add(riga);}
				}
	
	if (listaDocs.size()>0){
		String[] split;
		split = listaDocs.get(0).toString().split(";;");
		codDocumento = split[5];
		idDocumento = split[6];
		req.setAttribute("codDocumento", codDocumento);
		req.setAttribute("idDocumento", idDocumento);
		goToAction(new DownloadPdf());
		}
	else {
		// AGGIUNGERE GESTIONE  IN CASO NON ESISTA DOCUMENTO
		String urlRitorno = "";
		
		if(tipo.equals("stampaCc"))
		{
			
			urlRitorno     			+= "vam.cc.StampaDetail.us";
			
		}
		else if(tipo.equals("stampaAcc"))
		{
			urlRitorno     				  += "vam.accettazione.Detail.us?id=" + idAccettazione;
				}
		else if(tipo.equals("stampaAccMultipla"))
		{
			urlRitorno     				  += "vam.accettazioneMultipla.StampaAccettazioni.us?idAccMultipla=" + idAccMultipla;
				}
		else if(tipo.equals("stampaIstoMultiplo"))
		{
			urlRitorno     				  += "vam.cc.esamiIstopatologici.List.us";
		
		}
		else if(tipo.equals("stampaIstoSingolo"))
		{
			urlRitorno     				  += "vam.cc.esamiIstopatologici.Detail.us?id="+idEsame;
			//??
		}
		else if(tipo.equals("stampaIstoSingoloLP"))
		{
			urlRitorno     				  += "vam.cc.esamiIstopatologici.Detail.us?id="+idEsame;
			//??
		
		}
		else if(tipo.equals("stampaDecesso"))
		{
			urlRitorno     			+= "vam.cc.Detail.us?idCartellaClinica=" + idCc;
		}
		else if(tipo.equals("stampaRefertoNecro"))
		{
			urlRitorno     			+= "vam.cc.autopsie.Detail.us?id=" + +idEsame;
			//??
		}
		else if(tipo.equals("stampaVerbalePrelievo"))
		{
			urlRitorno     			+= "vam.cc.autopsie.Detail.us?id=" +idEsame;
			//??
		}
		
		setErrore("Documento non presente");
		redirectTo(urlRitorno, req, res);
	
		}
	
	
		}
}

