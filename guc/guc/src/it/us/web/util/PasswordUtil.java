/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util;

import it.us.web.bean.BUtente;
import it.us.web.exceptions.PasswordException;
import it.us.web.util.properties.Message;

import java.util.Random;

public class PasswordUtil {
	
	public static void checkPassword( String password, BUtente user )
		throws PasswordException
	{
		String problem = null;
		
		try
		{
			pwdCtrl( password, user.getUsername() );
		}
		catch (PasswordException e)
		{
			problem = e.getMessage();
		}
		
		if( problem != null )
		{
			throw new PasswordException( problem );
		}
		
	}
	
	public static void checkPassword( String password, String username )
		throws PasswordException
	{
		String problem = null;
		
		try
		{
			pwdCtrl( password, username );
		}
		catch (PasswordException e)
		{
			problem = e.getMessage();
		}
		
		if( problem != null )
		{
			throw new PasswordException( problem );
		}
		
	}
	
	private static void pwdCtrl( String password, String username )
		throws PasswordException
	{
		String problem = null;
		
		if( password == null ){
			problem = ( (problem != null) ? (problem) : ("") )
					+ Message.getSmart( "password_nulla" ) + "\n"; // - La password non puo' essere nulla\n";
		}
		
		if( password.length() < 8 )
		{	
			problem = ( (problem != null) ? (problem) : ("") )
					+ Message.getSmart( "password_minore_8_caratteri" ) + "\n"; // " - Numeri di caratteri della password minore di 8\n";
		}
		
		if( username == null )
		{
			problem = ( (problem != null) ? (problem) : ("") )
					+ Message.getSmart( "user_id_nulla" ) + "\n"; // " - Errore: user id nulla\n";
		}
		
		if( !containsNumber(password) )
		{
			problem = ( (problem != null) ? (problem) : ("") )
					+ Message.getSmart( "password_senza_cifre" ) + "\n"; // " - La password deve contenere almeno un numero\n";
		}
		
		if( !containsAlphabetic(password) )
		{
			problem = ( (problem != null) ? (problem) : ("") )
					+ Message.getSmart( "password_senza_lettere" ) + "\n"; // " - La password deve contenere almeno una lettera dell'alfabeto\n";
		}
		
		if( !containsOtherChar(password) )
		{
			problem = ( (problem != null) ? (problem) : ("") )
						+ Message.getSmart( "password_senza_caratteri_speciali" ) + "\n"; // " - La password deve contenere almeno un carattere speciale\n";
		}
		
		if ( password.equalsIgnoreCase( username ) )
		{
			problem = ( (problem != null) ? (problem) : ("") )
					+ Message.getSmart( "password_uguale_username" ) + "\n"; // " - La password e l'username non possono essere uguali\n";
		}
		
		if ( containsSpace(password) )
		{
			problem = ( (problem != null) ? (problem) : ("") )
					+ Message.getSmart( "password_con_caratteri_spaziatura" ) + "\n"; // " - La password non puo' avere caratteri di spaziatura\n";
		}
		
		if ( containsNChar( password, 4 ) )
		{
			problem = ( (problem != null) ? (problem) : ("") )
					+ Message.getSmart( "password_con_piu_3_caratteri_uguali_consecutivi" ) + "\n"; // " - La password non puo' avere piu' di 3 caratteri uguali consecutivi\n";
		}
		
		if( problem != null )
		{
			throw new PasswordException( problem );
		}
	}

	private static boolean containsSpace(String password)
	{
		boolean ret = true;
		if( password.matches(".*\\s.*") )
		{
			ret = true;
		}
		else
		{
			ret = false;
		}
		
		return ret;	
	}

	private static boolean containsAlphabetic(String password) 
	{		
		boolean ret = true;
		if( password.matches(".*[a-zA-Z].*") )
		{
			ret = true;
		}
		else
		{
			ret = false;
		}
		
		return ret;	
	}

	private static boolean containsNumber(String password) 
	{	
		boolean ret = true;
		if( password.matches(".*\\d.*") )
		{
			ret = true;
		}
		else
		{
			ret = false;
		}
		return ret;
	}
	
	private static boolean containsOtherChar(String password) 
	{
	
		boolean ret = true;
		if( password.matches(".*[^0-9a-zA-Z].*") )
		{
			ret = true;
		}
		else
		{
			ret = false;
		}
		
		return ret;
	}
	
	private static boolean containsNChar( String password, int n ) 
	{
		return countLongestSequence( password ) >= n;
	}

	public static int countLongestSequence( String password )
	{
		boolean stop = false;
		int ret = 0;
		int count = 0;
		byte[] b = new byte[0];
		byte temp = -1;
		
		if( (password == null) || (password.length() < 1) )
		{
			stop = true;
			ret = 0;
		}
		else
		{
			b = password.getBytes();
			temp = b[0];
			count = 1;
			ret = 1;
		}		
		
		for( int i = 1; (i < b.length) && !stop; i++ )
		{
			if( b[i] == temp )
			{
				count++;
				ret = (count > ret) ? (count) : (ret);
			}
			else
			{
				temp = b[i];
				count = 1;
			}
		}
		
		return ret;
	}
	
	public static String random()
	{
		boolean okNum = false;
		boolean okLetter = false;
		boolean okSpecial = false;
		
		long seed = (long)( System.currentTimeMillis() * 0.134532987987987987153269845478 );
		
		Random r = new Random( seed );
		
		float offset = r.nextFloat();
		int size = 8 + (int)( offset * 8 );
		
		byte[] buff = new byte[ size ];
		
		for( int i = 0; i < size; i++ )
		{
			int type = (int) ( r.nextFloat() * 10 );
			if( i >= size - 3 )
			{
				if( !okLetter )
				{
					type = 0;
				}
				else if( !okNum )
				{
					type = 4;
				}
				else if( !okSpecial )
				{
					type = 5;
				}
			}
			
			int from = 0;
			int to = 0;
						
			switch ( type ) {
			case 0: //lettere minuscole
			case 1:
			case 9:
				from = 97;
				to = 122;
				okLetter = true;
				break;
			case 2: //lettere maiuscole
			case 3:
			case 8:
				from = 65;
				to = 90;
				okLetter = true;
				break;
			case 4: //numeri
				from = 48;
				to = 57;
				okNum = true;
				break;
			case 5: //caratteri speciali
				from = 33;
				to = 47;
				okSpecial = true;
				break;
			case 6: //caratteri speciali (2)
				from = 91;
				to = 95;
				okSpecial = true;
				break;
			case 7:
			case 10:
				from = 58;
				to = 63;
				okSpecial = true;
			default:
				break;
			}
			
			buff [ i ] = (byte)Math.round( from + ( r.nextFloat() * ( to - from ) ) );
		}
		return new String(buff) ;

	}
	
	public static String random(BUtente user){
	
		String ret = null;
		boolean ok = false;
		
		while( !ok )
		{
			ret = random();
			try
			{
				checkPassword( ret, user );
				ok = true;
			}
			catch (PasswordException e)
			{
				e.printStackTrace();
				ok = false;
			}
		}
		
		return ret;
	
	}
	
	public static void main(String[] args)
	{
		System.out.println( random() );
	}

}
