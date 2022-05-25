<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<script language="JavaScript" type="text/javascript" src="js/vam/cc/esamiSangue/add.js"></script>


<form action="vam.cc.esamiSangue.Add.us" name="form" method="post" class="marginezero" onsubmit="javascript:return checkform(this);" >
           
    
    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
  	<h4 class="titolopagina">
     		Nuovo esame del sangue
    </h4>
    
    <table class="tabella">
    	        
        <tr>
    		<td>
    			 Data Richiesta <font color="red"> *</font>
    			 <input type="text" id="dataRichiesta" name="dataRichiesta" maxlength="32" size="50" readonly="readonly" style="width:246px;"/>
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_1" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dataRichiesta",     // id of the input field
        					ifFormat       :    "%d/%m/%Y",      // format of the input field
       						button         :    "id_img_1",  // trigger for the calendar (button ID)
       						// align          :    "Tl",           // alignment (defaults to "Bl")
        					singleClick    :    true,
        					timeFormat		:   "24",
        					showsTime		:   false
   							 });					    
  					 </script>   
    		</td>
    		<td>
    			 Data Esito
    			 <input type="text" id="dataEsito" name="dataEsito" maxlength="32" size="50" readonly="readonly" style="width:246px;"/>
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
    			 <input type="text" id="sangue1" name="ematochimiciBun" maxlength="10" size="10"/>
    		</td>
        </tr>
        
         <tr class='odd'>
    		<td>
    			Bilirubina (mg/dl)
    		</td>
    		<td>
    			 <input type="text" id="sangue2" name="ematochimiciBilirubinaTotale" maxlength="10" size="10"/>
    		</td>
        </tr>
        
         <tr class='even'>
    		<td>
    			Creatinina (mg/dl)
    		</td>
    		<td>
    			 <input type="text" id="sangue3" name="ematochimiciCreatinina" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Glucosio (mg/dl)
    		</td>
    		<td>
    			 <input type="text" id="sangue4" name="ematochimiciGlucosio" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Colesterolo (mg/dl)
    		</td>
    		<td>
    			 <input type="text" id="sangue5" name="ematochimiciColesterolo" maxlength="10" size="10"/>
    		</td>
        </tr>
        
         <tr class='odd'>
    		<td>
    			Proteine Totali (g/dl)
    		</td>
    		<td>
    			 <input type="text" id="sangue6" name="ematochimiciProteineTotali" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Albumine (g/dl)
    		</td>
    		<td>
    			 <input type="text" id="sangue7" name="ematochimiciAlbumine" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Globuline (g/dl)
    		</td>
    		<td>
    			 <input type="text" id="sangue8" name="ematochimiciGlobuline" maxlength="10" size="10"/>
    		</td>
        </tr>
        
         <tr class='even'>
    		<td>
    			Rapporto A:G (g/dl)
    		</td>
    		<td>
    			 <input type="text" id="sangue9" name="ematochimiciRapportoAG" maxlength="10" size="10"/>
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
    			 <input type="text" id="sangue10" name="elettrolitiCalcio" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Calcio corretto (mg/dl)
    		</td>
    		<td>
    			 <input type="text" id="sangue11" name="elettrolitiCalcioCorretto" maxlength="10" size="10"/>
    		</td>
        </tr>
        
         <tr class='even'>
    		<td>
    			Fosforo (mg/dl)
    		</td>
    		<td>
    			 <input type="text" id="sangue12" name="elettrolitiFosforo" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Sodio (mEq/L)
    		</td>
    		<td>
    			 <input type="text" id="sangue13" name="elettrolitiSodio" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Potassio (mEq/L)
    		</td>
    		<td>
    			 <input type="text" id="sangue14" name="elettrolitiPotassio" maxlength="10" size="10"/>
    		</td>
        </tr>
        
         <tr class='odd'>
    		<td>
    			Rapporto Na/K
    		</td>
    		<td>
    			 <input type="text" id="sangue15" name="elettrolitiRapportoNaK" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Cloro (mEq/L)
    		</td>
    		<td>
    			 <input type="text" id="sangue16" name="elettrolitiCloro" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Rapporto Ca/P
    		</td>
    		<td>
    			 <input type="text" id="sangue17" name="elettrolitiRapportoCaP" maxlength="10" size="10"/>
    		</td>
        </tr>
        
         <tr class='even'>
    		<td>
    			Prodotto Ca*P
    		</td>
    		<td>
    			 <input type="text" id="sangue18" name="elettrolitiProdottoCaP" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Cloro corretto (mEq/L)
    		</td>
    		<td>
    			 <input type="text" id="sangue18" name="elettrolitiCloroCorretto" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Fe (&#181;g/dl)
    		</td>
    		<td>
    			 <input type="text" id="sangue19" name="elettrolitiFe" maxlength="10" size="10"/>
    		</td>
        </tr>
        
         <tr class='odd'>
    		<td>
    			Magnesio (mg/dl)
    		</td>
    		<td>
    			 <input type="text" id="sangue20" name="elettrolitiMagnesio" maxlength="10" size="10"/>
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
    			 <input type="text" id="sangue21" name="emogasArteriosoPh" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			TCO2 ((mEq/L))
    		</td>
    		<td>
    			 <input type="text" id="sangue22" name="emogasArteriosoTco2" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			HCO3 (mEq/L)
    		</td>
    		<td>
    			 <input type="text" id="sangue23" name="emogasArteriosoHco3" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			pCO2 (mmHg)
    		</td>
    		<td>
    			 <input type="text" id="sangue24" name="emogasArteriosoPco2" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			pO2 (mmHg)
    		</td>
    		<td>
    			 <input type="text" id="sangue25" name="emogasArteriosoPo2" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Anion Gap (mEq/L)
    		</td>
    		<td>
    			 <input type="text" id="sangue26" name="emogasArteriosoAnionGap" maxlength="10" size="10"/>
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
    			 <input type="text" id="sangue27" name="emogasVenosoPh" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			TCO2 (mEq/L)
    		</td>
    		<td>
    			 <input type="text" id="sangue28" name="emogasVenosoTco2" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			HCO3 (mEq/L)
    		</td>
    		<td>
    			 <input type="text" id="sangue29" name="emogasVenosoHco3" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			pCO2 (mmHg)
    		</td>
    		<td>
    			 <input type="text" id="sangue30" name="emogasVenosoPco2" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			pO2 (mmHg)
    		</td>
    		<td>
    			 <input type="text" id="sangue31" name="emogasVenosoPo2" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Anion Gap (mEq/L)
    		</td>
    		<td>
    			 <input type="text" id="sangue32" name="emogasVenosoAnionGap" maxlength="10" size="10"/>
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
    			 <input type="text" id="sangue33" name="enzimaticiAst" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			ALT (IU/L)
    		</td>
    		<td>
    			 <input type="text" id="sangue34" name="enzimaticiAlt" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			ALP (IU/L)
    		</td>
    		<td>
    			 <input type="text" id="sangue35" name="enzimaticiAlp" maxlength="10" size="10"/>
    		</td>
        </tr>
        
         <tr class='odd'>
    		<td>
    			GGT (IU/L)
    		</td>
    		<td>
    			 <input type="text" id="sangue36" name="enzimaticiGgt" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			CPK (IU/L)
    		</td>
    		<td>
    			 <input type="text" id="sangue37" name="enzimaticiCpk" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			LDH (IU/L)
    		</td>
    		<td>
    			 <input type="text" id="sangue38" name="enzimaticiLdh" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Amilasi (IU/L)
    		</td>
    		<td>
    			 <input type="text" id="sangue39" name="enzimaticiAmilasi" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Lipasi (IU/L)
    		</td>
    		<td>
    			 <input type="text" id="sangue40" name="enzimaticiLipasi" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Fruttosamina (mcmol/l)
    		</td>
    		<td>
    			 <input type="text" id="sangue41" name="enzimaticiFruttosamina" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Trigliceridi (mg/dl)
    		</td>
    		<td>
    			 <input type="text" id="sangue42" name="enzimaticiTrigliceridi" maxlength="10" size="10"/>
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
    			 <input type="text" id="sangue43" name="elettroforesiAlbumine" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Alfa1 (g/dl)
    		</td>
    		<td>
    			 <input type="text" id="sangue44" name="elettroforesiAlfa1" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Alfa2 (g/dl)
    		</td>
    		<td>
    			 <input type="text" id="sangue45" name="elettroforesiAlfa2" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Beta (g/dl)
    		</td>
    		<td>
    			 <input type="text" id="sangue46" name="elettroforesiBeta" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Gamma (g/dl)
    		</td>
    		<td>
    			 <input type="text" id="sangue47" name="elettroforesiGamma" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Globuline (%)
    		</td>
    		<td>
    			 <input type="text" id="sangue48" name="elettroforesiGlobuline" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Rapporto A:G
    		</td>
    		<td>
    			 <input type="text" id="sangue49" name="elettroforesiRapportoAG" maxlength="10" size="10"/>
    		</td>
        </tr>
	
		
		
		<tr >
        	<th colspan="2">
        		Acidi Biliari
        	</th>        	
        </tr>
	
	
		<tr class='even'>
    		<td>
    			Acidi Biliari (mcmol/l)
    		</td>
    		<td>
    			 <input type="text" id="sangue50" name="acidiBiliari" maxlength="10" size="10"/>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Acidi Biliari Postp. (mcmol/l)
    		</td>
    		<td>
    			 <input type="text" id="sangue51" name="acidiBiliariPostp" maxlength="10" size="10"/>
    		</td>
        </tr>
	
	
        
       
         
		
        <tr class='even'>
        	<td>
        		<font color="red">* </font> Campi obbligatori
        	</td>
    		<td>    			
    			<input type="submit" value="Aggiungi" />
    			<input type="button" value="Annulla" onclick="attendere(), location.href='vam.cc.Detail.us'">
    			
    		</td>
        </tr>
	</table>
</form>