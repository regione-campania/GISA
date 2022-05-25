/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils
{
	private static final Logger				logger				= LoggerFactory.getLogger( DateUtils.class );
	private static final String				datePattern			= "dd/MM/yyyy";
	private static final String				timestampPattern	= "yyyy-MM-dd HH:mm:ss";
	private static final SimpleDateFormat	sdf					= new SimpleDateFormat( datePattern );
	private static final SimpleDateFormat	timestampSdf		= new SimpleDateFormat( timestampPattern );
	
	
	public static Timestamp parseTimestamp( String timestamp )
	{
		Timestamp ret = null;
		try
		{
			logger.debug( "Parsing timestamp: " + timestamp );
			if( (timestamp != null) && (timestamp.trim().length() > 0) )
			{
				ret = new Timestamp ( timestampSdf.parse( timestamp.trim() ).getTime() );
			}
		}
		catch ( Exception e )
		{
			logger.error( "Errore nel parsing del timestamp: " + timestamp + ". Formato accettato: " + timestampPattern, e );
		}
		
		return ret;
	}
	
	/**
	 * 
	 * @param data nel formato dd/MM/yyyy
	 * @return
	 */
	public static String dataToString( Date data )
	{
		String ret = null;
		
		try
		{
			if( data != null )
			{
				ret = sdf.format( data );
			}
		}
		catch ( Exception e )
		{
			logger.error( "Errore nella formattazione della data: " + data, e );
		}
		
		return ret;
	}
	
	/**
	 * 
	 * @param data nel formato dd/MM/yyyy
	 * @return
	 */
	public static Date parseDateUtil( String data )
	{
		Date ret = null;
		
		try
		{
			logger.debug( "Parsing data: " + data );
			if( (data != null) && (data.trim().length() > 0) )
			{
				ret = sdf.parse( data.trim() );
			}
		}
		catch ( Exception e )
		{
			logger.error( "Errore nel parsing della data: " + data + ". Formato accettato: " + datePattern, e );
		}
		
		return ret;
	}
	
	/**
	 * 
	 * @param data nel formato dd/MM/yyyy
	 * @return
	 */
	public static java.sql.Date parseDateSql( String data )
	{
		java.sql.Date ret = null;
		
		try
		{
			ret = new java.sql.Date( sdf.parse( data ).getTime() );
		}
		catch ( Exception e )
		{
			logger.error( "", e );
		}
		
		return ret;
	}
	
	/**
	 * 
	 * @param data nel formato dd/MM/yyyy
	 * @return
	 */
	public static Timestamp parseTimestampSql( String data )
	{
		Timestamp ret = null;
		
		try
		{
			ret = new Timestamp( parseDateUtil( data ).getTime() );
		}
		catch ( Exception e )
		{
			logger.error( "", e );
		}
		
		return ret;
	}
	
	public static class MyUtilDateConverter implements Converter
	{

		@SuppressWarnings("unchecked")
		@Override
		public Object convert(Class clazz, Object value)
		{
			return parseDateUtil( (String)value );
		}
		
	}

	public static Date annoCorrente( Date data )
	{
		Date ret = null;
		
		SimpleDateFormat anno = new SimpleDateFormat( "yyyy" );
		try
		{
			ret = anno.parse( anno.format( data ) );
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public static String annoCorrenteString( Date data )
	{
		String ret = null;
		
		SimpleDateFormat anno = new SimpleDateFormat( "yyyy" );
		
		ret = anno.format( data );
		
		return ret;
	}

	public static Date annoSuccessivo( Date data )
	{
		Date ret = null;
		
		SimpleDateFormat anno = new SimpleDateFormat( "yyyy" );
		String annoCorrente = anno.format( data );
		String annoSuccessivo = Integer.toString( Integer.parseInt( annoCorrente ) + 1 );
		try
		{
			ret = anno.parse( annoSuccessivo );
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		
		return ret;
	}
	
	/* Per la gestione dell'agenda*/
	public static String calendarConvert (String data) {
				
		String parsedData="";
				
		if (data.contains("UTC"))
			parsedData = convertUTCDate(data);
		if (data.contains("GMT"))
			parsedData = convertGMTDate(data);
				
		return parsedData;
	}
	
	private static String convertGMTDate (String data) {
		
		String parsedData="";
		
		String mese 	= data.substring(4, 7);
		String giorno 	= data.substring(8, 10);
		String anno 	= data.substring(11, 15);
		String orario 	= data.substring(16, 24);
		
		parsedData = anno+"-"+resolveMonth(mese)+"-"+giorno+"T"+orario;
		
		return parsedData;
	}
	
	private static String convertUTCDate (String data) {
		String parsedData="";		
			
		String mese 	= data.substring(4, 7);
		String giorno 	= data.substring(8, 10);
		String anno		= "";
		String orario	= "";
		
		if (giorno.equalsIgnoreCase("1 ") || giorno.equalsIgnoreCase("2 ") || giorno.equalsIgnoreCase("3 ") || giorno.equalsIgnoreCase("4 ") || giorno.equalsIgnoreCase("5 ") || giorno.equalsIgnoreCase("6 ") || giorno.equalsIgnoreCase("7 ")|| giorno.equalsIgnoreCase("8 ") || giorno.equalsIgnoreCase("9 ")) {
			giorno	= "0"+giorno;
			giorno 	= giorno.trim();
			anno 	= data.substring(28, 32);
			orario 	= data.substring(10, 18);
		}
		else {
			anno 	= data.substring(29, 33);
			orario 	= data.substring(11, 19);
		}
		
		parsedData = anno+"-"+resolveMonth(mese)+"-"+giorno+"T"+orario;
		
		return parsedData;
	}
	
	private static String resolveMonth (String mese) {
		String monthParsed = "";
		 	
	 	if (mese.equalsIgnoreCase("Jan"))
			monthParsed = "01";
	 	else if (mese.equalsIgnoreCase("Feb"))
			monthParsed = "02";
	 	else if (mese.equalsIgnoreCase("Mar"))
			monthParsed = "03";
	 	else if (mese.equalsIgnoreCase("Apr"))
			monthParsed = "04";
	 	else if (mese.equalsIgnoreCase("May"))
			monthParsed = "05";
	 	else if (mese.equalsIgnoreCase("Jun"))
			monthParsed = "06";
	 	else if (mese.equalsIgnoreCase("Jul"))
			monthParsed = "07";
	 	else if (mese.equalsIgnoreCase("Aug"))
			monthParsed = "08";
	 	else if (mese.equalsIgnoreCase("Sep"))
			monthParsed = "09";
	 	else if (mese.equalsIgnoreCase("Oct"))
			monthParsed = "10";
	 	else if (mese.equalsIgnoreCase("Nov"))
			monthParsed = "11";
	 	else if (mese.equalsIgnoreCase("Dec"))
			monthParsed = "12";
		
		return monthParsed;
	}
	
	
}
