<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="operazioneScelta" class="java.lang.String" scope="request" />

<jsp:useBean id="StabilimentoOpu" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<%@ include file="../initPage.jsp" %>

<table style="width: 37%">
<%
	if("cambioTitolarita".equalsIgnoreCase(operazioneScelta))
	{
	%>
	<tr>
		<td>PARTITA IVA</td>
		<td>
	
			<%-- <input type="text" value = "<%=toHtml(StabilimentoOpu.getOperatore().getPartitaIva()) %>" <%if(StabilimentoOpu.getOperatore().getTipo_impresa()!=9){ %>class="required" <%} %>min="11" maxlength="11" id="partitaIvaVariazione" name="partitaIvaVariazione" placeholder="Inserire la partita Iva" size="11"  onblur="rimuoviSpazi(this)"/> --%>
<!-- <font color="red">Attenzione. Indicare la coppia Partita IVA / Numero registrazione dell'impresa cedente.</font> -->
		
		<%=toHtml(StabilimentoOpu.getOperatore().getPartitaIva()) %>&nbsp;<font color="red" style="position: relative; left : 50px;">  Partita IVA impresa cui si subentra</font>
		<input type="hidden" name="partitaIvaVariazione" id="partitaIvaVariazione" />
		</td>
	</tr>
	<%} %>

	<tr>
		<td>NUMERO REGISTRAZIONE</td>
		<td>
			<%-- <input type="text" value = "<%=toHtml(StabilimentoOpu.getNumero_registrazione()) %>"  id="numeroRegistrazioneVariazione" name="numeroRegistrazioneVariazione" placeholder="Inserire il numero registrazione" size="55" onblur="rimuoviSpazi(this)" /> --%>
			<%=toHtml(StabilimentoOpu.getNumero_registrazione()) %>  
			<input type="hidden" name ="numeroRegistrazioneVariazione" id="numeroRegistrazioneVariazione" value="<%=toHtml(StabilimentoOpu.getNumero_registrazione()) %>" />
		</td>
	</tr>
</table>

 