<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="com.darkhorseventures.database.*"%>
<%@page import="org.aspcfs.controller.*"%>
<%@page import="java.util.*"%> 
<%@page import="org.aspcfs.modules.admin.base.User"%>
<% 
/* ApplicationPrefs prefs = (ApplicationPrefs) request.getSession().getServletContext().getAttribute("applicationPrefs");
String ceDriver = prefs.get("GATEKEEPER.DRIVER");
String ceHost = prefs.get("GATEKEEPER.URL");
String ceUser = prefs.get("GATEKEEPER.USER");
String ceUserPw = prefs.get("GATEKEEPER.PASSWORD"); */

//ConnectionElement ce = new ConnectionElement(ceHost, ceUser, ceUserPw);
ConnectionPool cp = new ConnectionPool("java:comp/env/jdbc/bdu");
SystemStatus thisSystem = null; 
SessionManager sessionManager = null;
HashMap sessions = null;
thisSystem = (SystemStatus) ((Hashtable) application.getAttribute("SystemStatus")).get(cp.getUrl());
if(thisSystem != null){
	sessionManager = thisSystem.getSessionManager();
}
if(sessionManager != null){
	sessions = sessionManager.getSessions();
}
User u = null;
UserSession s = null;
%>
<%=sessions.keySet().size()%> 
