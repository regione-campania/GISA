<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
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









<% 
Date adesso = new Date();


int  numTotaleConnessioniAttive = 0 ;

ConnectionPool cp = new ConnectionPool("java:comp/env/jdbc/bduM");
SystemStatus thisSystem = null; 
SessionManager sessionManager = null;
HashMap sessions = null;
thisSystem = (SystemStatus) ((Hashtable) application.getAttribute("SystemStatus")).get(cp.getUrl());
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





<% 
int aslUtente = 0;
int prog = 0 ;
for(Object o : sessions.keySet()){
	HashMap listaConnectionForUser = (HashMap) sessions.get(o);
	
	numTotaleConnessioniAttive += listaConnectionForUser.size();
	
	
	
	
		

%>


	<%
} %>




<%
}
%>

<%=numTotaleConnessioniAttive %>



