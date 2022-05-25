<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<table class="tabella" style="width: 99%">
	<tr align="right">
		<td width="33%">Nome <font color="red">*</font><input value="${accettazione.richiedenteNome }" type="text" id="richiedenteNome" name="richiedenteNome" disabled="disabled" maxlength="64"> </td>
		<td width="33%">Cognome <font color="red">*</font><input value="${accettazione.richiedenteCognome }" type="text" id="richiedenteCognome" name="richiedenteCognome" disabled="disabled" maxlength="64"> </td>
		<td width="33%">Telefono <input value="${accettazione.richiedenteTelefono }" type="text" name="richiedenteTelefono" disabled="disabled" maxlength="16"> </td>
		<td width="33%">&nbsp;</td>
	</tr>
	<tr align="right">
		<td width="33%">Codice Fiscale <input value="${accettazione.richiedenteCodiceFiscale }" type="text" name="richiedenteCodiceFiscale" maxlength="16" disabled="disabled" maxlength="64"> </td>
		<td width="33%">Documento <input value="${accettazione.richiedenteDocumento }" type="text" name="richiedenteDocumento" disabled="disabled" maxlength="64"> </td>
		<td width="33%">Residenza <input value="${accettazione.richiedenteResidenza }" type="text" name="richiedenteResidenza" disabled="disabled" maxlength="64"> </td>
		<td width="33%">&nbsp;</td>
	</tr>
</table>

<script>
	function controllaInfo(){
		var ck = document.getElementById("richiedenteProprietario"); //ckbox coincide con il proprietario
		var e = document.getElementById("tipoRichiedente"); 		 //select tipi di proprietario
		var strUser = e.options[e.selectedIndex].value; 
		if(strUser==1){		//privato
			if (ck!=null){
				if(ck.checked==false){
					if (document.getElementById("richiedenteNome").value=="") return 1;
					if (document.getElementById("richiedenteCognome").value=="") return 1;
				}
				else return 0;
			}
			else {
				if (document.getElementById("richiedenteNome").value=="") return 1;
				if (document.getElementById("richiedenteCognome").value=="") return 1;
			}
		}
	}
</script>
