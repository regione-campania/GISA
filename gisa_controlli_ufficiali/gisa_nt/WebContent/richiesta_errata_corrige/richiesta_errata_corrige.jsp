<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ include file="../../initPage.jsp" %> 
<%@page import="org.aspcfs.modules.richiesteerratacorrige.base.RichiestaErrataCorrigeCampo"%>

<jsp:useBean id="Richiesta" class="org.aspcfs.modules.richiesteerratacorrige.base.RichiestaErrataCorrige" scope="request"/>
<jsp:useBean id="Stabilimento" class="org.aspcfs.modules.ricercaunica.base.RicercaOpu" scope="request"/>
<jsp:useBean id="LookupTipoInformazione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="LookupMotivoCorrezione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Utente" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<link rel="stylesheet" type="text/css" media="all" documentale_url="" href="gestione_documenti/schede/schede_layout.css" />



<table width="100%">
<!-- <col width="20%"><col width="20%"><col width="20%"><col width="40%"> -->
<col width="30%"><col width="73%">

<tr>
<td><div align="left"><img style="text-decoration: none;" width="80" height="80" documentale_url="" src="gestione_documenti/schede/images/regionecampania.jpg" /></div>
<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>

</td>
<td><center><b><label class="titolo"><U>Modulo (rev. 1) per la gestione, nel sistema GISA, degli<BR/> <br/>ERRATA CORRIGE<br/><br/> RIGUARDANTI  GLI STABILIMENTI(*) </U></label></b></center>
</td>

</tr>
</table>


<table cellpadding="5" style="border-collapse: collapse" width="100%">
<col width="30%">
<tr><td class="blue" colspan="4" align="center">IL SOTTOSCRITTO</td></tr>
<tr><td class="blue" colspan="2">NOME</td><td class="layout" colspan="2"><%=Utente.getContact().getNameFirst() %>  <%=Utente.getContact().getNameLast() %> </td></tr>
<tr><td class="blue" colspan="2">ASL</td><td class="layout" colspan="2"><%=AslList.getSelectedValue(Utente.getSiteId()) %> </td></tr>
<%-- <td class="blue">STRUTTURA DI APPARTENENZA</td><td class="layout"><%=toHtml(Utente.getStrutturaAppartenenza()) %> </td></tr> --%>
<tr><td class="blue" colspan="2">IDENTIFICATO IN GISA DALLA LOGIN</td><td class="layout" colspan="2"><%=Utente.getUsername() %> </td></tr>
<tr><td class="blue" colspan="4" align="center">RELATIVAMENTE AI DATI DELLO STABILIMENTO IDENTIFICATO DA</td></tr>
<tr><td class="blue" colspan="2">RAGIONE SOCIALE</td><td class="layout" colspan="2"><%=Stabilimento.getRagioneSociale() %> </td></tr>
<tr><td class="blue" colspan="2">NUMERO REGISTRAZIONE</td><td class="layout" colspan="2"><%= (Stabilimento.getNumeroRegistrazione() != null && !Stabilimento.getNumeroRegistrazione().equals("")) ? Stabilimento.getNumeroRegistrazione() : (Stabilimento.getNumAut() != null && !Stabilimento.getNumAut().equals("")) ?  Stabilimento.getNumAut() : "" %>  </td></tr>
<tr><td class="blue" colspan="4" align="center">RICHIEDE</td></tr>
<tr><td class="layout" colspan="4" align="center">LA MODIFICA DELLE INFORMAZIONI PRESENTI NEL SISTEMA GISA SECONDO QUANTO SPECIFICATO NEL SEGUENTE MODULO</td></tr>
</table>
<br/><br/>
<table cellpadding="5" style="border-collapse: collapse" width="100%">
<tr><th class="blue">Informazione da modificare</th><th class="blue">Dato errato</th><th class="blue">Dato Corretto</th></tr>
<%for (int i = 0; i<Richiesta.getCampi().size(); i++){ 
RichiestaErrataCorrigeCampo campo = (RichiestaErrataCorrigeCampo) Richiesta.getCampi().get(i);
%>
<tr><td class="layout"> <%=LookupTipoInformazione.getSelectedValue(campo.getIdLookupInfoDaModificare()) %> </td>
<td class="layout"><%=campo.getDatoErrato() %></td>
<td class="layout"><%=campo.getDatoCorretto() %></td>
<% } %>
</table>
<br/><br/>
<table cellpadding="5" style="border-collapse: collapse" width="100%">
<col width="30%">
<tr><td class="blue">MOTIVO DELLA CORREZIONE:</td> <td class="layout"><%=LookupMotivoCorrezione.getSelectedValue(Richiesta.getIdLookupMotivoCorrezione()) %></td></tr>
<% if (Richiesta.getIdControllo()>0) {%>
<tr><td class="blue">ID CONTROLLO UFFICIALE</td> <td class="layout"><%=Richiesta.getIdControllo() %></td></tr>
<% } %>
<tr><td class="blue">NUM TELEFONO: </td><td class="layout" colspan="2"><%=toHtml(Richiesta.getTelefono()) %> </td></tr>
<tr><td class="blue">EMAIL: </td><td class="layout" colspan="2"><%=toHtml(Richiesta.getMail()) %> </td></tr>
<tr><td class="blue">NOTE:</td> <td class="layout"><%=toHtml(Richiesta.getNote()) %></td></tr>
</table>

<br/><br/>

<b>Data: <i><%=toDateasString(Richiesta.getData()) %></i></b><br/><br/>

(*) il presente modulo deve essere usato unicamente nei casi in cui la variazione dati non rientri nelle fattispecie previste dalla Delibera Regionale 318/2015 (DISPOSIZIONI PER LE NOTIFICHE ED IL RICONOSCIMENTO DEGLI STABILIMENTI).
<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>
<br/>