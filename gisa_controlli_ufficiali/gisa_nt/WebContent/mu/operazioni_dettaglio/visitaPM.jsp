<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.mu.base.Organo"%>
<%@page import="org.aspcfs.modules.mu.operazioni.base.VisitaPM"%>
<%@page import="org.aspcfs.modules.mu.operazioni.base.Macellazione"%>
<%@ include file="include.jsp"%>

<%
	Macellazione thisMacellazione = dettaglioCapo.getDettagliMacellazione();
              
              VisitaPM visita = (dettaglioCapo.getDettagliMacellazione()).getVisitaPm();
%>


<table width="100%" border="0" cellpadding="2" cellspacing="2"
	class="details" style="border: 1px solid black">


	<tr>
		<th colspan="3"><strong>Macellazione</strong></th>
	</tr>
	<tr class="containerBody">
		<td class="formLabel">Progressivo</td>
		<td><%=visita.getProgressivoMacellazionePm()%></td>
	</tr>
	<tr class="containerBody">
		<td class="formLabel">Tipo</td>
		<td colspan="2"><%=TipiMacellazione.getSelectedValue(visita.getIdTipoMacellazionePm())%></td>
	</tr>



	<tr>
		<th colspan="2"><strong>Visita Post Mortem</strong></th>
	</tr>

	<tr class="containerBody">
		<td class="formLabel">Data Macellazione</td>
		<td><%=toDateasString(visita.getDataVisitaPm())%></td>
	</tr>

	<tr class="containerBody">
		<td class="formLabel">Esito</td>
		<td>
			<%-- <%
				Iterator itP = visita.getListaEsito().iterator();
			                	while (itP.hasNext()){
			%> --%> <%=EsitiVpm.getSelectedValue(visita.getIdEsitoPm())%><br>
		<%-- 	<%
				}
			%> --%>


		</td>
	</tr>

	<tr class="containerBody">
		<td class="formLabel">Data Ricezione Esito</td>
		<td>
			<%=
				toDateasString(visita.getDataRicezioneEsitoVisitaPm())
			%>
		</td>
	</tr>



	<tr class="containerBody" id="vpm_riga_patologie">
		<td class="formLabel">Patologie Rilevate</td>

		<td>
			<%
				Iterator itPa = visita.getListaPatologieRilevate().iterator();
			                	while (itPa.hasNext()){
			%> <%=Patologie.getSelectedValue((Integer) itPa.next())%><br>
			<%
				}
			%>



		</td>
	</tr>
	
	
		<tr class="containerBody" id="vpm_riga_patologie">
		<td class="formLabel">Organi</td>

		<td>
			<%
				Iterator itPo = visita.getListaOrgani().iterator();
			                	while (itPo.hasNext()){
			                		Organo thisOrgano = (Organo) itPo.next();
			%><%=Organi.getSelectedValue(thisOrgano.getId_organo())%>: <%=PatologieOrgani.getSelectedValue(thisOrgano.getId_patologia())%><br>
			<%
				}
			%>



		</td>
	</tr>
	
	

	<tr class="containerBody">
		<td class="formLabel">Causa Presunta o Accertata<br />(per
			eventuali patologie)
		</td>
		<td><%=visita.getCausaPresuntaAccertataVisitaPm()%></td>
	</tr>

	<tr class="containerBody">
		<td class="formLabel">Note</td>
		<td><%=visita.getNoteVisitaPm()%></td>

	</tr>

	<tr>
		<th colspan="2">Veterinari addetti al controllo</th>
	</tr>
	<%
		HashMap<String, ArrayList<Contact>> listaVeterinari = (HashMap<String, ArrayList<Contact>>) request
			.getAttribute("listaVeterinari");
	%>
	<dhv:evaluate if="<%=(visita.getIdVeterinario1Pm() > 0)%>">
		<tr>
			<td colspan="2">1. <%
				for (String gruppo : listaVeterinari.keySet()) {
			%> <%
 	for (Contact vet : listaVeterinari.get(gruppo)) {
 				if (vet.getUserId() == visita.getIdVeterinario1Pm()){
 %> <%=vet.getNameLast()%> <%
 	break;}}
 %> <%
 	}
 %>
			</td>
		</tr>
	</dhv:evaluate>

	<dhv:evaluate if="<%=(visita.getIdVeterinario2Pm() > 0)%>">
		<tr>
			<td colspan="2">2. <%
				for (String gruppo : listaVeterinari.keySet()) {
			%> <%
 	for (Contact vet : listaVeterinari.get(gruppo)) {
 				if (vet.getUserId() == visita.getIdVeterinario2Pm()){
 %> <%=vet.getNameLast()%> <%
 	break;}}
 %> <%
 	}
 %>

			</td>
		</tr>
	</dhv:evaluate>

	<dhv:evaluate if="<%=(visita.getIdVeterinario3Pm() > 0)%>">
		<tr>
			<td colspan="2">3 <%
				for (String gruppo : listaVeterinari.keySet()) {
			%> <%
 	for (Contact vet : listaVeterinari.get(gruppo)) {
 				if (vet.getUserId() == visita.getIdVeterinario3Pm()){
 %> <%=vet.getNameLast()%> <%
 	break;}}
 %> <%
 	}
 %>
			</td>
		</tr>

	</dhv:evaluate>



</table>

</br>
<%-- <table id="tableID1" class="details" width="100%" style="border:1px solid black">
	<tr>
		<td><%@include file="campioni_add.jsp"%>
		</td>
	</tr>
	<tr>
		<td><%@include file="tamponi_add.jsp"%>
		</td>
	</tr>
</table> --%>
