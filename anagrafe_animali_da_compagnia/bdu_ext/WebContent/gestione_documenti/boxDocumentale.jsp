<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
  	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript">

function openGestioneDocumenti(idAnimale, idSpecie, idTipo, idMicrochip, idLinea, idEvento){
	var res;
	var result;
		window.open('GestioneDocumenti.do?command=ListaDocumentiByTipo&tipo='+idTipo+'&IdSpecie='+idSpecie+'&IdAnimale='+idAnimale+'&id_microchip='+idMicrochip+'&idLinea='+idLinea+'&idEvento='+idEvento,'open_window',
		'height=295px,width=595px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}
</script> 
</head>
<body>

<a href="#" onclick="openGestioneDocumenti('<%=request.getParameter("idAnimale") %>','<%=request.getParameter("idSpecie") %>', '<%=request.getParameter("idTipo")  %>', '<%=request.getParameter("idMicrochip")  %>','<%=request.getParameter("idLinea")  %>', '<%=request.getParameter("idEvento")  %>');"
					id="" target="_self"><input type="submit" name="Timbra PDF" class="buttonClass" value="Genera PDF" />
	</a>
	
</body>
</html>