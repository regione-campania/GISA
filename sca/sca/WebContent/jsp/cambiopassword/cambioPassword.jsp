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

<body>

<%String error = (String) request.getAttribute("ErroreCambioPassword");
if (error!=null && !error.equals("")){%>
<script>alert('<%=error%>')</script>
<font color="red"><%=error %></font><br/><br/>
<%} %>

<%String conferma = (String) request.getAttribute("ConfermaCambioPassword");
if (conferma!=null && !conferma.equals("")){%>
<script>alert('<%=conferma%>')</script>

<font color="green"><%=conferma.replaceAll(";", "<br/>") %></font><br/>

<% String esitoMail = (String) request.getAttribute("EsitoMail");
if (esitoMail!=null && !esitoMail.equals("")){%>
ESITO INVIO MAIL: <%=esitoMail%><br/>
<%} %>

<input type="button" onClick="reloadOpener()" value="Chiudi e ricarica"/> <br/><br/>
<%} %>

</body>

<div id="formCambioPassword">

<div class="testbox">
  <h1>
  Cambio Password
  
   <a href="guida/Gisa_sca_cambio_password.pdf" target="_blank">  
   <img src="images/question-mark.png" width="30"/>   
   </a> 

  </h1>

<form action="cambiopassword.EseguiCambioPassword.us" method="post" id="cambioPassword">
  <hr>
  <label id="icon" for="name"><img src="images/cambiopassword/username.png"/></label>
 <input type="text" name="username" id="username" value="" placeholder="username"/>
  
 <label id="icon" for="name"><img src="images/cambiopassword/shield.png"/></label>
 <input type="password" name="pwdOld" id="pwdOld" placeholder="vecchia password" value=""/>
 
 <div id="policyPassword">
 <label id="icon" for="name"><img src="images/cambiopassword/shield.png"/></label>
 <input type="password" class="password" name="pwd1" id="pwd1" maxlength="15" placeholder="nuova password" onmouseover="show()" onmouseout="hide()"/>
		<div align="right" id="policyForm" style="display:none"> 
		<font size="1px">
		<ul class="helper-text">
		<li class="length">Lunghezza 10-15 caratteri.</li>
		<li class="lowercase">Contiene almeno una minuscola.</li>
		<li class="uppercase">Contiene almeno una maiuscola.</li>
		<li class="special">Contiene almeno un numero.</li>
		</ul>
		</font>
		</div>
</div>



  <label id="icon" for="name"><img src="images/cambiopassword/shield.png"/></label>
  <input type="password" name="pwd2" id="pwd2" maxlength="15" placeholder="conferma password"/>
 
 <div class="gender">
    <input type="radio" value="None" id="male" name="gender" checked onClick="mostraNascondi(this)"/>
    <label for="male" class="radio" chec>Nascondi</label>
    <input type="radio" value="None" id="female" name="gender" onClick="mostraNascondi(this)"/>
    <label for="female" class="radio">Mostra</label>
   </div> 
   
   <p>La modifica sara loggata e avra' effetto immediato. <a href="#" onClick="showPolicy(); return false;">Policy</a></p>
   <a href="#" class="button" onClick="checkForm(); return false;">Procedi</a>
  </form>
</div>
</div>


<div id="attenderePrego" style="display:none">
<img src="images/loader.gif" />
Modifica password in corso.
</div>

<%@ include file="policyPassword.jsp" %>
