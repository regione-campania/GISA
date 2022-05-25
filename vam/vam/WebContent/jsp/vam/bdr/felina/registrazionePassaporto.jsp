<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>

<%@page import="it.us.web.bean.BUtente"%>
<%@page import="java.util.Date"%>
<script language="Javascript">
function isnum(obj) 
{
   if (obj==null || obj.value==null || obj.value=="")
   {
      alert('Inserire il numero passaporto.');
      obj.value="";
      obj.focus();
      return false;
   }
   else if(obj.value.length!=13)
   {
      alert('Il numero passaporto deve essere composto da 13 caratteri alfanumerici.');
      obj.value="";
      obj.focus();
      return false;
   }
   return true;
}

function checkform()
{
	if(isnum(document.getElementById('numeroPassaporto')))
		if(myConfirm('Sicuro di voler procedere col salvataggio della registrazione di rilascio passaporto in BDR?'))
			location.href='vam.bdr.felina.Passaporto.us?idAccettazione='+document.getElementById('idAccettazione').value+'&dataPassaporto='+document.getElementById('dataPassaporto').value+'&numeroPassaporto='+document.getElementById('numeroPassaporto').value+'&notePassaporto='+document.getElementById('notePassaporto').value;
}
</script>
<form action="vam.bdr.felina.Passaporto.us" method="post"><input
	type="button" value="Procedi" onclick="checkform();" />

<fieldset><legend>Registrazione Rilascio Passaporto
${accettazione.animale.lookupSpecie.description }
${accettazione.animale.identificativo }</legend> <input type="hidden"
	id="idAccettazione" name="idAccettazione" value="${accettazione.id }" />

<table class="tabella">
	<tr class="even">
		<td>Data Rilascio Passaporto</td>
		<td><input type="text"
			value="<fmt:formatDate type="date" value="<%=new Date() %>" pattern="dd/MM/yyyy" />"
			id="dataPassaporto" name="dataPassaporto" id="dataPassaporto"
			maxlength="10" size="10" readonly="readonly" /> <img
			src="images/b_calendar.gif" alt="calendario" id="id_img_1" /> <script
			type="text/javascript">
							Calendar.setup({
								inputField  :    "dataPassaporto",      // id of the input field
								ifFormat    :    "%d/%m/%Y",  // format of the input field
								button      :    "id_img_1",  // trigger for the calendar (button ID)
								// align    :    "rl,      // alignment (defaults to "Bl")
								singleClick :    true,
								timeFormat	:   "24",
								showsTime	:   false
							});					    
						</script></td>
	</tr>
	<tr class="odd">
		<td>Numero Passaporto</td>
		<td><input id="numeroPassaporto" type="text" size="30"
			maxlength="13" name="numeroPassaporto" /></td>
	</tr>
	<tr class="even">
		<td>Note Passaporto</td>
		<td><textarea rows="3" cols="30" name="notePassaporto" id="notePassaporto"
			maxLength="150"></textarea></td>
	</tr>
</table>
</fieldset>

<br />
<input type="button" value="Procedi" onclick="checkform();" /></form>
