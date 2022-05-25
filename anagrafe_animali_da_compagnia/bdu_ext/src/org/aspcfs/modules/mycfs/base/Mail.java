/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.mycfs.base;

import javax.mail.*;
import java.util.*;
import javax.mail.internet.*;


public class Mail {
	//String host = getPref(context, "MAILSERVER"); // tuo smtp
	//String from = getPref(context, "EMAILADDRESS"); // tuo indirizzo email
	//String ToAddress = "destinatario@email.com"; // destinatario
	//String user = "xyzabgd";
	//String pass = "password";
	
	String host = "";
	String user = "";
	String pass = "";
	String from = "";
	String to = "";
	String testo = "";
	String sogg = "";
	String rispondiA = "";
	int port ;
	
	
	
	

	public int getPort() {
		return port;
	}




	public void setPort(int port) {
		this.port = port;
	}




	public String getHost() {
		return host;
	}




	public void setHost(String host) {
		this.host = host;
	}




	public String getUser() {
		return user;
	}




	public void setUser(String user) {
		this.user = user;
	}




	public String getPass() {
		return pass;
	}




	public void setPass(String pass) {
		this.pass = pass;
	}




	public String getFrom() {
		return from;
	}




	public void setFrom(String from) {
		this.from = from;
	}




	public String getTo() {
		return to;
	}




	public void setTo(String to) {
		this.to = to;
	}




	public String getTesto() {
		return testo;
	}




	public void setTesto(String testo) {
		this.testo = testo;
	}




	public String getSogg() {
		return sogg;
	}




	public void setSogg(String sogg) {
		this.sogg = sogg;
	}
	
	
	

public String getRispondiA() {
		return rispondiA;
	}




	public void setRispondiA(String rispondiA) {
		this.rispondiA = rispondiA;
	}




public Mail(){}

	public int sendMail() {
		
		int result = 1;
		try {
			// initialize the StringBuffer object within the try/catch loop
			StringBuffer sb = new StringBuffer(testo);

			// Get system properties
			Properties props = System.getProperties();

			// Setup mail server
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.starttls.enable","true");
			props.put("mail.smtp.socketFactory.fallback", "true");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port+"");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.localhost", host);
			// Get session
			
			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(true);
			session.setPasswordAuthentication(new URLName("smtp", host, port,"INBOX", user, pass),
					new PasswordAuthentication(user, pass));
		
			
			// Define message
			MimeMessage msg = new MimeMessage(session);
			
			// Set the from address
			msg.setFrom(new InternetAddress(from));
			
			// Set the to address
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));
			// Set the subject
			msg.setSubject(sogg);
			// Set the text content for body
			//sb.append("This is the 1st String line.\n\n");
			//sb.append("This is the 2nd String line.\n\n");
		//	sb.append("This is the 3rd String line.\n\n");
			msg.setText(sb.toString(), "utf-8", "html");
			
			
			InternetAddress[] replyToAddress = new
			InternetAddress[1];
			
			replyToAddress[0] = new InternetAddress(rispondiA);
			msg.setReplyTo(replyToAddress);
			// Send message
			Transport tr = session.getTransport("smtp");
		
			tr.connect(host,user, pass);
			msg.saveChanges(); // don't forget this
			tr.sendMessage(msg, msg.getAllRecipients());
			tr.close();
		} catch (MessagingException e) {
			System.out.println(e);
			result = -1;
		}finally{
			return result;
		}
		
	
	}


}