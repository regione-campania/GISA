<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ include file="initPage.jsp" %>
<html>
<head>
  <title>Centric CRM</title>
  <%if( request.getAttribute( "to_url" ) != null ){ %>
  <meta http-equiv="refresh" content="0;URL=<%= ((String)request.getAttribute("to_url")) %>">
  <%}else{
	  if( request.getAttribute( "to_url_suap" ) != null ){
		  
		  %>
		      <meta http-equiv="refresh" content="0;URL=<%=request.getAttribute( "to_url_suap" )%>">
		  
		  <%
	  }
	  else
	  {
	  %>
    <meta http-equiv="refresh" content="1;URL=<%= request.getScheme() %>://<%= getServerUrl(request) + "/" + request.getParameter("redirectTo") %>">
  
  <%} }%>
</head>
</html>