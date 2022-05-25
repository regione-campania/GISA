/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.actions;
   
import java.sql.Connection;
import java.util.ArrayList;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.ComuniAnagrafica;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.anagrafe_animali.base.AnimaleList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.LineaProduttivaList;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;

public class LineaProduttivaAction extends CFSModule {
 
	public String executeCommandSearch(ActionContext context){
		Connection db = null;
		try 
		{ 
		
			db = this.getConnection(context);
			LineaProduttivaList lpList = new LineaProduttivaList();
			PagedListInfo lpInfo = this.getPagedListInfo(context,"SearchLineaProduttiva");
			lpInfo.setLink("LineaProduttivaAction.do?command=Search");
			lpInfo.setSearchCriteria(lpList, context);
			lpInfo.setColumnToSortBy("lp.tipo_iter,lp.id_categoria");
			lpList.setPagedListInfo(lpInfo);
			lpList.setTipoSelezione(context.getRequest().getParameter("tipoSelezione"));
			lpList.buildList(db);
			context.getRequest().setAttribute("ListaLineaProduttiva", lpList);
			
			LookupList listaMacroCategoria = new LookupList(db,"opu_lookup_macrocategorie_linee_produttive");
			listaMacroCategoria.addItem(-1,"-SELEZIONA CATEGORIA-") ;
			
			LookupList listaCategoria = new LookupList(db,"opu_lookup_aggregazioni_linee_produttive");
			listaCategoria.addItem(-1,"-SELEZIONA CATEGORIA-") ;
			
	 	 	
			
			context.getRequest().setAttribute("ListaMacroCategoria", listaMacroCategoria);
			context.getRequest().setAttribute("listaCategoria", listaCategoria);
			
			
 
	
			

		} 
		catch (Exception e) 
		{
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally 
		{
			this.freeConnection(context, db);
		}
		//return ("SearchOK"); 
		return getReturn(context, "Search");
	}
	
	public String executeCommandScegliLineaProduttiva(ActionContext context){
		
		
		Connection db = null;
		try {
			db = getConnection(context);
			
			
			String[] lineeProduttiveSelezionate = context.getRequest().getParameterValues("idLineaProduttiva");
			LineaProduttivaList listaLineeProduttive = new LineaProduttivaList() ; 
			if (lineeProduttiveSelezionate != null && lineeProduttiveSelezionate.length>0)
			{
				for (int i = 0 ; i < lineeProduttiveSelezionate.length; i++)
				{
					if(!lineeProduttiveSelezionate[i].equals("-1"))
					{
					LineaProduttiva lp = new LineaProduttiva (db,Integer.parseInt(lineeProduttiveSelezionate[i]));
					listaLineeProduttive.add(lp);
					}
				}
				
			}
			
			
			listaLineeProduttive.setTipoSelezione(context.getParameter("tipoSelezione"));
			
			context.getRequest().setAttribute("LineeProduttiveList", listaLineeProduttive);
			
			if (context.getRequest().getParameter("tipoRegistrazione") != null)
				context.getRequest().setAttribute("tipoRegistrazione", context.getRequest().getParameter("tipoRegistrazione"));
			
		}catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("ClosePopupOK");
	}

	
public String executeCommandListaAnimali (ActionContext context){
		
		if (!hasPermission(context, "accounts-accounts-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		
		try {

			String tempLineaProduttivaId = context.getRequest().getParameter("idLinea");
			

			if (tempLineaProduttivaId == null) {
				tempLineaProduttivaId = (String) context.getRequest().getAttribute("idLinea");
			}

			//String iter = context.getRequest().getParameter("tipo");
			Integer lineaPId = null;

			if (tempLineaProduttivaId != null) {
				lineaPId = Integer.parseInt(tempLineaProduttivaId);
			}
			
			UserBean user = (UserBean) context.getSession().getAttribute("User");
			AnimaleList animaleList = new AnimaleList();
			
			 if ("animali".equals(context.getSession().getAttribute("previousSearchType")) || "accounts".equals(context.getSession().getAttribute("previousSearchType"))) {
				 //reset pagedListInfo
				 this.deletePagedListInfo(context, "animaliListInfo");
			 }
			// Prepare pagedListInfo
			PagedListInfo searchListInfo = this.getPagedListInfo(context,
			"animaliListInfo");
			searchListInfo.setLink("LineaProduttivaAction.do?command=ListaAnimali&idLinea="+lineaPId);
			searchListInfo.setListView("all");

			
				db = this.getConnection(context);
				
				Operatore operatore = new Operatore();
				operatore.queryRecordOperatorebyIdLineaProduttiva(db, lineaPId);
				context.getRequest().setAttribute("operatore", operatore);
				
				LookupList lookupSpecie = new LookupList(db,"lookup_specie");
				context.getRequest().setAttribute("LookupSpecie", lookupSpecie);
				// Build the organization list
				animaleList.setPagedListInfo(searchListInfo);
				animaleList.setMinerOnly(false);
				animaleList.setTypeId(searchListInfo
						.getFilterKey("listFilter1"));

				animaleList.setStageId(searchListInfo
						.getCriteriaValue("searchcodeStageId"));

				searchListInfo.setSearchCriteria(animaleList, context);

				if ("my".equals(searchListInfo.getListView())) {
					//operatoreList.setOwnerId(this.getUserId(context));
				}
				if (animaleList.getIdAsl() == -1) {
					animaleList.setIdAsl(this.getUserSiteId(context));
					//organizationList.setIncludeOrganizationWithoutSite(false);
				} else if (animaleList.getIdAsl() == -1) {
					//TUTTE LE ASLanimaleList.setIncludeAllAsl(true);
				}
				// fetching criterea for account status (active, disabled or
				// any)
				//int enabled = searchListInfo.getFilterKey("listFilter2");

				animaleList.setId_proprietario_o_detentore(lineaPId);

				animaleList.buildList(db);

				context.getRequest().setAttribute("animaleList", animaleList);

				LookupList siteList = new LookupList(db, "lookup_site_id");
				siteList.addItem(-1, "-- SELEZIONA VOCE --");
				context.getRequest().setAttribute("AslList", siteList);


				LookupList razzaList = new LookupList(db, "lookup_razza");
				razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				//assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("razzaList", razzaList);


				LookupList mantelloList = new LookupList(db, "lookup_mantello");
				mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
				//assetManufacturerList.remove(assetManufacturerList.get("N.D."));
				context.getRequest().setAttribute("mantelloList", mantelloList);
				
				
				LookupList statoList = new LookupList(db, "lookup_tipologia_stato");
				context.getRequest().setAttribute("statoList", statoList);


				context.getSession().setAttribute("previousSearchType", "animaliProprietari");
			
			
				return getReturn(context, "List");
		
	} catch (Exception e) {
		e.printStackTrace();
		// Go through the SystemError process
		context.getRequest().setAttribute("Error", e);
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}

}



public String executeCommandDetails(ActionContext context) {
	if (!hasPermission(context, "accounts-accounts-view")) {
		return ("PermissionError");
	}
	Connection db = null;
	SystemStatus systemStatus = this.getSystemStatus(context);
	Operatore newOperatoreLineaProduttiva = null;
	try {

		String tempLineaProduttivaId = context.getRequest().getParameter("lineaId");
		if (tempLineaProduttivaId == null) {
			tempLineaProduttivaId = (String) context.getRequest().getAttribute("lineaId");
		}
		if (tempLineaProduttivaId == null){
			tempLineaProduttivaId = (String) context.getRequest().getParameter("idLinea");
		}

		//String iter = context.getRequest().getParameter("tipo");
		Integer lineaPId = null;

		if (tempLineaProduttivaId != null) {
			lineaPId = Integer.parseInt(tempLineaProduttivaId);
		}

		UserBean user = (UserBean)context.getRequest().getSession().getAttribute("User");
		
		

		db = this.getConnection(context);

		//newOperatore = new Operatore(db, tempid);
		newOperatoreLineaProduttiva = new Operatore();
		newOperatoreLineaProduttiva.queryRecordOperatorebyIdLineaProduttiva(db, lineaPId);
		context.getRequest().setAttribute("OperatoreDettagli", newOperatoreLineaProduttiva);


		LookupList siteList = new LookupList(db, "lookup_site_id");
		siteList.addItem(-1, "-- SELEZIONA VOCE --");
		context.getRequest().setAttribute("AslList", siteList);


		ComuniAnagrafica c = new ComuniAnagrafica();
		//Provvisoriamente prendo tutti i comuni
		ArrayList<ComuniAnagrafica> listaComuni =  c.buildList_all(db, ((UserBean)context.getSession().getAttribute("User")).getSiteId());

		LookupList comuniList = new LookupList(listaComuni, -1);

		comuniList.addItem(-1, "Seleziona comune");
		context.getRequest().setAttribute("ComuniList", comuniList);



		return getReturn(context, "Details");

	} catch (Exception e) {
		e.printStackTrace();
		// Go through the SystemError process
		context.getRequest().setAttribute("Error", e);
		return ("SystemError");
	} finally {
		this.freeConnection(context, db);
	}



}


public String executeCommandPrepareMovimentazioneDiMassa(ActionContext context){

	
	if (!hasPermission(context, "accounts-accounts-view")) {
		return ("PermissionError");
	}
	Connection db = null;
	SystemStatus systemStatus = this.getSystemStatus(context);
	
	try {

		String tempLineaProduttivaId = context.getRequest().getParameter("idLinea");
		

		if (tempLineaProduttivaId == null) {
			tempLineaProduttivaId = (String) context.getRequest().getAttribute("idLinea");
		}

		//String iter = context.getRequest().getParameter("tipo");
		Integer lineaPId = null;

		if (tempLineaProduttivaId != null) {
			lineaPId = Integer.parseInt(tempLineaProduttivaId);
		}
		
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		AnimaleList animaleList = new AnimaleList();
		
		 if ("animali".equals(context.getSession().getAttribute("previousSearchType")) || "accounts".equals(context.getSession().getAttribute("previousSearchType"))) {
			 //reset pagedListInfo
			 this.deletePagedListInfo(context, "animaliListInfo");
		 }
		// Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context,
		"animaliListInfo");
		searchListInfo.setLink("LineaProduttivaAction.do?command=ListaAnimali&idLinea="+lineaPId);
		searchListInfo.setListView("all");

		
			db = this.getConnection(context);
			
			Operatore operatore = new Operatore();
			operatore.queryRecordOperatorebyIdLineaProduttiva(db, lineaPId);
			context.getRequest().setAttribute("operatore", operatore);
			
			LookupList lookupSpecie = new LookupList(db,"lookup_specie");
			context.getRequest().setAttribute("LookupSpecie", lookupSpecie);
			// Build the organization list
			animaleList.setPagedListInfo(searchListInfo);
			animaleList.setMinerOnly(false);
			animaleList.setTypeId(searchListInfo
					.getFilterKey("listFilter1"));

			animaleList.setStageId(searchListInfo
					.getCriteriaValue("searchcodeStageId"));

			searchListInfo.setSearchCriteria(animaleList, context);

			if ("my".equals(searchListInfo.getListView())) {
				//operatoreList.setOwnerId(this.getUserId(context));
			}
			if (animaleList.getIdAsl() == -1) {
				animaleList.setIdAsl(this.getUserSiteId(context));
				//organizationList.setIncludeOrganizationWithoutSite(false);
			} else if (animaleList.getIdAsl() == -1) {
				//TUTTE LE ASLanimaleList.setIncludeAllAsl(true);
			}
			// fetching criterea for account status (active, disabled or
			// any)
			//int enabled = searchListInfo.getFilterKey("listFilter2");

			animaleList.setId_proprietario_o_detentore(lineaPId);

			animaleList.buildList(db);

			context.getRequest().setAttribute("animaleList", animaleList);

//			LookupList siteList = new LookupList(db, "lookup_site_id");
//			siteList.addItem(-1, "-- SELEZIONA VOCE --");
//			context.getRequest().setAttribute("AslList", siteList);
//
//
//			LookupList razzaList = new LookupList(db, "lookup_razza");
//			razzaList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
//			//assetManufacturerList.remove(assetManufacturerList.get("N.D."));
//			context.getRequest().setAttribute("razzaList", razzaList);
//
//
//			LookupList mantelloList = new LookupList(db, "lookup_mantello");
//			mantelloList.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
//			//assetManufacturerList.remove(assetManufacturerList.get("N.D."));
//			context.getRequest().setAttribute("mantelloList", mantelloList);
			
			
			LookupList statoList = new LookupList(db, "lookup_tipologia_stato");
			context.getRequest().setAttribute("statoList", statoList);


			context.getSession().setAttribute("previousSearchType", "animaliProprietari");
		
		
			return getReturn(context, "MovimentazioneDiMassaList");
	
} catch (Exception e) {
	e.printStackTrace();
	// Go through the SystemError process
	context.getRequest().setAttribute("Error", e);
	return ("SystemError");
} finally {
	this.freeConnection(context, db);
}


	
}


}
