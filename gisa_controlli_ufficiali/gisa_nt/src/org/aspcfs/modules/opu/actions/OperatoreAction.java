/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.actions;

import java.rmi.ServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.ComuniAnagrafica;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.accounts.base.Provincia;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.SICCodeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.LineaProduttivaList;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.OperatoreList;
import org.aspcfs.modules.opu.base.SedeList;
import org.aspcfs.modules.opu.base.SoggettoFisico;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.opu.base.StabilimentoList;
import org.aspcfs.modules.trasportoanimali.actions.SediList;
import org.aspcfs.utils.OperatoreInsertEsception;
import org.aspcfs.utils.PopolaCombo;
import org.aspcfs.utils.web.CountrySelect;
import org.aspcfs.utils.web.CustomLookupList;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.LookupListList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.RequestUtils;
import org.aspcfs.utils.web.StateSelect;

import com.daffodilwoods.daffodildb.server.sql99.dcl.sqlcontrolstatement.throwexception;
import com.daffodilwoods.daffodildb.server.sql99.expression.booleanvalueexpression.length;
import com.darkhorseventures.framework.actions.ActionContext;

public class OperatoreAction extends CFSModule {

	public String executeCommandDefault(ActionContext context) {

		return executeCommandSearchForm(context);
	}

	public String executeCommandAdd(ActionContext context) {

		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = this.getConnection(context);
			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");


			/*QUESTO PARAMETRO e' SETTATO NELLA REQUEST DAL COMMAND INSERT DI OPERATORE ACTION*/
			if (context.getRequest().getAttribute("Exist") != null && !("").equals(context.getRequest().getAttribute("Exist"))) {
				context.getRequest().setAttribute("Exist",(String) context.getRequest().getAttribute("Exist"));
			}

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db,((UserBean) context.getSession().getAttribute("User")).getSiteId(), 1);
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "");

			context.getRequest().setAttribute("ComuniList", comuniList);
			Operatore newOperatore = (Operatore) context.getFormBean();
			newOperatore.setAction(context.getAction().getActionName());
			context.getRequest().setAttribute("Operatore", newOperatore);

			Provincia provinciaAsl = new Provincia();
			provinciaAsl.getProvinciaAsl(db, thisUser.getSiteId());
			context.getRequest().setAttribute("provinciaAsl", provinciaAsl);

			context.getRequest().setAttribute("TipologiaSoggetto",(String) context.getRequest().getParameter("tipologiaSoggetto"));

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Add Account", "Accounts Add");
		context.getRequest().setAttribute("systemStatus",this.getSystemStatus(context));

		return getReturn(context, "Add");
	}


	public String executeCommandInsert(ActionContext context)throws SQLException {


		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;

		Operatore newOperatore = (Operatore) context.getFormBean();
		if (("false").equals((String) context.getParameter("doContinueLp"))) 
		{
			if ( context.getRequest().getParameter("inregione") != null && ("no").equals(context.getRequest().getParameter("inregione")) )
				newOperatore.setFlagFuoriRegione(true);
			context.getRequest().setAttribute("Operatore", newOperatore);
			if (context.getParameter("popup")!=null)
				context.getRequest().setAttribute("PopUp", context.getParameter("popup"));
			return executeCommandAdd(context);
		}

		newOperatore.setEnteredBy(getUserId(context));
		newOperatore.setModifiedBy(getUserId(context));
		if (context.getParameter("flagRicCe")!=null)
			newOperatore.setFlagRicCe(true);
		else
			newOperatore.setFlagRicCe(false);

		try {

			db = this.getConnection(context);
			SoggettoFisico soggettoAdded = null;
			soggettoAdded = new SoggettoFisico(context.getRequest());
			boolean exist = false;


			/**
			 * CONTROLLO DI ESISTENZA DELL'OPERATORE PER PARTITA IVA
			 */
			exist = newOperatore.checkEsistenzaOperatore(db);
			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			SoggettoFisico soggettoEsistente = null;
			soggettoEsistente = soggettoAdded.verificaSoggetto(db);
			/* se il soggetto non esiste lo aggiungo */

			if (soggettoEsistente == null|| soggettoEsistente.getIdSoggetto() <= 0) 
			{
				
				
				soggettoAdded.setEnteredBy(getUserId(context));
				soggettoAdded.setModifiedBy(getUserId(context));
				soggettoAdded.insert(db,context);
				newOperatore.setRappLegale(soggettoAdded);

			} else 
			{
				/* se esiste */

				if (soggettoEsistente.getIdSoggetto() > 0) 
				{
					Indirizzo indirizzoAdded = soggettoAdded.getIndirizzo();

					
					
					if(!soggettoAdded.getComuneNascita().equalsIgnoreCase(soggettoEsistente.getComuneNascita()) || 
							soggettoAdded.getDataNascita()!=soggettoEsistente.getDataNascita() || 
							!soggettoAdded.getNome().equalsIgnoreCase(soggettoEsistente.getNome()) || 
							!soggettoAdded.getCognome().equalsIgnoreCase(soggettoEsistente.getCognome()) || 
							
							soggettoEsistente.getIndirizzo()==null  || 
							(soggettoEsistente.getIndirizzo() !=null && soggettoEsistente.getIndirizzo().getComune()!=indirizzoAdded.getComune()) || 
							(soggettoEsistente.getIndirizzo() !=null && !soggettoEsistente.getIndirizzo().getVia().equalsIgnoreCase(indirizzoAdded.getVia()))						
							)
						
					{
						soggettoAdded.setIdSoggetto(soggettoEsistente.getIdSoggetto());
						soggettoAdded.updateSoloIndirizzo(db,context);
					}
					
					
					
					newOperatore.setRappLegale(soggettoAdded);
				}
			}



			if (exist) 
			{
				context.getRequest().setAttribute("Exist","Esiste Una Impresa con partita iva "+newOperatore.getPartitaIva());
				context.getRequest().setAttribute("rappLegale", soggettoAdded);
				if (context.getParameter("popup")!=null)
					context.getRequest().setAttribute("PopUp", context.getParameter("popup"));
				return executeCommandAdd(context);
			}


			Provincia provinciaAsl = new Provincia();
			provinciaAsl.getProvinciaAsl(db, user.getSiteId());
			context.getRequest().setAttribute("provinciaAsl", provinciaAsl);

			Indirizzo indirizzoAdded = null;
			if (new Integer(context.getRequest().getParameter("via")).intValue() > 0) 
			{

				indirizzoAdded = new Indirizzo(db, new Integer(context.getRequest().getParameter("via")).intValue());
				indirizzoAdded.setTipologiaSede(1); // Legale
			} else 
			{
				if (context.getRequest().getParameter("searchcodeIdprovincia")==null)
				{
					indirizzoAdded=soggettoAdded.getIndirizzo();
				}
				else
				{
					indirizzoAdded = new Indirizzo(context.getRequest(), db,context);
					indirizzoAdded.setTipologiaSede(1);
				}

			}

			newOperatore.getListaSediOperatore().add(indirizzoAdded);


			if (  	 context.getRequest().getParameter("doContinue") != null 	&& 
					!context.getRequest().getParameter("doContinue").equals("")	&& 
					context.getRequest().getParameter("doContinue").equals("false")
					) 
			{
				if (context.getParameter("popup")!=null)
					context.getRequest().setAttribute("PopUp", context.getParameter("popup"));

				return executeCommandAdd(context);
			}

			isValid = this.validateObject(context, db, newOperatore);

			if (isValid) 
			{
				recordInserted = newOperatore.insert(db,context);
				context.getRequest().setAttribute("opId",newOperatore.getIdOperatore());
			}


		} catch (Exception errorMessage) 
		{

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");
		if (recordInserted) 
		{
			if (context.getRequest().getParameter("popup") != null) 
			{
				context.getRequest().setAttribute("OperatoreAdd", newOperatore);
				context.getRequest().setAttribute("TipologiaSoggetto", "1");
				return ("ClosePopupOK");
			}
		}

		StabilimentoAction action = new StabilimentoAction();
		context.getRequest().setAttribute("idOp", newOperatore.getIdOperatore());
		return action.executeCommandAdd(context);

		// return ("InsertOK");

	}



	public String executeCommandUpdateSedeLegale(ActionContext context)throws SQLException {


		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;

		Operatore newOperatore =null;


		try {

			db = this.getConnection(context);
			boolean exist = false;

			int idOperatore = Integer.parseInt(context.getParameter("idOperatore"));
			newOperatore = new Operatore();
			newOperatore.queryRecordOperatore(db, idOperatore);
			Stabilimento newStabilimento = new Stabilimento(db,  Integer.parseInt(context.getParameter("idStabilimento")));

			newStabilimento.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("StabilimentoDettaglio",
					newStabilimento);

			Indirizzo indirizzoAdded = null;
			if (new Integer(context.getRequest().getParameter("via")).intValue() > 0) 
			{

				indirizzoAdded = new Indirizzo(db, new Integer(context.getRequest().getParameter("via")).intValue());
				indirizzoAdded.setTipologiaSede(1); // Legale
			} else 
			{
				indirizzoAdded = new Indirizzo(context.getRequest(), db,context);
				indirizzoAdded.setTipologiaSede(1);


			}
			indirizzoAdded.setModifiedBy(getUserId(context));
			newOperatore.getListaSediOperatore().add(indirizzoAdded);

		} catch (Exception errorMessage) 
		{

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");


		return "ModifySLOK";

		// return ("InsertOK");

	}


	public String executeCommandViewStoricoSoggettoFisicoImpresa(ActionContext context)throws SQLException {


		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;

		Operatore newOperatore =null;


		try {

			db = this.getConnection(context);
			boolean exist = false;

			int idOperatore = Integer.parseInt(context.getParameter("idOperatore"));
			newOperatore = new Operatore();
			newOperatore.queryRecordOperatoreStorico(db, idOperatore);
			Stabilimento newStabilimento = new Stabilimento(db,  Integer.parseInt(context.getParameter("idStabilimento")));
			newStabilimento.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("StabilimentoDettaglio",
					newStabilimento);
			context.getRequest().setAttribute("OperatoreDettaglio",
					newOperatore);


		} catch (Exception errorMessage) 
		{

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");


		return "ViewStoricoImpresaOK";

		// return ("InsertOK");

	}


	public String executeCommandUpdateSoggettoFisicoImpresa(ActionContext context)throws SQLException {


		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;

		Operatore newOperatore =null;


		try {

			db = this.getConnection(context);
			boolean exist = false;

			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			int idOperatore = Integer.parseInt(context.getParameter("idOperatore"));
			newOperatore = new Operatore();
			newOperatore.queryRecordOperatore(db, idOperatore);
			Stabilimento newStabilimento = new Stabilimento(db,  Integer.parseInt(context.getParameter("idStabilimento")));
			newStabilimento.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("StabilimentoDettaglio",
					newStabilimento);
			//newStabilimento.get
			SoggettoFisico soggettoAdded = null;
			soggettoAdded = new SoggettoFisico(context.getRequest());
			soggettoAdded.setModifiedBy(user.getUserId());
			soggettoAdded.setIpModifiedBy(user.getUserRecord().getIp());
			soggettoAdded.setEnteredBy(user.getUserId());
			soggettoAdded.setIpEnteredBy(user.getUserRecord().getIp());
			SoggettoFisico soggettoEsistente = null;
			soggettoEsistente = soggettoAdded.verificaSoggetto(db);
			/* se il soggetto non esiste lo aggiungo */

			if (soggettoEsistente == null|| soggettoEsistente.getIdSoggetto() <= 0) 
			{
				soggettoAdded.insert(db,context);
				newOperatore.setRappLegale(soggettoAdded);

			} else 
			{
				/* se esiste */

				if (soggettoEsistente.getIdSoggetto() > 0) 
				{
					Indirizzo indirizzoAdded = soggettoAdded.getIndirizzo();

					if ("si".equalsIgnoreCase(context.getParameter("sovrascrivi"))) 
					{
						soggettoEsistente.setNome(soggettoAdded.getNome());
						soggettoEsistente.setCognome(soggettoAdded.getCognome());
						soggettoEsistente.setDataNascita(soggettoAdded.getDataNascita());
						soggettoEsistente.setComuneNascita(soggettoAdded.getComuneNascita());
						soggettoEsistente.setDocumentoIdentita(soggettoAdded.getDocumentoIdentita());
						soggettoEsistente.setFax(soggettoAdded.getFax());
						soggettoEsistente.setProvinciaNascita(soggettoAdded.getProvinciaNascita());
						soggettoEsistente.setTelefono1(soggettoAdded.getTelefono1());
						soggettoEsistente.setTelefono2(soggettoAdded.getTelefono2());
						soggettoEsistente.setEmail(soggettoAdded.getEmail());
						soggettoEsistente.setModifiedBy(user.getUserId());
						soggettoEsistente.setIpModifiedBy(user.getUserRecord().getIp());
						soggettoEsistente.setIndirizzo(indirizzoAdded);
						soggettoEsistente.update(db,context);

					}
					newOperatore.setRappLegale(soggettoEsistente);




				}
			}

			newOperatore.updateSoggettoFisico(db,context);

		} catch (Exception errorMessage) 
		{

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");


		return "ModifySLOK";

		// return ("InsertOK");

	}


	public String executeCommandSearchForm(ActionContext context) {



		String id_specie=context.getRequest().getParameter("specie");
		context.getRequest().setAttribute("id_specie", id_specie);
		UserBean user = (UserBean) context.getSession()
				.getAttribute("User");

		Operatore operatore = new Operatore();

		operatore.setAction(context.getAction().getActionName());
		context.getRequest().setAttribute("Operatore", operatore);
		// Bypass search form for portal users
		if (isPortalUser(context)) {
			// return (executeCommandSearch(context));
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = getConnection(context);

			this.resetPagedListInfo(context);
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(Constants.INVALID_SITE, "-- TUTTI --");
			context.getRequest().setAttribute("SiteList", siteList);
			context.getRequest().setAttribute("TipologiaSoggetto",
					context.getRequest().getParameter("tipologiaSoggetto"));

			if (context.getRequest().getParameter("idLineaProduttiva1") != null) {
				LineaProduttiva lp = new LineaProduttiva(db,
						new Integer(context.getRequest().getParameter(
								"idLineaProduttiva1")));
				context.getRequest().setAttribute("LineaProduttiva", lp);
			}

			ComuniAnagrafica c = new ComuniAnagrafica();
			//UserBean user = (UserBean) context.getSession().getAttribute("User");
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList(db, user.getSiteId(),
					ComuniAnagrafica.IN_REGIONE);

			LookupList comuniList = new LookupList(listaComuni, -1);

			comuniList.addItem(-1, "Seleziona comune");
			context.getRequest().setAttribute("ComuniList", comuniList);




			// reset the offset and current letter of the paged list in order to
			// make sure we search ALL accounts
			PagedListInfo orgListInfo = this.getPagedListInfo(context,
					"SearchOrgListInfo");
			orgListInfo.setCurrentLetter("");
			orgListInfo.setCurrentOffset(0);

			LookupList lineeProduttive = new LookupList(db,
					"opu_lookup_attivita_linee_produttive_aggregazioni");
			context.getRequest().setAttribute("LineeProduttiveList",
					lineeProduttive);
			// buildFormElements(context, db);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Search Accounts", "Accounts Search");

		return getReturn(context, "Search");

	}


	public String executeCommandSearch(ActionContext context) {
		UserBean user = (UserBean) context.getSession().getAttribute("User");
		OperatoreList operatoreList = new OperatoreList();
		String source = (String) context.getRequest().getParameter("source");

		String partitaIva = (String) context.getRequest().getParameter(
				"PartitaIva");
		String codiceF = (String) context.getRequest().getParameter(
				"CodiceFiscale");
		String ragione_sociale = (String) context.getRequest().getParameter(
				"RagioneSociale");
		String comune = (String) context.getRequest().getParameter(
				"comune");
		context.getRequest().setAttribute("TipologiaSoggetto",
				context.getRequest().getParameter("tipologiaSoggetto"));

		// organizationList.setTipologia(1);

		if (!"".equals(partitaIva) && partitaIva != null) {
			operatoreList.setPartitaIva(partitaIva);
		}
		if (!"".equals(codiceF) && codiceF != null) {
			operatoreList.setCodiceFiscale(codiceF);
		}

		if (ragione_sociale != null && !"".equals(ragione_sociale)) {
			operatoreList.setRagioneSociale(ragione_sociale);
		}

		if (comune != null && !"".equals(comune)) {
			operatoreList.setComune(comune);
		}

		addModuleBean(context, "View Accounts", "Search Results");

		// Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context,"SearchOrgListInfo");
		searchListInfo.setLink(context.getAction().getActionName()+".do?command=Search"+ RequestUtils.addLinkParams(context.getRequest(), "popup|popupType|actionId"));
		searchListInfo.setListView("all");

		Connection db = null;
		try {
			db = this.getConnection(context);

			if ((searchListInfo.getListView() == null || ""
					.equals(searchListInfo.getListView()))
					&& !"searchForm".equals(source)) {
				return "ListOK";
			}


			operatoreList.setPagedListInfo(searchListInfo);
			searchListInfo.setSearchCriteria(operatoreList, context);

			if (context.getRequest().getParameter("doContinue") != null
					&& !context.getRequest().getParameter("doContinue")
					.equals("")
					&& context.getRequest().getParameter("doContinue")
					.equals("false")) {

				return executeCommandSearchForm(context);
			}

			operatoreList.buildList(db);
			context.getRequest().setAttribute("OrgList", operatoreList);


			LookupList siteList = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("SiteIdList", siteList);

			LookupList tipologiaList = new LookupList(db, "opu_lookup_attivita_linee_produttive_aggregazioni");
			context.getRequest().setAttribute("tipologiaList",tipologiaList);

			Operatore operatore = new Operatore();

			operatore.setAction(context.getAction().getActionName());
			context.getRequest().setAttribute("Operatore", operatore);



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





	public String executeCommandViewDia(ActionContext context) {


		int idOp = Integer.parseInt(context.getParameter("opId"));


		OperatoreList operatoreList = new OperatoreList();
		operatoreList.setIdOperatore(idOp);

		// Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context,
				"SearchOrgListInfo");
		searchListInfo.setLink("OperatoreAction.do?command=ViewDia&opId="+idOp);
		searchListInfo.setListView("all");
		// Need to reset any sub PagedListInfos since this is a new account
		this.resetPagedListInfo(context);
		Connection db = null;
		try {
			db = this.getConnection(context);
			Operatore operatoreCorrente = new Operatore();
			operatoreCorrente.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());

			operatoreCorrente.queryRecordOperatore(db, idOp);
			context.getRequest().setAttribute("Operatore", operatoreCorrente);
			operatoreList.setPagedListInfo(searchListInfo);
			searchListInfo.setSearchCriteria(operatoreList, context);
			operatoreList.setFlag_dia(true);
			operatoreList.buildList(db);
			context.getRequest().setAttribute("OrgList", operatoreList);


			LookupList siteList = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("SiteIdList", siteList);




			return getReturn(context, "ViewDia");

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	}


	public String executeCommandViewRaggroppamenti(ActionContext context) {


		int idOp = Integer.parseInt(context.getParameter("opId"));


		OrganizationList operatoreList = new OrganizationList();

		// Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context,
				"SearchOrgListInfo");
		searchListInfo.setLink("OperatoreAction.do?command=ViewRaggroppamenti&opId="+idOp);
		searchListInfo.setListView("all");
		// Need to reset any sub PagedListInfos since this is a new account
		this.resetPagedListInfo(context);
		Connection db = null;
		try {
			db = this.getConnection(context);

			operatoreList.setPagedListInfo(searchListInfo);
			searchListInfo.setSearchCriteria(operatoreList, context);
			operatoreList.buildListRaggruppamentiOperatoreUnico(db, idOp);
			context.getRequest().setAttribute("OrgList", operatoreList);


			LookupList siteList = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("SiteIdList", siteList);




			return getReturn(context, "ViewRaggruppamentiOpu");

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
	}


	public String executeCommandModify(ActionContext context) {

		Connection db = null;
		Operatore newOperatore = null;
		Stabilimento newStabilimento = null;
		try {

			String tempOpId = context.getRequest().getParameter("opId");
			if (tempOpId == null) {
				tempOpId = (String)(""+ context.getRequest().getAttribute("opId"));
			}

			Integer tempid = null;

			if (tempOpId != null) {
				tempid = Integer.parseInt(tempOpId);
			}

			String tempStabId = context.getRequest().getParameter("stabId");
			if (tempStabId == null) {
				tempStabId = ""
						+ (Integer) context.getRequest().getAttribute("idStab");
			}
			// String iter = context.getRequest().getParameter("tipo");

			Integer stabid = null;

			//			if (tempOpId != null) {
			//				tempid = Integer.parseInt(tempOpId);
			//			}

			if (tempStabId != null) {
				stabid = Integer.parseInt(tempStabId);
			}

			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			db = this.getConnection(context);

			newOperatore = new Operatore();
			newOperatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());

			newOperatore.queryRecordOperatore(db, tempid);
			context.getRequest().setAttribute("OperatoreDettagli", newOperatore);


			newStabilimento = new Stabilimento(db,  stabid);

			newStabilimento.getPrefissoAction(context.getAction().getActionName());
			context.getRequest().setAttribute("StabilimentoDettaglio",
					newStabilimento);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,((UserBean) context.getSession().getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);

			int tipoVariazione = Integer.parseInt(context.getParameter("tipoVariazione"));
			String variazione = "" ;
			switch(tipoVariazione)
			{
			case 1 :	/*Sede Legale*/
			{
				variazione = "SedeLegale" ;
				break ;
			}
			case 2 :	/*Soggetto fisico*/
			{
				variazione = "SoggettoFisico" ;
				break ;
			}
			case 3 :	/*Dati Impresa*/
			{
				variazione = "Dati" ;
				break ;
			}

			}




			return getReturn(context, "Modify"+variazione);
			// return "DetailsOperatoreOK";

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

		Connection db = null;
		Operatore newOperatore = null;
		try {

			String tempOpId = context.getRequest().getParameter("opId");
			if (tempOpId == null) {
				tempOpId = (String)(""+ context.getRequest().getAttribute("opId"));
			}

			Integer tempid = null;

			if (tempOpId != null) {
				tempid = Integer.parseInt(tempOpId);
			}
			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			db = this.getConnection(context);

			newOperatore = new Operatore();
			newOperatore.getListaStabilimenti().setIdAsl(((UserBean)context.getRequest().getSession().getAttribute("User")).getSiteId());

			newOperatore.queryRecordOperatore(db, tempid);
			context.getRequest().setAttribute("OperatoreDettagli", newOperatore);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,((UserBean) context.getSession().getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);


			return getReturn(context, "Details");
			// return "DetailsOperatoreOK";

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}
	private void resetPagedListInfo(ActionContext context) {
		this.deletePagedListInfo(context, "SearchOrgListInfo");
		this.deletePagedListInfo(context, "AccountFolderInfo");
		this.deletePagedListInfo(context, "RptListInfo");
		this.deletePagedListInfo(context, "OpportunityPagedInfo");
		this.deletePagedListInfo(context, "AccountTicketInfo");
		this.deletePagedListInfo(context, "AutoGuideAccountInfo");
		this.deletePagedListInfo(context, "RevenueListInfo");
		this.deletePagedListInfo(context, "AccountDocumentInfo");
		this.deletePagedListInfo(context, "ServiceContractListInfo");
		this.deletePagedListInfo(context, "AssetListInfo");
		this.deletePagedListInfo(context, "AccountProjectInfo");
		this.deletePagedListInfo(context, "orgHistoryListInfo");


	}



	public String executeCommandVerificaEsistenza(ActionContext context,Connection db)throws SQLException,Exception,OperatoreInsertEsception {


		boolean recordInserted = false;
		boolean isValid = false;

		Operatore newOperatore = (Operatore) context.getRequest().getAttribute("Operatore");


		try {

			



			LookupList nazioniList= new LookupList(db,"lookup_nazioni");
			SoggettoFisico soggettoAdded = null;
			soggettoAdded = new SoggettoFisico(db, context.getRequest(),nazioniList);



			soggettoAdded.setEnteredBy(getUserId(context));
			soggettoAdded.setModifiedBy(getUserId(context));
			newOperatore.setRappLegale(soggettoAdded);

			newOperatore.setRagioneSociale(context.getRequest().getParameter("ragioneSociale"));
			newOperatore.setPartitaIva(context.getRequest().getParameter("partitaIva"));
			newOperatore.setCodFiscale(context.getRequest().getParameter("codFiscale"));


			/**
			 * CONTROLLO DI ESISTENZA DELL'OPERATORE PER PARTITA IVA
			 */
			List<Operatore> listaOp = newOperatore.checkEsistenzaOperatoreSuap(db,context.getParameter("methodRequest"));

			int esitoCompare=-1 ;

			Operatore operatoreTrovato = null ;
			for(Operatore operatoreEsistente : listaOp)
			{
				esitoCompare = newOperatore.compareTo(operatoreEsistente);
				if (esitoCompare==0)
				{
					operatoreTrovato = operatoreEsistente;
					break;
				}


			}


			switch(esitoCompare)
			{
			case -1 :
			{
				
				String importOp = context.getRequest().getParameter("importOp");
//									if (newOperatore.getPartitaIva()!= null && !"".equals(newOperatore.getPartitaIva()) && importOp  ==null)
//									{
//									String checkOrganization = "select org_id,lt.description  from organization o left join lookup_tipologia_operatore lt  on lt.code=o.tipologia where trim(o.partita_iva) ilike ? and o.trashed_date is null";
//									PreparedStatement  pst = db.prepareStatement(checkOrganization);
//									pst.setString(1, newOperatore.getPartitaIva());
//									ResultSet rs = pst.executeQuery();
//									if (rs.next())
//									{
//										return "organization";
//									}
//									}

				return "-1" ;// OPERATORE NON ESISTE IN BANCA DATI


			}
			default  :
			{
				context.getRequest().setAttribute("ListaOperatori", listaOp);
				System.out.println("################a'a'a'a'OPERATORE CON PARTITA IVA TROVATO MA NON CIRRISPONDENTE ");
				context.getRequest().setAttribute("Operatore", newOperatore);
				return "2" ;// Inserimento bloccato chiedere all'utente la scelta da fare
			}
			

			
			}




		} catch (SQLException errorMessage) 
		{
			context.getRequest().setAttribute("Error", errorMessage);
			return "-1";
		}


	}




	public String executeCommandInsertSuap(ActionContext context,Connection db)throws SQLException,IndirizzoNotFoundException, OperatoreInsertEsception,Exception {


		boolean recordInserted = false;
		boolean isValid = false;

		Operatore newOperatore = (Operatore) context.getRequest().getAttribute("Operatore");

		
		newOperatore.setNote(context.getRequest().getParameter("noteImpresa"));
		newOperatore.setEnteredBy(getUserId(context));
		newOperatore.setModifiedBy(getUserId(context));
		
		System.out.println("#######################OPERATORE ACTION SUAP #################################### INIZIO");

		UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
		try {


			LookupList nazioniList= new LookupList(db,"lookup_nazioni");

			/*Costruzione dell'operatore da inserire*/
			SoggettoFisico soggettoAdded = null;

			soggettoAdded = new SoggettoFisico(db, context.getRequest(),nazioniList);

			SoggettoFisico soggettoEsistente = null;
			
			System.out.println("#######################OPERATORE ACTION SUAP #################################### INIZIO VERIFICA SOGGETTO FISICO");
			soggettoEsistente = soggettoAdded.verificaSoggetto(db);
			
			System.out.println("#######################OPERATORE ACTION SUAP #################################### FINE VERIFICA SOGGETTO FISICO");

			/* se il soggetto non esiste lo aggiungo */

			if (soggettoEsistente == null|| soggettoEsistente.getIdSoggetto() <= 0) 
			{
				soggettoAdded.setEnteredBy(getUserId(context));
				soggettoAdded.setModifiedBy(getUserId(context));
				System.out.println("#######################OPERATORE ACTION SUAP #################################### INIZIO INSERIMENTO SOGGETTO FISICO ");

				soggettoAdded.insert(db,context);
				System.out.println("#######################OPERATORE ACTION SUAP #################################### FINE INSERIMENTO SOGGETTO FISICO ");
				newOperatore.setRappLegale(soggettoAdded);

			} else 
			{
				/* se esiste */

				if (soggettoEsistente.getIdSoggetto() > 0) 
				{
					
					soggettoAdded.getIndirizzo().insert(db, context);
					
					if(soggettoEsistente.getIndirizzo().getIdIndirizzo()<0 || ( soggettoEsistente.getIndirizzo().getComune()<=0 && soggettoEsistente.getIndirizzo().getIdIndirizzo()>0))
					{
						PreparedStatement pst = db.prepareStatement("update opu_soggetto_fisico set indirizzo_id =? where codice_fiscale ilike ? ");
						pst.setInt(1, soggettoEsistente.getIndirizzo().getIdIndirizzo());
						pst.setString(2,soggettoEsistente.getCodFiscale() );
						pst.execute();
					}
					
					Indirizzo indirizzoAdded = soggettoAdded.getIndirizzo();
					soggettoEsistente.setNome(soggettoAdded.getNome());
					soggettoEsistente.setCognome(soggettoAdded.getCognome());
					soggettoEsistente.setDataNascita(soggettoAdded.getDataNascita());
					soggettoEsistente.setComuneNascita(soggettoAdded.getComuneNascita());
					soggettoEsistente.setDocumentoIdentita(soggettoAdded.getDocumentoIdentita());
					soggettoEsistente.setFax(soggettoAdded.getFax());
					soggettoEsistente.setProvinciaNascita(soggettoAdded.getProvinciaNascita());
					soggettoEsistente.setTelefono1(soggettoAdded.getTelefono1());
					soggettoEsistente.setTelefono2(soggettoAdded.getTelefono2());
					soggettoEsistente.setEmail(soggettoAdded.getEmail());
					soggettoEsistente.setModifiedBy(user.getUserId());
					soggettoEsistente.setIpModifiedBy(user.getUserRecord().getIp());
					soggettoEsistente.setEnteredBy(getUserId(context));
					soggettoEsistente.setModifiedBy(getUserId(context));
					soggettoEsistente.setIndirizzo(indirizzoAdded);

					newOperatore.setRappLegale(soggettoEsistente);
//					soggettoEsistente.updateSoloIndirizzo(db);
				}
			}

			Indirizzo indirizzoAdded = null;
			
				indirizzoAdded = new Indirizzo(context.getRequest(),nazioniList, db,context);
				indirizzoAdded.setTipologiaSede(1);

			
			newOperatore.getListaSediOperatore().add(indirizzoAdded);
			if (!"".equals(context.getRequest().getParameter("tipo_impresa")))
				newOperatore.setTipo_impresa(Integer.parseInt(context.getRequest().getParameter("tipo_impresa")));
			if (!"".equals(context.getParameter("tipo_societa")))
				newOperatore.setTipo_societa(Integer.parseInt(context.getParameter("tipo_societa")));


			if (newOperatore.getTipo_impresa()==1)
			{
				indirizzoAdded = newOperatore.getRappLegale().getIndirizzo();
				SedeList listaSedi = new SedeList();
				listaSedi.add(indirizzoAdded);
				newOperatore.setListaSediOperatore(listaSedi);
				
			}
			
				
			
			/**
			 * CONTROLLO DI ESISTENZA DELL'OPERATORE PER PARTITA IVA
			 */
			System.out.println("#######################OPERATORE ACTION SUAP #################################### INIZIO CHECK ESISTENZA OPERATORE SUAP ");

			if(context.getParameter("idStabilimento")!=null && !"".equalsIgnoreCase(context.getParameter("idStabilimento")))
			{
			int idStabilimento = Integer.parseInt(context.getParameter("idStabilimento"));
			if(idStabilimento>0)
			{
				Stabilimento st = new Stabilimento(db, idStabilimento);
				Operatore op = st.getOperatore();
				if(op.getSedeLegale()!=null && ( ( op.getSedeLegale().getIdIndirizzo()<=0 && newOperatore.getSedeLegale().getIdIndirizzo()>0) ||op.getSedeLegale().getComune()<=0))
				{
					SedeList listaSedi = new SedeList();
					listaSedi.add(newOperatore.getSedeLegale());
					op.setListaSediOperatore(listaSedi);
					op.updateSedeLegale(db, context);
				}
			
				context.getRequest().setAttribute("Operatore", op);

				
				
				return "1" ;
			}
			}
			
			
			List<Operatore> listaOp = newOperatore.checkEsistenzaOperatoreSuap(db,context.getParameter("methodRequest"));
			
			System.out.println("#######################OPERATORE ACTION SUAP #################################### FINE CHECK ESISTENZA OPERATORE SUAP ");

			int esitoCompare=-1 ;

			Operatore operatoreTrovato = null ;
			for(Operatore operatoreEsistente : listaOp)
			{
				esitoCompare = newOperatore.compareTo(operatoreEsistente);
				if (esitoCompare==0)
				{
					operatoreTrovato = operatoreEsistente;
					break;
				}


			}
			
			if ((context.getParameter("methodRequest").equalsIgnoreCase("cambiotitolarita")&& newOperatore.getIdOperatore()<0) || newOperatore.getTipo_impresa()==3)
			{
				esitoCompare=-1;
			}

			switch(esitoCompare)
			{
			case -1 :
			{
				
				isValid = this.validateObject(context, db, newOperatore);

				if (isValid) 
				{
					System.out.println("#######################OPERATORE ACTION SUAP #################################### INIZIO INSERIMENTO IMPRESA ");
					
					recordInserted = newOperatore.insert(db,context);
					System.out.println("#######################OPERATORE ACTION SUAP #################################### FINE INSERIMENTO IMPRESA ");

					
					context.getRequest().setAttribute("opId",newOperatore.getIdOperatore());
				}
				context.getRequest().setAttribute("Operatore", newOperatore);
				return "1" ;// Inserimento OK


			}
			case 0 :
			{
				/*Riuso*/

				newOperatore.setIdOperatore(operatoreTrovato.getIdOperatore());
				context.getRequest().setAttribute("Operatore", newOperatore);
				System.out.println("#######################OPERATORE ACTION SUAP #################################### RITORNA 1 ");

				return "1" ; // Inserimento OK
			}

			case 1 :
			{
				String paramSovrascrivi = context.getParameter("sovrascrivi");
				if(paramSovrascrivi==null)
					paramSovrascrivi="n.d";
				switch(paramSovrascrivi)
				{

				case "si" :
				{

					soggettoEsistente.update(db,context);
					isValid = this.validateObject(context, db, newOperatore);
	
					Operatore op = new Operatore();
					op.queryRecordOperatore(db, Integer.parseInt(context.getRequest().getParameter("idOperatore")));
					context.getRequest().setAttribute("Operatore", op);
					
					System.out.println("#######################OPERATORE ACTION SUAP #################################### RITORNA 1 E COSTRUISCO QUERY RECORD OPERATORE");

					return "1" ;// Inserimento OK
				}
				case "no" :
				{
					/*L'operatore esiste in banca dati e l'utente non vuole sovrascrivere i dati.*/
					Operatore op = new Operatore();
					op.queryRecordOperatore(db, Integer.parseInt(context.getRequest().getParameter("idOperatore")));
					context.getRequest().setAttribute("Operatore", op);
					System.out.println("#######################OPERATORE ACTION SUAP #################################### RITORNA 1 E COSTRUISCO QUERY RECORD OPERATORE");

					return "1" ; // Inserimento OK

				}
				case "n.d" :
				{
					/*la partita iva che si sta inserento corrisponde a un altro operatore presente in banca dati ma con dati diversi
					 * viene sollevata una eccezione e chiesto all'utente se vuole sovrasrivere i dati  presenti o mantenere quelli presenti
					 * i banca dati
					 * */
					context.getRequest().setAttribute("ListaOperatori", listaOp);
					System.out.println("################a'a'a'a'OPERATORE CON PARTITA IVA TROVATO MA NON CIRRISPONDENTE ");
					context.getRequest().setAttribute("Operatore", newOperatore);
					return "2" ;// Inserimento bloccato chiedere all'utente la scelta da fare
				}

				}

				break;
			}
			}



		} catch (SQLException errorMessage) 
		{
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			System.out.println("#######################OPERATORE ACTION SUAP #################################### ERRORE");

			return "-1";
		} 
		System.out.println("#######################OPERATORE ACTION SUAP #################################### FINITA LA CHIAMATA DI OPERATORE ACTION RITORNO A STABILIMENTO ACTION");


		context.getRequest().setAttribute("idOp", newOperatore.getIdOperatore());
		return "";
	}

	public String executeCommandTrovaPartitaIva(ActionContext context, String partitaIva)throws SQLException,Exception,OperatoreInsertEsception {


		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;

		Operatore newOperatore = (Operatore) context.getRequest().getAttribute("Operatore");


		try {

			db = this.getConnection(context);

			LookupList nazioniList= new LookupList(db,"lookup_nazioni");
			SoggettoFisico soggettoAdded = null;
			soggettoAdded = new SoggettoFisico(db,context.getRequest(),nazioniList);

			soggettoAdded.setEnteredBy(getUserId(context));
			soggettoAdded.setModifiedBy(getUserId(context));
			newOperatore.setRappLegale(soggettoAdded);

			newOperatore.setPartitaIva(partitaIva);

			List<Operatore> listaOp = newOperatore.checkOperatorePartitaIva(db);

//				listaOp = new ArrayList<Operatore>();
//				listaOp.add(newOperatore);
				context.getRequest().setAttribute("ListaOperatori", listaOp);
				context.getRequest().setAttribute("idOp", newOperatore.getIdOperatore());
				return "2" ; // OPERATORE ESISTE E I DATI COINCIDONO CON QUELLI IN BANCA DATI VIENE PROPOSTO ALL'UTENTE
			


		} catch (SQLException errorMessage) 
		{
			context.getRequest().setAttribute("Error", errorMessage);
			return "-1";
		} finally {
			this.freeConnection(context, db);
		}

	}

}
