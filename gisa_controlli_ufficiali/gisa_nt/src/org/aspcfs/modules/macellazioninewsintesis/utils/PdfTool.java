/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioninewsintesis.utils;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.modules.base.Address;
import org.aspcfs.modules.gestioneDocumenti.actions.GestioneAllegatiMacelli;
import org.aspcfs.modules.macellazioninewsintesis.base.Art17;
import org.aspcfs.modules.stabilimenti.base.Organization;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfAnnotation;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfFormField;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.TextField;

public class PdfTool
{
	/**
	 * NB: Imlementare la visualizzazione dell'elenco
	 * @param context
	 * @param reportName
	 * @param filtri
	 * @param risultati
	 * @param elenco
	 */
	
	class FieldCell implements PdfPCellEvent{
		
		PdfFormField formField;
		PdfWriter writer;
		int width;
		
		public FieldCell(PdfFormField formField, int width, 
			PdfWriter writer){
			this.formField = formField;
			this.width = width;
			this.writer = writer;
		}
		
		public void cellLayout(PdfPCell cell, Rectangle rect, 
			PdfContentByte[] canvas){
			try{
				// delete cell border
				PdfContentByte cb = canvas[PdfPTable
					.LINECANVAS];
				cb.reset();
				
				formField.setWidget(
					new Rectangle(rect.getLeft(), 
						rect.getBottom(), 
						rect.getLeft()+width, 
						rect.getTop()), 
						PdfAnnotation
						.HIGHLIGHT_NONE);
				
				writer.addAnnotation(formField);
			}catch(Exception e){
				System.out.println(e);
			}
		}
	}
	
	public static String printRegistroMacellazioni( ActionContext context, int orgId, String reportName, String data, ArrayList<Stats> elenco, Stats header, float[] sizes )
	{
		try
		{
//			String					dir		= context.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "nuovi_report_pdf_template" + File.separator;
//		    PdfReader				reader	= new PdfReader( dir + "Modello Report.pdf" );
		    ByteArrayOutputStream 	out2	= new ByteArrayOutputStream();
//		    PdfStamper				stamper	= new PdfStamper( reader, out );
//		    AcroFields				sform	= stamper.getAcroFields();

//		    form.setField( "report", reportName );
//		    form.setField( "data", oggi() );

//		    stamper.setFormFlattening( true );
//		    stamper.close();

		    registroMacellazioni( elenco, reportName, data, header, sizes, out2 );

		   // write( context, out2, "REGISTRO MACELLAZIONI " + reportName, data );
		    //write( context, out, "ART17 mac. " + macello.getName() + " eserc. " + esercente.getName(), data );
		    GestioneAllegatiMacelli allegato = new GestioneAllegatiMacelli();
			allegato.setBa(out2.toByteArray());
			allegato.setOrg(orgId);
			allegato.setDataMacellazione(data);
			allegato.setTipoDocumento("Macelli_Registro");
			return allegato.chiamaServerDocumentale(context);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally {
			return "-none-";
		}
	}

	private static void registroMacellazioni( ArrayList<Stats> elenco, String macello, String data, Stats header, 
			float[] sizes, ByteArrayOutputStream out2 )
	{
		try
		{
			Document document = new Document( new Rectangle(PageSize.A4.getHeight(), PageSize.A4.getWidth()) );
			PdfWriter writer = PdfWriter.getInstance(document, out2);
//			PdfReader reader = new PdfReader(out.toByteArray());
			document.open();
			PdfContentByte cb = writer.getDirectContent();

//			PdfImportedPage page = writer.getImportedPage( reader, 1 );
//			cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);

			PdfPTable	table	= null;
			Color		gray	= new Color( 226, 226, 226 );
			Color		blue	= new Color( 114, 159, 207 );
			Font		f		= new Font(Font.HELVETICA, 8, Font.NORMAL, Color.black);
			Font		fh		= new Font(Font.HELVETICA, 9, Font.NORMAL, Color.white);
			BaseFont	fp		= BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );// 8, Font.ITALIC, blue);
			
			int numero_pagine	= calcolaNumeroPagine( elenco.size(), page_elem_reg_mac );
			int curr_pag		= 0;
			
			for( int i = 0; i < elenco.size(); i++ )
			{
				if( (i % page_elem_reg_mac) == 0 )
				{
					document.newPage();
					++curr_pag;
					
					//intestazione
					cb.beginText();
					cb.setFontAndSize( fp, 8 );
					cb.setRGBColorFill(114, 159, 207);
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "REGISTRO MACELLAZIONI  \"" + macello + "\" del " + data, 30, PageSize.A4.getWidth() * 0.955f, 0);
					cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Pagina " + curr_pag + " di " + numero_pagine, PageSize.A4.getHeight() - 30, PageSize.A4.getWidth() * 0.955f, 0);
					cb.endText();
					
					//firma del veterinario
					cb.beginText();
					cb.setFontAndSize( fp, 8 );
					cb.setRGBColorFill( 0, 0, 0 );
					cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Firma del Veterinario _________________________________________", PageSize.A4.getHeight() - 30, PageSize.A4.getWidth() * 0.1f, 0);
					cb.endText();
					
					table = new PdfPTable( sizes );
					table.setTotalWidth( PageSize.A4.getHeight() - 60 );
			        table.getDefaultCell().setBorderWidth(0);
			        table.getDefaultCell().setHorizontalAlignment( Element.ALIGN_CENTER );
			        table.getDefaultCell().setBackgroundColor( blue );
			        for( int j = 0; j < header.getSize(); j++  )
			        {
			        	table.addCell( new Phrase( header.get( j ), fh ) );
			        }
				}
				
				if( (i % 2) == 1 )
				{
			        table.getDefaultCell().setBackgroundColor( gray );
				}
				else
				{
			        table.getDefaultCell().setBackgroundColor( Color.white );
				}
				
				Stats st = elenco.get( i );
				
//		        table.addCell( new Phrase( st.getAsl(), f ) );
		        
		        for( int j = 0; j < st.getSize(); j++  )
		        {
			        table.addCell( new Phrase( st.getNext(), f ) );
		        }
		        
		        if( ((i + 1) % page_elem_reg_mac) == 0 )
				{
		        	table.writeSelectedRows( 0, -1, 30, PageSize.A4.getWidth() * 0.94f, cb );
		        	table = null;
				}
			}
			
			if( table != null )
			{
				table.writeSelectedRows( 0, -1, 30, PageSize.A4.getWidth() * 0.94f, cb );
			}
	        
	        
	        

			document.close();
				
				
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	private static int calcolaNumeroPagine(int size, int page_elem)
	{
		return (int)Math.ceil( (size * 1.0) / page_elem );
	}

	private static void write(ActionContext context, ByteArrayOutputStream out, String reportName, String data) throws IOException
	{
		HttpServletResponse res = context.getResponse();
	 	res.setContentType( "application/pdf" );
	 	res.setHeader( "Content-Disposition","inline; filename=\"" + timestamp( data ) + " " + reportName + ".pdf\";" ); 
	 	
	 	ServletOutputStream sout = res.getOutputStream();
	 	sout.write( out.toByteArray() );
	 	sout.flush();
	}

	private static String timestamp()
	{
		return (new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")).format( new Date() );
	}
	
	private static String timestamp( String data )
	{
		try
		{
			return (new SimpleDateFormat("yyyy-MM-dd")).format( (new SimpleDateFormat("dd/MM/yyyy")).parse( data ) );
		}
		catch (ParseException e)
		{
			return timestamp();
		}
	}

	private static String oggi()
	{
		return (new SimpleDateFormat("dd/MM/yyyy")).format( new Date() );
	}
	
	public static void main(String[] args)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream( "c:\\registro_macellazioninew.pdf" );

		    ByteArrayOutputStream 	out2	= new ByteArrayOutputStream();
			ArrayList<Stats>		elenco	= new ArrayList<Stats>();
			Stats					header	= new Stats();
			float[]					sizes	= { 0.25f, 0.25f, 0.25f, 0.25f };
			
			header.results = new ArrayList<String>();
			header.results.add( "data arrivo" );
			header.results.add( "specie" );
			header.results.add( "codice animale" );
			header.results.add( "sesso" );
			
			elenco.add( header );
		    
			registroMacellazioni( elenco, "Agerola Carni", "12/04/2010", header, sizes, out2 );

		    fos.write( out2.toByteArray() );
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void printModuloBSE(ActionContext context, Organization org, String asl,
			String data, ArrayList<Stats> elenco, Stats header, float[] sizes)
	{
		try
		{
		    ByteArrayOutputStream out = new ByteArrayOutputStream();

		    moduloBSE( elenco, org, asl, data, header, sizes, out );

		    write( context, out, "MODULO BSE " + org.getName(), data );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		
	}
	
	public static void printModuloAbbattimento(ActionContext context, Organization org, String asl, String speditore,
			String data, ArrayList<Stats> elenco, Stats header, float[] sizes)
	{
		try
		{
		    ByteArrayOutputStream out = new ByteArrayOutputStream();

		    moduloAbbattimento( elenco, org, asl, speditore, data, header, sizes, out );

		    write( context, out, "MODULO ABBATTIMENTO " + org.getName(), data );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		
	}

	private static void moduloBSE(ArrayList<Stats> elenco, Organization org, String asl,
			String data, Stats header, float[] sizes, ByteArrayOutputStream out)
	{
		
		try
		{
			Document document = new Document( new Rectangle(PageSize.A4.getHeight(), PageSize.A4.getWidth()) );
			PdfWriter writer = PdfWriter.getInstance(document, out);
			document.open();
			
			PdfContentByte cb = writer.getDirectContent();

			PdfPTable	table	= null;
			Color		gray	= new Color( 226, 226, 226 );
			Color		blue	= new Color( 114, 159, 207 );
			Font		f		= new Font(Font.HELVETICA, 8, Font.NORMAL, Color.black);
			Font		fh		= new Font(Font.HELVETICA, 9, Font.NORMAL, Color.white);
			BaseFont	fp		= BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );// 8, Font.ITALIC, blue);
			BaseFont	fp2		= BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
			
			int numero_pagine	= calcolaNumeroPagine( elenco.size(), page_elem_mod_bse );
			int curr_pag		= 0;
			
			for( int i = 0; i < elenco.size(); i++ )
			{
				if( (i % page_elem_mod_bse) == 0 )
				{
					document.newPage();
					++curr_pag;
					
					cb.beginText();

					//intestazione
					cb.setFontAndSize( fp, 11 );
					cb.setRGBColorFill(114, 159, 207);
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Sistema nazionale di Sorveglianza epidemiologica della BSE: SCHEDA DI ACCOMPAGNAMENTO CAMPIONI",
							120, PageSize.A4.getWidth() * 0.955f, 0);

					//numero pagina
					cb.setFontAndSize( fp, 8 );
					cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Pagina " + curr_pag + " di " + numero_pagine, PageSize.A4.getHeight() - 30, PageSize.A4.getWidth() * 0.955f, 0);
					
					//dati sul macello e simili + firma del veterinario
					cb.setFontAndSize( fp2, 9 );
					cb.setRGBColorFill( 0, 0, 0 );
					cb.showTextAligned(
							PdfContentByte.ALIGN_LEFT, 
							"Verbale n.______________              " +
							"ASL: " + asl + "                " +
							"Prelevatore___________________________                " +
							"Data Prelievo: " + data, 
							30, PageSize.A4.getWidth() * 0.27f , 0); // 0.90f
					
					cb.showTextAligned(
							PdfContentByte.ALIGN_LEFT, 
							"Luogo di prelievo: macello                   " +
							"Denominazione: " + org.getName(), 
							30, PageSize.A4.getWidth() * 0.235f , 0);	// 0.865f

					Address oa = org.getAddress("Operativo");
					oa = (oa == null) ? ((Address)org.getAddressList().get(0)) : (oa);
					cb.showTextAligned(
							PdfContentByte.ALIGN_LEFT, 
							"Num. Autorizzazione / Codice azienda: " + org.getNumAut() + "                                 " +
							"Comune: " + ( (oa != null) ? (oa.getCity()) : ("") ) + "                         " +
							"Provincia: " + ( (oa != null) ? (oa.getState()) : ("") ) , 
							30, PageSize.A4.getWidth() * 0.20f, 0); //0.83f

					cb.setFontAndSize( fp, 9 );
					
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, 
							"Firma del Titolare dell'Impianto _________________________________________", 
							30, PageSize.A4.getWidth() * 0.1f, 0);
					
					cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, 
							"Veterinario Timbro e Firma _________________________________________", 
							PageSize.A4.getHeight() - 30, PageSize.A4.getWidth() * 0.1f, 0);
					
					cb.endText();
					
					table = new PdfPTable( sizes );
					table.setTotalWidth( PageSize.A4.getHeight() - 60 );
			        table.getDefaultCell().setBorderWidth(0);
			        table.getDefaultCell().setHorizontalAlignment( Element.ALIGN_CENTER );
			        table.getDefaultCell().setBackgroundColor( blue );
			        for( int j = 0; j < header.getSize(); j++  )
			        {
			        	table.addCell( new Phrase( header.get( j ), fh ) );
			        }
				}
				
				if( (i % 2) == 1 )
				{
			        table.getDefaultCell().setBackgroundColor( gray );
				}
				else
				{
			        table.getDefaultCell().setBackgroundColor( Color.white );
				}
				
				Stats st = elenco.get( i );
				
//		        table.addCell( new Phrase( st.getAsl(), f ) );
		        
		        for( int j = 0; j < st.getSize(); j++  )
		        {
			        table.addCell( new Phrase( st.getNext(), f ) );
		        }
		        
		        if( ((i + 1) % page_elem_mod_bse) == 0 )
				{
		        	table.writeSelectedRows( 0, -1, 30, PageSize.A4.getWidth() * 0.80f, cb );
		        	table = null;
				}
			}
			
			if( table != null )
			{
				table.writeSelectedRows( 0, -1, 30, PageSize.A4.getWidth() * 0.94f, cb );
			}
	        
	        
	        

			document.close();
				
				
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		
	}
	
	private static void moduloAbbattimento(ArrayList<Stats> elenco, Organization org, String asl, String speditore,
			String data, Stats header, float[] sizes, ByteArrayOutputStream out)
	{
		
		try
		{
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, out);
			document.open();
			
			PdfContentByte cb = writer.getDirectContent();

			PdfPTable	table	= null;
			Color		gray	= new Color( 226, 226, 226 );
			Color		blue	= new Color( 114, 159, 207 );
			Font		f		= new Font(Font.HELVETICA, 8, Font.NORMAL, Color.black);
			Font		fh		= new Font(Font.HELVETICA, 9, Font.NORMAL, Color.white);
			BaseFont	fp		= BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );// 8, Font.ITALIC, blue);
			BaseFont	fp2		= BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
			
			int numero_pagine	= calcolaNumeroPagine( elenco.size(), page_elem_mod_abb );
			int curr_pag		= 0;
			
			for( int i = 0; i < elenco.size(); i++ )
			{
				if( (i % page_elem_mod_abb) == 0 )
				{
					document.newPage();
					++curr_pag;
					
					cb.beginText();

					//intestazione
					cb.setFontAndSize( fp2, 9 );
					cb.setRGBColorFill( 0, 0, 0 );
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "Regione Campania",
							PageSize.A4.getWidth() / 2, PageSize.A4.getHeight() * 0.955f, 0);
					

					cb.setFontAndSize( fp2, 11 );
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "Azienda Sanitaria Locale " + asl,
							PageSize.A4.getWidth() / 2, PageSize.A4.getHeight() * 0.935f, 0);

					
//					cb.setFontAndSize( fp, 11 );
//					cb.setRGBColorFill(114, 159, 207);
//					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Sistema nazionale di Sorveglianza epidemiologica della BSE: SCHEDA DI ACCOMPAGNAMENTO CAMPIONI",
//							120, PageSize.A4.height() * 0.955f, 0);

					//numero pagina
					cb.setFontAndSize( fp, 8 );
					cb.setRGBColorFill(114, 159, 207);
					cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Pagina " + curr_pag + " di " + numero_pagine, PageSize.A4.getWidth() - 30, PageSize.A4.getHeight() * 0.955f, 0);
					
					//dati sul macello e simili + firma del veterinario
					cb.setFontAndSize( fp2, 9 );
					cb.setRGBColorFill( 0, 0, 0 );
					cb.showTextAligned(
							PdfContentByte.ALIGN_RIGHT, 
							"Prot. ______________________           " +
							"li ______________________", 
							PageSize.A4.getWidth() - 30, PageSize.A4.getHeight() * 0.90f, 0);
					
					cb.setFontAndSize( fp2, 7 );
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "BONIFICA SANITARIA",
							PageSize.A4.getWidth() / 2, PageSize.A4.getHeight() * 0.87f, 0);
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "DEGLI ALLEVAMENTI DALLA TUBERCOLOSI E DALLA BRUCELLOSI",
							PageSize.A4.getWidth() / 2, PageSize.A4.getHeight() * 0.855f, 0);
					cb.setFontAndSize( fp2, 5 );
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "(Legge 9 - 6 - 1964, n.615 e legge 23 - 1 - 1968 n.33)",
							PageSize.A4.getWidth() / 2, PageSize.A4.getHeight() * 0.84f, 0);
					cb.setFontAndSize( fp, 11 );
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "ATTESTATO DI ABBATTIMENTO DI ANIMALI INFETTI",
							PageSize.A4.getWidth() / 2, PageSize.A4.getHeight() * 0.81f, 0);
					
					cb.setFontAndSize( fp2, 8 );
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, 
							"Il sottoscritto Dott. ________________________________ in qualita' VETERINARIO UFFICIALE visti gli atti d'Ufficio ed a richiesta",
							30, PageSize.A4.getHeight() * 0.78f, 0);
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, 
							" dell'interessato, attesta che in data " + data +
							" presso l'impianto di mattazione " + org.getName() +
							((elenco.size() > 1) ? 
							( " sono stati abbattuti i seguenti animali" ) : 
							( " e' stato abbattuto il seguente animale" )),
							30, PageSize.A4.getHeight() * 0.76f, 0);

					cb.setFontAndSize( fp2, 9 );
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, 
							"ALLEVATORE: " + speditore,
							30, PageSize.A4.getHeight() * 0.725f, 0);

					cb.setFontAndSize( fp2, 7 );
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, 
							"Si rilascia la presente attestazione ai fini della corresponsione della indennita' di", 
							30, PageSize.A4.getHeight() * 0.12f, 0);
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, 
							"abbattimento previsto dall'art. 2 della legge 23-1-1968, n.33.", 
							30, PageSize.A4.getHeight() * 0.105f, 0);
					
					cb.setFontAndSize( fp, 9 );
					cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, 
							"IL DIRETTORE DEL MACELLO __________________________", 
							PageSize.A4.getWidth() - 30, PageSize.A4.getHeight() * 0.08f, 0);
					
					cb.endText();
					
					table = new PdfPTable( sizes );
					table.setTotalWidth( PageSize.A4.getWidth() - 60 );
			        table.getDefaultCell().setBorderWidth(0);
			        table.getDefaultCell().setHorizontalAlignment( Element.ALIGN_CENTER );
			        table.getDefaultCell().setBackgroundColor( blue );
			        for( int j = 0; j < header.getSize(); j++  )
			        {
			        	table.addCell( new Phrase( header.get( j ), fh ) );
			        }
				}
				
				if( (i % 2) == 1 )
				{
			        table.getDefaultCell().setBackgroundColor( gray );
				}
				else
				{
			        table.getDefaultCell().setBackgroundColor( Color.white );
				}
				
				Stats st = elenco.get( i );
				
//		        table.addCell( new Phrase( st.getAsl(), f ) );
		        
		        for( int j = 0; j < st.getSize(); j++  )
		        {
			        table.addCell( new Phrase( st.getNext(), f ) );
		        }
		        
		        if( ((i + 1) % page_elem_mod_abb) == 0 )
				{
		        	table.writeSelectedRows( 0, -1, 30, PageSize.A4.getHeight() * 0.70f, cb );
		        	table = null;
				}
			}
			
			if( table != null )
			{
				table.writeSelectedRows( 0, -1, 30, PageSize.A4.getHeight() * 0.70f, cb );
			}
	        
	        
	        

			document.close();
				
				
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		
	}
	

	public static String printModuloArt17( ActionContext context, Organization macello, String asl, Organization esercente,
			Art17 art17, String data, ArrayList<Stats> elenco, Stats header, float[] sizes, String reportDir, Connection db, ConfigTipo configTipo )
	{
		try
		{
		    ByteArrayOutputStream out = new ByteArrayOutputStream();

		    moduloArt17( elenco, macello, asl, esercente, data, art17, header, sizes, out, reportDir, db, configTipo );

		    //write( context, out, "ART17 mac. " + macello.getName() + " eserc. " + esercente.getName(), data );
		    GestioneAllegatiMacelli allegato = new GestioneAllegatiMacelli();
			allegato.setBa(out.toByteArray());
			allegato.setOrg(macello.getOrgId());
			allegato.setDataMacellazione(data);
			allegato.setTipoDocumento("Macelli_17");
			return allegato.chiamaServerDocumentale(context);
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally {
			return "-none-";
		}
		
	}

	private static void moduloArt17( ArrayList<Stats> elenco, Organization macello, String asl, Organization esercente,
			String data, Art17 art17, Stats header, float[] sizes, ByteArrayOutputStream out, String reportDir, Connection db, ConfigTipo configTipo )
	{
		
		try
		{
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, out);
			document.open();
			
			PdfContentByte cb = writer.getDirectContent();

			PdfPTable	table	= null;
			Color		gray	= new Color( 226, 226, 226 );
			Color		blue	= new Color( 114, 159, 207 );
			Font		f		= new Font(Font.HELVETICA, 8, Font.NORMAL, Color.black);
			Font		fh		= new Font(Font.HELVETICA, 9, Font.NORMAL, Color.white);
			BaseFont	fp		= BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );// 8, Font.ITALIC, blue);
			BaseFont	fp2		= BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
			
			int numero_pagine	= calcolaNumeroPagine( elenco.size(), page_elem_mod_art17 );
			int curr_pag		= 0;
			
			for( int i = 0; i < elenco.size(); i++ )
			{
				if( (i % page_elem_mod_art17) == 0 )
				{
					document.newPage();
					++curr_pag;
					
					//ellisse in alto a destra
					cb.saveState();
					cb.setColorStroke(Color.black);
					cb.ellipse( PageSize.A4.getWidth() - 60, PageSize.A4.getHeight() - 60, 
							PageSize.A4.getWidth() - 140, PageSize.A4.getHeight() - 100 );
					cb.stroke();
					cb.saveState();
					
					//Logo asl in alto a sinistra
					int idAsl = macello.getSiteId();
					
					if(idAsl >= 201){
						
						/*HashMap<Integer, String> logoAsl = new HashMap<Integer, String>();
						logoAsl.put(-1, "regionecampania.jpg");
						logoAsl.put(0, "regionecampania.jpg");
						logoAsl.put(1, "AV1.jpg");
						logoAsl.put(2, "AV2.jpg");
						logoAsl.put(3, "Benevento.jpg");
						logoAsl.put(4, "Caserta1.jpg");
						logoAsl.put(5, "Caserta2.jpg");
						logoAsl.put(6, "NA1centro.jpg");
						logoAsl.put(7, "Napoli2.jpg");
						logoAsl.put(8, "Napoli3.jpg");
						logoAsl.put(9, "Napoli4.jpg");
						logoAsl.put(10, "NA5.jpg");
						logoAsl.put(11, "SA1.jpg");
						logoAsl.put(12, "SA2.jpg");
						logoAsl.put(13, "SA3.jpg"); 
						String logo = logoAsl.get(idAsl);*/
						LookupList asl_img = new LookupList(db,"lookup_site_id");
				    	Image image = Image.getInstance(reportDir + "/images/"+asl_img.getSelectedValue(idAsl)+".jpg");	
						cb.saveState();
						cb.addImage(image, 100F, 0, 0, 70F, 30, PageSize.A4.getHeight()- 80);
						cb.stroke();
						cb.saveState();
						
					}
					
					//rettangolo in alto a sinistra
					cb.saveState();
					cb.setColorStroke(Color.black);
					cb.rectangle( 30, PageSize.A4.getHeight() - 113, 
							140, 20 );
					cb.stroke();
					cb.saveState();
					
					//rettangolo per il bollo in basso a destra
					cb.saveState();
					cb.setColorStroke(Color.black);
					cb.rectangle( 30, 55, 
							35, 35 );
					cb.stroke();
					
					cb.beginText();
					
					//testo contenuto nell'ellisse
					cb.setFontAndSize( fp2, 11 );
					cb.setRGBColorFill( 0, 0, 0 );
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "IT",
							PageSize.A4.getWidth() - 100, PageSize.A4.getHeight() - 72, 0);
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, macello.getApprovalNumber(),
							PageSize.A4.getWidth() - 100, PageSize.A4.getHeight() - 84, 0);
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "CE",
							PageSize.A4.getWidth() - 100, PageSize.A4.getHeight() - 96, 0);
					
					//testo contenuto nel rettangolo in alto a sinistra
					cb.setFontAndSize( fp2, 10 );
					cb.setRGBColorFill( 0, 0, 0 );
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "n.",
							32, PageSize.A4.getHeight() - 107, 0);
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, +art17.getProgressivo() + "/" + art17.getAnno() + "/" + macello.getApprovalNumber(),
							117, PageSize.A4.getHeight() - 107, 0);
					
					//testo nel quadrato per il bollo
					cb.setFontAndSize( fp2, 5 );
					cb.setRGBColorFill( 0, 0, 0 );
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "BOLLO",
							48, 70, 0);

					//intestazione
					cb.setFontAndSize( fp2, 9 );
					cb.setRGBColorFill( 0, 0, 0 );
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "Regione Campania",
							PageSize.A4.getWidth() / 2, PageSize.A4.getHeight() * 0.955f, 0);
					

					cb.setFontAndSize( fp2, 11 );
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "Azienda Sanitaria Locale " + asl,
							PageSize.A4.getWidth() / 2, PageSize.A4.getHeight() * 0.935f, 0);

					
					cb.setFontAndSize( fp2, 9 );
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "Servizio Veterinario",
							PageSize.A4.getWidth() / 2, PageSize.A4.getHeight() * 0.915f, 0);
					

					cb.setFontAndSize( fp, 11 );
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "ISPEZIONI DELLE CARNI",
							PageSize.A4.getWidth() / 2, PageSize.A4.getHeight() * 0.895f, 0);
					

					cb.setFontAndSize( fp2, 5 );
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "(Art 17 R.D. 20/12/1928, N. 3298)",
							PageSize.A4.getWidth() / 2, PageSize.A4.getHeight() * 0.880f, 0);

					//numero pagina
					cb.setFontAndSize( fp, 8 );
					cb.setRGBColorFill(114, 159, 207);
					cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Pagina " + curr_pag + " di " + numero_pagine, 
							PageSize.A4.getWidth() - 30, PageSize.A4.getHeight() * 0.955f, 0);
					
					//dati sul macello e simili + firma del veterinario
					cb.setFontAndSize( fp2, 10 );
					cb.setRGBColorFill( 0, 0, 0 );
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "MACELLO: " + macello.getName(),
							30, PageSize.A4.getHeight() * 0.85f, 0);
					

					Address oa = macello.getAddress("5");
					oa = (oa == null) ? ((Address)macello.getAddressList().get(0)) : (oa);
					if( oa != null && (oa.getCity() != null) )
					{
						cb.setFontAndSize( fp2, 8 );
						cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Comune di " + ( (oa != null) ? (oa.getCity()) : ("") ) 
								+ " (" + ( (oa != null) ? (oa.getState()) : ("") ) + ")",
								30, PageSize.A4.getHeight() * 0.83f, 0);
					}
					
					cb.setFontAndSize( fp2, 7 );
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Data della macellazione: " + data,
							30, PageSize.A4.getHeight() * 0.80f, 0);
					

					String indirizzo_esercente = "";
					if(esercente.getOrgId()!= -999){
						Address oe = esercente.getAddress("Operativo");
						oe = (oe.getId() == -1) ? ((Address)esercente.getAddressList().get(0)) : (oe);
						indirizzo_esercente = " - " + ( (oe != null) ? (oe.getStreetAddressLine1() + ", " + oe.getCity() + " (" + oe.getState() + ")") : ("") );
					}
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "ESERCENTE: " + esercente.getName() + indirizzo_esercente,
							30, PageSize.A4.getHeight() * 0.78f, 0);
					
					cb.setFontAndSize( fp2, 7 );
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, 
							"Materiale specifico a rischio distrutto come per legge", 
							30, PageSize.A4.getHeight() * 0.12f, 0);
					
					cb.setFontAndSize( fp, 8 );
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, 
							"IL VETERINARIO UFFICIALE", 
							PageSize.A4.getWidth() - 130, PageSize.A4.getHeight() * 0.08f, 0);
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, 
							"DELL'ASL " + asl, 
							PageSize.A4.getWidth() - 130, PageSize.A4.getHeight() * 0.07f, 0);

					cb.endText();
					
					table = new PdfPTable( sizes );
					table.setTotalWidth( PageSize.A4.getWidth() - 60 );
			        table.getDefaultCell().setBorderWidth(0);
			        table.getDefaultCell().setHorizontalAlignment( Element.ALIGN_CENTER );
			        table.getDefaultCell().setBackgroundColor( blue );
			        for( int j = 0; j < header.getSize(); j++  )
			        {
			        	table.addCell( new Phrase( header.get( j ), fh ) );
			        }
				}
				
				if( (i % 2) == 1 )
				{
			        table.getDefaultCell().setBackgroundColor( gray );
				}
				else
				{
			        table.getDefaultCell().setBackgroundColor( Color.white );
				}
				
				Stats st = elenco.get( i );
				
//		        table.addCell( new Phrase( st.getAsl(), f ) );
		        
		        for( int j = 0; j < st.getSize(); j++  )
		        {
			        table.addCell( new Phrase( st.getNext(), f ) );
		        }
		        
		        if( ((i + 1) % page_elem_mod_art17) == 0 )
				{
		        	table.writeSelectedRows( 0, -1, 30, PageSize.A4.getHeight() * 0.70f, cb );
		        	table = null;
				}
			}
			
			if( table != null )
			{
				table.writeSelectedRows( 0, -1, 30, PageSize.A4.getHeight() * 0.76f, cb );
			}
	        
	        
			
	        
			document.newPage();
			++curr_pag;
			
			cb.beginText();
			cb.setFontAndSize( fp2, 10 );
			cb.setRGBColorFill( 0, 0, 0 );
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "ALTRE SPECIE  *",30, PageSize.A4.getHeight() * 0.95f, 0);
			cb.endText();
			
			PdfPTable table2=new PdfPTable(sizes);
			table2.setTotalWidth( PageSize.A4.getWidth() - 60 );
	        table2.getDefaultCell().setBorderWidth(0);
	        table2.getDefaultCell().setHorizontalAlignment( Element.ALIGN_CENTER );
	        table2.getDefaultCell().setBackgroundColor( blue );
	        for( int j = 0; j < header.getSize(); j++  )
	        {
	        	table2.addCell( new Phrase( header.get( j ), fh ) );
	        	
	        }
	        
			if( table2 != null )
			{
				table2.writeSelectedRows( 0, -1, 30, PageSize.A4.getHeight() * 0.93f, cb );
			}
			
			TextField field = null;
			String nomicampi[] = {"auricolare","mezzene","dataNascita","specie","categoria","sesso","modello4","esitoVisita"};
			final int NUM_RIGHE = Integer.parseInt(ApplicationProperties.getProperty("NUM_RIGHE_ALTRE_SPECIE"));
			int NUM_COLONNE = configTipo.getNumeroColonneArt17(); //Integer.parseInt(ApplicationProperties.getProperty("NUM_COLONNE_ALTRE_SPECIE"));
			final float TABLE_WIDTH = 648.5F;
			
			float x1 = 0F;
			float x2 = 30F;
//			float y1 = 777.9F;
//			float y2 = 792.9F;
			float y1 = 760.9F;
			float y2 = 775.9F;
			
			ArrayList<HashMap<Integer, String>> art17AltreSpecieList = Art17AltreSpecieDao.getInstance().select(macello.getOrgId(), esercente.getOrgId(), esercente.getName(), data, db);
			
			for(int j = 0; j < NUM_RIGHE; j++){
				
				HashMap<Integer, String> art17AltreSpecieHash = null;
				
				if(j < art17AltreSpecieList.size()){
					art17AltreSpecieHash = art17AltreSpecieList.get(j);
				}
				else{
					art17AltreSpecieHash = new HashMap<Integer, String>();
				}
				
				y1 = y1 - 15F;
				y2 = y2 - 15F;
				
				for(int i = 0; i < NUM_COLONNE; i++){
					
					x1 = x2;
					x2 = x2 + TABLE_WIDTH * sizes[i];
					
					field = new TextField(writer, new Rectangle( x1, y1, x2, y2), nomicampi[i] + "_" + (j + 1) );
					field.setBorderColor(Color.BLACK);
					field.setBorderWidth(0F);
					field.setAlignment(Element.ALIGN_RIGHT);
					field.setMaxCharacterLength(100);
					field.setFontSize(8);
					field.setText(art17AltreSpecieHash.get(i) != null ? art17AltreSpecieHash.get(i) : "");
					field.setOptions(TextField.EDIT | TextField.VISIBLE);
					writer.addAnnotation(field.getTextField());
				}
				
				x1 = 0F;
				x2 = 30F;
				
			}
			
			TextField idMacello = new TextField(writer, new Rectangle(0,0,0,0), "idMacello" );
			idMacello.setOptions(TextField.HIDDEN);
			idMacello.setText(""+macello.getOrgId());
			writer.addAnnotation(idMacello.getTextField());
			
			TextField idEsercente = new TextField(writer, new Rectangle(0,0,0,0), "idEsercente" );
			idEsercente.setOptions(TextField.HIDDEN);
			idEsercente.setText(""+esercente.getOrgId());
			writer.addAnnotation(idEsercente.getTextField());
			
			TextField nomeEsercente = new TextField(writer, new Rectangle(0,0,0,0), "nomeEsercente" );
			nomeEsercente.setOptions(TextField.HIDDEN);
			nomeEsercente.setText(""+esercente.getName());
			writer.addAnnotation(nomeEsercente.getTextField());
			
			TextField dataMacellazione = new TextField(writer, new Rectangle(0,0,0,0), "dataMacellazione" );
			dataMacellazione.setOptions(TextField.HIDDEN);
			dataMacellazione.setText(data);
			writer.addAnnotation(dataMacellazione.getTextField());
			
			y1 = y1 - 20F;
			y2 = y2 - 20F;
			
//			cb.beginText();
//			cb.setFontAndSize( fp2, 7 );
//			cb.setRGBColorFill( 0, 0, 0 );
//			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "* Per salvare in banca dati le informazioni inserite fare click su Salva",30, y1, 0);
//			cb.endText();
//			
//			y1 = y1 - 30F;
//			y2 = y2 - 30F;
//			PushbuttonField submitBtn = new PushbuttonField(writer,new Rectangle(30F, y1, 60F, y2),"submitPOST");
//			submitBtn.setBackgroundColor(Color.GRAY);
//			submitBtn.setBorderStyle(PdfBorderDictionary.STYLE_BEVELED);
//			submitBtn.setText("Salva");
//			submitBtn.setOptions(PushbuttonField.VISIBLE_BUT_DOES_NOT_PRINT);
//			PdfFormField submitField = submitBtn.getField();
//			submitField.setAction(PdfAction.createSubmitForm(ApplicationProperties.getProperty("urlArt17Servlet"),null, PdfAction.SUBMIT_HTML_FORMAT));
//			writer.addAnnotation(submitField);

			/*
	        int NUM_RIGHE_ALTRE_SPECIE = 10;
	        for(int i = 0; i< NUM_RIGHE_ALTRE_SPECIE; i++ ){
	        	
	        	if( (i % 2) == 1 )
				{
			        table2.getDefaultCell().setBackgroundColor( gray );
				}
				else
				{
			        table2.getDefaultCell().setBackgroundColor( Color.white );
				}
	        	
	        	for( int j = 0; j < header.getSize(); j++  )
		        {
	        		
	        		//table.addCell("Prova");
	        		TextField ageComb = new TextField(writer, new Rectangle(0,
	        			 0, 30, 10), "prova");
	        		ageComb.setBorderColor(Color.BLACK);
	        		ageComb.setAlignment(Element.ALIGN_RIGHT);
	        		ageComb.setMaxCharacterLength(2);
	        		ageComb.setOptions(TextField.EDIT | TextField.VISIBLE);
	        		writer.addAnnotation(ageComb.getTextField());
	        		//document.add((Element) ageComb);
//	        		PdfPCell cell = new PdfPCell();
//	        		cell.setCellEvent(new FieldCell( ageComb.getTextField(), 30, writer ));
	        		//cell.setCellEvent(new FieldCell(ageComb.getTextField(), 30, writer));
	        		//table2.addCell( cell.setCellEvent(new FieldCell(ageComb.getTextField(), 30, writer));
	        		
		        }
	        	
	        	
	        	
	        	
	        	
	        	
	        }
	        */
	        
			/*
			TextField ageComb = new TextField(writer, new Rectangle(30,
       			 762.9F, 95F, 777.9F), "prova");
       		ageComb.setBorderColor(Color.BLACK);
       		ageComb.setAlignment(Element.ALIGN_RIGHT);
       		ageComb.setMaxCharacterLength(100);
       		ageComb.setFontSize(8);
       		ageComb.setOptions(TextField.EDIT | TextField.VISIBLE);
       		
       		TextField ageComb2 = new TextField(writer, new Rectangle(95F,
          			 762.9F, 160F, 777.9F), "prova2");
          		ageComb2.setBorderColor(Color.BLACK);
          		ageComb2.setAlignment(Element.ALIGN_RIGHT);
          		ageComb2.setMaxCharacterLength(100);
          		ageComb2.setFontSize(8);
          		ageComb2.setOptions(TextField.EDIT | TextField.VISIBLE);
          		
      		TextField ageComb3 = new TextField(writer, new Rectangle(160F,
         			 762.9F, 208.75F, 777.9F), "prova3");
         		ageComb3.setBorderColor(Color.BLACK);
         		ageComb3.setAlignment(Element.ALIGN_RIGHT);
         		ageComb3.setMaxCharacterLength(100);
         		ageComb3.setFontSize(8);
         		ageComb3.setOptions(TextField.EDIT | TextField.VISIBLE);
       		
       		writer.addAnnotation(ageComb.getTextField());
       		writer.addAnnotation(ageComb2.getTextField());
       		writer.addAnnotation(ageComb3.getTextField());
       		
       		*/
	        

//			if( table2 != null )
//			{
//				table2.writeSelectedRows( 0, -1, 30, PageSize.A4.getHeight() * 0.95f, cb );
//			}
			
	        
	        
			//document.add(table2);
			
//			if( table2 != null )
//			{
//				table2.writeSelectedRows( 0, -1, 30, PageSize.A4.getHeight() * 0.76f, cb );
//			}
			
			
			
			document.close();
				
				
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		
	}
	

	public static void printModuloMortiStalla( ActionContext context, Organization macello, String asl, String speditore,
			String data, ArrayList<Stats> elenco, Stats header, float[] sizes )
	{
		try
		{
		    ByteArrayOutputStream out = new ByteArrayOutputStream();

		    moduloMortiStalla( elenco, macello, asl, speditore, data, header, sizes, out );

		    write( context, out, "Morti in Stalla mac. " + macello.getName() + " spedit. " + speditore, data );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	private static void moduloMortiStalla( ArrayList<Stats> elenco, Organization macello, String asl, String speditore, String data,
			Stats header, float[] sizes, ByteArrayOutputStream out )
	{
		try
		{
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, out);
			document.open();
			
			PdfContentByte cb = writer.getDirectContent();

			PdfPTable	table	= null;
			Color		gray	= new Color( 226, 226, 226 );
			Color		blue	= new Color( 114, 159, 207 );
			Font		f		= new Font(Font.HELVETICA, 8, Font.NORMAL, Color.black);
			Font		fh		= new Font(Font.HELVETICA, 9, Font.NORMAL, Color.white);
			BaseFont	fp		= BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );// 8, Font.ITALIC, blue);
			BaseFont	fp2		= BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
			
			int numero_pagine	= calcolaNumeroPagine( elenco.size(), page_elem_mod_morti_stalla );
			int curr_pag		= 0;
			
			for( int i = 0; i < elenco.size(); i++ )
			{
				if( (i % page_elem_mod_morti_stalla) == 0 )
				{
					document.newPage();
					++curr_pag;
					
					//ellisse in alto a destra
//					cb.saveState();
//					cb.setColorStroke(Color.black);
//					cb.ellipse( PageSize.A4.width() - 60, PageSize.A4.height() - 60, 
//							PageSize.A4.width() - 140, PageSize.A4.height() - 100 );
//					cb.stroke();
//					cb.saveState();
					
					//rettangolo in alto a sinistra
//					cb.saveState();
//					cb.setColorStroke(Color.black);
//					cb.rectangle( 30, PageSize.A4.height() - 75, 
//							95, 20 );
//					cb.stroke();
//					cb.saveState();
					
					//rettangolo per il bollo in basso a destra
//					cb.saveState();
//					cb.setColorStroke(Color.black);
//					cb.rectangle( 30, 55, 
//							35, 35 );
//					cb.stroke();
					
					cb.beginText();
					
					//testo contenuto nell'ellisse
//					cb.setFontAndSize( fp2, 11 );
//					cb.setRGBColorFill( 0, 0, 0 );
//					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "IT",
//							PageSize.A4.width() - 100, PageSize.A4.height() - 72, 0);
//					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "1222 M",
//							PageSize.A4.width() - 100, PageSize.A4.height() - 84, 0);
//					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "CE",
//							PageSize.A4.width() - 100, PageSize.A4.height() - 96, 0);
					
					//testo contenuto nel rettangolo in alto a sinistra
//					cb.setFontAndSize( fp2, 10 );
//					cb.setRGBColorFill( 0, 0, 0 );
//					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "n.",
//							38, PageSize.A4.height() - 69, 0);
//					cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, art17.getProgressivo() + "/" + art17.getAnno(),
//							117, PageSize.A4.height() - 69, 0);
					
					//testo nel quadrato per il bollo
//					cb.setFontAndSize( fp2, 5 );
//					cb.setRGBColorFill( 0, 0, 0 );
//					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "BOLLO",
//							48, 70, 0);

					//intestazione
					cb.setFontAndSize( fp2, 9 );
					cb.setRGBColorFill( 0, 0, 0 );
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "Regione Campania",
							PageSize.A4.getWidth() / 2, PageSize.A4.getHeight() * 0.955f, 0);
					

					cb.setFontAndSize( fp2, 11 );
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "Azienda Sanitaria Locale " + asl,
							PageSize.A4.getWidth() / 2, PageSize.A4.getHeight() * 0.935f, 0);

					
					cb.setFontAndSize( fp2, 9 );
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "Servizio Veterinario",
							PageSize.A4.getWidth() / 2, PageSize.A4.getHeight() * 0.915f, 0);
					

					cb.setFontAndSize( fp, 11 );
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "CAPI MORTI IN STALLA",
							PageSize.A4.getWidth() / 2, PageSize.A4.getHeight() * 0.895f, 0);
					

//					cb.setFontAndSize( fp2, 5 );
//					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "(Art 17 R.D. 20/12/1928, N. 3298)",
//							PageSize.A4.width() / 2, PageSize.A4.height() * 0.880f, 0);

					//numero pagina
					cb.setFontAndSize( fp, 8 );
					cb.setRGBColorFill(114, 159, 207);
					cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Pagina " + curr_pag + " di " + numero_pagine, 
							PageSize.A4.getWidth() - 30, PageSize.A4.getHeight() * 0.955f, 0);
					
					//dati sul macello e simili + firma del veterinario
					cb.setFontAndSize( fp2, 10 );
					cb.setRGBColorFill( 0, 0, 0 );
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "MACELLO: " + macello.getName(),
							30, PageSize.A4.getHeight() * 0.85f, 0);
					

					Address oa = macello.getAddress("Operativo");
					oa = (oa == null) ? ((Address)macello.getAddressList().get(0)) : (oa);
					if( oa != null && (oa.getCity() != null) )
					{
						cb.setFontAndSize( fp2, 8 );
						cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Comune di " + ( (oa != null) ? (oa.getCity()) : ("") ) 
								+ " (" + ( (oa != null) ? (oa.getState()) : ("") ) + ")",
								30, PageSize.A4.getHeight() * 0.83f, 0);
					}
					
					cb.setFontAndSize( fp2, 7 );
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Data: " + data,
							30, PageSize.A4.getHeight() * 0.80f, 0);

					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "ESERCENTE: " + speditore,
							30, PageSize.A4.getHeight() * 0.78f, 0);
					
					cb.setFontAndSize( fp2, 7 );
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, 
							"Materiale specifico a rischio distrutto come per legge", 
							30, PageSize.A4.getHeight() * 0.12f, 0);
					
					cb.setFontAndSize( fp, 8 );
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, 
							"IL VETERINARIO UFFICIALE", 
							PageSize.A4.getWidth() - 130, PageSize.A4.getHeight() * 0.08f, 0);
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, 
							"DELL'ASL " + asl, 
							PageSize.A4.getWidth() - 130, PageSize.A4.getHeight() * 0.07f, 0);

					cb.endText();
					
					table = new PdfPTable( sizes );
					table.setTotalWidth( PageSize.A4.getWidth() - 60 );
			        table.getDefaultCell().setBorderWidth(0);
			        table.getDefaultCell().setHorizontalAlignment( Element.ALIGN_CENTER );
			        table.getDefaultCell().setBackgroundColor( blue );
			        for( int j = 0; j < header.getSize(); j++  )
			        {
			        	table.addCell( new Phrase( header.get( j ), fh ) );
			        }
				}
				
				if( (i % 2) == 1 )
				{
			        table.getDefaultCell().setBackgroundColor( gray );
				}
				else
				{
			        table.getDefaultCell().setBackgroundColor( Color.white );
				}
				
				Stats st = elenco.get( i );
				
//		        table.addCell( new Phrase( st.getAsl(), f ) );
		        
		        for( int j = 0; j < st.getSize(); j++  )
		        {
			        table.addCell( new Phrase( st.getNext(), f ) );
		        }
		        
		        if( ((i + 1) % page_elem_mod_morti_stalla) == 0 )
				{
		        	table.writeSelectedRows( 0, -1, 30, PageSize.A4.getHeight() * 0.70f, cb );
		        	table = null;
				}
			}
			
			if( table != null )
			{
				table.writeSelectedRows( 0, -1, 30, PageSize.A4.getHeight() * 0.76f, cb );
			}
	        
			

			document.close();
				
				
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		
	
	}

	private static final int page_elem_reg_mac			= 11;//era 15
	private static final int page_elem_mod_bse			= 10;
	private static final int page_elem_mod_abb			= 15;
	private static final int page_elem_mod_art17		= 15;
	private static final int page_elem_mod_morti_stalla	= 15;
}
