<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<form action="testing.AddError.us" name="form" method="post" class="marginezero" onsubmit="javascript:return checkform(this);">
           
    <h4 class="titolopagina">
     		Dettagli dell'errore
    </h4>
   
    <table class="tabella">
    	
    	<tr class='odd'>
    		<td width="20%">
    			Descrizione errore
    		</td>
    		<td>
    			<TEXTAREA NAME="ricoveroNote" COLS=60 ROWS=3><c:out value="${cc.ricoveroNote}"></c:out></TEXTAREA>   			
    		</td>
        </tr>
        
        <tr class='even'>
    		<td width="20%">
    			Eventuale eccezione
    		</td>
    		<td>
    			<TEXTAREA NAME="ricoveroNote" COLS=60 ROWS=10><c:out value="${cc.ricoveroNote}"></c:out></TEXTAREA>   			
    		</td>
        </tr>
        
        <tr class='odd'>
        	<td width="20%">
    			Screenshot
    		</td>
    		<td> 
    		<img src="testingImages/c.jpg" height="60%" width="60%"/>
    		</td>
    	</tr>
    	
		
        <tr class='even'>
        	<td>
        	</td>
    		<td>    			
    			<input type="submit" value="Segnala errore""/>    			
    		</td>
        </tr>
	</table>
</form>