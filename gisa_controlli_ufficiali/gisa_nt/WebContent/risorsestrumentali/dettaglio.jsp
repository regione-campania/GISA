<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.Date"%>
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
<jsp:useBean id="ListaAsl" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />

<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<script src="javascript/tooltipsy.min.js"></script>

<link rel="stylesheet" href="css/jquery-ui.css" />

<%
String edit = (String) request.getAttribute("edit");
%>

<script>

$( document ).ready(function() {
	
	});
function openMessaggioForm()
{
	$( "#esportazioneSdc" ).dialog({
	    modal: true,
	    title: "ESTRAZIONE MODELLO 2",
	    height: '400',
	    autoOpen: true,
	    zIndex: 400,
	    width :'750px',
	    show: { effect: 'drop', direction: "down" },
	    buttons: {
	        
	        "CONTINUA": function() {
	        	$("#esportazioneSdcForm").submit();
	        	 $( this ).dialog( "close" );
	           // $( this ).dialog( "close" );
	        },
	        "Esci": function() {
	        	
		           $( this ).dialog( "close" );
		        }
	    }
	});
	
// 	 $( this ).dialog( "open" );
	
}
</script>
<table class="trails" cellspacing="0">
		<tr>	
		
			<td width="100%"><a href="Dpat.do">DPAT</a> &gt <a href="Dpat.do?command=Home&combo_area=<%=DpatRS.getStrutturaAmbito()!=null ? DpatRS.getStrutturaAmbito().getId()+"" :"-1" %>&idAsl=<%=DpatRS.getIdAsl()%>&anno=<%=DpatRS.getAnno()%>">Allegati DPAT</a> &gt Modello 2 <%=DpatRS.getAnno() %> <%=ListaAsl.getSelectedValue(DpatRS.getIdAsl()) %></td>
		</tr>
	</table>
	
	<!-- SERVER DOCUMENTALE -->
	 <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
	  <img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
        <input type="button" title="PDF" value="PDF Modello 2"
        style="background-color:#FF4D00; font-weight: bold;"	
        onClick="openRichiestaPDF_DPAT('<%=DpatRS.getIdAsl()%>', '<%=DpatRS.getAnno()%>', 'DPAT_All2','<%=(DpatRS.getStrutturaAmbito()!=null && DpatRS.getStrutturaAmbito().getId()>0 ? DpatRS.getStrutturaAmbito().getId() : "-1")%>');">
        
<!--         <dhv:permission name="riapri-allegato2-view"> -->
<%-- 		 <input type="button" title="RIAPRI" style="width:130px" value="Riapri Allegato 2" onClick="javascript:window.location.href='DpatRS.do?command=RiapriRisorseStrumentali&idAsl=<%=DpatRS.getIdAsl()%>&anno=<%=DpatRS.getAnno()%>';"> --%>
<!-- </dhv:permission> -->
 <!-- SERVER DOCUMENTALE -->
 
 
 <%
 if(DpatRS.getStrutturaAmbito()!=null && DpatRS.getStrutturaAmbito().getId()>0)
 {
 %>
 <input type="button" value="Esporta Modello 2" onClick="location.href='DpatRS.do?command=GeneraExcel&anno=<%=DpatRS.getAnno() %>&combo_area=<%=DpatRS.getStrutturaAmbito().getId()%>&idAsl=<%=DpatRS.getIdAsl()%>'" style="background-color:#FF4D00; font-weight: bold;"/>
<%} %>

<form method="post">

<table width="100%" border="1">
<thead>
<tr>
<th colspan="12">ELENCO DELLE RISORSE STRUMENTALI IN POSSESSO DELLE STRUTTURE <%=(DpatRS.getFlagSianVet()!= null && ! "".equals(DpatRS.getFlagSianVet())) ? "("+DpatRS.getFlagSianVet()+")" : "" %></th>
</tr>
<tr >
	<th rowspan="2">&nbsp;</th>
	<th rowspan="2">
		N. AUTO DI SERVIZIO
		<br>(le auto utilizzate da più UU.OO. vanno conteggiate
		<br>una sola volta ed assegnate ad una sola U.O.)
	</th> 
	
	<th rowspan="2">ATTREZZATURE PER CAMPIONAMENTI
		<br>(lasciare visibile solo la risposta voluta)
	</th>
	<th colspan="2">N. PERSONAL COMPUTER</th>
	<th colspan="2">N. PERSONAL COMPUTER</th>
	
	<th rowspan="2">N. STAMPANTI</th>
	<th rowspan="2">QUANTITA' DI TELEFONI</th>
	<th rowspan="2">N. TERMOMETRI TARATI</th>
	<th colspan="2">ALTRO</th>
</tr>
<tr>
	
	<th>N. PERSONAL COMPUTER SENZA ADSL</th>
	<th>N. PERSONAL COMPUTER CON ADSL</th>
	<th>N. NOTEBOOK NON CONNESSI AD INTERNET</th>
	<th>N. NOTEBOOK CONNESSI AD INTERNET</th>
	<th>DESCRIZIONE</th>
	<th>QUANTITA'</th>
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
int sommaStampanti = 0 ;
int sommaTelefoni = 0 ;
int sommaTermom = 0 ;
int sommaQuant = 0 ;
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
	sommaStampanti += struttura.getNumStampanti() ; 
	sommaTelefoni += struttura.getNumTelefoni() ; 
	sommaTermom += struttura.getNumTermometriTarati(); 
	sommaQuant += struttura.getQuantitaAltro() ; 

	
	%> 

	<tr  class="row<%=rowid%>">
		<td class="hover" align="center" style="font-weight: bold" title="<%=struttura.getDescrizioneStrutturaLunga() %>"><%=struttura.getDescrizioneStruttura().toUpperCase() %></td>
		<td align="center" style="font-weight: bold"><%=struttura.getNumAuto() %></td>
		<td align="center" style="font-weight: bold"><%= AttrezzatureCampionamenti.getSelectedValue(struttura.getIdAttrezzatureCampionamenti())  %></td>
		<td align="center" style="font-weight: bold"><%=struttura.getNumComputerSenzaAdsl() %></td>
		<td align="center" style="font-weight: bold"><%=struttura.getNumComputerConAdsl() %></td>
		<td align="center" style="font-weight: bold"><%=struttura.getNumNotebookNonConnessi() %></td>
		<td align="center" style="font-weight: bold"><%=struttura.getNumNotebookConnessi() %></td>
		<td align="center" style="font-weight: bold"><%=struttura.getNumStampanti() %></td>
		<td align="center" style="font-weight: bold"><%=struttura.getNumTelefoni() %></td>
		<td align="center" style="font-weight: bold"><%=struttura.getNumTermometriTarati() %></td>
		<td align="center" style="font-weight: bold"><%=toHtml( struttura.getAltro_descrizione()) %></td>
		<td align="center" style="font-weight: bold"><%=struttura.getQuantitaAltro() %></td>
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
		<td ><span id = "totnumStampanti"><%="<b>"+sommaStampanti +"</b>"%></span></td>
		<td ><span id = "totnumTelefoni"><%="<b>"+sommaTelefoni +"</b>"%></span></td>
		<td ><span id = "totnumTermometri"><%="<b>"+sommaTermom +"</b>"%></span></td>
		<td >&nbsp;</td>
		<td ><span id = "totnumQuantita"><%="<b>"+sommaQuant+"</b>"%></span></td>
	
		</tr>

</tbody>

</table>
</form>


      

