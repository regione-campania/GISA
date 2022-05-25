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
  - Version: $Id: ticket_header_include.jsp 21501 2007-05-21 20:36:25Z matt $
  - Description:
  --%>
<%@ page import="org.aspcfs.modules.base.Constants, org.aspcfs.modules.cessazionevariazione.base.Ticket" %>
<%
  Ticket thisTicket = (Ticket)request.getAttribute("TicketDetails");
  if (thisTicket == null) {
    thisTicket = (Ticket)request.getAttribute("ticketDetails");
  }
  if (thisTicket == null) {
    thisTicket = (Ticket) request.getAttribute("ticket");
  }
%>
<table width="100%" border="0">
  <tr>
    <td nowrap width="100%"> <table width="100%" class="empty">
       
        <tr><td nowrap colspan="2" align="left">
          <strong><dhv:label name="">Operatore sottoposta a cessazione: </dhv:label></strong>
          <dhv:permission name="accounts-accounts-view">
            <a href="javascript:popURL('Accounts.do?command=Details&orgId=<%= thisTicket.getOrgId() %>&ticketId=<%=thisTicket.getId() %>&popup=true&viewOnly=true','AccountDetails','650','500','yes','yes');"><%= toHtml(thisTicket.getCompanyName()) %></a>
          </dhv:permission>
          <dhv:permission name="accounts-accounts-view" none="true">
            <%= toHtml(thisTicket.getCompanyName()) %>
          </dhv:permission>
        </td></tr>
      </table>
    </td>
   
  </tr>
  <tr>

  </tr>
</table>
<br />
