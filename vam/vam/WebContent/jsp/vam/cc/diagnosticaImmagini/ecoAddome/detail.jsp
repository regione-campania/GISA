<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date" %>
<%@ page import="it.us.web.bean.BUtente" %>
	
	<jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
	<h4 class="titolopagina">
	   	Dettaglio Eco-Addome
	</h4>
	
	<table class="tabella">
		<tr>
    		<td>
    			 Data Richiesta:&nbsp;
    			 <fmt:formatDate type="date" value="${ecoAddome.dataRichiesta}" pattern="dd/MM/yyyy" var="dataRichiesta"/> 
    			 ${dataRichiesta}  
    		</td>
    		<td>
    			 Data Esito:&nbsp;
    			 <fmt:formatDate type="date" value="${ecoAddome.dataEsito}" pattern="dd/MM/yyyy" var="dataEsito"/> 
    			 ${dataEsito}  
    		</td>
        </tr>   
	</table>
	
	<table class="tabella">
		
		<tr>
			<th colspan="3">
	       		Eco-Addome
	       	</th> 	       	     
		</tr>		
		
		<tr>
			<th>
	       		Tipo
	       	</th>
	       	<th>
	       		Esito
	       	</th>
	       	<th>
	       		Referti
	       	</th> 	       	     
		</tr>
	
	
		<c:set var="i" value='1'/>	
		<c:forEach items="${ecoAddomeTipi}" var="ecoAddomeTipo" >	
		
		<c:set var="esitoNormale" value="NO"></c:set>
		<c:set var="esitoAnormale" value="NO"></c:set>
		
		<%-- Cicli preventivi per controllare se il tipo per questo Eco-Addome ha esito normale, anormale o non esaminato --%>
		<c:forEach items="${ecoAddomeEsitos}" var="esito">
			<c:if test="${esito.tipo.id==ecoAddomeTipo.id && esito.normale==true}">
				<c:set var="esitoNormale" value="YES"></c:set>
			</c:if>
			<c:if test="${esito.tipo.id==ecoAddomeTipo.id && esito.normale==false}">
				<c:set var="esitoAnormale" value="YES"></c:set>
			</c:if>
			
		</c:forEach>
		
		
		<tr class="${i % 2 == 0 ? 'even' : 'odd'}">
		
			<td style="width:20%">				
				${ecoAddomeTipo.nome}   
			</td>
	    
			<td style="width:20%">
					<c:if test="${esitoNormale=='YES'}">
  		          		Normale
    				</c:if>
    				<c:if test="${esitoAnormale=='YES'}">
  		          		Anormale
    				</c:if>
    				<c:if test="${esitoNormale=='NO' && esitoAnormale=='NO'}">
  		          		Non esaminato 
    				</c:if>
			</td>			
			<td style="width:60%">
				<div id="_anormale${i }"  
					<c:if test="${esitoAnormale=='NO'}">
  		          		style="display:none;"
    				</c:if>
    				>	
					<c:forEach items="${ecoAddomeTipo.lookupEcoAddomeReferti}" var="referto" >
						<c:set var="checked" value="NO"/>
						<c:if test="${referto.tipo == 'A'}">
							<%-- Cicli preventivi per controllare se il referto è stato selezionato --%>
							<c:forEach items="${ecoAddomeEsitos}" var="esitoTemp">
								<c:forEach items="${esitoTemp.lookupEcoAddomeReferti}" var="refertoTemp">
								<c:if test="${refertoTemp.tipo=='A' && refertoTemp.id == referto.id}">
									<c:set var="checked" value="SI"></c:set>
								</c:if>
								</c:forEach>
							</c:forEach>
								<c:if test="${checked=='SI'}">
	  		          				${referto.nome}
	    						</c:if>
							<br>
						</c:if>				
					</c:forEach>
				</div>
				
				
				<div id="_normale${i }"   
					<c:if test="${esitoNormale=='NO'}">
  		          		style="display:none;"
    				</c:if>
    				>	
					<c:forEach items="${ecoAddomeTipo.lookupEcoAddomeReferti}" var="referto" >
						<c:set var="checked" value="NO"/>
						<c:if test="${referto.tipo == 'N'}">
						<%-- Cicli preventivi per controllare se il referto è stato selezionato --%>
							<c:forEach items="${ecoAddomeEsitos}" var="esitoTemp">
								<c:forEach items="${esitoTemp.lookupEcoAddomeReferti}" var="refertoTemp">
								<c:if test="${refertoTemp.tipo=='N' && refertoTemp.id == referto.id}">
									<c:set var="checked" value="SI"></c:set>
								</c:if>
								</c:forEach>
							</c:forEach>
								<c:if test="${checked=='SI'}">
	  		          				${referto.nome}	 
	    						</c:if>
							<br>
						</c:if>				
					</c:forEach>
					
					<c:if test="${ecoAddomeTipo.nome=='Gravidanza'}">
							<br/>
							Dimensione camera ovulare intrauterina (mm): 
							${ecoAddome.gravidanza1} x ${ecoAddome.gravidanza2} x ${ecoAddome.gravidanza3}						
							<br/>
							CRL (mm): ${ecoAddome.gravidanza4}
					</c:if>
				</div>
			</td>			
		</tr>
		
		<c:set var="i" value='${i+1}'/>

	    </c:forEach>
	    
	    </table>
	    <br>
		<table class="tabella">
			<tr>
				<th colspan="3">
				       		Altre Informazioni
				 </th>
			 </tr> 	  
			<tr class="odd">
				<td>Inserito da</td>
				<td>${ecoAddome.enteredBy} il <fmt:formatDate value="${ecoAddome.entered}" pattern="dd/MM/yyyy"/></td>
			</tr>
			<c:if test="${ecoAddome.modifiedBy!=null}">
			<tr class="even">
				<td>Modificato da</td><td>${ecoAddome.modifiedBy} il <fmt:formatDate value="${ecoAddome.modified}" pattern="dd/MM/yyyy"/></td>
			</tr>
			</c:if>
		</table>

		<input type="button" value="Modifica"  onclick="if(${cc.dataChiusura!=null}){ 
    				if(myConfirm('Cartella Clinica chiusa. Vuoi procedere?')){location.href='vam.cc.diagnosticaImmagini.ecoAddome.ToEdit.us?idEcoAddome=${ecoAddome.id}'}
    				}else{location.href='vam.cc.diagnosticaImmagini.ecoAddome.ToEdit.us?idEcoAddome=${ecoAddome.id}'}"/>
		<input type="button" value="Lista Eco-Addome"     onclick="location.href='vam.cc.diagnosticaImmagini.ecoAddome.List.us?'">
    	

