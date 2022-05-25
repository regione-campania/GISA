<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.json.*"%>
<jsp:useBean id="jsonMotivi" class="java.lang.String" scope="request"/>


<%if (jsonMotivi!=null && !jsonMotivi.equals("")){%>

<table class="details">
<col width="10%"><col width="5%"><col width="45%"><col width="30%">
<tr><th>Tipo motivo</th><th>Alias</th><th>Descrizione</th><th>Per conto di</th></tr>

<% 
	JSONArray jsonMotiviArray = new JSONArray(jsonMotivi);
	for (int j = 0; j<jsonMotiviArray.length(); j++){
	JSONObject jsonMotivo = (JSONObject) jsonMotiviArray.get(j);

%>

<tr><td><%=jsonMotivo.get("tipo_motivo") %></td><td><%=jsonMotivo.get("alias_motivo") %></td><td><%=jsonMotivo.get("descrizione_motivo").toString().replace(">>", ">><br/>") %></td><td><%=jsonMotivo.get("per_conto_di").toString().replace("->", "-><br/>") %></td></tr>

<% } %>
</table>
<% } else { %>
Non sono presenti motivi.
<% } %>


