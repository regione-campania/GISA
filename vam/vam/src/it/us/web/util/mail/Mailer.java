/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.mail;

import java.util.Properties;

import it.us.web.util.properties.Application;

import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer
{

	static String from_address	= null;
	static String from_name		= null;
	static String host			= null;
	static String username		= null;
	static String password		= null;
	
	static
	{
		host			= Application.get( "MAIL_HOST" );
		from_address	= Application.get( "MAIL_SENDER_ADDRESS" );
		from_name		= Application.get( "MAIL_SENDER_NAME" );
		username		= Application.get( "MAIL_USERNAME" );
		password		= Application.get( "MAIL_PASSWORD" );
	}
	

	public static void send( String destination, String cc, String message, String subject )
		throws EmailException, AddressException, MessagingException
	{		
		//Nuovissima
		
		StringBuffer sb = new StringBuffer(message);
		Properties props = System.getProperties();
		props.put("mail.smtp.socketFactory.port", Application.get("MAIL_PORT"));
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", Application.get("MAIL_PORT"));
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.localhost", host);
		
		Session sessione = Session.getDefaultInstance(props, null);
		sessione.setDebug(true);
		sessione.setPasswordAuthentication(
				new URLName("smtp", host, Integer.parseInt(Application.get("MAIL_PORT")),
				"INBOX", 
				Application.get("MAIL_USERNAME"), 
				Application.get("MAIL_PASSWORD")),
				new PasswordAuthentication(Application.get("MAIL_USERNAME"), Application.get("MAIL_PASSWORD")));

		MimeMessage msg = new MimeMessage(sessione);
		msg.setFrom(new InternetAddress(Application.get("MAIL_SENDER_ADDRESS")));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(destination));
		msg.setSubject(subject);
		msg.setText(sb.toString(), "utf-8", "html");
		
		Transport tr = sessione.getTransport("smtp");
		tr.connect(host,Application.get("MAIL_USERNAME"),Application.get("MAIL_PASSWORD"));
		msg.saveChanges();
		tr.sendMessage(msg, msg.getAllRecipients());
		tr.close();
		
		
		
		//Vecchia versione
		
		/*try
		{
			SimpleEmail se = new SendMail();
			se.setHostName( host );
			se.setFrom( from_address, from_name );
			se.addTo( destination );
			se.setSubject( subject );
			se.setMsg( message );
			if(cc!=null)
				se.addCc(cc);
			se.setAuthentication(username, password);
			se.send();
		} 
		catch (EmailException e)
		{
			throw e;
		}*/
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
