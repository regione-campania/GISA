<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>

	<h4 class="titolopagina">
     		Dettaglio Detenzione del Sinantropo numero ${d.catture.sinantropo.numeroAutomatico }
    </h4>

    	
    
    	<jsp:include page="/jsp/sinantropi/menuCatture.jsp"/>
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
    			Data di inizio detenzione
    		</td>
    		<td>
				<fmt:formatDate type="date" value="${d.dataDetenzioneDa}" pattern="dd/MM/yyyy" var="dataDetenzioneDa"/>
  				<c:out value="${dataDetenzioneDa}"/>
    		</td>
    		<td>				
    		</td>
        </tr>
               
        <tr class='even'>
    		<td>
    			Data di fine detenzione
    		</td>
    		<td>
				<fmt:formatDate type="date" value="${d.dataDetenzioneA}" pattern="dd/MM/yyyy" var="dataDetenzioneA"/>
  				<c:out value="${dataDetenzioneA}"/>
    		</td>
    		<td>				
    		</td>
        </tr>
              
        <tr class='odd'>
    		<td>
    			Tipologia detentore
    		</td>
    		<td>				
  				<c:out value="${d.lookupDetentori.description}"/>
    		</td>
    		<td>				
    		</td>
        </tr>
               
        <c:if test="${d.lookupDetentori.id == 1}">
        
	        <tr class='even'>
	    		<td>
	    			Nome detentore
	    		</td>
	    		<td>				
	  				<c:out value="${d.detentorePrivatoNome}"/>
	    		</td>
	    		<td>				
	    		</td>
	        </tr>
	        
	        <tr class='odd'>
	    		<td>
	    			Cognome detentore
	    		</td>
	    		<td>				
	  				<c:out value="${d.detentorePrivatoCognome}"/>
	    		</td>
	    		<td>				
	    		</td>
	        </tr>
	        
	        <tr class='even'>
	    		<td>
	    			Codice Fiscale detentore
	    		</td>
	    		<td>				
	  				<c:out value="${d.detentorePrivatoCodiceFiscale}"/>
	    		</td>
	    		<td>				
	    		</td>
	        </tr>
	        
	        <tr class='odd'>
	    		<td>
	    			Tipologia e numero documento
	    		</td>
	    		<td>				
	  				<c:out value="${d.lookupTipologiaDocumento.description}"/>
	    		</td>
	    		<td>
	    			<c:out value="${d.detentorePrivatoNumeroDocumento}"/>	    						
	    		</td>
	        </tr>
	        
	        <tr class='even'>
	    		<td>
	    			E-mail detentore
	    		</td>
	    		<td>				
	  				<c:out value="${d.detentorePrivatoEmail}"/>
	    		</td>
	    		<td>				
	    		</td>
	        </tr>
	        
	        <tr class='odd'>
	    		<td>
	    			Telefono detentore
	    		</td>
	    		<td>				
	  				<c:out value="${d.detentorePrivatoTelefono}"/>
	    		</td>
	    		<td>				
	    		</td>
	        </tr>
	        
	        <tr class='even'>
	    		<td>
	    			Provincia e Comune di detenzione
	    		</td>
	    		<td>				
	  				<c:choose>			
						<c:when test="${d.comuneDetenzione.bn == true}">
							<c:out value="Benevento"></c:out>	
						</c:when>
						<c:when test="${d.comuneDetenzione.na == true}">
							<c:out value="Napoli"></c:out>	
						</c:when>
						<c:when test="${d.comuneDetenzione.sa == true}">
							<c:out value="Salerno"></c:out>	
						</c:when>
						<c:when test="${d.comuneDetenzione.av == true}">
							<c:out value="Avellino"></c:out>	
						</c:when>
						<c:when test="${d.comuneDetenzione.ce == true}">
							<c:out value="Caserta"></c:out>	
						</c:when>
						<c:otherwise>
						</c:otherwise>
					</c:choose>
	    		</td>
	    		<td>
	    			<c:out value="${d.comuneDetenzione.description}"/>				
	    		</td>
	        </tr>
	        
	        <tr class='odd'>
	    		<td>
	    			Luogo di detenzione
	    		</td>
	    		<td>				
	  				<c:out value="${d.luogoDetenzione}"/>
	    		</td>
	    		<td>				
	    		</td>
	        </tr>
                
        
        </c:if>
        
        
         <c:if test="${d.lookupDetentori.id == 18 || d.lookupDetentori.id == 19}">
        
	        <tr class='even'>
	    		<td>
	    			Note sulla tipologia
	    		</td>
	    		<td>				
	  				<c:out value="${d.detentorePrivatoNome}"/>
	    		</td>
	    		<td>				
	    		</td>
	        </tr>
	    </c:if>
		
        <tr class='even'>
        	<td>
        	</td>
    		<td>    			
    			<input type="button" value="Modifica Scheda" onclick="attendere(), location.href='sinantropi.detenzioni.ToEdit.us?idDetenzione=${d.id}'">
	    	</td>
    		<td>
        	</td>
        	<td>
        	</td>
        </tr>
	</table>