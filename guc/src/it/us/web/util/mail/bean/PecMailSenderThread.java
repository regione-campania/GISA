/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.mail.bean;

import it.us.web.db.ApplicationProperties;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class PecMailSenderThread implements Runnable {
	
	private String oggetto;
	private String messaggio;
	private String dataModifica;
	private String mailDestinataria;
	private String  mailCcn;
      
    public PecMailSenderThread (String oggetto, String messaggio, String dataModifica, String mailDestinataria, String mailCcn) {
        this.oggetto= oggetto;
        this.messaggio= messaggio;
        this.dataModifica= dataModifica;
        this.mailDestinataria= mailDestinataria;
        this.mailCcn= mailCcn;
        
    }

    public void run() {
		
		HashMap<String,String> configs = new HashMap<String,String>();
		configs.put("mail.smtp.starttls.enable",ApplicationProperties.getProperty("mail.smtp.starttls.enable"));
		configs.put("mail.smtp.auth", ApplicationProperties.getProperty("mail.smtp.auth"));
		configs.put("mail.smtp.host", ApplicationProperties.getProperty("mail.smtp.host"));
		configs.put("mail.smtp.port", ApplicationProperties.getProperty("mail.smtp.port"));
		configs.put("mail.smtp.ssl.enable",ApplicationProperties.getProperty("mail.smtp.ssl.enable"));
		configs.put("mail.smtp.ssl.protocols", "tlsv1.2");
		configs.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		configs.put("mail.smtp.socketFactory.fallback", "false");
		
		PecMailSender sender = new PecMailSender(configs,ApplicationProperties.getProperty("username"), ApplicationProperties.getProperty("password"), dataModifica, mailDestinataria);
		sender.config = configs;
			
		//	creo cartella temporanea per salvare file xml il cui contenuto e'' estratto dal db	
		try {
			sender.sendMail(oggetto,messaggio,"gisasuap@cert.izsmportici.it", mailDestinataria, mailCcn, null);
		} catch (AddressException e) {
			e.printStackTrace();
			try {
				sender.sendMail(oggetto,messaggio,"gisasuap@cert.izsmportici.it", mailCcn, mailCcn, null);
			} catch (AddressException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MessagingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (MessagingException e) {
			e.printStackTrace();
			}
	}
}