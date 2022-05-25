<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us"%>


<c:set var="numEsamiSangue" value='${cc.esameSangues.size()}'/>	
         	<tr><td colspan="${numEsamiSangue +1 }">
         		<h3>EMATOCHIMICI</h3>
         	</td></tr>
        	<tr>
    			<td>
    				<h3>BUN (mg/dl)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.ematochimiciBun}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Bilirubina (mg/dl)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.ematochimiciBilirubinaTotale}
	    			</td>
	    		</c:forEach>
    		</tr>
			<tr>
    			<td>
    				<h3>Creatinina (mg/dl)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.ematochimiciCreatinina}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Glucosio (mg/dl)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.ematochimiciGlucosio}
	    			</td>
	    		</c:forEach>
    		</tr>
         	<tr>
    			<td>
    				<h3>Colesterolo (mg/dl)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.ematochimiciColesterolo}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Proteine Totali (g/dl)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.ematochimiciProteineTotali}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Albumine (g/dl)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.ematochimiciAlbumine}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Globuline (g/dl)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.ematochimiciGlobuline}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Globuline (g/dl)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.ematochimiciGlobuline}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Rapporto A:G (g/dl)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.ematochimiciRapportoAG}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<!--                                            -->
    		<tr><td colspan="${numEsamiSangue +1 }">
         		<h3>ELETTROLITI</h3>
         	<tr>
    			<td>
    				<h3>Calcio (mg/dl)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.elettrolitiCalcio}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Calcio corretto (mg/dl)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.elettrolitiCalcioCorretto}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Fosforo (mg/dl)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.elettrolitiFosforo}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Sodio (mEq/L)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.elettrolitiSodio}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Potassio (mEq/L)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.elettrolitiPotassio}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Rapporto Na/K</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.elettrolitiRapportoNaK}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Cloro (mEq/L)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.elettrolitiCloro}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Rapporto Ca/P</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.elettrolitiRapportoCaP}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Prodotto Ca*P</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.elettrolitiProdottoCaP}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Cloro corretto (mEq/L)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.elettrolitiCloroCorretto}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Fe (µg/dl)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.elettrolitiFe}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Magnesio (mg/dl)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.elettrolitiMagnesio}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<!--  ****************************************** -->
    		<tr><td colspan="${numEsamiSangue +1 }">
         		<h3>EMOGAS ARTERIOSO</h3>
         	</td></tr>
         	<tr>
    			<td>
    				<h3>PH</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.emogasArteriosoPh}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>TCO2 ((mEq/L))</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.emogasArteriosoTco2}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>HCO3 ((mEq/L))</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.emogasArteriosoHco3}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>pCO2 (mmHg)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.emogasArteriosoPco2}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>pO2 (mmHg)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.emogasArteriosoPo2}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Anion Gap (mEq/L)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.emogasArteriosoAnionGap}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<!--  ****************************************** -->
    		<tr><td colspan="${numEsamiSangue +1 }">
         		<h3>EMOGAS VENOSO</h3>
         		</td></tr>
         	<tr>
    			<td>
    				<h3>PH</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.emogasVenosoPh}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>TCO2 ((mEq/L))</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.emogasVenosoTco2}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>HCO3 ((mEq/L))</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.emogasVenosoHco3}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>pCO2 (mmHg)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.emogasVenosoPco2}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>pO2 (mmHg)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.emogasVenosoPo2}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Anion Gap (mEq/L)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.emogasVenosoAnionGap}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<!--  ********************************************* -->
    		<tr><td colspan="${numEsamiSangue +1 }">
         		<h3>ENZIMATICI</h3>
         	</td></tr>
         	<tr>
    			<td>
    				<h3>AST (IU/L)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.enzimaticiAst}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>ALT (IU/L)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.enzimaticiAlt}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>ALP (IU/L)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.enzimaticiAlp}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>GGT (IU/L)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.enzimaticiGgt}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>CPK (IU/L)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.enzimaticiCpk}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>LDH (IU/L)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.enzimaticiLdh}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Amilasi (IU/L)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.enzimaticiAmilasi}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Lipasi (IU/L)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.enzimaticiLipasi}
	    			</td>
	    		</c:forEach>
    		</tr>
    		
    		<tr>
    			<td>
    				<h3>Fruttosamina (mcmol/l)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.enzimaticiFruttosamina}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Trigliceridi (mg/dl) </h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.enzimaticiTrigliceridi}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<!--  ********************************************* -->
    		<tr><td colspan="${numEsamiSangue +1 }">
         		<h3>ELETTROFORESI</h3>
         		</td></tr>
         	<tr>
    			<td>
    				<h3>Albumine (%)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.elettroforesiAlbumine}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Alfa1 (g/dl)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.elettroforesiAlfa1}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Alfa2 (g/dl)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.elettroforesiAlfa2}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Beta (g/dl)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.elettroforesiBeta}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Gamma (g/dl)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.elettroforesiGamma}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Globuline (%) </h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.elettroforesiGlobuline}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Rapporto A:G</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.elettroforesiRapportoAG}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<!--  ********************************************* -->
    		<tr><td colspan="${numEsamiSangue +1 }">
         		<h3>ACIDI BILIARI</h3>
         		</td></tr>
         	<tr>
    			<td>
    				<h3>Acidi Biliari (mcmol/l)</h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.acidiBiliari}
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td>
    				<h3>Acidi Biliari Postp. (mcmol/l) </h3>
    			</td>
    			
    			<c:forEach items="${cc.esameSangues}" var="es" >
	    			<td>
	    				${es.acidiBiliariPostp}
	    			</td>
	    		</c:forEach>
    		</tr>