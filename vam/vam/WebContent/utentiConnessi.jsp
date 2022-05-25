<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.HashMap"%> 

<%@page import="java.util.ArrayList"%>


<% 
	HashMap<String, HttpSession> utenti = null;
	utenti = (HashMap<String, HttpSession>)request.getSession().getServletContext().getAttribute("utenti");
%>

<%= utenti.keySet().size() %>

