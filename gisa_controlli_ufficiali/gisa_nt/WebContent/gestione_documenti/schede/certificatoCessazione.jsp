<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import=" org.aspcfs.modules.util.imports.ApplicationProperties"%>
 

<%@page import="java.net.InetAddress"%>
<%@page import="org.aspcfs.modules.lineeattivita.base.*"%>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="schedaDetails" class="org.aspcf.modules.report.util.SchedaImpresa" scope="request"/>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../../initPage.jsp" %>

<%!
	public static String fixValore(String code) {
	if (code ==null || code.equals("null") || code.equals(""))
		return "&nbsp;";
	else
		return code.toUpperCase();
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Scheda</title>
</head>
<link rel="stylesheet" type="text/css" media="all" documentale_url="" href="gestione_documenti/schede/schede_layout.css" />

<style>@media all{
.boxIdDocumento {
	left: 5px;
	top:90px;
	}
.boxOrigineDocumento {
	left: 75px;
	top:85px;
	}
}</style> 
<body>
<table width="100%">
<col width="40%"><col width="60%">
<tr>
<td><div align="left"><img style="text-decoration: none;" height="80px" documentale_url="" src="gestione_documenti/schede/images/<%=schedaDetails.getAsl().toLowerCase() %>.jpg" /></div>
<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div></td>
<td><center><b><label class="titolo2">REGIONE CAMPANIA <br/>  
AZIENDA SANITARIA LOCALE <%=schedaDetails.getAsl()%><br/> 
DIPARTIMENTO DI PREVENZIONE</label></b></center>
<br/>
<table>
<tr><td>&nbsp;&nbsp;&nbsp;AL SIG.</td><td>&nbsp;&nbsp;&nbsp; <%=(schedaDetails.getNomeRappresentante()!=null) ? schedaDetails.getNomeRappresentante() : "" %></td></tr>
<tr><td>&nbsp;&nbsp;&nbsp;AL SINDACO DEL COMUNE DI </td><td>&nbsp;&nbsp;&nbsp; <%=schedaDetails.getComuneSindaco() %></td></tr>
</table>
</td>
</tr>
</table>
<br/>
<center>Oggetto: Certificato Cessazione Attività</center>
<center><u><b>Numero di Registrazione: <%=schedaDetails.getNRegistrazione()%></b></u></center><br/>
<table cellpadding="5" style="border-collapse: collapse" width="100%">
<col width="20%"><col width="80%">
<tr><td class="blue">Ragione sociale</td>
<td class="layout"><%=fixValore(schedaDetails.getRagioneSociale()) %></td></tr>
<tr><td class="blue">Denominazione</td>
<td class="layout"><%=fixValore(schedaDetails.getDenominazione()) %></td></tr>
<tr><td class="blue">Partita IVA</td>
<td class="layout"><%=fixValore(schedaDetails.getPartitaIva()) %></td></tr>
<tr><td class="blue">Amministratore/Titolare</td>
<td class="layout"><%=fixValore(schedaDetails.getNomeRappresentante()) %></td></tr>
<tr><td class="blue">Codice Fiscale</td>
<td class="layout"><%=fixValore(schedaDetails.getCodiceFiscale()) %></td></tr>
<tr><td class="blue">Telefono / Fax</td>
<td class="layout">	<%= fixValore( schedaDetails.getTelefono()) %>&nbsp; </td></tr>
<tr><td class="blue">e-mail</td>
<td class="layout"><%= fixValore( schedaDetails.getEmail())%>&nbsp; </td></tr>
<tr><td class="blue">Stato</td>
<td class="layout"><%=schedaDetails.getStatoImpresa() %></td></tr>
<tr><td colspan="4" class="grey"><b>Sede Legale</b></td></tr>
<tr><td class="blue">Via</td>
<td class="layout"><%= fixValore(schedaDetails.getViaSede()) %></td></tr>
<tr><td class="blue">Comune</td>
<td class="layout"><%= fixValore(schedaDetails.getComuneSede()) %></td></tr>
<tr><td class="blue">Stato</td>
<td class="layout"></td></tr>
<% if (schedaDetails.getIndirizzoMobile()==null) { %>
<tr><td colspan="4" class="grey"><b>Sede Operativa</b></td></tr>
<tr><td class="blue">Via</td>
<td class="layout"><%= fixValore(schedaDetails.getViaSedeOp()) %></td></tr>
<tr><td class="blue">Comune</td>
<td class="layout"><%= fixValore(schedaDetails.getComuneSedeOp()) %></td></tr>
<% } else { %>
<tr><td colspan="4" class="grey"><b>Attività / Struttura mobile</b></td></tr>
<tr><td class="blue">Via</td>
<td class="layout"><%= fixValore(schedaDetails.getIndirizzoMobile()) %></td></tr>
<tr><td class="blue">Comune</td>
<td class="layout"><%= fixValore(schedaDetails.getCittaMobile()) %></td></tr>
<tr><td class="blue">Tipo struttura</td>
<td class="layout"><%= fixValore(schedaDetails.getTipoStruttura()) %></td></tr>
<tr><td class="blue">Targa</td>
<td class="layout"><%= fixValore(schedaDetails.getTarga()) %></td></tr>
<%} %>

</table>

<table cellpadding="5" style="border-collapse: collapse"  width="100%">
<col width="15%"><col width="15%"><col width="15%"><col width="15%"><col width="15%"><col width="15%">
<tr><td colspan="6" class="grey"><b>Locali Funzionalmente Collegati</b></td></tr>
<tr><td class="blue">Locale 1</td>
<td class="layout"><%=fixValore(schedaDetails.getLocaleCollegato1()) %></td>
<td class="blue">Locale 2</td>
<td class="layout"><%=fixValore(schedaDetails.getLocaleCollegato2()) %></td>
<td class="blue">Locale 3</td>
<td class="layout"><%=fixValore(schedaDetails.getLocaleCollegato3()) %></td></tr>
<tr><td class="blue">Via</td>
<td class="layout"><%=fixValore(schedaDetails.getViaLocale1()) %></td>
<td class="blue">Via</td>
<td class="layout"><%=fixValore(schedaDetails.getViaLocale2()) %></td>
<td class="blue">Via</td>
<td class="layout"><%=fixValore(schedaDetails.getViaLocale3()) %></td></tr>
<tr><td class="blue">Comune</td>
<td class="layout"><%=fixValore(schedaDetails.getComuneLocale1())%></td>
<td class="blue">Comune</td>
<td class="layout"><%=fixValore(schedaDetails.getComuneLocale2()) %></td>
<td class="blue">Comune</td>
<td class="layout"><%=fixValore(schedaDetails.getComuneLocale3())%></td></tr>
<tr><td class="blue">Stato</td>
<td class="layout"></td>
<td class="blue">Stato</td>
<td class="layout"></td>
<td class="blue">Stato</td>
<td class="layout"></td></tr>
</table>
<table cellpadding="5" style="border-collapse: collapse" width="100%">
<col width="20%"><col width="80%">
<tr><td colspan="4" class="grey"><b>Codice ISTAT attività principale</b></td></tr>
<tr><td class="blue">n. codice</td>
<td class="layout"><%=fixValore(schedaDetails.getAtecoPrincCodice()) %></td></tr>
<tr><td colspan="4" class="grey"><b>Codici ISTAT attività secondarie</b></td></tr>
<%
ArrayList<LineeAttivita> linee_attivita_secondarie = (ArrayList<LineeAttivita>) request.getAttribute("linee_attivita_secondarie");
int i=0;
if (linee_attivita_secondarie!=null)
for (LineeAttivita linea: linee_attivita_secondarie) {
i++;
%>
<tr><td class="blue">n. codice</td>
<td class="layout">
<% if (!linea.getLinea_attivita().isEmpty()) { %>
			    			    <%= toHtml( linea.getCodice_istat() + " " + linea.getDescrizione_codice_istat()) %> <br/>
			    			    <%= toHtml( linea.getCategoria() + " - " + linea.getLinea_attivita() ) %>&nbsp;
							<% } else { %>
								<%= toHtml( linea.getCodice_istat() + " " + linea.getDescrizione_codice_istat()) %> <br/>
							<%= toHtml( linea.getCategoria() ) %>&nbsp;
			    			<% } %>
			    			</td></tr>
			    			<%
			    		 } 
			    	%>

</table>
<br/><br/><br/><br/><br/>

<div align="left">Data _______________________</div> <div align="right">Timbro e Firma _________________________</div>





</body>
</html>