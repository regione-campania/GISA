<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>

<jsp:useBean id="listaMedicoEsecutore" class="org.aspcfs.utils.web.LookupList"	scope="request" />
<jsp:useBean id="listaCausale" class="org.aspcfs.utils.web.LookupList"	scope="request" />
<jsp:useBean id="evento"
	class="org.aspcfs.modules.registrazioniAnimali.base.Evento"
	scope="request" />
<jsp:useBean id="veterinariList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="veterinariAslList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />

<%
	EventoAllontanamento eventoAll = (EventoAllontanamento) evento;
%>
<%@ include file="../initPage.jsp"%>
<%@ include file="../initPopupMenu.jsp"%>

<%@page
	import="org.aspcfs.modules.registrazioniAnimali.base.EventoAllontanamento"%>

<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="pagedList">
	<th colspan="2">Dettagli della registrazione di allontanamento  
	<a href="#" onclick="openRichiestaPDF('PrintDocumentoAllontanamento', '<%=eventoAll.getIdAnimale()%>','<%=eventoAll.getSpecieAnimaleId()%>', '-1', '-1', <%=eventoAll.getIdEvento() %>)" id="" target="_self">Documento di allontanamento</a>
	</th>

	<tr>
		<td><b><dhv:label name="">Data dell'allontanamento</dhv:label></b></td>
		<td><%=toDateasString(eventoAll.getDataAllontanamento())%>&nbsp;
		</td>
	</tr>
	
	<tr>
		<td><b><dhv:label name="">Medico esecutore</dhv:label></b></td>
		<td><%=listaMedicoEsecutore.getSelectedValue(eventoAll.getIdMedicoEsecutore())%></td>
	</tr>
	
	<% if (eventoAll.getIdMedicoEsecutore() == EventoAllontanamento.idTipoSoggettoASL) { %>
	<tr>
		<td><b>Veterinario ASL</b></td>
		<td><%=veterinariAslList.getSelectedValue(eventoAll.getIdVeterinarioASL())%></td>
	</tr>
	<% } else if (eventoAll.getIdMedicoEsecutore() == EventoAllontanamento.idTipoSoggettoLLPP) { %>
	<tr>
		<td><b>Veterinario LLPP</b></td>
		<td><%=veterinariList.getSelectedValue(eventoAll.getIdVeterinarioLLPP())%></td>
	</tr>
	<% } %>
	
	<tr>
		<td><b><dhv:label name="">Causale</dhv:label></b></td>
		<td><%=listaCausale.getSelectedValue(eventoAll.getIdCausale())%></td>
	</tr>

</table>