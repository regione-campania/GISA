/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneExcel.actions;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.dpat2019.base.DpatIstanza;
import org.aspcfs.modules.gestioneDocumenti.actions.GestioneAllegatiUpload;
import org.aspcfs.modules.gestioneExcel.base.ExcelDpat;
import org.aspcfs.modules.gestioneExcel.base.ExcelDpatVersione2016;
import org.aspcfs.modules.gestioneExcel.base.ExcelRegistroTrasgressori;
import org.aspcfs.modules.gestioneExcel.base.ExcelRegistroTrasgressoriOld;
import org.aspcfs.modules.gestoriacquenew.actions.StabilimentoGestoriAcqueReteNewAction;
import org.aspcfs.modules.gestoriacquenew.base.ControlloInterno;
import org.aspcfs.modules.gestoriacquenew.base.EccezioneDati;
import org.aspcfs.modules.gestoriacquenew.base.ExcelParserGestoriAcque;
import org.aspcfs.modules.gestoriacquenew.base.GestoreAcque;
import org.aspcfs.modules.gestoriacquenew.base.PuntoPrelievo;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.oia.actions.AccountVigilanza;
import org.aspcfs.modules.oia.base.Organization;
import org.aspcfs.modules.registrotrasgressori.base.Trasgressione;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.Part;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

//DAVIDE
public class GestioneExcel extends CFSModule { 
	
	
	public String executeCommandGetExcel(ActionContext context)
	{
		String toRet = null;
		Connection db = null;
		String nome = null;
		String fullPath = null;
		int anno = -1;
		
		try
		{
			db = this.getConnection(context);
			String tipoRichiesta = context.getRequest().getParameter("tipo_richiesta");
			if(tipoRichiesta.equals("registro_trasgressori"))
			{
				anno = Integer.parseInt(context.getRequest().getParameter("anno")) ;
				ExcelRegistroTrasgressori regBean = new ExcelRegistroTrasgressori();
				
				String path = getWebInfPath(context,"tmp_excel");
				
				Date data = new Date();
				DateFormat dateFormatter = new SimpleDateFormat("yyyy-mm-dd_hh-MM-ss");
				
				File theDir = new File(path);
		     	theDir.mkdirs();
		     	nome="Registro"+anno+"_"+dateFormatter.format(data.getTime())+".xlsx";
				
				regBean.setAnno(anno);
				regBean.setPath(path);
				regBean.setNome(nome);
				fullPath = path+nome;
				regBean.setFullPath(fullPath);
				regBean.genera(db);
				
				HashMap<String,String> valori = new HashMap<String,String>();
				File fileGenerato = new File(fullPath);
				valori.put("dimensione", fileGenerato.length()+"");
				valori.put("filename",fileGenerato.getName());
				valori.put("anno",anno+"");
				
				inviaRegistroADocumentale(context, fullPath, valori);
				
				
				
			}
			else if(tipoRichiesta.equals("registro_trasgressori_old"))
			{
				anno = Integer.parseInt(context.getRequest().getParameter("anno")) ;
				ExcelRegistroTrasgressoriOld regBean = new ExcelRegistroTrasgressoriOld();
				
				String path = getWebInfPath(context,"tmp_excel");
				
				Date data = new Date();
				DateFormat dateFormatter = new SimpleDateFormat("yyyy-mm-dd_hh-MM-ss");
				
				File theDir = new File(path);
		     	theDir.mkdirs();
		     	nome="Registro"+anno+"_"+dateFormatter.format(data.getTime())+".xlsx";
				
				regBean.setAnno(anno);
				regBean.setPath(path);
				regBean.setNome(nome);
				fullPath = path+nome;
				regBean.setFullPath(fullPath);
				regBean.genera(db);
				
				HashMap<String,String> valori = new HashMap<String,String>();
				File fileGenerato = new File(fullPath);
				valori.put("dimensione", fileGenerato.length()+"");
				valori.put("filename",fileGenerato.getName());
				valori.put("anno",anno+"");
				
				inviaRegistroADocumentale(context, fullPath, valori);
				
				
				
			}
//			else if(tipoRichiesta.equals("dpat"))
//			{
//				if(!hasPermission(context,"dpat_export-view"))
//					return "PermissionError";
//				
//				anno = Integer.parseInt(context.getRequest().getParameter("anno"));
//				String idAsl = context.getRequest().getParameter("idAsl");
//				String pathTempDir = getWebInfPath(context,"tmp_excel");
//				File tempDir = new File(pathTempDir);
//				tempDir.mkdirs();
//				
//				int idArea = -1 ;
//				String comboArea = context.getRequest().getParameter("combo_area");
//				if (comboArea!=null && !"".equals(comboArea))
//					idArea=Integer.parseInt(comboArea);
////				fullPath = ExcelDpat.generaFoglio(context.getRequest().getContextPath()+"/templateExcel/Template_nuovo.xlsx",idAsl, anno, db, pathTempDir+"/");
//				
//				
//				
//				String pathTemplate = context.getServletContext().getRealPath("/templateExcel/Template_nuovo.xlsx");
//				boolean trasposto = Boolean.parseBoolean(context.getRequest().getParameter("trasposto"));
//				fullPath = ExcelDpatVersione2016.generaFoglio(pathTemplate,idAsl, anno, db, pathTempDir,idArea,trasposto);
//				 
//				int lastInd = Math.max(fullPath.lastIndexOf("/"),fullPath.lastIndexOf("\\"));
//				nome = fullPath.substring(lastInd+1);
//				
//				File fGenerato = new File(fullPath);
//				HashMap<String,String> valori = new HashMap<String,String>();
//				
//				valori.put("dimensione",fGenerato.length()+"");
//				valori.put("filename",nome);
//				valori.put("asl", idAsl); 
//				valori.put("anno",anno+"");
//				valori.put("tipoOperazione","export");
//				
//				//inviamo una copia del file generato al documentale
//				inviaDpatADocumentale(context, fullPath, valori);
//				
//				
//				
//			}
			else if(tipoRichiesta.equalsIgnoreCase("censimentiApiari"))
			{
				int idApiario = Integer.parseInt(context.getRequest().getParameter("idApiario"));
				 
				String path = getWebInfPath(context,"tmp_excel");
				
				File theDir = new File(path);
		     	if(!theDir.exists())
		     		theDir.mkdirs();
		     	 
				
				fullPath = ext.aspcfs.modules.apicolture.actions.StabilimentoAction.generaFoglioExcelCensimentiPerApiario(db, idApiario, path)  ;
				nome = "Censimenti.xlsx";
				 
			}
			else if(tipoRichiesta.equalsIgnoreCase("censimentiApicoltore")) /*tutti apiari di quell'apicoltore */
			{
				int idApicoltore = Integer.parseInt(context.getRequest().getParameter("idApicoltore"));
				 
				String path = getWebInfPath(context,"tmp_excel");
				
				File theDir = new File(path);
		     	if(!theDir.exists())
		     		theDir.mkdirs();
		     	 
				
				fullPath = ext.aspcfs.modules.apicolture.actions.StabilimentoAction.generaFoglioExcelCensimentiPerApicoltore(db, idApicoltore, path)  ;
				nome = "Censimenti.xlsx";
				 
			}
			else if(tipoRichiesta.equalsIgnoreCase("movimentazioniApiari"))
			{
//				String codiceAzienda = context.getRequest().getParameter("codiceAziendaSearch");
				int idApiario = Integer.parseInt(context.getRequest().getParameter("idApiario"));
				String codiceAziendaSearch = context.getRequest().getParameter("codiceAziendaSearch");
				String progressivoApiarioSearch = context.getRequest().getParameter("progressivoApiarioSearch");
				String path = getWebInfPath(context,"tmp_excel");
				
				File theDir = new File(path);
		     	if(!theDir.exists())
		     		theDir.mkdirs();
		     	 
				
				fullPath =  ext.aspcfs.modules.apicolture.actions.Movimentazioni.generaFoglioExcelMovimentazioniPerApiario(db, codiceAziendaSearch,progressivoApiarioSearch, path)  ;
				nome = "Movimentazioni.xlsx";
			}
			 
			
			
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ("SystemError");
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return ("SystemError");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return "SystemError";
		}
		finally
		{
			this.freeConnection(context, db);
		}
		
		return downloadExcel(context, nome,fullPath);
		
	}
	
	
	//routine per far scaricare al client come stream
	public String downloadExcel(ActionContext context, String nomeFile, String pathFile)
	{
			 String fileType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
             
             
	      //  if (new File(fileName).exists()){
	         // Find this file id in database to get file name, and file type

	         // You must tell the browser the file type you are going to send
	         // for example application/pdf, text/plain, text/html, image/jpg
	         context.getResponse().setContentType(fileType);

	         // Make sure to show the download dialog
	         context.getResponse().setHeader("Content-disposition","attachment; filename=\""+nomeFile+"\"");

	         // Assume file name is retrieved from database
	         // For example D:\\file\\test.pdf
	         File inputFile = new File(pathFile);
	         File my_file = new File(inputFile.getAbsolutePath());

	         // This should send the file to browser
	         try
	         {	 OutputStream out = context.getResponse().getOutputStream();
	         	 
		         FileInputStream in = new FileInputStream(my_file);
		         byte[] buffer = new byte[4096];
		         int length;
		         while ((length = in.read(buffer)) > 0){
		            out.write(buffer, 0, length);
		         }
		         in.close();
		         out.flush();
		         out.close();
	         }
	         catch(Exception ex)
	         {
	        	 ex.printStackTrace();
	        	 return "SystemError";
	         }
	         return ("-none-");	
	}
	
	
	
	public String executeCommandPutExcel(ActionContext context)
	{
		
		Connection db = null;
		String toRet = null;
		
		try
		{
			db = getConnection(context);
			String fullPath = null;
			
			String pathTemp = this.getPath(context, "acque_rete_import");
			File tempFold = new File(pathTemp);
			tempFold.mkdirs();
			
			//questo mi scarica il contenuto del form (il file allegato) nella cartella
			HttpMultiPartParser multiPart = new HttpMultiPartParser();
			HashMap parts = multiPart.parseData(context.getRequest(), pathTemp);
			String codDocumento = upload(context, parts, pathTemp);
			if(codDocumento.equalsIgnoreCase("documentaleAllegatiError"))
				throw new Exception("Errore durante l'upload del file");
			
			//Vecchia gestione upload file: la rimango nel caso in cui dovesse servire per tipo_richiesta = dpat
			FileInfo fInfo = (FileInfo) parts.get("file1");
			String fileName = fInfo.getClientFileName();
			
			
			if( ((String)parts.get("tipo_richiesta")).equals("dpat"))
			{
				int anno = Integer.parseInt((String)parts.get("anno"));
				String idAsl = (String) parts.get("asl");
				if(!hasPermission(context,"dpat_import-view"))
					return "PermissionError";
				//leggiamo il file e lo scriviamo in cartella temporanea
				//il parametro associato al file e' un'istanza di file info
				if(parts.get("file1") instanceof FileInfo)
				{
					HashMap<String,String> valori = new HashMap<String,String>();
					valori.put("dimensione",fInfo.getSize()+"" );
					
					valori.put("filename",fileName);
					valori.put("asl", idAsl);
					valori.put("anno",anno+"");
					valori.put("tipoOperazione","import");
					fullPath = pathTemp+fileName;
					
					inviaDpatADocumentale(context,fullPath,valori);
					//il file adesso e' gia' scritto sul disco, quindi dobbiamo chiamare la routine per l'import
					
					//affinche' si possa garantire la compatibilita' con veersioni di fogli excel generati nel 2015 (quelli che possono avere metadati, non metadati etc, che sicuramente non sono verticali etc)
					//dobbiamo distinguere tra : 
					//file generato prima del 2016 e file generato dopo il 2016
					//nel primo caso dobbiamo lanciare le vecchie versioni delle routine
					//nel secondo caso, dobbiamo usare le nuove routine distinguendo tra file generato nel 2016 ma per un anno precedente al 2016 (es 2015 
					//che utilizza una vecchia struttura delle colonne cablate etc) e un file generato nel 2016 relativo al 2016
					
					int idArea = -1 ; //l'area serve lasciata a -1 anche per fogli del 2015, poiche' adesso la query lo vuole sempre in input
					
					//estraggo info sull'anno di generazione del foglio
					FileInputStream fis = new FileInputStream(new File(fullPath));
					XSSFWorkbook wb = new XSSFWorkbook(fis);
					String title = wb.getProperties().getCoreProperties().getTitle();
					wb.close();
					int iT = title.substring(0,title.indexOf("/")).lastIndexOf(" ");
					String dataStr = title.substring(iT+1,title.length());
					
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //mi serve solo l'anno
					Date annoGenerazioneFoglio = null;
					PreparedStatement pst = null;
					ResultSet rs = null;
					//trasformo in data sql
					annoGenerazioneFoglio = dateFormat.parse(dataStr);
					
					if(annoGenerazioneFoglio.getYear() + 1900 < 2016) //per fogli generati nel 2015, sicuramente non ci interessa avere l'area valorizzata, quindi la si passa come -1 alla query
					{
						ExcelDpat.importFoglio(fullPath,idAsl,anno,db,idArea);
					}
					else //fogli generati dal 2016 in poi, quindi sicuramente chiamiamo le nuove versioni...
					{
						if (anno >= 2016 && parts.get("combo_area")!=null) //in questo caso stiamo facendo import per una asl di un anno >= 2016 poiche' in quei casi viene valorizzata l'area
						{//altrimenti rimane valorizzata a -1 l'area
							String comboArea = (String)parts.get("combo_area");
							if (comboArea!=null && !"".equals(comboArea))
								idArea=Integer.parseInt(comboArea);
						}
						ExcelDpatVersione2016.importFoglio(fullPath,idAsl, anno, db,idArea);
					}
				}
				
				aggiungiAttributiAggiuntiviARequest(context, db);
				
				toRet = "DpatHomeRegOK";
				
			}
			/*se si importano tutti i datai anagrafici di tutti i gestori */
			else if(((String)parts.get("tipo_richiesta")).equalsIgnoreCase("gestori_acque_rete_import_massivo_anag"))
			{
				
				ArrayList<String> esitiErroriParsingfileAL = new ArrayList<String>();
				StringBuffer  esitiErroriSB = null;
				StringBuffer esitiInsert = new StringBuffer("");
				if(parts.get("file1") instanceof FileInfo)
				{
					fullPath = pathTemp+fileName;
					 
 
					org.apache.poi.ss.usermodel.Workbook wb = WorkbookFactory.create(new File(fullPath));
					ArrayList<GestoreAcque> gestoriToAdd = ExcelParserGestoriAcque.parseSheetPerImportMassivoAnagDifferentiGestori(wb, db,esitiErroriParsingfileAL);
					esitiErroriSB = postProcessEsiti(esitiErroriParsingfileAL);
					
					for(GestoreAcque gest : gestoriToAdd)
					{
						esitiInsert .append(gest.insert(db));
					}
 				 
				}
				
				/*in ogni caso allego pure la lista di tutti i gestori gia' importati */
				ArrayList<GestoreAcque> tuttiGestoriImportati = GestoreAcque.getAllGestoriConUtenteAssociato(db,0,false);
				context.getRequest().getSession().setAttribute("TuttiGestoriImportatiConUtentiEventuali",tuttiGestoriImportati);
				
				context.getRequest().setAttribute("esitiErroriParsingFile", esitiErroriSB);
				context.getRequest().setAttribute("esitiInsert", esitiInsert);
				
				toRet = "ImportMassivo"; 
				
				
			}
			/*se si importano tutti i punti prelievo per tutti i gestori */
			else if(((String)parts.get("tipo_richiesta")).equalsIgnoreCase("gestori_acque_rete_import_massivo_pp"))
			{
				
				ArrayList<String> esitiErroriParsingfileAL = new ArrayList<String>();
				StringBuffer esitiErroriParsingfileSB = new StringBuffer("");
				StringBuffer esitiInsert = new StringBuffer("");
				if(parts.get("file1") instanceof FileInfo)
				{
					fullPath = pathTemp+fileName;
					
//					FileInputStream fis = new FileInputStream(new File(fullPath));
//					XSSFWorkbook wb = new XSSFWorkbook(fis);
					org.apache.poi.ss.usermodel.Workbook wb = WorkbookFactory.create(new File(fullPath));
					HashMap<String,GestoreAcque> gestori = ExcelParserGestoriAcque.parseSheetPerImportMassivoPuntiPrelievoDifferentiGestori(wb, db,esitiErroriParsingfileAL);
					esitiErroriParsingfileSB = postProcessEsiti(esitiErroriParsingfileAL);
					for(String denominazioneGest : gestori.keySet())
					{
						GestoreAcque gest = gestori.get(denominazioneGest);
						/*inserisco i punti di prelievo (i gestori erano gia' stati inseriti) */
						for(PuntoPrelievo pp : gest.getPuntiPrelievo())
						{
							esitiInsert.append(pp.insert(db));
						}
					}
				}
				
				context.getRequest().setAttribute("esitiErroriParsingFile", esitiErroriParsingfileSB);
				context.getRequest().setAttribute("esitiInsert", esitiInsert);
				/*in ogni caso allego pure la lista di tutti i gestori gia' importati */
				ArrayList<GestoreAcque> tuttiGestoriImportati = GestoreAcque.getAllGestoriConUtenteAssociato(db,0,false);
				context.getRequest().getSession().setAttribute("TuttiGestoriImportatiConUtentiEventuali",tuttiGestoriImportati);
				
				toRet = "ImportMassivo"; 
				
				
			}
			
			/*import punti prelievo per un gestore specifico (quello associato all'utente loggato) */
			else if(((String)parts.get("tipo_richiesta")).equalsIgnoreCase("gestori_acque_rete_import_punti_prelievo"))
			{
				GestoreAcque gest = ((GestoreAcque)context.getRequest().getSession().getAttribute("GestoreAcque")) ;
				
				if(gest == null)
				{
					throw new Exception("Gestore Acque non trovato in sessione");
				}
				
				ArrayList<String> esitiErroriParsingfileAL = new ArrayList<String>();
				StringBuffer esitiErroriParsingfileSB = new StringBuffer("");
				StringBuffer esitiInsert = new StringBuffer("");
				if(parts.get("file1") instanceof FileInfo)
				{
					controlloFormatoNomeFile(((String)parts.get("tipo_richiesta")), fileName,null);
					/*if(controlloNomeFileEsistente(fileName,db)>0)
						throw new Exception("Nome file giÃ  presente tra gli import precedenti");*/
					
					fullPath = pathTemp+fileName;
					
					String nomeUltimoFile = getNomeUltimoFile(((String)parts.get("tipo_richiesta")), db, ((UserBean)context.getSession().getAttribute("User")).getUserId());
					Date dataUltimoFile = getDataDaNomeFile((String)parts.get("tipo_richiesta"), nomeUltimoFile, null);
					Date dataFileAttuale = getDataDaNomeFile((String)parts.get("tipo_richiesta"), fileName, null);
					if(dataUltimoFile!=null && dataFileAttuale.compareTo(dataUltimoFile)<0)
						throw new Exception("Data del file Ã¨ inferiore o uguale alla data dell'ultimo file inviato per Import Punti Prelievi");
					
					
//					FileInputStream fis = new FileInputStream(new File(fullPath));
//					XSSFWorkbook wb = new XSSFWorkbook(fis);
					org.apache.poi.ss.usermodel.Workbook wb = WorkbookFactory.create(new File(fullPath));
					
					String[] colonneIntestazione = new String[] {"Comune","Indirizzo","Denominazione","Tipologia","Ente Gestore","Stato","codice","latitudine","longitudine"};
					if(!controlloIntestazioneFile(wb, colonneIntestazione, 0))
						throw new Exception("Il file ha un'intestazione non valida. Controllare il file di esempio");
					
					ArrayList<PuntoPrelievo> returned = ExcelParserGestoriAcque.parseSheetPerImportGestoreLoggato(wb, db,esitiErroriParsingfileAL,gest);
					esitiErroriParsingfileSB = postProcessEsiti(esitiErroriParsingfileAL);
				    
					for(PuntoPrelievo ppNuovo : returned)
					{
						String esito = ppNuovo.insert(db);
						esitiInsert.append(esito);
					}
					logImport((((String)parts.get("tipo_richiesta"))), db, fInfo,(UserBean)context.getSession().getAttribute("User"), fullPath,null,codDocumento,esitiInsert,esitiErroriParsingfileSB);
					 
				}
				
				context.getRequest().setAttribute("esitiErroriParsingFile", esitiErroriParsingfileSB);
				context.getRequest().setAttribute("esitiInsert", esitiInsert);
				
				toRet = "AggiuntaPuntiPrelievoGestoreSpecifico"; 
				
				
			}
			/*import di controlli associati ai punti di prelievo del gestore associato all'utente loggato */
			else if(((String)parts.get("tipo_richiesta")).equalsIgnoreCase("gestori_acque_rete_import_controlli_interni"))
			{
				UserBean user=(UserBean)context.getSession().getAttribute("User");
				GestoreAcque gest = ((GestoreAcque)context.getRequest().getSession().getAttribute("GestoreAcque")) ;
				if(gest == null)
				{
					throw new Exception("Gestore Acque non trovato in sessione");
				}
				
				String tipo_decreto = (String)parts.get("tipo_decreto");

				ArrayList<String> esitiErroriParsingfileAL = new ArrayList<String>();
				StringBuffer esitiErroriParsingfileSB = new StringBuffer("");
				StringBuffer esitiInsert = new StringBuffer("");
				if(parts.get("file1") instanceof FileInfo)
				{
					controlloFormatoNomeFile(((String)parts.get("tipo_richiesta")), fileName,tipo_decreto);
					/*if(controlloNomeFileEsistente(fileName,db)>0)
						throw new Exception("Nome file giÃ  presente tra gli import precedenti");*/
					fullPath = pathTemp+fileName;
					 
					String nomeUltimoFile = getNomeUltimoFile(((String)parts.get("tipo_richiesta"))+"_"+tipo_decreto, db, ((UserBean)context.getSession().getAttribute("User")).getUserId());
					Date dataUltimoFile = getDataDaNomeFile((String)parts.get("tipo_richiesta"), nomeUltimoFile, tipo_decreto);
					Date dataFileAttuale = getDataDaNomeFile((String)parts.get("tipo_richiesta"), fileName, tipo_decreto);
					if(dataUltimoFile!=null && dataFileAttuale.compareTo(dataUltimoFile)<0)
						throw new Exception("Data del file Ã¨ inferiore o uguale alla data dell'ultimo file inviato per Import Controlli Interni decreto " + tipo_decreto);
					
//					FileInputStream fis = new FileInputStream(new File(fullPath));
//					XSSFWorkbook wb = new XSSFWorkbook(fis);
					org.apache.poi.ss.usermodel.Workbook wb = WorkbookFactory.create(new File(fullPath));
					HashMap<String,PuntoPrelievo> controlliPerPP = null;
					if(tipo_decreto.equals("31"))
					{
						String[] colonneIntestazione = new String[] {"CodiceGISA Punto Prelievo","data prelievo","ora prelievo","controllo di routine(PARTE A)","controllo di verifica(PARTE B)","controllo di replica","controllo di Ricerca Fitosanitari","cloro","temperatura","Esito","Parametri non conformi","Note"};
						if(!controlloIntestazioneFile(wb, colonneIntestazione, 0))
							throw new Exception("Il file ha un'intestazione non valida. Controllare il file di esempio");
						controlliPerPP = ExcelParserGestoriAcque.parseSheetPerImportControlliDecreto31(wb, db,esitiErroriParsingfileAL,gest,user);
					}
					else if(tipo_decreto.equals("28"))
					{
						String[] colonneIntestazione = new String[] {"CodiceGISA Punto Prelievo", "data prelievo", "ora prelievo", "Finalità della misura", "Nota su finalità della misura", "Motivo del prelievo", "Nota sul motivo del prelievo", "Denominazione della zona di Fornitura", "Gestore ZdF", "Tipo di acqua", "Note", "Numero di prelievi programmati all'anno (per punto di prelievo)", "Chi ha effettuato il prelievo", "Attività alfa totale (Bq/kg)", "", "Incertezza sull' Attività alfa totale (Bq/kg)", "Data della misura", "Laboratorio che ha effettuato la misura", "Metodo di prova utilizzato", "Attività beta totale (Bq/kg)", "", "Incertezza sull' Attività beta totale (Bq/kg)", "Data della misura", "Laboratorio che ha effettuato la misura", "Metodo di prova utilizzato", "Attività beta residua (Bq/kg)", "", "Incertezza sull' Attività beta residua (Bq/kg)", "Data della misura", "Laboratorio che ha effettuato la misura", "Metodo di prova utilizzato", "Concentrazione di 222Rn (Bq/l)", "", "Incertezza sulla concentrazione di 222Rn (Bq/l)", "Data della misura", "Laboratorio che ha effettuato la misura", "Metodo di prova utilizzato", "Concentrazione di 3H (Bq/l)", "", "Incertezza sulla concentrazione di 3H (Bq/l)", "Data della misura", "Laboratorio che ha effettuato la misura", "Metodo di prova utilizzato"};
						if(!controlloIntestazioneFile(wb, colonneIntestazione, 2))
							throw new Exception("Il file ha un'intestazione non valida. Controllare il file di esempio");
						controlliPerPP = ExcelParserGestoriAcque.parseSheetPerImportControlliDecreto28(wb, db,esitiErroriParsingfileAL,gest,user);
					}
					esitiErroriParsingfileSB = postProcessEsiti(esitiErroriParsingfileAL);
					 
					for(String ppKey : controlliPerPP.keySet())
					{
						PuntoPrelievo pp = controlliPerPP.get(ppKey); 
						for(ControlloInterno nuovoCI : pp.getControlliInterni())
						{
							String esito = nuovoCI.insert(db,user);
							esitiInsert.append(esito);
						}
						
					}
					logImport((((String)parts.get("tipo_richiesta"))), db, fInfo,(UserBean)context.getSession().getAttribute("User"), fullPath, tipo_decreto, codDocumento, esitiInsert, esitiErroriParsingfileSB);
					 
				}
				
				context.getRequest().setAttribute("esitiErroriParsingFile", esitiErroriParsingfileSB);
				context.getRequest().setAttribute("esitiInsert", esitiInsert);
				
				toRet = "AggiuntaControlliInterniGestoreSpecifico";
			}
			else
			{
				toRet = "SystemError";
			}
			
			//
			
			context.getRequest().setAttribute("msg_import","Import Effettuato Con Successo");
			
			
			
			return toRet;
		}
		catch(Exception ex)
		{
			context.getRequest().setAttribute("Error", ex.getMessage());
			ex.printStackTrace();
			return "SystemError";
		}
		finally
		{
			freeConnection(context, db);
		}
		
			
	}
	
	//siccome alcuni di questi metodi, dopo l'esecuzione devono ricaricare la vagina iniziale del dpat, e visto che questa richiede alcuni attributi 
	//ricevuti dalla request, glieli risettiamo in questo metodo
	private void aggiungiAttributiAggiuntiviARequest(ActionContext context,Connection db) throws SQLException
	{
		
		

		ArrayList<DpatIstanza> anniList = new ArrayList<DpatIstanza>();
		
		//ripreparo lista asl
		LookupList siteList = new LookupList(db, "lookup_site_id");
		siteList.addItem(-1, "--Seleziona--");
		siteList.remove(siteList.size() - 1);

		//ripreparo lista anni
		String sql = "select *  from dpat_istanza where trashed_date is null   order by anno";
		PreparedStatement pst = db.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {

			DpatIstanza ist = new DpatIstanza();
			ist.buildRecord(rs);
			anniList.add(ist);
		}
		rs.close();
		pst.close();
		
		//setto come attributi di request la lista anni e la lista asl
		context.getRequest().setAttribute("anniList", anniList);
		context.getRequest().setAttribute("siteList", siteList);

		
		
	}
	
	
	private void inviaDpatADocumentale(ActionContext context,String fullPath,HashMap<String,String> valori) throws IOException, SQLException
	{
		GestioneAllegatiUpload gestDocu = new GestioneAllegatiUpload();
		
		
		File file = new File(fullPath);
		byte []buffer = new byte[(int) file.length()];
		InputStream ios = null;

		ios = new FileInputStream(file);
		if (ios.read(buffer) == -1) {
			throw new IOException("EOF reached while trying to read the whole file");
		}  
		
		
		gestDocu.setBa(buffer);
		gestDocu.setFileDimension(valori.get("dimensione"));
		gestDocu.setFilename(valori.get("filename"));
		gestDocu.setOggetto(valori.get("asl")+"/"+valori.get("anno")+"/"+valori.get("tipoOperazione") );
		
		//cabl
		gestDocu.setOrgId(999);
		gestDocu.setActionOrigine("GenerazioneExcel");
		
		gestDocu.chiamaServerDocumentale(context);
		    
		
		
	}
	
	
	private void inviaRegistroADocumentale(ActionContext context,String fullPath,HashMap<String,String> valori) throws IOException, SQLException
	{
		GestioneAllegatiUpload gestDocu = new GestioneAllegatiUpload();
		
		
		File file = new File(fullPath);
		byte []buffer = new byte[(int) file.length()];
		InputStream ios = null;

		ios = new FileInputStream(file);
		if (ios.read(buffer) == -1) {
			throw new IOException("EOF reached while trying to read the whole file");
		}  
		
		
		gestDocu.setBa(buffer);
		gestDocu.setFileDimension(valori.get("dimensione"));
		gestDocu.setFilename(valori.get("filename"));
		gestDocu.setOggetto("registro trasgressori per tutti gli anni");
		
		//cabl
		gestDocu.setOrgId(998);
		gestDocu.setActionOrigine("GenerazioneExcel");
		
		gestDocu.chiamaServerDocumentale(context);
		    
		
		
	}
	
	
	/*utility function per togliere tutti gli esiti che indicano che la riga e' vuota, che sono dovuti alle righe in coda dei file excel che
	 * non danno nessuna informazione
	 */
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
	
	private void controlloFormatoNomeFile(String tipoRichiesta, String nomeFile,String tipo_decreto) throws Exception
	{
		String nomeFormatoFile_1parte = "";
		if(tipoRichiesta.equalsIgnoreCase("gestori_acque_rete_import_punti_prelievo"))
			nomeFormatoFile_1parte = "punti_prelievo_";
		else
			nomeFormatoFile_1parte = "controlli_interni_" + tipo_decreto + "_";
			
		if(nomeFile!=null && !nomeFile.equals("") && nomeFile.indexOf(nomeFormatoFile_1parte)!=0)
			throw new Exception("Formato nome file non valido");
		String data = nomeFile.replace(nomeFormatoFile_1parte, "");
		if(data.indexOf(".xls")<0)
			throw new Exception("Formato nome file non valido");
		else
			data=data.split(".xls")[0];
		if(data.length()!=8)
			throw new Exception("Formato nome file non valido");
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		sdf.setLenient(false);
		try
		{
			sdf.parse(data);
		}
		catch(ParseException ex)
		{
			throw new Exception("Formato nome file non valido");
		}
	}
	
	private void logImport(String tipo_richiesta, Connection db, FileInfo fileInfo, UserBean utente, String nomeFileCompleto, String tipoDecreto, String codDocumento, StringBuffer esitiInsert, StringBuffer esitiParsingFile) throws SQLException  
	{
		PreparedStatement pst = null;
		if(tipoDecreto!=null)
			tipo_richiesta+=tipo_richiesta+"_"+tipoDecreto;
		
		int gestoreAcque = 0;
		pst = db.prepareStatement(" select id_gestore_acque_anag from users_to_gestori_acque  where user_id = ?");
		pst.setInt   (1, utente.getUserId());
		ResultSet rs = pst.executeQuery();
		if(rs.next())
			gestoreAcque = rs.getInt(1);
		
		pst = db.prepareStatement(" insert into gestori_acque_log_import(nome_file,tipo_richiesta,data_import,id_utente,nome_file_completo,gestore_acque,cod_documento,esito,errore_insert,errore_parsing_file) values(?,?,current_timestamp,?,?,?,?,?,?,?) ");
		pst.setString(1, fileInfo.getClientFileName());
		pst.setString(2, tipo_richiesta);
		pst.setInt   (3, utente.getUserId());
		pst.setString(4,nomeFileCompleto);
		pst.setInt   (5, gestoreAcque);
		pst.setString(6, codDocumento);
		pst.setBoolean(7, esitiInsert.toString().equals("") && esitiParsingFile.toString().equals("") );
		pst.setString(8, esitiInsert.toString());
		pst.setString(9, esitiParsingFile.toString());
		pst.execute();
		rs.close();
		pst.close();
			
	
	}
	
	private int controlloNomeFileEsistente(String fileName, Connection db) throws SQLException  
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		int count = 0;
		pst = db.prepareStatement(" select count(*) as count from gestori_acque_log_import where nome_file = ?;");
		pst.setString(1, fileName);
		pst.executeQuery();
		rs = pst.getResultSet();
		if(rs.next())
			count = rs.getInt("count");
		pst.close();
		rs.close();
		return count;
			
	
	}
	
	private String getNomeUltimoFile(String tipo_richiesta, Connection db, int idUtente) throws SQLException  
	{
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		String nomeFile = null;
		pst = db.prepareStatement(" select nome_file  from gestori_acque_log_import " + 
							      " where tipo_richiesta = ? and id_utente = ? " +
							      " order by data_import desc limit 1 ");
		pst.setString(1, tipo_richiesta);
		pst.setInt(2, idUtente);
		pst.executeQuery();
		rs = pst.getResultSet();
		if(rs.next())
			nomeFile = rs.getString("nome_file");
		pst.close();
		rs.close();
		return nomeFile;
	}
	
	private boolean controlloIntestazioneFile(org.apache.poi.ss.usermodel.Workbook wb, String[] intestazioneColonne, int rigaIntestazione) throws EccezioneDati
	{
		org.apache.poi.ss.usermodel.Row row = wb.getSheetAt(0).getRow(rigaIntestazione); 
		
		for(int i=0;i<intestazioneColonne.length;i++)
		{
			String intestazioneAttesa = intestazioneColonne[i];
			String intestazioneFile = ExcelParserGestoriAcque.getCellValueSafe(row.getCell(i));
			if(!intestazioneAttesa.equalsIgnoreCase(intestazioneFile)){
				System.out.println("ERRORE SU CHECK COLONNE: "+intestazioneAttesa + " - " +intestazioneFile);
				return false;}
		}
		return true;
		
		
	}
	
	
	
	private Date getDataDaNomeFile(String tipoRichiesta, String nomeFile,String tipo_decreto) throws EccezioneDati, ParseException
	{
		if(nomeFile!=null)
		{
			String nomeFormatoFile_1parte = "";
			if(tipoRichiesta.equalsIgnoreCase("gestori_acque_rete_import_punti_prelievo"))
				nomeFormatoFile_1parte = "punti_prelievo_";
			else
				nomeFormatoFile_1parte = "controlli_interni_" + tipo_decreto + "_";
				
			String data = nomeFile.replace(nomeFormatoFile_1parte, "");
			data=data.split(".xls")[0];
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
			sdf.setLenient(false);
			return sdf.parse(data);
		}
		else
			return null;
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
}
