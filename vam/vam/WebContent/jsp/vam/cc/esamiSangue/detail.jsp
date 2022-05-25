<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/vam.tld" prefix="vam" %>

<vam:soglie animale="${es.cartellaClinica.accettazione.animale}" esame="${es }" >


<jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
<h4 class="titolopagina">
     	Dettaglio esame del sangue
</h4>

<table class="tabella" id="dettaglio" >
    
<%--     	<tr>
    		<th colspan="3">
    			Esame del sangue effettuato dal Dott. ${es.enteredBy.nome} ${es.enteredBy.cognome} 
    			in data <fmt:formatDate type="date" value="${es.dataRichiesta}" pattern="dd/MM/yyyy" var="dataRichiesta"/>
    			 <c:out value="${dataRichiesta}"/>
    		</th>
    	</tr>  --%>
    	
    	 <tr>
        	<th colspan="2">
        		Ematochimici
        	</th>        	
        </tr>
        
        <tr class='even'>
    		<td>
    			BUN (mg/dl) 
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="ematochimiciBun" />   			 
    		</td>
        </tr>
        
         <tr class='odd'>
    		<td>
    			Bilirubina (mg/dl)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="ematochimiciBilirubinaTotale" />    			 
    		</td>
        </tr>
        
         <tr class='even'>
    		<td>
    			Creatinina (mg/dl)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="ematochimiciCreatinina" />      			 
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Glucosio (mg/dl)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="ematochimiciGlucosio" />      			
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Colesterolo (mg/dl)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="ematochimiciColesterolo" />     			 
    		</td>
        </tr>
        
         <tr class='odd'>
    		<td>
    			Proteine Totali (g/dl)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="ematochimiciProteineTotali" />      			
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Albumine (g/dl)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="ematochimiciAlbumine" />      			
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Globuline (g/dl)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="ematochimiciGlobuline" />      			 
    		</td>
        </tr>
        
         <tr class='even'>
    		<td>
    			Rapporto A:G (g/dl)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="ematochimiciRapportoAG" />     			
    		</td>
        </tr>
       
                
	
	
		<tr>
        	<th colspan="2">
        		Elettroliti
        	</th>        	
        </tr>
	
		<tr class='even'>
    		<td>
    			Calcio (mg/dl)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="elettrolitiCalcio" />     			 
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Calcio corretto (mg/dl)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="elettrolitiCalcioCorretto" />     			 
    		</td>
        </tr>
        
         <tr class='even'>
    		<td>
    			Fosforo (mg/dl)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="elettrolitiFosforo" />     			
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Sodio (mEq/L)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="elettrolitiSodio" />     			 
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Potassio (mEq/L)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="elettrolitiPotassio" />     			 
    		</td>
        </tr>
        
         <tr class='odd'>
    		<td>
    			Rapporto Na/K
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="elettrolitiRapportoNaK" />     			 
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Cloro (mEq/L)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="elettrolitiCloro" />    			
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Rapporto Ca/P
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="elettrolitiRapportoCaP" />    			
    		</td>
        </tr>
        
         <tr class='even'>
    		<td>
    			Prodotto Ca*P
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="elettrolitiProdottoCaP" />
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Cloro corretto (mEq/L)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="elettrolitiCloroCorretto" />
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Fe (&#181;g/dl)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="elettrolitiFe" />
    		</td>
        </tr>
        
         <tr class='odd'>
    		<td>
    			Magnesio (mg/dl)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="elettrolitiMagnesio" />
    		</td>
        </tr>
	
		
	
		<tr>
        	<th colspan="2">
        		Emogas Arterioso
        	</th>        	
        </tr>
	
		<tr class='even'>
    		<td>
    			PH
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="emogasArteriosoPh" />
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			TCO2 ((mEq/L))
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="emogasArteriosoTco2" />
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			HCO3 (mEq/L)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="emogasArteriosoHco3" />
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			pCO2 (mmHg)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="emogasArteriosoPco2" />
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			pO2 (mmHg)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="emogasArteriosoPo2" />
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Anion Gap (mEq/L)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="emogasArteriosoAnionGap" />
    		</td>
        </tr>
	
	
	
		<tr>
        	<th colspan="2">
        		Emogas Venoso
        	</th>        	
        </tr>
        
		<tr class='even'>
    		<td>
    			PH
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="emogasVenosoPh" />
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			TCO2 (mEq/L)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="emogasVenosoTco2" />
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			HCO3 (mEq/L)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="emogasVenosoHco3" />
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			pCO2 (mmHg)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="emogasVenosoPco2" />
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			pO2 (mmHg)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="emogasVenosoPo2" />
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Anion Gap (mEq/L)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="emogasVenosoAnionGap" />
    		</td>
        </tr>
	
	
	
	
	
	
		<tr>
        	<th colspan="2">
        		Enzimatici
        	</th>        	
        </tr>
	
		<tr class='even'>
    		<td>
    			AST (IU/L)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="enzimaticiAst" />
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			ALT (IU/L)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="enzimaticiAlt" />
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			ALP (IU/L)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="enzimaticiAlp" />
    		</td>
        </tr>
        
         <tr class='odd'>
    		<td>
    			GGT (IU/L)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="enzimaticiGgt" />
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			CPK (IU/L)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="enzimaticiCpk" />
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			LDH (IU/L)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="enzimaticiLdh" />
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Amilasi (IU/L)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="enzimaticiAmilasi" />
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Lipasi (IU/L)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="enzimaticiLipasi" />
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Fruttosamina (mcmol/l)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="enzimaticiFruttosamina" />
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Trigliceridi (mg/dl)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="enzimaticiTrigliceridi" />
    		</td>
        </tr>
	
	
	
	
		<tr>
        	<th colspan="2">
        		Elettroforesi
        	</th>        	
        </tr>
	
	
		<tr class='even'>
    		<td>
    			Albumine (%)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="elettroforesiAlbumine" />
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Alfa1 (g/dl)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="elettroforesiAlfa1" />
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Alfa2 (g/dl)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="elettroforesiAlfa2" />
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Beta (g/dl)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="elettroforesiBeta" />
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Gamma (g/dl)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="elettroforesiGamma" />
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Globuline (%)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="elettroforesiGlobuline" />
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Rapporto A:G
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="elettroforesiRapportoAG" />
    		</td>
        </tr>
	
		
		
		<tr>
        	<th colspan="2">
        		Acidi Biliari
        	</th>        	
        </tr>
	
	
		<tr class='even'>
    		<td>
    			Acidi Biliari (mcmol/l)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="acidiBiliari" />
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Acidi Biliari Postp. (mcmol/l)
    		</td>
    		<td>
    			<vam:soglieoutput proprieta="acidiBiliariPostp" />
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
				<td>${es.enteredBy} il <fmt:formatDate value="${es.entered}" pattern="dd/MM/yyyy"/></td>
			</tr>
			<c:if test="${es.modifiedBy!=null}">
			<tr class="even">
				<td>Modificato da</td><td>${es.modifiedBy} il <fmt:formatDate value="${es.modified}" pattern="dd/MM/yyyy"/></td>
			</tr>
			</c:if>
		</table>
	    
	    <input type="button" value="Modifica esame" onclick="if(${cc.dataChiusura!=null}){ 
    				if(myConfirm('Cartella Clinica chiusa. Vuoi procedere?')){attendere(), location.href='vam.cc.esamiSangue.ToEdit.us?idEsameSangue=${es.id}'}
    				}else{attendere(), location.href='vam.cc.esamiSangue.ToEdit.us?idEsameSangue=${es.id}'}">
   	</vam:soglie>
   	
   	<script type="text/javascript">
		$("#dettaglio font[title]").tooltip( { position: "center right", offset: [5, 10] } );
	</script>