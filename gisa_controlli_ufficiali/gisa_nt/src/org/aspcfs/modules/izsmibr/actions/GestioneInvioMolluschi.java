/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.izsmibr.actions;

import it.izs.sinsa.services.AutenticazioneTO;
import it.izs.sinsa.services.AutorizzazioneTO;
import it.izs.sinsa.services.BusinessException_Exception;
import it.izs.sinsa.services.CampionePrelevatoWsTO;
import it.izs.sinsa.services.ContaminanteWsTO;
import it.izs.sinsa.services.Exception_Exception;
import it.izs.sinsa.services.InsertPrelievo;
import it.izs.sinsa.services.InsertPrelievoResponse;
import it.izs.sinsa.services.IzsWsException_Exception;
import it.izs.sinsa.services.PrelevatoreWsTO;
import it.izs.sinsa.services.Prelievi;
import it.izs.sinsa.services.PrelieviService;
import it.izs.sinsa.services.PrelievoAltreInformazioniWs;
import it.izs.sinsa.services.PrelievoWsTO;
import it.izs.sinsa.services.SOAPException_Exception;
import it.izs.sinsa.services.PrelievoAltreInformazioniWs.Informazioni;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.gestioneDocumenti.actions.GestioneAllegatiInvii;
import org.aspcfs.modules.izsmibr.base.CampioneMolluschi;
import org.aspcfs.modules.izsmibr.base.InvioMassivoMolluschi;
import org.aspcfs.modules.izsmibr.util.Ibr;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.RiepilogoImport;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;
import com.oreilly.servlet.MultipartRequest;

public class GestioneInvioMolluschi extends CFSModule{
	
	private static final int MAX_SIZE_REQ = 50000000;

	public String executeCommandToTestInvio(ActionContext context)
	{
		
		
		return "InvioMolluschiOK" ;
	}
	
	
	
	
	public String executeCommandToImportMolluschi(ActionContext context)
	{
		return "ToImportMolluschiOK";
	}
	
	
	
	  public String executeCommandListaInviiMolluschi(ActionContext context){
		  Connection db = null; 
		  try {
				
				db = getConnection(context);
				
				InvioMassivoMolluschi invioMassivo = new InvioMassivoMolluschi();
				context.getRequest().setAttribute("ListaInvii", invioMassivo.getListaInviMolluschi(db, context));
				
				
			} catch (Exception e) {	
				context.getRequest().setAttribute("Error", e);
				e.printStackTrace();
				return ("SystemError");
			} finally {
				this.freeConnection(context, db);
			}
		  
		  return "ListaInviMolluschiOK";
			
		  
	  }
	

	  public String executeCommandAllImportRecordsMolluschi(ActionContext context){
			Connection db = null; 
			try {
				
				db = getConnection(context);
				
				
				
				RiepilogoImport rImport = new RiepilogoImport();
				String data = (String) context.getRequest().getAttribute("dataEstrazione");
				
				int idInvioMassivo = Integer.parseInt(context.getParameter("idInvio"));
				InvioMassivoMolluschi invioMassivo = new InvioMassivoMolluschi();
				invioMassivo.queryRecord(db, idInvioMassivo);
				
				ArrayList<CampioneMolluschi> listaRecord = invioMassivo.getAllRecords(db);
				
				
				context.getRequest().setAttribute("allRecords", listaRecord);
				context.getRequest().setAttribute("InvioMassivo", invioMassivo);

				
				
			} catch (Exception e) {	
				context.getRequest().setAttribute("Error", e);
				e.printStackTrace();
				return ("SystemError");
			} finally {
				this.freeConnection(context, db);
			}
			
			if (context.getRequest().getParameter("layout")!=null && context.getRequest().getParameter("layout").equals("style") )
				return ("MolluschiUploadListStampaOK");
						
			return ("MolluschiUploadListOK");
			
		}
	  
	  private static final int BYTES_DOWNLOAD = 1024;
	  public String executeCommandEsportaCsv(ActionContext context){
			Connection db = null; 
			try {
				
				db = getConnection(context);
				
				
				context.getResponse().setContentType("text/plain");                  
				
				int idInvioMassivo = Integer.parseInt(context.getParameter("idInvio"));
				InvioMassivoMolluschi invioMassivo = new InvioMassivoMolluschi();
				invioMassivo.queryRecord(db, idInvioMassivo);
				
				ArrayList<CampioneMolluschi> listaRecord = invioMassivo.getAllRecords(db);
				
				String disposition = "attachment; fileName=record_ko_"+invioMassivo.getData()+".txt";
                context.getResponse().setHeader("Content-Disposition", disposition);

				String contenuto = "";
				for (CampioneMolluschi rec :listaRecord )
				{
								
					if (rec.getEsito_invio().equalsIgnoreCase("ko"))
					{
					contenuto+=rec.getPianoCodice()+";"+rec.getNumeroSchedaPrelievo()+";"+rec.getDataPrel()+";"+rec.getLuogoPrelievoCodice()+";"+rec.getMetodoCampionamentoCodice()+";"+rec.getMotivoCodice()+";"+rec.getPrelNome()+";"+rec.getPrelCognome()+";"+rec.getPrelCodFiscale()+";"+rec.getSitoCodice()+";"+rec.getComuneCodiceIstatParziale()+";"+rec.getSiglaProvincia()+";"+rec.getLaboratorioCodice()+";"+rec.getLatitudine()+";"+rec.getLongitudine()+";"+rec.getCodiceContaminante()+";"+rec.getProgressivoCampione()+";"+rec.getFoodexCodice()+";"+rec.getProfFondale()+";"+rec.getClassificazioneDellaZonaDiMareCe8542004()+System.lineSeparator();
					
					} 
					
				}
				
				
				InputStream input = new ByteArrayInputStream(contenuto.getBytes("UTF8"));

		        int read = 0;
		        byte[] bytes = new byte[BYTES_DOWNLOAD];
		        OutputStream os = context.getResponse().getOutputStream();

		        while ((read = input.read(bytes)) != -1) {
		            os.write(bytes, 0, read);
		        }
		        os.flush();
		        os.close();
				
			} catch (Exception e) {	
				context.getRequest().setAttribute("Error", e);
				e.printStackTrace();
				return ("SystemError");
			} finally {
				this.freeConnection(context, db);
			}
			
		
						
			return ("-none-");
			
		}
	  
	  private String StringPadding(String s,int width)
	  {
		  StringBuffer result = new StringBuffer("");
		  if (s==null)
			  s="";
		     for( int i = 0; i < width-s.length(); i++ )
		        result.append( " " );
		     result.append( s );
		     return result.toString();
	  }
	
	
	public String executeCommandImportMolluschi(ActionContext context) throws Exception
	{
		boolean ritornareAllaPagina = true;
		int userId = getUserId(context);
		ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute("ConnectionElement");
		UserBean u = (UserBean)context.getSession().getAttribute("User");
		ConnectionPool cp = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");
		String contesto = (String)context.getServletContext().getAttribute("SUFFISSO_TAB_ACCESSI");
		ConnectionPool cpRo = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPoolRO");
		HttpSession session = context.getRequest().getSession();
		ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
		String browser = context.getBrowser();
		Object systemStatus = context.getServletContext().getAttribute("SystemStatus");
		String actionName = context.getAction().getActionName();
		String actionClassName = context.getAction().getActionClassName();
		String remoteAddr = context.getRequest().getRemoteAddr();
		String command = context.getCommand();
		String ambiente = (String) context.getServletContext().getAttribute("ambiente");
		Hashtable systemStatusHash = (Hashtable) context.getServletContext().getAttribute("SystemStatus");
		
		System.out.println("IMPORT MOLLUSCHI. Utente: " + userId + ", Data: " + new Date() + "");
		Connection db  = null ;
		
		if(context.getSession().getAttribute("MolluschiImport")==null)
		{
		System.out.println("IMPORT MOLLUSCHI. Utente: " + userId + ", Data: " + new Date() + ". INIZIO");
		context.getSession().setAttribute("MolluschiImport", "START");
		try
		{
			
//			String filePath = this.getPath(context, "molluschi");
//			File theDir = new File(filePath);
//	        theDir.mkdirs();
			
			String filePath = getWebInfPath(context,"tmp_molluschi");

			MultipartRequest multi = new MultipartRequest(context.getRequest(), filePath,MAX_SIZE_REQ,"UTF-8");
			
			db = getConnection(ce, u, cp, contesto, cpRo, session, prefs, context, systemStatus, command, actionName, actionClassName, remoteAddr);
			
			boolean autocommitt = db.getAutoCommit();
			if (autocommitt==true)
				db.setAutoCommit(false);
			
			/*INSERIMENTO NUOVO INVIO*/
			System.out.println("IMPORT MOLLUSCHI. Utente: " + userId + ", Data: " + new Date() + ". INSERIMENTO.......");
			InvioMassivoMolluschi invio = new InvioMassivoMolluschi();
			invio.setData(new Timestamp(System.currentTimeMillis()));
			invio.setInviato_da(userId);
			invio.insert(db);
			System.out.println("IMPORT MOLLUSCHI. Utente: " + userId + ", Data: " + new Date() + ". .....INSERIMENTO RIUSCITO");
			
			/*ALLEGO IL FILE INVOCANDO IL DOCUMENTALE*/
			GestioneAllegatiInvii gestioneAllegati = new GestioneAllegatiInvii();
			gestioneAllegati.setIdInvioMolluschi(invio.getId());
			gestioneAllegati.setTipoAllegato("MOLLUSCHICA");
			File file1 = (File) multi.getFile("file1");
			
			if( ! file1.getName().endsWith(".xls") && !file1.getName().endsWith(".xlsx") )
			{
				db.rollback();
				autocommitt = db.getAutoCommit();
				if (autocommitt==false)
					db.setAutoCommit(true);
				
				context.getRequest().setAttribute("ImportKoError", "Il file caricato deve avere Estensione xls o xslx");
				context.getSession().removeAttribute("MolluschiImport");
				return "ImportMolluschiKO";
			}
			
			
			autocommitt = db.getAutoCommit();
			if (autocommitt==false)
			{
				db.commit();
				db.setAutoCommit(true);	
				System.out.println("IMPORT-MOLLUSCHI - Committato");
			}
				
			this.freeConnection(db, ce, u, cp, contesto, cpRo, session, prefs, context, systemStatus, command, actionName, actionClassName, remoteAddr, browser);
			/*LEGGO IL FILE E LO INSERISO NELLA TABELLA import_ibr */
			
			FileInputStream fileInputStream = new FileInputStream(file1);

			Workbook wb = WorkbookFactory.create(fileInputStream);
			Sheet sheet = wb.getSheetAt(0);
			
			System.out.println("IMPORT MOLLUSCHI. Utente: " + userId + ", Data: " + (new SimpleDateFormat("dd/MM/yyyy HH24:mm:ss.SSS")).format(new Date()) + ". INIZIO ELABORAZIONE FILE");
			for ( int i = 1;i<=sheet.getLastRowNum();i++)
			{
				System.out.println("IMPORT MOLLUSCHI. Utente: " + userId + ", Data: " + (new SimpleDateFormat("dd/MM/yyyy HH24:mm:ss.SSS")).format(new Date()) + ". RIGA " + i);
				Row row = sheet.getRow(i);
				int fcell = row.getFirstCellNum();// first cell number of excel
				int lcell = row.getLastCellNum(); //last cell number of excel
				if(containsValue(row, fcell, lcell) == true)
				{
					CampioneMolluschi caMolluschi =null;
					try
					{
						System.out.println("IMPORT MOLLUSCHI. Utente: " + userId + ", Data: " + (new SimpleDateFormat("dd/MM/yyyy HH24:mm:ss.SSS")).format(new Date()) + ". RIGA " + i + ": APERTURA CONNESSIONE");
						db =getConnection(ce, u, cp, contesto, cpRo, session, prefs, context, systemStatus, command, actionName, actionClassName, remoteAddr);
						System.out.println("IMPORT MOLLUSCHI. Utente: " + userId + ", Data: " + (new SimpleDateFormat("dd/MM/yyyy HH24:mm:ss.SSS")).format(new Date()) + ". RIGA " + i + ": APERTURA CONNESSIONE FINITA");
						
						System.out.println("IMPORT MOLLUSCHI. Utente: " + userId + ", Data: " + (new SimpleDateFormat("dd/MM/yyyy HH24:mm:ss.SSS")).format(new Date()) + ". RIGA " + i + ": COSTRUZIONE OGGETTO");
						caMolluschi = Ibr.getObjectFromRow(row);
						System.out.println("IMPORT MOLLUSCHI. Utente: " + userId + ", Data: " + (new SimpleDateFormat("dd/MM/yyyy HH24:mm:ss.SSS")).format(new Date()) + ". RIGA " + i + ": COSTRUZIONE OGGETTO FINITA");
						System.out.println("IMPORT MOLLUSCHI. Utente: " + userId + ", Data: " + (new SimpleDateFormat("dd/MM/yyyy HH24:mm:ss.SSS")).format(new Date()) + ". RIGA " + i + ": INVIO IN BDN IN CORSO....");
						caMolluschi.sendToBdn();
						System.out.println("IMPORT MOLLUSCHI. Utente: " + userId + ", Data: " + (new SimpleDateFormat("dd/MM/yyyy HH24:mm:ss.SSS")).format(new Date()) + ". RIGA " + i + ": ....INVIO RIUSCITO");
						
						autocommitt = db.getAutoCommit();
						if (autocommitt==true)
							db.setAutoCommit(false);		
	
						System.out.println("IMPORT MOLLUSCHI. Utente: " + userId + ", Data: " + (new SimpleDateFormat("dd/MM/yyyy HH24:mm:ss.SSS")).format(new Date()) + ". RIGA " + i + ": ESECUZIONE INSERT....");
						caMolluschi.insert(db, invio.getId());
						System.out.println("IMPORT MOLLUSCHI. Utente: " + userId + ", Data: " + (new SimpleDateFormat("dd/MM/yyyy HH24:mm:ss.SSS")).format(new Date()) + ". RIGA " + i + ": ....INSERT RIUSCITA");
						
						if(autocommitt==false)
							db.commit();
						autocommitt = db.getAutoCommit();
						if (autocommitt==false)
							db.setAutoCommit(true);			
						this.freeConnection(db, ce, u, cp, contesto, cpRo, session, prefs, context, systemStatus, command, actionName, actionClassName, remoteAddr, browser);
						
						System.out.println("IMPORT MOLLUSCHI. Utente: " + userId + ", Data: " + (new SimpleDateFormat("dd/MM/yyyy HH24:mm:ss.SSS")).format(new Date()) + ". RIGA " + i + ": CHIUSA CONNESSIONE");
						
						System.out.println("IMPORT-MOLLUSCHI - Committato");
						 
					}	
					catch(Exception e)
					{
						
						try
						{
							db =getConnection(ce, u, cp, contesto, cpRo, session, prefs, context, systemStatus, command, actionName, actionClassName, remoteAddr);
							db.prepareStatement("update invio_massivo_molluschi set stato_esecuzione = 2, time_end = current_timestamp, errore_elaborazione = 'riga " + i + " - " + e.getMessage() + ".' where id ="+invio.getId()).execute();
							//db.prepareStatement("delete from invio_massivo_molluschi where id ="+invio.getId()).execute();
							//db.prepareStatement("delete from import_ca_molluschi where id_invio_massivo_molluschi  ="+invio.getId()).execute();
							freeConnection(db, ce, u, cp, contesto, cpRo, session, prefs, context, systemStatus, command, actionName, actionClassName, remoteAddr, browser);
							context.getRequest().setAttribute("ImportKoError", e.getMessage());
							i = i+1;
							context.getRequest().setAttribute("numeroRiga", i);
							if(context!=null && context.getSession()!=null)
							{
								try
								{
									context.getSession().removeAttribute("MolluschiImport");
								}
								catch(Exception ex)
								{
									ritornareAllaPagina = false;
									System.out.println("Non è stato possibile rimuovere l'attributo MolluschiImport dalla sessione perchè la sessione è scaduta");
								}
							}
							
							e.printStackTrace();
							return "ImportMolluschiKO";
						}
						catch(Exception ex)
						{
							context.getRequest().setAttribute("ImportKoError", e.getMessage());
							i = i+1;
							context.getRequest().setAttribute("numeroRiga", i);
							if(context!=null && context.getSession()!=null)
							{
								try
								{
									context.getSession().removeAttribute("MolluschiImport");
								}
								catch(Exception ex2)
								{
									ritornareAllaPagina = false;
									System.out.println("Non è stato possibile rimuovere l'attributo MolluschiImport dalla sessione perchè la sessione è scaduta");
								}
							}
							ex.printStackTrace();
							return "ImportMolluschiKO";
							
						}
					}
				}
			}
			
			gestioneAllegati.setFileDaCaricare(file1);
			gestioneAllegati.setOggetto("INVIO PRELIEVI_MOLLUSCHI_"+invio.getData());
			gestioneAllegati.allegaFile(context, userId, ambiente, u, systemStatusHash, ce);
			db =getConnection(ce, u, cp, contesto, cpRo, session, prefs, context, systemStatus, command, actionName, actionClassName, remoteAddr);
			invio.setStatoEsecuzione(db);
			freeConnection(db, ce, u, cp, contesto, cpRo, session, prefs, context, systemStatus, command, actionName, actionClassName, remoteAddr, browser);
			
			if(context!=null && context.getSession()!=null)
			{
				try
				{
					context.getSession().removeAttribute("MolluschiImport");
				}
				catch(Exception ex)
				{
					ritornareAllaPagina = false;
					System.out.println("Non è stato possibile rimuovere l'attributo MolluschiImport dalla sessione perchè la sessione è scaduta");
				}
			}
			else
				ritornareAllaPagina = false;
			
		}
		catch(SQLException e)	
		{
			if(context!=null && context.getSession()!=null)
			{
				try
				{
					context.getSession().removeAttribute("MolluschiImport");
				}
				catch(Exception ex)
				{
					ritornareAllaPagina = false;
					System.out.println("Non è stato possibile rimuovere l'attributo MolluschiImport dalla sessione perchè la sessione è scaduta");
				}
			}
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			if(context!=null && context.getSession()!=null)
			{
				try
				{
					context.getSession().removeAttribute("MolluschiImport");
				}
				catch(Exception ex)
				{
					System.out.println("Non è stato possibile rimuovere l'attributo MolluschiImport dalla sessione perchè la sessione è scaduta");
				}
			}
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			if(context!=null && context.getSession()!=null)
			{
				try
				{
					context.getSession().removeAttribute("MolluschiImport");
				}
				catch(Exception ex)
				{
					ritornareAllaPagina = false;
					System.out.println("Non è stato possibile rimuovere l'attributo MolluschiImport dalla sessione perchè la sessione è scaduta");
				}
			}
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			if(context!=null && context.getSession()!=null)
			{
				try
				{
					context.getSession().removeAttribute("MolluschiImport");
				}
				catch(Exception ex)
				{
					ritornareAllaPagina = false;
					System.out.println("Non è stato possibile rimuovere l'attributo MolluschiImport dalla sessione perchè la sessione è scaduta");
				}
			}
			e.printStackTrace();
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			if(context!=null && context.getSession()!=null)
			{
				try
				{
					context.getSession().removeAttribute("MolluschiImport");
				}
				catch(Exception ex)
				{
					ritornareAllaPagina = false;
					System.out.println("Non è stato possibile rimuovere l'attributo MolluschiImport dalla sessione perchè la sessione è scaduta");
				}
			}
			e.printStackTrace();
		} 
		//Se è scaduta la sessione non ritorno niente
		if(ritornareAllaPagina)
			return executeCommandListaInviiMolluschi(context);
		else
			return "";
		}
		return "ImportInCorso";
		
		
		
	}
	
	
	
	
	public boolean containsValue(Row row, int fcell, int lcell) 
	{
	    boolean flag = false;
	    for (int i = fcell; i < lcell; i++) 
	    {
	    	if (StringUtils.isEmpty(String.valueOf(row.getCell(i))) == true || 
	    	    StringUtils.isWhitespace(String.valueOf(row.getCell(i))) == true || 
	    	    StringUtils.isBlank(String.valueOf(row.getCell(i))) == true || 
	    	    String.valueOf(row.getCell(i)).length() == 0 || 
	    	    row.getCell(i) == null) {} 
	    	else 
	    	{
	    		flag = true;
	    	}
	    }
	    return flag;
	}

}
