/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.mail;

import it.us.web.util.properties.Application;

import javax.activation.DataSource;

public class Mailer
{

	static String from_address	= null;
	static String from_name		= null;
	static String host			= null;
	
	static
	{
		host			= Application.get( "MAIL_HOST" );
		from_address	= Application.get( "MAIL_SENDER_ADDRESS" );
		from_name		= Application.get( "MAIL_SENDER_NAME" );
	}
	

	public static void send( String destination, String message, String subject )
		throws EmailException
	{		
		try
		{
			SimpleEmail se = new SendMail();
			se.setHostName( host );
			se.setFrom( from_address, from_name );
			se.addTo( destination );
			se.setSubject( subject );
			se.setMsg( message );
			se.send();
		} 
		catch (EmailException e)
		{
			throw e;
		}
	}
	
	public static void send( String destination, String message, String subject, DataSource ds, String att_name, String att_desc )
		throws EmailException
	{
		try
		{
			MultiPartEmail mpe = new MultiPartEmail();
			mpe.setHostName( host );
			mpe.setFrom( from_address, from_name );
			mpe.addTo( destination );
			mpe.setSubject( subject );
			mpe.setMsg( message );
			mpe.attach( ds, att_name, att_desc );
			mpe.send();
		}
		catch (EmailException e)
		{
			throw e;
		}
	}

	
}
