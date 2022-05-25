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
  - Version: $Id: calendar_ticket_events_due_today_include.jsp 11310 2005-04-13 20:05:00Z mrajkowski $
  - Description: 
  --%>
<%-- draws the ticket events for a specific day --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.troubletickets.base.Ticket,org.aspcfs.modules.base.Constants" %>
<%
  TicketEventList ticketEventList = (TicketEventList) thisDay.get(category);
%>

<%-- include tickets --%>
<dhv:evaluate if="<%= ticketEventList.getOpenTickets().size() > 0 %>">
<table border="0" id="ticketsdetails<%= toFullDateString(thisDay.getDate()) %>" width="100%">
  <%-- title row --%>
  <tr>
    <td>&nbsp;</td>
    <td colspan="3" nowrap class="eventName">
      <img border="0" src="images/tree0.gif" align="absmiddle" title="Tickets" />
      <dhv:label name="tickets.tickets">Tickets</dhv:label>
      (<%= ticketEventList.getOpenTickets().size() %>)
    </td>
  </tr>
  <%-- include ticket details --%>
  <%
    Iterator j = ticketEventList.getOpenTickets().iterator();
    if(j.hasNext()){
  %>
    <tr>
      <th>
        &nbsp;
      </th>
      <th class="weekSelector" nowrap>
        <strong><dhv:label name="tickets.symbol.number" param="number=">Ticket #</dhv:label></strong>
      </th>
      <th class="weekSelector" nowrap>
        <strong><dhv:label name="accounts.accounts_contacts_detailsimport.Company">Company</dhv:label></strong>
      </th>
      <th class="weekSelector" width="100%">
        <strong><dhv:label name="accounts.accounts_asset_history.Issue">Issue</dhv:label></strong>
      </th>
    </tr>
  <%  
      while(j.hasNext()){
      Ticket thisTicket = (Ticket) j.next();
      menuCount++;
    %>
    <tr>
     <td valign="top">
       <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayTicketMenu('select-arrow<%= menuCount %>','menuTicket','<%=  thisTicket.getId() %>');"
       onMouseOver="over(0, <%= menuCount %>)" onmouseout="out(0, <%= menuCount %>);hideMenu('menuTicket');"><img
       src="images/select-arrow.gif" name="select-arrow<%= menuCount %>" id="select-arrow<%= menuCount %>" align="absmiddle" border="0" /></a>
     </td>
     <td nowrap valign="top">
       <%= thisTicket.getPaddedId() %>
     </td>
     <td nowrap valign="top">
       <%= thisTicket.getCompanyName() %>
     </td>
     <td valign="top">
       <%= StringUtils.trimToSizeNoDots(toString(thisTicket.getProblem()), 30) %>
     </td>
    </tr>
   <% }
   } %>
</table>
</dhv:evaluate>

