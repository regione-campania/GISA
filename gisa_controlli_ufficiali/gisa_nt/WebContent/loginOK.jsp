<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<script>
window.location.href='/login';
</script>


<%--
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: login.jsp 24362 2007-12-09 17:01:04Z srinivasar@cybage.com $
  - Description: 
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.system.base.ApplicationVersion" %>
<jsp:useBean id="LoginBean" class="org.aspcfs.modules.login.beans.LoginBean" scope="request"/>
<jsp:useBean id="messaggio" class="java.lang.String" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="APP_VERSION" class="java.lang.String" scope="application"/>
<jsp:useBean id="APP_TEXT" class="java.lang.String" scope="application"/>
<jsp:useBean id="APP_ORGANIZATION" class="java.lang.String" scope="application"/>

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
<title><dhv:label name="templates.CentricCRM">G.I.S.A</dhv:label></title>
</head>
<link rel="stylesheet" href="css/template-login.css" type="text/css">
<script language="JavaScript">
  function focusForm(form) {
      form.username.focus();
      return false;
  }
  

  function loginCNS()
  {
 	 document.forms[0].action="Login.do?command=LoginCNS";
 	 document.forms[0].submit();
 	 
  }
</script>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="<% if (request.getAttribute("messaggio")!= null && !request.getAttribute("messaggio").equals(""))  {%>javascript:alert('<%=messaggio%>'); <% }%>"
<% if (request.getParameter("popup") != null) {
    out.println("window.opener.location='MyCFS.do?command=Home'; window.close();");
  } else if (request.getParameter("inline") != null) {
    out.println("window.parent.location='MyCFS.do?command=Home'");
  } else if (LoginBean.getUsername().equals("")) {
    out.println("document.login.username.focus()");
  } else {
    out.println("document.login.password.focus()");
  }%>>
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
  <tr>
    <td height="20%" valign="top" width="100%">
      &nbsp;
    </td>
  </tr>
  <tr>
    <td height="85%" width="100%" valign="top" align="center">
      <%-- Content --%>
          <form name="login" method="POST" action="Login.do?command=Login&auto-populate=true">
            <div class="loginContainer">
              <div class="logoContainer">
                 	<img src="images/concourseSuiteCommunitySplashOLD.png" alt="" width="300" height="200"  border="0" />
                 
              </div>
              <div class="loginBar"> </div>
              <div class="message">
	        <dhv:evaluate if="<%= hasText(LoginBean.getMessage()) %>">
	        <div id = "errore">
	          <%= toHtml(LoginBean.getMessage()) %>
	          </div>
	        </dhv:evaluate>
              </div>
<%
  String scheme = request.getScheme();
  if ("true".equals((String)getServletConfig().getServletContext().getAttribute("ForceSSL")) &&
     scheme.equals("http")) {
%>
                <div class="invalidScheme">
                  <dhv:label name="calendar.siteSecureConnections.text">This site is configured for secure connections only</dhv:label><br />
                  <a href="https://<%= getServerUrl(request) %>"><dhv:label name="calendar.goToSecureLogin">Go to Secure Login</dhv:label></a>
                </div>
<%} else {%>
              <div class="fieldsContainer">
                <span class="fieldLabel"><dhv:label name="accounts.Username">Username</dhv:label>:</span>
                <input type="text" name="username" value="<%= toHtmlValue(LoginBean.getUsername()) %>" size="20"><br/>
                <span class="fieldLabel"><dhv:label name="setup.password.colon">Password:</dhv:label></span>
                <input type="password" name="password" size="20"><br/>
              </div>
              <div class="information">
                <div class="nameAndSecurity">
                   
                    <dhv:evaluate if='<%= applicationPrefs.has("LAYOUT.JSP.LOGIN.TEXT") %>'>
                      <%= toHtml(applicationPrefs.get("LAYOUT.JSP.LOGIN.TEXT")) %>
                    </dhv:evaluate>
                    <% if("https".equals(request.getScheme())) {%>
                      <dhv:label name="calendar.secureLogin">Secure Login</dhv:label>
                    <%} else {%>
                      <dhv:label name="calendar.login">Login</dhv:label>
		  <%}%>
		 
		</div>
              </div>
             
              <div class="buttonContainer">
                <input type="submit" value="<dhv:label name="calendar.login">Login</dhv:label>" name="action">
                 <br><br>
		 	 <a href="javascript:loginCNS()">Entra con CNS</a>
              </div>
            </div>
<%}%>
            <dhv:evaluate if='<%= LoginBean.getRedirectTo() != null %>'>
              <input type="hidden" name="redirectTo" value="<%= LoginBean.getRedirectTo() %>" />
            </dhv:evaluate>
          </form>
          </td>
        </tr>
  <tr>
    <td height="8%" valign="top" width="100%">
      <%-- Copyright --%>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="center">
            <%= toHtml(applicationPrefs.get("VERSION")) %><br />
              <div class="licencing">
              <dhv:evaluate if="<%= hasText(APP_TEXT) %>">
                <%= toHtml(APP_TEXT) %>
                <dhv:evaluate if="<%= hasText(APP_ORGANIZATION) %>"><dhv:label name="calendar.licensedTo.colon" param='<%= "organization="+toHtml(APP_ORGANIZATION) %>'>Licensed to: <%= toHtml(APP_ORGANIZATION) %></dhv:label></dhv:evaluate>
                </dhv:evaluate>
              </div>
           
            </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
