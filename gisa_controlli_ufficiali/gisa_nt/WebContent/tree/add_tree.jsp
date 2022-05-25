<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="java.util.ArrayList"%>

<%
ArrayList<String> listaCombo = (ArrayList<String>)request.getAttribute("ListaCombo");
%>

<%@page import="java.sql.SQLException"%><form method = "post" action="Tree.do?command=CreateTree">
<table>
<tr>
<td>Combo di PartenzA</td>
<td>
<SELECT name = "tabella">
<option value = "-1">SELEZIONA VOCE</option>
<%
	for(String tabella : listaCombo)
	{
		%>
		<option value = "<%=tabella %>"><%=tabella %></option>
		<%
	}
%>
</SELECT>
</td>
</tr>
<tr>
<td>Nome Relazione</td>
<td>
<input type = "text" name = "nomeRelazione">
</td>
</tr>
<tr>
<td>Descrizione</td>
<td>
<textarea rows="6" cols="30" name = "descrizione" ></textarea>
</td>
</tr>
</table>
<input type = "submit" value = "Crea">
</form>

<%if(request.getAttribute("Errore")!=null) 
{
SQLException error = (SQLException) request.getAttribute("Errore");
%>
<font color="red">
Attenzione! Verificare il seguente errore : <%=error.getMessage()%>
</font>
<%}%>