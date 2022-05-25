<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<script language="JavaScript" type="text/javascript" src="js/vam/helpDesk/add.js"></script>


<form action="vam.helpDesk.Add.us" name="form" method="post" class="marginezero" onsubmit="javascript:return checkform(this);">
               
    <h4 class="titolopagina">
     		Nuova segnalazione
    </h4>
    <table class="tabella">
    	   
    	<tr>
    		<th colspan="2">
    			Richiesta effettuata da ${utente.nome} ${utente.cognome} 
    			in data <fmt:formatDate type="date" value="${dataRichiesta}" pattern="dd/MM/yyyy" var="dataRichiesta"/>
    			 <c:out value="${dataRichiesta}"/>
    		</th>
    	</tr>
    	     
        
        <tr class='even'>
    		<td>
    			Tipologia di segnalazione<font color="red"> *</font>
    		</td>
    		<td>
    			 <select style="width:98%" name="tipologiaTicket" id="tipologiaTicket"">
						<option value="0">&lt;----------&gt;</option>	 						
			        	 <c:forEach items="${tipologieTickets}" var="tt" >	
			        	 	<option value="${tt.id }">${tt.description }</option>	        	 				
						</c:forEach>
		     
    			</select>
    		</td>
        </tr>
        
         <tr class='odd'>
    		<td>
    			Descrizione<font color="red"> *</font>
    		</td>
    		<td>    			
	    		<textarea name="description" rows=7 cols=63></textarea>
    		</td>
        </tr>
        
         <tr class='even'>
    		<td>
    			Indirizzo e-mail (in caso si desideri ricevere una risposta) <font color="red"> *</font>
    		</td>
    		<td>
    			 <input type="text" name="email" maxlength="250" size="35"/>
    		</td>
        </tr>
                 
		
        <tr class='odd'>
        	<td>
        		<font color="red">* </font> Campi obbligatori
        	</td>
    		<td>    			
    			<input type="submit" value="Aggiungi" />
    			<input type="button" value="Annulla" onclick="attendere(), location.href='Home.us'">
    			
    		</td>
        </tr>
	</table>
</form>