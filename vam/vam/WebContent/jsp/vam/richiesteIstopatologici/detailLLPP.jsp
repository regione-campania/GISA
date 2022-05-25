<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<h4 class="titolopagina">
     		Dettaglio Esame Istopatologico
</h4>


<br>
<c:set var="tipo" scope="request" value="stampaIstoSingoloLP"/>
<c:set var="idEsame" scope="request" value="${esame.id }"/>
<c:import url="../../jsp/documentale/homeRichiesteIsto.jsp"/>
<!-- input type="button" value="Stampa la richiesta" onclick="location.href='vam.richiesteIstopatologici.Pdf.us?id=${esame.id }'" /-->
<input type="hidden" name="liberoProfessionista" value="${liberoProfessionista }" />
    
<table class="tabella">
    	
        <tr>
    		<th colspan="2">
    			Esame Istopatologico numero ${esame.numero} , richiesto 
    			in data <fmt:formatDate type="date" value="${esame.dataRichiesta}" pattern="dd/MM/yyyy" />
    		</th>
    	</tr>

		<tr class="odd">
    		<td style="width:30%">
    			 Peso Animale
    		</td>
    		<td style="width:70%">  
    			${esame.peso}
    		</td>
        </tr>     
        <tr class="even">
    		<td style="width:30%">
    			 Alimentazioni
    		</td>
    		<td style="width:70%">  
    		
    			<c:forEach items="${la}" var="a">
						
						${a.description} - 							
		    					      											
				</c:forEach>					
    		   		
    			
    		</td>
        </tr>     
        <tr class="odd">
    		<td style="width:30%">
    			 Habitat
    		</td>
    		<td style="width:70%">  
    			<c:forEach items="${lh}" var="h">
						
						${h.description} - 							
		    					      											
				</c:forEach>		
    		</td>
        </tr>     


        <tr class='even'>
    		<td>
    			 Laboratorio di destinazione
    		</td>
    		<td>
    			 ${esame.lass.description}
    		</td>
    		<td>
    		</td>
        </tr>

		<tr class="odd">
    		<td style="width:30%">
    			 Tipo Prelievo
    		</td>
    		<td style="width:70%">  
    			${esame.tipoPrelievo.description }
    		</td>
        </tr>     
        
        <tr>
    		<td style="width:30%">
    			 Tumori Precedenti
    		</td>
    		<td style="width:70%"> 
    			${esame.tumoriPrecedenti.description }  
    		</td>
        </tr>    
        
    <c:if test="${esame.tumoriPrecedenti.id == 2 }">
        <tr>
    		<td style="width:30%">
    			 T
    		</td>
    		<td style="width:70%"> 
    			${esame.t }  
    		</td>
        </tr>    
        
        <tr>
    		<td style="width:30%">
    			 N
    		</td>
    		<td style="width:70%"> 
    			${esame.n }  
    		</td>
        </tr>  
        
        <tr>
    		<td style="width:30%">
    			 M
    		</td>
    		<td style="width:70%">  
    			${esame.m }  
    		</td>
        </tr> 
    </c:if>
        
        <tr class="odd">
    		<td style="width:30%" >
    			 Dimensione (centimetri)
    		</td>
    		<td style="width:70%"> 
    			${esame.dimensione }
    		</td>
        </tr> 
        
        <tr>
    		<td style="width:30%">
    			 Interessamento Linfonodale
    		</td>
    		<td style="width:70%">  
    			${esame.interessamentoLinfonodale.description }  
    		</td>
        </tr> 
        
        <tr class="odd">
    		<td style="width:30%">
    			 Sede Lesione e Sottospecifica
    		</td>
    		<td style="width:70%">  
    			${esame.sedeLesione }  
    		</td>
        </tr> 
        
        <tr class="even">
    		<td style="width:30%">
    			 Numero riferimento mittente
    		</td>
    		<td style="width:70%">  
    			${esame.numeroAccettazioneSigla} 
    		</td>
        </tr>
        <tr>
        	<th colspan="2">
        		Risultato
        	</th>
        </tr>
        <tr>
    		<td style="width:30%">
    			 Descrizione Morfologica
    		</td>
    		<td style="width:70%">  
    			${esame.descrizioneMorfologica }  
    		</td>
        </tr>
        
        <tr class="odd">
    		<td style="width:30%">
    			 Diagnosi
    		</td>
    		<td style="width:70%">  
    			${esame.tipoDiagnosi.description }: ${esame.whoUmana } ${esame.diagnosiNonTumorale } 
    		</td>
        </tr> 
        
    	
   	</table>
