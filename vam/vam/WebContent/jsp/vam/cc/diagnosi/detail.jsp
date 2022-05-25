<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<jsp:include page="/jsp/vam/cc/menuCC.jsp?riepilogoCc=ancheDatiRicovero"/>
<h4 class="titolopagina">
	Dettaglio Diagnosi
</h4>
    	
<table class="tabella">
 <%--    	<tr>
    		<th colspan="3">
    			Diagnosi effettuata dal Dott. ${d.enteredBy.nome} ${d.enteredBy.cognome} 
    			in data <fmt:formatDate type="date" value="${d.dataDiagnosi}" pattern="dd/MM/yyyy" var="dataDiagnosi"/>
    			 <c:out value="${dataDiagnosi}"/>
    		</th>
    	</tr> --%>
    	 
        
        <tr class="even">
    		<td>
    			Lista delle diagnosi rilevate
    		</td>
    		<td>
    		
 					
			<c:forEach items="${deList}" var="deList" >							
				${deList.listaDiagnosi}	
				
				
				<c:choose>
					<c:when test="${ deList.provata == true}">																	
						<c:out value=" -- (Provata)"> (Provata) </c:out>
					</c:when>	
					<c:otherwise>																	
						<c:out value=" -- (Sospetta)"> (Sospetta) </c:out>
					</c:otherwise>						
				</c:choose>
				<br>	
				<br>
				
																	
			</c:forEach>
			
			<c:if test="${d.diagnosiChirurgiche!=''}">
				Diagnosi Chirurgiche: ${d.diagnosiChirurgiche}
			</c:if>
    		</td>
        </tr>
        
         <tr class="odd">
    		<td>
    			Note
    		</td>
    		<td>
    			<c:out value="${d.note}"/>    			 
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
				<td>${d.enteredBy} il <fmt:formatDate value="${d.entered}" pattern="dd/MM/yyyy"/></td>
			</tr>
			<c:if test="${d.modifiedBy!=null}">
			<tr class="even">
				<td>Modificato da</td><td>${d.modifiedBy} il <fmt:formatDate value="${d.modified}" pattern="dd/MM/yyyy"/></td>
			</tr>
			</c:if>
		</table>
			<input type="button" value="Modifica diagnosi" onclick="if(${cc.dataChiusura!=null}){ 
    				if(myConfirm('Cartella Clinica chiusa. Vuoi procedere?')){attendere(), location.href='vam.cc.diagnosi.ToEdit.us?idDiagnosi=${d.id}'}
    				}else{attendere(), location.href='vam.cc.diagnosi.ToEdit.us?idDiagnosi=${d.id}'}">
        
    	
