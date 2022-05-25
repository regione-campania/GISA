<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <jsp:useBean id="mail_av" class="java.lang.String" scope="request"/>
    <jsp:useBean id="mail_bn" class="java.lang.String" scope="request"/>
    <jsp:useBean id="mail_ce" class="java.lang.String" scope="request"/>
    <jsp:useBean id="mail_na1" class="java.lang.String" scope="request"/>
    <jsp:useBean id="mail_na2" class="java.lang.String" scope="request"/>
    <jsp:useBean id="mail_na3" class="java.lang.String" scope="request"/>
    <jsp:useBean id="mail_sa" class="java.lang.String" scope="request"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Recapiti mail ASL</title>
</head>
<body>

<ul>
<li>ASL Avellino: <%=mail_av %></li> 
<li>ASL Benevento: <%=mail_bn %></li> 
<li>ASL Caserta: <%=mail_ce %></li> 
<li>ASL Napoli 1 Nord: <%=mail_na1 %></li> 
<li>ASL Napoli 2 Centro: <%=mail_na2 %></li> 
<li>ASL Napoli 3 Sud: <%=mail_na3 %></li> 
<li>ASL Salerno: <%=mail_sa %></li> 
</ul>

</body>
</html>