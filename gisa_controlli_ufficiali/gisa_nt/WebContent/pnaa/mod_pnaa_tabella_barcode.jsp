<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<table cellpadding="10" class="barcodes">

<tr>
<td><b>Codice<br>Quesito<br>Diagnostico</b></td>
<td><% if(Mod.getCampioneCodiceEsame()!=null && !Mod.getCampioneCodiceEsame().equals("") ){ %> <img align ="middle" src="<%=createBarcodeImage(Mod.getCampioneCodiceEsame().toUpperCase() )%>" /> <% } else { %> NON DISPONIBILE <% } %></td>
</tr>

<tr>
<td><b>Codice<br>Stabilimento</b></td>
<td><% if(Mod.getCampioneCodiceOsa()!=null && !Mod.getCampioneCodiceOsa().equals("") ){ %> <img align ="middle" src="<%=createBarcodeImage(Mod.getCampioneCodiceOsa().toUpperCase() )%>" /> <% } else { %> NON DISPONIBILE <% } %></td>
</tr>

<tr>
<td><b>Codice<br>Matrice</b></td>
<td><% if(Mod.getCampioneListaCodiceMatrice()!=null && !Mod.getCampioneListaCodiceMatrice().equals("") ){ 
String[] codiciMatrici = Mod.getCampioneListaCodiceMatrice().split(",");
for (int i = 0; i<codiciMatrici.length; i++){%><img align ="middle" src="<%=createBarcodeImage(codiciMatrici[i].toUpperCase() )%>" /><br/><% } %>
<% } else { %> NON DISPONIBILE <% } %></td>
</tr>

<tr style="<%= (Mod.getCampioneCodicePreaccettazione()!=null && !Mod.getCampioneCodicePreaccettazione().equals("") ) ? "" : "display:none" %>">
<td><b>Codice<br>Preaccettazione</b></td>
<td><% if(Mod.getCampioneCodicePreaccettazione()!=null && !Mod.getCampioneCodicePreaccettazione().equals("") ){ %> <img align ="middle" src="<%=createBarcodeImage(Mod.getCampioneCodicePreaccettazione().toUpperCase() )%>" /> <% } else { %> NON DISPONIBILE <% } %></td>
</tr>

</table>