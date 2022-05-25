<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>


<input type="button" value="Immagini Istopatologico" onclick="javascript:avviaPopup( 'vam.cc.esamiIstopatologici.GestioneImmagini.us?id=${esame.id }&cc=${cc.id}' );" />

<c:if test="${esame.outsideCC==false}">
	<input type="button" value="Necroscopia/Vedi CC" onclick="javascript:if(${esame.cartellaClinica.autopsia!=null}){avviaPopup( 'vam.cc.autopsie.DetailDaIsto.us?idCc=${esame.cartellaClinica.id}' );}else{avviaPopup( 'vam.cc.DetailDaIsto.us?idCc=${esame.cartellaClinica.id}' );}" />			
</c:if>
<c:if test="${param['flagAnagrafe'] == null }">
	<input type="button" value="Chiudi" onclick="window.close();" />
</c:if>
				
<h4 class="titolopagina">
     		Dettaglio Esame Istopatologico
</h4>

<table class="tabella">
    	
    	<tr>
    		<th colspan="3">
    			Anagrafica Animale
    		</th>
    		
    	</tr>
    	
    	<tr class='even'>
    		<td>
    			Tipologia
    		</td>
    		<td>  
    			<c:out value="${esame.cartellaClinica.accettazione.animale.lookupSpecie.description}"/>
    		</td>  
    		<td>
    		</td>  		
        </tr>
    	
    	<tr class='odd'>
    		<td>
    			Identificativo
    		</td>
    		<td>
    			<c:out value="${esame.cartellaClinica.accettazione.animale.identificativo}"/>
    		</td>  
    		<td>
    		</td>  		
        </tr>
        
        <!-- tr class='even'>
    		<td>
    			Razza
    		</td>
    		<td>
    			<c:if test="${(esame.cartellaClinica.accettazione.animale.lookupSpecie.id == specie.cane or esame.cartellaClinica.accettazione.animale.lookupSpecie.id == specie.gatto) && esame.cartellaClinica.accettazione.animale.decedutoNonAnagrafe==false}">${razza}</c:if>
							  <c:if test="${esame.cartellaClinica.accettazione.animale.decedutoNonAnagrafe==true}">${esame.cartellaClinica.accettazione.animale.razza}</c:if>
						      <c:if test="${esame.cartellaClinica.accettazione.animale.lookupSpecie.id == specie.sinantropo && esame.cartellaClinica.accettazione.animale.decedutoNonAnagrafe==false}">${esame.cartellaClinica.accettazione.animale.specieSinantropoString} - ${esame.cartellaClinica.accettazione.animale.razzaSinantropo}</c:if>	</td>    
    		<td>
    		</td>		
        </tr-->
        
        <tr class='odd'>
    		<td>
    			Sesso
    		</td>
    		<td>
  				<c:if test="${(esame.cartellaClinica.accettazione.animale.lookupSpecie.id == specie.cane || esame.cartellaClinica.accettazione.animale.lookupSpecie.id == specie.gatto) && esame.cartellaClinica.accettazione.animale.decedutoNonAnagrafe==false}">${sesso}</c:if>
							  <c:if test="${esame.cartellaClinica.accettazione.animale.decedutoNonAnagrafe==true}">${esame.cartellaClinica.accettazione.animale.sesso}</c:if>
						      <c:if test="${esame.cartellaClinica.accettazione.animale.lookupSpecie.id == specie.sinantropo && esame.cartellaClinica.accettazione.animale.decedutoNonAnagrafe==false}">${esame.cartellaClinica.accettazione.animale.sesso}</c:if>
    		</td> 
    		<td>
    		</td>   		
        </tr>
        
        <tr class='even'>
    		<c:choose>
				<c:when test="${esame.cartellaClinica.accettazione.animale.lookupSpecie.id==3}">
					<td>
						Et&agrave;
					</td>
					<td>
						<fmt:formatDate type="date" value="${esame.cartellaClinica.accettazione.animale.dataNascita }" pattern="dd/MM/yyyy" />
						${esame.cartellaClinica.accettazione.animale.eta} <c:if test="${dataNascita}">(${dataNascita})</c:if>
					</td>
				</c:when>
				<c:otherwise>
					<td>
						Data nascita 
					</td>
					<td>
						<fmt:formatDate type="date" value="${esame.cartellaClinica.accettazione.animale.dataNascita }" pattern="dd/MM/yyyy" />
  						${dataNascita}
					</td>
				</c:otherwise>
			</c:choose>  
    		<td>
    		</td> 		
        </tr>
        
       <c:if test="${esame.cartellaClinica.accettazione.animale.dataMorte!=null || res.dataEvento!=null}">
       <c:choose>
        	<c:when test="${esame.cartellaClinica.accettazione.animale.decedutoNonAnagrafe == true}">						
				<fmt:formatDate type="date" value="${esame.cartellaClinica.accettazione.animale.dataMorte}" pattern="dd/MM/yyyy" var="dataMorte"/>
			</c:when>
			<c:otherwise>
				<fmt:formatDate type="date" value="${res.dataEvento}" pattern="dd/MM/yyyy" var="dataMorte"/>
			</c:otherwise>	
		</c:choose>	 
        <tr class='odd'>
   			<td>
   				Data del decesso
   			</td>
   			<td>
				${dataMorte}
			</td>
			<td>			
				<c:choose>
					<c:when test="${esame.cartellaClinica.accettazione.animale.decedutoNonAnagrafe == true}">						
						${cc.accettazione.animale.dataMorteCertezza}
					</c:when>
					<c:otherwise>
						${res.dataMorteCertezza}
					</c:otherwise>	
				</c:choose>	 
        	</td>
   		</tr>
    	
   		<tr class='even'>
      	  <td>
    			Probabile causa del decesso
   			</td>
   			<td>    
   				<c:choose>
    				<c:when test="${esame.cartellaClinica.accettazione.animale.decedutoNonAnagrafe}">
    					${esame.cartellaClinica.accettazione.animale.causaMorte.description}
    				</c:when>
    				<c:otherwise>
    					${res.decessoValue}
    				</c:otherwise>
    			</c:choose>	        	        
        	</td>
       		<td>
        	</td>
       	</tr>
       	</c:if>
       	
       	
       	
       	
       	
       	
       	<tr class='odd'>
    		<td>
    			Peso dell'animale (in Kg)
    		</td>
    		<td>
    			 <c:choose>
					<c:when test="${esame.outsideCC}">
						${esame.peso}
					</c:when>
					<c:otherwise>
						${esame.cartellaClinica.peso}
					</c:otherwise>   
				</c:choose>
    		</td>
    		<td>
    		</td>
        </tr>

        <tr class='even'>
    		<td>
    			Habitat 
    		</td>
    		<td>
				<c:choose>
					<c:when test="${esame.outsideCC}">
						${esame.lookupHabitats}
					</c:when>
					<c:otherwise>
						${esame.cartellaClinica.lookupHabitats}
					</c:otherwise>   
				</c:choose>
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Alimentazione
    		</td>
    		<td>
    			<c:choose>
					<c:when test="${esame.outsideCC}">
						${esame.lookupAlimentazionis}
					</c:when>
					<c:otherwise>
						${esame.cartellaClinica.lookupAlimentazionis}
					</c:otherwise>   
				</c:choose>
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr>
    		<th colspan="2">
    			Esame Istopatologico numero ${esame.numero } , richiesto 
    			in data <fmt:formatDate type="date" value="${esame.dataRichiesta}" pattern="dd/MM/yyyy" />
    			. Diagnosi ricevuta in data <fmt:formatDate type="date" value="${esame.dataEsito}" pattern="dd/MM/yyyy" />
    		</th>
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
        <tr>
        	<th colspan="2">
        		RISULTATO
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
   	
   	<input type="button" value="Immagini Istopatologico" onclick="javascript:avviaPopup( 'vam.cc.esamiIstopatologici.GestioneImmagini.us?id=${esame.id }&cc=${cc.id}' );" />
	<c:if test="${esame.outsideCC==false}">
		<input type="button" value="Necroscopia/Vedi CC" onclick="javascript:if(${esame.cartellaClinica.autopsia!=null}){avviaPopup( 'vam.cc.autopsie.DetailDaIsto.us?idCc=${esame.cartellaClinica.id}' );}else{avviaPopup( 'vam.cc.DetailDaIsto.us?idCc=${esame.cartellaClinica.id}' );}" />			
 	</c:if>
 	<c:if test="${param['flagAnagrafe'] == null }">
		<input type="button" value="Chiudi" onclick="window.close();" />
	</c:if>
