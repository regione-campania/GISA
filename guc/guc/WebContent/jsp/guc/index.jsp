<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.db.ApplicationProperties"%>
<script src="<%=ApplicationProperties.getProperty("SITO_GISA")%>/js/GisaSpid.js"></script>

<%
//String test = (String)request.getAttribute("test");
String test = null;
%>

<div id="content" align="center">
<center>      
	<h1>Login</h1>
 	<div align="center">
	  	<form id="f1" action="login.Login.us" method="post">					
			<!-- b>Username  </b> <br>
				<input type="text" name="utente" /> <br>					
			<b>Password  </b> <br>
				<input type="password" name="password" /> <br>
			<br>	
			<input type="hidden" name="action" value="login"/>
			<input type="submit" value="Entra" -->
			
<%
if(test!=null)
{
%>	
			<input type="text" name="cf_spid" id="cf_spid"/> <br>
<%
}
else
{
	%>	
			<input type="hidden" name="cf_spid" id="cf_spid"/> <br>
<%	
}
%>
			<input type="hidden" name="tk_spid" id="tk_spid"/> <br>
			<input type="button" value="Entra con SPID/CIE" onclick="GisaSpid.entraConSpid(event, null, null, 'callbackfoo', null, 'cfs.json');">
			
<%
if(test!=null)
{
%>
			<input type="submit" value="Entra-test" >
<%
}
%>
			
		</form>	
	</div>
</center>
</div>



<script type="text/javascript">
	function callbackfoo(data){

/* riempire i campi CF e token, quindi eseguire submit */
//	alert("spid-ret: " + JSON.stringify(data));
	if(data==null)
		{
			alert('Attenzione. Accesso non consentito.');
		}
	else
		{
    attendere();
	var cf_spid = document.getElementById('cf_spid');
	var tk_spid = document.getElementById('tk_spid');
	tk_spid.value=data.token;
	cf_spid.value=data.fiscalCode;

	console.log('token: ' + tk_spid.value);

//alert(tk_spid.value);
	document.forms[0].submit();
		}
}
	
	
	
</script>
