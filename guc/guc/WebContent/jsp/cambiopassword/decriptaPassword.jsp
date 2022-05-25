<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="java.io.*"%>

<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<link rel="stylesheet" href="css/cambiopassword/cambioPassword.css" type="text/css" media="screen" />
<link href='css/cambiopassword/OpenSans.css' rel='stylesheet' type='text/css'>
<link href="css/cambiopassword/font-awesome.css" rel="stylesheet">
<script language="JavaScript" TYPE="text/javascript" SRC="js/cambioPassword.js"></script>

<%String pwdDaDecriptare = (String)  request.getAttribute("pwdDaDecriptare");
String pwdDecriptata = (String)  request.getAttribute("pwdDecriptata");

if (pwdDaDecriptare==null || pwdDaDecriptare.equals("null"))
	pwdDaDecriptare="";
if (pwdDecriptata==null || pwdDecriptata.equals("null"))
	pwdDecriptata="";
%>

<div id="formDecriptaPassword">

<div class="testbox">
  <h1>Decripta Password</h1>

<form action="cambiopassword.DecriptaPassword.us" method="post" id="decriptaPassword">
  <hr>
 
 <label id="icon" for="name"><img src="images/cambiopassword/shield.png"/></label>
 <input type="text" name="pwdDaDecriptare" id="pwdDaDecriptare" placeholder="password" value="<%=pwdDaDecriptare%>"/>
 
 <label id="icon" for="name"><img src="images/cambiopassword/shield.png"/></label>
 <input type="text" name="pwdDecriptata" id="pwdDecriptata" placeholder="decriptata" readonly value="<%=pwdDecriptata%>"/>

    <a href="#" class="button" onClick="checkFormDecrypt(); return false;">Decripta</a>
  </form>
</div>
</div>



