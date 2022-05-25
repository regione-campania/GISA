<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.util.properties.Application"%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>

<div id="navigation">
    <ul>
    <li><a href="Index.us">Home</a></li>    
    <li class="last"><a href="IndexContatti.us">Contatti</a></li>
    </ul>
</div>
<div id="header"></div>
<br>
<div id="content">
  <table width="888">
    <tr>
      <td width="225">
      
	  <h1>Login</h1>
		  	<div align="center">
			  	<form action="login.Login.us" method="post">					
					<b>Username  </b> <br>
						<input type="text" name="utente"/> <br>						
					<b>Password  </b> <br>
						<input type="password" name="password"/> <br>
					<br>						
					<input type="hidden" name="action" value="login"/>
					<input type="submit" onclick="attendere()" value="Entra">
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
       <br>
	   <br>	
	    <h1>Accesso BDR</h1>
	 			<div class="news"><h2><a href="${BDU_PORTALE_URL}" accesskey="5" target="_new"><span>Cani/Gatti</span></a></h2>
				</div>	
			<div class="news"><h2><a href="indexS.jsp" accesskey="1"><span>Sinantropi/Marini/Zoo</span></a></h2>
			</div>	  								
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
                <p style="color:blue;">
	                Per assistenza  al software è possibile contattare l'Help Desk nei giorni feriali dalle ore 9 alle ore 13 ai seguenti numeri:<br/>
					Telefoni: <b><%=Application.get("TELEFONO_HD")%></b><br/>
					Per migliorare i processi legati all'assistenza si prega di utilizzare sempre l'invio di 'segnalazioni' usando il comando presente nella home page.<br/>
                	Per segnalare eventuali blocchi del sistema durante le ore non lavorative si prega di inviare una email alla casella <b><%=Application.get("MAIL_SENDER_ADDRESS")%></b>
					avente oggetto 'SISTEMA DOWN'.
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
          </ul>
          </div>
  <div id="fotter_copyright">

</div>
</div>
<div id="fotter2"><p><span class="style_03">VAM - Veterinary Activity Management

</div>

