/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package com.aspcfs.modules.aziendezootecniche.actions;

import it.izs.bdn.bean.InfoAllevamentoBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.aspcf.modules.controlliufficiali.base.AziendeZootFields;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Comuni;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.allevamenti.base.AllevamentoAjax;
import org.aspcfs.modules.allevamenti.base.Organization;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.gestioneml.base.SuapMasterListMacroareaList;
import org.aspcfs.modules.gestioneml.util.MasterListImportExcel;
import org.aspcfs.modules.gestioneml.util.MasterListImportRiga;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.modules.opu.base.Indirizzo;
import org.aspcfs.modules.opu.base.RicercheAnagraficheTab;
import org.aspcfs.modules.opu.base.SoggettoFisico;
import org.aspcfs.modules.ricercaunica.base.RicercaList;
import org.aspcfs.modules.ricercaunica.base.RicercaOpu;
import org.aspcfs.utils.AjaxCalls;
import org.aspcfs.utils.Utility;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.StateSelect;

import com.aspcfs.modules.aziendezootecniche.base.BDNDecodificheUtil;
import com.aspcfs.modules.aziendezootecniche.base.Impresa;
import com.aspcfs.modules.aziendezootecniche.base.IstanzaAllevamentoBdn;
import com.aspcfs.modules.aziendezootecniche.base.IstanzaProduttiva;
import com.aspcfs.modules.aziendezootecniche.base.NotFoundDecidificaException;
import com.aspcfs.modules.aziendezootecniche.base.Stabilimento;
import com.darkhorseventures.framework.actions.ActionContext;

public class AllevamentoAction extends CFSModule {
	
	public String executeCommandDashboard(ActionContext context) {
		if (!hasPermission(context, "allevamenti-dashboard-view")) {
			if (!hasPermission(context, "allevamenti-allevamenti-view")) {
				return ("PermissionError");
			}
			
			return (executeCommandSearchForm(context));
		}
	
		return ("DashboardOK");
	}
	
	
	public String executeCommandSincronizzaConBDN(ActionContext context) throws ParseException {



		String codAzienda = context.getRequest().getParameter("codAzienda");
		String idFiscale = context.getRequest().getParameter("idFiscale");
		String specie = context.getRequest().getParameter("specie");
		String denominazione = context.getRequest().getParameter("denominazione");


		Connection db = null;
		try
		{
			db = this.getConnection(context);

			AjaxCalls call = new AjaxCalls();
			InfoAllevamentoBean allevamento = null ;

			if (!specie.equals("0131") && ! specie.equals("0146"))
			{
				allevamento =call.getInfoAllevamento(codAzienda, idFiscale, specie,db);
			}

			else
			{
				ArrayList<InfoAllevamentoBean> listaAll = call.getAllevamenti(codAzienda, denominazione, specie,db,getUserId(context));
				allevamento = listaAll.get(0);
				allevamento.getOrientamentoProduzioneAvicoli(db, allevamento.getFlag_carne_latte());

			}

			IstanzaProduttiva org = new IstanzaProduttiva(db,Integer.parseInt(context.getParameter("altId")));
			if(allevamento != null )
			{

				Timestamp dataFineAttivita= null ;

				if (allevamento.getData_fine_attivita() != null)
				{
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					dataFineAttivita=new Timestamp (sdf.parse(allevamento.getData_fine_attivita()).getTime());
					org.setDataFineAttivita(dataFineAttivita);
				}

				
				if (context.getRequest().getParameter("dataFineAttivita")!=null && !context.getRequest().getParameter("dataFineAttivita").equals("null"))
					allevamento.setData_fine_attivita(context.getRequest().getParameter("dataFineAttivita"));
				//allevamento.setNum_capi_totali(context.getRequest().getParameter("numeroCapi"));
				allevamento.setSpecie_allevata(context.getRequest().getParameter("specieAllevata"));
				
				
			
				
				allevamento.setModifiedBy(getUserId(context));
				allevamento.sincronizzaBdnSimilOpu(db,org.getAltId(),org.getNumeroCapi());
				


			}
			context.getRequest().setAttribute("altId",org.getAltId());

			context.getRequest().setAttribute("InfoAllevamentoBean",allevamento);
		}
		catch(SQLException e)
		{
			e.printStackTrace();

		}finally
		{
			this.freeConnection(context, db);
		}




		return executeCommandDetails(context);
	}
	
	
	public String executeCommandDetails(ActionContext context) {
		if (!(hasPermission(context, "allevamenti-allevamenti-view"))) {
			return ("PermissionError");
		}

		
		
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = getConnection(context);
			IstanzaProduttiva istanzaAllevamento = new IstanzaProduttiva();
			if(context.getParameter("altId")!=null)
				istanzaAllevamento.queryRecord(db, Integer.parseInt(context.getParameter("altId")));
			else
				istanzaAllevamento.queryRecord(db, (Integer) context.getRequest().getAttribute("altId"));
			
			context.getRequest().setAttribute("Allevamento", istanzaAllevamento);
			
			AziendeZootFields az = new AziendeZootFields();
			az.queryRecordbyOrgId(db, Integer.parseInt(context.getParameter("altId")));

			context.getRequest().setAttribute("AzDetails", az);
			
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}


		return ("DetailsOK");
	}
		
	public String executeCommandSearchForm(ActionContext context) {
		if (!(hasPermission(context, "allevamenti-allevamenti-view"))) {
			return ("PermissionError");
		}

		/* if (!(hasPermission(context, "request-view"))) {
        return ("PermissionError");
      }
		 */
		
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = getConnection(context);

			
			LookupList ListaStati = new LookupList(db,
					"lookup_stato_lab");
			ListaStati.addItem(-1, "Tutti");
			ListaStati.removeElementByLevel(1);
			ListaStati.removeElementByLevel(3);
			ListaStati.removeElementByLevel(5);
			ListaStati.removeElementByLevel(6);
			ListaStati.removeElementByLevel(7);
			
			context.getRequest().setAttribute("ListaStati", ListaStati);


		
			LookupList TipologiaStruttura = new LookupList(db, "lookup_tipologia_struttura");
			TipologiaStruttura.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipologiaStruttura", TipologiaStruttura);


			LookupList OrientamentoProduttivo = new LookupList(db, "lookup_orientamento_produttivo");
			OrientamentoProduttivo.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("OrientamentoProduttivo", OrientamentoProduttivo);



			LookupList specie = new LookupList(db, "lookup_specie_allevata");
			specie.addItem(-1, "TUTTE LE SPECIE");
			context.getRequest().setAttribute("SpecieAllevata", specie);

			LookupList CategoriaSpecie = new LookupList(db, "lookup_categoria_specie_allevata");
			CategoriaSpecie.addItem(-1, "TUTTI I GRUPPI");
			context.getRequest().setAttribute("CategoriaSpecie", CategoriaSpecie);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
			context.getRequest().setAttribute("SiteList", siteList);

			//tabella modificata


			//reset the offset and current letter of the paged list in order to make sure we search ALL accounts
			PagedListInfo orgListInfo = this.getPagedListInfo(
					context, "SearchOrgListInfo");
			orgListInfo.setCurrentLetter("");
			orgListInfo.setCurrentOffset(0);
			if (orgListInfo.getSearchOptionValue("searchcodeContactCountry") != null && !"-1".equals(orgListInfo.getSearchOptionValue("searchcodeContactCountry"))) {
				StateSelect stateSelect = new StateSelect(systemStatus,orgListInfo.getSearchOptionValue("searchcodeContactCountry"));
				if (orgListInfo.getSearchOptionValue("searchContactOtherState") != null) {
					HashMap map = new HashMap();
					map.put((String)orgListInfo.getSearchOptionValue("searchcodeContactCountry"), (String)orgListInfo.getSearchOptionValue("searchContactOtherState"));
					stateSelect.setPreviousStates(map);
				}
				context.getRequest().setAttribute("ContactStateSelect", stateSelect);
			}
			if (orgListInfo.getSearchOptionValue("searchcodeAccountCountry") != null && !"-1".equals(orgListInfo.getSearchOptionValue("searchcodeAccountCountry"))) {
				StateSelect stateSelect = new StateSelect(systemStatus,orgListInfo.getSearchOptionValue("searchcodeAccountCountry"));
				if (orgListInfo.getSearchOptionValue("searchAccountOtherState") != null) {
					HashMap map = new HashMap();
					map.put((String)orgListInfo.getSearchOptionValue("searchcodeAccountCountry"), (String)orgListInfo.getSearchOptionValue("searchAccountOtherState"));
					stateSelect.setPreviousStates(map);
				}
				context.getRequest().setAttribute("AccountStateSelect", stateSelect);
			}
			String selectMalattie = "SELECT CODE,DESCRIPTION FROM CODICI_MALATTIE_ALLEVAMENTI";

			String selectQualificaSanitaria = "SELECT CODE,DESCRIPTION FROM CODICI_QUALIFICA_SANITARIA_ALLEVAMENTI order by description";
			HashMap<String, String> malattie = new HashMap<String, String>();
			HashMap<String, String> qualifiche = new HashMap<String, String>();

			PreparedStatement pst = db.prepareStatement(selectMalattie) ; 
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				String code = rs.getString(1);
				String descr = rs.getString(2);
				malattie.put(code, descr);
			}

			pst = db.prepareStatement(selectQualificaSanitaria) ; 
			rs = pst.executeQuery();
			while(rs.next())
			{
				String code = rs.getString(1);
				String descr = rs.getString(2);
				qualifiche.put(code, descr);
			}

			context.getRequest().setAttribute("malattie", malattie);
			context.getRequest().setAttribute("qualifiche", qualifiche);

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Search Accounts", "Accounts Search");

		// associazione OSM - azienda zootecnica
		String ricercaAllevamentiAssociabiliParameter = (String)context.getRequest().getParameter("ricercaAllevamentiAssociabiliParameter");
		if (ricercaAllevamentiAssociabiliParameter != null && ricercaAllevamentiAssociabiliParameter.equals("1")) {
			context.getRequest().setAttribute("ricercaAllevamentiAssociabiliAttribute", true);
		}


		return ("SearchOK");
	}
	
	public String executeCommandSearch(ActionContext context) {
		//		if (!hasPermission(context, "ricercaunica-view")) {
		//			return ("PermissionError");
		//		}



		RicercaList organizationList = new RicercaList();
		//Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context, "SearchOpuListInfoAllevamenti");
		searchListInfo.setLink("AziendeZootecniche.do?command=Search");
			

		
		Connection db = null;
		try {
			db = this.getConnection(context);	      
			organizationList.cessazioneAutomaticaAttivitaTemporane(db);
			
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
			organizationList.buildListAllevamenti(db);
			



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
			
			context.getRequest().setAttribute("StabilimentiList", organizationList);
			


			return "ListOK";



		} catch (Exception e) {
			//Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}



	}
	
	public String executeCommandToSearchBdn(ActionContext context)
	{
		
		return "ToSearchBdnOK" ;
	}
	
	public String executeCommandToSearchBoviniBdn(ActionContext context)
	{
		Connection db = null ;
		try
		{
			db = super.getConnection(context);
			LookupList specieList = new LookupList(db," (select * from lookup_specie_allevata where tipo_ws=1) ");
			context.getRequest().setAttribute("SpecieList", specieList);
		}
		catch(SQLException e)
		{
			super.freeConnection(context, db);
		}
		finally
		{
			super.freeConnection(context, db);
		}
		
		return "ToSearchBoviniBdnOK" ;
	}
	
	public String executeCommandToSearchAvicoliBdn(ActionContext context)
	{
		Connection db = null ;
		try
		{
			db = super.getConnection(context);
			LookupList specieList = new LookupList(db," (select * from lookup_specie_allevata where tipo_ws=2) ");
			context.getRequest().setAttribute("SpecieList", specieList);
		}
		catch(SQLException e)
		{
			super.freeConnection(context, db);
		}
		finally
		{
			super.freeConnection(context, db);
		}
		
		return "ToSearchAvicoliBdnOK" ;
	}
	
	
	
	
	
	public String executeCommandGetAllevamentoBDN(ActionContext context) throws ParseException
	{
		
		Connection db = null ;
		String mode = context.getParameter("mode");

		try
		{
			
			db = super.getConnection(context);
			
		String codAzienda = context.getParameter("codAzienda");
		String specie = context.getParameter("specieAzienda");
		String idFiscale = context.getParameter("idFiscaleAzienda");
		IstanzaAllevamentoBdn istanzaBdn = new IstanzaAllevamentoBdn();
		IstanzaAllevamentoBdn istanzaAllevamento = istanzaBdn.getAllevamento(codAzienda, idFiscale, specie);
		LookupList aslList = new LookupList(db,"lookup_site_id");

		if(istanzaAllevamento.getCod_errore()!=0)
		{
			context.getRequest().setAttribute("AslList",aslList);

			context.getRequest().setAttribute("IstanzaAllevamentoBdn",istanzaAllevamento);
			return "GetAllevamentBdnOK" ;

		}
		
		/*DECODIFICA DEI CODICI DELLA BDN*/
		istanzaAllevamento.setIndirizzoAllevamentoDescrizioneComune(BDNDecodificheUtil.getDescrizioneComuneByCodice(db, istanzaAllevamento.getIndirizzoAllevamentoCodComune(), istanzaAllevamento.getIndirizzoAllevamentoSiglaProvincia()));
		istanzaAllevamento.setAziendDescrizioneComune(BDNDecodificheUtil.getDescrizioneComuneByCodice(db,istanzaAllevamento.getAziendaCodiceComune(), BDNDecodificheUtil.getProvinciaByCodiceAzienda(istanzaAllevamento.getAziendaCodice())));
		istanzaAllevamento.setProprietarioIndirizzoDescrizioneComune(BDNDecodificheUtil.getDescrizioneComuneByCodiceECap(db, istanzaAllevamento.getProprietarioIndirizzoCodiceComune(), istanzaAllevamento.getProprietarioIndirizzoCap()));
		istanzaAllevamento.setDetentoreIndirizzoDescrizioneComune(BDNDecodificheUtil.getDescrizioneComuneByCodiceECap(db, istanzaAllevamento.getDetentoreIndirizzoCodiceComune(), istanzaAllevamento.getDetentoreIndirizzoCap()));
		istanzaAllevamento.setIdAsl(BDNDecodificheUtil.getAslByComuneCodiceAzienda(db, istanzaAllevamento.getAziendaCodiceComune(), BDNDecodificheUtil.getProvinciaByCodiceAzienda(istanzaAllevamento.getAziendaCodice())));
		
		
		istanzaAllevamento.setDescrizioneSpecie(BDNDecodificheUtil.getSpecieAllevata(istanzaAllevamento.getCodiceSpecie(), db));
		istanzaAllevamento.setDescrizioneOrientamentoProduttivo(BDNDecodificheUtil.getOrientamentoProduzione(db, istanzaAllevamento.getDescrizioneSpecie(), istanzaAllevamento.getCodiceTipoProduzione(), istanzaAllevamento.getCodiceOrientamentoProduttivo()));
		istanzaAllevamento.setDescrizioneTipoProduzione(BDNDecodificheUtil.getTipologiaProduzione(db, istanzaAllevamento.getDescrizioneSpecie(), istanzaAllevamento.getCodiceTipoProduzione(), istanzaAllevamento.getCodiceOrientamentoProduttivo()));

		SuapMasterListMacroareaList listaMacroarea = new SuapMasterListMacroareaList();
		listaMacroarea.setFlussoOrig(3);
		listaMacroarea.buildList(db);
		context.getRequest().setAttribute("ListaMacroarea", listaMacroarea);
		
		if("sync".equalsIgnoreCase(mode))
		{
			Impresa allevamento = new Impresa();
			
			Indirizzo sedeLegale = new Indirizzo();
			sedeLegale.setVia(istanzaAllevamento.getIndirizzoAllevmanentoVia());
			String comuneTesto = "" ;
			sedeLegale.setComuneTesto(BDNDecodificheUtil.getDescrizioneComuneByCodice(db, istanzaAllevamento.getIndirizzoAllevamentoCodComune(), istanzaAllevamento.getIndirizzoAllevamentoSiglaProvincia()));
			sedeLegale.setCap(istanzaAllevamento.getIndirizzoAllevamentoCap());
			
			Indirizzo residenzaProprietario = new Indirizzo();
			residenzaProprietario.setVia(istanzaAllevamento.getProprietarioIndirizzoVia());
			residenzaProprietario.setComuneTesto(BDNDecodificheUtil.getDescrizioneComuneByCodiceECap(db, istanzaAllevamento.getProprietarioIndirizzoCodiceComune(), istanzaAllevamento.getProprietarioIndirizzoCap()));
			residenzaProprietario.setCap(istanzaAllevamento.getProprietarioIndirizzoCap());
			SoggettoFisico proprietario = new SoggettoFisico();
			proprietario.setNome(istanzaAllevamento.getProprietarioNominativo());
			proprietario.setCodFiscale(istanzaAllevamento.getProprietarioIdFiscale());
			proprietario.setIndirizzo(residenzaProprietario);
			
			
			allevamento.setIdFiscaleAllevamento(istanzaAllevamento.getIdFiscaleAllevamento());
			allevamento.setRagioneSociale(istanzaAllevamento.getDenominazione());
			allevamento.setSedeLegale(sedeLegale);
			allevamento.setProprietario(proprietario);
			
			Stabilimento azienda = new Stabilimento();
			Indirizzo sedeOperativa = new Indirizzo();
			sedeOperativa.setVia(istanzaAllevamento.getProprietarioIndirizzoVia());
			sedeOperativa.setComuneTesto(BDNDecodificheUtil.getDescrizioneComuneByCodice(db, istanzaAllevamento.getAziendaCodiceComune(), BDNDecodificheUtil.getProvinciaByCodiceAzienda(istanzaAllevamento.getAziendaCodice())));
			sedeOperativa.setCap(istanzaAllevamento.getAziendaCap());
			
			azienda.setIdAsl(BDNDecodificheUtil.getAslByComuneCodiceAzienda(db, istanzaAllevamento.getAziendaCodiceComune(), BDNDecodificheUtil.getProvinciaByCodiceAzienda(istanzaAllevamento.getAziendaCodice())));
			azienda.setCodiceAzienda(istanzaAllevamento.getAziendaCodice());
			azienda.setSedeOperativa(sedeOperativa);
			azienda.setDataAperturaAzienda(istanzaAllevamento.getAziendaDataApertura());
			azienda.setDataChiusuraAzienda(istanzaAllevamento.getAziendaDataChiusura());
			
			
			IstanzaProduttiva istanzaProduttiva = new IstanzaProduttiva();
			Indirizzo residenzaDetentore = new Indirizzo();
			residenzaDetentore.setVia(istanzaAllevamento.getDetentoreIndirizzoVia());
			residenzaDetentore.setComuneTesto(BDNDecodificheUtil.getDescrizioneComuneByCodiceECap(db, istanzaAllevamento.getDetentoreIndirizzoCodiceComune(), istanzaAllevamento.getDetentoreIndirizzoCap()));
			residenzaDetentore.setCap(istanzaAllevamento.getDetentoreIndirizzoCap());
			SoggettoFisico detentore = new SoggettoFisico();
			detentore.setNome(istanzaAllevamento.getDetentoreNominativo());
			detentore.setCodFiscale(istanzaAllevamento.getDetentoreIdFiscale());
			detentore.setIndirizzo(residenzaDetentore);
			
			istanzaProduttiva.setAllevamento(allevamento);
			istanzaProduttiva.setAzienda(azienda);
			istanzaProduttiva.setDetentore(detentore);
			istanzaProduttiva.setDataInizioAttivita(istanzaAllevamento.getDataInizioAttivita());
			istanzaProduttiva.setDataFineAttivita(istanzaAllevamento.getDataFineAttivita());
			istanzaProduttiva.setCodiceSpecie(istanzaAllevamento.getCodiceSpecie());
			istanzaProduttiva.setCodiceOrientamentoProduttivo(istanzaAllevamento.getCodiceOrientamentoProduttivo());
			istanzaProduttiva.setCodiceTipologiaStruttura(istanzaAllevamento.getCodiceTipoProduzione());
			istanzaProduttiva.setId(-1);
			istanzaProduttiva.setIdLineaProduttiva(Integer.parseInt(context.getParameter("idLineaProduttiva")));
			
			
			
			/*CONTROLLO DI ESISTENZA IN ORGANIZATION*/
			boolean esisteAllevamento = false;
			int orgId = -1 ;
			PreparedStatement pstEsistenza = db.prepareStatement("select * from get_esistenza_allevamento(?, ?, ?, ?, ?) ");
			pstEsistenza.setString(1, codAzienda);
			pstEsistenza.setString(2, istanzaProduttiva.getAllevamento().getProprietario().getCodFiscale());
			pstEsistenza.setString(3, specie);
			pstEsistenza.setString(4, istanzaProduttiva.getCodiceOrientamentoProduttivo());
			pstEsistenza.setString(5, istanzaAllevamento.getDescrizioneOrientamentoProduttivo());
			ResultSet rsEsistenza = pstEsistenza.executeQuery();
			System.out.println("Esistenza allevamento: "+pstEsistenza.toString());
			if (rsEsistenza.next())
			{
				esisteAllevamento = rsEsistenza.getBoolean(1);
				orgId=rsEsistenza.getInt(2);
			}
			
			
			istanzaProduttiva.insert(db, getUserId(context));
			
			
			if(esisteAllevamento==true)
			{
				
				String sql = "UPDATE ticket set alt_id = ? where org_id = ?";
				
				String sqlDel = "UPDATE organization set trashed_date = current_date ,note_hd = 'IMPORTATO IN SIMIL OPU' where org_id = ?";
				
				
				String sqlDelT = "delete from ricerche_anagrafiche_old_materializzata_allevamenti where riferimento_id = ? and riferimento_id_nome  ilike 'orgid'";

				
				String sqlAzFields = "update aziende_zootecniche_fields set alt_id = ? where id_allevamento = ? ";

				
				
				
				PreparedStatement pst = db.prepareStatement(sql);
				pst.setInt(1, istanzaProduttiva.getAltId());
				pst.setInt(2, orgId);
				pst.execute();
				
				pst = db.prepareStatement(sqlDel);
				pst.setInt(1, orgId);
				pst.execute();
				
				pst = db.prepareStatement(sqlDelT);
				pst.setInt(1, orgId);
				pst.execute();
				
				pst = db.prepareStatement(sqlAzFields);
				pst.setInt(1, istanzaProduttiva.getAltId());
				pst.setInt(2, orgId);
				pst.execute();
				
				
				System.out.println("IMPORTARE TUTTE LE ATTIVITA COLLEGATE ALL'ANAGRAFICA + CANCELLARE DALLA VECCHIA ANAGRAFICA.");
			}
			
			
			RicercheAnagraficheTab.insertAllevamentoNuovoModello(db, istanzaProduttiva.getId());
			context.getRequest().setAttribute("Allevamento", istanzaProduttiva);
			
		}
		
		context.getRequest().setAttribute("AslList",aslList);

		context.getRequest().setAttribute("IstanzaAllevamentoBdn",istanzaAllevamento);
		}
		catch(SQLException e)
		{
			super.freeConnection(context, db);
		}
		finally
		{
			super.freeConnection(context, db);
		}
		if("sync".equalsIgnoreCase(mode))
		{
		return "GetAllevamentBdnGisaOK" ;
		}
		return "GetAllevamentBdnOK";
	}
	
	
	

	
	
	public String executeCommandGetAllevamentoAviBDN(ActionContext context) throws ParseException
	{
		
		Connection db = null ;
		String mode = context.getParameter("mode");

		try
		{
			
			db = super.getConnection(context);
			
		String codAzienda = context.getParameter("codAzienda");
		String specie = context.getParameter("specieAzienda");
		String idFiscale = context.getParameter("idFiscaleAzienda");
		IstanzaAllevamentoBdn istanzaBdn = new IstanzaAllevamentoBdn();
		
		IstanzaAllevamentoBdn istanzaAllevamento = istanzaBdn.getAllevamentoAvi(codAzienda, idFiscale, specie);
		LookupList aslList = new LookupList(db,"lookup_site_id");

		if(istanzaAllevamento.getCod_errore()!=0)
		{
			context.getRequest().setAttribute("AslList",aslList);

			context.getRequest().setAttribute("IstanzaAllevamentoBdn",istanzaAllevamento);
			return "GetAllevamentBdnOK" ;

		}
		
		/*DECODIFICA DEI CODICI DELLA BDN*/
		istanzaAllevamento.setIndirizzoAllevamentoDescrizioneComune(BDNDecodificheUtil.getDescrizioneComuneByCodice(db, istanzaAllevamento.getIndirizzoAllevamentoCodComune(), istanzaAllevamento.getIndirizzoAllevamentoSiglaProvincia()));
		istanzaAllevamento.setAziendDescrizioneComune(BDNDecodificheUtil.getDescrizioneComuneByCodice(db,istanzaAllevamento.getAziendaCodiceComune(), BDNDecodificheUtil.getProvinciaByCodiceAzienda(istanzaAllevamento.getAziendaCodice())));
		istanzaAllevamento.setProprietarioIndirizzoDescrizioneComune(BDNDecodificheUtil.getDescrizioneComuneByCodiceECap(db, istanzaAllevamento.getProprietarioIndirizzoCodiceComune(), istanzaAllevamento.getProprietarioIndirizzoCap()));
		istanzaAllevamento.setDetentoreIndirizzoDescrizioneComune(BDNDecodificheUtil.getDescrizioneComuneByCodiceECap(db, istanzaAllevamento.getDetentoreIndirizzoCodiceComune(), istanzaAllevamento.getDetentoreIndirizzoCap()));
		istanzaAllevamento.setIdAsl(BDNDecodificheUtil.getAslByComuneCodiceAzienda(db, istanzaAllevamento.getAziendaCodiceComune(), BDNDecodificheUtil.getProvinciaByCodiceAzienda(istanzaAllevamento.getAziendaCodice())));
	

		istanzaAllevamento.setDescrizioneSpecie(BDNDecodificheUtil.getSpecieAllevata(istanzaAllevamento.getCodiceSpecie(), db));
		istanzaAllevamento.setDescrizioneOrientamentoProduttivo(BDNDecodificheUtil.getOrientamentoProduzione(db, istanzaAllevamento.getDescrizioneSpecie(), istanzaAllevamento.getCodiceTipoProduzione(), istanzaAllevamento.getCodiceOrientamentoProduttivo()));
		istanzaAllevamento.setDescrizioneTipoProduzione(BDNDecodificheUtil.getTipologiaProduzione(db, istanzaAllevamento.getDescrizioneSpecie(), istanzaAllevamento.getCodiceTipoProduzione(), istanzaAllevamento.getCodiceOrientamentoProduttivo()));

		
		if("sync".equalsIgnoreCase(mode))
		{
			Impresa allevamento = new Impresa();
			
			Indirizzo sedeLegale = new Indirizzo();
			sedeLegale.setVia(istanzaAllevamento.getIndirizzoAllevmanentoVia());
			String comuneTesto = "" ;
			sedeLegale.setComuneTesto(BDNDecodificheUtil.getDescrizioneComuneByCodice(db, istanzaAllevamento.getIndirizzoAllevamentoCodComune(), istanzaAllevamento.getIndirizzoAllevamentoSiglaProvincia()));
			sedeLegale.setCap(istanzaAllevamento.getIndirizzoAllevamentoCap());
			
			Indirizzo residenzaProprietario = new Indirizzo();
			residenzaProprietario.setVia(istanzaAllevamento.getProprietarioIndirizzoVia());
			residenzaProprietario.setComuneTesto(BDNDecodificheUtil.getDescrizioneComuneByCodiceECap(db, istanzaAllevamento.getProprietarioIndirizzoCodiceComune(), istanzaAllevamento.getProprietarioIndirizzoCap()));
			residenzaProprietario.setCap(istanzaAllevamento.getProprietarioIndirizzoCap());
			SoggettoFisico proprietario = new SoggettoFisico();
			proprietario.setNome(istanzaAllevamento.getProprietarioNominativo());
			proprietario.setCodFiscale(istanzaAllevamento.getProprietarioIdFiscale());
			proprietario.setIndirizzo(residenzaProprietario);
			
			
			allevamento.setIdFiscaleAllevamento(istanzaAllevamento.getIdFiscaleAllevamento());
			allevamento.setRagioneSociale(istanzaAllevamento.getDenominazione());
			allevamento.setSedeLegale(sedeLegale);
			allevamento.setProprietario(proprietario);
			
			Stabilimento azienda = new Stabilimento();
			Indirizzo sedeOperativa = new Indirizzo();
			sedeOperativa.setVia(istanzaAllevamento.getProprietarioIndirizzoVia());
			sedeOperativa.setComuneTesto(BDNDecodificheUtil.getDescrizioneComuneByCodice(db, istanzaAllevamento.getAziendaCodiceComune(), BDNDecodificheUtil.getProvinciaByCodiceAzienda(istanzaAllevamento.getAziendaCodice())));
			sedeOperativa.setCap(istanzaAllevamento.getAziendaCap());
			
			azienda.setIdAsl(BDNDecodificheUtil.getAslByComuneCodiceAzienda(db, istanzaAllevamento.getAziendaCodiceComune(), BDNDecodificheUtil.getProvinciaByCodiceAzienda(istanzaAllevamento.getAziendaCodice())));
			azienda.setCodiceAzienda(istanzaAllevamento.getAziendaCodice());
			azienda.setSedeOperativa(sedeOperativa);
			azienda.setDataAperturaAzienda(istanzaAllevamento.getAziendaDataApertura());
			azienda.setDataChiusuraAzienda(istanzaAllevamento.getAziendaDataChiusura());
			
			
			
			
			IstanzaProduttiva istanzaProduttiva = new IstanzaProduttiva();
			Indirizzo residenzaDetentore = new Indirizzo();
			residenzaDetentore.setVia(istanzaAllevamento.getDetentoreIndirizzoVia());
			residenzaDetentore.setComuneTesto(BDNDecodificheUtil.getDescrizioneComuneByCodiceECap(db, istanzaAllevamento.getDetentoreIndirizzoCodiceComune(), istanzaAllevamento.getDetentoreIndirizzoCap()));
			residenzaDetentore.setCap(istanzaAllevamento.getDetentoreIndirizzoCap());
			SoggettoFisico detentore = new SoggettoFisico();
			detentore.setNome(istanzaAllevamento.getDetentoreNominativo());
			detentore.setCodFiscale(istanzaAllevamento.getDetentoreIdFiscale());
			detentore.setIndirizzo(residenzaDetentore);
			
			istanzaProduttiva.setAllevamento(allevamento);
			istanzaProduttiva.setAzienda(azienda);
			istanzaProduttiva.setDetentore(detentore);
			istanzaProduttiva.setDataInizioAttivita(istanzaAllevamento.getDataInizioAttivita());
			istanzaProduttiva.setDataFineAttivita(istanzaAllevamento.getDataFineAttivita());
			istanzaProduttiva.setCodiceSpecie(istanzaAllevamento.getCodiceSpecie());
			istanzaProduttiva.setCodiceOrientamentoProduttivo(istanzaAllevamento.getCodiceOrientamentoProduttivo());
			istanzaProduttiva.setCodiceTipologiaStruttura(istanzaAllevamento.getCodiceTipoProduzione());
			istanzaProduttiva.setId(-1);
			
			istanzaProduttiva.insert(db, getUserId(context));
			
			RicercheAnagraficheTab.insertAllevamentoNuovoModello(db, istanzaProduttiva.getId());
			context.getRequest().setAttribute("Allevamento", istanzaProduttiva);
			
		}
		
		context.getRequest().setAttribute("AslList",aslList);

		context.getRequest().setAttribute("IstanzaAllevamentoBdn",istanzaAllevamento);
		}
		catch(SQLException e)
		{
			super.freeConnection(context, db);
		}
		finally
		{
			super.freeConnection(context, db);
		}
		if("sync".equalsIgnoreCase(mode))
		{
		return "GetAllevamentBdnGisaOK" ;
		}
		return "GetAllevamentBdnOK";
	}
	
	
	public String executeCommandCompareWS(ActionContext context) {
		AllevamentoAjax allevamento = null;
		IstanzaProduttiva OrgDetails = null;
		ArrayList diff = null;
		Connection db = null;
		try {
			db = this.getConnection(context);


			String altId = context.getParameter("altId");
			String codice_azienda = context.getParameter("codiceAzienda");
			String partita_iva = context.getParameter("pIva");
			String codice_specie = context.getParameter("codSpecie");
			String denominazione = context.getParameter("denominazione");


			denominazione.replaceAll("|", "'");

			AjaxCalls ajaxCalls = new AjaxCalls();
			System.out.println("TEST 27/04 1");
			if (!"0131".equals(codice_specie) && ! "0146".equals(codice_specie))
			{
				System.out.println("TEST 27/04 2");
				allevamento = ajaxCalls.getAllevamento(codice_azienda, partita_iva, codice_specie);
				System.out.println("TEST 27/04 3");
			}
			else
			{
				ArrayList<InfoAllevamentoBean> listaAll = ajaxCalls.getAllevamenti(codice_azienda, denominazione, codice_specie,db,getUserId(context));
				if (listaAll.size()>0)
				{
					InfoAllevamentoBean iab = listaAll.get(0);
					iab.getOrientamentoProduzioneAvicoli(db, iab.getFlag_carne_latte());

					SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

					LookupList listaSpeci = new LookupList(db,"lookup_specie_allevata");
					allevamento = new AllevamentoAjax();
					allevamento.setDenominazione( iab.getDenominazione() );
					allevamento.setCodice_azienda( iab.getCodice_azienda() );
					allevamento.setCodice_fiscale(iab.getCod_fiscale());
					allevamento.setIndirizzo(iab.getIndirizzo());
					allevamento.setComune(Utility.getComuneFromCodiceAzienda(iab.getCodice_azienda(), db));
					allevamento.setNote(iab.getNote());
					allevamento.setSpecie_allevata(Integer.parseInt(iab.getSpecie_allevata()));
					allevamento.setDescrizione_specie(listaSpeci.getSelectedValue(allevamento.getSpecie_allevata()));
					allevamento.setCfDetentore(iab.getCod_fiscale_det());
					allevamento.setCfProprietario(iab.getCod_fiscale());

					if (iab.getData_inizio_attivita()!=null)
					{
						String dataInizioAttivita = iab.getData_inizio_attivita().substring(0, 10);
						try{
							allevamento.setData_inizio_attivita( sdf1.format( sdf2.parse(dataInizioAttivita) ) );
						}
						catch (Exception e) {

						}
					}

					if (iab.getData_fine_attivita() != null)
					{
						String dataFineAttivita = iab.getData_fine_attivita().substring(0, 10);
						try{
							allevamento.setData_fine_attivita( sdf1.format( sdf2.parse(dataFineAttivita) ) );
						}
						catch (Exception e) {
							e.printStackTrace();

						}
					}
					allevamento.setInBDN( true );


				}
			}


			 OrgDetails = new IstanzaProduttiva(db,Integer.parseInt(altId));
			context.getRequest().setAttribute("Allevamento", OrgDetails);
			diff = OrgDetails.equals(allevamento);
			Iterator iDiff = diff.iterator();
			while (iDiff.hasNext())
			{
				String differenza = iDiff.next().toString();
			}
		}
		catch (Exception errorMessage){
			errorMessage.printStackTrace();

		}
		finally
		{
			freeConnection(context, db);
		}
		context.getRequest().setAttribute("AllevamentoBDN", allevamento);
		context.getRequest().setAttribute("DiffenzeBDN", diff);
		context.getRequest().setAttribute("Allevamento", OrgDetails);

		return ("CompareWsOK");


	}
	
	public String executeCommandFindAllevamentiBdn(ActionContext context)
	{
		
		Connection db = null ;
		try
		{
			db = super.getConnection(context);
			
		String codAzienda = context.getParameter("codAzienda");
		String specie = context.getParameter("specieAzienda");
		IstanzaAllevamentoBdn istanzaBdn = new IstanzaAllevamentoBdn();
		List<IstanzaAllevamentoBdn> listaAllevamenti = istanzaBdn.findAllevamenti(db,codAzienda, "", "0"+specie);
		context.getRequest().setAttribute("ListaAllevamenti",listaAllevamenti);
		}
		catch(SQLException e)
		{
			super.freeConnection(context, db);
		}
		finally
		{
			super.freeConnection(context, db);
		}
		return "ListaIstanzaBdnOK" ;
	}
	
	
	
	public String executeCommandFindAllevamentiAvicoliBdn(ActionContext context)
	{
		
		Connection db = null ;
		try
		{
			db = super.getConnection(context);
			
		String codAzienda = context.getParameter("codAzienda");
		String specie = context.getParameter("specieAzienda");
		IstanzaAllevamentoBdn istanzaBdn = new IstanzaAllevamentoBdn();
		List<IstanzaAllevamentoBdn> listaAllevamenti = istanzaBdn.findAllevamentiAvi(db,codAzienda, "", "0"+specie);
		context.getRequest().setAttribute("ListaAllevamenti",listaAllevamenti);
		}
		catch(SQLException e)
		{
			super.freeConnection(context, db);
		}
		finally
		{
			super.freeConnection(context, db);
		}
		return "ListaIstanzaAviBdnOK" ;
	}
	public String executeCommandViewVigilanza(ActionContext context) {
		if (!hasPermission(context, "opu-vigilanza-view")) {
			return ("PermissionError");
		}

		Connection db = null;
		org.aspcfs.modules.vigilanza.base.TicketList ticList = new org.aspcfs.modules.vigilanza.base.TicketList();


		String tempAltId = context.getRequest().getParameter("altId");
		if (tempAltId == null) {
			tempAltId = ""
					+ (Integer) context.getRequest().getAttribute("altId");
		}
		// String iter = context.getRequest().getParameter("tipo");
		Integer tempid = null;
		Integer altid = null;



		if (tempAltId != null) {
			altid = Integer.parseInt(tempAltId);
		}
		ticList.setAltId(altid);
		// Prepare PagedListInfo
		PagedListInfo ticketListInfo = this.getPagedListInfo(context,
				"AccountTicketInfo", "t.assigned_date", "desc");
		ticketListInfo.setLink(context.getAction().getActionName()+".do?command=ViewVigilanza&altId="+altid
				);
		ticList.setPagedListInfo(ticketListInfo);
		try {



			db = this.getConnection(context);	




			IstanzaProduttiva istanzaAllevamento = new IstanzaProduttiva();
			istanzaAllevamento.queryRecord(db, Integer.parseInt(context.getParameter("altId")));
			istanzaAllevamento.getPrefissoAction(context.getAction().getActionName());

			context.getRequest().setAttribute("OrgDetails", istanzaAllevamento);

			SystemStatus systemStatus = this.getSystemStatus(context);
			LookupList TipoCampione = new LookupList(db,
					"lookup_tipo_controllo");
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList AuditTipo = new LookupList(db, "lookup_audit_tipo");
			AuditTipo.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AuditTipo", AuditTipo);

			LookupList TipoAudit = new LookupList(db, "lookup_tipo_audit");
			TipoAudit.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoAudit", TipoAudit);

			LookupList TipoIspezione = new LookupList(db,
					"lookup_tipo_ispezione");
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);

			org.aspcfs.modules.vigilanza.base.TicketList controlliList = new org.aspcfs.modules.vigilanza.base.TicketList();
			controlliList.setAltId(istanzaAllevamento.getAltId());
			/*
			 * int punteggioAccumulato =
			 * controlliList.buildListControlliUltimiAnni(db, passedId);
			 * context.getRequest().setAttribute("punteggioUltimiAnni",
			 * punteggioAccumulato);
			 */
			LookupList EsitoCampione = new LookupList(db,
					"lookup_esito_campione");
			EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
			// find record permissions for portal users

			UserBean thisUser = (UserBean) context.getSession().getAttribute("User"); 
			if (thisUser.getRoleId()==Role.RUOLO_CRAS)
				ticList.setIdRuolo(thisUser.getRoleId());
			
			ticList.buildList(db);

			context.getRequest().setAttribute("TicList", ticList);
			addModuleBean(context, "View Accounts", "Accounts View");
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			errorMessage.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "ViewVigilanza");
	}
	
	
	

}
