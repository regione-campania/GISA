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
  - Version: $Id: template1nav.jsp 24362 2007-12-09 17:01:04Z srinivasar@cybage.com $
  - Description:
  --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="org.aspcfs.controller.SubmenuItem,java.text.DateFormat,java.util.Iterator, java.io.File" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ModuleBean" class="org.aspcfs.modules.beans.ModuleBean" scope="request"/>
<jsp:useBean id="GlobalItems" class="java.lang.String" scope="request"/>
<jsp:useBean id="globalItemsPaneState" class="java.lang.String" scope="session"/>
<!-- import necessari al funzionamento della finestra modale di locking -->
<link rel="shortcut icon" href="images/favicon.ico" />  
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>
<script src='javascript/modalWindow.js'></script>

<%
  response.setHeader("Pragma", "no-cache"); // HTTP 1.0
  response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
  response.setHeader("Expires", "-1");
  boolean globalItemsPaneHidden = "HIDE".equals(globalItemsPaneState);
%>
<html>
<head>
<%@ include file="../initPage.jsp" %>
<title><dhv:label name="templates.CentricCRM">Concourse Suite Community Edition</dhv:label><%= ((!ModuleBean.hasName())?"":": " + ModuleBean.getName()) %></title>
  <jsp:include page="cssInclude.jsp" flush="true"/>
</head>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/quickAction.js?1"></script>
<script language="JavaScript" type="text/javascript" src="javascript/spanDisplay.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/globalItemsPane.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="dwr/engine.js">
	
</script>
<script type="text/javascript" src="dwr/util.js"></script>

<script language="JavaScript" TYPE="text/javascript" SRC="dwr/interface/DwrCustomSatisfaction.js"> </script>
<script language="JavaScript" type="text/javascript" src="javascript/customerSatisfaction.js"></script>	



<body leftmargin="0" rightmargin="0" margin="0" marginwidth="0" topmargin="0" marginheight="0">


<jsp:include page="customerSatisfaction.jsp"></jsp:include>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>
<div id="header">
<table border="0" width="100%" cellpadding="2" cellspacing="0">
  <tr>
    <td valign="top">
      <table border="0" cellpadding="0" cellspacing="2">
        <tr>
          <td valign="top">
            <dhv:label name="logo"><img src="images/bdu.png" align="absMiddle" height="80" width="218" border="0" /></dhv:label>
          </td>
        </tr>

      </table>
    </td>
    <th align="right" valign="top" nowrap>
      
    <img src="images/icons/stock_help-16.gif" border="0" align="absmiddle" height="16" width="16" />
      <%
      String includeModule = (String) request.getAttribute("IncludeModule");
      String pathGuida = application.getRealPath("guida");
	  String fileGuidaAtteso = "" ;
      String fileGuida = "" ;//includeModule.split("/")[1]+"_"+ includeModule.split("/")[2].substring(0,includeModule.split("/")[2].length()-3)+"html" ;
      fileGuida = includeModule.split("/")[1];
      File f = new File (pathGuida+"/sezione",fileGuida);
     
      
      %>

      <a href="javascript:popURL('<%="guida/guida.jsp?link="+fileGuida.replaceAll(" ","_") %>','CRM_Help','1000 ','500','yes','yes');" class="s" onMouseOver="window.status='Pop-up Help';return true;" onMouseOut="window.status='';return true;" title="<%=fileGuidaAtteso%>">Guida Utente</a>
     |
      <dhv:permission name="admin-view">
      <img src="images/icons/stock_form-properties-16.gif" border="0" align="absmiddle" height="16" width="16" />
      <a href="Admin.do"><dhv:label name="trails.admin">Admin</dhv:label></a>
      |</dhv:permission>
      <img src="images/icons/stock_exit-16.gif" border="0" align="absmiddle" height="16" width="16"/>
      <a href="Login.do?command=Logout" class="s"><dhv:label name="global.button.Logout">Logout</dhv:label></a>
      |<img src="images/icons/stock_about-16.gif" border="0" align="absmiddle" height="16" width="16"/>
     
      <dhv:evaluate if='<%= !User.getUserRecord().getContact().getNameFirstLast().equals("") %>'>
        <br />
        <% if(User.getActualUserId() != User.getUserId()) {%>
          <dhv:label name="admin.userAliasedTo" param='<%= "contactName="+toHtml(User.getUserRecord().getContact().getNameFirstLast()) %>'>User Aliased To <span class="highlight"><%= toHtml(User.getUserRecord().getContact().getNameFirstLast()) %></span> /</dhv:label>
        <%} else {%>
          <dhv:label name="admin.user.colon" param='<%= "contactName="+toHtml(User.getUserRecord().getContact().getNameFirstLast()) %>'>User: <span class="highlight"><%= toHtml(User.getUserRecord().getContact().getNameFirstLast()) %></span> /</dhv:label>
        <%}%>
      </dhv:evaluate>
      <b class="highlight"><%= User.getRole() %></b>
      <dhv:evaluate if="<%= User.getUserRecord().getManagerUser() != null && User.getUserRecord().getManagerUser().getContact() != null %>">
        <br /><dhv:label name="admin.manager.colon" param='<%= "managerName="+toHtml(User.getUserRecord().getManagerUser().getContact().getNameFull()) %>'>Manager: <span class="highlight"><%= toHtml(User.getUserRecord().getManagerUser().getContact().getNameFull()) %></span></dhv:label>
      </dhv:evaluate>
      <dhv:evaluate if='<%= System.getProperty("DEBUG") != null && "2".equals(System.getProperty("DEBUG")) && request.getAttribute("debug.action.time") != null %>'>
        <br />
        <dhv:permission name="admin-usage-view">
          Users logged in: <span class="highlight"><%= User.getSystemStatus(getServletConfig()).getTracker().getUserCount() %></span>
        </dhv:permission>
        <dhv:permission name="website-view">
          Website visitors: <span class="highlight"><%= User.getSystemStatus(getServletConfig()).getTracker().getGuestCount() %></span>
        </dhv:permission>
        <dhv:label name="admin.actionTook.colon" param='<%= "time=" + request.getAttribute("debug.action.time") %>'>Action took:</dhv:label>
      </dhv:evaluate>
      </th>
  </tr>
</table>


</div>
<!-- Main Menu -->
<div id="topmenutabs">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
  <tr>
    <td><img border="0" src="images/blank.gif" /></td>
    <%= request.getAttribute("MainMenuTops") %>
    <td style="width:100%; background: #FFF;"><img border="0" src="images/blank.gif" /></td>
  </tr>
  <tr>
    <td class="menuBackground">&nbsp;</td>
    <%= request.getAttribute("MainMenu") %>
    <td width="100%" class="menuBackground"><img border="0" src="images/blank.gif" /></td>
  </tr>
</table>
</div>
<!-- Sub Menu -->
<div id="header">
<table border="0" width="100%" cellspacing="0" class="submenu">
  <tr>
    <td width="100%">
      <table border="0" cellspacing="0" class="submenuItem">
        <tr>
          <td width="5"><font size="1">&nbsp;</font></td>
<%
 
    Iterator i = ModuleBean.getMenuItems().iterator();
    while (i.hasNext()) {
   
    	SubmenuItem thisItem = (SubmenuItem)i.next();
    	//thisItem.
    	if ("".equals(thisItem.getPermission()) || User.getSystemStatus(getServletConfig()).hasPermission(User.getRoleId(), thisItem.getPermission())) {
%>
          <td ><%= (thisItem.getAlternateHtml(User.getSystemStatus(getServletConfig()))) %></td>
          <td width="10">&nbsp;</td>
<%
      }
    }
%>
          <td><font size="1">&nbsp;</font></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</div>
<table border="0" width="100%" cellpadding="0" cellspacing="0" class="layoutPane">
  <tr>
    <td valign="top" width="100%" class="contentPane">
      <!-- The module goes here -->
      
      <jsp:include page="<%= includeModule %>" flush="true"/>
      <!-- End module -->
    </td>
  
  </tr>
</table>
<div id="footer">
<br />
<center>
<zeroio:tz timestamp="<%= new java.util.Date() %>" timeFormat="<%= DateFormat.LONG %>" timeZone="<%= User.getTimeZone()%>" /><br />
&#169; Copyright 2000-2007 Concourse Suite Community Edition &#149; <dhv:label name="global.label.allRightsReserved">All rights reserved.</dhv:label><br />
</div>
<%-- Allow pages have to have a scrollTo... must be at end of html --%>
<script language="JavaScript" type="text/javascript" src="javascript/scrollReload.js"></script>
<dhv:evaluate if='<%= request.getParameter("scrollTop") != null %>'>
<script language="JavaScript" type="text/javascript">
    if (window.scrollTo) window.scrollTo(<%= request.getParameter("scrollLeft") %>, <%= request.getParameter("scrollTop") %>);
</script>
</dhv:evaluate>
<iframe src="empty.html" name="template_commands" id="template_commands" style="visibility:hidden" height="0"></iframe>


<script>
 $(document).ready(function() {
	
	
	 if (document.forms[0]!=null){
// 		 alert(document.forms[0].getAttribute("onsubmit"))
		 var func = document.forms[0].onsubmit;
 		document.forms[0].onsubmit=function(event){
 			setTimestampStartRichiesta();
 		//	alert(func);
 			if(func!=null)
 				return func();
 			};
 		
	 }

 		 var oInput=document.createElement("INPUT");
 		oInput.setAttribute("type","hidden");
 		oInput.setAttribute("name","endTime");
 		oInput.setAttribute("id","endTime");
 		oInput.setAttribute("value",new Date().getTime());
 		document.getElementById("dialogCustomerSatisfaction").appendChild(oInput);
 });
 </script>

<script>
history.pushState(null, null, location.href);
window.addEventListener('popstate', function(event) {
history.pushState(null, null, location.href);
});
</script> 

<%if (request.getAttribute("customerSatisfaction")!=null){ %>
<script>openCustomerSatisfaction();</script>
<%} %>
</body>
</html>
