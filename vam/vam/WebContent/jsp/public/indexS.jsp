<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.util.properties.Application"%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>


 <script>
 
 window.location.href='/login';

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
    		 attendere();
    	}else{
    		if(confirm('Attenzione! Stai utilizzando una versione diversa o non aggiornata di Firefox che potrebbe generare problemi nell\'utilizzo del sistema.\nSei proprio sicuro di voler proseguire con l\' accesso in GISA?')){
    			attendere();
    		}
    	}
    
    } 
    
    }
 </script>


<div id="wrapper">
	<div id="header">
		<h1><a href="#">Anagrafe Sinantropi/Marini/Zoo</a></h1>
			<img src="./images/sinantropi/homePage/regione.png" align="right"/>
	</div>
	<!-- end header -->
	<div id="menu">
		<h2>Main Menu</h2>
		<ul>
			<li class="active"><a href="#">Home</a></li>			
			<li><a href="IndexContatti.us">Contatti</a></li>
		</ul>
	</div>
	<!-- end menu -->
	<div id="page">
		<div id="content">		
				<br>
				<br>
				<br>
				<br>
				<br>
				<p>Il sistema SinAgr nasce dall'esigenza di anagrafare non solo Cani e Gatti attraverso le rispettive anagrafi, 
					bensi' tutti quegli animali che sono comunque a contatto con l'uomo, definiti Sinantropi.
					<br>
					<br>
					Il sistema, in particolare, permette di:<br>
					>Anagrafare un nuovo Sinantropo;<br>
					>Gestire le registrazioni come rinvenimento, detenzione, rilascio, decesso.	
					
				</p>	
				<br>
				<p><b>Avviso</b></br>
				   Per segnalare eventuali blocchi del sistema durante le ore non lavorative si prega di inviare una email alla casella <b><%=Application.get("MAIL_SENDER_ADDRESS")%></b> 
				   avente oggetto 'SISTEMA DOWN'.
				</p>	
		</div>
		<!-- end content -->
		<div id="sidebar">
			<ul>
				<li id="submenu">
					<h2>Login</h2>
					<ul>
						<form action="login.Login.us" method="post">					
							<b>Username  </b> <br> <input type="text" name="utente"/> <br>						
							<b>Password  </b> <br> <input type="password" name="password"/> <br>
							<br>
							<input type="hidden" name="action" value="login"/>
							<input type="submit" onclick="checkBrowser()" value="Entra" align="center">
							<us:err classe="errore" />
							<us:mex classe="messaggio" />
						</form>	
					</ul>
				</li>
				<!--  li id="news">
					<h2>Note di Rilascio</h2>
					<ul>
						<li>
							<h3>10 giugno 2011</h3>
							<p>La versione 1.0 del sistema SinAgr e' on-line</p>
						</li>						
					</ul>
				</li-->
				<li id="news">
					<h2>Accesso BDR</h2>
					<ul>
							<li>
								<h3><a href="${BDU_PORTALE_URL}" accesskey="5" target="_new"><span>Cani/Gatti</span></a></h3>
							</li>
						<li>
							<h3><a href="indexV.jsp?" accesskey="1"><span>V.A.M.</span></a></h3>
						</li>						
					</ul>
				</li>
			</ul>
		</div>
		<!-- end sidebar -->
		<div style="clear: both;">&nbsp;</div>
	</div>
	<!-- end page -->
	<div id="footer">
		<p id="legal" align="cente">SinAgr - Anagrafe Sinantropi/Marini/Zoo</p>		
	</div>
	<!-- end footer -->
</div>