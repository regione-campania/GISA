<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="com.darkhorseventures.database.ConnectionElement"%>
<%@page import="com.darkhorseventures.database.ActionDb"%>
<%@page import="com.darkhorseventures.database.ConnectionPool"%>
<%@page import="org.aspcfs.modules.login.beans.UserBean"%>
<%@page import="org.aspcfs.controller.SubmenuItem"%>
<%@page import="java.net.URL"%>
<%@page import="org.aspcfs.controller.MainMenuItem"%>
<%@page import="com.zeroio.controller.Tracker"%>
<%@page import="java.util.Enumeration"%>
<%@page import="org.aspcfs.controller.ApplicationPrefs"%>
<%@page import="org.aspcfs.controller.SystemStatus"%>
<%@page import="java.util.Hashtable"%>
<%@page import="org.aspcfs.controller.SessionManager"%>
<%@page import="java.util.HashMap"%> 
<%@page import="org.aspcfs.controller.UserSession"%>
<%@page import="org.aspcfs.modules.admin.base.User"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.concurrent.TimeUnit"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.Dictionary"%>
<%@page import="java.util.Map"%>
<%@page import="org.w3c.dom.Node"%>
<%@page import="org.w3c.dom.Element"%>
<%@page import="org.aspcfs.utils.XMLUtils"%>
<%@page import="org.w3c.dom.NodeList"%>




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



<% 
Date adesso = new Date();



ApplicationPrefs prefs = (ApplicationPrefs) request.getSession().getServletContext().getAttribute("applicationPrefs");
String ceDriver = prefs.get("GATEKEEPER.DRIVER");
String ceHost = prefs.get("GATEKEEPER.URL");
String ceUser = prefs.get("GATEKEEPER.USER");
String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");

ConnectionElement ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
SystemStatus thisSystem = null; 
SessionManager sessionManager = null;
HashMap sessions = null;
thisSystem = (SystemStatus) ((Hashtable) application.getAttribute("SystemStatus")).get(ce.getUrl());
if(thisSystem != null){
	sessionManager = thisSystem.getSessionManager();
	
}
if(sessionManager != null){
	sessions = sessionManager.getDbconnection();
}



User u = null;
UserSession s = null;

if(sessions != null && sessions.size() > 0){

%>




<table>
<thead>
<tr>
<th colspan="5">Lista Connessioni Aperte</th>
</tr>
<tr>
<th>UserID.</th>
<th>Action</th>
<th>ActionPathE</th>
<th>comando</th>
<th>Time</th>
<th>Ip</th>
</tr>
</thead>
<% 
int aslUtente = 0;
int prog = 0 ;
for(Object o : sessions.keySet()){
	HashMap listaConnectionForUser = (HashMap) sessions.get(o);
	
	for (Object oo : listaConnectionForUser.keySet())
	{
		ActionDb aa = (ActionDb)listaConnectionForUser.get(oo) ;
		%>
		<tr>
<td><%=o.toString() %></td>
<td><%=aa.getActionName() %></td>
<td><%=aa.getActionppathname() %></td>
<td><%=aa.getCommand() %></td>
<td><%=aa.getDataApertura() %></td>
<td><%=aa.getIpChiamante() %></td>
</tr>
		<%
		
	}
	
	
	
	
		

%>


	<%
} %>




<%
}
%>

</table>
<br><br>




