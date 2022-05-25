/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.allerte.base;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

import org.aspcfs.modules.allerte.base.Ticket;
import org.apache.xerces.parsers.DOMParser;
import org.apache.xerces.dom.DocumentImpl;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.utils.AuditReport;
import org.aspcfs.utils.SMTPMessage;
import org.w3c.dom.*;
import org.w3c.dom.ranges.*;
import org.xml.sax.SAXException;

import com.daffodilwoods.daffodildb.server.backup.CopyFile;
import com.darkhorseventures.framework.actions.ActionContext;

import com.isavvix.tools.HttpMultiPartParser;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;


public class GestionePEC{

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
	    GestionePEC p = new GestionePEC();
	}
	
	public GestionePEC() {
	   
	}
	
	private static boolean isSian = false ;
	
	
	
	public boolean isSian() {
		return isSian;
	}

	public void setSian(boolean isSian) {
		this.isSian = isSian;
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
	
	
	public static String getMail_regione() {
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
	    NodeList d = null ;
	    if (isSian == true)
	    	 d = E.getElementsByTagName("REGIONE-S"); 
	    else
	    	 d = E.getElementsByTagName("REGIONE"); 
    	
	    return d.item(0).getTextContent();
	}
	
	public static String getMittentePEC_Asl(String asl_tag,boolean issian) {
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
	    NodeList d = null ;
	    isSian = issian ;
	    if (isSian == true)
	    	 d = E.getElementsByTagName(asl_tag+"-S"); 
	    else
	    	 d = E.getElementsByTagName(asl_tag); 
    	
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
	
	public static String getEmailRegione() {
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
	    NodeList d = E.getElementsByTagName("REGIONE"); 
    
	    return d.item(0).getTextContent();
	}
	
	public static String getEmailSalernoExSA3() {
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
	    NodeList d = null ;
	    if (isSian==true)
	    	 d = E.getElementsByTagName("SA3-S");
	    else
	    	 d = E.getElementsByTagName("SA3");
    	
	    return d.item(0).getTextContent();
	}
	
	public static String getEmailSalernoExSA2() {
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
	    NodeList d = null ;
	    if (isSian==true)
	    	 d = E.getElementsByTagName("SA2-S");
	    else
	    	 d = E.getElementsByTagName("SA2");
	  
    	
	    return d.item(0).getTextContent();
	}
	
	public static String getEmailSalernoExSA1() {
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
	    NodeList d = null ;
	    if (isSian==true)
	    	 d = E.getElementsByTagName("SA1-S");
	    else
	    	 d = E.getElementsByTagName("SA1");
    	
	    return d.item(0).getTextContent();
	}
	
	public static String getEmailNapoli3SudExNA5() {
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
	    NodeList d = null ;
	    if (isSian==true)
	    	 d = E.getElementsByTagName("NA5-S");
	    else
	    	 d = E.getElementsByTagName("NA5");
    	
	    return d.item(0).getTextContent();
	}
	
	public static String getEmailNapoli3SudExNA4() {
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
	    NodeList d = null ;
	    if (isSian==true)
	    	 d = E.getElementsByTagName("NA4-S");
	    else
	    	 d = E.getElementsByTagName("NA4");
    	
	    return d.item(0).getTextContent();
	}
	
	public static String getEmailNapoli2NordExNA3() {
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
	    NodeList d = null ;
	    if (isSian==true)
	    	 d = E.getElementsByTagName("NA3-S");
	    else
	    	 d = E.getElementsByTagName("NA3");
    	
	    return d.item(0).getTextContent();
	}
	
	public static String getEmailNapoli2NordExNA2() {
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
	    NodeList d = null ;
	    if (isSian==true)
	    	 d = E.getElementsByTagName("NA2-S");
	    else
	    	 d = E.getElementsByTagName("NA2");
    	
	    return d.item(0).getTextContent();
	}
	
	public static String getEmailNapoli1Centro() {
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
	    NodeList d = null ;
	    if (isSian==true)
	    	 d = E.getElementsByTagName("NA1-S");
	    else
	    	 d = E.getElementsByTagName("NA1");
    	
	    return d.item(0).getTextContent();
	}
	
	public static String getEmailCasertaExCE2() {
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
	    NodeList d = null ;
	    if (isSian==true)
	    	 d = E.getElementsByTagName("CE2-S");
	    else
	    	 d = E.getElementsByTagName("CE2");
    	
	    return d.item(0).getTextContent();
	}
	
	public static String getEmailCasertaExCE1() {
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
	    NodeList d = null ;
	    if (isSian==true)
	    	 d = E.getElementsByTagName("CE1-S");
	    else
	    	 d = E.getElementsByTagName("CE1");
    	
	    return d.item(0).getTextContent();
	}
	
	public static String getEmailBenevento() {
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
	    NodeList d = null ;
	    if (isSian==true)
	    	 d = E.getElementsByTagName("BN1-S");
	    else
	    	 d = E.getElementsByTagName("BN1");
    	
	    return d.item(0).getTextContent();
	}
	
	public static String getEmailAvellinoExAV2() {
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
	    NodeList d = null ;
	    if (isSian==true)
	    	 d = E.getElementsByTagName("AV2-S");
	    else
	    	 d = E.getElementsByTagName("AV2");
    	
	    return d.item(0).getTextContent();
	}
	
	public static String getEmailAvellinoExAV1() {
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
	    NodeList d = null ;
	    if (isSian==true)
	    	 d = E.getElementsByTagName("AV1-S");
	    else
	    	 d = E.getElementsByTagName("AV1");
    
	    return d.item(0).getTextContent();
	}
	
	public static String getEmailExAV2() {
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
	    NodeList d = E.getElementsByTagName("AV2"); 
    	
	    return d.item(0).getTextContent();
	}
	
	
	public static void inviaPes(String userName, String password, String mittente, String SmtpPort, String hostName,
							String destinatario, String subject, String msg) throws EmailException
	{	
		
     //   try {
        	
			HtmlEmail pes = new HtmlEmail();
		
			pes.setSSL(false);
    		pes.setSslSmtpPort(SmtpPort);
    		pes.setAuthentication(userName, password);
        	
        	pes.setSubject(subject);
			pes.setFrom(mittente);
			pes.setMsg(msg);
			pes.setTLS(true);
			pes.setHostName(hostName);

			
			String[] destinatari = destinatario.split(";");
			for (int i = 0 ; i <destinatari.length;i++)
			{
				pes.addTo(destinatari[i]);
				
				
			}
			pes.send();
		/*} catch (EmailException e) {
			e.printStackTrace();
		}*/
		
	}
	
	
	public static void inviaPes() throws EmailException
	{	
		

		String userName = "agc20.sett02@pec.regione.campania.it";
		String password = "zbzAXmhB";

		String mittente = "agc20.sett02@pec.regione.campania.it";
		String SmtpPort = "465";
		String hostName = "smtp.pec.actalis.it";

		String destinatario = "g.balzano@u-s.it", subject = "Prova", msg = "Prova invio mail da applicativo";

			 try {
		    	
				HtmlEmail pes = new HtmlEmail();
				
				pes.setSSL(false);
		    		pes.setSslSmtpPort(SmtpPort);
		    		pes.setAuthentication(userName, password);
		        	
		        	pes.setSubject(subject);
					pes.setFrom(mittente);
					pes.setMsg(msg);
					pes.setTLS(true);
					pes.setHostName(hostName);
					pes.addTo(destinatario );				
					pes.send();
				
				
				

				
			} catch (EmailException e) {
				e.printStackTrace();
				e.printStackTrace();
			}

	}
	
	
	
	public static void inviaPesAtt(String userName, String password, String mittente, String SmtpPort, String hostName,
			String destinatario, String subject, String msg,String[] pathFile,String[] pathRipristinare,ActionContext context,int idCu) 
	{

		try {
			String	dir		= context.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "reports"+File.separator+"template"+File.separator;
			
			String[] destinatari = destinatario.split(";");
		
			HtmlEmail pes = new HtmlEmail();
			
			pes.setSSL(false);
	    		pes.setSslSmtpPort(SmtpPort);
	    		pes.setAuthentication(userName, password);
	        	
	        	pes.setSubject(subject);
				pes.setFrom(mittente);
				pes.setMsg(msg);
				pes.setTLS(true);
				pes.setHostName(hostName);

			
			if (pathFile != null)
			{
			EmailAttachment attachment1 = new EmailAttachment();
			attachment1.setPath(dir+"Mancato Richiamo "+idCu+".pdf");
			pes.attach(attachment1);
			
			for (int i = 0; i< pathFile.length; i++)
			{
				EmailAttachment attachment = new EmailAttachment();
				attachment.setPath(pathFile[i]);
				pes.attach(attachment);
			}
			}
			
			for (String a : destinatari)
			{
				pes.addTo(a);
				
			}
			
			pes.send();
			
			/**
			 * RIPORTO I NOMI DEI FILE COME ERANO PRIMA 
			 */
			if (pathFile != null)
			{
			for (int i = 0; i< pathFile.length; i++)
			{
			
				File temp1 = new File(pathFile[i]);
	    		 temp1.renameTo(new File (pathRipristinare [ i ]));
				
			}
			File temp1 = new File(dir+"Mancato Richiamo "+idCu+".pdf");
			temp1.delete();
			}
			 
			

			} catch (EmailException e) {
				e.printStackTrace();
			}

		}

	@SuppressWarnings("deprecation")
	public static void preparaSpedisciPECripianificazioneallerta(String asl, Ticket allerta,AslCoinvolte ac , int cuRegione , boolean flag_sian ) throws EmailException {
		
		String userName = GestionePEC.getUserName();
		String password = GestionePEC.getPassword();
		String mittente = GestionePEC.getMittentePEC();
		String SmtpPort = GestionePEC.getSmtpPort();
		String hostName = GestionePEC.getHostName();
		
		String destinatario = "", subject = "", msg = "";
		isSian = flag_sian;
		
		
		msg 	= "Segnalazione Ripianificazione controlli ufficiali allerta : "+allerta.getIdAllerta();
		msg 	+= "\n\nControlli iniziali pianificati dalla regione : "+ac.getControlliUfficialiRegionaliPianificati();
		msg 	+= "\nControlli Ripianificati dalla regione "+cuRegione ;
		msg 	+= "L'allerta e' stata riaperta";
		
		if ("201".equalsIgnoreCase(asl))	{
			destinatario=GestionePEC.getEmailAvellinoExAV1() + ";" + GestionePEC.getEmailAvellinoExAV2();
			subject = "(Avellino)Ripianificazione controlli ufficiali per Allerta con Identificativo: " + allerta.getIdAllerta();
		
		}
		
		if ("202".equalsIgnoreCase(asl))	{
			destinatario=GestionePEC.getEmailBenevento();
			subject = "(Benevento)Ripianificazione controlli ufficiali per Allerta Allerta con Identificativo: " + allerta.getIdAllerta();
		}
		if ("203".equalsIgnoreCase(asl))	{
			destinatario=GestionePEC.getEmailCasertaExCE1() + ";" + GestionePEC.getEmailCasertaExCE2();
			subject = "(Caserta)Ripianificazione controlli ufficiali per Allerta Allerta con Identificativo: " + allerta.getIdAllerta();
		}
		
		if ("204".equalsIgnoreCase(asl))	{
			destinatario=GestionePEC.getEmailNapoli1Centro();
			subject = "(Napoli1Centro)Ripianificazione controlli ufficiali per Allerta Allerta con Identificativo: " + allerta.getIdAllerta();
		}
		if ("205".equalsIgnoreCase(asl))	{
			destinatario=GestionePEC.getEmailNapoli2NordExNA2() + ";" + GestionePEC.getEmailNapoli2NordExNA3();
			subject = "(Napoli2Nord)Ripianificazione controlli ufficiali per Allerta Allerta con Identificativo: " + allerta.getIdAllerta();
		}
		
		if ("206".equalsIgnoreCase(asl))	{
			destinatario=GestionePEC.getEmailNapoli3SudExNA4() + ";" +GestionePEC.getEmailNapoli3SudExNA5();
			subject = "(Napoli3Sud)Ripianificazione controlli ufficiali per Allerta Allerta con Identificativo: " + allerta.getIdAllerta();
		}
		
		if ("207".equalsIgnoreCase(asl))	{
			destinatario=GestionePEC.getEmailSalernoExSA1() + ";" + GestionePEC.getEmailSalernoExSA2() + ";" + GestionePEC.getEmailSalernoExSA3();
			subject = "(SalernoRipianificazione controlli ufficiali per Allerta Allerta con Identificativo: " + allerta.getIdAllerta();
		}
		
		
		if (! "".equals(subject))		
			inviaPes(userName, password, mittente, SmtpPort, hostName, destinatario, subject, msg);
			//inviaPes();
	}
	
	
	
	@SuppressWarnings("deprecation")
//	public static void preparaSpedisciPECallerta(String asl, Ticket allerta,boolean flag ) throws EmailException {
//		
//		isSian = flag;
//		String userName = GestionePEC.getUserName();
//		String password = GestionePEC.getPassword();
//		String mittente = GestionePEC.getMittentePEC();
//		String SmtpPort = GestionePEC.getSmtpPort();
//		String hostName = GestionePEC.getHostName();
//		
//		String destinatario = "", subject = "", msg = "";
//		
//		
//		
//		msg 	= "Segnalazione inserimento allerta \n\nInserimento effettuato in data : " + allerta.getDataApertura().toLocaleString()  + "\nDescrizione Breve: " + allerta.getDescrizioneBreve() + "\nIdentificativo : " + allerta.getIdAllerta();
//		
//		
//		if ("201".equalsIgnoreCase(asl))	{
//			destinatario=GestionePEC.getEmailAvellinoExAV1() + ";" + GestionePEC.getEmailAvellinoExAV2();
//			subject = "(Avellino) Segnalazione Allerta con Identificativo: " + allerta.getIdAllerta();
//		
//		}
//		
//		if ("202".equalsIgnoreCase(asl))	{
//			destinatario=GestionePEC.getEmailBenevento();
//			subject = "(Benevento Segnalazione Allerta con Identificativo: " + allerta.getIdAllerta();
//		}
//		if ("203".equalsIgnoreCase(asl))	{
//			destinatario=GestionePEC.getEmailCasertaExCE1() + ";" + GestionePEC.getEmailCasertaExCE2();
//			subject = "(Caserta)Segnalazione Allerta con Identificativo: " + allerta.getIdAllerta();
//		}
//		
//		if ("204".equalsIgnoreCase(asl))	{
//			destinatario=GestionePEC.getEmailNapoli1Centro();
//			subject = "(Napoli1Centro)Segnalazione Allerta con Identificativo: " + allerta.getIdAllerta();
//		}
//		if ("205".equalsIgnoreCase(asl))	{
//			destinatario=GestionePEC.getEmailNapoli2NordExNA2() + ";" + GestionePEC.getEmailNapoli2NordExNA3();
//			subject = "(Napoli2Nord)Segnalazione Allerta con Identificativo: " + allerta.getIdAllerta();
//		}
//		
//		if ("206".equalsIgnoreCase(asl))	{
//			destinatario=GestionePEC.getEmailNapoli3SudExNA4() + ";" +GestionePEC.getEmailNapoli3SudExNA5();
//			subject = "(Napoli3Sud)Segnalazione Allerta con Identificativo: " + allerta.getIdAllerta();
//		}
//		
//		if ("207".equalsIgnoreCase(asl))	{
//			destinatario=GestionePEC.getEmailSalernoExSA1() + ";" + GestionePEC.getEmailSalernoExSA2() + ";" + GestionePEC.getEmailSalernoExSA3();
//			subject = "(Salerno) Segnalazione Allerta con Identificativo: " + allerta.getIdAllerta();
//		}
//		
//		
//		
//		if (! "".equals(subject))
//			inviaPes(userName, password, mittente, SmtpPort, hostName, destinatario, subject, msg);
//		
//	}
	
	
public static void preparaSpedisciPECcu(String asl, org.aspcfs.modules.vigilanza.base.Ticket cu,String[] pathFile,String[] pathRipristinare,ActionContext context) {
		
		String userName = GestionePEC.getUserName();
		String password = GestionePEC.getPassword();
		String mittente = GestionePEC.getMittentePEC();
		String SmtpPort = GestionePEC.getSmtpPort();
		String hostName = GestionePEC.getHostName();
		
		String destinatario = "", subject = "", msg = "";

		boolean flag_sian = false ;
		if (cu.getCodiceAllerta().contains("S"))
			flag_sian = true ;
		GestionePEC.isSian = flag_sian ; 
		String tipo_procedura = "" ;
		if (cu.getProceduraRichiamo() == 0 )
		{
			tipo_procedura = "Attivate" ;
		}
		else
		{
			if (cu.getProceduraRichiamo() == 1 )
			{
				tipo_procedura = "Non Attivate ma non necessarie" ;
			}
			
		}
		
		if(pathFile != null)
		{
			msg 	= "Segnalazione inserimento Controllo ufficiale in ispezione - sistema allarme rapido per allerta con codice :"+cu.getCodiceAllerta()+". \n Proceduradi richiamo : non attiva ma  necessaria. \n\nInserimento effettuato in data : "+cu.getEntered();
		}
		else
		{
			msg 	= "Rettifica Controllo ufficiale in ispezione - sistema allarme rapido per allerta con codice :"+cu.getCodiceAllerta()+". \n Proceduradi richiamo : "+tipo_procedura+". \n\nInserimento effettuato in data : "+cu.getEntered();

		}
		destinatario=GestionePEC.getEmailAvellinoExAV1();
		subject = "(AvellinoExAV1)Segnalazione Inserimento CU con Identificativo: " + cu.getIdControlloUfficiale();

		
		
		if ("201".equalsIgnoreCase(asl))	{
			destinatario=GestionePEC.getEmailAvellinoExAV1() + ";" + GestionePEC.getEmailAvellinoExAV2();
			subject = "(Avellino) Segnalazione Inserimento CU con Identificativo: " + cu.getIdControlloUfficiale();
		
		}
		
		if ("202".equalsIgnoreCase(asl))	{
			destinatario=GestionePEC.getEmailBenevento();
			subject = "(Benevento Segnalazione Inserimento CU con Identificativo: " + cu.getIdControlloUfficiale();
		}
		if ("203".equalsIgnoreCase(asl))	{
			destinatario=GestionePEC.getEmailCasertaExCE1() + ";" + GestionePEC.getEmailCasertaExCE2();
			subject = "(Caserta)Segnalazione Inserimento CU con Identificativo: " + cu.getIdControlloUfficiale();
		}
		
		if ("204".equalsIgnoreCase(asl))	{
			destinatario=GestionePEC.getEmailNapoli1Centro();
			subject = "(Napoli1Centro)Segnalazione Inserimento CU con Identificativo: " + cu.getIdControlloUfficiale();
		}
		if ("205".equalsIgnoreCase(asl))	{
			destinatario=GestionePEC.getEmailNapoli2NordExNA2() + ";" + GestionePEC.getEmailNapoli2NordExNA3();
			subject = "(Napoli2Nord)Segnalazione Inserimento CU con Identificativo: " + cu.getIdControlloUfficiale();
		}
		
		if ("206".equalsIgnoreCase(asl))	{
			destinatario=GestionePEC.getEmailNapoli3SudExNA4() + ";" +GestionePEC.getEmailNapoli3SudExNA5();
			subject = "(Napoli3Sud)Segnalazione Inserimento CU con Identificativo: " + cu.getIdControlloUfficiale();
		}
		
		if ("207".equalsIgnoreCase(asl))	{
			destinatario=GestionePEC.getEmailSalernoExSA1() + ";" + GestionePEC.getEmailSalernoExSA2() + ";" + GestionePEC.getEmailSalernoExSA3();
			subject = "(Salerno) Segnalazione Inserimento CU con Identificativo: " + cu.getIdControlloUfficiale();
		}
		
		
		
		
		
		
		if (! "".equals(subject))
			inviaPesAtt(userName, password, mittente, SmtpPort, hostName, destinatario, subject, msg, pathFile,pathRipristinare,context,cu.getId());
	}

	
public static void preparaSpedisciPEC_allerta(Connection db,String descrAsl,String asl, org.aspcfs.modules.allerte.base.Ticket allerta,ActionContext context,String motivo_chiusura,boolean flag_sian) throws IOException, NumberFormatException, EmailException {
	
	String userName = GestionePEC.getUserName();
	String password = GestionePEC.getPassword();
	String mittente = GestionePEC.getMittentePEC();
	String SmtpPort = GestionePEC.getSmtpPort();
	String hostName = GestionePEC.getHostName();
	
	String destinatario = "", subject = "", msg = "";

	
	
	msg 	= "Segnalazione Chiusura Allerta per Asl :"+descrAsl + "\n Identificativo Allerta : "+allerta.getIdAllerta()+". \n";
	if (!"".equals(motivo_chiusura) && motivo_chiusura!=null)
	{
		msg+="Motivo Chiusura : "+motivo_chiusura;
	}
	boolean isSian = false ;
	if (allerta.getIdAllerta().contains("S"))
	{
		isSian = true ;
	}
	GestionePEC.isSian = isSian;
	
	destinatario=GestionePEC.getMail_regione() ;
	subject = "(AvellinoExAV1)Segnalazione Allerta con Identificativo: " + allerta.getIdAllerta();

	
	if ("201".equalsIgnoreCase(asl))	{
		//destinatario=GestionePEC.getEmailAvellinoExAV1() + ";" + GestionePEC.getEmailAvellinoExAV2();
		subject = "(Avellino) Segnalazione Chiusura Allerta con Identificativo: " + allerta.getIdAllerta();
	
	}
	
	if ("202".equalsIgnoreCase(asl))	{
		//destinatario=GestionePEC.getEmailBenevento();
		subject = "(Benevento Segnalazione Chiusura Allerta con Identificativo: " + allerta.getIdAllerta();
	}
	if ("203".equalsIgnoreCase(asl))	{
		//destinatario=GestionePEC.getEmailCasertaExCE1() + ";" + GestionePEC.getEmailCasertaExCE2();
		subject = "(Caserta)Segnalazione Chiusura Allerta con Identificativo: " + allerta.getIdAllerta();
	}
	
	if ("204".equalsIgnoreCase(asl))	{
		//destinatario=GestionePEC.getEmailNapoli1Centro();
		subject = "(Napoli1Centro)Segnalazione Chiusura Allerta con Identificativo: " + allerta.getIdAllerta();
	}
	if ("205".equalsIgnoreCase(asl))	{
		//destinatario=GestionePEC.getEmailNapoli2NordExNA2() + ";" + GestionePEC.getEmailNapoli2NordExNA3();
		subject = "(Napoli2Nord)Segnalazione Chiusura Allerta con Identificativo: " + allerta.getIdAllerta();
	}
	
	if ("206".equalsIgnoreCase(asl))	{
		//destinatario=GestionePEC.getEmailNapoli3SudExNA4() + ";" +GestionePEC.getEmailNapoli3SudExNA5();
		subject = "(Napoli3Sud)Segnalazione Chiusura Allerta con Identificativo: " + allerta.getIdAllerta();
	}
	
	if ("207".equalsIgnoreCase(asl))	{
		//destinatario=GestionePEC.getEmailSalernoExSA1() + ";" + GestionePEC.getEmailSalernoExSA2() + ";" + GestionePEC.getEmailSalernoExSA3();
		subject = "(Salerno) Segnalazione Chiusura Allerta con Identificativo: " + allerta.getIdAllerta();
	}
	
	
	
	//mittente = GestionePEC.getMittentePEC();
	if (! "".equals(subject))
		inviaPesAtt1(db,Integer.parseInt(asl),userName, password, mittente, SmtpPort, hostName, destinatario, subject, msg,context,allerta.getId(),allerta.getIdAllerta(),allerta.getDataApertura(),allerta);
}

public static void inviaPesAtt1(Connection db,int asl,String userName, String password, String mittente, String SmtpPort, String hostName,
		String destinatario, String subject, String msg,ActionContext context,int idCu,String idAllerta,Timestamp dataApertura,Ticket allerta) throws IOException, EmailException 
{

	
		UserBean user = (UserBean)context.getSession().getAttribute("User");
	
		AslCoinvolte	ac3	= allerta.getAslCoinvolta( user.getSiteId() );
		int cuEseguiti = 0;
		if (ac3 !=null )
		{
			cuEseguiti = ac3.getCu_eseguiti();
		}
		
	
		
		String	dir		= context.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "reports"+File.separator+"template"+File.separator;
		
		String[] destinatari = destinatario.split(";");
	
		HtmlEmail pes = new HtmlEmail();
		
			pes.setSSL(false);
    		pes.setSslSmtpPort(SmtpPort);
    		pes.setAuthentication(userName, password);
        	
        	pes.setSubject(subject);
			pes.setFrom(mittente);
			pes.setMsg(msg);
			pes.setTLS(true);
			pes.setHostName(hostName);

		String dirFile = "";
		/*if(cuEseguiti>0 || ac3 == null)
		{*/
		 dirFile = generaAllegatoFallerta(db,asl,allerta,context);
		
		
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(dirFile);
		pes.attach(attachment);
		//}
		for (String a : destinatari)
		{
			pes.addTo(a);
			
		}
		
		pes.send();
		if(cuEseguiti>0 || ac3 == null)
		{
			File f = new File(dirFile);
			if (f.exists())
			{
				f.delete();
			}
		}
		
		
		//flag = (new File(dir_tmp)).delete();
		/**
		 * RIPORTO I NOMI DEI FILE COME ERANO PRIMA 
		 */
		
		
		


	}

private static String  generaAllegatoFallerta(Connection db,int idAsl ,Ticket allerta, ActionContext context)
{
	try
	{
		
		String tipo = "" ;
		AllegatoF allegato = new AllegatoF();
		String	dirFilAllegatoF		= context.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "template_report"+File.separator+"AllegatoF.pdf";
		File f = new File (dirFilAllegatoF);
		FileOutputStream fos = new FileOutputStream(f);
		String tipo_alimenti = context.getRequest().getParameter("tipoAlimenti");
		String specie_alimenti = context.getRequest().getParameter("specie_alimenti");
		tipo = specie_alimenti+": "+tipo_alimenti;
		if (specie_alimenti==null )
		{
			tipo = allerta.getNoteAlimenti();
		     
		      
		}
		
		allegato.generate(db,fos,idAsl,allerta,tipo,context);
		fos.flush();
		

		 return dirFilAllegatoF;

	}
	catch(Exception e )
	{
		e.printStackTrace();
	}
	return "";
	


}
/*
public static void inviaPesAtt1(String userName, String password, String mittente, String SmtpPort, String hostName,
		String destinatario, String subject, String msg,ArrayList<String> pathFile,ArrayList<String> pathRipristinare,ArrayList<String> nomiFile,ActionContext context,int idCu) throws IOException 
{

	try {
		String	dir		= context.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "reports"+File.separator+"template"+File.separator;
		
		String[] destinatari = destinatario.split(";");
	
		HtmlEmail pes = new HtmlEmail();
		pes.setSSL(true);
		pes.setSslSmtpPort(SmtpPort);
		pes.setAuthentication(userName, password);
		pes.setSubject(subject);
		pes.setFrom(mittente);
		pes.setMsg(msg);
		pes.setTLS(true);
		pes.setHostName(hostName);
		String dir_tmp=context.getServletContext().getRealPath("/") + "WEB-INF"+ File.separator + "zip_tmp/";
		boolean flag = (new File(dir_tmp)).mkdir();
		
		ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(dir_tmp+"AllegatoF.zip")));
		
		byte[] data = new byte[1000]; 
		for (int i = 0; i< pathFile.size(); i++)
		{
			
		    int count;
		    
		    
		    BufferedInputStream in = new BufferedInputStream(new FileInputStream(pathFile.get(i)));
		    
		    out.putNextEntry(new ZipEntry(nomiFile.get(i)));
		    while((count = in.read(data,0,1000)) != -1)
		    { 
		    	out.write(data, 0, count);
		    }
		    in.close();
		   
		    //fileTocopy.renameTo(new File ())
		}
		out.flush();
		out.close();
		
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(dir_tmp+"AllegatoF.zip");
		pes.attach(attachment);
		
		for (String a : destinatari)
		{
			pes.addTo(a);
			
		}
		
		pes.send();
		//flag = (new File(dir_tmp)).delete();
		/**
		 * RIPORTO I NOMI DEI FILE COME ERANO PRIMA 
		 */
		
		/*for (int i = 0; i< pathFile.size(); i++)
		{
		
			File temp1 = new File(pathFile.get(i));
    		temp1.renameTo(new File (pathRipristinare.get(i)));
			
		}
		flag = (new File(dir_tmp)).delete();
		
	} catch (EmailException e) {
			e.printStackTrace();
		}

	}
*/


}