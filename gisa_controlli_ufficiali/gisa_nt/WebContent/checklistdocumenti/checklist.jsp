<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<%@page import="org.aspcfs.utils.web.*"%>
<%@page import="org.aspcfs.checklist.base.AuditChecklistType"%>
<%@page import="org.aspcfs.checklist.base.AuditChecklist"%>
<%@page import="org.aspcfs.modules.accounts.base.Organization"%>
<%@page import="com.darkhorseventures.framework.actions.ActionContext"%>


<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="checklistList" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="auditChecklist" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="AuditDetails" class="org.aspcfs.checklist.base.Audit" scope="request"/>
<jsp:useBean id="auditChecklistType" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="typeList" class="org.aspcfs.utils.web.CustomLookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="Audit" class="org.aspcfs.checklist.base.Audit" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/div.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checklist_controlli.js"></script>

<%@ include file="../initPage.jsp" %>
<link rel="stylesheet" type="text/css" media="screen" documentale_url="" href="css/cssDomanda.css" />
<link rel="stylesheet" type="text/css" media="print" documentale_url="" href="checklistdocumenti/print.css" />

<input type="hidden" name="id" value="<%=Audit.getId() %>">
<input type="hidden" name="orgId" value="<%=OrgDetails.getId() %>">
 
<br/>
<br/>   

<table>   <thead>      <tr> <td>

<table width="100%"> 
<col width="33%"><col width="33%">
<tr>
<td><div align="left"><img style="text-decoration: none;" width="80" height="80" documentale_url="" src="gestione_documenti/schede/images/regionecampania.jpg" /></div>
<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>

</td>
<td><div class="titolo"> Scheda <%= toHtml(OrgCategoriaRischioList.getSelectedValue(Audit.getTipoChecklist())) %> <%=(Audit.getLivelloRischioFinale() == -1) ? (" Provvisoria") : ("") %></div>
</td>
<td><img style="text-decoration: none;" height="60px" max-width="80px" documentale_url="" src="gestione_documenti/schede/images/<%= SiteList.getSelectedValue(TicketDetails.getSiteId()).toLowerCase() %>.jpg" /></td>
</tr>
</table>
 </td></tr>   </thead>
  <tbody> <tr><td>

<%@ include file="../checklistdocumenti/checklist_dettaglio.jsp" %>

</td></tr></tbody></table>    

  






