<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


<tr class='even'>
	<td>
		<c:if test="${(animale.lookupSpecie.id == specie.sinantropo)}">
			Famiglia/Genere:
		</c:if>
		<c:if test="${(animale.lookupSpecie.id != specie.sinantropo)}">
			Razza:
		</c:if>
	</td>
	<td>					  
		${anagraficaAnimale.razza}				
	</td>
</tr>
<tr class='odd'>
	<td>
		Sesso:
	</td>
	<td>
		${anagraficaAnimale.sesso}
	</td>
</tr>
	<c:if test="${animale.lookupSpecie.id==specie.cane}">
	<tr class='even'>
		<td>
			Taglia:
		</td>
		<td>
			${anagraficaAnimale.taglia}
		</td>
	</tr>
	</c:if>
	<c:if test="${animale.lookupSpecie.id!=specie.sinantropo}">
	<tr class='odd'>
		<td>
			Mantello:
		</td>
		<td>
			${anagraficaAnimale.mantello}
		</td>
	</tr>
	</c:if>	
	<tr class='even'>
		<td>
			Stato attuale:
		</td>
		<td>${anagraficaAnimale.statoAttuale }
		</td>
	</tr>
	<c:if test="${anagraficaAnimale.animale.lookupSpecie.id != specie.sinantropo}">
	<tr class='odd'>
		<td>
			Sterilizzazione:
		</td>
		<td>
			${anagraficaAnimale.sterilizzato }
		</td>
	</tr>
	</c:if>