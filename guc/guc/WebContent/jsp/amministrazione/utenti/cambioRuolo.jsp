<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage=""%>
<%@page import="java.util.*" %>
<%@page import="it.us.web.bean.BUtente"%>
<%@page import="it.us.web.bean.BRuolo"%>
<%@page import="it.us.web.util.properties.Label"%>
   
   		<%
   		    BUtente utente = (BUtente)session.getAttribute("utente");
    		
   		    if(utente == null)
       		{
		%>
    			
<div class="titolo"> <%=Label.getSmart("accesso_pagina_non_loggato")%> </div>
		<%
			}    
		%>
			
	 
	 <div class="area-contenuti-1">
		<br/>
		<%
			BUtente but		= (BUtente)request.getAttribute("user_selected");
			String username	= but.getUsername();
			String nome		= but.getNome();
			String cognome	= but.getCognome();
		%>
		<span class="sottotitolo">
			Username: <%=username%><br />
			Nome: <%=nome%><br />
			Cognome: <%=cognome%><br /><br /><br />
		</span>
		<form action="utenti.CambioRuolo.us" method="post" onsubmit="return checkForm();">
			<p>
				<select id="ruoli" name="ruoli" size="1" >
				<option value="-1" selected="selected">Seleziona un ruolo</option>
				<%
					Vector v = (Vector)request.getAttribute("ruoli");
					Enumeration e = (Enumeration)v.elements();
				%>
				<%
					while( e.hasMoreElements() )
					{
						BRuolo br = (BRuolo)e.nextElement();
				%>
					<option value="<%=br.getRuolo().replace( "\"", "&quot;" ) %>"><%=br.getRuolo() %></option>
				<%	} %>
				
				</select> <font color="red">* Campo obbligatorio</font>
			</p>
			<p>
				<input type="hidden" name="user_id" value="<%=but.getId() %>"/>
				<input class="button" type="submit" value="Assegna ruolo"/>
			</p>
		</form>
		
	</div>
	
	<script type="text/javascript">
	function checkForm()
	{
		if( $('#ruoli')[0].value == "-1" )
		{
			alert( 'Selezionare un ruolo' );
			return false;
		}
		else
		{
			return true;
		}
	}
	</script>
	