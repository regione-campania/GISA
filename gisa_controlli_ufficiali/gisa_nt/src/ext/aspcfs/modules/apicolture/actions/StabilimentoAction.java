/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package ext.aspcfs.modules.apicolture.actions;


import it.izs.apicoltura.apianagraficaattivita.ws.Api;
import it.izs.apicoltura.apianagraficaattivita.ws.Apiatt;
import it.izs.apicoltura.apianagraficaattivita.ws.Apicen;
import it.izs.apicoltura.apianagraficaattivita.ws.BusinessWsException_Exception;
import it.izs.apicoltura.apianagraficaattivita.ws.FieldError;
import it.izs.apicoltura.apianagraficaattivita.ws.WsApiAnagraficaApi;
import it.izs.apicoltura.apianagraficaattivita.ws.WsApiAnagraficaAttivita;
import it.izs.apicoltura.apianagraficaattivita.ws.WsApiAnagraficaVariazioniCen;
import it.izs.apicoltura.apianagraficaattivita.ws.WsApiAnagraficaVariazioniDet;
import it.izs.apicoltura.apianagraficaattivita.ws.WsApiAnagraficaVariazioniUbi;
import it.izs.apicoltura.apianagraficaazienda.ws.Apiazienda;
import it.izs.apicoltura.apianagraficaazienda.ws.WsApiAnagraficaAziende;
import it.izs.bdn.anagrafica.ws.Persone;
import it.izs.bdn.anagrafica.ws.WsApiAnagraficaPersoneBdn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.accounts.base.Provincia;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.allerte.base.AslCoinvolte;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.diffida.base.Ticket;
import org.aspcfs.modules.diffida.base.TicketList;
import org.aspcfs.modules.distributori.base.Distrubutore;
import org.aspcfs.modules.distributori.base.Organization;
import org.aspcfs.modules.gestoriacquenew.base.EccezioneDati;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.opu.base.RicercheAnagraficheTab;
import org.aspcfs.modules.trasportoanimali.base.Comuni;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DwrUtil;
import org.aspcfs.utils.OperatoreInsertEsception;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.StateSelect;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.Limit;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesa.view.html.editor.HtmlCellEditor;
import org.jmesa.view.html.toolbar.ToolbarItem;
import org.jmesa.view.html.toolbar.ToolbarItemFactoryImpl;
import org.json.JSONArray;
import org.json.JSONObject;

import com.darkhorseventures.framework.actions.ActionContext;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.xml.internal.ws.fault.ServerSOAPFaultException;

import ext.aspcfs.modules.apiari.base.Indirizzo;
import ext.aspcfs.modules.apiari.base.ModelloC;
import ext.aspcfs.modules.apiari.base.Operatore;
import ext.aspcfs.modules.apiari.base.OperatoreList;
import ext.aspcfs.modules.apiari.base.SoggettoFisico;
import ext.aspcfs.modules.apiari.base.Stabilimento;
import ext.aspcfs.modules.apiari.base.StabilimentoList;
import ext.aspcfs.modules.apiari.base.StabilimentoVariazioneCensimento;
import ext.aspcfs.modules.apiari.base.VariazioneCensimentoList;
import ext.aspcfs.modules.apiari.base.VariazioneDetentoreList;
import ext.aspcfs.modules.apiari.base.VariazioneUbicazioneList;
import ext.aspcfs.modules.apiari.base.WSBdnLog;
import ext.aspcfs.modules.apicolture.util.ApiUtil;

public class StabilimentoAction extends CFSModule {
	
	Logger logger = Logger.getLogger(StabilimentoAction.class);


	Integer[] array = null;
	List<String> uniquePropertyValues = null;
	int k = -1;

	public static  final int API_STATO_DA_NOTIFICARE = 1 ;
	public static  final int API_STATO_VALIDATO = 2 ;
	public static final int API_STATO_CESSATO = 3 ;
	public static final int API_STATO_PREGRESSO_DA_VALIDARE = 4 ;
	public static final int API_STATO_INCOMPLETO = 5 ;
	public static final int API_STATO_VENDUTO = 6;
	public static final int API_STATO_CESSATO_DA_SINCRONIZZARE = 9;
	public static final int API_STATO_CESSATO_SINCRONIZZATO = 8;


	
	public static String generaFoglioExcelCensimentiPerApicoltore(Connection conn, int idApicoltore, String pathTempFold) throws Exception
	{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		String fullPath = null;
		
		try
		{
			XSSFWorkbook wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet("censimenti");
			
			
			XSSFCellStyle stileCellaVerde = wb.createCellStyle();
			stileCellaVerde.setWrapText(true);
			stileCellaVerde.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			stileCellaVerde.setFillPattern(CellStyle.SOLID_FOREGROUND);
//			stileCella.setLocked(true);
			stileCellaVerde.setVerticalAlignment(CellStyle.ALIGN_LEFT);
			stileCellaVerde.setAlignment(CellStyle.ALIGN_CENTER);
			pst = conn.prepareStatement("select   concat(top.description,' ',ind.via,' ', ind.civico,' ', ind.cap) as indirizzo_apiario, "
										+"to_char(cens.data_censimento,'DD/MM/YYYY HH24:MI:SS') as data_censimento, cens.num_sciami, cens.num_alveari, cens.flag_notificato_asl, cens.num_pacchi, cens.num_regine "
										+" from   apicoltura_apiari_variazioni_censimenti cens join apicoltura_apiari apiari "
								    	+"on cens.id_apicoltura_apiario = apiari.id "
										+"join opu_indirizzo ind on ind.id = apiari.id_indirizzo "
										+"left join lookup_toponimi  top on top.code = toponimo "
										+"where cens.trashed_by is null and apiari.id_operatore = ? order by apiari.id,cens.data_censimento desc");
			pst.setInt(1, idApicoltore);
			
			/*intestazione */
			int iRow = 0;
			rs = pst.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while(rs.next())
			{
				if(iRow == 0)
				{
					
					Row headRow = sheet.createRow((short) 0);
					
					for(int j = 0; j<rsmd.getColumnCount(); j++)
					{
						Cell headCell = headRow.createCell(j);
						headCell.setCellStyle(stileCellaVerde);
						headCell.setCellValue(rsmd.getColumnName(j+1));
						sheet.setColumnWidth(j, 5000);
					}
					iRow++;
				}
				
				Row row = sheet.createRow(iRow);
				for(int j = 0; j< rsmd.getColumnCount(); j++)
				{
					Cell cell = row.createCell(j);
					cell.setCellValue(rs.getString(j+1));
				}
				
				iRow++;
			}
			
			fullPath = pathTempFold+"/"+"CensimentoApicoltore"+(int)(Math.random()*100)+".xlsx";
			File outputFile = new File(fullPath);
			
			if(!outputFile.exists() )
				outputFile.createNewFile();
				
			wb.write(new FileOutputStream(outputFile));
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			try{pst.close(); } catch(Exception ex){}
			try{rs.close(); } catch(Exception ex){}
		}
		
		return fullPath;
	
		
	}
	
	public static String generaFoglioExcelCensimentiPerApiario(Connection conn, int idApiario,String pathTempFold) throws Exception
	{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		String fullPath = null;
		
		try
		{
			XSSFWorkbook wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet("censimenti");
			
			
			XSSFCellStyle stileCellaVerde = wb.createCellStyle();
			stileCellaVerde.setWrapText(true);
			stileCellaVerde.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			stileCellaVerde.setFillPattern(CellStyle.SOLID_FOREGROUND);
//			stileCella.setLocked(true);
			stileCellaVerde.setVerticalAlignment(CellStyle.ALIGN_LEFT);
			stileCellaVerde.setAlignment(CellStyle.ALIGN_CENTER);
			pst = conn.prepareStatement("select   concat(top.description,' ',ind.via,' ', ind.civico,' ', ind.cap) as indirizzo_apiario, "
										+"to_char(cens.data_censimento,'DD/MM/YYYY HH24:MI:SS') as data_censimento, cens.num_sciami, cens.num_alveari, cens.flag_notificato_asl, cens.num_pacchi, cens.num_regine "
										+" from   apicoltura_apiari_variazioni_censimenti cens join apicoltura_apiari apiari "
								    	+"on cens.id_apicoltura_apiario = apiari.id "
										+"join opu_indirizzo ind on ind.id = apiari.id_indirizzo "
										+"left join lookup_toponimi  top on top.code = toponimo "
										+"where cens.trashed_by is null and apiari.id = ? order by cens.data_censimento");
			pst.setInt(1, idApiario);
			
			/*intestazione */
			int iRow = 0;
			rs = pst.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while(rs.next())
			{
				if(iRow == 0)
				{
					
					Row headRow = sheet.createRow((short) 0);
					
					for(int j = 0; j<rsmd.getColumnCount(); j++)
					{
						Cell headCell = headRow.createCell(j);
						headCell.setCellStyle(stileCellaVerde);
						headCell.setCellValue(rsmd.getColumnName(j+1));
						sheet.setColumnWidth(j, 5000);
					}
					iRow++;
				}
				
				Row row = sheet.createRow(iRow);
				for(int j = 0; j< rsmd.getColumnCount(); j++)
				{
					Cell cell = row.createCell(j);
					cell.setCellValue(rs.getString(j+1));
				}
				
				iRow++;
			}
			
			fullPath = pathTempFold+"/"+"CensimentoApiario"+(int)(Math.random()*100)+".xlsx";
			File outputFile = new File(fullPath);
			
			if(!outputFile.exists() )
				outputFile.createNewFile();
				
			wb.write(new FileOutputStream(outputFile));
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			try{pst.close(); } catch(Exception ex){}
			try{rs.close(); } catch(Exception ex){}
		}
		
		return fullPath;
	}
	

	public String executeCommandImportMasssivoApiariFuoriRegione(ActionContext context) 
	{

		
		Connection db = null;
		try {
			db = this.getConnection(context);

			WsApiAnagraficaApi wsApi = new WsApiAnagraficaApi();
			List<Api> listaApiariForuoRegione = wsApi.searchApiariFuoriRegione();
			String codiceAzienda = "" ;

			Operatore op = new Operatore();

			for (Api apiario : listaApiariForuoRegione)
			{
				System.out.println("CODICE AZIENDA IMPORTATO : ->"+apiario.getApiattAziendaCodice());
				codiceAzienda=apiario.getApiattAziendaCodice();
				op.setCodiceAzienda(codiceAzienda);
				
				WsApiAnagraficaAttivita wsAttivita = new WsApiAnagraficaAttivita();
				Apiatt attivita = wsAttivita.search(op, db, getUserId(context));

				WsApiAnagraficaPersoneBdn wsPersone = new WsApiAnagraficaPersoneBdn();
				Persone p = wsPersone.searchPersone(attivita.getPropIdFiscale() , db, getUserId(context));
				
				SoggettoFisico prop = new SoggettoFisico(p, db);
				if(prop.verificaSoggetto(db).getIdSoggetto()<=0)
				{
					prop.insert(db, context);
				}
				else
				{
					prop.setIdSoggetto(prop.verificaSoggetto(db).getIdSoggetto());
				}
				Operatore opImport = new Operatore(attivita, db,context);
				opImport.setStato(StabilimentoAction.API_STATO_VALIDATO);
				opImport.setRappLegale(prop);
				opImport.setIdAsl(16);
				
				
				
				
				if(opImport.checkEsistenzaOperatore(db)==false)
				{
					opImport.insert(db, context);
					
				}
				Persone det = wsPersone.searchPersone(apiario.getDetenIdFiscale() , db, getUserId(context));
				Stabilimento stabAp = new Stabilimento(apiario,db,context);
				
				SoggettoFisico detentore = new SoggettoFisico(det,db);
				if(detentore.verificaSoggetto(db).getIdSoggetto()<=0)
				{
					detentore.insert(db, context);
				}
				else
				{
					detentore.setIdSoggetto(detentore.verificaSoggetto(db).getIdSoggetto());
				}
				
				stabAp.setDetentore(detentore);
				stabAp.setIdOperatore(opImport.getIdOperatore());
				
				if(stabAp.checkEsistenzaApiario(db)==false)
				{
					stabAp.setStato(StabilimentoAction.API_STATO_VALIDATO);
					stabAp.setIdOperatore(opImport.getIdOperatore());
					stabAp.insert(db,false,context);
				}
				
				
				
			}

			context.getRequest().setAttribute("EsitoImport", "Import Eseguito Correttamente");

		}
		
		catch(Exception e)
		{
			context.getRequest().setAttribute("EsitoImport", "Si e verificato Un errore durante la sincronizzazione");
			return executeCommandSearchForm(context);
		}
		finally
		{
			this.freeConnection(context, db);
		}
		return executeCommandSearchForm(context);
	}
	
	
	
	public String executeCommandViewApiariFuoriRegione(ActionContext context) throws Exception
	{

		Connection db = null;
		try {
			db = this.getConnection(context);

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,((UserBean) context.getSession().getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList();
			comuniList.queryListComuni(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);
			
			
			LookupList lookupStati = new LookupList(db,"lookup_apicoltura_stati_apiario");
			context.getRequest().setAttribute("LookupStati", lookupStati);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("SiteIdList", siteList);
			
			WsApiAnagraficaApi wsApi = new WsApiAnagraficaApi();
			List<Api> listaApiariForuoRegione = wsApi.searchApiariFuoriRegione();
			String codiceAzienda = "" ;

			StabilimentoList listaApiariInRegione = new StabilimentoList();
			Operatore op = new Operatore();

			for (Api apiario : listaApiariForuoRegione)
			{
				System.out.println("CODICE AZIENDA IMPORTATO : ->"+apiario.getApiattAziendaCodice());
				codiceAzienda=apiario.getApiattAziendaCodice();
				op.setCodiceAzienda(codiceAzienda);
				
				WsApiAnagraficaAttivita wsAttivita = new WsApiAnagraficaAttivita();
				Apiatt attivita = wsAttivita.search(op, db, getUserId(context));

				WsApiAnagraficaPersoneBdn wsPersone = new WsApiAnagraficaPersoneBdn();
				Persone p = wsPersone.searchPersone(attivita.getPropIdFiscale() , db, getUserId(context));
				
				SoggettoFisico prop = new SoggettoFisico(p, db);
				
				Operatore opImport = new Operatore(attivita, db,context);
				opImport.setStato(StabilimentoAction.API_STATO_VALIDATO);
				opImport.setRappLegale(prop);
				opImport.setIdAsl(16);
				
				Persone det = wsPersone.searchPersone(apiario.getDetenIdFiscale() , db, getUserId(context));
				Stabilimento stabAp = new Stabilimento(apiario,db,context);
				
				SoggettoFisico detentore = new SoggettoFisico(det,db);
				stabAp.setDetentore(detentore);
				
				
					stabAp.setStato(StabilimentoAction.API_STATO_VALIDATO);
					stabAp.setIdOperatore(opImport.getIdOperatore());
					
					stabAp.setOperatore(opImport);
					listaApiariInRegione.add(stabAp);			
				
				
			}
			context.getRequest().setAttribute("OrgList", listaApiariInRegione);



		}
		catch(SQLException e)
		{

		}
		finally
		{
			this.freeConnection(context, db);
		}
		return "ListaApiariInRegioneOK";
	}





	public String executeCommandSearchFormVariazioni(ActionContext context) throws IndirizzoNotFoundException, IOException
	{
		ArrayList<Stabilimento> lista = new ArrayList<Stabilimento>();
		Connection db = null;
		try {
			db = this.getConnection(context);
			String nomeComune = context.getParameter("searchTerm");


			Stabilimento stab = new Stabilimento(db,Integer.parseInt(context.getParameter("stabId")));
			context.getRequest().setAttribute("StabilimentoDetails", stab);


		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		return "SearchFormVariazioniOK";
	}

	public String executeCommandDelete(ActionContext context) {
	    Connection db = null;
	    Stabilimento thisApiario = null;

	    boolean hasControlli = false;
	    boolean isSincronizzato = false;
	    boolean isCancellatoBdn = false;
	    boolean isCancellato = false;
	    String msg = "";

	    int idStabilimento = Integer.parseInt(context.getParameter("stabId"));
	    int opId = -1;

	    try {
	        db = this.getConnection(context);

	        thisApiario = new Stabilimento(db, idStabilimento);
	        opId = thisApiario.getIdOperatore();
	        hasControlli = thisApiario.hasControlli(db);

	        if (!hasControlli) {

	            if (!thisApiario.isSincronizzatoBdn()) {
	                thisApiario.delete(db, getUserId(context));
	                msg = "Apiario cancellato.";
	            } else {
	            	try {
	                isCancellatoBdn = thisApiario.cancellaBdn(db, getUserId(context));
	            	} catch (Exception e) {}
	                if (isCancellatoBdn) {
	                     thisApiario.delete(db, getUserId(context));
	                    msg = "Apiario cancellato.";
	                	
	                } else {
	                    isCancellato = false;
	                    msg = "Cancellazione non possibile, impedita dai servizi BDN.";
	                }

	            }

	        } else {
	            isCancellato = false;
	            msg = "Cancellazione non possibile, poichè all'apiario sono associati dei controlli ufficiali";
	        }

	    } catch (SQLException e) {

	    } finally {
	        this.freeConnection(context, db);
	    }

	    context.getRequest().setAttribute("msgCancellazione", msg);
	    
	  
	    	OperatoreAction op = new OperatoreAction();
	    	context.getRequest().setAttribute("opId", thisApiario.getIdOperatore());
	    	return "Attivita" + op.executeCommandDetails(context);
	    
	}


	public String executeCommandUpdateApiario(ActionContext context) throws Exception
	{
		ArrayList<Stabilimento> lista = new ArrayList<Stabilimento>();
		Connection db = null;
		int idStabilimento = Integer.parseInt(context.getParameter("idStabilimento"));
		try {
			db = this.getConnection(context);

			Stabilimento thisApiario = new Stabilimento(db,idStabilimento);


			/**
			 * RECUPERO COORDINATE
			 */
			thisApiario.getSedeOperativa().setLatitudine(context.getParameter("latitudine"));
			thisApiario.getSedeOperativa().setLongitudine(context.getParameter("longitudine"));

			/**
			 * RECUPERO VALORI  [CLASSIFICAZIONE SPECIE E MODALITA DATA INIZIO NUM ALVEARI E SCIAM]
			 */
			thisApiario.setIdApicolturaClassificazione(Integer.parseInt(context.getParameter("idApicolturaClassificazione")));
			thisApiario.setIdApicolturaSottospecie(Integer.parseInt(context.getParameter("idApicolturaSottospecie")));
			thisApiario.setIdApicolturaModalita(Integer.parseInt(context.getParameter("idApicolturaModalita")));

			thisApiario.setDataApertura(context.getParameter("dataApertura"));
			thisApiario.setNumAlveari(Integer.parseInt(context.getParameter("numAlveari")));
			thisApiario.setNumSciami(Integer.parseInt(context.getParameter("numSciami")));

			boolean isValid = validateObject(context, db, thisApiario);
			if (isValid)
			{
				thisApiario.aggiornaDatiApiario(db);
			}



		}
		catch(SQLException e)
		{

		}
		finally
		{
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("idStab", idStabilimento);
		return executeCommandDetails(context) ;
	}
	
	public String executeCommandUpdateModalitaApiario(ActionContext context) throws Exception
	{
		Connection db = null;
		int idStabilimento = Integer.parseInt(context.getParameter("idStabilimento"));
		String dataModifica = context.getParameter("dataModifica");
		String note = context.getParameter("note");

		String msg = "";
		String esito = "";
		
		try {
			db = this.getConnection(context);

			Stabilimento thisApiario = new Stabilimento(db,idStabilimento);
			
			thisApiario.setIdApicolturaModalita(Integer.parseInt(context.getParameter("idApicolturaModalita")));

			boolean isValid = validateObject(context, db, thisApiario);
			if (isValid)
			{
				thisApiario.aggiornaDatiModalitaApiario(db, dataModifica, note, getUserId(context));
				msg ="Apiario aggiornato in GISA. ";
				boolean isSincronizzato =	(thisApiario.getStato() == StabilimentoAction.API_STATO_VALIDATO);

				boolean isAggiornatoBdn = false;
				 if (isSincronizzato) {
					 try {
		                esito = thisApiario.aggiornaModalitaBdn(db, getUserId(context));
		                if (esito.equalsIgnoreCase("ok"))
		                	isAggiornatoBdn = true;
		            	} catch (Exception e) {}
		                if (!isAggiornatoBdn) {
		                    msg+= "Apiario NON aggiornato in BDN." + esito;
		                }

		            }
					
			}

		}
		catch(SQLException e)
		{

		}
		finally
		{
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("msgAggiornamento", msg);
		context.getRequest().setAttribute("idStab", idStabilimento);
		return executeCommandDetails(context) ;
	}

	


	public String executeCommandModificaCoordinateApiario(ActionContext context) throws Exception
	{
		ArrayList<Stabilimento> lista = new ArrayList<Stabilimento>();
		Connection db = null;
		int idStabilimento = Integer.parseInt(context.getParameter("idStabilimento"));
		try {
			db = this.getConnection(context);

			Stabilimento thisApiario = new Stabilimento(db,idStabilimento);


			/**
			 * RECUPERO COORDINATE
			 */
			thisApiario.getSedeOperativa().setLatitudine(context.getParameter("lat"));
			thisApiario.getSedeOperativa().setLongitudine(context.getParameter("lon"));
			thisApiario.aggiornaCoordinate(db);




		}
		catch(SQLException e)
		{

		}
		finally
		{
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("idStab", idStabilimento);
		return executeCommandDetails(context) ;
	}



	public String executeCommandSendBdn(ActionContext context) throws IndirizzoNotFoundException, IOException
	{
		ArrayList<Stabilimento> lista = new ArrayList<Stabilimento>();
		Connection db = null;
		try {
			db = this.getConnection(context);
			int idStabilimento = Integer.parseInt(context.getParameter("stabId"));
			Stabilimento thisApiario = new Stabilimento(db,idStabilimento);
			context.getRequest().setAttribute("idStab", thisApiario.getIdStabilimento());
			try
			{


				if (db.getAutoCommit()==true)
					db.setAutoCommit(false);




				WsApiAnagraficaPersoneBdn servicePersone = new WsApiAnagraficaPersoneBdn();
				Persone detentore = servicePersone.searchPersone(thisApiario.getDetentore(), db, getUserId(context));
				if (detentore==null)
					detentore =  servicePersone.insertAnagraficaPersona(thisApiario.getDetentore(), db, getUserId(context));





				/**
				 * STEP 5  INSERIMENTO DEGLI APIARI ASSOCIATI ALL'IMPRESA
				 */

				WsApiAnagraficaApi serviceApi = new WsApiAnagraficaApi();
				Api apiarioInserito = serviceApi.insertApiAnagraficaApiario(thisApiario, db, getUserId(context),context);

				
				//Gestione Autocensimento
				//Ricerca censimento
				WsApiAnagraficaVariazioniCen censimento = new WsApiAnagraficaVariazioniCen();
				int progressivoApiario  = Integer.parseInt(thisApiario.getProgressivoBDA());
				
				List<Apicen> censimenti = censimento.search(thisApiario.getOperatore().getCodiceAzienda(), progressivoApiario, thisApiario.getDataApertura(), "S",db);
				
				//Inserimento Censimento
				if(!censimenti.isEmpty())
				{
					thisApiario.setData_assegnazione_censimento(thisApiario.getDataApertura());
					thisApiario.nuovoCensimento(db, context, censimenti.get(0).getApicenId());
				}
				//Fine Gestione Autocensimento
				
				
				thisApiario.sincronizzaBdn(db, getUserId(context), apiarioInserito.getApiId());
				if (db.getAutoCommit()==false)
				{
					db.commit();
					db.setAutoCommit(true);
				}


			}
			// catch (BusinessWsException_Exception |  it.izs.bdn.anagrafica.ws.BusinessWsException_Exception e) {
			catch (EccezioneDati e) 
			{

				System.out.println("Errore nell'invio in BDN : "+e.getMessage());
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

				
				String msg = ApiUtil.getInfoRichiesta(thisApiario.getOperatore(), "KO", e.getMessage(), timestamp, thisUser,  new LookupList(db, "lookup_site_id"), new LookupList(db, "lookup_apicoltura_stati_apiario"),  new LookupList(db, "apicoltura_lookup_tipo_attivita"), new LookupList(db, "apicoltura_lookup_classificazione"), new LookupList(db, "apicoltura_lookup_sottospecie"), new LookupList(db, "apicoltura_lookup_modalita"));				

				if (db.getAutoCommit()==false)
				{
					db.rollback();
					db.setAutoCommit(true);
				}

				if (e.getMessage().contains("RECORD ESISTENTE"))
				{
					thisApiario.sincronizzaBdn(db, getUserId(context),null);

					if (db.getAutoCommit()==false)
					{
						db.commit();

					}
				}
				else
				{
					thisApiario.errSincronizzaBdn(db, e.getMessage());
					if (db.getAutoCommit()==false)
					{
						db.commit();

					}

				}



				context.getRequest().setAttribute("ErrorValidazioneError", e.getMessage());

				try
				{
					sendMailIzsm(context.getRequest(), msg+". L'errore e il seguente :"+e.getMessage(), "##GISA_API_WS_BDN_KO##", "gisahelpdesk@usmail.it");
				}catch(Exception e2)
				{
					System.out.println("Errore nell'invio della mail");
				}
				OperatoreAction opertoreAction = new OperatoreAction();
				return opertoreAction.executeCommandSearchForm(context);
			}
			catch(Exception e)
			{
				System.out.println("SYNC- Errore nell'invio in BDN : "+e.getMessage());
				OperatoreAction opertoreAction = new OperatoreAction();
				return opertoreAction.executeCommandSearchForm(context);
			}





		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		return executeCommandDetails(context);
	}

	public String executeCommandSearchVariazioni(ActionContext context) throws IndirizzoNotFoundException, IOException
	{

		Connection db = null;
		try {
			db = this.getConnection(context);
			String tipoRicerca = context.getParameter("idTipoVariazione");

			Stabilimento stab = new Stabilimento(db,Integer.parseInt(context.getParameter("searchcodeidApiario")));
			context.getRequest().setAttribute("StabilimentoDetails", stab);

			switch(Integer.parseInt(tipoRicerca))
			{


			case 1 : // UBICAZIONE
			{


				PagedListInfo searchListInfo = this.getPagedListInfo( context, "SearchVariazioniUbiListInfo");	

				String popup = context.getRequest().getParameter("popup");
				searchListInfo.setLink("ApicolturaApiari.do?command=Search&idTipoVariazione="+tipoRicerca);
				VariazioneUbicazioneList listaMovimentazioninom = new VariazioneUbicazioneList();
				listaMovimentazioninom.setPagedListInfo(searchListInfo);
				searchListInfo.setSearchCriteria(listaMovimentazioninom, context);
				listaMovimentazioninom.buildList(db);
				context.getRequest().setAttribute("ListaVariazioni", listaMovimentazioninom);
				return "SearchVariazioniUbicazioneOK" ;
			}
			case 2 : // CENSIMENTI
			{

				PagedListInfo searchListInfo = this.getPagedListInfo( context, "SearchVariazioniCensiListInfo");

				String popup = context.getRequest().getParameter("popup");
				searchListInfo.setLink("ApicolturaApiari.do?command=Search&idTipoVariazione="+tipoRicerca);
				VariazioneCensimentoList listaMovimentazioninom = new VariazioneCensimentoList();
				listaMovimentazioninom.setPagedListInfo(searchListInfo);
				searchListInfo.setSearchCriteria(listaMovimentazioninom, context);
				listaMovimentazioninom.buildList(db);
				context.getRequest().setAttribute("ListaVariazioni", listaMovimentazioninom);
				java.util.Date dateCurr = new java.util.Date();
				context.getRequest().setAttribute("dataOdierna", dateCurr);

				return "SearchVariazioniCensimentiOK";
			}
			case 3 : // DETENTORE
			{

				PagedListInfo searchListInfo = this.getPagedListInfo( context, "SearchVariazioniDetListInfo");

				String popup = context.getRequest().getParameter("popup");
				searchListInfo.setLink("ApicolturaApiari.do?command=Search&idTipoVariazione="+tipoRicerca);
				VariazioneDetentoreList listaMovimentazioninom = new VariazioneDetentoreList();
				listaMovimentazioninom.setPagedListInfo(searchListInfo);
				searchListInfo.setSearchCriteria(listaMovimentazioninom, context);
				listaMovimentazioninom.buildList(db);
				context.getRequest().setAttribute("ListaVariazioni", listaMovimentazioninom);

				return "SearchVariazioniDetentoreOK";
			}
			}


		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		return "-none-";
	}

	public String executeCommandSearchApiari(ActionContext context) throws IndirizzoNotFoundException, IOException
	{
		ArrayList<Stabilimento> lista = new ArrayList<Stabilimento>();
		Connection db = null;
		try {
			db = this.getConnection(context);
			String nomeComune = context.getParameter("searchTerm");

			JsonObject obj = new JsonObject();



			/*StabilimentoList listaStab = new StabilimentoList();
			listaStab.setIdOperatore(idOperatore);
			listaStab.setComune(nomeComune);
			listaStab.setStatoApiario(StabilimentoAction.API_STATO_VALIDATO);
			listaStab.setIdApiarioScelto(context.getParameter("idApiarioScelto"));


			listaStab.buildList(db);
			Iterator it = listaStab.iterator();
			JsonArray array = new JsonArray();
			while (it.hasNext())
			{
				Stabilimento stab = (Stabilimento) it.next();
				lista.add(stab);

				JsonObject o = new JsonObject();
				o.addProperty("id", stab.getIdStabilimento());
				o.addProperty("idOperatore", stab.getIdOperatore());
				o.addProperty("comune", stab.getSedeOperativa().getDescrizioneComune());
				o.addProperty("indirizzo", stab.getSedeOperativa().getVia());
				o.addProperty("provincia", stab.getSedeOperativa().getDescrizione_provincia());
				o.addProperty("cfDetentore", stab.getDetentore().getCodFiscale());
				o.addProperty("detentore", stab.getDetentore().getCognome()+" "+stab.getDetentore().getNome());
				o.addProperty("progressivo", stab.getProgressivoBDA());
				o.addProperty("numAlveari", stab.getNumAlveari());


				array.add(o);
			}
			 */

			String  idApiarioScelto = context.getParameter("idApiarioScelto");
			if (idApiarioScelto==null || "".equals(idApiarioScelto))
				idApiarioScelto ="-1";

			JsonArray array = new JsonArray();
			Stabilimento st = new Stabilimento();
			Operatore op = new Operatore();
			Indirizzo ind = new Indirizzo();

			ind.setDescrizioneComune(nomeComune);

			st.setSedeOperativa(ind);
			op.setCodiceAzienda(context.getParameter("codiceAzienda"));
			st.setOperatore(op);
			WsApiAnagraficaApi wsApi = new WsApiAnagraficaApi();
			List<Api> listaApiari =  wsApi.search(st, db, getUserId(context));

			int numApiari = 0 ;
			for(Api apiario : listaApiari)
			{

				if (apiario.getApiId()!=Integer.parseInt(idApiarioScelto) && (apiario.getComDescrizione().toLowerCase().contains(nomeComune.toLowerCase()) || nomeComune.equalsIgnoreCase("")))
				{
					numApiari++;
					JsonObject o = new JsonObject();
					o.addProperty("id", apiario.getApiId());
					o.addProperty("comune", apiario.getComDescrizione());
					o.addProperty("indirizzo", apiario.getIndirizzo());
					o.addProperty("provincia", apiario.getComProSigla());
					o.addProperty("cfDetentore", apiario.getDetenIdFiscale());
					o.addProperty("detentore", apiario.getDetenCognNome());
					o.addProperty("progressivo", apiario.getProgressivo());
					o.addProperty("numAlveari", apiario.getNumAlveari());
					o.addProperty("latitudine", apiario.getLatitudine());
					o.addProperty("longitudine", apiario.getLongitudine());


					o.addProperty("numSciami", apiario.getNumSciami());

					array.add(o);
				}



			}

			if (numApiari==0)
			{
				JsonObject o = new JsonObject();
				o.addProperty("id", -1);
				o.addProperty("comune", "Non Esistono In BDA Apiari Associati al codice "+context.getParameter("codiceAzienda"));
				o.addProperty("indirizzo", "");
				o.addProperty("provincia", "");
				o.addProperty("cfDetentore", "");
				o.addProperty("detentore", "");
				o.addProperty("progressivo", "");
				o.addProperty("numAlveari", "");
				array.add(o);
			}


			obj.addProperty("page", lista.size()/5);
			obj.addProperty("total", lista.size());
			obj.addProperty("records", "5");
			obj.add("rows", array);

			Gson gson = new GsonBuilder().create();

			context.getResponse().getOutputStream().print(gson.toJson(obj));

		}
		catch(SQLException e)
		{

		} catch (BusinessWsException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
		return "-none-";
	}
	public String executeCommandValidate(ActionContext context) {

		Connection db = null;
		try {
			db = this.getConnection(context);

			String codiceAzienda = "" ;
			int  idStabilimento = Integer.parseInt(context.getParameter("idStabilimento"));
			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

			Stabilimento stab  = new Stabilimento(db,idStabilimento);
			Operatore op  = stab.getOperatore();

			stab.setModifiedBy(thisUser.getUserId());
			stab.setNumSciami(Integer.parseInt(context.getRequest().getParameter("numSciami")));
			stab.setNumAlveari(Integer.parseInt(context.getRequest().getParameter("numAlveari")));

			stab.setNumPacchi(Integer.parseInt(context.getRequest().getParameter("numPacchi")));
			stab.setNumRegine(Integer.parseInt(context.getRequest().getParameter("numRegine")));


			stab.setDataApertura(context.getParameter("dataApertura"));
			stab.setIdApicolturaClassificazione(context.getParameter("idApicolturaClassificazione"));
			stab.setIdApicolturaModalita(context.getParameter("idApicolturaModalita"));
			stab.setIdApicolturaSottospecie(context.getParameter("idApicolturaSottospecie"));

			Indirizzo sedeOperativa = stab.getSedeOperativa();
			sedeOperativa.setComune(context.getParameter("comuneSO"));
			sedeOperativa.setCap(context.getParameter("pressoSO"));
			sedeOperativa.setProvincia(context.getParameter("searchcodeIdprovinciaTestoSO"));
			sedeOperativa.setVia(context.getParameter("viaTestoSO"));
			sedeOperativa.setLatitudine(context.getParameter("latitudine"));
			sedeOperativa.setLongitudine(context.getParameter("longitudine"));
			stab.setSedeOperativa(sedeOperativa);
			stab.update(db, context);

			Indirizzo sedeLegale = op.getSedeLegale();

			sedeLegale.setComune(context.getParameter("comuneSL"));
			sedeLegale.setCap(context.getParameter("pressoSL"));
			sedeLegale.setProvincia(context.getParameter("searchcodeIdprovinciaTestoSL"));
			sedeLegale.setVia(context.getParameter("viaTestoSL"));
			sedeLegale.setIdIndirizzo(-1);

			op.setModifiedBy(thisUser.getUserId());
			op.setIdTipoAttivita(context.getParameter("idTipoAttivita"));
			op.setDomicilioDigitale(context.getParameter("domicilioDigitale"));
			op.setTelefono1(context.getParameter("telefono1"));
			op.setTelefono2(context.getParameter("telefono2"));
			op.cancellaSedeLegale(db, sedeLegale);
			op.getListaSediOperatore().add(sedeLegale);
			op.update(db, context);






			int progressivo = 1;
			int istat = -1 ;
			String siglProv = "" ;

			String sel = "select max(progressivo)+1,sigla,istat from apicoltura_imprese_progressivi_comuni where id_comune =? group by sigla,istat ";
			PreparedStatement pst = db.prepareStatement(sel);
			pst.setInt(1, op.getSedeLegale().getComune());
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				progressivo = rs.getInt(1) ;
				istat=rs.getInt("istat");
				siglProv = rs.getString("sigla");
			}

			String istatasString = org.aspcfs.utils.StringUtils.zeroPad(istat,3);
			String progressivoPadding = org.aspcfs.utils.StringUtils.zeroPad(progressivo,3);


			codiceAzienda = ""+siglProv+istatasString+progressivoPadding ;
			String update = "update apicoltura_imprese set codice_azienda_regionale = ?,stato=? where id = ? ";
			pst = db.prepareStatement(update);
			pst.setString(1, codiceAzienda);
			pst.setInt(2, StabilimentoAction.API_STATO_VALIDATO);
			pst.setInt(3, op.getIdOperatore());
			pst.execute();
			context.getRequest().setAttribute("idStab", stab.getIdStabilimento());


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


	public String executeCommandAdd(ActionContext context) {


		Connection db = null;
		try {
			db = this.getConnection(context);

			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

			ComuniAnagrafica c = new ComuniAnagrafica();

			String flagDia = context.getParameter("flagDia");
			if (flagDia==null)
				flagDia = "false" ;



			Stabilimento newStabilimento = new Stabilimento();

			if ((Stabilimento) context.getRequest().getAttribute("newStabilimento") != null)
				newStabilimento = (Stabilimento) context.getRequest().getAttribute("newStabilimento");
			else
				if ((Stabilimento) context.getRequest().getAttribute("Stabilimento") != null)
					newStabilimento = (Stabilimento) context.getRequest().getAttribute("Stabilimento");



			int idOperatore = -1;

			if (context.getRequest().getParameter("opId") != null)
				idOperatore = Integer.parseInt(context.getRequest().getParameter("opId"));
			else if (context.getRequest().getAttribute("idOp") != null)
				idOperatore = (Integer) (context.getRequest().getAttribute("idOp"));
			else
				idOperatore = newStabilimento.getIdOperatore();

			Operatore operatore = new Operatore () ;
			if (idOperatore>0)
				operatore.queryRecordOperatore(db, idOperatore);
			context.getRequest().setAttribute("Operatore", operatore);
			newStabilimento.setOperatore(operatore);

			context.getRequest().setAttribute("newStabilimento",newStabilimento);

			LookupList aslList = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("AslList", aslList);

			context.getRequest().setAttribute("tipologiaSoggetto",(String) context.getRequest().getParameter("tipologiaSoggetto"));


			Provincia provinciaAsl = new Provincia();
			if (thisUser.getUserRecord().getSuap()==null)
				provinciaAsl.getProvinciaAsl(db, thisUser.getSiteId());

			context.getRequest().setAttribute("provinciaAsl", provinciaAsl);

			if (context.getRequest().getAttribute("newStabilimento")!=null)
				context.getRequest().setAttribute("newStabilimento",context.getRequest().getAttribute("newStabilimento"));
			
			
			LookupList NazioniList = new LookupList(db,"lookup_nazioni");
			NazioniList.addItem(-1, "Seleziona Nazione");
			NazioniList.setRequired(true);
			context.getRequest().setAttribute("NazioniList", NazioniList);


			LookupList apicolturaClassificazione = new LookupList(db,"apicoltura_lookup_classificazione");
			apicolturaClassificazione.addItem(-1, "Seleziona Classificazione");
			apicolturaClassificazione.setRequired(true);
			context.getRequest().setAttribute("ApicolturaClassificazione", apicolturaClassificazione);

			LookupList apicolturaModalita = new LookupList(db,"apicoltura_lookup_modalita");
			apicolturaModalita.addItem(-1, "Seleziona Modalita");
			apicolturaModalita.setRequired(true);
			
			if (operatore.getIdTipoAttivita()==2)
				apicolturaModalita.removeElementByCode(1);
			
			
			context.getRequest().setAttribute("ApicolturaModalita", apicolturaModalita);

			LookupList apicolturaSottospecie = new LookupList(db,"apicoltura_lookup_sottospecie");
			apicolturaSottospecie.addItem(-1, "Seleziona Sottospecie");
			apicolturaSottospecie.setRequired(true);
			context.getRequest().setAttribute("ApicolturaSottospecie", apicolturaSottospecie);

			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,((UserBean) context.getSession().getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList();
			comuniList.queryListComuni(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);
			
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		if(context.getParameter("flagLaboratio")!=null)
			return getReturn(context, "AddStabilimentoLab");

		return getReturn(context, "AddStabilimento");
	}



	public String executeCommandDetails(ActionContext context) {

		Connection db = null;
		SystemStatus systemStatus = this.getSystemStatus(context);
		Stabilimento newStabilimento = null;
		try {


			String tempStabId = context.getRequest().getParameter("stabId");
			if (tempStabId == null) {
				tempStabId = ""
						+ (Integer) context.getRequest().getAttribute("idStab");
			}
			// String iter = context.getRequest().getParameter("tipo");
			Integer tempid = null;
			Integer stabid = null;


			if (tempStabId != null) {
				stabid = Integer.parseInt(tempStabId);
			}

			db = this.getConnection(context);	

			newStabilimento = new Stabilimento(db,  stabid);

			context.getRequest().setAttribute("StabilimentoDettaglio",newStabilimento);

			Operatore operatore = new Operatore () ;
			org.aspcf.modules.controlliufficiali.base.Organization org = new org.aspcf.modules.controlliufficiali.base.Organization();
			org.setName(operatore.getRagioneSociale());


			TicketList diffList = new TicketList();
			diffList.setIdApiario(newStabilimento.getIdStabilimento());
			diffList.buildListDiffideOsaCentralizzato(db);
			context.getRequest().setAttribute("DiffideList", diffList);
			
			Ticket t = new Ticket();
			context.getRequest().setAttribute("DiffideList", t.getDiffide(db,false,null,newStabilimento.getIdStabilimento(),null,null));
			context.getRequest().setAttribute("DiffideListStorico", t.getDiffide(db,true,null,newStabilimento.getIdStabilimento(),null,null));


			operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());	
			context.getRequest().setAttribute("Operatore", operatore);


			LookupList ListaStati = new LookupList(db,"lookup_stato_lab");
			ListaStati.addItem(-1, getSystemStatus(context).getLabel("calendar.none.4dashes"));
			context.getRequest().setAttribute("ListaStati", ListaStati);


			LookupList aslList = new LookupList(db, "lookup_site_id");
			aslList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", aslList);
			
			LookupList lookupStati = new LookupList(db,"lookup_apicoltura_stati_apiario");
			context.getRequest().setAttribute("LookupStati", lookupStati);

			LookupList apicolturaClassificazione = new LookupList(db,"apicoltura_lookup_classificazione");
			apicolturaClassificazione.addItem(-1, "Seleziona Classificazione");
			apicolturaClassificazione.setRequired(true);
			context.getRequest().setAttribute("ApicolturaClassificazione", apicolturaClassificazione);

			LookupList apicolturaModalita = new LookupList(db,"apicoltura_lookup_modalita");
			apicolturaModalita.addItem(-1, "Seleziona Modalita");
			apicolturaModalita.setRequired(true);
			if (newStabilimento.getOperatore().getIdTipoAttivita()==2)
				apicolturaModalita.removeElementByCode(1);
			context.getRequest().setAttribute("ApicolturaModalita", apicolturaModalita);

			LookupList apicolturaSottospecie = new LookupList(db,"apicoltura_lookup_sottospecie");
			apicolturaSottospecie.addItem(-1, "Seleziona Sottospecie");
			apicolturaSottospecie.setRequired(true);
			context.getRequest().setAttribute("ApicolturaSottospecie", apicolturaSottospecie);

			LookupList tipoAttivitaApi = new LookupList(db,"apicoltura_lookup_tipo_attivita");
			tipoAttivitaApi.addItem(-1, "Seleziona Nazione");
			context.getRequest().setAttribute("TipoAttivitaApi", tipoAttivitaApi);

			ComuniAnagrafica c = new ComuniAnagrafica();
			// Provvisoriamente prendo tutti i comuni
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,((UserBean) context.getSession().getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList();
			comuniList.queryListComuni(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);



			String noteEsito = WSBdnLog.getNoteEsitoKO(db, stabid);
			context.getRequest().setAttribute("noteEsitoKO", noteEsito);

			context.getRequest().setAttribute("esitoValidazione", context.getRequest().getParameter("esitoValidazione"));




			return getReturn(context, "DetailsApiario");

		} catch (Exception e) {
			e.printStackTrace();
			// Go through the SystemError process
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}


	public String executeCommandSearchForm(ActionContext context) {

		//Bypass search form for portal users
		if (isPortalUser(context)) {
			return (executeCommandSearch(context));
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		Connection db = null;
		try {
			db = getConnection(context);
			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "--Tutti--");
			context.getRequest().setAttribute("SiteList", siteList);


			LookupList lookupStati = new LookupList(db,"lookup_apicoltura_stati_apiario");
			context.getRequest().setAttribute("LookupStati", lookupStati);

			if(context.getRequest().getAttribute("EsitoImport")!=null)
				context.getRequest().setAttribute("EsitoImport",context.getRequest().getAttribute("EsitoImport"));

			this.deletePagedListInfo(context, "SearchOpuListInfo");
			//reset the offset and current letter of the paged list in order to make sure we search ALL accounts
			PagedListInfo orgListInfo = this.getPagedListInfo(context, "SearchOpuListInfo");
			orgListInfo.setCurrentLetter("");
			orgListInfo.setCurrentOffset(0);



		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "Search Accounts", "Accounts Search");
		return ("SearchOK");
	}


	public String executeCommandSearch(ActionContext context) {


		StabilimentoList organizationList = new StabilimentoList();
		//Prepare pagedListInfo
		PagedListInfo searchListInfo = this.getPagedListInfo(context, "SearchOpuListInfo");
		searchListInfo.setLink("ApicolturaAttivita.do?command=Search");

		Connection db = null;
		try {
			db = this.getConnection(context);	      
			String searchFor = context.getRequest().getParameter("ricercaPer");
			
			searchListInfo.setSearchCriteria(organizationList, context);     
			organizationList.setPagedListInfo(searchListInfo);
			organizationList.buildList(db);


			//organizationList.setCodiceFiscale(context.getParameter("searchCodiceFiscale"));

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");

			context.getRequest().setAttribute("SiteIdList", siteList);
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


	public String executeCommandInsert(ActionContext context)
			throws Exception {


		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		boolean exist = false ;
		boolean inseritoBdn = true;

		// Integer orgId = null;
		Operatore operatore =	new Operatore();
		Stabilimento newStabilimento = (Stabilimento) context.getRequest().getAttribute("Stabilimento");
		try {
			//				SoggettoFisico soggettoAdded = null;

			db = this.getConnection(context);

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,-1);
			LookupList comuniList = new LookupList();
			comuniList.queryListComuni(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);

			LookupList provinceList = new LookupList(db, "lookup_province");
			provinceList.addItem(-1, "");
			SoggettoFisico soggettoAdded = null;
			soggettoAdded = new SoggettoFisico(context.getRequest());
			SoggettoFisico soggettoEsistente = null;
			soggettoEsistente = soggettoAdded.verificaSoggetto(db);

			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			/* se esiste */

			if (soggettoEsistente.getIdSoggetto() > 0) 
			{
				Indirizzo indirizzoAdded = soggettoAdded.getIndirizzo();


				newStabilimento.setDetentore(soggettoEsistente);
			}


			Indirizzo indirizzoAdded = null;
			LookupList nazioniList = new LookupList(db,"lookup_nazioni");

			indirizzoAdded = new Indirizzo(context.getRequest(), db,context);
			indirizzoAdded.setTipologiaSede(5);
			newStabilimento.setSedeOperativa(indirizzoAdded);

			newStabilimento.setEnteredBy(getUserId(context));
			newStabilimento.setModifiedBy(getUserId(context));
			newStabilimento.setIdOperatore(Integer.parseInt(context.getParameter("idOperatore")));
			
			newStabilimento.setAslRomaBdn(context.getParameter("aslRomaBdn"));

			operatore.queryRecordOperatore(db,newStabilimento.getIdOperatore());
			
			//Nuova gestione
			PreparedStatement pst = db.prepareStatement("select max(progressivo) as progressivo from apicoltura_apiari where id_operatore = " +operatore.getIdOperatore());
			ResultSet rs = pst.executeQuery();
			int progressivo = 0;
			if(rs.next())
				progressivo = rs.getInt("progressivo");
			newStabilimento.setProgressivoBDA(""+ (progressivo+1));
			//Fine nuova gestione
			
			/*Vecchia gestione
			newStabilimento.setProgressivoBDA(""+ (operatore.getListaStabilimenti().size()+1));
			Fine vecchia gestione*/
			newStabilimento.setFlagLaboratorio(false);
			newStabilimento.setOperatore(operatore);
			context.getRequest().setAttribute("opId", newStabilimento.getIdOperatore());
			newStabilimento.setData_assegnazione_detentore(newStabilimento.getDataApertura());
			String esitoStabilimento = newStabilimento.checkEsistenza(db);
			newStabilimento.setStato(StabilimentoAction.API_STATO_DA_NOTIFICARE);
			isValid = this.validateObject(context, db, newStabilimento);
			
			Api api = null;
			if (isValid ) {   
				
				
				if (newStabilimento.getOperatore().getIdBda()>0)
				{



					try
					{
						WsApiAnagraficaPersoneBdn servicePersone = new WsApiAnagraficaPersoneBdn();
						Persone detentore =  servicePersone.insertAnagraficaPersona(newStabilimento.getDetentore(), db, getUserId(context));

						WsApiAnagraficaApi servizioBdn = new WsApiAnagraficaApi();
						api = servizioBdn.insertApiAnagraficaApiario(newStabilimento, db,getUserId(context),context);
						newStabilimento.setIdBda(api.getApiId());

						newStabilimento.sincronizzaBdn(db, getUserId(context),api.getApiId());

						Timestamp timestamp = new Timestamp(System.currentTimeMillis());
						UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

						String msg = "INSERITO NUOVO APIARIO IN IMPRESA : "+operatore.getRagioneSociale()+"\n"+
								"PROPRIETARIO: "+operatore.getRappLegale().getNome()+" "+operatore.getRappLegale().getCognome()+"\n"+
								"PARTITA IVA: "+operatore.getPartitaIva()+"\n"+
								"ESITO : OK"+"\n"+
								"CODICE AZIENDA  : "+operatore.getCodiceAzienda()+"\n"+
								"DATA INVIO :"+timestamp+"\n"+
								"INVIATA DA :"+thisUser.getUsername();
						sendMailIzsm(context.getRequest(), msg, "##GISA_API_WS_BDN_OK##", "gisahelpdesk@usmail.it");
					}
					catch (EccezioneDati e) 
					{
						System.out.println("Errore nell'invio in BDN : "+e.getMessage());
						Timestamp timestamp = new Timestamp(System.currentTimeMillis());
						UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

						String msg = ApiUtil.getInfoRichiesta(operatore, "KO", e.getMessage(), timestamp, thisUser,  new LookupList(db, "lookup_site_id"), new LookupList(db, "lookup_apicoltura_stati_apiario"),  new LookupList(db, "apicoltura_lookup_tipo_attivita"), new LookupList(db, "apicoltura_lookup_classificazione"), new LookupList(db, "apicoltura_lookup_sottospecie"), new LookupList(db, "apicoltura_lookup_modalita"));				


						if (e.getMessage().contains("RECORD ESISTENTE"))
						{
							System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] ATTENZIONE: ERRORE RECORD ESISTENTE. ID APIARIO: " + newStabilimento.getIdApiario()); 
							e.printStackTrace();
							
							newStabilimento.sincronizzaBdn(db, getUserId(context), null);

							if (db.getAutoCommit()==false)
							{
								System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] ATTENZIONE: ERRORE RECORD ESISTENTE. FACCIO COMMIT. ID APIARIO: " + newStabilimento.getIdApiario()); 
								db.commit();
							}
						}
						else if (e.getMessage().toUpperCase().contains("LATITUDINE") || e.getMessage().toUpperCase().contains("LONGITUDINE") || e.getMessage().toUpperCase().contains("COORDINATE"))
						{
							System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] ATTENZIONE: ERRORE COORDINATE. ID APIARIO: " + newStabilimento.getIdApiario()); 
							e.printStackTrace();
							
							newStabilimento.sincronizzaBdn(db, getUserId(context), null);

							if (db.getAutoCommit()==false)
							{
								System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] ATTENZIONE: ERRORE COORDINATE. FACCIO COMMIT. ID APIARIO: " + newStabilimento.getIdApiario()); 
								db.commit();
							}
						}
						else
						{
							System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] ATTENZIONE: ERRORE GENERICO. ID APIARIO: " + newStabilimento.getIdApiario()); 
							e.printStackTrace();
							
							newStabilimento.errSincronizzaBdn(db, e.getMessage());
							if (db.getAutoCommit()==false)
							{
								System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] ATTENZIONE: ERRORE GENERICO. ID APIARIO: " + newStabilimento.getIdApiario()); 
								db.commit();

							}

						}



						context.getRequest().setAttribute("ErrorValidazioneError", e.getMessage());

						try
						{
							sendMailIzsm(context.getRequest(), msg+". L'errore e il seguente :"+e.getMessage(), "##GISA_API_WS_BDN_KO##", "gisahelpdesk@usmail.it");
						}
						catch(Exception e2)
						{
							System.out.println("Errore nell'invio della mail");
						}
						context.getRequest().setAttribute("Stabilimento", newStabilimento);
						return executeCommandAdd(context);
					}
					catch(Exception e)
					{
						System.out.println("SYNC- Errore nell'invio in BDN : "+e.getMessage());
					}
				}
				
				System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] TENTATIVO DI INSERIMENTO APIARIO " + ""); 
				recordInserted = newStabilimento.insert(db, true,context);
				System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] FINE TENTATIVO DI INSERIMENTO APIARIO " + ""); 
				System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] UPDATE STATO OPERATORE " + ""); 
				operatore.updateStato( db,operatore.getIdOperatore());
				System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] FINE UPDATE STATO OPERATORE " + ""); 
				//Se è il primo apiario e l'attività è per commercializzazione 
				if(operatore.getListaStabilimenti().isEmpty() && operatore.getIdTipoAttivita()==1)
				{
					System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] UPDATE TELEFONO " + ""); 
					operatore.updateTelefono(db, operatore.getIdOperatore(),context.getRequest().getParameter("telefono1"));
					System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] FINE UPDATE TELEFONO " + ""); 
				}
				
				if (recordInserted)
				{
					System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] RECORD INSERTED " + ""); 
					System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] INSERT API " + ""); 
					RicercheAnagraficheTab.inserApi(db, newStabilimento.getIdStabilimento());
					System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] FINE INSERT API " + ""); 
					if (newStabilimento.getOperatore().getIdBda()>0 && api!=null)
					{
						System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] SINCRONIZZA BDN " + ""); 
						newStabilimento.sincronizzaBdn(db, getUserId(context), api.getApiId());
						System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] FINE SINCRONIZZA BDN " + ""); 
					}
					System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] CAMBIO DETENTORE E UBICAZIONE " + ""); 
					newStabilimento.cambioDetentore(db, context);
					newStabilimento.cambioUbicazione(db, context,newStabilimento.getSedeOperativa().getIdIndirizzo(),false);
					System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] FINE CAMBIO DETENTORE E UBICAZIONE " + ""); 
					
					//Gestione invio in Bdn primo apiario per autoconsumo
					Boolean funzioneAbilitata = Boolean.parseBoolean(ApplicationProperties.getProperty("generazione_automatica_cun_autoconsumo"));
					if( funzioneAbilitata && newStabilimento.getOperatore().getIdTipoAttivita()==2 && newStabilimento.getOperatore().getListaStabilimenti().isEmpty())
					{
						System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] GENERAZIONE AUTOMATICA CUN " + ""); 
						//Recupero codice azienda in automatico
						String codiceAzienda = null;
						WsApiAnagraficaAziende wsAziende = new WsApiAnagraficaAziende();
						List<Apiazienda> listaAziende = wsAziende.getCodiceDisponibile(db, newStabilimento.getOperatore().getSedeLegale().getCodiceIstatComune(), newStabilimento.getOperatore().getSedeLegale().getSiglaProvincia(), "", getUserId(context));
						
						if(!listaAziende.isEmpty())
						{
							codiceAzienda = listaAziende.get(0).getCodice();
						}
						else
							context.getRequest().setAttribute("Error", "Non è stato possibile generare un codice azienda in automatico. Prego procedere alla validazione manualmente");
						//Se è stato possibile recuperare un codice azienda in automatico
						
						if(codiceAzienda!=null)
						{
							OperatoreAction opu = new OperatoreAction();
							String esitoValidazione = validaAziendaEApiari(context,codiceAzienda);
							if(esitoValidazione!=null)
							{
								esitoValidazione = "Errore durante l'invio di azienda e apiari in Bdn: " + esitoValidazione;
								context.getRequest().setAttribute("esitoValidazione", esitoValidazione);
							}
								
						}
						System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] FINE GENERAZIONE AUTOMATICA CUN " + ""); 
						
					}
					//Fine gestione invio in Bdn primo apiario per autoconsumo
					
					//Ricerca censimento
					WsApiAnagraficaVariazioniCen censimento = new WsApiAnagraficaVariazioniCen();
					int progressivoApiario  = Integer.parseInt(newStabilimento.getProgressivoBDA());
					
					System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] RICERCA LISTA CENSIMENTI " + ""); 
					List<Apicen> censimenti = censimento.search(newStabilimento.getOperatore().getCodiceAzienda(), progressivoApiario, newStabilimento.getDataApertura(), "S",db);
					System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] FINE RICERCA LISTA CENSIMENTI " + ""); 
					
					//Inserimento Censimento
					if(!censimenti.isEmpty())
					{
						System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] SET DATA ASSEGNAZIONE CENSIMENTO E NUOVO CENSIMENTO " + "");
						newStabilimento.setData_assegnazione_censimento(newStabilimento.getDataApertura());
						newStabilimento.nuovoCensimento(db, context, censimenti.get(0).getApicenId());
						System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] FINE DATA ASSEGNAZIONE CENSIMENTO E NUOVO CENSIMENTO " + "");
					}
				}
			}
			else
			{					
				context.getRequest().setAttribute("Stabilimento", newStabilimento);
				return executeCommandAdd(context);

			}
			Operatore op = new Operatore(db,newStabilimento.getIdOperatore());
			context.getRequest().setAttribute("Apiario", "SI");

			context.getRequest().setAttribute("OrgDetails", newStabilimento);

		}
		catch(Exception e)
		{
			if(e.getMessage().contains("check_capna"))
				context.getRequest().setAttribute("Error", "Il cap deve essere diverso da 80100");
			else
				context.getRequest().setAttribute("Error", e.getMessage());
			e.printStackTrace();
			return ("SystemError");
		}

		finally 
		{
			this.freeConnection(context, db);
		}
		
		if (!inseritoBdn){
			context.getRequest().setAttribute("Stabilimento", newStabilimento);
			return executeCommandAdd(context);
		}
		return "InsertOK";


	}



	public String executeCommandInsertLaboratorio(ActionContext context)
			throws Exception {


		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;
		boolean exist = false ;


		// Integer orgId = null;
		Operatore operatore =	new Operatore();
		Stabilimento newStabilimento = (Stabilimento) context.getRequest().getAttribute("Stabilimento");
		try {
			//				SoggettoFisico soggettoAdded = null;

			db = this.getConnection(context);

			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,-1);
			LookupList comuniList = new LookupList();
			comuniList.queryListComuni(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);

			LookupList provinceList = new LookupList(db, "lookup_province");
			provinceList.addItem(-1, "");
			SoggettoFisico soggettoAdded = null;
			soggettoAdded = new SoggettoFisico(context.getRequest());


			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			/* se esiste */



			Indirizzo indirizzoAdded = null;
			LookupList nazioniList = new LookupList(db,"lookup_nazioni");

			indirizzoAdded = new Indirizzo(context.getRequest(), db,context);
			indirizzoAdded.setTipologiaSede(5);
			newStabilimento.setSedeOperativa(indirizzoAdded);

			newStabilimento.setEnteredBy(getUserId(context));
			newStabilimento.setModifiedBy(getUserId(context));
			newStabilimento.setIdOperatore(Integer.parseInt(context.getParameter("idOperatore")));
			

			operatore.queryRecordOperatore(db,newStabilimento.getIdOperatore());
			
			newStabilimento.setDetentore(operatore.getRappLegale());
			newStabilimento.setFlagLaboratorio(true);
			newStabilimento.setOperatore(operatore);

			context.getRequest().setAttribute("opId", newStabilimento.getIdOperatore());
			newStabilimento.setData_assegnazione_detentore(newStabilimento.getDataApertura());


			newStabilimento.setStato(StabilimentoAction.API_STATO_VALIDATO);
			isValid = true;
			if (isValid ) {   
				recordInserted = newStabilimento.insert(db, true,context);


			}
			else
			{					
				context.getRequest().setAttribute("Stabilimento", newStabilimento);
				return executeCommandAdd(context);

			}
			Operatore op = new Operatore(db,newStabilimento.getIdOperatore());
			context.getRequest().setAttribute("Apiario", "SI");

			context.getRequest().setAttribute("OrgDetails", newStabilimento);




		}
		catch(SQLException e){
			e.printStackTrace();

		}

		finally {
			this.freeConnection(context, db);

		}
		return "InsertOK";


	}


	public String executeCommandViewVigilanza(ActionContext context) {


		Connection db = null;
		org.aspcfs.modules.vigilanza.base.TicketList ticList = new org.aspcfs.modules.vigilanza.base.TicketList();


		String tempStabId = context.getRequest().getParameter("stabId");
		if (tempStabId == null) {
			tempStabId = ""
					+ (Integer) context.getRequest().getAttribute("idStab");
		}
		// String iter = context.getRequest().getParameter("tipo");
		Integer tempid = null;
		Integer stabid = null;



		if (tempStabId != null) {
			stabid = Integer.parseInt(tempStabId);
		}
		ticList.setIdApiario(stabid);
		// Prepare PagedListInfo
		PagedListInfo ticketListInfo = this.getPagedListInfo(context,
				"AccountTicketInfo", "t.assigned_date", "desc");
		ticketListInfo.setLink(context.getAction().getActionName()+".do?command=ViewVigilanza&stabId="+stabid
				);
		ticList.setPagedListInfo(ticketListInfo);
		try {



			db = this.getConnection(context);	




			Stabilimento newStabilimento = new Stabilimento(db,  stabid);

			context.getRequest().setAttribute("OrgDetails", newStabilimento);

			Operatore operatore = new Operatore () ;
			org.aspcf.modules.controlliufficiali.base.Organization org = new org.aspcf.modules.controlliufficiali.base.Organization();
			org.setName(operatore.getRagioneSociale());



			operatore.queryRecordOperatore(db, newStabilimento.getIdOperatore());	
			context.getRequest().setAttribute("Operatore", operatore);


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
			controlliList.setIdApiario(newStabilimento.getIdStabilimento());
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

			ticList.setSiteId(thisUser.getSiteId());
			
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


	public String executeCommandUpdateSoggettoFisicoStabilimento(ActionContext context)throws SQLException {


		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;



		try {

			db = this.getConnection(context);
			boolean exist = false;

			org.aspcfs.modules.accounts.base.ComuniAnagrafica c = new org.aspcfs.modules.accounts.base.ComuniAnagrafica();
			ArrayList<org.aspcfs.modules.accounts.base.ComuniAnagrafica> listaComuni = c.buildList_all(db,((UserBean) context.getSession().getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList(listaComuni,-1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);


			LookupList lookupStati = new LookupList(db,"lookup_apicoltura_stati_apiario");
			context.getRequest().setAttribute("LookupStati", lookupStati);


			LookupList aslList = new LookupList(db, "lookup_site_id");
			aslList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", aslList);

			LookupList NazioniList = new LookupList(db,"lookup_nazioni");
			NazioniList.addItem(-1, "Seleziona Nazione");
			NazioniList.setRequired(true);
			context.getRequest().setAttribute("NazioniList", NazioniList);


			LookupList tipoAttivitaApi = new LookupList(db,"apicoltura_lookup_tipo_attivita");
			tipoAttivitaApi.addItem(-1, "Seleziona Tipo Attivita");
			context.getRequest().setAttribute("TipoAttivitaApi", tipoAttivitaApi);

			LookupList apicolturaClassificazione = new LookupList(db,"apicoltura_lookup_classificazione");
			apicolturaClassificazione.addItem(-1, "Seleziona Classificazione");
			apicolturaClassificazione.setRequired(true);
			context.getRequest().setAttribute("ApicolturaClassificazione", apicolturaClassificazione);

			LookupList apicolturaModalita = new LookupList(db,"apicoltura_lookup_modalita");
			apicolturaModalita.addItem(-1, "Seleziona Modalita");
			apicolturaModalita.setRequired(true);
			context.getRequest().setAttribute("ApicolturaModalita", apicolturaModalita);

			LookupList apicolturaSottospecie = new LookupList(db,"apicoltura_lookup_sottospecie");
			apicolturaSottospecie.addItem(-1, "Seleziona Sottospecie");
			apicolturaSottospecie.setRequired(true);
			context.getRequest().setAttribute("ApicolturaSottospecie", apicolturaSottospecie);

			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			int idStabilimento = Integer.parseInt(context.getParameter("idStabilimento"));

			Stabilimento stab = new Stabilimento(db, idStabilimento);



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
				stab.setDetentore(soggettoAdded);

			} else 
			{
				/* se esiste */

				if (soggettoEsistente.getIdSoggetto() > 0) 
				{
					Indirizzo indirizzoAdded = soggettoAdded.getIndirizzo();


					stab.setDetentore(soggettoEsistente);




				}
			}

			context.getRequest().setAttribute("idStab", stab.getIdStabilimento());
			context.getRequest().setAttribute("opId", stab.getIdOperatore());
			stab.setData_assegnazione_detentore(context.getParameter("data_assegnazione_detentore"));
			stab.cambioDetentore(db,  context);

			Operatore op = new Operatore();
			op.queryRecordOperatore(db, stab.getIdOperatore());
			//newStabilimento.get
			context.getRequest().setAttribute("OperatoreDettagli", op);

			WsApiAnagraficaVariazioniDet serviceDet = new WsApiAnagraficaVariazioniDet();
			serviceDet.insertApiAnagraficaDetentore(stab, db, getUserId(context));
			
			OperatoreAction.abilitazioneTastoCensimenti(context.getRequest());

		} catch (Exception errorMessage) 
		{

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");


		return "InsertVariazioneOK";
	}



	public String executeCommandUpdateCensimento(ActionContext context)throws SQLException {


		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;


		Stabilimento stab = null;
		try {

			db = this.getConnection(context);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			org.aspcfs.modules.accounts.base.ComuniAnagrafica c = new org.aspcfs.modules.accounts.base.ComuniAnagrafica();
			ArrayList<org.aspcfs.modules.accounts.base.ComuniAnagrafica> listaComuni = c.buildList_all(db,((UserBean) context.getSession().getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList(listaComuni,-1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);


			LookupList lookupStati = new LookupList(db,"lookup_apicoltura_stati_apiario");
			context.getRequest().setAttribute("LookupStati", lookupStati);


			LookupList aslList = new LookupList(db, "lookup_site_id");
			aslList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", aslList);

			LookupList NazioniList = new LookupList(db,"lookup_nazioni");
			NazioniList.addItem(-1, "Seleziona Nazione");
			NazioniList.setRequired(true);
			context.getRequest().setAttribute("NazioniList", NazioniList);


			LookupList tipoAttivitaApi = new LookupList(db,"apicoltura_lookup_tipo_attivita");
			tipoAttivitaApi.addItem(-1, "Seleziona Tipo Attivita");
			context.getRequest().setAttribute("TipoAttivitaApi", tipoAttivitaApi);

			LookupList apicolturaClassificazione = new LookupList(db,"apicoltura_lookup_classificazione");
			apicolturaClassificazione.addItem(-1, "Seleziona Classificazione");
			apicolturaClassificazione.setRequired(true);
			context.getRequest().setAttribute("ApicolturaClassificazione", apicolturaClassificazione);

			LookupList apicolturaModalita = new LookupList(db,"apicoltura_lookup_modalita");
			apicolturaModalita.addItem(-1, "Seleziona Modalita");
			apicolturaModalita.setRequired(true);
			context.getRequest().setAttribute("ApicolturaModalita", apicolturaModalita);

			LookupList apicolturaSottospecie = new LookupList(db,"apicoltura_lookup_sottospecie");
			apicolturaSottospecie.addItem(-1, "Seleziona Sottospecie");
			apicolturaSottospecie.setRequired(true);
			context.getRequest().setAttribute("ApicolturaSottospecie", apicolturaSottospecie);
			boolean exist = false;

			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			int idStabilimento = Integer.parseInt(context.getParameter("idStabilimento"));

			stab = new Stabilimento(db, idStabilimento);

			stab.setNumAlveari(context.getParameter("numAlveari"));
			stab.setNumSciami(context.getParameter("numSciami"));
			stab.setNumPacchi(context.getParameter("numPacchi"));
			stab.setNumRegine(context.getParameter("numRegine"));
			stab.setData_assegnazione_censimento(context.getParameter("data_assegnazione_censimento"));
			
			WsApiAnagraficaVariazioniCen serviceCen = new WsApiAnagraficaVariazioniCen();
			Apicen censimentoBdn = serviceCen.insertApiAnagraficaCensimento(stab, db, user.getUserId());
			
			stab.nuovoCensimento(db,  context, censimentoBdn.getApicenId());
			RicercheAnagraficheTab.inserApi(db, stab.getIdStabilimento());
			
			Operatore op = new Operatore();
			op.queryRecordOperatore(db, stab.getIdOperatore());
			
			context.getRequest().setAttribute("OperatoreDettagli", op);
			context.getRequest().setAttribute("idStab", stab.getIdStabilimento());
			context.getRequest().setAttribute("opId", stab.getIdOperatore());
			
			OperatoreAction.abilitazioneTastoCensimenti(context.getRequest());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			if(e instanceof BusinessWsException_Exception)
			{
				BusinessWsException_Exception ee = (BusinessWsException_Exception)e;
				if (db.getAutoCommit() == false) 
				{
					db.rollback();
					db.setAutoCommit(true);
				}
				String errore = "";
				errore = ee.getFaultInfo().getMessage() + " : " + ee.getFaultInfo().getResult().getErrore();
	
				for (FieldError error : ee.getFaultInfo().getResult().getErrors()) 
				{
					errore += "[" + error.getField() + ": " + error.getMessage() + "]";
				}
				context.getRequest().setAttribute("Error", errore);
				return ("SystemError");
			}
			else if(e instanceof ServerSOAPFaultException)
			{
				ServerSOAPFaultException ee = (ServerSOAPFaultException)e;
				if (db.getAutoCommit() == false) 
				{
					db.rollback();
					db.setAutoCommit(true);
				}
				String errore = ee.getMessage();
				context.getRequest().setAttribute("Error", errore);
				return ("SystemError");
			}
			else if(e instanceof IndirizzoNotFoundException || e instanceof ParseException || e instanceof java.lang.Exception)
			{
				context.getRequest().setAttribute("Error", e);
				return ("SystemError");
			}
		}
		finally 
		{
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");


		return "InsertVariazioneOK";
	}
	
	
	
	
	

	public String executeCommandUpdateUbicazione(ActionContext context)throws SQLException {


		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;

		Stabilimento stab =null;


		try {

			db = this.getConnection(context);
			org.aspcfs.modules.accounts.base.ComuniAnagrafica c = new org.aspcfs.modules.accounts.base.ComuniAnagrafica();
			ArrayList<org.aspcfs.modules.accounts.base.ComuniAnagrafica> listaComuni = c.buildList_all(db,((UserBean) context.getSession().getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList(listaComuni,-1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);


			LookupList lookupStati = new LookupList(db,"lookup_apicoltura_stati_apiario");
			context.getRequest().setAttribute("LookupStati", lookupStati);


			LookupList aslList = new LookupList(db, "lookup_site_id");
			aslList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", aslList);

			LookupList NazioniList = new LookupList(db,"lookup_nazioni");
			NazioniList.addItem(-1, "Seleziona Nazione");
			NazioniList.setRequired(true);
			context.getRequest().setAttribute("NazioniList", NazioniList);


			LookupList tipoAttivitaApi = new LookupList(db,"apicoltura_lookup_tipo_attivita");
			tipoAttivitaApi.addItem(-1, "Seleziona Tipo Attivita");
			context.getRequest().setAttribute("TipoAttivitaApi", tipoAttivitaApi);

			LookupList apicolturaClassificazione = new LookupList(db,"apicoltura_lookup_classificazione");
			apicolturaClassificazione.addItem(-1, "Seleziona Classificazione");
			apicolturaClassificazione.setRequired(true);
			context.getRequest().setAttribute("ApicolturaClassificazione", apicolturaClassificazione);

			LookupList apicolturaModalita = new LookupList(db,"apicoltura_lookup_modalita");
			apicolturaModalita.addItem(-1, "Seleziona Modalita");
			apicolturaModalita.setRequired(true);
			context.getRequest().setAttribute("ApicolturaModalita", apicolturaModalita);

			LookupList apicolturaSottospecie = new LookupList(db,"apicoltura_lookup_sottospecie");
			apicolturaSottospecie.addItem(-1, "Seleziona Sottospecie");
			apicolturaSottospecie.setRequired(true);
			context.getRequest().setAttribute("ApicolturaSottospecie", apicolturaSottospecie);

			boolean exist = false;

			int idstabilimento = Integer.parseInt(context.getParameter("idStabilimento"));
			stab = new Stabilimento(db,idstabilimento);
			context.getRequest().setAttribute("StabilimentoDettaglio",
					stab);

			stab.setData_assegnazione_ubicazione(context.getParameter("data_assegnazione_ubicazione"));

			Indirizzo indirizzoAdded = null;
			indirizzoAdded = new Indirizzo(context.getRequest(), db,context);
			indirizzoAdded.setTipologiaSede(5);

			indirizzoAdded.setModifiedBy(getUserId(context));
			stab.setSedeOperativa(indirizzoAdded);
			stab.cambioUbicazione(db,context,Integer.parseInt(context.getParameter("indirizzoOld")),false);

			PreparedStatement pst = db.prepareStatement("update apicoltura_movimentazioni_nomadismo set id_indirizzo_origine = ? where id_apiario_out = ? and flag_indirizzo_origine=true");
			pst.setInt(1, stab.getSedeOperativa().getIdIndirizzo());
			pst.setInt(2, stab.getIdStabilimento());
			pst.execute();

			context.getRequest().setAttribute("opId", stab.getIdOperatore());
			Operatore op = new Operatore();
			op.queryRecordOperatore(db, stab.getIdOperatore());
			//newStabilimento.get
			context.getRequest().setAttribute("OperatoreDettagli", op);


			WsApiAnagraficaVariazioniUbi serviceUbi = new WsApiAnagraficaVariazioniUbi();
			serviceUbi.insertApiAnagraficaUbicazione(stab, db, getUserId(context));
			
			OperatoreAction.abilitazioneTastoCensimenti(context.getRequest());

		} catch (Exception errorMessage) 
		{

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");

		return "InsertVariazioneOK";


		// return ("InsertOK");

	}



	public String executeCommandNotificheDaValidare(ActionContext context)throws SQLException {


		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;

		Stabilimento stab =null;


		try {

			UserBean user = (UserBean)context.getSession().getAttribute("User");
			db = this.getConnection(context);

			LookupList tipoAttivitaApi = new LookupList(db,"apicoltura_lookup_tipo_attivita");
			tipoAttivitaApi.addItem(-1, "Seleziona Nazione");
			context.getRequest().setAttribute("TipoAttivitaApi", tipoAttivitaApi);


			LookupList apicolturaClassificazione = new LookupList(db,"apicoltura_lookup_classificazione");
			apicolturaClassificazione.addItem(-1, "Seleziona Classificazione");
			apicolturaClassificazione.setRequired(true);
			context.getRequest().setAttribute("ApicolturaClassificazione", apicolturaClassificazione);

			LookupList apicolturaModalita = new LookupList(db,"apicoltura_lookup_modalita");
			apicolturaModalita.addItem(-1, "Seleziona Modalita");
			apicolturaModalita.setRequired(true);
			context.getRequest().setAttribute("ApicolturaModalita", apicolturaModalita);

			LookupList apicolturaSottospecie = new LookupList(db,"apicoltura_lookup_sottospecie");
			apicolturaSottospecie.addItem(-1, "Seleziona Sottospecie");
			apicolturaSottospecie.setRequired(true);
			context.getRequest().setAttribute("ApicolturaSottospecie", apicolturaSottospecie);


			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");

			context.getRequest().setAttribute("SiteIdList", siteList);

			boolean exist = false;
			/**
			 * COSTRUZIONE DELLA LISTA DELLE NUOVE ATTIVITA DA NOTIFICARE ALL'ASL
			 * PER LE QUALI OCCORRE LA VALIDAZIONE
			 */
			OperatoreList listaAttivita = new OperatoreList();
			listaAttivita.setSiteId(user.getSiteId());
			listaAttivita.setIdAsl(user.getSiteId());
			listaAttivita.setStato(API_STATO_DA_NOTIFICARE);
			listaAttivita.buildList(db);

			context.getRequest().setAttribute("ListaAttivitaDaValidare",listaAttivita);




		} catch (Exception errorMessage) 
		{

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");

		return "listaDaValidareOK";


		// return ("InsertOK");

	}
	
	
	public String executeCommandCessazioniDaValidare(ActionContext context)throws SQLException 
	{
		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;

		Stabilimento stab =null;


		try {

			UserBean user = (UserBean)context.getSession().getAttribute("User");
			db = this.getConnection(context);

			LookupList tipoAttivitaApi = new LookupList(db,"apicoltura_lookup_tipo_attivita");
			tipoAttivitaApi.addItem(-1, "Seleziona Nazione");
			context.getRequest().setAttribute("TipoAttivitaApi", tipoAttivitaApi);


			LookupList apicolturaClassificazione = new LookupList(db,"apicoltura_lookup_classificazione");
			apicolturaClassificazione.addItem(-1, "Seleziona Classificazione");
			apicolturaClassificazione.setRequired(true);
			context.getRequest().setAttribute("ApicolturaClassificazione", apicolturaClassificazione);

			LookupList apicolturaModalita = new LookupList(db,"apicoltura_lookup_modalita");
			apicolturaModalita.addItem(-1, "Seleziona Modalita");
			apicolturaModalita.setRequired(true);
			context.getRequest().setAttribute("ApicolturaModalita", apicolturaModalita);

			LookupList apicolturaSottospecie = new LookupList(db,"apicoltura_lookup_sottospecie");
			apicolturaSottospecie.addItem(-1, "Seleziona Sottospecie");
			apicolturaSottospecie.setRequired(true);
			context.getRequest().setAttribute("ApicolturaSottospecie", apicolturaSottospecie);


			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");

			context.getRequest().setAttribute("SiteIdList", siteList);

			boolean exist = false;
			/**
			 * COSTRUZIONE DELLA LISTA DELLE NUOVE ATTIVITA DA NOTIFICARE ALL'ASL
			 * PER LE QUALI OCCORRE LA VALIDAZIONE
			 */
			OperatoreList listaAttivita = new OperatoreList();
			listaAttivita.setSiteId(user.getSiteId());
			listaAttivita.setIdAsl(user.getSiteId());
			listaAttivita.setStato(API_STATO_CESSATO_DA_SINCRONIZZARE);
			listaAttivita.buildList(db);

			context.getRequest().setAttribute("ListaAttivitaDaCessare",listaAttivita);




		} catch (Exception errorMessage) 
		{

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");

		return "listaDaCessareOK";


		// return ("InsertOK");

	}








	public String executeCommandSincronizzaApiari(ActionContext context)throws SQLException, IndirizzoNotFoundException {


		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;

		Stabilimento stab =null;


		try {

			UserBean user = (UserBean)context.getSession().getAttribute("User");
			db = this.getConnection(context);

			LookupList tipoAttivitaApi = new LookupList(db,"apicoltura_lookup_tipo_attivita");
			tipoAttivitaApi.addItem(-1, "Seleziona Nazione");
			context.getRequest().setAttribute("TipoAttivitaApi", tipoAttivitaApi);


			LookupList apicolturaClassificazione = new LookupList(db,"apicoltura_lookup_classificazione");
			apicolturaClassificazione.addItem(-1, "Seleziona Classificazione");
			apicolturaClassificazione.setRequired(true);
			context.getRequest().setAttribute("ApicolturaClassificazione", apicolturaClassificazione);

			LookupList apicolturaModalita = new LookupList(db,"apicoltura_lookup_modalita");
			apicolturaModalita.addItem(-1, "Seleziona Modalita");
			apicolturaModalita.setRequired(true);
			context.getRequest().setAttribute("ApicolturaModalita", apicolturaModalita);

			LookupList apicolturaSottospecie = new LookupList(db,"apicoltura_lookup_sottospecie");
			apicolturaSottospecie.addItem(-1, "Seleziona Sottospecie");
			apicolturaSottospecie.setRequired(true);
			context.getRequest().setAttribute("ApicolturaSottospecie", apicolturaSottospecie);


			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");

			context.getRequest().setAttribute("SiteIdList", siteList);

			boolean exist = false;
			/**
			 * COSTRUZIONE DELLA LISTA DELLE NUOVE ATTIVITA DA NOTIFICARE ALL'ASL
			 * PER LE QUALI OCCORRE LA VALIDAZIONE
			 */


			StabilimentoList st = new StabilimentoList();

			st.setIdAsl(user.getSiteId());
			st.setStato(API_STATO_VALIDATO);
			st.setStatoApiario(API_STATO_DA_NOTIFICARE);
			st.buildList(db);
			Iterator<Stabilimento>  itst = st.iterator();



			StringBuffer ko = new StringBuffer() ;
			StringBuffer ok = new StringBuffer() ;

			while (itst.hasNext())
			{
				Stabilimento apiario = itst.next();


				if (apiario.getOperatore().getIdBda()>0)
				{



					try
					{
						WsApiAnagraficaPersoneBdn servicePersone = new WsApiAnagraficaPersoneBdn();
						Persone detentore =  servicePersone.insertAnagraficaPersona(apiario.getDetentore(), db, getUserId(context));

						WsApiAnagraficaApi servizioBdn = new WsApiAnagraficaApi();
						Api apiarioInserito = servizioBdn.insertApiAnagraficaApiario(apiario, db,getUserId(context),context);
						
						//Gestione Autocensimento
						//Ricerca censimento
						WsApiAnagraficaVariazioniCen censimento = new WsApiAnagraficaVariazioniCen();
						int progressivoApiario  = Integer.parseInt(apiario.getProgressivoBDA());
						
						List<Apicen> censimenti = censimento.search(apiario.getOperatore().getCodiceAzienda(), progressivoApiario, apiario.getDataApertura(), "S",db);
						
						//Inserimento Censimento
						if(!censimenti.isEmpty())
						{
							apiario.setData_assegnazione_censimento(apiario.getDataApertura());
							apiario.nuovoCensimento(db, context, censimenti.get(0).getApicenId());
						}
						//Fine Gestione Autocensimento
						
						
						apiario.sincronizzaBdn(db, getUserId(context), apiarioInserito.getApiId());

						Timestamp timestamp = new Timestamp(System.currentTimeMillis());
						UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

						ok.append("CODICE AZIENDA :"+apiario.getOperatore().getCodiceAzienda()+" - PROGRESSIVO:"+apiario.getProgressivoBDA()+"\n");
					}
					catch (EccezioneDati e) 
					{
						System.out.println("SYNC- Errore nell'invio in BDN : "+e.getMessage());
						ko.append("CODICE AZIENDA :"+apiario.getOperatore().getCodiceAzienda()+" - PROGRESSIVO:"+apiario.getProgressivoBDA()+"\n");

						if (e.getMessage().contains("RECORD ESISTENTE"))
							apiario.sincronizzaBdn(db, getUserId(context), null);
						else
							apiario.errSincronizzaBdn(db, e.getMessage());
						context.getRequest().setAttribute("ErrorValidazioneError", e.getMessage());

					}
					catch(Exception e)
					{
						System.out.println("SYNC- Errore nell'invio in BDN : "+e.getMessage());
						ko.append("CODICE AZIENDA :"+apiario.getOperatore().getCodiceAzienda()+" - PROGRESSIVO:"+apiario.getProgressivoBDA()+"\n");

						String errore = "Errore Interno GISA : "+e.getMessage() + "";
						apiario.errSincronizzaBdn(db, errore);
					}
				} // END IF
			}

			try
			{
				if(!ok.toString().equals(""))
					sendMailIzsm(context.getRequest(), ok.toString(), "##GISA_SYNC_API_WS_BDN_OK##", "gisahelpdesk@usmail.it");

				if(!ko.toString().equals(""))
					sendMailIzsm(context.getRequest(), ko.toString(), "##GISA_SYNC_API_WS_BDN_KO##", "gisahelpdesk@usmail.it");
			}catch(Exception e2)
			{
				System.out.println("Errore nell'invio della mail");
			}

		} catch (SQLException errorMessage) 
		{

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");

		return executeCommandListaRichiestaApiari(context);


		// return ("InsertOK");

	}







	public String executeCommandListaRichiestaApiari(ActionContext context)throws SQLException {


		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;

		Stabilimento stab =null;


		try {

			UserBean user = (UserBean)context.getSession().getAttribute("User");
			db = this.getConnection(context);

			LookupList tipoAttivitaApi = new LookupList(db,"apicoltura_lookup_tipo_attivita");
			tipoAttivitaApi.addItem(-1, "Seleziona Nazione");
			context.getRequest().setAttribute("TipoAttivitaApi", tipoAttivitaApi);


			LookupList apicolturaClassificazione = new LookupList(db,"apicoltura_lookup_classificazione");
			apicolturaClassificazione.addItem(-1, "Seleziona Classificazione");
			apicolturaClassificazione.setRequired(true);
			context.getRequest().setAttribute("ApicolturaClassificazione", apicolturaClassificazione);

			LookupList apicolturaModalita = new LookupList(db,"apicoltura_lookup_modalita");
			apicolturaModalita.addItem(-1, "Seleziona Modalita");
			apicolturaModalita.setRequired(true);
			context.getRequest().setAttribute("ApicolturaModalita", apicolturaModalita);

			LookupList apicolturaSottospecie = new LookupList(db,"apicoltura_lookup_sottospecie");
			apicolturaSottospecie.addItem(-1, "Seleziona Sottospecie");
			apicolturaSottospecie.setRequired(true);
			context.getRequest().setAttribute("ApicolturaSottospecie", apicolturaSottospecie);


			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");

			context.getRequest().setAttribute("SiteIdList", siteList);

			boolean exist = false;
			/**
			 * COSTRUZIONE DELLA LISTA DELLE NUOVE ATTIVITA DA NOTIFICARE ALL'ASL
			 * PER LE QUALI OCCORRE LA VALIDAZIONE
			 */
			StabilimentoList st = new StabilimentoList();

			st.setIdAsl(user.getSiteId());
			st.setStato(API_STATO_VALIDATO);
			st.setStatoApiario(API_STATO_DA_NOTIFICARE);
			st.setFlagLaboratorio(false);
			st.buildList(db);

			context.getRequest().setAttribute("ListaAttivitaDaValidare",st);




		} catch (Exception errorMessage) 
		{

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");

		return "listaApiariDaValidareOK";


		// return ("InsertOK");

	}

























	public String executeCommandListaNotifiche(ActionContext context)throws SQLException {


		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;

		Stabilimento stab =null;


		try {

			UserBean user = (UserBean)context.getSession().getAttribute("User");
			db = this.getConnection(context);

			LookupList apicolturaClassificazione = new LookupList(db,"apicoltura_lookup_classificazione");
			apicolturaClassificazione.addItem(-1, "Seleziona Classificazione");
			apicolturaClassificazione.setRequired(true);
			context.getRequest().setAttribute("ApicolturaClassificazione", apicolturaClassificazione);

			LookupList apicolturaModalita = new LookupList(db,"apicoltura_lookup_modalita");
			apicolturaModalita.addItem(-1, "Seleziona Modalita");
			apicolturaModalita.setRequired(true);
			context.getRequest().setAttribute("ApicolturaModalita", apicolturaModalita);

			LookupList apicolturaSottospecie = new LookupList(db,"apicoltura_lookup_sottospecie");
			apicolturaSottospecie.addItem(-1, "Seleziona Sottospecie");
			apicolturaSottospecie.setRequired(true);
			context.getRequest().setAttribute("ApicolturaSottospecie", apicolturaSottospecie);


			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1,  "-- SELEZIONA VOCE --");
			siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");

			context.getRequest().setAttribute("SiteIdList", siteList);

			boolean exist = false;
			/**
			 * COSTRUZIONE DELLA LISTA DELLE NUOVE ATTIVITA DA NOTIFICARE ALL'ASL
			 * PER LE QUALI OCCORRE LA VALIDAZIONE
			 */


			/**
			 * COSTRUZIONE DELLA LISTA DEI NUOVI APIARI REGISTRATI
			 * 
			 */
			StabilimentoList listaNuoviApiari = new StabilimentoList();
			listaNuoviApiari.setStatoApiario(API_STATO_DA_NOTIFICARE);
			listaNuoviApiari.setIdAsl(user.getSiteId());
			listaNuoviApiari.buildList(db);

			context.getRequest().setAttribute("ListaNuoviApiari",listaNuoviApiari);


			/**
			 * COSTRUZIONE DELLA LISTA VARIAZIONI UBICAZIONI APIARI
			 * 
			 */
			StabilimentoList listaVariazioniUbicazioni = new StabilimentoList();
			listaVariazioniUbicazioni.setIdAsl(user.getSiteId());
			listaVariazioniUbicazioni.setFlag_notifica_variazione_ubicazione(false);
			listaVariazioniUbicazioni.buildList(db);

			context.getRequest().setAttribute("VariazioniUbicazioni",listaVariazioniUbicazioni);

			/**
			 * COSTRUZIONE DELLA LISTA VARIAZIONI CENSIMENTI
			 * 
			 */
			StabilimentoList listaVariazioniCensimento = new StabilimentoList();
			listaVariazioniCensimento.setIdAsl(user.getSiteId());
			listaVariazioniCensimento.setFlag_notifica_variazione_censimento(false);
			listaVariazioniCensimento.buildList(db);

			context.getRequest().setAttribute("VariazioniCensimento",listaVariazioniCensimento);


			/**
			 * COSTRUZIONE DELLA LISTA VARIAZIONI DETENTORE
			 * 
			 */
			StabilimentoList listaVariazioniDetentore = new StabilimentoList();
			listaVariazioniDetentore.setIdAsl(user.getSiteId());
			listaVariazioniDetentore.setFlag_notifica_variazione_detentore(false);
			listaVariazioniDetentore.buildList(db);

			context.getRequest().setAttribute("VariazioniDetentore",listaVariazioniDetentore);






		} catch (Exception errorMessage) 
		{

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");

		return "listaNotificheOK";


		// return ("InsertOK");

	}

	public String executeCommandStampaCensimento(ActionContext context)throws SQLException {


		Connection db = null;
	
		int id = -1;
		
		String idString = context.getRequest().getParameter("id");
		if (idString == null)
			idString = (String) context.getRequest().getAttribute("id");
		try { id = Integer.parseInt(idString); } catch (Exception e){}
		
		try {

			db = this.getConnection(context);
			StabilimentoVariazioneCensimento cens = new StabilimentoVariazioneCensimento(db, id);
			Stabilimento apiario = new Stabilimento(db, cens.getIdApicoltoreApiario());
			
			context.getRequest().setAttribute("Censimento", cens);
			context.getRequest().setAttribute("Apiario", apiario);
			
			LookupList lookupStati = new LookupList(db,"lookup_apicoltura_stati_apiario");
			context.getRequest().setAttribute("LookupStati", lookupStati);

			LookupList aslList = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("AslList", aslList);

			LookupList tipoAttivitaApi = new LookupList(db,"apicoltura_lookup_tipo_attivita");
			context.getRequest().setAttribute("TipoAttivitaApi", tipoAttivitaApi);

			LookupList apicolturaClassificazione = new LookupList(db,"apicoltura_lookup_classificazione");
			context.getRequest().setAttribute("ApicolturaClassificazione", apicolturaClassificazione);

			LookupList apicolturaModalita = new LookupList(db,"apicoltura_lookup_modalita");
			context.getRequest().setAttribute("ApicolturaModalita", apicolturaModalita);

			LookupList apicolturaSottospecie = new LookupList(db,"apicoltura_lookup_sottospecie");
			context.getRequest().setAttribute("ApicolturaSottospecie", apicolturaSottospecie);

		} catch (Exception errorMessage) 
		{	errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return "StampaCensimentoOK";
	}

	public String executeCommandStampaMovimentazione(ActionContext context)throws SQLException {

		Connection db = null;
	
		int id = -1;
		
		String idString = context.getRequest().getParameter("id");
		if (idString == null)
			idString = (String) context.getRequest().getAttribute("id");
		try { id = Integer.parseInt(idString); } catch (Exception e){}
		
		try {

			db = this.getConnection(context);
			ModelloC movimentazione = new ModelloC(db, id);
			
			Stabilimento apiario = null;
			if(movimentazione.getIdTipoMovimentazione()!=4)
			{
				apiario = new Stabilimento(db, movimentazione.getIdStabilimentoOrigine());
			}
			else
			{
				StabilimentoList listStab = new StabilimentoList();
				listStab.setCun(movimentazione.getCodiceAziendaOrigine());
				listStab.buildList(db);
				apiario = (Stabilimento)listStab.get(0);
			}
			
			context.getRequest().setAttribute("Movimentazione", movimentazione);
			context.getRequest().setAttribute("Apiario", apiario);
			
			LookupList LookupTipoMovimentazione = new LookupList(db,"lookup_apicoltura_tipo_movimentazione");
			context.getRequest().setAttribute("LookupTipoMovimentazione", LookupTipoMovimentazione);
			
			LookupList lookupStati = new LookupList(db,"lookup_apicoltura_stati_apiario");
			context.getRequest().setAttribute("LookupStati", lookupStati);

			LookupList aslList = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("AslList", aslList);

			LookupList tipoAttivitaApi = new LookupList(db,"apicoltura_lookup_tipo_attivita");
			context.getRequest().setAttribute("TipoAttivitaApi", tipoAttivitaApi);

			LookupList apicolturaClassificazione = new LookupList(db,"apicoltura_lookup_classificazione");
			context.getRequest().setAttribute("ApicolturaClassificazione", apicolturaClassificazione);

			LookupList apicolturaModalita = new LookupList(db,"apicoltura_lookup_modalita");
			context.getRequest().setAttribute("ApicolturaModalita", apicolturaModalita);

			LookupList apicolturaSottospecie = new LookupList(db,"apicoltura_lookup_sottospecie");
			context.getRequest().setAttribute("ApicolturaSottospecie", apicolturaSottospecie);

		} catch (Exception errorMessage) 
		{	errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return "StampaMovimentazioneOK";
	}
	
	
	public String executeCommandStampaAllegatoC(ActionContext context)throws SQLException 
	{
		Connection db = null;
		int id = -1;
		
		String idString = context.getRequest().getParameter("id");
		if (idString == null)
			idString = (String) context.getRequest().getAttribute("id");
		try 
		{ 
			id = Integer.parseInt(idString); 
		} 
		catch (Exception e)
		{
		}
		
		try 
		{

			db = this.getConnection(context);
			ModelloC movimentazione = new ModelloC(db, id);
			Stabilimento apiario = new Stabilimento(db, movimentazione.getIdStabilimentoOrigine());
			Stabilimento apiarioDestinazione = null;
			Operatore operatoreDestinazione = null;
			if(movimentazione.getIdStabilimentoDestinazione()>0)
				apiarioDestinazione = new Stabilimento(db, movimentazione.getIdStabilimentoDestinazione());
			else
				operatoreDestinazione = new Operatore(db, movimentazione.getCodiceAziendaDestinazione(), null, null, null, null);
			
			context.getRequest().setAttribute("Movimentazione", movimentazione);
			context.getRequest().setAttribute("Apiario", apiario);
			context.getRequest().setAttribute("ApiarioDestinazione", apiarioDestinazione);
			context.getRequest().setAttribute("OperatoreDestinazione", operatoreDestinazione);
			
			LookupList LookupTipoMovimentazione = new LookupList(db,"lookup_apicoltura_tipo_movimentazione");
			context.getRequest().setAttribute("LookupTipoMovimentazione", LookupTipoMovimentazione);
			
			LookupList lookupStati = new LookupList(db,"lookup_apicoltura_stati_apiario");
			context.getRequest().setAttribute("LookupStati", lookupStati);

			LookupList ProvinceList = new LookupList(db,"lookup_province");
			context.getRequest().setAttribute("ProvinceList", ProvinceList);
			
			org.aspcfs.modules.accounts.base.Comuni c = new org.aspcfs.modules.accounts.base.Comuni();
			ArrayList<org.aspcfs.modules.accounts.base.Comuni> listaComuni = c.buildList(db, -1);
		    LookupList comuniList = new LookupList(listaComuni);
		    context.getRequest().setAttribute("ComuniList", comuniList);
			
			LookupList aslList = new LookupList(db, "lookup_site_id");
			context.getRequest().setAttribute("AslList", aslList);

			LookupList tipoAttivitaApi = new LookupList(db,"apicoltura_lookup_tipo_attivita");
			context.getRequest().setAttribute("TipoAttivitaApi", tipoAttivitaApi);

			LookupList apicolturaClassificazione = new LookupList(db,"apicoltura_lookup_classificazione");
			context.getRequest().setAttribute("ApicolturaClassificazione", apicolturaClassificazione);

			LookupList apicolturaModalita = new LookupList(db,"apicoltura_lookup_modalita");
			context.getRequest().setAttribute("ApicolturaModalita", apicolturaModalita);

			LookupList apicolturaSottospecie = new LookupList(db,"apicoltura_lookup_sottospecie");
			context.getRequest().setAttribute("ApicolturaSottospecie", apicolturaSottospecie);

		} 
		catch (Exception errorMessage) 
		{	
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} 
		finally 
		{
			this.freeConnection(context, db);
		}

		return "StampaAllegatoC_OK";
	}
	
	
	public String executeCommandDeleteCensimento(ActionContext context)throws SQLException {


		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;



		try {

			db = this.getConnection(context);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			org.aspcfs.modules.accounts.base.ComuniAnagrafica c = new org.aspcfs.modules.accounts.base.ComuniAnagrafica();
			ArrayList<org.aspcfs.modules.accounts.base.ComuniAnagrafica> listaComuni = c.buildList_all(db,((UserBean) context.getSession().getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList(listaComuni,-1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);


			LookupList lookupStati = new LookupList(db,"lookup_apicoltura_stati_apiario");
			context.getRequest().setAttribute("LookupStati", lookupStati);


			LookupList aslList = new LookupList(db, "lookup_site_id");
			aslList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", aslList);

			LookupList NazioniList = new LookupList(db,"lookup_nazioni");
			NazioniList.addItem(-1, "Seleziona Nazione");
			NazioniList.setRequired(true);
			context.getRequest().setAttribute("NazioniList", NazioniList);


			LookupList tipoAttivitaApi = new LookupList(db,"apicoltura_lookup_tipo_attivita");
			tipoAttivitaApi.addItem(-1, "Seleziona Tipo Attivita");
			context.getRequest().setAttribute("TipoAttivitaApi", tipoAttivitaApi);

			LookupList apicolturaClassificazione = new LookupList(db,"apicoltura_lookup_classificazione");
			apicolturaClassificazione.addItem(-1, "Seleziona Classificazione");
			apicolturaClassificazione.setRequired(true);
			context.getRequest().setAttribute("ApicolturaClassificazione", apicolturaClassificazione);

			LookupList apicolturaModalita = new LookupList(db,"apicoltura_lookup_modalita");
			apicolturaModalita.addItem(-1, "Seleziona Modalita");
			apicolturaModalita.setRequired(true);
			context.getRequest().setAttribute("ApicolturaModalita", apicolturaModalita);

			LookupList apicolturaSottospecie = new LookupList(db,"apicoltura_lookup_sottospecie");
			apicolturaSottospecie.addItem(-1, "Seleziona Sottospecie");
			apicolturaSottospecie.setRequired(true);
			context.getRequest().setAttribute("ApicolturaSottospecie", apicolturaSottospecie);
			boolean exist = false;

			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			int idStabilimento = Integer.parseInt(context.getParameter("idStabilimento"));
			int idCensimento = Integer.parseInt(context.getParameter("idCensimento"));

			Stabilimento stab = new Stabilimento(db, idStabilimento);
			StabilimentoVariazioneCensimento censimento = new StabilimentoVariazioneCensimento(db, idCensimento);
			censimento.setNote(context.getParameter("note"));
			
			
			WsApiAnagraficaVariazioniCen serviceCen = new WsApiAnagraficaVariazioniCen();
			String errore = serviceCen.deleteApiAnagraficaCensimento(censimento, db, user.getUserId());
			
			if(errore.equalsIgnoreCase("OK"))
			{
				stab.deleteCensimento(db,  context, censimento);
				StabilimentoVariazioneCensimento ultimoCen = new StabilimentoVariazioneCensimento(db,stab.getIdUltimoCensimento(db,stab,idCensimento));
				stab.setData_assegnazione_censimento(ultimoCen.getDataAssegnazioneCensimento()); 
				stab.setNumAlveari(ultimoCen.getNumAlveari()); 
				stab.setNumPacchi(ultimoCen.getNumPacchi());
				stab.setNumRegine(ultimoCen.getNumRegine());
				stab.setNumSciami(ultimoCen.getNumSciami());
				stab.setIdVariazioneEseguita(ultimoCen.getId());
				stab.aggiornaDatiCensimenti(db);
				RicercheAnagraficheTab.inserApi(db, stab.getIdStabilimento());
				
				Operatore op = new Operatore();
				op.queryRecordOperatore(db, stab.getIdOperatore());
				//newStabilimento.get
				
				long dataMilli = System.currentTimeMillis(); 
				java.util.Date data = new Date( dataMilli );
				SimpleDateFormat forma=new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
				String dataOdierna=forma.format(data);
				context.getRequest().setAttribute("dataOdierna", dataOdierna);
				context.getRequest().setAttribute("OperatoreDettagli", op);
				context.getRequest().setAttribute("idStab", stab.getIdStabilimento());
				context.getRequest().setAttribute("opId", stab.getIdOperatore());
			}
			else
			{
				context.getRequest().setAttribute("Error", errore);
				return ("SystemError");
			}
			
			OperatoreAction.abilitazioneTastoCensimenti(context.getRequest());

		} catch (Exception errorMessage) 
		{

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");

		return "InsertVariazioneOK";
	}
	
	
	
	public String executeCommandCessazioneApiario(ActionContext context)throws SQLException {


		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;



		try {

			db = this.getConnection(context);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			org.aspcfs.modules.accounts.base.ComuniAnagrafica c = new org.aspcfs.modules.accounts.base.ComuniAnagrafica();
			ArrayList<org.aspcfs.modules.accounts.base.ComuniAnagrafica> listaComuni = c.buildList_all(db,((UserBean) context.getSession().getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList(listaComuni,-1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);


			LookupList lookupStati = new LookupList(db,"lookup_apicoltura_stati_apiario");
			context.getRequest().setAttribute("LookupStati", lookupStati);


			LookupList aslList = new LookupList(db, "lookup_site_id");
			aslList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", aslList);

			LookupList NazioniList = new LookupList(db,"lookup_nazioni");
			NazioniList.addItem(-1, "Seleziona Nazione");
			NazioniList.setRequired(true);
			context.getRequest().setAttribute("NazioniList", NazioniList);


			LookupList tipoAttivitaApi = new LookupList(db,"apicoltura_lookup_tipo_attivita");
			tipoAttivitaApi.addItem(-1, "Seleziona Tipo Attivita");
			context.getRequest().setAttribute("TipoAttivitaApi", tipoAttivitaApi);

			LookupList apicolturaClassificazione = new LookupList(db,"apicoltura_lookup_classificazione");
			apicolturaClassificazione.addItem(-1, "Seleziona Classificazione");
			apicolturaClassificazione.setRequired(true);
			context.getRequest().setAttribute("ApicolturaClassificazione", apicolturaClassificazione);

			LookupList apicolturaModalita = new LookupList(db,"apicoltura_lookup_modalita");
			apicolturaModalita.addItem(-1, "Seleziona Modalita");
			apicolturaModalita.setRequired(true);
			context.getRequest().setAttribute("ApicolturaModalita", apicolturaModalita);

			LookupList apicolturaSottospecie = new LookupList(db,"apicoltura_lookup_sottospecie");
			apicolturaSottospecie.addItem(-1, "Seleziona Sottospecie");
			apicolturaSottospecie.setRequired(true);
			context.getRequest().setAttribute("ApicolturaSottospecie", apicolturaSottospecie);
			boolean exist = false;

			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			int idStabilimento = Integer.parseInt(context.getParameter("idStabilimento"));

			Stabilimento stab = new Stabilimento(db, idStabilimento);
			stab.setDataChiusura(context.getParameter("data_cessazione_apiario"));
			
			WsApiAnagraficaApi serviceApiari = new WsApiAnagraficaApi();
			String ritorno = serviceApiari.cessazioneApiAnagraficaApiario(stab, db, user.getUserId());
			if(!ritorno.equalsIgnoreCase("OK"))
			{
				context.getRequest().setAttribute("Error", ritorno);
				return ("SystemError");
			}
			
			RicercheAnagraficheTab.inserApi(db, stab.getIdStabilimento());
			
			Operatore op = new Operatore();
			op.queryRecordOperatore(db, stab.getIdOperatore());
			//newStabilimento.get
			context.getRequest().setAttribute("OperatoreDettagli", op);
			context.getRequest().setAttribute("idStab", stab.getIdStabilimento());
			context.getRequest().setAttribute("opId", stab.getIdOperatore());
			
			OperatoreAction.abilitazioneTastoCensimenti(context.getRequest());

		} catch (Exception errorMessage) 
		{

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} 
		finally 
		{
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");

		return "InsertVariazioneOK";
	}
	
	
	public String executeCommandModificaCensimento(ActionContext context)throws SQLException {


		Connection db = null;
		boolean recordInserted = false;
		boolean isValid = false;



		try {

			db = this.getConnection(context);

			LookupList siteList = new LookupList(db, "lookup_site_id");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);

			org.aspcfs.modules.accounts.base.ComuniAnagrafica c = new org.aspcfs.modules.accounts.base.ComuniAnagrafica();
			ArrayList<org.aspcfs.modules.accounts.base.ComuniAnagrafica> listaComuni = c.buildList_all(db,((UserBean) context.getSession().getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList(listaComuni,-1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);


			LookupList lookupStati = new LookupList(db,"lookup_apicoltura_stati_apiario");
			context.getRequest().setAttribute("LookupStati", lookupStati);


			LookupList aslList = new LookupList(db, "lookup_site_id");
			aslList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", aslList);

			LookupList NazioniList = new LookupList(db,"lookup_nazioni");
			NazioniList.addItem(-1, "Seleziona Nazione");
			NazioniList.setRequired(true);
			context.getRequest().setAttribute("NazioniList", NazioniList);


			LookupList tipoAttivitaApi = new LookupList(db,"apicoltura_lookup_tipo_attivita");
			tipoAttivitaApi.addItem(-1, "Seleziona Tipo Attivita");
			context.getRequest().setAttribute("TipoAttivitaApi", tipoAttivitaApi);

			LookupList apicolturaClassificazione = new LookupList(db,"apicoltura_lookup_classificazione");
			apicolturaClassificazione.addItem(-1, "Seleziona Classificazione");
			apicolturaClassificazione.setRequired(true);
			context.getRequest().setAttribute("ApicolturaClassificazione", apicolturaClassificazione);

			LookupList apicolturaModalita = new LookupList(db,"apicoltura_lookup_modalita");
			apicolturaModalita.addItem(-1, "Seleziona Modalita");
			apicolturaModalita.setRequired(true);
			context.getRequest().setAttribute("ApicolturaModalita", apicolturaModalita);

			LookupList apicolturaSottospecie = new LookupList(db,"apicoltura_lookup_sottospecie");
			apicolturaSottospecie.addItem(-1, "Seleziona Sottospecie");
			apicolturaSottospecie.setRequired(true);
			context.getRequest().setAttribute("ApicolturaSottospecie", apicolturaSottospecie);
			boolean exist = false;

			UserBean user = (UserBean) context.getRequest().getSession().getAttribute("User");
			int idStabilimento = Integer.parseInt(context.getParameter("idStabilimento"));
			int idCensimento = Integer.parseInt(context.getParameter("idCensimento"));

			Stabilimento stab = new Stabilimento(db, idStabilimento);
			StabilimentoVariazioneCensimento censimento = new StabilimentoVariazioneCensimento(db, idCensimento);
			censimento.setNumAlveari(Integer.parseInt(context.getParameter("numAlveari")));
			censimento.setNumSciami(Integer.parseInt(context.getParameter("numSciami")));
			censimento.setNumRegine(Integer.parseInt(context.getParameter("numRegine")));
			censimento.setNumPacchi(Integer.parseInt(context.getParameter("numPacchi")));

			stab.setNumAlveari(context.getParameter("numAlveari"));
			stab.setNumSciami(context.getParameter("numSciami"));
			stab.setNumPacchi(context.getParameter("numPacchi"));
			stab.setNumRegine(context.getParameter("numRegine"));
			stab.setData_assegnazione_censimento(context.getParameter("data_assegnazione_censimento"));
			stab.modificaCensimento(db,  context, censimento);

			
			
			RicercheAnagraficheTab.inserApi(db, stab.getIdStabilimento());

			
			Operatore op = new Operatore();
			op.queryRecordOperatore(db, stab.getIdOperatore());
			//newStabilimento.get
			context.getRequest().setAttribute("OperatoreDettagli", op);
			context.getRequest().setAttribute("idStab", stab.getIdStabilimento());
			context.getRequest().setAttribute("opId", stab.getIdOperatore());


			WsApiAnagraficaVariazioniCen serviceCen = new WsApiAnagraficaVariazioniCen();
			serviceCen.updateApiAnagraficaCensimento(stab, db, user.getUserId());
			
			OperatoreAction.abilitazioneTastoCensimenti(context.getRequest());

		} catch (Exception errorMessage) 
		{

			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "View Accounts", "Accounts Insert ok");

		

		return "InsertVariazioneOK";
	}
	
	private String validaAziendaEApiari(ActionContext context,String codiceAzienda) throws Exception 
	{
		System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] INIZIO  validaAziendaEApiari" + ""); 
		Connection db = null;
		try 
		{
			db = this.getConnection(context);
	
			if (db.getAutoCommit() == true)
				db.setAutoCommit(false);
	
			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
			
			int idOperatore = Integer.parseInt(context.getParameter("opId"));
			
			System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] validaAziendaEApiari. idOperatore" + ""); 
	
			Operatore op = new Operatore();
			op.queryRecordOperatore(db, idOperatore);
			
			System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] validaAziendaEApiari. queryRecordOperatore fatta " + ""); 
	
			/*int progressivo = 1;
			int istat = -1;
			String siglProv = "";
	
			String sel = "select max(progressivo)+1,sigla,istat from apicoltura_imprese_progressivi_comuni where id_comune =? group by sigla,istat ";
			PreparedStatement pst = db.prepareStatement(sel);
			pst.setInt(1, op.getSedeLegale().getComune());
			ResultSet rs = pst.executeQuery();
			if (rs.next()) 
			{
				progressivo = rs.getInt(1);
				istat = rs.getInt("istat");
				siglProv = rs.getString("sigla");
			}
	
			String istatasString = org.aspcfs.utils.StringUtils.zeroPad(istat, 3);
			String progressivoPadding = org.aspcfs.utils.StringUtils.zeroPad(progressivo, 3);
	
			codiceAzienda = "" + istatasString + siglProv + progressivoPadding;*/
	
			op.setCodiceAzienda(codiceAzienda);
			
			System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] validaAziendaEApiari. setCodiceAzienda " + "");
	
			WsApiAnagraficaPersoneBdn servicePersone = new WsApiAnagraficaPersoneBdn();
			try 
			{
				System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] validaAziendaEApiari. searchPersone inizio " + "");
				Persone proprietario = servicePersone.searchPersone(op.getRappLegale(), db, getUserId(context));
				System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] validaAziendaEApiari. searchPersone fine " + "");
				if (proprietario == null)
					proprietario = servicePersone.insertAnagraficaPersona(op.getRappLegale(), db, getUserId(context));
	
				System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] INIZIO  validaAziendaEApiari. insertAnagraficaPersona fine " + "");
				proprietario.getPersoneId();
				System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] INIZIO  validaAziendaEApiari. proprietario.getPersonaId fine " + "");
				PreparedStatement pst = db.prepareStatement("update opu_soggetto_fisico set id_bdn = ? where id = ? ");
				pst.setInt(1, proprietario.getPersoneId());
				pst.setInt(2, op.getRappLegale().getIdSoggetto());
				pst.execute();
				
				System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] INIZIO  validaAziendaEApiari. fatta query opu_soggetto_fisico" + "");
	
				WsApiAnagraficaAziende serviceziende = new WsApiAnagraficaAziende();
				Apiazienda apiAzienda = serviceziende.insert(op, db, getUserId(context));
				
				System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] INIZIO  validaAziendaEApiari. fatta serviceziende.insert" + "");
	
				WsApiAnagraficaAttivita serviceAttivitia = new WsApiAnagraficaAttivita();
				Apiatt attivitaApi = serviceAttivitia.insertApiAnagraficaattivita(op, db, getUserId(context));
				
				System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] INIZIO  validaAziendaEApiari. fatta insertApiAnagraficaattivita" + "");
	
				pst = db.prepareStatement("update apicoltura_imprese set id_bda=?, id_bdn_azienda = ?,id_bdn_attivita=? where id = ? ");
				pst.setInt(1, attivitaApi.getApiattId());
				pst.setInt(2, apiAzienda.getAziendaId());
				pst.setInt(3, attivitaApi.getApiattId());
				pst.setInt(4, op.getIdOperatore());
				pst.execute();
				
				System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] INIZIO  validaAziendaEApiari. fatta update apicoltura_imprese" + "");
	
				op.sincronizzaBdn(db, getUserId(context));
				
				System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] INIZIO  validaAziendaEApiari. fatta sincronizzaBdn" + "");
	
				String update = "update apicoltura_imprese set codice_azienda_regionale = ?,stato=?,codice_azienda=?  where id = ?; ";
				pst = db.prepareStatement(update);
				pst.setString(1, codiceAzienda);
				pst.setInt(2, StabilimentoAction.API_STATO_VALIDATO);
				pst.setString(3, codiceAzienda);
	
				pst.setInt(4, idOperatore);
	
				pst.execute();
				
				System.out.println("[DIAGNOSTICA APIARI CHE SI PERDONO] INIZIO  validaAziendaEApiari. fatta update apicoltura_imprese 2 volta" + "");
	
				op.setCodiceAzienda(codiceAzienda);
	
				if (db.getAutoCommit() == false) 
					db.commit();
			} 
			catch (Exception e) 
			{
				logger.error("Errore nell'invio in BDN : " + e.getMessage());
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	
				String msg = ApiUtil.getInfoRichiesta(op, "KO", e.getMessage(), timestamp, thisUser, new LookupList(db, "lookup_site_id"),  new LookupList(db, "lookup_apicoltura_stati_apiario"),  new LookupList(db, "apicoltura_lookup_tipo_attivita"), new LookupList(db, "apicoltura_lookup_classificazione"), new LookupList(db, "apicoltura_lookup_sottospecie"), new LookupList(db, "apicoltura_lookup_modalita"));				
				
				if (db.getAutoCommit() == false) 
				{
					db.rollback();
					db.setAutoCommit(true);
				}
				
				String errore = e.getMessage();
	
				try 
				{
					sendMailIzsm(context.getRequest(), msg + ". L'errore e il seguente :" + errore, "##GISA_API_WS_BDN_KO##", "gisahelpdesk@usmail.it");
				} 
				catch (Exception e2) 
				{
					logger.error("Errore nell'invio della mail");
				}
				return errore;
			}
			
			StabilimentoList listaApiari = op.getListaStabilimenti();
			Iterator<Stabilimento> itListaApiari = listaApiari.iterator();
			
			while (itListaApiari.hasNext()) 
			{
				Stabilimento thisApiario = itListaApiari.next();
	
				if (!thisApiario.isFlagLaboratorio()) 
				{
					try 
					{
						servicePersone.insertAnagraficaPersona(thisApiario.getDetentore(), db, getUserId(context));
						thisApiario.getOperatore().setCodiceAzienda(op.getCodiceAzienda());
	
						WsApiAnagraficaApi serviceApi = new WsApiAnagraficaApi();
						Api apiarioInserito = serviceApi.insertApiAnagraficaApiario(thisApiario, db, getUserId(context),context);
	
						//Gestione Autocensimento
						//Ricerca censimento
						WsApiAnagraficaVariazioniCen censimento = new WsApiAnagraficaVariazioniCen();
						int progressivoApiario  = Integer.parseInt(thisApiario.getProgressivoBDA());
						
						List<Apicen> censimenti = censimento.search(op.getCodiceAzienda(), progressivoApiario, thisApiario.getDataApertura(), "S",db);
						
						//Inserimento Censimento
						if(!censimenti.isEmpty())
						{
							thisApiario.setData_assegnazione_censimento(thisApiario.getDataApertura());
							thisApiario.nuovoCensimento(db, context, censimenti.get(0).getApicenId());
						}
						//Fine Gestione Autocensimento
						
						thisApiario.sincronizzaBdn(db, getUserId(context), apiarioInserito.getApiId());
	
						if (db.getAutoCommit() == false) 
						{
							db.commit();
						}
	
					} 
					catch (EccezioneDati e) 
					{
						logger.error("Errore nell'invio in BDN : " + e.getMessage());
						Timestamp timestamp = new Timestamp(System.currentTimeMillis());
						
						String msg = ApiUtil.getInfoRichiesta(op, "KO", e.getMessage(), timestamp, thisUser, new LookupList(db, "lookup_site_id"), new LookupList(db, "lookup_apicoltura_stati_apiario"),  new LookupList(db, "apicoltura_lookup_tipo_attivita"), new LookupList(db, "apicoltura_lookup_classificazione"), new LookupList(db, "apicoltura_lookup_sottospecie"), new LookupList(db, "apicoltura_lookup_modalita"));				
	
						if (db.getAutoCommit() == false) 
						{
							db.rollback();
							db.setAutoCommit(true);
						}
						if (e.getMessage().contains("RECORD ESISTENTE")) 
						{
							thisApiario.sincronizzaBdn(db, getUserId(context), null);

							if (db.getAutoCommit() == false) 
							{
								db.commit();
							}
						} 
						else 
						{
							thisApiario.errSincronizzaBdn(db, e.getMessage());
							if (db.getAutoCommit() == false) 
								db.commit();
						}
	
						try 
						{
							sendMailIzsm(context.getRequest(), msg + ". L'errore e il seguente :" + e.getMessage(),"##GISA_API_WS_BDN_KO##", "gisahelpdesk@usmail.it");
						} 
						catch (Exception e2) 
						{
							logger.error("Errore nell'invio della mail");
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
						System.out.println("SYNC- Errore nell'invio in BDN : "+e.getMessage());
						String errore = "Errore Interno GISA : "+e.getMessage() + "";
						thisApiario.errSincronizzaBdn(db, errore);
					}
				}
	
				if (db.getAutoCommit() == false) 
				{
					db.setAutoCommit(true);
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					String msg = ApiUtil.getInfoRichiesta(op, "OK", null, timestamp, thisUser, new LookupList(db, "lookup_site_id"),  new LookupList(db, "lookup_apicoltura_stati_apiario"),  new LookupList(db, "apicoltura_lookup_tipo_attivita"), new LookupList(db, "apicoltura_lookup_classificazione"), new LookupList(db, "apicoltura_lookup_sottospecie"), new LookupList(db, "apicoltura_lookup_modalita"));				
	
					sendMailIzsm(context.getRequest(), msg, "##GISA_API_WS_BDN_OK##", "gisahelpdesk@usmail.it");
				}
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			this.freeConnection(context, db);
		}
		return null;
	}
}
