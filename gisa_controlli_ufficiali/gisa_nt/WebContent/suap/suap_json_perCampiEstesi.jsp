<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.HashMap, java.util.ArrayList" %>
<%@page import="org.aspcfs.modules.opu.base.LineeMobiliHtmlFields" %>

<%
 
	
System.out.println("sono suap_json_perCampiEstesi.jsp e sono stato chiamato, rispondo con formato json");
System.out.println("id linea ricevuto" + request.getAttribute("idLinea") != null ? request.getAttribute("idLinea") : "NESSUNO");
ArrayList<LineeMobiliHtmlFields> inputs = (ArrayList<LineeMobiliHtmlFields>) request.getAttribute("inputs");
HashMap<String,String> htmlInputs = new HashMap<String,String>();
System.out.println("stampoInput");
ArrayList<String> labelSequOrdinata = new ArrayList<String>();
for(LineeMobiliHtmlFields l : inputs)
{
	System.out.println(l.getHtml());
	htmlInputs.put(l.getLabel_campo(),l.getHtml());
	labelSequOrdinata.add(l.getLabel_campo());
}
Gson gson = new GsonBuilder().create();
htmlInputs.put("ordine",gson.toJson(labelSequOrdinata));

response.setContentType("application/json");


out.print(gson.toJson(htmlInputs));
out.flush();

%>