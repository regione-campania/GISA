<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<input type="hidden" id="esito_laboratorio" value="" >
<input type="hidden" id="user_id_call_wslaboratorio" value="<%=User.getUserId()%>">

<script>

function ritornoDaLaboratorio(cod_preac, id_utente, id_laboratorio){
	DwrPreaccettazione.Preaccettazione_Ritorno_Da_Laboratorio(cod_preac, id_utente, id_laboratorio, {callback:ritornoDaLaboratorioCallBack,async:false});
}
function ritornoDaLaboratorioCallBack(returnValue)
{
	var dati = returnValue;
	
	if(returnValue.trim() != ''){
		
		document.getElementById("esito_laboratorio").value = returnValue;
		var campo_esito = null;
		if(document.getElementById('esito_note_esame')){
			campo_esito = document.getElementById('esito_note_esame');
			campo_esito.value = document.getElementById('esito_laboratorio').value;
			campo_esito.cols = "170";
			campo_esito.removeAttribute('onkeyup');
			campo_esito.removeAttribute('onpaste');
			campo_esito.readOnly = true;
			campo_esito.style.resize = 'none';
			campo_esito.style.display = 'none';
			campo_esito.insertAdjacentHTML('afterend', '<div>' + document.getElementById('esito_laboratorio').value + '</div>');
		} else if(document.getElementsByName('esito_note_esame')){
			campo_esito = document.getElementsByName('esito_note_esame')[0];
			campo_esito.value = document.getElementById('esito_laboratorio').value;
			campo_esito.cols = "170";
			campo_esito.removeAttribute('onkeyup');
			campo_esito.removeAttribute('onpaste');
			campo_esito.readOnly = true;
			campo_esito.style.resize = 'none';
			campo_esito.style.display = 'none';
			campo_esito.insertAdjacentHTML('afterend', '<div>' + document.getElementById('esito_laboratorio').value + '</div>');
		}
	}
}

if(document.getElementById("rigaCodPreacc")){
	if(document.getElementById("rigaCodPreacc").style.display != 'none'){
		
		var cod_preacc = document.getElementById("codpreacc").innerHTML;
		var user_chiamate_laboratorio = document.getElementById('user_id_call_wslaboratorio').value;
		var id_laboratorio = document.getElementsByName("destinatarioCampione")[0].value;
		ritornoDaLaboratorio(cod_preacc, user_chiamate_laboratorio, id_laboratorio);
	}
}

</script>
