/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.webservicesa_generale.richiesta.suap;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONObject;

public class ConnettoreAGisaGenerale {
	
	 URL urlServ;
	
	ConnettoreAGisaGenerale(URL url) { this.urlServ = url;}
	
	public String[] richiediInserimentoInGisa(HashMap<String,String> parametriScia,ArrayList<String> lineeToIns,int idUser,boolean isOperazioneGlobale) throws MalformedURLException, ParseException
	{
		HashMap<String,String[]> pars = new HashMap<String,String[]>();
		
		pars.put("operazione",new String[]{"inserimentoSciaInGisa"});
		for(String nomePar : parametriScia.keySet())
		{
			//attenzione puo' essere null se non e' valorizzato, ma noi dobbiamo inviarlo come stringa vuota
			String valorePar = parametriScia.get(nomePar) == null ? "" : parametriScia.get(nomePar);
			pars.put(nomePar,new String[]{valorePar});
		}
		
		//metto parametri quali id user, se e' op globale e id linee to insert
		pars.put("idUser",new String[]{idUser+""});
		pars.put("isOperazioneGlobale", new String[]{isOperazioneGlobale+""});
		//per le linee basta che invio i codici attivita
		String[] idsAttivita = lineeToIns.toArray(new String[lineeToIns.size()]);
		pars.put("lineeToIns", idsAttivita);
		
		
		//la risposta attesa idImpresa, idStabPerDocumentale e idNuovoStab
		String risp = sendPostRequest(urlServ,pars);
		JSONObject jR = new JSONObject(risp);
		return new String[]{
				jR.getString("idImpresa")
				,jR.getString("idNuovoStab")
				,jR.getString("idStabPerDocumentale")
				};
		
	}
	
	
	
	public HashMap<String,Boolean> getNomiGruppiFileRichiesti(ArrayList<String> codiciAttivita) throws MalformedURLException, ParseException
	{
		
		
		HashMap<String,Boolean> toRet = new HashMap<String,Boolean>();
		
		if(codiciAttivita.size() == 0)
		{
			return toRet;
		}
		
		HashMap<String,String[]> pars = new HashMap<String,String[]>();
		pars.put("operazione", new String[]{"getNomiGruppiFileRichiesti"}); 
		pars.put("codiciAttivita",codiciAttivita.toArray(new String[codiciAttivita.size()]));
		
		//la risposta attesa e' un unico jsonObject dove ogni entry e' nomeGruppo : boolean
		String risp = sendPostRequest(urlServ,pars);
		JSONObject jR = new JSONObject(risp);
		
		for(Iterator<String> k = jR.keys(); k.hasNext();)
		{
			String nomeGruppo = k.next();
			toRet.put(nomeGruppo.toUpperCase(),true);
		}
		
		return toRet;
	}
	
	public HashMap<String,Boolean> controllaSetLineeAttivitaOnTipoAttivita(int idTipoAttivita,
			ArrayList<String> codiciAttivita) throws MalformedURLException, ParseException
	{
		
		HashMap<String,Boolean> toRet = new HashMap<String,Boolean>();
		if(codiciAttivita.size() == 0)
		{
			return toRet;
		}
		
		HashMap<String,String[]> pars = new HashMap<String,String[]>();
		pars.put("operazione", new String[]{"controllaSetLineeAttivitaOnTipoAttivita"});
		pars.put("idTipoAttivita", new String[]{idTipoAttivita+""});
		pars.put("codiciAttivita",codiciAttivita.toArray(new String[codiciAttivita.size()]));
		
		String risp = sendPostRequest(urlServ,pars);
		//la risposta attesa e' un unico jsonObject dove ogni entry e' codiceAttivita : boolean
		JSONObject jR = new JSONObject(risp);
		for(Iterator<String> k = jR.keys(); k.hasNext();)
		{
			String keyCodAttivita = k.next();
			Boolean isValid =  jR.getBoolean(keyCodAttivita);
			toRet.put(keyCodAttivita,isValid);
		}
		
		
		return toRet;
	}
	
	public String ottieniIdDaDescrizioneUsandoTabellaLookup(String nomeTabella, String nomeCampoId, String nomeCampoDescrizione,String descrizione, boolean tramiteTabellaMapping ) throws ParseException, MalformedURLException
	{
 
		HashMap<String,String[]> pars = new HashMap<String,String[]>();
		pars.put("operazione", new String[]{"ottieniIdDaDescrizioneUsandoTabellaLookup"});
		pars.put("nomeTabella", new String[]{nomeTabella});
		pars.put("nomeCampoId", new String[]{nomeCampoId});
		pars.put("nomeCampoDescrizione", new String[]{nomeCampoDescrizione});
		pars.put("descrizione", new String[]{descrizione});
		pars.put("tramiteTabellaMapping", new String[]{tramiteTabellaMapping+""} );
		
		String risp = sendPostRequest(urlServ,pars);
		//la risposta attesa in json {nomeCampoId : ...}
		JSONObject jR = new JSONObject(risp);
		return jR.getString(nomeCampoId);
		
	}
	
	private String sendPostRequest(URL url, HashMap<String,String[]> pars)
	{
		
		try
		{
			 
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			
			String queryPostString = getPostQueryFromData(pars);
			OutputStream os = con.getOutputStream();
			os.write(queryPostString.getBytes());
			
			InputStream is = con.getInputStream();
			StringBuffer risp = new StringBuffer("");
			byte[] buff = new byte[1024];
			int r = -1;
			while((r = is.read(buff)) > 0)
			{
				risp.append(new String(buff,0,r));
			}
			return risp.toString();

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return "";
	}
	
	
	
	private String getPostQueryFromData(HashMap<String,String[]> pars)
	{
		StringBuffer sb = new StringBuffer("");
		for(String elK : pars.keySet())
		{
			for(String elPar : pars.get(elK)) //poiche' per ogni parametro possono esserci piu' valori
			{
				sb.append(elK+"="+elPar);
				sb.append("&");
			}
		}
		
		return sb.toString().substring(0,sb.length()-1);
	}
}
