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

<div id="navigation">
    <ul>
    <li><a href="#">Home</a></li>    
    <li class="last"><a href="IndexContatti.us">Contatti</a></li>
    </ul>
</div>
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
					<font color="red">
						</br>
						<b><jsp:include page="../messaggi/messaggio.txt" /></b>
					</font>
				</form>	
			</div>
	  	<br>
		<br>		
		
		<!-- Nascosta fin quando non sarà ristrutturata la maschera di inserimento per gli LP -->
		<!-- Sono stati inseriti anche 20 <br> più in basso da elimianre una volta che sarà ripristinato l'accesso -->
       <!--  h1>Richiesta Istopatologico</h1-->
	 		<!--  div align="center">
			  	<form action="vam.richiesteIstopatologici.FindAnimale.us" method="post">			
				<input type="hidden" name="liberoProfessionista" value="on" />
				<b>Identificativo animale</b> 
				<br>				 		
					<input type="text" name="identificativo" maxlength="15" /> 				  
				<br>  
				<br> 				
					<input type="submit" onclick="attendere()" value="Cerca"/>  					    			
				</form> 
				
				<div>
					<p> Sezione abilitata per i Liberi Professionisti</p>
				</div>	  
									
	        </div-->
       <!-- br-->
	   <!-- br-->	
	    <h1>Accesso BDR</h1>
	 			<div class="news"><h2><a href="${BDU_PORTALE_URL}" accesskey="5" target="_new"><span>Cani/Gatti</span></a></h2>
				</div>	
			<div class="news"><h2><a href="indexS.jsp" accesskey="1"><span>Sinantropi/Marini/Zoo</span></a></h2>
			</div>	  								
		    <br> 
		    <br>
		    <br>
		    <br>
		    <br>
		    <br>
		    <br>
		    <br>
		    <br>
		    <br>
		    <br>
		    <br>
		    <br>
		    <br>
		    <br>
		    <br>
		    <br>
		    <br>
		    <br>
		    <br>
		    <br>
		        					 
	  <!--h1>Note di Rilascio</h1>
	 		<div class="news"><h2>27/10/2011</h2>
				<p> La versione 5.0 è on-line.				
				</p>
			</div>	  								
		    <br-->    
      </td>	
      <td width="651" valign="top">
      <div id="content_right">
        <table width="633">
          <tr>
            <td><h3>VAM</h3>
            
              <div id="content_row1">
                <p>
	                <b>VAM</b> (Veterinary Activity Management) è un sistema per la gestione delle principali funzionalità 
	                relative ad ospedali ed ambulatori veterinari pubblici della regione campania. 
	                In particolare il sistema permette ad una struttura di gestire:
					<br>
					<font color="red"><b>- L'accettazione e la presenza degli animali presso le strutture pubbliche</b></font>
					<br>
					<font color="red"><b>- Il percorso clinico e chirurgico di ogni animale (cane, gatto e sinantropo) dall'accettazione, alla cartella clinica, fino alle dimissioni;</b></font>
					<br>
					<font color="red"><b>- Le attivita' della struttura stessa (agenda, magazzino, etc.).</b></font>
					<br>
                
                </div>
                
                <br> 
                
                <h3>Avviso</h3>
                
                <div id="content_row2">
                  <p>Per segnalare eventuali blocchi del sistema durante le ore non lavorative si prega di inviare una email alla casella <b><%=Application.get("MAIL_SENDER_ADDRESS")%></b> 
				     avente oggetto 'SISTEMA DOWN'.<br> <br> 
                  </p>
                </div> 
                
                <h3> Le funzionalità del sistema</h3>
                
                <div id="content_row2">
                  <p>Le funzionalità messe a disposizione dal sistema VAM sono le seguenti: 
                  <br>
                  - Trasferimenti fra le strutture; <br>
                  - Gestione Accettazione; <br>
                  - Gestione cartella clinica;<br>                                    
                  - Gestione agenda, magazzino e personale;<br>                 
                  - Gestione delle attivita' esterne.<br>                  
                  </p>
                </div>               
                
                <div id="content_row3">
                 					
                </div>
                
                </td>
          </tr>
        </table>
      </div>
      </td>
    </tr>
  </table>
</div><br>
<div id="fotter">

  <div id="fotter_navigation">
          <ul>
          <li><a href="#">Home</a></li>          
          <li><a href="IndexContatti.us">Contatti</a></li>
           <li style="padding-left:10px;"><div style="float:left;">Sviluppato e manutenuto da:</div>
          <div id="image" style="float:left; border: 1px solid black; margin-top: -10px; height: 37px; background-color: white;">
		  <img src="images/izsm.png"  style="border: 1px solid black;" />
</div></li>
          </ul>
          </div>
  <div id="fotter_copyright">

</div>
</div>
<div id="fotter2"><p><span class="style_03">VAM - Veterinary Activity Management

</div>

