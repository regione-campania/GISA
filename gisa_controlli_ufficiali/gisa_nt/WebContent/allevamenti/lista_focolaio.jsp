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
<%@ page import="java.util.*,org.aspcfs.modules.allevamenti.base.*,org.aspcfs.modules.contacts.base.*" %>
<%@ page import="org.aspcfs.modules.contacts.base.*" %>
<%@ page import="org.aspcfs.modules.focolai.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.allevamenti.base.Organization" scope="request"/>
<jsp:useBean id="historyList" class="org.aspcfs.modules.allevamenti.base.OrganizationHistoryList" scope="request"/>
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
  
  function flipFilterForm() {
    var span = document.getElementById("filterForm");
    if (span.style.display != 'none') {
      hideSpan("filterForm");
    } else {
      showSpan("filterForm");
    }
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
    window.location.href='AccountsHistoryAllev.do?command=View&orgId=<%= OrgDetails.getOrgId() + (isPopup(request)?"&popup=true":"") %>';
  }
</script>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Allevamenti.do"><dhv:label name="allevamenti.allevamenti">Accounts</dhv:label></a> > 
<a href="Allevamenti.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
<a href="Allevamenti.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="allevamenti.details">Account Details</dhv:label></a> >
<dhv:label name="reports.helpdesk.ticket.history.historysss">Focolai</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%> 
</dhv:evaluate>
<dhv:container name="allevamenti" selected="focolai" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
<dhv:evaluate if="<%= OrgDetails.getTrashedDate() == null %>">
<a href="Focolai.do?command=Aggiungi&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="accounts.accountssss">Apertura di un nuovo modulo A</dhv:label></a>&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
</dhv:evaluate>
<span name="filterForm" id="filterForm" style="display:none">
<form name="history" action="AccountsHistoryAllev.do?command=View&orgId=<%= OrgDetails.getOrgId() %><%= isPopup(request)?"&popup=true":"" %>" method="post" onSubmit="return checkForm(this);">
<input type="hidden" name="searchcodeOrgId" value="<%= OrgDetails.getOrgId() %>"/>
<% boolean check = orgHistoryListInfo.getSavedCriteria().size() == 0; %>
<table cellpadding="4" cellspacing="0" class="empty"><tr><td>
  <table cellpadding="0" cellspacing="0" class="floatWrap"><tr><td valign="top" width="100%">
    <div><dhv:permission name="allevamenti-allevamenti-view">
      <input type="checkbox" name="searchcodeNotes" value="true" <%= orgHistoryListInfo.getSearchOptionValue("searchcodeNotes").equals("true")||check?"checked":"" %> /> <dhv:label name="accounts.accountasset_include.Notes">Notes</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="allevamenti-allevamenti-contacts-calls-edit,allevamenti-allevamenti-contacts-calls-view">
      <input type="checkbox" name="searchcodeActivities" value="true" <%= orgHistoryListInfo.getSearchOptionValue("searchcodeActivities").equals("true")||check?"checked":"" %> /> <dhv:label name="allevamenti.allevamenti_calls_list.Activities">Activities</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="myhomepage-inbox-view,allevamenti-allevamenti-contacts-messages-view">
      <input type="checkbox" name="searchcodeEmail" value="true" <%= orgHistoryListInfo.getSearchOptionValue("searchcodeEmail").equals("true")||check?"checked":"" %> /> <dhv:label name="allevamenti.allevamenti_add.EmailOrMessages">Email/Messages</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="allevamenti-allevamenti-documents-edit,allevamenti-allevamenti-documents-view">
      <input type="checkbox" name="searchcodeDocuments" value="true" <%= orgHistoryListInfo.getSearchOptionValue("searchcodeDocuments").equals("true")||check?"checked":"" %> /> <dhv:label name="allevamenti.allevamenti_documents_details.Documents">Documents</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="allevamenti-allevamenti-tickets-edit,allevamenti-allevamenti-tickets-view">
      <input type="checkbox" name="searchcodeTickets" value="true" <%= orgHistoryListInfo.getSearchOptionValue("searchcodeTickets").equals("true")||check?"checked":"" %> /> <dhv:label name="accounts.tickets.tickets">Tickets</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="allevamenti-assets-view,allevamenti-assets-edit">
      <input type="checkbox" name="searchcodeAssets" value="true" <%= orgHistoryListInfo.getSearchOptionValue("searchcodeAssets").equals("true")||check?"checked":"" %> /> <dhv:label name="accounts.Assets">Assets</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="allevamenti-quotes-edit,allevamenti-quotes-view">
      <input type="checkbox" name="searchcodeQuotes" value="true" <%= orgHistoryListInfo.getSearchOptionValue("searchcodeQuotes").equals("true")||check?"checked":"" %> /> <dhv:label name="allevamenti.allevamenti_quotes_list.Quotes">Quotes</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="myhomepage-tasks-edit,myhomepage-tasks-view">
      <input type="checkbox" name="searchcodeTasks" value="true" <%= orgHistoryListInfo.getSearchOptionValue("searchcodeTasks").equals("true")||check?"checked":"" %> /> <dhv:label name="myitems.tasks">Tasks</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="allevamenti-service-contracts-edit,allevamenti-service-contracts-view">
      <input type="checkbox" name="searchcodeServiceContracts" value="true" <%= orgHistoryListInfo.getSearchOptionValue("searchcodeServiceContracts").equals("true")||check?"checked":"" %> /> <dhv:label name="allevamenti.allevamenti_sc_add.ServiceContracts">Service Contracts</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="allevamenti-allevamenti-opportunities-edit,allevamenti-allevamenti-opportunities-view">
      <input type="checkbox" name="searchcodeOpportunities" value="true" <%= orgHistoryListInfo.getSearchOptionValue("searchcodeOpportunities").equals("true")||check?"checked":"" %> /> <dhv:label name="allevamenti.allevamenti_contacts_oppcomponent_add.Opportunities">Opportunities</dhv:label>
    </dhv:permission></div>
    <div><dhv:permission name="allevamenti-allevamenti-relationships-view">
      <input type="checkbox" name="searchcodeRelationships" value="true" <%= orgHistoryListInfo.getSearchOptionValue("searchcodeRelationships").equals("true")||check?"checked":"" %> /> <dhv:label name="allevamenti.allevamenti_relationships_add.Relationships">Relationships</dhv:label>
    </dhv:permission></div>
      <%--  <td><input type="checkbox" name="searchcodeShowDisabledWithEnabled" value="true" <%= orgHistoryListInfo.getSearchOptionValue("searchcodeShowDisabledWithEnabled").equals("true")||check?"checked":"" %> /> <dhv:label name="global.trashed">Trashed</dhv:label> --%>
  </td></tr></table></td><td nowrap>
    <table cellpadding="0" cellspacing="0" class="empty">
      <tr>
        <td valign="top" align="center">
          [<a href="javascript:setChecked(1,'searchcode','history');"><dhv:label name="quotes.checkAll">Check All</dhv:label></a>]
        </td>
      </tr>
      <tr>
        <td valign="top" align="center">
          [<a href="javascript:setChecked(0,'searchcode','history');"><dhv:label name="quotes.clearAll">Clear All</dhv:label></a>]
        </td>
      </tr>
      <tr>
        <td nowrap valign="top" align="center">
          <input type="submit" value="<dhv:label name="accounts.history.applyFilters">Apply Filters</dhv:label>" />
        </td>
      </tr>
    </table>
</td></tr></table>
</form>
</span>

  <%--dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="orgHistoryListInfo" externalJScript="flipFilterForm();" externalText="<dhv:label name=\"pagedListInfo.filters\">Filtri</dhv:label>"/--%>
  <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
    <tr>
      
      <th nowrap>
        <strong><dhv:label name="reports.accounts.typess">Località</dhv:label></strong>
        <%= orgHistoryListInfo.getSortIcon("type") %>
      </th>
      <th nowrap>
        <strong><dhv:label name="reports.helpdesk.ticket.maintenance.partDescriptionss">Data Sospetto</dhv:label></strong>
      </th>
      <th nowrap>
        <strong><dhv:label name="accounts.accountHistory.contactfff">Data Apertura</dhv:label></strong>
        <%= orgHistoryListInfo.getSortIcon("c.namelast") %>
      </th>
      <th nowrap>
        <strong><dhv:label name="allevamenti.allevamenti_calls_list.Enteredff">Stato</dhv:label></strong>
        <%= orgHistoryListInfo.getSortIcon("history.entered") %>
      </th>
     
    </tr>
    
    <%
      SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
      ArrayList lista = (ArrayList) request.getAttribute("ListaFocolai"); 
      for(int i=0; i<lista.size(); i++)
      {
    	  Focolaio foc = (Focolaio) lista.get(i);
      
    %>
    <tr class="row1">
        <td valign="top">
         <%if(foc.getLocalita() != null && !foc.getLocalita().equals("")) {%>
           <%=foc.getLocalita() %>
          <%} %> 
        </td>
        
        <td valign="top">
          <%if(foc.getDataSospetto() != null) {%>
           <%=formatter.format(foc.getDataSospetto()) %>
          <%} %> 
        </td>
        
        <td valign="top">
          <%if(foc.getDataApertura() != null) {%>
        	<%=formatter.format(foc.getDataApertura()) %>
          <%} %>	
       </td>
       
       <td valign="top">
         <% if(foc.getApertura() == true && foc.getApertura() != false) {%>
           <a href="Focolai.do?command=DettaglioLista&focolaioId=<%=foc.getFocolaioId() %>"><font color="green">APERTO </font></a>
           <%request.getSession().setAttribute("abilitaModulo","ok"); %>  <!-- serve ad abilitare il modulo di chiusura -->
         <%}else{ %>
          <a href="Focolai.do?command=DettaglioListaModuloB&focolaioId=<%=foc.getFocolaioId() %>"><font color="red">CHIUSO </font></a>
        <%} %>  
       </td>
    </tr>
    
    <%} %>
    
    
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
		<tr class="row<%= rowid %>">
      <td valign="top">
        <dhv:permission name="<%= historyPermission %>">
          <dhv:permission name="<%= view %>">
            <% canView = true; %>
          </dhv:permission>
          <dhv:permission name="<%= modify %>">
            <% if (thisHistoryElement.getEnabled()) {canModify = true;} %>
          </dhv:permission>
          <dhv:permission name="<%= delete %>">
            <% canDelete = true; %>
          </dhv:permission>
          
        </dhv:permission>
        <dhv:permission name="<%= historyPermission %>" none="true">&nbsp;</dhv:permission>
      </td>
      <td valign="top" nowrap>
        <dhv:evaluate if="<%= !thisHistoryElement.getEnabled() %>"><font color="red"><%= toHtml(thisHistoryElement.getType()) %></font></dhv:evaluate>
        <dhv:evaluate if="<%= thisHistoryElement.getEnabled() %>"><%= toHtml(thisHistoryElement.getType()) %></dhv:evaluate>
      </td>
      <td valign="top" width="100%">
        <dhv:evaluate if="<%= !thisHistoryElement.getEnabled() %>"><font color="red"><%= toHtml(thisHistoryElement.getDescription()) %></font></dhv:evaluate>
        <dhv:evaluate if="<%= thisHistoryElement.getEnabled() %>"><%= toHtml(thisHistoryElement.getDescription()) %></dhv:evaluate>
      </td>
      <td valign="top">
        <dhv:evaluate if="<%= thisHistoryElement.getContactId() != -1 %>">
          <dhv:contactname id="<%= thisHistoryElement.getContactId() %>" listName="contacts"/>
        </dhv:evaluate>
        <dhv:evaluate if="<%= thisHistoryElement.getContactId() == -1 %>">
          &nbsp;
        </dhv:evaluate>
      </td>
      <td valign="top" nowrap>
        <dhv:evaluate if="<%= !thisHistoryElement.getEnabled() %>"><font color="red"><zeroio:tz timestamp="<%= thisHistoryElement.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /></font></dhv:evaluate>
        <dhv:evaluate if="<%= thisHistoryElement.getEnabled() %>"><zeroio:tz timestamp="<%= thisHistoryElement.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /></dhv:evaluate>
        <%-- <zeroio:tz timestamp="<%= thisHistoryElement.getEntered() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /> --%>
      </td>
      <td valign="top" nowrap>
        <dhv:evaluate if="<%= !thisHistoryElement.getEnabled() %>"><font color="red"><zeroio:tz timestamp="<%= thisHistoryElement.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /></font></dhv:evaluate>
        <dhv:evaluate if="<%= thisHistoryElement.getEnabled() %>"><zeroio:tz timestamp="<%= thisHistoryElement.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /></dhv:evaluate>
        <%-- <zeroio:tz timestamp="<%= thisHistoryElement.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" /> --%>
      </td>
		</tr>
<%}%>
<%} else {%>
<%-- 
		<tr class="containerBody">
      <td colspan="6">
        <dhv:label name="accounts.accountHistory.noHistoryFound">No history found.</dhv:label>
      </td>
    </tr>
    --%>
<%}%>
	</table>
	
	<br />
  <dhv:pagedListControl object="orgHistoryListInfo"/>
</dhv:container>
