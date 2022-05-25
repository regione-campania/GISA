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
<jsp:useBean id="Animale" class="org.aspcfs.modules.anagrafe_animali.base.Animale" scope="request"/>
<jsp:useBean id="LookupTipoInformazione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="LookupMotivoCorrezione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Utente" class="org.aspcfs.modules.admin.base.User" scope="request"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SpecieList" class="org.aspcfs.utils.web.LookupList" scope="request" />


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
<td><center><b><label class="titolo"><U>Modulo (rev. 1) per la gestione, nel sistema BDU, degli<BR/> <br/>ERRATA CORRIGE RIGUARDANTI <br/><br/> CANCELLAZIONI/MODIFICHE DATI NEL SISTEMA BDU </U></label></b></center>
</td>

</tr>
</table>


<table cellpadding="5" style="border-collapse: collapse" width="100%">
<col width="30%">
<tr><td class="blue" colspan="4" align="center">IL SOTTOSCRITTO</td></tr>
<tr><td class="blue" colspan="2">NOME</td><td class="layout" colspan="2"><%=Utente.getContact().getNameFirst() %>  <%=Utente.getContact().getNameLast() %> </td></tr>
<tr><td class="blue" colspan="2">ASL</td><td class="layout" colspan="2"><%=AslList.getSelectedValue(Utente.getSiteId()) %> </td></tr>
<%-- <td class="blue">STRUTTURA DI APPARTENENZA</td><td class="layout"><%=toHtml(Utente.getStrutturaAppartenenza()) %> </td></tr> --%>
<tr><td class="blue" colspan="2">IDENTIFICATO IN BDU DALLA LOGIN</td><td class="layout" colspan="2"><%=Utente.getUsername() %> </td></tr>
<tr><td class="blue" colspan="4" align="center">RELATIVAMENTE AI DATI DELL'ANIMALE IDENTIFICATO DA</td></tr>
<tr><td class="blue">MICROCHIP</td><td class="layout"><%=Animale.getMicrochip() %> </td> <td class="blue">SPECIE</td><td class="layout"><%=SpecieList.getSelectedValue(Animale.getIdSpecie()) %> </td></tr>
<tr><td class="blue">PROPRIETARIO</td><td class="layout"><%=Animale.getProprietario().getRagioneSociale() %> </td> <td class="blue">DETENTORE</td><td class="layout"><%=Animale.getDetentore().getRagioneSociale() %> </td></tr>
<tr><td class="blue" colspan="4" align="center">RICHIEDE</td></tr>
<tr><td class="layout" colspan="4" align="center">LA MODIFICA DELLE INFORMAZIONI PRESENTI NEL SISTEMA BDU SECONDO QUANTO SPECIFICATO NEL SEGUENTE MODULO</td></tr>
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
<tr><td class="blue">NUM TELEFONO: </td><td class="layout" colspan="2"><%=toHtml(Richiesta.getTelefono()) %> </td></tr>
<tr><td class="blue">EMAIL: </td><td class="layout" colspan="2"><%=toHtml(Richiesta.getMail()) %> </td></tr>
<tr><td class="blue">NOTE:</td> <td class="layout"><%=toHtml(Richiesta.getNote()) %></td></tr>
</table>

<br/><br/>

<b>Data: <i><%=toDateasString(Richiesta.getData()) %></i></b><br/><br/>