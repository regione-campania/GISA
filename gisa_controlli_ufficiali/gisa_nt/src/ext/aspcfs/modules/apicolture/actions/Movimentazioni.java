/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package ext.aspcfs.modules.apicolture.actions;

import it.izs.apicoltura.apianagraficaattivita.ws.Apiatt;
import it.izs.apicoltura.apianagraficaattivita.ws.WsApiAnagraficaApi;
import it.izs.apicoltura.apianagraficaattivita.ws.WsApiAnagraficaAttivita;
import it.izs.apicoltura.apimovimentazione.ws.Apidetmod;
import it.izs.apicoltura.apimovimentazione.ws.Apimodmov;
import it.izs.apicoltura.apimovimentazione.ws.WsApiAnagraficaModello;
import it.izs.apicoltura.apimovimentazione.ws.Apimodmov.ListaDettaglioModello;
import it.izs.bdn.action.utilsXML;
import it.izs.ws.WsPost;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.derby.iapi.services.classfile.CONSTANT_Index_info;
import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspcfs.modules.accounts.base.Comuni;
import org.aspcfs.modules.accounts.base.Provincia;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.gestoriacquenew.base.AnagraficaGestore;
import org.aspcfs.modules.gestoriacquenew.base.ControlloInterno;
import org.aspcfs.modules.gestoriacquenew.base.EccezioneDati;
import org.aspcfs.modules.gestoriacquenew.base.ExcelParserGestoriAcque;
import org.aspcfs.modules.gestoriacquenew.base.GestoreAcque;
import org.aspcfs.modules.gestoriacquenew.base.GestoreNotFoundException;
import org.aspcfs.modules.gestoriacquenew.base.PuntoPrelievo;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.opu.base.IndirizzoNotFoundException;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.Utility;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;
import org.json.JSONObject;

import com.darkhorseventures.framework.actions.ActionContext;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
import com.itextpdf.text.log.SysoLogger;
import com.sun.xml.internal.ws.fault.ServerSOAPFaultException;
import com.thoughtworks.xstream.XStream;

import ext.aspcfs.modules.apiari.base.Indirizzo;
import ext.aspcfs.modules.apiari.base.ModelloC;
import ext.aspcfs.modules.apiari.base.MovimentazioniList;
import ext.aspcfs.modules.apiari.base.Operatore;
import ext.aspcfs.modules.apiari.base.OperatoreFuoriRegione;
import ext.aspcfs.modules.apiari.base.OperatoreList;
import ext.aspcfs.modules.apiari.base.SoggettoFisico;
import ext.aspcfs.modules.apiari.base.Stabilimento;
import ext.aspcfs.modules.apiari.base.StabilimentoList;
import ext.aspcfs.modules.apiari.base.StoricoImport;

public class Movimentazioni extends CFSModule{
	
	public static final int TIPO_MOVIMENTAZIONE_COMPAVENDITA_ACQUISTO = 1 ;
	public static final int TIPO_MOVIMENTAZIONE_COMPAVENDITA_VENDITA	= 2 ;
	public static final int TIPO_MOVIMENTAZIONE_NOMADISMO	= 3 ;
	
	
	public static  final int API_STATO_DA_NOTIFICARE = 1 ;
	public static  final int API_STATO_VALIDATO = 2 ;
	public static  final int API_STATO_VALIDAZIONE_NON_RICHIESTA = 3 ;
	Logger logger = Logger.getLogger(Movimentazioni.class);
	
	
	
	
	
	public String executeCommandDetailsMovimentazioni(ActionContext context)
	{

		addModuleBean(context, "View Accounts", "Search Results");

		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

		String cfImpresa = thisUser.getSoggetto().getCodFiscale();
		Connection db = null;
		try {
			db = this.getConnection(context);
			
			int idMovimentazione = Integer.parseInt(context.getParameter("idMovimentazione"));
			
			
			LookupList LookupTipoMovimentazione = new LookupList(db,"lookup_apicoltura_tipo_movimentazione");
			context.getRequest().setAttribute("LookupTipoMovimentazione", LookupTipoMovimentazione);
			
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
			
			LookupList lookupStati = new LookupList(db, "lookup_apicoltura_stati_movimentazione");
			context.getRequest().setAttribute("LookupStati", lookupStati);
			
			ModelloC modelloC = new ModelloC();
			modelloC.queryRecord(db, idMovimentazione);
			modelloC.setApiarioOrigine(new Stabilimento(db, modelloC.getIdStabilimentoOrigine()));
			if(modelloC.getIdTipoMovimentazione()!=3 && modelloC.getIdStabilimentoDestinazione()>0)
				modelloC.setApiarioDestinazione(new Stabilimento(db, modelloC.getIdStabilimentoDestinazione()));
			else
			{
				Operatore operatoreDestinazione = new Operatore(db, modelloC.getCodiceAziendaDestinazione(), null, null, null, null);
				context.getRequest().setAttribute("OperatoreDestinazione", operatoreDestinazione);
			}
			
			context.getRequest().setAttribute("ModelloC", modelloC);
			
			OperatoreAction.abilitazioneTastoCensimenti(context.getRequest());

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return "detailsOK";
	}
	
	public String executeCommandModificaMovimentazioni(ActionContext context)
	{
		Connection db = null;
		
		String tipoMovimentazione = context.getParameter("tipoMovimentazione");
		if(tipoMovimentazione==null)
			tipoMovimentazione = (String)context.getRequest().getAttribute("tipoMovimentazione");
		
		try 
		{
			db = this.getConnection(context);

			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

			ComuniAnagrafica c = new ComuniAnagrafica();

			
			int idMovimentazione = -1;
			if(context.getParameter("idMovimentazione")==null)
				idMovimentazione = Integer.parseInt((String)context.getRequest().getAttribute("idMovimentazione"));
			else
				idMovimentazione = Integer.parseInt(context.getParameter("idMovimentazione"));

			
			int idApiario = -1;
			if(context.getParameter("idApiario")==null)
				idApiario = Integer.parseInt((String)context.getRequest().getAttribute("idApiario"));
			else
				idApiario = Integer.parseInt(context.getParameter("idApiario"));
			
			LookupList NazioniList = new LookupList(db,"lookup_nazioni");
			
			context.getRequest().setAttribute("NazioniList", NazioniList);


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

			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db,((UserBean) context.getSession().getAttribute("User")).getSiteId());
			LookupList comuniList = new LookupList();
			comuniList.queryListComuni(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("ComuniList", comuniList);
			
			ModelloC modelloC = new ModelloC();
			modelloC.queryRecord(db, idMovimentazione);
			if(modelloC.getIdStabilimentoOrigine()>0)
				modelloC.setApiarioOrigine(new Stabilimento(db, modelloC.getIdStabilimentoOrigine()));
			if(modelloC.getIdStabilimentoDestinazione()>0)
				modelloC.setApiarioDestinazione(new Stabilimento(db, modelloC.getIdStabilimentoDestinazione()));
			else
			{
				Operatore opDestinazione = new Operatore ();
				opDestinazione.queryRecordOperatore(db, modelloC.getCodiceAziendaDestinazione(), null, null, null, null);
				context.getRequest().setAttribute("OperatoreDestinazione", opDestinazione);
			}
		    context.getRequest().setAttribute("ModelloC", modelloC);
		    
		    
		    String cfImpresa = modelloC.getApiarioOrigine().getOperatore().getCodFiscale();
			Operatore op = new Operatore ();
			op.queryRecordOperatore(db, null, cfImpresa, null, null, null);
			op.getListaStabilimenti().setIdOperatore(op.getIdOperatore());
			op.getListaStabilimenti().buildList(db);
			context.getRequest().setAttribute("Operatore", op);
		    
		    if(modelloC.getIdTipoMovimentazione()!=4)
		    	context.getRequest().setAttribute("ApiarioOrigine", new Stabilimento(db,idApiario));
		    
		    long dataMilli = System.currentTimeMillis(); 
			//In formato data: 
			java.util.Date data = new Date( dataMilli );
			SimpleDateFormat forma=new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
			String dataOdierna=forma.format(data);
			context.getRequest().setAttribute("dataOdierna", dataOdierna);



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

		return "modifica"+tipoMovimentazione+"OK";
	}
	
	public String executeCommandAccettazione(ActionContext context)
	{

		Connection db = null;
		ModelloC modello = null;
		try 
		{
			db = this.getConnection(context);
			
			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
			
			String id = context.getParameter("id");
			
			//PROVENIENTE DA REGIOEN CAMPANIA PERCHE' ABBIAMO L'ID DEL MOVIMENTO
			if(id!=null && !id.equals(""))
			{
				modello = new ModelloC(db, Integer.parseInt(context.getParameter("id")));
				modello.setApiarioOrigine(new Stabilimento(db, modello.getIdStabilimentoOrigine()));
				modello.setAccettazioneDestinatario(2);
				modello.setUtenteAccettazioneDestinatario(thisUser.getUserId());
				modello.setModified_by(thisUser.getUserId());
				modello.setDataAccettazioneDestinatario(new Timestamp(System.currentTimeMillis()));
				modello.setProgressivoApiarioDestinazione(context.getParameter("progressivoDestinazione"));
				
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
				Date data = sdf1.parse(context.getParameter("dataIngresso"));
				modello.setDataIngresso(new Timestamp(data.getTime()));
				
				Date dataOdierna = sdf1.parse(sdf1.format(new Date()));
				
				
				if(modello.getDataIngresso().compareTo(dataOdierna)>0 || modello.getDataIngresso().compareTo(modello.getDataMovimentazione())<0)
				{
					System.out.println("Errore per la data ingresso");
					throw new EccezioneDati("Errore: data ingresso deve essere compresa tra la data odierna e la data uscita"  );
				}
	
				try 
				{
					db.setAutoCommit(false);
					System.out.println("Accettazione in esecuzione");
					modello.accettazione(db);
					System.out.println("Accettazione fatta");
					
					//Aggiornamento in BDN
					WsPost wsPost = new WsPost(db, WsPost.ENDPOINT_API_MOVIMENTAZIONI_INGRESSO,WsPost.AZIONE_INSERT);
					
					HashMap<String, Object> campiInput = new HashMap<>();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					
					
					campiInput.put("apimodmovDtModello",sdf.format(modello.getData_modello())+"T00:00:00.0");
					campiInput.put("apimodmovNumModello",modello.getNumero_modello());
					campiInput.put("provApiattDenominazione",modello.getApiarioOrigine().getOperatore().getRagioneSociale());
					campiInput.put("provApiattAziendaCodice",modello.getApiarioOrigine().getOperatore().getCodiceAzienda());
					campiInput.put("provApiProgressivo",modello.getApiarioOrigine().getProgressivoBDA());
					campiInput.put("apiProgressivo",modello.getProgressivoApiarioDestinazione());
					campiInput.put("numAlveari",modello.getNumApiariSpostati());
					campiInput.put("numPacchiDapi",modello.getNumPacchiDaSpostare());
					campiInput.put("numSciami",modello.getNumSciamiDaSpostare());
					campiInput.put("numApiRegine",modello.getNumRegineDaSpostare());
					campiInput.put("apimotingCodice","A");
					campiInput.put("apiattDenominazione",modello.getDenominazioneApicoltore());
					campiInput.put("apiattAziendaCodice",modello.getCodiceAziendaDestinazione());
					campiInput.put("dtIngresso",sdf.format(modello.getDataIngresso())+"T00:00:00.0");
					
					
					wsPost.setCampiInput(campiInput);
					wsPost.costruisciEnvelope(db);
					String response = wsPost.post(db, getUserId(context));
					String idMovimentazioneIngresso = utilsXML.getValoreNodoXML(response,"apiingId");
					
					if(idMovimentazioneIngresso==null || idMovimentazioneIngresso.equals(""))
					{
						System.out.println("Errore nell'invio in bdn della movimentazione in ingresso");
						String messaggio = utilsXML.getValoreNodoXML(response,"faultstring");
						throw new EccezioneDati("Errore nell'invio in bdn: " + ((messaggio!=null)?(messaggio):("")) );
					}
					
					modello.setAccettazioneSincronizzatoBdn(true);
					modello.setAccettazioneSincronizzataDa(getUserId(context));
					modello.setAccettazioneDataSincronizzazione(new Timestamp(System.currentTimeMillis()));
					modello.sincronizzaAccettazioneBdn(db);
					
					System.out.println("Faccio commit");
					db.commit();
					
				}
					
				catch(Exception e)
				{
					System.out.println("Faccio rollback");
					db.rollback();
					e.printStackTrace();
					context.getRequest().setAttribute("Error", e.getMessage());
				}
				finally
				{
					System.out.println("Finally!");
					this.freeConnection(context, db);
				}
			}
			//PROVENIENTE DA FUORI REGIONE
			else
			{
				try 
				{
					//Creo movimentazione con i dati dell'accettazione
					modello = new ModelloC();
					modello.setAccettazioneDestinatario(2);
					modello.setUtenteAccettazioneDestinatario(thisUser.getUserId());
					modello.setModified_by(thisUser.getUserId());
					modello.setDataAccettazioneDestinatario(new Timestamp(System.currentTimeMillis()));
					modello.setProgressivoApiarioDestinazione(context.getParameter("progressivoDestinazione"));
					modello.setDataMovimentazione(context.getParameter("dataMovimentazione"));
					
					SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
					Date data = sdf1.parse(context.getParameter("dataIngresso"));
					modello.setDataIngresso(new Timestamp(data.getTime()));
					//Fine creazione movimentazione con i dati del'accettazione
					
					//Controllo data selezionata
					Date dataOdierna = sdf1.parse(sdf1.format(new Date()));
					if(modello.getDataIngresso().compareTo(dataOdierna)>0 || modello.getDataIngresso().compareTo(modello.getDataMovimentazione())<0)
					{
						System.out.println("Errore per la data ingresso");
						throw new EccezioneDati("Errore: data ingresso deve essere compresa tra la data odierna e la data uscita"  );
					}
					//Fine controllo
					
					//Cerco dati movimentazione in bdn per prendere gli ulteriori dati da inserire
					String idBdn = context.getParameter("idBdn");
					WsPost wsPost = new WsPost(db, WsPost.ENDPOINT_API_MOVIMENTAZIONI,WsPost.AZIONE_GETBYPK);
					
					HashMap<String, Object> campiInput = new HashMap<>();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					
					campiInput.put("apimodmovId",idBdn);
					
					wsPost.setCampiInput(campiInput);
					wsPost.costruisciEnvelope(db);
					String response = wsPost.post(db, getUserId(context));
					String idBdnTrovato = utilsXML.getValoreNodoXML(response,"apimodmovId");
					if(idBdnTrovato==null || idBdnTrovato.equals(""))
					{
						System.out.println("Errore nella ricerca della movimentazione in bdn ");
						String messaggio = utilsXML.getValoreNodoXML(response,"faultstring");
						throw new EccezioneDati("Errore nell'invio in bdn: " + ((messaggio!=null)?(messaggio):("")) );
					}
					//Ricerca dati movimentazione in bdn finita!!!
					
					//Completo la creazione della movimentazione con i dati presi da bdn
					modello.setIdBdn(Integer.parseInt(idBdnTrovato));
					modello.setProgressivoApiarioOrigine(utilsXML.getValoreNodoXML(response,"apiProgressivo"));
					modello.setCodiceAziendaOrigine(utilsXML.getValoreNodoXML(response,"apiattAziendaCodice"));
					modello.setNumero_modello(utilsXML.getValoreNodoXML(response,"numModello"));
					String dataUscita = utilsXML.getValoreNodoXML(response,"dtUscita");
					modello.setDataMovimentazione(new Timestamp(sdf.parse(dataUscita).getTime()));
					String dataModello = utilsXML.getValoreNodoXML(response,"dtModello");
					modello.setData_modello(new Timestamp(sdf.parse(dataModello).getTime()));
					String tipoMovimentazione = utilsXML.getValoreNodoXML(response,"apimotuscCodice");
					if(tipoMovimentazione.equalsIgnoreCase("N"))
						modello.setIdTipoMovimentazione(2);
					else if(tipoMovimentazione.equalsIgnoreCase("C"))
						modello.setIdTipoMovimentazione(1);
					modello.setCodiceAziendaDestinazione(utilsXML.getValoreNodoXML(response,"destApiattAziendaCodice"));
					String attestazioneSanitaria = utilsXML.getValoreNodoXML(response,"attestazioneSanitaria");
					if(attestazioneSanitaria.equalsIgnoreCase("N"))
						modello.setAttestazioneSanitaria(false);
					else if(attestazioneSanitaria.equalsIgnoreCase("S"))
						modello.setAttestazioneSanitaria(true);
					
					String listaDettaglioModello = utilsXML.getValoreNodoXML(response,"listaDettaglioModello");
					listaDettaglioModello = utilsXML.getValoreNodoXML(response,"Apidetmod");
					
					modello.setSigla_prov_comune_dest(utilsXML.getValoreNodoXML(listaDettaglioModello,"destApiProSigla"));
					modello.setComune_dest(utilsXML.getValoreNodoXML(listaDettaglioModello,"destApiComDescrizione"));
					modello.setNumSciamiDaSpostare(utilsXML.getValoreNodoXML(listaDettaglioModello,"numSciami"));
					modello.setNumAlveariDaSpostare(utilsXML.getValoreNodoXML(listaDettaglioModello,"numAlveari"));
					modello.setNumPacchiDaSpostare(utilsXML.getValoreNodoXML(listaDettaglioModello,"numPacchiDapi"));
					modello.setNumRegineDaSpostare(utilsXML.getValoreNodoXML(listaDettaglioModello,"numApiRegine"));
					modello.setIndirizzo_dest(utilsXML.getValoreNodoXML(listaDettaglioModello,"destApiIndirizzo"));
					modello.setLatitudine_dest(utilsXML.getValoreNodoXML(listaDettaglioModello,"destApiLatitudine"));
					modello.setLongitudine_dest(utilsXML.getValoreNodoXML(listaDettaglioModello,"destApiLongitudine"));
					//Fine completamento creazione della movimentazione con i dati presi da bdn
					
					//Inserimento movimentazione accettata in Gisa
					db.setAutoCommit(false);
					modello.insert(db);
					//Fine inserimento
					
					//Aggiornamento in BDN - invio movimentazione in ingresso
					wsPost = new WsPost(db, WsPost.ENDPOINT_API_MOVIMENTAZIONI_INGRESSO,WsPost.AZIONE_INSERT);
					
					campiInput = new HashMap<>();
					sdf = new SimpleDateFormat("yyyy-MM-dd");
					
					campiInput.put("apimodmovDtModello",sdf.format(modello.getData_modello())+"T00:00:00.0");
					campiInput.put("apimodmovNumModello",modello.getNumero_modello());
					campiInput.put("provApiattDenominazione",modello.getApiarioOrigine().getOperatore().getRagioneSociale());
					campiInput.put("provApiattAziendaCodice",modello.getApiarioOrigine().getOperatore().getCodiceAzienda());
					campiInput.put("provApiProgressivo",modello.getApiarioOrigine().getProgressivoBDA());
					campiInput.put("apiProgressivo",modello.getProgressivoApiarioDestinazione());
					campiInput.put("numAlveari",modello.getNumApiariSpostati());
					campiInput.put("numPacchiDapi",modello.getNumPacchiDaSpostare());
					campiInput.put("numSciami",modello.getNumSciamiDaSpostare());
					campiInput.put("numApiRegine",modello.getNumRegineDaSpostare());
					campiInput.put("apimotingCodice","A");
					campiInput.put("apiattDenominazione",modello.getDenominazioneApicoltore());
					campiInput.put("apiattAziendaCodice",modello.getCodiceAziendaDestinazione());
					campiInput.put("dtIngresso",sdf.format(modello.getDataIngresso())+"T00:00:00.0");
					
					
					wsPost.setCampiInput(campiInput);
					wsPost.costruisciEnvelope(db);
					response = wsPost.post(db, getUserId(context));
					String idMovimentazioneIngresso = utilsXML.getValoreNodoXML(response,"apiingId");
					
					if(idMovimentazioneIngresso==null || idMovimentazioneIngresso.equals(""))
					{
						System.out.println("Errore nell'invio in bdn della movimentazione in ingresso");
						String messaggio = utilsXML.getValoreNodoXML(response,"faultstring");
						throw new EccezioneDati("Errore nell'invio in bdn: " + ((messaggio!=null)?(messaggio):("")) );
					}
					
					modello.setAccettazioneSincronizzatoBdn(true);
					modello.setAccettazioneSincronizzataDa(getUserId(context));
					modello.setAccettazioneDataSincronizzazione(new Timestamp(System.currentTimeMillis()));
					modello.sincronizzaAccettazioneBdn(db);
					
					db.commit();
						
					}
					
				catch(Exception e)
				{
					System.out.println("Faccio rollback");
					db.rollback();
					e.printStackTrace();
					context.getRequest().setAttribute("Error", e.getMessage());
				}
				finally
				{
					System.out.println("Finally!");
					this.freeConnection(context, db);
				}
			}
		
			context.getRequest().setAttribute("opId", modello.getApiarioOrigine().getOperatore().getIdOperatore());
			context.getRequest().setAttribute("searchcodeidAzienda", modello.getApiarioOrigine().getOperatore().getIdOperatore());
			context.getRequest().setAttribute("searchcodeCodiceAziendaSearch", modello.getCodiceAziendaDestinazione());
			
			System.out.println("Ritorna a lista movimentazioni in ingresso");
			return executeCommandSearchIngresso(context);
		} 
		catch (Exception e) 
		{
			context.getRequest().setAttribute("Error", e.getMessage());
			return ("SystemError");
		} 
		finally 
		{
			this.freeConnection(context, db);
		}
	}
	
	
	public String executeCommandSearchForm(ActionContext context)
	{

		addModuleBean(context, "View Accounts", "Search Results");

		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

		String cfImpresa = null;
    	if(thisUser.getRoleId()==org.aspcfs.modules.admin.base.Role.RUOLO_DELEGATO || thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("avellino") || 
    			thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("benevento") || 
    			thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("caserta") || 
    			thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("NAPOLI 1 CENTRO") || 
    			thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("NAPOLI 2 NORD") || 
    			thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("NAPOLI 3 SUD") || 
    			thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("salerno"))
    		cfImpresa = thisUser.getSoggetto().getCodFiscale();
    	else
    		cfImpresa = thisUser.getContact().getVisibilitaDelega();
    	
		Connection db = null;
		try {
			db = this.getConnection(context);
			
			if (cfImpresa!=null && !"".equals(cfImpresa))
			{
			Operatore op = new Operatore ();
			op.queryRecordOperatore(db, null, cfImpresa, null, null, null);
			op.getListaStabilimenti().setIdOperatore(op.getIdOperatore());
			op.getListaStabilimenti().buildList(db);
			
			context.getRequest().setAttribute("Operatore", op);
			}

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		return "SearchOK";
	}
	

	public String executeCommandList(ActionContext context)
	{

		return executeCommandSearchForm(context);

		
	}
	
	
	
	public static String generaFoglioExcelMovimentazioniPerApiario(Connection conn, String codiceApiarioSearch, String progressivoApiarioSearch ,String pathTempFold) throws Exception
	{
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		String fullPath = null;
		
		try
		{
			XSSFWorkbook wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet("movimentazioni");
			
			
			XSSFCellStyle stileCellaVerde = wb.createCellStyle();
			stileCellaVerde.setWrapText(true);
			stileCellaVerde.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			stileCellaVerde.setFillPattern(CellStyle.SOLID_FOREGROUND);
//			stileCella.setLocked(true);
			stileCellaVerde.setVerticalAlignment(CellStyle.ALIGN_LEFT);
			stileCellaVerde.setAlignment(CellStyle.ALIGN_CENTER);
			pst = conn.prepareStatement("select tipo.description as tipo_movimentazione, mov.data_movimentazione, "
							+"op_or.ragione_sociale as azienda_origine, "
							+"mov.progressivo_apiario_origine as prog_apiario_origine, "
							+"ap_or.num_alveari as num_alveari_origine, "
							+"case when op_dest.ragione_sociale is not null and op_dest.ragione_sociale != '' then op_dest.ragione_sociale else mov.denominazione_apicoltore end as azienda_destinazione, "
							+"mov.progressivo_apiario_destinazione as prog_apiario_destinazione, "
							+"mov.cf_partita_iva_apicoltore, "
							+"mov.denominazione_apicoltore, "
							+"mov.indirizzo_dest as indirizzo_apicoltore_destinazione, "
							+"mov.comune_dest as comune_apicoltore_destinazione, "
							+"case when mov.recupero_materiale_biologico = true then ('SI') else 'NO' end as recupero_materiale_biologico, "
							+"ap_dest.num_alveari as num_alveari_destinazione, "
							+"case when mov.sincronizzato_bdn = true then ('SINCRONIZZATO IN DATA ' || date_trunc('hour',mov.data_sincronizzazione)) else 'NON SINCRONIZZATO' end as stato_bdn "
							+", num_sciami_da_spostare , num_regine_da_spostare , num_pacchi_da_spostare , num_alveari_da_spostare "
							+"from apicoltura_movimentazioni mov join lookup_apicoltura_tipo_movimentazione tipo "
							+"on tipo.code = mov.id_tipo_movimentazione "
							+"join apicoltura_apiari ap_or on ap_or.id = mov.id_stabilimento_apiario_origine "
							+"join apicoltura_imprese op_or on op_or.id = ap_or.id_operatore "
							+"left join apicoltura_apiari ap_dest on ap_dest.id = mov.id_stabilimento_apiario_destinazione "
							+"left join apicoltura_imprese op_dest on op_dest.id = ap_dest.id_operatore or op_dest.codice_azienda = mov.codice_azienda_destinazione "
							+"where mov.trashed_date is null "
							+"and ((mov.codice_azienda_origine ilike ? and mov.progressivo_apiario_origine = ? ) or (mov.codice_azienda_origine ilike ? and mov.id_tipo_movimentazione = 4 )) "
							+"or ( mov.codice_azienda_destinazione ilike ? and mov.progressivo_apiario_destinazione = ? ) or (mov.codice_azienda_destinazione ilike ? and mov.id_tipo_movimentazione = 4 )  ");
			
			pst.setString(1, codiceApiarioSearch);
			pst.setString(2, progressivoApiarioSearch);
			pst.setString(3, codiceApiarioSearch);
			pst.setString(4, codiceApiarioSearch);
			pst.setString(5, progressivoApiarioSearch);
			pst.setString(6, codiceApiarioSearch);
			
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
			
			fullPath = pathTempFold+"/"+"MovimentazioniApicoltore"+(int)(Math.random()*100)+".xlsx";
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
	
	
	
	
	
	
	public String executeCommandNotificheMovimentazioniDaValidare(ActionContext context)
	{

		addModuleBean(context, "View Accounts", "Search Results");

		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

		String idAzienda			= context.getRequest().getParameter("searchcodeidAzienda");
		String idTipoMovimentazione	= context.getRequest().getParameter("idTipoMovimentazione");


		Connection db = null;
		try {
			db = this.getConnection(context);
			
			LookupList LookupTipoMovimentazione = new LookupList(db,"lookup_apicoltura_tipo_movimentazione");
			context.getRequest().setAttribute("LookupTipoMovimentazione", LookupTipoMovimentazione);
			
			LookupList listaAsl = new LookupList(db,"lookup_site_id");
			context.getRequest().setAttribute("SiteIdList", listaAsl);
			
			
			 PagedListInfo searchListInfo = this.getPagedListInfo( context, "SearchMovimentazioniListInfo");
			    
			    String popup = context.getRequest().getParameter("popup");
			    searchListInfo.setLink("ApicolturaMovimentazioni.do?command=Search&idTipoMovimentazione="+idTipoMovimentazione);
			    
			    MovimentazioniList listaMovimentazioni = new MovimentazioniList();
			    listaMovimentazioni.setStato(API_STATO_DA_NOTIFICARE);
			    listaMovimentazioni.setIdAsl(getUserSiteId(context));
			    
			    
			    searchListInfo.setSearchCriteria(listaMovimentazioni, context);
			    
			    
			    listaMovimentazioni.buildList(db);
			    context.getRequest().setAttribute("ListaMovimentazioni", listaMovimentazioni);

			    return "ListaMovimentazioniInValidazioneOK";
		
			
			
			

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}

	public String executeCommandSearch(ActionContext context)
	{

		addModuleBean(context, "View Accounts", "Search Results");

		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

		String idAzienda			= context.getRequest().getParameter("searchcodeidAzienda");
		String idTipoMovimentazione	= context.getRequest().getParameter("idTipoMovimentazione");


		Connection db = null;
		try {
			db = this.getConnection(context);
			
			LookupList LookupTipoMovimentazione = new LookupList(db,"lookup_apicoltura_tipo_movimentazione");
			context.getRequest().setAttribute("LookupTipoMovimentazione", LookupTipoMovimentazione);
			
//			String codiceAziendaSearch = context.getParameter("codiceAziendaSearch");
			String codiceAziendaSearch = context.getParameter("searchcodeCodiceAziendaSearch");
			if(codiceAziendaSearch==null || codiceAziendaSearch.equals("null") || codiceAziendaSearch.equals(""))
				codiceAziendaSearch = (String)context.getRequest().getAttribute("searchcodeCodiceAziendaSearch");
			context.getRequest().setAttribute("codiceAziendaSearch", codiceAziendaSearch);
			
			String progressivoApiarioSearch = context.getParameter("progressivoApiarioSearch");
			if(progressivoApiarioSearch==null || progressivoApiarioSearch.equals("null") || progressivoApiarioSearch.equals(""))
				progressivoApiarioSearch = (String)context.getRequest().getAttribute("progressivoApiarioSearch");
			context.getRequest().setAttribute("progressivoApiarioSearch", progressivoApiarioSearch);

			if(progressivoApiarioSearch==null || progressivoApiarioSearch.equals("null") || progressivoApiarioSearch.equals(""))
				progressivoApiarioSearch = context.getParameter("searchcodeProgressivoApiarioSearch");
			
			
			 PagedListInfo searchListInfo = this.getPagedListInfo( context, "SearchMovimentazioniListInfo");
			    
			    String popup = context.getRequest().getParameter("popup");
			    searchListInfo.setLink("ApicolturaMovimentazioni.do?command=Search&searchcodeProgressivoApiarioSearch="+progressivoApiarioSearch+"&progressivoApiarioSearch="+progressivoApiarioSearch+"&searchcodeCodiceAziendaSearch="+codiceAziendaSearch+"&codiceAziendaSearch="+codiceAziendaSearch+"&idTipoMovimentazione="+idTipoMovimentazione);
			    
			    MovimentazioniList listaMovimentazioni = new MovimentazioniList();
			    
			    listaMovimentazioni.setPagedListInfo(searchListInfo);
			    searchListInfo.setSearchCriteria(listaMovimentazioni, context);
			    listaMovimentazioni.setCodiceAziendaSearch(codiceAziendaSearch);
			    listaMovimentazioni.setProgressivoApiarioSearch(progressivoApiarioSearch);
			    listaMovimentazioni.buildList(db);
			    context.getRequest().setAttribute("ListaMovimentazioni", listaMovimentazioni);
			    
				//Gestione Abilitazione tasto Modifica
				SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
				GregorianCalendar gCalendar = new GregorianCalendar();
				gCalendar.setTimeInMillis(format.parse(format.format(new Date())).getTime());
				gCalendar.add(Calendar.DAY_OF_MONTH, -7);
				Date dataAttuale = new Date();
				dataAttuale.setTime(gCalendar.getTimeInMillis());
				context.getRequest().setAttribute("dataAttuale", dataAttuale);
				//Fine Gestione Abilitazione tasto Modifica
				
			    return "ListaMovimentazioniOK";

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}
	
	
	public String executeCommandSearchIngresso(ActionContext context)
	{

		addModuleBean(context, "View Accounts", "Search Results");

		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

		Connection db = null;
		try {
			db = this.getConnection(context);
			
			LookupList LookupTipoMovimentazione = new LookupList(db,"lookup_apicoltura_tipo_movimentazione");
			context.getRequest().setAttribute("LookupTipoMovimentazione", LookupTipoMovimentazione);
			
			LookupList LookupStatoAccettazioneMovimentazione = new LookupList(db,"apicoltura_lookup_stato_movimentazione_accettazione");
			context.getRequest().setAttribute("LookupStatoAccettazioneMovimentazione", LookupStatoAccettazioneMovimentazione);
			
//			String codiceAziendaSearch = context.getParameter("codiceAziendaSearch");
			String codiceAziendaSearch = context.getParameter("searchcodeCodiceAziendaSearch");
			if(codiceAziendaSearch==null)
				codiceAziendaSearch = (String)context.getRequest().getAttribute("searchcodeCodiceAziendaSearch");
			context.getRequest().setAttribute("codiceAziendaSearch", codiceAziendaSearch);

			PagedListInfo searchListInfo = this.getPagedListInfo( context, "SearchMovimentazioniIngressoListInfo");
			    
		    String popup = context.getRequest().getParameter("popup");
		    searchListInfo.setLink("ApicolturaMovimentazioni.do?command=SearchIngresso&searchcodeCodiceAziendaSearch="+codiceAziendaSearch);
		    
		    MovimentazioniList listaMovimentazioni = new MovimentazioniList();
		    
		    listaMovimentazioni.setPagedListInfo(searchListInfo);
		    searchListInfo.setSearchCriteria(listaMovimentazioni, context);
		    listaMovimentazioni.setCodiceAziendaDestinazione(codiceAziendaSearch);
		    ArrayList<Integer> tipiMovimentazioni = new ArrayList<Integer>();
		    tipiMovimentazioni.add(1);
		    tipiMovimentazioni.add(4);
		    listaMovimentazioni.setTipoMovimentazione(tipiMovimentazioni);
		    listaMovimentazioni.buildList(db);
		    context.getRequest().setAttribute("ListaMovimentazioni", listaMovimentazioni);
		    
		    
		    //DA FUORI REGIONE
		    WsPost wsPost = new WsPost(db, WsPost.ENDPOINT_API_MOVIMENTAZIONI,WsPost.AZIONE_GET);
			
			HashMap<String, Object> campiInput = new HashMap<>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			campiInput.put("flagInEntrataDaApicoltoriFuoriRegione","S");
			
			wsPost.setCampiInput(campiInput);
			wsPost.costruisciEnvelope(db);
			String response = wsPost.post(db, getUserId(context));
			
			this.freeConnection(context, db);
			
			while(response.indexOf("<ApimodmovTO>")>0)
			{
				int posizioneInizioReturn = response.indexOf("<ApimodmovTO>");
				int posizioneFineReturn = response.indexOf("</ApimodmovTO>") + 14;
				
				String responseOttenuto = response.substring(posizioneInizioReturn, posizioneFineReturn);
				responseOttenuto = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Body><ns2:searchApimodmovResponse xmlns:ns2=\"http://ws.apimovimentazione.apicoltura.izs.it/\">" + responseOttenuto + "</ns2:searchApimodmovResponse></S:Body></S:Envelope>";
				String destApiattAziendaCodice = utilsXML.getValoreNodoXML(responseOttenuto,"destApiattAziendaCodice" );
				String codiceTipoMovimento = utilsXML.getValoreNodoXML(responseOttenuto,"apimotuscCodice" );
				if(destApiattAziendaCodice.equals(codiceAziendaSearch) && codiceTipoMovimento.equalsIgnoreCase("C"))
				{
					/* Altri campi che servirebbe popolare
					thisMovimentazione.getId()
					thisMovimentazione.getComune_dest()
					thisMovimentazione.getIndirizzo_dest()
					thisMovimentazione.getNumApiariOrigine()
					thisMovimentazione.getCfPartitaApicoltore()
					thisMovimentazione.getIdTipoMovimentazione()
					thisMovimentazione.getIdStabilimentoOrigine()
					thisMovimentazione.getDenominazioneApicoltore()
					thisMovimentazione.getAccettazioneDestinatario()
					thisMovimentazione.getProgressivoApiarioDestinazione()*/
					
					ModelloC modello = new ModelloC();
					modello.setProgressivoApiarioOrigine(utilsXML.getValoreNodoXML(responseOttenuto,"apiProgressivo" ));
					modello.setCodiceAziendaOrigine(utilsXML.getValoreNodoXML(responseOttenuto,"apiattAziendaCodice" ));
					modello.setNumAlveariDaSpostare(utilsXML.getValoreNodoXML(responseOttenuto,"numAlveari" ));  
					modello.setNumSciamiDaSpostare(utilsXML.getValoreNodoXML(responseOttenuto,"numSciami" )); 
					modello.setNumPacchiDaSpostare(utilsXML.getValoreNodoXML(responseOttenuto,"numPacchiDapi" )); 
					modello.setNumRegineDaSpostare(utilsXML.getValoreNodoXML(responseOttenuto,"numApiRegine" )); 
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
					Timestamp dataMovimentazione = new Timestamp(sdf2.parse(utilsXML.getValoreNodoXML(responseOttenuto,"dtUscita" )).getTime());
					modello.setDataMovimentazione(dataMovimentazione); 
					modello.setCodiceAziendaDestinazione(utilsXML.getValoreNodoXML(responseOttenuto,"destApiattAziendaCodice" ));
					modello.setIdBdn(Integer.parseInt(utilsXML.getValoreNodoXML(responseOttenuto,"apimodmovId" )));  
					
					listaMovimentazioni.add(modello);
				}
				
				
				response = response.substring(0, posizioneInizioReturn) + response.substring(posizioneFineReturn, response.length());
			}
		    
		    
		    return "ListaMovimentazioniIngressoOK";

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}
	
	
	public String executeCommandSearchTutte(ActionContext context)
	{

		addModuleBean(context, "View Accounts", "Search Results");

		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

		Connection db = null;
		try 
		{
			db = this.getConnection(context);
			
			LookupList LookupTipoMovimentazione = new LookupList(db,"lookup_apicoltura_tipo_movimentazione");
			context.getRequest().setAttribute("LookupTipoMovimentazione", LookupTipoMovimentazione);
			
			LookupList LookupStatoAccettazioneMovimentazione = new LookupList(db,"apicoltura_lookup_stato_movimentazione_accettazione");
			context.getRequest().setAttribute("LookupStatoAccettazioneMovimentazione", LookupStatoAccettazioneMovimentazione);
			
			String codiceAziendaSearch = context.getParameter("searchcodeCodiceAziendaSearch");
			if(codiceAziendaSearch==null)
				codiceAziendaSearch = (String)context.getRequest().getAttribute("searchcodeCodiceAziendaSearch");
			context.getRequest().setAttribute("codiceAziendaSearch", codiceAziendaSearch);

			PagedListInfo searchListInfo = this.getPagedListInfo( context, "SearchMovimentazioniTutteListInfo");
			    
		    String popup = context.getRequest().getParameter("popup");
		    searchListInfo.setLink("ApicolturaMovimentazioni.do?command=SearchTutte&searchcodeCodiceAziendaSearch="+codiceAziendaSearch);
		    
		    MovimentazioniList listaMovimentazioni = new MovimentazioniList();
		    
		    listaMovimentazioni.setPagedListInfo(searchListInfo);
		    searchListInfo.setSearchCriteria(listaMovimentazioni, context);
		    listaMovimentazioni.setCodiceAziendaDestinazione(codiceAziendaSearch);
		    listaMovimentazioni.setCodiceAziendaOrigine(codiceAziendaSearch);
		    listaMovimentazioni.buildList(db);
		    context.getRequest().setAttribute("ListaMovimentazioni", listaMovimentazioni);
		    
		    return "ListaMovimentazioniTutteOK";

		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}
	
	public String executeCommandSearchStorico(ActionContext context)
	{
		addModuleBean(context, "View Accounts", "Search Results");

		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

		String idAzienda			= context.getRequest().getParameter("searchcodeidAzienda");
		String idMovimentazione	= context.getRequest().getParameter("idMovimentazione");

		Connection db = null;
		try 
		{
			db = this.getConnection(context);
			
			LookupList LookupTipoMovimentazione = new LookupList(db,"lookup_apicoltura_tipo_movimentazione");
			context.getRequest().setAttribute("LookupTipoMovimentazione", LookupTipoMovimentazione);
			
			String codiceAziendaSearch = context.getParameter("searchcodeCodiceAziendaSearch");
			context.getRequest().setAttribute("codiceAziendaSearch", codiceAziendaSearch);
			
			PagedListInfo searchListInfo = this.getPagedListInfo( context, "SearchMovimentazioniStoricoInfo");
			    
		    String popup = context.getRequest().getParameter("popup");
		    searchListInfo.setLink("ApicolturaMovimentazioni.do?command=SearchStorico&idMovimentazione="+idMovimentazione);
		    
		    MovimentazioniList listaMovimentazioni = new MovimentazioniList();
		    
		    listaMovimentazioni.setPagedListInfo(searchListInfo);
		    searchListInfo.setSearchCriteria(listaMovimentazioni, context);
		    listaMovimentazioni.setIdMovimentazione(Integer.parseInt(idMovimentazione));
		    listaMovimentazioni.buildListStorico(db);
		    context.getRequest().setAttribute("ListaMovimentazioni", listaMovimentazioni);

		    return "ListaMovimentazioniStoricoOK";
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

	public String executeCommandAdd(ActionContext context)
	{

		Connection db = null;
		try {
			db = this.getConnection(context);

			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

			ComuniAnagrafica c = new ComuniAnagrafica();

			String cfImpresa = null;
	    	if(thisUser.getRoleId()==org.aspcfs.modules.admin.base.Role.RUOLO_DELEGATO || thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("avellino") || 
	    			thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("benevento") || 
	    			thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("caserta") || 
	    			thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("NAPOLI 1 CENTRO") || 
	    			thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("NAPOLI 2 NORD") || 
	    			thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("NAPOLI 3 SUD") || 
	    			thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("salerno"))
	    		cfImpresa = thisUser.getSoggetto().getCodFiscale();
	    	else
	    		cfImpresa = thisUser.getContact().getVisibilitaDelega();

			Operatore op = new Operatore ();
			op.queryRecordOperatore(db, null, cfImpresa, null, null, null);
			op.getListaStabilimenti().setIdOperatore(op.getIdOperatore());
			op.getListaStabilimenti().buildList(db);

			context.getRequest().setAttribute("Operatore", op);

			LookupList NazioniList = new LookupList(db,"lookup_nazioni");
			
			context.getRequest().setAttribute("NazioniList", NazioniList);
			
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

		return "AddOK";
	}
	
	
	public String executeCommandToAddRichiesta(ActionContext context)
	{
		return "ToAddRichiestaOK";
	}
	
	
	
	public String executeCommandAddRichiesta(ActionContext context)
	{

		Connection db = null;
		
		String tipoMovimentazione = (context.getParameter("tipoMovimentazione")==null)?((String)context.getRequest().getAttribute("tipoMovimentazione")) :(context.getParameter("tipoMovimentazione"));

		try {
			db = this.getConnection(context);

			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

			ComuniAnagrafica c = new ComuniAnagrafica();

			String cfImpresa = null;
	    	if(thisUser.getRoleId()==org.aspcfs.modules.admin.base.Role.RUOLO_DELEGATO || thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("avellino") || 
	    			thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("benevento") || 
	    			thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("caserta") || 
	    			thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("NAPOLI 1 CENTRO") || 
	    			thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("NAPOLI 2 NORD") || 
	    			thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("NAPOLI 3 SUD") || 
	    			thisUser.getContact().getVisibilitaDelega().equalsIgnoreCase("salerno"))
	    		cfImpresa = thisUser.getSoggetto().getCodFiscale();
	    	else
	    		cfImpresa = thisUser.getContact().getVisibilitaDelega();
	    	

			Operatore op = new Operatore ();
			op.queryRecordOperatoreCfImpresaAttivo(db, cfImpresa);
			op.getListaStabilimenti().setIdOperatore(op.getIdOperatore());
			op.getListaStabilimenti().buildList(db);
			
			String idApiarioScelto = (context.getParameter("idApiarioScelto")==null)?(context.getRequest().getAttribute("idApiarioScelto")+"") :(context.getParameter("idApiarioScelto"));
			Stabilimento apiarioOrigine = null;
			if(idApiarioScelto!=null && !idApiarioScelto.equals("null"))
				apiarioOrigine = new Stabilimento(db,Integer.parseInt(idApiarioScelto));
			context.getRequest().setAttribute("ApiarioOrigine", apiarioOrigine);

			context.getRequest().setAttribute("Operatore", op);

			LookupList NazioniList = new LookupList(db,"lookup_nazioni");
			
			context.getRequest().setAttribute("NazioniList", NazioniList);

			long dataMilli = System.currentTimeMillis(); 
			//In formato data: 
			java.util.Date data = new Date( dataMilli );
			SimpleDateFormat forma=new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
			String dataOdierna=forma.format(data);
			context.getRequest().setAttribute("dataOdierna", dataOdierna);

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

		
		return "AddRichiesta"+tipoMovimentazione+"OK";
	}


	public String executeCommandInsert(ActionContext context)
	{
		Connection db = null;
		try {
			db = this.getConnection(context);

			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
			
			LookupList LookupTipoMovimentazione = new LookupList(db,"lookup_apicoltura_tipo_movimentazione");
			context.getRequest().setAttribute("LookupTipoMovimentazione", LookupTipoMovimentazione);
			
			long dataMilli = System.currentTimeMillis(); 
			java.util.Date data = new Date( dataMilli );
			SimpleDateFormat forma=new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);

			ModelloC modello = (ModelloC)context.getRequest().getAttribute("Movimentazione");
			modello.setStato(API_STATO_VALIDAZIONE_NON_RICHIESTA);
			modello.setEntered_by(getUserId(context));
			modello.setSincronizzato_bdn(false);
			
			modello.setLatitudine_dest(context.getParameter("latitudine_dest"));
			modello.setLongitudine_dest(context.getParameter("longitudine_dest"));
			modello.setCfPartitaIvaApicoltore(context.getParameter("cfPartitaIvaAziendaDestinazione"));
			modello.setDenominazioneApicoltore(context.getParameter("ragioneSocialeIn"));
			modello.setRecuperoMaterialeBiologico(( context.getParameter("recuperoMaterialeBiologico")!=null)?(Boolean.parseBoolean( context.getParameter("recuperoMaterialeBiologico"))):(null) );
			modello.setIndirizzo_dest(context.getParameter("indirizzoUbicazioneIn"));
			if(modello.getIdTipoMovimentazione()==4 || modello.getIdTipoMovimentazione()==1)
				modello.setComune_dest(context.getParameter("comuneIn"));	
			else
				modello.setComune_dest(context.getParameter("codiceApiarioDestinazione"));
			modello.setData_modello(forma.format(data));
			modello.setSigla_prov_comune_dest((context.getParameter("provinciaUbicazioneIn")!=null )?(context.getParameter("provinciaUbicazioneIn")):(null));
			modello.setProprietarioDestinazione((context.getParameter("nominativoIn")!=null )?(context.getParameter("nominativoIn")):(null));
			modello.setCfProprietarioDestinazione((context.getParameter("cfPropIn")!=null )?(context.getParameter("cfPropIn")):(null));
			if(context.getParameter("indirizzoIn")!=null )
				modello.setIndirizzo_dest(context.getParameter("indirizzoIn"));
			modello.setCodiceAziendaDestinazione((context.getParameter("codiceAziendaDestinazione")!=null )?(context.getParameter("codiceAziendaDestinazione")):(null));
			
			if(modello.getComune_dest()!=null)
			{
				Provincia p = new Provincia().getProvinciaByComune(db, modello.getComune_dest());
				modello.setSigla_prov_comune_dest(p.getCodProvincia());
			}
			
			modello.setNumAlveariDaSpostare(context.getParameter("numAlveariDaSpostare"));
			modello.setNumPacchiDaSpostare(context.getParameter("numPacchiDaSpostare"));
			modello.setNumRegineDaSpostare(context.getParameter("numRegineDaSpostare"));
			modello.setNumSciamiDaSpostare(context.getParameter("numSciamiDaSpostare"));
			  
			modello.setNumApiariSpostati(modello.getNumApiariOrigine());
			
			StabilimentoList listStab = new StabilimentoList();
			listStab.setCun(modello.getCodiceAziendaOrigine());
			listStab.setProgressivoApiarioFiltro(modello.getProgressivoApiarioOrigine());
			listStab.buildList(db);
			
			if(listStab.size()>0)
			{
				if(modello.getIdTipoMovimentazione()!=4)
					modello.setIdStabilimentoOrigine(((Stabilimento)listStab.get(0)).getIdStabilimento());
				/*setto l'asl dell'apicoltore di Origine*/
				modello.setId_asl_apicoltore_origine(((Stabilimento)listStab.get(0)).getOperatore().getIdAsl());
			}
			
			//Nel caso compravendita o nomadismo
			if (modello.getProgressivoApiarioDestinazione()!=null)
			{
				listStab = new StabilimentoList();
				listStab.setCun(modello.getCodiceAziendaDestinazione());
				listStab.setProgressivoApiarioFiltro(modello.getProgressivoApiarioDestinazione());
				listStab.buildList(db);
			
				if(listStab.size()>0)
					modello.setIdStabilimentoDestinazione(((Stabilimento)listStab.get(0)).getIdStabilimento());
			}
			
			boolean validazioneImplicita = false;
			//Se destinazione movimentazione e' in regione 
			//Se destinazione movimentazione e' fuori regione e non e' stata richiesta la validazione dall'apicoltore
			//allora valida direttamente
			
			//Recuperiamo ASL per verificare se apiario e' fuori regione.
			ext.aspcfs.modules.apiari.base.ComuniAnagrafica c = new ext.aspcfs.modules.apiari.base.ComuniAnagrafica(db,modello.getComune_dest());
			if(modello.getIdTipoMovimentazione()==3 && (c.getDescrizione()==null || c.getDescrizione()==null || c.getDescrizione().equals("") ) )
			{
				throw new Exception("Comune destinazione inserito non valido");
			}
				
			int idAsl = c.getIdAslByComune(db, modello.getComune_dest());
				
			
			if(idAsl!=16 || (idAsl==16 && context.getParameter("richiestaValidazione")==null ))
			{
				validazioneImplicita = true;
			}
			
			
			if( context.getParameter("comuneDestinazione")!=null)
			{
				ext.aspcfs.modules.apiari.base.ComuniAnagrafica com = new ext.aspcfs.modules.apiari.base.ComuniAnagrafica(db, Integer.parseInt(context.getParameter("comuneDestinazione")));
				modello.setComune_dest(com.getDescrizione());	
				Provincia p = new Provincia().getProvinciaByComune(db, com.getDescrizione());
				modello.setSigla_prov_comune_dest(p.getCodProvincia());
			}
			
			
			
			
			
			
			modello.setAttestazioneSanitaria(!validazioneImplicita);
			
			listStab = new StabilimentoList();
			listStab.setCun(modello.getCodiceAziendaOrigine());
			listStab.setProgressivoApiarioFiltro(modello.getProgressivoApiarioOrigine());
			listStab.buildList(db);
			
			if(listStab.size()>0)
				modello.setIdStabilimentoOrigine(((Stabilimento)listStab.get(0)).getIdStabilimento());
			
			modello.generaNumeroModello(db);
			
			if(modello.getIdTipoMovimentazione()!=2 && idAsl!=16)
			{
				modello.setAccettazioneDestinatario(1);
			}
			else
			{
				modello.setAccettazioneDestinatario(3);
			}
			
			if( (modello.getIdTipoMovimentazione()==1 || modello.getIdTipoMovimentazione()==2) && !validazioneImplicita)
			{
				modello.setStato(API_STATO_DA_NOTIFICARE);
			}
			
			boolean isValid = this.validateObject(context, db, modello);
			if (isValid ) 
			{
				try 
				{
						
						//if (db.getAutoCommit()==true)
						db.setAutoCommit(false);

						modello.insert(db);
						
						//Se e' stato inserito in Gisa e non ha dato errore di duplicati, lo metto in bdn
						String idMovimentazioneInserita = null;
						
						//Invio movimento in bdn
						System.out.println(" ------ INSERT MOVIMENTAZIONE -----"); 
						
						WsPost wsPost = new WsPost(db, (modello.getIdTipoMovimentazione()!=4)?(WsPost.ENDPOINT_API_MOVIMENTAZIONI):(WsPost.ENDPOINT_API_REGINE),WsPost.AZIONE_INSERT);
						
						HashMap<String, Object> campiInput = new HashMap<>();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						
						if(modello.getIdTipoMovimentazione()!=4)
						{
							campiInput.put("apiProgressivo", modello.getProgressivoApiarioOrigine());
							campiInput.put("apiattAziendaCodice", modello.getCodiceAziendaOrigine());
							campiInput.put("numModello", modello.getNumero_modello());
							if(modello.getIdTipoMovimentazione()!=3)
							{
								campiInput.put("attestazioneSanitaria", (modello.isAttestazioneSanitaria())?("S"):("N"));
								campiInput.put("statoRichiestaCodice", (modello.isAttestazioneSanitaria())?("I"):("C"));
								campiInput.put("dtStatoRichiesta", sdf.format(new Timestamp(dataMilli) )+"T00:00:00.0");
							}
							campiInput.put("destApiattAziendaCodice", modello.getCodiceAziendaDestinazione());
							campiInput.put("dtModello", sdf.format(modello.getData_modello())+"T00:00:00.0");
							campiInput.put("dtUscita", sdf.format(modello.getDataMovimentazione())+"T00:00:00.0");
							
							if(modello.getIdTipoMovimentazione()==1)
								campiInput.put("apimotuscCodice", "C");
							else if(modello.getIdTipoMovimentazione()==2)
								campiInput.put("apimotuscCodice", "N");
							else
								campiInput.put("apimotuscCodice", "I");
							
							String istat = "" ;
							if(modello.getIdTipoMovimentazione()==2 || modello.getIdTipoMovimentazione()==3)
							{
								String sql = "select cod_comune from comuni1 where nome ilike ? ";
								PreparedStatement pst = db.prepareStatement(sql);
								pst.setString(1, modello.getComune_dest());
								ResultSet rs = pst.executeQuery();
								if (rs.next())
									istat = rs.getString(1);
							}
								
							if(modello.getIdTipoMovimentazione()==3)
							{
								campiInput.put("destAziendaComIstat", istat);
								campiInput.put("destAziendaComProSigla", modello.getSigla_prov_comune_dest());
								campiInput.put("destAziendaIndirizzo", modello.getIndirizzo_dest());
								campiInput.put("destAziendaIdFiscale", modello.getCfPartitaApicoltore());
								campiInput.put("destAziendaDenominazione", modello.getDenominazioneApicoltore());
								campiInput.put("recuperoMaterialeBiologico", modello.getRecuperoMaterialeBiologico());
							}
								
							if(modello.getIdTipoMovimentazione()==2)
							{
								Apidetmod pp = null;
								
								pp = new Apidetmod();
								pp.setDestApiProgressivo( Integer.parseInt(modello.getProgressivoApiarioDestinazione()));
								pp.setDestApiComIstat(istat);
								pp.setDestApiProSigla(modello.getSigla_prov_comune_dest());
								pp.setDestApiComProSigla(modello.getSigla_prov_comune_dest());
								pp.setDestApiIndirizzo(modello.getIndirizzo_dest());
								pp.setNumAlveari(modello.getNumAlveariDaSpostare());
								pp.setNumSciami(0);
								pp.setNumPacchiDapi(0);
								pp.setNumApiRegine(modello.getNumRegineDaSpostare());
								pp.setApidetmodId(0);
								pp.setDestApiComDescrizione(modello.getComune_dest());
								pp.setDestApiLatitudine(modello.getLatitudine_dest());
								pp.setDestApiLongitudine(modello.getLongitudine_dest());
								pp.setDestApiId(modello.getIdBdaApiarioDestinazione());
								
								ListaDettaglioModello lista = new ListaDettaglioModello();
								lista.apidetmod = new ArrayList<Apidetmod>();
								lista.apidetmod.add(pp);
								
								  XStream xStream = new XStream();
								    String xml = "<![CDATA["+ xStream.toXML(lista.apidetmod).replaceAll("it.izs.apicoltura.apimovimentazione.ws.Apidetmod", "dettaglioModello")+"]]>";
						
								campiInput.put("xmlListaDettaglioModello", xml);
							}
							
							if(modello.getIdTipoMovimentazione()!=2)
							{
								campiInput.put("numAlveari", modello.getNumAlveariDaSpostare());
								campiInput.put("numSciami", modello.getNumSciamiDaSpostare());
								campiInput.put("numPacchiDapi", modello.getNumPacchiDaSpostare());
								campiInput.put("numApiRegine", modello.getNumRegineDaSpostare());
							}
						}
						else
						{
							campiInput.put("apiattAziendaCodice", modello.getCodiceAziendaOrigine());
							campiInput.put("dtUscita", sdf.format(modello.getDataMovimentazione())+"T00:00:00.0");
							campiInput.put("destApiattAziendaCodice", modello.getCodiceAziendaDestinazione());
							campiInput.put("numApiRegine", modello.getNumRegineDaSpostare());
						}
						
						
						if(Boolean.parseBoolean(ApplicationProperties.getProperty("comuneDestinazioneApiarioCompravendita")) && modello.getIdTipoMovimentazione()==1)
						{

							String sql = "select cod_comune, cod_provincia from comuni1 where id = ? ";
							PreparedStatement pst = db.prepareStatement(sql);
							pst.setInt(1, Integer.parseInt(context.getParameter("comuneDestinazione")));
							ResultSet rs = pst.executeQuery();
							String istat = null;
							String sigla_prov = null;
							if (rs.next())
							{
								istat = rs.getString(1);
								sigla_prov = rs.getString(2);
								if (!sigla_prov.equals(""))
								{
									sql = "select cod_provincia from lookup_province where code = ?::integer";
									pst = db.prepareStatement(sql);
									pst.setString(1, sigla_prov);
									rs = pst.executeQuery();
									if (rs.next())
										sigla_prov = rs.getString(1);
								}
							}
						
							campiInput.put("destComIstat", istat);
							campiInput.put("destProSigla", sigla_prov);
						}
						
						wsPost.setCampiInput(campiInput);
						wsPost.costruisciEnvelope(db);
						String response = wsPost.post(db, getUserId(context));
						idMovimentazioneInserita = utilsXML.getValoreNodoXML(response,(modello.getIdTipoMovimentazione()!=4)?("apimodmovId"):("apimovregId") );
						
						if(idMovimentazioneInserita==null || idMovimentazioneInserita.equals(""))
						{
							response = response.replaceAll("&lt;", "<");
							String errore = utilsXML.getValoreNodoXML(response,"result" );
							if(errore==null)
								errore = utilsXML.getValoreNodoXML(response,"faultstring" );
							throw new EccezioneDati("Errore nell'invio in bdn: " + errore);
						}
						
						//Fine invio in bdn
						
						//Se lo metto in bdn aggiorno gisa
						if(idMovimentazioneInserita!=null)
							modello.setIdBdn(Integer.parseInt(idMovimentazioneInserita));
						modello.setSincronizzato_bdn(true);
						modello.setSincronizzato_Da(getUserId(context));
						modello.setData_sincronizzazione(new Timestamp(System.currentTimeMillis()));
						modello.setStato(API_STATO_VALIDATO);
						modello.update(db);
						
						if (modello.getIdStabilimentoOrigine()>0)
						{
							String seql = "update apicoltura_apiari set num_alveari_effettivi =  ((case when num_alveari_effettivi is null then 0 else num_alveari_effettivi end-?)) where id = ? ";
							PreparedStatement pst = db.prepareStatement(seql);
							pst.setInt(1, modello.getNumApiariSpostati());
							pst.setInt(2, modello.getIdStabilimentoOrigine());
							pst.execute();
						}
						
						if (modello.getIdStabilimentoDestinazione()>0)
						{
							String seql = "update apicoltura_apiari set num_alveari_effettivi =  ((case when num_alveari_effettivi is null then 0 else num_alveari_effettivi end+?)) where id = ? ";
							PreparedStatement pst = db.prepareStatement(seql);
							pst.setInt(1, modello.getNumApiariSpostati());
							pst.setInt(2, modello.getIdStabilimentoDestinazione());
							pst.execute();
						}
					
					/*if (db.getAutoCommit()==false)
					{*/
					db.commit();
					//}
					
				}
				catch(Exception e)
				{
					db.rollback();
					e.printStackTrace();
					context.getRequest().setAttribute("Error", e.getMessage());
					context.getRequest().setAttribute("idApiarioScelto", modello.getIdStabilimentoOrigine());
					context.getRequest().setAttribute("tipoMovimentazione", LookupTipoMovimentazione.getSelectedValue(modello.getIdTipoMovimentazione()) );
					return executeCommandAddRichiesta(context);
					
				}
				finally
				{
					this.freeConnection(context, db);
				}
			
				context.getRequest().setAttribute("searchcodeCodiceAziendaSearch", modello.getCodiceAziendaOrigine());
				context.getRequest().setAttribute("progressivoApiarioSearch", modello.getProgressivoApiarioOrigine());
				
				return executeCommandSearch(context);
			}
			else
			{
				return executeCommandAdd(context);
			}


		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e.getMessage());
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}
	
	public String executeCommandMassivaApiRegine(ActionContext context)
	{
		OperatoreAction opAct = new OperatoreAction();
		
		ArrayList<String> esitiErroriParsingfileAL = new ArrayList<String>();
		StringBuffer  esitiErroriSB = null;
		StringBuffer esitiInsert = new StringBuffer("");
		
		String fullPath = null;
		String pathTemp = this.getPath(context, "acque_rete_import");
		File tempFold = new File(pathTemp);
		tempFold.mkdirs();
		
		try(Connection db = this.getConnection(context))
		{
			//questo mi scarica il contenuto del form (il file allegato) nella cartella
			HttpMultiPartParser multiPart = new HttpMultiPartParser();
			HashMap parts = multiPart.parseData(context.getRequest(), pathTemp);
			String codDocumento = upload(context, parts, pathTemp);
			if(codDocumento.equalsIgnoreCase("documentaleAllegatiError"))
				throw new Exception("Errore durante l'upload del file");
			
			//Vecchia gestione upload file: la rimango nel caso in cui dovesse servire per tipo_richiesta = dpat
			FileInfo fInfo = (FileInfo) parts.get("file1");
			String fileName = fInfo.getClientFileName();
		
			int idOperatore = Integer.parseInt((String)parts.get("idOperatoreMovMassiva"));
			context.getRequest().setAttribute("idOperatore", String.valueOf(idOperatore));

			Operatore operatore = new Operatore(db, idOperatore);
			
			if(parts.get("file1") instanceof FileInfo)
			{
				fullPath = pathTemp+fInfo.getClientFileName();
	
				org.apache.poi.ss.usermodel.Workbook wb = WorkbookFactory.create(new File(fullPath));
				HashMap<Integer,ModelloC> movToAdd = parseSheet(operatore,wb, db,esitiErroriParsingfileAL, context);
				esitiErroriSB = postProcessEsiti(esitiErroriParsingfileAL);
				
				Iterator<Integer> keys = movToAdd.keySet().iterator();
				while(keys.hasNext())
				{
					Integer riga = keys.next();
					ModelloC mov = movToAdd.get(riga);
					esitiInsert.append("<br/>RIGA " + riga + ": " + insert(mov,db,context));
				}
				
				wb.close();
				logImport(db, fInfo,(UserBean)context.getSession().getAttribute("User"), fullPath, operatore.getIdOperatore(),codDocumento, esitiInsert.toString(), esitiErroriSB.toString());
			}
			
			context.getRequest().setAttribute("esitiErroriParsingFile", esitiErroriSB);
			context.getRequest().setAttribute("esitiInsert", esitiInsert);
			
			context.getRequest().setAttribute("searchcodeCodiceAziendaSearch", operatore.getCodiceAzienda());
		}
		catch(Exception ex)
		{
			context.getRequest().setAttribute("Error", ex.getMessage());
			ex.printStackTrace();
		}
		
		//return opAct.executeCommandHome(context)+"ApicolturaOK";
		return executeCommandStoricoUploadFileApiRegine(context);
		
	}
	
	public String insert(ModelloC modello,Connection db,ActionContext context) throws Exception
	{
		String toRet = null;
		try
		{
			boolean validazioneImplicita = false;
			//Se destinazione movimentazione e' in regione 
			//Se destinazione movimentazione e' fuori regione e non e' stata richiesta la validazione dall'apicoltore
			//allora valida direttamente
			
			//Recuperiamo ASL per verificare se apiario e' fuori regione.
			Provincia p = new Provincia();
			p.getProvinciaBySigla(db, modello.getCodiceAziendaDestinazione().substring(3,5));
			
			ext.aspcfs.modules.apiari.base.ComuniAnagrafica c = new ext.aspcfs.modules.apiari.base.ComuniAnagrafica();
			int idAsl = c.getComuni(db, p.getIdProvincia()).get(0).getIdAsl();
				
			if(idAsl!=16 || (idAsl==16 && modello.isAttestazioneSanitaria()))
			{
				validazioneImplicita = true;
			}
			
			modello.setAttestazioneSanitaria(!validazioneImplicita);
			
			if(idAsl==16)
			{
				modello.setAccettazioneDestinatario(1);
			}
			else
			{
				modello.setAccettazioneDestinatario(3);
			}
			
			modello.insert(db);
			
			
			//Invio movimento in bdn
			System.out.println(" ------ INSERT MOVIMENTAZIONE API REGINE -----"); 
			
			WsPost wsPost = new WsPost(db, WsPost.ENDPOINT_API_REGINE,WsPost.AZIONE_INSERT);
			
			HashMap<String, Object> campiInput = new HashMap<>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			campiInput.put("apiattAziendaCodice", modello.getCodiceAziendaOrigine());
			campiInput.put("dtUscita", sdf.format(modello.getDataMovimentazione())+"T00:00:00.0");
			campiInput.put("destApiattAziendaCodice", modello.getCodiceAziendaDestinazione());
			campiInput.put("numApiRegine", modello.getNumRegineDaSpostare());
			wsPost.setCampiInput(campiInput);
			
			wsPost.costruisciEnvelope(db);
			String response = wsPost.post(db, getUserId(context));
			//Fine invio in bdn
			
			String idMovimentazioneInserita = utilsXML.getValoreNodoXML(response,"apimovregId" );
			if(idMovimentazioneInserita!=null && !idMovimentazioneInserita.equals(""))
			{
				toRet = "<br><font color='orange'> MOVIMENTAZIONE DI API REGINE CON DATA:  " + modello.getDataMovimentazione() + ", CODICE AZIENDA ORIGINE: " + modello.getCodiceAziendaOrigine() + ", CODICE AZIENDA DESTINAZIONE: " + modello.getCodiceAziendaDestinazione() + ", NUMERO API REGINE: " + modello.getNumRegineDaSpostare() + " INSERITA CON STATO 'NON SINCRONIZZATO' CON BDN PER ERRORI. CONTATTARE L'HELP-DESK.</font>";
				System.out.println("MOVIMENTAZIONE API REGINE INSERITA CON STATO 'NON SINCRONIZZATO' CON BDN PER ERRORI. DATA:  " + modello.getDataMovimentazione() + ", CODICE AZIENDA ORIGINE: " + modello.getCodiceAziendaOrigine() + ", CODICE AZIENDA DESTINAZIONE: " + modello.getCodiceAziendaDestinazione() + ", NUMERO API REGINE: " + modello.getNumRegineDaSpostare() );
				
				modello.setIdBdn(Integer.parseInt(idMovimentazioneInserita));
				modello.setSincronizzato_bdn(true);
				modello.setSincronizzato_Da(getUserId(context));
				modello.setData_sincronizzazione(new Timestamp(System.currentTimeMillis()));
				modello.sincronizzaBdn(db, getUserId(context), modello.getStato());
				
				toRet = "<br><font color='green'> MOVIMENTAZIONE DI API REGINE CON DATA:  " + modello.getDataMovimentazione() + ", CODICE AZIENDA ORIGINE: " + modello.getCodiceAziendaOrigine() + ", CODICE AZIENDA DESTINAZIONE: " + modello.getCodiceAziendaDestinazione() + ", NUMERO API REGINE: " + modello.getNumRegineDaSpostare() + " INSERITA CORRETTAMENTE</font>";
				System.out.println("MOVIMENTAZIONE API REGINE INSERITA. DATA:  " + modello.getDataMovimentazione() + ", CODICE AZIENDA ORIGINE: " + modello.getCodiceAziendaOrigine() + ", CODICE AZIENDA DESTINAZIONE: " + modello.getCodiceAziendaDestinazione() + ", NUMERO API REGINE: " + modello.getNumRegineDaSpostare() );
				
			}
			else
			{
				String errore = utilsXML.getValoreNodoXML_new(response,"utenteMsg" );
				errore = (errore!=null)?(errore):("");
				toRet = "<br><font color='orange'> MOVIMENTAZIONE DI API REGINE CON DATA:  " + modello.getDataMovimentazione() + ", CODICE AZIENDA ORIGINE: " + modello.getCodiceAziendaOrigine() + ", CODICE AZIENDA DESTINAZIONE: " + modello.getCodiceAziendaDestinazione() + ", NUMERO API REGINE: " + modello.getNumRegineDaSpostare() + " INSERITA CORRETTAMENTE IN BDA-r. INVIO IN BDN FALLITO PER IL SEGUENTE ERRORE: " + errore + ".</font>";
				System.out.println("MOVIMENTAZIONE API REGINE INSERITA IN BDA-R MA NON IN BDN PER IL SEGUENTE ERRORE: " + errore + ". DATA:  " + modello.getDataMovimentazione() + ", CODICE AZIENDA ORIGINE: " + modello.getCodiceAziendaOrigine() + ", CODICE AZIENDA DESTINAZIONE: " + modello.getCodiceAziendaDestinazione() + ", NUMERO API REGINE: " + modello.getNumRegineDaSpostare() );
			}
			if (db.getAutoCommit()==false)
			{
				db.commit();
			}
		}
		
		catch(ServerSOAPFaultException | it.izs.apicoltura.apimovimentazione.ws.BusinessWsException_Exception e)
		{
			logger.error("Errore nell'invio in BDN : "+e.getMessage());
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

			String msg = "INVIO MOVIMENTAZIONE API REGINE DA AZIENDA " + modello.getCodiceAziendaOrigine()+"ESITO : KO . DATA INVIO :"+timestamp+". INVIATA DA :"+thisUser.getUsername()+". ERRORE : "+e.getMessage();
			
			String errore = "" ;
			if (e instanceof it.izs.apicoltura.apimovimentazione.ws.BusinessWsException_Exception)
			{
				it.izs.apicoltura.apimovimentazione.ws.BusinessWsException_Exception ee = (it.izs.apicoltura.apimovimentazione.ws.BusinessWsException_Exception)e;
				errore = ee.getFaultInfo().getMessage()+" : "+ee.getFaultInfo().getResult().getErrore();

				for (it.izs.apicoltura.apimovimentazione.ws.FieldError error : ee.getFaultInfo().getResult().getErrors())
				{
					errore +="["+error.getField()+": "+error.getMessage()+ "]" ;

				}
			}
			else
			{
				if (e instanceof it.izs.bdn.anagrafica.ws.BusinessWsException_Exception)
				{

					it.izs.bdn.anagrafica.ws.BusinessWsException_Exception ee =(it.izs.bdn.anagrafica.ws.BusinessWsException_Exception)e;
					errore = ee.getFaultInfo().getMessage()+" : "+ee.getFaultInfo().getResult().getErrore();

					for (it.izs.bdn.anagrafica.ws.FieldError error : ee.getFaultInfo().getResult().getErrors())
					{
						errore +="["+error.getField()+": "+error.getMessage()+ "]" ;

					}
				}
				else
				{
					if (e instanceof ServerSOAPFaultException)
					{

						ServerSOAPFaultException ee =(ServerSOAPFaultException)e;
						errore = ee.getFault().getFaultString();
					}

					else
						errore = e.getMessage();
				}
			}
			try
			{
				sendMailIzsm(context.getRequest(), msg+". L'errore e il seguente :"+errore, "##GISA_API_WS_BDN_KO##", "gisahelpdesk@usmail.it");
			}
			catch(Exception e2)
			{
				logger.error("Errore nell'invio della mail");
			}
			
			toRet = "<br><font color='red'> MOVIMENTAZIONE DI API REGINE CON DATA:  " + modello.getDataMovimentazione() + ", CODICE AZIENDA ORIGINE: " + modello.getCodiceAziendaOrigine() + ", CODICE AZIENDA DESTINAZIONE: " + modello.getCodiceAziendaDestinazione() + ", NUMERO API REGINE: " + modello.getNumRegineDaSpostare() + " NON INSERITA CORRETTAMENTE : <b>"+errore+"</b> </font>";
			System.out.println("MOVIMENTAZIONE API REGINE NON INSERITA. DATA:  " + modello.getDataMovimentazione() + ", CODICE AZIENDA ORIGINE: " + modello.getCodiceAziendaOrigine() + ", CODICE AZIENDA DESTINAZIONE: " + modello.getCodiceAziendaDestinazione() + ", NUMERO API REGINE: " + modello.getNumRegineDaSpostare() );
			
		}
		 
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("MOVIMENTAZIONE API REGINE NON INSERITA. DATA:  " + modello.getDataMovimentazione() + ", CODICE AZIENDA ORIGINE: " + modello.getCodiceAziendaOrigine() + ", CODICE AZIENDA DESTINAZIONE: " + modello.getCodiceAziendaDestinazione() + ", NUMERO API REGINE: " + modello.getNumRegineDaSpostare() );
			String messaggio = ex.getMessage();
			if(ex.getMessage().contains("duplicati_movimentazioni"))
			{
				messaggio = "La movimentazione esiste gia";
			
			}
			toRet = "<br><font color='red'> MOVIMENTAZIONE DI API REGINE CON DATA:  " + modello.getDataMovimentazione() + ", CODICE AZIENDA ORIGINE: " + modello.getCodiceAziendaOrigine() + ", CODICE AZIENDA DESTINAZIONE: " + modello.getCodiceAziendaDestinazione() + ", NUMERO API REGINE: " + modello.getNumRegineDaSpostare() + " NON INSERITA CORRETTAMENTE : <b>"+messaggio+"</b> </font>";
		}
		
		
		
		
		
		return toRet;
		
	}

	public String executeCommandModifica(ActionContext context)
	{

		Connection db = null;
		try 
		{
			db = this.getConnection(context);
			
			long dataMilli = System.currentTimeMillis(); 
			java.util.Date data = new Date( dataMilli );
			SimpleDateFormat forma=new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
			
			ModelloC modelloVecchio = new ModelloC(db, Integer.parseInt(context.getParameter("id")));
			
			//Carico il nuovo modello
			ModelloC modelloNuovo = (ModelloC)context.getRequest().getAttribute("Movimentazione");
			
			if(modelloVecchio.getIdTipoMovimentazione()==2)
				modelloNuovo.setCodiceAziendaDestinazione(modelloVecchio.getCodiceAziendaDestinazione());
			modelloNuovo.setIdBdn(modelloVecchio.getIdBdn());
			modelloNuovo.setId(Integer.parseInt(context.getParameter("id")));
			modelloNuovo.setStato(API_STATO_DA_NOTIFICARE);
			modelloNuovo.setModified_by(getUserId(context));
			modelloNuovo.setSincronizzato_bdn(false);
			
			modelloNuovo.setSigla_prov_comune_dest(context.getParameter("provinciaUbicazioneIn"));
			modelloNuovo.setComune_dest(context.getParameter("codiceApiarioDestinazione"));
			modelloNuovo.setIndirizzo_dest(context.getParameter("indirizzoUbicazioneIn"));
			
			modelloNuovo.setIndirizzo_dest(context.getParameter("indirizzoUbicazioneIn"));
			if(modelloNuovo.getIdTipoMovimentazione()==4 || modelloNuovo.getIdTipoMovimentazione()==1)
				modelloNuovo.setComune_dest(context.getParameter("comuneIn"));	
			else
				modelloNuovo.setComune_dest(context.getParameter("codiceApiarioDestinazione"));
			if(context.getParameter("indirizzoIn")!=null )
				modelloNuovo.setIndirizzo_dest(context.getParameter("indirizzoIn"));
			
			modelloNuovo.setLatitudine_dest(context.getParameter("latitudine_dest"));
			modelloNuovo.setLongitudine_dest(context.getParameter("longitudine_dest"));
			modelloNuovo.setData_modello(modelloVecchio.getData_modello());
			
			modelloNuovo.setNumAlveariDaSpostare(context.getParameter("numAlveariDaSpostare"));
			modelloNuovo.setNumPacchiDaSpostare(context.getParameter("numPacchiDaSpostare"));
			modelloNuovo.setNumRegineDaSpostare(context.getParameter("numRegineDaSpostare"));
			modelloNuovo.setNumSciamiDaSpostare(context.getParameter("numSciamiDaSpostare"));
			modelloNuovo.setNumero_modello(context.getParameter("numero_modello"));
			modelloNuovo.setDenominazioneApicoltore((context.getParameter("ragioneSocialeIn")!=null )?(context.getParameter("ragioneSocialeIn")):(null));
			modelloNuovo.setCfPartitaIvaApicoltore((context.getParameter("cfPartitaIvaAziendaDestinazione")!=null )?(context.getParameter("cfPartitaIvaAziendaDestinazione")):(null));
			
			if( modelloVecchio.getIdTipoMovimentazione()==2)
			{
				modelloNuovo.setProgressivoApiarioDestinazione(context.getParameter("progressivoApiarioDestinazione"));
			}
			
			if(context.getParameter("indirizzoUbicazioneIn")!=null )
				modelloNuovo.setIndirizzo_dest(context.getParameter("indirizzoUbicazioneIn"));
			String istat = "" ;
			String sigla_prov = "";
			if(modelloNuovo.getIdTipoMovimentazione()==3 || modelloNuovo.getIdTipoMovimentazione()==2)
			{
				String sql = "select cod_comune, cod_provincia from comuni1 where nome ilike ? ";
				PreparedStatement pst = db.prepareStatement(sql);
				pst.setString(1, modelloNuovo.getComune_dest());
				ResultSet rs = pst.executeQuery();
				if (rs.next())
				{
					istat = rs.getString(1);
					sigla_prov = rs.getString(2);
					if (!sigla_prov.equals(""))
					{
						sql = "select cod_provincia from lookup_province where code = ?::integer";
						pst = db.prepareStatement(sql);
						pst.setString(1, sigla_prov);
						rs = pst.executeQuery();
						if (rs.next())
							sigla_prov = rs.getString(1);
					}
				}
			}
			
			modelloNuovo.setSigla_prov_comune_dest((context.getParameter("provinciaUbicazioneIn")!=null )?(context.getParameter("provinciaUbicazioneIn")):(null));
			
			if( context.getParameter("comuneDestinazione")!=null)
			{
				ext.aspcfs.modules.apiari.base.ComuniAnagrafica com = new ext.aspcfs.modules.apiari.base.ComuniAnagrafica(db, Integer.parseInt(context.getParameter("comuneDestinazione")));
				modelloNuovo.setComune_dest(com.getDescrizione());	
				Provincia p = new Provincia().getProvinciaByComune(db, com.getDescrizione());
				modelloNuovo.setSigla_prov_comune_dest(p.getCodProvincia());
			}
			 
			//compravendita
			if (modelloNuovo.getIdTipoMovimentazione()==1)
			{
				modelloNuovo.setNumApiariSpostati(modelloNuovo.getNumApiariOrigine());
			}
			
			StabilimentoList listStab = new StabilimentoList();
			listStab.setCun(modelloNuovo.getCodiceAziendaOrigine());
			listStab.setProgressivoApiarioFiltro(modelloNuovo.getProgressivoApiarioOrigine());
			listStab.buildList(db);
			
			if(listStab.size()>0)
			{
				modelloNuovo.setIdStabilimentoOrigine(((Stabilimento)listStab.get(0)).getIdStabilimento());
				/*setto l'asl dell'apicoltore di Origine*/
				modelloNuovo.setId_asl_apicoltore_origine(((Stabilimento)listStab.get(0)).getOperatore().getIdAsl());
			}
			
			
			if(modelloVecchio.getIdTipoMovimentazione()==1 || modelloVecchio.getIdTipoMovimentazione()==2)
			{
				StabilimentoList listStabDest = new StabilimentoList();
				listStabDest.setCun(modelloNuovo.getCodiceAziendaDestinazione());
				listStabDest.setProgressivoApiarioFiltro(modelloNuovo.getProgressivoApiarioDestinazione());
				listStabDest.buildList(db);
				
				if(listStabDest.size()>0)
				{
					modelloNuovo.setApiarioDestinazione(new Stabilimento(db, ((Stabilimento)listStabDest.get(0)).getIdStabilimento()));
				}
			
			
				listStab = new StabilimentoList();
				listStab.setCun(modelloNuovo.getCodiceAziendaDestinazione());
				listStab.setProgressivoApiarioFiltro(modelloNuovo.getProgressivoApiarioDestinazione());
				listStab.buildList(db);
				
				if(listStab.size()>0 && modelloNuovo.getIdTipoMovimentazione()==2)
					modelloNuovo.setIdStabilimentoDestinazione(((Stabilimento)listStab.get(0)).getIdStabilimento());
			}
			
			boolean validazioneImplicita = false;
			//Se destinazione movimentazione e' in regione 
			//Se destinazione movimentazione e' fuori regione e non e' stata richiesta la validazione dall'apicoltore
			//allora valida direttamente
			
			//Recuperiamo ASL per verificare se apiario e' fuori regione.
			ext.aspcfs.modules.apiari.base.ComuniAnagrafica c = new ext.aspcfs.modules.apiari.base.ComuniAnagrafica(db,modelloNuovo.getComune_dest());
			int idAsl = c.getIdAslByComune(db, modelloNuovo.getComune_dest());
				
			
			if(idAsl!=16 || (idAsl==16 && context.getParameter("richiestaValidazione")==null))
			{
				validazioneImplicita = true;
			}
			
			
			listStab = new StabilimentoList();
			listStab.setCun(modelloNuovo.getCodiceAziendaOrigine());
			listStab.setProgressivoApiarioFiltro(modelloNuovo.getProgressivoApiarioOrigine());
			listStab.buildList(db);
			
			if(listStab.size()>0)
				modelloNuovo.setIdStabilimentoOrigine(((Stabilimento)listStab.get(0)).getIdStabilimento());
			
			if(modelloNuovo.getIdTipoMovimentazione()!=2 && idAsl!=16)
			{
				modelloNuovo.setAccettazioneDestinatario(1);
			}
			else
			{
				modelloNuovo.setAccettazioneDestinatario(3);
			}
			
			boolean isValid = this.validateObject(context, db, modelloNuovo);
			if (isValid ) 
			{
				try 
				{
					
					modelloVecchio.storico(db);
					
					
					if(modelloVecchio.getIdBdn()>0)
					{
						if(modelloNuovo.getIdTipoMovimentazione()!=4)
						{
							WsApiAnagraficaModello serviceMovimentazioni = new WsApiAnagraficaModello();
							
							List<Apimodmov> modelli = serviceMovimentazioni.search(modelloVecchio, db, getUserId(context));
							Apimodmov movimentazione = null;
							if(modelli.size()==1)
								movimentazione = modelli.get(0);
							else
								throw new Exception("Nessuna o piu movimentazioni trovate");
							
							try 
							{
	
								GregorianCalendar cal = new GregorianCalendar();
								
								cal.setTime(modelloNuovo.getData_modello());
								XMLGregorianCalendar dtModello = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
	
								movimentazione.setDtModello(dtModello);	
								
								cal.setTime(modelloNuovo.getDataMovimentazione());
								XMLGregorianCalendar dtUscita = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
	
								movimentazione.setDtUscita(dtUscita);	
	
							} 
							catch (DatatypeConfigurationException e) 
							{
								e.printStackTrace();
							}
							
							if(modelloVecchio.getIdTipoMovimentazione()==1 || modelloVecchio.getIdTipoMovimentazione()==2)
							{
								movimentazione.setAttDestPropCognNome(modelloNuovo.getApiarioDestinazione().getOperatore().getRappLegale().getCognome() + " " + modelloNuovo.getApiarioDestinazione().getOperatore().getRappLegale().getNome());
								movimentazione.setAttDestPropIdFiscale(modelloNuovo.getApiarioDestinazione().getOperatore().getRappLegale().getCodFiscale());
								movimentazione.setDestApiattAziendaCodice(modelloNuovo.getApiarioDestinazione().getOperatore().getCodiceAzienda());
								movimentazione.setDestApiattDenominazione(modelloNuovo.getApiarioDestinazione().getOperatore().getRagioneSociale());
								movimentazione.setNumModello(modelloNuovo.getNumero_modello());
							}
							
							//Aggiornamento in BDN
							String idMovimentazioneAggiornata = null;
							String response = null;
							
							if(modelloVecchio.getIdTipoMovimentazione()==2)
							{
								
								
								WsPost wsPost = new WsPost(db, WsPost.ENDPOINT_API_MOVIMENTAZIONI,WsPost.AZIONE_GETBYPK);
								
								HashMap<String, Object> campiInput = new HashMap<>();
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								
								campiInput.put("apimodmovId",movimentazione.getApimodmovId());
								
								wsPost.setCampiInput(campiInput);
								wsPost.costruisciEnvelope(db);
								response = wsPost.post(db, getUserId(context));
								String idMovimentazioneDettaglio = utilsXML.getValoreNodoXML(response,"apidetmodId");
								
								
								
								wsPost = new WsPost(db, WsPost.ENDPOINT_API_MOVIMENTAZIONI_DETTAGLIO_MODELLO,WsPost.AZIONE_UPDATE);
								
								campiInput = new HashMap<>();
								
								campiInput.put("apimodmovNumModello",modelloNuovo.getNumero_modello());
								campiInput.put("apimodmovApiProgressivo",movimentazione.getApiProgressivo());
								campiInput.put("numAlveari",modelloNuovo.getNumAlveariDaSpostare());
								campiInput.put("numPacchiDapi",modelloNuovo.getNumPacchiDaSpostare());
								campiInput.put("numApiRegine",modelloNuovo.getNumRegineDaSpostare());
								campiInput.put("numSciami",modelloNuovo.getNumSciamiDaSpostare());
								campiInput.put("apimodmovDtModello",sdf.format(modelloNuovo.getData_modello())+"T00:00:00.0");
								campiInput.put("apimodmovDtUscita",sdf.format(modelloNuovo.getDataMovimentazione())+"T00:00:00.0");
								campiInput.put("apimodmovId",movimentazione.getApimodmovId());
								campiInput.put("apidetmodId",idMovimentazioneDettaglio);
								campiInput.put("destApiProgressivo",modelloNuovo.getProgressivoApiarioDestinazione());
								campiInput.put("destApiComDescrizione",modelloNuovo.getComune_dest());
								campiInput.put("destApiIndirizzo",modelloNuovo.getIndirizzo_dest());
								campiInput.put("destApiLatitudine",modelloNuovo.getLatitudine_dest());
								campiInput.put("destApiLongitudine",modelloNuovo.getLongitudine_dest());
								campiInput.put("destApiComIstat",istat);
								campiInput.put("destApiProSigla",sigla_prov);
								campiInput.put("apimodmovProvApiattCodice",modelloNuovo.getCodiceAziendaOrigine());
								campiInput.put("apimodmovDestApiattCodice",modelloNuovo.getCodiceAziendaDestinazione());
								
								
								
								wsPost.setCampiInput(campiInput);
								wsPost.costruisciEnvelope(db);
								response = wsPost.post(db, getUserId(context));
								idMovimentazioneAggiornata = utilsXML.getValoreNodoXML(response,"apidetmodId");
							}
							else
							{
								WsPost wsPost = new WsPost(db, WsPost.ENDPOINT_API_MOVIMENTAZIONI,WsPost.AZIONE_UPDATE);
								
								HashMap<String, Object> campiInput = new HashMap<>();
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								
								campiInput.put("numAlveari",modelloNuovo.getNumAlveariDaSpostare());
								campiInput.put("numPacchiDapi",modelloNuovo.getNumPacchiDaSpostare());
								campiInput.put("numApiRegine",modelloNuovo.getNumRegineDaSpostare());
								campiInput.put("numSciami",modelloNuovo.getNumSciamiDaSpostare());
								campiInput.put("apiattAziendaCodice",modelloNuovo.getCodiceAziendaOrigine());
								
								
								if(modelloVecchio.getIdTipoMovimentazione()==1 )
								{
									campiInput.put("destApiattAziendaCodice",modelloNuovo.getApiarioDestinazione().getOperatore().getCodiceAzienda());
									campiInput.put("destApiattAziendaCodice",modelloNuovo.getApiarioDestinazione().getOperatore().getCodiceAzienda());
									campiInput.put("destApiProgressivo",modelloNuovo.getProgressivoApiarioDestinazione());
								}
								else if(modelloVecchio.getIdTipoMovimentazione()==3)
								{
									campiInput.put("destAziendaIdFiscale",modelloNuovo.getCfPartitaApicoltore());
									campiInput.put("destAziendaDenominazione",modelloNuovo.getDenominazioneApicoltore());
									campiInput.put("destAziendaIndirizzo",modelloNuovo.getIndirizzo_dest());
									campiInput.put("destAziendaComProSigla",modelloNuovo.getSigla_prov_comune_dest());
									campiInput.put("destAziendaComIstat",istat);
								}
								campiInput.put("numModello",modelloNuovo.getNumero_modello());
								campiInput.put("dtModello",sdf.format(modelloNuovo.getData_modello())+"T00:00:00.0");
								campiInput.put("dtUscita",sdf.format(modelloNuovo.getDataMovimentazione())+"T00:00:00.0");
								campiInput.put("destAziendaComProSigla",modelloNuovo.getSigla_prov_comune_dest());
								campiInput.put("apimodmovId",movimentazione.getApimodmovId());
								campiInput.put("apiProgressivo",movimentazione.getApiProgressivo());
								campiInput.put("destApiComDescrizione",modelloNuovo.getComune_dest());
								
								String sql = "select cod_comune from comuni1 where nome ilike ? ";
								PreparedStatement pst = db.prepareStatement(sql);
								pst.setString(1, modelloNuovo.getComune_dest());
								ResultSet rs = pst.executeQuery();
								String istat2 = null;
								if (rs.next())
									istat2 = rs.getString(1);
								
								campiInput.put("destComIstat",istat2);
								campiInput.put("destProSigla", modelloNuovo.getSigla_prov_comune_dest());
								
								
								campiInput.put("destApiIndirizzo",modelloNuovo.getIndirizzo_dest());
								campiInput.put("attestazioneSanitaria",(modelloNuovo.isAttestazioneSanitaria())?("S"):("N"));
								campiInput.put("dtStatoRichiesta",sdf.format(new Timestamp(movimentazione.getDtStatoRichiesta().getMillisecond()))+"T00:00:00.0");
								if(modelloNuovo.getIdTipoMovimentazione()==1)
									campiInput.put("apimotuscCodice", "C");
								else if(modelloNuovo.getIdTipoMovimentazione()==2)
									campiInput.put("apimotuscCodice", "N");
								else if(modelloNuovo.getIdTipoMovimentazione()==3)
									campiInput.put("apimotuscCodice", "I");
								
								wsPost.setCampiInput(campiInput);
								wsPost.costruisciEnvelope(db);
								response = wsPost.post(db, getUserId(context));
								response = response.replaceAll("&lt;","<");
								response = response.replaceAll("&gt;",">");
								idMovimentazioneAggiornata = utilsXML.getValoreNodoXML(response,"apimodmovId");
							}
							
							if(idMovimentazioneAggiornata==null || idMovimentazioneAggiornata.equals(""))
							{
								String messaggio = utilsXML.getValoreNodoXML_new(response,"utenteMsg");
								if(messaggio==null)
									messaggio = utilsXML.getValoreNodoXML(response,"faultstring");
								messaggio=messaggio.replaceAll("&amp;apos;", "'");
								throw new EccezioneDati("Errore nell'invio in bdn: " + ((messaggio!=null)?(messaggio):("")) );
							}
							
							modelloNuovo.setSincronizzato_bdn(true);
							modelloNuovo.setSincronizzato_Da(getUserId(context));
							modelloNuovo.setData_sincronizzazione(new Timestamp(System.currentTimeMillis()));
							modelloNuovo.setStato(API_STATO_VALIDAZIONE_NON_RICHIESTA);
						}
						else
						{
							modelloNuovo.setSincronizzato_bdn(false);
						}
						
						if (modelloNuovo.getIdStabilimentoOrigine()>0)
						{
							String seql = "update apicoltura_apiari set num_alveari_effettivi =  ((case when num_alveari_effettivi is null then 0 else num_alveari_effettivi end-?)) where id = ? ";
							PreparedStatement pst = db.prepareStatement(seql);
							pst.setInt(1, modelloNuovo.getNumApiariSpostati());
							pst.setInt(2, modelloNuovo.getIdStabilimentoOrigine());
							pst.execute();
						}
						
						if (modelloNuovo.getIdStabilimentoDestinazione()>0)
						{
							String seql = "update apicoltura_apiari set num_alveari_effettivi =  ((case when num_alveari_effettivi is null then 0 else num_alveari_effettivi end+?)) where id = ? ";
							PreparedStatement pst = db.prepareStatement(seql);
							pst.setInt(1, modelloNuovo.getNumApiariSpostati());
							pst.setInt(2, modelloNuovo.getIdStabilimentoDestinazione());
							pst.execute();
						}
					}
					else
					{
						//Invio movimento in bdn
						System.out.println(" ------ INSERT MOVIMENTAZIONE -----"); 
						
						WsPost wsPost = new WsPost(db, (modelloNuovo.getIdTipoMovimentazione()!=4)?(WsPost.ENDPOINT_API_MOVIMENTAZIONI):(WsPost.ENDPOINT_API_REGINE),WsPost.AZIONE_INSERT);
						
						HashMap<String, Object> campiInput = new HashMap<>();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						
						if(modelloNuovo.getIdTipoMovimentazione()!=4)
						{
							campiInput.put("apiProgressivo", modelloNuovo.getProgressivoApiarioOrigine());
							campiInput.put("apiattAziendaCodice", modelloNuovo.getCodiceAziendaOrigine());
							campiInput.put("numModello", modelloNuovo.getNumero_modello());
							if(modelloNuovo.getIdTipoMovimentazione()!=3)
							{
								campiInput.put("attestazioneSanitaria", (modelloNuovo.isAttestazioneSanitaria())?("S"):("N"));
								campiInput.put("statoRichiestaCodice", (modelloNuovo.isAttestazioneSanitaria())?("I"):("C"));
								campiInput.put("dtStatoRichiesta", sdf.format(new Timestamp(dataMilli) )+"T00:00:00.0");
							}
							campiInput.put("destApiattAziendaCodice", modelloNuovo.getCodiceAziendaDestinazione());
							campiInput.put("dtModello", sdf.format(modelloNuovo.getData_modello())+"T00:00:00.0");
							campiInput.put("dtUscita", sdf.format(modelloNuovo.getDataMovimentazione())+"T00:00:00.0");
							
							if(modelloNuovo.getIdTipoMovimentazione()==1)
								campiInput.put("apimotuscCodice", "C");
							else if(modelloNuovo.getIdTipoMovimentazione()==2)
								campiInput.put("apimotuscCodice", "N");
							else
								campiInput.put("apimotuscCodice", "I");
							
							istat = "" ;
							if(modelloNuovo.getIdTipoMovimentazione()==2 || modelloNuovo.getIdTipoMovimentazione()==3)
							{
								String sql = "select cod_comune from comuni1 where nome ilike ? ";
								PreparedStatement pst = db.prepareStatement(sql);
								pst.setString(1, modelloNuovo.getComune_dest());
								ResultSet rs = pst.executeQuery();
								if (rs.next())
									istat = rs.getString(1);
							}
								
							if(modelloNuovo.getIdTipoMovimentazione()==3)
							{
								campiInput.put("destAziendaComIstat", istat);
								campiInput.put("destAziendaComProSigla", modelloNuovo.getSigla_prov_comune_dest());
								campiInput.put("destAziendaIndirizzo", modelloNuovo.getIndirizzo_dest());
								campiInput.put("destAziendaIdFiscale", modelloNuovo.getCfPartitaApicoltore());
								campiInput.put("destAziendaDenominazione", modelloNuovo.getDenominazioneApicoltore());
								campiInput.put("recuperoMaterialeBiologico", modelloNuovo.getRecuperoMaterialeBiologico());
							}
								
							if(modelloNuovo.getIdTipoMovimentazione()==2)
							{
								Apidetmod pp = null;
								
								pp = new Apidetmod();
								pp.setDestApiProgressivo( Integer.parseInt(modelloNuovo.getProgressivoApiarioDestinazione()));
								pp.setDestApiComIstat(istat);
								pp.setDestApiProSigla(modelloNuovo.getSigla_prov_comune_dest());
								pp.setDestApiComProSigla(modelloNuovo.getSigla_prov_comune_dest());
								pp.setDestApiIndirizzo(modelloNuovo.getIndirizzo_dest());
								pp.setNumAlveari(modelloNuovo.getNumApiariSpostati());
								pp.setNumSciami(0);
								pp.setNumPacchiDapi(0);
								pp.setNumApiRegine(modelloNuovo.getNumRegineDaSpostare());
								pp.setApidetmodId(0);
								pp.setDestApiComDescrizione(modelloNuovo.getComune_dest());
								pp.setDestApiLatitudine(modelloNuovo.getLatitudine_dest());
								pp.setDestApiLongitudine(modelloNuovo.getLongitudine_dest());
								pp.setDestApiId(modelloNuovo.getIdBdaApiarioDestinazione());
								
								ListaDettaglioModello lista = new ListaDettaglioModello();
								lista.apidetmod = new ArrayList<Apidetmod>();
								lista.apidetmod.add(pp);
								
								  XStream xStream = new XStream();
								    String xml = "<![CDATA["+ xStream.toXML(lista.apidetmod).replaceAll("it.izs.apicoltura.apimovimentazione.ws.Apidetmod", "dettaglioModello")+"]]>";
						
								campiInput.put("xmlListaDettaglioModello", xml);
							}
							
							if(modelloNuovo.getIdTipoMovimentazione()!=2)
							{
								campiInput.put("numAlveari", modelloNuovo.getNumAlveariDaSpostare());
								campiInput.put("numSciami", modelloNuovo.getNumSciamiDaSpostare());
								campiInput.put("numPacchiDapi", modelloNuovo.getNumPacchiDaSpostare());
								campiInput.put("numApiRegine", modelloNuovo.getNumRegineDaSpostare());
							}
						}
						else
						{
							campiInput.put("apiattAziendaCodice", modelloNuovo.getCodiceAziendaOrigine());
							campiInput.put("dtUscita", sdf.format(modelloNuovo.getDataMovimentazione())+"T00:00:00.0");
							campiInput.put("destApiattAziendaCodice", modelloNuovo.getCodiceAziendaDestinazione());
							campiInput.put("numApiRegine", modelloNuovo.getNumRegineDaSpostare());
						}
						
						wsPost.setCampiInput(campiInput);
						wsPost.costruisciEnvelope(db);
						String response = wsPost.post(db, getUserId(context));
						String idMovimentazioneInserita = utilsXML.getValoreNodoXML(response,(modelloNuovo.getIdTipoMovimentazione()!=4)?("apimodmovId"):("apimovregId") );
						
						if(idMovimentazioneInserita==null || idMovimentazioneInserita.equals(""))
						{
							response = response.replaceAll("&lt;", "<");
							String errore = utilsXML.getValoreNodoXML_new(response,"utenteMsg" );
							if(errore==null)
							{
								errore = (errore!=null)?(errore):(utilsXML.getValoreNodoXML(response,"message" ));
								errore = (errore!=null)?(errore):("");
							}
							throw new EccezioneDati("Errore nell'invio in bdn: " + errore);
						}
						
						//Fine invio in bdn
						
						//Se lo metto in bdn aggiorno gisa
						if(idMovimentazioneInserita!=null)
							modelloNuovo.setIdBdn(Integer.parseInt(idMovimentazioneInserita));
						modelloNuovo.setSincronizzato_bdn(true);
						modelloNuovo.setSincronizzato_Da(getUserId(context));
						modelloNuovo.setData_sincronizzazione(new Timestamp(System.currentTimeMillis()));
						modelloNuovo.update(db);
						
						if (modelloNuovo.getIdStabilimentoOrigine()>0)
						{
							String seql = "update apicoltura_apiari set num_alveari_effettivi =  ((case when num_alveari_effettivi is null then 0 else num_alveari_effettivi end-?)) where id = ? ";
							PreparedStatement pst = db.prepareStatement(seql);
							pst.setInt(1, modelloNuovo.getNumApiariSpostati());
							pst.setInt(2, modelloNuovo.getIdStabilimentoOrigine());
							pst.execute();
						}
						
						if (modelloNuovo.getIdStabilimentoDestinazione()>0)
						{
							String seql = "update apicoltura_apiari set num_alveari_effettivi =  ((case when num_alveari_effettivi is null then 0 else num_alveari_effettivi end+?)) where id = ? ";
							PreparedStatement pst = db.prepareStatement(seql);
							pst.setInt(1, modelloNuovo.getNumApiariSpostati());
							pst.setInt(2, modelloNuovo.getIdStabilimentoDestinazione());
							pst.execute();
						}
					}
					
					modelloNuovo.update(db);
					
					if (db.getAutoCommit()==false)
					{
						db.commit();
					}
					
				}
				catch (it.izs.apicoltura.apimovimentazione.ws.BusinessWsException_Exception e) 
				{
					e.printStackTrace();
					String errore ="";
					
					 errore = e.getFaultInfo().getMessage()+" : "+e.getFaultInfo().getResult().getErrore();
					
					for (it.izs.apicoltura.apimovimentazione.ws.FieldError error : e.getFaultInfo().getResult().getErrors())
					{
						errore +="["+error.getField()+": "+error.getMessage()+ "]" ;
			
					}
					
					LookupList LookupTipoMovimentazione = new LookupList(db,"lookup_apicoltura_tipo_movimentazione");
					context.getRequest().setAttribute("Error", e);
					context.getRequest().setAttribute("tipoMovimentazione", LookupTipoMovimentazione.getSelectedValue(modelloNuovo.getIdTipoMovimentazione())  );
					context.getRequest().setAttribute("idMovimentazione", modelloNuovo.getId()+"");
					context.getRequest().setAttribute("idApiario", modelloNuovo.getIdStabilimentoOrigine()+"");
					context.getRequest().setAttribute("errore", errore);
					return executeCommandModificaMovimentazioni(context);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					LookupList LookupTipoMovimentazione = new LookupList(db,"lookup_apicoltura_tipo_movimentazione");
					context.getRequest().setAttribute("Error", e.getMessage());
					context.getRequest().setAttribute("tipoMovimentazione", LookupTipoMovimentazione.getSelectedValue(modelloNuovo.getIdTipoMovimentazione())  );
					context.getRequest().setAttribute("idMovimentazione", modelloNuovo.getId()+"");
					context.getRequest().setAttribute("idApiario", modelloNuovo.getIdStabilimentoOrigine()+"");
					context.getRequest().setAttribute("errore", e.getMessage());
					return executeCommandModificaMovimentazioni(context);
				}
				finally
				{
					this.freeConnection(context, db);
				}
			
				Operatore op = new Operatore(db, modelloNuovo.getCodiceAziendaOrigine(),null,null,null,null);
				context.getRequest().setAttribute("searchcodeCodiceAziendaSearch", op.getCodiceAzienda());
				
				return executeCommandSearch(context);
				
			}
			else
			{
				return executeCommandAdd(context);
			}


		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e.getMessage());
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

	}

	
	
	
	public String executeCommandValidaMovimentazione(ActionContext context)   {

		Connection db = null;
		try {
			db = this.getConnection(context);



			if (db.getAutoCommit()==true)
				db.setAutoCommit(false);

			int  idMovimentazione = Integer.parseInt(context.getParameter("idMovimentazione"));
			
			ModelloC movimentazione = new ModelloC(db,idMovimentazione);
		
			WsApiAnagraficaModello serviceMovimentazioni = new WsApiAnagraficaModello();
			try
			{
				//L'update della validazione non lo facciamo, come da mail di Di Donato girata da Avallone il 15/01/2019 in bdn non e' possibile modificare i dati dello stato validazione
				//quindi memorizziamo la validazione soo in gisa e in bdn lasciamo cosi com'e'
				//UPDATE
				//if(movimentazione.getIdBdn()>0)
				if(false)
				{
					serviceMovimentazioni.valida(movimentazione, db, getUserId(context));
				}
				//INSERT
				else
				{
					
					//Se e' stato inserito in Gisa e non ha dato errore di duplicati, lo metto in bdn
					String idMovimentazioneInserita = null;
					
					//Invio movimento in bdn
					System.out.println(" ------ INSERT MOVIMENTAZIONE -----"); 
					
					WsPost wsPost = new WsPost(db, WsPost.ENDPOINT_API_MOVIMENTAZIONI,WsPost.AZIONE_INSERT);
					
					HashMap<String, Object> campiInput = new HashMap<>();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					long dataMilli = System.currentTimeMillis(); 
					
					campiInput.put("apiProgressivo", movimentazione.getProgressivoApiarioOrigine());
					campiInput.put("apiattAziendaCodice", movimentazione.getCodiceAziendaOrigine());
					campiInput.put("numModello", movimentazione.getNumero_modello());
					campiInput.put("attestazioneSanitaria", "S");
					campiInput.put("statoRichiestaCodice", "C");
					campiInput.put("dtStatoRichiesta", sdf.format(new Timestamp(dataMilli) )+"T00:00:00.0");
					campiInput.put("destApiattAziendaCodice", movimentazione.getCodiceAziendaDestinazione());
					campiInput.put("dtModello", sdf.format(movimentazione.getData_modello())+"T00:00:00.0");
					campiInput.put("dtUscita", sdf.format(movimentazione.getDataMovimentazione())+"T00:00:00.0");
					
					if(movimentazione.getIdTipoMovimentazione()==1)
						campiInput.put("apimotuscCodice", "C");
					else if(movimentazione.getIdTipoMovimentazione()==2)
						campiInput.put("apimotuscCodice", "N");
					
					String istat = "" ;
					if(movimentazione.getIdTipoMovimentazione()==2)
					{
						String sql = "select cod_comune from comuni1 where nome ilike ? ";
						PreparedStatement pst = db.prepareStatement(sql);
						pst.setString(1, movimentazione.getComune_dest());
						ResultSet rs = pst.executeQuery();
						if (rs.next())
							istat = rs.getString(1);
					}
						
						
					if(movimentazione.getIdTipoMovimentazione()==2)
					{
						Apidetmod pp = null;
						
						pp = new Apidetmod();
						pp.setDestApiProgressivo( Integer.parseInt(movimentazione.getProgressivoApiarioDestinazione()));
						pp.setDestApiComIstat(istat);
						pp.setDestApiProSigla(movimentazione.getSigla_prov_comune_dest());
						pp.setDestApiComProSigla(movimentazione.getSigla_prov_comune_dest());
						pp.setDestApiIndirizzo(movimentazione.getIndirizzo_dest());
						pp.setNumAlveari(movimentazione.getNumApiariSpostati());
						pp.setNumSciami(0);
						pp.setNumPacchiDapi(0);
						pp.setNumApiRegine(movimentazione.getNumRegineDaSpostare());
						pp.setApidetmodId(0);
						pp.setDestApiComDescrizione(movimentazione.getComune_dest());
						pp.setDestApiLatitudine(movimentazione.getLatitudine_dest());
						pp.setDestApiLongitudine(movimentazione.getLongitudine_dest());
						pp.setDestApiId(movimentazione.getIdBdaApiarioDestinazione());
						
						ListaDettaglioModello lista = new ListaDettaglioModello();
						lista.apidetmod = new ArrayList<Apidetmod>();
						lista.apidetmod.add(pp);
						
						  XStream xStream = new XStream();
						    String xml = "<![CDATA["+ xStream.toXML(lista.apidetmod).replaceAll("it.izs.apicoltura.apimovimentazione.ws.Apidetmod", "dettaglioModello")+"]]>";
				
						campiInput.put("xmlListaDettaglioModello", xml);
					}
					
					if(movimentazione.getIdTipoMovimentazione()==1)
					{
						campiInput.put("numAlveari", movimentazione.getNumAlveariDaSpostare());
						campiInput.put("numSciami", movimentazione.getNumSciamiDaSpostare());
						campiInput.put("numPacchiDapi", movimentazione.getNumPacchiDaSpostare());
						campiInput.put("numApiRegine", movimentazione.getNumRegineDaSpostare());
					}
					
					wsPost.setCampiInput(campiInput);
					wsPost.costruisciEnvelope(db);
					String response = wsPost.post(db, getUserId(context));
					idMovimentazioneInserita = utilsXML.getValoreNodoXML(response,"apimodmovId" );
					
					if(idMovimentazioneInserita==null || idMovimentazioneInserita.equals(""))
					{
						throw new EccezioneDati("Errore nell'invio in bdn");
					}
					movimentazione.setIdBdn(Integer.parseInt(idMovimentazioneInserita));
					
					//Fine invio in bdn
					
					
				}
					
				movimentazione.sincronizzaBdn(db, getUserId(context),API_STATO_VALIDATO);
				
				
				
				
				/*
				 * occorrerebbe aggiornare anche il numero di cassette effettive in seguito alla movimentazione campo num_alveari_effettivi di apicoltura_apiari
				 * 
				 * numero_alveari_effettivi contiene il numero di cassette in entrata , e escono solo e un numero negativo.
				 * 
				 * Per il calcolo effettivo occorre sommarlo alla consistenza dell'apiario.
				 * 
				 * */
				if (movimentazione.getIdStabilimentoOrigine()>0)
				{
					
					String seql = "update apicoltura_apiari set num_alveari_effettivi =  ((case when num_alveari_effettivi is null then 0 else num_alveari_effettivi end-?)) where id = ? ";
					PreparedStatement pst = db.prepareStatement(seql);
					pst.setInt(1, movimentazione.getNumApiariSpostati());
					pst.setInt(2, movimentazione.getIdStabilimentoOrigine());
					pst.execute();
					
				}
				
				if (movimentazione.getIdStabilimentoDestinazione()>0)
				{
					
					String seql = "update apicoltura_apiari set num_alveari_effettivi =  ((case when num_alveari_effettivi is null then 0 else num_alveari_effettivi end+?)) where id = ? ";
					PreparedStatement pst = db.prepareStatement(seql);
					pst.setInt(1, movimentazione.getNumApiariSpostati());
					pst.setInt(2, movimentazione.getIdStabilimentoDestinazione());
					pst.execute();
					
				}

				if (db.getAutoCommit()==false)
				{
					db.commit();
					context.getRequest().setAttribute("ErrorValidazioneError", "Movimentazione Validata Con Successo");

				}

			}
			catch (ServerSOAPFaultException |  it.izs.apicoltura.apimovimentazione.ws.BusinessWsException_Exception e) {
				// TODO Auto-generated catch block

				logger.error("Errore nell'invio in BDN : "+e.getMessage());
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

				String msg = "VALIDAZIONE MOVIMENTAZIONE AZIENDA ORIGINE "+movimentazione.getCodiceAziendaOrigine()+"ESITO : KO . DATA INVIO :"+timestamp+". INVIATA DA :"+thisUser.getUsername()+". ERRORE : "+e.getMessage();


				if (db.getAutoCommit()==false)
				{
					db.rollback();
					db.setAutoCommit(true);
				}
				String errore = "" ;
				
				if (e instanceof it.izs.apicoltura.apimovimentazione.ws.BusinessWsException_Exception)
				{

					it.izs.apicoltura.apimovimentazione.ws.BusinessWsException_Exception ee =(it.izs.apicoltura.apimovimentazione.ws.BusinessWsException_Exception)e;
					errore = ee.getFaultInfo().getMessage()+" : "+ee.getFaultInfo().getResult().getErrore();

					for (it.izs.apicoltura.apimovimentazione.ws.FieldError error : ee.getFaultInfo().getResult().getErrors())
					{
						errore +="["+error.getField()+": "+error.getMessage()+ "]" ;

					}
				}
				else
				{
					if (e instanceof ServerSOAPFaultException)
					{

						ServerSOAPFaultException ee =(ServerSOAPFaultException)e;
						errore = ee.getFault().getFaultString();


					}

					else
						errore = e.getMessage();
				}

				String sql = "update apicoltura_movimentazioni set errore_sincronizzazione = ?, sincronizzato_bdn = false where id = ?";
				PreparedStatement pst = db.prepareStatement(sql);
				pst.setString(1, errore);
				pst.setInt(2,movimentazione.getId());
				pst.execute();

				context.getRequest().setAttribute("ErrorValidazioneError", errore);

				try
				{
					sendMailIzsm(context.getRequest(), msg+". L'errore e il seguente :"+errore, "##GISA_API_WS_BDN_KO##", "gisahelpdesk@usmail.it");
				}catch(Exception e2)
				{
					logger.error("Errore nell'invio della mail");
				}
				
				
				

			} 
			
			catch (Exception e ) 
			{
				logger.error("Errore nell'invio in BDN : "+e.getMessage());
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

				String msg = "VALIDAZIONE MOVIMENTAZIONE AZIENDA ORIGINE "+movimentazione.getCodiceAziendaOrigine()+"ESITO : KO . DATA INVIO :"+timestamp+". INVIATA DA :"+thisUser.getUsername()+". ERRORE : "+e.getMessage();


				if (db.getAutoCommit()==false)
				{
					db.rollback();
					db.setAutoCommit(true);
				}
				String errore = e.getMessage() ;
				
				String sql = "update apicoltura_movimentazioni set errore_sincronizzazione = ? where id = ?";
				PreparedStatement pst = db.prepareStatement(sql);
				pst.setString(1, errore);
				pst.setInt(2,movimentazione.getId());
				pst.execute();

				context.getRequest().setAttribute("ErrorValidazioneError", errore);

				try
				{
					sendMailIzsm(context.getRequest(), msg+". L'errore e il seguente :"+errore, "##GISA_API_WS_BDN_KO##", "gisahelpdesk@usmail.it");
				}catch(Exception e2)
				{
					logger.error("Errore nell'invio della mail");
				}
			} 
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			this.freeConnection(context, db);
		}

		return executeCommandNotificheMovimentazioniDaValidare(context);
	}
	
	
	
	private static boolean rigaValorizzata(org.apache.poi.ss.usermodel.Row row, int numMaxColonne)
	{
		int i=1;
		for(i=1;i<=numMaxColonne;i++)
		{
			Cell cella = row.getCell(i);
			String valore = null;
			if(cella!=null)
				valore = getCellValueSafe(cella);
			if(cella!=null && valore!=null && !valore.equals(""))
				return true;
		}
		return false;
	}
	
	
	public static String getCellValueSafe(Cell cella)
	{
		DataFormatter df = new DataFormatter();
		String toRet = null;
		if(cella != null)
		{
			toRet = df.formatCellValue(cella);
			if(toRet != null)
			{
				toRet = toRet.trim();
			}
		}
		if(toRet != null)
		{
			String toRetTrimmed = toRet.trim();
			return toRetTrimmed;
		}
		else
			return toRet;	
		
	}
	
	public static Timestamp getDateSafe(Cell cella, String nomeCampo) throws ParseException 
	{
		String value = getCellValueSafe(cella);
		if(value != null)
		{
			try
			{
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				//sdf.setLenient(false);
				if(value.length()!=10)
					throw new ParseException(nomeCampo, -1);
				return new Timestamp(sdf.parse(value).getTime());
			}
			catch(ParseException e)
			{
				throw new ParseException(nomeCampo, -1);
			}
		}
		
		return null;
	}
	
	private void logImport(Connection db, FileInfo fileInfo, UserBean utente, String nomeFileCompleto, int idOperatore, String codDocumento, String esitiInsert, String esitiErroriParsingfileAL) throws SQLException  
	{
		PreparedStatement pst = db.prepareStatement(" insert into apicoltura_movimentazioni_api_regine_log_import(nome_file,id_utente,data_import,nome_file_completo,id_operatore,cod_documento,errore_insert,errore_parsing_file) values(?,?,current_timestamp,?,?,?,?,?) ");
		pst.setString(1, fileInfo.getClientFileName());
		pst.setInt   (2, utente.getUserId());
		pst.setString(3,nomeFileCompleto);
		pst.setInt   (4, idOperatore);
		pst.setString(5, codDocumento);
		pst.setString(6, esitiInsert);
		pst.setString(7, esitiErroriParsingfileAL);
		pst.execute();
		pst.close();
			
	
	}
	
	
	private StringBuffer postProcessEsiti(ArrayList<String> esiti)
	{
		StringBuffer sb = new StringBuffer("");
		
		/*post process degli esiti (siccome excel ha sempr eun sacco di righe vuote alla fine che vengon oscartate
		 * levo dal log quello delle ultime righe vuote
		 */
		int firstEmptyIndex = -1;
		int k = 0;
		for(String esito : esiti )
		{
			if(esito.matches("^.*(SALTATA POICHE' UNO O PIU' CAMPI VUOTI O NON CORRETTAMENTE VALORIZZATI).*$") || esito.contains("SALTATA:"))
			{
				if(firstEmptyIndex == -1 )
				{
					firstEmptyIndex = k;
				}
			}
			else
			{
				firstEmptyIndex = -1;
			}
			
			k++;
		}
		
		/*la prima la mettiamo sempre */
		/*
		if(esiti.size() > 0)
		{
			sb.append(esiti.get(0));
		}
		
		for(int i = 1; i< firstEmptyIndex && i< esiti.size();i++ )
		{
			sb.append(esiti.get(i));
		}
		*/
		
		for(int i = 0; i< esiti.size();i++ )
		{
			sb.append(esiti.get(i));
		}
		
		return sb;
	}
	
	public  HashMap<Integer,ModelloC> parseSheet(Operatore operatoreOrigine, org.apache.poi.ss.usermodel.Workbook wb,Connection db,ArrayList<String> alEsitiParsingFile, ActionContext context) throws ParseException
	{
		HashMap<Integer,ModelloC> movToAdd = new HashMap<Integer,ModelloC>(); 
		
		org.apache.poi.ss.usermodel.Sheet sheet = wb.getSheetAt(0);
		//Instanziazione di variabili per calcolo data
		Date dataAttuale = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		dataAttuale = sdf.parse(sdf.format(dataAttuale));
		
		GregorianCalendar gCalendarMesePrecedente = new GregorianCalendar();
		gCalendarMesePrecedente.setTimeInMillis(dataAttuale.getTime());
		gCalendarMesePrecedente.add(GregorianCalendar.MONTH, -1);
		
		GregorianCalendar gCalendarLimiteInferiore = new GregorianCalendar();
		gCalendarLimiteInferiore.setTimeInMillis(gCalendarMesePrecedente.getTimeInMillis());
		gCalendarLimiteInferiore.set(GregorianCalendar.DAY_OF_MONTH, 1);
		
		GregorianCalendar gCalendarLimiteSuperiore = new GregorianCalendar();
		gCalendarLimiteSuperiore.setTimeInMillis(gCalendarMesePrecedente.getTimeInMillis());
		gCalendarLimiteSuperiore.set(GregorianCalendar.DAY_OF_MONTH, gCalendarLimiteSuperiore.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
		
		//Da usare nel caso si stabilisca che le movimentazioni possono essere inviate entro il mese successivo, 
		//quindi si possono accettare anche date del mese attuale a patto che, ovviamente, non siano future
		//GregorianCalendar gCalendarLimiteSuperiore = gCalendarDataAttuale;
		
		
		Date limiteInferiore = new Date(gCalendarLimiteInferiore.getTimeInMillis());
		Date limiteSuperiore = new Date(gCalendarLimiteSuperiore.getTimeInMillis());
		
		//FINE Instanziazione di variabili per calcolo data
		
		ArrayList<String> esistenzaMovimentiFile = new ArrayList<String>();
		
		for(int ir = 1; ir<= sheet.getLastRowNum();ir++)
		{
			try
			{
				org.apache.poi.ss.usermodel.Row row = sheet.getRow(ir);
				int u = 0;
				
				if(!rigaValorizzata(row,4))
				{
					break;
				}
				
				//Leggo i valori della riga */
				//String codiceAziendaMittente = getCellValueSafe(row.getCell(u++));
				Timestamp dataMovimentazione =  getDateSafe (row.getCell(u++), "data_movimentazione");
				String codiceAziendaDestinazione = getCellValueSafe(row.getCell(u++));
				String numApiRegine = getCellValueSafe(row.getCell(u++));
				
				/*if(codiceAziendaMittente==null || dataMovimentazione==null || codiceAziendaDestinazione==null || numApiRegine==null )
					throw new Exception("VALORIZZARE TUTTI I CAMPI");*/
				
				//Controllo codice azienda mittente
				/*if(!codiceAziendaMittente.equalsIgnoreCase(operatoreOrigine.getCodiceAzienda()))
					throw new Exception("CODICE AZIENDA MITTENTE DEVE ESSERE UGUALE AL CODICE AZIENDA PER IL QUALE SI STA LAVORANDO: " + operatoreOrigine.getCodiceAzienda());*/
				//FINE controllo codice azienda mittente
				
				//Controllo data movimentazione con data odierna
				if(limiteInferiore.compareTo(dataMovimentazione)>0 || limiteSuperiore.compareTo(dataMovimentazione)<0)
					throw new Exception("DATA MOVIMENTAZIONE DEVE FAR RIFERIMENTO AL MESE PRECEDENTE");
				//FINE Controllo data movimentazione con data odierna
				
				//Controllo data movimentazione con data inizio attivita' e data cessazione azienda origine
				Date dataInizioAttivita = new Date (operatoreOrigine.getDataInizio().getTime());
				Date dataFineAttivita = new Date ();
				if(operatoreOrigine.getDataChiusura()!=null)
					dataFineAttivita = new Date(operatoreOrigine.getDataChiusura().getTime());
				if(dataInizioAttivita.compareTo(dataMovimentazione)>0 || (operatoreOrigine.getDataChiusura()!=null && dataFineAttivita.compareTo(dataMovimentazione)<0))
					throw new Exception("DATA MOVIMENTAZIONE DEVE ESSERE COMPRESA TRA DATA INIZIO ATTIVITA' E DATA CESSAZIONE AZIENDA ORIGINE");
				//FINE Controllo data movimentazione con data inizio attivita' e data cessazione azienda origine
				
				//Controllo campo numero api regine
				try
				{
					int numero = Integer.parseInt(numApiRegine);
					if(numero<=0)
						throw new NumberFormatException();
				}
				catch(NumberFormatException ex)
				{
					throw new Exception("CAMPO NUMERO API REGINE IN FORMATO NON CORRETTO, SONO AMMESSI SOLO NUMERI POSITIVI");
				}
				//FINE Controllo campo numero api regine
				
				//Controllo esistenza codice azienda destinazione
				Operatore operatore = new Operatore(db, codiceAziendaDestinazione, null, null,null, null);
				Date dataInizioAttivitaDestinazione = null;
				Date dataFineAttivitaDestinazione = null;
				boolean destinazioneExtraRegione = false;
				Apiatt attivitaBdn = null;
				if(operatore.getIdOperatore()<=0)
				{
					//Cerca in BDN
					destinazioneExtraRegione = true;
					operatore.setCodiceAzienda(codiceAziendaDestinazione);
					WsApiAnagraficaAttivita serviceAttivita = new WsApiAnagraficaAttivita();
					attivitaBdn = serviceAttivita.search(operatore, db, getUserId(context));
					if(attivitaBdn==null)
						throw new Exception("AZIENDA DESTINAZIONE CON CODICE " + codiceAziendaDestinazione + " NON TROVATA IN BDA-R E IN BDN");
					dataInizioAttivitaDestinazione = new Date( attivitaBdn.getDtInizioAttivita().getMillisecond());
					if(attivitaBdn.getDtCessazione()!=null)
						dataFineAttivitaDestinazione =  new Date( attivitaBdn.getDtCessazione().getMillisecond());
				}
				else
				{
					dataInizioAttivitaDestinazione = operatore.getDataInizio();
					dataFineAttivitaDestinazione = operatore.getDataChiusura();
				}
				//FINE Controllo esistenza codice azienda destinazione
				
				if(esistenzaMovimentiFile.contains(dataMovimentazione.toString()+codiceAziendaDestinazione+numApiRegine))
					throw new EccezioneDati("Movimentazione con data, numero api regine, azienda destinazione gia presente nel file");
				
				//Controllo data movimentazione con data inizio attivita' e data cessazione azienda destinazione
				dataInizioAttivita = new Date(dataInizioAttivitaDestinazione.getTime());
				if(dataFineAttivitaDestinazione!=null)
					dataInizioAttivita = new Date(dataFineAttivitaDestinazione.getTime());
				if(dataInizioAttivita.compareTo(dataMovimentazione)>0 || (operatoreOrigine.getDataChiusura()!=null && dataFineAttivitaDestinazione.compareTo(dataMovimentazione)<0))
					throw new Exception("AZIENDA DESTINAZIONE CON CODICE " + codiceAziendaDestinazione + " DEVE ESSERE ATTIVA ALLA DATA DI MOVIMENTAZIONE");
				//FINE Controllo data movimentazione con data inizio attivita' e data cessazione azienda destinazione
				
				//Costruzione movimento da inserire
				ModelloC modello = new ModelloC();
				modello.setStato(API_STATO_VALIDAZIONE_NON_RICHIESTA);
				modello.setEntered_by(getUserId(context));
				modello.setSincronizzato_bdn(false);
				modello.setData_modello(new Timestamp(dataAttuale.getTime()));
				modello.setDataMovimentazione(dataMovimentazione);
				modello.setCodiceAziendaDestinazione(codiceAziendaDestinazione);
				modello.setNumAlveariDaSpostare("0");
				modello.setNumPacchiDaSpostare("0");
				modello.setNumRegineDaSpostare(numApiRegine);
				modello.setNumSciamiDaSpostare("0");
				modello.setIdTipoMovimentazione(4);
				modello.setAttestazioneSanitaria(false);

				if(operatoreOrigine!=null)
				{
					modello.setId_asl_apicoltore_origine(operatoreOrigine.getIdAsl());
					modello.setCodiceAziendaOrigine(operatoreOrigine.getCodiceAzienda());
				}
				
				modello.generaNumeroModello(db);
				//FINE costruzione movimento da inserire
				
				movToAdd.put(ir, modello);
				esistenzaMovimentiFile.add(dataMovimentazione.toString()+codiceAziendaDestinazione+numApiRegine);
			}
			
			catch(ParseException ex)
			{
				alEsitiParsingFile.add("<font color=\"red\"><br>RIGA "+ir+" SALTATA: CAMPO " + ex.getMessage() + " IN FORMATO NON CORRETTO</font>");
			}
			catch(Exception ex)
			{
				alEsitiParsingFile.add("<font color=\"red\"><br>RIGA "+ir+" SALTATA PER IL SEGUENTE ERRORE " + ex.getMessage() + " </font>");
			}
			
		}
		
		return  movToAdd;
	}
	
	private String upload(ActionContext context, HashMap parts, String filePath) throws SQLException, IOException
	{
		Boolean documentaleDisponibile = Boolean.valueOf(ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE"));
		String documentaleNonDisponibileMessaggio = ApplicationProperties.getProperty("DOCUMENTALE_DISPONIBILE_MESSAGGIO");
		if (!documentaleDisponibile) 
		{
			context.getRequest().setAttribute("error", documentaleNonDisponibileMessaggio);
			context.getRequest().setAttribute("label", "documents");
			return "documentaleAllegatiError";
		}

		String op = "";
		
		String id = (String) parts.get("id");
		String altId = (String) parts.get("alt_id");
		op = (String) parts.get("op");
		String tipoAllegato = (String) parts.get("tipoAllegato");

		context.getRequest().setAttribute("op", op);
		context.getRequest().setAttribute("orgId", String.valueOf(id));

		int fileSize = -1;

		String filename = "";
		String fileDimension = "";
		if ((Object) parts.get("file1") instanceof FileInfo) 
		{
			// Update the database with the resulting file
			FileInfo newFileInfo = (FileInfo) parts.get("file1");
			// Insert a file description record into the database
			com.zeroio.iteam.base.FileItem thisItem = new com.zeroio.iteam.base.FileItem();
			thisItem.setAltId(Integer.parseInt(altId));
			thisItem.setLinkModuleId(Constants.ACCOUNTS);
			thisItem.setEnteredBy(getUserId(context));
			thisItem.setModifiedBy(getUserId(context));
			thisItem.setClientFilename(newFileInfo.getClientFileName());
			filename = newFileInfo.getRealFilename();
			thisItem.setFilename(newFileInfo.getRealFilename());
			thisItem.setVersion(1.0);
			thisItem.setSize(newFileInfo.getSize());
			fileSize = thisItem.getSize();
			fileDimension = String.valueOf(fileSize); 
		}

		int maxFileSize = -1;
		int mb1size = 1048576;
		if (ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI") != null)
			maxFileSize = Integer.parseInt(ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI"));

		
		String f1 = filePath + filename;

		File file = new File(f1);
		byte[] buffer = new byte[(int) file.length()];
		InputStream ios = null;
		try 
		{
			ios = new FileInputStream(file);
			if (ios.read(buffer) == -1) 
			{
				throw new IOException("EOF reached while trying to read the whole file");
			}
		} 
		finally 
		{
			try 
			{
				if (ios != null)
					ios.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}

		byte[] ba = buffer;
		return chiamaServerDocumentale(context, ba, tipoAllegato, id, filename, fileDimension, altId);
		
	}
	
	public String chiamaServerDocumentale(ActionContext context, byte[] ba, String tipoAllegato, String idGestore, String filename,String fileDimension, String altId ) throws SQLException, IOException 
	{
		String ip = context.getIpAddress();

		String baString = "";
		baString = byteArrayToString(ba);
		String codDocumento = "";
		
		HttpURLConnection conn = null;
		String url = "http://srvDOCUMENTALEL" + ApplicationProperties.getProperty("APP_PORTA_DOCUMENTALE") + "/"
				+ ApplicationProperties.getProperty("APP_DOCUMENTALE_ALLEGATI_CARICATI");
		// STAMPE

		URL obj;
		try 
		{
			obj = new URL(url);
			conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");

			StringBuffer requestParams = new StringBuffer();
			requestParams.append("baString");
			requestParams.append("=").append(URLEncoder.encode(baString, "ISO-8859-1"));
			requestParams.append("&");
			requestParams.append("provenienza");
			requestParams.append("=").append(ApplicationProperties.getProperty("APP_NAME_GISA"));
			requestParams.append("&");
			requestParams.append("tipoCertificato");
			requestParams.append("=").append(tipoAllegato);
			requestParams.append("&");
			requestParams.append("filename");
			requestParams.append("=").append(filename);
			requestParams.append("&");
			requestParams.append("fileDimension");
			requestParams.append("=").append(fileDimension);
			requestParams.append("&");
			requestParams.append("altId");
			requestParams.append("=").append(altId);
			requestParams.append("&");
			requestParams.append("idUtente");
			requestParams.append("=").append(getUserId(context));
			requestParams.append("&");
			requestParams.append("ipUtente");
			requestParams.append("=").append(ip);

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(requestParams.toString());
			wr.flush();

			conn.getContentLength();

			String messaggioPost = "";

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer result = new StringBuffer();
			if (in != null) {
				String ricevuto = in.readLine();
				result.append(ricevuto);
			}
			in.close();

			try {
				JSONObject jo = new JSONObject(result.toString());
				codDocumento = jo.get("codDocumento").toString();
			} catch (Exception e) {
			}
			if (codDocumento == null || codDocumento.equals("null") || codDocumento.equals(""))
				messaggioPost = "Possibile errore nel caricamento del file. Controllarne la presenza nella lista sottostante.";
			else
				messaggioPost = "OK! Caricamento completato con successo.";

			context.getRequest().setAttribute("messaggioPost", messaggioPost);

		} catch (ConnectException e1) {
			context.getRequest().setAttribute("error", "SERVER DOCUMENTALE OFFLINE");
			context.getRequest().setAttribute("label", "documents");
			return "documentaleAllegatiError";
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			context.getRequest().setAttribute("error", "ERRORE NEL CARICAMENTO DEL FILE");
			context.getRequest().setAttribute("label", "documents");
			return "documentaleAllegatiError";
		} catch (IOException e) {
			e.printStackTrace();
			context.getRequest().setAttribute("error", "ERRORE NEL CARICAMENTO DEL FILE");
			context.getRequest().setAttribute("label", "documents");
			return "documentaleAllegatiError";
		}

		finally {
			conn.disconnect();
		}
		
		return codDocumento;

	}
	
	private String byteArrayToString(byte[] ba) throws UnsupportedEncodingException 
	{
		String s = new String(ba, "ISO-8859-1");
		return s;

	}
	
	public String executeCommandStoricoUploadFileApiRegine(ActionContext context)
	{
		Connection conn = null;
		int userId = ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserId();
		String idOperatore	= context.getRequest().getParameter("idOperatore");
		if (idOperatore==null)
			idOperatore = (String) context.getRequest().getAttribute("idOperatore");
		
		try
		{
			PagedListInfo searchListInfo = this.getPagedListInfo( context, "StoricoUploadFileApiRegineInfo");
			
			searchListInfo.setLink("ApicolturaMovimentazioni.do?command=StoricoUploadFileApiRegine&idOperatore="+idOperatore);
			 
			conn = getConnection(context);
			userId = ((UserBean)context.getRequest().getSession().getAttribute("User")).getUserId();
		    
		    StoricoImport storicoImportList = new StoricoImport();
		    storicoImportList.setPagedListInfo(searchListInfo);
	        searchListInfo.setSearchCriteria(storicoImportList, context);
	        storicoImportList.setIdOperatore(Integer.parseInt(idOperatore));
	        storicoImportList.buildList(conn);
	        context.getRequest().setAttribute("storicoImportList", storicoImportList);
		}
		catch (Exception e) 
		{
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 
		finally
		{
			this.freeConnection(context, conn);
		}
		
		return "StoricoUploadFileApiRegineOK";
	}
	
	public String executeCommandVediErrori(ActionContext context)
	{
		String viewName = "VediErrori";
		Connection conn = null;
		try
		{
			conn = getConnection(context);
			Integer idImport = Integer.parseInt(context.getRequest().getParameter("idImport"));
			
			StoricoImport storicoImportList = new StoricoImport();
	        storicoImportList.setId(idImport);
	        storicoImportList.buildList(conn);
	        context.getRequest().setAttribute("storicoImportList", storicoImportList);
	        StoricoImport storico = (StoricoImport)storicoImportList.get(0);
			
			context.getRequest().setAttribute("erroreInsert", storico.getErroreInsert()); 
			context.getRequest().setAttribute("erroreParsingFile", storico.getErroreParsingFile()); 
			 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			viewName = "SystemError";
		}
		finally
		{
			this.freeConnection(context, conn);
		}
		
		return viewName;
	}
	
	public String executeCommandDownloadImport(ActionContext context)
	{
		int idImport=-1;
		String path_server="", titolo="";
		idImport = Integer.parseInt(context.getRequest().getParameter("idImport"));
		Connection db = null;
		
		try 
		{
			db = this.getConnection(context);
			
			StoricoImport storicoImportList = new StoricoImport();
	        storicoImportList.setId(idImport);
	        storicoImportList.buildList(db);
	        StoricoImport s = (StoricoImport)storicoImportList.get(0);
	        
			titolo = s.getNomeFile();
	        String fileType = s.getNomeFile().split("\\.")[1];
	        String fileName = s.getNomeFileCompleto();
	         
	        if (new File(fileName).exists())
	        {
	        	 context.getResponse().setContentType(fileType);
	        	 context.getResponse().setHeader("Content-disposition","attachment; filename="+titolo);
	
	        	 File my_file = new File(fileName);
	
	        	 OutputStream out =   context.getResponse().getOutputStream();
	        	 FileInputStream in = new FileInputStream(my_file);
	        	 byte[] buffer = new byte[4096];
	        	 int length;
	        	 while ((length = in.read(buffer)) > 0)
	        	 {
	        		 out.write(buffer, 0, length);
	        	 }
	        	 in.close();
	        	 out.flush();
	         }
	         else
	         {
	        	 PrintWriter out =   context.getResponse().getWriter();
	        	 out.println("File non trovato!");
	        	 out.println(fileName);
	        	 out.println("Si e' verificato un problema con il recupero del file. Si prega di contattare l'HelpDesk.");
	          }
	        
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			this.freeConnection(context, db);
		}
				
		return ("-none-");	
		
	}
	
}
