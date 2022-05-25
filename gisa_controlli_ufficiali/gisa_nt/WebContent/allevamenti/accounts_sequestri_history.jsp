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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.troubletickets.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.allevamenti.base.Organization" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.sequestri.base.Ticket" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script type="text/javascript" >
function reopen() {
    window.location.href='AllevamentiSequestri.do?command=ViewHistory&orgId=812&id=<%=TicketDetails.getId()%>';
  }
  
  
<!--

//-->
</script>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Allevamenti.do"><dhv:label name="">Allevamenti</dhv:label></a> > 
<a href="Allevamenti.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Allevamenti.do?command=Details&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Scheda Allevamento </dhv:label></a> >
<a href="Allevamenti.do?command=ViewSequestri&orgId=<%=TicketDetails.getOrgId() %>"><dhv:label name="sequestri">Help Desk</dhv:label></a> >
<a href="AllevamentiSequestri.do?command=TicketDetails&orgId=<%=TicketDetails.getOrgId() %>&id=<%=TicketDetails.getId() %>"><dhv:label name="sequestris">Scheda Sequestro/Blocco</dhv:label></a> >
<dhv:label name="documentis">Storia</dhv:label> 
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="allevamenti" selected="sequestri" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:container name="allevamentisequestri" selected="history" object="TicketDetails" param='<%= "id=" + TicketDetails.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
    <%-- @ include file="accounts_ticket_header_include.jsp" --%>
    <!-- aggiunto per le note -->
    <dhv:evaluate if="<%= ( OrgDetails.getTrashedDate() == null ) && (!TicketDetails.getCloseIt()) %>">
    <a href=" javascript:popURL('TroubleTicketsSequestri.do?command=AddNote&orgId=<%= OrgDetails.getOrgId() %>&ticketId=<%=TicketDetails.getId() %>','Note','575','200','yes','yes');" ><dhv:label name="accounts.accountHistory.addANote">Add a Note</dhv:label></a><br /><br />
   </dhv:evaluate>
    <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
      <tr>
        <th colspan="4">
          <strong><dhv:label name="accounts.ticketss.historyLogs">Storico Log Sequestro</dhv:label></strong>
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
        <% if(thisEntry.getEntryText().equals("[ Richiesta Aperta ]")) {%>
          [ Sequestro Aperto ]
          <%}else if(thisEntry.getEntryText().equals("[ Richiesta Chiusa ]")){%>
         [ Sequestro Chiuso ]
          <%} else{%>
           <%= toHtml(thisEntry.getEntryText()) %> 
		<%} %>
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
</dhv:container>
