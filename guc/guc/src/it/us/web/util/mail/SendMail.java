/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.mail;

import java.util.Collection;

import it.us.web.util.properties.Application;

public class SendMail extends SimpleEmail implements Runnable
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
	
	public SendMail()
	{
		return;
	}

	public void send( String destination, String message, String subject )
	{		
		try
		{
			setHostName( host );
			setFrom( from_address, from_name );			
			this.run();
			addTo( destination );
			setSubject( subject );
			setMsg( message );
			send();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void send( String destination, String bcc, String message, String subject )
	{		
		try
		{
			setHostName( host );
			setFrom( from_address, from_name );			
			this.run();
			addTo( destination );
			setSubject( subject );
			setMsg( message );
			setBcc(bcc);
			send();
		} 
		catch (Exception e) {
			e.printStackTrace();
		    send(bcc, bcc, message, subject);

		}
	}
	
	public void run() {
		
	}

}
