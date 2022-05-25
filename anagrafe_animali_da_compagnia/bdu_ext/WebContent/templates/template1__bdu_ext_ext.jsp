<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>

<!DOCTYPE html>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="it" lang="it">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=windows-1252" />
		 <script src="javascript/modernizr-2.6.2.min.js"></script>
        <script src="javascript/jquery-1.9.1.min.js"></script>
        <script src="javascript/jquery.steps.js"></script>
        <script src="javascript/jquery.validate.js"></script>
        <script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
        
        	<link rel="stylesheet" href="css/ext.css">
        	<link rel="stylesheet" href="css/ext_style.css">
  <script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/quickAction.js?1"></script>
<script language="JavaScript" type="text/javascript" src="javascript/spanDisplay.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/globalItemsPane.js"></script>
        
  <jsp:include page="cssInclude.jsp" flush="true"/>
<%@ page import="org.aspcfs.controller.SubmenuItem,java.text.DateFormat,java.util.Iterator, java.io.File" %>
<jsp:useBean id="ModuleBean" class="org.aspcfs.modules.beans.ModuleBean" scope="request"/>
<jsp:useBean id="GlobalItems" class="java.lang.String" scope="request"/>
<jsp:useBean id="globalItemsPaneState" class="java.lang.String" scope="session"/>

		
		
			  	<link rel="stylesheet" href="css/normalize.css">
			  <link rel="stylesheet" href="css/main.css">
        <link rel="stylesheet" href="css/jquery.steps.css">
        
        
		<link rel="stylesheet" type="text/css" href="css/ext/template_css.css" />
		<link rel="stylesheet" type="text/css" href="css/ext/theme.css" />
		
	
		<title>Gestione servizi esterni BDU</title>
		
	</head>
	
  <body class="white" >
  <%@ include file="../initPage.jsp" %>
    
   
	
      <%
      String includeModule = (String) request.getAttribute("IncludeModule");
      String pathGuida = application.getRealPath("guida");
	  String fileGuidaAtteso = "" ;
      String fileGuida = "" ;//includeModule.split("/")[1]+"_"+ includeModule.split("/")[2].substring(0,includeModule.split("/")[2].length()-3)+"html" ;
      fileGuida = includeModule.split("/")[1];
      File f = new File (pathGuida+"/sezione",fileGuida);
     
      
      %>

    
	
    <div  class="header_sviluppo" >
    
    </div>
    
    <div id="contentBody">

    <div id="sidebar-left">

	<div class="mymoduletable">		
		
		<p id="p3_my">       <dhv:evaluate if='<%= !User.getUserRecord().getContact().getNameFirstLast().equals("") %>'>
        <br />
        <% if(User.getActualUserId() != User.getUserId()) {%>
          <dhv:label name="admin.userAliasedTo" param='<%= "contactName="+toHtml(User.getUserRecord().getContact().getNameFirstLast()) %>'>User Aliased To 
          <span class=""><%= toHtml(User.getUserRecord().getContact().getNameFirstLast()) %></span> /</dhv:label>
        <%} else {%>
          User: <%= toHtml(User.getUserRecord().getContact().getNameFirstLast()) %> /
        <%}%>
      </dhv:evaluate>
     <%= User.getRole() %>
		</p>


	<ul id="qm0" class="qmmc">

	<li>
		<a onclick="" href="Login.do?command=Logout" accesskey="2"><span>Esci</span></a>
	</li>
 <%= request.getAttribute("MenuList") %>
				
	
	<li>  <a href="javascript:popURL('<%="guida/guida.jsp?link="+fileGuida.replaceAll(" ","_") %>','CRM_Help','1000 ','500','yes','yes');" class="s" onMouseOver="window.status='Pop-up Help';return true;" onMouseOut="window.status='';return true;" title="<%=fileGuidaAtteso%>">Guida Utente</a></li>
   </ul>
   
   <p id="p3_my"> <br/>Help Desk:<br/>0810116436 / 0810116437<br/> <br/> 
   </p>
</div>
<img id="finemenu" src="css/ext/images/finemenu.jpg"/>	

</div>
    	 
    	 
    	 
    	 
    	 
    	 
	</div>
	


		<div class="margine">

      
      <jsp:include page="<%= includeModule %>" flush="true"/>
		</div>
				
<%-- 	<div id="footer">
		<div class="padding">
             <div class="moduletable">
				 <jsp:include page="footer.jsp" flush="true"/>
			</div>
		</div>
	</div> --%>
			<script>
history.pushState(null, null, location.href);
window.addEventListener('popstate', function(event) {
history.pushState(null, null, location.href);
});
</script> 		
  </body>

</html>

