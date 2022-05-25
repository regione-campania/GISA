<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.db.ApplicationProperties"%>
<%@page import="it.us.web.action.GenericAction"%>
<%@page import="it.us.web.action.login.*"%>

<%
	//GenericAction.redirectTo("login.LogoutSca.us", request, response);
    boolean spidAttivato = false;
%>


<style>

/* unvisited link */


/* mouse over link */
h3:hover {
  color: hotpink;
  background-color: #e4e4e4;
}


.btn {

  background: #d9a234;
  background-image: linear-gradient(to bottom, #d9a234, #a3520c);
  border-radius: 9px;
  box-shadow: 3px 5px 5px #666666;
  font-family: Geneva, sans-serif;
  color: #ffffff;
  font-size: 10px;
  padding: 1px 10px 1px 10px;
  text-decoration: none;
}

.btn:hover {
  background: #ccbb21;
  background-image: linear-gradient(to bottom, #ccbb21, #c48351);
  color:#0a0a0a;
  font-weight: bold;
  text-decoration: none;
}
</style>


<script src="<%=ApplicationProperties.getProperty("SITO_GISA")%>/js/GisaSpid.js"></script>

<script>
<%
	if(request.getAttribute("reload")==null && spidAttivato)
	{
%>
		window.location.href="/login";
<%	
	}
%>
function gotoV() {
	
	f=document.getElementById('f1');
	//f.action='http://vam.anagrafecaninacampania.it/vam/login.Login.us';
	f.action='http://srvVAMW/vam/login.Login.us';
	f.submit();
}
function gotoB() {
	
	f=document.getElementById('f1');
	//f.action='http://srv.anagrafecaninacampania.it/bdu/Login.do?command=Login&auto-populate=true';
	f.action='http://srvBDUW/bdu/Login.do?command=Login&auto-populate=true';
	u=document.getElementById('usr');
	u.name='username';
	f.submit();
}
function gotoG() {
	
	f=document.getElementById('f1');
	//f.action='http://srv.gisacampania.it/gisa_nt/Login.do?command=Login&auto-populate=true';
	f.action='http://srvGISAW/gisa_nt/Login.do?command=Login&auto-populate=true';
	u=document.getElementById('usr');
	u.name='username';
	f.submit();
}

</script>

<script>
function openPopupCambioPassword(){
	        
	  window.open('cambiopassword.CambioPassword.us','popupSelect',
	         'height=600px,width=600px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}

function chkUser(iduser){
	if(document.getElementById(iduser).value+" " == " ") {
		alert('Inserire il corretto username con cui si intende eseguire la login');
		return false;
	}
	return true;
}

function callbackfoo(data){

/* riempire i campi CF e token, quindi eseguire submit */
//	alert("spid-ret: " + JSON.stringify(data));
	var cf_spid = document.getElementById('cf_spid');
	var tk_spid = document.getElementById('tk_spid');
	tk_spid.value=data.token;
	cf_spid.value=data.fiscalCode;

	console.log('token: ' + tk_spid.value);

//alert(tk_spid.value);
	document.forms[0].submit();
}

</script>



<h1 style="text-align:center;background-color:#eeeeee;">
G.I.S.A. - Sistema Centralizzato degli Accessi
</h1>

<%@ include file="avviso_messaggio_urgente.jsp" %>


<div id="content" align="center">
<center>      
 	<div align="center">
	  	<form id="f1" action="login.Login.us" method="post">					
			<b>Username  </b> <br>
				<input type="text" name="utente" id='usr' required /> <br>					
			<b>Password  </b> <br>
				<input type="password" name="password" required /> <br>
			<br>	
			<input type="hidden" name="action" value="login"/>
			<input type="submit" value="Entra">
			<br/><a href="#" style="color: inherit;" onClick="openPopupCambioPassword()">Cambia Password</a>
<%
if(spidAttivato)
{
%>
			<br><br><a href="/login" style="color: inherit;" ><h3>Accedi con SPID/CIE</h3></a>
			<%
}
%>
		</form>	
<br>
<!-- 		<button title="Usare l'accesso diretto in caso di problemi con l'accesso centralizzato" class='btn' onclick='gotoG()'>Gisa direct</button> -->
	<!-- 	<button  title="Usare l'accesso diretto in caso di problemi con l'accesso centralizzato" class='btn' onclick='gotoB()'>Bdu direct</button>
		<button  title="Usare l'accesso diretto in caso di problemi con l'accesso centralizzato" class='btn' onclick='gotoV()'>Vam direct</button>
	--></div>
</center>
</div>

