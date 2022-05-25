/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.report.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.modules.lineeattivita.base.LineeAttivita;
import org.aspcfs.modules.stampa_verbale_prelievo.base.Organization;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.campioni.actions.Campioni;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.Barcode;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.BarcodeCodabar;
import com.lowagie.text.pdf.BarcodeEAN;
import com.lowagie.text.pdf.BarcodeInter25;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import com.zeroio.webutils.FileDownload;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ManagePdfModules extends CFSModule{

	public String executeCommandPrintSelectedModules(ActionContext context) {
		   
	    String orgId = context.getRequest().getParameter("orgId");
	    String ticketId = context.getRequest().getParameter("ticketId");
	    
	    if(orgId != null && !orgId.equals("")){
	    	context.getRequest().setAttribute("orgId",orgId);
	    }
	    
	    if(ticketId != null && !ticketId.equals("")){
	    	context.getRequest().setAttribute("ticketId",ticketId);
	    }
	    
	    return ("PrintOK");
	}
	
	public String executeCommandPrenota(ActionContext context) {
		   
	    String orgId = context.getRequest().getParameter("orgId");
	    Connection db = null;
	    
	    try {
	    
	    	db = this.getConnection(context);
	    	
	    	LookupList quesiti_diagn = new LookupList(db, "quesiti_diagnostici_sigla");
	        quesiti_diagn.addItem(-1, "-- SELEZIONA VOCE --");
	        context.getRequest().setAttribute("Motivazione", quesiti_diagn);
	        
	    	
	    	LookupList siteList = new LookupList(db, "lookup_site_id");
	        siteList.addItem(-1, "-- SELEZIONA VOCE --");
	        siteList.addItem(Constants.INVALID_SITE,  "-- TUTTI --");
	        context.getRequest().setAttribute("SiteIdList", siteList);
	    	
	        Organization org = new Organization();
		    ResultSet rs = org.queryGetOsa(db, Integer.parseInt(orgId));
		    
		    while (rs.next()){
		    	org.setName(rs.getString("name"));
		    	org.setSiteId(rs.getInt("site_id"));
		    	org.setOrgId(rs.getInt("org_id"));
		    }
		    
		    if(orgId != null && !orgId.equals("")){
		    	context.getRequest().setAttribute("orgId",orgId);
		    }
		    
		    context.getRequest().setAttribute("OrgDetails", org);
	    }catch(SQLException s){
	    	s.printStackTrace();
	    }finally {
  		  	this.freeConnection(context, db);
	  	  }
	    
	    
	    
	    
	    return ("PrenotaOK");
	}
	
	
	public String executeCommandPrintSelectedEtichette(ActionContext context) {
		
		Connection db =  null;
		try {
			
		
			db = this.getConnection(context);
		LookupList quesiti_diagn = new LookupList(db, "quesiti_diagnostici_sigla");
        quesiti_diagn.addItem(-1, "-- SELEZIONA VOCE --");
        context.getRequest().setAttribute("QuesitiList", quesiti_diagn);
		} catch(SQLException s){
			s.printStackTrace();
		}
		finally
		{
			this.freeConnection(context, db);
		}
	    return ("PrintEtichetteOK");
	}
	
	public String executeCommandGenerateBarcode(ActionContext context) {
		   
	    String orgId = context.getRequest().getParameter("orgId");
	    String ticketId = context.getRequest().getParameter("ticketId");
	    context.getRequest().setAttribute("orgId",orgId);
	    context.getRequest().setAttribute("ticketId",ticketId);
   
	    return ("BarcodeOK");
   }
	
	public String executeCommandPrintBarcode(ActionContext context) {
		
		String numVerbale = (String)context.getRequest().getParameter("num_verbale");
		String reportDir = getWebInfPath(context, "template_report");  
		boolean automatico = false;
		String barcodevp = null;
		
		ManagePdfBase pdfBase =  new ManagePdfBase();
		java.sql.Connection db = null;
		try {
			db = this.getConnection(context);
			/*Genera barcode in base al testo inserito nella textarea*/
			if(numVerbale!=null && !numVerbale.equals("") ){
				barcodevp = numVerbale.trim(); 
			}
			/*Genera barcode con procedura automatica*/
			else {
				try {
					 barcodevp = pdfBase.generaCodice(db, 0,0);
					 automatico = true;
				} catch (Exception e) {
					
					pdfBase.rollback_sequence_etichette(db);
					e.printStackTrace();
					
				}
				
			}
			
			BufferedImage img = pdfBase.createBarcodeImage(barcodevp);
			
		    File outputfile = new File(reportDir+"/"+barcodevp+".png");
			try {
				ImageIO.write(img, "png", outputfile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			    
			FileInputStream fis = new FileInputStream(outputfile);
			//pdfBase.insertImageBarcode(db, fis, outputfile,Integer.parseInt(orgId), Integer.parseInt(ticketId), barcodevp, automatico );    
			
			/*Mostrare a video la bitmap generata*/
			BufferedInputStream is = new BufferedInputStream(new FileInputStream(outputfile));
			OutputStream out = context.getResponse().getOutputStream(); 
			context.getResponse().setContentType("application/octet-stream");
			context.getResponse().setHeader("Content-Disposition","attachment;filename="+barcodevp+".png");
			byte[] buf = new byte[4 * 1024]; // 4K buffer
			int bytesRead;
			while ((bytesRead = is.read(buf)) != -1) {
				   	out.write(buf, 0, bytesRead);
			}
			
			is.close();
			out.close(); 
			fis.close();
			//context.getRequest().setAttribute("orgId",orgId);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
  		  	this.freeConnection(context, db);
	  	}
				    
		
		
		
		return ("-none-");
		   
		   
	}
	
	 public String executeCommandPrintEtichette(ActionContext context){
		
		 String selectedValue = (String) context.getRequest().getParameter("selectMod");
		 String barcodeAnalita = null;
		 context.getRequest().setAttribute("value",selectedValue);
		 String idMatrice = context.getParameter("idMatrice");
		 String reportDir = getWebInfPath(context, "template_report");
		 String numVerbale = (String)context.getRequest().getParameter("num_verbale");
		 
		 ArrayList<String> analiti = new ArrayList<String>(); 
		 ManagePdfBase pdfBase =  new ManagePdfBase();  
		 java.sql.Connection db = null;
		 try{
			 db = this.getConnection(context);
			 
			 if(Integer.parseInt(selectedValue) == 5)
			 {
				 if(context.getRequest().getParameter("size")!=null)
				 {
						int size = Integer.parseInt(context.getRequest().getParameter("size"));
						for (int i = 1 ; i<=size; i++)
						{
							if(context.getRequest().getParameter("analitiId_"+i)!= null && !context.getRequest().getParameter("analitiId_"+i).equals(""))
							{
							int idFoglia = Integer.parseInt(context.getRequest().getParameter("analitiId_"+i));
							barcodeAnalita = pdfBase.retrieveCode(db, idFoglia, "analita");
							analiti.add(barcodeAnalita);	
							}
							
						}
					
				  }
			 }
			 
			 if(analiti.size() > 0){
				 context.getResponse().setContentType( "application/zip" );
				 context.getResponse().setHeader( "Content-Disposition","attachment; filename=\"" + "Analiti.zip" + "\";" ); 
				 ZipOutputStream out = new ZipOutputStream(context.getResponse().getOutputStream()); 
				 
				 for(int j=0; j < analiti.size(); j++){
					 
				 
					 byte[] buf = new byte[1024]; 
					 // Create the ZIP file  
				
					 // Compress the files 
					 BufferedImage img = pdfBase.createBarcodeImage(analiti.get(j));
				 
					 File outputfile = new File(reportDir+"/"+analiti.get(j)+".png");
					 ImageIO.write(img, "png", outputfile); 
					 FileInputStream fis = new FileInputStream(outputfile);
			     
					 BufferedInputStream in = new BufferedInputStream(fis);
					 out.putNextEntry(new ZipEntry(outputfile.getName())); //"image.jpg"
					 // Transfer bytes from the file to the ZIP file 
					 int len; 
					 while ((len = in.read(buf)) > 0) { 
						 out.write(buf, 0, len); 
					 } 
				    // Complete the entry 	 
					 in.close(); 
				
				 } 
				 out.close();
			 }
			
			 if(Integer.parseInt(selectedValue) == 4)
			 {
			 
				   String barcodeMatrice = "";
					if(context.getRequest().getParameter("size1")!=null)
					{
						int size = Integer.parseInt(context.getRequest().getParameter("size1"));
						for (int i = 1 ; i<=size; i++)
						{
							if(context.getRequest().getParameter("idMatrice_"+i)!= null && !context.getRequest().getParameter("idMatrice_"+i).equals(""))
							{
								
								barcodeMatrice = pdfBase.retrieveCode(db,Integer.parseInt(context.getRequest().getParameter("idMatrice_"+i)),"matrice");
							}

						}
						

						BufferedImage img = pdfBase.createBarcodeImage(barcodeMatrice);
					    File outputfile = new File(reportDir+"/"+barcodeMatrice+".png");
						ImageIO.write(img, "png", outputfile);
					    FileInputStream fis = new FileInputStream(outputfile);
						BufferedInputStream is = new BufferedInputStream(fis);
						OutputStream out = context.getResponse().getOutputStream(); 
						context.getResponse().setContentType("application/octet-stream");
						context.getResponse().setHeader("Content-Disposition","attachment;filename="+barcodeMatrice+".png");
						byte[] buf = new byte[4 * 1024]; // 4K buffer
						int bytesRead;
						while ((bytesRead = is.read(buf)) != -1) {
							   	out.write(buf, 0, bytesRead);
						}
						
						is.close();
						out.close(); 
						fis.close();  
						
					}
					   
			 }
			  
			  if(Integer.parseInt(selectedValue) == 1)
			  {
					String barcodevp = null;
				    /*Genera barcode in base al testo inserito nella textarea*/
					if(numVerbale!=null && !numVerbale.equals("") ){
							barcodevp = numVerbale.trim(); 
					}
					BufferedImage img = pdfBase.createBarcodeImage(barcodevp);
					File outputfile = new File(reportDir+"/"+barcodevp+".png");
					ImageIO.write(img, "png", outputfile);
					FileInputStream fis = new FileInputStream(outputfile);
					BufferedInputStream is = new BufferedInputStream(new FileInputStream(outputfile));
					OutputStream out = context.getResponse().getOutputStream(); 
					context.getResponse().setContentType("application/octet-stream");
					context.getResponse().setHeader("Content-Disposition","attachment;filename="+barcodevp+".png");
					byte[] buf = new byte[4 * 1024]; // 4K buffer
					int bytesRead;
					while ((bytesRead = is.read(buf)) != -1) {
						out.write(buf, 0, bytesRead);
					}
					is.close();
					out.close(); 
					fis.close();
				  
			  }
		 
		 }catch(SQLException s){
			 s.printStackTrace();
		 }catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		 }finally {
	  		  	this.freeConnection(context, db);
	  	  }	
		 
		 return ("-none-");
		  
	 }
	
	
   
	 
   public String executeCommandPrintModules(ActionContext context){
	
	  String orgId = context.getRequest().getParameter("orgId");
	  String ticketId = context.getRequest().getParameter("ticketId");
	  String tipoAction = context.getRequest().getParameter("tipoAction");
	  
	  if(tipoAction!=null && tipoAction.equals("prenota") ){
		  //Esegui Prenotazione Campione	
		  return this.executeCommandPrenotaCampione(context); 
	  }
	  
	  else {
		    
		  if( (orgId != null && !orgId.equals("null")) && (ticketId !=null && !ticketId.equals("null"))){
			  return this.executeCommandPrintModulesCompleteBarcode(context); 
		  }
		  else if(orgId != null && !orgId.equals("null")){
			  return this.executeCommandPrintModulesCompleteBarcode(context); 
		  }
		  else {
			  return this.executeCommandPrintBarcodeNumber(context);
		  } 
	  }
	  
   }
   
   public String executeCommandPrenotaCampione(ActionContext context){
		
	   String orgId = context.getRequest().getParameter("orgId");
	   org.aspcfs.modules.campioni.actions.Campioni c= new Campioni();
	   Connection db = null ;
	   try {   
		   db = this.getConnection(context);
		c.executeCommandInsert(context,db);
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   finally
	   {
		   this.freeConnection(context, db);
	   }
	   context.getRequest().setAttribute("orgId",orgId);
	   return "PrenotazioneOK";
		  
   }
   
   
	
   
	  
   public String executeCommandPrintModulesCompleteBarcode(ActionContext context){
	   
	  String orgId = context.getRequest().getParameter("orgId");
      String ticketId = context.getRequest().getParameter("ticketId"); 
	  String selectedValue = (String) context.getRequest().getParameter("tipo");
	  java.sql.Connection db = null;
	  ByteArrayOutputStream out = new ByteArrayOutputStream();
	  ManagePdfBase pdfBase =  new ManagePdfBase();
	  try 
	  {
		  Filtro f = new Filtro();
		  String reportDir = getWebInfPath(context, "template_report");
		  String reportDirImages = getWebInfPath(context, "reports");
		  PdfReader reader = null;
		  String displayName = null;
		  f.setOrgId(Integer.parseInt(orgId));  
		  db = this.getConnection(context);
		  
		  ResultSet rs = null;
		  
		  switch (Integer.parseInt(selectedValue)) {
			case 1: reader = new PdfReader( reportDir + "template_mod_1.pdf");
					displayName ="Mod-1-Verbale-campione-molluschi-in-produzione-primaria.pdf";
			break;
		  	case 2: reader = new PdfReader( reportDir + "template_mod_2.pdf");
		  			displayName = "Mod-2-Verbale-campione-battereologico.pdf";
		  	break;
		  	case 3: reader = new PdfReader( reportDir + "template_mod_3.pdf");
		  			displayName= "Mod-3-Verbale-campione-chimico.pdf";
			break;
		  	case 6: reader = new PdfReader( reportDir + "template_mod_6.pdf");
		  			displayName = "Mod-6-Verbale-prelievo-campioni-superficie-ambientale.pdf";
			break;
		  	case 10:reader = new PdfReader( reportDir + "template_mod_10.pdf");
		  			displayName = "Mod-10-Verbale-prelievo-campioni-superficie-carcasse.pdf";
			break;
		  	default:System.out.println("Nessuna delle scelte e' valida");
			break;
		  }	
		  
		  PdfStamper stamper = new PdfStamper(reader, out ); 
		  AcroFields form = stamper.getAcroFields(); 
		  
		  //Verificare la tipologia Operatore prima di procedere con la query
		  int tipologia = pdfBase.getTipologia(Integer.parseInt(orgId),db);
		  int asl = pdfBase.getAslFromOSA(Integer.parseInt(orgId), db);
		  switch (tipologia) {
			case 1: rs = f.queryRecord_impresa(db); 
					pdfBase.setFieldFromQuery(rs, form, f, 1); 
					if(f.getNum_reg()!= null && !f.getNum_reg().equals(""))
						pdfBase.setBarcodeImpresa(form,stamper, f.getNum_reg());
					//pdfBase.setBarcodeOrgid(form,stamper, f.getOrgId()+"");
			break;
		  	case 3: rs = f.queryRecord_stabilimenti(db); 
		  			pdfBase.setFieldFromQuery(rs, form, f,3); 
		  			if(f.getRicCE()!= null && !f.getRicCE().equals("")){
		  				pdfBase.setBarcodeStabilimento_Soa_Osm(form,stamper, f.getRicCE()+" "+f.getCodiceSezione()+" "+f.getCodiceImpianto());
	  				}
	  				else {
	  					form.setField("bc_osa", "NON DISPONIBILE");
	  				}
		  			//pdfBase.setBarcodeOrgid(form,stamper, f.getOrgId()+"");
		  	break;
		  	case 97: rs = f.queryRecord_soa(db); 
	  				pdfBase.setFieldFromQuery(rs, form, f, 97); 
	  				if(f.getRicCE()!= null && !f.getRicCE().equals("")){
	  					pdfBase.setBarcodeStabilimento_Soa_Osm(form,stamper, f.getRicCE());
	  				}
	  				else {
	  					form.setField("bc_osa", "NON DISPONIBILE");
	  				}
	  				
	  				// pdfBase.setBarcodeOrgid(form,stamper, f.getOrgId()+"");
	  		break;
		  	case 800: rs = f.queryRecord_osm(db);
		  			  pdfBase.setFieldFromQuery(rs, form, f, 800);
		  			  if(f.getNum_reg() != null && !f.getNum_reg().equals("")){
	  					pdfBase.setBarcodeStabilimento_Soa_Osm(form,stamper, f.getNum_reg());
		  			  }
		  			  else {
	  					form.setField("bc_osa", "NON DISPONIBILE");
		  			  }
		  			  
		  			  form.setField("tipologia",f.getTipologiaAttivita());
		  			 //pdfBase.setBarcodeOrgid(form,stamper, f.getOrgId()+"");
		  	break;
		  	
		  	default:
		  			rs = f.queryRecord(db);
			  		pdfBase.setFieldFromQuery(rs, form, f, -1);
			  		if(f.getNum_reg() != null && !f.getNum_reg().equals(""))
				  		pdfBase.setBarcodeStabilimento_Soa_Osm(form,stamper, f.getNum_reg());		  	
		  			if(f.getRicCE() != null && !f.getRicCE().equals(""))
				  		pdfBase.setBarcodeStabilimento_Soa_Osm(form,stamper, f.getRicCE());		  	
			break;
		  }
		  
		  try{
			  String barCodePrelievo;
			  String descrizione = "";
			  ArrayList<String> barCodeMatrici;
			  ArrayList<String> barCodeAnaliti;
			  ArrayList<String> path_analiti = new ArrayList<String>();
			  ArrayList<String> path_matrice = new ArrayList<String>();
			  ArrayList<Integer> idFoglie = new ArrayList<Integer>(); 
			  if(ticketId != null && !ticketId.equals("null") && ticketId != "null"){
				  
				  f.setIdCampione(Integer.parseInt(ticketId));
				  rs = f.queryRecord_campioni(db);
				  pdfBase.setFieldFromCU(form, rs, f, db);
				  //pdfBase.setFieldCampione(db, form, f);
				  barCodePrelievo = pdfBase.generaCodice(db, Integer.parseInt(selectedValue), Integer.parseInt(orgId), Integer.parseInt(ticketId));
				  
	//IN DEMO:R.M Parte da decommentare per la generazione del barcode relativa alla motivazione controllo e delle matrici e analiti			  
				 if(Integer.parseInt(selectedValue) !=6  && Integer.parseInt(selectedValue) != 10){
					if(f.getMotivazione()!= null && !f.getMotivazione().equals("")){
						pdfBase.setBarcodeMotivazione(form,stamper,f.getMotivazione());
					}
					else {
						//Cosa Esce se non abbiamo il barcode del motivo??
						form.setField("bc_non_disponibile","NON DISPONIBILE");
					}
					 
				 }
				 //Verra' tolto questo controllo nel momento in cui viene concordato per il mod.6 e mod.10
				 if(Integer.parseInt(selectedValue) != 6 && Integer.parseInt(selectedValue)!= 10){
					 
					 //barCodeAnaliti = pdfBase.getCodiceAnalita(db, Integer.parseInt(orgId), Integer.parseInt(ticketId));
					 idFoglie = pdfBase.getIdFoglia(db, Integer.parseInt(ticketId),"analiti","analiti_id");
		
					 for (int i = 0 ; i < idFoglie.size(); i++)
					 {
						 path_analiti = pdfBase.getPathAndCodesFromId(db,idFoglie.get(i),"analiti","analiti_id");
						 if(path_analiti.size() > 0){
							 descrizione += path_analiti.get(0)+"  ---------  ";
						 }
						 
					 }
					 
					 
					 form.setField("analiti",descrizione);
					 
					 ArrayList<Integer> idMatrice = pdfBase.getIdFoglia(db,Integer.parseInt(ticketId),"matrici","id_matrice");
					 path_matrice = pdfBase.getPathAndCodesFromId(db, idMatrice.get(0),"matrici","matrice_id");
					 if(path_matrice.size() > 0){
						 form.setField("matrice",path_matrice.get(0));
						 //Split codice_esame separato da ";"
						 if(path_matrice.get(1) != null && !path_matrice.get(1).equals("")){
							   int k = 0;
							   StringTokenizer st = new StringTokenizer(path_matrice.get(1),";");
							   while(st.hasMoreTokens()){
								 ++k;  
								 pdfBase.setBarcodeMatrici(form,stamper, st.nextToken(),k);  
							   }   
						   }
						 
					 }
					 					 
				 } 	 
				 
			  }
			
			  else {
				  barCodePrelievo = pdfBase.generaCodice(db, Integer.parseInt(selectedValue), Integer.parseInt(orgId), 0);
				  //R.M. Parte da commentare/decommentare per generare barcode matrici e analiti.	  
				  this.executeCommandMatriciAnalisiBarcode(context, form, stamper);
			  }
			  pdfBase.setBarcodePrelievo(form, stamper, barCodePrelievo);
			  if(asl > -1)
				  pdfBase.setLogoFromAsl(reportDir, reportDirImages, form, stamper, asl,db);

		  }catch (Exception e) {
			// TODO: handle exception
			  pdfBase.rollback_sequence(db, Integer.parseInt(selectedValue), Integer.parseInt(orgId));
			  e.printStackTrace();
			  throw new Exception ("Eccezione sequence!E' stato necessario il rollback");
		  }

		  
		  context.getRequest().setAttribute("orgId",orgId);	 	
		  stamper.setFormFlattening( true );
		  stamper.close();
		  FileDownload fileDownload = new FileDownload();
		  fileDownload.setDisplayName(displayName);
		  fileDownload.sendFile(context, out.toByteArray(), "application/pdf");
		  
		  out.close();
			
	  }catch ( Exception errorMessage ) {
			context.getRequest().setAttribute("Error", errorMessage);
  			errorMessage.printStackTrace();
  	  }finally {
  		  	this.freeConnection(context, db);
  	  }
			
  	 return ("-none-");
	 // return "HTMLOK";
  }
   
   
   public String executeCommandPrintModuleBarcodeNumber(ActionContext context){
	
	  return "PopupOK";
   }
   
   public String executeCommandPrintBarcodeNumber(ActionContext context){
   
	   
	  String orgId = context.getRequest().getParameter("orgId"); 
	  if(orgId != null && !orgId.equals("") && !orgId.equals("null")){
		  this.executeCommandPrintModulesCompleteBarcode(context);
	  }
		
	  String selectedValue = context.getRequest().getParameter("tipo");
	  java.sql.Connection db = null;
	  ByteArrayOutputStream out = new ByteArrayOutputStream();
	  ManagePdfBase pdfBase =  new ManagePdfBase();
	  try 
	  {
		  String reportDir = getWebInfPath(context, "template_report");
		  PdfReader reader = null;
		  String displayName = null;  
		  db = this.getConnection(context);
		  
		  switch (Integer.parseInt(selectedValue)) {
			case 1: reader = new PdfReader( reportDir + "template_mod_1.pdf");
					displayName ="Mod-1-Verbale-campione-molluschi-in-produzione-primaria.pdf";
			break;
		  	case 2: reader = new PdfReader( reportDir + "template_mod_2.pdf");
		  			displayName = "Mod-2-Verbale-campione-battereologico.pdf";
		  	break;
		  	case 3: reader = new PdfReader( reportDir + "template_mod_3.pdf");
		  			displayName= "Mod-3-Verbale-campione-chimico.pdf";
			break;
		  	case 6: reader = new PdfReader( reportDir + "template_mod_6.pdf");
		  			displayName = "Mod-6-Verbale-prelievo-campioni-superficie-ambientale.pdf";
			break;
		  	case 10:reader = new PdfReader( reportDir + "template_mod_10.pdf");
		  			displayName = "Mod-10-Verbale-prelievo-campioni-superficie-carcasse.pdf";
			break;
		  	default:System.out.println("Nessuna delle scelte e' valida");
			break;
		  }	
		  
		  PdfStamper stamper = new PdfStamper(reader, out ); 
		  AcroFields form = stamper.getAcroFields(); 
		  
		  try{
			  String barCodePrelievo;
			   barCodePrelievo = pdfBase.generaCodice(db, Integer.parseInt(selectedValue));
			   pdfBase.setBarcodePrelievo(form, stamper, barCodePrelievo);
			   
		  }catch (Exception e) {
			// TODO: handle exception
			  pdfBase.rollback_sequence(db, Integer.parseInt(selectedValue));
			  e.printStackTrace();
			  throw new Exception ("Eccezione sequence!E' stato necessario il rollback");
		  }
		  
		  stamper.setFormFlattening( true );
		  stamper.close();
		  FileDownload fileDownload = new FileDownload();
		  fileDownload.setDisplayName(displayName);
		  fileDownload.sendFile(context, out.toByteArray(), "application/pdf");
		  
		  out.close();
			
	  }catch ( Exception errorMessage ) {
			context.getRequest().setAttribute("Error", errorMessage);
  			errorMessage.printStackTrace();
  	  }finally {
  		  	this.freeConnection(context, db);
  	  }
			
      return ("-none-");
  	  //return "HTMLOK";
	  
  }
   
   
   
   public String executeCommandRetrieveBarcode(ActionContext context) throws IOException {
	   
	   String orgId = context.getRequest().getParameter("orgId");
	   String ticketId = context.getRequest().getParameter("ticketId");
	   String imageName;
	   OutputStream out = context.getResponse().getOutputStream(); 
	   ManagePdfBase pdfbase =  new ManagePdfBase();
	   java.sql.Connection db = null;
	   try {
		    db = this.getConnection(context);
			
			imageName = pdfbase.retrieveImageBarcode(db, out, Integer.parseInt(orgId), Integer.parseInt(ticketId));
			context.getResponse().setContentType("application/octet-stream");
			context.getResponse().setHeader("Content-Disposition","attachment;filename="+imageName+".png");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 
		}finally
		{
			out.close();
			this.freeConnection(context, db);
			
		}
		
	   return ("-none-");
	   
   }
   
   /*Metodo richiamato per la generazione di barcode matrice-analita */
   public void executeCommandMatriciAnalisiBarcode(ActionContext context,AcroFields form, PdfStamper stamper){
	   
	   String idMatrice = context.getParameter("idMatrice");
	   String barcodeAnalita = null;
	   ArrayList<String> analiti = new ArrayList<String>(); 
	   ArrayList<String> descAnaliti = new ArrayList<String>(); 

	   ManagePdfBase pdfBase =  new ManagePdfBase();  
	   java.sql.Connection db = null;
	   try{
			 db = this.getConnection(context);
			 if(context.getRequest().getParameter("size")!=null)
			 {
				int size = Integer.parseInt(context.getRequest().getParameter("size"));
				for (int i = 1 ; i<=size; i++)
				{
					if(context.getRequest().getParameter("analitiId_"+i)!= null && !context.getRequest().getParameter("analitiId_"+i).equals(""))
					{
						int idFoglia = Integer.parseInt(context.getRequest().getParameter("analitiId_"+i));
						barcodeAnalita = pdfBase.retrieveCode(db, idFoglia, "analita");
						analiti.add(barcodeAnalita);
						String descrizione  = pdfBase.getDescFromCode(db,idFoglia,"analiti","analiti_id");
						descAnaliti.add(descrizione);
					}
							
				}
			}
			 
			for(int j = 1; j <= analiti.size(); j++) {		
				pdfBase.setBarcodeAnaliti(form, stamper, analiti.get(j),j); //Modificare in get(j),j per il caso multiplo
				form.setField("analita_"+j, descAnaliti.get(j)); 
			}
		 
			if(idMatrice != null && !idMatrice.equals("-1")) {
				  String barcodeMatrice = pdfBase.retrieveCode(db,Integer.parseInt(idMatrice),"matrice");
				  pdfBase.setBarcodeMatrici(form,stamper, barcodeMatrice);
				  String descMatrice = pdfBase.getDescFromCode(db, Integer.parseInt(idMatrice), "matrici","matrice_id");
				  form.setField("matrice", descMatrice);
			}
			 
			 
	   }catch(SQLException s){
		   s.printStackTrace();
	   } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (DocumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		  	this.freeConnection(context, db);
	  }
	   
   }
   
   
   
}
