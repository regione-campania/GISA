<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <jsp:useBean id="idDocumento" class="java.lang.String" scope="request"/>
    <jsp:useBean id="idDocumentoSin" class="java.lang.String" scope="request"/>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Recupera ultimo pdf</title>
</head>
<body>
<% if (!idDocumento.equals("-1")){ %>
<a href="GestioneDocumenti.do?command=Download&idDocumento=<%=idDocumento%>"><input type="button" value="Recupera ultimo PDF generato (Modello)"></input></a>

<%} if (!idDocumentoSin.equals("-1")){ %>
<a href="GestioneDocumenti.do?command=Download&idDocumento=<%=idDocumentoSin%>"><input type="button" value="Recupera ultimo PDF generato (Schede SIN)"></input></a>
<%} if (idDocumento.equals("-1") && idDocumentoSin.equals("-1")){ %>
Non sono presenti documenti generati.
<%} %>
</body>
</html>