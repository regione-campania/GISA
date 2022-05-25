<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<script language="JavaScript" type="text/javascript" src="js/vam/helpDesk/edit.js"></script>

<form action="vam.helpDesk.Edit.us" name="form" method="post" class="marginezero" onsubmit="javascript:return checkform(this);">               
   
   <input type="hidden" name="idTicket" value="${t.id }"/>
   
   <h4 class="titolopagina">
    		Dettaglio segnalazione numero ${t.id}
   </h4>
   <table class="tabella">
   	   
   	<tr>
   		<th colspan="2">
   			Richiesta effettuata da ${t.enteredBy.nome} ${t.enteredBy.cognome} 
   			in data <fmt:formatDate type="date" value="${t.entered}" pattern="dd/MM/yyyy" var="dataRichiesta"/>
   			 <c:out value="${dataRichiesta}"/>
   		</th>
   	</tr>
   	     
       
       <tr class='even'>
   		<td>
   			Tipologia di segnalazione
   		</td>
   		<td>
   			 ${t.lookupTickets.description}
   		</td>
       </tr>
       
        <tr class='odd'>
   		<td>
   			Descrizione
   		</td>
   		<td>
   			${t.description }
   		</td>
       </tr>
       
        <tr class='even'>
   		<td>
   			Indirizzo e-mail di risposta
   		</td>
   		<td>
   			 ${t.email}
   		</td>
       </tr>
             
              
       <tr class='odd'>
   		<td>
   			Descrizione di chiusura<font color="red"> *</font>
   		</td>
   		<td>   			 
	    	<textarea name="closureDescription" rows=7 cols=63></textarea>
   		</td>
       </tr>       
	
       <tr class='even'>
       	<td>
       		<font color="red">* </font> Campi obbligatori
       	</td>
   		<td>    			
   			<input type="submit" value="Chiudi segnalazione" />
    		<input type="button" value="Annulla" onclick="attendere(), location.href='Home.us'">
   		</td>
       </tr>
	</table>
</form>
