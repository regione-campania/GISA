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
  - Version: $Id: troubletickets_list.jsp 15115 2006-05-31 16:47:51Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page
	import="java.util.*,org.aspcfs.modules.allerte_new.base.*,org.aspcfs.modules.allerte_new.base.Ticket,com.zeroio.iteam.base.*,java.text.DateFormat"%>

<%@page import="org.apache.batik.dom.util.HashTable"%><jsp:useBean
	id="CreatedByMeList" class="org.aspcfs.modules.allerte_new.base.TicketList"
	scope="request" />
<jsp:useBean id="CreatedByMeInfo"
	class="org.aspcfs.utils.web.PagedListInfo" scope="session" />
<jsp:useBean id="AssignedToMeList"
	class="org.aspcfs.modules.allerte_new.base.TicketList" scope="request" />
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="EsitoCampione" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="AssignedToMeInfo"
	class="org.aspcfs.utils.web.PagedListInfo" scope="session" />
<jsp:useBean id="OpenList"
	class="org.aspcfs.modules.allerte_new.base.TicketList" scope="request" />
<jsp:useBean id="OpenInfo" class="org.aspcfs.utils.web.PagedListInfo"
	scope="session" />
<jsp:useBean id="AllTicketsList"
	class="org.aspcfs.modules.allerte_new.base.TicketList" scope="request" />
<jsp:useBean id="AllTicketsInfo"
	class="org.aspcfs.utils.web.PagedListInfo" scope="session" />
<jsp:useBean id="UserGroupTicketList"
	class="org.aspcfs.modules.allerte_new.base.TicketList" scope="request" />
<jsp:useBean id="UserGroupTicketInfo"
	class="org.aspcfs.utils.web.PagedListInfo" scope="session" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="TipoAlimento" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="Origine" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="AlimentoInteressato"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="NonConformita" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="AltreIrregolarita"
	class="org.aspcfs.utils.web.LookupList" scope="request" />

<%@ include file="../initPage.jsp"%>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp"%>
<%@ include file="troubletickets_list_menu.jsp"%>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/confirmDelete.js"></SCRIPT>
<%-- Preload image rollovers for drop-down menu --%>
<script language="JavaScript" type="text/javascript">
  
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td><a href="TroubleTicketsAllerteNew.do"><dhv:label
			name="campionis">Allerte</dhv:label></a> > <dhv:label
			name="Sanzioni.visualizzas">Visualizza Allerte</dhv:label></td>
	</tr>
</table>
<%-- End Trails --%>
<% int count = 0; %>
<%--if ((request.getParameter("pagedListSectionId") == null && !(OpenInfo.getExpandedSelection()) && !(CreatedByMeInfo.getExpandedSelection()) && !(AllTicketsInfo.getExpandedSelection()) && !(UserGroupTicketInfo.getExpandedSelection())) || AssignedToMeInfo.getExpandedSelection()) { %>
<dhv:pagedListStatus tdClass="pagedListTab" showExpandLink="true" title="Tickets Assigned to Me" type="tickets.assigned.to.me" object="AssignedToMeInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="8">
      &nbsp;
    </th>
    <th valign="center" align="left">
      <strong><dhv:label name="quotes.number">Number</dhv:label></strong>
    </th>
    <th><b><dhv:label name="accounts.accounts_contacts_calls_details_followup_include.Priority">Priority</dhv:label></b></th>
    <th><b><dhv:label name="ticket.estResolutionDate">Est. Resolution Date</dhv:label></b></th>
    <th><b><dhv:label name="ticket.age">Age</dhv:label></b></th>
    <th><b><dhv:label name="richieste.richiedente">Richiedente</dhv:label></b></th>
	<th><b><dhv:label name="project.resourceAssigned">Resource Assigned</dhv:label></b></th>
  </tr>
<%
	Iterator k = AssignedToMeList.iterator();
	if ( k.hasNext() ) {
		int rowid = 0;
		while (k.hasNext()) {
      ++count;
		  rowid = (rowid != 1?1:2);
      Ticket assignedTic = (Ticket)k.next();
%>   
	<tr class="row<%= rowid %>">
    <td rowspan="2" width="8" valign="top" nowrap --%>
<%-- Use the unique id for opening the menu, and toggling the graphics --%>
<%--a href="javascript:displayMenu('select<%= count %>','menuTicket','<%= assignedTic.getId() %>');" 
          onMouseOver="over(0, <%= count %>)" 
          onmouseout="out(0, <%= count %>); hideMenu('menuTicket');"><img 
          src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
    </td>
		<td width="10%" valign="top" nowrap>
			<a href="TroubleTicketsCampioni.do?command=Details&id=<%= assignedTic.getId() %>"><%= assignedTic.getPaddedId() %></a>
		</td>
		<td width="12%" valign="top" nowrap>
			<%= toHtml(assignedTic.getPriorityName()) %>
		</td>
		<td width="15%" valign="top" nowrap>
      <% if(!User.getTimeZone().equals(assignedTic.getEstimatedResolutionDateTimeZone())){%>
      <zeroio:tz timestamp="<%= assignedTic.getEstimatedResolutionDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% } else { %>
      <zeroio:tz timestamp="<%= assignedTic.getEstimatedResolutionDate() %>" dateOnly="true" timeZone="<%= assignedTic.getEstimatedResolutionDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% } %>
		</td>
		<td width="6%" align="right" valign="top" nowrap>
			<%= assignedTic.getAgeOf() %>
		</td>
		<td width="45%" valign="top">
			<%= toHtml(assignedTic.getCompanyName()) %><dhv:evaluate if="<%= !(assignedTic.getCompanyEnabled()) %>">&nbsp;<font color="red">*</font></dhv:evaluate>
		</td>
		<td width="20%" nowrap valign="top">
			<dhv:username id="<%= assignedTic.getAssignedTo() %>" default="ticket.unassigned.text"/><dhv:evaluate if="<%= !(assignedTic.getHasEnabledOwnerAccount()) %>">&nbsp;<font color="red">*</font></dhv:evaluate>
		</td>
	</tr>
  <tr class="row<%= rowid %>">
    <td colspan="7" valign="top">
<%
  if (1==1) {
    Iterator files = assignedTic.getFiles().iterator();
    while (files.hasNext()) {
      FileItem thisFile = (FileItem)files.next();
      if (".wav".equalsIgnoreCase(thisFile.getExtension())) {
%>
  <a href="TroubleTicketsDocumentsCampioni.do?command=Download&stream=true&tId=<%= assignedTic.getId() %>&fid=<%= thisFile.getId() %>"><img src="images/file-audio.gif" border="0" align="absbottom"></a>
<%
      }
    }
  }
%>
      <%= toHtml(assignedTic.getProblemHeader()) %>&nbsp;
      <% if (assignedTic.getClosed() == null) { %>
        [<font color="green"><dhv:label name="project.open.lowercase">open</dhv:label></font>]
      <%} else {%>
        [<font color="red"><dhv:label name="project.closed.lowercase">closed</dhv:label></font>]
      <%}%>
    </td>
  </tr>
	<%}%>
</table>
  <% if (AssignedToMeInfo.getExpandedSelection()) {%>
<br>
<dhv:pagedListControl object="AssignedToMeInfo" tdClass="row1"/>
  <%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="7">
      <dhv:label name="tickets.search.notFound">No tickets found</dhv:label>
    </td>
  </tr>
</table>
	<%}%>
<br>
<%} --%>
<%-- if ( (request.getParameter("pagedListSectionId") == null && !(AssignedToMeInfo.getExpandedSelection()) && !(CreatedByMeInfo.getExpandedSelection()) && !(AllTicketsInfo.getExpandedSelection()) && !(UserGroupTicketInfo.getExpandedSelection())) || OpenInfo.getExpandedSelection()) { %>
<dhv:pagedListStatus tdClass="pagedListTab" showExpandLink="true" title="Other Tickets in My Department" type="tickets.other" object="OpenInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
		<th width="8">
      &nbsp;
    </th>
    <th valign="center" align="left">
      <strong><dhv:label name="quotes.number">Number</dhv:label></strong>
    </th>
    <th><b><dhv:label name="accounts.accounts_contacts_calls_details_followup_include.Priority">Priority</dhv:label></b></th>
    <th><b><dhv:label name="ticket.estResolutionDate">Est. Resolution Date</dhv:label></b></th>
    <th><b><dhv:label name="ticket.age">Age</dhv:label></b></th>
    <th><b><dhv:label name="accounts.accounts_contacts_detailsimport.Company">Company</dhv:label></b></th>
	<th><b><dhv:label name="project.resourceAssigned">Resource Assigned</dhv:label></b></th>
  </tr>
<%
	Iterator n = OpenList.iterator();
	if ( n.hasNext() ) {
		int rowid = 0;
		while (n.hasNext()) {
      ++count;
      rowid = (rowid != 1?1:2);
      Ticket openTic = (Ticket)n.next();
%>   
	<tr>
    <td rowspan="2" width="8" valign="top" nowrap class="row<%= rowid %>" --%>
<%-- Use the unique id for opening the menu, and toggling the graphics --%>
<%--a href="javascript:displayMenu('select<%= count %>','menuTicket','<%= openTic.getId() %>');" 
          onMouseOver="over(0, <%= count %>)" 
          onmouseout="out(0, <%= count %>); hideMenu('menuTicket');"><img 
          src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
    </td>
		<td width="10%" valign="top" nowrap class="row<%= rowid %>">
			<a href="TroubleTicketsCampioni.do?command=Details&id=<%= openTic.getId() %>"><%= openTic.getPaddedId() %></a>
		</td>
		<td width="12%" valign="top" nowrap class="row<%= rowid %>">
			<%= toHtml(openTic.getPriorityName()) %>
		</td>
		<td width="15%" valign="top" class="row<%= rowid %>">
      <% if(!User.getTimeZone().equals(openTic.getEstimatedResolutionDateTimeZone())){%>
      <zeroio:tz timestamp="<%= openTic.getEstimatedResolutionDate() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% } else { %>
      <zeroio:tz timestamp="<%= openTic.getEstimatedResolutionDate() %>" dateOnly="true" timeZone="<%= openTic.getEstimatedResolutionDateTimeZone() %>" showTimeZone="true" default="&nbsp;"/>
      <% } %>
		</td>
		<td width="6%" align="right" valign="top" nowrap class="row<%= rowid %>">
			<%= openTic.getAgeOf() %>
		</td>
		<td width="45%" valign="top" class="row<%= rowid %>">
			<%= toHtml(openTic.getCompanyName()) %><dhv:evaluate if="<%= !(openTic.getCompanyEnabled()) %>">&nbsp;<font color="red">*</font></dhv:evaluate>
		</td>
		<td width="20%" nowrap valign="top" class="row<%= rowid %>">
      <dhv:evaluate if="<%= openTic.isAssigned() %>">
        <dhv:username id="<%= openTic.getAssignedTo() %>" default="ticket.unassigned.text"/>
      </dhv:evaluate>
      <dhv:evaluate if="<%= !(openTic.getHasEnabledOwnerAccount()) %>"><font color="red">*</font></dhv:evaluate>
      <dhv:evaluate if="<%= (!openTic.isAssigned()) %>">
        <font color="red"><dhv:username id="<%= openTic.getAssignedTo() %>" default="ticket.unassigned.text"/></font>
      </dhv:evaluate>
		</td>
	</tr>
  <tr>
    <td colspan="7" valign="top" class="row<%= rowid %>">
<%
  if (1==1) {
    Iterator files = openTic.getFiles().iterator();
    while (files.hasNext()) {
      FileItem thisFile = (FileItem)files.next();
      if (".wav".equalsIgnoreCase(thisFile.getExtension())) {
%>
  <a href="TroubleTicketsDocumentsCampioni.do?command=Download&stream=true&tId=<%= openTic.getId() %>&fid=<%= thisFile.getId() %>"><img src="images/file-audio.gif" border="0" align="absbottom"></a>
<%
      }
    }
  }
%>
      <%= toHtml(openTic.getProblemHeader()) %>
      <% if (openTic.getClosed() == null) { %>
        [<font color="green"><dhv:label name="project.open.lowercase">open</dhv:label></font>]
      <%} else {%>
        [<font color="red"><dhv:label name="project.closed.lowercase">closed</dhv:label></font>]
      <%}%>
    </td>
  </tr>
	<%}%>
</table>
  <% if (OpenInfo.getExpandedSelection()) {%>
  <br>
  <dhv:pagedListControl object="OpenInfo" tdClass="row1"/>
  <%}%>
	<%} else {%>
		<tr class="containerBody">
      <td colspan="7">
        <dhv:label name="tickets.search.notFound">No tickets found</dhv:label>
      </td>
    </tr>
  </table>
	<%}%>
<br>
<%} --%>


<dhv:include name="ticketList.allTickets" none="true">
	<% if ( (request.getParameter("pagedListSectionId") == null && !(AssignedToMeInfo.getExpandedSelection()) && !(OpenInfo.getExpandedSelection()) && !(CreatedByMeInfo.getExpandedSelection()) && !(UserGroupTicketInfo.getExpandedSelection())) || AllTicketsInfo.getExpandedSelection()) { %>

	<!-- modifiche d.dauria -->
	<% 

Hashtable storico = (Hashtable) request.getSession().getAttribute("listaStorico");
ArrayList listatipoAlimento = (ArrayList) request.getSession().getAttribute("listaTipoAlimento");
ArrayList listaOrigine = (ArrayList) request.getSession().getAttribute("listaOrigine");

   
%>
&nbsp;<br>
	<br>
	<h3>Storico annuale</h3>


	<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
		<tr>
			<td width="8">&nbsp;</td>
			<% 
    
    //i valori devono essere le mie colonne
    int i = 0;
    int totaleRiga = 0;
    int[] vet = new int[listatipoAlimento.size()];
  for(i=0; i<listatipoAlimento.size(); i++)
    {
    	String colonna = (String) listatipoAlimento.get(i);    	
    
    %>
			<th valign="center" align="left"><strong><dhv:label
				name="quotes.numbesr"><%=colonna%></dhv:label></strong></th>
			<%  
   } 
   %>
			<th valign="center" align="left"><strong><dhv:label
				name="quotes.numbesr">Totale</dhv:label></strong></th>
		</tr>

		<%
      for( int j = 0; j < listaOrigine.size(); j++)
      {  
    	  totaleRiga = 0;
    	  String origine = (String) listaOrigine.get(j);
    	  Hashtable ch = (Hashtable) storico.get(origine);
    %>

		<tr>
			<th valign="center" align="left" width="200px"><strong><dhv:label
				name="quotes.numbesr"><%=origine %></dhv:label></strong></th>

			<% 
    	  if( ch == null )
    	  {
	    	  for( int k = 0; k <= listatipoAlimento.size(); k++ )   //< = perchè mi deve azzerare anche il totale
	    	  {
					%>
					
					
			<td  ><%=0 %></td>
			<%    		  
	    	  }
    	  }
    	  else
    	  {
    		  for( int k = 0; k < listatipoAlimento.size(); k++ )
	    	  {
    			  Integer contenutoB =(Integer) ch.get( listatipoAlimento.get( k ) );
    			  if( contenutoB == null )
    			  {
					%>
			<td><%=0 %></td>
			<%
    			  }
    			  else
    			  {
    				  vet[k] = vet[k] + contenutoB; 
    				   totaleRiga = totaleRiga + contenutoB; 
  					%>
			<td><%=contenutoB %></td>
			<%
    			  }
	    	  }
    		  %>
			<td><%=totaleRiga %></td>
			<%
    	  }
      }
    %>

<tr>
 <th valign="center" align="left"><strong><dhv:label
				name="quotes.numbesr">Totale</dhv:label></strong></th>
 <%
  for(int z=0; z<listatipoAlimento.size(); z++)
  {
  %>
    <td bgcolor="yellow"><%=vet[z] %></td>
  <%	 
  }
  %>

</tr>
		
			<%
				}	

 			%>	
		
	</table>
	<!-- fine modifiche -->

</dhv:include>







