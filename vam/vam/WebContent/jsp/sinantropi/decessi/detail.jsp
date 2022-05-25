<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>

		<h4 class="titolopagina">
     		Decesso Sinantropo numero ${s.numeroAutomatico }
    	</h4>
    	
    	<jsp:include page="/jsp/sinantropi/menuSin.jsp"/>   		 		 
		<script type="text/javascript">
				ddtabmenu.definemenu("ddtabs2",2);		
		</script> 
		
<table class="tabella">
    	
        <tr>
        	<th colspan="3">
        		Dati generali
        	</th>        	
        </tr>
            
        <tr class='odd'>
    		<td>
    			Data decesso
    		</td>
    		<td>
				<fmt:formatDate type="date" value="${s.dataDecesso}" pattern="dd/MM/yyyy" var="dataDecesso"/>
  				<c:out value="${dataDecesso}"/>
    		</td>
    		<td>				
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Causa Decesso
    		</td>
    		<td>				
  				<c:out value="${s.lookupCMI.description}"/>
    		</td>
    		<td>				
    		</td>
        </tr>
        
        <tr class='odd'>
        	<td>
        	</td>
    		<td>    			
    			<input type="button" value="Modifica Scheda" onclick="attendere(), location.href='sinantropi.decessi.ToEdit.us?idSinantropo=${s.id}'">
	    	</td>
    		<td>
        	</td>
        	<td>
        	</td>
        </tr>
	</table>