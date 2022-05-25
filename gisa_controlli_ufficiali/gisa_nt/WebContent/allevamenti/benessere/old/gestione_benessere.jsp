<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
	<%
boolean invioBenessereAnimale = false ;
for(Piano pm :TicketDetails.getPianoMonitoraggio()) {
	
	if (
			(OrgDetails.getSpecieA() == 160 ||  OrgDetails.getSpecieA() == 121 ||  OrgDetails.getSpecieA() == 122 ||  OrgDetails.getSpecieA() == 1211 ||  
			OrgDetails.getSpecieA() == 125 ||  OrgDetails.getSpecieA() == 131 ||  OrgDetails.getSpecieA() == 146 ||  OrgDetails.getSpecieA() == 124 ||  
			OrgDetails.getSpecieA() == 139 ||  OrgDetails.getSpecieA() == 134 ||  OrgDetails.getSpecieA() == 128 ||  OrgDetails.getSpecieA() == 129 || 
			OrgDetails.getSpecieA() == 126 ||  OrgDetails.getSpecieA() == 1461) && (
 (pm.getCodice_interno() != null && pm.getCodice_interno().equals("982")) || (pm.getCodice_interno() != null && pm.getCodice_interno().equals("983")))
 ){
		invioBenessereAnimale = true ;
	}
}
%>

<dhv:permission name="chkbns-chkbns-view">
			<%@ include
				file="/allevamenti/benessere/old/controlli_ufficiali_stampa_chk_bns.jsp"%>
		</dhv:permission>
		<dhv:permission name="ricompila-b11-view">
			<%
if (request.getAttribute("conformitaB11") != null && request.getAttribute("conformitaB11").equals("true")) {	
%>
			<input type="button"
				onclick="location.href='AllevamentiVigilanza.do?command=RicompilaSchedaB11&id=<%=TicketDetails.getId() %>'"
				value="Ricompila Checklist Atto B11 aggiornata">
			<% } %>
		</dhv:permission>
		<dhv:permission name="allevamenti-allevamenti-ba-view">
			<%

if (invioBenessereAnimale==true && TicketDetails.getClosed()!=null && ( TicketDetails.getEsito_import()==null || (TicketDetails.getEsito_import()!= null && (TicketDetails.getEsito_import().equalsIgnoreCase("ko") && !TicketDetails.getDescrizione_errore().toLowerCase().contains("record presente in anagrafe"))))) {
%>
<div align="center">
<font color="red">Invio non possibile per CU pregressi.</font>
</div><br/><br/>
				
			<%	 } %>
		</dhv:permission>
		
		<%if (invioBenessereAnimale && TicketDetails.getClosed()==null && TicketDetails.getEsito_import()!=null && TicketDetails.getEsito_import().equalsIgnoreCase("ko")){ %>
		<script>
		alert("Attenzione. Il controllo e' stato inviato con esito KO con la motivazione riportata in questa pagina. Pertanto il controllo e la lista di riscontro benessere sono stati riaperti.");
		</script>
		<%} %>
		
		<%-- INCLUSIONE DETTAGLIO CONTROLLO UFFICIALE--%>
		<%if (TicketDetails.getEsito_import()!=null){ %>
		<table cellpadding="4" cellspacing="0" width="100%" class="details">
			<tr>
				<th colspan="2">Esito Invio Ministero (Benessere animale)</th>
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
			<tr class="containerBody">
				<td nowrap class="formLabel">Note</td>
				<td>Si ricorda che un invio con esito "KO - Record gia' presente in anagrafe" indica un invio gia' presente in BDN e verra' conteggiato come "OK".</td>
			</tr>
		</table>
		<%} %>
		
		
		<%if (TicketDetails.getEsito_import_b11()!=null){ %>
		<table cellpadding="4" cellspacing="0" width="100%" class="details">
			<tr>
				<th colspan="2">Esito Invio Ministero (Condizionalità Atto B11)</th>
			</tr>
			<tr class="containerBody">
				<td nowrap class="formLabel">Esito</td>
				<td><%=TicketDetails.getEsito_import_b11() %></td>
			</tr>
			<tr class="containerBody">
				<td nowrap class="formLabel">Data Invio</td>
				<td><%=TicketDetails.getData_import_b11() %></td>
			</tr>
			<tr class="containerBody">
				<td nowrap class="formLabel">Descrizione Errore</td>
				<td><%=(TicketDetails.getDescrizione_errore_b11()!= null && !"".equals(TicketDetails.getDescrizione_errore_b11())? TicketDetails.getDescrizione_errore_b11() : "" ) %></td>
			</tr>
			<tr class="containerBody">
				<td nowrap class="formLabel">Note</td>
				<td>Si ricorda che un invio con esito "KO - Record gia' presente in anagrafe" indica un invio gia' presente in BDN e verra' conteggiato come "OK".</td>
			</tr>
		</table>
		<%} %>
		
		
		