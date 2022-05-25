/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.elenco_cani_canile.actions;


import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.opu.base.ComuniAnagrafica;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.anagrafe_animali.base.AnimaleList;
import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.web.LookupList;


import com.darkhorseventures.framework.actions.ActionContext;


public class ElencoCaniInCanile  extends  CFSModule{
	
	public String executeCommandDefault(ActionContext context) {
 		
		return executeCommandDashboard(context);
 	}
 	
	
	public String executeCommandDashboard(ActionContext context) {
		
		if (!hasPermission(context, "lista-cani-canili-view")) {
			return ("PermissionError");
		}

		Connection db = null;

		try {
			db = this.getConnection(context);
			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
			
			int idCanile = -1;
			idCanile = thisUser.getUserRecord().getIdLineaProduttivaRiferimento(); //Canile dell'utente referente canile
			
			if (idCanile > 0){
				
			AnimaleList listAnimali = new AnimaleList();
			listAnimali.setId_detentore(idCanile);
			
			listAnimali.setFlagDecesso(false); //CERCA SOLO ANIMALI VIVI
			listAnimali.setFlagSmarrimento(false); //ESCLUDI ANIMALI SMARRITI
			listAnimali.setBuildProprietario(false); //NON COSTRUISCO PROPRIETARIO
			listAnimali.buildList(db);
			
			context.getRequest().setAttribute("listaAnimali", listAnimali);
			
			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db, 0);
				
			/*	c.buildList(db,
					((UserBean) context.getSession().getAttribute("User"))
							.getSiteId(), 1);*/
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("comuniList", comuniList);
			
			
			
			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
			
		}

		return ("listCaniOK");
		
		  }
	
public String executeCommandPrintDocumentoListaCani(ActionContext context) {
		
		if (!hasPermission(context, "lista-cani-canili-view")) {
			return ("PermissionError");
		}

	/*	String HOST_CORRENTE = context.getRequest().getLocalAddr();
		String SERVER_BDU  	= HOST_CORRENTE;
		ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
		String APPLICATION_PORT = prefs.get("APPLICATION.PORT");
		String APPLICATION_NAME = prefs.get("APPLICATION.NAME");

		String url = "http://".concat(SERVER_BDU).concat(":").concat(APPLICATION_PORT).concat("/").concat(APPLICATION_NAME).concat("/");

		context.getRequest().setAttribute("SERVER_BDU", url); */
		Connection db = null;

		try {
			db = this.getConnection(context);
			UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
			
			int idCanile = -1;
			idCanile = thisUser.getUserRecord().getIdLineaProduttivaRiferimento(); //Canile dell'utente referente canile
			
			if (idCanile > -1){
				
			AnimaleList listAnimali = new AnimaleList();
			listAnimali.setId_detentore(idCanile);
			listAnimali.setFlagDecesso(false); //CERCA SOLO ANIMALI VIVI
			listAnimali.setFlagSmarrimento(false); //ESCLUDI ANIMALI SMARRITI	
			
			
			listAnimali.setBuildProprietario(false); //NON COSTRUISCO PROPRIETARIO
						
			listAnimali.buildList(db);
			
			context.getRequest().setAttribute("listaAnimali", listAnimali);
			
			ComuniAnagrafica c = new ComuniAnagrafica();
			ArrayList<ComuniAnagrafica> listaComuni = c.buildList_all(db, 0);
				
			/*	c.buildList(db,
					((UserBean) context.getSession().getAttribute("User"))
							.getSiteId(), 1);*/
			LookupList comuniList = new LookupList(listaComuni, -1);
			comuniList.addItem(-1, "");
			context.getRequest().setAttribute("comuniList", comuniList);
			
			
			
			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
			
		}

		return ("documentoListaCaniOk");
		
		  }
	
	public String executeCommandstampaCSV(ActionContext context) throws IOException {
		
		  Connection	db		= null;
		  try {
				db = this.getConnection(context);
				UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
				
				int idCanile = -1;
				idCanile = thisUser.getUserRecord().getIdLineaProduttivaRiferimento(); //Canile dell'utente referente canile
				
				
				if (idCanile > -1){
					
				AnimaleList listAnimali = new AnimaleList();
				listAnimali.setId_detentore(idCanile);		
				listAnimali.setFlagDecesso(false); //CERCA SOLO ANIMALI VIVI
				listAnimali.setFlagSmarrimento(false); //ESCLUDI ANIMALI SMARRITI			
				listAnimali.setBuildProprietario(false); //NON COSTRUISCO PROPRIETARIO
				listAnimali.buildList(db);
				
				ComuniAnagrafica comuni = new ComuniAnagrafica();
				ArrayList<ComuniAnagrafica> listaComuni = comuni.buildList_all(db, 0);
					
				/*	c.buildList(db,
						((UserBean) context.getSession().getAttribute("User"))
								.getSiteId(), 1);*/
				LookupList comuniList = new LookupList(listaComuni, -1);
				comuniList.addItem(-1, "");
				context.getRequest().setAttribute("comuniList", comuniList);

		 String header=""; 
	     header+= "MICROCHIP"+";";
		 header+= "TATUAGGIO"+";";
		 header+= "PROPRIETARIO"+";";
		 header+= "DETENTORE"+";";
		 header+= "COMUNE PROPRIETARIO"+";";
		 header+= "ASL CANE"+";";
		
		 StringBuffer sb = new StringBuffer(); 
		 sb.append(header+"\n");
		 
		 Animale c;
			for (int i = 0;i < listAnimali.size(); i++) {
			c = (Animale) listAnimali.get(i);
			Operatore prop = new Operatore();
			String propr_nome = "";
			String comune_proprietario = "";
			Operatore det = new Operatore();
			String det_nome = "";
			if (c.getNomeCognomeProprietario() != null && !("").equals(c.getNomeCognomeProprietario()) ){
				//prop = c.getProprietario();
				propr_nome = c.getNomeCognomeProprietario();
				//Stabilimento stab = (Stabilimento) prop.getListaStabilimenti().get(0);
				if (c.getIdComuneProprietario() > 0)
					comune_proprietario = comuniList.getSelectedValue(c.getIdComuneProprietario());
			}
			
			if (c.getNomeCognomeDetentore() != null && !("").equals(c.getNomeCognomeDetentore()) ){
				//det = c.getDetentore();
				det_nome =c.getNomeCognomeDetentore();
			}
			
			LookupList siteList = new LookupList(db, "lookup_asl_rif");
			siteList.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AslList", siteList);
			
			
	 		sb.append("\'"+c.getMicrochip()+"\';" );
	 		sb.append("\'"+((c.getTatuaggio() != null)? c.getTatuaggio() : "")+"\';" );
	 		sb.append(""+propr_nome +";" );
 		    sb.append(""+det_nome +";" );
	 		sb.append(""+comune_proprietario+";" );
	 		sb.append(""+siteList.getSelectedValue(c.getIdAslRiferimento())+";" );
	 		sb.append("\n");
	 		
	 		
			}

		 
		 
		  HttpServletResponse res = context.getResponse();
		  res.setContentType( "application/csv" );
		  res.setHeader( "Content-Disposition","attachment; filename=" +"canile" + ".csv" );
		//  res.setHeader( "Content-Disposition","attachment; filename=" +c.getNome_canile().replace(" ","_") + ".csv" ); 
		  ServletOutputStream sout = res.getOutputStream();
		  sout.write(sb.toString().getBytes());
		 	
		 	sout.flush();
		 
		 
		 
			
	}}
		  catch (Exception e) {
			// TODO: handle exception
		}
		  finally{
			  this.freeConnection(context, db);
		  }
		  return ("-none-");	
		  }
	
		  
}
	
	
	/*public String executeCommandstampa(ActionContext context) {
	//	  if (!hasPermission(context, "report-approvazione-contributi-view")) {
		//      return ("PermissionError");
		    
		 //   }
		  Connection	db		= null;
			
		  try
			{
			    db = getConnection(context);
			  	logger.info( "[CANINA] - ----------------- Cani in Canile -----------------" );
			    
			  	String					dir		= context.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "nuovi_report_pdf_template" + File.separator;
				int id_organization=  Integer.parseInt(context.getRequest().getParameter("id"));
				
				List<Cane> listaCani2=(List<Cane>) context.getRequest().getSession().getAttribute("listaCani");
				
			  	PdfReader 				reader  = new PdfReader(dir + "elencoCaniInCanile.pdf");
			    ByteArrayOutputStream 	out		= new ByteArrayOutputStream();
			    ByteArrayOutputStream 	out2	= new ByteArrayOutputStream();
			    PdfStamper				stamper	= new PdfStamper( reader, out );
			    AcroFields				form	= stamper.getAcroFields();
			    ArrayList	elenco		= new ArrayList();
				Stats		header		= new Stats();
				
				UserBean thisUser = (UserBean) context.getSession().getAttribute("User");

				float[]		sizes	= null;

				sizes =elenco_cani( context, elenco, header,db,listaCani2);
				
				String reportName="Elenco Cani presenti nel Canile "+thisUser.getContactName();
			
				form.setField( "data", oggi() );
			    form.setField( "nomeCanile", thisUser.getContactName() );
			    
			    if  ((elenco.size()/6)< 0){
				    form.setField( "cani","Nel canile non sono presenti cani registrati" );
			    }
			    else
			    	form.setField( "cani","Totale cani presenti " );
			    form.setField( "totale",Integer.toString(elenco.size()/6));
			    
			    stamper.setFormFlattening( true );
			    stamper.close();
			    setElenco(context, stamper, elenco, reportName, header, sizes, out, out2 );
			
			    write( context, out2, reportName );
			    
			}
			catch ( Exception e )
			{
				e.printStackTrace();
			}
			finally
			{
				this.freeConnection(context, db);
				System.gc();
			}
			 return "-none-";
	}


	public String executeCommandstampaCSV(ActionContext context) throws IOException {
		
			  Connection	db		= null;
			  List<Cane> listaCani2=(List<Cane>) context.getRequest().getSession().getAttribute("listaCani");
			 String id_organization=  (context.getRequest().getParameter("id"));

			 String header=""; 
		     header+= "MICROCHIP"+";";
			 header+= "TATUAGGIO"+";";
			 header+= "PROPRIETARIO"+";";
			 header+= "DETENTORE"+";";
			 header+= "COMUNE PROPRIETARIO"+";";
			 header+= "ASL CANE"+";";
			
			 StringBuffer sb = new StringBuffer(); 
			 sb.append(header+"\n");
			 for (Cane c: listaCani2){
			 		sb.append("\'"+c.getSerial_number()+"\';" );
			 		sb.append("\'"+c.getPo_number()+"\';" );
			 		sb.append(""+c.getNome_proprietario() +";" );
		 		    sb.append(""+c.getNome_canile() +";" );
			 		sb.append(""+c.getComune_proprietario()+";" );
			 		sb.append(""+c.getAsl_description()+";" );
			 		sb.append("\n");
			 }
			 
			 
			  HttpServletResponse res = context.getResponse();
			  res.setContentType( "application/csv" );
			  res.setHeader( "Content-Disposition","attachment; filename=" +"canile" + ".csv" );
			//  res.setHeader( "Content-Disposition","attachment; filename=" +c.getNome_canile().replace(" ","_") + ".csv" ); 
			  ServletOutputStream sout = res.getOutputStream();
			  sout.write(sb.toString().getBytes());
			 	
			 	sout.flush();
			 
			 
			 return ("-none-");	
				
		}

	  

	private static void write(ActionContext context, ByteArrayOutputStream out, String reportName) throws IOException, EmailException
	{
		HttpServletResponse res = context.getResponse();
	 	res.setContentType( "application/pdf" );
		
	 	res.setHeader( "Content-Disposition","attachment; filename=\""  + reportName + ".pdf\";" ); 
	 	ServletOutputStream sout = res.getOutputStream();
	 	sout.write( out.toByteArray() );
	 	
	 	sout.flush();
		 		 	
	}	
	
	private static int calcolaNumeroPagine(int size)
	{
				return (int)Math.ceil( (size * 1.0) / (page_elem ));
	}
	
	private static final int page_elem = 180;

	private static void setElenco(ActionContext context,PdfStamper stamper, ArrayList elenco, String reportName, Stats header, 
			float[] sizes, ByteArrayOutputStream out, ByteArrayOutputStream out2 )
	{
		try
		{
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, out2);
			PdfReader reader = new PdfReader(out.toByteArray());
			document.open();
			PdfContentByte cb = writer.getDirectContent();

			PdfImportedPage page = writer.getImportedPage( reader, 1 );

			cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
		
			PdfPTable	table	= null;
			
			Color		blue	= new Color( 114, 159, 207 );
			Font		f		= new Font(Font.HELVETICA, 8, Font.NORMAL, Color.black);
			Font		fh		= new Font(Font.HELVETICA, 7, Font.NORMAL, Color.white);
			BaseFont	fp		= BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );// 8, Font.ITALIC, blue);
			
			
			int numero_pagine	= calcolaNumeroPagine( elenco.size() );
			int curr_pag		= 0;
			for( int i = 0; i < elenco.size(); i++ )
			{
				if( (i % (page_elem)) == 0 )
				{
					document.newPage();
					++curr_pag;
					
					cb.beginText();
					cb.setFontAndSize( fp, 10 );
					cb.setRGBColorFill(114, 159, 207);
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "DETTAGLIO REPORT  \"" + reportName + "\"", 10, PageSize.A4.height() * 0.955f, 0);
					cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Pagina " + curr_pag + " di " + numero_pagine, PageSize.A4.width() - 10, PageSize.A4.height() * 0.955f, 0);
					cb.endText();
					
					table = new PdfPTable( sizes );
					//era 80
					table.setTotalWidth( PageSize.A4.width() - 20 );
				    table.getDefaultCell().setBorderWidth(0);
				   // table.getDefaultCell().setPadding(2);
				    table.getDefaultCell().setPaddingBottom(5);
			        table.getDefaultCell().setHorizontalAlignment( Element.ALIGN_CENTER );
			        table.getDefaultCell().setBackgroundColor( blue );
			        for( int j = 0; j < header.getSize(); j++  )
			        {
			        	table.addCell( new Phrase( header.get( j ), fh ) );
			        }
				}
				
				 table.getDefaultCell().setBackgroundColor( Color.white );
				Object st = elenco.get( i );
				
		        table.addCell( new Phrase( st.toString(), f ) );
		       if( ((i + 1) % page_elem) == 0 )
				{
		        	table.writeSelectedRows( 0, -1, 10, PageSize.A4.height() * 0.94f, cb );
		        	table = null;
				}
			}
			
			if( table != null )
			{
				table.writeSelectedRows( 0, -1, 10, PageSize.A4.height() * 0.94f, cb );
			}
	        
			
			document.close();
				
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	public static float[] elenco_cani(ActionContext context,
			ArrayList elenco, Stats header,Connection db,List<Cane> listaCani) throws SQLException, DocumentException
	{float[] fa=null;
		  try
			{
		ArrayList<Float> ret = new ArrayList<Float>();
		int j=1;
		
		for ( Cane l : listaCani){
		elenco.add(l.getSerial_number());
		elenco.add(l.getPo_number());
		elenco.add(l.getNome_proprietario());
		elenco.add(l.getNome_canile());
		elenco.add(l.getComune_proprietario());
		elenco.add(l.getAsl_description());
		
	}
			
		header.add("Microchip");
		ret.add(1.5f);
		header.add("Tatuaggio");
		ret.add(0.7f);
		header.add("Proprietario");
		ret.add(1.0f);
		header.add("Detentore");
		ret.add(1.0f);
		header.add("Comune Proprietario");
		ret.add(1.0f);
		header.add("Asl ");
		ret.add(1.0f);
		
		fa = new float[ ret.size() ];
		
		for( int i = 0; i < ret.size(); i++ )
		{
			fa[i] = ret.get( i ).floatValue();
		}
		}
		  catch(Exception e){
			  logger.severe("[CANINA] - EXCEPTION nel metodo elenco_cani della classe Elenco_cani_canile");
			  e.printStackTrace();
		 }
		
		return fa;
		
		
	}
	
	private static String oggi()
	{
		return (new SimpleDateFormat("dd/MM/yyyy")).format( new Date() );
	}
*/	

	
