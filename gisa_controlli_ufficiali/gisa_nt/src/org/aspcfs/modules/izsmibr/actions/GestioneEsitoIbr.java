/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.izsmibr.actions;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.xml.bind.JAXBException;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;
import javax.xml.ws.WebServiceFeature;

import org.apache.commons.fileupload.FileUploadException;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.gestioneDocumenti.actions.GestioneAllegatiInvii;
import org.aspcfs.modules.gestioneDocumenti.actions.GestioneDocumenti;
import org.aspcfs.modules.izsmibr.base.DsESITOIBRIUS;
import org.aspcfs.modules.izsmibr.base.GisaDsEsitoIBRA;
import org.aspcfs.modules.izsmibr.base.InvioMassivoIbr;
import org.aspcfs.modules.izsmibr.service.GisaWsIbr;
import org.aspcfs.modules.izsmibr.service.SOAPAutenticazione;
import org.aspcfs.modules.izsmibr.service.WsPianoIBR;
import org.aspcfs.modules.izsmibr.service.WsPianoIBRSoap;
import org.aspcfs.modules.izsmibr.util.Ibr;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.RiepilogoImport;

import com.darkhorseventures.framework.actions.ActionContext;
import com.oreilly.servlet.MultipartRequest;

public class GestioneEsitoIbr extends CFSModule {
	
	private static final int MAX_SIZE_REQ = 50000000;
	
	public String executeCommandToImportIbr(ActionContext context)
	{
		return "ToImportIBROK";
	}
	
	
	
	  public String executeCommandListaInviiIbr(ActionContext context){
		  Connection db = null; 
		  try {
				
				db = getConnection(context);
				
				InvioMassivoIbr invioMassivo = new InvioMassivoIbr();
				
				
				context.getRequest().setAttribute("ListaInvii", invioMassivo.getListaInviiIbr(db,context));
				
				
			} catch (Exception e) {	
				context.getRequest().setAttribute("Error", e);
				e.printStackTrace();
				return ("SystemError");
			} finally {
				this.freeConnection(context, db);
			}
		  
		  return "ListaInviIbrOK";
			
		  
	  }
	

	  public String executeCommandAllImportRecordsIBR(ActionContext context){
			Connection db = null; 
			try {
				
				db = getConnection(context);
				
				
				
				RiepilogoImport rImport = new RiepilogoImport();
				String data = (String) context.getRequest().getAttribute("dataEstrazione");
				
				int idInvioMassivo = Integer.parseInt(context.getParameter("idInvio"));
				InvioMassivoIbr invioMassivo = new InvioMassivoIbr();
				invioMassivo.queryRecord(db, idInvioMassivo);
				
				ArrayList<GisaDsEsitoIBRA> listaRecord = invioMassivo.getAllRecords(db);
				
				
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
				return ("IBRUploadListStampaOK");
						
			return ("IBRUploadListOK");
			
		}
	  
	  private static final int BYTES_DOWNLOAD = 1024;
	  public String executeCommandEsportaCsv(ActionContext context){
			Connection db = null; 
			try {
				
				db = getConnection(context);
				
				
				context.getResponse().setContentType("text/plain");                  
				
				int idInvioMassivo = Integer.parseInt(context.getParameter("idInvio"));
				InvioMassivoIbr invioMassivo = new InvioMassivoIbr();
				invioMassivo.queryRecord(db, idInvioMassivo);
				
				ArrayList<GisaDsEsitoIBRA> listaRecord = invioMassivo.getAllRecords(db);
				
				//String disposition = "attachment; fileName=record_ko_"+invioMassivo.getData()+".txt";
				String disposition = "attachment; fileName=record_ko_ok_"+invioMassivo.getData()+".txt";
                context.getResponse().setHeader("Content-Disposition", disposition);

				String tipoOperazione = "I";
				String contenuto = "";
				for (GisaDsEsitoIBRA rec :listaRecord )
				{
					//Unico bottone OK e KO. Richiesta di Avallone mail del 08/02/2017.	
					//if (rec.getEsito_invio().equalsIgnoreCase("ko"))
					//{
					tipoOperazione= "I";
					String codIstituto = StringPadding(rec.getRecord().getPARAMETERSLIST().get(0).getPCODICEISTITUTO(),2);
					String codSedeDiagnostica = StringPadding(rec.getRecord().getPARAMETERSLIST().get(0).getPCODICESEDEDIAGNOSTICA(),2);
					String codPrelievo = StringPadding(rec.getRecord().getPARAMETERSLIST().get(0).getPCODICEPRELIEVO(),17);
					String annoAcce = StringPadding(rec.getRecord().getPARAMETERSLIST().get(0).getPANNOACCETTAZIONE()+"",4);
					String numAcce = StringPadding(rec.getRecord().getPARAMETERSLIST().get(0).getPNUMEROACCETTAZIONE(),8);
					String codAzienda = StringPadding(rec.getRecord().getPARAMETERSLIST().get(0).getPCODICEAZIENDA(),8);
					String idFiscale = StringPadding(rec.getRecord().getPARAMETERSLIST().get(0).getPIDFISCALEPROPRIETARIO(),16);
					String specieAll = StringPadding(rec.getRecord().getPARAMETERSLIST().get(0).getPSPECIEALLEVATA(),4);
					String codiceCapo = StringPadding(rec.getRecord().getPARAMETERSLIST().get(0).getPCODICECAPO(),14);
					String dataPrel = StringPadding(rec.getRecord().getPARAMETERSLIST().get(0).getPDATAPRELIEVO(),8);
					String dataEsito = StringPadding(rec.getRecord().getPARAMETERSLIST().get(0).getPDATAESITO(),8);
					String esitoQualitat = StringPadding(rec.getRecord().getPARAMETERSLIST().get(0).getPESITOQUALITATIVO(),15);
					contenuto+=tipoOperazione+codIstituto+codSedeDiagnostica+codPrelievo+annoAcce+numAcce+codAzienda+idFiscale+specieAll+codiceCapo+dataPrel+dataEsito+esitoQualitat+rec.getErrore()+System.lineSeparator();
					//} 
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
	
	
	public String executeCommandImportIbr(ActionContext context) throws Exception
	{
		
		Connection db  = null ;
		try
		{
			
//			String filePath = this.getPath(context, "riunioni");
//			File theDir = new File(filePath);
//	        theDir.mkdirs();
			
			String filePath = getWebInfPath(context,"tmp_riunioni");
			
			MultipartRequest multi = new MultipartRequest(context.getRequest(), filePath,MAX_SIZE_REQ,"UTF-8");
			
			db = getConnection(context);
			
			boolean autocommitt = db.getAutoCommit();
			if (autocommitt==true)
				db.setAutoCommit(false);
			
			/*INSERIMENTO NUOVO INVIO*/
			InvioMassivoIbr invio = new InvioMassivoIbr();
			invio.setData(new Timestamp(System.currentTimeMillis()));
			invio.setInviato_da(getUserId(context));
			invio.insert(db);
			
			/*ALLEGO IL FILE INVOCANDO IL DOCUMENTALE*/
			GestioneAllegatiInvii gestioneAllegati = new GestioneAllegatiInvii();
			gestioneAllegati.setIdInvio(invio.getId());
			gestioneAllegati.setTipoAllegato("IBR");
			File file1 = (File) multi.getFile("file1");
			
			if( ! file1.getName().endsWith(".csv") && !file1.getName().endsWith(".txt") )
			{
				db.rollback();
			 autocommitt = db.getAutoCommit();
				if (autocommitt==false)
					db.setAutoCommit(true);
				
				context.getRequest().setAttribute("ImportKoError", "Attenzione! Il file caricato Deve avere Estensione CSV o TXT");
				return "ToImportIBROK";
			}
			
			db.commit();
			autocommitt = db.getAutoCommit();
			if (autocommitt==false)
				db.setAutoCommit(true);	
			this.freeConnection(context, db);
			/*LEGGO IL FILE E LO INSERISO NELLA TABELLA import_ibr */
			BufferedReader br = new BufferedReader(new FileReader(file1));
			String rigaLetta ="";
			
			
			while((rigaLetta=br.readLine())!=null)
			{
				DsESITOIBRIUS ibr =null;
				try
				{
					 ibr = Ibr.getObjectFromText(rigaLetta);
					 
					 
				}
				catch(Exception e)
				{
					if( db != null && db.isClosed()==false)
					{
					db.rollback();
					autocommitt = db.getAutoCommit();
					if (autocommitt==false)
						db.setAutoCommit(true);
					}
					context.getRequest().setAttribute("ImportKoError", "Attenzione! Il file caricato contiene record che non rispettano il Tracciato Record");
					return "ToImportIBROK";
				}
				GisaDsEsitoIBRA record = new GisaDsEsitoIBRA();
			
				
				
				
				try {
					
					String xmlRecord = Ibr.jaxbObjectToXML(ibr);
					record.setTracciato_record_richiesta(xmlRecord);
					record.setRecord(ibr);
					GisaWsIbr.sendRecordIBRBDN(xmlRecord,record);
					autocommitt = true ;
					db =getConnection(context);
					autocommitt = db.getAutoCommit();
					if (autocommitt==true)
						db.setAutoCommit(false);		
					
					
					record.insert(db, invio.getId());
					
					if(autocommitt==false)
						db.commit();
					autocommitt = db.getAutoCommit();
					if (autocommitt==false)
						db.setAutoCommit(true);			
					this.freeConnection(context, db);
					
				} catch (SOAPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
			}
			
			gestioneAllegati.setFileDaCaricare(file1);
			gestioneAllegati.setOggetto("INVIO IBR-IZSM_"+invio.getData());
			gestioneAllegati.allegaFile(context, getUserId(context));
			
			
			
			
			
		}
		catch(SQLException e)	
		{
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return executeCommandListaInviiIbr(context);
	}

}
