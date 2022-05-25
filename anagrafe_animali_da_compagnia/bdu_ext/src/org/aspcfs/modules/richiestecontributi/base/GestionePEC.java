/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.richiestecontributi.base;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeBodyPart;

import java.io.IOException;
import java.util.Properties;


import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;




public class GestionePEC {
	
	private boolean ok=false;
	public GestionePEC(String dir) throws EmailException {
		// Create the email message
	  //   MultiPartEmail email = new MultiPartEmail();
	    
	    // email.setAuthenticator(new DefaultAuthenticator(getUserName(),getPassword()));
try{	     
	     Properties props = System.getProperties();

			// Setup mail server
	     props.put("mail.smtp.socketFactory.port", "587");
			props.put("mail.smtp.host", getHostName());
			props.put("mail.smtp.starttls.enable","true");
			props.put("mail.smtp.socketFactory.fallback", "true");
			props.put("mail.smtp.auth", "true");
			//props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", "587"+"");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.localhost", getHostName());
			// Get session
//			
//			Session session = Session.getDefaultInstance(props, null);
//			session.setDebug(true);
//			session.setPasswordAuthentication(new URLName("smtp", getHostName(), 587,"INBOX", getUserName(), getPassword()),
//					new PasswordAuthentication(getUserName(), getPassword()));
//			
			
			Session sessione = Session.getDefaultInstance(props, null);
			sessione.setDebug(true);
			sessione.setPasswordAuthentication(new URLName("smtp", getHostName(), 587,"INBOX", getUserName(), getPassword()),
					new PasswordAuthentication(getUserName(), getPassword()));
			
			// Define message
			MimeMessage msg = new MimeMessage(sessione);
			
			// Set the from address
			msg.setFrom(new InternetAddress(getUserName()));
			
			// Set the to address
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					getEmailUfficiale()));
	    	
			msg.setText("In allegato l'elenco dei contributi approvati");
			msg.setSubject("Contributi Sterilizzazione");
		//	msg.setSubject("Test From JSP");		
			// Set the subject
			
			// Set the text content for body
			//sb.append("This is the 1st String line.\n\n");
			//sb.append("This is the 2nd String line.\n\n");
//			sb.append("This is the 3rd String line.\n\n");
			//msg.setText(sb.toString(), "utf-8", "html");
			
			

		Multipart multi =  new MimeMultipart ();
		BodyPart textBodyPart = new javax.mail.internet.MimeBodyPart();
		textBodyPart.setText ("In allegato l'elenco dei contributi approvati");
		multi.addBodyPart (textBodyPart);
		
	//	 MimeBodyPart attachPart = new MimeBodyPart();
		 
		
             textBodyPart.setFileName(dir);
       

         multi.addBodyPart(textBodyPart);
     
//   }

 // sets the multi-part as e-mail's content

		
		
		msg.setContent (multi);
		msg . saveChanges ();
			// Send message
			Transport tr = sessione.getTransport("smtp");
		 
			tr.connect(getHostName(),getUserName(), getPassword());
			msg.saveChanges(); // don't forget this
			tr.sendMessage(msg, msg.getAllRecipients());
			tr.close();

//			// Create the email message
//		     MultiPartEmail email = new MultiPartEmail();
//		     email.setMailSession(sessione);
//	     
////   	     email.setDebug(true);
//   	     email.setAuthentication(getUserName(), getPassword());
//   	     email.setHostName(getHostName()); 
//   	     email.setFrom(getMittentePEC());
//   	     email.setSmtpPort(Integer.parseInt(getSmtpPort()));
//   	     
//   	   
//    	
//    	 email.setMsg("In allegato l'elenco dei contributi approvati");
//		 email.setSubject("Contributi Sterilizzazione");
//	     email.addTo(getEmailUfficiale());
//	     email.setTLS(true);
	   
	    // Create the attachment
//	    EmailAttachment attachment = new EmailAttachment();
//	  	attachment.setPath(dir);
//	  	attachment.setDisposition(EmailAttachment.ATTACHMENT);
//	    attachment.setDescription("pdf contributi");	
//	    
//	    // add the attachment
//	     email.attach(attachment);
	 //    email.setSSL(false);
	     // send the email
	 //    email.send();
	     setOk(true);
}catch (Exception e){
	e.printStackTrace();
}
	     
	}
	

	public void setOk (boolean x){
		this.ok= x;
		
	}
	
	public boolean getOk (){
		return ok;
		
	}
	
	public static String getUserName() {
		DOMParser parser = new DOMParser();
	    try {
	    	parser.parse(GestionePEC.class.getResource( "conf_PEC.xml" ).getPath());
	    } catch (SAXException s)
	    {
	       s.printStackTrace();
	    } catch (IOException i) {
	        i.printStackTrace();
	    }
	    
	    Document doc =  parser.getDocument();
	    Element E = doc.getDocumentElement();
	    NodeList d = E.getElementsByTagName("USERNAME"); 
    	
	    return d.item(0).getTextContent();
	}
	
	public static String getPassword() {
		DOMParser parser = new DOMParser();
	    try {
	    	parser.parse(GestionePEC.class.getResource( "conf_PEC.xml" ).getPath());
	    } catch (SAXException s)
	    {
	       s.printStackTrace();
	    } catch (IOException i) {
	        i.printStackTrace();
	    }
	    
	    Document doc =  parser.getDocument();
	    Element E = doc.getDocumentElement();
	    NodeList d = E.getElementsByTagName("PASSWORD"); 
    	
	    return d.item(0).getTextContent();
	}
	
	public static String getMittentePEC() {
		DOMParser parser = new DOMParser();
	    try {
	    	parser.parse(GestionePEC.class.getResource( "conf_PEC.xml" ).getPath());
	    } catch (SAXException s)
	    {
	       s.printStackTrace();
	    } catch (IOException i) {
	        i.printStackTrace();
	    }
	    
	    Document doc =  parser.getDocument();
	    Element E = doc.getDocumentElement();
	    NodeList d = E.getElementsByTagName("MITTENTE"); 
    	
	    return d.item(0).getTextContent();
	}
	
	public static String getSmtpPort() {
		DOMParser parser = new DOMParser();
	    try {
	    	parser.parse(GestionePEC.class.getResource( "conf_PEC.xml" ).getPath());
	    } catch (SAXException s)
	    {
	       s.printStackTrace();
	    } catch (IOException i) {
	        i.printStackTrace();
	    }
	    
	    Document doc =  parser.getDocument();
	    Element E = doc.getDocumentElement();
	    NodeList d = E.getElementsByTagName("SMTPPORT"); 
    	
	    return d.item(0).getTextContent();
	}

	public static String getHostName() {
		DOMParser parser = new DOMParser();
	    try {
	    	parser.parse(GestionePEC.class.getResource( "conf_PEC.xml" ).getPath());
	    } catch (SAXException s)
	    {
	       s.printStackTrace();
	    } catch (IOException i) {
	        i.printStackTrace();
	    }
	    
	    Document doc =  parser.getDocument();
	    Element E = doc.getDocumentElement();
	    NodeList d = E.getElementsByTagName("HOSTNAME"); 
    	
	    return d.item(0).getTextContent();
	}
	
	public static String getEmailUfficiale() {
		DOMParser parser = new DOMParser();
	    try {
	    	parser.parse(GestionePEC.class.getResource( "conf_PEC.xml" ).getPath());
	    } catch (SAXException s)
	    {
	       s.printStackTrace();
	    } catch (IOException i) {
	        i.printStackTrace();
	    }
	    
	    Document doc =  parser.getDocument();
	    Element E = doc.getDocumentElement();
	    NodeList d = E.getElementsByTagName("Ufficiale"); 
    	
	    return d.item(0).getTextContent();
	}

/*	public static void inviaPes(String userName, String password, String mittente, String SmtpPort, String hostName,
							String destinatario, String subject, String msg) 
	{
		
        try {
    		HtmlEmail pes = new HtmlEmail();
    		
    		pes.setSSL(true);
    		pes.setSslSmtpPort(SmtpPort);
    		pes.setAuthentication(userName, password);
        	pes.addTo(destinatario);
        	pes.setSubject(subject);
			pes.setFrom(mittente);
			pes.setMsg(msg);
			pes.setTLS(true);
			pes.setHostName(hostName);
			
			
			pes.send();
			
		} catch (EmailException e) {
			e.printStackTrace();
		}
		
	}
	*/
/*	public static void inviaPesAtt(String userName, String password, String mittente, String SmtpPort, String hostName,
			String destinatario, String subject, String msg,String[] pathFile,String[] pathRipristinare) 
	{

		try {
			HtmlEmail pes = new HtmlEmail();

			pes.setSSL(true);
			pes.setSslSmtpPort(SmtpPort);
			pes.setAuthentication(userName, password);
			pes.addTo(destinatario);
			pes.setSubject(subject);
			pes.setFrom(mittente);
			pes.setMsg(msg);
			pes.setTLS(true);
			pes.setHostName(hostName);

			
			for (int i = 0; i< pathFile.length; i++)
			{
				EmailAttachment attachment = new EmailAttachment();
				attachment.setPath(pathFile[i]);
				pes.attach(attachment);
			}
			pes.send();
			
			
			
			for (int i = 0; i< pathFile.length; i++)
			{
			
				File temp1 = new File(pathFile[i]);
	    		 temp1.renameTo(new File (pathRipristinare [ i ]));
				
			}
			
			 
			

			} catch (EmailException e) {
				e.printStackTrace();
			}

		}

	
	*/
	
	
}
