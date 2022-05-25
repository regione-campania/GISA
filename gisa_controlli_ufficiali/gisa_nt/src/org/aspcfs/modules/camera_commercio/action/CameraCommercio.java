/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.camera_commercio.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.aspcfs.modules.accounts.actions.Accounts;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationAddress;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.camera_commercio.base.BCameraCommercio;
import org.aspcfs.modules.lineeattivita.base.LineeAttivita;
import org.aspcfs.modules.lineeattivita.base.RelAtecoLineeAttivita;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public final class CameraCommercio extends CFSModule 
{
	public String executeCommandDefault( ActionContext context )
	{
		String ret = "CameraCommercioOK";
		
		if (!hasPermission(context, "accounts-cameracommercio-view"))
		{
			ret = "PermissionError";
		}
		
		return ret;
	}
	
	public String executeCommandCerca( ActionContext context )
	{
		String ret = "CercaOK";
		
		if (!hasPermission(context, "accounts-cameracommercio-view"))
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
			context.getRequest().setAttribute( "risultati", BCameraCommercio.load( ragione_sociale, partita_iva, codice_fiscale, duplicati, db ) );
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
		if (!hasPermission(context, "accounts-cameracommercio-view"))
		{
			return "PermissionError";
		}
		
		String ret = "DettaglioOK";
		Connection db = null;
		
		String id = context.getParameter( "id" );
		
		try 
		{
			db = getConnection( context );
			context.getRequest().setAttribute( "dettaglio", BCameraCommercio.load( id, db) );
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
		if ( !hasPermission(context, "accounts-cameracommercio-edit") || !hasPermission(context, "accounts-accounts-add"))
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
	    	  BCameraCommercio.importato( id_cc, org.getOrgId(), db );
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
		
		if ( !hasPermission(context, "accounts-cameracommercio-edit") || !hasPermission(context, "accounts-accounts-add"))
		{
			return "PermissionError";
		}
	    
	    Connection db = null;
	    
	    try
	    {
	      db = this.getConnection(context);
	      LookupList rel_ateco_linea_attivita_List = costruisci_lookup_rel_ateco_linea_attivita(context);
	      context.getRequest().setAttribute("rel_ateco_linea_attivita_List", rel_ateco_linea_attivita_List);
	     
	      BCameraCommercio cc = BCameraCommercio.load( context.getParameter("id"), db );
	      Accounts acc = new Accounts();
	      
	      acc.executeCommandAdd( context );
	      
	      Organization org = (Organization)context.getRequest().getAttribute( "OrgDetails" );
	      OrganizationAddress oa = new OrganizationAddress();
	      context.getRequest().setAttribute( "Address", oa );
	      context.getRequest().setAttribute( "Camera", cc );
	      
		  org.setName( cc.getRagione_sociale() );		//ragione sociale
		  org.setBanca( cc.getDenominazione_ul() );		//denominazione
		  org.setPartitaIva( cc.getPartita_iva() );
		  org.setCodiceFiscaleRappresentante( cc.getCf_impresa() );
		  org.setTelefonoRappresentante( cc.getTelefono_ul() );
		  
		  oa.setStreetAddressLine1( cc.getCod_toponimo_sede_legale() +  " " + cc.getVia_sede_legale() + " " + cc.getCivico_sede_legale() ); //via
		  //oa.setCity( cc.getDescrizione_comune_sede_legale() );										//comune	
		  //oa.setCountry( cc.getProvincia_sede_legale() );												//provincia	
		  oa.setZip( cc.getCap_sede_legale() );
		  carica_lookup_ateco_nel_context(context,""+org.getId(),db);
	    }
	    catch (Exception e)
	    {
	      context.getRequest().setAttribute("Error", e);
	      e.printStackTrace();
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
	
	public LookupList costruisci_lookup_rel_ateco_linea_attivita_cod_ateco(ActionContext context, String cod_ateco){
	    LookupList ret = new LookupList();
		Connection db = null;

		try {
			db = getConnection(context);
			ArrayList<RelAtecoLineeAttivita> all_rel_ateco_linee_attivita = RelAtecoLineeAttivita.load_rel_ateco_linee_attivita_per_codice_istat(cod_ateco, db);

			for (RelAtecoLineeAttivita rel_ateco_linea : all_rel_ateco_linee_attivita) {
				if (!rel_ateco_linea.getLinea_attivita().isEmpty())
					ret.addItem( rel_ateco_linea.getId() , rel_ateco_linea.getCategoria() + " - " + rel_ateco_linea.getLinea_attivita());
				else
					ret.addItem( rel_ateco_linea.getId() , rel_ateco_linea.getCategoria() );
			}
			
			if (ret.size()==0) {
				ret.addItem(-1, "-- Selezionare prima il codice Ateco --" );
			}
			
		} catch (Exception e) {	
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return (null);
		} finally {
			this.freeConnection(context, db);
		}
		return ret;
	}
	
	public void carica_lookup_ateco_nel_context(ActionContext context, String orgid, Connection db){
		  LookupList rel_ateco_linea_attivita_List = costruisci_lookup_rel_ateco_linea_attivita(context);
	      context.getRequest().setAttribute("rel_ateco_linea_attivita_List", rel_ateco_linea_attivita_List);
	      ArrayList<RelAtecoLineeAttivita> all_rel_ateco_linee_attivita = RelAtecoLineeAttivita.load_all_rel_ateco_linee_attivita(db);
	      context.getRequest().setAttribute("all_rel_ateco_linee_attivita", all_rel_ateco_linee_attivita);
	      LineeAttivita linea_attivita_principale = LineeAttivita.load_linea_attivita_principale_per_org_id(orgid, db);
	      context.getRequest().setAttribute("linea_attivita_principale", linea_attivita_principale);
	      
	      LookupList List_id_rel_1 = new LookupList(); 
	      LookupList List_id_rel_2 = new LookupList(); 
	      LookupList List_id_rel_3 = new LookupList(); 
	      LookupList List_id_rel_4 = new LookupList(); 
	      LookupList List_id_rel_5 = new LookupList(); 
	      LookupList List_id_rel_6 = new LookupList(); 
	      LookupList List_id_rel_7 = new LookupList(); 
	      LookupList List_id_rel_8 = new LookupList(); 
	      LookupList List_id_rel_9 = new LookupList(); 
	      LookupList List_id_rel_10= new LookupList(); 
	      
	       	  
	    	  List_id_rel_1.addItem(-1, "-- Selezionare prima il codice Ateco --" );
	      
	    	  List_id_rel_2.addItem(-1, "-- Selezionare prima il codice Ateco --" );
	      
	    	  List_id_rel_3.addItem(-1, "-- Selezionare prima il codice Ateco --" );
	      
	    	  List_id_rel_4.addItem(-1, "-- Selezionare prima il codice Ateco --" );
	      
	    	  List_id_rel_5.addItem(-1, "-- Selezionare prima il codice Ateco --" );
	      
	    	  List_id_rel_6.addItem(-1, "-- Selezionare prima il codice Ateco --" );
	      
	    	  List_id_rel_7.addItem(-1, "-- Selezionare prima il codice Ateco --" );
	      
	    	  List_id_rel_8.addItem(-1, "-- Selezionare prima il codice Ateco --" );
	      
	    	  List_id_rel_9.addItem(-1, "-- Selezionare prima il codice Ateco --" );
	      
	    	  List_id_rel_10.addItem(-1, "-- Selezionare prima il codice Ateco --" );
	      
	     
	      context.getRequest().setAttribute("List_id_rel_1 ", List_id_rel_1 );
	      context.getRequest().setAttribute("List_id_rel_2 ", List_id_rel_2 );
	      context.getRequest().setAttribute("List_id_rel_3 ", List_id_rel_3 );
	      context.getRequest().setAttribute("List_id_rel_4 ", List_id_rel_4 );
	      context.getRequest().setAttribute("List_id_rel_5 ", List_id_rel_5 );
	      context.getRequest().setAttribute("List_id_rel_6 ", List_id_rel_6 );
	      context.getRequest().setAttribute("List_id_rel_7 ", List_id_rel_7 );
	      context.getRequest().setAttribute("List_id_rel_8 ", List_id_rel_8 );
	      context.getRequest().setAttribute("List_id_rel_9 ", List_id_rel_9 );
	      context.getRequest().setAttribute("List_id_rel_10", List_id_rel_10);

	  }
	
	public LookupList costruisci_lookup_rel_ateco_linea_attivita(ActionContext context){
	    LookupList ret = new LookupList();
		Connection db = null;

		try {
			db = getConnection(context);
			ArrayList<RelAtecoLineeAttivita> all_rel_ateco_linee_attivita = RelAtecoLineeAttivita.load_all_rel_ateco_linee_attivita(db);
						
			for (RelAtecoLineeAttivita rel_ateco_linea : all_rel_ateco_linee_attivita) {
				if (!rel_ateco_linea.getLinea_attivita().isEmpty())
					ret.addItem( rel_ateco_linea.getId() , rel_ateco_linea.getCodice_istat() + " : " + rel_ateco_linea.getCategoria() + " - " + rel_ateco_linea.getLinea_attivita());
				else
					ret.addItem( rel_ateco_linea.getId() , rel_ateco_linea.getCodice_istat() + " : " + rel_ateco_linea.getCategoria() );
			}
			
		} catch (Exception e) {	
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return (null);
		} finally {
			this.freeConnection(context, db);
		}
		return ret;
	}
}
