<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@page import="org.aspcfs.utils.web.*"									%>
<%@page import="org.aspcfs.checklist.base.*"							%>
<%@page import="com.darkhorseventures.framework.actions.ActionContext"	%>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"	/>
<jsp:useBean id="checklistList" class="java.util.ArrayList" scope="request"								/>
<jsp:useBean id="auditChecklist" class="java.util.ArrayList" scope="request"							/>
<jsp:useBean id="auditChecklistType" class="java.util.ArrayList" scope="request"						/>
<jsp:useBean id="ControlloUfficiale" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"	/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"		/>
<jsp:useBean id="typeList" class="org.aspcfs.utils.web.CustomLookupList" scope="request"				/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.osm.base.Organization" scope="request"		/>
<jsp:useBean id="Audit" class="org.aspcfs.checklist.base.Audit" scope="request"						/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"		/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js">		</script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js">	</script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js">		</script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/div.js">				</script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js">			</script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js">		</script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checklist_controlli_modify.js"></script>

<%@ include file="../initPage.jsp" %>

<link rel="stylesheet" type="text/css" href="css/checklist.css" >
<link rel="stylesheet" type="text/css" href="css/cssDomanda.css" >

<body onload="ultimaDomanda(<%=Audit.getIdLastDomanda() %>);inizializzaPunteggio(<%=Audit.getLivelloRischio() %>);aggiornaCategoria();">
<form name="addAccountAudit"  method="post" action="ChecklistOsm.do?command=Update&auto-populate=true&return<%= request.getParameter("return") %>" onSubmit="return checkForm();" >
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
  <a href="Osm.do">Osm Riconosciuti</a> > 
  <a href="Osm.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
  <a href="Osm.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>">Scheda Osm</a> >
  <a href="Osm.do?command=ViewVigilanza&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Controlli Ufficiali</dhv:label></a> >
      <a href="OsmVigilanza.do?command=TicketDetails&id=<%= request.getAttribute("idCon")%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Controllo Ufficiale</dhv:label></a> >
  <a href="ChecklistOsm.do?command=View&id=<%= Audit.getId() %>"><dhv:label name="accounts.Audit">Check List</dhv:label></a> >
  <dhv:label name="audit.modificaAudit">Modifica Check List</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>

<dhv:container name="osm" selected="audit" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>'>


  <input type="button" value="Annulla" onClick="window.location.href='ChecklistOsm.do?command=View&id=<%=Audit.getId()%>';this.form.dosubmit.value='false';" />


<%@ include file="../checklist/checklist_modify.jsp" %>



  <input type="button" value="Annulla" onClick="window.location.href='ChecklistOsm.do?command=View&id=<%=Audit.getId()%>';this.form.dosubmit.value='false';" />

</dhv:container>
</form>
</body>
<script>

if(document.getElementById("btnSaveTemp")!=null)
document.getElementById("btnSaveTemp").disabled="";
document.getElementById("btnSave2").disabled="";
document.getElementById("btnSave").disabled="";
</script>