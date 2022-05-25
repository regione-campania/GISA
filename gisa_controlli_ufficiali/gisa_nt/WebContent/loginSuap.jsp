<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

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
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="APP_VERSION" class="java.lang.String" scope="application"/>
<jsp:useBean id="APP_TEXT" class="java.lang.String" scope="application"/>
<jsp:useBean id="APP_ORGANIZATION" class="java.lang.String" scope="application"/>

<%
  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
  response.setHeader("Expires", "-1");
  
%>



<%@ include file="initPage.jsp" %>
<html>
<head>
<title><dhv:label name="templates.CentricCRM">G.I.S.A</dhv:label></title>

  <link href="css/tooltip.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
    <script src="javascript/tooltip.js" type="text/javascript"></script>
    
</head>
<link rel="stylesheet" href="css/template-login-suap.css" type="text/css">  
<link rel="stylesheet" href="css/stilisuap.css" type="text/css">  
<script language="JavaScript">
  function focusForm(form) {
      form.username.focus();
      return false;
  }
  function gestisciCb() {
     var cb = document.getElementById("delegatoUguale");
     var delegato = document.getElementById("codiceFiscaleDelegato");
     var richiedente = document.getElementById("codiceFiscaleRichiedente");
     
     if (cb.checked){
    	 delegato.value = richiedente.value;
    	 delegato.readOnly="readOnly";
    	 delegato.style.color = "grey";
    	 richiedente.readOnly="readOnly";
    	 richiedente.style.color = "grey";
     }
     else{
    	 delegato.value ="";
    	 delegato.readOnly="";
    	 delegato.style.color = "";
    	 richiedente.readOnly="";
    	 richiedente.style.color = "";
     }
     
  }
 
</script>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" >

<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
     
  <tr>
    <td height="85%" width="100%" valign="top" align="center">
    <img src="images/regionecampania_transparent.png" width="150" alt="" border="0" />       
    
    <img src="images/suap/suaplogo2.png" width="200" heigth="180"  alt="" border="0" /> 
    
     <dhv:evaluate if="<%= hasText(LoginBean.getMessage()) %>">
	 <h5><font color="red"><%= toHtml(LoginBean.getMessage()) %></font></h5> 
	 </dhv:evaluate>
	        
          

          </td>
        </tr>

</table>
</body>
</html>
