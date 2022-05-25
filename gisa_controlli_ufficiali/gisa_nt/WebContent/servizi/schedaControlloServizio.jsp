<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<link rel="stylesheet" documentale_url="" href="screen.css" type="text/css" media="screen" />

<script>
function recuperaScheda(){
	var objectId = document.getElementById("object_id").value;
	var tipoDettaglio = document.getElementById("tipo_dettaglio").value;
	var outputType = document.getElementById("output_type").value;
	var visualizzazione = document.getElementById("visualizzazione").value;
	var css = document.getElementById("object_css").value;
	location.href = "<%=request.getContextPath() %>/ServletServiziDettaglioCU?object_id="+objectId+"&tipo_dettaglio="+tipoDettaglio+"&output_type="+outputType+"&visualizzazione="+visualizzazione+"&object_css="+ encodeURIComponent(css);
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
			<td><input type="text" id="object_id" name="object_id" value="579727"/></td>
		</tr><!-- Table Row -->
		<tr>
			<td>Tipo dettaglio</td>
			<td><select style="width:140px" id="tipo_dettaglio" name="tipo_dettaglio"><option selected value="4">Ispezione semplice</option><option value="3">Audit</option><option value="5">Sorveglianza</option></select></td>
		</tr>
		
		<tr style="display:none">
			<td>Visualizzazione</td>
		<td><select style="width:140px" id="visualizzazione" name="visualizzazione"><option value="tutto">Tutto</option><option selected value="screen">Screen</option><option value="print">Print</option></select></td>
	</tr>

		<tr class="even">
			<td>Output type</td>
			<td><select style="width:140px" id="output_type" name="output_type"><option value="html">HTML</option><option value="xml">XML</option><option value="json">JSON</option></select></td>
		</tr>

	<tr style="display:none">
			<td>CSS</td>
			<td><textarea id="object_css" name="object_css"></textarea></td>
		</tr>
		
		
		<tr class="even">
			<td colspan="2"> <div align="center"><a href="#" class="button black" onClick="recuperaScheda()">INVIA</a></div></td>
		</tr>
		
	</tbody>
	<!-- Table Body -->

</table>