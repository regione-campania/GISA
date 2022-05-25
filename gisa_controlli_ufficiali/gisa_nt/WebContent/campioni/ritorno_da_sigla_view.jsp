<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<input type="hidden" id="esito_sigla" value="" >
<input type="hidden" id="user_id_call_wssigla" value="<%=User.getUserId()%>">

<script>

function ritornoDaSigla(cod_preac, id_utente){
	DwrPreaccettazione.Preaccettazione_Ritorno_Da_Sigla(cod_preac, id_utente, {callback:ritornoDaSiglaCallBack,async:false});
}
function ritornoDaSiglaCallBack(returnValue)
{
	var dati = returnValue;
	
	if(returnValue.trim() != ''){
		
		document.getElementById("esito_sigla").value = returnValue;
		var campo_esito = null;
		if(document.getElementById('esito_note_esame')){
			campo_esito = document.getElementById('esito_note_esame');
			campo_esito.value = document.getElementById('esito_sigla').value;
			campo_esito.cols = "170";
			campo_esito.removeAttribute('onkeyup');
			campo_esito.removeAttribute('onpaste');
			campo_esito.readOnly = true;
			campo_esito.style.resize = 'none';
			campo_esito.style.display = 'none';
			campo_esito.insertAdjacentHTML('afterend', '<div>' + document.getElementById('esito_sigla').value + '</div>');
		} else if(document.getElementsByName('esito_note_esame')){
			campo_esito = document.getElementsByName('esito_note_esame')[0];
			campo_esito.value = document.getElementById('esito_sigla').value;
			campo_esito.cols = "170";
			campo_esito.removeAttribute('onkeyup');
			campo_esito.removeAttribute('onpaste');
			campo_esito.readOnly = true;
			campo_esito.style.resize = 'none';
			campo_esito.style.display = 'none';
			campo_esito.insertAdjacentHTML('afterend', '<div>' + document.getElementById('esito_sigla').value + '</div>');
		}
	}
}

if(document.getElementById("rigaCodPreacc")){
	if(document.getElementById("rigaCodPreacc").style.display != 'none'){
		var user_chiamate_sigla = document.getElementById('user_id_call_wssigla').value;
		ritornoDaSigla(document.getElementById("codpreacc").innerHTML, user_chiamate_sigla);
	}
}

</script>
