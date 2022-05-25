<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>

<%@page import="org.aspcfs.modules.stabilimenti.base.PermessoVisibilitaStabilimenti"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%><jsp:useBean id="StatiStabilimenti" class="org.aspcfs.utils.web.LookupList" scope="request" />

<%
ArrayList<PermessoVisibilitaStabilimenti> lista_permessi = (ArrayList<PermessoVisibilitaStabilimenti>) request.getAttribute("lista_permessi");
%>

<form method="post" action="Stabilimenti.do?command=SavePermessiVisibilita">
<br/>
<table class = "details">
<tr>
<th>Ruolo</th>
<th colspan="<%=lista_permessi.size() %>">Visibilità su Stati</th>
</tr>
<tr class = "containerBody">
<td>&nbsp;</td>
<input type = "hidden" name = "numRuoli" value = "<%=lista_permessi.size() %>"/>
<input type = "hidden" name = "numStati" value = "<%=StatiStabilimenti.size() %>"/>
<%

for(int i = 0 ; i <StatiStabilimenti.size(); i++)
{
	LookupElement tmp = (LookupElement)StatiStabilimenti.get(i) ;
	if(tmp.getCode()!=0)
	{
%>
<td><%=tmp.getDescription() %></td>
<%		
}
}
%>
</tr>
<%
int indiceRuolo = 0 ;
for(PermessoVisibilitaStabilimenti permesso : lista_permessi)
{
	%>
	<tr>
	<td class = "formLabel">
		<input type = "hidden" name = "ruolo_<%=indiceRuolo %>" value = "<%=permesso.getIdRuolo() %>">
		<%=permesso.getDescrizioneRuolo() %>
	</td>
	<%
		for(int i = 0; i<StatiStabilimenti.size(); i++)
		{
			LookupElement tmp = (LookupElement)StatiStabilimenti.get(i) ;
			if(tmp.getCode()!=0)
			{
			%>
			<td>
				<input type="checkbox" name="permesso_<%=indiceRuolo %>_<%=i %>" <%if(permesso.getLista_stati().contains(tmp.getCode())){ %>checked="checked" <%} %> value="<%=tmp.getCode() %>">
			</td>
			<%
			}
		}
	
	%>
	
	</tr>
	
	
	<%
	indiceRuolo ++ ;
}
%>
</table>
<input type = "submit" value = "Salva">
</form>
