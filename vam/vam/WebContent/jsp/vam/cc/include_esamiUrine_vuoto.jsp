<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us"%>


<c:set var="numEsamiUrine" value='5'/>	
         	<tr><td colspan="${numEsamiUrine +1 }">
         		<h3>ESAME FISICO/CHIMICO</h3>
         	</td></tr>
        	<tr>
    			<td width="15%">
    				<h3>Volume (L/die)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Peso Specifico </h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
			<tr>
    			<td width="15%">
    				<h3>Colore</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
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
    				<h3>Proteine (mg/die)</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Glucosio</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Bilirubina</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    				
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Corpi Chetonici</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Emoglobina</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Nitriti</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Urobilinogeno (unità Erlich.) </h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    				
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Sangue</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<!--                                            -->
    		<tr><td colspan="${numEsamiUrine +1 }">
         		<h3>SEDIMENTO URINARIO</h3>
         	<tr>
    			<td width="15%">
    				<h3>Batteri</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Cellule Epiteliali</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    				
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Cilindri</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    			
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Cristalli</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    				
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Eritrociti</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    				
	    			</td>
	    		</c:forEach>
    		</tr>
    		<tr>
    			<td width="15%">
    				<h3>Leucociti</h3>
    			</td>
    			
    			<c:forEach var="i" begin="1" end="5" >
	    			<td width="15%">
	    				
	    			</td>
	    		</c:forEach>
    		</tr>
    		
    		