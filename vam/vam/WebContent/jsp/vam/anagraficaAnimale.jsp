<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<tr class='odd'>
    		<td>
    			<c:if test="${(anagraficaAnimale.animale.lookupSpecie.id == specie.sinantropo)}">
					Famiglia/Genere
				</c:if>
				<c:if test="${(anagraficaAnimale.animale.lookupSpecie.id != specie.sinantropo)}">
					Razza
				</c:if>
    		</td>
    		<td colspan="4">
    			${anagraficaAnimale.razza}			   
    		</td>    		
        </tr>
        
        <tr class='even'>
    		<td>
    			Sesso
    		</td>
    		<td colspan="4">
  				${anagraficaAnimale.sesso}			      
    		</td>    		
        </tr>
        
        <c:if test="${anagraficaAnimale.animale.lookupSpecie.id==specie.cane}">
        <tr class='odd'>
    		<td>
    			Taglia
    		</td>
    		<td colspan="4">
  				${anagraficaAnimale.taglia}		
  			</td>   		
        </tr>
        </c:if>
        
        <c:if test="${anagraficaAnimale.animale.lookupSpecie.id!=specie.sinantropo}">
        <tr class='even'>
    		<td>
    			Mantello
    		</td>
    		<td colspan="4">
  				${anagraficaAnimale.mantello}	
    		</td>    		
        </tr>
        </c:if>
        
        <c:if test="${anagraficaAnimale.animale.lookupSpecie.id != specie.sinantropo}">
        <tr class='odd'>
    		<td>
    			Sterilizzazione
    		</td>
    		<td colspan="4">
    			${anagraficaAnimale.sterilizzato}
    		</td>   		
        </tr>
        
         <tr class='even'>
    		<td>
    			Stato attuale
    		</td>
    		<td colspan="4">
				${anagraficaAnimale.statoAttuale}
    		</td>   		
        </tr>
        </c:if>