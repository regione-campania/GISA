<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="orgId" class="java.lang.String" scope="request"/>

<script>
function checkForm(form) {
	if (form.stimaPopolazione.value == ''){
		alert('Selezionare un valore per STIMA POPOLAZIONE.');
		return false;
	}
	if (form.utilizzoSistemi.value == '') {
		alert('Selezionare un valore per UTILIZZO SISTEMI.');
		return false;
	}
	if (form.utilizzoSistemi.value == 'S' && (!form.retiProtezione.checked && !form.cannonciniDissuasori.checked && !form.dissuasoriAghi.checked && !form.dissuasoriSonori.checked && !form.altro.checked )) {
		alert('Selezionare almeno un valore per SE SI, QUALI.');
		return false;
	}
	
	if (confirm('Confermare? Una volta compilata, la scheda adeguamento non sarà modificabile.')){
	form.submit();
	}
}</script>

<center>
<font color="red" size="4px">ATTENZIONE! Per proseguire con l'aggiunta dei controlli ufficiali su questa anagrafica e' necessario compilare la scheda adeguamento denominata: <br/>
STIMA DEL PATRIMONIO DEL COLOMBO DI CITTA' NELLE AZIENDE AGRICOLE E/O ZOOTECNICHE. <br/>
Dopo la compilazione sara' possibile continuare a lavorare sull'anagrafica.<br/>
Si ricorda che la compilazione sara' storicizzata e non sara' possibile modificare la scheda in seguito se non contattando l'Help Desk.
 </font>


<form action="SchedeAdeguamentiAction.do?command=InsertSchedaPiccioni" method="post">
<table class="details">
<tr><th colspan="2">STIMA DEL PATRIMONIO DEL COLOMBO DI CITTA' NELLE AZIENDE AGRICOLE E/O ZOOTECNICHE</th></tr>
<tr><td>STIMA POPOLAZIONE COLOMBI</td><td> 
<input type="radio" id="stimaPopolazioneD" name="stimaPopolazione" value="D">0/100<br/>
<input type="radio" id="stimaPopolazioneA" name="stimaPopolazione" value="A">100/200<br/>
<input type="radio" id="stimaPopolazioneB" name="stimaPopolazione" value="B">250/500<br/>
<input type="radio" id="stimaPopolazioneC" name="stimaPopolazione" value="C">Oltre 500
</td></tr>

<tr><td>UTILIZZO SISTEMI ECOLOGICI DI DISSASUASIONE O PREVENZIONE</td><td>
<input type="radio" id="utilizzoSistemiS" name="utilizzoSistemi" value="S"> SI<br/>
<input type="radio" id="utilizzoSistemiN" name="utilizzoSistemi" value="N"> NO
</td></tr>

<tr><td>SE SI, QUALI</td><td>

<input type="checkbox" id="retiProtezione" name="retiProtezione" value="S"> Reti di protezione<br/>
<input type="checkbox" id="cannonciniDissuasori" name="cannonciniDissuasori" value="S"> Cannoncini dissuasori<br/>
<input type="checkbox" id="dissuasoriAghi" name="dissuasoriAghi" value="S"> Dissuasori ad aghi<br/>
<input type="checkbox" id="dissuasoriSonori" name="dissuasoriSonori" value="S"> Dissuasori sonori/visivi<br/>
<input type="checkbox" id="altro" name="altro" value="S"> Altro

</td></tr>

<tr>
<td colspan="2" align="center">
<input type="button" value="ANNULLA" onClick="window.location.href='SchedeAdeguamentiAction.do?command=ViewSchedaPiccioni&orgId=<%=orgId%>'"/>

<dhv:permission name="allevamenti-allevamenti-schedapiccioni-add">
<input type="button" value="SALVA SCHEDA COLOMBO DI CITTA'" onClick="checkForm(this.form)"/>
</dhv:permission>

</td>
</tr>

</table>

<input type="hidden" id="orgIdAllevamento" name="orgIdAllevamento" value="<%=orgId%>">


</form>

</center>