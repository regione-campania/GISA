/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.servlet.ServletOutputStream;

import org.apache.commons.beanutils.DynaBean;

public class XLSUtils {
	
	private static final String squote = "<tr><td align=center>";
	private static final String equote = "</td></tr>";
	private static final String dquote = "</td><td align=center>";
	private static final String initialBold="<b>";
	private static final String endBold="</b>";
	
	
	private static final String blueFont = "<font color='blue'>";
	private static final String redFont = "<font color='red'>";
	private static final String endFont  = "</font>";
	
	
	
	
	public static void dynamicHeader (ResultSet rs , ServletOutputStream sout) throws IOException, SQLException {
		 

		  
		  //Lo String Buffer
		  StringBuffer sb = new StringBuffer(); 
		  
		  //Serve ad ottenere i meta-dati del ResultSet
		  ResultSetMetaData rsmd=rs.getMetaData();
		  
		  //Il numero di attributi
		  int columnNumber=rsmd.getColumnCount();
		  
		  
		  sout.write(("<tr><td colspan=\""+columnNumber+"\" border=\"1\">"+blueFont+ "Estrazione mensile (primo giorno del mese - tipo estratto conto) dei dati relativi ai cani presenti nei canili; per ognuno di quest'ultimi, vi è un record contenente asl, denominazione e totale dei cani ivi presenti."+endFont+"</td></tr>").getBytes());
		  sb.append(squote);
		
		  for (int i=1;i<=columnNumber;i++) {
			sb.append(initialBold);
			sb.append(blueFont);
			sb.append( rsmd.getColumnName(i) );
			sb.append(endFont);
			sb.append(endBold);
			if( i < columnNumber )
			{
				sb.append( dquote );
			}			
		 }
		  		
			sb.append( equote );
			sb.append( "\r\n" );
			
			sout.write( sb.toString().getBytes() );
		  
	  }
	
	 /* Questo metodo serve a stampare ogni tupla sul file*/
	  public static void dynamicRow (DynaBean dynaBean, ResultSet rs, ServletOutputStream sout) throws IOException, SQLException {
		  
		  //Lo String Buffer
		  StringBuffer sb = new StringBuffer(); 
		  
		  //Serve ad ottenere i meta-dati del ResultSet
		  ResultSetMetaData rsmd=rs.getMetaData();
		  
		  //Il numero di attributi
		  int columnNumber=rsmd.getColumnCount();
		  	  
		  sb.append(squote);
			 
		  for (int i=1;i<=columnNumber;i++) {
				
			sb.append( nullCheck( dynaBean, rsmd.getColumnName(i) ) );
			if( i < columnNumber )
			{
				sb.append( dquote );
			}
					
			}
		  		
			sb.append( equote );
			sb.append( "\r\n" );
			
			sout.write( sb.toString().getBytes() );
		  
		  
	  }
	  
		 private static Object nullCheck( DynaBean dynaBean, String string )
		 {
		 	return (dynaBean.get( string ) == null) ? ("") : (dynaBean.get( string ));
		 }
		 
		 
		 public static StringBuffer dynamicHeaderSb (ResultSet rs , StringBuffer sb) throws IOException, SQLException {
			 

			  
			  //Lo String Buffer
			//  StringBuffer sb = new StringBuffer(); 
			  
			  //Serve ad ottenere i meta-dati del ResultSet
			  ResultSetMetaData rsmd=rs.getMetaData();
			  
			  //Il numero di attributi
			  int columnNumber=rsmd.getColumnCount();
			  
			  
			  sb.append(("<tr><td colspan=\""+columnNumber+"\" border=\"1\">"+blueFont+ "Estrazione mensile (primo giorno del mese - tipo estratto conto) dei dati relativi ai cani presenti nei canili; per ognuno di quest'ultimi, vi è un record contenente asl, denominazione e totale dei cani ivi presenti."+endFont+"</td></tr>").getBytes());
			  sb.append(squote);
			
			  for (int i=1;i<=columnNumber;i++) {
				sb.append(initialBold);
				sb.append(blueFont);
				sb.append( rsmd.getColumnName(i) );
				sb.append(endFont);
				sb.append(endBold);
				if( i < columnNumber )
				{
					sb.append( dquote );
				}			
			 }
			  		
				sb.append( equote );
				sb.append( "\r\n" );
				
				//sout.write( sb.toString().getBytes() );
			  
				
				return sb;
		  }
		 
		 
		 
		 /* Questo metodo serve a stampare ogni tupla sul file*/
		  public static StringBuffer dynamicRowSb (DynaBean dynaBean, ResultSet rs, StringBuffer sout) throws IOException, SQLException {
			  
			  //Lo String Buffer
			//  StringBuffer sb = new StringBuffer(); 
			  
			  //Serve ad ottenere i meta-dati del ResultSet
			  ResultSetMetaData rsmd=rs.getMetaData();
			  
			  //Il numero di attributi
			  int columnNumber=rsmd.getColumnCount();
			  	  
			  sout.append(squote);
				 
			  for (int i=1;i<=columnNumber;i++) {
					
				sout.append( nullCheck( dynaBean, rsmd.getColumnName(i) ) );
				if( i < columnNumber )
				{
					sout.append( dquote );
				}
						
				}
			  		
				sout.append( equote );
				sout.append( "\r\n" );
				
				//sout.write( sb.toString().getBytes() );
				
				return sout;
			  
			  
		  }

}
