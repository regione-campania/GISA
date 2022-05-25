<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>


               
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
   			Descrizione di apertuta
   		</td>
   		<td>
   			${t.description }
   		</td>
       </tr>
       
        <tr class='even'>
   		<td>
   			Indirizzo e-mail (in caso si desideri ricevere una risposta)
   		</td>
   		<td>
   			 ${t.email}
   		</td>
       </tr>
       
       <tr class='odd'>
   		<td>
   			Descrizione di chiusura
   		</td>
   		<td>
   			${t.closureDescription }
   		</td>
       </tr>
                
	
       <tr class='even'>
       	<td>
       	</td>
   		<td>  
   			<us:can f="HD" sf="EDIT" og="MAIN" r="w" >	  			
   				<c:if test="${t.closureDescription == null}">
            		<input type="button" value="Gestisci segnalazione" onclick="attendere(), location.href='vam.helpDesk.ToEdit.us?idTicket=${t.id}'">
    			</c:if>   
    		</us:can>			
    		<input type="button" value="Lista segnalazioni" onclick="attendere(), location.href='vam.helpDesk.List.us'">
    		
   		</td>
       </tr>
</table>
