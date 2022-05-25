<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page
	import="org.aspcfs.modules.registrazioniAnimali.base.EventoInserimentoMicrochip,org.aspcfs.modules.opu.base.*"%>
<%@page
	import="org.aspcfs.modules.registrazioniAnimali.base.EventoInserimentoMicrochip"%>
<jsp:useBean id="tipoSoggettoSterilizz"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="veterinariChippatoriList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="evento"
	class="org.aspcfs.modules.registrazioniAnimali.base.Evento"
	scope="request" />

<%
	EventoInserimentoMicrochip eventoF = (EventoInserimentoMicrochip) evento;
%>
<%@ include file="../initPage.jsp"%>
<%@ include file="../initPopupMenu.jsp"%>


<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="pagedList">
	<th colspan="2">Dettagli della registrazione di inserimento
	microchip</th>

	<tr>
		<td><b><dhv:label name="">Data dell'inserimento</dhv:label></b></td>
		<td><%=toDateasString(eventoF.getDataInserimentoMicrochip())%>&nbsp;</td>
	</tr>
	<tr>
		<td><b><dhv:label name="">Numero microchip</dhv:label></b></td>
		<td><%=eventoF.getNumeroMicrochipAssegnato()%></td>
	</tr>
	<tr>
		<td><b><dhv:label name="">Veterinario chippatore</dhv:label></b></td>
		<td><%=veterinariChippatoriList.getSelectedValue(eventoF.getIdVeterinarioPrivatoInserimentoMicrochip())%></td>
	</tr>
</table>