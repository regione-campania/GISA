<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fn" uri="http://java.sun.com/jsp/jstl/functions"  %>

<table class="trails" cellspacing="0">
<tr>
	<td>
		<a href="Parafarmacie.do?command=SearchFormFcie">
		Farmacie / Grossisti / Parafarmacie
		</a> >
		<a href="Parafarmacie.do?command=Details&idFarmacia=${altId}&opId=${altId}&orgId=${altId}">
			Scheda Operatore Parafarmacia
		</a> 
		> Importa in Anagrafica stabilimenti	
	</td>
</tr>
</table>

<center>
<h2>SELEZIONEARE LA LINEA DI ATTIVITA' CON CUI IMPORTARE LO STABILIMENTO</h2>
	<select id="linee" name="linee" style="max-width: 100%;" onchange="mostra_prosegui()">
		<option value=""></option>
		<c:forEach items="${listLinee}" var="linee">			
			<option value="${linee.id}">${linee.path_descrizione}</option>
		</c:forEach>
	</select> 
	<input type="hidden" id="altId" value="${altId}" />
	<br><br><br><br><br><br><br><br><br>
	<button type="button" class=""
		id="button_prosegui" 
		style="display: none;" 
		onClick="vai_a_import()">PROSEGUI</button>
		
	<button type="button" class="yellowBigButton" 
		style="width: 250px;" 
		onClick="window.location.href='Parafarmacie.do?command=Details&idFarmacia=${altId}&opId=${altId}&orgId=${altId}'">ANNULLA</button>
	<br><br><br><br><br><br>
</center>



<script>


function vai_a_import() {
	
	loadModalWindow();			
	var id_linea = document.getElementById('linee').value;
	var alt_id = document.getElementById('altId').value;
	var link = 'GestioneAnagraficaAction.do?command=Import&altId='+alt_id+'&id_linea='+id_linea;
	window.location.href=link;

}

	   
function mostra_prosegui(){
		if(document.getElementById('linee').value != ''){
			document.getElementById('button_prosegui').setAttribute("class", "yellowBigButton");
			document.getElementById('button_prosegui').style='width: 250px; margin-right: 10%';
		} else {
			document.getElementById('button_prosegui').setAttribute("class", "");
			document.getElementById('button_prosegui').style='display: none';
		}
	}
</script>


