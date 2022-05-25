<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<jsp:useBean id="messaggioAttuale" class="java.lang.String" scope="request"/>

<table class="trails" cellspacing="0">
<tr>
<td>
<a href="MyCFS.do?command=Home"><dhv:label name="My Home Page" mainMenuItem="true">La Mia Home Page</dhv:label></a> >
Messaggio Home Page
</td>
</tr>
</table>

<form name="messageForm" action="MessaggioHomePage.do?command=Messaggio" method="post">

<table cellpadding="2" cellspacing="2" border="0" width="100%">
	<tr>
	    <td width="50%" valign="top">
	    	<table cellpadding="4" cellspacing="0" border="0" width="50%" class="details">
	        	<tr>
	          		<th colspan="2"><strong>Messaggio Home Page</dhv:label></strong></th>
	        	</tr>
		       
		        <tr>
	          		<td nowrap class="formLabel">Messaggio</td>
	          		<td><textarea cols="150" rows="4" name="messaggio" id="messaggio"><%=messaggioAttuale %></textarea></td>
	        	</tr>
		        
				
		        
			</table>
			<input type="submit" value="Salva"></input><br/>
			<p style="color: red;"><%= request.getAttribute("mess") != null ? request.getAttribute("mess") : ""%></p>
		</td>
	</tr>
</table>


<%
   Date date = new Date();
   Calendar cal = Calendar.getInstance();
   cal.setTime(date);
   int year = cal.get(Calendar.YEAR);
   int month = cal.get(Calendar.MONTH)+1;
   int day = cal.get(Calendar.DAY_OF_MONTH);
   int ora = cal.get(Calendar.HOUR_OF_DAY);
   int minuti = cal.get(Calendar.MINUTE);
   
   String oraString = String.valueOf(ora);
   if (oraString.length() == 1)
	   oraString = "0"+ora;
   String minutiString = String.valueOf(minuti);
   if (minutiString.length() == 1)
	   minutiString = "0"+minuti;
%>

<script>
function aggiungiMessaggioRiavvio(){
	var data = document.getElementById("data").value;
	var ora = document.getElementById("ora").value;
	var msg = document.getElementById("messaggioRiavvio").value;
	msg = msg.replace("#DATA#", data);
	msg = msg.replace("#ORA#", ora);
	var messaggio = document.getElementById("messaggio").value;
	document.getElementById("messaggio").value = msg+messaggio;
	
}

function rimuoviMessaggioRiavvio(){

	var messaggio = document.getElementById("messaggio").value;
	var rx = new RegExp("#######[\\d\\D]*?\#######</br>", "g");
	messaggio = messaggio.replace(rx, "");
	var messaggioNuovo = messaggio.trim();
	document.getElementById("messaggio").value = messaggioNuovo;
}


</script>

<br/><br/>

<table class="details">
<tr><th>Gestione messaggio riavvio</th></tr>
<tr><td colspan="4"> <textarea id="messaggioRiavvio" cols="150" rows="4">
#######<br/>
AVVISO RIAVVIO <br/> Si avvisa che #DATA# alle ore #ORA# il sistema subira' un'interruzione per un intervento di manutenzione programmata. <br/>
 Il sistema tornera' disponibile dopo pochi minuti.<br/> 
 Ci scusiamo per il disagio.<br/>
 #######</br>
</textarea>
</td></tr>
<tr>
<td><b>Data</b> <input type="text" id="data" name="data" size="15" value="oggi <%= day + "/" +  month + "/" + year%>"/>
<b>Ora</b><input type="time" id="ora" name="ora" size="5" value="<%=oraString +":"+minutiString%>"/>
<input type="button" value="AGGIUNGI MESSAGGIO DI RIAVVIO" onClick="aggiungiMessaggioRiavvio()"/>
<input type="button" value="RIMUOVI MESSAGGIO DI RIAVVIO" onClick="rimuoviMessaggioRiavvio()"/></td></tr>
</table>

</form>