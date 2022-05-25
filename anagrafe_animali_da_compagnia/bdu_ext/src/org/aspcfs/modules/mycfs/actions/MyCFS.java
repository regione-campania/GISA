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

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationHistory;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.anagrafe_animali.base.Animale;
import org.aspcfs.modules.anagrafe_animali.base.AnimaleList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.log.base.LogBean;
import org.aspcfs.modules.login.actions.Login;
import org.aspcfs.modules.login.beans.LoginBean;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.mycfs.base.AlertType;
import org.aspcfs.modules.mycfs.base.CFSNote;
import org.aspcfs.modules.mycfs.base.CFSNoteList;
import org.aspcfs.modules.mycfs.base.CalendarEventList;
import org.aspcfs.modules.mycfs.base.Mail;
import org.aspcfs.modules.mycfs.beans.CalendarBean;
import org.aspcfs.modules.opu.base.Operatore;
import org.aspcfs.modules.opu.base.RegistrazioniOperatoreList;
import org.aspcfs.modules.opu.base.Stabilimento;
import org.aspcfs.modules.registrazioniAnimali.base.EventoCessione;
import org.aspcfs.modules.registrazioniAnimali.base.EventoList;
import org.aspcfs.modules.tasks.base.Task;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

 
/**
 * The MyCFS module.
 *
 * @author chris
 * @version $Id: MyCFS.java 24300 2007-12-09 12:11:39Z srinivasar@cybage.com $
 * @created July 3, 2001
 */
public final class MyCFS extends CFSModule {



	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandDefault(ActionContext context) {
		String module = context.getRequest().getParameter("module");
		String includePage = context.getRequest().getParameter("include");
		context.getRequest().setAttribute("IncludePage", includePage);
		addModuleBean(context, module, module);
		return ("IncludeOK");
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
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
			
			if (getUserRole(context) != Integer.parseInt(ApplicationProperties.getProperty("ID_RUOLO_HD1")) && 
					getUserRole(context) != Integer.parseInt(ApplicationProperties.getProperty("ID_RUOLO_HD2")) 
					&& getUserRole(context) != Integer.parseInt(ApplicationProperties.getProperty("ID_RUOLO_AMMINISTRATORE")) ){
				noteList.setSentTo(
		              ((UserBean) context.getSession().getAttribute("User")).getUserRecord().getContact().getId());
			}else{
				noteList.setSentTo
			              (12004);
			}
		          noteList.setSentFrom(getUserId(context));
			noteList.setPagedListInfo(inboxInfo);
		//	noteList.setSentTo(getUserId(context));
		//	noteList.setSentFrom(getUserId(context));
			
		//	noteList.setSentFrom(getUserId(context));

			if (context.getRequest().getParameter("return") != null) {
				inboxInfo.setListView("new");
			}

			if ("old".equals(inboxInfo.getListView())) {
				noteList.setOldMessagesOnly(true);
			}

			if ("sent".equals(inboxInfo.getListView())) {
				if ("sent_namelast".equals(inboxInfo.getColumnToSortBy())) {
					inboxInfo.setColumnToSortBy("m.sent");
				}
				noteList.setSentMessagesOnly(true);
			} else {
				if ("m.sent".equals(inboxInfo.getColumnToSortBy())) {
					inboxInfo.setColumnToSortBy("sent_namelast");
				}
			}

			noteList.buildList(db);
			buildFormElements(context, db);
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

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandDeleteHeadline(ActionContext context) {
		if (!(hasPermission(context, "myhomepage-miner-delete"))) {
			return ("PermissionError");
		}
		Enumeration parameters = context.getRequest().getParameterNames();
		int orgId = -1;
		Connection db = null;
		try {
			db = this.getConnection(context);
			while (parameters.hasMoreElements()) {
				String param = (String) parameters.nextElement();

				if (context.getRequest().getParameter(param).equals("on")) {
					orgId = Integer.parseInt(param);
					Organization newOrg = new Organization();
					newOrg.setOrgId(orgId);
					newOrg.deleteMinerOnly(db);
					if (System.getProperty("DEBUG") != null) {
						System.out.println("MyCFS-> " + param + ": "
								+ context.getRequest().getParameter(param)
								+ "<br>");
					}
				}
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "", "Headline Delete OK");
		return ("DeleteOK");
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandCFSNoteDelete(ActionContext context) {
		if (!(hasPermission(context, "myhomepage-inbox-view"))) {
			return ("PermissionError");
		}
		Connection db = null;
		int myId = -1;
		PagedListInfo inboxInfo = this.getPagedListInfo(context, "InboxInfo");
		try {
			db = this.getConnection(context);
			int noteId = Integer.parseInt(context.getRequest().getParameter(
					"id"));
			/*
			 * For a sent message myId is a user_id else its a contactId
			 */
			if (inboxInfo.getListView().equals("sent")) {
				myId = getUserId(context);
			} else {
				myId = ((UserBean) context.getSession().getAttribute("User"))
						.getUserRecord().getContact().getId();
			}
			CFSNote newNote = new CFSNote(db, noteId, myId, inboxInfo
					.getListView());
			if (System.getProperty("DEBUG") != null) {
				System.out.println("\nMYCFS view mode"
						+ context.getRequest().getParameter("listView"));
			}
			newNote.setCurrentView(inboxInfo.getListView());
			newNote.delete(db, myId);
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "MyInbox", "Inbox Home");
		return (executeCommandInbox(context));
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandCFSNoteTrash(ActionContext context) {
		if (!(hasPermission(context, "myhomepage-inbox-view"))) {
			return ("PermissionError");
		}
		String returnPage = context.getRequest().getParameter("return");
		PagedListInfo inboxInfo = this.getPagedListInfo(context, "InboxInfo");
		Connection db = null;
		int myId = -1;
		try {
			db = this.getConnection(context);
			int noteId = Integer.parseInt(context.getRequest().getParameter(
					"id"));
			// For a sent message myId is a user_id else its a contactId
			if (inboxInfo.getListView().equals("sent")) {
				myId = getUserId(context);
			} else {
				myId = ((UserBean) context.getSession().getAttribute("User"))
						.getUserRecord().getContact().getId();
			}
			CFSNote newNote = new CFSNote(db, noteId, myId, inboxInfo
					.getListView());

			if (System.getProperty("DEBUG") != null) {
				System.out.println("MyCFS-> Status before: "
						+ newNote.getStatus());
			}
			if (newNote.getStatus() == 2) {
				newNote.setStatus(CFSNote.READ);
			} else {
				newNote.setStatus(CFSNote.OLD);
			}

			newNote.updateStatus(db);
			if (System.getProperty("DEBUG") != null) {
				System.out.println("MyCFS-> Status after: "
						+ newNote.getStatus());
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "MyInbox", "Inbox Home");
		return (executeCommandInbox(context));
	}

	/**
	 * Takes a look at the User Session Object and prepares the MyCFSBean for
	 * the JSP. The bean will contain all the information that the JSP can see.
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 * @since 1.1
	 */
	public String executeCommandHeadline(ActionContext context) {
		if (!hasPermission(context, "myhomepage-miner-view")) {
			return ("PermissionError");
		}
		addModuleBean(context, "Customize Headlines", "Customize Headlines");
		PagedListInfo orgListInfo = this.getPagedListInfo(context,
				"HeadlineListInfo");
		orgListInfo.setLink("MyCFS.do?command=Headline");
		Connection db = null;
		OrganizationList organizationList = new OrganizationList();
		try {
			db = this.getConnection(context);
			organizationList.setPagedListInfo(orgListInfo);
			organizationList.setMinerOnly(true);
			organizationList.setEnteredBy(getUserId(context));
			organizationList.buildList(db);
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("OrgList", organizationList);
		return ("HeadlineOK");
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandInsertHeadline(ActionContext context) {
		if (!(hasPermission(context, "myhomepage-miner-add"))) {
			return ("PermissionError");
		}
		addModuleBean(context, "Customize Headlines", "");
		boolean existsAlready = false;
		Connection db = null;
		String name = context.getRequest().getParameter("name");
		String sym = context.getRequest().getParameter("stockSymbol");
		Organization newOrg = (Organization) new Organization();
		newOrg.setName(name);
		newOrg.setTicker(sym);
		newOrg.setIndustry("1");
		newOrg.setEnteredBy(getUserId(context));
		newOrg.setModifiedBy(getUserId(context));
		newOrg.setOwner(-1);
		newOrg.setMinerOnly(true);
		try {
			db = this.getConnection(context);
			existsAlready = newOrg.checkIfExists(db, name);
			newOrg.insert(db);
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("OrgDetails", newOrg);
		return (executeCommandHeadline(context));
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandCFSNoteDetails(ActionContext context) {
		if (!(hasPermission(context, "myhomepage-inbox-view"))) {
			return ("PermissionError");
		}
		PagedListInfo inboxInfo = this.getPagedListInfo(context, "InboxInfo");
		Connection db = null;
		int myId = -1;
		CFSNote newNote = null;
		try {
			int msgId = Integer.parseInt(context.getRequest()
					.getParameter("id"));
			db = this.getConnection(context);
			// For a sent message myId is a user_id else its a contactId
			if (inboxInfo.getListView().equals("sent")) {
				myId = getUserId(context);
			} else {
				myId = ((UserBean) context.getSession().getAttribute("User"))
						.getUserRecord().getContact().getId();
			}
			
			if (getUserRole(context) != Integer.parseInt(ApplicationProperties.getProperty("ID_RUOLO_HD1")) && 
					getUserRole(context) != Integer.parseInt(ApplicationProperties.getProperty("ID_RUOLO_HD2")) 
					&& getUserRole(context) != Integer.parseInt(ApplicationProperties.getProperty("ID_RUOLO_AMMINISTRATORE")) ){
				newNote = new CFSNote(db, msgId, myId, inboxInfo.getListView());
			}else{
				newNote = new CFSNote(db, msgId, 12004, inboxInfo.getListView());
			}
			if (!inboxInfo.getListView().equalsIgnoreCase("sent")) {
				/*
				 * do not change status if its the OutBox
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
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
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
			String subject = (String) context.getRequest().getParameter(
					"subject");
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
				context.getRequest().setAttribute("mailRecipients",
						new String("true"));
			}
			context.getRequest().setAttribute("Note", newNote);
			context.getRequest().setAttribute("forwardType",
					"" + Constants.CFSNOTE);

			context.getSession().removeAttribute("selectedContacts");
			context.getSession().removeAttribute("finalContacts");
			context.getSession().removeAttribute("contactListInfo");

			buildFormElements(context, db);

			// (new ContactsList()).executeCommandContactList( context );

		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		addModuleBean(context, "MyInbox", "");

		return ("CFSNewMessageOK");
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */

	public String executeCommandSendMessage(ActionContext context) {
		int noteType = -1;
		boolean recordInserted = false;
		Connection db = null;
		boolean savecopy = false;
		boolean copyrecipients = false;
		HashMap errors = null;
		boolean isValid = false;
		try {
			
			db = this.getConnection(context);

			LookupList soggettoMessaggio = new LookupList(db,
					"lookup_messaggio_soggetto");

			LookupList tipoMessaggio = new LookupList(db,
					"lookup_messaggio_tipo");

			LookupList motivoSegnalazioneList = new LookupList(db,
					"lookup_opportunity_budget");

			LookupList tipoBugList = new LookupList(db,
					"lookup_opportunity_competitors");
			HashMap selectedList = (HashMap) context.getSession().getAttribute(
					"finalContacts");
			
			if (selectedList == null) {

				selectedList = new HashMap();
			}
			
			int contactid = ((UserBean) context.getSession().getAttribute("User"))
			.getUserRecord().getContact().getId();
			
			
			CFSNote thisNote = new CFSNote();
			
			thisNote.setEnteredBy(getUserId(context));
			thisNote.setEnteredBy(getUserId(context));
			thisNote.setModifiedBy(getUserId(context));
			
/*			thisNote.setEnteredBy(contactid);
			thisNote.setEnteredBy(contactid);
			thisNote.setModifiedBy(contactid);*/
			thisNote.setReplyId(getUserId(context));
			thisNote.setType(CFSNote.CALL);
			thisNote.setTipo(context.getRequest().getParameter("tipo"));
			if (context.getRequest().getParameter("subject") != null) {
				thisNote.setSubject(soggettoMessaggio
						.getSelectedValue(new Integer(context.getRequest()
								.getParameter("subject"))));
				context.getRequest().setAttribute("subject",
						thisNote.getSubject());
			} else {
				thisNote.setSubject("error");
			}
			if (context.getRequest().getParameter("body") != null) {
				thisNote.setBody(context.getRequest().getParameter("body"));
				context.getRequest().setAttribute("body", thisNote.getBody());
			} else {
				thisNote.setBody("error");
			}
			if (context.getRequest().getParameter("actionId") != null) {
				thisNote.setActionId(context.getRequest().getParameter(
						"actionId"));
			}
			if (context.getRequest().getParameter("savecopy") != null) {
				savecopy = true;
			}
			if (context.getRequest().getParameter("mailrecipients") != null) {
				copyrecipients = true;
				context.getRequest().setAttribute("mailrecipients", "true");
			}

			String dataSegnalazione = (String) context.getRequest().getParameter(
					"problemDate");
		//	System.out.println("Data:" + dataSegnalazione);

			// tipo bug
			String tipoBug = (String) context.getRequest().getParameter(
					"tipoBug");
			int tipoBugInt = -1;
			if (tipoBug != null) {
				tipoBugInt = new Integer(
						(String) context.getRequest().getParameter("tipoBug"));
				tipoBug = tipoBugList.getSelectedValue(tipoBugInt);
			}

		//	System.out.println("Tipo bug: " + tipoBug);

			// dettagli bug tecnico
			String postazione = (String) context.getRequest().getParameter(
					"postazione");
			//System.out.println("Postazione: " + postazione);
			String browser = (String) context.getRequest().getParameter(
					"browser");
		//	System.out.println("Browser: " + browser);
			String ripet = (String) context.getRequest().getParameter("ripet");
			String dettagliBugTecnico = "Postazione: " + postazione
					+ "\nBrowser: " + browser + "\nRipetitibilita: " + ripet
					+ "\n";
		//	System.out.println("Dettaglio tecnico:" + dettagliBugTecnico);
			// motivo segnalazione
			String motivoSegnalazione = (String) context.getRequest()
					.getParameter("motivo");

			if (motivoSegnalazione != null) {
				motivoSegnalazione = motivoSegnalazioneList.getSelectedValue(new Integer(
						motivoSegnalazione));
			}

		//	System.out.println("Motivo segnalazione: " + motivoSegnalazione);

			// aggiorno il corpo del messaggio:

			String dettaglioMess = "";
			if (dataSegnalazione != null)
				dettaglioMess = "Data riscontro problema: " + dataSegnalazione + "\n";
			if (thisNote.getTipo() == 1) {
				dettaglioMess += motivoSegnalazione + "\n";
			} else if (thisNote.getTipo() == 2) {
				if (tipoBugInt == 2)
					dettaglioMess += tipoBug + "\n";
				else if (tipoBugInt == 1)
					dettaglioMess += tipoBug + "\n" + dettagliBugTecnico + "\n";
			}

			thisNote.setBody(dettaglioMess + "\n" + thisNote.getBody());
			thisNote.setTelefonoIndicato((String) context.getRequest().getParameter("numero_tel") );
			thisNote.setEmailIndicata((String) context.getRequest().getParameter("rispondi_a"));

			Contact thisContact = null;
			User thisRecord = (User) ((UserBean) context.getSession()
					.getAttribute("User")).getUserRecord();
			thisRecord.setBuildContact(true);

			noteType = Integer.parseInt((String) context.getRequest()
					.getParameter("forwardType"));
			
			SystemStatus systemStatus = this.getSystemStatus(context);
			isValid = this.validateObject(context, db, thisNote);

			if (isValid) {
				thisRecord.setBuildContactDetails(true);
				thisRecord.buildResources(db);
				
				boolean sentDirectToHD = false;

				// GESTIONE RECUPERO INDIRIZZO MAIL UTENTE REFERENTE ASL PER
				// RUOLO DIVERSO DA REF ASL

				String idRuoloRefAsl = ApplicationProperties
						.getProperty("ID_RUOLO_REFERENTE_ASL");
				int idRuoloRefAslInt = -1;
				if (idRuoloRefAsl != null && !("").equals(idRuoloRefAsl)) {
					idRuoloRefAslInt = new Integer(idRuoloRefAsl).intValue();
				}

				if (thisRecord.getSiteId() > 0 && thisRecord.getRoleId() != idRuoloRefAslInt) {
					UserList referente_asl_list = new UserList();
					referente_asl_list.setSiteId(thisRecord.getSiteId());
					referente_asl_list.setRoleId(idRuoloRefAslInt);
					referente_asl_list.setBuildContact(true);
					referente_asl_list.setBuildContactDetails(true);

					referente_asl_list.buildList(db);

					for (int h = 0; h < referente_asl_list.size(); h++) {
						User thisUserReferente = (User) referente_asl_list
								.get(h);
						ArrayList data = new ArrayList();
						data.add(thisUserReferente.getContact()
								.getPrimaryEmailAddress());
						data.add(soggettoMessaggio.getSelectedValue(thisNote.getSubject()));
						selectedList
								.put(thisUserReferente.getContactId(), data);
					}
				} else  { // Invio
					// segnalazione
					// ad
					// help
					// desk
					// di
					// primo
					// livello
					sentDirectToHD = true;
					ArrayList data = new ArrayList();

					data.add(ApplicationProperties
							.getProperty("HD_LEVEL_1_EMAIL_ADDRESS"));
					data.add("soggetto");
					selectedList.put(12004, data);
				}

				boolean sendCopy = DatabaseUtils
						.parseBoolean(ApplicationProperties
								.getProperty("HD_LEVEL_2_SEND_MAIL"));

				if (sendCopy) { // Se settato l'invio, invio mail in copia
					// conoscenza ad help desk di secondo livello

					ArrayList data = new ArrayList();
					data.add(ApplicationProperties
							.getProperty("HD_LEVEL_2_EMAIL_ADDRESS"));
					data.add(ApplicationProperties.getProperty("soggetto_copia_segnalazioni_hd2"));
					selectedList.put(12004, data); //-3 invio help desk 2 livello
				}

				if (selectedList.size() != 0) {
					String replyAddr = (thisRecord.getContact()
							.getPrimaryEmailAddress() != null && !("").equals(thisRecord.getContact()
							.getPrimaryEmailAddress())) ? thisRecord.getContact()
									.getPrimaryEmailAddress() : (String) context.getRequest().getParameter("rispondi_a");
					int contactId = -1;
					String email = "";
					Set s = selectedList.keySet();
					Iterator i = s.iterator();
					String soggetto = "";
					while (i.hasNext() && isValid) {
						Integer hashKey = (Integer) i.next();
						contactId = hashKey.intValue();
						if (selectedList.get(hashKey) != null) {
							ArrayList st = (ArrayList) selectedList
									.get(hashKey);
							email = (String) st.get(0);
							soggetto = (String) st.get(1);
						}



						if (hashKey.intValue() > 0) { // Solo se sto inviando a
							// un contatto
							thisContact = new Contact();
							thisContact.setId(hashKey.intValue());
							// thisContact.setBuildDetails(true);
							thisContact.setBuildDetails(true); // COSTRUISCE
							// SOLO
							// INDIRIZZO E EMAIL
							// DA
							// CONTACT_ADDRESS E
							// CONTACT_EMAIL_ADDRESS
							thisContact.setBuildTypes(false); // Per evitare che
							// chiama buildtypes
							// nella costruzione
							// del contatto
							thisContact.build(db);
							thisContact.checkEnabledUserAccount(db);

						//	thisNote.setSentTo(contactId);
							//ADESSO CENTRIC VA PER USER_ID, NON PER CONTACT ID PER I MSG INVIATI /RICEVUTI
							thisNote.setSentTo(thisContact.getId());
							
						}

					if ((!email.startsWith("P:"))
								&& (copyrecipients || !thisContact.hasAccount())) {
							/*
							 * All contacts with email addresses (without user
							 * accounts)
							 */
							Mail mail = new Mail();
							mail.setHost(getPref(context, "MAILSERVER"));
							mail.setFrom(getPref(context, "EMAILADDRESS"));
							mail.setUser(getPref(context, "EMAILADDRESS"));
							mail.setPass(getPref(context, "MAILPASSWORD"));
							mail.setPort(Integer.parseInt(getPref(context, "PORTSERVER")));
							mail.setRispondiA(replyAddr);
//							if (replyAddr != null && !(replyAddr.equals(""))) {
//								mail.addReplyTo(replyAddr);
//							}
						//	mail.setType("text/html");
							mail.setTo(email);
							mail.setSogg(soggetto + "   "
									+ thisNote.getSubject());
//							mail.setSubject(soggetto + "   "
//									+ thisNote.getSubject());
							
							LookupList aslList = new LookupList(db, "lookup_asl_rif");
							String asl_rif = aslList.getSelectedValue(thisRecord.getSiteId());
							String message = systemStatus
									.getLabel("mail.body.emailContactWithBody");
							HashMap<String, String> map = new HashMap<String, String>();
							map.put("${thisRecord.contact.nameFirstLast}", thisRecord.getUsername()+ ", corrispondente a " +
											thisRecord.getContact().getNameFirstLast() +
											" (tel: " +  (String) context.getRequest().getParameter("numero_tel") + ") " + " dell'ASL " + asl_rif + ". Mail Di risposta : "+replyAddr );
							map.put("${thisNote.body}", StringUtils.toHtml(thisNote.getBody()));
							Template template = new Template(message);
							template.setParseElements(map);
							mail.setTesto(template.getParsedText());
							
							if (System.getProperty("DEBUG") != null) {
								System.out.println("Sending Mail .. "
										+ thisNote.getBody());
							}
							
							int result = mail.sendMail();
							
							context.getRequest().setAttribute("result", String.valueOf(result));
//						
								// Message is sent or probably not. Insert the note if sending
								// to a contact
								if (hashKey.intValue() > 0 || sentDirectToHD) {
									recordInserted = thisNote.insert(db);
									recordInserted = thisNote.insertLink(db,
											true);
									this.processInsertHook(context, thisNote);
								}
							
					} else if (email.startsWith("P:")
								&& !thisContact.hasAccount()) {
							/*
							 * All contacts without user accounts and without
							 * email addresses
							 */
							if (errors == null) {
								errors = new HashMap();
							}
							errors
								.put(
											"contact" + thisContact.getId(),
											systemStatus
													.getLabel("mail.contactNoEmailAddress"));
						} else if (email.startsWith("P:")
								&& thisContact.getOrgId() != -1
								&& (!thisContact.hasEnabledAccount() || copyrecipients)) {
							/*
							 * all contacts without email addresses with invalid
							 * user accounts
							 */
							if (errors == null) {
								errors = new HashMap();
							}
							errors
									.put(
											"contact" + thisContact.getId(),
											systemStatus
													.getLabel("mail.contactNoEmailAddress"));
						} else {
							recordInserted = thisNote.insert(db);
							recordInserted = thisNote.insertLink(db,
									thisContact.hasAccount());
							this.processInsertHook(context, thisNote);
						}
					}
				} else {
					isValid = false;
					if (errors == null) {
						errors = new HashMap();
					}
					errors.put("contactsError", systemStatus
							.getLabel("mail.contactsNotSelected"));
				}
			}
			context.getSession().removeAttribute("DepartmentList");
			context.getSession().removeAttribute("ProjectListSelect");
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			context.getRequest().setAttribute("Error", errorMessage.getMessage());
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
		if (errors != null) {
			processErrors(context, errors);
		}
		if (isValid) {
			return getReturn(context, "SendMessage");
		}
		if (noteType == Constants.CFSNOTE) {
			return executeCommandNewMessage(context);
		} else if (noteType == Constants.TASKS) {
			return executeCommandForwardMessage(context);
		} else {
			context.getRequest().setAttribute("Error",
					new Exception("Programming Error: Please check code"));
			return "SystemError";
		}
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
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
			context.getRequest().setAttribute("return", forward);
		}
		context.getSession().removeAttribute("selectedContacts");
		context.getSession().removeAttribute("finalContacts");
		SystemStatus systemStatus = this.getSystemStatus(context);
		try {
			String msgId = context.getRequest().getParameter("id");
			int noteType = Integer.parseInt(context.getRequest().getParameter(
					"forwardType"));
			context.getRequest().setAttribute("forwardType", "" + noteType);
			db = this.getConnection(context);
			newNote = new CFSNote();
			if (noteType == Constants.CFSNOTE) {
				// For a sent message myId is a user_id else its a contactId
				if (inboxInfo.getListView().equals("sent")) {
					myId = getUserId(context);
				} else {
					myId = ((UserBean) context.getSession()
							.getAttribute("User")).getUserRecord().getContact()
							.getId();
				}
				newNote = new CFSNote(db, Integer.parseInt(msgId), myId,
						inboxInfo.getListView());
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
				String originalMessage = systemStatus
						.getLabel("mail.label.originalMessage");
				String from = systemStatus.getLabel("mail.label.from");
				String fwd = systemStatus.getLabel("mail.label.forward");
				String sent = systemStatus.getLabel("mail.label.sent");
				String to = systemStatus.getLabel("mail.label.to");
				String subject = systemStatus
						.getLabel("mail.label.subject.colon");
				newNote.setSubject(fwd
						+ StringUtils.toString(newNote.getSubject()));
				newNote.setBody(originalMessage
						+ from
						+ StringUtils.toString(newNote.getSentName())
						+ "\n"
						+ sent
						+ DateUtils.getServerToUserDateTimeString(this
								.getUserTimeZone(context), DateFormat.SHORT,
								DateFormat.LONG, newNote.getEntered()) + "\n"
						+ to + recipientList.toString() + "\n" + subject
						+ StringUtils.toString(newNote.getSubject()) + "\n\n"
						+ StringUtils.toString(newNote.getBody()) + "\n\n");
			} else if (noteType == Constants.TASKS) {
				Task thisTask = new Task(db, Integer.parseInt(msgId));
				context.getRequest().setAttribute("TaskId", msgId);
				String userName = ((UserBean) context.getSession()
						.getAttribute("User")).getUserRecord().getContact()
						.getNameLastFirst();
				String taskDetails = systemStatus
						.getLabel("mail.label.taskDetails");
				String task = systemStatus.getLabel("mail.label.task");
				String from = systemStatus.getLabel("mail.label.from");
				String dueDate = systemStatus.getLabel("mail.label.dueDate");
				String relevantNotes = systemStatus
						.getLabel("mail.label.relevantNotes");
				newNote
						.setBody(taskDetails
								+ "\n"
								+ task
								+ StringUtils.toString(thisTask
										.getDescription())
								+ "\n"
								+ from
								+ StringUtils.toString(userName)
								+ "\n"
								+ dueDate
								+ (thisTask.getDueDate() != null ? DateUtils
										.getServerToUserDateString(this
												.getUserTimeZone(context),
												DateFormat.SHORT, thisTask
														.getDueDate()) : "-NA-")
								+ "\n"
								+ ("".equals(thisTask.getNotes()) ? ""
										: relevantNotes
												+ StringUtils.toString(thisTask
														.getNotes())) + "\n\n");
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		if (context.getAction().getActionName().equals("MyCFSInbox")) {
			addModuleBean(context, "My Inbox", "");
		} else if (context.getAction().getActionName().equals(
				"LeadsCallsForward")) {
			addModuleBean(context, "View Opportunities",
					"Opportunity Activities");
		} else {
			addModuleBean(context, "My Tasks", "Forward Message");
		}
		context.getRequest().setAttribute("Note", newNote);
		return ("ForwardMessageOK");
	}

	/**
	 * Sends a reply to a message
	 * 
	 * @param context
	 *            Description of the Parameter
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
		String originalMessage = systemStatus
				.getLabel("mail.label.originalMessage");
		String from = systemStatus.getLabel("mail.label.from");
		String sent = systemStatus.getLabel("mail.label.sent");
		String to = systemStatus.getLabel("mail.label.to");
		String subject = systemStatus.getLabel("mail.label.subject.colon");
		String reply = systemStatus.getLabel("mail.label.reply");
		try {
			int noteType = Integer.parseInt(context.getRequest().getParameter(
					"forwardType"));
			context.getRequest().setAttribute("forwardType",
					"" + Constants.CFSNOTE);
			String msgId = context.getRequest().getParameter("id");
			db = this.getConnection(context);

			int myId = -1;
			if (inboxInfo.getListView().equals("sent")) {
				myId = getUserId(context);
			} else {
				myId = ((UserBean) context.getSession().getAttribute("User"))
						.getUserRecord().getContact().getId();
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
			newNote.setSubject(reply
					+ StringUtils.toString(newNote.getSubject()));
			newNote.setBody(originalMessage
					+ from
					+ StringUtils.toString(newNote.getSentName())
					+ "\n"
					+ sent
					+ DateUtils.getServerToUserDateTimeString(this
							.getUserTimeZone(context), DateFormat.SHORT,
							DateFormat.LONG, newNote.getEntered()) + "\n" + to
					+ recipientList.toString() + "\n" + subject
					+ StringUtils.toString(newNote.getSubject()) + "\n\n"
					+ StringUtils.toString(newNote.getBody()) + "\n\n");

			// add the sender as a recipient
			User sender = this.getUser(context, newNote.getReplyId());
			Contact recipient = new Contact(db, sender.getContactId());
			context.getRequest().setAttribute("Recipient", recipient);

			// Add the recipient to the selectedList
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

	 public String executeCommandHome(ActionContext context) throws SQLException {

			if (!hasPermission(context, "myhomepage-dashboard-view")) {
				return ("PermissionError");
			}
			SystemStatus systemStatus = this.getSystemStatus(context);
			Connection db = null;
			UserBean user = (UserBean) context.getSession().getAttribute("User");
			
			
			
			
			  String message = context.getRequest().getParameter("Message");
			    if (message!=null && !"".equals(message))
			    {
			    	LoginBean login = new LoginBean();
			    	login.setMessage(message);
			    	context.getRequest().setAttribute("LoginBean", login);
			    }
			    
			

			addModuleBean(context, "View Accounts", "Search Results");

			try {
				db = this.getConnection(context);
				
				
				
//			    System.out.println("AAA  " + context.getServletContext().getContextPath());
//			    System.out.println("BBB  " +context.getServletContext().getInitParameter("context_starting"));
				
				  if (("bdu").equals(context.getServletContext().getInitParameter("context_starting"))){
					  
					  
					  
					  ArrayList<String> caniliOccupati =  new ArrayList<>();
					  if(user.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP")))
					  {
						  caniliOccupati = Stabilimento.caniliOccupati(user.getSiteId(), db);
					  }
					  context.getRequest().setAttribute("caniliOccupati", caniliOccupati);
					  
					  ArrayList<String> caniliBloccatiManualmente =  new ArrayList<>();
					  if(user.getRoleId() != new Integer(ApplicationProperties.getProperty("ID_RUOLO_LLP")))
					  {
						  caniliBloccatiManualmente = Stabilimento.caniliBloccatiManualmente(user.getSiteId(), db);
					  }
					  context.getRequest().setAttribute("caniliBloccatiManualmente", caniliBloccatiManualmente);
				    	
					
//							if (calendarInfo == null) {
//								calendarInfo = new CalendarBean(thisRec.getLocale());
//							}
							
						

//							PagedListInfo contactHistoryListInfo = this.getPagedListInfo(
//									context, "contactHistoryListInfo");
//							contactHistoryListInfo.setLink("MyCFS.do?command=Home"
//									+ RequestUtils.addLinkParams(context.getRequest(),
//											"popup|popupType|actionId"));

							if (user.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_AMMINISTRATORE_ASL")) ||
									user.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_ANAGRAFE_CANINA"))) {
								
								PagedListInfo toLimitCessioniList = new PagedListInfo();
								toLimitCessioniList.setColumnToSortBy("description");
								toLimitCessioniList.setItemsPerPage(10); //PER VISUALIZZARLI TUTTI
								
								
								EventoList eventiCircuitoCommerciale = new EventoList();
								
								eventiCircuitoCommerciale.setPagedListInfo(toLimitCessioniList);
								eventiCircuitoCommerciale
										.setIdTipologiaEvento(EventoCessione.idTipologiaDB);
								eventiCircuitoCommerciale
										.setId_asl_nuovo_proprietario(getUserAsl(context));
								eventiCircuitoCommerciale.setGet_only_opened(true);
								eventiCircuitoCommerciale.setFlag_circuito_commerciale(1);
								eventiCircuitoCommerciale.buildList(db); //--DA METTERE!!

								EventoList eventiCircuitoPrivato = new EventoList();
								eventiCircuitoPrivato.setPagedListInfo(toLimitCessioniList);
								eventiCircuitoPrivato
										.setIdTipologiaEvento(EventoCessione.idTipologiaDB);
								eventiCircuitoPrivato
										.setId_asl_nuovo_proprietario(getUserAsl(context));
								eventiCircuitoPrivato.setGet_only_opened(true);
								eventiCircuitoPrivato.setFlag_circuito_commerciale(0);
								eventiCircuitoPrivato.buildList(db); //--DA RIMETTERE
								
								
								
								// Lista registrazioni operatore

								RegistrazioniOperatoreList registrazioniList = new RegistrazioniOperatoreList();
								
								PagedListInfo searchListInfo = this.getPagedListInfo(context,
										"registrazioniListInfo");
								searchListInfo
										.setLink("OperatoreAction.do?command=AggiungiRegistrazioni");
								searchListInfo.setListView("all");

								registrazioniList.setPagedListInfo(toLimitCessioniList);
								registrazioniList.setIdAslDestinatariaModificaFuoriResidenza(getUserAsl(context));
								registrazioniList.setFlagCercaSoloSospese(true);
								registrazioniList.buildList(db);
								
								
								context.getRequest().setAttribute("listaRegistrazioni", registrazioniList);
								

								context.getRequest().setAttribute("eventiCircuitoCommerciale",
										eventiCircuitoCommerciale);
								context.getRequest().setAttribute("eventiCircuitoPrivato",
										eventiCircuitoPrivato);

								LookupList registrazioni = new LookupList(db,
										"lookup_tipologia_registrazione");
								context.getRequest().setAttribute("registrazioni",
										registrazioni);

								LookupList aslList = new LookupList(db, "lookup_asl_rif");
								context.getRequest().setAttribute("aslList", aslList);


							}

	
			
						return "HomeOK";
				    	
				    }else{
					System.out.println(getReturn(context, "Home_"+user.getRoleId()));
					
					if (user.getRoleId() == new Integer(ApplicationProperties.getProperty("RUOLO_OPERATORE_COMMERCIALE")).intValue() 
							){
						System.out.println(getReturn(context, "Home_"+user.getRoleId()));
						
						System.out.println("OP COMMERCIALE");
								return getReturn(context, "Home_"+user.getRoleId());
							}
					
					System.out.println("UTENTE COMUNE");
						return getReturn(context, "HomePrelievoDNA");
				    }
				

			} catch (Exception e) {
				context.getRequest().setAttribute("Error", e);
				e.printStackTrace();
				this.freeConnection(context, db);
				return ("SystemError");

			}finally {
				
				this.freeConnection(context, db);
			}
			
//			if (user.getRoleId() == new Integer(ApplicationProperties.getProperty("RUOLO_AMMINISTRATORE_ASL")) 
//			|| user.getRoleId() == new Integer(ApplicationProperties.getProperty("RUOLO_UTENTE_COMUNE")) || 
//			user.getRoleId() == new Integer(ApplicationProperties.getProperty("RUOLO_HD1")))
//		    System.out.println("Caricando contesto  " + context.getServletContext().getContextPath());
//		    System.out.println("Setting connection pools for  " +context.getServletContext().getServletContextName());
		
		    
		  

//			return "HomeOK";
//
//		return "HomeOK";

	}
	
	
	
	public String  executeCommandGetCessioniIncompiute(ActionContext context){

		if (!(hasPermission(context, "myhomepage_cessioni_incompiute-view"))) {
			return ("PermissionError");
		}
		addModuleBean(context, "Home", "");

		UserBean thisUser = (UserBean) context.getSession()
				.getAttribute("User");
		User thisRec = thisUser.getUserRecord();

		
		Connection db = null;
		CalendarBean calendarInfo = (CalendarBean) context.getSession()
				.getAttribute("CalendarInfo");
		if (isOfflineMode(context)) {
			try {
				db = this.getConnection(context);
				
			} catch (Exception errorMessage) {
				context.getRequest().setAttribute("Error", errorMessage);
				return "SystemError";
			} finally {
				this.freeConnection(context, db);
			}
		}
		try {

			db = this.getConnection(context);

				PagedListInfo toLimitCessioniList = new PagedListInfo();
				toLimitCessioniList.setColumnToSortBy("description");
				toLimitCessioniList.setItemsPerPage(10); //PER VISUALIZZARLI TUTTI
				
				
				EventoList eventiCircuitoCommerciale = new EventoList();
				
				eventiCircuitoCommerciale.setPagedListInfo(toLimitCessioniList);
				eventiCircuitoCommerciale
						.setIdTipologiaEvento(EventoCessione.idTipologiaDB);
				eventiCircuitoCommerciale
						.setId_asl_nuovo_proprietario(getUserAsl(context));
				eventiCircuitoCommerciale.setGet_only_opened(true);
				eventiCircuitoCommerciale.setFlag_circuito_commerciale(1);
				eventiCircuitoCommerciale.buildList(db); //--DA METTERE!!

				EventoList eventiCircuitoPrivato = new EventoList();
				eventiCircuitoPrivato.setPagedListInfo(toLimitCessioniList);
				eventiCircuitoPrivato
						.setIdTipologiaEvento(EventoCessione.idTipologiaDB);
				eventiCircuitoPrivato
						.setId_asl_nuovo_proprietario(getUserAsl(context));
				eventiCircuitoPrivato.setGet_only_opened(true);
				eventiCircuitoPrivato.setFlag_circuito_commerciale(0);
				eventiCircuitoPrivato.buildList(db); //--DA RIMETTERE
				
				
				
				// Lista registrazioni operatore

				RegistrazioniOperatoreList registrazioniList = new RegistrazioniOperatoreList();
				
				PagedListInfo searchListInfo = this.getPagedListInfo(context,
						"registrazioniListInfo");
				searchListInfo
						.setLink("OperatoreAction.do?command=AggiungiRegistrazioni");
				searchListInfo.setListView("all");

				registrazioniList.setPagedListInfo(toLimitCessioniList);
				registrazioniList.setIdAslDestinatariaModificaFuoriResidenza(getUserAsl(context));
				registrazioniList.setFlagCercaSoloSospese(true);
				registrazioniList.buildList(db);
				
				
				context.getRequest().setAttribute("listaRegistrazioni", registrazioniList);
				

				context.getRequest().setAttribute("eventiCircuitoCommerciale",
						eventiCircuitoCommerciale);
				context.getRequest().setAttribute("eventiCircuitoPrivato",
						eventiCircuitoPrivato);

				LookupList registrazioni = new LookupList(db,
						"lookup_tipologia_registrazione");
				context.getRequest().setAttribute("registrazioni",
						registrazioni);

				LookupList aslList = new LookupList(db, "lookup_asl_rif");
				context.getRequest().setAttribute("aslList", aslList);


			

		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return "SystemError";
		} finally {
			this.freeConnection(context, db);
		}
		SystemStatus systemStatus = this.getSystemStatus(context);

		return "CessioniIncludeOK";
	
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandAlerts(ActionContext context) {
		if (!(hasPermission(context, "myhomepage-dashboard-view"))) {
			return ("PermissionError");
		}
		Connection db = null;
		addModuleBean(context, "Home", "");
		CalendarBean calendarInfo = null;
		CalendarView companyCalendar = null;

		String returnPage = context.getRequest().getParameter("return");
		calendarInfo = (CalendarBean) context.getSession().getAttribute(
				returnPage != null ? returnPage + "CalendarInfo"
						: "CalendarInfo");

		try {
			db = this.getConnection(context);
			calendarInfo.update(db, context);
			int userId = this.getUserId(context);
			User thisUser = this.getUser(context, userId);
			// Use the user's locale
			companyCalendar = new CalendarView(calendarInfo, thisUser
					.getLocale());
			companyCalendar.setSystemStatus(this.getSystemStatus(context));
			companyCalendar.addHolidays();

			// check if the user's account is expiring
			if (context.getRequest().getParameter("userId") != null) {
				userId = Integer.parseInt(context.getRequest().getParameter(
						"userId"));
				if (userId == this.getUserId(context)) {
					Integer tmpUserId = (Integer) context.getSession()
							.getAttribute("calendarUserId");
					if (tmpUserId != null) {
						context.getSession().removeAttribute("calendarUserId");
					}
				} else {
					context.getSession().setAttribute("calendarUserId",
							new Integer(userId));
				}
			} else if (context.getSession().getAttribute("calendarUserId") != null) {
				userId = ((Integer) context.getSession().getAttribute(
						"calendarUserId")).intValue();
			}
			if (thisUser.getExpires() != null) {
				String expiryDate = DateUtils.getServerToUserDateString(this
						.getUserTimeZone(context), DateFormat.SHORT, thisUser
						.getExpires());
				companyCalendar.addEvent(expiryDate,
						CalendarEventList.EVENT_TYPES[9],
						"Your user login expires");
			}

			// create events depending on alert type
			String selectedAlertType = calendarInfo.getCalendarDetailsView();
			String param1 = "org.aspcfs.utils.web.CalendarView";
			String param2 = "java.sql.Connection";
			ArrayList alertTypes = calendarInfo.getAlertTypes();
			for (int i = 0; i < alertTypes.size(); i++) {
				AlertType thisAlert = (AlertType) alertTypes.get(i);
				Object thisInstance = Class.forName(thisAlert.getClassName())
						.newInstance();
				if (selectedAlertType.equalsIgnoreCase("all")
						|| selectedAlertType.toLowerCase().equals(
								(thisAlert.getName().toLowerCase()))) {

					// set module
					Method method = Class
							.forName(thisAlert.getClassName())
							.getMethod(
									"setModule",
									new Class[] { Class
											.forName("org.aspcfs.modules.actions.CFSModule") });
					method.invoke(thisInstance,
							new Object[] { (CFSModule) this });

					// set action context
					method = Class
							.forName(thisAlert.getClassName())
							.getMethod(
									"setContext",
									new Class[] { Class
											.forName("com.darkhorseventures.framework.actions.ActionContext") });
					method.invoke(thisInstance, new Object[] { context });

					// set userId
					method = Class.forName(thisAlert.getClassName()).getMethod(
							"setUserId",
							new Class[] { Class.forName("java.lang.String") });
					method.invoke(thisInstance, new Object[] { String
							.valueOf(userId) });

					// set Start and End Dates
					method = Class.forName(thisAlert.getClassName())
							.getMethod(
									"setAlertRangeStart",
									new Class[] { Class
											.forName("java.sql.Timestamp") });
					java.sql.Timestamp startDate = DatabaseUtils
							.parseTimestamp(DateUtils
									.getUserToServerDateTimeString(
											calendarInfo.getTimeZone(),
											DateFormat.SHORT,
											DateFormat.LONG,
											companyCalendar
													.getCalendarStartDate(context),
											Locale.US));
					method.invoke(thisInstance, new Object[] { startDate });

					method = Class.forName(thisAlert.getClassName())
							.getMethod(
									"setAlertRangeEnd",
									new Class[] { Class
											.forName("java.sql.Timestamp") });
					java.sql.Timestamp endDate = DatabaseUtils
							.parseTimestamp(DateUtils
									.getUserToServerDateTimeString(
											calendarInfo.getTimeZone(),
											DateFormat.SHORT,
											DateFormat.LONG,
											companyCalendar
													.getCalendarEndDate(context),
											Locale.US));
					method.invoke(thisInstance, new Object[] { endDate });

					// Add Events
					method = Class.forName(thisAlert.getClassName()).getMethod(
							"buildAlerts",
							new Class[] { Class.forName(param1),
									Class.forName(param2) });
					method.invoke(thisInstance, new Object[] { companyCalendar,
							db });
					if (!selectedAlertType.equalsIgnoreCase("all")) {
						break;
					}
				}
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return "SystemError";
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("CompanyCalendar", companyCalendar);
		return "CalendarDetailsOK";
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandMonthView(ActionContext context) {
		if (!(hasPermission(context, "myhomepage-dashboard-view"))) {
			return ("PermissionError");
		}
		Connection db = null;
		CalendarBean calendarInfo = null;
		CalendarView companyCalendar = null;
		addModuleBean(context, "Home", "");
		String returnPage = context.getRequest().getParameter("return");
		String beanName = (returnPage != null ? returnPage + "CalendarInfo"
				: "CalendarInfo");
		calendarInfo = (CalendarBean) context.getSession().getAttribute(
				beanName);
		if (calendarInfo == null) {
			calendarInfo = new CalendarBean(this.getUser(context,
					this.getUserId(context)).getLocale());
			context.getSession().setAttribute(beanName, calendarInfo);
		}
		try {
			db = this.getConnection(context);
			calendarInfo.update(db, context);
			int userId = this.getUserId(context);
			User thisUser = this.getUser(context, userId);
			// Use the user's locale
			companyCalendar = new CalendarView(calendarInfo, thisUser
					.getLocale());
			companyCalendar.setSystemStatus(this.getSystemStatus(context));
			// check if user account is expiring
			if (context.getRequest().getParameter("userId") != null) {
				userId = Integer.parseInt(context.getRequest().getParameter(
						"userId"));
				if (userId == this.getUserId(context)) {
					Integer tmpUserId = (Integer) context.getSession()
							.getAttribute("calendarUserId");
					if (tmpUserId != null) {
						context.getSession().removeAttribute("calendarUserId");
					}
				} else {
					context.getSession().setAttribute("calendarUserId",
							new Integer(userId));
				}
			} else if (context.getSession().getAttribute("calendarUserId") != null) {
				userId = ((Integer) context.getSession().getAttribute(
						"calendarUserId")).intValue();
			}
			if (thisUser.getExpires() != null) {
				String expiryDate = DateUtils.getServerToUserDateString(this
						.getUserTimeZone(context), DateFormat.SHORT, thisUser
						.getExpires());
				companyCalendar.addEventCount(CalendarEventList.EVENT_TYPES[9],
						expiryDate, new Integer(1));
			}

			User selectedUser = new User();
			selectedUser.setBuildContact(true);
			selectedUser.buildRecord(db, userId);
			context.getRequest().setAttribute("SelectedUser", selectedUser);

			// Use reflection to invoke methods on scheduler classes
			String param1 = "org.aspcfs.utils.web.CalendarView";
			String param2 = "java.sql.Connection";
			ArrayList alertTypes = calendarInfo.getAlertTypes();
			for (int i = 0; i < alertTypes.size(); i++) {
				AlertType thisAlert = (AlertType) alertTypes.get(i);
				Object thisInstance = Class.forName(thisAlert.getClassName())
						.newInstance();

				// set module
				Method method = Class
						.forName(thisAlert.getClassName())
						.getMethod(
								"setModule",
								new Class[] { Class
										.forName("org.aspcfs.modules.actions.CFSModule") });
				method.invoke(thisInstance, new Object[] { (CFSModule) this });

				// set action context
				method = Class
						.forName(thisAlert.getClassName())
						.getMethod(
								"setContext",
								new Class[] { Class
										.forName("com.darkhorseventures.framework.actions.ActionContext") });
				method.invoke(thisInstance, new Object[] { context });

				// set userId
				method = Class.forName(thisAlert.getClassName()).getMethod(
						"setUserId",
						new Class[] { Class.forName("java.lang.String") });
				method.invoke(thisInstance, new Object[] { String
						.valueOf(userId) });

				// set Start and End Dates
				java.sql.Timestamp startDate = DatabaseUtils
						.parseTimestamp(DateUtils
								.getUserToServerDateTimeString(calendarInfo
										.getTimeZone(), DateFormat.SHORT,
										DateFormat.LONG, companyCalendar
												.getCalendarStartDate(context),
										Locale.US));
				method = Class.forName(thisAlert.getClassName()).getMethod(
						"setAlertRangeStart",
						new Class[] { Class.forName("java.sql.Timestamp") });
				method.invoke(thisInstance, new Object[] { startDate });

				java.sql.Timestamp endDate = DatabaseUtils
						.parseTimestamp(DateUtils
								.getUserToServerDateTimeString(calendarInfo
										.getTimeZone(), DateFormat.SHORT,
										DateFormat.LONG, companyCalendar
												.getCalendarEndDate(context),
										Locale.US));
				method = Class.forName(thisAlert.getClassName()).getMethod(
						"setAlertRangeEnd",
						new Class[] { Class.forName("java.sql.Timestamp") });
				method.invoke(thisInstance, new Object[] { endDate });

				// Add Events
				method = Class.forName(thisAlert.getClassName()).getMethod(
						"buildAlertCount",
						new Class[] { Class.forName(param1),
								Class.forName(param2) });
				method.invoke(thisInstance,
						new Object[] { companyCalendar, db });
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return "SystemError";
		} finally {
			this.freeConnection(context, db);
		}
		context.getRequest().setAttribute("CompanyCalendar", companyCalendar);
		return "CalendarOK";
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandDayView(ActionContext context) {
		if (!(hasPermission(context, "myhomepage-dashboard-view"))) {
			return ("PermissionError");
		}
		CalendarBean calendarInfo = null;
		addModuleBean(context, "Home", "");
		String returnPage = context.getRequest().getParameter("return");
		calendarInfo = (CalendarBean) context.getSession().getAttribute(
				returnPage != null ? returnPage + "CalendarInfo"
						: "CalendarInfo");
		calendarInfo.setCalendarView("day");
		calendarInfo.resetParams("day");
		return executeCommandAlerts(context);
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandTodaysView(ActionContext context) {
		if (!(hasPermission(context, "myhomepage-dashboard-view"))) {
			return ("PermissionError");
		}
		CalendarBean calendarInfo = null;
		addModuleBean(context, "Home", "");
		String returnPage = context.getRequest().getParameter("return");
		calendarInfo = (CalendarBean) context.getSession().getAttribute(
				returnPage != null ? returnPage + "CalendarInfo"
						: "CalendarInfo");
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(calendarInfo.getTimeZone());
		calendarInfo.setCalendarView("day");
		calendarInfo.resetParams("day");
		calendarInfo.setPrimaryMonth(cal.get(Calendar.MONTH) + 1);
		calendarInfo.setPrimaryYear(cal.get(Calendar.YEAR));
		return executeCommandAlerts(context);
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandWeekView(ActionContext context) {
		if (!(hasPermission(context, "myhomepage-profile-view"))) {
			return ("PermissionError");
		}
		addModuleBean(context, "Home", "");
		CalendarBean calendarInfo = null;
		String returnPage = context.getRequest().getParameter("return");
		calendarInfo = (CalendarBean) context.getSession().getAttribute(
				returnPage != null ? returnPage + "CalendarInfo"
						: "CalendarInfo");
		calendarInfo.setCalendarView("week");
		calendarInfo.resetParams("week");
		return executeCommandAlerts(context);
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandAgendaView(ActionContext context) {
		if (!(hasPermission(context, "myhomepage-dashboard-view"))) {
			return ("PermissionError");
		}
		addModuleBean(context, "Home", "");
		CalendarBean calendarInfo = null;
		String returnPage = context.getRequest().getParameter("return");
		calendarInfo = (CalendarBean) context.getSession().getAttribute(
				returnPage != null ? returnPage + "CalendarInfo"
						: "CalendarInfo");
		calendarInfo.setAgendaView(true);
		calendarInfo.resetParams("agenda");
		return executeCommandAlerts(context);
	}

	/**
	 * Displays a list of profile items the user can select to modify
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandMyProfile(ActionContext context) {
		if (!(hasPermission(context, "myhomepage-profile-view"))) {
			return ("PermissionError");
		}
		addModuleBean(context, "MyProfile", "");
		return ("MyProfileOK");
	}

	/**
	 * The user wants to modify their name, etc.
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandMyCFSProfile(ActionContext context) {
		if (!hasPermission(context, "myhomepage-profile-personal-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		try {
			db = this.getConnection(context);
			SystemStatus systemStatus = this.getSystemStatus(context);
			context.getRequest().setAttribute("systemStatus", systemStatus);
			User thisUser = new User(db, this.getUserId(context));
			thisUser.setBuildContact(true);
			thisUser.setBuildContactDetails(true);
			thisUser.buildResources(db);
			ApplicationPrefs prefs = (ApplicationPrefs) context
					.getServletContext().getAttribute("applicationPrefs");
			StateSelect stateSelect = new StateSelect(systemStatus, thisUser
					.getContact().getAddressList().getCountries()
					+ "," + prefs.get("SYSTEM.COUNTRY"));
			stateSelect.setPreviousStates(thisUser.getContact()
					.getAddressList().getSelectedStatesHashMap());
			context.getRequest().setAttribute("StateSelect", stateSelect);
			buildFormElements(context, db);
			context.getRequest().setAttribute("User", thisUser);
			context.getRequest().setAttribute("EmployeeBean",
					thisUser.getContact());
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return "SystemError";
		}
		this.freeConnection(context, db);
		addModuleBean(context, "MyProfile", "");
		return ("ProfileOK");
	}

	/**
	 * The user's name was modified
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandUpdateProfile(ActionContext context) {
		if (!hasPermission(context, "myhomepage-profile-personal-edit")) {
			return ("PermissionError");
		}
		// Prepare the action
		Connection db = null;
		int resultCount = -1;
		boolean isValid = false;
		// Process the request
		Contact thisContact = (Contact) context.getFormBean();
		thisContact.setRequestItems(context);
		thisContact.setEnteredBy(getUserId(context));
		thisContact.setModifiedBy(getUserId(context));
		try {
			db = this.getConnection(context);
			isValid = this.validateObject(context, db, thisContact);
			if (isValid) {
				resultCount = thisContact.update(db);
			}
			if (resultCount == -1 || !isValid) {
				buildFormElements(context, db);
			} else {
				// If the user is in the cache, update the contact record
				thisContact.checkUserAccount(db);
				this.updateUserContact(db, context, thisContact.getUserId());
			}
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		if (resultCount == 1) {
			return ("UpdateProfileOK");
		} else {
			if (resultCount == -1 || !isValid) {
				return (executeCommandMyCFSProfile(context));
			}
			context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
			return ("UserError");
		}
	}

	/**
	 * The user wants to change the password
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandMyCFSPassword(ActionContext context) {
		if (!(hasPermission(context, "myhomepage-profile-password-edit"))) {
			return ("PermissionError");
		}
		Connection db = null;
		try {
			db = this.getConnection(context);
			User thisUser = new User(db, this.getUserId(context));
			thisUser.setBuildContact(false);
			thisUser.buildResources(db);
			context.getRequest().setAttribute("User", thisUser);
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		}
		this.freeConnection(context, db);
		addModuleBean(context, "MyProfile", "");
		return ("PasswordOK");
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandMyCFSWebdav(ActionContext context) {
		Connection db = null;
		try {
			db = this.getConnection(context);
			User thisUser = new User(db, this.getUserId(context));
			thisUser.setBuildContact(false);
			thisUser.buildResources(db);
			context.getRequest().setAttribute("User", thisUser);
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		}
		this.freeConnection(context, db);
		addModuleBean(context, "MyProfile", "");
		return ("WebdavOK");
	}

	/**
	 * The password was modified
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandUpdatePassword(ActionContext context) {
		if (!hasPermission(context, "myhomepage-profile-password-edit")) {
			return ("PermissionError");
		}
		Connection db = null;
		int resultCount = 0;
		User tempUser = (User) context.getFormBean();
		try {
			db = getConnection(context);
			User thisUser = new User(db, this.getUserId(context));
			thisUser.setBuildContact(false);
			thisUser.buildResources(db);
			tempUser.setUsername(thisUser.getUsername());
			resultCount = tempUser.updatePassword(db, context, thisUser
					.getPassword());
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		if (resultCount == -1) {
			processErrors(context, tempUser.getErrors());
			context.getRequest().setAttribute("NewUser", tempUser);
		}
		if (resultCount == -1) {
			return (executeCommandMyCFSPassword(context));
		} else if (resultCount == 1) {
			return ("UpdatePasswordOK");
		} else {
			context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
			return ("UserError");
		}
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandMyCFSSettings(ActionContext context) {
		// if (!hasPermission(context, "myhomepage-profile-settings-view")) {
		if (!hasPermission(context, "myhomepage-profile-view")) {
			return ("PermissionError");
		}
		Connection db = null;
		try {
			db = this.getConnection(context);
			User thisUser = new User(db, this.getUserId(context));
			thisUser.setBuildContact(true);
			thisUser.buildResources(db);
			context.getRequest().setAttribute("User", thisUser);
			context.getRequest().setAttribute("EmployeeBean",
					thisUser.getContact());
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		}
		this.freeConnection(context, db);
		addModuleBean(context, "MyProfile", "");
		return ("SettingsOK");
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public String executeCommandUpdateSettings(ActionContext context) {
		// if (!(hasPermission(context, "myhomepage-profile-settings-edit"))) {
		if (!(hasPermission(context, "myhomepage-profile-view"))) {
			return ("PermissionError");
		}
		// Process params
		String timeZone = context.getRequest().getParameter("timeZone");
		// Update the user record AND the cached record
		Connection db = null;
		try {
			db = getConnection(context);
			getUser(context, getUserId(context)).setTimeZone(timeZone);
			getUser(context, getUserId(context)).updateSettings(db);
		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			freeConnection(context, db);
		}
		return ("UpdateSettingsOK");
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of Parameter
	 * @param db
	 *            Description of Parameter
	 * @throws SQLException
	 *             Description of Exception
	 */

	protected void buildFormElements(ActionContext context, Connection db)
			throws SQLException {
		SystemStatus systemStatus = this.getSystemStatus(context);

		LookupList soggettoMessaggio = new LookupList(db,
				"lookup_messaggio_soggetto");
		context.getRequest().setAttribute("SoggettoMessaggio",
				soggettoMessaggio);

		LookupList tipoMessaggio = new LookupList(db, "lookup_messaggio_tipo");
		tipoMessaggio
				.setJsEvent("onChange=\"javascript:updateTipoMessaggio();\"");
		context.getRequest().setAttribute("TipoMessaggio", tipoMessaggio);

		LookupList motivoSegnalazione = new LookupList(db,
				"lookup_opportunity_budget");
		context.getRequest().setAttribute("MotivoSegnalazione",
				motivoSegnalazione);

		LookupList tipoBug = new LookupList(db,
				"lookup_opportunity_competitors");
		tipoBug.setJsEvent("onChange=\"javascript:updateTipoBug();\"");
		context.getRequest().setAttribute("TipoBug", tipoBug);

		/*
		 * LookupList departmentList = new LookupList(db, "lookup_department");
		 * departmentList.addItem(0,
		 * systemStatus.getLabel("calendar.none.4dashes"));
		 * context.getRequest().setAttribute("DepartmentList", departmentList);
		 */
		/*
		 * LookupList phoneTypeList = new LookupList(db,
		 * "lookup_contactphone_types");
		 * context.getRequest().setAttribute("ContactPhoneTypeList",
		 * phoneTypeList);
		 */

		/*
		 * LookupList emailTypeList = new LookupList(db,
		 * "lookup_contactemail_types");
		 * context.getRequest().setAttribute("ContactEmailTypeList",
		 * emailTypeList);
		 */

		/*
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * LookupList addressTypeList = new LookupList( db,
		 * "lookup_contactaddress_types"); context.getRequest().setAttribute(
		 * "ContactAddressTypeList", addressTypeList);
		 */

		// Make the StateSelect and CountrySelect drop down menus available in
		// the request.
		// This needs to be done here to provide the SystemStatus to the
		// constructors, otherwise translation is not possible
		StateSelect stateSelect = (StateSelect) context.getRequest()
				.getAttribute("StateSelect");
		if (stateSelect == null) {
			stateSelect = new StateSelect(systemStatus);
			context.getRequest().setAttribute("StateSelect", stateSelect);
		}
		CountrySelect countrySelect = new CountrySelect(systemStatus);
		context.getRequest().setAttribute("CountrySelect", countrySelect);
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandAddNote(ActionContext context) {
		addModuleBean(context, "Account History", "View History");
		String source = (String) context.getRequest().getParameter("source");
		OrganizationHistory history = null;
		history = new OrganizationHistory();
		context.getRequest().setAttribute("history", history);
		if (context.getRequest().getParameter("actionSource") != null) {
			return getReturn(context, "AddNotes");
		}
		return ("AddNote");
	}

	/**
	 * Description of the Method
	 * 
	 * @param context
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandSaveNote(ActionContext context) {
		String source = (String) context.getRequest().getParameter("source");
		OrganizationHistory thisHistory = new OrganizationHistory();
		String id = context.getRequest().getParameter("id");
		boolean isValid = false;
		boolean recordInserted = false;
		int resultCount = -1;
		Connection db = null;
		try {
			db = this.getConnection(context);
			if (id != null && !id.equalsIgnoreCase("-1")) {
				thisHistory = new OrganizationHistory(db, Integer.parseInt(id));
			}
			thisHistory.setEnteredBy(this.getUserId(context));
			thisHistory.setModifiedBy(this.getUserId(context));
			isValid = this.validateObject(context, db, thisHistory);
			if (isValid) {
				if (thisHistory.getId() != -1) {
					thisHistory.setDescription(context.getRequest()
							.getParameter("description"));
					resultCount = thisHistory.update(db);
				} else {
					thisHistory.setLinkObjectId(OrganizationHistory.USER_NOTE);
					thisHistory.setStatus(context.getRequest().getParameter(
							"status"));
					thisHistory.setDescription(context.getRequest()
							.getParameter("description"));
					thisHistory.setType(context.getRequest().getParameter(
							"type"));
					thisHistory.setEnabled(true);
					recordInserted = thisHistory.insert(db);
				}
			}
			if (!recordInserted && resultCount == -1) {
				context.getRequest().setAttribute("history", thisHistory);
				return "AddNote";
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}
		return "SaveNotesOK";
	}

	public String executeCommandCambioUtente(ActionContext context) {
		if (!(hasPermission(context, "myhomepage-profile-view"))) {
			return ("PermissionError");
		}

		return "CambioUtenteOK";
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
			 try {
				
				/*
				 * per evitare che se veniamo da sca, il cambio utente richiami la logout che richiami redirect verso sistema origine
				 */
				 context.getRequest().setAttribute("isCambioUtente",new Boolean(true)); 
				login.executeCommandLogout(context);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			  //encryptedToken =  Login.NEWencrypt(originalToken,this.getClass().getResource("aes_key"));
			 ClientSCAAesServlet cclient = new ClientSCAAesServlet( );
			 encryptedToken = cclient.crypt( originalToken);
			  context.getResponse().sendRedirect( "Login.do?command=LoginNoPassword&encryptedToken="+URLEncoder.encode(encryptedToken,"UTF-8"));

		  
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }	
		  catch(Exception ex)
		  {
			  ex.printStackTrace();
		  }
		

		return "-none-";
	}
	
//
//	public String executeCommandCambioUtenteConferma(ActionContext context) throws SQLException {
//		if (!(hasPermission(context, "myhomepage-profile-view"))) {
//			return ("PermissionError");
//		}
//
//		Connection db = null;
//		String username = context.getParameter("username");
//		LogBean lb = new LogBean();
//
//		try {
//			db = getConnection(context);
//			PreparedStatement pst = db
//					.prepareStatement("SELECT user_id FROM access a WHERE username = ? AND a.enabled and a.trashed_date is null");
//			pst.setString(1, username);
//			ResultSet res = pst.executeQuery();
//			UserBean oldUser = (UserBean) context.getSession().getAttribute(
//					"User");
//
//			if (res.next()) {
//				int userId = res.getInt("user_id");
//				User newUser = getUser(context, userId);
//		        //COSTRUISCO I DETTAGLI DEL CONTATTO PER AVER NUM TELEFONICO E EMAIL DI VET PRIVATI IN HPAGE
//				newUser.setBuildContact(true);
//				newUser.setBuildContactDetails(true);
//				newUser.buildResources(db);
//				UserBean ub = new UserBean();
//				ub.setSessionId(context.getSession().getId());
//				ub.setUserId(userId);
//				ub.setActualUserId(userId);
//				ub.setConnectionPoll(oldUser.getConnectionPoll());
//				//ub.setConnectionElement(oldUser.getConnectionElement());
//				ub.setClientType(context.getRequest());
//				ub.setIdRange(newUser.getIdRange());
//				newUser.setIp(context.getRequest().getRemoteAddr());
//				newUser.updateLogin(db);
//				ub.setUserRecord(newUser);
//
//				context.getSession().removeAttribute("User");
//
//				context.getSession().setAttribute("User", ub);
//				
//				/**
//				 * Aggiorno le informazioni di user_id della sessione (meccanismo per bloccare in caso di sovrapposizione utenti)
//				 */
//
//				context.getSession().setAttribute("UserID", ub.getUserId());
//				// salvo le info sul cambio di utente
//				lb.store(-1, newUser.getId(), 3, "Utente originario: "
//						+ oldUser.getUserId() + " ('" + oldUser.getUsername()
//						+ "')", context, db);
//			} else {
//				context.getRequest().setAttribute("errore",
//						"Utente \"" + username + "\" non trovato");
//
//				// salvo il tentativo di cambio utente
//				lb.store(-1, -1, 4, "Utente originario: " + oldUser.getUserId()
//						+ " ('" + oldUser.getUsername() + "') "
//						+ "Username Fornita: " + username, context, db);
//
//				return executeCommandCambioUtente(context);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			context.getRequest().setAttribute("errore", e.getMessage());
//			return executeCommandCambioUtente(context);
//		} finally {
//			freeConnection(context, db);
//		}
//
//		return executeCommandHome(context);
//	}
	
	public String executeCommandListaVeterinariPrivati(ActionContext context) {
		if (!(hasPermission(context, "myhomepage_lista_llpp-view"))) {
			return ("PermissionError");
		}
		int countRegolarizzati = 0;
		
		Connection db = null;
		
		try{
			
		db = getConnection(context);
		PagedListInfo searchListInfo = this.getPagedListInfo(context,
		"veterinariListInfo");
		searchListInfo.setLink("MyCFS.do?command=ListaVeterinariPrivati");
		searchListInfo.setListView("all");
		
		
		
		
		UserList listaVetList = new UserList();
		listaVetList.setEnabled(1);
		listaVetList.setPagedListInfo(searchListInfo);
		listaVetList.setBuildContact(true);
		listaVetList.setBuildContactDetails(true);
		listaVetList.setRoleId(Integer.parseInt(ApplicationProperties.getProperty("ID_RUOLO_LLP")));
		
		
		listaVetList.buildList(db);
		
		for (int i = 0; i<listaVetList.size(); i++){
			Contact c = ((User) listaVetList.get(i)).getContact();
			if (c.getPrimaryEmailAddress() != null && !("").equals(c.getPrimaryEmailAddress())
					&& c.getPrimaryPhoneNumber() != null && !("").equals(c.getPrimaryPhoneNumber())){
				countRegolarizzati++;
				
			}
		
		}
		
		context.getRequest().setAttribute("veterinariList", listaVetList);
		context.getRequest().setAttribute("countRegolarizzati", String.valueOf(countRegolarizzati));

		return "VeterinariListOK";
		}catch (Exception e) {
			freeConnection(context, db);
		}
		
		return "";
	}
	
	
	public String executeCommandUpdateAssociazione(ActionContext context) 
	{
		if (!(hasPermission(context, "associazioni-edit"))) 
			return ("PermissionError");
		
		Connection db = null;
		
		try
		{
			
			db = getConnection(context);
		
			String sql = "SELECT * FROM public_functions.updateAssociazione(?,?,?,?,?,?,?,?,?,?,?)";
			
			int id = Integer.parseInt(context.getRequest().getParameter("id"));
			String denominazione = context.getRequest().getParameter("denominazione");
			String codiceFiscale = context.getRequest().getParameter("codice_fiscale");
			String sede = context.getRequest().getParameter("sede");
			String indirizzo = context.getRequest().getParameter("indirizzo");
			String rappresentanteLegale = context.getRequest().getParameter("rappresentante_legale");
			String telefonoFisso = context.getRequest().getParameter("telefono_fisso");
			String telefonoCellulare = context.getRequest().getParameter("telefono_mobile");
			String email = context.getRequest().getParameter("email");
			String pec = context.getRequest().getParameter("pec");
			
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, id);
			pst.setString(2, denominazione);
			pst.setString(3, codiceFiscale);
			pst.setString(4, sede);
			pst.setString(5, indirizzo);
			pst.setString(6, rappresentanteLegale);
			pst.setString(7, telefonoFisso);
			pst.setString(8, telefonoCellulare);
			pst.setString(9, email);
			pst.setString(10, pec);
			pst.setInt(11, getUserId(context));
			
			pst.execute();
			
			return executeCommandListaAssociazioni(context);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			freeConnection(context, db);
		}
		
		return "";
	}
	
	
	
	public String executeCommandInsertAssociazione(ActionContext context) 
	{
		if (!(hasPermission(context, "associazioni-add"))) 
			return ("PermissionError");
		
		Connection db = null;
		
		try
		{
			
			db = getConnection(context);
		
			String sql = "SELECT * FROM public_functions.insertAssociazione(?,?,?,?,?,?,?,?,?,?)";
			
			String denominazione = context.getRequest().getParameter("denominazione");
			String codiceFiscale = context.getRequest().getParameter("codice_fiscale");
			String sede = context.getRequest().getParameter("sede");
			String indirizzo = context.getRequest().getParameter("indirizzo");
			String rappresentanteLegale = context.getRequest().getParameter("rappresentante_legale");
			String telefonoFisso = context.getRequest().getParameter("telefono_fisso");
			String telefonoCellulare = context.getRequest().getParameter("telefono_mobile");
			String email = context.getRequest().getParameter("email");
			String pec = context.getRequest().getParameter("pec");
			
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setString(1, denominazione);
			pst.setString(2, codiceFiscale);
			pst.setString(3, sede);
			pst.setString(4, indirizzo);
			pst.setString(5, rappresentanteLegale);
			pst.setString(6, telefonoFisso);
			pst.setString(7, telefonoCellulare);
			pst.setString(8, email);
			pst.setString(9, pec);
			pst.setInt(10, getUserId(context));
			
			pst.execute();
			
			return executeCommandListaAssociazioni(context);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			freeConnection(context, db);
		}
		
		return "";
	}
	
	public String executeCommandDeleteAssociazione(ActionContext context) 
	{
		if (!(hasPermission(context, "associazioni-delete"))) 
			return ("PermissionError");
		
		Connection db = null;
		
		try
		{
			
			db = getConnection(context);
		
			String sql = "SELECT * FROM public_functions.deleteAssociazione(?,?)";
			
			int id = Integer.parseInt(context.getRequest().getParameter("id"));
			
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, id);
			pst.setInt(2, getUserId(context));
			
			pst.execute();
			
			return executeCommandListaAssociazioni(context);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			freeConnection(context, db);
		}
		
		return "";
	}
	
	
	public String executeCommandToInsertAssociazione(ActionContext context) 
	{
		if (!(hasPermission(context, "associazioni-add"))) 
			return ("PermissionError");
		
		return "AssociazioniInsertOK";
	}

	
	public String executeCommandListaAssociazioni(ActionContext context) 
	{
		if (!(hasPermission(context, "associazioni-view"))) 
			return ("PermissionError");
		
		Connection db = null;
		
		try
		{
			
			db = getConnection(context);
			PagedListInfo searchListInfo = this.getPagedListInfo(context, "associazioniListInfo");
			searchListInfo.setLink("MyCFS.do?command=ListaAssociazioni");
			searchListInfo.setListView("all");
		
			String sql = "SELECT * FROM public_functions.getListaAssociazioni_v1()";
			Statement pst = db.createStatement();
			
			ResultSet rs = pst.executeQuery(sql);
			
			JSONObject associazioni = new JSONObject();
			
			while(rs.next())
			{
				JSONObject gson = new JSONObject();
				
				gson.put("id", rs.getInt("id"));
				gson.put("progressivo", rs.getString("progressivo"));
				gson.put("denominazione", rs.getString("denominazione"));
				gson.put("codice_fiscale", (rs.getString("codice_fiscale")!=null)?(rs.getString("codice_fiscale")):(""));
				gson.put("sede", rs.getString("sede"));
				gson.put("indirizzo", (rs.getString("indirizzo")!=null)?(rs.getString("indirizzo")):(""));
				gson.put("rappresentante_legale", (rs.getString("rappresentante_legale")!=null)?(rs.getString("rappresentante_legale")):(""));
				gson.put("telefono_fisso", (rs.getString("telefono_fisso")!=null)?(rs.getString("telefono_fisso")):(""));
				gson.put("telefono_cellulare", (rs.getString("telefono_cellulare")!=null)?(rs.getString("telefono_cellulare")):(""));
				gson.put("mail", (rs.getString("mail")!=null)?(rs.getString("mail")):(""));
				gson.put("pec", (rs.getString("pec")!=null)?(rs.getString("pec")):(""));
				
				associazioni.put(gson.getString("id"), gson);
			}
		
			context.getRequest().setAttribute("associazioni", associazioni);

			return "AssociazioniListOK";
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			freeConnection(context, db);
		}
		
		return "";
	}
	
	
	
	public String executeCommandToEditAssociazione(ActionContext context) 
	{
		if (!(hasPermission(context, "associazioni-edit"))) 
			return ("PermissionError");
		
		Connection db = null;
		
		try
		{
			
			db = getConnection(context);
			
			int id = Integer.parseInt(context.getRequest().getParameter("id"));
			
			String sql = "SELECT * FROM public_functions.getListaAssociazioni_v1(?)";
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setInt(1, id);
			
			ResultSet rs = pst.executeQuery();
			
			JSONObject associazione = new JSONObject();
			
			if(rs.next())
			{
				associazione.put("id", rs.getInt("id"));
				associazione.put("denominazione", rs.getString("denominazione"));
				associazione.put("codice_fiscale", (rs.getString("codice_fiscale")!=null)?(rs.getString("codice_fiscale")):(""));
				associazione.put("sede", rs.getString("sede"));
				associazione.put("indirizzo", (rs.getString("indirizzo")!=null)?(rs.getString("indirizzo")):(""));
				associazione.put("rappresentante_legale", (rs.getString("rappresentante_legale")!=null)?(rs.getString("rappresentante_legale")):(""));
				associazione.put("telefono_fisso", (rs.getString("telefono_fisso")!=null)?(rs.getString("telefono_fisso")):(""));
				associazione.put("telefono_cellulare", (rs.getString("telefono_cellulare")!=null)?(rs.getString("telefono_cellulare")):(""));
				associazione.put("mail", (rs.getString("mail")!=null)?(rs.getString("mail")):(""));
				associazione.put("pec", (rs.getString("pec")!=null)?(rs.getString("pec")):(""));
			}
		
			context.getRequest().setAttribute("associazione", associazione);

			return "AssociazioniEditOK";
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			freeConnection(context, db);
		}
		
		return "";
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
				mail.setSogg(" [[ SPEED TEST - BDU ]] ");

				mail.setRispondiA(getPref(context, "EMAILADDRESS"));
				

				mail.setTesto(context.getParameter("body")+"<br/>Username : "+thisRecord.getUsername()+"<br/>Cognome : "+thisRecord.getContact().getNameLast()+"<br/>Asl : "+thisRecord.getSiteIdName());
				

				
				mail.sendMail();
			
//				mail.setHost(getPref(context, "MAILSERVER"));
//				mail.setFrom(getPref(context, "EMAILADDRESS"));
//				mail.setUser(getPref(context, "EMAILADDRESS"));
//				mail.setPass(getPref(context, "MAILPASSWORD"));
//				mail.setPort(Integer.parseInt(getPref(context, "PORTSERVER")));
//				mail.setTo(getPref(context, "EMAILADDRESS"));
//				mail.setSogg(" [[ SPEED TEST - BDU ]] ");
//				mail.setTesto(context.getParameter("body")+"<br/>Username : "+thisRecord.getUsername()+"<br/>Cognome : "+thisRecord.getContact().getNameLast()+"<br/>Asl : "+thisRecord.getSiteIdName());
//				mail.sendMail();
			
				

			
			context.getSession().removeAttribute("DepartmentList");
			context.getSession().removeAttribute("ProjectListSelect");
		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
			context.getRequest().setAttribute("Error", errorMessage);
			
			context.getRequest().setAttribute("Error", errorMessage.getMessage());
			
		}  
		return "-none-";
	}
	
	
	 public String executeCommandToRicercaProprietarioLP(ActionContext context) 
	 {
		if (!(hasPermission(context, "myhomepage_ricerca_proprietario_lp-view"))) 
		{
			return ("PermissionError");
		}
			
		return ("ToRicercaProprietarioLPOK");
	 }
	 
	 public String executeCommandRicercaProprietarioLP(ActionContext context) 
	 {
		if (!(hasPermission(context, "myhomepage_ricerca_proprietario_lp-view"))) 
		{
			return ("PermissionError");
		}
		
		String mc = context.getRequest().getParameter("mc");
		Animale thisAnimale = null;
		
		ResultSet rs = null;
		PreparedStatement pst = null;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();
		
		Connection db = null;
		try 
		{
			
			Thread t = Thread.currentThread();
			db = this.getConnection(context);

			if (sqlFilter == null) 
			{
				sqlFilter = new StringBuffer();
			}
			
			sqlFilter.append("and (a.microchip = ? or a.tatuaggio =?)");
			sqlFilter.append(" AND a.data_cancellazione is NULL and a.trashed_date is NULL ");

			sqlSelect.append(" SELECT ");
			sqlSelect.append(" distinct a.* ");

			sqlSelect.append(" from animale a " );
			sqlSelect.append(" WHERE a.id >= 0 ");

			pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
			
			pst.setString(1, mc);
			pst.setString(2, mc);

			rs = pst.executeQuery();

			if (rs.next()) 
			{
				thisAnimale = new Animale(rs);
				Operatore proprietario = null;
				Operatore detentore = null;

				if (rs.getInt("id_proprietario") > -1) 
				{
					proprietario = new Operatore();
					proprietario.queryRecordOperatorebyIdLineaProduttiva(db, rs.getInt("id_proprietario"));
				}
				thisAnimale.setProprietario(proprietario);

				if (rs.getInt("id_detentore") > -1) 
				{
					detentore = new Operatore();
					detentore.queryRecordOperatorebyIdLineaProduttiva(db, rs.getInt("id_detentore"));
				}

				thisAnimale.setDetentore(detentore);
				
				LookupList statoList = new LookupList(db, "lookup_tipologia_stato");
				statoList.addItem(-1, "--Tutti--");
				context.getRequest().setAttribute("statoList", statoList);
				
				context.getRequest().setAttribute("thisAnimale", thisAnimale);
				
				LookupList siteList = new LookupList(db, "lookup_asl_rif");
				siteList.addItem(-1, "-- SELEZIONA VOCE --");
				context.getRequest().setAttribute("SiteList", siteList);
				
				LookupList specieList = new LookupList();
				specieList.setTable("lookup_specie");
				specieList.buildList(db);
				context.getRequest().setAttribute("specieList", specieList);

				LookupList razzaList = new LookupList();
				razzaList.setTable("lookup_razza");
				razzaList.setIdSpecie(thisAnimale.getIdSpecie());
				razzaList.buildList(db);
				context.getRequest().setAttribute("razzaList", razzaList);
				
				LookupList mantelloList = new LookupList();
				mantelloList.setTable("lookup_mantello");
				mantelloList.setIdSpecie(thisAnimale.getIdSpecie());
				mantelloList.buildList(db);
				context.getRequest().setAttribute("mantelloList", mantelloList);
				
				LookupList tagliaList = new LookupList(db, "lookup_taglia");
				context.getRequest().setAttribute("tagliaList", tagliaList);
	
				return ("RicercaProprietarioLPOK");
	
			}
			else
			{
				context.getRequest().setAttribute("errore", "Microchip \"" + mc + "\" non trovato");
				return ("ToRicercaProprietarioLPOK");
			}
			
			
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			context.getRequest().setAttribute("Error", e);
			return ("SystemError");
		} 
		finally 
		{
			this.freeConnection(context, db);
		}
	 }
	 
	 public String executeCommandEstrazioneAnimaliAnagrafatiSenzaOrigine(ActionContext context) 
	 {
			if (!(hasPermission(context, "estrazione_animali_anagrafati_senza_origine-view"))) 
			{
				return ("PermissionError");
			}
			int countRegolarizzati = 0;
			
			Connection db = null;
			
			try{
				
			db = getConnection(context);
			PreparedStatement stat = db.prepareStatement("select * from get_animali_anagrafati_senza_origine_per_utente() where totale >0");
			ResultSet rs = stat.executeQuery();
			ArrayList<ArrayList<String>> lista = new ArrayList<ArrayList<String>>();
			while(rs.next())
			{
				ArrayList<String> record = new ArrayList<String>();
				for(int i=1;i<=15 ;i++)
					record.add(rs.getString(i));
				lista.add(record);
			}
			
			
			
			
			context.getRequest().setAttribute("lista", lista);

			return "EstrazioneAnimaliAnagrafatiSenzaOrigineOK";
			}catch (Exception e) {
				freeConnection(context, db);
			}
			
			return "";
		}
	


 
}

