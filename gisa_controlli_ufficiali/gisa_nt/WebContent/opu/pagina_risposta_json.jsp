<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.HashMap" %>
<%
 
HashMap<String,String> risp = new HashMap<String,String>();

Gson gson = new GsonBuilder().create();
String risultato = (String)request.getAttribute("risultato");
risp.put("risultato",risultato);
if(request.getAttribute("msg_ko") != null)
{
	risp.put("msg_ko",(String)request.getAttribute("msg_ko"));
}

response.setContentType("application/json");

out.print(gson.toJson(risp));
out.flush();

%>