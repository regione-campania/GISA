/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.nuovi_report.util;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.aspcfs.modules.nuovi_report.base.Stats;
import com.darkhorseventures.framework.actions.ActionContext;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfAnnotation;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

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
	public static void print( ActionContext context, String reportName, ArrayList<String> filtri, ArrayList<Stats> risultati, ArrayList<Stats> elenco, Stats header, float[] sizes, boolean tipo_report )
	{
		try
		{
			String					dir		= context.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "nuovi_report_pdf_template" + File.separator;
		    PdfReader				reader	= new PdfReader( dir + "Modello Report.pdf" );
		    ByteArrayOutputStream 	out		= new ByteArrayOutputStream();
		    ByteArrayOutputStream 	out2	= new ByteArrayOutputStream();
		       
		    PdfStamper				stamper	= new PdfStamper( reader, out );
		    AcroFields				form	= stamper.getAcroFields();

		    form.setField( "report", reportName );
			form.setField( "data", oggi() );
		    
		    setFiltri	( form, filtri );
		    setRisultati( form, risultati );
			
		    stamper.setFormFlattening( true );
		    stamper.close();

		     setElenco( stamper, elenco, reportName, header, sizes, out, out2 ,tipo_report);
		    write( context, out2, reportName );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
	public static void print2( ActionContext context, String reportName, ArrayList<String> filtri, ArrayList<Stats> risultati, ArrayList<Stats> elenco, Stats header, float[] sizes, boolean tipo_report,ArrayList<Stats> risultati2,ArrayList<Stats> risultati3)
	{
		try
		{
			PdfReader				reader = null;
			String	dir		= context.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "nuovi_report_pdf_template" + File.separator;
		/*    if (val==true){
				reader	= new PdfReader( dir + "Modello Report3.pdf" );
		    }
		    else
		    {
		  */  	reader	= new PdfReader( dir + "ModelloReport3.pdf" );
		    //}
		    ByteArrayOutputStream 	out		= new ByteArrayOutputStream();
		    ByteArrayOutputStream 	out2	= new ByteArrayOutputStream();
		      
		    PdfStamper				stamper	= new PdfStamper( reader, out );
		    AcroFields				form	= stamper.getAcroFields();

		    form.setField( "report", reportName );
			form.setField( "data", oggi() );
		    
		    setFiltri	( form, filtri );
		    setRisultati( form, risultati );
			setRisultati2( form, risultati2 );	
			setRisultati3( form, risultati3 );
			
		    stamper.setFormFlattening( true );
		    stamper.close();

		    setElenco( stamper, elenco, reportName, header, sizes, out, out2 ,tipo_report);
		    write( context, out2, reportName );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	private static void setElenco(PdfStamper stamper, ArrayList<Stats> elenco, String reportName, Stats header, 
			float[] sizes, ByteArrayOutputStream out, ByteArrayOutputStream out2,boolean tipo_report )
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
			Color		gray	= new Color( 226, 226, 226 );
			Color		blue	= new Color( 114, 159, 207 );
			Font		f		= new Font(Font.HELVETICA, 9, Font.NORMAL, Color.black);
			Font		fh		= new Font(Font.HELVETICA, 10, Font.NORMAL, Color.white);
			BaseFont	fp		= BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );// 8, Font.ITALIC, blue);
			if (tipo_report==true){
						f		= new Font(Font.HELVETICA, 8, Font.NORMAL, Color.black);
						fh		= new Font(Font.HELVETICA, 9, Font.NORMAL, Color.white);
					fp		= BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );// 8, Font.ITALIC, blue);
					
			}
			int numero_pagine	= calcolaNumeroPagine( elenco.size() );
			int curr_pag		= 0;
			
			for( int i = 0; i < elenco.size(); i++ )
			{
				if( (i % page_elem) == 0 )
				{
					document.newPage();
					++curr_pag;
					cb.beginText();
					cb.setFontAndSize( fp, 8 );
					cb.setRGBColorFill(114, 159, 207);
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "DETTAGLIO REPORT  \"" + reportName + "\"", 50, PageSize.A4.height() * 0.955f, 0);
					cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Pagina " + curr_pag + " di " + numero_pagine, PageSize.A4.width() - 50, PageSize.A4.height() * 0.955f, 0);
					cb.endText();
					
					table = new PdfPTable( sizes );
					table.setTotalWidth( PageSize.A4.width() - 100 );
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
				
		        table.addCell( new Phrase( st.getAsl(), f ) );
		        
		        for( int j = 0; j < st.getSize(); j++  )
		        {
			        table.addCell( new Phrase( st.getNext(), f ) );
		        }
		        
		        if( ((i + 1) % page_elem) == 0 )
				{
		        	table.writeSelectedRows( 0, -1, 50, PageSize.A4.height() * 0.94f, cb );
		        	table = null;
				}
			}
			
			if( table != null )
			{
				table.writeSelectedRows( 0, -1, 50, PageSize.A4.height() * 0.94f, cb );
			}
	        
	        
	        

			document.close();
				
				
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	private static int calcolaNumeroPagine(int size)
	{
		return (int)Math.ceil( (size * 1.0) / page_elem );
	}

	private static void setFiltri(AcroFields form, ArrayList<String> filtri) throws IOException, DocumentException
	{
		String sum = "";
		for( String temp: filtri )
		{
			sum += (temp + "\r\n");
		}
		
		form.setField( "filtri", sum );
	}

	private static void setRisultati(AcroFields form, ArrayList<Stats> risultati) throws IOException, DocumentException
	{
		for( int i = 0; i < risultati.size(); i++ )
		{
			Stats temp = risultati.get( i );
			for( int j = 0; j < temp.getSize(); j++ )
			{
				form.setField( "r" + i + "c" + j, temp.getNext() );
				//imposto visibile il campo
				form.setFieldProperty( "r" + i + "c" + j, "flags", PdfAnnotation.FLAGS_PRINT, null );
				//NB: per renderlo invisibile: form.setFieldProperty( "r" + i + "c" + j, "flags", PdfAnnotation.FLAGS_PRINT | PdfAnnotation.FLAGS_HIDDEN, null );
			}
		}
	}

	private static void setRisultati2(AcroFields form, ArrayList<Stats> risultati) throws IOException, DocumentException
	{
		for( int i = 0; i < risultati.size(); i++ )
		{
			Stats temp = risultati.get( i );
			for( int j = 0; j < temp.getSize(); j++ )
			{
				form.setField( "asl" + i + "tot" + j, temp.getNext() );
				//imposto visibile il campo
				form.setFieldProperty( "asl" + i + "tot" + j, "flags", PdfAnnotation.FLAGS_PRINT, null );
				//NB: per renderlo invisibile: form.setFieldProperty( "r" + i + "c" + j, "flags", PdfAnnotation.FLAGS_PRINT | PdfAnnotation.FLAGS_HIDDEN, null );
			}
		}
	}
	private static void setRisultati3(AcroFields form, ArrayList<Stats> risultati) throws IOException, DocumentException
	{
		for( int i = 0; i < risultati.size(); i++ )
		{
			Stats temp = risultati.get( i );
			for( int j = 0; j < temp.getSize(); j++ )
			{
				form.setField( "a" + i + "tot" + j, temp.getNext() );
				//imposto visibile il campo
				form.setFieldProperty( "a" + i + "tot" + j, "flags", PdfAnnotation.FLAGS_PRINT, null );
				//NB: per renderlo invisibile: form.setFieldProperty( "r" + i + "c" + j, "flags", PdfAnnotation.FLAGS_PRINT | PdfAnnotation.FLAGS_HIDDEN, null );
			}
		}
	}
	private static void write(ActionContext context, ByteArrayOutputStream out, String reportName) throws IOException
	{
		HttpServletResponse res = context.getResponse();
	 	res.setContentType( "application/pdf" );
	 	res.setHeader( "Content-Disposition","attachment; filename=\"" + timestamp() + " " + reportName + ".pdf\";" ); 
	 	
	 	ServletOutputStream sout = res.getOutputStream();
	 	sout.write( out.toByteArray() );
	 	sout.flush();
		
	}

	private static String timestamp()
	{
		return (new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")).format( new Date() );
	}

	private static String oggi()
	{
		return (new SimpleDateFormat("dd/MM/yyyy")).format( new Date() );
	}
	
	private static final int page_elem = 55;
	
}
