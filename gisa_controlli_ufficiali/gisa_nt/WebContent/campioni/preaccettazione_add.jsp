<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<% 
String idAnagrafica = request.getParameter("preaccAnagraficaId");
String idCampione = request.getParameter("preaccCampioneId");
String idEnte = request.getParameter("preaccEnteId");
String idLaboratorio = request.getParameter("preaccLaboratorioId");
String idUtente = request.getParameter("preaccUserId");
%>

<script>
function getElencoDaLinea(identificativo_linea, id_ente, id_laboratorio)
{
	DwrPreaccettazione.Preaccettazione_GetElencoDaLinea(identificativo_linea, id_ente, id_laboratorio, {callback:getElencoDaLineaCallBack,async:false});
}
function getElencoDaLineaCallBack(returnValue)
{
	
	var obj;
	obj = JSON.parse(returnValue);
	
	var codiceHtml = '<center><b><font color="red">I nuovi codici preaccettazione possono essere generati direttamente dal dettaglio anagrafica tramite il bottone GENERA PREACCETTAZIONE.</font></b></center>';
	var len= obj.length;
	
	if (len > 0)	{
		codiceHtml = codiceHtml + '<table width= 100% id="tabCodPreacc" border = "2" cellpadding="5" cellspacing="5" class="details">' +
		'<tr><th colspan="8">LISTA CODICI DI PREACCETTAZIONE ASSOCIATI ALL ANAGRAFICA E AL LABORATORIO DI DESTINAZIONE DI QUESTO CAMPIONE</th></tr>'+
		'<tr>' +	
		'<th style="text-align:center">codice preaccettazione</th>' +
		'<th style="text-align:center">matrice campione</th>' +
		'<th style="text-align:center">quesito diagnostico</th>' +
		'<th style="text-align:center">data preaccettazione</th> ' +
		'<th style="text-align:center">ente</th> ' +
		'<th style="text-align:center">laboratorio</th> ' +
		'<th style="text-align:center">utente</th>' +
		'<th style="text-align:center" width= 10%>usa codice</th>' +
		'</tr>';

for( i = 0; i <len ; i++) {
codiceHtml = codiceHtml + '<tr>' +
				'<td style="text-align:center">'+ obj[i].codice +'</td>' +
				'<td style="text-align:center">'+ obj[i].desc_matrice +'</td>' +
				'<td style="text-align:center">'+ obj[i].desc_quesito +'</td>' +
				'<td style="text-align:center">'+ obj[i].data +'</td>' +
				'<td style="text-align:center">'+ obj[i].ente +'</td>' +
				'<td style="text-align:center">'+ obj[i].laboratorio +'</td>' +
				'<td style="text-align:center">'+ obj[i].username +'</td>' +
				'<td align="center"> <input type="radio" id="codpreac" name="codpreac"' +
				'value="'+ obj[i].codice +'" onClick="checkCodicePreaccettazione(this)"/></td>'+
				'</tr>';
}
codiceHtml = codiceHtml + '</table><br/><br/>';
} else {
	codiceHtml = codiceHtml + '<center><b>NON RISULTANO CODICI PREACCETTAZIONE ASSOCIATI ALL ANAGRAFICA E AL LABORATORIO DI DESTINAZIONE DI QUESTO CAMPIONE.</b></center>';
}
	document.getElementById("divCodiciPreaccettazione").innerHTML = codiceHtml;
	return returnValue;
}


function checkCodicePreaccettazione(radio){
if (confirm('Associare il codice preaccettazione selezionato al campione?')) {
	associaCampione('<%=idCampione%>', '<%=idUtente%>', radio.value);
}
else {
	radio.checked = false;
}
}

function associaCodiceAlCampione(idCampione, idUtente, idCodicePreacc){
	loadModalWindow();
	associaCampione(idCampione, idUtente, idCodicePreacc);
}

function associaCampione(idCampione, userId, idCodicePreacc){
	DwrPreaccettazione.Preaccettazione_Associacampione(idCampione, userId, idCodicePreacc, {callback:associaCampioneCallBack,async:false});
}
function associaCampioneCallBack(returnValue)
{
 	var obj;
 	obj = JSON.parse(returnValue);
 	var messaggio = obj.messaggio;
 	if (messaggio == 'ok'){
 		alert('Codice preaccettazione associato correttamente.');
 		window.location.href="Vigilanza.do?command=CampioneDetails&id=<%=idCampione%>";
 	}
 	else {
 		alert(messaggio);
 	}
     loadModalWindowUnlock();
}

function gestionePreaccettazione(){
	if (document.getElementById("divGestionePreaccettazione").style.display=="none")
		document.getElementById("divGestionePreaccettazione").style.display="block";
}
</script>



<div id="divGestionePreaccettazione" style="display:none">
<div id ="divCodiciPreaccettazione" name="divCodiciPreaccettazione"></div>
<br/><br/><br/><br/>
</div>

<script>
window.onload = function() {

if (document.getElementById("codpreacc")==null || document.getElementById("codpreacc").innerHTML == ''){ 
		getElencoDaLinea("<%=idAnagrafica%>", "<%=idEnte%>", "<%=idLaboratorio%>");
		document.getElementById("divGestionePreaccettazione").style.display="block";
}
};
</script>