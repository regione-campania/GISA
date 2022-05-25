<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.aspcfs.modules.opu.actions.BeanMappingLinea" %>
<%@page import="org.aspcfs.modules.opu.actions.BeanMappingGen" %>
<%
Gson gson = new GsonBuilder().create();
response.setContentType("application/json");

String op = (String)request.getAttribute("op");

if(op.equals("get_tipologie"))
{
	HashMap<Integer,String> tipologie = (HashMap<Integer,String>)request.getAttribute("tipologie");	
	out.print(gson.toJson(tipologie));
}
else if(op.equals("get_linee"))
{
	ArrayList<BeanMappingLinea> linee = (ArrayList<BeanMappingLinea>)request.getAttribute("linee");
	String t = gson.toJson(linee);
	System.out.println(t);
	out.print(gson.toJson(linee));
}
else if(op.equals("get_stabilimenti"))
{
	ArrayList<BeanMappingGen> orgs = (ArrayList<BeanMappingGen>) request.getAttribute("orgs");
	System.out.println(gson.toJson(orgs));
	out.print(gson.toJson(orgs));
}
else 
{
	out.print(gson.toJson(""));
}


out.flush();

%>