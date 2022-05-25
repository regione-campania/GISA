<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.darkhorseventures.database.ConnectionPool"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%

response.setIntHeader("Refresh", 5);
ConnectionPool sqlDriver = (ConnectionPool) application.getAttribute("ConnectionPool");

 
int ava = sqlDriver.AVAILABLE_CONNECTION ;
int max = sqlDriver.getMaxConnections();
int busy = sqlDriver.BUSY_CONNECTION;

%>

<table>

<tr>
<th>Connection Pool Monitoring [GISA]</th><th>MaxDeadTime</th><th>MaxIdleTime</th><th>Busy Conn</th><th>Avail Conn</th><th>Max Conn</th>
</tr>
<tr>
<td><%=""%></td>
<td><%=sqlDriver.getMaxDeadTime() %></td>
<td><%=sqlDriver.getMaxIdleTime() %></td>
<td><%=busy %></td>
<td><%=sqlDriver.AVAILABLE_CONNECTION %></td>

<td><%=max %></td>



</tr>
</table>


</body>
</html>