<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/vam.tld" prefix="vam" %>

<vam:soglie animale="${eu.cartellaClinica.accettazione.animale}" esame="${eu }" >


<jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
<h4 class="titolopagina">
     	Dettaglio esame delle urine
</h4>

<table class="tabella" id="dettaglio" >
    
<%--     	<tr>
    		<th colspan="3">
    			Esame delle urine effettuato dal Dott. ${es.enteredBy.nome} ${es.enteredBy.cognome} 
    			in data <fmt:formatDate type="date" value="${eu.dataRichiesta}" pattern="dd/MM/yyyy" /><br/>
    		</th>
    	</tr> --%>
    	
    	 <tr>
        	<th colspan="2">
        		Esame Fisico/Chimico
        	</th>        	
        </tr>
        
        <tr class='even'>
    		<td>
    			Volume (L/die) 
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="volume" />   			 
    		</td>
        </tr>
        
         <tr class='odd'>
    		<td>
    			Peso Specifico
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="pesoSpecifico" />    			 
    		</td>
        </tr>
        
         <tr class='even'>
    		<td>
    			Colore
    		</td>
    		<td>
    			<font color="<c:if test="${eu.colore.normale }">#00C000</c:if><c:if test="${!eu.colore.normale }">red</c:if>">
    				${eu.colore.description }
    			</font>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			PH
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="ph" />      			
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Proteine mg/die
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="proteine" />     			 
    		</td>
        </tr>
        
         <tr class='odd'>
    		<td>
    			Glucosio
    		</td>
    		<td>
    			<font color="<c:if test="${eu.glucosio.normale }">#00C000</c:if><c:if test="${!eu.glucosio.normale }">red</c:if>">
    				${eu.glucosio.descriptionS }
    			</font>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Bilirubina
    		</td>
    		<td>
    			<font color="<c:if test="${eu.bilirubina.normale }">#00C000</c:if><c:if test="${!eu.bilirubina.normale }">red</c:if>">
    				${eu.bilirubina.descriptionS }
    			</font>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Corpi Chetonici
    		</td>
    		<td>
    			<font color="<c:if test="${eu.corpiChetonici.normale }">#00C000</c:if><c:if test="${!eu.corpiChetonici.normale }">red</c:if>">
    				${eu.corpiChetonici.descriptionPM }
    			</font>
    		</td>
        </tr>
        
         <tr class='even'>
    		<td>
    			Emoglobina
    		</td>
    		<td>
    			<font color="<c:if test="${eu.emoglobina.normale }">#00C000</c:if><c:if test="${!eu.emoglobina.normale }">red</c:if>">
    				${eu.corpiChetonici.descriptionS }
    			</font>
    		</td>
        </tr>
       
        <tr class='odd'>
    		<td>
    			Nitriti
    		</td>
    		<td>
    			<font color="<c:if test="${eu.nitriti.normale }">#00C000</c:if><c:if test="${!eu.nitriti.normale }">red</c:if>">
    				${eu.nitriti.descriptionPM }
    			</font>
    		</td>
        </tr>
                
        <tr class='even'>
    		<td>
    			Urobilinogeno (unità Erlich.)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="urobilinogeno" />     			 
    		</td>
        </tr>
                
         <tr class='odd'>
    		<td>
    			Sangue
    		</td>
    		<td>
    			<font color="<c:if test="${eu.sangue.normale }">#00C000</c:if><c:if test="${!eu.sangue.normale }">red</c:if>">
    				${eu.sangue.descriptionS }
    			</font>
    		</td>
        </tr>
	
		<tr>
        	<th colspan="2">
        		Sedimento Urinario
        	</th>        	
        </tr>
	
		<tr class='even'>
    		<td>
    			Batteri
    		</td>
    		<td>
    			<font color="<c:if test="${eu.batteri.normale }">#00C000</c:if><c:if test="${!eu.batteri.normale }">red</c:if>">
    				${eu.batteri.descriptionPM }
    			</font>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Cellule Epiteliali
    		</td>
    		<td>
    			<font color="<c:if test="${eu.celluleEpiteliali.normale }">#00C000</c:if><c:if test="${!eu.celluleEpiteliali.normale }">red</c:if>">
    				${eu.celluleEpiteliali.descriptionPF }
    			</font>     			 
    		</td>
        </tr>
        
         <tr class='even'>
    		<td>
    			Cilindri
    		</td>
    		<td>
    			<font color="<c:if test="${eu.cilindri.normale }">#00C000</c:if><c:if test="${!eu.cilindri.normale }">red</c:if>">
    				${eu.cilindri.descriptionPM }
    			</font> 
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Cristalli
    		</td>
    		<td>
    			<font color="<c:if test="${eu.cristalli.normale }">#00C000</c:if><c:if test="${!eu.cristalli.normale }">red</c:if>">
    				${eu.cristalli.descriptionPM }
    			</font> 		 
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Eritrociti
    		</td>
    		<td>
    			<font color="<c:if test="${eu.eritrociti.normale }">#00C000</c:if><c:if test="${!eu.eritrociti.normale }">red</c:if>">
    				${eu.eritrociti.descriptionPM }
    			</font>
    		</td>
        </tr>
        
         <tr class='odd'>
    		<td>
    			Leucociti
    		</td>
    		<td>
    			<font color="<c:if test="${eu.leucociti.normale }">#00C000</c:if><c:if test="${!eu.leucociti.normale }">red</c:if>">
    				${eu.leucociti.descriptionPM }
    			</font>
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
				<td>${eu.enteredBy} il <fmt:formatDate value="${eu.entered}" pattern="dd/MM/yyyy"/></td>
			</tr>
			<c:if test="${eu.modifiedBy!=null}">
			<tr class="even">
				<td>Modificato da</td><td>${eu.modifiedBy} il <fmt:formatDate value="${eu.modified}" pattern="dd/MM/yyyy"/></td>
			</tr>
			</c:if>
		</table>
	    	<input type="button" value="Modifica esame" onclick="if(${cc.dataChiusura!=null}){ 
    				if(myConfirm('Cartella Clinica chiusa. Vuoi procedere?')){attendere(), location.href='vam.cc.esamiUrine.ToEdit.us?id=${eu.id}'}
    				}else{attendere(), location.href='vam.cc.esamiUrine.ToEdit.us?id=${eu.id}'}">
    	

   	
   	</vam:soglie>
   	
   	<script type="text/javascript">
		$("#dettaglio font[title]").tooltip( { position: "center right", offset: [5, 10] } );
	</script>