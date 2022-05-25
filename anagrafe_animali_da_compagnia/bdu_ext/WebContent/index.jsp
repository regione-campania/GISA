<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.utils.ApplicationProperties"%>
<%@ page import = "java.net.HttpURLConnection" %>
<%@ page import = "java.net.URL" %>

<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>

<%
int responseCodeLogin=-1;
String ambiente = "";

try {
ambiente = ApplicationProperties.getAmbiente();
} catch (Exception e) {}

System.out.println("### APPLICATIVO GISA: VERIFICO AMBIENTE. AMBIENTE: "+ambiente +" ###");

String URLName=request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort() + "/login/index.jsp"  ;
try {
       HttpURLConnection.setFollowRedirects(false);
       HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
       con.setRequestMethod("HEAD");
       responseCodeLogin=con.getResponseCode();
     }
     catch (Exception e) {
        e.printStackTrace();
     }
System.out.println("### APPLICATIVO GISA: CERCO SE ESISTE APPLICATIVO LOGIN. RESPONSE CODE: "+responseCodeLogin +" ###");
   %>


<% boolean redirected = false;

  // During Servlet initialization, the setup parameter is set if the application
  // is completely configured
  if ((Object) getServletConfig().getServletContext().getAttribute("cfs.setup") == null) {
    System.out.println("### APPLICATIVO GISA: NON ESISTE FILE CONFIGURAZIONE. REDIRECTO ALLA CONFIGURAZIONE ###");
    RequestDispatcher setup = getServletConfig().getServletContext().getRequestDispatcher("/Setup.do?command=Default");
    setup.forward(request, response);
  } else {
	
      System.out.println("### APPLICATIVO GISA: ESISTE FILE CONFIGURAZIONE. ###");
	  
	  if (responseCodeLogin == 200) {
	    System.out.println("### APPLICATIVO GISA: ESISTE FILE CONFIGURAZIONE ED ESISTE APPLICATIVO LOGIN. REDIRECTO A /LOGIN ###");
	  	response.sendRedirect("/login");
	  	redirected=true;
	  }
  
	 if (!redirected) { 
	  
  // If the site is setup, then check to see if this is an upgraded version of the app
  if (applicationPrefs.isUpgradeable()) {
    RequestDispatcher upgrade = getServletConfig().getServletContext().getRequestDispatcher("/Upgrade.do?command=Default");
    upgrade.forward(request, response);
  } else {
			  if (!"ufficiale".equalsIgnoreCase(ambiente)) {
	    			System.out.println("### APPLICATIVO GISA: ESISTE FILE CONFIGURAZIONE E NON ESISTE APPLICATIVO LOGIN. REDIRECTO A EX_LOGIN.JSP (2) ###");
				    response.sendRedirect("ex_login_031.jsp");
    		  } else {
    			  	System.out.println("### APPLICATIVO GISA: ESISTE FILE CONFIGURAZIONE E NON ESISTE APPLICATIVO LOGIN IN AMBIENTE UFFICIALE. NON FACCIO NULLA (2) ###");
    		  }

  } } }
%>