<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.google.gson.Gson"%>

<%
String ImportKoError = (String)request.getAttribute("ImportKoError");
Integer numeroRiga = (Integer)request.getAttribute("numeroRiga");

Gson gson = new GsonBuilder().create();
out.print(gson.toJson("___Errore_Tracciato_Record___Attenzione! Si e' verificato il seguente errore" + ((numeroRiga!=null)?(" alla riga numero " + numeroRiga):("")) +  ": " + ImportKoError ));
out.flush();
%>