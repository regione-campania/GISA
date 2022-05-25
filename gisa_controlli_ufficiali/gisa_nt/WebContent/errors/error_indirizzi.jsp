<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script>
function goBack() {
    window.history.go(-2);
}
</script>
<%String erroreIndirizzi = "";
String[] lines = errorMessage.split(System.getProperty("line.separator"));
erroreIndirizzi = lines[0];
erroreIndirizzi = erroreIndirizzi.substring(erroreIndirizzi.indexOf("[IndirizziException]"), erroreIndirizzi.length());
erroreIndirizzi = erroreIndirizzi.replace("[IndirizziException]", "ATTENZIONE : ");
%>
<table width="100%">
	<tr><td><font color="red" size="3px"><b><%=erroreIndirizzi %></b></font></td></tr>
	<tr><td></td></tr>
	<tr><td>Possibili Cause:</td></tr>
	<tr><td>
		<li>Comune Insesistente o non selezionato</li>
		<li>Provincia Inesistente</li>
		<li>Comune non associabile alla Provincia selezionata</li>
	</td></tr>
	<tr></tr>
	<tr><td><font size="3px">Si prega di tornare indietro e riprovare.</font></td></tr>
	<tr><td><input type="button"  onclick="goBack()" value="Indietro"/></td></tr>
</table>