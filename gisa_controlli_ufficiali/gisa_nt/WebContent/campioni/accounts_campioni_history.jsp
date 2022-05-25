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
  - Version: $Id: accounts_tickets_history.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.campioni.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.stabilimenti.base.Organization" scope="request"/>
<jsp:useBean id="OrgDetails2" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.campioni.base.Ticket" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script type="text/javascript" >
function reopen() {
    window.location.href='TroubleTicketsCampioni.do?command=ViewHistory&orgId=<%= TicketDetails.getOrgId()%>&id=<%=TicketDetails.getId()%>';
  }
  
  
<!--

//-->
</script>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTicketsCampioni.do"><dhv:label name="campioni">Help Desk</dhv:label></a> > 
<% if ("yes".equals((String)session.getAttribute("searchTickets"))) {%>
  <a href="TroubleTicketsCampioni.do?command=SearchTicketsForm"><dhv:label name="tickets.searchForm">Search Form</dhv:label></a> >
  <a href="TroubleTicketsCampioni.do?command=SearchTickets"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<%}else{%> 
  <a href="TroubleTicketsCampioni.do?command=Home"><dhv:label name="sanzionie.visualizza">Visualizza Campione</dhv:label></a> >
<%}%>
<a href="TroubleTicketsCampioni.do?command=Details&id=<%= TicketDetails.getId() %>"><dhv:label name="sanzioni.dettegli">Scheda Campione</dhv:label></a> >
<dhv:label name="accounts.accounts_documentsa_details.Documents">Storico</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
 
 
 <% String param1 = "id=" + TicketDetails.getId(); %>
<dhv:container name="campioni" selected="history" object="TicketDetails" param="<%= param1 %>">
  <%@ include file="ticket_header_include.jsp" %>
  

    <%-- @ include file="accounts_ticket_header_include.jsp" --%>
    <%if(OrgDetails.getTipologia()!=3) {%>
    <dhv:evaluate if="<%= OrgDetails2.getTrashedDate() == null && (!TicketDetails.getCloseIt()) %>"><dhv:permission name="campioni-campioni-history-add">
	<a href=" javascript:popURL('TroubleTicketsCampioni.do?command=AddNote&ticketId=<%=TicketDetails.getId() %>&orgId=<%= OrgDetails2.getOrgId() %>','Note','575','200','yes','yes');" ><dhv:label name="accounts.accountHistory.addANote">Add a Note</dhv:label></a><br /><br />
</dhv:permission></dhv:evaluate>
<%}else{ %>
<dhv:evaluate if="<%= OrgDetails.getTrashedDate() == null && (!TicketDetails.getCloseIt()) %>"><dhv:permission name="campioni-campioni-history-add">
	<a href=" javascript:popURL('TroubleTicketsCampioni.do?command=AddNote&ticketId=<%=TicketDetails.getId() %>&orgId=<%= OrgDetails.getOrgId() %>','Note','575','200','yes','yes');" ><dhv:label name="accounts.accountHistory.addANote">Add a Note</dhv:label></a><br /><br />
</dhv:permission></dhv:evaluate>
<%} %>
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    
      <tr>
        <th colspan="4">
          <strong><dhv:label name="aaccounts.tickets.historyLog">Storia Campione</dhv:label></strong>
        </th>
      </tr>
    <%
        Iterator hist = TicketDetails.getHistory().iterator();
        if (hist.hasNext()) {
          while (hist.hasNext()) {
            TicketLog thisEntry = (TicketLog)hist.next();
    %>
    <% if (thisEntry.getSystemMessage() == true) {%>
      <tr class="row1">
    <% } else { %>
      <tr class="containerBody">
    <%}%>
        <td nowrap valign="top" class="formLabel">
          <dhv:username id="<%= thisEntry.getEnteredBy() %>"/>
        </td>
        <td nowrap valign="top">
          <zeroio:tz timestamp="<%= thisEntry.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
        </td>
        <td valign="top" width="100%">
          <%= toHtml(thisEntry.getEntryText()) %>
        </td>
      </tr>
    <%
        }
      } else {
    %>
      <tr class="containerBody">
        <td>
          <font color="#9E9E9E" colspan="3"><dhv:label name="project.tickets.noLogEntries">No Log Entries.</dhv:label></font>
        </td>
      </tr>
    <%}%>
    </table>
</dhv:container>
