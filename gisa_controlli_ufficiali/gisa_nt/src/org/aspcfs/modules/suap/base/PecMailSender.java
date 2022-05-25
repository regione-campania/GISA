/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.suap.base;

import java.io.File;
import java.util.HashMap;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.aspcfs.modules.util.imports.ApplicationProperties;

public class PecMailSender {

	HashMap<String,String> config;
	String username;
	String password;
	
	
	private void addAttachment(Multipart multipart, String pathName,String fileName) throws MessagingException
	{
	    DataSource source = new FileDataSource(pathName);
	    BodyPart messageBodyPart = new MimeBodyPart();        
	    messageBodyPart.setDataHandler(new DataHandler(source));
	    messageBodyPart.setFileName(fileName);
	    multipart.addBodyPart(messageBodyPart);
	}
	
	
	public PecMailSender(HashMap<String,String> config,String username, String password) {
		this.config = config;
		this.username = username;
		this.password = password;
		
	}
	
	
	
	public static void main (String[] arg)
	{
		
		
		HashMap<String,String> configs = new HashMap<String,String>();
		configs.put("mail.smtp.starttls.enable",ApplicationProperties.getProperty("mail.smtp.starttls.enable"));
		configs.put("mail.smtp.auth", ApplicationProperties.getProperty("mail.smtp.auth"));
		configs.put("mail.smtp.host", ApplicationProperties.getProperty("mail.smtp.host"));
		configs.put("mail.smtp.port", ApplicationProperties.getProperty("mail.smtp.port"));
		configs.put("mail.smtp.ssl.enable",ApplicationProperties.getProperty("mail.smtp.ssl.enable"));
		configs.put("mail.smtp.ssl.protocols", "tlsv1.2");
		configs.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		configs.put("mail.smtp.socketFactory.fallback", "false");
		
		PecMailSender sender = new PecMailSender(configs,ApplicationProperties.getProperty("username"), ApplicationProperties.getProperty("password"));
		//	creo cartella temporanea per salvare file xml il cui contenuto e' estratto dal db	
		try {
			sender.sendMail("Test da Locale","Invio Test "+
					"Test","gisasuap@cert.izsmportici.it", "gisadev@u-s.it", null);
		} catch (AddressException e) {

			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
			}
		
	}
	
	
	public void sendMail(String oggetto,String msg,String fromAddr,String toAddr,File allegato) throws MessagingException 
	{
		
		
		System.out.println("invio mail a "+toAddr);
		try
		{
		Properties props = new Properties();
		for(String kConf : config.keySet())
		{
			props.put(kConf,config.get(kConf));
		}
		
		Session sess = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		 });
		
		sess.setDebug(false);
		
		
		MimeMessage mimeMsg = new MimeMessage(sess);
		
		mimeMsg.setFrom(new InternetAddress(fromAddr));
		mimeMsg.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddr));
		
		
		mimeMsg.setSubject(oggetto);
		StringBuffer sb = new StringBuffer(msg);
		mimeMsg.setText(sb.toString(),"utf-8","html");
		
		if (allegato!=null)
		{
		Multipart multiPart = new MimeMultipart();
		addAttachment(multiPart,allegato.getAbsolutePath(),allegato.getName());
		mimeMsg.setContent(multiPart);
		}
		Transport.send(mimeMsg);
//		Transport tr = sess.getTransport("smtp");
//		tr.connect();
//		mimeMsg.saveChanges();
//		tr.sendMessage(mimeMsg, mimeMsg.getAllRecipients());
//		tr.close();
		}
		catch(AddressException e)
		{
			throw e;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		
	
	}
	

	
	public void sendMail(String oggetto,String msg,String fromAddr,String toAddr[],File allegato) throws AddressException, MessagingException
	{
		Properties props = new Properties();
		for(String kConf : config.keySet())
		{
			props.put(kConf,config.get(kConf));
		}
		
		Session sess = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		 });
		
		sess.setDebug(false);
		
		
		MimeMessage mimeMsg = new MimeMessage(sess);
		
		mimeMsg.setFrom(new InternetAddress(fromAddr));
		
		InternetAddress[] arrayIndr = new InternetAddress[toAddr.length];
		
		for (int i = 0 ; i < toAddr.length; i ++)
		{
			arrayIndr[i]=new InternetAddress(toAddr[i]);
		}
		mimeMsg.addRecipients(Message.RecipientType.TO, arrayIndr);
		
		
		mimeMsg.setSubject(oggetto);
		StringBuffer sb = new StringBuffer(msg);
		mimeMsg.setText(sb.toString(),"utf-8","html");
		
		if (allegato!=null)
		{
		Multipart multiPart = new MimeMultipart();
		addAttachment(multiPart,allegato.getAbsolutePath(),allegato.getName());
		mimeMsg.setContent(multiPart);
		}
		Transport.send(mimeMsg);

		
		
	}
	
	public void sendMailConAllegato(String oggetto,String msg,String fromAddr,String toAddr[],File allegato) throws AddressException, MessagingException
	{
		Properties props = new Properties();
		for(String kConf : config.keySet())
		{
			props.put(kConf,config.get(kConf));
		}
		
		Session sess = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		 });
		
		sess.setDebug(false);
		
		
		MimeMessage mimeMsg = new MimeMessage(sess);
		Multipart multipart = new MimeMultipart();
		
		mimeMsg.setFrom(new InternetAddress(fromAddr));
		
		InternetAddress[] arrayIndr = new InternetAddress[toAddr.length];
		
		for (int i = 0 ; i < toAddr.length; i ++)
		{
			arrayIndr[i]=new InternetAddress(toAddr[i]);
		}
		mimeMsg.addRecipients(Message.RecipientType.TO, arrayIndr);
		
		
		mimeMsg.setSubject(oggetto);
		
		StringBuffer sb = new StringBuffer(msg);
		
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(sb.toString(), "text/html");
		multipart.addBodyPart(messageBodyPart); 
			
		
		if (allegato!=null)
		{
			MimeBodyPart attachPart = new MimeBodyPart();
			String attachFile = allegato.getAbsolutePath();
			DataSource source = new FileDataSource(attachFile);
			attachPart.setDataHandler(new DataHandler(source));
			attachPart.setFileName(new File(attachFile).getName());
			multipart.addBodyPart(attachPart);
		}
		mimeMsg.setContent(multipart);
		Transport.send(mimeMsg);
	}
	
	
	
//	public static void main(String[] args) throws AddressException, MessagingException
//	{
//		HashMap<String,String> config = new HashMap<String,String>();
//
//		config.put("mail.smtp.starttls.enable","true");
//		config.put("mail.smtp.auth", "true");
//		config.put("mail.smtp.host", "sendm.cert.legalmail.it");
//		config.put("mail.smtp.port", "25");
//		config.put("mail.smtp.ssl.enable","true");
//		
//		PecMailSender sender = new PecMailSender(config, "M3023707", "US9560031");
//		
////		String tmpFolderPath = getWebInfPath(context,"tmp_attachment_pecmail");
////		File tmpFold = new File(tmpFolderPath);
////		tmpFold.mkdir();
////		File tmpXmlFile = new File(tmpFolderPath+"tempFile.txt");
////		tmpXmlFile.createNewFile();
////		
////		
////		sender.sendMail("CIAO", "gisasuap@cert.izsmportici.it", "molotv@gmail.com", new File[]{tmpXmlFile});
//	}
	
}



