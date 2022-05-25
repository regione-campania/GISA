<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="Partita" class="org.aspcfs.modules.macellazioninewopu.base.Partita" scope="request" />
<jsp:useBean id="Error" class="java.lang.String" scope="request" />


<table class="details" width="100%" cellpadding="4">
<tr><th colspan="2">Errore cancellazione</th></tr>

<tr>
<td>Numero</td>
<td><%=Partita.getCd_partita()%></td>
</tr>

<tr>
<td colspan="2"><%=Error %></td>
</tr>

<tr>
<td colspan="2"><input type="button" value="CHIUDI" onClick="window.close()"/></td>
</tr>

</table>

