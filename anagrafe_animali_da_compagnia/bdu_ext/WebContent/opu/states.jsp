<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.utils.GestoreConnessioni"%><%@ page language="java" import="java.sql.*" %>
<% response.setContentType("text/html");%>
<%

String str=request.getParameter("queryString");
Connection con =  null ;
try {

con = GestoreConnessioni.getConnection();

String sql = "SELECT nome FROM comuni1 WHERE  trashed is false and nome ilike '"+str+"%' LIMIT 10";
Statement stm = con.createStatement();
stm.executeQuery(sql);
ResultSet rs= stm.getResultSet();
while (rs.next ()){
out.println("<li onclick='fill("+rs.getString("nome")+");'>"+rs.getString("nome")+"</li>");
}}catch(Exception e){
out.println("Exception is ;"+e);
}
finally
{
	GestoreConnessioni.freeConnection(con) ;
}
%>
