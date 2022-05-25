/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestionecu.actions;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.gestionecu.base.Anagrafica;
import org.aspcfs.modules.gestionecu.base.Asl;
import org.aspcfs.modules.gestionecu.base.Controllo;
import org.aspcfs.modules.gestionecu.base.Linea;
import org.aspcfs.modules.gestionecu.base.Motivo;
import org.aspcfs.modules.gestionecu.base.Oggetto;
import org.aspcfs.modules.gestionecu.base.Qualifica;
import org.aspcfs.modules.gestionecu.base.Tecnica;
import org.aspcfs.modules.gestionecu.util.GestioneCUUtil;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.oia.base.OiaNodo;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.utils.web.LookupList;
import org.json.JSONArray;
import org.json.JSONObject;

import com.darkhorseventures.framework.actions.ActionContext;

public class GestioneCU extends CFSModule{

	public String executeCommandAdd(ActionContext context) throws NumberFormatException, IndirizzoNotFoundException, IllegalAccessException, InstantiationException
	{

		if (!hasPermission(context, "gestionenuovacu-add")) {
			return ("PermissionError");
		}

		JSONObject jsonControllo = new JSONObject();

		try {jsonControllo = new JSONObject(context.getRequest().getParameter("jsonControllo"));} catch (Exception e){}
		if (jsonControllo == null)
			try {jsonControllo = new JSONObject((String) context.getRequest().getAttribute("jsonControllo"));} catch (Exception e){}	
		
		Connection db = null;

		try 
		{
			db = this.getConnection(context);

			if (jsonControllo.has("Tecnica")) {
				jsonControllo.remove("Tecnica");
			}

			if (!jsonControllo.has("Anagrafica")){
				int riferimentoId = -1;
				String riferimentoIdNomeTab = null;

				try {riferimentoId = Integer.parseInt(context.getRequest().getParameter("riferimentoId"));} catch (Exception e) {}
				if (riferimentoId == -1)
					try {riferimentoId = Integer.parseInt((String) context.getRequest().getAttribute("riferimentoId"));} catch (Exception e) {}

				try {riferimentoIdNomeTab = context.getRequest().getParameter("riferimentoIdNomeTab");} catch (Exception e) {}
				if (riferimentoIdNomeTab == null)
					try {riferimentoIdNomeTab = (String) context.getRequest().getAttribute("riferimentoIdNomeTab");} catch (Exception e) {}

				Anagrafica anag = new Anagrafica(db, riferimentoId, riferimentoIdNomeTab);

				JSONObject jsonAnagrafica = new JSONObject();
				jsonAnagrafica.put("riferimentoId", riferimentoId);
				jsonAnagrafica.put("riferimentoIdNomeTab", riferimentoIdNomeTab);
				jsonAnagrafica.put("ragioneSociale", anag.getRagioneSociale());
				jsonAnagrafica.put("partitaIva", anag.getPartitaIva());
				jsonControllo.put("Anagrafica", jsonAnagrafica);

				JSONObject jsonUtente = new JSONObject();
				jsonUtente.put("userId", getUserId(context));
				jsonControllo.put("Utente", jsonUtente);

			}

			int riferimentoId = Integer.parseInt(((JSONObject) jsonControllo.get("Anagrafica")).get("riferimentoId").toString());
			String riferimentoIdNomeTab = ((JSONObject) jsonControllo.get("Anagrafica")).get("riferimentoIdNomeTab").toString();

			ArrayList<Tecnica> listaTecniche = Tecnica.buildLista(db, riferimentoId, riferimentoIdNomeTab);
			context.getRequest().setAttribute("ListaTecniche", listaTecniche);

		} 

		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("jsonControllo", jsonControllo);

		return "AddOK";
	}

	public String executeCommandAddLinea(ActionContext context) throws NumberFormatException, IndirizzoNotFoundException, IllegalAccessException, InstantiationException, ParseException
	{

		if (!hasPermission(context, "gestionenuovacu-add")) {
			return ("PermissionError");
		}

		JSONObject jsonControllo = new JSONObject();

		try {jsonControllo = new JSONObject(context.getRequest().getParameter("jsonControllo"));} catch (Exception e){}
		if (jsonControllo == null)
			try {jsonControllo = new JSONObject((String) context.getRequest().getAttribute("jsonControllo"));} catch (Exception e){}

		if (jsonControllo.has("Linee")) {
			jsonControllo.remove("Linee");
		}

		if (!jsonControllo.has("Tecnica")) {
			int tecnicaId = -1;
			String tecnicaNome = "";

			try {tecnicaId = Integer.parseInt(context.getRequest().getParameter("tecnicaId"));} catch (Exception e) {}
			if (tecnicaId == -1)
				try {tecnicaId = Integer.parseInt((String) context.getRequest().getAttribute("tecnicaId"));} catch (Exception e) {}

			try {tecnicaNome = context.getRequest().getParameter("tecnicaNome_"+tecnicaId);} catch (Exception e) {}

			JSONObject jsonTecnica = new JSONObject();
			jsonTecnica.put("id", tecnicaId);
			jsonTecnica.put("nome", tecnicaNome);
			jsonControllo.put("Tecnica", jsonTecnica);

		}
		Connection db = null;

		try 
		{
			db = this.getConnection(context);

			int riferimentoId = Integer.parseInt(((JSONObject) jsonControllo.get("Anagrafica")).get("riferimentoId").toString());
			String riferimentoIdNomeTab = ((JSONObject) jsonControllo.get("Anagrafica")).get("riferimentoIdNomeTab").toString();
			int tecnicaId = Integer.parseInt(((JSONObject) jsonControllo.get("Tecnica")).get("id").toString());

			ArrayList<Linea> listaLinee = Linea.buildLista(db, riferimentoId, riferimentoIdNomeTab, tecnicaId);
			context.getRequest().setAttribute("ListaLinee", listaLinee);

		} 

		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("jsonControllo", jsonControllo);

		return "AddLineaOK";
	}

	public String executeCommandAddDataOggetto(ActionContext context) throws NumberFormatException, IndirizzoNotFoundException, IllegalAccessException, InstantiationException, ParseException
	{

		if (!hasPermission(context, "gestionenuovacu-add")) {
			return ("PermissionError");
		}

		JSONObject jsonControllo = new JSONObject();

		try {jsonControllo = new JSONObject(context.getRequest().getParameter("jsonControllo"));} catch (Exception e){}
		if (jsonControllo == null)
			try {jsonControllo = new JSONObject((String) context.getRequest().getAttribute("jsonControllo"));} catch (Exception e){}

		if (jsonControllo.has("Dati")) {
			jsonControllo.remove("Dati");
		}
		if (jsonControllo.has("Asl")) {
			jsonControllo.remove("Asl");
		}
		if (jsonControllo.has("Oggetti")) {
			jsonControllo.remove("Oggetti");
		}

		if (!jsonControllo.has("Linee")) {
			String[] lineaIds = null;
			try {lineaIds = context.getRequest().getParameterValues("lineaId");} catch (Exception e) {}

			JSONArray jsonLinee = new JSONArray();

			if (lineaIds != null) {
				for (int i = 0; i<lineaIds.length;i++){
					JSONObject jsonLinea = new JSONObject();
					jsonLinea.put("id", lineaIds[i]);
					jsonLinea.put("nome", context.getRequest().getParameter("lineaNome_"+lineaIds[i]));
					jsonLinea.put("codice", context.getRequest().getParameter("lineaCodice_"+lineaIds[i]));
					jsonLinee.put(jsonLinea);
				}
			}
			jsonControllo.put("Linee", jsonLinee);

		}

		Connection db = null;

		try 
		{
			db = this.getConnection(context);


			ArrayList<Oggetto> listaOggetti = Oggetto.buildLista(db);
			context.getRequest().setAttribute("ListaOggetti", listaOggetti);

			LookupList listaSpecieTrasportata = new LookupList(db, "lookup_specie_trasportata");
			listaSpecieTrasportata.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("ListaSpecieTrasportata", listaSpecieTrasportata);
			
			int riferimentoId = Integer.parseInt(((JSONObject) jsonControllo.get("Anagrafica")).get("riferimentoId").toString());
			String riferimentoIdNomeTab = ((JSONObject) jsonControllo.get("Anagrafica")).get("riferimentoIdNomeTab").toString();
			int idUtente = getUserId(context);

			ArrayList<Asl> listaAsl = Asl.buildLista(db, riferimentoId, riferimentoIdNomeTab, idUtente);
			context.getRequest().setAttribute("ListaAsl", listaAsl);

		} 

		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("jsonControllo", jsonControllo);

		return "AddDataOggettoOK";
	}

	public String executeCommandAddMotivo(ActionContext context) throws NumberFormatException, IndirizzoNotFoundException, IllegalAccessException, InstantiationException, ParseException
	{

		if (!hasPermission(context, "gestionenuovacu-add")) {
			return ("PermissionError");
		}

		JSONObject jsonControllo = new JSONObject();

		try {jsonControllo = new JSONObject(context.getRequest().getParameter("jsonControllo"));} catch (Exception e){}
		if (jsonControllo == null)
			try {jsonControllo = new JSONObject((String) context.getRequest().getAttribute("jsonControllo"));} catch (Exception e){}

		if (jsonControllo.has("Motivi")) {
			jsonControllo.remove("Motivi");
		}

		if (!jsonControllo.has("Dati")) {
			String[] oggettoIds = null;
			String dataInizioControllo = null;
			String dataFineControllo = null;
			String noteControllo = null;
			int idAslControllo = -1;
			String nomeAslControllo = null;
			
			try {oggettoIds = context.getRequest().getParameterValues("oggettoId");} catch (Exception e) {}

			try {dataInizioControllo = context.getRequest().getParameter("dataInizioControllo");} catch (Exception e) {}
			try {dataFineControllo = context.getRequest().getParameter("dataFineControllo");} catch (Exception e) {}
			try {noteControllo = context.getRequest().getParameter("noteControllo");} catch (Exception e) {}
			
			try {idAslControllo = Integer.parseInt(context.getRequest().getParameter("aslId"));} catch (Exception e) {}
			try {nomeAslControllo = context.getRequest().getParameter("aslNome_"+idAslControllo);} catch (Exception e) {}

			JSONObject jsonDati = new JSONObject();
			jsonDati.put("dataInizio", dataInizioControllo);
			jsonDati.put("dataFine", dataFineControllo);
			jsonDati.put("note", noteControllo);  
			jsonControllo.put("Dati", jsonDati);
			
			JSONObject jsonAsl = new JSONObject();
			jsonAsl.put("id", idAslControllo);
			jsonAsl.put("nome", nomeAslControllo);
			jsonControllo.put("Asl", jsonAsl);

			JSONArray jsonOggetti = new JSONArray();
			if (oggettoIds!=null) {
				for (int i = 0; i<oggettoIds.length;i++){
					JSONObject jsonOggetto = new JSONObject();
					jsonOggetto.put("id", oggettoIds[i]);
					jsonOggetto.put("nome", context.getRequest().getParameter("oggettoNome_"+oggettoIds[i]));
					jsonOggetto.put("codiceEvento", context.getRequest().getParameter("oggettoCodiceEvento_"+oggettoIds[i]));

					//Gestione campi estesi oggetto

					JSONArray jsonOggettiCampi = new JSONArray();
					if (jsonOggetto.has("codiceEvento")) {
						Map<String, String[]> map = context.getRequest().getParameterMap();
						Set set = map.entrySet();
						Iterator it = set.iterator();
						while (it.hasNext()) {
							Map.Entry<String, String[]> entry = (Entry<String, String[]>) it.next();
							String paramName = entry.getKey();
							if (paramName.startsWith("_"+jsonOggetto.get("codiceEvento")+"_")){
								String[] paramValues = entry.getValue();
								String paramValue = paramValues[0];
								JSONObject campo = new JSONObject();
								campo.put(paramName.replace("_"+jsonOggetto.get("codiceEvento")+"_", ""), paramValue);
								jsonOggettiCampi.put(campo);
							}
						}
					}
					jsonOggetto.put("CampiEstesi", jsonOggettiCampi);
					jsonOggetti.put(jsonOggetto);
				}
			}
			jsonControllo.put("Oggetti", jsonOggetti);

		}

		Connection db = null;

		try 
		{
			db = this.getConnection(context);

			//Aggiunta motivi controllo

			Calendar calCorrente = GregorianCalendar.getInstance();
			Date dataCorrente = new Date(System.currentTimeMillis());
			int tolleranzaGiorni = Integer.parseInt(org.aspcfs.modules.vigilanza.blocchicu.ApplicationProperties.getProperty("TOLLERANZA_MODIFICA_CU"));
			dataCorrente.setDate(dataCorrente.getDate()- tolleranzaGiorni);
			calCorrente.setTime(new Timestamp(dataCorrente.getTime()));
			int annoCorrente = calCorrente.get(Calendar.YEAR);

			int riferimentoId = Integer.parseInt(((JSONObject) jsonControllo.get("Anagrafica")).get("riferimentoId").toString());
			String riferimentoIdNomeTab = ((JSONObject) jsonControllo.get("Anagrafica")).get("riferimentoIdNomeTab").toString();
			int tecnicaId = Integer.parseInt(((JSONObject) jsonControllo.get("Tecnica")).get("id").toString());

			ArrayList<Motivo> listaMotivi = new ArrayList<Motivo>();
			listaMotivi = GestioneCUUtil.loadListaMotivi(db, riferimentoId, riferimentoIdNomeTab, annoCorrente, tecnicaId);
			context.getRequest().setAttribute("ListaMotivi", listaMotivi);

		} 

		catch (Exception e)  
		{
			e.printStackTrace();
		}
		finally 
		{
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("jsonControllo", jsonControllo);

		return "AddMotivoOK";
	}

	public String executeCommandAddNucleo(ActionContext context) throws NumberFormatException, IndirizzoNotFoundException, IllegalAccessException, InstantiationException, ParseException
	{

		if (!hasPermission(context, "gestionenuovacu-add")) {
			return ("PermissionError");
		}

		JSONObject jsonControllo = new JSONObject();

		try {jsonControllo = new JSONObject(context.getRequest().getParameter("jsonControllo"));} catch (Exception e){}
		if (jsonControllo == null)
			try {jsonControllo = new JSONObject((String) context.getRequest().getAttribute("jsonControllo"));} catch (Exception e){}

		if (jsonControllo.has("Nucleo")) {
			jsonControllo.remove("Nucleo");
		}

		if (!jsonControllo.has("Motivi")) {
			String[] motivoIds = null;

			try {motivoIds = context.getRequest().getParameterValues("motivoId");} catch (Exception e) {}

			JSONArray jsonMotivi= new JSONArray();

			if (motivoIds != null) {
				for (int i = 0; i<motivoIds.length;i++){

					JSONObject jsonMotivo = new JSONObject();
					int idAttivita = Integer.parseInt(motivoIds[i].split("_")[0]);
					int idPianoMonitoraggio = Integer.parseInt(motivoIds[i].split("_")[1]);
					jsonMotivo.put("idAttivita", idAttivita);
					jsonMotivo.put("idPianoMonitoraggio", idPianoMonitoraggio);
					jsonMotivo.put("nome", context.getRequest().getParameter("motivoNome_"+motivoIds[i]).replaceAll("'", ""));
					jsonMotivo.put("codiceEvento", context.getRequest().getParameter("motivoCodiceEvento_"+motivoIds[i]));

					JSONObject jsonPerContoDi = new JSONObject();
					jsonPerContoDi.put("id", context.getRequest().getParameter("motivoPerContoDiId_"+motivoIds[i]));
					jsonPerContoDi.put("nome", context.getRequest().getParameter("motivoPerContoDiNome_"+motivoIds[i]).replaceAll("'", ""));
					jsonMotivo.put("PerContoDi",  jsonPerContoDi);

					//Gestione campi estesi motivo
					JSONArray jsonMotiviCampi = new JSONArray();
					if (jsonMotivo.has("codiceEvento")){
						Map<String, String[]> map = context.getRequest().getParameterMap();
						Set set = map.entrySet();
						Iterator it = set.iterator();
						while (it.hasNext()) {
							Map.Entry<String, String[]> entry = (Entry<String, String[]>) it.next();
							String paramName = entry.getKey();
							if (paramName.startsWith("_"+jsonMotivo.get("codiceEvento")+"_")){
								String[] paramValues = entry.getValue();
								String paramValue = paramValues[0];
								JSONObject campo = new JSONObject();
								campo.put(paramName.replace("_"+jsonMotivo.get("codiceEvento")+"_", ""), paramValue);
								jsonMotiviCampi.put(campo);
							}
						}
					}
					jsonMotivo.put("CampiEstesi", jsonMotiviCampi);
					jsonMotivi.put(jsonMotivo);
				} }
			jsonControllo.put("Motivi", jsonMotivi);

		}

		Connection db = null;

		try 
		{
			db = this.getConnection(context);

			ArrayList<Qualifica> listaQualifiche =  GestioneCUUtil.loadQualifiche(db, getUserId(context));
			context.getRequest().setAttribute("ListaQualifiche", listaQualifiche);  

		} 

		catch (Exception e)  
		{
			e.printStackTrace();
		}
		finally 
		{
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("jsonControllo", jsonControllo);

		return "AddNucleoOK";
	}

	public String executeCommandAddRiepilogo(ActionContext context) throws NumberFormatException, IndirizzoNotFoundException, IllegalAccessException, InstantiationException, ParseException
	{

		if (!hasPermission(context, "gestionenuovacu-add")) {
			return ("PermissionError");
		}

		JSONObject jsonControllo = new JSONObject();

		try {jsonControllo = new JSONObject(context.getRequest().getParameter("jsonControllo"));} catch (Exception e){}
		if (jsonControllo == null)
			try {jsonControllo = new JSONObject((String) context.getRequest().getAttribute("jsonControllo"));} catch (Exception e){}

		if (!jsonControllo.has("Nucleo")) {
			String[] componentiIds = null;

			try {componentiIds = context.getRequest().getParameterValues("componenteId");} catch (Exception e) {}

			JSONArray jsonComponenti= new JSONArray();

			if (componentiIds != null) {
				for (int i = 0; i<componentiIds.length;i++){

					JSONObject jsonComponente = new JSONObject();
					int idComponente = Integer.parseInt(componentiIds[i]);
					jsonComponente.put("id", idComponente);
					jsonComponente.put("nominativo", context.getRequest().getParameter("componenteNome_"+componentiIds[i]).replaceAll("'", ""));

					JSONObject jsonQualifica = new JSONObject();
					jsonQualifica.put("id", context.getRequest().getParameter("componenteQualificaId_"+componentiIds[i]));
					jsonQualifica.put("nome", context.getRequest().getParameter("componenteQualificaNome_"+componentiIds[i]).replaceAll("'", ""));
					jsonComponente.put("Qualifica", jsonQualifica);

					JSONObject jsonStruttura = new JSONObject();
					jsonStruttura.put("id", context.getRequest().getParameter("componenteStrutturaId_"+componentiIds[i]));
					jsonStruttura.put("nome", context.getRequest().getParameter("componenteStrutturaNome_"+componentiIds[i]).replaceAll("'", ""));
					jsonComponente.put("Struttura", jsonStruttura);

					jsonComponenti.put(jsonComponente);
				}
			}
			jsonControllo.put("Nucleo", jsonComponenti);

		}

		context.getRequest().setAttribute("jsonControllo", jsonControllo);

		return "AddRiepilogoOK";
	}

	public String executeCommandInsert(ActionContext context) throws NumberFormatException, IndirizzoNotFoundException, IllegalAccessException, InstantiationException, ParseException
	{

		if (!hasPermission(context, "gestionenuovacu-add")) {
			return ("PermissionError");
		}

		JSONObject jsonControllo = new JSONObject();

		try {
			jsonControllo = new JSONObject(context.getRequest().getParameter("jsonControllo"));
		} catch (Exception e){}

		Connection db = null;

		try 
		{
			db = this.getConnection(context);
			Controllo cu = new Controllo();
			cu.insert(db, jsonControllo);
			context.getRequest().setAttribute("CU",  cu);

		} 

		catch (Exception e)  
		{
			e.printStackTrace();
		}
		finally 
		{
			this.freeConnection(context, db);
		}
		
		context.getRequest().setAttribute("jsonControllo",  jsonControllo);
		return "InsertOK";
	}
	
	public String executeCommandPrepareSimulazione(ActionContext context) throws NumberFormatException, IndirizzoNotFoundException, IllegalAccessException, InstantiationException, ParseException
	{

		if (!hasPermission(context, "gestionenuovacu-add")) {
			return ("PermissionError");
		}
		
		return "SimulazioneOK";
	}
	
	public String executeCommandSimulazione(ActionContext context) throws NumberFormatException, IndirizzoNotFoundException, IllegalAccessException, InstantiationException, ParseException
	{

		if (!hasPermission(context, "gestionenuovacu-add")) {
			return ("PermissionError");
		}

		JSONObject jsonControllo = new JSONObject();

		try {
			jsonControllo = new JSONObject(context.getRequest().getParameter("jsonControllo"));
		} catch (Exception e){}

	
		if (jsonControllo.has("Nucleo"))
			return executeCommandAddRiepilogo(context);
		if (jsonControllo.has("Motivi"))
			return executeCommandAddNucleo(context);
		if (jsonControllo.has("Dati"))
			return executeCommandAddMotivo(context);
		if (jsonControllo.has("Linee"))
			return executeCommandAddDataOggetto(context);
		if (jsonControllo.has("Tecnica"))
			return executeCommandAddLinea(context);
		if (jsonControllo.has("Anagrafica"))
			return executeCommandAdd(context);
		
		return "";
	}
	
	public String executeCommandView(ActionContext context) throws NumberFormatException, IndirizzoNotFoundException, IllegalAccessException, InstantiationException
	{

		if (!hasPermission(context, "gestionenuovacu-add")) {
			return ("PermissionError");
		}
	
		JSONObject jsonControllo = new JSONObject();
		
		Connection db = null;

		try 
		{
			db = this.getConnection(context);

			
				int idControllo = -1;
				
				try {idControllo = Integer.parseInt(context.getRequest().getParameter("idControllo"));} catch (Exception e) {}
				if (idControllo == -1)
					try {idControllo = Integer.parseInt((String) context.getRequest().getAttribute("idControllo"));} catch (Exception e) {}

				jsonControllo = Controllo.getJson(db, idControllo);

		} 

		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			this.freeConnection(context, db);
		}

		context.getRequest().setAttribute("jsonControllo", jsonControllo);

		return "ViewOK";
	}
}
