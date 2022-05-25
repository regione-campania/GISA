/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.ProjectList;

import org.apache.batik.dom.util.HashTable;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.RoleList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.FilterList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Creates a Contacts List with 5 filters & subfilters . Can be used in two
 * variants : Single/Multiple<br>
 * Single and mutiple define if the selection can be single/multiple
 *
 * @author akhi_m
 * @version $Id: ContactsList.java,v 1.16.66.1 2004/01/23 15:33:26 ananth Exp
 *          $
 * @created September 5, 2002
 */
public final class ContactsList extends CFSModule {

	/**
	 * Description of the Method
	 *
	 * @param context Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String executeCommandContactList(ActionContext context) {
		Connection db = null;
		ContactList contactList = null;
		boolean listDone = false;
		try {
			db = this.getConnection(context);



			ArrayList<Role> listaRuoli = new ArrayList<Role>();
			PreparedStatement pst1 = db.prepareStatement("select * from (select role.role_id as code, role.role as description from role where role.enabled union select role_ext.role_id as code, role_ext.role as description  from role_ext where role_ext.enabled)a order by description");
			ResultSet rs = pst1.executeQuery();
			while(rs.next())
			{
				int ruoloId = rs.getInt(1);
				String descrizione = rs.getString(2);
				Role ruolo = new Role();
				ruolo.setDescription(descrizione);
				ruolo.setId(ruoloId);
				listaRuoli.add(ruolo);
			}
			

			context.getRequest().setAttribute("listaRuoli", listaRuoli);
			LookupList listaAsl = new LookupList(db, "lookup_site_id");
			listaAsl.addItem(
					-1, "--Nessuna Asl--"); //All Departments
			context.getRequest().setAttribute("listaAsl", listaAsl);

			String lastname = context.getParameter("firstName");
			String firstname = context.getParameter("lastName");
			String[] ruoli = context.getRequest().getParameterValues("ruolo");
			String asl = context.getParameter("asl");

			SystemStatus system =  this.getSystemStatus(context);


			Hashtable listaUtenti = system.getUserList();
			
			contactList = new ContactList();
			
			Iterator<Integer> itKey =listaUtenti.keySet().iterator();
			while (itKey.hasNext())
			{
				User utente = (User)listaUtenti.get(itKey.next());
				utente.getContact().setRole(utente.getRole());
				if(firstname !=null && lastname != null && (!firstname.trim().equals("") ||  !lastname.trim().equals(""))
						)
				{
					if (!firstname.trim().equals(""))
					{
						if (utente.getContact().getNameFirst()!=null &&utente.getContact().getNameFirst().toLowerCase().contains(firstname))
						{
							if (!lastname.trim().equals(""))
							{
								if (utente.getContact().getNameLast()!= null && utente.getContact().getNameLast().toLowerCase().contains(lastname))
								{
									if (ruoli!=null && ruoli.length>0)
									{
										for(int i = 0 ; i<ruoli.length;i++)
										{
											if (ruoli[i].equals(utente.getRoleId()))
											{
												
												contactList.add(utente.getContact());

											}
										}
										
									}
									else
										
									contactList.add(utente.getContact());
								}
								
							}
							else
							{
								if (ruoli!=null && ruoli.length>0)
								{
									for(int i = 0 ; i<ruoli.length;i++)
									{
										if (ruoli[i].equals(utente.getRoleId()))
										{
											contactList.add(utente.getContact());

										}
									}
									
								}
								else
								contactList.add(utente.getContact());
							}
							
						}
						
					}
					else
					{
						if (!lastname.trim().equals(""))
						{
							if (utente.getContact().getNameLast() != null && utente.getContact().getNameLast().toLowerCase().contains(lastname))
							{
								if (!firstname.trim().equals(""))
								{
									if (utente.getContact().getNameFirst()!= null &&utente.getContact().getNameFirst().toLowerCase().contains(firstname))
									{
										if (ruoli!=null && ruoli.length>0)
										{
											for(int i = 0 ; i<ruoli.length;i++)
											{
												if (ruoli[i].equals(utente.getRoleId()))
												{
													contactList.add(utente.getContact());

												}
											}
										}
										else
										contactList.add(utente.getContact());
									}
									
								}
								else
								{
									if (ruoli!=null && ruoli.length>0)
									{
										for(int i = 0 ; i<ruoli.length;i++)
										{
											if (ruoli[i].equals(utente.getRoleId()))
											{
												contactList.add(utente.getContact());

											}
										}
									}
									else
									contactList.add(utente.getContact());
								}
								
							}
							
						}
						
					}
					
				}
				else
				{
					
					if (ruoli!=null && ruoli.length>0)
					{
						for(int i = 0 ; i<ruoli.length;i++)
						{
							if (ruoli[i].equals(utente.getRoleId()+""))
							{
								
								if (asl!= null && ! asl.equals("") && ! "-1".equals(asl))
								{
									
									if(asl.equals(utente.getSiteId()+""))
									{
										contactList.add(utente.getContact());
									}
									
									
								}
								else
								contactList.add(utente.getContact());

							}
						}
					}
					else
						if (asl!= null && ! asl.equals(""))
						{
							
							if(asl.equals(utente.getSiteId()+""))
							{
								contactList.add(utente.getContact());
							}
							
						}
						else
					contactList.add(utente.getContact());
				}
				
			}
			
			context.getRequest().setAttribute("ContactList", contactList);


			LookupList siteList = new LookupList(db, "lookup_site_id");
			listaAsl.addItem(
					-1, "--Nessuna Asl--"); //All Departments
			context.getRequest().setAttribute("SiteIdList", siteList);




		} catch (Exception e) {
			context.getRequest().setAttribute("Error", e);
			e.printStackTrace();
			return ("SystemError");
		} finally {
			this.freeConnection(context, db);
		}

		String ret = "CFSContactListDipendentiOK";

		return ret;
	}






	/**
	 * Sets the parameters attribute of the ContactsList object
	 *
	 * @param contactList The new parameters value
	 * @param context     The new parameters value
	 */
	private void setParameters(ContactList contactList, ActionContext context, Connection db) throws Exception {
		SystemStatus thisSystem = this.getSystemStatus(context);
		// search for a particular contact
		String defaultFirstName = thisSystem.getLabel(
				"accounts.accounts_add.FirstName");
		String defaultLastName = thisSystem.getLabel(
				"contacts.LastName");

		String firstName = context.getRequest().getParameter("searchnamefirst");
		String lastName = context.getRequest().getParameter("searchnamelast");
		String[] idRuoli = context.getRequest().getParameterValues("ruolo");
		String idAsl = context.getRequest().getParameter("asl");

		if (idRuoli != null) {
			if (idRuoli.length>0)
			{
				contactList.setListaRuoliFiltrare(idRuoli);
			}

		}




		if (firstName != null) {
			if (!defaultFirstName.equals(firstName) && !"".equals(firstName.trim())) {
				contactList.setFirstName("%" + firstName + "%");
			}
		}
		if (lastName != null) {
			if (!defaultLastName.equals(lastName) && !"".equals(lastName.trim())) {
				contactList.setLastName("%" + lastName + "%");
			}
		}
		if (context.getRequest().getParameter("reset") != null) {
			context.getSession().removeAttribute("ContactListInfo");
		}
		PagedListInfo contactListInfo = this.getPagedListInfo(
				context, "ContactListInfo");

		contactListInfo.setLink("ContactsList.do?command=ContactList");
		contactListInfo.setListView("all");

		//filter for departments & project teams
		if (!contactListInfo.hasListFilters()) {
			contactListInfo.addFilter(1, "0");
		}
		String leads = context.getRequest().getParameter("leads");
		if (leads != null && !"".equals(leads.trim())) {
			context.getRequest().setAttribute("leads", leads);
		}
		String tasks = context.getRequest().getParameter("tasks");
		if (tasks != null && !"".equals(tasks.trim())) {
			context.getRequest().setAttribute("tasks", tasks);
		}
		String recipient = context.getRequest().getParameter("recipient");
		if (recipient != null && !"".equals(recipient)) {
			context.getRequest().setAttribute("recipient", recipient);
		}
		String secondFilter = context.getRequest().getParameter("searchcoderole_id");
		String usersOnly = context.getRequest().getParameter("usersOnly");
		String hierarchy = context.getRequest().getParameter("hierarchy");
		String nonUsersOnly = context.getRequest().getParameter("nonUsersOnly");
		String orgId = context.getRequest().getParameter("orgId");
		String siteIdUser = context.getRequest().getParameter("siteIdUser");
		String siteIdContact = context.getRequest().getParameter("siteIdContact");
		String siteIdOrg = context.getRequest().getParameter("siteIdOrg");
		String mySiteOnly = context.getRequest().getParameter("mySiteOnly");
		String departmentId = context.getRequest().getParameter("departmentId");
		String siteIdString = context.getRequest().getParameter("siteId");
		String ticketIdString = context.getRequest().getParameter("ticketId");
		String includeAllSites = context.getRequest().getParameter("includeAllSites");
		String allowDuplicateRecipient = context.getRequest().getParameter("allowDuplicateRecipient");
		if (allowDuplicateRecipient != null && !"".equals(allowDuplicateRecipient.trim())) {
			context.getRequest().setAttribute("allowDuplicateRecipient", allowDuplicateRecipient);
		}
		String source = context.getRequest().getParameter("hiddensource");
		if (source != null && !"".equals(source.trim())) {
			context.getRequest().setAttribute("hiddensource", source);
		}
		String actionItemId = context.getRequest().getParameter("actionItemId");
		if (actionItemId != null && !"".equals(actionItemId.trim())) {
			context.getRequest().setAttribute("actionItemId", actionItemId);
		}
		int siteId = this.getUserSiteId(context);
		if (siteIdString != null && !"".equals(siteIdString.trim())) {
			siteId = Integer.parseInt(siteIdString);
		}
		if (siteIdUser != null && !"".equals(siteIdUser.trim()) && !"-1".equals(siteIdUser.trim())) {
			User user = this.getUser(context, Integer.parseInt(siteIdUser.trim()));
			siteId = user.getSiteId();
		}
		
		if (siteIdOrg != null && !"".equals(siteIdOrg.trim()) && !"-1".equals(siteIdOrg.trim())) {
			Organization org = new Organization(db, Integer.parseInt(siteIdOrg));
			siteId = org.getSiteId();
		}
		if ((ticketIdString != null) &&
				!"".equals(ticketIdString) &&
				!"null".equals(ticketIdString) &&
				!"-1".equals(ticketIdString)) {
			Ticket ticket = new Ticket(db, Integer.parseInt(ticketIdString));
			siteId = ticket.getOrgSiteId();
		}
		if (!isSiteAccessPermitted(context, String.valueOf(siteId))) {
			throw new SQLException("PermissionError");
		}
		/*
    if (user.getSiteId() != -1 && user.getSiteId() != siteId) {
      throw new SQLException("PermissionError");
    }
		 */
		//add filters
		FilterList filters = new FilterList(thisSystem, context.getRequest());
		context.getRequest().setAttribute("Filters", filters);

		//  set Filter for retrieving addresses depending on typeOfContact
		String firstFilter = filters.getFirstFilter(contactListInfo.getListView());
		if (firstFilter.equalsIgnoreCase("all") && !"dipendenti".equalsIgnoreCase( context.getParameter( "dipendenti" )) ) {
			contactList.addIgnoreTypeId(Contact.EMPLOYEE_TYPE);
			contactList.addIgnoreTypeId(Contact.LEAD_TYPE);
			contactList.setAllContacts(
					true, this.getUserId(context), this.getUserRange(context));
			contactList.setSiteId(siteId);
		}
		if (firstFilter.equalsIgnoreCase("employees") || "dipendenti".equalsIgnoreCase( context.getParameter( "dipendenti" )) ) {
			contactList.setEmployeesOnly(Constants.TRUE);
			if (secondFilter != null && !"".equals(secondFilter)) {

				contactList.setRole_id(secondFilter);
			}
			contactList.setSiteId(siteId);
		}
		if (firstFilter.equalsIgnoreCase("mycontacts")) {
			contactList.addIgnoreTypeId(Contact.EMPLOYEE_TYPE);
			contactList.addIgnoreTypeId(Contact.LEAD_TYPE);
			contactList.setOwner(this.getUserId(context));
			contactList.setSiteId(siteId);
			contactList.setPersonalId(this.getUserId(context));
		}
		if (firstFilter.equalsIgnoreCase("accountcontacts")) {
			contactList.setWithAccountsOnly(true);
			contactList.setSiteId(siteId);
		}
		if (firstFilter.equalsIgnoreCase("myprojects")) {
			contactList.setWithProjectsOnly(true);
			if (secondFilter != null && !"".equals(secondFilter)) {
				contactList.setProjectId(Integer.parseInt(secondFilter));
			}
			contactList.setSiteId(siteId);
		}
		contactListInfo.setListView(firstFilter);
		contactList.setPagedListInfo(contactListInfo);

		contactListInfo.setSearchCriteria(contactList, context);
		contactList.setCheckUserAccess(true);
		contactList.setBuildDetails(true);
		contactListInfo.setSearchCriteria(contactList, context);
		contactList.setBuildTypes(true);
		if ("true".equals(usersOnly)) {
			
			contactList.setIncludeEnabledUsersOnly(true);
			if (hierarchy != null && !"".equals(hierarchy)) {
				contactList.setHierarchialUsers(hierarchy);
			}
			contactList.setUserRoleType(Constants.ROLETYPE_REGULAR);
		}
		if ("true".equals(nonUsersOnly)) {
			contactList.setIncludeNonUsersOnly(true);
		}
		if (orgId != null && !"".equals(orgId.trim())) {
			contactList.setOrgId(Integer.parseInt(orgId));
		}
		if ("true".equals(usersOnly)) {
			if (departmentId != null && !"".equals(departmentId.trim()) && !"null".equals(departmentId.trim()))
			{
				context.getRequest().setAttribute("departmentId", departmentId);
				contactList.setDepartmentId(Integer.parseInt(departmentId));
			}
		}

		// Setting filter criterea for campaign groups based on
		// siteId of the organization associated with the account contacts.
		if ("true".equals(context.getRequest().getParameter("campaign")) &&
				firstFilter.equalsIgnoreCase("accountcontacts")) {
			contactList.setSiteId(siteId);
		}
		if (mySiteOnly != null && "true".equals(mySiteOnly)) {
			contactList.setExclusiveToSite(true);
		}
		if (includeAllSites != null && "true".equals(includeAllSites.trim())) {
			if (siteId == -1) {
				contactList.setIncludeAllSites(true);
			}
		}

		if (idAsl != null) {
			if (!"".equals(idAsl.trim())) {
				int aslId = Integer.parseInt(idAsl);
				if(aslId>-1)
					contactList.setSiteId(aslId);
			}
		}
		context.getRequest().setAttribute("includeAllSites", includeAllSites);
		context.getRequest().setAttribute("siteId", String.valueOf(siteId));
		context.getRequest().setAttribute("mySiteOnly", mySiteOnly);
	}
}

