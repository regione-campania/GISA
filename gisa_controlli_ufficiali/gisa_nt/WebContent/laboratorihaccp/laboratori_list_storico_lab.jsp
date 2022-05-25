<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.laboratorihaccp.base.*,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="SearchHistoryLabListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.laboratorihaccp.base.Organization" scope="request"/>
<jsp:useBean id="Ente" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="MatriciHaccp" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="DenominazioniHaccp" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ListaCampi" class="java.util.HashMap" scope="session"/>

<%@ include file="../initPage.jsp" %>

<% String param1 = "orgId=" + OrgDetails.getOrgId();
if(OrgDetails.getOrgId()== 0){
	param1 = "orgId=" + request.getAttribute("orgId");
}
Iterator iterator = ListaCampi.entrySet().iterator();

 
%>

<style type="text/css">
tr.d0 td {
	background-color: #FFFFCC; color: black;
}
tr.d1 td {
	background-color: #9999CC; color: black;
}
</style>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="LaboratoriHACCP.do?command=SearchForm"><dhv:label name="">Laboratori HACCP</dhv:label></a> > 
<%--if (request.getParameter("return") == null) { %>
<a href="LaboratoriHACCP.do?command=Search&tipoRicerca=<%= request.getAttribute("tipoRicerca")%>"><dhv:label name="">Risultati Ricerca</dhv:label></a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="LaboratoriHACCP.do?command=Dashboard">Cruscotto</a> >
<%}--%>
<a href="LaboratoriHACCPHistory.do?command=Storico&<%= param1 %>"><dhv:label name="">Visualizza Storico</dhv:label></a> >
<dhv:label name="">Storico per Laboratorio HACCP</dhv:label></a> >
</td>
</tr>
</table>
<%-- End Trails --%> 
 
<dhv:container name="laboratorihaccp" selected="Storico" object="OrgDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' hideContainer="<%= OrgDetails.isTrashed() %>">
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchHistoryLabListInfo"/>
<% int columnCount = 0; %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
   
   <th nowrap <% ++columnCount; %>>
        <strong>Data di modifica</strong>
   </th>
          
<zeroio:debug value='<%="JSP::accounts_list.jsp "+ SearchHistoryLabListInfo.getSearchOptionValue("searchcodeOrgSiteId")+" == "+(String.valueOf(Constants.INVALID_SITE)) %>'/>
        <th <% ++columnCount; %>>
          <strong><dhv:label name="">Campi modificati</dhv:label></strong>
        </th>
               
  </tr>

<%
	Iterator lab = OrgDetails.getLabList().iterator();
	if ( lab.hasNext() ) {
    int rowid = 0;
    int i = 0;
    while (lab.hasNext()) {
    i++;
    rowid = (rowid != 1 ? 1 : 2);
    Organization thisLab = (Organization)lab.next();
%>
		
<tr> 		
    <td>
	   <%= thisLab.getModifiedString() %>
	</td> 
	<td>
		<% if(thisLab.getName() != null && !(thisLab.getName().equals(""))) { %>
			<%= thisLab.getName() %> -
		<% } if(thisLab.getAccountNumber() != null && !(thisLab.getAccountNumber().equals(""))) { %>
			<%= thisLab.getAccountNumber() %> -
		<% } if(thisLab.getCognomeRappresentante() != null && !(thisLab.getCognomeRappresentante().equals(""))) {%>	
		<%= thisLab.getCognomeRappresentante() %> -
		<% } if(thisLab.getStato() != null && !(thisLab.getStato().equals(""))) { %>
		<%= thisLab.getStato() %> -
		<% } if(thisLab.getDataCambioStato() != null && !(thisLab.getDataCambioStato().equals(""))) { %>
			<b>Data stato:</b><%= thisLab.getDataCambioStatoFormat() %> 
		<% } %>
	</td>
   </tr>
   <%}%>
       
  <%}else{%>
   <tr class="containerBody">
      <td colspan="7">
        <dhv:label name="">Nessua Modifica per il Laboratorio selezionato.</dhv:label>
      </td>
   </tr>
   <%}%> 
   
</table>
<dhv:pagedListControl object="SearchHistoryLabListInfo"/>
</dhv:container>

