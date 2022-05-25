<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.apache.commons.io.IOUtils"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.util.Enumeration"%>
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
Enumeration<String> enume = application.getAttributeNames();

int i = 1 ;
long sizeTot = 0 ;
while (enume.hasMoreElements())
{
	String next = enume.nextElement();
			
			
	out.println(i+")"+ next+"<br>");
	out.println(i+")"+ ((Object)application.getAttribute(next)).toString()+"<br>");
	out.println(i+")"+ ((Object)application.getAttribute(next)).toString()+"<br>");

	Class c = ((Object)application.getAttribute(next)).getClass();
	String className = c.getName();
	String classAsPath = className.replace('.', '/') + ".class";
	System.out.println(classAsPath);
	
	/*InputStream stream = c.getClassLoader().getResourceAsStream(classAsPath);
	byte[] arr = IOUtils.toByteArray(stream);
	out.println(i+")"+ arr.length+"<br>");
	sizeTot += arr.length ;*/
	i++;
}
out.println("--------------------SIZE TOT----------------------"+sizeTot);
%>

</body>
</html>