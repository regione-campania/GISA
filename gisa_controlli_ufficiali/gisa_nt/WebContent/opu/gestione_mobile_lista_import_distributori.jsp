<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.utils.InvioMassivoDistributori"%>
<%@page import="org.aspcfs.modules.distributori.base.Distrubutore"%>
<%@page import="java.util.ArrayList"%>
<jsp:useBean id="InvioMassivo" class="org.aspcfs.utils.InvioMassivoDistributori" scope="request"/>

<%
ArrayList<Distrubutore> listaRecordKO = (ArrayList<Distrubutore>)request.getAttribute("listaRecordKO");
%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<tr>
<th>
Data Import
</th>
<th>
Esito Import
</th>
<th>
Scarica File
</th>
</tr>
<%
ArrayList<InvioMassivoDistributori> listaInvii = (ArrayList<InvioMassivoDistributori>) request.getAttribute("listaInvii");
for(InvioMassivoDistributori invio : listaInvii)
{
%>
<tr>
<td><%=invio.getData() %></td>
<td><%=invio.getEsito() %></td>
<td><a href="OpuStab.do?command=DownloadFileEsitoImportDistributori&idInvio=<%=invio.getId() %>" >Scarica</a></td>
</tr>
<%
}
%>
</table>

<input type="button" value="ESCI" onClick="$( '#dialogImport' ).dialog('close');"/>
