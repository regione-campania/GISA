<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<li>
	<b>
		<c:if test="${(animale.lookupSpecie.id == specie.sinantropo)}">
				Famiglia/Genere:
		</c:if>
		<c:if test="${(animale.lookupSpecie.id != specie.sinantropo)}">
			Razza:
		</c:if>
	</b> 
	${anagraficaAnimale.razza}					
</li>
<li>
	<b>
		Sesso:
	</b>
	${anagraficaAnimale.sesso}		
</li>
		
<c:if test="${animale.lookupSpecie.id==specie.cane}">
	<li>
		<b>
			Taglia:
		</b> 
		${anagraficaAnimale.taglia}
	</li>
</c:if>
			
<c:if test="${animale.lookupSpecie.id!=specie.sinantropo}">
	<li>
		<b>
			Mantello:
		</b> 
		${anagraficaAnimale.mantello}
	</li>
</c:if>

<li>
	<b>
		Stato attuale:
	</b> 
	${anagraficaAnimale.statoAttuale }
</li>

<c:if test="${animale.lookupSpecie.id != specie.sinantropo}">
	<li>
		<b>
			Sterilizzazione:
		</b> 
		<c:if test="${anagraficaAnimale.sterilizzato}">
		Sterilizzato
		</c:if>
	</li>
</c:if>