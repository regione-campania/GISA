<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>
<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

<jsp:useBean id="Capo" class="org.aspcfs.modules.macellazioni.base.Capo" scope="request" />

<script>
function checkForm(form){
	var msg="";
	
	if (form.note.value=='')
		alert("Note di cancellazione obbligatorie");
	else{
		loadModalWindow();
		form.submit();
	}
	}

</script>




<form id="cancellazione" name="cancellazione"
	action="Macellazioni.do?command=CancellaCapo&auto-populate=true"
	method="post">

<table class="details" width="100%" cellpadding="4">
<tr><th colspan="2">Cancellazione</th></tr>

<tr>
<td>Matricola</td>
<td><%=Capo.getCd_matricola() %></td>
</tr>

<tr>
<td>Note cancellazione</td>
<td><textarea id="note" name="note" rows="5" cols="20"></textarea> <font color="red">*</font></td>
</tr>

<tr>
<td colspan="2"><input type="button" value="CONFERMA" onClick="checkForm(this.form)"/></td>
</tr>

</table>

<input type="hidden" id="idCapo" name="idCapo" value="<%=Capo.getId()%>"/> 
</form>



