<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<% 
int riferimentoId = Integer.parseInt(request.getParameter("riferimentoId"));
String riferimentoIdNomeTab = request.getParameter("riferimentoIdNomeTab");
%>

<script>
function openPopupSchedeSupplementari(url){
	var res;
	var result;
		window.open(url,'popupSelectSchedeSupplementari',
		'height=400px,width=1300px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=no ,modal=yes');
		
}
</script>

<input style="width:250px" value="Gestione Schede Supplementari" type="button" onClick="openPopupSchedeSupplementari('GestioneSchedeSupplementari.do?command=Lista&riferimentoId=<%=riferimentoId%>&riferimentoIdNomeTab=<%=riferimentoIdNomeTab%>');return false;"/>
