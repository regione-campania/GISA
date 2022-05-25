<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="java.util.Iterator"%>
<%@page import="org.aspcfs.modules.allevamenti.base.Organization"%>
<jsp:useBean id="ListaAllevamenti" class="org.aspcfs.modules.allevamenti.base.OrganizationList" scope="request"/>

<script>
function setParentValue(ragione_sociale,id_allevamento)
{
	window.opener.document.getElementById("ragione_sociale_allevamento").value = ragione_sociale ;
	window.opener.document.getElementById("id_allevamento").value = id_allevamento ;
	window.opener.document.getElementById('azienda').innerHTML = ragione_sociale ;
	window.close();
}

</script>

<table class="details" width="100%">
<tr>
<th>Codice Azienda</th>
<th>Id Allevamento</th>
<th>Ragione Sociale</th>
<th>Specie Allevata</th>
</tr>

<%

if (ListaAllevamenti.size()!=0)
{
Iterator it = ListaAllevamenti.iterator();
while (it.hasNext())
{
	Organization allevamento = (Organization)it.next();
	%>
	<tr>
	<td><a href="#" onclick="setParentValue('<%=allevamento.getName() %>',<%=allevamento.getOrgId() %>)"><%=allevamento.getAccountNumber() %></a></td>
	<td><%=allevamento.getId_allevamento() %></td>
	<td><%=allevamento.getName() %></td>
	<td><%=allevamento.getSpecieAllev() %></td>
	</tr>
	
	<%
	}

}
else
{
	%>
	<tr>
	
	<td colspan="4">Nessun Allevamento Trovato Per il codice Azienda</td><tr>
	<%
}
%>