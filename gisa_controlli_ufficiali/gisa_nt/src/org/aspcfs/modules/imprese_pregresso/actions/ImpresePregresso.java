/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.imprese_pregresso.actions;

import java.sql.Connection;
import java.sql.SQLException;

import org.aspcfs.modules.accounts.actions.Accounts;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationAddress;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.imprese_pregresso.base.BImpresePregresso;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public final class ImpresePregresso extends CFSModule 
{
	public String executeCommandDefault( ActionContext context )
	{
		String ret = "ImpresePregressoOK";
		
		if (!hasPermission(context, "accounts-impresepregresso-view"))
		{
			ret = "PermissionError";
		}
		
		return ret;
	}
	
	public String executeCommandCerca( ActionContext context )
	{
		String ret = "CercaOK";
		
		if (!hasPermission(context, "accounts-impresepregresso-view"))
		{
			return "PermissionError";
		}
		
		String ragione_sociale	= context.getParameter( "ragione_sociale" );
		String partita_iva		= context.getParameter( "partita_iva" );
		String codice_fiscale	= context.getParameter( "codice_fiscale" );
		String duplicati		= context.getParameter( "duplicati" );
		
		Connection db = null;
		
		try 
		{
			db = getConnection( context );
			context.getRequest().setAttribute( "risultati", BImpresePregresso.load( ragione_sociale, partita_iva, codice_fiscale, duplicati, db ) );
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection( context, db );
		}
		
		return ret;
	}
	
	public String executeCommandDettaglio( ActionContext context )
	{
		if (!hasPermission(context, "accounts-impresepregresso-view"))
		{
			return "PermissionError";
		}
		
		String ret = "DettaglioOK";
		Connection db = null;
		
		String id = context.getParameter( "id" );
		
		try 
		{
			db = getConnection( context );
			context.getRequest().setAttribute( "dettaglio", BImpresePregresso.load( id, db) );
			LookupList siteList = new LookupList(db, "lookup_site_id");
		      siteList.addItem(-1, "-- SELEZIONA VOCE --");
		      context.getRequest().setAttribute("SiteList", siteList);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection( context, db );
		}
		
		return ret;
	}
	
	public String executeCommandInsert( ActionContext context )
	{
		if ( !hasPermission(context, "accounts-impresepregresso-edit") || !hasPermission(context, "accounts-accounts-add"))
		{
			return "PermissionError";
		}
		
		String ret = "InsertOK";
		
	    Connection db = null;
	    
	    try
	    {
	      db = this.getConnection(context);
	      
	      Accounts acc = new Accounts();
	      
	      String esito = acc.executeCommandInsert( context );
	      
	      if( esito.equalsIgnoreCase( "InsertOK" ) )
	      {
	    	  String id_cc = context.getParameter( "id_cc" );
	    	  Organization org = (Organization)context.getRequest().getAttribute( "OrgDetails" );
	    	  BImpresePregresso.importato( id_cc, org.getOrgId(), db );
	      }
		  
	    }
	    catch (Exception e)
	    {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    }
	    finally
	    {
	      this.freeConnection(context, db);
	    }
	    
	    addModuleBean(context, "Add Account", "Accounts Add");
	    context.getRequest().setAttribute(
	        "systemStatus", this.getSystemStatus(context));

	    if (context.getRequest().getParameter("actionSource") != null) {
	        return getReturn(context, "AddAccount");
	    }

		
		return ret;
	}
	
	public String executeCommandTrasformaOsa( ActionContext context )
	{
		String ret = "TrasformaOK";
		
		if ( !hasPermission(context, "accounts-impresepregresso-edit") || !hasPermission(context, "accounts-accounts-add"))
		{
			return "PermissionError";
		}
	    
	    Connection db = null;
	    
	    try
	    {
	      db = this.getConnection(context);
	      
	      BImpresePregresso cc = BImpresePregresso.load( context.getParameter("id"), db );
	      Accounts acc = new Accounts();
	      
	      acc.executeCommandAdd( context );
	      
	      Organization org = (Organization)context.getRequest().getAttribute( "OrgDetails" );
	      OrganizationAddress oa = new OrganizationAddress();
	      OrganizationAddress sedeLegale = new OrganizationAddress();;
	      context.getRequest().setAttribute( "AddressSedeOperativa", oa );
	      context.getRequest().setAttribute( "AddressLegale", sedeLegale );
	      context.getRequest().setAttribute( "Impresa", cc );
	      
		  org.setName( cc.getRagione_sociale() );		//ragione sociale
		  org.setBanca( cc.getDenominazione() );		//denominazione
		  org.setPartitaIva( cc.getPartita_iva() );
		  org.setCodiceFiscale( cc.getCodice_fiscale() );
		  org.setCognomeRappresentante( cc.getRappresentante_legale() );
		  org.setAlertText(cc.getTipologia_attivita());
		  org.setCodiceFiscaleCorrentista(cc.getCodice_ateco());
		  org.setCodiceImpresaInterno(cc.getCodice_registrazione_sian());
		  org.setDataPresentazione(cc.getData_inizio_attivita());
		  org.setDate2(cc.getData_fine_attivita());
		  String codiceasl = cc.getCodice_istat_asl();
		  int asl = -1;
		  if(codiceasl.equals("101")){
				asl = 201;
								
			}else if(codiceasl.equals("102")){
				asl = 201;
								
			}else if(codiceasl.equals("103")){
				asl = 202;
							
			}else if(codiceasl.equals("104")){
				asl = 203;
				
			}
			else if(codiceasl.equals("105")){
				asl = 203;
				
			}
			else if(codiceasl.equals("106")){
				asl = 204;
				
			}
			else if(codiceasl.equals("107")){
				asl = 205;
				
			}
			else if(codiceasl.equals("108")){
				asl = 205;
				
			}
			else if(codiceasl.equals("109")){
				asl = 206;
				
			}
			else if(codiceasl.equals("110")){
				asl = 206;
				
			}else if(codiceasl.equals("111")){
				asl = 207;
				
			}
			else if(codiceasl.equals("112")){
				asl = 207;
				
			}
			else if(codiceasl.equals("113")){
				asl = 207;
				
			}
		  	org.setSiteId(asl);
			
		  	sedeLegale.setStreetAddressLine1( cc.getVia_sede_legale() );//indirizzo sede legale
		  	sedeLegale.setCity( cc.getComune_sede_legale() );		
		  	sedeLegale.setType(1);
						
		  oa.setStreetAddressLine1( cc.getIndirizzo_sede_operativa() );//indirizzo sede operativa
		  oa.setCity( cc.getComune_sede_operativa() );	
		  oa.setType(5);
		  
		
	        
	    }
	    catch (Exception e)
	    {
	      context.getRequest().setAttribute("Error", e);
	      return ("SystemError");
	    }
	    finally
	    {
	      this.freeConnection(context, db);
	    }
	    
	    addModuleBean(context, "Add Account", "Accounts Add");
	    context.getRequest().setAttribute(
	        "systemStatus", this.getSystemStatus(context));
	    

	    if (context.getRequest().getParameter("actionSource") != null) {
	        return getReturn(context, "AddAccount");
	    }

		return ret;
	}
	
}
