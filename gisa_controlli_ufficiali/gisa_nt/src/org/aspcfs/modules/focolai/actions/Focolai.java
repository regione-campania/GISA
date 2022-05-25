/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.focolai.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.allevamenti.base.Organization;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Parameter;
import org.aspcfs.modules.focolai.base.Focolaio;
import org.aspcfs.modules.focolai.base.FocolaioDecorso;
import org.aspcfs.modules.focolai.base.FocolaioDenunciaAnimali;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.ParameterUtils;

import com.darkhorseventures.framework.actions.ActionContext;

public final class Focolai extends CFSModule
{

	public String executeCommandDefault(ActionContext context)
	{
		return executeCommandHome(context);
	}

	public String executeCommandHome(ActionContext context)
	{
		if (!hasPermission(context, "focolai-view")) {
		      return ("PermissionError");
		}
		
		
		SystemStatus systemStatus = this.getSystemStatus(context);
	    Connection db = null;
	    
		try
		{
			db = getConnection(context);
			
			String orgid = context.getRequest().getParameter("orgId");
		    Organization newOrg = new Organization(db, Integer.parseInt(orgid));
		    context.getRequest().setAttribute("OrgDetails", newOrg);
		    
		    ArrayList arr = (ArrayList) newOrg.getFocolai(db);   //ricerca d.dauria
		    context.getRequest().setAttribute("ListaFocolai", arr);
		    
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("Asl", siteList);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
	    }
		finally
		{
			this.freeConnection(context, db);
		}
		return "HomeOK";
	}
	/*
	public String executeCommandCerca( ActionContext context )
	{
		if (!hasPermission(context, "acque-view")) {
		      return ("PermissionError");
		}
		
		SystemStatus systemStatus = this.getSystemStatus(context);
	    Connection db = null;
		try
		{
			db = getConnection(context);
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("Asl", siteList);
			
			String asl = context.getParameter( "asl" );
			if( "-1".equalsIgnoreCase( asl ) )
			{
				asl = null;
			}
			
			Vector<Acqua> acque = Acqua.load(
					asl,
					context.getParameter( "codice_identificativo" ),
					context.getParameter( "comune" ),
					context.getParameter( "classe_principale" ),
					context.getParameter( "classe_provvisoria" ),
					context.getParameter( "identificativo_decreto" ),
					db );
			
			context.getRequest().setAttribute( "acque", acque );
			
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
	    }
		finally
		{
			this.freeConnection(context, db);
		}
		return "CercaOK";
	}
	*/
	public String executeCommandAggiungi( ActionContext context )
	{
		Organization newOrg = null;
		
		if (!hasPermission(context, "focolai-add")) {
		      return ("PermissionError");
		}
		
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Connection db = null;
		try
		{
			db = getConnection(context);
			
			String orgid = context.getRequest().getParameter("orgId");
		    newOrg = new Organization(db, Integer.parseInt(orgid));
		    context.getRequest().setAttribute("OrgDetails", newOrg);
			
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("Asl", siteList);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
	    }
		finally
		{
			this.freeConnection(context, db);
		}
		
		return "AggiungiOK";
	}
	/*
	public String executeCommandDettaglio( ActionContext context )
	{
		if (!hasPermission(context, "acque-view")) {
		      return ("PermissionError");
		}
		
	    SystemStatus systemStatus = this.getSystemStatus(context);
		String id = context.getParameter( "id" );
		Connection db = null;
		
		try
		{
			db = getConnection( context );
			
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("Asl", siteList);
			
			Acqua acqua = Acqua.load( id, db );
			
			context.getRequest().setAttribute( "acqua", acqua );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
	    }
		
		return "DettaglioOK";
	}
	*/
	public String executeCommandModifica( ActionContext context )
	{
		if (!hasPermission(context, "focolai-view")) {
		      return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		   
		
		
			try {
					db = getConnection( context );
					int orgid = Integer.parseInt(context.getRequest().getParameter("orgId"));
					Organization newOrg = new Organization(db, orgid);
					context.getRequest().setAttribute("OrgDetails", newOrg);
				
					LookupList siteList = new LookupList(db, "lookup_site_id");
					siteList.addItem(-1,  "-- SELEZIONA VOCE --");
					siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
					context.getRequest().setAttribute("Asl", siteList);
					//context.getRequest().getAttribute("focolaio");
					
				
					  String id = (String) context.getRequest().getParameter("focolaioId");
					   Focolaio foc = new Focolaio( db, Integer.parseInt(id));    //per il dettaglio
					context.getRequest().setAttribute( "focolaio", foc );
					
				
					
					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		return "ModificaOK";
	}
	
	public String executeCommandSalva( ActionContext context )
	{
		if (!hasPermission(context, "focolai-add")) {
		      return ("PermissionError");
		}
		
		Connection db = null;
		
		try
		{
			db = getConnection( context );
			int id = -1;
			
			Focolaio focolaio = getBean( context );
			
			Timestamp now	= new Timestamp( System.currentTimeMillis() );
		
			focolaio.insert(db);
			
			int orgid = Integer.parseInt(context.getRequest().getParameter("orgId"));
		    Organization newOrg = new Organization(db, orgid);
		    context.getRequest().setAttribute("OrgDetails", newOrg);
		 
		    id = DatabaseUtils.getCurrVal(db, "focolai_focolaio_id_seq", id);
		
		    context.getRequest().setAttribute("FocolaioDetails", id); //in sessione per il dettaglio
		    
		    ArrayList<Parameter> specie = ParameterUtils.list(context.getRequest(), "specie");
		    ArrayList<Parameter> complessivo = ParameterUtils.list(context.getRequest(), "complessivo");
		    ArrayList<Parameter> natiStalla = ParameterUtils.list(context.getRequest(), "natiStalla");
		    ArrayList<Parameter> introdotti = ParameterUtils.list(context.getRequest(), "introdotti");
		    ArrayList<Parameter> ammalati = ParameterUtils.list(context.getRequest(), "ammalati");
		    ArrayList<Parameter> morti = ParameterUtils.list(context.getRequest(), "morti");
			
		    for( int i = 0; i < complessivo.size(); i++ )
		    {
		    	FocolaioDenunciaAnimali temp = new FocolaioDenunciaAnimali();
		    	temp.setFocolaioId(id);
		    	temp.setSpecie(specie.get(i).getValore());
		    	temp.setComplessivo(complessivo.get(i).getValore());
		    	temp.setNatiStalla(natiStalla.get(i).getValore());
		    	temp.setIntrodotti(introdotti.get(i).getValore());
		    	temp.setAmmalati(ammalati.get(i).getValore());
		    	temp.setMorti(morti.get(i).getValore());
		    	temp.insert(db);
		   
		    }
		    
			Focolaio foc = new Focolaio( db, id );    //per il dettaglio
			context.getRequest().setAttribute( "focolaio", foc );
			
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
	    }
		
		
		return executeCommandDettaglio(context);
	}
	
	
	public String executeCommandDettaglio( ActionContext context )
	{
		if (!hasPermission(context, "focolai-view")) {
		      return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		   
		
		
			try {
				db = getConnection( context );
				
					
		
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("Asl", siteList);
			//context.getRequest().getAttribute("focolaio");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		
		return "DettaglioOK";
	}	
	
	public String executeCommandDettaglioLista( ActionContext context )
	{
		if (!hasPermission(context, "focolai-view")) {
		      return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		   
		
		
			try {
				db = getConnection( context );
				
					
			    
				String id = (String) context.getRequest().getParameter("focolaioId");
				 Focolaio foc = new Focolaio( db, Integer.parseInt(id) );    //per il dettaglio
					context.getRequest().setAttribute( "focolaio", foc );
					
				int orgId = foc.getOrgId();
				Organization org = new Organization(db, orgId);
				context.getRequest().setAttribute("OrgDetails", org);
				
		
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("Asl", siteList);
			//context.getRequest().getAttribute("focolaio");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		
		return "DettaglioOK";
	}	
	
	/*
	public String executeCommandUpdate( ActionContext context )
	{
		return executeCommandDettaglio( context );
	}
	*/
	public Focolaio getBean( ActionContext context )
	{
		Focolaio ret = new Focolaio();
		
		//ret.setFocolaioId(context.getParameter("focolaioId"));
		ret.setOrgId(context.getParameter("orgId"));
		ret.setLocalita(context.getParameter("localita"));
		ret.setDataSospetto(context.getParameter("dataSospetto"));
		ret.setDataProva(context.getParameter("dataProva"));
		ret.setDataApertura(context.getParameter("dataApertura"));
		ret.setOrigineMalattia(context.getParameter("origineMalattia"));
		ret.setProvenienzaRegionale(context.getParameter("provenienzaRegionale"));
		ret.setDataProvenienzaRegionale(context.getParameter("dataProvenienzaRegionale"));
		ret.setProvenienzaExtraRegionale(context.getParameter("provenienzaExtraRegionale"));
		ret.setDataProvenienzaExtraRegionale(context.getParameter("dataProvenienzaExtraRegionale"));
		ret.setPascoloInfetto(context.getParameter("pascoloInfetto"));
		ret.setReinfezione(context.getParameter("reinfezione"));
		ret.setCaniInfetti(context.getParameter("caniInfetti"));
		ret.setMontaEsterna(context.getParameter("montaEsterna"));
		ret.setIatrogena(context.getParameter("iatrogena"));
		ret.setDataProvvedimenti(context.getParameter("dataProvvedimenti"));
		ret.setNumeroProvvedimenti(context.getParameter("numeroProvvedimenti"));
		ret.setProposteAdozione(context.getParameter("proposteAdozione"));
		ret.setDataImmunizzanti(context.getParameter("dataImmunizzanti"));
		ret.setOsservazioni(context.getParameter("osservazioni"));
		ret.setMalattia(context.getParameter("malattia"));
		ret.setSpecieAnimale(context.getParameter("specie_animale"));
		
		
		return ret;
	}
	
	public Focolaio getBeanModuloB( ActionContext context )
	{
		Focolaio ret = new Focolaio();
		
		//ret.setFocolaioId(context.getParameter("focolaioId"));
		ret.setOrgId(context.getParameter("orgId"));
		ret.setLocalita(context.getParameter("localita"));
		ret.setDataSospetto(context.getParameter("dataSospetto"));
		ret.setDataProva(context.getParameter("dataProva"));
		ret.setDataApertura(context.getParameter("dataApertura"));
		ret.setOrigineMalattia(context.getParameter("origineMalattia"));
		ret.setProvenienzaRegionale(context.getParameter("provenienzaRegionale"));
		ret.setDataProvenienzaRegionale(context.getParameter("dataProvenienzaRegionale"));
		ret.setProvenienzaExtraRegionale(context.getParameter("provenienzaExtraRegionale"));
		ret.setDataProvenienzaExtraRegionale(context.getParameter("dataProvenienzaExtraRegionale"));
		ret.setPascoloInfetto(context.getParameter("pascoloInfetto"));
		ret.setReinfezione(context.getParameter("reinfezione"));
		ret.setCaniInfetti(context.getParameter("caniInfetti"));
		ret.setMontaEsterna(context.getParameter("montaEsterna"));
		ret.setIatrogena(context.getParameter("iatrogena"));
		ret.setDataProvvedimenti(context.getParameter("dataProvvedimenti"));
		ret.setNumeroProvvedimenti(context.getParameter("numeroProvvedimenti"));
		ret.setProposteAdozione(context.getParameter("proposteAdozione"));
		ret.setDataImmunizzanti(context.getParameter("dataImmunizzanti"));
		ret.setOsservazioni(context.getParameter("osservazioni"));
		ret.setMalattia(context.getParameter("malattia"));
		ret.setSpecieAnimale(context.getParameter("specie_animale"));
		ret.setDataUltimoCaso(context.getParameter("dataUltimoCaso"));
		ret.setDataRevocaSindaco(context.getParameter("dataRevocaSindaco"));
		ret.setProposteRevoca(context.getParameter("proposteRevoca"));
		ret.setIdFocolaioChiuso(context.getParameter("focolaioId"));  //errata
		
		
		return ret;
	}
	
	
	public String executeCommandUpdate( ActionContext context )
	{
		if (!hasPermission(context, "focolai-add")) {
		      return ("PermissionError");
		}
		
		Connection db = null;
		
		try
		{
			db = getConnection( context );
			//int id = -1;
			
			Focolaio focolaio = getBean( context );
			
			Timestamp now	= new Timestamp( System.currentTimeMillis() );
			String id = (String) context.getRequest().getParameter("focolaioId");
			
			focolaio.update(db , Integer.parseInt(id));
			
			
			
			//tabella interna
			    ArrayList<Parameter> specie = ParameterUtils.list(context.getRequest(), "specie");
			    ArrayList<Parameter> complessivo = ParameterUtils.list(context.getRequest(), "complessivo");
			    ArrayList<Parameter> natiStalla = ParameterUtils.list(context.getRequest(), "natiStalla");
			    ArrayList<Parameter> introdotti = ParameterUtils.list(context.getRequest(), "introdotti");
			    ArrayList<Parameter> ammalati = ParameterUtils.list(context.getRequest(), "ammalati");
			    ArrayList<Parameter> morti = ParameterUtils.list(context.getRequest(), "morti");
			    ArrayList<Parameter> denuncia = ParameterUtils.list(context.getRequest(), "denuncia");
				
			    for( int i = 0; i < specie.size(); i++ )
			    {
			    	FocolaioDenunciaAnimali temp = new FocolaioDenunciaAnimali();
			    	temp.setFocolaioId(id);
			    	temp.setSpecie(specie.get(i).getValore());
			    	temp.setComplessivo(complessivo.get(i).getValore());
			    	temp.setNatiStalla(natiStalla.get(i).getValore());
			    	temp.setIntrodotti(introdotti.get(i).getValore());
			    	temp.setAmmalati(ammalati.get(i).getValore());
			    	temp.setMorti(morti.get(i).getValore());
			    	temp.update(db, Integer.parseInt(id), Integer.parseInt(denuncia.get(i).getValore()));
			   
			    }
			    
			    int orgid = Integer.parseInt(context.getRequest().getParameter("orgId"));
			    Organization newOrg = new Organization(db, orgid);
			    context.getRequest().setAttribute("OrgDetails", newOrg);
			    
			    Focolaio foc = new Focolaio( db, Integer.parseInt(id) );    //per il dettaglio
				context.getRequest().setAttribute( "focolaio", foc );
	
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
	    }
		
		
		return executeCommandDettaglio(context);
	}
	
	public String executeCommandModuloChiusura( ActionContext context )
	{
		Organization newOrg = null;
		
		if (!hasPermission(context, "focolai-add")) {
		      return ("PermissionError");
		}
		
	    SystemStatus systemStatus = this.getSystemStatus(context);
	    Connection db = null;
		try
		{
			db = getConnection(context);
			
			String id = (String) context.getRequest().getParameter("focolaioId");
			 Focolaio foc = new Focolaio( db, Integer.parseInt(id) );    //per il dettaglio
				context.getRequest().setAttribute( "focolaio", foc );
			
			int orgid = foc.getOrgId();
		    newOrg = new Organization(db, orgid);
		    context.getRequest().setAttribute("OrgDetails", newOrg);
			
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("Asl", siteList);
			
			   
			
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
	    }
		finally
		{
			this.freeConnection(context, db);
		}
		
		return "AggiungiModuloBOK";
	}
	
	
	public String executeCommandSalvaModuloB( ActionContext context )
	{
		if (!hasPermission(context, "focolai-add")) {
		      return ("PermissionError");
		}
		
		Connection db = null;
		
		try
		{
			db = getConnection( context );
			int id = -1;
			
			Focolaio focolaio = getBeanModuloB( context );
			int focolaioId = Integer.parseInt(context.getRequest().getParameter("focolaioId"));
			
			Timestamp now	= new Timestamp( System.currentTimeMillis() );
		
			focolaio.insertModuloB(db,focolaioId);
			
			int orgid = Integer.parseInt(context.getRequest().getParameter("orgId"));
		    Organization newOrg = new Organization(db, orgid);
		    context.getRequest().setAttribute("OrgDetails", newOrg);
		 
		    id = DatabaseUtils.getCurrVal(db, "focolai_focolaio_id_seq", id);
		
		    context.getRequest().setAttribute("FocolaioDetails", id); //in sessione per il dettaglio
		   
		    ArrayList<Parameter> specie = ParameterUtils.list(context.getRequest(), "specie");
		    ArrayList<Parameter> morti = ParameterUtils.list(context.getRequest(), "morti");
		    ArrayList<Parameter> abbattuti = ParameterUtils.list(context.getRequest(), "abbattuti");
		    ArrayList<Parameter> guariti = ParameterUtils.list(context.getRequest(), "guariti");
		    ArrayList<Parameter> totaleMalati = ParameterUtils.list(context.getRequest(), "totaleMalati");
		    ArrayList<Parameter> smarriti = ParameterUtils.list(context.getRequest(), "smarriti");
		    ArrayList<Parameter> sani = ParameterUtils.list(context.getRequest(), "sani");
		    ArrayList<Parameter> esistenti = ParameterUtils.list(context.getRequest(), "esistenti");
		    
		    for( int i = 0; i < morti.size(); i++ )
		    {
		    	FocolaioDecorso temp = new FocolaioDecorso();
		    	temp.setFocolaioId(id);
		    	temp.setSpecie(specie.get(i).getValore());
		    	temp.setMorti(morti.get(i).getValore());
		    	temp.setAbbattuti(abbattuti.get(i).getValore());
		    	temp.setGuariti(guariti.get(i).getValore());
		    	temp.setTotaleMalati(totaleMalati.get(i).getValore());
		    	temp.setSmarriti(smarriti.get(i).getValore());
		    	temp.setSani(sani.get(i).getValore());
		    	temp.setEsistenti(esistenti.get(i).getValore());
		    	temp.insert(db);
		   
		    }
		   
			Focolaio foc = new Focolaio( db, id, 2 );    //il 3 parametro serve a discriminare il tipo di costruttore
			context.getRequest().setAttribute( "focolaio", foc );
			
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
	    }
		
		
		return executeCommandDettaglioModuloB(context);
	}
	
	public String executeCommandDettaglioModuloB( ActionContext context )
	{
		if (!hasPermission(context, "focolai-view")) {
		      return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		   
		
		
			try {
				db = getConnection( context );
				
					
		
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("Asl", siteList);
			//context.getRequest().getAttribute("focolaio");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		
		return "DettaglioModuloBOK";
	}
	
	
	
	public String executeCommandModificaModuloB( ActionContext context )
	{
		if (!hasPermission(context, "focolai-view")) {
		      return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		   
		
		
			try {
					db = getConnection( context );
					int orgid = Integer.parseInt(context.getRequest().getParameter("orgId"));
					Organization newOrg = new Organization(db, orgid);
					context.getRequest().setAttribute("OrgDetails", newOrg);
				
					LookupList siteList = new LookupList(db, "lookup_site_id");
					siteList.addItem(-1,  "-- SELEZIONA VOCE --");
					siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
					context.getRequest().setAttribute("Asl", siteList);
					//context.getRequest().getAttribute("focolaio");
					
				
					  String id = (String) context.getRequest().getParameter("focolaioId");
					   Focolaio foc = new Focolaio( db, Integer.parseInt(id),2);    //per il dettaglio
					context.getRequest().setAttribute( "focolaio", foc );
					
				
					
					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		return "ModificaModuloBOK";
	}
	
	
	
	public String executeCommandUpdateModuloB( ActionContext context )
	{
		if (!hasPermission(context, "focolai-add")) {
		      return ("PermissionError");
		}
		
		Connection db = null;
		
		try
		{
			db = getConnection( context );
			//int id = -1;
			
			Focolaio focolaio = getBeanModuloB( context );
			
			Timestamp now	= new Timestamp( System.currentTimeMillis() );
			String id = (String) context.getRequest().getParameter("focolaioId");
			
			focolaio.updateModuloB(db , Integer.parseInt(id));
			
			
			
			//tabella interna
			    ArrayList<Parameter> specie = ParameterUtils.list(context.getRequest(), "specie");
			    ArrayList<Parameter> morti = ParameterUtils.list(context.getRequest(), "morti");
			    ArrayList<Parameter> abbattuti = ParameterUtils.list(context.getRequest(), "abbattuti");
			    ArrayList<Parameter> guariti = ParameterUtils.list(context.getRequest(), "guariti");
			    ArrayList<Parameter> totaleMalati = ParameterUtils.list(context.getRequest(), "totaleMalati");
			    ArrayList<Parameter> smarriti = ParameterUtils.list(context.getRequest(), "smarriti");
			    ArrayList<Parameter> sani = ParameterUtils.list(context.getRequest(), "sani");
			    ArrayList<Parameter> esistenti = ParameterUtils.list(context.getRequest(), "esistenti");
			    ArrayList<Parameter> denuncia = ParameterUtils.list(context.getRequest(), "denuncia");
				
			    for( int i = 0; i < morti.size(); i++ )
			    {
			    	FocolaioDecorso temp = new FocolaioDecorso();
			    	temp.setFocolaioId(id);
			    	temp.setSpecie(specie.get(i).getValore());
			    	temp.setMorti(morti.get(i).getValore());
			    	temp.setAbbattuti(abbattuti.get(i).getValore());
			    	temp.setGuariti(guariti.get(i).getValore());
			    	temp.setTotaleMalati(totaleMalati.get(i).getValore());
			    	temp.setSmarriti(smarriti.get(i).getValore());
			    	temp.setSani(sani.get(i).getValore());
			    	temp.setEsistenti(esistenti.get(i).getValore());
			    	temp.update(db, Integer.parseInt(id), Integer.parseInt(denuncia.get(i).getValore()));
			   
			    }
			    
			    int orgid = Integer.parseInt(context.getRequest().getParameter("orgId"));
			    Organization newOrg = new Organization(db, orgid);
			    context.getRequest().setAttribute("OrgDetails", newOrg);
			    
			    Focolaio foc = new Focolaio( db, Integer.parseInt(id), 2 );    //per il dettaglio del modulo B
				context.getRequest().setAttribute( "focolaio", foc );
	
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
	    }
		
		
		return executeCommandDettaglioModuloB(context);
	}
	
	public String executeCommandDettaglioListaModuloB( ActionContext context )
	{
		if (!hasPermission(context, "focolai-view")) {
		      return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		   
		
		
			try {
				db = getConnection( context );
				
					
			    
				String id = (String) context.getRequest().getParameter("focolaioId");
				 Focolaio foc = new Focolaio( db, Integer.parseInt(id), 2 );    //per il dettaglio del modulo B, 2 raprresenta un discriminante
					context.getRequest().setAttribute( "focolaio", foc );
					
				int orgId = foc.getOrgId();
				Organization org = new Organization(db, orgId);
				context.getRequest().setAttribute("OrgDetails", org);
				
		
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("Asl", siteList);
			//context.getRequest().getAttribute("focolaio");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		
		return "DettaglioModuloBOK";
	}	
	
	
	public String executeCommandStampaModuloA( ActionContext context )
	{
		if (!hasPermission(context, "focolai-view")) {
		      return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		   
		
		
			try {
				db = getConnection( context );
				
					
			    
				String id = (String) context.getRequest().getParameter("focolaioId");
				 Focolaio foc = new Focolaio( db, Integer.parseInt(id) );    //per il dettaglio
					context.getRequest().setAttribute( "focolaio", foc );
					
				int orgId = foc.getOrgId();
				Organization org = new Organization(db, orgId);
				context.getRequest().setAttribute("OrgDetails", org);
				
		
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("Asl", siteList);
			//context.getRequest().getAttribute("focolaio");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		
		return "popupModuloAOK";
	}

	public String executeCommandStampaModuloB( ActionContext context )
	{
		if (!hasPermission(context, "focolai-view")) {
		      return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		   
		
		
			try {
				db = getConnection( context );
				
					
			    
				String id = (String) context.getRequest().getParameter("focolaioId");
				 Focolaio foc = new Focolaio( db, Integer.parseInt(id), 2 );    //per il dettaglio del modulo B. il 2 indica proprio questa esigenza
					context.getRequest().setAttribute( "focolaio", foc );
					
				int orgId = foc.getOrgId();
				Organization org = new Organization(db, orgId);
				context.getRequest().setAttribute("OrgDetails", org);
				
		
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("Asl", siteList);
			//context.getRequest().getAttribute("focolaio");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		
		return "popupModuloBOK";
	}

	
}
