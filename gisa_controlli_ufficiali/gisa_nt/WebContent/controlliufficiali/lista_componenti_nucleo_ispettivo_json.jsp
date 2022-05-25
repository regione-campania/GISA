<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.lang.reflect.Type"%>
<%@page import="com.google.gson.JsonSerializationContext"%>
<%@page import="com.google.gson.JsonPrimitive"%>
<%@page import="com.google.gson.JsonObject"%>
<%@page import="com.google.gson.JsonElement"%>
<%@page import="com.google.gson.JsonSerializer"%>
<%@page import="com.google.gson.ExclusionStrategy"%>
<%@page import="com.google.gson.FieldAttributes"%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatStrumentoCalcoloNominativi"%>
<%@page import="java.util.ArrayList"%>

<jsp:useBean id="ListaToReturn" class="java.util.ArrayList" scope="request" />

<%@ include file="../initPage.jsp" %>

<%
Gson gson = new GsonBuilder().serializeNulls().create();


%>
<%=gson.toJson(ListaToReturn) %>


