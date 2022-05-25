/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.fromresttogisa;
		
import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.darkhorseventures.database.ConnectionPool;

public class ServletInserimentoSciaInGisa extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		UtilAccessoDb mapper = new UtilAccessoDb();
		Connection db = null;
		String risp = "";
		try
		{
			db =  ((ConnectionPool)getServletContext().getAttribute("ConnectionPool")).dataSource.getConnection();
			
			//controllo che tipo di richiesta mi e' arrivata
			String tipoOp = req.getParameterValues("operazione")[0];
			if(tipoOp != null)
			{
				if(tipoOp.equalsIgnoreCase("getNomiGruppiFileRichiesti"))
				{
					String[] codiciAttivita = req.getParameterValues("codiciAttivita");
					ArrayList<String> alCodiciAtt = new ArrayList<String>();
					for(String el : codiciAttivita)
					{
						alCodiciAtt.add(el);
					}
					
					HashMap<String,Boolean> gruppiDocumenti = mapper.getNomiGruppiFileRichiesti(db, alCodiciAtt);
					risp = getJsonStringFromMapBoolean(gruppiDocumenti);
					
				}
				else if(tipoOp.equalsIgnoreCase("controllaSetLineeAttivitaOnTipoAttivita"))
				{
					int idTipoAttivita = Integer.parseInt(req.getParameterValues("idTipoAttivita")[0]);
					String[] codiciAttivita = req.getParameterValues("codiciAttivita");
					ArrayList<String> alCodiciAtt = new ArrayList<String>();
					for(String el : codiciAttivita)
					{
						alCodiciAtt.add(el);
					}
					 HashMap<String,Boolean> codiciAttivitaValide = mapper.controllaSetLineeAttivitaOnTipoAttivita(db, idTipoAttivita, alCodiciAtt);
					 risp = getJsonStringFromMapBoolean(codiciAttivitaValide);
				}
				else if(tipoOp.equalsIgnoreCase("ottieniIdDaDescrizioneUsandoTabellaLookup"))
				{
					 
					String nomeTabella = req.getParameterValues("nomeTabella")[0];
					String nomeCampoId = req.getParameterValues("nomeCampoId")[0];
					String nomeCampoDescrizione = req.getParameterValues("nomeCampoDescrizione")[0];
					String descrizione = req.getParameterValues("descrizione")[0];
					Boolean tramiteTabellaMapping = Boolean.parseBoolean(req.getParameterValues("tramiteTabellaMapping")[0]);
					
					String idOttenuto = mapper.ottieniIdDaDescrizioneUsandoTabellaLookup(db, nomeTabella, nomeCampoId, nomeCampoDescrizione, descrizione, tramiteTabellaMapping);
					risp = getJsonStringFromSinglePair(nomeCampoId, idOttenuto);
				}
				else if(tipoOp.equalsIgnoreCase("inserimentoSciaInGisa"))
				{
					//estraggo i parametri della scia da inserire
					HashMap<String,Object> parametriSciaComeDaXml = estraiParametriSciaDaRequest(req);
					//results sono [idimpresa,idNuovoStab,idStabPerDocumentale]
					int[] results = mapper.inserisciDatiScia( db,parametriSciaComeDaXml);
					JSONObject jOb = new JSONObject();
					jOb.put("idImpresa",results[0]+"");
					jOb.put("idNuovoStab",results[1]+"");
					jOb.put("idStabPerDocumentale",results[2]+"");
					risp = jOb.toString();
				}
			}
		
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try {db.close();} catch(Exception ex) {}
		}
		
		resp.setContentType("application/json");
		resp.getWriter().println(risp);
		
	*/
	}
/*
	
	private HashMap<String,Object> estraiParametriSciaDaRequest(HttpServletRequest req)
	{
		HashMap<String,Object> tagDaEstrarre = new HashMap<String,Object>();
		//attenzione che i parametri ricevuti come stringa vuota, vanno settati come null per il thread che li dovra' usare
		//e i parametri arrivano come array di 1 elemento
		tagDaEstrarre.put("tipo_attivita", "");
		tagDaEstrarre.put("carattere", "");
		tagDaEstrarre.put("tipo_impresa_societa", "");
		tagDaEstrarre.put("tipo_operazione", "");
		tagDaEstrarre.put("ragione_sociale", "");
		tagDaEstrarre.put("partita_iva", "");
		tagDaEstrarre.put("cf_impresa", "");
		tagDaEstrarre.put("nome_rapp_sede_legale", "");
		tagDaEstrarre.put("cognome_rapp_sede_legale", "");
		tagDaEstrarre.put("sesso_rapp_sede_legale", "");
		tagDaEstrarre.put("cf_rapp_sede_legale", "");
		tagDaEstrarre.put("data_nascita_rapp_sede_legale", "");
		tagDaEstrarre.put("nazione_nascita_rapp_sede_legale", "");
		tagDaEstrarre.put("comune_nascita_rapp_sede_legale", "");
		tagDaEstrarre.put("nazione_residenza_rapp_sede_legale", "");
		tagDaEstrarre.put("provincia_residenza_rapp_sede_legale", "");
		tagDaEstrarre.put("comune_residenza_rapp_sede_legale", "");
		tagDaEstrarre.put("cap_residenza_rapp_sede_legale", "");
		tagDaEstrarre.put("ind_rapp_sede_legale", "");
		tagDaEstrarre.put("ind_sede_legale", "");
		tagDaEstrarre.put("cap_sede_legale", "");
		tagDaEstrarre.put("comune_sede_legale", "");
		tagDaEstrarre.put("prov_sede_legale", "");
		tagDaEstrarre.put("istat_legale", "");
		tagDaEstrarre.put("ind_stab", "");
		tagDaEstrarre.put("cap_stab", "");
		tagDaEstrarre.put("prov_stab", "");
		tagDaEstrarre.put("comune_stab", "");
		tagDaEstrarre.put("istat_operativo", "");
		tagDaEstrarre.put("data_inizio_attivita", "");
		tagDaEstrarre.put("data_fine_attivita", "");
		tagDaEstrarre.put("domicilio_digitale", "");
		tagDaEstrarre.put("latitudine", "");
		tagDaEstrarre.put("longitudine", "");
		tagDaEstrarre.put("partita_iva_variazione", "");
		tagDaEstrarre.put("numero_registrazione_variazione","");
		tagDaEstrarre.put("cessazione_globale","");
		
		
		for(String nomePar : tagDaEstrarre.keySet())
		{
			String[] t = req.getParameterValues(nomePar);
			if(t == null || t[0] == null || t[0].trim().equals(""))
			{ //se e' vuoto in qualche modo, gli setto null 
				tagDaEstrarre.put(nomePar, null);
			}
			else
			{
				tagDaEstrarre.put(nomePar,t[0]);
			}
		}
		
		//estraggo anche info aggiuntive non dell'xml
		//,quali  l'id user e LE LINEE DA INSERIRE
		tagDaEstrarre.put("idUser", req.getParameterValues("idUser")[0]);
		tagDaEstrarre.put("isOperazioneGlobale", req.getParameterValues("isOperazioneGlobale")[0]);
		tagDaEstrarre.put("lineeToIns",req.getParameterValues("lineeToIns"));
		
		
		return tagDaEstrarre;
	}
	
	private String getJsonStringFromSinglePair(String key,String value)
	{
		JSONObject jR = new JSONObject();
		jR.put(key, value);
		return jR.toString();
	}
	
	//tutte le entry diventano chiavi di unico oggetto json
	private String getJsonStringFromMapBoolean(HashMap<String, Boolean> mappa) throws ParseException {
		JSONObject toRet = new JSONObject();
		
		for(String k : mappa.keySet())
		{
			toRet.put(k, mappa.get(k));
		}
		return toRet.toString();
		
	}
	
	
 
	*/
}
