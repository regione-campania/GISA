<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%
System.out.println("sono stato chiamato");
JSONArray arr = new JSONArray();
JSONObject obj = new JSONObject();
 obj.put("codice", "002NA009");
 obj.put("indirizzo", "via pippo");
 obj.put("provincia", "CE");
arr.put(obj);

JSONObject obj2 = new JSONObject();

obj2.put("codice", "009NA009");
obj2.put("indirizzo", "via pippo 234");arr.put(obj2);
obj2.put("provincia", "CE");
JSONObject obj3 = new JSONObject();

obj3.put("codice", "022NA009");
obj3.put("indirizzo", "via prova pippo");
obj3.put("provincia", "CE");
arr.put(obj3);


JSONObject obj4 = new JSONObject();
obj4.put("codice", "122NA009");
obj4.put("indirizzo", "via pippo prova pippo");
obj4.put("provincia", "CE");

arr.put(obj4);

// obj.put("nome", "paperino");
JSONObject obj1 = new JSONObject();
obj1.putOpt("contacts", arr);

out.print(arr.toString());

%>