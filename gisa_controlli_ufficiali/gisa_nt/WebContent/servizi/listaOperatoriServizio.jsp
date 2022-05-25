<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<link rel="stylesheet" documentale_url="" href="screen.css" type="text/css" media="screen" />

<script>
function recuperaScheda(){
	var name = document.getElementById("name").value;
	var targa = document.getElementById("targa").value;
	var iva = document.getElementById("partita_iva").value;
	var comune = document.getElementById("city").value;
	var outputType = document.getElementById("output_type").value;
	location.href = "<%=request.getContextPath() %>/ServletServiziOperatore?name="+name+"&targa="+targa+"&iva="+iva+"&output_type="+outputType+"&city="+comune;
	
	
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
			<td>Ragione sociale</td>
			<td><input type="text" id="name" name="name" value=""/></td>
		</tr><!-- Table Row -->
	
		<tr class="even">
			<td>Targa</td>
		<td><input type="text" id="targa" name="targa" value=""/></td>		
		</tr>

		<tr>
			<td>Comune</td>
			<td><input type="text" id="city" name="city"/></td>
		</tr>
		
		<tr>
			<td>Partita IVA</td>
			<td><input type="text" id="partita_iva" name="partita_iva"/></td>
	</tr>

		<tr class="even">
			<td>Output type</td>
			<td><select style="width:140px" id="output_type" name="output_type"><option value="html">HTML</option><option value="xml">XML</option><option value="json">JSON</option></select></td>
		</tr>

		
		
		<tr class="even">
			<td colspan="2"> <div align="center"><a href="#" class="button black" onClick="recuperaScheda()">INVIA</a></div></td>
		</tr>
		
	</tbody>
	<!-- Table Body -->

</table>