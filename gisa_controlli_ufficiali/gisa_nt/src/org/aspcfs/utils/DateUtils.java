/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.aspcfs.modules.login.beans.UserBean;

import com.darkhorseventures.framework.actions.ActionContext;

public class DateUtils
{
	private static final String				datePattern			= "dd/MM/yyyy";
	private static final String				timestampPattern	= "yyyy-MM-dd HH:mm:ss";
	private static final SimpleDateFormat	sdf					= new SimpleDateFormat( datePattern );
	private static final SimpleDateFormat	timestampSdf		= new SimpleDateFormat( timestampPattern );
	
	 /**
	   * Takes a string and tries to convert it to a Timestamp
	   *
	   * @param tmp Description of the Parameter
	   * @return Description of the Return Value
	   */
	  public static java.sql.Timestamp parseTimestampString(String tmp) {
	    java.sql.Timestamp timestampValue = null;
	    try {
	      java.util.Date tmpDate = DateFormat.getDateTimeInstance(
	          DateFormat.SHORT, DateFormat.LONG).parse(tmp);
	      timestampValue = new java.sql.Timestamp(new java.util.Date().getTime());
	      timestampValue.setTime(tmpDate.getTime());
	      return timestampValue;
	    } catch (Exception e) {
	      try {
	        return java.sql.Timestamp.valueOf(tmp);
	      } catch (Exception e2) {
	      }
	    }
	    return null;
	  }
	  

	  public static java.sql.Timestamp parseDateStringNew(String tmp, String format) {
		    java.sql.Timestamp dateValue = null;
		    SimpleDateFormat df = null;
		    df = new SimpleDateFormat(format);
		    try {
		    	java.util.Date tmpDate = df.parse(tmp);//, new ParsePosition(0));
		      dateValue = new java.sql.Timestamp(tmpDate.getTime());//new java.util.Date().getTime());
		      //dateValue.setTime(tmpDate.getTime());
		      return dateValue;
		    } catch (Exception e) {
		      try {
		        return java.sql.Timestamp.valueOf(tmp);
		      } catch (Exception e2) {
		      }
		    }
		    return null;
		  }



	  /**
	   * Takes a string and tries to convert it to a Timestamp based on the
	   * specified formatting
	   *
	   * @param tmp    Description of the Parameter
	   * @param format Description of the Parameter
	   * @return Description of the Return Value
	   */
	  public static java.sql.Timestamp parseTimestampString(String tmp, String format) {
	    java.sql.Timestamp timestampValue = null;
	    SimpleDateFormat df = null;
	    df = new SimpleDateFormat(format);
	    try {
	      //java.util.Date tmpDate = DateFormat.getDateTimeInstance().parse(tmp);
	      java.util.Date tmpDate = df.parse(tmp, new ParsePosition(0));
	      timestampValue = new java.sql.Timestamp(new java.util.Date().getTime());
	      timestampValue.setTime(tmpDate.getTime());
	      return timestampValue;
	    } catch (Exception e) {
	      try {
	        return java.sql.Timestamp.valueOf(tmp);
	      } catch (Exception e2) {
	      }
	    }
	    return null;
	  }


	  /**
	   * Takes a string and tries to convert it to a Date based on the specified
	   * formatting
	   *
	   * @param tmp    Description of the Parameter
	   * @param format Description of the Parameter
	   * @return Description of the Return Value
	   */
	  public static java.sql.Date parseDateString(String tmp, String format) {
	    java.sql.Date dateValue = null;
	    SimpleDateFormat df = null;
	    df = new SimpleDateFormat(format);
	    try {
	      java.util.Date tmpDate = df.parse(tmp, new ParsePosition(0));
	      dateValue = new java.sql.Date(new java.util.Date().getTime());
	      dateValue.setTime(tmpDate.getTime());
	      return dateValue;
	    } catch (Exception e) {
	      try {
	        return java.sql.Date.valueOf(tmp);
	      } catch (Exception e2) {
	      }
	    }
	    return null;
	  }
	  
	  public static java.sql.Timestamp parseDate(String tmp) {
		    java.sql.Timestamp timestampValue = null;
		    try 
		    {
		    	SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy");
		    	timestampValue = new Timestamp(d.parse(tmp).getTime());
		    	
		    } catch (Exception e) {
		      try {
		        e.printStackTrace();
		      } catch (Exception e2) {
		      }
		    }
		    return timestampValue;
		  }
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
	
	/**
	   * Returns the current date of the calendar in the m/d/yyyy format as a
	   * string
	   *
	   * @param cal Description of the Parameter
	   * @return The dateString value
	   */
	  public static String getDateString(Calendar cal) {
	    return (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(
	        Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR);
	  }


	  /**
	   * Returns a directory structure based on the date supplied
	   *
	   * @param fileDate Description of the Parameter
	   * @return The datePath value
	   */
	  public static String getDatePath(java.util.Date fileDate) {
	    return getDatePath(new java.sql.Timestamp(fileDate.getTime()));
	  }


	  /**
	   * Returns a directory structure based on the timestamp supplied, used for
	   * the fileLibrary: yyyy/MMdd/
	   *
	   * @param fileDate Description of Parameter
	   * @return The DatePath value
	   */
	  public static String getDatePath(java.sql.Timestamp fileDate) {
	    if(fileDate == null){
	      return "0000" + System.getProperty("file.separator") + "0000" + System.getProperty("file.separator");
	    }
	    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy");
	    String datePathToUse1 = formatter1.format(fileDate);
	    SimpleDateFormat formatter2 = new SimpleDateFormat("MMdd");
	    String datePathToUse2 = formatter2.format(fileDate);
	    return datePathToUse1 + System.getProperty("file.separator") + datePathToUse2 + System.getProperty(
	        "file.separator");
	  }


	  /**
	   * Returns a string that is suitable for a filename based on a timestamp
	   *
	   * @return The filename value
	   */
	  public static String getFilename() {
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	    return formatter.format(new java.util.Date());
	  }


	  /**
	   * The HTML date/time selector works on 5's so round up to next 5
	   *
	   * @param millis Description of the Parameter
	   * @return Description of the Return Value
	   */
	  public static Timestamp roundUpToNextFive(long millis) {
	    Calendar cal = Calendar.getInstance();
	    while (cal.get(Calendar.MINUTE) % 5 != 0) {
	      cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + 1);
	    }
	    return new Timestamp(cal.getTimeInMillis());
	  }


	  /**
	   * Calculates the number of days between two calendar days in a manner which
	   * is independent of the Calendar type used.
	   *
	   * @param d1 The first date.
	   * @param d2 The second date.
	   * @return The number of days between the two dates. Zero is returned if
	   *         the dates are the same, one if the dates are adjacent, etc. The order
	   *         of the dates does not matter, the value returned is always >= 0. If
	   *         Calendar types of d1 and d2 are different, the result may not be
	   *         accurate.
	   */
	  public static int getDaysBetween(java.util.Calendar d1, java.util.Calendar d2) {
	    if (d1.after(d2)) {
	      // swap dates so that d1 is start and d2 is end
	      java.util.Calendar swap = d1;
	      d1 = d2;
	      d2 = swap;
	    }
	    int days = d2.get(java.util.Calendar.DAY_OF_YEAR) -
	        d1.get(java.util.Calendar.DAY_OF_YEAR);
	    int y2 = d2.get(java.util.Calendar.YEAR);
	    if (d1.get(java.util.Calendar.YEAR) != y2) {
	      d1 = (java.util.Calendar) d1.clone();
	      do {
	        days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
	        d1.add(java.util.Calendar.YEAR, 1);
	      } while (d1.get(java.util.Calendar.YEAR) != y2);
	    }
	    return days;
	  }


	  /**
	   * Accesses a valid user to return a date in the user's locale
	   *
	   * @param context
	   * @param date
	   * @return
	   */
	  public static String getDateAsString(java.sql.Timestamp date, ActionContext context) {
	    Locale locale = null;
	    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
	    if (thisUser != null) {
	      locale = thisUser.getLocale();
	    }
	    if (locale == null) {
	      locale = Locale.getDefault();
	    }
	    return getDateAsString(date, locale);
	  }


	  /**
	   * Gets the dateAsString attribute of the DateUtils class
	   *
	   * @param tmpTimestamp Description of the Parameter
	   * @param locale       Description of the Parameter
	   * @return The dateAsString value
	   */
	  public static String getDateAsString(Timestamp tmpTimestamp, Locale locale) {
	    String dateAsString = "";
	    try {
	      SimpleDateFormat formatter = (SimpleDateFormat) SimpleDateFormat.getDateInstance(
	    		DateFormat.SHORT, Locale.ITALY ); //DateFormat.SHORT, locale);
	      
	      formatter.applyPattern(get4DigitYearDateFormat(formatter.toPattern()));
	      dateAsString = formatter.format(tmpTimestamp);
	    } catch (Exception e) {
	    }
	    return dateAsString;
	  }


	  /**
	   * Returns a date format with four digit year pattern
	   *
	   * @param tmpDateFormat Description of the Parameter
	   * @return Description of the Return Value
	   */
	  public static String get4DigitYearDateFormat(String tmpDateFormat) {
	    if (tmpDateFormat.indexOf("yy") > -1 && tmpDateFormat.indexOf("yyyy") == -1) {
	      return StringUtils.replace(tmpDateFormat, "yy", "yyyy");
	    } else {
	      return tmpDateFormat;
	    }
	  }

	  public static boolean isWeekend(Calendar cal) {
	    return (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
	        cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
	  }
	  
	  /**
	   * Converte il formato di una data da "gg/mm/aaaa" a "Mese gg, aaaa"
	   * @throws ParseException 
	   * @author Gennaro
	   */
	  public static String converti( String data ) throws ParseException {
		String ret = null;
		data = data.trim();
		if( (data != null) && (data.length() > 0)  ) {
			data = data.replace("-", "/");
			SimpleDateFormat sdf1 = new SimpleDateFormat( "dd/MM/yyyy" );
			Date d;
			try {
				d = sdf1.parse( data );
			}
			catch (ParseException e) {
			   sdf1 = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" );
			   sdf1.setLenient( true );
			   d = sdf1.parse( data );
			}
			ret = sdf1.format( d );		
		}
	    return ret;
	  }
	  
	  
	  /**
	   * Converte un timestamp in una string in formato "gg/mm/aaaa"
	   * @param  timestamp
	   * @author Alberto
	   */
	  public static String timestamp2string(Timestamp timestamp) {
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String dataSting = "";
			if ( timestamp != null)
			{
				java.util.Date data =  (java.util.Date) timestamp ;
				dataSting = sdf.format(data);
			}
			return dataSting;
		}

	  
	  /**
	   * Gets the userToServerDateTime attribute of the DateUtils class
	   *
	   * @param timeZone   Description of the Parameter
	   * @param dateFormat Description of the Parameter
	   * @param timeFormat Description of the Parameter
	   * @param date       Description of the Parameter
	   * @param locale     Description of the Parameter
	   * @return The userToServerDateTime value
	   */
	  public static Timestamp getUserToServerDateTime(TimeZone timeZone, int dateFormat, int timeFormat, String date, Locale locale) {
	    try {
	    	
	    	if (date != null && ! "".equals(date))
	    	{
	    		
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	     return new Timestamp (sdf.parse(date).getTime());
	    	}	
	   
	    } catch (Exception e) {
	      if (date != null && !"".equals(date)) {
	        System.err.println("DateUtils-> Timestamp " + e);
	      }
	    }
	    return null;
	  }


	  /**
	   * Returns the converted server time based on the current calendar time and
	   * timezone of the user
	   *
	   * @param cal      Description of the Parameter
	   * @param timeZone Description of the Parameter
	   * @return The userToServerDateTime value
	   */
	  public static java.sql.Timestamp getUserToServerDateTime(Calendar cal, TimeZone timeZone) {
	    java.sql.Timestamp timestampValue = null;
	    try {
	      String date = getDateString(cal);
	      DateFormat localFormatter = DateFormat.getDateInstance(DateFormat.SHORT);
	      if (timeZone != null) {
	        localFormatter.setTimeZone(timeZone);
	      }
	      timestampValue = new java.sql.Timestamp(
	          localFormatter.parse(date).getTime());
	    } catch (Exception e) {
	      System.out.println(
	          "DateUtils-> getUserToServerDateTime Exception" + e.toString());
	    }
	    return timestampValue;
	  }


	  /**
	   * Gets the serverToUserDateString attribute of the DateUtils class
	   *
	   * @param timeZone   Description of the Parameter
	   * @param dateFormat Description of the Parameter
	   * @param date       Description of the Parameter
	   * @return The serverToUserDateString value
	   */
	  public static String getServerToUserDateString(TimeZone timeZone, int dateFormat, java.sql.Timestamp date) {
	    SimpleDateFormat formatter = null;
	    try {
	      // Used by the Calendar for storing events in an array
	      formatter = (SimpleDateFormat) SimpleDateFormat.getDateInstance(
	          dateFormat);
	      formatter.applyPattern("M/d/yyyy");
	      formatter.setTimeZone(timeZone);
	    } catch (Exception e) {
	      System.err.println("EXCEPTION: DateUtils -> Timestamp " + date);
	    }
	    return formatter.format((java.util.Date) date);
	  }


	  /**
	   * Gets the serverToUserDateTimeString attribute of the DateUtils class
	   *
	   * @param timeZone   Description of the Parameter
	   * @param dateFormat Description of the Parameter
	   * @param timeFormat Description of the Parameter
	   * @param date       Description of the Parameter
	   * @return The serverToUserDateTimeString value
	   */
	  public static String getServerToUserDateTimeString(TimeZone timeZone, int dateFormat, int timeFormat, java.sql.Timestamp date) {
	    SimpleDateFormat formatter = null;
	    try {
	      //TODO: combine the Locale when User Locale that has been implemented
	      formatter = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance(
	          dateFormat, timeFormat);
	      if (timeZone != null) {
	        formatter.setTimeZone(timeZone);
	      }
	    } catch (Exception e) {
	      System.err.println("EXCEPTION: DateUtils -> Timestamp " + date);
	    }
	    return formatter.format(date);
	  }


	
}
