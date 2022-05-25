<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script type="text/javascript">
function openListaComuniNascita(){
	var res;
	var result;

if (true) {
	window.open('opu/listaComuniNascita.jsp','popupSelect',
	'height=595px,width=400px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	
	
	} else {
	
		res = window.showModalDialog('opu/listaComuniNascita.jsp','popupSelect',
		'dialogWidth:400px;dialogHeight:400px;center: 1; scroll: 0; help: 1; status: 0');
	
	}
}
	
</script>

<img src="javascript/reveal/Question_Mark.png" border="0"
			align="absmiddle" height="16" width="16" />
		<a href="#"
			onclick="openListaComuniNascita();"
			id="" target="_self">Lista comuni di nascita presenti in banca dati</a>
	