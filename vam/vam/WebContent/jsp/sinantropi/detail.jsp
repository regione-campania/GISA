<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>

<h4 class="titolopagina">
  Dettaglio Sinantropo numero ${s.numeroAutomatico }
</h4>
<c:if test="${interactiveMode != 'Y'}">
    		<jsp:include page="/jsp/sinantropi/menuSin.jsp"/>   		 
			<script type="text/javascript">
					ddtabmenu.definemenu("ddtabs2",0);
			</script> 
</c:if>

<table class="tabella">
    	    	
    	         
    	  	
    	       
        <tr>
        	<th colspan="3">
        		Dati anagrafici animale
        	</th>        	
        </tr>
                
        <tr class='even'>
    		<td>
    			Identificativo Animale in BDR
    		</td>
    		<td>
    			 <c:out value="${s.numeroAutomatico}"/>
    		</td>
    		<td>
    		</td>
        </tr>
        
        <c:if test="${s.numeroUfficiale!=''}">
        	<tr class='odd'>
    			<td>
    				Numero Ufficiale Istituto Faunistico
    			</td>
    			<td>
    				 <c:out value="${s.numeroUfficiale}"/>
    			</td>
    			<td>
    			</td>
        	</tr>
        </c:if>
        
        <c:if test="${s.mc!=''}">
        	<tr class='odd'>
    			<td>
    				Microchip
    			</td>
    			<td>
    				 <c:out value="${s.mc}"/>
    			</td>
    			<td>
    			</td>
        	</tr>
        </c:if>
        
         <c:if test="${s.codiceIspra!=''}">
        	<tr class='odd'>
    			<td>
    				Codice ISPRA
    			</td>
    			<td>
    				 ${s.codiceIspra}
    			</td>
    			<td>
    			</td>
        	</tr>
        </c:if>
        
        <tr class='even'>
    		<td>
    			Famiglia/Genere
    		</td>
    		<td>
    			 <c:out value="${s.lookupSpecieSinantropi.description}"/>
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Et&agrave;
    		</td>
    		<td>
    			<fmt:formatDate type="date" value="${s.dataNascitaPresunta}" pattern="dd/MM/yyyy" var="dataNascita"/>
  				${s.eta.description} <c:if test="${dataNascita}">(${dataNascita})</c:if>
    		</td>
    		<td>				
    		</td>
        </tr>
        
         <!--  tr class='even'>
    		<td>
    			Specie
    		</td>
    		<td>    
				<c:out value="${razza}"/>
    		</td>
    		<td>
    		</td>
        </tr-->
        
        <tr class='even'>
    		<td>
    			Sesso
    		</td>
    		<td>
				<c:choose>
	    			<c:when test="${s.sesso == 'M'}">
						<c:out value="Maschio"/>
					</c:when>
					<c:when test="${s.sesso == 'F'}">
						<c:out value="Femmina"/>
					</c:when>
					<c:otherwise>
						<c:out value="Non definito"/>
					</c:otherwise>	
				</c:choose>	
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='odd'>
	        <td>
	    		Note
    		</td>
    		<td>    		 
	        	 <c:out value="${s.note}"/>	       
	        </td>
        </tr>
      
          	
   
		
        <tr class='even'>
        	<td align="center">
        		<c:if test="${interactiveMode != 'Y'}">
		    		<input type="button" value="Modifica Scheda" onclick="attendere(), location.href='sinantropi.ToEdit.us?idSinantropo=${s.id}'">
        		</c:if>   
        	</td>
    		<td>    			
	    	</td>
    		<td>
        	</td>
        	<td>
        	</td>
        </tr>
	</table>