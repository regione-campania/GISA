<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: accounts_search_results.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.*" %>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_contacts_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  
  
  function reopen() {
    scrollReload('Requestor.do?command=Search');
  }
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Requestor.do"><dhv:label name="requestor.requestor">Accounts</dhv:label></a> > 
<dhv:label name="requestor.SearchResults">Search Results</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="requestor-requestor-add"><a href="Requestor.do?command=Add">Aggiungi una richiesta D.I.A.</a></dhv:permission>
<dhv:permission name="requestor-requestor-add" none="true"><br></dhv:permission>
<center><%= SearchOrgListInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchOrgListInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
<tr>
  <th width="8">
      &nbsp;
  </th>
  <th width="30%" nowrap="true">
    <strong><a href="Requestor.do?command=Search&column=c.namelast">Name</a></strong>
    <%= SearchOrgListInfo.getSortIcon("c.namelast") %>
  </th>
  <th width="10%" >
    <strong><dhv:label name="requestor.requestor_contacts_add.Title">Title</dhv:label></strong>
  </th>   
  <th width="20%" nowrap="true">
    <strong><a href="Requestor.do?command=Search&column=c.org_name"><dhv:label name="organization.name">Account Name</dhv:label></a></strong>
    <%= SearchOrgListInfo.getSortIcon("c.org_name") %>
  </th>
  
   
  <dhv:evaluate if='<%= SearchOrgListInfo.getSearchOptionValue("searchcodeSiteId") == String.valueOf(Constants.INVALID_SITE) %>'>
 
  <th width="10%">
    <strong><dhv:label name="requestor.site">Site</dhv:label></strong>
  </th>
  
  </dhv:evaluate>
  
 
 
  
  
  
  <th width="30%">
        <strong><dhv:label name="requestor.phoneFax">Phone/Fax</dhv:label></strong>
  </th>
  <th>
        <strong><dhv:label name="requestor.requestor_add.Email">Email</dhv:label></strong>
  </th>
</tr>
<%
	Iterator j = ContactList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
      i++;
		  rowid = (rowid != 1?1:2);
      Contact thisContact = (Contact)j.next();
%>      
		<tr class="row<%= rowid %>">
      <td valign="center" nowrap>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
         <a href="javascript:displayMenu('select<%= i %>','menuContact', '<%= thisContact.getOrgId() %>', '<%= thisContact.getId() %>', '<%= thisContact.getPrimaryContact() %>','<%= (((thisContact.getTrashedDate() != null) || !thisContact.getEnabled()) || ((thisContact.getOrgTrashedDate() != null) || !thisContact.getOrgEnabled()))%>');"
         onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>)"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
      </td>
      <td valign="center">
        <a href="ContactsReq.do?command=Details&id=<%=thisContact.getId()%>"><%= toHtml(thisContact.getNameLastFirst()) %></a>
      </td>
      <td valign="center">
        <%= toHtml(thisContact.getTitle()) %>
      </td>
      <td valign="center" nowrap>
        <a href="Requestor.do?command=Details&orgId=<%= thisContact.getOrgId() %>"><%= toHtml(thisContact.getOrgName()) %></a>
      </td>
        
      <dhv:evaluate if='<%= SearchOrgListInfo.getSearchOptionValue("searchcodeSiteId") == String.valueOf(Constants.INVALID_SITE) %>'>
        <td valign="top"><%= SiteIdList.getSelectedValue(thisContact.getSiteId()) %></td>
      </dhv:evaluate>
    
      <td valign="center" nowrap>
        <% if (thisContact.getPhoneNumberList().size() > 1) { %>
            <%= thisContact.getPhoneNumberList().getHtmlSelect("contactphone", -1) %>
        <% } else if (thisContact.getPhoneNumberList().size() == 1) { 
             PhoneNumber thisNumber = (PhoneNumber) thisContact.getPhoneNumberList().get(0);
         %>
             <%= String.valueOf(thisNumber.getTypeName().charAt(0)) + ":" + toHtml(thisNumber.getNumber()) %>
        <%}%>
        &nbsp;
      </td>
      <td valign="center" nowrap>
        <% if (thisContact.getEmailAddressList().size() > 1) { %>
            <%= thisContact.getEmailAddressList().getHtmlSelect("contactemail", -1) %>
        <% } else if (thisContact.getEmailAddressList().size() == 1) { 
             EmailAddress thisAddress = (EmailAddress) thisContact.getEmailAddressList().get(0);
         %>
             <%= String.valueOf(thisAddress.getTypeName().charAt(0)) + ":" + toHtml(thisAddress.getEmail()) %>
        <%}%>
        &nbsp;
      </td>
		</tr>
<%}%>
<%} else {%>
		<tr class="containerBody">
      <td colspan="<%= SearchOrgListInfo.getSearchOptionValue("searchcodeSiteId") == String.valueOf(Constants.INVALID_SITE)?"7":"6" %>">
        No contacts found.
      </td>
    </tr>
<%}%>
	</table>
<br>
<dhv:pagedListControl object="SearchOrgListInfo" tdClass="row1"/>
