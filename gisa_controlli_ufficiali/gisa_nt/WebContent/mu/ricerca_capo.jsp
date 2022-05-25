<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script>
function checkForm(form){
	var matricola = form.matricola.value;
	var message = '';
	var esito = true;
	if (matricola.length < 5){
		message += 'Inserire almeno cinque caratteri nel campo MATRICOLA.';
		esito = false;
	}
		if (esito==false)
			alert (message);
		return esito;
}
</script>

<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="MacellazioneUnica.do?command=List&orgId=51251">Home macellazioni
		</td>
	</tr>
</table>

 <form name="ricercaCapo" id="ricercaCapo" action="MacellazioneUnica.do?command=CercaCapo" method="post" >
 
<input type="hidden" id="idMacello" name="idMacello" value="<%=request.getParameter("orgId")%>"/> 
<table class="details" layout="fixed" width="50%">
<col width="180px">
<th colspan="2">Ricerca capo</th> 
<tr><td class="formLabel">Matricola</td> <td><input type="text" id="matricola" name="matricola"/></td></tr>
</table>

<input type="button" value="CERCA" onClick="if (checkForm(this.form)){this.form.submit();}"/> 

</form>