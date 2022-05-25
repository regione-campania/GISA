<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ListaStati" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>

<jsp:useBean id="Operatore" class="org.aspcfs.modules.opu.base.Operatore" scope="request"/>

<%@ include file="../initPage.jsp"%>


<%if(StabilimentoDettaglio.getLineaProduttivaPrimaria() != null && StabilimentoDettaglio.getLineaProduttivaPrimaria().getId()==111111) 
{%>
<font color = "red">ATTENZIONE ! Variare la Linea di Attivita </font>
<%} %>


<table width="100%" class="details">
	<tr>
		<th colspan="5">Lista Linee Produttive</strong>
		</th>
	</tr>
	<tr>
<!-- 		<th style="background-color: rgb(204, 255, 153);"><strong>Num. Reg.</strong> -->
<!-- 		</th> -->
		<th style="background-color: rgb(204, 255, 153);"><strong>Attivita</strong>
		</th>
		<th style="background-color: rgb(204, 255, 153);"><strong>Data Inizio</strong>
		</th>
		<th style="background-color: rgb(204, 255, 153);"><strong>Data Fine</strong>
		</th>
		<th style="background-color: rgb(204, 255, 153);">Stato
		</th>
	</tr>
	
	<dhv:evaluate if="<%=(StabilimentoDettaglio.getListaLineeProduttive().size() > 0)%>">
	<%
		Iterator<LineaProduttiva> itLplist = StabilimentoDettaglio.getListaLineeProduttive().iterator();
				int indice = 1;
				while (itLplist.hasNext()) {
					LineaProduttiva lp = itLplist.next();
	%>
		<input type="hidden" name="idLineaProduttivaAdded_Stabilimento" id="idLineaProduttivaAdded_Stabilimento<%=indice%>" value="<%=lp.getId()%>">
			<tr>
<%-- 				<td><%=lp.getNumeroRegistrazione() %></td> --%>
				<td><%=lp.getAttivita() +( (lp.getId() ==111111) ? "-TIPO ATTIVITA :"+lp.getDescrizione_linea_attivita() :"")%></td>
				<td><%=lp.getDataInizioasString() %></td>
				<td><%=lp.getDataFineasString() %></td>
				<td><%=ListaStati.getSelectedValue(lp.getStato()) %></td>
				
			
			
				
			</tr>
			<%
				}
			%>
	</dhv:evaluate>
	 
	
</table>