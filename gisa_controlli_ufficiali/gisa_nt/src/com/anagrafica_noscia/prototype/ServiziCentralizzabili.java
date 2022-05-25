/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package com.anagrafica_noscia.prototype;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.suap.campiestesiv2.CampiEstesiV2;
import org.aspcfs.utils.GeoCoder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.anagrafica_noscia.prototype.base_beans.Comune;
import com.anagrafica_noscia.prototype.base_beans.LookupPair;
import com.anagrafica_noscia.prototype.masterlist.MasterList;
import com.darkhorseventures.framework.actions.ActionContext;

public class ServiziCentralizzabili extends CFSModule
{
	
	 
	
	
	/*tutti i comuni campani */
	public String executeCommandGetComuni(ActionContext cont)
	{
		
		JSONObject resp = new JSONObject();
		Connection conn  = null;
		boolean soloCampani = false;
		try
		{
			try { 
				soloCampani = Boolean.parseBoolean(cont.getRequest().getParameter("soloCampani"));
			}catch(Exception ex){}
			
			
			JSONObject toAppend = new JSONObject();
			JSONObject toAppend2 = new JSONObject(); /*corrispondenza idcomune -> nome */
			
			conn = getConnection(cont);
			
			ArrayList<Comune> comuni = null;
			if(soloCampani)
				comuni = Comune.getAllComuniCampani(conn);
			else
				comuni = Comune.getAllComuni(conn);
			
			for(Comune com : comuni)
			{
				JSONObject comune = new JSONObject();	
				comune.put("nome",com.getNome());
				comune.put("id", com.getId());
				comune.put("cod_regione", com.getCodRegione()); 
				
				toAppend.put(com.getNome()+"",comune);
				toAppend2.put(com.getId()+"",com.getNome());
				
			}
			
			 
			
		 
			
			resp.put("comuni", toAppend);
			resp.put("idComuniToNomi", toAppend2);
			cont.getRequest().setAttribute("oggettoRisposta", resp);
			return "SerializzaJSON";
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return "SystemError";
		}
		finally
		{
			freeConnection(cont, conn);
		}
		
		
		
	}
	
	
	/*valori da una lookup specificata nel parametro di request 
	 * PRECONDITION:
	 * parametro di request nomeLookup
	 * parametro di request nomeCampoCode
	 * parametro di request nomeCampoDescription
	 * POSTCONDIZION: 
	 * */
	public String executeCommandGetValuesFromLookup(ActionContext cont)
	{
		
		JSONObject resp = new JSONObject();
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conn = null;
		String nomeLookup = cont.getRequest().getParameter("nomeLookup");
		String nomeCampoCode = cont.getRequest().getParameter("nomeCampoCode");
		String nomeCampoDescription = cont.getRequest().getParameter("nomeCampoDescription");
		
		try
		{
			JSONArray toAppend = new JSONArray();
			conn= getConnection(cont);
			
			ArrayList<LookupPair> pairs = LookupPair.getAll(conn, nomeLookup, nomeCampoCode, nomeCampoDescription,"enabled = true");
			for(LookupPair pair : pairs)
			{
				JSONObject ob = new JSONObject();
				ob.put(nomeCampoCode, pair.getCode());
				ob.put(nomeCampoDescription, pair.getDesc().toUpperCase());
				toAppend.put(ob);
			}
			
			
			 
			resp.put("entries", toAppend);
			cont.getRequest().setAttribute("oggettoRisposta", resp);
			return "SerializzaJSON";
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return "SystemError";
		}
		finally
		{
			
			try{pst.close();} catch(Exception ex){}
			try{rs.close();} catch(Exception ex){}
			freeConnection(cont,conn);
		}
		
	}
	
	
	public String executeCommandCalcolaCoordinate(ActionContext context)
	{
		
		 
		JSONObject resp = new JSONObject();
		String viewName="SerializzaJSON";
		HttpServletRequest req = context.getRequest();
		GeoCoder geocod = new GeoCoder();
		
		try
		{
			 String indirizzo = req.getParameter("indirizzo");
			 String desccomune = req.getParameter("desccomune");
			 String descprovincia = req.getParameter("descprovincia");
			 /*da id comune a desc comune */
			
			 
			 
			 String[] coord = geocod.getCoords(indirizzo, desccomune, descprovincia) ;
			 
			 if(!coord[2].equalsIgnoreCase("v") && !coord[2].equalsIgnoreCase("a"))
			 {
				 throw new Exception("Il geocoder ha ritornato valore di errore risoluzione indirizzo (esito "+coord[2]+")");
			 }
			 
			 resp.put("lng", coord[0]);
			 resp.put("lat", coord[1]);
			 resp.put("esito_geo", coord[2]);
			 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			viewName = "SystemError";
		}
		finally
		{
			 
		}
		
		req.setAttribute("oggettoRisposta",resp);
		
		return viewName;
	
		
	}
	
	
	public String executeCommandCalcolaCf(ActionContext context)
	{
		
		StringBuffer urlWebServiceEsternoCalcolo = new StringBuffer("http://webservices.dotnethell.it/codicefiscale.asmx/CalcolaCodiceFiscale");
		JSONObject resp = new JSONObject();
		String viewName="SerializzaJSON";
		HttpURLConnection conn = null;
		URL url = null;
		String nome = null;
		String cognome = null;
		String sex = null;
		String desccomune = null;
		String descNascita = null;
		HttpServletRequest req = context.getRequest();
		 
		
		try
		{
			nome = req.getParameter("nome");
			cognome = req.getParameter("cognome");
			sex = req.getParameter("sex");
			desccomune = req.getParameter("desccomune");
			descNascita = req.getParameter("data_nascita");
			if(		nome == null || nome.trim().length() == 0 ||
					cognome == null || cognome.trim().length() == 0 ||
					sex == null || sex.trim().length() == 0 ||
					desccomune == null || desccomune.trim().length() == 0 ||
					descNascita == null || descNascita.trim().length() == 0 
					
			  )
			{
				
				throw new Exception("Il servizio centralizzato (ServiziCentralizzabili.java) per il calcolo CF e' stato chiamato senza passare tutti i parametri");
			}
			else
			{
				if(!descNascita.matches("^[0-9]{2}/[0-9]{2}/[0-9]{4}$"))
				{
					throw new Exception("Il servizio centralizzato (ServiziCentralizzabili.java) per il calcolo CF e' stato chiamato con un formato data ("+descNascita+") non valido.");
				}
				 
				
				urlWebServiceEsternoCalcolo.append("?nome="+URLEncoder.encode(nome,"UTF-8")+"&");
				urlWebServiceEsternoCalcolo.append("cognome="+URLEncoder.encode(cognome,"UTF-8")+"&");
				urlWebServiceEsternoCalcolo.append("comunenascita="+URLEncoder.encode(desccomune,"UTF-8")+"&");
				urlWebServiceEsternoCalcolo.append("datanascita="+URLEncoder.encode(descNascita,"UTF-8")+"&");
				urlWebServiceEsternoCalcolo.append("sesso="+URLEncoder.encode(sex,"UTF-8"));
				
				url = new URL(urlWebServiceEsternoCalcolo.toString());
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				
				int r = -1; 
				StringBuffer sb = new StringBuffer();
				byte[] buff = new byte[1024];
				InputStream is = conn.getInputStream();
				while((r = is.read(buff))>0)
				{
					sb.append(new String(buff,0,r));
				}
				System.out.println(sb.toString());
				
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				ByteArrayInputStream bais = new ByteArrayInputStream(sb.toString().getBytes());
				Document doc = db.parse( bais);
				
				Node n = doc.getElementsByTagName("string").item(0);
				String cf = n.getTextContent();
				
				if(cf.contains("inesistente"))
				{
					throw new Exception("Il servizio esterno di calcolo del CF non riesce a risolvere il CF!");
				}
				
				resp.put("cf",cf);
				
				
			}
			 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			viewName = "SystemError";
		}
		finally
		{
			 
		}
		
		req.setAttribute("oggettoRisposta",resp);
		
		return viewName;
	}
	
	
	
	
	
	public String executeCommandOttieniMasterList(ActionContext context)
	{
		
		 
		JSONObject resp = new JSONObject();
		String viewName="SerializzaJSON";
		HttpServletRequest req = context.getRequest();
		Connection db = null;
	 
		
		/*i campi estesi associati a ciascuna linea mandali vuoti, 
		 * verranno popolati quando si sceglie una linea direttamente 
		 */
		
		try
		{
			db = getConnection(context);
			/*indica che tipo di ml stiamo ricercando */
			String descFlussoOrigine = req.getParameter("descflussoorigine");
			JSONObject masterListAsJson = MasterList.getMasterListAsJson(descFlussoOrigine,db);
			resp.put("masterList", masterListAsJson);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			viewName = "SystemError";
		}
		finally
		{
			freeConnection(context,db);
		}
		
		 
		
		req.setAttribute("oggettoRisposta",resp);
		
		return viewName;
	}
	
	
	
	
	/*questa gia' usa la nuova gestione (v2 di stabilimentosintesisaction) */
	public String executeCommandOttieniCampiEstesi(ActionContext context)
	{
		
		 
		JSONObject resp = new JSONObject();
		String viewName="SerializzaJSON";
		HttpServletRequest req = context.getRequest();
		Connection db = null;
		 try
		 {
			 db = getConnection(context);
			 Integer idLinea = Integer.parseInt(context.getRequest().getParameter("idLineaML"));
			 Integer idRelazione = Integer.parseInt(context.getRequest().getParameter("idRel"));
			 JSONObject campi = CampiEstesiV2.getCampiEstesi(idLinea,  idRelazione,db);
			 resp.put("campiEstesi", campi);
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
		 }
		 finally
		 {
			 this.freeConnection(context, db);
		 }
		 

		 
		
		req.setAttribute("oggettoRisposta",resp);
		
		return viewName;
	
		
	}
	
	
}
