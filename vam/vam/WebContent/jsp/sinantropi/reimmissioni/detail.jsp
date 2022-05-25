<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>

<h4 class="titolopagina">
     Dettaglio Rilascio 
</h4>
    	
    	<jsp:include page="/jsp/sinantropi/menuCatture.jsp"/>   		 
		<script type="text/javascript">
			ddtabmenu.definemenu("ddtabs2",3);	
		</script>
		  
 <table class="tabella">   	       
        <tr>
        	<th colspan="3">
        		Dettagli
        	</th>        	
        </tr>
        
         <tr class='odd'>
    		<td>
    			Data
    		</td>
    		<td>
				<fmt:formatDate type="date" value="${c.reimmissioni.dataReimmissione}" pattern="dd/MM/yyyy" var="dataReimmissione"/>
  				<c:out value="${dataReimmissione}"/>
    		</td>
    		<td>				
    		</td>
        </tr>
           
        
        <tr class='even'>
    		<td>
    			Provincia e Comune
    		</td>
    		<td>				
  				<c:choose>			
					<c:when test="${c.reimmissioni.comuneReimmissione.bn == true}">
						<c:out value="Benevento"></c:out>	
					</c:when>
					<c:when test="${c.reimmissioni.comuneReimmissione.na == true}">
						<c:out value="Napoli"></c:out>	
					</c:when>
					<c:when test="${c.reimmissioni.comuneReimmissione.sa == true}">
						<c:out value="Salerno"></c:out>	
					</c:when>
					<c:when test="${c.reimmissioni.comuneReimmissione.av == true}">
						<c:out value="Avellino"></c:out>	
					</c:when>
					<c:when test="${c.reimmissioni.comuneReimmissione.ce == true}">
						<c:out value="Caserta"></c:out>	
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>
    		</td>
    		<td>
    			<c:out value="${c.reimmissioni.comuneReimmissione.description}"/>				
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Luogo
    		</td>
    		<td>				
  				<c:out value="${c.reimmissioni.luogoReimmissione}"/>
    		</td>
    		<td>				
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Codice ISPRA
    		</td>
    		<td>				
  				${c.reimmissioni.codiceIspra}
    		</td>
    		<td>				
    		</td>
        </tr>
               
        
		
        <tr class='odd'>
        	<td>
        	</td>
    		<td>    			
    			<input type="button" value="Modifica Scheda" onclick="attendere(), location.href='sinantropi.reimmissioni.ToEdit.us?idCattura=${c.id}'">
	    	</td>
    		<td>
        	</td>        	
        </tr>
	</table>