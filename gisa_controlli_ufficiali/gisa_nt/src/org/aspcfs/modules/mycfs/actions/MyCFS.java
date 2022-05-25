/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.mycfs.actions;

import com.darkhorseventures.framework.actions.ActionContext;

import crypto.nuova.gestione.ClientSCAAesServlet;

import org.aspcf.modules.controlliufficiali.base.Norma;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.login.actions.Login;
import org.aspcfs.modules.login.beans.LoginBean;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.base.CFSNote;
import org.aspcfs.modules.mycfs.base.CFSNoteList;
import org.aspcfs.modules.mycfs.base.Mail;
import org.aspcfs.modules.mycfs.base.PostIt;
import org.aspcfs.modules.tasks.base.Task;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.modules.vigilanza.base.TicketList;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.ricercaunica.base.RicercaOpu;
import org.aspcfs.modules.suap.base.ListaRichiesteSuap;
import org.aspcfs.modules.suap.base.Richiesta;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Flags.Flag;
import javax.mail.search.FlagTerm;

/**
 * The MyCFS module.
 *
 * @author chris
 * @version $Id: MyCFS.java 24300 2007-12-09 12:11:39Z srinivasar@cybage.com $
 * @created July 3, 2001
 */
public final class MyCFS extends CFSModule {

	
	
	 public String executeCommandCFSNoteDetails(ActionContext context) {
		    if (!(hasPermission(context, "myhomepage-inbox-view"))) {
		      return ("PermissionError");
		    }
		    PagedListInfo inboxInfo = this.getPagedListInfo(context, "InboxInfo");
		    Connection db = null;
		    int myId = -1;
		    CFSNote newNote = null;
		    try {
		      int msgId = Integer.parseInt(context.getRequest().getParameter("id"));
		      db = this.getConnection(context);
		      //For a sent message myId is a user_id else its a contactId
		      if (inboxInfo.getListView().equals("sent")) {
		        myId = getUserId(context);
		      } else {
		        myId = ((UserBean) context.getSession().getAttribute("User")).getUserRecord().getContact().getId();
		      }
		      newNote = new CFSNote(db, msgId, myId, inboxInfo.getListView());
		      if (!inboxInfo.getListView().equalsIgnoreCase("sent")) {
		        /*
		         *  do not change status if its the OutBox
		         */
		        if (newNote.getStatus() == CFSNote.NEW) {
		          newNote.setStatus(CFSNote.READ);
		          newNote.updateStatus(db);
		        }
		      }
		    } catch (Exception errorMessage) {
		      context.getRequest().setAttribute("Error", errorMessage);
		      return ("SystemError");
		    } finally {
		      this.freeConnection(context, db);
		    }
		    addModuleBean(context, "MyInbox", "Inbox Details");
		    context.getRequest().setAttribute("NoteDetails", newNote);
		    return ("CFSNoteDetailsOK");
		  }
	
	
	
	  
		  
  /**
   * Description of the Method
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
    String module = context.getRequest().getParameter("module");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    addModuleBean(context, module, module);
    return ("IncludeOK");
  }
  
  public String executeCommandInbox(ActionContext context) {
		if (!(hasPermission(context, "myhomepage-inbox-view"))) {
			return ("PermissionError");
		}
		PagedListInfo inboxInfo = this.getPagedListInfo(context, "InboxInfo");
		inboxInfo.setLink("MyCFSInbox.do?command=Inbox");

		Connection db = null;
		CFSNoteList noteList = new CFSNoteList();
		SystemStatus systemStatus = this.getSystemStatus(context);
		
		try {
			db = this.getConnection(context);
			noteList.setPagedListInfo(inboxInfo);
			noteList.setSentTo(
					((UserBean) context.getSession().getAttribute("User")).getUserRecord().getContact().getId());
			noteList.setSentFrom(getUserId(context));

			if (context.getRequest().getParameter("return") != null) {
				inboxInfo.setListView("new");
			}

			if ("old".equals(inboxInfo.getListView())) {
				noteList.setOldMessagesOnly(true);
			}


			// TODO: Modify CFSNoteList so that only 1 query is always generated so
			// this doesn't have to happen...
			if ("sent".equals(inboxInfo.getListView())) {
				/*
				 *  Changing sort column as  "sent_namelast" is not a column in the query
				 */
				if ("sent_namelast".equals(inboxInfo.getColumnToSortBy())) {
					inboxInfo.setColumnToSortBy("m.sent");
				}
				noteList.setSentMessagesOnly(true);
			} else {
				/*
				 *  Changing sort column back to requested column
				 */
				if ("m.sent".equals(inboxInfo.getColumnToSortBy())) {
					inboxInfo.setColumnToSortBy("sent_namelast");
				}
			}

			noteList.buildList(db);
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("systemStatus", systemStatus);
		context.getRequest().setAttribute("CFSNoteList", noteList);
		addModuleBean(context, "MyInbox", "Inbox Home");
		return ("InboxOK");
	}

  
  
  
  public String executeCommandNewMessage(ActionContext context) {

		if (!(hasPermission(context, "myhomepage-inbox-view"))) {
			return ("PermissionError");
		}

		Connection db = null;
		try {
			db = this.getConnection(context);
			CFSNote newNote = (CFSNote) context.getFormBean();
			if (newNote == null) {
				newNote = new CFSNote();
			}
			String subject = (String) context.getRequest().getParameter("subject");
			String body = (String) context.getRequest().getParameter("body");
			String mailRecipients = (String) context.getRequest().getParameter(
					"mailrecipients");
			if (subject != null && !"".equals(subject)) {
				newNote.setSubject(subject);
			}
			if (body != null && !"".equals(body)) {
				newNote.setBody(body);
			}
			if (mailRecipients != null) {
				context.getRequest().setAttribute(
						"mailRecipients", new String("true"));
			}
			
			LookupList soggettoMessaggio = new LookupList(db, "lookup_messaggio_soggetto");
			context.getRequest().setAttribute("SoggettoMessaggio", soggettoMessaggio);

			LookupList tipoMessaggio = new LookupList(db, "lookup_messaggio_tipo");
			tipoMessaggio.setJsEvent("onChange=\"javascript:updateTipoMessaggio();\"");
			context.getRequest().setAttribute("TipoMessaggio", tipoMessaggio);
			
			LookupList tipoBug = new LookupList(db, "lookup_opportunity_budget");
			tipoBug.setJsEvent("onChange=\"javascript:updateTipoBug();\"");
			context.getRequest().setAttribute("TipoBug", tipoBug);
			
			context.getRequest().setAttribute("Note", newNote);
			context.getRequest().setAttribute("forwardType", "" + Constants.CFSNOTE);

			context.getSession().removeAttribute("selectedContacts");
			context.getSession().removeAttribute("finalContacts");
			context.getSession().removeAttribute("contactListInfo");



		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "MyInbox", "");



		return ("CFSNewMessageOK");
	}
  
  public String executeCommandListReport(ActionContext context) {
		if (!(hasPermission(context, "myhomepage-dashboard-view"))) {
			return ("PermissionError");
		}
		addModuleBean(context, "Home", "");
		if (getUserId(context) == 0) {
			return ("MaintenanceModeOK");
		}
		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
		User thisRec = thisUser.getUserRecord();

		int userId = thisUser.getContact().getId();
		Connection db = null;

		

		try
		{
			db = getConnection(context);
			int enteredBy = ((UserBean)context.getSession().getAttribute("User")).getUserId();
			LookupList TipoCampione = new LookupList(db, "lookup_tipo_controllo");
			TipoCampione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoCampione", TipoCampione);

			LookupList AuditTipo = new LookupList(db, "lookup_audit_tipo");
			AuditTipo.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("AuditTipo", AuditTipo);

			LookupList TipoAudit = new LookupList(db, "lookup_tipo_audit");
			TipoAudit.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoAudit", TipoAudit);

			LookupList TipoIspezione = new LookupList(db, "lookup_tipo_ispezione");
			TipoIspezione.addItem(-1, "-- SELEZIONA VOCE --");
			context.getRequest().setAttribute("TipoIspezione", TipoIspezione);
			TicketList cu = new TicketList();
			cu.buildListCuApertiDa(db, enteredBy,thisUser.getSiteId());
			context.getRequest().setAttribute("CUAperti", cu);

			TicketList cuSorv = new TicketList();
			cuSorv.buildListCuSorveglianzaApertiDa(db, enteredBy,thisUser.getSiteId());
			context.getRequest().setAttribute("CUSorveglianza", cuSorv);
			
			TicketList cuChiusiFup = new TicketList();
			cuChiusiFup.buildListCuChiusiWithFUp(db, enteredBy,thisUser.getSiteId());
			context.getRequest().setAttribute("CUChiusiFU", cuChiusiFup);
			
			TicketList cuChiusuraUfficio = new TicketList();
			cuChiusuraUfficio.buildListCuChiusuraUfficio(db, enteredBy,thisUser.getSiteId());
			context.getRequest().setAttribute("CuChiusuraUfficio", cuChiusuraUfficio);
			
			
			
			TicketList cuApertiASL = new TicketList();
			cuApertiASL.buildListCuApertiASL(db, thisUser.getSiteId());
			context.getRequest().setAttribute("CUApertiASL", cuApertiASL);

			TicketList cuSorvASL = new TicketList();
			cuSorvASL.buildListCuSorveglianzaApertiASL(db,thisUser.getSiteId());
			context.getRequest().setAttribute("CUSorveglianzaASL", cuSorvASL);
			
			TicketList cuChiusiFupASL = new TicketList();
			cuChiusiFupASL.buildListCuChiusiWithFUpASL(db, thisUser.getSiteId());
			context.getRequest().setAttribute("CUChiusiFUASL", cuChiusiFupASL);

			
			org.aspcfs.modules.followup.base.TicketList NCinScadenza = new org.aspcfs.modules.followup.base.TicketList();
			//NCinScadenza.buildListFollowUpInScadenza(db,enteredBy);
			NCinScadenza.buildListFollowUpInScadenza(db, enteredBy,8);
			/*while (rs.next()){
				
				 Ticket thisTicket = new Ticket(rs);
				 NCinScadenza.add(thisTicket);
			}*/
			context.getRequest().setAttribute("NCinScadenza", NCinScadenza);

		}
		catch ( Exception e )
		{
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		}
		 finally {
				this.freeConnection(context, db);
			}

		return "ListResultOK";
	}
  
  
	
  /**
   * Takes a look at the User Session Object and prepares the MyCFSBean for the
   * JSP. The bean will contain all the information that the JSP can see.
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   * @since 1.1
   */
  public String executeCommandHome(ActionContext context) {
    if (!(hasPermission(context, "myhomepage-dashboard-view"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "Home", "");
    if (getUserId(context) == 0) {
      return ("MaintenanceModeOK");
    }
    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
    User thisRec = thisUser.getUserRecord();


	int userId=thisUser.getContact().getId();
    Connection db = null;    
  
    
    String message = context.getRequest().getParameter("Message");
    if (message!=null && !"".equals(message))
    {
    	LoginBean login = new LoginBean();
    	login.setMessage(message);
    	context.getRequest().setAttribute("LoginBean", login);
    }
    
  
    try {
    	  	
	    db = this.getConnection(context);
	   
	    PreparedStatement stat = null;
		stat = db.prepareStatement( "select * from messaggi_interni_link" +
				" where sent_to='"+userId+"' and status=0" );
		ResultSet rs = stat.executeQuery();

		int comNum=0;
		while (rs.next()) {
			comNum++;
		}

		if (comNum==1) 
			context.getRequest().setAttribute("comunicazioni", "Attenzione! Ci sono messaggi non letti in 'Comunicazioni Interne'");
		else if (comNum>1) 
			context.getRequest().setAttribute("comunicazioni", "Hai" + " " + comNum + " "+" messaggi non letti in 'Comunicazioni Interne'");

		//Blocco per la gestione delle richieste.
		String contesto = (String)context.getServletContext().getAttribute("SUFFISSO_TAB_ACCESSI");
		if (contesto!=null && !contesto.equals("_ext")){
			//Aggiunta parte relativa alla SCIA
			ListaRichiesteSuap richieste = new ListaRichiesteSuap();
			int numScia = richieste.getNumeroRichiesteDaValidare(db, thisRec.getSiteId());
			
			context.getRequest().setAttribute("numScia", numScia);
			//viewName = "mostraRichiesteDaValutare";
			if(numScia > 0){
				context.getRequest().setAttribute("richieste", "Attenzione! Ci sono "+ numScia +" richieste da validare per la tua ASL");
			}else {
				context.getRequest().setAttribute("richieste", "Non ci sono richieste da validare");
			}
			
		}
				
		

    } catch (Exception errorMessage) {
	      context.getRequest().setAttribute("Error", errorMessage);
	      return "SystemError";
    } finally {
      	this.freeConnection(context, db);
    }    
    SystemStatus systemStatus = this.getSystemStatus(context);    
 
     
    return "HomeOK";
  }
  
  
  
  public String executeCommandCambioUtente(ActionContext context)
	{
		if (!(hasPermission(context, "myhomepage-profile-view"))) {
			return ("PermissionError");
		}

		return "CambioUtenteOK";
	}

	public String executeCommandListaVolture(ActionContext context)
	{
		Connection db = null;
		try
		{
			UserBean user = (UserBean)context.getRequest().getSession().getAttribute("User");
			db = getConnection(context);
			org.aspcfs.modules.cessazionevariazione.base.TicketList lista = new org.aspcfs.modules.cessazionevariazione.base.TicketList();
			lista.setSiteId(user.getSiteId());
			lista.setStatoVoltura(0);
			lista.buildListVoltureStabSoaInRichiesta(db);
			
			context.getRequest().setAttribute("VoltureInRichiesta", lista);
			
		}
		catch (SQLException e) {
			// TODO: handle exception
		}
		finally
		{
			this.freeConnection(context, db);
		}
		
		return "VoltureInRichiestaOK" ;
	}
	
	  public static byte[] encrypt(String text,URL url) throws IOException, NoSuchAlgorithmException,FileAesKeyException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		  
		  if(url ==null)
		  {
			  throw new FileAesKeyException("File aes_key not found");
		  }
		  SecretKeySpec spec = getKeySpec(url.getPath());
		  Cipher cipher = Cipher.getInstance("AES");
		  cipher.init(Cipher.ENCRYPT_MODE, spec);
		  sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
	    
	    return enc.encode(cipher.doFinal(text.getBytes())).getBytes() ;
	  }
	  
	  public static String asHex(byte buf[]) {
	        StringBuffer sb = new StringBuffer(buf.length * 2);
	        for (int i = 0; i < buf.length; i++) {
	            if (((int) buf[i] & 0xff) < 0x10) {
	                sb.append("0");
	            }

	            sb.append(Long.toString((int) buf[i] & 0xff, 16));
	        }

	        return sb.toString();
	    }

	  public static SecretKeySpec getKeySpec(String path) throws IOException, NoSuchAlgorithmException,FileAesKeyException {
		    byte[] bytes = new byte[16];
		    
		    File f = new File(path.replaceAll("%20", " "));
		    
		    SecretKeySpec spec = null;
		    if (f.exists()) 
		    {
		      new FileInputStream(f).read(bytes);
		      
		    } else {
		      /* KeyGenerator kgen = KeyGenerator.getInstance("AES");
		       kgen.init(128);
		       key = kgen.generateKey();
		       bytes = key.getEncoded();
		       new FileOutputStream(f).write(bytes);*/
		    	throw new FileAesKeyException("File aes_key not found");
		    	
		    }
		    spec = new SecretKeySpec(bytes,"AES");
		    return spec;
		  }
	  
	public String executeCommandCambioUtenteConferma(ActionContext context)
	{
		if (!(hasPermission(context, "myhomepage-profile-view"))) {
			return ("PermissionError");
		}

		 UserBean user = (UserBean)context.getSession().getAttribute("User");
		  /**COSTRUZIONE DEL TOKEN**/
		
		  String originalToken = System.currentTimeMillis() + "@"+context.getParameter("username");
		  String encryptedToken = null ;
		  try {
		
			 Login login = new Login();
			 /********************************************/
			 
			 
			 try {
				 context.getRequest().setAttribute("isCambioUtente", new Boolean(true)); 
				 /*usato oper evitare di fare la redirect (verso sistema origine) quando 
				 il logout e' fatto per cambio utente*/
				 
				 login.executeCommandLogout(context);
			 } catch (SQLException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
			 }
			
			 /*Modifica 23/03/2017 test nuova versione */
			 
			 // encryptedToken =  Login.NEWencrypt(originalToken,this.getClass().getResource("aes_key2"));
			 /********************************************/
			 /*String path = this.getClass().getResource("aes_key2").getPath().replaceAll("%20", " ");
			 FileInputStream fis = new FileInputStream(new File(path));
			 byte[] buff = new byte[1024];
			int r = fis.read(buff);
			 String key = new String(buff,0,r);*/
			  try
			  { 
				  ClientSCAAesServlet cclient = new ClientSCAAesServlet();
				  encryptedToken = cclient.crypt( originalToken);//CryptEncryptServlet.encrypt(originalToken, key);
			  }catch(Exception ex)
			  {
				  ex.printStackTrace();
			  }
			 // fis.close();
			  
			 context.getResponse().sendRedirect( "Login.do?command=LoginNoPassword&encryptedToken="+URLEncoder.encode(encryptedToken,"UTF-8"));

		  
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return "-none-";
	}
	
	
	public String executeCommandSendSpeedTest(ActionContext context) {
		int noteType = -1;
	
		boolean isValid = false;
		User thisRecord = (User) ((UserBean) context.getSession().getAttribute(
				"User")).getUserRecord();
		thisRecord.setBuildContact(true);

		try { 

				SystemStatus systemStatus = this.getSystemStatus(context);
				// if  (selectedList.size() != 0) {
			
				Mail mail = new Mail();
				
				//mail.setHost("smtp.office365.com");
				//mail.setFrom("infogisa@izsmportici.it");
				//mail.setUser("infogisa@izsmportici.it");
				//mail.setPass("Gisa_2011");
			
				mail.setHost(getPref(context, "MAILSERVER"));
				mail.setFrom(getPref(context, "EMAILADDRESS"));
				mail.setUser(getPref(context, "EMAILADDRESS"));
				mail.setPass(getPref(context, "MAILPASSWORD"));
				mail.setPort(Integer.parseInt(getPref(context, "PORTSERVER")));
				mail.setTo(getPref(context, "EMAILADDRESS"));
				mail.setSogg(" [[ SPEED TEST - GISA ]] ");
				mail.setTesto(context.getParameter("body")+"<br/>Username : "+thisRecord.getUsername()+"<br/>Cognome : "+thisRecord.getContact().getNameLast()+"<br/>Asl : "+thisRecord.getSiteIdName());
				mail.sendMail();
			
				

			
			context.getSession().removeAttribute("DepartmentList");
			context.getSession().removeAttribute("ProjectListSelect");
		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			
			context.getRequest().setAttribute("Error", errorMessage.getMessage());
			
		}  
		return "-none-";
	}
	
	public String executeCommandSendMessage(ActionContext context) {
		int noteType = -1;
		boolean recordInserted = false;
		Connection db = null;
		CFSNote thisNote = new CFSNote();
		thisNote.setEnteredBy(getUserId(context));
		thisNote.setModifiedBy(getUserId(context));
		thisNote.setReplyId(getUserId(context));
		thisNote.setType(CFSNote.CALL);
		thisNote.setTipo( context.getRequest().getParameter("tipo") );
		if (context.getRequest().getParameter("body") != null) {
			thisNote.setBody(context.getRequest().getParameter("body"));
			context.getRequest().setAttribute("body", thisNote.getBody());
		} else {
			thisNote.setBody("error");
		}
		if (context.getRequest().getParameter("actionId") != null) {
			thisNote.setActionId(context.getRequest().getParameter("actionId"));
		} 

		// preparo nuovi campi di dettaglio segnalazione
		// data
		String data = (String) context.getRequest().getParameter("problemDate");		            
		// tipo bug
		String tipoBug = (String) context.getRequest().getParameter("tipoBug");
		int tipoBugInt = -1;
		if(tipoBug != null){
			tipoBugInt = Integer.parseInt(tipoBug);
			if(tipoBug.equals("1"))
				tipoBug = "Tipo Bug: \t	TECNICO";
			else if(tipoBug.equals("2"))
				tipoBug = "Tipo Bug: \t	PROCEDURALE";
		}
		//dettagli bug tecnico
		String postazione = (String)  context.getRequest().getParameter("postazione");
		String browser = (String)  context.getRequest().getParameter("browser");
		String ripet = (String)  context.getRequest().getParameter("ripet");
		String dettagliBugTecnico="Postazione: " + postazione + "\nBrowser: " + browser + "\nRipetitibilita: " + ripet + "\n";
		// motivo segnalazione
		String motivoSegnalazione = (String)  context.getRequest().getParameter("motivo");
		if(motivoSegnalazione != null){
			if(motivoSegnalazione.equals("1"))
				motivoSegnalazione = 	"Motivo Segnalazione: \t	OPERAZIONE UTENTE ERRATA";
			else if(motivoSegnalazione.equals("2"))
				motivoSegnalazione = 	"Motivo Segnalazione: \t	INFORMAZIONI OPERATIVE";
			else if(motivoSegnalazione.equals("3"))
				motivoSegnalazione = 	"Motivo Segnalazione: \t	INFORMAZIONI PROCEDURALI";
		}

		// aggiorno il corpo del messaggio:

		String dettaglioMess = "";
		if(data != null)
			dettaglioMess = "Data riscontro problema: " + data + "\n";
		if(thisNote.getTipo() == 1) {
			dettaglioMess += motivoSegnalazione + "\n";
		} else if(thisNote.getTipo() == 2) {
			if(tipoBugInt == 2)
				dettaglioMess += tipoBug + "\n";
			else if(tipoBugInt == 1)
				dettaglioMess += tipoBug + "\n" + dettagliBugTecnico+ "\n";
		}

		thisNote.setBody(dettaglioMess + "\n" + thisNote.getBody());

		boolean isValid = false;
		User thisRecord = (User) ((UserBean) context.getSession().getAttribute(
				"User")).getUserRecord();
		thisRecord.setBuildContact(true);

		try {

			db = this.getConnection(context);

			if (context.getRequest().getParameter("subject") != null)
			{
				try
				{
					int sbj_id = Integer.parseInt( context.getRequest().getParameter("subject") );
					LookupList soggettoMessaggio = new LookupList(db, "lookup_messaggio_soggetto");
					thisNote.setSubject( soggettoMessaggio.getSelectedValue( sbj_id ) );
				}
				catch (Exception e)
				{
					thisNote.setSubject( context.getRequest().getParameter("subject") );
				}
				context.getRequest().setAttribute("subject", thisNote.getSubject());
			} else {
				thisNote.setSubject("error");
			}

			noteType = Integer.parseInt(
					(String) context.getRequest().getParameter("forwardType"));
			SystemStatus systemStatus = this.getSystemStatus(context);
			isValid = this.validateObject(context, db, thisNote);
			if (isValid) {
				// if (selectedList.size() != 0) {
				String replyAddr = (String) context.getRequest().getParameter("rispondi_a");//thisRecord.getContact().getPrimaryEmailAddress();
				int contactId = 13672;
				String email ="";
				if (ApplicationProperties.getProperty("ambiente").equalsIgnoreCase("ufficiale"))
					email= "gisahelpdesk@usmail.it";
				else
					email= "gisadev@usmail.it";
				
				// Set s = selectedList.keySet();
				// Iterator i = s.iterator();
				/*  while (i.hasNext() && isValid) {
						Integer hashKey = (Integer) i.next();
						contactId = hashKey.intValue();
						if (selectedList.get(hashKey) != null) {
							Object st = selectedList.get(hashKey);
							email = (new Contact( db, contactId )).getEmailAddress( 1 );//st.toString();
	            }*/
				/* thisContact = new Contact();
						thisContact.setId(hashKey.intValue());
						thisContact.setBuildDetails(true);
						thisContact.build(db);*/
				//thisContact.checkEnabledUserAccount(db);

				CFSNote tmpNote = new CFSNote();
				tmpNote.setEnteredBy(getUserId(context));
				tmpNote.setModifiedBy(getUserId(context));
				tmpNote.setReplyId(getUserId(context));
				tmpNote.setType(CFSNote.CALL);
				tmpNote.setSubject(thisNote.getSubject());
				tmpNote.setBody(thisNote.getBody());
				tmpNote.setActionId(thisNote.getActionId());
				tmpNote.setTipo(thisNote.getTipo());
				tmpNote.setSentTo(contactId);

				//new
				String sTipo=new String();
				if(thisNote.getTipo()==1) sTipo= "ERRORE";
				else  if(thisNote.getTipo()==2) sTipo= "SUGGERIMENTO";
				else  sTipo= "ALTRO";

				tmpNote.setSubject( sTipo + ": "+ thisNote.getSubject());


				Mail mail = new Mail();
				
				//mail.setHost("smtp.office365.com");
				//mail.setFrom("infogisa@izsmportici.it");
				//mail.setUser("infogisa@izsmportici.it");
				//mail.setPass("Gisa_2011");
			
				mail.setHost(getPref(context, "MAILSERVER"));
				mail.setFrom(getPref(context, "EMAILADDRESS"));
				mail.setUser(getPref(context, "EMAILADDRESS"));
				mail.setPass(getPref(context, "MAILPASSWORD"));
				mail.setPort(Integer.parseInt(getPref(context, "PORTSERVER")));
				mail.setRispondiA(replyAddr);

//				mail.setType("text/html");

				mail.setTo(email);
				mail.setSogg(" [GISA] "+tmpNote.getSubject());
				String message = systemStatus.getLabel(
						"mail.body.emailContactWithBody");

				String asl_rif = "";
				try
				{
			 		int site_id = thisRecord.getSiteId();
					LookupList siteIdList = new LookupList(db, "lookup_site_id");
					siteIdList.addItem( -1, "--Nessuna--");
					asl_rif = siteIdList.getSelectedValue( site_id );
				}
				catch (Exception e)
				{
					//thisNote.setSubject( context.getRequest().getParameter("subject") );
					asl_rif = "--Nessuna--";
				}

				HashMap<String, String> map = new HashMap<String, String>();
				map.put("${thisRecord.contact.nameFirstLast}", thisRecord.getUsername()+ ", corrispondente a " +
								thisRecord.getContact().getNameFirstLast() + " con Qualifica: " + thisRecord.getRole() +
								" (tel: " +  (String) context.getRequest().getParameter("numero_tel") + ") " + " dell'ASL " + asl_rif + ". Mail Di risposta : "+replyAddr );
				map.put("${thisNote.body}", StringUtils.toHtml(tmpNote.getBody()));
				Template template = new Template(message);
				template.setParseElements(map);
				System.out.println("###SENDMAIL### "+template.getParsedText());
				mail.setTesto(template.getParsedText());
				
				mail.sendMail();
				System.out.println("###SENDMAIL### INVIATA MAIL");
				// controllo se l'invio mail e' andato a buon fine
				
				
					//Message is sent. Insert the note
					recordInserted = tmpNote.insert(db,context);
					recordInserted = tmpNote.insertLink(db, true);
					this.processInsertHook(context, tmpNote);
				

			}
			context.getSession().removeAttribute("DepartmentList");
			context.getSession().removeAttribute("ProjectListSelect");
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);

			context.getRequest().setAttribute("Error", errorMessage.getMessage());
			System.out.println("###SENDMAIL### ERRORE "+errorMessage.getMessage());
			return getReturn(context, "SendMessage");
		} finally {
			this.freeConnection(context, db);
		}
		if (context.getAction().getActionName().equals(
				"ExternalContactsCallsForward")) {
			addModuleBean(context, "External Contacts", "");
		} else if (context.getAction().getActionName().equals("MyCFSInbox")) {
			addModuleBean(context, "MyInbox", "");
		} else if (context.getAction().getActionName().equals("MyTasksForward")) {
			addModuleBean(context, "My Tasks", "");
		}
		if (isValid) {
			return getReturn(context, "SendMessage");
		}
		if (noteType == Constants.CFSNOTE) {
			return executeCommandNewMessage(context);
		} else if (noteType == Constants.TASKS) {
			return executeCommandForwardMessage(context);
		} else {
			context.getRequest().setAttribute(
					"Error", new Exception("Programming Error: Please check code"));
			return "SystemError";
		}
	}

	public String executeCommandForwardMessage(ActionContext context) {
		Connection db = null;
		int myId = -1;
		CFSNote newNote = null;
		PagedListInfo inboxInfo = this.getPagedListInfo(context, "InboxInfo");
		if (!(hasPermission(context, "myhomepage-inbox-view"))) {
			return ("PermissionError");
		}
		String forward = context.getRequest().getParameter("return");
		if (forward != null && !"".equals(forward.trim())) {
			context.getRequest().setAttribute("return",forward);
		}
		context.getSession().removeAttribute("selectedContacts");
		context.getSession().removeAttribute("finalContacts");
		SystemStatus systemStatus = this.getSystemStatus(context);
		try {
			String msgId = context.getRequest().getParameter("id");
			int noteType = Integer.parseInt(
					context.getRequest().getParameter("forwardType"));
			context.getRequest().setAttribute("forwardType", "" + noteType);
			db = this.getConnection(context);
			newNote = new CFSNote();
			if (noteType == Constants.CFSNOTE) {
				// For a sent message myId is a user_id else its a contactId
				if (inboxInfo.getListView().equals("sent")) {
					myId = getUserId(context);
				} else {
					myId = ((UserBean) context.getSession().getAttribute("User")).getUserRecord().getContact().getId();
				}
				newNote = new CFSNote(
						db, Integer.parseInt(msgId), myId, inboxInfo.getListView());
				HashMap recipients = newNote.buildRecipientList(db);
				Iterator i = recipients.keySet().iterator();
				StringBuffer recipientList = new StringBuffer();
				while (i.hasNext()) {
					Object st = i.next();
					recipientList.append(st);
					if (i.hasNext()) {
						recipientList.append(",");
					}
				}
				String originalMessage = systemStatus.getLabel("mail.label.originalMessage");
				String from = systemStatus.getLabel("mail.label.from");
				String fwd = systemStatus.getLabel("mail.label.forward");
				String sent = systemStatus.getLabel("mail.label.sent");
				String to = systemStatus.getLabel("mail.label.to");
				String subject = systemStatus.getLabel("mail.label.subject.colon");
				newNote.setSubject(fwd + StringUtils.toString(newNote.getSubject()));
				newNote.setBody(
						originalMessage +
						from + StringUtils.toString(newNote.getSentName()) + "\n" +
						sent + DateUtils.getServerToUserDateTimeString(
								this.getUserTimeZone(context), DateFormat.SHORT, DateFormat.LONG, newNote.getEntered()) + "\n" +
								to + recipientList.toString() + "\n" +
								subject + StringUtils.toString(newNote.getSubject()) +
								"\n\n" +
								StringUtils.toString(newNote.getBody()) + "\n\n");
			} else if (noteType == Constants.TASKS) {
				Task thisTask = new Task(db, Integer.parseInt(msgId));
				context.getRequest().setAttribute("TaskId", msgId);
				String userName = ((UserBean) context.getSession().getAttribute(
						"User")).getUserRecord().getContact().getNameLastFirst();
				String taskDetails = systemStatus.getLabel("mail.label.taskDetails");
				String task = systemStatus.getLabel("mail.label.task");
				String from = systemStatus.getLabel("mail.label.from");
				String dueDate = systemStatus.getLabel("mail.label.dueDate");
				String relevantNotes = systemStatus.getLabel("mail.label.relevantNotes");
				newNote.setBody(
						taskDetails + "\n" +
								task + StringUtils.toString(thisTask.getDescription()) + "\n" +
								from + StringUtils.toString(userName) + "\n" +
								dueDate + (thisTask.getDueDate() != null ? DateUtils.getServerToUserDateString(
										this.getUserTimeZone(context), DateFormat.SHORT, thisTask.getDueDate()) : "-NA-") + "\n" +
										("".equals(thisTask.getNotes()) ? "" : relevantNotes + StringUtils.toString(
												thisTask.getNotes())) + "\n\n");
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		if (context.getAction().getActionName().equals("MyCFSInbox")) {
			addModuleBean(context, "My Inbox", "");
		} else if (context.getAction().getActionName().equals("LeadsCallsForward")) {
			addModuleBean(context, "View Opportunities", "Opportunity Activities");
		} else {
			addModuleBean(context, "My Tasks", "Forward Message");
		}
		context.getRequest().setAttribute("Note", newNote);
		return ("ForwardMessageOK");
	}


	/**
	 * Sends a reply to a message
	 *
	 * @param context Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandReplyToMessage(ActionContext context) {
		Connection db = null;
		CFSNote newNote = null;
		PagedListInfo inboxInfo = this.getPagedListInfo(context, "InboxInfo");
		if (!(hasPermission(context, "myhomepage-inbox-view"))) {
			return ("PermissionError");
		}
		SystemStatus systemStatus = this.getSystemStatus(context);
		String originalMessage = systemStatus.getLabel("mail.label.originalMessage");
		String from = systemStatus.getLabel("mail.label.from");
		String sent = systemStatus.getLabel("mail.label.sent");
		String to = systemStatus.getLabel("mail.label.to");
		String subject = systemStatus.getLabel("mail.label.subject.colon");
		String reply = systemStatus.getLabel("mail.label.reply");
		try {
			int noteType = Integer.parseInt(
					context.getRequest().getParameter("forwardType"));
			context.getRequest().setAttribute("forwardType", "" + Constants.CFSNOTE);
			String msgId = context.getRequest().getParameter("id");
			db = this.getConnection(context);

			int myId = -1;
			if (inboxInfo.getListView().equals("sent")) {
				myId = getUserId(context);
			} else {
				myId = ((UserBean) context.getSession().getAttribute("User")).getUserRecord().getContact().getId();
			}
			String listView = inboxInfo.getListView();
			newNote = new CFSNote(db, Integer.parseInt(msgId), myId, listView);
			HashMap recipients = newNote.buildRecipientList(db);
			Iterator i = recipients.keySet().iterator();
			StringBuffer recipientList = new StringBuffer();
			while (i.hasNext()) {
				Object st = i.next();
				recipientList.append(st);
				if (i.hasNext()) {
					recipientList.append(",");
				}
			}
			newNote.setSubject(reply + StringUtils.toString(newNote.getSubject()));
			newNote.setBody(
					originalMessage +
					from + StringUtils.toString(newNote.getSentName()) + "\n" +
					sent + DateUtils.getServerToUserDateTimeString(
							this.getUserTimeZone(context), DateFormat.SHORT, DateFormat.LONG, newNote.getEntered()) + "\n" +
							to + recipientList.toString() + "\n" +
							subject + StringUtils.toString(newNote.getSubject()) +
							"\n\n" +
							StringUtils.toString(newNote.getBody()) + "\n\n");

			//add the sender as a recipient
			User sender = this.getUser(context, newNote.getReplyId());
		      Contact recipient = systemStatus.getUser(sender.getId()).getContact(); ;//new Contact(db, sender.getContactId());

			context.getRequest().setAttribute("Recipient", recipient);

			//Add the recipient to the selectedList
			HashMap thisList = null;
			if (context.getSession().getAttribute("finalContacts") != null) {
				thisList = (HashMap) context.getSession().getAttribute(
						"finalContacts");
				thisList.clear();
			} else {
				thisList = new HashMap();
				context.getSession().setAttribute("finalContacts", thisList);
			}
			String recipientEmail = recipient.getPrimaryEmailAddress();
			thisList.put(new Integer(sender.getContactId()), recipientEmail);
			if (context.getSession().getAttribute("selectedContacts") != null) {
				HashMap tmp = (HashMap) context.getSession().getAttribute(
						"selectedContacts");
				tmp.clear();
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "MyInbox", "Message Reply");
		context.getRequest().setAttribute("Note", newNote);
		return getReturn(context, "ReplyMessage");
	}
	
	
	
	
	 public String executeCommandLegalMainInBoxNotifications(ActionContext context) {
		  
		  
		    int myId = -1;
		    CFSNote newNote = null;
		    try {
		     
		    	Properties props = System.getProperties();
		        props.put("mail.debug", "true");
		        props.put("mail.imap.host", "mbox.cert.legalmail.it");
		        props.put("mail.imap.port", "995");
		        props.put("mail.imap.user", ApplicationProperties.getProperty("username"));
		        props.put("mail.imap.password", ApplicationProperties.getProperty("password"));
		        props.put("mail.imap.timeout", "158000");
		        props.put("mail.imap.connectiontimeout", "158000");
		        props.put( "mail.imap.auth", "true" );      
		        props.put("mail.imap.starttls.enable", "true"); 
		        props.put("mail.imap.socketFactory.port", "993");
		        props.put("mail.imap.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		    	Session session = Session.getInstance(props);
		    	
		    	Store store = session.getStore("imap");
		    	session.setDebug(false);
		    	System.out.println(store.getURLName());
		    	store.connect("mbox.cert.legalmail.it", ApplicationProperties.getProperty("username"), ApplicationProperties.getProperty("password"));
		    	 
		    	 Folder inbox = store.getFolder("INBOX");	
		    	 inbox.open(Folder.READ_ONLY);
		    	 
		    	 Flags seen = new Flags(Flag.SEEN);
		    	 FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
		    	 Message messages[] = inbox.search(unseenFlagTerm);
		    	 context.getRequest().setAttribute("MessageMail", messages);
		     
		    } catch (Exception errorMessage) {
		      context.getRequest().setAttribute("Error", errorMessage);
		      return ("SystemError");
		    } 
		    addModuleBean(context, "MyInbox", "Inbox Details");
		    context.getRequest().setAttribute("NoteDetails", newNote);
		    return ("LegalMailInBoxOK");
		  }
	 
	 
	
	 public String executeCommandPostItVisualizza(ActionContext context) {
		
		 int tipo = -1;
		 
		 try { tipo = Integer.parseInt(context.getRequest().getParameter("tipo"));} catch (Exception e) {}	
		 
		 if (tipo==-1)
			 tipo = Integer.parseInt((String) context.getRequest().getAttribute("tipo"));
			 
		 PostIt postit = new PostIt();
		 Connection db = null;
			
			try {
				db = this.getConnection(context);
				postit.setTipo(tipo);
				postit.buildUltimoPostIt(db);
				context.getRequest().setAttribute("postIt", postit);
			} catch (Exception errorMessage) {
				context.getRequest().setAttribute("Error", errorMessage);
				return ("SystemError");
			} finally {
				this.freeConnection(context, db);
			}
			
			return "postIt"+postit.getTipo()+"OK";
		}
	 
	 public String executeCommandPostItModifica(ActionContext context) {
		 
		 if (!hasPermission(context, "gestionepostit_asl-add") && !hasPermission(context, "gestionepostit_regione-add") && !hasPermission(context, "gestionepostit_orsa-add")) {
				return ("PermissionError");
			}
		 
		 int tipo = Integer.parseInt(context.getRequest().getParameter("tipo"));	
		 
			Connection db = null;
			
			try {
				db = this.getConnection(context);
				PostIt postit = new PostIt();
				postit.setTipo(tipo);
				postit.buildUltimoPostIt(db);
				context.getRequest().setAttribute("postIt", postit);
			} catch (Exception errorMessage) {
				context.getRequest().setAttribute("Error", errorMessage);
				return ("SystemError");
			} finally {
				this.freeConnection(context, db);
			}
			context.getRequest().setAttribute("tipo", String.valueOf(tipo));
			return "postItModificaOK";
		}
	
	 public String executeCommandPostItAggiorna(ActionContext context) {
		 
		 if (!hasPermission(context, "gestionepostit_asl-add") && !hasPermission(context, "gestionepostit_regione-add") && !hasPermission(context, "gestionepostit_orsa-add")) {
				return ("PermissionError");
			}
		 
			Connection db = null;

			int tipo = Integer.parseInt(context.getRequest().getParameter("tipo"));	
			String messaggio = context.getRequest().getParameter("messaggio");
			
			
			try {
				db = this.getConnection(context);
				PostIt postit = new PostIt();
				postit.setMessaggio(messaggio);
				postit.setIdUtente(getUserId(context));
				postit.setTipo(tipo);
				postit.inserisci(db);
			} catch (Exception errorMessage) {
				context.getRequest().setAttribute("Error", errorMessage);
				return ("SystemError");
			} finally {
				this.freeConnection(context, db);
			}
			
			context.getRequest().setAttribute("tipo", String.valueOf(tipo));
			return executeCommandPostItVisualizza(context);
		}
	
	 public String executeCommandQualitaDatiAsl(ActionContext context) {

			if (!(hasPermission(context, "qualita_dati_asl-view"))) {
				return ("PermissionError");
			}

			Connection db = null;
			try {
				db = this.getConnection(context);
				
				LookupList siteList = new LookupList(db, "lookup_site_id");
				siteList.removeElementByCode(16);
				siteList.addItem(300, "TUTTE");
				context.getRequest().setAttribute("SiteList", siteList);
				
				String aslRichiesta = context.getRequest().getParameter("idAsl"); 
				
				ArrayList<String> listaQualita = new ArrayList<String>();
							
				UserBean user = (UserBean)context.getRequest().getSession().getAttribute("User");
				int idAsl = user.getSiteId();
				
				if (idAsl == -1 && aslRichiesta !=null)
					idAsl = Integer.parseInt(aslRichiesta);
				
				if (idAsl == -1){
					context.getRequest().setAttribute("msg", "Questo utente non appartiene a nessuna ASL.");
					return "QualitaDatiAslOK";
				}
				
				
				for (int i = 201; i<=207; i++){
					
				
					String asl = "";
					int numCuAperti = -1;
					int numCuTotali = -1;
					int numSorveglianzeSenzaChecklist = -1;
					int numSorveglianzeTotali = -1;
					int numErrataCorrigeMesePrecedente = -1;
					int numErrataCorrigeMeseMesePrecedente = -1;
					int numErrataCorrigeMeseMeseMesePrecedente = -1;
					int numErrataCorrigeArt17MesePrecedente = -1;
					int numErrataCorrigeArt17MeseMesePrecedente = -1;
					int numErrataCorrigeArt17MeseMeseMesePrecedente = -1;

					asl = siteList.getSelectedValue(i);
					
					if (i == idAsl || idAsl == 300) {
						PreparedStatement pst = null;
						ResultSet rs = null;
						
						pst = db.prepareStatement("select * from public_functions.qualita_dati_get_cu_aperti(?)");
						pst.setInt(1, i);
						rs = pst.executeQuery();
						if (rs.next())
							numCuAperti = rs.getInt(1);
						
						pst = db.prepareStatement("select * from public_functions.qualita_dati_get_cu_totali(?)");
						pst.setInt(1, i);
						rs = pst.executeQuery();
						if (rs.next())
							numCuTotali = rs.getInt(1);
						
						pst = db.prepareStatement("select * from public_functions.qualita_dati_get_cu_sorveglianza_senza_checklist(?)");
						pst.setInt(1, i);
						rs = pst.executeQuery();
						if (rs.next())
							numSorveglianzeSenzaChecklist = rs.getInt(1);
						
						pst = db.prepareStatement("select * from public_functions.qualita_dati_get_cu_sorveglianza_totali(?)");
						pst.setInt(1, i);
						rs = pst.executeQuery();
						if (rs.next())
							numSorveglianzeTotali = rs.getInt(1);
						
						pst = db.prepareStatement("select * from public_functions.qualita_dati_get_errata_corrige(?)");
						pst.setInt(1, i);
						rs = pst.executeQuery();
						if (rs.next())
							numErrataCorrigeMesePrecedente = rs.getInt(1);
						
						pst = db.prepareStatement("select * from public_functions.qualita_dati_get_errata_corrige_mese_precedente(2, ?)");
						pst.setInt(1, i);
						rs = pst.executeQuery();
						if (rs.next())
							numErrataCorrigeMeseMesePrecedente = rs.getInt(1);
						
						pst = db.prepareStatement("select * from public_functions.qualita_dati_get_errata_corrige_mese_precedente(3, ?)");
						pst.setInt(1, i);
						rs = pst.executeQuery();
						if (rs.next())
							numErrataCorrigeMeseMeseMesePrecedente = rs.getInt(1);
						
						pst = db.prepareStatement("select * from public_functions.qualita_dati_get_errata_corrige_art17(?)");
						pst.setInt(1, i);
						rs = pst.executeQuery();
						if (rs.next())
							numErrataCorrigeArt17MesePrecedente = rs.getInt(1);
						
						pst = db.prepareStatement("select * from public_functions.qualita_dati_get_errata_corrige_art17_mese_precedente(2, ?)");
						pst.setInt(1, i);
						rs = pst.executeQuery();
						if (rs.next())
							numErrataCorrigeArt17MeseMesePrecedente = rs.getInt(1);
						
						pst = db.prepareStatement("select * from public_functions.qualita_dati_get_errata_corrige_art17_mese_precedente(3, ?)");
						pst.setInt(1, i);
						rs = pst.executeQuery();
						if (rs.next())
							numErrataCorrigeArt17MeseMeseMesePrecedente = rs.getInt(1);
						
						String res = i+";;"+asl+";;"+
									 numCuAperti+";;"+numCuTotali+";;"+
									 numSorveglianzeSenzaChecklist+";;"+numSorveglianzeTotali+";;"+
									 numErrataCorrigeMesePrecedente+";;"+numErrataCorrigeMeseMesePrecedente+";;"+numErrataCorrigeMeseMeseMesePrecedente+";;"+
									 +numErrataCorrigeArt17MesePrecedente+";;"+numErrataCorrigeArt17MeseMesePrecedente+";;"+numErrataCorrigeArt17MeseMeseMesePrecedente;
						listaQualita.add(res);
						
					}		
					
				}
				context.getRequest().setAttribute("listaQualita", listaQualita); 

			} catch (Exception errorMessage) {
				context.getRequest().setAttribute("Error", errorMessage);
				return ("SystemError");
			} finally {
				this.freeConnection(context, db);
			}
			return ("QualitaDatiAslOK");
		}
	 
	 public String executeCommandQualitaDatiAslLista(ActionContext context) {

			if (!(hasPermission(context, "qualita_dati_asl-view"))) {
				return ("PermissionError");
			}

			Connection db = null;
			try {
				db = this.getConnection(context);
				
					
				int tipo = Integer.parseInt(context.getRequest().getParameter("tipo"));
				int idAsl = Integer.parseInt(context.getRequest().getParameter("idAsl"));
				
				ArrayList<String> listaElementi = new ArrayList<String>();
				
				PreparedStatement pst = null;
				ResultSet rs = null;
				
				if (tipo==1) {
					pst = db.prepareStatement("select * from public_functions.qualita_dati_get_cu_aperti_lista(?)");
					pst.setInt(1, idAsl);
					rs = pst.executeQuery();
					while (rs.next()){
						String res = rs.getInt("ticketid")+";;"+rs.getString("ragione_sociale") +";;"+rs.getString("tecnica_controllo")+";;"+rs.getString("data_controllo");
						listaElementi.add(res);
					}
				}
				else if (tipo==2) {
					pst = db.prepareStatement("select * from public_functions.qualita_dati_get_cu_sorveglianza_senza_checklist_lista(?)");
					pst.setInt(1, idAsl);
					rs = pst.executeQuery();
					while (rs.next()){
						String res = rs.getInt("ticketid")+";;"+rs.getString("ragione_sociale") +";;"+rs.getString("tecnica_controllo")+";;"+rs.getString("data_controllo");
						listaElementi.add(res);
					}
				}
				else if (tipo==3){
					pst = db.prepareStatement("select * from public_functions.qualita_dati_get_errata_corrige_lista(?)");
					pst.setInt(1, idAsl);
					rs = pst.executeQuery();
					while (rs.next()){
						String res = rs.getTimestamp("data")+";;"+rs.getString("ragione_sociale") +";;"+rs.getString("num_registrazione");
						listaElementi.add(res);
					}
				}
				else if (tipo==4){
					pst = db.prepareStatement("select * from public_functions.qualita_dati_get_errata_corrige_art17_lista(?)");
					pst.setInt(1, idAsl);
					rs = pst.executeQuery();
					while (rs.next()){
						String res = rs.getTimestamp("data_articolo")+";;"+rs.getString("ragione_sociale") +";;"+rs.getString("approval_number") +";;"+rs.getString("identificativo") +";;"+rs.getString("tipo_macellazione");
						listaElementi.add(res);
					}
				}
				
				context.getRequest().setAttribute("tipo", String.valueOf(tipo));
				context.getRequest().setAttribute("listaElementi", listaElementi);

			} catch (Exception errorMessage) {
				context.getRequest().setAttribute("Error", errorMessage);
				return ("SystemError");
			} finally {
				this.freeConnection(context, db);
			}
			return ("QualitaDatiAslListaOK");
		}
	 
	 public String executeCommandGestioneNormeSanzioni(ActionContext context) {
		    if (!(hasPermission(context, "gestione-norme-view"))) {
		      return ("PermissionError");
		    }
		   ArrayList<Norma> listaNorme = new ArrayList<Norma>();
		    Connection db = null;
		   
		    try {
		      db = this.getConnection(context);
		      
		      PreparedStatement pst = db.prepareStatement("select * from lookup_norme where trashed_date is null order by ordinamento asc"); 
			    ResultSet rs = pst.executeQuery();
			    while (rs.next()){
			    	Norma norma = new Norma(rs);
			    	listaNorme.add(norma);
			    }
			   		    
		    } catch (Exception errorMessage) {
		      context.getRequest().setAttribute("Error", errorMessage);
		      return ("SystemError");
		    } finally {
		      this.freeConnection(context, db);
		    }
		    context.getRequest().setAttribute("listaNorme", listaNorme);
		    return ("ListaNormeOK");
		  }
	 
	 public String executeCommandInsertNormeSanzioni(ActionContext context) {
		    if (!(hasPermission(context, "gestione-norme-add"))) {
		      return ("PermissionError");
		    }
		    
		    String description = context.getRequest().getParameter("description");
		    String viewDiffida = context.getRequest().getParameter("viewDiffida");
		    String ordinamento = context.getRequest().getParameter("ordinamento");
		    String codiceTariffa = context.getRequest().getParameter("codiceTariffa");
		    String competenzaUod = context.getRequest().getParameter("competenzaUod");
		    String gruppo = context.getRequest().getParameter("gruppo");

		    Connection db = null;
		   
		    try {
		      db = this.getConnection(context);
		      
		      Norma norma = new Norma();
		      norma.setDescription(description);
		      norma.setViewDiffida(viewDiffida);
		      norma.setCodiceTariffa(codiceTariffa);
		      norma.setCompetenzaUod(competenzaUod);
		      norma.setEnabled(true);
		      norma.setGruppo(gruppo);
		      norma.setOrdinamento(ordinamento+"-");
		      norma.insert(db, getUserId(context)); 
		 
		    } catch (Exception errorMessage) {
		      context.getRequest().setAttribute("Error", errorMessage);
		      return ("SystemError");
		    } finally {
		      this.freeConnection(context, db);
		    }
		    return executeCommandGestioneNormeSanzioni(context);
		  }
	 
	 public String executeCommandUpdateNormeSanzioni(ActionContext context) {
		    if (!(hasPermission(context, "gestione-norme-edit"))) {
		      return ("PermissionError");
		    }
		    
		    String code = context.getRequest().getParameter("code");
		    String description = context.getRequest().getParameter("description");
		    String viewDiffida = context.getRequest().getParameter("viewDiffida");
		    String enabled = context.getRequest().getParameter("enabled");
		    String ordinamento = context.getRequest().getParameter("ordinamento");
		    String codiceTariffa = context.getRequest().getParameter("codiceTariffa");
		    String competenzaUod = context.getRequest().getParameter("competenzaUod"); 

		    Connection db = null;
		   
		    try {
		      db = this.getConnection(context);
		      
		      Norma norma = new Norma(db, Integer.parseInt(code));
		      norma.setDescription(description);
		      norma.setViewDiffida(viewDiffida);
		      norma.setCodiceTariffa(codiceTariffa);
		      norma.setCompetenzaUod(competenzaUod);
		      norma.setEnabled(enabled);
		      norma.setOrdinamento(ordinamento);
		      norma.update(db, getUserId(context));
		 
		    } catch (Exception errorMessage) {
		      context.getRequest().setAttribute("Error", errorMessage);
		      return ("SystemError");
		    } finally {
		      this.freeConnection(context, db);
		    }
		    return executeCommandGestioneNormeSanzioni(context);
		  }
	 
	 public String executeCommandGestioneMacelli(ActionContext context) {
		    if (!(hasPermission(context, "gestione-macelli-view"))) {
		      return ("PermissionError");
		    }
		    
		    String username =  ((UserBean) context.getSession().getAttribute("User")).getUserRecord().getUsername();

		    ArrayList<RicercaOpu> macelliList = new ArrayList<RicercaOpu>();
		    Connection db = null;
		   
		    try {
		      db = this.getConnection(context);
		      PreparedStatement pst = db.prepareStatement("select * from get_macelli_associati_utente(?)");
		      pst.setString(1, username);
		      ResultSet rs = pst.executeQuery();
		      while (rs.next()){
		    	  RicercaOpu mac = new RicercaOpu(db, rs.getInt("alt_id_macello"), "alt_id");
		    	  macelliList.add(mac);
		      }
		      context.getRequest().setAttribute("macelliList", macelliList);		      
		 
		    } catch (Exception errorMessage) {
		      context.getRequest().setAttribute("Error", errorMessage);
		      return ("SystemError");
		    } finally {
		      this.freeConnection(context, db);
		    }
		    return "GestioneMacelliOK";
		  }
	 
	 public String executeCommandGestioneSchedeSupplementari(ActionContext context) { 
		    if (!(hasPermission(context, "gestione-schede-supplementari-view"))) {
		      return ("PermissionError");
		    }
		    
		    String username =  ((UserBean) context.getSession().getAttribute("User")).getUserRecord().getUsername();

		    ArrayList<RicercaOpu> anagraficheList = new ArrayList<RicercaOpu>();
		    Connection db = null;
		   
		    try {
		      db = this.getConnection(context);
		      PreparedStatement pst = db.prepareStatement("select * from get_anagrafiche_schede_associate_utente(?)");
		      pst.setString(1, username);
		      ResultSet rs = pst.executeQuery();
		      while (rs.next()){
		    	  RicercaOpu mac = new RicercaOpu(db, rs.getInt("riferimento_id"), rs.getString("riferimento_id_nome_tab"), true);
		    	  anagraficheList.add(mac);
		      }
		      context.getRequest().setAttribute("anagraficheList", anagraficheList);		      
		 
		    } catch (Exception errorMessage) {
		      context.getRequest().setAttribute("Error", errorMessage);
		      return ("SystemError");
		    } finally {
		      this.freeConnection(context, db);
		    }
		    return "GestioneSchedeSupplementariOK";
		  }
}

