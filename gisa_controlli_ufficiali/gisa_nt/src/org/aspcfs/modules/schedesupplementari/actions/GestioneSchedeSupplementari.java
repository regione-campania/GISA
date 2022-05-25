/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.schedesupplementari.actions;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.ricercaunica.base.RicercaOpu;
import org.aspcfs.modules.schedesupplementari.base.IstanzaScheda;

import com.darkhorseventures.framework.actions.ActionContext;


public final class GestioneSchedeSupplementari extends CFSModule {
	

	Logger logger = Logger.getLogger("MainLogger");


	public String executeCommandLista(ActionContext context) {
		
		if (!(hasPermission(context, "gestione-schede-supplementari-view"))) {
			return ("PermissionError");
		}
		
		int riferimentoId = -1;
		String riferimentoIdNomeTab = null;

		try { riferimentoId = Integer.parseInt(context.getRequest().getParameter("riferimentoId"));} catch (Exception e) {}
		if (riferimentoId == -1)
			try { riferimentoId = Integer.parseInt((String)context.getRequest().getAttribute("riferimentoId"));} catch (Exception e) {}
		
		try { riferimentoIdNomeTab = context.getRequest().getParameter("riferimentoIdNomeTab");} catch (Exception e) {}
		if (riferimentoIdNomeTab == null)
			try { riferimentoIdNomeTab = (String)context.getRequest().getAttribute("riferimentoIdNomeTab");} catch (Exception e) {}
	
		ArrayList<IstanzaScheda> listaSchede = new ArrayList<IstanzaScheda>();
		
		Connection db = null;
		try {
			db = this.getConnection(context);
			
			listaSchede = IstanzaScheda.buildListaSchede(db, riferimentoId, riferimentoIdNomeTab);
			context.getRequest().setAttribute("listaSchede", listaSchede);
			    
		}catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 
		finally
		{
			this.freeConnection(context, db);
		}
		
		return "ListaSchedeOK";
	}


	public String executeCommandViewScheda(ActionContext context) {
		
		if (!(hasPermission(context, "gestione-schede-supplementari-view"))) {
			return ("PermissionError");
		}
		
		int riferimentoId = -1;
		String riferimentoIdNomeTab = null;
		int idIstanzaLinea = -1;
		String numScheda = null;
		
		String ret = "";
		
		try { riferimentoId = Integer.parseInt(context.getRequest().getParameter("riferimentoId"));} catch (Exception e) {}
		if (riferimentoId == -1)
			try { riferimentoId = Integer.parseInt((String)context.getRequest().getAttribute("riferimentoId"));} catch (Exception e) {}
		
		try { riferimentoIdNomeTab = context.getRequest().getParameter("riferimentoIdNomeTab");} catch (Exception e) {}
		if (riferimentoIdNomeTab == null)
			try { riferimentoIdNomeTab = (String)context.getRequest().getAttribute("riferimentoIdNomeTab");} catch (Exception e) {}
		
		try { idIstanzaLinea = Integer.parseInt(context.getRequest().getParameter("idIstanzaLinea"));} catch (Exception e) {}
		if (idIstanzaLinea == -1)
			try { idIstanzaLinea = Integer.parseInt((String)context.getRequest().getAttribute("idIstanzaLinea"));} catch (Exception e) {}
	
		try { numScheda = context.getRequest().getParameter("numScheda");} catch (Exception e) {}
		if (numScheda == null)
			try { numScheda = (String)context.getRequest().getAttribute("numScheda");} catch (Exception e) {}
		
		
		Connection db = null;
		try {
			db = this.getConnection(context);
			IstanzaScheda ist = new IstanzaScheda(db, riferimentoId, riferimentoIdNomeTab, idIstanzaLinea, numScheda);
			context.getRequest().setAttribute("Istanza", ist);
			ret = "ViewScheda"+ist.getReturnView()+"OK"; 

		}catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 
		finally
		{
			this.freeConnection(context, db);
		}
		
		return ret;
	}


public String executeCommandInsertScheda(ActionContext context) {
		
	if (!(hasPermission(context, "gestione-schede-supplementari-add"))) {
		return ("PermissionError");
	}
	
		int riferimentoId = -1;
		String riferimentoIdNomeTab = null;
		int idIstanzaLinea = -1;
		String numScheda = null;
		
		String ret = "";
		
		try { riferimentoId = Integer.parseInt(context.getRequest().getParameter("riferimentoId"));} catch (Exception e) {}
		if (riferimentoId == -1)
			try { riferimentoId = Integer.parseInt((String)context.getRequest().getAttribute("riferimentoId"));} catch (Exception e) {}
		
		try { riferimentoIdNomeTab = context.getRequest().getParameter("riferimentoIdNomeTab");} catch (Exception e) {}
		if (riferimentoIdNomeTab == null)
			try { riferimentoIdNomeTab = (String)context.getRequest().getAttribute("riferimentoIdNomeTab");} catch (Exception e) {}
		
		try { idIstanzaLinea = Integer.parseInt(context.getRequest().getParameter("idIstanzaLinea"));} catch (Exception e) {}
		if (idIstanzaLinea == -1)
			try { idIstanzaLinea = Integer.parseInt((String)context.getRequest().getAttribute("idIstanzaLinea"));} catch (Exception e) {}
	
		try { numScheda = context.getRequest().getParameter("numScheda");} catch (Exception e) {}
		if (numScheda == null)
			try { numScheda = (String)context.getRequest().getAttribute("numScheda");} catch (Exception e) {}
		
		
		Connection db = null;
		try {
			db = this.getConnection(context);
			IstanzaScheda ist = new IstanzaScheda(db, riferimentoId, riferimentoIdNomeTab, idIstanzaLinea, numScheda);
			ist.buildDaRequest(context);
			
			ist.insert(db, getUserId(context));
			
			context.getRequest().setAttribute("riferimentoId", String.valueOf(ist.getRiferimentoId()));
			context.getRequest().setAttribute("riferimentoIdNomeTab", ist.getRiferimentoIdNomeTab());
			context.getRequest().setAttribute("idIstanzaLinea", String.valueOf(ist.getIdIstanzaLinea()));
			context.getRequest().setAttribute("numScheda", ist.getNumScheda());

		}catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 
		finally
		{
			this.freeConnection(context, db);
		}
		
		return executeCommandViewScheda(context);
	}

}
