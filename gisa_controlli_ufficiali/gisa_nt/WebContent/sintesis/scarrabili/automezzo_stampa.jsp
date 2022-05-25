<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<jsp:useBean id="Stabilimento" class="org.aspcfs.modules.sintesis.base.SintesisStabilimento" scope="request"/>
<jsp:useBean id="Automezzo" class="org.aspcfs.modules.sintesis.base.SintesisAutomezzo" scope="request"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ProvinceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ToponimiList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ page import="org.aspcfs.modules.sintesis.base.*" %>
<%@ page import="org.aspcfs.modules.gestioneml.base.*" %> 

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<link rel="stylesheet" type="text/css" media="screen" documentale_url="" href="sintesis/scarrabili/css/screen.css" />
<link rel="stylesheet" type="text/css" media="print" documentale_url="" href="sintesis/scarrabili/css/print.css" />

<%@ include file="../../initPage.jsp" %> 

<div align="right"><b>Allegato 10</b></div>
<table class="details" width="100%"style="border-collapse: collapse" cellspacing="10">
<tr><td valign="top" align="right" >Spazio per apporre il timbro di protocollo

<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../../hostName.jsp" %></div>

</td> <td valign="top"> Data _____________ Prot _____________</td></tr>
</table>
 
<center>
<b>Servizio Veterinario Sanita' Animale UOS SOA ASL NA2NORD</b><br/>
Oggetto: <b>Comunicazione relativa agli automezzi o ai contenitori riutilizzabili per il trasporto <br/>di sottoprodotti o di prodotti derivati</b> (da presentare in duplice copia, per ogni singolo automezzo)
</center>

[X]<b>comunicazione per acquisizione automezzo/contenitore</b><br/>

<b> 1. Impresa che utilizza l'automezzo/contenitore o impresa che noleggia a terzi l'automezzo/contenitore</b>
<table class="details" width="100%"style="border-collapse: collapse" cellspacing="10">
<tr><td>
Denominazione o ragione sociale <label class="layout"><%=toHtmlFill(Stabilimento.getOperatore().getRagioneSociale(), 120) %></label><br/>
Codice fiscale: <label class="layout"><%=toHtmlFill(Stabilimento.getOperatore().getCodiceFiscaleImpresa(), 120) %></label><br/>
Partita I.V.A. (se diversa da C.F.) <label class="layout"><%=toHtmlFill(Stabilimento.getOperatore().getPartitaIva(), 120) %></label><br/>
con sede operativa nel comune di <label class="layout"><%=toHtmlFill(ComuniList.getSelectedValue(Stabilimento.getIndirizzo().getComune()), 120) %></label> Provincia <label class="layout"><%=toHtmlFill(ProvinceList.getSelectedValue(Stabilimento.getIndirizzo().getIdProvincia()), 10) %></label> <br/>
Via/Piazza  <label class="layout"><%=toHtmlFill(ToponimiList.getSelectedValue(Stabilimento.getIndirizzo().getToponimo()), 10) %></label> <label class="layout"><%=toHtmlFill(Stabilimento.getIndirizzo().getVia(), 120) %></label> N. <label class="layout"><%=toHtmlFill(Stabilimento.getIndirizzo().getCivico(), 10) %></label> CAP <label class="layout"><%=toHtmlFill(Stabilimento.getIndirizzo().getCap(), 15) %></label>
</td></tr>
</table>

<b> 2. Identificativo dell'automezzo/contenitore</b>
<table class="details" width="100%"style="border-collapse: collapse" cellspacing="10">
<tr><td>
Marca <label class="layout"><%=toHtmlFill(Automezzo.getAutomezzoMarca(), 60) %></label> Tipo <label class="layout"><%=toHtmlFill(Automezzo.getAutomezzoTipo(), 60) %></label> Targa <label class="layout"><%=toHtmlFill(Automezzo.getAutomezzoTarga(), 60) %></label> <br/>
</td></tr>
</table>

<b> 3. Luogo di ricovero abituale dell'automezzo/contenitore</b> (se diverso dalla sede operativa dell'impresa)
<table class="details" width="100%"style="border-collapse: collapse" cellspacing="10">
<tr><td>
Via/Corso/Piazza  <label class="layout"><%=toHtmlFill(ToponimiList.getSelectedValue(Automezzo.getRicoveroIdToponimo()), 10) %></label> <label class="layout"><%=toHtmlFill(Automezzo.getRicoveroVia(), 120) %></label> N. civico <label class="layout"><%=toHtmlFill(Automezzo.getRicoveroCivico(), 10) %></label> <br/>
Comune <label class="layout"><%=toHtmlFill(ComuniList.getSelectedValue(Automezzo.getRicoveroIdComune()), 120) %></label> Provincia <label class="layout"><%=toHtmlFill(ProvinceList.getSelectedValue(Automezzo.getRicoveroIdProvincia()), 10) %></label> 
</td></tr>
</table>

<b> 4. Luogo di detenzione del registro delle partite di cui all'art. 22 del Reg. CE/1069/2009</b> (se diverso dalla sede operativa dell'impresa)
<table class="details" width="100%"style="border-collapse: collapse" cellspacing="10">
<tr><td>
Via/Corso/Piazza  <label class="layout"><%=toHtmlFill(ToponimiList.getSelectedValue(Automezzo.getDetenzioneIdToponimo()), 10) %></label> <label class="layout"><%=toHtmlFill(Automezzo.getDetenzioneVia(), 120) %></label> N. civico <label class="layout"><%=toHtmlFill(Automezzo.getDetenzioneCivico(), 10) %></label> <br/>
Comune <label class="layout"><%=toHtmlFill(ComuniList.getSelectedValue(Automezzo.getDetenzioneIdComune()), 120) %></label> Provincia <label class="layout"><%=toHtmlFill(ProvinceList.getSelectedValue(Automezzo.getDetenzioneIdProvincia()), 10) %></label> 
</td></tr>
</table>

<b> 5. Caratteristiche dell'automezzo o del contenitore e materiali trasportati</b>
<table class="details" width="100%"style="border-collapse: collapse" cellspacing="10">
<tr><td>
[<%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoCisternaTrasporto())) ? "X" : " " %>] cisterna adibita al trasporto<br/>
[<%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoVeicoloFreschi())) ? "X" : " " %>] veicolo adibito al trasporto di sottoprodotti freschi [<%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoVeicoloFreschiCat1())) ? "X" : " " %>] categoria 1 [<%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoVeicoloFreschiCat2())) ? "X" : " " %>] categoria 2 [<%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoVeicoloFreschiCat3())) ? "X" : " " %>] categoria 3<br/>
[<%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoVeicoloDerivati())) ? "X" : " " %>] veicolo adibito al trasporto di prodotti derivati [<%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoVeicoloDerivatiCat1())) ? "X" : " " %>] categoria 1 [<%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoVeicoloDerivatiCat2())) ? "X" : " " %>] categoria 2 [<%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoVeicoloDerivatiCat3())) ? "X" : " " %>] categoria 3<br/>
[<%=(Automezzo!=null && "S".equals(Automezzo.getAutomezzoContenitore())) ? "X" : " " %>] contenitore: dimensioni e caratteristiche <label class="layout"><%=toHtmlFill(Automezzo.getAutomezzoContenitoreDimensioni(), 120) %></label>
</td></tr>
</table>

<b> 6. Caratteristiche del trasporto</b>
<table class="details" width="100%"style="border-collapse: collapse" cellspacing="10">
<tr><td>
[<%=(Automezzo!=null && "S".equals(Automezzo.getTrasportoTemperaturaControllata())) ? "X" : " " %>] a temperatura controllata<br/>
[<%=(Automezzo!=null && "S".equals(Automezzo.getTrasportoIsotermico())) ? "X" : " " %>] isotermico [<%=(Automezzo!=null && "S".equals(Automezzo.getTrasportoTemperaturaAmbiente())) ? "X" : " " %>] a temperatura ambiente<br/>
</td></tr>
</table>

<table class="details" width="100%"style="border-collapse: collapse" cellspacing="10">
<tr><td><b><i>NUMERO IDENTIFICATIVO ATTRIBUITO:</i></b> <label class="layout"><%=toHtmlFill(Automezzo.getNumeroIdentificativo(), 120) %></label> <br/><br/>
LUOGO E DATA .................................................. IL RESPONSABILE VETERINARIO .............................................
</td></tr>
</table>
<b>N.B. UNA COPIA DEVE ESSERE CONSERVATA SULL'AUTOMEZZO DURANTE IL TRASPORTO</b>


