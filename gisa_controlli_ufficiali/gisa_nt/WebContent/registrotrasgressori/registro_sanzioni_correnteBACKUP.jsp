<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ include file="../../initPage.jsp"%>
<jsp:useBean id="listaTrasgressioni" class="java.util.Vector" scope="request"/>
<jsp:useBean id="messaggio" class="java.lang.String" scope="request"/>
<jsp:useBean id="anno" class="java.lang.String" scope="request"/>
<jsp:useBean id="trimestre" class="java.lang.String" scope="request"/>
<%@page import="org.aspcfs.modules.registrotrasgressori.base.*"%>
<jsp:useBean id="gruppiUtente" type="java.util.ArrayList" scope="request" />


  <% UserBean user = (UserBean) session.getAttribute("User");%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %> 


<link rel="stylesheet" documentale_url="" href="registrotrasgressori/css/trasgressori_screen.css" type="text/css" media="screen" />
<link rel="stylesheet" documentale_url="" href="registrotrasgressori/css/trasgressori_print.css" type="text/css" media="print" />

<%@ include file="js/registro_sanzioni_js.jsp"%>

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
<script>
	function modifica(id){
		loadModalWindow();
		window.location.href = "RegistroTrasgressori.do?command=ModificaSanzione&id="+id;
	}
	
	function dettaglioPagoPa(idSanzione){
		loadModalWindow();
		window.location.href = "GestionePagoPa.do?command=View&idSanzione="+idSanzione+"&origine=NumeroOrdinanza";
}
</script>

 <script>$(document).ready(function() {
      // Initialize Smart Wizard
      loadModalWindowUnlock();
      
//     //  $('#wizard').smartWizard();
//       $('#wizard').smartWizard(    		  
//     		     {
//     		      divHeadPaddingLeft:  2,
//     		      divBodyPaddingLeft:   2,
//     		      fixedTypeNumber:  1
    		      			
//     		      	    }); 

      
  }); 
</script>

<script type="text/javascript" src="dwr/interface/DwrTrasgressione.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>


<%@page import="org.aspcfs.modules.vigilanza.base.MotivoIspezione"%>
<%@page import="java.util.ArrayList"%>


<div class="documentaleNonStampare">
<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>
</div>

<div class="documentaleStampare" style="display:none">
<div class="boxIdDocumento"></div> <div class="boxOrigineDocumento"><%@ include file="../../hostName.jsp" %></div>
</div>

<script type="text/javascript">
	//loadModalWindow();
	loadModalWindowCustom('<div style="color:red; font-size: 40px;">Attendere la corretta compilazione del Registro Trasgressori... </div>');
	</script>


<html><body>
  <form name="form1" id="form1" action="RegistroTrasgressori.do?command=SalvaRegistro" method="post">
  	
	<label class="layout">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; REGISTRO SANZIONI AMMINISTRATIVE ANNO <%= anno %> <%=(trimestre!=null && !trimestre.equals("0")) ? "TRIMESTRE " +trimestre : ""%></label> 
</div> 
<table  class="details2" id = "tablelistatrasgressioni">

	<thead>
		<tr class="tablesorter-headerRow" role="row">
		<% int index = -1; %>
		<% index++;%> <th>Azione </th>  
		<% index++;%> <th>N° prog <br/>  <input type="text" id="myInput<%=index %>" onkeyup="filtra('<%=index %>')" placeholder=""></th>  
			<% index++;%> <th>Id controllo <br/> <input type="text" id="myInput<%=index %>" onkeyup="filtra('<%=index %>')" placeholder=""> </th>  
			<% index++;%> <th>ASL di competenza <br/> <input type="text" id="myInput<%=index %>" onkeyup="filtra('<%=index %>')" placeholder=""></th>  
			<% index++;%> <th>Ente accertatore 1 <br/> <input type="text" id="myInput<%=index %>" onkeyup="filtra('<%=index %>')" placeholder=""></th> 
			<% index++;%> <th>Ente accertatore 2 <br/> <input type="text" id="myInput<%=index %>" onkeyup="filtra('<%=index %>')" placeholder=""></th>  
			<% index++;%> <th>Ente accertatore 3 <br/> <input type="text" id="myInput<%=index %>" onkeyup="filtra('<%=index %>')" placeholder=""></th>
			<% index++;%> <th>PV N° <br/> <input type="text" id="myInput<%=index %>" onkeyup="filtra('<%=index %>')" placeholder=""> </th>  
			<% index++;%> <th>Num. sequestro eventualmente effettuato <br/> <input type="text" id="myInput<%=index %>" onkeyup="filtra('<%=index %>')" placeholder=""></th> 
			<% index++;%> <th>Data accertamento  <br/> <input type="text" id="myInput<%=index %>" onkeyup="filtra('<%=index %>')" placeholder=""> </th>  
			<% index++;%> <th>Data prot. in entrata in regione</th>  
			<% index++;%> <th>Trasgressore <br/> <input type="text" id="myInput<%=index %>" onkeyup="filtra('<%=index %>')" placeholder=""></th>   
			<% index++;%> <th>Obbligato in solido <br/> <input type="text" id="myInput<%=index %>" onkeyup="filtra('<%=index %>')" placeholder=""></th>
			<% index++;%> <th>Importo sanzione ridotta</th>
			<% index++;%> <th>Importo sanzione ridotta del 30%</th>  
			<% index++;%> <th>Illecito di competenza U.O.D. 01</th>  
			<% index++;%> <th>Data ultima notifica</th>  
			<% index++;%> <th>PV oblato in misura ridotta</th> 
			<% index++;%> <th>Importo effettivamente introitato (1)</th> 
			<% index++;%> <th>Data pagamento</th> 
			<% index++;%> <th>Pagamento effettuato nei termini</th> 
			<% index++;%> <th>Funzionario assegnatario</th> 
			<% index++;%> <th>Presentati scritti difensivi</th> 	
			<% index++;%> <th>Presentata richiesta riduzione sanzione e/o rateizzazione</th> 
			<% index++;%> <th>Presentata richiesta audizione</th> 
			<% index++;%> <th>Ordinanza emessa</th> 
			<% index++;%> <th>Num. ordinanza <input type="text" id="myInput<%=index %>" onkeyup="filtra('<%=index %>')" placeholder=""></th> 
			<% index++;%> <th>Data di emissione dell'Ordinanza</th> 
			<% index++;%> <th>Giorni di lavorazione pratica</th> 
			<% index++;%> <th>Importo sanzione ingiunta</th> 
			<% index++;%> <th>Data ultima notifica ordinanza</th> 
			<% index++;%> <th>Data pagamento ordinanza</th> 
			<% index++;%> <th>Concessa rateizzazione dell'ordinanza-ingiunzione</th> 
			<% index++;%> <th>Rate pagate</th> 
			<% index++;%> <th>Ordinanza ingiunzione oblata</th> 
			<% index++;%> <th>Importo effettivamente introitato (2)</th> 
			<% index++;%> <th>Presentata opposizione all'ordinanza-ingiunzione</th> 
			<% index++;%> <th>Sentenza favorevole al ricorrente</th> 
			<% index++;%> <th>Importo stabilito dalla A.G.</th> 
			<% index++;%> <th>Ordinanza-ingiunzione oblata secondo il dispositivo della sentenza</th> 
			<% index++;%> <th>Importo effettivamente introitato (3)</th> 
			<% index++;%> <th>Avviata per l'esecuzione forzata</th> 
			<% index++;%> <th>Importo effettivamente introitato (4)</th> 
			<% index++;%> <th>IUV/RT Generati</th>
			<% index++;%> <th>Note Gruppo 1</th> 
			<% index++;%> <th>Note Gruppo 2</th> 
			<% index++;%> <th>Processo Verbale</th> 
			<% index++;%> <th>Altri allegati inseriti dagli agenti</th> 
			<% index++;%> <th>Altri allegati</th>
			<% index++;%> <th>Pratica chiusa</th> 
					
		</tr>
	</thead>
	<tbody aria-relevant="all" aria-live="polite">
	
	<%if (listaTrasgressioni.size()>0){
		for (int i=0;i<listaTrasgressioni.size(); i++){
			Trasgressione trasgr = (Trasgressione) listaTrasgressioni.get(i);
	%>
		
		<tr id="riga_<%=i%>" class="<%= ("C".equals(trasgr.getOrdinanzaEmessa())) ? "orange" : (trasgr.isPraticaChiusa()) ? "green" : "row"+i%2%>">
			
			<td>
			<dhv:permission name="registro_trasgressori-edit">
			<input type="button" value="Modifica" onClick="modifica('<%=trasgr.getId()%>')"/>
			</dhv:permission>
			
			<% if (org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("GESTIONE_PAGOPA")!=null && org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("GESTIONE_PAGOPA").equalsIgnoreCase("si")) {%>
			<% if (PopolaCombo.isPrevistoPagoPA(trasgr.getIdSanzione())){ %>
			<%if (gruppiUtente.contains(1)) { %>
			<dhv:permission name="gestione_pagopa-view">
			<br/><br/>
			<input type="button" id="invia" value="GESTIONE PAGOPA" onClick="dettaglioPagoPa('<%=trasgr.getIdSanzione()%>')"/>
			</dhv:permission>
			<%} %>
			<%} %>
			<%} %>
			  
			 </td>			
			<td><%= trasgr.getProgressivo() %>\<%=trasgr.getAnnoYY() %></td>
			
			<td> 
			
			<label class="layout">
			
			<% if (trasgr.getIdControllo()>0) {
				boolean linkVisibile = false; %>
			
				<dhv:permission name="registro_trasgressori_link_cu-view">
				<% linkVisibile = true; %>
				<a href="Vigilanza.do?command=TicketDetails&id=<%=trasgr.getIdControllo() %>" target="_blank"><%=trasgr.getIdControllo() %></a>
				</dhv:permission>
				
				<% if (!linkVisibile) { %>
					<%=trasgr.getIdControllo() %>
				<%} %>
				
			<% } %>
			
			</label> 
			
			</td>
			
			<td>  
			<label class="layout"><%=(trasgr.getAsl()!=null) ? trasgr.getAsl() : "" %></label> </td>
			
			<% LinkedHashMap<String,String> listaEnti = trasgr.getListaEnti();
			int q = 0;
			for(Map.Entry<String, String> ente : listaEnti.entrySet()){
				q++;
			%>
			<td>  
			<label class="layout"><%=(ente.getValue()!=null) ? ente.getValue() : "" %></label></td>
			<%} %>
			
			<% for (int o = q; o<3; o++) {%>
			<td>  <label class="layout"><%="" %></label> </td>
			<%} %>
			
			<td>
			<label class="layout"><%=(trasgr.getPV()!=null) ? trasgr.getPV() : "" %></label> </td>
			
			<td>
			<label class="layout"><%=(trasgr.getPVsequestro()!=null) ? trasgr.getPVsequestro() : "" %></label> </td>
			
			<td>  
			<label class="layout"><%=(trasgr.getDataAccertamento()!=null) ?  toDateasString(trasgr.getDataAccertamento()) : "" %></label> </td>
			
			<td> 
			<label class="layout"><%=(trasgr.getDataProt()!=null) ?  toDateasString(trasgr.getDataProt()) : "" %></label>
			</td>
						
			<td>  
			<label class="layout"><%=(trasgr.getTrasgressore()!=null) ? trasgr.getTrasgressore() : "" %></label></td>
			
			<td>  
			<label class="layout"><%=(trasgr.getObbligatoInSolido()!=null) ? trasgr.getObbligatoInSolido() : "" %></label></td>
			
			<td>  
			<label class="layout"><%= trasgr.getImportoSanzioneRidotta() %>  &#x20ac; </label> </td>
			
			<td>  
			<label class="layout"><%=(trasgr.getImportoSequestroRiduzione() !=null) ? trasgr.getImportoSequestroRiduzione() : "" %></label></td>
			
			<td> 
			<input type="checkbox"  disabled <% if (trasgr.isCompetenzaRegionale()){ %> checked="checked" <%} %>/>  </td>
			
			<td> 
			<% if (trasgr.isCompetenzaRegionale()) { %>
			<label class="layout"><%=(trasgr.getDataUltimaNotifica()!=null) ?  toDateasString(trasgr.getDataUltimaNotifica()) : "" %></label>
			<% } %>
			</td>
			
			<td> 
			<% if (trasgr.isCompetenzaRegionale()) { %>
			<input type="checkbox" disabled <% if (trasgr.isPvOblato()){ %> checked="checked" <%} %>/> 
			</td>
			<% } %>
			
			<td> 
			<% if (trasgr.isCompetenzaRegionale()) { %>
			<label class="layout"><%=trasgr.getImportoEffettivamenteVersato1() %></label>
			<% } %>
			</td>
			
			<td> 
			<% if (trasgr.isCompetenzaRegionale()) { %>
			<label class="layout"><%=(trasgr.getDataPagamento()!=null) ?  toDateasString(trasgr.getDataPagamento()) : "" %></label>
			<%} %></td>
			
			<td> 
			<% if (trasgr.isCompetenzaRegionale()) { %>
			<label class="layout"><%=trasgr.isPagamentoRidottoConsentito() ? "SI" : "NO" %></label>
			<%} %>
			</td>
			
			<td> 
			<% if (trasgr.isCompetenzaRegionale()) { %>
			<label class="layout"><%=(trasgr.getFiAssegnatario()!=null) ? trasgr.getFiAssegnatario() : "" %></label>
			<% } %>
			</td>
			
			<td> 
			<% if (trasgr.isCompetenzaRegionale()) { %>
			<input type="checkbox" disabled <% if (trasgr.isPresentatiScritti()){ %> checked="checked" <%} %> /> 
			<% } %>
			</td>
			
			<td> 
			<% if (trasgr.isCompetenzaRegionale()) { %>
			<input type="checkbox" disabled <% if (trasgr.isRichiestaRiduzioneSanzione()){ %> checked="checked" <%} %> />
			<% } %>
			</td>
			
			<td> 
			<% if (trasgr.isCompetenzaRegionale()) { %>
			<input type="checkbox" disabled <% if (trasgr.isRichiestaAudizione()){ %> checked="checked" <%} %> />
			<% } %>
			</td>
			
			<td> 
			<% if (trasgr.isCompetenzaRegionale()) { %>
			<input type="radio" disabled <% if ("A".equals(trasgr.getOrdinanzaEmessa())){ %> checked="checked" <%} %>/> Ord. Archiviazione <br/>
			<input type="radio" disabled <% if ("B".equals(trasgr.getOrdinanzaEmessa())){ %> checked="checked" <%} %>/> Ord. Ingiunzione <br/>
			<input type="radio" disabled <% if ("C".equals(trasgr.getOrdinanzaEmessa())){ %> checked="checked" <%} %>/> Pratica non lavorata 
			<% } %>
			</td>
			
			<td> 
			<% if (trasgr.isCompetenzaRegionale()) { %>
			<label class="layout"><%=(trasgr.getNumOrdinanza()!=null) ? trasgr.getNumOrdinanza() : "" %></label> 
			<% } %>
			</td>
			 
			<td>  
			<% if (trasgr.isCompetenzaRegionale()) { %>
           	<label class="layout"><%=(trasgr.getDataEmissione()!=null) ?  toDateasString(trasgr.getDataEmissione()) : "" %></label>
		 	<% } %>
		 	</td>
		 
		 	<td> 
		 	<% if (trasgr.isCompetenzaRegionale()) { %>
			<label class="layout"><%=(trasgr.getGiorniLavorazione()>-1) ? trasgr.getGiorniLavorazione() : "" %></label>
			<% } %>
			</td>
			
			<td> 
			<% if (trasgr.isCompetenzaRegionale()) { %>
			<% if ("B".equals(trasgr.getOrdinanzaEmessa())) {%>
			<label class="layout"><%=trasgr.getImportoSanzioneIngiunta() %></label> &#x20ac;
			<% } %> 
			<% } %>
			</td>
			
			<td> 
			<% if (trasgr.isCompetenzaRegionale()) { %>
			<% if ("B".equals(trasgr.getOrdinanzaEmessa())) {%>
			<label class="layout"><%=(trasgr.getDataUltimaNotificaOrdinanza()!=null) ?  toDateasString(trasgr.getDataUltimaNotificaOrdinanza()) : "" %></label>
			<% } %> 
			<% } %>
			 </td>
			
			<td> 
			<% if (trasgr.isCompetenzaRegionale()) { %>
			<% if ("B".equals(trasgr.getOrdinanzaEmessa())) {%>
			<label class="layout"><%=(trasgr.getDataPagamentoOrdinanza()!=null) ?  toDateasString(trasgr.getDataPagamentoOrdinanza()) : "" %></label>
			<% } %> 
			<% } %>
			</td>

			<td> 
			<% if (trasgr.isCompetenzaRegionale()) { %>
			<% if ("B".equals(trasgr.getOrdinanzaEmessa())) {%>
		    <input type="radio" disabled <% if (trasgr.isRichiestaRateizzazione()){ %> checked="checked" <%} %>/> SI <br/>
			<input type="radio" disabled <% if (!trasgr.isRichiestaRateizzazione()){ %> checked="checked" <%} %>/> NO 
			<% } %>
			<% } %>
			</td>
		
			<td> 
			<% if (trasgr.isCompetenzaRegionale()) { %>
			<% if ("B".equals(trasgr.getOrdinanzaEmessa()) && trasgr.isRichiestaRateizzazione()) {%>
			<table> 
			<tr>
			<% for (int j=0; j<trasgr.getRate().length; j++ ) {%>
				<td><input type="checkbox" disabled <% if (trasgr.getRate()[j]){ %> checked="checked" <%} %> /> <%=j+1 %></td>
				<% if (j==4){ %></tr><tr> <% } %>
			<% } %>
			</tr>
			</table>
			<% } %>
			<% } %>
			</td>
			
			<td> 
			<% if (trasgr.isCompetenzaRegionale()) { %>
			<% if ("B".equals(trasgr.getOrdinanzaEmessa())) {%>
			<input type="checkbox" disabled <% if (trasgr.isIngiunzioneOblata()){ %> checked="checked" <%} %> />
			<% } %>
			<% } %>
			</td>
			
			<td> 
			<% if (trasgr.isCompetenzaRegionale()) { %>
			<% if ("B".equals(trasgr.getOrdinanzaEmessa())) {%>
			<label class="layout"><%=trasgr.getImportoEffettivamenteVersato2() %></label> &#x20ac;
			<% } %>
			<% } %>
			</td>
			
			<td> 
			<% if (trasgr.isCompetenzaRegionale()) { %>
			<% if ("B".equals(trasgr.getOrdinanzaEmessa())) {%>
			<input type="checkbox" disabled <% if (trasgr.isPresentataOpposizione()){ %> checked="checked" <%} %> /> 
			<% } %>
			<% } %>
			</td>
			
			<td> 
			<% if (trasgr.isCompetenzaRegionale()) { %>
			<% if ("B".equals(trasgr.getOrdinanzaEmessa())) {%>
			<input type="radio" disabled <% if (trasgr.isSentenzaFavorevole()){ %> checked="checked" <%} %>/> SI <br/>
			<input type="radio" disabled <% if (!trasgr.isSentenzaFavorevole()){ %> checked="checked" <%} %>/> NO 
			<% } %>
			<% } %>
			</td>
		
			<td> 
			<% if (trasgr.isCompetenzaRegionale()) { %>
			<% if ("B".equals(trasgr.getOrdinanzaEmessa()) && !trasgr.isSentenzaFavorevole()) {%>
			<label class="layout"><%=trasgr.getImportoStabilito() %></label> &#x20ac; 
			<% } %>
			<% } %>
			</td>
			
			<td> 
			<% if (trasgr.isCompetenzaRegionale()) { %>
			<% if ("B".equals(trasgr.getOrdinanzaEmessa()) && !trasgr.isSentenzaFavorevole()) {%>
			<input type="checkbox" disabled <% if (trasgr.isIngiunzioneOblataSentenza()){ %> checked="checked" <%} %> />
			<% } %>
			<% } %>
			</td>
			
			<td> 
			<% if (trasgr.isCompetenzaRegionale()) { %>
			<% if ("B".equals(trasgr.getOrdinanzaEmessa()) && !trasgr.isSentenzaFavorevole()) {%>
			<label class="layout"><%=trasgr.getImportoEffettivamenteVersato3() %></label> &#x20ac;
			<% } %>
			<% } %>
			</td>
			
			<td> 
			<% if (trasgr.isCompetenzaRegionale()) { %>
			<% if ("B".equals(trasgr.getOrdinanzaEmessa()) && !trasgr.isSentenzaFavorevole()) {%>
			<input type="checkbox" disabled <% if (trasgr.isIscrizioneRuoliSattoriali()){ %> checked="checked" <%} %> />
			<% } %>
			<% } %>
			</td>
			
			<td> 
			<% if (trasgr.isCompetenzaRegionale()) { %>
			<% if ("B".equals(trasgr.getOrdinanzaEmessa()) && !trasgr.isSentenzaFavorevole()) {%>
			<label class="layout"><%=trasgr.getImportoEffettivamenteVersato4() %></label> &#x20ac; 
			<% } %>
			<% } %>
			</td>
			
			<td>
			<% if (trasgr.getPagopaIuv()!=null && !trasgr.getPagopaIuv().equals("") && trasgr.getListaPagopaIuv().length>0) { 
				for (int k = 0; k<trasgr.getListaPagopaIuv().length; k++) {
					String iuvCompleto = (String) trasgr.getListaPagopaIuv()[k];
					String iuv[] = iuvCompleto.split("#");
					String iuvIuv = "";
					String iuvUrl = "";
					String iuvStato = "";
					try {iuvIuv = iuv[0];} catch (Exception e){}
					try {iuvUrl = iuv[1];} catch (Exception e){}
					try {iuvStato = iuv[2];} catch (Exception e){}

					%> 
					
					<% if (iuvStato.equalsIgnoreCase(Pagamento.PAGAMENTO_NON_INIZIATO)) { %>
					<a href="<%=iuvUrl%>"><%=iuvIuv %> (<%=iuvStato %>)</a>
					<%} else { %>
					<%=iuvIuv %> (<%=iuvStato %>)
					<%} %>
					<br/><br/>
					
		 		<%} } %>
		 	</td>
			
			<td> 
			<textarea class="layout" disabled><%= (trasgr.getNote1()!=null) ? trasgr.getNote1() : ""%></textarea>
			</td>
			
			<td> 
			<textarea class="layout" disabled><%= (trasgr.getNote2()!=null) ? trasgr.getNote2() : ""%></textarea>
			</td>
		
			<td>
			<div id="linkDocumentoPv_<%=i%>">
			<% if (trasgr.getAllegatoPv()!=null && !trasgr.getAllegatoPv().equals("")) { 
				for (int k = 0; k<trasgr.getListaAllegatiPv().length; k++) {%>
					<a id = "<%=trasgr.getListaAllegatiPv()[k]%>_download" href="GestioneAllegatiTrasgressori.do?command=DownloadPDF&codDocumento=<%=trasgr.getListaAllegatiPv()[k]%>&nomeDocumento=<%=trasgr.getListaAllegatiPv()[k]%>"> <label id="<%=trasgr.getListaAllegatiPv()[k]%>">DOWNLOAD <%=k+1 %></label>  </a><br/><br/>
		 			<script>recuperaOggettoAllegato('<%=trasgr.getListaAllegatiPv()[k]%>');</script>
		 		<%} } %>
		 	</div> 
		 	</td>
		 	
		 	<td>
			<div id="linkDocumentoAl_<%=i%>">
			<% if (trasgr.getAllegatoAl()!=null && !trasgr.getAllegatoAl().equals("")) { 
				for (int k = 0; k<trasgr.getListaAllegatiAl().length; k++) {%>
					<a id = "<%=trasgr.getListaAllegatiPv()[k]%>_download" href="GestioneAllegatiTrasgressori.do?command=DownloadPDF&codDocumento=<%=trasgr.getListaAllegatiAl()[k]%>&nomeDocumento=<%=trasgr.getListaAllegatiAl()[k]%>"> <label id="<%=trasgr.getListaAllegatiAl()[k]%>">DOWNLOAD <%=k+1 %></label>  </a><br/><br/>
		 			<script>recuperaOggettoAllegato('<%=trasgr.getListaAllegatiAl()[k]%>');</script>
		 		<%} } %>
		 	</div> 
		 	</td>
		 	
		 	<td>
			<div id="linkDocumento_<%=i%>">
			<% if (trasgr.getAllegatoDocumentale()!=null && !trasgr.getAllegatoDocumentale().equals("")) { 
				for (int k = 0; k<trasgr.getListaAllegatiDocumentale().length; k++) {%>
					<a id = "<%=trasgr.getListaAllegatiPv()[k]%>_download" href="GestioneAllegatiTrasgressori.do?command=DownloadPDF&codDocumento=<%=trasgr.getListaAllegatiDocumentale()[k]%>&nomeDocumento=<%=trasgr.getIdSanzione()+"_"+trasgr.getAnno()+"_"+k%>"> <label id="<%=trasgr.getListaAllegatiDocumentale()[k]%>">DOWNLOAD <%=k+1 %></label>  </a><br/><br/>
		 			<script>recuperaOggettoAllegato('<%=trasgr.getListaAllegatiDocumentale()[k]%>');</script>
		 		<%} } %>
		 	</div> 
		    <label name="titoloDocumento_<%=i%>"  id="titoloDocumento_<%=i%>"></label>
		 	</td>
		 
			<td> 
			<input type="checkbox" disabled <% if (trasgr.isPraticaChiusa()){ %> checked="checked" <%} %> /> 
			</td>
		 
		 </tr>
		
		<% } } else {%>
		
		<tr><td colspan="5"> Non sono presenti controlli per l'anno o il trimestre corrente. &nbsp; </td></tr>
		
		<% } %>
	
	</tbody>
	
	</table>
	
	
		</div>
</form>		
	</body></html>