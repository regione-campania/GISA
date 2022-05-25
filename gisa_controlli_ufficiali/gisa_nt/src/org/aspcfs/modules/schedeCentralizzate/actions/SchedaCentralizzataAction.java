/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.schedeCentralizzate.actions;

import java.sql.Connection;
import java.sql.SQLException;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.schedeCentralizzate.base.SchedaCentralizzata;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

public class SchedaCentralizzataAction extends CFSModule {
	public SchedaCentralizzataAction(){
		
	}
	
	public String executeCommandGeneraScheda(ActionContext context) {

		String orgId = context.getRequest().getParameter("orgId");
		String stabId = context.getRequest().getParameter("stabId");
		String altId = context.getRequest().getParameter("altId");
		String add1 = context.getRequest().getParameter("addressid");
		String add2 = context.getRequest().getParameter("addressid2");
		String add3 = context.getRequest().getParameter("addressid3");
		String idCampoEsteso = context.getRequest().getParameter("idCampoEsteso");
		String tipo = context.getRequest().getParameter("tipoOperatore");
		
		SchedaCentralizzata scheda = new SchedaCentralizzata();
		scheda.setOrgId(orgId);
		scheda.setStabId(stabId);
		scheda.setAltId(altId);
		scheda.setAddressId1(add1);
		scheda.setAddressId2(add2);
		scheda.setAddressId3(add3);
		scheda.setIdCampoEsteso(idCampoEsteso);
		scheda.setTipo(tipo);
		scheda.setDestinazione("print");
		
		Connection db = null;
		
		try {
			db = this.getConnection(context);
			scheda.popolaScheda(db);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			this.freeConnection(context, db);
		}
		
		context.getRequest().setAttribute("SchedaOperatore", scheda);
		return "SchedaOperatoreOk";
		
	}

	public String executeCommandDettaglioGestioneScheda(ActionContext context) {
		
		if (!hasPermission(context, "schede_centralizzate-view")) {
			return ("PermissionError");
		}

		String tipo = context.getRequest().getParameter("tipo");
		
		if (tipo==null || tipo.equals("null") || tipo.equals(""))
			tipo = "1";
		context.getRequest().setAttribute("tipo", tipo);
		
		Connection db = null;
		
		try {
			db = this.getConnection(context);
			LookupList tipoList = new LookupList(db, "lookup_tipo_scheda_operatore");
			context.getRequest().setAttribute( "tipoList", tipoList);
		
			SchedaCentralizzata scheda = new SchedaCentralizzata();
			scheda.setTipo(tipo);
			scheda.dettaglioGestioneScheda(db);
			context.getRequest().setAttribute("SchedaOperatore", scheda);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			this.freeConnection(context, db);
		}
		
		String operazione = context.getRequest().getParameter("operazione");
		if (operazione!=null && operazione.equals("modifica"))
			return "ModificaGestioneSchedaOperatoreOk";
		else if (operazione!=null && operazione.equals("clona"))
			return "ClonaGestioneSchedaOperatoreOk";
		return "DettaglioGestioneSchedaOperatoreOk";
		
	}
	
	public String executeCommandUpdateGestioneScheda(ActionContext context) {
		
		if (!hasPermission(context, "schede_centralizzate-edit")) {
			return ("PermissionError");
		}

		String tipo = context.getRequest().getParameter("tipo");
		
		if (tipo==null || tipo.equals("null") || tipo.equals(""))
			tipo = "1";
		context.getRequest().setAttribute("tipo", tipo);
		
		String numero = context.getRequest().getParameter("indice");
		int num = Integer.parseInt(numero);
		
		Connection db = null;
		
		try {
			db = this.getConnection(context);
			LookupList tipoList = new LookupList(db, "lookup_tipo_scheda_operatore");
			context.getRequest().setAttribute( "tipoList", tipoList);
		
			
			//Fare update
			for (int i =0; i< num; i++){
				
				SchedaCentralizzata scheda = new SchedaCentralizzata(db, context, i);
				if (scheda.getId()>0)
					scheda.update(db);
				else if (scheda.getId()==-999){
					}
				else{
					scheda.setTipo(tipo);
					scheda.insert(db);
				}
				
			}
			
			SchedaCentralizzata scheda = new SchedaCentralizzata();
			scheda.setTipo(tipo);
			scheda.dettaglioGestioneScheda(db);
			context.getRequest().setAttribute("SchedaOperatore", scheda);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			this.freeConnection(context, db);
		}
		
		return executeCommandDettaglioGestioneScheda(context);
		
	}
	
}
