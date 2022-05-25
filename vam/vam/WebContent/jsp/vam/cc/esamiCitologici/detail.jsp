<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>


<jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
<h4 class="titolopagina">
     		Dettaglio Esame Citologico
</h4>		 

<br/>

<table class="tabella">
    	
        <tr>
    		<th colspan="2">
    			Esame Citologico del <fmt:formatDate type="date" value="${esame.dataRichiesta}" pattern="dd/MM/yyyy" />
    		</th>
    	</tr>

		<tr class="odd">
    		<td style="width:30%">
    			 Data esito
    		</td>
    		<td style="width:70%">  
    			<fmt:formatDate type="date" value="${esame.dataEsito}" pattern="dd/MM/yyyy" var="dataEsito"/>
    			 <c:out value="${dataEsito}"/>

    		</td>
        </tr>
        
		<tr>
    		<td style="width:30%">
    			 Tipo Prelievo
    		</td>
    		<td style="width:70%">  
    			${esame.tipoPrelievo }<c:if test="${esame.tipoPrelievoAltro!=null && esame.tipoPrelievoAltro!=''}">: ${esame.tipoPrelievoAltro }</c:if>
    		</td>
        </tr>
        
        <tr class="odd">
    		<td style="width:30%">
    			 Aspetto della lesione
    		</td>
    		<td style="width:70%">  
    			${esame.aspettoLesione }
    		</td>
        </tr>
        
        <tr>
    		<td style="width:30%">
    			 Diagnosi
    		</td>
    		<td style="width:70%">  
    			${esame.diagnosi}
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
				<td>${esame.enteredBy} il <fmt:formatDate value="${esame.entered}" pattern="dd/MM/yyyy"/></td>
			</tr>
			<c:if test="${esame.modifiedBy!=null}">
			<tr class="even">
				<td>Modificato da</td><td>${esame.modifiedBy} il <fmt:formatDate value="${esame.modified}" pattern="dd/MM/yyyy"/></td>
			</tr>
			</c:if>
		</table>

    			<input type="button" value="Modifica" onclick="if(${cc.dataChiusura!=null}){ 
    				if(myConfirm('Cartella Clinica chiusa. Vuoi procedere?')){location.href='vam.cc.esamiCitologici.ToEdit.us?idEsame=${esame.id }'}
    				}else{location.href='vam.cc.esamiCitologici.ToEdit.us?idEsame=${esame.id }'}">
	    		<input type="button" value="Lista Esami Citologici" onclick="location.href='vam.cc.esamiCitologici.List.us'">

