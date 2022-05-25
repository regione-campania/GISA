<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage=""%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="US" %>
   		
<%@page import="java.util.Vector"%>
<%@page import="it.us.web.bean.BUtente"%>
<%@page import="it.us.web.util.properties.Label"%>
<%@page import="it.us.web.bean.BRuolo"%>

  <% 
  	Vector ruoli = (Vector)request.getAttribute("ruoli");
  	String  ruolo = ((String)request.getAttribute("ruolo")!=null)?((String)request.getAttribute("ruolo")):("");
    String descrizione = ((String)request.getAttribute("descrizione")!=null)?((String)request.getAttribute("descrizione")):("");
    String ruoloDaClonare = ((String)request.getAttribute("ruoloDaClonare")!=null)?((String)request.getAttribute("ruoloDaClonare")):("");
    BUtente utente = (BUtente)session.getAttribute("utente");
  %> 

<div class="contenuti-logged">
    
   		<%
    		if(utente == null)
       		{
		%>
    			<div class="titolo"> <%=Label.getSmart("accesso_pagina_non_loggato")%> </div>
		<%
			}
   		%>
		
			<div class="titolo">Definisci un nuovo ruolo:</div>
	 
	 <div class="area-contenuti-1">
		<br/>
			
	        <table>
	        <tr>
	        	<td>
       			<form action="ruoli.Add.us" method="post" onsubmit="return checkForm();">
       		<table class="menu">       
      			<tr>
      				<td>Ruolo: </td>
      				<td> <input id="ruoloField" maxlength="45" type="text" name="ruolo" value="<%=ruolo%>"/> <font color="red">*</font> </td>
      			</tr>
      			<tr>
      				<td>Descrizione: </td>
      				<td> <input id="descrizioneField" maxlength="80" type="text" name="descrizione" value="<%=descrizione%>"/> <font color="red">*</font> </td>
      			</tr>
      			<tr>
      				<td nowrap >Copia funzioni da:</td>
      				<td> <select name="ruoloDaClonare" /> 
      					<option value="">Non selezionato</option>
      					<%
      						int i = 0;
      						while(i<ruoli.size())
      						{
      							BRuolo ruoloB = (BRuolo)ruoli.elementAt(i);
      					%>
      					    <option value="<%=ruoloB.getRuolo()%>"
      					<%
      						if(ruoloB.getRuolo().equals(ruoloDaClonare))
      						{
      					%>    
      						selected="selected"
      					<%
      						}
      					%>
      					> <%=ruoloB.getRuolo()%> 
      					
      					    </option>
      					<%
      						i++;
      						}
      					%>
      				</td>
      			</tr>
      			<tr>
      				<td>
      					<font color="red">Campi obbligatori</font><br/> 
      					<input class="button" type="submit" value="Crea Ruolo" /><br/>
      				</td>
      			</tr>
			</table>
			</form>
			</td>
			</tr>
		

		
		 	<br/>
			 
		
			</table> 
		
		</div>
		
<script type="text/javascript">
function checkForm()
{
	if( $('#ruoloField')[0].value.length > 0 && $('#descrizioneField')[0].value.length > 0  )
	{
		return true;
	}
	else
	{
		alert( "Inserire un ruolo ed una descrizione" );
		return false;
	}
}
</script>
