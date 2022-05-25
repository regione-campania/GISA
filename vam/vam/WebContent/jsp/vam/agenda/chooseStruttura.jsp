<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<script language="JavaScript" type="text/javascript" src="js/vam/agenda/chooseStruttura.js"></script>

<form action="vam.agenda.ToDetail.us" name="form" method="post" class="marginezero" onsubmit="javascript:return checkform(this);">
          
    <h4 class="titolopagina">
     		Scelta della struttura da prenotare
    </h4>
   
    <table class="tabella">
    	        
    	<tr class='odd'>
    		<td>
    			Struttura
    		</td>
    		<td>
    			<select name="idStrutturaClinica">
					<option value="-1">&lt;--- Selezionare la struttura ---&gt;</option>
    				<c:forEach var="s" items="${sp}">
            			<option value="<c:out value="${s.id}"/>">
            				${s.denominazione}
    					</option>
            		</c:forEach>
    			</select>
    		</td>
    		
        </tr>   
       		
        <tr class='even'>
        	<td>
        	</td>
    		<td>    			
    			<input type="submit" value="Dettaglio"/>    			
    		</td>
        </tr>
	</table>
</form>