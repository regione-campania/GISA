<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page contentType="text/html; charset=iso-8859-1" pageEncoding="iso-8859-1"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.aspcfs.modules.dpat.base.DpatRisorseStrumentaliStruttureList"%>
<%@page import="org.aspcfs.modules.dpat.base.DpatRisorseStrumentaliStruttura"%>
<%@page import="org.aspcfs.modules.dpat.base.DpatStruttura"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="DpatRS" class="org.aspcfs.modules.dpat.base.DpatRisorseStrumentali" scope="request"/>
<jsp:useBean id="AttrezzatureCampionamenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" href="css/jquery-ui.css" />

<style media="print">
body {
  	font-size:14px !important;
  	line-height: 1.2em !important; 
 }

</style>

<!--  SERVER DOCUMENTALE -->
 <%@ page import=" org.aspcfs.modules.util.imports.ApplicationProperties"%>
    <%@page import="java.net.InetAddress"%>
    <link rel="stylesheet" type="text/css" media="print" documentale_url="" href="css/dpat_print.css" />
<!--  SERVER DOCUMENTALE -->

<!-- INIT DOCUMENTALE -->
	<%@ include file="/gestione_documenti/initEncodingDocumentale.jsp" %>
<!-- FINE INIT DOCUMENTALE -->

	<!-- SERVER DOCUMENTALE -->
	 <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
	  <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="PDF" value="PDF Modello 2"	onClick="openRichiestaPDF_DPAT('<%=DpatRS.getIdAsl()%>', '<%=DpatRS.getAnno()%>', 'DPAT_All2');">
 <!-- SERVER DOCUMENTALE -->

<%
String edit = (String) request.getAttribute("edit");
%>

<div class="documentaleStampare" style="display:none">
<div class="boxIdDocumento"></div> <div class="boxOrigineDocumento"><%@ include file="../../hostName.jsp" %></div>
</div>

<form method="post">

<table width="100%" border="1" style="page-break-after: always">
<thead>
<tr>
<th colspan="7">ELENCO DELLE RISORSE STRUMENTALI IN POSSESSO DELLE STRUTTURE <%=(DpatRS.getFlagSianVet()!= null && ! "".equals(DpatRS.getFlagSianVet())) ? "("+DpatRS.getFlagSianVet()+")" : "" %></th>
</tr>
<tr >
	<th rowspan="2">&nbsp;</th>
	<th rowspan="2">
		N. AUTO DI SERVIZIO
		<br>(le auto utilizzate da piu' UU.OO. vanno conteggiate
		<br>una sola volta ed assegnate ad una sola U.O.)
	</th> 
	
	<th rowspan="2">ATTREZZATURE PER CAMPIONAMENTI
		<br>(lasciare visibile solo la risposta voluta)
	</th>
	<th colspan="2">N. PERSONAL COMPUTER</th>
	<th colspan="2">N. PERSONAL COMPUTER</th>
	
</tr>
<tr>
	
	<th>N. PERSONAL COMPUTER SENZA ADSL</th>
	<th>N. PERSONAL COMPUTER CON ADSL</th>
	<th>N. NOTEBOOK NON CONNESSI AD INTERNET</th>
	<th>N. NOTEBOOK CONNESSI AD INTERNET</th>
	
</tr>



</thead>
	<tbody id = "bodytable">
<%


DpatRisorseStrumentaliStruttureList listStrutture =  DpatRS.getListaStrutture();
int rowid = 0 ;
AttrezzatureCampionamenti.setSelectStyle("style=\"width: 100%;heigh:100%;\"");
int sommaAuto = 0 ;
int sommaPcNoInt = 0 ;
int sommaPcInt = 0  ;
int sommaNbNoConn = 0 ;
int sommaNbConn =  0 ;
for (int i = 0 ; i < listStrutture.size(); i++)
{
	rowid = (rowid != 1 ? 1 : 2);
	DpatRisorseStrumentaliStruttura struttura = (DpatRisorseStrumentaliStruttura)listStrutture.get(i);
	AttrezzatureCampionamenti.setJsEvent("onchange=\"aggiornaDati("+struttura.getId()+",'id_attrezzature_campionamenti',this.value,this)\"");
	sommaAuto += struttura.getNumAuto() ; 
	sommaPcNoInt += struttura.getNumComputerSenzaAdsl() ; 
	sommaPcInt += struttura.getNumComputerConAdsl() ; 
	sommaNbNoConn += struttura.getNumNotebookNonConnessi() ; 
	sommaNbConn += struttura.getNumNotebookConnessi() ; 
	
	%> 

	<tr class = "row<%=rowid%>">
		<td ><%=fixEncoding(struttura.getDescrizioneStruttura().toUpperCase()) %></td>
		<td ><%=struttura.getNumAuto() %></td>
		<td ><%= fixEncoding(AttrezzatureCampionamenti.getSelectedValue(struttura.getIdAttrezzatureCampionamenti()))  %></td>
		<td ><%=struttura.getNumComputerSenzaAdsl() %></td>
		<td ><%=struttura.getNumComputerConAdsl() %></td>
		<td ><%=struttura.getNumNotebookNonConnessi() %></td>
		<td ><%=struttura.getNumNotebookConnessi() %></td>
	
	</tr>
		
		
		
	
		

	
	<%
}

%>
<tr bgcolor= "#ffdead">
		<td ><b>TOT.</b></td>
		<td ><span id = "totnumAuto"><%="<b>"+sommaAuto +"</b>"%></span></td>
		<td >&nbsp;</td>	
		<td ><span id = "totnumComputerNoAdsl"><%="<b>"+sommaPcNoInt +"</b>"%></span></td>
		<td ><span id = "totnumComputerAdsl"><%="<b>"+sommaPcInt +"</b>"%></span></td>
		<td ><span id = "totnumNotebookNoInternet"><%="<b>"+sommaNbNoConn +"</b>"%></span></td>
		<td ><span id = "totnumNotebookInternet"><%="<b>"+sommaNbConn +"</b>"%></span></td>

	
		</tr>

</tbody>

</table>



<table width="100%" border="1">
<thead>
<tr>
<th colspan="6">ELENCO DELLE RISORSE STRUMENTALI IN POSSESSO DELLE STRUTTURE <%=(DpatRS.getFlagSianVet()!= null && ! "".equals(DpatRS.getFlagSianVet())) ? "("+DpatRS.getFlagSianVet()+")" : "" %></th>
</tr>
<tr >
	<th rowspan="2">&nbsp;</th>
		
	<th rowspan="2">N. STAMPANTI</th>
	<th rowspan="2">QUANTITA' DI TELEFONI</th>
	<th rowspan="2">N. TERMOMETRI TARATI</th>
	<th colspan="2">ALTRO</th>
</tr>
<tr>
	
	
	<th>DESCRIZIONE</th>
	<th>QUANTITA'</th>
</tr>



</thead>
	<tbody id = "bodytable">
<%


DpatRisorseStrumentaliStruttureList listStrutture2 =  DpatRS.getListaStrutture();
int rowid2 = 0 ;
AttrezzatureCampionamenti.setSelectStyle("style=\"width: 100%;heigh:100%;\"");
int sommaStampanti = 0 ;
int sommaTelefoni = 0 ;
int sommaTermom = 0 ;
int sommaQuant = 0 ;
for (int i2 = 0 ; i2 < listStrutture2.size(); i2++)
{
	rowid2 = (rowid2 != 1 ? 1 : 2);
	DpatRisorseStrumentaliStruttura struttura2 = (DpatRisorseStrumentaliStruttura)listStrutture2.get(i2);
	AttrezzatureCampionamenti.setJsEvent("onchange=\"aggiornaDati("+struttura2.getId()+",'id_attrezzature_campionamenti',this.value,this)\"");
	sommaStampanti += struttura2.getNumStampanti() ; 
	sommaTelefoni += struttura2.getNumTelefoni() ; 
	sommaTermom += struttura2.getNumTermometriTarati(); 
	sommaQuant += struttura2.getQuantitaAltro() ; 

	
	%> 

	<tr class = "row<%=rowid2%>">
		<td ><%=fixEncoding(struttura2.getDescrizioneStruttura().toUpperCase()) %></td>
		
		<td ><%=struttura2.getNumStampanti() %></td>
		<td ><%=struttura2.getNumTelefoni() %></td>
		<td ><%=struttura2.getNumTermometriTarati() %></td>
		<td ><%=fixEncoding(toHtml( struttura2.getAltro_descrizione())) %></td>
		<td ><%=struttura2.getQuantitaAltro() %></td>
	</tr>
		
		
		
	
		

	
	<%
}

%>
<tr bgcolor= "#ffdead">
		<td ><b>TOT.</b></td>
		<td ><span id = "totnumStampanti"><%="<b>"+sommaStampanti +"</b>"%></span></td>
		<td ><span id = "totnumTelefoni"><%="<b>"+sommaTelefoni +"</b>"%></span></td>
		<td ><span id = "totnumTermometri"><%="<b>"+sommaTermom +"</b>"%></span></td>
		<td >&nbsp;</td>
		<td ><span id = "totnumQuantita"><%="<b>"+sommaQuant+"</b>"%></span></td>
	
		</tr>

</tbody>

</table>









</form>

