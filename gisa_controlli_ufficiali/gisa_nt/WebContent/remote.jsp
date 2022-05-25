<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.HashMap"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
JSONArray array = new JSONArray(); 
HashMap map = new HashMap();
map.put("nome", "camillo");
map.put("cognome", "benson");
HashMap map2 = new HashMap();
map2.put("comune", "torre annunziata");
map2.put("indirizzo", "via marconi");
JSONObject indirizzo = new JSONObject(map2);



map.put("indirizzo", indirizzo);
JSONObject o = new JSONObject(map);

%>
<%=o.toString().replace(",}", "}") %>

