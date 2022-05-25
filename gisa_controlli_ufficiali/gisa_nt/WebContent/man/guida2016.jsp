<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.Hashtable"%>
<%@page import="com.darkhorseventures.database.ConnectionElement"%>
<%@page import="org.aspcfs.controller.ApplicationPrefs"%>
<%@page import="org.aspcfs.controller.SystemStatus"%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<%
String link = request.getParameter("link");
String suffisso = (String) request.getServletContext().getAttribute("SUFFISSO_TAB_ACCESSI");
String guida = "";
ApplicationPrefs prefs = (ApplicationPrefs) request.getSession().getServletContext().getAttribute("applicationPrefs");
String ceDriver = prefs.get("GATEKEEPER.DRIVER");
String ceHost = prefs.get("GATEKEEPER.URL");
String ceUser = prefs.get("GATEKEEPER.USER");
String ceUserPw = prefs.get("GATEKEEPER.PASSWORD");

ConnectionElement ce = new ConnectionElement(ceHost, ceUser, ceUserPw);

if (suffisso!=null && suffisso.equalsIgnoreCase("_ext"))
{
	guida = "guidaGisaExt.jsp";
	SystemStatus systemStatus = null ;
	systemStatus = (SystemStatus) ((Hashtable) application.getAttribute("SystemStatus")).get(ce.getUrl());
	if(systemStatus.hasPermission(User.getRoleId(), "guida-apicoltura-view"))
	{
		guida="http://www.gisacampania.it/manuali/Manuale Utente BDA-R.pdf";
	}
}
	
else
	guida = "guida.jsp";
guida += "#"+link;
%>

<script>
document.location.href='<%=guida%>'
</script>