<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us"%>


<c:set var="numEsamiSangue" value='5'/>	
         	<tr><td colspan="${numEsamiSangue +1 }">
         		<h3>EMATOCHIMICI</h3>
         	</td></tr>
        	<tr>
    			<td width="15%">
    				<h3>BUN (mg/dl)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    				
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Bilirubina (mg/dl)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
			<tr>
    			<td width="15%">
    				<h3>Creatinina (mg/dl)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Glucosio (mg/dl)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
         	<tr>
    			<td width="15%">
    				<h3>Colesterolo (mg/dl)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    				
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Proteine Totali (g/dl)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Albumine (g/dl)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Globuline (g/dl)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Globuline (g/dl)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Rapporto A:G (g/dl)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<!--                                            -->
    		<tr><td colspan="${numEsamiSangue +1 }">
         		<h3>ELETTROLITI</h3>
         	<tr>
    			<td width="15%">
    				<h3>Calcio (mg/dl)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Calcio corretto (mg/dl)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Fosforo (mg/dl)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    				
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Sodio (mEq/L)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    				
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Potassio (mEq/L)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    				
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Rapporto Na/K</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Cloro (mEq/L)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    				
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Rapporto Ca/P</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Prodotto Ca*P</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Cloro corretto (mEq/L)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Fe (µg/dl)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Magnesio (mg/dl)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<!--  ****************************************** -->
    		<tr><td colspan="${numEsamiSangue +1 }">
         		<h3>EMOGAS ARTERIOSO</h3>
         	</td></tr>
         	<tr>
    			<td width="15%">
    				<h3>PH</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>TCO2 ((mEq/L))</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>HCO3 ((mEq/L))</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>pCO2 (mmHg)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>pO2 (mmHg)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    				
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Anion Gap (mEq/L)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<!--  ****************************************** -->
    		<tr><td colspan="${numEsamiSangue +1 }">
         		<h3>EMOGAS VENOSO</h3>
         		</td></tr>
         	<tr>
    			<td width="15%">
    				<h3>PH</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>TCO2 ((mEq/L))</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>HCO3 ((mEq/L))</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>pCO2 (mmHg)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>pO2 (mmHg)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Anion Gap (mEq/L)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<!--  ********************************************* -->
    		<tr><td colspan="${numEsamiSangue +1 }">
         		<h3>ENZIMATICI</h3>
         	</td></tr>
         	<tr>
    			<td width="15%">
    				<h3>AST (IU/L)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>ALT (IU/L)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>ALP (IU/L)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>GGT (IU/L)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>CPK (IU/L)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>LDH (IU/L)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Amilasi (IU/L)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Lipasi (IU/L)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		
    		<tr>
    			<td width="15%">
    				<h3>Fruttosamina (mcmol/l)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Trigliceridi (mg/dl) </h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<!--  ********************************************* -->
    		<tr><td colspan="${numEsamiSangue +1 }">
         		<h3>ELETTROFORESI</h3>
         		</td></tr>
         	<tr>
    			<td width="15%">
    				<h3>Albumine (%)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Alfa1 (g/dl)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Alfa2 (g/dl)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Beta (g/dl)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    		
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Gamma (g/dl)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    		
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Globuline (%) </h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    		
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Rapporto A:G</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<!--  ********************************************* -->
    		<tr><td colspan="${numEsamiSangue +1 }">
         		<h3>ACIDI BILIARI</h3>
         		</td></tr>
         	<tr>
    			<td width="15%">
    				<h3>Acidi Biliari (mcmol/l)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Acidi Biliari Postp. (mcmol/l) </h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>