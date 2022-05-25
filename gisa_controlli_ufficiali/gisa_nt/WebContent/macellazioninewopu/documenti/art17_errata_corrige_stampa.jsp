<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@page import="java.net.InetAddress"%>
  <%@ page import="java.util.*"%>
    <%@page import="org.aspcfs.modules.accounts.base.OrganizationAddress"%>
 <jsp:useBean id="OrgDetails" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="Partita" class="org.aspcfs.modules.macellazioninewopu.base.Partita" scope="request"/>
 <jsp:useBean id="aslList" class="org.aspcfs.utils.web.LookupList"	scope="request" />
 <jsp:useBean id="specieList" class="org.aspcfs.utils.web.LookupList" scope="request" />
  <jsp:useBean id="userIp" class="java.lang.String" scope="request"/>
   <jsp:useBean id="userName" class="java.lang.String" scope="request"/>
    <jsp:useBean id="timeNow" class="java.lang.String" scope="request"/>
    <jsp:useBean id="definitivoDocumentale" class="java.lang.String" scope="request"/>
  <jsp:useBean id="ErrataCorrige" class="org.aspcfs.modules.macellazioninewopu.base.Art17ErrataCorrige" scope="request"/>
   
     <jsp:useBean id="nomeMacello" class="java.lang.String" scope="request"/>
<jsp:useBean id="comuneMacello" class="java.lang.String" scope="request"/>
<jsp:useBean id="approvalNumber" class="java.lang.String" scope="request"/>
<jsp:useBean id="aslMacello" class="java.lang.String" scope="request"/>

<%@page import="org.aspcfs.modules.contacts.base.Contact"%>
<%@page import="org.aspcfs.modules.macellazioninewopu.base.DestinatarioCarni"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../../initPage.jsp" %>
<link rel="stylesheet" documentale_url="" href="css/moduli_print.css" type="text/css" media="print" />
<link rel="stylesheet" type="text/css" media="screen" documentale_url="" href="css/moduli_screen.css">

<style>
.boxIdDocumento {
	position: relative;
	left: 5px;
	top: 1px;
	border: 0.5px solid black;
	width: 60px;
	height: 20px;
	margin-top: 0px;
	text-align: center;
	padding-top: 10px;
	font-size: 8px;
}
.boxOrigineDocumento {
	text-align: left;
	font-size: 9px;
}
</style>

<%int z = 0; %>
<!-- INIT DOCUMENTALE -->
	<%@ include file="/gestione_documenti/initDocumentale.jsp" %>
<!-- FINE INIT DOCUMENTALE -->

<html>
<body>
<table width="100%"">
<col width="33%"> <col width="33%">
<tr>
<td>
<div class="boxIdDocumento"></div><br/>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>
<i><%=ErrataCorrige.getIpUtente() %> - <%=ErrataCorrige.getNomeUtente() %></i>
</td>
<td></td>
<td><div align="right"><img style="text-decoration: none;" height="80" documentale_url="" src="gestione_documenti/schede/images/<%=aslList.getSelectedValue(OrgDetails.getSiteId()).toLowerCase() %>.jpg" /></div></td>
</tr>

</table>
<br/>

<center><b>Regione Campania<br/>
Azienda Sanitaria Locale <%=aslList.getSelectedValue(OrgDetails.getSiteId())%><br/>
Servizio Veterinario<br/><br/>
ISPEZIONE DELLE CARNI<br/><br/>
<font size="5px"><U>ERRATA CORRIGE PER ART.17 (R.D. 20/12/1928, N. 3298) *</U></font></b></center>
<br/><br/>

<center>
Il sottoscritto <%=ErrataCorrige.getSottoscritto() %>, relativamente agli art.17 
<br/>
<%=ErrataCorrige.getRiferimentoArt17()%> 
<br/>
elaborati e rilasciati presso il macello <%=nomeMacello %> del comune di <%=comuneMacello %> , per la data di macellazione <%=toDateasString(Partita.getDataSessioneMacellazione()) %> 
<br/><br/>
<b>DICHIARA CHE</b></center><br/><br/>
È stata erroneamente digitata l'informazione riguardante i campi della partita con numero <b><u><%=Partita.getCd_partita() %></u></b>:<br/><br/>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<col width="33%"><col width="33%">
<tr><th style="border:1px solid black;">Dati</th>
<th style="border:1px solid black;">Dati errati</th>
<th style="border:1px solid black;">Dati corretti</th></tr>

<tr>
<td style="border:1px solid black;"><% if (ErrataCorrige.isNumeroModificato()) {%>[X]<%} %> Il numero della partita</td>
<td style="border:1px solid black;"><%= (ErrataCorrige.isNumeroModificato()) ? ErrataCorrige.getNumeroErrato() : ""  %>&nbsp;</td>
<td style="border:1px solid black;"><%= (ErrataCorrige.isNumeroModificato()) ? ErrataCorrige.getNumeroCorretto() : ""  %>&nbsp;</td>
</tr>
<tr>
<td style="border:1px solid black;"><% if (ErrataCorrige.isMod4Modificato()) {%>[X]<%} %> Il riferimento al Mod. 4 è</td>
<td style="border:1px solid black;"><%= (ErrataCorrige.isMod4Modificato()) ? ErrataCorrige.getMod4Errato() : ""  %>&nbsp;</td>
<td style="border:1px solid black;"><%= (ErrataCorrige.isMod4Modificato()) ? ErrataCorrige.getMod4Corretto() : ""  %>&nbsp;</td>
</tr>
<tr>
<td style="border:1px solid black;"><% if (ErrataCorrige.isVeterinario1Modificato()) {%>[X]<%} %> Veterinario 1</td>
<td style="border:1px solid black;"><%= (ErrataCorrige.isVeterinario1Modificato()) ? ErrataCorrige.getVeterinario1Errato() : ""  %>&nbsp;</td>
<td style="border:1px solid black;"><%= (ErrataCorrige.isVeterinario1Modificato()) ? ErrataCorrige.getVeterinario1Corretto() : ""  %>&nbsp;</td>
</tr>
<tr>
<td style="border:1px solid black;"><% if (ErrataCorrige.isVeterinario2Modificato()) {%>[X]<%} %> Veterinario 2</td>
<td style="border:1px solid black;"><%= (ErrataCorrige.isVeterinario2Modificato()) ? ErrataCorrige.getVeterinario2Errato() : ""  %>&nbsp;</td>
<td style="border:1px solid black;"><%= (ErrataCorrige.isVeterinario2Modificato()) ? ErrataCorrige.getVeterinario2Corretto() : ""  %>&nbsp;</td>
</tr>
<tr>
<td style="border:1px solid black;"><% if (ErrataCorrige.isVeterinario3Modificato()) {%>[X]<%} %> Veterinario 3</td>
<td style="border:1px solid black;"><%= (ErrataCorrige.isVeterinario3Modificato()) ? ErrataCorrige.getVeterinario3Errato() : ""  %>&nbsp;</td>
<td style="border:1px solid black;"><%= (ErrataCorrige.isVeterinario3Modificato()) ? ErrataCorrige.getVeterinario3Corretto() : ""  %>&nbsp;</td>
</tr>
<% if (ErrataCorrige.isDestinatario1Modificato()){ %>
<tr>
<td style="border:1px solid black;">[X] Destinatario 1</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario1nomeErrato()%> &nbsp;</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario1nomeCorretto()%>&nbsp;</td>
</tr>
<% } %>
<% if (ErrataCorrige.isDestinatario2Modificato()){ %>
<tr>
<td style="border:1px solid black;">[X] Destinatario 2</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario2nomeErrato()%> &nbsp;</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario2nomeCorretto()%>&nbsp;</td>
</tr>
<% } %>
<% if (ErrataCorrige.isDestinatario3Modificato()){ %>
<tr>
<td style="border:1px solid black;">[X] Destinatario 3</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario3nomeErrato()%> &nbsp;</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario3nomeCorretto()%>&nbsp;</td>
</tr>
<% } %>
<% if (ErrataCorrige.isDestinatario4Modificato()){ %>
<tr>
<td style="border:1px solid black;">[X] Destinatario 4</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario4nomeErrato()%> &nbsp;</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario4nomeCorretto()%>&nbsp;</td>
</tr>
<% } %>
<% if (ErrataCorrige.isDestinatario5Modificato()){ %>
<tr>
<td style="border:1px solid black;">[X] Destinatario 5</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario5nomeErrato()%> &nbsp;</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario5nomeCorretto()%>&nbsp;</td>
</tr>
<% } %>
<% if (ErrataCorrige.isDestinatario6Modificato()){ %>
<tr>
<td style="border:1px solid black;">[X] Destinatario 6</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario6nomeErrato()%> &nbsp;</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario6nomeCorretto()%>&nbsp;</td>
</tr>
<% } %>
<% if (ErrataCorrige.isDestinatario7Modificato()){ %>
<tr>
<td style="border:1px solid black;">[X] Destinatario 7</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario7nomeErrato()%> &nbsp;</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario7nomeCorretto()%>&nbsp;</td>
</tr>
<% } %>
<% if (ErrataCorrige.isDestinatario8Modificato()){ %>
<tr>
<td style="border:1px solid black;">[X] Destinatario 8</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario8nomeErrato()%> &nbsp;</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario8nomeCorretto()%>&nbsp;</td>
</tr>
<% } %>
<% if (ErrataCorrige.isDestinatario9Modificato()){ %>
<tr>
<td style="border:1px solid black;">[X] Destinatario 9</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario9nomeErrato()%> &nbsp;</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario9nomeCorretto()%>&nbsp;</td>
</tr>
<% } %>
<% if (ErrataCorrige.isDestinatario10Modificato()){ %>
<tr>
<td style="border:1px solid black;">[X] Destinatario 10</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario10nomeErrato()%> &nbsp;</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario10nomeCorretto()%>&nbsp;</td>
</tr>
<% } %>
<% if (ErrataCorrige.isDestinatario11Modificato()){ %>
<tr>
<td style="border:1px solid black;">[X] Destinatario 11</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario11nomeErrato()%> &nbsp;</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario11nomeCorretto()%>&nbsp;</td>
</tr>
<% } %>
<% if (ErrataCorrige.isDestinatario12Modificato()){ %>
<tr>
<td style="border:1px solid black;">[X] Destinatario 12</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario12nomeErrato()%> &nbsp;</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario12nomeCorretto()%>&nbsp;</td>
</tr>
<% } %>
<% if (ErrataCorrige.isDestinatario13Modificato()){ %>
<tr>
<td style="border:1px solid black;">[X] Destinatario 13</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario13nomeErrato()%> &nbsp;</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario13nomeCorretto()%>&nbsp;</td>
</tr>
<% } %>
<% if (ErrataCorrige.isDestinatario14Modificato()){ %>
<tr>
<td style="border:1px solid black;">[X] Destinatario 14</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario14nomeErrato()%> &nbsp;</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario14nomeCorretto()%>&nbsp;</td>
</tr>
<% } %>
<% if (ErrataCorrige.isDestinatario15Modificato()){ %>
<tr>
<td style="border:1px solid black;">[X] Destinatario 15</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario15nomeErrato()%> &nbsp;</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario15nomeCorretto()%>&nbsp;</td>
</tr>
<% } %>
<% if (ErrataCorrige.isDestinatario16Modificato()){ %>
<tr>
<td style="border:1px solid black;">[X] Destinatario 16</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario16nomeErrato()%> &nbsp;</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario16nomeCorretto()%>&nbsp;</td>
</tr>
<% } %>
<% if (ErrataCorrige.isDestinatario17Modificato()){ %>
<tr>
<td style="border:1px solid black;">[X] Destinatario 17</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario17nomeErrato()%> &nbsp;</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario17nomeCorretto()%>&nbsp;</td>
</tr>
<% } %>
<% if (ErrataCorrige.isDestinatario18Modificato()){ %>
<tr>
<td style="border:1px solid black;">[X] Destinatario 18</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario18nomeErrato()%> &nbsp;</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario18nomeCorretto()%>&nbsp;</td>
</tr>
<% } %>
<% if (ErrataCorrige.isDestinatario19Modificato()){ %>
<tr>
<td style="border:1px solid black;">[X] Destinatario 19</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario19nomeErrato()%> &nbsp;</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario19nomeCorretto()%>&nbsp;</td>
</tr>
<% } %>
<% if (ErrataCorrige.isDestinatario20Modificato()){ %>
<tr>
<td style="border:1px solid black;">[X] Destinatario 20</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario20nomeErrato()%> &nbsp;</td>
<td style="border:1px solid black;"><%= ErrataCorrige.getDestinatario20nomeCorretto()%>&nbsp;</td>
</tr>
<% } %>
<tr>
<td style="border:1px solid black;"><% if (ErrataCorrige.isAltroModificato()) {%>[X]<%} %> Altro</td>
<td style="border:1px solid black;"><%= (ErrataCorrige.isAltroModificato()) ? ErrataCorrige.getAltroErrato() : ""  %>&nbsp;</td>
<td style="border:1px solid black;"><%= (ErrataCorrige.isAltroModificato()) ? ErrataCorrige.getAltroCorretto() : ""  %>&nbsp;</td>
</tr>
</table>
<br/>
<b>Motivo della correzione:</b> <%=ErrataCorrige.getMotivo() %> <br/><br/>

<table width="100%">
<col width="50%">
<tr>
<td><b>Data</b> <br/>
<%=toDateWithTimeasString(ErrataCorrige.getEntered()) %></td>
<td><div align="right">Il veterinario ufficiale dell'ASL <%=aslList.getSelectedValue(aslMacello)%></div></td>
</table>
<br/><br/><br/><br/>
     * <i>Il presente modulo rappresenta una notifica documentale. Eventuali correzioni sui dati dovranno essere effettuate separatamente.</i>
   
</body>
</html>