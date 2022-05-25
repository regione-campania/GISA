<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>


<%@page import="it.us.web.bean.BUtente"%>
<%@page import="it.us.web.bean.BRuolo"%>
<%@page import="it.us.web.bean.BGuiView"%>
<%@page import="it.us.web.util.properties.Message"%>
<%@page import="it.us.web.permessi.Permessi"%>
<%@page import="java.util.Vector"%><html xmlns="http://www.w3.org/1999/xhtml" xml:lang="it" lang="it">

    
    <%
    	BUtente utente = (BUtente)session.getAttribute("utente");
    	Vector<BRuolo> ruoli = (Vector<BRuolo>)request.getAttribute("ruoli");
    	BGuiView bGuiView = (BGuiView)request.getAttribute("bGuiView");
    %>
    
   
	 <div class="titolo">Permessi associati ai ruoli per la funzione<br/>  <%=bGuiView.getNome_funzione()%> -> <%=bGuiView.getNome_subfunzione()%> -> <%=bGuiView.getNome_gui()%></div>
	 
	 <div class="area-contenuti-2" style="width:50%">
		 <br/>
<%
			if(ruoli!=null && !ruoli.isEmpty())
			{
%>
			<table class="griglia">
				<tr>
					<th>Ruolo</th>
					<th>Permesso</th>
				</tr>
<%	
				int i=0;
				while(i<ruoli.size())
				{
			%>
				<tr class="<%=(i%2 == 0) ? ("even") : ("odd") %>">
					<td><%=ruoli.elementAt(i).getRuolo()%></td>
					<td><%=Permessi.getPermissions( ruoli.elementAt(i).getRuolo() , bGuiView )%></td>
				</th>
			<%
				i++;
				}
				out.println("</table>");
			}
			%>
	<br/>
	<input type="button" class="button" onclick="window.close()" value="Chiudi POPUP"/>
		
	</div>
