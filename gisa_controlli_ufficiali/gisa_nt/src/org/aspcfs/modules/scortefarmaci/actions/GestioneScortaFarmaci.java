/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.scortefarmaci.actions;

import it.izs.ws.WsPost;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.allevamenti.base.Organization;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.scortefarmaci.base.ScortaAllevamento;
import org.aspcfs.modules.scortefarmaci.base.VeterinarioAllevamento;
import org.aspcfs.modules.scortefarmaci.util.ScorteUtil;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class GestioneScortaFarmaci extends CFSModule {
	
	
	public String executeCommandViewScortaAllevamento(ActionContext context) throws IndirizzoNotFoundException{
		

		int orgId = -1;
		
		String orgIdString = context.getRequest().getParameter("orgId");
		if (orgIdString==null)
			orgIdString = (String) context.getRequest().getAttribute("orgId");
		
		try {orgId = Integer.parseInt(orgIdString);} catch (Exception e){}
		
		context.getRequest().setAttribute("Response", context.getRequest().getAttribute("Response"));
		context.getRequest().setAttribute("comunicazioneOK", context.getRequest().getAttribute("comunicazioneOK"));
		
		Connection db = null;
		
		try { 
			db = this.getConnection(context);
			
			Organization org = new Organization(db, orgId);
			context.getRequest().setAttribute("OrgDetails", org);
			
			LookupList aslList = new LookupList(db, "lookup_site_id");
			aslList.removeElementByCode(16);
			context.getRequest().setAttribute("AslList", aslList); 

			ScortaAllevamento scorta = new ScortaAllevamento();
			scorta.queryRecordByCodici(db, org.getAccountNumber(), org.getCodiceFiscaleRappresentante());
			
			context.getRequest().setAttribute("Scorta", scorta);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			this.freeConnection(context, db);
		}
		return "ViewScortaAllevamentoOK";
	}
	
public String executeCommandModifyScortaAllevamento(ActionContext context) throws IndirizzoNotFoundException{
	
	if (!hasPermission(context, "scortefarmaci-edit")) {
		return ("PermissionError");
	}

	int orgId = -1;
	
	String orgIdString = context.getRequest().getParameter("orgId");
	if (orgIdString==null)
		orgIdString = (String) context.getRequest().getAttribute("orgId");
	
	try {orgId = Integer.parseInt(orgIdString);} catch (Exception e){}
	
	context.getRequest().setAttribute("Response", context.getRequest().getAttribute("Response"));
	context.getRequest().setAttribute("comunicazioneOK", context.getRequest().getAttribute("comunicazioneOK"));
		
		Connection db = null;
		
		try {
			db = this.getConnection(context);
			
			Organization org = new Organization(db, orgId);
			context.getRequest().setAttribute("OrgDetails", org);
			
			LookupList aslList = new LookupList(db, "lookup_site_id");
			aslList.removeElementByCode(16);
			context.getRequest().setAttribute("AslList", aslList);
			
			ScortaAllevamento scorta = new ScortaAllevamento();
			scorta.queryRecordByCodici(db, org.getAccountNumber(), org.getCodiceFiscaleRappresentante());
			
			if (scorta.getScortaNumAutorizzazione()==null)
				scorta.generaNumAutorizzazione(db, org.getAccountNumber(), org.getCodiceFiscaleRappresentante());
			
			if (scorta.getId()<=0){
				scorta.setCodAzienda(org.getAccountNumber());
				scorta.setIdFiscaleAllevamento(org.getCodiceFiscaleRappresentante());
				scorta.setAslCodice(String.valueOf(org.getSiteId()));
			}
			
			context.getRequest().setAttribute("Scorta", scorta);  
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			this.freeConnection(context, db);
		}
		return "ModifyScortaAllevamentoOK";
	}

public String executeCommandInsertScortaAllevamento(ActionContext context) throws IndirizzoNotFoundException{
	
	if (!hasPermission(context, "scortefarmaci-edit")) {
		return ("PermissionError");
	}

	int id = -1;
	
	String idString = context.getRequest().getParameter("id");
	if (idString==null)
		idString = (String) context.getRequest().getAttribute("id");
	
	try {id = Integer.parseInt(idString);} catch (Exception e){}
	
	Connection db = null;
	
	String response = "";
	boolean invioOK = false;
	
	try {
		db = this.getConnection(context);
		
		int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
		
		ScortaAllevamento scorta = new ScortaAllevamento(db, id);
		scorta.setCodAzienda(context.getRequest().getParameter("codAzienda"));
		scorta.setIdFiscaleAllevamento(context.getRequest().getParameter("idFiscaleAllevamento"));
		scorta.setEnteredBy(getUserId(context));
		scorta.setTipoScortaCodice(context.getRequest().getParameter("tipoScortaCodice"));
		scorta.setScortaNumAutorizzazione(context.getRequest().getParameter("scortaNumAutorizzazione"));
		scorta.setScortaDataInizio(context.getRequest().getParameter("scortaDataInizio"));
		scorta.setAslCodice(context.getRequest().getParameter("aslCodice"));
		scorta.insert(db);
		
		context.getRequest().setAttribute("orgId", orgId);

		System.out.println(" ------ INVIO DATI A BDN id scorta "+scorta.getId()+" -----"); 
		
		WsPost ws = new WsPost();  
		
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_UPSERT_SCORTA_BDN"));
		
		String envelope = "";
		PreparedStatement pst = db.prepareStatement("select * from get_chiamata_ws_upsert_scorta(?)");
		pst.setInt(1, orgId);
		ResultSet rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);
		
		ws.setWsRequest(envelope);
		response= ws.postJSONWithAuthentication(db, getUserId(context), ApplicationProperties.getProperty("USERNAME_INSERT_SCORTA_BDN"), ApplicationProperties.getProperty("PASSWORD_INSERT_SCORTA_BDN"), ApplicationProperties.getProperty("TOKEN_INSERT_SCORTA_BDN"));
		
		String lovscortaId =  ScorteUtil.estraiDaPattern("<lovscortaId>", "</lovscortaId>", response);
		
		if (lovscortaId!=null && !lovscortaId.equals("")) { //INVIO OK
			scorta.setLovscortaId(lovscortaId);
			scorta.updateLovscortaId(db);
			response = "Dato sincronizzato con BDN.";
			invioOK = true;
		}
		else if (response.toLowerCase().contains("esiste già la scorta")){ //INVIO KO. RECUPERO I DATI E CONTROLLO
				response = "In BDN e' gia' presente la scorta farmaci con dati diversi da quelli inviati. Il salvataggio e' stato annullato. Contattare l'Help Desk.";
				scorta.delete(db);
				invioOK = false;
			
		}
		else {
			scorta.delete(db);
			invioOK = false;
		}
		
		context.getRequest().setAttribute("Response", fixResponse(response));
		context.getRequest().setAttribute("comunicazioneOK", String.valueOf(invioOK));

		if (!invioOK){
			context.getRequest().setAttribute("Scorta", scorta);
		}
			
	} catch (Exception e) {
		context.getRequest().setAttribute("Error", e);
		response ="ERRORE INASPETTATO. <BR/><BR/>"+e.toString();
	} finally{
		freeConnection(context, db);
	}
	
	if (invioOK)
		return executeCommandViewScortaAllevamento(context);
	else
		return executeCommandModifyScortaAllevamento(context);

}

public String executeCommandUpdateScortaAllevamento(ActionContext context) throws IndirizzoNotFoundException{
	
	if (!hasPermission(context, "scortefarmaci-edit")) {
		return ("PermissionError");
	}

	int id = -1;
	
	String idString = context.getRequest().getParameter("id");
	if (idString==null)
		idString = (String) context.getRequest().getAttribute("id");
	
	try {id = Integer.parseInt(idString);} catch (Exception e){}
	
	Connection db = null;
	
	String response = "";
	boolean updateOK = false;
	
	try {
		db = this.getConnection(context);
		
		int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
		
		ScortaAllevamento scorta = new ScortaAllevamento(db, id);
		scorta.setCodAzienda(context.getRequest().getParameter("codAzienda"));
		scorta.setIdFiscaleAllevamento(context.getRequest().getParameter("idFiscaleAllevamento"));
		scorta.setEnteredBy(getUserId(context));
		scorta.setTipoScortaCodice(context.getRequest().getParameter("tipoScortaCodice"));
		scorta.setScortaNumAutorizzazione(context.getRequest().getParameter("scortaNumAutorizzazione"));
		scorta.setScortaDataInizio(context.getRequest().getParameter("scortaDataInizio"));
		scorta.setScortaDataFine(context.getRequest().getParameter("scortaDataFine"));
		scorta.setAslCodice(context.getRequest().getParameter("aslCodice"));
		scorta.insert(db);
		
		context.getRequest().setAttribute("orgId", orgId);

		System.out.println(" ------ INVIO DATI A BDN id scorta "+scorta.getId()+" -----"); 
		
		WsPost ws = new WsPost();  
		
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_UPSERT_SCORTA_BDN"));
		
		String envelope = "";
		PreparedStatement pst = db.prepareStatement("select * from get_chiamata_ws_upsert_scorta(?)");
		pst.setInt(1, orgId);
		ResultSet rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);
		
		ws.setWsRequest(envelope);
		response= ws.postJSONWithAuthentication(db, getUserId(context), ApplicationProperties.getProperty("USERNAME_INSERT_SCORTA_BDN"), ApplicationProperties.getProperty("PASSWORD_INSERT_SCORTA_BDN"), ApplicationProperties.getProperty("TOKEN_INSERT_SCORTA_BDN"));
		
		String lovscortaId =  ScorteUtil.estraiDaPattern("<lovscortaId>", "</lovscortaId>", response);
		
		if (lovscortaId!=null && !lovscortaId.equals("")) { //INVIO OK
			response = "Dato sincronizzato con BDN.";
			updateOK = true;
		}
		else {
			scorta.rollback(db);
			updateOK = false;
		}
		
		context.getRequest().setAttribute("Response", fixResponse(response));
		context.getRequest().setAttribute("comunicazioneOK", String.valueOf(updateOK));

		if (!updateOK){
			context.getRequest().setAttribute("Scorta", scorta);
		}
			
	} catch (Exception e) {
		context.getRequest().setAttribute("Error", e);
		response ="ERRORE INASPETTATO. <BR/><BR/>"+e.toString();
	} finally{
		freeConnection(context, db);
	}
	
	if (updateOK)
		return executeCommandViewScortaAllevamento(context);
	else
		return executeCommandModifyScortaAllevamento(context);

}

public String executeCommandInsertVeterinarioAllevamento(ActionContext context) throws IndirizzoNotFoundException{
	
	if (!hasPermission(context, "scortefarmaci-edit")) {
		return ("PermissionError");
	}
	
	String response = "";
	boolean invioOK = false;
	
	Connection db = null;
	
	try {
		db = this.getConnection(context);
		
		int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
		
		VeterinarioAllevamento vet = new VeterinarioAllevamento();
		vet.setCodAzienda(context.getRequest().getParameter("codAzienda"));
		vet.setIdFiscaleAllevamento(context.getRequest().getParameter("idFiscaleAllevamento"));
		vet.setEnteredBy(getUserId(context));
		vet.setTipoScortaCodice(context.getRequest().getParameter("tipoScortaCodice"));
		vet.setVetPersIdFiscale(context.getRequest().getParameter("vetPersIdFiscale"));
		vet.setFlagResponsabile(context.getRequest().getParameter("flagResponsabile"));
		
		if (vet.esisteGia(db)) {
			response ="Su questa scorta farmaci e' gia' presente questo veterinario.";
			context.getRequest().setAttribute("Response", fixResponse(response));
			return executeCommandModifyVeterinarioAllevamento(context);
		}
		
		vet.insert(db); 
		
		context.getRequest().setAttribute("orgId", orgId);

		System.out.println(" ------ INVIO DATI A BDN id veterinario "+vet.getId()+" -----"); 
		
		WsPost ws = new WsPost();  
		
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_INSERT_SCORTA_VETERINARIO_BDN"));
		
		String envelope = "";
		PreparedStatement pst = db.prepareStatement("select * from get_chiamata_ws_invio_scorta_veterinario(?)");
		pst.setInt(1, vet.getId());
		ResultSet rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);
		
		ws.setWsRequest(envelope);
		response= ws.postJSONWithAuthentication(db, getUserId(context), ApplicationProperties.getProperty("USERNAME_INSERT_SCORTA_BDN"), ApplicationProperties.getProperty("PASSWORD_INSERT_SCORTA_BDN"), ApplicationProperties.getProperty("TOKEN_INSERT_SCORTA_BDN"));
		
		String scovetId =  ScorteUtil.estraiDaPattern("<scovetId>", "</scovetId>", response);
		
		if (scovetId!=null && !scovetId.equals("")) { //INVIO OK
			vet.setScovetId(scovetId);
			vet.updateScovetId(db);
			response = "Dato sincronizzato con BDN.";
			invioOK = true;
		}
		else if (response.toLowerCase().contains("esiste record")){ //INVIO KO. RECUPERO I DATI E CONTROLLO
			ws = new WsPost();  
			ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_SEARCH_SCORTA_VETERINARIO_BDN"));
			envelope = "";
			pst = db.prepareStatement("select * from get_chiamata_ws_search_scorta_veterinario(?)");
			pst.setInt(1, vet.getId());
			rs = pst.executeQuery();
			while (rs.next())
				envelope = rs.getString(1);
			
			ws.setWsRequest(envelope);
			response= ws.getWithAuthentication(db, getUserId(context), ApplicationProperties.getProperty("USERNAME_INSERT_SCORTA_BDN"), ApplicationProperties.getProperty("PASSWORD_INSERT_SCORTA_BDN"), ApplicationProperties.getProperty("TOKEN_INSERT_SCORTA_BDN"));
			
			String searchscovetId =  ScorteUtil.estraiDaPattern("<scovetId>", "</scovetId>", response);
			String searchflResponsabile =  ScorteUtil.estraiDaPattern("<flResponsabile>", "</flResponsabile>", response);
			
			if (searchflResponsabile.equals(vet.getFlagResponsabile())){
				vet.setScovetId(searchscovetId);
				vet.updateScovetId(db);
				response = "Dato sincronizzato con BDN.";
				invioOK = true;			
				}
			else {
				response = "In BDN e' gia' presente questo veterinario per la scorta farmaci con dati diversi da quelli inviati. Il salvataggio e' stato annullato. Contattare l'Help Desk.";
				vet.delete(db);
				invioOK = false;
			}
		}
		else {
			vet.delete(db);
			invioOK = false;
		}
		
			context.getRequest().setAttribute("Response", fixResponse(response));
			context.getRequest().setAttribute("comunicazioneOK", String.valueOf(invioOK));

			
	} catch (Exception e) {
		context.getRequest().setAttribute("Error", e);
		response ="ERRORE INASPETTATO. <BR/><BR/>"+e.toString();
	} finally{
		freeConnection(context, db);
	}
	
	if (invioOK)
		return executeCommandViewScortaAllevamento(context);
	else
		return executeCommandModifyVeterinarioAllevamento(context);

}

public String executeCommandModifyVeterinarioAllevamento(ActionContext context) throws IndirizzoNotFoundException{
	
	if (!hasPermission(context, "scortefarmaci-edit")) {
		return ("PermissionError");
	}

	int id = -1;
	
	String idString = context.getRequest().getParameter("id");
	if (idString==null)
		idString = (String) context.getRequest().getAttribute("id");
	
	try {id = Integer.parseInt(idString);} catch (Exception e){}
	
	int orgId = -1;
	
	String orgIdString = context.getRequest().getParameter("orgId");
	if (orgIdString==null)
		orgIdString = (String) context.getRequest().getAttribute("orgId");
	
	try {orgId = Integer.parseInt(orgIdString);} catch (Exception e){}
	
	context.getRequest().setAttribute("orgId", String.valueOf(orgId));  
	
	context.getRequest().setAttribute("Response", context.getRequest().getAttribute("Response"));
	context.getRequest().setAttribute("comunicazioneOK", context.getRequest().getAttribute("comunicazioneOK"));

	Connection db = null;
		
		try {
			db = this.getConnection(context);
			
			Organization org = new Organization(db, orgId);
			context.getRequest().setAttribute("OrgDetails", org);

			ScortaAllevamento scorta = new ScortaAllevamento();
			scorta.queryRecordByCodici(db, org.getAccountNumber(), org.getCodiceFiscaleRappresentante());
			context.getRequest().setAttribute("Scorta", scorta);

			VeterinarioAllevamento veterinario = null;
			
			if (id>0)
				veterinario = new VeterinarioAllevamento(db, id);
			else{
				veterinario = new VeterinarioAllevamento();
				veterinario.setCodAzienda(scorta.getCodAzienda());
				veterinario.setTipoScortaCodice(scorta.getTipoScortaCodice());
				veterinario.setIdFiscaleAllevamento(scorta.getIdFiscaleAllevamento());
				veterinario.setFlagResponsabile("S");
			}
			
			context.getRequest().setAttribute("Veterinario", veterinario);  
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			this.freeConnection(context, db);
		}
		return "ModifyVeterinarioAllevamentoOK";
	}
	
public String executeCommandUpdateVeterinarioAllevamento(ActionContext context) throws IndirizzoNotFoundException{
	
	if (!hasPermission(context, "scortefarmaci-edit")) {
		return ("PermissionError");
	}

	int id = -1;
	
	String idString = context.getRequest().getParameter("id");
	if (idString==null)
		idString = (String) context.getRequest().getAttribute("id");
	
	try {id = Integer.parseInt(idString);} catch (Exception e){}
	
	Connection db = null;
	
	String response = "";
	boolean updateOK = false;
	
	try {
		db = this.getConnection(context);
		
		int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
		
		VeterinarioAllevamento vet = new VeterinarioAllevamento(db, id);
		vet.setCodAzienda(context.getRequest().getParameter("codAzienda"));
		vet.setIdFiscaleAllevamento(context.getRequest().getParameter("idFiscaleAllevamento"));
		vet.setEnteredBy(getUserId(context));
		vet.setTipoScortaCodice(context.getRequest().getParameter("tipoScortaCodice"));
		vet.setVetPersIdFiscale(context.getRequest().getParameter("vetPersIdFiscale"));
		vet.setFlagResponsabile(context.getRequest().getParameter("flagResponsabile"));
		
		if (vet.esisteGia(db)) {
			response ="Su questa scorta farmaci e' gia' presente questo veterinario.";
			context.getRequest().setAttribute("Response", fixResponse(response));
			return executeCommandModifyVeterinarioAllevamento(context);
		}
		
		vet.insert(db);
		
		context.getRequest().setAttribute("orgId", orgId);

		System.out.println(" ------ INVIO DATI A BDN id veterinario "+vet.getId()+" -----"); 
		
		WsPost ws = new WsPost();  
		
		ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_UPDATE_SCORTA_VETERINARIO_BDN"));
		
		String envelope = "";
		PreparedStatement pst = db.prepareStatement("select * from get_chiamata_ws_update_scorta_veterinario(?)");
		pst.setInt(1, vet.getId());
		ResultSet rs = pst.executeQuery();
		while (rs.next())
			envelope = rs.getString(1);
		
		ws.setWsRequest(envelope);
		response= ws.postJSONWithAuthentication(db, getUserId(context), ApplicationProperties.getProperty("USERNAME_INSERT_SCORTA_BDN"), ApplicationProperties.getProperty("PASSWORD_INSERT_SCORTA_BDN"), ApplicationProperties.getProperty("TOKEN_INSERT_SCORTA_BDN"));
		
		String scovetId =  ScorteUtil.estraiDaPattern("<scovetId>", "</scovetId>", response);
		
		if (scovetId!=null && !scovetId.equals("")) { //INVIO OK
			response = "Dato sincronizzato con BDN.";
			updateOK = true;
		}
		else {
			vet.rollback(db); 
			updateOK = false;
		}
		
		context.getRequest().setAttribute("Response", fixResponse(response));
		context.getRequest().setAttribute("comunicazioneOK", String.valueOf(updateOK));

		if (!updateOK){
			context.getRequest().setAttribute("Veterinario", vet);
		}
			
	} catch (Exception e) {
		context.getRequest().setAttribute("Error", e);
		response ="ERRORE INASPETTATO. <BR/><BR/>"+e.toString();
	} finally{
		freeConnection(context, db);
	}
	
	if (updateOK)
		return executeCommandViewScortaAllevamento(context);
	else
		return executeCommandModifyVeterinarioAllevamento(context);

}

private String fixResponse(String response) {
	String output = "";
	if (response == null)
		output = "Errore nell'invio dei dati.";
	else if (response.contains("non gestito"))
		output = "Errore nell'invio dei dati<br/>"+ScorteUtil.estraiDaPattern("Errore non gestito:", "</div>", response);
	else if (response.length()>500)
		output = "Errore nell'invio dei dati.";
	else
		output = response;
	return output;
}
}
