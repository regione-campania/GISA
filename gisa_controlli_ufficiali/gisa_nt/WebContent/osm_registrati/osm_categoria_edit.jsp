<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<script>
function aggiornamento(orgId,id)
{
	
	opener.document.details.action ="OsmRegistratiVigilanza.do?command=TicketDetails&orgId="+document.aggiornacategoria.orgId.value+"&id="+document.aggiornacategoria.ticketid.value;
	opener.document.details.submit();
	window.close();
}
</script>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<%
String orgId 	= (String)request.getAttribute("orgId");
String ticketid = (String)request.getAttribute("ticketid");		 
%>
<script>
function check(){
if (document.aggiornacategoria.data.value=="")
{
	alert("Controlla di aver inserito la data di prossimo controllo");
	
}
else
{
	document.aggiornacategoria.submit();
}

	
}

</script>

<form method="post"  name = "aggiornacategoria" action="OsmRegistratiVigilanza.do?command=AggiornaOsm">
<input type = "hidden" name = "orgId" value = "<%=orgId %>">
<input type = "hidden" name = "ticketid" value = "<%=ticketid %>">
<%if (request.getAttribute("Aggiornamento")!=null)
	{%>
	<font color = "red">Categoria Aggiornata Correttamente</font>
	<%}%>

<table cellpadding="4" cellspacing="0" width="100%" class="details">
<tr>
	<th colspan="2"><strong>Aggiorna Categoria Di Rischio</strong></th>
</tr>
<tr class="containerBody">
 	<td nowrap class="formLabel">Categoria di Rischio</td>
	<td><select name = "categoria"> 
			<option value = "1">1</option>
			<option value = "2">2</option>
			<option value = "3">3</option>
			<option value = "4">4</option>
			<option value = "5">5</option>
		</select>
	</td>
</tr>
<tr class="containerBody">
 	<td nowrap class="formLabel">Data</td>
	<td><input type="text" readonly="readonly" name="data" size="10" value="" />&nbsp;<a href="javascript:popCalendar('aggiornacategoria','data','it','IT','Europe/Berlin');"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a></td>
<font color = "red">*</font>
</tr>
</table>
<input type = "button" onclick="check()" value = "Aggiorna">
</form>
	
	<%if (request.getAttribute("Aggiornamento")!=null)
		
	{%>
	<script>
	aggiornamento();
	
	
	
	</script>
	
	<%}%>