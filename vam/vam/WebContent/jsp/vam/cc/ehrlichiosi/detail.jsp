<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<form action="" name="form" method="post" id="form" class="marginezero">

<table class="tabella">
    	
    	
    	<jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
    	<h4 class="titolopagina">
     		Dettaglio Ehrlichiosi
    	</h4> 
    	
    	<tr>
    		<th colspan="3">
    			Dettaglio 
    		</th>
    	</tr>    
                 
        
        <tr class='even'>
    		<td>
    			Data Richiesta:&nbsp;
    			 <fmt:formatDate type="date" value="${e.dataRichiesta}" pattern="dd/MM/yyyy" var="dataRichiesta"/>
    			 <c:out value="${dataRichiesta}"/>
    		</td>
    		<td>
    			Data Esito:&nbsp;
    			 <fmt:formatDate type="date" value="${e.dataEsito}" pattern="dd/MM/yyyy" var="dataEsito"/>
    			 <c:out value="${dataEsito}"/>
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Esito
    		</td>
    		<td>
    			 <c:out value="${e.lee.description}"/>
    		</td>
    		<td>
    		</td>
        </tr>
        	</table>
        <table class="tabella">
			<tr>
				<th colspan="3">
				       		Altre Informazioni
				 </th>
			 </tr> 	  
			<tr class="odd">
				<td>Inserito da</td>
				<td>${e.enteredBy} il <fmt:formatDate value="${e.entered}" pattern="dd/MM/yyyy"/></td>
			</tr>
			<c:if test="${e.modifiedBy!=null}">
			<tr class="even">
				<td>Modificato da</td><td>${e.modifiedBy} il <fmt:formatDate value="${e.modified}" pattern="dd/MM/yyyy"/></td>
			</tr>
			</c:if>
		</table>
        
  				<input type="button" value="Modifica" onclick="if(${cc.dataChiusura!=null}){ 
    				if(myConfirm('Cartella Clinica chiusa. Vuoi procedere?')){attendere(), location.href='vam.cc.ehrlichiosi.ToEdit.us?idEhrlichiosi=${e.id}'}
    				}else{attendere(), location.href='vam.cc.ehrlichiosi.ToEdit.us?idEhrlichiosi=${e.id}'}">				
    			<input type="button" value="Lista Ehrlichiosi" onclick="attendere(), location.href='vam.cc.ehrlichiosi.List.us'">				
    	

</form>