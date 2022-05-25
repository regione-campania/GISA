<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.allevamenti.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>

<jsp:useBean id="ListaAziende" class="java.util.ArrayList" scope="request"/>

<%@ include file="../initPage.jsp" %>

<font color="red"> Schermata SOLO PER HELP DESK per tentativo di interrogazione ai nuovi WS Elicicoltura.</font><br/><br/>

<table class="details" width="100%">
<tr>
<th>Denominazione</th>
<th>Azienda codice</th>
<th>Tipo Descrizione</th>
<th>Proprietario</th>
<th>Data inizio</th>
<th>Data fine</th>
<th>Specie allevata</th>
<th>Orientamento produttivo</th>
<th>Importa</th>
</tr>

<% for (int i = 0; i<ListaAziende.size(); i++) {
	ElicicolturaAllevamento azienda = (ElicicolturaAllevamento) ListaAziende.get(i);%>
	<tr>
	<td><%=toHtml(azienda.getDenominazione()) %></td>
	<td><%=toHtml(azienda.getAziendaCodice()) %></td>
	<td><%=toHtml(azienda.getTipattDescrizione()) %></td>
	<td><%=toHtml(azienda.getOperCognNome()) %></td>
	<td><%=toHtml(azienda.getDtInizioAttivita()) %></td>
	<td><%=toHtml(azienda.getDtFineAttivita()) %></td>
	
	<td>
	<% for (int j = 0; j<azienda.getDettagliAttivita().size(); j++) {
	ElicicolturaDettagliAttivita dettagli = (ElicicolturaDettagliAttivita) azienda.getDettagliAttivita().get(j);%>
	<%=toHtml(dettagli.getSpecieDescrizione()) %>
	<% } %>
	</td>
	
	<td>
	<% for (int k = 0; k<azienda.getDettagliAttivita().size(); k++) {
	ElicicolturaDettagliAttivita dettagli = (ElicicolturaDettagliAttivita) azienda.getDettagliAttivita().get(k);%>
	<%=toHtml(dettagli.getOriproDescrizione()) %>
	<% } %>
	</td>
		
	<td>
<%-- 	<input type="button" value="IMPORTA" onClick="alert('<%=azienda.getAziendaId()%>')"/> --%>
	</td>
	</tr>
	<% } %>
	</table>	

