<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<br/>
<TABLE rules="all" cellpadding="10" style="border-collapse: collapse">
<tr>
	<td style="text-align:center; width:200px; height:100px;border: 1px solid black;">
		<b>Codice<br>Quesito<br>Diagnostico</b> 
	</td>
	<td style="text-align:center; width:500px; height:100px;border: 1px solid black;">
		<% if(OrgTampone.getMotivazione() != null && !OrgTampone.getMotivazione().equals("") && 
				 OrgTampone.getBarcodeMotivazione() != null){ %>
			<img src="<%=createBarcodeImage(OrgTampone.getBarcodeMotivazione())%>" />
			<% } else { %>
				NON DISPONIBILE
			<% } %>
		
	</td>
</tr>
<tr>
	<td style="text-align:center; width:200px;height:100px;border: 1px solid black;">
		<b>Codice<br>Stabilimento</b>
	</td>
	<td style="text-align:center; width:500px;height:100px;border: 1px solid black;">
		<% if( OrgTampone.getBarcodeOSA() != null){ %>
		<img class="codeOsa" ="middle" src="<%=createBarcodeImage(OrgTampone.getBarcodeOSA())%>" />
		<% } else { %>
			NON DISPONIBILE
		<% } %>	
	</td>
</tr>
<tr>
	<td style="text-align:center; width:200px; heigth:100px;border: 1px solid black;">
		<b>Codice<br>Matrice</b> 
	</td>
	<td style="text-align:center;width:500px; height:100px;border: 1px solid black; ">
	<%	
		if(OrgTampone.getCodiciMatrice() != null && !OrgTampone.getCodiciMatrice().equals("")){
			int k = 0;
			StringTokenizer st = new StringTokenizer(OrgTampone.getCodiciMatrice(),";");
			while(st.hasMoreTokens()){
				++k;  %>
				<img align="middle" src="<%=createBarcodeImage(st.nextToken())%>"/><br><br>
				  
			<% 
			} 
		}  else { %>
			NON DISPONIBILE
		<% } %>	
		</td>   	
</tr>
</TABLE>