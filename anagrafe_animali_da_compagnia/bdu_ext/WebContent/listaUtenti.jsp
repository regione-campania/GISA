<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.Date"%>
<%@page import="org.aspcfs.modules.mycfs.base.Mail"%>
<%@page import="com.darkhorseventures.database.ConnectionPool"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Comparator"%>
<%@page import="org.aspcfs.modules.admin.base.User"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.aspcfs.modules.admin.base.UserList"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.aspcfs.controller.SystemStatus"%>
<%@page import="org.aspcfs.controller.ApplicationPrefs"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>




<style type="text/css">

/*
This was made by João Sardinha
Visit me at http://johnsardine.com/

The table style doesnt contain images, it contains gradients for browsers who support them as well as round corners and drop shadows.

It has been tested in all major browsers.

This table style is based on Simple Little Table By Orman Clark,
you can download a PSD version of this at his website, PremiumPixels.
http://www.premiumpixels.com/simple-little-table-psd/

PLEASE CONTINUE READING AS THIS CONTAINS IMPORTANT NOTES

*/

/*
Im reseting this style for optimal view using Eric Meyer's CSS Reset - http://meyerweb.com/eric/tools/css/reset/
------------------------------------------------------------------ */

table {border-spacing: 0; } /* IMPORTANT, I REMOVED border-collapse: collapse; FROM THIS LINE IN ORDER TO MAKE THE OUTER BORDER RADIUS WORK */

/*------------------------------------------------------------------ */



/*
Table Style - This is what you want
------------------------------------------------------------------ */


table {
	font-family:Arial, Helvetica, sans-serif;
	color:#666;
	font-size:12px;
	text-shadow: 1px 1px 0px #fff;
	background:#eaebec;
	width:100%;
	border:#ccc 1px solid;
	margin-top:20 px;
	-moz-border-radius:3px;
	-webkit-border-radius:3px;
	border-radius:3px;

	-moz-box-shadow: 0 1px 2px #d1d1d1;
	-webkit-box-shadow: 0 1px 2px #d1d1d1;
	box-shadow: 0 1px 2px #d1d1d1;
}
table th {
	padding:21px 25px 22px 25px;
	border-top:1px solid #fafafa;
	border-bottom:1px solid #e0e0e0;
	text-align: center;
	font-size: 16px;
	background: #ededed;
	background: -webkit-gradient(linear, left top, left bottom, from(#ededed), to(#ebebeb));
	background: -moz-linear-gradient(top,  #ededed,  #ebebeb);
}
table th:first-child{
	text-align: center;
	padding-left:20px;
}
table tr:first-child th:first-child{
	-moz-border-radius-topleft:3px;
	-webkit-border-top-left-radius:3px;
	border-top-left-radius:3px;
}
table tr:first-child th:last-child{
	-moz-border-radius-topright:3px;
	-webkit-border-top-right-radius:3px;
	border-top-right-radius:3px;
}
table tr{
	text-align: center;
	padding-left:20px;
}
table tr td:first-child{
	text-align: left;
	padding-left:20px;
	border-left: 0;
}
table tr td {
	padding:18px;
	border-top: 1px solid #ffffff;
	border-bottom:1px solid #e0e0e0;
	border-left: 1px solid #e0e0e0;
	font-size: 14px;
	text-align:center;
	background: #fafafa;
	background: -webkit-gradient(linear, left top, left bottom, from(#fbfbfb), to(#fafafa));
	background: -moz-linear-gradient(top,  #fbfbfb,  #fafafa);
}
table tr.even td{
	background: #f6f6f6;
	background: -webkit-gradient(linear, left top, left bottom, from(#f8f8f8), to(#f6f6f6));
	background: -moz-linear-gradient(top,  #f8f8f8,  #f6f6f6);
}
table tr:last-child td{
	border-bottom:0;
}
table tr:last-child td:first-child{
	-moz-border-radius-bottomleft:3px;
	-webkit-border-bottom-left-radius:3px;
	border-bottom-left-radius:3px;
}
table tr:last-child td:last-child{
	-moz-border-radius-bottomright:3px;
	-webkit-border-bottom-right-radius:3px;
	border-bottom-right-radius:3px;
}


</style>
</head>
<body>

<table>

<tr>
<th>Nome</th>
<th>Cognome</th>
<th>Asl</th>
<th>Username</th>
<th>Ruolo</th>
</tr>
<%


Hashtable listaUtenti = null ;



ConnectionPool cp = (ConnectionPool)application.getAttribute("ConnectionPool");// new ConnectionPool("java:comp/env/jdbc/bdu");
System.out.println("valore di cp "+cp);
SystemStatus thisSystem = null; 
thisSystem = (SystemStatus) ((Hashtable) application.getAttribute("SystemStatus")).get(cp.getUrl());

if(thisSystem != null){
	listaUtenti = thisSystem.getUserList();
	
}



if (listaUtenti != null)
{
	Iterator itKey = listaUtenti.keySet().iterator();
	while (itKey.hasNext())
	{
		int kiave = (Integer) itKey.next();
		User utente = (User)listaUtenti.get(kiave);
		%>
		<tr>
		
		<td><%= utente.getContact().getNameFirst()%></td>
		<td><%= utente.getContact().getNameLast()%></td>
		<td><%= utente.getSiteIdName()%></td>
		<td><%= utente.getUsername()%></td>
		<td><%= utente.getRole()%></td>
		</tr>
		<%
	}
	
}%>

<%!

 String getPref(HttpServletRequest context, String param) {
	ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute(
			"applicationPrefs");
	if (prefs != null) {
		return prefs.get(param);
	} else {
		return null;
	}
}%><%

try
{
String email = "infogisa@izsmportici.it";
Mail mail = new Mail();	
mail.setHost(getPref(request, "MAILSERVER"));
mail.setFrom(getPref(request, "EMAILADDRESS"));
mail.setUser(getPref(request, "EMAILADDRESS"));
mail.setPass(getPref(request, "MAILPASSWORD"));
mail.setPort(Integer.parseInt(getPref(request, "PORTSERVER")));
mail.setTo(email);
mail.setSogg("[#!CUSTOMERSATISFACTION-BDU-KO]");
mail.setTesto("["+new Date()+"] L utente  non è soddisfatto del servizio [], indicando il seguente problema : [Tempo di esecuzione dal submit a rendering :]sec.");

System.out.println("INVIO MAIL "+getPref(request, "MAILSERVER"));
mail.sendMail();
System.out.println("POST INVIO MAIL "+getPref(request, "MAILSERVER"));
}catch(Exception e)
{
	e.printStackTrace();
}
%>
</table>
</body>
</html>