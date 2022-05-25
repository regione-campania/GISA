<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page
	import="org.aspcfs.modules.registrazioniAnimali.base.EventoRegistrazioneEsitoControlliCommerciali,org.aspcfs.modules.opu.base.*"%>

<jsp:useBean id="evento"
	class="org.aspcfs.modules.registrazioniAnimali.base.Evento"
	scope="request" />
<jsp:useBean id="esitoList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="esitoDocumentaliList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="esitoLabList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="controlliCommercialiDecisione" class="org.aspcfs.utils.web.LookupList"
	scope="request" />



<%
	EventoRegistrazioneEsitoControlliCommerciali eventoF = (EventoRegistrazioneEsitoControlliCommerciali) evento;
%>
<%@ include file="../initPage.jsp"%>
<%@ include file="../initPopupMenu.jsp"%>



<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="pagedList">
	<th colspan="2">Dettagli della registrazione di inserimento
	controlli</th>

	<tr>
		<td><b><dhv:label name="">Data di registrazione degli esiti</dhv:label></b></td>
		<td><%=toDateasString(eventoF.getDataInserimentoControlli())%>&nbsp;
		</td>
	</tr>

	<dhv:evaluate if="<%=eventoF.isFlagPresenzaEsitoControlloIdentita()%>">
		<tr class="containerBody">
			<td valign="top" class="formLabel2"><dhv:label name="">Controllo identità</dhv:label>
			</td>
			<td>Esito <%=esitoList.getSelectedValue(eventoF
							.getIdEsitoControlloIdentita())%> del <%=toDateasString(eventoF.getDataEsitoControlloIdentita())%></td>

		</tr>
	</dhv:evaluate>

	<dhv:evaluate
		if="<%=eventoF.isFlagPresenzaEsitoControlloDocumentale()%>">
		<tr class="containerBody">
			<td valign="top" class="formLabel2"><dhv:label name="">Controllo documentale</dhv:label>
			</td>
			<td>Esito <%=esitoDocumentaliList.getSelectedValue(eventoF
							.getIdEsitoControlloDocumentale())%> del <%=toDateasString(eventoF
									.getDataEsitoControlloDocumentale())%></td>

		</tr>
	</dhv:evaluate>


	<dhv:evaluate if="<%=!eventoF.isFlagPresenzaEsitoControlloFisico()%>">
		<tr class="containerBody">
			<td valign="top" class="formLabel2"><dhv:label name="">Controllo Fisico</dhv:label>
			</td>
			<td>Esito <%=esitoList.getSelectedValue(eventoF
							.getIdEsitoControlloFisico())%> del <%=toDateasString(eventoF.getDataEsitoControlloFisico())%></td>

		</tr>
	</dhv:evaluate>


	<dhv:evaluate
		if="<%=!eventoF.isFlagPresenzaEsitoControlloLaboratorio()%>">
		<tr class="containerBody">
			<td valign="top" class="formLabel2"><dhv:label name="">Controllo Laboratorio</dhv:label>
			</td>
			<td>Esito <%=esitoLabList.getSelectedValue(eventoF
							.getIdEsitoControlloLaboratorio())%> del <%=toDateasString(eventoF
									.getDataEsitoControlloLaboratorio())%></td>

		</tr>
	</dhv:evaluate>




	<tr class="containerBody">
		<td valign="top" class="formLabel2"><dhv:label name="">Decisione finale</dhv:label>
		</td>
		<td><%=controlliCommercialiDecisione.getSelectedValue(eventoF
							.getIdDecisioneFinale())%>
		</td>

	</tr>




</table>


</table>







</table>