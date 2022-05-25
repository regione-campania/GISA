<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<td><c:if
		test="${anagraficaAnimale.animale.lookupSpecie.id!=specie.sinantropo}">
		<h3>MANTELLO</h3>
	</c:if></td>
<td><c:if
		test="${anagraficaAnimale.animale.lookupSpecie.id!=specie.sinantropo}">
  					${anagraficaAnimale.mantello} 
    			</c:if></td>

<td><c:if
		test="${anagraficaAnimale.animale.lookupSpecie.id==specie.cane}">
		<h3>TAGLIA</h3>
	</c:if></td>
<td>${anagraficaAnimale.taglia}</td>
<c:if
	test="${anagraficaAnimale.animale.lookupSpecie.id != specie.sinantropo}">
	<tr><td>
		<h3>Sterilizzazione</h3></td>
		<td>${anagraficaAnimale.sterilizzato}</td>
	</tr>
</c:if>