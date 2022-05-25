/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.allevamenti.actions;

import it.izs.bdn.action.utilsXML;
import it.izs.bdn.bean.InfoAllevamentoBean;
import it.izs.bdn.bean.InfoAziendaBean;
import it.izs.bdn.bean.InfoPersonaBean;
import it.izs.ws.ClientB11;
import it.izs.ws.WsPost;
import it.izs.ws.WsPostElicicoltura;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

import org.aspcf.modules.checklist_benessere.base.Rendicontazione;
import org.aspcf.modules.controlliufficiali.base.AziendeZootFields;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Comuni;
import org.aspcfs.modules.actionplans.base.ActionItemWork;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.allevamenti.base.AllevamentoAjax;
import org.aspcfs.modules.allevamenti.base.ElicicolturaAllevamento;
import org.aspcfs.modules.allevamenti.base.Organization;
import org.aspcfs.modules.allevamenti.base.OrganizationList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.CustomField;
import org.aspcfs.modules.base.CustomFieldCategory;
import org.aspcfs.modules.base.CustomFieldCategoryList;
import org.aspcfs.modules.base.CustomFieldGroup;
import org.aspcfs.modules.base.CustomFieldRecord;
import org.aspcfs.modules.base.CustomFieldRecordList;
import org.aspcfs.modules.diffida.base.TicketList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.modules.opu.base.RicercheAnagraficheTab;
import org.aspcfs.modules.pipeline.base.OpportunityHeaderList;
import org.aspcfs.modules.scortefarmaci.base.ScortaAllevamento;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.AjaxCalls;
import org.aspcfs.utils.CapiAllevamentiUtil;
import org.aspcfs.utils.FileAesKeyException;
import org.aspcfs.utils.InvioBA;
import org.aspcfs.utils.JasperReportUtils;
import org.aspcfs.utils.LeggiFile;
import org.aspcfs.utils.PopolaCombo;
import org.aspcfs.utils.RiepilogoImport;
import org.aspcfs.utils.Utility;
import org.aspcfs.utils.web.CountrySelect;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.StateSelect;
import org.json.JSONArray;
import org.json.JSONObject;

import com.darkhorseventures.framework.actions.ActionContext;
import com.oreilly.servlet.MultipartRequest;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.webutils.FileDownload;


/**
 *  Action for the Account module
 *
 * @author     chris
 * @created    August 15, 2001
 * @version    $Id: Accounts.java 18261 2007-01-08 14:45:02Z matt $
 */
public final class Accounts extends CFSModule {

	/**
	 *  Default: not used
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandDefault(ActionContext context) {

		return executeCommandDashboard(context);
	}


	public String executeCommandAllImportRecordsBA(ActionContext context){
		Connection db = null; 
		try {

			db = getConnection(context);

			RiepilogoImport rImport = new RiepilogoImport();
			String data = (String) context.getRequest().getAttribute("dataEstrazione");
			if (data==null)
				data = context.getRequest().getParameter("dataEstrazione");

			rImport.buildListBA(db, data);

			context.getRequest().setAttribute("allRecords", rImport.getAllRecord());

			int[] totaliBA = new int[2];
			totaliBA = rImport.getTotaliBA(db);
			context.getRequest().setAttribute("BAtotaliOK", String.valueOf(totaliBA[0]));
			context.getRequest().setAttribute("BAtotaliKO", String.valueOf(totaliBA[1]));

			int[] totaliBA_2014 = new int[2];
			totaliBA_2014 = rImport.getTotaliBA_2014(db);
			context.getRequest().setAttribute("BAtotali_2014OK", String.valueOf(totaliBA_2014[0]));
			context.getRequest().setAttribute("BAtotali_2014KO", String.valueOf(totaliBA_2014[1]));

		} catch (Exception e) {	
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		if (context.getRequest().getParameter("layout")!=null && context.getRequest().getParameter("layout").equals("style") )
			return ("BAUploadListStampaOK");

		return ("BAUploadListOK");

	}

	public String executeCommandAllImportRecordsB11(ActionContext context){
		Connection db = null; 
		
		String data = null;
		String idControllo = null;
		
		try {

			db = getConnection(context);

			RiepilogoImport rImport = new RiepilogoImport();
			data = (String) context.getRequest().getAttribute("dataEstrazione");
			if (data==null)
				data = context.getRequest().getParameter("dataEstrazione");
			
			idControllo = (String) context.getRequest().getAttribute("idControllo");
			if (idControllo==null)
				idControllo = context.getRequest().getParameter("idControllo");

			rImport.buildListB11(db, data);

			context.getRequest().setAttribute("allRecords", rImport.getAllRecord());

			int[] totaliB11 = new int[2];
			totaliB11 = rImport.getTotaliB11(db);
			context.getRequest().setAttribute("B11totaliOK", String.valueOf(totaliB11[0]));
			context.getRequest().setAttribute("B11totaliKO", String.valueOf(totaliB11[1]));

			int[] totaliB11_2014 = new int[2];
			totaliB11_2014 = rImport.getTotaliB11_2014(db);
			context.getRequest().setAttribute("B11totali_2014OK", String.valueOf(totaliB11_2014[0]));
			context.getRequest().setAttribute("B11totali_2014KO", String.valueOf(totaliB11_2014[1]));


		} catch (Exception e) {	
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		if (context.getRequest().getParameter("layout")!=null && context.getRequest().getParameter("layout").equals("style") )
			return ("B11UploadListStampaOK");

		if (idControllo!=null)
			return ("B11UploadListStyleOK");
		return ("B11UploadListOK");

	}


	public String executeCommandAllImportRecordsSin(ActionContext context){
		Connection db = null;

		try {

			db = getConnection(context);

			RiepilogoImport rImport = new RiepilogoImport();
			rImport.buildListSin(db);

			context.getRequest().setAttribute("allRecords", rImport.getAllRecord());

		} catch (Exception e) {	
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("UploadListSinOK");

	}


	
	




	public String executeCommandFilterImportSing(ActionContext context){
		HttpServletRequest req = context.getRequest();

		return "FilterSinOk" ;

	}

	public String executeCommandFilterImportBa(ActionContext context){
		HttpServletRequest req = context.getRequest();

		return "FilterBaOk" ;

	}
	public String executeCommandFilterRendicontazioneBa(ActionContext context){
		HttpServletRequest req = context.getRequest();
		Connection db = null;

		try {

			db = getConnection(context);
			LookupList specieList = new LookupList(db,"lookup_chk_bns_mod");
			context.getRequest().setAttribute("LookupSpecie", specieList);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		finally
		{
			freeConnection(context, db);
		}

		return "FilterRendicontazioneBaOk" ;

	}


	public String executeCommandSendSin(ActionContext context){
		HttpServletRequest req = context.getRequest();

		Connection db = null;

		try {

			db = getConnection(context);


			String anno = context.getParameter("anno");
			String logPath = getWebContentPath(context, "logSin");
			String pathDownloadLog = "" ;//it.izs.sinsa.services.Client.importGisaToBdn(logPath,anno);



			String[] data = pathDownloadLog.split("[ _ ]");
			String dataOperazione = data[0].trim()+"/"+data[1].trim()+"/"+data[2].trim();

			RiepilogoImport rImport = new RiepilogoImport( dataOperazione, pathDownloadLog,true );
			rImport.setOkLog(pathDownloadLog+"_ok.txt");
			rImport.setKoLog(pathDownloadLog+"_ko.txt");
			rImport.insertSin(db);

			context.getRequest().setAttribute("pathLog", pathDownloadLog);

		} catch (Exception e) {	
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return executeCommandAllImportRecordsSin(context);

	}





	public String executeCommandToInterrogaBDN(ActionContext context) {



		String codAzienda = context.getRequest().getParameter("cod_azienda");
		String specie = context.getRequest().getParameter("specie");



		AjaxCalls call = new AjaxCalls();
		
		Connection db = null;
		try
		{
			db = getConnection(context);
			LookupList specieList = new LookupList(db,"lookup_specie_allevata");
			context.getRequest().setAttribute("SpecieList", specieList);
			
			ArrayList<InfoAllevamentoBean> lista = call.getAllevamenti(codAzienda,"","0"+specie,db,getUserId(context));
			context.getRequest().setAttribute("ListaAllevamenti",lista);

			if(lista.isEmpty())
			{
				context.getRequest().setAttribute("Errore", "Lista vuota");
			}

		}
		catch(SQLException e)
		{
			e.printStackTrace();

		}finally
		{
			freeConnection(context, db);
		}


		return "searchBdnOk";
	}

	public String executeCommandImportGisa(ActionContext context) throws ParseException {


		System.out.println("Metodo executeCommandImportGisa: avvio");
		String codAzienda = context.getRequest().getParameter("codAzienda");
		String idFiscale = context.getRequest().getParameter("idFiscale");
		String specie = context.getRequest().getParameter("specie");
		String denominazione = context.getRequest().getParameter("denominazione");
		String codiceOrientamentoProd = context.getRequest().getParameter("codiceOrientamentoProd");
		String descrizioneOrientamentoProd = context.getRequest().getParameter("descrizioneOrientamentoProd");
		String stato = context.getRequest().getParameter("stato");
		String idAllevamento = context.getRequest().getParameter("idAllevamento");
		
		InfoAllevamentoBean allevamento = null;

		int orgId = -1;

		Connection db = null;
		try
		{

			db = this.getConnection(context);
			
			boolean esisteAllevamento = false;
			PreparedStatement pstEsistenza = db.prepareStatement("select * from get_esistenza_allevamento(?, ?, ?, ?, ?, ?) ");
			pstEsistenza.setString(1, codAzienda);
			pstEsistenza.setString(2, idFiscale);
			pstEsistenza.setString(3, specie);
			pstEsistenza.setString(4, codiceOrientamentoProd);
			pstEsistenza.setString(5, descrizioneOrientamentoProd);
			pstEsistenza.setString(6, stato);

			ResultSet rsEsistenza = pstEsistenza.executeQuery();
			System.out.println("Esistenza allevamento: "+pstEsistenza.toString());
			if (rsEsistenza.next())
			{
				esisteAllevamento = rsEsistenza.getBoolean(1);
			}
			System.out.println("Metodo executeCommandImportGisa: esisteAllevamento: "+ esisteAllevamento);
			if (esisteAllevamento){
				System.out.println("Esistenza allevamento: Esiste");
				context.getRequest().setAttribute("ErroreImport", "Azienda ["+codAzienda+"] / ["+idFiscale+"] gia' esistente in anagrafica. Import non consentito.");
				return executeCommandAdd(context);
			}
				
			 
			AjaxCalls call = new AjaxCalls();
			
			System.out.println("Metodo executeCommandImportGisa: isNumeric: " + specie + ", nuemric? " + org.apache.commons.lang.StringUtils.isNumeric(specie));
			String specieCod = null;
			InfoAllevamentoBean tmpIAB = new InfoAllevamentoBean();
			if (!org.apache.commons.lang.StringUtils.isNumeric(specie))
			{
				specie = tmpIAB.getCodSpecieAllevataByAltCod(specie, db);
			}
			else
			{
				specieCod = tmpIAB.getAltShortDescriptionAllevataByShort("0"+specie, db);
				System.out.println("Metodo executeCommandImportGisa: specieCod: " + specieCod);
			}
						
			System.out.println("Metodo executeCommandImportGisa: specie: " + specie);
			//GESTIONE INSERIMENTO ALLEVAMENTO
			//SPECIE ACQUACOLTURA
			if(specie.equalsIgnoreCase("200") || specie.equalsIgnoreCase("0160") || specie.equalsIgnoreCase("201"))
			{
				String responseAzienda = null;
				String responseAllevamento = null;
				String responseProprietario = null;
				String responseResponsabile = null;

				System.out.println(" ------ RICHIESTA DATI AZIENDA ACQUACOLTURA " + codAzienda + " -----"); 
					
				WsPost ws = new WsPost();  

				//Recupero dati azienda e allevamento
				ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_ACQUACOLTURA_BDN"));
					
				String envelopeAzienda 	   = costruisciEnvelope("Azienda", codAzienda, null);
				String envelopeAllevamento = costruisciEnvelope("Allevamento", codAzienda, idFiscale);
				
				System.out.println("Metodo executeCommandImportGisa: envelopeAzienda: " + envelopeAzienda);
				System.out.println("Metodo executeCommandImportGisa: envelopeAllevamento: " + envelopeAllevamento);
					
				ws.setWsRequest(envelopeAzienda);
				responseAzienda = ws.post(db, getUserId(context));
					
				System.out.println("Metodo executeCommandImportGisa: responseAzienda: " + responseAzienda);
				
				ws.setWsRequest(envelopeAllevamento);
				responseAllevamento = ws.post(db, getUserId(context));
				System.out.println("Metodo executeCommandImportGisa: responseAllevamento: " + responseAllevamento);
				
				String specieOttenuta = null;
				while(specieOttenuta==null || !specieOttenuta.equalsIgnoreCase(specieCod))
				{
					int posizioneInizioReturn = responseAllevamento.indexOf("<return>");
					if(posizioneInizioReturn<0)
						posizioneInizioReturn = responseAllevamento.indexOf("<return xmlns=\"\">");
					System.out.println("Metodo executeCommandImportGisa: posizioneInizioReturn: " + posizioneInizioReturn);
					
					int posizioneFineReturn = responseAllevamento.indexOf("</return>") + 9;
					System.out.println("Metodo executeCommandImportGisa: posizioneFineReturn: " + posizioneFineReturn);
					
					String responseAllevamentoOttenuto = responseAllevamento.substring(posizioneInizioReturn, posizioneFineReturn);
					System.out.println("Metodo executeCommandImportGisa: responseAllevamentoOttenuto: " + responseAllevamentoOttenuto);
					
					specieOttenuta = utilsXML.getValoreNodoXML(responseAllevamentoOttenuto,"gspCodice" );
					System.out.println("Metodo executeCommandImportGisa: specieOttenuta: " + specieOttenuta);
					if(specieOttenuta!=null && specieOttenuta.equalsIgnoreCase(specieCod))
					{
						responseAllevamento = "<?xml version=\"1.0\" ?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Body><ns2:getAllevamentoResponse xmlns:ns2=\"http://acquacoltura.izs.it/services\">" + responseAllevamentoOttenuto + "></ns2:getAllevamentoResponse></S:Body></S:Envelope>";
						break;
					}
					else
					{
						System.out.println("Metodo executeCommandImportGisa: response parte da tagliare: " + responseAllevamentoOttenuto );
						responseAllevamento = responseAllevamento.substring(0, posizioneInizioReturn) + responseAllevamento.substring(posizioneFineReturn, responseAllevamento.length());
						System.out.println("Metodo executeCommandImportGisa: response nuovo: " + responseAllevamento);
					}
				}
				//Fine recupero dati azienda e allevamento
				
				//Recupero dati proprietario e rappresentante
				ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_AZIENDE_BDN"));
					
				String envelopeProprietario 	   = costruisciEnvelopePersona(utilsXML.getValoreNodoXML(responseAllevamento,"propIdfiscale" ));
				String envelopeResponsabile        = costruisciEnvelopePersona(utilsXML.getValoreNodoXML(responseAllevamento,"respIdfiscale" ));
					
				ws.setWsRequest(envelopeProprietario);
				responseProprietario = ws.post(db, getUserId(context));
					
				ws.setWsRequest(envelopeResponsabile);
				responseResponsabile = ws.post(db, getUserId(context));
				//Fine recupero dati azienda e allevamento
				
				String allevId = utilsXML.getValoreNodoXML(responseAllevamento,"allevId" );
				String aslCodice = utilsXML.getValoreNodoXML(responseAllevamento,"aslCodice" );
				String propIdfiscale = utilsXML.getValoreNodoXML(responseAllevamento,"propIdfiscale" );
				String dtApertura = utilsXML.getValoreNodoXML(responseAllevamento,"dtApertura" );
				String specieAllevate = utilsXML.getValoreNodoXML(responseAllevamento,"saaDescrizione" );
				String tipoAllevamento = utilsXML.getValoreNodoXML(responseAllevamento,"talDescrizione" );
				String tipologiaProduttiva = utilsXML.getValoreNodoXML(responseAllevamento,"tprDescrizione" );
				String respIdfiscale = utilsXML.getValoreNodoXML(responseAllevamento,"respIdfiscale" );
				String via = utilsXML.getValoreNodoXML(responseAzienda,"indirizzo" );
				String viaSedeOperativa = utilsXML.getValoreNodoXML(responseAzienda,"indirizzo" );
				String cap = utilsXML.getValoreNodoXML(responseAzienda,"cap" );
				String capSedeOperativa = utilsXML.getValoreNodoXML(responseAzienda,"cap" );
				String provincia = utilsXML.getValoreNodoXML(responseAzienda,"siglaProvincia" );
				String comIstat = utilsXML.getValoreNodoXML(responseAzienda,"comIstat" );
				//In bdn le coordinate stanno solo sull'azienda
				String latitudine = utilsXML.getValoreNodoXML(responseAzienda,"latitudine" );
				String longitudine = utilsXML.getValoreNodoXML(responseAzienda,"longitudine" );
				String latitudineSedeOperativa = utilsXML.getValoreNodoXML(responseAzienda,"latitudine" );
				String longitudineSedeOperativa = utilsXML.getValoreNodoXML(responseAzienda,"longitudine" );
				String nominativoProp = utilsXML.getValoreNodoXML(responseProprietario,"COGN_NOME" );
				String nominativoResp = utilsXML.getValoreNodoXML(responseResponsabile,"COGN_NOME" );
				String indirizzoProp = utilsXML.getValoreNodoXML(responseProprietario,"INDIRIZZO" );
				String indirizzoResp = utilsXML.getValoreNodoXML(responseResponsabile,"INDIRIZZO" );
				String capProp = utilsXML.getValoreNodoXML(responseProprietario,"CAP" );
				String capResp = utilsXML.getValoreNodoXML(responseResponsabile,"CAP" );
				String comIstatProp = utilsXML.getValoreNodoXML(responseProprietario,"COM_CODICE" );
				String comIstatResp = utilsXML.getValoreNodoXML(responseResponsabile,"COM_CODICE" );
				String comuneSedeOperativa = Utility.getComuneFromCodiceAzienda(codAzienda, db);
				
				
				PreparedStatement pst = db.prepareStatement("select * from public_functions.import_acquacoltura(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				pst.setString(1, denominazione);
				pst.setString(2, aslCodice);
				pst.setString(3, codAzienda);
				pst.setString(4, tipoAllevamento);
				pst.setString(5, propIdfiscale);
				pst.setString(6, tipologiaProduttiva);
				pst.setInt   (7, getUserId(context));
				pst.setString(8, dtApertura);
				pst.setString(9, provincia);
				pst.setString(10, via);
				pst.setString(11, cap);
				pst.setString(12, latitudine);
				pst.setString(13, longitudine);
				pst.setString(14, comIstat);
				pst.setString(15, respIdfiscale);
				pst.setString(16, specieAllevate);
				pst.setString(17, allevId);
				pst.setString(18, specie);
				pst.setString(19, nominativoProp);
				pst.setString(20, nominativoResp);
				pst.setString(21, indirizzoProp);
				pst.setString(22, indirizzoResp);
				pst.setString(23, capProp);
				pst.setString(24, capResp);
				pst.setString(25, comIstatProp);
				pst.setString(26, comIstatResp);
				pst.setString(27, viaSedeOperativa);
				pst.setString(28, comuneSedeOperativa);
				pst.setString(29, capSedeOperativa);
				pst.setString(30, latitudineSedeOperativa);
				pst.setString(31, longitudineSedeOperativa);
				
				System.out.println("QUERY IMPORT ALLEVAMENTO ACQUACOLTURA IN GISA: " + pst.toString());
				
				ResultSet rs = pst.executeQuery();
				
			}
			//TUTTO IL RESTO DELLE SPECIE
			else
			{
				
				
				allevamento = allevamento.getInfoAllevamento(idAllevamento, db, getUserId(context));
				if(allevamento.getErrore()==null ||   "".equals(allevamento.getErrore()))
				{
					orgId = allevamento.insert(db,context,getUserId(context));
				}
			}
			//FINE GESTIONE INSERIMENTO ALLEVAMENTO
			

			PreparedStatement pst = db.prepareStatement("select * from aziende where cod_azienda ilike ? ");
			pst.setString(1, codAzienda);
			ResultSet rs = pst.executeQuery();
			if (!rs.next())
			{
				InfoAziendaBean aziendaBean = new InfoAziendaBean();
				InfoAziendaBean aziendaRet = aziendaBean.getInfoAllevamentoBean(codAzienda,db,getUserId(context));
				aziendaRet.insert(db);
			}


			pst = db.prepareStatement("select * from operatori_allevamenti where cf ilike ? ");
			pst.setString(1, idFiscale);
			rs = pst.executeQuery();
			if (!rs.next())
			{
				InfoPersonaBean pp = new InfoPersonaBean();
				InfoPersonaBean pp1 = pp.getListaAziende(idFiscale,db,getUserId(context));
				pp1.insert(db);

			}
			
			
			/*REFRESH TAB MATERIALIZZATA*/
			RicercheAnagraficheTab.inserOrganization(db, orgId);




		}
		catch(Exception e)
		{
			e.printStackTrace();

		}finally
		{
			this.freeConnection(context, db);
		}
		
//		if (orgId>0){
//			context.getRequest().setAttribute("orgId", String.valueOf(orgId));
//			
//			org.aspcfs.modules.opu.actions.StabilimentoAction stabAction = new org.aspcfs.modules.opu.actions.StabilimentoAction();
//			return stabAction.executeCommandCaricaImport(context);
//		}
		
		if(allevamento!=null && allevamento.getErrore()!=null && !"".equals(allevamento.getErrore()))
			context.getRequest().setAttribute("EsitoImport", "Occorso un errore nella chiamata ai servizi: ["+allevamento.getErrore()+"]");
		else if (orgId>0)
			context.getRequest().setAttribute("orgIdInserito", String.valueOf(orgId));

		
		return executeCommandSearchForm(context);
	}

	
	public String executeCommandSincronizzaConBDN(ActionContext context) throws ParseException {



		String codAzienda = context.getRequest().getParameter("codAzienda");
		String idFiscale = context.getRequest().getParameter("idFiscale");
//		String specie = context.getRequest().getParameter("specie");
		String denominazione = context.getRequest().getParameter("denominazione");
		
		String orgIdString = context.getRequest().getParameter("orgId");
		String dataInizioAttivita = context.getRequest().getParameter("dataInizioAttivita");
		String dataFineAttivita = context.getRequest().getParameter("dataFineAttivita");
		String orientamentoProd = context.getRequest().getParameter("orientamento_prod");
		String specieAllevata = context.getRequest().getParameter("specieAllevata");
		String numeroCapiString = context.getRequest().getParameter("numeroCapi");
		String capLegale = context.getRequest().getParameter("capLegale");
		String indirizzoLegale = context.getRequest().getParameter("indirizzoLegale");
		String comuneLegale = context.getRequest().getParameter("comuneLegale");
		String indirizzoOperativa = context.getRequest().getParameter("indirizzoOperativa");
		String codComuneOperativa = context.getRequest().getParameter("codComuneOperativa");
		String istatComuneOperativa = context.getRequest().getParameter("istatComuneOperativa");
		String capOperativa = context.getRequest().getParameter("capOperativa");
		String latitudineOperativa = context.getRequest().getParameter("latitudineOperativa");
		String longitudineOperativa = context.getRequest().getParameter("longitudineOperativa");
		String codiceFiscaleProp = context.getRequest().getParameter("codiceFiscaleProp");
		String codiceFiscaleDet = context.getRequest().getParameter("codiceFiscaleDet");
		String tipologia_strutt = context.getRequest().getParameter("tipologia_strutt");
		String codiceTipoAllevamento = context.getRequest().getParameter("codiceTipoAllevamento");

		
		int orgId = -1;
		int numeroCapi = -1;
		
		try {orgId = Integer.parseInt(orgIdString);} catch (Exception e) {}
		try {numeroCapi = Integer.parseInt(numeroCapiString);} catch (Exception e) {}
	
		InfoAllevamentoBean allevamento = new InfoAllevamentoBean();
		InfoAziendaBean azienda = new InfoAziendaBean();
		
		Connection db = null;
		try
		{
			db = this.getConnection(context);

			if (dataFineAttivita != null && !dataFineAttivita.equals("null") && !dataFineAttivita.equals("")) 
			{
				allevamento.setData_fine_attivita(dataFineAttivita);
			}
			if (dataInizioAttivita != null && !dataInizioAttivita.equals("null") && !dataInizioAttivita.equals("")) 
			{
				allevamento.setData_inizio_attivita(dataInizioAttivita);
			}
			
			allevamento.setCodiceTipoAllevamento(codiceTipoAllevamento);
			allevamento.setOrientamento_prod(orientamentoProd);
			allevamento.setTipo_produzione(tipologia_strutt);
			allevamento.setCodice_specie(specieAllevata);
			allevamento.setNum_capi_totali(numeroCapiString);
			allevamento.setModifiedBy(getUserId(context));
			allevamento.setIndirizzo(indirizzoLegale);
			allevamento.setCap(capLegale);
			allevamento.setComune(comuneLegale);
			allevamento.setCodice_specie(specieAllevata);
			allevamento.setCodice_azienda(codAzienda);
			allevamento.setCodFiscaleProp(codiceFiscaleProp);
			allevamento.setCod_fiscale_det(codiceFiscaleDet);
			allevamento.setDenominazione(denominazione);
			allevamento.setCod_fiscale(idFiscale);
			allevamento.sincronizzaBdn(db,orgId);
			
			azienda.setCod_azienda(codAzienda);
			azienda.setIndirizzo_Azienda(indirizzoOperativa);
			azienda.setCod_comune_azienda(codComuneOperativa);
			azienda.setComuneAziendaCalcolato(istatComuneOperativa);
			azienda.setCap_azienda(capOperativa);
			if(latitudineOperativa!=null && !latitudineOperativa.equals(""))
				azienda.setLatitudine(Double.parseDouble(latitudineOperativa));
			if(longitudineOperativa!=null && !longitudineOperativa.equals(""))
				azienda.setLongidutine(Double.parseDouble(longitudineOperativa));
			azienda.sincronizzaBdn(db, orgId);

			context.getRequest().setAttribute("orgId",orgId);
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
	
//	public String executeCommandSincronizzaConBDN_OLD(ActionContext context) throws ParseException {
//
//
//
//		String codAzienda = context.getRequest().getParameter("codAzienda");
//		String idFiscale = context.getRequest().getParameter("idFiscale");
//		String specie = context.getRequest().getParameter("specie");
//		String denominazione = context.getRequest().getParameter("denominazione");
//
//
//		Connection db = null;
//		try
//		{
//			db = this.getConnection(context);
//
//			AjaxCalls call = new AjaxCalls();
//			InfoAllevamentoBean allevamento = null ;
//
//			if (!specie.equals("0131") && ! specie.equals("0146"))
//			{
//				allevamento =call.getInfoAllevamento(codAzienda, idFiscale, specie,db);
//			}
//
//			else
//			{
//
//				ArrayList<InfoAllevamentoBean> listaAll = call.getAllevamenti(codAzienda, denominazione, specie);
//				allevamento = listaAll.get(0);
//				allevamento.getOrientamentoProduzioneAvicoli(db, allevamento.getFlag_carne_latte());
//
//
//
//			}
//
//			Organization org = new Organization(db,Integer.parseInt(context.getParameter("orgId")));
//			if(allevamento != null )
//			{
//
//				Timestamp dataFineAttivita= null ;
//
//				if (allevamento.getData_fine_attivita() != null)
//				{
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//					dataFineAttivita=new Timestamp (sdf.parse(allevamento.getData_fine_attivita()).getTime());
//
//					org.setDate2(dataFineAttivita);
//
//
//				}
//
//				org.setOrientamentoProd(allevamento.getOrientamento_prod());
//				
//				if (context.getRequest().getParameter("dataFineAttivita")!=null && !context.getRequest().getParameter("dataFineAttivita").equals("null"))
//					allevamento.setData_fine_attivita(context.getRequest().getParameter("dataFineAttivita"));
//				//allevamento.setNum_capi_totali(context.getRequest().getParameter("numeroCapi"));
//				allevamento.setSpecie_allevata(context.getRequest().getParameter("specieAllevata"));
//				
//				
//				if (allevamento.getNum_capi_totali() != null && !"".equals(allevamento.getNum_capi_totali()))
//					org.setNumero_capi(Integer.parseInt( allevamento.getNum_capi_totali()));
//
//				
//				allevamento.setModifiedBy(getUserId(context));
//				allevamento.sincronizzaBdn(db,org.getOrgId(),org.getNumero_capi());
//				
//
//
//			}
//			context.getRequest().setAttribute("orgId",org.getOrgId());
//
//			context.getRequest().setAttribute("InfoAllevamentoBean",allevamento);
//		}
//		catch(SQLException e)
//		{
//			e.printStackTrace();
//
//		}finally
//		{
//			this.freeConnection(context, db);
//		}
//
//
//
//
//		return executeCommandDetails(context);
//	}

	/**
	 *  Reports: Displays a list of previously generated reports with
	 *  view/delete/download options.
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandReports(ActionContext context) {
		if (!hasPermission(context, "allevamenti-allevamenti-reports-view")) {
			return ("PermissionError");
		}
		//Set the menu: the user is in the Reports module
		addModuleBean(context, "Reports", "ViewReports");
		//Retrieve the paged list that will be used for paging through reports
		PagedListInfo rptListInfo = this.getPagedListInfo(context, "RptListInfo");
		rptListInfo.setLink("Allevamenti.do?command=Reports");
		//Prepare the file list for accounts
		FileItemList files = new FileItemList();
		files.setLinkModuleId(Constants.DOCUMENTS_ACCOUNTS_REPORTS);
		files.setPagedListInfo(rptListInfo);
		//Check the combo box value from the report list for filtering reports
		if ("all".equals(rptListInfo.getListView())) {
			//Show only the reports that this user or someone below this user created
			files.setOwnerIdRange(this.getUserRange(context));
		} else {
			//Show only this user's reports
			files.setOwner(this.getUserId(context));
		}
		Connection db = null;
		try {
			//Get a connection from the connection pool for this user
			db = this.getConnection(context);
			//Generate the list of files based on the above criteria
			files.buildList(db);
			context.getRequest().setAttribute("FileList", files);
		} catch (Exception errorMessage) {
			//An error occurred, go to generic error message page
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			//Always free the database connection
			this.freeConnection(context, db);
		}
		return ("ReportsOK");
	}


	/**
	 *  DownloadCSVReport: Sends a copy of the CSV report to the user's local
	 *  machine
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandDownloadCSVReport(ActionContext context) {
		if (!(hasPermission(context, "allevamenti-allevamenti-reports-view"))) {
			return ("PermissionError");
		}
		Exception errorMessage = null;
		String itemId = (String) context.getRequest().getParameter("fid");
		FileItem thisItem = null;
		Connection db = null;
		try {
			db = getConnection(context);
			thisItem = new FileItem(
					db, Integer.parseInt(itemId), -1, Constants.DOCUMENTS_ACCOUNTS_REPORTS);
		} catch (Exception e) {
			errorMessage = e;
		} finally {
			this.freeConnection(context, db);
		}
		//Start the download
		try {
			FileItem itemToDownload = null;
			itemToDownload = thisItem;
			//itemToDownload.setEnteredBy(this.getUserId(context));
			String filePath = this.getPath(context, "allevamenti-reports") + getDatePath(itemToDownload.getEntered()) + itemToDownload.getFilename() + ".csv";
			FileDownload fileDownload = new FileDownload();
			fileDownload.setFullPath(filePath);
			fileDownload.setDisplayName(itemToDownload.getClientFilename());
			if (fileDownload.fileExists()) {
				fileDownload.sendFile(context);
				//Get a db connection now that the download is complete
				db = getConnection(context);
				itemToDownload.updateCounter(db);
			} else {
				System.err.println(
						"allevamenti-> Trying to send a file that does not exist");
			}
		} catch (java.net.SocketException se) {
			//User either canceled the download or lost connection
		} catch (Exception e) {
			errorMessage = e;

		} finally {
			this.freeConnection(context, db);
		}
		if (errorMessage == null) {
			return ("-none-");
		} else {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		}
	}


	/**
	 *  ShowReportHtml: Displays a preview of the selected report in HTML format
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandShowReportHtml(ActionContext context) {
		if (!(hasPermission(context, "allevamenti-allevamenti-reports-view"))) {
			return ("PermissionError");
		}
		FileItem thisItem = null;
		String itemId = (String) context.getRequest().getParameter("fid");
		Connection db = null;
		try {
			db = getConnection(context);
			thisItem = new FileItem(
					db, Integer.parseInt(itemId), -1, Constants.DOCUMENTS_ACCOUNTS_REPORTS);
			String filePath = this.getPath(context, "allevamenti-reports") + getDatePath(
					thisItem.getEntered()) + thisItem.getFilename() + ".html";
			String textToShow = includeFile(filePath);
			context.getRequest().setAttribute("ReportText", textToShow);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("ReportHtmlOK");
	}


	/**
	 *  GenerateForm: Displays the form that allows the user to select criteria
	 *  and specify information for a new Accounts report
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandGenerateForm(ActionContext context) {
		if (!hasPermission(context, "allevamenti-allevamenti-reports-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		CustomFieldCategoryList thisList = new CustomFieldCategoryList();
		thisList.setLinkModuleId(Constants.ACCOUNTS);
		thisList.setIncludeEnabled(Constants.TRUE);
		thisList.setIncludeScheduled(Constants.TRUE);
		thisList.setAllSelectOption(true);
		thisList.setBuildResources(false);
		try {
			db = getConnection(context);
			thisList.buildList(db);
			context.getRequest().setAttribute("CategoryList", thisList);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Reports", "Generate new");
		return ("GenerateFormOK");
	}


	/**
	 *  ExportReport: Creates both an HTML version (for preview) and a CSV version
	 *  of the report
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */



	/**
	 *  DeleteReport: Deletes previously generated report files (HTML and CSV)
	 *  from server and all related file information from the project_files table.
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandDeleteReport(ActionContext context) {
		if (!(hasPermission(context, "allevamenti-allevamenti-reports-delete"))) {
			return ("PermissionError");
		}
		boolean recordDeleted = false;
		String itemId = (String) context.getRequest().getParameter("fid");
		Connection db = null;
		try {
			db = getConnection(context);
			FileItem thisItem = new FileItem(
					db, Integer.parseInt(itemId), -1, Constants.DOCUMENTS_ACCOUNTS_REPORTS);
			recordDeleted = thisItem.delete(
					db, this.getPath(context, "allevamenti-reports"));
			String filePath1 = this.getPath(context, "allevamenti-reports") + getDatePath(
					thisItem.getEntered()) + thisItem.getFilename() + ".csv";
			java.io.File fileToDelete1 = new java.io.File(filePath1);
			if (!fileToDelete1.delete()) {
				System.err.println("FileItem-> Tried to delete file: " + filePath1);
			}
			String filePath2 = this.getPath(context, "allevamenti-reports") + getDatePath(
					thisItem.getEntered()) + thisItem.getFilename() + ".html";
			java.io.File fileToDelete2 = new java.io.File(filePath2);
			if (!fileToDelete2.delete()) {
				System.err.println("FileItem-> Tried to delete file: " + filePath2);
			}
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Reports", "Reports del");
		if (recordDeleted) {
			return ("DeleteReportOK");
		} else {
			return ("DeleteReportERROR");
		}
	}


	/**
	 *  Search: Displays the Account search form
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandSearchForm(ActionContext context) {
		if (!(hasPermission(context, "allevamenti-allevamenti-view"))) {
			return ("PermissionError");
		}

		/* if (!(hasPermission(context, "request-view"))) {
        return ("PermissionError");
      }
		 */
		//Bypass search form for portal users
		if (isPortalUser(context)) {
			return (executeCommandSearch(context));
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = getConnection(context);

			ArrayList<Comuni> comuni = new ArrayList<Comuni>();
			String sql = "select codiceistatcomune , comune from comuni order by comune ";
			PreparedStatement pst1 = db.prepareStatement(sql);
			ResultSet rs1 = pst1.executeQuery();
			while (rs1.next())
			{
				Comuni c = new Comuni();

				String istat = rs1.getString(1);
				String descr = rs1.getString(2);
				String prov = "";
				if (istat.substring(0,2).equals("62"))
				{
					prov = "BN";
				}
				else
				{
					if (istat.substring(0,2).equals("63"))
					{
						prov = "NA";
					}
					else
					{
						if (istat.substring(0,2).equals("64"))
						{
							prov = "AV";
						}

						else
						{
							if (istat.substring(0,2).equals("65"))
							{
								prov = "SA";
							}
							else
							{
								if (istat.substring(0,2).equals("61"))
								{
									prov = "CE";
								}
							}
						}
					}

				}
				c.setCodice(istat.substring(2)+prov);
				c.setComune(descr) ;
				comuni.add(c);

			}


			context.getRequest().setAttribute("ComuniList", comuni);
			if (context.getRequest().getAttribute("InfoAllevamentoBean")!=null)
			{
				context.getRequest().setAttribute("InfoAllevamentoBean",(InfoAllevamentoBean)context.getRequest().getAttribute("InfoAllevamentoBean"));
			}

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

		context.getRequest().setAttribute("EsitoImport",context.getRequest().getAttribute("EsitoImport"));

		
		if (context.getRequest().getParameter("popup")!=null && context.getRequest().getParameter("popup").equals("true") )
			return ("SearchPopupOK");

		return ("SearchOK");
	}


	/**
	 *  Add: Displays the form used for adding a new Account
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandAdd(ActionContext context) {
		if (!hasPermission(context, "allevamenti-allevamenti-add")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = this.getConnection(context);
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);




			LookupList specie = new LookupList(db, "lookup_specie_allevata");
			specie.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SpecieList", specie);


			Organization newOrg = (Organization) context.getFormBean();
			ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
			StateSelect stateSelect = new StateSelect(systemStatus, (newOrg.getAddressList() != null?newOrg.getAddressList().getCountries():"")+prefs.get("SYSTEM.COUNTRY"));
			stateSelect.setPreviousStates((newOrg.getAddressList() != null? newOrg.getAddressList().getSelectedStatesHashMap():new HashMap()));
			context.getRequest().setAttribute("StateSelect", stateSelect);


		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		
		String ErroreImport = (String)context.getRequest().getAttribute("ErroreImport");
		if (ErroreImport != null) {
			context.getRequest().setAttribute("ErroreImport", ErroreImport);
		}
		
		addModuleBean(context, "Add Account", "Accounts Add");
		context.getRequest().setAttribute(
				"systemStatus", this.getSystemStatus(context));
		//if a different module reuses this action then do a explicit return
		if (context.getRequest().getParameter("actionSource") != null) {
			return getReturn(context, "AddAccount");
		}

		return getReturn(context, "Add");
	}

	
	public String executeCommandSearchBDN(ActionContext context) {
		if (!hasPermission(context, "allevamenti-allevamenti-add")) {
			return ("PermissionError");
		}
		return getReturn(context, "Add");
	}

	/**
	 *  Details: Displays all details relating to the selected Account. The user
	 *  can also goto a modify page from this form or delete the Account entirely
	 *  from the database
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandDetails(ActionContext context) {
		if (!hasPermission(context, "allevamenti-allevamenti-view")) {
			return ("PermissionError");
		}
		
		UserBean userBean = (UserBean) context.getSession().getAttribute("User");
		User user = userBean.getUserRecord();
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Organization newOrg = null;
		try {

			String temporgId = context.getRequest().getParameter("orgId");
			if (temporgId == null) {
				temporgId = (String) context.getRequest().getAttribute("orgId");
			}
			int tempid = Integer.parseInt(temporgId);
			db = this.getConnection(context);
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(temporgId))) {
				return ("PermissionError");
			}


			context.getRequest().setAttribute("InfoAllevamentoBean", context.getRequest().getAttribute("InfoAllevamentoBean"));

			newOrg = new Organization(db, tempid);
			
			
			
			//Caricamento Diffide
			org.aspcfs.modules.diffida.base.Ticket t = new org.aspcfs.modules.diffida.base.Ticket();
			context.getRequest().setAttribute("DiffideList", t.getDiffide(db,false,null,null,newOrg.getOrgId(),null));
			context.getRequest().setAttribute("DiffideListStorico", t.getDiffide(db,true,null,null,newOrg.getOrgId(),null));


			//Aggiunta di adeguamento scheda
			AziendeZootFields az = new AziendeZootFields();
			az.queryRecordbyOrgId(db, tempid);

			context.getRequest().setAttribute("AzDetails", az);

			//check whether or not the owner is an active User

			//Dopo l'inserimento riconverti
			Iterator it_coords = newOrg.getAddressList().iterator();
			while(it_coords.hasNext()){

				org.aspcfs.modules.allevamenti.base.OrganizationAddress thisAddress = (org.aspcfs.modules.allevamenti.base.OrganizationAddress) it_coords.next();
				if(thisAddress.getLatitude()!=0 && thisAddress.getLongitude()!=0){

					String spatial_coords [] = null;
					spatial_coords = this.converToWgs84UTM33NInverter(Double.toString(thisAddress.getLatitude()), Double.toString(thisAddress.getLongitude()),db);

					thisAddress.setLatitude(spatial_coords[0]);
					thisAddress.setLongitude(spatial_coords[1]);

				}
			}

			//context.getSession().setAttribute("lat", Double.toString(thisAddress.getLatitude()));
			//context.getSession().setAttribute("lon", Double.toString(thisAddress.getLongitude()));




			/* org.aspcfs.modules.audit.base.AuditList audit = new org.aspcfs.modules.audit.base.AuditList();
      int AuditOrgId = newOrg.getOrgId();
      audit.setOrgId(AuditOrgId);

      //audit.buildList(db);

      if( (audit.size() - 1)>=0){

      context.getRequest().setAttribute("Audit",audit.get(0) );

      }
			 */
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);

			LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
			categoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);
			
			context.getRequest().setAttribute("trovatoInBdn", false);
			
			if(ApplicationProperties.getProperty("check_differenze_allevamenti_bdn_on_dettaglio").equals("true"))
			{
				InfoAllevamentoBean iab = InfoAllevamentoBean.getInfoAllevamento(newOrg.getId_allevamento(),db,getUserId(context));
			
				AjaxCalls ajaxCalls = new AjaxCalls();
				if(iab.getId_allevamento()!=null && !iab.getId_allevamento().equals(""))
				{
					
					
					
					
					
					
					
					
					AllevamentoAjax allevamento = null;
					InfoAziendaBean aziendaBean = null;
					InfoAziendaBean aziendaRet = null;
					
					boolean trovato = true;
					
					if(newOrg.getCodice_specie() == 200 || newOrg.getCodice_specie() == 160 || newOrg.getCodice_specie() == 201)
					{
						LookupList listaSpeci = new LookupList(db,"lookup_specie_allevata");
						
						SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
						SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
						
						String responseAzienda = null;
						String responseAllevamento = null;
						String responseProprietario = null;
						String responseResponsabile = null;

						System.out.println(" ------ RICHIESTA DATI AZIENDA ACQUACOLTURA " + newOrg.getAccountNumber() + " -----"); 
							
						WsPost ws = new WsPost();  

						//Recupero dati azienda e allevamento
						ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_ACQUACOLTURA_BDN"));
							
						String envelopeAzienda 	   = costruisciEnvelope("Azienda", newOrg.getAccountNumber(), null);
						String envelopeAllevamento = costruisciEnvelope("Allevamento", newOrg.getAccountNumber(),null );
						
						System.out.println("Metodo executeCommandImportGisa: envelopeAzienda: " + envelopeAzienda);
						System.out.println("Metodo executeCommandImportGisa: envelopeAllevamento: " + envelopeAllevamento);
							
						ws.setWsRequest(envelopeAzienda);
						responseAzienda = ws.post(db, getUserId(context));
							
						System.out.println("Metodo executeCommandImportGisa: responseAzienda: " + responseAzienda);
						
						ws.setWsRequest(envelopeAllevamento);
						responseAllevamento = ws.post(db, getUserId(context));
						System.out.println("Metodo executeCommandImportGisa: responseAllevamento: " + responseAllevamento);
						
						String specieOttenuta = null;
						String allevIdOttenuto = null;
						String specieToCompare = newOrg.getSpecieAllev();
						if(specieToCompare.equalsIgnoreCase("PESCI"))
							specieToCompare="PES";
						else if(specieToCompare.equalsIgnoreCase("MOLLUSCHI"))
							specieToCompare="MOL";
						else if(specieToCompare.equalsIgnoreCase("CROSTACEI"))
							specieToCompare="CRO";
						if(!responseAllevamento.contains(newOrg.getId_allevamento()))
							trovato = false;
						else
						{
						while(specieOttenuta==null || !specieOttenuta.equalsIgnoreCase("0" + newOrg.getCodice_specie()))
						{
							int posizioneInizioReturn = -1;
							posizioneInizioReturn = responseAllevamento.indexOf("<return xmlns=\"\">");
							if(posizioneInizioReturn==-1)
								posizioneInizioReturn = responseAllevamento.indexOf("<return>");
							System.out.println("Metodo executeCommandImportGisa: posizioneInizioReturn: " + posizioneInizioReturn);
							
							int posizioneFineReturn = responseAllevamento.indexOf("</return>") + 9;
							System.out.println("Metodo executeCommandImportGisa: posizioneFineReturn: " + posizioneFineReturn);
							
							String responseAllevamentoOttenuto = responseAllevamento.substring(posizioneInizioReturn, posizioneFineReturn);
							System.out.println("Metodo executeCommandImportGisa: responseAllevamentoOttenuto: " + responseAllevamentoOttenuto);
							
							specieOttenuta = utilsXML.getValoreNodoXML(responseAllevamentoOttenuto,"gspCodice" );
							System.out.println("Metodo executeCommandImportGisa: specieOttenuta: " + specieOttenuta);
							allevIdOttenuto = utilsXML.getValoreNodoXML(responseAllevamentoOttenuto,"allevId" );
							
							System.out.println("Metodo executeCommandImportGisa: specieOttenuta: " + specieOttenuta);
							System.out.println("Metodo executeCommandImportGisa: specieToCompare: " + specieToCompare);
							System.out.println("Metodo executeCommandImportGisa: newOrg.getId_allevamento(): " + newOrg.getId_allevamento());
							System.out.println("Metodo executeCommandImportGisa: allevIdOttenuto: " + allevIdOttenuto);
							if(specieOttenuta!=null && specieOttenuta.equalsIgnoreCase(specieToCompare) && newOrg.getId_allevamento().equals(allevIdOttenuto))
							{
								responseAllevamento = "<?xml version=\"1.0\" ?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Body><ns2:getAllevamentoResponse xmlns:ns2=\"http://acquacoltura.izs.it/services\">" + responseAllevamentoOttenuto + "></ns2:getAllevamentoResponse></S:Body></S:Envelope>";
								break;
							}
							else
							{
								System.out.println("Metodo executeCommandImportGisa: response parte da tagliare: " + responseAllevamentoOttenuto );
								responseAllevamento = responseAllevamento.substring(0, posizioneInizioReturn) + responseAllevamento.substring(posizioneFineReturn, responseAllevamento.length());
								System.out.println("Metodo executeCommandImportGisa: response nuovo: " + responseAllevamento);
							}
							if(!responseAllevamento.contains(newOrg.getId_allevamento()))
								trovato = false;
						}
						}
						//Fine recupero dati azienda e allevamento
						
						//Recupero dati proprietario e rappresentante
						ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_AZIENDE_BDN"));
							
						String envelopeProprietario 	   = costruisciEnvelopePersona(utilsXML.getValoreNodoXML(responseAllevamento,"propIdfiscale" ));
						String envelopeResponsabile        = costruisciEnvelopePersona(utilsXML.getValoreNodoXML(responseAllevamento,"respIdfiscale" ));
							
						ws.setWsRequest(envelopeProprietario);
						responseProprietario = ws.post(db, getUserId(context));
							
						ws.setWsRequest(envelopeResponsabile);
						responseResponsabile = ws.post(db, getUserId(context));
						//Fine recupero dati azienda e allevamento
						
						String allevDenominazione =  utilsXML.getValoreNodoXML(responseAllevamento,"allevDenominazione" );
						String allevId = utilsXML.getValoreNodoXML(responseAllevamento,"allevId" );
						String aslCodice = utilsXML.getValoreNodoXML(responseAllevamento,"aslCodice" );
						String propIdfiscale = utilsXML.getValoreNodoXML(responseAllevamento,"propIdfiscale" );
						String dtApertura = utilsXML.getValoreNodoXML(responseAllevamento,"dtApertura" );
						String dtChiusura = utilsXML.getValoreNodoXML(responseAllevamento,"dtChiusura" );
						String specieAllevate = utilsXML.getValoreNodoXML(responseAllevamento,"saaDescrizione" );
						String tipoAllevamento = utilsXML.getValoreNodoXML(responseAllevamento,"talDescrizione" );
						tipoAllevamento = tipoAllevamento==null ?(""):(tipoAllevamento);
						String tipologiaProduttiva = utilsXML.getValoreNodoXML(responseAllevamento,"tprDescrizione" );
						String respIdfiscale = utilsXML.getValoreNodoXML(responseAllevamento,"respIdfiscale" );
						String via = utilsXML.getValoreNodoXML(responseAzienda,"indirizzo" );
						String viaSedeOperativa = utilsXML.getValoreNodoXML(responseAllevamento,"indirizzo" );
						String cap = utilsXML.getValoreNodoXML(responseAzienda,"cap" );
						String capSedeOperativa = utilsXML.getValoreNodoXML(responseAllevamento,"capSedeLegale" );
						String provincia = utilsXML.getValoreNodoXML(responseAzienda,"siglaProvincia" );
						String comIstat = utilsXML.getValoreNodoXML(responseAzienda,"comIstat" );
						String latitudine = utilsXML.getValoreNodoXML(responseAzienda,"latitudine" );
						String longitudine = utilsXML.getValoreNodoXML(responseAzienda,"longitudine" );
						String nominativoProp = utilsXML.getValoreNodoXML(responseProprietario,"COGN_NOME" );
						String nominativoResp = utilsXML.getValoreNodoXML(responseResponsabile,"COGN_NOME" );
						String indirizzoProp = utilsXML.getValoreNodoXML(responseProprietario,"INDIRIZZO" );
						String indirizzoResp = utilsXML.getValoreNodoXML(responseResponsabile,"INDIRIZZO" );
						String capProp = utilsXML.getValoreNodoXML(responseProprietario,"CAP" );
						String capResp = utilsXML.getValoreNodoXML(responseResponsabile,"CAP" );
						String comIstatProp = utilsXML.getValoreNodoXML(responseProprietario,"COM_CODICE" );
						String comIstatResp = utilsXML.getValoreNodoXML(responseResponsabile,"COM_CODICE" );
						String orientamentoProd = utilsXML.getValoreNodoXML(responseAllevamento,"tprDescrizione" );
						orientamentoProd = orientamentoProd==null ?(""):(orientamentoProd);
						
						allevamento = new AllevamentoAjax();
						allevamento.setDenominazione(allevDenominazione);
						System.out.println("Metodo executeCommandImportGisa: setDenominazione: " + allevDenominazione);
						System.out.println("Metodo executeCommandImportGisa: responseAllevamento: " + responseAllevamento);
						allevamento.setCodice_azienda(newOrg.getAccountNumber());
						allevamento.setCodice_fiscale(propIdfiscale);
						allevamento.setIndirizzo(via);
						allevamento.setComune(Utility.getComuneFromCodiceAzienda(newOrg.getAccountNumber(), db));
						allevamento.setSpecie_allevata(newOrg.getCodice_specie());
						allevamento.setDescrizione_specie(listaSpeci.getSelectedValue(newOrg.getCodice_specie()));
						allevamento.setCfDetentore(respIdfiscale);
						allevamento.setCfProprietario(propIdfiscale);
						allevamento.setCodiceTipoAllevamento(tipoAllevamento);
						allevamento.setOrientamento_prod(orientamentoProd);
						allevamento.setTipologia_strutt(tipoAllevamento);
						if(dtApertura!=null && !dtApertura.equals("") && !dtApertura.equals("null"))
							allevamento.setData_inizio_attivita( sdf1.format( sdf2.parse(dtApertura) ) );
						if(dtChiusura!=null && !dtChiusura.equals("") && !dtChiusura.equals("null"))
							allevamento.setData_fine_attivita( sdf1.format( sdf2.parse(dtChiusura) ) );
						allevamento.setInBDN( true );
						
						aziendaRet = new InfoAziendaBean();
						aziendaRet.setNomeComuneAzienda(Utility.getComuneFromCodiceIstat(newOrg.getAccountNumber(), comIstat, db));
						aziendaRet.setIndirizzo_Azienda(via);
						aziendaRet.setCap_azienda(cap);
						if(latitudine!=null && !latitudine.equals(""))
							aziendaRet.setLatitudine(Double.parseDouble(latitudine));
						if(longitudine!=null && !longitudine.equals(""))
							aziendaRet.setLongidutine(Double.parseDouble(longitudine));
					}
					else
					{
						allevamento = ajaxCalls.findAllevamento(newOrg.getId_allevamento(), getUserId(context));
						aziendaBean = new InfoAziendaBean();
						aziendaRet = aziendaBean.getInfoAllevamentoBean(newOrg.getAccountNumber(),db,getUserId(context));
					}
					
					
					
					
					
					
					
					
					
					if(trovato)
					{
					newOrg.setOrientamentoProd(newOrg.getOrientamentoProd()==null ? "" : newOrg.getOrientamentoProd());
					newOrg.setTipologiaStrutt(newOrg.getTipologiaStrutt()==null ? "" : newOrg.getTipologiaStrutt());
					ArrayList diff = newOrg.equals(allevamento,aziendaRet, db);
					Iterator iDiff = diff.iterator();
					if (iDiff.hasNext())
						context.getRequest().setAttribute("differenza", "true");
					context.getRequest().setAttribute("trovatoInBdn", iab.getId_allevamento()!=null && !iab.getId_allevamento().equals(""));
					}
				}
			}
			
			
			
			//GESTIONE PICCIONI
			context.getRequest().setAttribute("MessaggioSchedaPiccioni", (String) context.getRequest().getAttribute("MessaggioSchedaPiccioni"));
			
			//GESTIONE BIOGAS
			context.getRequest().setAttribute("MessaggioSchedaBiogas", (String) context.getRequest().getAttribute("MessaggioSchedaBiogas"));


		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addRecentItem(context, newOrg);
		String action = context.getRequest().getParameter("action");
		if (action != null && action.equals("modify")) {
			//If user is going to the modify form
			addModuleBean(context, "Accounts", "Modify Account Details");
			return ("DetailsOK");
		} else {
			//If user is going to the detail screen
			addModuleBean(context, "View Accounts", "View Account Details");
			context.getRequest().setAttribute("OrgDetails", newOrg);

			// associazione OSM - azienda zootecnica
			String ricercaAllevamentiAssociabiliParameter = (String)context.getRequest().getParameter("ricercaAllevamentiAssociabiliParameter");
			if (ricercaAllevamentiAssociabiliParameter != null && ricercaAllevamentiAssociabiliParameter.equals("1")) {
				context.getRequest().setAttribute("ricercaAllevamentiAssociabiliAttribute", true);
			}

			return getReturn(context, "Details");
		}
	}
	
	
	
	
	
	
	public String executeCommandModificaMiscela(ActionContext context) 
	{
		if (!hasPermission(context, "allevamenti-allevamenti-view")) 
		{
			return ("PermissionError");
		}
		Connection db = null;
		Organization newOrg = null;
		try 
		{
			String temporgId = context.getRequest().getParameter("orgId");
			String miscelaString = context.getRequest().getParameter("miscela");
			boolean miscela = Boolean.parseBoolean(miscelaString);
			int tempid = Integer.parseInt(temporgId);
			db = this.getConnection(context);
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(temporgId)))
			{
				return ("PermissionError");
			}
			
			newOrg = new Organization();
			newOrg.setOrgId(tempid);
			newOrg.setMiscela(miscela);
			newOrg.updateMiscela(db,getUserId(context));
			
			return executeCommandDetails(context);
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
	}

	//aggiunto da d.dauria
	public String executeCommandFocolai(ActionContext context) {
		if (!hasPermission(context, "allevamenti-allevamenti-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Organization newOrg = null;
		try {

			String temporgId = context.getRequest().getParameter("orgId");
			if (temporgId == null) {
				temporgId = (String) context.getRequest().getAttribute("orgId");
			}
			int tempid = Integer.parseInt(temporgId);
			db = this.getConnection(context);
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(temporgId))) {
				return ("PermissionError");
			}
			newOrg = new Organization(db, tempid);
			//check whether or not the owner is an active User
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);

			LookupList categoriaRischioList = new LookupList(db, "lookup_org_catrischio");
			categoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("OrgCategoriaRischioList", categoriaRischioList);



		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addRecentItem(context, newOrg);
		String action = context.getRequest().getParameter("action");
		if (action != null && action.equals("modify")) {
			//If user is going to the modify form
			addModuleBean(context, "Accounts", "Modify Account Details");
			return ("FocolaioOK");
		} else {
			//If user is going to the detail screen
			addModuleBean(context, "View Accounts", "View Account Details");
			context.getRequest().setAttribute("OrgDetails", newOrg);
			return getReturn(context, "Focolaio");
		}
	}



	public String executeCommandViewCampioni(ActionContext context) {
		if (!hasPermission(context, "allevamenti-allevamenti-campioni-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		org.aspcfs.modules.campioni.base.TicketList ticList = new org.aspcfs.modules.campioni.base.TicketList();
		Organization newOrg = null;
		//Process request parameters
		int passedId = Integer.parseInt(
				context.getRequest().getParameter("orgId"));

		//Prepare PagedListInfo
		PagedListInfo ticketListInfo = this.getPagedListInfo(
				context, "AccountTicketInfo", "t.entered", "desc");
		ticketListInfo.setLink(
				"Accounts.do?command=ViewCampioni&orgId=" + passedId);
		ticList.setPagedListInfo(ticketListInfo);
		try {
			db = this.getConnection(context);
			SystemStatus systemStatus = this.getSystemStatus(context);
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_campione");
			TipoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
			EsitoCampione.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
			//find record permissions for portal users
			if (!isRecordAccessPermitted(context, db, passedId)) {
				return ("PermissionError");
			}
			newOrg = new Organization(db, passedId);
			ticList.setOrgId(passedId);
			if (newOrg.isTrashed()) {
				ticList.setIncludeOnlyTrashed(true);
			}
			ticList.buildList(db);
			context.getRequest().setAttribute("TicList", ticList);
			context.getRequest().setAttribute("OrgDetails", newOrg);
			addModuleBean(context, "View Accounts", "Accounts View");
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "ViewCampioni");
	}

	public String executeCommandViewVigilanza(ActionContext context) {
		if (!hasPermission(context, "allevamenti-allevamenti-vigilanza-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		org.aspcfs.modules.vigilanza.base.TicketList ticList = new org.aspcfs.modules.vigilanza.base.TicketList();
		Organization newOrg = null;
		//Process request parameters
		int passedId = Integer.parseInt(
				context.getRequest().getParameter("orgId"));

		//Prepare PagedListInfo
		PagedListInfo ticketListInfo = this.getPagedListInfo(
				context, "AccountTicketInfo", "t.entered", "desc");
		ticketListInfo.setLink(
				"Allevamenti.do?command=ViewVigilanza&orgId=" + passedId);
		ticList.setPagedListInfo(ticketListInfo);
		try {


			db = this.getConnection(context);
			SystemStatus systemStatus = this.getSystemStatus(context);
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoCampione", TipoCampione);


			LookupList AuditTipo = new LookupList(db, "lookup_audit_tipo");
			AuditTipo.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AuditTipo", AuditTipo);

			LookupList TipoAudit = new LookupList(db, "lookup_tipo_audit");
			TipoAudit.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoAudit", TipoAudit);

			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);


			org.aspcfs.modules.vigilanza.base.TicketList controlliList = new org.aspcfs.modules.vigilanza.base.TicketList();
			controlliList.setOrgId(passedId);

			LookupList EsitoCampione = new LookupList(db, "lookup_esito_campione");
			EsitoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("EsitoCampione", EsitoCampione);
			//find record permissions for portal users
			if (!isRecordAccessPermitted(context, db, passedId)) {
				return ("PermissionError");
			}
			newOrg = new Organization(db, passedId);
			ticList.setOrgId(passedId);
			if (newOrg.isTrashed()) {
				ticList.setIncludeOnlyTrashed(true);
			}
			
			String statusId = context.getRequest().getParameter("statusId");
			ticList.setStatusId(statusId);		
			
			UserBean thisUser = (UserBean) context.getSession().getAttribute("User"); 
			if (thisUser.getRoleId()==Role.RUOLO_CRAS)
				ticList.setIdRuolo(thisUser.getRoleId());
			
			ticList.buildList(db);

			context.getRequest().setAttribute("TicList", ticList);
			context.getRequest().setAttribute("OrgDetails", newOrg);
			addModuleBean(context, "View Accounts", "Accounts View");
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "ViewVigilanza");
	}


	/**
	 *  Description of the Method
	 *
	 * @param  context  Description of the Parameter
	 * @return          Description of the Return Value
	 */
	public String executeCommandDashboard(ActionContext context) {
		if (!hasPermission(context, "allevamenti-dashboard-view")) {
			if (!hasPermission(context, "allevamenti-allevamenti-view")) {
				return ("PermissionError");
			}
			//Bypass dashboard and search form for portal users
			if (isPortalUser(context)) {
				return (executeCommandSearch(context));
			}
			return (executeCommandSearchForm(context));
		}
		addModuleBean(context, "Dashboard", "Dashboard");
		CalendarBean calendarInfo = (CalendarBean) context.getSession().getAttribute(
				"AccountsCalendarInfo");
		if (calendarInfo == null) {
			calendarInfo = new CalendarBean(
					this.getUser(context, this.getUserId(context)).getLocale());
			calendarInfo.addAlertType(
					"Accounts", "org.aspcfs.modules.allevamenti.base.AccountsListScheduledActions", "Accounts");
			calendarInfo.setCalendarDetailsView("Accounts");
			context.getSession().setAttribute("AccountsCalendarInfo", calendarInfo);
		}


		return ("DashboardOK");
	}

	public String executeCommandToExportExcelOperatori(ActionContext context) {
		if (!hasPermission(context, "allevamenti-allevamenti-view")) {
			return ("PermissionError");
		}

		return "ToExportExcelOperatori";
	}




	/**
	 *  Search Accounts
	 *
	 * @param  context  Description of the Parameter
	 * @return          Description of the Return Value
	 */
	public String executeCommandSearch(ActionContext context) {
		if (!hasPermission(context, "allevamenti-allevamenti-view")) {
			return ("PermissionError");
		}

		String source = (String) context.getRequest().getParameter("source");
		OrganizationList organizationList = new OrganizationList();
		addModuleBean(context, "View Accounts", "Search Results");

		// associazione OSM - azienda zootecnica
		String visualizzaAllevamentiAssociati = (String) context.getRequest().getParameter("visualizzaAllevamentiAssociati");
		if (visualizzaAllevamentiAssociati != null) {
			context.getRequest().getSession().removeAttribute("SearchOrgListInfo");
		}

		//Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(
				context, "SearchOrgListInfo");

		String popup = context.getRequest().getParameter("popup");
		searchListInfo.setLink("Allevamenti.do?command=Search&popup="+popup);
		SystemStatus systemStatus = this.getSystemStatus(context);
		//Need to reset any sub PagedListInfos since this is a new account
		this.resetPagedListInfo(context);
		Connection db = null;
		try {
			db = this.getConnection(context);

			//For portal usr set source as 'searchForm' explicitly since
			//the search form is bypassed.
			//temporary solution for page redirection for portal user.
			if (isPortalUser(context)) {
				organizationList.setOrgSiteId(this.getUserSiteId(context));
				source = "searchForm";
			}
			organizationList.setTipologia(2);

			/*if((context.getParameter("searchAccountName"))!=null){
          organizationList.setBanca(context.getParameter("searchAccountName"));
          }*/
			//return if no criteria is selected
			/*if ((searchListInfo.getListView() == null || "".equals(
          searchListInfo.getListView())) && !"searchForm".equals(source)) {
        return "ListOK";
      }*/




			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(-2, "-- TUTTI --");
			context.getRequest().setAttribute("SiteIdList", siteList);

			LookupList specie = new LookupList(db, "lookup_specie_allevata");
			specie.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SpecieAllevata", specie);

			LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
			OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);


			LookupList TipologiaStruttura = new LookupList(db, "lookup_tipologia_struttura");
			TipologiaStruttura.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipologiaStruttura", TipologiaStruttura);


			LookupList OrientamentoProduttivo = new LookupList(db, "lookup_orientamento_produttivo");
			OrientamentoProduttivo.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("OrientamentoProduttivo", OrientamentoProduttivo);


			//Display list of accounts if user chooses not to list contacts
			if (!"true".equals(searchListInfo.getCriteriaValue("searchContacts"))) {
				if ("contacts".equals(context.getSession().getAttribute("previousSearchType"))) {
					this.deletePagedListInfo(context, "SearchOrgListInfo");
					searchListInfo = this.getPagedListInfo(context, "SearchOrgListInfo");
					searchListInfo.setLink("Allevamenti.do?command=Search");
				}
				//Build the organization list
				organizationList.setPagedListInfo(searchListInfo);
				organizationList.setMinerOnly(false);
				organizationList.setTypeId(searchListInfo.getFilterKey("listFilter1"));
				/*if((context.getParameter("searchAccountName"))!=null){
            organizationList.setBanca(context.getParameter("searchAccountName"));
            }*/
				organizationList.setStageId(searchListInfo.getCriteriaValue("searchcodeStageId"));

				searchListInfo.setSearchCriteria(organizationList, context);
				//fetching criterea for account source (my accounts or all accounts)
				if ("my".equals(searchListInfo.getListView())) {
					organizationList.setOwnerId(this.getUserId(context));
				}
				if (organizationList.getOrgSiteId() == Constants.INVALID_SITE) {
					organizationList.setOrgSiteId(this.getUserSiteId(context));
					organizationList.setIncludeOrganizationWithoutSite(false);
				} else if (organizationList.getOrgSiteId() == -1) {
					organizationList.setIncludeOrganizationWithoutSite(true);
				}
				//fetching criterea for account status (active, disabled or any)
				int enabled = searchListInfo.getFilterKey("listFilter2");
				organizationList.setIncludeEnabled(enabled);
				//If the user is a portal user, fetching only the
				//the organization that he access to
				//(i.e., the organization for which he is an account contact
				if (isPortalUser(context)) {
					organizationList.setOrgSiteId(this.getUserSiteId(context));
					organizationList.setIncludeOrganizationWithoutSite(false);
					organizationList.setOrgId(getPortalUserPermittedOrgId(context));
				}

				// associazione OSM - azienda zootecnica
				if (visualizzaAllevamentiAssociati != null) {
					// la richiesta arriva dalla scheda "OSM Registrati"  e l'associazione e' gia' presente
					String codAziendaZootecnicaAsString = (String) context.getRequest().getAttribute("codAziendaZootecnica");
					String idAssociazione = (String) context.getRequest().getAttribute("idAssociazione");
					if (codAziendaZootecnicaAsString != null && !"".equals(codAziendaZootecnicaAsString.trim())) {	        	
						// codice azienda zootecnica != null e non vuoto (esiste una azienda zootecnica associata all'OSM)
						// ricerchiamo gli allevamenti con codice azienda zootecnica associata all'OSM da cui parte la richiesta	        	
						searchListInfo.getSavedCriteria().put("searchcodeAccountNumber", codAziendaZootecnicaAsString);
						searchListInfo.setSearchCriteria(organizationList, context);
						organizationList.setPagedListInfo(searchListInfo);
						context.getRequest().setAttribute("idAssociazione", idAssociazione);        		
					}
					else {
						throw new Exception("Si sta tentando di associare un allevamento con codice azienda zootecnica non valido.");
					}
				}

				// associazione OSM - azienda zootecnica
				String ricercaAllevamentiAssociabiliParameter = (String)context.getRequest().getParameter("ricercaAllevamentiAssociabiliParameter");
				if (ricercaAllevamentiAssociabiliParameter != null && ricercaAllevamentiAssociabiliParameter.equals("1")) {
					// la richiesta arriva dalla scheda "OSM Registrati"  e l'associazione non e' presente
					// ricerchiamo gli allevamenti associabili
					organizationList.setFlagRicercaAllevamentiAssociabiliAdOsm(true);
					organizationList.setPagedListInfo(null);
					/*
        	// ricerca allevamenti appartenenti alla stessa ASL dell'OSM da cui parte la richiesta
        	org.aspcfs.modules.allevamenti.base.Organization allevamentoACuiAssociareOsm = (org.aspcfs.modules.allevamenti.base.Organization)context.getSession().getAttribute("allevamentoACuiAssociareOsm");
        	int siteIdOsm = allevamentoACuiAssociareOsm.getSiteId();		    
	        searchListInfo.getSavedCriteria().put("searchcodeOrgSiteId", siteIdOsm);
	        searchListInfo.setSearchCriteria(organizationList, context);
	        organizationList.setPagedListInfo(searchListInfo);
					 */	
				}

				organizationList.buildList(db);


				context.getRequest().setAttribute("OrgList", organizationList);



				context.getSession().setAttribute("previousSearchType", "accounts");

				// associazione OSM - azienda zootecnica
				if (ricercaAllevamentiAssociabiliParameter != null && ricercaAllevamentiAssociabiliParameter.equals("1")) {
					context.getRequest().setAttribute("ricercaAllevamentiAssociabiliAttribute", true);
				}

				if (context.getRequest().getParameter("popup")!=null && context.getRequest().getParameter("popup").equals("true") )
					return ("ListPopupOK");

				return "ListOK";

			} 
		} catch (Exception e) {
			//Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return "";
	}


	/**
	 *  ViewTickets: Displays Ticket history (open and closed) for a particular
	 *  Account.
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandViewTickets(ActionContext context) {
		if (!hasPermission(context, "allevamenti-allevamenti-tickets-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		TicketList ticList = new TicketList();
		Organization newOrg = null;
		//Process request parameters
		int passedId = Integer.parseInt(
				context.getRequest().getParameter("orgId"));

		//Prepare PagedListInfo
		PagedListInfo ticketListInfo = this.getPagedListInfo(
				context, "AccountTicketInfo", "t.entered", "desc");
		ticketListInfo.setLink(
				"Allevamenti.do?command=ViewTickets&orgId=" + passedId);
		ticList.setPagedListInfo(ticketListInfo);
		try {
			db = this.getConnection(context);
			//find record permissions for portal users
			if (!isRecordAccessPermitted(context, db, passedId)) {
				return ("PermissionError");
			}
			newOrg = new Organization(db, passedId);
			ticList.setOrgId(passedId);
			if (newOrg.isTrashed()) {
				ticList.setIncludeOnlyTrashed(true);
			}
			ticList.buildList(db);
			context.getRequest().setAttribute("TicList", ticList);
			context.getRequest().setAttribute("OrgDetails", newOrg);
			addModuleBean(context, "View Accounts", "Accounts View");
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return getReturn(context, "ViewTickets");
	}

	//importazione allevamenti
	public String executeCommandAllevamentiUpload(ActionContext context){
		Connection db = null;

		try {
			db = getConnection(context);

			LookupList llist=new LookupList(db,"lookup_specie_allevata");
			llist.addItem(-1, "-nessuno-");
			context.getRequest().setAttribute("SpecieA", llist);

			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
			if (thisUser.getRoleId()==21){ //UTENTE COMUNE
				context.getRequest().setAttribute("UserComune", thisUser);
			}

			if (thisUser.getRoleId()==23){ //UTENTE PROVINCIA
				context.getRequest().setAttribute("UserProvincia", thisUser);
			}		     



		} catch (Exception e) {	
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("AllevamentiUploadAddOK");

	}

	public String executeCommandCapiUpload(ActionContext context){
		Connection db = null;

		try {
			db = getConnection(context);



			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
			if (thisUser.getRoleId()==21){ //UTENTE COMUNE
				context.getRequest().setAttribute("UserComune", thisUser);
			}

			if (thisUser.getRoleId()==23){ //UTENTE PROVINCIA
				context.getRequest().setAttribute("UserProvincia", thisUser);
			}		     



		} catch (Exception e) {	
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("AllevamentiCapiUploadAddOK");

	}


	public String executeCommandUploadDoc(ActionContext context){
		HttpServletRequest req = context.getRequest();
		String action = req.getParameter("action");
		Connection db = null;

		try {

			db = getConnection(context);
			MultipartRequest multi = null;
			if (action==null){
				int maxUploadSize = 50000000;
				multi = new MultipartRequest( req, ".", maxUploadSize );
			}

			/*LookupList llist = new LookupList(db,"lookup_specie_allevata");
		    llist.addItem(-1, "-nessuno-");
		    context.getRequest().setAttribute("SpecieA", llist);*/

			String specieA = multi.getParameter("SpecieA");
			context.getRequest().setAttribute("SpecieA", specieA);
			File myFileT = multi.getFile("file1"); 
			BufferedReader input = new BufferedReader(new FileReader(myFileT));

			String logPath = getWebContentPath(context, "logAllevamenti");

			String pathDownloadLog = LeggiFile.leggiCampi(context, db,input,logPath,specieA, getUserId(context));

			String[] data = pathDownloadLog.split("[ _ ]");
			String dataOperazione = data[0].trim()+"/"+data[1].trim()+"/"+data[2].trim();

			RiepilogoImport rImport = new RiepilogoImport( dataOperazione, pathDownloadLog );
			rImport.insert(db);

			context.getRequest().setAttribute("pathLog", pathDownloadLog);

		} catch (Exception e) {	
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("AllevamentiUploadSaveOK");

	}


	public String executeCommandUploadCapiDoc(ActionContext context){
		HttpServletRequest req = context.getRequest();
		String action = req.getParameter("action");
		Connection db = null;

		try {

			db = getConnection(context);
			MultipartRequest multi = null;
			if (action==null){
				int maxUploadSize = 50000000;
				multi = new MultipartRequest( req, ".", maxUploadSize );
			}

			/*LookupList llist = new LookupList(db,"lookup_specie_allevata");
		    llist.addItem(-1, "-nessuno-");
		    context.getRequest().setAttribute("SpecieA", llist);*/


			File myFileT = multi.getFile("file1"); 
			BufferedReader input = new BufferedReader(new FileReader(myFileT));

			String logPath = getWebContentPath(context, "logAllevamenti");

			String pathDownloadLog = CapiAllevamentiUtil.leggiCampiCapiAllevamenti(input, logPath, db);

			String[] data = pathDownloadLog.split("[ _ ]");
			String dataOperazione = data[0].trim()+"/"+data[1].trim()+"/"+data[2].trim();

			RiepilogoImport rImport = new RiepilogoImport( dataOperazione, pathDownloadLog );
			rImport.insert(db);

			context.getRequest().setAttribute("pathLog", pathDownloadLog);

		} catch (Exception e) {	
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("AllevamentiCapiUploadSaveOK");

	}


	public String executeCommandAllImportRecords(ActionContext context){
		Connection db = null;

		try {

			db = getConnection(context);

			RiepilogoImport rImport = new RiepilogoImport();
			rImport.buildList(db);

			context.getRequest().setAttribute("allRecords", rImport.getAllRecord());

		} catch (Exception e) {	
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("AllevamentiUploadListOK");

	}


	public String executeCommandAllImportCapiRecords(ActionContext context){
		Connection db = null;

		try {

			db = getConnection(context);

			RiepilogoImport rImport = new RiepilogoImport();
			rImport.buildListCapi(db);

			context.getRequest().setAttribute("allRecords", rImport.getAllRecord());

		} catch (Exception e) {	
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("AllevamentiUploadListCapiOK");

	}

	/**
	 *  Insert: Inserts a new Account into the database.
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandInsert(ActionContext context) {
		if (!hasPermission(context, "allevamenti-allevamenti-add")) {
			return ("PermissionError");
		}
		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		Organization insertedOrg = null;
		SystemStatus systemStatus = this.getSystemStatus(context);

		Organization newOrg = (Organization) context.getFormBean();


		/*if(newOrg.getOrgId() == -1){
    	newOrg = (Organization) context.getRequest().getAttribute("allevamento");
    }*/

		newOrg.setTypeList(context.getRequest().getParameterValues("selectedList"));
		newOrg.setEnteredBy(getUserId(context));
		newOrg.setModifiedBy(getUserId(context));
		newOrg.setOwner(getUserId(context));

		try {
			db = this.getConnection(context);


			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);


			LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
			OrgCategoriaRischioList.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);



			newOrg.setRequestItems(context);

			if (this.getUserSiteId(context) != -1) {
				// Set the site Id of the account to be equal to the site Id of the user
				// for a new account
				if (newOrg.getId() == -1) {
					newOrg.setSiteId(this.getUserSiteId(context));
				} else {
					// Check whether the user has access to update the organization
					if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
						return ("PermissionError");
					}
				}
			}

			isValid = this.validateObject(context, db, newOrg);
			if (isValid) {
				recordInserted = newOrg.insert(db,context);
			}
			if (recordInserted) {
				insertedOrg = new Organization(db, newOrg.getOrgId());
				context.getRequest().setAttribute("OrgDetails", insertedOrg);
				addRecentItem(context, newOrg);
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");
		if (recordInserted) {
			String target = context.getRequest().getParameter("target");
			if (context.getRequest().getParameter("popup") != null) {
				return ("ClosePopupOK");
			}
			if (target != null && "add_contact".equals(target)) {
				return ("InsertAndAddContactOK");  
			} else {
				return ("InsertOK");
			}
		}
		return (executeCommandAdd(context));
	}


	public String executeCommandUpdateCatRischio(ActionContext context) {
		if (!(hasPermission(context, "allevamenti-tipochecklist-edit"))) {
			return ("PermissionError");
		}
		Connection db = null;
		int resultCount = -1;
		boolean isValid = false;
		String org_id = context.getRequest().getParameter( "orgId" );
		String account_size = context.getRequest().getParameter( "accountSize" );
		Organization oldOrg = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		try {
			db = this.getConnection(context);
			//set the name to namelastfirstmiddle if individual

			Organization newOrg = new Organization( db, Integer.parseInt( org_id ) );
			newOrg.setAccountSize( account_size );
			newOrg.setTypeList(
					context.getRequest().getParameterValues("selectedList"));
			newOrg.setModifiedBy(getUserId(context));
			newOrg.setEnteredBy(getUserId(context));

			oldOrg = new Organization(db, newOrg.getOrgId());
			isValid = this.validateObject(context, db, newOrg);
			if (isValid) {
				resultCount = newOrg.update(db,context);
			}

		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Modify Account");

		return ("UpdateCatRischioOK");
	}


	/**
	 *  Update: Updates the Organization table to reflect user-entered
	 *  changes/modifications to the currently selected Account
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandUpdate(ActionContext context) {
		if (!(hasPermission(context, "allevamenti-allevamenti-edit"))) {
			return ("PermissionError");
		}
		Connection db = null;
		int resultCount = -1;
		boolean isValid = false;
		Organization newOrg = (Organization) context.getFormBean();
		Organization oldOrg = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		newOrg.setTypeList(
				context.getRequest().getParameterValues("selectedList"));
		newOrg.setModifiedBy(getUserId(context));
		newOrg.setEnteredBy(getUserId(context));
		try {
			db = this.getConnection(context);
			//set the name to namelastfirstmiddle if individual

			newOrg.setRequestItems(context);

			oldOrg = new Organization(db, newOrg.getOrgId());
			isValid = this.validateObject(context, db, newOrg);
			if (isValid) {
				resultCount = newOrg.update(db,context);
			}
			if (resultCount == 1) {
				processUpdateHook(context, oldOrg, newOrg);
				//if this is an individual account, populate and update the primary contact


				LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
				OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
				context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Modify Account");

		if (resultCount == -1 || !isValid) {
			return (executeCommandModify(context));
		} else if (resultCount == 1) {
			if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
					"return").equals("list")) {
				return (executeCommandSearch(context));
			} else if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
					"return").equals("dashboard")) {
				return (executeCommandDashboard(context));
			} else if (context.getRequest().getParameter("return") != null && context.getRequest().getParameter(
					"return").equals("Calendar")) {
				if (context.getRequest().getParameter("popup") != null) {
					return ("PopupCloseOK");
				}
			} else {
				return ("UpdateOK");
			}
		} else {
			context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
			return ("UserError");
		}
		return ("UpdateOK");
	}


	/**
	 *  Delete: Deletes an Account from the Organization table
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandDelete(ActionContext context) {
		if (!hasPermission(context, "allevamenti-allevamenti-delete")) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Exception errorMessage = null;
		boolean recordDeleted = false;
		Organization thisOrganization = null;
		Connection db = null;
		try {
			db = this.getConnection(context);
			thisOrganization = new Organization(
					db, Integer.parseInt(context.getRequest().getParameter("orgId")));
			//check permission to record
			if (!isRecordAccessPermitted(context, db, thisOrganization.getId())) {
				return ("PermissionError");
			}
			if (context.getRequest().getParameter("action") != null) {
				if (((String) context.getRequest().getParameter("action")).equals(
						"delete")) {
					// NOTE: these may have different options later
					thisOrganization.setContactDelete(true);
					thisOrganization.setRevenueDelete(true);
					thisOrganization.setDocumentDelete(true);
					OpportunityHeaderList opportunityList = new OpportunityHeaderList();
					opportunityList.setOrgId(thisOrganization.getOrgId());
					opportunityList.buildList(db);
					opportunityList.invalidateUserData(context, db);
					thisOrganization.setForceDelete(
							context.getRequest().getParameter("forceDelete"));
					recordDeleted = thisOrganization.delete(
							db, context, getDbNamePath(context));
				} else if (((String) context.getRequest().getParameter("action")).equals(
						"disable")) {
					recordDeleted = thisOrganization.disable(db);
				}
			}
		} catch (Exception e) {
			errorMessage = e;
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Accounts", "Delete Account");
		if (errorMessage == null) {
			if (recordDeleted) {

				context.getRequest().setAttribute(
						"refreshUrl", "Allevamenti.do?command=Search");
				if ("disable".equals(context.getRequest().getParameter("action")) && "list".equals(
						context.getRequest().getParameter("return"))) {
					return executeCommandSearch(context);
				}
				return "DeleteOK";
			} else {
				processErrors(context, thisOrganization.getErrors());
				return (executeCommandSearch(context));
			} //ok!
		} else {

			context.getRequest().setAttribute(
					"actionError", systemStatus.getLabel(
							"object.validation.actionError.accountDeletion"));
			context.getRequest().setAttribute(
					"refreshUrl", "Allevamenti.do?command=Search");
			return ("DeleteError");
		}
	}


	/**
	 *  Description of the Method
	 *
	 * @param  context  Description of the Parameter
	 * @return          Description of the Return Value
	 */
	public String executeCommandTrash(ActionContext context) {
		if (!hasPermission(context, "allevamenti-allevamenti-delete")) {
			return ("PermissionError");
		}
		boolean recordUpdated = false;
		Organization thisOrganization = null;
		Connection db = null;
		try {
			db = this.getConnection(context);
			String orgId = context.getRequest().getParameter("orgId");
			//check permission to record
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
				return ("PermissionError");
			}
			thisOrganization = new Organization(db, Integer.parseInt(orgId));
			// NOTE: these may have different options later
			recordUpdated = thisOrganization.updateStatus(
					db, context, true, this.getUserId(context));
			this.invalidateUserData(context, this.getUserId(context));
			this.invalidateUserData(context, thisOrganization.getOwner());

		} catch (Exception e) {
			context.getRequest().setAttribute(
					"refreshUrl", "Allevamenti.do?command=Search");
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Accounts", "Delete Account");
		if (recordUpdated) {
			context.getRequest().setAttribute(
					"refreshUrl", "Allevamenti.do?command=Search");
			if ("list".equals(context.getRequest().getParameter("return"))) {
				return executeCommandSearch(context);
			}
			return "DeleteOK";
		} else {
			return (executeCommandSearch(context));
		}
	}


	/**
	 *  Description of the Method
	 *
	 * @param  context  Description of the Parameter
	 * @return          Description of the Return Value
	 */
	public String executeCommandRestore(ActionContext context) {
		if (!hasPermission(context, "allevamenti-allevamenti-delete")) {
			return ("PermissionError");
		}
		boolean recordUpdated = false;
		Organization thisOrganization = null;
		Connection db = null;
		try {
			db = this.getConnection(context);
			String orgId = context.getRequest().getParameter("orgId");
			//check permission to record
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
				return ("PermissionError");
			}
			thisOrganization = new Organization(db, Integer.parseInt(orgId));
			// NOTE: these may have different options later
			recordUpdated = thisOrganization.updateStatus(
					db, context, false, this.getUserId(context));
			this.invalidateUserData(context, this.getUserId(context));
			this.invalidateUserData(context, thisOrganization.getOwner());
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Accounts", "Delete Account");
		if (recordUpdated) {
			context.getRequest().setAttribute(
					"refreshUrl", "Allevamenti.do?command=Search");
			if ("list".equals(context.getRequest().getParameter("return"))) {
				return executeCommandSearch(context);
			}
			return this.executeCommandDetails(context);
		} else {
			return (executeCommandSearch(context));
		}
	}


	/**
	 *  Description of the Method
	 *
	 * @param  context  Description of the Parameter
	 * @return          Description of the Return Value
	 */
	public String executeCommandEnable(ActionContext context) {
		if (!hasPermission(context, "allevamenti-allevamenti-edit")) {
			return ("PermissionError");
		}
		boolean recordEnabled = false;
		Organization thisOrganization = null;
		Connection db = null;
		try {
			db = this.getConnection(context);
			thisOrganization = new Organization(
					db, Integer.parseInt(context.getRequest().getParameter("orgId")));
			recordEnabled = thisOrganization.enable(db);
			if (!recordEnabled) {
				this.validateObject(context, db, thisOrganization);
			}
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Accounts", "Delete Account");
		return (executeCommandSearch(context));
	}


	/**
	 *  Description of the Method
	 *
	 * @param  context  Description of the Parameter
	 * @return          Description of the Return Value
	 */
	public String executeCommandConfirmDelete(ActionContext context) {
		if (!hasPermission(context, "allevamenti-allevamenti-delete")) {
			return ("PermissionError");
		}
		Connection db = null;
		Organization thisOrg = null;
		HtmlDialog htmlDialog = new HtmlDialog();
		String id = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		if (context.getRequest().getParameter("id") != null) {
			id = context.getRequest().getParameter("id");
		}
		try {
			db = this.getConnection(context);
			thisOrg = new Organization(db, Integer.parseInt(id));
			//check permission to record
			if (!isRecordAccessPermitted(context, db, thisOrg.getId())) {
				return ("PermissionError");
			}


			htmlDialog.addMessage(
					systemStatus.getLabel("confirmdelete.caution") + "\n" );
			htmlDialog.setTitle(systemStatus.getLabel("confirmdelete.title"));
			/*htmlDialog.setHeader(systemStatus.getLabel("confirmdelete.header"));*/
			htmlDialog.addButton(
					systemStatus.getLabel("button.delete"), "javascript:window.location.href='Allevamenti.do?command=Trash&action=delete&orgId=" + thisOrg.getOrgId() + "&forceDelete=true" + "'");
			if (thisOrg.getEnabled()) {
				htmlDialog.addButton(
						systemStatus.getLabel("button.disableOnly"), "javascript:window.location.href='Allevamenti.do?command=Delete&orgId=" + thisOrg.getOrgId() + "&action=disable'");
			}
			htmlDialog.addButton(
					systemStatus.getLabel("button.cancel"), "javascript:parent.window.close()");
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getSession().setAttribute("Dialog", htmlDialog);
		return ("ConfirmDeleteOK");
	}


	/**
	 *  Modify: Displays the form used for modifying the information of the
	 *  currently selected Account
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandModify(ActionContext context) {
		if (!hasPermission(context, "allevamenti-allevamenti-edit")) {
			return ("PermissionError");
		}
		String orgid = context.getRequest().getParameter("orgId");
		context.getRequest().setAttribute("orgId", orgid);

		int tempid = Integer.parseInt(orgid);
		Connection db = null;
		Organization newOrg = null;
		try {
			db = this.getConnection(context);
			newOrg = (Organization) context.getFormBean();
			if (newOrg.getId() == -1) {
				newOrg = new Organization(db, tempid);

			} 
			if (!isRecordAccessPermitted(context, db, newOrg.getId())) {
				return ("PermissionError");
			}

			SystemStatus systemStatus = this.getSystemStatus(context);
			context.getRequest().setAttribute("systemStatus", systemStatus);


			LookupList OrgCategoriaRischioList = new LookupList(db, "lookup_org_catrischio");
			OrgCategoriaRischioList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("OrgCategoriaRischioList", OrgCategoriaRischioList);




			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);


			//Make the StateSelect and CountrySelect drop down menus available in the request.
			//This needs to be done here to provide the SystemStatus to the constructors, otherwise translation is not possible

			CountrySelect countrySelect = new CountrySelect(systemStatus);
			context.getRequest().setAttribute("CountrySelect", countrySelect);
			context.getRequest().setAttribute("systemStatus", systemStatus);


		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Account Modify");
		context.getRequest().setAttribute("OrgDetails", newOrg);
		if (context.getRequest().getParameter("popup") != null) {
			return ("PopupModifyOK");
		} else {
			return ("ModifyOK");
		}
	}

	public String executeCommandModificaCatRischio(ActionContext context) {
		if ((!hasPermission(context, "allevamenti-tipochecklist-add"))) {
			return ("PermissionError");
		}
		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Organization newOrg = null;
		try {
			String temporgId = context.getRequest().getParameter("orgId");
			if (temporgId == null) {
				temporgId = (String) context.getRequest().getAttribute("orgId");
			}
			int tempid = Integer.parseInt(temporgId);
			db = this.getConnection(context);
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(temporgId))) {
				return ("PermissionError");
			}
			newOrg = new Organization(db, tempid);
			//check whether or not the owner is an active User
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);



		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addRecentItem(context, newOrg);
		String action = context.getRequest().getParameter("action");
		if (action != null && action.equals("modify")) {
			//If user is going to the modify form
			addModuleBean(context, "Accounts", "Modify Account Details");
			return ("ModificaCatRischioOK");
		} else {
			//If user is going to the detail screen
			addModuleBean(context, "View Accounts", "View Account Details");
			context.getRequest().setAttribute("OrgDetails", newOrg);
			return getReturn(context, "ModificaCatRischio");
		}
	}

	/**
	 *  Description of the Method
	 *
	 * @param  context  Description of the Parameter
	 * @return          Description of the Return Value
	 */
	public String executeCommandFolderList(ActionContext context) {
		if (!(hasPermission(context, "allevamenti-allevamenti-folders-view"))) {
			return ("PermissionError");
		}
		Connection db = null;
		Organization thisOrganization = null;
		try {
			String orgId = context.getRequest().getParameter("orgId");
			db = this.getConnection(context);
			//check permission to record
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
				return ("PermissionError");
			}
			thisOrganization = new Organization(db, Integer.parseInt(orgId));
			context.getRequest().setAttribute("OrgDetails", thisOrganization);
			//Show a list of the different folders available in Accounts
			CustomFieldCategoryList thisList = new CustomFieldCategoryList();
			thisList.setLinkModuleId(Constants.ACCOUNTS);
			thisList.setLinkItemId(thisOrganization.getId());
			thisList.setIncludeEnabled(Constants.TRUE);
			thisList.setIncludeScheduled(Constants.TRUE);
			thisList.setBuildResources(false);
			thisList.setBuildTotalNumOfRecords(true);
			thisList.buildList(db);
			context.getRequest().setAttribute("CategoryList", thisList);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Accounts", "Custom Fields Details");
		return this.getReturn(context, "FolderList");
	}


	/**
	 *  Fields: Shows a list of custom field records that are located "within" the
	 *  selected Custom Folder. Also shows the details of a particular Custom
	 *  Field Record when it is selected (details page)
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandFields(ActionContext context) {
		if (!(hasPermission(context, "allevamenti-allevamenti-folders-view"))) {
			return ("PermissionError");
		}
		Connection db = null;
		Organization thisOrganization = null;
		String source = context.getRequest().getParameter("source");
		String actionStepId = context.getRequest().getParameter("actionStepId");
		if (source != null && !"".equals(source.trim())) {
			context.getRequest().setAttribute("source", source);
		}
		if (actionStepId != null && !"".equals(actionStepId.trim())) {
			context.getRequest().setAttribute("actionStepId", actionStepId);
		}
		String recordId = null;
		boolean showRecords = true;
		String selectedCatId = null;
		try {
			String orgId = context.getRequest().getParameter("orgId");
			db = this.getConnection(context);
			//check permission to record
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
				return ("PermissionError");
			}
			thisOrganization = new Organization(db, Integer.parseInt(orgId));
			context.getRequest().setAttribute("OrgDetails", thisOrganization);

			//Show a list of the different folders available in Accounts
			CustomFieldCategoryList thisList = new CustomFieldCategoryList();
			thisList.setLinkModuleId(Constants.ACCOUNTS);
			thisList.setIncludeEnabled(Constants.TRUE);
			thisList.setIncludeScheduled(Constants.TRUE);
			thisList.setBuildResources(false);
			thisList.buildList(db);
			context.getRequest().setAttribute("CategoryList", thisList);

			//See which one is currently selected or use the default
			selectedCatId = (String) context.getRequest().getParameter("catId");
			if (selectedCatId == null) {
				selectedCatId = (String) context.getRequest().getAttribute("catId");
			}
			if (selectedCatId == null) {
				selectedCatId = "" + thisList.getDefaultCategoryId();
			}
			context.getRequest().setAttribute("catId", selectedCatId);

			if (Integer.parseInt(selectedCatId) > 0) {
				//See if a specific record has been chosen from the list
				recordId = context.getRequest().getParameter("recId");
				String recordDeleted = (String) context.getRequest().getAttribute(
						"recordDeleted");
				if (recordDeleted != null) {
					recordId = null;
				}

				//Now build the specified or default category
				CustomFieldCategory thisCategory = thisList.getCategory(
						Integer.parseInt(selectedCatId));
				if (recordId == null && thisCategory.getAllowMultipleRecords()) {
					//The user didn't request a specific record, so show a list
					//of records matching this category that the user can choose from
					PagedListInfo folderListInfo = this.getPagedListInfo(
							context, "AccountFolderInfo");
					folderListInfo.setLink(
							"Allevamenti.do?command=Fields&orgId=" + orgId + "&catId=" + selectedCatId);

					CustomFieldRecordList recordList = new CustomFieldRecordList();
					recordList.setLinkModuleId(Constants.ACCOUNTS);
					recordList.setLinkItemId(thisOrganization.getOrgId());
					recordList.setCategoryId(thisCategory.getId());
					recordList.buildList(db);
					recordList.buildRecordColumns(db, thisCategory);
					context.getRequest().setAttribute("Records", recordList);
				} else {
					//The user requested a specific record, or this category only
					//allows a single record.
					thisCategory.setLinkModuleId(Constants.ACCOUNTS);
					thisCategory.setLinkItemId(thisOrganization.getOrgId());
					if (recordId != null) {
						thisCategory.setRecordId(Integer.parseInt(recordId));
					} else {
						thisCategory.buildRecordId(db);
						recordId = String.valueOf(thisCategory.getRecordId());
					}
					thisCategory.setIncludeEnabled(Constants.TRUE);
					thisCategory.setIncludeScheduled(Constants.TRUE);
					thisCategory.setBuildResources(true);
					thisCategory.buildResources(db);
					showRecords = false;

					if (thisCategory.getRecordId() > -1) {
						CustomFieldRecord thisRecord = new CustomFieldRecord(
								db, thisCategory.getRecordId());
						context.getRequest().setAttribute("Record", thisRecord);
					}
				}
				context.getRequest().setAttribute("Category", thisCategory);
			}
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Custom Fields Details");
		if (Integer.parseInt(selectedCatId) <= 0) {
			return getReturn(context, "FieldsEmpty");
		} else if (recordId == null && showRecords) {
			return getReturn(context, "FieldRecordList");
		} else {
			return getReturn(context, "Fields");
		}
	}


	/**
	 *  Description of the Method
	 *
	 * @param  context  Description of the Parameter
	 * @return          Description of the Return Value
	 */
	public String executeCommandCheckFields(ActionContext context) {
		if (!(hasPermission(context, "allevamenti-allevamenti-folders-view"))) {
			return ("PermissionError");
		}
		Connection db = null;
		Organization thisOrganization = null;
		String recordId = context.getRequest().getParameter("recId");
		String source = context.getRequest().getParameter("source");
		String actionStepId = context.getRequest().getParameter("actionStepId");
		if (source != null && !"".equals(source.trim())) {
			context.getRequest().setAttribute("source", source);
		}
		if (actionStepId != null && !"".equals(actionStepId.trim())) {
			context.getRequest().setAttribute("actionStepId", actionStepId);
		}
		boolean hasMultipleRecords = false;
		String selectedCatId = null;
		try {
			String orgId = context.getRequest().getParameter("orgId");
			db = this.getConnection(context);
			//check permission to record
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
				return ("PermissionError");
			}
			thisOrganization = new Organization(db, Integer.parseInt(orgId));
			context.getRequest().setAttribute("OrgDetails", thisOrganization);

			//Show a list of the different folders available in Accounts
			CustomFieldCategoryList thisList = new CustomFieldCategoryList();
			thisList.setLinkModuleId(Constants.ACCOUNTS);
			thisList.setIncludeEnabled(Constants.TRUE);
			thisList.setIncludeScheduled(Constants.TRUE);
			thisList.setBuildResources(false);
			thisList.buildList(db);
			context.getRequest().setAttribute("CategoryList", thisList);

			//See which one is currently selected or use the default
			selectedCatId = (String) context.getRequest().getParameter("catId");
			if (selectedCatId == null) {
				selectedCatId = (String) context.getRequest().getAttribute("catId");
			}
			if (selectedCatId == null) {
				selectedCatId = "" + thisList.getDefaultCategoryId();
			}
			context.getRequest().setAttribute("catId", selectedCatId);

			if (Integer.parseInt(selectedCatId) > 0) {
				//Now build the specified or default category
				CustomFieldCategory thisCategory = thisList.getCategory(
						Integer.parseInt(selectedCatId));
				thisCategory.setLinkModuleId(Constants.ACCOUNTS);
				thisCategory.setIncludeEnabled(Constants.TRUE);
				thisCategory.setIncludeScheduled(Constants.TRUE);
				thisCategory.setLinkItemId(thisOrganization.getOrgId());
				thisCategory.setBuildResources(true);
				thisCategory.buildRecordId(db);
				thisCategory.buildResources(db);
				if (recordId == null || "".equals(recordId.trim()) || "-1".equals(recordId)) {
					if (thisCategory.getRecordId() != -1) {
						recordId = String.valueOf(thisCategory.getRecordId());
					}
				}
				if ((recordId != null && !"".equals(recordId.trim())) && !"-1".equals(recordId)) {
					CustomFieldRecord thisRecord = new CustomFieldRecord(
							db, Integer.parseInt(recordId));
					context.getRequest().setAttribute("Record", thisRecord);
				}
				if (thisCategory.getRecordId() == -1 && thisCategory.getAllowMultipleRecords()) {
					//The user didn't request a specific record, so show a list
					//of records matching this category that the user can choose from
					PagedListInfo folderListInfo = this.getPagedListInfo(
							context, "AccountFolderInfo");
					folderListInfo.setLink(
							"Allevamenti.do?command=Fields&orgId=" + orgId + "&catId=" + selectedCatId);

					CustomFieldRecordList recordList = new CustomFieldRecordList();
					recordList.setLinkModuleId(Constants.ACCOUNTS);
					recordList.setLinkItemId(thisOrganization.getOrgId());
					recordList.setCategoryId(thisCategory.getId());
					recordList.buildList(db);
					recordList.buildRecordColumns(db, thisCategory);
					hasMultipleRecords = (recordList.size() > 0 && (recordId == null || "".equals(recordId.trim()))) ||
							(recordList.size() > 1 && recordId != null && !"".equals(recordId.trim()));
					context.getRequest().setAttribute("Records", recordList);
				} else if (thisCategory.getRecordId() != -1 && thisCategory.getAllowMultipleRecords()) {
					context.getRequest().setAttribute("recordDeleted", "true");
					// TODO: Executing a new action within an open db can create a deadlock
					return executeCommandFields(context);
				} else if (thisCategory.getRecordId() != -1 && !thisCategory.getAllowMultipleRecords()) {
					context.getRequest().setAttribute("recId", recordId);
					// TODO: Executing a new action within an open db can create a deadlock
					return executeCommandModifyFields(context);
				} else if (thisCategory.getRecordId() == -1 && !thisCategory.getAllowMultipleRecords()) {
					// TODO: Executing a new action within an open db can create a deadlock
					return executeCommandAddFolderRecord(context);
				}
				//The user requested a specific record, or this category only
				//allows a single record.
				context.getRequest().setAttribute("Category", thisCategory);
			}
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Custom Fields Details");
		if (Integer.parseInt(selectedCatId) <= 0) {
			return getReturn(context, "FieldsEmpty");
		} else {
			if (hasMultipleRecords) {
				return executeCommandModifyFields(context);
			}
			return getReturn(context, "FieldRecordList");
		}
	}


	/**
	 *  AddFolderRecord: Displays the form for inserting a new custom field record
	 *  for the selected Account.
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandAddFolderRecord(ActionContext context) {
		if (!(hasPermission(context, "allevamenti-allevamenti-folders-add"))) {
			return ("PermissionError");
		}
		Connection db = null;
		String source = context.getRequest().getParameter("source");
		String actionStepId = context.getRequest().getParameter("actionStepId");
		if (source != null && !"".equals(source.trim())) {
			context.getRequest().setAttribute("source", source);
		}
		if (actionStepId != null && !"".equals(actionStepId.trim())) {
			context.getRequest().setAttribute("actionStepId", actionStepId);
		}
		Organization thisOrganization = null;
		try {
			String orgId = context.getRequest().getParameter("orgId");
			db = this.getConnection(context);
			//check permission to record
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
				return ("PermissionError");
			}
			thisOrganization = new Organization(db, Integer.parseInt(orgId));
			context.getRequest().setAttribute("OrgDetails", thisOrganization);

			String selectedCatId = (String) context.getRequest().getParameter(
					"catId");
			CustomFieldCategory thisCategory = new CustomFieldCategory(
					db,
					Integer.parseInt(selectedCatId));
			thisCategory.setLinkModuleId(Constants.ACCOUNTS);
			thisCategory.setLinkItemId(thisOrganization.getOrgId());
			thisCategory.setIncludeEnabled(Constants.TRUE);
			thisCategory.setIncludeScheduled(Constants.TRUE);
			thisCategory.setBuildResources(true);
			thisCategory.buildResources(db);
			context.getRequest().setAttribute("Category", thisCategory);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Add Folder Record");
		context.getRequest().setAttribute(
				"systemStatus", this.getSystemStatus(context));
		return getReturn(context, "AddFolderRecord");
	}


	/**
	 *  ModifyFields: Displays the modify form for the selected Custom Field
	 *  Record.
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandModifyFields(ActionContext context) {
		if (!hasPermission(context, "allevamenti-allevamenti-folders-edit")) {
			return ("PermissionError");
		}
		Connection db = null;
		Organization thisOrganization = null;
		String source = context.getRequest().getParameter("source");
		String actionStepId = context.getRequest().getParameter("actionStepId");
		String specie_allev = context.getRequest().getParameter("specie_allev");
		context.getRequest().setAttribute("specie_allev", specie_allev);
		if (source != null && !"".equals(source.trim())) {
			context.getRequest().setAttribute("source", source);
		}
		if (actionStepId != null && !"".equals(actionStepId.trim())) {
			context.getRequest().setAttribute("actionStepId", actionStepId);
		}
		String selectedCatId = (String) context.getRequest().getParameter("catId");
		String recordId = (String) context.getRequest().getParameter("recId");
		if (recordId == null || "".equals(recordId.trim()) || "-1".equals(recordId.trim())) {
			recordId = (String) context.getRequest().getAttribute("recId");
			if (recordId == null || "".equals(recordId.trim())) {
				recordId = String.valueOf(-1);
			}
		}
		try {
			String orgId = context.getRequest().getParameter("orgId");
			db = this.getConnection(context);
			//check permission to record
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
				return ("PermissionError");
			}
			thisOrganization = new Organization(db, Integer.parseInt(orgId));
			context.getRequest().setAttribute("OrgDetails", thisOrganization);

			CustomFieldCategory thisCategory = new CustomFieldCategory(
					db,
					Integer.parseInt(selectedCatId));
			thisCategory.setLinkModuleId(Constants.ACCOUNTS);
			thisCategory.setLinkItemId(thisOrganization.getOrgId());
			thisCategory.setRecordId(Integer.parseInt(recordId));
			thisCategory.setIncludeEnabled(Constants.TRUE);
			thisCategory.setIncludeScheduled(Constants.TRUE);
			thisCategory.setBuildResources(true);
			thisCategory.buildResources(db);
			context.getRequest().setAttribute("Category", thisCategory);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Modify Custom Fields");
		context.getRequest().setAttribute(
				"systemStatus", this.getSystemStatus(context));
		if (recordId.equals("-1")) {
			return getReturn(context, "AddFolderRecord");
		} else {
			return getReturn(context, "ModifyFields");
		}
	}


	/**
	 *  UpdateFields: Performs the actual update of the selected Custom Field
	 *  Record based on user-submitted information from the modify form.
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandUpdateFields(ActionContext context) {
		if (!(hasPermission(context, "allevamenti-allevamenti-folders-edit"))) {
			return ("PermissionError");
		}
		Connection db = null;
		boolean popup = false;
		String source = context.getRequest().getParameter("source");
		String actionStepId = context.getRequest().getParameter("actionStepId");
		if (source != null && !"".equals(source.trim())) {
			context.getRequest().setAttribute("source", source);
		}
		if (actionStepId != null && !"".equals(actionStepId.trim())) {
			context.getRequest().setAttribute("actionStepId", actionStepId);
		}
		String popupString = context.getRequest().getParameter("popup");
		popup = (popupString != null && !"".equals(popupString.trim()) && "true".equals(popupString));
		Organization thisOrganization = null;
		int resultCount = 0;
		boolean isValid = false;
		context.getRequest().setAttribute(
				"systemStatus", this.getSystemStatus(context));

		try {
			String orgId = context.getRequest().getParameter("orgId");
			db = this.getConnection(context);
			//check permission to record
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
				return ("PermissionError");
			}
			thisOrganization = new Organization(db, Integer.parseInt(orgId));
			context.getRequest().setAttribute("OrgDetails", thisOrganization);

			CustomFieldCategoryList thisList = new CustomFieldCategoryList();
			thisList.setLinkModuleId(Constants.ACCOUNTS);
			thisList.setIncludeEnabled(Constants.TRUE);
			thisList.setIncludeScheduled(Constants.TRUE);
			thisList.setBuildResources(false);
			thisList.buildList(db);
			context.getRequest().setAttribute("CategoryList", thisList);

			String selectedCatId = (String) context.getRequest().getParameter(
					"catId");
			String recordId = (String) context.getRequest().getParameter("recId");
			context.getRequest().setAttribute("catId", selectedCatId);
			CustomFieldCategory thisCategory = new CustomFieldCategory(
					db,
					Integer.parseInt(selectedCatId));
			thisCategory.setLinkModuleId(Constants.ACCOUNTS);
			thisCategory.setLinkItemId(thisOrganization.getOrgId());
			thisCategory.setRecordId(Integer.parseInt(recordId));
			thisCategory.setIncludeEnabled(Constants.TRUE);
			thisCategory.setIncludeScheduled(Constants.TRUE);
			thisCategory.setBuildResources(true);
			thisCategory.buildResources(db);
			thisCategory.setParameters(context);
			thisCategory.setModifiedBy(this.getUserId(context));
			if (!thisCategory.getReadOnly()) {
				thisCategory.setCanNotContinue(true);
				isValid = this.validateObject(context, db, thisCategory);
				if (isValid) {
					Iterator groups = (Iterator) thisCategory.iterator();
					while (groups.hasNext()) {
						CustomFieldGroup group = (CustomFieldGroup) groups.next();
						Iterator fields = (Iterator) group.iterator();
						while (fields.hasNext()) {
							CustomField field = (CustomField) fields.next();
							field.setValidateData(true);
							field.setRecordId(thisCategory.getRecordId());
							isValid = this.validateObject(context, db, field) && isValid;
						}
					}
				}
				if (isValid && resultCount != -1) {
					thisCategory.setCanNotContinue(true);
					resultCount = thisCategory.update(db);
					thisCategory.setCanNotContinue(false);
					resultCount = thisCategory.insertGroup(
							db, thisCategory.getRecordId());
				}
			}
			context.getRequest().setAttribute("Category", thisCategory);
			if (resultCount != -1 && isValid) {
				thisCategory.buildResources(db);
				CustomFieldRecord thisRecord = new CustomFieldRecord(
						db, thisCategory.getRecordId());
				context.getRequest().setAttribute("Record", thisRecord);
			} else {
				if (System.getProperty("DEBUG") != null) {
					System.out.println("allevamenti-> ModifyField validation error");
				}
				if (actionStepId != null && !"".equals(actionStepId.trim())) {
					context.getRequest().setAttribute("recId", recordId);
				}
				context.getRequest().setAttribute(
						"systemStatus", this.getSystemStatus(context));
				return getReturn(context, "ModifyFields");
			}
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		if (resultCount == 1 && isValid) {
			if (context.getRequest().getParameter("source") != null && "attachplan".equals(context.getRequest().getParameter("source"))) {
				return "UpdateFieldsAttachPlanOK";
			}
			return getReturn(context, "UpdateFields");
		} else {
			context.getRequest().setAttribute(
					"Error", CFSModule.NOT_UPDATED_MESSAGE);
			return ("UserError");
		}
	}


	/**
	 *  InsertFields: Performs the actual insert of a new Custom Field Record.
	 *
	 * @param  context  Description of Parameter
	 * @return          Description of the Returned Value
	 */
	public String executeCommandInsertFields(ActionContext context) {
		if (!(hasPermission(context, "allevamenti-allevamenti-folders-add"))) {
			return ("PermissionError");
		}
		boolean popup = false;
		String source = context.getRequest().getParameter("source");
		String actionStepId = context.getRequest().getParameter("actionStepId");
		if (source != null && !"".equals(source.trim())) {
			context.getRequest().setAttribute("source", source);
		}
		if (actionStepId != null && !"".equals(actionStepId.trim())) {
			context.getRequest().setAttribute("actionStepId", actionStepId);
		}
		String popupString = context.getRequest().getParameter("popup");
		popup = (popupString != null && !"".equals(popupString.trim()) && "true".equals(popupString));
		Connection db = null;
		int resultCode = -1;
		boolean isValid = false;
		Organization thisOrganization = null;

		try {
			db = this.getConnection(context);
			String orgId = context.getRequest().getParameter("orgId");
			//check permission to record
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
				return ("PermissionError");
			}
			thisOrganization = new Organization(db, Integer.parseInt(orgId));
			context.getRequest().setAttribute("OrgDetails", thisOrganization);

			CustomFieldCategoryList thisList = new CustomFieldCategoryList();
			thisList.setLinkModuleId(Constants.ACCOUNTS);
			thisList.setIncludeEnabled(Constants.TRUE);
			thisList.setIncludeScheduled(Constants.TRUE);
			thisList.setBuildResources(false);
			thisList.buildList(db);
			context.getRequest().setAttribute("CategoryList", thisList);

			String selectedCatId = (String) context.getRequest().getParameter(
					"catId");
			context.getRequest().setAttribute("catId", selectedCatId);
			CustomFieldCategory thisCategory = new CustomFieldCategory(
					db,
					Integer.parseInt(selectedCatId));
			thisCategory.setLinkModuleId(Constants.ACCOUNTS);
			thisCategory.setLinkItemId(thisOrganization.getOrgId());
			thisCategory.setIncludeEnabled(Constants.TRUE);
			thisCategory.setIncludeScheduled(Constants.TRUE);
			thisCategory.setBuildResources(true);
			thisCategory.buildResources(db);
			thisCategory.setParameters(context);
			thisCategory.setEnteredBy(this.getUserId(context));
			thisCategory.setModifiedBy(this.getUserId(context));
			if (!thisCategory.getReadOnly()) {
				thisCategory.setCanNotContinue(true);
				isValid = this.validateObject(context, db, thisCategory);
				if (isValid) {
					resultCode = thisCategory.insert(db);
					Iterator groups = (Iterator) thisCategory.iterator();
					while (groups.hasNext()) {
						CustomFieldGroup group = (CustomFieldGroup) groups.next();
						Iterator fields = (Iterator) group.iterator();
						while (fields.hasNext()) {
							CustomField field = (CustomField) fields.next();
							field.setValidateData(true);
							field.setRecordId(thisCategory.getRecordId());
							isValid = this.validateObject(context, db, field) && isValid;
						}
					}
				}
				thisCategory.setCanNotContinue(false);
				if (isValid && resultCode != -1) {
					resultCode = thisCategory.insertGroup(
							db, thisCategory.getRecordId());
				}
			}
			context.getRequest().setAttribute("Category", thisCategory);
			if (resultCode != -1 && isValid) {
				processInsertHook(context, thisCategory);
			} else {
				if (thisCategory.getRecordId() != -1) {
					CustomFieldRecord record = new CustomFieldRecord(
							db, thisCategory.getRecordId());
					record.delete(db);
				}
				if (System.getProperty("DEBUG") != null) {
					System.out.println("allevamenti-> InsertField validation error");
				}
				context.getRequest().setAttribute(
						"systemStatus", this.getSystemStatus(context));
				return getReturn(context, "AddFolderRecord");
			}
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		if (popup) {
			if (context.getRequest().getParameter("source") != null && "attachplan".equals(context.getRequest().getParameter("source"))) {
				return "InsertFieldsAttachPlanOK";
			}
		}
		return (this.executeCommandFields(context));
	}


	/**
	 *  Description of the Method
	 *
	 * @param  context  Description of the Parameter
	 * @return          Description of the Return Value
	 */
	public String executeCommandDeleteFields(ActionContext context) {
		if (!(hasPermission(context, "allevamenti-allevamenti-folders-delete"))) {
			return ("PermissionError");
		}
		String source = context.getRequest().getParameter("source");
		String popupString = context.getRequest().getParameter("popup");
		boolean popup = (popupString != null && "true".equals(popupString.trim()));
		boolean returnToList = true;
		String actionStepId = context.getRequest().getParameter("actionStepId");
		ActionItemWork item = null;
		if (source != null && !"".equals(source.trim())) {
			context.getRequest().setAttribute("source", source);
		}
		if (actionStepId != null && !"".equals(actionStepId.trim())) {
			context.getRequest().setAttribute("actionStepId", actionStepId);
		}
		Connection db = null;
		try {
			db = this.getConnection(context);
			String selectedCatId = context.getRequest().getParameter("catId");
			String recordId = context.getRequest().getParameter("recId");
			String orgId = context.getRequest().getParameter("orgId");

			//check permission to record
			if (!isRecordAccessPermitted(context, db, Integer.parseInt(orgId))) {
				return ("PermissionError");
			}

			CustomFieldCategory thisCategory = new CustomFieldCategory(
					db,
					Integer.parseInt(selectedCatId));

			CustomFieldRecord thisRecord = new CustomFieldRecord(
					db, Integer.parseInt(recordId));
			thisRecord.setLinkModuleId(Constants.ACCOUNTS);
			thisRecord.setLinkItemId(Integer.parseInt(orgId));
			thisRecord.setCategoryId(Integer.parseInt(selectedCatId));
			if (actionStepId != null && !"".equals(actionStepId.trim()) && !"-1".equals(actionStepId) && !"null".equals(actionStepId)) {
				item = new ActionItemWork(db, Integer.parseInt(actionStepId));
			}
			if (!thisCategory.getReadOnly()) {
				thisRecord.delete(db);
			}
			context.getRequest().setAttribute("recordDeleted", "true");
			if (item != null && item.getId() > 0) {
				if (thisRecord.getId() == item.getLinkItemId() && item.getLinkModuleId() == ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACCOUNTS)) {
					returnToList = false;
					thisCategory.setRecordId(thisRecord.getId());
					context.getRequest().setAttribute("Category", thisCategory);
				}
			}
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		if (returnToList) {
			return getReturn(context, "DeleteFields");
		}
		return "DeletedFieldPopupOK";
	}


	/**
	 *  Description of the Method
	 *
	 * @param  context  Description of the Parameter
	 */
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


	/**
	 *  Description of the Method
	 *
	 * @param  context  Description of the Parameter
	 * @return          Description of the Return Value
	 */
	public String executeCommandOwnerJSList(ActionContext context) {

		int siteId = -1;
		String siteIdString = context.getRequest().getParameter("siteId");
		if ((siteIdString != null) || !"".equals(siteIdString)) {
			siteId = Integer.parseInt(siteIdString);
		}
		if (!isSiteAccessPermitted(context, String.valueOf(siteId))) {
			return ("PermissionError");
		}
		UserList userList = new UserList();
		HtmlSelect userListSelect = new HtmlSelect();
		try {
			userList = filterOwnerListForSite(context, siteId);
			userListSelect = userList.getHtmlSelectObj("userId", this.getUserId(context));
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}
		context.getRequest().setAttribute("UserListSelect", userListSelect);
		return ("OwnerJsListOK");
	}


	/**
	 *  Description of the Method
	 *
	 * @param  context        Description of the Parameter
	 * @param  siteId         Description of the Parameter
	 * @return                Description of the Return Value
	 * @exception  Exception  Description of the Exception
	 */
	private UserList filterOwnerListForSite(ActionContext context, int siteId) throws Exception {
		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
		//this is how we get the multiple-level heirarchy...recursive function.
		User thisRec = thisUser.getUserRecord();
		UserList shortChildList = thisRec.getShortChildList();
		UserList userList = thisRec.getFullChildList(
				shortChildList, new UserList());
		userList.setMyId(getUserId(context));

		userList.setIncludeMe(true);
		userList.setExcludeDisabledIfUnselected(true);
		userList.setExcludeExpiredIfUnselected(true);

		// filter possible owners for accounts with site ids.

		// An account WITH a site id can be owned by a user with the same
		// site id or by one who has access to all sites
		// (i.e., siteId = -1 or site_id IS NULL)
		if (siteId != -1) {
			Iterator itr = userList.iterator();
			while (itr.hasNext()) {
				User tmpUser = (User) itr.next();
				if (tmpUser.getSiteId() == -1) {
					continue;
				}
				if (siteId != tmpUser.getSiteId()) {
					itr.remove();
				}
			}
		}

		// An account WITHOUT a site id can ONLY be owned by a user with access
		// to all sites
		// (i.e., siteId = -1 or site_id IS NULL)
		if (siteId == -1) {
			Iterator itr = userList.iterator();
			while (itr.hasNext()) {
				User tmpUser = (User) itr.next();
				if (siteId != tmpUser.getSiteId()) {
					itr.remove();
				}
			}
		}
		return userList;
	}

	/**
	 *  Description of the Method
	 *
	 * @param  context     Description of the Parameter
	 * @param  organization  Description of the Parameter
	 * @param  db  Description of the Parameter
	 *
	 * @exception  Exception  Description of the Exception
	 */

	/* aggiunto da d.dauria per la stampa del report */
	public String executeCommandPrintReport(ActionContext context) {
		if (!hasPermission(context, "requestor-requestor-view") && !hasPermission(context, "requestor-requestor-report-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		try {
			db = this.getConnection(context);
			String id = (String) context.getRequest().getParameter("id").split(";")[0];
			HashMap map = new HashMap();
			map.put("orgid", new Integer(id));
			map.put("path", getWebInfPath(context, "reports"));
			//provide the dictionary as a parameter to the quote report
			map.put("CENTRIC_DICTIONARY", this.getSystemStatus(context).getLocalizationPrefs());
			//String filename = "modB.xml";
			String filename = (String) context.getRequest().getParameter("file");

			//provide a seperate database connection for the subreports

			map.put("SCRIPT_DB_CONNECTION", db);

			//Replace the font based on the system language to support i18n chars
			String fontPath = getWebInfPath(context, "fonts");
			String reportDir = getWebInfPath(context, "reports");
			JasperReport jasperReport = JasperReportUtils.getReport(reportDir + filename);
			String language = getPref(context, "SYSTEM.LANGUAGE");

			JasperReportUtils.modifyFontProperties(jasperReport, reportDir, fontPath, language);

			byte[] bytes = JasperRunManager.runReportToPdf(jasperReport, map, db);

			if (bytes != null) {
				FileDownload fileDownload = new FileDownload();
				if(filename.equals("allevamenti.xml"))
					fileDownload.setDisplayName("Allevamento_" + id + ".pdf");
				else if (filename.equals("modelloC.xml"))
					fileDownload.setDisplayName("Allevamento_" + id + ".pdf");
				else
					fileDownload.setDisplayName("Allevamento_" + id + ".pdf");
				fileDownload.sendFile(context, bytes, "application/pdf");
			} else {
				return ("SystemError");
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return ("-none-");
	}

	public String executeCommandStampaSchedaAllevamento(ActionContext context) {
		if (!hasPermission(context, "requestor-requestor-view") && !hasPermission(context, "requestor-requestor-report-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		try {
			db = this.getConnection(context);
			String id = (String) context.getRequest().getParameter("id").split(";")[0];
			String filename = (String) context.getRequest().getParameter("file");
			Organization org = new Organization(db, Integer.parseInt(id));
			context.getRequest().setAttribute("orgDetails", org);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("SiteList", siteList);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}
		return ("StampaSchedaAllevamentoOk");
	}


	/* AGGIUNTO DA D.ZANFARDINO PER LA COMPARAZIONE DEI DATI ALLEVAMENTO CON WS DELLA BDN */
	public String executeCommandCompareWS(ActionContext context) {
		AllevamentoAjax allevamento = null;
		InfoAziendaBean aziendaRet = null;
		Organization OrgDetails = null;
		ArrayList diff = null;
		Connection db = null;
		try {
			db = this.getConnection(context);

			LookupList listaSpeci = new LookupList(db,"lookup_specie_allevata");

			String orgId = context.getParameter("orgId");
			String codice_azienda = context.getParameter("codiceAzienda");
			String partita_iva = context.getParameter("pIva");
			String allevIdInput = context.getParameter("allevId");
			String codice_specie = context.getParameter("codSpecie");
			InfoAllevamentoBean tmpIAB = new InfoAllevamentoBean();
			String descrizione_specie = tmpIAB.getAltShortDescriptionAllevataByShort(codice_specie, db);
			String denominazione = context.getParameter("denominazione");


			denominazione.replaceAll("|", "'");

			AjaxCalls ajaxCalls = new AjaxCalls();
			
			
			
			if(codice_specie.equalsIgnoreCase("0200") || codice_specie.equalsIgnoreCase("0160") || codice_specie.equalsIgnoreCase("0201"))
			{
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
				
				String responseAzienda = null;
				String responseAllevamento = null;
				String responseProprietario = null;
				String responseResponsabile = null;

				System.out.println(" ------ RICHIESTA DATI AZIENDA ACQUACOLTURA " + codice_azienda + " -----"); 
					
				WsPost ws = new WsPost();  

				//Recupero dati azienda e allevamento
				ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_ACQUACOLTURA_BDN"));
					
				String envelopeAzienda 	   = costruisciEnvelope("Azienda", codice_azienda, null);
				String envelopeAllevamento = costruisciEnvelope("Allevamento", codice_azienda, null);
				
				System.out.println("Metodo executeCommandImportGisa: envelopeAzienda: " + envelopeAzienda);
				System.out.println("Metodo executeCommandImportGisa: envelopeAllevamento: " + envelopeAllevamento);
					
				ws.setWsRequest(envelopeAzienda);
				responseAzienda = ws.post(db, getUserId(context));
					
				System.out.println("Metodo executeCommandImportGisa: responseAzienda: " + responseAzienda);
				
				ws.setWsRequest(envelopeAllevamento);
				responseAllevamento = ws.post(db, getUserId(context));
				System.out.println("Metodo executeCommandImportGisa: responseAllevamento: " + responseAllevamento);
				
				String specieOttenuta = null;
				String allevIdOttenuto = null;
				while(specieOttenuta==null || !specieOttenuta.equalsIgnoreCase(codice_specie))
				{
					int posizioneInizioReturn = -1;
					posizioneInizioReturn = responseAllevamento.indexOf("<return xmlns=\"\">");
					if(posizioneInizioReturn==-1)
						posizioneInizioReturn = responseAllevamento.indexOf("<return>");
					System.out.println("Metodo executeCommandImportGisa: posizioneInizioReturn: " + posizioneInizioReturn);
					
					int posizioneFineReturn = responseAllevamento.indexOf("</return>") + 9;
					System.out.println("Metodo executeCommandImportGisa: posizioneFineReturn: " + posizioneFineReturn);
					
					String responseAllevamentoOttenuto = responseAllevamento.substring(posizioneInizioReturn, posizioneFineReturn);
					System.out.println("Metodo executeCommandImportGisa: responseAllevamentoOttenuto: " + responseAllevamentoOttenuto);
					
					specieOttenuta = utilsXML.getValoreNodoXML(responseAllevamentoOttenuto,"gspCodice" );
					System.out.println("Metodo executeCommandImportGisa: specieOttenuta: " + specieOttenuta);
					allevIdOttenuto = utilsXML.getValoreNodoXML(responseAllevamentoOttenuto,"allevId" );
					if(specieOttenuta!=null && specieOttenuta.equalsIgnoreCase(descrizione_specie) && allevIdInput.equals(allevIdOttenuto))
					{
						responseAllevamento = "<?xml version=\"1.0\" ?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Body><ns2:getAllevamentoResponse xmlns:ns2=\"http://acquacoltura.izs.it/services\">" + responseAllevamentoOttenuto + "></ns2:getAllevamentoResponse></S:Body></S:Envelope>";
						break;
					}
					else
					{
						System.out.println("Metodo executeCommandImportGisa: response parte da tagliare: " + responseAllevamentoOttenuto );
						responseAllevamento = responseAllevamento.substring(0, posizioneInizioReturn) + responseAllevamento.substring(posizioneFineReturn, responseAllevamento.length());
						System.out.println("Metodo executeCommandImportGisa: response nuovo: " + responseAllevamento);
					}
				}
				//Fine recupero dati azienda e allevamento
				
				//Recupero dati proprietario e rappresentante
				ws.setWsUrl(ApplicationProperties.getProperty("END_POINT_AZIENDE_BDN"));
					
				String envelopeProprietario 	   = costruisciEnvelopePersona(utilsXML.getValoreNodoXML(responseAllevamento,"propIdfiscale" ));
				String envelopeResponsabile        = costruisciEnvelopePersona(utilsXML.getValoreNodoXML(responseAllevamento,"respIdfiscale" ));
					
				ws.setWsRequest(envelopeProprietario);
				responseProprietario = ws.post(db, getUserId(context));
					
				ws.setWsRequest(envelopeResponsabile);
				responseResponsabile = ws.post(db, getUserId(context));
				//Fine recupero dati azienda e allevamento
				
				String allevDenominazione =  utilsXML.getValoreNodoXML(responseAllevamento,"allevDenominazione" );
				String allevId = utilsXML.getValoreNodoXML(responseAllevamento,"allevId" );
				String aslCodice = utilsXML.getValoreNodoXML(responseAllevamento,"aslCodice" );
				String propIdfiscale = utilsXML.getValoreNodoXML(responseAllevamento,"propIdfiscale" );
				String dtApertura = utilsXML.getValoreNodoXML(responseAllevamento,"dtApertura" );
				String dtChiusura = utilsXML.getValoreNodoXML(responseAllevamento,"dtChiusura" );
				String specieAllevate = utilsXML.getValoreNodoXML(responseAllevamento,"saaDescrizione" );
				String tipoAllevamento = utilsXML.getValoreNodoXML(responseAllevamento,"talDescrizione" );
				tipoAllevamento = tipoAllevamento==null ?(""):(tipoAllevamento);
				String tipologiaProduttiva = utilsXML.getValoreNodoXML(responseAllevamento,"tprDescrizione" );
				String respIdfiscale = utilsXML.getValoreNodoXML(responseAllevamento,"respIdfiscale" );
				String via = utilsXML.getValoreNodoXML(responseAzienda,"indirizzo" );
				String viaSedeOperativa = utilsXML.getValoreNodoXML(responseAllevamento,"indirizzo" );
				String cap = utilsXML.getValoreNodoXML(responseAzienda,"cap" );
				String capSedeOperativa = utilsXML.getValoreNodoXML(responseAllevamento,"capSedeLegale" );
				String provincia = utilsXML.getValoreNodoXML(responseAzienda,"siglaProvincia" );
				String comIstat = utilsXML.getValoreNodoXML(responseAzienda,"comIstat" );
				String latitudine = utilsXML.getValoreNodoXML(responseAzienda,"latitudine" );
				String longitudine = utilsXML.getValoreNodoXML(responseAzienda,"longitudine" );
				String nominativoProp = utilsXML.getValoreNodoXML(responseProprietario,"COGN_NOME" );
				String nominativoResp = utilsXML.getValoreNodoXML(responseResponsabile,"COGN_NOME" );
				String indirizzoProp = utilsXML.getValoreNodoXML(responseProprietario,"INDIRIZZO" );
				String indirizzoResp = utilsXML.getValoreNodoXML(responseResponsabile,"INDIRIZZO" );
				String capProp = utilsXML.getValoreNodoXML(responseProprietario,"CAP" );
				String capResp = utilsXML.getValoreNodoXML(responseResponsabile,"CAP" );
				String comIstatProp = utilsXML.getValoreNodoXML(responseProprietario,"COM_CODICE" );
				String comIstatResp = utilsXML.getValoreNodoXML(responseResponsabile,"COM_CODICE" );
				String orientamentoProd = utilsXML.getValoreNodoXML(responseAllevamento,"tprDescrizione" );
				orientamentoProd = orientamentoProd==null ?(""):(orientamentoProd);
				
				allevamento = new AllevamentoAjax();
				allevamento.setDenominazione(allevDenominazione);
				allevamento.setCodice_azienda(codice_azienda);
				allevamento.setCodice_fiscale(propIdfiscale);
				allevamento.setIndirizzo(via);
				allevamento.setComune(Utility.getComuneFromCodiceAzienda(codice_azienda, db));
				allevamento.setSpecie_allevata(Integer.parseInt(codice_specie));
				allevamento.setDescrizione_specie(listaSpeci.getSelectedValue(Integer.parseInt(codice_specie)));
				allevamento.setCfDetentore(respIdfiscale);
				allevamento.setCfProprietario(propIdfiscale);
				allevamento.setCodiceTipoAllevamento(tipoAllevamento);
				allevamento.setOrientamento_prod(orientamentoProd);
				allevamento.setTipologia_strutt(tipoAllevamento);
				if(dtApertura!=null && !dtApertura.equals("") && !dtApertura.equals("null"))
					allevamento.setData_inizio_attivita( sdf1.format( sdf2.parse(dtApertura) ) );
				if(dtChiusura!=null && !dtChiusura.equals("") && !dtChiusura.equals("null"))
					allevamento.setData_fine_attivita( sdf1.format( sdf2.parse(dtChiusura) ) );
				allevamento.setInBDN( true );
				
				aziendaRet = new InfoAziendaBean();
				aziendaRet.setNomeComuneAzienda(Utility.getComuneFromCodiceIstat(codice_azienda, comIstat, db));
				aziendaRet.setIndirizzo_Azienda(via);
				aziendaRet.setCap_azienda(cap);
				if(latitudine!=null && !latitudine.equals(""))
					aziendaRet.setLatitudine(Double.parseDouble(latitudine));
				if(longitudine!=null && !longitudine.equals(""))
					aziendaRet.setLongidutine(Double.parseDouble(longitudine));
			}
			else
			{
				allevamento = ajaxCalls.findAllevamento(allevIdInput, getUserId(context));
				
				InfoAziendaBean aziendaBean = new InfoAziendaBean();
				aziendaRet = aziendaBean.getInfoAllevamentoBean(codice_azienda,db,getUserId(context));
			}


			/*if(allevamento!=null && allevamento.getErrore()==0)
			{*/
			OrgDetails = new Organization(db,Integer.parseInt(orgId));
			OrgDetails.setOrientamentoProd(OrgDetails.getOrientamentoProd()==null ? "" : OrgDetails.getOrientamentoProd());
			OrgDetails.setTipologiaStrutt(OrgDetails.getTipologiaStrutt()==null ? "" : OrgDetails.getTipologiaStrutt());
			context.getRequest().setAttribute("OrgDetails", OrgDetails);
			diff = OrgDetails.equals(allevamento,aziendaRet, db);
			Iterator iDiff = diff.iterator();
			while (iDiff.hasNext())
			{
				String differenza = iDiff.next().toString();
			}
			//}
		}
		catch (Exception errorMessage){
			errorMessage.printStackTrace();

		}
		finally
		{
			freeConnection(context, db);
		}
		
		
		/*if(allevamento==null || allevamento.getErrore()>0)
			context.getRequest().setAttribute("EsitoImport", "Occorso un errore nella chiamata ai servizi: ["+allevamento.getErrore()+"]");
		else {*/
		
		
		
		System.out.println("DATA INIZIOOOOOOOOOOO 4: " + allevamento.getData_inizio_attivita());
		System.out.println("DATA FINEEEEEEEEEEEEE 4: " + allevamento.getData_fine_attivita());
		
		if (allevamento.getData_fine_attivita()!=null){
			try {
				db = this.getConnection(context);
			ScortaAllevamento scorta = new ScortaAllevamento();
			System.out.println("CERCO SCORTA: " + OrgDetails.getAccountNumber() + " "+ OrgDetails.getCodiceFiscaleRappresentante());
			scorta.queryRecordByCodici(db, OrgDetails.getAccountNumber(), OrgDetails.getCodiceFiscaleRappresentante()); 
			if (scorta!=null && scorta.getId()>0 && (scorta.getScortaDataFine()==null || scorta.getScortaDataFine().equals(""))){
				System.out.println("ESISTE UNA SCORTA ATTIVA!");
				context.getRequest().setAttribute("Messaggio", "Attenzione! non è possibile allineare con BDN a causa di una scorta farmaci attiva su questo allevamento. Per eventuali chiarimenti contattare l'help desk.");
				return ("CompareWsKO");
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				freeConnection(context, db);
			}
			
		}

		context.getRequest().setAttribute("AllevamentoBDN", allevamento);
		context.getRequest().setAttribute("AziendaBDN", aziendaRet);
		context.getRequest().setAttribute("DiffenzeBDN", diff);
		context.getRequest().setAttribute("OrgDetails", OrgDetails);
		//}

		return ("CompareWsOK");


	}


	private static SecretKeySpec getKeySpec(String path) throws IOException, NoSuchAlgorithmException,FileAesKeyException {
		byte[] bytes = new byte[16];

		File f = new File(path.replaceAll("%20", " "));

		SecretKeySpec spec = null;
		if (f.exists()) 
		{
			new FileInputStream(f).read(bytes);

		} else {
			/* KeyGenerator kgen = KeyGenerator.getInstance("AES");
	       kgen.init(128);
	       key = kgen.generateKey();
	       bytes = key.getEncoded();
	       new FileOutputStream(f).write(bytes);*/
			throw new FileAesKeyException("File aes_key not found");

		}
		spec = new SecretKeySpec(bytes,"AES");
		return spec;
	}
	private static byte[] encrypt(String text,URL url) throws IOException, NoSuchAlgorithmException,FileAesKeyException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		if(url ==null)
		{
			throw new FileAesKeyException("File aes_key not found");
		}
		SecretKeySpec spec = getKeySpec(url.getPath());
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, spec);
		sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();

		return enc.encode(cipher.doFinal(text.getBytes())).getBytes() ;
	}

	public static String asHex(byte buf[]) {
		StringBuffer sb = new StringBuffer(buf.length * 2);
		for (int i = 0; i < buf.length; i++) {
			if (((int) buf[i] & 0xff) < 0x10) {
				sb.append("0");
			}

			sb.append(Long.toString((int) buf[i] & 0xff, 16));
		}

		return sb.toString();
	}

	public String executeCommandLoginOrsa(ActionContext context) throws IOException 
	{
		UserBean user = (UserBean)context.getSession().getAttribute("User");
		/**COSTRUZIONE DEL TOKEN**/

		String originalToken = System.currentTimeMillis() + "@"+"admin";
		byte[] encryptedToken = null ;
		try {

			encryptedToken =  encrypt(originalToken,this.getClass().getResource("aes_key"));
			InetAddress inetHost = InetAddress.getByName("srv.anagrafecaninacampania.it");
			String inetHostOrsa= InetAddress.getByName("endpointAPPORSA").getHostAddress();
			context.getResponse().sendRedirect( "http://"+inetHostOrsa+"/orsaGmap/login.LoginNoPassword.us?partenza=0&arrivo=1000&encryptedToken="+asHex(encryptedToken));
			//		  context.getResponse().sendRedirect( "http://172.16.3.194:8080/CanAgr_Priv/Login.do?command=LoginNoPassword&encryptedToken="+asHex(encryptedToken));


		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FileAesKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "-none-" ;
	}



	public String executeCommandRendicontazioneBa(ActionContext context){

		Connection db = null;
		ArrayList<Rendicontazione> listaCapitoli = new ArrayList<Rendicontazione>();
		String idSpecie = context.getParameter("specie");
		String anno = context.getParameter("anno");

		try {
			db = this.getConnection(context);

			String querySql = "SELECT * FROM lookup_specie_allevata where code = ?";
			String tipoSpecie = "";
			PreparedStatement pst = db.prepareStatement(querySql) ;
			pst.setInt(1,Integer.parseInt(idSpecie));
			ResultSet rs = pst.executeQuery();
			while (rs.next()){
				tipoSpecie = rs.getString("description");
			}
			if(idSpecie.equals("1211")){
				tipoSpecie = "VITELLI";
			}
			if(idSpecie.equals("1461")){
				tipoSpecie = "POLLI DA CARNE";
			}


			listaCapitoli = Rendicontazione.buildList(db, anno,idSpecie );
			if ( listaCapitoli.size() == 0) {
				return ("ErroreRendicontazione");
			}

			context.getRequest().setAttribute("CapitoliList", listaCapitoli);
			context.getRequest().setAttribute("specie", idSpecie );
			context.getRequest().setAttribute("tipoSpecie", tipoSpecie);
			context.getRequest().setAttribute("anno", anno);


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.freeConnection(context, db);
		}



		return ("RendicontazioneBaOK");

	}

	public String executeCommandDetailsWS(ActionContext context) {
		AllevamentoAjax allevamento = null;
		Organization OrgDetails = null;
		ArrayList diff = null;
		Connection db = null;
		try {
			db = this.getConnection(context);


			String orgId = context.getParameter("orgId");
			String codice_azienda = context.getParameter("codiceAzienda");
			String partita_iva = context.getParameter("pIva");
			String codice_specie = context.getParameter("codSpecie");


			AjaxCalls ajaxCalls = new AjaxCalls();
			allevamento = ajaxCalls.getAllevamento(codice_azienda, partita_iva, codice_specie);

			OrgDetails = new Organization(db,Integer.parseInt(orgId));

			diff = OrgDetails.equals(allevamento,null,db);
			Iterator iDiff = diff.iterator();
			while (iDiff.hasNext())
			{
				String differenza = iDiff.next().toString();
			}
		}
		catch (Exception errorMessage){

		}
		context.getRequest().setAttribute("AllevamentoBDN", allevamento);
		context.getRequest().setAttribute("DiffenzeBDN", diff);
		context.getRequest().setAttribute("OrgDetails", OrgDetails);



		return ("AllevamentoWsOK");
	}

	/*
  private String[] converToWgs84UTM33NInverter(String latitudine , String longitudine, Connection db) throws SQLException
  {
          String lat ="";
          String lon = "";
          String [] coord = new String [2];
          String sql1 = 
                  "select         y \n"        + 
                  "( \n"                                + 
                  "        transform \n"        + 
                  "        (  \n"                        + 
                  "                geomfromtext \n"        + 
                  "                (  \n"                                + 
                  "                        'POINT("+longitudine+" "+latitudine+")', 32633 \n"        + 
                  "         \n"                                        + 
                  "                ), 4326 \n"                        + 
                  "        ) \n"                                        + 
                  ") AS y, \n"                                + 
                  "x \n"                                                + 
                  "( \n"                                                + 
                  "        transform \n"                        + 
                  "        (  \n"                                        + 
                  "                geomfromtext \n"        + 
                  "                (  \n"                                + 
                  "                        'POINT("+longitudine+" "+latitudine+")', 32633 \n"        + 
                  "         \n"                        + 
                  "                ), 4326 \n"        + 
                  "        ) \n"                        + 
                  ") AS x \n";

        try {

          PreparedStatement stat1 = db.prepareStatement( sql1 );
          ResultSet res1 = stat1.executeQuery();
          if( res1.next() )
          {
                  lat = res1.getString( "y") ;
                  lon=res1.getString( "x" );
                  coord [0] =lat;
                  coord [1] =lon;

          }
          res1.close();
          stat1.close();

        }catch (Exception e){
        	e.printStackTrace();
        }

        return coord;

  } */

	private void aggiungiOrientamentoProduttivo(Connection db, ArrayList<InfoAllevamentoBean> lista){
		ArrayList<InfoAllevamentoBean> lista2 = new ArrayList<InfoAllevamentoBean>();
		for (int i = 0; i<lista.size(); i++){
			InfoAllevamentoBean all = (InfoAllevamentoBean) lista.get(i);
			if(all.getOrientamento_prod()==null || all.getOrientamento_prod().equals("") || all.getOrientamento_prod().equals("null"))
				all.setOrientamento_prod(db);
			lista2.add(all);
		}
		lista = lista2;
	}
	
	private String costruisciEnvelope(String tipoServizio, String codAzienda, String idFiscale)
	{
		return   "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://acquacoltura.izs.it/services\"><soapenv:Header><ser:SOAPAutorizzazione><ruolo>regione</ruolo></ser:SOAPAutorizzazione><ser:SOAPAutenticazione><password>" + ApplicationProperties.getProperty("PASSWORD_WS_BDN") + "</password><username>" + ApplicationProperties.getProperty("USERNAME_WS_BDN") + "</username></ser:SOAPAutenticazione></soapenv:Header><soapenv:Body><ser:get" + tipoServizio + "><codice>" + codAzienda + "</codice>" + (idFiscale!=null ? "<idFiscaleProp>" + idFiscale + "</idFiscaleProp>" : "") + "</ser:get" + tipoServizio + "></soapenv:Body></soapenv:Envelope>";
	}
	
	private String costruisciEnvelopePersona(String cf)
	{
		return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://bdr.izs.it/webservices\">        <soapenv:Header> <web:SOAPAutenticazione> <web:username>" + ApplicationProperties.getProperty("USERNAME_WS_BDN") + "</web:username>  <web:password>" + ApplicationProperties.getProperty("PASSWORD_WS_BDN") + "</web:password> </web:SOAPAutenticazione> </soapenv:Header>  <soapenv:Body>      <web:getPersona>       <web:p_persona_idfiscale>" + cf + "</web:p_persona_idfiscale>   </web:getPersona> </soapenv:Body></soapenv:Envelope>";
	}
	
	public String executeCommandSearchFormElicicoltura(ActionContext context) {
		
		return ("SearchFormElicicolturaOK"); 

}
	
	public String executeCommandSearchElicicoltura(ActionContext context) {
	
		Connection db = null;
		WsPostElicicoltura ws = new WsPostElicicoltura();  
		String response = "";
		String token = "";
		
		String aziendaCodice=context.getRequest().getParameter("aziendaCodice");
		
		try {
			db = this.getConnection(context);
		
			System.out.println(" ------ INVIO DATI A ELICICOLTURA  -----");   
				
			ws.setWsUrl(ApplicationProperties.getProperty("URL_TOKEN_ELICICOLTURA_BDN"));
			ws.setWsRequest("");
			response= ws.getToken(db, getUserId(context), ApplicationProperties.getProperty("USERNAME_ELICICOLTURA_BDN"), ApplicationProperties.getProperty("PASSWORD_ELICICOLTURA_BDN"));
			
			JSONObject json = new JSONObject(response);
			token = json.getString("access_token");

			ws = new WsPostElicicoltura();  
			ws.setWsUrl(ApplicationProperties.getProperty("URL_SEARCH_AZIENDE_ELICICOLTURA_BDN"));
			ws.setWsRequest("{\"lo\":\"AND\",\"cr\":[{\"f\":\"aziendaCodice\",\"op\":\"EQ\",\"v1\":\""+aziendaCodice+"\",\"v2\":\"\"}]}");
			response= ws.getAttivitaPerAzienda(db, getUserId(context), token);
			
			ArrayList<ElicicolturaAllevamento> listaAziende = new ArrayList<ElicicolturaAllevamento>();
			JSONArray jsonArray = new JSONArray(response); 
			for (int i = 0; i < jsonArray.length(); i++) {
			    JSONObject explrObject = jsonArray.getJSONObject(i);
				ElicicolturaAllevamento azienda = new ElicicolturaAllevamento(explrObject);
				listaAziende.add(azienda);
			}
			
			context.getRequest().setAttribute("ListaAziende", listaAziende);
			
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			response ="ERRORE INASPETTATO. <BR/><BR/>"+e.toString();
		} finally{
			freeConnection(context, db);
		}
			
		return ("SearchElicicolturaOK");

}
	
	public String executeCommandSetCodiceSINVSA(ActionContext context) throws SQLException {
		String codice = context.getRequest().getParameter("codice-sinvsa"); 
		String dataCodice = context.getRequest().getParameter("data-codice-sinvsa");
		int riferimentoId = Integer.parseInt(context.getRequest().getParameter("riferimento-id")); 
		String riferimentoId_nomeTab = context.getRequest().getParameter("riferimento-id-nome-tab");
		int userId = Integer.parseInt(context.getRequest().getParameter("user-id"));
		
		context.getRequest().setAttribute("orgId", String.valueOf(riferimentoId));
		
		try {
			PopolaCombo.setCodiceSINVSA(codice, dataCodice, riferimentoId, riferimentoId_nomeTab, userId);
		} catch (Exception e) {
			throw e;
		}
		
		return this.executeCommandDetails(context);
	}

	
}

