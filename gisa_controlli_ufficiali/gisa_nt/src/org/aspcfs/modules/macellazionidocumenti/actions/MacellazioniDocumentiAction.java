/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazionidocumenti.actions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.macellazionidocumenti.base.Articolo17;
import org.aspcfs.modules.macellazionidocumenti.base.ModelloGenerico;
import org.aspcfs.modules.macellazionidocumenti.base.RegistroMacellazioni;
import org.aspcfs.modules.macellazioninew.base.GenericBean;
import org.aspcfs.modules.macellazioninew.base.Partita;
import org.aspcfs.modules.macellazioninew.utils.ConfigTipo;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.sintesis.base.SintesisStabilimento;
import org.aspcfs.modules.stabilimenti.base.Organization;
import org.aspcfs.modules.stabilimenti.base.OrganizationAddress;
import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class MacellazioniDocumentiAction extends CFSModule {
	
	ConfigTipo configTipo = null;
	
	
	public String executeCommandArt17(ActionContext context) throws SQLException, IOException{
		
		Connection db = null;
		getConfigTipo(context);
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		String ip = context.getIpAddress();
		int user_id = user.getUserRecord().getId();
		String idSeduta = context.getRequest().getParameter("idSeduta");
		String idPartita = context.getRequest().getParameter("idPartita");
		try {
		 db = this.getConnection(context);
		Articolo17 art17 = new Articolo17();
		art17.setEsercente(context.getRequest().getParameter("esercente"));
		art17.setNomeEsercente(context.getRequest().getParameter("nomeEsercente"));
		//art17.setTipo(context.getRequest().getParameter("tipo"));

		int idMacello = -1;
		String nomeMacello = "";
		String comuneMacello = "";
		int aslMacello = -1;
		String approvalNumber = "";
		int orgId = -1;
		int altId =-1;
		
		try {orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));}catch (Exception e) {}
		try {altId = Integer.parseInt(context.getRequest().getParameter("altId"));}catch (Exception e) {}
			
		if (orgId>0){
			idMacello = orgId;
			Organization macello = new Organization(db, idMacello);
			nomeMacello = macello.getName();
			approvalNumber = macello.getApprovalNumber();
			aslMacello = macello.getSiteId();
			
			Iterator iaddressM = macello.getAddressList().iterator();
		    while (iaddressM.hasNext()) {
		      OrganizationAddress thisAddress = (OrganizationAddress)iaddressM.next();
		      if (thisAddress.getType()==5)
				comuneMacello = thisAddress.getCity() + " ("+ thisAddress.getState()+")";
		    }
		}
		else if (altId>0 && DatabaseUtils.getTipologiaPartizione(db, altId) == Ticket.ALT_OPU){
			idMacello = altId;
			Stabilimento macello = new Stabilimento(db, idMacello, true);
			nomeMacello = macello.getOperatore().getRagioneSociale();
			approvalNumber = macello.getApprovalNumber(db);
			aslMacello = macello.getIdAsl();
			comuneMacello = macello.getSedeOperativa().getDescrizioneComune() + " ("+  macello.getSedeOperativa().getDescrizione_provincia()+")";
		}
		else if (altId>0 && DatabaseUtils.getTipologiaPartizione(db, altId) == Ticket.ALT_SINTESIS){
			idMacello = altId;
			SintesisStabilimento macello = new SintesisStabilimento(db, idMacello, true);
			nomeMacello = macello.getOperatore().getRagioneSociale();
			approvalNumber = macello.getApprovalNumber();
			aslMacello = macello.getIdAsl();
			comuneMacello = macello.getIndirizzo().getDescrizioneComune() + " ("+  macello.getIndirizzo().getDescrizione_provincia()+")";
		}
		context.getRequest().setAttribute("nomeMacello", nomeMacello);	
		context.getRequest().setAttribute("approvalNumber", approvalNumber);	
		context.getRequest().setAttribute("comuneMacello", comuneMacello);	
		context.getRequest().setAttribute("aslMacello", String.valueOf(aslMacello));	
		art17.setIdMacello(idMacello);
		
		art17.setData(context.getRequest().getParameter("data"));
		art17.setIdPartita(idPartita);
		art17.setIdSeduta(idSeduta);
		art17.setIdUtente(user_id);
		art17.popolaDaModulo(db, configTipo);
		art17.popolaDaArt17(db);
		art17.gestisciChiudiArt17(db);
		context.getRequest().setAttribute("art17", art17);
		
		LookupList siteList = new LookupList(db, "lookup_site_id");
		context.getRequest().setAttribute("SiteList", siteList);
		
		
		
		String nomeEsercente ="";
		String indirizzoEsercente="";
		
		if (art17.getEsercente()>0 && DatabaseUtils.getTipologiaPartizione(db, art17.getEsercente())==Ticket.ALT_ORGANIZATION){
			Organization esercente = new Organization(db, art17.getEsercente());
			nomeEsercente = esercente.getName();
			Iterator iaddressM = esercente.getAddressList().iterator();
		    while (iaddressM.hasNext()) {
		      OrganizationAddress thisAddress = (OrganizationAddress)iaddressM.next();
		      if (thisAddress.getType()==5)
		    	  indirizzoEsercente = " - "+ thisAddress.getStreetAddressLine1()+", "+  thisAddress.getCity() + " ("+ thisAddress.getState()+")";
		}
		}
		else if (art17.getEsercente()>0  && DatabaseUtils.getTipologiaPartizione(db, art17.getEsercente())==Ticket.ALT_OPU){
			Stabilimento esercente = new Stabilimento(db, art17.getEsercente(), true);
			nomeEsercente =esercente.getOperatore().getRagioneSociale();
	    	  indirizzoEsercente = " - "+ esercente.getSedeOperativa().getDescrizioneToponimo() + " " + esercente.getSedeOperativa().getVia()+ " "+esercente.getSedeOperativa().getCivico()+", "+  esercente.getSedeOperativa().getDescrizioneComune() + " ("+ esercente.getSedeOperativa().getDescrizione_provincia()+")";
		}
		else if (art17.getEsercente()>0  && DatabaseUtils.getTipologiaPartizione(db, art17.getEsercente())==Ticket.ALT_SINTESIS){
			SintesisStabilimento esercente = new SintesisStabilimento(db, art17.getEsercente(), true);
			nomeEsercente =esercente.getOperatore().getRagioneSociale();
	    	  indirizzoEsercente = " - "+ esercente.getIndirizzo().getDescrizioneToponimo() + " " + esercente.getIndirizzo().getVia()+ " "+esercente.getIndirizzo().getCivico()+", "+  esercente.getIndirizzo().getDescrizioneComune() + " ("+ esercente.getIndirizzo().getDescrizione_provincia()+")";
		}
		else {
			nomeEsercente=art17.getNomeEsercente();
			indirizzoEsercente = "";
		}
		
		context.getRequest().setAttribute("nomeEsercente", nomeEsercente);
		context.getRequest().setAttribute("indirizzoEsercente", indirizzoEsercente);
		}

		catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");}
		finally {
			this.freeConnection(context, db);
		}
		
		String ret = "printArt17_";
		ret = ret + configTipo.getIdTipo();
		
		if (idSeduta!=null && !idSeduta.equals("null"))
			ret = ret+"_Sedute";
		
		ret = ret + "_OK";
		
		return ret;
		
	}
public String executeCommandRegistroMacellazioni(ActionContext context) throws SQLException, IOException{
		
		Connection db = null;
		getConfigTipo(context);
		
		try {
		 db = this.getConnection(context);
		RegistroMacellazioni reg = new RegistroMacellazioni();
		
		int idMacello = -1;
		String nomeMacello = "";
		
		int orgId = -1;
		int altId =-1;
		
		try {orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));}catch (Exception e) {}
		try {altId = Integer.parseInt(context.getRequest().getParameter("altId"));}catch (Exception e) {}
		
		
		if (orgId>0){
			idMacello = orgId;
			Organization macello = new Organization(db, idMacello);
			nomeMacello = macello.getName();
		}
		else if (altId>0 && DatabaseUtils.getTipologiaPartizione(db, altId) == Ticket.ALT_OPU){
			idMacello = altId;
			Stabilimento macello = new Stabilimento(db, idMacello, true);
			nomeMacello = macello.getOperatore().getRagioneSociale();
		}
		else if (altId>0 && DatabaseUtils.getTipologiaPartizione(db, altId) == Ticket.ALT_SINTESIS){
			idMacello = altId;
			SintesisStabilimento macello = new SintesisStabilimento(db, idMacello, true);
			nomeMacello = macello.getOperatore().getRagioneSociale();
		}
			
		reg.setIdMacello(idMacello);
		
		reg.setData(context.getRequest().getParameter("data"));
		reg.setSeduta(context.getRequest().getParameter("seduta"));
		reg.popolaDaRegistro(db, configTipo);
		context.getRequest().setAttribute("reg", reg);
	
		 
	
		context.getRequest().setAttribute("nomeMacello", nomeMacello);
		LookupList siteList = new LookupList(db, "lookup_site_id");
		context.getRequest().setAttribute("SiteList", siteList);
		}
		catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");}
		finally {
			this.freeConnection(context, db);
		}
		
		String ret = "printRegistro_";
		ret = ret + configTipo.getIdTipo();
		ret = ret + "_OK";
		
		return ret;
		
	}

public String executeCommandModelloGenerico(ActionContext context) throws SQLException, IOException{
	
	Connection db = null;
	int tipoModulo = -1;
	
	int idMacello = -1;
	int aslMacello = -1;
	String nomeMacello = "";
	String approvalNumber = "";
	String comuneMacello = "";

	
	int orgId = -1;
	int altId =-1;
	
	try {orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));}catch (Exception e) {}
	try {altId = Integer.parseInt(context.getRequest().getParameter("altId"));}catch (Exception e) {}
	
	ModelloGenerico mod = new ModelloGenerico();
	
	try {
	 db = this.getConnection(context);
	 
		if (orgId>0){
			idMacello = orgId;
			Organization macello = new Organization(db, idMacello);
			aslMacello = macello.getSiteId();
			nomeMacello = macello.getName();
			approvalNumber = macello.getApprovalNumber();
			
			Iterator iaddressM = macello.getAddressList().iterator();
		    while (iaddressM.hasNext()) {
		      OrganizationAddress thisAddress = (OrganizationAddress)iaddressM.next();
		      if (thisAddress.getType()==5)
				comuneMacello = thisAddress.getCity() + " ("+ thisAddress.getState()+")";
		    }
		    
		}
		else if (altId>0 && DatabaseUtils.getTipologiaPartizione(db, altId) == Ticket.ALT_OPU){
			idMacello = altId;
			Stabilimento macello = new Stabilimento(db, idMacello, true);
			nomeMacello = macello.getOperatore().getRagioneSociale();
			approvalNumber = macello.getApprovalNumber(db);
			aslMacello = macello.getIdAsl();
			comuneMacello = macello.getSedeOperativa().getDescrizioneComune() + " ("+  macello.getSedeOperativa().getDescrizione_provincia()+")";
		}
		else if (altId>0 && DatabaseUtils.getTipologiaPartizione(db, altId) == Ticket.ALT_SINTESIS){
			idMacello = altId;
			SintesisStabilimento macello = new SintesisStabilimento(db, idMacello, true);
			nomeMacello = macello.getOperatore().getRagioneSociale();
			approvalNumber = macello.getApprovalNumber();
			aslMacello = macello.getIdAsl();
			comuneMacello = macello.getIndirizzo().getDescrizioneComune() + " ("+  macello.getIndirizzo().getDescrizione_provincia()+")";
		}
		
			
		mod.setIdMacello(idMacello);
	
		mod.setData(context.getRequest().getParameter("data"));
		mod.setTipoModulo(context.getRequest().getParameter("tipoModulo"));
		mod.setAslMacello(aslMacello);
		mod.popola(db);
		context.getRequest().setAttribute("mod", mod);
		tipoModulo = mod.getTipoModulo();
	
	LookupList siteList = new LookupList(db, "lookup_site_id");
	context.getRequest().setAttribute("SiteList", siteList);
	LookupList listaSpecie	= new LookupList( db, "m_lookup_specie" );
	context.getRequest().setAttribute("listaSpecie", listaSpecie);
	LookupList provvedimenti	= new LookupList( db, "m_lookup_provvedimenti_casl" );
	context.getRequest().setAttribute("ProvvedimentiList", provvedimenti);
	LookupList nonconformita	= new LookupList( db, "m_lookup_motivi_comunicazioni_asl" );
	context.getRequest().setAttribute("NonConformitaList", nonconformita);
	LookupList listaLuoghiVerifica = new LookupList(db, "m_lookup_luoghi_verifica");
	context.getRequest().setAttribute("listaLuoghiVerifica", listaLuoghiVerifica);
	LookupList listaRazze	= new LookupList( db, "razze_bovini" );
	context.getRequest().setAttribute("listaRazze", listaRazze);
	LookupList listaPA  = new LookupList( db, "m_lookup_provvedimenti_vam");
	context.getRequest().setAttribute("listaPA", listaPA);
	LookupList listaOrgani	= new LookupList( db, "m_lookup_organi" );
	context.getRequest().setAttribute("listaOrgani", listaOrgani);
	LookupList listaStadi  = new LookupList( db, "m_lookup_patologie_organi");
	context.getRequest().setAttribute("listaStadi", listaStadi);
	LookupList categorieBovine  = new LookupList( db, "m_lookup_specie_categorie_bovine");
	categorieBovine.addItem(-1, "ALTRE CATEGORIE");
	context.getRequest().setAttribute("categorieBovine",categorieBovine);
	LookupList categorieBufaline  = new LookupList( db, "m_lookup_specie_categorie_bufaline");
	categorieBufaline.addItem(-1, "ALTRE CATEGORIE");
	context.getRequest().setAttribute("categorieBufaline",categorieBufaline);
	
	context.getRequest().setAttribute("approvalNumber", approvalNumber);	
	context.getRequest().setAttribute("nomeMacello", nomeMacello);	
	context.getRequest().setAttribute("comuneMacello", comuneMacello);	

	
	}
	catch (Exception errorMessage) {
		errorMessage.printStackTrace();
		context.getRequest().setAttribute("Error", errorMessage);
		return ("SystemError");}
	finally {
		this.freeConnection(context, db);
	}
	
	String ret = "printModello_";
	ret = ret + tipoModulo;
	ret = ret + "_OK";
	
	return ret;
	
}

	private void getConfigTipo(ActionContext context)
	{
		if(context.getSession().getAttribute("configTipo")!=null){
			Object o = context.getSession().getAttribute("configTipo");
			
			try {
				configTipo = (ConfigTipo) context.getSession().getAttribute("configTipo");
				}
			catch (Exception e){
				try {
					org.aspcfs.modules.macellazioninewopu.utils.ConfigTipo configTipo2 = (org.aspcfs.modules.macellazioninewopu.utils.ConfigTipo) context.getSession().getAttribute("configTipo");
					final int tipo = configTipo2.getIdTipo();
					configTipo = new ConfigTipo(tipo);
					context.getSession().setAttribute("configTipo", configTipo);					}
				catch (Exception e2){
					org.aspcfs.modules.macellazioninewsintesis.utils.ConfigTipo configTipo2 = (org.aspcfs.modules.macellazioninewsintesis.utils.ConfigTipo) context.getSession().getAttribute("configTipo");
					final int tipo = configTipo2.getIdTipo();
					configTipo = new ConfigTipo(tipo);
					context.getSession().setAttribute("configTipo", configTipo);
				}
			}
	}
	}	
	public String executeCommandModelloEchinococco(ActionContext context) throws SQLException, IOException{
		
		getConfigTipo(context);
		Connection db = null;
		String organo = context.getRequest().getParameter("organo");
		context.getRequest().setAttribute("organo", organo);
		
		try {
		 db = this.getConnection(context);
		Organization macello = new Organization(db, Integer.parseInt(context.getRequest().getParameter("orgId")));
		context.getRequest().setAttribute("macello", macello);
//		Partita partita = new Partita();
//		partita.load(context.getRequest().getParameter("idPartita"), db, configTipo);
//		
		
		Partita partita = (Partita) GenericBean.load(context.getRequest().getParameter("idPartita"), db, configTipo);
		context.getRequest().setAttribute("partita", partita);
		LookupList siteList = new LookupList(db, "lookup_site_id");
		context.getRequest().setAttribute("SiteList", siteList);
		
		
		}
		catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");}
		finally {
			this.freeConnection(context, db);
		}
		
		String ret = "printModello_Echinococco";
		ret = ret + "_OK";
		
		return ret;
		
	}
	
}
