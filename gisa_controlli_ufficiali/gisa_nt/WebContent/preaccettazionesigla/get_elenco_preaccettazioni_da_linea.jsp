<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=10" />

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/jquery.min.js"></script>
<script src='javascript/modalWindow.js'></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<style>
body {font-family: Arial, Helvetica, sans-serif;
	  text-align: center;
}
.form {border: 5px solid #f1f1f1;
	  width: 90%;
	  text-align: center;
}



.buttonLogin {
	background-color: #87CEEB;
	color: black;
	padding: 15px 23px;
    margin: 8px 0;
    border: none;
    cursor: pointer;
    width: 30%;
    font-size: 18px;
}



.imgcontainer {
    text-align: center;
    margin: 24px 0 12px 0;
}

img.avatar {
    width: 25%;
    
}

.container {
    padding: 16px;
}

</style>

<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="ReportPreaccettazione.do?command=DiagnosticaPreaccettazione">HOME DIAGNOSTICA SERVIZI</a>
		</td>
	</tr>
</table>

<body>
<center>
<h2>lista codici preaccettazione da linea utilizzabili</h2>
</center>

<% if(org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("ambiente").equalsIgnoreCase("sviluppo")){%>
	<input type="hidden" id="host_name_e_porta" value="http://srvgisal" />
	
<% } else if(org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("ambiente").equalsIgnoreCase("demo")){  %>
	<input type="hidden" id="host_name_e_porta" value="http://col.gisacampania.it:8080" />
	
<% } else if(org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("ambiente").equalsIgnoreCase("ufficiale")){  %>
	<input type="hidden" id="host_name_e_porta" value="http://srv.gisacampania.it" />
<% } %>


<center><div class="form">

  <div class="container">
  <font color='red' size="2px">
   n.b. cliccando sul tasto "lista codici preaccettazione" verranno mostrati solo codici di preaccettazione
   associati alla linea dell'OSA ma non ancora associati ad alcun campione, una volta che un codice
   di preaccettazione viene associato al campione non appare piu in questa lista.
   </font>  
   <br><br>
   parametri linea attivita <br><br>
   
   <center>
   <table>
   		<tr>
   			<td>riferimento_id</td>
   			<td><input id="riferimento_id" type="text" placeholder="riferimento_id" value="346698" readonly></td>
   		</tr>
   		<tr>
   			<td>riferimento_id_nome</td>
   			<td><input id="riferimento_id_nome" type="text" placeholder="riferimento_id_nome" value="stabId" readonly></td>
   		</tr>
   		<tr>
   			<td>riferimento_id_nome_tab</td>
   			<td><input id="riferimento_id_nome_tab" type="text" placeholder="riferimento_id_nome_tab" value="opu_stabilimento" readonly></td>
   		</tr>
   		<tr>
   			<td>id_linea_materializzata</td>
   			<td><input id="id_linea_materializzata" type="text" placeholder="id_linea_materializzata" value="415476" readonly></td>
   		</tr>
   		<tr>
   			<td>tipologia_operatore</td>
   			<td><input id="tipologia_operatore" type="text" placeholder="tipologia_operatore" value="999" readonly><br/></td>
   		</tr>
   </table>
   <br>
   <font color='red' size="2px">
   (query per recuperare i parametri linea osa: 
   	select riferimento_id, 
   		   riferimento_id_nome, 
   		   riferimento_id_nome_tab, 
   		   id_linea, 
   		   tipologia_operatore 
   		from ricerche_anagrafiche_old_materializzata 
   		where riferimento_id = 346698)
   </font>   
   
   </center>
   
   <button class="buttonLogin" onclick="lista_codici_preaccettazione()">lista codici preaccettazione</button>
  </div>

</div></center>

<center>

	<br><br>lista il codici preaccettazione: servizio chiamato e input<br>
	<textarea id="req1" rows=6 cols="80"></textarea><br><br>

	lista il codici: output chiamata servizio<br>
	<textarea id="resp1" rows=6 cols="80"></textarea><br><br><br>

</center>



<div ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></div>
</body>

<script type="text/javascript">

function lista_codici_preaccettazione(){
	
	var identificativo_linea = document.getElementById('riferimento_id').value.trim() + 
							   document.getElementById('riferimento_id_nome').value.trim() + 
							   document.getElementById('riferimento_id_nome_tab').value.trim() + 
							   document.getElementById('id_linea_materializzata').value.trim() + 
							   document.getElementById('tipologia_operatore').value.trim();
	
	var datiDainviare = {"identificativo_linea": identificativo_linea};
	
	var url_services = document.getElementById('host_name_e_porta').value.trim() + 
						"/preaccettazione/All/Report/Getelencodalinea";
	
	document.getElementById("req1").value = "url servizio: " + url_services + " \n\ninput: " + JSON.stringify(datiDainviare, null, 4);
	
	loadModalWindow();
	 $.ajax({

       url: url_services,
       async: false,
       data: JSON.stringify(datiDainviare),					
	   type: 'POST',
       dataType: 'text',
       contentType: 'application/json',
       success: function(dati) {
       	var obj;
    	  	try {
    	  		obj = JSON.parse(dati);
    		} catch (e) {
    			console.log(e instanceof SyntaxError);
    			alert("Chiamata fallita SyntaxError!!!");
    			loadModalWindowUnlock();
    			return false;
    		}
 
    		document.getElementById("resp1").value = JSON.stringify(obj, null, 4); 
    		loadModalWindowUnlock();
         }
	 })
	
}


</script>

