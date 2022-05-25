<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<h2>stato operazione</h2>
<tr>
	<td>  
		<label id="inserimento_linea">operazione in corso</label>    
		<input type="hidden" id="alt_id" value="${alt_id}"> 
		<input type="hidden" id="stab_id" value="${stab_id}"> 
		<input type="hidden" id="numeroPratica" value="${numeroPratica}"> 
		<input type="hidden" id="desc_operatore" value="${desc_operatore}">
		<input type="hidden" id="idComunePratica" value="${idComunePratica}">
	</td>
</tr>

<script>
	
	var stabId = document.getElementById("stab_id").value; 
	var altId = document.getElementById("alt_id").value; 
	var numeroPratica = document.getElementById("numeroPratica").value; 
	var comunePratica = document.getElementById("idComunePratica").value;
	var desc_operatore = document.getElementById("desc_operatore").value;
	
	loadModalWindow();
	window.location.href='GestioneAllegatiGins.do?command=ListaAllegati&numeroPratica=' + numeroPratica + 
			'&desc_operatore=' + desc_operatore +
			'&alt_id=' + altId + 
			'&stab_id=' + stabId + 
			'&idComunePratica=' + comunePratica;

</script>