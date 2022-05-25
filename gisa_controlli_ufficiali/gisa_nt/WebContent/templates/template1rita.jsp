<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import=" org.aspcfs.modules.util.imports.ApplicationProperties"%>
<%@page import="java.net.InetAddress"%>
<html>
<head>

<link rel="stylesheet" type="text/css" media="screen" documentale_url="" href="controlliufficiali/mod5_screen.css" />
<link rel="stylesheet" type="text/css" media="print" documentale_url="" href="controlliufficiali/mod5_print.css" /> 
<link rel="stylesheet" type="text/css" href="css/capitalize.css"></link>		

<title></title>
</head>
<body leftmargin="0" rightmargin="0" margin="0" marginwidth="0" topmargin="0" marginheight="0">

<table border="0" width="100%">
  <tr>
    <td valign="top">
<jsp:include page='<%= (String) request.getAttribute("IncludeModule") %>' flush="true"/>
    </td>
  </tr>
</table>
</body>
</html>

