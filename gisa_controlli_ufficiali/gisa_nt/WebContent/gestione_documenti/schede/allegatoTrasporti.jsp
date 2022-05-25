<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import=" org.aspcfs.modules.util.imports.ApplicationProperties"%>
 

<%@page import="java.net.InetAddress"%>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="allegatoDetails" class="org.aspcfs.modules.trasportoanimali.base.SchedaAllegato" scope="request"/>
<jsp:useBean id="specieList" class="java.lang.String" scope="request"/>
<jsp:useBean id="tipoAllegato" class="java.lang.String" scope="request"/>
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

<table width="100%" cellpadding="5">
<col width="33%"><col width="33%"><col width="33%">
<tr>
<td><div align="left"><u><label class="titolo">Allegato <%=tipoAllegato %></label></u></div>
<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>
</td>
<td><center><img style="text-decoration: none;" width="80" height="80" documentale_url="" src="gestione_documenti/schede/images/regionecampania.jpg" /></center>
</td>
<td><div align="right"><u><i><label class="titolo">REPUBBLICA ITALIANA</label></i></u></div>
</td>
</tr>
</table> 

<% if (tipoAllegato.equals("C") || tipoAllegato.equals("D")) {%>
<br/><br/><br/>
<table width="100%" cellpadding="5" style="border-collapse: collapse">
<tr><td class="layout">1. AUTORIZZAZIONE DEL TRASPORTATORE (TRANSPORTER AUTHORIZATION) N.<br/>
<br/><br/>
<center><%=allegatoDetails.getAccountNumber() %></center></td></tr></table>

<table width="100%" cellpadding="5" style="border-collapse: collapse">
<col width="50%">
<tr><td class="layout">2. IDENTIFICAZIONE DEL TRASPORTATORE<br/>(TRANSPORTER IDENTIFICATION) <br/>2.1 Ragione sociale (Company name)<br/><br/>
<center><%=allegatoDetails.getRagioneSociale() %></center></td>
<td class="layout">
<% if (tipoAllegato.equals("C")){ %>
TIPO (TYPE) 1<br/>
NON VALIDO (NOT VALID)<br/>
PER LUNGHI VIAGGI<br/>
(FOR LONG JOURNEYS)<br/>
<%} else if (tipoAllegato.equals("D")){ %>
TIPO (TYPE) 2<br/>
VALIDO PER TUTTI I VIAGGI<br/>
COMPRESI LUNGHI VIAGGI<br/>
(Including long journeys)<br/>
<%} %>
</td></tr></table>

<table width="100%" cellpadding="5" style="border-collapse: collapse">
<tr><td class="layout">2.2 Indirizzo (Address)<br/><br/>
<center><%=allegatoDetails.getIndirizzo() %></center></td></tr></table>

<table width="100%" cellpadding="5" style="border-collapse: collapse">
<col width="33%"><col width="33%"><col width="33%">
<tr><td class="layout">2.3 Città (Town)<br/><br/>
<center><%=allegatoDetails.getComune() %></center></td>
<td class="layout">2.4 Codice postale (Postal code) <br/><br/>
<center><%=allegatoDetails.getCap() %></center></td>
<td class="layout">2.5 Stato membro (Member state) <br/><br/>
<center><%=allegatoDetails.getNazione() %></center></td></tr>

<tr><td class="layout">2.6 Telefono (Telephone)<br/><br/>
<center><%=allegatoDetails.getTelefono() %></center></td>
<td class="layout">2.7 Fax <br/><br/>
<center><%=allegatoDetails.getFax() %></center></td>
<td class="layout">2.8 E-mail<br/><br/>
<center><%=(allegatoDetails.getEmail()!=null) ? allegatoDetails.getEmail() : "" %></center></td></tr></table>

<table width="100%" cellpadding="5" style="border-collapse: collapse">
<tr><td class="layout">3. AUTORIZZAZIONE LIMITATA A TALUNI (AUTHORISATION LIMITED TO CERTAIN):<br/><br/>
Tipi di Animali (Types of animals) [ ]  Modi di trasporto (Modes of trasport)  [ ] </td></tr>
<% if (specieList!=null && !specieList.equals("null")){ %>
<tr><td class="layout">Specificare (Specify here): <b><%=specieList %></b> </td></tr>
<%} %>
<tr><td class="layout">Note: <%=(allegatoDetails.getNote()!=null) ? allegatoDetails.getNote() : "" %> </td></tr>
<tr><td class="layout">Data di scadenza (Expiry date): ________________________ </td></tr>
<tr><td class="layout">4. AUTORITA' CHE RILASCIA L'AUTORIZZAZIONE (AUTORITHY ISSUING THE AUTHORISATION)<br/>
4.1 Nome e indirizzo dell'autorita' (Name and address of the autorithy) <br/>
&nbsp;<br/>&nbsp;<br/>&nbsp;<br/>
</td></tr></table>


<table width="100%" cellpadding="5" style="border-collapse: collapse">
<col width="33%"><col width="33%"><col width="33%">
<tr><td class="layout">4.2 Telefono (Telephone)<br/>
&nbsp;<br/>&nbsp;<br/></td>
<td class="layout">4.3 Fax <br/>
&nbsp;<br/>&nbsp;<br/> </td>
<td class="layout">4.4 E-mail<br/>
&nbsp;<br/>&nbsp;<br/></td></tr></table>

<table width="100%" cellpadding="5">
<col width="33%"><col width="33%"><col width="33%">
<tr><td >4.5 Data (Date)<br/>
&nbsp;<br/>______________________<br/></td>
<td>4.6 Luogo (Place)<br/>
&nbsp;<br/>______________________<br/> </td>
<td>4.7 Timbro ufficiale (Official stamp)<br/>
&nbsp;<br/>&nbsp;<br/></td></tr></table>

4.8 Nome e firma del funzionario (Name and signature of the official)<br/>
<br/>

____________________________________
<%} else if (tipoAllegato.equals("H")) { %>
<center><b><label class="titolo1">Autodichiarazione della registrazione come trasportatore "conto proprio" di equidi</label></b></center>
<br/>
<div class="nodott_margin_low">Il sottoscritto </div>
<div class="dott_margin_low">&nbsp;<%=(allegatoDetails.getRagioneSociale() != null) ? allegatoDetails.getRagioneSociale() : ""%></div>
<div class="clear1"></div>
<div class="nodott_margin_low">C.F. </div>
<div class="dott_margin_low">&nbsp;<%=(allegatoDetails.getCodiceFiscaleRappresentante() != null) ? allegatoDetails.getCodiceFiscaleRappresentante() : ""%></div>
<div class="clear1"></div>
<div class="nodott_margin_low">residente in via </div>
<div class="dott_margin_low">&nbsp;<%=(allegatoDetails.getIndirizzo() != null) ? allegatoDetails.getIndirizzo() : ""%></div>
<div class="clear1"></div>
<div class="nodott_margin_low">Comune </div>
<div class="dott_margin_low">&nbsp;<%=(allegatoDetails.getComune() != null) ? allegatoDetails.getComune() : ""%> (<%=(allegatoDetails.getProvincia() != null) ? allegatoDetails.getProvincia() : ""%>)</div>
<br/><br/><br/>
in quanto proprietario/detentore di:<br/><br/>
<%=(allegatoDetails.getD1() != null) ? "[X]" : "[&nbsp;&nbsp;]"%> equidi allevati per diporto<br/><br/>
<%=(allegatoDetails.getD2() != null) ? "[X]" : "[&nbsp;&nbsp;]"%> equidi registrati o comunque non da macello <br/><br/>
<%=(allegatoDetails.getD3() != null) ? "[X]" : "[&nbsp;&nbsp;]"%> equidi comunque trasportati senza finalità economica <br/><br/> 
<br/> 
<div class="nodott_margin_low">presso la propria abitazione/allevamento </div>
<div class="dott_margin_low">&nbsp;<%=(allegatoDetails.getBanca() != null) ? allegatoDetails.getBanca() : ""%></div>
<div class="clear1"></div>
<div class="nodott_margin_low">sito in via </div>
<div class="dott_margin_low">&nbsp;<%=(allegatoDetails.getSede() != null) ? allegatoDetails.getSede() : ""%></div>
<div class="clear1"></div>
<div class="nodott_margin_low">Comune </div>
<div class="dott_margin_low">&nbsp;<%=(allegatoDetails.getCittaSede() != null) ? allegatoDetails.getCittaSede() : ""%> (<%=(allegatoDetails.getStateSede() != null) ? allegatoDetails.getStateSede() : ""%>)</div>
<div class="clear1"></div><br/>
consapevole delle sanzioni penali previste dall'art. 26 ella legge 4 gennaio 1968 n. 15, per le ipotesi di falsità in atti e dichiarazioni mendaci ivi indicate (artt. 482, 483, 489, 495 e 496 CP), nonché della sanzione della decadenza dai benefici conseguiti a seguito di un provvedimento adottato in base ad una dichiarazione rivelatasi successivamente mendace,
<br/><center><b>DICHIARA</b></center>
<b>
<ul>
<li>di essere a conoscenza dei principi espressi dagli articoli 3 e 27 del Reg. (CE) 1/2005 in materia di protezione degli animali durante il trasporto</li>
<li>di trasportare esclusivamente i propri equidi, con il proprio trailer/van <u><%=(allegatoDetails.getMezzo() != null) ? allegatoDetails.getMezzo() : "_____________"%></u>  targa <u><%=(allegatoDetails.getTarga() != null) ? allegatoDetails.getTarga() : "_____________"%></u></li>
<li>che il sopraindicato mezzo di trasporto ha pavimento e pareti ben connesse, lavabili e disinfettabili e raccordati tra loro in modo da impedire la fuoriuscita di liquami ed ha le seguenti dimensioni interne:
<div class="clear1"></div>
<br/>
<center><table width="40%" style="border-collapse: collapse">
<col width="50%">
<tr><td class="layout">SUPERFICIE</td> <td class="layout"></td></tr>
<tr><td class="layout">ALTEZZA</td> <td class="layout"></td></tr>
</table></center>
<br/>
</li>
<li>di essere stato registrato nell'apposito registro dei trasportatori "conto proprio" di equidi presso il Servizio Veterinario dell'Azienda Sanitaria <br/>
<u><%=(allegatoDetails.getAsl() != null) ? allegatoDetails.getAsl() : "_____________"%></u> in data <u><%=(allegatoDetails.getDate1() != null) ? toDateasString(allegatoDetails.getDate1()) : "_____________"%></u></li>

</ul>
Si impegna altresì a attuare una procedura di controllo e manutenzione periodica per il mantenimento dei requisiti del mezzo di trasporto e di aggiornare presso il Servizio Veterinario in cui è stato registrato come 

trasportatore di equidi "conto proprio"


, ogni eventuale variazione inerente alla propria registrazione ed ai mezzi utilizzati per il trasporto di animali vivi.</b>
<br/><br/>
<table width="100%">
<col width="33%"><col width="33%"><col width="33%">
<tr><td>Luogo, data</td>
<td>Firma</td>
<td>Timbro/Firma</td></tr>
<tr><td><br/><br/>_____________________</td>
<td><br/><br/>____________________</td>
<td></td>
</tr>
</table>

<br/><br/><br/>

Visto, il Servizio Veterinario<br/><br/>
Luogo, data<br/><br/><br/><br/>
__________________________________

<br/><br/><br/><br/>
<label class="piccolo">* indicare le caratteristiche e generalita' di ogni mezzo utilizzato per il trasporto di animali vivi</label>


<%} else if (tipoAllegato.equals("G")) { %>
<center><b><label class="titolo1">Autodichiarazione della registrazione come produttore primario ai sensi del Reg. (CE) 852/2004</label></b></center>
<br/>
<div class="nodott_margin_low">Il sottoscritto titolare/responsabile/conduttore dell'azienda di allevamento</div><br/>
<div class="dott_margin_low">&nbsp;<%=(allegatoDetails.getRagioneSociale() != null) ? allegatoDetails.getRagioneSociale() : ""%></div>
<div class="clear1"></div>
<div class="nodott_margin_low">codice azienda </div>
<div class="dott_margin_low">&nbsp;<%=(allegatoDetails.getAccountNumber() != null) ? allegatoDetails.getAccountNumber() : ""%></div>
<div class="clear1"></div>
<div class="nodott_margin_low">via </div>
<div class="dott_margin_low">&nbsp;<%=(allegatoDetails.getIndirizzo() != null) ? allegatoDetails.getIndirizzo() : ""%></div>
<div class="clear1"></div>
<div class="nodott_margin_low">Comune </div>
<div class="dott_margin_low">&nbsp;<%=(allegatoDetails.getComune() != null) ? allegatoDetails.getComune() : ""%> (<%=(allegatoDetails.getProvincia() != null) ? allegatoDetails.getProvincia() : ""%>)</div>
<br/><br/><br/>
consapevole delle sanzioni penali previste dall'art. 26 ella legge 4 gennaio 1968 n. 15, per le ipotesi di falsità in atti e dichiarazioni mendaci ivi indicate (artt. 482, 483, 489, 495 e 496 CP), nonchè della sanzione della decadenza dai benefici conseguiti a seguito di un provvedimento adottato in base ad una dichiarazione rivelatasi successivamente mendace,
<br/><center><b>DICHIARA</b></center>
<b>
<ul>
<li>di essere a conoscenza dei principi espressi dagli articoli 3 e 27 del Reg. (CE) 1/2005 in materia di protezione degli animali durante il trasporto</li>
<li>di trasportare esclusivamente i propri propri animali, appartenenti alle seguenti specie/categorie <u><%=specieList %></u><br/>
, esclusivamente con il proprio mezzo di trasporto* marca <u><%=(allegatoDetails.getMezzo() != null) ? allegatoDetails.getMezzo() : "_____________"%></u> targa <u><%=(allegatoDetails.getTarga() != null) ? allegatoDetails.getTarga() : "_____________"%></u> soddisfando i requisiti del sopraccitato articolo 3 del Reg. (CE) 1/2005</li>
<li>che il sopraindicato mezzo di trasporto ha pavimento e pareti ben connesse, lavabili e disinfettabili e raccordati tra loro in modo da impedire la fuoriuscita di liquami ed ha le seguenti dimensioni interne:
<div class="clear1"></div>
<br/>
<center><table width="100%" style="border-collapse: collapse">
<col width="15%"><col width="15%"><col width="15%"><col width="15%"><col width="15%"><col width="15%">
<tr><td class="layout"></td>
<td class="layout">1 PIANO</td>
<td class="layout">2 PIANO</td>
<td class="layout">3 PIANO</td>
<td class="layout">4 PIANO</td>
<td class="layout">TOTALE</td>
</tr>

<tr><td class="layout">SUPERFICIE</td>
<td class="layout"></td>
<td class="layout"></td>
<td class="layout"></td>
<td class="layout"></td>
<td class="layout"></td>
</tr>

<tr><td class="layout">ALTEZZA</td>
<td class="layout"></td>
<td class="layout"></td>
<td class="layout"></td>
<td class="layout"></td>
<td class="layout"></td>
</tr>


</table></center>
<br/>
</li>
<li>di essere stato registrato ai sensi dell'Allegato I, del Reg. (CE) 852/2004 quale produttore primario presso il Servizio Veterinario dell'Azienda Sanitaria<br/>
<u><%=(allegatoDetails.getAsl() != null) ? allegatoDetails.getAsl() : "_____________"%></u> in data <u><%=(allegatoDetails.getDate1() != null) ? toDateasString(allegatoDetails.getDate1()) : "_____________"%></u></li>

</ul>
Si impegna altresì a attuare una procedura di controllo e manutenzione periodica per il mantenimento dei requisiti del mezzo di trasporto e di aggiornare presso il Servizio Veterinario in cui è stato registrato come produttore primario, ogni eventuale variazione inerente alla propria registrazione ed ai mezzi utilizzati per il trasporto di animali vivi.</b>
<br/><br/>
<table width="100%">
<col width="33%"><col width="33%"><col width="33%">
<tr><td>Luogo, data</td>
<td>Firma</td>
<td>Timbro/Firma</td></tr>
<tr><td><br/><br/>_____________________</td>
<td><br/><br/>____________________</td>
<td></td>
</tr>
</table>

<br/><br/><br/>

Visto, il Servizio Veterinario<br/><br/>
Luogo, data<br/><br/><br/><br/>
__________________________________

<br/><br/><br/><br/>
<label class="piccolo">* indicare le caratteristiche e generalita' di ogni mezzo utilizzato per il trasporto di animali vivi</label>


<% } %>


<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>
<br/>


</body>