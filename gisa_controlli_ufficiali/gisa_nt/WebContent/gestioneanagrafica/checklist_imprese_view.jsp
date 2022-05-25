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
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="Audit" class="org.aspcfs.checklist.base.Audit" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

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

<link rel="stylesheet" type="text/css" href="css/cssDomanda.css" >

<form name="viewAccountAudit" action="GestioneAnagraficaCheckList.do?command=Modify&auto-populate=true&id=<%=Audit.getId()%>&return=details<%= addLinkParams(request, "popup|popupType|actionId") %>" method="post">

<input type="hidden" name="id" value="<%=Audit.getId() %>">
<input type="hidden" name="altId" value="<%=OrgDetails.getAltId() %>">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
     <dhv:label name="">Gestione Anagrafica Impresa -><a  href="GestioneAnagraficaAction.do?command=Details&altId=<%=OrgDetails.getAltId()%>">Scheda Impresa</a> -><a href="GestioneAnagraficaAction.do?command=ViewVigilanza&altId=<%=OrgDetails.getAltId()%>"> Controlli Ufficiali </a>-> <a href="GestioneAnagraficaVigilanza.do?command=TicketDetails&id=<%=TicketDetails.getIdControlloUfficiale()%>&altId=<%=OrgDetails.getAltId()%>">Scheda controllo</a>->CheckList</dhv:label>

</td>
</tr>
</table>

<dhv:permission name="accounts-accounts-report-view">
  <table width="100%" border="0">
    <tr>
       <td nowrap align="right">
        <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
          
          <%--if(OrgDetails.getCategoriaRischio() == -1){ %>
          
         <input type="button" value="Stampa Scheda Check List"	onClick='javascript:location.href="<%=OrgDetails.getAction() %>CheckList.do?command=Stampa&id=<%= Audit.getId() %>";'>
      <%
    }
    else {
    %>
        <input type="button" value="Stampa Scheda Check List"	onClick='javascript:location.href= "<%=OrgDetails.getAction() %>CheckList.do?command=Stampa&id=<%= Audit.getId() %>";'>
    <%	
    }
    --%>
      
       <%@ include file="../checklistdocumenti/checklist_stampa.jsp" %> 
       
      </td>
    </tr>
  </table>
</dhv:permission>
<%-- End Trails --%>
</dhv:evaluate>

<%
String nomeContainer = "gestioneanagrafica";
request.setAttribute("Operatore",OrgDetails.getOperatore());
String param = "altId="+OrgDetails.getAltId();

%>
<dhv:container name="<%=nomeContainer %>" selected="vigilanza" object="Operatore" param='<%= "altId=" + OrgDetails.getAltId() %>'>

<%if(TicketDetails.isCategoriaisAggiornata()==false)
{
	
	%>
  <input type="hidden" name="idC" value="<%=Audit.getIdControllo() %>">

     <dhv:permission name="accounts-accounts-audit-edit"><input type="submit" value="Modifica"></dhv:permission>
   
    <input type="hidden" name="idC" value="<%=Audit.getIdControllo() %>">
 
  <%} %>
<br/>
<br/>

<%@ include file="../checklist/anagrafica_checklist_view.jsp" %>


  <%if(TicketDetails.isCategoriaisAggiornata()==false){ 
  %>
     
    <dhv:permission name="accounts-accounts-audit-edit"><input type="submit" value="Modifica"></dhv:permission>
  
    
<%
  } %>


</dhv:container>


</form>