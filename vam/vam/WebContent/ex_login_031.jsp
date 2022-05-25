<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.util.properties.Application"%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>



<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="js/geolocation/geolocation.js"></script>
	
	
	
	
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
    		 attendere();
    	}else{
    		if(confirm('Attenzione! Stai utilizzando una versione diversa o non aggiornata di Firefox che potrebbe generare problemi nell\'utilizzo del sistema.\nSei proprio sicuro di voler proseguire con l\' accesso in GISA?')){
    			attendere();
    		}
    	}
    
    } 
    
    }
 </script>

<div id="header_<%=config.getServletContext().getAttribute("ambiente")%>"></div>
<br>
<div id="content">
  <table width="888">
    <tr>
      <td width="225">
      
	  <h1>Login</h1>
		  	<div align="center">
			  	<form name="login" action="login.Login.us" method="post">					
					<b>Username  </b> <br>
						<input type="text" name="utente"/> <br>						
					<b>Password  </b> <br>
						<input type="password" name="password"/> <br>
					<br>		
					<input type="hidden" name="action" value="login"/>
					<input type="submit" onclick="checkBrowser()" value="Entra">
					
					<!-- INIZIO Gestione coordinate per localizzazione utente -->
					<input type="hidden" name="access_position_lat">
             		<input type="hidden" name="access_position_lon">
             		<input type="hidden" name="access_position_err">
             		
             		
             		
             		<script language="JavaScript">
  						setPositionField(document.login.access_position_lat,document.login.access_position_lon,document.login.access_position_err);
					</script>

					<!-- FINE   Gestione coordinate per localizzazione utente -->
					<us:err classe="errore" />
					<us:mex classe="messaggio" />
					
				</form>	
			</div>
      </td>	
      <td width="651" valign="top">
      <div id="content_right">
        <table width="633">
          <tr>
            <td><h3>VAM</h3></td>
          </tr>
        </table>
      </div>
      </td>
    </tr>
  </table>
</div>