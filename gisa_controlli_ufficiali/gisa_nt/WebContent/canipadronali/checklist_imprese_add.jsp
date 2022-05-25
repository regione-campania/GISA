<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<%@ page import = "org.aspcfs.modules.canipadronali.base.Cane"%>
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

<%Cane OrgDetails = (Cane) request.getAttribute("OrgDetails"); %>
<form name="addAccountAudit" method="post" action="CheckListCaniPadronali.do?command=Save&auto-populate=true" onSubmit="return checkForm();">
<input type="hidden" name ="assetId" value="<%=OrgDetails.getId() %>">

<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">

  <a href="CaniPadronali.do=command=SearchForm">Anagrafica Cani di proprieta</a> > 
  <%--<a href="CaniPadronali.do=command=Detail&orgId=<%=OrgDetails.getOrgId() %>&assetId=<%=OrgDetails.getId() %>">Scheda Cane Padronale</a> > --%>
  <a href="CaniPadronaliVigilanza.do?command=TicketDetails&id=<%= request.getAttribute("idC")%>&orgId=<%=OrgDetails.getOrgId()%>&assetId=<%=OrgDetails.getId() %>"><dhv:label name="">Controllo Ufficiale</dhv:label></a> >
  <dhv:label name="audit.aggiungiAudiat">Aggiungi Check List</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>

<dhv:container name="canipadronali" selected="vigilanza" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId()+"&assetId="+OrgDetails.getId()%>' >
<%@ include file="../checklist/checklist_add.jsp" %>

	

</dhv:container>
</form>


