<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.tamponi.base.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.aziendeagricole.base.Organization" scope="request"/>
<body >
<form name="addticket" action="AziendeAgricoleTamponi.do?command=Insert&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0" >
<tr>
<td>
  <a href="AziendeAgricole.do">AziendeAgricole</a> > 
  <a href="AziendeAgricole.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
  <a href="AziendeAgricole.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Scheda Aziende Agricole</a> >
    <a href="AziendeAgricoleVigilanza.do?command=ViewVigilanza&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Controlli Ufficiali</dhv:label></a> >
    <a href="AziendeAgricoleVigilanza.do?command=TicketDetails&id=<%= request.getAttribute("idC")%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Controllo Ufficiale</dhv:label></a> >
    
  <%--a href="Accounts.do?command=ViewTamponi&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="tamponi">Tamponi</dhv:label></a> --%>
  <dhv:label name="tamponi.aggiungi">Aggiungi Tampone</dhv:label>
</td>
</tr>
</table >
<%-- End Trails --%>
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="return checkForm(this.form)">
<input type="button" value="Annulla" onClick="window.location.href='AziendeAgricoleVigilanza.do?command=TicketDetails&id=<%= request.getAttribute("idC") %>&orgId=<%=OrgDetails.getOrgId()%>';this.form.dosubmit.value='false';" />
<br>
<dhv:formMessage />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<% if (request.getAttribute("closedError") != null) { %>
  <%= showAttribute(request, "closedError") %>
<%}%>
<%-- include basic troubleticket add form --%>

<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.text.DateFormat, org.aspcfs.modules.actionplans.base.*" %>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EsitoTampone" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_1" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_2" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_3" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_4" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_5" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_6" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_7" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_8" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_9" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_10" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Tamponi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_1" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_2" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_3" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_4" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_5" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_6" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_7" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_8" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_9" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_10" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleo" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoDue" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoTre" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoQuattro" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoCinque" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoSei" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoSette" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoOtto" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoNove" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoDieci" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiNonTrasformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiNonTrasformatiValori" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ConseguenzePositivita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ResponsabilitaPositivita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiTrasformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiVegetali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoTampone" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolvedByDeptList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.tamponi.base.Ticket" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Provvedimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SanzioniAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Recipient" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="DestinatarioTampone" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EscalationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.aziendeagricole.base.OrganizationList" scope="request"/>
<jsp:useBean id="SubList1" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="resolvedUserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="actionPlans" class="org.aspcfs.modules.actionplans.base.ActionPlanList" scope="request"/>
<jsp:useBean id="insertActionPlan" class="java.lang.String" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="defectSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProducts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/controlli_tamponi.js"></SCRIPT>

<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Aggiungi Tampone</dhv:label></strong>
    </th>
	</tr>
  
	  <%@ include file="/tamponi/tamponi_add.jsp"%>
      
    
 </table>
    </br>
    <br><br>
<input type="hidden" name="close" value="">
<input type="hidden" name="refresh" value="-1">
<input type="hidden" name="modified" value="<%=  TicketDetails.getModified() %>" />
<input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
<input type="hidden" name="statusId" value="<%=  TicketDetails.getStatusId() %>" />
<input type="hidden" name="trashedDate" value="<%=  TicketDetails.getTrashedDate() %>" />
<%= addHiddenParams(request, "popup|popupType|actionId") %>


<br>
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save" onClick="return checkForm(this.form)">
<input type="button" value="Annulla" onClick="window.location.href='AziendeAgricoleVigilanza.do?command=TicketDetails&id=<%= request.getAttribute("idC") %>&orgId=<%=OrgDetails.getOrgId()%>';this.form.dosubmit.value='false';" />
</form>
</body>
