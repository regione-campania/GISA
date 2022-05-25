<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.Iterator"%>
<%@page import="org.aspcfs.controller.ApplicationPrefs"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
ApplicationPrefs prefs = new ApplicationPrefs(request.getServletContext());
prefs.add("PORTSERVER", "25");
prefs.add("MAILPASSWORD", "");
prefs.add("MAILSERVER", "127.0.0.1");
prefs.add("EMAILADDRESS", "segnalazioni@gisasegn.it");


request.getServletContext().setAttribute("applicationPrefs", prefs);

Iterator it =prefs.getPrefs().keySet().iterator();
while (it.hasNext())
{
	String kiave =(String)it.next() ;
	out.println(kiave + ": "+ prefs.getPref(request.getServletContext(),kiave)+"<br>");
}
%>

<%=prefs.getPref(request.getServletContext(), "PORTSERVER") %>
<%=prefs.getPref(request.getServletContext(), "MAILSERVER") %>


</body>
</html>