<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="DatiAMR" class="org.aspcf.modules.controlliufficiali.base.DatiAMR" scope="request"/>
<jsp:useBean id="Response" class="java.lang.String" scope="request"/>

<%@ include file="../initPage.jsp" %>

<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

 <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
	
<% if (Response != null && !Response.equals("")) { %>
<table width="100%">
<tr style="background:yellow"><th colspan="2">ESITO INVIO</th></tr>
<tr><td><%=Response %>

<%if (Response.toUpperCase().contains("SEDE PRODUTTIVA INESISTENTE")){ %>
<BR/>Assicurarsi di aver inserito un Num. Registrazione Provenienza presente in BDN.
<% } %>

</td></tr>
</table>
<br/>
<% } %>	
	
	
<table class="details" width="100%">
<tr><th colspan="2">DATI AMR</th></tr>

<tr><td>ID CONTROLLO</td> <td> <%=TicketDetails.getId() %> </td> </tr>
<tr><td>NUMERO VERBALE AMR</td> <td><%=toHtml(DatiAMR.getNumVerbaleAMR()) %></td></tr>
<tr><td>CODICE FISCALE VETERINARIO PRELEVATORE</td> <td><%=toHtml(DatiAMR.getCodiceFiscaleVeterinario()) %></td></tr>
<tr><td>ORA INIZIO PRELIEVO CAMPIONE</td> <td><%=toHtml(DatiAMR.getOraInizioPrelievo()) %></td></tr>
<tr><td>ORA FINE PRELIEVO CAMPIONE</td> <td><%=toHtml(DatiAMR.getOraFinePrelievo()) %></td></tr>
<tr><td>CODICE FISCALE RAPPRESENTANTE</td> <td><%=toHtml(DatiAMR.getCodiceFiscaleRappresentante()) %></td></tr>
<tr><td>TELEFONO DELLA DITTA</td> <td><%=toHtml(DatiAMR.getTelefono()) %></td></tr>

<%if (TicketDetails.getTipologia_operatore()==2000) { //SOLO SE SINTESIS (MACELLO)%>
<tr><td>ID FISCALE PROPRIETARIO</td> <td><%=toHtml(DatiAMR.getIdFiscaleProprietario()) %></td></tr>
<tr><td>RAGIONE SOCIALE AZIENDA</td> <td><%=toHtml(DatiAMR.getRagioneSociale()) %></td></tr>
<tr><td>INDIRIZZO DELL'AZIENDA</td> <td><%=toHtml(DatiAMR.getIndirizzo()) %></td></tr>
<% } %>

<tr><td>NUM REGISTRAZIONE PROVENIENZA</td> <td><%=toHtml(DatiAMR.getNumRegistrazioneProvenienza()) %></td></tr>
<tr><td>LOCALE/RECINTO CAMPIONATO</td> <td><%=toHtml(DatiAMR.getLocale()) %></td></tr>
<tr><td>DATA DI ACCASAMENTO</td> <td><%=toHtml(DatiAMR.getDataAccasamento()) %></td></tr>
<tr><td>CAPACITA DI MACELLAZIONE</td> <td><%=toHtml(DatiAMR.getCapacita()) %></td></tr>

<% if (DatiAMR.getDataInvio()==null) { %>
<tr><td colspan="2"><center>
<input type="button" value="MODIFICA DATI" onClick="loadModalWindow(); window.location.href='PrintReportVigilanza.do?command=AggiungiAMR&idControllo=<%=TicketDetails.getId()%>'"/>
<input type="button" value="INVIA A SINVSA" onClick="if (confirm('INVIARE A SINVSA?')) { loadModalWindow(); window.location.href='PrintReportVigilanza.do?command=InviaAMR&idControllo=<%=TicketDetails.getId()%>'}"/>
</center></td></tr>
<% }  else {%>
<tr><td>DATA INVIO</td> <td><%=toDateasString(DatiAMR.getDataInvio()) %></td></tr>
<% } %>
<tr><td colspan="2"><center><input type="button" value="STAMPA DOCUMENTO ACCOMPAGNAMENTO CAMPIONE AMR" onClick="openRichiestaPDFAMR('<%=TicketDetails.getId() %>')"/></center></td></tr>
</table>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>
