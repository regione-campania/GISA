/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util;

//import java.awt.Color;
import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.jfree.report.ElementAlignment;
//import org.jfree.report.JFreeReport;
//import org.jfree.report.elementfactory.TextFieldElementFactory;

public class XlsUtil
{
	public static void write( String csv, ServletOutputStream out )
	{
		Vector<String[]> griglia	= new Vector<String[]>();
		String[] righe				= csv.split( "\n" );
		int nrow					= righe.length;
		int ncol					= -1;
		
		//JFreeReportBoot.getInstance().start();
		
		for( int i = 0; i < nrow; i++ )
		{
			String[] temp	= righe[i].split( "\t" );
			ncol			= (temp.length > ncol) ? (temp.length) : (ncol);
			for( int j = 0; j < temp.length; j++ )
			{
				temp[j] = temp[j].trim();
			}
			griglia.add( temp );
		}
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet( "main" );
		
		for( int i = 0; i < nrow; i++ )
		{
			String[] riga	= griglia.elementAt( i );
			int nThisRow	= riga.length;
			HSSFRow row = sheet.createRow( i + 1 );
			row.setHeightInPoints( 12 );
			
			for( short j = 0; j < ncol; j++ )
			{
				HSSFCell cell = row.createCell( j );
				cell.setCellValue( new HSSFRichTextString( (j < nThisRow)?(riga[j]):("") ) );
			}
		}
		
		settaLarghezze( sheet, ncol, griglia );
		
		try
		{
			wb.write( out );
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		//DefaultTableModel dtm = new DefaultTableModel( nrow, ncol );
		//Log.debug( "CREATO DFM -> " + dtm.toString() );
		/*
		for( int i = 0; i < nrow; i++ )
		{
			String[] riga	= griglia.elementAt( i );
			int nThisRow	= riga.length;
			
			for( int j = 0; j < ncol; j++ )
			{
				Log.debug( "RIGA " + i + " COLONNA " + j + " = " + riga[j] );
				dtm.setValueAt( (j < nThisRow)?(riga[j]):(""), i, j );
			}
		}
		*/
		

		//for( int i = 0; i < ncol; i++ )
		//{
		//	dtm.addColumn( i + "" );
		//}
		
		/*
		for( int i = 0; i < nrow; i++ )
		{
			dtm.addRow( griglia.elementAt( i ) );
			Log.debug( "Aggiunta riga " + i );
		}
		*/

		//for( int i = 0; i < nrow; i++ )
		//{
		//	String[] riga	= griglia.elementAt( i );
		//	int nThisRow	= riga.length;
			
		//	for( int j = 0; j < ncol; j++ )
		//	{
		//		Log.debug( "RIGA " + i + " COLONNA " + j + " = " + riga[j] );
		//		dtm.setValueAt( (j < nThisRow)?(riga[j]):(""), i, j );
		//	}
		//}
		
		//JFreeReport jfr = createReportDefinition( ncol );
		//Log.debug( "Creato report" );
		//jfr.setData( dtm );
		//DataFactory df = new TableDataFactory( "", dtm );
		//jfr.setDataFactory( df );
		//Log.debug( "Dati impostati" );
		
		/*
		try
		{
			PlainTextReportUtil.createPlainText( jfr, out );
			Log.debug( "Scritto Testo Plain!" );
			final FlowExcelOutputProcessor target = new FlowExcelOutputProcessor( jfr.getConfiguration(), out );
			final FlowReportProcessor reportProcessor = new FlowReportProcessor( jfr, target );
			Log.debug( "Creato XLS Processor" );
			reportProcessor.processReport();
			reportProcessor.close();
		}
		catch (Exception e)
		{
			Log.exception( e );
		}
		*/
		
		
	}

	private static void settaLarghezze(HSSFSheet sheet, int ncol, Vector<String[]> griglia )
	{
		int[] size = new int[ncol];
		
		for( int i = 0; i < griglia.size(); i++ )
		{
			String[] riga = griglia.elementAt( i );
			for( int j = 0; j < riga.length; j++ )
			{
				size[j] = ( 1 > size[j] ) ? (1) : (size[j]);
				size[j] = ( riga[j].length() > size[j] ) ? ( (riga[j].length() > 50) ? (50) : (riga[j].length()) ) : (size[j]);
			}
		}
		
		for( int col = 0; col < ncol; col++ )
		{
			sheet.setColumnWidth( (short)col, (short)(size[col] * 256) );
		}
	}

//	private static JFreeReport createReportDefinition ( int ncol )
//	  {
//
//	    final JFreeReport report = new JFreeReport();
//	    report.setName( "XLS EXPORT" );
//	    
//
//	    for( int i = 0; i < ncol; i++ )
//	    {	
//	    	TextFieldElementFactory factory = new TextFieldElementFactory();
//		    factory.setColor(Color.black);
//		    factory.setHorizontalAlignment(ElementAlignment.RIGHT);
//		    factory.setVerticalAlignment(ElementAlignment.MIDDLE);
//		    factory.setFieldname( i + "" );
//		    factory.setWidth( (float)50 );
//	    	report.getItemBand().addElement( factory.createElement() );
//	    }
//	    
//	    /*
//	    //TextFieldElementFactory factory = new TextFieldElementFactory();
//	    factory.setName("T1");
//	    factory.setAbsolutePosition(new Point2D.Float(0, 0));
//	    //factory.setMinimumSize(new FloatDimension(150, 12));
//	    factory.setColor(Color.black);
//	    factory.setHorizontalAlignment(ElementAlignment.RIGHT);
//	    factory.setVerticalAlignment(ElementAlignment.MIDDLE);
//	    factory.setNullString("-");
//	    factory.setFieldname("Column1");
//	    report.getItemBand().addElement(factory.createElement());
//
//	    factory = new TextFieldElementFactory();
//	    factory.setName("T2");
//	    factory.setAbsolutePosition(new Point2D.Float(200, 0));
//	    //factory.setMinimumSize(new FloatDimension(150, 12));
//	    factory.setColor(Color.black);
//	    factory.setHorizontalAlignment(ElementAlignment.LEFT);
//	    factory.setVerticalAlignment(ElementAlignment.MIDDLE);
//	    factory.setNullString("-");
//	    factory.setFieldname("Column2");
//	    report.getItemBand().addElement(factory.createElement());
//	    */
//	    return report;
//
//	  }
}
