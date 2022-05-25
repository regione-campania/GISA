<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%@page import="com.zeroio.controller.Tracker"%>
<%@page import="java.util.Enumeration"%>
<%@page import="org.aspcfs.controller.ApplicationPrefs"%>
<%@page import="com.darkhorseventures.database.ConnectionElement"%>
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

int indice = 0 ;
while(indice<10)
{
if(sessionManager != null){
	sessions = sessionManager.getSessions();
}

UserSession s = null;

Object oo = null ;
if(sessions != null && sessions.size() > 0){
	
	try{
		for(Object o : sessions.keySet()){
			oo=o;
			s = (UserSession)sessions.get(Integer.parseInt(o.toString()));
			try
			{
				sessions.remove(Integer.parseInt(o.toString()));
			
			}catch(Exception e)
			{
				sessions.remove(Integer.parseInt(o.toString()));
				
			}
		}
	}
	catch(Exception e){
		
		sessions.remove(Integer.parseInt(oo.toString()));
		
		
	}
	
}
indice ++ ;
}



System.gc();

%>


