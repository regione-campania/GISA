<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script>
function goBack() {
	
    	window.history.go(-1);

}
</script>

<table width="100%">
	<tr><td><font color="red" size="3px"><b>AZIONE NON CONSENTITA</b></font></td></tr>
	<tr><td></td></tr>
	<tr><td>Possibili Cause:</td></tr>
	<tr><td>
		<li>- E' statoeffettuato un doppio click su un bottone</li>
		<li>- E' in corso una elaborazione </li>
		
	</td></tr>
	<tr></tr>
	<tr><td><font size="3px">INDIETRO</font></td></tr>
	<tr><td><input type="button"  onclick="goBack()" value="Indietro"/></td></tr>
</table>