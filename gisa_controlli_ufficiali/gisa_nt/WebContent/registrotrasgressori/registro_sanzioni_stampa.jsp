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
<%@page import="org.aspcfs.modules.registrotrasgressori.base.Trasgressione"%>


<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>


<link rel="stylesheet" documentale_url="" href="registrotrasgressori/css/trasgressori_screen.css" type="text/css" media="screen" />
<link rel="stylesheet" documentale_url="" href="registrotrasgressori/css/trasgressori_print.css" type="text/css" media="print" />

 
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<div class="boxIdDocumento"></div> <div class="boxOrigineDocumento"><%@ include file="../../hostName.jsp" %></div>  
<br/>

<html>

<body>
   
   <table style="border:1px solid black" width="100%">
   <tr><td style="border:0"><b>Istruzioni di lettura relative all'impaginazione</b></td></tr>
   <tr><td style="border:0"><font color="red">Nel documento le informazioni relative alla singola sanzione sono riportate divise su più pagine.<br/>
   Per leggere tutti i campi occorre scorrere l'intero documento usando come riferimento il N° prog, sempre riportato nella prima colonna di ogni pagina.</font></td></tr>
   </table>
   <br/>
 
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <thead>
  <tr> <th colspan="10"> <center>REGISTRO SANZIONI AMMINISTRATIVE ANNO <%= anno %></center> </th></tr>

			<tr>
			<th id="nprog"><strong>N° prog</strong></th>
			<th id="idcontrollo"><strong>Id controllo</strong></th>
			<th id="asl"><strong>ASL di competenza</strong></th>
			<th id="ente1"><strong>Ente accertatore 1</strong></th>
			<th id="ente2"><strong>Ente accertatore 2</strong></th>
			<th id="ente3"><strong>Ente accertatore 3</strong></th>
			<th id="pv"><strong>PV N°</strong></th>
			<th id="seq"><strong>Num. sequestro eventualmente effettuato</strong></th>
			<th id="dataAcc"><strong>Data accertamento</strong></th>
			<th id="dataProt"><strong>Data prot. in entrata in regione</strong></th>
			
					</tr>
	</thead>
		<tbody>
			<%
	
	if (listaTrasgressioni.size()>0){
		for (int i=0;i<listaTrasgressioni.size(); i++){
			Trasgressione trasgr = (Trasgressione) listaTrasgressioni.get(i);
			
			%>
			
			<tr class="<%=("C".equals(trasgr.getOrdinanzaEmessa())) ? "orange" : (trasgr.isPraticaChiusa()) ? "green" : "row"+i%2 %>">
			<td headers="nprog"><%= trasgr.getProgressivo() %>\<%=trasgr.getAnnoYY() %> &nbsp; </td> 
			<td headers="idcontrollo"> <%=trasgr.getIdControllo() %> &nbsp; </td>
			<td headers="asl">  <%=(trasgr.getAsl()!=null) ? trasgr.getAsl() : "" %> &nbsp; </td>
			
					<% LinkedHashMap<String,String> listaEnti = trasgr.getListaEnti();
			int q = 0;
			for(Map.Entry<String, String> ente : listaEnti.entrySet()){
				q++;
			%>
			<td headers="ente<%=q%>">  <%=(ente.getValue()!=null) ? ente.getValue() : "" %>  &nbsp; </td>
			<%} %>
			<% for (int o = q; o<3; o++) {%>
			<td headers="ente<%=o%>">  <%= "" %>  &nbsp; </td>
			<%} %>
				 		
			
<%-- 			<td headers="ente1">  <%=(trasgr.getEnte1()!=null) ? trasgr.getEnte1() : "" %> &nbsp; </td> --%>
<%-- 			<td headers="ente2">  <%=(trasgr.getEnte2()!=null) ? trasgr.getEnte2() : "" %> &nbsp; </td> --%>
<%-- 			<td headers="ente3">  <%=(trasgr.getEnte3()!=null) ? trasgr.getEnte3() : "" %> &nbsp; </td> --%>

			<td headers="pv"> <%=(trasgr.getPV()!=null) ? trasgr.getPV() : "" %> &nbsp; </td>
			<td headers="seq">  <%=(trasgr.getPVsequestro()!=null) ? trasgr.getPVsequestro() : "" %> &nbsp; </td>
			<td headers="dataAcc">  <%=(trasgr.getDataAccertamento()!=null) ?  toDateasString(trasgr.getDataAccertamento()) : "" %> </td>
			<td headers="dataProc">  <%=(trasgr.getDataProt()!=null) ?  toDateasString(trasgr.getDataProt()) : "" %> &nbsp; </td>
		
			
		
		</tr>
		<%} } else {%>
		<tr><td colspan="5"> Non sono presenti controlli per l'anno corrente. &nbsp; </td></tr>
		
		<% } %>
		</tbody>
		</table>

	<div style="page-break-before:always"> &nbsp;</div>

  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <thead>
  <tr> <th colspan="9"> <center>REGISTRO SANZIONI AMMINISTRATIVE ANNO <%= anno %></center> </th></tr>

			<tr>
			<th id="nprog2"><strong>N° prog</strong></th>
			<th id="tras"><strong>Trasgressore</strong></th>  
			<th id="obbl"><strong>Obbligato in solido</strong></th>
			<th id="ridotta"><strong>Importo sanzione ridotta</strong></th>
			<th id="seqrid"><strong>Importo sanzione ridotta del 30%</strong></th>
			<th id="competenza"><strong>Illecito di competenza U.O.D. 01</strong></th>
			<th id="pvoblatoridotta"><strong>PV oblato in misura ridotta</strong></th>
			<th id="pvoblatoultraridotta"><strong>PV oblato in misura ultraridotta</strong></th>
			<th id="datapagamento"><strong>Data pagamento</strong></th>
			
			
		
			</tr>
	</thead>
		<tbody>
			<%
	
	if (listaTrasgressioni.size()>0){
		for (int i=0;i<listaTrasgressioni.size(); i++){
			Trasgressione trasgr = (Trasgressione) listaTrasgressioni.get(i);
			
			%>
			<tr class="<%=("C".equals(trasgr.getOrdinanzaEmessa())) ? "orange" : (trasgr.isPraticaChiusa()) ? "green" : "row"+i%2 %>">
			<td headers="nprog2"><%= trasgr.getProgressivo() %>\<%=trasgr.getAnnoYY() %> &nbsp; </td>
				<td headers="tras">  <%=(trasgr.getTrasgressore()!=null) ? trasgr.getTrasgressore() : "" %> &nbsp; </td>
			<td headers="obbl">  <%=(trasgr.getObbligatoInSolido()!=null) ? trasgr.getObbligatoInSolido() : "" %> &nbsp; </td>
			<td headers="ridotta">  <%= trasgr.getImportoSanzioneRidotta() %> &#x20ac; &nbsp; </td>
			<td headers="seqrid">  <%=(trasgr.getImportoSequestroRiduzione() !=null) ? trasgr.getImportoSequestroRiduzione() : "" %> &nbsp; </td>
			<td headers="competenza"> <% if (trasgr.isCompetenzaRegionale()){ %> X <%} %> &nbsp; </td>
			<td headers="dataultimanotifica">  <%=(trasgr.getDataUltimaNotifica()!=null) ?  toDateasString(trasgr.getDataUltimaNotifica()) : "" %> </td>
			<td headers="pvoblatoridotta"> <% if (trasgr.isCompetenzaRegionale() && trasgr.isPagopaPvOblatoRidotta()){ %> X <%} %> &nbsp; </td>
			<td headers="pvoblatoultraridotta"> <% if (trasgr.isCompetenzaRegionale() && trasgr.isPagopaPvOblatoUltraRidotta()){ %> X <%} %> &nbsp; </td>
			<td headers="datapagamento">  <%=(trasgr.getPagopaPvDataPagamento()!=null) ?  toDateasString(trasgr.getPagopaPvDataPagamento()) : "" %> </td>
				
			</tr>
		<%} } else {%>
		<tr><td colspan="5"> Non sono presenti controlli per l'anno corrente. &nbsp; </td></tr>
		
		<% } %>
		</tbody>
		</table>
		
	<div style="page-break-before:always"> &nbsp;</div>

  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <thead>
  <tr> <th colspan="12"> <center>REGISTRO SANZIONI AMMINISTRATIVE ANNO <%= anno %></center> </th></tr>

			<tr>
			<th id="nprog3"><strong>N° prog</strong></th>
			<th id="fi"><strong>Funzionario assegnatario</strong></th>
			<th id="scritti"><strong>Presentati scritti difensivi</strong></th>	
			<th id="riduzione"><strong>Presentata richiesta riduzione sanzione e/o rateizzazione</strong></th>
			<th id="audizione"><strong>Presentata richiesta audizione</strong></th>
			<th id="argomentazioni"><strong>Ordinanza emessa</strong></th>
			<th id="numOrdinanza"><strong>Num. Ordinanza</strong></th>
			<th id="dataEm"><strong>Data di emissione dell'Ordinanza</strong></th>
			<th id="giorni"><strong>Giorni di lavorazione pratica</strong></th>
			<th id="ingiunta"><strong>Importo sanzione ingiunta</strong></th>
			<th id="dataultimanotificaordinanza"><strong>Data ultima notifica ordinanza</strong></th>
			<th id="datapagamentoordinanza"><strong>Data pagamento ordinanza</strong></th>
<!-- 			<th id="ridottoconsentitoordinanza"><strong>Pagamento ordinanza effettuato nei termini</strong></th> -->
			
			</tr>
	</thead>
		<tbody>
			<%
	
	if (listaTrasgressioni.size()>0){
		for (int i=0;i<listaTrasgressioni.size(); i++){
			Trasgressione trasgr = (Trasgressione) listaTrasgressioni.get(i);
			
			%>
		<tr class="<%=("C".equals(trasgr.getOrdinanzaEmessa())) ? "orange" : (trasgr.isPraticaChiusa()) ? "green" : "row"+i%2 %>">
		<td headers="nprog3"><%= trasgr.getProgressivo() %>\<%=trasgr.getAnnoYY() %> &nbsp; </td>
		<td headers="fi"> <% if (trasgr.isCompetenzaRegionale()) { %> <%=trasgr.getFiAssegnatario() %> <%} %> &nbsp; </td>
		<td headers="scritti"> <% if (trasgr.isCompetenzaRegionale() && trasgr.isPresentatiScritti()){ %> X <%} %> &nbsp; </td>
		<td headers="riduzione"> <% if (trasgr.isCompetenzaRegionale() && trasgr.isRichiestaRiduzioneSanzione()){ %> X <%} %> &nbsp; </td>
		<td headers="audizione"> <% if (trasgr.isCompetenzaRegionale() && trasgr.isRichiestaAudizione()){ %> X <%} %> &nbsp; </td>
		<td headers="argomentazioni"> <% if (trasgr.isCompetenzaRegionale() && "A".equals(trasgr.getOrdinanzaEmessa())){ %>  Ord. Archiviazione <%} %> <br/>
		 <% if (trasgr.isCompetenzaRegionale() && "B".equals(trasgr.getOrdinanzaEmessa())){ %> Ord. Ingiunzione <%} %>	<br/>
		 <% if (trasgr.isCompetenzaRegionale() && "C".equals(trasgr.getOrdinanzaEmessa())){ %> Pratica non lavorata <%} %> </td>
		 <td headers="numOrdinanza"> <%=(trasgr.getNumOrdinanza()!=null && trasgr.isCompetenzaRegionale()) ? trasgr.getNumOrdinanza() : "" %> &nbsp; </td>
		 <td headers="dataEm"> <%=(trasgr.getDataEmissione()!=null && trasgr.isCompetenzaRegionale()) ?  toDateasString(trasgr.getDataEmissione()) : "" %> &nbsp; </td>
		<td headers="giorni"> <%=(trasgr.getGiorniLavorazione()>-1 && trasgr.isCompetenzaRegionale()) ? trasgr.getGiorniLavorazione() : "" %> &nbsp; </td>
		<td headers="ingiunta"> <%if (!"B".equals(trasgr.getOrdinanzaEmessa()) && trasgr.isCompetenzaRegionale()) {%> <%=trasgr.getImportoSanzioneIngiunta() %> &#x20ac; <%} %> &nbsp; </td>
		<td headers="dataultimanotificaordinanza"> <%=(trasgr.getDataUltimaNotificaOrdinanza()!=null) ?  toDateasString(trasgr.getDataUltimaNotificaOrdinanza()) : "" %> &nbsp; </td>
		<td headers="datapagamentoordinanza"> <%=(trasgr.getDataPagamentoOrdinanza()!=null ) ?  toDateasString(trasgr.getDataPagamentoOrdinanza()) : "" %> &nbsp; </td>
<%-- 		<td headers="ridottoconsentitoordinanza">  <% if (trasgr.isPagamentoRidottoConsentitoOrdinanza()){ %> X <%} %> &nbsp; </td> --%>
		
			
		</tr>
		<%} } else {%>
		<tr><td colspan="5"> Non sono presenti controlli per l'anno corrente. &nbsp; </td></tr>
		
		<% } %>
		</tbody>
		</table>
		
		<div style="page-break-before:always"> &nbsp;</div>
			
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <thead>
  <tr> <th colspan="14"> <center>REGISTRO SANZIONI AMMINISTRATIVE ANNO <%= anno %></center> </th></tr>

			<tr>
			<th id="nprog4"><strong>N° prog</strong></th>
			<th id="rateizzazione"><strong>Concessa rateizzazione dell'ordinanza-ingiunzione</strong></th>
			<th id="ordinanza"><strong>Ordinanza ingiunzione oblata</strong></th>
			<th id="versato2"><strong>Importo effettivamente introitato (2)</strong></th>
			<th id="opposizione"><strong>Presentata opposizione all'ordinanza-ingiunzione</strong></th>
			<th id="favorevole"><strong>Sentenza favorevole al ricorrente</strong></th>
			<th id="stabilito"><strong>Importo stabilito dalla A.G.</strong></th>
			<th id="sentenza"><strong>Ordinanza-ingiunzione oblata secondo il dispositivo della sentenza</strong></th>
			<th id="versato3"><strong>Importo effettivamente introitato (3)</strong></th>
			<th id="ruoli"><strong>Avviata per l'esecuzione forzata</strong></th>
			<th id="versato4"><strong>Importo effettivamente introitato (4)</strong></th>
			<th id="note"><strong>Note Gruppo 1</strong></th>
			<th id="note"><strong>Note Gruppo 2</strong></th>
			<th id="praticachiusa"><strong>Pratica chiusa</strong></th>
			</tr>
	</thead>
		<tbody>
			<%
	
	if (listaTrasgressioni.size()>0){
		for (int i=0;i<listaTrasgressioni.size(); i++){
			Trasgressione trasgr = (Trasgressione) listaTrasgressioni.get(i);
			
			%>
		<tr class="<%=("C".equals(trasgr.getOrdinanzaEmessa())) ? "orange" : (trasgr.isPraticaChiusa()) ? "green" : "row"+i%2 %>">
		<td headers="nprog4"><%= trasgr.getProgressivo() %>\<%=trasgr.getAnnoYY() %> &nbsp; </td>
		<td headers="rateizzazione"> <% if (!"B".equals(trasgr.getOrdinanzaEmessa()) && trasgr.isRichiestaRateizzazione() && trasgr.isCompetenzaRegionale()){
			String rate = "SI. Rate pagate: " + trasgr.getPagopaNoRatePagate();
			%> <%= rate %>
			
		<% } %> &nbsp; </td>
		<td headers="ordinanza"> <% if (!"B".equals(trasgr.getOrdinanzaEmessa()) && trasgr.isIngiunzioneOblata() && trasgr.isCompetenzaRegionale()){ %> X <%} %> &nbsp; </td>
		<td headers="versato2">  <% if (!"B".equals(trasgr.getOrdinanzaEmessa()) && trasgr.isCompetenzaRegionale()) { %> <%= trasgr.getImportoEffettivamenteVersato2()%> &#x20ac; <%} %> &nbsp; </td>
		
		<td headers="opposizione"> <% if (!"B".equals(trasgr.getOrdinanzaEmessa()) && trasgr.isPresentataOpposizione() && trasgr.isCompetenzaRegionale()){ %> X <%} %> &nbsp; </td>
		<td headers="favorevole"><% if (!"B".equals(trasgr.getOrdinanzaEmessa()) &&  trasgr.isSentenzaFavorevole() && trasgr.isCompetenzaRegionale()){ %> X <%} %>  &nbsp; </td>
		<td headers="stabilito"> <% if (!"B".equals(trasgr.getOrdinanzaEmessa()) && !trasgr.isSentenzaFavorevole() && trasgr.isCompetenzaRegionale() ) { %> <%=trasgr.getImportoStabilito() %> &#x20ac; <%} %> &nbsp; </td>
		<td headers="sentenza"> <% if (!"B".equals(trasgr.getOrdinanzaEmessa()) && !trasgr.isSentenzaFavorevole() && trasgr.isIngiunzioneOblataSentenza() && trasgr.isCompetenzaRegionale()){ %> X <%} %> &nbsp; </td>
		<td headers="versato3">  <% if (!"B".equals(trasgr.getOrdinanzaEmessa()) && trasgr.isCompetenzaRegionale() && !trasgr.isSentenzaFavorevole()) { %> <%= trasgr.getImportoEffettivamenteVersato3()%> &#x20ac; <%} %> &nbsp; </td>
		<td headers="ruoli"> <% if (!"B".equals(trasgr.getOrdinanzaEmessa()) && !"B".equals(trasgr.getOrdinanzaEmessa()) && !trasgr.isSentenzaFavorevole() && trasgr.isIscrizioneRuoliSattoriali() && trasgr.isCompetenzaRegionale()){ %> X <%} %> &nbsp; </td>
		<td headers="versato4"> <% if (!"B".equals(trasgr.getOrdinanzaEmessa()) && trasgr.isCompetenzaRegionale() && !trasgr.isSentenzaFavorevole()) { %>  <%= trasgr.getImportoEffettivamenteVersato4()%> &#x20ac; <%} %> &nbsp; </td>
		<td headers="note1"> <%= trasgr.getNote1()%> &nbsp; </td>
		<td headers="note2"> <%= trasgr.getNote2()%> &nbsp; </td>
		<td headers="praticachiusa"><% if (trasgr.isPraticaChiusa()){ %> X <%} %>  &nbsp; </td>
		
			
		</tr>
		<%} } else {%>
		<tr><td colspan="5"> Non sono presenti controlli per l'anno corrente. &nbsp; </td></tr>
		
		<% } %>
		</tbody>
		</table>

</body>

<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>
<br/>

</html>