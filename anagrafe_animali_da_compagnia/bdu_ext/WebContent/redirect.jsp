<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ include file="initPage.jsp" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>
<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
  <title>Concourse Suite Community Edition</title>
<%--   <meta http-equiv="refresh" content="1;URL=<%= request.getAttribute("redirectUrl") %>"> --%>
</head>

<body onload="loadModalWindow();document.forms[0].submit();">
<form method="post" action="<%=request.getAttribute("redirectUrl")%>">
<input type="hidden" name="Message" value = "<%=request.getAttribute("Message") %>">
</form>
</body>
</html>
