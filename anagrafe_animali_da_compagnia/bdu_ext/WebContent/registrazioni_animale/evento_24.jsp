<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="tipoSoggettoSterilizz"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="municipalitaList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="evento"
	class="org.aspcfs.modules.registrazioniAnimali.base.Evento"
	scope="request" />

<%
	EventoCattura eventoF = (EventoCattura) evento;
%>
<%@ include file="../initPage.jsp"%>
<%@ include file="../initPopupMenu.jsp"%>

<%@page
	import="org.aspcfs.modules.registrazioniAnimali.base.*,org.aspcfs.modules.opu.base.*"%>

<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="pagedList">
	<th colspan="2">Dettagli della registrazione di ricattura</th>

	<tr>
		<td><b><dhv:label name="">Data della ricattura</dhv:label></b></td>
		<td><%=toDateasString(eventoF.getDataCattura())%>&nbsp;</td>
	</tr>
	<tr>
		<td width="20%"><b><dhv:label name="">Comune della cattura</dhv:label></b>
		</td>
		<td><%=(eventoF.getIdComuneCattura() > 0) ? comuniList
					.getSelectedValue(eventoF.getIdComuneCattura()) : "N.D"%>

		</td>
	</tr>
	
	    <tr><td><b><dhv:label name="">Municipalità</dhv:label></b></td>
  	<td>
  	<%=(eventoF.getIdMunicipalita() > 0) ?  toHtml(municipalitaList.getSelectedValue(eventoF.getIdMunicipalita())).replace("\uFFFD", "à") : "N.D."%>
  	</td>
     </tr>

<%-- 	<tr>
		<td><b><dhv:label name="">Via cattura</dhv:label></b></td>
		<td><%=(eventoF.getIndirizzoCattura() != null && !("")
							.equals(eventoF.getIndirizzoCattura())) ? eventoF
					.getIndirizzoCattura() : "--"%>
		</td>
	</tr> --%>

	<tr>
		<td><b><dhv:label name="">Verbale cattura</dhv:label></b></td>
		<td><%=(eventoF.getVerbaleCattura() != null) ? eventoF
					.getVerbaleCattura() : "---"%>
		</td>
	</tr>
	<tr>
		<td><b><dhv:label name="">Luogo cattura</dhv:label></b></td>
		<td><%=(eventoF.getLuogoCattura() != null) ? eventoF
					.getLuogoCattura() : "---"%>
		</td>
	</tr>

	<tr>
		<td><b><dhv:label name="">Proprietario/Detentore</dhv:label></b></td>
		<td>
		<%
			Operatore proprietario = eventoF.getProprietario();
			if (proprietario != null) {
				Stabilimento stab = (Stabilimento) (proprietario
						.getListaStabilimenti().get(0));
				LineaProduttiva linea = (LineaProduttiva) (stab
						.getListaLineeProduttive().get(0));
		%> <a
			href="OperatoreAction.do?command=Details&opId=<%=linea.getId()%>"><%=toHtml(proprietario.getRagioneSociale())%></a>
		<%
			} else {
		%> -- <%
			}
		%>/ <%
			Operatore detentore = eventoF.getDetentore();
			if (detentore != null) {
				Stabilimento stab1 = (Stabilimento) (detentore
						.getListaStabilimenti().get(0));
				LineaProduttiva linea1 = (LineaProduttiva) (stab1
						.getListaLineeProduttive().get(0));
		%> <a
			href="OperatoreAction.do?command=Details&opId=<%=linea1.getId()%>"><%=toHtml(detentore.getRagioneSociale())%>&nbsp;</a>
		<%
			} else {
		%> -- <%
			}
		%>
		</td>
		</td>
</table>