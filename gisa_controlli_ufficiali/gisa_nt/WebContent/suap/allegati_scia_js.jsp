<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
 <%
 					int maxFileSize=-1;
 					int mb1size = 1048576;
  					maxFileSize=Integer.parseInt(org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI"));
 					String maxSizeString = String.format("%.2f", (double) maxFileSize/ (double) mb1size);
 					%>
 					
 					
  <script>
function checkFile(file){
var fileCaricato = file;
var errorString = '';

if (!GetFileSize(fileCaricato))
	errorString+='\nErrore! Selezionare un file con dimensione superiore a 0 ed inferiore a <%=maxSizeString%> MB';
if (errorString!= ''){
	alert(errorString)
	file.value = null;
}
}

function GetFileSize(fileCaricato) {
var input = fileCaricato;
  file = input.files[0];
  if (file.size == 0 || file.size> <%=Integer.parseInt(org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI"))%>)
	 	return false;
  return true;
	}
</script>					