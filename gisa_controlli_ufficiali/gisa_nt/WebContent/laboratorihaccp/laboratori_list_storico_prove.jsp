<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.laboratorihaccp.base.*,org.aspcfs.modules.contacts.base.*, org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="SearchHistoryProveListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.laboratorihaccp.base.Organization" scope="request"/>
<jsp:useBean id="Ente" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="MatriciHaccp" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="DenominazioniHaccp" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ListaCampi" class="java.util.HashMap" scope="session"/>
<%@ include file="../initPage.jsp" %>

<% String param1 = "orgId=" + OrgDetails.getOrgId();
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
<a href="LaboratoriHACCP.do?command=Details&tipoRicerca=prova&<%= param1 %>"><dhv:label name="">Scheda Laboratorio HACCP</dhv:label></a> > 
<a href="LaboratoriHACCPHistory.do?command=Storico&<%= param1 %>"><dhv:label name="">Visualizza Storico per Laboratorio HACCP</dhv:label></a> >
<dhv:label name="">Storico Prove per Laboratorio HACCP</dhv:label></a> 
</td>
</tr>
</table>
<%-- End Trails --%> 
 
<dhv:container name="laboratorihaccp" selected="Storico" object="OrgDetails" param="<%= param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' hideContainer="<%= OrgDetails.isTrashed() %>">
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchHistoryProveListInfo"/>
<% int columnCount = 0; %>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
   
   <th nowrap <% ++columnCount; %>>
        <strong>Data</strong>
   </th>
    
   <th nowrap <% ++columnCount; %>>
        <strong>Id Prova</strong>
	</th>
        
<zeroio:debug value='<%="JSP::accounts_list.jsp "+ SearchHistoryProveListInfo.getSearchOptionValue("searchcodeOrgSiteId")+" == "+(String.valueOf(Constants.INVALID_SITE)) %>'/>
        <th <% ++columnCount; %>>
          <strong><dhv:label name="">Campi modificati</dhv:label></strong>
        </th>      
  </tr>

<%
	Iterator prove = OrgDetails.getProveList().iterator();
	if ( prove.hasNext() ) {
    int rowid = 0;
    int i = 0;
    while (prove.hasNext()) {
    i++;
    rowid = (rowid != 1 ? 1 : 2);
    Prova thisProva = (Prova)prove.next();
%>
		
<tr> 		
    <td>
	   <%= thisProva.getModifiedString() %>
	</td>
	<td>
		<%= thisProva.getIdProva()%>
	</td>
	 
	<td>
		<% if( MatriciHaccp.getSelectedValue(thisProva.getCodiceMatrice()) != null && !(MatriciHaccp.getSelectedValue( thisProva.getCodiceMatrice())).equals("")) { %>
			<%= MatriciHaccp.getSelectedValue( thisProva.getCodiceMatrice()) %> -
		<% } if( DenominazioniHaccp.getSelectedValue( thisProva.getCodiceDenominazione()) != null && !(DenominazioniHaccp.getSelectedValue( thisProva.getCodiceDenominazione())).equals("")) {  %>	
		<%= DenominazioniHaccp.getSelectedValue( thisProva.getCodiceDenominazione()) %> -
		<% } if(thisProva.getNorma() != null && !thisProva.getNorma().equals("")) { %>
			<%= thisProva.getNorma()  %> -
		<% } %>	
			<%= toHtml(((thisProva.getAccreditata()==true)?("Accreditata"):("In Accreditamento")))%> -
		<% if( Ente.getSelectedValue( thisProva.getCodiceEnte()) != null && !(Ente.getSelectedValue( thisProva.getCodiceEnte())).equals("")) {  %>
			<%= Ente.getSelectedValue( thisProva.getCodiceEnte()) %>
		<% } if(thisProva.getDecreto() != null && !thisProva.getDecreto().equals("")) { %>
			<%= toHtml(thisProva.getDecreto()) %>
		<% } %>
		
	</td>
   </tr>
   <%}%>
       
  <%}else{%>
   <tr class="containerBody">
      <td colspan="7">
        <dhv:label name="">Nessun Elenco Prove Modificate.</dhv:label>
      </td>
   </tr>
   <%}%> 
   
</table>
<dhv:pagedListControl object="SearchHistoryProveListInfo"/>
</dhv:container>
