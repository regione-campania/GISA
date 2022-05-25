<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>



<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<jsp:useBean id="Stabilimento" class="org.aspcfs.modules.sintesis.base.SintesisStabilimento" scope="request"/>

<jsp:useBean id="TipoImpresaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoSocietaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ToponimiList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ProvinceList" class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="StatiStabilimento" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="StatiLinea" class="org.aspcfs.utils.web.LookupList" scope="request" />

<%@page import="org.aspcfs.modules.sintesis.base.*"%>


<%@ include file="../initPage.jsp" %>

<script>
function modificaDati(id){
	loadModalWindow();
	window.location.href="StabilimentoSintesisAction.do?command=PrepareModificaDati&id="+id;
}

function openStorico(id){
	 window.open('StabilimentoSintesisAction.do?command=StoricoStabilimento&id='+id,'popupSelect',
     'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');

}
function openProdotti(id){
	 window.open('StabilimentoSintesisAction.do?command=ListaProdotti&id='+id,'popupSelect',
    'height=600px,width=600px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');

}

function openProdottiLinea(id){
	 window.open('StabilimentoSintesisAction.do?command=ListaProdottiLinea&idRelazione='+id,'popupSelect',
   'height=600px,width=600px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');

}

function openMercatoLinea(id){
	 window.open('StabilimentoSintesisMercatoAction.do?command=ListaOperatoriMercatoLinea&idRelazione='+id,'popupSelect',
   'height=600px,width=1000px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');

}

function openAutomezziLinea(id){
	 window.open('StabilimentoSintesisAction.do?command=ListaAutomezziLinea&idRelazione='+id,'popupSelect',
  'height=600px,width=1200px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');

}

function refreshDimensioniIframe(iframe){
	$(iframe).height($(iframe).contents().find('html').height());
}
</script>
  
  <%
String nomeContainer = "sintesis";
request.setAttribute("Operatore",Stabilimento.getOperatore());
String param = "altId="+Stabilimento.getAltId();
int idLineaMacello = -1;
int idLineaCaseificio = -1;


for (int j = 0; j<Stabilimento.getLinee().size(); j++){
SintesisRelazioneLineaProduttiva rel = (SintesisRelazioneLineaProduttiva) Stabilimento.getLinee().get(j);

if(rel.getPathCompleto().toLowerCase().contains("sh macello")){
	idLineaMacello=rel.getIdRelazione();
}

if(rel.getCodiceUnivoco().equalsIgnoreCase("MS.B-MS.B90-MS.B90.400")){
	idLineaCaseificio=rel.getIdRelazione();
}

}

%>

<dhv:permission name="opu-view">
<table class="trails" cellspacing="0">
<tr>
<td>
  <dhv:label name=""><a href="OpuStab.do?command=SearchForm" >
  Gestione Anagrafiche SINTESIS </a>-><a  href="RicercaUnica.do?command=Search">
  Elenco stabilimenti</a> -> Dettaglio Stabilimento</dhv:label>
 
</td>
</tr>
</table>
</dhv:permission>

<% boolean isGestoreMacelli = false; %>
<dhv:permission name="gestione-macelli-view">
<% isGestoreMacelli = true; %>
</dhv:permission>

<%@ include file="../../controlliufficiali/diffida_list.jsp" %>

  <dhv:container name="<%=nomeContainer %>" selected="Scheda" object="Operatore" param="<%=param%>">
  
   <center>
<%--   <input type="button" value="storico" onClick="openStorico('<%=Stabilimento.getIdStabilimento()%>')"/><br/> --%>

<dhv:permission name="sintesis-edit">
<input type="button" value="MODIFICA DATI IMPRESA" onClick="modificaDati('<%=Stabilimento.getIdStabilimento()%>')" />
</dhv:permission>
  </center>
  
  
  
 <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
	
  		<div align="right">
<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
<input type="button" title="Stampa Scheda" value="Stampa Scheda" onClick="openRichiestaPDFSintesisAnagrafica('<%= Stabilimento.getAltId() %>', '32');">
</div>
  		
  		<br/><br/>
  		
  		<dhv:permission name="note_hd-view">
<jsp:include page="../note_hd/link_note_hd.jsp">
<jsp:param name="riferimentoId" value="<%=Stabilimento.getIdStabilimento() %>" />
<jsp:param name="riferimentoIdNomeTab" value="sintesis_stabilimento" />
</jsp:include> <br><br>
</dhv:permission>
  
<jsp:include page="../preaccettazionesigla/button_preaccettazione.jsp">
    <jsp:param name="riferimentoIdPreaccettazione" value="<%=Stabilimento.getAltId() %>" />
    <jsp:param name="riferimentoIdNomePreaccettazione" value="altId" />
    <jsp:param name="riferimentoIdNomeTabPreaccettazione" value="sintesis_stabilimento" />
    <jsp:param name="userIdPreaccettazione" value="<%=User.getUserId() %>" />
</jsp:include>


<% if (idLineaCaseificio>0){ %>
<iframe scrolling="no" src="SchedeAdeguamentiAction.do?command=ViewSchedaBiogas&altId=<%=Stabilimento.getAltId() %>" style="top:0;left: 0;width:100%;height: 100%; border: none;" onload="refreshDimensioniIframe(this)"></iframe>
<% if (request.getAttribute("MessaggioSchedaBiogas")!=null && !request.getAttribute("MessaggioSchedaBiogas").equals("")) {%>
<script>
alert("<%=request.getAttribute("MessaggioSchedaBiogas")%>");
</script>
<%} %>
<%} %>

<br>
<br>
<jsp:include page="../gestionecodicesinvsa/gestione_codice_sinvsa.jsp">
	<jsp:param name="action" value="StabilimentoSintesisAction" />
	<jsp:param name="riferimentoId" value="<%=Stabilimento.getIdStabilimento() %>" />
	<jsp:param name="riferimentoIdNomeTab" value="sintesis_stabilimento" />
	<jsp:param name="idRuoloUtente" value="<%= User.getRoleId() %>" />
</jsp:include>


<%if (1==1) { %>
<jsp:include page="../schede_centralizzate/iframe.jsp">
    <jsp:param name="objectId" value="<%=Stabilimento.getAltId() %>" />
       <jsp:param name="objectIdName" value="alt_id" />
     <jsp:param name="tipo_dettaglio" value="32" />
     </jsp:include>
<%} else { %>  		


<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">

<tr><th colspan="2">Impresa</th></tr>

<tr>
<td class="formLabel">Ragione sociale</td>
<td><%=Stabilimento.getOperatore().getRagioneSociale()%></td>
</tr>

<tr>
<td class="formLabel">Partita IVA</td>
<td><%=Stabilimento.getOperatore().getPartitaIva()%></td>
</tr>

<tr>
<td class="formLabel">Codice Fiscale Impresa</td>
<td><%=Stabilimento.getOperatore().getCodiceFiscaleImpresa()%></td>
</tr>


<tr>
<td class="formLabel">Tipo Impresa</td>
<td><%=TipoImpresaList.getSelectedValue(Stabilimento.getOperatore().getTipoImpresa()) %></td>
</tr>

<tr><td class="formLabel">Tipo Societa</td>
<td><%=(Stabilimento.getOperatore().getTipoSocieta()>0) ? TipoSocietaList.getSelectedValue(Stabilimento.getOperatore().getTipoSocieta()) : "" %></td>
</tr>

<tr><td class="formLabel">Indirizzo sede legale</td>
<td>
<%=ToponimiList.getSelectedValue(Stabilimento.getOperatore().getSedeLegale().getToponimo()) %> 
<%=toHtml(Stabilimento.getOperatore().getSedeLegale().getVia())%>, 
<%=toHtml(Stabilimento.getOperatore().getSedeLegale().getCivico() )%>, 
<%=ComuniList.getSelectedValue(Stabilimento.getOperatore().getSedeLegale().getComune())%> 
(<%=ProvinceList.getSelectedValue(Stabilimento.getOperatore().getSedeLegale().getIdProvincia()) %>), 
 <%=toHtml(Stabilimento.getOperatore().getSedeLegale().getCap() )%>
</td>
</tr>

<tr><td class="formLabel">Domicilio digitale</td>
<td><%=toHtml(Stabilimento.getOperatore().getDomicilioDigitale() )%></td>
</tr>

<tr><th colspan="2">Rappresentante legale</th></tr>

<tr><td class="formLabel">Nome</td>
<td><%=toHtml(Stabilimento.getOperatore().getRappLegale().getNome() )%></td>
</tr>

<tr><td class="formLabel">Cognome</td>
<td><%=toHtml(Stabilimento.getOperatore().getRappLegale().getCognome() )%></td>
</tr>

<tr><td class="formLabel">Codice Fiscale</td>
<td><%=toHtml(Stabilimento.getOperatore().getRappLegale().getCodiceFiscale() )%></td>
</tr>

<tr><td class="formLabel">Indirizzo residenza</td>
<td><%if (Stabilimento.getOperatore().getRappLegale().getIndirizzo()!=null) { %>
<%=ToponimiList.getSelectedValue(Stabilimento.getOperatore().getRappLegale().getIndirizzo().getToponimo()) %> 
<%=toHtml(Stabilimento.getOperatore().getRappLegale().getIndirizzo().getVia())%>, 
<%=toHtml(Stabilimento.getOperatore().getRappLegale().getIndirizzo().getCivico() )%>, 
<%=ComuniList.getSelectedValue(Stabilimento.getOperatore().getRappLegale().getIndirizzo().getComune())%> 
(<%=ProvinceList.getSelectedValue(Stabilimento.getOperatore().getRappLegale().getIndirizzo().getIdProvincia()) %>), 
 <%=toHtml(Stabilimento.getOperatore().getRappLegale().getIndirizzo().getCap() )%>
 <%} %>
</td>
</tr>

<tr><td class="formLabel">Domicilio digitale</td>
<td><%=toHtml(Stabilimento.getOperatore().getRappLegale().getDomicilioDigitale() )%></td>
</tr>
	


</table>


<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">

<tr><th colspan="2">Sede Operativa</th></tr>

<tr>
<td class="formLabel">Denominazione</td>
<td><%=Stabilimento.getDenominazione() %></td>
</tr>

<tr>
<td class="formLabel">Stato  </td>
<td><%=StatiStabilimento.getSelectedValue(Stabilimento.getStato()) %></td>
</tr>

<tr>
<td class="formLabel">Approval number</td>
<td><%=Stabilimento.getApprovalNumber() %></td>
</tr>

<tr>
<td class="formLabel">Indirizzo</td>
<td><%=ToponimiList.getSelectedValue(Stabilimento.getIndirizzo().getToponimo())%> <%=Stabilimento.getIndirizzo().getVia() %>  <%=toHtml(Stabilimento.getIndirizzo().getCivico()) %>, <%=ComuniList.getSelectedValue(Stabilimento.getIndirizzo().getComune())%>, <%=ProvinceList.getSelectedValue(Stabilimento.getIndirizzo().getIdProvincia())%>, <%=toHtml(Stabilimento.getIndirizzo().getCap())%>
</td>
</tr>
<%} %>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" <%= isGestoreMacelli ? "style= 'display:none'" : "" %>>
<tr><th colspan="2">Linee attivita'</th></tr>


<% for (int j = 0; j<Stabilimento.getLinee().size(); j++){
SintesisRelazioneLineaProduttiva rel = (SintesisRelazioneLineaProduttiva) Stabilimento.getLinee().get(j);
%>

<tr>
<td class="formLabel"><b>Attivita'</b></td>
<td><b><%=rel.getPathCompleto() %></b>  </td>
</tr>

<%if (rel.getPathCompletoLineaProduttivaOld()!=null) { %>
<tr>
<td class="formLabel"><i>(importata)</i></td>
<td><i><%=rel.getPathCompletoLineaProduttivaOld() %></i></td>
</tr>
<%} %>

<tr><td class="formLabel"> </td><td>
<table cellpadding="4" cellspacing="0" border="0" class="details"><tr>
<td class="formLabel">Stato </td>
<td><%=StatiLinea.getSelectedValue(rel.getStato()) %></td>

<%if (rel.getDataInizio()!=null) {%>
<td class="formLabel">Data inizio </td>
<td><%=toDateasString(rel.getDataInizio())%></td>
<%} %>

<%if (rel.getDataFine()!=null) {%>
<td class="formLabel">Data fine </td>
<td><%=toDateasString(rel.getDataFine()) %></td>
<%} %>

<td> 

<input type="button" value="GESTIONE PRODOTTI" onClick="openProdottiLinea('<%=rel.getIdRelazione()%>')"/>

&nbsp;

<input type="button" value="OPERATORI" onclick="openMercatoLinea('<%=rel.getIdRelazione()%>');"/>

&nbsp;

<input type="button" value="AUTOMEZZI" onclick="openAutomezziLinea('<%=rel.getIdRelazione()%>');"/>

</td>
</tr>
</table>
</td></tr>


<tr style="display: none;" class="tr_campi_estesi" id="tr_campi_estesi_<%=rel.getIdRelazione()%>">
	 
</tr>

<%}%>

</table>
<br/>



<%if (idLineaMacello>0) {%>
<center>
<table class="details" width="50%">
<tr><th colspan="2" align="center">Gestione Macellazioni</th></tr>
<tr><td align="center"><a href="MacellazioniSintesis.do?command=ListTipi&altId=<%=Stabilimento.getAltId()%>">Macellazioni </a></td>
<td align="center"><a href="MacellazioniSintesis.do?command=ToStampeModuli&altId=<%=Stabilimento.getAltId()%>">Stampa moduli precompilati </a></td></tr>
</table>
</center>
<%} %>


</dhv:container>




</body>
</html>