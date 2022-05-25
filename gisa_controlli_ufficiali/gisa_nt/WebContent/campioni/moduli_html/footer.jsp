<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<br/>
<TABLE rules="all" cellpadding="10" style="border-collapse: collapse">
<tr>
		<td style="text-align:center; width:200px;border: 1px solid black;">
		<b>Codice<br>Quesito<br>Diagnostico</b> 
	</td>
		<td style="text-align:center; width:200px; border: 1px solid black;">
		<% if(OrgCampione.getBarcodeMotivazione() != null && !OrgCampione.getBarcodeMotivazione().equals("") ){ %>
			<img src="<%=createBarcodeImage(OrgCampione.getBarcodeMotivazione())%>" />
			<% } else { %>
				NON DISPONIBILE
			<% } %>
		
	</td>
</tr>
<tr>
		<td style="text-align:center; width:200px; border: 1px solid black;">
		<b>Codice<br>Stabilimento</b>
	</td>
		<td style="text-align:center; width:200px; border: 1px solid black;">
	<% //if(OrgCampione.getMotivazione() != null && !OrgCampione.getMotivazione().equals("") && OrgCampione.getBarcodeMotivazione() != null &&
			if(OrgCampione.getBarcodeOSA() != null){ %>
			<img class="codeOsa" ="middle" src="<%=createBarcodeImage(OrgCampione.getBarcodeOSA())%>" />
			<% } else { %>
				NON DISPONIBILE
			<% } %>
		
	</td>
</tr>
<tr>
		<td style="text-align:center; width:200px; border: 1px solid black;">
		<b>Codice<br>Matrice</b> 
	</td>
		<td style="text-align:center; width:200px; border: 1px solid black;">
	<%	
		if(OrgCampione.getCodiciMatrice() != null && !OrgCampione.getCodiciMatrice().equals("")){
			int k = 0;
			StringTokenizer st = new StringTokenizer(OrgCampione.getCodiciMatrice(),";");
			while(st.hasMoreTokens()){
				++k;  %>
				<img align="middle" src="<%=createBarcodeImage(st.nextToken())%>" /><br><br>
				  
			<% 
			} 
		}  else { %>
				NON DISPONIBILE
			<% } %>
		</td>   	
</tr>

<tr style="display:none">
		<td style="text-align:center; width:200px; border: 1px solid black;">
		<b>Codice<br>Preaccettazione</b> 
	</td>
			<td style="text-align:center; width:200px; border: 1px solid black;">
		<% 	if(OrgCampione.getCodPreaccettazione() != null && !OrgCampione.getCodPreaccettazione().equals("")){ %>
			<img align ="middle" src="<%=createBarcodeImage(OrgCampione.getCodPreaccettazione().toUpperCase() )%>" /> 
			<% } else { %>
				NON DISPONIBILE
			<% } %>
		</td>   	
</tr>
</TABLE>

