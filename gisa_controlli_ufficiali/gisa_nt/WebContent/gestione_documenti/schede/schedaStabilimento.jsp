<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import=" org.aspcfs.modules.util.imports.ApplicationProperties"%>
 <%@ page import="org.aspcfs.modules.osm.base.SottoAttivita"%>
<%@page import="java.net.InetAddress"%>
<%@page import="org.aspcfs.modules.lineeattivita.base.*"%>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="schedaDetails" class="org.aspcf.modules.report.util.SchedaImpresa" scope="request"/>
<jsp:useBean id="statoPratica" class="java.lang.String" scope="request"/>
<jsp:useBean id="indirizzoTitolare" class="java.lang.String" scope="request"/>
<jsp:useBean id="linee_attivita" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="categoriaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../../initPage.jsp" %>
<%@ include file="barcode.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Scheda</title>
</head>
<link rel="stylesheet" type="text/css" media="all" documentale_url="" href="gestione_documenti/schede/schede_layout.css" />

<body>

<table width="100%">
<col width="33%"><col width="33%"><col width="33%">
<tr>
<td><div align="left"><img style="text-decoration: none;" width="80" height="80" documentale_url="" src="gestione_documenti/schede/images/regionecampania.jpg" /></div>
<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>

</td>
<td><center><b><label class="titolo">Scheda <%=(schedaDetails.getOperatore()!=null ) ? schedaDetails.getOperatore() : "Impresa" %></label></b></center>
</td>
<td><div align="right"><img style="text-decoration: none;" height="80" documentale_url="" src="gestione_documenti/schede/images/<%=schedaDetails.getAsl().toLowerCase() %>.jpg" /></div>
</td>
</tr>
</table>

<table cellpadding="5" style="border-collapse: collapse">
<col width="33%">
<tr><td colspan="4" class="grey"><b>Informazione primaria</b></td></tr>
<tr><td class="blue" >Ragione sociale</td>
<td class="layout"><%=schedaDetails.getRagioneSociale() %></td></tr>
<tr><td class="blue">Numero Registrazione</td>
<td class="layout">
<%if (schedaDetails.getNumeroRegistrazione()!=null && !schedaDetails.getNumeroRegistrazione().equals("")){ %>
<img src="<%=createBarcodeImage(schedaDetails.getNumeroRegistrazione())%>" />
<%} else { %>
NON DEFINITO
<%} %>
</td></tr>
<tr><td class="blue">Partita IVA</td>
<td class="layout"><%=schedaDetails.getPartitaIva() %></td></tr>
<tr><td class="blue">Codice Fiscale</td>
<td class="layout"><%=(schedaDetails.getCodiceFiscale()!=null) ? schedaDetails.getCodiceFiscale() : "" %></td></tr>
<tr><td class="blue">Domicilio Digitale</td>
<td class="layout"><%=(schedaDetails.getDomicilioDigitale()!=null) ? schedaDetails.getDomicilioDigitale() : "" %></td></tr>
<%if (schedaDetails.getTarga()!= null) { %>
<tr><td class="blue">Targa</td>
<td class="layout"><%=schedaDetails.getTarga() %></td></tr>
<%}  %>
<tr><td class="blue">Data presentazione istanza</td>
<td class="layout"><%=(schedaDetails.getDataPresentazioneDIA()!= null) ? toDateasString(schedaDetails.getDataPresentazioneDIA()) :  toDateasString(schedaDetails.getDataInizioAttivita())%></td></tr>
<tr><td class="blue">Categoria di rischio</td>
<td class="layout"><%= schedaDetails.getCategoriaRischio()%></td></tr>
<tr><td class="blue">Prossimo controllo con la tecnica della sorveglianza</td>
<td class="layout"><%=toDateasString(schedaDetails.getProssimoControllo()) %></td></tr>
<tr><td class="blue">Stato Impresa</td>
<td class="layout"><%=schedaDetails.getStatoImpresa() %></td></tr>
<tr><td class="blue">Stato Pratica</td>
<td class="layout"><%=statoPratica%></td></tr>
<tr><td colspan="4" class="grey"><b>Linee produttive</b></td></tr>

<%
				Iterator iElencoAttivita = linee_attivita.iterator();
				if (iElencoAttivita.hasNext()) {
			%>

<%	while (iElencoAttivita.hasNext()) {
						SottoAttivita thisAttivita = (SottoAttivita) iElencoAttivita.next();
			%>    
    		<tr><td class="blue">Categoria e attività</td> 	<td class="layout">
      				<%= categoriaList.getSelectedValue( thisAttivita.getCodice_sezione() ) %>
      			<%=toHtml(thisAttivita.getAttivita())%>      				
      			</td></tr>
      			<%} }%>
<tr><td colspan="4" class="grey"><b>Titolare o Legale Rappresentante</b></td></tr>
<tr><td class="blue">Codice Fiscale</td>
<td class="layout"> 	<%= (schedaDetails.getCodiceFiscaleRappresentante()!=null) ? schedaDetails.getCodiceFiscaleRappresentante() : ""%>&nbsp; </td></tr>
<tr><td class="blue">Nome</td>
<td class="layout"><%= (schedaDetails.getNomeRappresentante()!=null) ? schedaDetails.getNomeRappresentante() : "" %>&nbsp; </td></tr>
<tr><td class="blue">Cognome</td>
<td class="layout">	<%=(schedaDetails.getCognomeRappresentante()!=null) ? schedaDetails.getCognomeRappresentante() : "" %>&nbsp; </td></tr>
<tr><td class="blue">Luogo e data di nascita</td>
<td class="layout">	<%= (schedaDetails.getComuneNascitaRappresentante()!=null) ? schedaDetails.getComuneNascitaRappresentante() : ""%>&nbsp;     <%= toDateasString(schedaDetails.getDataNascitaRappresentante())%></td></tr>
<tr><td class="blue">Email</td>
<td class="layout"><%= (schedaDetails.getMailRappresentante()!=null) ? schedaDetails.getMailRappresentante() : "" %>&nbsp; </td></tr>
<tr><td class="blue">Telefono</td>
<td class="layout">	<%= (schedaDetails.getTelefonoRappresentante()!=null) ? schedaDetails.getTelefonoRappresentante() : "" %>&nbsp; </td></tr>
<tr><td class="blue">Fax</td>
<td class="layout"><%= ( schedaDetails.getFaxRappresentante()!=null ) ? schedaDetails.getFaxRappresentante() : ""%>&nbsp; </td></tr>
<tr><td class="blue">Indirizzo</td>
<td class="layout"><%= (indirizzoTitolare!=null && !indirizzoTitolare.equals("null null null")) ? indirizzoTitolare : ""%>&nbsp; </td></tr>
<tr><td colspan="4" class="grey"><b>Indirizzi</b></td></tr>
<tr><td class="blue">Sede Legale</td>
<td class="layout"><%= (schedaDetails.getSedeLegale()!=null) ? schedaDetails.getSedeLegale() : "" %></td></tr>
<tr><td class="blue">Sede Operativa</td>
<td class="layout"><%= (schedaDetails.getSedeOperativa()!=null) ?  schedaDetails.getSedeOperativa()  : "" %></td></tr>

<tr><td colspan="4" class="grey"><b>Controllo della Notifica</b></td></tr>
<tr><td class="blue">Data Completamento D.I.A.</td>
<td class="layout"><%= toDateasString(schedaDetails.getDataCompletamentoDIA()) %></td></tr>
<tr><td class="blue">Esito</td>
<td class="layout"><%= ( schedaDetails.getEsito()!=null) ? schedaDetails.getEsito() : ""%></td></tr>
</table>

</body>
</html>