<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<form name="form" method="post" class="marginezero" >
           
    
    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
    <h4 class="titolopagina">
     		Diagnostica per immagini
    </h4>
	
    <table class="tabella">
    	<tr>
        	<th colspan="2">
        		Scelta tipo esame
        	</th>
        </tr>              
        
    	<tr class='odd'>
    		<td>
    			Tipo
    		</td>
    		<td>
    			<select id="diagnosticaImmagini">
					<option value="">&lt;--- Selezionare il tipo ---&gt;</option>
					<option value="ecoAddome.List.us">Eco-Addome</option>
					<option value="ecoCuore.List.us">Eco-Cuore</option>
					<option value="tac.List.us">TAC</option>
					<option value="rx.List.us">RX</option>
    			</select>
    		</td>
        </tr>
        	
        <tr class='even'>
        	<td>
        	</td>
    		<td>    			
    			<input type="button" value="Prosegui" onclick="if(document.getElementById('diagnosticaImmagini').value==''){alert('Selezionare un tipo');}else{location.href='vam.cc.diagnosticaImmagini.'+document.getElementById('diagnosticaImmagini').value;}"/>
    		</td>
        </tr>
	</table>
</form>