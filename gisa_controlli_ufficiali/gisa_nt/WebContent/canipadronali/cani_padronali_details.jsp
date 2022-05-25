<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="Proprietario" class ="org.aspcfs.modules.canipadronali.base.Proprietario" scope ="request" />
<jsp:useBean id="ListaAsl" class ="org.aspcfs.utils.web.LookupList" scope ="request" />

<%@ include file="../initPage.jsp" %>
<% String param1 = "orgId=" + Proprietario.getIdProprietario(); %>
<dhv:container name="canipadronalidet" selected="details" object="CaneDetails" param="<%= param1 %>" >
<table class="trails" cellspacing="0">
<tr>
<td>Cani Padronali> Anagrafica Cani di proprieta</td>
</tr>
</table>
<br>





 <jsp:include page="../schede_centralizzate/iframe.jsp">
    <jsp:param name="objectId" value="<%=Proprietario.getIdProprietario() %>" />
     <jsp:param name="tipo_dettaglio" value="31" />
</jsp:include>

</dhv:container>