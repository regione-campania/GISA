/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.richiestecontributi.base;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
 
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class GestionePEC2 {
	private boolean ok=false;

	 
	
	 
	    public static void sendEmailWithAttachments(String dir)
	            throws AddressException, MessagingException {
	        // sets SMTP server properties
	        Properties properties = new Properties();
	        properties.put("mail.smtp.host", getHostName());
	        properties.put("mail.smtp.port", Integer.parseInt(getSmtpPort()));
	        properties.put("mail.smtp.auth", "true");
	        properties.put("mail.smtp.starttls.enable", "true");
	        properties.put("mail.user", getUserName());
	        properties.put("mail.password", getPassword());
	        properties.put("mail.smtp.localhost", getHostName());
	 
	        // creates a new session with an authenticator
	        Authenticator auth = new Authenticator() {
	            public PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(getUserName(), getPassword());
	            }
	        };
	        Session session = Session.getInstance(properties, auth);
	 
	        // creates a new e-mail message
	        Message msg = new MimeMessage(session);
	 
	        msg.setFrom(new InternetAddress(getUserName()));
	        InternetAddress[] toAddresses = { new InternetAddress("g.balzano@u-s.it") };
	        msg.setRecipients(Message.RecipientType.TO, toAddresses);
	        msg.setSubject("sogg");
	        msg.setSentDate(new Date());
	 
	        // creates message part
	        MimeBodyPart messageBodyPart = new MimeBodyPart();
	        messageBodyPart.setContent("dfdsf", "text/html");
	 
	        // creates multi-part
	        Multipart multipart = new MimeMultipart();
	        multipart.addBodyPart(messageBodyPart);
	 
	        // adds attachments
//	        if (attachFiles != null && attachFiles.length > 0) {
//	            for (String filePath : attachFiles) {
	                MimeBodyPart attachPart = new MimeBodyPart();
	 
	                try {
	                    attachPart.attachFile(dir);
	                } catch (IOException ex) {
	                    ex.printStackTrace();
	                }
	 
	                multipart.addBodyPart(attachPart);
	            
	     //   }
	 
	        // sets the multi-part as e-mail's content
	        msg.setContent(multipart);
	 
	        // sends the e-mail
	        Transport.send(msg);
	 
	    }
	 
	    /**
	     * Test sending e-mail with attachments
	     */
	    public static void main(String[] args) {
	        // SMTP info
	        String host = "smtp.gmail.com";
	        String port = "587";
	        String mailFrom = "your-email-address";
	        String password = "your-email-password";
	 
	        // message info
	        String mailTo = "your-friend-email";
	        String subject = "New email with attachments";
	        String message = "I have some attachments for you.";
	 
	 
	        try {
	            sendEmailWithAttachments("C:/elenco_iscritti.csv");
	            System.out.println("Email sent.");
	        } catch (Exception ex) {
	            System.out.println("Could not send email.");
	            ex.printStackTrace();
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
