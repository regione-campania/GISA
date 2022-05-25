<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ include file="../initPage.jsp" %>

<table class="trails" cellspacing="0">
<tr>
<td>
<a href="LaboratoriHACCP.do?command=SearchForm"><dhv:label name="">Laboratori HACCP</dhv:label></a> > 
<%--if (request.getParameter("return") == null) { %>
<a href="LaboratoriHACCP.do?command=Search&tipoRicerca=<%= request.getAttribute("tipoRicerca")%>"><dhv:label name="">Risultati Ricerca</dhv:label></a> >
<%} else if (request.getParameter("return").equals("dashboard")) {%>
<a href="LaboratoriHACCP.do?command=Dashboard">Cruscotto</a> >
<%}--%>
<a href="LaboratoriHACCP.do?command=Details&tipoRicerca=prova&orgId=<%= request.getAttribute("orgId") %>"><dhv:label name="">Scheda Laboratorio HACCP</dhv:label></a> > 
<dhv:label name="">Storico Laboratorio HACCP</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:container name="laboratorihaccp" selected="Storico">
<html>
	<a href="LaboratoriHACCPHistory.do?command=StoricoProve&tipoStorico=lab&orgId=<%= request.getAttribute("orgId") %>">Visualizza Storico Laboratori</a> &nbsp
	<a href="LaboratoriHACCPHistory.do?command=StoricoProve&tipoStorico=prove&orgId=<%= request.getAttribute("orgId") %>">Visualizza Storico Prove</a>
</html>
</dhv:container>