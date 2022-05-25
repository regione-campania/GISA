<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.modules.mu.operazioni.base.VisitaPMSemplificata"%>
<%@ include file="include.jsp"%>
<table width="100%" border="0" cellpadding="2" cellspacing="2"
	class="details" style="border: 1px solid black">
	 <%
              VisitaPMSemplificata visita = (dettaglioCapo.getDettagliMacellazione()).getVp();
              %>
	<tr>
              		<th colspan="3"><strong>Macellazione</strong></th>
            	</tr>
	            <tr class="containerBody">
	              <td class="formLabel" >Progressivo</td>
	              <td >
	              	<%=visita.getProgressivoMacellazionePm() %>
	              </td>
	            </tr>
	            <tr class="containerBody">
	              <td class="formLabel" >Tipo</td>
	              <td colspan="2"><%=TipiMacellazione.getSelectedValue(visita.getIdTipoMacellazionePm()) %></td>
	            </tr>
	            
	
	<tr>
		<th colspan="2"><strong>Visita Post Mortem</strong></th>
	</tr>

	<tr class="containerBody">
		<td class="formLabel">Data Macellazione</td>
		<td><%=toDateasString(visita.getDataVisitaPm()) %></td>
	</tr>


 <tr class="containerBody">
                <td class="formLabel">Esito</td>
                <td><%=EsitiVpm.getSelectedValue(visita.getIdEsitoPm()) %>
				</td>               
            </tr>
            

<!-- 	<tr class="containerBody">
		<td class="formLabel">Data Ricezione Esito</td>
		<td> <input readonly type="text" name="vpm_data_esito" id="vpm_data_esito" size="10" value="" />
		 <a href="#" onClick="cal19.select(document.forms[0].vpm_data_esito,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a></td>
	</tr> -->

	<tr>
		<th colspan="2">Veterinari addetti al controllo</th>
	</tr>
	<%
		HashMap<String, ArrayList<Contact>> listaVeterinari = (HashMap<String, ArrayList<Contact>>) request
				.getAttribute("listaVeterinari");
	%>
<dhv:evaluate if="<%=(visita.getIdVeterinario1Pm() > 0) %>">
	<tr>
		<td colspan="2">1.				<%
					for (String gruppo : listaVeterinari.keySet()) {
				%>
				
				<%
					for (Contact vet : listaVeterinari.get(gruppo)) {
						if (vet.getUserId() == visita.getIdVeterinario1Pm()){
				%>
				<%=vet.getNameLast()%>
				<%
						break;}}
				%>
				<%
					}
				%>
		</td>
	</tr>
</dhv:evaluate>

<dhv:evaluate if="<%=(visita.getIdVeterinario2Pm() > 0) %>">
	<tr>
		<td colspan="2">2.
				<%
					for (String gruppo : listaVeterinari.keySet()) {
				%>
				
				<%
					for (Contact vet : listaVeterinari.get(gruppo)) {
						if (vet.getUserId() == visita.getIdVeterinario2Pm()){
				%>
				<%=vet.getNameLast()%>
				<%
						break;}}
				%>
				<%
					}
				%>
	
		</td>
	</tr>
</dhv:evaluate>

<dhv:evaluate if="<%=(visita.getIdVeterinario3Pm() > 0) %>">
	<tr>
		<td colspan="2">3				<%
					for (String gruppo : listaVeterinari.keySet()) {
				%>
				
				<%
					for (Contact vet : listaVeterinari.get(gruppo)) {
						if (vet.getUserId() == visita.getIdVeterinario3Pm()){
				%>
				<%=vet.getNameLast()%>
				<%
						break;}}
				%>
				<%
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
