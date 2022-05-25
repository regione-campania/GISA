<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.net.URL"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="configurazione.centralizzata.nuova.gestione.ClientServizioCentralizzato"%>
<%
int responseCodeLogin=-1;
String ambiente = "";

try {
	ClientServizioCentralizzato sclient = new ClientServizioCentralizzato();
    //ambiente="sviluppo";
	ambiente=(String) sclient.getAmbiente().getString("ambiente");
} catch (Exception e) {}

System.out.println("### APPLICATIVO VAM: VERIFICO AMBIENTE. AMBIENTE: "+ambiente +" ###");

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
System.out.println("### APPLICATIVO VAM: CERCO SE ESISTE APPLICATIVO LOGIN. RESPONSE CODE: "+responseCodeLogin +" ###");
   %>


<% boolean redirected = false;

  // During Servlet initialization, the setup parameter is set if the application
  // is completely configured
	
      System.out.println("### APPLICATIVO VAM: ESISTE FILE CONFIGURAZIONE. ###");
	  
	  if (responseCodeLogin == 200) {
	    System.out.println("### APPLICATIVO VAM: ESISTE APPLICATIVO LOGIN. REDIRECTO A /LOGIN ###");
	  	response.sendRedirect("/login");
	  	redirected=true;
	  }
  
	 if (!redirected) { 
	  
  // If the site is setup, then check to see if this is an upgraded version of the app
    //During login, check the application locale if needed
			  if (!"ufficiale".equalsIgnoreCase(ambiente)) {
	    			System.out.println("### APPLICATIVO VAM: NON ESISTE APPLICATIVO LOGIN. REDIRECTO A login.Logout (2) ###");
	    			session.setAttribute("system","vam");
	    			response.sendRedirect("ex_login_031.jsp");
    		  } else {
    			  	System.out.println("### APPLICATIVO VAM: NON ESISTE APPLICATIVO LOGIN IN AMBIENTE UFFICIALE. NON FACCIO NULLA (2) ###");
    		  }

  } 
%>