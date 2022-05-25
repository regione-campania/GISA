<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script>
window.location.href='/login';
</script>





<%--
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: login.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description: 
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.system.base.ApplicationVersion" %>
<jsp:useBean id="LoginBean" class="org.aspcfs.modules.login.beans.LoginBean" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="APP_VERSION" class="java.lang.String" scope="application"/>
<jsp:useBean id="APP_TEXT" class="java.lang.String" scope="application"/>
<jsp:useBean id="APP_ORGANIZATION" class="java.lang.String" scope="application"/>
<jsp:useBean id="messaggio" class="java.lang.String" scope="request"/>

<%!
  public static String getLongDate(java.util.Date tmp) {
    java.text.SimpleDateFormat formatter1 = new java.text.SimpleDateFormat ("MMMMM d, yyyy");
    return(formatter1.format(tmp));
  }
%>
<%
  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
  response.setHeader("Expires", "-1");
%>
<%@ include file="initPage.jsp" %>
<html>
<head>
<title><dhv:label name="templates.CentricCRM">Banca Dati Unificata - Accesso</dhv:label></title>
</head>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/geolocation.js"></script>
<link rel="shortcut icon" href="images/favicon.ico" /> 
<link rel="stylesheet" href="css/template-login.css" type="text/css">
<script language="JavaScript">
  function focusForm(form) {
      form.username.focus();
      return false;
  }
</script>



 <script>

 function loginCNS()
 {
	 document.forms[0].action="Login.do?command=LoginCNS";
	 document.forms[0].submit();
	 
 }
 var nVer = navigator.appVersion;
 var nAgt = navigator.userAgent;
 var browserName  = navigator.appName;
 var fullVersion  = ''+parseFloat(navigator.appVersion); 
 var majorVersion = parseInt(navigator.appVersion,10);
 var nameOffset,verOffset,ix;

 // In Opera 15+, the true version is after "OPR/" 
 if ((verOffset=nAgt.indexOf("OPR/"))!=-1) {
  browserName = "Opera";
  fullVersion = nAgt.substring(verOffset+4);
 }
 // In older Opera, the true version is after "Opera" or after "Version"
 else if ((verOffset=nAgt.indexOf("Opera"))!=-1) {
  browserName = "Opera";
  fullVersion = nAgt.substring(verOffset+6);
  if ((verOffset=nAgt.indexOf("Version"))!=-1) 
    fullVersion = nAgt.substring(verOffset+8);
 }
 // In MSIE, the true version is after "MSIE" in userAgent
 else if ((verOffset=nAgt.indexOf("MSIE"))!=-1) {
  browserName = "Microsoft Internet Explorer";
  fullVersion = nAgt.substring(verOffset+5);
 }
 // In Chrome, the true version is after "Chrome" 
 else if ((verOffset=nAgt.indexOf("Chrome"))!=-1) {
  browserName = "Chrome";
  fullVersion = nAgt.substring(verOffset+7);
 }
 // In Safari, the true version is after "Safari" or after "Version" 
 else if ((verOffset=nAgt.indexOf("Safari"))!=-1) {
  browserName = "Safari";
  fullVersion = nAgt.substring(verOffset+7);
  if ((verOffset=nAgt.indexOf("Version"))!=-1) 
    fullVersion = nAgt.substring(verOffset+8);
 }
 // In Firefox, the true version is after "Firefox" 
 else if ((verOffset=nAgt.indexOf("Firefox"))!=-1) {
  browserName = "Firefox";
  fullVersion = nAgt.substring(verOffset+8);
 }
 // In most other browsers, "name/version" is at the end of userAgent 
 else if ( (nameOffset=nAgt.lastIndexOf(' ')+1) < 
           (verOffset=nAgt.lastIndexOf('/')) ) 
 {
  browserName = nAgt.substring(nameOffset,verOffset);
  fullVersion = nAgt.substring(verOffset+1);
  if (browserName.toLowerCase()==browserName.toUpperCase()) {
   browserName = navigator.appName;
  }
 }
 // trim the fullVersion string at semicolon/space if present
 if ((ix=fullVersion.indexOf(";"))!=-1)
    fullVersion=fullVersion.substring(0,ix);
 if ((ix=fullVersion.indexOf(" "))!=-1)
    fullVersion=fullVersion.substring(0,ix);

 majorVersion = parseInt(''+fullVersion,10);
 if (isNaN(majorVersion)) {
  fullVersion  = ''+parseFloat(navigator.appVersion); 
  majorVersion = parseInt(navigator.appVersion,10);
 }

 /*document.write(''
  +'Browser name  = '+browserName+'<br>'
  +'Full version  = '+fullVersion+'<br>'
  +'Major version = '+majorVersion+'<br>'
  +'navigator.appName = '+navigator.appName+'<br>'
  +'navigator.userAgent = '+navigator.userAgent+'<br>'
 )*/
 
 
    function checkBrowser() { 
    	
     if(navigator.userAgent.indexOf("Chrome") != -1 ) 
    {
    	if(confirm('Attenzione! Stai utilizzando un browser diverso da Firefox. La scelta può generare problemi nell\'utilizzo del sistema.\nSei proprio sicuro di voler completare l\' accesso? Se sì, cliccare \'OK\' altrimenti \'Annulla\'.')){
    		 document.login.submit();
    	} 
    }
    else {
    	if(fullVersion >=30) {
    		 document.login.submit();
    	}else{
    		if(confirm('Attenzione! Stai utilizzando una versione diversa o non aggiornata di Firefox che potrebbe generare problemi nell\'utilizzo del sistema.\nSei proprio sicuro di voler proseguire con l\' accesso in GISA?')){
    			document.login.submit();
    		}
    	}
    
    } 
    
    }
 </script>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="<% if (request.getAttribute("messaggio")!= null && !request.getAttribute("messaggio").equals(""))  {%>javascript:alert('<%=messaggio%>'); <% }%>"
<%
  if (request.getParameter("popup") != null) {
    out.println("window.opener.location='MyCFS.do?command=Home'; window.close();");
  } else if (request.getParameter("inline") != null) {
    out.println("window.parent.location='MyCFS.do?command=Home'");
  } else if (LoginBean.getUsername().equals("")) {
    out.println("document.login.username.focus()");
  } else {
    out.println("document.login.password.focus()");
  }
%>>
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
  <tr>
    <td height="7%" valign="top" width="100%">
      &nbsp;
    </td>
  </tr>
  <tr>
 
    <td height="85%" width="100%" valign="top">
      <%-- Content --%>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="9%" height="26"><img src="images/sidespace.gif" width="97" height="1"></td>
          <td width="1%" height="26" colspan="3">&nbsp;</td>
        </tr>
        <tr>
          <td colspan="4">
          <form name="login" method="POST" action="Login.do?command=Login&auto-populate=true">
            <table width="100%" cellspacing="1" cellpadding="3" border="0">
              <tr>
                <td colspan="4" align="center">
                  <dhv:evaluate if='<%= !applicationPrefs.has("LAYOUT.JSP.LOGIN.LOGO") %>'>                  
                  <img src="images_<%=application.getInitParameter("context_starting") %>/bdu_<%=ApplicationProperties.getProperty("AMBIENTE") %>.png"  border="0" />                    
                  </dhv:evaluate>
                  <br><br>
                  <dhv:evaluate if='<%= applicationPrefs.has("LAYOUT.JSP.LOGIN.LOGO") %>'>
                    <%= applicationPrefs.get("LAYOUT.JSP.LOGIN.LOGO") %>
                  </dhv:evaluate>
                </td>
              </tr>
              <tr>
                <td border="0">
                <!--<table style="border:1px #EFEFEF solid;background: #EFEFEF" align="center" width="50%">-->
                <TABLE BORDER="0">
                <tr style="display:none"><td align="center">
                  <font size="2"><strong>
                    <dhv:evaluate if='<%= !applicationPrefs.has("LAYOUT.JSP.LOGIN.TEXT") %>'>
                      Banca dati unificata - Accesso
                    </dhv:evaluate>
                    <dhv:evaluate if='<%= applicationPrefs.has("LAYOUT.JSP.LOGIN.TEXT") %>'>
                      <%= toHtml(applicationPrefs.get("LAYOUT.JSP.LOGIN.TEXT")) %>
                    </dhv:evaluate>
                    <% if("https".equals(request.getScheme())) {%>
                      <dhv:label name="calendar.secureLogin">Secure Login</dhv:label>
                    <%} else {%>
                    	<!-- Etichetta dopo "Circuito Commerciale" -->
                      <dhv:label name=""></dhv:label>
                    <%}%></strong>
                  <dhv:evaluate if="<%= hasText(APP_TEXT) %>">
                    <br /><%= toHtml(APP_TEXT) %>
                    <dhv:evaluate if="<%= hasText(APP_ORGANIZATION) %>"><br /><dhv:label name="calendar.licensedTo.colon" param='<%= "organization="+toHtml(APP_ORGANIZATION) %>'>Licensed to: <%= toHtml(APP_ORGANIZATION) %></dhv:label></dhv:evaluate>
                  </dhv:evaluate>
                  </font>
                </td></tr>
                </table>
                </td>
              </tr>
<%
  String scheme = request.getScheme();
  if ("true".equals((String)getServletConfig().getServletContext().getAttribute("ForceSSL")) && 
     scheme.equals("http")) {
%>              
              <tr> 
                <td align="center" colspan="4">
                  <font size="2"><dhv:label name="calendar.siteSecureConnections.text">This site is configured for secure connections only</dhv:label></font><br />
                  <font size="2"><a href="https://<%= getServerUrl(request) %>"><dhv:label name="calendar.goToSecureLogin">Go to Secure Login</dhv:label></a></font>
                </td>
              </tr>
<%} else {%>
              <tr>
                <td align="center" colspan="4">
                
              <table style="border:1px #EFEFEF solid" align="center" width="50%">
              <tr><td colspan="2">&nbsp;</td></tr>
<div id="errore">
<dhv:evaluate if="<%= hasText(LoginBean.getMessage()) %>">
</div>
              <tr>
                <td align="center" colspan="2">
                  <center><font size="2" color='red'><div id="errore"><%= toHtml(LoginBean.getMessage()) %></div></font></center>
                </td>
              </tr>
</dhv:evaluate>
              <tr><td align="center">
              <table border="0">
              <tr>
                <td align="right">
                  <font size="2"><dhv:label name="accounts.Username">Username</dhv:label>:</font>
                </td>
                <td>
                  <font size="2"><input type="text" name="username" value="<%= toHtmlValue(LoginBean.getUsername()) %>" size="20">&nbsp;</font>
                </td>
              </tr>
              <tr>
                <td align="right">
                  <font size="2"><dhv:label name="setup.password.colon">Password:</dhv:label></font>
                </td>
                <td>
                  <font size="2"><input type="password" name="password" size="20"></font>
                </td>
              </tr>
              <tr>
                <td align="right">&nbsp;</td>
                <td>
                
                <input type="hidden" name="access_position_lat">
             	<input type="hidden" name="access_position_lon">
             	<input type="hidden" name="access_position_err">
             	
             	
                  <input type="button" onclick="javascript:checkBrowser();" value="<dhv:label name="calendar.login">Accesso</dhv:label>" name="action">
                </td>
              </tr>
              </table>
              </td></tr>
              <tr><td colspan="2">&nbsp;</td></tr>
              </table>
              </td>
            </tr>
<%}%>
            </table>
            <dhv:evaluate if='<%= LoginBean.getRedirectTo() != null %>'>
              <input type="hidden" name="redirectTo" value="<%= LoginBean.getRedirectTo() %>" />
            </dhv:evaluate>
            

            
            
          </form>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="8%" valign="top" width="100%">
      <%-- Copyright --%>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="center">
            <%= toHtml(applicationPrefs.get("VERSION")) %><br />
            &#169; Copyright 2000-2007 Centric CRM &#149; <!--<dhv:label name="global.label.allRightsReserved">All rights reserved.</dhv:label>-->
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>

<script language="JavaScript">
  setPositionField(document.login.access_position_lat,document.login.access_position_lon,document.login.access_position_err);
</script>


</body>
</html>


