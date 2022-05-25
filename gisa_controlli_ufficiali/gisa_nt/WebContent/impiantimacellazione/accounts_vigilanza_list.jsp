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
  - Version: $Id: accounts_tickets_list.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.impiantimacellazione.base.*,org.aspcfs.modules.vigilanza.base.Ticket,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.impiantimacellazione.base.Organization" scope="request"/>
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EsitoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TicList" class="org.aspcfs.modules.vigilanza.base.TicketList" scope="request"/>
<jsp:useBean id="AccountTicketInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_vigilanza_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  
</script>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ImpiantiMacellazione.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
<a href="ImpiantiMacellazione.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="ImpiantiMacellazione.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
<dhv:label name="vigilanza">Controlli Ufficiali</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="impiantiMacellazione" selected="vigilanza" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:evaluate if="<%= !OrgDetails.isTrashed() %>">
    <dhv:permission name="impiantiMacellazione-impiantiMacellazione-vigilanza-add"><a href="AccountVigilanza.do?command=Add&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.richiesta.add">Aggiungi Nuovo Controllo Ufficiale</dhv:label></a></dhv:permission>
  </dhv:evaluate>
    <input type=hidden name="orgId" value="<%= OrgDetails.getOrgId() %>">
    <br>
  <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="AccountTicketInfo"/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <%-- tr>
      <th>
        &nbsp;
      </th>
      <th width="30%" valign="center" align="left">
        <strong>Numero</strong>
      </th>
      <th width="20%" nowrap>
        <b><strong><a href="ImpiantiMacellazione.do?command=ViewTickets&orgId=<%= OrgDetails.getOrgId() %>&column=pri_code<%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.accounts_contacts_calls_details_followup_include.Priority">Priority</dhv:label></a></strong></b>
        <%= AccountTicketInfo.getSortIcon("pri_code") %>
      </th>
      <th width="15%">
        <b><dhv:label name="ticket.estResolutionDate">Est. Resolution Date</dhv:label></b>
      </th>
      <th width="15%">
        <b><dhv:label name="ticket.age">Age</dhv:label></b>
      </th>
      <th width="20%">
        <b><dhv:label name="accounts.accounts_contacts_calls_list.AssignedTo">Assigned To</dhv:label></b>
      </th>
      <th width="20%">
        <b><dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label></b>
      </th>
      </tr --%>
        <tr>
		<th width="8">
      &nbsp;
    </th>
    <th valign="center" align="left">
      <strong><dhv:label name="quotes.number">Number</dhv:label></strong>
    </th>
     <th><b><dhv:label name="sanzionia.data_richiesta">Data</dhv:label></b></th>
    <%-- th><b><dhv:label name="accounts.accounts_contacts_calls_details_followup_include.Priority">Priority</dhv:label></b></th --%>
    <%-- th><b><dhv:label name="ticket.estResolutionDate">Est. Resolution Date</dhv:label></b></th --%>
    <th><b><dhv:label name="sanzionia.richiedente">Tipo di controllo</dhv:label></b></th>
     <%-- %>th><b><dhv:label name="sanzionia.richiedente">Esito Controllo</dhv:label></b></th--%>
    <th><b><dhv:label name="sanzionia.richiedente">Modificato</dhv:label></b></th>
	<%-- th><b><dhv:label name="project.resourceAssigned">Resource Assigned</dhv:label></b></th --%>
  </tr>
  <%
    Iterator j = TicList.iterator();
    if ( j.hasNext() ) {
      int rowid = 0;
      int i =0;
      while (j.hasNext()) {
        i++;
        rowid = (rowid != 1?1:2);
        Ticket thisTic = (Ticket)j.next();
  %>
    <tr class="row<%= rowid %>">
    
      <td rowspan="2" width="8" valign="top" nowrap>
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
        <%-- To display the menu, pass the actionId, accountId and the contactId--%>
        <a href="javascript:displayMenu('select<%= i %>','menuTic','<%= OrgDetails.getId() %>','<%= thisTic.getId() %>', '<%= thisTic.isTrashed() || OrgDetails.isTrashed() %>', '<%= thisTic.isClosed() %>');" onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuTic');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
      </td>
   
		<td width="10%" valign="top" nowrap>
			<%-- a href="TroubleTickets_asl.do?command=Details&id=<%= thisTic.getId() %>"><%= thisTic.getPaddedId() %></a --%>
			<%-- a href="AccountTickets.do?command=RichiesteDetails&id=<%= thisTic.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisTic.getPaddedId() %></a --%>
			<a href="AccountVigilanza.do?command=TicketDetails&id=<%= thisTic.getId() %>&orgId=<%= thisTic.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisTic.getPaddedId() %></a>
		</td>
      <%-- 		<td width="20%" valign="top" nowrap>
			<dhv:label name="<%="richieste." + thisTic.getTipo_richiesta() %>"><%=thisTic.getTipo_richiesta()%></dhv:label>
		</td>
	  --%>	
		<td width="15%" valign="top" class="row<%= rowid %>">
      <% if(!User.getTimeZone().equals(thisTic.getAssignedDate())){%>
      <zeroio:tz timestamp="<%= thisTic.getAssignedDate() %>" timeZone="<%= User.getTimeZone() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
      <% } else { %>
      <zeroio:tz timestamp="<%= thisTic.getAssignedDate() %>" dateOnly="true" timeZone="<%= thisTic.getAssignedDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
      <% } %>
		</td>
		<%if(thisTic.getTipoCampione() > -1) {%>
		<td valign="top"><%= TipoCampione.getSelectedValue(thisTic.getTipoCampione()) %></td>
		<%}else{%>
		<td>-
		</td>
		<%} %>
		<%--if(thisTic.getEsitoCampione() > -1) {%>
		<td valign="top"><%= EsitoCampione.getSelectedValue(thisTic.getEsitoCampione()) %></td>	
		<%}else{%>
		<td>-
		</td>
		<%} --%>
		<%-- da aggiungere il valore del richiedente 
		<td>
		<%= toHtml(thisTic.getCompanyName()) %><dhv:evaluate if="<%= !(thisTic.getCompanyEnabled()) %>">&nbsp;<font color="red">*</font></dhv:evaluate>
		</td>--%>
		<td width="45%" valign="top">
			<zeroio:tz timestamp="<%= thisTic.getModified() %>" dateOnly="false" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" />
		</td>
	</tr>
      <%-- td width="15" valign="top" nowrap>
        <a href="AccountTickets.do?command=TicketDetails&id=<%= thisTic.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisTic.getPaddedId() %></a>
      </td>
      <td valign="top" nowrap>
        <%= toHtml(thisTic.getPriorityName()) %>
      </td>
      <td width="15%" valign="top" nowrap>
        <% if(!User.getTimeZone().equals(thisTic.getEstimatedResolutionDateTimeZone())){%>
        <zeroio:tz timestamp="<%= thisTic.getEstimatedResolutionDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% } else { %>
        <zeroio:tz timestamp="<%= thisTic.getEstimatedResolutionDate() %>" dateOnly="true" timeZone="<%= thisTic.getEstimatedResolutionDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
        <% } %>
      </td>
      <td width="8%" align="right" valign="top" nowrap>
        <%= thisTic.getAgeOf() %>
      </td>
      <td width="150" nowrap valign="top">
        <dhv:username id="<%= thisTic.getAssignedTo() %>" default="ticket.unassigned.text"/><dhv:evaluate if="<%= !(thisTic.getHasEnabledOwnerAccount()) %>">&nbsp;<font color="red">*</font></dhv:evaluate>
      </td>
      <td width="150" nowrap valign="top">
        <% if (thisTic.getClosed() == null) { %>
          <zeroio:tz timestamp="<%= thisTic.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/>
        <%} else {%>
          <zeroio:tz timestamp="<%= thisTic.getClosed() %>" dateOnly="true" default="&nbsp;" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" />
        <%}%>
      </td>
     </tr --%>
<tr class="row<%= rowid %>">
      <td colspan="7" valign="top">
        <%
          if (1==1) {
            Iterator files = thisTic.getFiles().iterator();
            while (files.hasNext()) {
              FileItem thisFile = (FileItem)files.next();
              if (".wav".equalsIgnoreCase(thisFile.getExtension())) {
        %>
          <a href="AccountVigilanzaDocuments.do?command=Download&stream=true&tId=<%= thisTic.getId() %>&fid=<%= thisFile.getId() %>"><img src="images/file-audio.gif" border="0" align="absbottom"></a>
        <%
              }
            }
          }
        %>
        <%= toHtml(thisTic.getProblemHeader()) %>&nbsp;
        <% if (thisTic.getClosed() == null) { %>
          [<font color="green"><dhv:label name="project.open.lowercase1">open</dhv:label></font>]
        <%} else {%>
          [<font color="red"><dhv:label name="project.closed.lowercase1">closed</dhv:label></font>]
        <%}%>
      </td>
    </tr>
  <%}%>
  <%} else {%>
  
    <tr class="containerBody">
      <td colspan="7">
        <dhv:label name="accounts.richieste.search.notFound">Nessun Controllo Ufficiale Trovato.</dhv:label>
      </td>
    </tr>
  <%}%>
  </table>
	<br>
  <dhv:pagedListControl object="AccountTicketInfo"/>
</dhv:container>