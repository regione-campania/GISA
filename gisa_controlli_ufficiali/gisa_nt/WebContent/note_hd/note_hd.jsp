<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<% 
int riferimentoId = Integer.parseInt(request.getParameter("riferimentoId"));
String riferimentoIdNomeTab = request.getParameter("riferimentoIdNomeTab");
%>


<style>
table a:link {
	color: #666;
	font-weight: bold;
	text-decoration:none;
}
table a:visited {
	color: #999999;
	font-weight:bold;
	text-decoration:none;
}
table a:active,
table a:hover {
	color: #bd5a35;
	text-decoration:underline;
}
table {
	font-family:Arial, Helvetica, sans-serif;
	color:#666;
	font-size:12px;
	text-shadow: 1px 1px 0px #fff;
	margin:20px;
	border:#ccc 1px solid;
	-moz-border-radius:3px;
	-webkit-border-radius:3px;
	border-radius:3px;

	-moz-box-shadow: 0 1px 2px #d1d1d1;
	-webkit-box-shadow: 0 1px 2px #d1d1d1;
	box-shadow: 0 1px 2px #d1d1d1;
/* 	table-layout: fixed; */
    width: 80%;
}
table th {
	padding:21px 25px 22px 25px;
	border-top:1px solid #fafafa;
	border-bottom:1px solid #e0e0e0;
	text-align: center;
	background: #ededed;
	background: -webkit-gradient(linear, left top, left bottom, from(#ededed), to(#ebebeb));
	background: -moz-linear-gradient(top,  #ededed,  #ebebeb);
}
table th:first-child {
	text-align: center;
	padding-left:20px;
}
table tr:first-child th:first-child {
	-moz-border-radius-topleft:3px;
	-webkit-border-top-left-radius:3px;
	border-top-left-radius:3px;
}
table tr:first-child th:last-child {
	-moz-border-radius-topright:3px;
	-webkit-border-top-right-radius:3px;
	border-top-right-radius:3px;
}
table tr {
	text-align: center;
	padding-left:20px;
}
table td:first-child {
	text-align: center;
	padding-left:20px;
	border-left: 0;
}
table td {
	text-align: center;
	padding:18px;
	border-top: 1px solid #ffffff;
	border-bottom:1px solid #e0e0e0;
	border-left: 1px solid #e0e0e0;
	word-wrap: break-word;         /* All browsers since IE 5.5+ */
    overflow-wrap: break-word;     /* Renamed property in CSS3 draft spec */

}

table tr.presente td {
	background: #98FB98;
		color:#000;

}

table tr:last-child td {
	border-bottom:0;
}
table tr:last-child td:first-child {
	-moz-border-radius-bottomleft:3px;
	-webkit-border-bottom-left-radius:3px;
	border-bottom-left-radius:3px;
}
table tr:last-child td:last-child {
	-moz-border-radius-bottomright:3px;
	-webkit-border-bottom-right-radius:3px;
	border-bottom-right-radius:3px;
}
html * {
    font-family: "Trebuchet MS", Helvetica, sans-serif;
}
</style>

<%@page import="org.aspcfs.utils.GestoreConnessioni"%><%@ page language="java" import="java.sql.*" %>
<% response.setContentType("text/html");%>

<%! public static String fixStringa(String nome)
  {
	 if (nome == null || nome.equals("null"))
		  return "";
	  String toRet = nome; 
	  
	  toRet = toRet.replaceAll("\\?","");
	
	  return toRet;
	  
  }%>

 

<table> 
<col width="20%">
<col width="10%">
<tr>
<th>NOME TABELLA</th>
<th>ID TABELLA</th>
<th>NOTE HD</th>
</tr>

<%

Connection con =  null ;
try {

con = GestoreConnessioni.getConnection();

String sql = "SELECT * FROM  get_note_hd(?, ?);";
PreparedStatement pst = con.prepareStatement(sql);
pst.setInt(1, riferimentoId);
pst.setString(2, riferimentoIdNomeTab);

ResultSet rs= pst.executeQuery();
while (rs.next ()){
%>
<tr>
<td><%=fixStringa(rs.getString("nome_tabella")) %></td>
<td><%=rs.getInt("id_tabella") %></td>
<td><%=fixStringa(rs.getString("note_tabella")) %></td>
</tr>
<%
}}catch(Exception e){
out.println("Exception is ;"+e);
}
finally
{
	GestoreConnessioni.freeConnection(con) ;
}
%>


</table>

<br/>

