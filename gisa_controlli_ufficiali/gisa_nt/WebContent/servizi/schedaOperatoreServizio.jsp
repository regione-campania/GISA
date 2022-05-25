<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<link rel="stylesheet" documentale_url="" href="screen.css" type="text/css" media="screen" />

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../../initPage.jsp" %>

 

<script>
function aggiornaTipoDettaglio(val){
document.getElementById("tipo_dettaglio").value=val;	
}

function recuperaScheda(){
	var objectId = document.getElementById("object_id").value;
	var objectIdName = document.getElementById("object_id_name").value;
	var tipoDettaglio = document.getElementById("tipo_dettaglio").value;
	var outputType = document.getElementById("output_type").value;
	var visualizzazione = document.getElementById("visualizzazione").value;
	var css = document.getElementById("object_css").value;
	location.href = "<%=request.getContextPath() %>/ServletServiziScheda?object_id="+objectId+"&tipo_dettaglio="+tipoDettaglio+"&object_id_name="+objectIdName+"&output_type="+outputType+"&visualizzazione="+visualizzazione+"&object_css="+ encodeURIComponent(css);
	
	
}
</script>

<table cellspacing='0'> <!-- cellspacing='0' is important, must stay -->

	<!-- Table Header -->
	<thead>
		<tr>
			<th>Nome campo</th>
			<th>Valore campo</th>
		</tr>
	</thead>
	<!-- Table Header -->

	<!-- Table Body -->
	<tbody>

		<tr>
			<td>Object id</td>
			<td><input type="text" id="object_id" name="object_id" value="50045"/></td>
		</tr><!-- Table Row -->
	
		<tr class="even">
			<td>Object id name</td>
			<td><select style="width:140px" id="object_id_name" name="object_id_name"><option value="org_id">orgId</option> <option value="stab_id">stabId</option> <option value="alt_id">altId</option> </select></td>
		
		</tr>

		<tr>
			<td>Tipo dettaglio</td>
			<td>
<select style="width:300px" id="tipo_dettaglio_select" name="tipo_dettaglio_select" onChange="aggiornaTipoDettaglio(this.value)">			
<option value="1">852</option>
<option value="12">852 Mobili</option>
<option value="2">853</option>
<option value="15">Anagrafica Stabilimenti</option>
<option value="23">Anagrafica Stabilimenti - Attestato</option>
<option value="26">Anagrafica Stabilimenti - Fissi - Attestato</option>
<option value="24">Anagrafica Stabilimenti - Fissi - Scheda</option>
<option value="27">Anagrafica Stabilimenti - Mobili - Attestato</option>
<option value="25">Anagrafica Stabilimenti - Mobili - Scheda</option>
<option value="22">Anagrafica Stabilimenti - Richieste</option>
<option value="28">Anagrafica Stabilimenti - Richieste nuove</option>
<option value="20">Apiari</option>
<option value="21">Apiari Riepilogo</option>
<option value="11">Aziende Agricole</option>
<option value="6">Aziende Zootecniche</option>
<option value="8">Canili</option>
<option value="9">Colonie</option>
<option value="13">DIA</option>
<option value="14">DIA Mobili</option>
<option value="3">Imbarcazioni</option>
<option value="7">Molluschi Bivalvi</option>
<option value="10">Operatori commerciali</option>
<option value="5">OSM Registrati</option>
<option value="17">OSM Riconosciuti</option>
<option value="19">Parafarmacie</option>
<option value="4">SOA</option>
<option value="16">TEST</option>
<option value="18">Trasporto Animali</option>
</select>
<br/>		<input type="text" id="tipo_dettaglio" name="tipo_dettaglio" value="1"/></td>
		</tr>
		
		<tr>
			<td>Visualizzazione</td>
		<td><select style="width:140px" id="visualizzazione" name="visualizzazione"><option value="tutto">Tutto</option><option selected value="screen">Screen</option><option value="print">Print</option></select></td>
	</tr>

		<tr class="even">
			<td>Output type</td>
			<td><select style="width:140px" id="output_type" name="output_type"><option value="html">HTML</option><option value="xml">XML</option><option value="json">JSON</option></select></td>
		</tr>

		<tr>
			<td>CSS</td>
			<td><textarea id="object_css" name="object_css"></textarea></td>
		</tr>
		
		
		<tr class="even">
			<td colspan="2"> <div align="center"><a href="#" class="button black" onClick="recuperaScheda()">INVIA</a></div></td>
		</tr>
		
	</tbody>
	<!-- Table Body -->
 
</table>
