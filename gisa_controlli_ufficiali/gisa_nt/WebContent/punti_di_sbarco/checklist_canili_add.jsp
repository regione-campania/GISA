<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<%@ page import = "org.aspcfs.modules.punti_di_sbarco.base.Organization"%>
<%@ page import = "org.aspcfs.utils.web.CustomLookupElement"%>
<%@ page import = "org.aspcfs.utils.web.CustomLookupList"%>
<jsp:useBean id="ControlloUfficiale" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="checklistList" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="typeList" class="org.aspcfs.utils.web.CustomLookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAttenzioneChecklist.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/div.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checklist_controlli.js"></script>
<%@ include file="../initPage.jsp" %>

<link rel="stylesheet" type="text/css" href="css/checklist.css" >

<%Organization OrgDetails = (Organization) request.getAttribute("OrgDetails"); %>
<form name="addAccountAudit" method="post" action="CheckListPuntiSbarco.do?command=Save&auto-populate=true" onSubmit="return checkForm();">

<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
  <a href="PuntiSbarco.do">PuntiSbarco</a> > 
  <a href="PuntiSbarco.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
  <a href="PuntiSbarco.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>">Scheda Punto Di Sbarco</a> >
  <a href="PuntiSbarcoVigilanza.do?command=ViewVigilanza&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Controlli Ufficiali</dhv:label></a> >
  <a href="PuntiSbarcoVigilanza.do?command=TicketDetails&id=<%= request.getAttribute("idC")%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Controllo Ufficiale</dhv:label></a> >
  <dhv:label name="audit.aggiungiAudit">Aggiungi Check List</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>

<dhv:container name="punti_di_sbarco" selected="vigilanza" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>'>
<%@ include file="../checklist/checklist_add.jsp" %>

	

</dhv:container>
</form>


