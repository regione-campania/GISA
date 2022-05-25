<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ include file="../initPage.jsp" %>

<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.campioni.base.Ticket" scope="request"/>
<jsp:useBean id="orgId" class="java.lang.String" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="DestinatarioCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@page import="org.aspcfs.modules.campioni.base.Analita"%>

<table class="trails" cellspacing="0" >
<tr>
<td>
<a href="OsaSearch.do">Operatori</dhv:label></a> > 
<a href="OsaSearch.do?command=Search"><dhv:label name=" ">Ricerca Operaoti</dhv:label></a> >
<a href="PrintModulesHTML.do?command=Prenota&orgId=<%=orgId%>"><dhv:label name=" ">Prenotazione Campione</dhv:label></a> >
<dhv:label name=" ">Dettaglio Campione Prenotato</dhv:label></a> 
</td>
</tr>
</table>

<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Dettaglio Campione Prenotato</dhv:label></strong>
    </th>
	</tr>
  
     <%@ include file="/campioni/campioni_view.jsp" %>
  
</table> <!--  chiusura tabella generale -->
