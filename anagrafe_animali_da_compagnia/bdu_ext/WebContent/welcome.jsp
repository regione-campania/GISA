<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%
  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
  response.setHeader("Expires", "-1");
%>
<html>
<head>
<%@ include file="initPage.jsp" %>
<META HTTP-EQUIV="refresh" content="2;URL=https://<%= getServerUrl(request) %>">
<title><dhv:label name="templates.CentricCRM">Concourse Suite Community Edition</dhv:label></title>
</head>
<body>
<center><dhv:label name="calendar.redirectingToSecureLogin.text">Redirecting to secure login...</dhv:label></center>
</body>
</html>