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
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.soa.base.*,org.aspcfs.modules.vigilanza.base.Ticket,com.zeroio.iteam.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.soa.base.Organization" scope="request"/>
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EsitoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TicList" class="org.aspcfs.modules.vigilanza.base.TicketList" scope="request"/>
<jsp:useBean id="TipoAudit" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoIspezione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AccountTicketInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
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
<a href="Soa.do"><dhv:label name="soa.soa">Accounts</dhv:label></a> > 
<% if (request.getParameter("return") == null) { %>
<a href="Soa.do?command=Search"><dhv:label name="soa.SearchResults">Search Results</dhv:label></a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="Soa.do?command=Dashboard">Cruscotto</a> >
<%}%>
<a href="Soa.do?command=Details&orgId=<%=OrgDetails.getOrgId() %>"><dhv:label name="soa.details">Account Details</dhv:label></a> >

<dhv:label name="vigilanza">Tickets</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="soa" selected="vigilanza" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:evaluate if="<%= !OrgDetails.isTrashed() %>">
    <dhv:permission name="soa-soa-vigilanza-add"><a href="SoaVigilanza.do?command=Add&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="accounts.richiesta.add">Aggiungi Nuovo Controllo Ufficiale</dhv:label></a></dhv:permission>
  </dhv:evaluate>
    <input type=hidden name="orgId" value="<%= OrgDetails.getOrgId() %>">
    <br>
    
    <script>
function filtraCu(radio){
	var value = radio.value;
	loadModalWindow();
	window.location.href = 	"<%="Soa"  %>.do?command=ViewVigilanza&orgId=<%= request.getParameter("orgId")%>&statusId="+value;
}

</script>


<dhv:permission name="cu-filtra-view">
<table style="border: 1px solid black">
<tr>
<td><input type="radio" id="tutti" name="status" value="-1" <%if (request.getParameter("statusId")==null || request.getParameter("statusId").equals("-1")){ %>checked<%} %> onChange="filtraCu(this)"/>Tutti</td>
<td><input type="radio" id="aperti" name="status" value="1" <%if (request.getParameter("statusId")!=null && request.getParameter("statusId").equals("1")){ %>checked<%} %> onChange="filtraCu(this)"/>Aperti/Riaperti</td>
<%-- <td><input type="radio" id="riaperti" name="status" value="3" <%if (request.getParameter("statusId")!=null && request.getParameter("statusId").equals("3")){ %>checked<%} %> onChange="filtraCu(this)"/>Riaperti</td> --%>
<td><input type="radio" id="chiusi" name="status" value="2" <%if (request.getParameter("statusId")!=null && request.getParameter("statusId").equals("2")){ %>checked<%} %> onChange="filtraCu(this)"/>Chiusi</td>
</tr>
</table>
<font color="red">Attenzione:  Per i filtri Aperti e Chiusi, tutti i risultati saranno mostrati nella prima pagina.</font>
</dhv:permission>


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
        <b><strong><a href="Soa.do?command=ViewTickets&orgId=<%= OrgDetails.getOrgId() %>&column=pri_code<%= addLinkParams(request, "popup|popupType|actionId") %>"><dhv:label name="soa.soa_contacts_calls_details_followup_include.Priority">Priority</dhv:label></a></strong></b>
        <%= AccountTicketInfo.getSortIcon("pri_code") %>
      </th>
      <th width="15%">
        <b><dhv:label name="ticket.estResolutionDate">Est. Resolution Date</dhv:label></b>
      </th>
      <th width="15%">
        <b><dhv:label name="ticket.age">Age</dhv:label></b>
      </th>
      <th width="20%">
        <b><dhv:label name="soa.soa_contacts_calls_list.AssignedTo">Assigned To</dhv:label></b>
      </th>
      <th width="20%">
        <b><dhv:label name="soa.soa_contacts_calls_details.Modified">Modified</dhv:label></b>
      </th>
      </tr --%>
        <tr>
		
    <th valign="center" align="left">
      <strong><dhv:label name="">Identificativo</dhv:label></strong>
    </th>
     <th valign="center" align="left">
      <strong><dhv:label name="">Controllo del</dhv:label></strong>
    </th>
     <th><b><dhv:label name="">Data fine controllo</dhv:label></b></th>
    <%-- th><b><dhv:label name="soa.soa_contacts_calls_details_followup_include.Priority">Priority</dhv:label></b></th --%>
    <%-- th><b><dhv:label name="ticket.estResolutionDate">Est. Resolution Date</dhv:label></b></th --%>
    <th><b><dhv:label name="sanzionia.richiedente">Tipo di controllo</dhv:label></b></th>
   <th style ="display:none"><b><dhv:label name="sanzionia.richiedente">Attivita Svolte</dhv:label></b></th>
    <th><b><dhv:label name="sanzionia.richiedente">Inserito da</dhv:label></b></th>
	<%-- th><b><dhv:label name="project.resourceAssigned">Resource Assigned</dhv:label></b></th --%>
  </tr>
  <%
    Iterator j = TicList.iterator();
    boolean hasNext = j.hasNext();
    if ( hasNext ) {
      int rowid = 0;
      int i =0;
     
      while ( hasNext ) {
        i++;
        rowid = (rowid != 1?1:2);
    	  
        Ticket thisTic = (Ticket)j.next();
    	  
        hasNext = j.hasNext();
  %>
    <tr class="row<%= rowid %>">
      
      <td width="10%" valign="top" nowrap><%=thisTic.getPaddedId() %></td>
		<td width="10%" valign="top" nowrap>
			<%-- a href="TroubleTickets_asl.do?command=Details&id=<%= thisTic.getId() %>"><%= thisTic.getPaddedId() %></a --%>
			<%-- a href="SoaVigilanza.do?command=RichiesteDetails&id=<%= thisTic.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisTic.getPaddedId() %></a --%>
			<a href="SoaVigilanza.do?command=TicketDetails&id=<%= thisTic.getId() %>&orgId=<%= thisTic.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><zeroio:tz timestamp="<%= thisTic.getAssignedDate() %>" timeZone="<%= User.getTimeZone() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
      </a>
		</td>
     
		<td width="15%" valign="top" class="row<%= rowid %>">
      <%-- if(!User.getTimeZone().equals(thisTic.getAssignedDate())){--%>
      <zeroio:tz timestamp="<%= thisTic.getDataFineControllo() %>" timeZone="<%= User.getTimeZone() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
     
			<%if(thisTic.getTipoCampione() > -1) {%>
		<td valign="top"><%= TipoCampione.getSelectedValue(thisTic.getTipoCampione()) %>
	
		</td>
		<%}else{%>
		<td>-
		</td>
		<td style ="display:none">	N.campioni&nbsp;&nbsp;: <%=thisTic.getNumCampioni() %> </br>
				N.tamponi&nbsp;&nbsp;&nbsp;: <%=thisTic.getNumTamponi() %>	</br>
				N.sanzioni&nbsp;&nbsp;&nbsp;: <%=thisTic.getNumSanzioni() %> </br>
				N.sequestri&nbsp;&nbsp;: <%=thisTic.getNumSequestri() %></br>
				N.reati&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: <%=thisTic.getNumReati() %>		</br>
				N.followup&nbsp;&nbsp;&nbsp;: <%=thisTic.getNumFollowup() %> </br>
		
		
		
		</td>
		<%} %>
		
		<td nowrap>
      <dhv:username id="<%= thisTic.getEnteredBy() %>" />
	</td>
	</tr>
      
<tr class="row<%= rowid %>">
      <td colspan="7" valign="top">
        <%
          if (1==1) {
            Iterator files = thisTic.getFiles().iterator();
            while (files.hasNext()) {
              FileItem thisFile = (FileItem)files.next();
              if (".wav".equalsIgnoreCase(thisFile.getExtension())) {
        %>
          <a href="SoaVigilanzaDocuments.do?command=Download&stream=true&tId=<%= thisTic.getId() %>&fid=<%= thisFile.getId() %>"><img src="images/file-audio.gif" border="0" align="absbottom"></a>
        <%
              }
            }
          }
        %>
        <%= toHtml(thisTic.getProblemHeader()) %>&nbsp;
        <% if (thisTic.getClosed() == null) { %>
          [<font color="green"><dhv:label name="projecta.open.lowercase1">pratica aperta</dhv:label></font>]
        <%} else {%>
          [<font color="red"><dhv:label name="projecta.closed.lowercase1">pratica chiusa</dhv:label></font>]
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