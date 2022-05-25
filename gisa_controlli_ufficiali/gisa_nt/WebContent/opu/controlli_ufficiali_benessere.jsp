<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<%
 
boolean isLineaAllevamento = false; 
String benessere = (String) request.getAttribute("shedaBenessere");
System.out.println("scheda ben "+benessere);
if (benessere!=null && benessere.equals("esiste"))
	isLineaAllevamento = true;

if (isLineaAllevamento) {


	boolean invioBenessereAnimale = false ;
	for(Piano pm :TicketDetails.getPianoMonitoraggio()) {
		if (pm.getCodice_interno().equals("982") || pm.getCodice_interno().equals("983") || ( pm.getDescrizione().toLowerCase().contains("benessere animale") || pm.getDescrizione().toLowerCase().contains("20 piano") || pm.getDescrizione().toLowerCase().contains("benessere animale") || pm.getDescrizione().toLowerCase().contains("49 piano"))){
			invioBenessereAnimale = true ;
		}
	}
%>

<!-- <dhv:permission name="allevamenti-allevamenti-ba-view"> -->
<%-- <% --%>
<%-- if (invioBenessereAnimale==true && TicketDetails.getClosed()!=null && ( TicketDetails.getEsito_import()==null || (TicketDetails.getEsito_import()!= null && TicketDetails.getEsito_import().equalsIgnoreCase("ko")))) {%>  --%>
<!-- 	<center> -->
<%-- 	<input type="button" onclick="location.href='AllevamentiVigilanza.do?command=SendControlloBenessere&tipo=puntuale&id=<%=TicketDetails.getId() %>'" value="Invia Al Ministero"> --%>
<!-- 	</center> -->
<%-- 	<%	 } %> --%>
<!-- </dhv:permission> -->
		
		<%-- INCLUSIONE DETTAGLIO CONTROLLO UFFICIALE--%>
		<%if (TicketDetails.getEsito_import()!=null){ %>
<center>
	<table cellpadding="4" cellspacing="0" width="100%" class="details">
			<tr>
				<th colspan="2">Esito Invio Ministero</th>
			</tr>
			<tr class="containerBody">
				<td nowrap class="formLabel">Esito</td>
				<td><%=TicketDetails.getEsito_import() %></td>
			</tr>
			<tr class="containerBody">
				<td nowrap class="formLabel">Data Invio</td>
				<td><%=TicketDetails.getData_import() %></td>
			</tr>
			<tr class="containerBody">
				<td nowrap class="formLabel">Descrizione Errore</td>
				<td><%=(TicketDetails.getDescrizione_errore()!= null && !"".equals(TicketDetails.getDescrizione_errore())? TicketDetails.getDescrizione_errore() : "" ) %></td>
			</tr>
		</table>
		</center>
		<%} %>
		
		<%} %>
	