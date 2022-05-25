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
<h2>Associa codice preaccettazione a campione</h2>
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
   <br>
	step 1: andare sull'OSA fittizio "test diagnostica..." e creare un campione di cui prendere l'id
	(n.b. prendere id campione e non id cu, non utilizzare lo stesso campione per codici di preaccettazione diversi)
	<br>
	step 2: nel dettaglio dell'OSA fittizio "test diagnostica..." prendere un codice preaccettazione generato 
	ma non ancora associato ad un campione 
	(tasto "genera preaccettazione sigla" -> "visualizza lista codici preaccettazione utilizzabili")
	</font>   			
	<br><br>
	
   <center>
   <table>
   		<tr>
   			<td>UserId</td>
   			<td>   
   				<input id="userId" type="text" placeholder="userId" value="<%=User.getUserId() %>" readonly> &nbsp; (utente <%=User.getUsername() %>)
   			</td>
   		</tr>
   		<tr>
   			<td>id_campione</td>
   			<td>
   				<input id="id_campione" type="text" placeholder="id_campione" value="" >
   			</td> 
   		</tr>
   		<tr>
   			<td>codice_preaccettazione</td>
   			<td>
   				<input id="codice_preaccettazione" type="text" placeholder="codice_preaccettazione" value="" >
   			 </td>
   		</tr>
   </table>
   <br>
   <font color='red' size="1px"></font>   
   
   </center>
   
   <button class="buttonLogin" onclick="associa_codice_a_cmp()">associa codice preaccettazione a campione</button>
  </div>

</div></center>

<center>

	<br><br>associa codice preaccettazione a campione: servizio chiamato e input<br>
	<textarea id="req1" rows=6 cols="80"></textarea><br><br>

	associa codice preaccettazione a campione: output chiamata servizio<br>
	<textarea id="resp1" rows=6 cols="80"></textarea><br><br><br>

</center>



<div ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></div>
</body>

<script type="text/javascript">

function associa_codice_a_cmp(){
	
	var id_campione = document.getElementById('id_campione').value.trim();
	var userId = document.getElementById('userId').value.trim();
	var codice_preaccettazione = document.getElementById('codice_preaccettazione').value.trim();
	
	var datiDainviare = { 
				"id_campione": id_campione,
				"userId": userId,
				"codice_preaccettazione": codice_preaccettazione 
		      };
	var url_services = document.getElementById('host_name_e_porta').value.trim() + 
						"/preaccettazione/All/Prenotazioni/Associacampione";
	
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

