<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<script language="JavaScript" type="text/javascript" src="js/vam/cc/esamiSangue/edit.js"></script>


<form action="vam.cc.esamiSangue.Edit.us" name="form" method="post" class="marginezero" onsubmit="javascript:return checkform(this);" >
           
    <input type="hidden" name="idEsameSangue" value="<c:out value="${es.id}"/>"/>
	
	
	<jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
	<h4 class="titolopagina">
     	Modifica esame del sangue
	</h4>
	
	<table class="tabella">
	    
	    	<tr>
	    		<th colspan="3">
	    			Esame del sangue effettuato dal Dott. ${es.enteredBy.nome} ${es.enteredBy.cognome} 
	    			in data <fmt:formatDate type="date" value="${es.dataRichiesta}" pattern="dd/MM/yyyy" var="dataRichiesta"/>
	    			 <c:out value="${dataRichiesta}"/>
	    		</th>
	    	</tr>
	    	
	    	<tr class='even'>
	    		<td>
    			 	Data Esito
    				<fmt:formatDate value="${es.dataEsito}" pattern="dd/MM/yyyy" var="dataEsito"/>   			 
    				<input type="text" id="dataEsito" name="dataEsito" maxlength="32" size="50" readonly="readonly" style="width:246px;" value="${dataEsito}"/>
    				<img src="images/b_calendar.gif" alt="calendario" id="id_img_2" />
 						<script type="text/javascript">
      						 Calendar.setup({
        						inputField     :    "dataEsito",     // id of the input field
        						ifFormat       :    "%d/%m/%Y",      // format of the input field
       							button         :    "id_img_2",  // trigger for the calendar (button ID)
       							// align          :    "Tl",           // alignment (defaults to "Bl")
        						singleClick    :    true,
        						timeFormat		:   "24",
        						showsTime		:   false
   								 });					    
  					 	</script>   
    			</td>
    		</tr>
	    	
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
	    			<input type="text" id="sangue1" name="ematochimiciBun" maxlength="10" size="10" value="<c:out value="${es.ematochimiciBun}"/>"/>
	    			    			 
	    		</td>
	        </tr>
	        
	         <tr class='odd'>
	    		<td>
	    			Bilirubina (mg/dl)
	    		</td>
	    		<td>    		
	    			<input type="text" id="sangue2" name="ematochimiciBilirubinaTotale" maxlength="10" size="10" value="<c:out value="${es.ematochimiciBilirubinaTotale}"/>"/>
	    			 			 
	    		</td>
	        </tr>
	        
	         <tr class='even'>
	    		<td>
	    			Creatinina (mg/dl)
	    		</td>
	    		<td>
	    			   	
	    			 <input type="text" id="sangue3" name="ematochimiciCreatinina" maxlength="10" size="10" value="<c:out value="${es.ematochimiciCreatinina}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='odd'>
	    		<td>
	    			Glucosio (mg/dl)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue4" name="ematochimiciGlucosio" maxlength="10" size="10" value="<c:out value="${es.ematochimiciGlucosio}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='even'>
	    		<td>
	    			Colesterolo (mg/dl)
	    		</td>
	    		<td>
	    		 
	    			 <input type="text" id="sangue5" name="ematochimiciColesterolo" maxlength="10" size="10" value="<c:out value="${es.ematochimiciColesterolo}"/>"/>
	    		</td>
	        </tr>
	        
	         <tr class='odd'>
	    		<td>
	    			Proteine Totali (g/dl)
	    		</td>
	    		<td>
	    		  
	    			 <input type="text" id="sangue6" name="ematochimiciProteineTotali" maxlength="10" size="10" value="<c:out value="${es.ematochimiciProteineTotali}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='even'>
	    		<td>
	    			Albumine (g/dl)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue7" name="ematochimiciAlbumine" maxlength="10" size="10" value="<c:out value="${es.ematochimiciAlbumine}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='odd'>
	    		<td>
	    			Globuline (g/dl)
	    		</td>
	    		<td>
	    		  
	    			 <input type="text" id="sangue8" name="ematochimiciGlobuline" maxlength="10" size="10" value="<c:out value="${es.ematochimiciGlobuline}"/>"/>
	    		</td>
	        </tr>
	        
	         <tr class='even'>
	    		<td>
	    			Rapporto A:G (g/dl)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue9" name="ematochimiciRapportoAG" maxlength="10" size="10" value="<c:out value="${es.ematochimiciRapportoAG}"/>"/>
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
	    		
	    			 <input type="text" id="sangue10" name="elettrolitiCalcio" maxlength="10" size="10" value="<c:out value="${es.elettrolitiCalcio}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='odd'>
	    		<td>
	    			Calcio corretto (mg/dl)
	    		</td>
	    		<td>
	    		 
	    			 <input type="text" id="sangue11" name="elettrolitiCalcioCorretto" maxlength="10" size="10" value="<c:out value="${es.elettrolitiCalcioCorretto}"/>"/>
	    		</td>
	        </tr>
	        
	         <tr class='even'>
	    		<td>
	    			Fosforo (mg/dl)
	    		</td>
	    		<td>
	    		 
	    			 <input type="text" id="sangue12" name="elettrolitiFosforo" maxlength="10" size="10" value="<c:out value="${es.elettrolitiFosforo}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='odd'>
	    		<td>
	    			Sodio (mEq/L)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue13" name="elettrolitiSodio" maxlength="10" size="10" value="<c:out value="${es.elettrolitiSodio}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='even'>
	    		<td>
	    			Potassio (mEq/L)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue14" name="elettrolitiPotassio" maxlength="10" size="10" value="<c:out value="${es.elettrolitiPotassio}"/>"/>
	    		</td>
	        </tr>
	        
	         <tr class='odd'>
	    		<td>
	    			Rapporto Na/K
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue15" name="elettrolitiRapportoNaK" maxlength="10" size="10" value="<c:out value="${es.elettrolitiRapportoNaK}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='even'>
	    		<td>
	    			Cloro (mEq/L)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue16" name="elettrolitiCloro" maxlength="10" size="10" value="<c:out value="${es.elettrolitiCloro}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='odd'>
	    		<td>
	    			Rapporto Ca/P
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue17" name="elettrolitiRapportoCaP" maxlength="10" size="10" value="<c:out value="${es.elettrolitiRapportoCaP}"/>"/>
	    		</td>
	        </tr>
	        
	         <tr class='even'>
	    		<td>
	    			Prodotto Ca*P
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue18" name="elettrolitiProdottoCaP" maxlength="10" size="10" value="<c:out value="${es.elettrolitiProdottoCaP}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='odd'>
	    		<td>
	    			Cloro corretto (mEq/L)
	    		</td>
	    		<td>
	    			
	    			 <input type="text" id="sangue18" name="elettrolitiCloroCorretto" maxlength="10" size="10" value="<c:out value="${es.elettrolitiCloroCorretto}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='even'>
	    		<td>
	    			Fe (&#181;g/dl)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue19" name="elettrolitiFe" maxlength="10" size="10" value="<c:out value="${es.elettrolitiFe}"/>"/>
	    		</td>
	        </tr>
	        
	         <tr class='odd'>
	    		<td>
	    			Magnesio (mg/dl)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue20" name="elettrolitiMagnesio" maxlength="10" size="10" value="<c:out value="${es.elettrolitiMagnesio}"/>"/>
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
	    		
	    			 <input type="text" id="sangue21" name="emogasArteriosoPh" maxlength="10" size="10" value="<c:out value="${es.emogasArteriosoPh}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='odd'>
	    		<td>
	    			TCO2 ((mEq/L))
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue22" name="emogasArteriosoTco2" maxlength="10" size="10" value="<c:out value="${es.emogasArteriosoTco2}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='even'>
	    		<td>
	    			HCO3 (mEq/L)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue23" name="emogasArteriosoHco3" maxlength="10" size="10" value="<c:out value="${es.emogasArteriosoHco3}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='odd'>
	    		<td>
	    			pCO2 (mmHg)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue24" name="emogasArteriosoPco2" maxlength="10" size="10" value="<c:out value="${es.emogasArteriosoPco2}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='even'>
	    		<td>
	    			pO2 (mmHg)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue25" name="emogasArteriosoPo2" maxlength="10" size="10" value="<c:out value="${es.emogasArteriosoPo2}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='odd'>
	    		<td>
	    			Anion Gap (mEq/L)
	    		</td>
	    		<td>	    		
	    			 <input type="text" id="sangue26" name="emogasArteriosoAnionGap" maxlength="10" size="10" value="<c:out value="${es.emogasArteriosoAnionGap}"/>"/>
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
	    		
	    			 <input type="text" id="sangue27" name="emogasVenosoPh" maxlength="10" size="10" value="<c:out value="${es.emogasVenosoPh}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='odd'>
	    		<td>
	    			TCO2 (mEq/L)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue28" name="emogasVenosoTco2" maxlength="10" size="10" value="<c:out value="${es.emogasVenosoTco2}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='even'>
	    		<td>
	    			HCO3 (mEq/L)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue29" name="emogasVenosoHco3" maxlength="10" size="10" value="<c:out value="${es.emogasVenosoHco3}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='odd'>
	    		<td>
	    			pCO2 (mmHg)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue30" name="emogasVenosoPco2" maxlength="10" size="10" value="<c:out value="${es.emogasVenosoPco2}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='even'> 
	    		<td>
	    			pO2 (mmHg)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue31" name="emogasVenosoPo2" maxlength="10" size="10" value="<c:out value="${es.emogasVenosoPo2}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='odd'>
	    		<td>
	    			Anion Gap (mEq/L)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue32" name="emogasVenosoAnionGap" maxlength="10" size="10" value="<c:out value="${es.emogasVenosoAnionGap}"/>"/>
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
	    		
	    			 <input type="text" id="sangue33" name="enzimaticiAst" maxlength="10" size="10" value="<c:out value="${es.enzimaticiAst}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='odd'>
	    		<td>
	    			ALT (IU/L)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue34" name="enzimaticiAlt" maxlength="10" size="10" value="<c:out value="${es.enzimaticiAlt}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='even'>
	    		<td>
	    			ALP (IU/L)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue35" name="enzimaticiAlp" maxlength="10" size="10" value="<c:out value="${es.enzimaticiAlp}"/>"/>
	    		</td>
	        </tr>
	        
	         <tr class='odd'>
	    		<td>
	    			GGT (IU/L)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue36" name="enzimaticiGgt" maxlength="10" size="10" value="<c:out value="${es.enzimaticiGgt}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='even'>
	    		<td>
	    			CPK (IU/L)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue37" name="enzimaticiCpk" maxlength="10" size="10" value="<c:out value="${es.enzimaticiCpk}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='odd'>
	    		<td>
	    			LDH (IU/L)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue38" name="enzimaticiLdh" maxlength="10" size="10" value="<c:out value="${es.enzimaticiLdh}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='even'>
	    		<td>
	    			Amilasi (IU/L)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue39" name="enzimaticiAmilasi" maxlength="10" size="10" value="<c:out value="${es.enzimaticiAmilasi}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='odd'>
	    		<td>
	    			Lipasi (IU/L)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue40" name="enzimaticiLipasi" maxlength="10" size="10" value="<c:out value="${es.enzimaticiLipasi}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='even'>
	    		<td>
	    			Fruttosamina (mcmol/l)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue41" name="enzimaticiFruttosamina" maxlength="10" size="10" value="<c:out value="${es.enzimaticiFruttosamina}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='odd'>
	    		<td>
	    			Trigliceridi (mg/dl)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue42" name="enzimaticiTrigliceridi" maxlength="10" size="10" value="<c:out value="${es.enzimaticiTrigliceridi}"/>"/>
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
	    		
	    			 <input type="text" id="sangue43" name="elettroforesiAlbumine" maxlength="10" size="10" value="<c:out value="${es.elettroforesiAlbumine}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='odd'>
	    		<td>
	    			Alfa1 (g/dl)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue44" name="elettroforesiAlfa1" maxlength="10" size="10" value="<c:out value="${es.elettroforesiAlfa1}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='even'>
	    		<td>
	    			Alfa2 (g/dl)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue45" name="elettroforesiAlfa2" maxlength="10" size="10" value="<c:out value="${es.elettroforesiAlfa2}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='odd'>
	    		<td>
	    			Beta (g/dl)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue46" name="elettroforesiBeta" maxlength="10" size="10" value="<c:out value="${es.elettroforesiBeta}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='even'>
	    		<td>
	    			Gamma (g/dl)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue47" name="elettroforesiGamma" maxlength="10" size="10" value="<c:out value="${es.elettroforesiGamma}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='odd'>
	    		<td>
	    			Globuline (%)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue48" name="elettroforesiGlobuline" maxlength="10" size="10" value="<c:out value="${es.elettroforesiGlobuline}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='even'>
	    		<td>
	    			Rapporto A:G
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue49" name="elettroforesiRapportoAG" maxlength="10" size="10" value="<c:out value="${es.elettroforesiRapportoAG}"/>"/>
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
	    		
	    			 <input type="text" id="sangue50" name="acidiBiliari" maxlength="10" size="10" value="<c:out value="${es.acidiBiliari}"/>"/>
	    		</td>
	        </tr>
	        
	        <tr class='odd'>
	    		<td>
	    			Acidi Biliari Postp. (mcmol/l)
	    		</td>
	    		<td>
	    		
	    			 <input type="text" id="sangue51" name="acidiBiliariPostp" maxlength="10" size="10" value="<c:out value="${es.acidiBiliariPostp}"/>"/>
	    		</td>
	        </tr>
		    	
	    	
	       <tr class='even'>	        	
	    		<td colspace="2"> 	    						
	    			<input type="submit" value="Modifica" />
	    			<input type="button" value="Annulla" onclick="attendere(), location.href='vam.cc.Detail.us'">
  			    			
	    		</td>
        </tr>
	</table>
</form>