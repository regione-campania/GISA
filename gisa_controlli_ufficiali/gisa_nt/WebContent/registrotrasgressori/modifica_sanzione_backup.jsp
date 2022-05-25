<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ include file="../../initPage.jsp"%>
<jsp:useBean id="trasgr" class="org.aspcfs.modules.registrotrasgressori.base.Trasgressione" scope="request"/>
<jsp:useBean id="gruppiUtente" type="java.util.ArrayList" scope="request" />
<jsp:useBean id="messaggio" class="java.lang.String" scope="request"/>
<%@page import="org.aspcfs.modules.registrotrasgressori.base.Trasgressione"%>

  <% UserBean user = (UserBean) session.getAttribute("User");%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %> 

<link rel="stylesheet" documentale_url="" href="registrotrasgressori/css/trasgressori_screen.css" type="text/css" media="screen" />
<link rel="stylesheet" documentale_url="" href="registrotrasgressori/css/trasgressori_print.css" type="text/css" media="print" />


<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>
<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<%@ include file="js/registro_sanzioni_js.jsp"%>

<% if (messaggio!=null && !messaggio.equals("")){ %>
<script> alert('<%=messaggio%>');</script>

<div style="background:yellow">
<pre><xmp><%=messaggio.replaceAll(">", ">\n") %></xmp></pre>
</div>
<%} %>



<form name="form1" id="form1" action="RegistroTrasgressori.do?command=UpdateSanzione" method="post">

<table class="details2" id = "tabletrasgressione" cellpadding="10" cellspacing="10">

<tr><th colspan="4">Modifica sanzione nel Registro Trasgressori</th></tr>

<tr><th>N° prog </th>  
<td><%= trasgr.getProgressivo() %>\<%=trasgr.getAnnoYY() %></td>

<th>Id controllo </th>  
<td><%=(trasgr.getIdControllo()>0) ? trasgr.getIdControllo() : "" %></td></tr>

<tr><th>ASL di competenza</th>  
<td><%=(trasgr.getAsl()!=null) ? trasgr.getAsl() : "" %></td>

<th>Ente accertatore</th>  
<td>
<% LinkedHashMap<String,String> listaEnti = trasgr.getListaEnti();
for(Map.Entry<String, String> ente : listaEnti.entrySet()){%>
<%=(ente.getValue()!=null) ? ente.getValue()+"<br/>" : "" %>
<%} %>
</td></tr> 

<tr><th>PV N° </th>  
<td><%=(trasgr.getPV()!=null) ? trasgr.getPV() : "" %></td>

<th>Num. sequestro eventualmente effettuato</th>  
<td><%=(trasgr.getPVsequestro()!=null) ? trasgr.getPVsequestro() : "" %></td></tr>
 
<tr><th>Data accertamento  </th>  
<td>
<input class="layout" type="text" id="data_accertamento" name="data_accertamento" readonly="readonly" size="10" value="<%=(trasgr.getDataAccertamento()!=null) ?  toDateasString(trasgr.getDataAccertamento()) : "" %>"/> 
</td>

<th>Data prot. in entrata in regione</th>  
<td>
<input class="editField" gruppo="1" type="text" readonly id="data_prot_entrata" name="data_prot_entrata" size="10" value="<%=(trasgr.getDataProt()!=null) ?  toDateasString(trasgr.getDataProt()) : "" %>"/>
<%if (gruppiUtente.contains(1)) { %>
<a href="#" onClick="cal19.select(document.forms[0].data_prot_entrata,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a> &nbsp;
<%} %>
</td></tr>

<tr><th>Trasgressore</th>  
<td><%=(trasgr.getTrasgressore()!=null) ? trasgr.getTrasgressore() : "" %></td>

<th>Obbligato in solido</th>  
<td><%=(trasgr.getObbligatoInSolido()!=null) ? trasgr.getObbligatoInSolido() : "" %></td></tr>

<tr><th>Importo sanzione ridotta</th>  
<td><%= trasgr.getImportoSanzioneRidotta() %>  &#x20ac; </td>

<th>Importo sanzione ridotta del 30%</th>  
<td><%=(trasgr.getImportoSequestroRiduzione() !=null) ? trasgr.getImportoSequestroRiduzione() : "" %></td></tr>

<tr><th>Illecito di competenza U.O.D. 01</th>  
<td>
<input type="checkbox" gruppo="1" <%if (!gruppiUtente.contains(1)) { %> onClick="return false" style="opacity : .50; filter: alpha(opacity=50)" <%} %> name="competenza_regionale" id="competenza_regionale" <% if (trasgr.isCompetenzaRegionale()){ %> checked="checked" <%} %> onClick="gestisciCompetenzaRegionale(); gestisciPraticaChiusaIndiretta();"/>
</td>

<th>Data ultima notifica</th>  
<td class="competenzaRegionaleClass">
<input class="editField" gruppo="1" type="text" readonly id="data_ultima_notifica" name="data_ultima_notifica" size="10" value="<%=(trasgr.getDataUltimaNotifica()!=null) ?  toDateasString(trasgr.getDataUltimaNotifica()) : "" %>"/>
<%if (gruppiUtente.contains(1)) { %>
<a href="#" onClick="cal19.select(document.forms[0].data_ultima_notifica,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a> &nbsp;
<%} %>
</td></tr>

<tr><th>PV oblato in misura ridotta</th>  
<td class="competenzaRegionaleClass">
<input type="checkbox"  gruppo="2" <%if (!gruppiUtente.contains(2)) {%> onClick="return false" style="opacity : .50; filter: alpha(opacity=50)" <%}%> name="pv_oblato_ridotta" id="pv_oblato_ridotta" <% if (trasgr.isPvOblato()){ %> checked="checked" <%} %>/>
</td>

<th>Importo effettivamente introitato (1)</th>  
<td class="competenzaRegionaleClass">
<input class="editField" type="text" gruppo="2" <%if (!gruppiUtente.contains(2)) {%> readonly <%}%> name="importo_effettivamente_versato1" id="importo_effettivamente_versato1" size="10" value="<%=trasgr.getImportoEffettivamenteVersato1() %>" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')"/> &#x20ac; 
</td></tr>
 
<tr><th>Data pagamento</th>  
<td class="competenzaRegionaleClass">
<input class="editField" gruppo="2" type="text" readonly id="data_pagamento" name="data_pagamento" size="10" value="<%=(trasgr.getDataPagamento()!=null) ?  toDateasString(trasgr.getDataPagamento()) : "" %>"/>
<%if (gruppiUtente.contains(2)) { %>
<a href="#" onClick="cal19.select(document.forms[0].data_pagamento,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a> &nbsp;
<%} %>
</td>

<th>Pagamento effettuato nei termini</th>  
<td class="competenzaRegionaleClass">
<label class="layout" type="text" readonly name="pagamento_ridotto_consentito" id="pagamento_ridotto_consentito" size="10"><%=trasgr.isPagamentoRidottoConsentito() ? "SI" : "NO" %></label>
</td></tr>
 
<tr><th>Funzionario assegnatario</th>  
<td class="competenzaRegionaleClass">
<input class="editField" type="text" gruppo="1" <%if (!gruppiUtente.contains(1)) { %> readonly <%} %> name="fi_assegnatario" id="fi_assegnatario" size="10" value="<%=(trasgr.getFiAssegnatario()!=null) ? trasgr.getFiAssegnatario() : "" %>"/>
</td>

<th>Presentati scritti difensivi</th>  
<td class="competenzaRegionaleClass">
<input type="checkbox" gruppo="1" <%if (!gruppiUtente.contains(1)) { %> onClick="return false" style="opacity : .50; filter: alpha(opacity=50)" <%} %> name="presentati_scritti" id="presentati_scritti" <% if (trasgr.isPresentatiScritti()){ %> checked="checked" <%} %> 
</td></tr>
 	
<tr><th>Presentata richiesta riduzione sanzione e/o rateizzazione</th>  
<td class="competenzaRegionaleClass">
<input type="checkbox" gruppo="1" <%if (!gruppiUtente.contains(1)) {%> onClick="return false" style="opacity : .50; filter: alpha(opacity=50)" <%}%> name="presentata_richiesta_riduzione" id="presentata_richiesta_riduzione" <% if (trasgr.isRichiestaRiduzioneSanzione()){ %> checked="checked" <%} %> 
</td>

<th>Presentata richiesta audizione</th>  
<td class="competenzaRegionaleClass">
<input type="checkbox" gruppo="1" <%if (!gruppiUtente.contains(1)) {%> onClick="return false" style="opacity : .50; filter: alpha(opacity=50)" <%}%> name="presentata_richiesta_audizione" id="presentata_richiesta_audizione" <% if (trasgr.isRichiestaAudizione()){ %> checked="checked" <%} %> 
</td></tr>
 
<tr><th>Ordinanza emessa</th>  
<td class="competenzaRegionaleClass">
<input type="radio" gruppo="1" <%if (!gruppiUtente.contains(1)) {%> onClick="return false" style="opacity : .50; filter: alpha(opacity=50)" <%}%> name="ordinanza_emessa" id="ordinanza_emessaA" value="A" <% if ("A".equals(trasgr.getOrdinanzaEmessa())){ %> checked="checked" <%} %> onClick="gestisciArgomentazioni(); gestisciPraticaChiusaIndiretta();"/> Ord. Archiviazione <br/>
<input type="radio" gruppo="1" <%if (!gruppiUtente.contains(1)) {%> onClick="return false" style="opacity : .50; filter: alpha(opacity=50)" <%}%> name="ordinanza_emessa" id="ordinanza_emessaB" value="B" <% if ("B".equals(trasgr.getOrdinanzaEmessa())){ %> checked="checked" <%} %> onClick="gestisciArgomentazioni();"/> Ord. Ingiunzione
<input type="radio" gruppo="1" <%if (!gruppiUtente.contains(1)) {%> onClick="return false" style="opacity : .50; filter: alpha(opacity=50)" <%}%> name="ordinanza_emessa" id="ordinanza_emessaC" value="C" <% if ("C".equals(trasgr.getOrdinanzaEmessa())){ %> checked="checked" <%} %> onClick="gestisciArgomentazioni(); gestisciPraticaChiusaIndiretta();"/> Pratica non lavorata
</td>

<th>Num. ordinanza</th>  
<td class="competenzaRegionaleClass">
<input class="editField" type="text" gruppo="1" <%if (!gruppiUtente.contains(1)) {%> readonly <%}%> name="num_ordinanza" id="num_ordinanza" size="10" value="<%=(trasgr.getNumOrdinanza()!=null) ? trasgr.getNumOrdinanza() : "" %>" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')" /> </td>
</td></tr>

<tr><th>Data di emissione dell'Ordinanza</th>  
<td class="competenzaRegionaleClass">
<input class="editField" gruppo="1" type="text" readonly id="data_emissione" name="data_emissione" size="10" value="<%=(trasgr.getDataEmissione()!=null) ?  toDateasString(trasgr.getDataEmissione()) : "" %>" />
<%if (gruppiUtente.contains(1)) {%> 
<a href="#" onClick="cal19.select(document.forms[0].data_emissione,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
<%} %>
</td>

<th>Giorni di lavorazione pratica</th>  
<td class="competenzaRegionaleClass">
<input class="layout" type="text" readonly id="giorni_lavorazione" name="giorni_lavorazione" size="3" value="<%=(trasgr.getGiorniLavorazione()>-1) ? trasgr.getGiorniLavorazione() : "" %>"/>
</td></tr>
 
<tr><th>Importo sanzione ingiunta</th>  
<td class="competenzaRegionaleClass argomentazioniClass">
<input class="editField" type="text" gruppo="1" <%if (!gruppiUtente.contains(1)) {%> readonly <%}%> name="importo_sanzione_ingiunta" id="importo_sanzione_ingiunta" size="10" value="<%=trasgr.getImportoSanzioneIngiunta() %>" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')"/> &#x20ac;
</td>

<th>Data ultima notifica ordinanza</th>  
<td class="competenzaRegionaleClass argomentazioniClass">
<input class="editField" gruppo="1" type="text" readonly id="data_ultima_notifica_ordinanza" name="data_ultima_notifica_ordinanza" size="10" value="<%=(trasgr.getDataUltimaNotificaOrdinanza()!=null) ?  toDateasString(trasgr.getDataUltimaNotificaOrdinanza()) : "" %>"/>
<%if (gruppiUtente.contains(1)) { %>
<a href="#" onClick="cal19.select(document.forms[0].data_ultima_notifica_ordinanza,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a> &nbsp;
<%} %>
</td></tr>
 
<tr><th>Data pagamento ordinanza</th>  
<td class="competenzaRegionaleClass argomentazioniClass">
<input class="editField" gruppo="2" type="text" readonly id="data_pagamento_ordinanza" name="data_pagamento_ordinanza" size="10" value="<%=(trasgr.getDataPagamentoOrdinanza()!=null) ?  toDateasString(trasgr.getDataPagamentoOrdinanza()) : "" %>"/>
<%if (gruppiUtente.contains(2)) { %>
<a href="#" onClick="cal19.select(document.forms[0].data_pagamento_ordinanza,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a> &nbsp;
<%} %>
</td>

<th>Concessa rateizzazione dell'ordinanza-ingiunzione</th>  
<td class="competenzaRegionaleClass argomentazioniClass">
<input type="radio" gruppo="1" <%if (!gruppiUtente.contains(1)) {%> onClick="return false" style="opacity : .50; filter: alpha(opacity=50)" <%}%> name="richiesta_rateizzazione" id="richiesta_rateizzazioneSI" value="true" <% if (trasgr.isRichiestaRateizzazione()){ %> checked="checked" <%} %> onClick="gestisciRichiestaRateizzazione()"/> SI <br/>
<input type="radio" gruppo="1" <%if (!gruppiUtente.contains(1)) {%> onClick="return false" style="opacity : .50; filter: alpha(opacity=50)" <%}%> name="richiesta_rateizzazione" id="richiesta_rateizzazioneNO" <% if (!trasgr.isRichiestaRateizzazione()){ %> checked="checked" <%} %> onClick="gestisciRichiestaRateizzazione()"/> NO 
</td></tr>
 
<tr><th>Rate pagate</th>  
<td class="competenzaRegionaleClass argomentazioniClass rateizzazioneClass">
<table> 
<tr>
<% for (int j=0; j<trasgr.getRate().length; j++ ) {%>
<td><input type="checkbox" gruppo="2" <%if (!gruppiUtente.contains(2)) {%> onClick="return false" style="opacity : .50; filter: alpha(opacity=50)" <%}%>  name="rata<%=j+1 %>" id="rata<%=j %>" <% if (trasgr.getRate()[j]){ %> checked="checked" <%} %>/> <%=j+1 %></td>
<% if (j==4){ %></tr><tr> <% } %>
<% } %>
</tr>
</table>
</td>

<th>Ordinanza ingiunzione oblata</th> 
<td class="competenzaRegionaleClass argomentazioniClass">
<input type="checkbox" gruppo="2" <%if (!gruppiUtente.contains(2)) {%> onClick="return false" style="opacity : .50; filter: alpha(opacity=50)" <%}%>  name="ordinanza_ingiunzione_oblata" id="ordinanza_ingiunzione_oblata" <% if (trasgr.isIngiunzioneOblata()){ %> checked="checked" <%} %>/> 
</td></tr>
  
<tr><th>Importo effettivamente introitato (2)</th>  
<td class="competenzaRegionaleClass argomentazioniClass">
<input class="editField" type="text" gruppo="2" <%if (!gruppiUtente.contains(2)) {%> readonly <%}%> name="importo_effettivamente_versato2" id="importo_effettivamente_versato2" size="10" value="<%=trasgr.getImportoEffettivamenteVersato2() %>" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')"/> &#x20ac; 
</td>

<th>Presentata opposizione all'ordinanza-ingiunzione</th>  
<td class="competenzaRegionaleClass argomentazioniClass">
<input type="checkbox" gruppo="1" <%if (!gruppiUtente.contains(1)) {%> onClick="return false" style="opacity : .50; filter: alpha(opacity=50)" <%}%> name="presentata_opposizione" id="presentata_opposizione" <% if (trasgr.isPresentataOpposizione()){ %> checked="checked" <%} %>/>
</td></tr>
 
<tr><th>Sentenza favorevole al ricorrente</th>  
<td class="competenzaRegionaleClass argomentazioniClass">
<input type="radio" gruppo="1" <%if (!gruppiUtente.contains(1)) {%> onClick="return false" style="opacity : .50; filter: alpha(opacity=50)" <%}%> name="sentenza_favorevole" id="sentenza_favorevoleSI" value="true" <% if (trasgr.isSentenzaFavorevole()){ %> checked="checked" <%} %> onClick="gestisciSentenza()"/> SI <br/>
<input type="radio" gruppo="1" <%if (!gruppiUtente.contains(1)) {%> onClick="return false" style="opacity : .50; filter: alpha(opacity=50)" <%}%> name="sentenza_favorevole" id="sentenza_favorevoleNO" <% if (!trasgr.isSentenzaFavorevole()){ %> checked="checked" <%} %> onClick="gestisciSentenza()"/> NO 
</td>

<th>Importo stabilito dalla A.G.</th>  
<td class="competenzaRegionaleClass argomentazioniClass sentenzaClass">
<input class="editField" type="text" gruppo="1" <%if (!gruppiUtente.contains(1)) {%> readonly <%}%> name="importo_stabilito" id="importo_stabilito" size="10" value="<%=trasgr.getImportoStabilito() %>" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')"/> &#x20ac;
</td></tr> 

<tr><th>Ordinanza-ingiunzione oblata secondo il dispositivo della sentenza</th>  
<td class="competenzaRegionaleClass argomentazioniClass sentenzaClass">
<input type="checkbox" gruppo="1" <%if (!gruppiUtente.contains(1)) { %> onClick="return false" style="opacity : .50; filter: alpha(opacity=50)" <%} %> name="ordinanza_ingiunzione_sentenza" id="ordinanza_ingiunzione_sentenza" <% if (trasgr.isIngiunzioneOblataSentenza()){ %> checked="checked" <%} %>/>
</td>

<th>Importo effettivamente introitato (3)</th>  
<td class="competenzaRegionaleClass argomentazioniClass sentenzaClass">
<input class="editField" type="text" gruppo="2" <%if (!gruppiUtente.contains(2)) {%> readonly <%}%> name="importo_effettivamente_versato3" id="importo_effettivamente_versato3" size="10" value="<%=trasgr.getImportoEffettivamenteVersato3() %>" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')"/> &#x20ac; 
</td></tr>
 
<tr><th>Avviata per l'esecuzione forzata</th> 
<td class="competenzaRegionaleClass argomentazioniClass sentenzaClass">
<input type="checkbox" gruppo="2" <%if (!gruppiUtente.contains(2)) {%> onClick="return false" style="opacity : .50; filter: alpha(opacity=50)" <%}%> name="iscrizione_ruoli_sattoriali" id="iscrizione_ruoli_sattoriali" <% if (trasgr.isIscrizioneRuoliSattoriali()){ %> checked="checked" <%} %>/>
</td>

<th>Importo effettivamente introitato (4)</th>  
<td class="competenzaRegionaleClass argomentazioniClass sentenzaClass">
<input class="editField" type="text" gruppo="2" <%if (!gruppiUtente.contains(2)) {%> readonly <%}%> name="importo_effettivamente_versato4" id="importo_effettivamente_versato4" size="10" value="<%=trasgr.getImportoEffettivamenteVersato4() %>" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')" /> &#x20ac;
</td></tr>
 
<tr><th>Note Gruppo 1</th>  
<td>
<textarea class="editField" gruppo="1" <%if (!gruppiUtente.contains(1)) {%> readonly <%}%> name="note1" id="note1" cols="20" rows="3"/><%= (trasgr.getNote1()!=null) ? trasgr.getNote1() : ""%></textarea>
</td>

<th>Note Gruppo 2</th>  
<td>
<textarea class="editField" gruppo="2" <%if (!gruppiUtente.contains(2)) {%> readonly <%}%> name="note2" id="note2" cols="20" rows="3"/><%= (trasgr.getNote2()!=null) ? trasgr.getNote2() : ""%></textarea>
</td></tr>
 
<tr><th>Processo Verbale</th>  

<td>
<div id="linkDocumentoPv">
<% if (trasgr.getAllegatoPv()!=null && !trasgr.getAllegatoPv().equals("")) { 
for (int k = 0; k<trasgr.getListaAllegatiPv().length; k++) {%>
<a href="GestioneAllegatiTrasgressori.do?command=DownloadPDF&codDocumento=<%=trasgr.getListaAllegatiPv()[k]%>&nomeDocumento=<%=trasgr.getListaAllegatiPv()[k]%>">  <label id="<%=trasgr.getListaAllegatiPv()[k]%>">DOWNLOAD <%=k+1 %></label>  </a><br/><br/>
<script> recuperaOggettoAllegato('<%=trasgr.getListaAllegatiPv()[k]%>')</script>
<%} } %>
</div> 
</td>

<th>Altri allegati inseriti dagli agenti</th>  

<td>
<div id="linkDocumentoAl">
<% if (trasgr.getAllegatoAl()!=null && !trasgr.getAllegatoAl().equals("")) { 
for (int k = 0; k<trasgr.getListaAllegatiAl().length; k++) {%>
<a href="GestioneAllegatiTrasgressori.do?command=DownloadPDF&codDocumento=<%=trasgr.getListaAllegatiAl()[k]%>&nomeDocumento=<%=trasgr.getListaAllegatiAl()[k]%>"> <label id="<%=trasgr.getListaAllegatiAl()[k]%>">DOWNLOAD <%=k+1 %></label>  </a><br/><br/>
<script> recuperaOggettoAllegato('<%=trasgr.getListaAllegatiAl()[k]%>')</script>
<%} } %>
</div> 
</td>

</tr>

<tr><th>Altri allegati</th>  
<td>
<div id="linkDocumento">
<% if (trasgr.getAllegatoDocumentale()!=null && !trasgr.getAllegatoDocumentale().equals("")) { 
for (int k = 0; k<trasgr.getListaAllegatiDocumentale().length; k++) {%>
<a href="GestioneAllegatiTrasgressori.do?command=DownloadPDF&codDocumento=<%=trasgr.getListaAllegatiDocumentale()[k]%>&nomeDocumento=<%=trasgr.getIdSanzione()+""+trasgr.getAnno()+""+k%>"> <label id="<%=trasgr.getListaAllegatiDocumentale()[k]%>">DOWNLOAD <%=k+1 %></label>  </a><br/><br/>
<script> recuperaOggettoAllegato('<%=trasgr.getListaAllegatiDocumentale()[k]%>')</script>

<%} } %>
</div> 
<label name="titoloDocumento"  id="titoloDocumento"></label>
<%if (gruppiUtente.contains(1) || gruppiUtente.contains(2)) {%>
<a href = "javascript:openUploadAllegatoTragressori('<%=trasgr.getId() %>','<%=trasgr.getIdSanzione() %>', 'RegistroTrasgressori')" id="allega">Allega nuovo</a>
<%} %>
<input type="hidden" id="allegato_documentale" name="allegato_documentale" value="<% if (trasgr.getAllegatoDocumentale()!=null && !trasgr.getAllegatoDocumentale().equals("")) { for (int k = 0; k<trasgr.getListaAllegatiDocumentale().length; k++) {%><%= trasgr.getListaAllegatiDocumentale()[k] %><%if (k<trasgr.getListaAllegatiDocumentale().length-1) { %>;<% } } } %>"/>
</td>

<th>Pratica chiusa</th>  
<td>
<input type="checkbox" gruppo="2" <%if (!gruppiUtente.contains(2)) {%> onClick="return false" style="opacity : .50; filter: alpha(opacity=50)" <%}%>  name="pratica_chiusa" id="pratica_chiusa" <% if (trasgr.isPraticaChiusa()){ %> checked="checked" <%} %>  onClick="gestisciPraticaChiusa()" />
</td></tr>

<tr><th>Modificato da</th> 
<td> <dhv:username id="<%= trasgr.getIdUtenteModifica() %>" /></td> 
<th>Modificato il</th> 
<td><%=toDateasStringWitTime(trasgr.getDataModifica()) %></td>
</tr>


<tr>
<th colspan="2"><input type="button" id="annulla" value="ANNULLA" onClick="annullaModificaSanzione('<%=trasgr.getAnno()%>', '<%=trasgr.getTrimestre()%>')"/></th>
<th colspan="2"><input type="button" id="salva" value="SALVA" onClick="checkFormModificaSanzione(this.form)"/></th>
</tr> 

<tr><th colspan="4"><input type="button" id="torna" value="TORNA AL REGISTRO (TRIMESTRE DI QUESTA SANZIONE)" onClick="tornaAlRegistro('<%=trasgr.getAnno()%>', '<%=trasgr.getTrimestre()%>')"/></th></tr>
<tr><th colspan="4"><input type="button" id="torna" value="TORNA AL REGISTRO (TUTTO L'ANNO)" onClick="tornaAlRegistro('<%=trasgr.getAnno()%>', '0')"/></th></tr>

<input type="hidden" readonly id="idControlloUfficiale" name="idControlloUfficiale" value="<%=trasgr.getIdControllo() %>"/>
<input type="hidden" readonly id="id_sanzione" name="id_sanzione" value="<%=trasgr.getIdSanzione() %>"/>
<input type="hidden" readonly id="id" name="id" value="<%=trasgr.getId() %>"/>
	
<input type="hidden" name="allegato_documentale"  id="allegato_documentale" value="<%=(trasgr.getAllegatoDocumentale()!=null) ? trasgr.getAllegatoDocumentale() : "" %>"></input>

</table>

<% if (!"B".equals(trasgr.getOrdinanzaEmessa())){%>
			<script>gestisciArgomentazioni()</script>
<%} else if (trasgr.isSentenzaFavorevole()){%>
			<script>gestisciSentenza()</script>
<%} %>

<%if (!trasgr.isRichiestaRateizzazione()) {%>
			<script>gestisciRichiestaRateizzazione()</script>
<%} %>

<%if (!trasgr.isCompetenzaRegionale()) {%>
			<script>gestisciCompetenzaRegionale()</script>
<%} %>

<%if (trasgr.isPraticaChiusa()) {%>
			<script>gestisciPraticaChiusa()</script>
<%} %>			
		
</form>		
