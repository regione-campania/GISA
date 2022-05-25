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
  - Version: $Id: accounts_history.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.canili.base.*,org.aspcfs.modules.contacts.base.*" %>
<%@ page import="org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.canili.base.Organization" scope="request"/>
<jsp:useBean id="historyList" class="org.aspcfs.modules.canili.base.OrganizationHistoryList" scope="request"/>
<jsp:useBean id="orgHistoryListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_history_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/executeFunction.js"></script>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  
  
  function checkForm(form) {
    return true;
  }
  

  function setChecked(val,chkName,thisForm) {
    var frm = document.forms[thisForm];
    var len = document.forms[thisForm].elements.length;
    var i=0;
    for( i=0 ; i<len ; i++) {
      if (frm.elements[i].name.indexOf(chkName)!=-1) {
        frm.elements[i].checked=val;
      }
    }
  }
  
  function reopen() {
    window.location.href='CaniliHistory.do?command=View&orgId=<%= OrgDetails.getOrgId() + (isPopup(request)?"&popup=true":"") %>';
  }
</script>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Canili.do">Canili</a> > 
<a href="Canili.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Canili.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Scheda Canile</a> >
<dhv:label name="reports.helpdesk.ticket.history.history">History</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="canili" selected="history" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
<dhv:evaluate if="<%= OrgDetails.getTrashedDate() == null %>"><dhv:permission name="canili-history-add">
<a href=" javascript:popURL('CaniliHistory.do?command=AddNote&orgId=<%= OrgDetails.getOrgId() %>','Note','575','200','yes','yes');" ><dhv:label name="accounts.accountHistory.addANote">Add a Note</dhv:label></a><br /><br />
</dhv:permission></dhv:evaluate>

  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      <th width="8" nowrap>&nbsp;</th>
         <th width="100%">
        <strong><dhv:label name="reports.helpdesk.ticket.maintenance.partDescription">Description</dhv:label></strong>
      </th>
      <th nowrap>
        <strong><a href="CaniliHistory.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=c.namelast"><dhv:label name="">Inserito da</dhv:label></a></strong>
        <%= orgHistoryListInfo.getSortIcon("history.enteredby") %>
      </th>
      <th nowrap>
        <strong><a href="CaniliHistory.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=history.entered"><dhv:label name="accounts.accounts_calls_list.Entered">Entered</dhv:label></a></strong>
        <%= orgHistoryListInfo.getSortIcon("history.entered") %>
      </th>
      <th nowrap>
        <strong><a href="CaniliHistory.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=c.namelast"><dhv:label name="">Modificato da</dhv:label></a></strong>
        <%= orgHistoryListInfo.getSortIcon("history.enteredby") %>
      </th>
      <th nowrap>
        <strong><a href="CaniliHistory.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=history.modified"><dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label></a></strong>
        <%= orgHistoryListInfo.getSortIcon("history.modified") %>
      </th>
    </tr>
<%
	Iterator j = historyList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
      i++;
		  rowid = (rowid != 1?1:2);
      OrganizationHistory thisHistoryElement = (OrganizationHistory) j.next();
      String modify = thisHistoryElement.getPermission(true);
      String view = thisHistoryElement.getPermission(false);
      String delete = thisHistoryElement.getDeletePermission();
      String historyPermission = thisHistoryElement.getViewOrModifyOrDeletePermission();
      boolean canView = false;
      boolean canModify = false;
      boolean canDelete = false;
%>
		<dhv:permission name="<%= historyPermission %>">
		<tr class="row<%= rowid %>">
      <td valign="top">
        
          <dhv:permission name="<%= view %>">
            <% canView = true; %>
          </dhv:permission>
          <dhv:permission name="<%= modify %>">
            <% if (thisHistoryElement.getEnabled()) {canModify = true;} %>
          </dhv:permission>
          <dhv:permission name="<%= delete %>">
            <% canDelete = true; %>
          </dhv:permission>
          <a href="javascript:displayMenu('select<%= i %>','menuAccountHistory', '<%= thisHistoryElement.getId() %>', '<%= thisHistoryElement.getOrgId() %>','<%= thisHistoryElement.getContactId() %>', '<%= thisHistoryElement.getLinkObjectId() %>', '<%= thisHistoryElement.getLinkItemId() %>', <%= canView %>, <%= canModify %>, <%= canDelete %>);"
          onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuAccountHistory');">
          <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0" /></a>
       
        
      </td>
      
      <td valign="top" width="100%">
        <dhv:evaluate if="<%= !thisHistoryElement.getEnabled() %>"><font color="red"><%= toHtml(thisHistoryElement.getDescription()) %></font></dhv:evaluate>
        <dhv:evaluate if="<%= thisHistoryElement.getEnabled() %>"><%= toHtml(thisHistoryElement.getDescription()) %></dhv:evaluate>
      </td>
      <td valign="top">
        <dhv:evaluate if="<%= thisHistoryElement.getEnteredBy() != -1 %>">
          <dhv:username id="<%= thisHistoryElement.getEnteredBy() %>" />
        </dhv:evaluate>
        <dhv:evaluate if="<%= thisHistoryElement.getEnteredBy() == -1 %>">
          &nbsp;
        </dhv:evaluate>
      </td>
      <td valign="top" nowrap>
        <dhv:evaluate if="<%= !thisHistoryElement.getEnabled() %>"><font color="red"><zeroio:tz timestamp="<%= thisHistoryElement.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /></font></dhv:evaluate>
        <dhv:evaluate if="<%= thisHistoryElement.getEnabled() %>"><zeroio:tz timestamp="<%= thisHistoryElement.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /></dhv:evaluate>
        <%-- <zeroio:tz timestamp="<%= thisHistoryElement.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /> --%>
      </td>
      <td valign="top">
        <dhv:evaluate if="<%= thisHistoryElement.getModifiedBy() != -1 %>">
          <dhv:username id="<%= thisHistoryElement.getModifiedBy() %>" />
        </dhv:evaluate>
        <dhv:evaluate if="<%= thisHistoryElement.getModifiedBy() == -1 %>">
          &nbsp;
        </dhv:evaluate>
      </td>
      <td valign="top" nowrap>
        <dhv:evaluate if="<%= !thisHistoryElement.getEnabled() %>"><font color="red"><zeroio:tz timestamp="<%= thisHistoryElement.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /></font></dhv:evaluate>
        <dhv:evaluate if="<%= thisHistoryElement.getEnabled() %>"><zeroio:tz timestamp="<%= thisHistoryElement.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /></dhv:evaluate>
        <%-- <zeroio:tz timestamp="<%= thisHistoryElement.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /> --%>
      </td>
      
		</tr></dhv:permission>
<%}%>
<%} else {%>
		<tr class="containerBody">
      <td colspan="6">
        <dhv:label name="">Non ci sono note.</dhv:label>
      </td>
    </tr>
<%}%>
	</table>
	<br />
 
</dhv:container>
