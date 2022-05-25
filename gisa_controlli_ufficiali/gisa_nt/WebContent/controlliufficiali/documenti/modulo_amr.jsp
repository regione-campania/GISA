<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="DatiAMR" class="org.aspcf.modules.controlliufficiali.base.DatiAMR" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../../initPage.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Scheda</title>
</head>
<link rel="stylesheet" type="text/css" media="all" documentale_url="" href="gestione_documenti/schede/schede_layout.css" />

<body>

<table width="100%">
<!-- <col width="20%"><col width="20%"><col width="20%"><col width="40%"> -->
<col width="33%"><col width="33%"><col width="33%">

<tr>
<td><div align="left"><img style="text-decoration: none;" width="80" height="80" documentale_url="" src="gestione_documenti/schede/images/regionecampania.jpg" /></div>
<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>

</td>
<td><center><b><label class="titolo">SCHEDA CONTROLLO <br/><br/> AMR</label></b></center>
</td>
<!-- <td><div class="boxQRDocumento"></div></td> -->
<td><div align="right"><img style="text-decoration: none;" height="80px" width="200px" documentale_url="" src="gestione_documenti/schede/images/<%=SiteList.getSelectedValue(TicketDetails.getSiteId()).toLowerCase() %>.jpg" /></div></td>
</tr>
</table>


<table cellpadding="5" style="border-collapse: collapse" width="100%">
<col width="33%">

<tr><td class="blue">ID CONTROLLO</td> <td class="layout"> <%=TicketDetails.getId() %> </td> </tr>
<tr><td class="blue">ASL </td> <td class="layout"> <%=SiteList.getSelectedValue(TicketDetails.getSiteId()).toUpperCase() %> </td> </tr>
<tr><td class="blue">NUMERO VERBALE AMR</td> <td class="layout"><%=toHtml(DatiAMR.getNumVerbaleAMR()) %></td></tr>
<tr><td class="blue">CODICE FISCALE VETERINARIO PRELEVATORE</td> <td class="layout"><%=toHtml(DatiAMR.getCodiceFiscaleVeterinario()) %></td></tr>
<tr><td class="blue">ORA INIZIO PRELIEVO CAMPIONE</td> <td class="layout"><%=toHtml(DatiAMR.getOraInizioPrelievo()) %></td></tr>
<tr><td class="blue">ORA FINE PRELIEVO CAMPIONE</td> <td class="layout"><%=toHtml(DatiAMR.getOraFinePrelievo()) %></td></tr>
<tr><td class="blue">CODICE FISCALE RAPPRESENTANTE</td> <td class="layout"><%=toHtml(DatiAMR.getCodiceFiscaleRappresentante()) %></td></tr>
<tr><td class="blue">TELEFONO DELLA DITTA</td> <td class="layout"><%=toHtml(DatiAMR.getTelefono()) %></td></tr>

<%if (TicketDetails.getTipologia_operatore()==2000) { //SOLO SE SINTESIS (MACELLO)%>
<tr><td class="blue">ID FISCALE PROPRIETARIO</td> <td class="layout"><%=toHtml(DatiAMR.getIdFiscaleProprietario()) %></td></tr>
<tr><td class="blue">RAGIONE SOCIALE AZIENDA</td> <td class="layout"><%=toHtml(DatiAMR.getRagioneSociale()) %></td></tr>
<tr><td class="blue">INDIRIZZO DELL'AZIENDA</td> <td class="layout"><%=toHtml(DatiAMR.getIndirizzo()) %></td></tr>
<% } %>
<tr><td class="blue">NUM. REGISTRAZIONE PROVENIENZA</td> <td class="layout"><%=toHtml(DatiAMR.getNumRegistrazioneProvenienza()) %></td></tr>
<tr><td class="blue">LOCALE/RECINTO CAMPIONATO</td> <td class="layout"><%=toHtml(DatiAMR.getLocale()) %></td></tr>
<tr><td class="blue">DATA DI ACCASAMENTO</td> <td class="layout"><%=toHtml(DatiAMR.getDataAccasamento()) %></td></tr>
<tr><td class="blue">CAPACITA DI MACELLAZIONE</td> <td class="layout"><%=toHtml(DatiAMR.getCapacita()) %></td></tr>
<tr><td class="blue">STATO INVIO</td> <td class="layout"> <%= (DatiAMR.getDataInvio()==null) ? "<font color=\"red\">NON INVIATO</font>" : "INVIATO IL "+toDateasString(DatiAMR.getDataInvio()) %> </td> </tr>
</table>
<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>
<br/>

</body>
</html>