<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="com.darkhorseventures.database.ConnectionElement"%>
<%@page import="org.aspcfs.controller.*"%>
<%@page import="java.util.*"%> 
<%@page import="org.aspcfs.modules.admin.base.User"%>

<% 
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
	sessions = sessionManager.getSessions();
}
%>
<html><body>
<%= request.getContextPath()%>  
<a href="checkUtenti.jsp" target=_blank>
<script>var d=new Date(); document.write('<b> Utenti alle ' + d.getHours()+'-'+ d.getMinutes() +'-'+  d.getSeconds() + '</b> :  ')</script>
</a>
<span style="background-color:yellow;font:small-caption;font-size:130%;font-weight:bold">
&nbsp; <%= sessions != null ? sessions.keySet().size() : "Nessun utente"%>  &nbsp;
</span>
</body></html>
