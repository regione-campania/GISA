<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:useBean id="listaDocumenti" class="java.util.ArrayList" scope="request"/>

<%@ page import="java.util.*" %>
<jsp:useBean id="tipo" class="java.lang.String" scope="request"/>



   <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
		<tr>
				<th><strong>Data creazione</strong></th>
				<th><strong>Generato da</strong></th>
				<th><strong>Recupera</strong></th>
		</tr>
	
			
			<%
	String[] split;
	if (listaDocumenti.size()>0)
		for (int i=0;i<listaDocumenti.size(); i++){
			split = listaDocumenti.get(i).toString().split(";;");
			
			
			%>
			
			<tr>
			<td><%= split[0] %></td>
			<td><%= split[2] %>
			</td> 
			<td>
			
			<a href="documentale.DownloadPdf.us?codDocumento=<%=split[5]%>&idDocumento=<%=split[6] %>"><input type="button" value="DOWNLOAD"></input></a>
			
			</td> 
		</tr>
		<%} %>
		
		</table>
	<br>

		

  <!-- dhv:pagedListControl object="AssetTicketInfo"/-->



</body>
</html>