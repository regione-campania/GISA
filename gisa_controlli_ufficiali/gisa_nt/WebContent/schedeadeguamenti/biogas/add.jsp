<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="orgId" class="java.lang.String" scope="request"/>
<jsp:useBean id="altId" class="java.lang.String" scope="request"/>

<script>
function checkForm(form) {
	if (form.impiantoBiogas.value == ''){
		alert('Selezionare un valore per IMPIANTO BIOGAS.');
		return false;
	}
	
	if (form.impiantoBiogas.value == 'S' && form.tipologiaBiogas.value == '') {
		alert('Selezionare un valore per TIPOLOGIA BIOGAS');
		return false;
	}
	
	if (confirm('Confermare? Una volta compilata, la scheda adeguamento non sarà modificabile.')){
	form.submit();
	}
}

function checkImpianto(val){
	if (val.value == 'S'){
		document.getElementById("tipologiaBiogasR").disabled = false;
		document.getElementById("tipologiaBiogasD").disabled = false;
	}
	else {
		document.getElementById("tipologiaBiogasR").disabled = true;
		document.getElementById("tipologiaBiogasD").disabled = true;
		document.getElementById("tipologiaBiogasR").checked = false;
		document.getElementById("tipologiaBiogasD").checked = false;
	}
}

</script>

<center>
<font color="red" size="4px">ATTENZIONE! Per proseguire con l'aggiunta dei controlli ufficiali su questa anagrafica e' necessario compilare la scheda adeguamento denominata: <br/>
INFORMAZIONI IMPIANTO BIOGAS <br/>
Dopo la compilazione sara' possibile continuare a lavorare sull'anagrafica.<br/>
Si ricorda che la compilazione sara' storicizzata e non sara' possibile modificare la scheda in seguito se non contattando l'Help Desk.
 </font>


<form action="SchedeAdeguamentiAction.do?command=InsertSchedaBiogas" method="post">
<table class="details">
<tr><th colspan="2">INFORMAZIONI IMPIANTO BIOGAS</th></tr>
<tr><td>E' PRESENTE UN IMPIANTO BIOGAS?</td><td> 
<input type="radio" id="impiantoBiogasS" name="impiantoBiogas" value="S" onClick="checkImpianto(this)">SI<br/>
<input type="radio" id="impiantoBiogasN" name="impiantoBiogas" value="N" onClick="checkImpianto(this)">NO
</td></tr>

<tr><td>TIPOLOGIA</td><td>
<input type="radio" disabled id="tipologiaBiogasR" name="tipologiaBiogas" value="R">Riconosciuto<br/>
<input type="radio" disabled id="tipologiaBiogasD" name="tipologiaBiogas" value="D">Deroga
</td></tr>

<tr>
<td colspan="2" align="center">
<input type="button" value="ANNULLA" onClick="window.location.href='SchedeAdeguamentiAction.do?command=ViewSchedaBiogas&orgId=<%=orgId%>'"/>

<dhv:permission name="adeguamenti-schedabiogas-add">
<input type="button" value="SALVA SCHEDA BIOGAS" onClick="checkForm(this.form)"/>
</dhv:permission>

</td>
</tr>

</table>

<input type="hidden" id="orgIdBiogas" name="orgIdBiogas" value="<%=orgId%>">
<input type="hidden" id="altIdBiogas" name="altIdBiogas" value="<%=altId%>">


</form>

</center>