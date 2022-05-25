/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.ricerca_microchip.base;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils
{
	private static final String				datePattern			= "dd/MM/yyyy";
	private static final String				timestampPattern	= "yyyy-MM-dd HH:mm:ss";
	private static final SimpleDateFormat	sdf					= new SimpleDateFormat( datePattern );
	private static final SimpleDateFormat	timestampSdf		= new SimpleDateFormat( timestampPattern );
	
	
	public static Timestamp parseTimestamp( String timestamp )
	{
		Timestamp ret = null;
		try
		{
			if( (timestamp != null) && (timestamp.trim().length() > 0) )
			{
				ret = new Timestamp ( timestampSdf.parse( timestamp.trim() ).getTime() );
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
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
			e.printStackTrace();
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
			if( (data != null) && (data.trim().length() > 0) )
			{
				ret = sdf.parse( data.trim() );
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
		}
		
		return ret;
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
	
}
