<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.Map.Entry"%>
<%@ include file="../../../initPage.jsp" %>

<jsp:useBean id="campiHash" class="java.util.LinkedHashMap" scope="request"/>

<% if (campiHash.size()>0) {%>
<table width="100%">
<col widh="10%">
<col widh="90%">
<th align="center" colspan="2" bgcolor="#5CB8E6">Dati estesi</th>

<%
Set<Entry> entries = campiHash.entrySet();
for (Entry elemento : entries) 
{
%>
<tr><td width="15%" class="formLabel"> 
<%=elemento.getKey() %>
</td>

<td>
<%=elemento.getValue() %>
</td></tr>

<% } %>

</table>
<% } %>


