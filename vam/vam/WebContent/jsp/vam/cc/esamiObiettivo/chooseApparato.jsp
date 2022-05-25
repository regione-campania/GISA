<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<form action="vam.cc.esamiObiettivo.ToDetailSpecifico.us" name="form" method="post" class="marginezero">
           
   
    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>

 	 <h4 class="titolopagina">
     		Scelta dell'apparato
    </h4>
    
    <table class="tabella">
    	<tr>
        	<th colspan="2">
        		Scegli apparato da analizzare
        	</th>
        </tr>              
        
    	<tr class='odd'>
    		<td>
    			Apparato
    		</td>
    		<td>
    			<select name="idApparato">
					<option value="">&lt;--- Selezionare l'apparato ---&gt;</option>
    				<c:forEach var="leoa" items="${leoa}">
            			<option value="<c:out value="${leoa.id}"/>">
            				${leoa.description}
    					</option>
            		</c:forEach>
    			</select>
    		</td>
        </tr>
        	
        <tr class='even'>
        	<td>
        	</td>
    		<td>    			
    			<input type="submit" value="Prosegui" onclick="attendere()"/>
    		</td>
        </tr>
	</table>
</form>