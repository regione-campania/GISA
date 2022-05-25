/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.ricercaunica.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.gestioneanagrafica.base.OggettoPerStorico;
//import org.aspcfs.modules.accounts.base.Organization;
//import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.global_search.base.OrganizationListView;
import org.aspcfs.modules.global_search.base.OrganizationView;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.assets.base.Asset;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.base.Constants;


import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.LineaProduttiva;
import org.aspcfs.modules.opu.base.RicercheAnagraficheTab;
import org.aspcfs.modules.opu.base.StabilimentoList;
import org.aspcfs.modules.ricercaunica.base.RicercaList;
import org.aspcfs.modules.ricercaunica.base.RicercaOpu;
import org.aspcfs.utils.PopolaCombo;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.StateSelect;

import com.darkhorseventures.framework.actions.ActionContext;
//import com.lowagie.text.Cell;

public final class RicercaUnica extends CFSModule {

	Logger logger = Logger.getLogger("MainLogger");


	public String executeCommandDefault( ActionContext context )
	{
		return executeCommandDashboard(context);
	}

	public String executeCommandDashboard(ActionContext context) {

		return (executeCommandSearchForm(context));

	}

	public String executeCommandSearchForm(ActionContext context) {
		//	    if (!(hasPermission(context, "global-search-view"))) {
		//	      return ("PermissionError");
		//	    }
		//	    
		//Bypass search form for portal users

		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;

		try {
			db = getConnection(context);


			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "--Tutti--");
			context.getRequest().setAttribute("SiteList", siteList);

			LookupList ListaStati = new LookupList(db,
					"lookup_stato_lab");
			ListaStati.addItem(-1, "Tutti");
			context.getRequest().setAttribute("ListaStati", ListaStati);

			this.deletePagedListInfo(context, "searchListInfo");
			org.aspcfs.modules.accounts.base.Comuni c = new org.aspcfs.modules.accounts.base.Comuni();
			UserBean user = (UserBean) context.getSession().getAttribute("User");

			//verificare la lista comuni
			ArrayList<org.aspcfs.modules.accounts.base.Comuni> listaComuni = c.buildList(db, user.getSiteId());

			LookupList comuniList = new LookupList(listaComuni);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);

			String tipoOp = context.getParameter("tipoOperazione");
			context.getRequest().setAttribute("tipoOperazione",  context.getParameter("tipoOperazione"));

			if(tipoOp!=null)
			{
				switch (Integer.parseInt(tipoOp)) {
				case 1:
					/*Spostamento controlli*/
					if (context.getParameter("rifId")!=null)
					{
						int rifId = Integer.parseInt(context.getParameter("rifId"));
						String rifIdNome = context.getParameter("rifIdNome");

						RicercaOpu r1 = new RicercaOpu(db,rifId,rifIdNome);
						context.getRequest().setAttribute("AnagraficaPartenza", r1);

					}
					break;

				default:
					break;
				}

			}





		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {	
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Search Accounts", "Accounts Search");
		if (context.getParameter("Popup")!=null)
		{
			context.getRequest().setAttribute("Popup", context.getParameter("Popup"));
			return "SearchPopupOK";
		}
		return ("SearchOK");

	}



	private void convergiAnagraficheTraduzioneMasterList(RicercaOpu rOrigine,RicercaOpu rDestinazione,ActionContext context,Connection db) throws Exception
	{
		
		if(rOrigine.getTipologia()!=999 && rDestinazione.getTipologia()==999)
		{
			
			ArrayList<LineaProduttiva> listaLineeMasterList = new ArrayList<LineaProduttiva>();
			
			ArrayList<LineaProduttiva> lpListOld = rOrigine.getListaControlliPerLinea();
			for(LineaProduttiva lp : lpListOld)
			{
				String  candidatoScelto = context.getParameter("candidato-"+lp.getId_rel_stab_lp());
				
				if(candidatoScelto!=null && !"".equals(candidatoScelto))
				{
					
					
					String levelSql = "select * from ml8_linee_attivita_nuove_materializzata where id_padre=?";
					PreparedStatement  pst = db.prepareStatement(levelSql);
					pst.setInt(1, Integer.parseInt(candidatoScelto));
					ResultSet rs = pst.executeQuery();
					if(rs.next())
					{
						throw new Exception("	");
					}
					LineaProduttiva lineaMl = new LineaProduttiva();	
					lineaMl.setId(candidatoScelto);
					lineaMl.setId_rel_stab_lp(lp.getId_rel_stab_lp());
					lineaMl.setNumeroControlliUfficialiEseguiti(lp.getNumeroControlliUfficialiEseguiti());
					listaLineeMasterList.add(lineaMl);
				}
				else
				{
					throw new Exception("CONTROLLARE DI AVER SCELTO UN CANDIDATO PER OGNI ATTIVITA DI ORIGINE");
				}
				
			}
			rOrigine.setListaControlliPerLinea(listaLineeMasterList);
		}
	}
	
	private void convergiPraticheGins(Connection db, int rifIdOrigine, int rifIdDestinazione, int userid) {
		
		try {	
			String sql = "select * from convergi_pratiche_gins(?,?,?)";
			PreparedStatement pst;
			pst = db.prepareStatement(sql);	
			pst.setInt(1, rifIdOrigine);
			pst.setInt(2, rifIdDestinazione);
			pst.setInt(3, userid);
			System.out.println(pst);
			ResultSet rs = pst.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getAltIdConvergenza(Connection db, int id_stab_destinazione){
		
		int alt_id_output = -1;
		
		try {	
			String sql = "select alt_id from opu_stabilimento where id = ?";
			PreparedStatement pst;
			pst = db.prepareStatement(sql);	
			pst.setInt(1, id_stab_destinazione);
			System.out.println(pst);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				alt_id_output = rs.getInt("alt_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return alt_id_output;
	}
	
	public String executeCommandCovergenziAnagrafiche(ActionContext context) {
		//		if (!hasPermission(context, "ricercaunica-view")) {
		//			return ("PermissionError");
		//		}

		
		Connection db = null;
		RicercaOpu rDestinazione = null;
		try {
			db = this.getConnection(context);	 
			
			int rifIdOrigine = Integer.parseInt(context.getParameter("rifIdOrigine"));
			int rifIdDestinazione = Integer.parseInt(context.getParameter("rifIdDestinazione"));
			
			String rifNomeColOrigine= context.getParameter("rifNomeColonnaOrigine");
			String rifNomeColDestinazione = context.getParameter("rifNomeColonnaDestinazione");
			
			//prima della convergenza salvo storico stabilimento destinazione
			int alt_id_destinazione = getAltIdConvergenza(db, rifIdDestinazione);
			OggettoPerStorico stab_pre_modifica = new OggettoPerStorico(alt_id_destinazione, db);
			
			RicercaOpu rOrigine = new RicercaOpu(db, rifIdOrigine,rifNomeColOrigine);
			rDestinazione = new RicercaOpu(db, rifIdDestinazione,rifNomeColDestinazione);
			
			convergiAnagraficheTraduzioneMasterList(rOrigine, rDestinazione, context,db);
			
			PopolaCombo.convergiAnagrafiche(rOrigine, rDestinazione, getUserId(context), "");
			
			//convergenza eventi su osa da stabilimento origine a quello di destinazione ed inserimento finta pratica convergenza
			convergiPraticheGins(db, rifIdOrigine, rifIdDestinazione, getUserId(context));
			
			//
			stab_pre_modifica.salvaStoricoEvento(db,rifIdDestinazione,stab_pre_modifica);
			
			context.getRequest().setAttribute("AnagraficaPartenza", rOrigine);
			context.getRequest().setAttribute("AnagraficaDestinazione", rDestinazione);

			//organizationList.setCodiceFiscale(context.getParameter("searchCodiceFiscale"));

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("SiteIdList", siteList);

			LookupList ListaStati = new LookupList(db,"lookup_stato_lab");
			ListaStati.addItem(-1, "Tutti");
			context.getRequest().setAttribute("ListaStati", ListaStati);

			LookupList tipoOperatore = new LookupList(db, "lookup_tipologia_operatore");
			context.getRequest().setAttribute("tipoOperatore", tipoOperatore);

			context.getRequest().setAttribute("tipoOperazione",  context.getParameter("tipoOperazione"));
			
			// REFRESH ANAGRAFICA MATERIALIZZATA
			RicercheAnagraficheTab.inserOpu(db, rifIdOrigine);
			RicercheAnagraficheTab.inserOpu(db, rifIdDestinazione);
	
			rDestinazione.setMsgConvergenza("CONVERGENZA ANAGRAFICHE ESEGUITA CON SUCCESSO");
			rDestinazione.setEsitoConvergenza(1);
			context.getRequest().setAttribute("AnagraficaDestinazione", rDestinazione);
			return "ConvergiControlliJsonOK";



		} catch (Exception e) {
			//Go through the SystemError process
			if(e.getMessage().startsWith("CONTROLLARE"))
				rDestinazione.setMsgConvergenza("ATTENZIONE : \n "+e.getMessage());
			else
				rDestinazione.setMsgConvergenza("ATTENZIONE! SI E' VERIFICATO UN ERRORE CONTATTARE L'HELP-DESK");
				
			rDestinazione.setEsitoConvergenza(2);
			context.getRequest().setAttribute("AnagraficaDestinazione", rDestinazione);
			return "ConvergiControlliJsonOK";
		} finally {
			this.freeConnection(context, db);
		}


	}
	

	public String executeCommandSceltaDestinazioneCovergenza(ActionContext context) {
		//		if (!hasPermission(context, "ricercaunica-view")) {
		//			return ("PermissionError");
		//		}

		
		Connection db = null;
		try {
			db = this.getConnection(context);	 
			
			int rifIdOrigine = Integer.parseInt(context.getParameter("rifIdOrigine"));
			int rifIdDestinazione = Integer.parseInt(context.getParameter("rifIdDestinazione"));
			
			String rifNomeColOrigine= context.getParameter("rifNomeColonnaOrigine");
			String rifNomeColDestinazione = context.getParameter("rifNomeColonnaDestinazione");
			
			
		
			
			RicercaOpu rOrigine = new RicercaOpu(db, rifIdOrigine,rifNomeColOrigine);
			RicercaOpu rDestinazione = new RicercaOpu(db, rifIdDestinazione,rifNomeColDestinazione);
			
			context.getRequest().setAttribute("AnagraficaPartenza", rOrigine);
			context.getRequest().setAttribute("AnagraficaDestinazione", rDestinazione);

			//organizationList.setCodiceFiscale(context.getParameter("searchCodiceFiscale"));

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("SiteIdList", siteList);

			LookupList ListaStati = new LookupList(db,"lookup_stato_lab");
			ListaStati.addItem(-1, "Tutti");
			context.getRequest().setAttribute("ListaStati", ListaStati);

			LookupList tipoOperatore = new LookupList(db, "lookup_tipologia_operatore");
			context.getRequest().setAttribute("tipoOperatore", tipoOperatore);

			context.getRequest().setAttribute("tipoOperazione",  context.getParameter("tipoOperazione"));

			return "ConvergiControlliOK";



		} catch (Exception e) {
			//Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}



	}
	
	
	
	
	
	
	
	
	




	public String executeCommandSearch(ActionContext context) {
		//		if (!hasPermission(context, "ricercaunica-view")) {
		//			return ("PermissionError");
		//		}



		RicercaList organizationList = new RicercaList();
		//Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context, "SearchOpuListInfo");

		if (context.getRequest().getRequestURI().contains("Suap"))
			searchListInfo.setLink("RicercaUnicaSuap.do?command=Search");
		else		
		{

			if (context.getParameter("tipoOperaione")!=null)
				searchListInfo.setLink("RicercaUnica.do?command=Search&tipoOperaione="+context.getParameter("tipoOperaione"));
			else
			{
				searchListInfo.setLink("RicercaUnica.do?command=Search");
			}

		}
		Connection db = null;
		try {
			db = this.getConnection(context);	      
//			organizationList.cessazioneAutomaticaAttivitaTemporane(db);
			
			searchListInfo.setSearchCriteria(organizationList, context);     
			organizationList.setPagedListInfo(searchListInfo);
			//	organizationList.setEscludiInDomanda(true);
			//	organizationList.setEscludiRespinti(true);

			//			String idAsl = context.getRequest().getParameter("searchaslSedeProduttiva");
			//			organizationList.setIdAsl(idAsl);
			//			
			//			String tipoRicerca = context.getRequest().getParameter("tipoRicerca");
			//			organizationList.setTipoRicerca(tipoRicerca);
//			organizationList.setTarga(context.getRequest().getParameter("targa"));
			
			
			
			String tipoOp = context.getParameter("tipoOperazione");
			if(tipoOp!=null)
			{
				organizationList.setRicercaUnicaSpostamentoCu(true);
			}
			organizationList.buildList(db);
			



			//organizationList.setCodiceFiscale(context.getParameter("searchCodiceFiscale"));

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("SiteIdList", siteList);

			LookupList ListaStati = new LookupList(db,"lookup_stato_lab");
			ListaStati.addItem(-1, "Tutti");
			context.getRequest().setAttribute("ListaStati", ListaStati);



			LookupList tipoOperatore = new LookupList(db, "lookup_tipologia_operatore");
			context.getRequest().setAttribute("tipoOperatore", tipoOperatore);

			context.getRequest().setAttribute("tipoOperazione",  context.getParameter("tipoOperazione"));


			String pageOperazionePregfisso = "";
			if(tipoOp!=null)
			{
				switch (Integer.parseInt(tipoOp)) {
				case 1:
					/*Spostamento controlli*/

					if (context.getParameter("rifId")!=null)
					{
						int rifId = Integer.parseInt(context.getParameter("rifId"));
						String rifIdNome = context.getParameter("rifIdNome");

						
						RicercaOpu anagraficaPartenza = new RicercaOpu(db, rifId, rifIdNome);
							context.getRequest().setAttribute("AnagraficaPartenza", anagraficaPartenza);
						
						
						
					}


					pageOperazionePregfisso="RisultatiRicercaSpostaControlli";
					break;

				default:
					break;
				}

			}
			context.getRequest().setAttribute("StabilimentiList", organizationList);
			
			if(context.getParameter("modalita")!=null && context.getParameter("modalita").equals("json"))
				return pageOperazionePregfisso+"ListPopupJsonOK";

			if (context.getParameter("Popup")!=null)
				return pageOperazionePregfisso+"ListPopupOK";

			return pageOperazionePregfisso+"ListOK";



		} catch (Exception e) {
			//Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}



	}


	private void resetPagedListInfo(ActionContext context) {

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

}